from datetime import date
from pathlib import Path
from tempfile import TemporaryDirectory
from unittest import TestCase

from scripts.content_health import status_for


class ContentHealthTests(TestCase):
    def test_current_verified_document(self) -> None:
        with TemporaryDirectory() as directory:
            path = Path(directory) / "guide.md"
            path.write_text(
                "# Guide\n\n> **Last verified:** 2026-01-01\n",
                encoding="utf-8",
            )
            self.assertEqual(status_for(path, date(2026, 6, 1)), ("current", "2026-01-01"))

    def test_review_pending_document(self) -> None:
        with TemporaryDirectory() as directory:
            path = Path(directory) / "guide.md"
            path.write_text(
                "# Guide\n\n> **Review status:** Source verification pending.\n",
                encoding="utf-8",
            )
            self.assertEqual(status_for(path, date(2026, 6, 1)), ("review-pending", None))

    def test_old_verification_is_due(self) -> None:
        with TemporaryDirectory() as directory:
            path = Path(directory) / "guide.md"
            path.write_text(
                "# Guide\n\n> **Last verified:** 2024-01-01\n",
                encoding="utf-8",
            )
            self.assertEqual(status_for(path, date(2026, 6, 1)), ("review-due", "2024-01-01"))
