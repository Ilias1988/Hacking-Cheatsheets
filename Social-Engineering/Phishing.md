# 🎣 Phishing Cheatsheet

```
██████╗ ██╗  ██╗██╗███████╗██╗  ██╗██╗███╗   ██╗ ██████╗ 
██╔══██╗██║  ██║██║██╔════╝██║  ██║██║████╗  ██║██╔════╝ 
██████╔╝███████║██║███████╗███████║██║██╔██╗ ██║██║  ███╗
██╔═══╝ ██╔══██║██║╚════██║██╔══██║██║██║╚██╗██║██║   ██║
██║     ██║  ██║██║███████║██║  ██║██║██║ ╚████║╚██████╔╝
╚═╝     ╚═╝  ╚═╝╚═╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 📊 Phishing Types

| Type | Target | Description |
|------|--------|-------------|
| **Phishing** | Mass | Generic emails to many users |
| **Spear Phishing** | Specific | Targeted at specific individuals |
| **Whaling** | Executives | Targeting C-level/management |
| **Clone Phishing** | Any | Copy of legitimate email with malicious content |
| **Vishing** | Any | Voice/phone phishing |
| **Smishing** | Any | SMS phishing |
| **Pharming** | Any | DNS manipulation to fake site |

---

## 🛠️ GoPhish Setup & Usage

### Installation
```bash
# Download GoPhish
wget https://github.com/gophish/gophish/releases/download/v0.12.1/gophish-v0.12.1-linux-64bit.zip
unzip gophish-v0.12.1-linux-64bit.zip
cd gophish

# Start GoPhish
./gophish

# Access admin panel
https://localhost:3333
# Default creds: admin / check console output
```

### Campaign Setup Flow
```
1. Sending Profile   - Configure SMTP server
2. Landing Page      - Create fake login page
3. Email Template    - Design phishing email
4. Users & Groups    - Import target list
5. Launch Campaign   - Send phishing emails
6. Monitor Results   - Track opens, clicks, credentials
```

### Email Template Example
```html
<!DOCTYPE html>
<html>
<body>
<p>Dear {{.FirstName}},</p>
<p>Your IT department requires you to verify your account credentials.</p>
<p>Please click the link below to complete verification:</p>
<p><a href="{{.URL}}">Verify Account</a></p>
<p>This action is required within 24 hours.</p>
<br>
<p>IT Support Team</p>
</body>
</html>
```

### Landing Page Example
```html
<!DOCTYPE html>
<html>
<head>
    <title>Microsoft Login</title>
</head>
<body>
    <form method="post" action="">
        <h2>Sign in</h2>
        <input type="email" name="email" placeholder="Email" required>
        <input type="password" name="password" placeholder="Password" required>
        <button type="submit">Sign in</button>
    </form>
</body>
</html>
```

---

## 🔥 Evilginx2 (2FA Bypass)

### Installation
```bash
# Install Evilginx2
go install github.com/kgretzky/evilginx2@latest

# Or download binary
wget https://github.com/kgretzky/evilginx2/releases/...

# Run
sudo evilginx2
```

### Basic Setup
```bash
# In Evilginx2 console
config domain yourdomain.com
config ip YOUR_SERVER_IP

# List phishlets
phishlets

# Enable Microsoft 365 phishlet
phishlets hostname o365 login.yourdomain.com
phishlets enable o365

# Create lure
lures create o365
lures get-url 0
```

### Phishlet Targets
```
o365        - Microsoft 365
google      - Google accounts
linkedin    - LinkedIn
twitter     - Twitter/X
github      - GitHub
instagram   - Instagram
```

---

## 📧 Email Harvesting

### theHarvester
```bash
# Email harvesting
theHarvester -d target.com -b all

# Specific sources
theHarvester -d target.com -b google,linkedin,bing

# Output to file
theHarvester -d target.com -b all -f output.html
```

### Hunter.io
```bash
# Via API
curl "https://api.hunter.io/v2/domain-search?domain=target.com&api_key=YOUR_KEY"

# Get email format
curl "https://api.hunter.io/v2/email-finder?domain=target.com&first_name=John&last_name=Doe&api_key=YOUR_KEY"
```

### LinkedIn Enumeration
```bash
# Using linkedin2username
python linkedin2username.py -u USERNAME -c COMPANY_NAME

# Format guessing
john.doe@company.com
jdoe@company.com
j.doe@company.com
johnd@company.com
```

---

## 📮 Sending Infrastructure

### SMTP Services for Testing
```
- Amazon SES
- SendGrid
- Mailgun
- SMTP2GO
- Self-hosted (high spam risk)
```

### SPF/DKIM/DMARC Bypass
```bash
# Check SPF record
dig txt target.com | grep spf

# SPF weaknesses to exploit:
# - "include:" with permissive third parties
# - "~all" (softfail) instead of "-all"
# - No SPF record

# Check DMARC
dig txt _dmarc.target.com
```

### Domain Setup for Phishing
```bash
# Similar domains (typosquatting)
target.com → tarqet.com
target.com → target-secure.com
target.com → target.co
target.com → target-login.com

# Check availability
whois potential-phish-domain.com
```

---

## 📱 Smishing (SMS Phishing)

### Common Pretexts
```
- Package delivery notification
- Bank security alert
- Prize/lottery winning
- Account verification
- Two-factor auth code
```

### SMS Sending Services
```
- Twilio
- Nexmo/Vonage
- Plivo
- AWS SNS
```

### Example Messages
```
[FedEx] Your package is waiting. Confirm delivery: http://short.url/abc

[BankName] Unusual activity detected. Verify now: http://secure-link.com

[Netflix] Your payment failed. Update: http://netflix-billing.com
```

---

## 📞 Vishing (Voice Phishing)

### Common Scenarios
```
□ IT support needing password reset
□ Bank fraud department
□ Government agency (IRS, tax office)
□ Tech support scam
□ CEO/CFO urgent request
□ Vendor verification
```

### Vishing Script Example
```
"Hello, this is [Name] from the IT help desk. We've detected 
unusual activity on your account and need to verify your 
credentials to prevent a security breach. Can you confirm 
your username and current password so I can validate your 
account?"
```

### Tools
```bash
# Caller ID spoofing
- SpoofCard
- SpoofTel

# Voice changers
- Voicemod
- Clownfish
```

---

## 🎯 SET (Social Engineering Toolkit)

### Installation
```bash
git clone https://github.com/trustedsec/social-engineer-toolkit
cd social-engineer-toolkit
pip install -r requirements.txt
sudo python setoolkit
```

### Menu Options
```
1) Spear-Phishing Attack Vectors
2) Website Attack Vectors
3) Infectious Media Generator
4) Create a Payload and Listener
5) Mass Mailer Attack
```

### Credential Harvester
```bash
# In SET:
1) Social-Engineering Attacks
2) Website Attack Vectors
3) Credential Harvester Attack Method
2) Site Cloner
# Enter URL to clone (e.g., https://login.microsoft.com)
# SET will host fake site and capture credentials
```

---

## 📝 Phishing Email Templates

### Password Reset
```
Subject: [ACTION REQUIRED] Password Reset Request

Dear {Name},

We received a request to reset your password. If you did 
not make this request, please click below to secure your 
account immediately.

[Secure My Account]

This link will expire in 24 hours.

Best regards,
Security Team
```

### IT Support
```
Subject: Scheduled System Maintenance - Action Required

Dear {Name},

Our IT team will be performing scheduled maintenance. To 
ensure uninterrupted access, please verify your credentials:

[Verify Now]

Failure to verify may result in account lockout.

IT Department
```

### Package Delivery
```
Subject: Your Package Could Not Be Delivered

Your package from Amazon could not be delivered. Please 
confirm your delivery address:

[Confirm Delivery]

Tracking #: {Random}

Thank you,
Delivery Team
```

---

## 📋 Phishing Campaign Checklist

```markdown
Pre-Campaign:
□ Define scope and targets (authorized testing)
□ Gather target email addresses
□ Register similar domain
□ Set up SMTP infrastructure
□ Configure SPF/DKIM if possible
□ Create landing page
□ Create email template

Campaign Execution:
□ Test email delivery (spam filters)
□ Import target list
□ Schedule campaign
□ Monitor delivery rates
□ Track opens/clicks/submissions

Post-Campaign:
□ Generate reports
□ Analyze click rates
□ Review captured data
□ Identify training needs
□ Document findings
```

---

## 🔗 Related Cheatsheets

- [Pretexting](./Pretexting.md)
- [OSINT - Google Dorking](../Google-Dorking/README.md)

---

**Back to Overview:** [🎭 Social Engineering](./README.md)
