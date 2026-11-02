# 🐉 Kali Linux-verktøy – fullstendig referanse (600+ verktøy)

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗  ██╗ █████╗ ██╗     ██╗    ██╗     ██╗███╗   ██╗██╗   ██╗██╗  ██╗
  ██║ ██╔╝██╔══██╗██║     ██║    ██║     ██║████╗  ██║██║   ██║╚██╗██╔╝
  █████╔╝ ███████║██║     ██║    ██║     ██║██╔██╗ ██║██║   ██║ ╚███╔╝ 
  ██╔═██╗ ██╔══██║██║     ██║    ██║     ██║██║╚██╗██║██║   ██║ ██╔██╗ 
  ██║  ██╗██║  ██║███████╗██║    ███████╗██║██║ ╚████║╚██████╔╝██╔╝ ██╗
  ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝    ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝  ╚═╝
                    REFERANSEGUIDE FOR VERKTØY
```

<p align="center">
  <img src="https://img.shields.io/badge/Kali_Linux-Tools-557C94?style=for-the-badge&logo=kalilinux" alt="Kali">
  <img src="https://img.shields.io/badge/600+-Tools-red?style=for-the-badge" alt="Verktøy">
  <img src="https://img.shields.io/badge/14-Categories-blue?style=for-the-badge" alt="Kategorier">
</p>

---

## 📋 Innhold

- [Informasjonsinnhenting](#1--informasjonsinnhenting)
- [Sårbarhetsanalyse](#2--sårbarhetsanalyse)
- [Analyse av webapplikasjoner](#3--analyse-av-webapplikasjoner)
- [Sikkerhetsvurdering av databaser](#4--sikkerhetsvurdering-av-databaser)
- [Passordangrep](#5--passordangrep)
- [Angrep mot trådløse nettverk](#6--angrep-mot-trådløse-nettverk)
- [Omvendt utvikling (reverse engineering)](#7--omvendt-utvikling-reverse-engineering)
- [Verktøy for sårbarhetsutnyttelse](#8--verktøy-for-sårbarhetsutnyttelse)
- [Avlytting og forfalskning](#9--avlytting-og-forfalskning)
- [Arbeid etter innbrudd (post-exploitation)](#10--arbeid-etter-innbrudd-post-exploitation)
- [Digital etterforskning](#11--digital-etterforskning)
- [Rapporteringsverktøy](#12--rapporteringsverktøy)
- [Sosial manipulering (social engineering)](#13--sosial-manipulering-social-engineering)
- [Maskinvarehacking](#14--maskinvarehacking)
- [📊 Hurtigreferanse – 50 sentrale verktøy](#-hurtigreferanse--50-sentrale-verktøy)
- [📚 Ressurser](#-ressurser)

---

> **Merknad om omfang og versjoner:** «600+» er originalens beskrivelse av Kali-verktøyutvalget; tabellene her er et utvalg. Verktøy, pakkenavn og kategorier kan endres, og alt er ikke forhåndsinstallert eller tilgjengelig på alle plattformer. Installerte verktøy avhenger blant annet av valgte metapakker. Se [Kalis verktøykatalog](https://www.kali.org/tools/) og [dokumentasjonen om metapakker](https://www.kali.org/docs/general-use/metapackages/).

> **Begreper:** OSINT er informasjonsinnhenting fra åpne kilder. Enumeration er systematisk kartlegging; brute force er systematisk gjetting; fuzzing tester mange ulike inndata. En exploit utnytter en sårbarhet, og en payload er nyttelasten som kjøres eller leveres. MitM betyr mellommannsangrep. Hastighetsrangeringer er påstander fra originalen, ikke målte sammenligninger her.

## 1. 🔍 Informasjonsinnhenting

### DNS-analyse

| Verktøy | Beskrivelse |
|------|-------------|
| **dnsrecon** | Skript for DNS-kartlegging |
| **dnsenum** | Verktøy for DNS-kartlegging |
| **fierce** | Verktøy for innledende DNS-kartlegging |
| **dnsmap** | Systematisk gjetting av underdomener |
| **dnstracer** | Verktøy for sporing av DNS-oppslag |
| **dnswalk** | Feilsøking av DNS-databaser |

### Oppdagelse av verter

| Verktøy | Beskrivelse |
|------|-------------|
| **nmap** | Nettverkskartlegging og portskanning |
| **masscan** | Rask portskanner |
| **hping3** | Generator for nettverkspakker |
| **fping** | Rask ping-kartlegging av adresser |
| **arping** | Verktøy for ping med ARP |
| **netdiscover** | Aktiv og passiv kartlegging med ARP |
| **unicornscan** | Asynkron nettverksskanner |

### Nettverks- og portskannere

| Verktøy | Beskrivelse |
|------|-------------|
| **nmap** | Den mest populære nettverksskanneren |
| **masscan** | Den raskeste portskanneren ifølge originalen |
| **rustscan** | Moderne og rask skanner |
| **zmap** | Rask nettverksskanner som sender én pakke per mål |
| **unicornscan** | Asynkron TCP/UDP-skanner |
| **nbtscan** | Skanner for NetBIOS-navn |
| **onesixtyone** | SNMP-skanner |

### OSINT-verktøy

| Verktøy | Beskrivelse |
|------|-------------|
| **maltego** | OSINT og grafisk analyse av sammenhenger |
| **spiderfoot** | Verktøy for automatisering av OSINT |
| **recon-ng** | Rammeverk for innledende kartlegging på nettet |
| **theHarvester** | Innsamling av e-postadresser, underdomener og IP-adresser |
| **shodan** | Kommandolinjegrensesnitt for en IoT-søkemotor |
| **amass** | Kartlegging av angrepsflaten |
| **subfinder** | Verktøy for å finne underdomener |
| **assetfinder** | Finner domener og underdomener |
| **waybackurls** | Henter URL-er fra Wayback Machine |
| **gau** | Henter URL-er (Get All URLs) |

### Ruteanalyse

| Verktøy | Beskrivelse |
|------|-------------|
| **traceroute** | Sporing av nettverksruter |
| **mtr** | Verktøy for nettverksdiagnostikk |
| **netmask** | Verktøy for nettverksmasker |
| **tcptraceroute** | Rutesporing med TCP |

### SMB-analyse

| Verktøy | Beskrivelse |
|------|-------------|
| **smbclient** | SMB-klient |
| **smbmap** | Kartlegging av SMB-delinger |
| **enum4linux** | Kartlegging av Windows og Samba |
| **enum4linux-ng** | Neste generasjon av enum4linux |
| **rpcclient** | MS-RPC-klient |
| **nbtscan** | NetBIOS-skanner |

### SMTP-analyse

| Verktøy | Beskrivelse |
|------|-------------|
| **smtp-user-enum** | Kartlegging av brukere via SMTP |
| **swaks** | Testverktøy for SMTP |

### SNMP-analyse

| Verktøy | Beskrivelse |
|------|-------------|
| **snmpwalk** | Henting av data via SNMP |
| **snmpcheck** | Kartlegging av enheter via SNMP |
| **onesixtyone** | Rask SNMP-skanner |
| **braa** | SNMP-skanner for mange mål |

### SSL-analyse

| Verktøy | Beskrivelse |
|------|-------------|
| **sslscan** | SSL/TLS-skanner |
| **sslyze** | Analyseverktøy for SSL/TLS |
| **testssl.sh** | Testing av SSL/TLS |

---

## 2. 🔓 Sårbarhetsanalyse

### Sårbarhetsskannere

| Verktøy | Beskrivelse |
|------|-------------|
| **nmap** | NSE-skript for sårbarhetstesting |
| **nikto** | Skanner for webservere |
| **openvas** | Åpent verktøy for sårbarhetsvurdering |
| **legion** | Penetrasjonstesting av nettverk |
| **lynis** | Verktøy for sikkerhetsrevisjon |
| **nuclei** | Malbasert sårbarhetsskanner |

### Verktøy for fuzzing

| Verktøy | Beskrivelse |
|------|-------------|
| **wfuzz** | Fuzzer for webapplikasjoner |
| **ffuf** | Rask fuzzer for webapplikasjoner |
| **gobuster** | Systematisk gjetting av kataloger og DNS-navn |
| **dirb** | Skanner for webinnhold |
| **dirbuster** | Systematisk gjetting av katalognavn |
| **feroxbuster** | Rekursiv kartlegging av innhold |
| **spike** | Fuzzer for protokoller |
| **sfuzz** | Enkel fuzzer |

### VoIP-verktøy

| Verktøy | Beskrivelse |
|------|-------------|
| **sipvicious** | Verktøy for SIP-protokollen |
| **voiphopper** | Bytte til VoIP-VLAN (VLAN hopping) |

---

## 3. 🌐 Analyse av webapplikasjoner

### Identifisering av publiseringssystemer (CMS)

| Verktøy | Beskrivelse |
|------|-------------|
| **wpscan** | Sårbarhetsskanner for WordPress |
| **joomscan** | Skanner for Joomla |
| **droopescan** | Skanner for Drupal, WordPress og Joomla |
| **cmseek** | Identifisering og utnyttelse av CMS-er |

### Sårbarhetsskannere for webapplikasjoner

| Verktøy | Beskrivelse |
|------|-------------|
| **nikto** | Skanner for webservere |
| **burpsuite** | Sikkerhetstesting av webapplikasjoner |
| **zaproxy** | OWASP ZAP-proxy |
| **wapiti** | Sårbarhetsskanner for webapplikasjoner |
| **skipfish** | Sikkerhetsskanner for webapplikasjoner |
| **arachni** | Sikkerhetsskanner for webapplikasjoner |
| **w3af** | Rammeverk for angrep mot webapplikasjoner |
| **vega** | Sikkerhetsskanner for webapplikasjoner |

### Verktøy for gjennomgang av nettsteder (crawlere)

| Verktøy | Beskrivelse |
|------|-------------|
| **cutycapt** | Skjermbilde av nettside |
| **dirb** | Skanner for webinnhold |
| **dirbuster** | Systematisk gjetting av katalognavn |
| **gobuster** | Systematisk gjetting av kataloger og DNS-navn |
| **feroxbuster** | Rask kartlegging av innhold |

### Proxyer for webapplikasjoner

| Verktøy | Beskrivelse |
|------|-------------|
| **burpsuite** | Webproxy og skanner |
| **zaproxy** | OWASP ZAP |
| **mitmproxy** | HTTP-proxy |
| **proxychains** | Kjeder av proxyer |

### SQL-injeksjon

| Verktøy | Beskrivelse |
|------|-------------|
| **sqlmap** | Automatisering av SQL-injeksjon |
| **sqlninja** | Injeksjon mot SQL Server |
| **bbqsql** | Blind SQL-injeksjon |
| **jsql-injection** | SQL-injeksjon med Java |
| **ghauri** | Verktøy for SQL-injeksjon |

### XSS-verktøy

| Verktøy | Beskrivelse |
|------|-------------|
| **xsser** | Testing av XSS-sårbarheter |
| **xssstrike** | Avansert oppdagelse av XSS |
| **dalfox** | Parameteranalyse for XSS |

---

## 4. 💾 Sikkerhetsvurdering av databaser

| Verktøy | Beskrivelse |
|------|-------------|
| **sqlmap** | Automatisering av SQL-injeksjon |
| **sqlninja** | Injeksjon mot MS SQL |
| **jsql-injection** | Java-basert SQL-injeksjon |
| **oscanner** | Oracle-skanner |
| **tnscmd10g** | Verktøy for Oracle TNS-lytteren |
| **odat** | Verktøy for angrep mot Oracle-databaser |
| **mdbtools** | Verktøy for MS Access |
| **sidguesser** | Gjetting av Oracle SID |

---

## 5. 🔑 Passordangrep

### Frakoblede angrep (offline)

| Verktøy | Beskrivelse |
|------|-------------|
| **john** | Passordknekking med John the Ripper |
| **hashcat** | Passordknekking med GPU |
| **ophcrack** | Knekking av Windows-passord |
| **hashid** | Identifisering av hasher |
| **hash-identifier** | Identifisering av hashtyper |
| **chntpw** | Tilbakestilling av Windows-passord |
| **samdump2** | Verktøy for uttrekk fra SAM |
| **crunch** | Ordlistegenerator |
| **cewl** | Generator for egne ordlister |
| **cupp** | Profilbasert generering av passordforslag |
| **rsmangler** | Verktøy for å lage varianter av ordlister |
| **wordlists** | Samling av ordlister |

### Angrep mot aktive tjenester (online)

| Verktøy | Beskrivelse |
|------|-------------|
| **hydra** | Knekking av innlogginger mot nettverkstjenester |
| **medusa** | Parallelle innloggingsforsøk med systematisk gjetting |
| **ncrack** | Knekking av autentisering mot nettverkstjenester |
| **patator** | Verktøy for systematisk gjetting til flere formål |
| **thc-pptp-bruter** | Systematisk gjetting mot PPTP |
| **crowbar** | Verktøy for systematisk gjetting (brute force) |

### Bruk av hashen til autentisering (pass-the-hash)

| Verktøy | Beskrivelse |
|------|-------------|
| **mimikatz** | Uttrekk av påloggingsopplysninger fra Windows |
| **pth-toolkit** | Verktøysett for pass-the-hash |
| **smbmap** | SMB-kartlegging |
| **crackmapexec** | Verktøy for penetrasjonstesting av nettverk |
| **evil-winrm** | Fjernadministrasjon av Windows |

---

## 6. 📡 Angrep mot trådløse nettverk

### Bluetooth

| Verktøy | Beskrivelse |
|------|-------------|
| **bluelog** | Bluetooth-skanner |
| **bluemaho** | Sikkerhetstesting av Bluetooth |
| **blueranger** | Registrering av Bluetooth-enheter i nærheten |
| **btscanner** | Skanner for Bluetooth-enheter |
| **redfang** | Skanner for skjulte Bluetooth-enheter |
| **spooftooph** | Forfalskning av Bluetooth-identitet |

### WiFi-verktøy

| Verktøy | Beskrivelse |
|------|-------------|
| **aircrack-ng** | Verktøysamling for WiFi-sikkerhet |
| **airmon-ng** | Aktivering av overvåkingsmodus (monitor mode) |
| **airodump-ng** | Pakkefangst |
| **aireplay-ng** | Pakkeinjeksjon |
| **airbase-ng** | Falskt aksesspunkt |
| **wifite** | Automatisert sikkerhetsvurdering av WiFi |
| **fern-wifi-cracker** | Verktøy for sikkerhetsvurdering av WiFi |
| **kismet** | Oppdagelse av trådløse nettverk |
| **reaver** | Verktøy for WPS-angrep |
| **bully** | Systematisk gjetting mot WPS |
| **pixiewps** | Frakoblet angrep mot WPS |
| **wifiphisher** | Verktøy for phishing via WiFi |
| **fluxion** | WPA-angrep med mellommann (MitM) |
| **hostapd-wpe** | Innsamling av påloggingsopplysninger over trådløst nettverk |
| **cowpatty** | Knekking av WPA-PSK |
| **asleap** | Gjenfinning av LEAP/PPTP-passord |
| **eapmd5pass** | Knekking av EAP-MD5-passord |
| **mdk3** | Verktøy for angrep mot trådløse nettverk |
| **mdk4** | Verktøy for utnyttelse av trådløse nettverk |

### RFID/NFC

| Verktøy | Beskrivelse |
|------|-------------|
| **mfoc** | Frakoblet knekking av Mifare Classic |
| **mfcuk** | Universelt verktøysett for Mifare Classic |
| **mfterm** | Terminal for Mifare Classic |
| **libnfc** | NFC-bibliotek |
| **proxmark3** | Verktøy for RFID-forskning |

---

## 7. 🔧 Omvendt utvikling (reverse engineering)

### Feilsøkingsverktøy (debuggere)

| Verktøy | Beskrivelse |
|------|-------------|
| **gdb** | GNU Debugger |
| **edb-debugger** | Feilsøkingsverktøy for flere plattformer |
| **radare2** | Rammeverk for omvendt utvikling |
| **x64dbg** | Feilsøkingsverktøy for x64/x32 |
| **ollydbg** | Feilsøkingsverktøy for x86 |
| **immunity-debugger** | Feilsøkingsverktøy som kan styres med Python-skript |

### Disassemblere

> **Versjonsmerknad:** Beskrivelsen «Radare2 GUI» i originalen viser til eldre Cutter-utgaver. Dagens Cutter bygger på Rizin. Se [Cutter-prosjektet](https://github.com/rizinorg/cutter). En disassembler viser maskinkode som assemblerkode; en dekompilator forsøker å gjenskape kode på et høyere nivå.

| Verktøy | Beskrivelse |
|------|-------------|
| **ghidra** | NSA-verktøy for omvendt utvikling |
| **radare2** | Rammeverk for omvendt utvikling |
| **cutter** | Grafisk grensesnitt for Radare2 ifølge originalen (se merknaden) |
| **rizin** | Rammeverk for omvendt utvikling |
| **objdump** | Disassembler for objektfiler |
| **IDA Free** | Interaktiv disassembler |

### Andre verktøy for omvendt utvikling

| Verktøy | Beskrivelse |
|------|-------------|
| **apktool** | Verktøy for Android-APK-er |
| **dex2jar** | Konvertering fra DEX til JAR |
| **jadx** | Java-dekompilator |
| **jd-gui** | Grafisk grensesnitt for Java-dekompilering |
| **bytecode-viewer** | Visning av Java-bytekode |
| **androguard** | Analyse av Android-apper |
| **frida** | Dynamisk instrumentering |
| **angr** | Plattform for analyse av binærfiler |
| **binwalk** | Verktøy for analyse av fastvare |

---

## 8. 💣 Verktøy for sårbarhetsutnyttelse

### Rammeverk for sårbarhetsutnyttelse

| Verktøy | Beskrivelse |
|------|-------------|
| **metasploit-framework** | Rammeverk for sårbarhetsutnyttelse |
| **armitage** | Grafisk grensesnitt for Metasploit |
| **cobalt-strike** | Simulering av en angriper |
| **beef-xss** | Utnyttelse av nettlesere |
| **routersploit** | Utnyttelse av rutere |
| **exploitdb** | Database med angrepskode (exploits) |
| **searchsploit** | Søk i Exploit-DB |

### Verktøy for shellcode

| Verktøy | Beskrivelse |
|------|-------------|
| **msfvenom** | Generator for nyttelaster (payloads) |
| **veil** | Rammeverk for generering av nyttelaster |
| **shellnoob** | Verktøysett for shellcode |

### Sårbarhetsutnyttelse i nettverk

| Verktøy | Beskrivelse |
|------|-------------|
| **responder** | Forgiftning av LLMNR/NBT-NS-svar |
| **impacket** | Verktøysett for nettverksprotokoller |
| **crackmapexec** | Verktøy for arbeid etter innbrudd |
| **evil-winrm** | Kommandoskall via WinRM |
| **powershell-empire** | Rammeverk for arbeid etter innbrudd |

---

## 9. 📡 Avlytting og forfalskning

### Verktøy for nettverksavlytting

| Verktøy | Beskrivelse |
|------|-------------|
| **wireshark** | Analyse av nettverksprotokoller |
| **tcpdump** | Avlytting fra kommandolinjen |
| **tshark** | Wireshark i terminalen |
| **ettercap** | Nettverksavlytting og mellommannsangrep |
| **dsniff** | Verktøy for sikkerhetsvurdering av nettverk |
| **net-creds** | Avlytting av påloggingsopplysninger |
| **pcredz** | Uttrekk av påloggingsopplysninger |

### Verktøy for forfalskning (spoofing)

| Verktøy | Beskrivelse |
|------|-------------|
| **arpspoof** | Forfalskning av ARP-svar |
| **dnsspoof** | Forfalskning av DNS-svar |
| **macchanger** | Endring av MAC-adresse |
| **ettercap** | Forgiftning av ARP-tabeller |
| **bettercap** | Verktøy for nettverksangrep |
| **mitmproxy** | HTTP-proxy for mellommannsangrep |
| **sslstrip** | Nedgradering fra HTTPS til HTTP |

### Avlytting av VoIP

| Verktøy | Beskrivelse |
|------|-------------|
| **sipvicious** | SIP/VoIP-verktøy |
| **voiphopper** | Bytte til VoIP-VLAN (VLAN hopping) |

---

## 10. 🎯 Arbeid etter innbrudd (post-exploitation)

### Bakdører i operativsystemer

| Verktøy | Beskrivelse |
|------|-------------|
| **msfvenom** | Generator for nyttelaster (payloads) |
| **veil** | Omgåelse av antivirus |
| **shellter** | Dynamisk injeksjon av shellcode |
| **weevely** | Webskall i PHP |

### Tunnelering

| Verktøy | Beskrivelse |
|------|-------------|
| **sshuttle** | VPN via SSH-tunnel |
| **chisel** | Tunnelering over HTTP |
| **proxychains** | Kjeder av proxyer |
| **socat** | Verktøy for videresending av data |
| **plink** | PuTTY-kommandolinjeklient (PuTTY link) |
| **stunnel** | Tunnelering over TLS |
| **dns2tcp** | Tunnelering over DNS |
| **iodine** | DNS-tunnel |
| **ptunnel** | Tunnelering over ICMP |
| **pwnat** | Traversering av NAT |
| **udptunnel** | Tunnelering over UDP |

### Arbeid etter innbrudd i Windows

| Verktøy | Beskrivelse |
|------|-------------|
| **mimikatz** | Uttrekk av påloggingsopplysninger fra Windows |
| **crackmapexec** | Penetrasjonstesting av Windows og AD |
| **evil-winrm** | Kommandoskall via WinRM |
| **impacket** | Windows-protokoller |
| **bloodhound** | Innledende kartlegging av Active Directory |
| **powershell-empire** | Arbeid etter innbrudd med PowerShell |
| **rubeus** | Misbruk av Kerberos |
| **kerbrute** | Systematisk gjetting mot Kerberos |

### Arbeid etter innbrudd i Linux

| Verktøy | Beskrivelse |
|------|-------------|
| **linpeas** | Rettighetseskalering i Linux |
| **linenum** | Kartlegging av Linux |
| **pspy** | Prosessovervåking |
| **linux-exploit-suggester** | Finner angrepskode for kjernesårbarheter |

---

## 11. 🔬 Digital etterforskning

### Rammeverk for digital etterforskning

| Verktøy | Beskrivelse |
|------|-------------|
| **autopsy** | Plattform for digital etterforskning |
| **sleuthkit** | Verktøysett for digital etterforskning |
| **dff** | Rammeverk for digital etterforskning |

### Filgjenoppretting fra rådata (file carving)

| Verktøy | Beskrivelse |
|------|-------------|
| **foremost** | Verktøy for filgjenoppretting fra rådata |
| **scalpel** | Filgjenoppretting fra rådata |
| **bulk_extractor** | Uttrekk av informasjon |
| **photorec** | Gjenoppretting av bilder |
| **testdisk** | Gjenoppretting av data |

### Avbildning for digital etterforskning

| Verktøy | Beskrivelse |
|------|-------------|
| **dc3dd** | Avbildning av disker for digital etterforskning |
| **dcfldd** | Utvidet versjon av dd |
| **guymager** | Grafisk verktøy for avbildning til digital etterforskning |

### Minneetterforskning

| Verktøy | Beskrivelse |
|------|-------------|
| **volatility** | Minneetterforskning |
| **volatility3** | Minneanalyse |
| **lime** | Uttrekk av minne fra Linux |

### Nettverksetterforskning

| Verktøy | Beskrivelse |
|------|-------------|
| **wireshark** | Pakkeanalyse |
| **tcpdump** | Pakkefangst |
| **ngrep** | Mønstersøk i nettverkstrafikk (grep) |
| **NetworkMiner** | Nettverksetterforskning |

### PDF-etterforskning

| Verktøy | Beskrivelse |
|------|-------------|
| **pdfid** | PDF-skanner |
| **pdf-parser** | PDF-analyse |
| **peepdf** | Verktøy for PDF-analyse |

---

## 12. 📝 Rapporteringsverktøy

| Verktøy | Beskrivelse |
|------|-------------|
| **cutycapt** | Skjermbilder av nettsider |
| **faraday** | Samarbeidsverktøy for penetrasjonstesting |
| **maltego** | Visualisering av OSINT |
| **pipal** | Passordanalyse |
| **recordmydesktop** | Skjermopptak |
| **cherrytree** | Notatapp |
| **dradis** | Samarbeidsplattform |
| **metagoofil** | Uttrekk av metadata |
| **eyewitness** | Skjermbilde av nettsted |
| **gowitness** | Skjermbilde av nettsted |

---

## 13. 🎭 Sosial manipulering (social engineering)

| Verktøy | Beskrivelse |
|------|-------------|
| **setoolkit** | Social Engineering Toolkit |
| **gophish** | Rammeverk for phishing |
| **king-phisher** | Phishingkampanjer |
| **evilginx2** | Rammeverk for phishing med mellommannsangrep |
| **beef-xss** | Utnyttelse av nettlesere |
| **maltego** | Kartlegging av sammenhenger med OSINT |
| **theHarvester** | Innsamling av e-postadresser |
| **sherlock** | OSINT basert på brukernavn |
| **social-analyzer** | Analyse av sosiale medier |

---

## 14. 🔧 Maskinvarehacking

| Verktøy | Beskrivelse |
|------|-------------|
| **arduino** | Maskinvareplattform |
| **binwalk** | Analyse av fastvare |
| **flashrom** | Programmering av flashminne |
| **openocd** | Feilsøking direkte på brikken |
| **proxmark3** | RFID-forskning |

---

## 📊 Hurtigreferanse – 50 sentrale verktøy

| # | Verktøy | Kategori | Bruksområde |
|---|------|----------|----------|
| 1 | **nmap** | Skanning | Portskanning |
| 2 | **metasploit** | Sårbarhetsutnyttelse | Rammeverk for angrepskode |
| 3 | **burpsuite** | Web | Testing av webapplikasjoner |
| 4 | **wireshark** | Avlytting | Pakkeanalyse |
| 5 | **john** | Passord | Passordknekking |
| 6 | **hashcat** | Passord | Knekking med GPU |
| 7 | **hydra** | Passord | Systematisk gjetting mot aktive tjenester |
| 8 | **sqlmap** | Web | SQL-injeksjon |
| 9 | **aircrack-ng** | Trådløst | Knekking av WiFi-passord |
| 10 | **nikto** | Web | Skanning av webapplikasjoner |
| 11 | **gobuster** | Web | Systematisk gjetting av katalognavn |
| 12 | **ffuf** | Web | Fuzzing av webapplikasjoner |
| 13 | **dirb** | Web | Katalogskanning |
| 14 | **wpscan** | Web | Skanning av WordPress |
| 15 | **enum4linux** | Informasjon | SMB-kartlegging |
| 16 | **crackmapexec** | Etter innbrudd | Penetrasjonstesting av AD |
| 17 | **mimikatz** | Etter innbrudd | Uttrekk av påloggingsopplysninger |
| 18 | **responder** | Sårbarhetsutnyttelse | Forgiftning av LLMNR-svar |
| 19 | **impacket** | Sårbarhetsutnyttelse | Protokollverktøy |
| 20 | **bloodhound** | Etter innbrudd | AD-kartlegging |
| 21 | **netcat** | Nytteverktøy | Verktøy for TCP/UDP |
| 22 | **socat** | Nytteverktøy | Videresending av data |
| 23 | **tcpdump** | Avlytting | Pakkefangst |
| 24 | **ettercap** | Avlytting | Mellommannsangrep |
| 25 | **bettercap** | Avlytting | Nettverksangrep |
| 26 | **proxychains** | Nytteverktøy | Kjeder av proxyer |
| 27 | **msfvenom** | Sårbarhetsutnyttelse | Generering av nyttelaster |
| 28 | **searchsploit** | Sårbarhetsutnyttelse | Søk etter angrepskode |
| 29 | **theHarvester** | OSINT | Innsamling av e-postadresser |
| 30 | **maltego** | OSINT | Analyse av sammenhenger |
| 31 | **recon-ng** | OSINT | Rammeverk for innledende kartlegging |
| 32 | **amass** | OSINT | Kartlegging av underdomener |
| 33 | **subfinder** | OSINT | Oppdagelse av underdomener |
| 34 | **nuclei** | Sårbarheter | Malbasert skanner |
| 35 | **zaproxy** | Web | OWASP ZAP |
| 36 | **wifite** | Trådløst | Automatisert sikkerhetsvurdering av WiFi |
| 37 | **ghidra** | Omvendt utvikling | Omvendt utvikling |
| 38 | **radare2** | Omvendt utvikling | Analyse av binærfiler |
| 39 | **gdb** | Omvendt utvikling | Feilsøkingsverktøy |
| 40 | **binwalk** | Omvendt utvikling | Analyse av fastvare |
| 41 | **volatility** | Digital etterforskning | Minneanalyse |
| 42 | **autopsy** | Digital etterforskning | Etterforskningsplattform |
| 43 | **foremost** | Digital etterforskning | Filgjenoppretting fra rådata |
| 44 | **setoolkit** | Sosial manipulering | Verktøysett for sosial manipulering |
| 45 | **beef-xss** | Web | Utnyttelse av nettlesere |
| 46 | **linpeas** | Rettighetseskalering | Kartlegging av Linux |
| 47 | **winpeas** | Rettighetseskalering | Kartlegging av Windows |
| 48 | **chisel** | Tunnelering | HTTP-tunnel |
| 49 | **sshuttle** | Tunnelering | VPN via SSH |
| 50 | **evil-winrm** | Etter innbrudd | Kommandoskall via WinRM |

---

## 📚 Ressurser

- [Kali Linux Tools](https://www.kali.org/tools/)
- [Kali-dokumentasjon](https://www.kali.org/docs/)
- [Kali-metapakker](https://www.kali.org/docs/general-use/metapackages/)

---

<p align="center">
  <b>🐉 Mestre Kali Linux!</b><br>
  <i>600+ verktøy lett tilgjengelig</i>
</p>
