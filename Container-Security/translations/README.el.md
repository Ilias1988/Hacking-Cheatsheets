# 🐳 Ασφάλεια containers — Οδηγοί Docker και Kubernetes

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇮🇹 Italiano](README.it.md) • [🇹🇷 Türkçe](README.tr.md)

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

## Πριν ξεκινήσετε

Ένα **container** είναι απομονωμένο περιβάλλον εκτέλεσης εφαρμογών. Στα Linux containers η απομόνωση βασίζεται στον πυρήνα (kernel) του συστήματος φιλοξενίας, του **host**. Ένα **image** είναι το πρότυπο από το οποίο δημιουργούνται containers· αποτελείται από επίπεδα αρχείων (**layers**). Το **Docker** διαχειρίζεται images και containers, ενώ το **Kubernetes**, συχνά συντομευμένο σε **K8s**, οργανώνει εφαρμογές σε ένα **cluster**, δηλαδή ομάδα υπολογιστών.

Ξεκινήστε από τον οδηγό Docker και συνεχίστε με Kubernetes. Κάθε οδηγός εξηγεί πρώτα τους βασικούς όρους. Τα ονόματα εργαλείων, οι εντολές, οι παράμετροι και τα αναγνωριστικά ρυθμίσεων παραμένουν στα αγγλικά ώστε να μπορούν να αναζητηθούν και να χρησιμοποιηθούν σωστά. Τα γραφικά γράμματα των αρχικών τίτλων διατηρούνται όπως στο πρωτότυπο.

Τα παραδείγματα αφορούν εξουσιοδοτημένους ελέγχους ή δικό σας απομονωμένο εργαστήριο. Ορισμένες εντολές αλλάζουν αρχεία, δημιουργούν πόρους ή παρέχουν πρόσβαση στον host. Οι πίνακες περιγράφουν πιθανούς κινδύνους και όχι βέβαιη εκμετάλλευση κάθε εγκατάστασης.

## Περιεχόμενα

- [🎯 Έλεγχος ασφάλειας containers](#section-01)
- [📖 Οδηγοί ασφάλειας containers](#section-02)
- [🛠️ Βασικά εργαλεία ασφάλειας containers](#section-03)
- [📊 Συνηθισμένες αδυναμίες ασφάλειας containers](#section-04)
- [🔗 Γρήγορη αναφορά εντολών Docker](#section-05)
- [🔗 Γρήγορη αναφορά εντολών Kubernetes](#section-06)
- [📚 Πηγές και τεκμηρίωση](#section-07)

<a id="section-01"></a>

## 🎯 Έλεγχος ασφάλειας containers

Ο έλεγχος ασφάλειας containers εξετάζει αδυναμίες στις ακόλουθες περιοχές:
- 🐳 **Docker:** images, containers και υπηρεσία daemon
- ☸️ **Kubernetes:** pods, δικαιώματα RBAC, Secrets και πολιτικές δικτύου
- 📦 **Images containers:** βασικά images, επίπεδα αρχείων και γνωστές ευπάθειες
- 🔐 **Ευαίσθητα στοιχεία:** εκτεθειμένοι κωδικοί και μεταβλητές περιβάλλοντος
- 🌐 **Δίκτυο:** δικτύωση containers και μηχανισμοί διαχείρισης επικοινωνίας υπηρεσιών (service mesh)

---

<a id="section-02"></a>

## 📖 Οδηγοί ασφάλειας containers

| Πλατφόρμα | Περιγραφή | Οδηγός |
| ---------- | ------------- | ------- |
| **Docker** | Διαφυγή από container, ανάλυση images και εκμετάλλευση αδυναμιών του daemon | [📄 Προβολή](Docker-Pentesting.el.md) |
| **Kubernetes** | Έλεγχος cluster Kubernetes, αδυναμιών RBAC και διαφυγής από pods | [📄 Προβολή](Kubernetes-Pentesting.el.md) |

---

<a id="section-03"></a>

## 🛠️ Βασικά εργαλεία ασφάλειας containers

| Εργαλείο | Χρήση | Πλατφόρμα |
| ------ | --------- | ---------- |
| **Trivy** | Σάρωση για γνωστές ευπάθειες | Docker/K8s |
| **kube-hunter** | Έλεγχος διείσδυσης σε Kubernetes | Kubernetes |
| **kubeaudit** | Έλεγχος ρυθμίσεων ασφάλειας του cluster | Kubernetes |
| **Falco** | Παρακολούθηση ασφάλειας κατά την εκτέλεση | Docker και Kubernetes |
| **Docker Bench** | Έλεγχος ρυθμίσεων ασφάλειας Docker | Docker |
| **Dive** | Ανάλυση των επιπέδων ενός image | Docker |
| **kubeletctl** | Έλεγχος αδυναμιών του kubelet | Kubernetes |
| **Peirates** | Πλαίσιο εργαλείων ελέγχου Kubernetes | Kubernetes |

### Παραδείγματα εγκατάστασης εργαλείων

Το brew απαιτεί εγκατεστημένο Homebrew, ενώ pip και go απαιτούν τα αντίστοιχα περιβάλλοντα. Τα παρακάτω είναι διαφορετικά παραδείγματα και όχι ενιαίο script εγκατάστασης. Ελέγξτε τις οδηγίες κάθε εργαλείου για το δικό σας λειτουργικό και την έκδοσή του.

```bash
# Trivy: σαρωτής γνωστών ευπαθειών
brew install trivy
# ή
docker run aquasec/trivy image nginx:latest

# kube-hunter: έλεγχος διείσδυσης σε Kubernetes
pip install kube-hunter
kube-hunter --remote target-cluster.com

# kubeletctl
go install github.com/cyberark/kubeletctl@latest

# Docker Bench Security
git clone https://github.com/docker/docker-bench-security.git
cd docker-bench-security && ./docker-bench-security.sh

# Dive: ανάλυση επιπέδων image
brew install dive
dive nginx:latest
```

---

<a id="section-04"></a>

## 📊 Συνηθισμένες αδυναμίες ασφάλειας containers

Οι επιπτώσεις εξαρτώνται από τα δικαιώματα, την έκδοση και τα επιπλέον μέτρα προστασίας. Για παράδειγμα, η πρόσβαση σε socket ενός rootless daemon δεν συνεπάγεται αυτομάτως δικαιώματα root σε ολόκληρο τον host.


| Αδυναμία | Περιγραφή | Πιθανή επίπτωση |
| --------------- | ------------- | -------- |
| **Container με εκτεταμένα προνόμια** | Ενεργοποιημένη επιλογή --privileged | Διαφυγή από την απομόνωση του container |
| **Εκτεθειμένο Docker socket** | Προσαρτημένο /var/run/docker.sock | Δυνατότητα εκτεταμένης πρόσβασης στον host |
| **Προσαρτήσεις ευαίσθητων καταλόγων** | Προσαρτημένοι κατάλογοι /etc ή /root | Διαρροή δεδομένων |
| **Επικίνδυνα πρόσθετα δικαιώματα capabilities** | Δικαιώματα όπως το CAP_SYS_ADMIN | Ανύψωση δικαιωμάτων |
| **Ανώνυμη πρόσβαση στο kubelet** | Ελλιπής έλεγχος πρόσβασης στο API του kubelet | Πρόσβαση σε pods |
| **Εκτεθειμένος πίνακας διαχείρισης Kubernetes** | Πρόσβαση χωρίς ταυτοποίηση | Πιθανός έλεγχος του cluster, ανάλογα με τα δικαιώματα |
| **Ενσωματωμένα ευαίσθητα στοιχεία** | Κωδικοί ή άλλα ευαίσθητα στοιχεία μέσα σε images | Κλοπή στοιχείων σύνδεσης |
| **Παρωχημένα βασικά images** | Γνωστές ευπάθειες με αναγνωριστικό CVE | Απομακρυσμένη εκτέλεση κώδικα ή ανύψωση δικαιωμάτων |

---

<a id="section-05"></a>

## 🔗 Γρήγορη αναφορά εντολών Docker

```bash
# Πληροφορίες για τα containers
docker ps -a
docker inspect CONTAINER_ID

# Έλεγχος πρόσθετων δικαιωμάτων (capabilities)
docker inspect --format='{{.HostConfig.Privileged}}' CONTAINER_ID
docker inspect --format='{{.HostConfig.CapAdd}}' CONTAINER_ID

# Έλεγχος προσαρτήσεων καταλόγων και τόμων
docker inspect --format='{{.Mounts}}' CONTAINER_ID

# Ανάλυση image
docker history IMAGE_NAME
docker inspect IMAGE_NAME
```

<a id="section-06"></a>

## 🔗 Γρήγορη αναφορά εντολών Kubernetes

```bash
# Πληροφορίες για το cluster
kubectl cluster-info
kubectl get nodes

# Καταγραφή pods σε όλα τα namespaces
kubectl get pods --all-namespaces

# Έλεγχος δικαιωμάτων RBAC
kubectl auth can-i --list
kubectl auth can-i create pods

# Ανάγνωση Secrets με τα διαθέσιμα δικαιώματα
kubectl get secrets --all-namespaces
kubectl get secret SECRET_NAME -o jsonpath='{.data}'
```

---

<a id="section-07"></a>

## 📚 Πηγές και τεκμηρίωση

- [Ασφάλεια containers — OWASP](https://owasp.org/www-project-docker-security/)
- [Τεκμηρίωση ασφάλειας Kubernetes](https://kubernetes.io/docs/concepts/security/)
- [Καλές πρακτικές ασφάλειας Docker](https://docs.docker.com/develop/security-best-practices/)
- [Οδηγός containers — HackTricks](https://book.hacktricks.xyz/linux-hardening/privilege-escalation/docker-security)

---

<p align="center">
  <b>🐳 Μελετήστε υπεύθυνα την ασφάλεια containers.</b>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
