package kamil.com.monitorowaniezdrowia

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.support.v4.app.FragmentTransaction
import android.util.Log
import kamil.com.monitorowaniezdrowia.Fragment.fit
import kamil.com.monitorowaniezdrowia.Fragment.kuchnia
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.app_bar_main2.*
import kotlinx.android.synthetic.main.content_main2.*



class Main2Activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    fit.OnFragmentInteractionListener,
    kuchnia.OnFragmentInteractionListener {

    lateinit var fitFragment:fit
    lateinit var kuchniaFragment:kuchnia

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setSupportActionBar(toolbar)


        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        fitFragment  = fit.newInstance()
        kuchniaFragment = kuchnia.newInstance()

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.anim.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation_main view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.wyloguj -> {
                val pref = getSharedPreferences("loginData", Context.MODE_PRIVATE)
                val editor = pref.edit()
                editor.clear()
                editor.apply()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.fit -> {
                toolbar.title="Fit"
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fitFragment)
                    .addToBackStack(fitFragment.toString())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.kuchnia -> {
                toolbar.title="Kuchnia"
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, kuchniaFragment)
                    .addToBackStack(kuchniaFragment.toString())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.ustawienia -> {
                toolbar.title="dziala3"
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onFragmentInteraction(uri: Uri) {
        Log.d("Monitorowanie zdrowia", "OnFragmentInteraction")
    }


}
