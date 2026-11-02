# ⚡ OWASP ZAP — Guia de referência completo

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ____  _    _  ___   _____ _____    _____   ___  _____  
  / __ \| |  | |/ _ \ / ____|  __ \  |__  /  / _ \|  __ \ 
 | |  | | |  | | | | | (___ | |__) |   / /  / /_\ \ |__) |
 | |  | | |/\| | |_| |\___ \|  ___/   / /  |  _  |  ___/ 
 | |__| \  /\  / | | |____) | |      / /__ | | | | |     
  \____/ \/  \/|_| |_|_____/|_|     /_____|_| |_|_|     
                                                          
    Zed Attack Proxy — Ferramenta gratuita de testes de segurança
```

<p align="center">
  <img src="https://img.shields.io/badge/OWASP-ZAP-blue?style=for-the-badge" alt="OWASP ZAP">
  <img src="https://img.shields.io/badge/Web-Security-red?style=for-the-badge" alt="Web Security">
  <img src="https://img.shields.io/badge/Open-Source-green?style=for-the-badge" alt="Open Source">
  <img src="https://img.shields.io/badge/Free-Tool-purple?style=for-the-badge" alt="Free">
</p>

<p align="center">
  <b>🔒 Um analisador gratuito de segurança web amplamente utilizado</b>
</p>

> **Termos e notas de versão:** O título histórico «OWASP ZAP» foi mantido para corresponder ao original; o projeto é atualmente [ZAP by Checkmarx](https://www.zaproxy.org/docs/zap-ownership/), independente da OWASP. Os nomes dos controlos e menus permanecem em inglês; nas versões atuais, as opções de proxy estão em [Options → Network → Local Servers/Proxies](https://www.zaproxy.org/docs/desktop/start/proxies/). Os preços e números de complementos pertencem ao original e podem mudar. A [análise baseline](https://www.zaproxy.org/docs/docker/baseline-scan/) executa o spider, que envia pedidos, e depois análise passiva; não equivale a ausência de interação. O cliente Python apresentado inicia tarefas assíncronas e deve aguardar a sua conclusão antes de recolher resultados completos. Para relatórios atuais, consultar a [API de geração de relatórios](https://www.zaproxy.org/docs/desktop/addons/report-generation/api/); o endpoint HTML antigo depende da versão. Nos exemplos Docker, guardar relatórios no host requer uma pasta partilhada adequada. Uma análise passiva não substitui uma avaliação completa da configuração TLS.

---

<a id="-table-of-contents"></a>
## 📋 Índice

- [Introdução](#-introduction)
- [Instalação](#-installation)
- [Visão geral da interface](#-interface-overview)
- [Configuração do proxy](#-proxy-configuration)
- [Testes manuais](#-manual-testing)
- [Spider e crawler (recolha de conteúdo)](#-spider--crawler)
- [Análise ativa](#-active-scanning)
- [Análise passiva](#-passive-scanning)
- [Contextos e sessões](#-contexts--sessions)
- [Fuzzer](#-fuzzer)
- [API e automatização](#-api--automation)
- [Linha de comandos](#-command-line)
- [Complementos](#-add-ons)
- [Exemplos práticos](#-real-world-examples)
- [Referência rápida](#-quick-reference)
- [Conselhos e boas práticas](#-tips--best-practices)
- [Recursos](#-resources)

---

<a id="-introduction"></a>
## 🎯 Introdução

**OWASP ZAP** (Zed Attack Proxy) é uma ferramenta gratuita de testes de intrusão, de código aberto, que o original apresenta como mantida pela OWASP (ver nota de versão). Foi concebida para encontrar vulnerabilidades de segurança em aplicações web.

### Porquê utilizar o ZAP?

| Funcionalidade | Descrição |
|---------|-------------|
| **Gratuito e de código aberto** | Sem custos de licença, desenvolvido pela comunidade |
| **Fácil de utilizar** | Acessível a principiantes e com funcionalidades poderosas |
| **Análise automatizada** | Spider e análise ativa/passiva |
| **Testes manuais** | Proxy de interceção e fuzzer |
| **Suporte de API** | API REST completa para automatização |
| **Multiplataforma** | Windows, Linux, macOS, Docker |
| **Extensível** | Marketplace com mais de 100 complementos |

### Comparação entre ZAP e Burp Suite

| Funcionalidade | ZAP | Burp Suite |
|---------|-----|------------|
| **Preço** | Gratuito | A partir de $449/ano (valor do original) |
| **Código aberto** | ✅ Sim | ❌ Não |
| **Análise automatizada** | ✅ Completa | Apenas na edição Pro |
| **Acesso à API** | ✅ Gratuito | Apenas na edição Pro |
| **Desenvolvimento ativo** | ✅ Sim | ✅ Sim |
| **Community** | OWASP | PortSwigger |

---

<a id="-installation"></a>
## 📥 Instalação

### Windows
```bash
# Descarregar a partir do site oficial
https://www.zaproxy.org/download/

# Ou utilizar Chocolatey
choco install owasp-zap
```

### Linux (Kali/Ubuntu)
```bash
# Kali (pré-instalado)
zaproxy

# Ubuntu/Debian
sudo apt install zaproxy

# Snap
sudo snap install zaproxy --classic
```

### macOS
```bash
# Homebrew
brew install --cask owasp-zap

# Descarregar o DMG a partir do site oficial
```

### Docker
```bash
# Obter a imagem mais recente
docker pull ghcr.io/zaproxy/zaproxy:stable

# Executar o ZAP em modo daemon (serviço em segundo plano)
docker run -u zap -p 8080:8080 -i ghcr.io/zaproxy/zaproxy:stable zap.sh -daemon -host 0.0.0.0 -port 8080

# Executar com interface gráfica (Linux)
docker run -u zap -p 8080:8080 -p 8090:8090 -v /tmp:/zap/wrk/:rw \
    --rm -it ghcr.io/zaproxy/zaproxy:stable zap-webswing.sh
```

### Verificar a instalação
```bash
# Iniciar o ZAP
zaproxy
zap.sh       # Linux
zap.bat      # Windows
```

---

<a id="-interface-overview"></a>
## 🖥️ Visão geral da interface

### Painéis principais

| Painel | Descrição |
|-------|-------------|
| **Sites Tree** | Vista hierárquica dos sites visitados |
| **History** | Todos os pedidos/respostas |
| **Alerts** | Vulnerabilidades encontradas |
| **Active Scan** | Progresso da análise atual |
| **Spider** | URL recolhidos |
| **Search** | Pesquisar em todos os dados |
| **Request/Response** | Ver detalhes |

### Ícones da barra de ferramentas

| Ícone | Função |
|------|----------|
| 🌐 | Análise através de Quick Start |
| 🔍 | Spider |
| ⚡ | Active Scan |
| 🛑 | Break/Intercept |
| 📊 | Relatórios |
| ⚙️ | Opções |

### Modos

| Modo | Descrição |
|------|-------------|
| **Safe** | Sem operações perigosas |
| **Protected** | Analisar apenas alvos dentro do âmbito |
| **Standard** | Análise completa (predefinido) |
| **ATTACK** | Análise agressiva |

---

<a id="-proxy-configuration"></a>
## 🔌 Configuração do proxy

### Configuração do navegador

**Proxy predefinido do ZAP:**
```
Host: localhost (127.0.0.1)
Port: 8080
```

### Configuração do Firefox
```
1. Settings → Network Settings → Manual proxy
2. HTTP Proxy: 127.0.0.1  Port: 8080
3. Selecionar "Also use this proxy for HTTPS"
4. Ou utilizar a extensão FoxyProxy
```

### Importar o certificado do ZAP
```
1. No navegador, visitar: http://zap
2. Clicar em "Download ZAP Root CA Certificate"
3. Importar para o navegador:
   Firefox: Settings → Privacy → Certificates → Import
   Chrome: Settings → Security → Manage certificates
```

### Configurar as opções do proxy ZAP
```
Tools → Options → Local Proxies

Definições:
- Address: localhost
- Port: 8080
- Behind NAT: ativar se necessário
- Remove unsupported encodings: ativar
```

### Proxy a montante (cadeia de proxies)
```
Tools → Options → Network → Connection

Use proxy chain (utilizar uma cadeia de proxies):
- Address: corporate-proxy.com
- Port: 8080
- Autenticação, se for necessária
```

---

<a id="-manual-testing"></a>
## 🔧 Testes manuais

### Intercetar pedidos (Break)

```
1. Clicar no botão verde "Break" (🛑) da barra de ferramentas
2. Percorrer a aplicação
3. Modificar o pedido intercetado
4. Clicar em "Submit and Continue" ou "Drop"

Opções de Break:
- Intercetar todos os pedidos
- Intercetar todas as respostas
- Intercetar um padrão de URL específico
```

### Editor de pedidos (Request Editor)

```
Clicar com o botão direito no pedido → Open/Resend with Request Editor

Funcionalidades:
- Modificar qualquer parte do pedido
- Adicionar/remover cabeçalhos
- Alterar o método HTTP
- Enviar e ver a resposta
```

### Pedido manual
```
Tools → Manual Request Editor

Ou: Ctrl+M

Criar pedidos personalizados de raiz
```

### Encode/Decode/Hash
```
Tools → Encode/Decode/Hash

Suporta:
- Codificação/descodificação URL
- Codificação/descodificação Base64
- Codificação/descodificação HTML
- Codificação/descodificação hexadecimal
- Cálculo de hashes MD5, SHA1 e SHA256
```

### Funcionalidade de pesquisa
```
Separador Search → introduzir o padrão

Pesquisar em:
- URL
- Pedidos
- Respostas
- Cabeçalhos
- Tudo
```

---

<a id="-spider--crawler"></a>
## 🕷️ Spider e crawler (recolha de conteúdo)

### Spider tradicional

```
Clicar com o botão direito no alvo → Spider → Spider...

Ou: Ctrl+Alt+S

Opções:
- Profundidade máxima da recolha
- Número máximo de elementos descendentes
- Duração máxima
- Processar parâmetros
```

### Opções do spider
```
Tools → Options → Spider

Definições:
- Max Depth: 5-10 (predefinido: 5)
- Max Children: 10
- Max Duration: 0 (sem limite)
- Thread Count: 5
- Handle forms: Yes (processar formulários)
- Post forms: Yes (submeter formulários)
```

### AJAX Spider

```
Para aplicações com utilização intensiva de JavaScript:

Clicar com o botão direito no alvo → AJAX Spider → AJAX Spider...

Requer um navegador:
- Firefox (recomendado)
- Chrome
- HtmlUnit (sem interface gráfica)
```

### Opções do AJAX Spider
```
Tools → Options → AJAX Spider

Definições:
- Browser: Firefox
- Max Crawl Depth: 10
- Max Crawl States: 0 (sem limite)
- Max Duration: 60 minutos
- Clicar nos elementos predefinidos
- Clicar em elementos com: onclick, etc.
```

---

<a id="-active-scanning"></a>
## ⚡ Análise ativa

### Iniciar uma análise ativa

```
Clicar com o botão direito no alvo → Active Scan...

Ou: Ctrl+Alt+A

Configurar:
- Starting Point
- Context (contexto opcional)
- User (utilizador para autenticação)
- Policy
- Technology
```

### Políticas de análise

```
Analyze → Scan Policy Manager

Criar/modificar políticas:
- Que testes executar
- Threshold (limiar de suspeita)
- Strength (intensidade dos testes)
```

### Categorias das políticas

| Categoria | Testes |
|----------|-------|
| **Injection (injeção)** | SQL, comandos do sistema operativo e LDAP |
| **Client Browser (navegador cliente)** | XSS, DOM XSS |
| **Server Security (segurança do servidor)** | Inclusão remota de ficheiros |
| **Information (informação)** | Travessia de diretórios e divulgação de informação |
| **Miscellaneous (diversos)** | Redirecionamentos controlados pelo utilizador |

### Intensidade da análise

| Nível | Descrição |
|-------|-------------|
| **Low** | Poucos pedidos, rápido |
| **Medium** | Equilibrado |
| **High** | Muitos pedidos, aprofundado |
| **Insane** | Quantidade máxima de testes (lento) |

### Seleção de tecnologias
```
Clicar com o botão direito no alvo → Technology → Select...

Filtrar as análises por tecnologia:
- PHP, ASP, JSP
- MySQL, PostgreSQL, Oracle
- Apache, IIS, Nginx
- Linux, Windows
```

---

<a id="-passive-scanning"></a>
## 👁️ Análise passiva

### O que é a análise passiva?

Analisa o tráfego sem enviar pedidos adicionais.

```
Automática — analisa continuamente o tráfego que passa pelo proxy

Deteta:
- Cabeçalhos de segurança em falta
- Divulgação de informação
- Problemas de cookies
- SSL/TLS fraco
- Divulgação de endereços IP privados
```

### Regras de análise passiva

```
Analyze → Scan Policy Manager → Passive Scan

As regras incluem:
- Application Error Disclosure
- Cookie Without Secure Flag
- Cross-Domain JavaScript
- Information Disclosure
- Private IP Disclosure
- X-Frame-Options Header Missing
```

### Configurar a análise passiva
```
Tools → Options → Passive Scan

Definições:
- Scan only in scope: recomendado
- Max alerts per rule: 10
- Tags: ativar
```

---

<a id="-contexts--sessions"></a>
## 🎯 Contextos e sessões

### O que são contextos?

Agrupamentos lógicos de URL relacionados para organizar os testes.

### Criar um contexto
```
Clicar com o botão direito em Sites → Include in Context → New Context

Ou: File → New Context
```

### Definições dos contextos

| Definição | Finalidade |
|---------|---------|
| **Include in Context** | URL a incluir |
| **Exclude from Context** | URL a excluir |
| **Technology** | Tecnologia do servidor |
| **Authentication** | Método de início de sessão |
| **Gestão de sessões** | Gestão de cookies/tokens |
| **Users** | Contas de utilizador para testes |

### Tipos de autenticação

| Tipo | Caso de utilização |
|------|----------|
| **Form-based** | Formulários de início de sessão |
| **HTTP Basic** | Autenticação Basic |
| **JSON-based** | Autenticação de API |
| **Script-based** | Autenticação personalizada |

### Autenticação baseada em formulários
```
1. Registar o pedido de início de sessão no ZAP
2. Botão direito → Include in Context
3. Context → Authentication → Form-based
4. Definir Login URL
5. Definir Login Request POST Data
6. Configurar os parâmetros de nome de utilizador/palavra-passe
7. Definir os indicadores de sessão iniciada/terminada
```

### Gestão de sessões
```
Context → Session Management

Tipos:
- Cookie-based (baseada em cookies, a mais comum)
- HTTP Auth
- Script-based
```

### Users
```
Context → Users → Add

Adicionar utilizadores de teste:
- Name: admin
- Credentials: admin/password123
```

---

<a id="-fuzzer"></a>
## 🎲 Fuzzer

### Iniciar o fuzzer
```
1. Selecionar o pedido em History
2. Botão direito → Attack → Fuzz...
3. Selecionar a posição onde aplicar o fuzzing
4. Adicionar payloads
5. Iniciar
```

### Adicionar uma posição de fuzzing
```
1. Na janela Fuzzer, selecionar o texto a submeter a fuzzing
2. Clicar em "Add..."
3. Selecionar o tipo de payload
```

### Tipos de payload

| Tipo | Descrição |
|------|-------------|
| **Strings** | Lista personalizada de cadeias de caracteres |
| **File** | Carregar de um ficheiro |
| **File Fuzzers** | Listas de palavras incluídas |
| **Numbers** | Sequências de números |
| **Regex** | Gerados a partir de uma expressão regular |
| **Script** | Script personalizado |

### Fuzzers incluídos
```
Add → File Fuzzers

Categorias:
- jbrofuzz (XSS, SQL, etc.)
- dirbuster (diretórios)
- fuzzdb (abrangente)
```

### Opções do fuzzer
```
Opções:
- Threads simultâneas
- Atraso entre pedidos
- Follow redirects (seguir redirecionamentos)
- Mostrar todos os resultados ou aplicar filtros
```

### Exemplo de fuzzer: fuzzing de parâmetros
```
1. Capturar: GET /page?id=1
2. Botão direito → Fuzz
3. Selecionar "1" 
4. Adicionar payloads de injeção SQL
5. Iniciar o fuzzer
6. Analisar os resultados pelo comprimento/código da resposta
```

---

<a id="-api--automation"></a>
## 🤖 API e automatização

### Ativar a API
```
Tools → Options → API

Definições:
- Enabled: Yes (ativada)
- API Key: (gerada automaticamente ou personalizada)
- Permitted addresses: localhost
```

### Acesso à API
```bash
# Raiz da API
http://localhost:8080/JSON/

# Com chave de API
http://localhost:8080/JSON/core/view/alerts/?apikey=YOUR_API_KEY

# Obter a versão
curl "http://localhost:8080/JSON/core/view/version/?apikey=API_KEY"
```

### Endpoints comuns da API

| Endpoint | Finalidade |
|----------|---------|
| `/core/view/alerts/` | Obter todos os alertas |
| `/core/view/hosts/` | Obter todos os hosts |
| `/spider/action/scan/` | Iniciar o spider |
| `/ascan/action/scan/` | Iniciar uma análise ativa |
| `/core/view/urls/` | Obter todos os URL |
| `/core/action/shutdown/` | Encerrar o ZAP |

### Exemplos de API

```bash
# Percorrer um URL com o spider
curl "http://localhost:8080/JSON/spider/action/scan/?url=http://target.com&apikey=API_KEY"

# Verificar o estado do spider
curl "http://localhost:8080/JSON/spider/view/status/?apikey=API_KEY"

# Iniciar uma análise ativa
curl "http://localhost:8080/JSON/ascan/action/scan/?url=http://target.com&apikey=API_KEY"

# Obter os alertas
curl "http://localhost:8080/JSON/core/view/alerts/?apikey=API_KEY"

# Gerar um relatório HTML
curl "http://localhost:8080/OTHER/core/other/htmlreport/?apikey=API_KEY" > report.html
```

### Cliente da API em Python
```python
from zapv2 import ZAPv2

# Ligar ao ZAP
zap = ZAPv2(apikey='your-api-key', proxies={'http': 'http://127.0.0.1:8080'})

# Spider
zap.spider.scan(url='http://target.com')

# Active Scan
zap.ascan.scan(url='http://target.com')

# Obter os alertas
alerts = zap.core.alerts()
for alert in alerts:
    print(f"{alert['risk']}: {alert['name']}")
```

---

<a id="-command-line"></a>
## ⌨️ Linha de comandos

### Opções básicas da linha de comandos
```bash
# Iniciar o ZAP em modo daemon
zap.sh -daemon

# Iniciar numa porta específica
zap.sh -daemon -port 8090

# Análise rápida
zap.sh -quickurl http://target.com -quickout report.html

# Análise baseline (Docker)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py -t http://target.com
```

### Framework de automatização
```bash
# Executar uma análise automatizada
zap.sh -cmd -autorun automation.yaml
```

### Exemplo de YAML para automatização
```yaml
env:
  contexts:
    - name: "Default Context"
      urls:
        - "http://target.com"

jobs:
  - type: spider
    parameters:
      url: "http://target.com"
      maxDuration: 5
      
  - type: passiveScan-wait
    parameters:
      maxDuration: 5
      
  - type: activeScan
    parameters:
      scanOnlyInScope: true
      
  - type: report
    parameters:
      template: "traditional-html"
      reportFile: "report.html"
```

### Análises com Docker

```bash
# Análise baseline (apenas análise passiva; ver nota)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
    -t https://target.com \
    -r report.html

# Análise completa (ativa)
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-full-scan.py \
    -t https://target.com \
    -r report.html

# Análise de API
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-api-scan.py \
    -t https://target.com/api/openapi.json \
    -f openapi \
    -r report.html
```

---

<a id="-add-ons"></a>
## 🧩 Complementos

### Instalar complementos
```
Help → Check for Updates → Marketplace

Ou: Tools → Add-ons → Marketplace
```

### Complementos essenciais

| Complemento | Descrição |
|--------|-------------|
| **Active Scan Rules** | Regras de análise adicionais |
| **Regras de análise passiva** | Regras passivas adicionais |
| **FuzzDB** | Payloads de fuzzing |
| **Retire.js** | Detetar bibliotecas JavaScript vulneráveis |
| **AJAX Spider** | Crawler compatível com JavaScript |
| **Selenium** | Automatização do navegador |
| **Wappalyzer** | Identificação de tecnologias |
| **Access Control** | Testes de controlo de acesso |
| **Directory List** | Listas de palavras de diretórios |

### Gestão de complementos
```
# Listar os complementos instalados
Tools → Add-ons → Installed

# Atualizar tudo
Help → Check for Updates → Update All
```

---

<a id="-real-world-examples"></a>
## 🎬 Exemplos práticos

### Exemplo 1: análise web rápida
```
1. Introduzir o URL no separador Quick Start
2. Clicar em "Attack"
3. O ZAP percorre e analisa a aplicação automaticamente
4. Rever os alertas após a conclusão
```

### Exemplo 2: testes manuais e automatizados
```
1. Configurar o proxy do navegador para utilizar o ZAP
2. Percorrer a aplicação manualmente (explorar todas as funcionalidades)
3. Clicar com o botão direito no alvo em Sites → Spider
4. Aguardar a conclusão do spider
5. Clicar com o botão direito no alvo → Active Scan
6. Rever os alertas
7. Gerar o relatório
```

### Exemplo 3: análise autenticada
```
1. Criar um Context para o alvo
2. Configurar Form-based Authentication
3. Adicionar as credenciais do utilizador de teste
4. Configurar o modo Forced User
5. Executar o spider como utilizador autenticado
6. Active Scan
```

### Exemplo 4: testes de API
```
1. Importar a definição da API (OpenAPI/Swagger)
2. File → Import → Import OpenAPI definition
3. Percorrer a API com o spider
4. Active Scan
```

### Exemplo 5: integração CI/CD
```bash
# Jenkins Pipeline
docker run -t ghcr.io/zaproxy/zaproxy:stable zap-baseline.py \
    -t https://staging.myapp.com \
    -r zap_report.html \
    -x zap_report.xml
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Ações essenciais

| Tarefa | Método |
|------|--------|
| Iniciar o proxy | O ZAP inicia automaticamente na porta 8080 |
| Spider | Botão direito → Spider |
| Active Scan | Botão direito → Active Scan |
| Intercetar | Clicar no botão Break (🛑) |
| Aplicar fuzzing | Clicar com o botão direito no pedido → Fuzz |
| Generate Report (gerar relatório) | Report → Generate Report |

### Atalhos de teclado

| Atalho | Ação |
|----------|--------|
| `Ctrl+Alt+S` | Janela Spider |
| `Ctrl+Alt+A` | Janela Active Scan |
| `Ctrl+M` | Pedido manual |
| `Ctrl+B` | Ativar/desativar Break |
| `Ctrl+F` | Pesquisar |
| `Ctrl+R` | Resend Request (reenviar pedido) |

### Modos de análise

| Modo | Quando utilizar |
|------|----------|
| **Safe** | Quando não se pretende efetuar alterações |
| **Protected** | Testar apenas um âmbito específico |
| **Standard** | Testes normais |
| **ATTACK** | Testes agressivos (com autorização!) |

---

<a id="-tips--best-practices"></a>
## 💡 Conselhos e boas práticas

### Conselhos de desempenho

1. **Limitar o âmbito**
   ```
   Definir o contexto e analisar apenas os itens dentro do âmbito
   ```

2. **Utilizar AJAX Spider em aplicações de página única (SPA)**
   ```
   Aplicações modernas podem necessitar do AJAX Spider além do spider tradicional
   ```

3. **Configurar as tecnologias**
   ```
   Selecionar apenas as tecnologias relevantes para reduzir o tempo da análise
   ```

### Conselhos para melhorar a precisão

1. **Autenticar primeiro**
   ```
   Configurar a autenticação para obter uma cobertura mais aprofundada
   ```

2. **Explorar manualmente antes de automatizar**
   ```
   Percorrer primeiro a aplicação manualmente para descobrir todas as funcionalidades
   ```

3. **Rever todos os alertas**
   ```
   Procurar falsos positivos e confirmar os verdadeiros positivos
   ```

### Fluxo de trabalho

```
1. Configurar o proxy
2. Exploração manual (com autenticação)
3. Spider (tradicional + AJAX)
4. Revisão da análise passiva
5. Análise ativa
6. Testes manuais (fuzzing, casos limite)
7. Gerar o relatório
```

---

<a id="-legal-disclaimer"></a>
## ⚠️ Aviso legal

> **AVISO:** O OWASP ZAP é uma ferramenta de testes de segurança que deve ser utilizada apenas em **testes autorizados**.
> 
> - ✅ Testar aplicações que lhe pertencem
> - ✅ Testar com autorização escrita explícita
> - ✅ Utilizar em ambientes de teste/pré-produção
> - ❌ Nunca analisar ambientes de produção sem autorização
> - ❌ Nunca analisar sistemas que não lhe pertencem
> 
> **Os testes de segurança não autorizados são ilegais e podem dar origem a processos criminais.**

---

<a id="-resources"></a>
## 📚 Recursos

### Recursos oficiais
- [Site do OWASP ZAP](https://www.zaproxy.org/)
- [Documentação do ZAP](https://www.zaproxy.org/docs/)
- [ZAP GitHub](https://github.com/zaproxy/zaproxy)
- [Documentação da API do ZAP](https://www.zaproxy.org/docs/api/)

### Recursos de aprendizagem
- [Guia de testes OWASP](https://owasp.org/www-project-web-security-testing-guide/)
- [ZAP in Ten](https://www.zaproxy.org/zap-in-ten/)
- [Vídeos de aprofundamento do ZAP](https://www.zaproxy.org/docs/videos/)

### Guias de referência relacionados
- [Burp Suite](../../Burp-Suite/translations/README.pt.md)
- [Nikto](../../Nikto/README.md)
- [SQLMap](../../SQLMap/translations/README.pt.md)

---

<p align="center">
  <b>⚡ Domine os testes de segurança de aplicações web!</b><br>
  <i>Gratuito, poderoso e desenvolvido pela comunidade</i>
</p>
