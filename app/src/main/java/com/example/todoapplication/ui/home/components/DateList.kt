package com.example.todoapplication.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.todoapplication.ui.theme.BlueNormal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DateList(
    selectedDate: LocalDate,                // 外部传入选中日期
    onDateChange: (LocalDate) -> Unit       // 点击日期回调
) {

    var currentWeekStart by remember { mutableStateOf(getWeekStart(selectedDate)) }

    val weekDates = remember(currentWeekStart) {
        (0..6).map { currentWeekStart.plusDays(it.toLong()) }
    }

    Column(
        modifier = Modifier.fillMaxWidth().background(Color.White)
    ) {

        // -------- 日期列表 ---------
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp) // 元素间距
        ) {
            items(weekDates) { date ->

                val isToday = date == LocalDate.now()
                val isSelected = date == selectedDate

                Column(
                    modifier = Modifier
                        .width(46.dp)                // 元素宽度固定(不会超屏)
                        .clickable {
                            onDateChange(date)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // 星期
                    Text(
                        text = date.format(DateTimeFormatter.ofPattern("EEE")),
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) BlueNormal else Color.Black
                    )

                    // 日期圆形标记
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = when {
                                    isSelected -> BlueNormal
                                    isToday -> Color.LightGray
                                    else -> Color.Transparent
                                },
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = date.dayOfMonth.toString(),
                            textAlign = TextAlign.Center,
                            color = if (isSelected) Color.White else Color.Black,
                            fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }

        // -------- 左右切换按钮 --------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "← 上一周",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        currentWeekStart = currentWeekStart.minusWeeks(1)
                        val dayOfWeekOffset = (selectedDate.dayOfWeek.value - 1).toLong()
                        onDateChange(currentWeekStart.plusDays(dayOfWeekOffset))
                    },
                color = BlueNormal
            )

            Text(
                text = "下一周 →",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        currentWeekStart = currentWeekStart.plusWeeks(1)
                        val dayOfWeekOffset = (selectedDate.dayOfWeek.value - 1).toLong()
                        onDateChange(currentWeekStart.plusDays(dayOfWeekOffset))
                    },
                color = BlueNormal
            )
        }
    }
}

/** 根据日期获取当周星期一 */
fun getWeekStart(date: LocalDate): LocalDate {
    val dayOfWeek = date.dayOfWeek.value
    return date.minusDays((dayOfWeek - 1).toLong())
}