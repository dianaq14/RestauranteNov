package com.example.restaurante

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.restaurante.room_database.ToDo
import com.example.restaurante.room_database.ToDoDAO
import com.example.restaurante.room_database.ToDoDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class NewTaskActivity : AppCompatActivity() {
    //datos de newtaskactivity
    lateinit var editTextTitle : EditText
    lateinit var editTextTime : EditText
    lateinit var editTextPlace : EditText
    lateinit var editTextId: EditText
    lateinit var bnSaveTask: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)

        editTextTitle  = findViewById(R.id.TextTitle)
        editTextTime   = findViewById(R.id.TextTime)
        editTextPlace  = findViewById(R.id.TextPlace)
        editTextId     = findViewById(R.id.Text_IdnewTask)
        bnSaveTask     = findViewById(R.id.bnNewTask_saveTask)

        if (this.intent.getStringExtra("id") != null)
        {
            editTextTitle.setText(this.intent.getStringExtra("tarea"))
            editTextTime.setText(this.intent.getStringExtra("hora"))
            editTextPlace.setText(this.intent.getStringExtra("lugar"))
            editTextId.setText(this.intent.getStringExtra("id"))
            bnSaveTask.setText("Edit task")
        }
    }

    fun onSaveTask(view: View) {
        var title: String = editTextTitle.text.toString()
        var time: String = editTextTime.text.toString()
        var place: String = editTextPlace.text.toString()
        var id : String = editTextId.text.toString()

        val db = ToDoDatabase.getDatabase(this)
        val todoDAO = db.todoDao()
        //firebase
        val dbFirebase = FirebaseFirestore.getInstance()
        //termina firebase

        val task = ToDo(id.toInt(),title,time,place)

        runBlocking {
            launch {
                if(id.equals("0")){
                    var result = todoDAO.insertTask(task)
                    if (result!= 1L)
                    {
                        //empieza crear tarea firebase
                        dbFirebase.collection("ToDo").document(result.toString())
                            .set(
                                hashMapOf(
                                    "title" to title,
                                    "time" to time,
                                    "place" to place
                                )
                            )
                        //termina crear tarea firebase
                        setResult(RESULT_OK)
                        finish()
                    }
                }else{
                    todoDAO.updateTask(task)
                    // empieza editar tarea firebase
                    dbFirebase.collection("ToDo").document(id)
                        .set(
                            hashMapOf(
                                "title" to title,
                                "time" to time,
                                "place" to place
                            )
                        )
                    //termina editar tarea firebase
                       finish()
                   }
            }
        }
        val principal= Intent(this, ToDoActivity::class.java)
        startActivity(principal)
    }
}