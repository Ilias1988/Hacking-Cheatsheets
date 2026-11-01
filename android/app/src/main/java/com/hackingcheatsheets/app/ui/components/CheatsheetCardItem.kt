package com.hackingcheatsheets.app.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hackingcheatsheets.app.data.AppLanguage
import com.hackingcheatsheets.app.data.Cheatsheet
import com.hackingcheatsheets.app.ui.theme.BorderDark
import com.hackingcheatsheets.app.ui.theme.CodeBlockBg
import com.hackingcheatsheets.app.ui.theme.CyberGreen
import com.hackingcheatsheets.app.ui.theme.SurfaceDark
import com.hackingcheatsheets.app.ui.theme.SurfaceVariantDark
import com.hackingcheatsheets.app.ui.theme.TextMuted
import com.hackingcheatsheets.app.ui.theme.TextPrimary
import com.hackingcheatsheets.app.ui.theme.TextSecondary
import com.hackingcheatsheets.app.ui.theme.getCategoryMeta
import org.json.JSONArray

@Composable
fun CheatsheetCardItem(
    tool: Cheatsheet,
    language: AppLanguage,
    onClick: () -> Unit,
    showQuickCopy: Boolean = false
) {
    val context = LocalContext.current
    val meta = getCategoryMeta(tool.category)

    val firstCmd = if (showQuickCopy && !tool.quickCommandsJson.isNullOrBlank()) {
        try {
            val arr = JSONArray(tool.quickCommandsJson)
            if (arr.length() > 0) arr.getString(0) else null
        } catch (e: Exception) {
            null
        }
    } else null

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = SurfaceDark),
        shape = RoundedCornerShape(12.dp),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = SolidColor(BorderDark)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Category Mini Icon Pill
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
                            contentDescription = tool.category,
                            tint = meta.color,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = if (language == AppLanguage.TR) tool.titleTr else tool.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = (if (language == AppLanguage.TR) meta.titleTr else tool.category).uppercase(),
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.SemiBold,
                                color = meta.color
                            )
                        }
                    }
                }

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Open",
                    tint = TextMuted,
                    modifier = Modifier.size(20.dp)
                )
            }

            val desc = if (language == AppLanguage.TR) tool.descriptionTr else tool.description
            if (!desc.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    color = TextSecondary
                )
            }

            // Optional 1-tap quick command snippet
            if (!firstCmd.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(CodeBlockBg)
                        .border(1.dp, BorderDark, RoundedCornerShape(6.dp))
                        .clickable {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            clipboard.setPrimaryClip(ClipData.newPlainText("Command", firstCmd))
                            Toast.makeText(context, if (language == AppLanguage.TR) "Komut kopyalandı!" else "Command copied!", Toast.LENGTH_SHORT).show()
                        }
                        .padding(horizontal = 8.dp, vertical = 5.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = firstCmd,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            color = CyberGreen,
                            maxLines = 1,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy Command",
                            tint = TextMuted,
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }
            }
        }
    }
}
