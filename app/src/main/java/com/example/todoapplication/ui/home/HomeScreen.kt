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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.todoapplication.data.api.model.InsertTodoRequest
import com.example.todoapplication.data.local.TodoStorage
import com.example.todoapplication.data.local.parseUserFile
import com.example.todoapplication.data.repository.ToDoRepository
import com.example.todoapplication.ui.home.components.AppDropdownMenu
import com.example.todoapplication.ui.home.components.DateList
import com.example.todoapplication.ui.home.components.EditTodoDialog
import com.example.todoapplication.ui.home.components.Todo
import com.example.todoapplication.ui.theme.BlueNormal
import com.example.todoapplication.ui.theme.SkyBlue
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {

    val context = LocalContext.current
    val fileContent = parseUserFile(context)
    val scope = rememberCoroutineScope()

    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header
        // TODO: To Show Select Calendar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
                .background(BlueNormal),
            contentAlignment = Alignment.Center
        ) {
            SelectCalendar(
                onAddClick = { showDialog = true },
            )
        }

        // Main Part
        //  TODO: To Show TODO List
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(7f)
                .background(SkyBlue),
            contentAlignment = Alignment.Center
        ) {
            TodoList()
        }
    }

    // 添加 To do 弹窗
    if (showDialog) {
        EditTodoDialog(
            titleDefault = "",
            contentDefault = "",
            deadlineDefault = null,
            priorityDefault = 1,
            repeatTypeDefault = false,
            categoryDefault = "生活",
            onDismiss = { showDialog = false },
            onConfirm = { title, content, deadline, priority, repeatType, category ->
                showDialog = false

                // 构造 InsertTodoRequest
                val todoRequest = InsertTodoRequest(
                    userId = fileContent?.id ?: "",
                    title = title,
                    content = content,
                    deadline = deadline,
                    status = 0,
                    priority = priority,
                    repeatType = if (repeatType) 1 else 0,
                    classify = category
                )


                val repo = ToDoRepository()
                // 调用后端插入
                scope.launch {
                    val response = repo.insertTodo(todoRequest)
                    if (response?.code == true && response.data != null) {
                        TodoStorage.addTodo(context, response.data)
                    }
                }
            }
        )
    }
}

/**
 * Show Select Calendar
 * */
@Composable
fun SelectCalendar(
    onAddClick: () -> Unit,
) {
    var menuExpanded by remember { mutableStateOf(false) }

    // → 用户选择的条件
    var selectedFilter by remember { mutableStateOf<String?>(null) }

    // → 升降序开关
    var isAscending by remember { mutableStateOf(true) }

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
                modifier = Modifier.clickable {
                    menuExpanded = true
                }
            )

            AppDropdownMenu(
                expanded = menuExpanded,
                onDismiss = { menuExpanded = false },
                options = listOf("默认", "状态", "优先级", "类型")
            ) { selected ->
                selectedFilter = selected
                menuExpanded = false
            }

            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "添加",
                modifier = Modifier.clickable {
                    onAddClick()
                }
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
            Column() {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "查询条件：${selectedFilter ?: "默认"}",
                        color = androidx.compose.ui.graphics.Color.White
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (isAscending) "升序" else "降序",
                            color = androidx.compose.ui.graphics.Color.White
                        )
                        androidx.compose.material.Switch(
                            checked = isAscending,
                            onCheckedChange = { isAscending = it }
                        )
                    }
                }
                DateList()
            }
        }
    }
}


/**
 * To do List
 * */
@Composable
fun TodoList() {
    val scrollState = rememberScrollState()


    val context = LocalContext.current
    val fileContent = parseUserFile(context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        fileContent?.todos?.forEach { todo ->
            Todo(data = todo)
        }
    }

}