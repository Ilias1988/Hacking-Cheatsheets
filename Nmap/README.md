# Nmap — Network Discovery and Scanning Guide

> **Last verified:** 2026-09-17
> **Tested version:** Nmap 7.991
> **Evidence:** Official documentation checked + CLI smoke-tested
> **Test method:** Official Nmap.org RPM, SHA-256 verified; `--version`, `--help`, and 52 high-use flags checked
> **Lab status:** Examples are syntax-validated, not target-behaviour tests

[Nmap](https://nmap.org/) is a network exploration and security-auditing tool.
This guide focuses on repeatable, authorized enumeration rather than maximum
scan speed or firewall-evasion recipes.

Only scan addresses and protocols covered by written authorization. Confirm
rate limits, exclusions, source IPs, maintenance windows, and the handling of
fragile systems before starting.

## Install and Verify

Use the [official download page](https://nmap.org/download) for the current
Windows, macOS, RPM, or source release. Linux distribution packages are also
convenient but may ship an older version.

```bash
# Debian/Ubuntu/Kali
sudo apt update
sudo apt install nmap

# Fedora
sudo dnf install nmap

# Verify the actual build and available flags
nmap --version
nmap --help
```

On Windows, raw-packet features depend on Npcap and usually require an elevated
terminal. The Nmap installer may include an older Npcap build; compare it with
the current release named on Nmap's official download page before upgrading.

## How to Read Results

Common port states are observations, not vulnerability conclusions:

| State | Practical meaning |
|---|---|
| `open` | An application accepted a connection or packet |
| `closed` | The host responded, but no application is listening there |
| `filtered` | A packet filter or network condition prevented a conclusion |
| `unfiltered` | Reachable, but the selected scan cannot determine open versus closed |
| `open|filtered` | No response distinguishes open from filtered |
| `closed|filtered` | The selected scan cannot distinguish the two states |

Service names are initially inferred from ports. Use `-sV` and manual protocol
validation before reporting a product or version.

## Targets and Scope Controls

```bash
# Single host or authorized subnet
nmap 192.0.2.10
nmap 192.0.2.0/28

# Read approved targets from a file
nmap -iL scope.txt

# Explicit exclusions
nmap 192.0.2.0/24 --exclude 192.0.2.1,192.0.2.254
nmap 192.0.2.0/24 --excludefile exclusions.txt

# IPv6
nmap -6 2001:db8::10
```

Prefer `-iL` plus `--excludefile` for engagements: both files can be reviewed,
versioned outside the public repository, and retained with the evidence.

## Host Discovery

```bash
# Discovery only; no port scan
nmap -sn 192.0.2.0/28

# Skip reverse DNS for faster, less noisy output
nmap -sn -n 192.0.2.0/28

# Request reverse DNS for every target
nmap -sn -R 192.0.2.0/28

# Select discovery probes when the rules of engagement permit them
sudo nmap -sn -PS80,443 -PA3389 -PU53 192.0.2.0/28
sudo nmap -sn -PE -PP -PM 192.0.2.0/28
```

On a local Ethernet network, privileged Nmap normally uses ARP or Neighbor
Discovery because it is more reliable than IP probes. Across routed networks,
probe behavior differs by platform and privilege level.

`-Pn` skips host discovery and attempts the requested scan against every input
address. It does not prove that a host is online and can make large scans much
slower:

```bash
nmap -Pn -p 443 192.0.2.10
```

## TCP and UDP Scans

```bash
# TCP SYN scan: raw-packet privileges required
sudo nmap -sS 192.0.2.10

# TCP connect scan: works without raw-packet privileges
nmap -sT 192.0.2.10

# Targeted UDP scan; UDP is slower and often returns open|filtered
sudo nmap -sU -p 53,123,161 192.0.2.10

# Combine UDP with one TCP scan type
sudo nmap -sS -sU -p T:22,80,443,U:53,161 192.0.2.10
```

Use `--reason` to see which response led to a state classification:

```bash
sudo nmap -sS --reason -p 22,80,443 192.0.2.10
```

## Port Selection

```bash
# Explicit ports and ranges
nmap -p 22,80,443,8000-8100 192.0.2.10

# All TCP ports
nmap -p- 192.0.2.10

# Most common ports from Nmap's frequency data
nmap --top-ports 1000 192.0.2.10

# Fast scan: fewer ports than the default scan
nmap -F 192.0.2.10

# Scan in numeric order instead of randomizing ports
nmap -r -p 1-1024 192.0.2.10

# Remove prohibited or fragile ports from consideration
nmap --top-ports 1000 --exclude-ports 9100 192.0.2.10
```

`-p-` is a port range, not a scan type. Pairing it with unsafe rates can
generate substantial traffic.

## Service and OS Detection

```bash
# Probe detected ports for service and version information
nmap -sV -p 22,80,443 192.0.2.10

# Lighter or more complete version probing
nmap -sV --version-light -p 443 192.0.2.10
nmap -sV --version-all -p 443 192.0.2.10
nmap -sV --version-intensity 5 -p 443 192.0.2.10

# OS detection generally needs raw-packet privileges
sudo nmap -O 192.0.2.10

# OS detection, version detection, default scripts, and traceroute
sudo nmap -A 192.0.2.10
```

`-A` is a bundle, not a stealth option. OS detection is most useful when Nmap
finds at least one open and one closed TCP port. Report uncertain fingerprints
as estimates and corroborate them with other evidence.

## Nmap Scripting Engine

```bash
# Default script set
nmap -sC -sV -p 22,80,443 192.0.2.10

# Named scripts
nmap --script http-title,http-headers -p 80,443 192.0.2.10

# Script arguments; quote them so the shell does not reinterpret punctuation
nmap --script http-title --script-args 'http.useragent=Authorized-Assessment' \
  -p 80,443 192.0.2.10

# Rebuild the local script database after intentionally adding scripts
sudo nmap --script-updatedb
```

Read each script's official NSE documentation and source before running it.
Categories such as `intrusive`, `brute`, `dos`, `exploit`, and broad `vuln`
selection can cause authentication attempts, state changes, crashes, or heavy
load. Even a script marked `safe` is not a substitute for target-specific risk
review.

## Timing and Load Control

Nmap timing templates range from `-T0` through `-T5`; `-T3` is the normal
default. Faster templates shorten several timeouts and may reduce accuracy or
overload sensitive infrastructure.

```bash
# Conservative general scan with explicit upper rate and timeout
nmap -sT -T3 --max-rate 100 --host-timeout 10m \
  -p 1-1000 192.0.2.10

# Limit retransmissions where packet loss has been characterized
nmap -sT --max-retries 2 -p 80,443 192.0.2.10

# Add a minimum delay between probes to a host
nmap -sT --scan-delay 200ms -p 80,443 192.0.2.10
```

`--min-rate` asks Nmap to maintain at least a chosen packet rate and can force
aggressive behavior. Prefer `--max-rate` as a safety ceiling unless a tested
lab plan specifically requires a minimum rate.

## Output and Evidence

```bash
# Normal, XML, and legacy grepable output together
nmap -sV -p 22,80,443 -oA evidence/host-192.0.2.10 192.0.2.10

# Individual formats
nmap -sV -oN results.nmap 192.0.2.10
nmap -sV -oX results.xml 192.0.2.10
nmap -sV -oG results.gnmap 192.0.2.10

# Verbose or debug console output
nmap -v 192.0.2.10
nmap -d 192.0.2.10

# Resume an interrupted normal-output scan when supported by its log
nmap --resume results.nmap
```

`-oA basename` creates `.nmap`, `.xml`, and `.gnmap` files. Prefer XML for
automation; grepable output is retained for legacy workflows. Protect results
because hostnames, ports, service banners, and topology may be sensitive.

Record at minimum:

- exact Nmap version and command;
- source host/interface and privilege level;
- UTC start/end times and authorized target list;
- exclusions, timing/rate controls, and packet-loss conditions;
- original XML plus representative manually validated evidence.

Use `--append-output` only when mixing runs in one file is intentional; separate
files are usually easier to attribute and review.

## Interface, Routing, and Traceroute

```bash
# Select an interface when routing is ambiguous
sudo nmap -e eth0 -sS 192.0.2.10

# Request traceroute as part of the scan
sudo nmap --traceroute 192.0.2.10

# Explicit source address only when routing and authorization require it
sudo nmap -e eth0 -S 192.0.2.20 -sS 192.0.2.10

# Explicit source port when an approved test case requires it
sudo nmap --source-port 53 -sS -p 443 192.0.2.10
```

Incorrect `-e`, `-S`, or `--source-port` values can produce misleading results
or test the wrong network path. Capture the route and interface configuration
with the engagement evidence.

## Practical Assessment Flow

```bash
# 1. Confirm which addresses respond to permitted discovery
nmap -sn -n -iL scope.txt --excludefile exclusions.txt -oA 01-discovery

# 2. Triage common TCP ports with a safety ceiling
nmap -sT -Pn --top-ports 1000 --open --reason \
  --max-rate 100 -iL live-hosts.txt -oA 02-tcp-triage

# 3. Run service detection only on discovered ports
nmap -sV --version-intensity 5 -p 22,80,443 \
  192.0.2.10 -oA 03-services-192.0.2.10

# 4. Apply a reviewed script set to the relevant services
nmap -sC -sV -p 22,80,443 \
  192.0.2.10 -oA 04-nse-192.0.2.10
```

Do not generate `live-hosts.txt` from a public or mixed-scope dataset without
review. At every stage, compare output with the approved scope and stop if
unexpected infrastructure appears.

## Common Mistakes

| Mistake | Better practice |
|---|---|
| Treating `-Pn` as proof a host is up | Describe it as skipped discovery and validate reachability separately |
| Reporting the port-name guess as a product | Use `-sV` and manually confirm the protocol/banner |
| Running `-A` everywhere | Select only the detection features needed for the test case |
| Using `-p-` with an unbounded rate | Apply an approved `--max-rate`, timeout, and target batch size |
| Running broad NSE categories blindly | Review named scripts, arguments, categories, and side effects first |
| Parsing terminal output | Preserve XML and parse it with a maintained library |
| Assuming no response means no host | Consider filtering, loss, discovery probes, and privilege level |

## Verification Notes

The repository smoke test downloads the official Nmap 7.991 x86-64 RPM from
Nmap.org inside an ephemeral Fedora container, verifies the SHA-256 published
by Nmap.org, extracts it without installing it on the host, checks version/help
output, and removes the container image pulled by the test. No network target is
scanned, so behavioural accuracy still requires an isolated authorized lab.

## Official References

- [Nmap download page](https://nmap.org/download)
- [Nmap Reference Guide](https://nmap.org/book/man.html)
- [Official installation guide](https://nmap.org/book/install.html)
- [Nmap Scripting Engine documentation](https://nmap.org/nsedoc/)
- [Nmap changelog](https://nmap.org/changelog.html)

---

[← Main index](../README.md) · [Tool testing policy](../docs/TOOL-TESTING.md) · [Safe use](../docs/SAFE_USE.md)
