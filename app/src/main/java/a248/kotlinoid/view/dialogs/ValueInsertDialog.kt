package a248.kotlinoid.view.dialogs

import a248.kotlinoid.R
import a248.kotlinoid.model.ValuesEntity
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import android.view.View
import java.util.*


fun FragmentActivity.buildValueInsertDialog(ctx: Context, onCreated: (ValuesEntity) -> Unit, sensorId: Int, servId: Int) {
    val builder = AlertDialog.Builder(this)
    val inflater = this.layoutInflater
    val dialogView = inflater.inflate(R.layout.dialog_value_insert, null)
    builder.setView(dialogView)

    var etValue: EditText? = null
//    var etDatetime: EditText? = null
    var datePicker: DatePicker? = null
    var timePicker: TimePicker? = null
    var datePickerButton: Button? = null
    var timePickerButton: Button? = null

    with (dialogView) {
        etValue = findViewById(R.id.dialog_et_value) as EditText
        datePicker = findViewById(R.id.date_picker) as DatePicker
        timePicker = findViewById(R.id.time_picker) as TimePicker
        datePickerButton = findViewById(R.id.date_picker_button) as Button
        timePickerButton = findViewById(R.id.time_picker_button) as Button
//        etDatetime = findViewById(R.id.dialog_et_datetime) as EditText
    }

    timePicker?.setIs24HourView(true)

    datePickerButton?.setOnClickListener({
        datePicker?.visibility = View.VISIBLE
        timePicker?.visibility = View.GONE
        datePickerButton?.visibility = View.GONE
        timePickerButton?.visibility = View.VISIBLE
    })

    timePickerButton?.setOnClickListener({
        datePicker?.visibility = View.GONE
        timePicker?.visibility = View.VISIBLE
        timePickerButton?.visibility = View.GONE
        datePickerButton?.visibility = View.VISIBLE
    })

    builder.setTitle(ctx.getString(R.string.insert_value))
    builder.setPositiveButton("Ок") {dialog, which ->
        run {
            val obj = ValuesEntity()
            obj.sensorId = sensorId
            obj.serverSensorId = servId
            if (etValue?.text?.toString()?.isEmpty() ?: true) {
                etValue?.setError("Поле не должно быть пустым")
            } else {
                obj.value = etValue?.text?.toString()?.toFloat() ?: 0.0f
            }
            val c = Calendar.getInstance()
            c.set(datePicker!!.year, datePicker!!.month, datePicker!!.dayOfMonth,
                    timePicker!!.currentHour, timePicker!!.currentMinute)
            val currentDateTime = Date(c.timeInMillis)
            obj.date = currentDateTime
//            Log.d("===============1", etDatetime?.text?.toString())
            onCreated(obj)
        }
    }
    builder.setNegativeButton("Отмена", {dialog, which ->  })
    builder.create().show()
}