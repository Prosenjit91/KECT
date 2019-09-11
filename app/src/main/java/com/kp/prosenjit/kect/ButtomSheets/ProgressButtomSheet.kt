package com.kp.prosenjit.kect.ButtomSheets

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kp.prosenjit.kect.Adapter.ContactsAdapter
import com.kp.prosenjit.kect.R
import kotlinx.android.synthetic.main.buttomsheet_progress.*

class ProgressButtomSheet (val mcontext:Context,val heading:String,val errorString:String):BottomSheetDialogFragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.buttomsheet_progress, container, false)
        isCancelable = true
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_heading.text=heading
        if(!errorString.isEmpty()){
           avi_progress.visibility=View.GONE
           tv_error_text.visibility=View.VISIBLE
        }
        cancelButton.setOnClickListener {
            dismiss()
        }
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }
}