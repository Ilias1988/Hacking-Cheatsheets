# 📱 移动安全速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./README.tr.md) • [🇬🇷 Ελληνικά](./README.el.md)

```
███╗   ███╗ ██████╗ ██████╗ ██╗██╗     ███████╗    ███████╗███████╗ ██████╗██╗   ██╗██████╗ ██╗████████╗██╗   ██╗
████╗ ████║██╔═══██╗██╔══██╗██║██║     ██╔════╝    ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██║╚══██╔══╝╚██╗ ██╔╝
██╔████╔██║██║   ██║██████╔╝██║██║     █████╗      ███████╗█████╗  ██║     ██║   ██║██████╔╝██║   ██║    ╚████╔╝ 
██║╚██╔╝██║██║   ██║██╔══██╗██║██║     ██╔══╝      ╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██║   ██║     ╚██╔╝  
██║ ╚═╝ ██║╚██████╔╝██████╔╝██║███████╗███████╗    ███████║███████╗╚██████╗╚██████╔╝██║  ██║██║   ██║      ██║   
╚═╝     ╚═╝ ╚═════╝ ╚═════╝ ╚═╝╚══════╝╚══════╝    ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝   ╚═╝      ╚═╝   
```

---

## 🎯 移动应用安全测试

移动应用安全测试旨在识别以下方面的漏洞：
- 📦 **应用程序包** - APK/IPA 分析
- 💾 **数据存储** - 不安全的本地存储
- 🔐 **身份验证** - 薄弱的身份验证机制
- 🌐 **网络** - 不安全的通信
- 🔑 **密码学** - 薄弱的加密方式
- 🛡️ **平台** - 操作系统特有的漏洞

---

## 📖 移动安全指南

| 平台 | 说明 | 指南 |
|----------|-------------|-------|
| **Android** | APK 分析、Frida、root 检测绕过 | [📄 查看](./Android-Pentesting.zh-CN.md) |
| **iOS** | IPA 分析、越狱、Objection | [📄 查看](./iOS-Pentesting.zh-CN.md) |

---

## 🛠️ 移动安全常用工具

| 工具 | 平台 | 用途 |
|------|----------|---------|
| **Frida** | 两者 | 动态插桩（运行时观察或修改程序行为） |
| **Objection** | 两者 | 移动应用运行时探索 |
| **MobSF** | 两者 | 自动化安全分析 |
| **jadx** | Android | APK 反编译 |
| **apktool** | Android | APK 逆向工程 |
| **Burp Suite** | 两者 | 流量拦截 |
| **Ghidra** | 两者 | 二进制分析 |
| **drozer** | Android | 安全评估 |

### 快速安装工具
```bash
# Frida
pip install frida-tools

# Objection
pip install objection

# MobSF (Docker)
docker pull opensecurity/mobile-security-framework-mobsf
docker run -p 8000:8000 opensecurity/mobile-security-framework-mobsf

# jadx
# 从 https://github.com/skylot/jadx/releases 下载

# apktool
# 从 https://ibotpeaches.github.io/Apktool/install/ 下载

# drozer
pip install drozer
```

---

## 📊 OWASP Mobile Top 10 (2024)

> **范围说明：** 本表保留原文指定的 2024 版分类；它用于提示移动应用风险领域，不等同于完整测试方法或覆盖性证明。

| # | 漏洞 | 说明 |
|---|---------------|-------------|
| M1 | 凭据使用不当 | 硬编码凭据、不安全的存储 |
| M2 | 供应链安全不足 | 恶意 SDK、被入侵的库 |
| M3 | 不安全的身份验证与授权 | 身份验证薄弱、访问控制失效 |
| M4 | 输入与输出验证不足 | 注入、WebView 中的 XSS |
| M5 | 不安全的通信 | 未使用 TLS、证书问题 |
| M6 | 隐私保护措施不足 | 个人身份信息（PII）泄露、权限过多 |
| M7 | 二进制保护不足 | 缺少混淆、篡改风险 |
| M8 | 安全配置错误 | 启用调试、不安全的默认设置 |
| M9 | 不安全的数据存储 | 明文存储、日志泄露 |
| M10 | 密码学保护不足 | 薄弱算法、硬编码密钥 |

---

## 🔗 快速参考：Frida 命令

> **译注（Frida 版本）：** 原文把 `-f` 示例标为“附加”，但它实际用于启动目标进程。`--no-pause` 属于旧版参数；当前 CLI 默认在启动后恢复主线程，并提供 `--pause` 保持暂停。这里保留原文命令，使用时需核对工具版本。参见 [Frida CLI 源码](https://github.com/frida/frida-tools/blob/main/frida_tools/repl.py)。

```bash
# 列出运行中的进程
frida-ps -U

# 附加到进程（此处 -f 实际为启动进程，见译注）
frida -U -f com.app.package

# 启动进程并加载脚本
frida -U -f com.app.package -l script.js --no-pause

# 使用 Objection 探索
objection -g com.app.package explore
```

---

## 📚 参考资源

- [OWASP 移动安全](https://owasp.org/www-project-mobile-security/)
- [OWASP MASTG](https://mas.owasp.org/MASTG/)
- [Frida 文档](https://frida.re/docs/)
- [HackTricks Mobile](https://book.hacktricks.xyz/mobile-pentesting)

---

<p align="center">
  <b>📱 负责任地开展移动应用安全测试！</b>
</p>
