# SQLMap Advanced Capabilities

> **Last verified:** 2026-09-17
> **Checked against:** SQLMap `1.10.9.12#dev`, commit `fa96906f76301fc7932c9f7a0c5c5277234677a2`
> **Evidence:** Official CLI/source and maintained Usage documentation
> **Safety:** Lab-only or separately authorized test cases; not default workflow

SQLMap can move far beyond detection. These capabilities remain important for
CTFs, training, and some contracted assessments, but they can expose data,
alter the database or host, trigger endpoint defenses, or interrupt service.

Before using anything in this document, define the exact capability in the
rules of engagement, take recoverable snapshots or backups, establish
monitoring and stop conditions, and confirm who will validate cleanup.

## Risk Map

| Capability | Minimum classification | Typical side effects |
|---|---|---|
| Broad target collection | High | Out-of-scope traffic, form submission, account actions |
| Credential/hash enumeration | High | Sensitive credential material in output and logs |
| Bulk dumping | High | Privacy exposure, load, large evidence footprint |
| Custom SQL | High/destructive | Data modification or locking |
| Server file read | High | Exposure of secrets and configuration |
| Server file write/UDF | Destructive | Persistent artifacts, executable code |
| OS command/shell/takeover | Destructive | Host compromise and EDR/AV response |
| Registry modification | Destructive | Configuration change or instability |
| WAF transformation chains | High | Detection evasion and difficult attribution |

## Direct Database Connections

`-d` bypasses the web application and connects directly to a database service:

```bash
python sqlmap.py -d 'sqlite:////lab/data/test.db' --tables
```

Use direct connections only with dedicated lab credentials or an explicitly
approved database account. Avoid putting passwords directly on a multi-user
system's command line. Direct mode tests database permissions, not the original
web injection path, so report it separately.

## Bulk Targets, Logs, Crawling, and Forms

SQLMap can obtain many targets from `-m`, `-l`, `-g`, `--crawl`, or `--forms`.
This is operationally dangerous because a source list may contain unrelated
hosts, logout endpoints, destructive actions, or third-party resources.

For an authorized bulk lab:

```bash
python sqlmap.py -m lab-targets.txt \
  --scope='^http://127\.0\.0\.1:8080/' \
  --level=1 --risk=1 --threads=1 \
  --batch --time-limit=900
```

Review the final target list manually before execution. `--scope` is a second
control, not permission to trust unreviewed discovery.

## Credential and Privilege Enumeration

The following switches can expose sensitive authentication and authorization
information:

```bash
python sqlmap.py -r lab-request.txt -p id --users
python sqlmap.py -r lab-request.txt -p id --privileges
python sqlmap.py -r lab-request.txt -p id --roles
python sqlmap.py -r lab-request.txt -p id --passwords
```

`--passwords` may retrieve password hashes. In a professional assessment,
schema metadata or the existence of an accessible credential table is often
enough to demonstrate impact. If hashes are necessary, agree on storage,
redaction, cracking authorization, and deletion before retrieval.

## Bulk Data Retrieval

Useful controls include `-D`, `-T`, `-C`, `--where`, `--start`, `--stop`,
`--first`, and `--last`. Prefer a narrow query over `--dump-all`:

```bash
python sqlmap.py -r lab-request.txt -p id \
  -D labdb -T demo_records -C id,label \
  --dump --start=1 --stop=3 --where='is_demo = 1'
```

`--dump-all` is retained as a capability reference but should not be a routine
pentest command. It expands both database load and the volume of regulated data
that must be protected and deleted.

## Custom SQL

SQLMap supports `--sql-query`, `--sql-shell`, and `--sql-file`. Even a query
intended to be read-only may call a function, lock rows, or run under unexpected
permissions.

Safe lab example:

```bash
python sqlmap.py -r lab-request.txt -p id \
  --sql-query='SELECT CURRENT_USER'
```

Do not enter an interactive SQL shell on production merely for exploration.
Pre-review individual statements, use a disposable database snapshot, and
capture the exact query and result.

## Server File Read

`--file-read` asks the DBMS to read a file accessible to its operating-system
account:

```bash
python sqlmap.py -r lab-request.txt -p id \
  --file-read='/tmp/sqlmap-lab-proof.txt'
```

This confirms both injection and database-host file access. It may retrieve
secrets and can trigger monitoring. Use a harmless file created specifically
for the lab; do not default to password files, application secrets, cloud
credentials, or customer configuration.

## Server File Write and UDF Injection

`--file-write` with `--file-dest`, and `--udf-inject` with `--shared-lib`, can
create persistent server-side artifacts. They are destructive capabilities.

Lab-only syntax using a non-executable marker:

```bash
python sqlmap.py -r lab-request.txt -p id \
  --file-write='./lab-marker.txt' \
  --file-dest='/tmp/sqlmap-lab-marker.txt'
```

After the test, remove the marker through the authorized administration path,
not by assuming SQLMap can always reverse the write. Record its hash, location,
owner, timestamps, cleanup command, and post-cleanup verification.

Do not upload a webshell simply to prove write access. A harmless marker plus
path and permission evidence normally demonstrates the same control failure
with much less risk.

## OS Command and Takeover Capabilities

Current capabilities include:

- `--os-cmd` for a single operating-system command;
- `--os-shell` for an interactive shell;
- `--os-pwn` for an out-of-band takeover workflow;
- `--os-smbrelay` for an SMB relay workflow;
- `--os-bof` for applicable DBMS process-memory exploitation;
- `--priv-esc`, `--msf-path`, and `--tmp-path` as supporting controls.

These are destructive-capability tests. They may install UDFs, write temporary
files, start processes, open connections, trigger EDR, or destabilize the DBMS.
The guide intentionally does not provide a turnkey reverse-shell or persistence
payload.

For an isolated disposable lab, a single identity command can be sufficient:

```bash
python sqlmap.py -r lab-request.txt -p id --os-cmd='whoami'
```

Run it only after snapshotting the target and monitoring filesystem, process,
network, and database changes. Stop if SQLMap proposes a technique outside the
approved plan.

## Windows Registry Operations

SQLMap can read, add, or delete Registry values using `--reg-read`,
`--reg-add`, `--reg-del`, `--reg-key`, `--reg-value`, `--reg-data`, and
`--reg-type`.

Registry reads can expose sensitive system configuration. Registry additions
and deletions can alter startup, security policy, services, or application
behavior. Use a disposable Windows lab and export the affected key before any
write. Confirm restoration with an independent administrative tool.

## Tamper Scripts and WAF Behavior

`--list-tampers` shows the transformations bundled with the tested checkout;
`--tamper` applies one or more scripts. A tamper script changes payload syntax,
not authorization, and can introduce false negatives or unexpected DBMS
semantics.

Safe review sequence:

```bash
python sqlmap.py --list-tampers
python sqlmap.py -r lab-request.txt -p id --tamper=space2comment \
  --level=1 --risk=1 --threads=1
```

Use one transformation at a time in a controlled lab, capture the final HTTP
request, and confirm that the DBMS meaning is unchanged. Do not treat long
tamper chains, random agents, Tor, or proxy rotation as a generic recipe for
stealth or WAF bypass.

## Non-SQL Injection Modes

The current maintained interface includes `--nosql`, `--graphql`, `--ldap`,
`--xpath`, `--ssti`, `--xxe`, and `--hql`. These modes do not share the same
enumeration model as relational SQL injection. Use only one per run and validate
it against a purpose-built local lab for that technology.

## REST API

`sqlmapapi.py` can expose SQLMap tasks and results through HTTP. Treat it as a
control plane for an offensive tool:

- bind only to a trusted lab interface;
- place authentication and network controls in front of it;
- never expose it directly to the internet;
- keep task options and logs out of public CI artifacts;
- terminate the service and remove task data after the lab.

## Cleanup and Retest

`--cleanup` is relevant only when SQLMap created DBMS UDFs or tables. It is not a
universal rollback and it does not remove arbitrary files, commands, registry
changes, database writes, or evidence copies.

For every advanced test:

1. Inventory planned artifacts before execution.
2. Monitor and record actual artifacts during execution.
3. Use the system owner's administration path for cleanup.
4. Verify independently that files, functions, processes, connections, and
   configuration changes are gone.
5. Preserve only the minimum evidence required by the engagement.
6. Retest the remediation with the smallest safe proof.

## Official References

- [SQLMap Usage](https://github.com/sqlmapproject/sqlmap/wiki/Usage)
- [Techniques](https://github.com/sqlmapproject/sqlmap/wiki/Techniques)
- [REST API](https://github.com/sqlmapproject/sqlmap/wiki/REST-API)
- [License and authorization warning](https://github.com/sqlmapproject/sqlmap/wiki/License)

---

[← SQLMap guide](./README.md) · [Option reference](./OPTIONS.md) · [Lab plan](./LAB.md)
