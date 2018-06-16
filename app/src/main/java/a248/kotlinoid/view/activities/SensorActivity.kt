package a248.kotlinoid.view.activities

import a248.kotlinoid.R
import a248.kotlinoid.view.fragments.SensorFragment
import a248.kotlinoid.view.fragments.MainFragment
import a248.kotlinoid.view.SupportLifecycleActivity
import a248.kotlinoid.view.dialogs.buildSimpleDialog
import a248.kotlinoid.view.dialogs.buildValueInsertDialog
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sensor.*

class SensorActivity : SupportLifecycleActivity() {         //сменить итемы

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor)

        setSupportActionBar(this.toolbar)

        val fragmentInstance = SensorFragment.newInstance(
                intent.getIntExtra(MainFragment.EXTRA_SENSOR_UUID, 0),
                intent.getIntExtra(MainFragment.EXTRA_SENSOR_SID, 0)
        )


        val fab = this.floatingActionButton
        fab.setOnClickListener {
            buildValueInsertDialog(this, {
                fragmentInstance.insertValue(it)
            },
                    intent.getIntExtra(MainFragment.EXTRA_SENSOR_UUID, 0),
                    intent.getIntExtra(MainFragment.EXTRA_SENSOR_SID, 0))
        }

        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragmentInstance).commit()
    }

}