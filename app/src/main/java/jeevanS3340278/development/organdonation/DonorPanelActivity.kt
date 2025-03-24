package jeevanS3340278.development.organdonation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DonorPanelActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DonorPanelActivityScreen()
        }
    }
}

@Composable
fun DonorPanelActivityScreen() {

    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color.Red
                )
                .padding(12.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Organ Donation App",
                color = Color.White,
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "By Jeevan Reddy",
                color = Color.White,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier
                    .clickable {
                        ManageDonorData.selectedScreen=1
                        ManageDonorData.resetData()
                        context.startActivity(Intent(context, RegisterDonorActivity::class.java))
                    }
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = colorResource(id = R.color.black),
                        shape = RoundedCornerShape(6.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .size(128.dp)
                        .padding(vertical = 6.dp),
                    painter = painterResource(id = R.drawable.donor_registration),
                    contentDescription = "Donor Registration"
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                        .padding(vertical = 6.dp),
                    text = "Donor Registration",
                    color = Color.White,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .clickable {
                        context.startActivity(Intent(context, SearchDonorsActivity::class.java))
                    }
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = colorResource(id = R.color.black),
                        shape = RoundedCornerShape(6.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .size(128.dp)
                        .padding(vertical = 6.dp)
                        ,
                    painter = painterResource(id = R.drawable.search_donor),
                    contentDescription = "Search Donor"
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                        .padding(vertical = 6.dp),
                    text = "Search Donor",
                    color = Color.White,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .clickable {
                        context.startActivity(Intent(context, ManageDonorActivity::class.java))
                    }
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = colorResource(id = R.color.black),
                        shape = RoundedCornerShape(6.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .size(128.dp)
                        .padding(vertical = 6.dp)
                        ,
                    painter = painterResource(id = R.drawable.manage_donor),
                    contentDescription = "Update/Mange Donor Details"
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                        .padding(vertical = 6.dp),
                    text = "Update/Mange Donor Details",
                    color = Color.White,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .clickable {
                        context.startActivity(Intent(context, FAQActivity::class.java))
                    }
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = colorResource(id = R.color.black),
                        shape = RoundedCornerShape(6.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .size(128.dp)
                        .padding(vertical = 6.dp)
                        ,
                    painter = painterResource(id = R.drawable.learn_organ_donation),
                    contentDescription = "Learn About Organ Donation"
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                        .padding(vertical = 6.dp),
                    text = "Learn About Organ Donation",
                    color = Color.White,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

//          DonorOptionsScreen()

        }


    }
}

@Composable
fun DonorOptionsScreen() {

    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DonorButton(text = "Register as Donor") {
            context.startActivity(Intent(context, RegisterDonorActivity::class.java))
            (context as Activity).finish()
        }
        DonorButton(text = "Search Donor") {
            context.startActivity(Intent(context, SearchDonorsActivity::class.java))
            (context as Activity).finish()
        }
        DonorButton(text = "Update/Manage Donor Profile") { /* Handle click */ }
        DonorButton(text = "Learn about Donation") { /* Handle click */ }
    }
}

@Composable
fun DonorButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(60.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text = text, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}


@Preview(showBackground = true)
@Composable
fun DonorPanelActivityScreenPreview() {
    DonorPanelActivityScreen()
}
