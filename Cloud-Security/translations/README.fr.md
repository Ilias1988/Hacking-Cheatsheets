# ☁️ Fiches pratiques de sécurité cloud

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
 ██████╗██╗      ██████╗ ██╗   ██╗██████╗     ███████╗███████╗ ██████╗██╗   ██╗██████╗ ██╗████████╗██╗   ██╗
██╔════╝██║     ██╔═══██╗██║   ██║██╔══██╗    ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██║╚══██╔══╝╚██╗ ██╔╝
██║     ██║     ██║   ██║██║   ██║██║  ██║    ███████╗█████╗  ██║     ██║   ██║██████╔╝██║   ██║    ╚████╔╝ 
██║     ██║     ██║   ██║██║   ██║██║  ██║    ╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██║   ██║     ╚██╔╝  
╚██████╗███████╗╚██████╔╝╚██████╔╝██████╔╝    ███████║███████╗╚██████╗╚██████╔╝██║  ██║██║   ██║      ██║   
 ╚═════╝╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝     ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝   ╚═╝      ╚═╝   
```

> **Terminologie :** un *bucket* est un conteneur de stockage ; IAM désigne la gestion des identités et des accès ; une SSRF est une falsification de requête côté serveur. Les procédures d’installation illustrent les versions du document source : vérifiez la documentation de chaque outil avant utilisation.

---

## 🎯 Qu’est-ce qu’un test de sécurité cloud ?

Les tests de sécurité cloud consistent à identifier les vulnérabilités des infrastructures cloud, notamment :
- 🪣 **Stockage** - Mauvaises configurations de S3, Blob et GCS
- 🔑 **IAM** - Failles de gestion des identités et des accès
- 🖥️ **Calcul** - Vulnérabilités d’EC2, des machines virtuelles et des services serverless (sans serveur à administrer)
- 🌐 **Réseau** - VPC, groupes de sécurité et pare-feu
- 🔐 **Secrets** - Identifiants et clés exposés

---

## 📖 Guides de sécurité cloud

| Fournisseur cloud | Description | Guide |
|----------------|-------------|-------|
| **AWS** | Tests d’intrusion sur Amazon Web Services | [📄 Consulter](./AWS-Pentesting.fr.md) |
| **Azure** | Tests d’intrusion sur Microsoft Azure | [📄 Consulter](./Azure-Pentesting.fr.md) |
| **GCP** | Tests d’intrusion sur Google Cloud Platform | [📄 Consulter](./GCP-Pentesting.fr.md) |

---

## 🛠️ Outils essentiels de sécurité cloud

| Outil | Utilisation | Plateforme |
|------|---------|----------|
| **ScoutSuite** | Audit de sécurité multicloud | Toutes |
| **Prowler** | Évaluation de la sécurité AWS | AWS |
| **CloudSploit** | Scanner de sécurité cloud | Toutes |
| **Pacu** | Framework d’exploitation AWS | AWS |
| **CloudBrute** | Énumération des ressources cloud | Toutes |
| **S3Scanner** | Énumération des buckets S3 | AWS |
| **AzureHound** | Chemins d’attaque dans Azure AD | Azure |
| **gcp_enum** | Énumération GCP | GCP |

### Installation rapide des outils

> **Note de version Prowler :** les appels `./prowler` du document source correspondent à une ancienne organisation du projet. Consultez le [README officiel de Prowler](https://github.com/prowler-cloud/prowler/blob/master/README.md) pour la procédure actuelle ; les commandes originales sont conservées ci-dessous.

```bash
# ScoutSuite (multicloud)
pip install scoutsuite
scout aws

# Prowler (AWS)
git clone https://github.com/prowler-cloud/prowler
cd prowler && ./prowler

# Pacu (exploitation AWS)
git clone https://github.com/RhinoSecurityLabs/pacu
cd pacu && python3 pacu.py

# CloudBrute (énumération)
go install github.com/0xsha/CloudBrute@latest
```

---

## 📊 Vulnérabilités cloud courantes

| Vulnérabilité | Description | Impact |
|---------------|-------------|--------|
| **S3/Blob publics** | Stockage accessible publiquement | Fuite de données |
| **Permissions IAM excessives** | Permissions * | Compromission complète du compte |
| **Métadonnées exposées** | 169.254.169.254 accessible | Vol d’identifiants |
| **Identifiants codés en dur** | Clés présentes dans le code ou les configurations | Prise de contrôle du compte |
| **SSRF vers les métadonnées** | Accès aux métadonnées cloud | Vol d’identifiants |
| **Fonctions non sécurisées** | Mauvaise configuration de Lambda/Functions | Exécution de code |

---

## 🔗 Référence rapide : points d’accès aux métadonnées cloud

| Fournisseur | Point d’accès aux métadonnées |
|----------|-------------------|
| **AWS** | `http://169.254.169.254/latest/meta-data/` |
| **Azure** | `http://169.254.169.254/metadata/instance?api-version=2021-02-01` |
| **GCP** | `http://169.254.169.254/computeMetadata/v1/` |
| **DigitalOcean** | `http://169.254.169.254/metadata/v1/` |

---

## 📚 Ressources

- [Sécurité cloud OWASP](https://owasp.org/www-project-cloud-security/)
- [Bonnes pratiques de sécurité AWS](https://docs.aws.amazon.com/security/)
- [Documentation de sécurité Azure](https://docs.microsoft.com/en-us/azure/security/)
- [Sécurité GCP](https://cloud.google.com/security)
- [HackTricks Cloud](https://cloud.hacktricks.xyz/)

---

<p align="center">
  <b>☁️ Testez la sécurité du cloud de manière responsable !</b>
</p>
