# 🚨 Guía rápida de respuesta a incidentes

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Incident-Response.tr.md) • [🇬🇷 Ελληνικά](Incident-Response.el.md)

> **Notas de lectura:** IR significa respuesta a incidentes; triage es la evaluación inicial; IOC son indicadores de compromiso. Los tiempos de la tabla son orientativos. La fecha de `forfiles` es un ejemplo literal. La adquisición mediante `/dev/mem` depende de las restricciones del kernel y no garantiza un volcado completo.


```
  ██╗███╗   ██╗ ██████╗██╗██████╗ ███████╗███╗   ██╗████████╗
  ██║████╗  ██║██╔════╝██║██╔══██╗██╔════╝████╗  ██║╚══██╔══╝
  ██║██╔██╗ ██║██║     ██║██║  ██║█████╗  ██╔██╗ ██║   ██║   
  ██║██║╚██╗██║██║     ██║██║  ██║██╔══╝  ██║╚██╗██║   ██║   
  ██║██║ ╚████║╚██████╗██║██████╔╝███████╗██║ ╚████║   ██║   
  ╚═╝╚═╝  ╚═══╝ ╚═════╝╚═╝╚═════╝ ╚══════╝╚═╝  ╚═══╝   ╚═╝   
        ██████╗ ███████╗███████╗██████╗  ██████╗ ███╗   ██╗███████╗███████╗
        ██╔══██╗██╔════╝██╔════╝██╔══██╗██╔═══██╗████╗  ██║██╔════╝██╔════╝
        ██████╔╝█████╗  ███████╗██████╔╝██║   ██║██╔██╗ ██║███████╗█████╗  
        ██╔══██╗██╔══╝  ╚════██║██╔═══╝ ██║   ██║██║╚██╗██║╚════██║██╔══╝  
        ██║  ██║███████╗███████║██║     ╚██████╔╝██║ ╚████║███████║███████╗
        ╚═╝  ╚═╝╚══════╝╚══════╝╚═╝      ╚═════╝ ╚═╝  ╚═══╝╚══════╝╚══════╝
```

---

## 📊 Ciclo de vida de la respuesta a incidentes (NIST)

```
┌──────────────────┐     ┌──────────────────┐     ┌──────────────────┐     ┌──────────────────┐
│  1. PREPARACIÓN  │────▶│   2. DETECCIÓN   │────▶│  3. CONTENCIÓN   │────▶│ 4. ERRADICACIÓN  │
│                  │     │    Y ANÁLISIS    │     │                  │     │                  │
└──────────────────┘     └──────────────────┘     └──────────────────┘     └──────────────────┘
         ▲                                                                          │
         │                                                                          │
         │                                                                          │
         │               ┌──────────────────┐     ┌──────────────────┐              │
         └───────────────│   6. LECCIONES   │◀────│ 5. RECUPERACIÓN  │◀─────────────┘
                         │    APRENDIDAS    │     │                  │
                         └──────────────────┘     └──────────────────┘
```

---

## 1️⃣ Preparación

### Lista de comprobación de la documentación
```markdown
- [ ] Plan de respuesta a incidentes documentado
- [ ] Lista de contactos (internos y externos)
- [ ] Plan de comunicación
- [ ] Inventario de activos
- [ ] Diagramas de red
- [ ] Configuraciones de referencia
- [ ] Verificación de las copias de seguridad
```

### Plantilla de contactos del equipo de respuesta a incidentes (IR)
```
Responsable de IR:          [Nombre] - [Teléfono] - [Correo]
Seguridad de TI:      [Nombre] - [Teléfono] - [Correo]
Asesoría jurídica:            [Nombre] - [Teléfono] - [Correo]
Comunicación:   [Nombre] - [Teléfono] - [Correo]
Recursos humanos:               [Nombre] - [Teléfono] - [Correo]
Análisis forense externo: [Empresa] - [Teléfono]
Fuerzas de seguridad:  [Organismo] - [Teléfono]
```

### Herramientas esenciales de respuesta a incidentes
```bash
# Adquisición forense
- FTK Imager
- Volatility
- Velociraptor
- KAPE

# Análisis de red
- Wireshark
- tcpdump
- Zeek

# Análisis de registros
- Splunk/ELK
- grep/awk/sed
```

---

## 2️⃣ Detección y análisis

### Preguntas para la evaluación inicial (triage)
```markdown
1. ¿De qué tipo de incidente se trata?
   - [ ] Infección por malware
   - [ ] Acceso no autorizado
   - [ ] Filtración de datos
   - [ ] Denegación de servicio
   - [ ] Amenaza interna
   - [ ] Phishing

2. ¿Cuándo se detectó?
3. ¿Cómo se detectó?
4. ¿Qué sistemas están afectados?
5. ¿Qué datos pueden haberse visto afectados?
6. ¿Sigue activo?
```

### Evaluación rápida de Windows
```cmd
# Sesiones de usuario actuales
query user
qwinsta

# Conexiones activas
netstat -ano | findstr ESTABLISHED

# Procesos en ejecución
tasklist /v
wmic process get processid,parentprocessid,commandline

# Modificaciones recientes de archivos
forfiles /P C:\ /S /D +01/01/2024

# Tareas programadas
schtasks /query /fo LIST /v

# Servicios
sc query state=all
wmic service get name,startmode,state

# Claves Run del Registro
reg query "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run"
reg query "HKCU\SOFTWARE\Microsoft\Windows\CurrentVersion\Run"

# Historial reciente de PowerShell
type %APPDATA%\Microsoft\Windows\PowerShell\PSReadLine\ConsoleHost_history.txt
```

### Evaluación rápida de Linux
```bash
# Usuarios con sesión iniciada
w
who
last -a

# Conexiones activas
ss -tulnp
netstat -tulnp
lsof -i

# Procesos en ejecución
ps auxwww
pstree -p

# Modificaciones recientes de archivos
find / -mtime -1 -type f 2>/dev/null

# Tareas de cron
cat /etc/crontab
ls -la /etc/cron.*
for user in $(cut -f1 -d: /etc/passwd); do crontab -u $user -l 2>/dev/null; done

# Scripts de inicio
ls -la /etc/init.d/
systemctl list-unit-files --type=service

# Historial
cat ~/.bash_history
cat /root/.bash_history
```

### Análisis de red
```bash
# Capturar tráfico
tcpdump -i eth0 -w capture.pcap

# Filtrar un host específico
tcpdump -i eth0 host 192.168.1.100

# Tráfico HTTP
tcpdump -i eth0 port 80 or port 443

# Consultas DNS
tcpdump -i eth0 port 53
```

---

## 3️⃣ Contención

### Contención a corto plazo
```bash
# Aislar la red (Windows)
netsh advfirewall set allprofiles state on
netsh advfirewall firewall add rule name="Block All" dir=in action=block
netsh advfirewall firewall add rule name="Block All Out" dir=out action=block

# Aislar la red (Linux)
iptables -P INPUT DROP
iptables -P OUTPUT DROP
iptables -P FORWARD DROP

# Desactivar la cuenta comprometida
net user compromised_user /active:no   # Windows
passwd -l compromised_user             # Linux

# Finalizar el proceso malicioso
taskkill /PID <pid> /F               # Windows
kill -9 <pid>                        # Linux
```

### Contención a largo plazo
```bash
# Segmentar la red
# Aplicar reglas de cortafuegos para aislar la VLAN afectada
# Actualizar las ACL de switches y routers

# Bloquear las IP y los dominios maliciosos en el cortafuegos
# Añadir a la lista de bloqueo del proxy
# Actualizar el sumidero DNS (DNS sinkhole)

# Desactivar temporalmente los servicios afectados
sc config <service> start= disabled   # Windows
systemctl disable <service>           # Linux
```

### Preservación de evidencias
```bash
# Volcado de memoria (Windows: usar WinPMEM o DumpIt)
winpmem_mini_x64.exe memory.raw

# Volcado de memoria (Linux)
dd if=/dev/mem of=/evidence/memory.dump bs=1M

# Imagen de disco
dd if=/dev/sda of=/evidence/disk.img bs=4M status=progress

# Hash para verificar la integridad
sha256sum /evidence/disk.img > disk.img.sha256

# Capturar primero los datos volátiles (orden de volatilidad):
# 1. Memoria
# 2. Estado de la red
# 3. Procesos en ejecución
# 4. Disco
```

---

## 4️⃣ Erradicación

### Eliminación de malware
```bash
# Identificar y eliminar la persistencia

# Windows: Registro
reg delete "HKLM\SOFTWARE\Microsoft\Windows\CurrentVersion\Run" /v "MalwareName" /f

# Windows: tareas programadas
schtasks /delete /tn "MalwareTask" /f

# Windows: servicios
sc delete "MalwareService"

# Linux: cron
crontab -r
rm /etc/cron.d/malware

# Linux: systemd
systemctl disable malware
rm /etc/systemd/system/malware.service
```

### Restablecimiento de contraseñas
```bash
# Forzar el restablecimiento de contraseña de todas las cuentas comprometidas
# Restablecer las contraseñas de las cuentas de servicio
# Rotar las claves y los tokens de API
# Invalidar las sesiones

# Windows: forzar el cambio de contraseña
net user username /logonpasswordchg:yes

# Renovar los tickets Kerberos (si AD está comprometido)
# - Restablecer la contraseña de krbtgt DOS VECES
# - Restablecer todas las contraseñas de cuentas de administrador
```

### Parches y actualizaciones
```bash
# Aplicar los parches de seguridad pendientes
# Actualizar las firmas del antivirus
# Corregir las configuraciones incorrectas que permitieron el ataque
```

---

## 5️⃣ Recuperación

### Restauración de sistemas
```bash
# Restaurar a partir de una copia de seguridad limpia
# Reconstruir los sistemas comprometidos
# Validar la integridad antes de volver a ponerlos en línea

# Restauración gradual:
1. Restaurar primero los sistemas críticos
2. Vigilar los indicios de reinfección
3. Restaurar los demás sistemas
4. Volver al funcionamiento normal
```

### Supervisión después de la recuperación
```bash
# Aumentar el nivel de detalle de los registros
# Desplegar supervisión adicional
# Vigilar el posible regreso del atacante

# Indicadores clave que se deben supervisar:
- Los mismos IOC (IP, dominios y hashes)
- Tácticas, técnicas y procedimientos (TTP) similares
- Intentos de acceso a las mismas cuentas
- Comunicación con la infraestructura de mando y control (C2)
```

---

## 6️⃣ Lecciones aprendidas

### Plantilla de informe posterior al incidente
```markdown
## Resumen del incidente
- Identificador del incidente:
- Fecha de detección:
- Fecha de contención:
- Fecha de resolución:
- Tipo de incidente:
- Gravedad:

## Cronología
| Fecha y hora | Evento |
|-----------|-------|
| YYYY-MM-DD HH:MM | Detección inicial |
| ... | ... |

## Evaluación del impacto
- Sistemas afectados:
- Datos comprometidos:
- Impacto en el negocio:
- Impacto económico:

## Análisis de la causa raíz
- ¿Cómo consiguió acceso el atacante?
- ¿Qué vulnerabilidades se explotaron?
- ¿Qué controles fallaron?

## Acciones realizadas
1. Detección
2. Contención
3. Erradicación
4. Recuperación

## Recomendaciones
1. Mejoras inmediatas
2. Mejoras de seguridad a largo plazo
3. Cambios en las políticas

## Lecciones aprendidas
- ¿Qué funcionó bien?
- ¿Qué podría mejorarse?
- ¿Se identificaron necesidades de formación?
```

---

## 📊 Referencia rápida

### Clasificación de la gravedad

| Nivel | Descripción | Tiempo de respuesta |
|-------|-------------|---------------|
| **Crítica** | Compromiso activo, exfiltración de datos | Inmediata |
| **Alta** | Compromiso confirmado | < 1 hora |
| **Media** | Posible compromiso | < 4 horas |
| **Baja** | Actividad sospechosa | < 24 horas |

### Indicadores de compromiso (IOC) habituales que se deben recopilar

| Tipo de IOC | Ejemplos |
|----------|----------|
| **Hash** | MD5, SHA1, SHA256 |
| **IP** | IP de origen y destino |
| **Dominio** | Dominios de C2 |
| **URL** | URL maliciosas |
| **Correo electrónico** | Remitente del phishing |
| **Archivo** | Nombre del archivo de malware |
| **Registro** | Claves de persistencia |

---

## 🔗 Guías rápidas relacionadas

- [Análisis de registros](./Log-Analysis.es.md)
- [Detección con SIEM](./SIEM-Detection.es.md)
- [Volatility (Análisis forense)](../../Volatility/translations/README.es.md)

---

**Siguiente:** [Análisis de registros →](./Log-Analysis.es.md)
