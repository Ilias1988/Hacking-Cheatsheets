# 📱 Mobile Security Cheatsheets

```
███╗   ███╗ ██████╗ ██████╗ ██╗██╗     ███████╗    ███████╗███████╗ ██████╗██╗   ██╗██████╗ ██╗████████╗██╗   ██╗
████╗ ████║██╔═══██╗██╔══██╗██║██║     ██╔════╝    ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██║╚══██╔══╝╚██╗ ██╔╝
██╔████╔██║██║   ██║██████╔╝██║██║     █████╗      ███████╗█████╗  ██║     ██║   ██║██████╔╝██║   ██║    ╚████╔╝ 
██║╚██╔╝██║██║   ██║██╔══██╗██║██║     ██╔══╝      ╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██║   ██║     ╚██╔╝  
██║ ╚═╝ ██║╚██████╔╝██████╔╝██║███████╗███████╗    ███████║███████╗╚██████╗╚██████╔╝██║  ██║██║   ██║      ██║   
╚═╝     ╚═╝ ╚═════╝ ╚═════╝ ╚═╝╚══════╝╚══════╝    ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝   ╚═╝      ╚═╝   
```

---

## 🎯 Mobile Application Security Testing

Mobile app security testing involves identifying vulnerabilities in:
- 📦 **Application Package** - APK/IPA analysis
- 💾 **Data Storage** - Insecure local storage
- 🔐 **Authentication** - Weak auth mechanisms
- 🌐 **Network** - Insecure communications
- 🔑 **Cryptography** - Weak encryption
- 🛡️ **Platform** - OS-specific vulnerabilities

---

## 📖 Mobile Security Guides

| Platform | Description | Guide |
|----------|-------------|-------|
| **Android** | APK analysis, Frida, root detection bypass | [📄 View](./Android-Pentesting.md) |
| **iOS** | IPA analysis, jailbreak, Objection | [📄 View](./iOS-Pentesting.md) |

---

## 🛠️ Essential Mobile Security Tools

| Tool | Platform | Purpose |
|------|----------|---------|
| **Frida** | Both | Dynamic instrumentation |
| **Objection** | Both | Runtime mobile exploration |
| **MobSF** | Both | Automated security analysis |
| **jadx** | Android | APK decompilation |
| **apktool** | Android | APK reverse engineering |
| **Burp Suite** | Both | Traffic interception |
| **Ghidra** | Both | Binary analysis |
| **drozer** | Android | Security assessment |

### Quick Tool Install
```bash
# Frida
pip install frida-tools

# Objection
pip install objection

# MobSF (Docker)
docker pull opensecurity/mobile-security-framework-mobsf
docker run -p 8000:8000 opensecurity/mobile-security-framework-mobsf

# jadx
# Download from https://github.com/skylot/jadx/releases

# apktool
# Download from https://ibotpeaches.github.io/Apktool/install/

# drozer
pip install drozer
```

---

## 📊 OWASP Mobile Top 10 (2024)

| # | Vulnerability | Description |
|---|---------------|-------------|
| M1 | Improper Credential Usage | Hardcoded credentials, insecure storage |
| M2 | Inadequate Supply Chain Security | Malicious SDKs, compromised libraries |
| M3 | Insecure Authentication/Authorization | Weak auth, broken access control |
| M4 | Insufficient Input/Output Validation | Injection, XSS in WebViews |
| M5 | Insecure Communication | No TLS, certificate issues |
| M6 | Inadequate Privacy Controls | PII leakage, excessive permissions |
| M7 | Insufficient Binary Protections | No obfuscation, tampering |
| M8 | Security Misconfiguration | Debug enabled, insecure defaults |
| M9 | Insecure Data Storage | Plaintext storage, logs |
| M10 | Insufficient Cryptography | Weak algorithms, hardcoded keys |

---

## 🔗 Quick Reference: Frida Commands

```bash
# List running processes
frida-ps -U

# Attach to process
frida -U -f com.app.package

# Spawn with script
frida -U -f com.app.package -l script.js --no-pause

# Objection explore
objection -g com.app.package explore
```

---

## 📚 Resources

- [OWASP Mobile Security](https://owasp.org/www-project-mobile-security/)
- [OWASP MASTG](https://mas.owasp.org/MASTG/)
- [Frida Documentation](https://frida.re/docs/)
- [HackTricks Mobile](https://book.hacktricks.xyz/mobile-pentesting)

---

<p align="center">
  <b>📱 Hack Mobile Apps Responsibly!</b>
</p>
