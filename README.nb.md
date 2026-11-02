# 🔴 Hacking Cheatsheets — Norsk utgave (bokmål)

[🇬🇧 English](README.md) • [🇬🇷 Ελληνικά](README.el.md) • [🇹🇷 Türkçe](README.tr.md) • [🇮🇹 Italiano](README.it.md) • [🇩🇪 Deutsch](README.de.md) • [🇷🇺 Русский](README.ru.md) • [🇫🇷 Français](README.fr.md) • [🇪🇸 Español](README.es.md) • [🇵🇹 Português (Portugal)](README.pt.md) • [🌐 العربية](README.ar.md) • [🇨🇳 简体中文](README.zh-CN.md) • [🇳🇴 Norsk (Bokmål)](README.nb.md)

Praktiske veiledninger for penetrasjonstesting, defensiv sikkerhet, CTF-øvelser og verktøy for cybersikkerhet. Denne siden samler alle de 127 dokumentene i 77 temamapper, oversatt fra engelsk.

## 📋 Innhold etter kategori

- [Angrepsmetodikk (Kill Chain)](#category-01)
- [Blue Team og defensiv sikkerhet](#category-02)
- [Skysikkerhet](#category-03)
- [Sikkerhet i mobilapper](#category-04)
- [Containersikkerhet](#category-05)
- [Sosial manipulering](#category-06)
- [Rapportmaler](#category-07)
- [OSINT og åpne kilder](#category-08)
- [Penetrasjonstesting av nettverk](#category-09)
- [CTF-veiledninger](#category-10)
- [IoT-sikkerhet](#category-11)
- [Rammeverk for utnyttelse av sårbarheter](#category-12)
- [Rekognosering og skanning](#category-13)
- [Testing av webapplikasjoner](#category-14)
- [Kontroll og gjenoppretting av passord](#category-15)
- [Nettverksanalyse](#category-16)
- [Bug bounty](#category-17)
- [Samling av payloads](#category-18)
- [Websårbarheter](#category-19)
- [Avanserte angrepsteknikker](#category-20)
- [Dorking og OSINT](#category-21)
- [Rettighetseskalering](#category-22)
- [Digital etterforskning](#category-23)
- [Omvendt utvikling](#category-24)
- [WiFi-sikkerhet](#category-25)
- [Active Directory](#category-26)
- [Ressurser](#category-27)

<a id="category-01"></a>
## Angrepsmetodikk (Kill Chain)

- [🚪 Fase 1: Innledende tilgang](Attack-Methodology/translations/01-Initial-Access.nb.md)
- [🔍 Fase 2: Oppdagelse og kartlegging](Attack-Methodology/translations/02-Enumeration.nb.md)
- [⬆️ Fase 3: Rettighetseskalering](Attack-Methodology/translations/03-Privilege-Escalation.nb.md)
- [➡️ Fase 4: Lateral bevegelse](Attack-Methodology/translations/04-Lateral-Movement.nb.md)
- [🔒 Fase 5: Vedvarende tilgang](Attack-Methodology/translations/05-Persistence.nb.md)
- [🛡️ Fase 6: Omgåelse av sikkerhetsmekanismer](Attack-Methodology/translations/06-Defense-Evasion.nb.md)
- [🎯 Fase 7: Gjennomføring av målene](Attack-Methodology/translations/07-Actions-Objectives.nb.md)
- [🎯 Angrepsmetodikk – Kill Chain](Attack-Methodology/translations/README.nb.md)

<a id="category-02"></a>
## Blue Team og defensiv sikkerhet

- [🚨 Hendelseshåndtering – hurtigreferanse](Blue-Team/translations/Incident-Response.nb.md)
- [📊 Logganalyse – hurtigreferanse](Blue-Team/translations/Log-Analysis.nb.md)
- [📈 SIEM-deteksjon – hurtigreferanse](Blue-Team/translations/SIEM-Detection.nb.md)
- [🎯 Trusseljakt – hurtigreferanse](Blue-Team/translations/Threat-Hunting.nb.md)
- [🔒 Systemherding – hurtigreferanse](Blue-Team/translations/Hardening.nb.md)
- [📝 Sigma-regler – hurtigreferanse](Blue-Team/translations/Sigma-Rules.nb.md)
- [🔬 YARA-regler – hurtigreferanse](Blue-Team/translations/YARA-Rules.nb.md)
- [🛡️ Blue Team – hurtigreferanse](Blue-Team/translations/README.nb.md)

<a id="category-03"></a>
## Skysikkerhet

- [🟠 Hurtigreferanse for penetrasjonstesting av AWS](Cloud-Security/translations/AWS-Pentesting.nb.md)
- [🔵 Hurtigreferanse for penetrasjonstesting av Azure](Cloud-Security/translations/Azure-Pentesting.nb.md)
- [🔴 Hurtigreferanse for penetrasjonstesting av GCP](Cloud-Security/translations/GCP-Pentesting.nb.md)
- [☁️ Hurtigreferanse for skysikkerhet](Cloud-Security/translations/README.nb.md)

<a id="category-04"></a>
## Sikkerhet i mobilapper

- [🤖 Hurtigreferanse for penetrasjonstesting av Android](Mobile-Security/translations/Android-Pentesting.nb.md)
- [🍎 Hurtigreferanse for penetrasjonstesting av iOS](Mobile-Security/translations/iOS-Pentesting.nb.md)
- [📱 Hurtigreferanse for mobilsikkerhet](Mobile-Security/translations/README.nb.md)

<a id="category-05"></a>
## Containersikkerhet

- [🐳 Hurtigreferanse for penetrasjonstesting av Docker](Container-Security/translations/Docker-Pentesting.nb.md)
- [☸️ Hurtigreferanse for penetrasjonstesting av Kubernetes](Container-Security/translations/Kubernetes-Pentesting.nb.md)
- [🐳 Hurtigreferanse for containersikkerhet](Container-Security/translations/README.nb.md)

<a id="category-06"></a>
## Sosial manipulering

- [🎣 Phishing – hurtigreferanse](Social-Engineering/translations/Phishing.nb.md)
- [🎭 Pretexting – hurtigreferanse](Social-Engineering/translations/Pretexting.nb.md)
- [🎭 Sosial manipulering – hurtigreferanse](Social-Engineering/translations/README.nb.md)

<a id="category-07"></a>
## Rapportmaler

- [📝 Mal for penetrasjonstestrapport](Reporting/translations/Pentest-Report-Template.nb.md)
- [🐛 Mal for bug bounty-rapport](Reporting/translations/Bug-Bounty-Report-Template.nb.md)
- [📊 Mal for ledelsessammendrag](Reporting/translations/Executive-Summary-Template.nb.md)

<a id="category-08"></a>
## OSINT og åpne kilder

- [👤 OSINT for personsøk](OSINT/translations/People-Search.nb.md)
- [📧 E-post-OSINT – hurtigreferanse](OSINT/translations/Email-OSINT.nb.md)
- [📱 OSINT i sosiale medier](OSINT/translations/Social-Media-OSINT.nb.md)
- [🌐 OSINT for domener og IP-adresser](OSINT/translations/Domain-IP-OSINT.nb.md)
- [🖼️ Bilde-OSINT](OSINT/translations/Image-OSINT.nb.md)
- [🔍 OSINT (Open Source Intelligence) – hurtigreferanse](OSINT/translations/README.nb.md)

<a id="category-09"></a>
## Penetrasjonstesting av nettverk

- [🔍 Hurtigreferanse for portskanning](Network-Pentesting/translations/Port-Scanning.nb.md)
- [📂 Hurtigreferanse for nettverkskartlegging](Network-Pentesting/translations/Network-Enumeration.nb.md)
- [🕵️ MITM-angrep (man-in-the-middle)](Network-Pentesting/translations/MITM-Attacks.nb.md)
- [💥 Hurtigreferanse for utnyttelse av tjenester](Network-Pentesting/translations/Service-Exploitation.nb.md)
- [🌐 Hurtigreferanse for penetrasjonstesting av nettverk](Network-Pentesting/translations/README.nb.md)

<a id="category-10"></a>
## CTF-veiledninger

- [🌐 Hurtigreferanse for weboppgaver i CTF](CTF/translations/Web-CTF.nb.md)
- [🔐 Hurtigreferanse for kryptografi i CTF](CTF/translations/Crypto-CTF.nb.md)
- [🔄 Hurtigreferanse for omvendt utvikling i CTF](CTF/translations/Reverse-Engineering-CTF.nb.md)
- [🔬 Hurtigreferanse for digital etterforskning i CTF](CTF/translations/Forensics-CTF.nb.md)
- [💥 Hurtigreferanse for pwn og binær utnyttelse i CTF](CTF/translations/Pwn-CTF.nb.md)
- [🏁 Hurtigreferanser for CTF](CTF/translations/README.nb.md)

<a id="category-11"></a>
## IoT-sikkerhet

- [💾 Hurtigreferanse for fastvareanalyse](IoT-Hacking/translations/Firmware-Analysis.nb.md)
- [🔧 Hurtigreferanse for maskinvarehacking](IoT-Hacking/translations/Hardware-Hacking.nb.md)
- [📡 Hurtigreferanse for IoT-hacking](IoT-Hacking/translations/README.nb.md)

<a id="category-12"></a>
## Rammeverk for utnyttelse av sårbarheter

- [🔴 Metasploit Framework – komplett hurtigreferanse](Metasploit/translations/README.nb.md)
- [🎭 Meterpreter – komplett hurtigreferanse](Metasploit/translations/Meterpreter.nb.md)
- [🔑 Mimikatz – komplett hurtigreferanse](Mimikatz/translations/README.nb.md)
- [💻 PowerShell – komplett hurtigreferanse for penetrasjonstesting](PowerShell/translations/README.nb.md)
- [🐧 Linux-kommandoer og Bash – komplett hurtigreferanse for penetrasjonstesting](Linux-Commands/translations/README.nb.md)

<a id="category-13"></a>
## Rekognosering og skanning

- [🔍 Nmap – komplett hurtigreferanse](Nmap/translations/README.nb.md)
- [🚀 Gobuster – komplett hurtigreferanse](Gobuster/translations/README.nb.md)
- [🕷️ Nikto – komplett hurtigreferanse](Nikto/translations/README.nb.md)

<a id="category-14"></a>
## Testing av webapplikasjoner

- [💉 SQLMap – komplett hurtigreferanse](SQLMap/translations/README.nb.md)
- [🔶 Burp Suite – komplett hurtigreferanse](Burp-Suite/translations/README.nb.md)
- [⚡ OWASP ZAP – komplett hurtigreferanse](OWASP-ZAP/translations/README.nb.md)

<a id="category-15"></a>
## Kontroll og gjenoppretting av passord

- [🔓 Hydra – komplett hurtigreferanse](Hydra/translations/README.nb.md)
- [🔨 John the Ripper – komplett hurtigreferanse](John-The-Ripper/translations/README.nb.md)
- [⚡ Hashcat – komplett hurtigreferanse](Hashcat/translations/README.nb.md)

<a id="category-16"></a>
## Nettverksanalyse

- [🦈 Wireshark – komplett hurtigreferanse](Wireshark/translations/README.nb.md)
- [📡 tcpdump – komplett hurtigreferanse](tcpdump/translations/README.nb.md)

<a id="category-17"></a>
## Bug bounty

- [🐛 Metodikk for bug bounty](Bug-Bounty-Methodology/translations/README.nb.md)
- [🎯 Nuclei – komplett hurtigreferanse](Nuclei/translations/README.nb.md)
- [🚀 ffuf – komplett hurtigreferanse](ffuf/translations/README.nb.md)
- [🔍 Subfinder – komplett hurtigreferanse](Subfinder/translations/README.nb.md)
- [🌐 httpx – komplett hurtigreferanse](httpx/translations/README.nb.md)
- [🌐 Amass – kartlegging av angrepsflaten](Amass/translations/README.nb.md)
- [🔗 GAU – Get All URLs](GAU/translations/README.nb.md)
- [⚔️ Katana – moderne webcrawler](Katana/translations/README.nb.md)
- [🔍 Arjun – oppdagelse av skjulte parametere](Arjun/translations/README.nb.md)
- [🦊 Dalfox – rammeverk for XSS-skanning](Dalfox/translations/README.nb.md)

<a id="category-18"></a>
## Samling av payloads

- [💉 Samling av XSS-payloads](Payloads/translations/XSS.nb.md)
- [💉 Payloads for SQL-injeksjon](Payloads/translations/SQLi.nb.md)
- [📂 Samling av LFI-payloads](Payloads/translations/LFI.nb.md)
- [🔥 Samling av SSTI-payloads](Payloads/translations/SSTI.nb.md)
- [💻 Payloads for kommandoinjeksjon](Payloads/translations/Command-Injection.nb.md)
- [🍃 Payloads for NoSQL-injeksjon](Payloads/translations/NoSQL-Injection.nb.md)
- [🔓 Payloads for deserialisering](Payloads/translations/Deserialization.nb.md)
- [🔌 Hurtigreferanse for WebSocket-angrep](Payloads/translations/WebSocket-Attacks.nb.md)
- [📊 Hurtigreferanse for GraphQL-injeksjon](Payloads/translations/GraphQL-Injection.nb.md)

<a id="category-19"></a>
## Websårbarheter

- [🔌 Hurtigreferanse for sikkerhetstesting av API-er](API-Security/translations/README.nb.md)
- [🔓 IDOR – Insecure Direct Object Reference](IDOR/translations/README.nb.md)
- [🌐 SSRF – Server-Side Request Forgery](SSRF/translations/README.nb.md)
- [📄 XXE – XML External Entity Injection](XXE/translations/README.nb.md)
- [⚡ Hurtigreferanse for race conditions](Race-Conditions/translations/README.nb.md)
- [🔐 Hurtigreferanse for omgåelse av autentisering](Auth-Bypass/translations/README.nb.md)
- [🌍 Hurtigreferanse for feilkonfigurert CORS](CORS/translations/README.nb.md)
- [🔀 Hurtigreferanse for åpen omdirigering](Open-Redirect/translations/README.nb.md)

<a id="category-20"></a>
## Avanserte angrepsteknikker

- [🛡️ Hurtigreferanse for omgåelse av WAF](WAF-Bypass/translations/README.nb.md)
- [☁️ Hurtigreferanse for omgåelse av Cloudflare](Cloudflare-Bypass/translations/README.nb.md)
- [🎯 Hurtigreferanse for overtakelse av underdomener](Subdomain-Takeover/translations/README.nb.md)
- [💾 Hurtigreferanse for forgiftning av webhurtigbuffer](Cache-Poisoning/translations/README.nb.md)
- [📦 Hurtigreferanse for HTTP request smuggling](HTTP-Request-Smuggling/translations/README.nb.md)
- [🧬 Hurtigreferanse for prototype pollution](Prototype-Pollution/translations/README.nb.md)

<a id="category-21"></a>
## Dorking og OSINT

- [🔍 Google dorking – komplett hurtigreferanse](Google-Dorking/translations/README.nb.md)
- [🌐 Shodan – komplett hurtigreferanse](Shodan/translations/README.nb.md)
- [🐙 GitHub-dorking – komplett hurtigreferanse](GitHub-Dorking/translations/README.nb.md)

<a id="category-22"></a>
## Rettighetseskalering

- [🐧 Rettighetseskalering i Linux – komplett hurtigreferanse](Linux-PrivEsc/translations/README.nb.md)
- [🪟 Rettighetseskalering i Windows – komplett hurtigreferanse](Windows-PrivEsc/translations/README.nb.md)

<a id="category-23"></a>
## Digital etterforskning

- [🧠 Volatility – hurtigreferanse for minneetterforskning](Volatility/translations/README.nb.md)
- [🔍 Autopsy – hurtigreferanse for digital etterforskning](Autopsy/translations/README.nb.md)
- [📷 ExifTool – hurtigreferanse for metadataanalyse](ExifTool/translations/README.nb.md)
- [🔧 Binwalk – hurtigreferanse for fastvareanalyse](Binwalk/translations/README.nb.md)

<a id="category-24"></a>
## Omvendt utvikling

- [🔍 Ghidra – hurtigreferanse for omvendt utvikling](Ghidra/translations/README.nb.md)
- [🐛 GDB – hurtigreferanse for GNU Debugger](GDB/translations/README.nb.md)
- [🔧 x64dbg – hurtigreferanse for feilsøking i Windows](x64dbg/translations/README.nb.md)

<a id="category-25"></a>
## WiFi-sikkerhet

- [📶 Aircrack-ng – hurtigguide til en verktøysamling for WiFi-testing](Aircrack-ng/translations/README.nb.md)
- [📶 Wifite – hurtigguide til automatisert sikkerhetsrevisjon av WiFi](Wifite/translations/README.nb.md)
- [🦈 Bettercap – hurtigguide til et rammeverk for nettverksangrep](Bettercap/translations/README.nb.md)

<a id="category-26"></a>
## Active Directory

- [🏢 Angrepsmetodikk for Active Directory](AD-Attack-Methodology/translations/README.nb.md)
- [🩸 BloodHound – visualisering av angrepsveier i Active Directory](BloodHound/translations/README.nb.md)
- [🐍 Impacket – Python-verktøykasse for AD-angrep](Impacket/translations/README.nb.md)
- [🗡️ CrackMapExec – sveitsisk lommekniv for AD](CrackMapExec/translations/README.nb.md)
- [🎫 Rubeus – verktøykasse for misbruk av Kerberos](Rubeus/translations/README.nb.md)
- [⚡ PowerView – AD-kartlegging med PowerShell](PowerView/translations/README.nb.md)
- [🎣 Responder – forgiftning av LLMNR/NBT-NS](Responder/translations/README.nb.md)
- [😈 Evil-WinRM – fjernskall for Windows](Evil-WinRM/translations/README.nb.md)
- [🔑 Kerbrute – brukerkartlegging med Kerberos](Kerbrute/translations/README.nb.md)

<a id="category-27"></a>
## Ressurser

- [📚 Ordlister – fullstendig referanseguide](Wordlists/translations/README.nb.md)
- [🐉 Kali Linux-verktøy – fullstendig referanse (600+ verktøy)](Kali-Linux-Tools/translations/README.nb.md)

## Slik bruker du veiledningene

Velg en kategori og åpne en veiledning. Tilpass eksemplene til laboratoriet ditt: versjoner, forutsetninger og tillatelser kan variere. Verktøynavn, alternativer, filbaner og nødvendige faguttrykk beholder den opprinnelige formen. Last ned eller klon repositoriet for å lese uten nettilgang.

```bash
git clone https://github.com/Ilias1988/Hacking-Cheatsheets.git
cd Hacking-Cheatsheets
```

## Bidra

Meld fra om oversettelsesfeil via Issues, og foreslå forbedringer gjennom en pull request. Les først [retningslinjene for bidrag på engelsk](CONTRIBUTING.md).

## Autorisert bruk

Innholdet er beregnet på læring og autorisert sikkerhetstesting. Bruk eksemplene bare på egne systemer eller med uttrykkelig tillatelse.

## Lisens

Prosjektet er tilgjengelig under [MIT-lisensen](LICENSE).

## Støtt prosjektet

Hvis veiledningene er nyttige, kan du gi repositoriet en stjerne, dele det og melde fra om feil.
