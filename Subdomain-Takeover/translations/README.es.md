# 🎯 Guía rápida de toma de control de subdominios

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
  ███████╗██╗   ██╗██████╗ ██████╗  ██████╗ ███╗   ███╗ █████╗ ██╗███╗   ██╗
  ██╔════╝██║   ██║██╔══██╗██╔══██╗██╔═══██╗████╗ ████║██╔══██╗██║████╗  ██║
  ███████╗██║   ██║██████╔╝██║  ██║██║   ██║██╔████╔██║███████║██║██╔██╗ ██║
  ╚════██║██║   ██║██╔══██╗██║  ██║██║   ██║██║╚██╔╝██║██╔══██║██║██║╚██╗██║
  ███████║╚██████╔╝██████╔╝██████╔╝╚██████╔╝██║ ╚═╝ ██║██║  ██║██║██║ ╚████║
  ╚══════╝ ╚═════╝ ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝
                ████████╗ █████╗ ██╗  ██╗███████╗ ██████╗ ██╗   ██╗███████╗██████╗ 
                ╚══██╔══╝██╔══██╗██║ ██╔╝██╔════╝██╔═══██╗██║   ██║██╔════╝██╔══██╗
                   ██║   ███████║█████╔╝ █████╗  ██║   ██║██║   ██║█████╗  ██████╔╝
                   ██║   ██╔══██║██╔═██╗ ██╔══╝  ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗
                   ██║   ██║  ██║██║  ██╗███████╗╚██████╔╝ ╚████╔╝ ███████╗██║  ██║
                   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝ ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝
```

---

## 🎯 ¿Qué es la toma de control de subdominios?

La **toma de control de subdominios** (subdomain takeover) puede ocurrir cuando un subdominio apunta mediante un CNAME a un servicio eliminado y otra persona puede reclamar ese recurso y publicar contenido malicioso.

### Impacto
- 🔴 **Phishing** - Alojar páginas de suplantación convincentes
- 🔴 **Robo de cookies** - Robar cookies configuradas para el dominio padre
- 🔴 **Toma de control de cuentas** - Interceptar callbacks de OAuth
- 🔴 **Daño reputacional** - Publicar contenido malicioso

---

> **Alcance del impacto:** las cookies disponibles dependen de atributos como Domain, Path, Secure y HttpOnly y de cómo se reciba la petición. El robo de cuentas mediante OAuth exige además un flujo vulnerable. Ninguno de estos impactos se deriva automáticamente de cualquier takeover.

## 🔍 Metodología de detección

### Paso 1: enumerar subdominios
```bash
# Varias herramientas para ampliar la cobertura
subfinder -d target.com -o subs.txt
amass enum -d target.com -o amass.txt
assetfinder --subs-only target.com >> subs.txt

# Combinar y eliminar duplicados
cat subs.txt amass.txt | sort -u > all_subs.txt
```

### Paso 2: buscar registros CNAME
```bash
# Comprobar los registros DNS
cat all_subs.txt | while read sub; do
    cname=$(dig CNAME +short $sub)
    if [ ! -z "$cname" ]; then
        echo "$sub -> $cname"
    fi
done

# O utilizar dnsrecon
dnsrecon -d target.com -t crt
```

### Paso 3: comprobar los CNAME huérfanos
```bash
# Comprobación manual
dig CNAME subdomain.target.com +short
# Devuelve: something.cloudfront.net
# Después, comprobar si el servicio está sin reclamar

# httpx para consultar los códigos de estado
cat all_subs.txt | httpx -status-code -title -tech-detect -no-color
```

---

## 🛠️ Herramientas automatizadas

### Subjack
```bash
# Instalar
go install github.com/haccer/subjack@latest

# Escanear
subjack -w subdomains.txt -t 100 -timeout 30 -o results.txt -ssl

# Con huellas de respuesta
subjack -w subdomains.txt -t 100 -a -timeout 30 -o results.txt -ssl
```

### Nuclei (plantillas de takeover)
```bash
# Utilizar las plantillas de Nuclei
nuclei -l subdomains.txt -t ~/nuclei-templates/takeovers/ -o takeovers.txt

# Comprobaciones de takeover concretas
nuclei -l subdomains.txt -t ~/nuclei-templates/takeovers/subdomain-takeover.yaml
```

### Can-I-Take-Over-XYZ
```bash
# Consultar las huellas en el repositorio de GitHub
# https://github.com/EdOverflow/can-i-take-over-xyz

# Utilizar las huellas manualmente o mediante herramientas automatizadas
```

---

> **Validación:** un CNAME huérfano o una huella HTTP es un indicio, no una confirmación de que pueda reclamarse el recurso. Las políticas de los proveedores cambian y las recetas siguientes reflejan el original. Comprueba las condiciones actuales del servicio antes de interpretar un resultado.

## 📋 Servicios potencialmente vulnerables y huellas de respuesta

### AWS S3
```
CNAME: bucket.s3.amazonaws.com
CNAME: bucket.s3-website-region.amazonaws.com

Fingerprint: "NoSuchBucket"
Fingerprint: "The specified bucket does not exist"

Takeover: crear un bucket con el mismo nombre
```

### AWS CloudFront
```
CNAME: xxxxx.cloudfront.net

Fingerprint: "Bad Request"
Fingerprint: "ERROR: The request could not be satisfied"

Takeover: crear una distribución de CloudFront con el mismo dominio alternativo
```

> **CloudFront:** un error genérico no demuestra takeover. Para añadir un dominio alternativo, CloudFront exige un certificado que cubra ese nombre y comprueba conflictos con otras distribuciones. La instrucción anterior no describe esos requisitos. Véase la [documentación de AWS](https://docs.aws.amazon.com/AmazonCloudFront/latest/DeveloperGuide/CNAMEs.html).

### GitHub Pages
```
CNAME: username.github.io
CNAME: organization.github.io

Fingerprint: "There isn't a GitHub Pages site here"

Takeover:
1. Crear el repositorio username.github.io
2. Añadir un archivo CNAME con el subdominio objetivo
```

> **GitHub Pages:** crear un repositorio y añadir CNAME no garantiza controlar el dominio. La verificación de dominios limita qué cuentas pueden publicar en el dominio verificado y sus subdominios inmediatos. Consulta la [documentación oficial de GitHub](https://docs.github.com/en/pages/configuring-a-custom-domain-for-your-github-pages-site/verifying-your-custom-domain-for-github-pages).

### Heroku
```
CNAME: xxx.herokuapp.com
CNAME: xxx.herokudns.com

Fingerprint: "No such app"
Fingerprint: "There's nothing here, yet"

Takeover: crear una aplicación de Heroku con el mismo nombre
```

### Azure
```
CNAME: xxx.azurewebsites.net
CNAME: xxx.cloudapp.azure.com
CNAME: xxx.blob.core.windows.net

Fingerprint: "404 - Web Site not found"

Takeover: crear un recurso con el mismo nombre
```

### Shopify
```
CNAME: shops.myshopify.com

Fingerprint: "Sorry, this shop is currently unavailable"
Fingerprint: "Only one step left"

Takeover: crear una tienda Shopify y añadir el dominio personalizado
```

### WordPress.com
```
CNAME: xxx.wordpress.com

Fingerprint: "Do you want to register *.wordpress.com?"

Takeover: registrar el subdominio en WordPress.com
```

### Zendesk
```
CNAME: xxx.zendesk.com

Fingerprint: "Help Center Closed"
Fingerprint: "This help center no longer exists"

Takeover: no siempre es posible; depende de la configuración de Zendesk
```

### Fastly
```
CNAME: xxx.fastly.net

Fingerprint: "Fastly error: unknown domain"

Takeover: añadir el dominio al servicio Fastly
```

### Pantheon
```
CNAME: xxx.pantheonsite.io

Fingerprint: "The gods are wise but do not know of the site"
Fingerprint: "404 error unknown site!"

Takeover: añadir el sitio en Pantheon
```

---

## 🔥 Pasos de explotación

### Ejemplo: toma de control mediante GitHub Pages

```bash
# 1. Encontrar un subdominio vulnerable
dig CNAME blog.target.com
# Devuelve: target.github.io (pero el repositorio no existe)

# 2. Crear el repositorio de GitHub
# Crear: yourusername/target (u org/target)

# 3. Activar GitHub Pages en los ajustes del repositorio

# 4. Añadir un archivo CNAME con el siguiente contenido:
blog.target.com

# 5. Esperar la propagación DNS

# 6. Verificar el takeover
curl https://blog.target.com
```

### Ejemplo: toma de control mediante S3

```bash
# 1. Confirmar la vulnerabilidad
dig CNAME assets.target.com
# Devuelve: assets-target.s3.amazonaws.com

curl http://assets-target.s3.amazonaws.com
# Devuelve: NoSuchBucket

# 2. Crear un bucket de S3 (misma región)
aws s3 mb s3://assets-target --region us-east-1

# 3. Configurar el bucket
# Añadir alojamiento web o acceso público

# 4. Subir contenido
echo "Subdomain Takeover PoC" > index.html
aws s3 cp index.html s3://assets-target/

# 5. Verificar
curl http://assets.target.com
```

---

> **Corrección del ejemplo S3:** al acceder mediante un CNAME personalizado, S3 utiliza el hostname de la petición para identificar el bucket, que debe coincidir exactamente. Crear `assets-target` no garantiza servir `assets.target.com`; el ejemplo original mezcla esos nombres. El acceso público, el endpoint y la configuración web también influyen. Véase la [guía de alojamiento virtual de S3](https://docs.aws.amazon.com/AmazonS3/latest/userguide/VirtualHosting.html).

## 📊 Referencia rápida

### Detección de servicios potencialmente vulnerables

| Servicio | Patrón CNAME | Fingerprint |
|---------|---------------|-------------|
| AWS S3 | `*.s3.amazonaws.com` | NoSuchBucket |
| CloudFront | `*.cloudfront.net` | Bad Request |
| GitHub | `*.github.io` | No GitHub Pages |
| Heroku | `*.herokuapp.com` | No such app |
| Azure | `*.azurewebsites.net` | 404 Web Site not found |
| Shopify | `shops.myshopify.com` | Shop unavailable |

### Lista de comprobación de takeover

```markdown
- [ ] Enumerar todos los subdominios
- [ ] Identificar los registros CNAME
- [ ] Comprobar si hay registros huérfanos
- [ ] Comparar las huellas de respuesta
- [ ] Verificar que el servicio está sin reclamar
- [ ] Intentar el takeover de forma ética
- [ ] Documentar e informar
```

---

## 🛠️ Resumen de herramientas

| Herramienta | Finalidad |
|------|---------|
| **subjack** | Detección automatizada de posibles takeovers |
| **nuclei** | Escaneo mediante plantillas |
| **can-i-take-over-xyz** | Referencia de huellas de respuesta |
| **dnsrecon** | Enumeración DNS |
| **subfinder** | Descubrimiento de subdominios |
| **httpx** | Comprobación HTTP |

---

## 📚 Recursos

- [can-i-take-over-xyz](https://github.com/EdOverflow/can-i-take-over-xyz)
- [Subjack](https://github.com/haccer/subjack)
- [Plantillas de takeover de Nuclei](https://github.com/projectdiscovery/nuclei-templates/tree/main/takeovers)

---

<p align="center">
  <b>🎯 ¡Identifica los subdominios que pueden reclamarse!</b><br>
  <i>¡Solo para pruebas autorizadas!</i>
</p>
