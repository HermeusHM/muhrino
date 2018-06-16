package a248.kotlinoid.view.fragments

import a248.kotlinoid.R
import a248.kotlinoid.databinding.FragmentSensorBinding
import a248.kotlinoid.model.ValuesEntity
import a248.kotlinoid.view.SupportLifecycleFragment
import a248.kotlinoid.view.adapters.ValuesRecyclerAdapter
import a248.kotlinoid.view.adapters.VerticalSpaceSensorDecorator
import a248.kotlinoid.view.dialogs.buildDeleteDialog
import a248.kotlinoid.viewmodel.SensorViewModel
import a248.kotlinoid.viewmodel.ValuesViewModel
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_sensor.*

class SensorFragment : SupportLifecycleFragment() {

    lateinit var rv: RecyclerView
    lateinit var adapter: ValuesRecyclerAdapter
    lateinit var binding: FragmentSensorBinding
    lateinit var sensorViewModel: SensorViewModel
    lateinit var valuesViewModel: ValuesViewModel

    companion object {
        private const val ARG_SENSOR_UUID = "arg_sensor_uuid"
        fun newInstance(uuid: Int, servId: Int): SensorFragment {
            val instance = SensorFragment()
            val args = Bundle()
            args.putInt(ARG_SENSOR_UUID, uuid)
            instance.arguments = args
            return instance
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sensor, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sensorViewModel = ViewModelProviders.of(this).get(SensorViewModel::class.java)
        sensorViewModel.init(activity)
        sensorViewModel.getSensor(arguments.getInt(ARG_SENSOR_UUID)).subscribe {
            t ->
            binding.sensor = t
            adapter.sensor = t
        }
        valuesViewModel = ViewModelProviders.of(this).get(ValuesViewModel::class.java)
        valuesViewModel.init(activity, arguments.getInt(ARG_SENSOR_UUID))
        valuesViewModel.values?.observe(this, Observer<List<ValuesEntity>> {
            adapter.values = it ?: listOf()
            adapter.notifyDataSetChanged()
        })
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv = this.values_rv
        rv.layoutManager = LinearLayoutManager(activity)
        adapter = ValuesRecyclerAdapter(listOf(), null, { deleteValue(it) })
        rv.adapter = adapter
        rv.addItemDecoration(VerticalSpaceSensorDecorator(1))
    }

    fun insertValue(valuesEntity: ValuesEntity) {
        valuesViewModel.addValues(valuesEntity)
    }

    fun deleteValue(valuesEntity: ValuesEntity) {
        activity.buildDeleteDialog(this.context) {
            valuesViewModel.deleteValues(valuesEntity)
        }
    }
}