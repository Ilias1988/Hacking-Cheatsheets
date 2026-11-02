# 🐉 Outils Kali Linux — référence complète (600+ outils)

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗  ██╗ █████╗ ██╗     ██╗    ██╗     ██╗███╗   ██╗██╗   ██╗██╗  ██╗
  ██║ ██╔╝██╔══██╗██║     ██║    ██║     ██║████╗  ██║██║   ██║╚██╗██╔╝
  █████╔╝ ███████║██║     ██║    ██║     ██║██╔██╗ ██║██║   ██║ ╚███╔╝ 
  ██╔═██╗ ██╔══██║██║     ██║    ██║     ██║██║╚██╗██║██║   ██║ ██╔██╗ 
  ██║  ██╗██║  ██║███████╗██║    ███████╗██║██║ ╚████║╚██████╔╝██╔╝ ██╗
  ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝    ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝  ╚═╝
                    GUIDE DE RÉFÉRENCE DES OUTILS
```

<p align="center">
  <img src="https://img.shields.io/badge/Kali_Linux-Tools-557C94?style=for-the-badge&logo=kalilinux" alt="Kali">
  <img src="https://img.shields.io/badge/600+-Tools-red?style=for-the-badge" alt="Outils">
  <img src="https://img.shields.io/badge/14-Categories-blue?style=for-the-badge" alt="Catégories">
</p>

---

## 📋 Table des matières

- [Collecte d’informations](#1--collecte-dinformations)
- [Analyse des vulnérabilités](#2--analyse-des-vulnérabilités)
- [Analyse des applications web](#3--analyse-des-applications-web)
- [Évaluation des bases de données](#4--évaluation-des-bases-de-données)
- [Attaques sur les mots de passe](#5--attaques-sur-les-mots-de-passe)
- [Attaques sur les réseaux sans fil](#6--attaques-sur-les-réseaux-sans-fil)
- [Rétro-ingénierie](#7--rétro-ingénierie)
- [Outils d’exploitation](#8--outils-dexploitation)
- [Capture et usurpation réseau](#9--capture-et-usurpation-réseau)
- [Post-exploitation](#10--post-exploitation)
- [Investigation numérique](#11--investigation-numérique)
- [Outils de compte rendu](#12--outils-de-compte-rendu)
- [Ingénierie sociale](#13--ingénierie-sociale)
- [Sécurité offensive matérielle](#14--sécurité-offensive-matérielle)
- [Référence rapide — 50 outils mis en avant](#-référence-rapide--50-outils-mis-en-avant)
- [Ressources](#-ressources)

---

## 1. 🔍 Collecte d’informations

> **Portée du catalogue :** « 600+ » et les 14 catégories reprennent l’intitulé de l’original. Cette sélection comporte des doublons et ne constitue pas un inventaire de 600 outils distincts ou préinstallés. Paquets, exécutables et catégories évoluent ; leur présence dépend aussi des métapaquets et de l’installation ([catalogue Kali](https://www.kali.org/tools/), [métapaquets](https://www.kali.org/docs/general-use/metapackages/)).

Les termes OSINT désignent le renseignement en sources ouvertes ; le fuzzing consiste à varier les entrées pour observer le comportement d’un programme. Un framework est un ensemble structuré d’outils ou de bibliothèques ; un firmware est un micrologiciel. Les noms d’outils sont conservés, y compris les graphies historiques du texte anglais.

### Analyse DNS

| Outil | Description |
|------|-------------|
| **dnsrecon** | Script d’énumération DNS |
| **dnsenum** | Outil d’énumération DNS |
| **fierce** | Outil de reconnaissance DNS |
| **dnsmap** | Recherche de sous-domaines par essais de noms |
| **dnstracer** | Outil de traçage DNS |
| **dnswalk** | Débogueur de bases DNS |

### Découverte d’hôtes

| Outil | Description |
|------|-------------|
| **nmap** | Cartographie réseau et analyse de ports |
| **masscan** | Analyse rapide de ports |
| **hping3** | Générateur de paquets réseau |
| **fping** | Balayage rapide par requêtes ping |
| **arping** | Utilitaire de requêtes ARP |
| **netdiscover** | Reconnaissance ARP active ou passive |
| **unicornscan** | Analyseur réseau asynchrone |

### Analyseurs réseau et de ports

| Outil | Description |
|------|-------------|
| **nmap** | Analyseur réseau très répandu |
| **masscan** | Analyseur de ports présenté comme le plus rapide |
| **rustscan** | Analyseur rapide et moderne |
| **zmap** | Analyse réseau rapide à paquet unique |
| **unicornscan** | Analyseur TCP/UDP asynchrone |
| **nbtscan** | Recherche de noms NetBIOS |
| **onesixtyone** | Analyseur SNMP |

### Outils de renseignement en sources ouvertes (OSINT)

| Outil | Description |
|------|-------------|
| **maltego** | Renseignement en sources ouvertes et analyse graphique des liens |
| **spiderfoot** | Automatisation du renseignement en sources ouvertes |
| **recon-ng** | Framework de reconnaissance web |
| **theHarvester** | Collecte d’adresses e-mail, de sous-domaines et d’adresses IP |
| **shodan** | Interface en ligne de commande du moteur de recherche IoT |
| **amass** | Cartographie de la surface d’attaque |
| **subfinder** | Outil de découverte de sous-domaines |
| **assetfinder** | Recherche de domaines et de sous-domaines |
| **waybackurls** | Récupération d’URL depuis la Wayback Machine |
| **gau** | Collecte d’URL (Get All URLs) |

### Analyse des routes

| Outil | Description |
|------|-------------|
| **traceroute** | Traçage des routes réseau |
| **mtr** | Outil de diagnostic réseau |
| **netmask** | Outil de manipulation des masques réseau |
| **tcptraceroute** | Traçage de route avec TCP |

### Analyse SMB

| Outil | Description |
|------|-------------|
| **smbclient** | Client SMB |
| **smbmap** | Énumération des partages SMB |
| **enum4linux** | Énumération Windows/Samba |
| **enum4linux-ng** | Nouvelle génération d’enum4linux |
| **rpcclient** | Client MS-RPC |
| **nbtscan** | Analyseur NetBIOS |

### Analyse SMTP

| Outil | Description |
|------|-------------|
| **smtp-user-enum** | Énumération des utilisateurs SMTP |
| **swaks** | Outil de test SMTP |

### Analyse SNMP

| Outil | Description |
|------|-------------|
| **snmpwalk** | Récupération de données SNMP |
| **snmpcheck** | Énumération des appareils SNMP |
| **onesixtyone** | Analyse rapide SNMP |
| **braa** | Analyse SNMP à grande échelle |

### Analyse SSL/TLS

| Outil | Description |
|------|-------------|
| **sslscan** | Analyseur SSL/TLS |
| **sslyze** | Outil d’analyse SSL/TLS |
| **testssl.sh** | Tests SSL/TLS |

---

## 2. 🔓 Analyse des vulnérabilités

### Analyseurs de vulnérabilités

| Outil | Description |
|------|-------------|
| **nmap** | Scripts NSE de recherche de vulnérabilités |
| **nikto** | Analyseur de serveurs web |
| **openvas** | Évaluation ouverte des vulnérabilités |
| **legion** | Tests d’intrusion réseau |
| **lynis** | Outil d’audit de sécurité |
| **nuclei** | Analyseur de vulnérabilités fondé sur des modèles |

### Outils de fuzzing

| Outil | Description |
|------|-------------|
| **wfuzz** | Fuzzer web (variation des entrées) |
| **ffuf** | Fuzzer web rapide |
| **gobuster** | Recherche de répertoires et de noms DNS par essais |
| **dirb** | Analyseur de contenu web |
| **dirbuster** | Recherche de répertoires par essais de noms |
| **feroxbuster** | Découverte récursive de contenu |
| **spike** | Fuzzer de protocoles |
| **sfuzz** | Fuzzer simple |

### Outils VoIP

| Outil | Description |
|------|-------------|
| **sipvicious** | Outils pour le protocole SIP |
| **voiphopper** | Saut entre VLAN dans un contexte VoIP |

---

## 3. 🌐 Analyse des applications web

### Identification des CMS

| Outil | Description |
|------|-------------|
| **wpscan** | Analyseur de vulnérabilités WordPress |
| **joomscan** | Analyseur Joomla |
| **droopescan** | Analyseur Drupal/WordPress/Joomla |
| **cmseek** | Détection et exploitation de CMS |

### Analyseurs de vulnérabilités web

| Outil | Description |
|------|-------------|
| **nikto** | Analyseur de serveurs web |
| **burpsuite** | Tests de sécurité des applications web |
| **zaproxy** | Proxy OWASP ZAP |
| **wapiti** | Analyseur de vulnérabilités web |
| **skipfish** | Analyseur de sécurité des applications web |
| **arachni** | Analyseur de sécurité des applications web |
| **w3af** | Framework d’attaque des applications web |
| **vega** | Analyseur de sécurité web |

### Exploration de contenu web

> **Précision de classement :** les outils de cette rubrique n’explorent pas tous les liens comme un crawler. CutyCapt capture le rendu d’une page ; les outils de recherche de répertoires découvrent du contenu par essais de chemins ([fiche CutyCapt](https://www.kali.org/tools/cutycapt/)).

| Outil | Description |
|------|-------------|
| **cutycapt** | Capture d’écran de page web |
| **dirb** | Analyseur de contenu web |
| **dirbuster** | Recherche exhaustive de répertoires |
| **gobuster** | Recherche de répertoires et de noms DNS par essais |
| **feroxbuster** | Découverte rapide de contenu |

### Proxies pour les applications web

| Outil | Description |
|------|-------------|
| **burpsuite** | Proxy et analyseur web |
| **zaproxy** | OWASP ZAP |
| **mitmproxy** | Proxy HTTP |
| **proxychains** | Chaînage de proxies |

### Injection SQL

| Outil | Description |
|------|-------------|
| **sqlmap** | Automatisation des injections SQL |
| **sqlninja** | Injection visant SQL Server |
| **bbqsql** | Injection SQL à l’aveugle |
| **jsql-injection** | Injection SQL avec un outil Java |
| **ghauri** | Outil d’injection SQL |

### Outils XSS

| Outil | Description |
|------|-------------|
| **xsser** | Testeur de vulnérabilités XSS |
| **xssstrike** | Détection avancée des XSS |
| **dalfox** | Analyse de paramètres pour les XSS |

---

## 4. 💾 Évaluation des bases de données

| Outil | Description |
|------|-------------|
| **sqlmap** | Automatisation des injections SQL |
| **sqlninja** | Injection visant Microsoft SQL |
| **jsql-injection** | Injection SQL avec un outil Java |
| **oscanner** | Analyseur Oracle |
| **tnscmd10g** | Outil pour le listener TNS d’Oracle |
| **odat** | Outil d’attaque de bases Oracle |
| **mdbtools** | Outils Microsoft Access |
| **sidguesser** | Recherche d’identifiants SID Oracle par essais |

---

## 5. 🔑 Attaques sur les mots de passe

### Attaques hors ligne

| Outil | Description |
|------|-------------|
| **john** | Recherche de mots de passe avec John the Ripper |
| **hashcat** | Recherche de mots de passe avec accélération GPU |
| **ophcrack** | Recherche de mots de passe Windows |
| **hashid** | Identification d’empreintes (hashes) |
| **hash-identifier** | Identification du type d’empreinte |
| **chntpw** | Réinitialisation de mots de passe Windows |
| **samdump2** | Utilitaire d’extraction de la SAM |
| **crunch** | Générateur de listes de mots |
| **cewl** | Générateur de listes de mots personnalisées |
| **cupp** | Création de profils de mots de passe utilisateur |
| **rsmangler** | Transformation et variation de listes de mots |
| **wordlists** | Collection de listes de mots |

### Attaques en ligne

| Outil | Description |
|------|-------------|
| **hydra** | Recherche d’identifiants de connexion réseau |
| **medusa** | Essais exhaustifs de connexion en parallèle |
| **ncrack** | Recherche de données d’authentification réseau |
| **patator** | Outil d’essais exhaustifs polyvalent |
| **thc-pptp-bruter** | Essais exhaustifs sur PPTP |
| **crowbar** | Outil d’essais exhaustifs |

### Authentification par empreinte (Pass-the-Hash)

| Outil | Description |
|------|-------------|
| **mimikatz** | Extraction d’identifiants Windows |
| **pth-toolkit** | Boîte à outils Pass-the-Hash |
| **smbmap** | Énumération SMB |
| **crackmapexec** | Outil de test d’intrusion réseau |
| **evil-winrm** | Administration Windows à distance |

---

## 6. 📡 Attaques sur les réseaux sans fil

### Bluetooth

| Outil | Description |
|------|-------------|
| **bluelog** | Analyseur Bluetooth |
| **bluemaho** | Tests de sécurité Bluetooth |
| **blueranger** | Détection de proximité Bluetooth |
| **btscanner** | Recherche d’appareils Bluetooth |
| **redfang** | Recherche d’appareils Bluetooth masqués |
| **spooftooph** | Usurpation Bluetooth |

### Outils Wi-Fi

| Outil | Description |
|------|-------------|
| **aircrack-ng** | Suite de test de sécurité Wi-Fi |
| **airmon-ng** | Activation du mode moniteur |
| **airodump-ng** | Capture de paquets |
| **aireplay-ng** | Injection de paquets |
| **airbase-ng** | Faux point d’accès |
| **wifite** | Audit Wi-Fi automatisé |
| **fern-wifi-cracker** | Outil d’audit Wi-Fi |
| **kismet** | Détection de réseaux sans fil |
| **reaver** | Outil d’attaque WPS |
| **bully** | Essais exhaustifs WPS |
| **pixiewps** | Attaque WPS hors ligne |
| **wifiphisher** | Outil d’hameçonnage Wi-Fi |
| **fluxion** | Attaque de l’intercepteur (MITM) dans un contexte WPA |
| **hostapd-wpe** | Collecte d’identifiants sur les réseaux sans fil |
| **cowpatty** | Recherche de clés WPA-PSK |
| **asleap** | Récupération de mots de passe LEAP/PPTP |
| **eapmd5pass** | Recherche de mots de passe EAP-MD5 |
| **mdk3** | Outil d’attaque des réseaux sans fil |
| **mdk4** | Outil d’exploitation des réseaux sans fil |

### RFID/NFC

| Outil | Description |
|------|-------------|
| **mfoc** | Recherche de clés Mifare Classic hors ligne |
| **mfcuk** | Boîte à outils universelle Mifare Classic |
| **mfterm** | Terminal pour Mifare Classic |
| **libnfc** | Bibliothèque NFC |
| **proxmark3** | Outil de recherche RFID |

---

## 7. 🔧 Rétro-ingénierie

### Débogueurs

| Outil | Description |
|------|-------------|
| **gdb** | Débogueur GNU |
| **edb-debugger** | Débogueur multiplateforme |
| **radare2** | Framework de rétro-ingénierie |
| **x64dbg** | Débogueur x64/x32 |
| **ollydbg** | Débogueur x86 |
| **immunity-debugger** | Débogueur automatisable avec Python |

### Désassembleurs

> **Version des descriptions :** Cutter repose désormais sur Rizin ; la mention Radare2 du tableau est historique. Les identifiants restent ceux de l’original ([projet officiel](https://github.com/rizinorg/cutter)).

| Outil | Description |
|------|-------------|
| **ghidra** | Outil de rétro-ingénierie de la NSA |
| **radare2** | Framework de rétro-ingénierie |
| **cutter** | Interface graphique de Radare2, selon l’original |
| **rizin** | Framework de rétro-ingénierie |
| **objdump** | Désassembleur de fichiers objets |
| **IDA Free** | Désassembleur interactif |

### Autres outils de rétro-ingénierie

| Outil | Description |
|------|-------------|
| **apktool** | Outil pour les APK Android |
| **dex2jar** | Convertisseur DEX vers JAR |
| **jadx** | Décompilateur Java |
| **jd-gui** | Interface graphique de décompilation Java |
| **bytecode-viewer** | Visualisation du bytecode Java |
| **androguard** | Analyse d’applications Android |
| **frida** | Instrumentation dynamique |
| **angr** | Plateforme d’analyse binaire |
| **binwalk** | Outil d’analyse de firmware |

---

## 8. 💣 Outils d’exploitation

> **Disponibilité :** une mention dans ce guide ne prouve pas qu’un outil est un exécutable Kali natif ou préinstallé. Par exemple, x64dbg cible Windows et Cobalt Strike fait l’objet d’une licence commerciale distincte ([x64dbg officiel](https://github.com/x64dbg/x64dbg), [éditeur Cobalt Strike](https://www.cobaltstrike.com/product/quote-request)). Kali documente aussi NetExec comme continuation de CrackMapExec, sans que les noms historiques des tableaux soient remplacés ([Kali](https://www.kali.org/tools/netexec/)).

### Frameworks d’exploitation

| Outil | Description |
|------|-------------|
| **metasploit-framework** | Framework d’exploitation |
| **armitage** | Interface graphique de Metasploit |
| **cobalt-strike** | Simulation d’adversaires |
| **beef-xss** | Exploitation de navigateurs |
| **routersploit** | Exploitation de routeurs |
| **exploitdb** | Base de données d’exploits |
| **searchsploit** | Recherche dans Exploit-DB |

### Outils de shellcode

| Outil | Description |
|------|-------------|
| **msfvenom** | Générateur de charges utiles (payloads) |
| **veil** | Framework de génération de charges utiles |
| **shellnoob** | Boîte à outils pour le shellcode |

### Exploitation réseau

| Outil | Description |
|------|-------------|
| **responder** | Empoisonnement LLMNR/NBT-NS |
| **impacket** | Boîte à outils pour les protocoles réseau |
| **crackmapexec** | Outil de post-exploitation |
| **evil-winrm** | Console WinRM |
| **powershell-empire** | Framework de post-exploitation |

---

## 9. 📡 Capture et usurpation réseau

### Outils de capture réseau

| Outil | Description |
|------|-------------|
| **wireshark** | Analyseur de protocoles réseau |
| **tcpdump** | Capture de trafic en ligne de commande |
| **tshark** | Wireshark en mode terminal |
| **ettercap** | Capture réseau et interposition MITM |
| **dsniff** | Outils d’audit réseau |
| **net-creds** | Capture d’identifiants sur le réseau |
| **pcredz** | Extraction d’identifiants |

### Outils d’usurpation

| Outil | Description |
|------|-------------|
| **arpspoof** | Usurpation ARP |
| **dnsspoof** | Usurpation DNS |
| **macchanger** | Modification d’adresse MAC |
| **ettercap** | Empoisonnement ARP |
| **bettercap** | Outil d’attaque réseau |
| **mitmproxy** | Proxy HTTP d’interposition MITM |
| **sslstrip** | Rétrogradation HTTPS |

### Capture VoIP

| Outil | Description |
|------|-------------|
| **sipvicious** | Outils SIP/VoIP |
| **voiphopper** | Saut entre VLAN dans un contexte VoIP |

---

## 10. 🎯 Post-exploitation

### Portes dérobées système

| Outil | Description |
|------|-------------|
| **msfvenom** | Générateur de charges utiles (payloads) |
| **veil** | Contournement de l’antivirus |
| **shellter** | Injection dynamique de shellcode |
| **weevely** | Console web PHP |

### Création de tunnels

| Outil | Description |
|------|-------------|
| **sshuttle** | VPN utilisant un tunnel SSH |
| **chisel** | Création de tunnels HTTP |
| **proxychains** | Chaînage de proxies |
| **socat** | Outil de relais |
| **plink** | Client de connexion PuTTY |
| **stunnel** | Création de tunnels TLS |
| **dns2tcp** | Création de tunnels DNS |
| **iodine** | Tunnel DNS |
| **ptunnel** | Création de tunnels ICMP |
| **pwnat** | Traversée de NAT |
| **udptunnel** | Création de tunnels UDP |

### Post-exploitation Windows

| Outil | Description |
|------|-------------|
| **mimikatz** | Extraction d’identifiants Windows |
| **crackmapexec** | Tests d’intrusion Windows/AD |
| **evil-winrm** | Console WinRM |
| **impacket** | Protocoles Windows |
| **bloodhound** | Reconnaissance Active Directory |
| **powershell-empire** | Post-exploitation avec PowerShell |
| **rubeus** | Abus de Kerberos |
| **kerbrute** | Essais exhaustifs Kerberos |

### Post-exploitation Linux

| Outil | Description |
|------|-------------|
| **linpeas** | Élévation de privilèges Linux |
| **linenum** | Énumération Linux |
| **pspy** | Surveillance des processus |
| **linux-exploit-suggester** | Recherche d’exploits du noyau |

---

## 11. 🔬 Investigation numérique

### Frameworks d’investigation numérique

| Outil | Description |
|------|-------------|
| **autopsy** | Plateforme d’investigation numérique |
| **sleuthkit** | Boîte à outils d’investigation numérique |
| **dff** | Framework d’investigation numérique |

### Récupération de fichiers par signatures

| Outil | Description |
|------|-------------|
| **foremost** | Outil de récupération de fichiers par signatures |
| **scalpel** | Récupération de fichiers par signatures |
| **bulk_extractor** | Extraction d’informations |
| **photorec** | Récupération de photos |
| **testdisk** | Récupération de données |

### Création d’images forensiques

| Outil | Description |
|------|-------------|
| **dc3dd** | Création d’images de disque pour l’investigation |
| **dcfldd** | Version enrichie de dd |
| **guymager** | Interface graphique de création d’images forensiques |

### Investigation de la mémoire

| Outil | Description |
|------|-------------|
| **volatility** | Investigation de la mémoire |
| **volatility3** | Analyse de la mémoire |
| **lime** | Extraction de la mémoire Linux |

### Investigation réseau

| Outil | Description |
|------|-------------|
| **wireshark** | Analyse de paquets |
| **tcpdump** | Capture de paquets |
| **ngrep** | Recherche de motifs dans le trafic réseau |
| **NetworkMiner** | Investigation réseau |

### Investigation PDF

| Outil | Description |
|------|-------------|
| **pdfid** | Analyseur PDF |
| **pdf-parser** | Analyse PDF |
| **peepdf** | Outil d’analyse PDF |

---

## 12. 📝 Outils de compte rendu

| Outil | Description |
|------|-------------|
| **cutycapt** | Captures d’écran de pages web |
| **faraday** | Outil collaboratif de test d’intrusion |
| **maltego** | Visualisation du renseignement en sources ouvertes |
| **pipal** | Analyse de mots de passe |
| **recordmydesktop** | Enregistrement d’écran |
| **cherrytree** | Application de prise de notes |
| **dradis** | Plateforme de collaboration |
| **metagoofil** | Extraction de métadonnées |
| **eyewitness** | Capture d’écran de site web |
| **gowitness** | Capture d’écran de site web |

---

## 13. 🎭 Ingénierie sociale

| Outil | Description |
|------|-------------|
| **setoolkit** | Boîte à outils d’ingénierie sociale |
| **gophish** | Framework d’hameçonnage |
| **king-phisher** | Campagne d’hameçonnage |
| **evilginx2** | Framework d’hameçonnage par interposition MITM |
| **beef-xss** | Exploitation de navigateurs |
| **maltego** | Relations issues du renseignement en sources ouvertes |
| **theHarvester** | Collecte d’adresses e-mail |
| **sherlock** | Recherche de noms d’utilisateurs en sources ouvertes |
| **social-analyzer** | Analyse des réseaux sociaux |

---

## 14. 🔧 Sécurité offensive matérielle

| Outil | Description |
|------|-------------|
| **arduino** | Plateforme matérielle |
| **binwalk** | Analyse de firmware |
| **flashrom** | Programmation de mémoire flash |
| **openocd** | Débogage sur puce |
| **proxmark3** | Recherche RFID |

---

## 📊 Référence rapide — 50 outils mis en avant

> **Précision :** le classement « Top 50 » et les qualificatifs comme « le plus rapide » proviennent du texte original ; ils ne constituent pas un benchmark. Une détection ou une suggestion de vulnérabilité reste une piste à vérifier, pas une garantie d’exploitation. La portée des fonctions dépend des versions, de la configuration et des droits.

| # | Outil | Catégorie | Utilisation |
|---|------|----------|----------|
| 1 | **nmap** | Analyse réseau | Analyse de ports |
| 2 | **metasploit** | Exploitation | Framework d’exploitation |
| 3 | **burpsuite** | Web | Tests web |
| 4 | **wireshark** | Capture réseau | Analyse de paquets |
| 5 | **john** | Mots de passe | Recherche de mots de passe |
| 6 | **hashcat** | Mots de passe | Recherche avec accélération GPU |
| 7 | **hydra** | Mots de passe | Essais exhaustifs en ligne |
| 8 | **sqlmap** | Web | Injection SQL |
| 9 | **aircrack-ng** | Sans fil | Recherche de clés Wi-Fi |
| 10 | **nikto** | Web | Analyse web |
| 11 | **gobuster** | Web | Recherche exhaustive de répertoires |
| 12 | **ffuf** | Web | Fuzzing web |
| 13 | **dirb** | Web | Analyse de répertoires |
| 14 | **wpscan** | Web | Analyse WordPress |
| 15 | **enum4linux** | Collecte d’informations | Énumération SMB |
| 16 | **crackmapexec** | Post-exploitation | Tests d’intrusion AD |
| 17 | **mimikatz** | Post-exploitation | Extraction d’identifiants |
| 18 | **responder** | Exploitation | Empoisonnement LLMNR |
| 19 | **impacket** | Exploitation | Outils de protocoles |
| 20 | **bloodhound** | Post-exploitation | Cartographie AD |
| 21 | **netcat** | Utilitaires | Utilitaire TCP/UDP |
| 22 | **socat** | Utilitaires | Relais de données |
| 23 | **tcpdump** | Capture réseau | Capture de paquets |
| 24 | **ettercap** | Capture réseau | Attaques de l’intercepteur (MITM) |
| 25 | **bettercap** | Capture réseau | Attaques réseau |
| 26 | **proxychains** | Utilitaires | Chaînage de proxies |
| 27 | **msfvenom** | Exploitation | Génération de charges utiles |
| 28 | **searchsploit** | Exploitation | Recherche d’exploits |
| 29 | **theHarvester** | OSINT | Collecte d’adresses e-mail |
| 30 | **maltego** | OSINT | Analyse de liens |
| 31 | **recon-ng** | OSINT | Framework de reconnaissance |
| 32 | **amass** | OSINT | Énumération de sous-domaines |
| 33 | **subfinder** | OSINT | Recherche de sous-domaines |
| 34 | **nuclei** | Vulnérabilités | Analyseur fondé sur des modèles |
| 35 | **zaproxy** | Web | OWASP ZAP |
| 36 | **wifite** | Sans fil | Audit Wi-Fi automatisé |
| 37 | **ghidra** | Rétro-ingénierie | Rétro-ingénierie |
| 38 | **radare2** | Rétro-ingénierie | Analyse binaire |
| 39 | **gdb** | Rétro-ingénierie | Débogueur |
| 40 | **binwalk** | Rétro-ingénierie | Analyse de firmware |
| 41 | **volatility** | Investigation numérique | Analyse de la mémoire |
| 42 | **autopsy** | Investigation numérique | Plateforme d’investigation numérique |
| 43 | **foremost** | Investigation numérique | Récupération par signatures |
| 44 | **setoolkit** | Ingénierie sociale | Boîte à outils d’ingénierie sociale |
| 45 | **beef-xss** | Web | Exploitation de navigateur |
| 46 | **linpeas** | Élévation de privilèges | Énumération Linux |
| 47 | **winpeas** | Élévation de privilèges | Énumération Windows |
| 48 | **chisel** | Tunnels | Tunnel HTTP |
| 49 | **sshuttle** | Tunnels | VPN sur SSH |
| 50 | **evil-winrm** | Post-exploitation | Console WinRM |

---

## 📚 Ressources

- [Outils Kali Linux](https://www.kali.org/tools/)
- [Documentation Kali](https://www.kali.org/docs/)
- [Métapaquets Kali](https://www.kali.org/docs/general-use/metapackages/)

---

<p align="center">
  <b>🐉 Maîtrisez Kali Linux !</b><br>
  <i>600+ outils à portée de main, selon l’intitulé original</i>
</p>
