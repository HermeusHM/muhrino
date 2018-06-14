package a248.kotlinoid.viewmodel

import a248.kotlinoid.model.SensorEntity
import a248.kotlinoid.model.SensorRepository
import a248.kotlinoid.model.getDb
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import io.reactivex.Flowable

class SensorViewModel : ViewModel() {

    var sensors: LiveData<List<SensorEntity>>? = null
    private var sensorRepository = SensorRepository

    fun init(context: Context) {
        if (sensorRepository.sensorDao == null) {
            sensorRepository.sensorDao = context.getDb().sensorDao()
        }
        if (sensors == null) {
            //sensorRepository.getSensorsFromServer()
            sensorRepository.getSensors()
            sensors = sensorRepository.getSensors()
        }
    }

    fun getSensorsFromServer() {
        sensorRepository.getSensorsFromServer()
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