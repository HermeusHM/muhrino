package a248.kotlinoid.model

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import java.util.*


interface APIInterface {
    @GET("get_all_sensors.php")
    fun getSensors(): Call<List<SensorPOJO>>
}