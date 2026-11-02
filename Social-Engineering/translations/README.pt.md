# 🎭 Guias de referência de engenharia social

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
███████╗ ██████╗  ██████╗██╗ █████╗ ██╗         ███████╗███╗   ██╗ ██████╗ ██╗███╗   ██╗███████╗███████╗██████╗ ██╗███╗   ██╗ ██████╗ 
██╔════╝██╔═══██╗██╔════╝██║██╔══██╗██║         ██╔════╝████╗  ██║██╔════╝ ██║████╗  ██║██╔════╝██╔════╝██╔══██╗██║████╗  ██║██╔════╝ 
███████╗██║   ██║██║     ██║███████║██║         █████╗  ██╔██╗ ██║██║  ███╗██║██╔██╗ ██║█████╗  █████╗  ██████╔╝██║██╔██╗ ██║██║  ███╗
╚════██║██║   ██║██║     ██║██╔══██║██║         ██╔══╝  ██║╚██╗██║██║   ██║██║██║╚██╗██║██╔══╝  ██╔══╝  ██╔══██╗██║██║╚██╗██║██║   ██║
███████║╚██████╔╝╚██████╗██║██║  ██║███████╗    ███████╗██║ ╚████║╚██████╔╝██║██║ ╚████║███████╗███████╗██║  ██║██║██║ ╚████║╚██████╔╝
╚══════╝ ╚═════╝  ╚═════╝╚═╝╚═╝  ╚═╝╚══════╝    ╚══════╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝╚═╝  ╚═══╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 O que é a engenharia social?

A engenharia social consiste em manipular pessoas para que divulguem informações confidenciais ou realizem ações que comprometam a segurança. Explora a psicologia humana em vez de vulnerabilidades técnicas.

**Princípios fundamentais:**
- 🎭 **Autoridade** — As pessoas confiam em figuras de autoridade
- ⏰ **Urgência** — A pressão do tempo reduz o pensamento crítico
- 🤝 **Reciprocidade** — As pessoas sentem obrigação de retribuir favores
- 👥 **Prova social** — As pessoas tendem a seguir o comportamento dos outros
- 😊 **Simpatia** — As pessoas acedem mais facilmente a pedidos de quem gostam
- 📉 **Escassez** — A disponibilidade limitada aumenta o desejo

---

## 📖 Guias de engenharia social

| Tema | Descrição | Guia |
|-------|-------------|-------|
| **Phishing** | Técnicas e ferramentas de phishing por email, SMS e voz | [📄 Ver](./Phishing.pt.md) |
| **Pretexting** | Criação de cenários e identidades fictícias credíveis | [📄 Ver](./Pretexting.pt.md) |

---

## 🛠️ Ferramentas essenciais de engenharia social

| Ferramenta | Finalidade | Tipo |
|------|---------|------|
| **GoPhish** | Framework para campanhas de phishing | Phishing |
| **SET (Social Engineering Toolkit)** | Framework abrangente de engenharia social | Várias finalidades |
| **Evilginx2** | Phishing com intermediário (MITM; contorno de 2FA) | Phishing |
| **King Phisher** | Plataforma para campanhas de phishing | Phishing |
| **Modlishka** | Phishing com reverse proxy (proxy inverso) | Phishing |
| **theHarvester** | Recolha de emails e domínios | Reconhecimento |
| **Ferramentas LinkedIn** | Enumeração de funcionários | OSINT |
| **Maltego** | OSINT e mapeamento de relações | Reconhecimento |

> **Precisão do original:** «contorno de 2FA» não se aplica a todos os métodos. O phishing com intermediário pode comprometer métodos tradicionais de MFA, mas passkeys e chaves FIDO2 oferecem resistência a phishing. Ver a [orientação da Microsoft](https://learn.microsoft.com/entra/fundamentals/zero-trust-protect-identities).

### Instalação rápida das ferramentas
```bash
# GoPhish
wget https://github.com/gophish/gophish/releases/download/v0.12.1/gophish-v0.12.1-linux-64bit.zip
unzip gophish-v0.12.1-linux-64bit.zip
./gophish

# SET (Social Engineering Toolkit)
git clone https://github.com/trustedsec/social-engineer-toolkit
cd social-engineer-toolkit
pip install -r requirements.txt
python setoolkit

# Evilginx2
go install github.com/kgretzky/evilginx2@latest

# theHarvester
pip install theHarvester
theHarvester -d target.com -b all
```

> **Versões:** os exemplos de instalação são preservados do original; o download do GoPhish fixa a versão `v0.12.1`. Confirma os requisitos e a sintaxe da versão utilizada antes de seguir os exemplos.

---

## 📊 Tipos de ataques de engenharia social

| Tipo de ataque | Descrição | Vetor |
|-------------|-------------|--------|
| **Phishing** | Emails enganosos para roubar credenciais | Email |
| **Spear Phishing** | Phishing dirigido a pessoas específicas | Email |
| **Whaling** | Phishing dirigido a membros da administração e da direção (C-level) | Email |
| **Vishing** | Engenharia social por voz ou telefone | Telefone |
| **Smishing** | Phishing por SMS | SMS |
| **Pretexting** | Criação de cenários falsos para obter informações | Qualquer |
| **Baiting** | Abandono de suportes infetados, como dispositivos USB, para atrair a vítima | Físico |
| **Quid Pro Quo** | Oferta de algo em troca de informações | Qualquer |
| **Tailgating** | Seguir uma pessoa autorizada para entrar numa área protegida | Físico |
| **Watering Hole** | Comprometimento de sites visitados pelos alvos | Web |

---

## 🔗 Referência rápida: indicadores de phishing

### Sinais de alerta em emails
```
□ O email do remetente não corresponde ao domínio da empresa
□ Saudações genéricas («Caro Cliente»)
□ Urgência ou ameaças
□ Erros ortográficos ou gramaticais
□ Ligações suspeitas (passar o cursor para verificar)
□ Anexos inesperados
□ Pedidos de informações sensíveis
□ Ofertas demasiado boas para serem verdade
```

### Análise de URLs
```bash
# Verificar a idade do domínio
whois suspicious-domain.com

# Verificar a reputação
https://www.virustotal.com
https://urlscan.io

# Analisar URLs encurtados
https://checkshorturl.com
```

---

## 📚 Recursos

- [Engenharia social — OWASP](https://owasp.org/www-community/attacks/Social_Engineering)
- [MITRE ATT&CK — Acesso inicial](https://attack.mitre.org/tactics/TA0001/)
- [Phishing.org](https://www.phishing.org/)
- [Framework de engenharia social](https://www.social-engineer.org/)

---

<p align="center">
  <b>🎭 Utiliza a engenharia social apenas em testes autorizados!</b>
</p>
