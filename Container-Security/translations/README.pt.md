# 🐳 Guias de referência de segurança de containers

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

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

> **Termos e âmbito:** Um container é um processo isolado que partilha o kernel do host; o host é o sistema que o executa. Um pod agrupa containers no Kubernetes. Um registry armazena imagens, e secrets são objetos ou valores sensíveis, como credenciais. As vulnerabilidades da tabela dependem da configuração e das permissões: uma API acessível ou uma capability isolada não demonstram, por si só, comprometimento. Ver a documentação de [execução de containers Docker](https://docs.docker.com/engine/containers/run/) e de [autenticação/autorização do kubelet](https://kubernetes.io/docs/reference/access-authn-authz/kubelet-authn-authz/).

---

## 🎯 Testes de segurança de containers

Os testes de segurança de containers consistem em identificar vulnerabilidades em:
- 🐳 **Docker** - Imagens, containers e daemon (serviço em segundo plano)
- ☸️ **Kubernetes** - Pods, RBAC (controlo de acesso baseado em funções), secrets e políticas de rede
- 📦 **Imagens de containers** - Imagens base, camadas e vulnerabilidades
- 🔐 **Secrets (segredos)** - Credenciais expostas e variáveis de ambiente
- 🌐 **Rede** - Redes de containers e service mesh (camada de comunicação entre serviços)

---

## 📖 Guias de segurança de containers

| Plataforma | Descrição | Guia |
|----------|-------------|-------|
| **Docker** | Evasão de containers, análise de imagens e exploração do daemon | [📄 Ver](Docker-Pentesting.pt.md) |
| **Kubernetes** | Ataques a clusters K8s, contorno de RBAC e evasão de pods | [📄 Ver](Kubernetes-Pentesting.pt.md) |

---

## 🛠️ Ferramentas essenciais de segurança de containers

| Ferramenta | Finalidade | Plataforma |
|------|---------|----------|
| **Trivy** | Analisador de vulnerabilidades | Docker/K8s |
| **kube-hunter** | Testes de intrusão em K8s | Kubernetes |
| **kubeaudit** | Auditoria de segurança do cluster | Kubernetes |
| **Falco** | Monitorização de segurança em execução | Ambas |
| **Docker Bench** | Auditoria de segurança do Docker | Docker |
| **Dive** | Análise de camadas de imagens | Docker |
| **kubeletctl** | Exploração do kubelet | Kubernetes |
| **Peirates** | Framework de ataque a K8s | Kubernetes |

### Instalação rápida de ferramentas
```bash
# Trivy (analisador de vulnerabilidades)
brew install trivy
# ou
docker run aquasec/trivy image nginx:latest

# kube-hunter (teste de intrusão em K8s)
pip install kube-hunter
kube-hunter --remote target-cluster.com

# kubeletctl
go install github.com/cyberark/kubeletctl@latest

# Docker Bench Security
git clone https://github.com/docker/docker-bench-security.git
cd docker-bench-security && ./docker-bench-security.sh

# Dive (análise de imagens)
brew install dive
dive nginx:latest
```

---

## 📊 Vulnerabilidades comuns em containers

| Vulnerabilidade | Descrição | Impacto |
|---------------|-------------|--------|
| **Container com privilégios elevados** | Opção --privileged | Evasão de containers |
| **Socket Docker exposto** | /var/run/docker.sock montado | Acesso total ao host |
| **Montagens sensíveis** | /etc e /root montados | Fuga de dados |
| **Capabilities inseguras** | CAP_SYS_ADMIN, etc. | Escalada de privilégios |
| **Kubelet anónimo** | API do kubelet sem autenticação | Acesso a pods |
| **Dashboard K8s exposto** | Sem autenticação obrigatória | Controlo total do cluster |
| **Segredos embutidos no código** | Segredos em imagens | Roubo de credenciais |
| **Imagens base desatualizadas** | CVE conhecidas | RCE (execução remota de código) e escalada de privilégios |

---

## 🔗 Referência rápida: comandos Docker

```bash
# Informações do container
docker ps -a
docker inspect CONTAINER_ID

# Verificar as capabilities (privilégios específicos do kernel)
docker inspect --format='{{.HostConfig.Privileged}}' CONTAINER_ID
docker inspect --format='{{.HostConfig.CapAdd}}' CONTAINER_ID

# Verificar as montagens
docker inspect --format='{{.Mounts}}' CONTAINER_ID

# Análise de imagens
docker history IMAGE_NAME
docker inspect IMAGE_NAME
```

## 🔗 Referência rápida: comandos Kubernetes

```bash
# Informações do cluster
kubectl cluster-info
kubectl get nodes

# Listar os pods (todos os namespaces)
kubectl get pods --all-namespaces

# Verificar o RBAC
kubectl auth can-i --list
kubectl auth can-i create pods

# Obter secrets
kubectl get secrets --all-namespaces
kubectl get secret SECRET_NAME -o jsonpath='{.data}'
```

---

## 📚 Recursos

- [OWASP Segurança de containers](https://owasp.org/www-project-docker-security/)
- [Segurança do Kubernetes](https://kubernetes.io/docs/concepts/security/)
- [Boas práticas de segurança do Docker](https://docs.docker.com/develop/security-best-practices/)
- [HackTricks Containers](https://book.hacktricks.xyz/linux-hardening/privilege-escalation/docker-security)

---

<p align="center">
  <b>🐳 Teste a segurança dos containers com responsabilidade!</b>
</p>
