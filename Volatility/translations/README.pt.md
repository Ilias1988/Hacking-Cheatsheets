# 🧠 Volatility — Guia de referência de análise forense de memória

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗   ██╗ ██████╗ ██╗      █████╗ ████████╗██╗██╗     ██╗████████╗██╗   ██╗
  ██║   ██║██╔═══██╗██║     ██╔══██╗╚══██╔══╝██║██║     ██║╚══██╔══╝╚██╗ ██╔╝
  ██║   ██║██║   ██║██║     ███████║   ██║   ██║██║     ██║   ██║    ╚████╔╝ 
  ╚██╗ ██╔╝██║   ██║██║     ██╔══██║   ██║   ██║██║     ██║   ██║     ╚██╔╝  
   ╚████╔╝ ╚██████╔╝███████╗██║  ██║   ██║   ██║███████╗██║   ██║      ██║   
    ╚═══╝   ╚═════╝ ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝╚══════╝╚═╝   ╚═╝      ╚═╝   
                      Framework de análise forense de memória
```

<p align="center">
  <img src="https://img.shields.io/badge/Volatility-Memory_Forensics-purple?style=for-the-badge" alt="Volatility">
  <img src="https://img.shields.io/badge/DFIR-blue?style=for-the-badge" alt="DFIR">
  <img src="https://img.shields.io/badge/Incident_Response-red?style=for-the-badge" alt="IR">
</p>

> **Notas de versão e precisão:** RAM é memória volátil; um dump é uma captura dessa memória. A maior parte dos exemplos utiliza Volatility 2, uma linha antiga baseada em Python 2.7. O [Volatility 3](https://volatility3.readthedocs.io/en/latest/getting-started-windows-tutorial.html) utiliza tabelas de símbolos e plugins com nomes/opções próprios; a identificação automática não elimina a necessidade de símbolos compatíveis. Os formatos suportados dependem da versão e dos componentes instalados, e alguns plugins citados são externos ou históricos. Os exemplos YARA misturam a utilização de ficheiros e regras: a [referência oficial do Vol2](https://github.com/volatilityfoundation/volatility/wiki/Command-Reference-Mal#yarascan) distingue `--yara-file` de `--yara-rules`. A tabela «apenas texto» para Vol2 é uma simplificação; a saída depende do plugin/renderizador. Uma diferença entre listas ou um resultado de malfind é um indício que requer análise, não prova isolada de malware. Os comandos originais foram preservados.

---

<a id="-table-of-contents"></a>
## 📋 Índice

- [O que é o Volatility](#-what-is-volatility)
- [Instalação](#-installation)
- [Utilização básica](#-basic-usage)
- [Identificação da imagem](#-image-identification)
- [Análise de processos](#-process-analysis)
- [Análise de rede](#-network-analysis)
- [Análise do registo](#-registry-analysis)
- [Análise de malware](#-malware-analysis)
- [Análise de utilizadores e credenciais](#-user--credential-analysis)
- [Análise de ficheiros e DLL](#-file--dll-analysis)
- [Análise cronológica](#-timeline-analysis)
- [Volatility 3](#-volatility-3)

---

<a id="-what-is-volatility"></a>
## 🎯 O que é o Volatility

**Volatility** é uma framework de código aberto para analisar capturas de memória RAM. É útil para:

- 🔍 **Resposta a incidentes** — Identificar malware ativo
- 🦠 **Análise de malware** — Extrair processos ocultos
- 🔐 **Recuperação de credenciais** — Extrair palavras-passe/hashes
- 📊 **Análise cronológica** — Reconstruir eventos
- 🕵️ **Procura proativa de ameaças** — Encontrar indicadores de comprometimento

### Formatos de memória suportados

| Formato | Descrição |
|--------|-------------|
| Raw (.raw, .mem) | Captura de memória em formato bruto |
| EWF (.E01) | Formato EnCase |
| AFF | Advanced Forensics Format (formato forense avançado) |
| Captura de falha | Capturas de falhas do Windows |
| VMware (.vmem) | Snapshots de VMware |
| VirtualBox (.sav) | Snapshots de VirtualBox |
| HPAK (.hpak) | Formato FDPro |
| LiME (.lime) | Linux Memory Extractor (captura de memória Linux) |

---

<a id="-installation"></a>
## 🚀 Instalação

### Volatility 2 (versão antiga)

```bash
# Clonar o repositório
git clone https://github.com/volatilityfoundation/volatility.git
cd volatility

# Instalar as dependências
pip install pycrypto distorm3 yara-python pillow openpyxl

# Executar
python vol.py -h
```

### Volatility 3 (versão atual)

```bash
# Instalar através de pip
pip3 install volatility3

# Ou clonar o repositório
git clone https://github.com/volatilityfoundation/volatility3.git
cd volatility3
pip3 install -r requirements.txt

# Executar
python3 vol.py -h
vol -h  # Se instalado através de pip
```

### Descarregar tabelas de símbolos (Vol3)

```bash
# Símbolos Windows
wget https://downloads.volatilityfoundation.org/volatility3/symbols/windows.zip

# Símbolos Linux
wget https://downloads.volatilityfoundation.org/volatility3/symbols/linux.zip

# Símbolos Mac
wget https://downloads.volatilityfoundation.org/volatility3/symbols/mac.zip

# Extrair para volatility3/symbols/
```

---

<a id="-basic-usage"></a>
## 💻 Utilização básica

### Sintaxe do Volatility 2

```bash
# Sintaxe geral
python vol.py -f <memory_dump> --profile=<profile> <plugin>

# Exemplo
python vol.py -f memory.raw --profile=Win7SP1x64 pslist
```

### Sintaxe do Volatility 3

```bash
# Sintaxe geral (identificação automática, conforme descrito no original)
vol -f <memory_dump> <plugin>

# Exemplo
vol -f memory.raw windows.pslist
```

### Opções comuns

```bash
# Volatility 2
-f FILE          # Ficheiro de captura de memória
--profile=PROFILE # Perfil do sistema operativo
-h               # Ajuda
--info           # Listar os plugins disponíveis

# Volatility 3
-f FILE          # Ficheiro de captura de memória
-o DIR           # Diretório de saída
-r RENDERER      # Formato de saída (pretty, json, csv)
-p PLUGIN_DIR    # Plugins adicionais
-h               # Ajuda
```

---

<a id="-image-identification"></a>
## 🔍 Identificação da imagem

### Identificar o perfil (Vol2)

```bash
# Obter os perfis sugeridos
python vol.py -f memory.raw imageinfo

# Informações mais detalhadas
python vol.py -f memory.raw kdbgscan
```

### Identificar o sistema operativo (Vol3)

```bash
# Identificação automática (Vol3 não utiliza um perfil manual do Vol2)
vol -f memory.raw windows.info

# Linux
vol -f memory.lime linux.info

# Mac
vol -f memory.raw mac.info
```

### Perfis comuns (Vol2)

| Sistema operativo | Perfil |
|----|---------|
| Windows XP SP2 x86 | WinXPSP2x86 |
| Windows XP SP3 x86 | WinXPSP3x86 |
| Windows 7 SP1 x86 | Win7SP1x86 |
| Windows 7 SP1 x64 | Win7SP1x64 |
| Windows 10 x64 | Win10x64_* |
| Windows Server 2012 | Win2012R2x64 |

---

<a id="-process-analysis"></a>
## ⚙️ Análise de processos

### Listar processos

```bash
# Volatility 2
python vol.py -f memory.raw --profile=Win7SP1x64 pslist
python vol.py -f memory.raw --profile=Win7SP1x64 pstree  # Vista em árvore
python vol.py -f memory.raw --profile=Win7SP1x64 psscan  # Processos ocultos

# Volatility 3
vol -f memory.raw windows.pslist
vol -f memory.raw windows.pstree
vol -f memory.raw windows.psscan
```

### Detalhes dos processos

```bash
# Argumentos da linha de comandos
python vol.py -f memory.raw --profile=Win7SP1x64 cmdline
vol -f memory.raw windows.cmdline

# Variáveis de ambiente
python vol.py -f memory.raw --profile=Win7SP1x64 envars

# Handles (identificadores de recursos) dos processos
python vol.py -f memory.raw --profile=Win7SP1x64 handles -p <PID>
vol -f memory.raw windows.handles --pid <PID>

# DLL carregadas
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist -p <PID>
vol -f memory.raw windows.dlllist --pid <PID>
```

### Detetar processos ocultos

```bash
# Comparar pslist e psscan
python vol.py -f memory.raw --profile=Win7SP1x64 psxview

# Deteção de process hollowing (substituição do conteúdo de processos)
python vol.py -f memory.raw --profile=Win7SP1x64 hollowfind
```

### Extrair o processo

```bash
# Extrair o executável
python vol.py -f memory.raw --profile=Win7SP1x64 procdump -p <PID> -D output/
vol -f memory.raw windows.pslist --pid <PID> --dump

# Extrair a memória
python vol.py -f memory.raw --profile=Win7SP1x64 memdump -p <PID> -D output/
vol -f memory.raw windows.memmap --pid <PID> --dump
```

---

<a id="-network-analysis"></a>
## 🌐 Análise de rede

### Ligações de rede

```bash
# Ligações ativas (Vol2)
python vol.py -f memory.raw --profile=Win7SP1x64 netscan    # Vista+
python vol.py -f memory.raw --profile=WinXPSP3x86 connections # XP
python vol.py -f memory.raw --profile=WinXPSP3x86 connscan    # XP

# Vol3
vol -f memory.raw windows.netscan
vol -f memory.raw windows.netstat
```

### Campos de saída

| Campo | Descrição |
|-------|-------------|
| Offset | Deslocamento na memória |
| Proto | TCP/UDP |
| LocalAddr | IP:porta local |
| ForeignAddr | IP:porta remoto |
| State | Estado da ligação |
| PID | ID do processo |
| Owner | Nome do processo |

### Artefactos de rede

```bash
# Sockets
python vol.py -f memory.raw --profile=WinXPSP3x86 sockets
python vol.py -f memory.raw --profile=WinXPSP3x86 sockscan
```

---

<a id="-registry-analysis"></a>
## 📝 Análise do registo

### Listar hives do registo

```bash
# Vol2
python vol.py -f memory.raw --profile=Win7SP1x64 hivelist

# Vol3
vol -f memory.raw windows.registry.hivelist
```

### Extrair o registo

```bash
# Mostrar uma chave específica
python vol.py -f memory.raw --profile=Win7SP1x64 printkey -K "Software\Microsoft\Windows\CurrentVersion\Run"
vol -f memory.raw windows.registry.printkey --key "Software\Microsoft\Windows\CurrentVersion\Run"

# Extrair a hive para um ficheiro
python vol.py -f memory.raw --profile=Win7SP1x64 dumpregistry -D output/
```

### Chaves de registo importantes

```bash
# Chaves de execução automática
printkey -K "Software\Microsoft\Windows\CurrentVersion\Run"
printkey -K "Software\Microsoft\Windows\CurrentVersion\RunOnce"

# Serviços
printkey -K "SYSTEM\CurrentControlSet\Services"

# Contas de utilizador
printkey -K "SAM\Domains\Account\Users"

# Ficheiros recentes
printkey -K "Software\Microsoft\Windows\CurrentVersion\Explorer\RecentDocs"
```

### User Assist (programas executados)

```bash
# Vol2
python vol.py -f memory.raw --profile=Win7SP1x64 userassist

# Vol3
vol -f memory.raw windows.registry.userassist
```

### Shellbags

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 shellbags
```

---

<a id="-malware-analysis"></a>
## 🦠 Análise de malware

### Malfind (código injetado)

```bash
# Procurar código injetado
python vol.py -f memory.raw --profile=Win7SP1x64 malfind
vol -f memory.raw windows.malfind

# Extrair para ficheiros
python vol.py -f memory.raw --profile=Win7SP1x64 malfind -D output/
```

### Hooks da SSDT

```bash
# Tabela de descritores de serviços do sistema
python vol.py -f memory.raw --profile=Win7SP1x64 ssdt

# Comparar com um sistema sem comprometimento
python vol.py -f memory.raw --profile=Win7SP1x64 ssdt | grep -v "ntoskrnl\|win32k"
```

### Análise de IDT/GDT

```bash
# Tabela de descritores de interrupções
python vol.py -f memory.raw --profile=Win7SP1x64 idt

# Tabela global de descritores
python vol.py -f memory.raw --profile=Win7SP1x64 gdt
```

### Análise de controladores

```bash
# Listar controladores
python vol.py -f memory.raw --profile=Win7SP1x64 driverscan
vol -f memory.raw windows.driverscan

# Lista de módulos
python vol.py -f memory.raw --profile=Win7SP1x64 modules
vol -f memory.raw windows.modules

# Módulos descarregados da memória
python vol.py -f memory.raw --profile=Win7SP1x64 unloadedmodules

# Hooks IRP de controladores
python vol.py -f memory.raw --profile=Win7SP1x64 driverirp
```

### Análise com YARA

```bash
# Analisar com regras YARA
python vol.py -f memory.raw --profile=Win7SP1x64 yarascan -Y "rule_file.yar"
python vol.py -f memory.raw --profile=Win7SP1x64 yarascan -y "string_to_find"

# Vol3
vol -f memory.raw yarascan.YaraScan --yara-file rules.yar
```

### Hooks de API

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 apihooks
```

---

<a id="-user--credential-analysis"></a>
## 🔐 Análise de utilizadores e credenciais

### Informações de utilizadores

```bash
# Obter os SID dos utilizadores
python vol.py -f memory.raw --profile=Win7SP1x64 getsids

# Último utilizador com sessão iniciada
python vol.py -f memory.raw --profile=Win7SP1x64 printkey -K "SOFTWARE\Microsoft\Windows\CurrentVersion\Authentication\LogonUI"
```

### Hashes de palavras-passe

```bash
# Extrair hashes do SAM
python vol.py -f memory.raw --profile=Win7SP1x64 hashdump
vol -f memory.raw windows.hashdump

# Segredos LSA
python vol.py -f memory.raw --profile=Win7SP1x64 lsadump

# Credenciais em cache
python vol.py -f memory.raw --profile=Win7SP1x64 cachedump
```

### Integração com Mimikatz

```bash
# Extrair credenciais (plugin mimikatz)
python vol.py -f memory.raw --profile=Win7SP1x64 mimikatz

# Vol3 (incluído)
vol -f memory.raw windows.lsadump
```

### Área de transferência

```bash
python vol.py -f memory.raw --profile=Win7SP1x64 clipboard
```

---

<a id="-file--dll-analysis"></a>
## 📁 Análise de ficheiros e DLL

### Pesquisa de ficheiros

```bash
# Procurar ficheiros na memória
python vol.py -f memory.raw --profile=Win7SP1x64 filescan
vol -f memory.raw windows.filescan

# Filtrar ficheiros específicos
python vol.py -f memory.raw --profile=Win7SP1x64 filescan | grep -i "\.exe$"
python vol.py -f memory.raw --profile=Win7SP1x64 filescan | grep -i "password"
```

### Extrair ficheiros

```bash
# Extrair um ficheiro pelo deslocamento
python vol.py -f memory.raw --profile=Win7SP1x64 dumpfiles -Q <OFFSET> -D output/
vol -f memory.raw windows.dumpfiles --virtaddr <OFFSET>

# Extrair todos os ficheiros
python vol.py -f memory.raw --profile=Win7SP1x64 dumpfiles -D output/
```

### Análise de DLL

```bash
# Listar DLL
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist
vol -f memory.raw windows.dlllist

# Processo específico
python vol.py -f memory.raw --profile=Win7SP1x64 dlllist -p <PID>

# Extrair uma DLL
python vol.py -f memory.raw --profile=Win7SP1x64 dlldump -p <PID> -D output/
```

### Cadeias de caracteres na memória

```bash
# Extrair cadeias de caracteres
python vol.py -f memory.raw --profile=Win7SP1x64 strings -s strings.txt

# Cadeias de caracteres de um processo específico
python vol.py -f memory.raw --profile=Win7SP1x64 strings -s strings.txt -p <PID>
```

---

<a id="-timeline-analysis"></a>
## 📅 Análise cronológica

### Criação de cronologia

```bash
# Criar uma cronologia
python vol.py -f memory.raw --profile=Win7SP1x64 timeliner --output-file timeline.body

# Vol3
vol -f memory.raw timeliner.Timeliner
```

### Análise da MFT

```bash
# Master File Table (tabela principal de ficheiros)
python vol.py -f memory.raw --profile=Win7SP1x64 mftparser
vol -f memory.raw windows.mftscan
```

---

<a id="-volatility-3"></a>
## 🔄 Volatility 3

### Diferenças principais

| Funcionalidade | Vol2 | Vol3 |
|---------|------|------|
| Perfil | Manual (`--profile=`) | Identificação automática |
| Nomes dos plugins | `pslist` | `windows.pslist` |
| Desempenho | Mais lento | Mais rápido |
| Python | Python 2.7 | Python 3 |
| Saída | Apenas texto | JSON, CSV, etc. |

### Referência de plugins Vol3

```bash
# Processos
vol -f dump.raw windows.pslist
vol -f dump.raw windows.pstree
vol -f dump.raw windows.psscan
vol -f dump.raw windows.cmdline
vol -f dump.raw windows.dlllist
vol -f dump.raw windows.handles

# Rede
vol -f dump.raw windows.netscan
vol -f dump.raw windows.netstat

# Registo
vol -f dump.raw windows.registry.hivelist
vol -f dump.raw windows.registry.printkey
vol -f dump.raw windows.registry.userassist

# Malware
vol -f dump.raw windows.malfind
vol -f dump.raw windows.driverscan

# Credenciais
vol -f dump.raw windows.hashdump
vol -f dump.raw windows.lsadump
vol -f dump.raw windows.cachedump

# Ficheiros
vol -f dump.raw windows.filescan
vol -f dump.raw windows.dumpfiles
```

### Plugins Linux (Vol3)

```bash
vol -f dump.lime linux.pslist
vol -f dump.lime linux.bash
vol -f dump.lime linux.check_syscall
vol -f dump.lime linux.lsmod
vol -f dump.lime linux.lsof
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos mais utilizados

| Finalidade | Comando Vol2 | Comando Vol3 |
|---------|--------------|--------------|
| Identificar a imagem | `imageinfo` | `windows.info` |
| Listar processos | `pslist` | `windows.pslist` |
| Árvore de processos | `pstree` | `windows.pstree` |
| Processos ocultos | `psscan` | `windows.psscan` |
| Rede | `netscan` | `windows.netscan` |
| DLLs | `dlllist` | `windows.dlllist` |
| Extrair o processo | `procdump` | `windows.pslist --dump` |
| Procurar malware | `malfind` | `windows.malfind` |
| Registo | `hivelist` | `windows.registry.hivelist` |
| Hashes | `hashdump` | `windows.hashdump` |
| Ficheiros | `filescan` | `windows.filescan` |
| Cadeias de caracteres | `strings` | - |

### Fluxo de trabalho de investigação

```
1. imageinfo/windows.info    → Identificar o sistema operativo
2. pslist/pstree/psscan      → Listar processos
3. cmdline                    → Verificar as linhas de comandos
4. netscan                    → Ligações de rede
5. malfind                    → Procurar código injetado
6. dlllist                    → Verificar as DLL carregadas
7. filescan                   → Procurar ficheiros relevantes
8. hashdump                   → Extrair credenciais
9. timeliner                  → Criar uma cronologia
```

---

<a id="-resources"></a>
## 📚 Recursos

- [Volatility Foundation](https://www.volatilityfoundation.org/)
- [Volatility3 GitHub](https://github.com/volatilityfoundation/volatility3)
- [Wiki do Volatility](https://github.com/volatilityfoundation/volatility/wiki)
- [Guia de referência de análise forense de memória](https://digital-forensics.sans.org/media/memory-forensics-cheat-sheet.pdf)

### Guias de referência relacionados
- [Mimikatz](../../Mimikatz/README.md)
- [Comandos Linux](../../Linux-Commands/README.md)
- [Windows PrivEsc](../../Windows-PrivEsc/README.md)

---

<p align="center">
  <b>🧠 Domine a análise forense de memória!</b><br>
  <i>A memória revela pistas: extraia informação da RAM</i>
</p>
