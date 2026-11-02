# 🔍 Guía rápida de OSINT (inteligencia de fuentes abiertas)

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

> **Nota de uso:** Los precios, la disponibilidad y la cobertura de los servicios son los indicados por el original y pueden variar. Las fuentes de theHarvester y los módulos de Recon-ng dependen de la versión y de las claves API disponibles. OSINT activo puede generar tráfico directo hacia el objetivo; VPN/Tor no garantiza anonimato.


```
 ██████╗ ███████╗██╗███╗   ██╗████████╗
██╔═══██╗██╔════╝██║████╗  ██║╚══██╔══╝
██║   ██║███████╗██║██╔██╗ ██║   ██║   
██║   ██║╚════██║██║██║╚██╗██║   ██║   
╚██████╔╝███████║██║██║ ╚████║   ██║   
 ╚═════╝ ╚══════╝╚═╝╚═╝  ╚═══╝   ╚═╝   
```

> **Inteligencia de fuentes abiertas (OSINT)**: recopilación y análisis de información disponible públicamente.

---

## 📑 Índice

| Tema | Descripción | Guía |
|-------|-------------|-------|
| **Búsqueda de personas** | Encontrar información sobre personas | [📄 Ver](./People-Search.es.md) |
| **OSINT de correo electrónico** | Reconocimiento y verificación de direcciones de correo | [📄 Ver](./Email-OSINT.es.md) |
| **Redes sociales** | Investigación en redes sociales | [📄 Ver](./Social-Media-OSINT.es.md) |
| **Dominios e IP** | Reconocimiento de dominios e IP | [📄 Ver](./Domain-IP-OSINT.es.md) |
| **OSINT de imágenes** | Búsqueda inversa y análisis de imágenes | [📄 Ver](./Image-OSINT.es.md) |

---

## 🎯 Metodología OSINT

```
┌──────────────────────────────────────────────────────────────────────┐
│  CICLO DE VIDA DE OSINT                                              │
│                                                                      │
│  1. REQUISITOS        → Definir las necesidades de inteligencia      │
│           ↓                                                          │
│  2. RECOPILACIÓN      → Recopilar datos originales de las fuentes    │
│           ↓                                                          │
│  3. PROCESAMIENTO     → Convertir los datos a un formato utilizable  │
│           ↓                                                          │
│  4. ANÁLISIS          → Evaluar y correlacionar los hallazgos        │
│           ↓                                                          │
│  5. DIFUSIÓN          → Informar y presentar la inteligencia         │
└──────────────────────────────────────────────────────────────────────┘
```

---

## 🛠️ Herramientas esenciales de OSINT

### Motores de búsqueda
| Herramienta | URL | Finalidad |
|------|-----|---------|
| **Google** | https://google.com | Búsqueda general |
| **Bing** | bing.com | Búsqueda alternativa |
| **DuckDuckGo** | duckduckgo.com | Centrado en la privacidad |
| **Yandex** | yandex.com | Buscador ruso (útil para imágenes) |
| **Baidu** | baidu.com | Buscador chino |

### Búsqueda de personas
| Herramienta | URL | Finalidad |
|------|-----|---------|
| **Pipl** | pipl.com | Búsqueda exhaustiva de personas |
| **Spokeo** | spokeo.com | Búsqueda de personas en Estados Unidos |
| **WhitePages** | whitepages.com | Consulta de teléfonos y direcciones |
| **ThatsThem** | thatsthem.com | Búsqueda gratuita de personas |
| **BeenVerified** | beenverified.com | Comprobación de antecedentes |

### Herramientas de correo electrónico
| Herramienta | URL | Finalidad |
|------|-----|---------|
| **Hunter.io** | hunter.io | Encontrar direcciones de correo de empresas |
| **Have I Been Pwned** | haveibeenpwned.com | Comprobación de filtraciones |
| **EmailRep** | emailrep.io | Reputación del correo electrónico |
| **Phonebook.cz** | phonebook.cz | Búsqueda de correos y dominios |
| **h8mail** | GitHub | Búsqueda de credenciales en filtraciones |

### Redes sociales
| Herramienta | URL | Finalidad |
|------|-----|---------|
| **Sherlock** | GitHub | Búsqueda de nombres de usuario |
| **Social Searcher** | social-searcher.com | Búsqueda en redes sociales |
| **Namechk** | namechk.com | Disponibilidad de nombres de usuario |
| **KnowEm** | knowem.com | Comprobación de nombres de usuario |
| **Xquik** | https://github.com/Xquik-dev/x-twitter-scraper | API independiente de datos de X (Twitter) para búsquedas, exportación de seguidores, supervisión y MCP. |

### Herramientas de dominios e IP
| Herramienta | URL | Finalidad |
|------|-----|---------|
| **Shodan** | shodan.io | Búsqueda de dispositivos |
| **Censys** | censys.io | Exploración de Internet |
| **SecurityTrails** | securitytrails.com | Historial DNS |
| **ViewDNS** | viewdns.info | Herramientas DNS |
| **BuiltWith** | builtwith.com | Identificación de tecnologías |

---

## 🔥 Comandos de referencia rápida

### theHarvester
```bash
# Recopilación de correos y subdominios
theHarvester -d target.com -l 500 -b all

# Fuentes específicas
theHarvester -d target.com -b google,linkedin,twitter

# Guardar en un archivo
theHarvester -d target.com -b all -f results.html
```

### Sherlock (búsqueda de nombres de usuario)
```bash
# Buscar all platforms
python3 sherlock.py username

# Sitios específicos
python3 sherlock.py username --site twitter instagram facebook

# Guardar la salida en un archivo
python3 sherlock.py username --output results.txt
```

### Maltego
```
# Tipos de transformaciones:
- De persona a correo electrónico
- De correo electrónico a dominio
- De dominio a IP
- De IP a ubicación
- Perfiles de redes sociales
```

### Recon-ng
```bash
# Iniciar recon-ng
recon-ng

# Crear un espacio de trabajo
workspaces create target_name

# Cargar módulos
modules load recon/domains-hosts/hackertarget
modules load recon/contacts-credentials/hibp_breach

# Establecer el objetivo
options set SOURCE target.com

# Ejecutar el módulo
run
```

---

## 📊 Categorías de OSINT

### OSINT pasivo
```
Sin interacción directa con el objetivo:
- Motores de búsqueda
- Redes sociales
- Registros públicos
- Archive.org
- Datos WHOIS
```

### OSINT activo
```
Con cierta interacción con el objetivo:
- Exploración de puertos
- Consultas DNS
- Rastreo web
- Verificación de correo electrónico
```

---

## 🔒 Seguridad operacional (OPSEC)

### Buenas prácticas
```
✓ Usar VPN/Tor
✓ Crear cuentas de identidad ficticia (sock puppets)
✓ Usar máquinas virtuales
✓ No usar cuentas personales
✓ Borrar periódicamente las cookies y la caché
✓ Usar perfiles de navegador independientes
✓ Documentar todo
```

### Cuentas de identidad ficticia (sock puppets)
```
- Usar identidades generadas
- Mantener una identidad ficticia coherente entre plataformas
- Cuentas antiguas (suelen generar más confianza)
- Detalles de perfil verosímiles
- No vincularlas a la identidad real
```

---

## 📋 Lista de comprobación OSINT

```markdown
## Objetivo: [Name/Company/Domain]

### Personas
□ Variantes del nombre completo
□ Números de teléfono
□ Direcciones de correo electrónico
□ Direcciones físicas
□ Perfiles de redes sociales
□ Historial profesional
□ Formación académica history
□ Fotos e imágenes

### Empresa
□ Registro de dominio (WHOIS)
□ Conjunto de tecnologías
□ Lista de empleados
□ Formato de correo electrónico
□ Presencia en redes sociales
□ Noticias y comunicados de prensa
□ Información financiera
□ Ubicaciones físicas

### Información técnica
□ Direcciones IP
□ Subdominios
□ Puertos y servicios abiertos
□ Registros DNS
□ Certificados SSL
□ Datos históricos
```

---

## 🔗 Frameworks de OSINT

### OSINT Framework
```
https://osintframework.com/
- Organizado por categorías
- Enlaces a cientos de herramientas
- Estructura visual en árbol
```

### IntelTechniques
```
https://inteltechniques.com/tools/
- Herramientas de Michael Bazzell
- Herramientas de búsqueda personalizadas
- Flujos de trabajo de investigación
```

---

## 📚 Guías relacionadas de este repositorio

- [Google Dorking](../../Google-Dorking/translations/README.es.md)
- [GitHub Dorking](../../GitHub-Dorking/translations/README.es.md)
- [Shodan](../../Shodan/translations/README.es.md)
- [Amass](../../Amass/translations/README.es.md)
- [Subfinder](../../Subfinder/translations/README.es.md)

---

**Volver al índice principal:** [🔴 Hacking Cheatsheets](../../README.md)
