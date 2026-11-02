# 🎯 Guía rápida de búsqueda proactiva de amenazas (threat hunting)

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Threat-Hunting.tr.md) • [🇬🇷 Ελληνικά](Threat-Hunting.el.md)

> **Aclaración de los ejemplos:** Threat hunting es la búsqueda proactiva de amenazas. Excluir las rutas de servicios que contienen `Windows` no verifica firmas digitales, aunque el comentario original lo sugiera. La enumeración basada en `ps` tampoco garantiza descubrir procesos ocultos. Deben comprobarse los índices de propiedades frente al esquema de cada versión.


```
████████╗██╗  ██╗██████╗ ███████╗ █████╗ ████████╗    ██╗  ██╗██╗   ██╗███╗   ██╗████████╗██╗███╗   ██╗ ██████╗ 
╚══██╔══╝██║  ██║██╔══██╗██╔════╝██╔══██╗╚══██╔══╝    ██║  ██║██║   ██║████╗  ██║╚══██╔══╝██║████╗  ██║██╔════╝ 
   ██║   ███████║██████╔╝█████╗  ███████║   ██║       ███████║██║   ██║██╔██╗ ██║   ██║   ██║██╔██╗ ██║██║  ███╗
   ██║   ██╔══██║██╔══██╗██╔══╝  ██╔══██║   ██║       ██╔══██║██║   ██║██║╚██╗██║   ██║   ██║██║╚██╗██║██║   ██║
   ██║   ██║  ██║██║  ██║███████╗██║  ██║   ██║       ██║  ██║╚██████╔╝██║ ╚████║   ██║   ██║██║ ╚████║╚██████╔╝
   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝   ╚═╝       ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═══╝   ╚═╝   ╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 Proceso de búsqueda proactiva de amenazas

```
┌──────────────────┐     ┌──────────────────┐     ┌──────────────────┐     ┌──────────────────┐
│   1. HIPÓTESIS   │────▶│   2. RECOPILAR   │────▶│   3. ANALIZAR    │────▶│   4. RESPONDER   │
│     (Teoría)     │     │     (Datos)      │     │     (Buscar)     │     │     (Actuar)     │
└──────────────────┘     └──────────────────┘     └──────────────────┘     └──────────────────┘
                                                                                    │
                                                                                    │
                                                                                    │
                                                  ┌──────────────────┐              │
                                                  │  5. DOCUMENTAR   │◀─────────────┘
                                                  │    (Aprender)    │
                                                  └──────────────────┘
```

---

## 📋 Ejemplos de hipótesis

| Categoría | Hipótesis |
|----------|------------|
| **Acceso inicial** | Los atacantes pueden usar phishing para distribuir malware |
| **Persistencia** | Los atacantes pueden crear tareas programadas para mantener la persistencia |
| **Credenciales** | Los atacantes pueden volcar LSASS para obtener credenciales |
| **Lateral** | Los atacantes pueden usar RDP para desplazarse lateralmente |
| **Exfiltración** | Los atacantes pueden usar túneles DNS para exfiltrar datos |

---

## 🔍 Técnicas de búsqueda proactiva

### Buscar mecanismos de persistencia

#### Claves Run del Registro de Windows
```powershell
# Consultar todas las claves Run
Get-ItemProperty -Path "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" -ErrorAction SilentlyContinue
Get-ItemProperty -Path "HKCU:\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" -ErrorAction SilentlyContinue
Get-ItemProperty -Path "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\RunOnce" -ErrorAction SilentlyContinue
```

#### Tareas programadas
```powershell
# Listar todas las tareas programadas
Get-ScheduledTask | Where-Object {$_.State -ne "Disabled"} | 
    Select-Object TaskName, TaskPath, State | Format-Table

# Buscar tareas sospechosas (ajenas a Microsoft)
Get-ScheduledTask | Where-Object {$_.Author -notlike "*Microsoft*"} | 
    Select-Object TaskName, Author, State
```

#### Servicios
```powershell
# Servicios sin firma (según la descripción del original)
Get-WmiObject Win32_Service | Where-Object {$_.PathName -notlike "*Windows*"} | 
    Select-Object Name, PathName, StartMode, State
```

### Buscar movimiento lateral

#### Conexiones RDP
```powershell
# Sesiones RDP (evento 4624, tipo 10)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4624} | 
    Where-Object {$_.Properties[8].Value -eq 10} |
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='Source';E={$_.Properties[18].Value}}
```

#### Conexiones de red
```powershell
# Conexiones actuales
Get-NetTCPConnection -State Established | 
    Select-Object LocalAddress, LocalPort, RemoteAddress, RemotePort, OwningProcess | 
    Format-Table

# Con los nombres de los procesos
Get-NetTCPConnection | ForEach-Object {
    $proc = Get-Process -Id $_.OwningProcess -ErrorAction SilentlyContinue
    [PSCustomObject]@{
        LocalAddress = $_.LocalAddress
        RemoteAddress = $_.RemoteAddress
        RemotePort = $_.RemotePort
        Process = $proc.ProcessName
    }
} | Format-Table
```

### Buscar accesos a credenciales

#### Acceso a LSASS
```powershell
# Evento 10 de Sysmon: acceso a LSASS
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=10} |
    Where-Object {$_.Properties[8].Value -like "*lsass.exe"} |
    Select-Object TimeCreated, @{N='Source';E={$_.Properties[4].Value}}
```

### Buscar ejecuciones

#### PowerShell codificado
```powershell
# PowerShell con codificación
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-PowerShell/Operational';ID=4104} |
    Where-Object {$_.Message -match "FromBase64" -or $_.Message -match "-enc"} |
    Select-Object TimeCreated, Message
```

#### Relaciones sospechosas entre procesos padre e hijo
```powershell
# Office inicia cmd o PowerShell
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=1} |
    Where-Object {
        ($_.Properties[20].Value -match "WINWORD|EXCEL|OUTLOOK") -and
        ($_.Properties[4].Value -match "cmd|powershell")
    }
```

---

## 🐧 Búsqueda proactiva de amenazas en Linux

### Persistencia
```bash
# Tareas de cron
cat /etc/crontab
for user in $(cut -f1 -d: /etc/passwd); do crontab -l -u $user 2>/dev/null; done

# Claves SSH autorizadas
find / -name "authorized_keys" 2>/dev/null

# Scripts de inicio
ls -la /etc/init.d/
systemctl list-unit-files --type=service | grep enabled

# Binarios SUID
find / -perm -4000 -type f 2>/dev/null
```

### Red
```bash
# Puertos a la escucha
ss -tulnp
netstat -tulnp

# Conexiones establecidas
ss -tnp state established
lsof -i -P -n | grep ESTABLISHED

# Actividad DNS inusual
tcpdump -i any port 53 -w dns.pcap
```

### Procesos
```bash
# Todos los procesos con el comando completo
ps auxwww

# Árbol de procesos
pstree -p

# Procesos ocultos
ps aux | awk '{print $2}' | xargs -I{} ls -la /proc/{}/exe 2>/dev/null

# Binarios modificados recientemente
find /usr/bin /usr/sbin -mtime -7 2>/dev/null
```

---

## 📊 Búsqueda de indicadores de compromiso (IOC)

### Búsqueda de hashes
```powershell
# Calcular hashes
Get-FileHash -Algorithm SHA256 C:\suspicious.exe

# Buscar un hash malicioso conocido
$badHash = "abc123..."
Get-ChildItem -Path C:\ -Recurse -File | Get-FileHash -Algorithm SHA256 |
    Where-Object {$_.Hash -eq $badHash}
```

### Búsqueda de IP y dominios
```powershell
# Caché DNS
Get-DnsClientCache | Where-Object {$_.Name -like "*suspicious*"}

# Conexión con una IP maliciosa conocida
Get-NetTCPConnection | Where-Object {$_.RemoteAddress -eq "1.2.3.4"}
```

---

## 🛠️ Herramientas

| Herramienta | Finalidad |
|------|---------|
| **Velociraptor** | Búsqueda proactiva en endpoints a gran escala |
| **OSQuery** | Consultas SQL en endpoints |
| **KAPE** | Recopilación para la evaluación inicial |
| **Chainsaw** | Análisis rápido de registros de Windows |
| **DeepBlueCLI** | Búsqueda de amenazas con PowerShell |

---

## 📋 Lista rápida de comprobación de búsqueda de amenazas

```markdown
□ Comprobar los puntos de inicio automático (Registro, servicios y tareas)
□ Revisar la creación reciente de procesos (evento 4688, Sysmon 1)
□ Analizar las conexiones de red
□ Buscar comandos codificados
□ Buscar accesos a LSASS
□ Comprobar relaciones inusuales entre procesos padre e hijo
□ Revisar los registros de PowerShell
□ Buscar indicadores de movimiento lateral
```

---

## 🔗 Guías rápidas relacionadas

- [Análisis de registros](./Log-Analysis.es.md)
- [Detección con SIEM](./SIEM-Detection.es.md)
- [Reglas Sigma](./Sigma-Rules.es.md)

---

**Anterior:** [← Detección con SIEM](./SIEM-Detection.es.md)

**Siguiente:** [Reglas Sigma →](./Sigma-Rules.es.md)
