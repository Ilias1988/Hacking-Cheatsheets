package com.hackingcheatsheets.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hackingcheatsheets.app.data.AppLanguage
import com.hackingcheatsheets.app.ui.components.CheatsheetCardItem
import com.hackingcheatsheets.app.ui.theme.BackgroundDark
import com.hackingcheatsheets.app.ui.theme.CyberGreen
import com.hackingcheatsheets.app.ui.theme.TextMuted
import com.hackingcheatsheets.app.ui.theme.TextSecondary
import com.hackingcheatsheets.app.viewmodel.CheatsheetViewModel

@Composable
fun FavoritesScreen(
    viewModel: CheatsheetViewModel,
    onNavigateToDetail: (String) -> Unit
) {
    val language by viewModel.currentLanguage.collectAsState()
    val favorites by viewModel.favorites.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (language == AppLanguage.TR) "FAVORİLER" else "BOOKMARKS",
            style = MaterialTheme.typography.headlineLarge,
            color = CyberGreen
        )
        Text(
            text = if (language == AppLanguage.TR) 
                "Hızlı saha erişimi için kaydettiğin araçlar (${favorites.size})" 
            else 
                "Your saved cheatsheets for quick field access (${favorites.size})",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 90.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (language == AppLanguage.TR) 
                        "Henüz yer imi eklenmedi.\nKılavuz sayfasındaki yer imi simgesine basarak ekleyebilirsin." 
                    else 
                        "No bookmarks yet.\nTap the bookmark icon on any guide to save it.",
                    color = TextMuted,
                    fontSize = 13.sp,
                    lineHeight = 20.sp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 90.dp)
            ) {
                items(favorites, key = { it.id }) { tool ->
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
}
