package com.hackingcheatsheets.app.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.DataObject
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lan
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class CategoryMeta(
    val categoryName: String,
    val icon: ImageVector,
    val color: Color,
    val titleTr: String,
    val descTr: String,
    val descEn: String
)

fun getCategoryMeta(categoryName: String): CategoryMeta {
    return when (categoryName.trim()) {
        "Active Directory" -> CategoryMeta(
            categoryName = "Active Directory",
            icon = Icons.Default.Lan,
            color = AccentPurple,
            titleTr = "Active Directory",
            descTr = "Kerberos, BloodHound, NetExec, Impacket ve etki alanı saldırıları",
            descEn = "Kerberos, BloodHound, NetExec, Impacket & domain attacks"
        )
        "Web Security & API" -> CategoryMeta(
            categoryName = "Web Security & API",
            icon = Icons.Default.Public,
            color = CyberGreen,
            titleTr = "Web Güvenliği & API",
            descTr = "Burp, SQLMap, SSRF, JWT, IDOR, XSS, WAF & OWASP Top 10",
            descEn = "Burp, SQLMap, SSRF, JWT, IDOR, XSS, WAF & OWASP Top 10"
        )
        "Recon & Scanning" -> CategoryMeta(
            categoryName = "Recon & Scanning",
            icon = Icons.Default.Radar,
            color = TerminalCyan,
            titleTr = "Keşif & Tarama",
            descTr = "Nmap, ffuf, Feroxbuster, RustScan, Nuclei ve hedef tespiti",
            descEn = "Nmap, ffuf, Feroxbuster, RustScan, Nuclei & asset discovery"
        )
        "Privilege Escalation" -> CategoryMeta(
            categoryName = "Privilege Escalation",
            icon = Icons.AutoMirrored.Filled.TrendingUp,
            color = WarningOrange,
            titleTr = "Yetki Yükseltme",
            descTr = "Linux & Windows PrivEsc, PEASS-ng, LOLBAS, GTFOBins",
            descEn = "Linux & Windows PrivEsc, PEASS-ng, LOLBAS, GTFOBins"
        )
        "Exploitation & Shells" -> CategoryMeta(
            categoryName = "Exploitation & Shells",
            icon = Icons.Default.Terminal,
            color = ExploitRed,
            titleTr = "Sömürü & Kabuklar",
            descTr = "Metasploit, Searchsploit, Msfvenom, Ters kabuklar & Socat",
            descEn = "Metasploit, Searchsploit, Msfvenom, Reverse shells & Socat"
        )
        "Reverse Engineering" -> CategoryMeta(
            categoryName = "Reverse Engineering",
            icon = Icons.Default.BugReport,
            color = AccentPink,
            titleTr = "Tersine Mühendislik",
            descTr = "Ghidra, GDB, Radare2, Pwntools, Ropper & ikili analiz",
            descEn = "Ghidra, GDB, Radare2, Pwntools, Ropper & binary exploitation"
        )
        "Password Cracking" -> CategoryMeta(
            categoryName = "Password Cracking",
            icon = Icons.Default.Key,
            color = AccentYellow,
            titleTr = "Parola Kırma",
            descTr = "Hashcat, John the Ripper, Hydra, CeWL, Crunch & NTH",
            descEn = "Hashcat, John the Ripper, Hydra, CeWL, Crunch & NTH"
        )
        "Pivoting & Tunneling" -> CategoryMeta(
            categoryName = "Pivoting & Tunneling",
            icon = Icons.Default.SwapHoriz,
            color = AccentBlue,
            titleTr = "Pivoting & Tünelleme",
            descTr = "Ligolo-ng, Chisel, Proxychains, SSH Tunnels & SOCKS5",
            descEn = "Ligolo-ng, Chisel, Proxychains, SSH Tunnels & SOCKS5"
        )
        "OSINT & Dorking" -> CategoryMeta(
            categoryName = "OSINT & Dorking",
            icon = Icons.Default.Visibility,
            color = AccentIndigo,
            titleTr = "Açık Kaynak İstihbaratı",
            descTr = "Shodan, Google Dorking, Sherlock, TruffleHog, Harvester",
            descEn = "Shodan, Google Dorking, Sherlock, TruffleHog, Harvester"
        )
        "Payloads & Wordlists" -> CategoryMeta(
            categoryName = "Payloads & Wordlists",
            icon = Icons.Default.DataObject,
            color = AccentYellow,
            titleTr = "Payloadlar & Kelime Listeleri",
            descTr = "Enjeksiyon yükleri, Seclists referansları & Kali araçları",
            descEn = "Injection payloads, Seclists references & Kali tools"
        )
        "Wireless & Network" -> CategoryMeta(
            categoryName = "Wireless & Network",
            icon = Icons.Default.Wifi,
            color = AccentEmerald,
            titleTr = "Kablosuz Ağ Güvenliği",
            descTr = "Aircrack-ng, Bettercap, Wifite, hcxdumptool, Reaver",
            descEn = "Aircrack-ng, Bettercap, Wifite, hcxdumptool, Reaver"
        )
        "Network Analysis" -> CategoryMeta(
            categoryName = "Network Analysis",
            icon = Icons.Default.Hub,
            color = AccentBlue,
            titleTr = "Ağ Analizi & Sniffing",
            descTr = "Wireshark, tshark, tcpdump, Scapy, Ettercap paket analizi",
            descEn = "Wireshark, tshark, tcpdump, Scapy, Ettercap packet capture"
        )
        "Cloud Security" -> CategoryMeta(
            categoryName = "Cloud Security",
            icon = Icons.Default.Cloud,
            color = AccentTeal,
            titleTr = "Bulut Güvenliği",
            descTr = "AWS, Azure, GCP denetimi, Pacu ve Prowler araçları",
            descEn = "AWS, Azure, GCP audit, Pacu and Prowler tools"
        )
        "Container Security" -> CategoryMeta(
            categoryName = "Container Security",
            icon = Icons.Default.Inventory2,
            color = AccentTeal,
            titleTr = "Konteyner Güvenliği",
            descTr = "Docker kaçışları, Kubernetes sızma testleri, Trivy",
            descEn = "Docker breakouts, Kubernetes pentesting, Trivy"
        )
        "Mobile Security" -> CategoryMeta(
            categoryName = "Mobile Security",
            icon = Icons.Default.PhoneAndroid,
            color = AccentLime,
            titleTr = "Mobil Güvenlik",
            descTr = "Android & iOS pentest, Frida, Jadx, APKTool enjeksiyonu",
            descEn = "Android & iOS pentesting, Frida, Jadx, APKTool hooks"
        )
        "Digital Forensics" -> CategoryMeta(
            categoryName = "Digital Forensics",
            icon = Icons.Default.Fingerprint,
            color = AccentRose,
            titleTr = "Adli Bilişim (Forensics)",
            descTr = "Autopsy, Volatility RAM analizi, Binwalk, ExifTool",
            descEn = "Autopsy, Volatility RAM analysis, Binwalk, ExifTool"
        )
        "Blue Team & Defense" -> CategoryMeta(
            categoryName = "Blue Team & Defense",
            icon = Icons.Default.Security,
            color = TerminalCyan,
            titleTr = "Mavi Takım & Savunma",
            descTr = "Sigma kuralları, YARA, Sysinternals, log ve tehdit avı",
            descEn = "Sigma rules, YARA, Sysinternals, logs & threat hunting"
        )
        "Attack Methodology" -> CategoryMeta(
            categoryName = "Attack Methodology",
            icon = Icons.Default.TrackChanges,
            color = ExploitRed,
            titleTr = "Saldırı Metodolojisi",
            descTr = "MITRE ATT&CK Kill Chain: 01 Erişimden 07 Hedefe kadar",
            descEn = "MITRE ATT&CK Kill Chain: 01 Initial Access to 07 Objectives"
        )
        "CTF Cheatsheets" -> CategoryMeta(
            categoryName = "CTF Cheatsheets",
            icon = Icons.Default.EmojiEvents,
            color = AccentYellow,
            titleTr = "CTF İpuçları & Rehberler",
            descTr = "Kriptografi, steganografi, web ve binary pwn ipuçları",
            descEn = "Cryptography, steganography, web & binary pwn tricks"
        )
        "IoT & Hardware" -> CategoryMeta(
            categoryName = "IoT & Hardware",
            icon = Icons.Default.Memory,
            color = AccentSlate,
            titleTr = "IoT & Donanım Güvenliği",
            descTr = "Gömülü sistemler, UART, JTAG, firmware analizi",
            descEn = "Embedded systems, UART, JTAG, firmware extraction"
        )
        "Social Engineering" -> CategoryMeta(
            categoryName = "Social Engineering",
            icon = Icons.Default.Psychology,
            color = AccentPink,
            titleTr = "Sosyal Mühendislik",
            descTr = "Oltalama (Phishing), OSINT insan faktörü ve senaryolar",
            descEn = "Phishing, pretexting, human OSINT & assessments"
        )
        "Reporting & Guides" -> CategoryMeta(
            categoryName = "Reporting & Guides",
            icon = Icons.Default.Description,
            color = AccentSlate,
            titleTr = "Raporlama & Şablonlar",
            descTr = "Pentest rapor taslakları, yönetici özeti, Bug Bounty",
            descEn = "Pentest report templates, executive summary, Bug Bounty"
        )
        else -> CategoryMeta(
            categoryName = categoryName,
            icon = Icons.Default.Folder,
            color = TerminalCyan,
            titleTr = categoryName,
            descTr = "Güvenlik araçları ve kılavuzlar",
            descEn = "Security tools and cheatsheets"
        )
    }
}
