package kamil.com.monitorowaniezdrowia

import android.content.Context
import android.os.Bundle
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.user_main_login.*
import kotlinx.android.synthetic.main.user_login.*
import kotlinx.android.synthetic.main.user_register.*
import android.view.animation.TranslateAnimation


class Login_Activity : AppCompatActivity() {

    lateinit var handler: DatabaseHelper
    var secondViewOpened = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_main_login)
        setSupportActionBar(toolbar)

        handler = DatabaseHelper(this)



        button.setOnClickListener {
            if (handler.userpasswordPresent(login_loginscreen.text.toString(),password_loginscreen.text.toString()))
            {
                val pref = getSharedPreferences("loginData", Context.MODE_PRIVATE)
                val editor = pref.edit()
                editor.putString("login", login_loginscreen.text.toString())
                editor.putString("password", password_loginscreen.text.toString())
                editor.putString("email", handler.useremail(login_loginscreen.text.toString()))
                editor.apply()
                val intent = Intent(this, MainScreen_Activity::class.java)
                startActivity(intent)
                finish()
            }
            else
            {
                Toast.makeText(this,"Użytkownik lub hasło nieprawidłowe", Toast.LENGTH_SHORT).show()
                login_loginscreen.text = null
                password_loginscreen.text = null
            }
        }

        register.setOnClickListener{
            loginlayout.slideDown()
            showRegistration()
            secondViewOpened = true
        }



        button2.setOnClickListener {
            if (handler.userPresent(login.text.toString()))
            {
                Toast.makeText(this,"Taki użytkownik już istnieje, zaloguj się", Toast.LENGTH_SHORT).show()
            }
            else {
                showLogin()
                handler.insertUserData(login.text.toString(), email.text.toString(), password.text.toString())
                login.text = null
                email.text = null
                password.text = null
            }
        }

    }

    override fun onBackPressed() {
        if (secondViewOpened==true)
        {
            showLogin()
            secondViewOpened=false
        }
        else
        {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.anim.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showRegistration(){
        loginlayout.slideDown()
        loginlayout.setVisibility(View.GONE)
        registrationlayout.slideUp()
        registrationlayout.setVisibility(View.VISIBLE)
    }

    private fun showLogin(){
        registrationlayout.slideDown()
        registrationlayout.setVisibility(View.GONE)
        loginlayout.slideUp()
        loginlayout.setVisibility(View.VISIBLE)
    }

    fun View.slideUp(duration: Int = 300) {
        visibility = View.VISIBLE
        val animate = TranslateAnimation(0f, 0f, this.height.toFloat(), 0f)
        animate.duration = duration.toLong()
        animate.fillAfter = true
        this.startAnimation(animate)
    }

    fun View.slideDown(duration: Int = 300) {
        visibility = View.VISIBLE
        val animate = TranslateAnimation(0f, 0f, 0f, this.height.toFloat())
        animate.duration = duration.toLong()
        animate.fillAfter = true
        this.startAnimation(animate)
    }

}
