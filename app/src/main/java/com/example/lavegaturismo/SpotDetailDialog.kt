package com.example.lavegaturismo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SpotDetailDialog(spot: SpotEntity, onDismiss: () -> Unit, navController: NavController) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Row {
                TextButton(onClick = onDismiss) {
                    Text("Cerrar")
                }
                Spacer(modifier = Modifier.width(8.dp))
//                TextButton(onClick = {
//                    navController.navigate("map/${spot.name}/${spot.latitude}/${spot.longitude}")
//                }) {
//                    Text("Ver en Mapa")
//                }
            }
        },
        title = { Text(spot.name, fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text("Descripción: ${spot.description}")
                Text("Historia: ${spot.history}")
                Text("Creador: ${spot.creator}")
                Text("Eventos: ${spot.events}")
                Text("Dato Curioso: ${spot.funFact}")
            }
        }
    )
}