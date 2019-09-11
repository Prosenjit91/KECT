package com.kp.prosenjit.kect.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kp.prosenjit.kect.Activity.Base
import com.kp.prosenjit.kect.R
import org.json.JSONArray
import android.widget.Toast
import android.content.ActivityNotFoundException
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import android.net.Uri


class ContactsAdapter(val mContext: Context?, val jsonArray: JSONArray): RecyclerView.Adapter<ContactsAdapter.AppListViewHolder>() {
    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(holder:AppListViewHolder, position: Int) {
        val name=jsonArray.getJSONObject(position).getString(Base.shared_user_name)
        val address=jsonArray.getJSONObject(position).getString(Base.shared_user_address)
        val mobile=jsonArray.getJSONObject(position).getString(Base.shared_user_mobile)
        //Log.e("name**",name)
        holder.nameView.text=name
        holder.tv_address.text=address
        holder.Icon_phone.setOnClickListener {
            try {
                val my_callIntent = Intent(Intent.ACTION_DIAL)
                my_callIntent.data = Uri.parse("tel:$mobile")
               mContext!!.startActivity(my_callIntent)
            } catch (e: Exception) {
                Toast.makeText(mContext, "Error in your phone call" + e.message, Toast.LENGTH_LONG)
                    .show()
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppListViewHolder {
        return AppListViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.item_contacts_buttomsheet,
                parent,
                false
            )
        )
    }
    override fun getItemCount(): Int {
        return jsonArray.length()
    }


    //ViewHolder class for RecyclerView
    class AppListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameView: TextView
        var tv_address:TextView
        var  Icon_phone:ImageView
        init {
            nameView = itemView.findViewById(R.id.appNameView)
            tv_address= itemView.findViewById(R.id.tv_address)
            Icon_phone= itemView.findViewById(R.id.Icon_phone)
        }

    }
}