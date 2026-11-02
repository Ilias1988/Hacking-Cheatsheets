# 🐉 Herramientas de Kali Linux: referencia completa (más de 600 herramientas)

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗  ██╗ █████╗ ██╗     ██╗    ██╗     ██╗███╗   ██╗██╗   ██╗██╗  ██╗
  ██║ ██╔╝██╔══██╗██║     ██║    ██║     ██║████╗  ██║██║   ██║╚██╗██╔╝
  █████╔╝ ███████║██║     ██║    ██║     ██║██╔██╗ ██║██║   ██║ ╚███╔╝ 
  ██╔═██╗ ██╔══██║██║     ██║    ██║     ██║██║╚██╗██║██║   ██║ ██╔██╗ 
  ██║  ██╗██║  ██║███████╗██║    ███████╗██║██║ ╚████║╚██████╔╝██╔╝ ██╗
  ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝    ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝  ╚═╝
                    GUÍA DE REFERENCIA DE HERRAMIENTAS
```

<p align="center">
  <img src="https://img.shields.io/badge/Kali_Linux-Tools-557C94?style=for-the-badge&logo=kalilinux" alt="Kali">
  <img src="https://img.shields.io/badge/600+-Tools-red?style=for-the-badge" alt="Herramientas">
  <img src="https://img.shields.io/badge/14-Categories-blue?style=for-the-badge" alt="Categorías">
</p>

> **Precisiones del original:** «más de 600» describe el conjunto de herramientas de Kali de forma general; esta guía ofrece una selección y repite algunas herramientas en varias categorías. La disponibilidad, el nombre del paquete y lo que viene instalado dependen de la versión, la arquitectura y los metapaquetes elegidos; no todos los nombres de la tabla son paquetes instalables ni programas nativos de Linux. Consúltese el [catálogo oficial](https://www.kali.org/tools/) y la documentación de [metapaquetes](https://www.kali.org/docs/general-use/metapackages/). Las clasificaciones y comparaciones de rapidez del original son orientativas. Cutter se basa actualmente en Rizin, aunque la tabla conserva la descripción histórica de Radare2; véase el [proyecto oficial](https://cutter.re/).

---

## 📋 Índice

1. [Recopilación de información](#1-recopilación-de-información)
2. [Análisis de vulnerabilidades](#2-análisis-de-vulnerabilidades)
3. [Análisis de aplicaciones web](#3-análisis-de-aplicaciones-web)
4. [Evaluación de bases de datos](#4-evaluación-de-bases-de-datos)
5. [Ataques a contraseñas](#5-ataques-a-contraseñas)
6. [Ataques a redes inalámbricas](#6-ataques-a-redes-inalámbricas)
7. [Ingeniería inversa](#7-ingeniería-inversa)
8. [Herramientas de explotación](#8-herramientas-de-explotación)
9. [Captura de tráfico y suplantación](#9-captura-de-tráfico-y-suplantación)
10. [Posexplotación](#10-posexplotación)
11. [Análisis forense](#11-análisis-forense)
12. [Herramientas para informes](#12-herramientas-para-informes)
13. [Ingeniería social](#13-ingeniería-social)
14. [Hacking de hardware](#14-hacking-de-hardware)

---

<a id="1-recopilación-de-información"></a>

## 1. 🔍 Recopilación de información

### Análisis de DNS

| Herramienta | Descripción |
|------|-------------|
| **dnsrecon** | Script de enumeración DNS |
| **dnsenum** | Herramienta de enumeración DNS |
| **fierce** | Herramienta de reconocimiento DNS |
| **dnsmap** | Descubrimiento de subdominios por fuerza bruta |
| **dnstracer** | Herramienta para rastrear consultas DNS |
| **dnswalk** | Depurador de bases de datos DNS |

### Descubrimiento de equipos

| Herramienta | Descripción |
|------|-------------|
| **nmap** | Exploración de redes y escaneo de puertos |
| **masscan** | Escáner rápido de puertos |
| **hping3** | Generador de paquetes de red |
| **fping** | Barrido rápido mediante ping |
| **arping** | Utilidad de ping mediante ARP |
| **netdiscover** | Reconocimiento ARP activo y pasivo |
| **unicornscan** | Escáner asíncrono de redes |

### Escáneres de redes y puertos

| Herramienta | Descripción |
|------|-------------|
| **nmap** | El escáner de redes más popular |
| **masscan** | El escáner de puertos más rápido |
| **rustscan** | Escáner moderno y rápido |
| **zmap** | Escáner rápido de redes con un único paquete por objetivo |
| **unicornscan** | Escáner asíncrono de TCP/UDP |
| **nbtscan** | Escáner de nombres NetBIOS |
| **onesixtyone** | Escáner SNMP |

### Herramientas OSINT (inteligencia de fuentes abiertas)

| Herramienta | Descripción |
|------|-------------|
| **maltego** | OSINT y análisis gráfico de relaciones |
| **spiderfoot** | Herramienta de automatización de OSINT |
| **recon-ng** | Plataforma de reconocimiento web |
| **theHarvester** | Recopilación de correos electrónicos, subdominios y direcciones IP |
| **shodan** | Interfaz de línea de comandos de un buscador de dispositivos IoT |
| **amass** | Representación de la superficie de ataque |
| **subfinder** | Herramienta de descubrimiento de subdominios |
| **assetfinder** | Búsqueda de dominios y subdominios |
| **waybackurls** | Obtención de URL de Wayback Machine |
| **gau** | Obtención de URL (Get All URLs) |

### Análisis de rutas

| Herramienta | Descripción |
|------|-------------|
| **traceroute** | Rastreo de rutas de red |
| **mtr** | Herramienta de diagnóstico de redes |
| **netmask** | Herramienta para máscaras de red |
| **tcptraceroute** | Rastreo de rutas mediante TCP |

### Análisis de SMB

| Herramienta | Descripción |
|------|-------------|
| **smbclient** | Cliente SMB |
| **smbmap** | Enumeración de recursos compartidos SMB |
| **enum4linux** | Enumeración de Windows/Samba |
| **enum4linux-ng** | Nueva generación de enum4linux |
| **rpcclient** | Cliente MS-RPC |
| **nbtscan** | Escáner NetBIOS |

### Análisis de SMTP

| Herramienta | Descripción |
|------|-------------|
| **smtp-user-enum** | Enumeración de usuarios SMTP |
| **swaks** | Herramienta de pruebas de SMTP |

### Análisis de SNMP

| Herramienta | Descripción |
|------|-------------|
| **snmpwalk** | Obtención de datos mediante SNMP |
| **snmpcheck** | Enumerador de dispositivos SNMP |
| **onesixtyone** | Escáner rápido de SNMP |
| **braa** | Escáner masivo de SNMP |

### Análisis de SSL

| Herramienta | Descripción |
|------|-------------|
| **sslscan** | Escáner SSL/TLS |
| **sslyze** | Herramienta de análisis de SSL/TLS |
| **testssl.sh** | Pruebas de SSL/TLS |

---

<a id="2-análisis-de-vulnerabilidades"></a>

## 2. 🔓 Análisis de vulnerabilidades

### Escáneres de vulnerabilidades

| Herramienta | Descripción |
|------|-------------|
| **nmap** | Scripts NSE para detectar vulnerabilidades |
| **nikto** | Escáner de servidores web |
| **openvas** | Evaluación de vulnerabilidades de código abierto |
| **legion** | Pruebas de penetración en redes |
| **lynis** | Herramienta de auditoría de seguridad |
| **nuclei** | Escáner de vulnerabilidades basado en plantillas |

### Herramientas de fuzzing (pruebas con entradas variables)

| Herramienta | Descripción |
|------|-------------|
| **wfuzz** | Herramienta de fuzzing web |
| **ffuf** | Herramienta rápida de fuzzing web |
| **gobuster** | Descubrimiento de directorios y nombres DNS por fuerza bruta |
| **dirb** | Escáner de contenido web |
| **dirbuster** | Descubrimiento de directorios por fuerza bruta |
| **feroxbuster** | Descubrimiento recursivo de contenido |
| **spike** | Herramienta de fuzzing de protocolos |
| **sfuzz** | Herramienta sencilla de fuzzing |

### Herramientas de VoIP

| Herramienta | Descripción |
|------|-------------|
| **sipvicious** | Herramientas del protocolo SIP |
| **voiphopper** | Salto entre VLAN en entornos VoIP |

---

<a id="3-análisis-de-aplicaciones-web"></a>

## 3. 🌐 Análisis de aplicaciones web

### Identificación de sistemas de gestión de contenidos (CMS)

| Herramienta | Descripción |
|------|-------------|
| **wpscan** | Escáner de vulnerabilidades de WordPress |
| **joomscan** | Escáner de Joomla |
| **droopescan** | Escáner de Drupal/WordPress/Joomla |
| **cmseek** | Detección y explotación de CMS |

### Escáneres de vulnerabilidades web

| Herramienta | Descripción |
|------|-------------|
| **nikto** | Escáner de servidores web |
| **burpsuite** | Pruebas de seguridad de aplicaciones web |
| **zaproxy** | Proxy OWASP ZAP |
| **wapiti** | Escáner de vulnerabilidades web |
| **skipfish** | Escáner de seguridad de aplicaciones web |
| **arachni** | Escáner de seguridad de aplicaciones web |
| **w3af** | Plataforma de ataques a aplicaciones web |
| **vega** | Escáner de seguridad web |

### Rastreadores web

| Herramienta | Descripción |
|------|-------------|
| **cutycapt** | Captura de pantalla de una página web |
| **dirb** | Escáner de contenido web |
| **dirbuster** | Descubrimiento de directorios por fuerza bruta |
| **gobuster** | Descubrimiento de directorios y nombres DNS por fuerza bruta |
| **feroxbuster** | Descubrimiento rápido de contenido |

### Proxies de aplicaciones web

| Herramienta | Descripción |
|------|-------------|
| **burpsuite** | Proxy y escáner web |
| **zaproxy** | OWASP ZAP |
| **mitmproxy** | Proxy HTTP |
| **proxychains** | Cadenas de proxies |

### Inyección SQL

| Herramienta | Descripción |
|------|-------------|
| **sqlmap** | Automatización de inyecciones SQL |
| **sqlninja** | Inyección SQL en SQL Server |
| **bbqsql** | Inyección SQL ciega |
| **jsql-injection** | Herramienta de inyección SQL escrita en Java |
| **ghauri** | Herramienta de inyección SQL |

### Herramientas de XSS

| Herramienta | Descripción |
|------|-------------|
| **xsser** | Herramienta para probar vulnerabilidades XSS |
| **xssstrike** | Detección avanzada de XSS |
| **dalfox** | Análisis de parámetros para detectar XSS |

---

<a id="4-evaluación-de-bases-de-datos"></a>

## 4. 💾 Evaluación de bases de datos

| Herramienta | Descripción |
|------|-------------|
| **sqlmap** | Automatización de inyecciones SQL |
| **sqlninja** | Inyección SQL en MS SQL |
| **jsql-injection** | Herramienta de inyección SQL escrita en Java |
| **oscanner** | Escáner de Oracle |
| **tnscmd10g** | Herramienta para el servicio de escucha TNS de Oracle |
| **odat** | Herramienta de ataques a bases de datos Oracle |
| **mdbtools** | Herramientas para MS Access |
| **sidguesser** | Descubrimiento de identificadores SID de Oracle |

---

<a id="5-ataques-a-contraseñas"></a>

## 5. 🔑 Ataques a contraseñas

### Ataques sin conexión

| Herramienta | Descripción |
|------|-------------|
| **john** | Recuperación de contraseñas con John the Ripper |
| **hashcat** | Recuperación de contraseñas mediante GPU |
| **ophcrack** | Recuperación de contraseñas de Windows |
| **hashid** | Identificación de hashes |
| **hash-identifier** | Identificación de tipos de hash |
| **chntpw** | Restablecimiento de contraseñas de Windows |
| **samdump2** | Utilidad de volcado de SAM |
| **crunch** | Generador de listas de palabras (wordlists) |
| **cewl** | Generador de listas de palabras personalizadas |
| **cupp** | Creación de perfiles para generar contraseñas candidatas de un usuario |
| **rsmangler** | Generación de variantes de listas de palabras |
| **wordlists** | Colección de listas de palabras |

### Ataques en línea

| Herramienta | Descripción |
|------|-------------|
| **hydra** | Ataques a inicios de sesión en servicios de red |
| **medusa** | Fuerza bruta paralela sobre inicios de sesión |
| **ncrack** | Ataques a la autenticación en servicios de red |
| **patator** | Herramienta de fuerza bruta de propósito general |
| **thc-pptp-bruter** | Fuerza bruta de PPTP |
| **crowbar** | Herramienta de fuerza bruta |

### Pass-the-Hash (autenticación mediante hashes)

| Herramienta | Descripción |
|------|-------------|
| **mimikatz** | Extracción de credenciales de Windows |
| **pth-toolkit** | Conjunto de herramientas de Pass-the-Hash |
| **smbmap** | Enumeración de SMB |
| **crackmapexec** | Herramienta de pruebas de penetración en redes |
| **evil-winrm** | Administración remota de Windows |

---

<a id="6-ataques-a-redes-inalámbricas"></a>

## 6. 📡 Ataques a redes inalámbricas

### Bluetooth

| Herramienta | Descripción |
|------|-------------|
| **bluelog** | Escáner Bluetooth |
| **bluemaho** | Pruebas de seguridad de Bluetooth |
| **blueranger** | Detección de proximidad mediante Bluetooth |
| **btscanner** | Escáner de dispositivos Bluetooth |
| **redfang** | Escáner de dispositivos Bluetooth ocultos |
| **spooftooph** | Suplantación de Bluetooth |

### Herramientas de WiFi

| Herramienta | Descripción |
|------|-------------|
| **aircrack-ng** | Conjunto de herramientas de seguridad WiFi |
| **airmon-ng** | Activación del modo monitor |
| **airodump-ng** | Captura de paquetes |
| **aireplay-ng** | Inyección de paquetes |
| **airbase-ng** | Punto de acceso (AP) falso |
| **wifite** | Auditor automatizado de WiFi |
| **fern-wifi-cracker** | Herramienta de auditoría de WiFi |
| **kismet** | Detector de redes inalámbricas |
| **reaver** | Herramienta de ataques a WPS |
| **bully** | Fuerza bruta de WPS |
| **pixiewps** | Ataque sin conexión a WPS |
| **wifiphisher** | Herramienta de phishing mediante WiFi |
| **fluxion** | Ataque de intermediario (MitM) contra WPA |
| **hostapd-wpe** | Obtención de credenciales mediante redes inalámbricas |
| **cowpatty** | Recuperación de claves WPA-PSK |
| **asleap** | Recuperación de contraseñas LEAP/PPTP |
| **eapmd5pass** | Recuperación de contraseñas EAP-MD5 |
| **mdk3** | Herramienta de ataques a redes inalámbricas |
| **mdk4** | Herramienta de explotación de redes inalámbricas |

### RFID/NFC

| Herramienta | Descripción |
|------|-------------|
| **mfoc** | Recuperación de claves de Mifare Classic sin conexión |
| **mfcuk** | Conjunto universal de herramientas para Mifare Classic |
| **mfterm** | Terminal para Mifare Classic |
| **libnfc** | Biblioteca NFC |
| **proxmark3** | Herramienta de investigación de RFID |

---

<a id="7-ingeniería-inversa"></a>

## 7. 🔧 Ingeniería inversa

### Depuradores

| Herramienta | Descripción |
|------|-------------|
| **gdb** | Depurador GNU |
| **edb-debugger** | Depurador multiplataforma |
| **radare2** | Plataforma de ingeniería inversa |
| **x64dbg** | Depurador x64/x32 |
| **ollydbg** | Depurador x86 |
| **immunity-debugger** | Depurador que admite scripts de Python |

### Desensambladores

| Herramienta | Descripción |
|------|-------------|
| **ghidra** | Herramienta de ingeniería inversa de la NSA |
| **radare2** | Plataforma de ingeniería inversa |
| **cutter** | Interfaz gráfica de Radare2 (según el original; véase la precisión sobre Cutter) |
| **rizin** | Plataforma de ingeniería inversa |
| **objdump** | Desensamblador de archivos objeto |
| **IDA Free** | Desensamblador interactivo |

### Otras herramientas de ingeniería inversa

| Herramienta | Descripción |
|------|-------------|
| **apktool** | Herramienta para archivos APK de Android |
| **dex2jar** | Conversor de DEX a JAR |
| **jadx** | Descompilador de Java |
| **jd-gui** | Interfaz gráfica de un descompilador de Java |
| **bytecode-viewer** | Visor de bytecode de Java |
| **androguard** | Análisis de aplicaciones Android |
| **frida** | Instrumentación dinámica |
| **angr** | Plataforma de análisis de binarios |
| **binwalk** | Herramienta de análisis de firmware |

---

<a id="8-herramientas-de-explotación"></a>

## 8. 💣 Herramientas de explotación

### Plataformas de explotación

| Herramienta | Descripción |
|------|-------------|
| **metasploit-framework** | Plataforma de explotación |
| **armitage** | Interfaz gráfica de Metasploit |
| **cobalt-strike** | Simulación de adversarios |
| **beef-xss** | Explotación de navegadores |
| **routersploit** | Explotación de routers |
| **exploitdb** | Base de datos de exploits |
| **searchsploit** | Búsqueda en Exploit-DB |

### Herramientas de shellcode (código ejecutable para cargas útiles)

| Herramienta | Descripción |
|------|-------------|
| **msfvenom** | Generador de cargas útiles (payloads) |
| **veil** | Plataforma de generación de cargas útiles |
| **shellnoob** | Conjunto de herramientas de shellcode |

### Explotación de redes

| Herramienta | Descripción |
|------|-------------|
| **responder** | Envenenamiento de respuestas LLMNR/NBT-NS |
| **impacket** | Conjunto de herramientas de protocolos de red |
| **crackmapexec** | Herramienta de posexplotación |
| **evil-winrm** | Shell de WinRM |
| **powershell-empire** | Plataforma de posexplotación |

---

<a id="9-captura-de-tráfico-y-suplantación"></a>

## 9. 📡 Captura de tráfico y suplantación

### Herramientas de captura de tráfico de red

| Herramienta | Descripción |
|------|-------------|
| **wireshark** | Analizador de protocolos de red |
| **tcpdump** | Captura de tráfico desde la línea de comandos |
| **tshark** | Wireshark para terminal |
| **ettercap** | Captura de tráfico de red y ataques de intermediario (MitM) |
| **dsniff** | Herramientas de auditoría de redes |
| **net-creds** | Captura de credenciales del tráfico |
| **pcredz** | Extracción de credenciales |

### Herramientas de suplantación

| Herramienta | Descripción |
|------|-------------|
| **arpspoof** | Suplantación de ARP |
| **dnsspoof** | Suplantación de DNS |
| **macchanger** | Cambio de direcciones MAC |
| **ettercap** | Envenenamiento de ARP |
| **bettercap** | Herramienta de ataques a redes |
| **mitmproxy** | Proxy HTTP de intermediario (MitM) |
| **sslstrip** | Degradación de HTTPS a HTTP (SSL stripping) |

### Captura de tráfico de VoIP

| Herramienta | Descripción |
|------|-------------|
| **sipvicious** | Herramientas de SIP/VoIP |
| **voiphopper** | Salto entre VLAN en entornos VoIP |

---

<a id="10-posexplotación"></a>

## 10. 🎯 Posexplotación

### Puertas traseras en sistemas operativos

| Herramienta | Descripción |
|------|-------------|
| **msfvenom** | Generador de cargas útiles (payloads) |
| **veil** | Evasión de antivirus |
| **shellter** | Inyector dinámico de shellcode |
| **weevely** | Shell web en PHP |

### Creación de túneles

| Herramienta | Descripción |
|------|-------------|
| **sshuttle** | VPN mediante túneles SSH |
| **chisel** | Creación de túneles HTTP |
| **proxychains** | Cadenas de proxies |
| **socat** | Herramienta de retransmisión de datos |
| **plink** | Cliente de línea de comandos de PuTTY (PuTTY link) |
| **stunnel** | Creación de túneles TLS |
| **dns2tcp** | Creación de túneles DNS |
| **iodine** | Túnel DNS |
| **ptunnel** | Creación de túneles ICMP |
| **pwnat** | Comunicación a través de NAT |
| **udptunnel** | Creación de túneles UDP |

### Posexplotación en Windows

| Herramienta | Descripción |
|------|-------------|
| **mimikatz** | Volcado de credenciales de Windows |
| **crackmapexec** | Pruebas de penetración en Windows/AD |
| **evil-winrm** | Shell de WinRM |
| **impacket** | Protocolos de Windows |
| **bloodhound** | Reconocimiento de Active Directory |
| **powershell-empire** | Posexplotación con PowerShell |
| **rubeus** | Abuso de Kerberos |
| **kerbrute** | Fuerza bruta de Kerberos |

### Posexplotación en Linux

| Herramienta | Descripción |
|------|-------------|
| **linpeas** | Escalada de privilegios en Linux |
| **linenum** | Enumeración de Linux |
| **pspy** | Monitor de procesos |
| **linux-exploit-suggester** | Búsqueda de exploits del kernel |

---

<a id="11-análisis-forense"></a>

## 11. 🔬 Análisis forense

### Plataformas de análisis forense

| Herramienta | Descripción |
|------|-------------|
| **autopsy** | Plataforma de análisis forense digital |
| **sleuthkit** | Conjunto de herramientas de análisis forense |
| **dff** | Plataforma de análisis forense digital |

### Recuperación forense por firmas (file carving)

| Herramienta | Descripción |
|------|-------------|
| **foremost** | Herramienta de recuperación de archivos por firmas |
| **scalpel** | Recuperación de archivos por firmas |
| **bulk_extractor** | Extracción de información |
| **photorec** | Recuperación de fotografías |
| **testdisk** | Recuperación de datos |

### Creación de imágenes forenses

| Herramienta | Descripción |
|------|-------------|
| **dc3dd** | Creación de imágenes forenses de discos |
| **dcfldd** | Versión mejorada de dd |
| **guymager** | Interfaz gráfica para crear imágenes forenses |

### Análisis forense de memoria

| Herramienta | Descripción |
|------|-------------|
| **volatility** | Análisis forense de memoria |
| **volatility3** | Análisis de memoria |
| **lime** | Extracción de memoria en Linux |

### Análisis forense de redes

| Herramienta | Descripción |
|------|-------------|
| **wireshark** | Análisis de paquetes |
| **tcpdump** | Captura de paquetes |
| **ngrep** | Búsqueda de patrones en tráfico de red, similar a grep |
| **NetworkMiner** | Análisis forense de redes |

### Análisis forense de PDF

| Herramienta | Descripción |
|------|-------------|
| **pdfid** | Escáner de PDF |
| **pdf-parser** | Análisis de PDF |
| **peepdf** | Herramienta de análisis de PDF |

---

<a id="12-herramientas-para-informes"></a>

## 12. 📝 Herramientas para informes

| Herramienta | Descripción |
|------|-------------|
| **cutycapt** | Capturas de pantalla de páginas web |
| **faraday** | Herramienta colaborativa para pruebas de penetración |
| **maltego** | Visualización de OSINT |
| **pipal** | Análisis de contraseñas |
| **recordmydesktop** | Grabación de pantalla |
| **cherrytree** | Aplicación para tomar notas |
| **dradis** | Plataforma de colaboración |
| **metagoofil** | Extracción de metadatos |
| **eyewitness** | Captura de pantalla de un sitio web |
| **gowitness** | Captura de pantalla de un sitio web |

---

<a id="13-ingeniería-social"></a>

## 13. 🎭 Ingeniería social

| Herramienta | Descripción |
|------|-------------|
| **setoolkit** | Conjunto de herramientas de ingeniería social |
| **gophish** | Plataforma de phishing (suplantación para obtener información) |
| **king-phisher** | Campañas de phishing |
| **evilginx2** | Plataforma de phishing mediante intermediario (MitM) |
| **beef-xss** | Explotación de navegadores |
| **maltego** | Relaciones obtenidas mediante OSINT |
| **theHarvester** | Recopilación de correos electrónicos |
| **sherlock** | OSINT sobre nombres de usuario |
| **social-analyzer** | Análisis de redes sociales |

---

<a id="14-hacking-de-hardware"></a>

## 14. 🔧 Hacking de hardware

| Herramienta | Descripción |
|------|-------------|
| **arduino** | Plataforma de hardware |
| **binwalk** | Análisis de firmware |
| **flashrom** | Programación de memorias flash |
| **openocd** | Depuración en el chip |
| **proxmark3** | Investigación de RFID |

---

## 📊 Referencia rápida: 50 herramientas destacadas

| # | Herramienta | Categoría | Caso de uso |
|---|------|----------|----------|
| 1 | **nmap** | Escaneo | Escaneo de puertos |
| 2 | **metasploit** | Explotación | Plataforma de explotación |
| 3 | **burpsuite** | Web | Pruebas web |
| 4 | **wireshark** | Captura de tráfico | Análisis de paquetes |
| 5 | **john** | Contraseñas | Recuperación de contraseñas |
| 6 | **hashcat** | Contraseñas | Recuperación de contraseñas mediante GPU |
| 7 | **hydra** | Contraseñas | Fuerza bruta en línea |
| 8 | **sqlmap** | Web | Inyección SQL |
| 9 | **aircrack-ng** | Redes inalámbricas | Recuperación de claves WiFi |
| 10 | **nikto** | Web | Escaneo web |
| 11 | **gobuster** | Web | Descubrimiento de directorios por fuerza bruta |
| 12 | **ffuf** | Web | Fuzzing web |
| 13 | **dirb** | Web | Escaneo de directorios |
| 14 | **wpscan** | Web | Escaneo de WordPress |
| 15 | **enum4linux** | Información | Enumeración de SMB |
| 16 | **crackmapexec** | Posexplotación | Pruebas de penetración en AD |
| 17 | **mimikatz** | Posexplotación | Volcado de credenciales |
| 18 | **responder** | Explotación | Envenenamiento de respuestas LLMNR |
| 19 | **impacket** | Explotación | Herramientas de protocolos |
| 20 | **bloodhound** | Posexplotación | Representación de AD |
| 21 | **netcat** | Utilidades | Utilidad de TCP/UDP |
| 22 | **socat** | Utilidades | Retransmisión de datos |
| 23 | **tcpdump** | Captura de tráfico | Captura de paquetes |
| 24 | **ettercap** | Captura de tráfico | Ataques de intermediario (MitM) |
| 25 | **bettercap** | Captura de tráfico | Ataques a redes |
| 26 | **proxychains** | Utilidades | Cadenas de proxies |
| 27 | **msfvenom** | Explotación | Generación de cargas útiles |
| 28 | **searchsploit** | Explotación | Búsqueda de exploits |
| 29 | **theHarvester** | OSINT | Recopilación de correos electrónicos |
| 30 | **maltego** | OSINT | Análisis de relaciones |
| 31 | **recon-ng** | OSINT | Plataforma de reconocimiento |
| 32 | **amass** | OSINT | Enumeración de subdominios |
| 33 | **subfinder** | OSINT | Búsqueda de subdominios |
| 34 | **nuclei** | Vulnerabilidades | Escáner basado en plantillas |
| 35 | **zaproxy** | Web | OWASP ZAP |
| 36 | **wifite** | Redes inalámbricas | Auditoría automatizada de WiFi |
| 37 | **ghidra** | Ingeniería inversa | Ingeniería inversa |
| 38 | **radare2** | Ingeniería inversa | Análisis de binarios |
| 39 | **gdb** | Ingeniería inversa | Depurador |
| 40 | **binwalk** | Ingeniería inversa | Análisis de firmware |
| 41 | **volatility** | Análisis forense | Análisis de memoria |
| 42 | **autopsy** | Análisis forense | Plataforma de análisis forense |
| 43 | **foremost** | Análisis forense | Recuperación de archivos por firmas |
| 44 | **setoolkit** | Ingeniería social | Herramientas de ingeniería social |
| 45 | **beef-xss** | Web | Explotación de navegadores |
| 46 | **linpeas** | Escalada de privilegios | Enumeración de Linux |
| 47 | **winpeas** | Escalada de privilegios | Enumeración de Windows |
| 48 | **chisel** | Creación de túneles | Túnel HTTP |
| 49 | **sshuttle** | Creación de túneles | VPN mediante SSH |
| 50 | **evil-winrm** | Posexplotación | Shell de WinRM |

---

## 📚 Recursos

- [Herramientas de Kali Linux](https://www.kali.org/tools/)
- [Documentación de Kali](https://www.kali.org/docs/)
- [Metapaquetes de Kali](https://www.kali.org/docs/general-use/metapackages/)

---

<p align="center">
  <b>🐉 ¡Domina Kali Linux!</b><br>
  <i>Más de 600 herramientas a tu alcance</i>
</p>
