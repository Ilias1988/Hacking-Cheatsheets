# httpx

> **Last verified:** 2026-09-17  
> **Checked against:** Official ProjectDiscovery documentation and httpx `v1.12.0` CLI help  
> **Smoke tested:** Official `projectdiscovery/httpx:latest` image; `-version` and `-help` only  
> **Scope:** Authorized hosts and conservative probing only

ProjectDiscovery httpx probes HTTP services and enriches them with status,
technology, TLS, DNS, response, and screenshot metadata. It is different from
the Python HTTP client package with the same name.

## Verify the Installed Build

```bash
httpx -version
httpx -help
```

This guide was checked against `v1.12.0`.

## Basic Probing

```bash
# One target
httpx -u https://app.example.test

# One host or URL per line
httpx -list approved-hosts.txt

# Useful low-impact probes
httpx -list approved-hosts.txt \
  -status-code -title -web-server -tech-detect \
  -ip -cname -cdn -response-time
```

By default, an HTTPS probe falls back to HTTP when HTTPS is unreachable. Use
`-no-fallback` when both results are needed, or `-no-fallback-scheme` to keep the
scheme supplied in the input.

## Match and Filter

```bash
# Include selected status codes
httpx -list approved-hosts.txt -match-code 200,301,302

# Remove common non-results
httpx -list approved-hosts.txt -filter-code 404

# Match a string or regular expression
httpx -list approved-hosts.txt -match-string "dashboard"
httpx -list approved-hosts.txt -match-regex "admin|login"

# Filter by technology after detection
httpx -list approved-hosts.txt -tech-detect \
  -match-condition 'contains(tech, "nginx")'
```

Validate match/filter assumptions with a small sample before applying them to a
large list; aggressive filters can hide relevant assets.

## Ports and Paths

Run port and path expansion as deliberate, separately scoped operations:

```bash
httpx -list approved-hosts.txt -ports http:80,8080,https:443,8443
httpx -list approved-hosts.txt -path /robots.txt,/security.txt
```

The official documentation recommends treating `-ports`, `-path`, screenshots,
TLS probes, CSP probes, HTTP/2, and pipeline checks as specific use cases rather
than enabling everything by default.

## Output and Evidence

```bash
# Text
httpx -list approved-hosts.txt -status-code -title -output httpx.txt

# The current -json flag writes JSONL
httpx -list approved-hosts.txt -json -output httpx.jsonl

# CSV
httpx -list approved-hosts.txt -csv -output httpx.csv

# Store responses in a controlled directory
httpx -list approved-hosts.txt -store-response \
  -store-response-dir evidence/httpx-responses/
```

Response storage and JSON output may contain cookies, tokens, private hostnames,
or personal data. Encrypt, restrict, redact, and delete them according to the
engagement rules.

## Screenshots

```bash
httpx -list approved-hosts.txt -screenshot \
  -exclude-screenshot-bytes -exclude-headless-body
```

`v1.12.0` does not advertise the old `-screenshot-output` or `-ssd` examples.
Use the current help and output structure. `-system-chrome` is available when an
approved local Chrome installation should be used.

## TLS and Protocol Probes

```bash
httpx -list approved-hosts.txt -tls-probe
httpx -list approved-hosts.txt -tls-grab
httpx -list approved-hosts.txt -http2
```

The old `-tlsgrab` spelling is not listed in `v1.12.0`; use `-tls-grab`.

## Headers, Proxy, and Methods

```bash
httpx -list approved-hosts.txt \
  -header "Authorization: Bearer TEST_TOKEN"

httpx -list approved-hosts.txt \
  -proxy http://127.0.0.1:8080

# Probe a specific request method
httpx -list approved-hosts.txt -x HEAD
```

`-method` displays the request method as a probe result; `-x` selects methods to
send. Do not place real secrets in committed command histories or output files.

## Performance Controls

```bash
httpx -list approved-hosts.txt \
  -threads 10 -rate-limit 10 -timeout 10 -retries 1
```

Start low and measure. Rate limiting is requests per second; thread count is
concurrency. Authorization for a host does not automatically authorize every
resolved IP, port, vhost, or linked domain.

## Pipeline Example

```bash
subfinder -domain example.test -silent |
  httpx -silent -status-code -title -tech-detect \
    -threads 5 -rate-limit 5
```

Use only domains explicitly inside scope and retain the intermediate asset list
so results remain reproducible.

## Removed or Corrected Examples

The previous guide contained examples not present in `v1.12.0` help, including
`-screenshot-output`, `-ssd`, `-tlsgrab`, and `-resume-file`. They are omitted.
Use `-resume` with httpx's managed `resume.cfg` behavior when resume support is
needed.

## References

- [httpx usage](https://docs.projectdiscovery.io/opensource/httpx/usage)
- [httpx repository](https://github.com/projectdiscovery/httpx)
- [httpx installation](https://docs.projectdiscovery.io/opensource/httpx/install)

---

[← Main index](../README.md)
