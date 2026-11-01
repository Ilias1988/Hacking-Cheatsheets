package com.hackingcheatsheets.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.hackingcheatsheets.app.data.CategorySummary
import com.hackingcheatsheets.app.data.Cheatsheet
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
fun CategoryScreen(
    viewModel: CheatsheetViewModel,
    initialCategory: String? = null,
    onNavigateToDetail: (String) -> Unit
) {
    val language by viewModel.currentLanguage.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val allCheatsheets by viewModel.allCheatsheets.collectAsState()
    val selectedCategoryState by viewModel.selectedCategory.collectAsState()

    var selectedCategory by remember { mutableStateOf(initialCategory ?: selectedCategoryState) }
    var categoryFilterQuery by remember { mutableStateOf("") }

    LaunchedEffect(selectedCategoryState) {
        if (selectedCategoryState != null) {
            selectedCategory = selectedCategoryState
        }
    }

    if (selectedCategory == null) {
        // TOP-LEVEL CATEGORIES LIST / GRID VIEW
        CategoryOverviewView(
            language = language,
            categories = categories,
            totalToolsCount = allCheatsheets.size,
            filterQuery = categoryFilterQuery,
            onFilterQueryChange = { categoryFilterQuery = it },
            onSelectCategory = { catName ->
                selectedCategory = catName
                viewModel.setCategory(catName)
                categoryFilterQuery = ""
            }
        )
    } else {
        // IN-CATEGORY DRILL DOWN VIEW
        val categoryTools = allCheatsheets.filter { it.category == selectedCategory }
        val filteredTools = if (categoryFilterQuery.isBlank()) {
            categoryTools
        } else {
            val q = categoryFilterQuery.lowercase().trim()
            categoryTools.filter { tool ->
                tool.title.lowercase().contains(q) ||
                tool.titleTr.lowercase().contains(q) ||
                tool.id.lowercase().contains(q) ||
                (tool.tags != null && tool.tags.lowercase().contains(q)) ||
                (tool.description != null && tool.description.lowercase().contains(q)) ||
                (tool.descriptionTr != null && tool.descriptionTr.lowercase().contains(q))
            }
        }

        CategoryDetailView(
            categoryName = selectedCategory!!,
            language = language,
            tools = filteredTools,
            totalInCategory = categoryTools.size,
            filterQuery = categoryFilterQuery,
            onFilterQueryChange = { categoryFilterQuery = it },
            onBackClick = {
                selectedCategory = null
                viewModel.setCategory(null)
                categoryFilterQuery = ""
            },
            onNavigateToDetail = onNavigateToDetail
        )
    }
}

@Composable
fun CategoryOverviewView(
    language: AppLanguage,
    categories: List<CategorySummary>,
    totalToolsCount: Int,
    filterQuery: String,
    onFilterQueryChange: (String) -> Unit,
    onSelectCategory: (String) -> Unit
) {
    val filteredCategories = if (filterQuery.isBlank()) {
        categories
    } else {
        val q = filterQuery.lowercase().trim()
        categories.filter { cat ->
            val meta = getCategoryMeta(cat.category)
            cat.category.lowercase().contains(q) ||
            meta.titleTr.lowercase().contains(q) ||
            meta.descTr.lowercase().contains(q) ||
            meta.descEn.lowercase().contains(q)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp)
    ) {
        item {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (language == AppLanguage.TR) "KATEGORİLER" else "CATEGORIES",
                        style = MaterialTheme.typography.headlineLarge,
                        color = CyberGreen
                    )
                    Text(
                        text = if (language == AppLanguage.TR) 
                            "${categories.size} Güvenlik Alanı • $totalToolsCount Araç"
                        else 
                            "${categories.size} Security Domains • $totalToolsCount Tools",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Instant Category Filter Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceDark)
                    .border(1.dp, BorderDark, RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Filter",
                        tint = CyberGreen,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    BasicTextField(
                        value = filterQuery,
                        onValueChange = onFilterQueryChange,
                        modifier = Modifier.weight(1f),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(color = TextPrimary),
                        cursorBrush = SolidColor(CyberGreen),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            if (filterQuery.isEmpty()) {
                                Text(
                                    text = if (language == AppLanguage.TR) 
                                        "Kategori veya konu filtrele (örn. Active Directory, Web)..." 
                                    else 
                                        "Filter category or domain (e.g. Active Directory, Web)...",
                                    color = TextMuted,
                                    fontSize = 13.sp
                                )
                            }
                            innerTextField()
                        }
                    )
                    if (filterQuery.isNotEmpty()) {
                        IconButton(
                            onClick = { onFilterQueryChange("") },
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

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Category Cards List
        items(filteredCategories, key = { it.category }) { cat ->
            val meta = getCategoryMeta(cat.category)
            CategoryCardItem(
                category = cat,
                meta = meta,
                language = language,
                onClick = { onSelectCategory(cat.category) }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun CategoryCardItem(
    category: CategorySummary,
    meta: com.hackingcheatsheets.app.ui.theme.CategoryMeta,
    language: AppLanguage,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(14.dp),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = SolidColor(BorderDark)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Category Icon with subtle glowing container
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(meta.color.copy(alpha = 0.14f))
                    .border(1.dp, meta.color.copy(alpha = 0.35f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = meta.icon,
                    contentDescription = category.category,
                    tint = meta.color,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Category Details
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (language == AppLanguage.TR) meta.titleTr else category.category,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )

                    // Count Badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(meta.color.copy(alpha = 0.15f))
                            .border(1.dp, meta.color.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "${category.count} ${if (language == AppLanguage.TR) "ARAÇ" else "TOOLS"}",
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            color = meta.color
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = if (language == AppLanguage.TR) meta.descTr else meta.descEn,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    maxLines = 2
                )
            }
        }
    }
}

@Composable
fun CategoryDetailView(
    categoryName: String,
    language: AppLanguage,
    tools: List<Cheatsheet>,
    totalInCategory: Int,
    filterQuery: String,
    onFilterQueryChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onNavigateToDetail: (String) -> Unit
) {
    val meta = getCategoryMeta(categoryName)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp)
    ) {
        item {
            // Category Hero Banner
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                shape = RoundedCornerShape(16.dp),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = Brush.horizontalGradient(
                        colors = listOf(meta.color.copy(alpha = 0.5f), BorderDark)
                    )
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(SurfaceVariantDark)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = meta.color,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(meta.color.copy(alpha = 0.18f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = meta.icon,
                                contentDescription = categoryName,
                                tint = meta.color,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (language == AppLanguage.TR) meta.titleTr else categoryName,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = "$totalInCategory ${if (language == AppLanguage.TR) "araç ve kılavuz" else "tools and guides"}",
                                style = MaterialTheme.typography.bodySmall,
                                color = meta.color
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (language == AppLanguage.TR) meta.descTr else meta.descEn,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // In-Category Search / Filter Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceDark)
                    .border(1.dp, BorderDark, RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search In Category",
                        tint = meta.color,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    BasicTextField(
                        value = filterQuery,
                        onValueChange = onFilterQueryChange,
                        modifier = Modifier.weight(1f),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(color = TextPrimary),
                        cursorBrush = SolidColor(meta.color),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            if (filterQuery.isEmpty()) {
                                Text(
                                    text = if (language == AppLanguage.TR) 
                                        "${if (language == AppLanguage.TR) meta.titleTr else categoryName} içinde ara..." 
                                    else 
                                        "Filter in ${categoryName}...",
                                    color = TextMuted,
                                    fontSize = 13.sp
                                )
                            }
                            innerTextField()
                        }
                    )
                    if (filterQuery.isNotEmpty()) {
                        IconButton(
                            onClick = { onFilterQueryChange("") },
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

            Text(
                text = "${tools.size} ${if (language == AppLanguage.TR) "araç listeleniyor" else "tools listed"}",
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        // Cheatsheet Cards in this category
        items(tools, key = { it.id }) { tool ->
            CheatsheetCardItem(
                tool = tool,
                language = language,
                onClick = { onNavigateToDetail(tool.id) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
