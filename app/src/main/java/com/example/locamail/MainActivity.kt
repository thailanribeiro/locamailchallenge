package com.example.locamail

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.locamail.ui.theme.LocamailTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LocamailTheme {
                Surface {
                    LoginScreen()
                    SmallTopAppBarExample()
                }
            }
        }
    }
}

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "LocaMail",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }, // Using the standard Text composable
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") }, // Using the standard Text composable
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Button(
            onClick = { /* Handle login */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login") // Using the standard Text composable
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = { /* Handle sign up */ }
        ) {
            Text("Sign up") // Using the standard Text composable
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallTopAppBarExample() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var componentOpened by remember { mutableStateOf("inbox") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Drawer title", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "Caixa de Entrada") },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Inbox,
                            contentDescription = "Caixa de Entrada"
                        )
                    },
                    selected = false,
                    onClick = {
                        componentOpened = "inbox"
                        scope.launch {
                            drawerState.apply {
                                close()
                            }
                        }
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Caixa de Saída") },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Mail,
                            contentDescription = "Caixa de Entrada"
                        )
                    },
                    selected = false,
                    onClick = {
                        componentOpened = "outbox"
                        scope.launch {
                            drawerState.apply {
                                close()
                            }
                        }
                    }

                )
                NavigationDrawerItem(
                    label = { Text(text = "Spam") },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.MailOutline,
                            contentDescription = "Spam"
                        )
                    },
                    selected = false,
                    onClick = {
                        componentOpened = "spam"
                        scope.launch {
                            drawerState.apply {
                                close()
                            }
                        }
                    }

                )
                NavigationDrawerItem(
                    label = { Text(text = "Lixeira") },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Lixeira"
                        )
                    },
                    selected = false,
                    onClick = {
                        componentOpened = "trash"
                        scope.launch {
                            drawerState.apply {
                                close()
                            }
                        }
                    }

                )

                // ...other drawer items
            }
        }
    ) {
        when (componentOpened) {
            "inbox" -> Inbox().inboxContent(scope, drawerState)
            "outbox" -> Outbox().Content(scope, drawerState)
            "spam" -> Spam().spamContent(scope, drawerState)
            "trash" -> Trash().trashContent(scope, drawerState)
            else -> {
                print("nenhum")
            }
        }
    }

}

@Preview
@Composable
fun PreviewSmallTopAppBarExample() {
    SmallTopAppBarExample()
}