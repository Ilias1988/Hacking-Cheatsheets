# 🕷️ Nikto — guia de referência completo

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
    _   _ _ _    _        
   | \ | (_) | _| |_ ___  
   |  \| | | |/ / __/ _ \ 
   | |\  | |   <| || (_) |
   |_| \_|_|_|\_\\__\___/ 
                          
   Analisador de servidores Web — desde 2001
```

<p align="center">
  <img src="https://img.shields.io/badge/Nikto-Web_Scanner-blue?style=for-the-badge" alt="Nikto">
  <img src="https://img.shields.io/badge/Vulnerability-Scanner-red?style=for-the-badge" alt="Vulnerability">
  <img src="https://img.shields.io/badge/CGI-Testing-green?style=for-the-badge" alt="CGI">
  <img src="https://img.shields.io/badge/SSL-Analysis-purple?style=for-the-badge" alt="SSL">
</p>

<p align="center">
  <b>🌐 Analisador de servidores Web de código aberto para testes de segurança</b>
</p>

> **Termos usados:** reconhecimento (*reconnaissance*) é a recolha de informações sobre o alvo; enumeração é a identificação organizada de sistemas, serviços ou recursos. Os comandos, opções e valores dos exemplos foram mantidos; a sua disponibilidade depende da versão e da plataforma.

> **Vocabulário:** virtual host é o sítio selecionado pelo nome enviado ao servidor; IDS é um sistema de deteção de intrusões. Opções de evasão ou pausas não garantem ausência de deteção. As estatísticas e versões da base de dados são exemplos históricos.

> **Precisão do original:** `-Save` guarda respostas positivas numa pasta, não um estado de análise para retomar. `-Resume` e `-cookies` não constam da lista oficial consultada. O formato de `-id` é `id:pass` ou `id:pass:realm`; colocar aspas em `admin:password` não o transforma em Base64. A tabela resumida de tuning sobrepõe categorias; usa a tabela detalhada e confirma a versão. Ver [opções oficiais do Nikto](https://github.com/sullo/nikto/wiki/Annotated-Option-List) e [gravação/reprodução de pedidos](https://github.com/sullo/nikto/wiki/Saving-Requests-Responses-%26-Replaying).

> **Atualizações:** o exemplo `-update` e os downloads em `UPDATES/2.1.5` são legados. Desde Nikto 2.1.6, a documentação indica atualização pela instalação/repositório correspondente, em vez de misturar bases de dados antigas. Ver [atualização oficial](https://github.com/sullo/nikto/wiki/Updating).

---

## 📋 Índice

- [Introdução](#-introduction)
- [Instalação](#-installation)
- [Sintaxe básica](#-basic-syntax)
- [Definição dos alvos](#-target-specification)
- [Opções de portas e SSL/TLS](#-port--ssl-options)
- [Seleção dos testes de análise](#-scan-tuning)
- [Autenticação](#-authentication)
- [Proxy e evasão](#-proxy--evasion)
- [Saída e relatórios](#-output--reporting)
- [Atualizações da base de dados](#-database-updates)
- [Exemplos práticos](#-real-world-examples)
- [Referência rápida](#-quick-reference)
- [Conselhos e boas práticas](#-tips--best-practices)
- [Recursos](#-resources)

---

<a id="-introduction"></a>
## 🎯 Introdução

O **Nikto** é um analisador de servidores Web de código aberto que realiza testes abrangentes a vários aspetos dos servidores, incluindo:

### O que o Nikto verifica

| Categoria | Descrição |
|----------|-------------|
| **Ficheiros perigosos** | Ficheiros potencialmente perigosos (cópias de segurança, etc.) |
| **Versões desatualizadas** | Software de servidor desatualizado |
| **Específico da versão** | Vulnerabilidades específicas da versão |
| **Configuração do servidor** | Configurações incorretas |
| **Scripts CGI** | Scripts CGI potencialmente perigosos |
| **SSL/TLS** | Problemas de certificados SSL/TLS |
| **Cabeçalhos** | Cabeçalhos HTTP em falta/inseguros |
| **Métodos** | Métodos HTTP perigosos |

### Estatísticas da base de dados

```
✅ 6,700+ ficheiros/CGIs potencialmente perigosos
✅ 1,250+ versões de servidores desatualizadas
✅ 270+ problemas específicos de versões
✅ 1,000+ problemas de configuração de servidores
```

### Funcionalidades

| Funcionalidade | Descrição |
|---------|-------------|
| **Abrangente** | Testes a milhares de vulnerabilidades |
| **Baseado em plugins** | Arquitetura extensível |
| **Vários formatos** | Saída HTML, XML, CSV e JSON |
| **Suporte SSL/TLS** | Testes HTTPS completos |
| **Evasão de IDS** | Técnicas de evasão incorporadas |
| **Autenticação** | Suporte Basic, Digest e NTLM |

---

<a id="-installation"></a>
## 📥 Instalação

### Kali Linux (pré-instalado)
```bash
# Atualizar o Nikto
sudo apt update && sudo apt install nikto

# Atualizar a base de dados
nikto -update
```

### Ubuntu/Debian
```bash
sudo apt install nikto
```

### A partir do GitHub
```bash
git clone https://github.com/sullo/nikto
cd nikto/program
perl nikto.pl -h
```

### macOS (Homebrew)
```bash
brew install nikto
```

### Verificar a instalação
```bash
nikto -Version
nikto -h          # Ajuda
```

---

<a id="-basic-syntax"></a>
## ⌨️ Sintaxe básica

### Sintaxe geral
```bash
nikto -h <host> [options]
```

### Primeiros passos
```bash
# Análise básica
nikto -h http://target.com

# Analisar uma porta específica
nikto -h target.com -p 80

# Analisar HTTPS
nikto -h https://target.com

# Analisar um IP
nikto -h 192.168.1.1
```

---

<a id="-target-specification"></a>
## 🎯 Definição dos alvos

### Alvo individual
```bash
# Pelo nome do sistema
nikto -h target.com

# Por IP
nikto -h 192.168.1.1

# Com protocolo
nikto -h http://target.com
nikto -h https://target.com
```

### Vários alvos
```bash
# A partir de ficheiro
nikto -h targets.txt

# targets.txt:
# http://target1.com
# http://target2.com:8080
# https://target3.com
```

### Alvo com caminho
```bash
# Analisar um caminho específico
nikto -h http://target.com/app

# Analisar uma pasta específica
nikto -h http://target.com/admin/
```

---

<a id="-port--ssl-options"></a>
## 🔌 Opções de portas e SSL/TLS

### Opções de portas
```bash
# Uma porta
nikto -h target.com -p 80

# Várias portas
nikto -h target.com -p 80,443,8080

# Intervalo de portas
nikto -h target.com -p 80-90

# Portas Web comuns
nikto -h target.com -p 80,443,8000,8080,8443
```

### Opções SSL/TLS

| Opção | Descrição |
|--------|-------------|
| `-ssl` | Forçar o modo SSL/TLS |
| `-nossl` | Desativar SSL/TLS |
| `-maxtime` | Tempo máximo de análise |

```bash
# Forçar SSL/TLS
nikto -h target.com -ssl

# Desativar SSL/TLS (apenas HTTP)
nikto -h target.com -nossl

# SSL/TLS numa porta não habitual
nikto -h target.com -p 8443 -ssl
```

### Virtual hosts
```bash
# Indicar o virtual host
nikto -h 192.168.1.1 -vhost target.com

# Útil quando:
# - Alvo atrás de um balanceador de carga
# - Vários sítios no mesmo IP
```

---

<a id="-scan-tuning"></a>
## 🎚️ Seleção dos testes de análise

### Opções de seleção de testes (-Tuning)

| Código | Descrição |
|------|-------------|
| `0` | Carregamento de ficheiros |
| `1` | Ficheiro de interesse / observado nos registos |
| `2` | Configuração incorreta / ficheiro predefinido |
| `3` | Divulgação de informações |
| `4` | Injeção (XSS/script/HTML) |
| `5` | Obtenção remota de ficheiros — dentro da raiz Web |
| `6` | Negação de serviço |
| `7` | Obtenção remota de ficheiros — em todo o servidor |
| `8` | Execução de comandos / shell remota |
| `9` | Injeção SQL |
| `a` | Contorno da autenticação |
| `b` | Identificação de software |
| `c` | Inclusão remota de código-fonte |
| `d` | WebService |
| `e` | Consola de administração |
| `x` | Inverter a seleção de testes |

### Utilizar a seleção de testes
```bash
# Testar apenas injeção SQL
nikto -h target.com -Tuning 9

# Testar XSS e injeção SQL
nikto -h target.com -Tuning 49

# Testar vários tipos de problemas
nikto -h target.com -Tuning 1234

# Excluir determinados testes (prefixo x)
nikto -h target.com -Tuning x6    # Excluir testes de DoS

# Todos exceto DoS
nikto -h target.com -Tuning x6
```

### Plugins
```bash
# Listar plugins disponíveis
nikto -list-plugins

# Executar um plugin específico
nikto -h target.com -Plugins apacheusers

# Executar vários plugins
nikto -h target.com -Plugins "apacheusers;robots"

# Excluir plugins
nikto -h target.com -Plugins "@@ALL;-robots"
```

### Plugins comuns

| Plugin | Descrição |
|--------|-------------|
| `apacheusers` | Enumeração de utilizadores Apache |
| `cgi` | Testes de scripts CGI |
| `robots` | Análise de robots.txt |
| `headers` | Análise de cabeçalhos HTTP |
| `httpoptions` | Testes de métodos HTTP |
| `outdated` | Verificação de software desatualizado |
| `ssl` | Análise SSL/TLS |
| `dictionary` | Ataque de dicionário |

---

<a id="-authentication"></a>
## 🔐 Autenticação

### Autenticação Basic
```bash
# Nome de utilizador e palavra-passe
nikto -h target.com -id admin:password

# Formato Base64
nikto -h target.com -id "admin:password"
```

### Autenticação Digest
```bash
# Autenticação Digest (detetada automaticamente)
nikto -h target.com -id admin:password
```

### Autenticação NTLM
```bash
# Autenticação NTLM
nikto -h target.com -id admin:password:domain
```

### Autenticação baseada em cookies
```bash
# Enviar cookies
nikto -h target.com -cookies "session=abc123; token=xyz789"

# A partir de ficheiro
nikto -h target.com -cookies cookies.txt
```

---

<a id="-proxy--evasion"></a>
## 🛡️ Proxy e evasão

### Suporte de proxy
```bash
# Proxy HTTP
nikto -h target.com -useproxy http://127.0.0.1:8080

# Proxy autenticado
nikto -h target.com -useproxy http://user:pass@proxy:8080

# Através do Burp Suite
nikto -h target.com -useproxy http://127.0.0.1:8080
```

### Evasão de IDS (-evasion)

| Código | Técnica |
|------|-----------|
| `1` | Codificação aleatória de URI (não UTF8) |
| `2` | Autorreferência de pasta (/./) |
| `3` | Terminação prematura do URL |
| `4` | Antepor uma cadeia aleatória longa |
| `5` | Parâmetro falso |
| `6` | TAB como separador no pedido |
| `7` | Alterar maiúsculas/minúsculas do URL |
| `8` | Utilizar o separador de pastas Windows (\) |
| `A` | Utilizar um retorno de carro (0x0d) |
| `B` | Utilizar o valor binário 0x0b |

```bash
# Uma técnica de evasão
nikto -h target.com -evasion 1

# Várias técnicas de evasão
nikto -h target.com -evasion 1234

# Todas as técnicas de evasão
nikto -h target.com -evasion 12345678AB
```

### User-Agent
```bash
# User-Agent personalizado
nikto -h target.com -useragent "Mozilla/5.0 (Windows NT 10.0)"

# Googlebot
nikto -h target.com -useragent "Googlebot/2.1"
```

### Temporização dos pedidos
```bash
# Pausa entre pedidos (segundos)
nikto -h target.com -Pause 2

# Tempo máximo de análise (segundos)
nikto -h target.com -maxtime 3600

# Tempo limite por pedido
nikto -h target.com -timeout 10
```

---

<a id="-output--reporting"></a>
## 📄 Saída e relatórios

### Formatos de saída

| Opção | Formato |
|--------|--------|
| `-o file` | Guardar a saída num ficheiro |
| `-Format htm` | Formato HTML |
| `-Format xml` | Formato XML |
| `-Format csv` | Formato CSV |
| `-Format txt` | Formato de texto |
| `-Format json` | Formato JSON |
| `-Format nbe` | Formato NBE |
| `-Format sql` | Formato SQL |

### Guardar resultados
```bash
# Saída de texto
nikto -h target.com -o report.txt

# Relatório HTML
nikto -h target.com -o report.html -Format htm

# Relatório XML
nikto -h target.com -o report.xml -Format xml

# Relatório JSON
nikto -h target.com -o report.json -Format json

# Relatório CSV
nikto -h target.com -o report.csv -Format csv
```

### Opções de detalhe da saída
```bash
# Apresentar opções
nikto -h target.com -Display V    # Detalhado
nikto -h target.com -Display D    # Depuração
nikto -h target.com -Display E    # Erros HTTP
nikto -h target.com -Display P    # Progresso
nikto -h target.com -Display S    # Ocultar o IP na saída
nikto -h target.com -Display 1    # Mostrar redirecionamentos
nikto -h target.com -Display 2    # Mostrar cookies
nikto -h target.com -Display 3    # Mostrar respostas 200/OK
nikto -h target.com -Display 4    # Mostrar URLs que exigem autenticação

# Várias opções de apresentação
nikto -h target.com -Display VEP
```

### Guardar progresso
```bash
# Guardar o estado da análise (permite retomar mais tarde)
nikto -h target.com -Save scan_state.txt

# Retomar a análise
nikto -Resume scan_state.txt
```

---

<a id="-database-updates"></a>
## 🔄 Atualizações da base de dados

### Atualizar a base de dados do Nikto
```bash
# Atualizar todas as bases de dados
nikto -update

# Atualização manual (se -update falhar)
cd /var/lib/nikto
wget https://cirt.net/nikto/UPDATES/2.1.5/db_outdated.csv
wget https://cirt.net/nikto/UPDATES/2.1.5/db_tests.csv
```

### Localização da base de dados
```bash
# Localizações predefinidas
/usr/share/nikto/databases/
/var/lib/nikto/

# Ficheiros da base de dados
db_tests         # Base de dados principal de testes
db_outdated      # Versões de software desatualizadas
db_server_msgs   # Mensagens do servidor
db_variables     # Variáveis
db_404_strings   # Cadeias de caracteres de páginas 404
```

---

<a id="-real-world-examples"></a>
## 🎬 Exemplos práticos

### Exemplo 1: análise básica de servidor Web
```bash
nikto -h http://target.com
```

### Exemplo 2: análise HTTPS abrangente
```bash
nikto -h https://target.com -ssl -p 443 -o report.html -Format htm
```

### Exemplo 3: análise de várias portas
```bash
nikto -h target.com -p 80,443,8080,8443 -o results.txt
```

### Exemplo 4: análise autenticada
```bash
nikto -h http://target.com/admin \
    -id admin:password \
    -o auth_scan.html \
    -Format htm
```

### Exemplo 5: análise discreta (evasão de IDS)
```bash
nikto -h target.com \
    -evasion 1234 \
    -Pause 2 \
    -useragent "Mozilla/5.0 (Windows NT 10.0)" \
    -o stealth_scan.txt
```

### Exemplo 6: através do proxy Burp
```bash
nikto -h https://target.com \
    -useproxy http://127.0.0.1:8080 \
    -o proxy_scan.html \
    -Format htm
```

### Exemplo 7: foco na injeção SQL
```bash
nikto -h target.com \
    -Tuning 9 \
    -o sqli_scan.txt
```

### Exemplo 8: auditoria completa
```bash
nikto -h https://target.com \
    -ssl \
    -p 443 \
    -Tuning x6 \
    -Display VEP \
    -o full_audit.html \
    -Format htm
```

### Exemplo 9: testes de virtual host
```bash
nikto -h 192.168.1.10 \
    -vhost app.target.com \
    -p 80,443 \
    -o vhost_scan.txt
```

### Exemplo 10: análise de alvos a partir de ficheiro
```bash
nikto -h targets.txt \
    -o batch_scan.html \
    -Format htm
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos essenciais

| Tarefa | Comando |
|------|---------|
| Análise básica | `nikto -h target.com` |
| Análise HTTPS | `nikto -h target.com -ssl` |
| Várias portas | `nikto -h target.com -p 80,443,8080` |
| Com autenticação | `nikto -h target.com -id user:pass` |
| Relatório HTML | `nikto -h target.com -o report.html -Format htm` |
| Evasão | `nikto -h target.com -evasion 123` |
| Através de proxy | `nikto -h target.com -useproxy http://127.0.0.1:8080` |
| Atualizar a base de dados | `nikto -update` |

### Opções comuns

| Opção | Descrição |
|--------|-------------|
| `-h` | Sistema alvo |
| `-p` | Portas a analisar |
| `-ssl` | Forçar o modo SSL/TLS |
| `-id` | Credenciais de autenticação |
| `-o` | Ficheiro de saída |
| `-Format` | Formato de saída |
| `-Tuning` | Seleção dos testes de análise |
| `-evasion` | Evasão de IDS |
| `-useproxy` | Servidor proxy |
| `-vhost` | Virtual host |
| `-Pause` | Intervalo entre testes |
| `-timeout` | Tempo limite do pedido |

### Referência rápida da seleção de testes

| Código | Categoria |
|------|----------|
| 1-4 | Informação/configuração incorreta |
| 4-5 | Injeção/obtenção de ficheiros |
| 6 | DoS (evitar em produção) |
| 7-8 | Acesso remoto |
| 9 | Injeção SQL |
| a | Contorno da autenticação |
| b | Identificação de software |
| x | Excluir |

---

<a id="-tips--best-practices"></a>
## 💡 Conselhos e boas práticas

### Conselhos de desempenho

1. **Começar por uma análise básica**
   ```bash
   nikto -h target.com
   ```

2. **Excluir testes de DoS**
   ```bash
   nikto -h target.com -Tuning x6
   ```

3. **Utilizar tempos limite adequados**
   ```bash
   nikto -h target.com -timeout 15 -Pause 1
   ```

### Conselhos para melhorar a precisão

1. **Atualizar a base de dados regularmente**
   ```bash
   nikto -update
   ```

2. **Utilizar o modo detalhado**
   ```bash
   nikto -h target.com -Display VE
   ```

3. **Combinar com outras ferramentas**
   ```bash
   # Primeiro Nmap, depois Nikto
   nmap -sV -p 80,443 target.com
   nikto -h target.com -p 80,443
   ```

### Problemas comuns

| Problema | Solução |
|-------|----------|
| Análise lenta | Reduzir `-Pause`, aumentar `-timeout` |
| Erros SSL/TLS | Utilizar `-ssl` explicitamente |
| Problemas de autenticação | Verificar o formato de `-id` |
| Resultados em falta | Atualizar a base de dados e verificar os plugins |

---

## ⚠️ Aviso legal

> **AVISO:** O Nikto é um analisador de servidores Web que deve ser utilizado apenas em **testes autorizados**.
> 
> - ✅ Analisar servidores que te pertençam
> - ✅ Analisar com autorização escrita explícita
> - ✅ Utilizar em testes de intrusão autorizados
> - ❌ Nunca analisar sem autorização
> - ❌ A análise não autorizada é ilegal
> 
> **O Nikto pode gerar tráfego significativo e desencadear alertas de segurança.**

---

<a id="-resources"></a>
## 📚 Recursos

### Recursos oficiais
- [Nikto GitHub](https://github.com/sullo/nikto)
- [Nikto Documentation](https://cirt.net/Nikto2)
- [CIRT.net](https://cirt.net/)

### Recursos de aprendizagem
- [OWASP Testing Guia](https://owasp.org/www-project-web-security-testing-guide/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Guias de referência relacionados
- [Nmap](../../Nmap/translations/README.pt.md)
- [Gobuster](../../Gobuster/translations/README.pt.md)
- [Burp Suite](../../Burp-Suite/translations/README.pt.md)

---

<p align="center">
  <b>🕷️ Domina a análise de servidores Web!</b><br>
  <i>Encontra vulnerabilidades antes dos atacantes</i>
</p>
