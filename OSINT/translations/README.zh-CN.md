# 🔍 OSINT（开源情报）速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

> **译注：** OSINT 指开源情报；OPSEC 指行动安全。原文将端口扫描归入“主动 OSINT”，这里应理解为主动侦察，它与仅查阅公开资料的被动收集不同。工具清单、免费／付费分类和数据覆盖范围均保留原文信息，实际可用性需要按服务当前条件核对。

```
 ██████╗ ███████╗██╗███╗   ██╗████████╗
██╔═══██╗██╔════╝██║████╗  ██║╚══██╔══╝
██║   ██║███████╗██║██╔██╗ ██║   ██║   
██║   ██║╚════██║██║██║╚██╗██║   ██║   
╚██████╔╝███████║██║██║ ╚████║   ██║   
 ╚═════╝ ╚══════╝╚═╝╚═╝  ╚═══╝   ╚═╝   
```

> **开源情报（Open Source Intelligence，OSINT）**——收集和分析公开可获取的信息。

---

## 📑 目录

| 主题 | 说明 | 指南 |
|-------|-------------|-------|
| **人员搜索** | 查找个人相关信息 | [📄 查看](./People-Search.zh-CN.md) |
| **电子邮件 OSINT** | 电子邮件信息收集与验证 | [📄 查看](./Email-OSINT.zh-CN.md) |
| **社交媒体** | 社交媒体调查 | [📄 查看](./Social-Media-OSINT.zh-CN.md) |
| **域名与 IP** | 域名与 IP 信息收集 | [📄 查看](./Domain-IP-OSINT.zh-CN.md) |
| **图像 OSINT** | 以图搜图与图像分析 | [📄 查看](./Image-OSINT.zh-CN.md) |

---

## 🎯 OSINT 方法论

```
┌─────────────────────────────────────────────────────────────┐
│                    OSINT 生命周期                           │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  1. 需求定义   →  明确情报需求                              │
│         ↓                                                   │
│  2. 收集       →  从信息源获取原始数据                      │
│         ↓                                                   │
│  3. 处理       →  将数据转换为可用格式                      │
│         ↓                                                   │
│  4. 分析       →  评估发现并建立关联                        │
│         ↓                                                   │
│  5. 分发       →  报告和呈现情报                            │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## 🛠️ OSINT 必备工具

### 搜索引擎
| 工具 | URL | 用途 |
|------|-----|---------|
| **Google** | https://google.com | 通用搜索 |
| **Bing** | bing.com | 备选搜索引擎 |
| **DuckDuckGo** | duckduckgo.com | 注重隐私 |
| **Yandex** | yandex.com | 俄罗斯搜索引擎（适合图像检索） |
| **Baidu** | baidu.com | 中文搜索 |

### 人员搜索
| 工具 | URL | 用途 |
|------|-----|---------|
| **Pipl** | pipl.com | 深入搜索人员信息 |
| **Spokeo** | spokeo.com | 美国人员信息搜索 |
| **WhitePages** | whitepages.com | 电话与地址查询 |
| **ThatsThem** | thatsthem.com | 免费人员信息搜索 |
| **BeenVerified** | beenverified.com | 背景调查 |

### 电子邮件工具
| 工具 | URL | 用途 |
|------|-----|---------|
| **Hunter.io** | hunter.io | 查找企业电子邮箱 |
| **Have I Been Pwned** | haveibeenpwned.com | 数据泄露查询 |
| **EmailRep** | emailrep.io | 电子邮箱信誉查询 |
| **Phonebook.cz** | phonebook.cz | 电子邮箱与域名搜索 |
| **h8mail** | GitHub | 搜索泄露的凭据 |

### 社交媒体
| 工具 | URL | 用途 |
|------|-----|---------|
| **Sherlock** | GitHub | 用户名搜索 |
| **Social Searcher** | social-searcher.com | 社交媒体搜索 |
| **Namechk** | namechk.com | 用户名可用性查询 |
| **KnowEm** | knowem.com | 用户名检查 |
| **Xquik** | https://github.com/Xquik-dev/x-twitter-scraper | 独立的 X（Twitter）数据 API，提供搜索、关注者导出、监控与 MCP 功能。 |

### 域名与 IP 工具
| 工具 | URL | 用途 |
|------|-----|---------|
| **Shodan** | shodan.io | 设备搜索 |
| **Censys** | censys.io | 互联网资产扫描 |
| **SecurityTrails** | securitytrails.com | DNS 历史记录 |
| **ViewDNS** | viewdns.info | DNS 工具 |
| **BuiltWith** | builtwith.com | 技术栈识别 |

---

## 🔥 常用命令速查

### theHarvester
```bash
# 收集电子邮箱与子域名
theHarvester -d target.com -l 500 -b all

# 指定来源
theHarvester -d target.com -b google,linkedin,twitter

# 保存到文件
theHarvester -d target.com -b all -f results.html
```

### Sherlock（用户名搜索）
```bash
# 搜索全部平台
python3 sherlock.py username

# 指定网站
python3 sherlock.py username --site twitter instagram facebook

# 输出到文件
python3 sherlock.py username --output results.txt
```

### Maltego
```
# Transform（信息转换）类型：
- 人员 → 电子邮箱
- 电子邮箱 → 域名
- 域名 → IP
- IP → 地理位置
- 社交媒体资料
```

### Recon-ng
```bash
# 启动 recon-ng
recon-ng

# 创建工作区
workspaces create target_name

# 加载模块
modules load recon/domains-hosts/hackertarget
modules load recon/contacts-credentials/hibp_breach

# 设置目标
options set SOURCE target.com

# 运行模块
run
```

---

## 📊 OSINT 分类

### 被动 OSINT
```
不与目标直接交互：
- 搜索引擎
- 社交媒体
- 公开记录
- Archive.org
- WHOIS 数据
```

### 主动 OSINT
```
与目标存在一定交互：
- 端口扫描
- DNS 查询
- 网页抓取
- 电子邮箱验证
```

---

## 🔒 行动安全（OPSEC）

### 最佳实践
```
✓ 使用 VPN/Tor
✓ 创建调查用独立身份账户
✓ 使用虚拟机
✓ 不要使用个人账户
✓ 定期清理 Cookie 与缓存
✓ 使用独立的浏览器配置文件
✓ 完整记录调查过程
```

### 调查用独立身份账户（Sock Puppet Accounts）
```
- 使用生成的身份信息
- 在不同平台保持身份设定一致
- 使用已有一定注册时间的账户（通常更易获信任）
- 填写可信的资料细节
- 不要与真实身份建立关联
```

---

## 📋 OSINT 检查清单

```markdown
## 目标：[Name/Company/Domain]

### 人员
□ 姓名的不同写法
□ 电话号码
□ 电子邮箱地址
□ 实际地址
□ 社交媒体资料
□ 职业经历
□ 教育经历
□ 照片／图像

### 公司
□ 域名注册信息（WHOIS）
□ 技术栈
□ 员工名单
□ 电子邮箱格式
□ 社交媒体足迹
□ 新闻／新闻稿
□ 财务信息
□ 实际办公地点

### 技术信息
□ IP 地址
□ 子域名
□ 开放端口／服务
□ DNS 记录
□ SSL 证书
□ 历史数据
```

---

## 🔗 OSINT 框架

### OSINT Framework
```
https://osintframework.com/
- 按类别组织
- 链接到数百种工具
- 可视化树状结构
```

### IntelTechniques
```
https://inteltechniques.com/tools/
- Michael Bazzell 的工具
- 自定义搜索工具
- 调查工作流程
```

---

## 📚 本仓库相关指南

- [Google Dorking](../../Google-Dorking/README.md)
- [GitHub Dorking](../../GitHub-Dorking/README.md)
- [Shodan](../../Shodan/README.md)
- [Amass](../../Amass/README.md)
- [Subfinder](../../Subfinder/README.md)

---

**返回主目录:** [🔴 Hacking 速查表](../../README.md)
