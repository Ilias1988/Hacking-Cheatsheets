# 🎣 Шпаргалка по фишингу

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Phishing.md) • [🇷🇺 Русский раздел](../../README.ru.md)

[🇹🇷 Türkçe Sürüm](Phishing.tr.md) • [🇬🇷 Ελληνικά](Phishing.el.md)

```
██████╗ ██╗  ██╗██╗███████╗██╗  ██╗██╗███╗   ██╗ ██████╗ 
██╔══██╗██║  ██║██║██╔════╝██║  ██║██║████╗  ██║██╔════╝ 
██████╔╝███████║██║███████╗███████║██║██╔██╗ ██║██║  ███╗
██╔═══╝ ██╔══██║██║╚════██║██╔══██║██║██║╚██╗██║██║   ██║
██║     ██║  ██║██║███████║██║  ██║██║██║ ╚████║╚██████╔╝
╚═╝     ╚═╝  ╚═╝╚═╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 📊 Виды фишинга

| Вид | Цель | Описание |
|------|--------|-------------|
| **Фишинг** | Массовая аудитория | Типовые письма большому числу пользователей |
| **Целевой фишинг** | Конкретные люди | Персонализированные атаки на выбранных людей |
| **Whaling** | Руководители | Атаки на высшее руководство и управленцев |
| **Клонированный фишинг** | Любая | Копия легитимного письма с вредоносным содержимым |
| **Вишинг** | Любая | Голосовой фишинг по телефону |
| **Смишинг** | Любая | Фишинг через SMS |
| **Фарминг (Pharming)** | Любая | Манипуляции с DNS для перенаправления на поддельный сайт |

---

## 🛠️ Настройка и использование GoPhish

### Установка
```bash
# Скачать GoPhish
wget https://github.com/gophish/gophish/releases/download/v0.12.1/gophish-v0.12.1-linux-64bit.zip
unzip gophish-v0.12.1-linux-64bit.zip
cd gophish

# Запустить GoPhish
./gophish

# Открыть панель администратора
https://localhost:3333
# Учётные данные по умолчанию: admin / пароль см. в выводе консоли
```

> **Примечание к исходнику:** пример закреплён за GoPhish v0.12.1. Команда cd gophish предполагает, что такой каталог уже существует; распаковка архива сама по себе не гарантирует этого имени каталога. Команды сохранены как в оригинале.

### Порядок настройки кампании
```
1. Sending Profile   - Настроить SMTP-сервер
2. Landing Page      - Создать поддельную страницу входа
3. Email Template    - Подготовить фишинговое письмо
4. Users & Groups    - Импортировать список целей
5. Launch Campaign   - Отправить фишинговые письма
6. Monitor Results   - Отслеживать открытия, переходы и учётные данные
```

### Пример шаблона письма
```html
<!DOCTYPE html>
<html>
<body>
<p>Dear {{.FirstName}},</p>
<p>Your IT department requires you to verify your account credentials.</p>
<p>Please click the link below to complete verification:</p>
<p><a href="{{.URL}}">Verify Account</a></p>
<p>This action is required within 24 hours.</p>
<br>
<p>IT Support Team</p>
</body>
</html>
```

### Пример посадочной страницы
```html
<!DOCTYPE html>
<html>
<head>
    <title>Microsoft Login</title>
</head>
<body>
    <form method="post" action="">
        <h2>Sign in</h2>
        <input type="email" name="email" placeholder="Email" required>
        <input type="password" name="password" placeholder="Password" required>
        <button type="submit">Sign in</button>
    </form>
</body>
</html>
```

---

## 🔥 Evilginx2 (обход 2FA)

> **Уточнение:** «обход 2FA» не означает обход любого второго фактора. CISA относит FIDO/WebAuthn к устойчивым к фишингу механизмам аутентификации; ограничение относится и к обобщению про MITM в обзоре. См. [CISA: Implementing Phishing-Resistant MFA](https://www.cisa.gov/sites/default/files/2023-01/fact-sheet-implementing-phishing-resistant-mfa-508c.pdf).

### Установка
```bash
# Установить Evilginx2
go install github.com/kgretzky/evilginx2@latest

# Или скачать готовый исполняемый файл
wget https://github.com/kgretzky/evilginx2/releases/...

# Запустить
sudo evilginx2
```

> **Примечание к исходнику:** URL загрузки с многоточием — сокращённый пример, а не готовая ссылка. Имена phishlet-модулей и команды сохранены из оригинала; сверяйте их с установленной версией.

### Базовая настройка
```bash
# В консоли Evilginx2
config domain yourdomain.com
config ip YOUR_SERVER_IP

# Показать phishlet-модули
phishlets

# Включить phishlet-модуль Microsoft 365
phishlets hostname o365 login.yourdomain.com
phishlets enable o365

# Создать приманку (lure)
lures create o365
lures get-url 0
```

### Цели phishlet-модулей
```
o365        - Microsoft 365
google      - Google accounts
linkedin    - LinkedIn
twitter     - Twitter/X
github      - GitHub
instagram   - Instagram
```

---

## 📧 Сбор адресов электронной почты

### theHarvester
```bash
# Сбор адресов электронной почты
theHarvester -d target.com -b all

# Конкретные источники
theHarvester -d target.com -b google,linkedin,bing

# Сохранить результат в файл
theHarvester -d target.com -b all -f output.html
```

### Hunter.io
```bash
# Через API
curl "https://api.hunter.io/v2/domain-search?domain=target.com&api_key=YOUR_KEY"

# Получить формат адреса электронной почты
curl "https://api.hunter.io/v2/email-finder?domain=target.com&first_name=John&last_name=Doe&api_key=YOUR_KEY"
```

### Сбор сведений из LinkedIn
```bash
# С помощью linkedin2username
python linkedin2username.py -u USERNAME -c COMPANY_NAME

# Подбор формата адресов
john.doe@company.com
jdoe@company.com
j.doe@company.com
johnd@company.com
```

---

## 📮 Инфраструктура отправки

### SMTP-сервисы для тестирования
```
- Amazon SES
- SendGrid
- Mailgun
- SMTP2GO
- Собственный сервер (высокий риск попадания в спам)
```

### Обход SPF/DKIM/DMARC
```bash
# Проверить запись SPF
dig txt target.com | grep spf

# Слабые места SPF для проверки:
# - "include:" со сторонними сервисами с избыточными разрешениями
# - "~all" (softfail) вместо "-all"
# - Отсутствие записи SPF

# Проверить DMARC
dig txt _dmarc.target.com
```

> **Уточнение:** softfail в SPF означает слабое отрицательное утверждение об авторизации отправителя; по [RFC 7208, §8.5](https://datatracker.ietf.org/doc/html/rfc7208#section-8.5) одного этого результата недостаточно для отклонения письма. Наличие ~all само по себе не доказывает обход SPF, DKIM или DMARC.

### Подготовка домена для фишинга
```bash
# Похожие домены (тайпсквоттинг)
target.com → tarqet.com
target.com → target-secure.com
target.com → target.co
target.com → target-login.com

# Проверить доступность
whois potential-phish-domain.com
```

---

## 📱 Смишинг (фишинг через SMS)

### Типичные предлоги
```
- Уведомление о доставке посылки
- Предупреждение службы безопасности банка
- Выигрыш приза или лотереи
- Подтверждение учётной записи
- Код двухфакторной аутентификации
```

### Сервисы отправки SMS
```
- Twilio
- Nexmo/Vonage
- Plivo
- AWS SNS
```

### Примеры сообщений
```
[FedEx] Ваша посылка ожидает доставки. Подтвердите доставку: http://short.url/abc

[BankName] Обнаружена необычная активность. Подтвердите данные: http://secure-link.com

[Netflix] Платёж не прошёл. Обновите данные: http://netflix-billing.com
```

---

## 📞 Вишинг (голосовой фишинг)

### Типичные сценарии
```
□ ИТ-поддержка, которой нужен сброс пароля
□ Отдел банка по борьбе с мошенничеством
□ Государственное ведомство (IRS, налоговая служба)
□ Мошенничество под видом технической поддержки
□ Срочная просьба генерального или финансового директора
□ Проверка поставщика
```

### Пример сценария разговора для вишинга
```
«Здравствуйте, это [Name] из службы ИТ-поддержки. Мы обнаружили
необычную активность в вашей учётной записи и должны проверить
учётные данные, чтобы предотвратить инцидент безопасности.
Можете подтвердить имя пользователя и текущий пароль,
чтобы я проверил вашу учётную запись?»
```

### Инструменты
```bash
# Подмена номера звонящего (Caller ID)
- SpoofCard
- SpoofTel

# Изменение голоса
- Voicemod
- Clownfish
```

---

## 🎯 SET (набор инструментов социальной инженерии)

### Установка
```bash
git clone https://github.com/trustedsec/social-engineer-toolkit
cd social-engineer-toolkit
pip install -r requirements.txt
sudo python setoolkit
```

### Пункты меню
```
1) Spear-Phishing Attack Vectors
2) Website Attack Vectors
3) Infectious Media Generator
4) Create a Payload and Listener
5) Mass Mailer Attack
```

### Сборщик учётных данных (Credential Harvester)
```bash
# В SET:
1) Social-Engineering Attacks
2) Website Attack Vectors
3) Credential Harvester Attack Method
2) Site Cloner
# Указать URL для копирования (например, https://login.microsoft.com)
# SET разместит поддельный сайт и будет перехватывать учётные данные
```

---

## 📝 Шаблоны фишинговых писем

### Сброс пароля
```
Тема: [ТРЕБУЕТСЯ ДЕЙСТВИЕ] Запрос на сброс пароля

Здравствуйте, {Name}!

Мы получили запрос на сброс вашего пароля. Если вы его
не отправляли, перейдите по ссылке ниже, чтобы немедленно
защитить учётную запись.

[Защитить учётную запись]

Ссылка действительна в течение 24 часов.

С уважением,
Служба безопасности
```

### ИТ-поддержка
```
Тема: Плановое обслуживание системы — требуется действие

Здравствуйте, {Name}!

Наша ИТ-команда проведёт плановое обслуживание.
Чтобы сохранить непрерывный доступ, подтвердите учётные данные:

[Подтвердить сейчас]

Без подтверждения учётная запись может быть заблокирована.

ИТ-отдел
```

### Доставка посылки
```
Тема: Не удалось доставить вашу посылку

Не удалось доставить вашу посылку из Amazon.
Пожалуйста, подтвердите адрес доставки:

[Подтвердить доставку]

Номер отслеживания: {Random}

Спасибо,
Служба доставки
```

---

## 📋 Контрольный список фишинговой кампании

```markdown
До кампании:
□ Определить границы проверки и цели (разрешённое тестирование)
□ Собрать адреса электронной почты целей
□ Зарегистрировать похожий домен
□ Подготовить SMTP-инфраструктуру
□ По возможности настроить SPF/DKIM
□ Создать посадочную страницу
□ Создать шаблон письма

Проведение кампании:
□ Проверить доставку писем и спам-фильтры
□ Импортировать список целей
□ Запланировать кампанию
□ Отслеживать долю доставленных писем
□ Отслеживать открытия, переходы и отправки форм

После кампании:
□ Сформировать отчёты
□ Проанализировать долю переходов
□ Проверить собранные данные
□ Определить потребности в обучении
□ Задокументировать результаты
```

---

## 🔗 Связанные шпаргалки

- [Претекстинг](./Pretexting.ru.md)
- [OSINT — Google Dorking](../../Google-Dorking/README.md)

---

**К обзору:** [🎭 Социальная инженерия](./README.ru.md)
