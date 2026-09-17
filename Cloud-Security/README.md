# ☁️ Cloud Security Cheatsheets

```
 ██████╗██╗      ██████╗ ██╗   ██╗██████╗     ███████╗███████╗ ██████╗██╗   ██╗██████╗ ██╗████████╗██╗   ██╗
██╔════╝██║     ██╔═══██╗██║   ██║██╔══██╗    ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██║╚══██╔══╝╚██╗ ██╔╝
██║     ██║     ██║   ██║██║   ██║██║  ██║    ███████╗█████╗  ██║     ██║   ██║██████╔╝██║   ██║    ╚████╔╝ 
██║     ██║     ██║   ██║██║   ██║██║  ██║    ╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██║   ██║     ╚██╔╝  
╚██████╗███████╗╚██████╔╝╚██████╔╝██████╔╝    ███████║███████╗╚██████╗╚██████╔╝██║  ██║██║   ██║      ██║   
 ╚═════╝╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝     ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝   ╚═╝      ╚═╝   
```

---

## 🎯 What is Cloud Security Testing?

Cloud security testing involves identifying vulnerabilities in cloud infrastructure including:
- 🪣 **Storage** - S3, Blob, GCS misconfigurations
- 🔑 **IAM** - Identity and access management flaws
- 🖥️ **Compute** - EC2, VMs, serverless vulnerabilities
- 🌐 **Network** - VPC, security groups, firewalls
- 🔐 **Secrets** - Exposed credentials, keys

---

## 📖 Cloud Security Guides

| Cloud Provider | Description | Guide |
|----------------|-------------|-------|
| **AWS** | Amazon Web Services pentesting | [📄 View](./AWS-Pentesting.md) |
| **Azure** | Microsoft Azure pentesting | [📄 View](./Azure-Pentesting.md) |
| **GCP** | Google Cloud Platform pentesting | [📄 View](./GCP-Pentesting.md) |

---

## 🛠️ Essential Cloud Security Tools

| Tool | Purpose | Platform |
|------|---------|----------|
| **ScoutSuite** | Multi-cloud security auditing | All |
| **Prowler** | AWS security assessment | AWS |
| **CloudSploit** | Cloud security scanner | All |
| **Pacu** | AWS exploitation framework | AWS |
| **CloudBrute** | Cloud enumeration | All |
| **S3Scanner** | S3 bucket enumeration | AWS |
| **AzureHound** | Azure AD attack paths | Azure |
| **gcp_enum** | GCP enumeration | GCP |

### Quick Tool Install
```bash
# ScoutSuite (Multi-cloud)
pip install scoutsuite
scout aws

# Prowler (AWS)
git clone https://github.com/prowler-cloud/prowler
cd prowler && ./prowler

# Pacu (AWS Exploitation)
git clone https://github.com/RhinoSecurityLabs/pacu
cd pacu && python3 pacu.py

# CloudBrute (Enumeration)
go install github.com/0xsha/CloudBrute@latest
```

---

## 📊 Common Cloud Vulnerabilities

| Vulnerability | Description | Impact |
|---------------|-------------|--------|
| **Public S3/Blob** | Publicly accessible storage | Data leak |
| **Overly permissive IAM** | * permissions | Full account compromise |
| **Exposed metadata** | 169.254.169.254 accessible | Credential theft |
| **Hardcoded credentials** | Keys in code/configs | Account takeover |
| **SSRF to metadata** | Access cloud metadata | Credential theft |
| **Insecure Functions** | Lambda/Functions misconfig | Code execution |

---

## 🔗 Quick Reference: Cloud Metadata Endpoints

| Provider | Metadata Endpoint |
|----------|-------------------|
| **AWS** | `http://169.254.169.254/latest/meta-data/` |
| **Azure** | `http://169.254.169.254/metadata/instance?api-version=2021-02-01` |
| **GCP** | `http://169.254.169.254/computeMetadata/v1/` |
| **DigitalOcean** | `http://169.254.169.254/metadata/v1/` |

---

## 📚 Resources

- [OWASP Cloud Security](https://owasp.org/www-project-cloud-security/)
- [AWS Security Best Practices](https://docs.aws.amazon.com/security/)
- [Azure Security Documentation](https://docs.microsoft.com/en-us/azure/security/)
- [GCP Security](https://cloud.google.com/security)
- [HackTricks Cloud](https://cloud.hacktricks.xyz/)

---

<p align="center">
  <b>☁️ Hack the Cloud Responsibly!</b>
</p>
