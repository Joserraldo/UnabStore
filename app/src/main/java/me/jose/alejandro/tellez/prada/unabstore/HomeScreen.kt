package me.jose.alejandro.tellez.prada.unabstore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onClickLogout: () -> Unit = {}) {

    val auth = Firebase.auth
    val user = auth.currentUser

    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var productos by remember { mutableStateOf(listOf<Producto>()) }

    // 🔄 Cargar los productos al iniciar
    LaunchedEffect(true) {
        FirestoreHelper.obtenerProductos { lista ->
            productos = lista
        }
    }

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text("Unab Shop", fontWeight = FontWeight.Bold, fontSize = 28.sp)
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.Notifications, "Notificaciones")
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.ShoppingCart, "Carrito")
                    }
                    IconButton(onClick = {
                        auth.signOut()
                        onClickLogout()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, "Cerrar Sesión")
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFFFF9900),
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 🧾 Formulario
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del producto") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    val p = Producto(nombre = nombre, descripcion = descripcion, precio = precio.toDoubleOrNull() ?: 0.0)
                    FirestoreHelper.agregarProducto(p, onSuccess = {
                        // Limpiar campos y recargar lista
                        nombre = ""
                        descripcion = ""
                        precio = ""
                        FirestoreHelper.obtenerProductos { productos = it }
                    }, onError = { })
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9900))
            ) {
                Text("Guardar producto", color = Color.White)
            }

            Spacer(Modifier.height(16.dp))

            Text("Productos guardados:", fontWeight = FontWeight.Bold, fontSize = 20.sp)

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(productos) { producto ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(producto.nombre, fontWeight = FontWeight.Bold)
                                Text(producto.descripcion)
                                Text("$${producto.precio}")
                            }
                            IconButton(onClick = {
                                producto.id?.let {
                                    FirestoreHelper.eliminarProducto(it, onSuccess = {
                                        FirestoreHelper.obtenerProductos { productos = it }
                                    }, onError = {})
                                }
                            }) {
                                Icon(Icons.Default.Delete, "Eliminar", tint = Color.Red)
                            }
                        }
                    }
                }
            }
        }
    }
}
