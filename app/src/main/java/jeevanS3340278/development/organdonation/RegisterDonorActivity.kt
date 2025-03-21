package jeevanS3340278.development.organdonation

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegisterDonorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DonorRegistrationForm()
        }
    }
}

@Composable
fun DonorRegistrationForm() {

    Log.e("Test", "Aftr - ${ManageDonorData.donorFormData}")

    var fullName by remember { mutableStateOf(ManageDonorData.donorFormData.name.ifEmpty { "" }) }
    var age by remember { mutableStateOf(ManageDonorData.donorFormData.age.ifEmpty { "" }) }
    var bloodGroup by remember { mutableStateOf(ManageDonorData.donorFormData.blood.ifEmpty { "" }) }
    var phoneNumber by remember { mutableStateOf(ManageDonorData.donorFormData.phoneNumber.ifEmpty { "" }) }
    var email by remember { mutableStateOf(ManageDonorData.donorFormData.email.ifEmpty { "" }) }
    var address by remember { mutableStateOf(ManageDonorData.donorFormData.address.ifEmpty { "" }) }
    var consentChecked by remember { mutableStateOf(false) }


//    fun parseOrgansList(organsToDonate: String): List<String> {
//        return organs.split(",").map { it.trim() }.filter { it.isNotEmpty() }
//    }

    val donorOrgans = ManageDonorData.donorFormData.organsToDonate.split(",").map { it.trim() }
        .filter { it.isNotEmpty() }

    var selectedOrgans by remember { mutableStateOf(donorOrgans.ifEmpty { listOf<String>() }) }

//    var selectedOrgans by remember { mutableStateOf(listOf<String>()) }

    val organOptions =
        listOf("Heart", "Liver", "Kidney", "Lungs", "Pancreas", "Cornea", "Skin", "Bone Marrow")

    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
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

            var title = ""

            title = if (ManageDonorData.selectedScreen == 1)
                "Organ Donor Registration"
            else
                "Update Donor Details"

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = title,
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

        }

//        Text(text = "Organ Donor Registration", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {


            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = age,
                onValueChange = { if (it.all { char -> char.isDigit() }) age = it },
                label = { Text("Age") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = bloodGroup,
                onValueChange = { bloodGroup = it },
                label = { Text("Blood Group (e.g., O+, A-, B+)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { if (it.all { char -> char.isDigit() }) phoneNumber = it },
                label = { Text("Phone Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Address") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Select Organs to Donate:", fontWeight = FontWeight.Bold)

            organOptions.forEach { organ ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = organ in selectedOrgans,
                        onCheckedChange = {
                            selectedOrgans = if (organ in selectedOrgans) {
                                selectedOrgans - organ
                            } else {
                                selectedOrgans + organ
                            }
                        }
                    )
                    Text(text = organ)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = consentChecked,
                    onCheckedChange = { consentChecked = it }
                )

                var consentText = ""

                consentText = if (ManageDonorData.selectedScreen == 1) {
                    "I agree to donate my selected organs"
                } else {
                    "I agree to update donor details"
                }

                Text(text = consentText)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (fullName.isNotEmpty() && age.isNotEmpty() && bloodGroup.isNotEmpty() &&
                        phoneNumber.isNotEmpty() && email.isNotEmpty() && address.isNotEmpty() &&
                        selectedOrgans.isNotEmpty() && consentChecked
                    ) {
                        // Perform registration action here (e.g., save to database)
                        Log.d(
                            "DonorRegistration",
                            "Donor Registered: $fullName, $selectedOrgans"
                        )

                        var selOrgans = ""

                        for (organ in selectedOrgans) {
                            selOrgans = "$selOrgans$organ,"
                        }

                        val donorFormData = DonorFormData(
                            fullName,
                            age,
                            blood = bloodGroup,
                            phoneNumber,
                            email,
                            address,
                            selOrgans,
                            ManageDonorData.donorFormData.id
                        )

                        if (ManageDonorData.selectedScreen == 1)
                            registerDonor(donorFormData, context)
                        else {
                            val updatedData = mapOf(
                                "address" to donorFormData.address,
                                "age" to donorFormData.age,
                                "blood" to donorFormData.blood,
                                "email" to donorFormData.email,
                                "name" to donorFormData.name,
                                "organsToDonate" to donorFormData.organsToDonate,
                                "phoneNumber" to donorFormData.phoneNumber,
                                "id" to donorFormData.id
                            )
                            updateDonorDetails(donorFormData.id, updatedData, context)

                        }


                    } else {
                        Log.d("DonorRegistration", "Please fill all fields and accept consent")
                    }
                },
                enabled = consentChecked,
                modifier = Modifier.fillMaxWidth()
            ) {

                if (ManageDonorData.selectedScreen == 1)
                    Text(text = "Register")
                else
                    Text(text = "Update Details")

            }
        }
    }
}


private fun registerDonor(donorFormData: DonorFormData, activityContext: Context) {

    val fireDB = FirebaseDatabase.getInstance()
    val databaseRef = fireDB.getReference("RegisteredDonors")

    val userEmail = OrganDonorProfileData.fetchUserMail(activityContext)

    val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
    val orderId = dateFormat.format(Date())
    donorFormData.id = orderId

    databaseRef.child(userEmail.replace(".", ",")).child(orderId).setValue(donorFormData)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(activityContext, "Donor Registered Successfully", Toast.LENGTH_SHORT)
                    .show()
                (activityContext as Activity).finish()
            } else {
                Toast.makeText(
                    activityContext,
                    "Donor Registration Failed: ${task.exception?.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        .addOnFailureListener { exception ->
            Toast.makeText(
                activityContext,
                "Donor Registration Failed: ${exception.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
}

data class DonorFormData(
    var name: String = "",
    var age: String = "",
    var blood: String = "",
    var phoneNumber: String = "",
    var email: String = "",
    var address: String = "",
    var organsToDonate: String = "",
    var id: String = ""
)


fun updateDonorDetails(donorId: String, updatedData: Map<String, Any>, context: Context) {


    try {
        val emailKey = OrganDonorProfileData.fetchUserMail(context).replace(".", ",") // Convert email for Firebase key

        val path = "RegisteredDonors/$emailKey/$donorId"
        Log.e("Test", "Patch Called : $path")
        val databaseReference = FirebaseDatabase.getInstance().getReference(path)

        databaseReference.updateChildren(updatedData)
            .addOnSuccessListener {
                Toast.makeText(
                    context,
                    "Details Updated Successfully",
                    Toast.LENGTH_SHORT
                ).show()

                (context as Activity).finish()
            }
            .addOnFailureListener {
                Toast.makeText(
                    context,
                    "Failed to update",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }catch (e: Exception)
    {
        Log.e("Test","Error Message : ${e.message}")
    }
}


