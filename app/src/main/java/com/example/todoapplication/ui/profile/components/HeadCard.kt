package com.example.todoapplication.ui.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapplication.R

@Composable
fun HeadCard(
    name: String = "users",
    imageRes: Int = R.drawable.default_image // default
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 左侧头像
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "头像",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        // name
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = name,
                fontSize = 18.sp,
                color = Color(0xFF333333),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}