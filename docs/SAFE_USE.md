# Safe and Authorized Use

> **Last verified:** 2026-09-17  
> **Checked against:** NIST SP 800-115 and OWASP testing guidance

The repository is intended for labs, CTFs, education, and explicitly authorized
security assessments. A command appearing in a cheatsheet is not permission to
run it against a system.

## Before Testing

- Obtain written authorization and confirm the exact assets, identities, and dates.
- Record prohibited techniques, production restrictions, rate limits, and stop conditions.
- Confirm who can approve scope changes and who must be contacted during an incident.
- Use dedicated test accounts and data whenever possible.
- Read scripts, templates, and payloads before execution; never trust a copied command blindly.

## During Testing

- Start with passive or low-impact checks and increase impact only when justified.
- Keep target lists explicit. Avoid broad CIDRs, wildcard domains, and unbounded recursion.
- Set conservative concurrency, timeouts, and request rates.
- Stop at the minimum evidence needed to prove the issue.
- Do not retrieve unrelated data, persist access, disrupt availability, or pivot outside scope.
- Keep a timestamped action log and immediately report unexpected impact.

## Evidence and Secrets

- Encrypt client evidence at rest and in transit.
- Redact credentials, tokens, personal data, and unrelated records from reports.
- Never commit real targets, secrets, customer data, or proprietary output to this repository.
- Hash original evidence when integrity or chain of custody matters.
- Follow the agreed retention period, then securely delete the evidence.

## Cleanup

Track every state-changing action. Remove test accounts, keys, certificates,
files, jobs, services, payloads, firewall rules, and cloud resources. Revoke
tokens, restore modified configuration, and verify the final state with the
system owner.

## Reporting Content Problems

If a guide is unsafe, outdated, or technically incorrect, open a content-update
issue with the affected file, tested version, evidence, and a maintained source.
For vulnerabilities in the repository itself, follow [SECURITY.md](../SECURITY.md).

---

[← Main index](../README.md)
