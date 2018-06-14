package a248.kotlinoid.model

import android.arch.lifecycle.LiveData
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by w-p94 on 15.04.2018.
 */
object ValuesRepository {

    var valuesDao: ValuesDao? = null            // изменить название на подходящее дао, сменить аналогично методы ниже

    fun checkDao(): ValuesDao {
        return valuesDao ?: throw NullPointerException("==Set Dao to SensorRepository")
    }

    fun getValues(sensorId:Int): LiveData<List<ValuesEntity>> = checkDao().getValuesBySensorId(sensorId)


    fun addValues(vararg values: ValuesEntity) {
        val nonNullDao = checkDao()
        unitResultTask({}, {nonNullDao.insertAll(*values)}, {})
    }

    fun deleteValues(value: ValuesEntity) {
        val nonNullDao = checkDao()
        unitResultTask({}, {nonNullDao.delete(value)}, {})
    }

}