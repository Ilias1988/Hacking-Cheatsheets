# 🔧 x64dbg — Guia de referência do depurador Windows

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
  ██╗  ██╗ ██████╗ ██╗  ██╗██████╗ ██████╗  ██████╗ 
  ╚██╗██╔╝██╔════╝ ██║  ██║██╔══██╗██╔══██╗██╔════╝ 
   ╚███╔╝ ███████╗ ███████║██║  ██║██████╔╝██║  ███╗
   ██╔██╗ ██╔═══██╗╚════██║██║  ██║██╔══██╗██║   ██║
  ██╔╝ ██╗╚██████╔╝     ██║██████╔╝██████╔╝╚██████╔╝
  ╚═╝  ╚═╝ ╚═════╝      ╚═╝╚═════╝ ╚═════╝  ╚═════╝ 
           Depurador Windows x64/x32
```

<p align="center">
  <img src="https://img.shields.io/badge/x64dbg-Windows_Debugger-blue?style=for-the-badge" alt="x64dbg">
  <img src="https://img.shields.io/badge/Reverse_Engineering-green?style=for-the-badge" alt="RE">
  <img src="https://img.shields.io/badge/Malware_Analysis-red?style=for-the-badge" alt="Malware">
</p>

---

## 📋 Índice

- [O que é o x64dbg](#-what-is-x64dbg)
- [Instalação](#-installation)
- [Visão geral da interface](#-interface-overview)
- [Utilização básica](#-basic-usage)
- [Pontos de paragem](#-breakpoints)
- [Execução passo a passo](#-stepping--execution)
- [Operações de memória](#-memory-operations)
- [Registos do processador](#-registers)
- [Análise da pilha](#-stack-analysis)
- [Aplicação de patches](#-patching)
- [Scripting](#-scripting)
- [Atalhos de teclado](#-keyboard-shortcuts)
- [Referência rápida](#-quick-reference)

---

<a id="-what-is-x64dbg"></a>
## 🎯 O que é o x64dbg

**x64dbg** é um depurador x64/x32 de código aberto para Windows. É o sucessor espiritual do OllyDbg e inclui funcionalidades modernas:

- 🔍 **Depuração em modo de utilizador** — Depurar aplicações Windows
- 🦠 **Análise de malware** — Analisar executáveis suspeitos
- 💥 **Desenvolvimento de exploits** — Investigar exploits para Windows
- 🔧 **Engenharia inversa** — Compreender o comportamento de binários
- 🧩 **Suporte de plugins** — Extensível através de plugins

Um debugger é um depurador. Breakpoints são pontos de paragem; registos são pequenas áreas de armazenamento do processador. Desassemblagem apresenta instruções de máquina em assembly; descompilação tenta reconstruir pseudocódigo de alto nível. Um dump é uma cópia ou vista do conteúdo da memória.

### Funcionalidades

| Funcionalidade | Descrição |
|---------|-------------|
| **GUI** | Interface moderna e personalizável |
| **Suporte de x64** | Depuração completa de 64 bits |
| **Scripting** | Linguagem de scripting integrada |
| **Plugins** | Ecossistema alargado de plugins |
| **Symbols (símbolos)** | Suporte de símbolos PDB |
| **Código aberto** | Gratuito e desenvolvido ativamente |

---

<a id="-installation"></a>
## 🚀 Instalação

### Descarregar

```
# Site oficial
https://x64dbg.com/

# Lançamentos no GitHub
https://github.com/x64dbg/x64dbg/releases

# Descarregar: snapshot_YYYY-MM-DD_HH-MM.zip
```

### Configuração

```
1. Extrair o ZIP para uma localização permanente (por exemplo, C:\x64dbg\)
2. Executar x96dbg.exe (iniciador para ambas as versões)
   - x32dbg.exe → Depuração de 32 bits
   - x64dbg.exe → Depuração de 64 bits
3. (Opcional) Adicionar ao PATH ou criar atalhos
```

### Plugins recomendados

| Plugin | Finalidade |
|--------|---------|
| **ScyllaHide** | Contorno de técnicas antidepuração |
| **SwissArmyKnife** | Vários utilitários |
| **x64dbgpy** | Scripting em Python |
| **TitanHide** | Antidepuração ao nível do kernel |
| **OllyDumpEx** | Cópia do conteúdo da memória |
| **Multiline Ultimate Assembler** | Modificação de várias linhas |

### Instalação de plugins

```
1. Descarregar o plugin (ficheiro DLL)
2. Copiar para: x64dbg\release\x32\plugins\ (32 bits)
           x64dbg\release\x64\plugins\ (64 bits)
3. Reiniciar o x64dbg
```

---

<a id="-interface-overview"></a>
## 🖥️ Visão geral da interface

### Janelas principais

Os nomes dos menus e ações reais são mantidos em inglês: File (ficheiro), Debug (depuração), View (ver), Follow (acompanhar), Breakpoint (ponto de paragem), Assemble (montar instruções), Binary (dados binários) e Scripts (scripts). Space é a tecla de espaço; Enter confirma e Escape permite regressar. Os nomes, atalhos e plugins dependem da versão e do contexto.

| Janela | Descrição | Atalho |
|--------|-------------|----------|
| **CPU** | Vista de desassemblagem | Alt+C |
| **Log (registo de mensagens)** | Saída de depuração | Alt+L |
| **Memory Map (mapa de memória)** | Regiões de memória | Alt+M |
| **Call Stack (pilha de chamadas)** | Rasto de chamadas | Alt+K |
| **Breakpoints (pontos de paragem)** | Todos os pontos de paragem | Alt+B |
| **Symbols (símbolos)** | Símbolos carregados | Alt+E |
| **Threads (tarefas de execução)** | Lista de threads | Alt+T |
| **Handles (identificadores de recursos)** | Lista de handles | Alt+H |

### Painéis da janela CPU

No diagrama, Disassembly é a vista de desassemblagem, Registers são os registos do processador, Hex Dump mostra bytes em hexadecimal e Stack mostra a pilha. Os rótulos originais da interface são preservados.

```
┌─────────────────┬──────────────────┐
│   Disassembly   │    Registers     │
│                 │                  │
├─────────────────┼──────────────────┤
│   Hex Dump      │    Stack         │
│                 │                  │
└─────────────────┴──────────────────┘
```

### Barra de estado

```
[Module.Address] | [Flags] | [EIP/RIP] | [Status]
```

---

<a id="-basic-usage"></a>
## 💻 Utilização básica

### Iniciar a depuração

```
# Abrir um executável
File → Open (F3)

# Associar a um processo
File → Attach (Alt+A)

# Abrir e executar
Debug → Run (F9)

# Reiniciar a depuração
Debug → Restart (Ctrl+F2)

# Parar a depuração
Debug → Stop (Alt+F2)
```

### Barra de comandos

> **Correção do original:** `g` e `r` são aliases de `run`; retomam a execução e podem usar um endereço como ponto de paragem temporário. Não são comandos genéricos para apenas navegar ou editar registos. Assim, as linhas com `g endereço` e `r registo=valor` devem ser interpretadas com esse limite. Ver [run/go/r/g](https://help.x64dbg.com/en/latest/commands/debug-control/run.html) e [mov/set](https://help.x64dbg.com/en/latest/commands/general-purpose/mov.html).

```
# Introduzir os comandos na barra de comandos inferior

# Ir para um endereço
g 0x401000
goto 0x401000

# Ir para uma expressão
g GetProcAddress
g kernel32.LoadLibraryA

# Pesquisar uma cadeia de texto
/s "password"

# Pesquisar valores hexadecimais
/x 90 90 90 90

# Pesquisar um padrão
/p "8B ?? 89"
```

### Navegação

```
# Ir para um endereço
Ctrl+G → Introduzir o endereço

# Ir para o ponto de entrada
Debug → Entry Point

# Acompanhar no desassemblador
Botão direito → Follow in Disassembler

# Recuar
- (tecla menos)

# Avançar
+ (tecla mais)
```

---

<a id="-breakpoints"></a>
## 🔴 Pontos de paragem

### Pontos de paragem de software (INT3)

```
# Ativar ou desativar o ponto de paragem no endereço atual
F2

# Definir um ponto de paragem por comando
bp 0x401000
bp kernel32.LoadLibraryA
bp module.function

# Definir um ponto de paragem condicional
bp 0x401000, "eax==0"
bp 0x401000, "[esp]==0x12345"

# Parar no carregamento de um módulo
bp $module(kernel32)
```

### Pontos de paragem de hardware

> **Precisão:** no comando `bph`, o tipo `r` significa leitura e escrita, não apenas leitura. Tamanho e alinhamento também se aplicam; 8 bytes é uma opção de x64. Ver a [documentação oficial](https://help.x64dbg.com/en/latest/commands/breakpoint-control/SetHardwareBreakpoint.html).

```
# Definir um ponto de paragem de hardware (limitado a 4)
Botão direito → Breakpoint → Hardware → Execute
Botão direito → Breakpoint → Hardware → Access
Botão direito → Breakpoint → Hardware → Write

# Por comando
bph 0x401000, x, 1    # Execução
bph 0x401000, r, 4    # Leitura (4 bytes)
bph 0x401000, w, 4    # Escrita (4 bytes)

# Tamanhos: 1, 2, 4 ou 8 bytes
```

### Pontos de paragem de memória

```
# Parar no acesso à memória
Botão direito na vista de memória → Breakpoint → Memory → Access
Botão direito na vista de memória → Breakpoint → Memory → Write

# Por comando
bpm 0x401000, r       # Leitura
bpm 0x401000, w       # Escrita
bpm 0x401000, x       # Execução
```

### Pontos de paragem condicionais

> **Correção do original:** o segundo argumento de `bp` é o nome do breakpoint, não a condição. As cadeias com comparações nos exemplos não configuram automaticamente uma condição. A documentação apresenta `SetBreakpointCondition` para esse efeito. Ver [SetBPX](https://help.x64dbg.com/en/latest/commands/breakpoint-control/SetBPX.html) e [condições](https://help.x64dbg.com/en/latest/commands/conditional-breakpoint-control/SetBreakpointCondition.html).

```
# Parar se a condição for verdadeira
bp 0x401000, "eax==0"
bp 0x401000, "eax!=ebx"
bp 0x401000, "[esp+4]>0x100"
bp 0x401000, "byte:[eax]==0x90"

# Parar segundo a contagem de ocorrências
bp 0x401000, "", 5    # Parar na 5.ª ocorrência

# Registar sem parar
bplog 0x401000, "EAX={eax}, EBX={ebx}"
```

### Gerir pontos de paragem

```
# Listar todos os pontos de paragem
Janela Breakpoints (Alt+B)

# Eliminar um ponto de paragem
bc 0x401000          # Eliminar o indicado
bc                   # Eliminar todos

# Ativar/desativar
bd 0x401000          # Desativar
be 0x401000          # Ativar

# Desativar todos
bd *

# Ativar todos
be *
```

---

<a id="-stepping--execution"></a>
## ▶️ Execução passo a passo

### Execução passo a passo

| Ação | Atalho | Descrição |
|--------|----------|-------------|
| Step Into (entrar nas chamadas) | F7 | Executar uma instrução |
| Step Over (não entrar nas chamadas) | F8 | Avançar sem entrar nas chamadas |
| Step Out (sair da função) | Ctrl+F9 | Executar até ao retorno |
| Run to Selection (executar até à seleção) | F4 | Executar até ao cursor |
| Run to User Code (executar até ao código do utilizador) | Alt+F9 | Sair do código do sistema |

### Execução

| Ação | Atalho | Descrição |
|--------|----------|-------------|
| Run (executar) | F9 | Continuar a execução |
| Run Skipping (executar ignorando) | Ctrl+F7 | Ignorar a instrução atual |
| Pause (pausar) | F12 | Interromper a execução |
| Reiniciar | Ctrl+F2 | Reiniciar a depuração |
| Close (fechar) | Alt+F2 | Parar a depuração |

### Rastreio de execução

```
# Rastrear entrando nas chamadas (registar todas as instruções)
Debug → Trace Into (Ctrl+F7)

# Rastrear sem entrar nas chamadas
Debug → Trace Over (Ctrl+F8)

# Registo do rastreio
Debug → Trace Record

# Parar o rastreio
Debug → Stop Trace
```

### Ignorar uma instrução

```
# Ignorar a instrução atual sem a executar
Botão direito → Set New Origin Here

# Ou
Ctrl+Shift+O
```

---

<a id="-memory-operations"></a>
## 🔍 Operações de memória

### Vista do conteúdo da memória (Memory Dump)

```
# Acompanhar um endereço na vista de memória
Botão direito → Follow in Dump → Address/Selection

# Ir para um endereço na vista de memória
Ctrl+G (na janela da vista de memória)

# Alterar o formato da vista de memória
Botão direito → Hex → [Byte/Word/Dword/Qword/ASCII/Unicode]
```

### Memory Map (mapa de memória)

```
# Abrir o mapa de memória
View → Memory Map (Alt+M)

# Mostra todas as regiões de memória:
- Endereço
- Tamanho
- Secção
- Tipo (Image/Private/Mapped: imagem/privada/mapeada)
- Proteção (Execute/Read/Write: executar/ler/escrever)
- Nome do módulo
```

### Pesquisar na memória

```
# Pesquisar na barra de comandos
/s "string"           # Cadeia de texto ASCII
/s L"string"          # Cadeia de texto Unicode
/x 90 90 90           # Bytes em hexadecimal
/p "8B ?? 89"         # Padrão com caracteres universais

# Encontrar referências
Botão direito → Find references to → Selected Address

# Encontrar cadeias de texto
Botão direito → Search for → All Strings
```

### Proteção de memória

```
# Ver a proteção
Memory Map → Coluna Protection (proteção)

# Proteções comuns
PAGE_EXECUTE_READ
PAGE_EXECUTE_READWRITE
PAGE_READONLY
PAGE_READWRITE
PAGE_NOACCESS
```

### Copiar o conteúdo da memória

```
# Guardar o conteúdo da memória num ficheiro
Botão direito em Memory Map → Dump Memory to File

# Copiar a memória com plugins
Plugins → OllyDumpEx → Dump
```

---

<a id="-registers"></a>
## 📊 Registos do processador

### Painel de registos

```
# Utilização geral (x64)
RAX, RBX, RCX, RDX
RSI, RDI, RBP, RSP
R8-R15, RIP

# Utilização geral (x32)
EAX, EBX, ECX, EDX
ESI, EDI, EBP, ESP
EIP

# Registos de segmento
CS, DS, SS, ES, FS, GS

# Flags (EFLAGS/RFLAGS)
CF, PF, AF, ZF, SF, TF, IF, DF, OF
```

### Modificar registos

```
# Fazer duplo clique no registo para editar
# Ou botão direito → Modify Value

# Através da barra de comandos
r eax=0
r rax=0x12345678
r eip=0x401000
r zf=1            # Definir a Zero Flag
```

### Flags importantes

| Flag | Nome | Quando é ativada |
|------|------|----------|
| ZF | Zero Flag (indicador de zero) | O resultado é zero |
| CF | Carry Flag (indicador de transporte) | Transbordamento sem sinal |
| SF | Sign Flag (indicador de sinal) | O resultado é negativo |
| OF | Overflow Flag (indicador de transbordamento) | Transbordamento com sinal |
| DF | Direction Flag (indicador de direção) | Direção das operações de cadeias |
| TF | Trap Flag (indicador de execução passo a passo) | Execução de um passo |

### Alternar flags

```
# Clicar na letra da flag no painel de registos
# Ou utilizar comandos:
r zf=!zf         # Alternar a Zero Flag
r cf=0           # Limpar a Carry Flag
r sf=1           # Definir a Sign Flag
```

---

<a id="-stack-analysis"></a>
## 📚 Análise da pilha

### Painel da pilha

```
# Mostra o conteúdo da pilha com:
- Endereço
- Valor
- Comentários (se disponíveis)

# Opções do botão direito:
- Follow DWORD in Disassembler
- Follow DWORD in Dump
- Modify (modificar)
```

### Call Stack (pilha de chamadas)

```
# Abrir a pilha de chamadas
View → Call Stack (Alt+K)

# Mostra:
- Return Address (endereço de retorno)
- From (chamador)
- To (função chamada)
- Tamanho
- Comment (comentário)
```

### Operações da pilha

> **Simulação parcial:** alterar apenas ESP/RSP move o ponteiro, mas não realiza a escrita ou leitura de memória de uma instrução PUSH/POP completa. As linhas originais são preservadas como exemplos dessa alteração de ponteiro.

```
# Ir para um endereço da pilha
Botão direito → Follow DWORD in Stack

# Modificar um valor da pilha
Botão direito → Modify → Edit

# Simulação de push/pop
r esp=esp-4      # Push (32 bits)
r esp=esp+4      # Pop (32 bits)
r rsp=rsp-8      # Push (64 bits)
r rsp=rsp+8      # Pop (64 bits)
```

### Disposição da pilha (stdcall de 32 bits)

```
[ESP]      → Endereço de retorno
[ESP+4]    → Argumento 1
[ESP+8]    → Argumento 2
[ESP+C]    → Argumento 3
...

Após PUSH EBP; MOV EBP, ESP:
[EBP]      → EBP guardado
[EBP+4]    → Endereço de retorno
[EBP+8]    → Argumento 1
[EBP-4]    → Variável local 1
```

---

<a id="-patching"></a>
## 🔧 Aplicação de patches

### Montar instruções assembly

```
# Botão direito na instrução → Assemble (Space)
# Introduzir a nova instrução ou instruções

# Exemplos:
NOP                    # Sem operação
JMP 0x401020           # Salto
MOV EAX, 0             # Limpar EAX
XOR EAX, EAX           # Limpar EAX (mais curto)
RET                    # Retorno
```

### Preencher com NOPs

```
# Selecionar o intervalo → Botão direito → Binary → Fill with NOPs

# Ou selecionar e premir Space:
# Introduzir vários NOPs
```

### Edição de binário

```
# Editar os bytes diretamente
Botão direito → Binary → Edit

# Preencher com um valor
Botão direito → Binary → Fill

# Copiar/colar dados binários
Botão direito → Binary → Copy
Botão direito → Binary → Paste
```

### Patches comuns

| Antes | Depois | Finalidade |
|--------|-------|---------|
| `74 XX` (JE) | `75 XX` (JNE) | Inverter a condição |
| `75 XX` (JNE) | `74 XX` (JE) | Inverter a condição |
| `74 XX` (JE) | `EB XX` (JMP) | Saltar sempre |
| `74 XX` (JE) | `90 90` (NOP) | Nunca saltar |
| `0F 84` (JE) | `0F 85` (JNE) | Inverter (salto near) |
| `CALL xxx` | `NOP NOP NOP NOP NOP` | Ignorar a chamada |

### Guardar patches

```
# Método 1: Patch File (modificar o ficheiro)
File → Patch File

# Método 2: Exportar patches
Botão direito → Copy → Selection
# Colar num editor hexadecimal

# Método 3: Plugin
Plugins → Export modified executable (exportar o executável modificado)
```

---

<a id="-scripting"></a>
## 📝 Scripting

### Sintaxe de comandos

```
# Variáveis
$var = 0x401000

# Comparação
cmp eax, 0

# Condição
je label
jne label

# Etiquetas
label:

# Comentários
; Isto é um comentário
```

### Script básico

> **Limitações do exemplo:** o padrão `8B EC` corresponde a MOV EBP, ESP, não inclui PUSH EBP e não identifica WinMain de forma única. Além disso, `find` devolve em `$result` um endereço virtual, não um deslocamento relativo; somá-lo ao endereço inicial não avança corretamente para o resultado seguinte. Ver a [documentação de find](https://help.x64dbg.com/en/latest/commands/searching/find.html).

```
; Script para encontrar WinMain
mov $addr, 0x401000        ; Endereço inicial
loop:
  find $addr, "8B EC"       ; Encontrar PUSH EBP; MOV EBP, ESP
  cmp $result, 0
  je notfound
  msg "Found at: {$result}"
  add $addr, $result
  add $addr, 1
  jmp loop
notfound:
  msg "Pattern not found"
ret
```

### Comandos de scripts

| Comando | Descrição |
|---------|-------------|
| `msg "text"` | Apresentar uma mensagem |
| `log "text"` | Registar na consola |
| `find addr, "pattern"` | Pesquisar um padrão |
| `bp addr` | Definir um ponto de paragem |
| `bc addr` | Eliminar um ponto de paragem |
| `sti` | Entrar nas chamadas |
| `sto` | Avançar sem entrar nas chamadas |
| `run` | Run (executar) |
| `ret` | Retornar/parar |

### Executar um script

```
# Carregar e executar um script
Scripts → Run Script

# Ou através da barra de comandos
scr "scriptfile.txt"
```

---

<a id="-keyboard-shortcuts"></a>
## ⌨️ Atalhos de teclado

### Geral

| Atalho | Ação |
|----------|--------|
| F3 | Abrir um ficheiro |
| Alt+A | Associar a um processo |
| Ctrl+F2 | Reiniciar |
| Alt+F2 | Parar a depuração |
| Ctrl+G | Ir para um endereço |
| Ctrl+F | Encontrar |

### Depuração

| Atalho | Ação |
|----------|--------|
| F2 | Ativar/desativar um ponto de paragem |
| F4 | Executar até à seleção |
| F7 | Entrar nas chamadas |
| F8 | Avançar sem entrar nas chamadas |
| F9 | Run (executar) |
| F12 | Pause (pausar) |
| Ctrl+F9 | Executar até ao retorno |

### Navegação

| Atalho | Ação |
|----------|--------|
| - | Recuar |
| + | Avançar |
| * | Ir para EIP/RIP |
| Enter | Acompanhar uma chamada ou salto |
| Escape | Recuar |

### Vistas

| Atalho | Ação |
|----------|--------|
| Alt+C | CPU |
| Alt+L | Log (registo de mensagens) |
| Alt+M | Memory Map (mapa de memória) |
| Alt+K | Call Stack (pilha de chamadas) |
| Alt+B | Pontos de paragem |
| Alt+E | Symbols (símbolos) |
| Alt+T | Threads (tarefas de execução) |

### Edição

| Atalho | Ação |
|----------|--------|
| Space | Montar instruções assembly |
| ; | Adicionar um comentário |
| : | Adicionar uma etiqueta |
| N | Mudar o nome |

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Comandos essenciais

| Comando | Descrição |
|---------|-------------|
| `bp addr` | Definir um ponto de paragem |
| `bc addr` | Eliminar um ponto de paragem |
| `bph addr, x` | Ponto de paragem de hardware |
| `g addr` | Ir para um endereço |
| `r reg=val` | Definir um registo |
| `sti` | Entrar nas chamadas |
| `sto` | Avançar sem entrar nas chamadas |
| `run` | Run (executar) |

### Padrões comuns

| Bytes | Instrução |
|-------|-------------|
| `90` | NOP |
| `CC` | INT3 (ponto de paragem) |
| `C3` | RET |
| `55` | PUSH EBP |
| `8B EC` | MOV EBP, ESP |
| `74 XX` | JE short |
| `75 XX` | JNE short |
| `EB XX` | JMP short |
| `E8 XX XX XX XX` | CALL near |
| `E9 XX XX XX XX` | JMP near |

### Sequência de análise

```
1. Carregar o executável      → F3
2. Definir paragem na entrada → F2 na entrada
3. Executar até à paragem     → F9
4. Percorrer o código por passos → F7/F8
5. Examinar registos e memória → Painéis
6. Definir pontos de paragem adicionais
7. Continuar a análise
8. Modificar se necessário    → Space
9. Guardar o ficheiro modificado
```

### Contorno de mecanismos antidepuração

```
# Instalar o plugin ScyllaHide
Plugins → ScyllaHide → Options → Selecionar o perfil

# Modificações manuais:
- Substituir chamadas a IsDebuggerPresent por NOPs
- Modificar PEB.BeingDebugged
- Tratar as exceções corretamente
```

---

## 📚 Recursos

- [Site oficial do x64dbg](https://x64dbg.com/)
- [x64dbg GitHub](https://github.com/x64dbg/x64dbg)
- [Documentação do x64dbg](https://help.x64dbg.com/)
- [Plugins do x64dbg](https://github.com/x64dbg/x64dbg/wiki/Plugins)

### Guias de referência relacionados
- [Ghidra](../../Ghidra/translations/README.pt.md)
- [GDB](../../GDB/translations/README.pt.md)
- [Escalada de privilégios Windows](../../Windows-PrivEsc/README.md)

---

<p align="center">
  <b>🔧 Domina a depuração em Windows!</b><br>
  <i>x64dbg — O sucessor moderno do OllyDbg</i>
</p>
