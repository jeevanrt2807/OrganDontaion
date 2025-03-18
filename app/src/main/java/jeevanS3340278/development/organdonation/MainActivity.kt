package jeevanS3340278.development.organdonation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DonorLanding()
        }
    }
}

@Composable
fun DonorLanding()
{
    val context = LocalContext.current as Activity
    var showSplash by remember { mutableStateOf(true) }

    DisposableEffect(Unit) {
        val job = CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            showSplash = false
        }
        onDispose { job.cancel() }
    }

    if (showSplash) {
        DonorLandingScreen()

    } else {
        val loginStatus = DonorSP.fetchLoginState(context)

        if(loginStatus)
        {
            context.startActivity(Intent(context, DonorPanelActivity::class.java))
            context.finish()
        }else{
            context.startActivity(Intent(context, StartDonationActivity::class.java))
            context.finish()
        }
    }

}

@Composable
fun DonorLandingScreen() {

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

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = "Organ Donation App",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = "Be a hero. Be an organ donor",
            style = MaterialTheme.typography.titleSmall.copy(
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(36.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                modifier = Modifier.weight(1f),
                painter = painterResource(id = R.drawable.kidney), contentDescription = "Organ"
            )
            Image(
                modifier = Modifier.weight(1f),
                painter = painterResource(id = R.drawable.heart),
                contentDescription = "Organ"
            )
            Image(
                modifier = Modifier.weight(1f),
                painter = painterResource(id = R.drawable.liverorgan),
                contentDescription = "Organ"
            )

        }

        Spacer(modifier = Modifier.weight(1f))


        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = "BY",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = "Jeevan Reddy Thummala",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(36.dp))

    }
}

@Preview(showBackground = true)
@Composable
fun DonorLandingPreview() {
    DonorLandingScreen()
}