# 🔶 Burp Suite — Guia de referência completo

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) | [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
    ____                      _____       _ __     
   / __ )__  ___________     / ___/__  __(_) /____
  / __  / / / / ___/ __ \    \__ \/ / / / / __/ _ \
 / /_/ / /_/ / /  / /_/ /   ___/ / /_/ / / /_/  __/
/_____/\__,_/_/  / .___/   /____/\__,_/_/\__/\___/ 
                /_/                                
    Plataforma de testes de segurança de aplicações web
```

<p align="center">
  <img src="https://img.shields.io/badge/Burp-Suite-orange?style=for-the-badge" alt="Burp Suite">
  <img src="https://img.shields.io/badge/Web-Security-red?style=for-the-badge" alt="Web Security">
  <img src="https://img.shields.io/badge/PortSwigger-blue?style=for-the-badge" alt="PortSwigger">
  <img src="https://img.shields.io/badge/Penetration-Testing-purple?style=for-the-badge" alt="Penetration Testing">
</p>

<p align="center">
  <b>🌐 Uma plataforma de referência no setor para testes de segurança de aplicações web</b>
</p>

> **Termos e notas de versão:** Proxy interceta o tráfego; scope define o âmbito autorizado; crawler percorre a aplicação; fuzzing testa variações de entrada. Os nomes dos controlos e menus permanecem em inglês para facilitar a sua localização. As interfaces antigas `Proxy → Options`, `Extender` e `Spider` diferem das versões atuais: consultar as [definições do Burp](https://portswigger.net/burp/documentation/desktop/settings). As edições, preços e requisitos da tabela pertencem ao original e devem ser confirmados na [PortSwigger](https://portswigger.net/burp). No comando Java, a opção de memória da JVM deve preceder `-jar`; a ordem original foi preservada. Os [atalhos são configuráveis](https://portswigger.net/burp/documentation/desktop/settings/ui/hotkeys); o original atribui `Ctrl+Shift+T` a duas ações. O [Sequencer](https://portswigger.net/burp/documentation/desktop/tools/sequencer/results/tests) aplica testes estatísticos a uma amostra: um resultado favorável não demonstra, por si só, segurança criptográfica. JWT usa Base64URL em segmentos; descodificar não valida a assinatura. Códigos HTTP e diferenças de comprimento são indícios, não confirmação isolada de vulnerabilidade.

---

<a id="-table-of-contents"></a>
## 📋 Índice

- [Introdução](#-introduction)
- [Instalação e configuração inicial](#-installation--setup)
- [Configuração do proxy](#-proxy-configuration)
- [Interceção de tráfego](#-intercepting-traffic)
- [Target e âmbito (Scope)](#-target--scope)
- [Spider/crawler (recolha de conteúdo)](#-spidercrawler)
- [Scanner](#-scanner)
- [Repeater](#-repeater)
- [Intruder](#-intruder)
- [Decoder](#-decoder)
- [Comparer](#-comparer)
- [Sequencer](#-sequencer)
- [Extensões](#-extensions)
- [Atalhos de teclado](#-keyboard-shortcuts)
- [Fluxo de trabalho de testes](#-testing-workflow)
- [Referência rápida](#-quick-reference)
- [Conselhos e boas práticas](#-tips--best-practices)
- [Recursos](#-resources)

---

<a id="-introduction"></a>
## 🎯 Introdução

**Burp Suite** é uma plataforma integrada para testar a segurança de aplicações web. Desenvolvida pela PortSwigger, é uma referência do setor nos testes de intrusão web.

### Edições

| Edição | Funcionalidades | Preço |
|---------|----------|-------|
| **Community** | Proxy básico e ferramentas manuais | Gratuito |
| **Professional** | Scanner, Intruder (sem limites) e Collaborator | $449/ano |
| **Enterprise** | Integração CI/CD e funcionalidades de equipa | Contactar a equipa comercial |

### Ferramentas principais

| Ferramenta | Descrição |
|------|-------------|
| **Proxy** | Intercetar e modificar tráfego HTTP/HTTPS |
| **Spider** | Percorrer aplicações web automaticamente |
| **Scanner** | Encontrar vulnerabilidades automaticamente |
| **Intruder** | Ataques automatizados personalizados |
| **Repeater** | Manipulação manual de pedidos |
| **Decoder** | Codificar/descodificar dados |
| **Comparer** | Comparar respostas |
| **Sequencer** | Analisar a aleatoriedade dos tokens |

---

<a id="-installation--setup"></a>
## 📥 Instalação e configuração inicial

### Descarregar

```bash
# Descarregar a partir do site oficial
https://portswigger.net/burp/releases

# Disponível para:
# - Windows (instalador/aplicação autónoma)
# - macOS (DMG)
# - Linux (script/JAR)
```

### Requisitos do sistema

| Requisito | Mínimo | Recomendado |
|-------------|---------|-------------|
| RAM | 4 GB | 8+ GB |
| Disco | 500 MB | 2+ GB |
| Java | JRE 8+ | JRE incluído |
| Ecrã | 1280x800 | 1920x1080 |

### Configuração da primeira execução

1. **Selecionar o tipo de projeto**
   - Temporary project (projeto temporário para testes)
   - New project on disk (guardar o trabalho em disco)
   - Open existing project (abrir um projeto existente)

2. **Configuração**
   - Use Burp defaults (utilizar as predefinições do Burp, recomendado para principiantes)
   - Load from configuration file (carregar um ficheiro de configuração)

3. **Memória atribuída**
   ```bash
   # Aumentar a memória para projetos grandes
   java -jar -Xmx4g burpsuite_pro.jar
   ```

---

<a id="-proxy-configuration"></a>
## 🔌 Configuração do proxy

### Configuração do navegador

#### Configuração manual do proxy

| Navegador | Localização das definições de proxy |
|---------|------------------------|
| Firefox | Settings → Network → Manual proxy |
| Chrome | Utiliza o proxy do sistema ou uma extensão |
| Safari | System Preferences → Network → Proxies |

**Proxy predefinido do Burp:**
```
Host: 127.0.0.1
Port: 8080
```

#### Configuração do Firefox
```
1. Abrir as Preferences do Firefox
2. Pesquisar "proxy"
3. Clicar em "Settings..."
4. Selecionar "Manual proxy configuration"
5. HTTP Proxy: 127.0.0.1  Port: 8080
6. Selecionar "Also use this proxy for HTTPS"
7. Clicar em OK
```

#### Extensão FoxyProxy (recomendada)
```
1. Instalar a extensão FoxyProxy
2. Adicionar um novo proxy:
   - Title: Burp Suite
   - Proxy Type: HTTP
   - Proxy IP: 127.0.0.1
   - Port: 8080
3. Ativar durante os testes
```

### Instalação do certificado SSL/TLS

```bash
# Passo 1: com o proxy ativado, visitar:
http://burp

# Passo 2: clicar em "CA Certificate" para descarregar

# Passo 3: importar o certificado:
# Firefox: Settings → Certificates → Import → Trust for websites
# Chrome: Settings → Security → Manage certificates → Import
# Sistema: adicionar aos certificados de raiz fidedignos
```

### Listeners do proxy

```
Proxy → Options → Proxy Listeners

Predefinido: 127.0.0.1:8080

Adicionar listeners personalizados:
- Portas diferentes para testes diferentes
- Escutar em todas as interfaces (0.0.0.0) para testes em dispositivos móveis
- Redirecionar para hosts diferentes
```

### Proxy invisível (para clientes sem suporte de proxy)

```
1. Proxy → Options → Proxy Listeners
2. Editar o listener → Request handling
3. Ativar "Support invisible proxying"
4. Configurar o ficheiro hosts ou o DNS
```

---

<a id="-intercepting-traffic"></a>
## 🎣 Interceção de tráfego

### Controlos de interceção

| Botão | Função |
|--------|----------|
| **Intercept is on/off** | Ativar/desativar a interceção |
| **Forward** | Enviar o pedido ao servidor |
| **Drop** | Descartar o pedido |
| **Ação** | Menu de opções adicionais |

### Opções de interceção

```
Proxy → Options → Intercept Client Requests

Exemplos de regras:
- Intercetar apenas itens dentro do âmbito
- Intercetar apenas tipos de ficheiros específicos
- Excluir imagens e ficheiros estáticos
```

### Modificação de pedidos

```http
# Pedido original
GET /page.php?id=1 HTTP/1.1
Host: target.com

# Pedido modificado (durante a interceção)
GET /page.php?id=1' OR '1'='1 HTTP/1.1
Host: target.com
```

### Match and Replace (correspondência e substituição)

```
Proxy → Options → Match and Replace

Regras comuns:
- Remover cabeçalhos de segurança
- Modificar o User-Agent
- Adicionar cabeçalhos personalizados
- Substituir parâmetros do pedido
```

| Tipo | Match | Replace | Finalidade |
|------|-------|---------|---------|
| Request header (cabeçalho do pedido) | `User-Agent: .*` | `User-Agent: Custom` | Alterar o User-Agent |
| Request body (corpo do pedido) | `password=test` | `password=admin` | Testar credenciais |
| Response header (cabeçalho da resposta) | `X-Frame-Options.*` | ` ` | Remover o cabeçalho |
| Response body (corpo da resposta) | `disabled` | `enabled` | Ativar botões |

### Modificação de respostas

```
Proxy → Options → Intercept Server Responses

Ativar para:
- Remover cabeçalhos de segurança
- Modificar JavaScript
- Alterar o conteúdo da resposta
- Testar controlos do lado do cliente
```

---

<a id="-target--scope"></a>
## 🎯 Target e âmbito (Scope)

### Adicionar ao âmbito

```
Método 1: clicar com o botão direito em Site map → Add to scope
Método 2: Target → Scope → Add (manualmente)
Método 3: a partir do histórico do Proxy → Add to scope
```

### Configuração do âmbito

```
Target → Scope

Include in scope (incluir no âmbito):
- Protocol: Any/HTTP/HTTPS
- Host/IP: target.com
- Port: Any/Specific
- File: padrão de expressão regular

Exemplos de padrões:
.*\.target\.com$          # Todos os subdomínios
^https://target\.com/app  # Caminho específico
```

### Definições avançadas de âmbito

```
Target → Scope → Advanced scope control

Include (incluir):
  Protocol: HTTPS
  Host: target.com
  Port: 443
  File: ^/api/.*

Exclude (excluir):
  Host: .*\.google\.com
  File: .*\.(jpg|png|gif|css|js)$
```

### Mapa do site (Site map)

```
Target → Site map

Funcionalidades:
- Ver todo o conteúdo descoberto
- Filtrar pelo âmbito
- Pesquisar/filtrar conteúdo
- Destacar itens relevantes
- Comparar mapas de sites
```

---

<a id="-spidercrawler"></a>
## 🕷️ Spider/crawler (recolha de conteúdo)

### Iniciar o spider

```
1. Clicar com o botão direito no alvo em Site map
2. Selecionar "Spider this host" ou "Spider this branch"
3. Ou: Target → Site map → botão direito → Scan → Crawl
```

### Definições do spider

```
Spider → Options (interface antiga) OU
Dashboard → New scan → Crawl

Definições do crawler:
- Profundidade máxima das ligações
- Tempo máximo de recolha
- Submissão de formulários
- Gestão do início de sessão
```

### Configuração da recolha

| Definição | Descrição | Recomendado |
|---------|-------------|-------------|
| **Link depth** | Profundidade até à qual seguir ligações | 5-10 |
| **Max requests** | Limite total de pedidos | 1000-5000 |
| **Duplicate removal** | Ignorar páginas semelhantes | Ativado |
| **Passive scanning** | Analisar durante a recolha | Ativado |

### Submissão de formulários

```
Spider → Options → Application Login

Configurar o início de sessão para:
- Um conjunto de credenciais
- Lista de credenciais
- Deteção de sessão
```

---

<a id="-scanner"></a>
## 🔍 Scanner

### Tipos de análise

| Tipo | Descrição | Caso de utilização |
|------|-------------|----------|
| **Passive** | Analisar o tráfego do proxy | Sempre ativo |
| **Active** | Enviar payloads para encontrar vulnerabilidades | Com autorização |
| **Full** | Análise abrangente | Quando há tempo disponível |
| **Custom** | Apenas testes específicos | Testes direcionados |

### Iniciar uma análise

```
Método 1: botão direito → Scan
Método 2: Dashboard → New scan
Método 3: enviar para Scanner a partir de outras ferramentas
```

### Configuração da análise

```
Dashboard → New scan → Scan configuration

Configurações incluídas:
- Audit checks - all except JavaScript analysis
- Audit checks - critical issues only
- Audit checks - light, active
- Audit checks - medium, active
- Audit checks - full, active
```

### Categorias de vulnerabilidades

```
O Scanner verifica:

Injeção:
- Injeção SQL
- Injeção de comandos do sistema operativo
- Injeção LDAP
- Injeção XML/XPath

Cross-Site Scripting (XSS):
- XSS refletido
- XSS armazenado
- XSS baseado no DOM

Autenticação:
- Falhas de autenticação
- Problemas de gestão de sessões
- Palavras-passe fracas

Outros:
- CSRF
- Travessia de diretórios
- Inclusão de ficheiros
- Divulgação de informação
- Problemas do lado do servidor
```

### Resultados da análise

```
Dashboard → Issue activity
Target → Site map → Issues

Para cada problema:
- Gravidade (High/Medium/Low/Info)
- Grau de confiança (Certain/Firm/Tentative)
- Descrição
- Correção
- Prova (pedido/resposta)
```

### Perfis de análise personalizados

```
1. Dashboard → New scan
2. Selecionar "Scan configuration"
3. Clicar em "New" ou modificar uma configuração existente
4. Guardar para reutilizar

Personalizar:
- Que vulnerabilidades testar
- Intensidade dos payloads
- Ritmo dos pedidos
- Gestão da autenticação
```

---

<a id="-repeater"></a>
## 🔁 Repeater

### Finalidade

Testes manuais e manipulação de pedidos individuais.

### Enviar para Repeater

```
1. Clicar com o botão direito em qualquer pedido
2. Selecionar "Send to Repeater"
3. Atalho: Ctrl+R
```

### Utilizar o Repeater

```
1. Modificar o pedido no painel esquerdo
2. Clicar em "Send" (ou Ctrl+Space)
3. Ver a resposta no painel direito
4. Comparar respostas
```

### Funcionalidades do Repeater

| Funcionalidade | Descrição |
|---------|-------------|
| **Multiple tabs** | Testar vários pedidos |
| **Request history** | Navegar com os botões < > |
| **Follow redirects** | Ativar/desativar o seguimento automático |
| **Process cookies** | Atualizar cookies automaticamente |
| **Content-Length** | Atualizar o cabeçalho automaticamente |

### Exemplos de testes

```http
# Testar injeção SQL
GET /user.php?id=1' HTTP/1.1

# Testar XSS
GET /search?q=<script>alert(1)</script> HTTP/1.1

# Testar o contorno da autenticação
GET /admin HTTP/1.1
Cookie: role=admin

# Testar IDOR
GET /api/user/1 HTTP/1.1  # O seu utilizador
GET /api/user/2 HTTP/1.1  # Outro utilizador
```

---

<a id="-intruder"></a>
## 🔫 Intruder

### Tipos de ataque

| Tipo | Posições | Payloads | Caso de utilização |
|------|-----------|----------|----------|
| **Sniper** | 1+ | 1 conjunto, um valor de cada vez | Teste de um único parâmetro |
| **Battering Ram** | 1+ | 1 conjunto, o mesmo valor em todas as posições | O mesmo valor em todas as posições |
| **Pitchfork** | 1+ | Vários conjuntos, em paralelo | Pares de nome de utilizador e palavra-passe |
| **Cluster Bomb** | 1+ | Vários conjuntos, todas as combinações | Força bruta completa de credenciais |

### Enviar para Intruder

```
1. Clicar com o botão direito no pedido
2. Selecionar "Send to Intruder"
3. Atalho: Ctrl+I
```

### Definir as posições

```
Intruder → Positions

Marcar os pontos de injeção com símbolos §:

GET /login.php?user=§admin§&pass=§test§ HTTP/1.1

Botões:
- Add § — Marcar a seleção
- Clear § — Remover marcadores
- Auto § — Detetar automaticamente
```

### Tipos de payload

| Tipo | Descrição |
|------|-------------|
| **Simple list** | Lista de palavras personalizada |
| **Runtime file** | Carregar de um ficheiro |
| **Numbers** | Números sequenciais/aleatórios |
| **Dates** | Sequências de datas |
| **Brute forcer** | Combinações de caracteres |
| **Null payloads** | Payloads vazios (testes de ritmo) |
| **Username generator** | Padrões comuns de nomes de utilizador |

### Processamento de payloads

```
Intruder → Payloads → Payload Processing

Adicionar regras:
- Adicionar prefixo/sufixo
- Codificar como URL
- Codificar em Base64
- Calcular hash (MD5, SHA)
- Correspondência/substituição
```

### Grep - Match

```
Intruder → Options → Grep - Match

Adicionar cadeias de caracteres a assinalar nas respostas:
- "Login successful"
- "Invalid password"
- "error"
- Expressão regular personalizada

Ajuda a identificar ataques bem-sucedidos.
```

### Exemplos de ataques

#### Força bruta do início de sessão
```
POST /login HTTP/1.1
Content-Type: application/x-www-form-urlencoded

username=§admin§&password=§password§

Tipo de ataque: Cluster bomb
Payload 1: lista de nomes de utilizador
Payload 2: lista de palavras-passe
```

#### Fuzzing de parâmetros
```
GET /api/user/§1§ HTTP/1.1

Tipo de ataque: Sniper
Payload: Numbers, de 1 a 1000
```

#### Descoberta de conteúdo
```
GET /§admin§ HTTP/1.1

Tipo de ataque: Sniper
Payload: lista de palavras de diretórios
Filtro: código de estado != 404
```

### Análise dos resultados

```
Após o ataque:
- Ordenar por comprimento (encontrar respostas diferentes)
- Ordenar por código de estado
- Filtrar por correspondência de grep
- Exportar resultados
```

---

<a id="-decoder"></a>
## 🔓 Decoder

### Codificação/descodificação

```
Separador Decoder

Operações:
- Codificação/descodificação URL
- Codificação/descodificação HTML
- Codificação/descodificação Base64
- Codificação/descodificação hexadecimal
- Compressão/descompressão Gzip
- ASCII Hex
- Binário
```

### Smart Decode

```
Decoder → Smart decode

Deteta e descodifica automaticamente:
- Base64
- Codificação URL
- Entidades HTML
- Codificações combinadas
```

### Utilizações comuns

```
# Descodificar um token JWT
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
→ Descodificar como Base64

# Descodificar um parâmetro URL
%3Cscript%3Ealert(1)%3C%2Fscript%3E
→ Descodificar como URL
→ <script>alert(1)</script>

# Codificar o payload
<script>alert(1)</script>
→ Codificar em Base64
→ Utilizar no ataque
```

---

<a id="-comparer"></a>
## 📊 Comparer

### Finalidade

Comparar dois conjuntos de dados (pedidos, respostas ou outros dados).

### Como utilizar

```
1. Clicar com o botão direito no item 1 → Send to Comparer
2. Clicar com o botão direito no item 2 → Send to Comparer
3. Abrir o separador Comparer
4. Selecionar os itens e clicar em "Words" ou "Bytes"
```

### Modos de comparação

| Modo | Descrição |
|------|-------------|
| **Words** | Comparar palavra a palavra |
| **Bytes** | Comparar byte a byte (exato) |

### Casos de utilização

```
- Comparar respostas de início de sessão bem-sucedido e falhado
- Encontrar diferenças nos tokens de sessão
- Comparar antes e depois das alterações
- Identificar campos de formulário ocultos
```

---

<a id="-sequencer"></a>
## 🔢 Sequencer

### Finalidade

Analisar a qualidade da aleatoriedade de tokens (ID de sessão, tokens CSRF).

### Como utilizar

```
1. Capturar um pedido que devolva um token
2. Botão direito → Send to Sequencer
3. Configurar a localização do token
4. Iniciar a captura em tempo real
5. Analisar os resultados
```

### Métricas de análise

| Métrica | Valor adequado | Descrição |
|--------|------------|-------------|
| **Overall quality** | >100 bits | Pontuação de aleatoriedade |
| **Character-level** | Entropia elevada | Aleatoriedade por carácter |
| **Bit-level** | Entropia elevada | Aleatoriedade por bit |

### Interpretação

```
Resultados:
- Excellent: token com aleatoriedade criptográfica (interpretação do original; ver nota)
- Good: aceitável para a maioria das utilizações  
- Poor: potencialmente previsível
- Very poor: pode ser possível adivinhar o token
```

---

<a id="-extensions"></a>
## 🧩 Extensões

### BApp Store

```
Extender → BApp Store

Extensões populares:
```

### Extensões úteis

| Extensão | Descrição |
|-----------|-------------|
| **Autorize** | Testes de autorização |
| **Logger++** | Registo de eventos melhorado |
| **Retire.js** | Detetar bibliotecas JavaScript vulneráveis |
| **Active Scan++** | Verificações adicionais de análise |
| **AuthMatrix** | Testes de matrizes de autorização |
| **Turbo Intruder** | Alternativa rápida ao Intruder |
| **Param Miner** | Descoberta de parâmetros ocultos |
| **JSON Web Tokens** | Análise/manipulação de JWT |
| **Hackvertor** | Codificação/descodificação avançada |
| **CO2** | Conjunto de ferramentas úteis |

### Instalar extensões

```
Método 1: BApp Store
1. Extender → BApp Store
2. Procurar a extensão
3. Clicar em Install

Método 2: instalação manual
1. Descarregar o ficheiro .jar ou .py
2. Extender → Extensions → Add
3. Selecionar o tipo de ficheiro e o caminho
```

### Definições das extensões

```
Extender → Options

- Ambiente Python (para extensões Python)
- Definições do ambiente Java
- Opções de registo de eventos
```

---

<a id="-keyboard-shortcuts"></a>
## ⌨️ Atalhos de teclado

### Atalhos globais

| Atalho | Ação |
|----------|--------|
| `Ctrl+I` | Send to Intruder (enviar para Intruder) |
| `Ctrl+R` | Send to Repeater (enviar para Repeater) |
| `Ctrl+S` | Search |
| `Ctrl+F` | Forward (encaminhar no Proxy) |
| `Ctrl+D` | Drop (descartar no Proxy) |
| `Ctrl+T` | Ativar/desativar a interceção |
| `Ctrl+Shift+T` | Ativar/desativar a interceção do Proxy |

### Navegação entre separadores

| Atalho | Ação |
|----------|--------|
| `Ctrl+Shift+D` | Dashboard |
| `Ctrl+Shift+T` | Target |
| `Ctrl+Shift+P` | Proxy |
| `Ctrl+Shift+I` | Intruder |
| `Ctrl+Shift+R` | Repeater |

### No Repeater

| Atalho | Ação |
|----------|--------|
| `Ctrl+Space` | Enviar o pedido |
| `Ctrl+G` | Go to (histórico de pedidos) |
| `Ctrl+Plus` | Novo separador |

### No editor

| Atalho | Ação |
|----------|--------|
| `Ctrl+U` | Codificar a seleção como URL |
| `Ctrl+Shift+U` | Descodificar a seleção como URL |
| `Ctrl+B` | Codificar em Base64 |
| `Ctrl+Shift+B` | Descodificar Base64 |

---

<a id="-testing-workflow"></a>
## 🎯 Fluxo de trabalho de testes

### Fluxo habitual de testes de aplicações web

```
Passo 1: configuração inicial
├── Configurar o proxy do navegador
├── Instalar o certificado SSL
└── Definir o âmbito

Passo 2: reconhecimento
├── Percorrer a aplicação manualmente
├── Executar o spider/crawler
└── Rever o Site map

Passo 3: análise
├── Verificar o histórico do Proxy
├── Rever os resultados da análise passiva
└── Identificar a superfície de ataque

Passo 4: testes
├── Testar com Repeater (manualmente)
├── Testar com Intruder (automaticamente)
├── Executar uma análise ativa
└── Utilizar extensões

Passo 5: relatório
├── Rever todos os resultados
├── Gerar o relatório
└── Documentar as provas
```

### Lista de verificação dos testes

```
Autenticação:
□ Força bruta do início de sessão
□ Política de palavras-passe
□ Bloqueio de contas
□ Gestão de sessões
□ Funcionalidade Remember me

Autorização:
□ Testes de IDOR
□ Escalada de privilégios
□ Acesso ao nível de função

Validação de dados de entrada:
□ Injeção SQL
□ XSS (refletido/armazenado/DOM)
□ Injeção de comandos
□ Carregamento de ficheiros
□ XXE

Sessão:
□ Fixação de sessão
□ Expiração da sessão
□ Aleatoriedade dos tokens

Lógica de negócio:
□ Manipulação de preços
□ Contorno do fluxo de trabalho
□ Condições de corrida
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Resumo das ferramentas

| Ferramenta | Utilização principal | Atalho principal |
|------|-------------|--------------|
| Proxy | Intercetar tráfego | Ctrl+T (ativar/desativar) |
| Repeater | Testes manuais | Ctrl+R (enviar para) |
| Intruder | Ataques automatizados | Ctrl+I (enviar para) |
| Scanner | Encontrar vulnerabilidades | Botão direito → Scan |
| Spider | Descobrir conteúdo | Botão direito → Spider |
| Decoder | Codificar/descodificar | - |
| Comparer | Comparar diferenças entre respostas | Send to Comparer (enviar para Comparer) |
| Sequencer | Análise de tokens | Send to Sequencer (enviar para Sequencer) |

### Referência rápida dos tipos de ataque do Intruder

| Ataque | Quando utilizar |
|--------|----------|
| **Sniper** | Testar um parâmetro de cada vez |
| **Battering Ram** | O mesmo payload em todas as posições |
| **Pitchfork** | Pares de nome de utilizador:palavra-passe |
| **Cluster Bomb** | Todas as combinações de nome de utilizador × palavra-passe |

### Códigos de estado comuns

| Código | Significado | Interpretação no Intruder |
|------|---------|----------------------|
| 200 | OK | Resposta normal |
| 301/302 | Redirecionamento | Pode indicar sucesso |
| 401 | Não autorizado | Credenciais incorretas |
| 403 | Proibido | Acesso negado |
| 404 | Não encontrado | Caminho inválido |
| 500 | Erro do servidor | Possível vulnerabilidade |

---

<a id="-tips--best-practices"></a>
## 💡 Conselhos e boas práticas

### Conselhos de desempenho

1. **Limitar o âmbito**
   ```
   Manter apenas os domínios alvo no âmbito
   Reduz o ruído no histórico do Proxy
   ```

2. **Desativar extensões**
   ```
   Desativar extensões não utilizadas para melhorar o desempenho
   ```

3. **Utilizar ficheiros de projeto**
   ```
   Guardar o trabalho frequentemente
   Utilizar ficheiros de projeto para testes grandes
   ```

### Conselhos para os testes

1. **Começar pela análise passiva**
   ```
   Permitir que o analisador passivo encontre problemas evidentes
   Rever os resultados antes da análise ativa
   ```

2. **Utilizar primeiro o Repeater**
   ```
   Os testes manuais revelam mais detalhes
   Compreender a aplicação antes de automatizar
   ```

3. **Organizar com separadores**
   ```
   Dar nomes claros aos separadores do Repeater
   Atribuir nomes aos ataques do Intruder
   ```

### Reduzir a deteção

```
Definições para reduzir a exposição:

Project options → Connections:
- Limitar o ritmo dos pedidos
- Atrasos aleatórios
- Limite de ligações simultâneas

Intruder → Options:
- Atraso entre pedidos
- Hora de início
```

### Resolução de problemas

| Problema | Solução |
|-------|----------|
| Não é possível intercetar HTTPS | Instalar o certificado da CA |
| Desempenho lento | Reduzir o âmbito e desativar extensões |
| Pedidos em falta | Verificar "Intercept Server Responses" |
| O Scanner não encontra vulnerabilidades | Aumentar a intensidade da análise |

---

<a id="-legal-disclaimer"></a>
## ⚠️ Aviso legal

> **AVISO:** O Burp Suite é uma ferramenta poderosa de testes de segurança que deve ser utilizada apenas em **testes autorizados**.
> 
> - ✅ Testar aplicações que lhe pertencem
> - ✅ Testar com autorização escrita explícita
> - ✅ Testar em ambientes dedicados a testes
> - ❌ Nunca analisar sistemas de produção sem autorização
> - ❌ Nunca utilizar para acesso não autorizado
> 
> **Os testes de segurança não autorizados são ilegais e podem dar origem a processos criminais.**

---

<a id="-resources"></a>
## 📚 Recursos

### Recursos oficiais
- [PortSwigger Web Security Academy](https://portswigger.net/web-security)
- [Documentação do Burp Suite](https://portswigger.net/burp/documentation)
- [BApp Store](https://portswigger.net/bappstore)

### Recursos de aprendizagem
- [Guia de testes OWASP](https://owasp.org/www-project-web-security-testing-guide/)
- [HackTheBox](https://www.hackthebox.com/)
- [TryHackMe](https://tryhackme.com/)

### Guias de referência relacionados
- [SQLMap](../../SQLMap/translations/README.pt.md)
- [Metasploit Framework](../../Metasploit/README.md)

---

<p align="center">
  <b>🔶 Domine os testes de segurança de aplicações web!</b><br>
  <i>A melhor forma de aprender é praticar: prepare um laboratório e experimente!</i>
</p>
