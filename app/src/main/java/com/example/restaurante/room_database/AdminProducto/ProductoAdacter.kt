package com.example.restaurante.room_database.AdminProducto

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.restaurante.R
import kotlinx.android.synthetic.main.item_producto.view.*


class ProductoAdacter (private val mContext: Context, private val listaProductos: List<Producto>):
    ArrayAdapter<Producto>(mContext,0,listaProductos){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext)
            .inflate(R.layout.item_producto,  parent, false)
        val producto= listaProductos[position]
        layout.nombreProd_itemProd.text= producto.nombreProd
        layout.precioProd_itemProd.text= "$${producto.precioProd}"
        //la imagen recibe un entero y esta imagen viene de item_producto
//        layout.imgProd_itemProducto.setImageResource(producto.imagenProd)
        val imageUri = ImagenController.getImagenUri(mContext,producto.idProductos.toLong())
        layout.imgProd_itemProducto.setImageURI(imageUri)
        return layout
    }
}