# 🐳 Konteyner Güvenliği Kopya Kağıdı (Container Security Cheatsheets)

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

## 🎯 Konteyner Güvenliği Testi

Konteyner güvenliği testi, aşağıdaki bileşenlerde bulunan zafiyetlerin tespit edilmesini kapsar:
- 🐳 **Docker** - İmajlar, konteynerler ve daemon servisi
- ☸️ **Kubernetes** - Pod'lar, RBAC yapılandırmaları, gizli bilgiler ve ağ politikaları
- 📦 **Konteyner İmajları** - Temel imajlar, katmanlar ve güvenlik açıkları
- 🔐 **Gizli Bilgiler (Secrets)** - İfşa olmuş kimlik bilgileri ve ortam değişkenleri
- 🌐 **Ağ (Network)** - Konteyner ağ iletişimi ve servis ağı (service mesh)

---

## 📖 Konteyner Güvenliği Kılavuzları

| Platform | Açıklama | Kılavuz |
|----------|----------|---------|
| **Docker** | Konteynerden kaçış (escape), imaj analizi ve daemon istismarı | [📄 Görüntüle](./Docker-Pentesting.tr.md) |
| **Kubernetes** | K8s küme saldırıları, RBAC atlatma ve pod'dan kaçış | [📄 Görüntüle](./Kubernetes-Pentesting.tr.md) |

---

## 🛠️ Temel Konteyner Güvenliği Araçları

| Araç | Amacı | Platform |
|------|-------|----------|
| **Trivy** | Güvenlik açığı ve gizli bilgi tarayıcısı | Docker/K8s |
| **kube-hunter** | Kubernetes sızma testi aracı | Kubernetes |
| **kubeaudit** | Küme güvenlik denetimi | Kubernetes |
| **Falco** | Çalışma zamanı güvenlik izleme | Her İkisi |
| **Docker Bench** | Docker güvenlik denetimi | Docker |
| **Dive** | İmaj katmanı analizi | Docker |
| **kubeletctl** | Kubelet sömürü aracı | Kubernetes |
| **Peirates** | Kubernetes saldırı çatısı | Kubernetes |

### Hızlı Araç Kurulumu
```bash
# Trivy (zafiyet tarayıcısı)
brew install trivy
# veya
docker run aquasec/trivy image nginx:latest

# kube-hunter (K8s sızma testi)
pip install kube-hunter
kube-hunter --remote target-cluster.com

# kubeletctl
go install github.com/cyberark/kubeletctl@latest

# Docker Bench Security
git clone https://github.com/docker/docker-bench-security.git
cd docker-bench-security && ./docker-bench-security.sh

# Dive (imaj analizi)
brew install dive
dive nginx:latest
```

---

## 📊 Yaygın Konteyner Güvenlik Açıkları

| Güvenlik Açığı | Açıklama | Etki |
|----------------|----------|------|
| **Ayrıcalıklı Konteyner (Privileged)** | --privileged bayrağı ile çalıştırma | Konteynerden kaçış (host'a sıçrama) |
| **Açık Docker Soketi** | /var/run/docker.sock dosyasının bağlanması | Tam host sistemi erişimi |
| **Hassas Dizin Bağlamaları** | /etc veya /root dizinlerinin bağlanması | Veri sızıntısı ve yetki yükseltme |
| **Güvensiz Yetenekler (Capabilities)** | CAP_SYS_ADMIN vb. izinlerin verilmesi | Yetki yükseltme |
| **Anonim Kubelet Erişimi** | Kubelet API'sinde kimlik doğrulama olmaması | Pod ve küme erişimi |
| **Açık K8s Kontrol Paneli (Dashboard)** | Kimlik doğrulama istemeyen dashboard | Tüm kümenin ele geçirilmesi |
| **Sabit Kodlanmış Gizli Bilgiler** | İmaj katmanlarında bırakılan şifreler | Kimlik bilgisi hırsızlığı |
| **Güncel Olmayan Temel İmajlar** | Bilinen CVE güvenlik açıkları | RCE ve yetki yükseltme |

---

## 🔗 Hızlı Başvuru: Docker Komutları

```bash
# Konteyner bilgisi
docker ps -a
docker inspect CONTAINER_ID

# Yetenekleri (capabilities) kontrol etme
docker inspect --format='{{.HostConfig.Privileged}}' CONTAINER_ID
docker inspect --format='{{.HostConfig.CapAdd}}' CONTAINER_ID

# Bağlı dizinleri (mounts) kontrol etme
docker inspect --format='{{.Mounts}}' CONTAINER_ID

# İmaj analizi
docker history IMAGE_NAME
docker inspect IMAGE_NAME
```

## 🔗 Hızlı Başvuru: Kubernetes Komutları

```bash
# Küme bilgisi
kubectl cluster-info
kubectl get nodes

# Pod'ları listeleme (tüm ad alanları)
kubectl get pods --all-namespaces

# RBAC izinlerini kontrol etme
kubectl auth can-i --list
kubectl auth can-i create pods

# Gizli bilgileri alma
kubectl get secrets --all-namespaces
kubectl get secret SECRET_NAME -o jsonpath='{.data}'
```

---

## 📚 Kaynaklar

- [OWASP Container Security](https://owasp.org/www-project-docker-security/)
- [Kubernetes Security](https://kubernetes.io/docs/concepts/security/)
- [Docker Security Best Practices](https://docs.docker.com/develop/security-best-practices/)
- [HackTricks Containers](https://book.hacktricks.xyz/linux-hardening/privilege-escalation/docker-security)

---

<p align="center">
  <b>🐳 Konteynerleri Sorumlu Bir Şekilde Test Edin!</b>
</p>
