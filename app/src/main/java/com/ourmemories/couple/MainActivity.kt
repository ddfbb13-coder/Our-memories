
package com.ourmemories.couple

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoriesApp()
        }
    }
}

@Composable
fun MemoriesApp() {
    val pink = Color(0xFFFF4F91)
    var name by remember { mutableStateOf("") }

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1B1019))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("💕", fontSize = 65.sp)

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Our Memories",
                color = pink,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Every moment with you is special ❤️",
                color = Color.White,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Your name", color = pink) },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = pink
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("Our Love Story ❤️")
            }
        }
    }
}
