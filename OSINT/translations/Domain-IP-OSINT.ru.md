# 🌐 OSINT для доменов и IP-адресов

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Domain-IP-OSINT.md) • [🇷🇺 Русский раздел](../../README.ru.md)

<p align="center">
  <a href="Domain-IP-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Domain-IP-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

---

## 🔍 Инструменты сбора сведений о доменах

| Инструмент | URL | Назначение |
| ------ | ----- | --------- |
| **WHOIS** | who.is | Регистрация доменов |
| **SecurityTrails** | securitytrails.com | История DNS |
| **ViewDNS** | viewdns.info | Инструменты DNS |
| **BuiltWith** | builtwith.com | Технологический стек |
| **Wappalyzer** | wappalyzer.com | Определение технологий |

---

## 🛠️ Команды

### WHOIS
```bash
whois target.com
whois 1.2.3.4
```

### Сбор сведений DNS
```bash
# Записи DNS
dig target.com ANY
dig target.com MX
dig target.com TXT
nslookup -type=any target.com

# Передача зоны
dig axfr @ns1.target.com target.com
host -t axfr target.com ns1.target.com
```

### Поиск поддоменов
```bash
# Subfinder
subfinder -d target.com -o subs.txt

# Amass
amass enum -d target.com -o subs.txt

# Assetfinder
assetfinder target.com
```

---

## 🔎 OSINT для IP-адресов

| Инструмент | URL | Назначение |
| ------ | ----- | --------- |
| **Shodan** | shodan.io | Поиск устройств |
| **Censys** | censys.io | Сканирование интернета |
| **IPinfo** | ipinfo.io | Геолокация IP-адресов |
| **AbuseIPDB** | abuseipdb.com | Репутация IP-адресов |

### Командная строка Shodan
```bash
shodan host 1.2.3.4
shodan search "hostname:target.com"
shodan domain target.com
```

### Поиск сведений об IP-адресе
```bash
curl ipinfo.io/1.2.3.4
curl ip-api.com/json/1.2.3.4
whois 1.2.3.4
```

---

## 📋 Контрольный список для домена

```markdown
□ Записи WHOIS
□ Записи DNS (A, MX, TXT, NS)
□ Поддомены
□ История DNS (SecurityTrails)
□ Технологический стек
□ Сведения о сертификатах SSL
□ Диапазоны IP-адресов
□ Обратный поиск по IP-адресу
□ Сведения об автономной системе (ASN)
```

---

**К разделу OSINT:** [🔍 Обзор OSINT](./README.ru.md)
