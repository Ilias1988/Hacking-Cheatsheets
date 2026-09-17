# BloodHound Community Edition

> **Last verified:** 2026-09-17  
> **Checked against:** Official BloodHound CE quickstart and collector documentation  
> **Scope:** Authorized Active Directory and Microsoft Entra ID environments

BloodHound maps identity relationships and attack paths. The maintained default
is BloodHound Community Edition (CE), installed with BloodHound CLI. The old
Electron application is legacy and should be used only when a course or dataset
specifically requires it.

## Components

| Component | Purpose |
|---|---|
| BloodHound CE | Web application, API, analysis, and graph visualization |
| BloodHound CLI | Supported local CE installation and update workflow |
| SharpHound CE | Active Directory data collector |
| AzureHound CE | Microsoft Entra ID and Azure IaaS data collector |
| OpenHound | Supported and community SaaS/platform collection workflows |

Treat collector output as sensitive identity data. It can expose users, groups,
sessions, permissions, trust relationships, certificate services, and likely
attack paths.

## Install BloodHound CE

The official quickstart currently recommends BloodHound CLI, Docker Desktop or
Docker Engine, at least 8 GB RAM, four CPU cores, and 10 GB free disk space for
a small local deployment.

### Linux

Download the current CLI release from the official repository, verify the
release asset, and unpack it:

```bash
wget https://github.com/SpecterOps/bloodhound-cli/releases/latest/download/bloodhound-cli-linux-amd64.tar.gz
tar -xvzf bloodhound-cli-linux-amd64.tar.gz
./bloodhound-cli install
```

### Windows PowerShell

```powershell
curl.exe -L `
  -o "$env:USERPROFILE\Downloads\bloodhound-cli-windows-amd64.zip" `
  https://github.com/SpecterOps/bloodhound-cli/releases/latest/download/bloodhound-cli-windows-amd64.zip

Set-Location "$env:USERPROFILE\Downloads"
tar -xf bloodhound-cli-windows-amd64.zip
.\bloodhound-cli.exe install
```

Save the randomly generated initial password, browse to
`http://localhost:8080/ui/login`, sign in as `admin`, and change the password.
The default Compose configuration binds the application to localhost; do not
expose it or its databases without an explicit architecture and access review.

If the initial password is lost:

```bash
./bloodhound-cli resetpwd
```

Update an existing local CE deployment with:

```bash
./bloodhound-cli update
```

## Collect Active Directory Data

Download SharpHound CE from the BloodHound CE UI under **Download Collectors**
or from the official SharpHound releases. Run collection from a domain-joined
Windows host with an approved identity:

```powershell
.\SharpHound.exe
```

Collector flags change over time. Inspect the exact installed build before
choosing methods, output paths, throttling, or scope:

```powershell
.\SharpHound.exe --version
.\SharpHound.exe --help
```

Prefer the smallest collection set that answers the assessment question. Avoid
session-heavy or repeated collection across production unless it is explicitly
approved and coordinated with defenders.

## Collect Microsoft Entra ID Data

Use AzureHound CE for Entra ID and Azure IaaS. Download it through BloodHound CE
or the official AzureHound release page. Grant only the documented permissions
needed for the selected collection and use a dedicated assessment identity.

Do not place real passwords or client secrets in shell history or this
repository. Use the current AzureHound help and official data-permissions page:

```powershell
.\AzureHound.exe --help
```

## Ingest Data

SharpHound and AzureHound generate JSON/ZIP output for ingestion. In CE, open:

```text
Administration → Data Collection → File Ingest
```

Upload the collector output, wait for processing to finish, and review data
quality before drawing conclusions. Missing sessions or incomplete collection
can hide paths; stale collection can show paths that no longer exist.

## Analysis Workflow

```text
Confirm collection coverage
→ Identify owned or starting principals
→ Mark critical/high-value assets
→ Pathfind to the approved objective
→ Validate every edge against current permissions
→ Document the smallest realistic path
→ Recommend control changes and retest
```

Useful questions include:

- Which non-privileged principals can reach a privileged role?
- Which ACL, group, session, delegation, or certificate edge creates the path?
- Is an edge current, exploitable in this environment, and inside scope?
- Which single control change breaks the most paths with the least disruption?
- Are there cross-domain, hybrid, or Entra relationships that expand impact?

BloodHound identifies relationships and possible paths; it is not proof that
every edge can be exploited. Validate critical edges safely before reporting.

## Evidence and Cleanup

- Record BloodHound, CLI, and collector versions.
- Record collection time, domain/tenant, identity, methods, and exclusions.
- Export only the minimum graph views needed for the report.
- Redact usernames, hostnames, tenant identifiers, and unrelated relationships.
- Encrypt raw ZIP/JSON and database backups at rest.
- Remove collector output and local CE data according to the engagement's retention plan.

## Legacy Migration Notes

- `BloodHound-Legacy` is deprecated; do not use its install instructions as the default.
- Old `SharpHound.ps1`/`Invoke-BloodHound` examples are intentionally omitted.
- Old Cypher query examples may not match the current CE schema or UI.
- Revalidate imported legacy datasets and queries against the current data model.

## References

- [BloodHound CE quickstart](https://bloodhound.specterops.io/get-started/quickstart/community-edition-quickstart)
- [BloodHound documentation](https://bloodhound.specterops.io/)
- [BloodHound CE repository](https://github.com/SpecterOps/BloodHound)
- [BloodHound CLI releases](https://github.com/SpecterOps/bloodhound-cli/releases)
- [SharpHound repository](https://github.com/SpecterOps/SharpHound)
- [AzureHound repository](https://github.com/SpecterOps/AzureHound)
- [Deprecated BloodHound Legacy repository](https://github.com/SpecterOps/BloodHound-Legacy)

---

[← Main index](../README.md)
