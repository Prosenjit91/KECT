package com.kp.prosenjit.kect.Adapter

import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kp.prosenjit.kect.Activity.Base
import com.kp.prosenjit.kect.Activity.MainActivity
import com.kp.prosenjit.kect.R

class AppListAdapter(
    val mContext: Context?,
    val appsList: List<ResolveInfo>,
    val intent: Intent?
) : RecyclerView.Adapter<AppListAdapter.AppListViewHolder>() {
    override fun onBindViewHolder(holder:AppListViewHolder, position: Int) {
        val info = appsList[position]
        val name = info.loadLabel(mContext!!.packageManager).toString()
        val icon = info.loadIcon(mContext.packageManager)
        holder.bind(name, icon)

        holder.itemView.setOnClickListener {
            val intent = intent
            intent!!.setPackage(info.activityInfo.packageName)
            (mContext as MainActivity).startActivityForResult(intent,Base.PAYMENT_REQUEST_CODE)

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppListViewHolder {
        return AppListViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.item_app_bottom_sheet,
                parent,
                false
            )
        )
    }
    override fun getItemCount(): Int {
        return appsList.size
    }


    //ViewHolder class for RecyclerView
    class AppListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iconView: ImageView
        var nameView: TextView

        init {
            iconView = itemView.findViewById(R.id.appIconView)
            nameView = itemView.findViewById(R.id.appNameView)
        }

        fun bind(name: String, icon: Drawable) {
            nameView.text = name
            iconView.setImageDrawable(icon)
        }
    }
}
