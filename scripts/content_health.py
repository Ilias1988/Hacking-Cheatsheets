#!/usr/bin/env python3
"""Report verification metadata coverage for canonical Markdown guides."""

from __future__ import annotations

import argparse
import re
import sys
from collections import Counter
from datetime import date, datetime
from pathlib import Path


VERIFIED_RE = re.compile(r"\*\*Last verified:\*\*\s*(\d{4}-\d{2}-\d{2})", re.IGNORECASE)
REVIEW_RE = re.compile(r"\*\*Review status:\*\*\s*(?:Source verification pending|Partial source review)", re.IGNORECASE)
SKIP_PARTS = {".git", ".github", "translations", "work", "node_modules"}
SKIP_NAMES = {
    "CODE_OF_CONDUCT.md",
    "CONTRIBUTING.md",
    "CONTRIBUTING.it.md",
    "LICENSE.md",
    "README.it.md",
    "SECURITY.md",
}


def canonical_guides(root: Path) -> list[Path]:
    return sorted(
        path
        for path in root.rglob("*.md")
        if path.name not in SKIP_NAMES
        and not any(part in SKIP_PARTS for part in path.relative_to(root).parts)
    )


def status_for(path: Path, today: date) -> tuple[str, str | None]:
    text = path.read_text(encoding="utf-8")
    match = VERIFIED_RE.search(text)
    if not match:
        if REVIEW_RE.search(text):
            return "review-pending", None
        return "unreviewed", None

    try:
        verified = datetime.strptime(match.group(1), "%Y-%m-%d").date()
    except ValueError:
        return "invalid-date", match.group(1)

    age = (today - verified).days
    if age < 0:
        return "future-date", match.group(1)
    if age > 365:
        return "review-due", match.group(1)
    return "current", match.group(1)


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("root", nargs="?", default=".")
    parser.add_argument(
        "--strict",
        action="store_true",
        help="fail when any canonical guide is not current",
    )
    parser.add_argument(
        "--require-status",
        action="store_true",
        help="fail when a guide is neither verified nor explicitly review-pending",
    )
    args = parser.parse_args()
    root = Path(args.root).resolve()
    today = date.today()
    rows: list[tuple[Path, str, str | None]] = []

    for path in canonical_guides(root):
        status, verified = status_for(path, today)
        rows.append((path.relative_to(root), status, verified))

    counts = Counter(status for _, status, _ in rows)
    print("Content verification status")
    print("---------------------------")
    for status in (
        "current",
        "review-due",
        "review-pending",
        "unreviewed",
        "invalid-date",
        "future-date",
    ):
        print(f"{status:12} {counts[status]:4}")

    needs_review = [row for row in rows if row[1] != "current"]
    if needs_review:
        print("\nReview queue")
        print("------------")
        for path, status, verified in needs_review:
            suffix = f" ({verified})" if verified else ""
            print(f"{status:12} {path}{suffix}")

    if args.strict and needs_review:
        return 1
    invalid_status = {"unreviewed", "invalid-date", "future-date"}
    if args.require_status and any(status in invalid_status for _, status, _ in rows):
        return 1
    return 0


if __name__ == "__main__":
    sys.exit(main())
