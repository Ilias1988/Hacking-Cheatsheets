# 🔨 John the Ripper - Scheda di riferimento completa

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

> **Nota sulla compatibilità:** molte opzioni e gli strumenti `2john` appartengono a Jumbo; nomi dei pacchetti, percorsi, estensioni degli script e supporto di `--version` dipendono dalla build. `--list=formats | wc -l` conta righe, non necessariamente singoli formati. L’autorilevamento può essere ambiguo; hash abbreviati con `...` e campi LM/NTLM troncati sono esempi, non dati validi. L’algoritmo predefinito di `/etc/shadow` dipende dalla distribuzione. Le regole operano su caratteri: `$123` e `^2023` non aggiungono automaticamente le intere stringhe “123” e “2023”. Il confronto con Hashcat è indicativo, non un benchmark. Vedi le [opzioni ufficiali](https://www.openwall.com/john/doc/OPTIONS.shtml), la [sintassi delle regole](https://www.openwall.com/john/doc/RULES.shtml) e la [documentazione Jumbo](https://github.com/openwall/john/tree/bleeding-jumbo/doc).

```
       __      __         
      / /___  / /_  ____  
 __  / / __ \/ __ \/ __ \ 
/ /_/ / /_/ / / / / / / / 
\____/\____/_/ /_/_/ /_/  
  Lo strumento per il cracking delle password     
```

<p align="center">
  <img src="https://img.shields.io/badge/John-Password_Cracker-red?style=for-the-badge" alt="John">
  <img src="https://img.shields.io/badge/Hash-Cracking-orange?style=for-the-badge" alt="Hash">
  <img src="https://img.shields.io/badge/Open-Source-blue?style=for-the-badge" alt="Open Source">
  <img src="https://img.shields.io/badge/Multi-Platform-green?style=for-the-badge" alt="Multi Platform">
</p>

<p align="center">
  <b>🔑 Il celebre strumento di verifica della sicurezza e recupero delle password</b>
</p>

---

## 📋 Indice

- [Introduzione](#-introduzione)
- [Installazione](#-installazione)
- [Sintassi di base](#-sintassi-di-base)
- [Modalità di cracking](#-modalità-di-cracking)
- [Estrazione degli hash](#-estrazione-degli-hash)
- [Tipi di hash comuni](#-tipi-di-hash-comuni)
- [Cracking delle password Linux](#-cracking-delle-password-linux)
- [Cracking delle password Windows](#-cracking-delle-password-windows)
- [Cracking delle password dei file](#-cracking-delle-password-dei-file)
- [Regole e trasformazioni delle parole](#-regole-e-trasformazioni-delle-parole)
- [Gestione delle sessioni](#-gestione-delle-sessioni)
- [Regolazione delle prestazioni](#-regolazione-delle-prestazioni)
- [Esempi pratici](#-esempi-pratici)
- [Riferimento rapido](#-riferimento-rapido)
- [Consigli e buone pratiche](#-consigli-e-buone-pratiche)
- [Risorse](#-risorse)

---

## 🎯 Introduzione

**John the Ripper** (John/JtR) è uno strumento gratuito e open source per verificare la sicurezza e recuperare password. È progettato per individuare password deboli e recuperare password a partire dai loro hash (cracking).

### Funzionalità principali

| Funzionalità | Descrizione |
|---------|-------------|
| **Più formati** | Supporta centinaia di tipi di hash |
| **Più modalità** | Single, Wordlist, Incremental |
| **Regole personalizzate** | Potenti regole di trasformazione delle parole (word mangling) |
| **Rilevamento automatico** | Rilevamento automatico del formato degli hash |
| **Multipiattaforma** | Linux, Windows, macOS |
| **Supporto GPU** | Accelerazione OpenCL (Jumbo) |

### John a confronto con Hashcat

| Funzionalità | John | Hashcat |
|---------|------|---------|
| **Ideale per** | Cracking su CPU | Cracking su GPU |
| **Facilità d'uso** | Più semplice | Più complesso |
| **Rilevamento automatico** | ✅ Sì | ❌ No |
| **Regole** | Potenti | Molto potenti |
| **Tipi di hash** | Molti | Di più |
| **Velocità (GPU)** | Buona | Migliore |

### Versioni

| Versione | Descrizione |
|---------|-------------|
| **John (Core)** | Versione di base |
| **John Jumbo** | Versione migliorata dalla comunità (consigliata) |

---

## 📥 Installazione

### Kali Linux (preinstallato)
```bash
john --version
```

### Ubuntu/Debian
```bash
# Versione standard
sudo apt install john

# Versione Jumbo (consigliata)
sudo apt install john-jumbo
```

### Da sorgente (Jumbo)
```bash
git clone https://github.com/openwall/john
cd john/src
./configure
make -s clean && make -sj4
cd ../run
./john --version
```

### macOS (Homebrew)
```bash
brew install john-jumbo
```

### Windows
```bash
# Scarica dal sito ufficiale
https://www.openwall.com/john/

# Oppure usa Chocolatey
choco install john
```

### Verifica dell'installazione
```bash
john --version
john --list=formats | wc -l    # Conta i formati supportati
```

---

## ⌨ Sintassi di base

### Sintassi generale
```bash
john [options] [password_file]
```

### Opzioni comuni

| Opzione | Descrizione |
|--------|-------------|
| `--wordlist=` | Percorso della wordlist |
| `--format=` | Specifica il formato dell'hash |
| `--rules` | Abilita le regole di trasformazione delle parole |
| `--show` | Mostra le password recuperate |
| `--single` | Modalità Single crack |
| `--incremental` | Modalità brute force |
| `--session=` | Assegna un nome alla sessione |
| `--restore` | Ripristina la sessione precedente |
| `--pot=` | File pot personalizzato |

### Avvio rapido
```bash
# Rileva automaticamente ed esegui il cracking
john hashes.txt

# Con wordlist
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Mostra le password recuperate
john --show hashes.txt
```

---

## 🎮 Modalità di cracking

### Modalità Single crack

Usa i nomi di accesso e le informazioni GECOS per generare possibili password.

```bash
# Modalità Single (la più rapida, prova varianti del nome utente)
john --single hashes.txt

# Con specifica del formato
john --single --format=sha512crypt hashes.txt
```

### Modalità Wordlist

Usa parole di un dizionario con regole facoltative.

```bash
# Attacco di base con wordlist
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Wordlist con regole
john --wordlist=wordlist.txt --rules hashes.txt

# Insieme di regole specifico
john --wordlist=wordlist.txt --rules=Jumbo hashes.txt
```

### Modalità Incremental (brute force)

Prova tutte le possibili combinazioni di caratteri.

```bash
# Incremental predefinita (tutti i caratteri)
john --incremental hashes.txt

# Set di caratteri specifico
john --incremental=Digits hashes.txt      # Solo cifre
john --incremental=Lower hashes.txt       # Solo minuscole
john --incremental=Upper hashes.txt       # Solo maiuscole
john --incremental=Alpha hashes.txt       # Solo lettere
john --incremental=Alnum hashes.txt       # Alfanumerico
john --incremental=LowerNum hashes.txt    # Minuscole e cifre
```

### Modalità Mask

Brute force basato su uno schema (maschera).

```bash
# Attacco con maschera (? = segnaposto)
john --mask='?l?l?l?l?d?d' hashes.txt

# Segnaposto:
# ?l = minuscole    ?u = maiuscole
# ?d = cifre        ?s = caratteri speciali
# ?a = tutti        ?h = esadecimale minuscolo
```

### Modalità External

Algoritmi di cracking personalizzati.

```bash
john --external=Filter_Alpha hashes.txt
```

---

## 🔍 Estrazione degli hash

### Password Linux

```bash
# Combina i file passwd e shadow
unshadow /etc/passwd /etc/shadow > unshadowed.txt

# Esegui il cracking del file combinato
john unshadowed.txt
```

### Hash Windows

```bash
# Dal file SAM (usa pwdump, secretsdump, ecc.)
# Formato: user:id:LM_hash:NTLM_hash:::

# Con hashdump di Metasploit
meterpreter> hashdump
Administrator:500:aad3b435b51404ee:31d6cfe0d16ae931:::

# Salva in un file ed esegui il cracking
john --format=nt hashes.txt
```

### Password dei file (strumenti 2john)

```bash
# File ZIP
zip2john protected.zip > zip_hash.txt

# File RAR
rar2john protected.rar > rar_hash.txt

# File PDF
pdf2john protected.pdf > pdf_hash.txt

# Documenti Office
office2john document.docx > office_hash.txt

# Chiavi SSH
ssh2john id_rsa > ssh_hash.txt

# File 7zip
7z2john archive.7z > 7z_hash.txt

# Database KeePass
keepass2john database.kdbx > keepass_hash.txt
```

---

## 🔢 Tipi di hash comuni

### Tabella dei formati degli hash

| Tipo di hash | Flag del formato | Esempio |
|-----------|-------------|---------|
| MD5 | `--format=raw-md5` | `5d41402abc4b2a76b9719d911017c592` |
| SHA1 | `--format=raw-sha1` | `aaf4c61ddcc5e8a2...` |
| SHA256 | `--format=raw-sha256` | `2cf24dba5fb0a30e...` |
| SHA512 | `--format=raw-sha512` | `cf83e1357eefb8bd...` |
| NTLM | `--format=nt` | `31d6cfe0d16ae931b73c59d7e0c089c0` |
| LM | `--format=lm` | `aad3b435b51404ee` |
| MySQL | `--format=mysql-sha1` | `*2470C0C06DEE42...` |
| bcrypt | `--format=bcrypt` | `$2a$10$N9qo8u...` |
| Linux SHA512 | `--format=sha512crypt` | `$6$rounds=5000$...` |
| Linux SHA256 | `--format=sha256crypt` | `$5$rounds=5000$...` |
| Linux MD5 | `--format=md5crypt` | `$1$salt$hash` |

### Elenca tutti i formati
```bash
# Elenca tutti i formati supportati
john --list=formats

# Cerca un formato specifico
john --list=formats | grep -i ntlm
john --list=formats | grep -i sha

# Conta i formati
john --list=formats | wc -l
```

### Identifica il tipo di hash
```bash
# Usa hash-identifier
hash-identifier

# Oppure hashid
hashid -m '$6$salt$hash...'
```

---

## 🐧 Cracking delle password Linux

### Prepara gli hash
```bash
# Combina passwd e shadow
sudo unshadow /etc/passwd /etc/shadow > linux_hashes.txt

# Oppure estrai soltanto l'hash
sudo cat /etc/shadow | grep username
```

### Esempi di formati degli hash
```
# Formato di /etc/shadow:
# username:$id$salt$hash:...

# $1$ = MD5
# $5$ = SHA-256
# $6$ = SHA-512 (il più comune nei Linux moderni nell'originale)
# $y$ = yescrypt (il più recente nell'originale)
```

### Esegui il cracking degli hash Linux
```bash
# Rilevamento automatico (consigliato)
john linux_hashes.txt

# Specifica il formato
john --format=sha512crypt linux_hashes.txt

# Con wordlist
john --wordlist=/usr/share/wordlists/rockyou.txt linux_hashes.txt

# Mostra i risultati
john --show linux_hashes.txt
```

---

## 🪟 Cracking delle password Windows

### Tipi di hash

| Tipo | Descrizione | Formato |
|------|-------------|--------|
| **LM** | Storico (debole) | `--format=lm` |
| **NTLM** | Windows moderno | `--format=nt` |
| **NTLMv2** | Autenticazione di rete | `--format=netntlmv2` |

### Cracking degli hash NTLM
```bash
# Formato degli hash NTLM:
# user:rid:lm_hash:ntlm_hash:::

# Esegui il cracking NTLM
john --format=nt windows_hashes.txt

# Con wordlist
john --format=nt --wordlist=rockyou.txt windows_hashes.txt

# Con regole
john --format=nt --wordlist=rockyou.txt --rules windows_hashes.txt
```

### Estrai gli hash Windows
```bash
# Con Metasploit
meterpreter> hashdump

# Con secretsdump (Impacket)
secretsdump.py -sam sam -system system LOCAL

# Con pwdump
pwdump7.exe
```

---

## 📄 Cracking delle password dei file

### File ZIP
```bash
# Estrai l'hash
zip2john protected.zip > zip.hash

# Esegui il cracking
john --wordlist=rockyou.txt zip.hash

# Mostra la password
john --show zip.hash
```

### File RAR
```bash
rar2john protected.rar > rar.hash
john --wordlist=rockyou.txt rar.hash
```

### File PDF
```bash
pdf2john protected.pdf > pdf.hash
john --wordlist=rockyou.txt pdf.hash
```

### Documenti Office
```bash
# Word, Excel, PowerPoint
office2john document.docx > office.hash
john --wordlist=rockyou.txt office.hash
```

### Chiavi private SSH
```bash
ssh2john id_rsa > ssh.hash
john --wordlist=rockyou.txt ssh.hash
```

### Database KeePass
```bash
keepass2john database.kdbx > keepass.hash
john --wordlist=rockyou.txt keepass.hash
```

---

## ⚙ Regole e trasformazioni delle parole

### Abilita le regole
```bash
# Regole predefinite
john --wordlist=wordlist.txt --rules hashes.txt

# Insieme di regole specifico
john --wordlist=wordlist.txt --rules=Single hashes.txt
john --wordlist=wordlist.txt --rules=Wordlist hashes.txt
john --wordlist=wordlist.txt --rules=Jumbo hashes.txt
john --wordlist=wordlist.txt --rules=KoreLogic hashes.txt
```

### Elenca le regole disponibili
```bash
john --list=rules
```

### Operazioni comuni delle regole

| Regola | Descrizione | Esempio |
|------|-------------|---------|
| `:` | Non modificare nulla | password → password |
| `l` | Converti tutto in minuscolo | PassWord → password |
| `u` | Converti tutto in maiuscolo | password → PASSWORD |
| `c` | Iniziale maiuscola | password → Password |
| `r` | Inverti | password → drowssap |
| `d` | Duplica | pass → passpass |
| `$X` | Aggiungi il carattere X alla fine | pass → passX |
| `^X` | Aggiungi il carattere X all'inizio | pass → Xpass |
| `sXY` | Sostituisci X con Y | password → pa$$word |

### Regole personalizzate
```bash
# Aggiungi a john.conf [List.Rules:Custom]
[List.Rules:Custom]
:
l
u
c
$1
$!
$123
^2023
```

---

## 💾 Gestione delle sessioni

### Crea una sessione con nome
```bash
john --wordlist=rockyou.txt --session=mycrack hashes.txt
```

### Ripristina la sessione
```bash
# Ripristina una sessione interrotta
john --restore=mycrack

# Ripristina la sessione predefinita
john --restore
```

### Verifica lo stato
```bash
# Premi un tasto durante il cracking per visualizzare lo stato
# Oppure consulta il file .rec
cat ~/.john/mycrack.rec
```

### File pot
```bash
# File pot predefinito
~/.john/john.pot

# File pot personalizzato
john --pot=custom.pot hashes.txt

# Mostra tutte le password recuperate
john --show --pot=custom.pot hashes.txt
```

---

## 🚀 Regolazione delle prestazioni

### Fork (più processi)
```bash
# Usa più core della CPU
john --fork=4 --wordlist=rockyou.txt hashes.txt
```

### OpenCL (GPU)
```bash
# Elenca i dispositivi OpenCL
john --list=opencl-devices

# Usa la GPU
john --format=sha512crypt-opencl --wordlist=rockyou.txt hashes.txt
```

### Limita i candidati
```bash
# Limita la lunghezza delle password
john --min-length=6 --max-length=12 --incremental hashes.txt
```

### Uso della memoria
```bash
# Riduci l'uso della memoria
john --mem-file-size=32 --wordlist=huge.txt hashes.txt
```

---

## 🎬 Esempi pratici

### Esempio 1: file shadow di Linux
```bash
# Combina i file
sudo unshadow /etc/passwd /etc/shadow > hashes.txt

# Cracking con rockyou
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Mostra i risultati
john --show hashes.txt
```

### Esempio 2: NTLM di Windows
```bash
# Dall'output di hashdump
echo "admin:500:aad3b435b51404ee:31d6cfe0d16ae931:::" > ntlm.txt

# Esegui il cracking
john --format=nt --wordlist=rockyou.txt ntlm.txt
```

### Esempio 3: password di un file ZIP
```bash
zip2john secret.zip > zip.hash
john --wordlist=rockyou.txt zip.hash
john --show zip.hash
```

### Esempio 4: passphrase di una chiave SSH
```bash
ssh2john ~/.ssh/id_rsa > ssh.hash
john --wordlist=rockyou.txt ssh.hash
```

### Esempio 5: hash MD5
```bash
echo "5d41402abc4b2a76b9719d911017c592" > md5.txt
john --format=raw-md5 --wordlist=rockyou.txt md5.txt
```

### Esempio 6: hash di applicazioni web
```bash
# Hash bcrypt
echo '$2a$10$N9qo8uLOickgx2ZMRZoMy...' > bcrypt.txt
john --format=bcrypt --wordlist=rockyou.txt bcrypt.txt
```

### Esempio 7: con regole
```bash
john --wordlist=small_wordlist.txt --rules=Jumbo hashes.txt
```

### Esempio 8: attacco Incremental
```bash
# Prova tutte le combinazioni di 6-8 minuscole e cifre
john --incremental=LowerNum --min-length=6 --max-length=8 hashes.txt
```

---

## 📊 Riferimento rapido

### Comandi essenziali

| Attività | Comando |
|------|---------|
| Cracking di base | `john hashes.txt` |
| Attacco con wordlist | `john --wordlist=rockyou.txt hashes.txt` |
| Con regole | `john --wordlist=words.txt --rules hashes.txt` |
| Mostra le password recuperate | `john --show hashes.txt` |
| Formato specifico | `john --format=raw-md5 hashes.txt` |
| Incremental | `john --incremental hashes.txt` |
| Ripristina la sessione | `john --restore=session` |
| Elenca i formati | `john --list=formats` |

### Strumenti 2john

| Strumento | Scopo |
|------|---------|
| `unshadow` | passwd/shadow di Linux |
| `zip2john` | File ZIP |
| `rar2john` | File RAR |
| `pdf2john` | File PDF |
| `office2john` | Documenti Office |
| `ssh2john` | Chiavi SSH |
| `keepass2john` | Database KeePass |

### Formati comuni

| Hash | Flag del formato |
|------|-------------|
| MD5 | `raw-md5` |
| SHA1 | `raw-sha1` |
| SHA256 | `raw-sha256` |
| NTLM | `nt` |
| Linux SHA512 | `sha512crypt` |
| bcrypt | `bcrypt` |
| MySQL | `mysql-sha1` |

---

## 💡 Consigli e buone pratiche

### Consigli di efficienza

1. **Prova prima la modalità Single**
   ```bash
   john --single hashes.txt
   ```

2. **Usa wordlist mirate**
   ```bash
   # Nome dell'azienda, città, ecc.
   john --wordlist=targeted.txt hashes.txt
   ```

3. **Inizia con wordlist piccole**
   ```bash
   john --wordlist=top1000.txt hashes.txt
   # Poi prova liste più grandi
   ```

4. **Combina le modalità**
   ```bash
   john --single hashes.txt
   john --wordlist=rockyou.txt --rules hashes.txt
   john --incremental hashes.txt
   ```

### Consigli sulle prestazioni

```bash
# Usa più core
john --fork=4 hashes.txt

# Accelerazione GPU (Jumbo)
john --format=sha512crypt-opencl hashes.txt
```

---

## ⚠ Avvertenze legali

> **ATTENZIONE:** John the Ripper è un potente strumento di cracking delle password da usare soltanto per **scopi autorizzati**.
> 
> - ✅ Verifica la sicurezza delle tue password
> - ✅ Esegui test con autorizzazione esplicita
> - ✅ Svolgi verifiche di sicurezza autorizzate
> - ❌ Non eseguire mai il cracking di password senza autorizzazione
> - ❌ L'accesso non autorizzato è illegale
> 
> **Il cracking delle password senza autorizzazione è illegale e può comportare conseguenze penali.**

---

## 📚 Risorse

### Risorse ufficiali
- [Sito ufficiale di John the Ripper](https://www.openwall.com/john/)
- [John Jumbo GitHub](https://github.com/openwall/john)
- [Wiki di John](https://openwall.info/wiki/john)

### Wordlists
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [CrackStation](https://crackstation.net/crackstation-wordlist-password-cracking-dictionary.htm)

### Schede correlate
- [Hydra](../../Hydra/translations/README.it.md)
- [Metasploit](../../Metasploit/translations/README.it.md)
- [Nmap](../../Nmap/translations/README.it.md)

---

<p align="center">
  <b>🔨 Padroneggia il cracking delle password!</b><br>
  <i>Esegui sempre il cracking in modo responsabile</i>
</p>
