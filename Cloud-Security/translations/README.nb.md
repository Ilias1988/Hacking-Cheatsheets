# ☁️ Hurtigreferanse for skysikkerhet

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 🎯 Hva er sikkerhetstesting av skytjenester?

Sikkerhetstesting av skytjenester går ut på å finne sårbarheter i skyinfrastrukturen, blant annet:
- 🪣 **Lagring** – feilkonfigurasjoner i S3, Blob og GCS
- 🔑 **IAM** – svakheter i identitets- og tilgangsstyring
- 🖥️ **Databehandling** – sårbarheter i EC2, virtuelle maskiner og serverløse tjenester
- 🌐 **Nettverk** – VPC, sikkerhetsgrupper og brannmurer
- 🔐 **Hemmeligheter (secrets)** – eksponerte påloggingsopplysninger og nøkler

---

> **Begreper:** En bøtte (bucket) er en lagringsbeholder. Kartlegging (enumeration) betyr systematisk innsamling av opplysninger om ressurser og rettigheter. SSRF er server-side request forgery: en sårbarhet som kan få serveren til å sende forespørsler på angriperens vegne.

## 📖 Veiledninger i skysikkerhet

| Skyleverandør | Beskrivelse | Veiledning |
|----------------|-------------|-------|
| **AWS** | Penetrasjonstesting av Amazon Web Services | [📄 Vis](./AWS-Pentesting.nb.md) |
| **Azure** | Penetrasjonstesting av Microsoft Azure | [📄 Vis](./Azure-Pentesting.nb.md) |
| **GCP** | Penetrasjonstesting av Google Cloud Platform | [📄 Vis](./GCP-Pentesting.nb.md) |

---

## 🛠️ Viktige verktøy for skysikkerhet

| Verktøy | Formål | Plattform |
|------|---------|----------|
| **ScoutSuite** | Sikkerhetsrevisjon på tvers av skyleverandører | Alle |
| **Prowler** | Sikkerhetsvurdering av AWS | AWS |
| **CloudSploit** | Sikkerhetsskanner for skyen | Alle |
| **Pacu** | Rammeverk for utnyttelse av sårbarheter i AWS | AWS |
| **CloudBrute** | Kartlegging av skyressurser | Alle |
| **S3Scanner** | Kartlegging av S3-bøtter | AWS |
| **AzureHound** | Angrepsveier i Azure AD | Azure |
| **gcp_enum** | Kartlegging av GCP | GCP |

> **Versjonsmerknad:** Prowler støtter også andre skyleverandører enn AWS. Installasjonseksemplet nedenfor er fra en eldre versjon; se [gjeldende Prowler-veiledning](https://github.com/prowler-cloud/prowler/blob/master/README.md) for versjonen du bruker.

### Rask installasjon av verktøy
```bash
# ScoutSuite (flere skyleverandører)
pip install scoutsuite
scout aws

# Prowler (AWS)
git clone https://github.com/prowler-cloud/prowler
cd prowler && ./prowler

# Pacu (utnyttelse av sårbarheter i AWS)
git clone https://github.com/RhinoSecurityLabs/pacu
cd pacu && python3 pacu.py

# CloudBrute (kartlegging)
go install github.com/0xsha/CloudBrute@latest
```

---

## 📊 Vanlige sårbarheter i skyen

| Sårbarhet | Beskrivelse | Konsekvens |
|---------------|-------------|--------|
| **Offentlig S3/Blob** | Lagring som er offentlig tilgjengelig | Datalekkasje |
| **For vide IAM-rettigheter** | Rettigheter med * | Full kompromittering av kontoen |
| **Eksponerte metadata** | 169.254.169.254 er tilgjengelig | Tyveri av påloggingsopplysninger |
| **Hardkodede påloggingsopplysninger** | Nøkler i kode eller konfigurasjonsfiler | Kontoovertakelse |
| **SSRF mot metadata** | Tilgang til metadata i skyen | Tyveri av påloggingsopplysninger |
| **Usikre funksjoner** | Feilkonfigurasjon av Lambda/Functions | Kodekjøring |

---

## 🔗 Hurtigreferanse: metadataendepunkter i skyen

| Leverandør | Metadataendepunkt |
|----------|-------------------|
| **AWS** | `http://169.254.169.254/latest/meta-data/` |
| **Azure** | `http://169.254.169.254/metadata/instance?api-version=2021-02-01` |
| **GCP** | `http://169.254.169.254/computeMetadata/v1/` |
| **DigitalOcean** | `http://169.254.169.254/metadata/v1/` |

---

## 📚 Ressurser

- [OWASP om skysikkerhet](https://owasp.org/www-project-cloud-security/)
- [Anbefalte sikkerhetstiltak for AWS](https://docs.aws.amazon.com/security/)
- [Sikkerhetsdokumentasjon for Azure](https://docs.microsoft.com/en-us/azure/security/)
- [Sikkerhet i GCP](https://cloud.google.com/security)
- [HackTricks Cloud](https://cloud.hacktricks.xyz/)

---

<p align="center">
  <b>☁️ Test sikkerheten i skyen på en ansvarlig måte!</b>
</p>
