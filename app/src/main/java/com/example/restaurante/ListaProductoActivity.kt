package com.example.restaurante

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.restaurante.room_database.AdminProducto.Producto
import com.example.restaurante.room_database.AdminProducto.ProductoAdacter
import com.example.restaurante.room_database.AdminProducto.ProductoDatabase
import kotlinx.android.synthetic.main.activity_lista_producto.*

class ListaProductoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_producto)

        /*val producto1= Producto(0,"Pescado finandino", 25000.00f,"250 g de salmon, en salsa pimentonfinandio",R.drawable.ic_baseline_dinner_dining_24)
          val producto2= Producto(0,"Hamburgesa mista", 15000.00f,"150 g res y 150 g cerdo en salsas de la casa",R.drawable.ic_hambur24)
          val listaProductos = listOf(producto1,producto2)*/

        //viene de ProductoDatabase.kt
        var listaProductos= emptyList<Producto>()
        var database= ProductoDatabase.getDatabase(this)
        database.productos().getAll().observe(
            this, Observer { listaProductos=it
                val adater = ProductoAdacter(this, listaProductos)
                listaViewProd.adapter = adater
                //activity lista producto
            }
        )

        listaViewProd.setOnItemClickListener (){ parent, view, position, id ->
            val intent = Intent(this, ProductoActivity::class.java)
            intent.putExtra("producto", listaProductos[position])
            startActivity(intent)
        }
        bnFloat_actListProd.setOnClickListener {
            val intent= Intent(this, NuevoProductoActivity::class.java)
            startActivity(intent)
        }


    }
}