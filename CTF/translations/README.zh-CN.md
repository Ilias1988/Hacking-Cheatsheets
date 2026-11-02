# 🏁 CTF 速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

> **译注：** CTF 是 Capture The Flag（夺旗赛）；flag 是题目要求提交的答案标记。这里的命令包含占位数据，必须按题目环境理解；例如 `"string"` 并不是完整、有效的 Base64 示例输入。编码格式参见 [RFC 4648](https://www.rfc-editor.org/rfc/rfc4648.html)。

```
 ██████╗████████╗███████╗
██╔════╝╚══██╔══╝██╔════╝
██║        ██║   █████╗  
██║        ██║   ██╔══╝  
╚██████╗   ██║   ██║     
 ╚═════╝   ╚═╝   ╚═╝     
 ██████╗██╗  ██╗███████╗ █████╗ ████████╗███████╗██╗  ██╗███████╗███████╗████████╗███████╗
██╔════╝██║  ██║██╔════╝██╔══██╗╚══██╔══╝██╔════╝██║  ██║██╔════╝██╔════╝╚══██╔══╝██╔════╝
██║     ███████║█████╗  ███████║   ██║   ███████╗███████║█████╗  █████╗     ██║   ███████╗
██║     ██╔══██║██╔══╝  ██╔══██║   ██║   ╚════██║██╔══██║██╔══╝  ██╔══╝     ██║   ╚════██║
╚██████╗██║  ██║███████╗██║  ██║   ██║   ███████║██║  ██║███████╗███████╗   ██║   ███████║
 ╚═════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚══════╝╚══════╝   ╚═╝   ╚══════╝
```

---

## 📑 目录

| 类别 | 说明 | 指南 |
|----------|-------------|-------|
| **Web** | SQLi、XSS、SSTI、LFI 与身份验证绕过 | [📄 查看](./Web-CTF.zh-CN.md) |
| **密码学** | RSA、AES、哈希与编码 | [📄 查看](./Crypto-CTF.zh-CN.md) |
| **逆向工程** | 反汇编、调试与补丁修改 | [📄 查看](./Reverse-Engineering-CTF.zh-CN.md) |
| **数字取证** | 隐写术、内存与磁盘分析 | [📄 查看](./Forensics-CTF.zh-CN.md) |
| **Pwn/Binary** | 缓冲区溢出、ROP 与 shellcode | [📄 查看](./Pwn-CTF.zh-CN.md) |

---

## 🎯 CTF 平台

| 平台 | URL | 类型 |
|----------|-----|------|
| **HackTheBox** | hackthebox.com | 靶机与 CTF |
| **TryHackMe** | tryhackme.com | 引导式学习 |
| **PicoCTF** | picoctf.org | 适合初学者 |
| **CTFtime** | ctftime.org | CTF 赛事日历 |
| **OverTheWire** | overthewire.org | 闯关练习 |
| **RootMe** | root-me.org | 挑战题目 |
| **CryptoHack** | cryptohack.org | 密码学挑战 |
| **pwnable.kr** | pwnable.kr | Pwn 挑战 |

---

## 🛠️ CTF 必备工具

| 类别 | 工具 |
|----------|-------|
| **Web** | Burp Suite, SQLMap, ffuf |
| **密码学** | CyberChef, RsaCtfTool, hashcat |
| **逆向工程** | Ghidra, IDA, Radare2, GDB |
| **数字取证** | Volatility, Autopsy, Binwalk |
| **Pwn** | pwntools, ROPgadget, gef |
| **隐写术** | steghide, zsteg, exiftool |

---

## 🔥 CTF 常用命令

### Web
```bash
# SQLi
sqlmap -u "http://url?id=1" --dbs

# 目录穷举
gobuster dir -u http://url -w /usr/share/wordlists/dirb/common.txt
```

### 密码学
```bash
# 哈希类型识别
hashid 'hash_string'
hash-identifier

# Base64 解码
echo "string" | base64 -d
```

### 数字取证
```bash
# 文件类型
file unknown_file
binwalk unknown_file

# 提取字符串
strings -n 6 file | grep -i flag
```

### Pwn
```bash
# 检查保护机制
checksec binary

# 使用 GDB 运行
gdb ./binary
```

---

## 📋 CTF 解题方法

```
1. 侦察
   - 仔细阅读题目描述
   - 下载全部文件
   - 识别题目类别（web／crypto／pwn 等）

2. 枚举
   - 运行合适的工具
   - 检查明显的漏洞
   - 在文件名与元数据中寻找线索

3. 利用
   - 运用已知技术
   - 使用 Google 搜索错误信息
   - 尝试常见 payload

4. 获取 flag
   - 查找 flag 格式：CTF{...}
   - 检查全部输出
   - 提交并验证
```

---

## 🏆 Flag 格式

```
常见 flag 格式：
- flag{...}
- FLAG{...}
- CTF{...}
- picoCTF{...}
- HTB{...}
- DUCTF{...}
- custom_flag_format
```

---

**返回主目录:** [🔴 Hacking 速查表](../../README.md)
