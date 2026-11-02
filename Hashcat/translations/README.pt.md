# ⚡ Hashcat — Guia de referência completo

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  _   _           _                _   
 | | | | __ _ ___| |__   ___ __ _| |_ 
 | |_| |/ _` / __| '_ \ / __/ _` | __|
 |  _  | (_| \__ \ | | | (_| (_| | |_ 
 |_| |_|\__,_|___/_| |_|\___\__,_|\__|
                                       
    A recuperação de palavras-passe mais rápida do mundo
```

<p align="center">
  <img src="https://img.shields.io/badge/Hashcat-GPU_Cracker-red?style=for-the-badge" alt="Hashcat">
  <img src="https://img.shields.io/badge/350+-Hash_Types-orange?style=for-the-badge" alt="Tipos de hash">
  <img src="https://img.shields.io/badge/GPU-Accelerated-blue?style=for-the-badge" alt="GPU">
  <img src="https://img.shields.io/badge/World's-Fastest-green?style=for-the-badge" alt="Mais rápido">
</p>

<p align="center">
  <b>🚀 O utilitário de recuperação de palavras-passe mais rápido e avançado do mundo</b>
</p>

---

## 📋 Índice

- [Introdução](#-introduction)
- [Instalação](#-installation)
- [Sintaxe básica](#-basic-syntax)
- [Modos de ataque](#-attack-modes)
- [Modos de hash](#-hash-modes)
- [Ataque com máscara](#-mask-attack)
- [Regras](#-rules)
- [Gestão de sessões](#-session-management)
- [Otimização de GPU](#-gpu-optimization)
- [Exemplos práticos](#-real-world-examples)
- [Referência rápida](#-quick-reference)
- [Dicas e boas práticas](#-tips--best-practices)
- [Recursos](#-resources)

---

<a id="-introduction"></a>
## 🎯 Introdução

**Hashcat** é apresentado como o utilitário de recuperação de palavras-passe mais rápido e avançado do mundo, com suporte para mais de 350 tipos de hash e aceleração por GPU.

Uma wordlist é uma lista de candidatos. Uma máscara descreve as classes de caracteres em cada posição; regras transformam as palavras candidatas. Um kernel é a rotina de cálculo executada no dispositivo. Cracking compara hashes de candidatos, em vez de «desencriptar» o hash.

### Principais funcionalidades

| Funcionalidade | Descrição |
|---------|-------------|
| **Mais de 350 tipos de hash** | MD5, SHA, NTLM, bcrypt, WPA e outros |
| **Acelerado por GPU** | NVIDIA CUDA, AMD OpenCL, Intel |
| **Várias GPU** | Suporte de quebra distribuída |
| **5 modos de ataque** | Dicionário, força bruta, híbrido, etc. |
| **Regras poderosas** | Transformação avançada de palavras |
| **Multiplataforma** | Linux, Windows, macOS |

### Hashcat comparado com John the Ripper

> **Contexto de versões:** a comparação e as contagens são simplificações do original. Versões atuais incluem `--identify` e deteção automática em determinadas condições; não interpretes «sem deteção» como uma limitação universal. Ver a [ajuda oficial atual](https://github.com/hashcat/hashcat/blob/master/src/usage.c).

| Funcionalidade | Hashcat | John |
|---------|---------|------|
| **Velocidade** | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ |
| **Suporte de GPU** | Excelente | Boa |
| **Tipos de hash** | 350+ | 300+ |
| **Deteção automática** | ❌ Não | ✅ Sim |
| **Facilidade de utilização** | Moderada | Mais simples |
| **Mais indicado para** | Quebra com GPU | Quebra com CPU |

---

<a id="-installation"></a>
## 📥 Instalação

### Kali Linux
```bash
sudo apt update && sudo apt install hashcat

# Com OpenCL
sudo apt install hashcat-nvidia  # NVIDIA
sudo apt install hashcat-amd     # AMD
```

### Ubuntu/Debian
```bash
sudo apt install hashcat
```

### Windows
```bash
# Descarregar do site oficial
https://hashcat.net/hashcat/

# Extrair e executar a partir da pasta
hashcat.exe -h
```

### A partir do código-fonte
```bash
git clone https://github.com/hashcat/hashcat.git
cd hashcat
make
./hashcat -h
```

### Verificar a instalação
```bash
hashcat --version
hashcat -I                    # Mostrar os dispositivos OpenCL
hashcat -b                    # Executar um teste de desempenho (benchmark)
```

---

<a id="-basic-syntax"></a>
## ⌨️ Sintaxe básica

### Sintaxe geral
```bash
hashcat [options] hash|hashfile [dictionary|mask]
```

### Opções essenciais

| Opção | Descrição |
|--------|-------------|
| `-m` | Tipo de hash (modo) |
| `-a` | Modo de ataque |
| `-o` | Ficheiro de saída |
| `-r` | Ficheiro de regras |
| `--show` | Mostrar palavras-passe recuperadas |
| `--status` | Ativar o ecrã de estado |
| `-w` | Perfil de carga de trabalho (1-4) |
| `--force` | Ignorar avisos |
| `-O` | Kernels otimizados |

### Início rápido
```bash
# Ataque básico de dicionário
hashcat -m 0 -a 0 hash.txt wordlist.txt

# Mostrar os hashes com palavra-passe recuperada
hashcat -m 0 hash.txt --show

# Consultar hashes de exemplo
hashcat -m 0 --example-hashes
```

---

<a id="-attack-modes"></a>
## 🎮 Modos de ataque

### Visão geral

| Modo | Nome | Descrição |
|------|------|-------------|
| `-a 0` | Dicionário | Ataque com lista de palavras |
| `-a 1` | Combinação | Combinar duas listas de palavras |
| `-a 3` | Força bruta | Ataque baseado em máscara |
| `-a 6` | Híbrido de dicionário + máscara | Lista de palavras + máscara |
| `-a 7` | Híbrido de máscara + dicionário | Máscara + lista de palavras |
| `-a 9` | Associação | Associações de palavras |

### Ataque de dicionário (-a 0)

```bash
# Ataque básico de dicionário
hashcat -m 0 -a 0 hash.txt wordlist.txt

# Com regras
hashcat -m 0 -a 0 hash.txt wordlist.txt -r rules/best64.rule

# Várias listas de palavras
hashcat -m 0 -a 0 hash.txt wordlist1.txt wordlist2.txt
```

### Ataque de combinação (-a 1)

Combina palavras de duas listas de palavras.

```bash
# Combinar duas listas de palavras
hashcat -m 0 -a 1 hash.txt wordlist1.txt wordlist2.txt

# Combinações no formato word1word2
```

### Ataque de força bruta/máscara (-a 3)

> **Precisão do exemplo:** a máscara de quatro posições `?d?d?d?d` não se estende automaticamente até oito posições com `--increment-max=8`. O limite superior continua condicionado pelo comprimento da máscara fornecida. Ver o [guia oficial de ataques com máscara](https://hashcat.net/wiki/doku.php?id=mask_attack).

```bash
# Apenas minúsculas, 6 caracteres
hashcat -m 0 -a 3 hash.txt ?l?l?l?l?l?l

# Apenas algarismos, 4-8 caracteres
hashcat -m 0 -a 3 hash.txt ?d?d?d?d --increment --increment-min=4 --increment-max=8

# Conjunto de caracteres personalizado
hashcat -m 0 -a 3 hash.txt -1 ?l?d ?1?1?1?1?1?1
```

### Ataques híbridos (-a 6, -a 7)

```bash
# Dicionário + máscara (acrescentar no fim)
hashcat -m 0 -a 6 hash.txt wordlist.txt ?d?d?d?d

# Máscara + dicionário (acrescentar no início)
hashcat -m 0 -a 7 hash.txt ?d?d?d?d wordlist.txt
```

---

<a id="-hash-modes"></a>
## 🔢 Modos de hash

### Modos de hash mais comuns

> As amostras com `...` ou comprimento reduzido são ilustrativas e não hashes completos para execução. Confirma o formato exigido pelo modo e a compatibilidade dos modos antigos, como `2500`, na versão utilizada. As tabelas originais foram preservadas.

| Modo | Tipo de hash | Exemplo |
|------|-----------|---------|
| 0 | MD5 | `5d41402abc4b2a76b9719d911017c592` |
| 100 | SHA1 | `aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d` |
| 1400 | SHA256 | `2cf24dba5fb0a30e26e83b2ac5b9e29e...` |
| 1700 | SHA512 | `cf83e1357eefb8bdf1542850d66d8007d620e405...` |
| 1000 | NTLM | `32ed87bdb5fdc5e9cba88547376818d4` |
| 3000 | LM | `aad3b435b51404ee` |
| 5600 | NetNTLMv2 | Formato complexo |
| 3200 | bcrypt | `$2a$10$...` |
| 1800 | sha512crypt (Linux) | `$6$...` |
| 500 | md5crypt (Linux) | `$1$...` |

### Aplicações web

| Modo | Tipo de hash |
|------|-----------|
| 400 | WordPress (phpass) |
| 2500 | WPA/WPA2 |
| 22000 | WPA-PBKDF2-PMKID+EAPOL |
| 121 | SMF |
| 2611 | vBulletin |
| 3711 | MediaWiki B |
| 7900 | Drupal7 |

### Bases de dados

| Modo | Tipo de hash |
|------|-----------|
| 300 | MySQL4.1/MySQL5 |
| 200 | MySQL323 |
| 1731 | MSSQL (2012, 2014) |
| 112 | Oracle S |
| 12300 | Oracle T |

### Documentos e ficheiros compactados

| Modo | Tipo de hash |
|------|-----------|
| 9600 | Office 2013 |
| 9500 | Office 2010 |
| 9400 | Office 2007 |
| 9700 | MS Office ⇐ 2003 |
| 10500 | PDF 1.4-1.6 |
| 10700 | PDF 1.7 Level 8 |
| 11600 | 7-Zip |
| 13600 | WinZip |
| 17200 | PKZIP |
| 13000 | RAR5 |
| 12500 | RAR3-hp |

### Criptomoedas

| Modo | Tipo de hash |
|------|-----------|
| 11300 | Carteira Bitcoin |
| 12700 | Carteira Blockchain |
| 16600 | Carteira Electrum |
| 15200 | Ethereum Keystore |

### Listar e pesquisar modos
```bash
# Listar todos os modos de hash
hashcat --help | grep -i "hash modes"

# Procurar um hash específico
hashcat --example-hashes | grep -i "ntlm"
hashcat --example-hashes | grep -i "sha256"

# Mostrar um exemplo do formato do hash
hashcat -m 1000 --example-hashes
```

---

<a id="-mask-attack"></a>
## 🎭 Ataque com máscara

### Marcadores de conjuntos de caracteres

| Marcador | Caracteres |
|-------------|------------|
| `?l` | abcdefghijklmnopqrstuvwxyz |
| `?u` | ABCDEFGHIJKLMNOPQRSTUVWXYZ |
| `?d` | 0123456789 |
| `?h` | 0123456789abcdef |
| `?H` | 0123456789ABCDEF |
| `?s` | !"#$%&'()*+,-./:;<=>?@[]^_`{|}~ |
| `?a` | ?l?u?d?s |
| `?b` | 0x00 - 0xff |

### Conjuntos de caracteres personalizados

```bash
# Definir um conjunto de caracteres personalizado
hashcat -a 3 -1 ?l?d hash.txt ?1?1?1?1?1?1

# Vários conjuntos de caracteres personalizados
hashcat -a 3 -1 abc -2 123 hash.txt ?1?2?1?2

# Caracteres especiais
hashcat -a 3 -1 '!@#$%' hash.txt pass?1?1?1?1
```

### Modo de incremento

```bash
# Testar comprimentos de 1-8 caracteres
hashcat -a 3 hash.txt ?a?a?a?a?a?a?a?a --increment

# Especificar o mínimo e o máximo
hashcat -a 3 hash.txt ?a?a?a?a?a?a?a?a --increment --increment-min=4 --increment-max=8
```

### Padrões comuns de máscaras

```bash
# 8 letras minúsculas
?l?l?l?l?l?l?l?l

# Palavra-passe + 4 algarismos
password?d?d?d?d

# Maiúscula + minúsculas + algarismos
?u?l?l?l?l?l?d?d

# Padrão Winter2024
?u?l?l?l?l?l?d?d?d?d

# Número de telefone
?d?d?d?d?d?d?d?d?d?d
```

---

<a id="-rules"></a>
## 📜 Regras

### Ficheiros de regras incluídos

```bash
# Localização
ls /usr/share/hashcat/rules/

# Regras populares
best64.rule           # As 64 melhores regras
rockyou-30000.rule    # As 30000 regras mais frequentes
d3ad0ne.rule          # Regras D3ad0ne
dive.rule             # Regras Dive
generated.rule        # Regras geradas
```

### Utilização de regras

```bash
# Um ficheiro de regras
hashcat -m 0 -a 0 hash.txt wordlist.txt -r best64.rule

# Vários ficheiros de regras
hashcat -m 0 -a 0 hash.txt wordlist.txt -r rule1.rule -r rule2.rule

# Gerar regras
hashcat -m 0 -a 0 hash.txt wordlist.txt -g 1000
```

### Operações comuns das regras

> Em `sXY`, X e Y são parâmetros: `password → pa$$word` ilustra substituir `s` por `$`. Ver as [regras oficiais](https://hashcat.net/wiki/doku.php?id=rule_based_attack).

| Regra | Descrição | Exemplo |
|------|-------------|---------|
| `:` | Não alterar | password |
| `l` | Converter tudo em minúsculas | PASSWORD → password |
| `u` | Converter tudo em maiúsculas | password → PASSWORD |
| `c` | Colocar a primeira letra em maiúscula | password → Password |
| `C` | Primeira letra em minúscula e restantes em maiúsculas | password → pASSWORD |
| `t` | Inverter maiúsculas e minúsculas | password → PASSWORD |
| `r` | Inverter | password → drowssap |
| `d` | Duplicar | pass → passpass |
| `$X` | Acrescentar X no fim | pass → passX |
| `^X` | Acrescentar X no início | pass → Xpass |
| `sXY` | Substituir X por Y | password → pa$$word |

---

<a id="-session-management"></a>
## 💾 Gestão de sessões

### Criar uma sessão
```bash
# Sessão com nome
hashcat -m 0 -a 0 hash.txt wordlist.txt --session=mysession

# Ficheiros de sessão guardados em ~/.hashcat/sessions/
```

### Pausar e retomar
```bash
# Pausar: premir 'p' durante a recuperação
# Retomar: premir 'r' durante a recuperação

# Ou restaurar a partir da linha de comandos
hashcat --session=mysession --restore
```

### Comandos de estado

Durante a recuperação de palavras-passe:
- `s` — Mostrar o estado
- `p` — Pausar
- `r` — Retomar
- `b` — Saltar o ataque atual
- `c` — Parar num ponto de controlo (checkpoint)
- `q` — Sair

### Opções de saída

> **Correção do comentário original:** `--outfile-format=2` corresponde apenas ao texto recuperado (`plain`); a combinação `1,2` representa hash e texto. Ver a [referência oficial de formatos de saída](https://hashcat.net/wiki/doku.php?id=hashcat).
```bash
# Guardar a saída num ficheiro
hashcat -m 0 -a 0 hash.txt wordlist.txt -o cracked.txt

# Formato de saída
hashcat -m 0 hash.txt --outfile-format=2    # hash:plain

# Mostrar as palavras-passe recuperadas
hashcat -m 0 hash.txt --show

# Mostrar os restantes hashes (ainda sem palavra-passe recuperada)
hashcat -m 0 hash.txt --left
```

---

<a id="-gpu-optimization"></a>
## 🚀 Otimização de GPU

### Perfis de carga de trabalho

> **Versões:** estes perfis e a respetiva marcação como «predefinido» vêm do original; confirma a disponibilidade de `-w` e os valores da versão instalada. `--force` ignora avisos, não corrige a configuração nem garante maior velocidade. Ver a [ajuda oficial atual](https://github.com/hashcat/hashcat/blob/master/src/usage.c).

| Perfil | Descrição |
|---------|-------------|
| `-w 1` | Baixo (predefinido, interativo) |
| `-w 2` | Médio |
| `-w 3` | Alto (pode bloquear o ambiente de trabalho) |
| `-w 4` | Máximo (sistema dedicado à recuperação) |

### Otimizar comandos
```bash
# Alto desempenho
hashcat -m 0 -a 0 hash.txt wordlist.txt -w 3 -O

# Kernels otimizados (mais rápidos, com limite de comprimento de palavra-passe)
hashcat -m 0 -a 0 hash.txt wordlist.txt -O

# Forçar a seleção do dispositivo
hashcat -m 0 -a 0 hash.txt wordlist.txt -d 1

# Desativar a CPU e utilizar apenas a GPU
hashcat -m 0 -a 0 hash.txt wordlist.txt -D 2
```

### Opções de dispositivos

> **Precisão do original:** `-D` seleciona tipos de dispositivo OpenCL; `-D 3 = FPGA` não é uma correspondência universal. A ajuda atual lista CPU e GPU e trata separadamente hardware através de bridges. Ver a [referência oficial](https://github.com/hashcat/hashcat/blob/master/src/usage.c).
```bash
# Listar dispositivos
hashcat -I

# Selecionar um dispositivo específico
hashcat -d 1 ...

# Tipos de dispositivo
# -D 1 = CPU
# -D 2 = GPU
# -D 3 = FPGA
```

### Teste de desempenho

> **Precisão do original:** `--benchmark-all` pede um benchmark de todos os modos de hash; não significa desativar a otimização OpenCL. Ver a [ajuda oficial](https://hashcat.net/wiki/doku.php?id=hashcat).
```bash
# Teste de desempenho completo
hashcat -b

# Tipo de hash específico
hashcat -b -m 0

# Teste de desempenho sem otimização OpenCL
hashcat -b --benchmark-all
```

---

<a id="-real-world-examples"></a>
## 🎬 Exemplos práticos

### Exemplo 1: Ataque de dicionário a MD5
```bash
hashcat -m 0 -a 0 md5_hashes.txt /usr/share/wordlists/rockyou.txt
```

### Exemplo 2: NTLM com regras
```bash
hashcat -m 1000 -a 0 ntlm_hashes.txt wordlist.txt -r /usr/share/hashcat/rules/best64.rule
```

### Exemplo 3: Handshake WPA/WPA2
```bash
# Converter primeiro para o formato do Hashcat
hcxpcapngtool -o hash.hc22000 capture.pcapng

# Recuperar palavras-passe
hashcat -m 22000 hash.hc22000 /usr/share/wordlists/rockyou.txt
```

### Exemplo 4: Força bruta a SHA256 (6 caracteres)
```bash
hashcat -m 1400 -a 3 sha256_hash.txt ?a?a?a?a?a?a
```

### Exemplo 5: Documento Office 2013
```bash
# Extrair o hash com office2john
office2john document.docx > office.hash

# Recuperar palavras-passe
hashcat -m 9600 office.hash wordlist.txt
```

### Exemplo 6: Palavra-passe do shadow do Linux
```bash
hashcat -m 1800 -a 0 shadow_hash.txt wordlist.txt
```

### Exemplo 7: Hash do WordPress
```bash
hashcat -m 400 -a 0 wordpress_hash.txt wordlist.txt
```

### Exemplo 8: Ataque híbrido
```bash
# Nome da empresa + 4 algarismos
hashcat -m 0 -a 6 hash.txt company_names.txt ?d?d?d?d
```

### Exemplo 9: Hashes de domínio Windows
```bash
# Hashes NTLM obtidos com secretsdump
hashcat -m 1000 -a 0 ntds_hashes.txt wordlist.txt -r best64.rule --username
```

### Exemplo 10: Carteira Bitcoin
```bash
# Extrair com bitcoin2john
hashcat -m 11300 bitcoin_hash.txt wordlist.txt
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos essenciais

| Tarefa | Comando |
|------|---------|
| Dicionário para MD5 | `hashcat -m 0 -a 0 hash.txt wordlist.txt` |
| Força bruta para NTLM | `hashcat -m 1000 -a 3 hash.txt ?a?a?a?a?a?a` |
| SHA256 com regras | `hashcat -m 1400 -a 0 hash.txt wordlist.txt -r rules.rule` |
| Mostrar as palavras-passe recuperadas | `hashcat -m 0 hash.txt --show` |
| Teste de desempenho | `hashcat -b` |
| Listar dispositivos | `hashcat -I` |
| Restaurar a sessão | `hashcat --session=name --restore` |

### Modos de hash comuns

| Modo | Tipo |
|------|------|
| 0 | MD5 |
| 100 | SHA1 |
| 1000 | NTLM |
| 1400 | SHA256 |
| 1800 | sha512crypt |
| 3200 | bcrypt |
| 22000 | WPA2 |

### Modos de ataque

| Modo | Tipo |
|------|------|
| -a 0 | Dicionário |
| -a 1 | Combinação |
| -a 3 | Força bruta |
| -a 6 | Híbrido (dicionário + máscara) |
| -a 7 | Híbrido (máscara + dicionário) |

---

<a id="-tips--best-practices"></a>
## 💡 Dicas e boas práticas

### Dicas de eficiência

1. **Começar com dicionário + regras**
   ```bash
   hashcat -m 0 -a 0 hash.txt rockyou.txt -r best64.rule
   ```

2. **Utilizar kernels otimizados**
   ```bash
   hashcat -O ...
   ```

3. **Testar padrões comuns**
   ```bash
   # Estação do ano + ano: Winter2024
   hashcat -a 3 -1 WSFJ -2 winterumeall hash.txt ?1?2?2?2?2?2?d?d?d?d
   ```

### Dicas de desempenho

```bash
# Desempenho máximo (sistema dedicado)
hashcat -m 0 -a 0 hash.txt wordlist.txt -w 4 -O --force

# Várias GPU
hashcat -d 1,2 ...
```

### Sequência de trabalho

```
1. Identificar o tipo de hash
2. Testar um ataque de dicionário com regras
3. Testar máscaras adaptadas ao alvo (padrões)
4. Testar força bruta completa (se for viável)
5. Testar ataques híbridos
```

---

## ⚠️ Aviso legal

> **AVISO:** O Hashcat é uma ferramenta poderosa de quebra de palavras-passe que deve ser utilizada apenas para **fins autorizados**.
> 
> - ✅ Auditar os teus próprios sistemas
> - ✅ Realizar avaliações de segurança com autorização
> - ✅ Recuperar as tuas próprias palavras-passe
> - ❌ Nunca quebrar palavras-passe sem autorização
> - ❌ O acesso não autorizado é ilegal
> 
> **Quebrar palavras-passe sem autorização é ilegal e pode resultar em acusações criminais.**

---

<a id="-resources"></a>
## 📚 Recursos

### Recursos oficiais
- [Site oficial do Hashcat](https://hashcat.net/hashcat/)
- [Wiki do Hashcat](https://hashcat.net/wiki/)
- [Hashcat GitHub](https://github.com/hashcat/hashcat)
- [Fórum do Hashcat](https://hashcat.net/forum/)

### Listas de palavras
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [CrackStation](https://crackstation.net/crackstation-wordlist-password-cracking-dictionary.htm)

### Guias de referência relacionados
- [John the Ripper](../../John-The-Ripper/translations/README.pt.md)
- [Hydra](../../Hydra/translations/README.pt.md)
- [Nmap](../../Nmap/README.md)

---

<p align="center">
  <b>⚡ Domina a quebra de palavras-passe com GPU!</b><br>
  <i>A recuperação de palavras-passe mais rápida do mundo</i>
</p>
