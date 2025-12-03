package com.example.todoapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                0 -> HomePage()
                1 -> UserPage()
            }
        }
    }
}

@Composable
fun UserPage() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // TODO: To Show User Page
        Text("User Page Show")
    }
}

@Composable
fun HomePage() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header
        // TODO: To Show Select Calendar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .background(Color(0xFF90CAF9)), // 蓝色背景
            contentAlignment = Alignment.Center
        ) {
            Text("Select Calendar", color = Color.White)
        }

        // Main Part
        //  TODO: To Show TODO List
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(5f)
                .background(Color(0xFFBBDEFB)), // 浅蓝背景
            contentAlignment = Alignment.Center
        ) {
            Text("TODO List")
        }
    }
}

@Composable
fun BottomMenu(selectedTabBar : Int, onTabSelected: (Int) -> Unit) {
    // TODO: Make and Show Botton Menu
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color(0xFF1976D2)), // 菜单栏背景色
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "首页",
            color = Color.White,
            modifier = Modifier.clickable(){
                onTabSelected(0)
            }
        )
        Text(
            "我的",
            color = Color.White,
            modifier = Modifier.clickable(){
                onTabSelected(1)
            }
        )
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