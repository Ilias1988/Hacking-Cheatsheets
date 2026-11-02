# 📧 Guía rápida de OSINT de correo electrónico

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="Email-OSINT.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="Email-OSINT.el.md">🇬🇷 Ελληνικά</a>
</p>

> **Aclaración:** SMTP no proporciona una prueba concluyente de que un buzón exista: los servidores pueden aceptar destinatarios no válidos, usar catch-all o bloquear la enumeración. Los códigos 250/550 del ejemplo no bastan para determinarlo. Los servicios, planes y resultados de registro por correo dependen del proveedor y pueden cambiar.


```
███████╗███╗   ███╗ █████╗ ██╗██╗     
██╔════╝████╗ ████║██╔══██╗██║██║     
█████╗  ██╔████╔██║███████║██║██║     
██╔══╝  ██║╚██╔╝██║██╔══██║██║██║     
███████╗██║ ╚═╝ ██║██║  ██║██║███████╗
╚══════╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚══════╝
```

---

## 🔍 Herramientas de descubrimiento de correos

| Herramienta | URL | Finalidad |
|------|-----|---------|
| **Hunter.io** | hunter.io | Encontrar direcciones de correo de empresas |
| **Phonebook.cz** | phonebook.cz | Búsqueda gratuita de correos |
| **Snov.io** | snov.io | Buscador de direcciones de correo |
| **Clearbit Connect** | clearbit.com | Enriquecimiento de datos de correo |
| **RocketReach** | rocketreach.co | Buscador de contactos |
| **Voila Norbert** | voilanorbert.com | Consulta de direcciones de correo |

---

## 🛠️ Comandos de enumeración de correos

### theHarvester
```bash
# Recopilación de correos
theHarvester -d target.com -l 500 -b all
theHarvester -d target.com -b google,linkedin,bing
theHarvester -d target.com -b all -f emails.html
```

### Hunter.io CLI
```bash
# Consulta de API
curl "https://api.hunter.io/v2/domain-search?domain=target.com&api_key=YOUR_KEY"

# Verificación de correo electrónico
curl "https://api.hunter.io/v2/email-verifier?email=test@target.com&api_key=YOUR_KEY"
```

### h8mail (búsqueda en filtraciones)
```bash
pip3 install h8mail
h8mail -t target@email.com
h8mail -t target@email.com -c h8mail_config.ini
```

### holehe (comprobación de registros)
```bash
pip3 install holehe
holehe target@email.com
holehe target@email.com --only-used
```

---

## 📧 Patrones de formato de correo electrónico

```bash
# Formatos corporativos habituales
first.last@company.com     # john.smith@company.com
flast@company.com          # jsmith@company.com
firstl@company.com         # johns@company.com
first_last@company.com     # john_smith@company.com
first@company.com          # john@company.com
last.first@company.com     # smith.john@company.com
```

### Generador de combinaciones de correo
```python
# Generar variantes de correo
names = ["john", "smith"]
domain = "company.com"

patterns = [
    f"{names[0]}.{names[1]}@{domain}",
    f"{names[0][0]}{names[1]}@{domain}",
    f"{names[0]}{names[1][0]}@{domain}",
    f"{names[0]}_{names[1]}@{domain}",
    f"{names[0]}@{domain}",
]
```

---

## 🔐 Comprobación de filtraciones

| Servicio | URL | Tipo |
|---------|-----|------|
| **Have I Been Pwned** | haveibeenpwned.com | Gratuito |
| **DeHashed** | dehashed.com | De pago |
| **LeakCheck** | leakcheck.io | De pago |
| **Snusbase** | snusbase.com | De pago |
| **IntelX** | intelx.io | Freemium |

### HIBP API
```bash
curl -H "hibp-api-key: YOUR_KEY" \
  "https://haveibeenpwned.com/api/v3/breachedaccount/test@example.com"
```

---

## ✅ Verificación de correo electrónico

### Métodos de verificación
```
1. Comprobación de registros MX: el dominio tiene servidor de correo
2. Verificación SMTP: existencia del buzón
3. Comprobación de sintaxis: formato de correo válido
4. Comprobación de correo desechable: no es temporal
5. Comprobación de cuentas de función: no es una cuenta genérica (info@, admin@)
```

### Comprobación SMTP sencilla
```bash
# Comprobar los registros MX
nslookup -type=mx target.com
dig mx target.com

# Verificación mediante Telnet
telnet mail.target.com 25
HELO test.com
MAIL FROM:<test@test.com>
RCPT TO:<target@target.com>
# 250 = existe, 550 = no existe (simplificación del original)
```

---

## 📋 Lista de comprobación de OSINT de correo

```markdown
## Correo objetivo: [email@domain.com]

□ Verificar la existencia del correo (SMTP/Hunter)
□ Comprobar filtraciones de datos (HIBP)
□ Encontrar cuentas asociadas (holehe)
□ Buscar en Google/Bing
□ Comprobar registros en redes sociales
□ Buscar perfiles públicos
□ Buscar en sitios para compartir texto (paste sites)
□ Comprobar redes profesionales
```

---

**Volver a OSINT:** [🔍 Índice de OSINT](./README.es.md)
