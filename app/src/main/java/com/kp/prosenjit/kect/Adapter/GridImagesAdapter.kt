package com.kp.prosenjit.kect.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kp.prosenjit.kect.Activity.ViewFullscreenImage
import com.kp.prosenjit.kect.R


import java.util.ArrayList

class GridImagesAdapter(
    private val mContext: Context,
    private val imageURLs: ArrayList<String>
) : RecyclerView.Adapter<GridImagesAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_gallery, null))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val requestOptions = RequestOptions().placeholder(R.drawable.img_placeholder)
        Glide.with(mContext).load(imageURLs[position])
            .apply(requestOptions)
            .into(holder.image)
        holder.itemView.setOnClickListener {
           val image_url=imageURLs.get(position)
            Log.e("image_url**",image_url)
            val intent = Intent(mContext, ViewFullscreenImage::class.java)
            intent.putExtra("image_url", image_url)
            mContext.startActivity(intent)

        }


    }

    override fun getItemCount(): Int {
        return imageURLs.size ?: 0
    }
    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView

        init {
            image = itemView.findViewById(R.id.imageView) as ImageView
        }
    }
}
