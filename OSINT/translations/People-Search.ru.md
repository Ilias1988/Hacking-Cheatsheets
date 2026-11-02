# 👤 Поиск людей с помощью OSINT

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../People-Search.md) • [🇷🇺 Русский раздел](../../README.ru.md)

<p align="center">
  <a href="People-Search.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="People-Search.el.md">🇬🇷 Ελληνικά</a>
</p>

```
██████╗ ███████╗ ██████╗ ██████╗ ██╗     ███████╗
██╔══██╗██╔════╝██╔═══██╗██╔══██╗██║     ██╔════╝
██████╔╝█████╗  ██║   ██║██████╔╝██║     █████╗  
██╔═══╝ ██╔══╝  ██║   ██║██╔═══╝ ██║     ██╔══╝  
██║     ███████╗╚██████╔╝██║     ███████╗███████╗
╚═╝     ╚══════╝ ╚═════╝ ╚═╝     ╚══════╝╚══════╝
███████╗███████╗ █████╗ ██████╗  ██████╗██╗  ██╗ 
██╔════╝██╔════╝██╔══██╗██╔══██╗██╔════╝██║  ██║ 
███████╗█████╗  ███████║██████╔╝██║     ███████║ 
╚════██║██╔══╝  ██╔══██║██╔══██╗██║     ██╔══██║ 
███████║███████╗██║  ██║██║  ██║╚██████╗██║  ██║ 
╚══════╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝ 
```

---

## 🔍 Поисковые сервисы для поиска людей

### Бесплатные сервисы

> **Примечание:** распределение сервисов по бесплатным и платным, а также оценки поиска лиц ниже воспроизводят оригинал; это не гарантия доступности, тарифа или точности. Совпадение фотографии или имени само по себе не подтверждает личность.

| Инструмент | URL | Охват |
| ------ | ----- | ---------- |
| **ThatsThem** | thatsthem.com | США |
| **FastPeopleSearch** | fastpeoplesearch.com | США |
| **TruePeopleSearch** | truepeoplesearch.com | США |
| **Webmii** | webmii.com | Весь мир |
| **PeekYou** | peekyou.com | США |
| **Pipl** | pipl.com | Весь мир |

### Платные сервисы
| Инструмент | URL | Возможности |
| ------ | ----- | ---------- |
| **Spokeo** | spokeo.com | Расширенный поиск, социальные сети |
| **BeenVerified** | beenverified.com | Проверка биографических сведений |
| **Intelius** | intelius.com | Сведения о судимостях |
| **US Search** | ussearch.com | Судебные записи |
| **Radaris** | radaris.com | Сведения о недвижимости |

---

## 🔎 Поиск людей с помощью Google dorks

Google dorks — поисковые запросы с операторами, позволяющими ограничить сайт, тип файла и другие условия поиска. Запросы ниже оставлены на языке оригинала.

### Базовые запросы
```
# Поиск по имени
"John Smith" site:linkedin.com
"John Smith" site:facebook.com
"John Smith" site:twitter.com

# Имя + местоположение
"John Smith" "New York"
"John Smith" "NYC" OR "New York City"

# Имя + компания
"John Smith" "Microsoft"
"John Smith" site:microsoft.com

# Имя + адрес почты
"John Smith" "@gmail.com"
"John Smith" email
```

### Профессиональная информация
```
# Поиск резюме/CV
"John Smith" filetype:pdf resume
"John Smith" filetype:doc CV
intitle:"curriculum vitae" "John Smith"

# Поиск в LinkedIn
site:linkedin.com/in "John Smith"
site:linkedin.com "John Smith" "Software Engineer"

# Профессиональные профили
site:about.me "John Smith"
site:crunchbase.com "John Smith"
```

### Поиск профилей в социальных сетях
```
# Найти все профили
"John Smith" (site:facebook.com OR site:twitter.com OR site:instagram.com)

# Поиск по имени пользователя
"johnsmith123" site:twitter.com
"johnsmith123" site:reddit.com
"johnsmith123" site:github.com
```

---

## 🛠️ Инструменты командной строки

### Sherlock (поиск по имени пользователя)

> **Версии:** исходные команды Sherlock отражают старую структуру проекта, а команда установки PhoneInfoga через pip относится к старому подходу. Используйте [официальную документацию Sherlock](https://sherlockproject.xyz/installation) и [PhoneInfoga](https://sundowndev.github.io/phoneinfoga/getting-started/install/) для выбранной версии; примеры оригинала сохранены.

```bash
# Установка
git clone https://github.com/sherlock-project/sherlock.git
cd sherlock
pip3 install -r requirements.txt

# Поиск имени пользователя на разных платформах
python3 sherlock username

# Несколько имён пользователей
python3 sherlock user1 user2 user3

# Только определённые сайты
python3 sherlock username --site twitter instagram facebook

# Экспорт результатов
python3 sherlock username --output results.txt
python3 sherlock username --csv
python3 sherlock username --xlsx
```

### Maigret (расширенный поиск по имени пользователя)
```bash
# Установка
pip3 install maigret

# Поиск
maigret username

# Через Tor
maigret username --tor

# Вывод в JSON
maigret username --json results.json
```

### holehe (проверка регистрации по адресу почты)
```bash
# Установка
pip3 install holehe

# Проверить адрес почты в разных сервисах
holehe email@example.com

# Показывать только сайты с регистрацией
holehe email@example.com --only-used
```

---

## 📧 Поиск человека по адресу электронной почты

### Найти человека по адресу почты
```
1. Найти адрес в Google: "email@example.com"
2. Проверить HIBP: haveibeenpwned.com
3. Поискать в базах сведений об утечках
4. Проверить регистрации в социальных сетях
5. Использовать сервисы обратного поиска по адресу почты
```

### Определение формата адресов почты
```bash
# Типичные форматы адресов почты
first.last@company.com
flast@company.com
firstl@company.com
first_last@company.com
first@company.com

# Определение формата через Hunter.io
https://hunter.io/email-finder
```

---

## 📱 OSINT по номеру телефона

### Сервисы поиска по номеру телефона
| Сервис | URL | Тип |
| --------- | ----- | ------ |
| **TrueCaller** | truecaller.com | Определение номера звонящего |
| **Sync.me** | sync.me | Определение номера звонящего |
| **NumLookup** | numlookup.com | Бесплатный поиск |
| **SpyDialer** | spydialer.com | Обратный поиск |
| **CallerID Test** | calleridtest.com | Сведения об операторе |

### Google dorks для номеров телефона
```
# Базовый поиск
"555-123-4567"
"+1 555 123 4567"
"5551234567"

# Найти связанные сведения
"555-123-4567" site:facebook.com
"555-123-4567" "@"
"555-123-4567" "email"
```

### Сбор открытых сведений о номерах телефона
```bash
# phoneinfoga
pip3 install phoneinfoga
phoneinfoga scan -n "+1234567890"
phoneinfoga serve -p 8080  # Веб-интерфейс

# С помощью API
curl "https://api.numlookupapi.com/v1/validate/14155551234?apikey=YOUR_KEY"
```

---

## 🏠 Адреса и сведения о недвижимости

### Поиск недвижимости
| Сервис | URL | Охват |
| --------- | ----- | ---------- |
| **Zillow** | zillow.com | США |
| **Redfin** | redfin.com | США |
| **Окружная служба оценки недвижимости** | [зависит от региона] | Местные реестры |
| **Whitepages** | whitepages.com | США |

### Открытые реестры
```
# Судебные записи
PACER (pacer.gov) — федеральные суды
Сайты судов штатов — суды штатов

# Сведения об избирателях
Базы регистрации избирателей (различаются по штатам)

# Регистрация компаний
Сайты секретарей штатов (Secretary of State)
```

---

## 👥 Анализ социальных связей

### OSINT в LinkedIn
```
# Поиск профилей
site:linkedin.com/in "John Smith"
site:linkedin.com/in "John Smith" "Company Name"

# Найти связи
Проверить раздел "People Also Viewed" («Также просматривали»)
Проверить общие контакты
Список сотрудников компании
```

### OSINT в Facebook
```
# URL профилей
facebook.com/search/people/?q=john%20smith
facebook.com/search/pages/?q=john%20smith

# Graph Search (возможности теперь ограничены)
People named "John" who work at "Company"
People who live in "City" and like "Page"
```

### Инструменты анализа социальных связей

> **Устаревший пример:** [репозиторий Twint](https://github.com/twintproject/twint) архивирован; приведённые команды не гарантируют работу с современным X.

```bash
# Social Analyzer
pip3 install social-analyzer
social-analyzer --username "target" --metadata

# Twint (Twitter)
pip3 install twint
twint -u username --followers
twint -u username --following
```

---

## 📸 Поиск по фотографиям и лицам

### Обратный поиск изображений
| Сервис | URL | Лучше подходит для |
| --------- | ----- | ---------- |
| **Google Images** | images.google.com | Общий поиск |
| **TinEye** | tineye.com | Точные совпадения |
| **Yandex** | yandex.com/images | Лучше подходит для лиц |
| **Bing Images** | bing.com/images | Хорошая альтернатива |
| **PimEyes** | pimeyes.com | Распознавание лиц |

### Поиск по лицу
```
1. Yandex Images — в оригинале рекомендуется для поиска лиц
2. PimEyes — платный поиск по лицу
3. Social Catfish — обратный поиск изображений для социальных сетей
```

---

## 📋 Контрольный список поиска людей

```markdown
## Цель: [Full Name]

### Основные сведения
□ Полное официальное имя
□ Дата рождения
□ Текущий адрес
□ Номера телефонов
□ Адреса электронной почты

### Присутствие в интернете
□ Профиль LinkedIn
□ Профиль Facebook
□ Аккаунт Twitter/X
□ Instagram
□ TikTok
□ Другие социальные сети
□ Личный сайт или блог

### Профессиональная деятельность
□ Текущий работодатель
□ Должность
□ Опыт работы
□ Образование
□ Профессиональные лицензии
□ Публикации и патенты

### Записи в реестрах
□ Сведения о недвижимости
□ Судебные записи
□ Регистрация компаний
□ Регистрация избирателя
□ Записи о браке и разводе

### Связи
□ Члены семьи
□ Знакомые и связанные лица
□ Деловые партнёры
□ Социальные связи
```

---

## ⚠️ Правовые и этические аспекты

```
✓ Использовать только общедоступные сведения
✓ Соблюдать законодательство о защите персональных данных (GDPR, CCPA)
✓ Не выдавать себя за других и не вводить в заблуждение
✓ Документировать методологию
✓ Использовать только для законных целей
✗ Не преследовать людей и не домогаться их
✗ Не получать доступ к закрытым учётным записям
✗ Не публиковать результаты без согласия
```

---

**К разделу OSINT:** [🔍 Обзор OSINT](./README.ru.md)
