package com.example.restaurante

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurante.room_database.ToDoDatabase
import com.example.restaurante.room_database.ToDoRepository.ToDoRepository
import com.example.restaurante.room_database.viewmodel.ToDoViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ToDoFragment :Fragment(){
    private lateinit var lisRecyclerView: RecyclerView
    private lateinit var myAdapter: RecyclerView.Adapter<MyTaskListAdapter.MyViewHolder>

    var myTaskTitles: ArrayList<String> = ArrayList()
    var myTaskTimes: ArrayList<String> = ArrayList()
    var myTaskPlaces: ArrayList<String> = ArrayList()
    var myTaskIds: ArrayList<String> = ArrayList()

//    firebase
    var info : Bundle = Bundle()
    private lateinit var toDoViewModel : ToDoViewModel
    private lateinit var toDoRepository : ToDoRepository
//    firebase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmento = inflater.inflate(R.layout.fragment_to_do, container, false)

        /*val btn_hacermenu: Button = fragmento.findViewById(R.id.btn_hacermenu)
        val btn_compra: Button = fragmento.findViewById(R.id.btn_compra)
        val btn_cocinar: Button = fragmento.findViewById(R.id.btn_cocinar)
        val btn_enviardomicilio: Button = fragmento.findViewById(R.id.btn_enviardomicilio)

        btn_hacermenu.setOnClickListener(View.OnClickListener {
            val datos = Bundle()
            datos.putString("tarea", "Hacer el menú del restaurante")
            datos.putString("hora", "4:30am")
            datos.putString("lugar", "Abastos")
            activity?.getSupportFragmentManager()?.beginTransaction()
                ?.setReorderingAllowed(true)
                ?.replace(R.id.fcv, DetailFragment::class.java, datos, "detail")
                ?.addToBackStack("")
                ?.commit()
        })
        btn_compra.setOnClickListener(View.OnClickListener {
            val datos = Bundle()
            datos.putString("tarea", "llevar la compra")
            datos.putString("hora", "6:30am")
            datos.putString("lugar", "Loto Flavor Fusion")
            activity?.getSupportFragmentManager()?.beginTransaction()
                ?.setReorderingAllowed(true)
                ?.replace(R.id.fcv, DetailFragment::class.java, datos, "detail")
                ?.addToBackStack("")
                ?.commit()
        })
        btn_cocinar.setOnClickListener(View.OnClickListener {
            val datos = Bundle()
            datos.putString("tarea", "Cocinar")
            datos.putString("hora", "8:30am")
            datos.putString("lugar", "Loto Flavor Fusion")
            activity?.getSupportFragmentManager()?.beginTransaction()
                ?.setReorderingAllowed(true)
                ?.replace(R.id.fcv, DetailFragment::class.java, datos, "detail")
                ?.addToBackStack("")
                ?.commit()
        })
        btn_enviardomicilio.setOnClickListener(View.OnClickListener {
            val datos = Bundle()
            datos.putString("tarea", "Enviar domicilios")
            datos.putString("hora", "12:00 del medio dia")
            datos.putString("lugar", "Loto Flavor Fusion")
            activity?.getSupportFragmentManager()?.beginTransaction()
                ?.setReorderingAllowed(true)
                ?.replace(R.id.fcv, DetailFragment::class.java, datos, "detail")
                ?.addToBackStack("")
                ?.commit()
        })*/
        return fragmento
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*var myTaskTitles: ArrayList<String> = ArrayList()
        var myTaskTimes: ArrayList<String> = ArrayList()
        var myTaskPlaces: ArrayList<String> = ArrayList()

        myTaskTitles.add("Hacer el menu del restaurante")
        myTaskTitles.add("llevar la compra")
        myTaskTitles.add("Cocinar")
        myTaskTitles.add("enviar domicilios")

        myTaskTimes.add("4:00 am")
        myTaskTimes.add("5:30 am")
        myTaskTimes.add("8:30 am")
        myTaskTimes.add("12:00m")

        myTaskPlaces.add("Abastos")
        myTaskPlaces.add("Restaurante")
        myTaskPlaces.add("Restaurante")
        myTaskPlaces.add("Restaurante")

        var info : Bundle = Bundle()
        info.putStringArrayList("titles",myTaskTitles)
        info.putStringArrayList("times",myTaskTimes)
        info.putStringArrayList("places",myTaskPlaces)

        lisRecyclerView = requireView().findViewById(R.id.recyclerList)
        myAdapter = MyTaskListAdapter(activity as AppCompatActivity, info)
        lisRecyclerView.setHasFixedSize(true)
        lisRecyclerView.adapter = myAdapter
        lisRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }*/
        val fab: View = requireActivity().findViewById(R.id.fab_home)
        fab.setOnClickListener { view ->
            val intent = Intent(activity, NewTaskActivity::class.java)
            val recursiveScope = 0
            startActivityForResult(intent, recursiveScope)
        }
        var info: Bundle = Bundle()
        info.putStringArrayList("titles", myTaskTitles)
        info.putStringArrayList("times", myTaskTimes)
        info.putStringArrayList("places", myTaskPlaces)
        info.putStringArrayList("ids", myTaskIds)

        lisRecyclerView = requireView().findViewById(R.id.recyclerList)
        myAdapter = MyTaskListAdapter(activity as AppCompatActivity, info)
        lisRecyclerView.setHasFixedSize(true)
        lisRecyclerView.adapter = myAdapter
        lisRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        updateList()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                updateList()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun updateList() {
        val db = ToDoDatabase.getDatabase(requireActivity())
        val toDODAD = db.todoDao()
/*        runBlocking {
            launch {
                var result = toDODAD.getAllTask()
                var i = 1
                myTaskTitles.clear()
                myTaskTimes.clear()
                myTaskPlaces.clear()
                myTaskIds.clear()
                while (i < result.lastIndex) {
                    myTaskTitles.add(result[i].title.toString())
                    myTaskTimes.add(result[i].time.toString())
                    myTaskPlaces.add(result[i].place.toString())
                    myTaskIds.add(result[i].id.toString())
                    i++
                }
                myAdapter.notifyDataSetChanged()
            }
        }*/
        //aca viene el firebase
        toDoRepository = ToDoRepository(toDODAD)
        toDoViewModel = ToDoViewModel(toDoRepository)

        var result = toDoViewModel.getAllTasks()

        result.invokeOnCompletion {

            var theTask = toDoViewModel.getTheTasks()

                if(theTask!!.size!=0) {

                    var i = 1
                    myTaskTitles.clear()
                    myTaskTimes.clear()
                    myTaskPlaces.clear()
                    myTaskIds.clear()
                    while (i < theTask.lastIndex) {
                        myTaskTitles.add(theTask[i].title.toString())
                        myTaskTimes.add(theTask[i].time.toString())
                        myTaskPlaces.add(theTask[i].place.toString())
                        myTaskIds.add(theTask[i].id.toString())
                        i++
                    }
                    myAdapter.notifyDataSetChanged()
                }
            }
        // aca termina el firebase
        }


    }


