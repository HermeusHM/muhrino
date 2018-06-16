package a248.kotlinoid.view.dialogs

import a248.kotlinoid.R
import a248.kotlinoid.model.SensorEntity
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AlertDialog
import android.widget.EditText
import kotlinx.android.synthetic.main.dialog_set_domain.view.*


fun FragmentActivity.buildSetDomainDialog(ctx: Context, onCreated: (String) -> Unit) {
    val builder = AlertDialog.Builder(this)
    val inflater = this.layoutInflater
    val dialogView = inflater.inflate(R.layout.dialog_set_domain, null)
    builder.setView(dialogView)

    var etTitle: EditText? = null

    with (dialogView) {
        etTitle = findViewById(R.id.dialog_et_domain) as EditText
    }

    builder.setTitle(ctx.getString(R.string.set_domain))
    builder.setPositiveButton("Ок") {dialog, which ->
        run {
            val domain  = etTitle?.text?.toString() ?: "Нет названия"
            onCreated(domain)
        }
    }
    builder.setNegativeButton("Отмена", {dialog, which ->  })
    builder.create().show()
}