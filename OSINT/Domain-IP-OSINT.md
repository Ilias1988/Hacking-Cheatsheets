# 🌐 Domain & IP OSINT

---

## 🔍 Domain Reconnaissance Tools

| Tool | URL | Purpose |
|------|-----|---------|
| **WHOIS** | who.is | Domain registration |
| **SecurityTrails** | securitytrails.com | DNS history |
| **ViewDNS** | viewdns.info | DNS tools |
| **BuiltWith** | builtwith.com | Technology stack |
| **Wappalyzer** | wappalyzer.com | Tech detection |

---

## 🛠️ Commands

### WHOIS
```bash
whois target.com
whois 1.2.3.4
```

### DNS Enumeration
```bash
# DNS records
dig target.com ANY
dig target.com MX
dig target.com TXT
nslookup -type=any target.com

# Zone transfer
dig axfr @ns1.target.com target.com
host -t axfr target.com ns1.target.com
```

### Subdomain Discovery
```bash
# Subfinder
subfinder -d target.com -o subs.txt

# Amass
amass enum -d target.com -o subs.txt

# Assetfinder
assetfinder target.com
```

---

## 🔎 IP OSINT

| Tool | URL | Purpose |
|------|-----|---------|
| **Shodan** | shodan.io | Device search |
| **Censys** | censys.io | Internet scanning |
| **IPinfo** | ipinfo.io | IP geolocation |
| **AbuseIPDB** | abuseipdb.com | IP reputation |

### Shodan CLI
```bash
shodan host 1.2.3.4
shodan search "hostname:target.com"
shodan domain target.com
```

### IP Lookup
```bash
curl ipinfo.io/1.2.3.4
curl ip-api.com/json/1.2.3.4
whois 1.2.3.4
```

---

## 📋 Domain Checklist

```markdown
□ WHOIS records
□ DNS records (A, MX, TXT, NS)
□ Subdomains
□ Historical DNS (SecurityTrails)
□ Technology stack
□ SSL certificate info
□ IP ranges
□ Reverse IP lookup
□ ASN information
```

---

**Back to OSINT:** [🔍 OSINT Overview](./README.md)
