/**
 * To Do Card Style
 * */
package com.example.todoapplication.ui.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.todoapplication.data.api.model.DeleteTodoRequest
import com.example.todoapplication.data.local.RepeatListStorage.getCompletedDates
import com.example.todoapplication.data.local.RepeatListStorage.updateCompletedDate
import com.example.todoapplication.data.local.TodoStorage
import com.example.todoapplication.data.local.parseUserFile
import com.example.todoapplication.data.model.TodoItem
import com.example.todoapplication.data.model.TodoUpdate
import com.example.todoapplication.data.repository.RepeatListRepository
import com.example.todoapplication.data.repository.ToDoRepository
import com.example.todoapplication.data.utils.truncateString
import com.example.todoapplication.ui.home.utils.calculateGradientWidth
import com.example.todoapplication.ui.theme.CoralRed
import com.example.todoapplication.ui.theme.DarkBlue
import com.example.todoapplication.ui.theme.Gray500
import com.example.todoapplication.ui.theme.TealSoft
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Todo(
    data: TodoItem?,
    selectedDate: LocalDate,
    revealWidthDp: Dp = 96.dp,         //  Translation width
    onEdit: (String) -> Unit = {

    },
    onDelete: () -> Unit = {}
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current
    val revealWidthPx = with(density) { revealWidthDp.toPx() }

    val offsetX = remember { Animatable(0f) }
    val isChecked = remember { mutableStateOf((data?.status ?: 0) == 1) }
    var todo by remember { mutableStateOf(data!!) }

    // 获取当前 todo 在 selectedDate 是否完成
    val completedDates = remember { mutableStateOf(getCompletedDates(context, todo._id)) }
    val isCompleted = remember(selectedDate, completedDates.value) {
        mutableStateOf(completedDates.value.contains(selectedDate.toString()))
    }

    val repo = ToDoRepository()

    // 控制 Dialog 显示
    val showDialog = remember { mutableStateOf(false) }
    val showDeleteDialog = remember { mutableStateOf(false) }

    // 添加 to do 修改弹窗
    if (showDialog.value) {
        EditTodoDialog(
            titleDefault = data?.title ?: "",
            contentDefault = data?.content ?: "",
            deadlineDefault = data?.deadline,
            priorityDefault = data?.priority ?: 1,
            repeatTypeDefault = data?.repeatType == 1,
            categoryDefault = data?.classify ?: "生活",
            onDismiss = { showDialog.value = false },
            onConfirm = { newTitle, newContent, newDeadline, newPriority, newRepeat, category ->
                showDialog.value = false
                todo = todo.copy(
                    title = newTitle,
                    content = newContent,
                    deadline = newDeadline,
                    priority = newPriority,
                    repeatType = if (newRepeat) 1 else 0,
                    classify = category
                )
                // 更新本地数据
                val updatedTodo = TodoUpdate(
                    title = newTitle,
                    content = newContent,
                    deadline = newDeadline,
                    priority = newPriority,
                    repeatType = if (newRepeat) 1 else 0,
                    classify = category
                )
                TodoStorage.updateTodo(context, todoId = data?._id ?: "", update = updatedTodo)
                //  数据库更新  status
                scope.launch {
                    try {
                        val todoResponse = repo.updateTodo(
                            todoId = data?._id ?: "",
                            updates = TodoUpdate(
                                title = newTitle,
                                content = newContent,
                                deadline = newDeadline,
                                priority = newPriority,
                                repeatType = if (newRepeat) 1 else 0,
                                classify = category

                            )
                        )
                        if (todoResponse?.code == true) {
                            println("后端更新成功")
                        } else {
                            println("后端更新失败")
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                // 调用回调
                onEdit(newTitle) // 或者改成返回完整数据的回调
            }
        )
    }

    // 添加 to do 删除弹窗
    if (showDeleteDialog.value) {
        DeleteTodoDialog(
            onConfirm = {
                showDeleteDialog.value = false

                // 本地删除
                TodoStorage.deleteTodo(context, data?._id ?: "")

                // 调用后端删除
                scope.launch {
                    val id = data?._id ?: return@launch
                    repo.deleteTodo(id)
                }

                onDelete() // 通知父组件更新列表
            },
            onDismiss = { showDeleteDialog.value = false }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clickable(onClick = {
                showDialog.value = true
            }),
    ) {
        // backend card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Gray500),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { showDialog.value = true },
                modifier = Modifier
                    .background(TealSoft)
                ) {
                Icon(Icons.Default.Edit, contentDescription = "编辑", tint = Color.White)
            }
            IconButton(
                onClick = { showDeleteDialog.value = true },
                modifier = Modifier
                    .background(CoralRed)) {
                Icon(Icons.Default.Delete, contentDescription = "删除", tint = Color.White)
            }
        }

        // frontend card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(offsetX.value.roundToInt(), 0) } // 用 Animatable 的值偏移
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        // delta > 0  -> move to right
                        // delta < 0  -> move to left
                        val coerced = (offsetX.value + delta).coerceIn(-revealWidthPx, 0f)
                        // Set current location
                        scope.launch { offsetX.snapTo(coerced) }
                    },
                    onDragStopped = { velocity ->
                        scope.launch {
                            val threshold = -revealWidthPx / 2f
                            val settleTo = if (offsetX.value <= threshold) -revealWidthPx else 0f
                            offsetX.animateTo(settleTo, animationSpec = tween(durationMillis = 200))
                        }
                    }
                )
        ) {
            // Card Detail List
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        elevation = 4.dp,
                        clip = false
                    )
                    .background(Color.White)
            ) {
                val gradientColors = when (todo.priority) {
                    3 -> listOf(CoralRed.copy(alpha = 0.5f), Color.Transparent)
                    2 -> listOf(DarkBlue.copy(alpha = 0.5f), Color.Transparent)
                    1 -> listOf(TealSoft.copy(alpha = 0.5f), Color.Transparent)
                    else -> listOf(Color(0xFFE0F7FA), Color.Transparent)
                }
                val gradientWidthPx = calculateGradientWidth(todo.deadline, todo.repeatType)
                gradientWidthPx
                // Card 内容
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                colors = gradientColors,
                                startX = 0f,
                                endX = gradientWidthPx
                            )
                        )
                ) {
                    Checkbox(
                        checked = if (todo.repeatType == 1) isCompleted.value else isChecked.value,
                        onCheckedChange = { checked ->
                            if (todo.repeatType == 1) {
                                // 重复任务：基于日期完成逻辑
                                isCompleted.value = checked
                                updateCompletedDate(context, todo._id, selectedDate.toString(), checked)
                                completedDates.value = getCompletedDates(context, todo._id)

                                // 调用后端同步完成状态
                                scope.launch {
                                    try {
                                        val repeatRepo = RepeatListRepository()
                                        val userId = parseUserFile(context)?.id ?: return@launch
                                        val dateStr = selectedDate.toString()
                                        val response = repeatRepo.repeatList(todo._id, userId, dateStr)
                                        if (response?.code == true) {
                                            println("重复任务后端更新成功")
                                        } else {
                                            println("重复任务后端更新失败")
                                        }
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                            } else {
                                // 普通任务：基于 status
                                isChecked.value = checked
                                todo = todo.copy(status = if (checked) 1 else 0)
                                // 更新本地
                                TodoStorage.updateTodo(
                                    context,
                                    todoId = todo._id,
                                    update = TodoUpdate(status = if (checked) 1 else 0)
                                )
                                // 调用后端更新
                                scope.launch {
                                    try {
                                        ToDoRepository().updateTodo(
                                            todoId = todo._id,
                                            updates = TodoUpdate(status = if (checked) 1 else 0)
                                        )
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                            }
                        }
                    )
                    Text(
                        text = truncateString(todo.title, 20),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        color = if (todo.repeatType == 1) {
                            if (isCompleted.value) Color.Gray else Color.Black
                        } else {
                            if (todo.status == 1) Color.Gray else Color.Black
                        },
                        textDecoration = if (todo.repeatType == 1) {
                            if (isCompleted.value) TextDecoration.LineThrough else TextDecoration.None
                        } else {
                            if (todo.status == 1) TextDecoration.LineThrough else TextDecoration.None
                        }
                    )
                }
            }
        }
    }
}