# 🐧 LinPEAS Quick Reference

> **Last verified:** 2026-09-17  
> **Checked against:** Current official PEASS-ng release documentation  
> **Scope:** Authorized Linux hosts and labs only

LinPEAS enumerates Linux privilege-escalation paths. Its output is evidence to review, not proof that every highlighted item is exploitable.

## Obtain and Verify

Prefer transferring a pinned release from your assessment workstation. Record its SHA-256 before execution.

```bash
curl -fL https://github.com/peass-ng/PEASS-ng/releases/latest/download/linpeas.sh -o linpeas.sh
sha256sum linpeas.sh
chmod 700 linpeas.sh
```

Avoid piping remote content directly into a shell. Inspect the downloaded script and follow the engagement's tooling policy.

## Run

```bash
# Standard local enumeration
./linpeas.sh | tee linpeas-output.txt

# Preserve color codes when capturing output
./linpeas.sh -a | tee linpeas-output.txt
```

Review high-signal areas manually:

- Kernel, distribution, architecture, and patch level.
- `sudo` rules, SUID/SGID files, capabilities, and writable service files.
- Scheduled jobs, systemd units, PATH and library-loading weaknesses.
- Credentials in configuration, history, environment variables, backups, and mounts.
- Containers, sockets, groups, NFS exports, and network trust relationships.

## Cleanup

```bash
rm -f ./linpeas.sh ./linpeas-output.txt
```

Remove transferred tools and output only after preserving the evidence required by the engagement.

## References

- [PEASS-ng repository](https://github.com/peass-ng/PEASS-ng)
- [Linux Privilege Escalation guide](../Linux-PrivEsc/README.md)
