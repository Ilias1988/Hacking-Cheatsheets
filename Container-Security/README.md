# 🐳 Container Security Cheatsheets

> **Review status:** Source verification pending. Confirm version-sensitive commands against the official documentation before use.

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

## 🎯 Container Security Testing

Container security testing involves identifying vulnerabilities in:
- 🐳 **Docker** - Images, containers, daemon
- ☸️ **Kubernetes** - Pods, RBAC, secrets, network policies
- 📦 **Container Images** - Base images, layers, vulnerabilities
- 🔐 **Secrets** - Exposed credentials, env vars
- 🌐 **Network** - Container networking, service mesh

---

## 📖 Container Security Guides

| Platform | Description | Guide |
|----------|-------------|-------|
| **Docker** | Container escape, image analysis, daemon exploitation | [📄 View](./Docker-Pentesting.md) |
| **Kubernetes** | K8s cluster attacks, RBAC bypass, pod escape | [📄 View](./Kubernetes-Pentesting.md) |

---

## 🛠️ Essential Container Security Tools

| Tool | Purpose | Platform |
|------|---------|----------|
| **Trivy** | Vulnerability scanner | Docker/K8s |
| **kube-hunter** | K8s penetration testing | Kubernetes |
| **kubeaudit** | Cluster security audit | Kubernetes |
| **Falco** | Runtime security monitoring | Both |
| **Docker Bench** | Docker security audit | Docker |
| **Dive** | Image layer analysis | Docker |
| **kubeletctl** | Kubelet exploitation | Kubernetes |
| **Peirates** | K8s attack framework | Kubernetes |

### Quick Tool Install
```bash
# Trivy (vulnerability scanner)
brew install trivy
# or
docker run aquasec/trivy image nginx:latest

# kube-hunter (K8s pentest)
pip install kube-hunter
kube-hunter --remote target-cluster.com

# kubeletctl
go install github.com/cyberark/kubeletctl@latest

# Docker Bench Security
git clone https://github.com/docker/docker-bench-security.git
cd docker-bench-security && ./docker-bench-security.sh

# Dive (image analysis)
brew install dive
dive nginx:latest
```

---

## 📊 Common Container Vulnerabilities

| Vulnerability | Description | Impact |
|---------------|-------------|--------|
| **Privileged Container** | --privileged flag | Container escape |
| **Exposed Docker Socket** | /var/run/docker.sock mounted | Full host access |
| **Sensitive Mounts** | /etc, /root mounted | Data leak |
| **Insecure Capabilities** | CAP_SYS_ADMIN, etc. | Privilege escalation |
| **Anonymous Kubelet** | No auth on kubelet API | Pod access |
| **Exposed K8s Dashboard** | No auth required | Cluster takeover |
| **Hardcoded Secrets** | Secrets in images | Credential theft |
| **Outdated Base Images** | Known CVEs | RCE, privilege escalation |

---

## 🔗 Quick Reference: Docker Commands

```bash
# Container info
docker ps -a
docker inspect CONTAINER_ID

# Check capabilities
docker inspect --format='{{.HostConfig.Privileged}}' CONTAINER_ID
docker inspect --format='{{.HostConfig.CapAdd}}' CONTAINER_ID

# Check mounts
docker inspect --format='{{.Mounts}}' CONTAINER_ID

# Image analysis
docker history IMAGE_NAME
docker inspect IMAGE_NAME
```

## 🔗 Quick Reference: Kubernetes Commands

```bash
# Cluster info
kubectl cluster-info
kubectl get nodes

# List pods (all namespaces)
kubectl get pods --all-namespaces

# Check RBAC
kubectl auth can-i --list
kubectl auth can-i create pods

# Get secrets
kubectl get secrets --all-namespaces
kubectl get secret SECRET_NAME -o jsonpath='{.data}'
```

---

## 📚 Resources

- [OWASP Container Security](https://owasp.org/www-project-docker-security/)
- [Kubernetes Security](https://kubernetes.io/docs/concepts/security/)
- [Docker Security Best Practices](https://docs.docker.com/develop/security-best-practices/)
- [HackTricks Containers](https://book.hacktricks.xyz/linux-hardening/privilege-escalation/docker-security)

---

<p align="center">
  <b>🐳 Hack Containers Responsibly!</b>
</p>
