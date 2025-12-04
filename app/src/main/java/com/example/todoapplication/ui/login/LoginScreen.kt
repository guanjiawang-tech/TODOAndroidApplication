/**
 * Login Page
 * */
package com.example.todoapplication.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TextFieldDefaults
import com.example.todoapplication.R
import com.example.todoapplication.ui.components.Lines
import com.example.todoapplication.ui.theme.DarkBlue
import com.example.todoapplication.ui.theme.PureWhite
import com.example.todoapplication.ui.theme.SkyBlue

@Composable
fun LoginScreen(onLoginSuccess: (String) -> Unit) {
    //  TODO: Login Page
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    //  ====================  CSS
    /**
     * CSS For Input
     * */
    val clearTextFieldColors = TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        cursorColor = Color.DarkGray
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SkyBlue),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .width(320.dp)
                .padding(20.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = PureWhite
            ),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Logo
                Image(
                    painter = painterResource(id = R.drawable.todo_logo ),
                    contentDescription = null,
                    modifier = Modifier.height(30.dp)
                )

                // 输入框
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    placeholder = { Text("用户名") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    colors = clearTextFieldColors
                )
                Lines()

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("密码") },
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                    colors = clearTextFieldColors
                )
                Lines()

                // 登录按钮
                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkBlue,
                        contentColor = Color.White
                    )
                ) {
                    Text("Login", fontSize = MaterialTheme.typography.titleMedium.fontSize)
                }

                Spacer(modifier = Modifier.height(10.dp))

                // OR
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Divider(modifier = Modifier.weight(1f))
                    Text("   或   ", color = Color.Gray)
                    Divider(modifier = Modifier.weight(1f))
                }

                Text(
                    text = "忘记密码了？",
                    color = DarkBlue,
                    modifier = Modifier.padding(top = 10.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
