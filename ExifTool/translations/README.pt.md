# 📷 ExifTool — Guia de referência de extração de metadados

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███████╗██╗  ██╗██╗███████╗████████╗ ██████╗  ██████╗ ██╗     
  ██╔════╝╚██╗██╔╝██║██╔════╝╚══██╔══╝██╔═══██╗██╔═══██╗██║     
  █████╗   ╚███╔╝ ██║█████╗     ██║   ██║   ██║██║   ██║██║     
  ██╔══╝   ██╔██╗ ██║██╔══╝     ██║   ██║   ██║██║   ██║██║     
  ███████╗██╔╝ ██╗██║██║        ██║   ╚██████╔╝╚██████╔╝███████╗
  ╚══════╝╚═╝  ╚═╝╚═╝╚═╝        ╚═╝    ╚═════╝  ╚═════╝ ╚══════╝
                 Ferramenta de extração de metadados
```

<p align="center">
  <img src="https://img.shields.io/badge/ExifTool-Metadata-blue?style=for-the-badge" alt="ExifTool">
  <img src="https://img.shields.io/badge/OSINT-green?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Forensics-red?style=for-the-badge" alt="Forensics">
</p>

> **Notas de precisão:** Metadados são informações associadas ao conteúdo; podem ser alterados, removidos ou estar incorretos, pelo que não provam isoladamente autenticidade, autoria ou localização. O [ExifTool distingue leitura de escrita por formato](https://github.com/exiftool/exiftool): os formatos Office indicados não têm, em geral, o mesmo suporte de escrita dos JPEG, e `-all=` não é uma garantia universal de remoção. Em [PDF](https://raw.githubusercontent.com/exiftool/exiftool/master/html/TagNames/PDF.html), as alterações são incrementais e reversíveis; informação antiga pode continuar no ficheiro. [AllDates](https://raw.githubusercontent.com/exiftool/exiftool/master/html/TagNames/Shortcuts.html) é um atalho para tags específicas, não para todas as datas do ficheiro/sistema. A cópia de metadados e os exemplos de renomeação dependem das tags graváveis e da sintaxe da versão. As secções de escrita/remoção modificam ficheiros; o próprio guia inclui exemplos de preservação do original. Todos os valores de metadados e comandos foram mantidos.

---

<a id="-table-of-contents"></a>
## 📋 Índice

- [O que é o ExifTool](#-what-is-exiftool)
- [Instalação](#-installation)
- [Utilização básica](#-basic-usage)
- [Ler metadados](#-reading-metadata)
- [Escrever metadados](#-writing-metadata)
- [Remover metadados](#-removing-metadata)
- [Tipos de ficheiros](#-file-types)
- [OSINT e análise forense](#-osint--forensics)
- [Processamento em lote](#-batch-processing)
- [Referência rápida](#-quick-reference)

---

<a id="-what-is-exiftool"></a>
## 🎯 O que é o ExifTool

**ExifTool** é uma ferramenta de linha de comandos para ler, escrever e modificar metadados em ficheiros. É útil para:

- 🔍 **OSINT** — Extrair informações de localização e de dispositivos a partir de imagens
- 🔬 **Análise forense** — Analisar metadados de documentos
- 🔐 **Privacidade** — Remover metadados sensíveis
- 📸 **Fotografia** — Gerir metadados de fotografias
- 📄 **Documentos** — Analisar metadados de ficheiros PDF e Office

### Porque são importantes os metadados

| Caso de utilização | Exemplo |
|----------|---------|
| **Geolocalização** | Coordenadas GPS nas fotografias |
| **Informações do dispositivo** | Modelo da câmara e número de série |
| **Datas e horas** | Quando o ficheiro foi criado/modificado |
| **Autor** | Quem criou o documento |
| **Software** | Que aplicação criou o ficheiro |
| **Histórico de edição** | Datas/horas de modificação |

---

<a id="-installation"></a>
## 🚀 Instalação

### Linux (Kali/Ubuntu)

```bash
# Debian/Ubuntu/Kali
sudo apt install exiftool

# Ou
sudo apt install libimage-exiftool-perl
```

### macOS

```bash
# Homebrew
brew install exiftool
```

### Windows

```bash
# Descarregar a partir do site oficial
https://exiftool.org/

# Extrair exiftool(-k).exe
# Mudar o nome para exiftool.exe
# Adicionar a PATH ou utilizar diretamente
```

### Verificar a instalação

```bash
exiftool -ver
# Output: 12.xx
```

---

<a id="-basic-usage"></a>
## 💻 Utilização básica

### Sintaxe básica

```bash
# Ler metadados
exiftool [OPTIONS] FILE

# Escrever metadados
exiftool -TAG=VALUE FILE

# Vários ficheiros
exiftool *.jpg
exiftool -r directory/
```

### Opções comuns

| Opção | Descrição |
|--------|-------------|
| `-r` | Recursivo (processar subdiretórios) |
| `-v` | Saída detalhada |
| `-n` | Sem conversão de apresentação (valores em bruto) |
| `-s` | Nomes abreviados de tags |
| `-G` | Mostrar o nome do grupo de cada tag |
| `-j` | Saída em JSON |
| `-csv` | Saída em CSV |
| `-overwrite_original` | Não criar cópia de segurança |

---

<a id="-reading-metadata"></a>
## 📖 Ler metadados

### Ver todos os metadados

```bash
# Todos os metadados
exiftool image.jpg

# Saída detalhada
exiftool -v image.jpg

# Saída muito detalhada
exiftool -v3 image.jpg
```

### Tags específicas

```bash
# Uma tag
exiftool -GPSLatitude image.jpg

# Várias tags
exiftool -GPSLatitude -GPSLongitude -Make -Model image.jpg

# Excluir tags
exiftool --MakerNotes image.jpg
```

### Leitura por grupos

```bash
# Mostrar todas as tags EXIF
exiftool -EXIF:all image.jpg

# Mostrar todas as tags IPTC
exiftool -IPTC:all image.jpg

# Mostrar todas as tags XMP
exiftool -XMP:all image.jpg

# Mostrar todas as tags GPS
exiftool -GPS:all image.jpg

# Mostrar os nomes dos grupos
exiftool -G image.jpg
```

### Grupos de metadados comuns

| Grupo | Descrição |
|-------|-------------|
| EXIF | Dados da câmara/imagem |
| IPTC | Metadados de notícias/imprensa |
| XMP | Metadados extensíveis da Adobe |
| GPS | Dados de localização |
| MakerNotes | Dados específicos da câmara |
| File | Informações básicas do ficheiro |
| Composite | Valores calculados |

### Formatos de saída

```bash
# Saída em JSON
exiftool -j image.jpg

# Saída em CSV
exiftool -csv image.jpg

# Saída em HTML
exiftool -htmlDump image.jpg > output.html

# Separado por tabulações
exiftool -t image.jpg

# Formato personalizado
exiftool -p '$filename: $make $model' image.jpg
```

---

<a id="-writing-metadata"></a>
## ✏️ Escrever metadados

### Escrever uma tag

```bash
# Definir o autor
exiftool -Author="John Doe" image.jpg

# Definir os direitos de autor
exiftool -Copyright="© 2024 Company" image.jpg

# Definir um comentário
exiftool -Comment="My photo" image.jpg
```

### Escrever várias tags

```bash
exiftool -Author="John Doe" -Copyright="© 2024" -Keywords="nature,landscape" image.jpg
```

### Escrever coordenadas GPS

```bash
# Definir coordenadas GPS
exiftool -GPSLatitude=37.7749 -GPSLongitude=-122.4194 image.jpg

# Com referências de hemisfério
exiftool -GPSLatitude=37.7749 -GPSLatitudeRef=N -GPSLongitude=122.4194 -GPSLongitudeRef=W image.jpg

# A partir do formato de coordenadas do Google Maps
exiftool "-GPSLatitude=37 46 29.64" "-GPSLongitude=122 25 9.84" -GPSLatitudeRef=N -GPSLongitudeRef=W image.jpg
```

### Escrever data/hora

```bash
# Definir data/hora
exiftool -DateTimeOriginal="2024:01:15 10:30:00" image.jpg

# Definir todas as datas (conforme o âmbito de AllDates; ver nota)
exiftool -AllDates="2024:01:15 10:30:00" image.jpg

# Deslocar datas
exiftool "-DateTimeOriginal+=1:0:0 0:0:0" image.jpg  # Adicionar 1 ano
exiftool "-DateTimeOriginal-=0:0:7 0:0:0" image.jpg  # Subtrair 7 dias
```

### Copiar metadados entre ficheiros

```bash
# Copiar todos os metadados
exiftool -TagsFromFile source.jpg destination.jpg

# Copiar tags específicas
exiftool -TagsFromFile source.jpg -GPS:all -EXIF:all destination.jpg

# Copiar para vários ficheiros
exiftool -TagsFromFile source.jpg -GPS:all *.jpg
```

---

<a id="-removing-metadata"></a>
## 🗑️ Remover metadados

### Remover todos os metadados

```bash
# Remover todos os metadados (mantendo o ficheiro utilizável, segundo o original)
exiftool -all= image.jpg

# Remover sem cópia de segurança
exiftool -overwrite_original -all= image.jpg

# Processar vários ficheiros
exiftool -all= *.jpg
exiftool -r -all= directory/
```

### Remover metadados específicos

```bash
# Remover apenas dados GPS
exiftool -GPS:all= image.jpg
exiftool -geotag= image.jpg

# Remover apenas EXIF
exiftool -EXIF:all= image.jpg

# Remover comentários
exiftool -Comment= image.jpg

# Remover informações do autor
exiftool -Author= -Creator= -Artist= image.jpg
```

### Remover de tipos de ficheiros específicos

```bash
# Imagens
exiftool -all= *.jpg *.png *.gif

# PDFs
exiftool -all= document.pdf

# Ficheiros Office
exiftool -all= *.docx *.xlsx *.pptx

# Vídeos
exiftool -all= video.mp4
```

### Remover informações para proteger a privacidade

```bash
# Remover todas as informações de identificação
exiftool -all= -overwrite_original \
  -gps:all= \
  -Author= \
  -Creator= \
  -XMP:all= \
  image.jpg
```

---

<a id="-file-types"></a>
## 📁 Tipos de ficheiros

### Tipos de ficheiros suportados (mais de 400)

| Categoria | Formatos |
|----------|---------|
| **Imagens** | JPEG, PNG, GIF, TIFF, BMP, WebP, HEIC, RAW |
| **Fotografias RAW** | CR2, NEF, ARW, DNG, ORF, RW2 |
| **Vídeo** | MP4, MOV, AVI, MKV, WebM |
| **Áudio** | MP3, WAV, FLAC, M4A, OGG |
| **Documentos** | PDF, DOCX, XLSX, PPTX |
| **Arquivos** | ZIP (suporte limitado) |

### Específico de cada tipo de ficheiro

```bash
# Metadados PDF
exiftool document.pdf
exiftool -Title -Author -Creator -Producer document.pdf

# Metadados de vídeo
exiftool -Duration -VideoFrameRate -ImageWidth -ImageHeight video.mp4

# Metadados de áudio
exiftool -Artist -Album -Title -Duration audio.mp3

# Documentos Office
exiftool -Author -LastModifiedBy -CreateDate -ModifyDate file.docx
```

---

<a id="-osint--forensics"></a>
## 🔍 OSINT e análise forense

### Extrair a localização GPS

```bash
# Mostrar coordenadas GPS
exiftool -GPS:all image.jpg

# GPS em formato legível
exiftool -GPSPosition image.jpg

# Graus decimais
exiftool -n -GPSLatitude -GPSLongitude image.jpg

# Google Maps URL
exiftool -p 'https://www.google.com/maps?q=$GPSLatitude,$GPSLongitude' image.jpg
```

### Identificação do dispositivo

```bash
# Informações da câmara
exiftool -Make -Model -SerialNumber image.jpg

# Software utilizado
exiftool -Software -CreatorTool image.jpg

# Informações da objetiva (se disponíveis)
exiftool -Lens -LensModel -FocalLength image.jpg

# Informações detalhadas da câmara
exiftool -MakerNotes:all image.jpg
```

### Análise cronológica

```bash
# Todas as datas/horas
exiftool -time:all image.jpg

# Datas/horas do sistema de ficheiros
exiftool -FileModifyDate -FileAccessDate -FileCreateDate image.jpg

# Datas/horas EXIF
exiftool -DateTimeOriginal -CreateDate -ModifyDate image.jpg

# Procurar discrepâncias nas datas
exiftool -a -G1 -s -time:all image.jpg
```

### Análise forense de documentos

```bash
# Análise de PDF
exiftool -a -G1 document.pdf

# Histórico do ficheiro Office
exiftool -a -G1 -RevisionNumber -TotalEditTime -LastPrinted file.docx

# Procurar informações do autor
exiftool -Author -Creator -LastModifiedBy -Company document.pdf
```

### Autenticidade da imagem

```bash
# Procurar software de edição
exiftool -Software -HistorySoftwareAgent image.jpg

# Comparar a miniatura com a imagem
exiftool -b -ThumbnailImage image.jpg > thumb.jpg

# Procurar modificações
exiftool -ModifyDate -CreateDate -DateTimeOriginal image.jpg
```

### Consultas úteis de OSINT

```bash
# Extrair todas as informações de identificação
exiftool -Make -Model -SerialNumber -Artist -Copyright -GPSPosition image.jpg

# Procurar indícios de ferramentas de esteganografia
exiftool -Software image.jpg | grep -i "steg\|hide"

# Extrair a miniatura incorporada
exiftool -b -ThumbnailImage image.jpg > thumbnail.jpg

# Procurar artefactos de redes sociais
exiftool -a -G1 image.jpg | grep -i "facebook\|instagram\|twitter"
```

---

<a id="-batch-processing"></a>
## ⚙️ Processamento em lote

### Processar vários ficheiros

```bash
# Todos os JPG no diretório atual
exiftool *.jpg

# Recursivo
exiftool -r directory/

# Extensões específicas
exiftool -ext jpg -ext png directory/
```

### Remover metadados em lote

```bash
# Remover de todas as imagens
exiftool -all= -overwrite_original -r directory/

# Apenas tipos específicos
exiftool -ext jpg -ext png -all= -overwrite_original -r directory/
```

### Mudar nomes de ficheiros em lote

```bash
# Mudar o nome de acordo com a data
exiftool '-filename<CreateDate' -d '%Y%m%d_%H%M%S%%-c.%%e' *.jpg

# Mudar o nome de acordo com o modelo da câmara
exiftool '-filename<${Make}_${Model}_${filename}' *.jpg

# Mudar o nome com um contador
exiftool '-filename<photo_%04c.%%e' *.jpg
```

### Copiar metadados em lote

```bash
# Copiar os dados GPS de um ficheiro para todos
exiftool -TagsFromFile source.jpg -GPS:all -overwrite_original *.jpg
```

### Exportar para CSV

```bash
# Exportar os metadados para CSV
exiftool -csv *.jpg > metadata.csv

# Campos específicos
exiftool -csv -filename -GPSLatitude -GPSLongitude -Make -Model *.jpg > gps_data.csv
```

### Processamento condicional

```bash
# Apenas ficheiros com GPS
exiftool -if '$GPSLatitude' -GPSPosition *.jpg

# Apenas ficheiros de uma câmara específica
exiftool -if '$Make eq "Canon"' -filename *.jpg

# Ficheiros modificados após a data
exiftool -if '$ModifyDate gt "2024:01:01"' *.jpg
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos mais utilizados

| Finalidade | Comando |
|---------|---------|
| Ver todos os metadados | `exiftool image.jpg` |
| Ver GPS | `exiftool -GPS:all image.jpg` |
| Remover todos os metadados | `exiftool -all= image.jpg` |
| Remover GPS | `exiftool -GPS:all= image.jpg` |
| Saída em JSON | `exiftool -j image.jpg` |
| Recursivo | `exiftool -r directory/` |
| Informações da câmara | `exiftool -Make -Model image.jpg` |
| Datas e horas | `exiftool -time:all image.jpg` |

### Tags comuns

| Tag | Descrição |
|-----|-------------|
| `-Make` | Fabricante da câmara |
| `-Model` | Modelo da câmara |
| `-DateTimeOriginal` | Quando a fotografia foi tirada |
| `-GPSLatitude` | Latitude GPS |
| `-GPSLongitude` | Longitude GPS |
| `-Author` | Autor do ficheiro |
| `-Copyright` | Informações de direitos de autor |
| `-Software` | Software de edição |
| `-ImageWidth` | Largura da imagem |
| `-ImageHeight` | Altura da imagem |

### Lista de verificação de privacidade

```bash
# 1. Verificar que metadados existem
exiftool -a -G1 image.jpg

# 2. Verificar a presença de GPS
exiftool -GPS:all image.jpg

# 3. Procurar informações de identificação
exiftool -Make -Model -SerialNumber -Author -Software image.jpg

# 4. Remover tudo
exiftool -all= -overwrite_original image.jpg

# 5. Verificar a remoção
exiftool image.jpg
```

### Lista de verificação forense

```bash
# 1. Preservar o original (criar primeiro uma cópia!)
cp image.jpg image_original.jpg

# 2. Extração completa de metadados
exiftool -a -G1 -s image.jpg > metadata_report.txt

# 3. Extrair GPS
exiftool -GPS:all -c "%.6f" image.jpg

# 4. Cronologia
exiftool -time:all -G1 image.jpg

# 5. Informações do dispositivo
exiftool -Make -Model -SerialNumber -LensModel image.jpg

# 6. Procurar indícios de adulteração
exiftool -Software -HistorySoftwareAgent image.jpg
```

---

<a id="-resources"></a>
## 📚 Recursos

- [Site oficial do ExifTool](https://exiftool.org/)
- [Documentação do ExifTool](https://exiftool.org/TagNames/)
- [Fórum do ExifTool](https://exiftool.org/forum/)
- [Tipos de ficheiros suportados](https://exiftool.org/#supported)

### Guias de referência relacionados
- [Autopsy](../../Autopsy/translations/README.pt.md)
- [Volatility](../../Volatility/translations/README.pt.md)
- [Comandos Linux](../../Linux-Commands/README.md)

---

<p align="center">
  <b>📷 Domine a análise de metadados!</b><br>
  <i>ExifTool — Cada ficheiro conta uma história</i>
</p>
