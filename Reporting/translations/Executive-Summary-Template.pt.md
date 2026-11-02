# 📊 Modelo de resumo executivo

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Executive-Summary-Template.tr.md) • [🇬🇷 Ελληνικά](Executive-Summary-Template.el.md)

```
███████╗██╗  ██╗███████╗ ██████╗██╗   ██╗████████╗██╗██╗   ██╗███████╗
██╔════╝╚██╗██╔╝██╔════╝██╔════╝██║   ██║╚══██╔══╝██║██║   ██║██╔════╝
█████╗   ╚███╔╝ █████╗  ██║     ██║   ██║   ██║   ██║██║   ██║█████╗  
██╔══╝   ██╔██╗ ██╔══╝  ██║     ██║   ██║   ██║   ██║╚██╗ ██╔╝██╔══╝  
███████╗██╔╝ ██╗███████╗╚██████╗╚██████╔╝   ██║   ██║ ╚████╔╝ ███████╗
╚══════╝╚═╝  ╚═╝╚══════╝ ╚═════╝ ╚═════╝    ╚═╝   ╚═╝  ╚═══╝  ╚══════╝
███████╗██╗   ██╗███╗   ███╗███╗   ███╗ █████╗ ██████╗ ██╗   ██╗
██╔════╝██║   ██║████╗ ████║████╗ ████║██╔══██╗██╔══██╗╚██╗ ██╔╝
███████╗██║   ██║██╔████╔██║██╔████╔██║███████║██████╔╝ ╚████╔╝ 
╚════██║██║   ██║██║╚██╔╝██║██║╚██╔╝██║██╔══██║██╔══██╗  ╚██╔╝  
███████║╚██████╔╝██║ ╚═╝ ██║██║ ╚═╝ ██║██║  ██║██║  ██║   ██║   
╚══════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   
```

> **Finalidade:** Um resumo conciso, sem linguagem técnica, destinado à direção executiva, ao conselho de administração e às partes interessadas da gestão.

> **Como utilizar:** este é um modelo, não um relatório de resultados reais. Os campos entre colchetes são instruções de preenchimento traduzidas; substitui-os pela informação do caso. Os marcadores técnicos e valores literais dos exemplos foram preservados. PoC significa prova de conceito; payload é a carga de teste; endpoint é um ponto de acesso de uma aplicação. Os endereços, valores, versões e provas apresentados são exemplos. Caminhos como `./evidence/vuln-001-sqli.png` e `attachment_url` são marcadores para provas a fornecer, não ficheiros incluídos.

---

## 📋 Estrutura do resumo executivo

> **Nota sobre as ilustrações:** `70/100`, percentagens, médias do setor, custos, poupanças e reduções de risco são valores fictícios preservados do original. Não constituem benchmarks nem cálculos validados; explicita o método, as fontes e as hipóteses antes de os utilizar. Os prazos de correção também são exemplos a acordar no contexto da organização.

````markdown
# RESUMO EXECUTIVO DA AVALIAÇÃO DE SEGURANÇA

**Cliente:** [Nome da empresa]
**Tipo de avaliação:** [Teste de intrusão / Auditoria de segurança / Avaliação de vulnerabilidades]
**Data:** [Período da avaliação]
**Classificação:** CONFIDENCIAL

---

## 1. Objetivo e âmbito

[A tua empresa] foi contratada para realizar [tipo de avaliação] de [Nome da empresa], abrangendo 
[descrever o âmbito em termos de negócio]. O objetivo foi avaliar o estado de segurança 
e identificar potenciais riscos para as operações da organização.

**O âmbito incluiu:**
- [Sistema de negócio 1]
- [Sistema de negócio 2]
- [Rede/Infraestrutura]

---

## 2. Classificação global da segurança

┌─────────────────────────────────────────────────────┐
│                                                     │
│            CLASSIFICAÇÃO GLOBAL DO RISCO: ELEVADA   │
│                                                     │
│    ████████████████████░░░░░░░░░░  70/100          │
│                                                     │
│    É necessária atenção imediata                    │
│                                                     │
└─────────────────────────────────────────────────────┘

| Classificação | Definição |
|--------|------------|
| 🔴 **Crítica** | É necessária ação imediata. Probabilidade elevada de violação de dados. |
| 🟠 **Elevada** | Vulnerabilidades significativas. É necessária correção urgente. |
| 🟡 **Média** | Risco moderado. Planear a correção num prazo de 30-60 dias. |
| 🟢 **Baixa** | Problemas menores. Resolver durante a manutenção normal. |

---

## 3. Resumo dos principais resultados

| Gravidade | Quantidade | Exemplos |
|----------|-------|----------|
| 🔴 Crítica | X | Acesso à base de dados, contorno da autenticação |
| 🟠 Elevada | X | Exposição de dados, escalada de privilégios |
| 🟡 Média | X | Problemas de configuração, acesso limitado a dados |
| 🟢 Baixa | X | Recomendações de boas práticas |
| **Total** | **X** | |

### Distribuição dos resultados

```
Crítica   ████████░░░░░░░░░░░░  X resultados (XX%)
Elevada   ██████████████░░░░░░  X resultados (XX%)
Média     ████████████░░░░░░░░  X resultados (XX%)
Baixa     ████░░░░░░░░░░░░░░░░  X resultados (XX%)
```

---

## 4. Avaliação do impacto no negócio

### Risco financeiro
| Categoria de risco | Impacto potencial | Probabilidade |
|---------------|------------------|------------|
| Violação de dados | $X - $X milhões | Elevada |
| Interrupção da atividade | $X/dia de indisponibilidade | Média |
| Multas regulamentares | $X (GDPR/PCI-DSS) | Elevada |
| Danos reputacionais | Significativo | Elevada |

### Visualização do risco

```
                    PROBABILIDADE
             Baixa       Média      Elevada
         ┌───────────┬───────────┬───────────┐
 Elevada │   Média   │  Elevada  │  Crítica  │  IMPACTO
         ├───────────┼───────────┼───────────┤
   Média │   Baixa   │   Média   │  Elevada  │
         ├───────────┼───────────┼───────────┤
   Baixa │   Baixa   │   Baixa   │   Média   │
         └───────────┴───────────┴───────────┘
```

### Principais riscos para o negócio identificados

1. **Exposição de dados dos clientes**
   - X registos de clientes potencialmente acessíveis
   - Implicações ao nível do RGPD/legislação de privacidade

2. **Continuidade da atividade**
   - Sistemas essenciais vulneráveis a interrupções
   - Possibilidade de X horas/dias de indisponibilidade

3. **Propriedade intelectual**
   - [Risco específico para dados proprietários]

---

## 5. Resultados críticos (ação imediata necessária)

### Resultado 1: [Título — com foco no impacto no negócio]
> **Risco:** Um atacante poderia [impacto no negócio em linguagem simples]
>
> **Afetado:** [Sistema/processo de negócio]
>
> **Recomendação:** [Correção em termos gerais]

### Resultado 2: [Título]
> **Risco:** [Impacto no negócio]
>
> **Afetado:** [Sistema]
>
> **Recomendação:** [Correção]

---

## 6. Recomendações por prioridade

### Imediato (0-30 dias)
| Prioridade | Ação | Investimento | Redução do risco |
|----------|--------|------------|----------------|
| 1 | [Ação 1] | $ | Elevada |
| 2 | [Ação 2] | $$ | Elevada |
| 3 | [Ação 3] | $ | Média |

### Curto prazo (30-90 dias)
| Prioridade | Ação | Investimento | Redução do risco |
|----------|--------|------------|----------------|
| 4 | [Ação 4] | $$ | Média |
| 5 | [Ação 5] | $$$ | Média |

### Longo prazo (90+ dias)
| Prioridade | Ação | Investimento | Redução do risco |
|----------|--------|------------|----------------|
| 6 | [Ação 6] | $$$ | Baixa |
| 7 | [Ação 7] | $$$$ | Estratégico |

---

## 7. Investimento e redução do risco

```
    Redução do risco                             Investimento
                                                necessário
    ▲                                           
    │     ┌─────┐                               
100%│     │ P1  │ ← Resultados rápidos                  $
    │     └─────┘   (Valor elevado, custo baixo)      
    │                                           
 75%│        ┌─────┐                            
    │        │ P2  │                            $$
    │        └─────┘                            
    │                                           
 50%│              ┌─────┐                      
    │              │ P3  │                      $$$
    │              └─────┘                      
    │                                           
 25%│                    ┌─────┐                
    │                    │ P4  │                $$$$
    │                    └─────┘                
    └────────────────────────────────────────► 
         $      $$      $$$     $$$$
                   Orçamento
```

---

## 8. Comparação da maturidade de segurança

### Situação atual e referência do setor

| Domínio | Atual | Média do setor | Objetivo |
|--------|---------|--------------|--------|
| Controlo de acesso | ██░░░░░░░░ 20% | ████████░░ 80% | ████████░░ 80% |
| Proteção de dados | ████░░░░░░ 40% | ███████░░░ 70% | ████████░░ 85% |
| Monitorização | ███░░░░░░░ 30% | ██████░░░░ 60% | ████████░░ 80% |
| Resposta a incidentes | █░░░░░░░░░ 10% | █████░░░░░ 50% | ███████░░░ 70% |

---

## 9. Conclusão

O estado atual de segurança de [Nome da empresa] apresenta um risco **[ELEVADO/MÉDIO/BAIXO]** para 
as operações da organização. A avaliação identificou **X** vulnerabilidades, incluindo 
**X problemas críticos** que exigem atenção imediata.

**Conclusões principais:**
1. [Ponto mais importante para a direção]
2. [Segundo ponto mais importante]
3. [Terceiro ponto mais importante]

**Investimento recomendado:** $XXX,XXX - $XXX,XXX ao longo de 12 meses

**Resultado esperado:** Redução de X% no risco de segurança

---

## 10. Próximos passos

| Cronologia | Ação | Responsável | Estado |
|----------|--------|-------|--------|
| Imediato | Resolver os resultados críticos | Equipa de segurança | Pendente |
| Semana 1 | Rever o relatório detalhado | Diretor de TI | Pendente |
| Semana 2 | Aprovar o orçamento de correção | CFO | Pendente |
| Mês 1 | Iniciar a correção | Equipa de segurança | Pendente |
| Mês 3 | Voltar a testar os resultados críticos | [Fornecedor] | Pendente |

---

**Informações de contacto:**
[Nome da tua empresa]
[Nome do consultor responsável]
[Correio eletrónico] | [Telefone]

---

*Este documento é confidencial e destina-se apenas a pessoal autorizado.*
````

---

## 🎯 Resumo executivo de uma página

````markdown
┌────────────────────────────────────────────────────────────────────┐
│                    RESUMO DA AVALIAÇÃO DE SEGURANÇA                 │
│                         [Nome da empresa]                              │
│                           [Data]                                    │
├────────────────────────────────────────────────────────────────────┤
│                                                                    │
│  AVALIAÇÃO GLOBAL        RESUMO DOS RESULTADOS                     │
│  ┌───────────┐           ┌──────────────────────────┐              │
│  │           │           │ 🔴 Crítica:     X        │              │
│  │   ELEVADO │           │ 🟠 Elevada:         X    │              │
│  │   RISCO   │           │ 🟡 Média:       X        │              │
│  │           │           │ 🟢 Baixa:          X     │              │
│  └───────────┘           │ Total:           X       │              │
│                          └──────────────────────────┘              │
│                                                                    │
│  3 PRINCIPAIS RISCOS                                               │
│  ═══════════                                                       │
│  1. [Risco crítico — descrição numa linha]                         │
│  2. [Risco elevado — descrição numa linha]                             │
│  3. [Risco elevado — descrição numa linha]                             │
│                                                                    │
│  IMPACTO NO NEGÓCIO                                                │
│  ═══════════════                                                   │
│  • Potencial violação de dados que afeta X clientes                │
│  • Multas regulamentares até $X                                    │
│  • Risco de interrupção da atividade durante X dias                │
│                                                                    │
│  AÇÕES IMEDIATAS NECESSÁRIAS                                       │
│  ══════════════════════════                                        │
│  □ [Ação 1] - Custo estimado: $XX,XXX                            │
│  □ [Ação 2] - Custo estimado: $XX,XXX                            │
│  □ [Ação 3] - Custo estimado: $XX,XXX                            │
│                                                                    │
│  INVESTIMENTO RECOMENDADO: $XXX,XXX                                │
│  REDUÇÃO DO RISCO ESPERADA: XX%                                    │
│                                                                    │
└────────────────────────────────────────────────────────────────────┘
````

---

## 📈 Elementos visuais para a direção

### Gráfico da evolução do risco
```
Nível de risco
    ▲
100 │
    │         ●─────Antes da avaliação
 80 │         │
    │         │
 60 │         │
    │         ▼
 40 │              ○ ─ ─ Após a correção (objetivo)
    │
 20 │
    │
  0 └───────────────────────────────────────────►
        Q1      Q2      Q3      Q4      Tempo
```

### Custo da inação
````markdown
| Cronologia | Custo de uma violação de dados | Custo de correção | Poupança |
|----------|----------------|------------------|---------|
| Agora | $0 | $100K | N/A |
| Após a violação de dados | $1.5M | $300K | -$1.3M |
| Com seguro | $500K | $150K | -$350K |
````

---

## 💡 Conselhos para escrever para a direção

### Boas práticas ✅
```
✓ Começa pelo impacto no negócio, evitando detalhes técnicos
✓ Utiliza linguagem simples, sem jargão
✓ Apresenta uma análise clara de custos/benefícios
✓ Inclui recomendações aplicáveis
✓ Limita o resumo a 2-3 páginas
✓ Utiliza elementos visuais (gráficos, diagramas, tabelas)
✓ Destaca o retorno do investimento (ROI) em segurança
✓ Compara com referências do setor
```

### Práticas a evitar ❌
```
✗ Não utilizes terminologia técnica (CVE, CVSS, etc.)
✗ Não listes todos os resultados; concentra-te nos críticos
✗ Não apresentes detalhes técnicos excessivos
✗ Não te esqueças de incluir custos e prazos
✗ Não escrevas mais do que o necessário
✗ Não recorras a mensagens alarmistas sem soluções
```

---

## 📋 Lista de verificação do resumo executivo

````markdown
## Antes de apresentar à direção

□ O resumo tem, no máximo, 2-3 páginas
□ Não é utilizado jargão técnico
□ O impacto no negócio é apresentado com clareza
□ São incluídos custos e prazos
□ Estão presentes elementos visuais (gráficos/diagramas)
□ As recomendações são aplicáveis
□ O retorno do investimento na correção é explicado
□ É incluída uma comparação com o setor
□ Os próximos passos estão claramente definidos
□ São fornecidas informações de contacto
````

---

**Voltar aos relatórios:** [📝 Modelos de relatórios](../../README.md#-reporting-templates)
