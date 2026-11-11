package com.ourmemories.couple

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Background = Color(0xFF160B13)
private val SurfacePink = Color(0xFF28111F)
private val HotPink = Color(0xFFFF5C9A)
private val SoftPink = Color(0xFFFFB4CF)
private val Muted = Color(0xFFC9A8B7)

data class Memory(val title: String, val date: String, val note: String, val favorite: Boolean = false)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { OurMemoriesApp() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OurMemoriesApp() {
    var memories by remember {
        mutableStateOf(
            listOf(
                Memory("Our first meeting", "12 Feb 2026", "The day our story began. 💗", true),
                Memory("A little date together", "20 Mar 2026", "Every moment with you feels special."),
                Memory("Our favorite sunset", "05 Apr 2026", "One sky, two hearts. 🌙", true)
            )
        )
    }
    var showAdd by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = HotPink,
            secondary = SoftPink,
            background = Background,
            surface = SurfacePink,
            onBackground = Color.White,
            onSurface = Color.White
        )
    ) {
        Scaffold(
            containerColor = Background,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showAdd = true },
                    containerColor = HotPink,
                    contentColor = Color.White
                ) { Icon(Icons.Default.Add, contentDescription = "Add memory") }
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 22.dp, bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("OUR LITTLE WORLD", color = SoftPink, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                            Text("Our Memories", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
                            Text("Every moment, forever together.", color = Muted, fontSize = 14.sp)
                        }
                        Box(
                            modifier = Modifier.size(52.dp).background(
                                Brush.linearGradient(listOf(HotPink, Color(0xFF9B3D76))),
                                CircleShape
                            ),
                            contentAlignment = Alignment.Center
                        ) { Icon(Icons.Default.Favorite, null, tint = Color.White, modifier = Modifier.size(25.dp)) }
                    }
                }
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().background(
                            Brush.linearGradient(listOf(Color(0xFF642747), Color(0xFF301426))),
                            RoundedCornerShape(24.dp)
                        ).padding(20.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.AutoAwesome, null, tint = SoftPink)
                                Spacer(Modifier.width(8.dp))
                                Text("A LOVE STORY", color = SoftPink, fontWeight = FontWeight.Bold, fontSize = 12.sp, letterSpacing = 1.5.sp)
                            }
                            Text("Made of little moments\nand endless love.", color = Color.White, fontSize = 23.sp, fontWeight = FontWeight.Bold, lineHeight = 30.sp)
                            Text("Keep your favorite memories close.", color = Color(0xFFE8C5D5), fontSize = 13.sp)
                        }
                    }
                }
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Our timeline", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                        Text("${memories.size} memories", color = SoftPink, fontSize = 12.sp)
                    }
                }
                items(memories) { memory ->
                    MemoryCard(memory = memory, onFavorite = {
                        memories = memories.map { if (it == memory) it.copy(favorite = !it.favorite) else it }
                    })
                }
                item {
                    Text("More beautiful moments are waiting… 💞", color = Muted, fontSize = 12.sp, modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
                }
            }
        }

        if (showAdd) {
            AlertDialog(
                onDismissRequest = { showAdd = false },
                containerColor = SurfacePink,
                title = { Text("Add a memory 💗", color = Color.White) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Memory title") }, singleLine = true)
                        OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("Date (e.g. 12 Nov 2026)") }, singleLine = true)
                        OutlinedTextField(value = note, onValueChange = { note = it }, label = { Text("A little note") })
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        if (title.isNotBlank()) {
                            memories = listOf(Memory(title.trim(), date.ifBlank { "A special day" }, note.trim())) + memories
                            title = ""; date = ""; note = ""; showAdd = false
                        }
                    }) { Text("Save", color = HotPink) }
                },
                dismissButton = { TextButton(onClick = { showAdd = false }) { Text("Cancel", color = SoftPink) } }
            )
        }
    }
}

@Composable
fun MemoryCard(memory: Memory, onFavorite: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfacePink),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(54.dp).background(Color(0xFF482039), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) { Icon(Icons.Default.Photo, contentDescription = null, tint = SoftPink, modifier = Modifier.size(25.dp)) }
            Spacer(Modifier.width(13.dp))
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(memory.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(memory.date, color = SoftPink, fontSize = 11.sp)
                if (memory.note.isNotBlank()) Text(memory.note, color = Muted, fontSize = 13.sp)
            }
            IconButton(onClick = onFavorite) {
                Icon(
                    if (memory.favorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = HotPink
                )
            }
        }
    }
}
