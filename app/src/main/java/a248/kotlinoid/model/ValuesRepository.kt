package a248.kotlinoid.model

import android.arch.lifecycle.LiveData
import android.util.Log
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by w-p94 on 15.04.2018.
 */
object ValuesRepository {

    var valuesDao: ValuesDao? = null
    var getAllValuesSubscription: Disposable? = null

    fun checkDao(): ValuesDao {
        return valuesDao ?: throw NullPointerException("==Set Dao to SensorRepository")
    }

    fun getValues(sensorId:Int): LiveData<List<ValuesEntity>> = checkDao().getValuesBySensorId(sensorId)

    fun addValues(vararg values: ValuesEntity) {
        val nonNullDao = checkDao()
        unitResultTask({}, {nonNullDao.insertAll(*values)}, {})
    }

    fun syncWithServer(baseUrl: String) {
        getAllValuesSubscription = checkDao()
                .getAllValues()
                .subscribe {
                    valuesList ->
                    if (getAllValuesSubscription != null) {
                        getAllValuesSubscription!!.dispose()
                    }
               //     Log.d("abrakadra vlz", "VALUES LIST SIZE = ${valuesList.size}")
                    valuesList.forEach {
                        val retrofit = Retrofit.Builder()
                                .baseUrl("http://" + baseUrl + "/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build()
                        val messagesApi = retrofit.create(APIInterface::class.java)
                 //       Log.d("abrakadabra", it.serverSensorId.toString())
                        val valuePOJO = ValuePOJO(
                                it.value,
                                //it.date.toString(),
                                "${it.date.year}-${it.date.month}-${it.date.day} ${it.date.hours}:${it.date.minutes}:${it.date.seconds}",
                                it.serverSensorId
                        )
                        val request = messagesApi.createValue(valuePOJO)
                        request.enqueue(object: Callback<ValueResponse> {
                            override fun onFailure(call: Call<ValueResponse>?, t: Throwable?) {
                       //         Log.d("abrakadabra", "fail ${t.toString()}")
                            }

                            override fun onResponse(call: Call<ValueResponse>?, response: Response<ValueResponse>) {
                       //         Log.d("abrakadabra", "code ${response.code().toString()}")
                       //         Log.d("abrakadabra", response.body()?.message ?: "No message")
                            }
                        })
                    }
        }
    }

    fun deleteValues(value: ValuesEntity) {
        val nonNullDao = checkDao()
        unitResultTask({}, {nonNullDao.delete(value)}, {})
    }

}