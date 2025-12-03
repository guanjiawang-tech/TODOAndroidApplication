/**
 * Home Page Screen
 * */
package com.example.todoapplication.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoapplication.ui.home.components.Todo
import com.example.todoapplication.ui.theme.BlueNormal
import com.example.todoapplication.ui.theme.SkyBlue

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
                .background(BlueNormal),
            contentAlignment = Alignment.Center
        ) {
            SelectCalendar()
        }

        // Main Part
        //  TODO: To Show TODO List
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(5f)
                .background(SkyBlue),
            contentAlignment = Alignment.Center
        ) {
            TodoList()
        }
    }
}

/**
 * Show Select Calendar
 * */
@Composable
fun SelectCalendar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BlueNormal)
    ) {
        // 上部分：图标行（占 1/5）
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // 上部分高度占比
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "菜单",
                modifier = Modifier.clickable { onMenuClick() }
            )

            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "添加",
                modifier = Modifier.clickable { onAddClick() }
            )
        }

        // 下部分：可以放日期、标题等，占 4/5
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f), // 下部分高度占比
            contentAlignment = Alignment.Center
        ) {
            // TODO: Data
            Text("Data Sessions")
        }
    }
}

private fun RowScope.onAddClick() {
    TODO("Not yet implemented")
}

private fun RowScope.onMenuClick() {
    TODO("Not yet implemented")
}

/**
 * To do List
 * */
@Composable
fun TodoList() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        repeat(20) { index ->
            Todo()
        }
    }

}