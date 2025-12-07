/**
 * User Page Screen
 * */
package com.example.todoapplication.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.todoapplication.data.local.RepeatListStorage
import com.example.todoapplication.data.local.UserStorage
import com.example.todoapplication.data.local.parseUserFile
import com.example.todoapplication.data.model.ProfileMenuItem
import com.example.todoapplication.data.model.StripConfig
import com.example.todoapplication.data.repository.ToDoRepository
import com.example.todoapplication.ui.components.Lines
import com.example.todoapplication.ui.profile.components.HeadCard
import com.example.todoapplication.ui.profile.components.LogoutDialog
import com.example.todoapplication.ui.profile.components.MenuList
import com.example.todoapplication.ui.theme.SkyBlue
import kotlinx.coroutines.launch

@Composable
fun UserScreen(
    onLogout: () -> Unit,
    onClearCache: () -> Unit
) {

    val context = LocalContext.current
    val fileContent = parseUserFile(context)
    val repeatList = RepeatListStorage.load(context)

    var showLogoutDialog by remember {
        mutableStateOf(false)
    }
//    val scope = rememberCoroutineScope()

    val menuItems = listOf(
        ProfileMenuItem(
            "清除缓存",
            StripConfig(isShow = true, stripHeight = 16, stripColor = Color.Blue),
            true,
            onClick = {
                onClearCache()
                UserStorage.clearUser(context)
            }
        ),
        ProfileMenuItem(
            "读取文件",
            StripConfig(isShow = true, stripHeight = 16, stripColor = Color.Blue),
            true,
            onClick = {
                println("data.json 内容: $fileContent")
                println("repeatList.json 内容: $repeatList")
            }
        ),
        ProfileMenuItem(
            "退出登录",
            StripConfig(isShow = true, stripHeight = 16, stripColor = Color.Blue),
            true,
            onClick = {
                showLogoutDialog = true
//                scope.launch {
//                    val repo = ToDoRepository()
//                    val todoList = repo.GetTodoList("674146ce439a8591c4a5841a")
//                    println("List -> $todoList")
//                }
            }
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

        // 显示退出确认弹窗
        if (showLogoutDialog) {
            LogoutDialog(
                onConfirm = {
                    onClearCache()
                    UserStorage.clearUser(context)
                    showLogoutDialog = false
                    onLogout() // 通知 MainActivity 更新状态
                    showLogoutDialog = false
                },
                onDismiss = {
                    showLogoutDialog = false
                }
            )
        }

    }
}