package a248.kotlinoid.view.dialogs

import a248.kotlinoid.R
import a248.kotlinoid.model.SensorEntity
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AlertDialog
import android.widget.EditText

fun FragmentActivity.buildSimpleDialog(ctx: Context, onCreated: (SensorEntity) -> Unit) {
    val builder = AlertDialog.Builder(this)
    val inflater = this.layoutInflater
    val dialogView = inflater.inflate(R.layout.dialog_insert, null)
    builder.setView(dialogView)

    var etTitle: EditText? = null
    var etDesc: EditText? = null

    with (dialogView) {
        etTitle = findViewById(R.id.dialog_et_title) as EditText
        etDesc = findViewById(R.id.dialog_et_desc) as EditText
    }

    builder.setTitle(ctx.getString(R.string.new_sensor))
    builder.setPositiveButton("Ок") {dialog, which ->
        run {
            val obj = SensorEntity()      //сменить название сущности
            obj.title = etTitle?.text?.toString() ?: "Нет названия"
            obj.desc = etDesc?.text?.toString() ?: "Нет описания"
            onCreated(obj)
        }
    }
    builder.setNegativeButton("Отмена", {dialog, which ->  })
    builder.create().show()
}