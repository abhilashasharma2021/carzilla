package com.example.carzilla.New.newwork.childhelp

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.carzilla.databinding.BottomManagerBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FabSheet: BottomSheetDialogFragment(), View.OnClickListener {
   lateinit var binding:BottomManagerBinding
   override fun onCreateDialog(savedInstanceState:Bundle?):Dialog {
      val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
      dialog.setOnShowListener {
         val bottomSheet =
            (it as BottomSheetDialog).findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
         val behavior = BottomSheetBehavior.from(bottomSheet !!)
         behavior.state = BottomSheetBehavior.STATE_EXPANDED
         BottomSheetBehavior.from(bottomSheet).peekHeight =
            Resources.getSystem().displayMetrics.heightPixels

         behavior.isDraggable = false
      }

      return dialog
   }

   override fun onCreateView(
      inflater:LayoutInflater, container:ViewGroup?, savedInstanceState:Bundle?
   ):View {
      binding = BottomManagerBinding.inflate(layoutInflater)
      return binding.root
   }

   override fun onViewCreated(view:View, savedInstanceState:Bundle?) {
      binding.imgCross.setOnClickListener(this)
   }

   override fun onClick(v:View?) {
      when (v) {
         binding.imgCross ->{
            dismiss()
         }
      }
   }
}