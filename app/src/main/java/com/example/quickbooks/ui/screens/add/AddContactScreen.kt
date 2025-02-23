package com.example.quickbooks.ui.screens.add


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.quickbooks.ui.components.CustomTextField
import com.example.quickbooks.ui.components.DatePickerDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactScreen(
    navController: NavController,
    viewModel: AddContactViewModel = hiltViewModel()
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.updatePhoto(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambah Kontak") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { imagePickerLauncher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.AccountCircle, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Pilih Foto")
            }

            CustomTextField(
                value = viewModel.nama,
                onValueChange = { viewModel.nama = it },
                label = "Nama",
                isError = viewModel.namaError
            )

            CustomTextField(
                value = viewModel.namaPanggilan,
                onValueChange = { viewModel.namaPanggilan = it },
                label = "Nama Panggilan",
                isError = viewModel.namaPanggilanError
            )

            CustomTextField(
                value = viewModel.email,
                onValueChange = { viewModel.email = it },
                label = "Email",
                isError = viewModel.emailError
            )

            CustomTextField(
                value = viewModel.alamat,
                onValueChange = { viewModel.alamat = it },
                label = "Alamat (Opsional)"
            )

            Button(
                onClick = { showDatePicker = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Tanggal Lahir: ${viewModel.getFormattedDate()}")
            }

            CustomTextField(
                value = viewModel.noHp,
                onValueChange = { viewModel.noHp = it },
                label = "No. HP/WA",
                isError = viewModel.noHpError
            )

            Button(
                onClick = { showConfirmDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("SIMPAN")
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDateSelected = { timestamp ->
                viewModel.updateTanggalLahir(timestamp)
            },
            onDismiss = { showDatePicker = false }
        )
    }

    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("Konfirmasi") },
            text = { Text("Apakah Anda yakin ingin menyimpan data ini?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.saveContact {
                            navController.navigateUp()
                        }
                    }
                ) {
                    Text("Ya")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDialog = false }) {
                    Text("Tidak")
                }
            }
        )
    }
}