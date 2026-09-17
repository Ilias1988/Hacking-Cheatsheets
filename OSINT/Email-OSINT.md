# 📧 Email OSINT Cheatsheet

```
███████╗███╗   ███╗ █████╗ ██╗██╗     
██╔════╝████╗ ████║██╔══██╗██║██║     
█████╗  ██╔████╔██║███████║██║██║     
██╔══╝  ██║╚██╔╝██║██╔══██║██║██║     
███████╗██║ ╚═╝ ██║██║  ██║██║███████╗
╚══════╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚══════╝
```

---

## 🔍 Email Discovery Tools

| Tool | URL | Purpose |
|------|-----|---------|
| **Hunter.io** | hunter.io | Find company emails |
| **Phonebook.cz** | phonebook.cz | Free email search |
| **Snov.io** | snov.io | Email finder |
| **Clearbit Connect** | clearbit.com | Email enrichment |
| **RocketReach** | rocketreach.co | Contact finder |
| **Voila Norbert** | voilanorbert.com | Email lookup |

---

## 🛠️ Email Enumeration Commands

### theHarvester
```bash
# Email gathering
theHarvester -d target.com -l 500 -b all
theHarvester -d target.com -b google,linkedin,bing
theHarvester -d target.com -b all -f emails.html
```

### Hunter.io CLI
```bash
# API query
curl "https://api.hunter.io/v2/domain-search?domain=target.com&api_key=YOUR_KEY"

# Email verification
curl "https://api.hunter.io/v2/email-verifier?email=test@target.com&api_key=YOUR_KEY"
```

### h8mail (Breach Search)
```bash
pip3 install h8mail
h8mail -t target@email.com
h8mail -t target@email.com -c h8mail_config.ini
```

### holehe (Registration Check)
```bash
pip3 install holehe
holehe target@email.com
holehe target@email.com --only-used
```

---

## 📧 Email Format Patterns

```bash
# Common corporate formats
first.last@company.com     # john.smith@company.com
flast@company.com          # jsmith@company.com
firstl@company.com         # johns@company.com
first_last@company.com     # john_smith@company.com
first@company.com          # john@company.com
last.first@company.com     # smith.john@company.com
```

### Email Permutator
```python
# Generate email variations
names = ["john", "smith"]
domain = "company.com"

patterns = [
    f"{names[0]}.{names[1]}@{domain}",
    f"{names[0][0]}{names[1]}@{domain}",
    f"{names[0]}{names[1][0]}@{domain}",
    f"{names[0]}_{names[1]}@{domain}",
    f"{names[0]}@{domain}",
]
```

---

## 🔐 Breach Checking

| Service | URL | Type |
|---------|-----|------|
| **Have I Been Pwned** | haveibeenpwned.com | Free |
| **DeHashed** | dehashed.com | Paid |
| **LeakCheck** | leakcheck.io | Paid |
| **Snusbase** | snusbase.com | Paid |
| **IntelX** | intelx.io | Freemium |

### HIBP API
```bash
curl -H "hibp-api-key: YOUR_KEY" \
  "https://haveibeenpwned.com/api/v3/breachedaccount/test@example.com"
```

---

## ✅ Email Verification

### Verification Methods
```
1. MX Record Check - Domain has mail server
2. SMTP Verification - Mailbox exists
3. Syntax Check - Valid email format
4. Disposable Check - Not temporary email
5. Role Check - Not generic (info@, admin@)
```

### Simple SMTP Check
```bash
# Check MX records
nslookup -type=mx target.com
dig mx target.com

# Telnet verification
telnet mail.target.com 25
HELO test.com
MAIL FROM:<test@test.com>
RCPT TO:<target@target.com>
# 250 = exists, 550 = doesn't exist
```

---

## 📋 Email OSINT Checklist

```markdown
## Target Email: [email@domain.com]

□ Verify email exists (SMTP/Hunter)
□ Check data breaches (HIBP)
□ Find associated accounts (holehe)
□ Search in Google/Bing
□ Check social media registrations
□ Look for public profiles
□ Search in paste sites
□ Check professional networks
```

---

**Back to OSINT:** [🔍 OSINT Overview](./README.md)
