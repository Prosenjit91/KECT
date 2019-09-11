package com.kp.prosenjit.kect.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.kp.prosenjit.kect.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.kp.prosenjit.kect.Activity.Base
import com.kp.prosenjit.kect.Adapter.ActivitySlideAdpter
import com.kp.prosenjit.kect.NetworkCall.CustomResponse
import com.kp.prosenjit.kect.NetworkCall.NetworkUrlRequest
import com.kp.prosenjit.kect.NetworkCall.WebUrls
import kotlinx.android.synthetic.main.fragment_activity.*
import org.json.JSONObject


class ActivityFragment(val mContext: Context) : Fragment(){
    lateinit var rootView: View
    lateinit  var slider_card:CardView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView= inflater.inflate(R.layout.fragment_activity,container,false)
        slider_card=rootView.findViewById(R.id.slider_card) as CardView
        fetchActivity()
        return rootView
    }

    private fun fetchActivity() {
        val parameter= JSONObject()
        NetworkUrlRequest(WebUrls.ACTIVITY_URL,parameter, Base.METHOD_GET,object : CustomResponse.Listener<String>{
            override fun onSuccessResponse(response: String) {
             try {
                 val jsonObject = JSONObject(response)
                 val error = jsonObject.getString(Base.ERROR_NAME)
                 try {
                     val message = jsonObject.getString(Base.RESULT_MSG)
                 } catch (e: Exception) {
                 }

                 if (error.equals(Base.ERROR_SUSSESS)) {
                     val jsonarray = jsonObject.getJSONArray("ACTIVITY_DETAILS")
                     Log.e("ACTIVITY_DETAILS**", "Size==>" + jsonarray.length())
                     if(jsonarray.length()>0) {
                         activity!!.runOnUiThread {
                             slider_card.visibility = View.VISIBLE
                             val mAdapter = ActivitySlideAdpter(mContext, jsonarray)
                             imageSlider.setSliderAdapter(mAdapter)
                         }
                     }else{
                         slider_card.visibility = View.GONE
                     }

                 } else {
                     slider_card.visibility = View.GONE
                 }
             }catch (e:Exception){
                 slider_card.visibility = View.GONE
             }
            }

            override fun onError(response: String) {
                slider_card.visibility=View.GONE
            }
        })
    }
}