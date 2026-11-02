# 📧 Шпаргалка по OSINT для электронной почты

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Email-OSINT.md) • [🇷🇺 Русский раздел](../../README.ru.md)

<p align="center">
  <a href="Email-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Email-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

```
███████╗███╗   ███╗ █████╗ ██╗██╗     
██╔════╝████╗ ████║██╔══██╗██║██║     
█████╗  ██╔████╔██║███████║██║██║     
██╔══╝  ██║╚██╔╝██║██╔══██║██║██║     
███████╗██║ ╚═╝ ██║██║  ██║██║███████╗
╚══════╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚══════╝
```

---

## 🔍 Инструменты поиска адресов почты

| Инструмент | URL | Назначение |
| ------ | ----- | --------- |
| **Hunter.io** | hunter.io | Поиск корпоративных адресов почты |
| **Phonebook.cz** | phonebook.cz | Бесплатный поиск адресов почты |
| **Snov.io** | snov.io | Поиск адресов почты |
| **Clearbit Connect** | clearbit.com | Дополнение сведений об адресе почты |
| **RocketReach** | rocketreach.co | Поиск контактов |
| **Voila Norbert** | voilanorbert.com | Поиск по адресу почты |

---

## 🛠️ Команды сбора сведений об адресах почты

### theHarvester
```bash
# Сбор адресов электронной почты
theHarvester -d target.com -l 500 -b all
theHarvester -d target.com -b google,linkedin,bing
theHarvester -d target.com -b all -f emails.html
```

### Hunter.io из командной строки
```bash
# Запрос к API
curl "https://api.hunter.io/v2/domain-search?domain=target.com&api_key=YOUR_KEY"

# Проверка адреса почты
curl "https://api.hunter.io/v2/email-verifier?email=test@target.com&api_key=YOUR_KEY"
```

### h8mail (поиск в данных об утечках)
```bash
pip3 install h8mail
h8mail -t target@email.com
h8mail -t target@email.com -c h8mail_config.ini
```

### holehe (проверка регистрации)
```bash
pip3 install holehe
holehe target@email.com
holehe target@email.com --only-used
```

---

## 📧 Шаблоны адресов электронной почты

```bash
# Типичные корпоративные форматы
first.last@company.com     # john.smith@company.com
flast@company.com          # jsmith@company.com
firstl@company.com         # johns@company.com
first_last@company.com     # john_smith@company.com
first@company.com          # john@company.com
last.first@company.com     # smith.john@company.com
```

### Генератор вариантов адреса почты
```python
# Сформировать варианты адреса почты
names = ["john", "smith"]
domain = "company.com"

patterns = [
    f"{names[0]}.{names[1]}@{domain}",
    f"{names[0][0]}{names[1]}@{domain}",
    f"{names[0]}{names[1][0]}@{domain}",
    f"{names[0]}_{names[1]}@{domain}",
    f"{names[0]}@{domain}",
]
```

---

## 🔐 Проверка утечек

| Сервис | URL | Тип |
| --------- | ----- | ------ |
| **Have I Been Pwned** | haveibeenpwned.com | Бесплатно |
| **DeHashed** | dehashed.com | Платно |
| **LeakCheck** | leakcheck.io | Платно |
| **Snusbase** | snusbase.com | Платно |
| **IntelX** | intelx.io | Базовые функции бесплатно, расширенные — платно |

### API HIBP
```bash
curl -H "hibp-api-key: YOUR_KEY" \
  "https://haveibeenpwned.com/api/v3/breachedaccount/test@example.com"
```

---

## ✅ Проверка адреса электронной почты

### Способы проверки
```
1. Проверка MX — у домена есть почтовый сервер
2. Проверка через SMTP — предполагаемое существование почтового ящика
3. Проверка синтаксиса — корректный формат адреса
4. Проверка одноразовых адресов — адрес не временный
5. Проверка служебного адреса — адрес не общий (info@, admin@)
```

### Простая проверка через SMTP

> **Уточнение:** вывод «250 = адрес существует, 550 = не существует» в оригинале упрощён. Код 250 подтверждает принятие SMTP-команды, а 550 может означать отсутствие ящика, отсутствие доступа или отказ по политике. Такой ответ не доказывает существование либо отсутствие адреса; см. [RFC 5321, коды ответов](https://datatracker.ietf.org/doc/html/rfc5321#section-4.2.3).

```bash
# Проверить записи MX
nslookup -type=mx target.com
dig mx target.com

# Проверка через Telnet
telnet mail.target.com 25
HELO test.com
MAIL FROM:<test@test.com>
RCPT TO:<target@target.com>
# 250 = существует, 550 = не существует
```

---

## 📋 Контрольный список Email OSINT

```markdown
## Проверяемый адрес: [email@domain.com]

□ Проверить существование адреса почты (SMTP/Hunter)
□ Проверить утечки данных (HIBP)
□ Найти связанные учётные записи (holehe)
□ Поискать в Google/Bing
□ Проверить регистрации в социальных сетях
□ Найти открытые профили
□ Поискать на сайтах публикации текстовых фрагментов
□ Проверить профессиональные сети
```

---

**К разделу OSINT:** [🔍 Обзор OSINT](./README.ru.md)
