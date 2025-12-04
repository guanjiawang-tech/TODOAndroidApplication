/**
 * User Page Screen
 * */
package com.example.todoapplication.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.todoapplication.data.local.UserStorage
import com.example.todoapplication.data.local.parseUserFile
import com.example.todoapplication.ui.profile.components.HeadCard
import com.example.todoapplication.ui.profile.components.MenuList
import com.example.todoapplication.ui.profile.components.StripConfig
import com.example.todoapplication.ui.theme.DarkBlue
import com.example.todoapplication.ui.theme.SkyBlue

// 数据类定义菜单项
data class ProfileMenuItem(
    val title: String,
    val strip: StripConfig = StripConfig(isShow = false, stripHeight = 0, stripColor = Color.Transparent),
    val showArrow: Boolean = false
)

@Composable
fun UserScreen() {

    val context = LocalContext.current
    val fileContent = parseUserFile(context)

    val menuItems = listOf(
        ProfileMenuItem("清除缓存", StripConfig(isShow = true, stripHeight = 16, stripColor = Color.Blue), true),
        ProfileMenuItem("读取文件", StripConfig(isShow = true, stripHeight = 16, stripColor = Color.Green), true),
        ProfileMenuItem("退出登录", StripConfig(isShow = true, stripHeight = 16, stripColor = Color.Green), true),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SkyBlue)
    ) {
        // TODO: To Show User Page
        HeadCard(fileContent?.username ?: "user" )

        menuItems.forEach { item ->
            MenuList(
                title = item.title,
                strip = item.strip,
                showArrow = item.showArrow
            )
        }
//        Button(
//            onClick = {
//                UserStorage.clearUser(context)
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 20.dp),
//            shape = RoundedCornerShape(6.dp),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = DarkBlue,
//                contentColor = Color.White
//            )
//        ) {
//            Text("清除缓存", fontSize = MaterialTheme.typography.titleMedium.fontSize)
//        }
//
//        val fileContent = parseUserFile(context)
//        Button(
//            onClick = {
//                println("data.json 内容: $fileContent")
//            },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 20.dp),
//            shape = RoundedCornerShape(6.dp),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = DarkBlue,
//                contentColor = Color.White
//            )
//        ) {
//            Text("读取文件", fontSize = MaterialTheme.typography.titleMedium.fontSize)
//        }


    }
}