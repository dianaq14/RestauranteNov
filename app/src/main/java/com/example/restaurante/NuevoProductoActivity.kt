package com.example.restaurante

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.restaurante.room_database.AdminProducto.ImagenController
import com.example.restaurante.room_database.AdminProducto.Producto
import com.example.restaurante.room_database.AdminProducto.ProductoDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_nuevo_producto.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NuevoProductoActivity : AppCompatActivity() {

    private val SELECT_ACTIVITY= 50
    private var imageUri: Uri?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_producto)
        val database =ProductoDatabase.getDatabase(this)
        val dbFirebase = FirebaseFirestore.getInstance()

        var idProducto: Int=0
        //sirve para funcion editar y delete en producto_menu.xml
        if (intent.hasExtra("productos")){
            val producto = intent.extras?.getSerializable("productos")as Producto
            //vienen de activity_nuevo_producto.xml
            name_ActNewProd.setText(producto.nombreProd)
            price_ActNewProd.setText(producto.precioProd.toString())
            descripcion_ActNewProd.setText(producto.descripcionProd)
            idProducto= producto.idProductos
            val imageUri = ImagenController.getImagenUri(this,producto.idProductos.toLong())
            imgSelect_ActNewProd.setImageURI(imageUri)
        }

        bnGuardar_ActNewProd.setOnClickListener {
            val nombre = name_ActNewProd.text.toString()
            val precio = price_ActNewProd.text.toString().toFloat()
            val descripcion = descripcion_ActNewProd.text.toString()
            val producto = Producto(idProducto, nombre,precio,descripcion, R.drawable.ic_launcher_background )

            if (idProducto==0) {
                CoroutineScope(Dispatchers.IO).launch {
                    var result = database.productos().insert(producto)
                    //empieza firebase
                    dbFirebase.collection("Productos").document(result.toString())
                        .set(
                            hashMapOf(
                                "nombre" to nombre,
                                "precio" to precio,
                                "descripcion" to descripcion
                            )
                        )
                    //termina firebase
                    imageUri?.let {
                        ImagenController.saveImagen(this@NuevoProductoActivity,result,it)
                    }
                    this@NuevoProductoActivity.finish()
                }
            }
            else
            {
                    CoroutineScope(Dispatchers.IO).launch {
                       database.productos().update(producto)
                        //llamar a la imagen que se va a remplazar
                        imageUri?.let {
                            ImagenController.saveImagen(this@NuevoProductoActivity,idProducto.toLong(),it)
                        }
                        //empieza firebase
                        dbFirebase.collection("Productos").document(idProducto.toString())
                            .set(
                                hashMapOf(
                                    "nombre" to nombre,
                                    "precio" to precio,
                                    "descripcion" to descripcion
                                )
                            )
                        //termina firebase
                        this@NuevoProductoActivity.finish()
                    }//termina launch
                val principal=Intent(this, ListaProductoActivity::class.java)
                startActivity(principal)
                  }
        }
        //imagen activity nuevo producto
        imgSelect_ActNewProd.setOnClickListener {
            ImagenController.selectPhoneFromGallery(this,SELECT_ACTIVITY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when{
            requestCode==SELECT_ACTIVITY && resultCode==Activity.RESULT_OK ->{
                imageUri = data!!.data
                imgSelect_ActNewProd.setImageURI(imageUri)
            }
        }
    }
}