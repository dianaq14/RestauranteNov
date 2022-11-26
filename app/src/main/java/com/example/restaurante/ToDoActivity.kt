package com.example.restaurante

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.navigateUp

class ToDoActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)
        /*if(savedInstanceState== null)
        {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fcv,ToDoFragment::class.java, null,"todo")
                .commit()
        }*/
        setSupportActionBar(findViewById(R.id.my_toolbar))
        val fab : View = findViewById(R.id.fab_home)
        fab.setOnClickListener{ view ->}

        val drawerLayout : DrawerLayout = findViewById(R.id.drawer_layout)
        val navView : NavigationView = findViewById(R.id.nav_view)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_myprofile,R.id.nav_menu,R.id.nav_Reservation, R.id.nav_delivery),drawerLayout)
        setupActionBarWithNavController(navController,appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp(appBarConfiguration) ||super.onSupportNavigateUp()

    }
}