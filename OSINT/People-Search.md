# 👤 People Search OSINT

```
██████╗ ███████╗ ██████╗ ██████╗ ██╗     ███████╗
██╔══██╗██╔════╝██╔═══██╗██╔══██╗██║     ██╔════╝
██████╔╝█████╗  ██║   ██║██████╔╝██║     █████╗  
██╔═══╝ ██╔══╝  ██║   ██║██╔═══╝ ██║     ██╔══╝  
██║     ███████╗╚██████╔╝██║     ███████╗███████╗
╚═╝     ╚══════╝ ╚═════╝ ╚═╝     ╚══════╝╚══════╝
███████╗███████╗ █████╗ ██████╗  ██████╗██╗  ██╗ 
██╔════╝██╔════╝██╔══██╗██╔══██╗██╔════╝██║  ██║ 
███████╗█████╗  ███████║██████╔╝██║     ███████║ 
╚════██║██╔══╝  ██╔══██║██╔══██╗██║     ██╔══██║ 
███████║███████╗██║  ██║██║  ██║╚██████╗██║  ██║ 
╚══════╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝ 
```

---

## 🔍 People Search Engines

### Free Services
| Tool | URL | Coverage |
|------|-----|----------|
| **ThatsThem** | thatsthem.com | US |
| **FastPeopleSearch** | fastpeoplesearch.com | US |
| **TruePeopleSearch** | truepeoplesearch.com | US |
| **Webmii** | webmii.com | Global |
| **PeekYou** | peekyou.com | US |
| **Pipl** | pipl.com | Global |

### Paid Services
| Tool | URL | Features |
|------|-----|----------|
| **Spokeo** | spokeo.com | Deep search, social media |
| **BeenVerified** | beenverified.com | Background checks |
| **Intelius** | intelius.com | Criminal records |
| **US Search** | ussearch.com | Court records |
| **Radaris** | radaris.com | Property records |

---

## 🔎 Google Dorking for People

### Basic Searches
```
# Name search
"John Smith" site:linkedin.com
"John Smith" site:facebook.com
"John Smith" site:twitter.com

# Name + Location
"John Smith" "New York"
"John Smith" "NYC" OR "New York City"

# Name + Company
"John Smith" "Microsoft"
"John Smith" site:microsoft.com

# Name + Email
"John Smith" "@gmail.com"
"John Smith" email
```

### Professional Info
```
# Resume/CV search
"John Smith" filetype:pdf resume
"John Smith" filetype:doc CV
intitle:"curriculum vitae" "John Smith"

# LinkedIn specific
site:linkedin.com/in "John Smith"
site:linkedin.com "John Smith" "Software Engineer"

# Professional profiles
site:about.me "John Smith"
site:crunchbase.com "John Smith"
```

### Social Media Discovery
```
# Find all profiles
"John Smith" (site:facebook.com OR site:twitter.com OR site:instagram.com)

# Username search
"johnsmith123" site:twitter.com
"johnsmith123" site:reddit.com
"johnsmith123" site:github.com
```

---

## 🛠️ Command Line Tools

### Sherlock (Username Search)
```bash
# Install
git clone https://github.com/sherlock-project/sherlock.git
cd sherlock
pip3 install -r requirements.txt

# Search username across platforms
python3 sherlock username

# Multiple usernames
python3 sherlock user1 user2 user3

# Specific sites only
python3 sherlock username --site twitter instagram facebook

# Export results
python3 sherlock username --output results.txt
python3 sherlock username --csv
python3 sherlock username --xlsx
```

### Maigret (Enhanced Username Search)
```bash
# Install
pip3 install maigret

# Search
maigret username

# With Tor
maigret username --tor

# JSON output
maigret username --json results.json
```

### holehe (Email Registration Check)
```bash
# Install
pip3 install holehe

# Check email across services
holehe email@example.com

# Only show registered sites
holehe email@example.com --only-used
```

---

## 📧 Email to Person

### Find Person from Email
```
1. Search email in Google: "email@example.com"
2. Check HIBP: haveibeenpwned.com
3. Search in breach databases
4. Check social media registrations
5. Reverse email lookup services
```

### Email Format Discovery
```bash
# Common email formats
first.last@company.com
flast@company.com
firstl@company.com
first_last@company.com
first@company.com

# Hunter.io format finder
https://hunter.io/email-finder
```

---

## 📱 Phone Number OSINT

### Phone Lookup Services
| Service | URL | Type |
|---------|-----|------|
| **TrueCaller** | truecaller.com | Caller ID |
| **Sync.me** | sync.me | Caller ID |
| **NumLookup** | numlookup.com | Free lookup |
| **SpyDialer** | spydialer.com | Reverse lookup |
| **CallerID Test** | calleridtest.com | Carrier info |

### Phone Number Google Dorks
```
# Basic search
"555-123-4567"
"+1 555 123 4567"
"5551234567"

# Find associated info
"555-123-4567" site:facebook.com
"555-123-4567" "@"
"555-123-4567" "email"
```

### OSINT on Phone Numbers
```bash
# phoneinfoga
pip3 install phoneinfoga
phoneinfoga scan -n "+1234567890"
phoneinfoga serve -p 8080  # Web interface

# Using APIs
curl "https://api.numlookupapi.com/v1/validate/14155551234?apikey=YOUR_KEY"
```

---

## 🏠 Address & Property Records

### Property Search
| Service | URL | Coverage |
|---------|-----|----------|
| **Zillow** | zillow.com | US |
| **Redfin** | redfin.com | US |
| **County Assessor** | [varies] | Local records |
| **Whitepages** | whitepages.com | US |

### Public Records
```
# Court records
PACER (pacer.gov) - Federal courts
State court websites - State courts

# Voter records
Voter registration databases (varies by state)

# Business registrations
State Secretary of State websites
```

---

## 👥 Social Network Analysis

### LinkedIn OSINT
```
# Profile search
site:linkedin.com/in "John Smith"
site:linkedin.com/in "John Smith" "Company Name"

# Find connections
Check "People Also Viewed"
Check mutual connections
Company employees list
```

### Facebook OSINT
```
# Profile URLs
facebook.com/search/people/?q=john%20smith
facebook.com/search/pages/?q=john%20smith

# Graph search (limited now)
People named "John" who work at "Company"
People who live in "City" and like "Page"
```

### Tools for Social Analysis
```bash
# Social Analyzer
pip3 install social-analyzer
social-analyzer --username "target" --metadata

# Twint (Twitter)
pip3 install twint
twint -u username --followers
twint -u username --following
```

---

## 📸 Photo/Face Search

### Reverse Image Search
| Service | URL | Best For |
|---------|-----|----------|
| **Google Images** | images.google.com | General |
| **TinEye** | tineye.com | Exact matches |
| **Yandex** | yandex.com/images | Best for faces |
| **Bing Images** | bing.com/images | Good alternative |
| **PimEyes** | pimeyes.com | Facial recognition |

### Face Search
```
1. Yandex Images - Best for finding faces
2. PimEyes - Paid facial recognition
3. Social Catfish - Reverse image for social
```

---

## 📋 People Search Checklist

```markdown
## Target: [Full Name]

### Basic Information
□ Full legal name
□ Date of birth
□ Current address
□ Phone numbers
□ Email addresses

### Online Presence
□ LinkedIn profile
□ Facebook profile
□ Twitter/X account
□ Instagram
□ TikTok
□ Other social media
□ Personal website/blog

### Professional
□ Current employer
□ Job title
□ Work history
□ Education
□ Professional licenses
□ Publications/Patents

### Records
□ Property records
□ Court records
□ Business registrations
□ Voter registration
□ Marriage/Divorce records

### Connections
□ Family members
□ Associates
□ Business partners
□ Social connections
```

---

## ⚠️ Legal & Ethical Considerations

```
✓ Only use publicly available information
✓ Respect privacy laws (GDPR, CCPA)
✓ Don't impersonate or deceive
✓ Document your methodology
✓ Use for legitimate purposes only
✗ Don't stalk or harass
✗ Don't access private accounts
✗ Don't share findings publicly without consent
```

---

**Back to OSINT:** [🔍 OSINT Overview](./README.md)
