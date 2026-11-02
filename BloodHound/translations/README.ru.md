<a id="-bloodhound---active-directory-attack-path-visualization"></a>
# 🩸 BloodHound — визуализация путей атак в Active Directory

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🏠 Русская главная](../../README.ru.md) • [🇮🇹 Versione Italiana](README.it.md) | [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗      ██████╗  ██████╗ ██████╗ ██╗  ██╗ ██████╗ ██╗   ██╗███╗   ██╗██████╗ 
  ██╔══██╗██║     ██╔═══██╗██╔═══██╗██╔══██╗██║  ██║██╔═══██╗██║   ██║████╗  ██║██╔══██╗
  ██████╔╝██║     ██║   ██║██║   ██║██║  ██║███████║██║   ██║██║   ██║██╔██╗ ██║██║  ██║
  ██╔══██╗██║     ██║   ██║██║   ██║██║  ██║██╔══██║██║   ██║██║   ██║██║╚██╗██║██║  ██║
  ██████╔╝███████╗╚██████╔╝╚██████╔╝██████╔╝██║  ██║╚██████╔╝╚██████╔╝██║ ╚████║██████╔╝
  ╚═════╝ ╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝ ╚═╝  ╚═╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝╚═════╝ 
                        AD Attack Path Visualization
```

<p align="center">
  <img src="https://img.shields.io/badge/BloodHound-Active_Directory-red?style=for-the-badge" alt="BloodHound">
  <img src="https://img.shields.io/badge/Attack_Paths-blue?style=for-the-badge" alt="Attack Paths">
  <img src="https://img.shields.io/badge/Graph_Database-green?style=for-the-badge" alt="Graph">
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Что такое BloodHound](#-what-is-bloodhound)
- [Установка](#-installation)
- [Сбор данных SharpHound](#-sharphound-collection)
- [Работа с интерфейсом BloodHound](#-using-bloodhound-gui)
- [Встроенные запросы](#-built-in-queries)
- [Собственные запросы Cypher](#-custom-cypher-queries)
- [Анализ путей атак](#-attack-path-analysis)
- [BloodHound CE](#-bloodhound-ce)
- [Краткий справочник](#-quick-reference)

---

<a id="-what-is-bloodhound"></a>
## 🎯 Что такое BloodHound

**BloodHound** использует теорию графов, чтобы находить скрытые пути атак в Active Directory. Он отображает:

- 🔗 **Пути атак** — цепочки, ведущие к правам администратора домена (Domain Admin)
- 👤 **Связи пользователей** — членство в группах и сеансы
- 💻 **Связи компьютеров** — права администратора и сеансы
- 🔓 **Ошибки настройки** — разрешения, которые можно использовать для атаки
- 🎯 **Критически важные цели** — ключевые учётные записи

<a id="components"></a>
### Компоненты

> **Различия версий:** основная часть оригинала описывает BloodHound Legacy с настольным интерфейсом и Neo4j. CE означает **Community Edition**, а Enterprise — отдельный продукт; CE не является «облачной/корпоративной редакцией». Схемы установки, интерфейс и совместимые сборщики различаются. См. [официальное описание перехода к CE](https://specterops.io/blog/2023/08/08/bloodhound-community-edition-a-new-era/).

| Компонент | Описание |
|-----------|-------------|
| **BloodHound** | Графический интерфейс визуализации (на основе Neo4j) |
| **SharpHound** | Средство сбора данных (.exe или .ps1) |
| **Neo4j** | Графовая база данных |
| **BloodHound CE** | Новая облачная/корпоративная редакция |

---

<a id="-installation"></a>
## 🚀 Установка

<a id="kali-linux"></a>
### Kali Linux

```bash
# Установить BloodHound
sudo apt update
sudo apt install bloodhound

# Установить Neo4j
sudo apt install neo4j

# Запустить Neo4j
sudo neo4j start

# Веб-консоль Neo4j: http://localhost:7474
# Учётные данные по умолчанию: neo4j / neo4j (сменить при первом входе)

# Запуск BloodHound
bloodhound
```

<a id="windows"></a>
### Windows

```powershell
# Загрузить из раздела релизов GitHub
https://github.com/BloodHoundAD/BloodHound/releases

# Установить Neo4j Community Edition
https://neo4j.com/download/

# Запустить Neo4j
# Запустить от имени администратора и запустить службу

# Запустить BloodHound.exe
```

<a id="docker-recommended"></a>
### Docker (рекомендуется)

```bash
# Neo4j с настройками для BloodHound
docker run -d \
  --name neo4j \
  -p 7474:7474 -p 7687:7687 \
  -e NEO4J_AUTH=neo4j/bloodhound \
  neo4j:4.4

# Дождаться запуска, затем запустить BloodHound
bloodhound
```

---

<a id="-sharphound-collection"></a>
## 📡 Сбор данных с помощью SharpHound

<a id="download-sharphound"></a>
### Загрузка SharpHound

```bash
# Из релизов BloodHound
https://github.com/BloodHoundAD/BloodHound/tree/master/Collectors

# Файлы:
# - SharpHound.exe (самостоятельный исполняемый файл)
# - SharpHound.ps1 (PowerShell)
```

<a id="basic-collection"></a>
### Базовый сбор данных

> **Уточнение:** запуск без параметров использует набор **Default**, а не **All**. Состав Default шире сокращённой таблицы ниже и зависит от версии. В актуальной документации каталог вывода задаётся через `--OutputDirectory`; пример с `-o` сохранён из оригинала. Старый PowerShell-сборщик и современные версии SharpHound не взаимозаменяемы. См. [параметры SharpHound CE](https://bloodhound.specterops.io/collect-data/ce-collection/sharphound-flags).

```powershell
# Запустить SharpHound (по умолчанию — все методы сбора)
.\SharpHound.exe

# Указать домен
.\SharpHound.exe -d domain.local

# Указать каталог вывода
.\SharpHound.exe -o C:\Temp\

# Версия для PowerShell
Import-Module .\SharpHound.ps1
Invoke-BloodHound
```

<a id="collection-methods"></a>
### Методы сбора

```powershell
# Все методы сбора
.\SharpHound.exe -c All

# Отдельные методы
.\SharpHound.exe -c Default
.\SharpHound.exe -c Group
.\SharpHound.exe -c Session
.\SharpHound.exe -c Trusts
.\SharpHound.exe -c ACL
.\SharpHound.exe -c Container
.\SharpHound.exe -c RDP
.\SharpHound.exe -c DCOM
.\SharpHound.exe -c PSRemote
.\SharpHound.exe -c LocalAdmin
.\SharpHound.exe -c LocalGroup
.\SharpHound.exe -c SPNTargets
.\SharpHound.exe -c DCOnly
```

<a id="collection-method-details"></a>
### Подробности методов сбора

| Метод | Описание | Активность |
|--------|-------------|-------|
| Default | Group, LocalAdmin, Session, Trusts | Низкая |
| All | Все данные | Высокая |
| Session | Пользователи с активными сеансами | Средняя |
| LocalGroup | Участники локальных групп | Средняя |
| ACL | Разрешения ACL | Низкая |
| DCOnly | Сбор только с контроллера домена (DC) | Очень низкая |
| Group | Членство в группах | Низкая |
| Trusts | Доверительные отношения доменов | Низкая |

<a id="stealth-collection"></a>
### Сбор с пониженной активностью (stealth)

> **Совместимость:** `--stealth` меняет выбранный способ сбора, а не гарантирует незаметность; DCOnly ограничивает обращения контроллерами домена. Параметр `--skipdns` не указан в текущем перечне флагов. `--throttle` задаёт задержку в миллисекундах. Примеры оригинала сохранены; см. [документацию SharpHound](https://bloodhound.specterops.io/collect-data/ce-collection/sharphound-flags).

```powershell
# Режим пониженной активности (медленнее, меньше запросов)
.\SharpHound.exe -c DCOnly
.\SharpHound.exe --stealth

# Без разрешения DNS-имён
.\SharpHound.exe --skipdns

# Задержка между запросами
.\SharpHound.exe --throttle 1000
```

<a id="loop-collection-sessions"></a>
### Циклический сбор (сеансы)

```powershell
# Собирать данные о сеансах в течение заданного времени
.\SharpHound.exe -c Session --loop --loopduration 02:00:00

# Цикл с интервалом
.\SharpHound.exe -c Session --loop --loopinterval 00:05:00
```

<a id="from-linux-bloodhoundpy"></a>
### Из Linux (bloodhound.py)

> **Совместимость Python-сборщика:** показанные пакет `bloodhound` и команда `bloodhound-python` относятся к Legacy. Для CE разработчик поддерживает отдельную ветку и пакет `bloodhound-ce`; формат данных должен подходить версии сервера. См. [README разработчика BloodHound.py](https://github.com/dirkjanm/BloodHound.py).

```bash
# Установка
pip install bloodhound

# Запустить сбор
bloodhound-python -u user -p 'password' -d domain.local -ns 10.10.10.1 -c All

# С хешем
bloodhound-python -u user --hashes :NTLMHASH -d domain.local -c All
```

---

<a id="-using-bloodhound-gui"></a>
## 🖥️ Работа с графическим интерфейсом BloodHound

<a id="start-bloodhound"></a>
### Запуск BloodHound

```bash
# 1. Запустить Neo4j
sudo neo4j start

# 2. Дождаться запуска Neo4j (проверить http://localhost:7474)

# 3. Запустить BloodHound
bloodhound

# 4. Войти
# URL: bolt://localhost:7687
# Имя пользователя: neo4j
# Пароль: (ваш пароль)
```

<a id="import-data"></a>
### Импорт данных

```
1. Нажмите кнопку "Upload Data" (загрузка данных, значок папки)
2. Выберите файл .zip, созданный SharpHound
3. Дождитесь окончания импорта
4. Данные появятся на графе
```

<a id="interface-overview"></a>
### Обзор интерфейса

| Раздел | Описание |
|---------|-------------|
| **Search** | Поиск пользователей, компьютеров и групп |
| **Analysis** | Готовые запросы |
| **Filters** | Видимость связей и узлов |
| **Graph** | Визуальное представление |
| **Node Info** | Сведения о выбранном элементе |

<a id="navigation"></a>
### Навигация

```
- Левая кнопка мыши: выбрать узел
- Правая кнопка мыши: контекстное меню
- Колесо прокрутки: увеличить или уменьшить масштаб
- Перетаскивание: переместить область просмотра
- Ctrl+Click: добавить к выделению
```

---

<a id="-built-in-queries"></a>
## 🔍 Встроенные запросы

> **Названия интерфейса:** ниже сохранены английские имена запросов из Legacy и добавлены русские пояснения. В CE набор запросов и элементы интерфейса отличаются; актуальная работа с данными описана в [руководстве CE](https://bloodhound.specterops.io/get-started/quickstart/community-edition-quickstart).

<a id="domain-information"></a>
### Сведения о домене

```
- Find all Domain Admins — найти всех администраторов домена
- Find all Domain Controllers — найти все контроллеры домена
- Find high value targets — найти критически важные объекты
- Map Domain Trusts — построить карту доверительных отношений доменов
```

<a id="shortest-paths"></a>
### Кратчайшие пути

```
- Shortest Paths to Domain Admins — кратчайшие пути к администраторам домена
- Shortest Path from Owned Principals — кратчайший путь от скомпрометированных субъектов
- Shortest Path to High Value Targets — кратчайший путь к критически важным объектам
- Shortest Path to Unconstrained Delegation — кратчайший путь к неограниченному делегированию
```

<a id="kerberos"></a>
### Kerberos

```
- Find Kerberoastable Accounts — найти учётные записи для Kerberoasting
- Find AS-REP Roastable Users — найти пользователей для AS-REP Roasting
- Shortest Path from Kerberoastable Users — кратчайший путь от пользователей, доступных для Kerberoasting
- Find Principals with Unconstrained Delegation — найти субъекты с неограниченным делегированием
```

<a id="acl-analysis"></a>
### Анализ ACL

```
- Find Principals with DCSync Rights — найти субъекты с правами DCSync
- Find Dangerous Rights for Domain Users — найти опасные права группы Domain Users
- Find Users with Foreign Domain Group Membership — найти пользователей, состоящих в группах другого домена
```

<a id="computers"></a>
### Компьютеры

```
- Find Computers with Unsupported OS — найти компьютеры с неподдерживаемой ОС
- Find Computers where Domain Users are Local Admin — найти компьютеры, где Domain Users имеют локальные права администратора
- Find Computers with Sessions of High Value Users — найти компьютеры с сеансами критически важных пользователей
```

<a id="gpo"></a>
### GPO

```
- Find all GPO Owned by Owned Principals — найти GPO под контролем скомпрометированных субъектов
- Find GPO Affecting High Value Targets — найти GPO, влияющие на критически важные объекты
```

---

<a id="-custom-cypher-queries"></a>
## 📝 Собственные запросы Cypher

> **Совместимость запросов:** примеры сохранены для исходной схемы Legacy/Neo4j. Свойства `owned` и `highvalue`, поддерживаемый синтаксис и возможность изменения графа через `SET` следует сверять со своей версией CE и её настройками. Названия групп в строках запросов — примеры, зависящие от домена и локализации. Не всякая связь графа означает осуществимый шаг атаки. См. [поддерживаемый синтаксис Cypher](https://bloodhound.specterops.io/analyze-data/explore/cypher-supported), [настройку backend](https://bloodhound.specterops.io/get-started/custom-installation) и [обработку связей в BloodHound](https://specterops.io/blog/2022/08/03/introducing-bloodhound-4-2-the-azure-refactor/).

<a id="basic-queries"></a>
### Базовые запросы

```cypher
// Найти всех пользователей
MATCH (u:User) RETURN u

// Найти все компьютеры
MATCH (c:Computer) RETURN c

// Найти всех администраторов домена
MATCH (g:Group {name: "DOMAIN ADMINS@DOMAIN.LOCAL"})
MATCH (u:User)-[:MemberOf*1..]->(g)
RETURN u.name

// Найти все контроллеры домена
MATCH (c:Computer)-[:MemberOf*1..]->(g:Group)
WHERE g.name CONTAINS "DOMAIN CONTROLLERS"
RETURN c.name
```

<a id="session-queries"></a>
### Запросы о сеансах

```cypher
// Найти компьютеры с сеансами администраторов домена
MATCH (u:User)-[:MemberOf*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
MATCH (c:Computer)-[:HasSession]->(u)
RETURN u.name, c.name

// Найти пользователей с сеансами на нескольких компьютерах
MATCH (c:Computer)-[:HasSession]->(u:User)
WITH u, COUNT(c) as sessions
WHERE sessions > 5
RETURN u.name, sessions
ORDER BY sessions DESC
```

<a id="path-queries"></a>
### Запросы путей

```cypher
// Кратчайший путь к правам администратора домена
MATCH p=shortestPath(
  (u:User {name:"TARGETUSER@DOMAIN.LOCAL"})
  -[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
)
RETURN p

// Все пути к правам администратора домена (не более 10)
MATCH p=(u:User)-[*1..5]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
RETURN p LIMIT 10

// Пути от скомпрометированных субъектов безопасности
MATCH p=shortestPath(
  (u {owned:true})-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"})
)
RETURN p
```

<a id="acl-queries"></a>
### Запросы ACL

```cypher
// Пользователи с правами DCSync
MATCH (u)-[:GetChanges]->(d:Domain)
MATCH (u)-[:GetChangesAll]->(d)
RETURN u.name

// Пользователи, которые могут сбрасывать пароли
MATCH (u:User)-[:ForceChangePassword]->(t:User)
RETURN u.name, t.name

// Пользователи с правом GenericAll для компьютеров
MATCH (u:User)-[:GenericAll]->(c:Computer)
RETURN u.name, c.name
```

<a id="kerberos-queries"></a>
### Запросы Kerberos

```cypher
// Пользователи, доступные для Kerberoasting, с путём к DA
MATCH (u:User {hasspn:true})
MATCH p=shortestPath((u)-[*1..]->(g:Group {name:"DOMAIN ADMINS@DOMAIN.LOCAL"}))
RETURN u.name, LENGTH(p)

// Пользователи, доступные для AS-REP Roasting
MATCH (u:User {dontreqpreauth:true})
RETURN u.name

// Компьютеры с неограниченным делегированием (unconstrained delegation)
MATCH (c:Computer {unconstraineddelegation:true})
RETURN c.name
```

<a id="high-value-targets"></a>
### Критически важные объекты

```cypher
// Отметить критически важные цели
MATCH (u:User {name:"TARGETUSER@DOMAIN.LOCAL"})
SET u.highvalue = true

// Найти пути к критически важным целям
MATCH p=shortestPath(
  (u:User)-[*1..]->(t {highvalue:true})
)
RETURN p
```

---

<a id="-attack-path-analysis"></a>
## 🎯 Анализ путей атак

<a id="finding-attack-paths"></a>
### Поиск путей атак

```
1. Найдите исходный узел (скомпрометированного пользователя)
2. Нажмите правой кнопкой → "Mark as Owned" (отметить как скомпрометированный)
3. Выполните запрос "Shortest Path from Owned Principals" (кратчайший путь от скомпрометированных субъектов)
4. Проанализируйте связи пути
```

<a id="common-attack-edges"></a>
### Распространённые связи в путях атак

> **Уточнение DCSync:** отдельного права GetChanges или GetChangesAll недостаточно: важна их совокупность на объекте домена. BloodHound объединяет эти предпосылки в связь DCSync при последующей обработке графа. См. [объяснение разработчиков](https://specterops.io/blog/2022/08/03/introducing-bloodhound-4-2-the-azure-refactor/).

| Связь | Способ атаки |
|------|---------------|
| **MemberOf** | Членство в группе |
| **AdminTo** | Права локального администратора |
| **HasSession** | Получение учётных данных |
| **CanRDP** | Доступ по RDP |
| **CanPSRemote** | Удалённое управление PowerShell |
| **ExecuteDCOM** | Выполнение через DCOM |
| **GenericAll** | Полный контроль |
| **GenericWrite** | Изменение объекта |
| **WriteOwner** | Изменение владельца |
| **WriteDacl** | Изменение ACL |
| **ForceChangePassword** | Сброс пароля |
| **AddMember** | Добавление в группу |
| **AllExtendedRights** | DCSync и другие возможности |
| **GetChanges** | DCSync (часть 1) |
| **GetChangesAll** | DCSync (часть 2) |

<a id="exploiting-attack-edges"></a>
### Использование связей для атаки

<a id="genericall-on-user"></a>
#### GenericAll для пользователя

```powershell
# Сброс пароля
net user targetuser NewP@ssw0rd /domain

# Или с помощью PowerView
Set-DomainUserPassword -Identity targetuser -AccountPassword (ConvertTo-SecureString 'NewP@ssw0rd' -AsPlainText -Force)
```

<a id="genericall-on-computer"></a>
#### GenericAll для компьютера

```powershell
# Атака с делегированием, ограниченным на основе ресурса (RBCD)
# Использовать Rubeus + PowerMad
```

<a id="forcechangepassword"></a>
#### ForceChangePassword

```powershell
# Сброс пароля
net user targetuser NewP@ssw0rd /domain
```

<a id="addmember"></a>
#### AddMember

```powershell
# Добавить пользователя в группу
net group "Domain Admins" attackeruser /add /domain

# Или с помощью PowerView
Add-DomainGroupMember -Identity "Domain Admins" -Members "attackeruser"
```

<a id="writedacl"></a>
#### WriteDacl

```powershell
# Добавить права DCSync
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attackeruser -Rights DCSync
```

---

<a id="-bloodhound-ce"></a>
## 🆕 BloodHound CE

<a id="about-bloodhound-ce"></a>
### О BloodHound CE

> **Исправление оригинала:** CE — Community Edition. Утверждение «PostgreSQL, а не Neo4j» чрезмерно упрощено: PostgreSQL используется для данных приложения, а графовый backend может быть Neo4j или PostgreSQL в зависимости от версии и конфигурации. См. [официальное руководство по backend](https://bloodhound.specterops.io/get-started/custom-installation).

**BloodHound Community Edition** — новая версия:
- Современный веб-интерфейс
- Серверная база PostgreSQL (не Neo4j)
- REST API
- Повышенная производительность

<a id="installation"></a>
### Установка

> **Версионное примечание:** приведена прежняя команда развёртывания. Текущее руководство рекомендует BloodHound CLI, который управляет Docker Compose; начальный пароль администратора генерируется при установке. Исходная команда оставлена без изменений. См. [официальный Quickstart CE](https://bloodhound.specterops.io/get-started/quickstart/community-edition-quickstart).

```bash
# Docker Compose (рекомендуется)
curl -L https://ghst.ly/getbhce | docker compose -f - up

# Адрес: http://localhost:8080
# По умолчанию: admin / (посмотреть журналы docker)
```

<a id="sharphound-for-ce"></a>
### SharpHound для CE

```powershell
# Загрузить средство сбора, совместимое с CE
# Из релизов BloodHound CE

# Запустить сбор (тот же синтаксис)
.\SharpHound.exe -c All
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="sharphound-commands"></a>
### Команды SharpHound

| Команда | Описание |
|---------|-------------|
| `.\SharpHound.exe` | Сбор по умолчанию |
| `.\SharpHound.exe -c All` | Все методы |
| `.\SharpHound.exe -c DCOnly` | Только DC (пониженная активность) |
| `.\SharpHound.exe -c Session --loop` | Циклический сбор сеансов |
| `.\SharpHound.exe -d domain.local` | Указать домен |
| `.\SharpHound.exe --stealth` | Режим пониженной активности |

<a id="common-queries"></a>
### Распространённые запросы

| Запрос | Назначение |
|-------|---------|
| Find all Domain Admins — найти всех администраторов домена | Определить участников Domain Admins (DA) |
| Shortest Path to DA — кратчайший путь к DA | Найти путь атаки |
| Kerberoastable Users — пользователи, доступные для Kerberoasting | Найти SPN для Kerberoasting |
| AS-REP Roastable — доступные для AS-REP Roasting | Пользователи без предварительной аутентификации |
| Unconstrained Delegation — неограниченное делегирование | Использование делегирования для атаки |
| DCSync Rights — права DCSync | Найти субъекты безопасности с правами DCSync |

<a id="analysis-workflow"></a>
### Порядок анализа

```
1. Соберите данные с помощью SharpHound
2. Импортируйте их в BloodHound
3. Отметьте скомпрометированные субъекты безопасности
4. Выполните "Shortest Path from Owned" (кратчайший путь от скомпрометированного объекта)
5. Проанализируйте связи в пути атаки
6. Выполните атаки
7. Отметьте новые скомпрометированные субъекты безопасности
8. Повторите
```

<a id="key-files"></a>
### Основные файлы

> **Файлы:** имена и состав ниже — пример оригинала. SharpHound обычно создаёт ZIP с JSON; точные имена, префиксы и набор файлов зависят от версии и параметров вывода. См. [Output Options](https://bloodhound.specterops.io/collect-data/ce-collection/sharphound-flags#output-options).

```
Выходные файлы SharpHound:
- *_BloodHound.zip (данные JSON)
- computers.json
- users.json
- groups.json
- domains.json
- sessions.json
- ous.json
- gpos.json
- containers.json
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [BloodHound GitHub](https://github.com/BloodHoundAD/BloodHound)
- [SharpHound](https://github.com/BloodHoundAD/SharpHound)
- [BloodHound Docs](https://bloodhound.readthedocs.io/)
- [bloodhound.py](https://github.com/fox-it/BloodHound.py)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [Mimikatz](../../Mimikatz/translations/README.ru.md)
- [PowerShell](../../PowerShell/translations/README.ru.md)
- [Windows PrivEsc](../../Windows-PrivEsc/translations/README.ru.md)

---

<p align="center">
  <b>🩸 Найдите свой путь к правам администратора домена!</b><br>
  <i>BloodHound — надёжный помощник пентестера Active Directory</i>
</p>
