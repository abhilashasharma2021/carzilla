package com.example.carzilla.New.ui

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.FrameLayout
import com.example.carzilla.databinding.ShowWebviewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class WebViewSheet: BottomSheetDialogFragment(), View.OnClickListener {
   lateinit var binding :ShowWebviewBinding
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
      inflater:LayoutInflater,
      container:ViewGroup?,
      savedInstanceState:Bundle?
   ):View {
      binding = ShowWebviewBinding.inflate (layoutInflater)

      return binding.root
   }

   override fun onViewCreated(view:View, savedInstanceState:Bundle?) {
      binding.webView.settings.javaScriptEnabled = true
      binding.webView.settings.pluginState = WebSettings.PluginState.ON
      binding.webView.loadUrl(this.requireArguments().getString("link") as String)
      binding.imageViewBack.setOnClickListener { dismiss() }
      binding.textViewTitle.text = this.requireArguments().getString("title") as String

   }

   override fun onClick(v:View?) {
      when (v) {
      }
   }
}