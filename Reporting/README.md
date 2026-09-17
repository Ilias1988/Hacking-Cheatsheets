# 📝 Penetration Testing Reporting

> **Last verified:** 2026-09-17  
> **Scope:** Professional, authorized security assessments

A useful report lets an engineer reproduce and fix the issue, lets management understand risk, and makes clear what was and was not tested.

## Templates

| Deliverable | Template | Use |
|---|---|---|
| Full assessment | [Pentest Report](./Pentest-Report-Template.md) | Methodology, scope, findings, evidence, and remediation |
| Vulnerability submission | [Bug Bounty Report](./Bug-Bounty-Report-Template.md) | Concise, reproducible report for a coordinated disclosure program |
| Leadership brief | [Executive Summary](./Executive-Summary-Template.md) | Business impact, themes, and prioritized next steps |

## Minimum Finding Fields

- Stable finding identifier and descriptive title.
- Affected asset, endpoint, component, and tested account or role.
- CWE and relevant OWASP category.
- Severity rationale and CVSS vector when the engagement requires CVSS.
- Preconditions, reproducible steps, sanitized evidence, and observed result.
- Business impact tied to the assessed environment.
- Specific remediation, compensating controls, and a retest procedure.
- References to authoritative standards or vendor guidance.

## Evidence Handling

- Prefer text requests/responses over screenshots when secrets can be redacted safely.
- Preserve timestamps, tool versions, hashes, and original filenames.
- Redact tokens, passwords, cookies, personal data, and unrelated records.
- Keep raw evidence outside the Git repository and follow the engagement retention policy.
- Distinguish observed facts, analyst inference, and untested potential impact.

## Severity Quality Check

Severity is not the scanner's label. Consider exploitability, required privileges, user interaction, exposure, data sensitivity, blast radius, existing controls, and the client's threat model. Document why the rating is appropriate.

## References

- [CVSS v4.0 Specification](https://www.first.org/cvss/v4.0/specification-document)
- [MITRE CWE](https://cwe.mitre.org/)
- [OWASP Risk Rating Methodology](https://owasp.org/www-community/OWASP_Risk_Rating_Methodology)
- [NIST SP 800-115](https://csrc.nist.gov/pubs/sp/800/115/final)

---

[← Main index](../README.md)
