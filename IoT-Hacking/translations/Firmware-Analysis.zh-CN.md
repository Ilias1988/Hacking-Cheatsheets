# 💾 固件分析速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

> **版本与示例说明：** Binwalk v3 已用 Rust 重写，本文中的命令行示例保留原文；不同主版本的选项和提取行为需按所用版本核对，参见 [Binwalk 官方项目](https://github.com/ReFirmLabs/binwalk)。高熵也可能来自压缩数据，不能单凭熵值确认加密。`unsquashfs` 的输入应是镜像文件或块设备，`squashfs-root` 通常为输出目录，详见 [SquashFS 官方说明](https://github.com/plougher/squashfs-tools/blob/master/Documentation/4.7.4/USAGE-UNSQUASHFS.md)。JFFS2 示例假设内核支持相应模块，且 `/dev/mtdblock0` 对应预期的测试设备，并非适用于所有主机的通用路径。

[🇹🇷 Türkçe Sürüm](Firmware-Analysis.tr.md) • [🇬🇷 Ελληνικά](Firmware-Analysis.el.md)

---

## 📥 固件获取

```bash
# 从厂商网站下载
# 通过中间人方式（MITM）捕获 OTA（空中下载）更新
# 从设备中转储（UART、SPI、JTAG）
# 从移动应用中提取
```

---

## 🔍 初步分析

```bash
# 文件类型
file firmware.bin

# 字符串
strings firmware.bin | grep -i password
strings firmware.bin | grep -i admin
strings firmware.bin | grep -i http

# 熵分析（辅助识别压缩或加密）
binwalk -E firmware.bin
```

---

## 📦 使用 Binwalk 提取

```bash
# 扫描固件
binwalk firmware.bin

# 提取全部内容
binwalk -e firmware.bin

# 提取到指定目录
binwalk -e -C output firmware.bin

# 递归提取
binwalk -eM firmware.bin

# 指定特征签名
binwalk -D 'elf:elf' firmware.bin
```

---

## 🗂️ 文件系统

### SquashFS
```bash
# 提取
unsquashfs squashfs-root
unsquashfs -d output filesystem.squashfs

# 重新打包
mksquashfs output modified.squashfs -comp xz
```

### JFFS2
```bash
# 挂载
modprobe jffs2
modprobe mtdram total_size=32768 erase_size=256
dd if=jffs2.img of=/dev/mtdblock0
mount -t jffs2 /dev/mtdblock0 /mnt
```

### UBIFS
```bash
# 使用 ubi_reader 提取
ubireader_extract_files image.ubi
```

---

## 🔐 机密信息与凭据

```bash
# 搜索凭据
grep -rni "password" filesystem/
grep -rni "admin" filesystem/
grep -rni "api_key" filesystem/
grep -rni "secret" filesystem/

# 查找 SSH 密钥
find . -name "id_rsa*"
find . -name "*.pem"

# 查找配置文件
find . -name "*.conf"
find . -name "*.cfg"
cat etc/shadow
cat etc/passwd
```

---

## 🔄 二进制分析

```bash
# 查找 ELF 二进制文件
find . -type f -exec file {} \; | grep ELF

# 使用 Ghidra 分析
ghidraRun

# 查找硬编码字符串
strings binary | grep -E "http|ftp|telnet|ssh"
```

---

## 🛠️ 工具

| 工具 | 用途 |
|------|---------|
| **Binwalk** | 提取 |
| **firmware-mod-kit** | 修改固件 |
| **EMBA** | 自动化分析 |
| **Ghidra** | 二进制逆向工程（RE） |
| **firmwalker** | 搜索机密信息 |

---

## 📋 固件分析检查清单

```markdown
□ 识别文件类型
□ 检查熵值（是否加密？）
□ 使用 binwalk 提取
□ 挂载文件系统
□ 搜索凭据
□ 分析二进制文件
□ 检查后门
□ 查找调试接口
```

---

**返回 IoT 目录：** [📡 IoT 安全测试](./README.zh-CN.md)
