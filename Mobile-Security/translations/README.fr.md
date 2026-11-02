# 📱 Fiches pratiques de sécurité mobile

<!-- language-home-navigation -->
[🏠 Accueil en français](../../README.fr.md) • [🌐 Languages](../../README.md#-available-languages)

[🇹🇷 Türkçe Sürüm](README.tr.md) • [🇬🇷 Ελληνικά](README.el.md)

```
███╗   ███╗ ██████╗ ██████╗ ██╗██╗     ███████╗    ███████╗███████╗ ██████╗██╗   ██╗██████╗ ██╗████████╗██╗   ██╗
████╗ ████║██╔═══██╗██╔══██╗██║██║     ██╔════╝    ██╔════╝██╔════╝██╔════╝██║   ██║██╔══██╗██║╚══██╔══╝╚██╗ ██╔╝
██╔████╔██║██║   ██║██████╔╝██║██║     █████╗      ███████╗█████╗  ██║     ██║   ██║██████╔╝██║   ██║    ╚████╔╝ 
██║╚██╔╝██║██║   ██║██╔══██╗██║██║     ██╔══╝      ╚════██║██╔══╝  ██║     ██║   ██║██╔══██╗██║   ██║     ╚██╔╝  
██║ ╚═╝ ██║╚██████╔╝██████╔╝██║███████╗███████╗    ███████║███████╗╚██████╗╚██████╔╝██║  ██║██║   ██║      ██║   
╚═╝     ╚═╝ ╚═════╝ ╚═════╝ ╚═╝╚══════╝╚══════╝    ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝   ╚═╝      ╚═╝   
```

---

## 🎯 Tests de sécurité des applications mobiles

Les tests de sécurité mobile consistent à rechercher des vulnérabilités dans les éléments suivants :
- 📦 **Paquet de l’application** — Analyse des APK et IPA
- 💾 **Stockage des données** — Stockage local insuffisamment protégé
- 🔐 **Authentification** — Mécanismes d’authentification faibles
- 🌐 **Réseau** — Communications non sécurisées
- 🔑 **Cryptographie** — Chiffrement faible
- 🛡️ **Plateforme** — Vulnérabilités propres au système d’exploitation

---

## 📖 Guides de sécurité mobile

| Plateforme | Description | Guide |
|----------|-------------|-------|
| **Android** | Analyse d’APK, Frida, contournement de la détection du root | [📄 Consulter](./Android-Pentesting.fr.md) |
| **iOS** | Analyse d’IPA, jailbreak, Objection | [📄 Consulter](./iOS-Pentesting.fr.md) |

---

## 🛠️ Outils essentiels de sécurité mobile

| Outil | Plateforme | Utilité |
|------|----------|---------|
| **Frida** | Les deux | Instrumentation dynamique |
| **Objection** | Les deux | Exploration de l’application pendant son exécution |
| **MobSF** | Les deux | Analyse de sécurité automatisée |
| **jadx** | Android | Décompilation d’APK |
| **apktool** | Android | Rétro-ingénierie d’APK |
| **Burp Suite** | Les deux | Interception du trafic |
| **Ghidra** | Les deux | Analyse de binaires |
| **drozer** | Android | Évaluation de la sécurité |

### Installation rapide des outils
```bash
# Frida
pip install frida-tools

# Objection
pip install objection

# MobSF (Docker)
docker pull opensecurity/mobile-security-framework-mobsf
docker run -p 8000:8000 opensecurity/mobile-security-framework-mobsf

# jadx
# Télécharger depuis https://github.com/skylot/jadx/releases

# apktool
# Télécharger depuis https://ibotpeaches.github.io/Apktool/install/

# drozer
pip install drozer
```

---

## 📊 Top 10 OWASP Mobile (2024)

| # | Vulnérabilité | Description |
|---|---------------|-------------|
| M1 | Mauvaise utilisation des identifiants | Identifiants codés en dur, stockage insuffisamment protégé |
| M2 | Sécurité insuffisante de la chaîne d’approvisionnement | SDK malveillants, bibliothèques compromises |
| M3 | Authentification et autorisation non sécurisées | Authentification faible, contrôle d’accès défaillant |
| M4 | Validation insuffisante des entrées et sorties | Injections, XSS dans les WebViews |
| M5 | Communications non sécurisées | Absence de TLS, problèmes de certificat |
| M6 | Protection insuffisante de la vie privée | Divulgation de données personnelles, autorisations excessives |
| M7 | Protection insuffisante du binaire | Absence d’obfuscation, modification du binaire |
| M8 | Mauvaise configuration de sécurité | Débogage activé, paramètres par défaut non sécurisés |
| M9 | Stockage non sécurisé des données | Stockage en clair, journaux |
| M10 | Cryptographie insuffisante | Algorithmes faibles, clés codées en dur |

---

## 🔗 Référence rapide : commandes Frida

```bash
# Lister les processus en cours
frida-ps -U

# Lancer le processus (option -f)
frida -U -f com.app.package

# Lancer l’application avec un script
frida -U -f com.app.package -l script.js --no-pause

# Explorer avec Objection
objection -g com.app.package explore
```

---

> **Note de version Frida :** les commandes sources conservent `--no-pause`, option historique. Le [code actuel de frida-tools](https://github.com/frida/frida-tools/blob/main/frida_tools/repl.py) reprend l’exécution par défaut et propose `--pause`. De plus, `-f` lance un processus ; il ne s’attache pas à un processus déjà lancé. Vérifiez la syntaxe de votre version.

## 📚 Ressources

- [Sécurité mobile OWASP](https://owasp.org/www-project-mobile-security/)
- [OWASP MASTG](https://mas.owasp.org/MASTG/)
- [Documentation Frida](https://frida.re/docs/)
- [HackTricks Mobile](https://book.hacktricks.xyz/mobile-pentesting)

---

<p align="center">
  <b>📱 Testez les applications mobiles de manière responsable !</b>
</p>
