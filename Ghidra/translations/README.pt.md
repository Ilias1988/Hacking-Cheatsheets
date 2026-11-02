# 🔍 Ghidra — Guia de referência de engenharia inversa

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
   ██████╗ ██╗  ██╗██╗██████╗ ██████╗  █████╗ 
  ██╔════╝ ██║  ██║██║██╔══██╗██╔══██╗██╔══██╗
  ██║  ███╗███████║██║██║  ██║██████╔╝███████║
  ██║   ██║██╔══██║██║██║  ██║██╔══██╗██╔══██║
  ╚██████╔╝██║  ██║██║██████╔╝██║  ██║██║  ██║
   ╚═════╝ ╚═╝  ╚═╝╚═╝╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝
              Suite de engenharia inversa da NSA
```

<p align="center">
  <img src="https://img.shields.io/badge/Ghidra-Reverse_Engineering-blue?style=for-the-badge" alt="Ghidra">
  <img src="https://img.shields.io/badge/NSA-Open_Source-green?style=for-the-badge" alt="NSA">
  <img src="https://img.shields.io/badge/FREE-red?style=for-the-badge" alt="Free">
</p>

---

## 📋 Índice

- [O que é o Ghidra](#-what-is-ghidra)
- [Instalação](#-installation)
- [Visão geral da interface](#-interface-overview)
- [Criação de um projeto](#-creating-a-project)
- [Fundamentos da análise](#-analysis-basics)
- [Navegação](#-navigation)
- [Descompilador](#-decompiler)
- [Tipos de dados e estruturas](#-data-types--structures)
- [Aplicação de patches e modificações](#-patching--modifying)
- [Scripting](#-scripting)
- [Atalhos de teclado](#-keyboard-shortcuts)
- [Referência rápida](#-quick-reference)

---

<a id="-what-is-ghidra"></a>
## 🎯 O que é o Ghidra

**Ghidra** é uma suite gratuita e de código aberto para engenharia inversa de software (SRE), desenvolvida pela NSA. Disponibiliza:

- 🔍 **Desassemblador** — Converte código binário em assembly
- 🔄 **Descompilador** — Gera pseudocódigo semelhante a C
- 📊 **Análise** — Análise automatizada de código
- 🔧 **Aplicação de patches** — Modifica binários
- 🐍 **Scripting** — Automatização com Python/Java
- 👥 **Colaboração** — Suporte para vários utilizadores

Desassemblagem converte instruções de máquina em assembly. Descompilação tenta reconstruir pseudocódigo de nível superior; o resultado não é necessariamente o código-fonte original. Uma string é uma cadeia de texto, um patch é uma alteração no binário e XREF significa referência cruzada.

### Arquiteturas suportadas

| Arquitetura | Variantes |
|--------------|----------|
| **x86** | 16/32/64-bit |
| **ARM** | ARM32, ARM64, Thumb |
| **MIPS** | 32/64-bit |
| **PowerPC** | 32/64-bit |
| **SPARC** | 32/64-bit |
| **RISC-V** | 32/64-bit |
| **AVR** | 8-bit |
| **6502** | 8-bit |

### Formatos suportados

| Formato | Descrição |
|--------|-------------|
| ELF | Executáveis Linux |
| PE/COFF | Executáveis Windows |
| Mach-O | Executáveis macOS |
| Raw Binary (binário em bruto) | Firmware, ROM |
| APK/DEX | Aplicações Android |
| e muitos outros... |

---

<a id="-installation"></a>
## 🚀 Instalação

### Descarregar

```bash
# Descarregar do site oficial
https://ghidra-sre.org/

# Ou através dos lançamentos no GitHub
https://github.com/NationalSecurityAgency/ghidra/releases
```

### Requisitos

> **Versões:** os requisitos e comandos com JDK 17 são os do original. O README oficial atual indica JDK 25 de 64 bits; utiliza os requisitos da versão concreta do Ghidra. Ver a [instalação oficial](https://github.com/NationalSecurityAgency/ghidra/blob/master/README.md).

| Requisito | Versão |
|-------------|---------|
| **JDK** | 17+ (64-bit) |
| **RAM** | Mínimo de 4GB, recomendado 8GB+ |
| **Disco** | 1GB+ para instalação |

### Linux/macOS

```bash
# Instalar o JDK 17+
sudo apt install openjdk-17-jdk  # Debian/Ubuntu
brew install openjdk@17          # macOS

# Extrair o Ghidra
unzip ghidra_*.zip
cd ghidra_*

# Executar
./ghidraRun
```

### Windows

```batch
:: Instalar o JDK 17+ da Oracle ou Adoptium
:: Extrair ghidra_*.zip
:: Executar ghidraRun.bat
```

---

<a id="-interface-overview"></a>
## 🖥️ Visão geral da interface

### Janelas principais

Os nomes dos menus e controlos são mantidos em inglês para facilitar a localização: File (ficheiro), Import (importar), Analysis (análise), Search (pesquisar), Window (janela), References (referências), Rename (mudar o nome) e Retype (alterar o tipo). Os atalhos e nomes exatos dependem da versão e do contexto da janela.

| Janela | Descrição |
|--------|-------------|
| **Program Trees (árvores do programa)** | Estrutura de ficheiros do projeto |
| **Symbol Tree (árvore de símbolos)** | Funções, etiquetas e classes |
| **Data Type Manager (gestor de tipos de dados)** | Tipos de dados definidos |
| **Listing (listagem)** | Vista de desassemblagem |
| **Decompiler (descompilador)** | Pseudocódigo C |
| **Console (consola)** | Saída e scripting |
| **Defined Strings (cadeias de texto definidas)** | Cadeias de texto encontradas |
| **Bookmarks (marcadores)** | Localizações guardadas |

### Janela Listing (desassemblagem)

Na listagem ilustrativa, Address é o endereço, Bytes são os bytes, Disassembly é a desassemblagem e Comments são os comentários. Os identificadores das colunas são mantidos como na interface.

```
Address    | Bytes          | Disassembly           | Comments
-----------|----------------|-----------------------|----------
00401000   | 55             | PUSH EBP              | Início da função
00401001   | 89 e5          | MOV EBP, ESP          |
00401003   | 83 ec 10       | SUB ESP, 0x10         | Quadro da pilha
00401006   | e8 f5 ff ff ff | CALL FUN_00401000     |
```

---

<a id="-creating-a-project"></a>
## 📁 Criação de um projeto

### Novo projeto

```
1. File → New Project (ficheiro → novo projeto)
2. Escolher:
   - Non-Shared Project (projeto local, não partilhado)
   - Shared Project (projeto partilhado, para colaboração)
3. Selecionar o diretório
4. Introduzir o nome do projeto
5. Clicar em Finish (concluir)
```

### Importar um binário

```
1. File → Import File (I) (ficheiro → importar ficheiro)
2. Selecionar o binário ou ficheiro
3. Escolher o formato (normalmente detetado automaticamente)
4. Configurar as opções:
   - Language/Processor (linguagem/processador)
   - Compiler spec (especificação do compilador)
5. Clicar em OK
6. Analyze? → Yes (analisar? → sim, recomendado)
```

### Opções de análise

| Opção | Descrição |
|--------|-------------|
| ASCII Strings (cadeias de texto ASCII) | Encontrar cadeias de texto |
| Descompilador | Ativar a descompilação |
| Stack Analysis (análise da pilha) | Análise de quadros da pilha |
| Reference Analysis (análise de referências) | Encontrar referências cruzadas |
| Function ID | Correspondência com funções de bibliotecas |
| Call Convention (convenção de chamada) | Identificar convenções de chamada |

---

<a id="-analysis-basics"></a>
## 🔬 Fundamentos da análise

### Análise automática

```
Analysis → Auto Analyze (A)
- Executa todos os analisadores selecionados
- Pode demorar em binários grandes
- Apresenta a evolução no monitor de tarefas
```

### Analisadores executados pontualmente

```
Analysis → One Shot → [Analyzer]
- ASCII Strings
- Decompiler Parameter ID
- Function ID
- Stack Analysis
```

### Identificação de funções

> **Precisão do original:** a opção «Non-returning Functions» diz respeito a funções sem retorno; ativá-la não constitui, por si só, um método para descobrir todas as funções. A identificação automática depende dos analisadores, do formato e da qualidade da análise.

```
# Encontrar todas as funções
Analysis → Auto Analyze → Ativar "Non-returning Functions"

# Criação manual de funções
Clicar com o botão direito no endereço → Create Function (F)

# Código ainda não definido
Botão direito → Disassemble (D) (desassemblar)
```

---

<a id="-navigation"></a>
## 🧭 Navegação

### Ir para um endereço

```
# Atalho de teclado
G → Introduzir o endereço → OK

# Exemplos:
0x00401000      # Endereço hexadecimal
main            # Nome da função
entry           # Ponto de entrada
LAB_00401234    # Etiqueta
```

### Atalhos de navegação

| Atalho | Ação |
|----------|--------|
| `G` | Ir para um endereço |
| `Ctrl+G` | Ir para um endereço |
| `Alt+←` | Recuar na navegação |
| `Alt+→` | Avançar na navegação |
| `Ctrl+Shift+E` | Ir para o ponto de entrada |

### Referências cruzadas (XREFs)

```
# Ver referências para a localização atual
Botão direito → References → Show References To

# Encontrar onde a função é chamada
Selecionar a função → Botão direito → References → Show References To

# Teclado
Ctrl+Shift+F → Encontrar referências
```

### Pesquisar funções

```
# Pesquisar
Search → Memory (S)           # Pesquisar na memória
Search → For Strings          # Encontrar cadeias de texto
Search → For Direct References # Encontrar referências para o endereço
Search → Program Text         # Pesquisar na desassemblagem

# Pesquisa de funções
Symbol Tree → Filter → Escrever o nome da função
```

---

<a id="-decompiler"></a>
## 💻 Descompilador

### Abrir o descompilador

```
Window → Decompiler
# Ou clicar no separador Decompiler
# Descompila automaticamente a função selecionada
```

### Ler código descompilado

```c
// Exemplo de resultado da descompilação
undefined4 main(int param_1, char **param_2)
{
  char local_20 [16];
  
  printf("Enter password: ");
  scanf("%15s", local_20);
  if (strcmp(local_20, "secret123") == 0) {
    puts("Access granted!");
  }
  else {
    puts("Access denied!");
  }
  return 0;
}
```

### Melhorar a descompilação

```
# Mudar o nome de variáveis
Botão direito na variável → Rename Variable (L)

# Alterar o tipo de dados
Botão direito na variável → Retype Variable (Ctrl+L)

# Adicionar comentários
; → Definir comentário de fim de linha (EOL)
Ctrl+; → Definir um comentário de cabeçalho (plate comment)

# Definir a assinatura da função
Botão direito na função → Edit Function Signature
```

### Dicas do descompilador

| Ação | Como |
|--------|-----|
| Mudar o nome de uma variável | `L` ou botão direito |
| Alterar o tipo | `Ctrl+L` |
| Criar uma estrutura | Botão direito → Auto Create Structure |
| Separar/juntar variáveis | Botão direito → Split/Merge |
| Confirmar a assinatura | `P` |

---

<a id="-data-types--structures"></a>
## 📊 Tipos de dados e estruturas

### Criar uma estrutura

```
# Método 1: A partir do Data Type Manager
Data Type Manager → Botão direito → New → Structure

# Método 2: Criar automaticamente no descompilador
Decompiler → Botão direito na variável → Auto Create Structure
```

### Definir uma estrutura

```c
// Estrutura de exemplo
struct Player {
    char name[32];      // Deslocamento 0x00
    int health;         // Deslocamento 0x20
    int score;          // Deslocamento 0x24
    float position[3];  // Deslocamento 0x28
};
```

### Aplicar uma estrutura

```
# Na vista Listing
Selecionar o endereço → Botão direito → Data → Choose Data Type

# Ou
Premir T → Selecionar a estrutura

# No Decompiler
Botão direito na variável → Retype Variable → Selecionar a estrutura
```

### Vetores (arrays)

```
# Criar um vetor
Selecionar o endereço inicial → Botão direito → Data → Create Array
Introduzir o número de elementos
```

### Enumerações (enums)

```
# Criar uma enumeração
Data Type Manager → Botão direito → New → Enum

# Definir valores
enum Status {
    INACTIVE = 0,
    ACTIVE = 1,
    PAUSED = 2
};
```

---

<a id="-patching--modifying"></a>
## 🔧 Aplicação de patches e modificações

### Aplicar um patch a uma instrução

```
# Método 1: Teclado
Selecionar a instrução → Ctrl+Shift+G
Introduzir a nova instrução → Premir Enter

# Método 2: Menu
Botão direito → Patch Instruction
```

### Modificar dados

```
# Alterar bytes
Botão direito → Patch Data
Introduzir bytes em hexadecimal

# Ou
Selecionar os bytes → Ctrl+Shift+G
```

### Patches comuns

| Original | Modificado | Finalidade |
|----------|---------|---------|
| `JE` | `JMP` | Saltar sempre |
| `JNE` | `JMP` | Saltar sempre |
| `JE` | `NOP` | Nunca saltar |
| `CALL` | `NOP NOP NOP NOP NOP` | Ignorar a chamada |
| `74` | `75` | JE → JNE |
| `75` | `74` | JNE → JE |
| `74` | `EB` | JE → JMP short |

### Exportar o binário modificado

> **Precisão do formato:** exportar bytes em bruto não garante a reconstrução de um executável PE/ELF utilizável. O formato e as opções do exportador devem corresponder ao ficheiro pretendido; a documentação distingue Raw Bytes de Original File. Ver o [guia oficial dos exportadores](https://github.com/NationalSecurityAgency/ghidra/blob/master/Ghidra/Features/Base/src/main/help/help/topics/ExporterPlugin/exporter.htm).

```
File → Export Program
Format: Binary (formato: binário)
Selecionar o destino
```

### Sequência de NOPs (NOP sled)

```
# Preencher com NOPs
Selecionar o intervalo → Botão direito → Clear Code Block
Depois: Selecionar → Botão direito → Patch → Fill with NOPs
```

---

<a id="-scripting"></a>
## 🐍 Scripting

### Gestor de scripts

```
Window → Script Manager
# Ou: Tools → Script Manager
```

### Executar um script

```
Script Manager → Selecionar o script → Run (botão verde de reprodução)
# Ou fazer duplo clique no script
```

### Scripts Python (Ghidra Python)

> **Motor Python:** o exemplo usa uma f-string de Python 3. A integração atual PyGhidra suporta CPython 3; esse código não deve ser assumido compatível com o antigo Jython 2. Ver o [módulo oficial PyGhidra](https://github.com/NationalSecurityAgency/ghidra/blob/master/Ghidra/Features/PyGhidra/README.md).

```python
# Exemplo: Listar todas as funções
from ghidra.program.model.listing import Function

fm = currentProgram.getFunctionManager()
funcs = fm.getFunctions(True)  # True = ordem crescente

for func in funcs:
    print(f"Function: {func.getName()} at {func.getEntryPoint()}")
```

### Operações comuns em scripts

```python
# Obter o endereço atual
currentAddress

# Obter o programa atual
currentProgram

# Obter o gestor de funções
fm = currentProgram.getFunctionManager()

# Obter a listagem (desassemblagem)
listing = currentProgram.getListing()

# Obter a instrução no endereço
instr = listing.getInstructionAt(addr)

# Obter os bytes no endereço
memory = currentProgram.getMemory()
bytes = getBytes(addr, length)

# Criar uma etiqueta
createLabel(addr, "my_label", True)

# Adicionar um comentário
setEOLComment(addr, "My comment")

# Obter referências
refMgr = currentProgram.getReferenceManager()
refs = refMgr.getReferencesTo(addr)
```

### Scripts incluídos úteis

| Script | Finalidade |
|--------|---------|
| FindCrypt | Encontrar constantes criptográficas |
| FunctionID | Identificar funções de bibliotecas |
| ExportFunctionInfoScript | Exportar a lista de funções |
| FindStrings | Encontrar referências a cadeias de texto |
| SearchMemory | Pesquisar padrões |

---

<a id="-keyboard-shortcuts"></a>
## ⌨️ Atalhos de teclado

### Navegação

| Atalho | Ação |
|----------|--------|
| `G` | Ir para um endereço |
| `Alt+←` | Recuar |
| `Alt+→` | Avançar |
| `Ctrl+Shift+E` | Ponto de entrada |
| `Ctrl+E` | Exportar a tabela |
| `N` | Próxima posição não definida |

### Análise

| Atalho | Ação |
|----------|--------|
| `F` | Criar uma função |
| `D` | Desassemblar |
| `C` | Limpar a definição |
| `P` | Criar um ponteiro |
| `T` | Aplicar um tipo de dados |
| `A` | Analisar automaticamente |

### Edição

| Atalho | Ação |
|----------|--------|
| `L` | Mudar o nome (etiqueta/variável) |
| `;` | Definir um comentário de fim de linha |
| `Ctrl+;` | Definir um comentário de cabeçalho |
| `Ctrl+L` | Alterar o tipo da variável |
| `Ctrl+Shift+G` | Modificar uma instrução |

### Pesquisar

| Atalho | Ação |
|----------|--------|
| `Ctrl+Shift+F` | Encontrar referências |
| `Ctrl+F` | Pesquisar texto |
| `S` | Pesquisar na memória |
| `Ctrl+B` | Pesquisar bytes |

### Janelas

| Atalho | Ação |
|----------|--------|
| `Ctrl+D` | Mostrar/ocultar o descompilador |
| `Ctrl+T` | Mostrar/ocultar o gestor de tipos de dados |
| `Ctrl+S` | Árvore de símbolos |

---

<a id="-quick-reference"></a>
## 📊 Referência rápida

### Sequência de análise

```
1. Importar o binário      → File → Import
2. Analisar automaticamente → Analysis → Auto Analyze (aceitar predefinições)
3. Encontrar entry/main    → G → main ou entry
4. Rever as funções       → Symbol Tree → Functions
5. Examinar cadeias de texto → Search → For Strings
6. Seguir a execução      → Acompanhar chamadas e saltos
7. Mudar nomes de símbolos → L em funções ou variáveis
8. Documentar             → Adicionar comentários (;)
```

### Tarefas comuns

| Tarefa | Como |
|------|-----|
| Encontrar main() | `G` → `main` ou pesquisar em Symbol Tree |
| Ver cadeias de texto | Search → For Strings |
| Encontrar operações criptográficas | Executar o script FindCrypt |
| Verificar importações | Symbol Tree → Imports |
| Encontrar referências cruzadas | Selecionar → Ctrl+Shift+F |
| Criar uma função | Selecionar o endereço → `F` |
| Descompilar | Window → Decompiler |

### Lista de verificação de engenharia inversa

```
☐ Identificar o tipo de ficheiro (PE, ELF, etc.)
☐ Executar a análise automática
☐ Encontrar o ponto de entrada / main
☐ Procurar pistas nas cadeias de texto
☐ Identificar funções de interesse
☐ Mapear o fluxo do programa
☐ Analisar funções suspeitas
☐ Documentar as descobertas com comentários
☐ Mudar o nome de funções e variáveis
☐ Criar estruturas conforme necessário
```

### Convenções de chamada de funções

| Convenção | Retorno | Argumentos | Limpeza da pilha |
|------------|--------|------|----------------|
| **cdecl** | EAX | Pilha (R→L) | Chamador |
| **stdcall** | EAX | Pilha (R→L) | Função chamada |
| **fastcall** | EAX | ECX, EDX, pilha | Função chamada |
| **x64 Win** | RAX | RCX, RDX, R8, R9 | Chamador |
| **x64 Linux** | RAX | RDI, RSI, RDX, RCX, R8, R9 | Chamador |

---

## 📚 Recursos

- [Site oficial do Ghidra](https://ghidra-sre.org/)
- [Ghidra GitHub](https://github.com/NationalSecurityAgency/ghidra)
- [Documentação do Ghidra](https://ghidra.re/ghidra_docs/api/)
- [Awesome Ghidra](https://github.com/AllsafeCyberSecurity/awesome-ghidra)

### Guias de referência relacionados
- [Binwalk](../../Binwalk/README.md)
- [Comandos Linux](../../Linux-Commands/README.md)
- [Volatility](../../Volatility/README.md)

---

<p align="center">
  <b>🔍 Domina a engenharia inversa!</b><br>
  <i>Ghidra — A oferta da NSA à comunidade de segurança</i>
</p>
