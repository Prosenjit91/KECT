package com.kp.prosenjit.kect.Adapter

import com.smarteist.autoimageslider.SliderViewAdapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kp.prosenjit.kect.R
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.fragment_activity.*
import org.json.JSONArray


class ActivitySlideAdpter (val mContext: Context,val jsonArray: JSONArray): SliderViewAdapter<ActivitySlideAdpter.SliderAdapterVH>() {
    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapterVH {
        return SliderAdapterVH(LayoutInflater.from(parent!!.context).inflate(R.layout.item_activity_slider, null))
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH?, position: Int) {
        val galleryarray=jsonArray.getJSONObject(position).getJSONArray("activity_images")
        if(galleryarray.length()>0){
            viewHolder!!.imageSlider.visibility=View.VISIBLE
            val mAdapter = ActvityImageSlider(mContext, galleryarray)
            viewHolder.imageSlider.setSliderAdapter(mAdapter)
        }else{
            viewHolder!!.imageSlider.visibility=View.GONE
        }
        val title=jsonArray.getJSONObject(position).getString("activity_title")
        val description=jsonArray.getJSONObject(position).getString("activity_body")
        viewHolder.tv_ac_title.text=title
        viewHolder.tv_ac_des.text=description
    }

    override fun getCount(): Int {
       return jsonArray.length()
    }


    inner class SliderAdapterVH(var itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
        var tv_ac_title: TextView
        var tv_ac_des: TextView
        lateinit var imageSlider: SliderView
        init {
            tv_ac_title = itemView.findViewById(R.id.tv_ac_title)
            tv_ac_des = itemView.findViewById(R.id.tv_ac_des)
            imageSlider= itemView.findViewById(R.id.imageSlider)
        }
    }

}