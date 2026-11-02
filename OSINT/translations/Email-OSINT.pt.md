# 📧 Guia de referência de OSINT de email

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Email-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Email-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

```
███████╗███╗   ███╗ █████╗ ██╗██╗     
██╔════╝████╗ ████║██╔══██╗██║██║     
█████╗  ██╔████╔██║███████║██║██║     
██╔══╝  ██║╚██╔╝██║██╔══██║██║██║     
███████╗██║ ╚═╝ ██║██║  ██║██║███████╗
╚══════╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚══════╝
```

> **Notas de precisão:** Em SMTP, `250` indica aceitação do comando e `550` pode significar caixa inexistente, falta de acesso ou rejeição por política; não provam isoladamente a existência ou inexistência do destinatário ([RFC 5321](https://www.rfc-editor.org/rfc/rfc5321.html)). Um registo MX também não comprova que uma caixa específica exista. Na [API HIBP](https://haveibeenpwned.com/API/v3), a pesquisa de contas exige uma chave e o plano apropriado, além de identificação por User-Agent; a indicação «gratuito» da tabela não descreve todos os acessos à API. Os backends e as fontes do [theHarvester](https://github.com/laramies/theHarvester) variam com a versão. Resultados de registos em serviços devem ser confirmados e não demonstram, por si só, quem controla a conta.

---

## 🔍 Ferramentas de descoberta de emails

| Ferramenta | URL | Finalidade |
|------|-----|---------|
| **Hunter.io** | hunter.io | Encontrar emails empresariais |
| **Phonebook.cz** | phonebook.cz | Pesquisa gratuita de emails |
| **Snov.io** | snov.io | Descoberta de emails |
| **Clearbit Connect** | clearbit.com | Enriquecimento de dados de email |
| **RocketReach** | rocketreach.co | Descoberta de contactos |
| **Voila Norbert** | voilanorbert.com | Consulta de emails |

---

## 🛠️ Comandos de enumeração de emails

### theHarvester
```bash
# Recolha de emails
theHarvester -d target.com -l 500 -b all
theHarvester -d target.com -b google,linkedin,bing
theHarvester -d target.com -b all -f emails.html
```

### CLI do Hunter.io
```bash
# Consulta à API
curl "https://api.hunter.io/v2/domain-search?domain=target.com&api_key=YOUR_KEY"

# Verificação de email
curl "https://api.hunter.io/v2/email-verifier?email=test@target.com&api_key=YOUR_KEY"
```

### h8mail (pesquisa em fugas de dados)
```bash
pip3 install h8mail
h8mail -t target@email.com
h8mail -t target@email.com -c h8mail_config.ini
```

### holehe (verificação de registos em serviços)
```bash
pip3 install holehe
holehe target@email.com
holehe target@email.com --only-used
```

---

## 📧 Padrões de formato de email

```bash
# Formatos empresariais comuns
first.last@company.com     # john.smith@company.com
flast@company.com          # jsmith@company.com
firstl@company.com         # johns@company.com
first_last@company.com     # john_smith@company.com
first@company.com          # john@company.com
last.first@company.com     # smith.john@company.com
```

### Gerador de permutações de email
```python
# Gerar variações de email
names = ["john", "smith"]
domain = "company.com"

patterns = [
    f"{names[0]}.{names[1]}@{domain}",
    f"{names[0][0]}{names[1]}@{domain}",
    f"{names[0]}{names[1][0]}@{domain}",
    f"{names[0]}_{names[1]}@{domain}",
    f"{names[0]}@{domain}",
]
```

---

## 🔐 Consulta de fugas de dados

| Serviço | URL | Tipo |
|---------|-----|------|
| **Have I Been Pwned** | haveibeenpwned.com | Gratuito |
| **DeHashed** | dehashed.com | Pago |
| **LeakCheck** | leakcheck.io | Pago |
| **Snusbase** | snusbase.com | Pago |
| **IntelX** | intelx.io | Freemium (gratuito com funções pagas) |

### HIBP API
```bash
curl -H "hibp-api-key: YOUR_KEY" \
  "https://haveibeenpwned.com/api/v3/breachedaccount/test@example.com"
```

---

## ✅ Verificação de email

### Métodos de verificação
```
1. Verificação de registos MX — O domínio tem um servidor de email
2. Verificação SMTP — A caixa de correio existe
3. Verificação de sintaxe — Formato de email válido
4. Verificação de emails descartáveis — Não é um email temporário
5. Verificação de função — Não é um endereço genérico (info@, admin@)
```

### Verificação SMTP simples
```bash
# Verificar os registos MX
nslookup -type=mx target.com
dig mx target.com

# Verificação através de Telnet
telnet mail.target.com 25
HELO test.com
MAIL FROM:<test@test.com>
RCPT TO:<target@target.com>
# 250 = existe, 550 = não existe (simplificação do original; ver nota)
```

---

## 📋 Lista de verificação de OSINT de email

```markdown
## Email alvo: [email@domain.com]

□ Verificar se o email existe (SMTP/Hunter)
□ Consultar fugas de dados (HIBP)
□ Encontrar contas associadas (holehe)
□ Pesquisar no Google/Bing
□ Verificar registos em redes sociais
□ Procurar perfis públicos
□ Pesquisar em sites de publicação de texto (paste sites)
□ Consultar redes profissionais
```

---

**Voltar a OSINT:** [🔍 Visão geral de OSINT](README.pt.md)
