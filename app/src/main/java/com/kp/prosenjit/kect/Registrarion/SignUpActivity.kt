package com.kp.prosenjit.kect.Registrarion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.kp.prosenjit.kect.Activity.Base
import com.kp.prosenjit.kect.NetworkCall.*
import com.kp.prosenjit.kect.R
import kotlinx.android.synthetic.main.layout_sign_up.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class SignUpActivity : Base(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_sign_up)
        ChangeStausBarColour(this@SignUpActivity, R.color.colorwhite)

        gotoSignin.setOnClickListener {
            Log.e("signin Onclick","True")
            GoToSignIn()
        }
        signUp.setOnClickListener {
            Log.e("signin Onclick","True")
            CheckValidation()
        }
    }
    fun GoToSignIn(){
        val  intent= Intent(this@SignUpActivity, SiginInActivity::class.java).apply {  }
        startActivity(intent)
    }

    private fun CheckValidation() {
        if(et_name.text.isEmpty()){
            errorShake(ln_name)
        }else if(et_address.text.isEmpty()){
            errorShake(ln_address)
        }else if(et_mobile.text.isEmpty()){
            errorShake(ln_mobile)
        }else if(et_mobile.text.length!==10){
            errorShake(ln_mobile)
        }else if(et_password.text!!.isEmpty()){
            errorShake(ln_password)
        }else{
            signUp.isEnabled=false
            createUser()
        }
    }

    private fun createUser() {
        hideKeyboard()
        aviProgressVisible(avi_progress,"BallPulseIndicator")
         val parameter = JSONObject()
         try {
             parameter.put("name",et_name.text.toString().trim())
             parameter.put("mobile",et_mobile.text.toString().trim())
             parameter.put("password",et_password.text.toString().trim())
             parameter.put("confirm_password",et_password.text.toString().trim())
             parameter.put("address",et_address.text.toString().trim())
             parameter.put("email","")
         } catch (e: JSONException) {
             // TODO Auto-generated catch block
             e.printStackTrace()
         }
        NetworkUrlRequest(WebUrls.SIGNUP_URL,parameter,METHOD_POST,object : CustomResponse.Listener<String>{
            override fun onSuccessResponse(response: String) {
                try {
                    val signup_obj = JSONObject(response)
                    Log.e("signup_obj",""+signup_obj)
                    val error=signup_obj.getString(ERROR_NAME)
                    Log.e("error",error)
                    val message=signup_obj.getString(RESULT_MSG)
                    if(error.equals(ERROR_SUSSESS)){
                        Log.e("If True","Call")
                        ShowGifOnlyButtonWithForwardPage(this@SignUpActivity,"User Created Successfully",message,R.drawable.info_x,"Sign In",SiginInActivity::class.java)
                    }else{
                        ShowGifOnlyOkFinish(this@SignUpActivity," User Create Failed",message,R.drawable.info_x)
                    }

                }catch (e:Exception){
                    Log.e("excep","Call")
                }

            }

            override fun onError(response: String) {
                signUp.isEnabled=true
                ShowGifOnlyOkFinish(this@SignUpActivity,"Create User Failed",response,R.drawable.info_x)
            }
        })
    }

}