# ⚡ Hashcat - Scheda di riferimento completa

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

> **Nota sugli esempi originali:** conteggi, prestazioni, driver e pacchetti dipendono dalla versione; le stelle non sono un benchmark e più GPU non implicano distribuzione su più host. Esiste anche il rilevamento tramite `--identify`, quindi la tabella semplifica. Il profilo predefinito non è necessariamente `-w 1`; `--outfile-format=2` indica il testo recuperato, non `hash:plain`. `--benchmark-all` amplia i benchmark, non disabilita l’ottimizzazione OpenCL. Il modo 2500 è storico; identificativi, formati e conversioni devono corrispondere ai dati, anche per Office e WordPress. Hash con `...` sono illustrativi. Una maschera di quattro posizioni non cresce fino a otto soltanto con `--increment-max=8`; le maschere possono inoltre essere interpretate dalla shell. `--force` non corregge problemi dei driver. Comandi e valori restano invariati: consulta la [documentazione ufficiale](https://hashcat.net/wiki/doku.php?id=hashcat) e la [guida alle maschere](https://hashcat.net/wiki/doku.php?id=mask_attack).

```
  _   _           _                _   
 | | | | __ _ ___| |__   ___ __ _| |_ 
 | |_| |/ _` / __| '_ \ / __/ _` | __|
 |  _  | (_| \__ \ | | | (_| (_| | |_ 
 |_| |_|\__,_|___/_| |_|\___\__,_|\__|
                                       
    Il recupero delle password più rapido al mondo
```

<p align="center">
  <img src="https://img.shields.io/badge/Hashcat-GPU_Cracker-red?style=for-the-badge" alt="Hashcat">
  <img src="https://img.shields.io/badge/350+-Hash_Types-orange?style=for-the-badge" alt="Hash Types">
  <img src="https://img.shields.io/badge/GPU-Accelerated-blue?style=for-the-badge" alt="GPU">
  <img src="https://img.shields.io/badge/World's-Fastest-green?style=for-the-badge" alt="Fastest">
</p>

<p align="center">
  <b>🚀 L'utilità di recupero delle password più rapida e avanzata al mondo</b>
</p>

---

## 📋 Indice

- [Introduzione](#-introduzione)
- [Installazione](#-installazione)
- [Sintassi di base](#-sintassi-di-base)
- [Modalità di attacco](#-modalità-di-attacco)
- [Modalità hash](#-modalità-hash)
- [Attacco con maschera](#-attacco-con-maschera)
- [Regole](#-regole)
- [Gestione delle sessioni](#-gestione-delle-sessioni)
- [Ottimizzazione GPU](#-ottimizzazione-gpu)
- [Esempi pratici](#-esempi-pratici)
- [Riferimento rapido](#-riferimento-rapido)
- [Consigli e buone pratiche](#-consigli-e-buone-pratiche)
- [Risorse](#-risorse)

---

## 🎯 Introduzione

**Hashcat** è un'utilità di recupero delle password descritta come la più rapida e avanzata al mondo; supporta oltre 350 tipi di hash e sfrutta l'accelerazione GPU.

### Funzionalità principali

| Funzionalità | Descrizione |
|---------|-------------|
| **Oltre 350 tipi di hash** | MD5, SHA, NTLM, bcrypt, WPA e altri |
| **Accelerazione GPU** | NVIDIA CUDA, AMD OpenCL, Intel |
| **Più GPU** | Supporto del cracking distribuito |
| **5 modalità di attacco** | Dizionario, brute force, ibrido, ecc. |
| **Regole potenti** | Trasformazioni avanzate delle parole |
| **Multipiattaforma** | Linux, Windows, macOS |

### Hashcat a confronto con John the Ripper

| Funzionalità | Hashcat | John |
|---------|---------|------|
| **Velocità** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ |
| **Supporto GPU** | Eccellente | Buona |
| **Tipi di hash** | 350+ | 300+ |
| **Rilevamento automatico** | ❌ No | ✅ Sì |
| **Facilità d'uso** | Moderata | Più semplice |
| **Ideale per** | Cracking su GPU | Cracking su CPU |

---

## 📥 Installazione

### Kali Linux
```bash
sudo apt update && sudo apt install hashcat

# Con OpenCL
sudo apt install hashcat-nvidia  # NVIDIA
sudo apt install hashcat-amd     # AMD
```

### Ubuntu/Debian
```bash
sudo apt install hashcat
```

### Windows
```bash
# Scarica dal sito ufficiale
https://hashcat.net/hashcat/

# Estrai ed esegui dalla cartella
hashcat.exe -h
```

### Da sorgente
```bash
git clone https://github.com/hashcat/hashcat.git
cd hashcat
make
./hashcat -h
```

### Verifica dell'installazione
```bash
hashcat --version
hashcat -I                    # Mostra i dispositivi OpenCL
hashcat -b                    # Esegui il benchmark
```

---

## ⌨ Sintassi di base

### Sintassi generale
```bash
hashcat [options] hash|hashfile [dictionary|mask]
```

### Opzioni essenziali

| Opzione | Descrizione |
|--------|-------------|
| `-m` | Tipo di hash (modalità) |
| `-a` | Modalità di attacco |
| `-o` | File di output |
| `-r` | File di regole |
| `--show` | Mostra le password recuperate |
| `--status` | Abilita la schermata di stato |
| `-w` | Profilo di carico (1-4) |
| `--force` | Ignora gli avvisi |
| `-O` | Kernel ottimizzati |

### Avvio rapido
```bash
# Attacco di base con dizionario
hashcat -m 0 -a 0 hash.txt wordlist.txt

# Mostra gli hash risolti
hashcat -m 0 hash.txt --show

# Esamina gli hash di esempio
hashcat -m 0 --example-hashes
```

---

## 🎮 Modalità di attacco

### Panoramica

| Modalità | Nome | Descrizione |
|------|------|-------------|
| `-a 0` | Dizionario | Attacco con wordlist |
| `-a 1` | Combinazione | Combina due wordlist |
| `-a 3` | Brute force | Attacco basato su maschera |
| `-a 6` | Ibrido dizionario+maschera | Wordlist e maschera |
| `-a 7` | Ibrido maschera+dizionario | Maschera e wordlist |
| `-a 9` | Associazione | Associazioni di parole |

### Attacco con dizionario (-a 0)

```bash
# Attacco di base con dizionario
hashcat -m 0 -a 0 hash.txt wordlist.txt

# Con regole
hashcat -m 0 -a 0 hash.txt wordlist.txt -r rules/best64.rule

# Più wordlist
hashcat -m 0 -a 0 hash.txt wordlist1.txt wordlist2.txt
```

### Attacco combinato (-a 1)

Combina parole di due wordlist.

```bash
# Combina due wordlist
hashcat -m 0 -a 1 hash.txt wordlist1.txt wordlist2.txt

# Combinazioni word1word2
```

### Attacco brute force/con maschera (-a 3)

```bash
# Tutte minuscole, 6 caratteri
hashcat -m 0 -a 3 hash.txt ?l?l?l?l?l?l

# Tutte cifre, 4-8 caratteri
hashcat -m 0 -a 3 hash.txt ?d?d?d?d --increment --increment-min=4 --increment-max=8

# Set di caratteri personalizzato
hashcat -m 0 -a 3 hash.txt -1 ?l?d ?1?1?1?1?1?1
```

### Attacchi ibridi (-a 6, -a 7)

```bash
# Dizionario e maschera (aggiunta alla fine)
hashcat -m 0 -a 6 hash.txt wordlist.txt ?d?d?d?d

# Maschera e dizionario (aggiunta all'inizio)
hashcat -m 0 -a 7 hash.txt ?d?d?d?d wordlist.txt
```

---

## 🔢 Modalità hash

### Modalità hash più comuni

| Modalità | Tipo di hash | Esempio |
|------|-----------|---------|
| 0 | MD5 | `5d41402abc4b2a76b9719d911017c592` |
| 100 | SHA1 | `aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d` |
| 1400 | SHA256 | `2cf24dba5fb0a30e26e83b2ac5b9e29e...` |
| 1700 | SHA512 | `cf83e1357eefb8bdf1542850d66d8007d620e405...` |
| 1000 | NTLM | `32ed87bdb5fdc5e9cba88547376818d4` |
| 3000 | LM | `aad3b435b51404ee` |
| 5600 | NetNTLMv2 | Formato complesso |
| 3200 | bcrypt | `$2a$10$...` |
| 1800 | sha512crypt (Linux) | `$6$...` |
| 500 | md5crypt (Linux) | `$1$...` |

### Applicazioni web

| Modalità | Tipo di hash |
|------|-----------|
| 400 | WordPress (phpass) |
| 2500 | WPA/WPA2 |
| 22000 | WPA-PBKDF2-PMKID+EAPOL |
| 121 | SMF |
| 2611 | vBulletin |
| 3711 | MediaWiki B |
| 7900 | Drupal7 |

### Database

| Modalità | Tipo di hash |
|------|-----------|
| 300 | MySQL4.1/MySQL5 |
| 200 | MySQL323 |
| 1731 | MSSQL (2012, 2014) |
| 112 | Oracle S |
| 12300 | Oracle T |

### Documenti e archivi

| Modalità | Tipo di hash |
|------|-----------|
| 9600 | Office 2013 |
| 9500 | Office 2010 |
| 9400 | Office 2007 |
| 9700 | MS Office ⇐ 2003 |
| 10500 | PDF 1.4-1.6 |
| 10700 | PDF 1.7 Level 8 |
| 11600 | 7-Zip |
| 13600 | WinZip |
| 17200 | PKZIP |
| 13000 | RAR5 |
| 12500 | RAR3-hp |

### Criptovalute

| Modalità | Tipo di hash |
|------|-----------|
| 11300 | Portafoglio Bitcoin |
| 12700 | Portafoglio Blockchain |
| 16600 | Portafoglio Electrum |
| 15200 | Ethereum Keystore |

### Elenca/cerca le modalità
```bash
# Elenca tutte le modalità hash
hashcat --help | grep -i "hash modes"

# Cerca un hash specifico
hashcat --example-hashes | grep -i "ntlm"
hashcat --example-hashes | grep -i "sha256"

# Mostra un esempio del formato dell'hash
hashcat -m 1000 --example-hashes
```

---

## 🎭 Attacco con maschera

### Segnaposto dei set di caratteri

| Segnaposto | Caratteri |
|-------------|------------|
| `?l` | abcdefghijklmnopqrstuvwxyz |
| `?u` | ABCDEFGHIJKLMNOPQRSTUVWXYZ |
| `?d` | 0123456789 |
| `?h` | 0123456789abcdef |
| `?H` | 0123456789ABCDEF |
| `?s` | !"#$%&'()*+,-./:;<=>?@[]^_`{|}~ |
| `?a` | ?l?u?d?s |
| `?b` | 0x00 - 0xff |

### Set di caratteri personalizzati

```bash
# Definisci un set di caratteri personalizzato
hashcat -a 3 -1 ?l?d hash.txt ?1?1?1?1?1?1

# Più set di caratteri personalizzati
hashcat -a 3 -1 abc -2 123 hash.txt ?1?2?1?2

# Caratteri speciali
hashcat -a 3 -1 '!@#$%' hash.txt pass?1?1?1?1
```

### Modalità Increment

```bash
# Prova lunghezze da 1 a 8
hashcat -a 3 hash.txt ?a?a?a?a?a?a?a?a --increment

# Specifica minimo e massimo
hashcat -a 3 hash.txt ?a?a?a?a?a?a?a?a --increment --increment-min=4 --increment-max=8
```

### Schemi di maschera comuni

```bash
# 8 lettere minuscole
?l?l?l?l?l?l?l?l

# Password e 4 cifre
password?d?d?d?d

# Maiuscola, minuscole e cifre
?u?l?l?l?l?l?d?d

# Schema Winter2024
?u?l?l?l?l?l?d?d?d?d

# Numero di telefono
?d?d?d?d?d?d?d?d?d?d
```

---

## 📜 Regole

### File di regole inclusi

```bash
# Percorso
ls /usr/share/hashcat/rules/

# Regole diffuse
best64.rule           # Le migliori 64 regole
rockyou-30000.rule    # Le prime 30000 regole
d3ad0ne.rule          # Regole D3ad0ne
dive.rule             # Regole Dive
generated.rule        # Regole generate
```

### Uso delle regole

```bash
# Un file di regole
hashcat -m 0 -a 0 hash.txt wordlist.txt -r best64.rule

# Più file di regole
hashcat -m 0 -a 0 hash.txt wordlist.txt -r rule1.rule -r rule2.rule

# Genera regole
hashcat -m 0 -a 0 hash.txt wordlist.txt -g 1000
```

### Operazioni comuni delle regole

| Regola | Descrizione | Esempio |
|------|-------------|---------|
| `:` | Non modificare nulla | password |
| `l` | Converti tutto in minuscolo | PASSWORD → password |
| `u` | Converti tutto in maiuscolo | password → PASSWORD |
| `c` | Iniziale maiuscola | password → Password |
| `C` | Primo carattere minuscolo, gli altri maiuscoli | password → pASSWORD |
| `t` | Inverti maiuscole e minuscole | password → PASSWORD |
| `r` | Inverti | password → drowssap |
| `d` | Duplica | pass → passpass |
| `$X` | Aggiungi X alla fine | pass → passX |
| `^X` | Aggiungi X all'inizio | pass → Xpass |
| `sXY` | Sostituisci X con Y | password → pa$$word |

---

## 💾 Gestione delle sessioni

### Crea una sessione
```bash
# Sessione con nome
hashcat -m 0 -a 0 hash.txt wordlist.txt --session=mysession

# File di sessione archiviati in ~/.hashcat/sessions/
```

### Pausa e ripresa
```bash
# Pausa: premi 'p' durante il cracking
# Ripresa: premi 'r' durante il cracking

# Oppure ripristina dalla riga di comando
hashcat --session=mysession --restore
```

### Comandi di stato

Durante il cracking:
- `s` - Mostra lo stato
- `p` - Pausa
- `r` - Riprendi
- `b` - Salta l'attacco corrente (bypass)
- `c` - Checkpoint
- `q` - Esci

### Opzioni di output
```bash
# Output su file
hashcat -m 0 -a 0 hash.txt wordlist.txt -o cracked.txt

# Formato di output
hashcat -m 0 hash.txt --outfile-format=2    # hash:plain

# Mostra le password recuperate
hashcat -m 0 hash.txt --show

# Mostra gli hash rimanenti (non risolti)
hashcat -m 0 hash.txt --left
```

---

## 🚀 Ottimizzazione GPU

### Profili di carico

| Profilo | Descrizione |
|---------|-------------|
| `-w 1` | Basso (predefinito, interattivo nell'originale) |
| `-w 2` | Medio |
| `-w 3` | Alto (può bloccare il desktop) |
| `-w 4` | Estremo (sistema dedicato al cracking) |

### Comandi di ottimizzazione
```bash
# Prestazioni elevate
hashcat -m 0 -a 0 hash.txt wordlist.txt -w 3 -O

# Kernel ottimizzati (più rapidi, lunghezza delle password limitata)
hashcat -m 0 -a 0 hash.txt wordlist.txt -O

# Forza la scelta del dispositivo
hashcat -m 0 -a 0 hash.txt wordlist.txt -d 1

# Disabilita la CPU e usa soltanto la GPU
hashcat -m 0 -a 0 hash.txt wordlist.txt -D 2
```

### Opzioni dei dispositivi
```bash
# Elenca i dispositivi
hashcat -I

# Seleziona un dispositivo specifico
hashcat -d 1 ...

# Tipi di dispositivo
# -D 1 = CPU
# -D 2 = GPU
# -D 3 = FPGA
```

### Benchmark
```bash
# Benchmark completo
hashcat -b

# Tipo di hash specifico
hashcat -b -m 0

# Benchmark senza ottimizzazione OpenCL
hashcat -b --benchmark-all
```

---

## 🎬 Esempi pratici

### Esempio 1: attacco MD5 con dizionario
```bash
hashcat -m 0 -a 0 md5_hashes.txt /usr/share/wordlists/rockyou.txt
```

### Esempio 2: NTLM con regole
```bash
hashcat -m 1000 -a 0 ntlm_hashes.txt wordlist.txt -r /usr/share/hashcat/rules/best64.rule
```

### Esempio 3: handshake WPA/WPA2
```bash
# Converti prima nel formato di Hashcat
hcxpcapngtool -o hash.hc22000 capture.pcapng

# Esegui il cracking
hashcat -m 22000 hash.hc22000 /usr/share/wordlists/rockyou.txt
```

### Esempio 4: brute force SHA256 (6 caratteri)
```bash
hashcat -m 1400 -a 3 sha256_hash.txt ?a?a?a?a?a?a
```

### Esempio 5: documento Office 2013
```bash
# Estrai l'hash con office2john
office2john document.docx > office.hash

# Esegui il cracking
hashcat -m 9600 office.hash wordlist.txt
```

### Esempio 6: password shadow di Linux
```bash
hashcat -m 1800 -a 0 shadow_hash.txt wordlist.txt
```

### Esempio 7: hash WordPress
```bash
hashcat -m 400 -a 0 wordpress_hash.txt wordlist.txt
```

### Esempio 8: attacco ibrido
```bash
# Nome dell'azienda e 4 cifre
hashcat -m 0 -a 6 hash.txt company_names.txt ?d?d?d?d
```

### Esempio 9: hash di un dominio Windows
```bash
# Hash NTLM da secretsdump
hashcat -m 1000 -a 0 ntds_hashes.txt wordlist.txt -r best64.rule --username
```

### Esempio 10: portafoglio Bitcoin
```bash
# Estrai con bitcoin2john
hashcat -m 11300 bitcoin_hash.txt wordlist.txt
```

---

## 📊 Riferimento rapido

### Comandi essenziali

| Attività | Comando |
|------|---------|
| MD5 con dizionario | `hashcat -m 0 -a 0 hash.txt wordlist.txt` |
| Brute force NTLM | `hashcat -m 1000 -a 3 hash.txt ?a?a?a?a?a?a` |
| SHA256 con regole | `hashcat -m 1400 -a 0 hash.txt wordlist.txt -r rules.rule` |
| Mostra le password recuperate | `hashcat -m 0 hash.txt --show` |
| Benchmark | `hashcat -b` |
| Elenca i dispositivi | `hashcat -I` |
| Ripristina la sessione | `hashcat --session=name --restore` |

### Modalità hash comuni

| Modalità | Tipo |
|------|------|
| 0 | MD5 |
| 100 | SHA1 |
| 1000 | NTLM |
| 1400 | SHA256 |
| 1800 | sha512crypt |
| 3200 | bcrypt |
| 22000 | WPA2 |

### Modalità di attacco

| Modalità | Tipo |
|------|------|
| -a 0 | Dizionario |
| -a 1 | Combinazione |
| -a 3 | Brute force |
| -a 6 | Ibrido (dizionario+maschera) |
| -a 7 | Ibrido (maschera+dizionario) |

---

## 💡 Consigli e buone pratiche

### Consigli di efficienza

1. **Inizia con dizionario e regole**
   ```bash
   hashcat -m 0 -a 0 hash.txt rockyou.txt -r best64.rule
   ```

2. **Usa kernel ottimizzati**
   ```bash
   hashcat -O ...
   ```

3. **Punta agli schemi comuni**
   ```bash
   # Stagione e anno: Winter2024
   hashcat -a 3 -1 WSFJ -2 winterumeall hash.txt ?1?2?2?2?2?2?d?d?d?d
   ```

### Consigli sulle prestazioni

```bash
# Prestazioni massime (sistema dedicato)
hashcat -m 0 -a 0 hash.txt wordlist.txt -w 4 -O --force

# Più GPU
hashcat -d 1,2 ...
```

### Procedura di lavoro

```
1. Identifica il tipo di hash
2. Prova un attacco con dizionario e regole
3. Prova maschere mirate (schemi)
4. Prova il brute force completo (se fattibile)
5. Prova attacchi ibridi
```

---

## ⚠ Avvertenze legali

> **ATTENZIONE:** Hashcat è un potente strumento di cracking delle password da usare soltanto per **scopi autorizzati**.
> 
> - ✅ Verifica la sicurezza dei tuoi sistemi
> - ✅ Svolgi verifiche di sicurezza con autorizzazione
> - ✅ Recupera le tue password
> - ❌ Non eseguire mai il cracking di password senza autorizzazione
> - ❌ L'accesso non autorizzato è illegale
> 
> **Il cracking delle password senza autorizzazione è illegale e può comportare conseguenze penali.**

---

## 📚 Risorse

### Risorse ufficiali
- [Sito ufficiale di Hashcat](https://hashcat.net/hashcat/)
- [Wiki di Hashcat](https://hashcat.net/wiki/)
- [Hashcat GitHub](https://github.com/hashcat/hashcat)
- [Forum di Hashcat](https://hashcat.net/forum/)

### Wordlists
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [CrackStation](https://crackstation.net/crackstation-wordlist-password-cracking-dictionary.htm)

### Schede correlate
- [John the Ripper](../../John-The-Ripper/translations/README.it.md)
- [Hydra](../../Hydra/translations/README.it.md)
- [Nmap](../../Nmap/translations/README.it.md)

---

<p align="center">
  <b>⚡ Padroneggia il cracking delle password su GPU!</b><br>
  <i>Il recupero delle password più rapido al mondo</i>
</p>
