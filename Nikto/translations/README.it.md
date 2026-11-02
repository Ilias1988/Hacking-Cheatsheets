# 🕷 Nikto - Scheda di riferimento completa

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
    _   _ _ _    _        
   | \ | (_) | _| |_ ___  
   |  \| | | |/ / __/ _ \ 
   | |\  | |   <| || (_) |
   |_| \_|_|_|\_\\__\___/ 
                          
   Scanner di server web - Dal 2001
```

<p align="center">
  <img src="https://img.shields.io/badge/Nikto-Web_Scanner-blue?style=for-the-badge" alt="Nikto">
  <img src="https://img.shields.io/badge/Vulnerability-Scanner-red?style=for-the-badge" alt="Vulnerability">
  <img src="https://img.shields.io/badge/CGI-Testing-green?style=for-the-badge" alt="CGI">
  <img src="https://img.shields.io/badge/SSL-Analysis-purple?style=for-the-badge" alt="SSL">
</p>

<p align="center">
  <b>🌐 Scanner open source di server web per i test di sicurezza</b>
</p>

---

## 📋 Indice

- [Introduzione](#-introduzione)
- [Installazione](#-installazione)
- [Sintassi di base](#-sintassi-di-base)
- [Definizione dei bersagli](#-definizione-dei-bersagli)
- [Opzioni delle porte e di SSL](#-opzioni-delle-porte-e-di-ssl)
- [Regolazione della scansione](#-regolazione-della-scansione)
- [Autenticazione](#-autenticazione)
- [Proxy ed elusione](#-proxy-ed-elusione)
- [Output e report](#-output-e-report)
- [Aggiornamenti del database](#-aggiornamenti-del-database)
- [Esempi pratici](#-esempi-pratici)
- [Riferimento rapido](#-riferimento-rapido)
- [Consigli e buone pratiche](#-consigli-e-buone-pratiche)
- [Risorse](#-risorse)

---

## 🎯 Introduzione

> **Nota sulla compatibilità:** conteggi del database, plugin e opzioni riportano l’originale e non garantiscono lo stato della release installata. Le etichette “stealth” ed “elusione IDS” non garantiscono che la scansione passi inosservata.

**Nikto** è uno scanner open source che esegue numerosi test sui server web, tra cui:

### Che cosa verifica Nikto

| Categoria | Descrizione |
|----------|-------------|
| **File pericolosi** | File potenzialmente pericolosi (backup e simili) |
| **Versioni obsolete** | Software del server obsoleto |
| **Problemi specifici della versione** | Vulnerabilità specifiche della versione |
| **Configurazione del server** | Configurazioni errate |
| **Script CGI** | Script CGI potenzialmente pericolosi |
| **SSL/TLS** | Problemi dei certificati SSL |
| **Header** | Header HTTP mancanti o non sicuri |
| **Metodi** | Metodi HTTP pericolosi |

### Statistiche del database

```
✅ Oltre 6,700 file/CGI potenzialmente pericolosi
✅ Oltre 1,250 versioni obsolete di server
✅ Oltre 270 problemi specifici della versione
✅ Oltre 1,000 problemi di configurazione del server
```

### Funzionalità

| Funzionalità | Descrizione |
|---------|-------------|
| **Completo** | Verifiche per migliaia di vulnerabilità |
| **Basato su plugin** | Architettura estensibile |
| **Più formati** | Output HTML, XML, CSV e JSON |
| **Supporto SSL** | Test completi su HTTPS |
| **Elusione IDS** | Tecniche di elusione integrate |
| **Autenticazione** | Supporto Basic, Digest e NTLM |

---

## 📥 Installazione

### Kali Linux (preinstallato)
```bash
# Aggiorna Nikto
sudo apt update && sudo apt install nikto

# Aggiorna il database
nikto -update
```

### Ubuntu/Debian
```bash
sudo apt install nikto
```

### Da GitHub
```bash
git clone https://github.com/sullo/nikto
cd nikto/program
perl nikto.pl -h
```

### macOS (Homebrew)
```bash
brew install nikto
```

### Verifica dell'installazione
```bash
nikto -Version
nikto -h          # Guida
```

---

## ⌨ Sintassi di base

### Sintassi generale
```bash
nikto -h <host> [options]
```

### Avvio rapido
```bash
# Scansione di base
nikto -h http://target.com

# Scansiona indicando la porta
nikto -h target.com -p 80

# Scansiona HTTPS
nikto -h https://target.com

# Scansiona un IP
nikto -h 192.168.1.1
```

---

## 🎯 Definizione dei bersagli

### Bersaglio singolo
```bash
# Per nome host
nikto -h target.com

# Per IP
nikto -h 192.168.1.1

# Con protocollo
nikto -h http://target.com
nikto -h https://target.com
```

### Bersagli multipli
```bash
# Da file
nikto -h targets.txt

# targets.txt:
# http://target1.com
# http://target2.com:8080
# https://target3.com
```

### Bersaglio con percorso
```bash
# Scansiona un percorso specifico
nikto -h http://target.com/app

# Scansiona una directory specifica
nikto -h http://target.com/admin/
```

---

## 🔌 Opzioni delle porte e di SSL

### Opzioni delle porte
```bash
# Porta singola
nikto -h target.com -p 80

# Più porte
nikto -h target.com -p 80,443,8080

# Intervallo di porte
nikto -h target.com -p 80-90

# Porte web comuni
nikto -h target.com -p 80,443,8000,8080,8443
```

### Opzioni SSL/TLS

| Opzione | Descrizione |
|--------|-------------|
| `-ssl` | Forza la modalità SSL |
| `-nossl` | Disattiva SSL |
| `-maxtime` | Durata massima della scansione |

```bash
# Forza SSL
nikto -h target.com -ssl

# Disattiva SSL (solo HTTP)
nikto -h target.com -nossl

# SSL su una porta non standard
nikto -h target.com -p 8443 -ssl
```

### Host virtuali
```bash
# Specifica l'host virtuale
nikto -h 192.168.1.1 -vhost target.com

# Utile quando:
# - Il bersaglio è dietro un bilanciatore di carico
# - Più siti condividono lo stesso IP
```

---

## 🎚 Regolazione della scansione

### Opzioni di regolazione (-Tuning)

| Codice | Descrizione |
|------|-------------|
| `0` | Caricamento di file |
| `1` | File interessante / rilevato nei log |
| `2` | Configurazione errata / file predefinito |
| `3` | Divulgazione di informazioni |
| `4` | Injection (XSS/script/HTML) |
| `5` | Recupero di file remoti all'interno della directory web |
| `6` | Negazione del servizio (DoS) |
| `7` | Recupero di file remoti nell'intero server |
| `8` | Esecuzione di comandi / shell remota |
| `9` | SQL Injection |
| `a` | Bypass dell'autenticazione |
| `b` | Identificazione del software |
| `c` | Inclusione di sorgenti remoti |
| `d` | WebService |
| `e` | Console di amministrazione |
| `x` | Inverte le opzioni di regolazione |

### Uso della regolazione
```bash
# Esegui solo i test per SQL injection
nikto -h target.com -Tuning 9

# Esegui i test per XSS e SQL injection
nikto -h target.com -Tuning 49

# Esegui i test di più categorie
nikto -h target.com -Tuning 1234

# Escludi alcuni test (prefisso x)
nikto -h target.com -Tuning x6    # Escludi i test DoS

# Tutto tranne i test DoS
nikto -h target.com -Tuning x6
```

### Plugin
```bash
# Elenca i plugin disponibili
nikto -list-plugins

# Esegui un plugin specifico
nikto -h target.com -Plugins apacheusers

# Esegui più plugin
nikto -h target.com -Plugins "apacheusers;robots"

# Escludi plugin
nikto -h target.com -Plugins "@@ALL;-robots"
```

### Plugin comuni

| Plugin | Descrizione |
|--------|-------------|
| `apacheusers` | Enumerazione degli utenti Apache |
| `cgi` | Test degli script CGI |
| `robots` | Analisi di robots.txt |
| `headers` | Analisi degli header HTTP |
| `httpoptions` | Test dei metodi HTTP |
| `outdated` | Verifica del software obsoleto |
| `ssl` | Analisi SSL/TLS |
| `dictionary` | Attacco a dizionario |

---

## 🔐 Autenticazione

### Autenticazione Basic

> **Correzione dell’originale:** racchiudere `admin:password` tra virgolette non lo converte in Base64. La sintassi documentata di `-id` è `id:pass` oppure `id:pass:realm`; non assumere che il terzo campo equivalga sempre a un dominio NTLM. Gli esempi `-cookies` vanno verificati per la release usata: tale opzione non compare nell’[elenco ufficiale consultato](https://github.com/sullo/nikto/wiki/Annotated-Option-List).
```bash
# Nome utente e password
nikto -h target.com -id admin:password

# Formato Base64 (etichetta inesatta nell'originale: vedi nota)
nikto -h target.com -id "admin:password"
```

### Autenticazione Digest
```bash
# Autenticazione Digest (rilevata automaticamente)
nikto -h target.com -id admin:password
```

### Autenticazione NTLM
```bash
# Autenticazione NTLM
nikto -h target.com -id admin:password:domain
```

### Autenticazione basata sui cookie
```bash
# Invia cookie
nikto -h target.com -cookies "session=abc123; token=xyz789"

# Da file
nikto -h target.com -cookies cookies.txt
```

---

## 🛡 Proxy ed elusione

### Supporto proxy
```bash
# Proxy HTTP
nikto -h target.com -useproxy http://127.0.0.1:8080

# Proxy autenticato
nikto -h target.com -useproxy http://user:pass@proxy:8080

# Tramite Burp Suite
nikto -h target.com -useproxy http://127.0.0.1:8080
```

### Elusione IDS (-evasion)

| Codice | Tecnica |
|------|-----------|
| `1` | Codifica casuale dell'URI (non UTF-8) |
| `2` | Autoriferimento alla directory (/./) |
| `3` | Terminazione anticipata dell'URL |
| `4` | Anteposizione di una lunga stringa casuale |
| `5` | Parametro fittizio |
| `6` | TAB come separatore nella richiesta |
| `7` | Cambia maiuscole e minuscole dell'URL |
| `8` | Usa il separatore delle directory di Windows (\) |
| `A` | Usa un ritorno a capo (0x0d) |
| `B` | Usa il valore binario 0x0b |

```bash
# Tecnica di elusione singola
nikto -h target.com -evasion 1

# Più tecniche di elusione
nikto -h target.com -evasion 1234

# Tutte le tecniche di elusione
nikto -h target.com -evasion 12345678AB
```

### User-Agent
```bash
# User-Agent personalizzato
nikto -h target.com -useragent "Mozilla/5.0 (Windows NT 10.0)"

# Googlebot
nikto -h target.com -useragent "Googlebot/2.1"
```

### Tempi delle richieste
```bash
# Pausa tra le richieste (secondi)
nikto -h target.com -Pause 2

# Durata massima della scansione (secondi)
nikto -h target.com -maxtime 3600

# Timeout per richiesta
nikto -h target.com -timeout 10
```

---

## 📄 Output e report

### Formati di output

| Opzione | Formato |
|--------|--------|
| `-o file` | Output su file |
| `-Format htm` | Formato HTML |
| `-Format xml` | Formato XML |
| `-Format csv` | Formato CSV |
| `-Format txt` | Formato testo |
| `-Format json` | Formato JSON |
| `-Format nbe` | Formato NBE |
| `-Format sql` | Formato SQL |

### Salvataggio dei risultati
```bash
# Output di testo
nikto -h target.com -o report.txt

# Report HTML
nikto -h target.com -o report.html -Format htm

# Report XML
nikto -h target.com -o report.xml -Format xml

# Report JSON
nikto -h target.com -o report.json -Format json

# Report CSV
nikto -h target.com -o report.csv -Format csv
```

### Opzioni del livello di dettaglio
```bash
# Opzioni di visualizzazione
nikto -h target.com -Display V    # Dettagliato
nikto -h target.com -Display D    # Debug
nikto -h target.com -Display E    # Errori HTTP
nikto -h target.com -Display P    # Avanzamento
nikto -h target.com -Display S    # Rimuovi gli IP dall'output
nikto -h target.com -Display 1    # Mostra i reindirizzamenti
nikto -h target.com -Display 2    # Mostra i cookie
nikto -h target.com -Display 3    # Mostra le risposte 200/OK
nikto -h target.com -Display 4    # Mostra gli URL che richiedono autenticazione

# Più opzioni di visualizzazione
nikto -h target.com -Display VEP
```

### Salvataggio dell'avanzamento

> **Correzione dell’originale:** `-Save` salva le risposte positive in una directory, non lo stato di una scansione da riprendere. L’esempio con `-Resume` non è documentato come funzione di ripresa nell’[elenco ufficiale delle opzioni](https://github.com/sullo/nikto/wiki/Annotated-Option-List); vedi anche [salvataggio e replay](https://github.com/sullo/nikto/wiki/Saving-Requests-Responses-&-Replaying).
```bash
# Salva lo stato della scansione (per riprenderla in seguito, secondo l'originale)
nikto -h target.com -Save scan_state.txt

# Riprendi la scansione (esempio dipendente dalla versione: vedi nota)
nikto -Resume scan_state.txt
```

---

## 🔄 Aggiornamenti del database

### Aggiornamento del database di Nikto

> **Nota sulle versioni:** secondo la [documentazione ufficiale](https://github.com/sullo/nikto/wiki/Updating), `-update` riguarda versioni precedenti alla 2.1.6. Gli URL manuali `2.1.5` sono riferimenti storici, non un metodo generale per aggiornare una release moderna. Gli esempi sono conservati per fedeltà all’originale.
```bash
# Aggiorna tutti i database
nikto -update

# Aggiornamento manuale (se -update non riesce)
cd /var/lib/nikto
wget https://cirt.net/nikto/UPDATES/2.1.5/db_outdated.csv
wget https://cirt.net/nikto/UPDATES/2.1.5/db_tests.csv
```

### Posizione del database
```bash
# Percorsi predefiniti
/usr/share/nikto/databases/
/var/lib/nikto/

# File del database
db_tests         # Database principale dei test
db_outdated      # Versioni obsolete del software
db_server_msgs   # Messaggi del server
db_variables     # Variabili
db_404_strings   # Stringhe delle pagine 404
```

---

## 🎬 Esempi pratici

### Esempio 1: scansione di base di un server web
```bash
nikto -h http://target.com
```

### Esempio 2: scansione HTTPS completa
```bash
nikto -h https://target.com -ssl -p 443 -o report.html -Format htm
```

### Esempio 3: scansione di più porte
```bash
nikto -h target.com -p 80,443,8080,8443 -o results.txt
```

### Esempio 4: scansione autenticata
```bash
nikto -h http://target.com/admin \
    -id admin:password \
    -o auth_scan.html \
    -Format htm
```

### Esempio 5: scansione stealth (elusione IDS)
```bash
nikto -h target.com \
    -evasion 1234 \
    -Pause 2 \
    -useragent "Mozilla/5.0 (Windows NT 10.0)" \
    -o stealth_scan.txt
```

### Esempio 6: tramite il proxy Burp
```bash
nikto -h https://target.com \
    -useproxy http://127.0.0.1:8080 \
    -o proxy_scan.html \
    -Format htm
```

### Esempio 7: test mirato alla SQL injection
```bash
nikto -h target.com \
    -Tuning 9 \
    -o sqli_scan.txt
```

### Esempio 8: verifica completa
```bash
nikto -h https://target.com \
    -ssl \
    -p 443 \
    -Tuning x6 \
    -Display VEP \
    -o full_audit.html \
    -Format htm
```

### Esempio 9: test degli host virtuali
```bash
nikto -h 192.168.1.10 \
    -vhost app.target.com \
    -p 80,443 \
    -o vhost_scan.txt
```

### Esempio 10: scansione da file dei bersagli
```bash
nikto -h targets.txt \
    -o batch_scan.html \
    -Format htm
```

---

## 📊 Riferimento rapido

### Comandi essenziali

| Attività | Comando |
|------|---------|
| Scansione di base | `nikto -h target.com` |
| Scansione HTTPS | `nikto -h target.com -ssl` |
| Più porte | `nikto -h target.com -p 80,443,8080` |
| Con autenticazione | `nikto -h target.com -id user:pass` |
| Report HTML | `nikto -h target.com -o report.html -Format htm` |
| Elusione | `nikto -h target.com -evasion 123` |
| Tramite proxy | `nikto -h target.com -useproxy http://127.0.0.1:8080` |
| Aggiornamento del database | `nikto -update` |

### Opzioni comuni

| Opzione | Descrizione |
|--------|-------------|
| `-h` | Host bersaglio |
| `-p` | Porte da scansionare |
| `-ssl` | Forza la modalità SSL |
| `-id` | Credenziali di autenticazione |
| `-o` | File di output |
| `-Format` | Formato di output |
| `-Tuning` | Regolazione della scansione |
| `-evasion` | Elusione IDS |
| `-useproxy` | Server proxy |
| `-vhost` | Host virtuale |
| `-Pause` | Ritardo tra i test |
| `-timeout` | Timeout della richiesta |

### Riferimento rapido della regolazione

| Codice | Categoria |
|------|----------|
| 1-4 | Informazioni/configurazione errata |
| 4-5 | Injection/recupero |
| 6 | DoS (da evitare in produzione) |
| 7-8 | Accesso remoto |
| 9 | SQL Injection |
| a | Bypass dell'autenticazione |
| b | Identificazione del software |
| x | Esclusione |

---

## 💡 Consigli e buone pratiche

### Consigli per le prestazioni

1. **Inizia con una scansione di base**
   ```bash
   nikto -h target.com
   ```

2. **Escludi i test DoS**
   ```bash
   nikto -h target.com -Tuning x6
   ```

3. **Usa timeout adeguati**
   ```bash
   nikto -h target.com -timeout 15 -Pause 1
   ```

### Consigli per l'accuratezza

1. **Aggiorna regolarmente il database**
   ```bash
   nikto -update
   ```

2. **Usa la modalità dettagliata**
   ```bash
   nikto -h target.com -Display VE
   ```

3. **Combina con altri strumenti**
   ```bash
   # Prima Nmap, poi Nikto
   nmap -sV -p 80,443 target.com
   nikto -h target.com -p 80,443
   ```

### Problemi comuni

| Problema | Soluzione |
|-------|----------|
| Scansione lenta | Riduci `-Pause`, aumenta `-timeout` |
| Errori SSL | Usa esplicitamente il flag `-ssl` |
| Problemi di autenticazione | Verifica il formato di `-id` |
| Risultati mancanti | Aggiorna il database e verifica i plugin |

---

## ⚠ Avvertenza legale

> **ATTENZIONE:** Nikto è uno scanner di server web da usare esclusivamente per **test autorizzati**.
> 
> - ✅ Esegui scansioni sui server di tua proprietà
> - ✅ Esegui scansioni con esplicito permesso scritto
> - ✅ Usalo in penetration test autorizzati
> - ❌ Non eseguire mai scansioni senza autorizzazione
> - ❌ Le scansioni non autorizzate sono illegali
> 
> **Nikto può generare traffico significativo e attivare avvisi di sicurezza.**

---

## 📚 Risorse

### Risorse ufficiali
- [Nikto GitHub](https://github.com/sullo/nikto)
- [Documentazione di Nikto](https://cirt.net/Nikto2)
- [CIRT.net](https://cirt.net/)

### Risorse per l'apprendimento
- [Guida OWASP ai test](https://owasp.org/www-project-web-security-testing-guide/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Schede correlate
- [Nmap](../../Nmap/translations/README.it.md)
- [Gobuster](../../Gobuster/translations/README.it.md)
- [Burp Suite](../../Burp-Suite/translations/README.it.md)

---

<p align="center">
  <b>🕷️ Padroneggia la scansione dei server web!</b><br>
  <i>Trova le vulnerabilità prima degli attaccanti</i>
</p>
