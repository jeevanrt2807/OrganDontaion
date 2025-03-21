package jeevanS3340278.development.organdonation

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
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

    // Sample Donor List
//    val donors = listOf(
//        Donor("Rahul Sharma", 32, "O+", listOf("Kidney", "Liver")),
//        Donor("Priya Menon", 28, "A+", listOf("Heart", "Cornea")),
//        Donor("Vikram Reddy", 45, "B-", listOf("Liver", "Skin")),
//        Donor("Ananya Verma", 36, "AB+", listOf("Bone Marrow", "Pancreas")),
//        Donor("Arjun Das", 30, "O-", listOf("Lungs", "Cornea")),
//        Donor("Sneha Kapoor", 27, "B+", listOf("Heart", "Kidney")),
//        Donor("Ramesh Yadav", 50, "A-", listOf("Liver", "Pancreas"))
//    )



    val context = LocalContext.current as Activity

    val userEmail = OrganDonorProfileData.fetchUserMail(context)

    var donorsList by remember { mutableStateOf(listOf<DonorFormData>()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(userEmail) {
        getDonorsList() { orders ->
            donorsList = orders

            isLoading = false
        }
    }

    val filteredDonors = donorsList.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.blood.contains(searchQuery, ignoreCase = true)
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
                text = "Organ Donor Registration",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

        }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {


            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search Donors (by Name or Blood Group)") },
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
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Donor Name : ${donor.name}", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = "Age : ${donor.age}", fontSize = 16.sp)
            Text(text = "Blood Group : ${donor.blood}", fontSize = 16.sp)
            Text(text = "Organs : ${donor.organsToDonate}", fontSize = 16.sp)
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


fun getDonorsListOld(userEmail: String, callback: (List<DonorFormData>) -> Unit) {
    val emailKey = userEmail.replace(".", ",")

    val databaseReference = FirebaseDatabase.getInstance().getReference("RegisteredDonors/")

//    val databaseReference = FirebaseDatabase.getInstance().getReference("RegisteredDonors/$emailKey")


    databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val donationsList = mutableListOf<DonorFormData>()
            for (donationSnapshot in snapshot.children) {
                val donation = donationSnapshot.getValue(DonorFormData::class.java)
                donation?.let { donationsList.add(it) }
            }
            callback(donationsList)
        }

        override fun onCancelled(error: DatabaseError) {
            println("Error: ${error.message}")
            callback(emptyList())
        }
    })
}
