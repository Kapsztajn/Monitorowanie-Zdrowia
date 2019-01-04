package kamil.com.monitorowaniezdrowia

import android.content.AsyncQueryHandler
import android.os.Bundle
import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.KeyEvent
import android.view.View
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.user_register.*

class MainActivity : AppCompatActivity() {

    lateinit var handler: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        handler = DatabaseHelper(this)

        button.setOnClickListener {
            if (handler.userPresent(login_loginscreen.text.toString(),password_loginscreen.text.toString()))
            {val intent = Intent(this, Main3Activity::class.java)
            startActivity(intent)}
            else
            {
                Toast.makeText(this,"Użytkownik lub hasło nieprawidłowe", Toast.LENGTH_SHORT).show()
                login_loginscreen.text = null
                password_loginscreen.text = null
            }
        }

        register.setOnClickListener{
            showRegistration()
        }

        button2.setOnClickListener {
            showLogin()
            handler.insertUserData(login.text.toString(),email.text.toString(),password.text.toString())
            login.text = null
            email.text = null
            password.text = null
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
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showRegistration(){
        registrationlayout.visibility= View.VISIBLE
        loginlayout.visibility=View.GONE
    }

    private fun showLogin(){
        registrationlayout.visibility= View.GONE
        loginlayout.visibility=View.VISIBLE

    }

}
