# ⚡ NetExec - Network Service Assessment

> **Last verified:** 2026-09-17  
> **Checked against:** Current official NetExec installation and command documentation  
> **Scope:** Authorized networks and labs only

NetExec is the community-maintained continuation of CrackMapExec. New workflows should use `nxc`; keep `crackmapexec` or `cme` only when documenting a legacy environment.

## Install

```bash
sudo apt install pipx git
pipx ensurepath
pipx install git+https://github.com/Pennyw0rth/NetExec
nxc --version
```

Prefer the package supplied by your penetration-testing distribution when reproducible versioning is important.

## Target and Credential Forms

```bash
nxc smb 192.0.2.10
nxc smb 192.0.2.0/24
nxc smb targets.txt

nxc smb 192.0.2.10 -u TEST_USER -p 'TEST_PASSWORD'
nxc smb 192.0.2.10 -u TEST_USER -H 'NTLM_HASH'
nxc smb 192.0.2.10 -u localadmin -p 'TEST_PASSWORD' --local-auth
```

Use test accounts where possible. Password spraying can lock accounts and generate significant telemetry; confirm thresholds and written authorization first.

## SMB Enumeration

```bash
nxc smb 192.0.2.10 -u TEST_USER -p 'TEST_PASSWORD' --shares
nxc smb 192.0.2.10 -u TEST_USER -p 'TEST_PASSWORD' --users
nxc smb 192.0.2.10 -u TEST_USER -p 'TEST_PASSWORD' --groups
nxc smb 192.0.2.10 -u TEST_USER -p 'TEST_PASSWORD' --pass-pol
nxc smb 192.0.2.0/24 --gen-relay-list relay-targets.txt
```

The relay list is a validation input, not authorization to relay credentials. Confirm the engagement's allowed techniques before proceeding.

## LDAP and Active Directory

```bash
nxc ldap 192.0.2.10 -u TEST_USER -p 'TEST_PASSWORD' --users
nxc ldap 192.0.2.10 -u TEST_USER -p 'TEST_PASSWORD' --groups
nxc ldap 192.0.2.10 -u TEST_USER -p 'TEST_PASSWORD' --password-not-required
nxc ldap 192.0.2.10 -u TEST_USER -p 'TEST_PASSWORD' --admin-count
```

Check protocol-specific help before using a flag:

```bash
nxc smb --help
nxc ldap --help
nxc winrm --help
nxc mssql --help
```

## WinRM, SSH, and MSSQL

```bash
nxc winrm 192.0.2.10 -u TEST_USER -p 'TEST_PASSWORD'
nxc ssh 192.0.2.20 -u TEST_USER -p 'TEST_PASSWORD'
nxc mssql 192.0.2.30 -u TEST_USER -p 'TEST_PASSWORD'
```

A successful authentication is not the same as authorization to execute commands. Record the protocol, identity, privilege level, and exact target.

## Password-Spray Safety

```bash
# One approved password against an approved user list
nxc smb dc.example.test -u approved-users.txt -p 'APPROVED_TEST_PASSWORD' --continue-on-success
```

Before spraying:

- Obtain the password and account-lockout policy.
- Exclude service, break-glass, and sensitive accounts.
- Agree on rate, time window, stop conditions, and monitoring contacts.
- Stop immediately if lockouts or operational impact appear.

## Evidence

```bash
nxc smb targets.txt -u TEST_USER -p 'TEST_PASSWORD' --shares | tee nxc-shares.txt
```

Redact credentials and hashes. Preserve the NetExec version, timestamp, source address, target list, and relevant log identifiers.

## Migration from CrackMapExec

| Legacy | Current |
|---|---|
| `crackmapexec smb ...` | `nxc smb ...` |
| `cme ldap ...` | `nxc ldap ...` |
| CrackMapExec wiki | [NetExec Wiki](https://www.netexec.wiki/) |
| Archived source | [Maintained NetExec repository](https://github.com/Pennyw0rth/NetExec) |

Most common syntax is familiar, but modules and flags evolve. Validate every migrated command with the installed version's help.

## References

- [NetExec documentation](https://www.netexec.wiki/)
- [NetExec repository](https://github.com/Pennyw0rth/NetExec)
- [Legacy CrackMapExec archive](https://github.com/byt3bl33d3r/CrackMapExec)

---

**Related:** [AD Attack Methodology](../AD-Attack-Methodology/README.md) · [NTLM Relay](../AD-Attack-Methodology/NTLM-Relay-and-Coercion.md)
