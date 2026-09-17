# 🎭 Pretexting Cheatsheet

> **Review status:** Source verification pending. Confirm version-sensitive commands against the official documentation before use.

```
██████╗ ██████╗ ███████╗████████╗███████╗██╗  ██╗████████╗██╗███╗   ██╗ ██████╗ 
██╔══██╗██╔══██╗██╔════╝╚══██╔══╝██╔════╝╚██╗██╔╝╚══██╔══╝██║████╗  ██║██╔════╝ 
██████╔╝██████╔╝█████╗     ██║   █████╗   ╚███╔╝    ██║   ██║██╔██╗ ██║██║  ███╗
██╔═══╝ ██╔══██╗██╔══╝     ██║   ██╔══╝   ██╔██╗    ██║   ██║██║╚██╗██║██║   ██║
██║     ██║  ██║███████╗   ██║   ███████╗██╔╝ ██╗   ██║   ██║██║ ╚████║╚██████╔╝
╚═╝     ╚═╝  ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 What is Pretexting?

Pretexting is the practice of creating a fabricated scenario (pretext) to engage a target and extract information or manipulate them into performing an action. It's the foundation of most social engineering attacks.

**Key Components:**
- 🎭 **Persona** - The fake identity you assume
- 📖 **Story** - The believable scenario/reason
- 🎯 **Goal** - What you want to achieve
- 🔧 **Props** - Supporting materials (badges, emails)

---

## 👥 Common Personas

### IT Support
```
Role: IT Help Desk Technician
Goal: Password reset, remote access, software install

"Hi, this is Mike from IT. We're seeing some unusual 
activity on your account and need to verify your 
credentials to prevent a lockout."
```

### Vendor/Contractor
```
Role: Third-party vendor (HVAC, security, cleaning)
Goal: Physical access, network access

"I'm here from [Vendor Company] for the scheduled 
maintenance. Can you let me into the server room?"
```

### New Employee
```
Role: New hire who needs help
Goal: Information gathering, building access

"Hi, I just started in marketing and I'm having 
trouble accessing the shared drive. Could you help?"
```

### Executive/Authority
```
Role: CEO, CFO, senior manager
Goal: Urgent requests, wire transfers, access

"This is urgent. The CEO needs this report immediately 
and I need you to bypass normal procedures."
```

### Delivery Person
```
Role: FedEx, UPS, courier
Goal: Physical access, package drop-off

"I have a package that requires a signature. 
Can you let me in to the reception?"
```

---

## 📋 Pretext Scenarios

### Phone Pretexts

#### IT Password Reset
```
"Hello, this is [Name] from the IT help desk. We've 
detected your account was compromised in a recent 
security incident. I need to verify your identity and 
help you reset your password. Can you confirm your 
current password so I can validate it's really you?"
```

#### Vendor Verification
```
"Hi, I'm calling from [Vendor] accounts payable. We're 
updating our records and need to verify your company's 
banking details for the next payment. Can you confirm 
the account number you have on file for us?"
```

#### HR Survey
```
"Hello, I'm conducting an employee satisfaction survey 
for HR. The survey is anonymous, but I need to verify 
you're an employee. Can you tell me your department 
and manager's name?"
```

### In-Person Pretexts

#### Fire Inspector
```
"Hi, I'm from the city fire marshal's office. We're 
conducting surprise inspections. I'll need access to 
your server room to check the fire suppression system."
```

#### Job Interview
```
"I'm here for the 2 PM interview with HR. While I wait, 
could you point me to the restroom?" 
(Use opportunity to scout the building)
```

#### Maintenance Worker
```
"We got a call about a water leak in this area. 
Can you show me where the server room is? I need to 
check the pipes running above the ceiling."
```

---

## 🧠 Psychological Principles

### Authority
```
✓ Use titles: "IT Director", "Security Manager"
✓ Dress the part: suits, uniforms, badges
✓ Speak confidently
✓ Reference policies and procedures
```

### Urgency
```
✓ "This needs to happen before the CEO's meeting at 3"
✓ "The system will lock you out in 10 minutes"
✓ "We're under audit and need this immediately"
✓ Create time pressure to prevent verification
```

### Reciprocity
```
✓ Do a small favor first
✓ "I just helped resolve that printer issue..."
✓ "I brought coffee for everyone..."
✓ Create obligation to return the favor
```

### Social Proof
```
✓ "Everyone else has already done this"
✓ "Your manager John already approved this"
✓ "The whole marketing team completed the form"
✓ Reference others who complied
```

### Liking
```
✓ Find common ground
✓ Mirror body language
✓ Compliment authentically
✓ Build rapport before asking
```

---

## 🔧 Props & Supporting Materials

### Physical Props
```
□ Fake ID badges
□ Business cards
□ Clipboard with official-looking forms
□ Hi-vis vest / uniform
□ Toolbox or laptop bag
□ Printed emails/authorization letters
□ Vendor paperwork
```

### Digital Props
```
□ Spoofed caller ID
□ Fake email address (similar domain)
□ Fake LinkedIn profile
□ Cloned company website
□ Forged authorization emails
□ Fake meeting invites
```

### Documentation
```
□ Service ticket number
□ Work order
□ Authorization form
□ Reference number
□ Manager's name (from OSINT)
```

---

## 🔍 OSINT for Pretexting

### Target Research
```bash
# LinkedIn for employee info
- Names, titles, departments
- Reporting structure
- Recent posts/activity

# Company website
- Executive team
- Org chart
- Office locations
- Vendor/partner info

# Press releases
- Recent news, acquisitions
- Executive changes
```

### Useful Information
```
□ Employee names and titles
□ Department structure
□ Manager names
□ Email format (first.last@company.com)
□ Phone numbers
□ Office locations
□ Vendor relationships
□ Recent company events
```

---

## 📱 Physical Social Engineering

### Tailgating
```
Technique: Follow authorized person through secure door

"Oh thanks, I forgot my badge today!"
"Can you hold the door? My hands are full."
"I'm here with [Name] from [Department]."
```

### Piggybacking
```
Technique: Get authorized person to let you in

"Hi, I'm from [Vendor]. They're expecting me but the 
receptionist stepped away. Could you let me in?"
```

### Dumpster Diving
```
Target: Discarded documents, hardware

□ Organization charts
□ Employee lists
□ Financial documents
□ Password notes
□ Old hardware with data
```

### USB Drops (Baiting)
```
Technique: Leave infected USB drives

Locations:
□ Parking lot
□ Reception area
□ Break rooms
□ Near smoking areas

Label ideas:
□ "Confidential - HR"
□ "Salary Info 2024"
□ "Layoff List"
□ "Private Photos"
```

---

## 📝 Script Templates

### Phone Script - IT Support
```
[Ring ring]
Target: "Hello?"
You: "Hi, is this [Target Name]?"
Target: "Yes."
You: "Great, this is [Your Name] from the IT security 
team. We've been monitoring some unusual login 
attempts on your account from an overseas IP address. 
We want to make sure it wasn't you. Were you trying 
to log in from [Random Country] today?"
Target: "No, that wasn't me!"
You: "I thought so. For security, I need to verify 
your account. Can you confirm your username and 
we'll reset your password to lock out the attacker?"
```

### Email Script - Executive Request
```
Subject: Urgent - Wire Transfer Needed

Hi [Name],

I'm in a meeting and can't call. I need you to 
process an urgent wire transfer for a time-sensitive 
acquisition. 

Please transfer $45,000 to the following account:
[Details]

I'll explain when I'm out of this meeting. Please 
confirm once completed.

[Executive Name]
Sent from my iPhone
```

---

## ⚠️ Legal & Ethical Considerations

### Rules of Engagement
```
□ Always have written authorization
□ Define clear scope and boundaries
□ No accessing personal accounts
□ No blackmail or coercion
□ Document everything
□ Have emergency contacts
□ Get-out-of-jail letter on hand
```

### What to Avoid
```
✗ Impersonating law enforcement
✗ Making real threats
✗ Accessing systems beyond scope
✗ Stealing actual money/assets
✗ Creating legal liability
✗ Causing psychological harm
```

---

## 📋 Pretexting Checklist

```markdown
Preparation:
□ Research target organization (OSINT)
□ Identify key personnel
□ Develop persona and backstory
□ Prepare supporting materials/props
□ Practice script and responses
□ Have backup stories ready

Execution:
□ Stay calm and confident
□ Control the conversation
□ Be ready to pivot if challenged
□ Don't oversell or provide too much detail
□ Know when to abort

Post-Engagement:
□ Document what worked/didn't
□ Secure any captured data
□ Report findings
□ Provide recommendations
```

---

## 🔗 Related Cheatsheets

- [Phishing](./Phishing.md)
- [OSINT - Google Dorking](../Google-Dorking/README.md)

---

**Back to Overview:** [🎭 Social Engineering](./README.md)
