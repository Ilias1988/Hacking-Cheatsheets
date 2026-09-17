# SQLMap Lab Validation Plan

> **Last verified:** 2026-09-17
> **Tool snapshot:** SQLMap `1.10.9.12#dev`, commit `fa96906f76301fc7932c9f7a0c5c5277234677a2`
> **Lab status:** Planned; CLI smoke test passed, behavioral test not yet executed
> **Scope:** Disposable, isolated, intentionally vulnerable systems only

This document defines how SQLMap behavior should be tested before the guide is
labelled lab-validated. It deliberately does not claim that a help check proves
detection, exploitation, cleanup, or false-positive behavior.

## Isolation Requirements

Before starting:

- use a dedicated VM or container network with no route to work, home, VPN, or
  cloud-management networks;
- bind the vulnerable application to loopback when practical;
- use an intentionally vulnerable application and synthetic data only;
- pin the application, database, and container image versions or digests;
- take a recoverable target snapshot;
- disable reuse of real browser cookies, credentials, proxy history, and DNS;
- record host firewall rules and confirm the expected egress boundary.

Do not adapt this lab by replacing `127.0.0.1` with a public target.

## Environment Record

Complete this table for every run:

| Field | Recorded value |
|---|---|
| Date/time in UTC | Pending |
| Tester | Pending |
| SQLMap version and commit | `1.10.9.12#dev`, `fa96906f…` |
| Python version | 3.13 |
| Host OS | Pending |
| Vulnerable application/version | Pending |
| Database/version | Pending |
| Container/VM image digests | Pending |
| Network topology | Pending |
| Snapshot identifier | Pending |
| Evidence directory | Pending |

## Synthetic Dataset

Use a small table containing only invented records, for example:

| id | label | is_demo |
|---:|---|---:|
| 1 | alpha | 1 |
| 2 | beta | 1 |
| 3 | gamma | 1 |
| 4 | control | 0 |

Create a harmless file such as `/tmp/sqlmap-lab-proof.txt` containing a random
run identifier if file-read validation is approved. Do not place real hashes,
tokens, keys, or personal information in the lab.

## Test Case 1: Version and Help

```bash
python sqlmap.py --version
python sqlmap.py -hh
```

Expected:

- version matches the recorded checkout;
- documented options appear in advanced help;
- no target traffic is generated.

Status: CLI smoke-tested and passed.

## Test Case 2: Focused GET Detection

```bash
python sqlmap.py \
  -u 'http://127.0.0.1:8080/item?id=1' \
  -p id --level=1 --risk=1 \
  --threads=1 --delay=0.2 --time-limit=300 \
  --batch -t evidence/get-traffic.txt \
  --report-json=evidence/get-report.json
```

Record:

- request count and elapsed time;
- identified parameter, DBMS, and technique;
- whether the result is reproducible after a clean session;
- the matching manual control request.

Status: Pending.

## Test Case 3: Raw POST Request

Create `lab-request.txt` from the local application with a synthetic session and
a harmless POST action:

```http
POST /search HTTP/1.1
Host: 127.0.0.1:8080
Content-Type: application/x-www-form-urlencoded

query=test&id=1
```

Then run:

```bash
python sqlmap.py -r lab-request.txt -p id \
  --level=1 --risk=1 --threads=1 --delay=0.2 \
  --batch -t evidence/post-traffic.txt
```

Confirm that only `id` changes and that the application performs no write,
purchase, message, login, logout, or administrative action.

Status: Pending.

## Test Case 4: Dynamic-Page Comparison

Introduce a harmless dynamic value such as a timestamp or nonce, then compare:

```bash
python sqlmap.py -r lab-request.txt -p id --text-only
python sqlmap.py -r lab-request.txt -p id --string='Synthetic item'
python sqlmap.py -r lab-request.txt -p id --code=200
```

Expected:

- the documented comparison control reduces instability;
- a non-injectable control parameter remains negative;
- no filter is selected merely because it produces a desired result.

Status: Pending.

## Test Case 5: Minimal Enumeration

After detection is independently confirmed:

```bash
python sqlmap.py -r lab-request.txt -p id --current-db
python sqlmap.py -r lab-request.txt -p id -D labdb --tables
python sqlmap.py -r lab-request.txt -p id \
  -D labdb -T demo_records -C id,label \
  --dump --start=1 --stop=3 --where='is_demo = 1'
```

Expected:

- only the synthetic database/table/columns are accessed;
- no system databases or control row are retrieved;
- output files contain exactly the expected records.

Status: Pending.

## Test Case 6: Session and Offline Reuse

```bash
python sqlmap.py -r lab-request.txt -p id -s evidence/session.sqlite
python sqlmap.py -r lab-request.txt -p id \
  -s evidence/session.sqlite --offline
```

Capture traffic to confirm the offline run does not contact the target.

Status: Pending.

## Optional High-Impact Tests

File read, file write, UDF injection, custom SQL, registry operations, or OS
commands require separate approval even inside the lab. For each selected test:

1. Restore the clean snapshot.
2. Record expected artifacts and monitoring points.
3. Execute only one capability.
4. Stop after the minimum proof.
5. Clean up through the target's administrative path.
6. Restore again and confirm the artifact is absent.

Do not combine these features into a single “full takeover” run because that
makes side effects and cleanup difficult to attribute.

## Evidence Checklist

- version and commit output;
- command with secrets redacted;
- target and network topology;
- UTC timestamps;
- request/response traffic and JSON report;
- application, database, and system logs;
- snapshot identifiers;
- expected versus observed result;
- artifact inventory and cleanup proof;
- final pass/fail decision for each test case.

## Cleanup

At the end of the run:

1. Stop and remove the vulnerable application environment.
2. Remove test-only Docker images if they were pulled for this run.
3. Delete temporary SQLMap checkouts and unneeded session files.
4. Retain only approved, redacted evidence.
5. Restore or destroy the disposable target snapshot.
6. Verify there is no remaining listener, process, container, volume, route, or
   synthetic credential.

The repository should not claim “SQLMap lab-tested” until the environment table
and relevant test cases above contain observed results.

---

[← SQLMap guide](./README.md) · [Option reference](./OPTIONS.md) · [Advanced capabilities](./ADVANCED.md)
