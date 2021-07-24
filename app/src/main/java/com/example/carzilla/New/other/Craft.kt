package com.jaqa.helpers

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder


object Craft {
    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private const val MY_PREF = "MY_PREF"

/*    inline fun Context.toast(message: String, layout: View) {
        layout.textViewMessage.text = message

        val myToast = Toast(this)
        myToast.duration = Toast.LENGTH_SHORT
        //myToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        myToast.setGravity(Gravity.BOTTOM, 0, 0);
        myToast.view = layout
        myToast.show()
    }*/

    inline fun <reified T : Activity> Activity.startActivity() {
        startActivity(createIntent<T>())
    }

    inline fun <reified T : Activity> Context.createIntent() =
        Intent(this, T::class.java)


    fun Context.putKey(Key: String?, Value: String?) {
        sharedPreferences =
            getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        editor = sharedPreferences?.edit()
        editor?.putString(Key, Value)
        editor?.apply()
    }

    fun Context.getKey(Key: String?): String? {
        sharedPreferences =
            getSharedPreferences(MY_PREF, Context.MODE_PRIVATE)
        return sharedPreferences?.getString(Key, "")
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun Context.isOnline(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false
    }


    fun Context.confirmationDialog(
        title: String,
        clickPosition: Int = 0,
        items: ArrayList<String>,
        id: ArrayList<String>,
        proceed: (position: Int, value: String, id: String) -> Unit
    ) {
        lateinit var itemaNamesArray: Array<String>
        lateinit var itemaIdArray: Array<String>

        if (items.size != 0) {
            itemaNamesArray = items.toTypedArray()
            itemaIdArray = id.toTypedArray()
            MaterialAlertDialogBuilder(this)
                .setTitle(title)
                .setNegativeButton("Cancel") { dialog, which ->
                    // Respond to neutral button press
                }
                .setPositiveButton("Okay") { dialog, which ->
                    // Respond to positive button press
                    if (itemaNamesArray.isNotEmpty()) {
                        val position =
                            (dialog as androidx.appcompat.app.AlertDialog).listView.checkedItemPosition
                        proceed.invoke(position, itemaNamesArray[position], itemaIdArray[position])
                    }


                }
                // Single-choice items (initialized with checked item)
                .setSingleChoiceItems(itemaNamesArray, clickPosition) { dialog, which ->
                    // Respond to item chosen
                }
                .show()
        } else {
            Toast.makeText(this, "No items found", Toast.LENGTH_SHORT).show()
        }


    }

/*
     fun loadingPopup(action: Boolean,dialog: Dialog) {
        dialog.setContentView(R.layout.loading_popup)
        var imgLoading: ImageView? = null
        var btn_cancel: CardView? = null
        var rlbg: RelativeLayout? = null
        imgLoading = dialog.findViewById(R.id.imgLoading)
        //   val draw = BitmapDrawable(resources, blurBitmap)
        imgLoading.load("https") {
            crossfade(true)
            placeholder(R.drawable.loadin_spinner)
            error(R.drawable.loadin_spinner)
        }
        dialog.setCancelable(false)

        val window = dialog.window
        dialog.window?.setBackgroundDrawableResource(R.color.black_overlay)
        //  window?.setBackgroundDrawable(draw)
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        window?.setGravity(Gravity.CENTER_VERTICAL)
        if (action){
            dialog.show()
        }else {
            dialog.dismiss()
        }


        */
/*  AppUtils.screenShot(this@HomeActivity) { bitmap ->
              val blurBitmap: Bitmap? = BlurView(application).blurBackground(bitmap, 15)
              Log.e("HomeActivity", "takeExit: "+blurBitmap);
              Log.e("HomeActivity", "takeExit: "+bitmap);
          }*//*

    }
*/


    fun View.textView(id: Int): TextView {
        return findViewById(id)

    }

    fun View.editText(id: Int): EditText {
        return findViewById(id)

    }

    fun View.imageView(id: Int): ImageView {
        return findViewById(id)

    }

    fun View.materialCardView(id: Int): MaterialCardView {
        return findViewById(id)

    }

    fun View.linearLayout(id: Int): LinearLayout {
        return findViewById(id)

    }

    fun View.constraintLayout(id: Int): ConstraintLayout {
        return findViewById(id)

    }

    fun View.relativeLayout(id: Int): RelativeLayout {
        return findViewById(id)

    }


    fun EditText.setFieldError(errorMsg: String? = null) {
        return if (errorMsg.isNullOrEmpty()) {
            this.setError("Required")
        } else {
            this.setError(errorMsg)
        }

    }

    private val getValidateList = arrayListOf<Boolean>()
    fun isValidate(
        id: EditText,
        errorMsg: String? = null,
        isMobile: Boolean? = false,
        mobileNumberLength: Int = 10
    ) = apply {
        var isValidatedField = false
        when {
            id.text.isNullOrEmpty() -> {
                id.setFieldError(errorMsg)
            }
            isMobile == true -> {
                isValidatedField = if (id.length() < mobileNumberLength) {
                    id.setFieldError("Please enter valid mobile number")
                    false
                } else id.length() <= mobileNumberLength

            }

            else -> {
                isValidatedField = true
            }
        }
        getValidateList.add(isValidatedField)
        getValidateList.forEach {
            if (!it) isValidatedField = it
        }

    }

    fun getValidatedFields(): Boolean {
        var isValidatedField = true
        getValidateList.forEach {
            if (it) {

            } else {
                isValidatedField = it
            }
        }
        getValidateList.clear()
        return isValidatedField
    }

    fun startAnimation(
        view: View,
        startValue: Float = 0f,
        endValue: Float = 1f,
        animationDuration: Long = 1500,
        animatorListener: (ValueAnimator?) -> Unit
    ) {
        val valueAnimator = ValueAnimator.ofFloat(startValue, endValue)
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            view.scaleX = value
            view.scaleY = value

        }
        valueAnimator.interpolator = BounceInterpolator()
        valueAnimator.duration = animationDuration
        // Set animator listener.
        animatorListener(valueAnimator)
        /*valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {

                // Navigate to main activity on navigation end.
            }

            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {}
        })*/
        // Start animation.
        valueAnimator.start()
    }

    fun crossFadeHideShow(viewToShow: View, viewToHide: View) {
        viewToShow.apply {
            alpha = 0f
            isVisible = true
            animate().alpha(1f).setDuration(500L).setListener(null)
        }
        viewToHide.animate().alpha(0f).setDuration(500)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    viewToHide.isVisible = false
                }
            })
    }

}