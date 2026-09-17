# 🛡️ Network Defense Field Guide

> **Last verified:** 2026-09-17  
> **Checked against:** Current CISA, Zeek, Suricata, Wireshark, and MITRE ATT&CK guidance  
> **Scope:** Defensive monitoring and authorized validation

Network defense combines architecture, telemetry, detection, and repeatable response. A firewall rule without visibility and ownership is not a durable control.

## Defensive Priorities

1. Maintain an authoritative asset and network inventory.
2. Segment users, servers, management, backups, labs, and internet-facing services.
3. Deny unnecessary east-west and outbound traffic.
4. Centralize DNS, DHCP, VPN, firewall, proxy, and authentication logs.
5. Baseline expected protocols and investigate deviations.
6. Test controls from both sides of each trust boundary.

## Segmentation Checklist

- [ ] Administrative interfaces are reachable only from managed jump hosts.
- [ ] Domain controllers are not general-purpose file or web servers.
- [ ] Workstations cannot initiate SMB, RDP, WinRM, or SSH to peer workstations unless required.
- [ ] Backup infrastructure has separate credentials and restricted inbound access.
- [ ] Guest, IoT, lab, and unmanaged devices use isolated networks.
- [ ] Egress filtering restricts uncommon ports, direct DNS, and unauthorized tunnels.
- [ ] IPv6 receives the same policy and visibility as IPv4.

## Validation Commands

Run only from systems included in the assessment scope.

```bash
# Confirm the route and exposed TCP services
traceroute 192.0.2.10
nmap -sT -Pn -p 22,53,80,443,445,3389 192.0.2.10

# Validate DNS policy
dig @192.0.2.53 example.org

# Capture a narrow evidence set
sudo tcpdump -i eth0 -nn 'host 192.0.2.10 and (port 53 or port 443)'
```

Successful blocking should be visible in firewall logs and attributable to the tested source, destination, rule, and change ticket.

## Detection Coverage

| Activity | Useful telemetry | Example signal |
|---|---|---|
| Reconnaissance | Firewall, Zeek, NDR | One source contacting many ports or hosts |
| Password spraying | VPN, IdP, AD, RADIUS | Many users with a small password set |
| Lateral movement | SMB, RDP, WinRM, SSH | New source/destination administrative pairs |
| DNS tunneling | Resolver logs, Zeek DNS | Long or high-entropy labels and unusual volume |
| Command and control | Proxy, TLS, NetFlow | Periodic low-volume connections or rare destinations |
| Data staging/exfiltration | DLP, proxy, NetFlow | Unusual archive creation followed by outbound volume |

## Zeek and Suricata Quick Checks

```bash
# Inspect Zeek connection and DNS logs
zeek-cut id.orig_h id.resp_h id.resp_p proto service < conn.log | head
zeek-cut query qtype_name answers < dns.log | head

# Validate Suricata configuration and rules
suricata -T -c /etc/suricata/suricata.yaml

# Review high-severity EVE alerts
jq 'select(.event_type == "alert" and .alert.severity <= 2)' eve.json
```

## Incident Pivot

When a signal is credible:

1. Preserve the original alert and raw telemetry.
2. Identify affected identities, assets, and time range.
3. Search for the same indicator across DNS, proxy, endpoint, and identity logs.
4. Contain with the narrowest effective control.
5. Confirm the control and watch for alternate channels.
6. Record gaps and convert them into engineering work.

## References

- [CISA Cross-Sector Cybersecurity Performance Goals](https://www.cisa.gov/cross-sector-cybersecurity-performance-goals)
- [Zeek documentation](https://docs.zeek.org/)
- [Suricata documentation](https://docs.suricata.io/)
- [MITRE ATT&CK Network content](https://attack.mitre.org/)

---

**Related:** [Hardening](./Hardening.md) · [SIEM Detection](./SIEM-Detection.md) · [Threat Hunting](./Threat-Hunting.md)
