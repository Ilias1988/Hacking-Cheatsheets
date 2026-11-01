package com.hackingcheatsheets.app.ui.theme

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private fun Context.findActivity(): Activity? {
    var ctx = this
    while (ctx is ContextWrapper) {
        if (ctx is Activity) return ctx
        ctx = ctx.baseContext
    }
    return null
}

private val DarkColorScheme = darkColorScheme(
    primary = CyberGreen,
    onPrimary = BackgroundDark,
    primaryContainer = SurfaceVariantDark,
    onPrimaryContainer = CyberGreen,
    secondary = TerminalCyan,
    onSecondary = BackgroundDark,
    background = BackgroundDark,
    onBackground = TextPrimary,
    surface = SurfaceDark,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextSecondary,
    outline = BorderDark,
    outlineVariant = BorderLight,
    error = ExploitRed,
    onError = TextPrimary
)

@Composable
fun HackingCheatsheetsTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = view.context.findActivity()?.window
            window?.let { win ->
                win.statusBarColor = BackgroundDark.toArgb()
                win.navigationBarColor = SurfaceDark.toArgb()
                WindowCompat.getInsetsController(win, view).isAppearanceLightStatusBars = false
                WindowCompat.getInsetsController(win, view).isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
