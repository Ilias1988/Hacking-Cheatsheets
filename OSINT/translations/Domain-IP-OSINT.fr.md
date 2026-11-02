# 🌐 OSINT sur les domaines et adresses IP

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Domain-IP-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Domain-IP-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 🔍 Outils de reconnaissance des domaines

| Outil | URL | Utilité |
|------|-----|---------|
| **WHOIS** | who.is | Enregistrement des domaines |
| **SecurityTrails** | securitytrails.com | Historique DNS |
| **ViewDNS** | viewdns.info | Outils DNS |
| **BuiltWith** | builtwith.com | Technologies utilisées |
| **Wappalyzer** | wappalyzer.com | Détection des technologies |

---

## 🛠️ Commandes

### WHOIS
```bash
whois target.com
whois 1.2.3.4
```

### Énumération DNS

> **Précision :** une requête `ANY` ne garantit pas l’ensemble des enregistrements DNS ; un serveur peut limiter sa réponse. Voir la [RFC 8482](https://www.rfc-editor.org/rfc/rfc8482.html).
```bash
# Enregistrements DNS
dig target.com ANY
dig target.com MX
dig target.com TXT
nslookup -type=any target.com

# Transfert de zone
dig axfr @ns1.target.com target.com
host -t axfr target.com ns1.target.com
```

### Découverte de sous-domaines
```bash
# Subfinder
subfinder -d target.com -o subs.txt

# Amass
amass enum -d target.com -o subs.txt

# Assetfinder
assetfinder target.com
```

---

## 🔎 OSINT sur les adresses IP

| Outil | URL | Utilité |
|------|-----|---------|
| **Shodan** | shodan.io | Recherche d’appareils |
| **Censys** | censys.io | Analyse des services exposés sur Internet |
| **IPinfo** | ipinfo.io | Géolocalisation des adresses IP |
| **AbuseIPDB** | abuseipdb.com | Réputation des adresses IP |

### Interface en ligne de commande de Shodan
```bash
shodan host 1.2.3.4
shodan search "hostname:target.com"
shodan domain target.com
```

### Recherche d’informations sur une adresse IP
```bash
curl ipinfo.io/1.2.3.4
curl ip-api.com/json/1.2.3.4
whois 1.2.3.4
```

---

## 📋 Liste de contrôle des domaines

```markdown
□ Données WHOIS
□ Enregistrements DNS (A, MX, TXT, NS)
□ Sous-domaines
□ Historique DNS (SecurityTrails)
□ Technologies utilisées
□ Informations sur les certificats SSL
□ Plages d’adresses IP
□ Recherche inversée d’adresses IP
□ Informations sur le numéro de système autonome (ASN)
```

---

**Retour à l’OSINT :** [🔍 Vue d’ensemble de l’OSINT](./README.fr.md)
