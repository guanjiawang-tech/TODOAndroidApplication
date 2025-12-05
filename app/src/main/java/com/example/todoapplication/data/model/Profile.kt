package com.example.todoapplication.data.model

import androidx.compose.ui.graphics.Color

// 数据类定义菜单项
data class ProfileMenuItem(
    val title: String,
    val strip: StripConfig = StripConfig(
        isShow = false,
        stripHeight = 0,
        stripColor = Color.Transparent
    ),
    val showArrow: Boolean = false,
    val onClick: () -> Unit = {}
)

// Menu组件数据
data class StripConfig(
    val isShow: Boolean = false,
    val stripHeight: Int = 12,
    val stripColor: Color = Color.Blue
)