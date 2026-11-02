# 🎣 Guía rápida de phishing

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Phishing.tr.md) • [🇬🇷 Ελληνικά](Phishing.el.md)

```
██████╗ ██╗  ██╗██╗███████╗██╗  ██╗██╗███╗   ██╗ ██████╗ 
██╔══██╗██║  ██║██║██╔════╝██║  ██║██║████╗  ██║██╔════╝ 
██████╔╝███████║██║███████╗███████║██║██╔██╗ ██║██║  ███╗
██╔═══╝ ██╔══██║██║╚════██║██╔══██║██║██║╚██╗██║██║   ██║
██║     ██║  ██║██║███████║██║  ██║██║██║ ╚████║╚██████╔╝
╚═╝     ╚═╝  ╚═╝╚═╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 📊 Tipos de phishing

| Tipo | Objetivo | Descripción |
|------|--------|-------------|
| **Phishing** | Masivo | Correos genéricos para muchos usuarios |
| **Spear Phishing** | Específico | Dirigido a personas concretas |
| **Whaling** | Directivos | Dirigido a la alta dirección |
| **Clone Phishing** | Cualquiera | Copia de un correo legítimo con contenido malicioso |
| **Vishing** | Cualquiera | Phishing por voz o teléfono |
| **Smishing** | Cualquiera | Phishing por SMS |
| **Pharming** | Cualquiera | Manipulación de DNS para redirigir a un sitio falso |

---

## 🛠️ Configuración y uso de GoPhish

### Instalación
```bash
# Descargar GoPhish
wget https://github.com/gophish/gophish/releases/download/v0.12.1/gophish-v0.12.1-linux-64bit.zip
unzip gophish-v0.12.1-linux-64bit.zip
cd gophish

# Iniciar GoPhish
./gophish

# Acceder al panel de administración
https://localhost:3333
# Credenciales predeterminadas: admin / consultar la salida de la consola
```

### Flujo de configuración de una campaña
```
1. Sending Profile   - Configurar el servidor SMTP
2. Landing Page      - Crear una página de inicio de sesión falsa
3. Email Template    - Diseñar el correo de phishing
4. Users & Groups    - Importar la lista de objetivos
5. Launch Campaign   - Enviar los correos de phishing
6. Monitor Results   - Registrar aperturas, clics y credenciales
```

### Ejemplo de plantilla de correo
```html
<!DOCTYPE html>
<html>
<body>
<p>Dear {{.FirstName}},</p>
<p>Your IT department requires you to verify your account credentials.</p>
<p>Please click the link below to complete verification:</p>
<p><a href="{{.URL}}">Verify Account</a></p>
<p>This action is required within 24 hours.</p>
<br>
<p>IT Support Team</p>
</body>
</html>
```

> **Conservación del ejemplo:** el HTML mantiene sus textos y valores literales en inglés para conservar el payload original; los marcadores `{{.FirstName}}` y `{{.URL}}` pertenecen a GoPhish.

### Ejemplo de página de destino
```html
<!DOCTYPE html>
<html>
<head>
    <title>Microsoft Login</title>
</head>
<body>
    <form method="post" action="">
        <h2>Sign in</h2>
        <input type="email" name="email" placeholder="Email" required>
        <input type="password" name="password" placeholder="Password" required>
        <button type="submit">Sign in</button>
    </form>
</body>
</html>
```

---

## 🔥 Evilginx2 (elusión de 2FA)

> **Alcance técnico:** la expresión «elusión de 2FA» del original no se aplica a todos los factores. FIDO2/WebAuthn proporciona autenticación resistente al phishing; véase la [guía de CISA](https://www.cisa.gov/sites/default/files/2023-01/fact-sheet-implementing-phishing-resistant-mfa-508c.pdf).

### Instalación
```bash
# Instalar Evilginx2
go install github.com/kgretzky/evilginx2@latest

# O descargar el binario
wget https://github.com/kgretzky/evilginx2/releases/...

# Ejecutar
sudo evilginx2
```

> **Compatibilidad:** el original fija GoPhish en `v0.12.1` y utiliza ejemplos históricos de Evilginx2. La URL terminada en `...` es un marcador incompleto, no una descarga utilizable. Comprueba el nombre del binario, la versión y la existencia del phishlet `o365` en el entorno de laboratorio.

### Configuración básica
```bash
# En la consola de Evilginx2
config domain yourdomain.com
config ip YOUR_SERVER_IP

# Listar los phishlets
phishlets

# Habilitar el phishlet de Microsoft 365
phishlets hostname o365 login.yourdomain.com
phishlets enable o365

# Crear un enlace señuelo (lure)
lures create o365
lures get-url 0
```

### Objetivos de los phishlets
```
o365        - Microsoft 365
google      - Google accounts
linkedin    - LinkedIn
twitter     - Twitter/X
github      - GitHub
instagram   - Instagram
```

---

## 📧 Recopilación de direcciones de correo

### theHarvester
```bash
# Recopilar direcciones de correo
theHarvester -d target.com -b all

# Fuentes específicas
theHarvester -d target.com -b google,linkedin,bing

# Guardar la salida en un archivo
theHarvester -d target.com -b all -f output.html
```

### Hunter.io
```bash
# Mediante la API
curl "https://api.hunter.io/v2/domain-search?domain=target.com&api_key=YOUR_KEY"

# Obtener el formato de correo
curl "https://api.hunter.io/v2/email-finder?domain=target.com&first_name=John&last_name=Doe&api_key=YOUR_KEY"
```

### Enumeración en LinkedIn
```bash
# Con linkedin2username
python linkedin2username.py -u USERNAME -c COMPANY_NAME

# Probar formatos de dirección
john.doe@company.com
jdoe@company.com
j.doe@company.com
johnd@company.com
```

---

## 📮 Infraestructura de envío

### Servicios SMTP para pruebas
```
- Amazon SES
- SendGrid
- Mailgun
- SMTP2GO
- Servidor propio (alto riesgo de clasificación como spam)
```

### Elusión de SPF/DKIM/DMARC
```bash
# Comprobar el registro SPF
dig txt target.com | grep spf

# Debilidades de SPF que se pueden aprovechar:
# - "include:" con terceros permisivos
# - "~all" (fallo suave o softfail) en lugar de "-all"
# - Ausencia de registro SPF

# Comprobar DMARC
dig txt _dmarc.target.com
```

### Configuración de dominios para phishing
```bash
# Dominios parecidos (typosquatting)
target.com → tarqet.com
target.com → target-secure.com
target.com → target.co
target.com → target-login.com

# Comprobar la disponibilidad
whois potential-phish-domain.com
```

---

## 📱 Smishing (phishing por SMS)

### Pretextos habituales
```
- Aviso de entrega de un paquete
- Alerta de seguridad bancaria
- Premio o lotería ganada
- Verificación de cuenta
- Código de autenticación de dos factores
```

### Servicios de envío de SMS
```
- Twilio
- Nexmo/Vonage
- Plivo
- AWS SNS
```

### Ejemplos de mensajes
```
[FedEx] Tu paquete está pendiente. Confirma la entrega: http://short.url/abc

[BankName] Se ha detectado actividad inusual. Verifica ahora: http://secure-link.com

[Netflix] Tu pago ha fallado. Actualiza los datos: http://netflix-billing.com
```

---

## 📞 Vishing (phishing por voz)

### Escenarios habituales
```
□ Soporte informático que solicita restablecer una contraseña
□ Departamento de fraude de un banco
□ Organismo público (IRS, agencia tributaria)
□ Estafa de soporte técnico
□ Petición urgente del CEO/CFO
□ Verificación de proveedores
```

### Ejemplo de guion de vishing
```
«Hola, soy [Name], del servicio de soporte informático. Hemos detectado
actividad inusual en tu cuenta y necesitamos verificar tus credenciales
para evitar una brecha de seguridad. ¿Puedes confirmar tu nombre de
usuario y tu contraseña actual para que pueda validar tu cuenta?»
```

### Herramientas
```bash
# Suplantación del identificador de llamada
- SpoofCard
- SpoofTel

# Modificadores de voz
- Voicemod
- Clownfish
```

---

## 🎯 SET (Social Engineering Toolkit)

### Instalación
```bash
git clone https://github.com/trustedsec/social-engineer-toolkit
cd social-engineer-toolkit
pip install -r requirements.txt
sudo python setoolkit
```

### Opciones del menú
```
1) Spear-Phishing Attack Vectors
2) Website Attack Vectors
3) Infectious Media Generator
4) Create a Payload and Listener
5) Mass Mailer Attack
```

### Capturador de credenciales (Credential Harvester)
```bash
# En SET:
1) Social-Engineering Attacks
2) Website Attack Vectors
3) Credential Harvester Attack Method
2) Site Cloner
# Introducir la URL que se va a clonar (p. ej., https://login.microsoft.com)
# SET alojará el sitio falso y capturará credenciales
```

---

## 📝 Plantillas de correos de phishing

### Restablecimiento de contraseña
```
Asunto: [ACCIÓN NECESARIA] Solicitud de restablecimiento de contraseña

Estimado/a {Name}:

Hemos recibido una solicitud para restablecer tu contraseña. Si no la
has realizado, haz clic a continuación para proteger tu cuenta de inmediato.

[Proteger mi cuenta]

Este enlace caducará dentro de 24 horas.

Un saludo,
Equipo de seguridad
```

### Soporte informático
```
Asunto: Mantenimiento programado del sistema - Acción necesaria

Estimado/a {Name}:

Nuestro equipo de TI realizará tareas de mantenimiento programadas.
Para garantizar el acceso sin interrupciones, verifica tus credenciales:

[Verificar ahora]

Si no realizas la verificación, tu cuenta podría quedar bloqueada.

Departamento de TI
```

### Entrega de paquetes
```
Asunto: No se ha podido entregar tu paquete

No se ha podido entregar tu paquete de Amazon.
Confirma tu dirección de entrega:

[Confirmar entrega]

Número de seguimiento: {Random}

Gracias,
Equipo de reparto
```

---

## 📋 Lista de comprobación de una campaña de phishing

```markdown
Antes de la campaña:
□ Definir el alcance y los objetivos (pruebas autorizadas)
□ Recopilar las direcciones de correo de los objetivos
□ Registrar un dominio parecido
□ Preparar la infraestructura SMTP
□ Configurar SPF/DKIM si es posible
□ Crear la página de destino
□ Crear la plantilla de correo

Ejecución de la campaña:
□ Probar la entrega de correos (filtros antispam)
□ Importar la lista de objetivos
□ Programar la campaña
□ Supervisar las tasas de entrega
□ Registrar aperturas, clics y envíos de formularios

Después de la campaña:
□ Generar informes
□ Analizar las tasas de clics
□ Revisar los datos capturados
□ Identificar necesidades de formación
□ Documentar los hallazgos
```

---

## 🔗 Guías rápidas relacionadas

- [Pretexting](./Pretexting.es.md)
- [OSINT - Google Dorking](../../Google-Dorking/translations/README.es.md)

---

**Volver al resumen:** [🎭 Ingeniería social](./README.es.md)
