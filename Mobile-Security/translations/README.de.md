# 📱 Spickzettel zur Sicherheit mobiler Anwendungen

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
███╗   ███╗ ██████╗ ██████╗ ██╗██╗     ███████╗    ███████╗███████╗ ██████╗██╗   ██╗██████╗ ██╗████████╗██╗   ██╗
████╗ ████║██╔═══██╗██╔══██╗██║██║     ██╔════╝    ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██║╚══██╔══╝╚██╗ ██╔╝
██╔████╔██║██║   ██║██████╔╝██║██║     █████╗      ███████╗█████╗  ██║     ██║   ██║██████╔╝██║   ██║    ╚████╔╝ 
██║╚██╔╝██║██║   ██║██╔══██╗██║██║     ██╔══╝      ╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██║   ██║     ╚██╔╝  
██║ ╚═╝ ██║╚██████╔╝██████╔╝██║███████╗███████╗    ███████║███████╗╚██████╗╚██████╔╝██║  ██║██║   ██║      ██║   
╚═╝     ╚═╝ ╚═════╝ ╚═════╝ ╚═╝╚══════╝╚══════╝    ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝   ╚═╝      ╚═╝   
```

---

## 🎯 Sicherheitsprüfung mobiler Anwendungen

Bei der Sicherheitsprüfung mobiler Apps werden Schwachstellen in folgenden Bereichen gesucht:
- 📦 **Anwendungspaket** – Analyse von APK-/IPA-Dateien
- 💾 **Datenspeicherung** – unsichere lokale Speicherung
- 🔐 **Authentifizierung** – schwache Authentifizierungsverfahren
- 🌐 **Netzwerk** – unsichere Kommunikation
- 🔑 **Kryptografie** – schwache Verschlüsselung
- 🛡️ **Plattform** – betriebssystemspezifische Schwachstellen

---

## 📖 Leitfäden zur Sicherheit mobiler Anwendungen

| Plattform | Beschreibung | Leitfaden |
|----------|-------------|-------|
| **Android** | APK-Analyse, Frida und Umgehung der Root-Erkennung | [📄 Ansehen](./Android-Pentesting.de.md) |
| **iOS** | IPA-Analyse, Jailbreak und Objection | [📄 Ansehen](./iOS-Pentesting.de.md) |

---

## 🛠️ Wichtige Werkzeuge für mobile Sicherheitsprüfungen

| Tool | Plattform | Zweck |
|------|----------|---------|
| **Frida** | Beide | Dynamische Instrumentierung (Eingriffe in laufende Programme) |
| **Objection** | Beide | Untersuchung mobiler Apps zur Laufzeit |
| **MobSF** | Beide | Automatisierte Sicherheitsanalyse |
| **jadx** | Android | Dekompilierung von APK-Dateien |
| **apktool** | Android | Reverse Engineering von APK-Dateien |
| **Burp Suite** | Beide | Abfangen des Netzwerkverkehrs |
| **Ghidra** | Beide | Analyse von Binärdateien |
| **drozer** | Android | Sicherheitsbewertung |

### Werkzeuge schnell installieren
```bash
# Frida
pip install frida-tools

# Objection
pip install objection

# MobSF (Docker)
docker pull opensecurity/mobile-security-framework-mobsf
docker run -p 8000:8000 opensecurity/mobile-security-framework-mobsf

# jadx
# Download unter https://github.com/skylot/jadx/releases

# apktool
# Download unter https://ibotpeaches.github.io/Apktool/install/

# drozer
pip install drozer
```

---

## 📊 OWASP Mobile Top 10 (2024)

| # | Schwachstelle | Beschreibung |
|---|---------------|-------------|
| M1 | Unsachgemäßer Umgang mit Zugangsdaten | Fest im Code hinterlegte Zugangsdaten, unsichere Speicherung |
| M2 | Unzureichende Sicherheit der Softwarelieferkette | Schädliche SDKs, kompromittierte Bibliotheken |
| M3 | Unsichere Authentifizierung und Autorisierung | Schwache Authentifizierung, fehlerhafte Zugriffskontrolle |
| M4 | Unzureichende Prüfung von Ein- und Ausgaben | Injection, XSS in WebViews |
| M5 | Unsichere Kommunikation | Fehlendes TLS, Zertifikatsprobleme |
| M6 | Unzureichende Datenschutzmaßnahmen | Abfluss personenbezogener Daten, zu weitreichende Berechtigungen |
| M7 | Unzureichender Schutz von Binärdateien | Fehlende Verschleierung, Manipulation |
| M8 | Sicherheitsrelevante Fehlkonfiguration | Aktivierter Debugmodus, unsichere Standardeinstellungen |
| M9 | Unsichere Datenspeicherung | Klartextspeicherung, Protokolle |
| M10 | Unzureichende Kryptografie | Schwache Algorithmen, fest hinterlegte Schlüssel |

---

## 🔗 Kurzreferenz: Frida-Befehle

```bash
# Laufende Prozesse auflisten
frida-ps -U

# Prozess über seine Paketkennung starten
frida -U -f com.app.package

# App mit Skript starten
frida -U -f com.app.package -l script.js --no-pause

# App mit Objection untersuchen
objection -g com.app.package explore
```

---

## 📚 Ressourcen

- [OWASP Sicherheit mobiler Anwendungen](https://owasp.org/www-project-mobile-security/)
- [OWASP MASTG](https://mas.owasp.org/MASTG/)
- [Frida-Dokumentation](https://frida.re/docs/)
- [HackTricks Mobile](https://book.hacktricks.xyz/mobile-pentesting)

---

<p align="center">
  <b>📱 Prüfe mobile Apps verantwortungsvoll!</b>
</p>

---

## Hinweise zur deutschen Ausgabe

> **Begriffe und Versionen:** APK und IPA sind die Anwendungspakete von Android beziehungsweise iOS. Dynamische Instrumentierung beobachtet oder verändert eine App zur Laufzeit; statische Analyse untersucht ihre Dateien ohne Ausführung. SSL-Pinning (heute meist TLS-Zertifikats- oder Schlüsselbindung) ist eine zusätzliche Vertrauensprüfung der App. Die OWASP-Tabelle bezieht sich ausdrücklich auf die Ausgabe 2024, nicht auf einen zeitlosen Prüfstandard.

> **Frida-Hinweis:** `-f` startet einen neuen Prozess; es verbindet sich nicht einfach mit einem bereits laufenden Prozess. `--no-pause` stammt aus älteren CLI-Versionen; aktuelle `frida-tools` bieten stattdessen `--pause`. Die Originalbefehle bleiben unverändert. [Frida-CLI-Quellcode](https://github.com/frida/frida-tools/blob/main/frida_tools/repl.py).
