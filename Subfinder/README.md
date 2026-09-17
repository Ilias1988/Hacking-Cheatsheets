# Subfinder

> **Last verified:** 2026-09-17  
> **Checked against:** Official ProjectDiscovery documentation and Subfinder `v2.16.0` CLI help  
> **Smoke tested:** Official `projectdiscovery/subfinder:latest` image; `-version` and `-help` only  
> **Scope:** Passive discovery for explicitly authorized domains

Subfinder discovers subdomains from passive online sources. Passive does not
mean invisible: providers receive queries, API keys may be used, and later
resolution or probing can generate direct target traffic.

## Verify the Installed Build

```bash
subfinder -version
subfinder -help
```

This guide was checked against `v2.16.0`.

## Basic Discovery

```bash
# One domain
subfinder -domain example.test

# Quiet pipeline-friendly output
subfinder -domain example.test -silent

# Multiple authorized domains, one per line
subfinder -list scope.txt -output discovered-subdomains.txt
```

Normalize and deduplicate the output before passing it to active tools. Confirm
that discovered subsidiaries, acquisitions, third parties, and wildcard results
are actually inside scope.

## Sources

```bash
# List available sources
subfinder -list-sources

# Select or exclude sources
subfinder -domain example.test -sources crtsh,hackertarget
subfinder -domain example.test -exclude-sources shodan,securitytrails

# Use all configured sources; slower and may require API keys
subfinder -domain example.test -all

# Use sources that support recursive subdomain queries
subfinder -domain example.test -recursive
```

Store provider API keys in the provider configuration, not in this repository,
screenshots, shell history, or reports:

```bash
subfinder -provider-config /secure/path/provider-config.yaml \
  -domain example.test
```

Apply least privilege and rotate exposed keys.

## Match and Filter

```bash
subfinder -domain example.test -match api,dev,stage
subfinder -domain example.test -filter www,mail
```

In `v2.16.0`, the flags are `-match`/`-m` and `-filter`/`-f`. The previous
`-exclude-subdomains` example is not present in current help.

## Output

```bash
# Text file
subfinder -domain example.test -output subdomains.txt

# JSONL with source attribution
subfinder -domain example.test -json -collect-sources \
  -output subdomains.jsonl

# One output directory per input domain; valid with -list
subfinder -list scope.txt -output-dir results/
```

The current CLI lists `-json`/`-oJ` for JSONL and `-output-dir`/`-oD` for a
directory. The old `-oJL` example is not a current flag.

## Active Resolution

```bash
subfinder -domain example.test -active -ip \
  -r resolvers.txt
```

`-active` (`-nW`) resolves discovered names. `-ip` is valid only with active
mode. The `-t` flag controls concurrent resolving only in active mode; it is not
a general passive-source concurrency control.

Active resolution creates target-facing DNS activity. Use trusted resolvers and
confirm it is permitted.

## Rate and Time Controls

```bash
subfinder -domain example.test \
  -rate-limit 5 -timeout 30 -max-time 10

# Per-provider limits
subfinder -domain example.test \
  -rate-limits "hackertarget=2/s,github=30/m"
```

Provider quotas and acceptable rates differ. Conservative settings reduce API
errors, account throttling, and unintended load.

## Proxy

```bash
subfinder -domain example.test \
  -proxy http://127.0.0.1:8080
```

Confirm what the selected source and resolver traffic actually sends through
the proxy before relying on it for privacy or evidence collection.

## Pipeline Example

```bash
subfinder -domain example.test -silent \
  -output discovered-subdomains.txt

httpx -list discovered-subdomains.txt -silent \
  -status-code -title -threads 5 -rate-limit 5
```

Keep passive discovery separate from active HTTP probing in notes and evidence.
This makes scope validation and impact attribution clearer.

## Evidence Checklist

- Subfinder version and configuration path.
- Input scope and execution timestamp.
- Enabled sources and whether paid/API-backed sources were used.
- Raw results, normalized results, and scope exclusions.
- Whether active resolution or downstream probing occurred.
- Sensitive provider keys excluded from evidence.

## References

- [Subfinder usage](https://docs.projectdiscovery.io/opensource/subfinder/usage)
- [Subfinder repository](https://github.com/projectdiscovery/subfinder)
- [Subfinder installation](https://docs.projectdiscovery.io/opensource/subfinder/install)

---

[← Main index](../README.md)
