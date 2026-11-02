<a id="-autopsy---digital-forensics-platform-cheatsheet"></a>
# 🔍 Autopsy 数字取证平台速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](./README.tr.md) • [🇬🇷 Ελληνικά](./README.el.md)

```
   █████╗ ██╗   ██╗████████╗ ██████╗ ██████╗ ███████╗██╗   ██╗
  ██╔══██╗██║   ██║╚══██╔══╝██╔═══██╗██╔══██╗██╔════╝╚██╗ ██╔╝
  ███████║██║   ██║   ██║   ██║   ██║██████╔╝███████╗ ╚████╔╝ 
  ██╔══██║██║   ██║   ██║   ██║   ██║██╔═══╝ ╚════██║  ╚██╔╝  
  ██║  ██║╚██████╔╝   ██║   ╚██████╔╝██║     ███████║   ██║   
  ╚═╝  ╚═╝ ╚═════╝    ╚═╝    ╚═════╝ ╚═╝     ╚══════╝   ╚═╝   
              数字取证平台              
```

<p align="center">
  <img src="https://img.shields.io/badge/Autopsy-Digital_Forensics-blue?style=for-the-badge" alt="Autopsy">
  <img src="https://img.shields.io/badge/Sleuth_Kit-green?style=for-the-badge" alt="TSK">
  <img src="https://img.shields.io/badge/DFIR-red?style=for-the-badge" alt="DFIR">
</p>

---

<a id="-table-of-contents"></a>
## 📋 目录

- [什么是 Autopsy](#-what-is-autopsy)
- [安装](#-installation)
- [创建案件](#-creating-a-case)
- [添加数据源](#-adding-data-sources)
- [分析处理模块（Ingest Modules）](#-ingest-modules)
- [分析功能](#-analysis-features)
- [文件分析](#-file-analysis)
- [时间线分析](#-timeline-analysis)
- [关键词搜索（Keyword Search）](#-keyword-search)
- [报告生成](#-reporting)
- [Sleuth Kit 命令行工具](#-cli-tools-sleuth-kit)

---

<a id="-what-is-autopsy"></a>
## 🎯 什么是 Autopsy

**Autopsy** 是基于 The Sleuth Kit（TSK）的免费开源数字取证平台，提供以下功能：

- 🖥️ **图形界面** - 易于使用的取证分析界面
- 💾 **磁盘分析** - 检查硬盘、USB 存储和镜像
- 📁 **文件恢复** - 恢复已删除文件
- 📊 **时间线分析** - 重建用户活动
- 🔍 **关键词搜索（Keyword Search）** - 搜索所有数据
- 📧 **邮件分析** - 解析邮件数据库
- 🌐 **Web 痕迹** - 浏览器历史和书签
- 📱 **移动设备取证** - Android/iOS 分析

<a id="supported-evidence-types"></a>
### 支持的证据类型

| 类型 | 格式 |
|------|---------|
| **磁盘镜像** | E01, AFF, Raw (dd), VHD, VMDK |
| **本地磁盘（Local Disk）** | 物理／逻辑驱动器 |
| **虚拟机文件** | VMDK, VHD, VHDX |
| **逻辑文件（Logical Files）** | 文件夹和文件 |
| **未分配空间（Unallocated Space）** | 文件雕刻数据 |

---

<a id="-installation"></a>
## 🚀 安装

<a id="windows"></a>
### Windows

```bash
# 从官方网站下载
https://www.autopsy.com/download/

# 运行安装程序
autopsy-x.x.x-64bit.msi

# 默认安装位置
C:\Program Files\Autopsy-x.x.x\
```

<a id="linux-kali"></a>
### Linux (Kali)

```bash
# 通过 apt 安装（版本差异见译注）
sudo apt update
sudo apt install autopsy

# 运行
autopsy
```

<a id="requirements"></a>
### 要求

| 组件 | 最低要求 | 推荐配置 |
|-----------|---------|-------------|
| **RAM** | 8 GB | 16+ GB |
| **CPU** | 多核 | 4+ 核心 |
| **存储** | SSD | NVMe SSD |
| **OS** | Windows 10/11, Linux | Windows 10/11 |

---

<a id="-creating-a-case"></a>
## 📁 创建案件

<a id="new-case-wizard"></a>
### 新建案件向导

1. **File → New Case（文件→新建案件）**
2. 输入 **Case Name（案件名称）** （例如 "Investigation_2024"）
3. 输入 **Base Directory（基础目录）** （案件文件的保存位置）
4. 选择 **Case Type（案件类型）**:
   - Single-user（单用户，本地）
   - Multi-user（多用户，协作）
5. 输入 **Case Number（案件编号）** （可选）
6. 输入 **Examiner（检验人员）** 信息
7. 点击 **Finish（完成）**

<a id="case-structure"></a>
### 案件目录结构

```
CaseName/
├── autopsy.db           # 案件数据库（SQLite）
├── Reports/             # 生成的报告
├── Export/              # 导出的文件
├── ModuleOutput/        # 模块结果
└── [HostName]/          # 每台主机的数据
    ├── [DataSource]/    # 证据文件（实际保存方式见译注）
    └── Reports/
```

<a id="case-properties"></a>
### 案件属性

| 字段 | 说明 |
|-------|-------------|
| Case Name（案件名称） | 调查标识 |
| Case Number（案件编号） | 参考编号 |
| Examiner（检验人员） | 分析人员姓名 |
| Base Directory（基础目录） | 案件存储位置 |
| Case Type（案件类型） | 单用户／多用户 |

---

<a id="-adding-data-sources"></a>
## 📀 添加数据源

<a id="data-source-types"></a>
### 数据源类型

| 类型 | 说明 |
|------|-------------|
| **Disk Image or VM File（磁盘镜像或虚拟机文件）** | E01, Raw, VMDK, VHD |
| **本地磁盘（Local Disk）** | 物理驱动器分析 |
| **逻辑文件（Logical Files）** | 导入文件夹／文件 |
| **未分配空间（Unallocated Space）** | 空闲空间的原始镜像 |

<a id="adding-disk-image"></a>
### 添加磁盘镜像

1. **Add Data Source（添加数据源）** （工具栏）
2. 选择 **Disk Image or VM File（磁盘镜像或虚拟机文件）**
3. 浏览并选择镜像文件
4. 选择 **Time Zone（时区）**
5. 配置 **分析处理模块（Ingest Modules）** （见下一节）
6. 点击 **Finish（完成）**

<a id="adding-local-disk"></a>
### 添加本地磁盘

```
⚠️ 警告：使用写保护设备或只读模式！
```

1. **Add Data Source（添加数据源）**
2. 选择 **本地磁盘（Local Disk）**
3. 从列表选择磁盘
4. 配置分析处理模块
5. **Finish（完成）**

<a id="supported-image-formats"></a>
### 支持的镜像格式

| 格式 | 扩展名 | 说明 |
|--------|-----------|-------------|
| Raw | .raw, .dd, .img | 逐位复制 |
| EnCase | .E01, .Ex01 | Expert Witness 格式 |
| AFF | .aff | 高级取证格式（Advanced Forensic Format） |
| VMDK | .vmdk | VMware 磁盘 |
| VHD/VHDX | .vhd, .vhdx | Hyper-V 磁盘 |

---

<a id="-ingest-modules"></a>
## ⚙️ 分析处理模块（Ingest Modules）

<a id="what-are-ingest-modules"></a>
### 什么是分析处理模块（Ingest Modules）？

对数据源执行自动化分析并提取取证痕迹的模块。

<a id="core-modules"></a>
### 核心模块

| 模块 | 说明 |
|--------|-------------|
| **近期活动（Recent Activity）** | 浏览器历史、下载记录、Cookie |
| **哈希查询（Hash Lookup）** | 在已知数据库中查询 MD5/SHA1 |
| **文件类型识别（File Type Identification）** | 根据签名识别文件类型 |
| **关键词搜索（Keyword Search）** | 索引并搜索内容 |
| **邮件解析器（Email Parser）** | 解析 PST、MBOX、EML |
| **扩展名不匹配（Extension Mismatch）** | 检测更名后扩展名不符的文件 |
| **嵌入文件提取器（Embedded File Extractor）** | 从压缩包／文档提取内容 |
| **EXIF 解析器（EXIF Parser）** | 提取图像元数据 |
| **加密检测（Encryption Detection）** | 查找加密文件 |
| **值得关注的文件（Interesting Files）** | 查找值得关注的文件 |
| **PhotoRec Carver（文件雕刻器）** | 文件雕刻（carving） |
| **虚拟机提取器（Virtual Machine Extractor）** | 提取虚拟机文件 |

<a id="hash-databases"></a>
### 哈希数据库

```
# 添加已知哈希数据库
Tools → Options → Hash Database（工具→选项→哈希数据库）

# 类型：
- Known (NSRL)：忽略已知良性文件（原文概括）
- Known Bad：恶意软件哈希命中时告警
```

<a id="nsrl-national-software-reference-library"></a>
### NSRL（国家软件参考库，National Software Reference Library）

```bash
# 下载 NSRL
https://www.nist.gov/itl/ssd/software-quality-group/national-software-reference-library-nsrl

# 在 Autopsy 中导入
Tools → Options → Hash Database → Import Database（工具→选项→哈希数据库→导入数据库）
```

<a id="module-configuration"></a>
### 模块配置

```
# 按案件启用／禁用模块
右键点击数据源 → Run Ingest Modules（运行分析处理模块）

# 配置模块设置
点击模块名称旁的齿轮图标
```

---

<a id="-analysis-features"></a>
## 🔬 分析功能

<a id="tree-view-left-panel"></a>
### 树形视图（左侧面板）

```
Data Sources（数据源）
├── [Image Name]
│   ├── Volume 1
│   │   ├── [Folders]
│   │   ├── $OrphanFiles
│   │   └── $Unalloc
│   └── Volume 2
│
Views（视图）
├── File Types（文件类型）
│   ├── By Extension（按扩展名）
│   └── By MIME Type（按 MIME 类型）
├── Deleted Files（已删除文件）
├── File Size（文件大小）
└── ...

Results（结果）
├── Extracted Content（提取的内容）
│   ├── Web History（浏览历史）
│   ├── Web Downloads（Web 下载记录）
│   ├── Web Cookies（Web Cookie）
│   ├── Recent Documents（最近文档）
│   └── ...
├── Keyword Hits（关键词命中）
├── HashSet Hits（哈希集命中）
├── E-mail Messages（邮件消息）
├── Interesting Items（值得关注的条目）
├── Accounts（账户）
└── ...

Tags（标签）
├── Follow Up（待跟进）
├── Notable Item（重要条目）
└── [Custom Tags]
```

<a id="quick-analysis-areas"></a>
### 快速分析区域

| 类别 | 检查内容 |
|----------|-----------------|
| **已删除文件（Deleted Files）** | 已恢复的文件、用户删除的文件 |
| **浏览历史（Web History）** | 访问的网站、搜索记录 |
| **下载记录（Downloads）** | 已下载文件 |
| **最近文档（Recent Documents）** | 最近打开的文件 |
| **USB 设备** | 连接过的设备 |
| **电子邮件** | 通信内容 |
| **图像** | 照片、截图 |
| **归档文件** | ZIP、RAR 内容 |

---

<a id="-file-analysis"></a>
## 📄 文件分析

<a id="viewing-files"></a>
### 查看文件

```
# Content Viewer（内容查看器，底部面板）
- Hex        : 原始十六进制视图
- Text       : 文本内容
- Application: 渲染视图
- Strings    : 提取的字符串
- Metadata   : 文件元数据
- Results    : 分析结果
- Indexed Text: 可搜索的文本
```

<a id="file-properties"></a>
### 文件属性

| 属性 | 说明 |
|----------|-------------|
| 名称 | 文件名 |
| 位置 | 完整路径 |
| 大小 | 文件大小 |
| 创建时间 | 创建时间 |
| 修改时间 | 最后修改时间 |
| 访问时间 | 最后访问时间 |
| MD5/SHA1/SHA256 | 哈希值 |
| 已知状态（Known） | NSRL 状态 |
| MIME 类型 | 识别出的类型 |

<a id="extracting-files"></a>
### 提取文件

```
# 提取单个文件
右键点击 → Extract File(s)（提取文件）

# 提取多个文件
选择文件 → 右键点击 → Extract（提取）

# 提取到指定位置
Actions → Extract to...（操作→提取到……）
```

<a id="deleted-file-recovery"></a>
### 已删除文件恢复

```
Views → Deleted Files（视图→已删除文件）

# 显示：
- $Recycle.Bin 中的文件
- MFT 中标记为已删除的文件
- 通过文件雕刻得到的文件（PhotoRec）
```

---

<a id="-timeline-analysis"></a>
## 📊 时间线分析

<a id="timeline-view"></a>
### 时间线视图

```
# 打开时间线
Tools → Timeline（工具→时间线）

# 或者
Results → Timeline（结果→时间线）
```

<a id="timeline-features"></a>
### 时间线功能

| 功能 | 说明 |
|---------|-------------|
| **计数视图（Counts View）** | 事件频率随时间的变化 |
| **详细视图（Details View）** | 单个事件 |
| **列表视图（List View）** | 表格形式的事件列表 |
| **过滤器（Filters）** | 按类型、日期等过滤 |

<a id="event-types"></a>
### 事件类型

| 类型 | 颜色 | 说明 |
|------|-------|-------------|
| 文件内容修改（File Modified） | 蓝色 | 文件内容发生变化 |
| 文件访问（File Accessed） | 绿色 | 文件被打开 |
| 文件创建（File Created） | 黄色 | 文件被创建 |
| 文件状态变化（File Changed） | 红色 | 元数据发生变化 |
| Web 活动（Web Activity） | 紫色 | 浏览器事件 |

<a id="timeline-filters"></a>
### 时间线过滤器

```
# 按日期范围过滤
Start Date（开始日期）: 2024-01-01
End Date（结束日期）: 2024-12-31

# 按类型过滤
☑ File System（文件系统）
☑ Web Activity（Web 活动）
☐ Registry（注册表，如已解析）

# 按数据源过滤
☑ Disk Image 1
☐ Disk Image 2
```

<a id="timeline-export"></a>
### 导出时间线

```
# 导出时间线
File → Export → Timeline (CSV/TSV)（文件→导出→时间线）

# 配合外部工具使用
- Excel
- plaso/log2timeline
- timesketch
```

---

<a id="-keyword-search"></a>
## 🔍 关键词搜索（Keyword Search）

<a id="creating-keyword-lists"></a>
### 创建关键词列表

```
# Tools → Options → Keyword Search（工具→选项→关键词搜索）

# 添加新列表
1. 点击 "New List"（新建列表）
2. 为列表命名
3. 添加关键词：
   - Literal（精确字面匹配）
   - Regex（正则表达式模式）
   - Substring（子串）
```

<a id="common-search-keywords"></a>
### 常用搜索关键词

```
# 财务信息
credit card
bank account
swift
iban
bitcoin
wallet

# 凭据
password
login
username
secret
key

# 敏感信息
confidential
secret
classified
internal only

# 网络信息
ip address: \b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b
email: [a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}
```

<a id="regex-examples"></a>
### 正则表达式示例

| 用途 | 正则表达式 |
|---------|-------|
| 电子邮件 | `[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}` |
| IP 地址 | `\b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b` |
| 电话号码（美国） | `\b\d{3}[-.]?\d{3}[-.]?\d{4}\b` |
| 信用卡 | `\b\d{4}[- ]?\d{4}[- ]?\d{4}[- ]?\d{4}\b` |
| 美国社会保障号码（SSN） | `\b\d{3}-\d{2}-\d{4}\b` |
| URL | `https?://[^\s]+` |

<a id="running-search"></a>
### 执行搜索

```
# 临时搜索
Keyword Search（右上角关键词搜索）→ 输入词项 → Search（搜索）

# 使用关键词列表
右键点击数据源 → Run Ingest Modules → Keyword Search（运行分析处理模块→关键词搜索）
```

---

<a id="-reporting"></a>
## 📝 报告生成

<a id="generate-report"></a>
### 生成报告（Generate Report）

```
# 生成报告
Tools → Generate Report（工具→生成报告）

# 或者
右键点击案件 → Generate Report（生成报告）
```

<a id="report-types"></a>
### 报告类型

| 类型 | 说明 |
|------|-------------|
| **HTML 报告** | 网页格式，包含图像 |
| **Excel 报告** | 电子表格格式 |
| **文本报告** | 纯文本 |
| **TSK Body 文件** | 时间线 body 文件 |
| **Files - FS** | 文件系统报告 |
| **便携案件（Portable Case）** | 可共享的案件子集 |

<a id="html-report-options"></a>
### HTML 报告选项

```
# 选择要包含的数据
☑ Tagged Results（已标记的结果）
☑ Interesting Items（值得关注的条目）
☑ Hashset Hits（哈希集命中）
☑ Keyword Hits（关键词命中）
☑ Recent Activity (Web)（近期 Web 活动）
☑ Accounts（账户）
☑ File Types（文件类型）

# 选项
☑ Include thumbnails（包含缩略图）
☑ Include metadata（包含元数据）
```

<a id="portable-case"></a>
### 便携案件（Portable Case）

```
# 创建便携案件以便共享
Generate Report → Portable Case（生成报告→便携案件）

# 包含：
- 选定的证据
- 分析结果
- 可在 Autopsy 中打开
```

---

<a id="-cli-tools-sleuth-kit"></a>
## 💻 Sleuth Kit 命令行工具

<a id="sleuth-kit-commands"></a>
### Sleuth Kit 命令

Autopsy 使用 The Sleuth Kit（TSK）作为后端。可使用以下命令行工具：

<a id="image-information"></a>
### 镜像信息

```bash
# 获取镜像信息
img_stat image.E01
img_stat image.dd

# 获取文件系统信息
fsstat -o 2048 image.dd
```

<a id="file-listing"></a>
### 文件列表

```bash
# 列出文件
fls -r -o 2048 image.dd

# 列出已删除文件
fls -r -d -o 2048 image.dd

# 选项：
# -r    递归
# -d    仅已删除文件
# -o    偏移量（分区起点，单位见译注）
```

<a id="file-recovery"></a>
### 文件恢复

```bash
# 按 inode 获取文件
icat -o 2048 image.dd 12345 > recovered_file

# 获取文件内容
icat -r -o 2048 image.dd 12345 > file.txt
```

<a id="timeline"></a>
### 时间线

```bash
# 创建 body 文件
fls -r -m "/" -o 2048 image.dd > body.txt

# 创建时间线
mactime -b body.txt -d > timeline.csv
```

<a id="hash-calculation"></a>
### 哈希计算

```bash
# 计算 MD5
md5sum image.dd

# 计算 SHA256
sha256sum image.dd

# 校验镜像
sha256sum -c image.dd.sha256
```

<a id="common-tsk-tools"></a>
### 常用 TSK 工具

| 工具 | 用途 |
|------|---------|
| `img_stat` | 镜像信息 |
| `mmls` | 分区表 |
| `fsstat` | 文件系统信息 |
| `fls` | 文件列表 |
| `icat` | 按 inode 提取文件 |
| `istat` | inode 信息 |
| `blkls` | 提取未分配空间 |
| `srch_strings` | 查找字符串 |
| `tsk_recover` | 恢复所有文件（默认行为见译注） |
| `mactime` | 创建时间线 |

---

<a id="-quick-reference"></a>
## 📊 速查

<a id="investigation-workflow"></a>
### 调查流程

```
1. Create Case（创建案件）                 → 填写信息并创建案件
2. Add Data Source（添加数据源）           → 磁盘镜像或驱动器
3. Run Ingest Modules（运行分析处理模块）  → 自动化分析
4. Review Results（查看结果）              → 检查提取的内容
5. Keyword Search（关键词搜索）            → 搜索词项
6. Timeline Analysis（时间线分析）         → 重建事件
7. Tag Evidence（标记证据）                → 标记重要条目
8. Generate Report（生成报告）             → 创建最终报告
```

<a id="keyboard-shortcuts"></a>
### 键盘快捷键

| 快捷键 | 操作 |
|----------|--------|
| Ctrl+N | 新建案件 |
| Ctrl+O | 打开案件 |
| Ctrl+F | 查找（表格内） |
| Ctrl+G | 生成报告（Generate Report） |
| F5 | 刷新 |

<a id="common-locations-to-check"></a>
### 常见检查位置

| Windows | 路径 |
|---------|------|
| 最近文件 | `Users\*\AppData\Roaming\Microsoft\Windows\Recent` |
| 下载记录（Downloads） | `Users\*\Downloads` |
| 浏览器历史 | `Users\*\AppData\Local\[Browser]\User Data` |
| 临时文件 | `Users\*\AppData\Local\Temp` |
| 回收站 | `$Recycle.Bin` |
| 预取文件 | `Windows\Prefetch` |
| 事件日志 | `Windows\System32\winevt\Logs` |

---

<a id="-resources"></a>
## 📚 参考资料

- [Autopsy 官方网站](https://www.autopsy.com/)
- [Sleuth Kit](https://www.sleuthkit.org/)
- [Autopsy 文档](https://sleuthkit.org/autopsy/docs/user-docs/latest/)
- [DFIR 培训](https://www.dfir.training/)

<a id="related-cheatsheets"></a>
### 相关速查表
- [Volatility](../../Volatility/translations/README.zh-CN.md)
- [Linux 命令](../../Linux-Commands/translations/README.zh-CN.md)
- [Wireshark](../../Wireshark/translations/README.zh-CN.md)

---

<p align="center">
  <b>🔍 掌握数字取证！</b><br>
  <i>Autopsy：数字调查人员的得力助手</i>
</p>

> 译注：本页混用了不同版本的界面示例。[Kali 官方软件包页](https://www.kali.org/tools/autopsy/)列出的 apt 包是 Autopsy Forensic Browser 2.24，与这里多数现代桌面 GUI 操作不同；菜单、快捷键、格式支持和处理模块应按实际版本核对。[Autopsy 数据源文档](https://sleuthkit.org/autopsy/docs/user-docs/4.22.1/ds_page.html)说明案件引用数据源，而不会自动把整个数据源复制到案件目录，因此目录树仅为概念示意。

> 译注：[TSK fls 手册](https://www.sleuthkit.org/sleuthkit/man/fls.html)说明 -o 使用扇区偏移，2048 只是示例，不适用于所有镜像。[tsk_recover 手册](https://www.sleuthkit.org/sleuthkit/man/tsk_recover.html)说明默认仅恢复未分配文件，导出已分配和未分配文件需 -e；表中“所有文件”是概括，恢复也不保证完整。正则示例只适合筛选候选内容，不会严格验证 IP 范围、邮箱合法性或信用卡有效性。原始命令、关键词、日期和正则均保留原样。
