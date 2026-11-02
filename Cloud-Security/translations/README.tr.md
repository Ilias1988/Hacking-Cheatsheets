# ☁️ Bulut Güvenliği Kopya Kağıdı (Cloud Security Cheatsheets)

```
 ██████╗██╗      ██████╗ ██╗   ██╗██████╗     ███████╗███████╗ ██████╗██╗   ██╗██████╗ ██╗████████╗██╗   ██╗
██╔════╝██║     ██╔═══██╗██║   ██║██╔══██╗    ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██║╚══██╔══╝╚██╗ ██╔╝
██║     ██║     ██║   ██║██║   ██║██║  ██║    ███████╗█████╗  ██║     ██║   ██║██████╔╝██║   ██║    ╚████╔╝ 
██║     ██║     ██║   ██║██║   ██║██║  ██║    ╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██║   ██║     ╚██╔╝  
╚██████╗███████╗╚██████╔╝╚██████╔╝██████╔╝    ███████║███████╗╚██████╗╚██████╔╝██║  ██║██║   ██║      ██║   
 ╚═════╝╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝     ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝   ╚═╝      ╚═╝   
```

---

## 🎯 Bulut Güvenliği Testi Nedir?

Bulut güvenliği testi, bulut altyapısındaki güvenlik açıklarının tespit edilmesini kapsar:
- 🪣 **Depolama (Storage)** - S3, Blob, GCS yapılandırma hataları
- 🔑 **IAM** - Kimlik ve erişim yönetimi (Identity & Access Management) açıkları
- 🖥️ **İşlem/Hesaplama (Compute)** - EC2, VM ve sunucusuz (serverless) güvenlik açıkları
- 🌐 **Ağ (Network)** - VPC, güvenlik grupları ve güvenlik duvarları
- 🔐 **Gizli Bilgiler (Secrets)** - İfşa olmuş kimlik bilgileri ve API anahtarları

---

## 📖 Bulut Güvenliği Kılavuzları

| Bulut Sağlayıcısı | Açıklama | Kılavuz |
|-------------------|----------|---------|
| **AWS** | Amazon Web Services sızma testi | [📄 Görüntüle](./AWS-Pentesting.tr.md) |
| **Azure** | Microsoft Azure sızma testi | [📄 Görüntüle](./Azure-Pentesting.tr.md) |
| **GCP** | Google Cloud Platform sızma testi | [📄 Görüntüle](./GCP-Pentesting.tr.md) |

---

## 🛠️ Temel Bulut Güvenliği Araçları

| Araç | Amacı | Platform |
|------|-------|----------|
| **ScoutSuite** | Çoklu bulut güvenlik denetimi | Tümü |
| **Prowler** | AWS güvenlik değerlendirmesi | AWS |
| **CloudSploit** | Bulut güvenlik tarayıcısı | Tümü |
| **Pacu** | AWS sömürü (exploitation) çatısı | AWS |
| **CloudBrute** | Bulut varlık numaralandırma | Tümü |
| **S3Scanner** | S3 bucket numaralandırma | AWS |
| **AzureHound** | Azure AD saldırı yolları analizi | Azure |
| **gcp_enum** | GCP numaralandırma aracı | GCP |

### Hızlı Araç Kurulumu
```bash
# ScoutSuite (Çoklu bulut)
pip install scoutsuite
scout aws

# Prowler (AWS)
git clone https://github.com/prowler-cloud/prowler
cd prowler && ./prowler

# Pacu (AWS Sömürü Aracı)
git clone https://github.com/RhinoSecurityLabs/pacu
cd pacu && python3 pacu.py

# CloudBrute (Numaralandırma)
go install github.com/0xsha/CloudBrute@latest
```

---

## 📊 Yaygın Bulut Güvenlik Açıkları

| Güvenlik Açığı | Açıklama | Etki |
|----------------|----------|------|
| **Public S3/Blob** | Halka açık depolama alanları | Veri sızıntısı |
| **Aşırı Yetkili IAM** | Geniş kapsamlı (*) izinler | Tüm hesabın ele geçirilmesi |
| **Açık Üstveri Servisi** | 169.254.169.254 erişilebilirliği | Kimlik bilgisi hırsızlığı |
| **Sabit Kodlanmış Kimlik Bilgileri** | Kod/yapılandırmada unutulan anahtarlar | Hesap ele geçirme |
| **Üstveriye Yönelik SSRF** | Bulut üstverisine erişim sağlayan SSRF | Kimlik bilgisi hırsızlığı |
| **Güvensiz Fonksiyonlar** | Lambda/Functions yapılandırma hataları | Kod çalıştırma |

---

## 🔗 Hızlı Başvuru: Bulut Üstveri (Metadata) Uç Noktaları

| Sağlayıcı | Üstveri Uç Noktası (Metadata Endpoint) |
|-----------|----------------------------------------|
| **AWS** | `http://169.254.169.254/latest/meta-data/` |
| **Azure** | `http://169.254.169.254/metadata/instance?api-version=2021-02-01` |
| **GCP** | `http://169.254.169.254/computeMetadata/v1/` |
| **DigitalOcean** | `http://169.254.169.254/metadata/v1/` |

---

## 📚 Kaynaklar

- [OWASP Cloud Security](https://owasp.org/www-project-cloud-security/)
- [AWS Security Best Practices](https://docs.aws.amazon.com/security/)
- [Azure Security Documentation](https://docs.microsoft.com/en-us/azure/security/)
- [GCP Security](https://cloud.google.com/security)
- [HackTricks Cloud](https://cloud.hacktricks.xyz/)

---

<p align="center">
  <b>☁️ Bulutu Sorumlu Bir Şekilde Test Edin!</b>
</p>
