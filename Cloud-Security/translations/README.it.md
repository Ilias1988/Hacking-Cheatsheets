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

## 🎯 Cos'è il Cloud Security Testing?

Il cloud security testing consiste nell'identificare vulnerabilità nell'infrastruttura cloud, tra cui:
- 🪣 **Storage** - S3, Blob, GCS mal configurati
- 🔑 **IAM** - Difetti nella gestione di identità e accessi
- 🖥️ **Compute** - Vulnerabilità in EC2, VM, serverless
- 🌐 **Network** - VPC, security group, firewall
- 🔐 **Secrets** - Credenziali e chiavi esposte

---

## 📖 Guide alla Sicurezza Cloud

| Provider Cloud | Descrizione | Guida |
|----------------|-------------|-------|
| **AWS** | Pentesting Amazon Web Services | [📄 Visualizza](./AWS-Pentesting.it.md) |
| **Azure** | Pentesting Microsoft Azure | [📄 Visualizza](./Azure-Pentesting.it.md) |
| **GCP** | Pentesting Google Cloud Platform | [📄 Visualizza](./GCP-Pentesting.it.md) |

---

## 🛠️ Strumenti Essenziali per la Sicurezza Cloud

| Strumento | Scopo | Piattaforma |
|-----------|-------|-------------|
| **ScoutSuite** | Audit di sicurezza multi-cloud | Tutte |
| **Prowler** | Valutazione sicurezza AWS | AWS |
| **CloudSploit** | Scanner di sicurezza cloud | Tutte |
| **Pacu** | Framework di exploitation AWS | AWS |
| **CloudBrute** | Enumerazione cloud | Tutte |
| **S3Scanner** | Enumerazione bucket S3 | AWS |
| **AzureHound** | Percorsi di attacco Azure AD | Azure |
| **gcp_enum** | Enumerazione GCP | GCP |

### Installazione Rapida Strumenti
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

## 📊 Vulnerabilità Cloud Comuni

| Vulnerabilità | Descrizione | Impatto |
|---------------|-------------|---------|
| **Public S3/Blob** | Storage accessibile pubblicamente | Data leak |
| **IAM troppo permissivo** | Permessi * | Compromissione totale account |
| **Metadata esposti** | Accesso a 169.254.169.254 | Furto credenziali |
| **Credenziali hardcoded** | Chiavi in codice/config | Account takeover |
| **SSRF su metadata** | Accesso ai metadata cloud | Furto credenziali |
| **Funzioni insicure** | Lambda/Funzioni mal configurate | Code execution |

---

## 🔗 Riferimento Rapido: Endpoint Metadata Cloud

| Provider | Endpoint Metadata |
|----------|-------------------|
| **AWS** | `http://169.254.169.254/latest/meta-data/` |
| **Azure** | `http://169.254.169.254/metadata/instance?api-version=2021-02-01` |
| **GCP** | `http://169.254.169.254/computeMetadata/v1/` |
| **DigitalOcean** | `http://169.254.169.254/metadata/v1/` |

---

## 📚 Risorse

- [OWASP Cloud Security](https://owasp.org/www-project-cloud-security/)
- [AWS Security Best Practices](https://docs.aws.amazon.com/security/)
- [Azure Security Documentation](https://docs.microsoft.com/it-it/azure/security/)
- [GCP Security](https://cloud.google.com/security)
- [HackTricks Cloud](https://cloud.hacktricks.xyz/)

---

<p align="center">
  <b>☁️ Hackera il Cloud in modo responsabile!</b>
</p>
