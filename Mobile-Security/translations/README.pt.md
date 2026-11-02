# 📱 Guias de referência de segurança móvel

<!-- language-home-navigation -->
[🏠 Página inicial em português](../../README.pt.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
███╗   ███╗ ██████╗ ██████╗ ██╗██╗     ███████╗    ███████╗███████╗ ██████╗██╗   ██╗██████╗ ██╗████████╗██╗   ██╗
████╗ ████║██╔═══██╗██╔══██╗██║██║     ██╔════╝    ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██║╚══██╔══╝╚██╗ ██╔╝
██╔████╔██║██║   ██║██████╔╝██║██║     █████╗      ███████╗█████╗  ██║     ██║   ██║██████╔╝██║   ██║    ╚████╔╝ 
██║╚██╔╝██║██║   ██║██╔══██╗██║██║     ██╔══╝      ╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██║   ██║     ╚██╔╝  
██║ ╚═╝ ██║╚██████╔╝██████╔╝██║███████╗███████╗    ███████║███████╗╚██████╗╚██████╔╝██║  ██║██║   ██║      ██║   
╚═╝     ╚═╝ ╚═════╝ ╚═════╝ ╚═╝╚══════╝╚══════╝    ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝   ╚═╝      ╚═╝   
```

> **Termos usados:** pentest é um teste de intrusão; instrumentação dinâmica permite observar ou alterar o comportamento de uma aplicação durante a execução. Root e jailbreak dão acesso privilegiado ao dispositivo Android ou iOS. SSL pinning (também chamado certificate pinning) restringe os certificados ou chaves aceites pela aplicação; o termo histórico “SSL” é mantido nos nomes dos comandos. As instruções abaixo preservam os exemplos do original e dependem da versão das ferramentas.

---

## 🎯 Testes de segurança de aplicações móveis

Os testes de segurança de aplicações móveis procuram identificar vulnerabilidades nas seguintes áreas:
- 📦 **Pacote da aplicação** — análise de APK/IPA
- 💾 **Armazenamento de dados** — armazenamento local inseguro
- 🔐 **Autenticação** — mecanismos de autenticação frágeis
- 🌐 **Rede** — comunicações inseguras
- 🔑 **Criptografia** — cifragem frágil
- 🛡️ **Plataforma** — vulnerabilidades específicas do sistema operativo

---

## 📖 Guias de segurança móvel

| Plataforma | Descrição | Guia |
|----------|-------------|-------|
| **Android** | Análise de APK, Frida, contorno da deteção de root | [📄 Ver](./Android-Pentesting.pt.md) |
| **iOS** | Análise de IPA, jailbreak, Objection | [📄 Ver](./iOS-Pentesting.pt.md) |

---

## 🛠️ Ferramentas essenciais de segurança móvel

| Ferramenta | Plataforma | Finalidade |
|------|----------|---------|
| **Frida** | Ambas | Instrumentação dinâmica |
| **Objection** | Ambas | Exploração de aplicações móveis durante a execução |
| **MobSF** | Ambas | Análise automática de segurança |
| **jadx** | Android | Descompilação de APK |
| **apktool** | Android | Engenharia inversa de APK |
| **Burp Suite** | Ambas | Interceção de tráfego |
| **Ghidra** | Ambas | Análise de binários |
| **drozer** | Android | Avaliação de segurança |

### Instalação rápida das ferramentas
```bash
# Frida
pip install frida-tools

# Objection
pip install objection

# MobSF (Docker)
docker pull opensecurity/mobile-security-framework-mobsf
docker run -p 8000:8000 opensecurity/mobile-security-framework-mobsf

# jadx
# Descarregar de https://github.com/skylot/jadx/releases

# apktool
# Descarregar de https://ibotpeaches.github.io/Apktool/install/

# drozer
pip install drozer
```

---

## 📊 OWASP Mobile Top 10 (2024)

| # | Vulnerabilidade | Descrição |
|---|---------------|-------------|
| M1 | Utilização incorreta de credenciais | Credenciais embutidas no código, armazenamento inseguro |
| M2 | Segurança inadequada da cadeia de fornecimento | SDKs maliciosos, bibliotecas comprometidas |
| M3 | Autenticação/autorização inseguras | Autenticação frágil, falhas no controlo de acesso |
| M4 | Validação insuficiente de entradas/saídas | Injeção, XSS em WebViews |
| M5 | Comunicação insegura | Ausência de TLS, problemas com certificados |
| M6 | Controlos de privacidade inadequados | Divulgação de informação pessoal identificável (PII), permissões excessivas |
| M7 | Proteções insuficientes dos binários | Ausência de ofuscação, adulteração |
| M8 | Configuração de segurança incorreta | Depuração ativada, predefinições inseguras |
| M9 | Armazenamento inseguro de dados | Armazenamento em texto simples, registos |
| M10 | Criptografia insuficiente | Algoritmos fracos, chaves embutidas no código |

---

## 🔗 Referência rápida: comandos Frida

> **Nota de versão:** os comentários do original que dizem “associar” junto de `frida -U -f` são imprecisos: `-f` inicia uma nova instância (*spawn*). A opção legada `--no-pause` foi preservada; a implementação atual retoma a execução por predefinição e oferece `--pause`. Ver o código oficial de [seleção do alvo](https://github.com/frida/frida-tools/blob/main/frida_tools/application.py) e da [consola Frida](https://github.com/frida/frida-tools/blob/main/frida_tools/repl.py).

```bash
# Listar os processos em execução
frida-ps -U

# Associar ao processo
frida -U -f com.app.package

# Iniciar uma nova instância com um script
frida -U -f com.app.package -l script.js --no-pause

# Exploração com Objection
objection -g com.app.package explore
```

---

## 📚 Recursos

- [Segurança móvel OWASP](https://owasp.org/www-project-mobile-security/)
- [OWASP MASTG](https://mas.owasp.org/MASTG/)
- [Documentação do Frida](https://frida.re/docs/)
- [HackTricks Mobile](https://book.hacktricks.xyz/mobile-pentesting)

---

<p align="center">
  <b>📱 Testa a segurança de aplicações móveis com responsabilidade!</b>
</p>
