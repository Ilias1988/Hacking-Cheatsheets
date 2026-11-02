# 🔨 John the Ripper — Guia de referência completo

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
       __      __         
      / /___  / /_  ____  
 __  / / __ \/ __ \/ __ \ 
/ /_/ / /_/ / / / / / / / 
\____/\____/_/ /_/_/ /_/  
  A ferramenta de quebra de palavras-passe
```

<p align="center">
  <img src="https://img.shields.io/badge/John-Password_Cracker-red?style=for-the-badge" alt="John">
  <img src="https://img.shields.io/badge/Hash-Cracking-orange?style=for-the-badge" alt="Hash">
  <img src="https://img.shields.io/badge/Open-Source-blue?style=for-the-badge" alt="Código aberto">
  <img src="https://img.shields.io/badge/Multi-Platform-green?style=for-the-badge" alt="Multiplataforma">
</p>

<p align="center">
  <b>🔑 A lendária ferramenta de auditoria de segurança e recuperação de palavras-passe</b>
</p>

---

## 📋 Índice

- [Introdução](#-introduction)
- [Instalação](#-installation)
- [Sintaxe básica](#-basic-syntax)
- [Modos de quebra de palavras-passe](#-cracking-modes)
- [Extração de hashes](#-hash-extraction)
- [Tipos de hash comuns](#-common-hash-types)
- [Quebra de palavras-passe Linux](#-linux-password-cracking)
- [Quebra de palavras-passe Windows](#-windows-password-cracking)
- [Quebra de palavras-passe de ficheiros](#-file-password-cracking)
- [Regras e transformação de palavras](#-rules--mangling)
- [Gestão de sessões](#-session-management)
- [Ajuste de desempenho](#-performance-tuning)
- [Exemplos práticos](#-real-world-examples)
- [Referência rápida](#-quick-reference)
- [Dicas e boas práticas](#-tips--best-practices)
- [Recursos](#-resources)

---

<a id="-introduction"></a>
## 🎯 Introdução

**John the Ripper** (John/JtR) é uma ferramenta gratuita e de código aberto para auditoria de segurança e recuperação de palavras-passe. Foi concebida para detetar palavras-passe fracas e recuperar palavras-passe a partir dos respetivos hashes.

Um hash é um resultado derivado de uma palavra-passe; cracking consiste em testar candidatos e comparar os resultados, não em «desencriptar» o hash. GECOS é o campo de informações do utilizador em passwd. Word mangling significa transformar palavras, por exemplo alterando maiúsculas ou acrescentando caracteres. O ficheiro pot guarda os resultados já recuperados.

### Principais funcionalidades

| Funcionalidade | Descrição |
|---------|-------------|
| **Vários formatos** | Suporta centenas de tipos de hash |
| **Vários modos** | Single, lista de palavras e incremental |
| **Regras personalizadas** | Regras poderosas de transformação de palavras |
| **Deteção automática** | Deteção automática do formato do hash |
| **Multiplataforma** | Linux, Windows, macOS |
| **Suporte de GPU** | Aceleração OpenCL (Jumbo) |

### John comparado com Hashcat

> **Contexto de versões:** a comparação é simplificada. O Hashcat atual também inclui identificação de hashes; os resultados de velocidade dependem do formato e do equipamento. Ver a [ajuda oficial do Hashcat](https://github.com/hashcat/hashcat/blob/master/src/usage.c).

| Funcionalidade | John | Hashcat |
|---------|------|---------|
| **Mais indicado para** | Quebra com CPU | Quebra com GPU |
| **Facilidade de utilização** | Mais simples | Mais complexo |
| **Deteção automática** | ✅ Sim | ❌ Não |
| **Regras** | Poderosas | Muito poderosas |
| **Tipos de hash** | Muitos | Mais |
| **Velocidade (GPU)** | Boa | Melhor |

### Versões

| Versão | Descrição |
|---------|-------------|
| **John (Core)** | Versão base |
| **John Jumbo** | Versão melhorada pela comunidade (recomendada) |

---

<a id="-installation"></a>
## 📥 Instalação

### Kali Linux (pré-instalado)
```bash
john --version
```

### Ubuntu/Debian
```bash
# Versão normal
sudo apt install john

# Versão Jumbo (recomendada)
sudo apt install john-jumbo
```

### A partir do código-fonte (Jumbo)
```bash
git clone https://github.com/openwall/john
cd john/src
./configure
make -s clean && make -sj4
cd ../run
./john --version
```

### macOS (Homebrew)
```bash
brew install john-jumbo
```

### Windows
```bash
# Descarregar do site oficial
https://www.openwall.com/john/

# Ou utilizar o Chocolatey
choco install john
```

### Verificar a instalação

> **Versões e distribuição:** os nomes de pacotes, opções como `--version` e comandos 2john variam com a compilação e a distribuição; algumas ferramentas podem ter sufixos como `.py`. Confirma a versão e os executáveis fornecidos. A [página oficial do pacote no Kali](https://www.kali.org/tools/john/) documenta os comandos aí disponíveis.
```bash
john --version
john --list=formats | wc -l    # Contar os formatos suportados
```

---

<a id="-basic-syntax"></a>
## ⌨️ Sintaxe básica

### Sintaxe geral
```bash
john [options] [password_file]
```

### Opções comuns

| Opção | Descrição |
|--------|-------------|
| `--wordlist=` | Caminho da lista de palavras |
| `--format=` | Especificar o formato do hash |
| `--rules` | Ativar regras de transformação de palavras |
| `--show` | Mostrar palavras-passe recuperadas |
| `--single` | Modo Single |
| `--incremental` | Modo de força bruta |
| `--session=` | Dar um nome à sessão |
| `--restore` | Restaurar a sessão anterior |
| `--pot=` | Ficheiro pot personalizado |

### Início rápido
```bash
# Detetar automaticamente e recuperar palavras-passe
john hashes.txt

# Com lista de palavras
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Mostrar palavras-passe recuperadas
john --show hashes.txt
```

---

<a id="-cracking-modes"></a>
## 🎮 Modos de quebra de palavras-passe

### Modo Single

Utiliza os nomes de início de sessão e as informações GECOS para gerar possíveis palavras-passe.

```bash
# Modo Single (o mais rápido; testa variações do nome de utilizador)
john --single hashes.txt

# Com especificação do formato
john --single --format=sha512crypt hashes.txt
```

### Modo de lista de palavras

Utiliza palavras de um dicionário, com regras opcionais.

```bash
# Ataque básico com lista de palavras
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Lista de palavras com regras
john --wordlist=wordlist.txt --rules hashes.txt

# Conjunto de regras específico
john --wordlist=wordlist.txt --rules=Jumbo hashes.txt
```

### Modo incremental (força bruta)

Testa todas as combinações de caracteres possíveis.

```bash
# Modo incremental predefinido (todos os caracteres)
john --incremental hashes.txt

# Conjunto de caracteres específico
john --incremental=Digits hashes.txt      # Apenas algarismos
john --incremental=Lower hashes.txt       # Apenas minúsculas
john --incremental=Upper hashes.txt       # Apenas maiúsculas
john --incremental=Alpha hashes.txt       # Apenas letras
john --incremental=Alnum hashes.txt       # Alfanumérico
john --incremental=LowerNum hashes.txt    # Minúsculas e algarismos
```

### Modo de máscara

Força bruta baseada num padrão.

```bash
# Ataque com máscara (? = marcadores)
john --mask='?l?l?l?l?d?d' hashes.txt

# Marcadores:
# ?l = minúsculas    ?u = maiúsculas
# ?d = algarismos    ?s = especiais
# ?a = todos        ?h = hexadecimal em minúsculas
```

### Modo externo

Algoritmos personalizados de quebra de palavras-passe.

```bash
john --external=Filter_Alpha hashes.txt
```

---

<a id="-hash-extraction"></a>
## 🔍 Extração de hashes

### Palavras-passe Linux

```bash
# Combinar os ficheiros passwd e shadow
unshadow /etc/passwd /etc/shadow > unshadowed.txt

# Recuperar palavras-passe a partir do ficheiro combinado
john unshadowed.txt
```

### Hashes Windows

```bash
# A partir do ficheiro SAM (utilizar pwdump, secretsdump, etc.)
# Formato: user:id:LM_hash:NTLM_hash:::

# Utilizar hashdump do Metasploit
meterpreter> hashdump
Administrator:500:aad3b435b51404ee:31d6cfe0d16ae931:::

# Guardar num ficheiro e recuperar palavras-passe
john --format=nt hashes.txt
```

### Palavras-passe de ficheiros (ferramentas 2john)

```bash
# Ficheiros ZIP
zip2john protected.zip > zip_hash.txt

# Ficheiros RAR
rar2john protected.rar > rar_hash.txt

# Ficheiros PDF
pdf2john protected.pdf > pdf_hash.txt

# Documentos Office
office2john document.docx > office_hash.txt

# Chaves SSH
ssh2john id_rsa > ssh_hash.txt

# Ficheiros 7zip
7z2john archive.7z > 7z_hash.txt

# Bases de dados KeePass
keepass2john database.kdbx > keepass_hash.txt
```

---

<a id="-common-hash-types"></a>
## 🔢 Tipos de hash comuns

### Tabela de formatos de hash

> **Amostras do original:** valores com `...` são abreviados e não são hashes completos utilizáveis. Os exemplos hashdump também apresentam campos reduzidos; um hash NT completo tem 32 caracteres hexadecimais. Os dados originais foram preservados para referência, incluindo no exemplo bcrypt.

| Tipo de hash | Opção de formato | Exemplo |
|-----------|-------------|---------|
| MD5 | `--format=raw-md5` | `5d41402abc4b2a76b9719d911017c592` |
| SHA1 | `--format=raw-sha1` | `aaf4c61ddcc5e8a2...` |
| SHA256 | `--format=raw-sha256` | `2cf24dba5fb0a30e...` |
| SHA512 | `--format=raw-sha512` | `cf83e1357eefb8bd...` |
| NTLM | `--format=nt` | `31d6cfe0d16ae931b73c59d7e0c089c0` |
| LM | `--format=lm` | `aad3b435b51404ee` |
| MySQL | `--format=mysql-sha1` | `*2470C0C06DEE42...` |
| bcrypt | `--format=bcrypt` | `$2a$10$N9qo8u...` |
| Linux SHA512 | `--format=sha512crypt` | `$6$rounds=5000$...` |
| Linux SHA256 | `--format=sha256crypt` | `$5$rounds=5000$...` |
| Linux MD5 | `--format=md5crypt` | `$1$salt$hash` |

### Listar todos os formatos
```bash
# Listar todos os formatos suportados
john --list=formats

# Procurar um formato específico
john --list=formats | grep -i ntlm
john --list=formats | grep -i sha

# Contar formatos
john --list=formats | wc -l
```

### Identificar o tipo de hash
```bash
# Utilizar hash-identifier
hash-identifier

# Ou hashid
hashid -m '$6$salt$hash...'
```

---

<a id="-linux-password-cracking"></a>
## 🐧 Quebra de palavras-passe Linux

### Preparar os hashes
```bash
# Combinar passwd e shadow
sudo unshadow /etc/passwd /etc/shadow > linux_hashes.txt

# Ou extrair apenas o hash
sudo cat /etc/shadow | grep username
```

### Exemplos de formato de hash

> **Contexto de versões:** as expressões «o mais comum» e «o mais recente» nos comentários são qualificações temporais do original. Identifica o prefixo e o formato presentes no ficheiro concreto, em vez de assumir um algoritmo apenas por ser Linux.
```
# Formato de /etc/shadow:
# username:$id$salt$hash:...

# $1$ = MD5
# $5$ = SHA-256
# $6$ = SHA-512 (o mais comum em Linux moderno)
# $y$ = yescrypt (o mais recente)
```

### Recuperar palavras-passe a partir de hashes Linux
```bash
# Deteção automática (recomendada)
john linux_hashes.txt

# Especificar o formato
john --format=sha512crypt linux_hashes.txt

# Com lista de palavras
john --wordlist=/usr/share/wordlists/rockyou.txt linux_hashes.txt

# Mostrar os resultados
john --show linux_hashes.txt
```

---

<a id="-windows-password-cracking"></a>
## 🪟 Quebra de palavras-passe Windows

### Tipos de hash

| Tipo | Descrição | Formato |
|------|-------------|--------|
| **LM** | Formato antigo (fraco) | `--format=lm` |
| **NTLM** | Windows moderno | `--format=nt` |
| **NTLMv2** | Autenticação de rede | `--format=netntlmv2` |

### Recuperar palavras-passe a partir de hashes NTLM
```bash
# Formato do hash NTLM:
# user:rid:lm_hash:ntlm_hash:::

# Recuperar palavras-passe a partir de NTLM
john --format=nt windows_hashes.txt

# Com lista de palavras
john --format=nt --wordlist=rockyou.txt windows_hashes.txt

# Com regras
john --format=nt --wordlist=rockyou.txt --rules windows_hashes.txt
```

### Extrair hashes Windows
```bash
# Utilizar o Metasploit
meterpreter> hashdump

# Utilizar secretsdump (Impacket)
secretsdump.py -sam sam -system system LOCAL

# Utilizar pwdump
pwdump7.exe
```

---

<a id="-file-password-cracking"></a>
## 📄 Quebra de palavras-passe de ficheiros

### Ficheiros ZIP
```bash
# Extrair o hash
zip2john protected.zip > zip.hash

# Recuperar palavras-passe
john --wordlist=rockyou.txt zip.hash

# Mostrar a palavra-passe
john --show zip.hash
```

### Ficheiros RAR
```bash
rar2john protected.rar > rar.hash
john --wordlist=rockyou.txt rar.hash
```

### Ficheiros PDF
```bash
pdf2john protected.pdf > pdf.hash
john --wordlist=rockyou.txt pdf.hash
```

### Documentos Office
```bash
# Word, Excel, PowerPoint
office2john document.docx > office.hash
john --wordlist=rockyou.txt office.hash
```

### Chaves privadas SSH
```bash
ssh2john id_rsa > ssh.hash
john --wordlist=rockyou.txt ssh.hash
```

### Base de dados KeePass
```bash
keepass2john database.kdbx > keepass.hash
john --wordlist=rockyou.txt keepass.hash
```

---

<a id="-rules--mangling"></a>
## ⚙️ Regras e transformação de palavras

### Ativar regras
```bash
# Regras predefinidas
john --wordlist=wordlist.txt --rules hashes.txt

# Conjunto de regras específico
john --wordlist=wordlist.txt --rules=Single hashes.txt
john --wordlist=wordlist.txt --rules=Wordlist hashes.txt
john --wordlist=wordlist.txt --rules=Jumbo hashes.txt
john --wordlist=wordlist.txt --rules=KoreLogic hashes.txt
```

### Listar as regras disponíveis
```bash
john --list=rules
```

### Operações comuns das regras

> Em `sXY`, X e Y são parâmetros: o exemplo `password → pa$$word` corresponde à substituição de `s` por `$`, não aos caracteres literais X e Y.

| Regra | Descrição | Exemplo |
|------|-------------|---------|
| `:` | Não alterar | password → password |
| `l` | Converter tudo em minúsculas | PassWord → password |
| `u` | Converter tudo em maiúsculas | password → PASSWORD |
| `c` | Colocar a primeira letra em maiúscula | password → Password |
| `r` | Inverter | password → drowssap |
| `d` | Duplicar | pass → passpass |
| `$X` | Acrescentar o carácter X no fim | pass → passX |
| `^X` | Acrescentar o carácter X no início | pass → Xpass |
| `sXY` | Substituir X por Y | password → pa$$word |

### Regras personalizadas

> **Precisão das regras:** `$X` e `^X` acrescentam um carácter por operação. Assim, `$123` e `^2023` não significam, por si só, acrescentar cadeias completas. Os exemplos originais são mantidos; para acrescentar `123` no fim, a sequência de operações é `$1$2$3`. Ver a [sintaxe oficial de regras do John](https://www.openwall.com/john/doc/RULES.shtml).
```bash
# Adicionar a john.conf [List.Rules:Custom]
[List.Rules:Custom]
:
l
u
c
$1
$!
$123
^2023
```

---

<a id="-session-management"></a>
## 💾 Gestão de sessões

### Criar uma sessão com nome
```bash
john --wordlist=rockyou.txt --session=mycrack hashes.txt
```

### Restaurar a sessão
```bash
# Restaurar uma sessão interrompida
john --restore=mycrack

# Restaurar a sessão predefinida
john --restore
```

### Consultar o estado
```bash
# Premir qualquer tecla durante a recuperação para consultar o estado
# Ou consultar o ficheiro .rec
cat ~/.john/mycrack.rec
```

### Ficheiro pot
```bash
# Ficheiro pot predefinido
~/.john/john.pot

# Ficheiro pot personalizado
john --pot=custom.pot hashes.txt

# Mostrar todas as palavras-passe recuperadas
john --show --pot=custom.pot hashes.txt
```

---

<a id="-performance-tuning"></a>
## 🚀 Ajuste de desempenho

### Fork (vários processos)
```bash
# Utilizar vários núcleos de CPU
john --fork=4 --wordlist=rockyou.txt hashes.txt
```

### OpenCL (GPU)
```bash
# Listar os dispositivos OpenCL
john --list=opencl-devices

# Utilizar a GPU
john --format=sha512crypt-opencl --wordlist=rockyou.txt hashes.txt
```

### Limitar as palavras-passe candidatas
```bash
# Limitar o comprimento das palavras-passe
john --min-length=6 --max-length=12 --incremental hashes.txt
```

### Utilização de memória
```bash
# Reduzir a utilização de memória
john --mem-file-size=32 --wordlist=huge.txt hashes.txt
```

---

<a id="-real-world-examples"></a>
## 🎬 Exemplos práticos

### Exemplo 1: Ficheiro shadow do Linux
```bash
# Combinar os ficheiros
sudo unshadow /etc/passwd /etc/shadow > hashes.txt

# Recuperar palavras-passe com rockyou
john --wordlist=/usr/share/wordlists/rockyou.txt hashes.txt

# Mostrar os resultados
john --show hashes.txt
```

### Exemplo 2: NTLM do Windows
```bash
# A partir da saída de hashdump
echo "admin:500:aad3b435b51404ee:31d6cfe0d16ae931:::" > ntlm.txt

# Recuperar palavras-passe
john --format=nt --wordlist=rockyou.txt ntlm.txt
```

### Exemplo 3: Palavra-passe de ficheiro ZIP
```bash
zip2john secret.zip > zip.hash
john --wordlist=rockyou.txt zip.hash
john --show zip.hash
```

### Exemplo 4: Frase-passe de chave SSH
```bash
ssh2john ~/.ssh/id_rsa > ssh.hash
john --wordlist=rockyou.txt ssh.hash
```

### Exemplo 5: Hashes MD5
```bash
echo "5d41402abc4b2a76b9719d911017c592" > md5.txt
john --format=raw-md5 --wordlist=rockyou.txt md5.txt
```

### Exemplo 6: Hashes de aplicações web
```bash
# Hash bcrypt
echo '$2a$10$N9qo8uLOickgx2ZMRZoMy...' > bcrypt.txt
john --format=bcrypt --wordlist=rockyou.txt bcrypt.txt
```

### Exemplo 7: Com regras
```bash
john --wordlist=small_wordlist.txt --rules=Jumbo hashes.txt
```

### Exemplo 8: Ataque incremental
```bash
# Testar todas as combinações de 6-8 caracteres com minúsculas e algarismos
john --incremental=LowerNum --min-length=6 --max-length=8 hashes.txt
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos essenciais

| Tarefa | Comando |
|------|---------|
| Recuperação básica | `john hashes.txt` |
| Ataque com lista de palavras | `john --wordlist=rockyou.txt hashes.txt` |
| Com regras | `john --wordlist=words.txt --rules hashes.txt` |
| Mostrar as palavras-passe recuperadas | `john --show hashes.txt` |
| Formato específico | `john --format=raw-md5 hashes.txt` |
| Incremental | `john --incremental hashes.txt` |
| Restaurar a sessão | `john --restore=session` |
| Listar formatos | `john --list=formats` |

### Ferramentas 2john

| Ferramenta | Finalidade |
|------|---------|
| `unshadow` | Ficheiros passwd/shadow do Linux |
| `zip2john` | Ficheiros ZIP |
| `rar2john` | Ficheiros RAR |
| `pdf2john` | Ficheiros PDF |
| `office2john` | Documentos Office |
| `ssh2john` | Chaves SSH |
| `keepass2john` | Bases de dados KeePass |

### Formatos comuns

| Hash | Opção de formato |
|------|-------------|
| MD5 | `raw-md5` |
| SHA1 | `raw-sha1` |
| SHA256 | `raw-sha256` |
| NTLM | `nt` |
| Linux SHA512 | `sha512crypt` |
| bcrypt | `bcrypt` |
| MySQL | `mysql-sha1` |

---

<a id="-tips--best-practices"></a>
## 💡 Dicas e boas práticas

### Dicas de eficiência

1. **Testar primeiro o modo Single**
   ```bash
   john --single hashes.txt
   ```

2. **Utilizar listas de palavras adaptadas ao alvo**
   ```bash
   # Nome da empresa, cidade, etc.
   john --wordlist=targeted.txt hashes.txt
   ```

3. **Começar com listas de palavras pequenas**
   ```bash
   john --wordlist=top1000.txt hashes.txt
   # Depois testar listas maiores
   ```

4. **Combinar modos**
   ```bash
   john --single hashes.txt
   john --wordlist=rockyou.txt --rules hashes.txt
   john --incremental hashes.txt
   ```

### Dicas de desempenho

```bash
# Utilizar vários núcleos
john --fork=4 hashes.txt

# Aceleração por GPU (Jumbo)
john --format=sha512crypt-opencl hashes.txt
```

---

## ⚠️ Aviso legal

> **AVISO:** O John the Ripper é uma ferramenta poderosa de quebra de palavras-passe que deve ser utilizada apenas para **fins autorizados**.
> 
> - ✅ Auditar as tuas próprias palavras-passe
> - ✅ Testar com autorização explícita
> - ✅ Realizar avaliações de segurança com autorização
> - ❌ Nunca quebrar palavras-passe sem autorização
> - ❌ O acesso não autorizado é ilegal
> 
> **Quebrar palavras-passe sem autorização é ilegal e pode resultar em acusações criminais.**

---

<a id="-resources"></a>
## 📚 Recursos

### Recursos oficiais
- [Site oficial do John the Ripper](https://www.openwall.com/john/)
- [John Jumbo GitHub](https://github.com/openwall/john)
- [Wiki do John](https://openwall.info/wiki/john)

### Listas de palavras
- [SecLists](https://github.com/danielmiessler/SecLists)
- [RockYou](https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt)
- [CrackStation](https://crackstation.net/crackstation-wordlist-password-cracking-dictionary.htm)

### Guias de referência relacionados
- [Hydra](../../Hydra/translations/README.pt.md)
- [Metasploit](../../Metasploit/README.md)
- [Nmap](../../Nmap/README.md)

---

<p align="center">
  <b>🔨 Domina a quebra de palavras-passe!</b><br>
  <i>Quebra palavras-passe sempre de forma responsável</i>
</p>
