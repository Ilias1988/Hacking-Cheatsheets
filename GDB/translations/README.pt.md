# 🐛 GDB — Guia de referência do depurador GNU

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗ ██████╗ ██████╗ 
  ██╔════╝ ██╔══██╗██╔══██╗
  ██║  ███╗██║  ██║██████╔╝
  ██║   ██║██║  ██║██╔══██╗
  ╚██████╔╝██████╔╝██████╔╝
   ╚═════╝ ╚═════╝ ╚═════╝ 
    GNU Debugger
```

<p align="center">
  <img src="https://img.shields.io/badge/GDB-Debugger-blue?style=for-the-badge" alt="GDB">
  <img src="https://img.shields.io/badge/Reverse_Engineering-green?style=for-the-badge" alt="RE">
  <img src="https://img.shields.io/badge/Exploit_Dev-red?style=for-the-badge" alt="Exploit">
</p>

---

## 📋 Índice

- [O que é o GDB](#-what-is-gdb)
- [Instalação e configuração](#-installation--setup)
- [Utilização básica](#-basic-usage)
- [Pontos de paragem](#-breakpoints)
- [Controlo da execução](#-execution-control)
- [Inspeção da memória](#-examining-memory)
- [Registos do processador](#-registers)
- [Análise da pilha](#-stack-analysis)
- [GEF/PEDA/pwndbg](#-gefpedapwndbg)
- [Desenvolvimento de exploits](#-exploit-development)
- [Referência rápida](#-quick-reference)

---

<a id="-what-is-gdb"></a>
## 🎯 O que é o GDB

**GDB** (GNU Debugger) é o depurador de referência em Linux. É essencial para:

- 🐛 **Depuração** — Encontrar e corrigir erros
- 🔍 **Engenharia inversa** — Compreender o comportamento de binários
- 💥 **Desenvolvimento de exploits** — Transbordamentos de buffers e ROP
- 📊 **Análise de binários** — Examinar o fluxo de execução
- 🧪 **Desafios CTF** — Resolver desafios de engenharia inversa e pwn

Um breakpoint é um ponto de paragem; um watchpoint vigia alterações ou acessos a dados. A stack é a pilha de chamadas, organizada em quadros (frames); a heap é a zona usada na alocação dinâmica de memória. ROP combina pequenas sequências de instruções, chamadas gadgets. Desassemblar é representar código de máquina em assembly, não descompilar para uma linguagem de alto nível.

### Arquiteturas suportadas

| Arquitetura | Notas |
|--------------|-------|
| x86 | Intel de 32 bits |
| x86-64 | Intel/AMD de 64 bits |
| ARM | ARM de 32 bits |
| ARM64 | ARM de 64 bits |
| MIPS | Processadores MIPS |
| PowerPC | Processadores PPC |

---

<a id="-installation--setup"></a>
## 🚀 Instalação e configuração

### Instalar o GDB

```bash
# Debian/Ubuntu/Kali
sudo apt install gdb

# Fedora/RHEL
sudo dnf install gdb

# Arch Linux
sudo pacman -S gdb

# macOS (utilizar lldb em alternativa, ou)
brew install gdb
```

### Instalar o GEF (GDB Enhanced Features)

```bash
# GEF — A opção mais recomendada
bash -c "$(curl -fsSL https://gef.blah.cat/sh)"

# Ou manualmente
wget -O ~/.gdbinit-gef.py -q https://gef.blah.cat/py
echo source ~/.gdbinit-gef.py >> ~/.gdbinit
```

### Instalar o PEDA

```bash
git clone https://github.com/longld/peda.git ~/peda
echo "source ~/peda/peda.py" >> ~/.gdbinit
```

### Instalar o pwndbg

```bash
git clone https://github.com/pwndbg/pwndbg
cd pwndbg
./setup.sh
```

### Verificar a instalação

```bash
gdb --version
gdb -q
(gdb) quit
```

---

<a id="-basic-usage"></a>
## 💻 Utilização básica

### Iniciar o GDB

> **Correção do original:** `attach` é um comando dentro do GDB. A linha de shell `gdb attach <PID>` não é equivalente a `gdb -p <PID>`; o primeiro argumento pode ser interpretado como nome de ficheiro. Ver a [documentação oficial de attach](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Attach.html).

```bash
# Depurar um executável
gdb ./program

# Depurar com argumentos
gdb --args ./program arg1 arg2

# Associar a um processo em execução
gdb -p <PID>
gdb attach <PID>

# Modo silencioso (sem mensagem inicial)
gdb -q ./program

# Executar comandos de um ficheiro
gdb -x commands.gdb ./program
```

### Comandos básicos

| Comando | Abreviatura | Descrição |
|---------|-------|-------------|
| `run` | `r` | Iniciar o programa |
| `run args` | `r args` | Executar com argumentos |
| `continue` | `c` | Continuar a execução |
| `quit` | `q` | Sair do GDB |
| `help` | `h` | Mostrar a ajuda |
| `help <cmd>` | `h <cmd>` | Ajuda de um comando |

### Comandos de ficheiros

```bash
# Carregar o executável
(gdb) file ./program

# Carregar símbolos
(gdb) symbol-file ./program.debug

# Carregar um core dump
(gdb) core-file core

# Definir o diretório de trabalho
(gdb) cd /path/to/dir

# Definir argumentos
(gdb) set args arg1 arg2
```

---

<a id="-breakpoints"></a>
## 🔴 Pontos de paragem

### Definir pontos de paragem

```bash
# Parar numa função
(gdb) break main
(gdb) b main

# Parar num endereço
(gdb) break *0x08048456
(gdb) b *0x08048456

# Parar numa linha
(gdb) break file.c:25
(gdb) b 25

# Parar num deslocamento relativamente à função
(gdb) break *main+48

# Ponto de paragem temporário (eliminado automaticamente)
(gdb) tbreak main
```

### Pontos de paragem condicionais

```bash
# Parar quando a condição for verdadeira
(gdb) break main if argc > 2
(gdb) break *0x08048456 if $eax == 0

# Adicionar uma condição a um ponto de paragem existente
(gdb) condition 1 $eax == 0

# Remover a condição
(gdb) condition 1
```

### Gerir pontos de paragem

```bash
# Listar os pontos de paragem
(gdb) info breakpoints
(gdb) info b

# Eliminar um ponto de paragem
(gdb) delete 1
(gdb) d 1

# Eliminar todos os pontos de paragem
(gdb) delete
(gdb) d

# Desativar um ponto de paragem
(gdb) disable 1
(gdb) dis 1

# Ativar um ponto de paragem
(gdb) enable 1
(gdb) en 1

# Desativar todos
(gdb) disable

# Ativar todos
(gdb) enable
```

### Watchpoints (pontos de paragem em dados)

```bash
# Vigiar alterações numa variável
(gdb) watch variable
(gdb) watch *0x08048456

# Vigiar leituras
(gdb) rwatch variable

# Vigiar leituras ou escritas
(gdb) awatch variable

# Vigiar uma expressão
(gdb) watch ($eax > 10)
```

### Catchpoints (interceção de eventos)

```bash
# Intercetar chamadas de sistema
(gdb) catch syscall

# Intercetar uma chamada de sistema específica
(gdb) catch syscall write

# Intercetar sinais
(gdb) catch signal SIGSEGV

# Intercetar exceções
(gdb) catch throw
(gdb) catch catch
```

---

<a id="-execution-control"></a>
## ▶️ Controlo da execução

### Execução passo a passo

```bash
# Avançar uma instrução (entrando nas chamadas)
(gdb) stepi
(gdb) si

# Avançar uma linha (entrando nas chamadas)
(gdb) step
(gdb) s

# Avançar sem entrar nas chamadas
(gdb) nexti
(gdb) ni

# Avançar uma linha sem entrar nas chamadas
(gdb) next
(gdb) n

# Continuar até ao retorno
(gdb) finish
(gdb) fin

# Continuar até ao endereço
(gdb) until *0x08048456
(gdb) u *0x08048456
```

### Execução

```bash
# Iniciar ou reiniciar o programa
(gdb) run
(gdb) r

# Continuar após um ponto de paragem
(gdb) continue
(gdb) c

# Executar até ao próximo ponto de paragem
(gdb) continue

# Ignorar N ocorrências de pontos de paragem
(gdb) continue 5

# Saltar para o endereço (perigoso!)
(gdb) jump *0x08048456
```

### Controlo de processos

```bash
# Terminar o processo atual
(gdb) kill

# Desassociar do processo
(gdb) detach

# Associar a um processo
(gdb) attach <PID>

# Acompanhar o fork (processo pai/filho)
(gdb) set follow-fork-mode child
(gdb) set follow-fork-mode parent
```

---

<a id="-examining-memory"></a>
## 🔍 Inspeção da memória

### Comando print

```bash
# Apresentar uma variável
(gdb) print variable
(gdb) p variable

# Apresentar em hexadecimal
(gdb) print/x variable
(gdb) p/x $eax

# Apresentar em binário
(gdb) p/t variable

# Apresentar em decimal
(gdb) p/d variable

# Apresentar como carácter
(gdb) p/c $al

# Apresentar como cadeia de texto
(gdb) p/s pointer

# Apresentar um vetor
(gdb) p *array@10     # 10 elementos
```

### Comando de inspeção (x)

> **Precisão da unidade:** em `x/10x`, o tamanho da unidade pode ser herdado de um comando anterior. A interpretação «10 palavras» pressupõe essa predefinição; `w` e `g` explícitos nos outros exemplos fixam unidades de 4 e 8 bytes. Ver o [manual de inspeção de memória](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Memory.html).

```bash
# Sintaxe: x/NFU address
# N = quantidade, F = formato, U = tamanho da unidade

# Formatos: x(hexadecimal), d(decimal), u(sem sinal), o(octal),
# t(binário), c(carácter), s(cadeia de texto), i(instrução)
# Unidades: b(byte), h(meia palavra=2), w(palavra=4), g(unidade de 8 bytes)

# Examinar a memória
(gdb) x/10x $esp           # 10 palavras em hexadecimal a partir de ESP
(gdb) x/20wx 0x08048456    # 20 palavras em hexadecimal
(gdb) x/s 0x08048456       # Cadeia de texto no endereço
(gdb) x/10i $eip           # 10 instruções a partir de EIP
(gdb) x/10gx $rsp          # 10 unidades de 8 bytes a partir de RSP

# Padrões comuns
(gdb) x/100x $esp          # Examinar a pilha
(gdb) x/20i $eip           # Desassemblar
(gdb) x/s $eax             # Ponteiro para cadeia de texto
```

### Regiões de memória

```bash
# Mostrar o mapa de memória
(gdb) info proc mappings

# Mostrar as secções
(gdb) maintenance info sections

# Mostrar os ficheiros
(gdb) info files
```

### Desassemblagem

```bash
# Desassemblar uma função
(gdb) disassemble main
(gdb) disas main

# Desassemblar a função atual
(gdb) disassemble
(gdb) disas

# Desassemblar um intervalo
(gdb) disas 0x08048456, 0x08048500

# Desassemblar com o código-fonte (se disponível)
(gdb) disas /m main

# Definir a sintaxe
(gdb) set disassembly-flavor intel
(gdb) set disassembly-flavor att
```

---

<a id="-registers"></a>
## 📊 Registos do processador

### Ver os registos do processador

```bash
# Todos os registos
(gdb) info registers
(gdb) i r

# Registo específico
(gdb) info registers eax
(gdb) p $eax
(gdb) p/x $rip

# Todos os registos, incluindo FP/SSE
(gdb) info all-registers
```

### Modificar registos

```bash
# Definir o valor de um registo
(gdb) set $eax = 0
(gdb) set $eip = 0x08048456
(gdb) set $rip = 0x400000

# Definir flags
(gdb) set $eflags = 0x246
```

### Referência de registos x86

| Registo | 64-bit | 32-bit | 16-bit | 8-bit | Finalidade |
|----------|--------|--------|--------|-------|---------|
| Acumulador | RAX | EAX | AX | AL/AH | Valor de retorno |
| Base | RBX | EBX | BX | BL/BH | Utilização geral |
| Contador | RCX | ECX | CX | CL/CH | Contador de ciclo |
| Dados | RDX | EDX | DX | DL/DH | Entrada/saída e multiplicação |
| Índice de origem | RSI | ESI | SI | SIL | Origem de cadeias |
| Índice de destino | RDI | EDI | DI | DIL | Destino de cadeias |
| Ponteiro de base | RBP | EBP | BP | BPL | Quadro da pilha |
| Ponteiro da pilha | RSP | ESP | SP | SPL | Topo da pilha |
| Instrução | RIP | EIP | IP | - | Próxima instrução |

### Registos adicionais de x64

```
R8-R15 : Registos de utilização geral
R8D-R15D : Partes inferiores de 32 bits
R8W-R15W : Partes inferiores de 16 bits
R8B-R15B : Partes inferiores de 8 bits
```

---

<a id="-stack-analysis"></a>
## 📚 Análise da pilha

### Ver a pilha

```bash
# Rasto de chamadas da pilha
(gdb) backtrace
(gdb) bt

# Rasto completo com variáveis locais
(gdb) backtrace full
(gdb) bt full

# Mostrar o quadro da pilha
(gdb) frame
(gdb) f

# Selecionar um quadro da pilha
(gdb) frame 2
(gdb) f 2

# Mostrar o conteúdo da pilha
(gdb) x/50wx $esp    # 32-bit
(gdb) x/50gx $rsp    # 64-bit
```

### Quadro da pilha

```bash
# Mostrar informações do quadro da pilha
(gdb) info frame
(gdb) i f

# Mostrar variáveis locais
(gdb) info locals

# Mostrar argumentos
(gdb) info args

# Subir ou descer entre quadros
(gdb) up
(gdb) down
```

### Comandos da pilha

> **Modelo simplificado:** os deslocamentos baseados em EBP/RBP pressupõem um quadro convencional com ponteiro de base. Código otimizado pode não seguir esse modelo; usa as informações do depurador e da convenção de chamada. Ver a [explicação oficial de stack frames](https://sourceware.org/gdb/current/onlinedocs/gdb.html/Frames.html).

```bash
# Examinar o endereço de retorno
(gdb) x/wx $ebp+4     # 32-bit
(gdb) x/gx $rbp+8     # 64-bit

# Examinar o EBP guardado
(gdb) x/wx $ebp       # 32-bit
(gdb) x/gx $rbp       # 64-bit

# Mostrar a disposição da pilha
(gdb) x/20wx $esp-20  # Antes e depois
```

---

<a id="-gefpedapwndbg"></a>
## 🎨 GEF/PEDA/pwndbg

### Comandos GEF

> Os comandos de GEF, PEDA e pwndbg pertencem às respetivas extensões, não ao GDB base. A disponibilidade e a sintaxe variam por versão; confirma na documentação da extensão usada.

```bash
# Apresentação do contexto (automática)
gef➤ context

# Checksec (mecanismos de segurança)
gef➤ checksec

# Pesquisar um padrão
gef➤ pattern create 200
gef➤ pattern search $rsp

# Encontrar uma cadeia de texto ou valor
gef➤ search-pattern "/bin/sh"
gef➤ grep "/bin/sh"

# Análise da heap
gef➤ heap chunks
gef➤ heap bins

# Gadgets ROP
gef➤ ropgadget

# Assistente para format strings
gef➤ format-string-helper

# VMMap (mapa de memória)
gef➤ vmmap

# Cadeia de desreferenciação
gef➤ dereference $rsp 20

# Telescope (desreferenciação inteligente)
gef➤ telescope $rsp 20

# Informações ELF
gef➤ elf-info

# Valor do canário
gef➤ canary

# Informações de PIE/ASLR
gef➤ pie
gef➤ aslr
```

### Comandos PEDA

```bash
# Verificação de segurança
peda➤ checksec

# Encontrar uma cadeia de texto
peda➤ find "/bin/sh"
peda➤ searchmem "/bin/sh"

# Criar ou pesquisar um padrão
peda➤ pattern create 200
peda➤ pattern offset $eip

# Gadgets ROP
peda➤ ropgadget
peda➤ ropsearch "pop rdi"

# Shellcode
peda➤ shellcode generate x86/linux exec

# Contexto
peda➤ context all

# Assembly
peda➤ assemble
```

### Comandos pwndbg

```bash
# Contexto
pwndbg> context

# Checksec
pwndbg> checksec

# Pesquisar
pwndbg> search -s "/bin/sh"
pwndbg> search -p 0xdeadbeef

# Padrão cíclico
pwndbg> cyclic 200
pwndbg> cyclic -l <value>

# ROP
pwndbg> rop --grep "pop rdi"

# Heap
pwndbg> heap
pwndbg> bins
pwndbg> arena

# GOT/PLT
pwndbg> got
pwndbg> plt
```

---

<a id="-exploit-development"></a>
## 💥 Desenvolvimento de exploits

### Encontrar o deslocamento

> **Inconsistência do exemplo:** gerar um padrão e depois fornecer apenas caracteres `A` ao programa não introduz esse padrão cíclico. A segunda sequência também não mostra a criação de `input.txt`; `print(cyclic(...))` em Python 3 pode imprimir a representação de bytes. Os exemplos são preservados e não constituem um fluxo completo de cálculo do deslocamento.

```bash
# Método 1: Padrão (GEF)
gef➤ pattern create 200
gef➤ run < <(python3 -c 'print("A"*200)')
gef➤ pattern search $rsp

# Método 2: Padrão (pwntools)
$ python3 -c "from pwn import *; print(cyclic(200))"
gef➤ run < input.txt
gef➤ cyclic -l 0x61616167
```

### Fundamentos de buffer overflow

```bash
# Verificar o ASLR
$ cat /proc/sys/kernel/randomize_va_space
# 0=desativado, 1=parcial, 2=completo

# Desativar o ASLR (para testes)
$ echo 0 | sudo tee /proc/sys/kernel/randomize_va_space

# Verificar as proteções do binário
gef➤ checksec

# Encontrar a localização do endereço de retorno
(gdb) x/20wx $esp     # Antes da falha
(gdb) i f             # Informações do quadro da pilha
```

### Encontrar gadgets

```bash
# Encontrar "/bin/sh"
gef➤ search-pattern "/bin/sh"

# Encontrar o endereço de system()
(gdb) p system
(gdb) p &system

# Encontrar um gadget pop rdi (64 bits)
gef➤ ropgadget --grep "pop rdi"

# Encontrar pop; ret (32 bits)
gef➤ ropgadget --grep "pop"
```

### Escrever na memória

```bash
# Definir um valor na memória
(gdb) set {int}0x08048456 = 0x41414141
(gdb) set {char}0x08048456 = 'A'

# Escrever uma cadeia de texto
(gdb) set {char[10]}0x08048456 = "AAAA"

# Chamar uma função
(gdb) call system("/bin/sh")
```

### Útil para exploits

```bash
# Acompanhar os processos filhos
(gdb) set follow-fork-mode child

# Desativar o ASLR no GDB
(gdb) set disable-randomization on

# Mostrar bibliotecas partilhadas
(gdb) info sharedlibrary

# Encontrar o endereço base da libc
gef➤ vmmap libc

# Encontrar PLT/GOT
(gdb) info functions @plt
gef➤ got
```

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos essenciais

| Comando | Descrição |
|---------|-------------|
| `r` | Executar o programa |
| `c` | Continuar |
| `b *addr` | Definir um ponto de paragem |
| `si` | Avançar uma instrução |
| `ni` | Próxima instrução |
| `fin` | Concluir a função |
| `x/Nx addr` | Examinar a memória |
| `p $reg` | Apresentar um registo |
| `i r` | Informações dos registos |
| `bt` | Rasto de chamadas |
| `disas` | Desassemblar |
| `q` | Sair |

### Inspeção da memória

| Comando | Descrição |
|---------|-------------|
| `x/10x $esp` | 10 palavras em hexadecimal a partir de ESP |
| `x/s addr` | Cadeia de texto no endereço |
| `x/i $eip` | Instrução em EIP |
| `x/20wx addr` | 20 palavras em hexadecimal |
| `x/10gx $rsp` | 10 unidades de 8 bytes a partir de RSP |

### Depuração rápida

```bash
# Iniciar e parar em main
$ gdb -q ./program
(gdb) break main
(gdb) run

# Examinar a falha
(gdb) run < payload
(gdb) x/10i $eip
(gdb) i r
(gdb) bt
```

### Sequência de desenvolvimento de um exploit

```
1. checksec           → Verificar as proteções
2. pattern create     → Criar um padrão
3. Executar com padrão → Provocar uma falha no programa
4. pattern search     → Encontrar o deslocamento
5. Encontrar gadgets  → Gadgets ROP
6. Construir payload  → Criar o exploit
7. Testar             → Depurar com o GDB
```

---

## 📚 Recursos

- [Manual oficial do GDB](https://sourceware.org/gdb/current/onlinedocs/gdb/)
- [Documentação do GEF](https://hugsy.github.io/gef/)
- [Documentação do pwndbg](https://github.com/pwndbg/pwndbg)
- [PEDA](https://github.com/longld/peda)

### Guias de referência relacionados
- [Ghidra](../../Ghidra/translations/README.pt.md)
- [Comandos Linux](../../Linux-Commands/README.md)
- [Escalada de privilégios Linux](../../Linux-PrivEsc/README.md)

---

<p align="center">
  <b>🐛 Domina a depuração em Linux!</b><br>
  <i>GDB — A ferramenta essencial para quem faz engenharia inversa em Linux</i>
</p>
