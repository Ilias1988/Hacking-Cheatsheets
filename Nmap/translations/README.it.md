# 🔍 Nmap - Scheda di riferimento completa

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
    _   __                      
   / | / /___ ___  ____ _____  
  /  |/ / __ `__ \/ __ `/ __ \ 
 / /|  / / / / / / /_/ / /_/ / 
/_/ |_/_/ /_/ /_/\__,_/ .___/  
                     /_/       
    Network Mapper - Lo scanner di rete
```

<p align="center">
  <img src="https://img.shields.io/badge/Nmap-Network_Scanner-blue?style=for-the-badge" alt="Nmap">
  <img src="https://img.shields.io/badge/Reconnaissance-red?style=for-the-badge" alt="Reconnaissance">
  <img src="https://img.shields.io/badge/Port_Scanning-green?style=for-the-badge" alt="Port Scanning">
  <img src="https://img.shields.io/badge/NSE_Scripts-purple?style=for-the-badge" alt="NSE Scripts">
</p>

<p align="center">
  <b>🌐 Lo strumento più potente per la scansione e l'individuazione delle reti</b>
</p>

---

## 📋 Indice

- [Introduzione](#-introduzione)
- [Installazione](#-installazione)
- [Sintassi di base](#-sintassi-di-base)
- [Definizione dei bersagli](#-definizione-dei-bersagli)
- [Individuazione degli host](#-individuazione-degli-host)
- [Tecniche di scansione delle porte](#-tecniche-di-scansione-delle-porte)
- [Selezione delle porte](#-selezione-delle-porte)
- [Rilevamento di servizi e versioni](#-rilevamento-di-servizi-e-versioni)
- [Rilevamento del sistema operativo](#-rilevamento-del-sistema-operativo)
- [Tempi e prestazioni](#-tempi-e-prestazioni)
- [Script NSE](#-script-nse)
- [Formati di output](#-formati-di-output)
- [Elusione di firewall e IDS](#-elusione-di-firewall-e-ids)
- [Esempi pratici](#-esempi-pratici)
- [Riferimento rapido](#-riferimento-rapido)
- [Consigli e buone pratiche](#-consigli-e-buone-pratiche)
- [Risorse](#-risorse)

---

## 🎯 Introduzione

> **Nota terminologica:** “stealth” indica una scansione meno evidente in certe condizioni, non invisibile. SYN, ritardi, frammentazione ed esche non garantiscono di evitare IDS/firewall; anche “funziona sempre” per TCP Connect è una semplificazione. Vedi la [guida ufficiale Nmap](https://nmap.org/book/man-bypass-firewalls-ids.html).

**Nmap** (Network Mapper) è un'utilità gratuita e open source per individuare reti ed eseguire verifiche di sicurezza. È uno standard del settore per la scansione di rete.

### Funzionalità principali

| Funzionalità | Descrizione |
|---------|-------------|
| **Individuazione degli host** | Individua gli host attivi in una rete |
| **Scansione delle porte** | Identifica porte aperte e servizi |
| **Rilevamento delle versioni** | Determina le versioni dei servizi |
| **Rilevamento del sistema operativo** | Identifica i sistemi operativi |
| **Script NSE** | Scansione delle vulnerabilità e sfruttamento |
| **Formati di output** | Output XML, elaborabile con grep e normale |

### Stati delle porte

| Stato | Descrizione |
|-------|-------------|
| `open` | L'applicazione accetta connessioni |
| `closed` | La porta è raggiungibile ma nessuna applicazione è in ascolto |
| `filtered` | Nmap non può stabilire se la porta è aperta (firewall) |
| `unfiltered` | La porta è raggiungibile ma lo stato è sconosciuto |
| `open|filtered` | Non è possibile distinguere tra aperta e filtrata |
| `closed|filtered` | Non è possibile distinguere tra chiusa e filtrata |

---

## 📥 Installazione

### Kali Linux (preinstallato)
```bash
# Aggiorna Nmap
sudo apt update && sudo apt install nmap
```

### Ubuntu/Debian
```bash
sudo apt install nmap
```

### CentOS/RHEL
```bash
sudo yum install nmap
```

### Windows
```bash
# Scarica da https://nmap.org/download.html
# Oppure usa Chocolatey
choco install nmap
```

### macOS
```bash
brew install nmap
```

### Verifica dell'installazione
```bash
nmap --version
nmap -h          # Guida
```

---

## ⌨ Sintassi di base

### Sintassi generale
```bash
nmap [Scan Type] [Options] {target}
```

### Esempi iniziali
```bash
# Scansione di base
nmap 192.168.1.1

# Scansiona più host
nmap 192.168.1.1 192.168.1.2 192.168.1.3

# Scansiona un intervallo
nmap 192.168.1.1-254

# Scansiona l'intera sottorete
nmap 192.168.1.0/24

# Scansiona i bersagli da un file
nmap -iL targets.txt
```

---

## 🎯 Definizione dei bersagli

### Bersagli singoli
```bash
# Indirizzo IP
nmap 192.168.1.1

# Nome host
nmap scanme.nmap.org

# Dominio
nmap example.com
```

### Bersagli multipli
```bash
# Separati da spazi
nmap 192.168.1.1 192.168.1.2 192.168.1.3

# Intervallo
nmap 192.168.1.1-100

# Notazione CIDR
nmap 192.168.1.0/24

# Carattere jolly
nmap 192.168.1.*

# Intervallo di ottetti
nmap 192.168.0-255.1-254

# Combinazione di formati
nmap 192.168.1.0/24 10.0.0.1
```

### Input da file
```bash
# Leggi da file
nmap -iL targets.txt

# targets.txt:
# 192.168.1.1
# 192.168.1.0/24
# scanme.nmap.org
```

### Esclusione dei bersagli
```bash
# Escludi un singolo host
nmap 192.168.1.0/24 --exclude 192.168.1.1

# Escludi gli host elencati in un file
nmap 192.168.1.0/24 --excludefile exclude.txt
```

### Bersagli casuali
```bash
# Scansiona host casuali (per ricerca)
nmap -iR 100
```

---

## 🔎 Individuazione degli host

### Opzioni di individuazione

| Opzione | Descrizione |
|--------|-------------|
| `-sL` | Scansione elenco: elenca soltanto i bersagli |
| `-sn` | Ping scan: nessuna scansione delle porte |
| `-Pn` | Salta l'individuazione degli host (considera tutti gli host attivi) |
| `-PS` | Ping TCP SYN |
| `-PA` | Ping TCP ACK |
| `-PU` | Ping UDP |
| `-PE` | Ping ICMP echo |
| `-PP` | Ping ICMP timestamp |
| `-PM` | Ping ICMP netmask |
| `-PO` | Ping tramite protocolli IP |
| `-PR` | Ping ARP (rete locale) |

### Ping scan (senza scansione delle porte)
```bash
# Individua soltanto gli host attivi
nmap -sn 192.168.1.0/24

# Ping scan con elenco di host
nmap -sn -iL hosts.txt
```

### Salta l'individuazione degli host
```bash
# Scansiona tutti gli host (senza ping iniziale)
nmap -Pn 192.168.1.1

# Utile quando:
# - Gli host bloccano ICMP
# - La scansione attraversa un firewall
```

### Ping TCP SYN/ACK
```bash
# Ping TCP SYN sulla porta 80
nmap -PS80 192.168.1.1

# Ping TCP ACK sulla porta 80
nmap -PA80 192.168.1.1

# Più porte
nmap -PS22,80,443 192.168.1.1
```

### Ping UDP
```bash
# Ping UDP
nmap -PU53 192.168.1.1

# Più porte
nmap -PU53,161 192.168.1.1
```

### Ping ICMP
```bash
# ICMP echo
nmap -PE 192.168.1.1

# ICMP timestamp
nmap -PP 192.168.1.1

# ICMP netmask
nmap -PM 192.168.1.1
```

### Ping ARP (rete locale)
```bash
# Scansione ARP (la più veloce nella rete locale)
nmap -PR 192.168.1.0/24

# Solo scansione ARP
nmap -sn -PR 192.168.1.0/24
```

### Disattiva la risoluzione DNS
```bash
# Nessuna risoluzione DNS (più veloce)
nmap -n 192.168.1.1

# Risolvi sempre i nomi DNS
nmap -R 192.168.1.1
```

---

## 🔌 Tecniche di scansione delle porte

### Panoramica dei tipi di scansione

| Opzione | Nome | Descrizione |
|--------|------|-------------|
| `-sS` | Scansione SYN | Scansione stealth (predefinita, richiede root) |
| `-sT` | Connessione TCP | Connessione TCP completa (non richiede root) |
| `-sU` | Scansione UDP | Scansione delle porte UDP |
| `-sA` | Scansione ACK | Mappa le regole del firewall |
| `-sW` | Scansione Window | Simile alla scansione ACK, usa il campo window |
| `-sM` | Scansione Maimon | Sonda FIN/ACK |
| `-sN` | Scansione NULL | Nessun flag impostato |
| `-sF` | Scansione FIN | Flag FIN impostato |
| `-sX` | Scansione Xmas | Flag FIN, PSH e URG |

### Scansione SYN stealth (predefinita)
```bash
# Scansione più comune (richiede root)
sudo nmap -sS 192.168.1.1

# Non completa mai l'handshake TCP
# Veloce e discreta
```

### Scansione TCP Connect
```bash
# Connessione TCP completa
nmap -sT 192.168.1.1

# Non richiede root
# Più rilevabile, ma funziona sempre (affermazione dell'originale: vedi nota)
```

### Scansione UDP
```bash
# Scansione delle porte UDP
sudo nmap -sU 192.168.1.1

# La scansione UDP è LENTA
# Combina con il rilevamento delle versioni
sudo nmap -sU -sV 192.168.1.1

# Porte UDP comuni
sudo nmap -sU -p 53,67,68,69,123,161,500 192.168.1.1
```

### TCP/UDP combinati
```bash
# Scansiona sia TCP sia UDP
sudo nmap -sS -sU 192.168.1.1

# Porte specifiche
sudo nmap -sS -sU -p T:80,443,U:53,161 192.168.1.1
```

### Scansioni NULL, FIN e Xmas
```bash
# Scansione NULL (nessun flag)
sudo nmap -sN 192.168.1.1

# Scansione FIN (solo flag FIN)
sudo nmap -sF 192.168.1.1

# Scansione Xmas (FIN, PSH, URG)
sudo nmap -sX 192.168.1.1

# Utile per:
# - Aggirare firewall senza stato
# - Non funziona su Windows
```

### Scansione ACK
```bash
# Mappa le regole del firewall
sudo nmap -sA 192.168.1.1

# Determina:
# - Quali porte sono filtrate
# - Le regole del firewall
# Restituisce: unfiltered oppure filtered
```

### Scansione Window
```bash
# Simile alla scansione ACK
sudo nmap -sW 192.168.1.1

# Usa la dimensione della finestra TCP per determinare lo stato
```

### Scansione Idle/Zombie
```bash
# Massima discrezione: usa un host zombie
sudo nmap -sI zombie_host 192.168.1.1

# Il tuo IP non contatta il bersaglio (affermazione dell'originale: vedi nota)
# Richiede un host zombie adatto
```

---

## 🎚 Selezione delle porte

### Opzioni delle porte

| Opzione | Descrizione |
|--------|-------------|
| `-p <port>` | Scansiona porte specifiche |
| `-p-` | Scansiona tutte le 65535 porte |
| `-p 1-1000` | Scansiona un intervallo di porte |
| `--top-ports <n>` | Scansiona le N porte più comuni |
| `-F` | Scansione rapida (le 100 porte più comuni) |
| `-r` | Scansiona le porte in sequenza |

### Porte specifiche
```bash
# Porta singola
nmap -p 80 192.168.1.1

# Più porte
nmap -p 80,443,22 192.168.1.1

# Intervallo di porte
nmap -p 1-1000 192.168.1.1

# Tutte le porte
nmap -p- 192.168.1.1

# Combinazione di formati
nmap -p 22,80,443,1000-2000 192.168.1.1
```

### Porte per protocollo

> **Nota sui protocolli:** i prefissi `T:` e `U:` selezionano gli intervalli di porte, ma non attivano da soli la scansione UDP. Gli esempi originali vanno letti insieme al tipo di scansione scelto, come `-sU` per UDP.
```bash
# Porte TCP
nmap -p T:80,443 192.168.1.1

# Porte UDP
nmap -p U:53,161 192.168.1.1

# Entrambi
nmap -p T:80,443,U:53 192.168.1.1
```

### Porte più comuni
```bash
# Le 100 porte più comuni (veloce)
nmap -F 192.168.1.1

# Le 10 porte più comuni
nmap --top-ports 10 192.168.1.1

# Le 1000 porte più comuni (predefinito)
nmap --top-ports 1000 192.168.1.1
```

### Riferimento delle porte comuni

| Porta | Servizio |
|------|---------|
| 21 | FTP |
| 22 | SSH |
| 23 | Telnet |
| 25 | SMTP |
| 53 | DNS |
| 80 | HTTP |
| 110 | POP3 |
| 135 | MSRPC |
| 139 | NetBIOS |
| 143 | IMAP |
| 443 | HTTPS |
| 445 | SMB |
| 993 | IMAPS |
| 995 | POP3S |
| 1433 | MSSQL |
| 1521 | Oracle |
| 3306 | MySQL |
| 3389 | RDP |
| 5432 | PostgreSQL |
| 5900 | VNC |
| 8080 | HTTP-Proxy |

---

## 🔬 Rilevamento di servizi e versioni

### Opzioni di rilevamento delle versioni

| Opzione | Descrizione |
|--------|-------------|
| `-sV` | Rilevamento delle versioni |
| `--version-intensity <0-9>` | Intensità delle sonde |
| `--version-light` | Rilevamento leggero delle versioni (intensità 2) |
| `--version-all` | Prova tutte le sonde (intensità 9) |
| `--version-trace` | Debug del rilevamento delle versioni |

### Rilevamento di base delle versioni
```bash
# Rileva le versioni dei servizi
nmap -sV 192.168.1.1

# Output tipico:
# PORT    STATE SERVICE VERSION
# 22/tcp  open  ssh     OpenSSH 7.9
# 80/tcp  open  http    Apache httpd 2.4.38
```

### Intensità del rilevamento delle versioni
```bash
# Scansione leggera (più veloce)
nmap -sV --version-light 192.168.1.1

# Tutte le sonde (approfondita)
nmap -sV --version-all 192.168.1.1

# Intensità personalizzata (0-9)
nmap -sV --version-intensity 5 192.168.1.1
```

---

## 💻 Rilevamento del sistema operativo

### Opzioni di rilevamento del sistema operativo

| Opzione | Descrizione |
|--------|-------------|
| `-O` | Abilita il rilevamento del sistema operativo |
| `--osscan-limit` | Esegue il rilevamento solo sugli host promettenti |
| `--osscan-guess` | Stima il sistema operativo in modo più aggressivo |
| `--max-os-tries` | Numero massimo di tentativi di rilevamento del sistema operativo |

### Rilevamento di base del sistema operativo
```bash
# Rileva il sistema operativo (richiede root)
sudo nmap -O 192.168.1.1

# Esempio di output:
# Running: Linux 3.X|4.X
# OS CPE: cpe:/o:linux:linux_kernel:3
# OS details: Linux 3.10 - 4.11
```

### Stima più aggressiva del sistema operativo
```bash
# Stima più aggressiva
sudo nmap -O --osscan-guess 192.168.1.1
```

### Scansione aggressiva (-A)
```bash
# Abilita rilevamento del sistema operativo e delle versioni, script e traceroute
sudo nmap -A 192.168.1.1

# Equivale a: -O -sV -sC --traceroute
```

---

## ⏱ Tempi e prestazioni

### Profili temporali

| Opzione | Nome | Descrizione |
|--------|------|-------------|
| `-T0` | Paranoid | Elusione IDS, molto lento |
| `-T1` | Sneaky | Elusione IDS, lento |
| `-T2` | Polite | Meno banda, più lento |
| `-T3` | Normal | Tempi predefiniti |
| `-T4` | Aggressive | Veloce, per reti affidabili |
| `-T5` | Insane | Molto veloce, può non rilevare alcune porte |

### Utilizzo
```bash
# Lento e discreto
nmap -T1 192.168.1.1

# Normale (predefinito)
nmap -T3 192.168.1.1

# Veloce
nmap -T4 192.168.1.1

# Molto veloce (rete locale)
nmap -T5 192.168.1.1
```

### Regolazione dettagliata dei tempi

| Opzione | Descrizione |
|--------|-------------|
| `--min-rate <n>` | Numero minimo di pacchetti al secondo |
| `--max-rate <n>` | Numero massimo di pacchetti al secondo |
| `--min-parallelism <n>` | Numero minimo di sonde parallele |
| `--max-parallelism <n>` | Numero massimo di sonde parallele |
| `--min-hostgroup <n>` | Numero minimo di host in parallelo |
| `--max-hostgroup <n>` | Numero massimo di host in parallelo |
| `--host-timeout <time>` | Interrompe i tentativi sull'host dopo il tempo indicato |
| `--scan-delay <time>` | Ritardo tra le sonde |
| `--max-retries <n>` | Numero massimo di ritentativi delle sonde |

```bash
# Invia almeno 1000 pacchetti al secondo
nmap --min-rate 1000 192.168.1.0/24

# Massimo 100 pacchetti al secondo (rallenta)
nmap --max-rate 100 192.168.1.1

# Interrompi i tentativi sull'host dopo 30 minuti
nmap --host-timeout 30m 192.168.1.0/24

# Ritardo tra le sonde
nmap --scan-delay 1s 192.168.1.1
```

---

## 📜 Script NSE

### Categorie degli script

| Categoria | Descrizione |
|----------|-------------|
| `auth` | Bypass dell'autenticazione |
| `broadcast` | Individuazione di host tramite broadcast |
| `brute` | Attacchi brute force |
| `default` | Script predefiniti (-sC) |
| `discovery` | Individuazione dei servizi |
| `dos` | Negazione del servizio (DoS) |
| `exploit` | Sfruttamento delle vulnerabilità |
| `external` | Interrogazione di servizi esterni |
| `fuzzer` | Fuzzing (test con input variabili) |
| `intrusive` | Può causare l'arresto dei servizi |
| `malware` | Rilevamento di malware |
| `safe` | Script classificati come safe |
| `version` | Rilevamento delle versioni |
| `vuln` | Rilevamento delle vulnerabilità |

### Opzioni degli script

| Opzione | Descrizione |
|--------|-------------|
| `-sC` | Script predefiniti |
| `--script <scripts>` | Esegui script specifici |
| `--script-args <args>` | Argomenti degli script |
| `--script-updatedb` | Aggiorna il database degli script |
| `--script-help <script>` | Guida dello script |

### Script predefiniti
```bash
# Esegui gli script predefiniti
nmap -sC 192.168.1.1

# Con rilevamento delle versioni (consigliato)
nmap -sV -sC 192.168.1.1
```

### Esecuzione di script specifici
```bash
# Script singolo
nmap --script http-title 192.168.1.1

# Più script
nmap --script http-title,http-headers 192.168.1.1

# Categoria
nmap --script vuln 192.168.1.1

# Carattere jolly
nmap --script "http-*" 192.168.1.1

# Combina categorie
nmap --script "vuln and safe" 192.168.1.1

# Escludi script
nmap --script "not intrusive" 192.168.1.1
```

### Script più usati

#### Scansione delle vulnerabilità
```bash
# Tutti gli script di rilevamento delle vulnerabilità
nmap --script vuln 192.168.1.1

# Verifica di una CVE specifica
nmap --script smb-vuln-ms17-010 192.168.1.1

# Heartbleed
nmap --script ssl-heartbleed 192.168.1.1

# ShellShock
nmap --script http-shellshock 192.168.1.1
```

#### Script HTTP
```bash
# Enumerazione HTTP
nmap --script http-enum 192.168.1.1

# Header HTTP
nmap --script http-headers 192.168.1.1

# Metodi HTTP
nmap --script http-methods 192.168.1.1

# Titolo HTTP
nmap --script http-title 192.168.1.1

# Robots.txt
nmap --script http-robots.txt 192.168.1.1
```

#### Script SMB
```bash
# Enumerazione SMB
nmap --script smb-enum-shares 192.168.1.1
nmap --script smb-enum-users 192.168.1.1
nmap --script smb-os-discovery 192.168.1.1

# Vulnerabilità SMB
nmap --script smb-vuln* 192.168.1.1
```

#### Script SSH
```bash
# Metodi di autenticazione SSH
nmap --script ssh-auth-methods 192.168.1.1

# Chiave host SSH
nmap --script ssh-hostkey 192.168.1.1

# Brute force SSH
nmap --script ssh-brute 192.168.1.1
```

#### Script DNS
```bash
# Brute force DNS
nmap --script dns-brute example.com

# Trasferimento di zona DNS
nmap --script dns-zone-transfer example.com
```

#### Script MySQL/database
```bash
# Informazioni MySQL
nmap --script mysql-info 192.168.1.1

# Enumerazione MySQL
nmap --script mysql-enum 192.168.1.1

# Brute force MySQL
nmap --script mysql-brute 192.168.1.1
```

### Argomenti degli script
```bash
# Brute force con wordlist
nmap --script ssh-brute --script-args userdb=users.txt,passdb=passwords.txt 192.168.1.1

# Brute force dell'autenticazione HTTP
nmap --script http-brute --script-args http-brute.path=/admin 192.168.1.1
```

---

## 📄 Formati di output

### Opzioni di output

| Opzione | Descrizione |
|--------|-------------|
| `-oN <file>` | Output normale |
| `-oX <file>` | Output XML |
| `-oG <file>` | Output elaborabile con grep |
| `-oA <basename>` | Tutti i formati |
| `-oS <file>` | Output in stile script kiddie |

### Salvataggio dell'output
```bash
# Output normale
nmap -oN scan.txt 192.168.1.1

# Output XML (per altri strumenti)
nmap -oX scan.xml 192.168.1.1

# Output elaborabile con grep
nmap -oG scan.gnmap 192.168.1.1

# Tutti i formati insieme
nmap -oA scan 192.168.1.1
# Crea: scan.nmap, scan.xml, scan.gnmap
```

### Livello di dettaglio e debug

> **Precisazione sull’output:** `--open` include anche stati potenzialmente aperti, come `open|filtered` e `unfiltered`, non soltanto `open`. Il formato `-oG` è deprecato; per elaborazioni strutturate è preferibile XML. Vedi la [documentazione ufficiale](https://nmap.org/book/man-output.html).

| Opzione | Descrizione |
|--------|-------------|
| `-v` | Aumenta il livello di dettaglio |
| `-vv` | Più dettagli |
| `-d` | Modalità debug |
| `-dd` | Più informazioni di debug |
| `--reason` | Mostra il motivo dello stato della porta |
| `--open` | Mostra solo le porte aperte |
| `--packet-trace` | Mostra tutti i pacchetti |

```bash
# Output dettagliato
nmap -v 192.168.1.1

# Output molto dettagliato
nmap -vv 192.168.1.1

# Mostra solo le porte aperte
nmap --open 192.168.1.1

# Mostra il motivo
nmap --reason 192.168.1.1
```

---

## 🛡 Elusione di firewall e IDS

### Tecniche di elusione

| Opzione | Descrizione |
|--------|-------------|
| `-f` | Frammenta i pacchetti |
| `--mtu <size>` | Dimensione MTU personalizzata |
| `-D <decoys>` | Usa esche (decoy) |
| `-S <IP>` | Falsifica l'IP sorgente |
| `--source-port <port>` | Falsifica la porta sorgente |
| `--data-length <n>` | Aggiunge dati casuali |
| `--randomize-hosts` | Ordine casuale degli host |
| `--spoof-mac <mac>` | Falsifica l'indirizzo MAC |
| `--badsum` | Invia checksum errati |

### Frammentazione dei pacchetti
```bash
# Frammenta i pacchetti
nmap -f 192.168.1.1

# MTU personalizzata
nmap --mtu 16 192.168.1.1
```

### Esche (decoy)
```bash
# Usa esche (decoy)
nmap -D decoy1,decoy2,ME 192.168.1.1

# Esche casuali
nmap -D RND:5 192.168.1.1
```

### Falsificazione dell'IP e della porta sorgente
```bash
# Falsifica l'IP sorgente
nmap -S 192.168.1.100 -e eth0 192.168.1.1

# Falsifica la porta sorgente (porte comunemente consentite)
nmap --source-port 53 192.168.1.1
nmap --source-port 80 192.168.1.1
```

### Falsificazione dell'indirizzo MAC
```bash
# MAC casuale
nmap --spoof-mac 0 192.168.1.1

# Produttore specifico
nmap --spoof-mac Apple 192.168.1.1

# MAC specifico
nmap --spoof-mac 00:11:22:33:44:55 192.168.1.1
```

### Aggiunta di dati ai pacchetti
```bash
# Aggiungi dati casuali ai pacchetti
nmap --data-length 25 192.168.1.1
```

---

## 🎬 Esempi pratici

### Esempio 1: individuazione rapida degli host di rete
```bash
# Individua gli host attivi
nmap -sn 192.168.1.0/24
```

### Esempio 2: scansione di base delle porte
```bash
# Scansione con rilevamento delle versioni
nmap -sV 192.168.1.1
```

### Esempio 3: scansione completa
```bash
# Scansione completa con rilevamento del sistema operativo e script
sudo nmap -sS -sV -O -sC 192.168.1.1
```

### Esempio 4: scansione completa aggressiva
```bash
# Tutte le porte, scansione aggressiva
sudo nmap -A -T4 -p- 192.168.1.1
```

### Esempio 5: scansione delle vulnerabilità
```bash
# Verifica la presenza di vulnerabilità
nmap --script vuln 192.168.1.1
```

### Esempio 6: scansione stealth
```bash
# Scansione discreta
sudo nmap -sS -T2 -f --data-length 25 192.168.1.1
```

### Esempio 7: scansione di un server web
```bash
# Scansione web completa
nmap -sV -p 80,443,8080,8443 --script "http-*" 192.168.1.1
```

### Esempio 8: enumerazione SMB
```bash
# Enumerazione SMB completa
nmap -p 445 --script smb-enum-shares,smb-enum-users,smb-vuln* 192.168.1.1
```

### Esempio 9: scansione dell'intera sottorete
```bash
# Scansiona l'intera sottorete
nmap -sS -sV -O -T4 --open 192.168.1.0/24 -oA subnet_scan
```

### Esempio 10: servizi UDP
```bash
# Servizi UDP comuni
sudo nmap -sU -sV -p 53,67,68,69,123,161,500 192.168.1.1
```

---

## 📊 Riferimento rapido

### Comandi essenziali

| Comando | Descrizione |
|---------|-------------|
| `nmap <target>` | Scansione di base |
| `nmap -sn <target>` | Solo ping scan |
| `nmap -Pn <target>` | Salta l'individuazione degli host |
| `nmap -sS <target>` | Scansione SYN stealth |
| `nmap -sV <target>` | Rilevamento delle versioni |
| `nmap -O <target>` | Rilevamento del sistema operativo |
| `nmap -A <target>` | Scansione aggressiva |
| `nmap -sC <target>` | Script predefiniti |
| `nmap -p- <target>` | Tutte le porte |
| `nmap --script vuln <target>` | Scansione delle vulnerabilità |

### Velocità e discrezione

| Esigenza | Opzioni |
|------|---------|
| **Veloce** | `-T4 --min-rate 1000` |
| **Discreto** | `-T1 -f --data-length 25` |
| **Bilanciato** | `-T3` (predefinito) |

### Scansioni comuni

```bash
# Scansione rapida
nmap -F 192.168.1.1

# Scansione TCP completa
nmap -sT -p- 192.168.1.1

# Scansione dei servizi
nmap -sV 192.168.1.1

# Scansione delle vulnerabilità
nmap --script vuln 192.168.1.1

# Tutto
sudo nmap -A -T4 -p- 192.168.1.1
```

---

## 💡 Consigli e buone pratiche

### Consigli per le prestazioni

1. **Usa la scansione SYN (predefinita)**
   ```bash
   # Richiede root, ma è la più veloce
   sudo nmap -sS 192.168.1.1
   ```

2. **Limita inizialmente l'intervallo delle porte**
   ```bash
   # Inizia dalle porte più comuni
   nmap --top-ports 100 192.168.1.1
   ```

3. **Aumenta la velocità sulle reti locali**
   ```bash
   nmap -T5 --min-rate 5000 192.168.1.0/24
   ```

### Consigli per l'accuratezza

1. **Usa più tipi di scansione**
   ```bash
   # TCP e UDP
   sudo nmap -sS -sU -sV 192.168.1.1
   ```

2. **Aumenta i tentativi sulle reti inaffidabili**
   ```bash
   nmap --max-retries 3 192.168.1.1
   ```

### Aspetti legali

> ⚠️ **IMPORTANTE:** esegui scansioni solo sulle reti di tua proprietà o per cui hai il permesso.

- ✅ Le tue reti
- ✅ Ambienti di laboratorio
- ✅ Penetration test autorizzati
- ❌ Host casuali su Internet
- ❌ Reti per cui non hai il permesso

---

## ⚠ Avvertenza legale

> **ATTENZIONE:** Nmap è uno strumento potente da usare esclusivamente per **scansioni autorizzate**.
> 
> - ✅ Esegui scansioni sulle tue reti
> - ✅ Esegui scansioni con esplicito permesso scritto
> - ✅ Usalo in penetration test autorizzati
> - ❌ Non eseguire mai scansioni di reti senza autorizzazione
> - ❌ Le scansioni non autorizzate possono essere illegali
> 
> **Le scansioni di rete non autorizzate sono illegali in molte giurisdizioni.**

---

## 📚 Risorse

### Risorse ufficiali
- [Sito ufficiale di Nmap](https://nmap.org/)
- [Guida di riferimento di Nmap](https://nmap.org/book/man.html)
- [Documentazione degli script NSE](https://nmap.org/nsedoc/)

### Risorse per l'apprendimento
- [Libro Nmap Network Scanning](https://nmap.org/book/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Schede correlate
- [Metasploit Framework](../../Metasploit/translations/README.it.md)
- [SQLMap](../../SQLMap/README.md)
- [Burp Suite](../../Burp-Suite/translations/README.it.md)

---

<p align="center">
  <b>🔍 Padroneggia la ricognizione di rete!</b><br>
  <i>Conosci la tua rete prima degli attaccanti</i>
</p>
