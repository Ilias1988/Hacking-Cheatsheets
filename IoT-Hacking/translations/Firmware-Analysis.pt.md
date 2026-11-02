# 💾 Guia de referência de análise de firmware

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Firmware-Analysis.tr.md) • [🇬🇷 Ελληνικά](Firmware-Analysis.el.md)

---

## 📥 Obtenção de firmware

OTA (over-the-air) é a atualização recebida por uma ligação sem fios ou de rede. MITM (Man-in-the-Middle) é a interposição entre as partes de uma comunicação; dump é uma cópia do conteúdo de uma memória.

```bash
# Descarregar do site do fabricante
# Capturar uma atualização OTA (MITM)
# Obter uma cópia da memória do dispositivo (UART, SPI, JTAG)
# Extrair da aplicação móvel
```

---

## 🔍 Análise inicial

```bash
# Tipo de ficheiro
file firmware.bin

# Cadeias de texto (strings)
strings firmware.bin | grep -i password
strings firmware.bin | grep -i admin
strings firmware.bin | grep -i http

# Entropia (detetar compressão ou cifra)
binwalk -E firmware.bin
```

---

## 📦 Extração com Binwalk

> **Versões:** o projeto documenta o [Binwalk v3](https://github.com/ReFirmLabs/binwalk/blob/master/README.md). Os exemplos abaixo são preservados do original; confirma as opções suportadas pela versão instalada, sobretudo nas extrações por assinatura. A entropia é um indício de compressão ou cifra, não uma confirmação por si só.

```bash
# Analisar o firmware
binwalk firmware.bin

# Extrair tudo
binwalk -e firmware.bin

# Extrair para um diretório
binwalk -e -C output firmware.bin

# Extração recursiva
binwalk -eM firmware.bin

# Assinaturas específicas
binwalk -D 'elf:elf' firmware.bin
```

---

## 🗂️ Sistemas de ficheiros

### SquashFS

> **Precisão do original:** `unsquashfs` extrai uma imagem SquashFS; não monta um diretório. O argumento deve identificar a imagem do sistema de ficheiros. Se `squashfs-root` já for o diretório extraído, o comando original com esse argumento não se aplica. Ver o [manual de unsquashfs distribuído pelo Debian](https://manpages.debian.org/testing/squashfs-tools/unsquashfs.1.en.html).
```bash
# Extrair
unsquashfs squashfs-root
unsquashfs -d output filesystem.squashfs

# Recriar a imagem
mksquashfs output modified.squashfs -comp xz
```

### JFFS2

> **Condição do exemplo:** a sequência original pressupõe que `/dev/mtdblock0` corresponde ao dispositivo de memória RAM criado para este teste. O número não é universal; confirma o mapeamento antes de usar o exemplo, pois `dd` escreve no dispositivo indicado em `of=`.
```bash
# Montar
modprobe jffs2
modprobe mtdram total_size=32768 erase_size=256
dd if=jffs2.img of=/dev/mtdblock0
mount -t jffs2 /dev/mtdblock0 /mnt
```

### UBIFS
```bash
# Extrair com ubi_reader
ubireader_extract_files image.ubi
```

---

## 🔐 Segredos e credenciais

```bash
# Procurar credenciais
grep -rni "password" filesystem/
grep -rni "admin" filesystem/
grep -rni "api_key" filesystem/
grep -rni "secret" filesystem/

# Encontrar chaves SSH
find . -name "id_rsa*"
find . -name "*.pem"

# Encontrar ficheiros de configuração
find . -name "*.conf"
find . -name "*.cfg"
cat etc/shadow
cat etc/passwd
```

---

## 🔄 Análise de binários

```bash
# Encontrar binários ELF
find . -type f -exec file {} \; | grep ELF

# Analisar com o Ghidra
ghidraRun

# Encontrar cadeias de texto incorporadas no código
strings binary | grep -E "http|ftp|telnet|ssh"
```

---

## 🛠️ Ferramentas

| Ferramenta | Finalidade |
|------|---------|
| **Binwalk** | Extração |
| **firmware-mod-kit** | Modificação de firmware |
| **EMBA** | Análise automatizada |
| **Ghidra** | Engenharia inversa de binários |
| **firmwalker** | Pesquisa de segredos |

---

## 📋 Lista de verificação de análise de firmware

```markdown
□ Identificar o tipo de ficheiro
□ Verificar a entropia (está cifrado?)
□ Extrair com o Binwalk
□ Montar o sistema de ficheiros
□ Procurar credenciais
□ Analisar binários
□ Verificar se existem acessos ocultos (backdoors)
□ Procurar interfaces de depuração
```

---

**Voltar a IoT:** [📡 Hacking de IoT](./README.pt.md)
