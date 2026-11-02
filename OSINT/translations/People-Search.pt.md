# 👤 OSINT de pesquisa de pessoas

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="People-Search.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="People-Search.el.md">🇬🇷 Ελληνικά</a>
</p>

```
██████╗ ███████╗ ██████╗ ██████╗ ██╗     ███████╗
██╔══██╗██╔════╝██╔═══██╗██╔══██╗██║     ██╔════╝
██████╔╝█████╗  ██║   ██║██████╔╝██║     █████╗  
██╔═══╝ ██╔══╝  ██║   ██║██╔═══╝ ██║     ██╔══╝  
██║     ███████╗╚██████╔╝██║     ███████╗███████╗
╚═╝     ╚══════╝ ╚═════╝ ╚═╝     ╚══════╝╚══════╝
███████╗███████╗ █████╗ ██████╗  ██████╗██╗  ██╗ 
██╔════╝██╔════╝██╔══██╗██╔══██╗██╔════╝██║  ██║ 
███████╗█████╗  ███████║██████╔╝██║     ███████║ 
╚════██║██╔══╝  ██╔══██║██╔══██╗██║     ██╔══██║ 
███████║███████╗██║  ██║██║  ██║╚██████╗██║  ██║ 
╚══════╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝ 
```

> **Notas de versão:** Muitos serviços e registos desta página têm cobertura sobretudo nos EUA; a classificação gratuito/pago pertence ao original e deve ser confirmada no serviço. Os comandos antigos de Sherlock foram preservados; a [CLI documentada](https://sherlockproject.xyz/usage) utiliza `sherlock`, e a seleção de vários sites depende das opções suportadas. O [PhoneInfoga](https://github.com/sundowndev/phoneinfoga) atual é um projeto em Go, descrito pelos autores como não mantido; a instalação por `pip3` do original é histórica. O [Twint foi arquivado em março de 2023](https://github.com/twintproject/twint), pelo que os exemplos não devem ser presumidos funcionais no X atual. As frases de Graph Search são exemplos históricos. Um nome de utilizador ou uma semelhança facial não comprovam identidade.

---

## 🔍 Motores de pesquisa de pessoas

### Serviços gratuitos
| Ferramenta | URL | Cobertura |
|------|-----|----------|
| **ThatsThem** | thatsthem.com | EUA |
| **FastPeopleSearch** | fastpeoplesearch.com | EUA |
| **TruePeopleSearch** | truepeoplesearch.com | EUA |
| **Webmii** | webmii.com | Global |
| **PeekYou** | peekyou.com | EUA |
| **Pipl** | pipl.com | Global |

### Serviços pagos
| Ferramenta | URL | Funcionalidades |
|------|-----|----------|
| **Spokeo** | spokeo.com | Pesquisa aprofundada e redes sociais |
| **BeenVerified** | beenverified.com | Verificação de antecedentes |
| **Intelius** | intelius.com | Registos criminais |
| **EUA Pesquisar** | ussearch.com | Registos judiciais |
| **Radaris** | radaris.com | Registos de imóveis |

---

## 🔎 Google dorking para pesquisa de pessoas

### Pesquisas básicas
```
# Pesquisa por nome
"John Smith" site:linkedin.com
"John Smith" site:facebook.com
"John Smith" site:twitter.com

# Nome + localização
"John Smith" "New York"
"John Smith" "NYC" OR "New York City"

# Nome + empresa
"John Smith" "Microsoft"
"John Smith" site:microsoft.com

# Nome + email
"John Smith" "@gmail.com"
"John Smith" email
```

### Informação profissional
```
# Pesquisa de currículos/CV
"John Smith" filetype:pdf resume
"John Smith" filetype:doc CV
intitle:"curriculum vitae" "John Smith"

# Específico do LinkedIn
site:linkedin.com/in "John Smith"
site:linkedin.com "John Smith" "Software Engineer"

# Perfis profissionais
site:about.me "John Smith"
site:crunchbase.com "John Smith"
```

### Descoberta de redes sociais
```
# Encontrar todos os perfis
"John Smith" (site:facebook.com OR site:twitter.com OR site:instagram.com)

# Pesquisa de nomes de utilizador
"johnsmith123" site:twitter.com
"johnsmith123" site:reddit.com
"johnsmith123" site:github.com
```

---

## 🛠️ Ferramentas de linha de comandos

### Sherlock (pesquisa de nomes de utilizador)
```bash
# Instalar
git clone https://github.com/sherlock-project/sherlock.git
cd sherlock
pip3 install -r requirements.txt

# Pesquisar o nome de utilizador em várias plataformas
python3 sherlock username

# Vários nomes de utilizador
python3 sherlock user1 user2 user3

# Apenas sites específicos
python3 sherlock username --site twitter instagram facebook

# Exportar os resultados
python3 sherlock username --output results.txt
python3 sherlock username --csv
python3 sherlock username --xlsx
```

### Maigret (pesquisa avançada de nomes de utilizador)
```bash
# Instalar
pip3 install maigret

# Pesquisar
maigret username

# Com Tor
maigret username --tor

# Saída em JSON
maigret username --json results.json
```

### holehe (verificação de registos com email)
```bash
# Instalar
pip3 install holehe

# Verificar o email em vários serviços
holehe email@example.com

# Mostrar apenas sites com registo
holehe email@example.com --only-used
```

---

## 📧 Do email à pessoa

### Encontrar uma pessoa a partir do email
```
1. Pesquisar o email no Google: "email@example.com"
2. Consultar o HIBP: haveibeenpwned.com
3. Pesquisar em bases de dados de fugas de dados
4. Verificar registos em redes sociais
5. Serviços de pesquisa inversa de email
```

### Descoberta de formatos de email
```bash
# Formatos comuns de email
first.last@company.com
flast@company.com
firstl@company.com
first_last@company.com
first@company.com

# Descoberta de formatos com Hunter.io
https://hunter.io/email-finder
```

---

## 📱 OSINT de números de telefone

### Serviços de consulta de números de telefone
| Serviço | URL | Tipo |
|---------|-----|------|
| **TrueCaller** | truecaller.com | Identificação de chamadas |
| **Sync.me** | sync.me | Identificação de chamadas |
| **NumLookup** | numlookup.com | Consulta gratuita |
| **SpyDialer** | spydialer.com | Pesquisa inversa |
| **CallerID Test** | calleridtest.com | Informações do operador |

### Google dorks para números de telefone
```
# Pesquisa básica
"555-123-4567"
"+1 555 123 4567"
"5551234567"

# Encontrar informação associada
"555-123-4567" site:facebook.com
"555-123-4567" "@"
"555-123-4567" "email"
```

### OSINT sobre números de telefone
```bash
# phoneinfoga
pip3 install phoneinfoga
phoneinfoga scan -n "+1234567890"
phoneinfoga serve -p 8080  # Interface web

# Utilizar API
curl "https://api.numlookupapi.com/v1/validate/14155551234?apikey=YOUR_KEY"
```

---

## 🏠 Moradas e registos de imóveis

### Pesquisa de imóveis
| Serviço | URL | Cobertura |
|---------|-----|----------|
| **Zillow** | zillow.com | EUA |
| **Redfin** | redfin.com | EUA |
| **County Assessor** | [varia] | Registos locais |
| **Whitepages** | whitepages.com | EUA |

### Registos públicos
```
# Registos judiciais
PACER (pacer.gov) — Tribunais federais
Sites dos tribunais estaduais — Tribunais estaduais

# Registos eleitorais
Bases de dados de inscrição eleitoral (variam por estado)

# Registos comerciais
Sites das Secretarias de Estado de cada estado dos EUA
```

---

## 👥 Análise de redes sociais

### OSINT de LinkedIn
```
# Pesquisa de perfis
site:linkedin.com/in "John Smith"
site:linkedin.com/in "John Smith" "Company Name"

# Encontrar ligações
Consultar "People Also Viewed" (perfis também visualizados)
Verificar as ligações em comum
Lista de colaboradores da empresa
```

### OSINT de Facebook
```
# URL de perfis
facebook.com/search/people/?q=john%20smith
facebook.com/search/pages/?q=john%20smith

# Graph Search (atualmente limitado, segundo o original)
People named "John" who work at "Company"
People who live in "City" and like "Page"
```

### Ferramentas para análise de redes sociais
```bash
# Social Analyzer
pip3 install social-analyzer
social-analyzer --username "target" --metadata

# Twint (Twitter)
pip3 install twint
twint -u username --followers
twint -u username --following
```

---

## 📸 Pesquisa de fotografias/rostos

### Pesquisa inversa de imagens
| Serviço | URL | Mais indicado para |
|---------|-----|----------|
| **Google Images** | images.google.com | Geral |
| **TinEye** | tineye.com | Correspondências exatas |
| **Yandex** | yandex.com/images | Mais indicado para rostos (segundo o original) |
| **Bing Images** | bing.com/images | Boa alternativa |
| **PimEyes** | pimeyes.com | Reconhecimento facial |

### Pesquisa de rostos
```
1. Yandex Images — Mais indicado para encontrar rostos (segundo o original)
2. PimEyes — Reconhecimento facial pago
3. Social Catfish — Pesquisa inversa de imagens para redes sociais
```

---

## 📋 Lista de verificação de pesquisa de pessoas

```markdown
## Alvo: [Nome completo]

### Informações básicas
□ Nome legal completo
□ Data de nascimento
□ Morada atual
□ Números de telefone
□ Endereços de email

### Presença online
□ Perfil de LinkedIn
□ Perfil de Facebook
□ Conta de Twitter/X
□ Instagram
□ TikTok
□ Outras redes sociais
□ Site/blogue pessoal

### Aspetos profissionais
□ Empregador atual
□ Cargo
□ Histórico profissional
□ Formação académica
□ Licenças profissionais
□ Publicações/patentes

### Registos
□ Registos de imóveis
□ Registos judiciais
□ Registos comerciais
□ Inscrição eleitoral
□ Registos de casamento/divórcio

### Ligações
□ Familiares
□ Pessoas associadas
□ Parceiros de negócio
□ Ligações sociais
```

---

## ⚠️ Considerações legais e éticas

```
✓ Utilizar apenas informação disponível publicamente
✓ Respeitar as leis de privacidade (RGPD/GDPR, CCPA)
✓ Não usurpar identidades nem enganar
✓ Documentar a metodologia
✓ Utilizar apenas para fins legítimos
✗ Não perseguir nem assediar
✗ Não aceder a contas privadas
✗ Não divulgar publicamente os resultados sem consentimento
```

---

**Voltar a OSINT:** [🔍 Visão geral de OSINT](README.pt.md)
