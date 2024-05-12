package com.example.locamail

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class Outbox : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content(scope: CoroutineScope, drawerState: DrawerState) {

        val selectedEmail = remember { mutableStateOf<EmailModel?>(null) }
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
                val emails = EmailService().getEmails(LocalContext.current);
                LazyColumn {
                    items(emails) { email ->
                        if (email.sended && !email.spam && !email.deleted) {
                            EmailItemOutbox(email = email) {
                                // Atualize o email selecionado quando um email for clicado
                                selectedEmail.value = email
                            }
                        }
                    }
                }
            }
        }

        // Exiba os detalhes do email selecionado
        selectedEmail.value?.let { email ->
            EmailDetail().show(email = email) {
                // Feche os detalhes do email quando o usuário clicar no botão de fechar
                selectedEmail.value = null
            }
        }
    }

    @Composable
    fun EmailItemOutbox(email: EmailModel, onItemClick: () -> Unit) {
        // Aqui você pode criar a IU para exibir um único item de e-mail
        // Por exemplo:
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onItemClick)
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
                Text(text = email.date, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}