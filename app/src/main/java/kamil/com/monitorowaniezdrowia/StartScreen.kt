package kamil.com.monitorowaniezdrowia

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.content.Intent







class StartScreen : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_screen)

        Handler().postDelayed({

            val pref = getSharedPreferences("loginData", Context.MODE_PRIVATE)

            if (pref.getString("login", null)==null && pref.getString("password", null)==null){

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else
            {
                val intent = Intent(this, Main2Activity::class.java)
                startActivity(intent)

            }
            finish()
        }, 1500)
    }
}