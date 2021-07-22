package com.example.carzilla

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.carzilla.New.newwork.gsonclasses.ShowAllOrders
import com.example.carzilla.New.newwork.helper.GenricAdapter
import com.example.carzilla.New.newwork.helper.ViewHolder
import com.example.carzilla.databinding.ActivityMainBinding
import com.httpconnection.httpconnectionV2.Http
import com.httpconnection.httpconnectionV2.interfaces.IGetResponse
import com.httpconnection.httpconnectionV2.models.Exception

class MainActivity : AppCompatActivity() {


    lateinit var appBarConfiguration:AppBarConfiguration
    lateinit var binding: ActivityMainBinding
    lateinit var navController:NavController


    companion object{

        lateinit var drawerLayout:DrawerLayout

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        drawerLayout = findViewById(R.id.drawer_layout)
        setSupportActionBar(binding.contentMain.toolbar)
        // menu should be considered as top level destinations.
//        appBarConfiguration = AppBarConfiguration(setOf(
//            R.id.loginFragment,R.id.registerFragment,R.id.phoneVerificationFragment,
//            R.id.menu_setting, R.id.menu_reminder, R.id.menu_income_due,R.id.menu_subscription), drawerLayout)
//
       // navController = findNavController(R.id.fragment)


//        setupActionBarWithNavController(navController, appBarConfiguration)
      //  binding.navView.setupWithNavController(navController)




        binding.navView.setNavigationItemSelectedListener {item ->
         when(item.itemId){

             R.id.menu_subscription -> {

                 navController.navigate(R.id.menu_subscription)
                 binding.contentMain.toolbar.setTitle("BUY SUBSCRIPTION")
                 binding.drawerLayout.closeDrawers()

                 false
             }


             else -> false
         }


        }


        navController.addOnDestinationChangedListener(object:NavController.OnDestinationChangedListener{
            override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {


                when(destination.id){

                    R.id.menu_subscription -> {

                        showBar(true)

                        binding.contentMain.toolbar.setNavigationIcon(R.drawable.back_white)


                    }



                else -> showBar(false)



                }


            }

        })


    }

    private fun showBar(visible: Boolean):Any {
        if (visible){
        binding.contentMain.toolbar.visibility = View.VISIBLE
            }else {
            binding.contentMain.toolbar.visibility = View.GONE

        }
            return ""
    }


    override fun onSupportNavigateUp(): Boolean {
        //val navController = findNavController(R.id.fragment)

        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}