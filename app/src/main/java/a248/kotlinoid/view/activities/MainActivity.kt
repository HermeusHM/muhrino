package a248.kotlinoid.view.activities

import a248.kotlinoid.R
import a248.kotlinoid.view.fragments.MainFragment
import a248.kotlinoid.view.SupportLifecycleActivity
import a248.kotlinoid.view.dialogs.buildSetDomainDialog
import a248.kotlinoid.view.dialogs.buildSimpleDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : SupportLifecycleActivity() {

    companion object {
        val PREFS_DOMAIN_KEY = "PREFS_DOMAIN_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(this.toolbar)

        val fragmentInstance = MainFragment.newInstance()
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragmentInstance).commit()

        val fab = this.fab_main
        fab.setOnClickListener { fragmentInstance.loadSensors() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.set_domain) {
            this.buildSetDomainDialog(this) {
                //android.widget.Toast.makeText(this, it, android.widget.Toast.LENGTH_LONG).show()
                val sharedPref = getPreferences(android.content.Context.MODE_PRIVATE)
                with (sharedPref.edit()) {
                    putString(a248.kotlinoid.view.activities.MainActivity.PREFS_DOMAIN_KEY, it)
                    apply()
                }
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun getDomain(): String {
        val sharedPref = getPreferences(android.content.Context.MODE_PRIVATE)
        return sharedPref.getString(PREFS_DOMAIN_KEY, "http://192.168.43.51:80/")
    }
}
