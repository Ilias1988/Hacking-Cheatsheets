# 📊 Vorlage für die Management-Zusammenfassung

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

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

> **Zweck:** Eine knappe, nichttechnische Zusammenfassung für Geschäftsleitung, Vorstandsmitglieder und weitere Verantwortliche im Management.

---

## 📋 Aufbau der Management-Zusammenfassung

````markdown
# MANAGEMENT-ZUSAMMENFASSUNG DER SICHERHEITSPRÜFUNG

**Auftraggeber:** [Unternehmensname]
**Art der Prüfung:** [Penetrationstest / Sicherheitsaudit / Schwachstellenbewertung]
**Datum:** [Prüfzeitraum]
**Einstufung:** VERTRAULICH

---

## 1. Zweck und Umfang

[Ihr Unternehmen] wurde mit einer [Art der Prüfung] bei [Unternehmensname] beauftragt; 
der Umfang umfasst [Prüfumfang aus geschäftlicher Sicht beschreiben]. Ziel war es, den Sicherheitszustand 
zu bewerten und mögliche Risiken für den Geschäftsbetrieb zu ermitteln.

**Der Umfang umfasste:**
- [Geschäftssystem 1]
- [Geschäftssystem 2]
- [Netzwerk/Infrastruktur]

---

## 2. Gesamteinstufung der Sicherheit

┌────────────────────────────────────────┐
│ GESAMTRISIKOEINSTUFUNG: HOCH           │
│                                        │
│ ████████████████████░░░░░░░░░░  70/100 │
│                                        │
│ Sofortiger Handlungsbedarf             │
└────────────────────────────────────────┘

| Einstufung | Definition |
|--------|------------|
| 🔴 **Kritisch** | Sofortiges Handeln erforderlich. Hohe Wahrscheinlichkeit einer Kompromittierung. |
| 🟠 **Hoch** | Erhebliche Schwachstellen. Dringende Behebung erforderlich. |
| 🟡 **Mittel** | Moderates Risiko. Behebung innerhalb von 30-60 Tagen planen. |
| 🟢 **Niedrig** | Geringfügige Probleme. Im Rahmen regulärer Wartung beheben. |

---

## 3. Zusammenfassung der wichtigsten Befunde

| Schweregrad | Anzahl | Beispiele |
|----------|-------|----------|
| 🔴 Kritisch | X | Datenbankzugriff, Umgehung der Authentifizierung |
| 🟠 Hoch | X | Datenoffenlegung, Rechteausweitung |
| 🟡 Mittel | X | Konfigurationsprobleme, eingeschränkter Datenzugriff |
| 🟢 Niedrig | X | Empfehlungen zu bewährten Sicherheitspraktiken |
| **Gesamt** | **X** | |

### Verteilung der Befunde

```
Kritisch  ████████░░░░░░░░░░░░  X Befunde (XX%)
Hoch      ██████████████░░░░░░  X Befunde (XX%)
Mittel    ████████████░░░░░░░░  X Befunde (XX%)
Niedrig       ████░░░░░░░░░░░░░░░░  X Befunde (XX%)
```

---

## 4. Bewertung der geschäftlichen Auswirkungen

### Finanzielles Risiko
| Risikokategorie | Mögliche Auswirkungen | Eintrittswahrscheinlichkeit |
|---------------|------------------|------------|
| Datenkompromittierung | $X - $X Millionen | Hoch |
| Betriebsunterbrechung | $X/Tag Ausfallzeit | Mittel |
| Bußgelder | $X (GDPR/PCI-DSS) | Hoch |
| Reputationsschaden | Erheblich | Hoch |

### Risikodarstellung

```
EINTRITTSWAHRSCHEINLICHKEIT (Spalten) / AUSWIRKUNG (Zeilen)
┌────────────┬─────────┬─────────┬──────────┐
│ Auswirkung │ Niedrig │ Mittel  │ Hoch     │
├────────────┼─────────┼─────────┼──────────┤
│ Hoch       │ Mittel  │ Hoch    │ Kritisch │
├────────────┼─────────┼─────────┼──────────┤
│ Mittel     │ Niedrig │ Mittel  │ Hoch     │
├────────────┼─────────┼─────────┼──────────┤
│ Niedrig    │ Niedrig │ Niedrig │ Mittel   │
└────────────┴─────────┴─────────┴──────────┘
```

### Ermittelte zentrale Geschäftsrisiken

1. **Offenlegung von Kundendaten**
   - X Kundendatensätze möglicherweise zugänglich
   - Mögliche Folgen im Zusammenhang mit DSGVO/Datenschutzvorgaben

2. **Betriebskontinuität**
   - Zentrale Systeme sind anfällig für Unterbrechungen
   - Mögliche Ausfallzeit von X Stunden/Tagen

3. **Geistiges Eigentum**
   - [Konkretes Risiko für unternehmenseigene Daten]

---

## 5. Kritische Befunde (sofortiges Handeln erforderlich)

### Befund 1: [Titel mit Schwerpunkt auf geschäftlichen Auswirkungen]
> **Risiko:** Ein Angreifer könnte [geschäftliche Auswirkung in verständlicher Sprache]
>
> **Betroffen:** [Geschäftssystem/-prozess]
>
> **Empfehlung:** [Übergeordnete Maßnahme]

### Befund 2: [Titel]
> **Risiko:** [Business impact]
>
> **Betroffen:** [System]
>
> **Empfehlung:** [Abhilfe]

---

## 6. Priorisierte Empfehlungen

### Sofort (0-30 Tage)
| Priorität | Maßnahme | Investition | Risikoreduktion |
|----------|--------|------------|----------------|
| 1 | [Maßnahme 1] | $ | Hoch |
| 2 | [Maßnahme 2] | $$ | Hoch |
| 3 | [Maßnahme 3] | $ | Mittel |

### Kurzfristig (30-90 Tage)
| Priorität | Maßnahme | Investition | Risikoreduktion |
|----------|--------|------------|----------------|
| 4 | [Maßnahme 4] | $$ | Mittel |
| 5 | [Maßnahme 5] | $$$ | Mittel |

### Langfristig (90+ Tage)
| Priorität | Maßnahme | Investition | Risikoreduktion |
|----------|--------|------------|----------------|
| 6 | [Maßnahme 6] | $$$ | Niedrig |
| 7 | [Maßnahme 7] | $$$$ | Strategisch |

---

## 7. Investition im Verhältnis zur Risikoreduktion

```
    Risiko                                        Investition
    Reduktion                                   Erforderlich
    ▲                                           
    │     ┌─────┐                               
100%│     │ P1  │ ← Schnelle Erfolge                  $
    │     └─────┘   (Hoher Nutzen, geringe Kosten)      
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
                   Budget
```

---

## 8. Vergleich des Sicherheitsreifegrads

### Aktueller Stand und Branchenvergleich

| Bereich | Aktueller Stand | Branchendurchschnitt | Ziel |
|--------|---------|--------------|--------|
| Zugriffskontrolle | ██░░░░░░░░ 20% | ████████░░ 80% | ████████░░ 80% |
| Datenschutz | ████░░░░░░ 40% | ███████░░░ 70% | ████████░░ 85% |
| Überwachung | ███░░░░░░░ 30% | ██████░░░░ 60% | ████████░░ 80% |
| Reaktion auf Sicherheitsvorfälle | █░░░░░░░░░ 10% | █████░░░░░ 50% | ███████░░░ 70% |

---

## 9. Fazit

Der aktuelle Sicherheitszustand von [Unternehmensname] bedeutet ein **[HOHES/MITTLERES/NIEDRIGES]** Risiko für den 
Geschäftsbetrieb. Die Prüfung ergab **X** Schwachstellen, darunter 
**X kritische** Probleme mit sofortigem Handlungsbedarf.

**Wichtigste Erkenntnisse:**
1. [Wichtigster Punkt für die Geschäftsleitung]
2. [Zweitwichtigster Punkt]
3. [Drittwichtigster Punkt]

**Empfohlene Investition:** $XXX,XXX - $XXX,XXX über 12 Monate

**Erwartetes Ergebnis:** X% Verringerung des Sicherheitsrisikos

---

## 10. Nächste Schritte

| Zeitplan | Maßnahme | Verantwortlich | Status |
|----------|--------|-------|--------|
| Sofort | Kritische Befunde beheben | Sicherheitsteam | Ausstehend |
| Woche 1 | Detaillierten Bericht prüfen | IT-Leitung | Ausstehend |
| Woche 2 | Budget für die Behebung freigeben | CFO | Ausstehend |
| Monat 1 | Mit der Behebung beginnen | Sicherheitsteam | Ausstehend |
| Monat 3 | Kritische Befunde erneut testen | [Dienstleister] | Ausstehend |

---

**Kontaktinformationen:**
[Name Ihres Unternehmens]
[Name der leitenden Beratungsperson]
[E-Mail] | [Telefon]

---

*Dieses Dokument ist vertraulich und ausschließlich für berechtigte Personen bestimmt.*
````

---

## 🎯 Einseitige Management-Zusammenfassung

````markdown
┌────────────────────────────────────────────────────────────┐
│ ZUSAMMENFASSUNG DER SICHERHEITSPRÜFUNG                     │
│ [Unternehmensname]                                         │
│ [Datum]                                                    │
│                                                            │
│ GESAMTEINSTUFUNG: HOHES RISIKO                             │
│ BEFUNDÜBERSICHT                                            │
│ Kritisch: X | Hoch: X | Mittel: X | Niedrig: X | Gesamt: X │
│                                                            │
│ DIE 3 GRÖSSTEN RISIKEN                                     │
│ 1. [Kritisches Risiko in einem Satz]                       │
│ 2. [Hohes Risiko in einem Satz]                            │
│ 3. [Hohes Risiko in einem Satz]                            │
│                                                            │
│ GESCHÄFTLICHE AUSWIRKUNGEN                                 │
│ • Mögliche Datenkompromittierung mit X betroffenen Kunden  │
│ • Bußgelder bis zu $X                                      │
│ • Risiko einer Betriebsunterbrechung von X Tagen           │
│                                                            │
│ SOFORT ERFORDERLICHE MASSNAHMEN                            │
│ □ [Maßnahme 1] - Geschätzte Kosten: $XX,XXX                │
│ □ [Maßnahme 2] - Geschätzte Kosten: $XX,XXX                │
│ □ [Maßnahme 3] - Geschätzte Kosten: $XX,XXX                │
│                                                            │
│ EMPFOHLENE INVESTITION: $XXX,XXX                           │
│ ERWARTETE RISIKOREDUKTION: XX%                             │
└────────────────────────────────────────────────────────────┘
````

---

## 📈 Visualisierungen für Führungskräfte

### Verlauf des Risikos
```
Risikoniveau
    ▲
100 │
    │         ●─────Vor der Prüfung
 80 │         │
    │         │
 60 │         │
    │         ▼
 40 │              ○ ─ ─ Nach der Behebung (Ziel)
    │
 20 │
    │
  0 └───────────────────────────────────────────►
        Q1      Q2      Q3      Q4      Zeit
```

### Kosten des Nichtstuns
````markdown
| Zeitplan | Kosten einer Kompromittierung | Kosten der Behebung | Einsparungen |
|----------|----------------|------------------|---------|
| Jetzt | $0 | $100K | N/A |
| Nach einer Kompromittierung | $1.5M | $300K | -$1.3M |
| Mit Versicherung | $500K | $150K | -$350K |
````

---

## 💡 Schreibtipps für Führungskräfte

### Empfehlenswert ✅
```
✓ Geschäftliche Auswirkungen zuerst nennen, technische Einzelheiten nachordnen
✓ Verständliche Sprache ohne Fachjargon verwenden
✓ Klare Kosten-Nutzen-Analyse vorlegen
✓ Umsetzbare Empfehlungen aufnehmen
✓ Auf höchstens 2-3 Seiten begrenzen
✓ Visualisierungen verwenden (Diagramme, Grafiken, Tabellen)
✓ Wirtschaftlichen Nutzen der Sicherheitsinvestition hervorheben
✓ Mit Branchenmaßstäben vergleichen
```

### Zu vermeiden ❌
```
✗ Keine technischen Fachbegriffe verwenden (CVE, CVSS usw.)
✗ Nicht jeden Befund aufzählen, sondern auf kritische Befunde konzentrieren
✗ Keine übermäßigen technischen Einzelheiten angeben
✗ Kosten und Zeitpläne nicht vergessen
✗ Nicht länger als nötig schreiben
✗ Keine Angst erzeugen, ohne Lösungen anzubieten
```

---

## 📋 Checkliste für die Management-Zusammenfassung

````markdown
## Vor der Präsentation vor der Geschäftsleitung

□ Zusammenfassung umfasst höchstens 2-3 Seiten
□ Kein technischer Fachjargon verwendet
□ Geschäftliche Auswirkungen klar benannt
□ Kosten und Zeitplan enthalten
□ Visuelle Elemente vorhanden (Diagramme/Grafiken)
□ Empfehlungen sind umsetzbar
□ Wirtschaftlicher Nutzen der Behebung erklärt
□ Branchenvergleich enthalten
□ Nächste Schritte klar definiert
□ Kontaktinformationen angegeben
````

---

**Zurück zu Reporting:** 📝 Berichtsvorlagen (die im Original verlinkte Datei `Reporting/README.md` fehlt).

---

## Hinweise zur deutschen Ausgabe

> **Beispielwerte:** Risikowerte wie `70/100`, Reifegradprozente, Branchenmittelwerte, Kosten, Einsparungen und erwartete Risikoreduktionen sind illustrative Angaben aus der Vorlage. Ohne benannte Messmethode, Datenquelle, Bezugsgröße und geprüfte Rechnung sind sie keine belastbaren Kennzahlen. Die Kostentabelle definiert insbesondere die Vergleichsbasis ihrer Einsparungen nicht. Währungswerte, Zahlen und Fristen wurden unverändert übernommen; sie müssen für einen realen Bericht nachvollziehbar ersetzt und berechnet werden.

> **Risiko einordnen:** Technischer Schweregrad und geschäftliches Risiko sind zu unterscheiden. CVSS-Basiswerte allein bilden weder die konkrete Bedrohungslage noch sämtliche Auswirkungen im jeweiligen Unternehmen ab. Auch feste Behebungsfristen sind Beispiele und müssen zum vereinbarten Risikomanagement passen. [FIRST: CVSS und Risiko](https://www.first.org/cvss/v4.0/user-guide).

> **Layout:** Die übersetzten Diagramme wurden für lesbare deutsche Beschriftungen neu ausgerichtet; ihre Phasen, Kategorien und Zahlen bleiben erhalten. Äußere Markdown-Vorlagen verwenden vier Backticks, damit enthaltene Code-Fences nicht vorzeitig schließen. [CommonMark: Code-Fences](https://spec.commonmark.org/0.31.2/#fenced-code-blocks).
