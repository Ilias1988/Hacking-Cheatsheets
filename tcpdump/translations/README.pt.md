# 📡 tcpdump — Guia de referência completo

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _             _                       
 | |_ ___ _ __ | |_ _ _ _____ _ __ _ __ 
 | __/ __| '_ \| __| | | '_ ` _ \| '_ \
 | || (__| |_) | |_| |_| | | | | | |_) |
  \__\___| .__/ \__|\__,_|_| |_| | .__/ 
         |_|                     |_|    
      Analisador de pacotes por linha de comandos
```

<p align="center">
  <img src="https://img.shields.io/badge/tcpdump-Packet_Analyzer-blue?style=for-the-badge" alt="tcpdump">
  <img src="https://img.shields.io/badge/Command-Line-red?style=for-the-badge" alt="CLI">
  <img src="https://img.shields.io/badge/BPF-Filters-green?style=for-the-badge" alt="BPF">
  <img src="https://img.shields.io/badge/Cross-Platform-orange?style=for-the-badge" alt="Multiplataforma">
</p>

<p align="center">
  <b>🔍 O clássico analisador de pacotes por linha de comandos</b>
</p>

---

## 📋 Índice

- [Introdução](#-introduction)
- [Instalação](#-installation)
- [Sintaxe básica](#-basic-syntax)
- [Opções comuns](#-common-options)
- [Filtros BPF](#-bpf-filters)
- [Filtros de protocolos](#-protocol-filters)
- [Opções de saída](#-output-options)
- [Exemplos práticos](#-real-world-examples)
- [Referência rápida](#-quick-reference)
- [Dicas e boas práticas](#-tips--best-practices)
- [Recursos](#-resources)

---

<a id="-introduction"></a>
## 🎯 Introdução

**tcpdump** é um analisador de pacotes poderoso, utilizado na linha de comandos e disponível na maioria dos sistemas operativos do tipo Unix. Utiliza a biblioteca libpcap e a sintaxe Berkeley Packet Filter (BPF).

BPF é a linguagem de expressões usada para selecionar os pacotes de interesse. Uma interface é o ponto de captura, como uma placa de rede; pcap é um formato de ficheiro de captura. Os exemplos usam nomes de interface e endereços ilustrativos, que dependem do ambiente.

### Principais funcionalidades

| Funcionalidade | Descrição |
|---------|-------------|
| **Leve** | Sem interface gráfica, com utilização mínima de recursos |
| **Filtros BPF** | Filtragem poderosa de pacotes |
| **Compatível com pcap** | Leitura e escrita de ficheiros pcap |
| **Utilizável em scripts** | Ideal para automatização |
| **Universal** | Disponível em todos os sistemas Unix |

### tcpdump comparado com Wireshark

| Funcionalidade | tcpdump | Wireshark |
|---------|---------|-----------|
| **Interface** | CLI | GUI |
| **Recursos** | Mínimos | Mais elevados |
| **Utilização remota** | SSH nativo | Requer configuração |
| **Utilização em scripts** | Simples | Complexa |
| **Análise** | Básica | Avançada |

---

<a id="-installation"></a>
## 📥 Instalação

### Linux (pré-instalado na maioria das distribuições)
```bash
# Kali/Debian/Ubuntu
sudo apt install tcpdump

# CentOS/RHEL
sudo yum install tcpdump

# Arch
sudo pacman -S tcpdump
```

### macOS (pré-instalado)
```bash
tcpdump --version
```

### Verificar a instalação
```bash
tcpdump --version
tcpdump -D              # Listar interfaces
```

---

<a id="-basic-syntax"></a>
## ⌨️ Sintaxe básica

### Sintaxe geral
```bash
tcpdump [options] [filter expression]
```

### Comandos básicos
```bash
# Capturar na interface predefinida
sudo tcpdump

# Capturar numa interface específica
sudo tcpdump -i eth0

# Capturar um número limitado de pacotes
sudo tcpdump -c 100

# Capturar com saída detalhada
sudo tcpdump -v
sudo tcpdump -vv
sudo tcpdump -vvv
```

---

<a id="-common-options"></a>
## ⚙️ Opções comuns

### Opções de visualização

| Opção | Descrição |
|--------|-------------|
| `-i` | Interface onde capturar |
| `-c` | Número de pacotes a capturar |
| `-n` | Não resolver nomes de máquinas |
| `-nn` | Não resolver nomes de máquinas nem de portas |
| `-v` | Saída detalhada |
| `-vv` | Saída mais detalhada |
| `-vvv` | Nível máximo de detalhe |
| `-q` | Saída reduzida |
| `-t` | Não apresentar a marca temporal |
| `-tt` | Apresentar a marca temporal Unix |
| `-ttt` | Apresentar a diferença de tempo |
| `-tttt` | Apresentar data e hora |

### Opções de captura

> **Precisão do original:** `-s0` usa o snaplen predefinido, atualmente 262144 bytes no [manual oficial](https://github.com/the-tcpdump-group/tcpdump/blob/master/tcpdump.1.in); não significa comprimento ilimitado. «Pacote completo» pressupõe que cabe nesse limite e foi capturado sem truncagem.

| Opção | Descrição |
|--------|-------------|
| `-w` | Guardar num ficheiro pcap |
| `-r` | Ler de um ficheiro pcap |
| `-s` | Comprimento capturado por pacote (bytes) |
| `-s0` | Capturar pacotes completos |
| `-D` | Listar as interfaces disponíveis |

### Opções de saída

| Opção | Descrição |
|--------|-------------|
| `-A` | Apresentar ASCII |
| `-X` | Apresentar hexadecimal e ASCII |
| `-XX` | Apresentar hexadecimal e ASCII com cabeçalhos |
| `-e` | Apresentar o cabeçalho da camada de ligação |
| `-l` | Saída com buffer por linha |

### Combinações comuns
```bash
# Sem resolução DNS, saída detalhada e pacotes completos
sudo tcpdump -nnvvs0 -i eth0

# Guardar num ficheiro
sudo tcpdump -i eth0 -w capture.pcap

# Ler de um ficheiro
tcpdump -r capture.pcap

# Saída ASCII
sudo tcpdump -A -i eth0

# Hexadecimal + ASCII
sudo tcpdump -X -i eth0
```

---

<a id="-bpf-filters"></a>
## 🎣 Filtros BPF

### Filtros de máquinas

```bash
# Tráfego de ou para uma máquina específica
tcpdump host 192.168.1.1

# Apenas a máquina de origem
tcpdump src host 192.168.1.1

# Apenas a máquina de destino
tcpdump dst host 192.168.1.1

# Várias máquinas
tcpdump host 192.168.1.1 or host 192.168.1.2

# Excluir uma máquina
tcpdump not host 192.168.1.1

# Rede ou sub-rede
tcpdump net 192.168.1.0/24
tcpdump src net 10.0.0.0/8
```

### Filtros de portas

```bash
# Porta específica
tcpdump port 80
tcpdump port 443

# Porta de origem
tcpdump src port 443

# Porta de destino
tcpdump dst port 22

# Intervalo de portas
tcpdump portrange 1-1024

# Várias portas
tcpdump port 80 or port 443
tcpdump port 80 or port 443 or port 8080
```

### Filtros de protocolos

```bash
# Por protocolo
tcpdump tcp
tcpdump udp
tcpdump icmp
tcpdump arp

# Protocolo e porta
tcpdump tcp port 80
tcpdump udp port 53
```

### Operadores lógicos

Na shell, operadores como `&&`, `||`, `!` e parênteses podem ter significado próprio. A expressão BPF deve chegar intacta ao tcpdump; a [sintaxe oficial de pcap-filter](https://github.com/the-tcpdump-group/libpcap/blob/master/pcap-filter.manmisc.in) documenta os operadores da linguagem de filtragem.

| Operador | Alternativa | Descrição |
|----------|-------------|-------------|
| `and` | `&&` | E lógico (AND) |
| `or` | `\|\|` | OU lógico (OR) |
| `not` | `!` | Negação lógica (NOT) |
| `()` | | Agrupamento |

### Filtros combinados

```bash
# Máquina E porta
tcpdump host 192.168.1.1 and port 80

# Máquina E protocolo
tcpdump host 192.168.1.1 and tcp

# Filtro complexo
tcpdump 'host 192.168.1.1 and (port 80 or port 443)'

# Excluir tráfego específico
tcpdump not port 22 and not port 53

# Tráfego entre duas máquinas
tcpdump host 192.168.1.1 and host 192.168.1.2
```

---

<a id="-protocol-filters"></a>
## 📡 Filtros de protocolos

### Flags TCP (indicadores de controlo)

```bash
# Pacotes SYN
tcpdump 'tcp[tcpflags] & tcp-syn != 0'
tcpdump 'tcp[13] & 2 != 0'

# Pacotes SYN-ACK
tcpdump 'tcp[tcpflags] & (tcp-syn|tcp-ack) == (tcp-syn|tcp-ack)'

# Pacotes FIN
tcpdump 'tcp[tcpflags] & tcp-fin != 0'

# Pacotes RST
tcpdump 'tcp[tcpflags] & tcp-rst != 0'

# Pacotes PSH
tcpdump 'tcp[tcpflags] & tcp-push != 0'

# Apenas ACK
tcpdump 'tcp[tcpflags] == tcp-ack'
```

### Referência de flags TCP

| Flag | Hexadecimal | Decimal | tcpdump |
|------|-----|-----|---------|
| FIN | 0x01 | 1 | tcp-fin |
| SYN | 0x02 | 2 | tcp-syn |
| RST | 0x04 | 4 | tcp-rst |
| PSH | 0x08 | 8 | tcp-push |
| ACK | 0x10 | 16 | tcp-ack |
| URG | 0x20 | 32 | tcp-urg |

### Tráfego HTTP

> **Precisão do primeiro filtro:** a expressão com `ip[2:2]` seleciona pacotes IPv4 com dados TCP na porta 80, em ambas as direções. Não confirma que o conteúdo seja um pedido HTTP. Os filtros GET/POST seguintes verificam bytes em posições calculadas e não substituem a reconstrução de fluxos. Ver o [manual oficial](https://github.com/the-tcpdump-group/tcpdump/blob/master/tcpdump.1.in).

```bash
# Pedidos HTTP (porta 80)
tcpdump -A 'tcp port 80 and (((ip[2:2] - ((ip[0]&0xf)<<2)) - ((tcp[12]&0xf0)>>2)) != 0)'

# Pedidos GET
tcpdump -A 'tcp port 80 and tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x47455420'

# Pedidos POST
tcpdump -A 'tcp port 80 and tcp[((tcp[12:1] & 0xf0) >> 2):4] = 0x504f5354'

# Captura HTTP simples
tcpdump -A -s0 'tcp port 80'
```

### Tráfego DNS

```bash
# Todo o tráfego DNS
tcpdump port 53
tcpdump udp port 53

# Apenas consultas DNS
tcpdump 'udp port 53 and udp[10] & 0x80 = 0'

# Apenas respostas DNS
tcpdump 'udp port 53 and udp[10] & 0x80 = 0x80'
```

### Tráfego ICMP

```bash
# Todo o tráfego ICMP
tcpdump icmp

# Pedido de eco ICMP (ping)
tcpdump 'icmp[icmptype] == icmp-echo'

# Resposta de eco ICMP
tcpdump 'icmp[icmptype] == icmp-echoreply'

# ICMP de destino inacessível
tcpdump 'icmp[icmptype] == icmp-unreach'
```

### Tráfego ARP

```bash
# Todo o tráfego ARP
tcpdump arp

# Pedidos ARP
tcpdump 'arp[6:2] == 1'

# Respostas ARP
tcpdump 'arp[6:2] == 2'
```

---

<a id="-output-options"></a>
## 💾 Opções de saída

### Guardar num ficheiro

> **Correção do original:** `-w capture.pcap` guarda os pacotes no ficheiro; o pipe para `tee` não duplica automaticamente a apresentação textual dos pacotes. Versões com `--print` permitem apresentar os pacotes mesmo com `-w`. Ver o [manual oficial](https://github.com/the-tcpdump-group/tcpdump/blob/master/tcpdump.1.in).

```bash
# Guardar num ficheiro pcap
tcpdump -w capture.pcap

# Guardar com rotação (100 ficheiros, 1 MB cada)
tcpdump -w capture.pcap -W 100 -C 1

# Guardar com marca temporal no nome do ficheiro
tcpdump -w capture_%Y%m%d_%H%M%S.pcap -G 3600

# Capturar e apresentar
tcpdump -w capture.pcap | tee
```

### Ler de um ficheiro

```bash
# Ler um ficheiro pcap
tcpdump -r capture.pcap

# Ler com filtros
tcpdump -r capture.pcap 'host 192.168.1.1'

# Contar pacotes
tcpdump -r capture.pcap | wc -l

# Ler com saída detalhada
tcpdump -nnvvXSs0 -r capture.pcap
```

### Formatos de visualização

```bash
# Conteúdo ASCII
tcpdump -A -i eth0

# Hexadecimal e ASCII
tcpdump -X -i eth0

# Apenas hexadecimal
tcpdump -x -i eth0

# Com cabeçalhos Ethernet
tcpdump -XX -i eth0

# Apresentar números de sequência absolutos
tcpdump -S -i eth0
```

---

<a id="-real-world-examples"></a>
## 🎬 Exemplos práticos

### Exemplo 1: Capturar tráfego web
```bash
sudo tcpdump -i eth0 -nnvvXSs0 'tcp port 80 or tcp port 443'
```

### Exemplo 2: Capturar consultas DNS
```bash
sudo tcpdump -i eth0 -nn 'udp port 53'
```

### Exemplo 3: Capturar tráfego para uma máquina específica
```bash
sudo tcpdump -i eth0 -nn host 192.168.1.100
```

### Exemplo 4: Encontrar ligações SSH
```bash
sudo tcpdump -i eth0 'tcp port 22'
```

### Exemplo 5: Capturar ICMP (ping)
```bash
sudo tcpdump -i eth0 icmp
```

### Exemplo 6: Detetar uma análise de portas (SYN)
```bash
sudo tcpdump -i eth0 'tcp[tcpflags] == tcp-syn'
```

### Exemplo 7: Capturar apenas tráfego de entrada

> **Erro de quoting no original:** as aspas simples impedem a shell de executar `$(...)`. Assim, o endereço não é calculado na linha apresentada. O exemplo é preservado; «destino igual a um IP local» também não é uma definição universal da direção de captura.
```bash
sudo tcpdump -i eth0 'dst host $(hostname -I | cut -d" " -f1)'
```

### Exemplo 8: Monitorizar uma comunicação específica
```bash
sudo tcpdump -i eth0 'host 192.168.1.1 and host 192.168.1.2'
```

### Exemplo 9: Capturar credenciais FTP
```bash
sudo tcpdump -A -i eth0 'tcp port 21'
```

### Exemplo 10: Guardar para análise no Wireshark
```bash
sudo tcpdump -i eth0 -s0 -w capture.pcap
# Depois abrir capture.pcap no Wireshark
```

### Exemplo 11: Captura remota por SSH
```bash
ssh user@remote 'sudo tcpdump -w - -i eth0' | wireshark -k -i -
```

### Exemplo 12: Capturar excluindo SSH
```bash
sudo tcpdump -i eth0 'not port 22'
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos essenciais

| Tarefa | Comando |
|------|---------|
| Listar interfaces | `tcpdump -D` |
| Captura básica | `tcpdump -i eth0` |
| Sem resolução DNS | `tcpdump -nn` |
| Capturar N pacotes | `tcpdump -c 100` |
| Guardar num ficheiro | `tcpdump -w file.pcap` |
| Ler de um ficheiro | `tcpdump -r file.pcap` |
| Saída detalhada | `tcpdump -v` |
| Saída ASCII | `tcpdump -A` |
| Saída hexadecimal | `tcpdump -X` |
| Pacote completo | `tcpdump -s0` |

### Filtros comuns

| Filtro | Descrição |
|--------|-------------|
| `host 1.2.3.4` | Tráfego de ou para a máquina |
| `port 80` | Tráfego na porta 80 |
| `tcp` | Apenas tráfego TCP |
| `udp` | Apenas tráfego UDP |
| `icmp` | Apenas tráfego ICMP |
| `net 192.168.1.0/24` | Tráfego de ou para a sub-rede |
| `src host 1.2.3.4` | A origem é a máquina indicada |
| `dst port 443` | Porta de destino 443 |

### Combinações de filtros

| Exemplo | Descrição |
|---------|-------------|
| `host A and port 80` | Máquina A, porta 80 |
| `port 80 or port 443` | Porta 80 ou 443 |
| `not port 22` | Excluir SSH |
| `tcp and port 80` | Porta TCP 80 |

---

<a id="-tips--best-practices"></a>
## 💡 Dicas e boas práticas

### Dicas de desempenho

```bash
# Não resolver DNS (mais rápido)
tcpdump -nn

# Limitar o tamanho capturado por pacote
tcpdump -s 96   # Apenas cabeçalhos

# Utilizar filtros BPF para reduzir a carga
tcpdump 'host 192.168.1.1'  # Filtrar ao nível do kernel
```

### Dicas de segurança

> **Correção do comentário:** `-Z user` reduz os privilégios depois de abrir o dispositivo de captura ou o ficheiro de entrada, antes do processamento subsequente; não espera pelo fim da captura. Ver o [manual oficial](https://github.com/the-tcpdump-group/tcpdump/blob/master/tcpdump.1.in).

```bash
# Executar sem root (se estiver configurado)
# Ou utilizar o menor tempo de captura necessário

# Reduzir os privilégios após a captura
tcpdump -Z user
```

### Aliases úteis

```bash
# Adicionar a ~/.bashrc
alias tcpdump='sudo tcpdump -nnvvXSs0'
alias tcpdump-web='sudo tcpdump -nnvvXSs0 port 80 or port 443'
alias tcpdump-dns='sudo tcpdump -nn port 53'
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
> **A monitorização de redes sem autorização é ilegal.**

---

<a id="-resources"></a>
## 📚 Recursos

### Recursos oficiais
- [Manual do tcpdump](https://www.tcpdump.org/manpages/tcpdump.1.html)
- [Site oficial do tcpdump](https://www.tcpdump.org/)
- [Sintaxe dos filtros BPF](https://biot.com/capstats/bpf.html)

### Guias de referência relacionados
- [Wireshark](../../Wireshark/translations/README.pt.md)
- [Nmap](../../Nmap/README.md)
- [Metasploit](../../Metasploit/README.md)

---

<p align="center">
  <b>📡 Domina a análise de pacotes pela linha de comandos!</b><br>
  <i>A alternativa leve ao Wireshark</i>
</p>
