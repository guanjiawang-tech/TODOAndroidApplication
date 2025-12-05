package com.example.todoapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapplication.data.api.Client.apiService
import com.example.todoapplication.data.local.TodoStorage
import com.example.todoapplication.data.local.UserStorage
import com.example.todoapplication.data.local.parseUserFile
import com.example.todoapplication.data.repository.ToDoRepository
import com.example.todoapplication.ui.home.HomeScreen
import com.example.todoapplication.ui.login.LoginScreen
import com.example.todoapplication.ui.profile.UserScreen
import com.example.todoapplication.ui.theme.DarkBlue
import com.example.todoapplication.ui.theme.Gray500
import com.example.todoapplication.ui.theme.SkyBlue
import com.example.todoapplication.ui.theme.TODOApplicationTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // init files
        UserStorage.initStorage(this)

        enableEdgeToEdge()
        setContent {
            // ===================== To Do Application Main Activity ===================== //
            TODOApplicationTheme {
                var user by remember { mutableStateOf(UserStorage.getUser(this)) }
                val scope = rememberCoroutineScope()

                val context = LocalContext.current
                //  本地文件
                val fileContent = parseUserFile(context)

                if (user == null) {
                    LoginScreen(
                        apiService = apiService,
                        onLoginSuccess = { username ->
                            user = username // Reload
                        },
                        onSkipLogin = {
                            UserStorage.saveUser(this, "guest", "guest")
                            user = "guest"
                        }
                    )
                } else {
                    scope.launch {
                        val repo = ToDoRepository()
                        val todoResponse  = repo.GetTodoList(fileContent?.id ?: "")

                        if (todoResponse?.code == true && !todoResponse.data.isNullOrEmpty()) {
                            TodoStorage.saveTodo(context, todoResponse.data!!)  // 传入真正的 List<Todo>
                            println("📁 已写入文件 --> ${todoResponse.data!!.size} 条记录")
                        } else {
                            println("⚠ 未获取到 Todo 数据")
                        }

//                        println("List -> $todoResponse ")
                    }
                    AppLayout()  // Login Success
                }
            }
        }
    }
}

@Composable
fun AppLayout() {

    /**
     * Page Status
     * 0 -> 首页
     * 1 -> 我的
     * */
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            //Footer
            BottomMenu(selectedTab) {
                selectedTab = it
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            when(selectedTab){
                0 -> HomeScreen()
                1 -> UserScreen()
            }
        }
    }
}

@Composable
fun BottomMenu(selectedTabBar : Int, onTabSelected: (Int) -> Unit) {
    // TODO: Make and Show Botton Menu

    //  Menu List
    val menuItems = listOf(
        Pair("首页", R.drawable.icon_home),
        Pair("我的", R.drawable.icon_user)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(SkyBlue),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        menuItems.forEachIndexed { index, item ->
            //  component for tool bar
            NavigationBarItem(
                selected = selectedTabBar == index,
                onClick = { onTabSelected(index) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.second),
                        contentDescription = item.first,
                        modifier = Modifier.size(26.dp)
                    )
                },
                label = {
                    Text(text = item.first)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = DarkBlue,
                    selectedTextColor = DarkBlue,
                    unselectedIconColor = Gray500,
                    unselectedTextColor = Gray500,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

// To Preview
@Preview(showBackground = true)
@Composable
fun PreviewAppLayout() {
    TODOApplicationTheme {
        AppLayout()
    }
}