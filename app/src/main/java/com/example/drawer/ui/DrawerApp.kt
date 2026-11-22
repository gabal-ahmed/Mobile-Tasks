package com.example.drawer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

enum class Page(val title: String, val icon: ImageVector) {
    Home("Home", Icons.Filled.Home),
    Profile("Profile", Icons.Filled.Person),
    Settings("Settings", Icons.Filled.Settings)
}

private val AppColors = lightColorScheme(
    primary = Color(0xFF1B6B5A),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFCDEDE3),
    onPrimaryContainer = Color(0xFF00201A),
    secondaryContainer = Color(0xFFCDEDE3),
    onSecondaryContainer = Color(0xFF00201A),
    background = Color(0xFFF6FAF8),
    surface = Color(0xFFF6FAF8),
    surfaceContainerLow = Color(0xFFFFFFFF)
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = AppColors, content = content)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerApp() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var current by remember { mutableStateOf(Page.Home) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerShape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp)) {
                DrawerHeader()
                Spacer(Modifier.height(12.dp))
                Page.entries.forEach { page ->
                    NavigationDrawerItem(
                        icon = { Icon(page.icon, contentDescription = null) },
                        label = { Text(page.title) },
                        selected = page == current,
                        onClick = {
                            current = page
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
                    )
                }
                Spacer(Modifier.weight(1f))
                HorizontalDivider(Modifier.padding(horizontal = 24.dp))
                Text(
                    "Version 1.0",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(24.dp)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(current.title, fontWeight = FontWeight.SemiBold) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Open menu")
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        ) { padding ->
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(20.dp)
            ) {
                when (current) {
                    Page.Home -> HomePage()
                    Page.Profile -> ProfilePage()
                    Page.Settings -> SettingsPage()
                }
            }
        }
    }
}

@Composable
private fun Avatar(size: Int) {
    Box(
        Modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "A",
            fontSize = (size / 2.2).sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun DrawerHeader() {
    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(24.dp)
    ) {
        Avatar(56)
        Spacer(Modifier.height(12.dp))
        Text("Ahmed", color = Color.White, style = MaterialTheme.typography.titleMedium)
        Text("Mobile Developer", color = Color.White.copy(alpha = 0.8f), style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun HomePage() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Welcome back 👋", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("Open the menu to move between pages.", color = MaterialTheme.colorScheme.outline)
        Spacer(Modifier.height(4.dp))
        InfoCard(Icons.Filled.Star, "Getting started", "This app uses a modal navigation drawer.")
        InfoCard(Icons.Filled.Info, "Tip", "Tap outside the drawer to close it.")
    }
}

@Composable
private fun InfoCard(icon: ImageVector, title: String, body: String) {
    ElevatedCard(Modifier.fillMaxWidth()) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.width(16.dp))
            Column {
                Text(title, fontWeight = FontWeight.SemiBold)
                Text(body, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
            }
        }
    }
}

@Composable
private fun ProfilePage() {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.height(16.dp))
        Avatar(96)
        Spacer(Modifier.height(12.dp))
        Text("Ahmed", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("Mobile Developer", color = MaterialTheme.colorScheme.outline)
        Spacer(Modifier.height(24.dp))
        ElevatedCard(Modifier.fillMaxWidth()) {
            ProfileRow(Icons.Filled.LocationOn, "Cairo, Egypt")
            HorizontalDivider(Modifier.padding(horizontal = 16.dp))
            ProfileRow(Icons.Filled.DateRange, "Joined 2026")
        }
    }
}

@Composable
private fun ProfileRow(icon: ImageVector, text: String) {
    Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.width(16.dp))
        Text(text)
    }
}

@Composable
private fun SettingsPage() {
    var notifications by remember { mutableStateOf(true) }
    var darkMode by remember { mutableStateOf(false) }
    ElevatedCard(Modifier.fillMaxWidth()) {
        SettingRow(Icons.Filled.Notifications, "Notifications", notifications) { notifications = it }
        HorizontalDivider(Modifier.padding(horizontal = 16.dp))
        SettingRow(Icons.Filled.Build, "Dark mode", darkMode) { darkMode = it }
    }
}

@Composable
private fun SettingRow(icon: ImageVector, label: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.width(16.dp))
        Text(label, Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onChange)
    }
}
