package com.example.todoapplication.ui.home.components


import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.todoapplication.ui.theme.BlueNormal
import com.example.todoapplication.ui.theme.CoralRed
import com.example.todoapplication.ui.theme.DarkBlue
import com.example.todoapplication.ui.theme.TealSoft
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EditTodoDialog(
    titleDefault: String,
    contentDefault: String,
    deadlineDefault: String?,
    priorityDefault: Int,
    repeatTypeDefault: Boolean,
    categoryDefault: String = "生活",
    onDismiss: () -> Unit,
    onConfirm: (
        title: String,
        content: String,
        deadline: String?,
        priority: Int,
        repeatType: Boolean,
        category: String
            ) -> Unit
) {
    val titleState = remember { mutableStateOf(titleDefault) }
    val contentState = remember { mutableStateOf(contentDefault) }
    val priorityState = remember { mutableStateOf(priorityDefault) }
    val repeatTypeState = remember { mutableStateOf(repeatTypeDefault) }
    val deadlineState = remember { mutableStateOf(deadlineDefault ?: "") }

    //  分类状态
    val categoryOptions = listOf("生活", "工作", "学习")
    val categoryState = remember { mutableStateOf(categoryDefault) }

    // 日期选择器
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val dateFormatter = java.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun openDatePicker() {
        if (!repeatTypeState.value) {  // 只有 repeatType 关闭时才能选择日期
            val dialog = android.app.DatePickerDialog(
                context,
                { _: android.widget.DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                    calendar.set(year, month, dayOfMonth)
                    deadlineState.value = dateFormatter.format(calendar.time)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            dialog.show()
        }
    }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("编辑待办事项") },
        text = {
            Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {

                // Title
                OutlinedTextField(
                    value = titleState.value,
                    onValueChange = { titleState.value = it },
                    label = { Text("标题") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Content
                OutlinedTextField(
                    value = contentState.value,
                    onValueChange = { contentState.value = it },
                    label = { Text("内容") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Deadline
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (deadlineState.value.isNullOrBlank() || deadlineState.value == "null")
                            "未设置截止日期"
                        else
                            "截止日期: ${deadlineState.value.take(10)}",
                        color = if (repeatTypeState.value) Color.Gray else Color.Black
                    )
                    TextButton(
                        onClick = { openDatePicker() },
                        enabled = !repeatTypeState.value
                    ) {
                        Text("选择日期")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Priority
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("优先级: ", modifier = Modifier.padding(end = 8.dp))
                    (1..3).forEach { level ->
                        val color = when(level) {
                            1 -> TealSoft
                            2 -> DarkBlue
                            3 -> CoralRed
                            else -> Color.Gray
                        }
                        Button(
                            onClick = { priorityState.value = level },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (priorityState.value == level) color else Color.LightGray
                            ),
                            modifier = Modifier.padding(end = 4.dp)
                        ) {
                            Text(level.toString(), color = Color.White)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text("分类:", modifier = Modifier.padding(bottom = 6.dp))
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    categoryOptions.forEach { option ->
                        Button(
                            onClick = { categoryState.value = option },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (categoryState.value == option) DarkBlue else Color.LightGray
                            ),
                            modifier = Modifier.padding(end = 4.dp)
                        ) {
                            Text(option, color = Color.White)
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))


                // Repeat Type
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("重复: ", modifier = Modifier.padding(end = 8.dp))
                    Switch(
                        checked = repeatTypeState.value,
                        onCheckedChange = { repeatTypeState.value = it }
                    )
                }

            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(
                        titleState.value,
                        contentState.value,
                        if (deadlineState.value.isEmpty()) null else deadlineState.value,
                        priorityState.value,
                        repeatTypeState.value,
                        categoryState.value
                    )
                }
            ) {
                Text("确定")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("取消")
            }
        }
    )
}
