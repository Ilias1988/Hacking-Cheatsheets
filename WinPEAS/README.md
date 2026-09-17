# 🪟 WinPEAS Quick Reference

> **Last verified:** 2026-09-17  
> **Checked against:** Current official PEASS-ng release documentation  
> **Scope:** Authorized Windows hosts and labs only

WinPEAS enumerates Windows privilege-escalation paths. Validate every result manually and account for endpoint-protection policy before transferring or executing assessment tools.

## Obtain and Verify

Download the appropriate release asset on the assessment workstation, pin the release when reproducibility matters, and record its hash.

```powershell
Get-FileHash .\winPEASx64.exe -Algorithm SHA256
Get-AuthenticodeSignature .\winPEASx64.exe
```

Do not disable security controls merely to run a tool unless that action is explicitly authorized and documented.

## Run

```cmd
winPEASx64.exe
winPEASx64.exe systeminfo userinfo servicesinfo applicationsinfo
winPEASx64.exe log=winpeas-output.txt
```

Manually validate findings involving:

- Service permissions and unquoted service paths.
- Scheduled tasks, autoruns, installers, and writable program directories.
- Token privileges, local groups, sessions, and credential material.
- AlwaysInstallElevated, UAC configuration, and endpoint policy.
- Registry ACLs, saved credentials, shares, and sensitive files.

## Cleanup

```cmd
del winPEASx64.exe
del winpeas-output.txt
```

Preserve required evidence before cleanup and report any control changes made during testing.

## References

- [PEASS-ng repository](https://github.com/peass-ng/PEASS-ng)
- [Windows Privilege Escalation guide](../Windows-PrivEsc/README.md)
