# 👤 OSINT de búsqueda de personas

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="People-Search.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="People-Search.el.md">🇬🇷 Ελληνικά</a>
</p>

> **Aclaración de versión:** Se conservan las sintaxis originales. PhoneInfoga actual se distribuye mediante binarios, contenedores o compilación; `pip3 install phoneinfoga` no representa su instalación actual. Véase [documentación de PhoneInfoga](https://sundowndev.github.io/phoneinfoga/getting-started/install/). [Twint](https://github.com/twintproject/twint) está archivado desde 2023. Las coincidencias de nombres, perfiles o rostros requieren corroboración; las valoraciones de buscadores y tarifas son las del original.


```
██████╗ ███████╗ ██████╗ ██████╗ ██╗     ███████╗
██╔══██╗██╔════╝██╔═══██╗██╔══██╗██║     ██╔════╝
██████╔╝█████╗  ██║   ██║██████╔╝██║     █████╗  
██╔═══╝ ██╔══╝  ██║   ██║██╔═══╝ ██║     ██╔══╝  
██║     ███████╗╚██████╔╝██║     ███████╗███████╗
╚═╝     ╚══════╝ ╚═════╝ ╚═╝     ╚══════╝╚══════╝
███████╗███████╗ █████╗ ██████╗  ██████╗██╗  ██╗ 
██╔════╝██╔════╝██╔══██╗██╔══██╗██╔════╝██║  ██║ 
███████╗█████╗  ███████║██████╔╝██║     ███████║ 
╚════██║██╔══╝  ██╔══██║██╔══██╗██║     ██╔══██║ 
███████║███████╗██║  ██║██║  ██║╚██████╗██║  ██║ 
╚══════╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝ 
```

---

## 🔍 Motores de búsqueda de personas

### Servicios gratuitos
| Herramienta | URL | Cobertura |
|------|-----|----------|
| **ThatsThem** | thatsthem.com | Estados Unidos |
| **FastPeopleSearch** | fastpeoplesearch.com | Estados Unidos |
| **TruePeopleSearch** | truepeoplesearch.com | Estados Unidos |
| **Webmii** | webmii.com | Global |
| **PeekYou** | peekyou.com | Estados Unidos |
| **Pipl** | pipl.com | Global |

### Servicios de pago
| Herramienta | URL | Funciones |
|------|-----|----------|
| **Spokeo** | spokeo.com | Búsqueda exhaustiva y redes sociales |
| **BeenVerified** | beenverified.com | Comprobación de antecedentes |
| **Intelius** | intelius.com | Antecedentes penales |
| **US Search** | ussearch.com | Registros judiciales |
| **Radaris** | radaris.com | Registros de propiedad |

---

## 🔎 Google dorking para buscar personas

### Búsquedas básicas
```
# Búsqueda por nombre
"John Smith" site:linkedin.com
"John Smith" site:facebook.com
"John Smith" site:twitter.com

# Nombre y ubicación
"John Smith" "New York"
"John Smith" "NYC" OR "New York City"

# Nombre y empresa
"John Smith" "Microsoft"
"John Smith" site:microsoft.com

# Nombre y correo electrónico
"John Smith" "@gmail.com"
"John Smith" email
```

### Información profesional
```
# Búsqueda de currículums
"John Smith" filetype:pdf resume
"John Smith" filetype:doc CV
intitle:"curriculum vitae" "John Smith"

# Específico de LinkedIn
site:linkedin.com/in "John Smith"
site:linkedin.com "John Smith" "Software Engineer"

# Perfiles profesionales
site:about.me "John Smith"
site:crunchbase.com "John Smith"
```

### Descubrimiento de redes sociales
```
# Encontrar todos los perfiles
"John Smith" (site:facebook.com OR site:twitter.com OR site:instagram.com)

# Búsqueda de nombres de usuario
"johnsmith123" site:twitter.com
"johnsmith123" site:reddit.com
"johnsmith123" site:github.com
```

---

## 🛠️ Herramientas de línea de comandos

### Sherlock (búsqueda de nombres de usuario)
```bash
# Instalar
git clone https://github.com/sherlock-project/sherlock.git
cd sherlock
pip3 install -r requirements.txt

# Buscar username across platforms
python3 sherlock username

# Varios nombres de usuario
python3 sherlock user1 user2 user3

# Solo sitios específicos
python3 sherlock username --site twitter instagram facebook

# Exportar los resultados
python3 sherlock username --output results.txt
python3 sherlock username --csv
python3 sherlock username --xlsx
```

### Maigret (búsqueda ampliada de nombres de usuario)
```bash
# Instalar
pip3 install maigret

# Buscar
maigret username

# Con Tor
maigret username --tor

# Salida JSON
maigret username --json results.json
```

### holehe (comprobación de registros por correo)
```bash
# Instalar
pip3 install holehe

# Comprobar un correo en varios servicios
holehe email@example.com

# Mostrar solo los sitios con registro
holehe email@example.com --only-used
```

---

## 📧 Del correo electrónico a la persona

### Encontrar a una persona a partir del correo
```
1. Buscar el correo en Google: "email@example.com"
2. Comprobar HIBP: haveibeenpwned.com
3. Buscar en bases de datos de filtraciones
4. Comprobar registros en redes sociales
5. Servicios de búsqueda inversa de correo
```

### Descubrimiento del formato de correo
```bash
# Formatos habituales de correo
first.last@company.com
flast@company.com
firstl@company.com
first_last@company.com
first@company.com

# Buscador de formatos de Hunter.io
https://hunter.io/email-finder
```

---

## 📱 OSINT de números de teléfono

### Servicios de consulta de teléfonos
| Servicio | URL | Tipo |
|---------|-----|------|
| **TrueCaller** | truecaller.com | Identificación de llamadas |
| **Sync.me** | sync.me | Identificación de llamadas |
| **NumLookup** | numlookup.com | Consulta gratuita |
| **SpyDialer** | spydialer.com | Consulta inversa |
| **CallerID Test** | calleridtest.com | Información del operador |

### Google dorks de números de teléfono
```
# Búsqueda básica
"555-123-4567"
"+1 555 123 4567"
"5551234567"

# Encontrar información asociada
"555-123-4567" site:facebook.com
"555-123-4567" "@"
"555-123-4567" "email"
```

### OSINT sobre números de teléfono
```bash
# phoneinfoga
pip3 install phoneinfoga
phoneinfoga scan -n "+1234567890"
phoneinfoga serve -p 8080  # Interfaz web

# Usar API
curl "https://api.numlookupapi.com/v1/validate/14155551234?apikey=YOUR_KEY"
```

---

## 🏠 Direcciones y registros de propiedad

### Búsqueda de propiedades
| Servicio | URL | Cobertura |
|---------|-----|----------|
| **Zillow** | zillow.com | Estados Unidos |
| **Redfin** | redfin.com | Estados Unidos |
| **County Assessor** | [varies] | Registros locales |
| **Whitepages** | whitepages.com | Estados Unidos |

### Registros públicos
```
# Registros judiciales
PACER (pacer.gov) - Tribunales federales
Sitios de tribunales estatales: tribunales de los estados

# Registros electorales
Bases de datos del censo electoral (varían según el estado)

# Registros empresariales
Sitios web de las secretarías de Estado de cada estado
```

---

## 👥 Análisis de redes sociales

### OSINT de LinkedIn
```
# Búsqueda de perfiles
site:linkedin.com/in "John Smith"
site:linkedin.com/in "John Smith" "Company Name"

# Encontrar conexiones
Consultar "People Also Viewed" (otros perfiles vistos)
Comprobar conexiones en común
Lista de empleados de la empresa
```

### OSINT de Facebook
```
# URL de perfiles
facebook.com/search/people/?q=john%20smith
facebook.com/search/pages/?q=john%20smith

# Graph Search (actualmente limitado)
People named "John" who work at "Company"
People who live in "City" and like "Page"
```

### Herramientas de análisis de redes sociales
```bash
# Social Analyzer
pip3 install social-analyzer
social-analyzer --username "target" --metadata

# Twint (Twitter)
pip3 install twint
twint -u username --followers
twint -u username --following
```

---

## 📸 Búsqueda de fotografías y rostros

### Búsqueda inversa de imágenes
| Servicio | URL | Más útil para |
|---------|-----|----------|
| **Google Images** | images.google.com | General |
| **TinEye** | tineye.com | Coincidencias exactas |
| **Yandex** | yandex.com/images | Especialmente útil para rostros |
| **Bing Images** | bing.com/images | Buena alternativa |
| **PimEyes** | pimeyes.com | Reconocimiento facial |

### Búsqueda de rostros
```
1. Yandex Images - Especialmente útil para encontrar rostros
2. PimEyes - Reconocimiento facial de pago
3. Social Catfish - Búsqueda inversa de imágenes para redes sociales
```

---

## 📋 Lista de comprobación de búsqueda de personas

```markdown
## Objetivo: [Full Name]

### Información básica
□ Nombre legal completo
□ Fecha de nacimiento
□ Dirección actual
□ Números de teléfono
□ Direcciones de correo electrónico

### Presencia en Internet
□ Perfil de LinkedIn
□ Perfil de Facebook
□ Cuenta de Twitter/X
□ Instagram
□ TikTok
□ Otras redes sociales
□ Sitio web o blog personal

### Información profesional
□ Empleador actual
□ Puesto de trabajo
□ Historial laboral
□ Formación académica
□ Licencias profesionales
□ Publicaciones y patentes

### Registros
□ Registros de propiedad
□ Registros judiciales
□ Registros empresariales
□ Inscripción electoral
□ Registros de matrimonio y divorcio

### Conexiones
□ Familiares
□ Personas vinculadas
□ Socios empresariales
□ Conexiones sociales
```

---

## ⚠️ Consideraciones legales y éticas

```
✓ Usar solo información disponible públicamente
✓ Respetar las leyes de privacidad (GDPR, CCPA)
✓ No suplantar identidades ni engañar
✓ Documentar la metodología
✓ Usar solo para fines legítimos
✗ No acosar ni perseguir a personas
✗ No acceder a cuentas privadas
✗ No publicar los hallazgos sin consentimiento
```

---

**Volver a OSINT:** [🔍 Índice de OSINT](./README.es.md)
