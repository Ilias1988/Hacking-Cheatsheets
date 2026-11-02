# 🔧 Binwalk — 固件分析速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██████╗ ██╗███╗   ██╗██╗    ██╗ █████╗ ██╗     ██╗  ██╗
  ██╔══██╗██║████╗  ██║██║    ██║██╔══██╗██║     ██║ ██╔╝
  ██████╔╝██║██╔██╗ ██║██║ █╗ ██║███████║██║     █████╔╝ 
  ██╔══██╗██║██║╚██╗██║██║███╗██║██╔══██║██║     ██╔═██╗ 
  ██████╔╝██║██║ ╚████║╚███╔███╔╝██║  ██║███████╗██║  ██╗
  ╚═════╝ ╚═╝╚═╝  ╚═══╝ ╚══╝╚══╝ ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝
                   固件分析与提取工具
```

<p align="center">
  <img src="https://img.shields.io/badge/Binwalk-Firmware_Analysis-blue?style=for-the-badge" alt="Binwalk">
  <img src="https://img.shields.io/badge/IoT-Hacking-green?style=for-the-badge" alt="IoT">
  <img src="https://img.shields.io/badge/CTF-red?style=for-the-badge" alt="CTF">
</p>

---

## 📋 目录

- [什么是 Binwalk](#-what-is-binwalk)
- [安装](#-installation)
- [基本用法](#-basic-usage)
- [签名扫描](#-signature-scanning)
- [提取](#-extraction)
- [熵分析](#-entropy-analysis)
- [文件系统分析](#-file-system-analysis)
- [CTF 挑战](#-ctf-challenges)
- [高级选项](#-advanced-options)
- [快速参考](#-quick-reference)

---

<a id="-what-is-binwalk"></a>

## 🎯 什么是 Binwalk

**Binwalk** 是一款快速、易用的固件镜像分析、逆向工程和数据提取工具，主要用于：

- 🔧 **固件分析** — 识别嵌入的文件／代码
- 🔓 **物联网安全测试** — 提取路由器／摄像头固件
- 🎯 **CTF 挑战** — 查找隐藏数据
- 🔬 **取证** — 分析二进制文件
- 🔍 **逆向工程** — 理解文件结构

### Binwalk 可以检测什么

| 类别 | 示例 |
|----------| ---------- |
| **压缩数据** | gzip, bzip2, lzma, xz, zlib |
| **归档文件** | tar, zip, rar, 7z, cpio |
| **文件系统** | squashfs, cramfs, jffs2, ext, ubifs |
| **可执行文件** | ELF, PE, ARM, MIPS |
| **图像** | JPEG, PNG, GIF, BMP |
| **密码学相关数据** | OpenSSL、证书 |
| **引导加载程序** | U-Boot、ARM 头部 |

---

<a id="-installation"></a>

## 🚀 安装

> **译注：**本文大部分选项和安装命令对应旧版 Python Binwalk 2；当前 [Binwalk 3](https://github.com/ReFirmLabs/binwalk)已用 Rust 重写，不能把 `setup.py` 安装步骤或全部旧选项直接套用到当前源码。原始命令保留供版本对照。

### Linux（Kali/Ubuntu）

```bash
# 安装 binwalk
sudo apt update
sudo apt install binwalk

# 安装所有提取工具（推荐）
sudo apt install mtd-utils gzip bzip2 tar arj lhasa p7zip p7zip-full cabextract \
  cramfsswap squashfs-tools sleuthkit default-jdk lzop srecord

# 或采用完整安装方式
cd /opt
git clone https://github.com/ReFirmLabs/binwalk.git
cd binwalk
sudo python3 setup.py install

# 安装用于 squashfs 的 sasquatch
sudo apt install zlib1g-dev liblzma-dev liblzo2-dev
git clone https://github.com/devttys0/sasquatch
cd sasquatch && ./build.sh
```

### macOS

```bash
brew install binwalk
```

### 验证安装

```bash
binwalk --help
binwalk -h
```

---

<a id="-basic-usage"></a>

## 💻 基本用法

### 基本语法

```bash
# 扫描文件（显示签名）
binwalk firmware.bin

# 提取文件
binwalk -e firmware.bin

# 递归提取
binwalk -eM firmware.bin
```

### 常用选项

| 选项 | 说明 |
|--------| ------------- |
| `-e, --extract` | 提取已知文件类型 |
| `-M, --matryoshka` | 递归扫描提取出的文件 |
| `-r, --rm` | 提取后删除雕刻出的文件 |
| `-q, --quiet` | 静默模式 |
| `-v, --verbose` | 详细输出 |
| `-d, --depth` | 递归深度（默认：8） |
| `-C, --directory` | 输出目录 |
| `-f, --log` | 将日志写入文件 |

---

<a id="-signature-scanning"></a>

## 🔍 签名扫描

### 基础扫描

```bash
# 扫描签名
binwalk firmware.bin

# 输出示例：
# DECIMAL       HEXADECIMAL     DESCRIPTION
# --------------------------------------------------------------------------------
# 0             0x0             TRX firmware header, little endian
# 28            0x1C            gzip compressed data
# 1572892       0x18001C        Squashfs filesystem
```

### 指定签名类型

```bash
# 只显示指定签名
binwalk -A firmware.bin          # 操作码／可执行代码
binwalk -R firmware.bin          # 原始字节（签名文件）
binwalk -B firmware.bin          # 标准签名（默认）

# 显示所有操作码
binwalk -A firmware.bin

# 搜索原始字符串／十六进制字节
binwalk -R "\x89PNG" firmware.bin
binwalk -R "password" firmware.bin
```

### 包含／排除过滤器

```bash
# 只包含指定类型
binwalk --include="filesystem" firmware.bin
binwalk --include="compressed" firmware.bin

# 排除指定类型
binwalk --exclude="jpeg" firmware.bin

# 只显示指定签名
binwalk -y "squashfs" firmware.bin
binwalk -y "gzip" firmware.bin
```

> **译注：**Binwalk 2 的 `-R` 后面接待匹配的原始字节／字符串，而不是签名文件；所以上面的 `binwalk -R firmware.bin` 示例不能理解为正常扫描该文件。自定义 magic 文件使用 `-m`。参见 [2.3.4 签名模块](https://github.com/ReFirmLabs/binwalk/blob/v2.3.4/src/binwalk/modules/signature.py)。当前版本的包含／排除参数按签名名称选择类型，参见 [Binwalk 3 提取文档](https://github.com/ReFirmLabs/binwalk/wiki/File-Analysis-and-Extraction)。

### 自定义签名

```bash
# 使用自定义签名文件
binwalk -m custom_signatures.txt firmware.bin

# 签名文件格式：
# 0   string  MZ  Microsoft executable
# 0   belong  0x89504E47  PNG image
```

---

<a id="-extraction"></a>

## 📦 提取

### 基本提取操作

```bash
# 提取所有识别出的文件
binwalk -e firmware.bin
# 创建：_firmware.bin.extracted/

# 提取到指定目录
binwalk -e -C /output/dir firmware.bin

# 递归提取（matryoshka）
binwalk -eM firmware.bin
binwalk --extract --matryoshka firmware.bin
```

### 提取选项

```bash
# 提取后删除中间文件（清理）
binwalk -er firmware.bin

# 限制递归深度
binwalk -eM --depth=3 firmware.bin

# 静默提取
binwalk -eq firmware.bin

# 详细显示提取过程
binwalk -ev firmware.bin
```

### 手动提取（dd）

```bash
# 当 binwalk 无法自动提取时
# 从 binwalk 扫描结果中获取偏移量
binwalk firmware.bin
# 示例：Squashfs 位于偏移量 1572892

# 使用 dd 提取
dd if=firmware.bin of=squashfs.bin bs=1 skip=1572892

# 或指定 count（已知大小时）
dd if=firmware.bin of=squashfs.bin bs=1 skip=1572892 count=5000000
```

### 按指定类型雕刻数据

```bash
# 只提取指定文件类型
binwalk -D "jpeg:jpg" firmware.bin
binwalk -D "png:png" firmware.bin
binwalk -D "elf:elf" firmware.bin

# 多种类型
binwalk -D "jpeg:jpg" -D "png:png" firmware.bin

# 格式：type:extension:command
binwalk -D "gzip:gz:gunzip %e" firmware.bin
```

---

<a id="-entropy-analysis"></a>

## 📊 熵分析

### 什么是熵？

熵用于衡量数据的随机性：
- **高熵（0.9-1.0）** = 加密／压缩数据
- **中等熵（0.5-0.8）** = 可执行代码
- **低熵（0.0-0.4）** = 纯文本、重复数据

> **译注：**上述区间及下方图形解释只是经验性示意，不能据此可靠地区分压缩、加密、代码或文本。Binwalk 2 常见熵值归一化到 0–1，而 [Binwalk 3 熵图](https://github.com/ReFirmLabs/binwalk/wiki/Generating-Entropy-Graphs)使用 0–8 刻度；高熵既可能来自压缩，也可能来自加密。

### 生成熵图

```bash
# 计算熵
binwalk -E firmware.bin
binwalk --entropy firmware.bin

# 保存熵图
binwalk -E --save firmware.bin
# 创建：firmware.bin.png

# 显示图例
binwalk -EJ firmware.bin
```

### 熵分析选项

```bash
# 设置块大小
binwalk -E --block=1024 firmware.bin

# 显示熵值（不绘图）
binwalk -E --nplot firmware.bin

# 检测上升／下降边缘
binwalk -E --edge firmware.bin
```

> **译注：**Binwalk 2 的 `-J` 是保存 PNG，而不是启用图例；其熵模块也没有上文的 `--edge` 选项。参见 [2.3.4 熵模块](https://github.com/ReFirmLabs/binwalk/blob/v2.3.4/src/binwalk/modules/entropy.py)。Binwalk 3 默认保存熵图，可用 `--png` 指定路径；原示例保持不变。

### 解读熵

```
高位平坦线 (~1.0)               → 加密数据
尖峰形态 (~0.7)                 → 压缩数据
波浪形曲线 (~0.5)               → 代码／数据
低位平坦线 (~0.2)               → 纯文本
突然降至 0                      → 空字节填充
```

---

<a id="-file-system-analysis"></a>

## 💾 文件系统分析

### 常见固件文件系统

| 文件系统 | 说明 |
|-------------| ------------- |
| **SquashFS** | 只读压缩文件系统（最常见） |
| **JFFS2** | 日志型闪存文件系统 |
| **UBIFS** | 面向大容量闪存，相较 JFFS2 有所改进 |
| **CramFS** | 压缩 ROM 文件系统 |
| **YAFFS** | 又一种闪存文件系统（Yet Another Flash File System） |
| **ext2/3/4** | 标准 Linux 文件系统 |

### 提取 SquashFS

```bash
# 使用 Binwalk 提取
binwalk -e firmware.bin

# 使用 unsquashfs 手动提取
unsquashfs squashfs-root.bin
ls squashfs-root/

# 挂载（若支持）
sudo mount -t squashfs squashfs.bin /mnt
```

### 提取 JFFS2

```bash
# 使用 jefferson
pip3 install jefferson
jefferson firmware.jffs2 -d output/

# 或使用 binwalk
binwalk -e firmware.bin
```

### 提取 UBIFS

```bash
# 使用 ubireader
pip3 install ubi_reader
ubireader_extract_images firmware.ubi -o output/
ubireader_extract_files firmware.ubi -o output/
```

### 分析提取出的文件

```bash
# 提取后浏览内容
cd _firmware.bin.extracted/

# 查找值得关注的文件
find . -name "*.conf"
find . -name "passwd"
find . -name "shadow"
find . -name "*.key"
find . -name "*.pem"

# 搜索密码／敏感信息
grep -r "password" .
grep -r "admin" .
grep -r "root:" .

# 查找可执行文件
find . -type f -executable
file */bin/*
```

---

<a id="-ctf-challenges"></a>

## 🎯 CTF 挑战

### 常见 CTF 场景

#### 图像中的隐藏文件

```bash
# 扫描图像中的隐藏数据
binwalk image.png
binwalk image.jpg

# 提取隐藏文件
binwalk -e image.png

# 同时检查字符串
strings image.png | head -50
```

#### 多层归档文件

```bash
# 递归提取（matryoshka）
binwalk -eM challenge.bin

# 多层压缩
binwalk -eM --depth=10 nested.bin
```

#### 查找隐藏文本

```bash
# 配合 strings 使用
binwalk challenge.bin
strings challenge.bin | grep -i "flag"
strings challenge.bin | grep "CTF{"
```

#### 加密区段

```bash
# 通过熵分析发现加密数据
binwalk -E challenge.bin

# 如果发现高熵，可能存在加密
# 尝试常见密码，或从其他位置查找密钥
```

### CTF 解题流程

```bash
# 1. 基础扫描
binwalk challenge.bin

# 2. 检查熵
binwalk -E challenge.bin

# 3. 提取全部内容
binwalk -eM challenge.bin

# 4. 搜索提取出的文件
cd _challenge.bin.extracted/
find . -type f -exec file {} \;
grep -r "flag" .
strings * | grep -i flag

# 5. 检查隐藏数据
binwalk -A challenge.bin    # 操作码
xxd challenge.bin | head    # 十六进制转储
```

### 隐写检测

```bash
# 扫描图像中的隐藏数据
binwalk -B suspicious.jpg

# 检查追加的数据
binwalk suspicious.png

# 发现后提取
binwalk -e suspicious.jpg

# 将文件大小与预期值比较
ls -la suspicious.jpg
```

---

<a id="-advanced-options"></a>

## ⚙️ 高级选项

> **译注：**本节混合了不同版本及部分不适用的选项。当前 [Binwalk 3 参数定义](https://github.com/ReFirmLabs/binwalk/blob/master/src/cliparser.rs)不提供这里的 `-W`、`--disable-plugin`、`--csv` 或 `--json`；JSON 结果使用 [`--log`](https://github.com/ReFirmLabs/binwalk/wiki/JSON-Analysis-Results)保存。Binwalk 2 的 `-W` 本身已支持文件比较，源码中没有单独的 `--diff` 开关，见 [2.3.4 hexdiff 模块](https://github.com/ReFirmLabs/binwalk/blob/v2.3.4/src/binwalk/modules/hexdiff.py)。原命令保留。

### 十六进制转储输出

```bash
# 显示各偏移处的十六进制转储
binwalk -W firmware.bin
binwalk --hexdump firmware.bin

# 转储指定偏移处的十六进制数据
binwalk -o 0x1000 -l 512 -W firmware.bin
```

### 偏移选项

```bash
# 从指定偏移开始
binwalk -o 1024 firmware.bin
binwalk --offset=1024 firmware.bin

# 只扫描指定长度
binwalk -l 10000 firmware.bin
binwalk --length=10000 firmware.bin
```

### 性能选项

```bash
# 使用多个进程
binwalk --threads=4 firmware.bin

# 禁用指定插件
binwalk --disable-plugin=lzma firmware.bin
```

### 输出格式

```bash
# CSV 输出
binwalk --csv firmware.bin > results.csv

# JSON 输出
binwalk --json firmware.bin > results.json

# 将日志写入文件
binwalk -f scan.log firmware.bin
```

### 比较两个文件

```bash
# 比较两个固件版本
binwalk -W --diff firmware_v1.bin firmware_v2.bin

# 以十六进制显示差异
```

---

<a id="-quick-reference"></a>

## 📊 快速参考

### 常用命令

| 用途 | 命令 |
|---------| --------- |
| 扫描文件 | `binwalk firmware.bin` |
| 提取全部内容 | `binwalk -e firmware.bin` |
| 递归提取 | `binwalk -eM firmware.bin` |
| 熵分析 | `binwalk -E firmware.bin` |
| 操作码扫描 | `binwalk -A firmware.bin` |
| 搜索字符串 | `binwalk -R "pattern" file` |
| 提取到指定目录 | `binwalk -e -C outdir/ file` |
| 提取并删除中间文件 | `binwalk -er firmware.bin` |

### 常见文件签名

| 签名 | 说明 |
|-----------| ------------- |
| `\x1f\x8b\x08` | gzip 压缩 |
| `BZh` | bzip2 压缩 |
| `\xfd7zXZ` | xz 压缩 |
| `hsqs` | SquashFS（小端序） |
| `sqsh` | SquashFS（大端序） |
| `\x85\x19` | JFFS2 |
| `UBI#` | UBIFS |
| `\x89PNG` | PNG 图像 |
| `\xff\xd8\xff` | JPEG 图像 |
| `PK\x03\x04` | ZIP 归档 |

> **译注：**表中的 `UBI#` 是 UBI 擦除计数器头部标识，不能直接等同于 UBIFS 文件系统签名；UBIFS 位于 UBI 卷之上。参见 [Linux UBI 头部定义](https://github.com/torvalds/linux/blob/master/drivers/mtd/ubi/ubi-media.h)与 [UBIFS 文档](https://docs.kernel.org/filesystems/ubifs.html)。

### 提取流程

```
1. binwalk firmware.bin         → 识别内容
2. binwalk -E firmware.bin      → 检查是否加密
3. binwalk -e firmware.bin      → 提取
4. cd _firmware.bin.extracted   → 浏览内容
5. find . -name "*" -type f     → 列出所有文件
6. grep -r "password" .         → 查找敏感信息
```

### 手动提取

```bash
# 从 binwalk 获取偏移量
binwalk firmware.bin
# Squashfs 位于 0x180000（十进制 1572864）

# 使用 dd 提取
dd if=firmware.bin of=fs.squashfs bs=1 skip=1572864

# 提取 squashfs
unsquashfs fs.squashfs
```

### 常用文件位置（固件）

| 路径 | 内容 |
|------| ---------- |
| `/etc/passwd` | 用户账户 |
| `/etc/shadow` | 密码哈希 |
| `/etc/config/` | 设备配置 |
| `/etc/init.d/` | 启动脚本 |
| `/usr/bin/` | 用户程序 |
| `/www/` | Web 界面 |
| `/etc/ssl/` | SSL 证书 |

---

## 📚 参考资料

- [Binwalk GitHub](https://github.com/ReFirmLabs/binwalk)
- [Binwalk 维基](https://github.com/ReFirmLabs/binwalk/wiki)
- [固件分析入门](https://www.youtube.com/results?search_query=firmware+analysis+binwalk)
- [OWASP 物联网固件分析](https://owasp.org/www-project-iot-security/)

### 相关速查表
- [ExifTool](../../ExifTool/README.md)
- [Volatility](../../Volatility/README.md)
- [Autopsy](../../Autopsy/README.md)
- [Linux 命令](../../Linux-Commands/README.md)

---

<p align="center">
  <b>🔧 掌握固件分析！</b><br>
  <i>Binwalk — 揭开固件中的秘密</i>
</p>
