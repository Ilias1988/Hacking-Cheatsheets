# 🎭 Social Engineering Cheatsheets

```
███████╗ ██████╗  ██████╗██╗ █████╗ ██╗         ███████╗███╗   ██╗ ██████╗ ██╗███╗   ██╗███████╗███████╗██████╗ ██╗███╗   ██╗ ██████╗ 
██╔════╝██╔═══██╗██╔════╝██║██╔══██╗██║         ██╔════╝████╗  ██║██╔════╝ ██║████╗  ██║██╔════╝██╔════╝██╔══██╗██║████╗  ██║██╔════╝ 
███████╗██║   ██║██║     ██║███████║██║         █████╗  ██╔██╗ ██║██║  ███╗██║██╔██╗ ██║█████╗  █████╗  ██████╔╝██║██╔██╗ ██║██║  ███╗
╚════██║██║   ██║██║     ██║██╔══██║██║         ██╔══╝  ██║╚██╗██║██║   ██║██║██║╚██╗██║██╔══╝  ██╔══╝  ██╔══██╗██║██║╚██╗██║██║   ██║
███████║╚██████╔╝╚██████╗██║██║  ██║███████╗    ███████╗██║ ╚████║╚██████╔╝██║██║ ╚████║███████╗███████╗██║  ██║██║██║ ╚████║╚██████╔╝
╚══════╝ ╚═════╝  ╚═════╝╚═╝╚═╝  ╚═╝╚══════╝    ╚══════╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝╚═╝  ╚═══╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 What is Social Engineering?

Social engineering is the art of manipulating people to divulge confidential information or perform actions that compromise security. It exploits human psychology rather than technical vulnerabilities.

**Key Principles:**
- 🎭 **Authority** - People trust authority figures
- ⏰ **Urgency** - Time pressure reduces critical thinking
- 🤝 **Reciprocity** - People feel obligated to return favors
- 👥 **Social Proof** - People follow the crowd
- 😊 **Liking** - People comply with those they like
- 📉 **Scarcity** - Limited availability increases desire

---

## 📖 Social Engineering Guides

| Topic | Description | Guide |
|-------|-------------|-------|
| **Phishing** | Email, SMS, voice phishing techniques & tools | [📄 View](./Phishing.md) |
| **Pretexting** | Creating believable scenarios & personas | [📄 View](./Pretexting.md) |

---

## 🛠️ Essential Social Engineering Tools

| Tool | Purpose | Type |
|------|---------|------|
| **GoPhish** | Phishing campaign framework | Phishing |
| **SET (Social Engineering Toolkit)** | Comprehensive SE framework | Multi-purpose |
| **Evilginx2** | MITM phishing (bypass 2FA) | Phishing |
| **King Phisher** | Phishing campaign platform | Phishing |
| **Modlishka** | Reverse proxy phishing | Phishing |
| **theHarvester** | Email/domain harvesting | Recon |
| **LinkedIn tools** | Employee enumeration | OSINT |
| **Maltego** | OSINT & relationship mapping | Recon |

### Quick Tool Install
```bash
# GoPhish
wget https://github.com/gophish/gophish/releases/download/v0.12.1/gophish-v0.12.1-linux-64bit.zip
unzip gophish-v0.12.1-linux-64bit.zip
./gophish

# SET (Social Engineering Toolkit)
git clone https://github.com/trustedsec/social-engineer-toolkit
cd social-engineer-toolkit
pip install -r requirements.txt
python setoolkit

# Evilginx2
go install github.com/kgretzky/evilginx2@latest

# theHarvester
pip install theHarvester
theHarvester -d target.com -b all
```

---

## 📊 Social Engineering Attack Types

| Attack Type | Description | Vector |
|-------------|-------------|--------|
| **Phishing** | Deceptive emails to steal credentials | Email |
| **Spear Phishing** | Targeted phishing at specific individuals | Email |
| **Whaling** | Phishing targeting executives (C-level) | Email |
| **Vishing** | Voice/phone-based social engineering | Phone |
| **Smishing** | SMS-based phishing | SMS |
| **Pretexting** | Creating fake scenarios to extract info | Any |
| **Baiting** | Leaving infected media (USB, etc.) | Physical |
| **Quid Pro Quo** | Offering something in exchange for info | Any |
| **Tailgating** | Following authorized person into secure area | Physical |
| **Watering Hole** | Compromising websites targets visit | Web |

---

## 🔗 Quick Reference: Phishing Indicators

### Red Flags in Emails
```
□ Sender email doesn't match company domain
□ Generic greetings ("Dear Customer")
□ Urgency or threats
□ Spelling/grammar errors
□ Suspicious links (hover to check)
□ Unexpected attachments
□ Request for sensitive information
□ Too good to be true offers
```

### URL Analysis
```bash
# Check domain age
whois suspicious-domain.com

# Check reputation
https://www.virustotal.com
https://urlscan.io

# Analyze shortened URLs
https://checkshorturl.com
```

---

## 📚 Resources

- [OWASP Social Engineering](https://owasp.org/www-community/attacks/Social_Engineering)
- [MITRE ATT&CK - Initial Access](https://attack.mitre.org/tactics/TA0001/)
- [Phishing.org](https://www.phishing.org/)
- [Social Engineering Framework](https://www.social-engineer.org/)

---

<p align="center">
  <b>🎭 Use Social Engineering for Authorized Testing Only!</b>
</p>
