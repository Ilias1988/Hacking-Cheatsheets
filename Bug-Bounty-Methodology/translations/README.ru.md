<a id="-bug-bounty-methodology"></a>
# 🐛 Методология Bug Bounty

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)
[🇬🇧 English](../README.md) • [🇷🇺 Русский раздел](../../README.ru.md)

> **Уточнения к оригиналу:** команды и пути шаблонов зависят от версии инструмента и наличия API-ключей. В [Subfinder](https://github.com/projectdiscovery/subfinder) -recursive выбирает источники, поддерживающие рекурсивные запросы, а не гарантирует полный рекурсивный обход DNS. Находки по ASN, сертификатам, перенаправлениям и сигнатурам — кандидаты: принадлежность ресурса, границы программы и последствия проверяются отдельно. Несколько имён промежуточных файлов различаются; фрагменты не являются одной готовой программой. Заголовок XSS-последовательности не означает, что показанный сбор URL уже подтверждает XSS.

> **Уточнение CORS:** утверждение оригинала «wildcard плюс credentials = Critical» некорректно: браузер блокирует доступ к ответу credentialed-запроса при Access-Control-Allow-Origin: *. Нужны проверка конкретной конфигурации и подтверждённые последствия; см. [MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/CORS/Errors/CORSNotSupportingCredentials).

> **Пояснение схемы:** этапы 1–6 — поиск поддоменов; ASN и IP; доступные узлы; сбор URL; поиск уязвимостей; подробное исследование каталогов, параметров и Git. Английская ASCII-схема и эмблема (Complete Bug Bounty Hunting Methodology — полная методология Bug Bounty) сохранены без изменения выравнивания.


[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗   ██╗ ██████╗     ██████╗  ██████╗ ██╗   ██╗███╗   ██╗████████╗██╗   ██╗
  ██╔══██╗██║   ██║██╔════╝     ██╔══██╗██╔═══██╗██║   ██║████╗  ██║╚══██╔══╝╚██╗ ██╔╝
  ██████╔╝██║   ██║██║  ███╗    ██████╔╝██║   ██║██║   ██║██╔██╗ ██║   ██║    ╚████╔╝ 
  ██╔══██╗██║   ██║██║   ██║    ██╔══██╗██║   ██║██║   ██║██║╚██╗██║   ██║     ╚██╔╝  
  ██████╔╝╚██████╔╝╚██████╔╝    ██████╔╝╚██████╔╝╚██████╔╝██║ ╚████║   ██║      ██║   
  ╚═════╝  ╚═════╝  ╚═════╝     ╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝   ╚═╝      ╚═╝   
            Complete Bug Bounty Hunting Methodology
```

<p align="center">
  <img src="https://img.shields.io/badge/Bug_Bounty-Methodology-red?style=for-the-badge" alt="Bug Bounty">
  <img src="https://img.shields.io/badge/Recon-blue?style=for-the-badge" alt="Recon">
  <img src="https://img.shields.io/badge/Vulnerability-green?style=for-the-badge" alt="Vuln">
</p>

---

<a id="-attack-flow-overview"></a>
## 🗺️ Обзор последовательности действий

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                      BUG BOUNTY HUNTING METHODOLOGY                         │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  PHASE 1: Subdomain Enum    PHASE 2: ASN & IP        PHASE 3: Live Hosts   │
│  ├── Subfinder              ├── ASN Mapping          ├── HTTPX Probing     │
│  ├── Amass                  ├── IP Harvesting        ├── Port Discovery    │
│  ├── crt.sh                 └── Shodan               └── Aquatone          │
│  └── Wayback                                                                │
│                                                                             │
│  PHASE 4: URL Collection    PHASE 5: Vuln Hunting    PHASE 6: Deep Dive    │
│  ├── Katana/Hakrawler       ├── XSS Testing          ├── Directory Brute   │
│  ├── GAU                    ├── LFI/RFI              ├── Parameter Fuzzing │
│  └── Parameter Extract      ├── SSRF/CORS            └── Git Exposure      │
│                             └── Open Redirect                               │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

<a id="-table-of-contents"></a>
## 📋 Содержание

1. [Этап 1: поиск поддоменов](#-phase-1-subdomain-enumeration)
2. [Этап 2: поиск ASN и IP-адресов](#-phase-2-asn--ip-discovery)
3. [Этап 3: поиск доступных узлов](#-phase-3-live-host-discovery)
4. [Этап 4: сбор URL](#-phase-4-url-collection)
5. [Этап 5: поиск уязвимостей](#-phase-5-vulnerability-hunting)
6. [Этап 6: поиск каталогов и файлов](#-phase-6-directory--file-discovery)
7. [Краткий справочник](#-quick-reference)
8. [Установка инструментов](#-tool-installation)

---

<a id="-phase-1-subdomain-enumeration"></a>
## 🔍 Этап 1: поиск поддоменов

> **Цель:** найти как можно больше поддоменов: чем шире поверхность атаки, тем больше возможностей обнаружить уязвимости.

<a id="automated-enumeration-tools"></a>
### Автоматизированный поиск

<a id="subfinder"></a>
#### Subfinder
```bash
# Быстрый поиск поддоменов по нескольким источникам
subfinder -d example.com -all -recursive -o subfinder.txt

# Пояснения к параметрам:
# -all: использовать все источники (бесплатные и платные)
# -recursive: рекурсивный поиск поддоменов (формулировка оригинала; см. уточнение)
# -o: выходной файл
```
> 💡 **Совет:** Subfinder — быстрый инструмент поиска. Используйте `-all` для более широкого набора источников.

<a id="assetfinder"></a>
#### Assetfinder
```bash
# Найти домены и поддомены, связанные с целью
assetfinder --subs-only example.com > assetfinder.txt

# Быстрый и простой вариант для первоначальной разведки
```
> 💡 **Совет:** Assetfinder работает быстро, но охват ограничен. Дополняйте его другими инструментами.

<a id="sublist3r"></a>
#### Sublist3r
```bash
# Поиск поддоменов методами OSINT
sublist3r -d example.com -e baidu,yahoo,google,bing,ask,netcraft,virustotal,threatcrowd,crtsh,passivedns -v -o sublist3r.txt

# -e: указать поисковые системы
# -v: подробный вывод
```

<a id="amass"></a>
#### Amass
```bash
# Подробное исследование поверхности атаки
amass enum -passive -d example.com | cut -d']' -f 2 | awk '{print $1}' | sort -u > amass.txt

# Пассивный режим — без прямого обращения к цели
# -active может дать больше результатов, но создаёт больше трафика
```
> ⚠️ **Примечание:** активный режим Amass создаёт заметный трафик. Начинайте с пассивного режима.

<a id="public-sources"></a>
### Открытые источники

<a id="certificate-transparency-crtsh"></a>
#### Прозрачность сертификатов (crt.sh)
```bash
# Найти поддомены в журналах сертификатов SSL
curl -s https://crt.sh\?q\=\example.com\&output\=json | jq -r '.[].name_value' | grep -Po '(\w+\.\w+\.\w+)$' > crtsh.txt
```
> 💡 **Зачем crt.sh?** Записи сертификатов SSL открыты и дают бесплатный источник сведений о поддоменах.

<a id="wayback-machine"></a>
#### Wayback Machine
```bash
# Поиск исторических поддоменов в веб-архивах
curl -s "http://web.archive.org/cdx/search/cdx?url=*.example.com/*&output=text&fl=original&collapse=urlkey" | sort | sed -e 's_https*://__' -e "s/\/.*//" -e 's/:.*//' -e 's/^www\.//' | sort -u > wayback.txt
```
> 💡 **Совет:** старые поддомены могут сохраняться, хотя о них забыли; их стоит проверить в согласованных границах.

<a id="subdomain-processing"></a>
### Обработка поддоменов

<a id="merge--deduplicate-results"></a>
#### Объединение результатов и удаление дубликатов
```bash
# Объединить файлы поддоменов и удалить дубликаты
cat *.txt | sort -u > final_subdomains.txt

# Подсчитать результаты
wc -l final_subdomains.txt
```

<a id="ffuf-subdomain-bruteforce"></a>
#### Перебор поддоменов с FFUF
```bash
# Перебор поддоменов по словарю
ffuf -u "https://FUZZ.example.com" -w wordlist.txt -mc 200,301,302

# -mc: учитывать указанные коды состояния
# Пример словаря: /usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt
```

<a id="subdomain-permutation-with-alterx"></a>
#### Генерация вариантов поддоменов с Alterx
```bash
# Сгенерировать варианты поддоменов и разрешить их имена
subfinder -d example.com | alterx | dnsx

# Дополнить домен типичными шаблонами
echo example.com | alterx -enrich | dnsx

# Использовать словарь для генерации вариантов
echo example.com | alterx -pp word=/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt | dnsx
```
> 💡 **Пример:** при обнаружении `dev.example.com` alterx генерирует варианты `dev1`, `dev2`, `dev-api` и другие.

---

<a id="-phase-2-asn--ip-discovery"></a>
## 🌐 Этап 2: поиск ASN и IP-адресов

> **Цель:** найти диапазоны IP-адресов и инфраструктуру, связанную с исследуемой организацией.

<a id="asn-mapping"></a>
### Определение диапазонов по ASN

```bash
# Найти IP-адреса, связанные с ASN домена
asnmap -d example.com | dnsx -silent -resp-only

# Найти ресурсы по названию организации
amass intel -org "organization_name"

# Найти ресурсы в диапазоне IP-адресов
amass intel -active -cidr 159.69.129.82/32

# Найти ресурсы по номеру ASN
amass intel -active -asn [asnno]
```
> 💡 **Зачем ASN?** С организацией могут быть связаны целые диапазоны IP-адресов, в которых можно найти дополнительные серверы.

<a id="ip-harvesting"></a>
### Сбор IP-адресов

<a id="virustotal"></a>
#### VirusTotal
```bash
# Извлечь IP-адреса из VirusTotal
curl -s "https://www.virustotal.com/vtapi/v2/domain/report?domain=example.com&apikey=[api-key]" | jq -r '.. | .ip_address? // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

<a id="alienvault-otx"></a>
#### AlienVault OTX
```bash
# Получить IP-адреса из AlienVault Open Threat Exchange
curl -s "https://otx.alienvault.com/api/v1/indicators/hostname/example.com/url_list?limit=500&page=1" | jq -r '.url_list[]?.result?.urlworker?.ip // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

<a id="urlscanio"></a>
#### URLScan.io
```bash
# Извлечь IP-адреса из URLScan.io
curl -s "https://urlscan.io/api/v1/search/?q=domain:example.com&size=10000" | jq -r '.results[]?.page?.ip // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

<a id="shodan-ssl-search"></a>
#### Поиск сертификатов SSL в Shodan
```bash
# Найти IP-адреса через поиск сертификатов SSL в Shodan
shodan search Ssl.cert.subject.CN:"example.com" 200 --fields ip_str | httpx -sc -title -server -td
```
> 💡 **Совет:** поиск сертификатов SSL в Shodan помогает найти серверы, использующие сертификат цели, включая малоизвестные узлы.

---

<a id="-phase-3-live-host-discovery"></a>
## ✅ Этап 3: поиск доступных узлов

> **Цель:** отфильтровать поддомены и выделить доступные узлы.

<a id="http-probing-with-httpx"></a>
### Проверка HTTP-сервисов с HTTPX

```bash
# Базовая проверка нескольких портов
cat subdomain.txt | httpx -ports 80,443,8080,8000,8888 -threads 200 > subdomains_alive.txt

# Подробная проверка кодов состояния, заголовков страниц и технологий
cat subdomain.txt | httpx -sc -title -server -td -ports 80,443,8080,8000,8888 -threads 200

# Параметры:
# -sc: код состояния
# -title: заголовок страницы
# -server: заголовок Server
# -td: определение технологий
```

<a id="visual-recon-with-aquatone"></a>
### Визуальная разведка с Aquatone

```bash
# Сделать снимки экрана доступных узлов
cat hosts.txt | aquatone

# Указанные порты
cat hosts.txt | aquatone -ports 80,443,8000,8080,8443

# Расширенный набор портов
cat hosts.txt | aquatone -ports 80,81,443,591,2082,2087,2095,2096,3000,8000,8001,8008,8080,8083,8443,8834,8888
```
> 💡 **Зачем снимки экрана?** Они помогают быстро заметить интересные приложения, формы входа и страницы ошибок.

---

<a id="-phase-4-url-collection"></a>
## 🔗 Этап 4: сбор URL

> **Цель:** собрать URL для проверки параметров и поиска уязвимостей.

<a id="active-crawling"></a>
### Активный обход сайтов

<a id="katana"></a>
#### Katana
```bash
# Быстрый веб-краулер для поиска URL
katana -u livesubdomains.txt -d 2 -o urls.txt

# -d 2: глубина в два уровня
# Быстрый обход современных веб-приложений
```

<a id="hakrawler"></a>
#### Hakrawler
```bash
# Простой быстрый веб-краулер
cat urls.txt | hakrawler -u > crawled_urls.txt
```

<a id="passive-url-collection"></a>
### Пассивный сбор URL

<a id="gau-get-all-urls"></a>
#### GAU (получение всех URL)
```bash
# Получить известные URL из AlienVault OTX, Wayback Machine и Common Crawl
cat livesubdomains.txt | gau | sort -u > passive_urls.txt

# Получить URL с кодом 200 и удалить дубликаты
echo example.com | gau --mc 200 | urldedupe > urls.txt
```
> 💡 **Зачем GAU?** Исторические данные могут содержать URL малоизвестных конечных точек.

<a id="urlfinder"></a>
#### URLFinder
```bash
# Найти URL в различных источниках
urlfinder -d example.com | sort -u > urlfinder_urls.txt
```

<a id="parameter-extraction"></a>
### Извлечение параметров

```bash
# Извлечь URL с параметрами для дальнейшей проверки
cat allurls.txt | grep '=' | urldedupe | tee params_urls.txt

# Поиск по шаблонам параметров
cat allurls.txt | grep -E '\?[^=]+=.+$' | tee params_urls.txt

# Шаблон GF SQLi — URL, потенциально связанные с SQL-инъекцией
cat allurls.txt | gf sqli

# Шаблон GF XSS
cat allurls.txt | gf xss

# Шаблон GF LFI
cat allurls.txt | gf lfi
```
> 💡 **Шаблоны GF:** установите `gf` и получите шаблоны из github.com/1ndianl33t/Gf-Patterns

---

<a id="-phase-5-vulnerability-hunting"></a>
## 🔓 Этап 5: поиск уязвимостей

<a id="-xss-discovery"></a>
### 🔴 Поиск XSS

<a id="reflected-xss-testing"></a>
#### Проверка отражённого XSS
```bash
# Последовательность поиска кандидатов на отражённый XSS
subfinder -d "example.com" -all -recursive | \
httpx -mc 200 -silent | \
sed -E 's,https?://(www\.)?,,' | anew | \
urlfinder -all | \
iconv -f ISO-8859-1 -t UTF-8 -c | \
grep -aE '\?.*=.*(&.*)?' | \
grep -aiEv "\.(css|ico|woff|woff2|svg|ttf|eot|png|jpg|jpeg|js|json|pdf|gif|xml|webp)($|\s|\?|&|#|/|\.)" | \
awk -F'[?&=]' '!seen[$1$2]++' | anew
```

<a id="blind-xss-testing"></a>
#### Проверка слепого XSS
```bash
# Проверка слепого XSS специальными полезными нагрузками
# Использовать сервисы вроде XSS Hunter или Burp Collaborator
# Проверить поля ввода, заголовки и параметры с полезной нагрузкой
```
> 💡 **Слепой XSS:** полезная нагрузка может выполниться позже, когда администратор откроет данные; результат отслеживают через callback-сервер.

<a id="-lfi-discovery"></a>
### 🔴 Поиск LFI

```bash
# Проверка LFI с FFUF и поиском содержимого файла passwd
echo "https://example.com/" | gau | gf lfi | uro | \
sed 's/=.*/=/' | qsreplace "FUZZ" | sort -u | \
xargs -I{} ffuf -u {} -w payloads/lfi.txt -c -mr "root:(x|\*|\$[^\:]*):0:0:" -v

# Проверка LFI с curl и параллельной обработкой
gau example.com | gf lfi | qsreplace "/etc/passwd" | \
xargs -I% -P 25 sh -c 'curl -s "%" 2>&1 | grep -q "root:x" && echo "VULN! %"'

# Проверка LFI с HTTPx
echo 'https://example.com/index.php?page=' | \
httpx -paths payloads/lfi.txt -threads 50 -random-agent -mc 200 -mr "root:(x|\*|\$[^\:]*):0:0:"
```
> 💡 **Совет по LFI:** проверьте `/etc/passwd`, `....//....//etc/passwd` и закодированные варианты.

<a id="-open-redirect-discovery"></a>
### 🔴 Поиск открытых перенаправлений

```bash
# Найти параметры перенаправления
cat urls.txt | grep -Pi "returnUrl=|continue=|dest=|destination=|forward=|go=|goto=|next=|redirect=|redirect_to=|redirect_uri=|redirect_url=|return=|returnTo=|return_url=|rurl=|target=|to=|url=" | tee redirect_params.txt

# Шаблон GF redirect
cat urls.txt | gf redirect | uro | sort -u | tee redirect_params.txt

# Проверить параметры перенаправления
cat redirect_params.txt | qsreplace "https://evil.com" | httpx -silent -fr -mr "evil.com"

# Полная последовательность проверки открытых перенаправлений
subfinder -d example.com -all | httpx -silent | gau | gf redirect | uro | qsreplace "https://evil.com" | httpx -silent -fr -mr "evil.com"
```

<a id="-ssrf-discovery"></a>
### 🔴 Поиск SSRF

```bash
# Найти параметры, потенциально связанные с SSRF
cat urls.txt | grep -E 'url=|uri=|redirect=|next=|data=|path=|dest=|proxy=|file=|img=|out=|continue=' | sort -u

# Найти шаблоны API и webhook
cat urls.txt | grep -i 'webhook\|callback\|upload\|fetch\|import\|api' | sort -u

# Проверка SSRF с Nuclei
cat urls.txt | nuclei -t nuclei-templates/vulnerabilities/ssrf/

# Базовая проверка SSRF с обращением к localhost
curl "https://example.com/page?url=http://127.0.0.1:80/"

# SSRF к метаданным облака AWS
curl "https://example.com/api?endpoint=http://169.254.169.254/latest/meta-data/"
```
> ⚠️ **Последствия SSRF:** возможен доступ к внутренним сервисам, метаданным облака и другим ресурсам.

<a id="-cors-testing"></a>
### 🔴 Проверка CORS

```bash
# Проверить CORS с указанным Origin
curl -H "Origin: http://evil.com" -I https://example.com/api/

# Подробный анализ CORS
curl -H "Origin: http://evil.com" -I https://example.com/api/ | \
grep -i -e "access-control-allow-origin" -e "access-control-allow-methods" -e "access-control-allow-credentials"

# Автоматизированная проверка CORS с Nuclei
cat subdomains.txt | httpx -silent | nuclei -t nuclei-templates/vulnerabilities/cors/ -o cors_results.txt
```
> 💡 **CORS в оригинале:** сочетание `Access-Control-Allow-Origin: *` с credentials названо критическим; см. уточнение выше.

<a id="nuclei-vulnerability-scanning"></a>
### Поиск уязвимостей с Nuclei

```bash
# Одна цель
nuclei -u https://example.com -bs 50 -c 30

# Несколько целей
nuclei -l live_domains.txt -bs 50 -c 30

# Только критическая и высокая критичность
nuclei -l live_domains.txt -s critical,high -bs 50 -c 30

# -bs: размер пакета целей
# -c: число параллельных проверок
```

---

<a id="-phase-6-directory--file-discovery"></a>
## 📂 Этап 6: поиск каталогов и файлов

<a id="sensitive-file-discovery"></a>
### Поиск конфиденциальных файлов

```bash
# Базовый набор расширений конфиденциальных файлов
cat allurls.txt | grep -E "\.xls|\.xml|\.xlsx|\.json|\.pdf|\.sql|\.doc|\.docx|\.pptx|\.txt|\.zip|\.tar\.gz|\.tgz|\.bak|\.7z|\.rar|\.log|\.cache|\.secret|\.db|\.backup|\.yml|\.gz|\.config|\.csv|\.yaml|\.md|\.md5"

# Расширенное регулярное выражение для поиска файлов
cat allurls.txt | grep -E "\.(xls|xml|xlsx|json|pdf|sql|doc|docx|pptx|txt|zip|tar\.gz|tgz|bak|7z|rar|log|cache|secret|db|backup|yml|gz|config|csv|yaml|md|md5|tar|xz|7zip|p12|pem|key|crt|csr|sh|pl|py|java|class|jar|war|ear|sqlitedb|sqlite3|dbf|db3|accdb|mdb|sqlcipher|gitignore|env|ini|conf|properties|plist|cfg)$"

# Поисковый запрос Google для файлов
# site:*.example.com (ext:doc OR ext:docx OR ext:pdf OR ext:xls OR ext:xlsx OR ext:txt OR ext:xml OR ext:json OR ext:zip OR ext:sql OR ext:bak OR ext:conf OR ext:log)
```

<a id="directory-bruteforcing"></a>
### Перебор каталогов

<a id="dirsearch"></a>
#### Dirsearch
```bash
# Базовый поиск каталогов
dirsearch -u https://example.com --full-url --deep-recursive -r

# Расширенная проверка с несколькими расширениями
dirsearch -u https://example.com -e php,cgi,htm,html,js,txt,bak,zip,old,conf,log,asp,aspx,jsp,sql,json,xml,yml,yaml,py,rb --random-agent --recursive -R 3 -t 20 --exclude-status=404 --follow-redirects
```

<a id="ffuf-directory-discovery"></a>
#### Поиск каталогов с FFUF
```bash
# Подробный перебор каталогов с FFUF
ffuf -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-big.txt \
-u https://example.com/FUZZ \
-fc 400,401,402,403,404,429,500,501,502,503 \
-recursion -recursion-depth 2 \
-e .html,.php,.txt,.pdf,.js,.css,.zip,.bak,.old,.log,.json,.xml,.config,.env \
-ac -c \
-H "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:91.0) Gecko/20100101 Firefox/91.0" \
-t 10
```

<a id="git-exposure-detection"></a>
### Поиск открытых каталогов Git

```bash
# Найти доступные извне каталоги .git
cat domains.txt | httpx -sc -server -cl -path "/.git/" -mc 200 -location -ms "Index of" -probe

# Проверить .git/config
curl -s https://example.com/.git/config

# Скачать репозиторий Git, если он открыт
git-dumper https://example.com/.git/ output/
```
> ⚠️ **Открытый Git:** может раскрыть исходный код, учётные данные и секреты.

<a id="hidden-parameter-discovery-with-arjun"></a>
### Поиск скрытых параметров с Arjun

```bash
# Пассивный поиск параметров
arjun -u https://example.com/endpoint.php -oT arjun_output.txt -t 10 --rate-limit 10 --passive -m GET,POST --headers "User-Agent: Mozilla/5.0"

# Активный поиск параметров по словарю
arjun -u https://example.com/endpoint.php -oT arjun_output.txt -m GET,POST \
-w /usr/share/wordlists/seclists/Discovery/Web-Content/burp-parameter-names.txt \
-t 10 --rate-limit 10 --headers "User-Agent: Mozilla/5.0"
```

<a id="subdomain-takeover"></a>
### Захват поддомена

```bash
# Автоматизированный поиск возможного захвата поддомена
subzy run --targets subdomains.txt --concurrency 100 --hide_fails --verify_ssl
```
> 💡 **Признак возможного захвата:** запись CNAME указывает на неиспользуемый ресурс сервиса (S3, Heroku и т. п.).

<a id="wordpress-testing"></a>
### Проверка WordPress

```bash
# Полная проверка безопасности WordPress
wpscan --url https://example.com --disable-tls-checks --api-token YOUR_API_TOKEN \
-e at -e ap -e u --enumerate ap --plugins-detection aggressive --force

# -e at: все темы
# -e ap: все плагины
# -e u: пользователи
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="recon-pipeline"></a>
### Последовательность разведки

```bash
# 1. Поиск поддоменов
subfinder -d example.com -all -recursive -o subs.txt
amass enum -passive -d example.com >> subs.txt
cat subs.txt | sort -u > all_subs.txt

# 2. Поиск доступных узлов
cat all_subs.txt | httpx -ports 80,443,8080,8000 -threads 200 > alive.txt

# 3. Сбор URL
cat alive.txt | gau | sort -u > urls.txt
cat alive.txt | katana -d 2 >> urls.txt

# 4. URL с параметрами
cat urls.txt | grep '=' | urldedupe > params.txt

# 5. Поиск уязвимостей
nuclei -l alive.txt -s critical,high -o nuclei_results.txt
```

<a id="essential-one-liners"></a>
### Основные однострочные команды

| Назначение | Команда |
| --------- | --------- |
| Найти поддомены | `subfinder -d target.com -silent` |
| Проверить доступные узлы | `cat subs.txt \| httpx -silent` |
| Собрать URL | `echo target.com \| gau` |
| Найти параметры | `cat urls.txt \| grep '='` |
| Проверить перенаправления | `cat urls.txt \| gf redirect` |
| Проверка Nuclei | `nuclei -l hosts.txt -s critical,high` |

<a id="file-extensions-to-hunt"></a>
### Расширения файлов для поиска

| Тип | Расширения |
| ------ | ------------ |
| **Конфигурация** | `.env, .config, .conf, .ini, .yml, .yaml` |
| **Резервные копии** | `.bak, .old, .backup, .zip, .tar.gz` |
| **Исходный код** | `.php, .asp, .aspx, .jsp, .py, .rb` |
| **Данные** | `.sql, .db, .sqlite, .json, .xml` |
| **Документы** | `.pdf, .doc, .docx, .xls, .xlsx` |

---

<a id="-tool-installation"></a>
## 🛠️ Установка инструментов

<a id="essential-tools"></a>
### Основные инструменты

```bash
# Subfinder
go install -v github.com/projectdiscovery/subfinder/v2/cmd/subfinder@latest

# HTTPX
go install -v github.com/projectdiscovery/httpx/cmd/httpx@latest

# Nuclei
go install -v github.com/projectdiscovery/nuclei/v3/cmd/nuclei@latest

# Katana
go install github.com/projectdiscovery/katana/cmd/katana@latest

# GAU
go install github.com/lc/gau/v2/cmd/gau@latest

# FFUF
go install github.com/ffuf/ffuf/v2@latest

# Amass
go install -v github.com/owasp-amass/amass/v4/...@master

# Assetfinder
go install github.com/tomnomnom/assetfinder@latest
```

<a id="xss--redirect-tools"></a>
### Инструменты XSS и перенаправлений

```bash
# OR (проверка открытых перенаправлений)
go install -v github.com/h6nt3r/or@latest

# RC (отражённый XSS)
go install -v github.com/h6nt3r/rc@latest

# CXSS (сканер контекстного XSS)
go install github.com/haxshadow/cxss@latest

# XSSER
go install -v github.com/h6nt3r/xsser@latest

# GF (поиск по шаблонам)
go install github.com/tomnomnom/gf@latest
```

<a id="utility-tools"></a>
### Вспомогательные инструменты

```bash
# URO (удаление дубликатов URL)
pip3 install uro

# Arjun (поиск параметров)
pip3 install arjun

# Subzy (поиск захвата поддомена)
go install -v github.com/PentestPad/subzy@latest
```

---

<a id="-bug-bounty-tips"></a>
## 🎯 Советы по Bug Bounty

1. **Автоматизируйте, но проверяйте вручную** — инструменты находят кандидатов, а уязвимость подтверждаете вы
2. **Проверяйте robots.txt и sitemap.xml** — они могут раскрыть малоизвестные пути
3. **Изучайте JavaScript** — конечные точки API, секреты и учётные данные в коде
4. **Проверяйте все параметры** — в том числе скрытые, найденные с помощью Arjun
5. **Рассматривайте цепочки уязвимостей** — сочетание SSRF и XSS может усилить последствия
6. **Следите за качеством отчёта** — понятные PoC, последствия и шаги воспроизведения
7. **Будьте терпеливы** — поиск существенных уязвимостей требует времени

---

<a id="-related-cheatsheets"></a>
## 📚 Связанные шпаргалки

- [Nuclei](../../Nuclei/README.md)
- [ffuf](../../ffuf/README.md)
- [Subfinder](../../Subfinder/README.md)
- [httpx](../../httpx/README.md)
- [Google Dorking](../../Google-Dorking/README.md)
- [Burp Suite](../../Burp-Suite/translations/README.ru.md)

---

<p align="center">
  <b>🐛 Удачного поиска!</b><br>
  <i>Bug Bounty — настойчивость помогает находить ценные уязвимости</i>
</p>
