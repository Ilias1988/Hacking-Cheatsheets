# 🔧 Binwalk — Guia de referência de análise de firmware

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗███╗   ██╗██╗    ██╗ █████╗ ██╗     ██╗  ██╗
  ██╔══██╗██║████╗  ██║██║    ██║██╔══██╗██║     ██║ ██╔╝
  ██████╔╝██║██╔██╗ ██║██║ █╗ ██║███████║██║     █████╔╝ 
  ██╔══██╗██║██║╚██╗██║██║███╗██║██╔══██║██║     ██╔═██╗ 
  ██████╔╝██║██║ ╚████║╚███╔███╔╝██║  ██║███████╗██║  ██╗
  ╚═════╝ ╚═╝╚═╝  ╚═══╝ ╚══╝╚══╝ ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝
           Ferramenta de análise e extração de firmware
```

<p align="center">
  <img src="https://img.shields.io/badge/Binwalk-Firmware_Analysis-blue?style=for-the-badge" alt="Binwalk">
  <img src="https://img.shields.io/badge/IoT-Hacking-green?style=for-the-badge" alt="IoT">
  <img src="https://img.shields.io/badge/CTF-red?style=for-the-badge" alt="CTF">
</p>

> **Notas de versão e precisão:** Firmware é o software incorporado num dispositivo; carving extrai dados a partir de assinaturas/deslocamentos. O guia utiliza sobretudo opções e instalação de Binwalk 2. O [Binwalk 3](https://github.com/ReFirmLabs/binwalk) foi reescrito em Rust: `setup.py` e várias opções antigas não são instruções universais para a versão atual. A linha `binwalk -R firmware.bin` não especifica de forma inequívoca o padrão e ficheiro como nos exemplos seguintes; os comandos foram mantidos para correspondência com o original. Opções como hexdump/diff, formatos de saída e plugins devem ser confirmadas para a versão em uso. Uma assinatura pode ser um falso positivo, e entropia elevada também pode resultar de compressão ou outros dados: os intervalos e curvas são heurísticas, não prova de cifragem. A extração exige suporte para o formato e ferramentas compatíveis; os offsets e tamanhos são exemplos.

---

<a id="-table-of-contents"></a>
## 📋 Índice

- [O que é o Binwalk](#-what-is-binwalk)
- [Instalação](#-installation)
- [Utilização básica](#-basic-usage)
- [Pesquisa de assinaturas](#-signature-scanning)
- [Extração](#-extraction)
- [Análise de entropia](#-entropy-analysis)
- [Análise de sistemas de ficheiros](#-file-system-analysis)
- [Desafios de CTF](#-ctf-challenges)
- [Opções avançadas](#-advanced-options)
- [Referência rápida](#-quick-reference)

---

<a id="-what-is-binwalk"></a>
## 🎯 O que é o Binwalk

**Binwalk** é uma ferramenta rápida e acessível para analisar imagens de firmware, estudar a sua estrutura e extrair dados. É útil para:

- 🔧 **Análise de firmware** — Identificar ficheiros/código incorporados
- 🔓 **Testes de IoT** — Extrair firmware de routers/câmaras
- 🎯 **Desafios de CTF** — Encontrar dados ocultos
- 🔬 **Análise forense** — Analisar ficheiros binários
- 🔍 **Engenharia inversa** — Compreender estruturas de ficheiros

### O que o Binwalk pode detetar

| Categoria | Exemplos |
|----------|----------|
| **Dados comprimidos** | gzip, bzip2, lzma, xz, zlib |
| **Arquivos** | tar, zip, rar, 7z, cpio |
| **Sistemas de ficheiros** | squashfs, cramfs, jffs2, ext, ubifs |
| **Executáveis** | ELF, PE, ARM, MIPS |
| **Imagens** | JPEG, PNG, GIF, BMP |
| **Criptografia** | OpenSSL e certificados |
| **Gestores de arranque** | U-Boot e cabeçalhos ARM |

---

<a id="-installation"></a>
## 🚀 Instalação

### Linux (Kali/Ubuntu)

```bash
# Instalar o binwalk
sudo apt update
sudo apt install binwalk

# Instalar todos os extratores (recomendado no original)
sudo apt install mtd-utils gzip bzip2 tar arj lhasa p7zip p7zip-full cabextract \
  cramfsswap squashfs-tools sleuthkit default-jdk lzop srecord

# Ou efetuar uma instalação abrangente
cd /opt
git clone https://github.com/ReFirmLabs/binwalk.git
cd binwalk
sudo python3 setup.py install

# Instalar sasquatch para squashfs
sudo apt install zlib1g-dev liblzma-dev liblzo2-dev
git clone https://github.com/devttys0/sasquatch
cd sasquatch && ./build.sh
```

### macOS

```bash
brew install binwalk
```

### Verificar a instalação

```bash
binwalk --help
binwalk -h
```

---

<a id="-basic-usage"></a>
## 💻 Utilização básica

### Sintaxe básica

```bash
# Analisar o ficheiro (mostrar assinaturas)
binwalk firmware.bin

# Extrair ficheiros
binwalk -e firmware.bin

# Extração recursiva
binwalk -eM firmware.bin
```

### Opções comuns

| Opção | Descrição |
|--------|-------------|
| `-e, --extract` | Extrair tipos de ficheiros conhecidos |
| `-M, --matryoshka` | Analisar recursivamente os ficheiros extraídos |
| `-r, --rm` | Eliminar os ficheiros recuperados por assinaturas após a extração |
| `-q, --quiet` | Modo silencioso |
| `-v, --verbose` | Saída detalhada |
| `-d, --depth` | Profundidade de recursão (predefinido: 8) |
| `-C, --directory` | Diretório de saída |
| `-f, --log` | Guardar o registo num ficheiro |

---

<a id="-signature-scanning"></a>
## 🔍 Pesquisa de assinaturas

### Análise básica

```bash
# Pesquisar assinaturas
binwalk firmware.bin

# Exemplo de saída:
# DECIMAL       HEXADECIMAL     DESCRIPTION
# --------------------------------------------------------------------------------
# 0             0x0             TRX firmware header, little endian
# 28            0x1C            gzip compressed data
# 1572892       0x18001C        Squashfs filesystem
```

### Tipos de assinaturas específicos

```bash
# Mostrar apenas determinadas assinaturas
binwalk -A firmware.bin          # Opcodes/código executável
binwalk -R firmware.bin          # Bytes em bruto (ficheiro de assinaturas, segundo o original; ver nota)
binwalk -B firmware.bin          # Assinaturas padrão (predefinido)

# Mostrar todos os opcodes
binwalk -A firmware.bin

# Pesquisar uma cadeia em bruto/hexadecimal
binwalk -R "\x89PNG" firmware.bin
binwalk -R "password" firmware.bin
```

### Filtros de inclusão/exclusão

```bash
# Incluir apenas tipos específicos
binwalk --include="filesystem" firmware.bin
binwalk --include="compressed" firmware.bin

# Excluir tipos
binwalk --exclude="jpeg" firmware.bin

# Mostrar apenas assinaturas específicas
binwalk -y "squashfs" firmware.bin
binwalk -y "gzip" firmware.bin
```

### Assinaturas personalizadas

```bash
# Utilizar um ficheiro de assinaturas personalizado
binwalk -m custom_signatures.txt firmware.bin

# Formato do ficheiro de assinaturas:
# 0   string  MZ  Microsoft executable
# 0   belong  0x89504E47  PNG image
```

---

<a id="-extraction"></a>
## 📦 Extração

### Extração básica

```bash
# Extrair todos os ficheiros reconhecidos
binwalk -e firmware.bin
# Cria: _firmware.bin.extracted/

# Extrair para um diretório específico
binwalk -e -C /output/dir firmware.bin

# Extrair recursivamente (matryoshka)
binwalk -eM firmware.bin
binwalk --extract --matryoshka firmware.bin
```

### Opções de extração

```bash
# Eliminar após a extração (limpeza)
binwalk -er firmware.bin

# Limitar a profundidade de recursão
binwalk -eM --depth=3 firmware.bin

# Extração silenciosa
binwalk -eq firmware.bin

# Extração com saída detalhada
binwalk -ev firmware.bin
```

### Extração manual (dd)

```bash
# Quando o binwalk não consegue extrair automaticamente
# Obter o deslocamento a partir da análise do binwalk
binwalk firmware.bin
# Exemplo: Squashfs no deslocamento 1572892

# Extrair através de dd
dd if=firmware.bin of=squashfs.bin bs=1 skip=1572892

# Ou com count (se o tamanho for conhecido)
dd if=firmware.bin of=squashfs.bin bs=1 skip=1572892 count=5000000
```

### Recuperar tipos específicos por assinaturas

```bash
# Extrair apenas tipos de ficheiros específicos
binwalk -D "jpeg:jpg" firmware.bin
binwalk -D "png:png" firmware.bin
binwalk -D "elf:elf" firmware.bin

# Vários tipos
binwalk -D "jpeg:jpg" -D "png:png" firmware.bin

# Formato: tipo:extensão:comando
binwalk -D "gzip:gz:gunzip %e" firmware.bin
```

---

<a id="-entropy-analysis"></a>
## 📊 Análise de entropia

### O que é a entropia?

A entropia mede a variabilidade dos dados:
- **Entropia elevada (0.9-1.0)** = Dados cifrados/comprimidos
- **Entropia média (0.5-0.8)** = Código executável
- **Entropia baixa (0.0-0.4)** = Texto simples e dados repetidos

### Gerar um gráfico de entropia

```bash
# Calcular a entropia
binwalk -E firmware.bin
binwalk --entropy firmware.bin

# Guardar o gráfico de entropia
binwalk -E --save firmware.bin
# Cria: firmware.bin.png

# Com legenda
binwalk -EJ firmware.bin
```

### Opções de entropia

```bash
# Definir o tamanho do bloco
binwalk -E --block=1024 firmware.bin

# Mostrar valores de entropia (sem gráfico)
binwalk -E --nplot firmware.bin

# Detetar transições ascendentes/descendentes
binwalk -E --edge firmware.bin
```

### Interpretar a entropia

```
Linha alta e plana (~1.0)  → Dados cifrados
Padrão com picos (~0.7)   → Dados comprimidos  
Linha ondulada (~0.5)       → Código/dados
Linha baixa e plana (~0.2)   → Texto simples
Queda súbita para 0       → Preenchimento com bytes nulos
```

---

<a id="-file-system-analysis"></a>
## 💾 Análise de sistemas de ficheiros

### Sistemas de ficheiros comuns em firmware

| Sistema de ficheiros | Descrição |
|-------------|-------------|
| **SquashFS** | Comprimido, apenas de leitura (o mais comum) |
| **JFFS2** | Sistema de ficheiros flash com journaling |
| **UBIFS** | Alternativa a JFFS2 para flash de grande capacidade |
| **CramFS** | Sistema de ficheiros ROM comprimido |
| **YAFFS** | Yet Another Flash File System |
| **ext2/3/4** | Sistema de ficheiros Linux habitual |

### Extrair SquashFS

```bash
# Extrair com Binwalk
binwalk -e firmware.bin

# Extração manual com unsquashfs
unsquashfs squashfs-root.bin
ls squashfs-root/

# Montar (se suportado)
sudo mount -t squashfs squashfs.bin /mnt
```

### Extrair JFFS2

```bash
# Utilizar jefferson
pip3 install jefferson
jefferson firmware.jffs2 -d output/

# Ou utilizar binwalk
binwalk -e firmware.bin
```

### Extrair UBIFS

```bash
# Utilizar ubireader
pip3 install ubi_reader
ubireader_extract_images firmware.ubi -o output/
ubireader_extract_files firmware.ubi -o output/
```

### Analisar os ficheiros extraídos

```bash
# Após a extração, explorar
cd _firmware.bin.extracted/

# Procurar ficheiros relevantes
find . -name "*.conf"
find . -name "passwd"
find . -name "shadow"
find . -name "*.key"
find . -name "*.pem"

# Procurar palavras-passe/segredos
grep -r "password" .
grep -r "admin" .
grep -r "root:" .

# Procurar executáveis
find . -type f -executable
file */bin/*
```

---

<a id="-ctf-challenges"></a>
## 🎯 Desafios de CTF

### Cenários comuns de CTF

#### Ficheiro oculto numa imagem

```bash
# Analisar a imagem à procura de dados ocultos
binwalk image.png
binwalk image.jpg

# Extrair ficheiros ocultos
binwalk -e image.png

# Verificar também as cadeias de caracteres
strings image.png | head -50
```

#### Arquivos sobrepostos

```bash
# Extração recursiva (matryoshka)
binwalk -eM challenge.bin

# Várias camadas de compressão
binwalk -eM --depth=10 nested.bin
```

#### Procurar texto oculto

```bash
# Combinar com strings
binwalk challenge.bin
strings challenge.bin | grep -i "flag"
strings challenge.bin | grep "CTF{"
```

#### Secções cifradas

```bash
# A análise de entropia pode revelar indícios de cifragem
binwalk -E challenge.bin

# Entropia elevada pode indicar cifragem (ver nota sobre outras causas)
# Experimentar palavras-passe comuns ou procurar a chave noutro local
```

### Fluxo de trabalho de CTF

```bash
# 1. Análise básica
binwalk challenge.bin

# 2. Verificar a entropia
binwalk -E challenge.bin

# 3. Extrair tudo
binwalk -eM challenge.bin

# 4. Pesquisar nos ficheiros extraídos
cd _challenge.bin.extracted/
find . -type f -exec file {} \;
grep -r "flag" .
strings * | grep -i flag

# 5. Procurar dados ocultos
binwalk -A challenge.bin    # Opcodes
xxd challenge.bin | head    # Representação hexadecimal (hex dump)
```

### Deteção de esteganografia

```bash
# Analisar imagens à procura de dados ocultos
binwalk -B suspicious.jpg

# Procurar dados acrescentados ao ficheiro
binwalk suspicious.png

# Extrair se forem encontrados
binwalk -e suspicious.jpg

# Comparar o tamanho do ficheiro com o esperado
ls -la suspicious.jpg
```

---

<a id="-advanced-options"></a>
## ⚙️ Opções avançadas

### Saída hexadecimal

```bash
# Mostrar uma representação hexadecimal nos deslocamentos
binwalk -W firmware.bin
binwalk --hexdump firmware.bin

# Mostrar uma representação hexadecimal num deslocamento específico
binwalk -o 0x1000 -l 512 -W firmware.bin
```

### Opções de deslocamento

```bash
# Começar no deslocamento
binwalk -o 1024 firmware.bin
binwalk --offset=1024 firmware.bin

# Analisar apenas o comprimento indicado
binwalk -l 10000 firmware.bin
binwalk --length=10000 firmware.bin
```

### Opções de desempenho

```bash
# Utilizar vários processos
binwalk --threads=4 firmware.bin

# Desativar determinados plugins
binwalk --disable-plugin=lzma firmware.bin
```

### Formatos de saída

```bash
# Saída em CSV
binwalk --csv firmware.bin > results.csv

# Saída em JSON 
binwalk --json firmware.bin > results.json

# Guardar o registo num ficheiro
binwalk -f scan.log firmware.bin
```

### Comparar dois ficheiros

```bash
# Comparar duas versões de firmware
binwalk -W --diff firmware_v1.bin firmware_v2.bin

# Mostra as diferenças em hexadecimal
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos mais utilizados

| Finalidade | Comando |
|---------|---------|
| Analisar o ficheiro | `binwalk firmware.bin` |
| Extrair tudo | `binwalk -e firmware.bin` |
| Extrair recursivamente | `binwalk -eM firmware.bin` |
| Análise de entropia | `binwalk -E firmware.bin` |
| Pesquisa de opcodes | `binwalk -A firmware.bin` |
| Pesquisar uma cadeia de caracteres | `binwalk -R "pattern" file` |
| Extrair para um diretório | `binwalk -e -C outdir/ file` |
| Extrair e eliminar | `binwalk -er firmware.bin` |

### Assinaturas de ficheiros comuns

| Assinatura | Descrição |
|-----------|-------------|
| `\x1f\x8b\x08` | Comprimido com gzip |
| `BZh` | Comprimido com bzip2 |
| `\xfd7zXZ` | Comprimido com xz |
| `hsqs` | SquashFS (little-endian) |
| `sqsh` | SquashFS (big-endian) |
| `\x85\x19` | JFFS2 |
| `UBI#` | UBIFS |
| `\x89PNG` | Imagem PNG |
| `\xff\xd8\xff` | Imagem JPEG |
| `PK\x03\x04` | Arquivo ZIP |

### Fluxo de trabalho de extração

```
1. binwalk firmware.bin       → Identificar o conteúdo
2. binwalk -E firmware.bin    → Procurar indícios de cifragem
3. binwalk -e firmware.bin    → Extrair
4. cd _firmware.bin.extracted → Explorar
5. find . -name "*" -type f   → Listar todos os ficheiros
6. grep -r "password" .       → Procurar segredos
```

### Extração manual

```bash
# Obter o deslocamento a partir do binwalk
binwalk firmware.bin
# Squashfs em 0x180000 (1572864 em decimal)

# Extrair com dd
dd if=firmware.bin of=fs.squashfs bs=1 skip=1572864

# Extrair squashfs
unsquashfs fs.squashfs
```

### Localizações úteis de ficheiros (firmware)

| Path | Conteúdo |
|------|----------|
| `/etc/passwd` | Contas de utilizador |
| `/etc/shadow` | Hashes de palavras-passe |
| `/etc/config/` | Configuração do dispositivo |
| `/etc/init.d/` | Scripts de arranque |
| `/usr/bin/` | Binários dos utilizadores |
| `/www/` | Interface web |
| `/etc/ssl/` | Certificados SSL |

---

<a id="-resources"></a>
## 📚 Recursos

- [Binwalk GitHub](https://github.com/ReFirmLabs/binwalk)
- [Wiki do Binwalk](https://github.com/ReFirmLabs/binwalk/wiki)
- [Firmware Analysis 101](https://www.youtube.com/results?search_query=firmware+analysis+binwalk)
- [Análise de firmware IoT na OWASP](https://owasp.org/www-project-iot-security/)

### Guias de referência relacionados
- [ExifTool](../../ExifTool/translations/README.pt.md)
- [Volatility](../../Volatility/translations/README.pt.md)
- [Autopsy](../../Autopsy/translations/README.pt.md)
- [Comandos Linux](../../Linux-Commands/README.md)

---

<p align="center">
  <b>🔧 Domine a análise de firmware!</b><br>
  <i>Binwalk — Explore o conteúdo do firmware</i>
</p>
