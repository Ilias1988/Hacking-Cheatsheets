# 🦈 Wireshark – komplett hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
 __        ___           ____  _                _    
 \ \      / (_)_ __ ___ / ___|| |__   __ _ _ __| | __
  \ \ /\ / /| | '__/ _ \\___ \| '_ \ / _` | '__| |/ /
   \ V  V / | | | |  __/ ___) | | | | (_| | |  |   < 
    \_/\_/  |_|_|  \___|____/|_| |_|\__,_|_|  |_|\_\
                                                     
           Analyse av nettverksprotokoller
```

<p align="center">
  <img src="https://img.shields.io/badge/Wireshark-Network_Analyzer-blue?style=for-the-badge" alt="Wireshark">
  <img src="https://img.shields.io/badge/Packet-Capture-red?style=for-the-badge" alt="Pakkeopptak">
  <img src="https://img.shields.io/badge/Protocol-Analysis-green?style=for-the-badge" alt="Protocol">
  <img src="https://img.shields.io/badge/Open-Source-orange?style=for-the-badge" alt="Åpen kildekode">
</p>

<p align="center">
  <b>🌐 Et mye brukt verktøy for analyse av nettverksprotokoller</b>
</p>

---

## 📋 Innholdsfortegnelse

- [Innledning](#-innledning)
- [Installasjon](#-installasjon)
- [Oversikt over grensesnittet](#-oversikt-over-grensesnittet)
- [Grunnleggende trafikkopptak](#-grunnleggende-trafikkopptak)
- [Visningsfiltre](#-visningsfiltre)
- [Opptaksfiltre](#-opptaksfiltre)
- [Protokollanalyse](#-protokollanalyse)
- [Følge datastrømmer](#-følge-datastrømmer)
- [Statistikk og analyse](#-statistikk-og-analyse)
- [tshark (kommandolinje)](#-tshark-kommandolinje)
- [Praktiske eksempler](#-praktiske-eksempler)
- [Hurtigreferanse](#-hurtigreferanse)
- [Tastatursnarveier](#-tastatursnarveier)
- [Tips og anbefalte arbeidsmåter](#-tips-og-anbefalte-arbeidsmåter)
- [Ressurser](#-ressurser)

---

## 🎯 Innledning

> **Begreper og presisering:** Opptaksfiltre (capture filters) velger hva som tas opp; visningsfiltre (display filters) velger hva som vises. BPF og Wiresharks filterspråk er forskjellige. Menystier og hurtigtaster er konkrete, versjonsavhengige UI-verdier og er bevart på engelsk. Betydningen av `!=` for flerverdifelt ble endret i Wireshark 3.6; se [filterveiledningen](https://www.wireshark.org/docs/wsug_html_chunked/ChWorkBuildDisplayFilterSection.html). SYN/ACK-, RST-, ARP- og DNS-størrelsesfiltrene viser indikatorer, ikke alene et fullstendig håndtrykk, angrep eller eksfiltrering. TLS-filtre dekrypterer ikke automatisk HTTPS. `tls.record.version == 0x0303` beviser ikke alene forhandlet TLS 1.2; se [TLS-feltreferansen](https://www.wireshark.org/docs/dfref/t/tls.html) og [RFC 8446](https://www.rfc-editor.org/rfc/rfc8446.html#section-5.1).

**Wireshark** er en gratis pakkeanalysator med åpen kildekode, brukt til feilsøking av nettverk, analyse, programvareutvikling og sikkerhetsrevisjon.

### Viktige egenskaper

| Egenskap | Beskrivelse |
|---------|-------------|
| **Grundig inspeksjon** | Støtter hundrevis av protokoller |
| **Direkteopptak** | Pakkeopptak i sanntid |
| **Frakoblet analyse** | Les opptaksfiler (pcap, pcapng) |
| **Kraftige filtre** | Visnings- og opptaksfiltre |
| **Flere plattformer** | Windows, Linux, macOS |
| **Grafisk grensesnitt og kommandolinje** | Wireshark (GUI) og tshark (CLI) |

### Bruksområder

| Bruksområde | Beskrivelse |
|----------|-------------|
| **Feilsøking av nettverk** | Undersøk tilkoblingsproblemer |
| **Sikkerhetsanalyse** | Oppdag ondsinnet trafikk |
| **Protokollutvikling** | Test nye protokoller |
| **Digital etterforskning** | Analyser opptatt trafikk |
| **Opplæring** | Lær om nettverk |

---

## 📥 Installasjon

### Windows
```bash
# Last ned fra det offisielle nettstedet
https://www.wireshark.org/download.html

# Eller bruk Chocolatey
choco install wireshark
```

### Linux (Kali/Ubuntu)
```bash
# Kali (forhåndsinstallert)
wireshark

# Ubuntu/Debian
sudo apt install wireshark

# Tillat opptak uten root
sudo usermod -aG wireshark $USER
# Logg ut og inn igjen
```

### macOS
```bash
brew install --cask wireshark
```

### Kontrollere installasjonen
```bash
wireshark --version
tshark --version
```

---

<a id="-oversikt-over-grensesnittet"></a>
## 🖥️ Oversikt over grensesnittet

### Områder i hovedvinduet

| Område | Beskrivelse |
|------|-------------|
| **Menylinje** | File, Edit, View, Go, Capture, Analyze |
| **Hovedverktøylinje** | Knapper for rask tilgang |
| **Filterlinje** | Skriv inn visningsfiltre |
| **Pakkeliste** | Alle pakker i opptaket |
| **Pakkedetaljer** | Protokolllagene i valgt pakke |
| **Pakkebyte** | Rådata i hex/ASCII |

### Kolonner i pakkelisten (standard)

| Kolonne | Beskrivelse |
|--------|-------------|
| No. | Pakkenummer |
| Time | Tidsstempel |
| Source | Kildens IP-/MAC-adresse |
| Destination | Målets IP-/MAC-adresse |
| Protokoll | Protokoll på høyeste lag |
| Length | Pakkestørrelse i byte |
| Info | Protokollspesifikke opplysninger |

---

## 📡 Grunnleggende trafikkopptak

### Starte opptak

```
1. Velg nettverksgrensesnitt på velkomstskjermen
2. Klikk på "Start Capture" (blå haifinne)
3. Eller: Capture → Start

Snarvei: Dobbeltklikk på grensesnittnavnet
```

### Stoppe opptak

```
Klikk på den røde firkanten i verktøylinjen
Eller: Capture → Stop
Eller: Ctrl+E
```

### Lagre opptak

```
File → Save As
Formater: pcapng (standard), pcap osv.
```

### Åpne en opptaksfil

```
File → Open
Eller: Ctrl+O
Eller: wireshark file.pcap
```

---

## 🔍 Visningsfiltre

### Filtersyntaks

Visningsfiltre bruker **syntaks for sammenligning av felt**:

```
field operator value
```

### Sammenligningsoperatorer

| Operator | Beskrivelse | Eksempel |
|----------|-------------|---------|
| `==` or `eq` | Lik | `ip.addr == 192.168.1.1` |
| `!=` or `ne` | Ikke lik | `ip.addr != 10.0.0.1` |
| `>` or `gt` | Større enn | `frame.len > 100` |
| `<` or `lt` | Mindre enn | `tcp.port < 1024` |
| `>=` or `ge` | Større enn eller lik | `frame.len >= 64` |
| `<=` or `le` | Mindre enn eller lik | `tcp.port <= 1024` |
| `contains` | Inneholder en streng | `http contains "password"` |
| `matches` | Treff med regulært uttrykk | `http.host matches ".*\.com"` |

### Logiske operatorer

| Operator | Beskrivelse | Eksempel |
|----------|-------------|---------|
| `and` or `&&` | Logisk OG | `ip.src == 192.168.1.1 and tcp.port == 80` |
| `or` or `\|\|` | Logisk ELLER | `tcp.port == 80 or tcp.port == 443` |
| `not` or `!` | Logisk IKKE | `not arp` |
| `()` | Gruppering | `(ip.src == 10.0.0.1) and (tcp or udp)` |

### IP-filtre

```bash
# Bestemt IP-adresse (kilde eller mål)
ip.addr == 192.168.1.1

# Bare kilde-IP
ip.src == 192.168.1.1

# Bare mål-IP
ip.dst == 192.168.1.1

# IP-område (undernett)
ip.addr == 192.168.1.0/24

# Utelat IP-adressen
ip.addr != 192.168.1.1

# Mellom to verter
ip.addr == 192.168.1.1 and ip.addr == 192.168.1.2
```

### Portfiltre

```bash
# Bestemt port (kilde eller mål)
tcp.port == 80
udp.port == 53

# Bare kildeport
tcp.srcport == 443

# Bare målport
tcp.dstport == 22

# Portområde
tcp.port >= 1 and tcp.port <= 1024

# Flere porter
tcp.port == 80 or tcp.port == 443 or tcp.port == 8080
tcp.port in {80 443 8080}
```

### Protokollfiltre

```bash
# Etter protokollnavn
http
dns
tcp
udp
arp
icmp
tls
ssh
ftp
smtp
dhcp

# Spesifikt for HTTP
http.request
http.response
http.request.method == "GET"
http.request.method == "POST"
http.response.code == 200
http.response.code >= 400

# Spesifikt for DNS
dns.qry.name == "google.com"
dns.qry.type == 1          # A-oppføring
dns.flags.response == 1    # DNS-responser

# Spesifikt for TCP
tcp.flags.syn == 1         # SYN-pakker
tcp.flags.fin == 1         # FIN-pakker
tcp.flags.reset == 1       # RST-pakker
tcp.analysis.retransmission  # Retransmisjoner
tcp.stream == 5            # Bestemt datastrøm
```

### Innholdsfiltre

```bash
# Inneholder en streng
http contains "password"
tcp contains "login"
frame contains "secret"

# HTTP-innhold
http.request.uri contains "admin"
http.host == "example.com"
http.user_agent contains "Mozilla"
http.cookie contains "session"

# Treff uten skille mellom store og små bokstaver
http.host matches "(?i)example"
```

### Vanlige filtereksempler

```bash
# Bare HTTP-trafikk
http

# HTTPS-trafikk
tls

# DNS-spørringer
dns.flags.response == 0

# Mislykkede forbindelser (RST)
tcp.flags.reset == 1

# Store pakker
frame.len > 1000

# Bestemt MAC-adresse
eth.addr == aa:bb:cc:dd:ee:ff

# Kringkastingstrafikk
eth.dst == ff:ff:ff:ff:ff:ff

# Trafikk utenfor lokalnettet
!(ip.addr == 192.168.0.0/16)

# TCPs treveis håndtrykk
tcp.flags.syn == 1 or tcp.flags.ack == 1

# HTTP POST-forespørsler med data
http.request.method == "POST" and http.file_data
```

---

## 🎣 Opptaksfiltre

### BPF-syntaks (Berkeley Packet Filter)

Opptaksfiltre bruker **BPF-syntaks**, som skiller seg fra visningsfiltre.

### Grunnleggende syntaks

```bash
# Vert
host 192.168.1.1
src host 192.168.1.1
dst host 192.168.1.1

# Nettverk
net 192.168.1.0/24
src net 10.0.0.0/8

# Port
port 80
src port 443
dst port 22
portrange 1-1024

# Protokoll
tcp
udp
icmp
arp
```

### Kombinerte filtre

```bash
# Vert og port
host 192.168.1.1 and port 80

# Bestemt samtale
host 192.168.1.1 and host 192.168.1.2

# Utelat trafikk
not port 22
not host 10.0.0.1

# Sammensatt filter
tcp and (port 80 or port 443) and host 192.168.1.1
```

### Vanlige opptaksfiltre

| Formål | Filter |
|---------|--------|
| Bestemt vert | `host 192.168.1.1` |
| Webtrafikk | `port 80 or port 443` |
| Bare DNS | `port 53` |
| Bare SSH | `port 22` |
| Ikke kringkasting | `not broadcast` |
| Bestemt undernett | `net 192.168.1.0/24` |

---

## 🔬 Protokollanalyse

### TCP-analyse

```bash
# TCP-flagg
tcp.flags.syn == 1 and tcp.flags.ack == 0    # Bare SYN
tcp.flags.syn == 1 and tcp.flags.ack == 1    # SYN-ACK
tcp.flags.fin == 1                            # FIN
tcp.flags.reset == 1                          # RST

# TCP-problemer
tcp.analysis.retransmission
tcp.analysis.duplicate_ack
tcp.analysis.zero_window
tcp.analysis.lost_segment

# TCP-strøm
tcp.stream == 0
```

### HTTP-analyse

```bash
# HTTP-forespørsler
http.request
http.request.method == "GET"
http.request.method == "POST"
http.request.uri contains "/login"

# HTTP-responser
http.response
http.response.code == 200
http.response.code == 404
http.response.code >= 400    # Feil

# HTTP-innhold
http.content_type contains "json"
http.content_type contains "html"
http.file_data contains "password"
```

### DNS-analyse

```bash
# DNS-spørringer
dns.flags.response == 0
dns.qry.name == "example.com"
dns.qry.type == 1      # A-oppføring
dns.qry.type == 28     # AAAA-oppføring
dns.qry.type == 5      # CNAME
dns.qry.type == 15     # MX

# DNS-responser
dns.flags.response == 1
dns.flags.rcode != 0   # Feil
```

### TLS-/SSL-analyse

```bash
# TLS-trafikk
tls

# TLS-håndtrykk
tls.handshake
tls.handshake.type == 1     # Client Hello
tls.handshake.type == 2     # Server Hello

# Sertifikater
tls.handshake.certificate

# TLS-versjon
tls.record.version == 0x0303  # TLS 1.2
```

### ARP-analyse

```bash
# All ARP-trafikk
arp

# ARP-forespørsler
arp.opcode == 1

# ARP-svar
arp.opcode == 2

# Uoppfordret ARP (mulig forfalskning)
arp.isgratuitous == 1
```

---

## 🔗 Følge datastrømmer

### Følge en TCP-strøm

```
Høyreklikk på pakken → Follow → TCP Stream

Eller: Analyze → Follow → TCP Stream
```

### Følge en HTTP-strøm

```
Høyreklikk på HTTP-pakken → Follow → HTTP Stream
```

### Følge en UDP-strøm

```
Høyreklikk på UDP-pakken → Follow → UDP Stream
```

### Strømvinduet

- Viser hele samtalen
- Fargekodet (klient/server)
- Lagre som tekst eller rådata
- Filterknapper for retning

---

## 📊 Statistikk og analyse

### Protokollhierarki

```
Statistics → Protocol Hierarchy

Viser fordelingen av protokoller i opptaket
```

### Samtaler

```
Statistics → Conversations

Vis alle samtaler etter:
- Ethernet
- IPv4/IPv6
- TCP
- UDP
```

### Endepunkter

```
Statistics → Endpoints

List alle unike endepunkter
```

### I/O-graf

```
Statistics → IO Graph

Vis trafikk over tid
```

### Ekspertinformasjon

```
Analyze → Expert Information

Viser advarsler, feil og merknader:
- TCP-retransmisjoner
- Feilformede pakker
- Feil
```

### HTTP-statistikk

```
Statistics → HTTP → Requests
Statistics → HTTP → Packet Counter
```

---

## 💻 tshark (kommandolinje)

### Grunnleggende bruk

```bash
# Ta opp pakker
tshark -i eth0

# Les en opptaksfil
tshark -r capture.pcap

# Skriv til fil
tshark -i eth0 -w output.pcap
```

### Bruke filtre

```bash
# Opptaksfilter
tshark -i eth0 -f "port 80"

# Visningsfilter
tshark -r capture.pcap -Y "http"
```

### Utdatavalg

```bash
# Vis bestemte felt
tshark -r capture.pcap -T fields -e ip.src -e ip.dst -e tcp.port

# Utdata i JSON
tshark -r capture.pcap -T json

# Vis statistikk
tshark -r capture.pcap -q -z io,stat,1
```

### Vanlige tshark-eksempler

```bash
# Direkteopptak av HTTP-forespørsler
tshark -i eth0 -Y "http.request" -T fields -e http.host -e http.request.uri

# Hent ut DNS-spørringer
tshark -r capture.pcap -Y "dns.flags.response == 0" -T fields -e dns.qry.name

# Tell pakker per IP-adresse
tshark -r capture.pcap -q -z ip_hosts,tree

# HTTP-statistikk
tshark -r capture.pcap -q -z http,tree
```

---

## 🎬 Praktiske eksempler

### Eksempel 1: Ta opp HTTP-trafikk
```bash
# Opptaksfilter: port 80
# Visningsfilter: http
# Følg HTTP-strømmen for å se hele forespørsler og responser
```

### Eksempel 2: Finne påloggingsopplysninger
```bash
# Visningsfilter
http.request.method == "POST" and http contains "password"

# Eller for FTP
ftp contains "PASS"
```

### Eksempel 3: Oppdage portskanning
```bash
# Flere SYN-pakker til forskjellige porter
tcp.flags.syn == 1 and tcp.flags.ack == 0

# Se etter mange mål fra samme kilde
Statistics → Conversations → TCP
```

### Eksempel 4: Eksfiltrering via DNS
```bash
# Store DNS-spørringer
dns and frame.len > 512

# Uvanlige TXT-oppføringer
dns.qry.type == 16
```

### Eksempel 5: Hente ut filer
```
File → Export Objects → HTTP

Velg filer som skal eksporteres
```

### Eksempel 6: Analysere TLS-håndtrykket
```bash
tls.handshake

# Se etter SNI i Client Hello
tls.handshake.type == 1
```

### Eksempel 7: Finne passord i klartekst
```bash
http contains "password" or http contains "passwd" or ftp or telnet
```

---

## 📊 Hurtigreferanse

### Viktige visningsfiltre

| Formål | Filter |
|---------|--------|
| HTTP-trafikk | `http` |
| DNS-trafikk | `dns` |
| Bestemt IP-adresse | `ip.addr == 192.168.1.1` |
| Bestemt port | `tcp.port == 80` |
| TCP-problemer | `tcp.analysis.flags` |
| HTTP-feil | `http.response.code >= 400` |
| Inneholder en streng | `frame contains "text"` |
| Utelat protokoll | `not arp` |

### Vanlige opptaksfiltre

| Formål | Filter |
|---------|--------|
| Bestemt vert | `host 192.168.1.1` |
| Webtrafikk | `port 80 or port 443` |
| Bare DNS | `port 53` |
| Ikke kringkasting | `not broadcast` |

---

<a id="-tastatursnarveier"></a>
## ⌨️ Tastatursnarveier

| Snarvei | Handling |
|----------|--------|
| `Ctrl+E` | Start/stopp opptak |
| `Ctrl+K` | Opptaksinnstillinger |
| `Ctrl+O` | Åpne fil |
| `Ctrl+S` | Lagre fil |
| `Ctrl+F` | Finn pakke |
| `Ctrl+G` | Gå til pakke |
| `Ctrl+→` | Neste pakke i samtalen |
| `Ctrl+←` | Forrige pakke i samtalen |
| `Ctrl+.` | Neste merkede pakke |
| `Ctrl+M` | Merk/fjern merking av pakke |
| `Ctrl+Shift+M` | Merk alle viste pakker |
| `Space` | Veksle protokolltreet |
| `Tab` | Neste rute |

---

## 💡 Tips og anbefalte arbeidsmåter

### Tips for opptak

1. **Bruk opptaksfiltre**
   ```
   Reduser filstørrelsen ved å filtrere under opptaket
   ```

2. **Ringbuffer for lange opptak**
   ```
   Capture → Options → Output → Ring buffer
   ```

3. **Ta opp eksternt**
   ```bash
   ssh user@host tcpdump -w - | wireshark -k -i -
   ```

### Tips for analyse

1. **Fargeregler**
   ```
   View → Coloring Rules
   Tilpass farger for protokollene
   ```

2. **Tidsvisning**
   ```
   View → Time Display Format
   Prøv "Seconds Since Beginning of Capture"
   ```

3. **Navneoppslag**
   ```
   View → Name Resolution
   Aktiver eller deaktiver etter behov
   ```

---

## ⚠️ Juridisk ansvarsfraskrivelse

> **ADVARSEL:** Opptak av nettverkspakker skal bare brukes til **autoriserte formål**.
> 
> - ✅ Ta opp på nettverk du eier
> - ✅ Sikkerhetstesting med tillatelse
> - ✅ Feilsøking på systemer du har tillatelse til å undersøke
> - ❌ Ta aldri opp på nettverk uten tillatelse
> - ❌ Opptak av andres trafikk kan være ulovlig
> 
> **Uautorisert nettverksovervåking er ulovlig i de fleste jurisdiksjoner.**

---

## 📚 Ressurser

### Offisielle ressurser
- [Wiresharks offisielle nettsted](https://www.wireshark.org/)
- [Wiresharks brukerveiledning](https://www.wireshark.org/docs/wsug_html/)
- [Referanse for visningsfiltre](https://www.wireshark.org/docs/dfref/)
- [Wireshark Wiki](https://wiki.wireshark.org/)

### Læringsressurser
- [Wireshark-veiledning](https://www.wireshark.org/docs/wsug_html/)
- [Eksempelopptak](https://wiki.wireshark.org/SampleCaptures)

### Relaterte hurtigreferanser
- [Nmap](../../Nmap/translations/README.nb.md)
- [Metasploit](../../Metasploit/translations/README.nb.md)
- [Hydra](../../Hydra/translations/README.nb.md)

---

<p align="center">
  <b>🦈 Lær nettverksanalyse!</b><br>
  <i>Undersøk trafikken på nettverket</i>
</p>
