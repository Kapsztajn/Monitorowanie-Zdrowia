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
import kamil.com.monitorowaniezdrowia.Fragment.ustawienia
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.app_bar_main2.*
import kotlinx.android.synthetic.main.content_main2.*
import android.widget.Toast
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.Sensor
import kotlinx.android.synthetic.main.fragment_fit.*


class Main2Activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    fit.OnFragmentInteractionListener,
    kuchnia.OnFragmentInteractionListener,
    ustawienia.OnFragmentInteractionListener,
    SensorEventListener{

    lateinit var fitFragment:fit
    lateinit var kuchniaFragment:kuchnia
    lateinit var ustawieniaFragment:ustawienia
    var activityRunning = false
    var mSensorManager : SensorManager?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setSupportActionBar(toolbar)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager



        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        fitFragment  = fit.newInstance()
        kuchniaFragment = kuchnia.newInstance()
        ustawieniaFragment = ustawienia.newInstance()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, fitFragment)
            .addToBackStack(fitFragment.toString())
            .commit()
    }

    override fun onResume() {
        super.onResume()
        activityRunning = true
        var stepsSensor = mSensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepsSensor == null) {
            Toast.makeText(this, "No Step Counter Sensor !", Toast.LENGTH_SHORT).show()
        } else {
            mSensorManager?.registerListener(this, stepsSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        activityRunning = false
        mSensorManager?.unregisterListener(this)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (activityRunning) {
            extraTextView.setText("" + event.values[0])
        }
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

    var ekran = 1

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.fit -> {
                toolbar.title="Fit"
                supportFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(R.id.fragment_container, fitFragment)
                    .commit()
                ekran = 1
                return@OnNavigationItemSelectedListener true
            }
            R.id.kuchnia -> {
                toolbar.title="Kuchnia"
                if (ekran == 1) {
                    supportFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                        .replace(R.id.fragment_container, kuchniaFragment)
                        .commit()
                }
                if (ekran == 3) {
                    supportFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.fragment_container, kuchniaFragment)
                        .commit()

                }
                 ekran = 2
                return@OnNavigationItemSelectedListener true
            }
            R.id.ustawienia -> {
                toolbar.title="Ustawienia"
                supportFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                    .replace(R.id.fragment_container, ustawieniaFragment)
                    .commit()
                ekran = 3
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onFragmentInteraction(uri: Uri) {
        Log.d("Monitorowanie zdrowia", "OnFragmentInteraction")
    }


}
