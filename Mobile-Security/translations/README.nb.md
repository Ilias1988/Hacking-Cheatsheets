# 📱 Hurtigreferanse for mobilsikkerhet

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
███╗   ███╗ ██████╗ ██████╗ ██╗██╗     ███████╗    ███████╗███████╗ ██████╗██╗   ██╗██████╗ ██╗████████╗██╗   ██╗
████╗ ████║██╔═══██╗██╔══██╗██║██║     ██╔════╝    ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██║╚══██╔══╝╚██╗ ██╔╝
██╔████╔██║██║   ██║██████╔╝██║██║     █████╗      ███████╗█████╗  ██║     ██║   ██║██████╔╝██║   ██║    ╚████╔╝ 
██║╚██╔╝██║██║   ██║██╔══██╗██║██║     ██╔══╝      ╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██║   ██║     ╚██╔╝  
██║ ╚═╝ ██║╚██████╔╝██████╔╝██║███████╗███████╗    ███████║███████╗╚██████╗╚██████╔╝██║  ██║██║   ██║      ██║   
╚═╝     ╚═╝ ╚═════╝ ╚═════╝ ╚═╝╚══════╝╚══════╝    ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝   ╚═╝      ╚═╝   
```

---

## 🎯 Sikkerhetstesting av mobilapplikasjoner

Sikkerhetstesting av mobilapper går ut på å finne sårbarheter innen:
- 📦 **Applikasjonspakke** – analyse av APK/IPA
- 💾 **Datalagring** – usikker lokal lagring
- 🔐 **Autentisering** – svake autentiseringsmekanismer
- 🌐 **Nettverk** – usikker kommunikasjon
- 🔑 **Kryptografi** – svak kryptering
- 🛡️ **Plattform** – sårbarheter knyttet til operativsystemet

---

> **Begreper:** Dynamisk instrumentering betyr å observere eller endre appens oppførsel mens den kjører. Root og jailbreak gir utvidet tilgang til henholdsvis Android og iOS. Sertifikatfesting (certificate pinning, ofte kalt SSL pinning) begrenser hvilke sertifikater eller nøkler appen godtar.

## 📖 Veiledninger i mobilsikkerhet

| Plattform | Beskrivelse | Veiledning |
|----------|-------------|-------|
| **Android** | APK-analyse, Frida og omgåelse av root-deteksjon | [📄 Les](./Android-Pentesting.nb.md) |
| **iOS** | IPA-analyse, jailbreak og Objection | [📄 Les](./iOS-Pentesting.nb.md) |

---

## 🛠️ Viktige verktøy for mobilsikkerhet

| Verktøy | Plattform | Formål |
|------|----------|---------|
| **Frida** | Begge | Dynamisk instrumentering |
| **Objection** | Begge | Utforsking av mobilapper under kjøring |
| **MobSF** | Begge | Automatisert sikkerhetsanalyse |
| **jadx** | Android | Dekompilering av APK |
| **apktool** | Android | Omvendt utvikling av APK |
| **Burp Suite** | Begge | Avskjæring av trafikk |
| **Ghidra** | Begge | Analyse av binærfiler |
| **drozer** | Android | Sikkerhetsvurdering |

### Rask installasjon av verktøy
```bash
# Frida
pip install frida-tools

# Objection
pip install objection

# MobSF (Docker)
docker pull opensecurity/mobile-security-framework-mobsf
docker run -p 8000:8000 opensecurity/mobile-security-framework-mobsf

# jadx
# Last ned fra https://github.com/skylot/jadx/releases

# apktool
# Last ned fra https://ibotpeaches.github.io/Apktool/install/

# drozer
pip install drozer
```

---

## 📊 OWASP Mobile Top 10 (2024)

| # | Sårbarhet | Beskrivelse |
|---|---------------|-------------|
| M1 | Feil bruk av påloggingsopplysninger | Hardkodede påloggingsopplysninger og usikker lagring |
| M2 | Utilstrekkelig sikkerhet i leverandørkjeden | Ondsinnede SDK-er og kompromitterte biblioteker |
| M3 | Usikker autentisering/autorisasjon | Svak autentisering og mangelfull tilgangskontroll |
| M4 | Utilstrekkelig validering av inn- og utdata | Injeksjon og XSS i WebViews |
| M5 | Usikker kommunikasjon | Manglende TLS og sertifikatproblemer |
| M6 | Utilstrekkelige personverntiltak | Lekkasje av personopplysninger og for vide tillatelser |
| M7 | Utilstrekkelig beskyttelse av binærfiler | Manglende tilsløring og mulighet for manipulering |
| M8 | Feil sikkerhetskonfigurasjon | Aktivert feilsøking og usikre standardinnstillinger |
| M9 | Usikker datalagring | Lagring i klartekst og logger |
| M10 | Utilstrekkelig kryptografi | Svake algoritmer og hardkodede nøkler |

---

## 🔗 Hurtigreferanse: Frida-kommandoer

> **Versjonsmerknad:** `-f` starter appen som en ny prosess (spawn), mens tilkobling til en eksisterende prosess er attach. Eksemplene med `--no-pause` er fra en eldre CLI-versjon; kontroller støttede flagg i versjonen du bruker. Se [Fridas CLI-kilde](https://github.com/frida/frida-tools/blob/main/frida_tools/repl.py).

```bash
# List prosesser som kjører
frida-ps -U

# Koble til prosessen (se versjonsmerknaden)
frida -U -f com.app.package

# Start en ny prosess med skript
frida -U -f com.app.package -l script.js --no-pause

# Utforsk med Objection
objection -g com.app.package explore
```

---

## 📚 Ressurser

- [OWASP om mobilsikkerhet](https://owasp.org/www-project-mobile-security/)
- [OWASP MASTG](https://mas.owasp.org/MASTG/)
- [Frida-dokumentasjon](https://frida.re/docs/)
- [HackTricks Mobile](https://book.hacktricks.xyz/mobile-pentesting)

---

<p align="center">
  <b>📱 Test mobilapper på en ansvarlig måte!</b>
</p>
