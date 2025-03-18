package jeevanS3340278.development.organdonation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
    val donors = listOf(
        Donor("Rahul Sharma", 32, "O+", listOf("Kidney", "Liver")),
        Donor("Priya Menon", 28, "A+", listOf("Heart", "Cornea")),
        Donor("Vikram Reddy", 45, "B-", listOf("Liver", "Skin")),
        Donor("Ananya Verma", 36, "AB+", listOf("Bone Marrow", "Pancreas")),
        Donor("Arjun Das", 30, "O-", listOf("Lungs", "Cornea")),
        Donor("Sneha Kapoor", 27, "B+", listOf("Heart", "Kidney")),
        Donor("Ramesh Yadav", 50, "A-", listOf("Liver", "Pancreas"))
    )

    val filteredDonors = donors.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.bloodGroup.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
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

// Donor Data Class
data class Donor(
    val name: String,
    val age: Int,
    val bloodGroup: String,
    val organs: List<String>
)

// Donor Item UI
@Composable
fun DonorItem(donor: Donor) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = donor.name, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = "Age: ${donor.age}", fontSize = 16.sp)
            Text(text = "Blood Group: ${donor.bloodGroup}", fontSize = 16.sp)
            Text(text = "Organs: ${donor.organs.joinToString(", ")}", fontSize = 16.sp)
        }
    }
}
