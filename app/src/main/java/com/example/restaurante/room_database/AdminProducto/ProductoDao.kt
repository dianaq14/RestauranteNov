package com.example.restaurante.room_database.AdminProducto

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductoDao {
    @Query("SELECT * FROM productos")
    fun getAll(): LiveData<List<Producto>>
    @Query("SELECT * FROM productos WHERE idProductos=:id")
    fun get(id: Int): LiveData<Producto>
    @Insert
    fun insert(productos: Producto): Long
    @Update
    fun update(productos: Producto)
    @Delete
    fun delete(productos: Producto)
}