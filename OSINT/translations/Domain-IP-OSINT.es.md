# 🌐 OSINT de dominios e IP

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Domain-IP-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Domain-IP-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

> **Aclaración:** Una consulta DNS `ANY` no garantiza obtener todos los registros. Una transferencia de zona solo devuelve datos si el servidor la permite. La geolocalización IP es aproximada y no identifica por sí sola una dirección física.


---

## 🔍 Herramientas de reconocimiento de dominios

| Herramienta | URL | Finalidad |
|------|-----|---------|
| **WHOIS** | who.is | Registro de dominios |
| **SecurityTrails** | securitytrails.com | Historial DNS |
| **ViewDNS** | viewdns.info | Herramientas DNS |
| **BuiltWith** | builtwith.com | Conjunto de tecnologías |
| **Wappalyzer** | wappalyzer.com | Detección de tecnologías |

---

## 🛠️ Comandos

### WHOIS
```bash
whois target.com
whois 1.2.3.4
```

### Enumeración DNS
```bash
# Registros DNS
dig target.com ANY
dig target.com MX
dig target.com TXT
nslookup -type=any target.com

# Transferencia de zona
dig axfr @ns1.target.com target.com
host -t axfr target.com ns1.target.com
```

### Descubrimiento de subdominios
```bash
# Subfinder
subfinder -d target.com -o subs.txt

# Amass
amass enum -d target.com -o subs.txt

# Assetfinder
assetfinder target.com
```

---

## 🔎 OSINT de IP

| Herramienta | URL | Finalidad |
|------|-----|---------|
| **Shodan** | shodan.io | Búsqueda de dispositivos |
| **Censys** | censys.io | Exploración de Internet |
| **IPinfo** | ipinfo.io | Geolocalización de IP |
| **AbuseIPDB** | abuseipdb.com | Reputación de IP |

### Shodan CLI
```bash
shodan host 1.2.3.4
shodan search "hostname:target.com"
shodan domain target.com
```

### Consulta de IP
```bash
curl ipinfo.io/1.2.3.4
curl ip-api.com/json/1.2.3.4
whois 1.2.3.4
```

---

## 📋 Lista de comprobación de dominios

```markdown
□ Registros WHOIS
□ Registros DNS (A, MX, TXT, NS)
□ Subdominios
□ Historial DNS (SecurityTrails)
□ Conjunto de tecnologías
□ Información de certificados SSL
□ Rangos de IP
□ Consulta inversa de IP
□ Información del número de sistema autónomo (ASN)
```

---

**Volver a OSINT:** [🔍 Índice de OSINT](./README.es.md)
