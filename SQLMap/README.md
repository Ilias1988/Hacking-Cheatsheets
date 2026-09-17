# SQLMap — Controlled SQL Injection Validation

> **Last verified:** 2026-09-17
> **Tested snapshot:** `1.10.9.12#dev`, commit `fa96906f76301fc7932c9f7a0c5c5277234677a2`
> **Evidence:** Official source and wiki checked + CLI smoke-tested
> **Test method:** Official `master` checkout; `--version`, `-hh`, and 74 documented flags checked with Python 3.13
> **Lab status:** No target requests were sent; examples require validation in an isolated authorized lab

[SQLMap](https://github.com/sqlmapproject/sqlmap) automates the detection and
validation of database injection flaws. It can also perform destructive or
high-impact actions, so this guide deliberately focuses on controlled detection,
minimal enumeration, reproducible evidence, and data minimization.

Use it only against systems covered by explicit written authorization. Confirm
whether automated injection, authenticated testing, time-based payloads, data
retrieval, and state-changing requests are in scope before starting.

## Install and Verify

SQLMap is continuously developed. The maintainers recommend using the official
Git repository when a current development build is required:

```bash
git clone --depth 1 https://github.com/sqlmapproject/sqlmap.git
cd sqlmap
python sqlmap.py --version
python sqlmap.py -hh
```

An existing Git checkout can be updated with:

```bash
python sqlmap.py --update
```

Record both `--version` and the Git commit because development builds can change
without a new stable release tag.

## Start With a Low-Impact Baseline

The following example uses a fictional reserved domain and does not represent a
real target:

```bash
python sqlmap.py \
  -u 'https://example.test/item?id=1' \
  -p id \
  --level=1 --risk=1 \
  --threads=1 --delay=0.5 \
  --timeout=10 --retries=1 --time-limit=600 \
  --batch \
  --output-dir=sqlmap-output \
  -t sqlmap-traffic.txt \
  --har=sqlmap-traffic.har \
  --report-json=sqlmap-report.json
```

Important controls:

| Option | Purpose |
|---|---|
| `-p id` | Test only the named parameter |
| `--level=1` | Use the smallest default test set |
| `--risk=1` | Avoid heavier and potentially state-changing risk levels |
| `--threads=1` | Keep request concurrency predictable |
| `--delay=0.5` | Pause between requests |
| `--time-limit=600` | Bound the complete run |
| `-t` | Preserve textual HTTP traffic |
| `--har` | Preserve HTTP Archive evidence |
| `--report-json` | Write machine-readable run results |

`--batch` accepts SQLMap's default answers. It is useful for repeatability but
does not make a run safe. Review the chosen options and use `--answers` when a
specific prompt must have an explicit pre-approved answer.

## Prefer a Captured Raw Request

For authenticated or complex applications, export the exact request from an
intercepting proxy, replace the intended injection position if needed, and use:

```bash
python sqlmap.py -r request.txt \
  -p id \
  --force-ssl \
  --level=1 --risk=1 \
  --threads=1 --delay=0.5 \
  --batch
```

Before saving the request:

- remove unrelated cookies, tokens, and personal data;
- confirm that replaying it cannot purchase, delete, send, approve, or modify;
- use a dedicated test account with the minimum required privileges;
- store the request and output in the engagement evidence location, not Git.

## Define the Request Precisely

```bash
# POST body; -X is the HTTP method alias shown by current CLI help
python sqlmap.py -u https://example.test/search \
  -X POST --data='query=test&id=1' -p id

# Cookie parameter
python sqlmap.py -u https://example.test/profile \
  --cookie='session=REDACTED; preference=1' -p preference

# Custom headers and User-Agent; -A is the current short alias
python sqlmap.py -u 'https://example.test/item?id=1' \
  -H 'X-Assessment-ID: AUTHORIZED-TEST' \
  -A 'Authorized-Security-Assessment'
```

SQLMap also supports `--method` and `--user-agent`; the tested `-hh` output
currently presents the short aliases `-X` and `-A`, while the long aliases remain
defined in the official CLI parser.

An asterisk can identify a specific insertion point:

```bash
python sqlmap.py -u 'https://example.test/item/1*/details' \
  --level=1 --risk=1
```

Avoid marking headers or values whose mutation could affect routing,
authentication, or another tenant unless that exact test case is authorized.

## Limit Parameters and Scope

```bash
# Test selected parameters only
python sqlmap.py -r request.txt -p id,category

# Skip known state-changing or sensitive parameters
python sqlmap.py -r request.txt --skip=csrf_token,action

# Exclude parameter names using a regular expression
python sqlmap.py -r request.txt --param-exclude='(?i)token|session|logout'

# Restrict parameter locations
python sqlmap.py -r request.txt --param-filter=POST

# Skip values that do not appear dynamic
python sqlmap.py -r request.txt --skip-static

# Keep discovered URLs inside the approved host/path expression
python sqlmap.py -r request.txt --scope='^https://example\.test/app/'
```

`--scope` is a filtering aid, not authorization. Review every input list and
redirect destination independently.

## Detection Controls

### Level and risk

`--level` ranges from 1 to 5 and expands the tested payloads and parameter
locations. `--risk` ranges from 1 to 3. The official documentation warns that
risk 2 adds heavy time-based tests and risk 3 adds `OR`-based tests that can
change every row when injected into an `UPDATE` statement.

Begin with:

```bash
--level=1 --risk=1
```

Increase either value only for a documented test case after reviewing the
request semantics and database impact.

### Techniques

`--technique` accepts combinations of:

| Letter | Technique |
|---|---|
| `B` | Boolean-based blind |
| `E` | Error-based |
| `U` | UNION query-based |
| `S` | Stacked queries |
| `T` | Time-based blind |
| `Q` | Inline queries |

For a focused initial check:

```bash
python sqlmap.py -r request.txt -p id \
  --technique=BEU --level=1 --risk=1
```

Stacked queries and time-based tests can have greater operational impact. Do not
enable them merely to make a scan more exhaustive.

### Stable response comparison

Dynamic pages can cause false positives or negatives. Select a stable response
signal after manually comparing valid and invalid requests:

```bash
python sqlmap.py -r request.txt -p id --string='Item details'
python sqlmap.py -r request.txt -p id --not-string='No results'
python sqlmap.py -r request.txt -p id --regexp='Results: [1-9][0-9]*'
python sqlmap.py -r request.txt -p id --code=200
python sqlmap.py -r request.txt -p id --text-only
python sqlmap.py -r request.txt -p id --titles
```

Choose one signal supported by observed behavior. Do not stack filters until a
desired answer appears.

## Authentication and CSRF

```bash
# HTTP authentication
python sqlmap.py -u 'https://example.test/item?id=1' \
  --auth-type=Basic --auth-cred='testuser:REDACTED'

# CSRF token refresh
python sqlmap.py -r request.txt \
  --csrf-token=csrf_token \
  --csrf-url='https://example.test/form'
```

Use disposable test credentials where possible. Redact credentials from shell
history, screenshots, traffic files, and reports. Token refresh does not make a
state-changing request safe to replay.

## Proxying and Manual Observation

```bash
python sqlmap.py -r request.txt \
  --proxy=http://127.0.0.1:8080 \
  --proxy-cred='proxyuser:REDACTED'
```

An intercepting proxy helps correlate automated tests with application state,
but pausing requests in the proxy can change timing-based results. Keep the
proxy mode and modifications documented with the evidence.

## Minimal Enumeration After Confirmation

Only enumerate what is required to demonstrate impact:

```bash
# Low-volume identity and context checks
python sqlmap.py -r request.txt -p id --banner
python sqlmap.py -r request.txt -p id --current-user
python sqlmap.py -r request.txt -p id --current-db
python sqlmap.py -r request.txt -p id --is-dba

# Schema-level metadata
python sqlmap.py -r request.txt -p id --dbs
python sqlmap.py -r request.txt -p id -D appdb --tables
python sqlmap.py -r request.txt -p id -D appdb -T products --columns
python sqlmap.py -r request.txt -p id -D appdb --schema
python sqlmap.py -r request.txt -p id -D appdb -T products --count
```

If a data sample is explicitly authorized, minimize it with row boundaries and
a restrictive condition:

```bash
python sqlmap.py -r request.txt -p id \
  -D appdb -T products -C id,name \
  --dump --start=1 --stop=3 --where='is_test = 1'
```

Review the actual SQL semantics and applicable privacy rules first. A `WHERE`
condition supplied through SQLMap is not a substitute for contractual scope.

## Sessions, Reproducibility, and Local Cleanup

```bash
# Save or load explicit configuration
python sqlmap.py -r request.txt --save=assessment.conf
python sqlmap.py -c assessment.conf

# Use an explicit session file
python sqlmap.py -r request.txt -s sqlmap-session.sqlite

# Ignore cached query results for a controlled retest
python sqlmap.py -r request.txt --fresh-queries

# Remove cached results for this target before a clean retest
python sqlmap.py -r request.txt --flush-session

# Inspect existing session data without target requests
python sqlmap.py -r request.txt --offline
```

`--flush-session` can make a later run send many requests again. Preserve the
original evidence before clearing anything. SQLMap's `--purge` removes local
SQLMap data; it does not remediate or clean the tested application.

## Intentionally Excluded High-Impact Features

This guide does not provide workflows for:

- interactive SQL or operating-system shells;
- uploading, writing, or reading server files;
- DBMS user-defined-function installation;
- password-hash or bulk customer-data extraction;
- WAF-evasion chains or destructive statements;
- broad crawling or search-engine target collection.

Those capabilities can cause material system or data impact. If a contracted
engagement genuinely requires one, define it as a separate test case with
approval, backups, monitoring, stop conditions, and a cleanup/retest plan.

## Validate Before Reporting

Before calling a result confirmed:

1. Preserve the exact request, response indicator, version, commit, and command.
2. Reproduce with the smallest relevant option set.
3. Compare against a clean control request.
4. Rule out caching, load balancers, unstable content, and rate limiting.
5. Manually validate the parameter and application behavior when safe.
6. Stop after proving the agreed impact; do not collect unnecessary data.

## Troubleshooting

| Symptom | Safer diagnostic |
|---|---|
| Results vary between runs | Lower threads, add delay, compare raw responses, choose one stable marker |
| Parameter is not tested | Use `-p`, check `--param-filter`, and inspect the captured request |
| Target appears different through proxy | Record proxy behavior and compare an unmodified control request |
| Old result keeps returning | Preserve evidence, then use `--fresh-queries` or a deliberate `--flush-session` |
| Time-based checks are unreliable | Characterize latency first and avoid increasing risk automatically |
| Authenticated request logs out | Stop, refresh a test session, and verify no state-changing step was replayed |

## Verification Notes

The repository smoke test starts an ephemeral official Python 3.13 container,
clones the SQLMap official `master` branch, records the exact commit, runs only
`--version` and `-hh`, checks the flags used by this guide, and removes the
container image pulled by the test. No HTTP target is supplied.

## Official References

- [SQLMap official repository](https://github.com/sqlmapproject/sqlmap)
- [SQLMap Usage documentation](https://github.com/sqlmapproject/sqlmap/wiki/Usage)
- [SQL injection techniques](https://github.com/sqlmapproject/sqlmap/wiki/Techniques)
- [Download and update guidance](https://github.com/sqlmapproject/sqlmap/wiki/Download-and-update)
- [License and authorization warning](https://github.com/sqlmapproject/sqlmap/wiki/License)

---

[← Main index](../README.md) · [Tool testing policy](../docs/TOOL-TESTING.md) · [Safe use](../docs/SAFE_USE.md)
