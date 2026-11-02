# 🎭 Guia de referência de pretexting

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Pretexting.tr.md) • [🇬🇷 Ελληνικά](Pretexting.el.md)

```
██████╗ ██████╗ ███████╗████████╗███████╗██╗  ██╗████████╗██╗███╗   ██╗ ██████╗ 
██╔══██╗██╔══██╗██╔════╝╚══██╔══╝██╔════╝╚██╗██╔╝╚══██╔══╝██║████╗  ██║██╔════╝ 
██████╔╝██████╔╝█████╗     ██║   █████╗   ╚███╔╝    ██║   ██║██╔██╗ ██║██║  ███╗
██╔═══╝ ██╔══██╗██╔══╝     ██║   ██╔══╝   ██╔██╗    ██║   ██║██║╚██╗██║██║   ██║
██║     ██║  ██║███████╗   ██║   ███████╗██╔╝ ██╗   ██║   ██║██║ ╚████║╚██████╔╝
╚═╝     ╚═╝  ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 O que é o pretexting?

Pretexting é a criação de um cenário fictício (um pretexto) para abordar um alvo e obter informações ou levá-lo a realizar uma ação. Constitui a base da maioria dos ataques de engenharia social.

**Componentes fundamentais:**
- 🎭 **Persona** — A identidade fictícia que assumes
- 📖 **História** — O cenário ou motivo credível
- 🎯 **Objetivo** — O resultado que pretendes alcançar
- 🔧 **Adereços** — Materiais de apoio, como crachás e emails

---

## 👥 Identidades fictícias comuns

### Apoio informático
```
Papel: Técnico do serviço de apoio informático
Objetivo: Reposição de palavra-passe, acesso remoto, instalação de software

«Olá, fala o Mike da informática. Detetámos alguma atividade
invulgar na sua conta e precisamos de verificar as suas
credenciais para evitar que a conta seja bloqueada.»
```

### Fornecedor ou prestador de serviços
```
Papel: Fornecedor externo (climatização, segurança, limpeza)
Objetivo: Acesso físico, acesso à rede

«Venho da [Vendor Company] para a manutenção agendada.
Pode deixar-me entrar na sala dos servidores?»
```

### Novo funcionário
```
Papel: Funcionário recém-contratado que precisa de ajuda
Objetivo: Recolha de informações, acesso ao edifício

«Olá, comecei agora no marketing e estou com dificuldades
em aceder à unidade partilhada. Pode ajudar-me?»
```

### Membro da direção ou figura de autoridade
```
Papel: CEO, CFO, gestor de topo
Objetivo: Pedidos urgentes, transferências bancárias, acesso

«Isto é urgente. O CEO precisa deste relatório imediatamente
e preciso que ignore os procedimentos habituais.»
```

### Estafeta
```
Papel: FedEx, UPS, estafeta
Objetivo: Acesso físico, entrega de encomendas

«Tenho uma encomenda que precisa de assinatura.
Pode deixar-me entrar na receção?»
```

---

## 📋 Cenários de pretexting

### Pretextos por telefone

#### Reposição de palavra-passe pelo apoio informático
```
«Olá, fala [Name] do apoio informático. Detetámos que a sua
conta foi comprometida num incidente de segurança recente.
Preciso de verificar a sua identidade e de o ajudar a repor
a palavra-passe. Pode confirmar a sua palavra-passe atual
para eu validar que estou mesmo a falar consigo?»
```

#### Verificação de fornecedor
```
«Olá, estou a ligar do departamento de contas a pagar da
[Vendor]. Estamos a atualizar os nossos registos e precisamos
de verificar os dados bancários da sua empresa para o próximo
pagamento. Pode confirmar o número de conta que tem registado
para a nossa empresa?»
```

#### Inquérito dos recursos humanos
```
«Olá, estou a realizar um inquérito de satisfação dos
funcionários para os recursos humanos. O inquérito é anónimo,
mas preciso de verificar se trabalha na empresa. Pode indicar
o seu departamento e o nome do seu responsável?»
```

### Pretextos presenciais

#### Inspetor de segurança contra incêndios
```
«Olá, venho do serviço municipal de inspeção contra incêndios.
Estamos a realizar inspeções sem aviso prévio. Preciso de
acesso à sala dos servidores para verificar o sistema de
extinção de incêndios.»
```

#### Entrevista de emprego
```
«Venho para a entrevista das 14h (2 PM) com os recursos humanos.
Enquanto espero, pode indicar-me onde fica a casa de banho?»
(Aproveitar a oportunidade para observar o edifício)
```

#### Técnico de manutenção
```
«Recebemos uma chamada sobre uma fuga de água nesta zona.
Pode mostrar-me onde fica a sala dos servidores? Preciso
de verificar os tubos que passam por cima do teto.»
```

---

## 🧠 Princípios psicológicos

### Autoridade
```
✓ Usar cargos: «Diretor de informática», «Responsável de segurança»
✓ Vestir de acordo com o papel: fatos, uniformes, crachás
✓ Falar com confiança
✓ Referir políticas e procedimentos
```

### Urgência
```
✓ «Isto tem de ficar feito antes da reunião do CEO às 3»
✓ «O sistema vai bloquear o seu acesso dentro de 10 minutos»
✓ «Estamos a ser auditados e precisamos disto imediatamente»
✓ Criar pressão de tempo para impedir a verificação
```

### Reciprocidade
```
✓ Fazer primeiro um pequeno favor
✓ «Acabei de ajudar a resolver aquele problema da impressora...»
✓ «Trouxe café para todos...»
✓ Criar a obrigação de retribuir o favor
```

### Prova social
```
✓ «Todos os outros já fizeram isto»
✓ «O seu responsável, John, já aprovou isto»
✓ «Toda a equipa de marketing já preencheu o formulário»
✓ Referir outras pessoas que acederam ao pedido
```

### Simpatia
```
✓ Encontrar pontos em comum
✓ Espelhar a linguagem corporal
✓ Fazer elogios sinceros
✓ Criar uma relação de confiança antes de pedir algo
```

---

## 🔧 Adereços e materiais de apoio

### Adereços físicos
```
□ Crachás de identificação falsos
□ Cartões de visita
□ Prancheta com formulários de aparência oficial
□ Colete de alta visibilidade ou uniforme
□ Caixa de ferramentas ou mala de portátil
□ Emails ou cartas de autorização impressos
□ Documentação de fornecedor
```

### Adereços digitais
```
□ Identificação de chamada falsificada (caller ID spoofing)
□ Endereço de email falso (domínio semelhante)
□ Perfil falso no LinkedIn
□ Site da empresa clonado
□ Emails de autorização forjados
□ Convites falsos para reuniões
```

### Documentação
```
□ Número de pedido de assistência
□ Ordem de trabalho
□ Formulário de autorização
□ Número de referência
□ Nome do responsável (obtido através de OSINT)
```

---

## 🔍 OSINT para pretexting

OSINT (Open Source Intelligence) é a recolha e análise de informações disponíveis em fontes abertas.

### Pesquisa sobre o alvo
```bash
# LinkedIn para informações sobre funcionários
- Nomes, cargos, departamentos
- Estrutura hierárquica
- Publicações e atividade recentes

# Site da empresa
- Equipa de direção
- Organograma
- Localização dos escritórios
- Informações sobre fornecedores e parceiros

# Comunicados de imprensa
- Notícias recentes, aquisições
- Alterações na direção
```

### Informações úteis
```
□ Nomes e cargos dos funcionários
□ Estrutura dos departamentos
□ Nomes dos responsáveis
□ Formato dos emails (first.last@company.com)
□ Números de telefone
□ Localização dos escritórios
□ Relações com fornecedores
□ Acontecimentos recentes na empresa
```

---

## 📱 Engenharia social presencial

### Tailgating
```
Técnica: Seguir uma pessoa autorizada através de uma porta protegida

«Ah, obrigado, esqueci-me do crachá hoje!»
«Pode segurar a porta? Tenho as mãos ocupadas.»
«Estou aqui com [Name] de [Department].»
```

### Piggybacking
```
Técnica: Conseguir que uma pessoa autorizada permita a entrada

«Olá, venho da [Vendor]. Estão à minha espera, mas a pessoa
da receção teve de se ausentar. Pode deixar-me entrar?»
```

### Dumpster diving (pesquisa em resíduos)
```
Alvo: Documentos e equipamento descartados

□ Organogramas
□ Listas de funcionários
□ Documentos financeiros
□ Notas com palavras-passe
□ Equipamento antigo com dados
```

### Abandono de dispositivos USB (baiting)
```
Técnica: Deixar dispositivos USB infetados

Locais:
□ Parque de estacionamento
□ Zona da receção
□ Salas de pausa
□ Perto das zonas de fumadores

Ideias para etiquetas:
□ «Confidencial — Recursos Humanos»
□ «Informações salariais 2024»
□ «Lista de despedimentos»
□ «Fotografias privadas»
```

---

## 📝 Modelos de guiões

### Guião telefónico — Apoio informático
```
[Toque do telefone]
Alvo: «Estou?»
Tu: «Olá, estou a falar com [Target Name]?»
Alvo: «Sim.»
Tu: «Muito bem, fala [Your Name] da equipa de segurança
informática. Temos estado a acompanhar algumas tentativas
invulgares de início de sessão na sua conta a partir de um
endereço IP no estrangeiro. Queremos confirmar que não foi
uma ação sua. Tentou iniciar sessão a partir de
[Random Country] hoje?»
Alvo: «Não, não fui eu!»
Tu: «Foi o que pensei. Por segurança, preciso de verificar
a sua conta. Pode confirmar o seu nome de utilizador?
Vamos repor a sua palavra-passe para impedir o acesso
do atacante.»
```

### Guião de email — Pedido de um membro da direção
```
Assunto: Urgente — É necessária uma transferência bancária

Olá [Name],

Estou numa reunião e não posso ligar. Preciso que processe
uma transferência bancária urgente para uma aquisição
com um prazo muito apertado.

Por favor, transfira $45,000 para a seguinte conta:
[Details]

Explico tudo quando sair desta reunião. Por favor,
confirme quando a transferência estiver concluída.

[Executive Name]
Enviado do meu iPhone
```

---

## ⚠️ Considerações legais e éticas

### Regras do teste (Rules of Engagement)
```
□ Ter sempre autorização por escrito
□ Definir claramente o âmbito e os limites
□ Não aceder a contas pessoais
□ Não recorrer a chantagem ou coação
□ Documentar tudo
□ Ter contactos de emergência
□ Ter à mão a carta de autorização para o teste
```

### O que evitar
```
✗ Fazer-se passar por agentes das forças de segurança
✗ Fazer ameaças reais
✗ Aceder a sistemas fora do âmbito definido
✗ Roubar dinheiro ou bens reais
✗ Criar responsabilidade legal
✗ Causar danos psicológicos
```

---

## 📋 Lista de verificação de pretexting

```markdown
Preparação:
□ Pesquisar a organização alvo (OSINT)
□ Identificar pessoas com funções essenciais
□ Desenvolver a identidade fictícia e a respetiva história
□ Preparar materiais de apoio e adereços
□ Praticar o guião e as respostas
□ Ter histórias alternativas preparadas

Execução:
□ Manter a calma e a confiança
□ Controlar a conversa
□ Estar preparado para adaptar a abordagem se houver suspeitas
□ Não exagerar na persuasão nem fornecer demasiados pormenores
□ Saber quando interromper a tentativa

Após o teste:
□ Documentar o que funcionou e o que não funcionou
□ Proteger todos os dados recolhidos
□ Comunicar os resultados
□ Apresentar recomendações
```

---

## 🔗 Guias de referência relacionados

- [Phishing](./Phishing.pt.md)
- [OSINT — Google Dorking](../../Google-Dorking/README.md)

---

**Voltar à visão geral:** [🎭 Engenharia social](./README.pt.md)
