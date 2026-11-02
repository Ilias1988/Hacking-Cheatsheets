# 🎣 Guia de referência de phishing

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Phishing.tr.md) • [🇬🇷 Ελληνικά](Phishing.el.md)

```
██████╗ ██╗  ██╗██╗███████╗██╗  ██╗██╗███╗   ██╗ ██████╗ 
██╔══██╗██║  ██║██║██╔════╝██║  ██║██║████╗  ██║██╔════╝ 
██████╔╝███████║██║███████╗███████║██║██╔██╗ ██║██║  ███╗
██╔═══╝ ██╔══██║██║╚════██║██╔══██║██║██║╚██╗██║██║   ██║
██║     ██║  ██║██║███████║██║  ██║██║██║ ╚████║╚██████╔╝
╚═╝     ╚═╝  ╚═╝╚═╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 📊 Tipos de phishing

| Tipo | Alvo | Descrição |
|------|--------|-------------|
| **Phishing** | Em massa | Emails genéricos enviados a muitos utilizadores |
| **Spear Phishing** | Específico | Ataque dirigido a pessoas específicas |
| **Whaling** | Membros da direção | Ataque dirigido à administração e gestão (C-level) |
| **Clone Phishing** | Qualquer | Cópia de um email legítimo com conteúdo malicioso |
| **Vishing** | Qualquer | Phishing por voz ou telefone |
| **Smishing** | Qualquer | Phishing por SMS |
| **Pharming** | Qualquer | Manipulação do DNS para encaminhar para um site falso |

Phishing designa a utilização de comunicações enganosas para obter dados ou induzir ações. Spear phishing é a variante direcionada; whaling centra-se em pessoas com cargos de direção.

---

## 🛠️ Configuração e utilização do GoPhish

### Instalação
```bash
# Descarregar o GoPhish
wget https://github.com/gophish/gophish/releases/download/v0.12.1/gophish-v0.12.1-linux-64bit.zip
unzip gophish-v0.12.1-linux-64bit.zip
cd gophish

# Iniciar o GoPhish
./gophish

# Aceder ao painel de administração
https://localhost:3333
# Credenciais iniciais: admin / consultar a saída da consola
```

> **Precisão do exemplo:** o ficheiro descarregado está fixado na versão `v0.12.1`. O comando `cd gophish` pressupõe que existe uma pasta com esse nome; após a extração, confirma a localização real do executável. Os exemplos de instalação são preservados do original.

### Sequência de configuração da campanha
```
1. Sending Profile   - Configurar o servidor SMTP
2. Landing Page      - Criar uma página de início de sessão falsa
3. Email Template    - Preparar o email de phishing
4. Users & Groups    - Importar a lista de alvos
5. Launch Campaign   - Enviar os emails de phishing
6. Monitor Results   - Acompanhar aberturas, cliques e credenciais
```

Os nomes das opções da interface são mantidos para facilitar a sua localização: perfil de envio, página de destino, modelo de email, utilizadores e grupos, lançamento da campanha e acompanhamento dos resultados.

### Exemplo de modelo de email
```html
<!DOCTYPE html>
<html>
<body>
<p>Dear {{.FirstName}},</p>
<p>Your IT department requires you to verify your account credentials.</p>
<p>Please click the link below to complete verification:</p>
<p><a href="{{.URL}}">Verify Account</a></p>
<p>This action is required within 24 hours.</p>
<br>
<p>IT Support Team</p>
</body>
</html>
```

O HTML original mantém-se intacto, incluindo `{{.FirstName}}` e `{{.URL}}`. O texto dirige-se ao destinatário pelo nome, afirma que o departamento de informática exige a verificação das credenciais, apresenta uma ligação «Verificar conta» e impõe um prazo de 24 horas, com assinatura da equipa de apoio informático.

### Exemplo de página de destino
```html
<!DOCTYPE html>
<html>
<head>
    <title>Microsoft Login</title>
</head>
<body>
    <form method="post" action="">
        <h2>Sign in</h2>
        <input type="email" name="email" placeholder="Email" required>
        <input type="password" name="password" placeholder="Password" required>
        <button type="submit">Sign in</button>
    </form>
</body>
</html>
```

Neste exemplo HTML, «Sign in» significa «Iniciar sessão» e «Password» significa «Palavra-passe». Os atributos, nomes dos campos e valores literais são preservados.

---

## 🔥 Evilginx2 (contorno de 2FA)

> **Precisão do original:** 2FA significa autenticação de dois fatores. O phishing com intermediário (AiTM/MITM) pode comprometer métodos tradicionais de MFA, mas não constitui um contorno universal: passkeys e chaves FIDO2 oferecem resistência a phishing. Ver a [orientação da Microsoft sobre proteção de identidades](https://learn.microsoft.com/entra/fundamentals/zero-trust-protect-identities). Os nomes e comandos Evilginx2 abaixo são os do original; confirma a compatibilidade com a versão utilizada.

### Instalação
```bash
# Instalar o Evilginx2
go install github.com/kgretzky/evilginx2@latest

# Ou descarregar o executável
wget https://github.com/kgretzky/evilginx2/releases/...

# Executar
sudo evilginx2
```

> A ligação terminada em `...` é um marcador incompleto no original, não um URL de download utilizável.

### Configuração básica
```bash
# Na consola do Evilginx2
config domain yourdomain.com
config ip YOUR_SERVER_IP

# Listar phishlets
phishlets

# Ativar o phishlet do Microsoft 365
phishlets hostname o365 login.yourdomain.com
phishlets enable o365

# Criar uma ligação de atração (lure)
lures create o365
lures get-url 0
```

Um phishlet é uma configuração que descreve o serviço a intermediar; uma lure é a ligação usada para encaminhar o alvo para esse cenário.

### Alvos dos phishlets
```
o365        - Microsoft 365
google      - Contas Google
linkedin    - LinkedIn
twitter     - Twitter/X
github      - GitHub
instagram   - Instagram
```

---

## 📧 Recolha de endereços de email

### theHarvester
```bash
# Recolher endereços de email
theHarvester -d target.com -b all

# Fontes específicas
theHarvester -d target.com -b google,linkedin,bing

# Guardar a saída num ficheiro
theHarvester -d target.com -b all -f output.html
```

### Hunter.io
```bash
# Através da API
curl "https://api.hunter.io/v2/domain-search?domain=target.com&api_key=YOUR_KEY"

# Obter o formato do email
curl "https://api.hunter.io/v2/email-finder?domain=target.com&first_name=John&last_name=Doe&api_key=YOUR_KEY"
```

### Enumeração no LinkedIn
```bash
# Utilizar o linkedin2username
python linkedin2username.py -u USERNAME -c COMPANY_NAME

# Inferir o formato dos endereços
john.doe@company.com
jdoe@company.com
j.doe@company.com
johnd@company.com
```

---

## 📮 Infraestrutura de envio

### Serviços SMTP para testes
```
- Amazon SES
- SendGrid
- Mailgun
- SMTP2GO
- Alojamento próprio (elevado risco de classificação como spam)
```

### Contorno de SPF/DKIM/DMARC
```bash
# Verificar o registo SPF
dig txt target.com | grep spf

# Fragilidades de SPF a explorar:
# - "include:" com terceiros demasiado permissivos
# - "~all" (softfail) em vez de "-all"
# - Ausência de registo SPF

# Verificar o DMARC
dig txt _dmarc.target.com
```

> **Precisão do original:** `~all` produz um resultado SPF softfail, não um resultado pass. Isso, por si só, não demonstra um contorno de DMARC: a avaliação envolve também o alinhamento dos identificadores SPF/DKIM e a política publicada. Ver [SPF — RFC 7208](https://www.rfc-editor.org/rfc/rfc7208) e [DMARC — RFC 7489](https://www.rfc-editor.org/rfc/rfc7489). SPF verifica remetentes autorizados, DKIM usa assinaturas de domínio e DMARC define alinhamento, política e relatórios.

### Configuração de domínios para phishing
```bash
# Domínios semelhantes (typosquatting)
target.com → tarqet.com
target.com → target-secure.com
target.com → target.co
target.com → target-login.com

# Verificar a disponibilidade
whois potential-phish-domain.com
```

Typosquatting é o uso de domínios que imitam outros, frequentemente através de erros de escrita ou pequenas alterações.

---

## 📱 Smishing (phishing por SMS)

### Pretextos comuns
```
- Notificação de entrega de encomenda
- Alerta de segurança bancária
- Prémio ou vitória em lotaria
- Verificação de conta
- Código de autenticação de dois fatores
```

### Serviços de envio de SMS
```
- Twilio
- Nexmo/Vonage
- Plivo
- AWS SNS
```

### Exemplos de mensagens
```
[FedEx] A sua encomenda está à espera. Confirme a entrega: http://short.url/abc

[BankName] Foi detetada atividade invulgar. Verifique agora: http://secure-link.com

[Netflix] O seu pagamento falhou. Atualize: http://netflix-billing.com
```

---

## 📞 Vishing (phishing por voz)

### Cenários comuns
```
□ Apoio informático que precisa de repor uma palavra-passe
□ Departamento de fraude de um banco
□ Organismo público (IRS, repartição de finanças)
□ Burla de apoio técnico
□ Pedido urgente do CEO ou CFO
□ Verificação de fornecedor
```

No exemplo, IRS refere-se ao organismo fiscal dos Estados Unidos.

### Exemplo de guião de vishing
```
«Olá, fala [Name] do apoio informático. Detetámos atividade
invulgar na sua conta e precisamos de verificar as suas
credenciais para evitar uma falha de segurança. Pode
confirmar o seu nome de utilizador e a palavra-passe atual
para eu validar a sua conta?»
```

### Ferramentas
```bash
# Falsificação da identificação de chamada (caller ID spoofing)
- SpoofCard
- SpoofTel

# Modificadores de voz
- Voicemod
- Clownfish
```

---

## 🎯 SET (Social Engineering Toolkit)

### Instalação
```bash
git clone https://github.com/trustedsec/social-engineer-toolkit
cd social-engineer-toolkit
pip install -r requirements.txt
sudo python setoolkit
```

### Opções do menu
```
1) Spear-Phishing Attack Vectors
2) Website Attack Vectors
3) Infectious Media Generator
4) Create a Payload and Listener
5) Mass Mailer Attack
```

As opções originais correspondem a: 1) vetores de spear phishing; 2) vetores de ataque por sites; 3) geração de suportes infetados; 4) criação de um payload (código ou conteúdo de ataque) e de um listener (processo à escuta); 5) envio massivo de emails.

### Recolha de credenciais
```bash
# No SET:
1) Social-Engineering Attacks
2) Website Attack Vectors
3) Credential Harvester Attack Method
2) Site Cloner
# Introduzir o URL a clonar (por exemplo, https://login.microsoft.com)
# O SET aloja o site falso e recolhe credenciais
```

Nesta sequência, as opções são «Ataques de engenharia social», «Vetores de ataque por sites», «Método de recolha de credenciais» e «Clonador de sites»; os textos do menu são preservados para corresponder à interface.

---

## 📝 Modelos de emails de phishing

### Reposição de palavra-passe
```
Assunto: [AÇÃO NECESSÁRIA] Pedido de reposição de palavra-passe

Caro/a {Name},

Recebemos um pedido de reposição da sua palavra-passe. Se
não fez este pedido, clique abaixo para proteger a sua
conta imediatamente.

[Proteger a minha conta]

Esta ligação expira dentro de 24 horas.

Com os melhores cumprimentos,
Equipa de Segurança
```

### Apoio informático
```
Assunto: Manutenção programada do sistema — Ação necessária

Caro/a {Name},

A nossa equipa de informática vai realizar uma manutenção
programada. Para garantir o acesso sem interrupções,
verifique as suas credenciais:

[Verificar agora]

A falta de verificação poderá resultar no bloqueio da conta.

Departamento de Informática
```

### Entrega de encomenda
```
Assunto: Não foi possível entregar a sua encomenda

Não foi possível entregar a sua encomenda da Amazon.
Por favor, confirme a morada de entrega:

[Confirmar entrega]

Número de seguimento: {Random}

Obrigado,
Equipa de Entregas
```

---

## 📋 Lista de verificação da campanha de phishing

```markdown
Antes da campanha:
□ Definir o âmbito e os alvos (teste autorizado)
□ Recolher os endereços de email dos alvos
□ Registar um domínio semelhante
□ Preparar a infraestrutura SMTP
□ Configurar SPF/DKIM, se possível
□ Criar a página de destino
□ Criar o modelo de email

Execução da campanha:
□ Testar a entrega dos emails (filtros de spam)
□ Importar a lista de alvos
□ Agendar a campanha
□ Acompanhar as taxas de entrega
□ Acompanhar aberturas, cliques e submissões

Após a campanha:
□ Gerar relatórios
□ Analisar as taxas de cliques
□ Rever os dados recolhidos
□ Identificar necessidades de formação
□ Documentar os resultados
```

---

## 🔗 Guias de referência relacionados

- [Pretexting](./Pretexting.pt.md)
- [OSINT — Google Dorking](../../Google-Dorking/README.md)

---

**Voltar à visão geral:** [🎭 Engenharia social](./README.pt.md)
