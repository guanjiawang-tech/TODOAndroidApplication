/**
 * Home Page Screen
 * */
package com.example.todoapplication.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun HomeScreen() {
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