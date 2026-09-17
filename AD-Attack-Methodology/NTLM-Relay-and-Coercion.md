# 🔁 NTLM Relay and Authentication Coercion

> **Last verified:** 2026-09-17  
> **Checked against:** Current NetExec, Impacket, Responder, and Microsoft hardening documentation  
> **Scope:** Authorized labs and explicitly approved internal assessments only

Relay testing can trigger authentication, change directory objects, or affect services. Agree on target lists, prohibited systems, test accounts, rate limits, and stop conditions before starting listeners or coercion checks.

## Determine Relay Exposure

```bash
# Produce systems where SMB signing is not required
nxc smb 192.0.2.0/24 --gen-relay-list relay-targets.txt

# Confirm a single target manually
nxc smb 192.0.2.10
```

Also review LDAP signing and channel binding, EPA on HTTP endpoints, WebClient exposure, and outbound NTLM restrictions.

## Lab Relay Setup

Use dedicated lab identities and a reviewed target file:

```bash
ntlmrelayx.py -tf relay-targets.txt -smb2support
```

Do not combine relay with dumping, command execution, or directory modification unless those impacts are explicitly allowed. A captured authentication and successful protocol negotiation may be sufficient evidence.

## Coercion Validation

Authentication coercion abuses an RPC or application behavior to make a system authenticate to an analyst-controlled listener. Prefer discovery or scan modes first, and exclude domain controllers, certificate authorities, backups, clusters, and other sensitive systems unless specifically approved.

Record:

- Source and destination systems.
- Protocol and RPC method tested.
- Test identity and service context.
- Whether signing, channel binding, or EPA prevented relay.
- Relevant Windows and network events.

## Common Defensive Controls

- Require SMB signing where feasible.
- Require LDAP signing and enforce LDAP channel binding.
- Enable EPA for AD CS and other integrated-authentication web endpoints.
- Disable unnecessary NTLM and WebClient usage in stages.
- Restrict outbound SMB and administrative protocols between segments.
- Disable LLMNR and NetBIOS name resolution where operationally safe.
- Monitor machine-account authentication to unusual hosts and directory changes following NTLM logons.

## Cleanup

Stop listeners, remove test files and temporary directory changes, revoke or delete test certificates, and verify that no captured material remains outside the approved evidence store.

## References

- [NetExec](https://www.netexec.wiki/)
- [Impacket ntlmrelayx](https://github.com/fortra/impacket)
- [Microsoft LDAP signing guidance](https://learn.microsoft.com/en-us/troubleshoot/windows-server/active-directory/enable-ldap-signing-in-windows-server)
- [Microsoft NTLM reduction guidance](https://learn.microsoft.com/en-us/windows-server/security/kerberos/ntlm-overview)

---

[← AD Attack Methodology](./README.md)
