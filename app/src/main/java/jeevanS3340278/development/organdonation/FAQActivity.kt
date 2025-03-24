package jeevanS3340278.development.organdonation

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.compose.rememberAsyncImagePainter
import java.io.File

class FAQActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FAQActivityScreen()
        }
    }
}


@Composable
fun FAQActivityScreen() {
    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.Red
                )
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        context.finish()
                    },
                painter = painterResource(id = R.drawable.back_arrow_36),
                contentDescription = "Arrow Back"
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "FAQ's",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "What is organ donation?",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Organ donation is when you decide to give an organ to save or transform the life of someone else.\n" +
                        "You can donate some organs while you are alive, and this is called living organ donation.\n" +
                        "However, most organ and tissue donations come from people who have died.",
                fontSize = 18.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Organ donation in 30 seconds",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Watch our short video to find out more about donating your organs after you die.",
                fontSize = 18.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(8.dp))

            ClickableText(
                text = AnnotatedString("Learn More"),
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline
                ),
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.organdonation.nhs.uk/helping-you-to-decide/about-organ-donation/")
                    )
                    context.startActivity(intent)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "What happens in the future",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "If you die in a way that means organ donation is a possibility, a specialist nurse will access the NHS Organ Donor Register to see if you had registered a decision.\n\n" +
                        "The specialist nurse will discuss your decision with your loved ones, or if you hadn't registered a decision, ask your loved ones if they know your feelings about organ donation.\n\n" +
                        "If a deemed consent or opt-out system applies where you live, it will be considered that you are willing to become a donor, unless you’ve opted out or are in one of the excluded groups.",
                fontSize = 18.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "If you had registered a decision to donate, or your family inform the specialist nurse that it's what you would have wanted, you could go on to save up to nine lives.\n\n" +
                        "If you had registered a decision not to become an organ donor, this will be respected.",
                fontSize = 18.sp,
                color = Color.DarkGray
            )
        }
    }
}



