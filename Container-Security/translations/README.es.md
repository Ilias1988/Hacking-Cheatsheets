# 🐳 Guías rápidas de seguridad de contenedores

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

> **Terminología:** Un contenedor aísla procesos; una imagen contiene sus capas y sistema de archivos; un pod es la unidad de despliegue de Kubernetes. RBAC significa control de acceso basado en roles; secrets son objetos que almacenan datos sensibles y capabilities son capacidades del kernel. Los puertos y configuraciones de esta guía son ejemplos y dependen del entorno.


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

## 🎯 Pruebas de seguridad de contenedores

Las pruebas de seguridad de contenedores consisten en identificar vulnerabilidades en:
- 🐳 **Docker** - Imágenes, contenedores y daemon (servicio en segundo plano)
- ☸️ **Kubernetes** - Pods, RBAC, secretos y políticas de red
- 📦 **Imágenes de contenedor** - Imágenes base, capas y vulnerabilidades
- 🔐 **Secretos (Secrets)** - Credenciales expuestas y variables de entorno
- 🌐 **Red** - Redes de contenedores y malla de servicios (service mesh)

---

## 📖 Guías de seguridad de contenedores

| Plataforma | Descripción | Guía ||
|----------|-------------|-------|
| **Docker** | Escape de contenedores, análisis de imágenes y explotación del daemon | [📄 Ver](./Docker-Pentesting.es.md) |
| **Kubernetes** | Ataques a clústeres K8s, evasión de RBAC y escape de pods | [📄 Ver](./Kubernetes-Pentesting.es.md) |

---

## 🛠️ Herramientas esenciales de seguridad de contenedores

| Herramienta | Finalidad | Plataforma |
|------|---------|----------|
| **Trivy** | Escáner de vulnerabilidades | Docker/K8s |
| **kube-hunter** | Pruebas de penetración de K8s | Kubernetes |
| **kubeaudit** | Auditoría de seguridad de clústeres | Kubernetes |
| **Falco** | Supervisión de seguridad en tiempo de ejecución | Ambas |
| **Docker Bench** | Auditoría de seguridad de Docker | Docker |
| **Dive** | Análisis de capas de imágenes | Docker |
| **kubeletctl** | Explotación de kubelet | Kubernetes |
| **Peirates** | Framework de ataque a K8s | Kubernetes |

### Instalación rápida de herramientas
```bash
# Trivy (escáner de vulnerabilidades)
brew install trivy
# o
docker run aquasec/trivy image nginx:latest

# kube-hunter (K8s pentest)
pip install kube-hunter
kube-hunter --remote target-cluster.com

# kubeletctl
go install github.com/cyberark/kubeletctl@latest

# Docker Bench Security
git clone https://github.com/docker/docker-bench-security.git
cd docker-bench-security && ./docker-bench-security.sh

# Dive (análisis de imágenes)
brew install dive
dive nginx:latest
```

---

## 📊 Vulnerabilidades habituales de contenedores

| Vulnerabilidad | Descripción | Impacto |
|---------------|-------------|--------|
| **Contenedor privilegiado** | Opción --privileged | Escape del contenedor |
| **Socket de Docker expuesto** | /var/run/docker.sock montado | Acceso completo al host |
| **Montajes sensibles** | /etc y /root montados | Filtración de datos |
| **Capabilities inseguras** | CAP_SYS_ADMIN, etc. | Elevación de privilegios |
| **Kubelet anónimo** | API de kubelet sin autenticación | Acceso a pods |
| **Panel de K8s expuesto** | No requiere autenticación | Toma de control del clúster |
| **Secretos incrustados en el código** | Secretos en las imágenes | Robo de credenciales |
| **Imágenes base desactualizadas** | CVE conocidas | Ejecución remota de código (RCE), elevación de privilegios |

---

## 🔗 Referencia rápida: comandos de Docker

```bash
# Información del contenedor
docker ps -a
docker inspect CONTAINER_ID

# Comprobar las capabilities (capacidades del kernel)
docker inspect --format='{{.HostConfig.Privileged}}' CONTAINER_ID
docker inspect --format='{{.HostConfig.CapAdd}}' CONTAINER_ID

# Comprobar los montajes
docker inspect --format='{{.Mounts}}' CONTAINER_ID

# Análisis de imágenes
docker history IMAGE_NAME
docker inspect IMAGE_NAME
```

## 🔗 Referencia rápida: comandos de Kubernetes

```bash
# Información del clúster
kubectl cluster-info
kubectl get nodes

# Listar pods (todos los namespaces)
kubectl get pods --all-namespaces

# Comprobar RBAC (control de acceso basado en roles)
kubectl auth can-i --list
kubectl auth can-i create pods

# Obtener secretos
kubectl get secrets --all-namespaces
kubectl get secret SECRET_NAME -o jsonpath='{.data}'
```

---

## 📚 Recursos

- [Seguridad de contenedores de OWASP](https://owasp.org/www-project-docker-security/)
- [Seguridad de Kubernetes](https://kubernetes.io/docs/concepts/security/)
- [Buenas prácticas de seguridad de Docker](https://docs.docker.com/develop/security-best-practices/)
- [Contenedores en HackTricks](https://book.hacktricks.xyz/linux-hardening/privilege-escalation/docker-security)

---

<p align="center">
  <b>🐳 ¡Realiza pruebas de contenedores con responsabilidad!</b>
</p>
