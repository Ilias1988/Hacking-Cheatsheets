# Content Verification Policy

> **Last verified:** 2026-09-17  
> **Checked against:** Repository maintenance and CI controls

This repository distinguishes verified content from historical material. A
guide without a trust label must not pass CI.

## Trust Labels

### Verified

```markdown
> **Last verified:** YYYY-MM-DD  
> **Checked against:** Named official documentation or maintained upstream source
```

“Verified” means the document was compared with the named source on that date.
It does not imply that every command was executed. A lab execution claim must
name the tested version and environment separately.

### Review Pending

```markdown
> **Review status:** Source verification pending. Confirm version-sensitive commands against the official documentation before use.
```

This label preserves useful historical material without presenting it as
current. Review-pending guides should not be the basis for production testing
until their commands, flags, claims, and links have been checked.

### Partial Review

```markdown
> **Review status:** Partial source review. Current sections are cited, but legacy sections still require verification.
```

Use this only during migration. Replace it with a verified date after the whole
document has been reviewed, or split verified content from legacy material.

## Source Standard

Prefer, in order:

1. Standards and vendor or project documentation.
2. Maintained upstream repositories and release notes.
3. Established security references such as OWASP, NIST, CISA, MITRE, and FIRST.
4. Secondary research only when it adds material not available from a primary source.

Archived repositories, blog posts, and course notes may explain legacy behavior
but must be labeled accordingly. Version-sensitive commands should include the
relevant tool version or instruct readers to consult local `--help` output.

## Automated Controls

```bash
python scripts/validate_docs.py .
python scripts/content_health.py . --require-status
python -m unittest discover -s tests -v
npx markdownlint-cli2 "**/*.md"
```

The content-health check fails when a canonical guide has neither a verification
date nor an explicit review-pending label. External links are checked by CI, but
a working link alone is not evidence that a technical claim is correct.

---

[← Maintenance guide](./MAINTENANCE.md) · [← Main index](../README.md)
