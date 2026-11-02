# 🔬 数字取证 CTF 速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Forensics-CTF.tr.md) • [🇬🇷 Ελληνικά](Forensics-CTF.el.md)

> **版本说明：** 本文带 `--profile=Win7SP1x64` 的命令属于 Volatility 2；Volatility 3 使用符号表，不沿用 profile 机制，参见 [官方迁移说明](https://volatility3.readthedocs.io/en/latest/vol2to3.html)。音频段落中的 [Plot Spectrum](https://manual.audacityteam.org/man/plot_spectrum.html) 是频谱图，按频率显示幅度；随时间变化的声谱图应参考 [Spectrogram View](https://manual.audacityteam.org/man/spectrogram_view.html)。原文菜单与命令保留作对照。

---

## 📁 文件分析

```bash
# 文件类型
file unknown_file

# 十六进制转储
xxd file | head
hexdump -C file | head

# 提取字符串
strings -n 6 file
strings -e l file    # 小端序

# 文件雕刻（按特征提取文件）
binwalk file
binwalk -e file      # 提取
foremost file
```

---

## 🖼️ 隐写术

### 图像

```bash
# 元数据
exiftool image.jpg
identify -verbose image.png

# Steghide（JPEG）
steghide extract -sf image.jpg

# Zsteg（PNG）
zsteg image.png
zsteg -a image.png   # 全部方法

# 最低有效位（LSB）分析
stegsolve.jar        # 图形界面工具

# 图像中的字符串
strings image.png | grep -i flag
```

### 音频
```bash
# 声谱图（Audacity；原文菜单实际对应频谱分析）
打开文件 → Analyze → Plot Spectrum（频谱分析）

# Sonic Visualiser
# 检查频率中是否隐藏数据
```

---

## 🧠 内存取证

### Volatility
```bash
# 识别 profile（系统配置档）
volatility -f dump.raw imageinfo

# 进程列表
volatility -f dump.raw --profile=Win7SP1x64 pslist
volatility -f dump.raw --profile=Win7SP1x64 pstree

# 命令历史
volatility -f dump.raw --profile=Win7SP1x64 cmdscan
volatility -f dump.raw --profile=Win7SP1x64 consoles

# 转储进程
volatility -f dump.raw --profile=Win7SP1x64 procdump -p 1234 -D output/

# 文件
volatility -f dump.raw --profile=Win7SP1x64 filescan | grep -i flag
```

---

## 💽 磁盘取证

```bash
# 挂载镜像
mount -o loop image.dd /mnt/disk

# 文件系统信息
fsstat image.dd

# Autopsy（图形界面）
autopsy

# Sleuth Kit
fls image.dd
icat image.dd 1234 > recovered_file
```

---

## 📊 网络取证（PCAP）

```bash
# Wireshark
wireshark capture.pcap

# Tshark
tshark -r capture.pcap
tshark -r capture.pcap -Y "http" -T fields -e http.file_data

# 提取文件
tcpflow -r capture.pcap
```

---

## 📋 取证检查清单

```markdown
□ 检查文件类型（file 命令）
□ 使用 strings 搜索 flag
□ 检查元数据（exiftool）
□ 使用 Binwalk 查找嵌入文件
□ 使用隐写分析工具
□ 内存分析（Volatility）
□ PCAP 分析（Wireshark）
```

---

**返回 CTF:** [🏁 CTF 概览](./README.zh-CN.md)
