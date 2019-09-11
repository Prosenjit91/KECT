package com.kp.prosenjit.kect.ButtomSheets

import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kp.prosenjit.kect.Adapter.AppListAdapter
import com.kp.prosenjit.kect.R
import kotlinx.android.synthetic.main.apps_bottomsheet.*

class AppsBottomSheet(val mContext:Context,val appsList: List<ResolveInfo>, val intent: Intent):
    BottomSheetDialogFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.apps_bottomsheet, container, false)
        isCancelable = true
        //Log.e("ListSize**","==>"+appsList.size)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_heading.text="UPI APPS"
        val manager = LinearLayoutManager(mContext)
        recycler_view.setLayoutManager(manager)
        val mAdapter = AppListAdapter(mContext, appsList, intent)
        recycler_view.setAdapter(mAdapter)
        cancelButton.setOnClickListener {
            dismiss()
        }
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

}
