# 📈 Ανίχνευση απειλών με SIEM

<!-- language-home-navigation -->
[🏠 Ελληνική αρχική σελίδα](../../README.el.md) • [🌐 Languages](../../README.md#-available-languages)

[🇬🇧 English](../SIEM-Detection.md) • [🇮🇹 Italiano](SIEM-Detection.it.md) • [🇹🇷 Türkçe](SIEM-Detection.tr.md)

[🛡️ Ελληνική επισκόπηση Blue Team](README.el.md)

```
  ███████╗██╗███████╗███╗   ███╗    ██████╗ ███████╗████████╗███████╗ ██████╗████████╗██╗ ██████╗ ███╗   ██╗
  ██╔════╝██║██╔════╝████╗ ████║    ██╔══██╗██╔════╝╚══██╔══╝██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║
  ███████╗██║█████╗  ██╔████╔██║    ██║  ██║█████╗     ██║   █████╗  ██║        ██║   ██║██║   ██║██╔██╗ ██║
  ╚════██║██║██╔══╝  ██║╚██╔╝██║    ██║  ██║██╔══╝     ██║   ██╔══╝  ██║        ██║   ██║██║   ██║██║╚██╗██║
  ███████║██║███████╗██║ ╚═╝ ██║    ██████╔╝███████╗   ██║   ███████╗╚██████╗   ██║   ██║╚██████╔╝██║ ╚████║
  ╚══════╝╚═╝╚══════╝╚═╝     ╚═╝    ╚═════╝ ╚══════╝   ╚═╝   ╚══════╝ ╚═════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝
```

---

## Πριν ξεκινήσετε

Το **SIEM** συγκεντρώνει καταγραφές από πολλές πηγές και βοηθά στη συσχέτισή τους. Ένα **query** είναι ερώτημα αναζήτησης. Ένα **dashboard** είναι πίνακας παρακολούθησης αποτελεσμάτων. Η **aggregation** ομαδοποιεί δεδομένα, για παράδειγμα μετρώντας αποτυχημένες συνδέσεις ανά IP.

Η **SPL** είναι γλώσσα αναζήτησης Splunk. Εδώ η **KQL** σημαίνει **Kibana Query Language**, όχι τη διαφορετική Kusto Query Language. Η **Elasticsearch DSL** περιγράφει αναζητήσεις σε μορφή JSON. Ονόματα όπως `index=windows`, `src_ip`, `EventCode` και `source.ip` προϋποθέτουν συγκεκριμένη συλλογή και αντιστοίχιση πεδίων· δεν είναι εγγυημένο ότι υπάρχουν έτσι στο δικό σας σύστημα.

Τα όρια, όπως «περισσότερα από 10 συμβάντα», είναι παραδείγματα που απαιτούν προσαρμογή, χρονικό παράθυρο και κατανόηση της κανονικής δραστηριότητας. **False positive** είναι ειδοποίηση για νόμιμη δραστηριότητα και **false negative** είναι επίθεση που δεν εντοπίστηκε. Οι απλές αναζητήσεις δεν αποτελούν πλήρη υλοποίηση κάθε τεχνικής MITRE ATT&CK.

## Περιεχόμενα

- [🔶 Ερωτήματα Splunk στη γλώσσα SPL](#section-01)
- [🟢 Αναζητήσεις Elasticsearch/Kibana — KQL και Lucene](#section-02)
- [📊 Παραδείγματα ανίχνευσης οργανωμένα με αναφορές στο MITRE ATT&CK](#section-03)
- [🔗 Σχετικοί οδηγοί αναφοράς](#section-04)

<a id="section-01"></a>

## 🔶 Ερωτήματα Splunk στη γλώσσα SPL

### Ταυτοποίηση και πρόσβαση

#### Εντοπισμός επαναλαμβανόμενων δοκιμών κωδικών
```spl
# Πολλές αποτυχημένες συνδέσεις από την ίδια IP
index=windows EventCode=4625
| stats count by src_ip, user
| where count > 10
| sort -count

# Αποτυχίες και επιτυχία σύνδεσης — πιθανή παραβίαση στοιχείων προς διερεύνηση
index=windows (EventCode=4625 OR EventCode=4624)
| transaction user maxspan=10m
| where eventcount > 5 AND match(EventCode, "4624")
| table _time, user, src_ip, EventCode
```

#### Εντοπισμός πιθανής δοκιμής κωδικών σε πολλούς λογαριασμούς

Τα συμβάντα αποτυχημένης σύνδεσης δεν αποκαλύπτουν τον κωδικό που δοκιμάστηκε. Το ερώτημα μετρά διαφορετικούς χρήστες ανά IP και επομένως εντοπίζει πιθανό μοτίβο spraying, όχι βεβαιωμένη χρήση του ίδιου κωδικού.

```spl
# Ένδειξη πολλών αποτυχιών σε διαφορετικούς χρήστες· ο κωδικός δεν είναι ορατός στα συμβάντα
index=windows EventCode=4625
| stats dc(user) as unique_users, count by src_ip
| where unique_users > 5 AND count > 10
| sort -unique_users

# Πολλοί χρήστες από μία IP σε μικρό χρονικό διάστημα
index=windows EventCode=4625
| bucket _time span=5m
| stats dc(user) as unique_users, values(user) as users by _time, src_ip
| where unique_users > 5
```

#### Επιτυχημένες συνδέσεις διαχειριστών
```spl
# Συνδέσεις λογαριασμών διαχειριστή
index=windows EventCode=4624 LogonType=10
| search user IN ("Administrator", "admin", "*admin*")
| table _time, user, src_ip, dest

# Συνδέσεις εκτός ενδεικτικού ωραρίου εργασίας
index=windows EventCode=4624
| eval hour=strftime(_time, "%H")
| where hour < 7 OR hour > 19
| stats count by user, src_ip
```

### Διεργασίες και εκτέλεση εντολών

#### Δημιουργία διεργασιών που χρειάζονται διερεύνηση
```spl
# Κωδικοποιημένες εντολές PowerShell
index=windows EventCode=4688 
| search CommandLine="*-enc*" OR CommandLine="*-encoded*" OR CommandLine="*FromBase64*"
| table _time, user, ParentProcessName, NewProcessName, CommandLine

# Ασυνήθιστες σχέσεις γονικής και θυγατρικής διεργασίας
index=windows EventCode=4688
| search ParentProcessName="*excel.exe" OR ParentProcessName="*word.exe" OR ParentProcessName="*outlook.exe"
| search NewProcessName="*cmd.exe" OR NewProcessName="*powershell.exe" OR NewProcessName="*wscript.exe"
| table _time, user, ParentProcessName, NewProcessName, CommandLine

# Χρήση διαθέσιμων εκτελέσιμων Windows που μπορούν να καταχραστούν (LOLBAS)
index=windows EventCode=4688
| search NewProcessName IN ("*certutil.exe", "*mshta.exe", "*regsvr32.exe", "*rundll32.exe", "*bitsadmin.exe")
| table _time, user, NewProcessName, CommandLine
```

#### Ενδείξεις χρήσης Mimikatz
```spl
# Λέξεις σχετικές με Mimikatz σε γραμμή εντολών
index=windows EventCode=4688
| search CommandLine="*sekurlsa*" OR CommandLine="*kerberos::*" OR CommandLine="*lsadump*"
| table _time, user, NewProcessName, CommandLine

# Πρόσβαση στη LSASS — συμβάν Sysmon 10
index=sysmon EventCode=10 TargetImage="*lsass.exe"
| table _time, SourceImage, SourceUser, GrantedAccess
```

### Μηχανισμοί διατήρησης πρόσβασης

#### Δημιουργία προγραμματισμένης εργασίας
```spl
# Νέες προγραμματισμένες εργασίες
index=windows EventCode=4698
| table _time, user, TaskName, TaskContent

# Εξαγωγή της εντολής από το περιεχόμενο προγραμματισμένων εργασιών
index=windows EventCode=4698
| rex field=TaskContent "<Command>(?<Command>[^<]+)</Command>"
| table _time, user, TaskName, Command
```

#### Εγκατάσταση υπηρεσίας
```spl
# Νέες εγκατεστημένες υπηρεσίες
index=windows EventCode=7045
| table _time, ServiceName, ServiceFileName, ServiceType, StartType

# Διαδρομές υπηρεσιών προς διερεύνηση
index=windows EventCode=7045
| search ServiceFileName="*temp*" OR ServiceFileName="*appdata*" OR ServiceFileName="*.ps1*"
| table _time, ServiceName, ServiceFileName
```

#### Αλλαγές μητρώου Windows
```spl
# Μεταβολές κλειδιών Run — συμβάντα Sysmon 12–14
index=sysmon EventCode IN (12, 13, 14)
| search TargetObject="*\\Run\\*" OR TargetObject="*\\RunOnce\\*"
| table _time, EventCode, Image, TargetObject, Details
```

### Πλευρική μετακίνηση

#### Δραστηριότητα απομακρυσμένης επιφάνειας εργασίας (RDP)
```spl
# Συνδέσεις RDP — τύπος σύνδεσης 10
index=windows EventCode=4624 LogonType=10
| stats count by src_ip, dest, user
| sort -count

# Πρόσβαση RDP σε πολλούς υπολογιστές από μία πηγή
index=windows EventCode=4624 LogonType=10
| stats dc(dest) as unique_hosts, values(dest) as hosts by src_ip, user
| where unique_hosts > 3
```

#### Ενδείξεις χρήσης PSExec/WMI
```spl
# Δραστηριότητα που μοιάζει με χρήση PSExec
index=windows EventCode=7045 ServiceFileName="*PSEXESVC*"
| table _time, src_ip, dest, ServiceFileName

# Απομακρυσμένη εκτέλεση μέσω WMI
index=windows EventCode=4688 
| search ParentProcessName="*WmiPrvSE.exe"
| table _time, user, dest, NewProcessName, CommandLine
```

### Εξαγωγή δεδομένων εκτός του συστήματος (Data Exfiltration)

#### Μεταφορές μεγάλου όγκου δεδομένων
```spl
# Μεγάλος όγκος εξερχόμενης κίνησης
index=firewall action=allowed direction=outbound
| stats sum(bytes_out) as total_bytes by src_ip, dest_ip
| where total_bytes > 100000000
| eval MB=round(total_bytes/1024/1024,2)
| table src_ip, dest_ip, MB

# Μεγάλα ερωτήματα DNS — πιθανή ένδειξη εξαγωγής δεδομένων
index=dns
| eval query_length=len(query)
| where query_length > 50
| stats count by src_ip, query
```

---

<a id="section-02"></a>

## 🟢 Αναζητήσεις Elasticsearch/Kibana — KQL και Lucene

### Ταυτοποίηση και πρόσβαση

#### Εντοπισμός επαναλαμβανόμενων δοκιμών κωδικών
```kql
# Αποτυχημένες συνδέσεις Windows
event.code: 4625 AND winlog.event_data.LogonType: 3

# Αποτυχημένες συνδέσεις για συγκεντρωτική ανάλυση
event.code: 4625
# Έπειτα ομαδοποιήστε τα αποτελέσματα ανά source.ip στον πίνακα παρακολούθησης

# Συμβάντα αποτυχίας και επιτυχίας σύνδεσης προς συσχέτιση
event.code: (4625 OR 4624)
```

#### Δοκιμές κωδικών σε πολλούς λογαριασμούς
```kql
# Αποτυχημένες ταυτοποιήσεις από μία IP σε πολλούς χρήστες
event.code: 4625 AND source.ip: *
# Ομαδοποίηση ανά source.ip και πλήθος διαφορετικών user.name
```

### Διεργασίες και εκτέλεση εντολών

#### Διεργασίες που χρειάζονται διερεύνηση
```kql
# Κωδικοποιημένο PowerShell
event.code: 4688 AND process.command_line: (*-enc* OR *-encoded* OR *FromBase64*)

# Εκτελέσιμα Windows που μπορούν να χρησιμοποιηθούν καταχρηστικά (LOLBAS)
event.code: 4688 AND process.name: (certutil.exe OR mshta.exe OR regsvr32.exe OR rundll32.exe)

# Εφαρμογή Office που εκκινεί περιβάλλον εντολών
event.code: 4688 AND process.parent.name: (WINWORD.EXE OR EXCEL.EXE OR OUTLOOK.EXE) AND process.name: (cmd.exe OR powershell.exe)
```

#### Ενδείξεις χρήσης Mimikatz
```kql
# Διεργασία με λέξεις σχετικές με Mimikatz
process.command_line: (*sekurlsa* OR *kerberos::* OR *lsadump* OR *mimikatz*)

# Πρόσβαση στη LSASS
event.code: 10 AND winlog.event_data.TargetImage: *lsass.exe
```

### Διατήρηση πρόσβασης

#### Προγραμματισμένες εργασίες (Scheduled Tasks)
```kql
# Δημιουργία εργασίας
event.code: 4698

# Ονόματα εργασιών προς διερεύνηση
event.code: 4698 AND winlog.event_data.TaskName: (*update* OR *sync* OR *helper*)
```

#### Υπηρεσίες
```kql
# Εγκατάσταση υπηρεσίας
event.code: 7045

# Διαδρομές υπηρεσιών προς διερεύνηση
event.code: 7045 AND winlog.event_data.ImagePath: (*temp* OR *appdata* OR *.ps1)
```

### Πλευρική μετακίνηση

#### RDP
```kql
# Συνδέσεις RDP
event.code: 4624 AND winlog.event_data.LogonType: 10

# Σύνδεση RDP από πηγή εκτός του ενδεικτικού επιτρεπόμενου εύρους
event.code: 4624 AND winlog.event_data.LogonType: 10 AND NOT source.ip: 192.168.*
```

#### PsExec
```kql
# Υπηρεσία PsExec
event.code: 7045 AND winlog.event_data.ServiceFileName: *PSEXESVC*
```

### Χρήσιμες συγκεντρωτικές αναζητήσεις με Elasticsearch DSL

Η ομαδοποίηση απαιτεί πεδία με κατάλληλη αντιστοίχιση τύπων δεδομένων. Το παράδειγμα πρέπει να περιορίζεται στο χρονικό εύρος της έρευνας.

```json
{
  "aggs": {
    "failed_logins_by_ip": {
      "terms": {
        "field": "source.ip",
        "size": 10
      },
      "aggs": {
        "unique_users": {
          "cardinality": {
            "field": "user.name"
          }
        }
      }
    }
  },
  "query": {
    "bool": {
      "must": [
        { "match": { "event.code": 4625 }}
      ]
    }
  }
}
```

---

<a id="section-03"></a>

## 📊 Παραδείγματα ανίχνευσης οργανωμένα με αναφορές στο MITRE ATT&CK

### Αρχική πρόσβαση (Initial Access / TA0001)
| Τεχνική | Ερώτημα Splunk |
| ----------- | -------------- |
| Ηλεκτρονικό ψάρεμα (Phishing) | `index=email \| search subject="*urgent*" OR attachment="*.exe"` |
| Έγκυροι λογαριασμοί | `index=windows EventCode=4624 LogonType=10 user!="SYSTEM"` |

### Εκτέλεση (Execution / TA0002)
| Τεχνική | Ερώτημα Splunk |
| ----------- | -------------- |
| PowerShell | `index=windows EventCode=4688 NewProcessName="*powershell*"` |
| Εκτέλεση σεναρίων | `index=windows EventCode=4688 NewProcessName IN ("*wscript*", "*cscript*")` |

### Διατήρηση πρόσβασης (Persistence / TA0003)
| Τεχνική | Ερώτημα Splunk |
| ----------- | -------------- |
| Προγραμματισμένη εργασία | `index=windows EventCode=4698` |
| Κλειδί Run στο μητρώο | `index=sysmon EventCode=13 TargetObject="*\\Run\\*"` |
| Υπηρεσία | `index=windows EventCode=7045` |

### Κλιμάκωση προνομίων (Privilege Escalation / TA0004)
| Τεχνική | Ερώτημα Splunk |
| ----------- | -------------- |
| Μεταβολή διακριτικών πρόσβασης (Tokens) | `index=windows EventCode=4672 NOT user="SYSTEM"` |
| Παράκαμψη ελέγχου λογαριασμού χρήστη (UAC Bypass) | `index=sysmon EventCode=1 Image="*fodhelper*"` |

### Αποφυγή μηχανισμών άμυνας — ταξινόμηση του πρωτοτύπου (TA0005)
| Τεχνική | Ερώτημα Splunk |
| ----------- | -------------- |
| Διαγραφή καταγραφών | `index=windows EventCode=1102` |
| Παραπλανητική εμφάνιση αρχείου ή διεργασίας | `index=sysmon EventCode=1 Image="*temp*\\svchost.exe"` |

### Πρόσβαση σε στοιχεία ταυτοποίησης (Credential Access / TA0006)
| Τεχνική | Ερώτημα Splunk |
| ----------- | -------------- |
| Εξαγωγή μνήμης LSASS | `index=sysmon EventCode=10 TargetImage="*lsass.exe"` |
| Εξαγωγή στοιχείων σύνδεσης | `index=windows EventCode=4688 CommandLine="*sekurlsa*"` |

### Πλευρική μετακίνηση (Lateral Movement / TA0008)
| Τεχνική | Ερώτημα Splunk |
| ----------- | -------------- |
| RDP | `index=windows EventCode=4624 LogonType=10` |
| PSExec | `index=windows EventCode=7045 ServiceFileName="*PSEXESVC*"` |
| WMI | `index=windows EventCode=4688 ParentProcessName="*WmiPrvSE*"` |

### Εξαγωγή δεδομένων (Exfiltration / TA0010)
| Τεχνική | Ερώτημα Splunk |
| ----------- | -------------- |
| Διοχέτευση δεδομένων μέσω DNS | `index=dns \| eval len=len(query) \| where len>50` |
| Επικοινωνία ιστού | `index=proxy bytes_out>10000000` |

---

<a id="section-04"></a>

## 🔗 Σχετικοί οδηγοί αναφοράς

- [Ανάλυση καταγραφών](Log-Analysis.el.md)
- [Κανόνες Sigma](Sigma-Rules.el.md)
- [Προληπτική αναζήτηση απειλών](Threat-Hunting.el.md)

---

**Προηγούμενο θέμα:** [← Ανάλυση καταγραφών](Log-Analysis.el.md)

**Επόμενο θέμα:** [Προληπτική αναζήτηση απειλών →](Threat-Hunting.el.md)

**Επιστροφή στην επισκόπηση:** [🛡️ Blue Team](README.el.md)

**Επιστροφή:** [Ελληνική αρχική σελίδα](../../README.el.md)
