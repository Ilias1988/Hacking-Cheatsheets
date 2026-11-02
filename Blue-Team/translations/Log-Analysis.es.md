# 📊 Guía rápida de análisis de registros

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Log-Analysis.tr.md) • [🇬🇷 Ελληνικά](Log-Analysis.el.md)

> **Nota sobre los ejemplos:** Los índices numéricos de `Properties` dependen del esquema y la versión del evento; deben contrastarse con sus campos. Las rutas de Linux y los patrones de `awk` dependen de la distribución y del formato del registro. Un evento aislado no demuestra por sí solo una intrusión.


```
  ██╗      ██████╗  ██████╗      █████╗ ███╗   ██╗ █████╗ ██╗  ██╗   ██╗███████╗██╗███████╗
  ██║     ██╔═══██╗██╔════╝     ██╔══██╗████╗  ██║██╔══██╗██║  ╚██╗ ██╔╝██╔════╝██║██╔════╝
  ██║     ██║   ██║██║  ███╗    ███████║██╔██╗ ██║███████║██║   ╚████╔╝ ███████╗██║███████╗
  ██║     ██║   ██║██║   ██║    ██╔══██║██║╚██╗██║██╔══██║██║    ╚██╔╝  ╚════██║██║╚════██║
  ███████╗╚██████╔╝╚██████╔╝    ██║  ██║██║ ╚████║██║  ██║███████╗██║   ███████║██║███████║
  ╚══════╝ ╚═════╝  ╚═════╝     ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚══════╝╚═╝   ╚══════╝╚═╝╚══════╝
```

---

## 🖥️ Registros de eventos de Windows

### Ubicación de los registros
```
# Registro de seguridad
C:\Windows\System32\winevt\Logs\Security.evtx

# Registro del sistema
C:\Windows\System32\winevt\Logs\System.evtx

# Registro de aplicaciones
C:\Windows\System32\winevt\Logs\Application.evtx

# Registros de PowerShell
C:\Windows\System32\winevt\Logs\Microsoft-Windows-PowerShell%4Operational.evtx
C:\Windows\System32\winevt\Logs\Windows PowerShell.evtx

# Sysmon (si está instalado)
C:\Windows\System32\winevt\Logs\Microsoft-Windows-Sysmon%4Operational.evtx
```

### Identificadores de eventos de seguridad críticos

#### Eventos de autenticación
| ID de evento | Descripción | Importancia |
|----------|-------------|------------|
| **4624** | Inicio de sesión correcto | Seguimiento del acceso de usuarios |
| **4625** | Inicio de sesión fallido | Detección de fuerza bruta |
| **4634** | Cierre de sesión | Seguimiento de sesiones |
| **4647** | Cierre de sesión iniciado por el usuario | Seguimiento de sesiones |
| **4648** | Inicio de sesión con credenciales explícitas | Movimiento lateral |
| **4672** | Asignación de privilegios especiales | Actividad de administración |
| **4768** | Solicitud de TGT Kerberos | Autenticación de AD |
| **4769** | Ticket de servicio Kerberos | Acceso a servicios |
| **4771** | Error de preautenticación Kerberos | Password spraying (prueba de pocas contraseñas en muchas cuentas) |
| **4776** | Autenticación NTLM | Validación de credenciales |

#### Tipos de inicio de sesión (para 4624/4625)
| Tipo | Descripción |
|------|-------------|
| 2 | Interactivo (local) |
| 3 | Red |
| 4 | Por lotes |
| 5 | Servicio |
| 7 | Desbloqueo |
| 8 | Red con credenciales en texto claro |
| 9 | Credenciales nuevas |
| 10 | Interactivo remoto (RDP) |
| 11 | Interactivo con caché |

#### Administración de cuentas
| ID de evento | Descripción |
|----------|-------------|
| **4720** | Cuenta de usuario creada |
| **4722** | Cuenta de usuario habilitada |
| **4723** | Intento de cambio de contraseña |
| **4724** | Restablecimiento de contraseña |
| **4725** | Cuenta de usuario deshabilitada |
| **4726** | Cuenta de usuario eliminada |
| **4728** | Miembro añadido a un grupo de seguridad |
| **4732** | Miembro añadido a un grupo local |
| **4756** | Miembro añadido a un grupo universal |

#### Eventos de procesos y servicios
| ID de evento | Descripción |
|----------|-------------|
| **4688** | Creación de procesos |
| **4689** | Finalización de procesos |
| **7045** | Servicio instalado |
| **7036** | Cambio de estado del servicio |
| **4697** | Servicio instalado (registro de seguridad) |

#### Tareas programadas
| ID de evento | Descripción |
|----------|-------------|
| **4698** | Tarea programada creada |
| **4699** | Tarea programada eliminada |
| **4700** | Tarea programada habilitada |
| **4702** | Tarea programada actualizada |

### Comandos de consulta de PowerShell
```powershell
# Obtener entradas del registro de seguridad
Get-WinEvent -LogName Security -MaxEvents 100

# Inicios de sesión fallidos (4625)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4625} | 
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='IP';E={$_.Properties[19].Value}}

# Inicios de sesión correctos (4624)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4624} |
    Select-Object TimeCreated, @{N='User';E={$_.Properties[5].Value}}, @{N='LogonType';E={$_.Properties[8].Value}}

# Creación de procesos nuevos (4688)
Get-WinEvent -FilterHashtable @{LogName='Security';ID=4688} |
    Select-Object TimeCreated, @{N='Process';E={$_.Properties[5].Value}}, @{N='CommandLine';E={$_.Properties[8].Value}}

# Instalaciones de servicios (7045)
Get-WinEvent -FilterHashtable @{LogName='System';ID=7045} |
    Select-Object TimeCreated, @{N='ServiceName';E={$_.Properties[0].Value}}

# Buscar por intervalo de tiempo
$startTime = (Get-Date).AddDays(-7)
Get-WinEvent -FilterHashtable @{LogName='Security';StartTime=$startTime}
```

### Comandos de wevtutil
```cmd
# Consultar el registro de seguridad
wevtutil qe Security /c:50 /f:text

# Consultar un identificador de evento específico
wevtutil qe Security /q:"*[System[(EventID=4625)]]" /c:100 /f:text

# Exportar el registro
wevtutil epl Security C:\evidence\security.evtx

# Borrar el registro (usar con precaución)
wevtutil cl Security
```

---

## 🐧 Análisis de registros de Linux

### Ubicación de los registros
```bash
# Autenticación
/var/log/auth.log        # Debian/Ubuntu
/var/log/secure          # RHEL/CentOS

# Sistema
/var/log/syslog          # Debian/Ubuntu
/var/log/messages        # RHEL/CentOS

# Kernel
/var/log/kern.log
/var/log/dmesg

# Cron
/var/log/cron

# Auditoría
/var/log/audit/audit.log

# Servidores web
/var/log/apache2/        # Apache
/var/log/nginx/          # Nginx
/var/log/httpd/          # RHEL Apache

# SSH
/var/log/auth.log
```

### Comandos habituales de análisis de registros

#### Análisis de autenticación
```bash
# Intentos fallidos de SSH
grep "Failed password" /var/log/auth.log
grep "authentication failure" /var/log/auth.log

# Inicios de sesión SSH correctos
grep "Accepted" /var/log/auth.log

# Fuerza bruta contra SSH (recuento por IP)
grep "Failed password" /var/log/auth.log | awk '{print $(NF-3)}' | sort | uniq -c | sort -rn

# Elevación de privilegios del usuario (sudo)
grep "sudo:" /var/log/auth.log
grep "session opened for user root" /var/log/auth.log

# Intentos de su
grep "su:" /var/log/auth.log
```

#### Análisis del sistema
```bash
# Horas de arranque y apagado
last -x | grep shutdown
last -x | grep reboot

# Usuarios con sesión iniciada actualmente
who
w

# Historial de inicios de sesión
last
lastlog

# Intentos fallidos de inicio de sesión
lastb
```

#### Análisis de procesos y servicios
```bash
# Ejecuciones de tareas de cron
grep CRON /var/log/syslog

# Inicio y parada de servicios
grep -E "Started|Stopped" /var/log/syslog
journalctl -u <service-name>

# Mensajes del kernel
dmesg | tail -100
grep -i error /var/log/kern.log
```

### Patrones útiles de grep y awk
```bash
# Extraer IP de los registros
grep -oE '\b[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\b' /var/log/auth.log

# Contar apariciones por IP
cat /var/log/auth.log | grep "Failed" | awk '{print $11}' | sort | uniq -c | sort -rn | head -20

# Búsqueda por tiempo
awk '/Jan 15 10:00/,/Jan 15 11:00/' /var/log/auth.log

# Buscar en varios archivos
grep -r "error" /var/log/

# Supervisión en tiempo real
tail -f /var/log/auth.log | grep --line-buffered "Failed"
```

### Journalctl (Systemd)
```bash
# Todos los registros
journalctl

# Modo de seguimiento
journalctl -f

# Desde un momento determinado
journalctl --since "2024-01-15 10:00:00"
journalctl --since "1 hour ago"

# Por unidad o servicio
journalctl -u sshd
journalctl -u nginx

# Por prioridad
journalctl -p err       # Solo errores
journalctl -p warning   # Advertencias y niveles superiores

# Mensajes del kernel
journalctl -k

# Salida JSON
journalctl -o json
```

---

## 🌐 Registros de servidores web

### Formato de registro de Apache
```
# Formato de registro combinado
192.168.1.100 - - [15/Jan/2024:10:30:00 +0000] "GET /admin HTTP/1.1" 200 1234 "http://example.com" "Mozilla/5.0..."

# Campos:
# IP - ident - usuario - [marca de tiempo] "método ruta protocolo" estado tamaño "referer" "user-agent"
```

### Formato de registro de Nginx
```
# Formato predeterminado similar al de Apache
192.168.1.100 - - [15/Jan/2024:10:30:00 +0000] "GET /admin HTTP/1.1" 200 1234 "http://example.com" "Mozilla/5.0..."
```

### Análisis de registros web
```bash
# URL más solicitadas
awk '{print $7}' /var/log/apache2/access.log | sort | uniq -c | sort -rn | head -20

# IP más frecuentes
awk '{print $1}' /var/log/apache2/access.log | sort | uniq -c | sort -rn | head -20

# Códigos de estado HTTP
awk '{print $9}' /var/log/apache2/access.log | sort | uniq -c | sort -rn

# Errores 404
grep ' 404 ' /var/log/apache2/access.log

# Solicitudes POST (posibles ataques)
grep '"POST' /var/log/apache2/access.log

# Intentos de inyección SQL
grep -iE "(union|select|insert|update|delete|drop|script)" /var/log/apache2/access.log

# Intentos de path traversal (recorrido de directorios)
grep -E "(\.\./|\.\.\\)" /var/log/apache2/access.log

# Solicitudes por hora
awk '{print $4}' /var/log/apache2/access.log | cut -d: -f2 | sort | uniq -c
```

---

## 🛡️ Análisis de registros de Sysmon

### Identificadores de eventos importantes de Sysmon
| ID de evento | Descripción |
|----------|-------------|
| 1 | Creación de procesos |
| 2 | Cambio de la hora de creación de un archivo |
| 3 | Conexión de red |
| 5 | Proceso finalizado |
| 6 | Controlador cargado |
| 7 | Imagen cargada (DLL) |
| 8 | CreateRemoteThread |
| 10 | ProcessAccess |
| 11 | FileCreate (creación de archivo) |
| 12-14 | Eventos del Registro |
| 15 | FileCreateStreamHash (hash de un flujo creado) |
| 17-18 | Eventos de canalizaciones (pipes) |
| 22 | Consulta DNS |

### Consultas de Sysmon con PowerShell
```powershell
# Creación de procesos (evento 1)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=1} |
    Select-Object TimeCreated, @{N='Image';E={$_.Properties[4].Value}}, @{N='CommandLine';E={$_.Properties[10].Value}}

# Conexiones de red (evento 3)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=3} |
    Select-Object TimeCreated, @{N='Image';E={$_.Properties[4].Value}}, @{N='DestIP';E={$_.Properties[14].Value}}

# Consultas DNS (evento 22)
Get-WinEvent -FilterHashtable @{LogName='Microsoft-Windows-Sysmon/Operational';ID=22} |
    Select-Object TimeCreated, @{N='Query';E={$_.Properties[4].Value}}
```

---

## 📊 Referencia rápida

### Patrones sospechosos que se deben buscar

| Categoría | Patrón |
|----------|---------|
| **Fuerza bruta** | Varios eventos 4625 desde la misma IP |
| **Ataque de password spraying** | Eventos 4625 para muchos usuarios desde la misma IP |
| **Movimiento lateral** | 4624 de tipo 3 + 4648 |
| **Persistencia** | 4698 (nueva tarea programada) |
| **Elevación de privilegios** | 4672 en cuentas sin privilegios de administrador |
| **Volcado de credenciales** | Acceso a lsass.exe |

### Herramientas de análisis de registros
| Herramienta | Uso |
|------|-----|
| **grep/awk/sed** | Procesamiento de registros de Linux |
| **PowerShell** | Análisis de registros de Windows |
| **Splunk** | SIEM empresarial |
| **ELK Stack** | SIEM de código abierto |
| **LogParser** | Consultas SQL sobre registros |
| **Chainsaw** | Análisis rápido de registros de Windows |

---

## 🔗 Guías rápidas relacionadas

- [Respuesta a incidentes](./Incident-Response.es.md)
- [Detección con SIEM](./SIEM-Detection.es.md)
- [Reglas Sigma](./Sigma-Rules.es.md)

---

**Anterior:** [← Respuesta a incidentes](./Incident-Response.es.md)

**Siguiente:** [Detección con SIEM →](./SIEM-Detection.es.md)
