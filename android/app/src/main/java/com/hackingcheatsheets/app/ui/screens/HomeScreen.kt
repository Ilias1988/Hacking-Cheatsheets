package com.hackingcheatsheets.app.ui.screens

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hackingcheatsheets.app.data.AppLanguage
import com.hackingcheatsheets.app.ui.components.CheatsheetCardItem
import com.hackingcheatsheets.app.ui.theme.AccentBlue
import com.hackingcheatsheets.app.ui.theme.AccentPurple
import com.hackingcheatsheets.app.ui.theme.BackgroundDark
import com.hackingcheatsheets.app.ui.theme.BorderDark
import com.hackingcheatsheets.app.ui.theme.BorderLight
import com.hackingcheatsheets.app.ui.theme.CyberGreen
import com.hackingcheatsheets.app.ui.theme.SurfaceDark
import com.hackingcheatsheets.app.ui.theme.SurfaceVariantDark
import com.hackingcheatsheets.app.ui.theme.TerminalCyan
import com.hackingcheatsheets.app.ui.theme.TextMuted
import com.hackingcheatsheets.app.ui.theme.TextPrimary
import com.hackingcheatsheets.app.ui.theme.TextSecondary
import com.hackingcheatsheets.app.ui.theme.getCategoryMeta
import com.hackingcheatsheets.app.viewmodel.CheatsheetViewModel

@Composable
fun HomeScreen(
    viewModel: CheatsheetViewModel,
    onNavigateToDetail: (String) -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToCategories: (String?) -> Unit
) {
    val language by viewModel.currentLanguage.collectAsState()
    val allCheatsheets by viewModel.allCheatsheets.collectAsState()
    val categories by viewModel.categories.collectAsState()

    val featuredIds = listOf(
        "ffuf", "ligolo-ng", "nmap", "certipy", "netexec",
        "peass-ng", "chisel", "metasploit", "sqlmap",
        "burp-suite", "feroxbuster", "jwt-attacks", "hydra",
        "bloodhound", "ghidra", "pypykatz", "searchsploit"
    )
    val featuredTools = allCheatsheets.filter { it.id in featuredIds }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp)
    ) {
        // Top Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(CyberGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Terminal,
                            contentDescription = "Terminal Icon",
                            tint = BackgroundDark,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "HACKING CHEATSHEETS",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = TextPrimary
                        )
                        Text(
                            text = if (language == AppLanguage.TR) 
                                "${allCheatsheets.size} ARAÇ • %100 ÇEVRİMDIŞI" 
                            else 
                                "${allCheatsheets.size} TOOLS • 100% OFFLINE",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.SemiBold,
                            color = CyberGreen
                        )
                    }
                }

                // Language Toggle Pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceDark)
                        .border(1.dp, BorderDark, RoundedCornerShape(16.dp))
                        .clickable { viewModel.toggleLanguage() }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = "Language",
                            tint = TerminalCyan,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (language == AppLanguage.TR) "🇹🇷 TR" else "🇬🇧 EN",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Search Bar Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceDark)
                    .border(1.dp, BorderDark, RoundedCornerShape(12.dp))
                    .clickable { onNavigateToSearch() }
                    .padding(horizontal = 14.dp, vertical = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = CyberGreen,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = if (language == AppLanguage.TR) 
                            "Araç, komut, parametre veya CVE ara..." 
                        else 
                            "Search tools, commands, flags, CVEs...",
                        color = TextMuted,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Hero Metric Dashboard Cards (3 Metrics)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Metric 1: Total Tools
                StatCard(
                    modifier = Modifier.weight(1f),
                    value = "${allCheatsheets.size}",
                    label = if (language == AppLanguage.TR) "Siber Araç" else "Cyber Tools",
                    accentColor = CyberGreen
                )

                // Metric 2: Categories
                StatCard(
                    modifier = Modifier.weight(1f),
                    value = "${categories.size}",
                    label = if (language == AppLanguage.TR) "Kategori" else "Domains",
                    accentColor = TerminalCyan
                )

                // Metric 3: Offline FTS
                StatCard(
                    modifier = Modifier.weight(1f),
                    value = "100%",
                    label = if (language == AppLanguage.TR) "Çevrimdışı" else "Offline",
                    accentColor = AccentPurple
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Categories Quick Jump Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (language == AppLanguage.TR) "⚡ KATEGORİ REHBERİ" else "⚡ BROWSE CATEGORIES",
                    style = MaterialTheme.typography.labelLarge,
                    color = CyberGreen
                )
                Text(
                    text = if (language == AppLanguage.TR) "Tümünü Gör →" else "View All →",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold,
                    color = TerminalCyan,
                    modifier = Modifier.clickable { onNavigateToCategories(null) }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Horizontal Category Carousel
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val priorityCategories = listOf(
                    "Active Directory",
                    "Web Security & API",
                    "Recon & Scanning",
                    "Privilege Escalation",
                    "Exploitation & Shells",
                    "Reverse Engineering",
                    "Password Cracking",
                    "Pivoting & Tunneling",
                    "OSINT & Dorking",
                    "Wireless & Network"
                )

                priorityCategories.forEach { catName ->
                    val meta = getCategoryMeta(catName)
                    val count = categories.firstOrNull { it.category == catName }?.count ?: 0

                    Box(
                        modifier = Modifier
                            .width(135.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceDark)
                            .border(1.dp, BorderDark, RoundedCornerShape(12.dp))
                            .clickable { onNavigateToCategories(catName) }
                            .padding(12.dp)
                    ) {
                        Column {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(meta.color.copy(alpha = 0.15f))
                                    .border(1.dp, meta.color.copy(alpha = 0.35f), RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = meta.icon,
                                    contentDescription = catName,
                                    tint = meta.color,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = if (language == AppLanguage.TR) meta.titleTr else catName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                maxLines = 1
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "$count ${if (language == AppLanguage.TR) "araç" else "tools"}",
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace,
                                color = meta.color
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // MITRE ATT&CK Phase Horizontal Selector
            Text(
                text = if (language == AppLanguage.TR) "🎯 SALDIRI AŞAMALARI (KILL CHAIN)" else "🎯 ATTACK KILL CHAIN",
                style = MaterialTheme.typography.labelLarge,
                color = TerminalCyan
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val phases = if (language == AppLanguage.TR) listOf(
                    "01 Başlangıç Erişimi" to "attack-methodology-01-initial-access",
                    "02 Numaralandırma" to "attack-methodology-02-enumeration",
                    "03 Yetki Yükseltme" to "attack-methodology-03-privilege-escalation",
                    "04 Yanal Hareket" to "attack-methodology-04-lateral-movement",
                    "05 Kalıcılık" to "attack-methodology-05-persistence",
                    "06 Savunma Atlama" to "attack-methodology-06-defense-evasion",
                    "07 Amaç Eylemleri" to "attack-methodology-07-actions-objectives"
                ) else listOf(
                    "01 Initial Access" to "attack-methodology-01-initial-access",
                    "02 Enumeration" to "attack-methodology-02-enumeration",
                    "03 PrivEsc" to "attack-methodology-03-privilege-escalation",
                    "04 Lateral Movement" to "attack-methodology-04-lateral-movement",
                    "05 Persistence" to "attack-methodology-05-persistence",
                    "06 Defense Evasion" to "attack-methodology-06-defense-evasion",
                    "07 Actions Objectives" to "attack-methodology-07-actions-objectives"
                )

                phases.forEach { (label, id) ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceDark)
                            .border(1.dp, BorderDark, RoundedCornerShape(8.dp))
                            .clickable { onNavigateToDetail(id) }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = label,
                            color = TextPrimary,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Featured High-Impact Tools Header
            Text(
                text = if (language == AppLanguage.TR) "🔥 ÖNE ÇIKAN SİBER ARAÇLAR" else "🔥 FEATURED SECURITY TOOLS",
                style = MaterialTheme.typography.labelLarge,
                color = CyberGreen
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Featured Tools List
        items(featuredTools, key = { it.id }) { tool ->
            CheatsheetCardItem(
                tool = tool,
                language = language,
                showQuickCopy = true,
                onClick = { onNavigateToDetail(tool.id) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    accentColor: Color
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(12.dp),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = SolidColor(BorderDark)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 18.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.ExtraBold,
                color = accentColor
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Medium,
                color = TextSecondary,
                maxLines = 1
            )
        }
    }
}
