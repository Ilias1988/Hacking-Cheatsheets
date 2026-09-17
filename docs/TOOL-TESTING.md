# Tool Documentation Testing

> **Last verified:** 2026-09-17  
> **Checked against:** Repository smoke-test implementation and GitHub Actions workflow

Tool documentation changes faster than general security methodology. This
repository separates three evidence levels so that a help check is never
misrepresented as a functional penetration test.

## Evidence Levels

| Level | Meaning |
|---|---|
| Source-checked | Compared with current official documentation or a maintained upstream repository |
| CLI smoke-tested | The recorded version and help output were executed; documented flags were confirmed to exist |
| Lab-tested | Commands were executed against an isolated authorized lab and the environment is recorded |

Passing a CLI smoke test confirms syntax availability, not runtime behavior,
permissions, side effects, provider responses, or vulnerability detection.

## Current Automated Coverage

| Tool | Verified distribution | Tested interface |
|---|---|---|
| Nuclei | `projectdiscovery/nuclei:latest` | `-version`, `-help`, documented flags |
| httpx | `projectdiscovery/httpx:latest` | `-version`, `-help`, documented flags |
| Subfinder | `projectdiscovery/subfinder:latest` | `-version`, `-help`, documented flags |
| ffuf | Official `v2.3.0` release + pinned SHA-256 | `-V`, `-h`, documented flags |
| Nmap | Official `v7.991` RPM + Nmap.org SHA-256 | `--version`, `--help`, documented flags |
| SQLMap | Official `master` checkout + recorded commit | `--version`, `-hh`, documented flags |

Run one tool at a time:

```bash
python scripts/tool_smoke_tests.py --tool nuclei
python scripts/tool_smoke_tests.py --tool httpx
python scripts/tool_smoke_tests.py --tool subfinder
python scripts/tool_smoke_tests.py --tool ffuf
python scripts/tool_smoke_tests.py --tool nmap
python scripts/tool_smoke_tests.py --tool sqlmap
```

Run the complete manifest:

```bash
python scripts/tool_smoke_tests.py
```

## Disk-Space Safety

The test runner checks whether a container image existed before the test. Images
pulled by the runner are removed in a `finally` block after success or failure.
Pre-existing user images are preserved. Official release archives are verified
against pinned SHA-256 digests and extracted only inside an automatically
deleted temporary directory. Package-only tests use ephemeral containers and
remove any base image pulled by the test. Use `--keep-images` only when
explicitly needed for container debugging.

Git-based tools are shallow-cloned from the configured official repository and
the tested commit is printed. Checkouts exist only inside a temporary directory
or ephemeral container and are removed after the test.

## Adding a Tool

1. Prefer an official image published by the tool maintainer.
2. If no official image exists, pin an official release asset and SHA-256 for
   each supported runner platform.
3. Add version/help arguments and a narrow version pattern to
   `scripts/tool-smoke-tests.json`.
4. Add only flags that actually appear in the maintained guide.
5. Run the smoke test locally and confirm artifact cleanup.
6. Record the observed exact version in the guide.
7. Keep functional target testing in a separate isolated lab procedure.

The scheduled workflow runs against current official images so removed flags
cause a visible failure. A successful scheduled run does not automatically
change verification dates; a maintainer must review the upstream change and the
guide before updating metadata.

## Supply-Chain Boundary

Scheduled tests execute third-party containers, checksum-pinned official
release binaries, or official source checkouts only with version/help
arguments. They receive no repository secrets, target lists, host networking,
privileged mode, or mounted customer data. Pulling `latest` or testing a moving
branch intentionally detects upstream CLI drift but is not reproducible; the
observed version and commit remain part of the evidence.

---

[← Verification policy](./VERIFICATION.md) · [← Main index](../README.md)
