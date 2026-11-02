# ☁️ Cloudflare 绕过测试速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
   ██████╗██╗      ██████╗ ██╗   ██╗██████╗ ███████╗██╗      █████╗ ██████╗ ███████╗
  ██╔════╝██║     ██╔═══██╗██║   ██║██╔══██╗██╔════╝██║     ██╔══██╗██╔══██╗██╔════╝
  ██║     ██║     ██║   ██║██║   ██║██║  ██║█████╗  ██║     ███████║██████╔╝█████╗  
  ██║     ██║     ██║   ██║██║   ██║██║  ██║██╔══╝  ██║     ██╔══██║██╔══██╗██╔══╝  
  ╚██████╗███████╗╚██████╔╝╚██████╔╝██████╔╝██║     ███████╗██║  ██║██║  ██║███████╗
   ╚═════╝╚══════╝ ╚═════╝  ╚═════╝ ╚═════╝ ╚═╝     ╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝
                    ██████╗ ██╗   ██╗██████╗  █████╗ ███████╗███████╗
                    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔══██╗██╔════╝██╔════╝
                    ██████╔╝ ╚████╔╝ ██████╔╝███████║███████╗███████╗
                    ██╔══██╗  ╚██╔╝  ██╔═══╝ ██╔══██║╚════██║╚════██║
                    ██████╔╝   ██║   ██║     ██║  ██║███████║███████║
                    ╚═════╝    ╚═╝   ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝
```

---

<a id="-detect-cloudflare"></a>
## 🎯 识别 Cloudflare

### 响应头
```bash
curl -I https://target.com

# 查找以下响应头：
cf-ray: XXXXX-YYY
server: cloudflare
cf-cache-status: DYNAMIC
cf-request-id: XXXX
```

### DNS
```bash
dig target.com

# Cloudflare IP 地址范围：
# 104.16.0.0/12
# 172.64.0.0/13
# 131.0.72.0/22
# 173.245.48.0/20
# 103.21.244.0/22
# 103.22.200.0/22
# 103.31.4.0/22
```

---

> **地址范围勘误：** 原文上方的 `104.16.0.0/12` 是旧范围；[Cloudflare 当前官方 IPv4 清单](https://www.cloudflare.com/ips-v4)列出 `104.16.0.0/13` 和 `104.24.0.0/14`，与文末清单一致。实际判断应以官方 IPv4/IPv6 清单为准；地址不在 Cloudflare 范围内也不能单独证明它是源站。

<a id="-find-origin-ip"></a>
## 🌐 查找源站 IP

### 1. DNS 历史记录工具

| 服务 | URL |
|---------|-----|
| SecurityTrails | https://securitytrails.com |
| ViewDNS | https://viewdns.info/iphistory |
| DNSDumpster | https://dnsdumpster.com |
| CrimeFlare | http://www.crimeflare.org:82/cfs.html |
| CloudFlair | GitHub 工具 |

```bash
# 使用 CloudFlair
python3 cloudflair.py target.com

# Bypass-Firewalls-by-DNS-History
./bypass-firewalls-by-DNS-history.sh target.com
```

### 2. 子域名发现
```bash
# 许多子域名可能暴露源站
subfinder -d target.com | httpx -ip

# 可能指向源站 IP 的常见子域名：
mail.target.com
ftp.target.com
cpanel.target.com
webmail.target.com
direct.target.com
origin.target.com
server.target.com
api.target.com
stage.target.com
dev.target.com
old.target.com
```

### 3. SSL 证书搜索
```bash
# 在 Censys.io 中搜索
# 查询：parsed.names: target.com AND NOT ip: 104.*

# 证书透明度日志
curl -s "https://crt.sh/?q=%.target.com&output=json" | jq -r '.[].common_name' | sort -u

# 搜索 Shodan
shodan search "ssl.cert.subject.cn:target.com"
```

### 4. 邮件分析
```bash
# 向不存在的地址发送邮件
# 检查退信中的邮件头

# 也可以订阅邮件通讯
# 检查 Received: 邮件头中是否有源站 IP

# 检查邮件头中的以下字段：
Received: from [ORIGIN_IP]
X-Originating-IP: [IP]
X-PM-Message-Id: (sometimes contains origin)
```

邮件头示例中，`X-PM-Message-Id` 后的英文括注意为“有时包含源站信息”，并非固定字段值。

### 5. 网站图标哈希（Shodan）
```python
import mmh3
import requests
import codecs

# 计算网站图标哈希
response = requests.get('https://target.com/favicon.ico')
favicon = codecs.encode(response.content, 'base64')
hash = mmh3.hash(favicon)
print(f"Favicon hash: {hash}")

# 搜索 Shodan：http.favicon.hash:{hash}
```

```bash
# Shodan 命令行
shodan search "http.favicon.hash:HASH_HERE"
```

### 6. 历史 WHOIS 记录
```bash
# 检查历史 WHOIS 记录中的旧 IP 地址
# whoxy.com
# domaintools.com
```

### 7. 网站源代码
```bash
# 检查以下位置是否有硬编码的 IP 地址：
# - JavaScript 文件
# - HTML 注释
# - API 端点
# - 错误信息

curl -s https://target.com | grep -oE '[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+'

# 检查 JS 文件
curl -s https://target.com/main.js | grep -oE 'https?://[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+'
```

### 8. MX 记录
```bash
dig MX target.com

# 邮件服务器可能与源站使用同一个 IP
nslookup mail.target.com
```

### 9. SPF 记录
```bash
dig TXT target.com | grep spf

# SPF 记录可能包含源站 IP
# v=spf1 ip4:1.2.3.4 include:_spf.google.com ~all
```

---

<a id="-direct-connection"></a>
## 🔥 直接连接源站

> **线索说明：** DNS 历史、邮件服务器、证书或图标相似性只能给出候选地址。CloudFlair 作者也把结果称为[可能的源站](https://github.com/christophetd/CloudFlair)，仍需结合目标站点的实际内容与配置验证。

### 测试发现的 IP
```bash
# 找到候选源站 IP 后：
curl -k -H "Host: target.com" https://ORIGIN_IP/

# 也可以编辑 /etc/hosts
echo "ORIGIN_IP target.com" >> /etc/hosts

# 然后按域名正常访问
curl https://target.com
```

> **HTTPS 说明：** 直接请求 IP 时，仅修改 `Host` 并不会同时设置目标域名的 TLS SNI；`-k` 也不会修正 SNI。[curl 官方说明](https://everything.curl.dev/usingcurl/connections/name.html)指出，SNI 来自 URL 中的主机名，因此原文的直接 IP 示例可能无法命中正确虚拟主机。

### 验证源站
```bash
# 检查内容是否相同
diff <(curl -s https://target.com) <(curl -sk -H "Host: target.com" https://ORIGIN_IP/)

# 检查 SSL 证书
openssl s_client -connect ORIGIN_IP:443 -servername target.com
```

---

<a id="-cloudflare-waf-bypass"></a>
## 🛡️ Cloudflare WAF 绕过测试

### 绕过速率限制
```http
# 轮换以下请求头
X-Forwarded-For: RANDOM_IP
CF-Connecting-IP: RANDOM_IP
True-Client-IP: RANDOM_IP
X-Real-IP: RANDOM_IP
```

> **请求头说明：** [Cloudflare 文档](https://developers.cloudflare.com/fundamentals/reference/http-headers/)说明，`CF-Connecting-IP` 由边缘节点传给源站，`X-Forwarded-For` 也会经过代理处理。客户端轮换这些字段不等于更换真实连接 IP；只有特定的错误信任或限流配置才可能受影响，不能视为通用绕过。

### 绕过机器人防护
```bash
# 使用真实浏览器的 User-Agent
curl -A "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36" https://target.com

# 使用 curl-impersonate
curl_chrome116 https://target.com
```

### Turnstile／质询绕过测试
```bash
# 使用浏览器自动化工具
# - Puppeteer + stealth 插件
# - Playwright
# - undetected-chromedriver

# FlareSolverr（Docker）
docker run -p 8191:8191 flaresolverr/flaresolverr
```

---

> **质询说明：** 修改 User-Agent、模拟 TLS 指纹或使用自动化浏览器都不保证通过机器人防护。Turnstile 还要求[服务端验证令牌](https://developers.cloudflare.com/turnstile/get-started/server-side-validation/)，令牌有时效且只能使用一次；[FlareSolverr 项目](https://github.com/FlareSolverr/FlareSolverr)也列有 CAPTCHA 支持限制。

<a id="-tools"></a>
## 🛠️ 工具

| 工具 | 用途 |
|------|---------|
| **CloudFlair** | 通过 Censys 查找源站 |
| **CrimeFlare** | 源站 IP 数据库 |
| **Bypass-Firewalls-by-DNS-History** | DNS 历史记录检查 |
| **FlareSolverr** | 浏览器质询处理工具 |
| **curl-impersonate** | 模拟浏览器 TLS 指纹 |
| **CloudBunny** | Cloudflare 绕过工具 |

> **版本与服务说明：** [CloudFlair 作者](https://github.com/christophetd/CloudFlair)说明，自 `2024` 年末起，免费 Censys 账户无法使用该工具所需的 API。原文安装和搜索示例均保留，API 权限、查询字段及工具兼容性需按所用服务版本核对。

### CloudFlair 用法
```bash
git clone https://github.com/christophetd/CloudFlair
cd CloudFlair
pip install -r requirements.txt

# 设置 Censys API 凭据
export CENSYS_API_ID=YOUR_ID
export CENSYS_API_SECRET=YOUR_SECRET

python cloudflair.py target.com
```

---

<a id="-quick-reference"></a>
## 📊 速查表

### 源站 IP 检查清单
```markdown
- [ ] DNS 历史记录（SecurityTrails、ViewDNS）
- [ ] 子域名（mail、ftp、cpanel、dev）
- [ ] SSL 证书（Censys、crt.sh）
- [ ] Shodan（网站图标哈希、SSL）
- [ ] 邮件头
- [ ] MX/SPF 记录
- [ ] 源代码分析
- [ ] 历史 WHOIS 记录
- [ ] IP 地址范围对比（是否位于 Cloudflare 地址范围之外）
```

### Cloudflare IP 地址范围
```
173.245.48.0/20
103.21.244.0/22
103.22.200.0/22
103.31.4.0/22
141.101.64.0/18
108.162.192.0/18
190.93.240.0/20
188.114.96.0/20
197.234.240.0/22
198.41.128.0/17
162.158.0.0/15
104.16.0.0/13
104.24.0.0/14
172.64.0.0/13
131.0.72.0/22
```

---

<a id="-resources"></a>
## 📚 参考资源

- [Cloudflare IP 地址范围](https://www.cloudflare.com/ips/)
- [CloudFlair 工具](https://github.com/christophetd/CloudFlair)
- [CrimeFlare](http://www.crimeflare.org:82/cfs.html)
- [FlareSolverr](https://github.com/FlareSolverr/FlareSolverr)

---

<p align="center">
  <b>☁️ 查找源站！</b><br>
  <i>仅限经授权的测试！</i>
</p>
