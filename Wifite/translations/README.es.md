# 📶 Wifite: guía rápida del auditor WiFi automatizado

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)


> **Precisiones del original:** ESSID es el nombre de red; BSSID identifica el punto de acceso (AP); handshake es el intercambio de autenticación. WPS es un mecanismo de configuración, no un tipo de cifrado. Un ataque PMKID captura un identificador derivado de la PMK, no la propia clave maestra, y solo funciona cuando el AP y su configuración permiten obtenerlo. No puede suponerse que funcione en la mayoría de redes. Las opciones, selección interactiva, unidades de señal, archivos y rutas dependen de la versión y del paquete: el [código de Wifite2](https://github.com/derv82/wifite2/blob/master/wifite/config.py) contempla `hs` y `cracked.txt` relativos al directorio de trabajo; la ruta `~/.config/wifite/` del original no es universal. Véase también su [definición de argumentos](https://github.com/derv82/wifite2/blob/master/wifite/args.py). Repetir `--dict` o `--essid` no garantiza acumular valores. El modo silencioso reduce la salida, no vuelve invisible el tráfico.


```
  ██╗    ██╗██╗███████╗██╗████████╗███████╗
  ██║    ██║██║██╔════╝██║╚══██╔══╝██╔════╝
  ██║ █╗ ██║██║█████╗  ██║   ██║   █████╗  
  ██║███╗██║██║██╔══╝  ██║   ██║   ██╔══╝  
  ╚███╔███╔╝██║██║     ██║   ██║   ███████╗
   ╚══╝╚══╝ ╚═╝╚═╝     ╚═╝   ╚═╝   ╚══════╝
         Herramienta automatizada de pruebas de seguridad WiFi
```

<p align="center">
  <img src="https://img.shields.io/badge/Wifite-Automated_WiFi-blue?style=for-the-badge" alt="Wifite">
  <img src="https://img.shields.io/badge/WPA2-Cracking-red?style=for-the-badge" alt="WPA2">
  <img src="https://img.shields.io/badge/Python-yellow?style=for-the-badge" alt="Python">
</p>

---

## 📋 Índice

- [Qué es Wifite](#qué-es-wifite)
- [Instalación](#instalación)
- [Uso básico](#uso-básico)
- [Selección de objetivos](#selección-de-objetivos)
- [Opciones de ataque](#opciones-de-ataque)
- [Ataques WPS](#ataques-wps)
- [Ataques PMKID](#ataques-pmkid)
- [Opciones avanzadas](#opciones-avanzadas)
- [Opciones de recuperación de contraseñas](#opciones-de-recuperación-de-contraseñas)
- [Ejemplos](#ejemplos)
- [Referencia rápida](#referencia-rápida)

---

<a id="qué-es-wifite"></a>

## 🎯 Qué es Wifite

**Wifite** (Wifite2) es una herramienta automatizada de auditoría inalámbrica que:

- 🤖 **Automatiza ataques**: no hace falta escribir los comandos manualmente
- 🎯 **Admite varios objetivos**: permite atacar varias redes
- 🔓 **WPA/WPA2/WEP/WPS**: todos los tipos de cifrado, según el original
- 💾 **Guarda el progreso**: permite reanudar ataques interrumpidos
- 🔄 **Captura PMKID**: ataques modernos sin cliente conectado

### Wifite frente a Wifite2

| Característica | Wifite (original) | Wifite2 |
|---------|-------------------|---------|
| Mantenimiento | No | Sí |
| Python | Python 2 | Python 3 |
| PMKID | No | Sí |
| Código | Desordenado | Reescritura limpia |
| Uso | No usar | **Recomendado** |

### Funciones

| Función | Descripción |
|---------|-------------|
| **Automatizado** | Auditoría WiFi sencilla y automatizada |
| **Selección inteligente** | Selecciona objetivos según la intensidad de señal |
| **Todos los ataques** | WPA, WEP, WPS, PMKID |
| **Recuperación de contraseñas** | Se integra con Hashcat y aircrack |
| **Reanudable** | Continuar sesiones interrumpidas |

---

<a id="instalación"></a>

## 🚀 Instalación

### Kali Linux (preinstalado)

```bash
# Ya instalado en Kali
wifite --help

# Actualizar a la última versión
sudo apt update && sudo apt install wifite
```

### Instalar Wifite2 desde GitHub

```bash
# Clonar el repositorio
git clone https://github.com/derv82/wifite2.git
cd wifite2

# Ejecutar directamente
sudo python3 wifite.py

# O instalar
sudo python3 setup.py install
```

### Dependencias

```bash
# Necesario
sudo apt install aircrack-ng

# Recomendado para disponer de todas las funciones
sudo apt install reaver bully pixiewps hashcat hcxdumptool hcxtools
```

### Verificar la instalación

```bash
wifite --help
wifite --version
```

---

<a id="uso-básico"></a>

## 💻 Uso básico

### Inicio rápido

```bash
# Ejecutar wifite (detectará automáticamente la interfaz)
sudo wifite

# Especificar la interfaz
sudo wifite -i wlan0
```

### Flujo de trabajo básico

```
1. Ejecutar wifite
2. Esperar a que aparezcan las redes
3. Pulsar Ctrl+C para detener la exploración
4. Seleccionar los objetivos por número
5. Wifite ejecuta los ataques automáticamente
6. Recuperar contraseñas a partir de los handshakes capturados
```

### Modo interactivo

```bash
# Iniciar wifite
sudo wifite

# La salida muestra:
   NUM  ESSID              ENCR  POWER  WPS  CLIENT
   ---  -----------------  ----  -----  ---  ------
    1   HomeNetwork        WPA2   -45   Yes     2
    2   Office_WiFi        WPA2   -52   No      0
    3   Free_Hotspot       OPN    -60   No      5

# Introducir los números de objetivo o Ctrl+C para seleccionar todos los visibles
```

---

<a id="selección-de-objetivos"></a>

## 🎯 Selección de objetivos

### Seleccionar por número

```bash
# Después de explorar, seleccionar los objetivos por número
# Ejemplo: seleccionar los objetivos 1 y 3
1, 3

# Seleccionar un intervalo
1-5

# Seleccionar todos
all
```

### Filtrar por ESSID

```bash
# Elegir un nombre de red específico
sudo wifite --essid "TargetNetwork"

# Varios ESSID
sudo wifite --essid "Network1" --essid "Network2"
```

### Filtrar por BSSID

```bash
# Elegir una dirección MAC específica
sudo wifite --bssid AA:BB:CC:DD:EE:FF
```

### Filtrar por cifrado

```bash
# Solo redes WPA
sudo wifite --wpa

# Solo redes WEP
sudo wifite --wep

# Solo redes con WPS activado
sudo wifite --wps
```

### Filtrar por canal

```bash
# Canal específico
sudo wifite -c 6

# Solo 5 GHz
sudo wifite --5ghz

# Solo 2,4 GHz
sudo wifite --2ghz
```

### Filtrar por señal

```bash
# Potencia mínima (dBm; cuanto más cerca de 0, más fuerte)
sudo wifite --power -50
```

---

<a id="opciones-de-ataque"></a>

## ⚔️ Opciones de ataque

### Opciones WPA/WPA2

```bash
# Establecer el tiempo de espera de desautenticación
sudo wifite --wpa-deauth-timeout 30

# Número de desautenticaciones que se enviarán
sudo wifite --num-deauths 5

# Omitir la captura de handshakes y usar solo PMKID
sudo wifite --pmkid
```

### Opciones de desautenticación

```bash
# Número de paquetes de desautenticación
sudo wifite --num-deauths 10

# Desautenticación continua
sudo wifite --num-deauths 0
```

### Orden de los ataques

```bash
# Omitir ataques WPA
sudo wifite --no-wpa

# Omitir ataques WEP
sudo wifite --no-wep

# Omitir ataques WPS
sudo wifite --no-wps

# Omitir ataques PMKID
sudo wifite --no-pmkid
```

### Finalizar NetworkManager

```bash
# Finalizar automáticamente los procesos que interfieren
sudo wifite --kill

# No finalizar procesos
sudo wifite --no-kill
```

---

<a id="ataques-wps"></a>

## 🔓 Ataques WPS

### Activar WPS

```bash
# Atacar solo redes con WPS activado
sudo wifite --wps

# Modo exclusivo de WPS
sudo wifite --wps-only
```

### Herramientas WPS

```bash
# Usar Reaver (predeterminado)
sudo wifite --wps --reaver

# Usar Bully
sudo wifite --wps --bully
```

### Ataque Pixie-Dust

```bash
# Activar Pixie-Dust (ataque WPS sin conexión durante la fase de cálculo)
sudo wifite --wps --pixie

# Tiempo de espera de Pixie-Dust
sudo wifite --wps --pixie-timeout 300
```

### Opciones WPS

```bash
# Tiempo de espera WPS por intento de PIN
sudo wifite --wps-time 120

# Número máximo de reintentos WPS
sudo wifite --wps-fails 10

# Tiempo de espera WPS total
sudo wifite --wps-timeout 300
```

---

<a id="ataques-pmkid"></a>

## 🔐 Ataques PMKID

### ¿Qué es PMKID?

Ataque PMKID (Pairwise Master Key Identifier, identificador de la clave maestra por pares):
- ✅ Captura la PMK directamente del AP (afirmación del original)
- ✅ No necesita un cliente conectado
- ✅ No requiere desautenticación
- ✅ Funciona en la mayoría de redes WPA2 (afirmación del original)

### Opciones PMKID

```bash
# Solo ataques PMKID (sin handshake)
sudo wifite --pmkid

# Tiempo de espera de PMKID
sudo wifite --pmkid-timeout 120
```

### Cómo funciona PMKID

```
1. Enviar una solicitud de asociación al AP
2. El AP responde con un PMKID en una trama EAPOL
3. Capturar el PMKID
4. Recuperar la contraseña sin conexión con hashcat
```

---

<a id="opciones-avanzadas"></a>

## ⚙️ Opciones avanzadas

### Opciones de interfaz

```bash
# Especificar la interfaz
sudo wifite -i wlan0

# Especificar la interfaz en modo monitor
sudo wifite -i wlan0mon
```

### Opciones de salida

```bash
# Salida detallada
sudo wifite -v
sudo wifite --verbose

# Salida muy detallada
sudo wifite -vv

# Modo silencioso
sudo wifite --quiet
```

### Opciones de tiempos

```bash
# Tiempo de exploración (segundos)
sudo wifite --scan-time 30

# Tiempo de espera total por objetivo
sudo wifite --attack-timeout 300
```

### Contraseñas recuperadas

```bash
# Las contraseñas recuperadas se guardan en:
# ~/.config/wifite/cracked.txt

# Ver las redes con contraseñas recuperadas
cat ~/.config/wifite/cracked.txt

# O especificar un archivo personalizado
sudo wifite --cracked cracked_passwords.txt
```

### Opciones de omisión

```bash
# Omitir la recuperación de contraseñas (solo capturar)
sudo wifite --skip-crack

# Omitir objetivos cuyas contraseñas ya se han recuperado
sudo wifite --skip-cracked
```

### MAC aleatoria

```bash
# Aleatorizar la dirección MAC
sudo wifite --random-mac

# No cambiar la dirección MAC
sudo wifite --no-random-mac
```

---

<a id="opciones-de-recuperación-de-contraseñas"></a>

## 🔓 Opciones de recuperación de contraseñas

### Ataque de diccionario

```bash
# Especificar la lista de palabras
sudo wifite --dict /usr/share/wordlists/rockyou.txt

# Varias listas de palabras
sudo wifite --dict wordlist1.txt --dict wordlist2.txt
```

### Integración con Hashcat

```bash
# Usar hashcat para recuperar contraseñas
sudo wifite --hashcat

# Ubicación de Hashcat
sudo wifite --hashcat-path /usr/bin/hashcat
```

### Opciones de Aircrack-ng

```bash
# Usar aircrack-ng (predeterminado)
sudo wifite --aircrack

# Ruta de Aircrack-ng
sudo wifite --aircrack-path /usr/bin/aircrack-ng
```

### Modo de recuperación de contraseñas únicamente

```bash
# Recuperar contraseñas a partir de archivos de handshake existentes
sudo wifite --crack

# Especificar el archivo de captura
sudo wifite --crack --dict wordlist.txt /path/to/capture.cap
```

---

<a id="ejemplos"></a>

## 📋 Ejemplos

### Auditoría WiFi básica

```bash
# Explorar y atacar todas las redes
sudo wifite

# Atacar con una lista de palabras personalizada
sudo wifite --dict /usr/share/wordlists/rockyou.txt
```

### Elegir una red específica

```bash
# Elegir por nombre
sudo wifite --essid "TargetNetwork" --dict rockyou.txt

# Elegir por MAC
sudo wifite --bssid AA:BB:CC:DD:EE:FF
```

### Ataque exclusivo de WPA2

```bash
# Solo redes WPA2 con PMKID
sudo wifite --wpa --pmkid --dict rockyou.txt
```

### Ataque WPS

```bash
# WPS con Pixie-Dust
sudo wifite --wps --pixie

# Solo fuerza bruta de WPS
sudo wifite --wps-only --bully
```

### Solo señal fuerte

```bash
# Atacar solo redes con señal fuerte
sudo wifite --power -50 --wpa
```

### Redes de 5 GHz

```bash
# Atacar solo la banda de 5 GHz
sudo wifite --5ghz --wpa
```

### Auditoría rápida (sin recuperar contraseñas)

```bash
# Capturar solo handshakes y recuperar contraseñas después
sudo wifite --skip-crack
```

### Reanudar el ataque

```bash
# Wifite guarda automáticamente el progreso
# Volver a ejecutarlo para reanudar
sudo wifite

# Omitir las contraseñas ya recuperadas
sudo wifite --skip-cracked
```

### Modo detallado

```bash
# Ver todos los detalles
sudo wifite -vv --dict rockyou.txt
```

### Modo de menor visibilidad

```bash
# Desautenticaciones mínimas y modo silencioso
sudo wifite --num-deauths 1 --quiet
```

---

<a id="referencia-rápida"></a>

## 📊 Referencia rápida

### Comandos esenciales

| Finalidad | Comando |
|---------|---------|
| Exploración básica | `sudo wifite` |
| Elegir por nombre | `sudo wifite --essid "Name"` |
| Elegir por MAC | `sudo wifite --bssid MAC` |
| Solo WPA | `sudo wifite --wpa` |
| Solo WPS | `sudo wifite --wps-only` |
| Ataque PMKID | `sudo wifite --pmkid` |
| Con una lista de palabras | `sudo wifite --dict wordlist.txt` |
| Omitir la recuperación de contraseñas | `sudo wifite --skip-crack` |

### Tipos de ataque

| Ataque | Opción | Objetivo |
|--------|------|--------|
| Handshake WPA | `--wpa` | WPA/WPA2-PSK |
| PMKID | `--pmkid` | WPA/WPA2-PSK |
| WPS Pixie | `--wps --pixie` | WPS activado |
| Fuerza bruta de WPS | `--wps-only` | WPS activado |
| WEP | `--wep` | WEP (obsoleto) |

### Listas de palabras habituales

| Ruta | Descripción |
|------|-------------|
| `/usr/share/wordlists/rockyou.txt` | Contraseñas más habituales |
| `/usr/share/wordlists/fasttrack.txt` | Comprobación rápida |
| `/usr/share/seclists/Passwords/WiFi-WPA/` | Específica de WiFi |

### Archivos capturados

```
~/.config/wifite/
├── cracked.txt          # Contraseñas recuperadas
├── hs/                  # Capturas de handshakes
│   └── handshake_*.cap
└── pmkid/               # Capturas PMKID
    └── pmkid_*.22000
```

### Referencia rápida de opciones

| Opción | Descripción |
|------|-------------|
| `-i` | Interfaz |
| `-c` | Canal |
| `-v` | Detallado |
| `--wpa` | Solo WPA/WPA2 |
| `--wep` | Solo WEP |
| `--wps` | WPS activado |
| `--pmkid` | Ataque PMKID |
| `--dict` | Lista de palabras |
| `--kill` | Finalizar procesos |
| `--power` | Señal mínima |
| `--5ghz` | Banda de 5 GHz |
| `--2ghz` | Banda de 2,4 GHz |

### Resolución de problemas

```bash
# No se encuentra la interfaz
sudo airmon-ng check kill
sudo wifite -i wlan0

# No se encuentran redes
# - Comprobar que el adaptador admite el modo monitor
# - Probar otras bandas: --5ghz o --2ghz
# - Acercarse a los objetivos

# No se ha capturado el handshake
# - Aumentar las desautenticaciones: --num-deauths 10
# - Esperar más: --wpa-deauth-timeout 60
# - Probar con distintos clientes

# Falla la recuperación de contraseñas
# - Usar una lista de palabras mejor
# - Probar hashcat con GPU: --hashcat
```

---

## 🔄 Wifite frente al uso manual de Aircrack-ng

| Tarea | Wifite | Suite Aircrack-ng |
|------|--------|-------------------|
| Modo monitor | Automático | `airmon-ng start wlan0` |
| Exploración | Automático | `airodump-ng wlan0mon` |
| Objetivo | Interactivo | Filtrado manual |
| Desautenticación | Automático | `aireplay-ng -0 ...` |
| Captura | Automático | `airodump-ng -w ...` |
| Recuperación de contraseñas | Automático | `aircrack-ng -w ...` |
| **Esfuerzo** | **Sencillo** | Manual |

---

## ⚠️ Aviso legal

```
⚠️ ADVERTENCIA: Usar Wifite solo en redes PROPIAS o con
AUTORIZACIÓN EXPRESA POR ESCRITO para realizar pruebas.

✅ Pruebas de penetración autorizadas
✅ Auditoría de seguridad (con permiso)
✅ Fines educativos (entorno de laboratorio)

❌ El acceso a redes sin autorización es ILEGAL
❌ No usar para fines maliciosos
```

---

## 📚 Recursos

- [Wifite2 GitHub](https://github.com/derv82/wifite2)
- [Wifite Wiki](https://github.com/derv82/wifite2/wiki)
- [Aircrack-ng](https://www.aircrack-ng.org/)

### Guías rápidas relacionadas
- [Aircrack-ng](../../Aircrack-ng/translations/README.es.md)
- [Hashcat](../../Hashcat/translations/README.es.md)
- [Comandos de Linux](../../Linux-Commands/translations/README.es.md)

---

<p align="center">
  <b>📶 ¡Auditoría WiFi automatizada!</b><br>
  <i>Wifite: apunta, haz clic y realiza pruebas (¡de forma ética!)</i>
</p>
