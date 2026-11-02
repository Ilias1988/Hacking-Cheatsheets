<a id="-nikto---complete-cheatsheet"></a>
# 🕷️ Nikto — полная шпаргалка

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)
[🇬🇧 English](../README.md) • [🇷🇺 Русский раздел](../../README.ru.md)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
    _   _ _ _    _        
   | \ | (_) | _| |_ ___  
   |  \| | | |/ / __/ _ \ 
   | |\  | |   <| || (_) |
   |_| \_|_|_|\_\\__\___/ 
                          
   Web Server Scanner - Since 2001
```

<p align="center">
  <img src="https://img.shields.io/badge/Nikto-Web_Scanner-blue?style=for-the-badge" alt="Nikto">
  <img src="https://img.shields.io/badge/Vulnerability-Scanner-red?style=for-the-badge" alt="Vulnerability">
  <img src="https://img.shields.io/badge/CGI-Testing-green?style=for-the-badge" alt="CGI">
  <img src="https://img.shields.io/badge/SSL-Analysis-purple?style=for-the-badge" alt="SSL">
</p>

<p align="center">
  <b>🌐 Открытый сканер веб-серверов для тестирования безопасности</b>
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание
> **Уточнения к оригиналу:** числа в статистике и ссылки на базы 2.1.5 относятся к данным исходника; состав проверок и параметры зависят от версии. В [официальном списке параметров Nikto](https://github.com/sullo/nikto/wiki/Annotated-Option-List) -Save сохраняет положительные ответы в каталог, а не состояние для продолжения; -Resume там не указан. Примеры раздела сохранения хода работы оставлены буквально и не подтверждают поддержку возобновления. Совместимость -cookies, -update и форматов вывода также сверяйте с выбранной версией. Кавычки в строке -id "admin:password" не кодируют значение в Base64. Методы evasion не гарантируют обход современной IDS; см. [официальное руководство](https://cirt.net/nikto2-docs/options.html).

> **Подпись ASCII-эмблемы:** Web Server Scanner — сканер веб-серверов; Since 2001 — с 2001 года. Рисунок оставлен без изменения выравнивания.


- [Введение](#-introduction)
- [Установка](#-installation)
- [Основной синтаксис](#-basic-syntax)
- [Указание целей](#-target-specification)
- [Порты и параметры SSL](#-port--ssl-options)
- [Настройка проверок](#-scan-tuning)
- [Аутентификация](#-authentication)
- [Прокси и обход обнаружения](#-proxy--evasion)
- [Вывод и отчёты](#-output--reporting)
- [Обновление баз](#-database-updates)
- [Практические примеры](#-real-world-examples)
- [Краткий справочник](#-quick-reference)
- [Советы и рекомендуемые практики](#-tips--best-practices)
- [Полезные материалы](#-resources)

---

<a id="-introduction"></a>
## 🎯 Введение

**Nikto** — открытый сканер веб-серверов, который выполняет комплекс проверок, включая следующие категории:

<a id="what-nikto-checks"></a>
### Что проверяет Nikto

| Категория | Описание |
| ---------- | ------------- |
| **Опасные файлы** | Потенциально опасные файлы (резервные копии и т. п.) |
| **Устаревшие версии** | Устаревшее серверное ПО |
| **Зависимость от версии** | Уязвимости определённых версий |
| **Конфигурация сервера** | Ошибки настройки |
| **CGI-скрипты** | Потенциально опасные CGI-скрипты |
| **SSL/TLS** | Проблемы сертификатов SSL |
| **Заголовки** | Отсутствующие или небезопасные заголовки HTTP |
| **Методы** | Опасные методы HTTP |

<a id="database-statistics"></a>
### Статистика базы

```
✅ 6,700+ потенциально опасных файлов и CGI
✅ 1,250+ устаревших версий серверов
✅ 270+ проблем определённых версий
✅ 1,000+ проблем конфигурации серверов
```

<a id="features"></a>
### Возможности

| Возможность | Описание |
| --------- | ------------- |
| **Широкий охват** | Проверки тысяч уязвимостей |
| **Плагинная архитектура** | Расширяемая архитектура |
| **Несколько форматов** | Вывод HTML, XML, CSV, JSON |
| **Поддержка SSL** | Полноценное тестирование HTTPS |
| **Обход IDS** | Встроенные приёмы обхода |
| **Аутентификация** | Поддержка Basic, Digest, NTLM |

---

<a id="-installation"></a>
## 📥 Установка

<a id="kali-linux-pre-installed"></a>
### Kali Linux (предустановлен)
```bash
# Обновить Nikto
sudo apt update && sudo apt install nikto

# Обновить базу
nikto -update
```

<a id="ubuntudebian"></a>
### Ubuntu/Debian
```bash
sudo apt install nikto
```

<a id="from-github"></a>
### Из GitHub
```bash
git clone https://github.com/sullo/nikto
cd nikto/program
perl nikto.pl -h
```

<a id="macos-homebrew"></a>
### macOS (Homebrew)
```bash
brew install nikto
```

<a id="verify-installation"></a>
### Проверка установки
```bash
nikto -Version
nikto -h          # Справка
```

---

<a id="-basic-syntax"></a>
## ⌨️ Основной синтаксис

<a id="general-syntax"></a>
### Общий синтаксис
```bash
nikto -h <host> [options]
```

<a id="quick-start"></a>
### Быстрый старт
```bash
# Базовое сканирование
nikto -h http://target.com

# Сканирование с указанием порта
nikto -h target.com -p 80

# Сканирование HTTPS
nikto -h https://target.com

# Сканирование IP-адреса
nikto -h 192.168.1.1
```

---

<a id="-target-specification"></a>
## 🎯 Указание целей

<a id="single-target"></a>
### Одна цель
```bash
# По имени хоста
nikto -h target.com

# По IP-адресу
nikto -h 192.168.1.1

# С указанием протокола
nikto -h http://target.com
nikto -h https://target.com
```

<a id="multiple-targets"></a>
### Несколько целей
```bash
# Из файла
nikto -h targets.txt

# targets.txt:
# http://target1.com
# http://target2.com:8080
# https://target3.com
```

<a id="target-with-path"></a>
### Цель с указанием пути
```bash
# Проверить определённый путь
nikto -h http://target.com/app

# Проверить определённый каталог
nikto -h http://target.com/admin/
```

---

<a id="-port--ssl-options"></a>
## 🔌 Порты и параметры SSL

<a id="port-options"></a>
### Параметры портов
```bash
# Один порт
nikto -h target.com -p 80

# Несколько портов
nikto -h target.com -p 80,443,8080

# Диапазон портов
nikto -h target.com -p 80-90

# Типичные порты веб-сервисов
nikto -h target.com -p 80,443,8000,8080,8443
```

<a id="ssltls-options"></a>
### Параметры SSL/TLS

| Параметр | Описание |
| -------- | ------------- |
| `-ssl` | Принудительно использовать SSL |
| `-nossl` | Отключить SSL |
| `-maxtime` | Максимальное время сканирования |

```bash
# Принудительно использовать SSL
nikto -h target.com -ssl

# Отключить SSL (только HTTP)
nikto -h target.com -nossl

# SSL на нестандартном порту
nikto -h target.com -p 8443 -ssl
```

<a id="virtual-hosts"></a>
### Виртуальные хосты
```bash
# Указать виртуальный хост
nikto -h 192.168.1.1 -vhost target.com

# Полезно, если:
# - Цель находится за балансировщиком нагрузки
# - На одном IP-адресе размещено несколько сайтов
```

---

<a id="-scan-tuning"></a>
## 🎚️ Настройка проверок

<a id="tuning-options--tuning"></a>
### Категории проверок (-Tuning)

| Код | Описание |
| ------ | ------------- |
| `0` | Загрузка файлов |
| `1` | Интересные файлы / встречавшиеся в журналах |
| `2` | Ошибки настройки / стандартные файлы |
| `3` | Раскрытие информации |
| `4` | Инъекции (XSS/Script/HTML) |
| `5` | Удалённое получение файлов внутри корня веб-сайта |
| `6` | Отказ в обслуживании |
| `7` | Удалённое получение файлов в пределах сервера |
| `8` | Выполнение команд / удалённая оболочка |
| `9` | SQL-инъекция |
| `a` | Обход аутентификации |
| `b` | Определение ПО |
| `c` | Удалённое включение исходного кода |
| `d` | Веб-сервисы |
| `e` | Административная консоль |
| `x` | Инверсия выбора категорий |

<a id="using-tuning"></a>
### Выбор категорий
```bash
# Проверять только SQL-инъекции
nikto -h target.com -Tuning 9

# Проверять XSS и SQL-инъекции
nikto -h target.com -Tuning 49

# Проверять несколько категорий
nikto -h target.com -Tuning 1234

# Исключить определённые проверки (префикс x)
nikto -h target.com -Tuning x6    # Исключить проверки DoS

# Всё, кроме DoS
nikto -h target.com -Tuning x6
```

<a id="plugins"></a>
### Плагины
```bash
# Показать доступные плагины
nikto -list-plugins

# Запустить определённый плагин
nikto -h target.com -Plugins apacheusers

# Запустить несколько плагинов
nikto -h target.com -Plugins "apacheusers;robots"

# Исключить плагины
nikto -h target.com -Plugins "@@ALL;-robots"
```

<a id="common-plugins"></a>
### Типовые плагины

| Плагин | Описание |
| -------- | ------------- |
| `apacheusers` | Перечисление пользователей Apache |
| `cgi` | Проверка CGI-скриптов |
| `robots` | Анализ robots.txt |
| `headers` | Анализ заголовков HTTP |
| `httpoptions` | Проверка методов HTTP |
| `outdated` | Проверка устаревшего ПО |
| `ssl` | Анализ SSL/TLS |
| `dictionary` | Словарная атака |

---

<a id="-authentication"></a>
## 🔐 Аутентификация

<a id="basic-authentication"></a>
### Базовая аутентификация (Basic)
```bash
# Имя пользователя и пароль
nikto -h target.com -id admin:password

# Формат Base64 (подпись оригинала; см. уточнение)
nikto -h target.com -id "admin:password"
```

<a id="digest-authentication"></a>
### Аутентификация Digest
```bash
# Аутентификация Digest (определяется автоматически)
nikto -h target.com -id admin:password
```

<a id="ntlm-authentication"></a>
### Аутентификация NTLM
```bash
# Аутентификация NTLM
nikto -h target.com -id admin:password:domain
```

<a id="cookie-based-authentication"></a>
### Аутентификация с использованием cookies
```bash
# Отправить cookies
nikto -h target.com -cookies "session=abc123; token=xyz789"

# Из файла
nikto -h target.com -cookies cookies.txt
```

---

<a id="-proxy--evasion"></a>
## 🛡️ Прокси и обход обнаружения

<a id="proxy-support"></a>
### Поддержка прокси
```bash
# Прокси HTTP
nikto -h target.com -useproxy http://127.0.0.1:8080

# Прокси с аутентификацией
nikto -h target.com -useproxy http://user:pass@proxy:8080

# Через Burp Suite
nikto -h target.com -useproxy http://127.0.0.1:8080
```

<a id="ids-evasion--evasion"></a>
### Обход IDS (-evasion)

| Код | Приём |
| ------ | ----------- |
| `1` | Случайное кодирование URI (не UTF-8) |
| `2` | Самоссылка каталога (/./) |
| `3` | Преждевременное завершение URL |
| `4` | Добавление длинной случайной строки в начало |
| `5` | Поддельный параметр |
| `6` | Табуляция как разделитель в запросе |
| `7` | Изменение регистра символов URL |
| `8` | Разделитель каталогов Windows (\) |
| `A` | Символ возврата каретки (0x0d) |
| `B` | Двоичное значение 0x0b |

```bash
# Один приём обхода
nikto -h target.com -evasion 1

# Несколько приёмов обхода
nikto -h target.com -evasion 1234

# Все приёмы обхода
nikto -h target.com -evasion 12345678AB
```

<a id="user-agent"></a>
### User-Agent
```bash
# Произвольный User-Agent
nikto -h target.com -useragent "Mozilla/5.0 (Windows NT 10.0)"

# Googlebot
nikto -h target.com -useragent "Googlebot/2.1"
```

<a id="request-timing"></a>
### Временные параметры запросов
```bash
# Пауза между запросами (секунды)
nikto -h target.com -Pause 2

# Максимальное время сканирования (секунды)
nikto -h target.com -maxtime 3600

# Время ожидания для каждого запроса
nikto -h target.com -timeout 10
```

---

<a id="-output--reporting"></a>
## 📄 Вывод и отчёты

<a id="output-formats"></a>
### Форматы вывода

| Параметр | Формат |
| -------- | -------- |
| `-o file` | Вывод в файл |
| `-Format htm` | Формат HTML |
| `-Format xml` | Формат XML |
| `-Format csv` | Формат CSV |
| `-Format txt` | Текстовый формат |
| `-Format json` | Формат JSON |
| `-Format nbe` | Формат NBE |
| `-Format sql` | Формат SQL |

<a id="save-results"></a>
### Сохранение результатов
```bash
# Текстовый вывод
nikto -h target.com -o report.txt

# Отчёт HTML
nikto -h target.com -o report.html -Format htm

# Отчёт XML
nikto -h target.com -o report.xml -Format xml

# Отчёт JSON
nikto -h target.com -o report.json -Format json

# Отчёт CSV
nikto -h target.com -o report.csv -Format csv
```

<a id="verbosity-options"></a>
### Параметры подробности вывода
```bash
# Параметры отображения
nikto -h target.com -Display V    # Подробный вывод
nikto -h target.com -Display D    # Отладочный вывод
nikto -h target.com -Display E    # Ошибки HTTP
nikto -h target.com -Display P    # Ход работы
nikto -h target.com -Display S    # Убрать IP-адреса из вывода
nikto -h target.com -Display 1    # Показать перенаправления
nikto -h target.com -Display 2    # Показать cookies
nikto -h target.com -Display 3    # Показать ответы 200/OK
nikto -h target.com -Display 4    # Показать URL, требующие аутентификации

# Несколько параметров отображения
nikto -h target.com -Display VEP
```

<a id="save-progress"></a>
### Сохранение хода работы
```bash
# Сохранить состояние сканирования (подпись оригинала; см. уточнение)
nikto -h target.com -Save scan_state.txt

# Возобновить сканирование (пример оригинала; см. уточнение)
nikto -Resume scan_state.txt
```

---

<a id="-database-updates"></a>
## 🔄 Обновление баз

<a id="update-nikto-database"></a>
### Обновление базы Nikto
```bash
# Обновить все базы
nikto -update

# Обновить вручную (если -update не работает)
cd /var/lib/nikto
wget https://cirt.net/nikto/UPDATES/2.1.5/db_outdated.csv
wget https://cirt.net/nikto/UPDATES/2.1.5/db_tests.csv
```

<a id="database-location"></a>
### Расположение баз
```bash
# Стандартные расположения
/usr/share/nikto/databases/
/var/lib/nikto/

# Файлы баз
db_tests         # Основная база проверок
db_outdated      # Устаревшие версии ПО
db_server_msgs   # Сообщения сервера
db_variables     # Переменные
db_404_strings   # Строки страниц 404
```

---

<a id="-real-world-examples"></a>
## 🎬 Практические примеры

<a id="example-1-basic-web-server-scan"></a>
### Пример 1: базовое сканирование веб-сервера
```bash
nikto -h http://target.com
```

<a id="example-2-comprehensive-https-scan"></a>
### Пример 2: подробная проверка HTTPS
```bash
nikto -h https://target.com -ssl -p 443 -o report.html -Format htm
```

<a id="example-3-scan-multiple-ports"></a>
### Пример 3: сканирование нескольких портов
```bash
nikto -h target.com -p 80,443,8080,8443 -o results.txt
```

<a id="example-4-authenticated-scan"></a>
### Пример 4: сканирование с аутентификацией
```bash
nikto -h http://target.com/admin \
    -id admin:password \
    -o auth_scan.html \
    -Format htm
```

<a id="example-5-stealth-scan-ids-evasion"></a>
### Пример 5: сканирование с приёмами обхода IDS
```bash
nikto -h target.com \
    -evasion 1234 \
    -Pause 2 \
    -useragent "Mozilla/5.0 (Windows NT 10.0)" \
    -o stealth_scan.txt
```

<a id="example-6-through-burp-proxy"></a>
### Пример 6: через прокси Burp
```bash
nikto -h https://target.com \
    -useproxy http://127.0.0.1:8080 \
    -o proxy_scan.html \
    -Format htm
```

<a id="example-7-sql-injection-focus"></a>
### Пример 7: проверка SQL-инъекций
```bash
nikto -h target.com \
    -Tuning 9 \
    -o sqli_scan.txt
```

<a id="example-8-full-audit"></a>
### Пример 8: полный аудит
```bash
nikto -h https://target.com \
    -ssl \
    -p 443 \
    -Tuning x6 \
    -Display VEP \
    -o full_audit.html \
    -Format htm
```

<a id="example-9-virtual-host-testing"></a>
### Пример 9: проверка виртуального хоста
```bash
nikto -h 192.168.1.10 \
    -vhost app.target.com \
    -p 80,443 \
    -o vhost_scan.txt
```

<a id="example-10-scan-from-target-file"></a>
### Пример 10: сканирование целей из файла
```bash
nikto -h targets.txt \
    -o batch_scan.html \
    -Format htm
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="essential-commands"></a>
### Основные команды

| Задача | Команда |
| ------ | --------- |
| Базовое сканирование | `nikto -h target.com` |
| Сканирование HTTPS | `nikto -h target.com -ssl` |
| Несколько портов | `nikto -h target.com -p 80,443,8080` |
| С аутентификацией | `nikto -h target.com -id user:pass` |
| Отчёт HTML | `nikto -h target.com -o report.html -Format htm` |
| Обход обнаружения | `nikto -h target.com -evasion 123` |
| Через прокси | `nikto -h target.com -useproxy http://127.0.0.1:8080` |
| Обновить базу | `nikto -update` |

<a id="common-options"></a>
### Основные параметры

| Параметр | Описание |
| -------- | ------------- |
| `-h` | Целевой хост |
| `-p` | Порты для сканирования |
| `-ssl` | Принудительно использовать SSL |
| `-id` | Учётные данные для аутентификации |
| `-o` | Файл вывода |
| `-Format` | Формат вывода |
| `-Tuning` | Настройка проверок |
| `-evasion` | Обход IDS |
| `-useproxy` | Прокси-сервер |
| `-vhost` | Виртуальный хост |
| `-Pause` | Задержка между проверками |
| `-timeout` | Время ожидания ответа |

<a id="tuning-quick-reference"></a>
### Краткий справочник категорий

| Код | Категория |
| ------ | ---------- |
| 1-4 | Информация / ошибки настройки |
| 4-5 | Инъекции / получение файлов |
| 6 | DoS (не применять в рабочей среде) |
| 7-8 | Удалённый доступ |
| 9 | SQL-инъекция |
| a | Обход аутентификации |
| b | Определение ПО |
| x | Исключение |

---

<a id="-tips--best-practices"></a>
## 💡 Советы и рекомендуемые практики

<a id="performance-tips"></a>
### Советы по производительности

1. **Начать с базового сканирования**
   ```bash
   nikto -h target.com
   ```

2. **Исключить проверки DoS**
   ```bash
   nikto -h target.com -Tuning x6
   ```

3. **Выбрать подходящее время ожидания**
   ```bash
   nikto -h target.com -timeout 15 -Pause 1
   ```

<a id="accuracy-tips"></a>
### Советы по точности результатов

1. **Регулярно обновлять базу**
   ```bash
   nikto -update
   ```

2. **Использовать подробный вывод**
   ```bash
   nikto -h target.com -Display VE
   ```

3. **Сочетать с другими инструментами**
   ```bash
   # Сначала Nmap, затем Nikto
   nmap -sV -p 80,443 target.com
   nikto -h target.com -p 80,443
   ```

<a id="common-issues"></a>
### Типичные проблемы

| Проблема | Решение |
| ------- | ---------- |
| Медленное сканирование | Уменьшить `-Pause`, увеличить `-timeout` |
| Ошибки SSL | Явно указать параметр `-ssl` |
| Проблемы аутентификации | Проверить формат `-id` |
| Отсутствуют результаты | Обновить базу и проверить плагины |

---

<a id="-legal-disclaimer"></a>
## ⚠️ Правовая оговорка

> **ВНИМАНИЕ:** Nikto — сканер веб-серверов, который следует использовать только для **разрешённого тестирования**.
> 
> - ✅ Проверяйте собственные серверы
> - ✅ Получайте явное письменное разрешение
> - ✅ Применяйте в согласованных тестах на проникновение
> - ❌ Не сканируйте без разрешения
> - ❌ Несанкционированное сканирование незаконно
> 
> **Nikto может создавать значительный трафик и вызывать срабатывание средств защиты.**

---

<a id="-resources"></a>
## 📚 Полезные материалы

<a id="official-resources"></a>
### Официальные материалы
- [Nikto на GitHub](https://github.com/sullo/nikto)
- [Документация Nikto](https://cirt.net/Nikto2)
- [CIRT.net](https://cirt.net/)

<a id="learning-resources"></a>
### Учебные материалы
- [Руководство по тестированию OWASP](https://owasp.org/www-project-web-security-testing-guide/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [Nmap](../../Nmap/translations/README.ru.md)
- [Gobuster](../../Gobuster/translations/README.ru.md)
- [Burp Suite](../../Burp-Suite/README.md)

---

<p align="center">
  <b>🕷️ Освойте проверку веб-серверов!</b><br>
  <i>Находите уязвимости раньше атакующих</i>
</p>
