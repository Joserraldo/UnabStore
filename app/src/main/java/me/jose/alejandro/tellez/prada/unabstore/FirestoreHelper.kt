package me.jose.alejandro.tellez.prada.unabstore

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.FirebaseFirestoreSettings

object FirestoreHelper {

    private val db = Firebase.firestore
    private const val TAG = "FirestoreHelper"

    init {
        // Configurar Firestore para mejor debugging
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        db.firestoreSettings = settings

        Log.d(TAG, "FirestoreHelper inicializado")
        Log.d(TAG, "Firestore App: ${db.app.name}")
    }

    fun agregarProducto(producto: Producto, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        Log.d(TAG, "=== INTENTANDO AGREGAR PRODUCTO ===")
        Log.d(TAG, "Nombre: ${producto.nombre}")
        Log.d(TAG, "Descripción: ${producto.descripcion}")
        Log.d(TAG, "Precio: ${producto.precio}")

        // Convertir el producto a un mapa para guardarlo
        val productoData = hashMapOf(
            "nombre" to producto.nombre,
            "descripcion" to producto.descripcion,
            "precio" to producto.precio,
            "timestamp" to com.google.firebase.Timestamp.now()
        )

        db.collection("productos")
            .add(productoData)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "✅ ÉXITO: Producto agregado con ID: ${documentReference.id}")
                Log.d(TAG, "Ruta completa: productos/${documentReference.id}")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "❌ ERROR al agregar producto", e)
                Log.e(TAG, "Tipo de error: ${e.javaClass.simpleName}")
                Log.e(TAG, "Mensaje: ${e.message}")
                onError(e)
            }
    }

    fun obtenerProductos(onResult: (List<Producto>) -> Unit) {
        Log.d(TAG, "=== OBTENIENDO PRODUCTOS ===")

        db.collection("productos")
            .orderBy("nombre", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { result ->
                Log.d(TAG, "✅ Respuesta recibida de Firestore")
                Log.d(TAG, "Documentos encontrados: ${result.size()}")
                Log.d(TAG, "Documentos vacíos: ${result.isEmpty}")

                if (result.isEmpty) {
                    Log.w(TAG, "⚠️ La colección 'productos' está vacía o no existe")
                }

                val productos = result.documents.mapNotNull { doc ->
                    try {
                        Log.d(TAG, "Procesando documento ID: ${doc.id}")
                        Log.d(TAG, "Datos del documento: ${doc.data}")

                        val producto = Producto(
                            id = doc.id,
                            nombre = doc.getString("nombre") ?: "",
                            descripcion = doc.getString("descripcion") ?: "",
                            precio = doc.getDouble("precio") ?: 0.0
                        )
                        Log.d(TAG, "Producto convertido: $producto")
                        producto
                    } catch (e: Exception) {
                        Log.e(TAG, "❌ Error al convertir documento: ${doc.id}", e)
                        null
                    }
                }

                Log.d(TAG, "Total productos procesados correctamente: ${productos.size}")
                productos.forEach {
                    Log.d(TAG, "- ${it.nombre} ($${it.precio})")
                }

                onResult(productos)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "❌ ERROR al obtener productos", e)
                Log.e(TAG, "Tipo de error: ${e.javaClass.simpleName}")
                Log.e(TAG, "Mensaje: ${e.message}")
                onResult(emptyList())
            }
    }

    fun eliminarProducto(id: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        Log.d(TAG, "=== ELIMINANDO PRODUCTO ===")
        Log.d(TAG, "ID: $id")

        db.collection("productos").document(id)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "✅ Producto eliminado exitosamente: $id")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "❌ Error al eliminar producto: $id", e)
                Log.e(TAG, "Mensaje: ${e.message}")
                onError(e)
            }
    }

    fun actualizarProducto(id: String, producto: Producto, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        Log.d(TAG, "=== ACTUALIZANDO PRODUCTO ===")
        Log.d(TAG, "ID: $id")

        val productoData = hashMapOf(
            "nombre" to producto.nombre,
            "descripcion" to producto.descripcion,
            "precio" to producto.precio
        )

        db.collection("productos").document(id)
            .update(productoData as Map<String, Any>)
            .addOnSuccessListener {
                Log.d(TAG, "✅ Producto actualizado: $id")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "❌ Error al actualizar producto: $id", e)
                onError(e)
            }
    }

    // Función de prueba para verificar conectividad
    fun testConexion(onResult: (Boolean, String) -> Unit) {
        Log.d(TAG, "=== PROBANDO CONEXIÓN A FIRESTORE ===")

        db.collection("test")
            .document("conexion")
            .set(mapOf("test" to true, "timestamp" to com.google.firebase.Timestamp.now()))
            .addOnSuccessListener {
                Log.d(TAG, "✅ Conexión exitosa a Firestore")
                onResult(true, "Conexión exitosa")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "❌ Error de conexión a Firestore", e)
                onResult(false, e.message ?: "Error desconocido")
            }
    }
}