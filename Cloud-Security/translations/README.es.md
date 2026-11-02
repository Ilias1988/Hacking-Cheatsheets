# ☁️ Guías rápidas de seguridad en la nube

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 🎯 ¿Qué son las pruebas de seguridad en la nube?

Las pruebas de seguridad en la nube consisten en identificar vulnerabilidades en la infraestructura cloud, entre ellas:
- 🪣 **Almacenamiento** - Configuraciones incorrectas de S3, Blob y GCS
- 🔑 **IAM** - Fallos en la gestión de identidades y accesos
- 🖥️ **Cómputo** - Vulnerabilidades en EC2, máquinas virtuales y servicios serverless (sin administración de servidores)
- 🌐 **Red** - VPC, grupos de seguridad y cortafuegos
- 🔐 **Secretos** - Credenciales y claves expuestas

---

## 📖 Guías de seguridad en la nube

| Proveedor cloud | Descripción | Guía |
|----------------|-------------|-------|
| **AWS** | Pruebas de penetración en Amazon Web Services | [📄 Ver](./AWS-Pentesting.es.md) |
| **Azure** | Pruebas de penetración en Microsoft Azure | [📄 Ver](./Azure-Pentesting.es.md) |
| **GCP** | Pruebas de penetración en Google Cloud Platform | [📄 Ver](./GCP-Pentesting.es.md) |

---

> **Terminología:** IAM es la gestión de identidades y accesos; un *bucket* es un contenedor de objetos. SSRF es la falsificación de peticiones del lado del servidor. Los endpoints de metadatos pueden requerir tokens o cabeceras específicas, como muestran las guías de cada proveedor.

## 🛠️ Herramientas esenciales de seguridad en la nube

| Herramienta | Finalidad | Plataforma |
|------|---------|----------|
| **ScoutSuite** | Auditoría de seguridad en múltiples nubes | Todas |
| **Prowler** | Evaluación de seguridad de AWS | AWS |
| **CloudSploit** | Escáner de seguridad en la nube | Todas |
| **Pacu** | Framework de explotación de AWS | AWS |
| **CloudBrute** | Enumeración de recursos cloud | Todas |
| **S3Scanner** | Enumeración de buckets de S3 | AWS |
| **AzureHound** | Rutas de ataque en Azure AD | Azure |
| **gcp_enum** | Enumeración de GCP | GCP |

### Instalación rápida de herramientas
```bash
# ScoutSuite (múltiples nubes)
pip install scoutsuite
scout aws

# Prowler (AWS)
git clone https://github.com/prowler-cloud/prowler
cd prowler && ./prowler

# Pacu (explotación de AWS)
git clone https://github.com/RhinoSecurityLabs/pacu
cd pacu && python3 pacu.py

# CloudBrute (enumeración)
go install github.com/0xsha/CloudBrute@latest
```

---

## 📊 Vulnerabilidades habituales en la nube

| Vulnerabilidad | Descripción | Impacto |
|---------------|-------------|--------|
| **S3/Blob públicos** | Almacenamiento accesible públicamente | Filtración de datos |
| **IAM con permisos excesivos** | Permisos * | Compromiso total de la cuenta |
| **Metadatos expuestos** | 169.254.169.254 accesible | Robo de credenciales |
| **Credenciales incrustadas en el código** | Claves en código o configuraciones | Toma de control de la cuenta |
| **SSRF contra metadatos** | Acceso a metadatos cloud | Robo de credenciales |
| **Funciones inseguras** | Configuración incorrecta de Lambda/Functions | Ejecución de código |

---

## 🔗 Referencia rápida: endpoints de metadatos cloud

| Proveedor | Endpoint de metadatos |
|----------|-------------------|
| **AWS** | `http://169.254.169.254/latest/meta-data/` |
| **Azure** | `http://169.254.169.254/metadata/instance?api-version=2021-02-01` |
| **GCP** | `http://169.254.169.254/computeMetadata/v1/` |
| **DigitalOcean** | `http://169.254.169.254/metadata/v1/` |

---

## 📚 Recursos

- [Seguridad en la nube de OWASP](https://owasp.org/www-project-cloud-security/)
- [Buenas prácticas de seguridad de AWS](https://docs.aws.amazon.com/security/)
- [Documentación de seguridad de Azure](https://docs.microsoft.com/en-us/azure/security/)
- [Seguridad de GCP](https://cloud.google.com/security)
- [HackTricks Cloud](https://cloud.hacktricks.xyz/)

---

<p align="center">
  <b>☁️ ¡Practica hacking en la nube de forma responsable!</b>
</p>
