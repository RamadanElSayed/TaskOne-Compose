package com.instant.taskone_compose
import com.instant.taskone_compose.ui.theme.TaskOneComposeTheme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskOneComposeTheme {
                val contacts = listOf(
                    Contact("John Doe", "123456789", "123 Street A"),
                    Contact("Jane Smith", "987654321", "456 Street B"),
                    Contact("Bob Johnson", "567890123", "789 Street C"),
                    Contact("Alice Cooper", "234567890", "321 Street D"),
                    Contact("Michael Brown", "345678901", "654 Street E"),
                    Contact("Rachel Green", "456789012", "987 Street F"),
                    Contact("Monica Geller", "", "123 Avenue G"),
                    Contact("Chandler Bing", "678901234", "456 Boulevard H"),
                    Contact("Ross Geller", "789012345", "789 Road I"),
                    Contact("Phoebe Buffay", "890123456", "321 Lane J")
                )

              ContactList(contacts = contacts)
                //ContactListAlertDialog(contacts = contacts)
            }
        }
    }
}




// Data model for Contact
data class Contact(val name: String, val phone: String, val address: String) {
//    fun validateContact(): Boolean {
//        // Example validation logic
//        return name.isNotBlank() && phone.isNotBlank() && address.isNotBlank()
//    }
}

// Parent Component - Contact List
@Composable
fun ContactList(contacts: List<Contact>) {
    var selectedContact by remember { mutableStateOf<Contact?>(null) }
    var validationError by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF))
    ) {
        ContactListContent(
            contacts = contacts,
            onContactSelected = { contact ->
                if (contact.validateContact()) {
                    selectedContact = contact
                    validationError = null
                } else {
                    validationError = "Invalid contact: ${contact.name}"
                    selectedContact = null
                }
            }
        )

        validationError?.let {
            ErrorDialog(
                message = it,
                onClose = { validationError = null }
            )
        }

        selectedContact?.let {
            ContactDetailDialog(
                contact = it,
                onClose = { selectedContact = null }
            )
        }
    }
}

// List Rendering Component
@Composable
fun ContactListContent(contacts: List<Contact>, onContactSelected: (Contact) -> Unit) {
    Column(
        modifier = Modifier.systemBarsPadding()
           .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        contacts.forEach { contact ->
            ContactItem(contact = contact) {
                onContactSelected(contact)
            }
        }
    }
}

// Individual Contact Component
@Composable
fun ContactItem(contact: Contact, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick)
            .shadow(4.dp, RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = contact.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF333333),
                    modifier = Modifier.weight(1f)
                )
            Column(modifier = Modifier.weight(2f)) {
                Text(
                    text = contact.phone,
                    fontSize = 14.sp,
                    color = Color(0xFF777777)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = contact.address,
                    fontSize = 14.sp,
                    color = Color(0xFF777777)
                )
            }
        }
    }
}

// Validation Error Dialog Component
@Composable
fun ErrorDialog(message: String, onClose: () -> Unit) {
    FullScreenCenteredBox(content = {
        Text(text = message, color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onClose,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text(text = "Close", color = Color.White)
        }
    })
}

// Selected Contact Detail Dialog Component
@Composable
fun ContactDetailDialog(contact: Contact, onClose: () -> Unit) {
    FullScreenCenteredBox(content = {
        Text(text = "Name: ${contact.name}", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = "Phone: ${contact.phone}", fontSize = 16.sp, color = Color.Gray)
        Text(text = "Address: ${contact.address}", fontSize = 16.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onClose,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
        ) {
            Text(text = "Close", color = Color.White)
        }
    })
}


// Utility Component for Full Screen Centered Box
@Composable
fun FullScreenCenteredBox(content: @Composable () -> Unit) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xAA000000)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.padding(32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
            }
        }
    }
}





// Parent Component - Contact List
@Composable
fun ContactListAlertDialog(contacts: List<Contact>) {
    var selectedContact by remember { mutableStateOf<Contact?>(null) }
    var validationError by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF))
    ) {
        ContactListContentAlertDialog(
            contacts = contacts,
            onContactSelected = { contact ->
                if (contact.validateContact()) {
                    selectedContact = contact
                    validationError = null
                } else {
                    validationError = "Invalid contact: ${contact.name}"
                    selectedContact = null
                }
            }
        )

        validationError?.let {
            ErrorDialogAlertDialog(
                message = it,
                onClose = { validationError = null }
            )
        }

        selectedContact?.let {
            ContactDetailDialogAlertDialog(
                contact = it,
                onClose = { selectedContact = null }
            )
        }
    }
}

// List Rendering Component
@Composable
fun ContactListContentAlertDialog(contacts: List<Contact>, onContactSelected: (Contact) -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        contacts.forEach { contact ->
            ContactItemAlertDialog(contact = contact) {
                onContactSelected(contact)
            }
        }
    }
}

// Individual Contact Component
@Composable
fun ContactItemAlertDialog(contact: Contact, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick)
            .shadow(4.dp, RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(
                text = contact.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF333333),
                modifier = Modifier.weight(1f)
            )
            Column(modifier = Modifier.weight(2f)) {
                Text(
                    text = contact.phone,
                    fontSize = 14.sp,
                    color = Color(0xFF777777)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = contact.address,
                    fontSize = 14.sp,
                    color = Color(0xFF777777)
                )
            }
        }
    }
}

// Validation Error Dialog Component using AlertDialog with enhanced UI
@Composable
fun ErrorDialogAlertDialog(message: String, onClose: () -> Unit) {
    AlertDialog(
        onDismissRequest = onClose,
        title = {
            Text(
                text = "Error",
                fontWeight = FontWeight.Bold,
                color = Color.Red,
                fontSize = 20.sp
            )
        },
        text = {
            Text(
                text = message,
                color = Color(0xFFB00020), // More subtle but clear red
                fontSize = 16.sp,
                modifier = Modifier.padding(8.dp)
            )
        },
        confirmButton = {
            Button(
                onClick = onClose,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text(text = "Close", color = Color.White)
            }
        },
        containerColor = Color(0xFFFFEAEA), // Light red background for better readability
        shape = RoundedCornerShape(16.dp), // Softer dialog edges
    )
}

// Selected Contact Detail Dialog Component using AlertDialog with enhanced UI
@Composable
fun ContactDetailDialogAlertDialog(contact: Contact, onClose: () -> Unit) {
    AlertDialog(
        onDismissRequest = onClose,
        title = {
            Text(
                text = "Contact Details",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF4CAF50) // Green title for success
            )
        },
        text = {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = "Name: ${contact.name}", fontSize = 18.sp, color = Color.Black)
                Text(text = "Phone: ${contact.phone}", fontSize = 16.sp, color = Color.Gray)
                Text(text = "Address: ${contact.address}", fontSize = 16.sp, color = Color.Gray)
            }
        },
        confirmButton = {
            Button(
                onClick = onClose,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // Green for success
            ) {
                Text(text = "Close", color = Color.White)
            }
        },
        containerColor = Color(0xFFE8F5E9), // Light green background for success dialog
        shape = RoundedCornerShape(16.dp), // Softer dialog edges
    )
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TaskOneComposeTheme  {
        val contacts = listOf(
            Contact("John Doe", "123456789", "123 Street A"),
            Contact("Jane Smith", "987654321", "456 Street B"),
            Contact("Bob Johnson", "567890123", "789 Street C"),)
    }
}




