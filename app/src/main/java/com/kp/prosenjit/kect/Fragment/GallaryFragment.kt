package com.kp.prosenjit.kect.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.kp.prosenjit.kect.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.kp.prosenjit.kect.Activity.Base
import com.kp.prosenjit.kect.Adapter.GridImagesAdapter
import com.kp.prosenjit.kect.NetworkCall.*
import com.wang.avi.AVLoadingIndicatorView
import kotlinx.android.synthetic.main.fragment_gallary.*
import org.json.JSONObject








class GallaryFragment(val mContext: Context) : Fragment(){
    lateinit var rootView: View
    lateinit var avi_progress: AVLoadingIndicatorView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView= inflater.inflate(R.layout.fragment_gallary,container,false)
        avi_progress = rootView.findViewById(R.id.avi_progress) as AVLoadingIndicatorView
        FetchGallery()
        return rootView
    }
   private fun FetchGallery() {
      // Base.aviProgressVisible(avi_progress,"BallPulseIndicator")
       avi_progress.visibility = View.VISIBLE
       avi_progress.show()
       val parameter=JSONObject()
       NetworkUrlRequest(WebUrls.GALLARY_URL,parameter,Base.METHOD_GET,object :CustomResponse.Listener<String>{
           override fun onSuccessResponse(response: String) {
               try {
                   activity!!.runOnUiThread {
                       avi_progress.hide()
                   }
                   val jsonObject = JSONObject(response)
                   val error = jsonObject.getString(Base.ERROR_NAME)
                   try {
                       val message = jsonObject.getString(Base.RESULT_MSG)
                   } catch (e: Exception) {
                   }

                   if (error.equals(Base.ERROR_SUSSESS)) {
                       var imagesList: ArrayList<String> = ArrayList()
                       val jsonarray = jsonObject.getJSONArray("GALLERY_DETAILS")
                       Log.e("success Response**", "GALLERY==>" + jsonarray)
                       for (i in 0 until jsonarray.length()) {
                           val galleryarray = jsonarray.getJSONObject(i).getJSONArray("gallery_images")
                           if (galleryarray.length() > 0) {
                               for (j in 0 until galleryarray.length()) {
                                   imagesList.add(WebUrls.GET_IMAGE_PATH + galleryarray.get(j))
                               }
                           }
                       }
                       Log.e("Imgae Arry**", "Size++>" + imagesList.size + "" + imagesList)
                       activity!!.runOnUiThread {
                           rv_gallery.visibility = View.VISIBLE
                           val mAdapter = GridImagesAdapter(mContext, imagesList)
                           rv_gallery.layoutManager = GridLayoutManager(mContext, 3)
                           rv_gallery.adapter = mAdapter

                       }

                   } else {
                       activity!!.runOnUiThread {
                           avi_progress.hide()
                       }
                   }
               }catch (e:Exception){
                   activity!!.runOnUiThread {
                       avi_progress.hide()
                   }
               }
           }

           override fun onError(response: String) {
               activity!!.runOnUiThread {
                   avi_progress.hide()
               }
           }
       })

    }


}