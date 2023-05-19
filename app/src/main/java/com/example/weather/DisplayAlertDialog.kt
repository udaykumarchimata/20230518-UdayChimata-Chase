package com.example.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun DisplayAlertDialog(openDialog: MutableState<Boolean>,onClick:()->Unit) {
    if (openDialog.value) {
        AlertDialog(onDismissRequest = {
            openDialog.value = false
        }, title = {
            Text(text = "Error")
        }, text = {
            Text(text = "Something went wrong, please try again later")
        }, buttons = {
            Row(
                modifier = Modifier.padding(all = 8.dp), horizontalArrangement = Arrangement.Center
            ) {
                Button(modifier = Modifier.fillMaxWidth(), onClick = { openDialog.value = false
                onClick()
                }) {
                    Text("Dismiss")
                }
            }
        })
    }
}