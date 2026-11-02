# 🦈 Wireshark — Guia de referência completo

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
 __        ___           ____  _                _    
 \ \      / (_)_ __ ___ / ___|| |__   __ _ _ __| | __
  \ \ /\ / /| | '__/ _ \\___ \| '_ \ / _` | '__| |/ /
   \ V  V / | | | |  __/ ___) | | | | (_| | |  |   < 
    \_/\_/  |_|_|  \___|____/|_| |_|\__,_|_|  |_|\_\
                                                     
           O analisador de protocolos de rede
```

<p align="center">
  <img src="https://img.shields.io/badge/Wireshark-Network_Analyzer-blue?style=for-the-badge" alt="Wireshark">
  <img src="https://img.shields.io/badge/Packet-Capture-red?style=for-the-badge" alt="Captura de pacotes">
  <img src="https://img.shields.io/badge/Protocol-Analysis-green?style=for-the-badge" alt="Protocolo">
  <img src="https://img.shields.io/badge/Open-Source-orange?style=for-the-badge" alt="Código aberto">
</p>

<p align="center">
  <b>🌐 O analisador de protocolos de rede mais popular do mundo</b>
</p>

---

## 📋 Índice

- [Introdução](#-introduction)
- [Instalação](#-installation)
- [Visão geral da interface](#-interface-overview)
- [Fundamentos da captura](#-capture-basics)
- [Filtros de visualização](#-display-filters)
- [Filtros de captura](#-capture-filters)
- [Análise de protocolos](#-protocol-analysis)
- [Acompanhamento de fluxos](#-following-streams)
- [Estatísticas e análise](#-statistics--analysis)
- [tshark (CLI)](#-tshark-cli)
- [Exemplos práticos](#-real-world-examples)
- [Referência rápida](#-quick-reference)
- [Atalhos de teclado](#-keyboard-shortcuts)
- [Dicas e boas práticas](#-tips--best-practices)
- [Recursos](#-resources)

---

<a id="-introduction"></a>
## 🎯 Introdução

**Wireshark** é um analisador de pacotes gratuito e de código aberto, utilizado para resolver problemas de rede, analisar tráfego, desenvolver software e realizar auditorias de segurança.

Uma captura guarda pacotes observados numa interface. Um filtro de captura decide o que é recolhido; um filtro de visualização decide o que aparece a partir dos dados já capturados. GUI significa interface gráfica; CLI significa interface de linha de comandos.

### Principais funcionalidades

| Funcionalidade | Descrição |
|---------|-------------|
| **Inspeção aprofundada** | Suporte para centenas de protocolos |
| **Captura em tempo real** | Captura de pacotes em tempo real |
| **Análise offline** | Leitura de ficheiros de captura (pcap, pcapng) |
| **Filtros poderosos** | Filtros de visualização e de captura |
| **Multiplataforma** | Windows, Linux, macOS |
| **GUI & CLI** | Wireshark com interface gráfica (GUI) e tshark com linha de comandos (CLI) |

### Casos de utilização

| Caso de utilização | Descrição |
|----------|-------------|
| **Resolução de problemas de rede** | Diagnosticar problemas de conectividade |
| **Análise de segurança** | Detetar tráfego malicioso |
| **Desenvolvimento de protocolos** | Testar novos protocolos |
| **Análise forense** | Analisar tráfego capturado |
| **Formação** | Aprender conceitos de redes |

---

<a id="-installation"></a>
## 📥 Instalação

### Windows
```bash
# Descarregar do site oficial
https://www.wireshark.org/download.html

# Ou utilizar o Chocolatey
choco install wireshark
```

### Linux (Kali/Ubuntu)
```bash
# Kali (pré-instalado)
wireshark

# Ubuntu/Debian
sudo apt install wireshark

# Permitir a captura sem utilizar root
sudo usermod -aG wireshark $USER
# Terminar a sessão e voltar a iniciá-la
```

### macOS
```bash
brew install --cask wireshark
```

### Verificar a instalação
```bash
wireshark --version
tshark --version
```

---

<a id="-interface-overview"></a>
## 🖥️ Visão geral da interface

### Áreas da janela principal

Os nomes originais da interface são mantidos para facilitar a localização: File (ficheiro), Edit (editar), View (ver), Go (navegar), Capture (capturar), Analyze (analisar) e Statistics (estatísticas). Follow significa acompanhar um fluxo; Save As significa guardar como; Open significa abrir; Export Objects significa exportar objetos. Os nomes e atalhos podem variar com a versão e o sistema operativo.

| Área | Descrição |
|------|-------------|
| **Menu Bar (barra de menus)** | File, Edit, View, Go, Capture, Analyze |
| **Main Toolbar (barra de ferramentas principal)** | Botões de acesso rápido |
| **Filter Bar (barra de filtros)** | Introduzir filtros de visualização |
| **Packet List (lista de pacotes)** | Todos os pacotes capturados |
| **Packet Details (detalhes do pacote)** | Camadas de protocolo do pacote selecionado |
| **Packet Bytes (bytes do pacote)** | Dados em bruto, em hexadecimal e ASCII |

### Colunas da lista de pacotes (predefinidas)

| Coluna | Descrição |
|--------|-------------|
| No. (número) | Número do pacote |
| Time (tempo) | Marca temporal |
| Source (origem) | IP/MAC de origem |
| Destination (destino) | IP/MAC de destino |
| Protocol (protocolo) | Protocolo da camada mais elevada |
| Length (comprimento) | Tamanho do pacote em bytes |
| Info (informações) | Informações específicas do protocolo |

---

<a id="-capture-basics"></a>
## 📡 Fundamentos da captura

### Iniciar a captura

```
1. Selecionar a interface no ecrã inicial
2. Clicar em «Start Capture» (barbatana azul de tubarão)
3. Ou: Capture → Start (capturar → iniciar)

Atalho: fazer duplo clique no nome da interface
```

### Parar a captura

```
Clicar no quadrado vermelho da barra de ferramentas
Ou: Capture → Stop (capturar → parar)
Ou: Ctrl+E
```

### Guardar a captura

```
File → Save As
Formatos: pcapng (predefinido), pcap, etc.
```

### Abrir um ficheiro de captura

```
File → Open
Ou: Ctrl+O
Ou: wireshark file.pcap
```

---

<a id="-display-filters"></a>
## 🔍 Filtros de visualização

### Sintaxe dos filtros

No esquema abaixo, field é o campo, operator é o operador e value é o valor. As expressões mantêm a sintaxe original do Wireshark. Ver a [referência oficial dos filtros](https://www.wireshark.org/docs/man-pages/wireshark-filter.html).

Os filtros de visualização utilizam **sintaxe de comparação de campos**:

```
field operator value
```

### Operadores de comparação

| Operador | Descrição | Exemplo |
|----------|-------------|---------|
| `==` ou `eq` | Igual | `ip.addr == 192.168.1.1` |
| `!=` ou `ne` | Diferente | `ip.addr != 10.0.0.1` |
| `>` ou `gt` | Maior do que | `frame.len > 100` |
| `<` ou `lt` | Menor do que | `tcp.port < 1024` |
| `>=` ou `ge` | Maior ou igual | `frame.len >= 64` |
| `<=` ou `le` | Menor ou igual | `tcp.port <= 1024` |
| `contains` | Contém uma cadeia de texto | `http contains "password"` |
| `matches` | Correspondência com expressão regular (regex) | `http.host matches ".*\.com"` |

### Operadores lógicos

| Operador | Descrição | Exemplo |
|----------|-------------|---------|
| `and` ou `&&` | E lógico (AND) | `ip.src == 192.168.1.1 and tcp.port == 80` |
| `or` ou `\|\|` | OU lógico (OR) | `tcp.port == 80 or tcp.port == 443` |
| `not` ou `!` | Negação lógica (NOT) | `not arp` |
| `()` | Agrupamento | `(ip.src == 10.0.0.1) and (tcp or udp)` |

### Filtros de IP

```bash
# Endereço IP específico (origem ou destino)
ip.addr == 192.168.1.1

# Apenas o IP de origem
ip.src == 192.168.1.1

# Apenas o IP de destino
ip.dst == 192.168.1.1

# Intervalo de IPs (sub-rede)
ip.addr == 192.168.1.0/24

# Excluir um IP
ip.addr != 192.168.1.1

# Entre duas máquinas
ip.addr == 192.168.1.1 and ip.addr == 192.168.1.2
```

### Filtros de portas

```bash
# Porta específica (origem ou destino)
tcp.port == 80
udp.port == 53

# Apenas a porta de origem
tcp.srcport == 443

# Apenas a porta de destino
tcp.dstport == 22

# Intervalo de portas
tcp.port >= 1 and tcp.port <= 1024

# Várias portas
tcp.port == 80 or tcp.port == 443 or tcp.port == 8080
tcp.port in {80 443 8080}
```

### Filtros de protocolos

```bash
# Pelo nome do protocolo
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

# Específicos de HTTP
http.request
http.response
http.request.method == "GET"
http.request.method == "POST"
http.response.code == 200
http.response.code >= 400

# Específicos de DNS
dns.qry.name == "google.com"
dns.qry.type == 1          # Registo A
dns.flags.response == 1    # Respostas DNS

# Específicos de TCP
tcp.flags.syn == 1         # Pacotes SYN
tcp.flags.fin == 1         # Pacotes FIN
tcp.flags.reset == 1       # Pacotes RST
tcp.analysis.retransmission  # Retransmissões
tcp.stream == 5            # Fluxo específico
```

### Filtros de conteúdo

```bash
# Contém uma cadeia de texto
http contains "password"
tcp contains "login"
frame contains "secret"

# Conteúdo HTTP
http.request.uri contains "admin"
http.host == "example.com"
http.user_agent contains "Mozilla"
http.cookie contains "session"

# Correspondência sem distinguir maiúsculas de minúsculas
http.host matches "(?i)example"
```

### Exemplos de filtros comuns

> **Precisão do original:** `tcp.flags.syn == 1 or tcp.flags.ack == 1` seleciona qualquer pacote com SYN ou ACK, incluindo muitos pacotes de dados; não isola apenas os três pacotes do handshake. É uma consequência da condição lógica apresentada. Os filtros `tls` e `http` também identificam protocolos reconhecidos, não todos os serviços de uma dada porta.

```bash
# Apenas tráfego HTTP
http

# Tráfego HTTPS
tls

# Consultas DNS
dns.flags.response == 0

# Ligações falhadas (RST)
tcp.flags.reset == 1

# Pacotes grandes
frame.len > 1000

# Endereço MAC específico
eth.addr == aa:bb:cc:dd:ee:ff

# Tráfego de difusão (broadcast)
eth.dst == ff:ff:ff:ff:ff:ff

# Tráfego não local
!(ip.addr == 192.168.0.0/16)

# Estabelecimento de ligação TCP em três passos (3-way handshake)
tcp.flags.syn == 1 or tcp.flags.ack == 1

# Pedidos HTTP POST com dados
http.request.method == "POST" and http.file_data
```

---

<a id="-capture-filters"></a>
## 🎣 Filtros de captura

### Sintaxe BPF (Berkeley Packet Filter)

Os filtros de captura utilizam **sintaxe BPF**, que é diferente da dos filtros de visualização.

### Sintaxe básica

```bash
# Máquina
host 192.168.1.1
src host 192.168.1.1
dst host 192.168.1.1

# Rede
net 192.168.1.0/24
src net 10.0.0.0/8

# Porta
port 80
src port 443
dst port 22
portrange 1-1024

# Protocolo
tcp
udp
icmp
arp
```

### Filtros combinados

```bash
# Máquina e porta
host 192.168.1.1 and port 80

# Comunicação específica
host 192.168.1.1 and host 192.168.1.2

# Excluir tráfego
not port 22
not host 10.0.0.1

# Filtro complexo
tcp and (port 80 or port 443) and host 192.168.1.1
```

### Filtros de captura comuns

| Finalidade | Filtro |
|---------|--------|
| Máquina específica | `host 192.168.1.1` |
| Tráfego web | `port 80 or port 443` |
| Apenas DNS | `port 53` |
| Apenas SSH | `port 22` |
| Excluir difusão (broadcast) | `not broadcast` |
| Sub-rede específica | `net 192.168.1.0/24` |

---

<a id="-protocol-analysis"></a>
## 🔬 Análise de protocolos

### Análise de TCP

```bash
# Flags TCP (indicadores de controlo)
tcp.flags.syn == 1 and tcp.flags.ack == 0    # Apenas SYN
tcp.flags.syn == 1 and tcp.flags.ack == 1    # SYN-ACK
tcp.flags.fin == 1                            # FIN
tcp.flags.reset == 1                          # RST

# Problemas TCP
tcp.analysis.retransmission
tcp.analysis.duplicate_ack
tcp.analysis.zero_window
tcp.analysis.lost_segment

# Fluxo TCP
tcp.stream == 0
```

### Análise de HTTP

```bash
# Pedidos HTTP
http.request
http.request.method == "GET"
http.request.method == "POST"
http.request.uri contains "/login"

# Respostas HTTP
http.response
http.response.code == 200
http.response.code == 404
http.response.code >= 400    # Erros

# Conteúdo HTTP
http.content_type contains "json"
http.content_type contains "html"
http.file_data contains "password"
```

### Análise de DNS

```bash
# Consultas DNS
dns.flags.response == 0
dns.qry.name == "example.com"
dns.qry.type == 1      # Registo A
dns.qry.type == 28     # Registo AAAA
dns.qry.type == 5      # CNAME
dns.qry.type == 15     # MX

# Respostas DNS
dns.flags.response == 1
dns.flags.rcode != 0   # Erros
```

### Análise de TLS/SSL

> **Precisão da versão:** `tls.record.version == 0x0303` não prova que foi negociado TLS 1.2. O TLS 1.3 usa esse valor no campo legado da camada de registo; confirma a versão negociada no handshake e em supported_versions. Ver a [especificação TLS 1.3, RFC 8446](https://www.rfc-editor.org/rfc/rfc8446).

```bash
# Tráfego TLS
tls

# Estabelecimento da sessão TLS (handshake)
tls.handshake
tls.handshake.type == 1     # Client Hello
tls.handshake.type == 2     # Server Hello

# Certificados
tls.handshake.certificate

# Versão TLS
tls.record.version == 0x0303  # TLS 1.2
```

### Análise de ARP

```bash
# Todo o tráfego ARP
arp

# Pedidos ARP
arp.opcode == 1

# Respostas ARP
arp.opcode == 2

# ARP gratuito (possível falsificação)
arp.isgratuitous == 1
```

---

<a id="-following-streams"></a>
## 🔗 Acompanhamento de fluxos

### Acompanhar um fluxo TCP

Os percursos de menu abaixo mantêm os rótulos em inglês: clica com o botão direito num pacote e escolhe Follow (acompanhar), seguido do tipo de fluxo.

```
Clicar com o botão direito no pacote → Follow → TCP Stream

Ou: Analyze → Follow → TCP Stream
```

### Acompanhar um fluxo HTTP

```
Clicar com o botão direito no pacote HTTP → Follow → HTTP Stream
```

### Acompanhar um fluxo UDP

```
Clicar com o botão direito no pacote UDP → Follow → UDP Stream
```

### Janela de fluxo

- Mostra a comunicação completa
- Distingue cliente e servidor por cores
- Permite guardar como texto ou dados em bruto
- Tem botões de filtro por direção

---

<a id="-statistics--analysis"></a>
## 📊 Estatísticas e análise

### Hierarquia de protocolos

```
Statistics → Protocol Hierarchy

Mostra a distribuição dos protocolos capturados
```

### Comunicações

```
Statistics → Conversations

Ver todas as comunicações por:
- Ethernet
- IPv4/IPv6
- TCP
- UDP
```

### Pontos de comunicação (endpoints)

```
Statistics → Endpoints

Listar todos os pontos de comunicação distintos
```

### Gráfico de entrada/saída (IO Graph)

```
Statistics → IO Graph

Visualizar o tráfego ao longo do tempo
```

### Informações de diagnóstico (Expert Information)

Expert Information reúne observações do analisador; são indícios a interpretar no contexto da captura, não uma confirmação automática de incidente.

```
Analyze → Expert Information

Mostra avisos, erros e notas:
- Retransmissões TCP
- Pacotes malformados
- Erros
```

### Estatísticas de HTTP

```
Statistics → HTTP → Requests
Statistics → HTTP → Packet Counter
```

---

<a id="-tshark-cli"></a>
## 💻 tshark (CLI)

### Utilização básica

```bash
# Capturar pacotes
tshark -i eth0

# Ler um ficheiro de captura
tshark -r capture.pcap

# Guardar num ficheiro
tshark -i eth0 -w output.pcap
```

### Aplicar filtros

```bash
# Filtro de captura
tshark -i eth0 -f "port 80"

# Filtro de visualização
tshark -r capture.pcap -Y "http"
```

### Opções de saída

```bash
# Mostrar campos específicos
tshark -r capture.pcap -T fields -e ip.src -e ip.dst -e tcp.port

# Saída JSON
tshark -r capture.pcap -T json

# Mostrar estatísticas
tshark -r capture.pcap -q -z io,stat,1
```

### Exemplos comuns de tshark

```bash
# Capturar pedidos HTTP em tempo real
tshark -i eth0 -Y "http.request" -T fields -e http.host -e http.request.uri

# Extrair consultas DNS
tshark -r capture.pcap -Y "dns.flags.response == 0" -T fields -e dns.qry.name

# Contar pacotes por IP
tshark -r capture.pcap -q -z ip_hosts,tree

# Estatísticas de HTTP
tshark -r capture.pcap -q -z http,tree
```

---

<a id="-real-world-examples"></a>
## 🎬 Exemplos práticos

### Exemplo 1: Capturar tráfego HTTP
```bash
# Filtro de captura: port 80
# Filtro de visualização: http
# Acompanhar o fluxo HTTP para ver os pedidos e respostas completos
```

### Exemplo 2: Encontrar credenciais
```bash
# Filtro de visualização
http.request.method == "POST" and http contains "password"

# Ou, para FTP
ftp contains "PASS"
```

### Exemplo 3: Detetar uma análise de portas
```bash
# Vários SYN dirigidos a portas diferentes
tcp.flags.syn == 1 and tcp.flags.ack == 0

# Procurar muitos destinos a partir de uma única origem
Statistics → Conversations → TCP
```

### Exemplo 4: Exfiltração por DNS

> Estes filtros são pistas para investigação: tamanho superior a 512 e consultas TXT também podem ocorrer em tráfego legítimo. O primeiro filtro mede o tamanho da trama com DNS, não apenas o tamanho do nome consultado.
```bash
# Consultas DNS grandes
dns and frame.len > 512

# Registos TXT invulgares
dns.qry.type == 16
```

### Exemplo 5: Extrair ficheiros
```
File → Export Objects → HTTP

Selecionar os ficheiros a exportar
```

### Exemplo 6: Analisar o handshake TLS
```bash
tls.handshake

# Consultar o Client Hello para procurar o SNI
tls.handshake.type == 1
```

### Exemplo 7: Encontrar palavras-passe em texto simples
```bash
http contains "password" or http contains "passwd" or ftp or telnet
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Filtros de visualização essenciais

| Finalidade | Filtro |
|---------|--------|
| Tráfego HTTP | `http` |
| Tráfego DNS | `dns` |
| IP específico | `ip.addr == 192.168.1.1` |
| Porta específica | `tcp.port == 80` |
| Problemas TCP | `tcp.analysis.flags` |
| Erros HTTP | `http.response.code >= 400` |
| Contém uma cadeia de texto | `frame contains "text"` |
| Excluir um protocolo | `not arp` |

### Filtros de captura comuns

| Finalidade | Filtro |
|---------|--------|
| Máquina específica | `host 192.168.1.1` |
| Tráfego web | `port 80 or port 443` |
| Apenas DNS | `port 53` |
| Excluir difusão (broadcast) | `not broadcast` |

---

<a id="-keyboard-shortcuts"></a>
## ⌨️ Atalhos de teclado

| Atalho | Ação |
|----------|--------|
| `Ctrl+E` | Iniciar/parar a captura |
| `Ctrl+K` | Opções de captura |
| `Ctrl+O` | Abrir ficheiro |
| `Ctrl+S` | Guardar ficheiro |
| `Ctrl+F` | Encontrar um pacote |
| `Ctrl+G` | Ir para um pacote |
| `Ctrl+→` | Pacote seguinte na comunicação |
| `Ctrl+←` | Pacote anterior na comunicação |
| `Ctrl+.` | Pacote marcado seguinte |
| `Ctrl+M` | Marcar/desmarcar um pacote |
| `Ctrl+Shift+M` | Marcar todos os pacotes apresentados |
| `Space` | Alternar a árvore de protocolos |
| `Tab` | Painel seguinte |

---

<a id="-tips--best-practices"></a>
## 💡 Dicas e boas práticas

### Dicas de captura

1. **Utilizar filtros de captura**
   ```
   Reduzir o tamanho do ficheiro aplicando filtros durante a captura
   ```

2. **Buffer circular para capturas longas**
   ```
   Capture → Options → Output → Ring buffer
   ```

3. **Capturar remotamente**
   ```bash
   ssh user@host tcpdump -w - | wireshark -k -i -
   ```

### Dicas de análise

1. **Regras de cores**
   ```
   View → Coloring Rules
   Personalizar as cores dos protocolos
   ```

2. **Visualização do tempo**
   ```
   View → Time Display Format
   Experimentar «Seconds Since Beginning of Capture» (segundos desde o início da captura)
   ```

3. **Resolução de nomes**
   ```
   View → Name Resolution
   Ativar ou desativar conforme necessário
   ```

---

## ⚠️ Aviso legal

> **AVISO:** A captura de pacotes de rede deve ser utilizada apenas para **fins autorizados**.
> 
> - ✅ Capturar tráfego em redes que te pertencem
> - ✅ Testar a segurança com autorização
> - ✅ Resolver problemas em sistemas autorizados
> - ❌ Nunca capturar tráfego em redes sem autorização
> - ❌ Capturar tráfego de terceiros pode ser ilegal
> 
> **A monitorização de redes sem autorização é ilegal na maioria das jurisdições.**

---

<a id="-resources"></a>
## 📚 Recursos

### Recursos oficiais
- [Site oficial do Wireshark](https://www.wireshark.org/)
- [Guia do utilizador do Wireshark](https://www.wireshark.org/docs/wsug_html/)
- [Referência de filtros de visualização](https://www.wireshark.org/docs/dfref/)
- [Wiki do Wireshark](https://wiki.wireshark.org/)

### Recursos de aprendizagem
- [Tutorial do Wireshark](https://www.wireshark.org/docs/wsug_html/)
- [Capturas de exemplo](https://wiki.wireshark.org/SampleCaptures)

### Guias de referência relacionados
- [Nmap](../../Nmap/README.md)
- [Metasploit](../../Metasploit/README.md)
- [Hydra](../../Hydra/translations/README.pt.md)

---

<p align="center">
  <b>🦈 Domina a análise de redes!</b><br>
  <i>Observa tudo o que passa na rede</i>
</p>
