package com.example.organdonation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class StartDonationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { 
            StartDonationScreen()
        }
    }
}

@Composable
fun StartDonationScreen() {

    var donorId by remember { mutableStateOf("") }
    var donorPassword by remember { mutableStateOf("") }

    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            painter = painterResource(id = R.drawable.donateorgans),
            contentDescription = "Donate Organs"
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            modifier = Modifier
                .width(300.dp)
                .padding(horizontal = 6.dp)
                .align(Alignment.CenterHorizontally),
            value = donorId,
            onValueChange = { donorId = it },
            label = { Text("Email*") }
        )

        Spacer(modifier = Modifier.height(6.dp))

        TextField(
            modifier = Modifier
                .width(300.dp)
                .padding(horizontal = 6.dp)
                .align(Alignment.CenterHorizontally),
            value = donorPassword,
            onValueChange = { donorPassword = it },
            label = { Text("Password*") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = Modifier
                .width(300.dp)
                .background(
                    color = colorResource(id = R.color.black),
                    shape = RoundedCornerShape(6.dp)
                )
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.white),
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(vertical = 6.dp)
                .align(Alignment.CenterHorizontally),
            text = "Start Donation",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color.White,
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            modifier = Modifier
                .clickable {
                    context.startActivity(Intent(context, JoinDonationActivity::class.java))
                    context.finish()
                }
                .width(300.dp)
                .align(Alignment.CenterHorizontally),
            text = "Want to Join Donation?",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall.copy(
                color = Color.Black,
            )
        )

    }
}

@Preview(showBackground = true)
@Composable
fun StartDonationScreenPreview() {
    StartDonationScreen()
}