# 🐉 Kali Linux Tools - Riferimento completo (600+ strumenti)

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

> **Nota sul catalogo:** “600+” e le categorie riprendono l’originale: questa pagina è una selezione, non un inventario completo o aggiornato di tutti i pacchetti. Disponibilità, nomi, manutenzione e installazione predefinita dipendono dalla release e dai metapacchetti; una voce non implica che lo strumento sia già installato. Alcune classificazioni e descrizioni sono storiche o semplificate, non confronti prestazionali verificati. Consulta il [catalogo ufficiale degli strumenti](https://www.kali.org/tools/) e la [guida ai metapacchetti](https://www.kali.org/docs/general-use/metapackages/). I nomi dei prodotti e tutte le voci delle tabelle originali sono conservati; OSINT indica l’analisi di fonti aperte e “enumerazione” la raccolta strutturata di informazioni.

[🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██╗  ██╗ █████╗ ██╗     ██╗    ██╗     ██╗███╗   ██╗██╗   ██╗██╗  ██╗
  ██║ ██╔╝██╔══██╗██║     ██║    ██║     ██║████╗  ██║██║   ██║╚██╗██╔╝
  █████╔╝ ███████║██║     ██║    ██║     ██║██╔██╗ ██║██║   ██║ ╚███╔╝ 
  ██╔═██╗ ██╔══██║██║     ██║    ██║     ██║██║╚██╗██║██║   ██║ ██╔██╗ 
  ██║  ██╗██║  ██║███████╗██║    ███████╗██║██║ ╚████║╚██████╔╝██╔╝ ██╗
  ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝    ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝  ╚═╝
                    GUIDA DI RIFERIMENTO AGLI STRUMENTI
```

<p align="center">
  <img src="https://img.shields.io/badge/Kali_Linux-Tools-557C94?style=for-the-badge&logo=kalilinux" alt="Kali">
  <img src="https://img.shields.io/badge/600+-Tools-red?style=for-the-badge" alt="Tools">
  <img src="https://img.shields.io/badge/14-Categories-blue?style=for-the-badge" alt="Categories">
</p>

---

## 📋 Indice

1. [Raccolta di informazioni](#1--raccolta-di-informazioni)
2. [Analisi delle vulnerabilità](#2--analisi-delle-vulnerabilità)
3. [Analisi delle applicazioni web](#3--analisi-delle-applicazioni-web)
4. [Valutazione dei database](#4--valutazione-dei-database)
5. [Attacchi alle password](#5--attacchi-alle-password)
6. [Attacchi wireless](#6--attacchi-wireless)
7. [Reverse engineering](#7--reverse-engineering)
8. [Strumenti di sfruttamento](#8--strumenti-di-sfruttamento)
9. [Sniffing e spoofing](#9--sniffing-e-spoofing)
10. [Post-exploitation](#10--post-exploitation)
11. [Analisi forense](#11--analisi-forense)
12. [Strumenti di reportistica](#12--strumenti-di-reportistica)
13. [Ingegneria sociale](#13--ingegneria-sociale)
14. [Hacking hardware](#14--hacking-hardware)

---

## 1. 🔍 Raccolta di informazioni

### Analisi DNS

| Strumento | Descrizione |
|------|-------------|
| **dnsrecon** | Script di enumerazione DNS |
| **dnsenum** | Strumento di enumerazione DNS |
| **fierce** | Strumento di ricognizione DNS |
| **dnsmap** | Ricerca di sottodomini tramite brute force |
| **dnstracer** | Strumento per tracciare il DNS |
| **dnswalk** | Debugger del database DNS |

### Individuazione degli host

| Strumento | Descrizione |
|------|-------------|
| **nmap** | Individuazione delle reti e scansione delle porte |
| **masscan** | Scanner veloce delle porte |
| **hping3** | Generatore di pacchetti di rete |
| **fping** | Ricerca rapida degli host tramite ping |
| **arping** | Utilità di ping ARP |
| **netdiscover** | Ricognizione ARP attiva e passiva |
| **unicornscan** | Scanner di rete asincrono |

### Scanner di rete e porte

| Strumento | Descrizione |
|------|-------------|
| **nmap** | Lo scanner di rete più popolare secondo l'originale |
| **masscan** | Lo scanner delle porte più veloce secondo l'originale |
| **rustscan** | Scanner moderno e veloce |
| **zmap** | Scanner di rete veloce a pacchetto singolo |
| **unicornscan** | Scanner TCP/UDP asincrono |
| **nbtscan** | Scanner dei nomi NetBIOS |
| **onesixtyone** | Scanner SNMP |

### Strumenti OSINT

| Strumento | Descrizione |
|------|-------------|
| **maltego** | OSINT e analisi grafica delle relazioni |
| **spiderfoot** | Strumento di automazione OSINT |
| **recon-ng** | Framework di ricognizione web |
| **theHarvester** | Raccolta di email, sottodomini e indirizzi IP |
| **shodan** | Client CLI del motore di ricerca IoT |
| **amass** | Mappatura della superficie di attacco |
| **subfinder** | Strumento per individuare sottodomini |
| **assetfinder** | Individuazione di domini e sottodomini |
| **waybackurls** | Recupero degli URL dalla Wayback Machine |
| **gau** | Recupero di tutti gli URL |

### Analisi dei percorsi di rete

| Strumento | Descrizione |
|------|-------------|
| **traceroute** | Tracciamento dei percorsi di rete |
| **mtr** | Strumento di diagnostica di rete |
| **netmask** | Strumento per le maschere di rete |
| **tcptraceroute** | Traceroute TCP |

### Analisi SMB

| Strumento | Descrizione |
|------|-------------|
| **smbclient** | Client SMB |
| **smbmap** | Enumerazione delle condivisioni SMB |
| **enum4linux** | Enumerazione Windows/Samba |
| **enum4linux-ng** | Versione successiva di enum4linux |
| **rpcclient** | Client MS-RPC |
| **nbtscan** | Scanner NetBIOS |

### Analisi SMTP

| Strumento | Descrizione |
|------|-------------|
| **smtp-user-enum** | Enumerazione degli utenti SMTP |
| **swaks** | Strumento di test SMTP |

### Analisi SNMP

| Strumento | Descrizione |
|------|-------------|
| **snmpwalk** | Recupero di dati SNMP |
| **snmpcheck** | Enumerazione dei dispositivi SNMP |
| **onesixtyone** | Scanner SNMP veloce |
| **braa** | Scanner SNMP su larga scala |

### Analisi SSL

| Strumento | Descrizione |
|------|-------------|
| **sslscan** | Scanner SSL/TLS |
| **sslyze** | Strumento di analisi SSL/TLS |
| **testssl.sh** | Test SSL/TLS |

---

## 2. 🔓 Analisi delle vulnerabilità

### Scanner delle vulnerabilità

| Strumento | Descrizione |
|------|-------------|
| **nmap** | Script NSE per le vulnerabilità |
| **nikto** | Scanner di server web |
| **openvas** | Valutazione delle vulnerabilità open source |
| **legion** | Penetration testing di rete |
| **lynis** | Strumento di verifica della sicurezza |
| **nuclei** | Scanner delle vulnerabilità basato su template |

### Strumenti di fuzzing

| Strumento | Descrizione |
|------|-------------|
| **wfuzz** | Fuzzer web |
| **ffuf** | Fuzzer web veloce |
| **gobuster** | Ricerca di directory e DNS tramite brute force |
| **dirb** | Scanner dei contenuti web |
| **dirbuster** | Ricerca di directory tramite brute force |
| **feroxbuster** | Individuazione ricorsiva dei contenuti |
| **spike** | Fuzzer di protocolli |
| **sfuzz** | Fuzzer semplice |

### Strumenti VoIP

| Strumento | Descrizione |
|------|-------------|
| **sipvicious** | Strumenti per il protocollo SIP |
| **voiphopper** | VLAN hopping VoIP (passaggio tra VLAN) |

---

## 3. 🌐 Analisi delle applicazioni web

### Identificazione dei CMS

| Strumento | Descrizione |
|------|-------------|
| **wpscan** | Scanner delle vulnerabilità WordPress |
| **joomscan** | Scanner per Joomla |
| **droopescan** | Scanner per Drupal, WordPress e Joomla |
| **cmseek** | Rilevamento e sfruttamento dei CMS |

### Scanner delle vulnerabilità web

| Strumento | Descrizione |
|------|-------------|
| **nikto** | Scanner di server web |
| **burpsuite** | Test di sicurezza delle applicazioni web |
| **zaproxy** | Proxy OWASP ZAP |
| **wapiti** | Scanner delle vulnerabilità web |
| **skipfish** | Scanner di applicazioni web |
| **arachni** | Scanner di applicazioni web |
| **w3af** | Framework di attacco alle applicazioni web |
| **vega** | Scanner della sicurezza web |

### Crawler web (esplorazione dei siti)

| Strumento | Descrizione |
|------|-------------|
| **cutycapt** | Screenshot di pagine web |
| **dirb** | Scanner dei contenuti web |
| **dirbuster** | Ricerca di directory tramite brute force |
| **gobuster** | Ricerca di directory e DNS tramite brute force |
| **feroxbuster** | Individuazione veloce dei contenuti |

### Proxy per applicazioni web

| Strumento | Descrizione |
|------|-------------|
| **burpsuite** | Proxy web e scanner |
| **zaproxy** | OWASP ZAP |
| **mitmproxy** | Proxy HTTP |
| **proxychains** | Catene di proxy |

### SQL injection

| Strumento | Descrizione |
|------|-------------|
| **sqlmap** | Automazione della SQL injection |
| **sqlninja** | Injection in SQL Server |
| **bbqsql** | SQL injection blind |
| **jsql-injection** | SQL injection in Java |
| **ghauri** | Strumento di SQL injection |

### Strumenti XSS

| Strumento | Descrizione |
|------|-------------|
| **xsser** | Strumento di verifica delle vulnerabilità XSS |
| **xssstrike** | Rilevamento avanzato degli XSS |
| **dalfox** | Analisi dei parametri per XSS |

---

## 4. 💾 Valutazione dei database

| Strumento | Descrizione |
|------|-------------|
| **sqlmap** | Automazione della SQL injection |
| **sqlninja** | Injection in MS SQL |
| **jsql-injection** | SQLi basata su Java |
| **oscanner** | Scanner Oracle |
| **tnscmd10g** | Strumento per il listener Oracle TNS |
| **odat** | Strumento di attacco ai database Oracle |
| **mdbtools** | Strumenti per MS Access |
| **sidguesser** | Ricerca dei SID Oracle tramite tentativi |

---

## 5. 🔑 Attacchi alle password

### Attacchi offline

| Strumento | Descrizione |
|------|-------------|
| **john** | John the Ripper per il cracking delle password |
| **hashcat** | Cracking delle password con GPU |
| **ophcrack** | Cracking delle password Windows |
| **hashid** | Identificatore di hash |
| **hash-identifier** | Identificatore del tipo di hash |
| **chntpw** | Reimpostazione delle password Windows |
| **samdump2** | Utilità di estrazione SAM |
| **crunch** | Generatore di wordlist |
| **cewl** | Generatore di wordlist personalizzate |
| **cupp** | Creazione di profili di password degli utenti |
| **rsmangler** | Modifica delle wordlist (mangling) |
| **wordlists** | Raccolta di wordlist |

### Attacchi online

| Strumento | Descrizione |
|------|-------------|
| **hydra** | Tentativi di accesso ai servizi di rete |
| **medusa** | Tentativi di accesso paralleli tramite brute force |
| **ncrack** | Tentativi di autenticazione di rete |
| **patator** | Brute force multiuso |
| **thc-pptp-bruter** | Brute force PPTP |
| **crowbar** | Strumento per il brute force |

### Passaggio dell'hash (Pass-the-Hash)

| Strumento | Descrizione |
|------|-------------|
| **mimikatz** | Estrazione delle credenziali Windows |
| **pth-toolkit** | Toolkit Pass-the-Hash |
| **smbmap** | Enumerazione SMB |
| **crackmapexec** | Strumento di pentesting di rete |
| **evil-winrm** | Gestione remota di Windows |

---

## 6. 📡 Attacchi wireless

### Bluetooth

| Strumento | Descrizione |
|------|-------------|
| **bluelog** | Scanner Bluetooth |
| **bluemaho** | Test di sicurezza Bluetooth |
| **blueranger** | Rilevamento della vicinanza Bluetooth |
| **btscanner** | Scanner di dispositivi Bluetooth |
| **redfang** | Scanner di dispositivi Bluetooth nascosti |
| **spooftooph** | Spoofing Bluetooth (falsificazione dell'identità) |

### Strumenti WiFi

| Strumento | Descrizione |
|------|-------------|
| **aircrack-ng** | Suite per la sicurezza WiFi |
| **airmon-ng** | Attivazione della modalità monitor |
| **airodump-ng** | Acquisizione di pacchetti |
| **aireplay-ng** | Iniezione di pacchetti |
| **airbase-ng** | Access point fittizio |
| **wifite** | Verifica automatizzata della sicurezza WiFi |
| **fern-wifi-cracker** | Strumento di verifica della sicurezza WiFi |
| **kismet** | Rilevamento delle reti wireless |
| **reaver** | Strumento di attacco WPS |
| **bully** | Brute force WPS |
| **pixiewps** | Attacco offline WPS |
| **wifiphisher** | Strumento di phishing WiFi |
| **fluxion** | Attacco MitM a WPA |
| **hostapd-wpe** | Raccolta di credenziali wireless |
| **cowpatty** | Cracking WPA-PSK |
| **asleap** | Recupero delle password LEAP/PPTP |
| **eapmd5pass** | Cracking EAP-MD5 |
| **mdk3** | Strumento di attacco wireless |
| **mdk4** | Strumento di sfruttamento delle reti wireless |

### RFID/NFC

| Strumento | Descrizione |
|------|-------------|
| **mfoc** | Cracking offline Mifare Classic |
| **mfcuk** | Toolkit universale Mifare Classic |
| **mfterm** | Terminale per Mifare Classic |
| **libnfc** | Libreria NFC |
| **proxmark3** | Strumento di ricerca RFID |

---

## 7. 🔧 Reverse engineering

### Debugger

| Strumento | Descrizione |
|------|-------------|
| **gdb** | GNU Debugger |
| **edb-debugger** | Debugger multipiattaforma |
| **radare2** | Framework di reverse engineering |
| **x64dbg** | Debugger x64/x32 |
| **ollydbg** | Debugger x86 |
| **immunity-debugger** | Debugger controllabile tramite script Python |

### Disassemblatori

| Strumento | Descrizione |
|------|-------------|
| **ghidra** | Strumento NSA per il reverse engineering |
| **radare2** | Framework di reverse engineering |
| **cutter** | Interfaccia grafica di Radare2 |
| **rizin** | Framework di reverse engineering |
| **objdump** | Disassemblatore di file oggetto |
| **IDA Free** | Disassemblatore interattivo |

### Altri strumenti di reverse engineering

| Strumento | Descrizione |
|------|-------------|
| **apktool** | Strumento per APK Android |
| **dex2jar** | Conversione da DEX a JAR |
| **jadx** | Decompilatore Java |
| **jd-gui** | Interfaccia grafica del decompilatore Java |
| **bytecode-viewer** | Visualizzatore di bytecode Java |
| **androguard** | Analisi delle app Android |
| **frida** | Strumentazione dinamica |
| **angr** | Piattaforma di analisi dei binari |
| **binwalk** | Strumento di analisi del firmware |

---

## 8. 💣 Strumenti di sfruttamento

### Framework di sfruttamento

| Strumento | Descrizione |
|------|-------------|
| **metasploit-framework** | Framework di sfruttamento |
| **armitage** | Interfaccia grafica di Metasploit |
| **cobalt-strike** | Simulazione di avversari |
| **beef-xss** | Sfruttamento del browser |
| **routersploit** | Sfruttamento dei router |
| **exploitdb** | Database di exploit |
| **searchsploit** | Ricerca in Exploit-DB |

### Strumenti per shellcode

| Strumento | Descrizione |
|------|-------------|
| **msfvenom** | Generatore di payload |
| **veil** | Framework di generazione dei payload |
| **shellnoob** | Toolkit per shellcode |

### Sfruttamento della rete

| Strumento | Descrizione |
|------|-------------|
| **responder** | Avvelenamento LLMNR/NBT-NS |
| **impacket** | Toolkit di protocolli di rete |
| **crackmapexec** | Strumento di post-exploitation |
| **evil-winrm** | Shell WinRM |
| **powershell-empire** | Framework di post-exploitation |

---

## 9. 📡 Sniffing e spoofing

### Analizzatori del traffico di rete

| Strumento | Descrizione |
|------|-------------|
| **wireshark** | Analizzatore dei protocolli di rete |
| **tcpdump** | Analizzatore del traffico da riga di comando |
| **tshark** | Wireshark da terminale |
| **ettercap** | Analisi del traffico di rete e MitM |
| **dsniff** | Strumenti di verifica della rete |
| **net-creds** | Intercettazione di credenziali |
| **pcredz** | Estrazione delle credenziali |

### Strumenti di spoofing

| Strumento | Descrizione |
|------|-------------|
| **arpspoof** | Spoofing ARP |
| **dnsspoof** | Spoofing DNS |
| **macchanger** | Modifica dell'indirizzo MAC |
| **ettercap** | Avvelenamento ARP |
| **bettercap** | Strumento di attacco alla rete |
| **mitmproxy** | Proxy HTTP MitM |
| **sslstrip** | Rimozione di HTTPS (SSL stripping) |

### Intercettazione VoIP

| Strumento | Descrizione |
|------|-------------|
| **sipvicious** | Strumenti SIP/VoIP |
| **voiphopper** | VLAN hopping VoIP (passaggio tra VLAN) |

---

## 10. 🎯 Post-exploitation

### Backdoor dei sistemi operativi

| Strumento | Descrizione |
|------|-------------|
| **msfvenom** | Generatore di payload |
| **veil** | Elusione degli antivirus |
| **shellter** | Iniezione dinamica di shellcode |
| **weevely** | Web shell PHP |

### Tunneling (creazione di tunnel)

| Strumento | Descrizione |
|------|-------------|
| **sshuttle** | VPN tramite tunnel SSH |
| **chisel** | Tunneling HTTP |
| **proxychains** | Catene di proxy |
| **socat** | Strumento di inoltro dei dati |
| **plink** | Collegamento PuTTY |
| **stunnel** | Tunneling TLS |
| **dns2tcp** | Tunneling DNS |
| **iodine** | Tunnel DNS |
| **ptunnel** | Tunneling ICMP |
| **pwnat** | Attraversamento del NAT |
| **udptunnel** | Tunneling UDP |

### Post-exploitation Windows

| Strumento | Descrizione |
|------|-------------|
| **mimikatz** | Estrazione di credenziali Windows |
| **crackmapexec** | Pentesting Windows/AD |
| **evil-winrm** | Shell WinRM |
| **impacket** | Protocolli Windows |
| **bloodhound** | Ricognizione Active Directory |
| **powershell-empire** | Post-exploitation PowerShell |
| **rubeus** | Abuso di Kerberos |
| **kerbrute** | Brute force Kerberos |

### Post-exploitation Linux

| Strumento | Descrizione |
|------|-------------|
| **linpeas** | Escalation dei privilegi Linux |
| **linenum** | Enumerazione Linux |
| **pspy** | Monitor dei processi |
| **linux-exploit-suggester** | Ricerca di exploit del kernel |

---

## 11. 🔬 Analisi forense

### Framework di analisi forense

| Strumento | Descrizione |
|------|-------------|
| **autopsy** | Piattaforma di analisi forense digitale |
| **sleuthkit** | Toolkit di analisi forense |
| **dff** | Framework di analisi forense digitale |

### Estrazione forense di file (carving)

| Strumento | Descrizione |
|------|-------------|
| **foremost** | Strumento di estrazione dei file |
| **scalpel** | Estrazione di file dai dati grezzi |
| **bulk_extractor** | Estrazione di informazioni |
| **photorec** | Recupero di foto |
| **testdisk** | Recupero di dati |

### Acquisizione di immagini forensi

| Strumento | Descrizione |
|------|-------------|
| **dc3dd** | Acquisizione forense di immagini disco |
| **dcfldd** | Versione potenziata di dd |
| **guymager** | Interfaccia grafica per immagini forensi |

### Analisi forense della memoria

| Strumento | Descrizione |
|------|-------------|
| **volatility** | Analisi forense della memoria |
| **volatility3** | Analisi della memoria |
| **lime** | Estrazione della memoria Linux |

### Analisi forense di rete

| Strumento | Descrizione |
|------|-------------|
| **wireshark** | Analisi dei pacchetti |
| **tcpdump** | Acquisizione di pacchetti |
| **ngrep** | Grep di rete |
| **NetworkMiner** | Analisi forense di rete |

### Analisi forense dei PDF

| Strumento | Descrizione |
|------|-------------|
| **pdfid** | Scanner PDF |
| **pdf-parser** | Analisi dei PDF |
| **peepdf** | Strumento di analisi PDF |

---

## 12. 📝 Strumenti di reportistica

| Strumento | Descrizione |
|------|-------------|
| **cutycapt** | Screenshot di pagine web |
| **faraday** | Strumento collaborativo per pentest |
| **maltego** | Visualizzazione OSINT |
| **pipal** | Analisi delle password |
| **recordmydesktop** | Registrazione dello schermo |
| **cherrytree** | Applicazione per appunti |
| **dradis** | Piattaforma di collaborazione |
| **metagoofil** | Estrazione dei metadati |
| **eyewitness** | Screenshot di siti web |
| **gowitness** | Screenshot di siti web |

---

## 13. 🎭 Ingegneria sociale

| Strumento | Descrizione |
|------|-------------|
| **setoolkit** | Social Engineering Toolkit (strumenti di ingegneria sociale) |
| **gophish** | Framework di phishing |
| **king-phisher** | Campagna di phishing |
| **evilginx2** | Framework di phishing MitM |
| **beef-xss** | Sfruttamento del browser |
| **maltego** | Relazioni OSINT |
| **theHarvester** | Raccolta di indirizzi email |
| **sherlock** | OSINT sui nomi utente |
| **social-analyzer** | Analisi dei social media |

---

## 14. 🔧 Hacking hardware

| Strumento | Descrizione |
|------|-------------|
| **arduino** | Piattaforma hardware |
| **binwalk** | Analisi del firmware |
| **flashrom** | Programmatore di memorie flash |
| **openocd** | Debugging sul chip |
| **proxmark3** | Ricerca RFID |

---

## 📊 Riferimento rapido - I 50 strumenti principali

| # | Strumento | Categoria | Caso d'uso |
|---|------|----------|----------|
| 1 | **nmap** | Scansione | Scansione delle porte |
| 2 | **metasploit** | Sfruttamento | Framework di exploit |
| 3 | **burpsuite** | Web | Test web |
| 4 | **wireshark** | Intercettazione del traffico | Analisi dei pacchetti |
| 5 | **john** | Password | Recupero delle password tramite tentativi (cracking) |
| 6 | **hashcat** | Password | Cracking con GPU |
| 7 | **hydra** | Password | Brute force online |
| 8 | **sqlmap** | Web | SQL injection |
| 9 | **aircrack-ng** | Wireless | Cracking delle password WiFi |
| 10 | **nikto** | Web | Scansione web |
| 11 | **gobuster** | Web | Ricerca di directory tramite brute force |
| 12 | **ffuf** | Web | Fuzzing web |
| 13 | **dirb** | Web | Scansione delle directory |
| 14 | **wpscan** | Web | Scansione WordPress |
| 15 | **enum4linux** | Informazioni | Enumerazione SMB |
| 16 | **crackmapexec** | Post-exploitation | Pentesting AD |
| 17 | **mimikatz** | Post-exploitation | Estrazione delle credenziali |
| 18 | **responder** | Sfruttamento | Avvelenamento LLMNR |
| 19 | **impacket** | Sfruttamento | Strumenti di protocollo |
| 20 | **bloodhound** | Post-exploitation | Mappatura AD |
| 21 | **netcat** | Utilità | Utilità TCP/UDP |
| 22 | **socat** | Utilità | Inoltro dei dati |
| 23 | **tcpdump** | Intercettazione del traffico | Acquisizione di pacchetti |
| 24 | **ettercap** | Intercettazione del traffico | Attacchi MitM |
| 25 | **bettercap** | Intercettazione del traffico | Attacchi alla rete |
| 26 | **proxychains** | Utilità | Catene di proxy |
| 27 | **msfvenom** | Sfruttamento | Generazione di payload |
| 28 | **searchsploit** | Sfruttamento | Ricerca di exploit |
| 29 | **theHarvester** | OSINT | Raccolta di indirizzi email |
| 30 | **maltego** | OSINT | Analisi delle relazioni |
| 31 | **recon-ng** | OSINT | Framework di ricognizione |
| 32 | **amass** | OSINT | Enumerazione dei sottodomini |
| 33 | **subfinder** | OSINT | Ricerca di sottodomini |
| 34 | **nuclei** | Vulnerabilità | Scanner basato su template |
| 35 | **zaproxy** | Web | OWASP ZAP |
| 36 | **wifite** | Wireless | Verifica WiFi automatizzata |
| 37 | **ghidra** | Reverse engineering | Reverse engineering |
| 38 | **radare2** | Reverse engineering | Analisi dei binari |
| 39 | **gdb** | Reverse engineering | Debugger |
| 40 | **binwalk** | Reverse engineering | Analisi del firmware |
| 41 | **volatility** | Analisi forense | Analisi della memoria |
| 42 | **autopsy** | Analisi forense | Piattaforma di analisi forense |
| 43 | **foremost** | Analisi forense | Estrazione di file (carving) |
| 44 | **setoolkit** | Ingegneria sociale | Toolkit di ingegneria sociale |
| 45 | **beef-xss** | Web | Sfruttamento del browser |
| 46 | **linpeas** | Escalation dei privilegi | Enumerazione Linux |
| 47 | **winpeas** | Escalation dei privilegi | Enumerazione Windows |
| 48 | **chisel** | Tunneling (creazione di tunnel) | Tunnel HTTP |
| 49 | **sshuttle** | Tunneling (creazione di tunnel) | VPN SSH |
| 50 | **evil-winrm** | Post-exploitation | Shell WinRM |

---

## 📚 Risorse

- [Kali Linux Tools](https://www.kali.org/tools/)
- [Documentazione Kali](https://www.kali.org/docs/)
- [Metapacchetti Kali](https://www.kali.org/docs/general-use/metapackages/)

---

<p align="center">
  <b>🐉 Padroneggia Kali Linux!</b><br>
  <i>600+ strumenti a portata di mano</i>
</p>
