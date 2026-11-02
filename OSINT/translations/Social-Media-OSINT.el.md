# 📱 OSINT — Έρευνα κοινωνικών δικτύων

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

<p align="center">
  <a href="../Social-Media-OSINT.md">🇬🇧 English</a> • <a href="Social-Media-OSINT.tr.md">🇹🇷 Türkçe</a>
</p>

[🔍 Ελληνική επισκόπηση OSINT](README.el.md)

---

## Πριν ξεκινήσετε

Η έρευνα **κοινωνικών δικτύων** αναζητά δημόσια προφίλ, αναρτήσεις και συσχετίσεις. Το **username** είναι το αναγνωριστικό ενός λογαριασμού, ενώ το εμφανιζόμενο όνομα μπορεί να είναι διαφορετικό. Το ίδιο username σε δύο πλατφόρμες δεν αποδεικνύει ότι οι λογαριασμοί ανήκουν στο ίδιο πρόσωπο.

Οι αριθμοί υποστηριζόμενων ιστοτόπων προέρχονται από το πρωτότυπο. Η πραγματική κάλυψη εξαρτάται από την έκδοση του εργαλείου, αλλαγές της πλατφόρμας και περιορισμούς πρόσβασης. Το αποτέλεσμα «βρέθηκε» ή «δεν βρέθηκε» χρειάζεται έλεγχο στην πηγή. Μια φόρμα σύνδεσης ή σελίδα σφάλματος μπορεί να επηρεάσει έναν αυτοματοποιημένο έλεγχο.

Τα URL μέσα στα παραδείγματα ανοίγουν σε browser και δεν είναι εντολές shell. Κάποια μπλοκ χωρίς δηλωμένη γλώσσα περιέχουν πραγματικές εντολές εγκατάστασης· διατηρούνται όπως στο πρωτότυπο. Η αναφορά Discord, Telegram ή άλλης πλατφόρμας στη λίστα ελέγχου αφορά πληροφορίες στις οποίες επιτρέπεται η πρόσβαση.

## Περιεχόμενα

- [🔍 Εργαλεία αναζήτησης ονομάτων χρήστη](#section-01)
- [📊 Έρευνα ανά πλατφόρμα](#section-02)
- [🛠️ Σύντομα παραδείγματα εντολών](#section-03)
- [📋 Λίστα ελέγχου κοινωνικών δικτύων](#section-04)

<a id="section-01"></a>

## 🔍 Εργαλεία αναζήτησης ονομάτων χρήστη

| Εργαλείο | URL | Πλατφόρμες |
| ------ | ----- | ----------- |
| **Sherlock** | github.com/sherlock-project | 300+ ιστότοποι στο πρωτότυπο |
| **Maigret** | github.com/soxoj/maigret | 2500+ ιστότοποι στο πρωτότυπο |
| **Namechk** | namechk.com | 100+ ιστότοποι στο πρωτότυπο |
| **KnowEm** | knowem.com | 500+ ιστότοποι στο πρωτότυπο |
| **WhatsMyName** | whatsmyname.app | Μέσω browser |

### Sherlock

Τα παραδείγματα του πρωτοτύπου χρησιμοποιούν διαφορετικές μορφές εκκίνησης και επιλογών Sherlock. Δεν είναι εγγυημένα ισοδύναμες για κάθε έκδοση. Επιβεβαιώστε τη σύνταξη από το [επίσημο έργο Sherlock](https://github.com/sherlock-project/sherlock)· οι εντολές εδώ διατηρούνται για αντιστοίχιση με την αγγλική έκδοση.

```bash
python3 sherlock username
python3 sherlock user1 user2 --output results.txt
```

### Maigret
```bash
maigret username
maigret username --tor --json results.json
```

---

<a id="section-02"></a>

## 📊 Έρευνα ανά πλατφόρμα

### Twitter/X

Το [αρχικό έργο Twint](https://github.com/twintproject/twint) είναι αρχειοθετημένο. Τα παραδείγματα διατηρούνται ως περιεχόμενο του πρωτοτύπου και δεν παρουσιάζονται ως επιβεβαιωμένη λειτουργική πρόσβαση στο σημερινό X.

```bash
# Twint — δείτε τη σημείωση συμβατότητας
twint -u username
twint -u username --followers
twint -s "search term" --since 2024-01-01

# Διευθύνσεις προφίλ — ανοίγουν σε browser
twitter.com/username
x.com/username
```

### Instagram
```
# Προφίλ: instagram.com/username
# Εργαλεία: Instaloader, Osintgram

# Instaloader
pip3 install instaloader
instaloader profile username --no-videos
```

### LinkedIn
```
site:linkedin.com/in "John Smith"
site:linkedin.com/in "John Smith" "Company"
```

### Facebook
```
facebook.com/username
facebook.com/search/people/?q=name
# Εργαλείο: fb-friend-list-scraper
```

### TikTok
```
tiktok.com/@username
# Εργαλείο: TikTok-Scraper
```

### Reddit
```
reddit.com/user/username
# Εργαλεία ή υπηρεσίες: BDFR, Pushshift API
```

---

<a id="section-03"></a>

## 🛠️ Σύντομα παραδείγματα εντολών

Το echo στέλνει κείμενο στην τυπική είσοδο του επόμενου προγράμματος· δεν το μετατρέπει αυτομάτως σε παράμετρο username. Η συγκεκριμένη γραμμή χρειάζεται έλεγχο με την έκδοση Sherlock που χρησιμοποιείτε.


```bash
# Social Analyzer
social-analyzer --username "target" --metadata --extract

# Παράδειγμα αναζήτησης με διοχέτευση εισόδου
echo "username" | sherlock --print-all
```

---

<a id="section-04"></a>

## 📋 Λίστα ελέγχου κοινωνικών δικτύων

```markdown
□ Δημόσιο προφίλ Twitter/X
□ Δημόσιο προφίλ Instagram
□ Δημόσιο προφίλ Facebook
□ Δημόσιο προφίλ LinkedIn
□ Λογαριασμός TikTok
□ Λογαριασμός Reddit
□ Προφίλ GitHub
□ Κανάλι YouTube
□ Δημόσια διαθέσιμοι χώροι Discord
□ Δημόσια διαθέσιμες ομάδες Telegram
□ Δημόσια διαθέσιμα προφίλ γνωριμιών
□ Προφίλ παιχνιδιών
```

---

**Επιστροφή:** [🔍 Ελληνική επισκόπηση OSINT](README.el.md)

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
