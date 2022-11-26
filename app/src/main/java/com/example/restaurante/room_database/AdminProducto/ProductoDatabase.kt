package com.example.restaurante.room_database.AdminProducto

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Producto::class], version = 1)
abstract class ProductoDatabase :RoomDatabase() {
    abstract fun productos(): ProductoDao
    companion object{
        @Volatile
        private var INSTANCE:ProductoDatabase?=null

        fun getDatabase(context: Context):ProductoDatabase{
            val tempInstance= INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance=Room.databaseBuilder(
                    context.applicationContext,
                    ProductoDatabase::class.java,
                    "producto_dbRestaurante"
                ).build()
                INSTANCE=instance
                return instance
            }
        }
    }

}