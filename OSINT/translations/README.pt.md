# 🔍 Guia de referência de OSINT (Open Source Intelligence)

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
 ██████╗ ███████╗██╗███╗   ██╗████████╗
██╔═══██╗██╔════╝██║████╗  ██║╚══██╔══╝
██║   ██║███████╗██║██╔██╗ ██║   ██║   
██║   ██║╚════██║██║██║╚██╗██║   ██║   
╚██████╔╝███████║██║██║ ╚████║   ██║   
 ╚═════╝ ╚══════╝╚═╝╚═╝  ╚═══╝   ╚═╝   
```

> **Open Source Intelligence** — Recolha e análise de informação disponível publicamente, ou informações de fontes abertas.

> **Notas de versão:** OSINT significa informações de fontes abertas; OPSEC é segurança operacional. As ferramentas, planos gratuitos e quantidades de sites listados refletem o original e podem mudar. Os exemplos de fontes do [theHarvester](https://github.com/laramies/theHarvester) dependem dos módulos e das chaves de API disponíveis. Os comandos antigos de Sherlock são mantidos; a [documentação do projeto](https://sherlockproject.xyz/usage) utiliza a CLI `sherlock` e opções adequadas à versão instalada. A divisão entre OSINT ativo e passivo é simplificada: a interação depende da ferramenta e da forma de recolha. VPN/Tor não garantem anonimato por si só.

---

## 📑 Índice

| Tema | Descrição | Guia |
|-------|-------------|-------|
| **Pesquisa de pessoas** | Encontrar informação sobre pessoas | [📄 Ver](People-Search.pt.md) |
| **OSINT de email** | Reconhecimento e verificação de endereços de email | [📄 Ver](Email-OSINT.pt.md) |
| **Redes sociais** | Investigação em redes sociais | [📄 Ver](Social-Media-OSINT.pt.md) |
| **Domínio e IP** | Reconhecimento de domínios/IP | [📄 Ver](Domain-IP-OSINT.pt.md) |
| **OSINT de imagens** | Pesquisa inversa e análise de imagens | [📄 Ver](Image-OSINT.pt.md) |

---

## 🎯 Metodologia de OSINT

```
┌─────────────────────────────────────────────────────────────┐
│                    CICLO DE VIDA DE OSINT                    │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│   1. REQUISITOS     →  Definir necessidades de informação   │
│         ↓                                                   │
│     2. RECOLHA        →  Reunir dados brutos das fontes     │
│         ↓                                                   │
│  3. PROCESSAMENTO  →  Converter dados para um formato útil  │
│         ↓                                                   │
│   4. ANÁLISE        →  Avaliar e correlacionar resultados   │
│         ↓                                                   │
│   5. DISSEMINAÇÃO   →  Comunicar e apresentar a informação  │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## 🛠️ Ferramentas essenciais de OSINT

### Motores de pesquisa
| Ferramenta | URL | Finalidade |
|------|-----|---------|
| **Google** | https://google.com | Pesquisa geral |
| **Bing** | bing.com | Pesquisa alternativa |
| **DuckDuckGo** | duckduckgo.com | Orientado para a privacidade |
| **Yandex** | yandex.com | Motor de pesquisa russo (útil para imagens) |
| **Baidu** | baidu.com | Motor de pesquisa chinês |

### Pesquisa de pessoas
| Ferramenta | URL | Finalidade |
|------|-----|---------|
| **Pipl** | pipl.com | Pesquisa aprofundada de pessoas |
| **Spokeo** | spokeo.com | Pesquisa de pessoas nos EUA |
| **WhitePages** | whitepages.com | Consulta de telefones/moradas |
| **ThatsThem** | thatsthem.com | Pesquisa gratuita de pessoas |
| **BeenVerified** | beenverified.com | Verificação de antecedentes |

### Ferramentas de email
| Ferramenta | URL | Finalidade |
|------|-----|---------|
| **Hunter.io** | hunter.io | Encontrar emails empresariais |
| **Have I Been Pwned** | haveibeenpwned.com | Consulta de fugas de dados |
| **EmailRep** | emailrep.io | Reputação de email |
| **Phonebook.cz** | phonebook.cz | Pesquisa de emails/domínios |
| **h8mail** | GitHub | Pesquisa de credenciais em fugas de dados |

### Redes sociais
| Ferramenta | URL | Finalidade |
|------|-----|---------|
| **Sherlock** | GitHub | Pesquisa de nomes de utilizador |
| **Social Searcher** | social-searcher.com | Pesquisa em redes sociais |
| **Namechk** | namechk.com | Disponibilidade de nomes de utilizador |
| **KnowEm** | knowem.com | Verificação de nomes de utilizador |
| **Xquik** | https://github.com/Xquik-dev/x-twitter-scraper | API independente de dados do X (Twitter) para pesquisa, exportação de seguidores, monitorização e MCP. |

### Ferramentas de domínios/IP
| Ferramenta | URL | Finalidade |
|------|-----|---------|
| **Shodan** | shodan.io | Pesquisa de dispositivos |
| **Censys** | censys.io | Análise da Internet |
| **SecurityTrails** | securitytrails.com | Histórico DNS |
| **ViewDNS** | viewdns.info | Ferramentas DNS |
| **BuiltWith** | builtwith.com | Identificação de tecnologias |

---

## 🔥 Comandos de referência rápida

### theHarvester
```bash
# Recolha de emails e subdomínios
theHarvester -d target.com -l 500 -b all

# Fontes específicas
theHarvester -d target.com -b google,linkedin,twitter

# Guardar num ficheiro
theHarvester -d target.com -b all -f results.html
```

### Sherlock (pesquisa de nomes de utilizador)
```bash
# Pesquisar em todas as plataformas
python3 sherlock.py username

# Sites específicos
python3 sherlock.py username --site twitter instagram facebook

# Guardar a saída num ficheiro
python3 sherlock.py username --output results.txt
```

### Maltego
```
# Tipos de transformações:
- Pessoa para email
- Email para domínio
- Domínio para IP
- IP para localização
- Perfis em redes sociais
```

### Recon-ng
```bash
# Iniciar o recon-ng
recon-ng

# Criar um workspace (área de trabalho)
workspaces create target_name

# Carregar módulos
modules load recon/domains-hosts/hackertarget
modules load recon/contacts-credentials/hibp_breach

# Definir o alvo
options set SOURCE target.com

# Executar o módulo
run
```

---

## 📊 Categorias de OSINT

### OSINT passivo
```
Sem interação direta com o alvo:
- Motores de pesquisa
- Redes sociais
- Registos públicos
- Archive.org
- Dados WHOIS
```

### OSINT ativo
```
Alguma interação com o alvo:
- Análise de portas
- Consultas DNS
- Recolha automatizada de páginas web
- Verificação de email
```

---

## 🔒 Segurança operacional (OPSEC)

### Boas práticas
```
✓ Utilizar VPN/Tor
✓ Criar contas sock puppet (identidades de investigação separadas)
✓ Utilizar máquinas virtuais
✓ Não utilizar contas pessoais
✓ Limpar regularmente os cookies e a cache
✓ Utilizar perfis de navegador separados
✓ Documentar tudo
```

### Contas sock puppet
```
- Utilizar identidades geradas
- Manter uma persona consistente entre plataformas
- Contas antigas (consideradas mais credíveis)
- Detalhes de perfil plausíveis
- Não associar à identidade real
```

---

## 📋 Lista de verificação de OSINT

```markdown
## Alvo: [Nome/Empresa/Domínio]

### Pessoas
□ Variações do nome completo
□ Números de telefone
□ Endereços de email
□ Moradas físicas
□ Perfis em redes sociais
□ Percurso profissional
□ Percurso académico
□ Fotografias/imagens

### Empresa
□ Registo do domínio (WHOIS)
□ Conjunto de tecnologias
□ Lista de colaboradores
□ Formato dos emails
□ Presença em redes sociais
□ Notícias/comunicados de imprensa
□ Informação financeira
□ Localizações físicas

### Aspetos técnicos
□ Endereços IP
□ Subdomínios
□ Portas/serviços expostos
□ Registos DNS
□ Certificados SSL
□ Dados históricos
```

---

## 🔗 Frameworks de OSINT

### OSINT Framework
```
https://osintframework.com/
- Organizado por categoria
- Ligações para centenas de ferramentas
- Estrutura visual em árvore
```

### IntelTechniques
```
https://inteltechniques.com/tools/
- Ferramentas de Michael Bazzell
- Ferramentas de pesquisa personalizadas
- Fluxos de trabalho de investigação
```

---

## 📚 Guias relacionados neste repositório

- [Google Dorking](../../Google-Dorking/README.md)
- [GitHub Dorking](../../GitHub-Dorking/README.md)
- [Shodan](../../Shodan/README.md)
- [Amass](../../Amass/README.md)
- [Subfinder](../../Subfinder/README.md)

---

**Voltar à página principal:** [🔴 Hacking Cheatsheets](../../README.md)
