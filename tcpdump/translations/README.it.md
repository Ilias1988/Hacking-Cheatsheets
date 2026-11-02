# 📡 tcpdump - Scheda di riferimento completa

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _             _                       
 | |_ ___ _ __ | |_ _ _ _____ _ __ _ __ 
 | __/ __| '_ \| __| | | '_ ` _ \| '_ \
 | || (__| |_) | |_| |_| | | | | | |_) |
  \__\___| .__/ \__|\__,_|_| |_| | .__/ 
         |_|                     |_|    
      Analizzatore di pacchetti a riga di comando
```

<p align="center">
  <img src="https://img.shields.io/badge/tcpdump-Packet_Analyzer-blue?style=for-the-badge" alt="tcpdump">
  <img src="https://img.shields.io/badge/Command-Line-red?style=for-the-badge" alt="CLI">
  <img src="https://img.shields.io/badge/BPF-Filters-green?style=for-the-badge" alt="BPF">
  <img src="https://img.shields.io/badge/Cross-Platform-orange?style=for-the-badge" alt="Cross Platform">
</p>

<p align="center">
  <b>🔍 Il classico analizzatore di pacchetti a riga di comando</b>
</p>

---

## 📋 Indice

- [Introduzione](#-introduzione)
- [Installazione](#-installazione)
- [Sintassi di base](#-sintassi-di-base)
- [Opzioni comuni](#-opzioni-comuni)
- [Filtri BPF](#-filtri-bpf)
- [Filtri dei protocolli](#-filtri-dei-protocolli)
- [Opzioni di output](#-opzioni-di-output)
- [Esempi pratici](#-esempi-pratici)
- [Riferimento rapido](#-riferimento-rapido)
- [Consigli e buone pratiche](#-consigli-e-buone-pratiche)
- [Risorse](#-risorse)

---

> **Nota sugli esempi originali:** `tcpdump -w capture.pcap | tee` salva i pacchetti nel file e non invia il loro riepilogo al pipe come suggerisce la didascalia. Nell’esempio 7, gli apici singoli impediscono alla shell di espandere `$(...)`: il filtro non funziona come descritto. `-Z` riduce i privilegi dopo l’apertura dell’interfaccia o del file di ingresso, non al termine dell’acquisizione; `-s 96` limita i byte e non garantisce di includere soltanto gli header. Il filtro dell’esempio 2 seleziona anche risposte DNS su UDP, non solo query. I comandi sono conservati per fedeltà all’originale: consulta il [manuale ufficiale di tcpdump](https://www.tcpdump.org/manpages/tcpdump.1.html).

## 🎯 Introduzione

**tcpdump** è un potente analizzatore di pacchetti a riga di comando, disponibile sulla maggior parte dei sistemi operativi di tipo Unix. Usa la libreria libpcap e la sintassi Berkeley Packet Filter (BPF).

### Funzionalità principali

| Funzionalità | Descrizione |
| --------- | ------------- |
| **Leggero** | Senza GUI, risorse minime |
| **Filtri BPF** | Filtraggio potente dei pacchetti |
| **Compatibile con pcap** | Lettura e scrittura di file pcap |
| **Utilizzabile negli script** | Adatto all'automazione |
| **Diffuso** | Disponibile sui sistemi Unix |

### tcpdump e Wireshark a confronto

| Funzionalità | tcpdump | Wireshark |
| --------- | --------- | ----------- |
| **Interfaccia** | CLI | GUI |
| **Risorse** | Minime | Maggiori |
| **Accesso remoto** | SSH nativo | Richiede configurazione |
| **Uso negli script** | Semplice | Complesso |
| **Analisi** | Di base | Avanzata |

---

## 📥 Installazione

### Linux (preinstallato su molte distribuzioni)
```bash
# Kali/Debian/Ubuntu
sudo apt install tcpdump

# CentOS/RHEL
sudo yum install tcpdump

# Arch
sudo pacman -S tcpdump
```

### macOS (preinstallato)
```bash
tcpdump --version
```

### Verifica dell'installazione
```bash
tcpdump --version
tcpdump -D              # Elencare le interfacce
```

---

## ⌨ Sintassi di base

### Sintassi generale
```bash
tcpdump [options] [filter expression]
```

### Comandi di base
```bash
# Acquisisci sull'interfaccia predefinita
sudo tcpdump

# Acquisisci su un'interfaccia specifica
sudo tcpdump -i eth0

# Acquisisci un numero limitato di pacchetti
sudo tcpdump -c 100

# Acquisisci con output dettagliato
sudo tcpdump -v
sudo tcpdump -vv
sudo tcpdump -vvv
```

---

## ⚙ Opzioni comuni

### Opzioni di visualizzazione

| Opzione | Descrizione |
| -------- | ------------- |
| `-i` | Interfaccia su cui acquisire |
| `-c` | Numero di pacchetti da acquisire |
| `-n` | Non risolvere i nomi host |
| `-nn` | Non risolvere i nomi host né i nomi dei servizi delle porte |
| `-v` | Output dettagliato |
| `-vv` | Output più dettagliato |
| `-vvv` | Massimo dettaglio |
| `-q` | Output ridotto |
| `-t` | Non stampare la marca temporale |
| `-tt` | Stampare la marca temporale Unix |
| `-ttt` | Stampare il delta (differenza temporale) |
| `-tttt` | Stampare data e ora |

### Opzioni di acquisizione

| Opzione | Descrizione |
| -------- | ------------- |
| `-w` | Scrivere in un file pcap |
| `-r` | Leggere da un file pcap |
| `-s` | Lunghezza massima acquisita per pacchetto (byte) |
| `-s0` | Acquisire i pacchetti completi |
| `-D` | Elencare le interfacce disponibili |

### Opzioni di output

| Opzione | Descrizione |
| -------- | ------------- |
| `-A` | Stampare in ASCII |
| `-X` | Stampare esadecimale e ASCII |
| `-XX` | Stampare esadecimale e ASCII con gli header |
| `-e` | Stampare l'header del livello di collegamento |
| `-l` | Output con buffering per riga |

### Combinazioni comuni
```bash
# Senza risoluzione DNS, output dettagliato e pacchetti completi
sudo tcpdump -nnvvs0 -i eth0

# Salva su file
sudo tcpdump -i eth0 -w capture.pcap

# Leggi da file
tcpdump -r capture.pcap

# Output ASCII
sudo tcpdump -A -i eth0

# Esadecimale e ASCII
sudo tcpdump -X -i eth0
```

---

## 🎣 Filtri BPF

### Filtri degli host

```bash
# Traffico da/verso un host specifico
tcpdump host 192.168.1.1

# Solo host di origine
tcpdump src host 192.168.1.1

# Solo host di destinazione
tcpdump dst host 192.168.1.1

# Più host
tcpdump host 192.168.1.1 or host 192.168.1.2

# Escludi un host
tcpdump not host 192.168.1.1

# Rete/sottorete
tcpdump net 192.168.1.0/24
tcpdump src net 10.0.0.0/8
```

### Filtri delle porte

```bash
# Porta specifica
tcpdump port 80
tcpdump port 443

# Porta di origine
tcpdump src port 443

# Porta di destinazione
tcpdump dst port 22

# Intervallo di porte
tcpdump portrange 1-1024

# Più porte
tcpdump port 80 or port 443
tcpdump port 80 or port 443 or port 8080
```

### Filtri dei protocolli

```bash
# Per protocollo
tcpdump tcp
tcpdump udp
tcpdump icmp
tcpdump arp

# Protocollo e porta
tcpdump tcp port 80
tcpdump udp port 53
```

### Operatori logici

| Operatore | Alternativa | Descrizione |
| ---------- | ------------- | ------------- |
| `and` | `&&` | AND logico |
| `or` | `\|\|` | OR logico |
| `not` | `!` | NOT logico |
| `()` | | Raggruppamento |

### Filtri combinati

```bash
# Host E porta
tcpdump host 192.168.1.1 and port 80

# Host E protocollo
tcpdump host 192.168.1.1 and tcp

# Filtro complesso
tcpdump 'host 192.168.1.1 and (port 80 or port 443)'

# Escludi traffico specifico
tcpdump not port 22 and not port 53

# Traffico tra due host
tcpdump host 192.168.1.1 and host 192.168.1.2
```

---

## 📡 Filtri dei protocolli

### Flag TCP

```bash
# Pacchetti SYN
tcpdump 'tcp[tcpflags] & tcp-syn != 0'
tcpdump 'tcp[13] & 2 != 0'

# Pacchetti SYN-ACK
tcpdump 'tcp[tcpflags] & (tcp-syn|tcp-ack) == (tcp-syn|tcp-ack)'

# Pacchetti FIN
tcpdump 'tcp[tcpflags] & tcp-fin != 0'

# Pacchetti RST
tcpdump 'tcp[tcpflags] & tcp-rst != 0'

# Pacchetti PSH
tcpdump 'tcp[tcpflags] & tcp-push != 0'

# Solo ACK
tcpdump 'tcp[tcpflags] == tcp-ack'
```

### Riferimento dei flag TCP

| Flag | Esadecimale | Decimale | tcpdump |
| ------ | ----- | ----- | --------- |
| FIN | 0x01 | 1 | tcp-fin |
| SYN | 0x02 | 2 | tcp-syn |
| RST | 0x04 | 4 | tcp-rst |
| PSH | 0x08 | 8 | tcp-push |
| ACK | 0x10 | 16 | tcp-ack |
| URG | 0x20 | 32 | tcp-urg |

### Traffico HTTP

```bash
# Richieste HTTP (porta 80)
tcpdump -A 'tcp port 80 and (((ip[2:2] - ((ip[0]&0xf)<<2)) - ((tcp[12]&0xf0)>>2)) != 0)'

# Richieste GET
tcpdump -A 'tcp port 80 and tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x47455420'

# Richieste POST
tcpdump -A 'tcp port 80 and tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x504f5354'

# Acquisizione HTTP semplice
tcpdump -A -s0 'tcp port 80'
```

### Traffico DNS

```bash
# Tutto il traffico DNS
tcpdump port 53
tcpdump udp port 53

# Solo query DNS
tcpdump 'udp port 53 and udp[10] & 0x80 = 0'

# Solo risposte DNS
tcpdump 'udp port 53 and udp[10] & 0x80 = 0x80'
```

### Traffico ICMP

```bash
# Tutto il traffico ICMP
tcpdump icmp

# Richiesta echo ICMP (ping)
tcpdump 'icmp[icmptype] == icmp-echo'

# Risposta echo ICMP
tcpdump 'icmp[icmptype] == icmp-echoreply'

# ICMP destinazione irraggiungibile
tcpdump 'icmp[icmptype] == icmp-unreach'
```

### Traffico ARP

```bash
# Tutto il traffico ARP
tcpdump arp

# Richieste ARP
tcpdump 'arp[6:2] == 1'

# Risposte ARP
tcpdump 'arp[6:2] == 2'
```

---

## 💾 Opzioni di output

### Scrivere su file

```bash
# Scrivere in un file pcap
tcpdump -w capture.pcap

# Scrivi con rotazione (100 file da 1 MB ciascuno)
tcpdump -w capture.pcap -W 100 -C 1

# Scrivi con marca temporale nel nome del file
tcpdump -w capture_%Y%m%d_%H%M%S.pcap -G 3600

# Acquisisci e visualizza
tcpdump -w capture.pcap | tee
```

### Leggere da file

```bash
# Leggi un file pcap
tcpdump -r capture.pcap

# Leggi applicando filtri
tcpdump -r capture.pcap 'host 192.168.1.1'

# Conta i pacchetti
tcpdump -r capture.pcap | wc -l

# Leggi con output dettagliato
tcpdump -nnvvXSs0 -r capture.pcap
```

### Formati di visualizzazione

```bash
# Contenuto ASCII
tcpdump -A -i eth0

# Esadecimale e ASCII
tcpdump -X -i eth0

# Solo esadecimale
tcpdump -x -i eth0

# Con header Ethernet
tcpdump -XX -i eth0

# Stampa i numeri di sequenza assoluti
tcpdump -S -i eth0
```

---

## 🎬 Esempi pratici

### Esempio 1: acquisire il traffico web
```bash
sudo tcpdump -i eth0 -nnvvXSs0 'tcp port 80 or tcp port 443'
```

### Esempio 2: acquisire le query DNS
```bash
sudo tcpdump -i eth0 -nn 'udp port 53'
```

### Esempio 3: acquisire il traffico verso un host specifico
```bash
sudo tcpdump -i eth0 -nn host 192.168.1.100
```

### Esempio 4: individuare connessioni SSH
```bash
sudo tcpdump -i eth0 'tcp port 22'
```

### Esempio 5: acquisire ICMP (ping)
```bash
sudo tcpdump -i eth0 icmp
```

### Esempio 6: rilevare una scansione delle porte (SYN)
```bash
sudo tcpdump -i eth0 'tcp[tcpflags] == tcp-syn'
```

### Esempio 7: acquisire solo il traffico in ingresso
```bash
sudo tcpdump -i eth0 'dst host $(hostname -I | cut -d" " -f1)'
```

### Esempio 8: monitorare una conversazione specifica
```bash
sudo tcpdump -i eth0 'host 192.168.1.1 and host 192.168.1.2'
```

### Esempio 9: acquisire credenziali FTP
```bash
sudo tcpdump -A -i eth0 'tcp port 21'
```

### Esempio 10: salvare per l'analisi in Wireshark
```bash
sudo tcpdump -i eth0 -s0 -w capture.pcap
# Poi apri capture.pcap in Wireshark
```

### Esempio 11: acquisizione remota tramite SSH
```bash
ssh user@remote 'sudo tcpdump -w - -i eth0' | wireshark -k -i -
```

### Esempio 12: acquisire escludendo SSH
```bash
sudo tcpdump -i eth0 'not port 22'
```

---

## 📊 Riferimento rapido

### Comandi essenziali

| Attività | Comando |
| ------ | --------- |
| Elencare le interfacce | `tcpdump -D` |
| Acquisizione di base | `tcpdump -i eth0` |
| Senza risoluzione DNS | `tcpdump -nn` |
| Acquisire N pacchetti | `tcpdump -c 100` |
| Scrivere su file | `tcpdump -w file.pcap` |
| Leggi da file | `tcpdump -r file.pcap` |
| Output dettagliato | `tcpdump -v` |
| Output ASCII | `tcpdump -A` |
| Output esadecimale | `tcpdump -X` |
| Pacchetto completo | `tcpdump -s0` |

### Filtri comuni

| Filtro | Descrizione |
| -------- | ------------- |
| `host 1.2.3.4` | Traffico da/verso l'host |
| `port 80` | Traffico sulla porta 80 |
| `tcp` | Solo traffico TCP |
| `udp` | Solo traffico UDP |
| `icmp` | Solo traffico ICMP |
| `net 192.168.1.0/24` | Traffico da/verso la sottorete |
| `src host 1.2.3.4` | L'host è l'origine |
| `dst port 443` | Porta di destinazione 443 |

### Combinazioni di filtri

| Esempio | Descrizione |
| --------- | ------------- |
| `host A and port 80` | Host A, porta 80 |
| `port 80 or port 443` | Porta 80 o 443 |
| `not port 22` | Escludere SSH |
| `tcp and port 80` | Porta TCP 80 |

---

## 💡 Consigli e buone pratiche

### Consigli sulle prestazioni

```bash
# Non risolvere i nomi DNS (più veloce)
tcpdump -nn

# Limita la dimensione acquisita per pacchetto
tcpdump -s 96   # Solo header

# Usa filtri BPF per ridurre il carico
tcpdump 'host 192.168.1.1'  # Filtra a livello del kernel
```

### Consigli sulla sicurezza

```bash
# Esegui senza privilegi root (se configurato)
# Oppure riduci al minimo la durata dell'acquisizione

# Riduci i privilegi dopo l'acquisizione (descrizione originale: vedi nota)
tcpdump -Z user
```

### Alias utili

```bash
# Aggiungi a ~/.bashrc
alias tcpdump='sudo tcpdump -nnvvXSs0'
alias tcpdump-web='sudo tcpdump -nnvvXSs0 port 80 or port 443'
alias tcpdump-dns='sudo tcpdump -nn port 53'
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
> **Il monitoraggio non autorizzato della rete è illegale.**

---

## 📚 Risorse

### Risorse ufficiali
- [Manuale di tcpdump](https://www.tcpdump.org/manpages/tcpdump.1.html)
- [Sito ufficiale di tcpdump](https://www.tcpdump.org/)
- [Sintassi dei filtri BPF](https://biot.com/capstats/bpf.html)

### Schede di riferimento correlate
- [Wireshark](../../Wireshark/translations/README.it.md)
- [Nmap](../../Nmap/translations/README.it.md)
- [Metasploit](../../Metasploit/translations/README.it.md)

---

<p align="center">
  <b>📡 Padroneggia l'analisi dei pacchetti a riga di comando!</b><br>
  <i>L'alternativa leggera a Wireshark</i>
</p>
