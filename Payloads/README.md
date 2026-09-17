# 💉 Web Payload Reference

> **Last verified:** 2026-09-17  
> **Scope:** Authorized labs and assessments only

This directory is a testing reference, not a copy-paste exploit pack. Start with a harmless proof of behavior, understand the parser and context, and increase impact only when the rules of engagement allow it.

## Index

| Topic | Guide | Primary validation goal |
|---|---|---|
| Cross-Site Scripting | [XSS](./XSS.md) | Controlled JavaScript execution in the intended context |
| SQL Injection | [SQLi](./SQLi.md) | Query influence without destructive data access |
| Local File Inclusion | [LFI](./LFI.md) | Confirm path control with a benign known file |
| Server-Side Template Injection | [SSTI](./SSTI.md) | Arithmetic or safe expression evaluation |
| Command Injection | [Command Injection](./Command-Injection.md) | Harmless timing or marker output |
| NoSQL Injection | [NoSQL Injection](./NoSQL-Injection.md) | Query/operator manipulation |
| Deserialization | [Deserialization](./Deserialization.md) | Type or object-graph control in an isolated lab |
| WebSocket Attacks | [WebSocket Attacks](./WebSocket-Attacks.md) | Authorization, origin, and message validation |
| GraphQL | [GraphQL Injection](./GraphQL-Injection.md) | Schema exposure, authorization, and resolver input handling |

## Safe Testing Pattern

1. Confirm scope, target, accounts, and prohibited actions.
2. Capture a normal request and identify the exact input context.
3. Use a non-destructive marker or delay.
4. Save the raw request, response, timestamp, and affected identity.
5. Stop after demonstrating the agreed business impact.
6. Provide remediation and a regression test.

## Placeholders

Examples use reserved or clearly synthetic values:

- `https://target.example`
- `192.0.2.0/24`, `198.51.100.0/24`, and `203.0.113.0/24`
- `TEST_USER`, `TEST_TOKEN`, and `LAB_ONLY`

Never paste production tokens, session identifiers, customer data, or private URLs into an issue or report template.

## Core References

- [OWASP Web Security Testing Guide](https://owasp.org/www-project-web-security-testing-guide/)
- [OWASP Cheat Sheet Series](https://cheatsheetseries.owasp.org/)
- [PortSwigger Web Security Academy](https://portswigger.net/web-security)

---

[← Main index](../README.md)
