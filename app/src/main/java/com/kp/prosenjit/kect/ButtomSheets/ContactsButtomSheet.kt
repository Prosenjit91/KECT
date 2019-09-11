package com.kp.prosenjit.kect.ButtomSheets

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kp.prosenjit.kect.Activity.MainActivity
import com.kp.prosenjit.kect.Adapter.ContactsAdapter
import com.kp.prosenjit.kect.R
import kotlinx.android.synthetic.main.apps_bottomsheet.cancelButton
import kotlinx.android.synthetic.main.apps_bottomsheet.recycler_view
import org.json.JSONArray

class ContactsButtomSheet(
    val mContext: Context,
    val activity: MainActivity,
    val jsonArray: JSONArray
): BottomSheetDialogFragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.buttomsheet_contacts, container, false)
        isCancelable = true
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val manager = LinearLayoutManager(mContext)
        recycler_view.setLayoutManager(manager)
        val mAdapter = ContactsAdapter(mContext,jsonArray)
        recycler_view.setAdapter(mAdapter)
        cancelButton.setOnClickListener {
            dismiss()
        }
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

}