# 🌐 OSINT de domínios e IP

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Domain-IP-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Domain-IP-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

> **Notas de precisão:** RDAP é o sucessor do protocolo WHOIS; consultar também os serviços RDAP para obter dados de registo atuais ([ICANN](https://www.icann.org/rdap)). Uma consulta DNS `ANY` não garante a devolução de todos os registos; o servidor pode dar uma resposta mínima ([RFC 8482](https://www.rfc-editor.org/rfc/rfc8482.html)). Geolocalização de IP é um indício de localização da infraestrutura, não uma prova da localização física de uma pessoa. As consultas de transferência de zona interagem diretamente com o servidor DNS.

---

## 🔍 Ferramentas de reconhecimento de domínios

| Ferramenta | URL | Finalidade |
|------|-----|---------|
| **WHOIS** | who.is | Registo do domínio |
| **SecurityTrails** | securitytrails.com | Histórico DNS |
| **ViewDNS** | viewdns.info | Ferramentas DNS |
| **BuiltWith** | builtwith.com | Conjunto de tecnologias |
| **Wappalyzer** | wappalyzer.com | Identificação de tecnologias |

---

## 🛠️ Comandos

### WHOIS
```bash
whois target.com
whois 1.2.3.4
```

### Enumeração DNS
```bash
# Registos DNS
dig target.com ANY
dig target.com MX
dig target.com TXT
nslookup -type=any target.com

# Transferência de zona
dig axfr @ns1.target.com target.com
host -t axfr target.com ns1.target.com
```

### Descoberta de subdomínios
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

| Ferramenta | URL | Finalidade |
|------|-----|---------|
| **Shodan** | shodan.io | Pesquisa de dispositivos |
| **Censys** | censys.io | Análise da Internet |
| **IPinfo** | ipinfo.io | Geolocalização de IP |
| **AbuseIPDB** | abuseipdb.com | Reputação de IP |

### CLI do Shodan
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

## 📋 Lista de verificação de domínios

```markdown
□ Registos WHOIS
□ Registos DNS (A, MX, TXT, NS)
□ Subdomínios
□ Histórico DNS (SecurityTrails)
□ Conjunto de tecnologias
□ Informações do certificado SSL
□ Intervalos de IP
□ Pesquisa inversa de IP
□ Informação sobre o ASN (número de sistema autónomo)
```

---

**Voltar a OSINT:** [🔍 Visão geral de OSINT](README.pt.md)
