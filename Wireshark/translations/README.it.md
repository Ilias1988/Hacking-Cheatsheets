# 🦈 Wireshark - Scheda di riferimento completa

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
 __        ___           ____  _                _    
 \ \      / (_)_ __ ___ / ___|| |__   __ _ _ __| | __
  \ \ /\ / /| | '__/ _ \\___ \| '_ \ / _` | '__| |/ /
   \ V  V / | | | |  __/ ___) | | | | (_| | |  |   < 
    \_/\_/  |_|_|  \___|____/|_| |_|\__,_|_|  |_|\_\
                                                     
           L'analizzatore dei protocolli di rete
```

<p align="center">
  <img src="https://img.shields.io/badge/Wireshark-Network_Analyzer-blue?style=for-the-badge" alt="Wireshark">
  <img src="https://img.shields.io/badge/Packet-Capture-red?style=for-the-badge" alt="Packet Capture">
  <img src="https://img.shields.io/badge/Protocol-Analysis-green?style=for-the-badge" alt="Protocol">
  <img src="https://img.shields.io/badge/Open-Source-orange?style=for-the-badge" alt="Open Source">
</p>

<p align="center">
  <b>🌐 L'analizzatore di protocolli di rete più conosciuto al mondo</b>
</p>

---

## 📋 Indice

- [Introduzione](#-introduzione)
- [Installazione](#-installazione)
- [Panoramica dell'interfaccia](#-panoramica-dellinterfaccia)
- [Fondamenti dell'acquisizione](#-fondamenti-dellacquisizione)
- [Filtri di visualizzazione](#-filtri-di-visualizzazione)
- [Filtri di acquisizione](#-filtri-di-acquisizione)
- [Analisi dei protocolli](#-analisi-dei-protocolli)
- [Seguire i flussi](#-seguire-i-flussi)
- [Statistiche e analisi](#-statistiche-e-analisi)
- [tshark (interfaccia a riga di comando)](#-tshark-interfaccia-a-riga-di-comando)
- [Esempi pratici](#-esempi-pratici)
- [Riferimento rapido](#-riferimento-rapido)
- [Scorciatoie da tastiera](#-scorciatoie-da-tastiera)
- [Consigli e buone pratiche](#-consigli-e-buone-pratiche)
- [Risorse](#-risorse)

---

> **Nota sui filtri:** i blocchi etichettati `bash` contengono anche filtri Wireshark o BPF e percorsi dei menu, non soltanto comandi della shell. Le etichette dell’interfaccia restano in inglese. Un filtro seleziona i pacchetti e non decifra HTTPS; anche la porta 443 non identifica da sola tutto il traffico HTTPS. La semantica di `!=` per campi con più valori è cambiata in Wireshark 3.6: vedi la [guida ufficiale ai filtri](https://www.wireshark.org/docs/wsug_html_chunked/ChWorkBuildDisplayFilterSection.html).

## 🎯 Introduzione

**Wireshark** è un analizzatore di pacchetti gratuito e open source, usato per diagnosticare problemi di rete, analizzare protocolli, sviluppare software e svolgere verifiche di sicurezza.

### Funzionalità principali

| Funzionalità | Descrizione |
| --------- | ------------- |
| **Ispezione approfondita** | Centinaia di protocolli supportati |
| **Acquisizione in tempo reale** | Acquisizione dei pacchetti in tempo reale |
| **Analisi offline** | Lettura dei file acquisiti (pcap, pcapng) |
| **Filtri potenti** | Filtri di visualizzazione e acquisizione |
| **Multipiattaforma** | Windows, Linux, macOS |
| **GUI e CLI** | Wireshark (GUI) e tshark (CLI) |

### Casi d'uso

| Caso d'uso | Descrizione |
| ---------- | ------------- |
| **Diagnosi dei problemi di rete** | Individuare i problemi di connettività |
| **Analisi della sicurezza** | Rilevare traffico malevolo |
| **Sviluppo di protocolli** | Verificare nuovi protocolli |
| **Analisi forense** | Analizzare il traffico acquisito |
| **Formazione** | Imparare il funzionamento delle reti |

---

## 📥 Installazione

### Windows
```bash
# Scarica dal sito ufficiale
https://www.wireshark.org/download.html

# Oppure usa Chocolatey
choco install wireshark
```

### Linux (Kali/Ubuntu)
```bash
# Kali (preinstallato)
wireshark

# Ubuntu/Debian
sudo apt install wireshark

# Consenti l'acquisizione senza privilegi root
sudo usermod -aG wireshark $USER
# Esci dalla sessione ed effettua nuovamente l'accesso
```

### macOS
```bash
brew install --cask wireshark
```

### Verifica dell'installazione
```bash
wireshark --version
tshark --version
```

---

## 🖥 Panoramica dell'interfaccia

### Aree della finestra principale

| Area | Descrizione |
| ------ | ------------- |
| **Barra dei menu** | File, Edit, View, Go, Capture, Analyze |
| **Barra degli strumenti principale** | Pulsanti di accesso rapido |
| **Barra dei filtri** | Inserire i filtri di visualizzazione |
| **Elenco dei pacchetti** | Tutti i pacchetti acquisiti |
| **Dettagli del pacchetto** | Livelli di protocollo del pacchetto selezionato |
| **Byte del pacchetto** | Dati grezzi in esadecimale e ASCII |

### Colonne dell'elenco dei pacchetti (predefinite)

| Colonna | Descrizione |
| -------- | ------------- |
| No. | Numero del pacchetto |
| Time | Marca temporale |
| Source | IP/MAC di origine |
| Destination | IP/MAC di destinazione |
| Protocol | Protocollo del livello più alto |
| Length | Dimensione del pacchetto in byte |
| Info | Informazioni specifiche del protocollo |

---

## 📡 Fondamenti dell'acquisizione

### Avviare l'acquisizione

```
1. Seleziona l'interfaccia nella schermata iniziale
2. Fai clic su "Start Capture" (la pinna di squalo blu)
3. Oppure: Capture → Start

Scorciatoia: fai doppio clic sul nome dell'interfaccia
```

### Interrompere l'acquisizione

```
Fai clic sul quadrato rosso nella barra degli strumenti
Oppure: Capture → Stop
Oppure: Ctrl+E
```

### Salvare l'acquisizione

```
File → Save As
Formati: pcapng (predefinito), pcap e altri.
```

### Aprire un file di acquisizione

```
File → Open
Oppure: Ctrl+O
Oppure: wireshark file.pcap
```

---

## 🔍 Filtri di visualizzazione

### Sintassi dei filtri

I filtri di visualizzazione usano una **sintassi di confronto dei campi**:

```
field operator value
```

### Operatori di confronto

| Operatore | Descrizione | Esempio |
| ---------- | ------------- | --------- |
| `==` or `eq` | Uguale | `ip.addr == 192.168.1.1` |
| `!=` or `ne` | Diverso | `ip.addr != 10.0.0.1` |
| `>` or `gt` | Maggiore di | `frame.len > 100` |
| `<` or `lt` | Minore di | `tcp.port < 1024` |
| `>=` or `ge` | Maggiore o uguale | `frame.len >= 64` |
| `<=` or `le` | Minore o uguale | `tcp.port <= 1024` |
| `contains` | Contiene una stringa | `http contains "password"` |
| `matches` | Corrispondenza con espressione regolare | `http.host matches ".*\.com"` |

### Operatori logici

| Operatore | Descrizione | Esempio |
| ---------- | ------------- | --------- |
| `and` or `&&` | AND logico | `ip.src == 192.168.1.1 and tcp.port == 80` |
| `or` or `\|\|` | OR logico | `tcp.port == 80 or tcp.port == 443` |
| `not` or `!` | NOT logico | `not arp` |
| `()` | Raggruppamento | `(ip.src == 10.0.0.1) and (tcp or udp)` |

### Filtri IP

```bash
# Indirizzo IP specifico (origine o destinazione)
ip.addr == 192.168.1.1

# Solo IP di origine
ip.src == 192.168.1.1

# Solo IP di destinazione
ip.dst == 192.168.1.1

# Intervallo IP (sottorete)
ip.addr == 192.168.1.0/24

# Escludi un IP
ip.addr != 192.168.1.1

# Tra due host
ip.addr == 192.168.1.1 and ip.addr == 192.168.1.2
```

### Filtri delle porte

```bash
# Porta specifica (origine o destinazione)
tcp.port == 80
udp.port == 53

# Solo porta di origine
tcp.srcport == 443

# Solo porta di destinazione
tcp.dstport == 22

# Intervallo di porte
tcp.port >= 1 and tcp.port <= 1024

# Più porte
tcp.port == 80 or tcp.port == 443 or tcp.port == 8080
tcp.port in {80 443 8080}
```

### Filtri dei protocolli

```bash
# Per nome del protocollo
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

# Specifici di HTTP
http.request
http.response
http.request.method == "GET"
http.request.method == "POST"
http.response.code == 200
http.response.code >= 400

# Specifici di DNS
dns.qry.name == "google.com"
dns.qry.type == 1          # Record A
dns.flags.response == 1    # Risposte DNS

# Specifici di TCP
tcp.flags.syn == 1         # Pacchetti SYN
tcp.flags.fin == 1         # Pacchetti FIN
tcp.flags.reset == 1       # Pacchetti RST
tcp.analysis.retransmission  # Ritrasmissioni
tcp.stream == 5            # Flusso specifico
```

### Filtri del contenuto

```bash
# Contiene una stringa
http contains "password"
tcp contains "login"
frame contains "secret"

# Contenuto HTTP
http.request.uri contains "admin"
http.host == "example.com"
http.user_agent contains "Mozilla"
http.cookie contains "session"

# Corrispondenza senza distinzione tra maiuscole e minuscole
http.host matches "(?i)example"
```

### Esempi di filtri comuni

```bash
# Solo traffico HTTP
http

# Traffico HTTPS
tls

# Query DNS
dns.flags.response == 0

# Connessioni non riuscite (RST)
tcp.flags.reset == 1

# Pacchetti di grandi dimensioni
frame.len > 1000

# Indirizzo MAC specifico
eth.addr == aa:bb:cc:dd:ee:ff

# Traffico broadcast
eth.dst == ff:ff:ff:ff:ff:ff

# Traffico non locale
!(ip.addr == 192.168.0.0/16)

# Handshake TCP a tre passaggi
tcp.flags.syn == 1 or tcp.flags.ack == 1

# Richieste HTTP POST con dati
http.request.method == "POST" and http.file_data
```

---

## 🎣 Filtri di acquisizione

### Sintassi BPF (Berkeley Packet Filter)

I filtri di acquisizione usano la **sintassi BPF**, diversa da quella dei filtri di visualizzazione.

### Sintassi di base

```bash
# Host
host 192.168.1.1
src host 192.168.1.1
dst host 192.168.1.1

# Rete
net 192.168.1.0/24
src net 10.0.0.0/8

# Porta
port 80
src port 443
dst port 22
portrange 1-1024

# Protocollo
tcp
udp
icmp
arp
```

### Filtri combinati

```bash
# Host e porta
host 192.168.1.1 and port 80

# Conversazione specifica
host 192.168.1.1 and host 192.168.1.2

# Escludi traffico
not port 22
not host 10.0.0.1

# Filtro complesso
tcp and (port 80 or port 443) and host 192.168.1.1
```

### Filtri di acquisizione comuni

| Scopo | Filtro |
| --------- | -------- |
| Host specifico | `host 192.168.1.1` |
| Traffico web | `port 80 or port 443` |
| Solo DNS | `port 53` |
| Solo SSH | `port 22` |
| Esclusione del broadcast | `not broadcast` |
| Sottorete specifica | `net 192.168.1.0/24` |

---

## 🔬 Analisi dei protocolli

### Analisi TCP

```bash
# Flag TCP
tcp.flags.syn == 1 and tcp.flags.ack == 0    # Solo SYN
tcp.flags.syn == 1 and tcp.flags.ack == 1    # SYN-ACK
tcp.flags.fin == 1                            # FIN
tcp.flags.reset == 1                          # RST

# Problemi TCP
tcp.analysis.retransmission
tcp.analysis.duplicate_ack
tcp.analysis.zero_window
tcp.analysis.lost_segment

# Flusso TCP
tcp.stream == 0
```

### Analisi HTTP

```bash
# Richieste HTTP
http.request
http.request.method == "GET"
http.request.method == "POST"
http.request.uri contains "/login"

# Risposte HTTP
http.response
http.response.code == 200
http.response.code == 404
http.response.code >= 400    # Errori

# Contenuto HTTP
http.content_type contains "json"
http.content_type contains "html"
http.file_data contains "password"
```

### Analisi DNS

```bash
# Query DNS
dns.flags.response == 0
dns.qry.name == "example.com"
dns.qry.type == 1      # Record A
dns.qry.type == 28     # Record AAAA
dns.qry.type == 5      # CNAME
dns.qry.type == 15     # MX

# Risposte DNS
dns.flags.response == 1
dns.flags.rcode != 0   # Errori
```

> **Nota TLS:** `tls.record.version == 0x0303` non prova che la sessione usi TLS 1.2: TLS 1.3 conserva questo valore nel campo legacy del record ([RFC 8446, sezione 5.1](https://www.rfc-editor.org/rfc/rfc8446.html#section-5.1)). Il filtro con SYN oppure ACK dell’esempio di handshake include anche molti pacchetti successivi, non soltanto i tre passaggi iniziali.

### Analisi TLS/SSL

```bash
# Traffico TLS
tls

# Handshake TLS
tls.handshake
tls.handshake.type == 1     # Client Hello
tls.handshake.type == 2     # Server Hello

# Certificati
tls.handshake.certificate

# Versione TLS
tls.record.version == 0x0303  # TLS 1.2
```

### Analisi ARP

```bash
# Tutto il traffico ARP
arp

# Richieste ARP
arp.opcode == 1

# Risposte ARP
arp.opcode == 2

# ARP gratuito (possibile spoofing, falsificazione dell'identità)
arp.isgratuitous == 1
```

---

## 🔗 Seguire i flussi

### Seguire un flusso TCP

```
Fai clic con il tasto destro sul pacchetto → Follow → TCP Stream

Oppure: Analyze → Follow → TCP Stream
```

### Seguire un flusso HTTP

```
Fai clic con il tasto destro sul pacchetto HTTP → Follow → HTTP Stream
```

### Seguire un flusso UDP

```
Fai clic con il tasto destro sul pacchetto UDP → Follow → UDP Stream
```

### Finestra del flusso

- Mostra l'intera conversazione
- Colori distinti per client e server
- Salva come testo o dati grezzi
- Pulsanti per filtrare in base alla direzione

---

## 📊 Statistiche e analisi

### Gerarchia dei protocolli

```
Statistics → Protocol Hierarchy

Mostra la ripartizione dei protocolli acquisiti
```

### Conversazioni

```
Statistics → Conversations

Visualizza tutte le conversazioni per:
- Ethernet
- IPv4/IPv6
- TCP
- UDP
```

### Endpoint

```
Statistics → Endpoints

Elenca tutti gli endpoint distinti
```

### Grafico I/O

```
Statistics → IO Graph

Visualizza il traffico nel tempo
```

### Informazioni diagnostiche (Expert Information)

```
Analyze → Expert Information

Mostra avvisi, errori e note:
- Ritrasmissioni TCP
- Pacchetti malformati
- Errori
```

### Statistiche HTTP

```
Statistics → HTTP → Requests
Statistics → HTTP → Packet Counter
```

---

## 💻 tshark (interfaccia a riga di comando)

### Utilizzo di base

```bash
# Acquisisci pacchetti
tshark -i eth0

# Leggi un file di acquisizione
tshark -r capture.pcap

# Scrivere su file
tshark -i eth0 -w output.pcap
```

### Applicare i filtri

```bash
# Filtro di acquisizione
tshark -i eth0 -f "port 80"

# Filtro di visualizzazione
tshark -r capture.pcap -Y "http"
```

### Opzioni di output

```bash
# Mostra campi specifici
tshark -r capture.pcap -T fields -e ip.src -e ip.dst -e tcp.port

# Output JSON
tshark -r capture.pcap -T json

# Mostra statistiche
tshark -r capture.pcap -q -z io,stat,1
```

### Esempi comuni di tshark

```bash
# Acquisisci le richieste HTTP in tempo reale
tshark -i eth0 -Y "http.request" -T fields -e http.host -e http.request.uri

# Estrai le query DNS
tshark -r capture.pcap -Y "dns.flags.response == 0" -T fields -e dns.qry.name

# Conta i pacchetti per IP
tshark -r capture.pcap -q -z ip_hosts,tree

# Statistiche HTTP
tshark -r capture.pcap -q -z http,tree
```

---

## 🎬 Esempi pratici

### Esempio 1: acquisire il traffico HTTP
```bash
# Filtro di acquisizione: port 80
# Filtro di visualizzazione: http
# Segui il flusso HTTP per vedere richieste e risposte complete
```

### Esempio 2: individuare credenziali
```bash
# Filtro di visualizzazione
http.request.method == "POST" and http contains "password"

# Oppure per FTP
ftp contains "PASS"
```

### Esempio 3: rilevare una scansione delle porte
```bash
# Più SYN verso porte diverse
tcp.flags.syn == 1 and tcp.flags.ack == 0

# Cerca molte destinazioni da un'unica origine
Statistics → Conversations → TCP
```

### Esempio 4: esfiltrazione tramite DNS
```bash
# Query DNS di grandi dimensioni
dns and frame.len > 512

# Record TXT insoliti
dns.qry.type == 16
```

### Esempio 5: estrarre file
```
File → Export Objects → HTTP

Seleziona i file da esportare
```

### Esempio 6: analizzare l'handshake TLS
```bash
tls.handshake

# Esamina il Client Hello per SNI
tls.handshake.type == 1
```

### Esempio 7: individuare password in chiaro
```bash
http contains "password" or http contains "passwd" or ftp or telnet
```

---

## 📊 Riferimento rapido

### Filtri di visualizzazione essenziali

| Scopo | Filtro |
| --------- | -------- |
| Traffico HTTP | `http` |
| Traffico DNS | `dns` |
| IP specifico | `ip.addr == 192.168.1.1` |
| Porta specifica | `tcp.port == 80` |
| Problemi TCP | `tcp.analysis.flags` |
| Errori HTTP | `http.response.code >= 400` |
| Contiene una stringa | `frame contains "text"` |
| Esclusione di un protocollo | `not arp` |

### Filtri di acquisizione comuni

| Scopo | Filtro |
| --------- | -------- |
| Host specifico | `host 192.168.1.1` |
| Traffico web | `port 80 or port 443` |
| Solo DNS | `port 53` |
| Esclusione del broadcast | `not broadcast` |

---

## ⌨ Scorciatoie da tastiera

| Scorciatoia | Azione |
| ---------- | -------- |
| `Ctrl+E` | Avviare/interrompere l'acquisizione |
| `Ctrl+K` | Opzioni di acquisizione |
| `Ctrl+O` | Aprire un file |
| `Ctrl+S` | Salvare un file |
| `Ctrl+F` | Cercare un pacchetto |
| `Ctrl+G` | Passare a un pacchetto |
| `Ctrl+→` | Pacchetto successivo nella conversazione |
| `Ctrl+←` | Pacchetto precedente nella conversazione |
| `Ctrl+.` | Pacchetto contrassegnato successivo |
| `Ctrl+M` | Contrassegnare/rimuovere il contrassegno dal pacchetto |
| `Ctrl+Shift+M` | Contrassegnare tutti i pacchetti visualizzati |
| `Space` | Espandere/comprimere l'albero dei protocolli |
| `Tab` | Riquadro successivo |

---

## 💡 Consigli e buone pratiche

### Consigli per l'acquisizione

1. **Usa filtri di acquisizione**
   ```
   Riduci la dimensione del file filtrando durante l'acquisizione
   ```

2. **Buffer circolare (ring buffer) per acquisizioni prolungate**
   ```
   Capture → Options → Output → Ring buffer
   ```

3. **Acquisisci da remoto**
   ```bash
   ssh user@host tcpdump -w - | wireshark -k -i -
   ```

### Consigli per l'analisi

1. **Regole di colorazione**
   ```
   View → Coloring Rules
   Personalizza i colori dei protocolli
   ```

2. **Visualizzazione dell'ora**
   ```
   View → Time Display Format
   Prova "Seconds Since Beginning of Capture" (secondi dall'inizio dell'acquisizione)
   ```

3. **Risoluzione dei nomi**
   ```
   View → Name Resolution
   Abilita o disabilita secondo le necessità
   ```

---

## ⚠ Avvertenze legali

> **ATTENZIONE:** l'acquisizione di pacchetti di rete va usata soltanto per **finalità autorizzate**.
> 
> - ✅ Acquisisci su reti di tua proprietà
> - ✅ Esegui test di sicurezza con autorizzazione
> - ✅ Diagnostica i problemi su sistemi per cui hai autorizzazione
> - ❌ Non acquisire mai su reti senza autorizzazione
> - ❌ Acquisire il traffico altrui può essere illegale
> 
> **Il monitoraggio non autorizzato della rete è illegale nella maggior parte delle giurisdizioni.**

---

## 📚 Risorse

### Risorse ufficiali
- [Sito ufficiale di Wireshark](https://www.wireshark.org/)
- [Guida utente di Wireshark](https://www.wireshark.org/docs/wsug_html/)
- [Riferimento dei filtri di visualizzazione](https://www.wireshark.org/docs/dfref/)
- [Wiki di Wireshark](https://wiki.wireshark.org/)

### Risorse per lo studio
- [Tutorial di Wireshark](https://www.wireshark.org/docs/wsug_html/)
- [Acquisizioni di esempio](https://wiki.wireshark.org/SampleCaptures)

### Schede di riferimento correlate
- [Nmap](../../Nmap/translations/README.it.md)
- [Metasploit](../../Metasploit/translations/README.it.md)
- [Hydra](../../Hydra/translations/README.it.md)

---

<p align="center">
  <b>🦈 Padroneggia l'analisi di rete!</b><br>
  <i>Osserva tutto ciò che passa sulla rete</i>
</p>
