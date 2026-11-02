# 🐳 Hurtigreferanse for containersikkerhet

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 🎯 Sikkerhetstesting av containere

Sikkerhetstesting av containere går ut på å finne sårbarheter i:
- 🐳 **Docker** – containeravbildninger, containere og daemon (bakgrunnstjeneste)
- ☸️ **Kubernetes** – poder, rollebasert tilgangsstyring (RBAC), hemmeligheter og nettverkspolicyer
- 📦 **Containeravbildninger (images)** – basisavbildninger, lag og sårbarheter
- 🔐 **Hemmeligheter (secrets)** – eksponerte påloggingsopplysninger og miljøvariabler
- 🌐 **Nettverk** – containernettverk og service mesh (kommunikasjonslag mellom tjenester)

---

## 📖 Veiledninger i containersikkerhet

> **Begreper:** Containerflukt (container escape) betyr å bryte isolasjonen mellom container og vert. En pod er Kubernetes-enheten som kjører én eller flere containere. Capabilities deler opp Linux-rettigheter; et service mesh styrer kommunikasjon mellom tjenester.


| Plattform | Beskrivelse | Veiledning |
|----------|-------------|-------|
| **Docker** | Containerflukt, analyse av avbildninger og utnyttelse av daemon | [📄 Les](./Docker-Pentesting.nb.md) |
| **Kubernetes** | Angrep mot K8s-klynger, omgåelse av RBAC og flukt fra poder | [📄 Les](./Kubernetes-Pentesting.nb.md) |

---

## 🛠️ Viktige verktøy for containersikkerhet

| Verktøy | Formål | Plattform |
|------|---------|----------|
| **Trivy** | Sårbarhetsskanner | Docker/K8s |
| **kube-hunter** | Penetrasjonstesting av K8s | Kubernetes |
| **kubeaudit** | Sikkerhetsrevisjon av klynger | Kubernetes |
| **Falco** | Sikkerhetsovervåking under kjøring | Begge |
| **Docker Bench** | Sikkerhetsrevisjon av Docker | Docker |
| **Dive** | Analyse av lag i avbildninger | Docker |
| **kubeletctl** | Utnyttelse av Kubelet | Kubernetes |
| **Peirates** | Angrepsrammeverk for K8s | Kubernetes |

### Rask installasjon av verktøy
```bash
# Trivy (sårbarhetsskanner)
brew install trivy
# eller
docker run aquasec/trivy image nginx:latest

# kube-hunter (penetrasjonstesting av K8s)
pip install kube-hunter
kube-hunter --remote target-cluster.com

# kubeletctl
go install github.com/cyberark/kubeletctl@latest

# Docker Bench Security
git clone https://github.com/docker/docker-bench-security.git
cd docker-bench-security && ./docker-bench-security.sh

# Dive (analyse av avbildninger)
brew install dive
dive nginx:latest
```

---

## 📊 Vanlige sårbarheter i containere

| Sårbarhet | Beskrivelse | Konsekvens |
|---------------|-------------|--------|
| **Privilegert container** | Flagget --privileged | Containerflukt |
| **Eksponert Docker-socket** | /var/run/docker.sock er montert | Full tilgang til verten |
| **Sensitive monteringer** | /etc og /root er montert | Datalekkasje |
| **Usikre capabilities** | CAP_SYS_ADMIN osv. | Rettighetseskalering |
| **Anonym Kubelet-tilgang** | Ingen autentisering på Kubelet-API-et | Tilgang til poder |
| **Eksponert K8s-dashbord** | Ingen autentisering kreves | Overtakelse av klyngen |
| **Hardkodede hemmeligheter** | Hemmeligheter i avbildninger | Tyveri av påloggingsopplysninger |
| **Utdaterte basisavbildninger** | Kjente CVE-er | Ekstern kodekjøring (RCE), rettighetseskalering |

---

## 🔗 Hurtigreferanse: Docker-kommandoer

```bash
# Containerinformasjon
docker ps -a
docker inspect CONTAINER_ID

# Kontroller capabilities
docker inspect --format='{{.HostConfig.Privileged}}' CONTAINER_ID
docker inspect --format='{{.HostConfig.CapAdd}}' CONTAINER_ID

# Kontroller monteringer
docker inspect --format='{{.Mounts}}' CONTAINER_ID

# Analyse av avbildninger
docker history IMAGE_NAME
docker inspect IMAGE_NAME
```

## 🔗 Hurtigreferanse: Kubernetes-kommandoer

```bash
# Klyngeinformasjon
kubectl cluster-info
kubectl get nodes

# List poder (alle navnerom)
kubectl get pods --all-namespaces

# Kontroller RBAC
kubectl auth can-i --list
kubectl auth can-i create pods

# Hent hemmeligheter
kubectl get secrets --all-namespaces
kubectl get secret SECRET_NAME -o jsonpath='{.data}'
```

---

## 📚 Ressurser

- [OWASP om containersikkerhet](https://owasp.org/www-project-docker-security/)
- [Sikkerhet i Kubernetes](https://kubernetes.io/docs/concepts/security/)
- [Anbefalte sikkerhetstiltak for Docker](https://docs.docker.com/develop/security-best-practices/)
- [HackTricks Containers](https://book.hacktricks.xyz/linux-hardening/privilege-escalation/docker-security)

---

<p align="center">
  <b>🐳 Test containere på en ansvarlig måte!</b>
</p>
