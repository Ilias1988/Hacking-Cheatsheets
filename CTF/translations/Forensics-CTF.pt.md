# 🔬 Guia de referência de análise forense para CTF

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Forensics-CTF.tr.md) • [🇬🇷 Ελληνικά](Forensics-CTF.el.md)

> **Notas de versão e precisão:** Esteganografia oculta dados noutro suporte; file carving procura recuperar ficheiros através da sua estrutura ou assinaturas. Os comandos `imageinfo` e `--profile=Win7SP1x64` pertencem ao Volatility 2. O [Volatility 3](https://volatility3.readthedocs.io/en/latest/getting-started-windows-tutorial.html) utiliza plugins e tabelas de símbolos com sintaxe diferente. No Audacity, [Plot Spectrum](https://manual.audacityteam.org/man/plot_spectrum.html) produz um gráfico de frequências, enquanto a [vista Spectrogram](https://manual.audacityteam.org/man/spectrogram_view.html) mostra a evolução temporal das frequências. A linha de menu no bloco bash é uma instrução de interface gráfica. `mount -o loop` não impõe montagem apenas de leitura, e `stegsolve.jar` identifica um ficheiro de aplicação, não garante uma invocação executável por si só.

---

## 📁 Análise de ficheiros

```bash
# Tipo de ficheiro
file unknown_file

# Representação hexadecimal (hex dump)
xxd file | head
hexdump -C file | head

# Cadeias de caracteres
strings -n 6 file
strings -e l file    # Little-endian (byte menos significativo primeiro)

# File carving (recuperação de ficheiros por assinaturas)
binwalk file
binwalk -e file      # Extrair
foremost file
```

---

## 🖼️ Esteganografia

### Imagens

```bash
# Metadados
exiftool image.jpg
identify -verbose image.png

# Steghide (JPEG)
steghide extract -sf image.jpg

# Zsteg (PNG)
zsteg image.png
zsteg -a image.png   # Todos os métodos

# Análise dos bits menos significativos (LSB)
stegsolve.jar        # Ferramenta com interface gráfica

# Cadeias de caracteres na imagem
strings image.png | grep -i flag
```

### Áudio
```bash
# Espectrograma (Audacity)
Abrir o ficheiro → Analyze → Plot Spectrum

# Sonic Visualiser
# Procurar dados ocultos nas frequências
```

---

## 🧠 Análise forense de memória

### Volatility
```bash
# Identificar o perfil
volatility -f dump.raw imageinfo

# Lista de processos
volatility -f dump.raw --profile=Win7SP1x64 pslist
volatility -f dump.raw --profile=Win7SP1x64 pstree

# Histórico de comandos
volatility -f dump.raw --profile=Win7SP1x64 cmdscan
volatility -f dump.raw --profile=Win7SP1x64 consoles

# Extrair a memória do processo
volatility -f dump.raw --profile=Win7SP1x64 procdump -p 1234 -D output/

# Ficheiros
volatility -f dump.raw --profile=Win7SP1x64 filescan | grep -i flag
```

---

## 💽 Análise forense de discos

```bash
# Montar a imagem
mount -o loop image.dd /mnt/disk

# Informações do sistema de ficheiros
fsstat image.dd

# Autopsy (GUI)
autopsy

# Sleuth Kit
fls image.dd
icat image.dd 1234 > recovered_file
```

---

## 📊 Análise forense de rede (PCAP)

```bash
# Wireshark
wireshark capture.pcap

# Tshark
tshark -r capture.pcap
tshark -r capture.pcap -Y "http" -T fields -e http.file_data

# Extrair ficheiros
tcpflow -r capture.pcap
```

---

## 📋 Lista de verificação de análise forense

```markdown
□ Verificar o tipo de ficheiro (comando file)
□ Pesquisar a flag nas cadeias de caracteres
□ Verificar os metadados (exiftool)
□ Utilizar Binwalk para encontrar ficheiros incorporados
□ Ferramentas de esteganografia
□ Análise de memória (Volatility)
□ Análise de PCAP (Wireshark)
```

---

**Voltar a CTF:** [🏁 Visão geral de CTF](README.pt.md)
