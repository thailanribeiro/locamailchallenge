package com.example.locamail

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
            "inbox" -> inboxContent(scope, drawerState)
            "outbox" -> outboxContent(scope, drawerState)
            "trash" -> trashContent(scope, drawerState)
            else -> {
                print("nenhum")
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun inboxContent(scope: CoroutineScope, drawerState: DrawerState) {

    val selectedEmail = remember { mutableStateOf<Email?>(null) }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Caixa de Entrada")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Menu"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Check, contentDescription = "Localized description")
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Localized description",
                        )
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            Icons.Filled.Mic,
                            contentDescription = "Localized description",
                        )
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            Icons.Filled.Image,
                            contentDescription = "Localized description",
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { /* do something */ },
                        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(Icons.Filled.Add, "Localized description")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            val emails = generateDummyEmails()
            LazyColumn {
                items(emails) { email ->
                    EmailItem(email = email) {
                        // Atualize o email selecionado quando um email for clicado
                        selectedEmail.value = email
                    }
                }
            }
        }
    }

    // Exiba os detalhes do email selecionado
    selectedEmail.value?.let { email ->
        EmailDetails(email = email) {
            // Feche os detalhes do email quando o usuário clicar no botão de fechar
            selectedEmail.value = null
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun outboxContent(scope: CoroutineScope, drawerState: DrawerState) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Caixa de saída")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Menu"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Check, contentDescription = "Localized description")
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Localized description",
                        )
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            Icons.Filled.Mic,
                            contentDescription = "Localized description",
                        )
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            Icons.Filled.Image,
                            contentDescription = "Localized description",
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { /* do something */ },
                        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(Icons.Filled.Add, "Localized description")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(text = "Emails enviados")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun trashContent(scope: CoroutineScope, drawerState: DrawerState) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Lixeira")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Menu"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Check, contentDescription = "Localized description")
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Localized description",
                        )
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            Icons.Filled.Mic,
                            contentDescription = "Localized description",
                        )
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            Icons.Filled.Image,
                            contentDescription = "Localized description",
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { /* do something */ },
                        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(Icons.Filled.Add, "Localized description")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(text = "Lixeira")
        }
    }
}

@Preview
@Composable
fun PreviewSmallTopAppBarExample() {
    SmallTopAppBarExample()
}

@Composable
fun EmailItem(email: Email, onItemClick: () -> Unit) {
    // Aqui você pode criar a IU para exibir um único item de e-mail
    // Por exemplo:
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable ( onClick = onItemClick )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.Email,
            contentDescription = "Email Icon",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = email.sender, fontWeight = FontWeight.Bold)
            Text(text = email.subject, style = MaterialTheme.typography.bodyLarge)
            Text(text = email.preview, style = MaterialTheme.typography.bodySmall)
        }
    }
}

// Definição fictícia de um modelo de e-mail
data class Email(
    val id: Int,
    val sender: String,
    val subject: String,
    val preview: String,
    val content: String
)

// Função fictícia para gerar e-mails de exemplo
fun generateDummyEmails(): List<Email> {
    return List(20) { index ->
        Email(
            id = index,
            sender = "Sender $index",
            subject = "Subject $index",
            preview = "Preview of email $index",
            content = "content $index"
        )
    }
}

@Composable
fun EmailDetails(email: Email, onCloseClick: () -> Unit) {
    // Aqui você pode criar a IU para exibir os detalhes do email
    // Por exemplo:
    AlertDialog(
        onDismissRequest = onCloseClick,
        title = { Text(text = email.subject) },
        text = {
            Column {
                Text(text = "Remetente: ${email.sender}")
                Text(text = "Conteúdo: ${email.content}")
                // Adicione mais informações conforme necessário
            }
        },
        confirmButton = {
            Button(onClick = onCloseClick) {
                Text("Fechar")
            }
        }
    )
}