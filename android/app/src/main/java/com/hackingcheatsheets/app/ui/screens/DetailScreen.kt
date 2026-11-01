package com.hackingcheatsheets.app.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hackingcheatsheets.app.data.AppLanguage
import com.hackingcheatsheets.app.ui.components.MarkdownBlock
import com.hackingcheatsheets.app.ui.components.MarkdownBlockView
import com.hackingcheatsheets.app.ui.components.normalizeAnchor
import com.hackingcheatsheets.app.ui.components.parseMarkdown
import com.hackingcheatsheets.app.ui.theme.BackgroundDark
import com.hackingcheatsheets.app.ui.theme.BorderDark
import com.hackingcheatsheets.app.ui.theme.CodeBlockBg
import com.hackingcheatsheets.app.ui.theme.CyberGreen
import com.hackingcheatsheets.app.ui.theme.SurfaceDark
import com.hackingcheatsheets.app.ui.theme.SurfaceVariantDark
import com.hackingcheatsheets.app.ui.theme.TerminalCyan
import com.hackingcheatsheets.app.ui.theme.TextMuted
import com.hackingcheatsheets.app.ui.theme.TextPrimary
import com.hackingcheatsheets.app.ui.theme.WarningOrange
import com.hackingcheatsheets.app.ui.theme.getCategoryMeta
import com.hackingcheatsheets.app.viewmodel.CheatsheetViewModel
import kotlinx.coroutines.launch
import org.json.JSONArray

@Composable
fun DetailScreen(
    id: String,
    viewModel: CheatsheetViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToDetail: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    val language by viewModel.currentLanguage.collectAsState()
    val cheatsheet by viewModel.getCheatsheetFlow(id).collectAsState(initial = null)
    val isFav by viewModel.isFavorite(id).collectAsState(initial = false)

    if (cheatsheet == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundDark),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = CyberGreen)
        }
        return
    }

    val tool = cheatsheet!!
    val meta = getCategoryMeta(tool.category)
    val content = if (language == AppLanguage.TR) tool.contentTr else tool.contentEn
    val blocks = remember(content) { parseMarkdown(content) }
    val hasQuickCommands = !tool.quickCommandsJson.isNullOrBlank()

    val onLinkClick: (String) -> Unit = { target ->
        when {
            target.startsWith("#") -> {
                val norm = normalizeAnchor(target)
                val blockIndex = blocks.indexOfFirst { block ->
                    if (block is MarkdownBlock.Header) {
                        val hNorm = normalizeAnchor(block.text)
                        hNorm == norm || hNorm.contains(norm) || norm.contains(hNorm)
                    } else false
                }
                if (blockIndex != -1) {
                    val scrollIndex = if (hasQuickCommands) blockIndex + 1 else blockIndex
                    coroutineScope.launch {
                        listState.animateScrollToItem(scrollIndex)
                    }
                }
            }
            target.endsWith(".tr.md") -> {
                viewModel.setLanguage(AppLanguage.TR)
            }
            target.endsWith("README.md") && !target.contains("/") -> {
                viewModel.setLanguage(AppLanguage.EN)
            }
            target.startsWith("http://") || target.startsWith("https://") -> {
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(target))
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(context, "Cannot open: $target", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                val slug = target.split("/")
                    .filter { it.isNotBlank() && it != ".." && it != "." && !it.endsWith(".md") }
                    .lastOrNull()?.lowercase()
                if (!slug.isNullOrBlank()) {
                    onNavigateToDetail(slug)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        // High-End Custom TopAppBar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceDark)
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = TextPrimary
                    )
                }
                Column(modifier = Modifier.padding(end = 6.dp)) {
                    Text(
                        text = if (language == AppLanguage.TR) tool.titleTr else tool.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        color = TextPrimary
                    )
                    Text(
                        text = (if (language == AppLanguage.TR) meta.titleTr else tool.category).uppercase(),
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = meta.color
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                // Language Switcher Toggle
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceVariantDark)
                        .border(1.dp, BorderDark, RoundedCornerShape(10.dp))
                        .clickable { viewModel.toggleLanguage() }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (language == AppLanguage.TR) "🇹🇷 TR" else "🇬🇧 EN",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = CyberGreen
                    )
                }

                // Bookmark Icon
                IconButton(onClick = { viewModel.toggleFavorite(id, isFav) }) {
                    Icon(
                        imageVector = if (isFav) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Favorite",
                        tint = if (isFav) WarningOrange else TextMuted
                    )
                }

                // Share Button
                IconButton(onClick = {
                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, content.take(1500))
                        type = "text/plain"
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Share Cheatsheet"))
                }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = TextMuted
                    )
                }
            }
        }

        // Cheatsheet Body
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 60.dp)
        ) {
            // Quick Commands horizontal list if available
            if (hasQuickCommands) {
                item {
                    val commands = try {
                        val arr = JSONArray(tool.quickCommandsJson)
                        val list = mutableListOf<String>()
                        for (idx in 0 until arr.length()) {
                            list.add(arr.getString(idx))
                        }
                        list
                    } catch (e: Exception) {
                        emptyList<String>()
                    }

                    if (commands.isNotEmpty()) {
                        Text(
                            text = if (language == AppLanguage.TR) "⚡ HIZLI KOMUT KOPYALAMA" else "⚡ QUICK COMMANDS",
                            style = MaterialTheme.typography.labelLarge,
                            color = CyberGreen
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            commands.take(10).forEach { cmd ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(CodeBlockBg)
                                        .border(1.dp, BorderDark, RoundedCornerShape(8.dp))
                                        .clickable {
                                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                            clipboard.setPrimaryClip(ClipData.newPlainText("Command", cmd))
                                            Toast.makeText(context, if (language == AppLanguage.TR) "Komut kopyalandı!" else "Command copied!", Toast.LENGTH_SHORT).show()
                                        }
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = cmd.take(38) + if (cmd.length > 38) "..." else "",
                                            fontSize = 11.5.sp,
                                            fontFamily = FontFamily.Monospace,
                                            color = TextPrimary
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Icon(
                                            imageVector = Icons.Default.ContentCopy,
                                            contentDescription = "Copy",
                                            tint = CyberGreen,
                                            modifier = Modifier.size(12.dp)
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            // Render Markdown Blocks
            itemsIndexed(blocks) { _, block ->
                MarkdownBlockView(block = block, onLinkClick = onLinkClick)
            }
        }
    }
}
