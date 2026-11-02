# 🐳 Fiches pratiques de sécurité des conteneurs

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

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

---

## 🎯 Tests de sécurité des conteneurs

Les tests de sécurité des conteneurs consistent à rechercher des vulnérabilités dans les éléments suivants :
- 🐳 **Docker** - Images, conteneurs et démon Docker
- ☸️ **Kubernetes** - Pods, contrôle d’accès fondé sur les rôles (RBAC), secrets et politiques réseau
- 📦 **Images de conteneurs** - Images de base, couches et vulnérabilités
- 🔐 **Secrets** - Identifiants exposés et variables d’environnement
- 🌐 **Réseau** - Réseau des conteneurs et maillage de services (service mesh)

---

## 📖 Guides de sécurité des conteneurs

| Plateforme | Description | Guide |
|----------|-------------|-------|
| **Docker** | Évasion de conteneur, analyse d’images et exploitation du démon | [📄 Consulter](./Docker-Pentesting.fr.md) |
| **Kubernetes** | Attaques de clusters K8s, contournement du RBAC et évasion de pod | [📄 Consulter](./Kubernetes-Pentesting.fr.md) |

---

## 🛠️ Outils essentiels pour la sécurité des conteneurs

| Outil | Utilisation | Plateforme |
|------|---------|----------|
| **Trivy** | Analyseur de vulnérabilités | Docker/K8s |
| **kube-hunter** | Tests d’intrusion Kubernetes | Kubernetes |
| **kubeaudit** | Audit de sécurité du cluster | Kubernetes |
| **Falco** | Surveillance de la sécurité à l’exécution | Les deux |
| **Docker Bench** | Audit de sécurité Docker | Docker |
| **Dive** | Analyse des couches d’image | Docker |
| **kubeletctl** | Exploitation de Kubelet | Kubernetes |
| **Peirates** | Framework d’attaque Kubernetes | Kubernetes |

### Installation rapide des outils
```bash
# Trivy (analyseur de vulnérabilités)
brew install trivy
# ou
docker run aquasec/trivy image nginx:latest

# kube-hunter (test d’intrusion K8s)
pip install kube-hunter
kube-hunter --remote target-cluster.com

# kubeletctl
go install github.com/cyberark/kubeletctl@latest

# Docker Bench Security
git clone https://github.com/docker/docker-bench-security.git
cd docker-bench-security && ./docker-bench-security.sh

# Dive (analyse d’images)
brew install dive
dive nginx:latest
```

---

## 📊 Vulnérabilités courantes des conteneurs

| Vulnérabilité | Description | Conséquences |
|---------------|-------------|--------|
| **Conteneur privilégié** | Option --privileged | Évasion de conteneur |
| **Socket Docker exposé** | /var/run/docker.sock monté | Accès complet à l’hôte |
| **Montages sensibles** | /etc et /root montés | Fuite de données |
| **Capabilities dangereuses** | CAP_SYS_ADMIN, etc. | Élévation de privilèges |
| **Accès anonyme à Kubelet** | API Kubelet sans authentification | Accès aux pods |
| **Tableau de bord K8s exposé** | Aucune authentification requise | Prise de contrôle du cluster |
| **Secrets inscrits en dur** | Secrets dans les images | Vol d’identifiants |
| **Images de base obsolètes** | Vulnérabilités connues (CVE) | Exécution de code à distance et élévation de privilèges |

---

## 🔗 Référence rapide : commandes Docker

```bash
# Informations sur le conteneur
docker ps -a
docker inspect CONTAINER_ID

# Vérifier les capabilities (capacités Linux)
docker inspect --format='{{.HostConfig.Privileged}}' CONTAINER_ID
docker inspect --format='{{.HostConfig.CapAdd}}' CONTAINER_ID

# Vérifier les montages
docker inspect --format='{{.Mounts}}' CONTAINER_ID

# Analyse d’images
docker history IMAGE_NAME
docker inspect IMAGE_NAME
```

## 🔗 Référence rapide : commandes Kubernetes

```bash
# Informations sur le cluster
kubectl cluster-info
kubectl get nodes

# Lister les pods de tous les espaces de noms
kubectl get pods --all-namespaces

# Vérifier le RBAC
kubectl auth can-i --list
kubectl auth can-i create pods

# Récupérer les secrets
kubectl get secrets --all-namespaces
kubectl get secret SECRET_NAME -o jsonpath='{.data}'
```

---

## 📚 Ressources

- [OWASP Sécurité des conteneurs](https://owasp.org/www-project-docker-security/)
- [Sécurité de Kubernetes](https://kubernetes.io/docs/concepts/security/)
- [Bonnes pratiques de sécurité Docker](https://docs.docker.com/develop/security-best-practices/)
- [HackTricks Containers](https://book.hacktricks.xyz/linux-hardening/privilege-escalation/docker-security)

---

<p align="center">
  <b>🐳 Testez les conteneurs de manière responsable !</b>
</p>
