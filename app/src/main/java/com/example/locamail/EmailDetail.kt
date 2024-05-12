package com.example.locamail

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class EmailDetail : ComponentActivity() {
    @Composable
    fun show(email: EmailModel, onCloseClick: () -> Unit) {
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
}