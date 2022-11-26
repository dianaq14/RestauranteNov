package com.example.restaurante.room_database.AdminProducto

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

object ImagenController {
    fun selectPhoneFromGallery(activity: Activity,code:Int){
        val intent = Intent(Intent(Intent.ACTION_PICK))
        intent.type="image/*"
        activity.startActivityForResult(intent,code)
    }
    fun saveImagen(context: Context,id:Long,uri: Uri){
        val file = File(context.filesDir,id.toString())
        val bytes = context.contentResolver.openInputStream(uri)?.readBytes()!!
        file.writeBytes(bytes)

        //codigo para firebase storege
        val database = Firebase.database
        val myRef= database.getReference("Productos")
        val Folder: StorageReference = FirebaseStorage.getInstance().getReference()
            .child("imagen")
        val file_name =Folder.child(id.toString())
        file_name.putFile(uri!!).addOnSuccessListener { taskSnapshort->
            file_name.getDownloadUrl().addOnSuccessListener{ uri->
                val hashMap= HashMap<String,String>()
                hashMap["link"]=java.lang.String.valueOf(uri)
                myRef.setValue(hashMap)
                Log.d("Mensaje", "se cargo correctamente")

            }

        }

    }
   fun getImagenUri(context: Context,id:Long): Uri{
        val file= File(context.filesDir,id.toString())
        return if(file.exists()) Uri.fromFile(file)
        else Uri
            .parse("android.resource://com.example.restaurante/drawable/placeholder")
    }

}