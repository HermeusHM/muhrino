package a248.kotlinoid.view.activities

import a248.kotlinoid.R
import a248.kotlinoid.view.fragments.MainFragment
import a248.kotlinoid.view.SupportLifecycleActivity
import a248.kotlinoid.view.dialogs.buildSimpleDialog
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : SupportLifecycleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(this.toolbar)

        val fragmentInstance = MainFragment.newInstance()
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragmentInstance).commit()

        val fab = this.floatingActionButton
        fab.setOnClickListener { fragmentInstance.loadSensors() }
    }

}
