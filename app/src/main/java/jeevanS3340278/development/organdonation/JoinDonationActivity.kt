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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.google.firebase.database.FirebaseDatabase

class JoinDonationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JoinDonationScreen()
        }
    }
}

@Composable
fun JoinDonationScreen() {

    var donorName by remember { mutableStateOf("") }

    var donorId by remember { mutableStateOf("") }
    var donorAge by remember { mutableStateOf("") }
    var donorBloodGroup by remember { mutableStateOf("") }
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
                .fillMaxWidth()
                .padding(horizontal = 6.dp)
                .align(Alignment.CenterHorizontally),
            value = donorName,
            onValueChange = { donorName = it },
            label = { Text("Donor Name*") }
        )

        Spacer(modifier = Modifier.height(6.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp)
                .align(Alignment.CenterHorizontally),
            value = donorId,
            onValueChange = { donorId = it },
            label = { Text("Donor Email*") }
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                modifier = Modifier
                    .width(100.dp)
                    .padding(horizontal = 6.dp),
                value = donorAge,
                onValueChange = { donorAge = it },
                label = { Text("Age*") }
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp),
                value = donorBloodGroup,
                onValueChange = { donorBloodGroup = it },
                label = { Text("Blood Group*") }
            )

        }

        Spacer(modifier = Modifier.height(6.dp))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp)
                .align(Alignment.CenterHorizontally),
            value = donorPassword,
            onValueChange = { donorPassword = it },
            label = { Text("Password*") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = Modifier
                .clickable {
                    when {

                        donorName.isEmpty() -> {
                            Toast
                                .makeText(context, "Name is mandatory", Toast.LENGTH_SHORT)
                                .show()

                        }

                        donorId.isEmpty() -> {
                            Toast
                                .makeText(context, "EmailId is mandatory", Toast.LENGTH_SHORT)
                                .show()
                        }

                        donorAge.isEmpty() -> {
                            Toast
                                .makeText(context, "Age is mandatory", Toast.LENGTH_SHORT)
                                .show()
                        }

                        donorBloodGroup.isEmpty() -> {
                            Toast
                                .makeText(context, "BloodGroup is mandatory", Toast.LENGTH_SHORT)
                                .show()
                        }

                        donorPassword.isEmpty() -> {
                            Toast
                                .makeText(context, "Password is mandatory", Toast.LENGTH_SHORT)
                                .show()
                        }

                        else -> {

                            val donorDetails = DonorDetails(
                                donorName,
                                donorId,
                                donorAge,
                                donorBloodGroup,
                                donorPassword
                            )

                            registerDonor(donorDetails, context)

                        }
                    }
                }
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
            text = "Join Donation",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color.White,
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            modifier = Modifier
                .clickable {
                    context.startActivity(Intent(context, StartDonationActivity::class.java))
                    context.finish()
                }
                .width(300.dp)
                .align(Alignment.CenterHorizontally),
            text = "Start Donation?",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall.copy(
                color = Color.Black,
            )
        )

    }
}


fun registerDonor(donorDetails: DonorDetails, context: Context) {

    FirebaseDatabase.getInstance().getReference("DonorDetails")
        .child(donorDetails.emailid.replace(".", ","))
        .setValue(donorDetails)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "You Registered Successfully", Toast.LENGTH_SHORT)
                    .show()
                context.startActivity(Intent(context, StartDonationActivity::class.java))
                (context as Activity).finish()

            } else {
                Toast.makeText(
                    context,
                    "Registration Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        .addOnFailureListener { _ ->
            Toast.makeText(
                context,
                "UnExpected Error",
                Toast.LENGTH_SHORT
            ).show()
        }
}


@Preview(showBackground = true)
@Composable
fun JoinDonationScreenPreview() {
    JoinDonationScreen()
}

data class DonorDetails(
    var name: String = "",
    var emailid: String = "",
    var age: String = "",
    var bloodGroup: String = "",
    var password: String = ""
)