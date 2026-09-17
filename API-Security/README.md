# API Security Testing

> **Last verified:** 2026-09-17  
> **Scope:** Authorized labs and contracted assessments only  
> **Primary references:** OWASP API Security Top 10 (2023), OWASP WSTG, GraphQL documentation

This guide favors repeatable authorization tests and controlled evidence over
large payload lists. Use two or more test identities whenever possible, stay
inside the agreed rate limits, and stop when the minimum proof is established.

## Testing Model

Create an identity and object matrix before testing:

| Actor | Tenant | Role | Own object | Other user's object | Admin function |
|---|---|---|---|---|---|
| Anonymous | — | None | — | Denied | Denied |
| User A | Alpha | User | Allowed | Denied | Denied |
| User B | Alpha | User | Allowed | Denied | Denied |
| User C | Beta | User | Allowed | Denied | Denied |
| Admin | Alpha | Admin | Allowed | Policy-dependent | Allowed |

For every request, record the endpoint, method, identity, tenant, object ID,
expected result, actual result, response status, and evidence reference.

## Discovery and Inventory

Collect endpoints from approved sources before fuzzing:

- OpenAPI/Swagger documents and exported API collections.
- Browser and mobile application traffic.
- JavaScript bundles and documented SDKs.
- Reverse-proxy routes, gateway configuration, and application logs.
- Versioned, beta, legacy, internal, and GraphQL endpoints.

```bash
# Save the specification before testing and review it locally.
curl --fail --silent --show-error \
  https://api.example.test/openapi.json \
  --output openapi.json

# Low-rate endpoint discovery in an explicitly approved lab.
ffuf -u https://api.example.test/FUZZ \
  -w /path/to/api-endpoints.txt \
  -rate 5 -mc all -fc 404
```

Do not treat a `200` response as proof that access was authorized. Compare the
returned fields, object owner, tenant, and server-side effects.

## OWASP API Security Top 10 (2023)

| ID | Risk | Practical question |
|---|---|---|
| API1 | Broken Object Level Authorization | Can one identity access another identity's object? |
| API2 | Broken Authentication | Are tokens, recovery, enrollment, and reauthentication enforced correctly? |
| API3 | Broken Object Property Level Authorization | Can hidden fields be read or written? |
| API4 | Unrestricted Resource Consumption | Are cost, concurrency, size, and rate bounded? |
| API5 | Broken Function Level Authorization | Can a lower role call privileged operations? |
| API6 | Unrestricted Access to Sensitive Business Flows | Can automation abuse a valuable workflow? |
| API7 | Server-Side Request Forgery | Can user-controlled URLs reach unintended destinations? |
| API8 | Security Misconfiguration | Are debug routes, permissive CORS, or verbose errors exposed? |
| API9 | Improper Inventory Management | Are undocumented or obsolete API versions reachable? |
| API10 | Unsafe Consumption of APIs | Is third-party data trusted without validation or isolation? |

## REST Authorization Workflow

Start with a valid request created by User A. Change only one variable at a
time and replay it as User B, a different tenant, a lower role, and anonymous.

```http
GET /api/v1/orders/ORDER_A HTTP/1.1
Host: api.example.test
Authorization: Bearer USER_A_TOKEN
```

Controlled BOLA/BOPLA checks:

1. Replace `ORDER_A` with a known test object owned by User B.
2. Repeat for read, update, delete, export, and nested-resource endpoints.
3. Test identifiers in the path, query, body, headers, and batch items.
4. Remove fields and add one documented privileged field at a time.
5. Verify server-side state directly; do not rely only on the response body.

Example mass-assignment comparison:

```json
{
  "displayName": "Test User",
  "role": "administrator"
}
```

The security question is whether the server ignores or rejects unauthorized
properties. A client-side-hidden field is not an authorization control.

## Function and Method Authorization

Test equivalent operations across routes and methods:

```text
GET    /api/v1/users/{id}
PATCH  /api/v1/users/{id}
DELETE /api/v1/users/{id}
POST   /api/v1/admin/users/{id}/disable
POST   /api/v1/users/{id}/export
```

Check alternate versions, content types, batch endpoints, background jobs, and
mobile-specific routes. Method override headers should be tested only when the
application or gateway supports them.

## Authentication and Session Checks

Review the complete lifecycle:

- Enrollment, verification, login, MFA, recovery, and credential changes.
- Access-token audience, issuer, signature, expiry, and scope.
- Refresh-token rotation, reuse detection, and revocation.
- Logout, password reset, role change, and account disablement.
- API keys in logs, URLs, client bundles, mobile packages, and error messages.
- Machine identities, webhook secrets, and service-to-service credentials.

For OAuth, OIDC, SAML, and JWT-specific workflows, use the
[Web Authentication guide](../Web-Authentication/README.md).

## GraphQL

Capture the schema from supplied documentation or authorized introspection,
then map each query and mutation to roles, tenants, and object ownership.

```graphql
query GetOrder($id: ID!) {
  order(id: $id) {
    id
    ownerId
    status
  }
}
```

Check:

- Resolver-level authorization, not only top-level route checks.
- Aliases and batches for inconsistent per-object enforcement.
- Mutations with hidden or privileged input properties.
- Field-level exposure of secrets, PII, and internal identifiers.
- Query depth, breadth, aliases, recursion, timeouts, and response-size limits.
- Error messages and field suggestions that reveal private schema details.

Use small, bounded queries for resource-limit checks. Do not run recursive or
high-cost queries against production unless the rules of engagement explicitly
permit resilience testing and monitoring is coordinated.

## Resource Consumption and Business Flows

Rate limits should account for identity, tenant, source, endpoint, and business
cost. Validate controls with a pre-agreed request budget rather than attempting
to exhaust a service.

High-value workflows often include:

- Registration, invitations, verification, and password recovery.
- Checkout, discounts, refunds, wallet transfers, and gift cards.
- Reservations, limited inventory, voting, and referral programs.
- Report generation, search, exports, uploads, and asynchronous jobs.
- SMS, email, AI inference, cloud provisioning, and other paid operations.

For each flow, test sequencing, replay, concurrency, idempotency, and state
transitions with dedicated test data. See [Business Logic Testing](../Business-Logic/README.md).

## SSRF and Webhooks

Map every feature that retrieves a URL: imports, previews, webhooks, PDF/image
rendering, feeds, redirects, and callback validation. Use an assessor-controlled
host and a unique token per test.

Verify scheme and redirect handling, DNS re-resolution, private/reserved
address blocking, egress allowlists, authentication forwarding, and response
exposure. Do not target cloud metadata or internal production services unless
that exact validation is explicitly authorized.

## CORS, Caching, and Gateways

- Test exact origins, credentialed requests, preflight behavior, and `Vary: Origin`.
- Compare edge and origin parsing of paths, methods, headers, and content types.
- Check whether authenticated responses can be cached and served cross-user.
- Review gateway transformations that add, remove, or trust identity headers.
- Confirm that rate limits and authorization apply consistently to every version.

## Safe Automation

```bash
# Keep targets explicit and concurrency conservative.
httpx -l approved-hosts.txt -threads 5 -rate-limit 5 \
  -status-code -title -tech-detect

# Run only reviewed Nuclei templates against approved targets.
nuclei -l approved-hosts.txt -t reviewed-templates/ \
  -rate-limit 5 -bulk-size 5 -concurrency 2
```

Review templates before execution. Exclude destructive, intrusive, fuzzing, and
denial-of-service checks unless separately approved.

## Finding Evidence

A strong API finding includes:

- Endpoint, method, API version, environment, time, and affected identity.
- Sanitized request and response pairs for the permitted and denied cases.
- The expected authorization or state-transition rule.
- Directly observed data or state change, with unnecessary sensitive data redacted.
- Preconditions, realistic impact, confidence, and cleanup performed.
- Server-side remediation and a concrete regression test.

## References

- [OWASP API Security Top 10](https://owasp.org/API-Security/)
- [OWASP Web Security Testing Guide](https://owasp.org/www-project-web-security-testing-guide/)
- [OWASP GraphQL Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/GraphQL_Cheat_Sheet.html)
- [GraphQL specification](https://spec.graphql.org/)
- [OAuth 2.0 Security Best Current Practice (RFC 9700)](https://www.rfc-editor.org/rfc/rfc9700)
- [OpenAPI specification](https://spec.openapis.org/oas/latest.html)

---

[← Main index](../README.md)
