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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapplication.ui.home.HomeScreen
import com.example.todoapplication.ui.profile.UserScreen
import com.example.todoapplication.ui.theme.DarkBlue
import com.example.todoapplication.ui.theme.Gray500
import com.example.todoapplication.ui.theme.SkyBlue
import com.example.todoapplication.ui.theme.TODOApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // ===================== To Do Application Main Activity ===================== //
            TODOApplicationTheme {
                AppLayout()
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