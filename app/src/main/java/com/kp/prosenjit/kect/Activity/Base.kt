package com.kp.prosenjit.kect.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.kp.prosenjit.kect.Registrarion.SiginInActivity
import com.shashank.sony.fancygifdialoglib.FancyGifDialog
import com.wang.avi.AVLoadingIndicatorView
import kotlinx.android.synthetic.main.layout_sign_in.*


open class Base :AppCompatActivity(){


    fun hideKeyboard(){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
    fun ChangeStausBarColour(activity: Activity, color: Int){
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = ContextCompat.getColor(activity, color)
        }
    }
    fun ShowGifOnlyOkFinish(activity: Activity,title: String, message: String, gifImage: Int) {
        runOnUiThread {
            FancyGifDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveBtnBackground("#FF4081")
                .setPositiveBtnText("Ok")
                .setNegativeBtnBackground("#FFA9A7A8")
                .setGifResource(gifImage)   //Pass your Gif here
                .isCancellable(true)
                .OnPositiveClicked {
                    // LAUNCH activity after certain time period

                }

                .build()
        }

    }
    fun ShowGifOnlyButtonWithForwardPage(
        activity: Activity,
        title: String,
        message: String,
        gifImage: Int,
        buttonname: String,
        clssName: Class<SiginInActivity>) {
        runOnUiThread {
            FancyGifDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveBtnBackground("#FF4081")
                .setPositiveBtnText(buttonname)
                .setNegativeBtnBackground("#FFA9A7A8")
                .setGifResource(gifImage)   //Pass your Gif here
                .isCancellable(false)
                .OnPositiveClicked {
                    // LAUNCH activity after certain time period
                    val  intent= Intent(activity, clssName).apply {  }
                    startActivity(intent)
                    finish()
                }

                .build()
        }

    }
    fun errorShake(target: View){
        YoYo.with(Techniques.Shake)
            .duration(3000)
            .playOn(target)
    }
    companion object {
        val PAYMENT_REQUEST_CODE = 400
        val ERROR_NAME="ERROR"
        val ERROR_SUSSESS="0"
        val ERROR_FAILED="1"
        val RESULT_MSG="MESSAGE"
        val shared_user_name="user_name"
        val shared_user_id="user_id"
        val shared_user_mobile="user_mobile"
        val shared_user_address="user_address"
        val shared_user_created_on="user_created_on"
        val METHOD_GET="1"
        val METHOD_POST="2"
        fun aviProgressVisible(avLoadingIndicatorView: AVLoadingIndicatorView, indicator_name: String) {
            if(avLoadingIndicatorView.visibility==View.GONE) {
                avLoadingIndicatorView.visibility = View.VISIBLE
                avLoadingIndicatorView.setIndicator(indicator_name)
            }
        }

        fun aviProgressInVisible(avLoadingIndicatorView: AVLoadingIndicatorView) {
            if(avLoadingIndicatorView.visibility==View.VISIBLE) {
                avLoadingIndicatorView.visibility = View.GONE
            }
        }
    }
}