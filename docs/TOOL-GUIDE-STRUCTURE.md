# Tool Guide Structure

> **Last verified:** 2026-09-17
> **Checked against:** Repository verification, testing, and maintenance policy

Tool documentation must preserve useful capability coverage without presenting
high-impact actions as ordinary defaults. A substantial tool guide should use
the following layered structure when the tool is complex enough to need it.

## Layer 1: Main Guide

`Tool/README.md` is the operational entry point. It should contain:

- exact tested version or commit;
- evidence level and test limitations;
- official installation and verification routes;
- a conservative authorized-use baseline;
- the common assessment workflow;
- scope, rate, evidence, cleanup, and troubleshooting guidance;
- links to the complete reference, advanced capabilities, and lab procedure.

The main guide must remain useful on its own, but it does not need to carry
every flag inline.

## Layer 2: Option Reference

`Tool/OPTIONS.md` preserves discoverability. It should:

- organize current options by the tool's own functional groups;
- state the version or commit against which options were checked;
- distinguish CLI-verified options from source-only or legacy options;
- summarize behavior rather than copying upstream help verbatim;
- identify options that expand scope, traffic, data access, or side effects;
- link to the exact official option reference.

Removing a useful current option merely because it is advanced is not an
acceptable refresh strategy.

## Layer 3: Advanced Capabilities

`Tool/ADVANCED.md` contains capabilities that are valuable in CTFs, isolated
labs, or explicitly authorized engagements but are inappropriate as defaults.
Each section must state:

- risk level: elevated, high, or destructive;
- required authorization and preconditions;
- likely side effects and sensitive evidence produced;
- stop conditions, recovery, and cleanup expectations;
- a safe lab-oriented example when one can be provided responsibly.

High-risk content should be contextualized, not silently deleted. Commands that
would add little educational value while materially increasing abuse potential
may remain descriptive rather than turnkey.

## Layer 4: Lab Validation

`Tool/LAB.md` records behavioral testing separately from CLI smoke tests. A lab
record should include:

- topology, operating systems, tool and target versions, and image digests;
- isolation controls and confirmation that no unauthorized route exists;
- test cases, expected behavior, observed behavior, and captured evidence;
- cleanup steps and verification that artifacts were removed;
- an explicit status such as planned, partial, passed, or failed.

Do not label a guide lab-tested because its help output was executed.

## Smaller Tools

Simple tools do not need four nearly empty files. Their README may contain the
same four layers as clearly marked sections. Split files when advanced content
would obscure the common workflow or make review difficult.

## Refresh Rule

When modernizing an existing guide:

1. Inventory its useful capabilities before rewriting it.
2. Check flags against the current CLI and primary sources.
3. Move specialized material to the appropriate layer instead of deleting it.
4. Remove only commands that are invalid, unsupported, duplicated, misleading,
   or unsafe without meaningful educational value.
5. Record omitted legacy features and their replacement when readers may still
   encounter them.
6. Run documentation validation, content health, unit tests, Markdown lint, and
   the relevant tool smoke test.

## Required Navigation

Every split guide must link the layers in both directions:

```text
README.md → OPTIONS.md → ADVANCED.md → LAB.md
    ↑_____________________________________|
```

This makes the main guide fast to use while retaining complete, reviewable
technical depth.

---

[← Maintenance guide](./MAINTENANCE.md) · [Verification policy](./VERIFICATION.md) · [Tool testing](./TOOL-TESTING.md)
