package com.example.restaurante

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MyTaskListAdapter(context: AppCompatActivity,
                        val info: Bundle):
        RecyclerView.Adapter<MyTaskListAdapter.MyViewHolder>()
 {
            class MyViewHolder(val layout: View): RecyclerView.ViewHolder(layout)
     private var context: AppCompatActivity = context
     var myTaskTitle: ArrayList<String> = info.getStringArrayList("titles") as ArrayList<String>
     var myTaskTime: ArrayList<String> = info.getStringArrayList("times") as ArrayList<String>
     var myTaskPlace: ArrayList<String> = info.getStringArrayList("places") as ArrayList<String>
     var myTaskIds: ArrayList<String> = info.getStringArrayList("ids") as ArrayList<String>

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
         val layout = LayoutInflater.from(parent.context).inflate(R.layout.food_list_item,parent,false)
     return MyViewHolder(layout)
     }

     override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
         var tasktitle = holder.layout.findViewById<TextView>(R.id.tasktitle)
         tasktitle.text= myTaskTitle[position]
         var tasktime = holder.layout.findViewById<TextView>(R.id.tasktime)
         tasktime.text= myTaskTime[position]
         var taskplace = holder.layout.findViewById<TextView>(R.id.taskplace)
         taskplace.text= myTaskPlace[position]

         holder.layout.setOnClickListener{
             Toast.makeText(holder.itemView.context,tasktitle.text, Toast.LENGTH_LONG).show()
             val datos = Bundle()
             datos.putString("tarea",tasktitle.text as String)
             datos.putString("hora",tasktime.text as String)
             datos.putString("lugar", myTaskPlace[position])
             datos.putString("id",  myTaskIds[position])
             context.getSupportFragmentManager()?.beginTransaction()
                 ?.setReorderingAllowed(true)
                 ?.replace(R.id.fcv, DetailFragment::class.java, datos,"detail")
                 ?.addToBackStack("")
                 ?.commit()
         }
     }

     override fun getItemCount(): Int {
         return myTaskTitle.size
     }
}