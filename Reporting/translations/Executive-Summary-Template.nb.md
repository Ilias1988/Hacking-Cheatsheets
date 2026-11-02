# 📊 Mal for ledelsessammendrag

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

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

> **Formål:** Et kortfattet, ikke-teknisk sammendrag for toppledere, styremedlemmer og andre beslutningstakere.
> **Om malen:** Funn, poengsummer, frister, priser og versjoner er illustrative eksempler som må erstattes med dokumenterte opplysninger fra det konkrete oppdraget. Engelske plassholdere er bevart slik at malfeltene kan kjennes igjen. Diagrammenes risikoprosenter, bransjereferanser og økonomiske tall er ikke faktiske målinger eller garantier. Bruk en uttrykkelig definert risikomodell og dokumenterte forutsetninger.


---

## 📋 Oppbygning av ledelsessammendrag

````markdown
# LEDELSESSAMMENDRAG AV SIKKERHETSVURDERINGEN

**Kunde:** [Company Name]
**Vurderingstype:** [Penetration Test / Security Audit / Vulnerability Assessment]
**Dato:** [Assessment Period]
**Klassifisering:** KONFIDENSIELT

---

## 1. Formål og omfang

[Your Company] fikk i oppdrag å gjennomføre en [type of assessment] for [Company Name] av
[describe scope in business terms]. Målet var å vurdere sikkerhetstilstanden
og identifisere mulige risikoer for virksomhetens drift.

**Innenfor omfanget:**
- [Business system 1]
- [Business system 2]
- [Network/Infrastructure]

---

## 2. Samlet sikkerhetsvurdering

┌─────────────────────────────────────────────────────┐
│                                                     │
│            SAMLET RISIKO: HØY                       │
│                                                     │
│    ████████████████████░░░░░░░░░░  70/100          │
│                                                     │
│    Krever umiddelbar oppfølging                     │
│                                                     │
└─────────────────────────────────────────────────────┘

| Nivå | Definisjon |
|--------|------------|
| 🔴 **Kritisk** | Umiddelbare tiltak kreves. Stor sannsynlighet for sikkerhetsbrudd. |
| 🟠 **Høy** | Betydelige sårbarheter. Utbedring haster. |
| 🟡 **Middels** | Moderat risiko. Planlegg utbedring innen 30-60 dager. |
| 🟢 **Lav** | Mindre problemer. Håndteres som del av normalt vedlikehold. |

---

## 3. Sammendrag av de viktigste funnene

| Alvorlighetsgrad | Antall | Eksempler |
|----------|-------|----------|
| 🔴 Kritisk | X | Databasetilgang, omgåelse av autentisering |
| 🟠 Høy | X | Dataeksponering, rettighetseskalering |
| 🟡 Middels | X | Konfigurasjonsfeil, begrenset datatilgang |
| 🟢 Lav | X | Anbefalinger om god praksis |
| **Totalt** | **X** | |

### Fordeling av funn

```
Kritisk  ████████░░░░░░░░░░░░  X funn (XX%)
Høy      ██████████████░░░░░░  X funn (XX%)
Middels    ████████████░░░░░░░░  X funn (XX%)
Lav       ████░░░░░░░░░░░░░░░░  X funn (XX%)
```

---

## 4. Vurdering av konsekvenser for virksomheten

### Økonomisk risiko
| Risikokategori | Mulig konsekvens | Sannsynlighet |
|---------------|------------------|------------|
| Databrudd | $X - $X millioner | Høy |
| Driftsavbrudd | $X/dag med nedetid | Middels |
| Bøter og reaksjoner ved regelbrudd | $X (GDPR/PCI-DSS) | Høy |
| Omdømmeskade | Betydelig | Høy |

### Visualisering av risiko

```
                    SANNSYNLIGHET
              Lav    Middels    Høy
         ┌────────┬─────────┬────────┐
    Høy  │Middels │  Høy    │Kritisk │  UTFALL
         ├────────┼─────────┼────────┤
Middels  │  Lav   │Middels  │  Høy   │
         ├────────┼─────────┼────────┤
    Lav  │  Lav   │   Lav   │Middels │
         └────────┴─────────┴────────┘
```

### Viktige identifiserte virksomhetsrisikoer

1. **Eksponering av kundedata**
   - X kundeoppføringer kan være tilgjengelige
   - Konsekvenser knyttet til GDPR/personvernregler

2. **Kontinuitet i driften**
   - Sentrale systemer er utsatt for driftsavbrudd
   - Mulighet for X timer/dager med nedetid

3. **Immaterielle verdier**
   - [Specific risk to proprietary data]

---

## 5. Kritiske funn (krever umiddelbare tiltak)

### Funn 1: [Title - Business Impact Focus]
> **Risiko:** En angriper kan [business impact in plain language]
>
> **Berørt:** [Business system/process]
>
> **Anbefaling:** [High-level fix]

### Funn 2: [Title]
> **Risiko:** [Business impact]
>
> **Berørt:** [System]
>
> **Anbefaling:** [Fix]

---

## 6. Prioriterte anbefalinger

### Umiddelbart (0-30 dager)
| Prioritet | Tiltak | Investering | Risikoreduksjon |
|----------|--------|------------|----------------|
| 1 | [Action 1] | $ | Høy |
| 2 | [Action 2] | $$ | Høy |
| 3 | [Action 3] | $ | Middels |

### På kort sikt (30-90 dager)
| Prioritet | Tiltak | Investering | Risikoreduksjon |
|----------|--------|------------|----------------|
| 4 | [Action 4] | $$ | Middels |
| 5 | [Action 5] | $$$ | Middels |

### På lang sikt (90+ dager)
| Prioritet | Tiltak | Investering | Risikoreduksjon |
|----------|--------|------------|----------------|
| 6 | [Action 6] | $$$ | Lav |
| 7 | [Action 7] | $$$$ | Strategisk |

---

## 7. Investering sammenlignet med risikoreduksjon

```
    Risiko                                        Investering
    reduksjon                                   kreves
    ▲                                           
    │     ┌─────┐                               
100%│     │ P1  │← Raske gevinster $             
    │└─────┘ (Stor verdi, lav kostnad)          
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
                   Budsjett
```

---

## 8. Sammenligning av sikkerhetsmodenhet

### Nåværende nivå sammenlignet med bransjereferanse

| Område | Nåværende nivå | Bransjegjennomsnitt | Mål |
|--------|---------|--------------|--------|
| Tilgangskontroll | ██░░░░░░░░ 20% | ████████░░ 80% | ████████░░ 80% |
| Databeskyttelse | ████░░░░░░ 40% | ███████░░░ 70% | ████████░░ 85% |
| Overvåking | ███░░░░░░░ 30% | ██████░░░░ 60% | ████████░░ 80% |
| Hendelseshåndtering | █░░░░░░░░░ 10% | █████░░░░░ 50% | ███████░░░ 70% |

---

## 9. Konklusjon

Den nåværende sikkerhetstilstanden hos [Company Name] innebærer en **[HIGH/MEDIUM/LOW]** risiko for
virksomhetens drift. Vurderingen identifiserte **X** sårbarheter, inkludert
**X kritiske** problemer som krever umiddelbar oppfølging.

**Viktigste budskap:**
1. [Most important point for executives]
2. [Second most important point]
3. [Third most important point]

**Anbefalt investering:** $XXX,XXX - $XXX,XXX over 12 måneder

**Forventet resultat:** X% reduksjon i sikkerhetsrisiko

---

## 10. Neste steg

| Tidsplan | Tiltak | Ansvarlig | Status |
|----------|--------|-------|--------|
| Umiddelbart | Håndter kritiske funn | Sikkerhetsteam | Avventer |
| Uke 1 | Gjennomgå detaljrapporten | IT-direktør | Avventer |
| Uke 2 | Godkjenn budsjett for utbedring | CFO | Avventer |
| Måned 1 | Start utbedring | Sikkerhetsteam | Avventer |
| Måned 3 | Test kritiske funn på nytt | [Vendor] | Avventer |

---

**Kontaktinformasjon:**
[Your Company Name]
[Lead Consultant Name]
[Email] | [Phone]

---

*Dette dokumentet er konfidensielt og bare beregnet på autorisert personell.*
````

---

## 🎯 Ledelsessammendrag på én side

```markdown
┌────────────────────────────────────────────────────────────────────┐
│SAMMENDRAG AV SIKKERHETSVURDERING                                    │
│                         [Company Name]                              │
│                           [Date]                                    │
├────────────────────────────────────────────────────────────────────┤
│                                                                    │
│  SAMLET NIVÅ          FUNNSAMMENDRAG                               │
│  ┌───────────┐           ┌──────────────────────────┐              │
│  │           │           │ 🔴 Kritisk:     X        │              │
│  │   HØY     │           │ 🟠 Høy:         X        │              │
│  │RISIKO     │           │🟡 Middels: X             │              │
│  │           │           │ 🟢 Lav:          X       │              │
│  └───────────┘           │Totalt: X                 │              │
│                          └──────────────────────────┘              │
│                                                                    │
│DE 3 STØRSTE RISIKOENE                                              │
│  ═══════════                                                       │
│  1. [Critical risk - one line description]                         │
│  2. [High risk - one line description]                             │
│  3. [High risk - one line description]                             │
│                                                                    │
│VIRKSOMHETSKONSEKVENSER                                             │
│  ═══════════════                                                   │
│  • Mulig databrudd som berører X kunder                            │
│• Bøter ved regelbrudd opptil $X                                    │
│• Risiko for driftsavbrudd på X dager                               │
│                                                                    │
│  UMIDDELBARE TILTAK KREVES                                         │
│  ══════════════════════════                                        │
│□ [Action 1] - Anslått kostnad: $XX,XXX                             │
│□ [Action 2] - Anslått kostnad: $XX,XXX                             │
│□ [Action 3] - Anslått kostnad: $XX,XXX                             │
│                                                                    │
│  ANBEFALT INVESTERING: $XXX,XXX                                    │
│FORVENTET RISIKOREDUKSJON: XX%                                      │
│                                                                    │
└────────────────────────────────────────────────────────────────────┘
```

---

## 📈 Visuelle elementer for ledelsen

### Diagram over risikoutvikling
```
Risikonivå
    ▲
100 │
    │         ●─────Før vurdering    
 80 │         │
    │         │
 60 │         │
    │         ▼
 40 │              ○ ─ ─ Etter utbedring (mål)     
    │
 20 │
    │
  0 └───────────────────────────────────────────►
        Q1      Q2      Q3      Q4      Tid
```

### Kostnad ved å ikke handle
```markdown
| Tidspunkt | Kostnad ved databrudd | Utbedringskostnad | Besparelse |
|----------|----------------|------------------|---------|
| Nå | $0 | $100K | N/A |
| Etter databrudd | $1.5M | $300K | -$1.3M |
| Med forsikring | $500K | $150K | -$350K |
```

---

## 💡 Skrivetips for ledelsesrapporter

### Gjør dette ✅
```
✓ Start med konsekvensene for virksomheten fremfor tekniske detaljer
✓ Bruk enkelt språk uten fagsjargong
✓ Gi en tydelig kost/nytte-vurdering
✓ Ta med konkrete anbefalinger som kan gjennomføres
✓ Begrens sammendraget til maksimalt 2-3 sider
✓ Bruk visuelle elementer (diagrammer, grafer og tabeller)
✓ Fremhev avkastningen av sikkerhetsinvesteringen (ROI)
✓ Sammenlign med bransjereferanser
```

### Unngå dette ❌
```
✗ Ikke bruk teknisk terminologi (CVE, CVSS osv.)
✗ Ikke list opp alle funn – legg vekt på de kritiske
✗ Ikke ta med unødvendig mange tekniske detaljer
✗ Ikke glem kostnader og tidsplaner
✗ Ikke skriv mer enn nødvendig
✗ Ikke skap frykt uten å presentere løsninger
```

---

## 📋 Sjekkliste for ledelsessammendrag

```markdown
## Før presentasjon for ledelsen

□ Sammendraget er på maksimalt 2-3 sider
□ Ingen teknisk fagsjargong er brukt
□ Konsekvensene for virksomheten er tydelig beskrevet
□ Kostnader og tidsplan er tatt med
□ Visuelle elementer (diagrammer/grafer) er tatt med
□ Anbefalingene kan gjennomføres
□ Avkastningen av utbedringen (ROI) er forklart
□ Sammenligning med bransjen er tatt med
□ Neste steg er tydelig definert
□ Kontaktinformasjon er oppgitt
```

---

**Tilbake til rapportmalene:** [📝 Rapportmaler](../../README.md#-reporting-templates)
