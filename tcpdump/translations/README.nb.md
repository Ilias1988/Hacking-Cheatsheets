# 📡 tcpdump – komplett hurtigreferanse

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _             _                       
 | |_ ___ _ __ | |_ _ _ _____ _ __ _ __ 
 | __/ __| '_ \| __| | | '_ ` _ \| '_ \
 | || (__| |_) | |_| |_| | | | | | |_) |
  \__\___| .__/ \__|\__,_|_| |_| | .__/ 
         |_|                     |_|    
      Pakkeanalyse fra kommandolinjen
```

<p align="center">
  <img src="https://img.shields.io/badge/tcpdump-Packet_Analyzer-blue?style=for-the-badge" alt="tcpdump">
  <img src="https://img.shields.io/badge/Command-Line-red?style=for-the-badge" alt="CLI">
  <img src="https://img.shields.io/badge/BPF-Filters-green?style=for-the-badge" alt="BPF">
  <img src="https://img.shields.io/badge/Cross-Platform-orange?style=for-the-badge" alt="Flere plattformer">
</p>

<p align="center">
  <b>🔍 Det klassiske kommandolinjeverktøyet for pakkeanalyse</b>
</p>

---

## 📋 Innholdsfortegnelse

- [Innledning](#-innledning)
- [Installasjon](#-installasjon)
- [Grunnleggende syntaks](#-grunnleggende-syntaks)
- [Vanlige alternativer](#-vanlige-alternativer)
- [BPF-filtre](#-bpf-filtre)
- [Protokollfiltre](#-protokollfiltre)
- [Utdatavalg](#-utdatavalg)
- [Praktiske eksempler](#-praktiske-eksempler)
- [Hurtigreferanse](#-hurtigreferanse)
- [Tips og anbefalte arbeidsmåter](#-tips-og-anbefalte-arbeidsmåter)
- [Ressurser](#-ressurser)

---

## 🎯 Innledning

> **Begreper og presisering:** Et grensesnitt (interface) er opptakspunktet; BPF avgrenser pakker, og snaplen bestemmer hvor mange byte som beholdes. `-s0` velger versjonens standardgrense, ikke en ubegrenset størrelse; 96 byte garanterer heller ikke bare headere. `-w` skriver rå pakkedata til fil og viser normalt ikke pakkebeskrivelser via `tee`. `-Z` slipper rettigheter etter at opptaksenheten er åpnet, ikke først etter at opptaket er ferdig. Se [prosjektets manual](https://raw.githubusercontent.com/the-tcpdump-group/tcpdump/master/tcpdump.1.in). I eksemplet med innkommende trafikk hindrer enkle anførselstegn shell-utvidelsen av `$(...)`; linjen er derfor ikke et fungerende automatisk IP-oppslag. HTTP-bytefiltre er forenklede og protokollavhengige; TLS-trafikk blir ikke automatisk klartekst. Eksemplene er bevart.

**tcpdump** er et kraftig kommandolinjeverktøy for pakkeanalyse som finnes på de fleste Unix-lignende operativsystemer. Det bruker libpcap-biblioteket og syntaksen Berkeley Packet Filter (BPF).

### Viktige egenskaper

| Egenskap | Beskrivelse |
|---------|-------------|
| **Lettvektsverktøy** | Uten grafisk grensesnitt, lite ressursbruk |
| **BPF-filtre** | Kraftig pakkefiltrering |
| **pcap-kompatibelt** | Les og skriv pcap-filer |
| **Egnet for skripting** | Godt egnet for automatisering |
| **Utbredt** | Tilgjengelig på Unix-systemer |

### tcpdump sammenlignet med Wireshark

| Egenskap | tcpdump | Wireshark |
|---------|---------|-----------|
| **Grensesnitt** | CLI | GUI |
| **Ressurser** | Lite | Høyere |
| **Ekstern bruk** | Direkte via SSH | Krever oppsett |
| **Skripting** | Enkelt | Mer sammensatt |
| **Analyse** | Grunnleggende | Avansert |

---

## 📥 Installasjon

### Linux (forhåndsinstallert på mange systemer)
```bash
# Kali/Debian/Ubuntu
sudo apt install tcpdump

# CentOS/RHEL
sudo yum install tcpdump

# Arch
sudo pacman -S tcpdump
```

### macOS (forhåndsinstallert)
```bash
tcpdump --version
```

### Kontrollere installasjonen
```bash
tcpdump --version
tcpdump -D              # List grensesnitt
```

---

<a id="-grunnleggende-syntaks"></a>
## ⌨️ Grunnleggende syntaks

### Generell syntaks
```bash
tcpdump [options] [filter expression]
```

### Grunnleggende kommandoer
```bash
# Ta opp på standardgrensesnittet
sudo tcpdump

# Ta opp på et bestemt grensesnitt
sudo tcpdump -i eth0

# Ta opp et begrenset antall pakker
sudo tcpdump -c 100

# Ta opp med detaljerte utdata
sudo tcpdump -v
sudo tcpdump -vv
sudo tcpdump -vvv
```

---

<a id="-vanlige-alternativer"></a>
## ⚙️ Vanlige alternativer

### Visningsalternativer

| Alternativ | Beskrivelse |
|--------|-------------|
| `-i` | Grensesnittet opptaket skal gjøres på |
| `-c` | Antall pakker som skal tas opp |
| `-n` | Ikke slå opp vertsnavn |
| `-nn` | Ikke slå opp vertsnavn eller portnavn |
| `-v` | Detaljerte utdata |
| `-vv` | Mer detaljerte utdata |
| `-vvv` | Maksimalt detaljnivå |
| `-q` | Stille modus / færre utdata |
| `-t` | Ikke skriv ut tidsstempel |
| `-tt` | Skriv ut Unix-tidsstempel |
| `-ttt` | Skriv ut delta (tidsforskjell) |
| `-tttt` | Skriv ut dato og klokkeslett |

### Opptaksalternativer

| Alternativ | Beskrivelse |
|--------|-------------|
| `-w` | Skriv til pcap-fil |
| `-r` | Les fra pcap-fil |
| `-s` | Antall byte som tas med fra hver pakke |
| `-s0` | Ta opp hele pakker |
| `-D` | List tilgjengelige grensesnitt |

### Utdatavalg

| Alternativ | Beskrivelse |
|--------|-------------|
| `-A` | Skriv ut ASCII |
| `-X` | Skriv ut hex og ASCII |
| `-XX` | Skriv ut hex og ASCII med headere |
| `-e` | Skriv ut header på lenkelaget |
| `-l` | Linjebufrede utdata |

### Vanlige kombinasjoner
```bash
# Uten DNS-oppslag, detaljert, hele pakker
sudo tcpdump -nnvvs0 -i eth0

# Lagre til fil
sudo tcpdump -i eth0 -w capture.pcap

# Les fra fil
tcpdump -r capture.pcap

# Utdata i ASCII
sudo tcpdump -A -i eth0

# Hex og ASCII
sudo tcpdump -X -i eth0
```

---

## 🎣 BPF-filtre

### Vertsfiltre

```bash
# Trafikk til/fra en bestemt vert
tcpdump host 192.168.1.1

# Bare kildevert
tcpdump src host 192.168.1.1

# Bare målvert
tcpdump dst host 192.168.1.1

# Flere verter
tcpdump host 192.168.1.1 or host 192.168.1.2

# Utelat vert
tcpdump not host 192.168.1.1

# Nettverk/undernett
tcpdump net 192.168.1.0/24
tcpdump src net 10.0.0.0/8
```

### Portfiltre

```bash
# Bestemt port
tcpdump port 80
tcpdump port 443

# Kildeport
tcpdump src port 443

# Målport
tcpdump dst port 22

# Portområde
tcpdump portrange 1-1024

# Flere porter
tcpdump port 80 or port 443
tcpdump port 80 or port 443 or port 8080
```

### Protokollfiltre

```bash
# Etter protokoll
tcpdump tcp
tcpdump udp
tcpdump icmp
tcpdump arp

# Protokoll og port
tcpdump tcp port 80
tcpdump udp port 53
```

### Logiske operatorer

| Operator | Alternativ | Beskrivelse |
|----------|-------------|-------------|
| `and` | `&&` | Logisk OG |
| `or` | `\|\|` | Logisk ELLER |
| `not` | `!` | Logisk IKKE |
| `()` |  | Gruppering |

### Kombinerte filtre

```bash
# Vert OG port
tcpdump host 192.168.1.1 and port 80

# Vert OG protokoll
tcpdump host 192.168.1.1 and tcp

# Sammensatt filter
tcpdump 'host 192.168.1.1 and (port 80 or port 443)'

# Utelat bestemt trafikk
tcpdump not port 22 and not port 53

# Trafikk mellom to verter
tcpdump host 192.168.1.1 and host 192.168.1.2
```

---

## 📡 Protokollfiltre

### TCP-flagg

```bash
# SYN-pakker
tcpdump 'tcp[tcpflags] & tcp-syn != 0'
tcpdump 'tcp[13] & 2 != 0'

# SYN-ACK-pakker
tcpdump 'tcp[tcpflags] & (tcp-syn|tcp-ack) == (tcp-syn|tcp-ack)'

# FIN-pakker
tcpdump 'tcp[tcpflags] & tcp-fin != 0'

# RST-pakker
tcpdump 'tcp[tcpflags] & tcp-rst != 0'

# PSH-pakker
tcpdump 'tcp[tcpflags] & tcp-push != 0'

# Bare ACK
tcpdump 'tcp[tcpflags] == tcp-ack'
```

### Referanse for TCP-flagg

| Flagg | Hex | Dec | tcpdump |
|------|-----|-----|---------|
| FIN | 0x01 | 1 | tcp-fin |
| SYN | 0x02 | 2 | tcp-syn |
| RST | 0x04 | 4 | tcp-rst |
| PSH | 0x08 | 8 | tcp-push |
| ACK | 0x10 | 16 | tcp-ack |
| URG | 0x20 | 32 | tcp-urg |

### HTTP-trafikk

```bash
# HTTP-forespørsler (port 80)
tcpdump -A 'tcp port 80 and (((ip[2:2] - ((ip[0]&0xf)<<2)) - ((tcp[12]&0xf0)>>2)) != 0)'

# GET-forespørsler
tcpdump -A 'tcp port 80 and tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x47455420'

# POST-forespørsler
tcpdump -A 'tcp port 80 and tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x504f5354'

# Enkelt HTTP-opptak
tcpdump -A -s0 'tcp port 80'
```

### DNS-trafikk

```bash
# All DNS-trafikk
tcpdump port 53
tcpdump udp port 53

# Bare DNS-spørringer
tcpdump 'udp port 53 and udp[10] & 0x80 = 0'

# Bare DNS-responser
tcpdump 'udp port 53 and udp[10] & 0x80 = 0x80'
```

### ICMP-trafikk

```bash
# All ICMP-trafikk
tcpdump icmp

# ICMP echo request (ping)
tcpdump 'icmp[icmptype] == icmp-echo'

# ICMP echo reply
tcpdump 'icmp[icmptype] == icmp-echoreply'

# ICMP unreachable
tcpdump 'icmp[icmptype] == icmp-unreach'
```

### ARP-trafikk

```bash
# All ARP-trafikk
tcpdump arp

# ARP-forespørsler
tcpdump 'arp[6:2] == 1'

# ARP-svar
tcpdump 'arp[6:2] == 2'
```

---

## 💾 Utdatavalg

### Skrive til fil

```bash
# Skriv til pcap-fil
tcpdump -w capture.pcap

# Skriv med rotasjon (100 filer, 1MB hver)
tcpdump -w capture.pcap -W 100 -C 1

# Skriv med tidsstempel i filnavnet
tcpdump -w capture_%Y%m%d_%H%M%S.pcap -G 3600

# Ta opp og vis
tcpdump -w capture.pcap | tee
```

### Lese fra fil

```bash
# Les pcap-fil
tcpdump -r capture.pcap

# Les med filtre
tcpdump -r capture.pcap 'host 192.168.1.1'

# Tell pakker
tcpdump -r capture.pcap | wc -l

# Les med detaljerte utdata
tcpdump -nnvvXSs0 -r capture.pcap
```

### Visningsformater

```bash
# ASCII-innhold
tcpdump -A -i eth0

# Hex og ASCII
tcpdump -X -i eth0

# Bare hex
tcpdump -x -i eth0

# Med Ethernet-headere
tcpdump -XX -i eth0

# Skriv ut absolutte sekvensnumre
tcpdump -S -i eth0
```

---

## 🎬 Praktiske eksempler

### Eksempel 1: Ta opp webtrafikk
```bash
sudo tcpdump -i eth0 -nnvvXSs0 'tcp port 80 or tcp port 443'
```

### Eksempel 2: Ta opp DNS-spørringer
```bash
sudo tcpdump -i eth0 -nn 'udp port 53'
```

### Eksempel 3: Ta opp trafikk til en bestemt vert
```bash
sudo tcpdump -i eth0 -nn host 192.168.1.100
```

### Eksempel 4: Finne SSH-forbindelser
```bash
sudo tcpdump -i eth0 'tcp port 22'
```

### Eksempel 5: Ta opp ICMP (ping)
```bash
sudo tcpdump -i eth0 icmp
```

### Eksempel 6: Oppdage portskanning (SYN)
```bash
sudo tcpdump -i eth0 'tcp[tcpflags] == tcp-syn'
```

### Eksempel 7: Ta opp bare innkommende trafikk
```bash
sudo tcpdump -i eth0 'dst host $(hostname -I | cut -d" " -f1)'
```

### Eksempel 8: Overvåke en bestemt samtale
```bash
sudo tcpdump -i eth0 'host 192.168.1.1 and host 192.168.1.2'
```

### Eksempel 9: Ta opp FTP-påloggingsopplysninger
```bash
sudo tcpdump -A -i eth0 'tcp port 21'
```

### Eksempel 10: Lagre for analyse i Wireshark
```bash
sudo tcpdump -i eth0 -s0 -w capture.pcap
# Åpne deretter capture.pcap i Wireshark
```

### Eksempel 11: Eksternt opptak via SSH
```bash
ssh user@remote 'sudo tcpdump -w - -i eth0' | wireshark -k -i -
```

### Eksempel 12: Ta opp uten SSH
```bash
sudo tcpdump -i eth0 'not port 22'
```

---

## 📊 Hurtigreferanse

### Viktige kommandoer

| Oppgave | Kommando |
|------|---------|
| List grensesnitt | `tcpdump -D` |
| Grunnleggende opptak | `tcpdump -i eth0` |
| Ingen DNS-oppslag | `tcpdump -nn` |
| Ta opp N pakker | `tcpdump -c 100` |
| Skriv til fil | `tcpdump -w file.pcap` |
| Les fra fil | `tcpdump -r file.pcap` |
| Detaljerte utdata | `tcpdump -v` |
| Utdata i ASCII | `tcpdump -A` |
| Utdata i hex | `tcpdump -X` |
| Hele pakken | `tcpdump -s0` |

### Vanlige filtre

| Filter | Beskrivelse |
|--------|-------------|
| `host 1.2.3.4` | Trafikk til/fra vert |
| `port 80` | Trafikk på port 80 |
| `tcp` | Bare TCP-trafikk |
| `udp` | Bare UDP-trafikk |
| `icmp` | Bare ICMP-trafikk |
| `net 192.168.1.0/24` | Trafikk til/fra undernett |
| `src host 1.2.3.4` | Kilden er verten |
| `dst port 443` | Målport 443 |

### Filterkombinasjoner

| Eksempel | Beskrivelse |
|---------|-------------|
| `host A and port 80` | Vert A, port 80 |
| `port 80 or port 443` | Port 80 eller 443 |
| `not port 22` | Utelat SSH |
| `tcp and port 80` | TCP-port 80 |

---

## 💡 Tips og anbefalte arbeidsmåter

### Tips for ytelse

```bash
# Unngå DNS-oppslag (raskere)
tcpdump -nn

# Begrens størrelsen som tas med fra hver pakke
tcpdump -s 96   # Bare headere

# Bruk BPF-filtre for å redusere belastningen
tcpdump 'host 192.168.1.1'  # Filtrer i kjernen
```

### Sikkerhetstips

```bash
# Kjør uten root (hvis konfigurert)
# Eller begrens opptakstiden

# Slipp rettigheter etter opptaket (se presiseringen)
tcpdump -Z user
```

### Nyttige aliaser

```bash
# Legg til i ~/.bashrc
alias tcpdump='sudo tcpdump -nnvvXSs0'
alias tcpdump-web='sudo tcpdump -nnvvXSs0 port 80 or port 443'
alias tcpdump-dns='sudo tcpdump -nn port 53'
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
> **Uautorisert nettverksovervåking er ulovlig.**

---

## 📚 Ressurser

### Offisielle ressurser
- [tcpdump-manual](https://www.tcpdump.org/manpages/tcpdump.1.html)
- [tcpdumps offisielle nettsted](https://www.tcpdump.org/)
- [BPF-filtersyntaks](https://biot.com/capstats/bpf.html)

### Relaterte hurtigreferanser
- [Wireshark](../../Wireshark/translations/README.nb.md)
- [Nmap](../../Nmap/translations/README.nb.md)
- [Metasploit](../../Metasploit/translations/README.nb.md)

---

<p align="center">
  <b>📡 Lær pakkeanalyse fra kommandolinjen!</b><br>
  <i>Et lettvektsalternativ til Wireshark</i>
</p>
