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
import androidx.compose.runtime.key
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
import com.example.todoapplication.data.model.TodoItem
import com.example.todoapplication.data.repository.ToDoRepository
import com.example.todoapplication.ui.home.components.AppDropdownMenu
import com.example.todoapplication.ui.home.components.DateList
import com.example.todoapplication.ui.home.components.EditTodoDialog
import com.example.todoapplication.ui.home.components.Todo
import com.example.todoapplication.ui.home.utils.getDefaultSortedTodos
import com.example.todoapplication.ui.theme.BlueNormal
import com.example.todoapplication.ui.theme.SkyBlue
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun HomeScreen() {

    val context = LocalContext.current
    val fileContent = parseUserFile(context)
    val scope = rememberCoroutineScope()

    var showDialog by remember { mutableStateOf(false) }
    // 维护选择的日期状态
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    // 升序/降序状态
    var isAscending by remember { mutableStateOf(false) }
    // 查询条件状态
    var selectedFilter by remember { mutableStateOf("默认") }

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
                selectedDate = selectedDate,
                onDateSelected = { selectedDate = it },
                onAddClick = { showDialog = true },
                isAscending = isAscending,
                onAscendingChange = { isAscending = it },
                selectedFilter = selectedFilter,
                onFilterChange = { selectedFilter = it }
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
            TodoList(
                todos = fileContent?.todos ?: emptyList(),
                selectedDate = selectedDate,
                selectedFilter = selectedFilter,
                isAscending = isAscending
            )
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
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onAddClick: () -> Unit,
    isAscending: Boolean,                      // 从外部传入状态
    onAscendingChange: (Boolean) -> Unit,      // 回调修改状态
    selectedFilter: String,
    onFilterChange: (String) -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    // → 用户选择的条件
//    var selectedFilter by remember { mutableStateOf<String?>(null) }

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
                onFilterChange(selected)
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
                        .padding(horizontal = 16.dp, vertical = 2.dp),
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
                            onCheckedChange = { checked ->
                                onAscendingChange(checked)
                            }
                        )
                    }
                }
                DateList(
                    selectedDate = selectedDate,
                    onDateChange = onDateSelected
                )
            }
        }
    }
}


/**
 * To do List
 * */
@Composable
fun TodoList(
    todos: List<TodoItem>,
    selectedDate: LocalDate,
    selectedFilter: String,
    isAscending: Boolean
) {
    val scrollState = rememberScrollState()


//    val context = LocalContext.current
//    val fileContent = parseUserFile(context)

    var sortedTodos = getDefaultSortedTodos(
        todos,
        selectedDate,
        selectedFilter,
        isAscending
    )

//    sortedTodos = if (isAscending) sortedTodos else sortedTodos.reversed()

//    //    测试数据
//    val testTodos = listOf(
//        TodoItem(
//            _id = "674147ff2b338f91fef87cb2",
//            userId = "674146ce439a8591c4a5841a",
//            title = "开发TODO Application111",
//            content = "今天要搭建数据库 for TODO application hello",
//            createdAt = "2025-12-04T00:00:00.000Z",
//            deadline = "2025-12-05T00:00:00.000Z",
//            status = 1,
//            priority = 1,
//            repeatType = 0,
//            classify = "学习"
//        ),
//        TodoItem(
//            _id = "6930d8df881c9afa84283b34",
//            userId = "674146ce439a8591c4a5841a",
//            title = "开发TODO Application backend",
//            content = "今天要搭建 backend for TODO application",
//            createdAt = "2025-12-04T00:00:00.000Z",
//            deadline = "2025-12-05T00:00:00.000Z",
//            status = 1,
//            priority = 2,
//            repeatType = 1,
//            classify = "工作"
//        ),
//        TodoItem(
//            _id = "6933a50477e4fe51cbb596bd",
//            userId = "674146ce439a8591c4a5841a",
//            title = "hello 113n",
//            content = "哈哈哈",
//            createdAt = "2025-12-06T03:37:40.613Z",
//            deadline = "2025-12-06T00:00:00.000Z",
//            status = 0,
//            priority = 1,
//            repeatType = 1,
//            classify = "生活"
//        ),
//        TodoItem(
//            _id = "6933a51477e4fe51cbb596bf",
//            userId = "674146ce439a8591c4a5841a",
//            title = "哈哈",
//            content = "1",
//            createdAt = "2025-12-06T03:37:56.939Z",
//            deadline = null,
//            status = 0,
//            priority = 1,
//            repeatType = 1,
//            classify = "生活"
//        )
//    )
//    val today = LocalDate.now()

//    val sortedTodos = getDefaultSortedTodos(
//        fileContent?.todos ?: emptyList(),
//        today
//    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        if (sortedTodos.isEmpty()) {
            // 空状态展示
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "暂无待办",
                    color = androidx.compose.ui.graphics.Color.Gray,
                )
            }
        } else {
            sortedTodos.forEach { todo ->
                key(todo._id) {
                    Todo(data = todo)
                }
            }
        }
    }

}