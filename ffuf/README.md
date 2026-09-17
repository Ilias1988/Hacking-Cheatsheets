# ffuf — Web Fuzzing Guide

> **Last verified:** 2026-09-17  
> **Tested version:** ffuf 2.3.0  
> **Evidence:** Official source checked + CLI smoke-tested  
> **Test method:** Official Windows release, SHA-256 verified; `-V`, `-h`, and 51 documented flags checked  
> **Lab status:** Examples below are syntax-validated, not target-behaviour tests

[ffuf](https://github.com/ffuf/ffuf) places wordlist values wherever a
keyword such as `FUZZ` appears in an HTTP request. It is useful for authorized
content discovery, virtual-host discovery, parameter testing, and request-body
fuzzing.

Use it only on systems you own or have explicit permission to test. Start with
conservative concurrency and rate limits; fuzzing can create substantial load.

## Install and Verify

Prefer the official release page or a package route listed by the maintainer:

```bash
# Go
go install github.com/ffuf/ffuf/v2@latest

# Windows
winget install ffuf.ffuf
scoop install ffuf

# macOS
brew install ffuf
```

Verify the installed build before relying on this guide:

```bash
ffuf -V
ffuf -h
```

For manually downloaded releases, verify the archive against the matching
`checksums.txt` file on the [official release](https://github.com/ffuf/ffuf/releases).

## Core Model

The minimal form is:

```bash
ffuf -w wordlist.txt -u https://example.test/FUZZ
```

`FUZZ` may be placed in the URL, a header, or request data. Custom keywords let
multiple wordlists target different positions:

```bash
ffuf -w users.txt:USER -w values.txt:VALUE \
  -u 'https://example.test/profile?user=USER&view=VALUE' \
  -mode clusterbomb
```

The main multi-wordlist modes are `clusterbomb`, `pitchfork`, and `sniper`.
Choose the mode deliberately because it changes the number and pairing of
requests.

## Safe Baseline

This baseline enables auto-calibration, limits request rate and concurrency,
and records reproducible JSON output:

```bash
ffuf -w wordlist.txt \
  -u https://example.test/FUZZ \
  -ac -rate 20 -t 10 -timeout 10 \
  -o ffuf-results.json -of json
```

`-t` controls concurrent workers, while `-rate` caps requests per second.
`-p 0.2` adds a fixed delay; a range such as `-p 0.2-0.8` adds jitter. Respect
the written rules of engagement rather than treating these values as universal.

## Content Discovery

```bash
# Paths
ffuf -w content.txt -u https://example.test/FUZZ -ac

# Append selected extensions to each word
ffuf -w content.txt -u https://example.test/FUZZ \
  -e .php,.html,.txt -ac

# Match selected status codes
ffuf -w content.txt -u https://example.test/FUZZ \
  -mc 200,204,301,302,307,401,403
```

A `403` can be a useful discovery result; it is not proof that access controls
are bypassable. Validate each result manually and record the exact request.

## Virtual-Host Discovery

```bash
ffuf -w vhosts.txt \
  -u https://example.test/ \
  -H 'Host: FUZZ.example.test' \
  -ac -ach
```

`-ach` performs auto-calibration per host. This tests HTTP virtual-host routing;
it is not DNS enumeration. Putting `FUZZ` directly in the hostname works only
when those names already resolve, for example through wildcard DNS or an
authorized lab configuration.

## Parameters and Request Bodies

```bash
# Parameter value
ffuf -w ids.txt -u 'https://example.test/item?id=FUZZ' -ac

# Parameter name
ffuf -w parameters.txt -u 'https://example.test/search?FUZZ=test' -ac

# Form body
ffuf -w values.txt -u https://example.test/profile \
  -X POST -H 'Content-Type: application/x-www-form-urlencoded' \
  -d 'display_name=FUZZ'

# JSON body
ffuf -w values.txt -u https://example.test/api/profile \
  -X POST -H 'Content-Type: application/json' \
  -d '{"display_name":"FUZZ"}'
```

Do not use authentication, password, or account-lockout examples unless that
activity is explicitly authorized and the safety controls are agreed in
advance.

## Raw Requests

Export a request from an intercepting proxy, replace the intended value with
`FUZZ`, then run:

```bash
ffuf -request request.txt -request-proto https \
  -w values.txt -ac -rate 10
```

Keep real session tokens and customer data out of the repository. Store raw
requests and results in an engagement-specific encrypted workspace.

## Matchers and Filters

Matchers select responses to display. Filters remove known noise.

| Signal | Match | Filter |
|---|---|---|
| HTTP status | `-mc` | `-fc` |
| Response size | `-ms` | `-fs` |
| Word count | `-mw` | `-fw` |
| Line count | `-ml` | `-fl` |
| Time to first byte | `-mt` | `-ft` |
| Regular expression | `-mr` | `-fr` |

Examples:

```bash
# Remove a uniform not-found response after measuring it
ffuf -w content.txt -u https://example.test/FUZZ -fs 1234

# Match a stable marker in the response body
ffuf -w values.txt -u 'https://example.test/item?id=FUZZ' \
  -mr 'Item details'

# Let ffuf derive baseline filters
ffuf -w content.txt -u https://example.test/FUZZ -ac
```

Avoid copying a filter size from another target. First send random nonexistent
paths, compare several responses, and confirm that the chosen filter does not
hide valid results.

## Recursion

Recursion is supported only when the URL ends in `FUZZ`:

```bash
ffuf -w content.txt -u https://example.test/FUZZ \
  -recursion -recursion-depth 2 \
  -recursion-strategy default \
  -maxtime 600 -maxtime-job 120 \
  -rate 20 -t 10
```

`default` follows discovered redirects; `greedy` recurses into all matches.
Bound depth and runtime to prevent accidental request explosions.

## Proxy and Replay

```bash
# Send every request through a proxy
ffuf -w content.txt -u https://example.test/FUZZ \
  -x http://127.0.0.1:8080

# Replay only matched requests through a proxy
ffuf -w content.txt -u https://example.test/FUZZ \
  -replay-proxy http://127.0.0.1:8080
```

`-x` is the proxy option in ffuf. It has a different meaning in some other
tools, so do not transfer flags between commands without checking `ffuf -h`.

## Output and Evidence

```bash
# JSON
ffuf -w content.txt -u https://example.test/FUZZ \
  -o results.json -of json

# HTML report
ffuf -w content.txt -u https://example.test/FUZZ \
  -o results.html -of html

# Do not create an empty result file
ffuf -w content.txt -u https://example.test/FUZZ \
  -o results.json -of json -or

# Diagnostic log
ffuf -w content.txt -u https://example.test/FUZZ \
  -debug-log ffuf-debug.log
```

For reproducibility, record the exact ffuf version, wordlist name and hash,
command, UTC time, authorized scope, filters, and representative raw requests.

## Troubleshooting

| Symptom | Check |
|---|---|
| Every word matches | Compare random nonexistent paths; use `-ac` or a measured filter |
| Nothing matches | Remove filters and inspect a known request manually |
| HTTPS virtual hosts fail | Confirm DNS/IP routing, TLS certificate, and intended Host header |
| Too many requests | Lower `-rate` and `-t`; set `-maxtime` and `-maxtime-job` |
| Redirect target is missing | Add `-r` only if following redirects is in scope |
| Encoded path behaves unexpectedly | Compare normal encoding with `-raw`; do not assume they are equivalent |

## Verification Notes

The repository smoke test downloads the official ffuf 2.3.0 release for the
runner platform, verifies a pinned SHA-256 digest, checks all flags referenced
by this guide, and deletes both the archive and extracted binary. It does not
send requests to a target and therefore does not claim behavioural lab
validation.

## Official References

- [ffuf repository and maintained usage](https://github.com/ffuf/ffuf)
- [Official releases and checksums](https://github.com/ffuf/ffuf/releases)
- [Configuration documentation](https://github.com/ffuf/ffuf/wiki/Configuration)

---

[← Main index](../README.md) · [Tool testing policy](../docs/TOOL-TESTING.md) · [Safe use](../docs/SAFE_USE.md)
