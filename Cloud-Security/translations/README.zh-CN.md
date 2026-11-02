# ☁️ 云安全速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

> **译注：** IAM 指身份与访问管理；元数据端点的可达性本身不等于凭据泄露，仍取决于请求头、令牌要求及实例身份配置。文中的工具安装示例保留原文，工具入口和支持平台应以各项目文档为准。

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

## 🎯 什么是云安全测试？

云安全测试旨在识别云基础设施中的漏洞，涵盖以下方面：
- 🪣 **存储**：S3、Blob、GCS 配置错误
- 🔑 **IAM（身份与访问管理）**：身份及访问控制缺陷
- 🖥️ **计算**：EC2、虚拟机及无服务器计算漏洞
- 🌐 **网络**：VPC（虚拟私有云）、安全组、防火墙
- 🔐 **机密信息**：泄露的凭据和密钥

---

## 📖 云安全指南

| 云服务提供商 | 说明 | 指南 |
|----------------|-------------|-------|
| **AWS** | Amazon Web Services 渗透测试 | [📄 查看](./AWS-Pentesting.zh-CN.md) |
| **Azure** | Microsoft Azure 渗透测试 | [📄 查看](./Azure-Pentesting.zh-CN.md) |
| **GCP** | Google Cloud 平台 渗透测试 | [📄 查看](./GCP-Pentesting.zh-CN.md) |

---

## 🛠️ 必备云安全工具

| 工具 | 用途 | 平台 |
|------|---------|----------|
| **ScoutSuite** | 多云安全审计 | 全部 |
| **Prowler** | AWS 安全评估 | AWS |
| **CloudSploit** | 云安全扫描器 | 全部 |
| **Pacu** | AWS 漏洞利用框架 | AWS |
| **CloudBrute** | 云资源枚举 | 全部 |
| **S3Scanner** | S3 存储桶枚举 | AWS |
| **AzureHound** | Azure AD 攻击路径 | Azure |
| **gcp_enum** | GCP 资源枚举 | GCP |

### 快速安装工具
```bash
# ScoutSuite（多云）
pip install scoutsuite
scout aws

# Prowler (AWS)
git clone https://github.com/prowler-cloud/prowler
cd prowler && ./prowler

# Pacu（AWS 漏洞利用）
git clone https://github.com/RhinoSecurityLabs/pacu
cd pacu && python3 pacu.py

# CloudBrute（枚举）
go install github.com/0xsha/CloudBrute@latest
```

---

## 📊 常见云安全漏洞

| 漏洞 | 说明 | 影响 |
|---------------|-------------|--------|
| **公开的 S3/Blob** | 可公开访问的存储 | 数据泄露 |
| **IAM 权限过大** | `*` 通配符权限 | 账户完全失陷 |
| **元数据暴露** | 可访问 169.254.169.254 | 凭据窃取 |
| **硬编码凭据** | 代码或配置中的密钥 | 账户接管 |
| **通过 SSRF 访问元数据** | 访问云元数据 | 凭据窃取 |
| **不安全的函数** | Lambda/Functions 配置错误 | 代码执行 |

---

## 🔗 速查：云元数据端点

| 提供商 | 元数据端点 |
|----------|-------------------|
| **AWS** | `http://169.254.169.254/latest/meta-data/` |
| **Azure** | `http://169.254.169.254/metadata/instance?api-version=2021-02-01` |
| **GCP** | `http://169.254.169.254/computeMetadata/v1/` |
| **DigitalOcean** | `http://169.254.169.254/metadata/v1/` |

---

## 📚 参考资源

- [OWASP 云安全](https://owasp.org/www-project-cloud-security/)
- [AWS 安全最佳实践](https://docs.aws.amazon.com/security/)
- [Azure 安全文档](https://docs.microsoft.com/en-us/azure/security/)
- [GCP 安全](https://cloud.google.com/security)
- [HackTricks Cloud](https://cloud.hacktricks.xyz/)

---

<p align="center">
  <b>☁️ 负责任地开展云安全测试！</b>
</p>
