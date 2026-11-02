# 📶 Wifite — Guia de referência da auditoria Wi-Fi automatizada

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗    ██╗██╗███████╗██╗████████╗███████╗
  ██║    ██║██║██╔════╝██║╚══██╔══╝██╔════╝
  ██║ █╗ ██║██║█████╗  ██║   ██║   █████╗  
  ██║███╗██║██║██╔══╝  ██║   ██║   ██╔══╝  
  ╚███╔███╔╝██║██║     ██║   ██║   ███████╗
   ╚══╝╚══╝ ╚═╝╚═╝     ╚═╝   ╚═╝   ╚══════╝
         Ferramenta automatizada de testes de segurança Wi-Fi
```

<p align="center">
  <img src="https://img.shields.io/badge/Wifite-Automated_WiFi-blue?style=for-the-badge" alt="Wifite">
  <img src="https://img.shields.io/badge/WPA2-Cracking-red?style=for-the-badge" alt="WPA2">
  <img src="https://img.shields.io/badge/Python-yellow?style=for-the-badge" alt="Python">
</p>

---

<a id="-table-of-contents"></a>
## 📋 Índice

- [O que é o Wifite](#-what-is-wifite)
- [Instalação](#-installation)
- [Utilização básica](#-basic-usage)
- [Seleção de alvos](#-target-selection)
- [Opções de ataque](#-attack-options)
- [Ataques a WPS](#-wps-attacks)
- [Ataques com PMKID](#-pmkid-attacks)
- [Opções avançadas](#-advanced-options)
- [Opções de recuperação de palavras-passe](#-cracking-options)
- [Exemplos](#-examples)
- [Referência rápida](#-quick-reference)

---

<a id="-what-is-wifite"></a>
## 🎯 O que é o Wifite

O **Wifite** (Wifite2) é uma ferramenta de auditoria automatizada de redes sem fios que:

> **Nota de versão:** Este guia descreve o Wifite2 e conserva os comandos do original. O repositório de derv82 encaminha agora os utilizadores do Wifite2 para a versão mantida por kimocoder e anuncia também o projeto Wifit3. A disponibilidade das opções, os caminhos de saída, a repetição de argumentos e a retoma de sessões dependem da versão; por exemplo, a versão mantida documenta opções explícitas de retoma. A instalação com `setup.py` é um exemplo antigo; o repositório original contém `Wifite.py` com maiúscula, enquanto o comando do guia usa `wifite.py`, uma diferença relevante em sistemas com nomes sensíveis a maiúsculas. [Repositório original](https://github.com/derv82/wifite2), [versão mantida do Wifite2](https://github.com/kimocoder/wifite2).

- 🤖 **Automatiza ataques** — Dispensa a introdução manual de comandos
- 🎯 **Vários alvos** — Permite atacar várias redes
- 🔓 **WPA/WPA2/WEP/WPS** — Abrange estes mecanismos de segurança
- 💾 **Guarda o progresso** — Permite retomar ataques interrompidos
- 🔄 **Captura de PMKID** — Ataques modernos sem um cliente ligado

### Wifite e Wifite2

| Funcionalidade | Wifite (original) | Wifite2 |
|---------|-------------------|---------|
| Manutenção ativa | Não | Sim |
| Python | Python 2 | Python 3 |
| PMKID | Não | Sim |
| Código | Pouco organizado | Reescrita organizada |
| Utilização | Desaconselhada | **Recomendada** |

### Funcionalidades

| Funcionalidade | Descrição |
|---------|-------------|
| **Automatizado** | Auditoria Wi-Fi com intervenção mínima |
| **Seleção inteligente de alvos** | Seleciona alvos pela intensidade do sinal |
| **Tipos de ataque** | WPA, WEP, WPS, PMKID |
| **Recuperação de palavras-passe** | Integração com Hashcat/aircrack |
| **Sessões retomáveis** | Continuar sessões interrompidas |

---

<a id="-installation"></a>
## 🚀 Instalação

### Kali Linux (pré-instalado)

```bash
# Já instalado no Kali
wifite --help

# Atualizar para a versão disponível mais recente
sudo apt update && sudo apt install wifite
```

### Instalar o Wifite2 a partir do GitHub

```bash
# Clonar o repositório
git clone https://github.com/derv82/wifite2.git
cd wifite2

# Executar diretamente
sudo python3 wifite.py

# Ou instalar
sudo python3 setup.py install
```

### Dependências

```bash
# Obrigatório
sudo apt install aircrack-ng

# Recomendado para dispor de todas as funcionalidades
sudo apt install reaver bully pixiewps hashcat hcxdumptool hcxtools
```

### Verificar a instalação

```bash
wifite --help
wifite --version
```

---

<a id="-basic-usage"></a>
## 💻 Utilização básica

### Início rápido

```bash
# Executar wifite (a interface será detetada automaticamente)
sudo wifite

# Indicar a interface
sudo wifite -i wlan0
```

### Fluxo de trabalho básico

```
1. Executar wifite
2. Aguardar que as redes apareçam
3. Premir Ctrl+C para parar a pesquisa
4. Selecionar os alvos pelo número
5. O Wifite executa os ataques automaticamente
6. Recuperar as palavras-passe a partir dos handshakes capturados
```

### Modo interativo

```bash
# Iniciar o wifite
sudo wifite

# A saída apresenta:
   NUM  ESSID              ENCR  POWER  WPS  CLIENT
   ---  -----------------  ----  -----  ---  ------
    1   HomeNetwork        WPA2   -45   Yes     2
    2   Office_WiFi        WPA2   -52   No      0
    3   Free_Hotspot       OPN    -60   No      5

# Introduzir os números dos alvos ou premir Ctrl+C para selecionar todos os visíveis
```

---

<a id="-target-selection"></a>
## 🎯 Seleção de alvos

### Selecionar pelo número

```bash
# Após a pesquisa, selecionar os alvos pelo número
# Exemplo: selecionar os alvos 1 e 3
1, 3

# Selecionar um intervalo
1-5

# Selecionar todos
all
```

### Filtrar por ESSID

```bash
# Selecionar uma rede pelo nome
sudo wifite --essid "TargetNetwork"

# Vários ESSIDs
sudo wifite --essid "Network1" --essid "Network2"
```

### Filtrar por BSSID

```bash
# Selecionar um endereço MAC específico
sudo wifite --bssid AA:BB:CC:DD:EE:FF
```

### Filtrar por cifragem

```bash
# Apenas redes WPA
sudo wifite --wpa

# Apenas redes WEP
sudo wifite --wep

# Apenas redes com WPS ativo
sudo wifite --wps
```

### Filtrar por canal

```bash
# Canal específico
sudo wifite -c 6

# Apenas 5 GHz
sudo wifite --5ghz

# Apenas 2,4 GHz
sudo wifite --2ghz
```

### Filtrar por sinal

```bash
# Intensidade mínima (dBm; quanto mais próximo de 0, mais forte)
sudo wifite --power -50
```

---

<a id="-attack-options"></a>
## ⚔️ Opções de ataque

### Opções WPA/WPA2

```bash
# Definir o tempo limite de desautenticação
sudo wifite --wpa-deauth-timeout 30

# Número de desautenticações a enviar
sudo wifite --num-deauths 5

# Ignorar a captura de handshake; utilizar apenas PMKID
sudo wifite --pmkid
```

### Opções de desautenticação

```bash
# Número de pacotes de desautenticação
sudo wifite --num-deauths 10

# Desautenticação contínua
sudo wifite --num-deauths 0
```

### Ordem dos ataques

```bash
# Ignorar ataques a WPA
sudo wifite --no-wpa

# Ignorar ataques a WEP
sudo wifite --no-wep

# Ignorar ataques a WPS
sudo wifite --no-wps

# Ignorar ataques com PMKID
sudo wifite --no-pmkid
```

### Terminar o NetworkManager

```bash
# Terminar automaticamente os processos que interferem
sudo wifite --kill

# Não terminar processos
sudo wifite --no-kill
```

---

<a id="-wps-attacks"></a>
## 🔓 Ataques a WPS

### Ativar WPS

```bash
# Atacar apenas redes com WPS ativo
sudo wifite --wps

# Modo exclusivo de WPS
sudo wifite --wps-only
```

### Ferramentas para WPS

```bash
# Utilizar o Reaver (predefinição)
sudo wifite --wps --reaver

# Utilizar o Bully
sudo wifite --wps --bully
```

### Ataque Pixie-Dust

```bash
# Ativar o Pixie-Dust (ataque offline a WPS)
sudo wifite --wps --pixie

# Tempo limite do Pixie-Dust
sudo wifite --wps --pixie-timeout 300
```

### Opções WPS

```bash
# Tempo limite de WPS por tentativa de PIN
sudo wifite --wps-time 120

# Número máximo de novas tentativas de WPS
sudo wifite --wps-fails 10

# Tempo limite global de WPS
sudo wifite --wps-timeout 300
```

---

<a id="-pmkid-attacks"></a>
## 🔐 Ataques com PMKID

### O que é o PMKID?

Ataque com PMKID (Pairwise Master Key Identifier, identificador da chave mestra do par):
- ✅ Captura a PMK diretamente do AP
- ✅ Dispensa um cliente ligado
- ✅ Dispensa a desautenticação
- ✅ Funciona na maioria das redes WPA2

> **Correção do original:** Captura-se o **PMKID**, um identificador calculado a partir da PMK, e não a própria PMK. A técnica depende de um AP que forneça material PMKID utilizável e permite testar palavras-passe candidatas offline; não se pode presumir que funcione na maioria das redes WPA2. Pode dispensar um cliente previamente ligado, mas a ferramenta subjacente pode executar outras ações: a opção PMKID, por si só, não garante ausência de desautenticação. [Descrição técnica do ataque](https://hashcat.net/forum/thread-7717.html), [notas do projeto Wifite2](https://github.com/derv82/wifite2/blob/master/PMKID.md).

### Opções PMKID

```bash
# Apenas ataques com PMKID (sem handshake)
sudo wifite --pmkid

# Tempo limite do PMKID
sudo wifite --pmkid-timeout 120
```

### Como funciona o PMKID

```
1. Enviar um pedido de associação ao AP
2. O AP responde com um PMKID numa trama EAPOL
3. Capturar o PMKID
4. Recuperar a palavra-passe offline com o hashcat
```

---

<a id="-advanced-options"></a>
## ⚙️ Opções avançadas

### Opções da interface

```bash
# Indicar a interface
sudo wifite -i wlan0

# Indicar a interface em modo monitor
sudo wifite -i wlan0mon
```

### Opções de saída

```bash
# Saída detalhada
sudo wifite -v
sudo wifite --verbose

# Saída muito detalhada
sudo wifite -vv

# Modo silencioso
sudo wifite --quiet
```

### Opções de temporização

```bash
# Duração da pesquisa (segundos)
sudo wifite --scan-time 30

# Tempo limite global por alvo
sudo wifite --attack-timeout 300
```

### Palavras-passe recuperadas

```bash
# As palavras-passe recuperadas são guardadas em:
# ~/.config/wifite/cracked.txt

# Ver as redes cuja palavra-passe foi recuperada
cat ~/.config/wifite/cracked.txt

# Ou indicar outro ficheiro
sudo wifite --cracked cracked_passwords.txt
```

### Opções para ignorar etapas

```bash
# Ignorar a recuperação da palavra-passe (apenas capturar)
sudo wifite --skip-crack

# Ignorar alvos cuja palavra-passe já foi recuperada
sudo wifite --skip-cracked
```

### MAC aleatório

```bash
# Gerar um endereço MAC aleatório
sudo wifite --random-mac

# Não alterar o MAC
sudo wifite --no-random-mac
```

---

<a id="-cracking-options"></a>
## 🔓 Opções de recuperação de palavras-passe

### Ataque de dicionário

```bash
# Indicar uma lista de palavras
sudo wifite --dict /usr/share/wordlists/rockyou.txt

# Várias listas de palavras
sudo wifite --dict wordlist1.txt --dict wordlist2.txt
```

### Integração com o Hashcat

```bash
# Utilizar o hashcat para recuperar palavras-passe
sudo wifite --hashcat

# Localização do Hashcat
sudo wifite --hashcat-path /usr/bin/hashcat
```

### Opções do Aircrack-ng

```bash
# Utilizar o aircrack-ng (predefinição)
sudo wifite --aircrack

# Caminho do aircrack-ng
sudo wifite --aircrack-path /usr/bin/aircrack-ng
```

### Modo exclusivo de recuperação de palavras-passe

```bash
# Recuperar palavras-passe a partir de ficheiros de handshake existentes
sudo wifite --crack

# Indicar o ficheiro de captura
sudo wifite --crack --dict wordlist.txt /path/to/capture.cap
```

---

<a id="-examples"></a>
## 📋 Exemplos

### Auditoria Wi-Fi básica

```bash
# Pesquisar e atacar todas as redes
sudo wifite

# Atacar com uma lista de palavras personalizada
sudo wifite --dict /usr/share/wordlists/rockyou.txt
```

### Selecionar uma rede específica

```bash
# Selecionar pelo nome
sudo wifite --essid "TargetNetwork" --dict rockyou.txt

# Selecionar pelo MAC
sudo wifite --bssid AA:BB:CC:DD:EE:FF
```

### Ataque exclusivo a WPA2

```bash
# Apenas redes WPA2 com PMKID
sudo wifite --wpa --pmkid --dict rockyou.txt
```

### Ataque a WPS

```bash
# WPS com Pixie-Dust
sudo wifite --wps --pixie

# Apenas força bruta de WPS
sudo wifite --wps-only --bully
```

### Apenas sinais fortes

```bash
# Atacar apenas redes com sinal forte
sudo wifite --power -50 --wpa
```

### Redes de 5 GHz

```bash
# Atacar apenas a banda de 5 GHz
sudo wifite --5ghz --wpa
```

### Auditoria rápida (sem recuperar palavras-passe)

```bash
# Capturar apenas handshakes; recuperar as palavras-passe mais tarde
sudo wifite --skip-crack
```

### Retomar o ataque

```bash
# O Wifite guarda o progresso automaticamente
# Basta executar novamente para retomar
sudo wifite

# Ignorar redes cuja palavra-passe já foi recuperada
sudo wifite --skip-cracked
```

### Modo detalhado

```bash
# Ver todos os detalhes
sudo wifite -vv --dict rockyou.txt
```

### Modo discreto

> **Nota:** Saída silenciosa não equivale a ausência de atividade na rede. O exemplo continua a permitir o envio de tramas de desautenticação; “discreto” é a designação utilizada no original.

```bash
# Mínimo de desautenticações, saída silenciosa
sudo wifite --num-deauths 1 --quiet
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos essenciais

| Finalidade | Comando |
|---------|---------|
| Pesquisa básica | `sudo wifite` |
| Selecionar pelo nome | `sudo wifite --essid "Name"` |
| Selecionar pelo MAC | `sudo wifite --bssid MAC` |
| Apenas WPA | `sudo wifite --wpa` |
| Apenas WPS | `sudo wifite --wps-only` |
| Ataque com PMKID | `sudo wifite --pmkid` |
| Com uma lista de palavras | `sudo wifite --dict wordlist.txt` |
| Ignorar a recuperação da palavra-passe | `sudo wifite --skip-crack` |

### Tipos de ataque

| Ataque | Opção | Alvo |
|--------|------|--------|
| Handshake WPA | `--wpa` | WPA/WPA2-PSK |
| PMKID | `--pmkid` | WPA/WPA2-PSK |
| Pixie-Dust de WPS | `--wps --pixie` | WPS ativo |
| Força bruta de WPS | `--wps-only` | WPS ativo |
| WEP | `--wep` | WEP (obsoleto) |

### Listas de palavras comuns

| Caminho | Descrição |
|------|-------------|
| `/usr/share/wordlists/rockyou.txt` | Palavras-passe mais comuns |
| `/usr/share/wordlists/fasttrack.txt` | Verificação rápida |
| `/usr/share/seclists/Passwords/WiFi-WPA/` | Específicas para Wi-Fi |

### Ficheiros capturados

```
~/.config/wifite/
├── cracked.txt          # Palavras-passe recuperadas
├── hs/                  # Capturas de handshakes
│   └── handshake_*.cap
└── pmkid/               # Capturas de PMKID
    └── pmkid_*.22000
```

### Referência rápida das opções

| Opção | Descrição |
|------|-------------|
| `-i` | Interface |
| `-c` | Canal |
| `-v` | Detalhado |
| `--wpa` | Apenas WPA/WPA2 |
| `--wep` | Apenas WEP |
| `--wps` | WPS ativo |
| `--pmkid` | Ataque com PMKID |
| `--dict` | Lista de palavras |
| `--kill` | Terminar processos |
| `--power` | Sinal mínimo |
| `--5ghz` | Banda de 5 GHz |
| `--2ghz` | Banda de 2,4 GHz |

### Resolução de problemas

```bash
# Interface não encontrada
sudo airmon-ng check kill
sudo wifite -i wlan0

# Nenhuma rede encontrada
# - Verificar se o adaptador suporta o modo monitor
# - Experimentar outras bandas: --5ghz ou --2ghz
# - Aproximar-se dos alvos

# Handshake não capturado
# - Aumentar as desautenticações: --num-deauths 10
# - Aguardar mais tempo: --wpa-deauth-timeout 60
# - Experimentar outros clientes

# A recuperação da palavra-passe falha
# - Utilizar uma lista de palavras mais adequada
# - Experimentar o hashcat com GPU: --hashcat
```

---

<a id="-wifite-vs-manual-aircrack-ng"></a>
## 🔄 Wifite e utilização manual do Aircrack-ng

| Tarefa | Wifite | Suite Aircrack-ng |
|------|--------|-------------------|
| Modo monitor | Automático | `airmon-ng start wlan0` |
| Pesquisa | Automático | `airodump-ng wlan0mon` |
| Alvo | Interativo | Filtragem manual |
| Desautenticação | Automático | `aireplay-ng -0 ...` |
| Captura | Automático | `airodump-ng -w ...` |
| Recuperação da palavra-passe | Automático | `aircrack-ng -w ...` |
| **Esforço** | **Reduzido** | Manual |

---

<a id="-legal-disclaimer"></a>
## ⚠️ Aviso legal

```
⚠️ AVISO: Utilize o Wifite apenas em redes de que SEJA PROPRIETÁRIO ou para as quais tenha 
AUTORIZAÇÃO EXPRESSA POR ESCRITO para realizar testes.

✅ Testes de intrusão autorizados
✅ Auditorias de segurança (com autorização)
✅ Fins educativos (em laboratório)

❌ O acesso não autorizado a redes é ILEGAL
❌ Nunca utilize para fins maliciosos
```

---

<a id="-resources"></a>
## 📚 Recursos

- [Wifite2 no GitHub](https://github.com/derv82/wifite2)
- [Wiki do Wifite](https://github.com/derv82/wifite2/wiki)
- [Aircrack-ng](https://www.aircrack-ng.org/)

### Guias de referência relacionados
- [Aircrack-ng](../../Aircrack-ng/translations/README.pt.md)
- [Hashcat](../../Hashcat/README.md)
- [Comandos Linux](../../Linux-Commands/README.md)

---

<p align="center">
  <b>📶 Auditoria Wi-Fi automatizada!</b><br>
  <i>Wifite — Selecione, clique e teste a segurança (com ética!)</i>
</p>
