package com.example.todoapplication.ui.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapplication.R

data class StripConfig(
    val isShow: Boolean = false,
    val stripHeight: Int = 12,
    val stripColor: Color = Color.Blue
)

@Composable
fun MenuList(
    title: String,
    strip: StripConfig = StripConfig(),
    titleImage: Int? = null,
    subTitle: String? = null,
    rightSlot: String? = null,
    showArrow: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(0.dp))
            .padding(horizontal = 22.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 左侧展示
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (strip.isShow) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(strip.stripHeight.dp)
                        .background(strip.stripColor, RoundedCornerShape(6.dp))
                )
            }

            titleImage?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = "title image",
                    modifier = Modifier.size(18.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                subTitle?.let {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        color = Color(0xFF9F9F9F)
                    )
                }
            }
        }

        // 右侧展示
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            rightSlot?.let {
                Text(
                    text = it,
                    fontSize = 12.sp,
                    color = Color(0xFF9F9F9F)
                )
            }
            if (showArrow) {
                Image(
                    painter = painterResource(id = R.drawable.right_arrow),
                    contentDescription = "arrow",
                    modifier = Modifier.size(8.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}