from pathlib import Path
from tempfile import TemporaryDirectory
from unittest import TestCase

from scripts.validate_docs import validate_file


class ValidateDocsTests(TestCase):
    def test_valid_relative_link(self) -> None:
        with TemporaryDirectory() as directory:
            root = Path(directory)
            target = root / "target.md"
            source = root / "source.md"
            target.write_text("# Target\n", encoding="utf-8")
            source.write_text("[Target](target.md)\n", encoding="utf-8")

            self.assertEqual(validate_file(root, source), [])

    def test_missing_relative_link(self) -> None:
        with TemporaryDirectory() as directory:
            root = Path(directory)
            source = root / "source.md"
            source.write_text("[Missing](missing.md)\n", encoding="utf-8")

            problems = validate_file(root, source)
            self.assertEqual(len(problems), 1)
            self.assertIn("missing target", problems[0].message)

    def test_unclosed_fence(self) -> None:
        with TemporaryDirectory() as directory:
            root = Path(directory)
            source = root / "source.md"
            source.write_text("```bash\necho test\n", encoding="utf-8")

            problems = validate_file(root, source)
            self.assertEqual(len(problems), 1)
            self.assertEqual(problems[0].message, "unclosed fenced code block")

    def test_long_outer_fence_can_contain_short_fence(self) -> None:
        with TemporaryDirectory() as directory:
            root = Path(directory)
            source = root / "source.md"
            source.write_text(
                "````markdown\n```bash\necho test\n```\n````\n",
                encoding="utf-8",
            )

            self.assertEqual(validate_file(root, source), [])
