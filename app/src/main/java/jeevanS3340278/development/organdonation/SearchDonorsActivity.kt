package jeevanS3340278.development.organdonation

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.os.Bundle
import android.util.Base64
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.ByteArrayInputStream
import java.io.IOException

class SearchDonorsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DonorSearchScreen()
        }
    }
}

@Composable
fun DonorSearchScreen() {
    var searchQuery by remember { mutableStateOf("") }

    val context = LocalContext.current as Activity
    val userEmail = OrganDonorProfileData.getDonorMail(context)
    var donorsList by remember { mutableStateOf(listOf<DonorFormData>()) }
    var loadDonors by remember { mutableStateOf(true) }

    LaunchedEffect(userEmail) {
        getDonorsList() { orders ->
            donorsList = orders
            loadDonors = false
        }
    }

    val filteredDonors = donorsList.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.blood.contains(searchQuery, ignoreCase = true) ||
                it.organsToDonate.contains(searchQuery, ignoreCase = true)
    }

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
                text = "Search Donor",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {


            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search Donors (by Name or Blood Group or Organ)") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Donor List
            LazyColumn {
                items(filteredDonors) { donor ->
                    DonorItem(donor)
                }
            }
        }
    }
}

// Donor Data Class
data class Donor(
    val name: String,
    val age: Int,
    val bloodGroup: String,
    val organs: List<String>
)

// Donor Item UI
@Composable
fun DonorItem(donor: DonorFormData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {


            if (donor.imageUrl.isNotEmpty())
                Image(
                    bitmap = decodeBase64ToBitmap(donor.imageUrl)!!.asImageBitmap(),
                    contentDescription = "Organ Donor Image",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(end = 8.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape),
                    contentScale = ContentScale.Crop
                )



            Column() {
                Text(
                    text = "Name : ${donor.name}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(text = "Age : ${donor.age}", fontSize = 16.sp)
                Text(text = "Blood Group : ${donor.blood}", fontSize = 16.sp)
                Text(text = "Organs : ${donor.organsToDonate}", fontSize = 16.sp)
            }
        }
    }
}

fun getDonorsList(callback: (List<DonorFormData>) -> Unit) {
    val databaseReference = FirebaseDatabase.getInstance().getReference("RegisteredDonors")

    databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val donationsList = mutableListOf<DonorFormData>()

            for (donorSnapshot in snapshot.children) { // Iterate through email keys
                for (donationSnapshot in donorSnapshot.children) { // Iterate through donation entries
                    val donation = donationSnapshot.getValue(DonorFormData::class.java)
                    donation?.let { donationsList.add(it) }
                }
            }

            callback(donationsList)
        }

        override fun onCancelled(error: DatabaseError) {
            println("Error: ${error.message}")
            callback(emptyList())
        }
    })
}


fun decodeBase64ToBitmap(base64String: String): Bitmap? {
    val decodedString = Base64.decode(base64String, Base64.DEFAULT)
    val originalBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    return originalBitmap
}


