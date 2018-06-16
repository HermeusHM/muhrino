package a248.kotlinoid.view.fragments

import a248.kotlinoid.view.adapters.MainRecyclerAdapter
import a248.kotlinoid.R
import a248.kotlinoid.view.adapters.VerticalSpaceSensorDecorator
import a248.kotlinoid.model.SensorEntity
import a248.kotlinoid.view.SupportLifecycleFragment
import a248.kotlinoid.view.activities.MainActivity
import a248.kotlinoid.view.activities.SensorActivity
import a248.kotlinoid.viewmodel.SensorViewModel
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment: SupportLifecycleFragment(){

    lateinit var rv: RecyclerView
    lateinit var progress: ProgressBar
    lateinit var adapter: MainRecyclerAdapter
    lateinit var viewModel: SensorViewModel

    companion object Factory {
        const val EXTRA_SENSOR_UUID = "extra_uuid"
        const val EXTRA_SENSOR_SID = "extra_sid"
        fun newInstance(): MainFragment {
            val fragment = MainFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? = inflater?.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv = this.fragment_main_rv
        rv.layoutManager = LinearLayoutManager(activity)
        adapter = MainRecyclerAdapter(listOf(), {startSensorActivity(it)}) { deleteSensor(it) }
        rv.adapter = adapter
        rv.addItemDecoration(VerticalSpaceSensorDecorator(1))
        progress = this.fragment_main_progress
        rv.visibility = View.GONE
        progress.visibility  = View.VISIBLE
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SensorViewModel::class.java)
        viewModel.init(activity)
        viewModel.sensors?.observe(this, Observer<List<SensorEntity>> { updateUI() })
    }

    fun updateUI() {
        adapter.sensors = viewModel.sensors?.value ?: listOf()
        adapter.notifyDataSetChanged()
        progress.visibility = View.GONE
        rv.visibility = View.VISIBLE
    }

    fun insertSensor(sensor: SensorEntity) {
        viewModel.addSensor(sensor)
    }

    fun loadSensors() {
        rv.visibility = View.GONE
        progress.visibility  = View.VISIBLE
        val activity = activity as MainActivity
        viewModel.syncWithServer(activity.getDomain())
    }

    fun deleteSensor(sensor: SensorEntity) {
        viewModel.deleteSensors(sensor)
    }

    fun startSensorActivity(sensor: SensorEntity) {
        val intent = Intent(activity, SensorActivity::class.java)
        intent.putExtra(EXTRA_SENSOR_UUID, sensor.uuid)
        intent.putExtra(EXTRA_SENSOR_SID, sensor.serverId)
        activity.startActivity(intent)
    }

}
