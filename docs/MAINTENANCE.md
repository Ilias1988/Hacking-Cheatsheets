# Maintenance Guide

> **Last verified:** 2026-09-17  
> **Checked against:** Repository validation and contribution workflow

This repository is a field reference. Accuracy and recoverability are more important than the number of commands collected.

## Document metadata

New and substantially revised cheatsheets should begin with:

```markdown
> **Last verified:** YYYY-MM-DD  
> **Tested with:** Tool X.Y on Kali/Ubuntu/Windows  
> **Scope:** Authorized labs and assessments only
```

Use exact versions when behavior depends on a release. If a command was checked only against official documentation, say so instead of claiming it was executed.

## Content lifecycle

1. **Current:** Tested or checked against a maintained upstream source within the last 12 months.
2. **Review due:** Older than 12 months or affected by a major upstream release.
3. **Legacy:** Still useful for older environments but not the recommended workflow.
4. **Retired:** Removed from navigation, with a short migration note when readers may still encounter it.

Review high-change topics—cloud APIs, identity platforms, scanners, and social-media OSINT—at least every six months.

## Sources and attribution

- Prefer official documentation, release notes, standards, and maintained upstream repositories.
- Link the source near version-sensitive instructions.
- Do not copy large portions of third-party documentation.
- Preserve license notices when adapting code or templates.

## Translations

English documents are canonical. A translation must link back to the canonical document and state its verification date. When a translation is missing, navigation must fall back to the English document rather than link to a nonexistent file.

Translation paths follow this format:

```text
Topic/
├── README.md
└── translations/
    └── README.it.md
```

## Pull-request checks

Run these commands before opening a pull request:

```bash
python scripts/validate_docs.py .
python -m unittest discover -s tests -v
python scripts/content_health.py . --require-status
npx markdownlint-cli2 "**/*.md"
```

For tools covered by the container manifest, also run the relevant CLI help
test. See [Tool Documentation Testing](./TOOL-TESTING.md).

The content-health report is advisory while the legacy library is reviewed. New
or substantially revised guides must include verification metadata. The GitHub
Actions workflow also checks external links. Intermittent rate limits may be
retried; broken or redirected project links should be updated to the maintained
canonical source.

See [Verification Policy](./VERIFICATION.md) for the trust labels used by the
repository.
