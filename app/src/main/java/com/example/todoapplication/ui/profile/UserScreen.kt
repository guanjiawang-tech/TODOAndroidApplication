/**
 * User Page Screen
 * */
package com.example.todoapplication.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.todoapplication.data.local.UserStorage
import com.example.todoapplication.data.local.parseUserFile
import com.example.todoapplication.data.model.ProfileMenuItem
import com.example.todoapplication.data.model.StripConfig
import com.example.todoapplication.ui.components.Lines
import com.example.todoapplication.ui.profile.components.HeadCard
import com.example.todoapplication.ui.profile.components.MenuList
import com.example.todoapplication.ui.theme.SkyBlue

@Composable
fun UserScreen() {

    val context = LocalContext.current
    val fileContent = parseUserFile(context)

    val menuItems = listOf(
        ProfileMenuItem(
            "清除缓存",
            StripConfig(isShow = true, stripHeight = 16, stripColor = Color.Blue),
            true,
            onClick = { UserStorage.clearUser(context) }
        ),
        ProfileMenuItem(
            "读取文件",
            StripConfig(isShow = true, stripHeight = 16, stripColor = Color.Blue),
            true,
            onClick = { println("data.json 内容: $fileContent") }
        ),
        ProfileMenuItem(
            "退出登录",
            StripConfig(isShow = true, stripHeight = 16, stripColor = Color.Blue),
            true,
            onClick = { println("点击了退出登录") }
        ),
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
                showArrow = item.showArrow,
                onClick = item.onClick
            )
            Lines()
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