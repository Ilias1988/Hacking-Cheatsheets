# 📤 File Upload Security Testing

> **Last verified:** 2026-09-17  
> **Scope:** Authorized applications and non-destructive test files only

File-upload testing covers validation, storage, retrieval, processing, authorization, and downstream consumers. Use harmless markers instead of executable payloads whenever possible.

## Workflow

1. Map accepted extensions, MIME types, size limits, image/document processing, and storage URLs.
2. Upload a uniquely named benign file and record the complete request and response.
3. Test validation layers independently: filename, extension, content type, magic bytes, parser, and post-processing.
4. Determine whether files are public, guessable, overwritten, transformed, or served with active content types.
5. Test authorization for create, read, replace, and delete operations using separate accounts.

## Safe Test Files

```text
upload-test-<random>.txt
Content: UPLOAD_VALIDATION_MARKER_<random>
```

For image processing, create a valid small image with inert metadata. For archive handling, use tiny archives with controlled filenames and no executable content.

## Test Cases

- Multiple extensions, mixed case, trailing dots/spaces, Unicode normalization, and reserved names.
- Mismatch between extension, `Content-Type`, magic bytes, and actual parser behavior.
- Server-side renaming, collision handling, and overwrite protection.
- Path separators or archive entries attempting to escape the intended directory.
- SVG/HTML/XML files served inline rather than as downloads.
- Image, PDF, office, media, and archive processors with malformed but non-destructive inputs.
- Direct-object authorization on download, preview, replace, and delete endpoints.
- Antivirus or content-disarm behavior, including fail-open timeouts.
- Storage-bucket policy, cache behavior, retention, and signed-URL expiry.

## Impact Questions

- Can uploaded content execute in the application or a trusted origin?
- Can one user read or replace another user's file?
- Can processing reach internal services, local files, or excessive resources?
- Can active content be used for stored XSS, phishing, or content-type confusion?
- Can the upload overwrite application or configuration files?

## Remediation

- Use allowlists and verify content with a hardened parser.
- Generate server-side object names and store outside executable/web roots.
- Serve user content from a separate origin with safe content disposition and type.
- Re-encode supported media and reject parser errors.
- Enforce authorization on every file operation.
- Set decompression, file-count, size, time, and recursion limits.

## References

- [OWASP File Upload Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/File_Upload_Cheat_Sheet.html)
- [OWASP Web Security Testing Guide](https://owasp.org/www-project-web-security-testing-guide/)

---

[← Main index](../README.md)
