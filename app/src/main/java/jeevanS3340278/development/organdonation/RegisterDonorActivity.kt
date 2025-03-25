package jeevanS3340278.development.organdonation

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegisterDonorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DonorPhoto.isImageSelected=false

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

    val donorOrgans = ManageDonorData.donorFormData.organsToDonate.split(",").map { it.trim() }
        .filter { it.isNotEmpty() }

    var selectedOrgans by remember { mutableStateOf(donorOrgans.ifEmpty { listOf<String>() }) }

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {


            Spacer(modifier = Modifier.height(16.dp))

            if (ManageDonorData.selectedScreen == 1)
                UploadDonorImage()

            Spacer(modifier = Modifier.height(8.dp))

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
                onValueChange = {
                    if (it.all { char -> char.isDigit() } && it.length <= 10) {
                        phoneNumber = it
                    }
                },
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
                        if(DonorPhoto.isImageSelected) {
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

                            if (ManageDonorData.selectedScreen == 1) {

                                val inputStream =
                                    context.contentResolver.openInputStream(DonorPhoto.selImageUri)
                                val bitmap = BitmapFactory.decodeStream(inputStream)
                                val outputStream = ByteArrayOutputStream()
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                                val base64Image =
                                    Base64.encodeToString(
                                        outputStream.toByteArray(),
                                        Base64.DEFAULT
                                    )

                                donorFormData.imageUrl = base64Image

                                registerDonor(donorFormData, context)
                            } else {

                                val updatedData = mapOf(
                                    "address" to donorFormData.address,
                                    "age" to donorFormData.age,
                                    "blood" to donorFormData.blood,
                                    "email" to donorFormData.email,
                                    "name" to donorFormData.name,
                                    "organsToDonate" to donorFormData.organsToDonate,
                                    "phoneNumber" to donorFormData.phoneNumber,
                                    "id" to donorFormData.id,
                                    "imageUrl" to ManageDonorData.donorFormData
                                )
                                updateDonorDetails(donorFormData.id, updatedData, context)

                            }

                        }else{
                            Toast.makeText(context,"Please upload image",Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        Toast.makeText(context,"Fill all fields",Toast.LENGTH_SHORT).show()
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

    val userEmail = OrganDonorProfileData.getDonorMail(activityContext)
    val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
    val orderId = dateFormat.format(Date())
    donorFormData.id = orderId
    FirebaseDatabase.getInstance().getReference("RegisteredDonors").child(userEmail.replace(".", ",")).child(orderId).setValue(donorFormData)
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
    var id: String = "",
    var imageUrl: String = ""
)


fun updateDonorDetails(donorId: String, updatedData: Map<String, Any>, context: Context) {


    try {
        val emailKey = OrganDonorProfileData.getDonorMail(context)
            .replace(".", ",")
        val path = "RegisteredDonors/$emailKey/$donorId"
        FirebaseDatabase.getInstance().getReference(path).updateChildren(updatedData)
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
    } catch (e: Exception) {
        Log.e("Test", "Error Message : ${e.message}")
    }
}


@Composable
fun UploadDonorImage() {
    val activityContext = LocalContext.current

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val captureImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                imageUri = getImageUri(activityContext)
                DonorPhoto.selImageUri = imageUri as Uri
                DonorPhoto.isImageSelected=true
            } else {
                DonorPhoto.isImageSelected=false
                Toast.makeText(activityContext, "Capture Failed", Toast.LENGTH_SHORT).show()
            }
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                Toast.makeText(activityContext, "Permission Granted", Toast.LENGTH_SHORT).show()
                captureImageLauncher.launch(getImageUri(activityContext)) // Launch the camera
            } else {
                Toast.makeText(activityContext, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = if (imageUri != null) {
                rememberAsyncImagePainter(model = imageUri)
            } else {
                painterResource(id = R.drawable.ic_add_image)
            },
            contentDescription = "Captured Image",
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .clickable {
                    if (ContextCompat.checkSelfPermission(
                            activityContext,
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        captureImageLauncher.launch(getImageUri(activityContext))
                    } else {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (imageUri == null) {
            Text(text = "Tap the image to capture")
        }
    }
}

fun getImageUri(activityContext: Context): Uri {
    val file = File(activityContext.filesDir, "captured_image.jpg")
    return FileProvider.getUriForFile(
        activityContext,
        "${activityContext.packageName}.fileprovider",
        file
    )
}


object DonorPhoto {
    lateinit var selImageUri: Uri
    var isImageSelected = false
}

