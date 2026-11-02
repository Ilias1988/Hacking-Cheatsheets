# 🐉 Kali-Linux-Werkzeuge – Vollständige Referenz (600+ Werkzeuge)

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██╗  ██╗ █████╗ ██╗     ██╗    ██╗     ██╗███╗   ██╗██╗   ██╗██╗  ██╗
  ██║ ██╔╝██╔══██╗██║     ██║    ██║     ██║████╗  ██║██║   ██║╚██╗██╔╝
  █████╔╝ ███████║██║     ██║    ██║     ██║██╔██╗ ██║██║   ██║ ╚███╔╝ 
  ██╔═██╗ ██╔══██║██║     ██║    ██║     ██║██║╚██╗██║██║   ██║ ██╔██╗ 
  ██║  ██╗██║  ██║███████╗██║    ███████╗██║██║ ╚████║╚██████╔╝██╔╝ ██╗
  ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝    ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝  ╚═╝
                    WERKZEUGREFERENZ
```

<p align="center">
  <img src="https://img.shields.io/badge/Kali_Linux-Tools-557C94?style=for-the-badge&logo=kalilinux" alt="Kali">
  <img src="https://img.shields.io/badge/600+-Tools-red?style=for-the-badge" alt="Werkzeuge">
  <img src="https://img.shields.io/badge/14-Categories-blue?style=for-the-badge" alt="Kategorien">
</p>

---

## 📋 Inhaltsverzeichnis

1. [Informationsgewinnung](#1--informationsgewinnung)
2. [Schwachstellenanalyse](#2--schwachstellenanalyse)
3. [Analyse von Webanwendungen](#3--analyse-von-webanwendungen)
4. [Datenbankprüfung](#4--datenbankprüfung)
5. [Passwortangriffe](#5--passwortangriffe)
6. [Angriffe auf Funknetze](#6--angriffe-auf-funknetze)
7. [Reverse Engineering](#7--reverse-engineering)
8. [Werkzeuge zur Exploitation](#8--werkzeuge-zur-exploitation)
9. [Paketmitschnitt und Spoofing](#9--paketmitschnitt-und-spoofing)
10. [Post-Exploitation](#10--post-exploitation)
11. [Forensik](#11--forensik)
12. [Werkzeuge für Berichte](#12--werkzeuge-für-berichte)
13. [Social Engineering](#13--social-engineering)
14. [Hardware-Hacking](#14--hardware-hacking)
15. [Kurzreferenz – die 50 wichtigsten Werkzeuge](#-kurzreferenz--die-50-wichtigsten-werkzeuge)
16. [Ressourcen](#-ressourcen)
17. [Hinweise zur Einordnung und zu Versionen](#-hinweise-zur-einordnung-und-zu-versionen)

---

## 1. 🔍 Informationsgewinnung

### DNS-Analyse

| Werkzeug | Beschreibung |
|------|-------------|
| **dnsrecon** | Skript zur DNS-Enumeration |
| **dnsenum** | Werkzeug zur DNS-Enumeration |
| **fierce** | DNS-Erkundungswerkzeug |
| **dnsmap** | Subdomains per Brute Force ermitteln |
| **dnstracer** | DNS-Auflösung nachverfolgen |
| **dnswalk** | Fehler in DNS-Datenbanken untersuchen |

### Hosts ermitteln

| Werkzeug | Beschreibung |
|------|-------------|
| **nmap** | Netzwerke erfassen und Ports scannen |
| **masscan** | Schneller Portscanner |
| **hping3** | Netzwerkpakete erzeugen |
| **fping** | Hosts schnell per Ping ermitteln |
| **arping** | Hilfsprogramm für ARP-Pings |
| **netdiscover** | Aktive und passive ARP-Erkundung |
| **unicornscan** | Asynchroner Netzwerkscanner |

### Netzwerk- und Portscanner

| Werkzeug | Beschreibung |
|------|-------------|
| **nmap** | Beliebtester Netzwerkscanner |
| **masscan** | Schnellster Portscanner |
| **rustscan** | Moderner schneller Scanner |
| **zmap** | Schneller Netzwerkscanner mit einzelnen Paketen |
| **unicornscan** | Asynchroner TCP-/UDP-Scanner |
| **nbtscan** | NetBIOS-Namen ermitteln |
| **onesixtyone** | SNMP-Scanner |

### OSINT-Werkzeuge

| Werkzeug | Beschreibung |
|------|-------------|
| **maltego** | OSINT und grafische Beziehungsanalyse |
| **spiderfoot** | OSINT automatisieren |
| **recon-ng** | Framework zur Web-Erkundung |
| **theHarvester** | E-Mail-Adressen, Subdomains und IP-Adressen sammeln |
| **shodan** | Befehlszeilenzugriff auf eine IoT-Suchmaschine |
| **amass** | Angriffsfläche erfassen |
| **subfinder** | Subdomains entdecken |
| **assetfinder** | Domains und Subdomains finden |
| **waybackurls** | URLs aus der Wayback Machine abrufen |
| **gau** | URLs sammeln (Get All URLs) |

### Routenanalyse

| Werkzeug | Beschreibung |
|------|-------------|
| **traceroute** | Netzwerkrouten nachverfolgen |
| **mtr** | Netzwerkdiagnosewerkzeug |
| **netmask** | Werkzeug für Netzmasken |
| **tcptraceroute** | Traceroute über TCP |

### SMB-Analyse

| Werkzeug | Beschreibung |
|------|-------------|
| **smbclient** | SMB-Client |
| **smbmap** | SMB-Freigaben ermitteln |
| **enum4linux** | Windows- und Samba-Systeme enumerieren |
| **enum4linux-ng** | Weiterentwicklung von enum4linux |
| **rpcclient** | MS-RPC-Client |
| **nbtscan** | NetBIOS-Scanner |

### SMTP-Analyse

| Werkzeug | Beschreibung |
|------|-------------|
| **smtp-user-enum** | Benutzer über SMTP ermitteln |
| **swaks** | SMTP-Testwerkzeug |

### SNMP-Analyse

| Werkzeug | Beschreibung |
|------|-------------|
| **snmpwalk** | Daten per SNMP abrufen |
| **snmpcheck** | Geräte über SNMP enumerieren |
| **onesixtyone** | Schneller SNMP-Scanner |
| **braa** | Viele Systeme per SNMP untersuchen |

### SSL-Analyse

| Werkzeug | Beschreibung |
|------|-------------|
| **sslscan** | SSL-/TLS-Scanner |
| **sslyze** | SSL-/TLS-Analysewerkzeug |
| **testssl.sh** | SSL/TLS prüfen |

---

## 2. 🔓 Schwachstellenanalyse

### Schwachstellenscanner

| Werkzeug | Beschreibung |
|------|-------------|
| **nmap** | NSE-Skripte zur Schwachstellenprüfung |
| **nikto** | Webserver-Scanner |
| **openvas** | Offene Plattform zur Schwachstellenbewertung |
| **legion** | Netzwerk-Penetrationstests |
| **lynis** | Werkzeug für Sicherheitsprüfungen |
| **nuclei** | Vorlagenbasierter Schwachstellenscanner |

### Fuzzing-Werkzeuge

| Werkzeug | Beschreibung |
|------|-------------|
| **wfuzz** | Web-Fuzzer |
| **ffuf** | Schneller Web-Fuzzer |
| **gobuster** | Verzeichnisse und DNS-Namen per Brute Force ermitteln |
| **dirb** | Webinhalte suchen |
| **dirbuster** | Verzeichnisse per Brute Force ermitteln |
| **feroxbuster** | Webinhalte rekursiv entdecken |
| **spike** | Protokoll-Fuzzer |
| **sfuzz** | Einfacher Fuzzer |

### VoIP-Werkzeuge

| Werkzeug | Beschreibung |
|------|-------------|
| **sipvicious** | Werkzeuge für das SIP-Protokoll |
| **voiphopper** | VLAN-Hopping über VoIP |

---

## 3. 🌐 Analyse von Webanwendungen

### CMS identifizieren

| Werkzeug | Beschreibung |
|------|-------------|
| **wpscan** | Scanner für WordPress-Schwachstellen |
| **joomscan** | Joomla-Scanner |
| **droopescan** | Scanner für Drupal, WordPress und Joomla |
| **cmseek** | CMS erkennen und Schwachstellen ausnutzen |

### Scanner für Webschwachstellen

| Werkzeug | Beschreibung |
|------|-------------|
| **nikto** | Webserver-Scanner |
| **burpsuite** | Sicherheit von Webanwendungen prüfen |
| **zaproxy** | OWASP-ZAP-Proxy |
| **wapiti** | Scanner für Webschwachstellen |
| **skipfish** | Sicherheitsscanner für Webanwendungen |
| **arachni** | Sicherheitsscanner für Webanwendungen |
| **w3af** | Framework für Angriffe auf Webanwendungen |
| **vega** | Web-Sicherheitsscanner |

### Web-Crawler

| Werkzeug | Beschreibung |
|------|-------------|
| **cutycapt** | Screenshot einer Webseite erstellen |
| **dirb** | Webinhalte suchen |
| **dirbuster** | Verzeichnisse per Brute Force ermitteln |
| **gobuster** | Verzeichnisse und DNS-Namen per Brute Force ermitteln |
| **feroxbuster** | Webinhalte schnell entdecken |

### Proxys für Webanwendungen

| Werkzeug | Beschreibung |
|------|-------------|
| **burpsuite** | Web-Proxy und Scanner |
| **zaproxy** | OWASP ZAP |
| **mitmproxy** | HTTP-Proxy |
| **proxychains** | Proxy-Ketten |

### SQL-Injection

| Werkzeug | Beschreibung |
|------|-------------|
| **sqlmap** | SQL-Injection automatisieren |
| **sqlninja** | SQL-Injection gegen SQL Server |
| **bbqsql** | Blinde SQL-Injection |
| **jsql-injection** | SQL-Injection-Werkzeug in Java |
| **ghauri** | SQL-Injection-Werkzeug |

### XSS-Werkzeuge

| Werkzeug | Beschreibung |
|------|-------------|
| **xsser** | Auf XSS-Schwachstellen prüfen |
| **xssstrike** | Erweiterte XSS-Erkennung |
| **dalfox** | Parameter auf XSS untersuchen |

---

## 4. 💾 Datenbankprüfung

| Werkzeug | Beschreibung |
|------|-------------|
| **sqlmap** | SQL-Injection automatisieren |
| **sqlninja** | SQL-Injection gegen MS SQL |
| **jsql-injection** | Java-basiertes SQL-Injection-Werkzeug |
| **oscanner** | Oracle-Scanner |
| **tnscmd10g** | Werkzeug für den Oracle-TNS-Listener |
| **odat** | Werkzeug für Angriffe auf Oracle-Datenbanken |
| **mdbtools** | Werkzeuge für MS Access |
| **sidguesser** | Oracle-SIDs erraten |

---

## 5. 🔑 Passwortangriffe

### Offline-Angriffe

| Werkzeug | Beschreibung |
|------|-------------|
| **john** | Passwörter mit John the Ripper knacken |
| **hashcat** | Passwörter mit der GPU knacken |
| **ophcrack** | Windows-Passwörter knacken |
| **hashid** | Hashes identifizieren |
| **hash-identifier** | Hash-Typen identifizieren |
| **chntpw** | Windows-Passwörter zurücksetzen |
| **samdump2** | SAM-Daten auslesen |
| **crunch** | Wortlisten erzeugen |
| **cewl** | Angepasste Wortlisten erzeugen |
| **cupp** | Passwortkandidaten aus Benutzerprofilen ableiten |
| **rsmangler** | Wortlisten variieren |
| **wordlists** | Sammlung von Wortlisten |

### Online-Angriffe

| Werkzeug | Beschreibung |
|------|-------------|
| **hydra** | Netzwerkanmeldungen per Brute Force prüfen |
| **medusa** | Anmeldungen parallel per Brute Force prüfen |
| **ncrack** | Netzwerkauthentifizierung per Brute Force prüfen |
| **patator** | Vielseitiges Brute-Force-Werkzeug |
| **thc-pptp-bruter** | Brute Force gegen PPTP |
| **crowbar** | Brute-Force-Werkzeug |

### Pass-the-Hash

| Werkzeug | Beschreibung |
|------|-------------|
| **mimikatz** | Windows-Zugangsdaten extrahieren |
| **pth-toolkit** | Pass-the-Hash-Werkzeugsammlung |
| **smbmap** | SMB-Ressourcen ermitteln |
| **crackmapexec** | Werkzeug für Netzwerk-Penetrationstests |
| **evil-winrm** | Windows fernverwalten |

---

## 6. 📡 Angriffe auf Funknetze

### Bluetooth

| Werkzeug | Beschreibung |
|------|-------------|
| **bluelog** | Bluetooth-Scanner |
| **bluemaho** | Bluetooth-Sicherheit prüfen |
| **blueranger** | Nähe von Bluetooth-Geräten erkennen |
| **btscanner** | Scanner für Bluetooth-Geräte |
| **redfang** | Nicht sichtbare Bluetooth-Geräte suchen |
| **spooftooph** | Bluetooth-Spoofing |

### WLAN-Werkzeuge

| Werkzeug | Beschreibung |
|------|-------------|
| **aircrack-ng** | Werkzeugsammlung für WLAN-Sicherheit |
| **airmon-ng** | Monitormodus aktivieren |
| **airodump-ng** | Netzwerkpakete mitschneiden |
| **aireplay-ng** | Netzwerkpakete einschleusen |
| **airbase-ng** | Gefälschter Access Point |
| **wifite** | Automatisierte WLAN-Sicherheitsprüfung |
| **fern-wifi-cracker** | Werkzeug zur WLAN-Sicherheitsprüfung |
| **kismet** | Funknetze erkennen |
| **reaver** | Werkzeug für WPS-Angriffe |
| **bully** | Brute Force gegen WPS |
| **pixiewps** | Offline-Angriff auf WPS |
| **wifiphisher** | Werkzeug für WLAN-Phishing |
| **fluxion** | Man-in-the-Middle-Angriff auf WPA |
| **hostapd-wpe** | Zugangsdaten in Funknetzen erfassen |
| **cowpatty** | WPA-PSK knacken |
| **asleap** | Passwörter für LEAP und PPTP wiederherstellen |
| **eapmd5pass** | Passwörter für EAP-MD5 knacken |
| **mdk3** | Werkzeug für Angriffe auf Funknetze |
| **mdk4** | Schwachstellen in Funknetzen ausnutzen |

### RFID/NFC

| Werkzeug | Beschreibung |
|------|-------------|
| **mfoc** | Mifare-Classic-Schlüssel offline knacken |
| **mfcuk** | Universelle Werkzeugsammlung für Mifare Classic |
| **mfterm** | Terminal für Mifare Classic |
| **libnfc** | NFC-Bibliothek |
| **proxmark3** | Werkzeug zur RFID-Forschung |

---

## 7. 🔧 Reverse Engineering

### Debugger

| Werkzeug | Beschreibung |
|------|-------------|
| **gdb** | GNU Debugger |
| **edb-debugger** | Plattformübergreifender Debugger |
| **radare2** | Reverse-Engineering-Framework |
| **x64dbg** | x64-/x32-Debugger |
| **ollydbg** | x86-Debugger |
| **immunity-debugger** | Debugger mit Python-Skriptunterstützung |

### Disassembler

| Werkzeug | Beschreibung |
|------|-------------|
| **ghidra** | Reverse-Engineering-Werkzeug der NSA |
| **radare2** | Reverse-Engineering-Framework |
| **cutter** | Grafische Oberfläche für Radare2 |
| **rizin** | Reverse-Engineering-Framework |
| **objdump** | Objektdateien disassemblieren |
| **IDA Free** | Interaktiver Disassembler |

### Weitere Reverse-Engineering-Werkzeuge

| Werkzeug | Beschreibung |
|------|-------------|
| **apktool** | Werkzeug für Android-APKs |
| **dex2jar** | DEX-Dateien in JAR-Dateien umwandeln |
| **jadx** | Java-Decompiler |
| **jd-gui** | Grafische Oberfläche für einen Java-Decompiler |
| **bytecode-viewer** | Java-Bytecode anzeigen |
| **androguard** | Android-Apps analysieren |
| **frida** | Dynamische Instrumentierung |
| **angr** | Plattform zur Analyse von Binärdateien |
| **binwalk** | Werkzeug zur Firmware-Analyse |

---

## 8. 💣 Werkzeuge zur Exploitation

### Exploitation-Frameworks

| Werkzeug | Beschreibung |
|------|-------------|
| **metasploit-framework** | Framework zum Ausnutzen von Schwachstellen |
| **armitage** | Grafische Oberfläche für Metasploit |
| **cobalt-strike** | Angreifer simulieren |
| **beef-xss** | Browserschwachstellen ausnutzen |
| **routersploit** | Routerschwachstellen ausnutzen |
| **exploitdb** | Exploit-Datenbank |
| **searchsploit** | Exploit-DB durchsuchen |

### Shellcode-Werkzeuge

| Werkzeug | Beschreibung |
|------|-------------|
| **msfvenom** | Payloads erzeugen |
| **veil** | Framework zur Payload-Erzeugung |
| **shellnoob** | Shellcode-Werkzeugsammlung |

### Schwachstellen in Netzwerken ausnutzen

| Werkzeug | Beschreibung |
|------|-------------|
| **responder** | LLMNR- und NBT-NS-Antworten fälschen |
| **impacket** | Werkzeugsammlung für Netzwerkprotokolle |
| **crackmapexec** | Post-Exploitation-Werkzeug |
| **evil-winrm** | WinRM-Shell |
| **powershell-empire** | Post-Exploitation-Framework |

---

## 9. 📡 Paketmitschnitt und Spoofing

### Netzwerk-Sniffer

| Werkzeug | Beschreibung |
|------|-------------|
| **wireshark** | Netzwerkprotokolle analysieren |
| **tcpdump** | Paketmitschnitt über die Befehlszeile |
| **tshark** | Wireshark für das Terminal |
| **ettercap** | Netzwerkverkehr mitschneiden und Man-in-the-Middle-Analysen durchführen |
| **dsniff** | Werkzeuge für Netzwerksicherheitsprüfungen |
| **net-creds** | Zugangsdaten im Netzwerkverkehr erfassen |
| **pcredz** | Zugangsdaten extrahieren |

### Spoofing-Werkzeuge

| Werkzeug | Beschreibung |
|------|-------------|
| **arpspoof** | ARP-Spoofing |
| **dnsspoof** | DNS-Spoofing |
| **macchanger** | MAC-Adressen ändern |
| **ettercap** | ARP-Poisoning (gefälschte ARP-Zuordnungen) |
| **bettercap** | Werkzeug für Netzwerkangriffe |
| **mitmproxy** | HTTP-Proxy für Man-in-the-Middle-Analysen |
| **sslstrip** | HTTPS-Verbindungen auf HTTP herabstufen |

### VoIP-Verkehr mitschneiden

| Werkzeug | Beschreibung |
|------|-------------|
| **sipvicious** | SIP-/VoIP-Werkzeuge |
| **voiphopper** | VLAN-Hopping über VoIP |

---

## 10. 🎯 Post-Exploitation

### Hintertüren im Betriebssystem

| Werkzeug | Beschreibung |
|------|-------------|
| **msfvenom** | Payloads erzeugen |
| **veil** | Virenschutz umgehen |
| **shellter** | Shellcode dynamisch einschleusen |
| **weevely** | PHP-Webshell |

### Tunneling

| Werkzeug | Beschreibung |
|------|-------------|
| **sshuttle** | VPN über SSH-Tunnel |
| **chisel** | HTTP-Tunneling |
| **proxychains** | Proxy-Ketten |
| **socat** | Daten weiterleiten |
| **plink** | PuTTY-Verbindungswerkzeug |
| **stunnel** | TLS-Tunneling |
| **dns2tcp** | DNS-Tunneling |
| **iodine** | DNS-Tunnel |
| **ptunnel** | ICMP-Tunneling |
| **pwnat** | NAT-Grenzen überwinden |
| **udptunnel** | UDP-Tunneling |

### Post-Exploitation unter Windows

| Werkzeug | Beschreibung |
|------|-------------|
| **mimikatz** | Windows-Zugangsdaten auslesen |
| **crackmapexec** | Penetrationstests gegen Windows und AD |
| **evil-winrm** | WinRM-Shell |
| **impacket** | Windows-Protokolle |
| **bloodhound** | Active Directory erkunden |
| **powershell-empire** | Post-Exploitation mit PowerShell |
| **rubeus** | Kerberos-Funktionen missbrauchen |
| **kerbrute** | Brute Force gegen Kerberos |

### Post-Exploitation unter Linux

| Werkzeug | Beschreibung |
|------|-------------|
| **linpeas** | Privilegien unter Linux erweitern |
| **linenum** | Linux-Systeme enumerieren |
| **pspy** | Prozesse überwachen |
| **linux-exploit-suggester** | Kernel-Exploits ermitteln |

---

## 11. 🔬 Forensik

### Forensik-Frameworks

| Werkzeug | Beschreibung |
|------|-------------|
| **autopsy** | Plattform für digitale Forensik |
| **sleuthkit** | Forensik-Werkzeugsammlung |
| **dff** | Framework für digitale Forensik |

### Forensisches File Carving

| Werkzeug | Beschreibung |
|------|-------------|
| **foremost** | Werkzeug zum Rekonstruieren von Dateien anhand ihrer Datenstrukturen |
| **scalpel** | Dateien anhand ihrer Datenstrukturen rekonstruieren |
| **bulk_extractor** | Informationen extrahieren |
| **photorec** | Fotos wiederherstellen |
| **testdisk** | Daten wiederherstellen |

### Forensische Abbilder erstellen

| Werkzeug | Beschreibung |
|------|-------------|
| **dc3dd** | Forensische Datenträgerabbilder erstellen |
| **dcfldd** | Erweiterte Variante von dd |
| **guymager** | Grafische Oberfläche zum Erstellen forensischer Abbilder |

### Arbeitsspeicherforensik

| Werkzeug | Beschreibung |
|------|-------------|
| **volatility** | Arbeitsspeicherforensik |
| **volatility3** | Arbeitsspeicher analysieren |
| **lime** | Linux-Arbeitsspeicher erfassen |

### Netzwerkforensik

| Werkzeug | Beschreibung |
|------|-------------|
| **wireshark** | Netzwerkpakete analysieren |
| **tcpdump** | Netzwerkpakete mitschneiden |
| **ngrep** | Netzwerkverkehr nach Mustern durchsuchen |
| **NetworkMiner** | Netzwerkforensik |

### PDF-Forensik

| Werkzeug | Beschreibung |
|------|-------------|
| **pdfid** | PDF-Scanner |
| **pdf-parser** | PDF-Dateien analysieren |
| **peepdf** | Werkzeug zur PDF-Analyse |

---

## 12. 📝 Werkzeuge für Berichte

| Werkzeug | Beschreibung |
|------|-------------|
| **cutycapt** | Screenshots von Webseiten erstellen |
| **faraday** | Werkzeug für gemeinsame Penetrationstests |
| **maltego** | OSINT-Ergebnisse visualisieren |
| **pipal** | Passwörter analysieren |
| **recordmydesktop** | Bildschirmaufzeichnung |
| **cherrytree** | Notizanwendung |
| **dradis** | Plattform zur Zusammenarbeit |
| **metagoofil** | Metadaten extrahieren |
| **eyewitness** | Website-Screenshot erstellen |
| **gowitness** | Website-Screenshot erstellen |

---

## 13. 🎭 Social Engineering

| Werkzeug | Beschreibung |
|------|-------------|
| **setoolkit** | Social Engineering Toolkit |
| **gophish** | Phishing-Framework |
| **king-phisher** | Phishing-Kampagnen durchführen |
| **evilginx2** | Framework für Man-in-the-Middle-Phishing |
| **beef-xss** | Browserschwachstellen ausnutzen |
| **maltego** | Beziehungen mit OSINT untersuchen |
| **theHarvester** | E-Mail-Adressen sammeln |
| **sherlock** | Benutzernamen mit OSINT recherchieren |
| **social-analyzer** | Soziale Medien analysieren |

---

## 14. 🔧 Hardware-Hacking

| Werkzeug | Beschreibung |
|------|-------------|
| **arduino** | Hardwareplattform |
| **binwalk** | Firmware analysieren |
| **flashrom** | Flash-Speicher programmieren |
| **openocd** | On-Chip-Debugging |
| **proxmark3** | RFID erforschen |

---

## 📊 Kurzreferenz – die 50 wichtigsten Werkzeuge

| # | Werkzeug | Kategorie | Einsatzgebiet |
|---|------|----------|----------|
| 1 | **nmap** | Scanning | Ports scannen |
| 2 | **metasploit** | Exploitation | Exploit-Framework |
| 3 | **burpsuite** | Web | Webanwendungen testen |
| 4 | **wireshark** | Paketmitschnitt | Netzwerkpakete analysieren |
| 5 | **john** | Passwörter | Passwörter knacken |
| 6 | **hashcat** | Passwörter | Hashes mit der GPU knacken |
| 7 | **hydra** | Passwörter | Online-Brute-Force |
| 8 | **sqlmap** | Web | SQL-Injection |
| 9 | **aircrack-ng** | Funknetze | WLAN-Schlüssel knacken |
| 10 | **nikto** | Web | Websysteme scannen |
| 11 | **gobuster** | Web | Verzeichnisse per Brute Force ermitteln |
| 12 | **ffuf** | Web | Web-Fuzzing |
| 13 | **dirb** | Web | Verzeichnisse suchen |
| 14 | **wpscan** | Web | WordPress untersuchen |
| 15 | **enum4linux** | Informationsgewinnung | SMB-Ressourcen ermitteln |
| 16 | **crackmapexec** | Post-Exploitation | AD-Penetrationstests |
| 17 | **mimikatz** | Post-Exploitation | Zugangsdaten auslesen |
| 18 | **responder** | Exploitation | LLMNR-Antworten fälschen |
| 19 | **impacket** | Exploitation | Protokollwerkzeuge |
| 20 | **bloodhound** | Post-Exploitation | AD-Strukturen erfassen |
| 21 | **netcat** | Hilfsprogramme | TCP-/UDP-Hilfsprogramm |
| 22 | **socat** | Hilfsprogramme | Daten weiterleiten |
| 23 | **tcpdump** | Paketmitschnitt | Netzwerkpakete mitschneiden |
| 24 | **ettercap** | Paketmitschnitt | Man-in-the-Middle-Angriffe |
| 25 | **bettercap** | Paketmitschnitt | Netzwerkangriffe |
| 26 | **proxychains** | Hilfsprogramme | Proxy-Ketten |
| 27 | **msfvenom** | Exploitation | Payloads erzeugen |
| 28 | **searchsploit** | Exploitation | Exploits suchen |
| 29 | **theHarvester** | OSINT | E-Mail-Adressen sammeln |
| 30 | **maltego** | OSINT | Beziehungen analysieren |
| 31 | **recon-ng** | OSINT | Framework zur Erkundung |
| 32 | **amass** | OSINT | Subdomains ermitteln |
| 33 | **subfinder** | OSINT | Subdomains finden |
| 34 | **nuclei** | Schwachstellen | Scanner mit Vorlagen |
| 35 | **zaproxy** | Web | OWASP ZAP |
| 36 | **wifite** | Funknetze | Automatisierte WLAN-Prüfung |
| 37 | **ghidra** | Reverse Engineering | Reverse Engineering |
| 38 | **radare2** | Reverse Engineering | Binärdateien analysieren |
| 39 | **gdb** | Reverse Engineering | Debugger |
| 40 | **binwalk** | Reverse Engineering | Firmware analysieren |
| 41 | **volatility** | Forensik | Arbeitsspeicher analysieren |
| 42 | **autopsy** | Forensik | Forensikplattform |
| 43 | **foremost** | Forensik | Dateien anhand ihrer Datenstrukturen rekonstruieren |
| 44 | **setoolkit** | Social Engineering | Social-Engineering-Werkzeugsammlung |
| 45 | **beef-xss** | Web | Browser-Exploits |
| 46 | **linpeas** | Rechteausweitung | Linux-Systeme enumerieren |
| 47 | **winpeas** | Rechteausweitung | Windows-Systeme enumerieren |
| 48 | **chisel** | Tunneling | HTTP-Tunnel |
| 49 | **sshuttle** | Tunneling | VPN über SSH |
| 50 | **evil-winrm** | Post-Exploitation | WinRM-Shell |

---

## 📚 Ressourcen

- [Kali-Linux-Werkzeuge](https://www.kali.org/tools/)
- [Kali-Dokumentation](https://www.kali.org/docs/)
- [Kali-Metapakete](https://www.kali.org/docs/general-use/metapackages/)

---

## 📝 Hinweise zur Einordnung und zu Versionen

Die Tabellen bilden die Auswahl und Bezeichnungen des englischen Originals ab (Quellenprüfung: 02.11.2026):

- **Umfang und Verfügbarkeit:** „600+“ ist keine Zählung der Werkzeuge auf dieser Seite. Die Tabellen enthalten eine Auswahl mit Mehrfachnennungen und keine vollständige aktuelle Kali-Paketliste. Die 14 Kategorien sind die Gliederung dieses Leitfadens. Installierte Werkzeuge hängen vom Abbild, der Architektur und den gewählten Metapaketen ab; eine Nennung garantiert keine Standardinstallation. OSINT bedeutet Recherche in öffentlich zugänglichen Quellen, Enumeration das systematische Ermitteln von Ressourcen. [Kali-Werkzeugkatalog](https://www.kali.org/tools/), [Kali-Metapakete](https://www.kali.org/docs/general-use/metapackages/).
- **Plattformen und Lizenzen:** Die Auswahl enthält auch Werkzeuge für andere Plattformen sowie kommerzielle Produkte. Beispielsweise ist x64dbg ein Windows-Debugger und Cobalt Strike ein separat angebotenes kommerzielles Produkt. Daraus lässt sich keine freie, native oder vorinstallierte Verfügbarkeit unter Kali ableiten. [x64dbg-Projekt](https://x64dbg.com/), [Cobalt Strike](https://www.cobaltstrike.com/product).
- **Geänderte oder ungenaue Namen:** Cutter verwendet inzwischen **Rizin** als Kern; „Radare2 GUI“ ist eine historische Beschreibung. Der Eintrag `xssstrike` bezeichnet offenbar **XSStrike**; die offizielle Projektdatei heißt `xsstrike.py`. Die ursprünglichen Tabellennamen bleiben zur Zuordnung erhalten. [Cutter-Projekt](https://cutter.re/), [XSStrike-Projekt](https://github.com/s0md3v/XSStrike).
- **Funktionsgruppen:** Die Rubrik „Web-Crawler“ ist weit gefasst: CutyCapt erstellt Abbildungen gerenderter Webseiten; die dort genannten Verzeichniswerkzeuge dienen vor allem der Inhaltssuche. Proxychains-ng leitet TCP-Verbindungen geeigneter, dynamisch gelinkter Programme über Proxys um und ist kein HTTP-Analyseproxy wie Burp oder ZAP; UDP und ICMP werden nicht unterstützt. [CutyCapt-Dokumentation](https://www.kali.org/tools/cutycapt/), [Proxychains-ng-Dokumentation](https://www.kali.org/tools/proxychains-ng/).
- **Ranglisten:** Formulierungen wie „beliebtester“, „schnellster“ und „die 50 wichtigsten“ übernehmen Wertungen des Originals. Diese Seite liefert dafür keine Messmethode oder vergleichbaren Benchmarks; sie sind keine belastbare Leistungsrangfolge.

---

<p align="center">
  <b>🐉 Kali Linux beherrschen!</b><br>
  <i>Über 600 Werkzeuge griffbereit</i>
</p>
