# 🔄 Guia de referência de engenharia inversa para CTF

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](Reverse-Engineering-CTF.tr.md) • [🇬🇷 Ελληνικά](Reverse-Engineering-CTF.el.md)

> **Notas de precisão:** Disassembly (desassemblagem) converte instruções de máquina em assembly; descompilação tenta reconstruir pseudocódigo. No [Ghidra](https://ghidra.re/ghidra_docs/GhidraClass/Beginner/Introduction_to_Ghidra_Student_Guide.html), o guia oficial abre a janela de descompilação em `Window → Decompiler`; a indicação genérica de F5 no original não deve ser assumida como atalho predefinido do Ghidra. As opções de descompilação no IDA dependem da edição, versão e arquitetura suportadas. Os registos `$rdi`/`$rsp` são exemplos de x86-64; os endereços são ilustrativos. `0x90` é NOP em x86 e o índice `0x1234` do exemplo Python é um deslocamento no ficheiro, não necessariamente um endereço virtual. Os nomes de menus e os valores literais foram preservados.

---

## 🔍 Análise inicial

```bash
# Tipo de ficheiro
file binary

# Cadeias de caracteres
strings binary | grep -i flag
strings -n 8 binary

# Informações ELF
readelf -h binary

# Verificar as proteções
checksec binary
```

---

## 🛠️ Desassembladores (disassemblers)

### Ghidra (gratuito)
```
1. File → Import File (importar o ficheiro)
2. Analyze → Auto Analyze (análise automática)
3. Procurar a função main()
4. Procurar strcmp/memcmp nas verificações da palavra-passe
5. F5 para descompilar (indicação do original; ver nota)
```

### IDA Free
```
1. Abrir o binário
2. F5 para obter pseudocódigo (Hex-Rays)
3. X para procurar referências cruzadas
4. N para mudar o nome das variáveis
```

### Radare2
```bash
r2 binary
aaa          # Analisar
afl          # Listar as funções
pdf @main    # Desassemblar a função main
VV           # Modo visual
```

---

## 🐛 Depuração com GDB

```bash
# Iniciar
gdb ./binary

# Pontos de interrupção (breakpoints)
b main
b *0x401234

# Executar
r
r arg1 arg2

# Avançar passo a passo
n           # Linha seguinte
s           # Entrar na chamada
c           # Continuar

# Inspecionar
x/s $rdi    # Cadeia de caracteres
x/10x $rsp  # 10 valores hexadecimais da pilha
info reg    # Registos do processador

# Procurar a flag
find 0x0, 0xffffffff, "flag"
```

### GEF/PEDA
```bash
# Instalar o GEF
bash -c "$(curl -fsSL https://gef.blah.cat/sh)"

# Comandos
vmmap
heap
telescope $rsp
```

---

## 🐍 Alteração de binários com Python

```python
# Ler o binário
with open("binary", "rb") as f:
    data = bytearray(f.read())

# Alterar bytes
data[0x1234] = 0x90  # NOP

# Escrever
with open("patched", "wb") as f:
    f.write(data)
```

---

## 📊 Padrões comuns

| Verificação | Contorno |
|-------|--------|
| `strcmp(input, "password")` | Extrair "password" |
| `if (x == 0x1337)` | Encontrar o valor 0x1337 |
| Antidepuração | Alterar a chamada ptrace |
| Cadeias de caracteres ofuscadas | Análise dinâmica |

---

## 📋 Lista de verificação de engenharia inversa

```markdown
□ Executar os comandos file/strings
□ Verificar a presença de símbolos de depuração
□ Abrir no Ghidra/IDA
□ Encontrar a função main
□ Procurar comparações de cadeias de caracteres
□ Seguir os dados introduzidos pelo utilizador
□ Alterar as verificações antidepuração, se necessário
□ Análise dinâmica com GDB
```

---

**Voltar a CTF:** [🏁 Visão geral de CTF](README.pt.md)
