<a id="-subdomain-takeover-cheatsheet"></a>
# 🎯 Справочник по перехвату поддоменов

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../README.md)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
  ███████╗██╗   ██╗██████╗ ██████╗  ██████╗ ███╗   ███╗ █████╗ ██╗███╗   ██╗
  ██╔════╝██║   ██║██╔══██╗██╔══██╗██╔═══██╗████╗ ████║██╔══██╗██║████╗  ██║
  ███████╗██║   ██║██████╔╝██║  ██║██║   ██║██╔████╔██║███████║██║██╔██╗ ██║
  ╚════██║██║   ██║██╔══██╗██║  ██║██║   ██║██║╚██╔╝██║██╔══██║██║██║╚██╗██║
  ███████║╚██████╔╝██████╔╝██████╔╝╚██████╔╝██║ ╚═╝ ██║██║  ██║██║██║ ╚████║
  ╚══════╝ ╚═════╝ ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝
                ████████╗ █████╗ ██╗  ██╗███████╗ ██████╗ ██╗   ██╗███████╗██████╗ 
                ╚══██╔══╝██╔══██╗██║ ██╔╝██╔════╝██╔═══██╗██║   ██║██╔════╝██╔══██╗
                   ██║   ███████║█████╔╝ █████╗  ██║   ██║██║   ██║█████╗  ██████╔╝
                   ██║   ██╔══██║██╔═██╗ ██╔══╝  ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗
                   ██║   ██║  ██║██║  ██╗███████╗╚██████╔╝ ╚████╔╝ ███████╗██║  ██║
                   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝ ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝
```

---

<a id="-what-is-subdomain-takeover"></a>
## 🎯 Что такое перехват поддомена

> **Уточнение к оригиналу.** Сигнатура ошибки или «висячая» запись DNS — признак для проверки, а не доказательство возможности перехвата. Названия сервисов, сообщения и способы из оригинала могут быть историческими. Возможность привязать домен, доступ к cookies и последствия для OAuth зависят от настроек и защит провайдера. Исходные примеры и сигнатуры сохранены.


**Перехват поддомена (subdomain takeover)** возможен, когда поддомен указывает через CNAME на удалённый сервис, а злоумышленник может заново зарегистрировать соответствующий ресурс и размещать вредоносное содержимое от имени поддомена.

<a id="impact"></a>
### Последствия
- 🔴 **Фишинг** — размещение убедительных фишинговых страниц
- 🔴 **Кража cookies** — получение cookies, установленных для родительского домена
- 🔴 **Захват учётных записей** — перехват обратных вызовов OAuth
- 🔴 **Ущерб репутации** — распространение вредоносного содержимого

---

<a id="-detection-methodology"></a>
## 🔍 Методика обнаружения

<a id="step-1-enumerate-subdomains"></a>
### Шаг 1: сбор поддоменов
```bash
# Несколько инструментов для более полного охвата
subfinder -d target.com -o subs.txt
amass enum -d target.com -o amass.txt
assetfinder --subs-only target.com >> subs.txt

# Объединить результаты и удалить дубликаты
cat subs.txt amass.txt | sort -u > all_subs.txt
```

<a id="step-2-find-cnames"></a>
### Шаг 2: поиск записей CNAME
```bash
# Проверить записи DNS
cat all_subs.txt | while read sub; do
    cname=$(dig CNAME +short $sub)
    if [ ! -z "$cname" ]; then
        echo "$sub -> $cname"
    fi
done

# Или использовать dnsrecon
dnsrecon -d target.com -t crt
```

<a id="step-3-check-for-dangling-cnames"></a>
### Шаг 3: проверка «висячих» записей CNAME
```bash
# Ручная проверка
dig CNAME subdomain.target.com +short
# Результат: something.cloudfront.net
# Затем проверить, не занят ли соответствующий ресурс сервиса

# Проверка кодов состояния с помощью httpx
cat all_subs.txt | httpx -status-code -title -tech-detect -no-color
```

---

<a id="-automated-tools"></a>
## 🛠️ Автоматизированные инструменты

<a id="subjack"></a>
### Subjack

> **Уточнение к инструментам.** В [справке Subjack](https://github.com/haccer/subjack) параметр `-a` означает отправку запросов ко всем URL, а не включение сигнатур. В [коде DNSRecon](https://github.com/darkoperator/dnsrecon/blob/master/dnsrecon/cli.py) `-t crt` ищет имена через crt.sh и не заменяет запрос CNAME. Современные шаблоны Nuclei находятся в [http/takeovers](https://github.com/projectdiscovery/nuclei-templates/tree/main/http/takeovers); старые пути ниже зависят от версии набора.

```bash
# Установка
go install github.com/haccer/subjack@latest

# Сканирование
subjack -w subdomains.txt -t 100 -timeout 30 -o results.txt -ssl

# С сигнатурами (подпись оригинала; см. уточнение)
subjack -w subdomains.txt -t 100 -a -timeout 30 -o results.txt -ssl
```

<a id="nuclei-takeover-templates"></a>
### Nuclei (шаблоны для обнаружения перехвата)
```bash
# Использование шаблонов nuclei
nuclei -l subdomains.txt -t ~/nuclei-templates/takeovers/ -o takeovers.txt

# Проверка конкретного варианта перехвата
nuclei -l subdomains.txt -t ~/nuclei-templates/takeovers/subdomain-takeover.yaml
```

<a id="can-i-take-over-xyz"></a>
### Can-I-Take-Over-XYZ
```bash
# Найти сигнатуры в репозитории GitHub
# https://github.com/EdOverflow/can-i-take-over-xyz

# Использовать сигнатуры вручную или в автоматизированных инструментах
```

---

<a id="-vulnerable-services--fingerprints"></a>
## 📋 Уязвимые сервисы и сигнатуры

> **Уточнение о провайдерах.** [CloudFront требует действительный TLS-сертификат](https://docs.aws.amazon.com/AmazonCloudFront/latest/DeveloperGuide/CNAMEs.html), покрывающий добавляемое доменное имя. [Проверенные домены GitHub Pages](https://docs.github.com/en/pages/configuring-a-custom-domain-for-your-github-pages-site/verifying-your-custom-domain-for-github-pages) защищены от привязки к чужой учётной записи. Для Azure существуют [TXT-проверка asuid и другие защиты](https://learn.microsoft.com/en-us/azure/security/fundamentals/subdomain-takeover). Поэтому приведённые упрощённые инструкции не гарантируют, что ресурс можно зарегистрировать или привязать.


<a id="aws-s3"></a>
### AWS S3
```
CNAME: bucket.s3.amazonaws.com
CNAME: bucket.s3-website-region.amazonaws.com

Сигнатура: "NoSuchBucket"
Сигнатура: "The specified bucket does not exist"

Перехват: создать бакет с тем же именем
```

<a id="aws-cloudfront"></a>
### AWS CloudFront
```
CNAME: xxxxx.cloudfront.net

Сигнатура: "Bad Request"
Сигнатура: "ERROR: The request could not be satisfied"

Перехват: создать дистрибутив CloudFront с тем же альтернативным доменным именем
```

<a id="github-pages"></a>
### GitHub Pages
```
CNAME: username.github.io
CNAME: organization.github.io

Сигнатура: "There isn't a GitHub Pages site here"

Перехват:
1. Создать репозиторий username.github.io
2. Добавить файл CNAME с целевым поддоменом
```

<a id="heroku"></a>
### Heroku
```
CNAME: xxx.herokuapp.com
CNAME: xxx.herokudns.com

Сигнатура: "No such app"
Сигнатура: "There's nothing here, yet"

Перехват: создать приложение Heroku с тем же именем
```

<a id="azure"></a>
### Azure
```
CNAME: xxx.azurewebsites.net
CNAME: xxx.cloudapp.azure.com
CNAME: xxx.blob.core.windows.net

Сигнатура: "404 - Web Site not found"

Перехват: создать ресурс с тем же именем
```

<a id="shopify"></a>
### Shopify
```
CNAME: shops.myshopify.com

Сигнатура: "Sorry, this shop is currently unavailable"
Сигнатура: "Only one step left"

Перехват: создать магазин Shopify и добавить пользовательский домен
```

<a id="wordpresscom"></a>
### WordPress.com
```
CNAME: xxx.wordpress.com

Сигнатура: "Do you want to register *.wordpress.com?"

Перехват: зарегистрировать поддомен на WordPress.com
```

<a id="zendesk"></a>
### Zendesk
```
CNAME: xxx.zendesk.com

Сигнатура: "Help Center Closed"
Сигнатура: "This help center no longer exists"

Перехват: возможен не всегда — зависит от настроек Zendesk
```

<a id="fastly"></a>
### Fastly
```
CNAME: xxx.fastly.net

Сигнатура: "Fastly error: unknown domain"

Перехват: добавить домен в сервис Fastly
```

<a id="pantheon"></a>
### Pantheon
```
CNAME: xxx.pantheonsite.io

Сигнатура: "The gods are wise but do not know of the site"
Сигнатура: "404 error unknown site!"

Перехват: добавить сайт на Pantheon
```

---

<a id="-exploitation-steps"></a>
## 🔥 Порядок эксплуатации

<a id="example-github-pages-takeover"></a>
### Пример: перехват через GitHub Pages

```bash
# 1. Найти уязвимый поддомен
dig CNAME blog.target.com
# Результат: target.github.io (но репозиторий отсутствует)

# 2. Создать репозиторий GitHub
# Создать: yourusername/target (или org/target)

# 3. Включить GitHub Pages в настройках репозитория

# 4. Добавить файл CNAME со следующим содержимым:
blog.target.com

# 5. Дождаться распространения изменений DNS

# 6. Проверить перехват
curl https://blog.target.com
```

<a id="example-s3-takeover"></a>
### Пример: перехват через S3

> **Уточнение к примеру S3.** По [правилам виртуального хостинга S3](https://docs.aws.amazon.com/AmazonS3/latest/userguide/VirtualHosting.html), при прямом CNAME имя бакета должно совпадать с исходным именем Host. В примере assets.target.com и assets-target различаются; создания указанного бакета недостаточно для подтверждения перехвата этого поддомена. Исходные команды сохранены.


```bash
# 1. Подтвердить уязвимость
dig CNAME assets.target.com
# Результат: assets-target.s3.amazonaws.com

curl http://assets-target.s3.amazonaws.com
# Результат: NoSuchBucket

# 2. Создать бакет S3 в том же регионе
aws s3 mb s3://assets-target --region us-east-1

# 3. Настроить бакет
# Настроить веб-хостинг или публичный доступ

# 4. Загрузить содержимое
echo "Subdomain Takeover PoC" > index.html
aws s3 cp index.html s3://assets-target/

# 5. Проверить результат
curl http://assets.target.com
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="vulnerable-service-detection"></a>
### Определение потенциально уязвимого сервиса

| Сервис | Шаблон CNAME | Сигнатура |
|---------|---------------|-------------|
| AWS S3 | `*.s3.amazonaws.com` | NoSuchBucket |
| CloudFront | `*.cloudfront.net` | Bad Request |
| GitHub | `*.github.io` | No GitHub Pages |
| Heroku | `*.herokuapp.com` | No such app |
| Azure | `*.azurewebsites.net` | 404 Web Site not found |
| Shopify | `shops.myshopify.com` | Shop unavailable |

<a id="takeover-checklist"></a>
### Контрольный список перехвата

```markdown
- [ ] Собрать все поддомены
- [ ] Найти записи CNAME
- [ ] Проверить «висячие» записи
- [ ] Сопоставить сигнатуры
- [ ] Убедиться, что ресурс сервиса не занят
- [ ] Проверить возможность перехвата в разрешённых пределах
- [ ] Задокументировать результат и подготовить отчёт
```

---

<a id="-tools-summary"></a>
## 🛠️ Сводка инструментов

| Инструмент | Назначение |
|------|---------|
| **subjack** | Автоматизированное обнаружение перехвата |
| **nuclei** | Сканирование по шаблонам |
| **can-i-take-over-xyz** | Справочник сигнатур |
| **dnsrecon** | Сбор записей DNS |
| **subfinder** | Поиск поддоменов |
| **httpx** | Проверка HTTP-ресурсов |

---

<a id="-resources"></a>
## 📚 Ресурсы

- [can-i-take-over-xyz](https://github.com/EdOverflow/can-i-take-over-xyz)
- [Subjack](https://github.com/haccer/subjack)
- [Шаблоны Nuclei для обнаружения перехвата](https://github.com/projectdiscovery/nuclei-templates/tree/main/takeovers)

---

<p align="center">
  <b>🎯 Находите возможности перехвата поддоменов!</b><br>
  <i>Только для проверок с разрешения владельца!</i>
</p>
