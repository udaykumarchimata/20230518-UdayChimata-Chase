package com.example.weather

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DisplayTemperature(minTemp: Double, maxTemp: Double) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            style = MaterialTheme.typography.caption.copy(fontSize = 20.sp)
                .copy(fontWeight = FontWeight(500)),
            letterSpacing = (0.5).sp,
            lineHeight = 24.sp,
            color = colorResource(id = R.color.black),
            text = stringResource(id = R.string.temperature_celsius),
            modifier = Modifier
                .padding(bottom = 4.dp)
                .align(Alignment.CenterHorizontally)
        )
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            Text(
                style = MaterialTheme.typography.caption.copy(fontSize = 14.sp)
                    .copy(fontWeight = FontWeight(400)),
                letterSpacing = (0.5).sp,
                lineHeight = 24.sp,
                text = stringResource(id = R.string.temp_min),
                color = colorResource(id = R.color.text_color),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                style = MaterialTheme.typography.caption.copy(fontSize = 14.sp)
                    .copy(fontWeight = FontWeight(400)),
                letterSpacing = (0.25).sp,
                lineHeight = 20.sp,
                text = minTemp.toString(),
                color = colorResource(id = R.color.value_color),
                modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            Text(
                style = MaterialTheme.typography.caption.copy(fontSize = 14.sp)
                    .copy(fontWeight = FontWeight(400)),
                letterSpacing = (0.5).sp,
                lineHeight = 24.sp,
                text = stringResource(id = R.string.temp_max),
                color = colorResource(id = R.color.text_color),
            )
            Text(
                style = MaterialTheme.typography.caption.copy(fontSize = 14.sp)
                    .copy(fontWeight = FontWeight(400)),
                letterSpacing = (0.25).sp,
                lineHeight = 20.sp,
                text = maxTemp.toString(),
                color = colorResource(id = R.color.value_color),
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}