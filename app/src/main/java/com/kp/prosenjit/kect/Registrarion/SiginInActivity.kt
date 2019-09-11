package  com.kp.prosenjit.kect.Registrarion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import com.kp.prosenjit.kect.Activity.Base
import com.kp.prosenjit.kect.Activity.MainActivity
import com.kp.prosenjit.kect.Database.TinyDB
import com.kp.prosenjit.kect.NetworkCall.*
import com.kp.prosenjit.kect.R
import kotlinx.android.synthetic.main.layout_sign_in.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject




class SiginInActivity : Base() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_sign_in)
        ChangeStausBarColour(this@SiginInActivity, R.color.colorwhite)
        gotoSignup.setOnClickListener {
            Log.e("Onclick","True")
            GoToSignUp()
        }
        signin.setOnClickListener {
            Log.e("Onclick","True")
            CheckValidation()
        }
    }

    private fun CheckValidation() {
        if(et_mobile.text.isEmpty()){
            errorShake(ln_mobile)
            et_mobile.setError("Mobile No. Required")
        }else if(et_mobile.text.length!==10){
            errorShake(ln_mobile)
            et_mobile.setError("Mobile No. Not valid")
        }else if(et_password.text.isEmpty()){
            errorShake(ln_password)
            et_password.setError("Password Required")
        }else{
            ExuteLogin()
        }
    }

    private fun ExuteLogin() {
        hideKeyboard()
        aviProgressVisible(avi_progress,"BallPulseIndicator")
        val parameter = JSONObject()
        try {
            parameter.put("mobile",et_mobile.text.toString().trim())
            parameter.put("password",et_password.text.toString().trim())
        } catch (e: JSONException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        NetworkUrlRequest(WebUrls.SIGNIN_URL,parameter,METHOD_POST,object : CustomResponse.Listener<String>{
            override fun onSuccessResponse(response: String) {
                signin.isEnabled=true
                runOnUiThread {
                    aviProgressInVisible(avi_progress)
                }
                try {
                    val signin_obj = JSONObject(response)
                    Log.e("signin_obj",""+signin_obj)
                    val error=signin_obj.getString(ERROR_NAME)
                    val message=signin_obj.getString(RESULT_MSG)
                    if(error.equals(ERROR_SUSSESS)){
                        Snackbar.make(et_mobile,message,Snackbar.LENGTH_SHORT).show()
                        val jsonarray=signin_obj.getJSONArray("USER_DETAILS")
                        StoreShared(jsonarray);
                        val  intent= Intent(this@SiginInActivity, MainActivity::class.java).apply {  }
                        startActivity(intent)
                        finish()
                    }else{
                        Log.e("signin_obj","Else")
                        ShowGifOnlyOkFinish(this@SiginInActivity,"Sign In Failed",message,R.drawable.info_x)
                    }
                }catch (e:Exception){
                    Log.e("Exception##",""+e)
                }

            }

            override fun onError(response: String) {
                signin.isEnabled=true
                runOnUiThread {
                    aviProgressInVisible(avi_progress)
                }
                ShowGifOnlyOkFinish(this@SiginInActivity,"Sign In Failed",response,R.drawable.info_x)
            }
        })
    }

    private fun StoreShared(jsonarray: JSONArray) {
        val tinyDb=TinyDB(this@SiginInActivity)
        for (i in 0..jsonarray.length()-1){
            tinyDb.putString(shared_user_name,jsonarray.getJSONObject(i).getString(shared_user_name))
            tinyDb.putString(shared_user_id,jsonarray.getJSONObject(i).getString(shared_user_id))
            tinyDb.putString(shared_user_mobile,jsonarray.getJSONObject(i).getString(shared_user_mobile))
            tinyDb.putString(shared_user_address,jsonarray.getJSONObject(i).getString(shared_user_address))
            tinyDb.putString(shared_user_created_on,jsonarray.getJSONObject(i).getString(shared_user_created_on))
        }

    }




    fun GoToSignUp(){
        val  intent= Intent(this@SiginInActivity, SignUpActivity::class.java).apply {  }
        startActivity(intent)
    }
}