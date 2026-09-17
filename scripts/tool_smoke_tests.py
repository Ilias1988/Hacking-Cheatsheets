#!/usr/bin/env python3
"""Validate documented CLI flags using official tool containers.

The script runs only version/help commands. Images pulled by this run are
removed in a finally block unless --keep-images is supplied. Images that were
already present before the run are never removed automatically.
"""

from __future__ import annotations

import argparse
import hashlib
import json
import platform
import re
import shlex
import shutil
import subprocess
import sys
import tempfile
import urllib.request
from pathlib import Path
from typing import Any


ANSI_RE = re.compile(r"\x1b\[[0-9;]*m")
FLAG_RE = re.compile(r"(?<![A-Za-z0-9_-])(--?[A-Za-z0-9][A-Za-z0-9-]*)")
GROUPED_FLAG_RE = re.compile(
    r"(?<![A-Za-z0-9_-])(-[A-Za-z0-9]+(?:/[A-Za-z0-9]+)+)"
)


def run(command: list[str], *, check: bool = True) -> subprocess.CompletedProcess[str]:
    return subprocess.run(
        command,
        check=check,
        capture_output=True,
        text=True,
        encoding="utf-8",
        errors="replace",
    )


def image_present(image: str) -> bool:
    return run(["docker", "image", "inspect", image], check=False).returncode == 0


def clean_output(result: subprocess.CompletedProcess[str]) -> str:
    return ANSI_RE.sub("", result.stdout + result.stderr)


def extract_flags(help_text: str) -> set[str]:
    flags = set(FLAG_RE.findall(help_text))
    for group in GROUPED_FLAG_RE.findall(help_text):
        first, *abbreviated = group.split("/")
        flags.add(first)
        flags.update(f"-{flag}" for flag in abbreviated)
    return flags


def validate_output(
    name: str,
    config: dict[str, Any],
    version: str,
    help_text: str,
) -> list[str]:
    failures: list[str] = []
    version_pattern = str(config["version_pattern"])
    version_line = next(
        (line.strip() for line in version.splitlines() if re.search(version_pattern, line)),
        next(
            (line.strip() for line in version.splitlines() if "version" in line.lower()),
            version.strip().splitlines()[0] if version.strip() else "unknown",
        ),
    )
    print(f"[{name}] {version_line}")

    if not re.search(version_pattern, version):
        failures.append(f"{name}: version output did not match {config['version_pattern']}")

    available = extract_flags(help_text)
    missing = sorted(set(config["expected_flags"]) - available)
    if missing:
        failures.append(f"{name}: missing documented flags: {', '.join(missing)}")
    else:
        print(f"[{name}] {len(config['expected_flags'])} documented flags present")
    return failures


def test_container_tool(
    name: str, config: dict[str, Any], keep_images: bool
) -> list[str]:
    image = str(config["image"])
    existed = image_present(image)
    failures: list[str] = []

    print(f"[{name}] image={image} preexisting={str(existed).lower()}")
    try:
        if not existed:
            pull = run(["docker", "pull", image])
            print(clean_output(pull).strip().splitlines()[-1])

        version = clean_output(
            run(["docker", "run", "--rm", image, *config["version_args"]])
        )
        help_text = clean_output(
            run(["docker", "run", "--rm", image, *config["help_args"]])
        )
        failures.extend(validate_output(name, config, version, help_text))
    except subprocess.CalledProcessError as error:
        output = clean_output(error)
        failures.append(f"{name}: command failed ({error.returncode}): {output.strip()}")
    finally:
        if not keep_images and not existed and image_present(image):
            removal = run(["docker", "image", "rm", image], check=False)
            if removal.returncode == 0:
                print(f"[{name}] removed test image")
            else:
                failures.append(f"{name}: could not remove test image")

    return failures


def platform_key() -> str:
    system = "windows" if sys.platform == "win32" else "linux"
    machine = platform.machine().lower()
    architecture = "amd64" if machine in {"amd64", "x86_64"} else "arm64"
    return f"{system}-{architecture}"


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as source:
        for chunk in iter(lambda: source.read(1024 * 1024), b""):
            digest.update(chunk)
    return digest.hexdigest()


def test_release_tool(name: str, config: dict[str, Any]) -> list[str]:
    failures: list[str] = []
    key = platform_key()
    asset = config.get("assets", {}).get(key)
    if not asset:
        return [f"{name}: no release asset configured for {key}"]

    print(f"[{name}] official release asset={asset['name']}")
    try:
        with tempfile.TemporaryDirectory(prefix=f"{name}-smoke-") as temp_name:
            temp = Path(temp_name)
            archive = temp / str(asset["name"])
            urllib.request.urlretrieve(str(asset["url"]), archive)
            actual_digest = sha256(archive)
            expected_digest = str(asset["sha256"]).lower()
            if actual_digest != expected_digest:
                return [
                    f"{name}: SHA-256 mismatch: expected {expected_digest}, got {actual_digest}"
                ]
            print(f"[{name}] SHA-256 verified")

            extract_dir = temp / "extracted"
            extract_dir.mkdir()
            shutil.unpack_archive(archive, extract_dir)
            executable = extract_dir / str(asset["binary"])
            if not executable.is_file():
                return [f"{name}: binary not found in official release archive"]
            executable.chmod(executable.stat().st_mode | 0o111)

            version = clean_output(run([str(executable), *config["version_args"]]))
            help_text = clean_output(run([str(executable), *config["help_args"]]))
            failures.extend(validate_output(name, config, version, help_text))
        print(f"[{name}] removed downloaded archive and extracted binary")
    except (OSError, ValueError, shutil.ReadError, subprocess.CalledProcessError) as error:
        failures.append(f"{name}: release test failed: {error}")
    return failures


def test_rpm_container_tool(
    name: str, config: dict[str, Any], keep_images: bool
) -> list[str]:
    image = str(config["base_image"])
    existed = image_present(image)
    asset = config["asset"]
    failures: list[str] = []
    print(f"[{name}] base_image={image} preexisting={str(existed).lower()}")
    try:
        if not existed:
            pull = run(["docker", "pull", image])
            print(clean_output(pull).strip().splitlines()[-1])

        rpm_path = f"/tmp/{asset['name']}"
        shell_command = "; ".join(
            [
                "set -eu",
                "dnf -q -y install curl ca-certificates cpio rpm >/dev/null",
                f"curl -fsSL -o {shlex.quote(rpm_path)} {shlex.quote(str(asset['url']))}",
                f"echo {shlex.quote(str(asset['sha256']) + '  ' + rpm_path)} | sha256sum -c -",
                "mkdir /tmp/nmap-root",
                f"cd /tmp/nmap-root && rpm2cpio {shlex.quote(rpm_path)} | cpio -idm --quiet",
                "NMAPDIR=/tmp/nmap-root/usr/share/nmap /tmp/nmap-root/usr/bin/nmap --version",
                "NMAPDIR=/tmp/nmap-root/usr/share/nmap /tmp/nmap-root/usr/bin/nmap --help",
            ]
        )
        output = clean_output(
            run(
                [
                    "docker",
                    "run",
                    "--rm",
                    "--entrypoint",
                    "/bin/sh",
                    image,
                    "-c",
                    shell_command,
                ]
            )
        )
        print(f"[{name}] official RPM SHA-256 verified")
        failures.extend(validate_output(name, config, output, output))
    except subprocess.CalledProcessError as error:
        failures.append(
            f"{name}: RPM container test failed ({error.returncode}): "
            f"{clean_output(error).strip()}"
        )
    finally:
        if not keep_images and not existed and image_present(image):
            removal = run(["docker", "image", "rm", image], check=False)
            if removal.returncode == 0:
                print(f"[{name}] removed base test image")
            else:
                failures.append(f"{name}: could not remove base test image")
    return failures


def test_git_tool(name: str, config: dict[str, Any]) -> list[str]:
    failures: list[str] = []
    repository = str(config["repository"])
    ref = str(config["ref"])
    print(f"[{name}] official repository={repository} ref={ref}")
    try:
        with tempfile.TemporaryDirectory(prefix=f"{name}-smoke-") as temp_name:
            checkout = Path(temp_name) / "checkout"
            run(
                [
                    "git",
                    "clone",
                    "--quiet",
                    "--depth",
                    "1",
                    "--branch",
                    ref,
                    repository,
                    str(checkout),
                ]
            )
            revision = run(
                ["git", "-C", str(checkout), "rev-parse", "HEAD"]
            ).stdout.strip()
            print(f"[{name}] commit={revision}")

            executable = checkout / str(config["executable"])
            version = clean_output(
                run([sys.executable, str(executable), *config["version_args"]])
            )
            help_text = clean_output(
                run([sys.executable, str(executable), *config["help_args"]])
            )
            failures.extend(validate_output(name, config, version, help_text))
        print(f"[{name}] removed temporary repository checkout")
    except subprocess.CalledProcessError as error:
        failures.append(
            f"{name}: git checkout test failed ({error.returncode}): "
            f"{clean_output(error).strip()}"
        )
    except OSError as error:
        failures.append(f"{name}: git checkout test failed: {error}")
    return failures


def test_git_container_tool(
    name: str, config: dict[str, Any], keep_images: bool
) -> list[str]:
    image = str(config["base_image"])
    existed = image_present(image)
    repository = str(config["repository"])
    ref = str(config["ref"])
    failures: list[str] = []
    print(f"[{name}] base_image={image} preexisting={str(existed).lower()}")
    try:
        if not existed:
            pull = run(["docker", "pull", image])
            print(clean_output(pull).strip().splitlines()[-1])

        shell_command = "; ".join(
            [
                "set -eu",
                "apt-get update -qq",
                "apt-get install -qq -y git ca-certificates >/dev/null",
                f"git clone --quiet --depth 1 --branch {shlex.quote(ref)} {shlex.quote(repository)} /tmp/checkout",
                "git -C /tmp/checkout rev-parse HEAD",
                f"python /tmp/checkout/{shlex.quote(str(config['executable']))} {' '.join(shlex.quote(str(arg)) for arg in config['version_args'])}",
                f"python /tmp/checkout/{shlex.quote(str(config['executable']))} {' '.join(shlex.quote(str(arg)) for arg in config['help_args'])}",
            ]
        )
        output = clean_output(
            run(
                [
                    "docker",
                    "run",
                    "--rm",
                    "--entrypoint",
                    "/bin/sh",
                    image,
                    "-c",
                    shell_command,
                ]
            )
        )
        revision = next(
            (line for line in output.splitlines() if re.fullmatch(r"[0-9a-f]{40}", line)),
            "unknown",
        )
        print(f"[{name}] commit={revision}")
        failures.extend(validate_output(name, config, output, output))
    except subprocess.CalledProcessError as error:
        failures.append(
            f"{name}: git container test failed ({error.returncode}): "
            f"{clean_output(error).strip()}"
        )
    finally:
        if not keep_images and not existed and image_present(image):
            removal = run(["docker", "image", "rm", image], check=False)
            if removal.returncode == 0:
                print(f"[{name}] removed base test image")
            else:
                failures.append(f"{name}: could not remove base test image")
    return failures


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument(
        "--manifest",
        default=Path(__file__).with_name("tool-smoke-tests.json"),
        type=Path,
    )
    parser.add_argument("--tool", action="append", dest="tools")
    parser.add_argument("--keep-images", action="store_true")
    args = parser.parse_args()

    manifest = json.loads(args.manifest.read_text(encoding="utf-8"))
    selected = args.tools or list(manifest)
    unknown = sorted(set(selected) - set(manifest))
    if unknown:
        print(f"unknown tools: {', '.join(unknown)}", file=sys.stderr)
        return 2

    failures: list[str] = []
    for name in selected:
        config = manifest[name]
        if "image" in config:
            if shutil.which("docker") is None:
                failures.append(f"{name}: docker is required")
            else:
                failures.extend(test_container_tool(name, config, args.keep_images))
        elif config.get("source") == "github-release":
            failures.extend(test_release_tool(name, config))
        elif config.get("source") == "rpm-container":
            if shutil.which("docker") is None:
                failures.append(f"{name}: docker is required")
            else:
                failures.extend(test_rpm_container_tool(name, config, args.keep_images))
        elif config.get("source") == "git-checkout":
            if shutil.which("git") is None:
                failures.append(f"{name}: git is required")
            else:
                failures.extend(test_git_tool(name, config))
        elif config.get("source") == "git-container":
            if shutil.which("docker") is None:
                failures.append(f"{name}: docker is required")
            else:
                failures.extend(
                    test_git_container_tool(name, config, args.keep_images)
                )
        else:
            failures.append(f"{name}: unsupported source configuration")

    if failures:
        print("\nSmoke-test failures", file=sys.stderr)
        for failure in failures:
            print(f"- {failure}", file=sys.stderr)
        return 1

    print(f"\nPassed smoke tests for {len(selected)} tool(s).")
    return 0


if __name__ == "__main__":
    sys.exit(main())
