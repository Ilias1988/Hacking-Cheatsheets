# 🔄 逆向工程 CTF 速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Reverse-Engineering-CTF.tr.md) • [🇬🇷 Ελληνικά](Reverse-Engineering-CTF.el.md)

> **界面说明：** Ghidra 与 IDA 的菜单、反编译快捷键不能混用。原文 Ghidra 段落中的 F5 说明按原样保留；官方流程是在 Code Browser 打开 `Window → Decompile`，窗口会跟随当前函数更新，参见 [Ghidra 官方帮助](https://github.com/NationalSecurityAgency/ghidra/blob/master/Ghidra/Features/Decompiler/src/main/help/help/topics/DecompilePlugin/DecompilerIntro.html)。汇编中的 `0x90` 表示 x86 NOP，不能作为所有架构的通用补丁字节。

---

## 🔍 初步分析

```bash
# 文件类型
file binary

# 提取字符串
strings binary | grep -i flag
strings -n 8 binary

# ELF 信息
readelf -h binary

# 检查保护机制
checksec binary
```

---

## 🛠️ 反汇编工具

### Ghidra（免费）
```
1. File → Import File（导入文件）
2. Analyze → Auto Analyze（自动分析）
3. 寻找 main() 函数
4. 寻找用于密码检查的 strcmp/memcmp 调用
5. 按 F5 反编译（原文快捷键说明，需核对工具配置）
```

### IDA Free
```
1. 打开二进制文件
2. 按 F5 查看伪代码（Hex-Rays）
3. 按 X 查找交叉引用
4. 按 N 重命名变量
```

### Radare2
```bash
r2 binary
aaa          # 分析
afl          # 列出函数
pdf @main    # 反汇编 main
VV           # 可视化模式
```

---

## 🐛 GDB 调试

```bash
# 启动
gdb ./binary

# 断点
b main
b *0x401234

# 运行
r
r arg1 arg2

# 单步执行
n           # 下一行
s           # 步入
c           # 继续

# 检查数据
x/s $rdi    # 字符串
x/10x $rsp  # 从栈中查看 10 个十六进制单元
info reg    # 寄存器

# 寻找 flag
find 0x0, 0xffffffff, "flag"
```

### GEF/PEDA
```bash
# 安装 GEF
bash -c "$(curl -fsSL https://gef.blah.cat/sh)"

# 命令
vmmap
heap
telescope $rsp
```

---

## 🐍 使用 Python 修改二进制文件

```python
# 读取二进制文件
with open("binary", "rb") as f:
    data = bytearray(f.read())

# 修改字节
data[0x1234] = 0x90  # NOP

# 写入
with open("patched", "wb") as f:
    f.write(data)
```

---

## 📊 常见模式

| 检查项 | 绕过思路 |
|-------|--------|
| `strcmp(input, "password")` | 提取 "password" |
| `if (x == 0x1337)` | 寻找数值 0x1337 |
| 反调试 | 修改 ptrace 相关代码 |
| 混淆字符串 | 动态分析 |

---

## 📋 逆向工程检查清单

```markdown
□ 运行 file/strings 命令
□ 检查调试符号
□ 在 Ghidra/IDA 中打开
□ 寻找 main 函数
□ 寻找字符串比较
□ 跟踪用户输入
□ 必要时修改反调试代码
□ 使用 GDB 开展动态分析
```

---

**返回 CTF:** [🏁 CTF 概览](./README.zh-CN.md)
