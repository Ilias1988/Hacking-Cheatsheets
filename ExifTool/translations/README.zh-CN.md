# 📷 ExifTool 元数据提取速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███████╗██╗  ██╗██╗███████╗████████╗ ██████╗  ██████╗ ██╗     
  ██╔════╝╚██╗██╔╝██║██╔════╝╚══██╔══╝██╔═══██╗██╔═══██╗██║     
  █████╗   ╚███╔╝ ██║█████╗     ██║   ██║   ██║██║   ██║██║     
  ██╔══╝   ██╔██╗ ██║██╔══╝     ██║   ██║   ██║██║   ██║██║     
  ███████╗██╔╝ ██╗██║██║        ██║   ╚██████╔╝╚██████╔╝███████╗
  ╚══════╝╚═╝  ╚═╝╚═╝╚═╝        ╚═╝    ╚═════╝  ╚═════╝ ╚══════╝
                      元数据提取工具
```

<p align="center">
  <img src="https://img.shields.io/badge/ExifTool-Metadata-blue?style=for-the-badge" alt="ExifTool">
  <img src="https://img.shields.io/badge/OSINT-green?style=for-the-badge" alt="OSINT">
  <img src="https://img.shields.io/badge/Forensics-red?style=for-the-badge" alt="Forensics">
</p>

---

## 📋 目录

- [ExifTool 是什么](#-what-is-exiftool)
- [安装](#-installation)
- [基本用法](#-basic-usage)
- [读取元数据](#-reading-metadata)
- [写入元数据](#-writing-metadata)
- [移除元数据](#-removing-metadata)
- [文件类型](#-file-types)
- [开源情报（OSINT）与数字取证](#-osint--forensics)
- [批量处理](#-batch-processing)
- [速查表](#-quick-reference)

---

<a id="-what-is-exiftool"></a>
## 🎯 ExifTool 是什么

**ExifTool** 是一款功能强大的命令行工具，可用于读取、写入和修改文件中的元数据，常用于以下场景：

- 🔍 **OSINT** - 从图像中提取位置和设备信息
- 🔬 **数字取证** - 分析文档元数据
- 🔐 **隐私保护** - 移除敏感元数据
- 📸 **摄影** - 管理照片元数据
- 📄 **文档** - 分析 PDF 和 Office 文件的元数据

### 为什么元数据很重要

| 使用场景 | 示例 |
|----------|---------|
| **地理定位** | 照片中的 GPS 坐标 |
| **设备信息** | 相机型号与序列号 |
| **时间戳** | 文件的创建／修改时间 |
| **作者** | 文档创建者 |
| **软件** | 创建文件所用的应用程序 |
| **编辑历史** | 修改时间戳 |

---

<a id="-installation"></a>
## 🚀 安装

### Linux (Kali/Ubuntu)

```bash
# Debian/Ubuntu/Kali
sudo apt install exiftool

# 也可以使用
sudo apt install libimage-exiftool-perl
```

### macOS

```bash
# Homebrew
brew install exiftool
```

### Windows

```bash
# 从官方网站下载
https://exiftool.org/

# 解压得到 exiftool(-k).exe
# 重命名为 exiftool.exe
# 加入 PATH，或直接运行
```

> **Windows 发行包说明：** [官方安装文档](https://exiftool.org/install.html)要求移动可执行文件时同时保留同级的 `exiftool_files` 文件夹，不能只复制 exe。下方 `12.xx` 是原文版本输出示例，不代表当前版本。

### 验证安装

```bash
exiftool -ver
# 输出示例：12.xx
```

---

<a id="-basic-usage"></a>
## 💻 基本用法

### 基本语法

```bash
# 读取元数据
exiftool [OPTIONS] FILE

# 写入元数据
exiftool -TAG=VALUE FILE

# 多个文件
exiftool *.jpg
exiftool -r directory/
```

### 常用选项

| 选项 | 说明 |
|--------|-------------|
| `-r` | 递归处理子目录 |
| `-v` | 详细输出 |
| `-n` | 不进行显示转换（原始值） |
| `-s` | 使用简短标签名 |
| `-G` | 显示每个标签所属的组 |
| `-j` | JSON 格式输出 |
| `-csv` | CSV 格式输出 |
| `-overwrite_original` | 不创建备份 |

---

<a id="-reading-metadata"></a>
## 📖 读取元数据

### 查看全部元数据

```bash
# 全部元数据
exiftool image.jpg

# 详细输出
exiftool -v image.jpg

# 更详细的输出
exiftool -v3 image.jpg
```

### 指定标签

```bash
# 单个标签
exiftool -GPSLatitude image.jpg

# 多个标签
exiftool -GPSLatitude -GPSLongitude -Make -Model image.jpg

# 排除标签
exiftool --MakerNotes image.jpg
```

### 按组读取

```bash
# 显示所有 EXIF 标签
exiftool -EXIF:all image.jpg

# 显示所有 IPTC 标签
exiftool -IPTC:all image.jpg

# 显示所有 XMP 标签
exiftool -XMP:all image.jpg

# 显示所有 GPS 标签
exiftool -GPS:all image.jpg

# 显示标签所属的组名
exiftool -G image.jpg
```

### 常见元数据组

| 组 | 说明 |
|-------|-------------|
| EXIF | 相机／图像数据 |
| IPTC | 新闻媒体元数据 |
| XMP | Adobe 可扩展元数据 |
| GPS | 位置数据 |
| MakerNotes | 相机厂商专有数据 |
| File | 文件基本信息 |
| Composite | 计算得到的值 |

### 输出格式

```bash
# JSON 格式输出
exiftool -j image.jpg

# CSV 格式输出
exiftool -csv image.jpg

# HTML 格式输出
exiftool -htmlDump image.jpg > output.html

# 以制表符分隔
exiftool -t image.jpg

# 自定义格式
exiftool -p '$filename: $make $model' image.jpg
```

---

<a id="-writing-metadata"></a>
## ✏️ 写入元数据

### 写入单个标签

```bash
# 设置作者
exiftool -Author="John Doe" image.jpg

# 设置版权信息
exiftool -Copyright="© 2024 Company" image.jpg

# 设置注释
exiftool -Comment="My photo" image.jpg
```

### 写入多个标签

```bash
exiftool -Author="John Doe" -Copyright="© 2024" -Keywords="nature,landscape" image.jpg
```

### 写入 GPS 坐标

```bash
# 设置 GPS 坐标
exiftool -GPSLatitude=37.7749 -GPSLongitude=-122.4194 image.jpg

# 同时指定经纬度参考方向
exiftool -GPSLatitude=37.7749 -GPSLatitudeRef=N -GPSLongitude=122.4194 -GPSLongitudeRef=W image.jpg

# 采用 Google Maps URL 所示的坐标格式
exiftool "-GPSLatitude=37 46 29.64" "-GPSLongitude=122 25 9.84" -GPSLatitudeRef=N -GPSLongitudeRef=W image.jpg
```

> **GPS 说明：** [官方 FAQ](https://exiftool.org/faq.html#Q14)指出，EXIF 坐标的参考方向由 `GPSLatitudeRef`／`GPSLongitudeRef` 单独指定。上方第一条仅写正负坐标的示例不能保证同时修正 EXIF 方向，应核对实际组及参考标签。

### 写入日期时间（DateTime）

```bash
# 设置日期／时间
exiftool -DateTimeOriginal="2024:01:15 10:30:00" image.jpg

# 设置所有日期（原文概括，参见下方说明）
exiftool -AllDates="2024:01:15 10:30:00" image.jpg

# 偏移日期
exiftool "-DateTimeOriginal+=1:0:0 0:0:0" image.jpg  # 增加 1 年
exiftool "-DateTimeOriginal-=0:0:7 0:0:0" image.jpg  # 减去 7 天
```

> **日期范围说明：** [`AllDates` 官方定义](https://exiftool.org/TagNames/Shortcuts.html)只包含 `DateTimeOriginal`、`CreateDate` 和 `ModifyDate`，并非文件中的全部时间字段。

### 在文件之间复制元数据

```bash
# 复制所有元数据（受可写标签与格式限制）
exiftool -TagsFromFile source.jpg destination.jpg

# 复制指定标签
exiftool -TagsFromFile source.jpg -GPS:all -EXIF:all destination.jpg

# 复制到多个文件
exiftool -TagsFromFile source.jpg -GPS:all *.jpg
```

---

<a id="-removing-metadata"></a>
## 🗑️ 移除元数据

> **支持范围说明：** “可读取”不等于“可写入或删除”。[官方格式表](https://exiftool.org/#supported)将 DOCX、XLSX、PPTX 列为只读，所以下方 Office 删除示例不能据此生效。[官方 FAQ](https://exiftool.org/faq.html#Q32)也说明，RAW 等格式可能依赖部分元数据才能正常显示，JPEG 的颜色信息也可能需要保留；`-all=` 不保证对所有格式安全或彻底清除身份线索。

### 移除全部元数据

```bash
# 移除全部元数据（原文称文件仍可用，需注意格式限制）
exiftool -all= image.jpg

# 移除元数据且不保留备份
exiftool -overwrite_original -all= image.jpg

# 处理多个文件
exiftool -all= *.jpg
exiftool -r -all= directory/
```

### 移除指定元数据

```bash
# 仅移除 GPS 数据
exiftool -GPS:all= image.jpg
exiftool -geotag= image.jpg

# 仅移除 EXIF 数据
exiftool -EXIF:all= image.jpg

# 移除注释
exiftool -Comment= image.jpg

# 移除作者信息
exiftool -Author= -Creator= -Artist= image.jpg
```

### 从指定文件类型中移除元数据

```bash
# 图像
exiftool -all= *.jpg *.png *.gif

# PDF 文件
exiftool -all= document.pdf

# Office 文件（原文示例，参见下方支持范围说明）
exiftool -all= *.docx *.xlsx *.pptx

# 视频
exiftool -all= video.mp4
```

> **PDF 说明：** [官方 PDF 文档](https://exiftool.org/TagNames/PDF.html)说明，ExifTool 使用可逆的增量更新，旧信息不会真正从 PDF 中删除。不能仅凭 `-all=` 后看不到标签，就认定敏感信息已被安全擦除。

### 为保护隐私而清理元数据

```bash
# 移除所有可识别身份的信息（原文概括）
exiftool -all= -overwrite_original \
  -gps:all= \
  -Author= \
  -Creator= \
  -XMP:all= \
  image.jpg
```

---

<a id="-file-types"></a>
## 📁 文件类型

### 支持的文件类型（400+）

| 类别 | 格式 |
|----------|---------|
| **图像** | JPEG, PNG, GIF, TIFF, BMP, WebP, HEIC, RAW |
| **RAW 照片** | CR2, NEF, ARW, DNG, ORF, RW2 |
| **视频** | MP4, MOV, AVI, MKV, WebM |
| **音频** | MP3, WAV, FLAC, M4A, OGG |
| **文档** | PDF, DOCX, XLSX, PPTX |
| **归档文件** | ZIP（有限支持） |

### 按文件类型读取

```bash
# PDF 元数据
exiftool document.pdf
exiftool -Title -Author -Creator -Producer document.pdf

# 视频元数据
exiftool -Duration -VideoFrameRate -ImageWidth -ImageHeight video.mp4

# 音频元数据
exiftool -Artist -Album -Title -Duration audio.mp3

# Office 文档
exiftool -Author -LastModifiedBy -CreateDate -ModifyDate file.docx
```

---

<a id="-osint--forensics"></a>
## 🔍 开源情报（OSINT）与数字取证

### 提取 GPS 位置

```bash
# 显示 GPS 坐标
exiftool -GPS:all image.jpg

# 便于阅读的 GPS 位置
exiftool -GPSPosition image.jpg

# 十进制度数
exiftool -n -GPSLatitude -GPSLongitude image.jpg

# Google Maps URL
exiftool -p 'https://www.google.com/maps?q=$GPSLatitude,$GPSLongitude' image.jpg
```

> **坐标输出说明：** [官方 FAQ](https://exiftool.org/faq.html#Q14)说明默认坐标是度分秒形式，`-n` 才输出十进制度数。上方拼接 Google Maps URL 的示例保留原文，实际格式及南／西方向还须核对，不能假定插值后就是有效坐标 URL。

### 设备识别

```bash
# 相机信息
exiftool -Make -Model -SerialNumber image.jpg

# 所用软件
exiftool -Software -CreatorTool image.jpg

# 镜头信息（如果存在）
exiftool -Lens -LensModel -FocalLength image.jpg

# 详细相机信息
exiftool -MakerNotes:all image.jpg
```

### 时间线分析

```bash
# 全部日期／时间
exiftool -time:all image.jpg

# 文件系统时间
exiftool -FileModifyDate -FileAccessDate -FileCreateDate image.jpg

# EXIF 时间
exiftool -DateTimeOriginal -CreateDate -ModifyDate image.jpg

# 查找日期差异
exiftool -a -G1 -s -time:all image.jpg
```

### 文档取证

```bash
# PDF 分析
exiftool -a -G1 document.pdf

# Office 文件历史信息
exiftool -a -G1 -RevisionNumber -TotalEditTime -LastPrinted file.docx

# 查找作者信息
exiftool -Author -Creator -LastModifiedBy -Company document.pdf
```

### 图像真实性分析

```bash
# 检查编辑软件信息
exiftool -Software -HistorySoftwareAgent image.jpg

# 对比缩略图与原图
exiftool -b -ThumbnailImage image.jpg > thumb.jpg

# 检查修改痕迹
exiftool -ModifyDate -CreateDate -DateTimeOriginal image.jpg
```

### 实用 OSINT 查询

```bash
# 提取全部可识别身份的信息
exiftool -Make -Model -SerialNumber -Artist -Copyright -GPSPosition image.jpg

# 检查隐写工具痕迹
exiftool -Software image.jpg | grep -i "steg\|hide"

# 提取内嵌缩略图
exiftool -b -ThumbnailImage image.jpg > thumbnail.jpg

# 查找社交媒体处理痕迹
exiftool -a -G1 image.jpg | grep -i "facebook\|instagram\|twitter"
```

---

<a id="-batch-processing"></a>
## ⚙️ 批量处理

### 处理多个文件

```bash
# 当前目录中的所有 JPG 文件
exiftool *.jpg

# 递归处理
exiftool -r directory/

# 指定扩展名
exiftool -ext jpg -ext png directory/
```

### 批量移除元数据

```bash
# 从所有图像中移除元数据
exiftool -all= -overwrite_original -r directory/

# 仅处理指定类型
exiftool -ext jpg -ext png -all= -overwrite_original -r directory/
```

### 批量重命名文件

```bash
# 按日期重命名
exiftool '-filename<CreateDate' -d '%Y%m%d_%H%M%S%%-c.%%e' *.jpg

# 按相机型号重命名
exiftool '-filename<${Make}_${Model}_${filename}' *.jpg

# 使用计数器重命名（原文示例，参见下方语法说明）
exiftool '-filename<photo_%04c.%%e' *.jpg
```

> **重命名语法说明：** 按[官方文件名文档](https://exiftool.org/filename.html)，`<` 用于从标签复制值，固定名称赋值使用 `=`；扩展名格式码在日期格式字符串中才需要额外的 `%`。上方计数器示例混用了这些形式，不能视为可靠的通用命令，原文仍按原样保留。

### 批量复制元数据

```bash
# 将一个文件的 GPS 数据复制到所有目标文件
exiftool -TagsFromFile source.jpg -GPS:all -overwrite_original *.jpg
```

### 导出为 CSV

```bash
# 将元数据导出为 CSV
exiftool -csv *.jpg > metadata.csv

# 指定字段
exiftool -csv -filename -GPSLatitude -GPSLongitude -Make -Model *.jpg > gps_data.csv
```

### 条件处理

```bash
# 仅处理含有 GPS 信息的文件
exiftool -if '$GPSLatitude' -GPSPosition *.jpg

# 仅处理指定相机拍摄的文件
exiftool -if '$Make eq "Canon"' -filename *.jpg

# 在指定日期之后修改的文件
exiftool -if '$ModifyDate gt "2024:01:01"' *.jpg
```

---

<a id="-quick-reference"></a>
## 📊 速查表

### 常用命令

| 用途 | 命令 |
|---------|---------|
| 查看全部元数据 | `exiftool image.jpg` |
| 查看 GPS 信息 | `exiftool -GPS:all image.jpg` |
| 移除全部元数据 | `exiftool -all= image.jpg` |
| 移除 GPS 信息 | `exiftool -GPS:all= image.jpg` |
| JSON 格式输出 | `exiftool -j image.jpg` |
| 递归处理 | `exiftool -r directory/` |
| 相机信息 | `exiftool -Make -Model image.jpg` |
| 时间戳 | `exiftool -time:all image.jpg` |

### 常见标签

| 标签 | 说明 |
|-----|-------------|
| `-Make` | 相机制造商 |
| `-Model` | 相机型号 |
| `-DateTimeOriginal` | 照片拍摄时间 |
| `-GPSLatitude` | GPS 纬度 |
| `-GPSLongitude` | GPS 经度 |
| `-Author` | 文件作者 |
| `-Copyright` | 版权信息 |
| `-Software` | 编辑软件 |
| `-ImageWidth` | 图像宽度 |
| `-ImageHeight` | 图像高度 |

### 隐私检查清单

```bash
# 1. 检查已有的元数据
exiftool -a -G1 image.jpg

# 2. 检查 GPS 信息
exiftool -GPS:all image.jpg

# 3. 检查可识别身份的信息
exiftool -Make -Model -SerialNumber -Author -Software image.jpg

# 4. 移除全部元数据
exiftool -all= -overwrite_original image.jpg

# 5. 验证移除结果
exiftool image.jpg
```

### 取证检查清单

```bash
# 1. 保留原始文件（先制作副本！）
cp image.jpg image_original.jpg

# 2. 导出完整元数据
exiftool -a -G1 -s image.jpg > metadata_report.txt

# 3. 提取 GPS 信息
exiftool -GPS:all -c "%.6f" image.jpg

# 4. 时间线
exiftool -time:all -G1 image.jpg

# 5. 设备信息
exiftool -Make -Model -SerialNumber -LensModel image.jpg

# 6. 检查篡改痕迹
exiftool -Software -HistorySoftwareAgent image.jpg
```

---

## 📚 参考资源

- [ExifTool 官方网站](https://exiftool.org/)
- [ExifTool 文档](https://exiftool.org/TagNames/)
- [ExifTool 论坛](https://exiftool.org/forum/)
- [支持的文件类型](https://exiftool.org/#supported)

### 相关速查表
- [Autopsy](../../Autopsy/README.md)
- [Volatility](../../Volatility/translations/README.zh-CN.md)
- [Linux 命令](../../Linux-Commands/translations/README.zh-CN.md)

---

<p align="center">
  <b>📷 掌握元数据分析！</b><br>
  <i>ExifTool - 每个文件都藏着故事</i>
</p>
