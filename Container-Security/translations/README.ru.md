# 🐳 Шпаргалки по безопасности контейнеров

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md) • [Русский раздел](../../README.ru.md)

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

## 🎯 Тестирование безопасности контейнеров

Тестирование безопасности контейнеров включает поиск уязвимостей в следующих компонентах:
- 🐳 **Docker** - Образы, контейнеры и демон Docker
- ☸️ **Kubernetes** - Поды, RBAC (управление доступом на основе ролей), секреты и сетевые политики
- 📦 **Образы контейнеров** - Базовые образы, слои и уязвимости
- 🔐 **Секреты** - Раскрытые учётные данные и переменные окружения
- 🌐 **Сеть** - Сетевая конфигурация контейнеров и service mesh (сервисная сеть)

---

## 📖 Руководства по безопасности контейнеров

| Платформа | Описание | Руководство |
|----------|-------------|-------|
| **Docker** | Выход из контейнера, анализ образов и эксплуатация демона | [📄 Открыть](./Docker-Pentesting.ru.md) |
| **Kubernetes** | Атаки на кластеры K8s, обход RBAC и выход из пода | [📄 Открыть](./Kubernetes-Pentesting.ru.md) |

---

## 🛠️ Основные инструменты безопасности контейнеров

| Инструмент | Назначение | Платформа |
|------|---------|----------|
| **Trivy** | Сканер уязвимостей | Docker/K8s |
| **kube-hunter** | Тестирование Kubernetes на проникновение | Kubernetes |
| **kubeaudit** | Аудит безопасности кластера | Kubernetes |
| **Falco** | Мониторинг безопасности во время выполнения | Обе |
| **Docker Bench** | Аудит безопасности Docker | Docker |
| **Dive** | Анализ слоёв образа | Docker |
| **kubeletctl** | Эксплуатация kubelet | Kubernetes |
| **Peirates** | Фреймворк атак на K8s | Kubernetes |

### Быстрая установка инструментов
```bash
# Trivy (сканер уязвимостей)
brew install trivy
# или
docker run aquasec/trivy image nginx:latest

# kube-hunter (тестирование K8s на проникновение)
pip install kube-hunter
kube-hunter --remote target-cluster.com

# kubeletctl
go install github.com/cyberark/kubeletctl@latest

# Docker Bench Security
git clone https://github.com/docker/docker-bench-security.git
cd docker-bench-security && ./docker-bench-security.sh

# Dive (анализ образов)
brew install dive
dive nginx:latest
```

---

## 📊 Распространённые уязвимости контейнеров

| Уязвимость | Описание | Последствия |
|---------------|-------------|--------|
| **Привилегированный контейнер** | Флаг --privileged | Выход из контейнера |
| **Доступный сокет Docker** | Смонтирован /var/run/docker.sock | Полный доступ к хосту |
| **Монтирование конфиденциальных каталогов** | Смонтированы /etc и /root | Утечка данных |
| **Опасные capabilities** | CAP_SYS_ADMIN и другие специальные полномочия Linux | Повышение привилегий |
| **Анонимный доступ к kubelet** | Отсутствие аутентификации в API kubelet | Доступ к подам |
| **Открытая панель управления K8s** | Аутентификация не требуется | Захват кластера |
| **Секреты в исходных материалах** | Секреты в образах | Кража учётных данных |
| **Устаревшие базовые образы** | Известные уязвимости CVE | Удалённое выполнение кода (RCE), повышение привилегий |

---

## 🔗 Краткий справочник команд Docker

```bash
# Сведения о контейнере
docker ps -a
docker inspect CONTAINER_ID

# Проверить capabilities (специальные полномочия Linux)
docker inspect --format='{{.HostConfig.Privileged}}' CONTAINER_ID
docker inspect --format='{{.HostConfig.CapAdd}}' CONTAINER_ID

# Проверить точки монтирования
docker inspect --format='{{.Mounts}}' CONTAINER_ID

# Анализ образа
docker history IMAGE_NAME
docker inspect IMAGE_NAME
```

## 🔗 Краткий справочник команд Kubernetes

```bash
# Сведения о кластере
kubectl cluster-info
kubectl get nodes

# Перечислить поды во всех пространствах имён
kubectl get pods --all-namespaces

# Проверить RBAC
kubectl auth can-i --list
kubectl auth can-i create pods

# Получить секреты
kubectl get secrets --all-namespaces
kubectl get secret SECRET_NAME -o jsonpath='{.data}'
```

---

## 📚 Полезные материалы

- [OWASP Container Security](https://owasp.org/www-project-docker-security/)
- [Kubernetes Security](https://kubernetes.io/docs/concepts/security/)
- [Docker Security Best Practices](https://docs.docker.com/develop/security-best-practices/)
- [HackTricks Containers](https://book.hacktricks.xyz/linux-hardening/privilege-escalation/docker-security)

---

<p align="center">
  <b>🐳 Исследуйте безопасность контейнеров ответственно!</b>
</p>
