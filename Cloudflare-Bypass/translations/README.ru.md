<a id="-cloudflare-bypass-cheatsheet"></a>
# ☁️ Справочник по обходу Cloudflare

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../README.md)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
   ██████╗██╗      ██████╗ ██╗   ██╗██████╗ ███████╗██╗      █████╗ ██████╗ ███████╗
  ██╔════╝██║     ██╔═══██╗██║   ██║██╔══██╗██╔════╝██║     ██╔══██╗██╔══██╗██╔════╝
  ██║     ██║     ██║   ██║██║   ██║██║  ██║█████╗  ██║     ███████║██████╔╝█████╗  
  ██║     ██║     ██║   ██║██║   ██║██║  ██║██╔══╝  ██║     ██╔══██║██╔══██╗██╔══╝  
  ╚██████╗███████╗╚██████╔╝╚██████╔╝██████╔╝██║     ███████╗██║  ██║██║  ██║███████╗
   ╚═════╝╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝ ╚═╝     ╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝
                    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
                    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
                    ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
                    ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
                    ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
                    ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
```

---

<a id="-detect-cloudflare"></a>
## 🎯 Обнаружение Cloudflare

> **Уточнение к оригиналу.** Заголовки и IP-диапазоны — признаки использования Cloudflare, а не подтверждение адреса исходного сервера (origin). В [официальном списке](https://www.cloudflare.com/ips/) диапазон `104.16.0.0/12` заменён на `104.16.0.0/13` и `104.24.0.0/14`; ранний пример ниже устарел. Исходные значения сохранены.

<a id="headers"></a>
### Заголовки
```bash
curl -I https://target.com

# Ищите следующие заголовки:
cf-ray: XXXXX-YYY
server: cloudflare
cf-cache-status: DYNAMIC
cf-request-id: XXXX
```

<a id="dns"></a>
### DNS
```bash
dig target.com

# Диапазоны IP-адресов Cloudflare:
# 104.16.0.0/12
# 172.64.0.0/13
# 131.0.72.0/22
# 173.245.48.0/20
# 103.21.244.0/22
# 103.22.200.0/22
# 103.31.4.0/22
```

---

<a id="-find-origin-ip"></a>
## 🌐 Поиск IP-адреса исходного сервера

> **Уточнение.** История DNS, сертификаты, favicon, почтовые заголовки и WHOIS дают кандидатов для проверки, а не доказательство того, что найден действующий веб-сервер origin. Почтовая и веб-инфраструктура могут различаться. Синтаксис запросов и работоспособность исторических сервисов зависят от их версии и доступности.

<a id="1-dns-history-tools"></a>
### 1. Инструменты истории DNS

| Сервис | URL |
|---------|-----|
| SecurityTrails | https://securitytrails.com |
| ViewDNS | https://viewdns.info/iphistory |
| DNSDumpster | https://dnsdumpster.com |
| CrimeFlare | http://www.crimeflare.org:82/cfs.html |
| CloudFlair | Инструмент на GitHub |

```bash
# С помощью CloudFlair
python3 cloudflair.py target.com

# Bypass-Firewalls-by-DNS-History
./bypass-firewalls-by-DNS-history.sh target.com
```

<a id="2-subdomain-discovery"></a>
### 2. Поиск поддоменов
```bash
# Некоторые поддомены могут раскрывать адрес исходного сервера
subfinder -d target.com | httpx -ip

# Типичные поддомены с IP исходного сервера:
mail.target.com
ftp.target.com
cpanel.target.com
webmail.target.com
direct.target.com
origin.target.com
server.target.com
api.target.com
stage.target.com
dev.target.com
old.target.com
```

<a id="3-ssl-certificate-search"></a>
### 3. Поиск по SSL-сертификатам
```bash
# Поиск в Censys.io
# Запрос: parsed.names: target.com AND NOT ip: 104.*

# Журналы Certificate Transparency
curl -s "https://crt.sh/?q=%.target.com&output=json" | jq -r '.[].common_name' | sort -u

# Поиск в Shodan
shodan search "ssl.cert.subject.cn:target.com"
```

<a id="4-email-analysis"></a>
### 4. Анализ электронной почты
```bash
# Отправьте письмо на несуществующий адрес
# Проверьте заголовки уведомления о недоставке

# Либо подпишитесь на рассылку
# Проверьте заголовки Received: на наличие IP исходного сервера

# Ищите в заголовках:
Received: from [ORIGIN_IP]
X-Originating-IP: [IP]
X-PM-Message-Id: (sometimes contains origin)
```

<a id="5-favicon-hash-shodan"></a>
### 5. Хеш favicon (Shodan)
```python
import mmh3
import requests
import codecs

# Вычислите хеш значка сайта (favicon)
response = requests.get('https://target.com/favicon.ico')
favicon = codecs.encode(response.content, 'base64')
hash = mmh3.hash(favicon)
print(f"Favicon hash: {hash}")

# Поиск в Shodan: http.favicon.hash:{hash}
```

```bash
# CLI Shodan
shodan search "http.favicon.hash:HASH_HERE"
```

<a id="6-historical-whois"></a>
### 6. История WHOIS
```bash
# Проверьте историю WHOIS на наличие прежних IP-адресов
# whoxy.com
# domaintools.com
```

<a id="7-website-source-code"></a>
### 7. Исходный код сайта
```bash
# Проверьте явно указанные IP-адреса в:
# - файлах JavaScript
# - комментариях HTML
# - конечных точках API
# - сообщениях об ошибках

curl -s https://target.com | grep -oE '[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+'

# Проверьте JS-файлы
curl -s https://target.com/main.js | grep -oE 'https?://[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+'
```

<a id="8-mx-records"></a>
### 8. Записи MX
```bash
dig MX target.com

# Почтовый сервер может использовать тот же IP, что и исходный сервер
nslookup mail.target.com
```

<a id="9-spf-records"></a>
### 9. Записи SPF
```bash
dig TXT target.com | grep spf

# Запись SPF может содержать IP исходного сервера
# v=spf1 ip4:1.2.3.4 include:_spf.google.com ~all
```

---

<a id="-direct-connection"></a>
## 🔥 Прямое подключение

> **Уточнение.** В HTTPS заголовок Host не задаёт TLS SNI: [curl берёт имя SNI из URL](https://everything.curl.dev/usingcurl/connections/name.html). Поэтому запрос к IP с подменённым Host может попасть не на тот виртуальный сервер; `-k` отключает проверку сертификата. Совпадение содержимого — дополнительный признак, а не окончательное доказательство. Доступ по IP зависит от защиты origin: [Authenticated Origin Pulls](https://developers.cloudflare.com/ssl/origin-configuration/authenticated-origin-pull/) и сетевые ограничения могут препятствовать прямым запросам.

<a id="test-found-ip"></a>
### Проверка найденного IP-адреса
```bash
# Когда найден предполагаемый IP исходного сервера:
curl -k -H "Host: target.com" https://ORIGIN_IP/

# Либо измените /etc/hosts
echo "ORIGIN_IP target.com" >> /etc/hosts

# Затем обращайтесь к домену обычным способом
curl https://target.com
```

<a id="verify-origin"></a>
### Проверка исходного сервера
```bash
# Сравните содержимое ответов
diff <(curl -s https://target.com) <(curl -sk -H "Host: target.com" https://ORIGIN_IP/)

# Проверьте SSL-сертификат
openssl s_client -connect ORIGIN_IP:443 -servername target.com
```

---

<a id="-cloudflare-waf-bypass"></a>
## 🛡️ Обход WAF Cloudflare

> **Уточнение.** Подмена перечисленных заголовков не гарантирует обход ограничения частоты: [Cloudflare формирует клиентские IP-заголовки](https://developers.cloudflare.com/fundamentals/reference/http-headers/) с учётом маршрута запроса и доверия к прокси. Изменение User-Agent или автоматизация браузера также не гарантируют обход. Для Turnstile обязательна [серверная проверка через Siteverify](https://developers.cloudflare.com/turnstile/get-started/server-side-validation/); запуск инструмента сам по себе её не отменяет. Команды оригинала сохранены.

<a id="rate-limiting-bypass"></a>
### Обход ограничения частоты запросов
```http
# Меняйте значения этих заголовков
X-Forwarded-For: RANDOM_IP
CF-Connecting-IP: RANDOM_IP
True-Client-IP: RANDOM_IP
X-Real-IP: RANDOM_IP
```

<a id="bot-protection-bypass"></a>
### Обход защиты от ботов
```bash
# Используйте User-Agent настоящего браузера
curl -A "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36" https://target.com

# Используйте curl-impersonate
curl_chrome116 https://target.com
```

<a id="turnstilechallenge-bypass"></a>
### Обход Turnstile и проверок браузера
```bash
# Используйте автоматизацию браузера
# - Puppeteer со stealth-плагином
# - Playwright
# - undetected-chromedriver

# FlareSolverr в Docker
docker run -p 8191:8191 flaresolverr/flaresolverr
```

---

<a id="-tools"></a>
## 🛠️ Инструменты

| Инструмент | Назначение |
|------|---------|
| **CloudFlair** | Поиск исходного сервера через Censys |
| **CrimeFlare** | База IP-адресов исходных серверов |
| **Bypass-Firewalls-by-DNS-History** | Проверка истории DNS |
| **FlareSolverr** | Обработка проверок браузера |
| **curl-impersonate** | Воспроизведение TLS-отпечатка браузера |
| **CloudBunny** | Инструмент обхода Cloudflare |

<a id="cloudflair-usage"></a>
### Использование CloudFlair
```bash
git clone https://github.com/christophetd/CloudFlair
cd CloudFlair
pip install -r requirements.txt

# Задайте учётные данные API Censys
export CENSYS_API_ID=YOUR_ID
export CENSYS_API_SECRET=YOUR_SECRET

python cloudflair.py target.com
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="origin-ip-checklist"></a>
### Список проверок для поиска IP исходного сервера
```markdown
- [ ] История DNS (SecurityTrails, ViewDNS)
- [ ] Поддомены (mail, ftp, cpanel, dev)
- [ ] SSL-сертификаты (Censys, crt.sh)
- [ ] Shodan (хеш favicon, SSL)
- [ ] Заголовки электронной почты
- [ ] Записи MX/SPF
- [ ] Анализ исходного кода
- [ ] История WHOIS
- [ ] Сравнение диапазонов IP (адрес вне диапазонов Cloudflare)
```

<a id="cloudflare-ip-ranges"></a>
### Диапазоны IP-адресов Cloudflare
```
173.245.48.0/20
103.21.244.0/22
103.22.200.0/22
103.31.4.0/22
141.101.64.0/18
108.162.192.0/18
190.93.240.0/20
188.114.96.0/20
197.234.240.0/22
198.41.128.0/17
162.158.0.0/15
104.16.0.0/13
104.24.0.0/14
172.64.0.0/13
131.0.72.0/22
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [Диапазоны IP-адресов Cloudflare](https://www.cloudflare.com/ips/)
- [Инструмент CloudFlair](https://github.com/christophetd/CloudFlair)
- [CrimeFlare](http://www.crimeflare.org:82/cfs.html)
- [FlareSolverr](https://github.com/FlareSolverr/FlareSolverr)

---

<p align="center">
  <b>☁️ Найдите исходный сервер!</b><br>
  <i>Только для проверок с разрешения владельца!</i>
</p>
