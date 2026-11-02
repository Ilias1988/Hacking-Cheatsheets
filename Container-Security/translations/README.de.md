# 🐳 Spickzettel zur Container-Sicherheit

<!-- language-home-navigation -->
[🏠 Deutsche Startseite](../../README.de.md) • [🌐 Languages](../../README.md#-available-languages)

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

## 🎯 Sicherheitstests für Container

Bei Sicherheitstests für Container werden Schwachstellen in folgenden Bereichen ermittelt:
- 🐳 **Docker** – Images, Container und Daemon
- ☸️ **Kubernetes** – Pods, rollenbasierte Zugriffskontrolle (RBAC), Secrets und Netzwerkrichtlinien
- 📦 **Container-Images** – Basis-Images, Schichten und Schwachstellen
- 🔐 **Secrets** – offengelegte Zugangsdaten und Umgebungsvariablen
- 🌐 **Netzwerk** – Container-Netzwerke und Service Mesh

---

## 📖 Leitfäden zur Container-Sicherheit

| Plattform | Beschreibung | Leitfaden |
|----------|-------------|-------|
| **Docker** | Container-Ausbruch, Image-Analyse und Ausnutzung von Daemon-Schwachstellen | [📄 Ansehen](./Docker-Pentesting.de.md) |
| **Kubernetes** | Angriffe auf K8s-Cluster, Umgehung von RBAC und Pod-Ausbruch | [📄 Ansehen](./Kubernetes-Pentesting.de.md) |

---

## 🛠️ Wichtige Werkzeuge für Container-Sicherheit

| Tool | Zweck | Plattform |
|------|---------|----------|
| **Trivy** | Schwachstellenscanner | Docker/K8s |
| **kube-hunter** | K8s-Penetrationstests | Kubernetes |
| **kubeaudit** | Sicherheitsprüfung des Clusters | Kubernetes |
| **Falco** | Sicherheitsüberwachung zur Laufzeit | Beide |
| **Docker Bench** | Sicherheitsprüfung von Docker | Docker |
| **Dive** | Analyse von Image-Schichten | Docker |
| **kubeletctl** | Ausnutzung von Kubelet-Schwachstellen | Kubernetes |
| **Peirates** | Framework für K8s-Angriffe | Kubernetes |

### Werkzeuge schnell installieren
```bash
# Trivy (Schwachstellenscanner)
brew install trivy
# oder
docker run aquasec/trivy image nginx:latest

# kube-hunter (K8s-Penetrationstest)
pip install kube-hunter
kube-hunter --remote target-cluster.com

# kubeletctl
go install github.com/cyberark/kubeletctl@latest

# Docker Bench Security
git clone https://github.com/docker/docker-bench-security.git
cd docker-bench-security && ./docker-bench-security.sh

# Dive (Image-Analyse)
brew install dive
dive nginx:latest
```

---

## 📊 Häufige Container-Schwachstellen

| Schwachstelle | Beschreibung | Auswirkung |
|---------------|-------------|--------|
| **Privilegierter Container** | Schalter --privileged | Container-Ausbruch |
| **Offengelegter Docker-Socket** | /var/run/docker.sock eingebunden | Vollständiger Zugriff auf den Host |
| **Sensible Einbindungen (Mounts)** | /etc und /root eingebunden | Datenabfluss |
| **Unsichere Capabilities** | CAP_SYS_ADMIN usw. | Rechteausweitung |
| **Anonymer Kubelet-Zugriff** | Keine Authentifizierung an der Kubelet-API | Zugriff auf Pods |
| **Offengelegtes K8s-Dashboard** | Keine Authentifizierung erforderlich | Übernahme des Clusters |
| **Fest im Code hinterlegte Secrets** | Secrets in Images | Diebstahl von Zugangsdaten |
| **Veraltete Basis-Images** | Bekannte CVEs | Codeausführung aus der Ferne (RCE), Rechteausweitung |

---

## 🔗 Kurzreferenz: Docker-Befehle

```bash
# Container-Informationen
docker ps -a
docker inspect CONTAINER_ID

# Capabilities prüfen
docker inspect --format='{{.HostConfig.Privileged}}' CONTAINER_ID
docker inspect --format='{{.HostConfig.CapAdd}}' CONTAINER_ID

# Einbindungen prüfen
docker inspect --format='{{.Mounts}}' CONTAINER_ID

# Image-Analyse
docker history IMAGE_NAME
docker inspect IMAGE_NAME
```

## 🔗 Kurzreferenz: Kubernetes-Befehle

```bash
# Cluster-Informationen
kubectl cluster-info
kubectl get nodes

# Pods auflisten (alle Namespaces)
kubectl get pods --all-namespaces

# RBAC prüfen
kubectl auth can-i --list
kubectl auth can-i create pods

# Secrets abrufen
kubectl get secrets --all-namespaces
kubectl get secret SECRET_NAME -o jsonpath='{.data}'
```

---

## 📚 Ressourcen

- [OWASP Container-Sicherheit](https://owasp.org/www-project-docker-security/)
- [Kubernetes Security](https://kubernetes.io/docs/concepts/security/)
- [Bewährte Sicherheitspraktiken für Docker](https://docs.docker.com/develop/security-best-practices/)
- [HackTricks Containers](https://book.hacktricks.xyz/linux-hardening/privilege-escalation/docker-security)

---

<p align="center">
  <b>🐳 Teste Container verantwortungsvoll!</b>
</p>

---

## Hinweise zur deutschen Ausgabe

> **Begriffe:** Ein *Container Escape* ist der Ausbruch aus der Container-Isolation. *Capabilities* sind einzeln vergebene Linux-Rechte. *RBAC* bezeichnet rollenbasierte Zugriffskontrolle; *Secrets* enthalten vertrauliche Werte. Eine einzelne unsichere Einstellung garantiert keinen erfolgreichen Ausbruch: Laufzeit, Kernel, Benutzer-Namensräume und zusätzliche Richtlinien beeinflussen die Wirkung. Die Werkzeuge und Installationsbeispiele bleiben wie im Original; ihre Syntax kann sich mit der Version ändern.

> **Einordnung:** Das Fehlen einer Anmeldung am Dashboard oder an Kubelet ist nicht mit unbeschränkten Rechten gleichzusetzen. Authentifizierung, Autorisierung und Admission-Kontrollen sind getrennte Schutzschichten. [Kubernetes: API-Zugriff kontrollieren](https://kubernetes.io/docs/concepts/security/controlling-access/).
