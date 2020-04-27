package com.etkin.app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.etkin.app.activites.LoginPage
import com.etkin.app.activites.RegistrationPage
import com.etkin.app.util.SharedPreferencesUtil
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import kotlinx.android.synthetic.main.splash_screen.*

class SplashScreen : AppCompatActivity() {

    private val SPLASH_TIME : Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        splash_screen_btn_login.setOnClickListener {
            toLoginPage()
        }

        splash_screen_btn_register.setOnClickListener {
            toRegistrationPage()
        }

        if(SharedPreferencesUtil.jwtGet(this@SplashScreen) != SharedPreferencesUtil.PREF_VALUE_JWT_DEFAULT) {
            DynamicToast.makeSuccess(this@SplashScreen,"Hesap Doğrulandı. Anasayfaya Yönlendiriliyorsunuz.",Toast.LENGTH_LONG).show()
            toMainActivity()
        }


    }


    private fun toMainActivity() {
        Handler().postDelayed({
            this@SplashScreen.startActivity(Intent(this@SplashScreen,MainActivity::class.java))
            this.finish()
        },SPLASH_TIME)
    }

    private fun toLoginPage() {
        this@SplashScreen.startActivity(Intent(this@SplashScreen,LoginPage::class.java))
        this.finish()
    }

    private fun toRegistrationPage() {
        this@SplashScreen.startActivity(Intent(this@SplashScreen,RegistrationPage::class.java))
        this.finish()
    }
}