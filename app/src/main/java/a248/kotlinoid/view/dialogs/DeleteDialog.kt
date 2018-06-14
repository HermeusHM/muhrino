package a248.kotlinoid.view.dialogs

import a248.kotlinoid.R
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AlertDialog

fun FragmentActivity.buildDeleteDialog(ctx: Context, onClickOk: () -> Unit) {

    val builder = AlertDialog.Builder(this)
    val inflater = this.layoutInflater
    val dialogView = inflater.inflate(R.layout.dialog_delete, null)
    builder.setView(dialogView)

    builder.setTitle(ctx.getString(R.string.delete_question))
    builder.setPositiveButton("Ок") {dialog, which ->
        run {
            onClickOk()
        }
    }
    builder.setNegativeButton("Отмена", {dialog, which ->  })
    builder.create().show()

}