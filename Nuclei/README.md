# Nuclei

> **Last verified:** 2026-09-17  
> **Checked against:** Official ProjectDiscovery documentation and Nuclei `v3.11.1` CLI help  
> **Smoke tested:** Official `projectdiscovery/nuclei:latest` image; `-version` and `-help` only  
> **Scope:** Authorized targets and reviewed templates only

Nuclei is a template-driven scanner. Its speed and extensibility are useful,
but templates can make intrusive requests, trigger workflows, execute code, or
create significant load. Review the selected templates and the rules of
engagement before scanning.

## Verify the Installed Build

```bash
nuclei -version
nuclei -help
nuclei -update-templates
```

This guide was checked against `v3.11.1`. Recheck local help when another
version is installed.

## Safe Starting Workflow

```text
Confirm scope → Prepare explicit input → Review template selection
→ Set conservative rate/concurrency → Run against test targets
→ Manually validate findings → Preserve evidence → Clean up
```

Start with an approved target list and a reviewed template directory:

```bash
nuclei -l approved-targets.txt \
  -t reviewed-templates/ \
  -rate-limit 5 \
  -bulk-size 5 \
  -concurrency 2 \
  -jsonl-export nuclei-findings.jsonl
```

Do not assume the public template set is automatically suitable for every
production assessment. Code, headless, and DAST/fuzzing templates require
separate consideration and explicit enablement.

## Inputs

```bash
# One target
nuclei -u https://app.example.test

# One target per line
nuclei -l approved-targets.txt

# Structured input
nuclei -l openapi.yaml -input-mode openapi
nuclei -l exported-request.burp -input-mode burp
```

Supported structured modes are version-dependent; `v3.11.1` lists `list`,
`burp`, `jsonl`, `yaml`, `openapi`, and `swagger`.

## Template Selection

```bash
# Explicit template or directory
nuclei -u https://app.example.test -templates reviewed-template.yaml
nuclei -l approved-targets.txt -templates reviewed-templates/

# Filter the active template root
nuclei -l approved-targets.txt -tags cve \
  -severity critical,high

# Exclusions
nuclei -l approved-targets.txt -exclude-tags dos,fuzz \
  -exclude-severity info,low

# Exclude a specific template ID
nuclei -l approved-targets.txt -exclude-id TEMPLATE-ID
```

Inspect available content before execution:

```bash
nuclei -tl
nuclei -validate -templates reviewed-templates/
```

Template directories and IDs change independently of the engine. Avoid hard
coding year-based paths such as `cves/2024/` unless that path exists in the
installed template release.

## Output

```bash
# Human-readable output
nuclei -l approved-targets.txt -output findings.txt

# Streaming JSONL
nuclei -l approved-targets.txt -jsonl -output findings.jsonl

# Explicit exporters
nuclei -l approved-targets.txt -json-export findings.json
nuclei -l approved-targets.txt -jsonl-export findings.jsonl
nuclei -l approved-targets.txt -markdown-export markdown-report/
nuclei -l approved-targets.txt -sarif-export findings.sarif
```

`-json` is not the current output flag in `v3.11.1`; use `-jsonl`,
`-json-export`, or `-jsonl-export` according to the required format.

## Rate and Concurrency Controls

| Flag | Meaning in `v3.11.1` |
|---|---|
| `-rate-limit` / `-rl` | Maximum requests per second |
| `-bulk-size` / `-bs` | Hosts analyzed in parallel per template |
| `-concurrency` / `-c` | Templates executed in parallel |
| `-timeout` | Request timeout in seconds |
| `-retries` | Retries for failed requests |

Use low values first. Increasing concurrency can amplify traffic far beyond the
number of input hosts because each template may issue multiple requests.

## Authentication and Sensitive Data

```bash
nuclei -l approved-targets.txt \
  -templates reviewed-authenticated-templates/ \
  -header "Authorization: Bearer TEST_TOKEN"
```

Avoid shell history and shared process listings for real tokens. Use dedicated
test credentials, minimize scopes, rotate them after testing, and redact secrets
from JSONL, Markdown, SARIF, debug, and request/response output.

## OAST, Headless, Code, and DAST Templates

- `-interactsh-server` configures a self-hosted Interactsh endpoint.
- `-headless` enables templates that require a browser.
- `-code` enables code-protocol templates.
- `-dast` enables DAST/fuzzing templates; the older `-fuzz` flag is deprecated.

These modes expand impact and data flows. Use them only when the templates,
callback infrastructure, privacy implications, and production risk have been
reviewed and authorized.

## Finding Validation

For every result:

1. Record the engine and template release/version.
2. Preserve the template ID, target, timestamp, and sanitized evidence.
3. Manually confirm the behavior with the minimum safe request.
4. Separate confirmed impact from scanner-inferred impact.
5. Record false positives and any cleanup performed.

## References

- [Nuclei running documentation](https://docs.projectdiscovery.io/opensource/nuclei/running)
- [Nuclei repository](https://github.com/projectdiscovery/nuclei)
- [Nuclei templates repository](https://github.com/projectdiscovery/nuclei-templates)
- [Template documentation](https://docs.projectdiscovery.io/templates/introduction)

---

[← Main index](../README.md)
