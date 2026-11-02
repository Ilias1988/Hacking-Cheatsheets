# 🤝 Zu Hacking Cheatsheets beitragen

[🇹🇷 Türkçe Sürüm](../CONTRIBUTING.tr.md)

Vielen Dank, dass du zu Hacking Cheatsheets beitragen möchtest! 🎉

Dieses Dokument beschreibt die Richtlinien für Beiträge zu diesem Repository.

---

## 📋 Inhaltsverzeichnis

- [Verhaltenskodex](#verhaltenskodex)
- [Wie kann ich beitragen?](#beitragen)
- [Richtlinien für Cheatsheets](#cheatsheet-richtlinien)
- [Stilrichtlinien](#stilrichtlinien)
- [Ablauf eines Pull Requests](#pull-requests)
- [Anerkennung](#anerkennung)
- [Fragen](#fragen)
- [Hinweise zum Original](#hinweise)

---

<a id="verhaltenskodex"></a>

## 📜 Verhaltenskodex

### Unser Versprechen

Wir setzen uns für ein freundliches, sicheres und einladendes Umfeld für alle Mitwirkenden ein.

### Umgang miteinander

- ✅ Begegne anderen respektvoll und schließe niemanden aus
- ✅ Gib konstruktive Rückmeldungen
- ✅ Orientiere dich am Wohl der Gemeinschaft
- ❌ Keine Belästigung oder Diskriminierung
- ❌ Keine rechtswidrigen Inhalte oder Anleitungen zur missbräuchlichen Nutzung

### Ethische Richtlinien

> ⚠️ **Alle Beiträge dürfen ausschließlich Bildungszwecken und einer ethisch vertretbaren Nutzung dienen.**
>
> Wir akzeptieren keine:
> - Inhalte, die rechtswidrige Aktivitäten fördern
> - Anleitungen für unbefugten Zugriff
> - Schadsoftware oder Exploit-Code, die für eine missbräuchliche Nutzung bestimmt sind

---

<a id="beitragen"></a>

## 🎯 Wie kann ich beitragen?

### 📝 Neue Cheatsheets hinzufügen

Kennst du dich mit einem Sicherheitswerkzeug aus, das hier noch nicht behandelt wird? Erstelle ein neues Cheatsheet!

1. Prüfe, ob das Werkzeug bereits in der [Cheatsheet-Übersicht](../README.de.md) aufgeführt ist
2. Erstelle einen neuen Ordner für das Werkzeug
3. Beachte die [Richtlinien für Cheatsheets](#cheatsheet-richtlinien)
4. Reiche einen Pull Request ein

### 🔧 Vorhandene Cheatsheets verbessern

- Tippfehler und Grammatikfehler korrigieren
- Fehlende Befehle oder Optionen ergänzen
- Erklärungen durch bessere Beispiele verständlicher machen
- Veraltete Informationen aktualisieren
- Tabellen zum schnellen Nachschlagen ergänzen

### 🐛 Probleme melden

Du hast einen Fehler gefunden oder einen Verbesserungsvorschlag?

1. Prüfe, ob bereits ein entsprechendes Issue existiert
2. Erstelle ein neues Issue mit:
   - einer klaren Beschreibung
   - Schritten zum Nachstellen, falls zutreffend
   - einem Lösungsvorschlag, falls vorhanden

### 🌐 Übersetzungen

Hilf dabei, die Cheatsheets auch Menschen ohne Englischkenntnisse zugänglich zu machen:

1. Erstelle einen Ordner wie `Metasploit/translations/`
2. Füge übersetzte Dateien mit Sprachkürzel hinzu: `README.es.md`, `README.de.md`
3. Übernimm die Struktur des Originals

---

<a id="cheatsheet-richtlinien"></a>

## 📖 Richtlinien für Cheatsheets

### Aufbau

Jedes Cheatsheet sollte diesem Aufbau folgen:

```markdown
# 🔴 Werkzeugname – Vollständiges Cheatsheet

[ASCII-Art-Banner – optional]

[Badges]

> Kurze Beschreibung des Werkzeugs

---

## 📋 Inhaltsverzeichnis
- [Abschnitt 1](#abschnitt-1)
- [Abschnitt 2](#abschnitt-2)
...

---

## 🎯 Einführung
Was ist das für ein Werkzeug? Wofür wird es verwendet?

---

## 📥 Installation
So wird das Werkzeug installiert

---

## ⌨️ Grundlegende Befehle
Wichtige Befehle mit Beispielen

---

## 🎬 Praxisbeispiele
Praktische Einsatzszenarien

---

## 📊 Kurzreferenz
Tabelle mit einer Befehlsübersicht

---

## 💡 Tipps und bewährte Vorgehensweisen

---

## ⚠️ Rechtlicher Hinweis

---

## 📚 Ressourcen
Links zur offiziellen Dokumentation und zu Lernmaterialien
```

### Erforderliche Abschnitte

| Abschnitt | Erforderlich | Beschreibung |
|---------|----------|-------------|
| Titel mit Emoji | ✅ | Eindeutiger Werkzeugname |
| Badges | ✅ | Mindestens 2–3 passende Badges |
| Inhaltsverzeichnis | ✅ | Links zu allen Abschnitten |
| Einführung | ✅ | Was das Werkzeug macht und wozu es dient |
| Grundlegende Befehle | ✅ | Zentrale Funktionen |
| Kurzreferenz | ✅ | Übersichtstabelle |
| Rechtlicher Hinweis | ✅ | Hinweis zur ethischen Nutzung |
| Installation | 📋 | Empfohlen |
| Beispiele | 📋 | Besonders empfohlen |
| Tipps | 📋 | Empfohlen |

---

<a id="stilrichtlinien"></a>

## 🎨 Stilrichtlinien

### Markdown-Formatierung

````markdown
# H1 – Haupttitel (nur einer pro Datei)
## H2 – Hauptabschnitte
### H3 – Unterabschnitte
#### H4 – Weitere Untergliederung

**Fettdruck** zur Hervorhebung
`code` für Befehle und Dateinamen
```code blocks``` für mehrzeilige Befehle
> Blockzitate für Hinweise und Warnungen
````

### Codeblöcke

Gib für die Syntaxhervorhebung immer die Sprache an:

````bash
# Gut – mit Sprachangabe
```bash
msfconsole -q
```

# Schlecht – ohne Sprachangabe
```
msfconsole -q
```
````

### Tabellen

Verwende Tabellen für Befehlsreferenzen:

```markdown
| Befehl | Beschreibung | Beispiel |
|---------|-------------|---------|
| `cmd1` | Führt X aus | `cmd1 -option` |
| `cmd2` | Führt Y aus | `cmd2 value` |
```

### Emojis

Verwende Emojis einheitlich:

| Emoji | Verwendung |
|-------|-------|
| 🔴 | Exploitation/Angriff |
| 🔍 | Erkundung |
| 🌐 | Web/Netzwerk |
| 🔓 | Zugangsdaten/Zugriff |
| ⚠️ | Warnung |
| 💡 | Tipp |
| ✅ | Empfohlen/gut |
| ❌ | Vermeiden/schlecht |
| 📋 | Referenz |
| 🎯 | Ziel |

### Badges

Verwende Badges von shields.io:

```markdown
![Badge-Name](https://img.shields.io/badge/Label-Message-color?style=for-the-badge)
```

Häufige Farben: `red`, `blue`, `green`, `orange`, `purple`

---

<a id="pull-requests"></a>

## 🔄 Ablauf eines Pull Requests

### Vor dem Einreichen

1. ✅ Lies die Richtlinien für Beiträge, also dieses Dokument
2. ✅ Suche nach vorhandenen Pull Requests zum gleichen Thema
3. ✅ Prüfe alle Befehle und Beispiele
4. ✅ Kontrolliere Rechtschreibung und Grammatik
5. ✅ Achte auf eine korrekte Formatierung

### Einen Pull Request einreichen

1. **Erstelle einen Fork** des Repositorys
2. **Erstelle** einen Feature-Branch:
   ```bash
   git checkout -b feature/add-nmap-cheatsheet
   ```
3. **Committe** deine Änderungen:
   ```bash
   git commit -m "Add: Nmap cheatsheet with scanning examples"
   ```
4. **Pushe** in deinen Fork:
   ```bash
   git push origin feature/add-nmap-cheatsheet
   ```
5. **Öffne** einen Pull Request

### Format für Commit-Nachrichten

```
Typ: Kurze Beschreibung

- Ausführliche Änderung 1
- Ausführliche Änderung 2

Closes #123 (falls zutreffend)
```

Typen:
- `Add:` Neue Inhalte
- `Fix:` Fehlerkorrekturen
- `Update:` Aktualisierungen vorhandener Inhalte
- `Docs:` Änderungen an der Dokumentation
- `Style:` Änderungen an der Formatierung

### Prüfung des Beitrags

1. Ein Maintainer prüft den Pull Request
2. Falls Änderungen erforderlich sind, erhältst du Rückmeldung
3. Nach der Freigabe wird der Pull Request zusammengeführt
4. Dein Beitrag wird gewürdigt!

---

<a id="anerkennung"></a>

## 🏆 Anerkennung

Alle Mitwirkenden erhalten Anerkennung:

- Aufnahme in die Liste der Mitwirkenden
- Erwähnung in den Versionshinweisen
- Eintrag im Beitragsdiagramm von GitHub

### Ehrenliste

*Mitwirkende mit besonders bedeutenden Beiträgen werden hier vorgestellt!*

---

<a id="fragen"></a>

## ❓ Fragen?

Wenn du Fragen hast:

1. Prüfe vorhandene Issues
2. Erstelle ein neues Issue mit dem Label `question`
3. Hab etwas Geduld, bis du eine Antwort erhältst

---

<a id="hinweise"></a>

## 📝 Hinweise zum Original

- Der im Original als „Planned Cheatsheets“ bezeichnete Link führt zur allgemeinen Cheatsheet-Übersicht. Deshalb lautet die Linkbeschriftung hier „Cheatsheet-Übersicht“; ein eigener Abschnitt für geplante Cheatsheets ist damit nicht gemeint.
- Die äußeren Begrenzungen der verschachtelten Markdown-Beispiele wurden auf vier Backticks erweitert. So bleiben die inneren Beispiele mit drei Backticks sichtbar und beenden den umgebenden Block nicht vorzeitig. Die Beispielbefehle und ihre Werte bleiben unverändert.
- Die Abschnittslinks im Aufbau-Beispiel sind Platzhalter für ein zukünftiges Cheatsheet. Beim Ausfüllen der Vorlage müssen sie zu dessen tatsächlichen Überschriften passen.

---

<p align="center">
  <b>Vielen Dank für deinen Beitrag! 🙏</b><br>
  <i>Gemeinsam machen wir Wissen über Cybersicherheit leichter zugänglich</i>
</p>
