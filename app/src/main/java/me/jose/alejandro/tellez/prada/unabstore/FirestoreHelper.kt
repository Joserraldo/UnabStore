package me.jose.alejandro.tellez.prada.unabstore
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


object FirestoreHelper {

    private val db = Firebase.firestore

    fun agregarProducto(producto: Producto, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        db.collection("productos")
            .add(producto)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onError(e) }
    }

    fun obtenerProductos(onResult: (List<Producto>) -> Unit) {
        db.collection("productos")
            .get()
            .addOnSuccessListener { result ->
                val productos = result.map { doc ->
                    doc.toObject(Producto::class.java).copy(id = doc.id)
                }
                onResult(productos)
            }
    }

    fun eliminarProducto(id: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        db.collection("productos").document(id)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onError(e) }
    }
}
