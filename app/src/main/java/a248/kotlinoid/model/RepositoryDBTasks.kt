package a248.kotlinoid.model

import android.os.AsyncTask

fun unitResultTask(preExecutor: () -> Unit,
               inBackground: () -> Unit,
               postExecutor: () -> Unit) {
    val task = object: AsyncTask<Void, Void, Unit>() {
        override fun onPreExecute() {
            super.onPreExecute()
            preExecutor()
        }
        override fun doInBackground(vararg params: Void?) {
            inBackground()
        }
        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            postExecutor()
        }
    }
    task.execute()
}
