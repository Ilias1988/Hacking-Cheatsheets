# 🌐 Amass – kartlegging av angrepsflaten

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

> **Versjonsmerknad:** Originalen blander eksempler på moduser, flagg og konfigurasjon som må kontrolleres mot den konkrete Amass-hovedversjonen. Antall kilder, API-vilkår og graf-/utdataoppsett er ikke aktuelle garantier. Passiv innsamling og aktiv DNS-kartlegging har ulike kontaktmønstre; ordet «safe» i arbeidsflyten er ikke en generell garanti. ASN er et autonomt systemnummer, og CIDR beskriver IP-prefikser. Se [Amass-prosjektet](https://github.com/owasp-amass/amass). De opprinnelige eksemplene er beholdt.

```
   █████╗ ███╗   ███╗ █████╗ ███████╗███████╗
  ██╔══██╗████╗ ████║██╔══██╗██╔════╝██╔════╝
  ███████║██╔████╔██║███████║███████╗███████╗
  ██╔══██║██║╚██╔╝██║██╔══██║╚════██║╚════██║
  ██║  ██║██║ ╚═╝ ██║██║  ██║███████║███████║
  ╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚══════╝╚══════╝
         Grundig kartlegging av angrepsflaten
```

<p align="center">
  <img src="https://img.shields.io/badge/Amass-OWASP-blue?style=for-the-badge" alt="Amass">
  <img src="https://img.shields.io/badge/Subdomain-Discovery-green?style=for-the-badge" alt="Subdomain">
  <img src="https://img.shields.io/badge/ASN-Mapping-red?style=for-the-badge" alt="ASN"><br>
  <a href="README.it.md">🇮🇹 Versione Italiana</a> | <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 📋 Innholdsfortegnelse

- [Hva er Amass](#-hva-er-amass)
- [Installasjon](#-installasjon)
- [Kartleggingsmodus](#-kartleggingsmodus)
- [Intel-modus](#-intel-modus)
- [DNS-modus](#-dns-modus)
- [Konfigurasjon](#-konfigurasjon)
- [Hurtigreferanse](#-hurtigreferanse)

---

## 🎯 Hva er Amass

**Amass** er et OWASP-prosjekt for nettverkskartlegging og oppdagelse av eksterne ressurser:

- 🔍 **Kartlegging av underdomener** – et omfattende verktøy
- 🏢 **Oppdagelse av ASN** – finn organisasjonens IP-områder
- 📡 **DNS-etterretning** – passiv og aktiv rekognosering
- 🗺️ **Kartlegging av angrepsflaten** – visualiser infrastrukturen

### Hvorfor Amass?

| Egenskap | Fordel |
|---------|---------|
| 55+ datakilder | Omfattende resultater |
| Passivt og aktivt | Velg støynivå |
| ASN-/organisasjonsdata | Finn mindre synlig infrastruktur |
| Grafdatabase | Visualiser forbindelser |

---

## 🚀 Installasjon

```bash
# Installasjon med Go
go install -v github.com/owasp-amass/amass/v4/...@master

# Kali Linux
sudo apt install amass

# Docker
docker pull caffix/amass

# Kontroller
amass -version
```

---

## 🔍 Kartleggingsmodus

### Grunnleggende kartlegging av underdomener

```bash
# Passiv kartlegging (ingen direkte kontakt med målet)
amass enum -passive -d example.com

# Aktiv kartlegging (utprøving av DNS-navn)
amass enum -active -d example.com

# Utprøving med ordliste
amass enum -active -brute -d example.com -w wordlist.txt
```

### Utdataalternativer

```bash
# Lagre i fil
amass enum -passive -d example.com -o subdomains.txt

# JSON-utdata
amass enum -passive -d example.com -json output.json

# Lagre i katalog
amass enum -passive -d example.com -dir ./amass_output/
```

### Flere mål

```bash
# Flere domener
amass enum -passive -d example.com -d target.com

# Fra fil
amass enum -passive -df domains.txt -o results.txt
```

### Avansert kartlegging

```bash
# Angi tidsavbrudd (minutter)
amass enum -passive -d example.com -timeout 30

# Begrens kildene
amass enum -passive -d example.com -src -include CertSpotter,Crtsh,HackerTarget

# Utelat kilder
amass enum -passive -d example.com -exclude Google,Bing

# Vis datakilder
amass enum -passive -d example.com -src
```

### Rekursiv kartlegging

```bash
# Aktiver rekursivt søk
amass enum -passive -d example.com -r

# Angi rekursjonsdybde
amass enum -passive -d example.com -max-depth 3
```

---

## 🏢 Intel-modus

> Intel-modus finner rotdomener, ASN-er og CIDR-områder knyttet til organisasjoner.

### Finne organisasjonens ressurser

```bash
# Etter organisasjonsnavn
amass intel -org "Tesla Inc"
amass intel -org "Microsoft"

# Omvendt WHOIS
amass intel -whois -d example.com
```

### Oppdagelse av ASN

```bash
# Finn ASN for domenet
amass intel -d example.com

# Kartlegg etter ASN
amass intel -asn 13335
amass intel -active -asn 13335

# Flere ASN-er
amass intel -asn 13335,15169
```

### CIDR-område

```bash
# Finn ressurser i CIDR-området
amass intel -active -cidr 192.168.1.0/24

# Flere CIDR-områder
amass intel -cidr 192.168.1.0/24 -cidr 10.0.0.0/8
```

### IP-område

```bash
# IP-adresseområde
amass intel -active -ip 192.168.1.1-254
```

---

## 📡 DNS-modus

### DNS-spørringer

```bash
# Grunnleggende DNS-oppslag
amass dns -d example.com

# Bestemte oppføringstyper
amass dns -d example.com -t A,AAAA,MX,NS,TXT
```

### Slå opp underdomener

```bash
# Slå opp underdomenene som ble funnet
amass dns -df subdomains.txt
```

---

<a id="-konfigurasjon"></a>
## ⚙️ Konfigurasjon

### Plassering av konfigurasjonsfil

```bash
# Standardplassering
~/.config/amass/config.yaml

# Angi egendefinert konfigurasjon
amass enum -config ./my_config.yaml -d example.com
```

### Eksempel på konfigurasjonsfil

```yaml
# config.yaml
scope:
  domains:
    - example.com
    - target.com

resolvers:
  - 8.8.8.8
  - 8.8.4.4
  - 1.1.1.1

options:
  log: /tmp/amass.log
  max_dns_queries: 10000

data_sources:
  Crtsh:
  - 
  SecurityTrails:
  - credentials:
      apikey: YOUR_API_KEY
  Shodan:
  - credentials:
      apikey: YOUR_SHODAN_KEY
  VirusTotal:
  - credentials:
      apikey: YOUR_VT_KEY
```

### Datakilder med API-nøkler

| Kilde | Gratisnivå | API kreves |
|--------|-----------|--------------|
| Crt.sh | ✅ | Nei |
| SecurityTrails | ✅ | Ja |
| Shodan | ✅ | Ja |
| VirusTotal | ✅ | Ja |
| Censys | ✅ | Ja |
| PassiveTotal | Begrenset | Ja |
| BinaryEdge | Begrenset | Ja |

---

## 📊 Hurtigreferanse

### Kartleggingskommandoer

| Kommando | Beskrivelse |
|---------|-------------|
| `amass enum -passive -d domain.com` | Passiv kartlegging av underdomener |
| `amass enum -active -d domain.com` | Aktiv DNS-kartlegging |
| `amass enum -active -brute -d domain.com` | Utprøving av underdomener |
| `amass enum -d domain.com -o out.txt` | Lagre i fil |
| `amass enum -d domain.com -json out.json` | JSON-utdata |

### Intel-kommandoer

| Kommando | Beskrivelse |
|---------|-------------|
| `amass intel -org "Company"` | Finn ressurser etter organisasjonsnavn |
| `amass intel -asn 12345` | Kartlegg etter ASN |
| `amass intel -cidr 10.0.0.0/8` | Kartlegg CIDR-område |
| `amass intel -whois -d domain.com` | Omvendt WHOIS |

### Viktige flagg

| Flagg | Beskrivelse |
|------|-------------|
| `-d` | Måldomene |
| `-df` | Domener fra fil |
| `-o` | Utdatafil |
| `-passive` | Bare passivt (uten DNS) |
| `-active` | Aktiver aktiv DNS |
| `-brute` | Utprøving av DNS-navn |
| `-src` | Vis datakilde |
| `-timeout` | Tidsavbrudd i minutter |

---

## 🎯 Arbeidsflyt for bug bounty

```bash
# 1. Passiv kartlegging (omtalt som trygt i originalen)
amass enum -passive -d target.com -o passive_subs.txt

# 2. Aktiv kartlegging med utprøving
amass enum -active -brute -d target.com -w dns_wordlist.txt -o active_subs.txt

# 3. Finn organisasjonens ressurser
amass intel -org "Target Inc" -o org_domains.txt

# 4. Slå sammen resultater
cat passive_subs.txt active_subs.txt | sort -u > all_subdomains.txt

# 5. Finn tilgjengelige verter
cat all_subdomains.txt | httpx -silent > live_hosts.txt
```

---

## 📚 Ressurser

- [OWASP Amass GitHub](https://github.com/owasp-amass/amass)
- [Amass User Guide](https://github.com/owasp-amass/amass/blob/master/doc/user_guide.md)

### Relaterte hurtigreferanser
- [Bug Bounty Methodology](../../Bug-Bounty-Methodology/translations/README.nb.md)
- [Subfinder](../../Subfinder/translations/README.nb.md)
- [httpx](../../httpx/translations/README.nb.md)

---

<p align="center">
  <b>🌐 Kartlegg angrepsflaten!</b><br>
  <i>Amass – omfattende kartlegging av underdomener</i>
</p>
