# 🐳 容器安全速查表

<!-- language-home-navigation -->
[🏠 中文首页](../../README.zh-CN.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

> **译注：** Pod 是 Kubernetes 的基本工作负载单元；RBAC 指基于角色的访问控制；capabilities 指 Linux 按功能拆分的特权。Secret 是 Kubernetes 资源类型，内容常以 Base64 表示，编码不等同于加密。工具安装和扫描段落保留作参考，本次翻译未执行这些操作。

```
 ██████╗ ██████╗ ███╗   ██╗████████╗ █████╗ ██╗███╗   ██╗███████╗██████╗ 
██╔════╝██╔═══██╗████╗  ██║╚══██╔══╝██╔══██╗██║████╗  ██║██╔════╝██╔══██╗
██║     ██║   ██║██╔██╗ ██║   ██║   ███████║██║██╔██╗ ██║█████╗  ██████╔╝
██║     ██║   ██║██║╚██╗██║   ██║   ██╔══██║██║██║╚██╗██║██╔══╝  ██╔══██╗
╚██████╗╚██████╔╝██║ ╚████║   ██║   ██║  ██║██║██║ ╚████║███████╗██║  ██║
 ╚═════╝ ╚═════╝ ╚═╝  ╚═══╝   ╚═╝   ╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝╚══════╝╚═╝  ╚═╝
███████╗███████╗ ██████╗██╗   ██╗██████╗ ██╗████████╗██╗   ██╗           
██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██║╚══██╔══╝╚██╗ ██╔╝           
███████╗█████╗  ██║     ██║   ██║██████╔╝██║   ██║    ╚████╔╝            
╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██║   ██║     ╚██╔╝             
███████║███████╗╚██████╗╚██████╔╝██║  ██║██║   ██║      ██║              
╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝   ╚═╝      ╚═╝              
```

---

## 🎯 容器安全测试

容器安全测试旨在发现以下组件中的漏洞：
- 🐳 **Docker** - 镜像、容器与守护进程
- ☸️ **Kubernetes** - Pod、基于角色的访问控制（RBAC）、Secret 与网络策略
- 📦 **容器镜像** - 基础镜像、镜像层与漏洞
- 🔐 **敏感信息（Secrets）** - 暴露的凭据与环境变量
- 🌐 **网络** - 容器网络与服务网格

---

## 📖 容器安全指南

| 平台 | 说明 | 指南 |
|----------|-------------|-------|
| **Docker** | 容器逃逸、镜像分析与守护进程利用 | [📄 查看](./Docker-Pentesting.zh-CN.md) |
| **Kubernetes** | K8s 集群攻击、RBAC 绕过与 Pod 逃逸 | [📄 查看](./Kubernetes-Pentesting.zh-CN.md) |

---

## 🛠️ 容器安全必备工具

| 工具 | 用途 | 平台 |
|------|---------|----------|
| **Trivy** | 漏洞扫描工具 | Docker/K8s |
| **kube-hunter** | K8s 渗透测试 | Kubernetes |
| **kubeaudit** | 集群安全审计 | Kubernetes |
| **Falco** | 运行时安全监控 | 两者均支持 |
| **Docker Bench** | Docker 安全审计 | Docker |
| **Dive** | 镜像层分析 | Docker |
| **kubeletctl** | Kubelet 利用 | Kubernetes |
| **Peirates** | K8s 攻击框架 | Kubernetes |

### 工具快速安装
```bash
# Trivy（漏洞扫描工具）
brew install trivy
# 或
docker run aquasec/trivy image nginx:latest

# kube-hunter（K8s 渗透测试）
pip install kube-hunter
kube-hunter --remote target-cluster.com

# kubeletctl
go install github.com/cyberark/kubeletctl@latest

# Docker Bench Security
git clone https://github.com/docker/docker-bench-security.git
cd docker-bench-security && ./docker-bench-security.sh

# Dive（镜像分析）
brew install dive
dive nginx:latest
```

---

## 📊 常见容器漏洞

| 漏洞 | 说明 | 影响 |
|---------------|-------------|--------|
| **特权容器** | --privileged 参数 | 容器逃逸 |
| **暴露的 Docker 套接字** | 挂载了 /var/run/docker.sock | 完全访问宿主机 |
| **敏感挂载** | 挂载了 /etc、/root | 数据泄露 |
| **不安全的 Linux capabilities（权能）** | CAP_SYS_ADMIN 等权能 | 权限提升 |
| **匿名 Kubelet 访问** | Kubelet API 未要求身份验证 | 访问 Pod |
| **暴露的 K8s Dashboard** | 无需身份验证 | 接管集群 |
| **硬编码敏感信息** | 镜像中包含敏感信息 | 凭据窃取 |
| **过时的基础镜像** | 已知 CVE 漏洞 | 远程代码执行（RCE）与权限提升 |

---

## 🔗 快速参考：Docker 命令

```bash
# 容器信息
docker ps -a
docker inspect CONTAINER_ID

# 检查 Linux capabilities（权能）
docker inspect --format='{{.HostConfig.Privileged}}' CONTAINER_ID
docker inspect --format='{{.HostConfig.CapAdd}}' CONTAINER_ID

# 检查挂载
docker inspect --format='{{.Mounts}}' CONTAINER_ID

# 镜像分析
docker history IMAGE_NAME
docker inspect IMAGE_NAME
```

## 🔗 快速参考：Kubernetes 命令

```bash
# 集群信息
kubectl cluster-info
kubectl get nodes

# 列出 Pod（所有命名空间）
kubectl get pods --all-namespaces

# 检查 RBAC
kubectl auth can-i --list
kubectl auth can-i create pods

# 获取 Secret
kubectl get secrets --all-namespaces
kubectl get secret SECRET_NAME -o jsonpath='{.data}'
```

---

## 📚 参考资源

- [OWASP 容器安全](https://owasp.org/www-project-docker-security/)
- [Kubernetes 安全](https://kubernetes.io/docs/concepts/security/)
- [Docker 安全最佳实践](https://docs.docker.com/develop/security-best-practices/)
- [HackTricks 容器专题](https://book.hacktricks.xyz/linux-hardening/privilege-escalation/docker-security)

---

<p align="center">
  <b>🐳 负责任地开展容器安全测试！</b>
</p>
