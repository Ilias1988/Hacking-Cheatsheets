#!/usr/bin/env python3
"""Validate Markdown structure and repository-local links without dependencies."""

from __future__ import annotations

import argparse
import re
import sys
from dataclasses import dataclass
from pathlib import Path
from urllib.parse import unquote


LINK_RE = re.compile(r"!?\[[^\]]*\]\(([^)]+)\)")
FENCE_RE = re.compile(r"^\s*(`{3,}|~{3,})")
REMOTE_SCHEMES = ("http://", "https://", "mailto:", "tel:", "data:")
SKIP_DIRS = {".git", ".venv", "node_modules", "work"}


@dataclass(frozen=True)
class Problem:
    path: Path
    line: int
    message: str


def markdown_files(root: Path) -> list[Path]:
    return sorted(
        path
        for path in root.rglob("*.md")
        if not any(part in SKIP_DIRS for part in path.relative_to(root).parts)
    )


def clean_destination(raw: str) -> str:
    destination = raw.strip()
    if destination.startswith("<") and destination.endswith(">"):
        destination = destination[1:-1]
    destination = re.split(r"\s+[\"']", destination, maxsplit=1)[0]
    return destination


def validate_file(root: Path, path: Path) -> list[Problem]:
    problems: list[Problem] = []
    active_fence: tuple[str, int] | None = None

    for number, line in enumerate(path.read_text(encoding="utf-8").splitlines(), 1):
        fence = FENCE_RE.match(line)
        if fence:
            token = fence.group(1)
            marker = token[0]
            if active_fence is None:
                active_fence = (marker, len(token))
            elif active_fence[0] == marker and len(token) >= active_fence[1]:
                active_fence = None
            continue

        if active_fence is not None:
            continue

        for match in LINK_RE.finditer(line):
            destination = clean_destination(match.group(1))
            if not destination or destination.startswith("#"):
                continue
            if destination.lower().startswith(REMOTE_SCHEMES):
                continue

            relative = unquote(re.split(r"[#?]", destination, maxsplit=1)[0])
            if not relative:
                continue
            target = (path.parent / relative).resolve()

            try:
                target.relative_to(root.resolve())
            except ValueError:
                problems.append(Problem(path, number, f"link escapes repository: {destination}"))
                continue

            if not target.exists():
                problems.append(Problem(path, number, f"missing target: {destination}"))

    if active_fence is not None:
        problems.append(Problem(path, 0, "unclosed fenced code block"))

    return problems


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("root", nargs="?", default=".")
    args = parser.parse_args()
    root = Path(args.root).resolve()

    problems: list[Problem] = []
    files = markdown_files(root)
    for path in files:
        problems.extend(validate_file(root, path))

    if problems:
        for problem in problems:
            location = problem.path.relative_to(root)
            suffix = f":{problem.line}" if problem.line else ""
            print(f"{location}{suffix}: {problem.message}")
        print(f"\n{len(problems)} documentation problem(s) found in {len(files)} Markdown files.")
        return 1

    print(f"Validated {len(files)} Markdown files: internal links and code fences are clean.")
    return 0


if __name__ == "__main__":
    sys.exit(main())
