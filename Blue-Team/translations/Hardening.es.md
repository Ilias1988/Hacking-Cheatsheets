# 🔒 Guía rápida de hardening del sistema

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Hardening.tr.md) • [🇬🇷 Ελληνικά](Hardening.el.md)

> **Aclaraciones sobre los ejemplos:** El modo `ConstrainedLanguage` configurado solo en la sesión no establece una restricción permanente; su aplicación de seguridad requiere control de aplicaciones. Véase [Microsoft: modos de lenguaje](https://learn.microsoft.com/en-us/powershell/module/microsoft.powershell.core/about/about_language_modes). `kernel.randomize_va_space` configura ASLR, aunque el original lo llama exec shield. `sc` debe interpretarse como la utilidad de control de servicios; en Windows PowerShell puede resolverse como otro alias. Los comandos y políticas heredados dependen de la versión del sistema.


```
██╗  ██╗ █████╗ ██████╗ ██████╗ ███████╗███╗   ██╗██╗███╗   ██╗ ██████╗ 
██║  ██║██╔══██╗██╔══██╗██╔══██╗██╔════╝████╗  ██║██║████╗  ██║██╔════╝ 
███████║███████║██████╔╝██║  ██║█████╗  ██╔██╗ ██║██║██╔██╗ ██║██║  ███╗
██╔══██║██╔══██║██╔══██╗██║  ██║██╔══╝  ██║╚██╗██║██║██║╚██╗██║██║   ██║
██║  ██║██║  ██║██║  ██║██████╔╝███████╗██║ ╚████║██║██║ ╚████║╚██████╔╝
╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝ ╚══════╝╚═╝  ╚═══╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🖥️ Hardening de Windows

### Seguridad de las cuentas
```powershell
# Desactivar el administrador local
net user Administrator /active:no

# Cambiar el nombre de la cuenta de administrador
wmic useraccount where name='Administrator' rename 'NewAdminName'

# Establecer una política de contraseñas robustas (GPO o secpol.msc)
# Longitud mínima: 14 caracteres
# Complejidad: activada
# Antigüedad máxima: 60 días

# Desactivar la cuenta de invitado
net user Guest /active:no

# Limitar el uso del token de administrador (UAC)
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System" /v EnableLUA /t REG_DWORD /d 1 /f
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System" /v ConsentPromptBehaviorAdmin /t REG_DWORD /d 2 /f
```

### Desactivar los servicios innecesarios
```powershell
# Desactivar Remote Registry
sc config RemoteRegistry start= disabled

# Desactivar Windows Remote Management (si no es necesario)
sc config WinRM start= disabled

# Desactivar SMBv1
Disable-WindowsOptionalFeature -Online -FeatureName SMB1Protocol -NoRestart

# Desactivar NetBIOS
Get-WmiObject Win32_NetworkAdapterConfiguration | where {$_.IPEnabled -eq $true} | 
    Invoke-WmiMethod -Name SetTcpipNetbios -ArgumentList 2
```

### Configuración del cortafuegos
```powershell
# Activar el Firewall de Windows
netsh advfirewall set allprofiles state on

# Bloquear las conexiones entrantes de forma predeterminada
netsh advfirewall set allprofiles firewallpolicy blockinbound,allowoutbound

# Activar el registro de eventos
netsh advfirewall set allprofiles logging filename %SystemRoot%\System32\LogFiles\Firewall\pfirewall.log
netsh advfirewall set allprofiles logging maxfilesize 4096
netsh advfirewall set allprofiles logging droppedconnections enable
```

### Seguridad de PowerShell
```powershell
# Activar el modo de lenguaje restringido (Constrained Language Mode)
$ExecutionContext.SessionState.LanguageMode = "ConstrainedLanguage"

# Activar el registro de bloques de script
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ScriptBlockLogging" /v EnableScriptBlockLogging /t REG_DWORD /d 1 /f

# Activar el registro de módulos
reg add "HKLM\SOFTWARE\Policies\Microsoft\Windows\PowerShell\ModuleLogging" /v EnableModuleLogging /t REG_DWORD /d 1 /f
```

### Política de auditoría
```powershell
# Activar el registro de la línea de comandos al crear procesos
reg add "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Policies\System\Audit" /v ProcessCreationIncludeCmdLine_Enabled /t REG_DWORD /d 1 /f

# Configurar las políticas de auditoría
auditpol /set /category:"Logon/Logoff" /success:enable /failure:enable
auditpol /set /category:"Account Logon" /success:enable /failure:enable
auditpol /set /category:"Account Management" /success:enable /failure:enable
auditpol /set /category:"Object Access" /success:enable /failure:enable
auditpol /set /category:"Privilege Use" /success:enable /failure:enable
```

### Windows Defender
```powershell
# Activar la protección en tiempo real
Set-MpPreference -DisableRealtimeMonitoring $false

# Activar la protección en la nube
Set-MpPreference -MAPSReporting Advanced
Set-MpPreference -SubmitSamplesConsent SendAllSamples

# Activar las reglas de reducción de la superficie de ataque
Set-MpPreference -AttackSurfaceReductionRules_Ids 56a863a9-875e-4185-98a7-b882c64b5ce5 -AttackSurfaceReductionRules_Actions Enabled
```

### Protección de LSASS
```powershell
# Activar Credential Guard (el original indica que requiere TPM)
reg add "HKLM\SYSTEM\CurrentControlSet\Control\LSA" /v LsaCfgFlags /t REG_DWORD /d 1 /f

# Activar LSASS como PPL (Protected Process Light, proceso protegido ligero)
reg add "HKLM\SYSTEM\CurrentControlSet\Control\LSA" /v RunAsPPL /t REG_DWORD /d 1 /f
```

---

## 🐧 Hardening de Linux

### Control de usuarios y acceso
```bash
# Desactivar el inicio de sesión de root
sudo passwd -l root

# Establecer la complejidad de las contraseñas (/etc/security/pwquality.conf)
minlen = 14
dcredit = -1
ucredit = -1
lcredit = -1
ocredit = -1

# Caducidad de las contraseñas (/etc/login.defs)
PASS_MAX_DAYS 90
PASS_MIN_DAYS 7
PASS_WARN_AGE 14

# Desactivar las cuentas que no se utilizan
usermod -s /sbin/nologin username
```

### Hardening de SSH (/etc/ssh/sshd_config)
```bash
# Desactivar el inicio de sesión de root
PermitRootLogin no

# Desactivar la autenticación por contraseña (usar claves)
PasswordAuthentication no
PubkeyAuthentication yes

# Cambiar el puerto predeterminado
Port 2222

# Limitar los usuarios
AllowUsers admin operator

# Solo protocolo 2
Protocol 2

# Desactivar las contraseñas vacías
PermitEmptyPasswords no

# Configuración de los tiempos de espera
ClientAliveInterval 300
ClientAliveCountMax 2

# Reiniciar SSH
sudo systemctl restart sshd
```

### Cortafuegos (iptables/nftables)
```bash
# Política predeterminada de denegación
iptables -P INPUT DROP
iptables -P FORWARD DROP
iptables -P OUTPUT ACCEPT

# Permitir la interfaz de loopback
iptables -A INPUT -i lo -j ACCEPT

# Permitir las conexiones establecidas
iptables -A INPUT -m state --state ESTABLISHED,RELATED -j ACCEPT

# Permitir SSH (puerto personalizado)
iptables -A INPUT -p tcp --dport 2222 -j ACCEPT

# Guardar las reglas
iptables-save > /etc/iptables/rules.v4
```

### Permisos de archivos
```bash
# Proteger los archivos críticos
chmod 644 /etc/passwd
chmod 000 /etc/shadow
chmod 644 /etc/group
chmod 600 /etc/ssh/sshd_config
chmod 700 /root

# Buscar archivos SUID/SGID
find / -perm -4000 -type f 2>/dev/null
find / -perm -2000 -type f 2>/dev/null

# Eliminar el bit SUID innecesario
chmod u-s /usr/bin/unnecessary_binary
```

### Desactivar los servicios que no se utilizan
```bash
# Listar los servicios habilitados
systemctl list-unit-files --state=enabled

# Desactivar los servicios innecesarios
systemctl disable bluetooth
systemctl disable cups
systemctl disable avahi-daemon
systemctl disable rpcbind

# Desactivar IPv6 si no es necesario
echo "net.ipv6.conf.all.disable_ipv6 = 1" >> /etc/sysctl.conf
```

### Hardening del kernel (/etc/sysctl.conf)
```bash
# Desactivar el reenvío IP
net.ipv4.ip_forward = 0

# Desactivar el enrutamiento de origen
net.ipv4.conf.all.accept_source_route = 0
net.ipv4.conf.default.accept_source_route = 0

# Activar SYN cookies
net.ipv4.tcp_syncookies = 1

# Desactivar las redirecciones ICMP
net.ipv4.conf.all.accept_redirects = 0
net.ipv4.conf.default.accept_redirects = 0
net.ipv4.conf.all.send_redirects = 0

# Activar exec shield (denominación del original)
kernel.randomize_va_space = 2

# Aplicar los cambios
sysctl -p
```

### Sistema de auditoría (auditd)
```bash
# Instalar auditd
apt install auditd

# Reglas de ejemplo (/etc/audit/rules.d/audit.rules)
-w /etc/passwd -p wa -k passwd_changes
-w /etc/shadow -p wa -k shadow_changes
-w /etc/sudoers -p wa -k sudoers_changes
-a always,exit -F arch=b64 -S execve -k command_execution

# Reiniciar auditd
systemctl restart auditd
```

---

## 📊 Lista rápida de comprobación del hardening

### Windows
```markdown
□ Desactivar los servicios innecesarios (SMBv1, Remote Registry)
□ Activar el Firewall de Windows
□ Configurar una política de contraseñas robustas
□ Activar el registro de PowerShell
□ Configurar la política de auditoría
□ Activar Windows Defender
□ Activar la protección de LSASS
□ Instalar las actualizaciones de seguridad
□ Eliminar el software que no se utiliza
```

### Linux
```markdown
□ Desactivar el inicio de sesión de root
□ Configurar el hardening de SSH
□ Activar el cortafuegos (iptables/ufw)
□ Establecer los permisos de archivo adecuados
□ Desactivar los servicios que no se utilizan
□ Configurar el hardening del kernel
□ Activar auditd
□ Instalar las actualizaciones de seguridad
□ Configurar fail2ban
```

---

## 📚 Recursos

- [Referencias de configuración de CIS](https://www.cisecurity.org/cis-benchmarks/)
- [Directrices del NIST](https://www.nist.gov/cyberframework)
- [Configuraciones de seguridad de referencia de Microsoft](https://docs.microsoft.com/en-us/windows/security/threat-protection/windows-security-baselines)

---

## 🔗 Guías rápidas relacionadas

- [Respuesta a incidentes](./Incident-Response.es.md)
- Defensa de red (guía ausente en el original)

---

**Anterior:** ← Defensa de red (guía ausente en el original)

**Volver al índice:** [🛡️ Blue Team](./README.es.md)
