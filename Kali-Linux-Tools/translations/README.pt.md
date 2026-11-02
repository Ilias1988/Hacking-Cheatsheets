# 🐉 Ferramentas Kali Linux — Referência completa (mais de 600 ferramentas)

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗  ██╗ █████╗ ██╗     ██╗    ██╗     ██╗███╗   ██╗██╗   ██╗██╗  ██╗
  ██║ ██╔╝██╔══██╗██║     ██║    ██║     ██║████╗  ██║██║   ██║╚██╗██╔╝
  █████╔╝ ███████║██║     ██║    ██║     ██║██╔██╗ ██║██║   ██║ ╚███╔╝ 
  ██╔═██╗ ██╔══██║██║     ██║    ██║     ██║██║╚██╗██║██║   ██║ ██╔██╗ 
  ██║  ██╗██║  ██║███████╗██║    ███████╗██║██║ ╚████║╚██████╔╝██╔╝ ██╗
  ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝    ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝  ╚═╝
                    GUIA DE REFERÊNCIA DE FERRAMENTAS
```

<p align="center">
  <img src="https://img.shields.io/badge/Kali_Linux-Tools-557C94?style=for-the-badge&logo=kalilinux" alt="Kali">
  <img src="https://img.shields.io/badge/600+-Tools-red?style=for-the-badge" alt="Ferramentas">
  <img src="https://img.shields.io/badge/14-Categories-blue?style=for-the-badge" alt="Categorias">
</p>

---

## 📋 Índice

1. [Recolha de informações](#1--information-gathering)
2. [Análise de vulnerabilidades](#2--vulnerability-analysis)
3. [Análise de aplicações web](#3--web-application-analysis)
4. [Avaliação de bases de dados](#4--database-assessment)
5. [Ataques a palavras-passe](#5--password-attacks)
6. [Ataques a redes sem fios](#6--wireless-attacks)
7. [Engenharia inversa](#7--reverse-engineering)
8. [Ferramentas de exploração](#8--exploitation-tools)
9. [Captura de tráfego e falsificação (spoofing)](#9--sniffing--spoofing)
10. [Pós-exploração](#10--post-exploitation)
11. [Análise forense](#11--forensics)
12. [Ferramentas de relatórios](#12--reporting-tools)
13. [Engenharia social](#13--social-engineering)
14. [Hacking de hardware](#14--hardware-hacking)

---

<a id="1--information-gathering"></a>
Este catálogo mantém as ferramentas e categorias do original. OSINT é a recolha e análise de informações em fontes abertas; fuzzing testa entradas variadas. Sniffing significa captura de tráfego e spoofing significa falsificação de identidade ou de dados. Desassemblagem apresenta código de máquina em assembly; descompilação procura reconstruir código de nível superior.

> **Âmbito e versões:** «600+» é uma indicação geral do original, não uma contagem das linhas deste guia. As ferramentas efetivamente disponíveis dependem da imagem, arquitetura, versão e metapacotes instalados; a lista não garante instalação predefinida nem disponibilidade de um pacote com o mesmo nome. Ver os [metapacotes oficiais](https://www.kali.org/docs/general-use/metapackages/) e o [catálogo atual de ferramentas](https://www.kali.org/tools/).

## 1. 🔍 Recolha de informações

### Análise de DNS

| Ferramenta | Descrição |
|------|-------------|
| **dnsrecon** | Script de enumeração DNS |
| **dnsenum** | Ferramenta de enumeração DNS |
| **fierce** | Ferramenta de reconhecimento DNS |
| **dnsmap** | Descoberta de subdomínios por força bruta |
| **dnstracer** | Ferramenta de rastreio DNS |
| **dnswalk** | Depurador de bases de dados DNS |

### Descoberta de máquinas

| Ferramenta | Descrição |
|------|-------------|
| **nmap** | Mapeamento de redes e análise de portas |
| **masscan** | Analisador rápido de portas |
| **hping3** | Gerador de pacotes de rede |
| **fping** | Descoberta rápida de máquinas por ping |
| **arping** | Utilitário de ping ARP |
| **netdiscover** | Reconhecimento ARP ativo ou passivo |
| **unicornscan** | Analisador de rede assíncrono |

### Analisadores de redes e portas

| Ferramenta | Descrição |
|------|-------------|
| **nmap** | O analisador de rede mais popular |
| **masscan** | O analisador de portas mais rápido |
| **rustscan** | Analisador moderno e rápido |
| **zmap** | Analisador rápido de rede com sondagens de um pacote |
| **unicornscan** | Analisador TCP/UDP assíncrono |
| **nbtscan** | Analisador de nomes NetBIOS |
| **onesixtyone** | Analisador SNMP |

### Ferramentas OSINT

| Ferramenta | Descrição |
|------|-------------|
| **maltego** | OSINT e análise gráfica de relações |
| **spiderfoot** | Ferramenta de automatização de OSINT |
| **recon-ng** | Framework de reconhecimento web |
| **theHarvester** | Recolha de emails, subdomínios e IPs |
| **shodan** | Linha de comandos para pesquisa de dispositivos IoT |
| **amass** | Mapeamento da superfície de ataque |
| **subfinder** | Ferramenta de descoberta de subdomínios |
| **assetfinder** | Encontrar domínios e subdomínios |
| **waybackurls** | Obter URLs do Wayback Machine |
| **gau** | Obter todos os URLs |

### Análise de rotas

| Ferramenta | Descrição |
|------|-------------|
| **traceroute** | Rastreio de rotas de rede |
| **mtr** | Ferramenta de diagnóstico de rede |
| **netmask** | Ferramenta de máscaras de rede |
| **tcptraceroute** | Rastreio de rotas com TCP |

### Análise de SMB

| Ferramenta | Descrição |
|------|-------------|
| **smbclient** | Cliente SMB |
| **smbmap** | Enumeração de partilhas SMB |
| **enum4linux** | Enumeração de Windows/Samba |
| **enum4linux-ng** | Nova geração do enum4linux |
| **rpcclient** | Cliente MS-RPC |
| **nbtscan** | Analisador NetBIOS |

### Análise de SMTP

| Ferramenta | Descrição |
|------|-------------|
| **smtp-user-enum** | Enumeração de utilizadores SMTP |
| **swaks** | Ferramenta de teste SMTP |

### Análise de SNMP

| Ferramenta | Descrição |
|------|-------------|
| **snmpwalk** | Obtenção de dados SNMP |
| **snmpcheck** | Enumeração de dispositivos SNMP |
| **onesixtyone** | Analisador SNMP rápido |
| **braa** | Analisador SNMP em massa |

### Análise de SSL

| Ferramenta | Descrição |
|------|-------------|
| **sslscan** | Analisador SSL/TLS |
| **sslyze** | Ferramenta de análise SSL/TLS |
| **testssl.sh** | Testes SSL/TLS |

---

<a id="2--vulnerability-analysis"></a>
## 2. 🔓 Análise de vulnerabilidades

### Analisadores de vulnerabilidades

| Ferramenta | Descrição |
|------|-------------|
| **nmap** | Scripts NSE para vulnerabilidades |
| **nikto** | Analisador de servidores web |
| **openvas** | Avaliação aberta de vulnerabilidades |
| **legion** | Testes de intrusão em redes |
| **lynis** | Ferramenta de auditoria de segurança |
| **nuclei** | Analisador de vulnerabilidades baseado em modelos |

### Ferramentas de fuzzing

| Ferramenta | Descrição |
|------|-------------|
| **wfuzz** | Fuzzer web |
| **ffuf** | Fuzzer web rápido |
| **gobuster** | Descoberta de diretórios e DNS por força bruta |
| **dirb** | Analisador de conteúdo web |
| **dirbuster** | Descoberta de diretórios por força bruta |
| **feroxbuster** | Descoberta recursiva de conteúdo |
| **spike** | Fuzzer de protocolos |
| **sfuzz** | Fuzzer simples |

### Ferramentas VoIP

| Ferramenta | Descrição |
|------|-------------|
| **sipvicious** | Ferramentas para o protocolo SIP |
| **voiphopper** | Salto entre VLANs de VoIP |

---

<a id="3--web-application-analysis"></a>
## 3. 🌐 Análise de aplicações web

### Identificação de CMS

| Ferramenta | Descrição |
|------|-------------|
| **wpscan** | Analisador de vulnerabilidades WordPress |
| **joomscan** | Analisador Joomla |
| **droopescan** | Analisador Drupal/WordPress/Joomla |
| **cmseek** | Deteção e exploração de CMS |

### Analisadores de vulnerabilidades web

| Ferramenta | Descrição |
|------|-------------|
| **nikto** | Analisador de servidores web |
| **burpsuite** | Testes de segurança de aplicações web |
| **zaproxy** | Proxy OWASP ZAP |
| **wapiti** | Analisador de vulnerabilidades web |
| **skipfish** | Analisador de segurança de aplicações web |
| **arachni** | Analisador de segurança de aplicações web |
| **w3af** | Framework de ataque a aplicações web |
| **vega** | Analisador de segurança web |

### Exploradores de sites (crawlers)

> A classificação do original agrupa funções diferentes: captura de imagem, descoberta de conteúdo e exploração de ligações não são a mesma operação. Por exemplo, a descrição de cutycapt nesta tabela é captura de imagem de uma página.

| Ferramenta | Descrição |
|------|-------------|
| **cutycapt** | Captura de imagem de uma página web |
| **dirb** | Analisador de conteúdo web |
| **dirbuster** | Descoberta de diretórios por força bruta |
| **gobuster** | Descoberta de diretórios e DNS por força bruta |
| **feroxbuster** | Descoberta rápida de conteúdo |

### Proxies de aplicações web

| Ferramenta | Descrição |
|------|-------------|
| **burpsuite** | Proxy e analisador web |
| **zaproxy** | OWASP ZAP |
| **mitmproxy** | Proxy HTTP |
| **proxychains** | Cadeias de proxies |

### Injeção de SQL

| Ferramenta | Descrição |
|------|-------------|
| **sqlmap** | Automatização de injeção de SQL |
| **sqlninja** | Injeção de SQL em SQL Server |
| **bbqsql** | Injeção de SQL sem resposta direta (blind) |
| **jsql-injection** | Injeção de SQL com Java |
| **ghauri** | Ferramenta de injeção de SQL |

### Ferramentas XSS

| Ferramenta | Descrição |
|------|-------------|
| **xsser** | Ferramenta de teste de vulnerabilidades XSS |
| **xssstrike** | Deteção avançada de XSS |
| **dalfox** | Análise de parâmetros para XSS |

---

<a id="4--database-assessment"></a>
## 4. 💾 Avaliação de bases de dados

| Ferramenta | Descrição |
|------|-------------|
| **sqlmap** | Automatização de injeção de SQL |
| **sqlninja** | Injeção de SQL em MS SQL |
| **jsql-injection** | Injeção de SQL baseada em Java |
| **oscanner** | Analisador Oracle |
| **tnscmd10g** | Ferramenta para o listener TNS Oracle |
| **odat** | Ferramenta de ataque a bases de dados Oracle |
| **mdbtools** | Ferramentas MS Access |
| **sidguesser** | Descoberta de SID Oracle por tentativa |

---

<a id="5--password-attacks"></a>
## 5. 🔑 Ataques a palavras-passe

### Ataques offline

| Ferramenta | Descrição |
|------|-------------|
| **john** | Quebra de palavras-passe com John the Ripper |
| **hashcat** | Quebra de palavras-passe com GPU |
| **ophcrack** | Quebra de palavras-passe Windows |
| **hashid** | Identificador de hashes |
| **hash-identifier** | Identificador de tipos de hash |
| **chntpw** | Reposição de palavras-passe Windows |
| **samdump2** | Utilitário de extração do SAM |
| **crunch** | Gerador de listas de palavras |
| **cewl** | Gerador de listas de palavras personalizadas |
| **cupp** | Criação de perfis de palavras-passe de utilizadores |
| **rsmangler** | Transformação de listas de palavras |
| **wordlists** | Coleção de listas de palavras |

### Ataques online

| Ferramenta | Descrição |
|------|-------------|
| **hydra** | Quebra de credenciais de acesso à rede |
| **medusa** | Força bruta paralela para autenticação |
| **ncrack** | Quebra de autenticação de rede |
| **patator** | Ferramenta de força bruta para vários fins |
| **thc-pptp-bruter** | Força bruta para PPTP |
| **crowbar** | Ferramenta de força bruta |

### Pass-the-hash (autenticação com hashes)

| Ferramenta | Descrição |
|------|-------------|
| **mimikatz** | Extração de credenciais Windows |
| **pth-toolkit** | Conjunto de ferramentas pass-the-hash |
| **smbmap** | Enumeração SMB |
| **crackmapexec** | Ferramenta de testes de intrusão em redes |
| **evil-winrm** | Gestão remota de Windows |

---

<a id="6--wireless-attacks"></a>
## 6. 📡 Ataques a redes sem fios

### Bluetooth

| Ferramenta | Descrição |
|------|-------------|
| **bluelog** | Analisador Bluetooth |
| **bluemaho** | Testes de segurança Bluetooth |
| **blueranger** | Deteção de proximidade Bluetooth |
| **btscanner** | Analisador de dispositivos Bluetooth |
| **redfang** | Descoberta de dispositivos Bluetooth ocultos |
| **spooftooph** | Falsificação de identidade Bluetooth |

### Ferramentas WiFi

| Ferramenta | Descrição |
|------|-------------|
| **aircrack-ng** | Suite de segurança WiFi |
| **airmon-ng** | Ativação do modo monitor |
| **airodump-ng** | Captura de pacotes |
| **aireplay-ng** | Injeção de pacotes |
| **airbase-ng** | Ponto de acesso falso |
| **wifite** | Auditoria automatizada de WiFi |
| **fern-wifi-cracker** | Ferramenta de auditoria WiFi |
| **kismet** | Detetor de redes sem fios |
| **reaver** | Ferramenta de ataque a WPS |
| **bully** | Força bruta para WPS |
| **pixiewps** | Ataque offline a WPS |
| **wifiphisher** | Ferramenta de phishing WiFi |
| **fluxion** | Ataque WPA com intermediário (MitM) |
| **hostapd-wpe** | Recolha de credenciais em redes sem fios |
| **cowpatty** | Quebra de WPA-PSK |
| **asleap** | Recuperação de palavras-passe LEAP/PPTP |
| **eapmd5pass** | Quebra de palavras-passe EAP-MD5 |
| **mdk3** | Ferramenta de ataque a redes sem fios |
| **mdk4** | Ferramenta de exploração de redes sem fios |

### RFID/NFC

| Ferramenta | Descrição |
|------|-------------|
| **mfoc** | Quebra offline de Mifare Classic |
| **mfcuk** | Conjunto universal de ferramentas Mifare Classic |
| **mfterm** | Terminal para Mifare Classic |
| **libnfc** | Biblioteca NFC |
| **proxmark3** | Ferramenta de investigação RFID |

---

<a id="7--reverse-engineering"></a>
## 7. 🔧 Engenharia inversa

### Depuradores

| Ferramenta | Descrição |
|------|-------------|
| **gdb** | Depurador GNU |
| **edb-debugger** | Depurador multiplataforma |
| **radare2** | Framework de engenharia inversa |
| **x64dbg** | Depurador x64/x32 |
| **ollydbg** | Depurador x86 |
| **immunity-debugger** | Depurador programável em Python |

### Desassembladores

> **Correção de versão:** a descrição «interface gráfica para Radare2» de Cutter corresponde a uma associação antiga. O projeto atual é baseado em Rizin. A linha original é mantida com esta ressalva; ver o [repositório oficial de Cutter](https://github.com/rizinorg/cutter).

| Ferramenta | Descrição |
|------|-------------|
| **ghidra** | Ferramenta de engenharia inversa da NSA |
| **radare2** | Framework de engenharia inversa |
| **cutter** | Interface gráfica para Radare2 |
| **rizin** | Framework de engenharia inversa |
| **objdump** | Desassemblador de ficheiros objeto |
| **IDA Free** | Desassemblador interativo |

### Outras ferramentas de engenharia inversa

| Ferramenta | Descrição |
|------|-------------|
| **apktool** | Ferramenta para APKs Android |
| **dex2jar** | Conversor de DEX para JAR |
| **jadx** | Descompilador Java |
| **jd-gui** | Interface gráfica de descompilador Java |
| **bytecode-viewer** | Visualizador de bytecode Java |
| **androguard** | Análise de aplicações Android |
| **frida** | Instrumentação dinâmica |
| **angr** | Plataforma de análise de binários |
| **binwalk** | Ferramenta de análise de firmware |

---

<a id="8--exploitation-tools"></a>
## 8. 💣 Ferramentas de exploração

### Frameworks de exploração

| Ferramenta | Descrição |
|------|-------------|
| **metasploit-framework** | Framework de exploração |
| **armitage** | Interface gráfica para Metasploit |
| **cobalt-strike** | Simulação de adversários |
| **beef-xss** | Exploração de navegadores |
| **routersploit** | Exploração de routers |
| **exploitdb** | Base de dados de exploits |
| **searchsploit** | Pesquisa no Exploit-DB |

### Ferramentas de shellcode

| Ferramenta | Descrição |
|------|-------------|
| **msfvenom** | Gerador de payloads |
| **veil** | Framework de geração de payloads |
| **shellnoob** | Conjunto de ferramentas de shellcode |

### Exploração de redes

| Ferramenta | Descrição |
|------|-------------|
| **responder** | Envenenamento de LLMNR/NBT-NS |
| **impacket** | Conjunto de ferramentas de protocolos de rede |
| **crackmapexec** | Ferramenta de pós-exploração |
| **evil-winrm** | Shell WinRM |
| **powershell-empire** | Framework de pós-exploração |

---

<a id="9--sniffing--spoofing"></a>
## 9. 📡 Captura de tráfego e falsificação (spoofing)

### Ferramentas de captura de tráfego

| Ferramenta | Descrição |
|------|-------------|
| **wireshark** | Analisador de protocolos de rede |
| **tcpdump** | Captura de tráfego na linha de comandos |
| **tshark** | Wireshark no terminal |
| **ettercap** | Captura de tráfego e MitM em rede |
| **dsniff** | Ferramentas de auditoria de rede |
| **net-creds** | Captura de credenciais no tráfego |
| **pcredz** | Extração de credenciais |

### Ferramentas de falsificação de identidade

| Ferramenta | Descrição |
|------|-------------|
| **arpspoof** | Falsificação de ARP |
| **dnsspoof** | Falsificação de DNS |
| **macchanger** | Alteração de endereços MAC |
| **ettercap** | Envenenamento de ARP |
| **bettercap** | Ferramenta de ataque a redes |
| **mitmproxy** | Proxy HTTP com intermediário (MitM) |
| **sslstrip** | Degradação de HTTPS para HTTP |

### Captura de tráfego VoIP

| Ferramenta | Descrição |
|------|-------------|
| **sipvicious** | Ferramentas SIP/VoIP |
| **voiphopper** | Salto entre VLANs de VoIP |

---

<a id="10--post-exploitation"></a>
## 10. 🎯 Pós-exploração

### Acessos ocultos ao sistema operativo (backdoors)

| Ferramenta | Descrição |
|------|-------------|
| **msfvenom** | Gerador de payloads |
| **veil** | Evasão de antivírus |
| **shellter** | Injeção dinâmica de shellcode |
| **weevely** | Web shell PHP |

### Criação de túneis

| Ferramenta | Descrição |
|------|-------------|
| **sshuttle** | VPN através de túnel SSH |
| **chisel** | Criação de túneis HTTP |
| **proxychains** | Cadeias de proxies |
| **socat** | Ferramenta de retransmissão |
| **plink** | Ligação PuTTY |
| **stunnel** | Criação de túneis TLS |
| **dns2tcp** | Criação de túneis DNS |
| **iodine** | Túnel DNS |
| **ptunnel** | Criação de túneis ICMP |
| **pwnat** | Atravessamento de NAT |
| **udptunnel** | Criação de túneis UDP |

### Pós-exploração em Windows

| Ferramenta | Descrição |
|------|-------------|
| **mimikatz** | Extração de credenciais Windows |
| **crackmapexec** | Testes de intrusão em Windows/AD |
| **evil-winrm** | Shell WinRM |
| **impacket** | Protocolos Windows |
| **bloodhound** | Reconhecimento de Active Directory |
| **powershell-empire** | Pós-exploração com PowerShell |
| **rubeus** | Abuso de Kerberos |
| **kerbrute** | Força bruta para Kerberos |

### Pós-exploração em Linux

| Ferramenta | Descrição |
|------|-------------|
| **linpeas** | Escalada de privilégios Linux |
| **linenum** | Enumeração Linux |
| **pspy** | Monitor de processos |
| **linux-exploit-suggester** | Pesquisa de exploits para o kernel |

---

<a id="11--forensics"></a>
## 11. 🔬 Análise forense

### Frameworks de análise forense

| Ferramenta | Descrição |
|------|-------------|
| **autopsy** | Plataforma de análise forense digital |
| **sleuthkit** | Conjunto de ferramentas forenses |
| **dff** | Framework de análise forense digital |

### Recuperação forense por assinaturas (carving)

| Ferramenta | Descrição |
|------|-------------|
| **foremost** | Ferramenta de recuperação de ficheiros por assinaturas |
| **scalpel** | Recuperação de ficheiros por assinaturas |
| **bulk_extractor** | Extrator de informações |
| **photorec** | Recuperação de fotografias |
| **testdisk** | Recuperação de dados |

### Criação de imagens forenses

| Ferramenta | Descrição |
|------|-------------|
| **dc3dd** | Criação de imagens forenses de discos |
| **dcfldd** | Versão melhorada de dd |
| **guymager** | Interface gráfica de criação de imagens forenses |

### Análise forense de memória

| Ferramenta | Descrição |
|------|-------------|
| **volatility** | Análise forense de memória |
| **volatility3** | Análise de memória |
| **lime** | Extração de memória Linux |

### Análise forense de redes

| Ferramenta | Descrição |
|------|-------------|
| **wireshark** | Análise de pacotes |
| **tcpdump** | Captura de pacotes |
| **ngrep** | Pesquisa de padrões no tráfego de rede |
| **NetworkMiner** | Análise forense de redes |

### Análise forense de PDF

| Ferramenta | Descrição |
|------|-------------|
| **pdfid** | Analisador de PDF |
| **pdf-parser** | Análise de PDF |
| **peepdf** | Ferramenta de análise de PDF |

---

<a id="12--reporting-tools"></a>
## 12. 📝 Ferramentas de relatórios

| Ferramenta | Descrição |
|------|-------------|
| **cutycapt** | Capturas de imagem de páginas web |
| **faraday** | Ferramenta colaborativa de testes de intrusão |
| **maltego** | Visualização de OSINT |
| **pipal** | Análise de palavras-passe |
| **recordmydesktop** | Gravação do ecrã |
| **cherrytree** | Aplicação de notas |
| **dradis** | Plataforma de colaboração |
| **metagoofil** | Extração de metadados |
| **eyewitness** | Captura de imagem de sites |
| **gowitness** | Captura de imagem de sites |

---

<a id="13--social-engineering"></a>
## 13. 🎭 Engenharia social

| Ferramenta | Descrição |
|------|-------------|
| **setoolkit** | Conjunto de ferramentas de engenharia social |
| **gophish** | Framework de phishing |
| **king-phisher** | Campanha de phishing |
| **evilginx2** | Framework de phishing com intermediário (MitM) |
| **beef-xss** | Exploração de navegadores |
| **maltego** | Relações entre dados OSINT |
| **theHarvester** | Recolha de endereços de email |
| **sherlock** | OSINT de nomes de utilizador |
| **social-analyzer** | Análise de redes sociais |

---

<a id="14--hardware-hacking"></a>
## 14. 🔧 Hacking de hardware

| Ferramenta | Descrição |
|------|-------------|
| **arduino** | Plataforma de hardware |
| **binwalk** | Análise de firmware |
| **flashrom** | Programador de memória flash |
| **openocd** | Depuração no circuito integrado |
| **proxmark3** | Investigação RFID |

---

## 📊 Referência rápida — 50 ferramentas de destaque

| # | Ferramenta | Categoria | Caso de utilização |
|---|------|----------|----------|
| 1 | **nmap** | Análise | Análise de portas |
| 2 | **metasploit** | Exploração | Framework de exploração |
| 3 | **burpsuite** | Web | Testes web |
| 4 | **wireshark** | Captura de tráfego | Análise de pacotes |
| 5 | **john** | Palavras-passe | Quebra de palavras-passe |
| 6 | **hashcat** | Palavras-passe | Quebra com GPU |
| 7 | **hydra** | Palavras-passe | Força bruta online |
| 8 | **sqlmap** | Web | SQL injection |
| 9 | **aircrack-ng** | Redes sem fios | Quebra de palavras-passe WiFi |
| 10 | **nikto** | Web | Análise web |
| 11 | **gobuster** | Web | Descoberta de diretórios por força bruta |
| 12 | **ffuf** | Web | Fuzzing web |
| 13 | **dirb** | Web | Análise de diretórios |
| 14 | **wpscan** | Web | Análise WordPress |
| 15 | **enum4linux** | Informação | Enumeração SMB |
| 16 | **crackmapexec** | Pós-exploração | Testes de intrusão em AD |
| 17 | **mimikatz** | Pós-exploração | Extração de credenciais |
| 18 | **responder** | Exploração | Envenenamento de LLMNR |
| 19 | **impacket** | Exploração | Ferramentas de protocolos |
| 20 | **bloodhound** | Pós-exploração | Mapeamento de AD |
| 21 | **netcat** | Utilitário | Utilitário TCP/UDP |
| 22 | **socat** | Utilitário | Retransmissão de dados |
| 23 | **tcpdump** | Captura de tráfego | Captura de pacotes |
| 24 | **ettercap** | Captura de tráfego | Ataques MitM |
| 25 | **bettercap** | Captura de tráfego | Ataques a redes |
| 26 | **proxychains** | Utilitário | Cadeias de proxies |
| 27 | **msfvenom** | Exploração | Geração de payloads |
| 28 | **searchsploit** | Exploração | Pesquisa de exploits |
| 29 | **theHarvester** | OSINT | Recolha de endereços de email |
| 30 | **maltego** | OSINT | Análise de relações |
| 31 | **recon-ng** | OSINT | Framework de reconhecimento |
| 32 | **amass** | OSINT | Enumeração de subdomínios |
| 33 | **subfinder** | OSINT | Descoberta de subdomínios |
| 34 | **nuclei** | Vulnerabilidades | Analisador baseado em modelos |
| 35 | **zaproxy** | Web | OWASP ZAP |
| 36 | **wifite** | Redes sem fios | Auditoria automatizada de WiFi |
| 37 | **ghidra** | Engenharia inversa (RE) | Reverse engineering |
| 38 | **radare2** | Engenharia inversa (RE) | Análise de binários |
| 39 | **gdb** | Engenharia inversa (RE) | Depurador |
| 40 | **binwalk** | Engenharia inversa (RE) | Análise de firmware |
| 41 | **volatility** | Análise forense | Análise de memória |
| 42 | **autopsy** | Análise forense | Plataforma forense |
| 43 | **foremost** | Análise forense | Recuperação de ficheiros por assinaturas |
| 44 | **setoolkit** | Engenharia social | Conjunto de ferramentas de engenharia social |
| 45 | **beef-xss** | Web | Exploração de navegadores |
| 46 | **linpeas** | Escalada de privilégios (PrivEsc) | Enumeração Linux |
| 47 | **winpeas** | Escalada de privilégios (PrivEsc) | Enumeração Windows |
| 48 | **chisel** | Criação de túneis | Túnel HTTP |
| 49 | **sshuttle** | Criação de túneis | VPN através de SSH |
| 50 | **evil-winrm** | Pós-exploração | Shell WinRM |

---

## 📚 Recursos

- [Ferramentas Kali Linux](https://www.kali.org/tools/)
- [Documentação Kali](https://www.kali.org/docs/)
- [Metapacotes Kali](https://www.kali.org/docs/general-use/metapackages/)

---

<p align="center">
  <b>🐉 Domina o Kali Linux!</b><br>
  <i>Mais de 600 ferramentas ao teu alcance</i>
</p>
