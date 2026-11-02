# ☁️ Cloud-Security-Spickzettel

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
 ██████╗██╗      ██████╗ ██╗   ██╗██████╗     ███████╗███████╗ ██████╗██╗   ██╗██████╗ ██╗████████╗██╗   ██╗
██╔════╝██║     ██╔═══██╗██║   ██║██╔══██╗    ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██║╚══██╔══╝╚██╗ ██╔╝
██║     ██║     ██║   ██║██║   ██║██║  ██║    ███████╗█████╗  ██║     ██║   ██║██████╔╝██║   ██║    ╚████╔╝ 
██║     ██║     ██║   ██║██║   ██║██║  ██║    ╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██║   ██║     ╚██╔╝  
╚██████╗███████╗╚██████╔╝╚██████╔╝██████╔╝    ███████║███████╗╚██████╗╚██████╔╝██║  ██║██║   ██║      ██║   
 ╚═════╝╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝     ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝   ╚═╝      ╚═╝   
```

---

## 🎯 Was bedeutet Cloud Security Testing?

Beim Cloud Security Testing werden Schwachstellen in der Cloud-Infrastruktur gesucht, unter anderem in folgenden Bereichen:
- 🪣 **Speicher** – Fehlkonfigurationen in S3, Blob Storage und GCS
- 🔑 **IAM** – Fehler in der Identitäts- und Zugriffsverwaltung (Identity and Access Management)
- 🖥️ **Compute** – Schwachstellen in EC2, virtuellen Maschinen und Serverless-Diensten
- 🌐 **Netzwerk** – VPC, Sicherheitsgruppen und Firewalls
- 🔐 **Secrets** – offengelegte Zugangsdaten und Schlüssel

---

## 📖 Leitfäden zur Cloud-Sicherheit

| Cloud-Anbieter | Beschreibung | Leitfaden |
|----------------|-------------|-------|
| **AWS** | Penetrationstests für Amazon Web Services | [📄 Anzeigen](./AWS-Pentesting.de.md) |
| **Azure** | Penetrationstests für Microsoft Azure | [📄 Anzeigen](./Azure-Pentesting.de.md) |
| **GCP** | Penetrationstests für Google Cloud Platform | [📄 Anzeigen](./GCP-Pentesting.de.md) |

---

## 🛠️ Wichtige Werkzeuge für Cloud-Sicherheit

| Tool | Zweck | Plattform |
|------|---------|----------|
| **ScoutSuite** | Sicherheitsprüfungen über mehrere Clouds hinweg | Alle |
| **Prowler** | Bewertung der AWS-Sicherheit | AWS |
| **CloudSploit** | Scanner für Cloud-Sicherheit | Alle |
| **Pacu** | Framework zur Ausnutzung von AWS-Schwachstellen | AWS |
| **CloudBrute** | Cloud-Enumeration (systematische Bestandsaufnahme) | Alle |
| **S3Scanner** | Enumeration von S3-Buckets | AWS |
| **AzureHound** | Angriffspfade in Azure AD | Azure |
| **gcp_enum** | Enumeration in GCP | GCP |

> **Versionshinweis:** Die Installationsbeispiele stammen aus dem Original und können ältere Releases voraussetzen. Prowler unterstützt inzwischen mehrere Clouds; die Tabelle nennt den hier behandelten AWS-Schwerpunkt. Der historische Aufruf `./prowler` entspricht nicht jeder aktuellen Installation. Siehe die [offizielle Prowler-Dokumentation](https://github.com/prowler-cloud/prowler/blob/master/README.md).

### Werkzeuge schnell installieren
```bash
# ScoutSuite (mehrere Clouds)
pip install scoutsuite
scout aws

# Prowler (AWS)
git clone https://github.com/prowler-cloud/prowler
cd prowler && ./prowler

# Pacu (Ausnutzung von AWS-Schwachstellen)
git clone https://github.com/RhinoSecurityLabs/pacu
cd pacu && python3 pacu.py

# CloudBrute (Enumeration)
go install github.com/0xsha/CloudBrute@latest
```

---

## 📊 Häufige Cloud-Schwachstellen

| Schwachstelle | Beschreibung | Auswirkung |
|---------------|-------------|--------|
| **Öffentliche S3-Buckets/Blob-Speicher** | Öffentlich zugänglicher Speicher | Datenabfluss |
| **Zu weitreichende IAM-Berechtigungen** | Berechtigungen mit * | Vollständige Kompromittierung des Kontos |
| **Offengelegte Metadaten** | 169.254.169.254 ist erreichbar | Diebstahl von Zugangsdaten |
| **Fest im Code hinterlegte Zugangsdaten** | Schlüssel im Code oder in Konfigurationen | Kontoübernahme |
| **Zugriff auf Metadaten über SSRF** | Zugriff auf Cloud-Metadaten | Diebstahl von Zugangsdaten |
| **Unsichere Functions** | Fehlkonfiguration von Lambda/Functions | Codeausführung |

---

## 🔗 Kurzreferenz: Endpunkte für Cloud-Metadaten

| Anbieter | Metadaten-Endpunkt |
|----------|-------------------|
| **AWS** | `http://169.254.169.254/latest/meta-data/` |
| **Azure** | `http://169.254.169.254/metadata/instance?api-version=2021-02-01` |
| **GCP** | `http://169.254.169.254/computeMetadata/v1/` |
| **DigitalOcean** | `http://169.254.169.254/metadata/v1/` |

---

> **Einordnung:** Ein erreichbarer Metadaten-Endpunkt allein bedeutet noch keinen Zugangsdatenverlust. Rollen bzw. verwaltete Identitäten, Token-Anforderungen und die Möglichkeiten der jeweiligen SSRF-Schwachstelle bestimmen den Zugriff. Bei AWS kann IMDSv2 verpflichtend sein; siehe [AWS: IMDS-Optionen](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/configuring-instance-metadata-options.html).

## 📚 Weiterführende Ressourcen

- [OWASP Cloud Security](https://owasp.org/www-project-cloud-security/)
- [Bewährte Sicherheitspraktiken für AWS](https://docs.aws.amazon.com/security/)
- [Dokumentation zur Azure-Sicherheit](https://docs.microsoft.com/en-us/azure/security/)
- [GCP Security](https://cloud.google.com/security)
- [HackTricks Cloud](https://cloud.hacktricks.xyz/)

---

<p align="center">
  <b>☁️ Teste die Cloud verantwortungsvoll!</b>
</p>
