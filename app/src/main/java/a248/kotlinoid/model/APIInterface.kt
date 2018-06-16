package a248.kotlinoid.model

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface APIInterface {
    @GET("get_all_sensors.php")
    fun getSensors(): Call<List<SensorPOJO>>

    @POST("create_values.php")
    fun createValue(@Body value: ValuePOJO): Call<ValueResponse>
}