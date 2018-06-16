package a248.kotlinoid.viewmodel

import a248.kotlinoid.model.SensorEntity
import a248.kotlinoid.model.SensorRepository
import a248.kotlinoid.model.ValuesRepository
import a248.kotlinoid.model.getDb
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import io.reactivex.Flowable
import org.jetbrains.anko.doAsyncResult

class SensorViewModel : ViewModel() {

    var sensors: LiveData<List<SensorEntity>>? = null
    private var sensorRepository = SensorRepository
    private var valuesRepository = ValuesRepository

    fun init(context: Context) {
        if (sensorRepository.sensorDao == null) {
            sensorRepository.sensorDao = context.getDb().sensorDao()
        }
        if (valuesRepository.valuesDao == null) {
            valuesRepository.valuesDao = context.getDb().valuesDao()
        }
        if (sensors == null) {
            //sensorRepository.syncWithServer()
            sensorRepository.getSensors()
            sensors = sensorRepository.getSensors()
        }
    }

    fun syncWithServer(baseUrl: String) {
        valuesRepository.syncWithServer(baseUrl)
        sensorRepository.syncWithServer(baseUrl)
    }

    fun getSensor(uuid: Int): Flowable<SensorEntity> {
        var result: SensorEntity? = null
        sensors?.value?.forEach { if (it.uuid == uuid) result = it }
        if (result != null) return Flowable.just(result)
        return sensorRepository.getSensorById(uuid)
    }

    fun addSensor(sensor: SensorEntity) {
        sensorRepository.addSensor(sensor)
    }

    fun deleteSensors(sensor: SensorEntity) {
        sensorRepository.deleteSensor(sensor)
    }

}