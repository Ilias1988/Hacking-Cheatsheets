# 👤 人员搜索 OSINT

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="People-Search.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="People-Search.el.md">🇬🇷 Ελληνικά</a>
</p>

> **版本说明：** 原文 Sherlock 示例包含旧的源码目录调用方式；当前发行版使用 `sherlock` 入口，参见 [官方安装说明](https://sherlockproject.xyz/installation)。PhoneInfoga 的现代项目采用 Go，原文的 `pip3 install phoneinfoga` 需按版本核对，参见 [项目仓库](https://github.com/sundowndev/phoneinfoga)。[Twint 仓库](https://github.com/twintproject/twint) 已于 2023 年归档，其示例仅保留作历史参考。Graph Search 段落同样属于旧功能示例，英文查询文本保持原样。

```
██████╗ ███████╗ ██████╗ ██████╗ ██╗     ███████╗
██╔══██╗██╔════╝██╔═══██╗██╔══██╗██║     ██╔════╝
██████╔╝█████╗  ██║   ██║██████╔╝██║     █████╗  
██╔═══╝ ██╔══╝  ██║   ██║██╔═══╝ ██║     ██╔══╝  
██║     ███████╗╚██████╔╝██║     ███████╗███████╗
╚═╝     ╚══════╝ ╚═════╝ ╚═╝     ╚══════╝╚══════╝
███████╗███████╗ █████╗ ██████╗  ██████╗██╗  ██╗ 
██╔════╝██╔════╝██╔══██╗██╔══██╗██╔════╝██║  ██║ 
███████╗█████╗  ███████║██████╔╝██║     ███████║ 
╚════██║██╔══╝  ██╔══██║██╔══██╗██║     ██╔══██║ 
███████║███████╗██║  ██║██║  ██║╚██████╗██║  ██║ 
╚══════╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝ 
```

---

## 🔍 人员搜索引擎

### 免费服务
| 工具 | URL | 覆盖范围 |
|------|-----|----------|
| **ThatsThem** | thatsthem.com | 美国 |
| **FastPeopleSearch** | fastpeoplesearch.com | 美国 |
| **TruePeopleSearch** | truepeoplesearch.com | 美国 |
| **Webmii** | webmii.com | 全球 |
| **PeekYou** | peekyou.com | 美国 |
| **Pipl** | pipl.com | 全球 |

### 付费服务
| 工具 | URL | 功能 |
|------|-----|----------|
| **Spokeo** | spokeo.com | 深入搜索与社交媒体信息 |
| **BeenVerified** | beenverified.com | 背景调查 |
| **Intelius** | intelius.com | 刑事记录 |
| **美国 搜索** | ussearch.com | 法院记录 |
| **Radaris** | radaris.com | 房产记录 |

---

## 🔎 使用 Google Dorking 搜索人员

### 基础搜索
```
# 姓名搜索
"John Smith" site:linkedin.com
"John Smith" site:facebook.com
"John Smith" site:twitter.com

# 姓名 + 地点
"John Smith" "New York"
"John Smith" "NYC" OR "New York City"

# 姓名 + 公司
"John Smith" "Microsoft"
"John Smith" site:microsoft.com

# 姓名 + 电子邮箱
"John Smith" "@gmail.com"
"John Smith" email
```

### 职业信息
```
# 简历搜索
"John Smith" filetype:pdf resume
"John Smith" filetype:doc CV
intitle:"curriculum vitae" "John Smith"

# 针对 LinkedIn 的搜索
site:linkedin.com/in "John Smith"
site:linkedin.com "John Smith" "Software Engineer"

# 职业资料
site:about.me "John Smith"
site:crunchbase.com "John Smith"
```

### 发现社交媒体账户
```
# 查找所有资料页
"John Smith" (site:facebook.com OR site:twitter.com OR site:instagram.com)

# 用户名搜索
"johnsmith123" site:twitter.com
"johnsmith123" site:reddit.com
"johnsmith123" site:github.com
```

---

## 🛠️ 命令行工具

### Sherlock（用户名搜索）
```bash
# 安装
git clone https://github.com/sherlock-project/sherlock.git
cd sherlock
pip3 install -r requirements.txt

# 跨平台搜索用户名
python3 sherlock username

# 多个用户名
python3 sherlock user1 user2 user3

# 仅搜索指定网站
python3 sherlock username --site twitter instagram facebook

# 导出结果
python3 sherlock username --output results.txt
python3 sherlock username --csv
python3 sherlock username --xlsx
```

### Maigret（增强型用户名搜索）
```bash
# 安装
pip3 install maigret

# 搜索
maigret username

# 使用 Tor
maigret username --tor

# JSON 输出
maigret username --json results.json
```

### holehe（电子邮箱注册状态检查）
```bash
# 安装
pip3 install holehe

# 跨服务检查电子邮箱
holehe email@example.com

# 仅显示已注册的网站
holehe email@example.com --only-used
```

---

## 📧 通过电子邮箱查找人员

### 通过电子邮箱寻找关联人员
```
1. 在 Google 中搜索邮箱："email@example.com"
2. 查询 HIBP：haveibeenpwned.com
3. 搜索泄露数据库
4. 检查社交媒体注册情况
5. 使用电子邮箱反向查询服务
```

### 电子邮箱格式发现
```bash
# 常见电子邮箱格式
first.last@company.com
flast@company.com
firstl@company.com
first_last@company.com
first@company.com

# Hunter.io 格式发现工具
https://hunter.io/email-finder
```

---

## 📱 电话号码 OSINT

### 电话号码查询服务
| 服务 | URL | 类型 |
|---------|-----|------|
| **TrueCaller** | truecaller.com | 来电身份识别 |
| **Sync.me** | sync.me | 来电身份识别 |
| **NumLookup** | numlookup.com | 免费查询 |
| **SpyDialer** | spydialer.com | 反向查询 |
| **CallerID Test** | calleridtest.com | 运营商信息 |

### 电话号码 Google Dork 查询
```
# 基础搜索
"555-123-4567"
"+1 555 123 4567"
"5551234567"

# 查找关联信息
"555-123-4567" site:facebook.com
"555-123-4567" "@"
"555-123-4567" "email"
```

### 电话号码开源情报调查
```bash
# phoneinfoga
pip3 install phoneinfoga
phoneinfoga scan -n "+1234567890"
phoneinfoga serve -p 8080  # 网页界面

# 使用 API
curl "https://api.numlookupapi.com/v1/validate/14155551234?apikey=YOUR_KEY"
```

---

## 🏠 地址与房产记录

### 房产搜索
| 服务 | URL | 覆盖范围 |
|---------|-----|----------|
| **Zillow** | zillow.com | 美国 |
| **Redfin** | redfin.com | 美国 |
| **县级评估机构** | [因地区而异] | 当地记录 |
| **Whitepages** | whitepages.com | 美国 |

### 公开记录
```
# 法院记录
PACER (pacer.gov)——联邦法院
州法院网站——州法院

# 选民记录
选民登记数据库（因州而异）

# 企业登记
各州州务卿网站
```

---

## 👥 社交网络分析

### LinkedIn 开源情报
```
# 个人资料搜索
site:linkedin.com/in "John Smith"
site:linkedin.com/in "John Smith" "Company Name"

# 查找关联人员
查看 "People Also Viewed"（其他人也浏览了）
查看共同联系人
公司员工名单
```

### Facebook 开源情报
```
# 个人资料 URL
facebook.com/search/people/?q=john%20smith
facebook.com/search/pages/?q=john%20smith

# Graph Search（现已受限）
People named "John" who work at "Company"
People who live in "City" and like "Page"
```

### 社交媒体分析工具
```bash
# Social Analyzer
pip3 install social-analyzer
social-analyzer --username "target" --metadata

# Twint (Twitter)
pip3 install twint
twint -u username --followers
twint -u username --following
```

---

## 📸 照片与人脸搜索

### 以图搜图
| 服务 | URL | 适用场景 |
|---------|-----|----------|
| **Google Images** | images.google.com | 通用 |
| **TinEye** | tineye.com | 精确匹配 |
| **Yandex** | yandex.com/images | 适合人脸搜索（原文评价） |
| **Bing Images** | bing.com/images | 可选的替代服务 |
| **PimEyes** | pimeyes.com | 人脸识别 |

### 人脸搜索
```
1. Yandex Images——适合寻找人脸（原文评价）
2. PimEyes——付费人脸识别
3. Social Catfish——面向社交信息的反向图像搜索
```

---

## 📋 人员搜索检查清单

```markdown
## 目标：[Full Name]

### 基本信息
□ 法定全名
□ 出生日期
□ 现居地址
□ 电话号码
□ 电子邮箱地址

### 网络足迹
□ LinkedIn 个人资料
□ Facebook 个人资料
□ Twitter/X 账户
□ Instagram
□ TikTok
□ 其他社交媒体
□ 个人网站／博客

### 职业信息
□ 当前雇主
□ 职位名称
□ 工作经历
□ 教育背景
□ 职业资格许可
□ 发表作品／专利

### 记录
□ 房产记录
□ 法院记录
□ 企业登记
□ 选民登记
□ 婚姻／离婚记录

### 关联人员
□ 家庭成员
□ 相关人员
□ 商业伙伴
□ 社交关系
```

---

## ⚠️ 法律与伦理注意事项

```
✓ 仅使用公开可获取的信息
✓ 遵守隐私法律（GDPR、CCPA）
✓ 不要冒用他人身份或进行欺骗
✓ 记录调查方法
✓ 仅用于合法目的
✗ 不要跟踪或骚扰他人
✗ 不要访问他人的非公开账户
✗ 未经同意，不要公开分享调查结果
```

---

**返回 OSINT:** [🔍 OSINT 概览](./README.zh-CN.md)
