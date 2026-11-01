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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hackingcheatsheets.app.data.AppLanguage
import com.hackingcheatsheets.app.ui.components.CheatsheetCardItem
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
fun SearchScreen(
    viewModel: CheatsheetViewModel,
    onNavigateToDetail: (String) -> Unit
) {
    val language by viewModel.currentLanguage.collectAsState()
    val query by viewModel.searchQuery.collectAsState()
    val rawResults by viewModel.searchResults.collectAsState()
    val categories by viewModel.categories.collectAsState()

    var activeCategoryFilter by remember { mutableStateOf<String?>(null) }

    // Filter results by active category if selected
    val results = if (activeCategoryFilter == null) {
        rawResults
    } else {
        rawResults.filter { it.category == activeCategoryFilter }
    }

    val popularSuggestions = listOf(
        "nmap", "kerberos", "reverse shell", "sqlmap", "burp",
        "mimikatz", "jwt", "smb", "linpeas", "chisel",
        "ligolo", "bloodhound", "hydra", "socat", "privesc"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Search Input Field
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceDark)
                .border(1.dp, if (query.isNotEmpty()) CyberGreen else BorderDark, RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = if (query.isNotEmpty()) CyberGreen else TextMuted,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                BasicTextField(
                    value = query,
                    onValueChange = { viewModel.setSearchQuery(it) },
                    modifier = Modifier.weight(1f),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = TextPrimary),
                    cursorBrush = SolidColor(CyberGreen),
                    singleLine = true,
                    decorationBox = { innerTextField ->
                        if (query.isEmpty()) {
                            Text(
                                text = if (language == AppLanguage.TR) 
                                    "Araç, komut, parametre veya CVE ara..." 
                                else 
                                    "Search tools, commands, flags, CVEs...",
                                color = TextMuted,
                                fontSize = 14.sp
                            )
                        }
                        innerTextField()
                    }
                )
                if (query.isNotEmpty()) {
                    IconButton(
                        onClick = { viewModel.setSearchQuery("") },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = TextMuted,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Horizontal Category Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // "All" Chip
            val isAllSelected = activeCategoryFilter == null
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isAllSelected) CyberGreen.copy(alpha = 0.2f) else SurfaceDark)
                    .border(
                        1.dp, 
                        if (isAllSelected) CyberGreen else BorderDark, 
                        RoundedCornerShape(8.dp)
                    )
                    .clickable { activeCategoryFilter = null }
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = if (language == AppLanguage.TR) "Tümü" else "All",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = if (isAllSelected) CyberGreen else TextSecondary
                )
            }

            // Category Chips
            categories.forEach { cat ->
                val meta = getCategoryMeta(cat.category)
                val isSelected = activeCategoryFilter == cat.category
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) meta.color.copy(alpha = 0.2f) else SurfaceDark)
                        .border(
                            1.dp, 
                            if (isSelected) meta.color else BorderDark, 
                            RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            activeCategoryFilter = if (isSelected) null else cat.category
                        }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = meta.icon,
                            contentDescription = cat.category,
                            tint = if (isSelected) meta.color else TextMuted,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = if (language == AppLanguage.TR) meta.titleTr else cat.category,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) meta.color else TextSecondary
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Popular Discovery Suggestions (when query is empty)
        if (query.isEmpty() && activeCategoryFilter == null) {
            Text(
                text = if (language == AppLanguage.TR) "⚡ HIZLI ARAMA ÖNERİLERİ" else "⚡ POPULAR SEARCHES",
                style = MaterialTheme.typography.bodySmall,
                color = TerminalCyan
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                popularSuggestions.forEach { tag ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(SurfaceVariantDark)
                            .border(1.dp, BorderDark, RoundedCornerShape(6.dp))
                            .clickable { viewModel.setSearchQuery(tag) }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "#$tag",
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            color = TextPrimary
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        // Result Count Banner
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (query.isEmpty() && activeCategoryFilter == null) {
                    if (language == AppLanguage.TR) "Tüm Kılavuzlar (${results.size})" else "All Cheatsheets (${results.size})"
                } else {
                    "${results.size} ${if (language == AppLanguage.TR) "sonuç bulundu" else "results found"}"
                },
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                color = if (results.isNotEmpty()) CyberGreen else TextMuted
            )

            if (activeCategoryFilter != null) {
                Text(
                    text = "• ${activeCategoryFilter}",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    color = TerminalCyan
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Search Results List with 1-tap quick command copy
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            items(results, key = { it.id }) { tool ->
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
}
