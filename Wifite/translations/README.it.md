# 📶 Wifite - Scheda di riferimento per l’audit WiFi automatizzato

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██╗    ██╗██╗███████╗██╗████████╗███████╗
  ██║    ██║██║██╔════╝██║╚══██╔══╝██╔════╝
  ██║ █╗ ██║██║█████╗  ██║   ██║   █████╗  
  ██║███╗██║██║██╔══╝  ██║   ██║   ██╔══╝  
  ╚███╔███╔╝██║██║     ██║   ██║   ███████╗
   ╚══╝╚══╝ ╚═╝╚═╝     ╚═╝   ╚═╝   ╚══════╝
         Strumento automatizzato per il test delle reti WiFi
```

<p align="center">
  <img src="https://img.shields.io/badge/Wifite-Automated_WiFi-blue?style=for-the-badge" alt="Wifite">
  <img src="https://img.shields.io/badge/WPA2-Cracking-red?style=for-the-badge" alt="WPA2">
  <img src="https://img.shields.io/badge/Python-yellow?style=for-the-badge" alt="Python">
</p>

---

> **Nota sulle versioni:** questa guida conserva comandi e confronti dell’originale Wifite2, non un elenco garantito delle opzioni della versione installata. Il [repository originale](https://github.com/derv82/wifite2) rimanda oggi a un altro progetto per Wifite2 supportato. Nomi dei flag, possibilità di ripresa, output e percorsi dei file possono differire; verifica la guida della release utilizzata. Il [codice di configurazione originale](https://github.com/derv82/wifite2/blob/master/wifite/config.py) mostra, per esempio, percorsi relativi per alcuni risultati. Modalità silenziosa e MAC casuale non garantiscono invisibilità.

## 📋 Indice

- [Che cos’è Wifite](#-che-cosè-wifite)
- [Installazione](#-installazione)
- [Utilizzo di base](#-utilizzo-di-base)
- [Selezione dei bersagli](#-selezione-dei-bersagli)
- [Opzioni degli attacchi](#-opzioni-degli-attacchi)
- [Attacchi WPS](#-attacchi-wps)
- [Attacchi PMKID](#-attacchi-pmkid)
- [Opzioni avanzate](#-opzioni-avanzate)
- [Opzioni di recupero delle password](#-opzioni-di-recupero-delle-password)
- [Esempi](#-esempi)
- [Riferimento rapido](#-riferimento-rapido)

---

## 🎯 Che cos’è Wifite

**Wifite** (Wifite2) è uno strumento automatizzato per la verifica della sicurezza delle reti wireless che:

- 🤖 **Automatizza gli attacchi** - Non richiede di digitare manualmente ogni comando
- 🎯 **Più bersagli** - Esegue test su più reti
- 🔓 **WPA/WPA2/WEP/WPS** - Tutti i tipi di cifratura indicati nell’originale
- 💾 **Salva l’avanzamento** - Riprende gli attacchi interrotti
- 🔄 **Acquisizione PMKID** - Attacchi moderni che non richiedono un client già connesso

### Confronto tra Wifite e Wifite2

| Funzionalità | Wifite (originale) | Wifite2 |
|---------|-------------------|---------|
| Manutenuto | No | Sì |
| Python | Python 2 | Python 3 |
| PMKID | No | Sì |
| Codice | Disordinato | Riscritto in modo più chiaro |
| Utilizzo | Da evitare | **Consigliato** |

### Funzionalità

| Funzionalità | Descrizione |
|---------|-------------|
| **Automatizzato** | Verifica WiFi guidata dalla selezione dei bersagli |
| **Selezione intelligente** | Seleziona i bersagli in base alla potenza del segnale |
| **Tutti gli attacchi** | WPA, WEP, WPS, PMKID |
| **Recupero delle password (cracking)** | Integrazione con Hashcat/aircrack |
| **Ripresa** | Prosegue le sessioni interrotte |

---

## 🚀 Installazione

### Kali Linux (preinstallato)

```bash
# Già installato in Kali
wifite --help

# Aggiorna alla versione più recente
sudo apt update && sudo apt install wifite
```

### Installazione di Wifite2 da GitHub

```bash
# Clona il repository
git clone https://github.com/derv82/wifite2.git
cd wifite2

# Esegui direttamente
sudo python3 wifite.py

# Oppure installa
sudo python3 setup.py install
```

### Dipendenze

```bash
# Richiesto
sudo apt install aircrack-ng

# Consigliati per tutte le funzionalità
sudo apt install reaver bully pixiewps hashcat hcxdumptool hcxtools
```

### Verifica dell’installazione

```bash
wifite --help
wifite --version
```

---

## 💻 Utilizzo di base

### Avvio rapido

```bash
# Avvia semplicemente wifite (rileva automaticamente l’interfaccia)
sudo wifite

# Specifica l’interfaccia
sudo wifite -i wlan0
```

### Procedura di base

```
1. Avvia wifite
2. Attendi che compaiano le reti
3. Premi Ctrl+C per interrompere la scansione
4. Seleziona uno o più bersagli tramite il numero
5. Wifite esegue automaticamente gli attacchi
6. Tenta il recupero della password dagli handshake acquisiti (scambi di messaggi di autenticazione)
```

### Modalità interattiva

```bash
# Avvia wifite
sudo wifite

# L’output mostra:
   NUM  ESSID              ENCR  POWER  WPS  CLIENT
   ---  -----------------  ----  -----  ---  ------
    1   HomeNetwork        WPA2   -45   Yes     2
    2   Office_WiFi        WPA2   -52   No      0
    3   Free_Hotspot       OPN    -60   No      5

# Inserisci i numeri dei bersagli oppure Ctrl+C per selezionare tutti quelli visibili
```

---

## 🎯 Selezione dei bersagli

ESSID indica il nome della rete; BSSID identifica l’access point tramite un indirizzo MAC.

### Selezione tramite numero

```bash
# Dopo la scansione, seleziona i bersagli tramite il numero
# Esempio: seleziona i bersagli 1 e 3
1, 3

# Seleziona un intervallo
1-5

# Seleziona tutti
all
```

### Filtro per ESSID

```bash
# Seleziona un nome di rete specifico
sudo wifite --essid "TargetNetwork"

# Più ESSID
sudo wifite --essid "Network1" --essid "Network2"
```

### Filtro per BSSID

```bash
# Seleziona un indirizzo MAC specifico
sudo wifite --bssid AA:BB:CC:DD:EE:FF
```

### Filtro per cifratura

```bash
# Solo reti WPA
sudo wifite --wpa

# Solo reti WEP
sudo wifite --wep

# Solo reti con WPS abilitato
sudo wifite --wps
```

### Filtro per canale

```bash
# Canale specifico
sudo wifite -c 6

# Solo 5 GHz
sudo wifite --5ghz

# Solo 2,4 GHz
sudo wifite --2ghz
```

### Filtro per segnale

```bash
# Potenza minima (dBm: più vicina a 0 significa più forte)
sudo wifite --power -50
```

---

## ⚔ Opzioni degli attacchi

### Opzioni WPA/WPA2

```bash
# Imposta il timeout della deautenticazione
sudo wifite --wpa-deauth-timeout 30

# Numero di deautenticazioni da inviare
sudo wifite --num-deauths 5

# Salta l’acquisizione dell’handshake, solo PMKID
sudo wifite --pmkid
```

### Opzioni di deautenticazione (deauthentication)

```bash
# Numero di pacchetti di deautenticazione
sudo wifite --num-deauths 10

# Deautenticazione continua
sudo wifite --num-deauths 0
```

### Ordine degli attacchi

```bash
# Salta gli attacchi WPA
sudo wifite --no-wpa

# Salta gli attacchi WEP
sudo wifite --no-wep

# Salta gli attacchi WPS
sudo wifite --no-wps

# Salta gli attacchi PMKID
sudo wifite --no-pmkid
```

### Arresto di NetworkManager

```bash
# Arresta automaticamente i processi che interferiscono
sudo wifite --kill

# Non arrestare i processi
sudo wifite --no-kill
```

---

## 🔓 Attacchi WPS

### Abilitazione di WPS

```bash
# Attacca solo reti con WPS abilitato
sudo wifite --wps

# Modalità solo WPS
sudo wifite --wps-only
```

### Strumenti WPS

```bash
# Usa Reaver (predefinito)
sudo wifite --wps --reaver

# Usa Bully
sudo wifite --wps --bully
```

### Attacco Pixie-Dust

```bash
# Abilita Pixie-Dust (attacco WPS offline)
sudo wifite --wps --pixie

# Timeout Pixie-Dust
sudo wifite --wps --pixie-timeout 300
```

### Opzioni WPS

```bash
# Timeout WPS per ogni tentativo di PIN
sudo wifite --wps-time 120

# Numero massimo di nuovi tentativi WPS
sudo wifite --wps-fails 10

# Timeout WPS complessivo
sudo wifite --wps-timeout 300
```

---

## 🔐 Attacchi PMKID

### Che cos’è PMKID?

Attacco PMKID (Pairwise Master Key Identifier, identificatore della chiave principale condivisa):
- ✅ Acquisisce la PMK direttamente dall’access point (affermazione dell’originale: vedi nota)
- ✅ Non richiede un client già connesso
- ✅ Non richiede la deautenticazione
- ✅ Funziona sulla maggior parte delle reti WPA2 (affermazione generale dell’originale: vedi nota)

> **Correzione dell’originale:** il PMKID è un identificatore, non la PMK né la password in chiaro. La sua acquisizione non garantisce il recupero della password. Le condizioni dipendono da access point, configurazione, adattatore e strumenti: “maggior parte delle reti WPA2” non è una garanzia e non implica supporto universale di WPA3. La [nota originale sul PMKID](https://github.com/derv82/wifite2/blob/master/PMKID.md) descrive anche casi di insuccesso e limiti dell’hardware. WPS è un meccanismo di configurazione, non un tipo di cifratura.

### Opzioni PMKID

```bash
# Solo attacchi PMKID (senza handshake)
sudo wifite --pmkid

# Timeout PMKID
sudo wifite --pmkid-timeout 120
```

### Come funziona PMKID

```
1. Invia una richiesta di associazione all’access point
2. L’access point risponde con il PMKID in un frame EAPOL
3. Acquisisci il PMKID
4. Tenta il recupero offline con hashcat
```

---

## ⚙ Opzioni avanzate

### Opzioni dell’interfaccia

```bash
# Specifica l’interfaccia
sudo wifite -i wlan0

# Specifica l’interfaccia in modalità monitor
sudo wifite -i wlan0mon
```

### Opzioni di output

```bash
# Output dettagliato
sudo wifite -v
sudo wifite --verbose

# Output molto dettagliato
sudo wifite -vv

# Modalità silenziosa
sudo wifite --quiet
```

### Opzioni temporali

```bash
# Durata della scansione (secondi)
sudo wifite --scan-time 30

# Timeout complessivo per bersaglio
sudo wifite --attack-timeout 300
```

### Password recuperate

```bash
# Le password recuperate vengono salvate in:
# ~/.config/wifite/cracked.txt

# Visualizza le reti per cui è stata recuperata la password
cat ~/.config/wifite/cracked.txt

# Oppure specifica un file personalizzato
sudo wifite --cracked cracked_passwords.txt
```

### Opzioni per saltare le operazioni

```bash
# Salta il recupero della password (solo acquisizione)
sudo wifite --skip-crack

# Salta i bersagli con password già recuperata
sudo wifite --skip-cracked
```

### MAC casuale

```bash
# Rendi casuale l’indirizzo MAC
sudo wifite --random-mac

# Non cambiare il MAC
sudo wifite --no-random-mac
```

---

## 🔓 Opzioni di recupero delle password

### Attacco con dizionario

```bash
# Specifica la wordlist
sudo wifite --dict /usr/share/wordlists/rockyou.txt

# Più wordlist
sudo wifite --dict wordlist1.txt --dict wordlist2.txt
```

### Integrazione con Hashcat

```bash
# Usa hashcat per recuperare la password
sudo wifite --hashcat

# Percorso di Hashcat
sudo wifite --hashcat-path /usr/bin/hashcat
```

### Opzioni Aircrack-ng

```bash
# Usa aircrack-ng (predefinito)
sudo wifite --aircrack

# Percorso di Aircrack-ng
sudo wifite --aircrack-path /usr/bin/aircrack-ng
```

### Modalità di solo recupero della password

```bash
# Tenta il recupero da file di handshake esistenti
sudo wifite --crack

# Specifica il file di acquisizione
sudo wifite --crack --dict wordlist.txt /path/to/capture.cap
```

---

## 📋 Esempi

### Verifica WiFi di base

```bash
# Scansiona e attacca tutte le reti
sudo wifite

# Attacca con una wordlist personalizzata
sudo wifite --dict /usr/share/wordlists/rockyou.txt
```

### Rete specifica come bersaglio

```bash
# Seleziona il bersaglio tramite il nome
sudo wifite --essid "TargetNetwork" --dict rockyou.txt

# Seleziona il bersaglio tramite il MAC
sudo wifite --bssid AA:BB:CC:DD:EE:FF
```

### Attacco limitato a WPA2

```bash
# Solo reti WPA2 con PMKID
sudo wifite --wpa --pmkid --dict rockyou.txt
```

### Attacco WPS

```bash
# WPS con Pixie-Dust
sudo wifite --wps --pixie

# Solo brute force WPS
sudo wifite --wps-only --bully
```

### Solo segnale forte

```bash
# Attacca solo reti con segnale forte
sudo wifite --power -50 --wpa
```

### Reti a 5 GHz

```bash
# Attacca soltanto la banda a 5 GHz
sudo wifite --5ghz --wpa
```

### Verifica rapida (senza recupero della password)

```bash
# Acquisisci soltanto gli handshake e recupera la password in seguito
sudo wifite --skip-crack
```

### Ripresa di un attacco

```bash
# Wifite salva automaticamente l’avanzamento
# Riavvia semplicemente lo strumento per riprendere
sudo wifite

# Salta i bersagli con password già recuperata
sudo wifite --skip-cracked
```

### Modalità dettagliata

```bash
# Visualizza tutti i dettagli
sudo wifite -vv --dict rockyou.txt
```

### Modalità discreta

```bash
# Deautenticazioni ridotte al minimo, modalità silenziosa
sudo wifite --num-deauths 1 --quiet
```

---

## 📊 Riferimento rapido

### Comandi essenziali

| Scopo | Comando |
|---------|---------|
| Scansione di base | `sudo wifite` |
| Bersaglio tramite nome | `sudo wifite --essid "Name"` |
| Bersaglio tramite MAC | `sudo wifite --bssid MAC` |
| Solo WPA | `sudo wifite --wpa` |
| Solo WPS | `sudo wifite --wps-only` |
| Attacco PMKID | `sudo wifite --pmkid` |
| Con elenco di password (wordlist) | `sudo wifite --dict wordlist.txt` |
| Salta il recupero della password | `sudo wifite --skip-crack` |

### Tipi di attacco

| Attacco | Flag | Bersaglio |
|--------|------|--------|
| WPA Handshake | `--wpa` | WPA/WPA2-PSK |
| PMKID | `--pmkid` | WPA/WPA2-PSK |
| WPS Pixie | `--wps --pixie` | WPS abilitato |
| WPS brute force | `--wps-only` | WPS abilitato |
| WEP | `--wep` | WEP (obsoleto) |

### Wordlist comuni

| Percorso | Descrizione |
|------|-------------|
| `/usr/share/wordlists/rockyou.txt` | Password più comuni |
| `/usr/share/wordlists/fasttrack.txt` | Verifica rapida |
| `/usr/share/seclists/Passwords/WiFi-WPA/` | Specifiche per il WiFi |

### File acquisiti

```
~/.config/wifite/
├── cracked.txt          # Password recuperate
├── hs/                  # Handshake acquisiti
│   └── handshake_*.cap
└── pmkid/               # PMKID acquisiti
    └── pmkid_*.22000
```

### Riferimento rapido dei flag

| Flag | Descrizione |
|------|-------------|
| `-i` | Interfaccia |
| `-c` | Canale |
| `-v` | Output dettagliato |
| `--wpa` | Solo WPA/WPA2 |
| `--wep` | Solo WEP |
| `--wps` | WPS abilitato |
| `--pmkid` | Attacco PMKID |
| `--dict` | Wordlist |
| `--kill` | Arresta i processi |
| `--power` | Segnale minimo |
| `--5ghz` | Banda a 5 GHz |
| `--2ghz` | Banda a 2,4 GHz |

### Risoluzione dei problemi

```bash
# Interfaccia non trovata
sudo airmon-ng check kill
sudo wifite -i wlan0

# Nessuna rete trovata
# - Verifica che l’adattatore supporti la modalità monitor
# - Prova bande diverse: --5ghz o --2ghz
# - Avvicinati ai bersagli

# Handshake non acquisito
# - Aumenta le deautenticazioni: --num-deauths 10
# - Attendi più a lungo: --wpa-deauth-timeout 60
# - Prova client diversi

# Recupero della password non riuscito
# - Usa una wordlist migliore
# - Prova hashcat con GPU: --hashcat
```

---

## 🔄 Confronto tra Wifite e l’uso manuale di Aircrack-ng

| Attività | Wifite | Suite Aircrack-ng |
|------|--------|-------------------|
| Modalità monitor | Automatica | `airmon-ng start wlan0` |
| Scansione | Automatica | `airodump-ng wlan0mon` |
| Bersaglio | Interattivo | Filtro manuale |
| Deautenticazione | Automatica | `aireplay-ng -0 ...` |
| Acquisizione | Automatica | `airodump-ng -w ...` |
| Recupero della password | Automatico | `aircrack-ng -w ...` |
| **Impegno** | **Semplice** | Manuale |

---

## ⚠ Avvertenza legale

```
⚠️ ATTENZIONE: usa Wifite soltanto su reti di tua PROPRIETÀ o per le quali disponi del
PERMESSO SCRITTO ESPLICITO di eseguire test.

✅ Penetration testing autorizzato
✅ Verifica della sicurezza (con permesso)
✅ Scopi didattici (ambiente di laboratorio)

❌ L’accesso non autorizzato alle reti è ILLEGALE
❌ Non usare mai lo strumento per scopi malevoli
```

---

## 📚 Risorse

- [Wifite2 GitHub](https://github.com/derv82/wifite2)
- [Wifite Wiki](https://github.com/derv82/wifite2/wiki)
- [Aircrack-ng](https://www.aircrack-ng.org/)

### Schede correlate
- [Aircrack-ng](../../Aircrack-ng/translations/README.it.md)
- [Hashcat](../../Hashcat/translations/README.it.md)
- [Comandi Linux](../../Linux-Commands/translations/README.it.md)

---

<p align="center">
  <b>📶 Verifica WiFi automatizzata!</b><br>
  <i>Wifite - Seleziona, fai clic e verifica la sicurezza (in modo etico!)</i>
</p>
