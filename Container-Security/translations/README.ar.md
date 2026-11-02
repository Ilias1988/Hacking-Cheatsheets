<a id="-container-security-cheatsheets"></a>
# 🐳 مراجع أمن الحاويات

<!-- language-home-navigation -->
[🏠 الصفحة الرئيسية العربية](../../README.ar.md) • [🌐 Languages](../../README.md#-available-languages)

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

<a id="-container-security-testing"></a>
## 🎯 اختبار أمن الحاويات

يشمل اختبار أمن الحاويات تحديد الثغرات في:
- 🐳 **Docker** - الصور والحاويات وخدمة Docker الخلفية (daemon)
- ☸️ **Kubernetes** - وحدات Pod والتحكم في الوصول بحسب الدور (RBAC) والأسرار وسياسات الشبكة
- 📦 **صور الحاويات** - الصور الأساسية والطبقات والثغرات
- 🔐 **الأسرار** - بيانات الاعتماد المكشوفة ومتغيرات البيئة
- 🌐 **الشبكة** - شبكات الحاويات وشبكة الخدمات (service mesh)

---

<a id="-container-security-guides"></a>
## 📖 أدلة أمن الحاويات

| المنصة | الوصف | الدليل |
|----------|-------------|-------|
| **Docker** | الهروب من الحاوية وتحليل الصور واستغلال الخدمة الخلفية | [📄 عرض](Docker-Pentesting.ar.md) |
| **Kubernetes** | هجمات عناقيد K8s وتجاوز RBAC والهروب من وحدات Pod | [📄 عرض](Kubernetes-Pentesting.ar.md) |

---

<a id="-essential-container-security-tools"></a>
## 🛠️ الأدوات الأساسية لأمن الحاويات

| الأداة | الغرض | المنصة |
|------|---------|----------|
| **Trivy** | ماسح للثغرات | Docker/K8s |
| **kube-hunter** | اختبار اختراق K8s | Kubernetes |
| **kubeaudit** | تدقيق أمن العنقود | Kubernetes |
| **Falco** | مراقبة الأمن أثناء التشغيل | كلاهما |
| **Docker Bench** | تدقيق أمن Docker | Docker |
| **Dive** | تحليل طبقات الصور | Docker |
| **kubeletctl** | استغلال Kubelet | Kubernetes |
| **Peirates** | إطار هجوم على K8s | Kubernetes |

<a id="quick-tool-install"></a>
### تثبيت الأدوات بسرعة
```bash
# Trivy (ماسح للثغرات)
brew install trivy
# أو
docker run aquasec/trivy image nginx:latest

# kube-hunter (اختبار اختراق K8s)
pip install kube-hunter
kube-hunter --remote target-cluster.com

# kubeletctl
go install github.com/cyberark/kubeletctl@latest

# Docker Bench Security
git clone https://github.com/docker/docker-bench-security.git
cd docker-bench-security && ./docker-bench-security.sh

# Dive (تحليل الصور)
brew install dive
dive nginx:latest
```

---

<a id="-common-container-vulnerabilities"></a>
## 📊 ثغرات شائعة في الحاويات

| الثغرة | الوصف | الأثر |
|---------------|-------------|--------|
| **حاوية ذات امتيازات موسعة** | الخيار --privileged | الهروب من الحاوية |
| **مقبس Docker مكشوف** | ربط /var/run/docker.sock داخل الحاوية | وصول كامل إلى المضيف |
| **نقاط ربط حساسة** | ربط /etc و/root داخل الحاوية | تسرّب البيانات |
| **قدرات Linux غير آمنة** | CAP_SYS_ADMIN وغيرها | رفع الامتيازات |
| **وصول مجهول إلى Kubelet** | واجهة kubelet API دون مصادقة | الوصول إلى وحدات Pod |
| **لوحة K8s مكشوفة** | لا تتطلب مصادقة | السيطرة على العنقود |
| **أسرار مضمنة في الشيفرة** | أسرار داخل الصور | سرقة بيانات الاعتماد |
| **صور أساسية قديمة** | ثغرات معروفة بمعرّفات CVE | تنفيذ شيفرة عن بُعد (RCE) ورفع الامتيازات |

---

<a id="-quick-reference-docker-commands"></a>
## 🔗 مرجع سريع: أوامر Docker

```bash
# معلومات الحاوية
docker ps -a
docker inspect CONTAINER_ID

# فحص قدرات Linux
docker inspect --format='{{.HostConfig.Privileged}}' CONTAINER_ID
docker inspect --format='{{.HostConfig.CapAdd}}' CONTAINER_ID

# فحص نقاط الربط
docker inspect --format='{{.Mounts}}' CONTAINER_ID

# تحليل الصور
docker history IMAGE_NAME
docker inspect IMAGE_NAME
```

<a id="-quick-reference-kubernetes-commands"></a>
## 🔗 مرجع سريع: أوامر Kubernetes

```bash
# معلومات العنقود
kubectl cluster-info
kubectl get nodes

# سرد وحدات Pod في جميع مساحات الأسماء
kubectl get pods --all-namespaces

# فحص RBAC
kubectl auth can-i --list
kubectl auth can-i create pods

# جلب الأسرار
kubectl get secrets --all-namespaces
kubectl get secret SECRET_NAME -o jsonpath='{.data}'
```

---

<a id="-resources"></a>
## 📚 الموارد

- [أمن الحاويات من OWASP](https://owasp.org/www-project-docker-security/)
- [أمن Kubernetes](https://kubernetes.io/docs/concepts/security/)
- [أفضل ممارسات أمن Docker](https://docs.docker.com/develop/security-best-practices/)
- [الحاويات في HackTricks](https://book.hacktricks.xyz/linux-hardening/privilege-escalation/docker-security)

---

<p align="center">
  <b>🐳 اختبر الحاويات بمسؤولية!</b>
</p>

> **مصطلحات أساسية:** الحاوية (Container) بيئة تشغيل معزولة؛ والصورة (Image) قالب طبقات الملفات المستخدم لإنشائها. العنقود (Cluster) مجموعة عقد Kubernetes، ووحدة Pod أصغر وحدة نشر فيه. RBAC هو التحكم في الوصول بحسب الدور. تشير Capabilities إلى قدرات Linux التي تقسّم بعض امتيازات root إلى صلاحيات أدق. حُفظت أسماء الأدوات والأوامر والقيم التقنية كما في الأصل.
