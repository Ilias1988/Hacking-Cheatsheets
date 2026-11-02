# 📱 Schede di riferimento sulla sicurezza mobile

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 🎯 Test di sicurezza delle applicazioni mobili

I test di sicurezza delle app mobili mirano a individuare vulnerabilità nelle seguenti aree:
- 📦 **Pacchetto applicativo** - Analisi di APK/IPA
- 💾 **Archiviazione dei dati** - Archiviazione locale non sicura
- 🔐 **Autenticazione** - Meccanismi di autenticazione deboli
- 🌐 **Rete** - Comunicazioni non sicure
- 🔑 **Crittografia** - Cifratura debole
- 🛡️ **Piattaforma** - Vulnerabilità specifiche del sistema operativo

---

## 📖 Guide alla sicurezza mobile

| Piattaforma | Descrizione | Guida |
|----------|-------------|-------|
| **Android** | Analisi APK, Frida, aggiramento del rilevamento del root | [📄 Visualizza](./Android-Pentesting.it.md) |
| **iOS** | Analisi IPA, jailbreak, Objection | [📄 Visualizza](./iOS-Pentesting.it.md) |

---

## 🛠️ Strumenti essenziali per la sicurezza mobile

| Strumento | Piattaforma | Scopo |
|------|----------|---------|
| **Frida** | Entrambe | Strumentazione dinamica (analisi e modifica durante l'esecuzione) |
| **Objection** | Entrambe | Esplorazione delle app mobili durante l'esecuzione |
| **MobSF** | Entrambe | Analisi automatizzata della sicurezza |
| **jadx** | Android | Decompilazione APK |
| **apktool** | Android | Reverse engineering (analisi del funzionamento interno) di APK |
| **Burp Suite** | Entrambe | Intercettazione del traffico |
| **Ghidra** | Entrambe | Analisi dei binari |
| **drozer** | Android | Valutazione della sicurezza |

### Installazione rapida degli strumenti
```bash
# Frida
pip install frida-tools

# Objection
pip install objection

# MobSF (Docker)
docker pull opensecurity/mobile-security-framework-mobsf
docker run -p 8000:8000 opensecurity/mobile-security-framework-mobsf

# jadx
# Scarica da https://github.com/skylot/jadx/releases

# apktool
# Scarica da https://ibotpeaches.github.io/Apktool/install/

# drozer
pip install drozer
```

---

## 📊 OWASP Mobile Top 10 (2024)

| # | Vulnerabilità | Descrizione |
|---|---------------|-------------|
| M1 | Uso improprio delle credenziali | Credenziali incorporate nel codice, archiviazione non sicura |
| M2 | Sicurezza inadeguata della catena di fornitura | SDK malevoli, librerie compromesse |
| M3 | Autenticazione/autorizzazione non sicure | Autenticazione debole, controlli di accesso inefficaci |
| M4 | Validazione insufficiente di input e output | Injection, XSS nelle WebView |
| M5 | Comunicazioni non sicure | Assenza di TLS, problemi con i certificati |
| M6 | Controlli della privacy inadeguati | Esposizione di dati personali (PII), permessi eccessivi |
| M7 | Protezioni insufficienti dei binari | Assenza di offuscamento, manomissione |
| M8 | Configurazione di sicurezza errata | Debug abilitato, impostazioni predefinite non sicure |
| M9 | Archiviazione dei dati non sicura | Dati in chiaro nell'archiviazione e nei log |
| M10 | Crittografia insufficiente | Algoritmi deboli, chiavi incorporate nel codice |

---

## 🔗 Riferimento rapido: comandi Frida

> **Nota sulle versioni:** gli esempi conservano le opzioni dell’originale. `--no-pause` appartiene a versioni precedenti di frida-tools e non è disponibile in tutte le versioni; verifica le opzioni della versione installata nella [discussione nel progetto Frida](https://github.com/frida/frida/discussions/2411).

```bash
# Elenca i processi in esecuzione
frida-ps -U

# Avvia il processo (spawn)
frida -U -f com.app.package

# Avvia il processo caricando lo script
frida -U -f com.app.package -l script.js --no-pause

# Esplora con Objection
objection -g com.app.package explore
```

---

## 📚 Risorse

- [OWASP Sicurezza mobile](https://owasp.org/www-project-mobile-security/)
- [OWASP MASTG](https://mas.owasp.org/MASTG/)
- [Documentazione di Frida](https://frida.re/docs/)
- [HackTricks Mobile](https://book.hacktricks.xyz/mobile-pentesting)

---

<p align="center">
  <b>📱 Esegui test sulle app mobili in modo responsabile!</b>
</p>
