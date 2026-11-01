package com.hackingcheatsheets.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hackingcheatsheets.app.data.AppLanguage
import com.hackingcheatsheets.app.ui.navigation.Screen
import com.hackingcheatsheets.app.ui.screens.CategoryScreen
import com.hackingcheatsheets.app.ui.screens.DetailScreen
import com.hackingcheatsheets.app.ui.screens.FavoritesScreen
import com.hackingcheatsheets.app.ui.screens.HomeScreen
import com.hackingcheatsheets.app.ui.screens.SearchScreen
import com.hackingcheatsheets.app.ui.screens.UtilitiesScreen
import com.hackingcheatsheets.app.ui.theme.BackgroundDark
import com.hackingcheatsheets.app.ui.theme.BorderDark
import com.hackingcheatsheets.app.ui.theme.CyberGreen
import com.hackingcheatsheets.app.ui.theme.HackingCheatsheetsTheme
import com.hackingcheatsheets.app.ui.theme.SurfaceDark
import com.hackingcheatsheets.app.ui.theme.TextMuted
import com.hackingcheatsheets.app.ui.theme.TextPrimary
import com.hackingcheatsheets.app.viewmodel.CheatsheetViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: CheatsheetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HackingCheatsheetsTheme {
                MainAppScaffold(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainAppScaffold(viewModel: CheatsheetViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val language by viewModel.currentLanguage.collectAsState()

    val showBottomBar = currentRoute != null && !currentRoute.startsWith("detail/")

    val items = listOf(
        NavigationItem(Screen.Home.route, if (language == AppLanguage.TR) "Ana Sayfa" else "Home", Icons.Default.Home),
        NavigationItem(Screen.Categories.route, if (language == AppLanguage.TR) "Kategori" else "Categories", Icons.Default.Folder),
        NavigationItem(Screen.Search.route, if (language == AppLanguage.TR) "Arama" else "Search", Icons.Default.Search),
        NavigationItem(Screen.Favorites.route, if (language == AppLanguage.TR) "Favoriler" else "Favorites", Icons.Default.Favorite),
        NavigationItem(Screen.Utilities.route, if (language == AppLanguage.TR) "Araçlar" else "Utilities", Icons.Default.Build)
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    modifier = Modifier.drawBehind {
                        drawLine(
                            color = BorderDark,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            strokeWidth = 1.dp.toPx()
                        )
                    },
                    containerColor = SurfaceDark,
                    contentColor = TextPrimary
                ) {
                    items.forEach { item ->
                        val selected = currentRoute == item.route
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.title) },
                            label = { Text(item.title, fontSize = 10.sp, fontWeight = if (selected) androidx.compose.ui.text.font.FontWeight.Bold else androidx.compose.ui.text.font.FontWeight.Normal) },
                            selected = selected,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = CyberGreen,
                                selectedTextColor = CyberGreen,
                                indicatorColor = CyberGreen.copy(alpha = 0.12f),
                                unselectedIconColor = TextMuted,
                                unselectedTextColor = TextMuted
                            ),
                            onClick = {
                                if (currentRoute != item.route) {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundDark)
                .padding(innerPadding)
        ) {
            NavHost(navController = navController, startDestination = Screen.Home.route) {
                composable(Screen.Home.route) {
                    HomeScreen(
                        viewModel = viewModel,
                        onNavigateToDetail = { id -> navController.navigate(Screen.Detail.createRoute(id)) },
                        onNavigateToSearch = { navController.navigate(Screen.Search.route) },
                        onNavigateToCategories = { cat ->
                            viewModel.setCategory(cat)
                            navController.navigate(Screen.Categories.route)
                        }
                    )
                }
                composable(Screen.Categories.route) {
                    CategoryScreen(
                        viewModel = viewModel,
                        initialCategory = viewModel.selectedCategory.value,
                        onNavigateToDetail = { id -> navController.navigate(Screen.Detail.createRoute(id)) }
                    )
                }
                composable(Screen.Search.route) {
                    SearchScreen(
                        viewModel = viewModel,
                        onNavigateToDetail = { id -> navController.navigate(Screen.Detail.createRoute(id)) }
                    )
                }
                composable(Screen.Favorites.route) {
                    FavoritesScreen(
                        viewModel = viewModel,
                        onNavigateToDetail = { id -> navController.navigate(Screen.Detail.createRoute(id)) }
                    )
                }
                composable(Screen.Utilities.route) {
                    UtilitiesScreen(viewModel = viewModel)
                }
                composable(
                    route = Screen.Detail.route,
                    arguments = listOf(navArgument("id") { type = NavType.StringType })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id") ?: ""
                    DetailScreen(
                        id = id,
                        viewModel = viewModel,
                        onNavigateBack = { navController.popBackStack() },
                        onNavigateToDetail = { newId -> navController.navigate(Screen.Detail.createRoute(newId)) }
                    )
                }
            }
        }
    }
}

data class NavigationItem(
    val route: String,
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)
