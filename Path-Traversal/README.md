# 📁 Path Traversal Testing

> **Last verified:** 2026-09-17  
> **Scope:** Authorized applications; use benign, non-secret files for validation

Path traversal occurs when user-controlled input influences a filesystem path outside the intended root. The same root cause can affect reads, writes, deletes, archives, templates, imports, and cloud object keys.

## Identify Path Inputs

Look beyond obvious `file=` parameters:

- Download, preview, export, import, theme, template, and language endpoints.
- Filenames in multipart uploads and archive entries.
- API object keys, report paths, log viewers, and backup/restore features.
- Headers or cookies that select resources, tenants, or localization files.

## Safe Validation

Create two harmless files in an authorized lab: one inside the allowed directory and one just outside it. Demonstrate boundary escape using the marker file rather than operating-system secrets.

Test normalization differences involving:

- `../` and `..\` separators.
- URL encoding and repeated decoding.
- Absolute paths, drive letters, UNC paths, and leading separators.
- Mixed separators, repeated dots, Unicode, null handling, and framework-specific canonicalization.
- Archive extraction paths and symbolic links.

Do not retrieve private keys, credential stores, or unrelated customer data when a marker proves the issue.

## Remediation Pattern

1. Map user input to a server-side identifier rather than a path.
2. Resolve the final canonical path once.
3. Verify it remains beneath the intended canonical root.
4. Use safe filesystem APIs and a least-privileged service account.
5. Separate read, write, and delete authorization.
6. Reject archive entries that are absolute, escape the destination, or resolve through unsafe links.

## Reporting

Include the intended root, supplied input, resolved path, affected operation, tested identity, safe marker evidence, and platform-specific behavior.

## References

- [OWASP Path Traversal](https://owasp.org/www-community/attacks/Path_Traversal)
- [CWE-22](https://cwe.mitre.org/data/definitions/22.html)

---

[← Main index](../README.md)
