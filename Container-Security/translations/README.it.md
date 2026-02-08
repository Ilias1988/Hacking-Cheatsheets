# 🐳 Container Security Cheatsheets

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

## 🎯 Cos'è il Container Security Testing?

Il container security testing consiste nell'identificare vulnerabilità in:
- 🐳 **Docker** - Immagini, container, demoni
- ☸️ **Kubernetes** - Pod, RBAC, secrets, network policies
- 📦 **Immagini Container** - Base images, layers, vulnerabilità
- 🔐 **Secrets** - Credenziali esposte, variabili ambiente
- 🌐 **Network** - Networking container, service mesh

---

## 📖 Guide alla Container Security

| Piattaforma | Descrizione | Guida |
|-------------|-------------|-------|
| **Docker** | Container escape, analisi immagini, exploitation di demoni | [📄 Visualizza](./Docker-Pentesting.it.md) |
| **Kubernetes** | Attacchi cluster K8s, bypass RBAC, pod escape | [📄 Visualizza](./Kubernetes-Pentesting.it.md) |

---

## 🛠️ Strumenti Essenziali per la Container Security

| Strumento | Scopo | Piattaforma |
|-----------|-------|-------------|
| **Trivy** | Scanner vulnerabilità | Docker/K8s |
| **kube-hunter** | Pentesting Kubernetes | Kubernetes |
| **kubeaudit** | Audit sicurezza cluster | Kubernetes |
| **Falco** | Monitoraggio runtime | Entrambi |
| **Docker Bench** | Audit sicurezza Docker | Docker |
| **Dive** | Analisi layer immagini | Docker |
| **kubeletctl** | Exploitation kubelet | Kubernetes |
| **Peirates** | Framework attacco K8s | Kubernetes |

### Installazione Rapida Strumenti
```bash
# Trivy (scanner vulnerabilità)
brew install trivy
# oppure
docker run aquasec/trivy image nginx:latest

# kube-hunter (pentest K8s)
pip install kube-hunter
kube-hunter --remote target-cluster.com

# kubeletctl
go install github.com/cyberark/kubeletctl@latest

# Docker Bench Security
git clone https://github.com/docker/docker-bench-security.git
cd docker-bench-security && ./docker-bench-security.sh

# Dive (analisi immagini)
brew install dive
dive nginx:latest
```

---

## 📊 Vulnerabilità Container Comuni

| Vulnerabilità | Descrizione | Impatto |
|---------------|-------------|---------|
| **Privileged Container** | Flag --privileged | Container escape |
| **Exposed Docker Socket** | /var/run/docker.sock montato | Accesso completo host |
| **Sensitive Mounts** | /etc, /root montati | Data leak |
| **Insecure Capabilities** | CAP_SYS_ADMIN, ecc. | Privilege escalation |
| **Anonymous Kubelet** | Nessuna auth su API kubelet | Accesso pod |
| **Exposed K8s Dashboard** | Nessuna auth richiesta | Compromissione cluster |
| **Hardcoded Secrets** | Secrets nelle immagini | Furto credenziali |
| **Outdated Base Images** | CVE noti | RCE, privilege escalation |

---

## 🔗 Riferimento Rapido: Comandi Docker

```bash
# Info container
docker ps -a
docker inspect CONTAINER_ID

# Controlla capabilities
docker inspect --format='{{.HostConfig.Privileged}}' CONTAINER_ID
docker inspect --format='{{.HostConfig.CapAdd}}' CONTAINER_ID

# Controlla mount
docker inspect --format='{{.Mounts}}' CONTAINER_ID

# Analisi immagini
docker history IMAGE_NAME
docker inspect IMAGE_NAME
```

## 🔗 Riferimento Rapido: Comandi Kubernetes

```bash
# Info cluster
kubectl cluster-info
kubectl get nodes

# Mostra pod (tutti i namespace)
kubectl get pods --all-namespaces

# Controlla RBAC
kubectl auth can-i --list
kubectl auth can-i create pods

# Ottieni secrets
kubectl get secrets --all-namespaces
kubectl get secret SECRET_NAME -o jsonpath='{.data}'
```

---

## 📚 Risorse

- [OWASP Container Security](https://owasp.org/www-project-docker-security/)
- [Kubernetes Security](https://kubernetes.io/docs/concepts/security/)
- [Docker Security Best Practices](https://docs.docker.com/develop/security-best-practices/)
- [HackTricks Containers](https://book.hacktricks.xyz/linux-hardening/privilege-escalation/docker-security)

---

<p align="center">
  <b>🐳 Hackera i container in modo responsabile!</b>
</p>
