package a248.kotlinoid.view.adapters

import a248.kotlinoid.R
import a248.kotlinoid.databinding.RecyclerSensorBinding
import a248.kotlinoid.model.SensorEntity
import android.graphics.Rect
import android.hardware.Sensor
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recycler_sensor.view.*

class MainRecyclerAdapter(
        var sensors: List<SensorEntity>,        // название сущности
        val onClick: (SensorEntity) -> Unit,
        val deleter: (SensorEntity) -> Unit) : RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>() {

    class ViewHolder(val binding: RecyclerSensorBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(sensor: SensorEntity, onClick: (SensorEntity) -> Unit, deleter: (SensorEntity) -> Unit) {
            binding.sensor = sensor
//            binding.sensorDelete.setOnClickListener{ deleter(sensor) }
            binding.root.setOnClickListener{ onClick(sensor) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
            = holder.bind(sensors[position], onClick, deleter)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            = ViewHolder(RecyclerSensorBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = sensors.size
}

class VerticalSpaceSensorDecorator(val height: Int): RecyclerView.ItemDecoration() {        // не меняет
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        outRect.bottom = height
    }
}