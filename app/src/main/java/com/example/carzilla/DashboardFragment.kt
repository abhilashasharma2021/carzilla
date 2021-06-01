package com.example.carzilla

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.carzilla.MainActivity.Companion.drawerLayout
import com.example.carzilla.New.NavigationActivity
import com.example.carzilla.New.NavigationActivity.drawer
import com.example.carzilla.databinding.BottomManagerBinding
import com.example.carzilla.databinding.FragmentDashboardBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {

   lateinit var binding:FragmentDashboardBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentDashboardBinding.inflate(layoutInflater)



        binding.drawerMenu.setOnClickListener {

            drawer.openDrawer(GravityCompat.START)


        }

        binding.dashboardMenuMain.setOnClickListener {

            openBottomManageBar()
        }






        return binding.root
    }

    private fun openBottomManageBar() {

        val bottom = BottomSheetDialog(requireActivity())
        val binding = BottomManagerBinding.inflate(layoutInflater)
        bottom.setContentView(binding.root)

        bottom.show()

    }


}

