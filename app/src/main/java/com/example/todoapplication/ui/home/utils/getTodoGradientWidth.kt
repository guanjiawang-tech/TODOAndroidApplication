package com.example.todoapplication.ui.home.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.OffsetDateTime

@Composable
fun calculateGradientWidth(
    deadline: String?,
    defaultDp: Dp = 60.dp,
    urgentDp: Dp = 240.dp
): Float {
    val density = LocalDensity.current
    val widthDp = remember(deadline) {
        var dp = defaultDp

        deadline?.let { dl ->
            try {
                val deadlineDate = OffsetDateTime.parse(dl).toLocalDate()
                val today = LocalDate.now()
                val twoDaysBefore = deadlineDate.minusDays(2)

                // 判断今天是否在截止日期前两天到截止日期之间
                if (!today.isBefore(twoDaysBefore) && !today.isAfter(deadlineDate)) {
                    dp = urgentDp
                }
            } catch (_: Exception) { }
        }

        dp
    }

    return with(density) { widthDp.toPx() }
}