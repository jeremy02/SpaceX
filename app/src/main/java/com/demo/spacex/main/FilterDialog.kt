package com.demo.spacex.main

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.demo.spacex.R
import com.demo.spacex.databinding.FilterDialogBinding

class FilterDialog() : DialogFragment() {

    private lateinit var binding : FilterDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // make dialog width match parent
        setStyle(STYLE_NO_TITLE, R.style.FullWidthDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FilterDialogBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        // Set transparent background and no title
        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE);
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init views
        binding.dialogCancelBtn.setOnClickListener{

        }
    }

    interface DialogListener {
        fun onFinishFilterDeviceDialog()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}