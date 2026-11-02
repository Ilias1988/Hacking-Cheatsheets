# 📶 Wifite – hurtigguide til automatisert sikkerhetsrevisjon av WiFi

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗    ██╗██╗███████╗██╗████████╗███████╗
  ██║    ██║██║██╔════╝██║╚══██╔══╝██╔════╝
  ██║ █╗ ██║██║█████╗  ██║   ██║   █████╗  
  ██║███╗██║██║██╔══╝  ██║   ██║   ██╔══╝  
  ╚███╔███╔╝██║██║     ██║   ██║   ███████╗
   ╚══╝╚══╝ ╚═╝╚═╝     ╚═╝   ╚═╝   ╚══════╝
         Automatisert verktøy for WiFi-testing
```

<p align="center">
  <img src="https://img.shields.io/badge/Wifite-Automated_WiFi-blue?style=for-the-badge" alt="Wifite">
  <img src="https://img.shields.io/badge/WPA2-Cracking-red?style=for-the-badge" alt="WPA2">
  <img src="https://img.shields.io/badge/Python-yellow?style=for-the-badge" alt="Python">
</p>

> **Merknad til originalen:** Flere flagg og lagringsstier er versjonsavhengige eller stemmer ikke med den undersøkte Wifite2-koden. Blant annet er `--no-wpa`, `--2ghz`, `--hashcat-path` og `--wpa-deauth-timeout` ikke definert der; `--5ghz` inkluderer 5 GHz og betyr ikke nødvendigvis «bare 5 GHz». `--wps-time` gjelder samlet ventetid for Pixie-Dust, `--cracked` viser tidligere knekte nettverk, og gjentatte `--essid`/`--dict` samler ikke verdiene i en liste ([argumentdefinisjoner](https://github.com/derv82/wifite2/blob/master/wifite/args.py), [konfigurasjon](https://github.com/derv82/wifite2/blob/master/wifite/config.py)). PMKID er en identifikator, ikke selve PMK-nøkkelen; den relevante verdien kan bare hentes fra sårbare tilgangspunkter, og støtten er ikke universell for WPA2 ([Bettercaps protokollbeskrivelse](https://www.bettercap.org/modules/wifi/)). Eksemplene og originalens påstander er bevart; kontroller dem mot dokumentasjonen for aktuell versjon.

---

## 📋 Innhold

- [Hva er Wifite](#-hva-er-wifite)
- [Installasjon](#-installasjon)
- [Grunnleggende bruk](#-grunnleggende-bruk)
- [Valg av mål](#-valg-av-mål)
- [Angrepsvalg](#-angrepsvalg)
- [WPS-angrep](#-wps-angrep)
- [PMKID-angrep](#-pmkid-angrep)
- [Avanserte valg](#-avanserte-valg)
- [Valg for passordknekking](#-valg-for-passordknekking)
- [Eksempler](#-eksempler)
- [Hurtigreferanse](#-hurtigreferanse)

---

## 🎯 Hva er Wifite

**Wifite** (Wifite2) er et verktøy for automatisert sikkerhetsrevisjon av trådløse nettverk som:

- 🤖 **Automatiserer angrep** – uten manuell inntasting av kommandoer
- 🎯 **Støtter flere mål** – angriper flere nettverk
- 🔓 **WPA/WPA2/WEP/WPS** – dekker krypterings- og oppsettsmekanismene i listen
- 💾 **Lagrer fremdrift** – gjenopptar avbrutte angrep
- 🔄 **Fanger opp PMKID** – moderne angrep uten tilkoblet klient

### Wifite sammenlignet med Wifite2

| Funksjon | Wifite (opprinnelig) | Wifite2 |
| --------- | ------------------- | --------- |
| Vedlikeholdes | Nei | Ja |
| Python | Python 2 | Python 3 |
| PMKID | Nei | Ja |
| Kode | Uoversiktlig | Ryddig omskriving |
| Bruk | Unngå | **Anbefalt** |

### Funksjoner

| Funksjon | Beskrivelse |
| --------- | ------------- |
| **Automatisert** | WiFi-revisjon med få manuelle trinn |
| **Smart målvalg** | Velger mål etter signalstyrke |
| **Alle angrep** | WPA, WEP, WPS, PMKID |
| **Passordknekking** | Integreres med Hashcat/aircrack |
| **Kan gjenopptas** | Fortsett avbrutte økter |

---

## 🚀 Installasjon

### Kali Linux (forhåndsinstallert)

```bash
# Allerede installert i Kali
wifite --help

# Oppdater til nyeste versjon
sudo apt update && sudo apt install wifite
```

### Installer Wifite2 fra GitHub

```bash
# Klon kodelageret
git clone https://github.com/derv82/wifite2.git
cd wifite2

# Kjør direkte
sudo python3 wifite.py

# Eller installer
sudo python3 setup.py install
```

### Avhengigheter

```bash
# Påkrevd
sudo apt install aircrack-ng

# Anbefales for full funksjonalitet
sudo apt install reaver bully pixiewps hashcat hcxdumptool hcxtools
```

### Kontroller installasjonen

```bash
wifite --help
wifite --version
```

---

## 💻 Grunnleggende bruk

### Hurtigstart

```bash
# Kjør wifite (grensesnittet oppdages automatisk)
sudo wifite

# Angi grensesnitt
sudo wifite -i wlan0
```

### Grunnleggende arbeidsflyt

```
1. Kjør wifite
2. Vent til nettverk vises
3. Trykk Ctrl+C for å stoppe skanningen
4. Velg ett eller flere mål etter nummer
5. Wifite angriper automatisk
6. Knekk nøklene fra innsamlede håndtrykk
```

### Interaktiv modus

```bash
# Start wifite
sudo wifite

# Utdataene viser:
   NUM  ESSID              ENCR  POWER  WPS  CLIENT
   ---  -----------------  ----  -----  ---  ------
    1   HomeNetwork        WPA2   -45   Yes     2
    2   Office_WiFi        WPA2   -52   No      0
    3   Free_Hotspot       OPN    -60   No      5

# Skriv inn målnumre, eller bruk Ctrl+C for å velge alle synlige mål
```

---

## 🎯 Valg av mål

### Velg etter nummer

```bash
# Etter skanningen velger du mål etter nummer
# Eksempel: Velg mål 1 og 3
1, 3

# Velg et intervall
1-5

# Velg alle
all
```

### Filtrer etter ESSID

```bash
# Velg et bestemt nettverksnavn
sudo wifite --essid "TargetNetwork"

# Flere ESSID-er
sudo wifite --essid "Network1" --essid "Network2"
```

### Filtrer etter BSSID

```bash
# Velg en bestemt MAC-adresse
sudo wifite --bssid AA:BB:CC:DD:EE:FF
```

### Filtrer etter kryptering

```bash
# Bare WPA-nettverk
sudo wifite --wpa

# Bare WEP-nettverk
sudo wifite --wep

# Bare nettverk med WPS aktivert
sudo wifite --wps
```

### Filtrer etter kanal

```bash
# Bestemt kanal
sudo wifite -c 6

# Bare 5 GHz
sudo wifite --5ghz

# Bare 2,4 GHz
sudo wifite --2ghz
```

### Filtrer etter signalstyrke

```bash
# Laveste signalstyrke (dBm; nærmere 0 betyr sterkere signal)
sudo wifite --power -50
```

---

<a id="-angrepsvalg"></a>
## ⚔️ Angrepsvalg

### Valg for WPA/WPA2

```bash
# Angi tidsavbrudd for deautentisering
sudo wifite --wpa-deauth-timeout 30

# Antall deautentiseringer som skal sendes
sudo wifite --num-deauths 5

# Hopp over fangst av håndtrykk; bare PMKID
sudo wifite --pmkid
```

### Valg for deautentisering

```bash
# Antall deautentiseringspakker
sudo wifite --num-deauths 10

# Kontinuerlig deautentisering
sudo wifite --num-deauths 0
```

### Angrepsrekkefølge

```bash
# Hopp over WPA-angrep
sudo wifite --no-wpa

# Hopp over WEP-angrep
sudo wifite --no-wep

# Hopp over WPS-angrep
sudo wifite --no-wps

# Hopp over PMKID-angrep
sudo wifite --no-pmkid
```

### Avslutt NetworkManager

```bash
# Avslutt forstyrrende prosesser automatisk
sudo wifite --kill

# Ikke avslutt prosesser
sudo wifite --no-kill
```

---

## 🔓 WPS-angrep

### Aktiver WPS

```bash
# Angrip bare nettverk med WPS aktivert
sudo wifite --wps

# Modus for bare WPS
sudo wifite --wps-only
```

### WPS-verktøy

```bash
# Bruk Reaver (standard)
sudo wifite --wps --reaver

# Bruk Bully
sudo wifite --wps --bully
```

### Pixie-Dust-angrep

```bash
# Aktiver Pixie-Dust (frakoblet WPS-angrep)
sudo wifite --wps --pixie

# Tidsavbrudd for Pixie-Dust
sudo wifite --wps --pixie-timeout 300
```

### Valg for WPS

```bash
# WPS-tidsavbrudd per PIN-forsøk
sudo wifite --wps-time 120

# Maksimalt antall nye WPS-forsøk
sudo wifite --wps-fails 10

# Samlet tidsavbrudd for WPS
sudo wifite --wps-timeout 300
```

---

## 🔐 PMKID-angrep

### Hva er PMKID?

Et PMKID-angrep (Pairwise Master Key Identifier, identifikator for en parvis hovednøkkel):
- ✅ Fanger opp PMK direkte fra tilgangspunktet
- ✅ Krever ingen klient
- ✅ Krever ingen deautentisering
- ✅ Fungerer på de fleste WPA2-nettverk

### Valg for PMKID

```bash
# Bare PMKID-angrep (uten håndtrykk)
sudo wifite --pmkid

# Tidsavbrudd for PMKID
sudo wifite --pmkid-timeout 120
```

### Slik fungerer PMKID

```
1. Send en tilknytningsforespørsel til tilgangspunktet
2. Tilgangspunktet svarer med PMKID i en EAPOL-ramme
3. Fang opp PMKID
4. Knekk passordet frakoblet med hashcat
```

---

<a id="-avanserte-valg"></a>
## ⚙️ Avanserte valg

### Grensesnittvalg

```bash
# Angi grensesnitt
sudo wifite -i wlan0

# Angi monitorgrensesnitt
sudo wifite -i wlan0mon
```

### Valg for utdata

```bash
# Detaljert utdata
sudo wifite -v
sudo wifite --verbose

# Svært detaljert utdata
sudo wifite -vv

# Stille modus
sudo wifite --quiet
```

### Tidsinnstillinger

```bash
# Skannetid (sekunder)
sudo wifite --scan-time 30

# Samlet tidsavbrudd per mål
sudo wifite --attack-timeout 300
```

### Knekte passord

```bash
# Knekte passord lagres her:
# ~/.config/wifite/cracked.txt

# Vis nettverk med knekte passord
cat ~/.config/wifite/cracked.txt

# Eller angi en egen fil
sudo wifite --cracked cracked_passwords.txt
```

### Valg for å hoppe over trinn

```bash
# Hopp over passordknekking (bare pakkefangst)
sudo wifite --skip-crack

# Hopp over mål der passordet allerede er knekt
sudo wifite --skip-cracked
```

### Tilfeldig MAC-adresse

```bash
# Velg en tilfeldig MAC-adresse
sudo wifite --random-mac

# Ikke endre MAC-adressen
sudo wifite --no-random-mac
```

---

## 🔓 Valg for passordknekking

### Ordlisteangrep

```bash
# Angi ordliste
sudo wifite --dict /usr/share/wordlists/rockyou.txt

# Flere ordlister
sudo wifite --dict wordlist1.txt --dict wordlist2.txt
```

### Integrasjon med Hashcat

```bash
# Bruk hashcat til passordknekking
sudo wifite --hashcat

# Plassering av Hashcat
sudo wifite --hashcat-path /usr/bin/hashcat
```

### Valg for Aircrack-ng

```bash
# Bruk aircrack-ng (standard)
sudo wifite --aircrack

# Sti til Aircrack-ng
sudo wifite --aircrack-path /usr/bin/aircrack-ng
```

### Modus for bare passordknekking

```bash
# Knekk passord fra eksisterende håndtrykksfiler
sudo wifite --crack

# Angi fil med pakkefangst
sudo wifite --crack --dict wordlist.txt /path/to/capture.cap
```

---

## 📋 Eksempler

### Grunnleggende WiFi-revisjon

```bash
# Skann og angrip alle nettverk
sudo wifite

# Angrip med en egen ordliste
sudo wifite --dict /usr/share/wordlists/rockyou.txt
```

### Velg et bestemt nettverk

```bash
# Velg mål etter navn
sudo wifite --essid "TargetNetwork" --dict rockyou.txt

# Velg mål etter MAC-adresse
sudo wifite --bssid AA:BB:CC:DD:EE:FF
```

### Angrep bare mot WPA2

```bash
# Bare WPA2-nettverk med PMKID
sudo wifite --wpa --pmkid --dict rockyou.txt
```

### WPS-angrep

```bash
# WPS med Pixie-Dust
sudo wifite --wps --pixie

# Bare uttømmende utprøving av WPS-PIN
sudo wifite --wps-only --bully
```

### Bare sterke signaler

```bash
# Angrip bare nettverk med sterkt signal
sudo wifite --power -50 --wpa
```

### 5 GHz-nettverk

```bash
# Angrip bare på 5 GHz-båndet
sudo wifite --5ghz --wpa
```

### Rask revisjon (uten passordknekking)

```bash
# Fang bare opp håndtrykk; knekk passordene senere
sudo wifite --skip-crack
```

### Gjenoppta angrepet

```bash
# Wifite lagrer fremdriften automatisk
# Kjør på nytt for å gjenoppta
sudo wifite

# Hopp over allerede knekte passord
sudo wifite --skip-cracked
```

### Detaljert modus

```bash
# Vis alle detaljer
sudo wifite -vv --dict rockyou.txt
```

### Diskré modus

```bash
# Minimalt antall deautentiseringer; stille modus
sudo wifite --num-deauths 1 --quiet
```

---

## 📊 Hurtigreferanse

### Viktige kommandoer

| Formål | Kommando |
| --------- | --------- |
| Grunnleggende skanning | `sudo wifite` |
| Velg mål etter navn | `sudo wifite --essid "Name"` |
| Velg mål etter MAC-adresse | `sudo wifite --bssid MAC` |
| Bare WPA | `sudo wifite --wpa` |
| Bare WPS | `sudo wifite --wps-only` |
| PMKID-angrep | `sudo wifite --pmkid` |
| Med ordliste | `sudo wifite --dict wordlist.txt` |
| Hopp over passordknekking | `sudo wifite --skip-crack` |

### Angrepstyper

| Angrep | Flagg | Mål |
| -------- | ------ | -------- |
| WPA-håndtrykk | `--wpa` | WPA/WPA2-PSK |
| PMKID | `--pmkid` | WPA/WPA2-PSK |
| WPS Pixie | `--wps --pixie` | WPS aktivert |
| Uttømmende utprøving av WPS-PIN | `--wps-only` | WPS aktivert |
| WEP | `--wep` | WEP (foreldet) |

### Vanlige ordlister

| Sti | Beskrivelse |
| ------ | ------------- |
| `/usr/share/wordlists/rockyou.txt` | De vanligste passordene |
| `/usr/share/wordlists/fasttrack.txt` | Rask kontroll |
| `/usr/share/seclists/Passwords/WiFi-WPA/` | Spesifikke for WiFi |

### Innsamlede filer

```
~/.config/wifite/
├── cracked.txt          # Knekte passord
├── hs/                  # Innsamlede håndtrykk
│   └── handshake_*.cap
└── pmkid/               # Innsamlede PMKID-er
    └── pmkid_*.22000
```

### Hurtigreferanse for flagg

| Flagg | Beskrivelse |
| ------ | ------------- |
| `-i` | Grensesnitt |
| `-c` | Kanal |
| `-v` | Detaljert utdata |
| `--wpa` | Bare WPA/WPA2 |
| `--wep` | Bare WEP |
| `--wps` | WPS aktivert |
| `--pmkid` | PMKID-angrep |
| `--dict` | Ordliste |
| `--kill` | Avslutt prosesser |
| `--power` | Laveste signalstyrke |
| `--5ghz` | 5 GHz-båndet |
| `--2ghz` | 2,4 GHz-båndet |

### Feilsøking

```bash
# Grensesnittet ble ikke funnet
sudo airmon-ng check kill
sudo wifite -i wlan0

# Ingen nettverk funnet
# - Kontroller at adapteren støtter monitormodus
# - Prøv andre frekvensbånd: --5ghz eller --2ghz
# - Flytt nærmere målene

# Håndtrykket ble ikke fanget opp
# - Øk antallet deautentiseringer: --num-deauths 10
# - Vent lenger: --wpa-deauth-timeout 60
# - Prøv andre klienter

# Passordknekkingen mislykkes
# - Bruk en bedre ordliste
# - Prøv hashcat med GPU: --hashcat
```

---

## 🔄 Wifite sammenlignet med manuell bruk av Aircrack-ng

| Oppgave | Wifite | Aircrack-ng-verktøysamlingen |
| ------ | -------- | ------------------- |
| Monitormodus | Automatisk | `airmon-ng start wlan0` |
| Skanning | Automatisk | `airodump-ng wlan0mon` |
| Mål | Interaktivt | Manuell filtrering |
| Deautentisering | Automatisk | `aireplay-ng -0 ...` |
| Pakkefangst | Automatisk | `airodump-ng -w ...` |
| Passordknekking | Automatisk | `aircrack-ng -w ...` |
| **Arbeidsinnsats** | **Enkelt** | Manuelt |

---

## ⚠️ Juridisk ansvarsfraskrivelse

```
⚠️ ADVARSEL: Bruk bare Wifite på nettverk du EIER eller har
UTTRYKKELIG SKRIFTLIG TILLATELSE til å teste.

✅ Autorisert penetrasjonstesting
✅ Sikkerhetsrevisjon (med tillatelse)
✅ Opplæring (i et laboratoriemiljø)

❌ Uautorisert nettverkstilgang er ULOVLIG
❌ Bruk aldri verktøyet til ondsinnede formål
```

---

## 📚 Ressurser

- [Wifite2 på GitHub](https://github.com/derv82/wifite2)
- [Wifites wiki](https://github.com/derv82/wifite2/wiki)
- [Aircrack-ng](https://www.aircrack-ng.org/)

### Relaterte hurtigguider
- [Aircrack-ng](../../Aircrack-ng/translations/README.nb.md)
- [Hashcat](../../Hashcat/translations/README.nb.md)
- [Linux-kommandoer](../../Linux-Commands/translations/README.nb.md)

---

<p align="center">
  <b>📶 Automatisert sikkerhetsrevisjon av WiFi!</b><br>
  <i>Wifite – velg, klikk og test (etisk!)</i>
</p>
