# 🎭 Schede di riferimento sul social engineering

<!-- language-home-navigation -->
[🏠 Pagina iniziale italiana](../../README.it.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../README.md) • [🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
███████╗ ██████╗  ██████╗██╗ █████╗ ██╗         ███████╗███╗   ██╗ ██████╗ ██╗███╗   ██╗███████╗███████╗██████╗ ██╗███╗   ██╗ ██████╗ 
██╔════╝██╔═══██╗██╔════╝██║██╔══██╗██║         ██╔════╝████╗  ██║██╔════╝ ██║████╗  ██║██╔════╝██╔════╝██╔══██╗██║████╗  ██║██╔════╝ 
███████╗██║   ██║██║     ██║███████║██║         █████╗  ██╔██╗ ██║██║  ███╗██║██╔██╗ ██║█████╗  █████╗  ██████╔╝██║██╔██╗ ██║██║  ███╗
╚════██║██║   ██║██║     ██║██╔══██║██║         ██╔══╝  ██║╚██╗██║██║   ██║██║██║╚██╗██║██╔══╝  ██╔══╝  ██╔══██╗██║██║╚██╗██║██║   ██║
███████║╚██████╔╝╚██████╗██║██║  ██║███████╗    ███████╗██║ ╚████║╚██████╔╝██║██║ ╚████║███████╗███████╗██║  ██║██║██║ ╚████║╚██████╔╝
╚══════╝ ╚═════╝  ╚═════╝╚═╝╚═╝  ╚═╝╚══════╝    ╚══════╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝╚═╝  ╚═══╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝ ╚═════╝ 
```

---

## 🎯 Che cos'è il social engineering?

Il social engineering (ingegneria sociale) consiste nel manipolare le persone affinché rivelino informazioni riservate o compiano azioni che compromettono la sicurezza. Sfrutta la psicologia umana anziché le vulnerabilità tecniche.

**Principi fondamentali:**
- 🎭 **Autorità** - Le persone tendono a fidarsi delle figure autorevoli
- ⏰ **Urgenza** - La pressione del tempo riduce il pensiero critico
- 🤝 **Reciprocità** - Le persone si sentono in dovere di ricambiare i favori
- 👥 **Riprova sociale** - Le persone seguono il comportamento degli altri
- 😊 **Simpatia** - Le persone assecondano più facilmente chi apprezzano
- 📉 **Scarsità** - Una disponibilità limitata aumenta il desiderio

---

## 📖 Guide al social engineering

| Argomento | Descrizione | Guida |
|-------|-------------|-------|
| **Phishing** | Tecniche e strumenti di phishing via email, SMS e voce | [📄 Visualizza](./Phishing.it.md) |
| **Pretexting** | Creazione di scenari e identità fittizie credibili | [📄 Visualizza](./Pretexting.it.md) |

---

## 🛠️ Strumenti essenziali per il social engineering

| Strumento | Scopo | Tipo |
|------|---------|------|
| **GoPhish** | Framework per campagne di phishing | Phishing |
| **SET (Social Engineering Toolkit)** | Framework completo per il social engineering | Multifunzione |
| **Evilginx2** | Phishing MITM, con intermediario nella comunicazione (bypass 2FA) | Phishing |
| **King Phisher** | Piattaforma per campagne di phishing | Phishing |
| **Modlishka** | Phishing tramite reverse proxy | Phishing |
| **theHarvester** | Raccolta di indirizzi email e domini | Ricognizione |
| **Strumenti per LinkedIn** | Individuazione dei dipendenti | OSINT |
| **Maltego** | OSINT e mappatura delle relazioni | Ricognizione |

> **Nota sulla versione:** il download di GoPhish è fissato a `v0.12.1`; i comandi originali sono conservati. Vedi la [documentazione ufficiale di GoPhish](https://docs.getgophish.com/user-guide/installation).

### Installazione rapida degli strumenti
```bash
# GoPhish
wget https://github.com/gophish/gophish/releases/download/v0.12.1/gophish-v0.12.1-linux-64bit.zip
unzip gophish-v0.12.1-linux-64bit.zip
./gophish

# SET (Social Engineering Toolkit)
git clone https://github.com/trustedsec/social-engineer-toolkit
cd social-engineer-toolkit
pip install -r requirements.txt
python setoolkit

# Evilginx2
go install github.com/kgretzky/evilginx2@latest

# theHarvester
pip install theHarvester
theHarvester -d target.com -b all
```

---

## 📊 Tipi di attacchi di social engineering

| Tipo di attacco | Descrizione | Vettore |
|-------------|-------------|--------|
| **Phishing** | Email ingannevoli per sottrarre credenziali | Email |
| **Spear phishing** | Phishing mirato a persone specifiche | Email |
| **Whaling** | Phishing rivolto ai dirigenti (C-level) | Email |
| **Vishing** | Social engineering tramite voce o telefono | Telefono |
| **Smishing** | Phishing tramite SMS | SMS |
| **Pretexting** | Creazione di scenari fittizi per ottenere informazioni | Qualsiasi |
| **Baiting** | Abbandono di supporti infetti (USB e simili) come esca | Fisico |
| **Quid pro quo** | Offerta di qualcosa in cambio di informazioni | Qualsiasi |
| **Tailgating** | Ingresso in un'area protetta seguendo una persona autorizzata | Fisico |
| **Watering hole** | Compromissione dei siti visitati dai bersagli | Web |

---

## 🔗 Riferimento rapido: indicatori di phishing

### Segnali di allarme nelle email
```
□ L'indirizzo del mittente non corrisponde al dominio aziendale
□ Saluti generici ("Gentile cliente")
□ Urgenza o minacce
□ Errori ortografici o grammaticali
□ Link sospetti (passare il puntatore per verificarli)
□ Allegati inattesi
□ Richieste di informazioni sensibili
□ Offerte troppo allettanti per essere vere
```

### Analisi degli URL
```bash
# Controlla l'età del dominio
whois suspicious-domain.com

# Controlla la reputazione
https://www.virustotal.com
https://urlscan.io

# Analizza gli URL abbreviati
https://checkshorturl.com
```

---

## 📚 Risorse

- [OWASP: social engineering](https://owasp.org/www-community/attacks/Social_Engineering)
- [MITRE ATT&CK: accesso iniziale](https://attack.mitre.org/tactics/TA0001/)
- [Phishing.org](https://www.phishing.org/)
- [Framework per il social engineering](https://www.social-engineer.org/)

---

<p align="center">
  <b>🎭 Usa il social engineering solo per test autorizzati!</b>
</p>
