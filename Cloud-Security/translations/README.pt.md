# ☁️ Guias de referência de segurança na cloud

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 🎯 O que são testes de segurança na cloud?

> **Terminologia:** cloud significa infraestrutura e serviços de computação na nuvem; um bucket é um contentor de armazenamento de objetos. SSRF (Server-Side Request Forgery) é a manipulação de pedidos efetuados pelo servidor. Os exemplos de instalação são os do original; confirma a sintaxe na documentação da versão utilizada.

Os testes de segurança na cloud consistem em identificar vulnerabilidades na infraestrutura, incluindo:
- 🪣 **Armazenamento** — Configurações incorretas de S3, Blob e GCS
- 🔑 **IAM** — Falhas na gestão de identidades e acessos (Identity and Access Management)
- 🖥️ **Computação** — Vulnerabilidades em EC2, máquinas virtuais e serviços serverless (sem gestão direta de servidores)
- 🌐 **Rede** — VPC, grupos de segurança e firewalls
- 🔐 **Segredos** — Credenciais e chaves expostas

---

## 📖 Guias de segurança na cloud

| Provedor cloud | Descrição | Guia |
|----------------|-------------|-------|
| **AWS** | Testes de intrusão na Amazon Web Services | [📄 Ver](./AWS-Pentesting.pt.md) |
| **Azure** | Testes de intrusão no Microsoft Azure | [📄 Ver](./Azure-Pentesting.pt.md) |
| **GCP** | Testes de intrusão na Google Cloud Platform | [📄 Ver](./GCP-Pentesting.pt.md) |

---

## 🛠️ Ferramentas essenciais de segurança na cloud

| Ferramenta | Finalidade | Plataforma |
|------|---------|----------|
| **ScoutSuite** | Auditoria de segurança em várias clouds | Todas |
| **Prowler** | Avaliação de segurança da AWS | AWS |
| **CloudSploit** | Análise de segurança na cloud | Todas |
| **Pacu** | Framework de exploração da AWS | AWS |
| **CloudBrute** | Enumeração de recursos na cloud | Todas |
| **S3Scanner** | Enumeração de buckets S3 | AWS |
| **AzureHound** | Percursos de ataque no Azure AD | Azure |
| **gcp_enum** | Enumeração de recursos GCP | GCP |

### Instalação rápida das ferramentas
```bash
# ScoutSuite (várias clouds)
pip install scoutsuite
scout aws

# Prowler (AWS)
git clone https://github.com/prowler-cloud/prowler
cd prowler && ./prowler

# Pacu (exploração da AWS)
git clone https://github.com/RhinoSecurityLabs/pacu
cd pacu && python3 pacu.py

# CloudBrute (enumeração)
go install github.com/0xsha/CloudBrute@latest
```

---

## 📊 Vulnerabilidades comuns na cloud

| Vulnerabilidade | Descrição | Impacto |
|---------------|-------------|--------|
| **S3/Blob público** | Armazenamento acessível publicamente | Fuga de dados |
| **IAM com permissões excessivas** | Permissões * | Comprometimento total da conta |
| **Metadados expostos** | 169.254.169.254 acessível | Roubo de credenciais |
| **Credenciais incorporadas no código** | Chaves no código ou em configurações | Apropriação da conta |
| **SSRF para metadados** | Acesso aos metadados da cloud | Roubo de credenciais |
| **Funções inseguras** | Configurações incorretas de Lambda/Functions | Execução de código |

---

## 🔗 Referência rápida: endpoints de metadados da cloud

| Provedor | Endpoint de metadados |
|----------|-------------------|
| **AWS** | `http://169.254.169.254/latest/meta-data/` |
| **Azure** | `http://169.254.169.254/metadata/instance?api-version=2021-02-01` |
| **GCP** | `http://169.254.169.254/computeMetadata/v1/` |
| **DigitalOcean** | `http://169.254.169.254/metadata/v1/` |

---

## 📚 Recursos

- [Segurança na cloud — OWASP](https://owasp.org/www-project-cloud-security/)
- [Boas práticas de segurança da AWS](https://docs.aws.amazon.com/security/)
- [Documentação de segurança do Azure](https://docs.microsoft.com/en-us/azure/security/)
- [Segurança da GCP](https://cloud.google.com/security)
- [HackTricks Cloud](https://cloud.hacktricks.xyz/)

---

<p align="center">
  <b>☁️ Explora a cloud com responsabilidade!</b>
</p>
