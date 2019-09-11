package com.kp.prosenjit.kect.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.kp.prosenjit.kect.Database.TinyDB
import com.kp.prosenjit.kect.R
import com.kp.prosenjit.kect.Registrarion.SiginInActivity

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashScreen : Base() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        val handler = Handler()
        handler.postDelayed({
            val tinyDb= TinyDB(this@SplashScreen)
            if(tinyDb.getString(shared_user_id).isEmpty()) {
                val intent = Intent(this@SplashScreen, SiginInActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }, 3000)
    }

}
