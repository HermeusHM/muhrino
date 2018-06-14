package a248.kotlinoid.viewmodel

import a248.kotlinoid.model.ValuesEntity
import a248.kotlinoid.model.ValuesRepository
import a248.kotlinoid.model.getDb
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import io.reactivex.Flowable

/**
 * Created by w-p94 on 15.04.2018.
 */
class ValuesViewModel : ViewModel() {

    var values: LiveData<List<ValuesEntity>>? = null
    private var valuesRepository = ValuesRepository

    fun init(context: Context, sensorId: Int) {
        if (valuesRepository.valuesDao == null) {
            valuesRepository.valuesDao = context.getDb().valuesDao()
        }
        if (values == null) {
            values = valuesRepository.getValues(sensorId)
        }
    }


    fun addValues(vararg values: ValuesEntity) {
        valuesRepository.addValues(*values)
    }

    fun deleteValues(value: ValuesEntity) {
        valuesRepository.deleteValues(value)
    }

}