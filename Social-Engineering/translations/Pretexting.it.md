# 🎭 Scheda di riferimento sul pretexting

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../Pretexting.md) • [🇹🇷 Türkçe Sürüm](Pretexting.tr.md) • [🇬🇷 Ελληνικά](Pretexting.el.md)

```
██████╗ ██████╗ ███████╗████████╗███████╗██╗  ██╗████████╗██╗███╗   ██╗ ██████╗ 
██╔══██╗██╔══██╗██╔════╝╚══██╔══╝██╔════╝╚██╗██╔╝╚══██╔══╝██║████╗  ██║██╔════╝ 
██████╔╝██████╔╝█████╗     ██║   █████╗   ╚███╔╝    ██║   ██║██╔██╗ ██║██║  ███╗
██╔═══╝ ██╔══██╗██╔══╝     ██║   ██╔══╝   ██╔██╗    ██║   ██║██║╚██╗██║██║   ██║
██║     ██║  ██║███████╗   ██║   ███████╗██╔╝ ██╗   ██║   ██║██║ ╚████║╚██████╔╝
╚═╝     ╚═╝  ╚═╝╚══════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 Che cos'è il pretexting?

Il pretexting consiste nel creare uno scenario inventato (un pretesto) per avvicinare un bersaglio, ottenere informazioni o indurlo a compiere un'azione. È alla base della maggior parte degli attacchi di social engineering.

**Componenti fondamentali:**
- 🎭 **Identità fittizia (persona)** - L'identità falsa che si assume
- 📖 **Storia** - Lo scenario o la motivazione credibile
- 🎯 **Obiettivo** - Ciò che si vuole ottenere
- 🔧 **Materiali di supporto (props)** - Badge, email e altri elementi a sostegno del pretesto

---

## 👥 Identità fittizie comuni

### Assistenza IT
```
Ruolo: tecnico dell'help desk IT
Obiettivo: ripristino della password, accesso remoto, installazione di software

"Ciao, sono Mike del reparto IT. Stiamo rilevando un'attività insolita
sul tuo account e dobbiamo verificare le tue
credenziali per evitare un blocco."
```

### Fornitore o appaltatore
```
Ruolo: fornitore esterno (climatizzazione, sicurezza, pulizie)
Obiettivo: accesso fisico, accesso alla rete

"Sono qui per conto di [Vendor Company] per la manutenzione
programmata. Può farmi entrare nella sala server?"
```

### Nuovo dipendente
```
Ruolo: neoassunto che ha bisogno di aiuto
Obiettivo: raccolta di informazioni, accesso all'edificio

"Ciao, ho appena iniziato a lavorare nel marketing e ho
problemi ad accedere all'unità condivisa. Puoi aiutarmi?"
```

### Dirigente o figura autorevole
```
Ruolo: CEO, CFO, alto dirigente
Obiettivo: richieste urgenti, bonifici, accesso

"È urgente. Il CEO ha bisogno di questo report immediatamente
e ho bisogno che tu salti le procedure ordinarie."
```

### Addetto alle consegne
```
Ruolo: FedEx, UPS, corriere
Obiettivo: accesso fisico, consegna di un pacco

"Ho un pacco per cui è necessaria una firma.
Può farmi entrare alla reception?"
```

---

## 📋 Scenari di pretexting

### Pretesti telefonici

#### Ripristino della password da parte dell'IT
```
"Buongiorno, sono [Name] dell'help desk IT. Abbiamo
rilevato che il suo account è stato compromesso in un recente
incidente di sicurezza. Devo verificare la sua identità e
aiutarla a reimpostare la password. Può confermare la sua
password attuale per consentirmi di verificare che sia davvero lei?"
```

#### Verifica del fornitore
```
"Buongiorno, chiamo dall'ufficio contabilità fornitori di [Vendor]. Stiamo
aggiornando i nostri archivi e dobbiamo verificare i dati
bancari della sua azienda per il prossimo pagamento. Può confermare
il numero di conto che avete registrato per noi?"
```

#### Sondaggio delle risorse umane
```
"Buongiorno, sto conducendo un sondaggio sulla soddisfazione dei dipendenti
per le risorse umane. Il sondaggio è anonimo, ma devo verificare
che lei sia un dipendente. Può indicarmi il suo reparto
e il nome del suo responsabile?"
```

### Pretesti di persona

#### Ispettore antincendio
```
"Buongiorno, vengo dall'ufficio comunale di prevenzione incendi. Stiamo
effettuando ispezioni a sorpresa. Avrò bisogno di accedere alla
vostra sala server per controllare l'impianto antincendio."
```

#### Colloquio di lavoro
```
"Sono qui per il colloquio delle 2 PM con le risorse umane. Nell'attesa,
può indicarmi dov'è il bagno?"
(Approfitta dell'occasione per osservare l'edificio)
```

#### Addetto alla manutenzione
```
"Abbiamo ricevuto una segnalazione di perdita d'acqua in questa zona.
Può indicarmi dove si trova la sala server? Devo
controllare le tubature che passano sopra il soffitto."
```

---

## 🧠 Principi psicologici

### Autorità
```
✓ Usa titoli: "Direttore IT", "Responsabile della sicurezza"
✓ Vesti in modo coerente con il ruolo: abiti, uniformi, badge
✓ Parla con sicurezza
✓ Cita politiche e procedure
```

### Urgenza
```
✓ "Deve essere fatto prima della riunione del CEO alle 3"
✓ "Il sistema bloccherà il tuo accesso tra 10 minuti"
✓ "Siamo sottoposti a un audit e ne abbiamo bisogno subito"
✓ Crea pressione temporale per ostacolare le verifiche
```

### Reciprocità
```
✓ Fai prima un piccolo favore
✓ "Ho appena aiutato a risolvere quel problema con la stampante..."
✓ "Ho portato il caffè per tutti..."
✓ Crea un senso di obbligo a ricambiare il favore
```

### Riprova sociale
```
✓ "Tutti gli altri lo hanno già fatto"
✓ "Il tuo responsabile John lo ha già approvato"
✓ "Tutto il team marketing ha compilato il modulo"
✓ Cita altre persone che hanno acconsentito
```

### Simpatia
```
✓ Trova interessi comuni
✓ Rispecchia il linguaggio del corpo
✓ Fai complimenti sinceri
✓ Crea un rapporto prima di avanzare richieste
```

---

## 🔧 Oggetti e materiali di supporto

### Materiali fisici
```
□ Badge identificativi falsi
□ Biglietti da visita
□ Portablocco con moduli dall'aspetto ufficiale
□ Gilet ad alta visibilità o uniforme
□ Cassetta degli attrezzi o borsa per portatile
□ Email stampate e lettere di autorizzazione
□ Documentazione del fornitore
```

### Materiali digitali
```
□ Identificativo del chiamante falsificato
□ Indirizzo email falso (dominio simile)
□ Profilo LinkedIn falso
□ Sito aziendale clonato
□ Email di autorizzazione contraffatte
□ Inviti a riunioni falsi
```

### Documentazione
```
□ Numero del ticket di assistenza
□ Ordine di lavoro
□ Modulo di autorizzazione
□ Numero di riferimento
□ Nome del responsabile (ricavato tramite OSINT)
```

---

## 🔍 OSINT per il pretexting

### Ricerca sul bersaglio
```bash
# LinkedIn per informazioni sui dipendenti
- Nomi, ruoli, reparti
- Struttura gerarchica
- Post e attività recenti

# Sito dell'azienda
- Gruppo dirigente
- Organigramma
- Sedi degli uffici
- Informazioni su fornitori e partner

# Comunicati stampa
- Notizie e acquisizioni recenti
- Cambiamenti nei vertici aziendali
```

### Informazioni utili
```
□ Nomi e ruoli dei dipendenti
□ Struttura dei reparti
□ Nomi dei responsabili
□ Formato degli indirizzi email (first.last@company.com)
□ Numeri di telefono
□ Sedi degli uffici
□ Rapporti con i fornitori
□ Eventi aziendali recenti
```

---

## 📱 Social engineering fisico

### Tailgating (ingresso al seguito)
```
Tecnica: seguire una persona autorizzata attraverso una porta con accesso controllato

"Grazie, oggi ho dimenticato il badge!"
"Puoi tenermi aperta la porta? Ho le mani occupate."
"Sono qui con [Name] di [Department]."
```

### Piggybacking (ingresso con l'aiuto di una persona autorizzata)
```
Tecnica: convincere una persona autorizzata a farti entrare

"Buongiorno, sono di [Vendor]. Mi stanno aspettando, ma la
persona alla reception si è allontanata. Può farmi entrare?"
```

### Dumpster diving (ricerca nei rifiuti)
```
Bersaglio: documenti e hardware scartati

□ Organigrammi
□ Elenchi dei dipendenti
□ Documenti finanziari
□ Appunti con password
□ Vecchio hardware contenente dati
```

### Abbandono di chiavette USB (baiting)
```
Tecnica: lasciare chiavette USB infette

Luoghi:
□ Parcheggio
□ Area reception
□ Sale pausa
□ Vicino alle aree fumatori

Idee per le etichette:
□ "Riservato - Risorse umane"
□ "Informazioni sugli stipendi 2024"
□ "Elenco dei licenziamenti"
□ "Foto private"
```

---

## 📝 Modelli di copione

### Copione telefonico - Assistenza IT
```
[Squillo]
Bersaglio: "Pronto?"
Tu: "Buongiorno, parlo con [Target Name]?"
Bersaglio: "Sì."
Tu: "Bene, sono [Your Name] del team di sicurezza
IT. Stiamo monitorando alcuni tentativi di accesso insoliti
al suo account da un indirizzo IP estero.
Vogliamo assicurarci che non fosse lei. Oggi ha provato
ad accedere da [Random Country]?"
Bersaglio: "No, non ero io!"
Tu: "Lo immaginavo. Per sicurezza devo verificare
il suo account. Può confermare il suo nome utente e
procederemo al ripristino della password per bloccare l'attaccante?"
```

### Copione email - Richiesta di un dirigente
```
Oggetto: Urgente - Necessario un bonifico

Ciao [Name],

Sono in riunione e non posso telefonare. Ho bisogno che tu
esegua un bonifico urgente per un'acquisizione con tempi
molto stretti.

Trasferisci $45,000 al seguente conto:
[Details]

Ti spiegherò tutto al termine della riunione. Ti chiedo di
confermare una volta completato.

[Executive Name]
Inviato dal mio iPhone
```

---

## ⚠️ Considerazioni legali ed etiche

### Regole di ingaggio
```
□ Disponi sempre di un'autorizzazione scritta
□ Definisci chiaramente perimetro e limiti
□ Non accedere ad account personali
□ Non ricorrere a ricatti o coercizione
□ Documenta tutto
□ Tieni a disposizione contatti di emergenza
□ Tieni a portata di mano la lettera di autorizzazione al test
```

> **Precisazione terminologica:** la “get-out-of-jail letter” dell’originale è la lettera che documenta l’autorizzazione e il perimetro del test; il nome informale non indica un’immunità.

### Cosa evitare
```
✗ Spacciarsi per appartenenti alle forze dell'ordine
✗ Formulare minacce reali
✗ Accedere a sistemi fuori dal perimetro autorizzato
✗ Sottrarre denaro o beni reali
✗ Creare responsabilità legali
✗ Causare danni psicologici
```

---

## 📋 Checklist del pretexting

```markdown
Preparazione:
□ Studia l'organizzazione bersaglio (OSINT)
□ Individua il personale chiave
□ Elabora l'identità fittizia e la sua storia
□ Prepara materiali e oggetti di supporto
□ Esercitati sul copione e sulle risposte
□ Prepara storie alternative

Esecuzione:
□ Mantieni calma e sicurezza
□ Mantieni il controllo della conversazione
□ Preparati a cambiare approccio se vieni messo in discussione
□ Non esagerare nel convincere e non fornire troppi dettagli
□ Riconosci quando è il momento di interrompere

Dopo l'attività:
□ Documenta cosa ha funzionato e cosa no
□ Metti al sicuro tutti i dati raccolti
□ Riporta i risultati
□ Fornisci raccomandazioni
```

---

## 🔗 Schede correlate

- [Phishing](./Phishing.it.md)
- [OSINT - Google Dorking](../../Google-Dorking/README.md)

---

**Torna alla panoramica:** [🎭 Social engineering](./README.it.md)
