/**
 * User Page Screen
 * */
package com.example.todoapplication.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.todoapplication.data.local.UserStorage
import com.example.todoapplication.ui.theme.DarkBlue

@Composable
fun UserScreen() {

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // TODO: To Show User Page
        Text("User Page Show")
        Button(
            onClick = {
                UserStorage.clearUser(context)
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
            Text("清除缓存", fontSize = MaterialTheme.typography.titleMedium.fontSize)
        }
    }
}