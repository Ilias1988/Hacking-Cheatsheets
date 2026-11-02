# 🎭 Guías rápidas de ingeniería social

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
███████╗ ██████╗  ██████╗██╗ █████╗ ██╗         ███████╗███╗   ██╗ ██████╗ ██╗███╗   ██╗███████╗███████╗██████╗ ██╗███╗   ██╗ ██████╗ 
██╔════╝██╔═══██╗██╔════╝██║██╔══██╗██║         ██╔════╝████╗  ██║██╔════╝ ██║████╗  ██║██╔════╝██╔════╝██╔══██╗██║████╗  ██║██╔════╝ 
███████╗██║   ██║██║     ██║███████║██║         █████╗  ██╔██╗ ██║██║  ███╗██║██╔██╗ ██║█████╗  █████╗  ██████╔╝██║██╔██╗ ██║██║  ███╗
╚════██║██║   ██║██║     ██║██╔══██║██║         ██╔══╝  ██║╚██╗██║██║   ██║██║██║╚██╗██║██╔══╝  ██╔══╝  ██╔══██╗██║██║╚██╗██║██║   ██║
███████║╚██████╔╝╚██████╗██║██║  ██║███████╗    ███████╗██║ ╚████║╚██████╔╝██║██║ ╚████║███████╗███████╗██║  ██║██║██║ ╚████║╚██████╔╝
╚══════╝ ╚═════╝  ╚═════╝╚═╝╚═╝  ╚═╝╚══════╝    ╚══════╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝╚═╝  ╚═══╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 ¿Qué es la ingeniería social?

La ingeniería social consiste en manipular a las personas para que revelen información confidencial o realicen acciones que comprometan la seguridad. Aprovecha la psicología humana en lugar de vulnerabilidades técnicas.

**Principios clave:**
- 🎭 **Autoridad** - Las personas confían en las figuras de autoridad
- ⏰ **Urgencia** - La presión del tiempo reduce el pensamiento crítico
- 🤝 **Reciprocidad** - Las personas sienten la obligación de devolver favores
- 👥 **Prueba social** - Las personas siguen el comportamiento de los demás
- 😊 **Simpatía** - Las personas acceden a las peticiones de quienes les caen bien
- 📉 **Escasez** - La disponibilidad limitada aumenta el deseo

---

> **Terminología:** *phishing* es el engaño mediante mensajes para obtener información; *pretexting* es la creación de un pretexto creíble. MITM significa intermediario entre cliente y servidor; 2FA es autenticación de dos factores y OSINT es inteligencia de fuentes abiertas.

## 📖 Guías de ingeniería social

| Tema | Descripción | Guía |
|-------|-------------|-------|
| **Phishing** | Técnicas y herramientas de phishing por correo, SMS y voz | [📄 Ver](./Phishing.es.md) |
| **Pretexting** | Creación de escenarios e identidades creíbles | [📄 Ver](./Pretexting.es.md) |

---

## 🛠️ Herramientas esenciales de ingeniería social

| Herramienta | Finalidad | Tipo |
|------|---------|------|
| **GoPhish** | Framework para campañas de phishing | Phishing |
| **SET (Social Engineering Toolkit)** | Framework completo de ingeniería social | Uso general |
| **Evilginx2** | Phishing mediante intermediario MITM (elusión de 2FA) | Phishing |
| **King Phisher** | Plataforma para campañas de phishing | Phishing |
| **Modlishka** | Phishing mediante proxy inverso | Phishing |
| **theHarvester** | Recopilación de correos y dominios | Reconocimiento |
| **Herramientas de LinkedIn** | Enumeración de empleados | OSINT |
| **Maltego** | OSINT y mapeo de relaciones | Reconocimiento |

### Instalación rápida de herramientas
```bash
# GoPhish
wget https://github.com/gophish/gophish/releases/download/v0.12.1/gophish-v0.12.1-linux-64bit.zip
unzip gophish-v0.12.1-linux-64bit.zip
./gophish

# SET (Social Engineering Toolkit)
git clone https://github.com/trustedsec/social-engineer-toolkit
cd social-engineer-toolkit
pip install -r requirements.txt
python setoolkit

# Evilginx2
go install github.com/kgretzky/evilginx2@latest

# theHarvester
pip install theHarvester
theHarvester -d target.com -b all
```

---

## 📊 Tipos de ataques de ingeniería social

| Tipo de ataque | Descripción | Vector |
|-------------|-------------|--------|
| **Phishing** | Correos engañosos para robar credenciales | Email |
| **Spear Phishing** | Phishing dirigido a personas concretas | Email |
| **Whaling** | Phishing dirigido a altos directivos | Email |
| **Vishing** | Ingeniería social mediante llamadas de voz | Teléfono |
| **Smishing** | Phishing mediante SMS | SMS |
| **Pretexting** | Creación de escenarios falsos para obtener información | Cualquiera |
| **Baiting** | Abandono de soportes infectados (USB, etc.) | Físico |
| **Quid Pro Quo** | Ofrecer algo a cambio de información | Cualquiera |
| **Tailgating** | Seguir a una persona autorizada para entrar en una zona restringida | Físico |
| **Watering Hole** | Comprometer sitios web que visitan los objetivos | Web |

---

## 🔗 Referencia rápida: indicadores de phishing

### Señales de alerta en los correos
```
□ El correo del remitente no coincide con el dominio de la empresa
□ Saludos genéricos («Estimado cliente»)
□ Urgencia o amenazas
□ Errores de ortografía o gramática
□ Enlaces sospechosos (pasar el cursor para comprobarlos)
□ Archivos adjuntos inesperados
□ Petición de información sensible
□ Ofertas demasiado buenas para ser ciertas
```

### Análisis de URL
```bash
# Comprobar la antigüedad del dominio
whois suspicious-domain.com

# Comprobar la reputación
https://www.virustotal.com
https://urlscan.io

# Analizar URL acortadas
https://checkshorturl.com
```

---

## 📚 Recursos

- [OWASP Social Engineering](https://owasp.org/www-community/attacks/Social_Engineering)
- [MITRE ATT&CK - Initial Access](https://attack.mitre.org/tactics/TA0001/)
- [Phishing.org](https://www.phishing.org/)
- [Social Engineering Framework](https://www.social-engineer.org/)

---

<p align="center">
  <b>🎭 ¡Utiliza la ingeniería social únicamente en pruebas autorizadas!</b>
</p>
