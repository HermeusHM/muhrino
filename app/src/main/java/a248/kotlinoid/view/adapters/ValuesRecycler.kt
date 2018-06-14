package a248.kotlinoid.view.adapters

import a248.kotlinoid.R
import a248.kotlinoid.model.SensorEntity
import a248.kotlinoid.model.ValuesEntity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_value.view.*

/**
 * Created by w-p94 on 15.04.2018.
 */
class ValuesRecyclerAdapter(
        var values: List<ValuesEntity>,
        var sensor: SensorEntity?,
        var deleter: (ValuesEntity) -> Unit)
    : RecyclerView.Adapter<ValuesRecyclerAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        fun bind(value: ValuesEntity, sensor: SensorEntity?, deleter: (ValuesEntity) -> Unit) = with(itemView) {
            itemView.value_tv_value.text = value.value.toString()
            itemView.value_tv_measure.text = sensor?.desc ?: ""
            itemView.value_tv_date.text = value.date.toString()
            itemView.value_delete.setOnClickListener { deleter(value) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            = ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_value, parent, false))

    override fun getItemCount(): Int = values.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
            = holder.bind(values[position], sensor, deleter)
}