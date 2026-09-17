# 🔐 Web Authentication and Federation Testing

> **Last verified:** 2026-09-17  
> **Checked against:** Current OAuth, OpenID Connect, SAML, WebAuthn, and OWASP guidance  
> **Scope:** Authorized applications and identity tenants only

Modern authentication testing follows the complete trust boundary: browser, application, identity provider, redirect endpoint, token validation, session creation, recovery, and account linking.

## Test Map

| Area | Questions |
|---|---|
| Enrollment | Can users register or link an attacker-controlled identity to another account? |
| Authentication | Are MFA, passkeys, device trust, and risk controls applied consistently? |
| Recovery | Can reset or recovery bypass stronger authentication? |
| OAuth/OIDC | Are redirect URIs, `state`, `nonce`, PKCE, issuer, and audience validated? |
| SAML | Are signature, destination, audience, time, and assertion replay protections enforced? |
| JWT | Are algorithm, key, claims, token type, and key-source trust pinned correctly? |
| Session | Are cookies protected, rotated, invalidated, and scoped correctly? |
| Authorization | Does every API enforce tenant, object, role, and ownership checks after login? |

## OAuth 2.0 and OpenID Connect

Capture the full authorization request and callback. Verify:

- Exact allowlisting of redirect URIs; no wildcard, parser differential, or open-redirect chain.
- Unpredictable, session-bound `state` and `nonce` values.
- Authorization Code flow with PKCE, using `S256` for public clients.
- Server-side validation of issuer, audience, signature, expiry, and authorized party.
- One-time code use and rejection of code substitution between clients or tenants.
- Minimal scopes and clear consent; no implicit privilege increase during account linking.

Use separate test identities for victim and attacker roles. A safe proof demonstrates incorrect binding without accessing unrelated user data.

## SAML

Check that the service provider validates:

- The response and/or assertion signature against the configured IdP certificate.
- `AudienceRestriction`, `Recipient`, `Destination`, `InResponseTo`, and time conditions.
- Assertion uniqueness and replay prevention.
- The expected identity provider, tenant, NameID format, and attribute mapping.
- Authorization independently of user-controlled attributes.

Do not rely on visually inspecting XML. Test the exact parser and library behavior in the target.

## JWT Validation

```text
Header: algorithm and key identifier
Payload: issuer, audience, subject, tenant, scopes/roles, time claims
Signature: trusted algorithm and pinned key source
Context: correct token type for the endpoint
```

Reject unsigned tokens, unexpected algorithms, attacker-controlled key URLs, weak symmetric secrets, missing audiences, and access/ID-token confusion.

## Sessions and Logout

- Cookies use `Secure`, `HttpOnly`, and an appropriate `SameSite` value.
- Session identifiers rotate after login, MFA, privilege changes, and recovery.
- Logout and password reset invalidate relevant server-side sessions and refresh tokens.
- Concurrent-session and remembered-device behavior matches policy.
- Sensitive operations require recent or stronger authentication.

## Reporting

Include a sequence diagram or numbered flow, the two identities involved, sanitized tokens or claim excerpts, the trust decision that failed, and the smallest effective remediation.

## References

- [OAuth 2.0 Security Best Current Practice](https://www.rfc-editor.org/rfc/rfc9700)
- [OpenID Connect Core](https://openid.net/specs/openid-connect-core-1_0.html)
- [OWASP OAuth 2.0 Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/OAuth2_Cheat_Sheet.html)
- [OWASP SAML Security Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/SAML_Security_Cheat_Sheet.html)
- [OWASP Session Management Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Session_Management_Cheat_Sheet.html)

---

[← Main index](../README.md)
