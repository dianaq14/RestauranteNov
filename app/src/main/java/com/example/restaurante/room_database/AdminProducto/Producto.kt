package com.example.restaurante.room_database.AdminProducto

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "productos")
class Producto (
    @PrimaryKey(autoGenerate = true)

    var idProductos: Int=0,

    val nombreProd: String,
    val precioProd: Float,
    val descripcionProd: String,
    val imagenProd: Int
        ) : Serializable