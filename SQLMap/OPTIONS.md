# SQLMap Option Reference

> **Last verified:** 2026-09-17
> **Checked against:** SQLMap `1.10.9.12#dev`, commit `fa96906f76301fc7932c9f7a0c5c5277234677a2`
> **Evidence:** Official `-hh` output and maintained Usage documentation
> **Purpose:** Discoverability; read the risk notes before building a command

This is a categorized practical map of SQLMap's current interface. It
summarizes option behavior rather than reproducing the upstream help text.
Always treat the local `python sqlmap.py -hh` output as authoritative for the
exact checkout in use.

Risk labels used here:

- **Routine:** normally local or request-shaping behavior, still scope-dependent.
- **Elevated:** can broaden traffic, authentication use, or information access.
- **High:** can retrieve sensitive data or interact directly with a DBMS.
- **Destructive:** can change the database, filesystem, registry, or operating
  system state.

## Help and Diagnostics

| Option | Summary | Risk |
|---|---|---|
| `-h`, `--help` | Basic help | Routine |
| `-hh` | Advanced help | Routine |
| `--version` | Version string | Routine |
| `-v 0..6` | Console verbosity | Routine |
| `--dependencies` | Check optional dependencies | Routine |
| `--disable-coloring` | Plain console output | Routine |
| `--list-tampers` | List bundled tamper modules | Elevated |

## Target Sources

| Option | Summary | Risk |
|---|---|---|
| `-u`, `--url` | One target URL | Elevated |
| `-r` | Raw HTTP request file | Elevated |
| `-l` | Parse proxy log targets | Elevated |
| `-m` | Read targets from a text file | High: easy scope expansion |
| `-g` | Use search results as targets | High: scope is difficult to guarantee |
| `-d` | Connect directly to a DBMS | High |
| `-c` | Load an INI configuration | Depends on stored options |
| `--openapi` | Derive targets from an OpenAPI/Swagger file or URL | High: review generated operations |
| `--openapi-base` | Supply a base URL for a host-less OpenAPI definition | High: defines target scope |
| `--openapi-tags` | Restrict OpenAPI operations by tag | Safety control, still review operations |

Review every target source before execution. A file, proxy log, search result,
redirect, or configuration can silently expand the intended scope.

## HTTP Request Construction

| Option | Summary | Risk |
|---|---|---|
| `-X`, `--method` | Force the HTTP method | Elevated for state-changing verbs |
| `--data` | Request body | Elevated |
| `--param-del` | Parameter delimiter | Routine |
| `--cookie` | Cookie header | Elevated: may contain live sessions |
| `--cookie-del` | Cookie delimiter | Routine |
| `--live-cookies` | Reload current cookies from a file before requests | High: sensitive live sessions |
| `--load-cookies` | Load cookies from a file | Elevated |
| `--drop-set-cookie` | Ignore response cookies | Routine |
| `-A`, `--user-agent` | Set User-Agent | Routine |
| `--http1.0` | Force legacy HTTP/1.0 | Elevated: changes request behavior |
| `--http2` | Use experimental HTTP/2 support | Elevated: validate compatibility |
| `--mobile` | Use a mobile User-Agent profile | Elevated: changes request fingerprint |
| `--random-agent` | Select a bundled User-Agent | Elevated: reduces attribution clarity |
| `--host` | Override Host header | Elevated: can change virtual-host scope |
| `--referer` | Set Referer | Routine |
| `-H`, `--header` | Add one header | Depends on header |
| `--headers` | Add multiple headers | Depends on headers |
| `--force-ssl` | Force HTTPS | Routine |
| `--chunked` | Chunked request body | Elevated |
| `--hpp` | HTTP parameter pollution | Elevated |
| `--eval` | Run Python before requests | High: executes local code |

The tested help abbreviates the long aliases for `-X`, `-A`, and `-H` because
of its formatter. `--method`, `--user-agent`, and `--header` were therefore
confirmed against the official parser and maintained Usage page rather than
counted among the 243 flags extracted from `-hh`.

## Authentication and Session Handling

| Option | Summary | Risk |
|---|---|---|
| `--auth-type` | HTTP auth type | Elevated |
| `--auth-cred` | HTTP credentials | High: sensitive input |
| `--auth-file` | PEM authentication file | High: sensitive key material |
| `--csrf-token` | Identify CSRF token parameter | Elevated |
| `--csrf-url` | URL used to refresh the token | Elevated |
| `--csrf-method` | HTTP method for token retrieval | Elevated |
| `--csrf-data` | Body for token retrieval | Elevated |
| `--csrf-retries` | Token refresh retries | Elevated |

Use dedicated low-privilege test accounts. Keep secrets out of shell history,
configuration files committed to Git, screenshots, and reports.

## Connection, Proxy, and Transport

| Option | Summary | Risk |
|---|---|---|
| `--proxy` | Route requests through a proxy | Elevated |
| `--proxy-cred` | Proxy credentials | High: sensitive input |
| `--proxy-file` | Rotate proxy entries | High: attribution and routing complexity |
| `--proxy-freq` | Requests sent before rotating a proxy list | High: attribution complexity |
| `--ignore-proxy` | Ignore system proxy settings | Elevated |
| `--tor` | Use Tor | High: authorization and attribution concerns |
| `--tor-port` | Tor proxy port | High |
| `--tor-type` | Tor proxy type | High |
| `--check-tor` | Validate Tor use | Elevated |
| `--delay` | Delay between requests | Routine safety control |
| `--timeout` | Connection timeout | Routine |
| `--retries` | Retry count | Routine; increases traffic |
| `--retry-on` | Retry when response content matches a regex | Elevated; increases traffic |
| `--randomize` | Randomize a parameter each request | Elevated |
| `--abort-code` | Stop on selected HTTP error codes | Routine safety control |
| `--ignore-code` | Ignore selected HTTP error codes | Elevated |
| `--ignore-redirects` | Do not follow redirects | Routine |
| `--ignore-timeouts` | Continue after timeouts | Elevated |
| `--skip-urlencode` | Avoid payload URL encoding | Elevated |
| `--skip-xmlencode` | Avoid safe payload encoding for SOAP/XML | Elevated |

Persistent connections are used by default in the tested version. Current
documentation exposes `--no-keep-alive` to opt out rather than requiring the
old `--keep-alive` behavior.

## Safety and Stability Controls

| Option | Summary | Risk |
|---|---|---|
| `--threads` | Concurrent requests | Elevated as value increases |
| `--time-limit` | Total runtime limit | Routine safety control |
| `--safe-url` | Periodically visit a safe URL | Elevated: still sends requests |
| `--safe-post` | Body for safe URL | Elevated |
| `--safe-req` | Safe request loaded from a file | Elevated |
| `--safe-freq` | Frequency for safe requests | Elevated |
| `--unstable` | Adjust behavior for unstable links | Elevated |
| `--batch` | Accept default answers | Elevated: review defaults first |
| `--answers` | Predefine prompt answers | Depends on answers |

## Optimization

| Option | Summary | Risk |
|---|---|---|
| `-o` | Enable the maintained optimization bundle | Elevated |
| `--no-keep-alive` | Disable persistent connections | Routine |
| `--null-connection` | Infer length without full bodies when compatible | Elevated |
| `--threads` | Parallelize compatible retrieval | Elevated |

Optimization changes traffic shape and can reduce repeatability. Record it with
the command and evidence.

## Parameter Selection and Injection Shaping

| Option | Summary | Risk |
|---|---|---|
| `-p` | Explicit test parameters | Routine safety control |
| `--skip` | Skip named parameters | Routine safety control |
| `--skip-static` | Skip apparently static values | Routine |
| `--param-exclude` | Exclude parameter names by regex | Routine |
| `--param-filter` | Restrict parameter locations | Routine |
| `--dbms` | Force DBMS type | Elevated: may create false assumptions |
| `--dbms-cred` | DBMS authentication credentials | High |
| `--esperanto` | Use the DBMS-agnostic enumeration engine | Elevated/experimental |
| `--os` | Force backend operating system | Elevated |
| `--invalid-bignum` | Use large numbers for invalidation | Elevated |
| `--invalid-logical` | Use logical operations for invalidation | Elevated |
| `--invalid-string` | Use random strings for invalidation | Elevated |
| `--no-cast` | Disable payload casting | Elevated |
| `--no-escape` | Disable string escaping | Elevated |
| `--prefix` | Injection payload prefix | High |
| `--suffix` | Injection payload suffix | High |
| `--tamper` | Apply payload transformation scripts | High |
| `--proof` | Prove exploitation of detected injection points | High; review generated proof |

## Detection

| Option | Summary | Risk |
|---|---|---|
| `--level 1..5` | Expand tests and parameter locations | Elevated as value increases |
| `--risk 1..3` | Add heavier or state-changing payload classes | High above 1 |
| `--string` | Match a true-response marker | Routine |
| `--not-string` | Match an absent marker | Routine |
| `--regexp` | Match response content by regex | Routine |
| `--code` | Match an HTTP status | Routine |
| `--lengths` | Compare responses only by content length | Routine |
| `--smart` | Continue extensive tests only after heuristic signal | Routine |
| `--text-only` | Compare textual content | Routine |
| `--titles` | Compare HTML titles | Routine |

## Techniques

| Option | Summary | Risk |
|---|---|---|
| `--technique` | Choose `B`, `E`, `U`, `S`, `T`, or `Q` techniques | Depends on selection |
| `--time-sec` | Time-based delay threshold | Elevated |
| `--disable-stats` | Disable the time-delay statistical model | Elevated |
| `--timeless` | Use experimental HTTP/2 timeless timing | High; technique-specific |
| `--union-cols` | UNION column range | Elevated |
| `--union-char` | UNION filler character | Elevated |
| `--union-from` | UNION `FROM` table | High |
| `--union-values` | UNION column values | High |
| `--dns-domain` | DNS exfiltration domain | High |
| `--second-url` | Retrieve a second URL for second-order checks | Elevated |
| `--second-req` | Use a second request for second-order checks | Elevated |

## Fingerprinting

| Option | Summary | Risk |
|---|---|---|
| `-f`, `--fingerprint` | Perform extensive DBMS fingerprinting | Elevated |

Fingerprint results are estimates until corroborated with application or
database evidence.

## Enumeration and Data Retrieval

| Option | Summary | Risk |
|---|---|---|
| `-a`, `--all` | Enable all enumeration switches | High; avoid as a default |
| `-b`, `--banner` | DBMS banner | Elevated |
| `--current-user` | Current DBMS user | Elevated |
| `--current-db` | Current database | Elevated |
| `--hostname` | DBMS hostname | Elevated |
| `--is-dba` | Check DBA status | Elevated |
| `--users` | Enumerate DBMS users | High |
| `--passwords` | Enumerate password hashes | High: credential material |
| `--privileges` | Enumerate privileges | High |
| `--roles` | Enumerate roles | High |
| `--dbs` | Enumerate databases | Elevated |
| `--tables` | Enumerate tables | Elevated |
| `--columns` | Enumerate columns | Elevated |
| `--schema` | Enumerate schema | Elevated |
| `--count` | Count table rows | Elevated |
| `--dump` | Retrieve table data | High |
| `--dump-all` | Retrieve all database data | High; rarely justifiable |
| `--search` | Search database object names | High |
| `--comments` | Retrieve DBMS object comments | Elevated |
| `--statements` | Retrieve active SQL statements | High: may expose other users' data |
| `--procs` | Retrieve stored procedures/functions and their source | High |
| `--exclude` | Exclude named database identifiers from enumeration | Data-minimization control |
| `--exclude-sysdbs` | Exclude system databases | Safety control |
| `-D`, `-T`, `-C`, `-U` | Select database, table, columns, or user | Scope controls |
| `--pivot-column` | Select pivot column | Elevated |
| `--where` | Restrict dumped rows | High; verify SQL semantics |
| `--start`, `--stop` | Bound retrieved rows | Data-minimization controls |
| `--first`, `--last` | Bound retrieved characters | Data-minimization controls |
| `--sql-query` | Execute a SQL statement | High or destructive |
| `--sql-shell` | Interactive SQL execution | Destructive potential |
| `--sql-file` | Execute SQL from files | Destructive potential |

## Brute-Force Metadata Checks

| Option | Summary | Risk |
|---|---|---|
| `--common-tables` | Probe common table names | Elevated traffic |
| `--common-columns` | Probe common column names | Elevated traffic |
| `--common-files` | Probe common files | High |

These are not password brute force, but they can generate substantial blind
query traffic.

## Filesystem, UDF, OS, and Registry

| Option family | Capabilities | Risk |
|---|---|---|
| `--file-read` | Read a DBMS-host file | High |
| `--file-write`, `--file-dest` | Write a local file to the server | Destructive |
| `--udf-inject`, `--shared-lib` | Inject a shared library/UDF | Destructive |
| `--os-cmd`, `--os-shell` | Execute OS commands | Destructive |
| `--os-pwn`, `--os-smbrelay`, `--os-bof` | Takeover workflows | Destructive |
| `--priv-esc` | Attempt privilege escalation | Destructive |
| `--msf-path`, `--tmp-path` | Configure takeover dependencies | Destructive |
| `--reg-read`, `--reg-add`, `--reg-del` | Windows Registry operations | High/destructive |
| `--reg-key`, `--reg-value`, `--reg-data`, `--reg-type` | Registry target/value controls | High/destructive |

See [Advanced capabilities](./ADVANCED.md) before considering any option in
this group.

## General Operation and Evidence

| Option | Summary | Risk |
|---|---|---|
| `-s` | Explicit session file | Routine; may contain sensitive data |
| `-t` | Text HTTP traffic log | High-sensitivity evidence |
| `--output-dir` | Output directory | Routine |
| `--har` | HTTP Archive output | High-sensitivity evidence |
| `--report-json` | JSON report | High-sensitivity evidence |
| `--abort-on-empty` | Stop data retrieval when results are empty | Routine safety control |
| `--base64`, `--base64-safe` | Decode selected Base64 parameters and choose alphabet | Elevated |
| `--binary-fields` | Identify result fields containing binary values | Elevated |
| `--check-internet` | Check internet connectivity before testing | Elevated: external request |
| `--csv-del` | Select CSV output delimiter | Routine |
| `--charset` | Restrict the blind-retrieval character set | Elevated |
| `--dump-file` | Write dumped data to a custom file | High-sensitivity evidence |
| `--dump-format` | Select CSV, HTML, SQLite, or JSONL output | High-sensitivity evidence |
| `--encoding` | Force character encoding for retrieved data | Elevated |
| `--eta` | Display estimated retrieval time | Routine |
| `--gpage` | Start Google-dork results at a selected page | High: scope expansion |
| `--hex` | Use DBMS hex conversion for retrieval | Elevated |
| `--flush-session` | Clear cached target session | Elevated: causes retesting |
| `--fresh-queries` | Ignore cached query results | Elevated traffic |
| `--offline` | Use only session data | Routine |
| `--save` | Save current options to INI | May store secrets |
| `--scope` | Regex-filter discovered targets | Safety control, not authorization |
| `--crawl`, `--crawl-exclude` | Discover linked targets | High: scope expansion |
| `--forms` | Parse and test forms | High: may submit state-changing forms |
| `--parse-errors` | Display DBMS errors | Elevated |
| `--preprocess` | Execute local request-processing code | High |
| `--postprocess` | Execute local response-processing code | High |
| `--test-filter`, `--test-skip` | Include/exclude payload tests | Elevated |
| `--skip-heuristics`, `--skip-waf` | Disable heuristic checks | Elevated |
| `--repair` | Retry unknown dump characters | High: additional data requests |
| `--table-prefix` | Prefix SQLMap temporary database tables | Destructive potential |
| `--unsafe-naming` | Disable escaping of DBMS identifiers | High |
| `--web-root` | Specify the server document root | High/destructive support option |
| `--purge` | Remove local SQLMap data | Destructive to local evidence |
| `--cleanup` | Remove SQLMap UDF/tables from DBMS | Destructive; use only after matching activity |
| `--update` | Update the checkout | Changes tested version |

## Non-SQL Injection Modes

Current maintained documentation also exposes dedicated modes including:

- `--nosql`
- `--graphql`
- `--ldap`
- `--xpath`
- `--ssti`
- `--xxe`
- `--hql`

The XXE mode also supports `--oob-server` and `--oob-token`; both can disclose
target interactions to the configured out-of-band service, so use only an
approved, controlled listener.

Only one of these modes is used per run. Their result and extraction semantics
differ from SQL enumeration, so do not assume `--dbs`, `--tables`, or `--dump`
apply. Treat each as a separate testing discipline with its own guide and lab.

## Interfaces and Automation

| Option or entry point | Summary | Risk |
|---|---|---|
| `--wizard` | Interactive command builder | Depends on selections |
| `--shell` | Interactive SQLMap command environment | Depends on selections |
| `--tui` | Textual interface | Depends on selections |
| `--gui` | Graphical interface when dependencies are available | Depends on selections |
| `sqlmapapi.py` | REST API server/client | High: protect and bind carefully |

Additional miscellaneous controls include:

| Option | Summary | Risk |
|---|---|---|
| `-z` | Short mnemonic option syntax | Elevated: expand and review before use |
| `--alert` | Run a local OS command when injection is found | High: local code execution |
| `--beep` | Audible prompt/finding notification | Routine |
| `--disable-hashing` | Disable automatic hash analysis in dumps | Routine/data minimization |
| `--no-logging` | Disable SQLMap file logging | Elevated: reduces evidence |
| `--no-truncate` | Show long console output without truncation | Routine; may expose data |
| `--results-file` | Set the CSV result file for multiple targets | High-sensitivity evidence |
| `--tmp-dir` | Set SQLMap's local temporary directory | Routine |

Do not expose the SQLMap API to an untrusted network. Treat API task data and
logs as sensitive assessment evidence.

## Official Reference

- [Complete maintained Usage documentation](https://github.com/sqlmapproject/sqlmap/wiki/Usage)
- [Techniques](https://github.com/sqlmapproject/sqlmap/wiki/Techniques)
- [REST API](https://github.com/sqlmapproject/sqlmap/wiki/REST-API)

---

[← SQLMap guide](./README.md) · [Advanced capabilities](./ADVANCED.md) · [Lab plan](./LAB.md)
