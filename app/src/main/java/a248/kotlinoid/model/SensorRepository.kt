package a248.kotlinoid.model

import android.arch.lifecycle.LiveData
import android.util.Log
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object SensorRepository {

    var sensorDao: SensorDao? = null

    fun checkDao(): SensorDao {
        return sensorDao ?: throw NullPointerException("==Set Dao to SensorRepository")
    }

    fun getSensors(): LiveData<List<SensorEntity>>? = checkDao().getAllAsLiveData()

    fun syncWithServer(baseUrl: String){
        val retrofit = Retrofit.Builder()
                .baseUrl("http://" + baseUrl + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val messagesApi = retrofit.create(APIInterface::class.java)

        val sensors = messagesApi.getSensors()
   //     Log.d("абракадабра","йцуйцуйцу ${sensors.request().url().toString()}");

        sensors.enqueue(object : Callback<List<SensorPOJO>> {
            override fun onResponse(call: Call<List<SensorPOJO>>, response: Response<List<SensorPOJO>>) {

                if ((response.isSuccessful) && (response.code() == 200)) {
                    Log.d("абракадабра","response " + response.body()!!.size)
                    deleteAllSensors()
                    val responseSensors = mutableListOf<SensorEntity>()
                    response.body()!!.forEach {
                        val sensor = SensorEntity()
                        sensor.title = it.name
                        sensor.desc = it.measurements
                        sensor.serverId = it.pid.toInt()
                        responseSensors.add(sensor)
                    }
                    addSensor(*responseSensors.toTypedArray())
                } else {
       //             Log.d("абракадабра", response.code().toString())
                }
            }
            override fun onFailure(call: Call<List<SensorPOJO>>, t: Throwable) {
     //           Log.d("абракадабра","FAILURE ${t.message}")
                t.printStackTrace()
            }
        })
    }

    fun getSensorById(uuid: Int): Flowable<SensorEntity> = checkDao().getSensorById(uuid)
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())

    fun addSensor(vararg sensors: SensorEntity) {
        val nonNullDao = checkDao()
        unitResultTask({}, {nonNullDao.insertAll(*sensors)}, {})
    }

    fun deleteSensor(sensor: SensorEntity) {
        val nonNullDao = checkDao()
        unitResultTask({}, {nonNullDao.delete(sensor)}, {})
    }

    fun deleteAllSensors() {
        val nonNullDao = checkDao()
        unitResultTask({}, {nonNullDao.deleteAll()}, {})
    }

}