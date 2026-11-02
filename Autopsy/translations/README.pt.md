# 🔍 Autopsy — Guia de referência da plataforma de análise forense digital

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   █████╗ ██╗   ██╗████████╗ ██████╗ ██████╗ ███████╗██╗   ██╗
  ██╔══██╗██║   ██║╚══██╔══╝██╔═══██╗██╔══██╗██╔════╝╚██╗ ██╔╝
  ███████║██║   ██║   ██║   ██║   ██║██████╔╝███████╗ ╚████╔╝ 
  ██╔══██║██║   ██║   ██║   ██║   ██║██╔═══╝ ╚════██║  ╚██╔╝  
  ██║  ██║╚██████╔╝   ██║   ╚██████╔╝██║     ███████║   ██║   
  ╚═╝  ╚═╝ ╚═════╝    ╚═╝    ╚═════╝ ╚═╝     ╚══════╝   ╚═╝   
              Plataforma de análise forense digital
```

<p align="center">
  <img src="https://img.shields.io/badge/Autopsy-Digital_Forensics-blue?style=for-the-badge" alt="Autopsy">
  <img src="https://img.shields.io/badge/Sleuth_Kit-green?style=for-the-badge" alt="TSK">
  <img src="https://img.shields.io/badge/DFIR-red?style=for-the-badge" alt="DFIR">
</p>

> **Notas de versão e precisão:** O guia mistura a plataforma gráfica Autopsy 4 com a instalação do pacote [Autopsy 2 do Kali](https://www.kali.org/tools/autopsy/); essa instalação não garante a mesma interface. Consultar a [documentação da versão gráfica](https://sleuthkit.org/autopsy/docs/user-docs/latest/) para módulos, formatos e opções disponíveis. Os nomes reais dos controlos, menus, tipos de resultado e termos de pesquisa permanecem em inglês, com explicações em português. Os caminhos de exemplo e a estrutura do caso variam com a versão e o modo de utilização. Nos comandos TSK, `-o 2048` representa um deslocamento em setores, não um valor universal em bytes ([fls](https://www.sleuthkit.org/sleuthkit/man/fls.html)). As expressões regulares são exemplos heurísticos: o padrão IPv4 aceita octetos inválidos e o de cartão não faz validação completa. Um resultado ou uma data deve ser interpretado com o contexto e o fuso horário da prova.

---

<a id="-table-of-contents"></a>
## 📋 Índice

- [O que é o Autopsy](#-what-is-autopsy)
- [Instalação](#-installation)
- [Criar um caso](#-creating-a-case)
- [Adicionar fontes de dados](#-adding-data-sources)
- [Ingest Modules (módulos de processamento)](#-ingest-modules)
- [Funcionalidades de análise](#-analysis-features)
- [Análise de ficheiros](#-file-analysis)
- [Análise cronológica](#-timeline-analysis)
- [Keyword Search (pesquisa de palavras-chave)](#-keyword-search)
- [Elaboração de relatórios](#-reporting)
- [Ferramentas de linha de comandos (Sleuth Kit)](#-cli-tools-sleuth-kit)

---

<a id="-what-is-autopsy"></a>
## 🎯 O que é o Autopsy

**Autopsy** é uma plataforma gratuita de análise forense digital, de código aberto, baseada no The Sleuth Kit (TSK). Disponibiliza:

- 🖥️ **Interface gráfica** — Análise forense acessível
- 💾 **Análise de discos** — Examinar discos rígidos, dispositivos USB e imagens
- 📁 **Recuperação de ficheiros** — Recuperar ficheiros eliminados
- 📊 **Análise cronológica** — Reconstruir a atividade do utilizador
- 🔍 **Pesquisa de palavras-chave** — Pesquisar em todos os dados
- 📧 **Análise de email** — Interpretar bases de dados de email
- 🌐 **Artefactos web** — Histórico e marcadores do navegador
- 📱 **Análise forense móvel** — Análise de Android/iOS

### Tipos de prova suportados

| Tipo | Formatos |
|------|---------|
| **Disk Images (imagens de disco)** | E01, AFF, Raw (dd), VHD, VMDK |
| **Local Disk** | Unidades físicas/lógicas |
| **VM Files (ficheiros de máquinas virtuais)** | VMDK, VHD, VHDX |
| **Logical Files** | Pastas e ficheiros |
| **Unallocated Space** | Dados recuperados por assinaturas (carving) |

---

<a id="-installation"></a>
## 🚀 Instalação

### Windows

```bash
# Descarregar a partir do site oficial
https://www.autopsy.com/download/

# Executar o instalador
autopsy-x.x.x-64bit.msi

# Localização predefinida
C:\Program Files\Autopsy-x.x.x\
```

### Linux (Kali)

```bash
# Instalar através de apt
sudo apt update
sudo apt install autopsy

# Executar
autopsy
```

### Requisitos

| Componente | Mínimo | Recomendado |
|-----------|---------|-------------|
| **RAM** | 8 GB | 16+ GB |
| **CPU** | Vários núcleos | 4 ou mais núcleos |
| **Armazenamento** | SSD | NVMe SSD |
| **Sistema operativo** | Windows 10/11, Linux | Windows 10/11 |

---

<a id="-creating-a-case"></a>
## 📁 Criar um caso

### Assistente de novo caso

1. **File → New Case**
2. Introduzir **Case Name** (por exemplo, "Investigation_2024")
3. Introduzir **Base Directory** (onde são guardados os ficheiros do caso)
4. Selecionar **Case Type**:
   - Single-user (um utilizador, local)
   - Multi-user (vários utilizadores, colaborativo)
5. Introduzir **Case Number** (opcional)
6. Introduzir as informações de **Examiner**
7. Clicar em **Finish**

### Estrutura do caso

```
CaseName/
├── autopsy.db           # Base de dados do caso (SQLite)
├── Reports/             # Relatórios gerados
├── Export/              # Ficheiros exportados
├── ModuleOutput/        # Resultados dos módulos
└── [HostName]/          # Dados por host
    ├── [DataSource]/    # Ficheiros de prova
    └── Reports/
```

### Propriedades do caso

| Campo | Descrição |
|-------|-------------|
| Case Name | Identificador da investigação |
| Case Number | Número de referência |
| Examiner | Nome do analista |
| Base Directory | Localização de armazenamento do caso |
| Case Type | Um/vários utilizadores |

---

<a id="-adding-data-sources"></a>
## 📀 Adicionar fontes de dados

### Tipos de fontes de dados

| Tipo | Descrição |
|------|-------------|
| **Disk Image or VM File** | E01, Raw, VMDK, VHD |
| **Local Disk** | Análise de unidade física |
| **Logical Files** | Importação de pastas/ficheiros |
| **Unallocated Space** | Imagem em formato bruto do espaço livre |

### Adicionar uma imagem de disco

1. **Add Data Source** (barra de ferramentas)
2. Selecionar **Disk Image or VM File**
3. Selecionar o ficheiro de imagem
4. Selecionar **Time Zone**
5. Configurar **Ingest Modules** (secção seguinte)
6. Clicar em **Finish**

### Adicionar um disco local

```
⚠️ AVISO: utilizar um bloqueador de escrita ou o modo apenas de leitura!
```

1. **Add Data Source**
2. Selecionar **Local Disk**
3. Escolher o disco na lista
4. Configurar os módulos de processamento
5. **Finish**

### Formatos de imagem suportados

| Formato | Extensão | Descrição |
|--------|-----------|-------------|
| Raw | .raw, .dd, .img | Cópia bit a bit |
| EnCase | .E01, .Ex01 | Formato Expert Witness |
| AFF | .aff | Advanced Forensic Format (formato forense avançado) |
| VMDK | .vmdk | Disco VMware |
| VHD/VHDX | .vhd, .vhdx | Disco Hyper-V |

---

<a id="-ingest-modules"></a>
## ⚙️ Ingest Modules (módulos de processamento)

### O que são os Ingest Modules?

Módulos de análise automatizada que processam as fontes de dados para extrair artefactos.

### Módulos principais

| Módulo | Descrição |
|--------|-------------|
| **Recent Activity** | Histórico do navegador, transferências e cookies |
| **Hash Lookup** | Comparação de MD5/SHA1 com bases de dados de referência |
| **File Type Identification** | Identificar tipos de ficheiros pela assinatura |
| **Keyword Search (pesquisa de palavras-chave)** | Indexar e pesquisar conteúdo |
| **Email Parser** | Interpretar PST, MBOX e EML |
| **Extension Mismatch** | Detetar ficheiros com nomes alterados |
| **Embedded File Extractor** | Extrair de arquivos/documentos |
| **EXIF Parser** | Extrair metadados de imagens |
| **Encryption Detection** | Procurar ficheiros cifrados |
| **Interesting Files** | Procurar ficheiros relevantes |
| **PhotoRec Carver** | Recuperação de ficheiros por assinaturas (carving) |
| **Virtual Machine Extractor** | Extrair ficheiros de máquinas virtuais |

### Bases de dados de hashes

```
# Adicionar bases de dados de hashes de referência
Tools → Options → Hash Database

# Tipos:
- Known (NSRL) — Ignorar ficheiros conhecidos como legítimos
- Known Bad — Alertar para hashes de malware
```

### NSRL (National Software Reference Library)

```bash
# Descarregar o NSRL
https://www.nist.gov/itl/ssd/software-quality-group/national-software-reference-library-nsrl

# Importar no Autopsy
Tools → Options → Hash Database → Import Database
```

### Configuração dos módulos

```
# Ativar/desativar módulos por caso
Clicar com o botão direito na fonte de dados → Run Ingest Modules

# Configurar as definições dos módulos
Clicar no ícone de engrenagem junto do nome do módulo
```

---

<a id="-analysis-features"></a>
## 🔬 Funcionalidades de análise

### Vista em árvore (painel esquerdo)

```
Data Sources
├── [Nome da imagem]
│   ├── Volume 1
│   │   ├── [Pastas]
│   │   ├── $OrphanFiles
│   │   └── $Unalloc
│   └── Volume 2
│
Views
├── File Types
│   ├── By Extension
│   └── By MIME Type
├── Deleted Files
├── File Size
└── ...

Results
├── Extracted Content
│   ├── Web History
│   ├── Web Downloads
│   ├── Web Cookies
│   ├── Recent Documents
│   └── ...
├── Keyword Hits
├── HashSet Hits
├── E-mail Messages
├── Interesting Items
├── Accounts
└── ...

Tags
├── Follow Up
├── Notable Item
└── [Etiquetas personalizadas]
```

### Áreas de análise rápida

| Categoria | O que procurar |
|----------|-----------------|
| **Deleted Files** | Ficheiros recuperados e eliminações efetuadas pelo utilizador |
| **Web History** | Sites visitados e pesquisas |
| **Downloads** | Ficheiros descarregados |
| **Recent Documents** | Ficheiros abertos recentemente |
| **USB Devices** | Dispositivos ligados |
| **Email** | Comunicações |
| **Imagens** | Fotografias e capturas de ecrã |
| **Arquivos** | Conteúdo de ZIP e RAR |

---

<a id="-file-analysis"></a>
## 📄 Análise de ficheiros

### Visualizar ficheiros

```
# Content Viewer (painel inferior)
- Hex        : vista hexadecimal em bruto
- Text       : conteúdo textual
- Application: apresentação renderizada
- Strings    : cadeias de caracteres extraídas
- Metadata   : metadados do ficheiro
- Results    : resultados da análise
- Indexed Text: texto pesquisável
```

### Propriedades dos ficheiros

| Propriedade | Descrição |
|----------|-------------|
| Name | Nome do ficheiro |
| Location | Caminho completo |
| Size | Tamanho do ficheiro |
| Created | Data/hora de criação |
| Modified | Última modificação |
| Accessed | Último acesso |
| MD5/SHA1/SHA256 | Valores de hash |
| Known | Estado no NSRL |
| MIME Type | Tipo detetado |

### Extrair ficheiros

```
# Extrair um ficheiro
Right-click → Extract File(s)

# Extrair vários ficheiros
Selecionar ficheiros → botão direito → Extract

# Extrair para uma localização específica
Actions → Extract to...
```

### Recuperação de ficheiros eliminados

```
Views → Deleted Files

# Mostra:
- Ficheiros em $Recycle.Bin
- Ficheiros marcados como eliminados na MFT
- Ficheiros recuperados por assinaturas (PhotoRec)
```

---

<a id="-timeline-analysis"></a>
## 📊 Análise cronológica

### Vista cronológica

```
# Abrir a Timeline
Tools → Timeline

# Ou
Results → Timeline
```

### Funcionalidades da cronologia

| Funcionalidade | Descrição |
|---------|-------------|
| **Counts View** | Frequência dos eventos ao longo do tempo |
| **Details View** | Eventos individuais |
| **List View** | Lista tabular de eventos |
| **Filters** | Filtrar por tipo, data, etc. |

### Tipos de eventos

| Tipo | Cor | Descrição |
|------|-------|-------------|
| File Modified | Azul | Conteúdo do ficheiro alterado |
| File Accessed | Verde | Ficheiro aberto |
| File Created | Amarelo | Ficheiro criado |
| File Changed | Vermelho | Metadados alterados |
| Web Activity | Roxo | Eventos do navegador |

### Filtros da cronologia

```
# Filtrar por intervalo de datas
Start Date: 2024-01-01
End Date: 2024-12-31

# Filtrar por tipo
☑ File System
☑ Web Activity
☐ Registry (if parsed)

# Filtrar por fonte de dados
☑ Disk Image 1
☐ Disk Image 2
```

### Exportar a cronologia

```
# Exportar a cronologia
File → Export → Timeline (CSV/TSV)

# Utilizar com ferramentas externas
- Excel
- plaso/log2timeline
- timesketch
```

---

<a id="-keyword-search"></a>
## 🔍 Keyword Search (pesquisa de palavras-chave)

### Criar listas de palavras-chave

```
# Tools → Options → Keyword Search

# Adicionar uma lista
1. Clicar em "New List"
2. Atribuir um nome à lista
3. Adicionar palavras-chave:
   - Literal (correspondência exata)
   - Regex (padrão de expressão regular)
   - Substring (parte de uma cadeia)
```

### Palavras-chave de pesquisa comuns

```
# Informação financeira
credit card
bank account
swift
iban
bitcoin
wallet

# Credenciais
password
login
username
secret
key

# Informação sensível
confidential
secret
classified
internal only

# Rede
ip address: \b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b
email: [a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}
```

### Exemplos de expressões regulares

| Finalidade | Regex |
|---------|-------|
| Email | `[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}` |
| Endereço IP | `\b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b` |
| Telefone (EUA) | `\b\d{3}[-.]?\d{3}[-.]?\d{4}\b` |
| Cartão de crédito | `\b\d{4}[- ]?\d{4}[- ]?\d{4}[- ]?\d{4}\b` |
| SSN | `\b\d{3}-\d{2}-\d{4}\b` |
| URL | `https?://[^\s]+` |

### Executar a pesquisa

```
# Pesquisa pontual
Keyword Search (canto superior direito) → introduzir o termo → Search

# Utilizar listas de palavras-chave
Clicar com o botão direito na fonte de dados → Run Ingest Modules → Keyword Search
```

---

<a id="-reporting"></a>
## 📝 Elaboração de relatórios

### Generate Report (gerar relatório)

```
# Generate Report (gerar relatório)
Tools → Generate Report

# Ou
Clicar com o botão direito no caso → Generate Report
```

### Tipos de relatório

| Tipo | Descrição |
|------|-------------|
| **HTML Report** | Formato web, inclui imagens |
| **Excel Report** | Formato de folha de cálculo |
| **Text Report** | Texto simples |
| **TSK Body File** | Ficheiro body para cronologia |
| **Files - FS** | Relatório do sistema de ficheiros |
| **Portable Case (caso portátil)** | Subconjunto que pode ser partilhado |

### Opções do relatório HTML

```
# Selecionar os dados a incluir
☑ Tagged Results
☑ Interesting Items
☑ Hashset Hits
☑ Keyword Hits
☑ Recent Activity (Web)
☑ Accounts
☑ File Types

# Opções
☑ Include thumbnails
☑ Include metadata
```

### Portable Case (caso portátil)

```
# Criar um caso portátil para partilha
Generate Report → Portable Case

# Inclui:
- Provas selecionadas
- Resultados da análise
- Pode ser aberto no Autopsy
```

---

<a id="-cli-tools-sleuth-kit"></a>
## 💻 Ferramentas de linha de comandos (Sleuth Kit)

### Comandos do Sleuth Kit

O Autopsy utiliza o The Sleuth Kit (TSK) como backend. Estão disponíveis os seguintes comandos de linha de comandos:

### Informações da imagem

```bash
# Obter informações da imagem
img_stat image.E01
img_stat image.dd

# Obter informações do sistema de ficheiros
fsstat -o 2048 image.dd
```

### Listagem de ficheiros

```bash
# Listar ficheiros
fls -r -o 2048 image.dd

# Listar ficheiros eliminados
fls -r -d -o 2048 image.dd

# Opções:
# -r    Recursivo
# -d    Apenas ficheiros eliminados
# -o    Deslocamento (início da partição)
```

### Recuperação de ficheiros

```bash
# Obter o ficheiro pelo inode
icat -o 2048 image.dd 12345 > recovered_file

# Obter o conteúdo do ficheiro
icat -r -o 2048 image.dd 12345 > file.txt
```

### Cronologia

```bash
# Criar um ficheiro body
fls -r -m "/" -o 2048 image.dd > body.txt

# Criar uma cronologia
mactime -b body.txt -d > timeline.csv
```

### Cálculo de hashes

```bash
# Calcular MD5
md5sum image.dd

# Calcular SHA256
sha256sum image.dd

# Verificar a imagem
sha256sum -c image.dd.sha256
```

### Ferramentas TSK comuns

| Ferramenta | Finalidade |
|------|---------|
| `img_stat` | Informações da imagem |
| `mmls` | Tabela de partições |
| `fsstat` | Informações do sistema de ficheiros |
| `fls` | Listagem de ficheiros |
| `icat` | Extrair o ficheiro pelo inode |
| `istat` | Informações do inode |
| `blkls` | Extrair espaço não alocado |
| `srch_strings` | Procurar cadeias de caracteres |
| `tsk_recover` | Recuperar todos os ficheiros |
| `mactime` | Criar uma cronologia |

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Fluxo de trabalho de investigação

```
1. Create Case          → Novo caso com os respetivos dados
2. Add Data Source      → Imagem de disco ou unidade
3. Run Ingest Modules   → Análise automatizada
4. Review Results       → Verificar o conteúdo extraído
5. Keyword Search       → Pesquisar termos
6. Timeline Analysis    → Reconstruir eventos
7. Tag Evidence         → Assinalar itens importantes
8. Generate Report      → Criar o relatório final
```

### Atalhos de teclado

| Atalho | Ação |
|----------|--------|
| Ctrl+N | New Case |
| Ctrl+O | Open Case |
| Ctrl+F | Find (pesquisar na tabela) |
| Ctrl+G | Generate Report (gerar relatório) |
| F5 | Refresh (atualizar) |

### Localizações comuns a verificar

| Windows | Caminho |
|---------|------|
| Ficheiros recentes | `Users\*\AppData\Roaming\Microsoft\Windows\Recent` |
| Downloads | `Users\*\Downloads` |
| Histórico do navegador | `Users\*\AppData\Local\[Browser]\User Data` |
| Ficheiros temporários | `Users\*\AppData\Local\Temp` |
| Reciclagem | `$Recycle.Bin` |
| Prefetch | `Windows\Prefetch` |
| Registos de eventos | `Windows\System32\winevt\Logs` |

---

<a id="-resources"></a>
## 📚 Recursos

- [Site oficial do Autopsy](https://www.autopsy.com/)
- [Sleuth Kit](https://www.sleuthkit.org/)
- [Documentação do Autopsy](https://sleuthkit.org/autopsy/docs/user-docs/latest/)
- [DFIR Training](https://www.dfir.training/)

### Guias de referência relacionados
- [Volatility](../../Volatility/translations/README.pt.md)
- [Comandos Linux](../../Linux-Commands/README.md)
- [Wireshark](../../Wireshark/README.md)

---

<p align="center">
  <b>🔍 Domine a análise forense digital!</b><br>
  <i>Autopsy — Um aliado de quem investiga provas digitais</i>
</p>
