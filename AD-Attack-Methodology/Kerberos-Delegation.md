# 🎟️ Kerberos Delegation Assessment

> **Last verified:** 2026-09-17  
> **Checked against:** Current Microsoft Kerberos, Impacket, Rubeus, and BloodHound CE documentation  
> **Scope:** Authorized Active Directory environments only

Delegation findings are about trust paths: which principal can obtain a service ticket, for which identity, and to which service. Do not treat every delegation setting as exploitable without validating prerequisites.

## Delegation Types

| Type | Key risk | High-signal evidence |
|---|---|---|
| Unconstrained | TGTs may be exposed to the delegated host | `TRUSTED_FOR_DELEGATION`, privileged sessions |
| Constrained | A service can delegate to configured SPNs | `msDS-AllowedToDelegateTo` |
| Resource-based constrained delegation | The target resource decides who may delegate | `msDS-AllowedToActOnBehalfOfOtherIdentity` |

## Discover with LDAP or BloodHound

```bash
nxc ldap 192.0.2.10 -u TEST_USER -p 'TEST_PASSWORD' --trusted-for-delegation
```

In BloodHound CE, review delegation-related edges together with session, local-admin, ownership, and ACL paths. Confirm results directly in LDAP before reporting.

## Constrained Delegation Validation

With approved credentials for a delegated service account:

```bash
getST.py \
  -spn 'cifs/fileserver.example.test' \
  -impersonate 'APPROVED_TEST_USER' \
  'example.test/service_account:TEST_PASSWORD'
```

Use the resulting credential cache only against the agreed service:

```bash
export KRB5CCNAME=APPROVED_TEST_USER.ccache
klist
smbclient.py -k -no-pass fileserver.example.test
```

## RBCD Review

Check who can create or control computer objects, modify the target computer object, or write the RBCD attribute. A complete path often requires multiple independent permissions; report each prerequisite.

## Defensive Review

- Keep privileged accounts in `Protected Users` where operationally appropriate.
- Mark sensitive accounts as not delegable.
- Remove unconstrained delegation and obsolete SPNs.
- Restrict who can join computers to the domain and control machine accounts.
- Monitor delegation attribute changes and unusual service-ticket requests.
- Use tiered administration and prevent privileged sessions on delegated hosts.

## Cleanup

- Delete test tickets and credential caches.
- Remove any test computer or attribute changes.
- Confirm replication and record the cleanup evidence.
- Never retain production Kerberos material in the repository or report attachments.

## References

- [Microsoft Kerberos constrained delegation overview](https://learn.microsoft.com/en-us/windows-server/security/kerberos/kerberos-constrained-delegation-overview)
- [Impacket](https://github.com/fortra/impacket)
- [BloodHound CE](https://github.com/SpecterOps/BloodHound)

---

[← AD Attack Methodology](./README.md)
