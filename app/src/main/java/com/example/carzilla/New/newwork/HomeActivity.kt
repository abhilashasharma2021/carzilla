package com.example.carzilla.New.newwork

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.carzilla.New.HomeFragment
import com.example.carzilla.New.NewSplashActivity
import com.example.carzilla.New.newwork.childhelp.FabSheet
import com.example.carzilla.New.newwork.fragments.EditProfileFragment
import com.example.carzilla.New.newwork.fragments.RepairOrderFragment
import com.example.carzilla.New.newwork.fragments.ShowOrderFragment
import com.example.carzilla.New.newwork.gsonclasses.ShowOrderCounts
import com.example.carzilla.New.newwork.helper.Connection
import com.example.carzilla.New.newwork.helper.Connection.CREATED_ORDER_COUNT
import com.example.carzilla.New.other.AppsContants
import com.example.carzilla.New.other.Keys
import com.example.carzilla.R
import com.example.carzilla.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.httpconnection.httpconnectionV2.Http
import com.httpconnection.httpconnectionV2.interfaces.IGetResponse
import com.httpconnection.httpconnectionV2.models.Exception
import com.jaqa.helpers.Craft.getKey
import com.jaqa.helpers.Craft.putKey
import com.jaqa.helpers.Craft.startActivity
import com.trendyol.medusalib.navigator.MultipleStackNavigator
import com.trendyol.medusalib.navigator.Navigator
import com.trendyol.medusalib.navigator.NavigatorConfiguration
import com.trendyol.medusalib.navigator.transaction.NavigatorTransaction

// created on 15/06/21 by monu
class HomeActivity : AppCompatActivity(), View.OnClickListener,
    Navigator.NavigatorListener {
    lateinit var binding: ActivityHomeBinding
    private val rootFragmentProvider: List<() -> Fragment> =
        listOf({ HomeFragment() })
    val multipleStackNavigator: MultipleStackNavigator = MultipleStackNavigator(
        supportFragmentManager,
        R.id.fragmentContainer,
        rootFragmentProvider,
        navigatorListener = this,
        navigatorConfiguration = NavigatorConfiguration(
            0, true, NavigatorTransaction.SHOW_HIDE
        )
    )
    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    binding.drawerLayout.openDrawer(GravityCompat.START)

                    return@OnNavigationItemSelectedListener true
                }
                R.id.Search -> {
                    multipleStackNavigator.switchTab(0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.drawerLayout.setOnClickListener(this)
        multipleStackNavigator.initialize(savedInstanceState)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(
            mOnNavigationItemSelectedListener
        )

        initialiseClicks()
        showCount()
    }

    private fun initialiseClicks() {
        with(binding) {
            fab.setOnClickListener(this@HomeActivity)
            linearRepairOrder.setOnClickListener(this@HomeActivity)
            linearOrder.setOnClickListener(this@HomeActivity)
            linearCreated.setOnClickListener(this@HomeActivity)
            binding.llbottmnav.menu.findItem(R.id.menu_logout)


            binding.llbottmnav.menu.findItem(R.id.menu_logout).setOnMenuItemClickListener {
                takeExit()
                true
            }

            val h = binding.llbottmnav.inflateHeaderView(R.layout.nav_header_main)
            val hg = h.findViewById<ImageView>(R.id.imageViewEdit)
            hg.setOnClickListener {
                launchFragments(EditProfileFragment.newInstance("HomeActivity", ""))
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.fab -> {
                val openFabSheet = FabSheet()
                openFabSheet.show(supportFragmentManager, "fabSheet")
            }
            binding.linearRepairOrder -> {
                launchFragments(RepairOrderFragment.newInstance("HomeActivity", ""))
            }

            binding.linearOrder -> {
                launchFragments(ShowOrderFragment.newInstance("", ""))
            }

            binding.linearCreated -> {
                launchFragments(ShowOrderFragment.newInstance("", ""))
            }
        }
    }

    override fun onBackPressed() {
        Log.e("flag--", "onBackPressed(HomeActivity.kt:97)-->>")
        if (multipleStackNavigator.canGoBack()) {
            multipleStackNavigator.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onTabChanged(tabIndex: Int) {
        when (tabIndex) {
            0 -> binding.bottomNavigationView.selectedItemId = R.id.home
            1 -> binding.bottomNavigationView.selectedItemId = R.id.Search
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        multipleStackNavigator.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    fun showCount() {
        Http.Post(CREATED_ORDER_COUNT)
            .bodyParameter(Connection.showCreatedOrderCount(getKey(Keys.ShopUserId)!!))
            .build().executeString(object : IGetResponse {
                override fun onResponse(response: String?) {
                    if (response?.isNotBlank() == true) {
                        Log.e("flag--", "(HomeActivity.kt:105)-->>$response")
                        val getData =
                            Http().createModelFromClass<ShowOrderCounts>(response)
                        if (getData.result == true) {
                            binding.txtCreatedCount.text = getData.data?.create
                            binding.txtInProgressCount.text = getData.data?.progress
                            binding.txtCompletedCount.text = getData.data?.complete
                        }
                    }
                }

                override fun onError(error: Exception?) {
                    Log.e("flag--", "(HomeActivity.kt:105)-->>$error")
                }
            })
    }

    fun launchFragments(fragment: Fragment) {
        binding.bottomView.isVisible = false
        val param =
            binding.rlContent.layoutParams as ViewGroup.MarginLayoutParams
        param.bottomMargin = 0
        multipleStackNavigator.start(fragment)
        binding.fragmentContainer.isVisible = true
    }

    fun takeExit() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.exit_popup)
        val btn_confirm: CardView? = dialog.findViewById(R.id.btnLogout)
        val btn_cancel: CardView? = dialog.findViewById(R.id.btn_cancel)
        btn_confirm?.setOnClickListener {
            AppsContants.sharedpreferences =
                getSharedPreferences(AppsContants.MyPREFERENCES, MODE_PRIVATE)
            val editor = AppsContants.sharedpreferences.edit()
            editor.putString(AppsContants.ShopID, "")
            editor.putString(AppsContants.ShopUserId, "")
            editor.apply()
            putKey(Keys.ShopUserId, "")

            startActivity<NewSplashActivity>()
            finishAffinity()
        }
        btn_cancel?.setOnClickListener {
            dialog.dismiss()
        }
        //   val draw = BitmapDrawable(resources, blurBitmap)
        val window = dialog.window
        dialog.window?.setBackgroundDrawableResource(R.color.black_overlay)
        //  window?.setBackgroundDrawable(draw)
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        window?.setGravity(Gravity.CENTER_VERTICAL)
        dialog.show()
        /*  AppUtils.screenShot(this@HomeActivity) { bitmap ->
              val blurBitmap: Bitmap? = BlurView(application).blurBackground(bitmap, 15)
              Log.e("HomeActivity", "takeExit: "+blurBitmap);
              Log.e("HomeActivity", "takeExit: "+bitmap);
          }*/
    }


}