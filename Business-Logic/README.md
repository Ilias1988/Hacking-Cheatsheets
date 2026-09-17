# 🧠 Business Logic and Workflow Testing

> **Last verified:** 2026-09-17  
> **Scope:** Authorized accounts, transactions, and test data only

Business-logic vulnerabilities violate the application's rules while using technically valid requests. Automation helps with repetition, but the core skill is modeling states, actors, invariants, and value movement.

## Build a Workflow Model

For each important journey, record:

- Actors, roles, tenants, and ownership boundaries.
- States and allowed transitions.
- Prices, limits, balances, inventory, quotas, approvals, and expirations.
- Server-generated identifiers and client-controlled values.
- Side effects such as email, payment, provisioning, refunds, or access grants.

## High-Value Test Cases

- Skip, repeat, reverse, or reorder workflow steps.
- Replay a one-time action or reuse an expired token.
- Change quantity, price, currency, discount, shipping, tax, or account identifiers.
- Perform the same action concurrently and inspect the final invariant.
- Cross tenant, role, account, or ownership boundaries.
- Trigger approval after modifying the underlying object.
- Combine individually low-risk features into an abusive chain.
- Compare web, mobile, API, legacy, and administrative implementations.
- Test whether server-side limits are enforced consistently across retries and channels.

## Race-Condition Method

1. Establish a deterministic baseline with one request.
2. Identify the narrow state transition and expected invariant.
3. Send a small synchronized batch using test data.
4. Stop when impact is proven; do not create operational or financial harm.
5. Record request count, timing method, responses, and final server state.

## Evidence and Reporting

Use a state diagram or numbered sequence. State the violated invariant plainly, for example: “a one-time credit can be redeemed twice.” Separate confirmed impact from hypothetical scaling and include reconciliation or cleanup actions.

## Remediation Themes

- Enforce state transitions and authorization on the server.
- Make sensitive operations idempotent.
- Use atomic transactions, uniqueness constraints, and appropriate locking.
- Recalculate prices, permissions, and eligibility from trusted data.
- Bind approvals and tokens to the exact object state and actor.
- Monitor abnormal velocity, retries, and cross-channel inconsistencies.

## References

- [PortSwigger Business Logic Vulnerabilities](https://portswigger.net/web-security/logic-flaws)
- [OWASP Web Security Testing Guide](https://owasp.org/www-project-web-security-testing-guide/)
- [CWE-840](https://cwe.mitre.org/data/definitions/840.html)

---

**Related:** [Race Conditions](../Race-Conditions/README.md) · [IDOR](../IDOR/README.md)
