# 🔓 Hydra - Scheda di riferimento completa

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

> **Nota sugli esempi originali:** moduli e dipendenze disponibili variano con la build. Ridurre i thread non limita da solo il numero di tentativi né evita il blocco degli account. `-w` riguarda l’attesa della risposta, mentre `-T` il parallelismo totale, non un timeout. `-R` ripristina una sessione: non è un’opzione di salvataggio iniziale. L’esempio `ssh ftp mysql` non rappresenta una singola invocazione valida per più servizi. `sshkey` usa file di chiavi private: `-x` non genera chiavi SSH. Nella generazione `-x`, `!` non indica automaticamente una classe completa di caratteri speciali. I comandi sono conservati; vedi l’[aiuto nel sorgente ufficiale](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra.c) e il [modulo sshkey](https://github.com/vanhauser-thc/thc-hydra/blob/master/hydra-sshkey.c). Le stringhe dei moduli HTTP e le condizioni di successo dipendono dall’applicazione.

```
  _   _           _           
 | | | |_   _  __| |_ __ __ _ 
 | |_| | | | |/ _` | '__/ _` |
 |  _  | |_| | (_| | | | (_| |
 |_| |_|\__, |\__,_|_|  \__,_|
        |___/                  
    Lo strumento rapido per verificare gli accessi di rete
```

<p align="center">
  <img src="https://img.shields.io/badge/Hydra-Password_Cracker-red?style=for-the-badge" alt="Hydra">
  <img src="https://img.shields.io/badge/Brute-Force-orange?style=for-the-badge" alt="Brute Force">
  <img src="https://img.shields.io/badge/50+-Protocols-blue?style=for-the-badge" alt="Protocols">
  <img src="https://img.shields.io/badge/Network-Login-green?style=for-the-badge" alt="Network">
</p>

<p align="center">
  <b>🔑 Il più celebre strumento parallelo per verificare le credenziali di accesso di rete</b>
</p>

---

## 📋 Indice

- [Introduzione](#-introduzione)
- [Installazione](#-installazione)
- [Sintassi di base](#-sintassi-di-base)
- [Protocolli supportati](#-protocolli-supportati)
- [Attacchi SSH](#-attacchi-ssh)
- [Attacchi FTP](#-attacchi-ftp)
- [Attacchi ai moduli HTTP](#-attacchi-ai-moduli-http)
- [Attacchi ai database](#-attacchi-ai-database)
- [Attacchi al desktop remoto](#-attacchi-al-desktop-remoto)
- [Altri servizi](#-altri-servizi)
- [Wordlist e password](#-wordlist-e-password)
- [Opzioni avanzate](#-opzioni-avanzate)
- [Esempi pratici](#-esempi-pratici)
- [Riferimento rapido](#-riferimento-rapido)
- [Consigli e buone pratiche](#-consigli-e-buone-pratiche)
- [Risorse](#-risorse)

---

## 🎯 Introduzione

**Hydra** (THC-Hydra) è uno strumento rapido e flessibile per verificare password tramite autenticazione online. Supporta numerosi protocolli e usa connessioni parallele per aumentare la velocità.

### Funzionalità principali

| Funzionalità | Descrizione |
|---------|-------------|
| **Oltre 50 protocolli** | SSH, FTP, HTTP, RDP, SMB e altri |
| **Esecuzione parallela** | Più connessioni simultanee |
| **Flessibile** | Header personalizzati, cookie e proxy |
| **Ripresa** | Salvataggio e ripristino delle sessioni di attacco |
| **Multipiattaforma** | Linux, Windows, macOS |

### Tipi di attacco

| Tipo | Descrizione |
|------|-------------|
| **Dizionario** | Prova le password di una lista (wordlist) |
| **Brute force** | Genera tutte le combinazioni |
| **Password spraying** | Una password su più utenti |
| **Credential stuffing** | Coppie username:password |

---

## 📥 Installazione

### Kali Linux (preinstallato)
```bash
hydra -h
```

### Ubuntu/Debian
```bash
sudo apt install hydra
```

### Da sorgente
```bash
git clone https://github.com/vanhauser-thc/thc-hydra
cd thc-hydra
./configure
make
sudo make install
```

### macOS (Homebrew)
```bash
brew install hydra
```

### Verifica dell'installazione
```bash
hydra -h
hydra -V
```

---

## ⌨ Sintassi di base

### Sintassi generale
```bash
hydra [options] target service
```

### Opzioni comuni

| Opzione | Descrizione |
|--------|-------------|
| `-l` | Un nome utente |
| `-L` | Lista di nomi utente |
| `-p` | Una password |
| `-P` | Lista di password |
| `-C` | File con campi separati da due punti (user:pass) |
| `-t` | Thread (predefiniti: 16) |
| `-s` | Numero di porta |
| `-o` | File di output |
| `-f` | Interrompi alla prima coppia valida |
| `-V` | Modalità dettagliata (verbose) |
| `-vV` | Output molto dettagliato |
| `-d` | Modalità debug |

### Avvio rapido
```bash
# Un utente, lista di password
hydra -l admin -P passwords.txt target ssh

# Lista di utenti, una password
hydra -L users.txt -p password123 target ssh

# Entrambe le liste
hydra -L users.txt -P passwords.txt target ssh
```

---

## 📡 Protocolli supportati

### Elenco completo dei protocolli

```
adam6500    afp         asterisk    cisco       cisco-enable
cvs         firebird    ftp         ftps        http-form-get
http-form-post          http-get    http-head   http-post
http-proxy  http-proxy-urlenum      https-form-get
https-form-post         https-get   https-head  https-post
imap        imaps       irc         ldap2       ldap2s
ldap3       ldap3-crammd5           ldap3-digestmd5
ldap3s      ldap3s-crammd5          ldap3s-digestmd5
memcached   mongodb     mssql       mysql       nntp
oracle      oracle-listener         oracle-sid  pcanywhere
pcnfs       pop3        pop3s       postgres    radmin2
rdp         redis       rexec       rlogin      rpcap
rsh         rtsp        s7-300      sapr3       sip
smb         smtp        smtp-enum   smtps       snmp
socks5      ssh         sshkey      svn         teamspeak
telnet      vmauthd     vnc         xmpp
```

---

## 🔐 Attacchi SSH

### Attacco SSH di base
```bash
# Un utente
hydra -l root -P /usr/share/wordlists/rockyou.txt ssh://192.168.1.1

# Più utenti
hydra -L users.txt -P passwords.txt ssh://192.168.1.1

# Porta specifica
hydra -l admin -P passwords.txt -s 2222 192.168.1.1 ssh
```

### SSH con opzioni
```bash
# Limita i tentativi (evita il blocco dell'account)
hydra -l admin -P passwords.txt -t 4 192.168.1.1 ssh

# Interrompi al primo successo
hydra -l admin -P passwords.txt -f 192.168.1.1 ssh

# Output dettagliato
hydra -l admin -P passwords.txt -V 192.168.1.1 ssh
```

### Autenticazione SSH con chiave
```bash
# Uso di una chiave privata (verifica di chiavi deboli)
hydra -l root -x 6:8:a 192.168.1.1 sshkey
```

---

## 📂 Attacchi FTP

### Attacco FTP di base
```bash
# Brute force FTP
hydra -l admin -P passwords.txt ftp://192.168.1.1

# Verifica dell'accesso FTP anonimo
hydra -l anonymous -p anonymous ftp://192.168.1.1

# Più utenti
hydra -L users.txt -P passwords.txt ftp://192.168.1.1
```

### FTPS (FTP su SSL)
```bash
hydra -l admin -P passwords.txt ftps://192.168.1.1
```

---

## 🌐 Attacchi ai moduli HTTP

### HTTP-GET-FORM

```bash
hydra -l admin -P passwords.txt target http-get-form \
    "/login.php:username=^USER^&password=^PASS^:Invalid"

# Formato:
# "/path:params:failure_string"
```

### HTTP-POST-FORM (il più comune)

```bash
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:username=^USER^&password=^PASS^:Invalid credentials"
```

### Sintassi degli attacchi ai moduli

```
"/page:POST_params:Failure_string"

Dove:
- ^USER^ = Segnaposto del nome utente
- ^PASS^ = Segnaposto della password
- Failure_string = Testo mostrato quando l’accesso fallisce
```

### Rilevamento basato sul successo

```bash
# Usa S= per la stringa di successo anziché quella di errore
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:user=^USER^&pass=^PASS^:S=Welcome"
```

### Con cookie

```bash
hydra -l admin -P passwords.txt target http-post-form \
    "/login.php:user=^USER^&pass=^PASS^:Invalid:H=Cookie: PHPSESSID=abc123"
```

### Autenticazione HTTP Basic

```bash
# Autenticazione HTTP Basic
hydra -l admin -P passwords.txt http-get://192.168.1.1/admin/

# HTTPS
hydra -l admin -P passwords.txt https-get://192.168.1.1/admin/
```

### Proxy HTTP

```bash
hydra -l admin -P passwords.txt http-proxy://proxy.server:3128
```

---

## 🗄 Attacchi ai database

### MySQL

```bash
hydra -l root -P passwords.txt mysql://192.168.1.1

# Porta specifica
hydra -l root -P passwords.txt -s 3307 192.168.1.1 mysql
```

### PostgreSQL

```bash
hydra -l postgres -P passwords.txt postgres://192.168.1.1
```

### Microsoft SQL Server

```bash
hydra -l sa -P passwords.txt mssql://192.168.1.1
```

### Oracle

```bash
# Attacco al SID Oracle
hydra -l SYS -P passwords.txt oracle://192.168.1.1/SID

# Listener Oracle
hydra -l admin -P passwords.txt oracle-listener://192.168.1.1
```

### MongoDB

```bash
hydra -l admin -P passwords.txt mongodb://192.168.1.1
```

### Redis

```bash
hydra -P passwords.txt redis://192.168.1.1
```

---

## 🖥 Attacchi al desktop remoto

### RDP (desktop remoto di Windows)

```bash
hydra -l administrator -P passwords.txt rdp://192.168.1.1

# Limita i thread (RDP è lento)
hydra -l administrator -P passwords.txt -t 1 rdp://192.168.1.1
```

### VNC

```bash
# VNC (solo password)
hydra -P passwords.txt vnc://192.168.1.1

# Versione specifica di VNC
hydra -P passwords.txt -s 5901 192.168.1.1 vnc
```

### Telnet

```bash
hydra -l admin -P passwords.txt telnet://192.168.1.1
```

---

## 🔧 Altri servizi

### SMB (condivisioni Windows)

```bash
hydra -l administrator -P passwords.txt smb://192.168.1.1
```

### SMTP

```bash
# Autenticazione SMTP
hydra -l user@domain.com -P passwords.txt smtp://192.168.1.1

# Enumerazione SMTP
hydra -L users.txt smtp-enum://192.168.1.1
```

### POP3/IMAP

```bash
# POP3
hydra -l user@domain.com -P passwords.txt pop3://192.168.1.1

# IMAP
hydra -l user@domain.com -P passwords.txt imap://192.168.1.1
```

### LDAP

```bash
hydra -l "cn=admin,dc=example,dc=com" -P passwords.txt ldap://192.168.1.1
```

### SNMP

```bash
hydra -P community_strings.txt snmp://192.168.1.1
```

---

## 📚 Wordlist e password

### Wordlist diffuse

```bash
# Percorsi in Kali Linux
/usr/share/wordlists/rockyou.txt
/usr/share/wordlists/dirb/common.txt
/usr/share/seclists/Passwords/

# Password di SecLists
/usr/share/seclists/Passwords/Common-Credentials/10-million-password-list-top-100000.txt
/usr/share/seclists/Passwords/darkweb2017-top10000.txt
/usr/share/seclists/Passwords/Default-Credentials/
```

### Generazione delle password (-x)

```bash
# Genera password: min:max:charset
hydra -l admin -x 4:6:a 192.168.1.1 ssh    # 4-6 lettere minuscole
hydra -l admin -x 4:6:A 192.168.1.1 ssh    # 4-6 lettere maiuscole
hydra -l admin -x 4:6:1 192.168.1.1 ssh    # 4-6 cifre
hydra -l admin -x 4:6:aA1 192.168.1.1 ssh  # Misto

# Opzioni dei set di caratteri:
# a = minuscole    A = maiuscole
# 1 = cifre        ! = caratteri speciali
```

### File con separazione tramite due punti (-C)

```bash
# Formato user:password
hydra -C creds.txt 192.168.1.1 ssh

# creds.txt:
# admin:admin
# admin:password
# root:toor
# guest:guest
```

### Personalizzazione di nomi utente e password

```bash
# Password vuote
hydra -l admin -e n 192.168.1.1 ssh

# Prova il nome utente come password
hydra -l admin -e s 192.168.1.1 ssh

# Prova il nome utente invertito come password
hydra -l admin -e r 192.168.1.1 ssh

# Tutte le verifiche speciali
hydra -l admin -e nsr -P passwords.txt 192.168.1.1 ssh
```

---

## ⚡ Opzioni avanzate

### Output e registrazione dei log

```bash
# Salva i risultati in un file
hydra -l admin -P passwords.txt -o results.txt 192.168.1.1 ssh

# Output JSON
hydra -l admin -P passwords.txt -o results.json -b json 192.168.1.1 ssh
```

### Ripresa degli attacchi

```bash
# Salva la sessione per riprenderla
hydra -l admin -P large_wordlist.txt -o results.txt -R 192.168.1.1 ssh

# Riprendi un attacco interrotto
hydra -R
```

### Supporto dei proxy

```bash
# Proxy HTTP
export HYDRA_PROXY=http://proxy:8080
hydra -l admin -P passwords.txt 192.168.1.1 ssh

# Proxy SOCKS
export HYDRA_PROXY=socks5://127.0.0.1:9050
hydra -l admin -P passwords.txt 192.168.1.1 ssh
```

### Regolazione delle prestazioni

```bash
# Thread (predefiniti: 16)
hydra -l admin -P passwords.txt -t 32 192.168.1.1 ssh

# Attesa tra i tentativi
hydra -l admin -P passwords.txt -w 10 192.168.1.1 ssh

# Timeout della connessione
hydra -l admin -P passwords.txt -T 30 192.168.1.1 ssh
```

### Più bersagli

```bash
# Da file
hydra -L users.txt -P passwords.txt -M targets.txt ssh

# targets.txt:
# 192.168.1.1
# 192.168.1.2
# 192.168.1.3
```

---

## 🎬 Esempi pratici

### Esempio 1: brute force SSH
```bash
hydra -l root -P /usr/share/wordlists/rockyou.txt \
    -t 4 -V 192.168.1.1 ssh
```

### Esempio 2: modulo di accesso web
```bash
hydra -l admin -P passwords.txt \
    192.168.1.1 http-post-form \
    "/login:username=^USER^&password=^PASS^:Invalid"
```

### Esempio 3: accesso a WordPress
```bash
hydra -l admin -P passwords.txt \
    target.com http-post-form \
    "/wp-login.php:log=^USER^&pwd=^PASS^:Invalid username"
```

### Esempio 4: più servizi
```bash
# Analizza più servizi sullo stesso host
hydra -l admin -P passwords.txt 192.168.1.1 ssh ftp mysql
```

### Esempio 5: password spraying
```bash
hydra -L users.txt -p "Winter2024!" \
    192.168.1.1 smb
```

### Esempio 6: con proxy/Tor
```bash
export HYDRA_PROXY=socks5://127.0.0.1:9050
hydra -l admin -P passwords.txt target.com http-post-form \
    "/login:user=^USER^&pass=^PASS^:Failed"
```

### Esempio 7: HTTP Basic Auth (pannello di amministrazione)
```bash
hydra -l admin -P passwords.txt \
    https-get://192.168.1.1/admin/
```

### Esempio 8: attacco RDP (con cautela!)
```bash
hydra -l administrator -P passwords.txt \
    -t 1 -V rdp://192.168.1.1
```

---

## 📊 Riferimento rapido

### Comandi essenziali

| Attività | Comando |
|------|---------|
| Attacco SSH | `hydra -l user -P pass.txt ssh://target` |
| Attacco FTP | `hydra -l user -P pass.txt ftp://target` |
| Modulo HTTP POST | `hydra -l user -P pass.txt target http-post-form "/login:u=^USER^&p=^PASS^:Failed"` |
| Attacco RDP | `hydra -l user -P pass.txt rdp://target` |
| Salva l'output | `hydra ... -o results.txt` |
| Riprendi l'attacco | `hydra -R` |

### Opzioni comuni

| Opzione | Descrizione |
|--------|-------------|
| `-l/-L` | Nome utente/lista |
| `-p/-P` | Password/lista |
| `-C` | File user:pass |
| `-t` | Threads |
| `-s` | Porta |
| `-f` | Interrompi al successo |
| `-V` | Output dettagliato |
| `-o` | File di output |
| `-x` | Genera password |
| `-e nsr` | Prova password vuota/uguale/invertita |

---

## 💡 Consigli e buone pratiche

### Consigli sulle prestazioni

1. **Limita i thread per i servizi sensibili**
   ```bash
   # RDP, SMB - usa pochi thread
   hydra -t 1 ...
   
   # SSH - quantità moderata
   hydra -t 4 ...
   
   # Moduli web - il valore può essere maggiore
   hydra -t 16 ...
   ```

2. **Usa wordlist mirate**
   ```bash
   # Meglio di rockyou.txt:
   # - Le 1000 password più comuni
   # - Credenziali predefinite
   # - Termini specifici dell'azienda
   ```

3. **Verifica prima le policy di blocco degli account**
   ```bash
   # Prova prima una wordlist piccola
   hydra -l admin -P small.txt -t 1 target ssh
   ```

### Consigli per ridurre la visibilità

```bash
# Aggiungi ritardi
hydra -l admin -P passwords.txt -w 30 target ssh

# Pochi thread
hydra -l admin -P passwords.txt -t 2 target ssh

# Tramite proxy
export HYDRA_PROXY=socks5://127.0.0.1:9050
```

---

## ⚠ Avvertenze legali

> **ATTENZIONE:** Hydra è un potente strumento di attacco da usare soltanto per **test autorizzati**.
> 
> - ✅ Esegui test su sistemi di tua proprietà
> - ✅ Esegui test con un'autorizzazione scritta esplicita
> - ✅ Usalo in penetration test autorizzati
> - ❌ Non attaccare mai senza autorizzazione
> - ❌ Il brute force senza autorizzazione è illegale
> 
> **I tentativi di accesso non autorizzato sono illegali e possono comportare conseguenze penali.**

---

## 📚 Risorse

### Risorse ufficiali
- [THC-Hydra GitHub](https://github.com/vanhauser-thc/thc-hydra)
- [Manuale di Hydra](https://github.com/vanhauser-thc/thc-hydra/blob/master/README.md)

### Wordlists
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [Credenziali predefinite](https://github.com/ihebski/DefaultCreds-cheat-sheet)

### Schede correlate
- [Nmap](../../Nmap/translations/README.it.md)
- [Metasploit](../../Metasploit/translations/README.it.md)
- [SQLMap](../../SQLMap/translations/README.it.md)

---

<p align="center">
  <b>🔓 Padroneggia la verifica delle credenziali di rete!</b><br>
  <i>Ottieni sempre prima l'autorizzazione</i>
</p>
