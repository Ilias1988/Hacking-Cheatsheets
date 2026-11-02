# 🔒 Guia de referência de hardening de sistemas

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Hardening.tr.md) • [🇬🇷 Ελληνικά](Hardening.el.md)

```
██╗  ██╗ █████╗ ██████╗ ██████╗ ███████╗███╗   ██╗██╗███╗   ██╗ ██████╗ 
██║  ██║██╔══██╗██╔══██╗██╔══██╗██╔════╝████╗  ██║██║████╗  ██║██╔════╝ 
███████║███████║██████╔╝██║  ██║█████╗  ██╔██╗ ██║██║██╔██╗ ██║██║  ███╗
██╔══██║██╔══██║██╔══██╗██║  ██║██╔══╝  ██║╚██╗██║██║██║╚██╗██║██║   ██║
██║  ██║██║  ██║██║  ██║██████╔╝███████╗██║ ╚████║██║██║ ╚████║╚██████╔╝
╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

> **Notas de precisão:** Hardening significa reforçar a configuração de segurança. Os exemplos misturam sintaxe de CMD, Windows PowerShell e ficheiros de configuração: em PowerShell, `sc` pode ser um alias e `%SystemRoot%` é expansão de CMD. A atribuição de `ConstrainedLanguage` afeta a sessão; a imposição de segurança exige uma política de controlo de aplicações adequada ([Microsoft](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.core/about/about_language_modes)). O Credential Guard requer configuração de VBS e pré-requisitos compatíveis; a linha de registo isolada não descreve toda a configuração ([Microsoft](https://learn.microsoft.com/en-us/windows/security/identity-protection/credential-guard/configure)). `Protocol 2` é uma diretiva histórica ausente do [OpenSSH atual](https://man.openbsd.org/sshd_config). `kernel.randomize_va_space = 2` configura ASLR, não o antigo Exec Shield ([kernel Linux](https://docs.kernel.org/admin-guide/sysctl/kernel.html#randomize-va-space)). As permissões de `/etc/shadow` devem seguir a distribuição; o exemplo `chmod 000` não é uma regra universal.

---

## 🖥️ Hardening do Windows

### Segurança das contas
```powershell
# Desativar o administrador local
net user Administrator /active:no

# Mudar o nome da conta de administrador
wmic useraccount where name='Administrator' rename 'NewAdminName'

# Definir uma política de palavras-passe robusta (GPO ou secpol.msc)
# Comprimento mínimo: 14 caracteres
# Complexidade: ativada
# Validade máxima: 60 dias

# Desativar a conta de convidado
net user Guest /active:no

# Limitar a utilização de tokens de administrador (UAC)
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System" /v EnableLUA /t REG_DWORD /d 1 /f
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System" /v ConsentPromptBehaviorAdmin /t REG_DWORD /d 2 /f
```

### Desativar serviços desnecessários
```powershell
# Desativar o Remote Registry
sc config RemoteRegistry start= disabled

# Desativar o Windows Remote Management (se não for necessário)
sc config WinRM start= disabled

# Desativar o SMBv1
Disable-WindowsOptionalFeature -Online -FeatureName SMB1Protocol -NoRestart

# Desativar o NetBIOS
Get-WmiObject Win32_NetworkAdapterConfiguration | where {$_.IPEnabled -eq $true} | 
    Invoke-WmiMethod -Name SetTcpipNetbios -ArgumentList 2
```

### Configuração da firewall
```powershell
# Ativar a Firewall do Windows
netsh advfirewall set allprofiles state on

# Bloquear ligações de entrada por predefinição
netsh advfirewall set allprofiles firewallpolicy blockinbound,allowoutbound

# Ativar o registo de eventos
netsh advfirewall set allprofiles logging filename %SystemRoot%\System32\LogFiles\Firewall\pfirewall.log
netsh advfirewall set allprofiles logging maxfilesize 4096
netsh advfirewall set allprofiles logging droppedconnections enable
```

### Segurança do PowerShell
```powershell
# Ativar o modo de linguagem restrita (Constrained Language Mode)
$ExecutionContext.SessionState.LanguageMode = "ConstrainedLanguage"

# Ativar o registo de blocos de script (Script Block Logging)
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ScriptBlockLogging" /v EnableScriptBlockLogging /t REG_DWORD /d 1 /f

# Ativar o registo de módulos (Module Logging)
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ModuleLogging" /v EnableModuleLogging /t REG_DWORD /d 1 /f
```

### Política de auditoria
```powershell
# Ativar o registo da linha de comandos na criação de processos
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System\Audit" /v ProcessCreationIncludeCmdLine_Enabled /t REG_DWORD /d 1 /f

# Configurar as políticas de auditoria
auditpol /set /category:"Logon/Logoff" /success:enable /failure:enable
auditpol /set /category:"Account Logon" /success:enable /failure:enable
auditpol /set /category:"Account Management" /success:enable /failure:enable
auditpol /set /category:"Object Access" /success:enable /failure:enable
auditpol /set /category:"Privilege Use" /success:enable /failure:enable
```

### Windows Defender
```powershell
# Ativar a proteção em tempo real
Set-MpPreference -DisableRealtimeMonitoring $false

# Ativar a proteção na cloud
Set-MpPreference -MAPSReporting Advanced
Set-MpPreference -SubmitSamplesConsent SendAllSamples

# Ativar regras de redução da superfície de ataque (ASR)
Set-MpPreference -AttackSurfaceReductionRules_Ids 56a863a9-875e-4185-98a7-b882c64b5ce5 -AttackSurfaceReductionRules_Actions Enabled
```

### Proteção do LSASS
```powershell
# Ativar o Credential Guard (o original indica que requer TPM)
reg add "HKLM\SYSTEM\CurrentControlSet\Control\LSA" /v LsaCfgFlags /t REG_DWORD /d 1 /f

# Ativar o LSASS como PPL (Protected Process Light)
reg add "HKLM\SYSTEM\CurrentControlSet\Control\LSA" /v RunAsPPL /t REG_DWORD /d 1 /f
```

---

## 🐧 Hardening do Linux

### Controlo de utilizadores e de acesso
```bash
# Desativar o início de sessão de root
sudo passwd -l root

# Definir a complexidade das palavras-passe (/etc/security/pwquality.conf)
minlen = 14
dcredit = -1
ucredit = -1
lcredit = -1
ocredit = -1

# Validade das palavras-passe (/etc/login.defs)
PASS_MAX_DAYS 90
PASS_MIN_DAYS 7
PASS_WARN_AGE 14

# Desativar contas não utilizadas
usermod -s /sbin/nologin username
```

### Hardening do SSH (/etc/ssh/sshd_config)
```bash
# Desativar o início de sessão de root
PermitRootLogin no

# Desativar a autenticação por palavra-passe (utilizar chaves)
PasswordAuthentication no
PubkeyAuthentication yes

# Mudar a porta predefinida
Port 2222

# Limitar os utilizadores
AllowUsers admin operator

# Apenas o protocolo 2
Protocol 2

# Desativar palavras-passe vazias
PermitEmptyPasswords no

# Definições de tempo limite
ClientAliveInterval 300
ClientAliveCountMax 2

# Reiniciar o SSH
sudo systemctl restart sshd
```

### Firewall (iptables/nftables)
```bash
# Política de bloqueio por predefinição
iptables -P INPUT DROP
iptables -P FORWARD DROP
iptables -P OUTPUT ACCEPT

# Permitir o tráfego de loopback
iptables -A INPUT -i lo -j ACCEPT

# Permitir ligações estabelecidas
iptables -A INPUT -m state --state ESTABLISHED,RELATED -j ACCEPT

# Permitir SSH (porta personalizada)
iptables -A INPUT -p tcp --dport 2222 -j ACCEPT

# Guardar as regras
iptables-save > /etc/iptables/rules.v4
```

### Permissões de ficheiros
```bash
# Proteger os ficheiros críticos
chmod 644 /etc/passwd
chmod 000 /etc/shadow
chmod 644 /etc/group
chmod 600 /etc/ssh/sshd_config
chmod 700 /root

# Procurar ficheiros SUID/SGID
find / -perm -4000 -type f 2>/dev/null
find / -perm -2000 -type f 2>/dev/null

# Remover o bit SUID desnecessário
chmod u-s /usr/bin/unnecessary_binary
```

### Desativar serviços não utilizados
```bash
# Listar os serviços ativados
systemctl list-unit-files --state=enabled

# Desativar serviços desnecessários
systemctl disable bluetooth
systemctl disable cups
systemctl disable avahi-daemon
systemctl disable rpcbind

# Desativar o IPv6 se não for necessário
echo "net.ipv6.conf.all.disable_ipv6 = 1" >> /etc/sysctl.conf
```

### Hardening do kernel (/etc/sysctl.conf)
```bash
# Desativar o encaminhamento IP
net.ipv4.ip_forward = 0

# Desativar o encaminhamento pela origem (source routing)
net.ipv4.conf.all.accept_source_route = 0
net.ipv4.conf.default.accept_source_route = 0

# Ativar SYN cookies
net.ipv4.tcp_syncookies = 1

# Desativar os redirecionamentos ICMP
net.ipv4.conf.all.accept_redirects = 0
net.ipv4.conf.default.accept_redirects = 0
net.ipv4.conf.all.send_redirects = 0

# Ativar a proteção indicada no original como exec shield
kernel.randomize_va_space = 2

# Aplicar as alterações
sysctl -p
```

### Sistema de auditoria (auditd)
```bash
# Instalar o auditd
apt install auditd

# Exemplos de regras (/etc/audit/rules.d/audit.rules)
-w /etc/passwd -p wa -k passwd_changes
-w /etc/shadow -p wa -k shadow_changes
-w /etc/sudoers -p wa -k sudoers_changes
-a always,exit -F arch=b64 -S execve -k command_execution

# Reiniciar o auditd
systemctl restart auditd
```

---

## 📊 Lista de verificação rápida de hardening

### Windows
```markdown
□ Desativar serviços desnecessários (SMBv1, Remote Registry)
□ Ativar a Firewall do Windows
□ Configurar uma política de palavras-passe robusta
□ Ativar o registo de eventos do PowerShell
□ Configurar a política de auditoria
□ Ativar o Windows Defender
□ Ativar a proteção do LSASS
□ Instalar atualizações de segurança
□ Remover software não utilizado
```

### Linux
```markdown
□ Desativar o início de sessão de root
□ Configurar o hardening do SSH
□ Ativar a firewall (iptables/ufw)
□ Definir permissões de ficheiros adequadas
□ Desativar serviços não utilizados
□ Configurar o hardening do kernel
□ Ativar o auditd
□ Instalar atualizações de segurança
□ Configurar o fail2ban
```

---

## 📚 Recursos

- [CIS Benchmarks](https://www.cisecurity.org/cis-benchmarks/)
- [Orientações do NIST](https://www.nist.gov/cyberframework)
- [Configurações de referência de segurança da Microsoft](https://docs.microsoft.com/en-us/windows/security/threat-protection/windows-security-baselines)

---

## 🔗 Guias de referência relacionados

- [Resposta a incidentes](Incident-Response.pt.md)
- Ainda não disponível no original

---

**Anterior:** Ainda não disponível no original

**Voltar à visão geral:** [🛡️ Blue Team](README.pt.md)
