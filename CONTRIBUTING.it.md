# 🤝 Contribuire al progetto Cheatsheets di Hacking

Innanzitutto, grazie per aver considerato di contribuire a Hacking Cheatsheets! 🎉

Questo documento fornisce le linee guida per contribuire a questo repository.

---

## 📋 Tabella dei Contenuti

- [Codice di Condotta](#-codice-di-condotta)
- [Come posso contribuire?](#-come-posso-contribuire)
- [Linee guida per le Cheatsheet](#-linee-guida-per-le-cheatsheet)
- [Guida allo Stile](#-guida-allo-stile)
- [Processo di Pull Request](#-processo-di-pull-request)
- [Riconoscimenti](#-riconoscimenti)

---

## 📜 Codice di Condotta

### Il nostro impegno

Ci impegniamo a fornire un ambiente amichevole, sicuro e accogliente per tutti i collaboratori.

### Standard

- ✅ Sii rispettoso e inclusivo
- ✅ Fornisci feedback costruttivi
- ✅ Focalizzati su ciò che è meglio per la community
- ❌ Niente molestie o discriminazioni
- ❌ Niente contenuti illegali o istruzioni per uso malevolo

### Linee Guida Etiche

> ⚠️ **Tutti i contributi devono essere esclusivamente a scopo educativo ed etico.**
> 
> Non accettiamo:
> - Contenuti che promuovono attività illegali
> - Istruzioni per accessi non autorizzati
> - Malware o codice exploit destinato a uso malevolo

---

## 🎯 Come posso contribuire?

### 📝 Aggiungere nuove Cheatsheet

Hai esperienza con uno strumento di sicurezza non ancora trattato? Crea una nuova cheatsheet!

1. Controlla se lo strumento è elencato nelle [Cheatsheets Programmate](README.it.md#-cheatsheets-programmate)
2. Crea una nuova cartella per lo strumento
3. Segui le [Linee guida per le Cheatsheet](#-linee-guida-per-le-cheatsheet)
4. Invia una pull request

### 🔧 Migliorare Cheatsheet esistenti

- Correggi refusi o errori grammaticali
- Aggiungi comandi o opzioni mancanti
- Migliora le spiegazioni con esempi migliori
- Aggiorna informazioni obsolete
- Aggiungi tabelle di riferimento rapido

### 🐛 Segnalare Problemi (Issues)

Hai trovato un errore o hai un suggerimento?

1. Controlla se l'issue esiste già
2. Crea una nuova issue con:
   - Descrizione chiara
   - Passaggi per riprodurre (se applicabile)
   - Correzione suggerita (se ne hai una)

### 🌐 Traduzioni

Aiutaci a rendere queste cheatsheet accessibili a chi non parla inglese:

1. Crea una cartella come `Metasploit/translations/`
2. Aggiungi i file tradotti con il suffisso della lingua: `README.it.md`, `README.es.md`
3. Segui la stessa struttura dell'originale

---

## 📖 Linee guida per le Cheatsheet

### Struttura

Ogni cheatsheet dovrebbe seguire questa struttura:

```markdown
# 🔴 Nome del Tool - Cheatsheet Completa

[Art Banner ASCII - Opzionale]

[Badges]

> Breve descrizione dello strumento

---

## 📋 Tabella dei Contenuti
- [Sezione 1](#section-1)
- [Sezione 2](#section-2)
...

---

## 🎯 Introduzione
Cos'è questo strumento? Perché si usa?

---

## 📥 Installazione
Come installare lo strumento

---

## ⌨️ Comandi Base
Comandi essenziali con esempi

---

## 🎬 Esempi reali
Casi d'uso pratici

---

## 📊 Tabella di Riferimento Rapida
Tabella riassuntiva dei comandi

---

## 💡 Consigli & Best Practice

---

## ⚠️ Disclaimer Legale

---

## 📚 Risorse
Link alla documentazione ufficiale e risorse per l'apprendimento

```

### Sezioni Richieste

| Sezione | Richiesta | Descrizione |
| --- | --- | --- |
| Titolo con emoji | ✅ | Nome chiaro dello strumento |
| Badges | ✅ | Almeno 2-3 badge pertinenti |
| Tabella dei Contenuti | ✅ | Link a tutte le sezioni |
| Introduzione | ✅ | Cosa e perché |
| Basic Commands | ✅ | Funzionalità principali |
| Quick Reference | ✅ | Tabella riassuntiva |
| Legal Disclaimer | ✅ | Avviso sull'uso etico |
| Installazione | 📋 | Raccomandata |
| Esempi | 📋 | Fortemente raccomandati |
| Tips | 📋 | Raccomandati |

---

## 🎨 Guida allo Stile

### Formattazione Markdown

````markdown
# H1 - Titolo Principale (solo uno per file)
## H2 - Sezioni Maggiori
### H3 - Sottosezioni
#### H4 - Sezioni Minori

**Grassetto** per enfatizzare
`code` per comandi e nomi di file
```blocchi di codice``` per comandi su più righe
> Blockquotes per note e avvisi
````

### Blocchi di Codice

Specifica sempre il linguaggio per l'evidenziazione della sintassi:

````markdown
# Corretto - con linguaggio
```bash
msfconsole -q
```

# Errato - senza linguaggio
```
msfconsole -q
```
````

### Tabelle

Usa le tabelle per i riferimenti ai comandi:

```markdown
| Comando | Descrizione | Esempio |
|---------|-------------|---------|
| `cmd1` | Fa X | `cmd1 -opzione` |
| `cmd2` | Fa Y | `cmd2 valore` |
```

### Emoji

Usa le emoji in modo coerente:

| Emoji | Utilizzo |
| --- | --- |
| 🔴 | Exploitation/Attacco |
| 🔍 | Reconnaissance |
| 🌐 | Web/Rete |
| 🔓 | Credentials/Access |
| ⚠️ | Warning |
| 💡 | Tip |
| ✅ | Do/Good |
| ❌ | Don't/Bad |
| 📋 | Reference |
| 🎯 | Target/Goal |

### Badges

Usa i badge di shields.io:

```markdown
![Badge Name](https://img.shields.io/badge/Label-Message-color?style=for-the-badge)
```

Colori comuni: `red`, `blue`, `green`, `orange`, `purple`

---

## 🔄 Processo di Pull Request

### Prima di inviare

1. ✅ Leggi le linee guida per contribuire (questo documento)
2. ✅ Cerca PR esistenti che trattano lo stesso problema
3. ✅ Testa tutti i comandi e gli esempi
4. ✅ Controlla ortografia e grammatica
5. ✅ Assicurati della corretta formattazione

### Inviare una PR

1. **Fork** del repository
2. **Crea** un feature branch:
```bash
git checkout -b feature/add-nmap-cheatsheet
```
3. **Commit** delle tue modifiche:
```bash
git commit -m "Add: Nmap cheatsheet with scanning examples"
```
4. **Push** sul tuo fork:
```bash
git push origin feature/add-nmap-cheatsheet
```
5. **Apri** una Pull Request

### Formato del Messaggio di Commit

```
Type: Breve descrizione

- Modifica dettagliata 1
- Modifica dettagliata 2

Closes #123 (se applicabile)
```

Tipologie (Types):

* `Add:` Nuovi contenuti
* `Fix:` Correzione bug
* `Update:` Aggiornamenti a contenuti esistenti
* `Docs:` Modifiche alla documentazione
* `Style:` Modifiche alla formattazione

### Processo di Revisione

1. Il Maintainer revisiona la PR
2. Viene fornito un feedback se sono necessarie modifiche
3. Una volta approvata, la PR viene unita (merge)
4. Il tuo contributo viene accreditato!

---

## 🏆 Riconoscimenti

Tutti i collaboratori riceveranno un riconoscimento:

* Aggiunta alla lista dei Collaboratori
* Menzionati nelle release notes
* Grafico dei contributi di GitHub

### Hall of Fame

*I collaboratori che forniranno contributi significativi saranno messi in risalto qui!*

---

## ❓ Domande?

Se hai domande:

1. Controlla le issue esistenti
2. Crea una nuova issue con l'etichetta `question`
3. Sii paziente per la risposta

---

<p align="center">
  <b>Grazie per il tuo contributo! 🙏</b>
  <i>Insieme rendiamo la conoscenza della cybersecurity più accessibile</i>
</p>
