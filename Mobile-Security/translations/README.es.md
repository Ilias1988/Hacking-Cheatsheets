# 📱 Guías de referencia de seguridad móvil

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
███╗   ███╗ ██████╗ ██████╗ ██╗██╗     ███████╗    ███████╗███████╗ ██████╗██╗   ██╗██████╗ ██╗████████╗██╗   ██╗
████╗ ████║██╔═══██╗██╔══██╗██║██║     ██╔════╝    ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██║╚══██╔══╝╚██╗ ██╔╝
██╔████╔██║██║   ██║██████╔╝██║██║     █████╗      ███████╗█████╗  ██║     ██║   ██║██████╔╝██║   ██║    ╚████╔╝ 
██║╚██╔╝██║██║   ██║██╔══██╗██║██║     ██╔══╝      ╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██║   ██║     ╚██╔╝  
██║ ╚═╝ ██║╚██████╔╝██████╔╝██║███████╗███████╗    ███████║███████╗╚██████╗╚██████╔╝██║  ██║██║   ██║      ██║   
╚═╝     ╚═╝ ╚═════╝ ╚═════╝ ╚═╝╚══════╝╚══════╝    ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝   ╚═╝      ╚═╝   
```

---

## 🎯 Pruebas de seguridad de aplicaciones móviles

Las pruebas de seguridad de aplicaciones móviles consisten en identificar vulnerabilidades en:
- 📦 **Paquete de la aplicación** - Análisis de APK/IPA
- 💾 **Almacenamiento de datos** - Almacenamiento local inseguro
- 🔐 **Autenticación** - Mecanismos de autenticación débiles
- 🌐 **Red** - Comunicaciones inseguras
- 🔑 **Criptografía** - Cifrado débil
- 🛡️ **Plataforma** - Vulnerabilidades específicas del sistema operativo

---

## 📖 Guías de seguridad móvil

| Plataforma | Descripción | Guía |
|----------|-------------|-------|
| **Android** | Análisis de APK, Frida y evasión de la detección de root | [📄 Ver](./Android-Pentesting.es.md) |
| **iOS** | Análisis de IPA, jailbreak y Objection | [📄 Ver](./iOS-Pentesting.es.md) |

---

## 🛠️ Herramientas esenciales de seguridad móvil

| Herramienta | Plataforma | Finalidad |
|------|----------|---------|
| **Frida** | Ambas | Instrumentación dinámica |
| **Objection** | Ambas | Exploración móvil durante la ejecución |
| **MobSF** | Ambas | Análisis de seguridad automatizado |
| **jadx** | Android | Descompilación de APK |
| **apktool** | Android | Ingeniería inversa de APK |
| **Burp Suite** | Ambas | Interceptación de tráfico |
| **Ghidra** | Ambas | Análisis de binarios |
| **drozer** | Android | Evaluación de seguridad |

### Instalación rápida de herramientas
```bash
# Frida
pip install frida-tools

# Objection
pip install objection

# MobSF (Docker)
docker pull opensecurity/mobile-security-framework-mobsf
docker run -p 8000:8000 opensecurity/mobile-security-framework-mobsf

# jadx
# Descargar desde https://github.com/skylot/jadx/releases

# apktool
# Descargar desde https://ibotpeaches.github.io/Apktool/install/

# drozer
pip install drozer
```

---

## 📊 OWASP Mobile Top 10 (2024)

| # | Vulnerabilidad | Descripción |
|---|---------------|-------------|
| M1 | Uso inadecuado de credenciales | Credenciales incrustadas en el código y almacenamiento inseguro |
| M2 | Seguridad insuficiente de la cadena de suministro | SDK maliciosos y bibliotecas comprometidas |
| M3 | Autenticación/autorización inseguras | Autenticación débil y control de acceso defectuoso |
| M4 | Validación insuficiente de entradas y salidas | Inyección y XSS en WebViews |
| M5 | Comunicaciones inseguras | Ausencia de TLS y problemas con certificados |
| M6 | Controles de privacidad insuficientes | Filtración de datos personales (PII) y permisos excesivos |
| M7 | Protecciones insuficientes de binarios | Ausencia de ofuscación y manipulación del binario |
| M8 | Configuración de seguridad incorrecta | Depuración habilitada y valores predeterminados inseguros |
| M9 | Almacenamiento de datos inseguro | Almacenamiento en texto claro y registros |
| M10 | Criptografía insuficiente | Algoritmos débiles y claves incrustadas en el código |

---

## 🔗 Referencia rápida: comandos de Frida

> **Versiones de Frida:** `-f` inicia un proceso; no se limita a conectarse a uno existente. `--no-pause` aparece en ejemplos antiguos y puede no existir en la versión instalada. Consulta la ayuda local y el [código oficial de frida-tools](https://github.com/frida/frida-tools/blob/main/frida_tools/repl.py).

```bash
# Listar procesos en ejecución
frida-ps -U

# Conectarse al proceso
frida -U -f com.app.package

# Iniciar un proceso con un script
frida -U -f com.app.package -l script.js --no-pause

# Explorar con Objection
objection -g com.app.package explore
```

---

## 📚 Recursos

- [OWASP Seguridad móvil](https://owasp.org/www-project-mobile-security/)
- [OWASP MASTG](https://mas.owasp.org/MASTG/)
- [Documentación de Frida](https://frida.re/docs/)
- [HackTricks Mobile](https://book.hacktricks.xyz/mobile-pentesting)

---

<p align="center">
  <b>📱 ¡Realiza pruebas de aplicaciones móviles de forma responsable!</b>
</p>
