import json
from pathlib import Path
from unittest import TestCase

from scripts.tool_smoke_tests import ANSI_RE, FLAG_RE, extract_flags


ROOT = Path(__file__).resolve().parents[1]


class ToolSmokeTests(TestCase):
    def test_flag_parser_supports_short_and_long_flags(self) -> None:
        help_text = "  -u, -target string[]  target URLs\n  -rl, -rate-limit int"
        self.assertEqual(
            set(FLAG_RE.findall(help_text)),
            {"-u", "-target", "-rl", "-rate-limit"},
        )

    def test_flag_parser_expands_nmap_grouped_flags(self) -> None:
        help_text = "  -sS/sT/sA/sW/sM\n  -PE/PP/PM\n  -6: Enable IPv6"
        self.assertTrue(
            {"-sS", "-sT", "-sA", "-sW", "-sM", "-PE", "-PP", "-PM", "-6"}
            <= extract_flags(help_text)
        )

    def test_ansi_sequences_are_removed(self) -> None:
        self.assertEqual(ANSI_RE.sub("", "\x1b[34mVersion\x1b[0m"), "Version")

    def test_manifest_has_unique_expected_flags(self) -> None:
        manifest_path = ROOT / "scripts" / "tool-smoke-tests.json"
        manifest = json.loads(manifest_path.read_text(encoding="utf-8"))
        self.assertGreaterEqual(len(manifest), 3)
        for name, config in manifest.items():
            flags = config["expected_flags"]
            self.assertEqual(len(flags), len(set(flags)), name)
            self.assertTrue(config["version_pattern"], name)
            self.assertTrue(
                config.get("image")
                or config.get("assets")
                or config.get("base_image")
                or config.get("repository"),
                name,
            )

    def test_release_assets_are_pinned_by_sha256(self) -> None:
        manifest_path = ROOT / "scripts" / "tool-smoke-tests.json"
        manifest = json.loads(manifest_path.read_text(encoding="utf-8"))
        release_tools = [
            config
            for config in manifest.values()
            if config.get("source") == "github-release"
        ]
        self.assertGreaterEqual(len(release_tools), 1)
        for config in release_tools:
            for asset in config["assets"].values():
                self.assertEqual(len(asset["sha256"]), 64)
                int(asset["sha256"], 16)
                self.assertTrue(asset["url"].startswith("https://github.com/"))

    def test_rpm_assets_are_pinned_to_nmap_org(self) -> None:
        manifest_path = ROOT / "scripts" / "tool-smoke-tests.json"
        manifest = json.loads(manifest_path.read_text(encoding="utf-8"))
        rpm_tools = [
            config
            for config in manifest.values()
            if config.get("source") == "rpm-container"
        ]
        self.assertGreaterEqual(len(rpm_tools), 1)
        for config in rpm_tools:
            asset = config["asset"]
            self.assertEqual(len(asset["sha256"]), 64)
            int(asset["sha256"], 16)
            self.assertTrue(asset["url"].startswith("https://nmap.org/"))

    def test_git_sources_use_official_github_repositories(self) -> None:
        manifest_path = ROOT / "scripts" / "tool-smoke-tests.json"
        manifest = json.loads(manifest_path.read_text(encoding="utf-8"))
        git_tools = [
            config
            for config in manifest.values()
            if config.get("source") in {"git-checkout", "git-container"}
        ]
        self.assertGreaterEqual(len(git_tools), 1)
        for config in git_tools:
            self.assertTrue(config["repository"].startswith("https://github.com/"))
            self.assertTrue(config["ref"])
            self.assertTrue(config["executable"].endswith(".py"))
