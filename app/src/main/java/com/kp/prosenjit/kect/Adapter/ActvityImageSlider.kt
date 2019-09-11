package com.kp.prosenjit.kect.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kp.prosenjit.kect.NetworkCall.WebUrls
import com.kp.prosenjit.kect.R
import com.smarteist.autoimageslider.SliderViewAdapter
import org.json.JSONArray

class ActvityImageSlider (val mContext: Context, val jsonArray: JSONArray): SliderViewAdapter<ActvityImageSlider.SliderAdapterVH>() {
    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapterVH {
        return SliderAdapterVH(LayoutInflater.from(parent!!.context).inflate(R.layout.item_activity_image_slider, null))
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH?, position: Int) {
        val requestOptions = RequestOptions().placeholder(R.drawable.img_placeholder)
        Glide.with(mContext).load(WebUrls.GET_IMAGE_PATH+jsonArray.get(position))
            .apply(requestOptions)
            .into(viewHolder!!.img_slide)
    }

    override fun getCount(): Int {
        return jsonArray.length()
    }


    inner class SliderAdapterVH(var itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
        var img_slide: ImageView

        init {
            img_slide = itemView.findViewById(R.id.img_slide)

        }
    }

}