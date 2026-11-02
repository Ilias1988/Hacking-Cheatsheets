# 📊 Modello di sintesi per la direzione

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Executive-Summary-Template.md) • [🇹🇷 Türkçe Sürüm](Executive-Summary-Template.tr.md) • [🇬🇷 Ελληνικά](Executive-Summary-Template.el.md)

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

> **Scopo:** una sintesi concisa e non tecnica destinata ai dirigenti, ai membri del consiglio di amministrazione e ai responsabili aziendali.

> **Nota sui dati di esempio:** punteggi, grafici, percentuali, costi, scadenze e confronti di settore sono illustrativi; non costituiscono misure o stime validate. Le barre non sono sempre proporzionali ai valori indicati. La scala 70/100 richiede una metodologia esplicita e non è un punteggio CVSS. Sostituisci i valori con dati motivati, indicandone fonti e incertezza. Nella tabella «Costo dell'inazione», gli importi negativi della colonna «Risparmio» provengono dall'originale, che non ne definisce la base di calcolo: chiariscila prima dell'uso.

---

## 📋 Struttura della sintesi per la direzione

````markdown
# SINTESI DIREZIONALE DELL'ASSESSMENT DI SICUREZZA

**Cliente:** [Nome dell'azienda]
**Tipo di assessment:** [Penetration test / Audit di sicurezza / Valutazione delle vulnerabilità]
**Data:** [Periodo dell'assessment]
**Classificazione:** RISERVATO

---

## 1. Scopo e ambito

[La tua azienda] è stata incaricata di condurre un [tipo di assessment] di [Nome dell'azienda],
relativo a [descrivere l'ambito in termini aziendali]. L'obiettivo era valutare il livello di
sicurezza e identificare i rischi potenziali per le attività aziendali.

**L'ambito comprendeva:**
- [Sistema aziendale 1]
- [Sistema aziendale 2]
- [Rete/Infrastruttura]

---

## 2. Valutazione complessiva della sicurezza

┌─────────────────────────────────────────────────────┐
│                                                     │
│         LIVELLO DI RISCHIO COMPLESSIVO: ALTO         │
│                                                     │
│    ████████████████████░░░░░░░░░░  70/100            │
│                                                     │
│    Richiede attenzione immediata                    │
│                                                     │
└─────────────────────────────────────────────────────┘

| Livello | Definizione |
|--------|------------|
| 🔴 **Critico** | È necessario agire immediatamente. Alta probabilità di violazione. |
| 🟠 **Alto** | Vulnerabilità significative. Sono necessari interventi urgenti. |
| 🟡 **Medio** | Rischio moderato. Pianificare la correzione entro 30-60 giorni. |
| 🟢 **Basso** | Problemi minori. Risolvere durante la manutenzione ordinaria. |

---

## 3. Riepilogo dei risultati principali

| Gravità | Numero | Esempi |
|----------|-------|----------|
| 🔴 Critica | X | Accesso al database, bypass dell'autenticazione |
| 🟠 Alta | X | Esposizione di dati, escalation dei privilegi |
| 🟡 Media | X | Problemi di configurazione, accesso limitato ai dati |
| 🟢 Bassa | X | Raccomandazioni sulle buone pratiche |
| **Totale** | **X** | |

### Distribuzione dei risultati

```
Critica   ████████░░░░░░░░░░░░  X risultati (XX%)
Alta      ██████████████░░░░░░  X risultati (XX%)
Media     ████████████░░░░░░░░  X risultati (XX%)
Bassa     ████░░░░░░░░░░░░░░░░  X risultati (XX%)
```

---

## 4. Valutazione dell'impatto aziendale

### Rischio finanziario
| Categoria di rischio | Impatto potenziale | Probabilità |
|---------------|------------------|------------|
| Violazione dei dati | $X - $X milioni | Alta |
| Interruzione delle attività | $X/giorno di indisponibilità | Media |
| Sanzioni normative | $X (GDPR/PCI-DSS) | Alta |
| Danno reputazionale | Significativo | Alta |

### Visualizzazione del rischio

```
                    PROBABILITÀ
              Bassa    Media     Alta
         ┌─────────┬─────────┬─────────┐
    Alto │  Medio  │  Alto   │ Critico │  IMPATTO
         ├─────────┼─────────┼─────────┤
   Medio │  Basso  │  Medio  │  Alto   │
         ├─────────┼─────────┼─────────┤
   Basso │  Basso  │  Basso  │  Medio  │
         └─────────┴─────────┴─────────┘
```

### Principali rischi aziendali identificati

1. **Esposizione dei dati dei clienti**
   - X record dei clienti potenzialmente accessibili
   - Implicazioni relative al GDPR/alla normativa sulla privacy

2. **Continuità operativa**
   - Sistemi essenziali vulnerabili a interruzioni
   - Possibili X ore/giorni di indisponibilità

3. **Proprietà intellettuale**
   - [Rischio specifico per i dati proprietari]

---

## 5. Risultati critici (richiedono un'azione immediata)

### Risultato 1: [Titolo incentrato sull'impatto aziendale]
> **Rischio:** Un attaccante potrebbe [impatto aziendale in parole semplici]
>
> **Ambito interessato:** [Sistema/Processo aziendale]
>
> **Raccomandazione:** [Correzione ad alto livello]

### Risultato 2: [Titolo]
> **Rischio:** [Impatto aziendale]
>
> **Ambito interessato:** [Sistema]
>
> **Raccomandazione:** [Correzione]

---

## 6. Raccomandazioni con priorità

### Immediate (0-30 giorni)
| Priorità | Azione | Investimento | Riduzione del rischio |
|----------|--------|------------|----------------|
| 1 | [Azione 1] | $ | Alta |
| 2 | [Azione 2] | $$ | Alta |
| 3 | [Azione 3] | $ | Media |

### A breve termine (30-90 giorni)
| Priorità | Azione | Investimento | Riduzione del rischio |
|----------|--------|------------|----------------|
| 4 | [Azione 4] | $$ | Media |
| 5 | [Azione 5] | $$$ | Media |

### A lungo termine (90+ giorni)
| Priorità | Azione | Investimento | Riduzione del rischio |
|----------|--------|------------|----------------|
| 6 | [Azione 6] | $$$ | Bassa |
| 7 | [Azione 7] | $$$$ | Strategica |

---

## 7. Investimento e riduzione del rischio

```
    Riduzione                                   Investimento
    del rischio                                 richiesto
    ▲
    │     ┌─────┐
100%│     │ P1  │ ← Risultati rapidi              $
    │     └─────┘   (Alto valore, basso costo)
    │
 75%│        ┌─────┐
    │        │ P2  │                               $$
    │        └─────┘
    │
 50%│              ┌─────┐
    │              │ P3  │                         $$$
    │              └─────┘
    │
 25%│                    ┌─────┐
    │                    │ P4  │                   $$$$
    │                    └─────┘
    └────────────────────────────────────────►
         $      $$      $$$     $$$$
                   Budget
```

---

## 8. Confronto della maturità della sicurezza

### Situazione attuale e riferimento di settore

| Area | Attuale | Media di settore | Obiettivo |
|--------|---------|--------------|--------|
| Controllo degli accessi | ██░░░░░░░░ 20% | ████████░░ 80% | ████████░░ 80% |
| Protezione dei dati | ████░░░░░░ 40% | ███████░░░ 70% | ████████░░ 85% |
| Monitoraggio | ███░░░░░░░ 30% | ██████░░░░ 60% | ████████░░ 80% |
| Risposta agli incidenti | █░░░░░░░░░ 10% | █████░░░░░ 50% | ███████░░░ 70% |

---

## 9. Conclusione

L'attuale livello di sicurezza di [Nome dell'azienda] presenta un rischio **[ALTO/MEDIO/BASSO]** per
le attività aziendali. L'assessment ha identificato **X** vulnerabilità, tra cui
**X criticità** che richiedono attenzione immediata.

**Punti principali:**
1. [Punto più importante per la direzione]
2. [Secondo punto più importante]
3. [Terzo punto più importante]

**Investimento raccomandato:** $XXX,XXX - $XXX,XXX nell'arco di 12 mesi

**Risultato atteso:** riduzione del rischio di sicurezza del X%

---

## 10. Prossimi passi

| Tempistica | Azione | Responsabile | Stato |
|----------|--------|-------|--------|
| Immediata | Affrontare i risultati critici | Team di sicurezza | In attesa |
| Settimana 1 | Esaminare il report dettagliato | Direttore IT | In attesa |
| Settimana 2 | Approvare il budget per le correzioni | CFO | In attesa |
| Mese 1 | Avviare le correzioni | Team di sicurezza | In attesa |
| Mese 3 | Verificare nuovamente i risultati critici | [Fornitore] | In attesa |

---

**Informazioni di contatto:**
[Nome della tua azienda]
[Nome del consulente responsabile]
[Email] | [Telefono]

---

*Questo documento è riservato ed è destinato esclusivamente al personale autorizzato.*
````

> **Nota terminologica:** nell'esempio «GDPR/PCI-DSS» sono raggruppati obblighi di natura diversa. Prima di usare il modello, distingui le sanzioni normative dalle eventuali conseguenze contrattuali e verifica quali requisiti si applicano al cliente.

---

## 🎯 Sintesi per la direzione in una pagina

```markdown
┌──────────────────────────────────────────────────────────────────────────┐
│                 SINTESI DELL'ASSESSMENT DI SICUREZZA                       │
│                         [Nome dell'azienda]                               │
│                              [Data]                                      │
├──────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  VALUTAZIONE COMPLESSIVA        RIEPILOGO DEI RISULTATI                     │
│  ┌───────────┐                 ┌──────────────────────────┐               │
│  │           │                 │ 🔴 Critica:      X       │               │
│  │  RISCHIO  │                 │ 🟠 Alta:         X       │               │
│  │   ALTO    │                 │ 🟡 Media:        X       │               │
│  │           │                 │ 🟢 Bassa:        X       │               │
│  └───────────┘                 │ Totale:         X       │               │
│                                └──────────────────────────┘               │
│                                                                          │
│  I 3 RISCHI PRINCIPALI                                                    │
│  ════════════════════                                                    │
│  1. [Rischio critico - descrizione in una riga]                            │
│  2. [Rischio alto - descrizione in una riga]                               │
│  3. [Rischio alto - descrizione in una riga]                               │
│                                                                          │
│  IMPATTO AZIENDALE                                                        │
│  ════════════════                                                        │
│  • Possibile violazione dei dati che interessa X clienti                  │
│  • Sanzioni normative fino a $X                                           │
│  • Rischio di interruzione delle attività per X giorni                    │
│                                                                          │
│  AZIONI IMMEDIATE RICHIESTE                                               │
│  ═════════════════════════                                               │
│  □ [Azione 1] - Costo stimato: $XX,XXX                                    │
│  □ [Azione 2] - Costo stimato: $XX,XXX                                    │
│  □ [Azione 3] - Costo stimato: $XX,XXX                                    │
│                                                                          │
│  INVESTIMENTO RACCOMANDATO: $XXX,XXX                                      │
│  RIDUZIONE ATTESA DEL RISCHIO: XX%                                        │
│                                                                          │
└──────────────────────────────────────────────────────────────────────────┘
```

---

## 📈 Elementi visivi per la direzione

### Grafico dell'andamento del rischio
```
Livello di rischio
    ▲
100 │
    │         ●─────Prima dell'assessment
 80 │         │
    │         │
 60 │         │
    │         ▼
 40 │              ○ ─ ─ Dopo le correzioni (obiettivo)
    │
 20 │
    │
  0 └───────────────────────────────────────────►
        Q1      Q2      Q3      Q4      Tempo
```

### Costo dell'inazione
```markdown
| Momento | Costo della violazione | Costo delle correzioni | Risparmio |
|----------|----------------|------------------|---------|
| Ora | $0 | $100K | N/A |
| Dopo una violazione | $1.5M | $300K | -$1.3M |
| Con assicurazione | $500K | $150K | -$350K |
```

---

## 💡 Consigli di scrittura per la direzione

### Cosa fare ✅
```
✓ Partire dall'impatto aziendale, non dai dettagli tecnici
✓ Usare un linguaggio semplice, senza gergo
✓ Fornire un'analisi chiara di costi e benefici
✓ Includere raccomandazioni concrete
✓ Limitare la sintesi a un massimo di 2-3 pagine
✓ Usare elementi visivi (grafici, diagrammi, tabelle)
✓ Evidenziare il ritorno sull'investimento (ROI) nella sicurezza
✓ Confrontare i risultati con i riferimenti di settore
```

### Cosa evitare ❌
```
✗ Non usare terminologia tecnica (CVE, CVSS, ecc.)
✗ Non elencare ogni risultato: concentrarsi sulle criticità
✗ Non fornire dettagli tecnici eccessivi
✗ Non dimenticare costi e tempistiche
✗ Non rendere il documento più lungo del necessario
✗ Non usare toni allarmistici senza proporre soluzioni
```

---

## 📋 Checklist della sintesi per la direzione

```markdown
## Prima della presentazione alla direzione

□ La sintesi non supera le 2-3 pagine
□ Non viene usato gergo tecnico
□ L'impatto aziendale è espresso chiaramente
□ Costi e tempistiche sono inclusi
□ Sono presenti elementi visivi (grafici/diagrammi)
□ Le raccomandazioni sono concrete
□ Il ROI delle correzioni è spiegato
□ È incluso un confronto con il settore
□ I prossimi passi sono definiti chiaramente
□ Sono fornite le informazioni di contatto
```

---

**Torna alla reportistica:** [📝 Modelli di report](../../README.it.md#category-07)
