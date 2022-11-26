package com.example.restaurante

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.restaurante.room_database.AdminProducto.ImagenController
import com.example.restaurante.room_database.AdminProducto.Producto
import com.example.restaurante.room_database.AdminProducto.ProductoDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_producto.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto)

        val producto = intent.getSerializableExtra("producto") as Producto
        val imageUri = ImagenController.getImagenUri(this, producto.idProductos.toLong())
        nombre_actProd.text= producto.nombreProd
        precio_actProd.text ="$${producto.precioProd}"
        descripcion_actProd.text = producto.descripcionProd
        //la imagen recibe un valor entero y viene de activity producto
//        imgView_actProd.setImageResource(producto.imagenProd)
        imgView_actProd.setImageURI(imageUri)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.producto_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val producto= intent.getSerializableExtra("producto")as Producto
        val database = ProductoDatabase.getDatabase(this)
        val dbFirebase = FirebaseFirestore.getInstance()
        when(item.itemId){
            //ProdMenu es producto_menu.xml
            R.id.edit_item_ProdMen->{
                val intent = Intent(this, NuevoProductoActivity::class.java)
                intent.putExtra("productos", producto)
                startActivity(intent)
            }// fin edit
            R.id.delete_item_ProdMenu->{
                CoroutineScope(Dispatchers.IO).launch {
                    database.productos().delete(producto)
                    dbFirebase.collection("Productos")
                        .document(producto.idProductos.toString()).delete()
                    //codigo firebase storege
                    val database = Firebase.database
                    val myRef = database.getReference("Productos")
                    val Folder: StorageReference = FirebaseStorage.getInstance().getReference().child("imagen")
                    val file_name = Folder.child(producto.idProductos.toString()).delete()

                    this@ProductoActivity.finish()
                }//fin launch delete
            }// fin delete
        }//fin when
        return super.onOptionsItemSelected(item)
    }//fin onOptions

}// fin class