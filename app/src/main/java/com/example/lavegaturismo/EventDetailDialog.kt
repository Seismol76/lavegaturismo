package com.example.lavegaturismo

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun EventDetailDialog(event: EventEntity, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar")
            }
        },
        title = { Text(event.name, fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text("Descripción: ${event.description}")
                Text("Historia: ${event.history}")
            }
        }
    )
}