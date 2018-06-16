package a248.kotlinoid.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.content.Context
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*



const val SENSOR_TABLE_NAME = "sensors"
const val VALUES_TABLE_NAME = "values_table"

@Entity(tableName = SENSOR_TABLE_NAME)
class SensorEntity {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
    @ColumnInfo(name = "title") var title: String = ""
    @ColumnInfo(name = "desc") var desc: String = ""
    @ColumnInfo(name = "server_id") var serverId: Int = 0
}

@Entity(tableName = VALUES_TABLE_NAME, foreignKeys = arrayOf(ForeignKey(entity = SensorEntity::class,
        parentColumns = arrayOf("uuid"),
        childColumns = arrayOf("sensorId"),
        onDelete = ForeignKey.CASCADE)))
class ValuesEntity {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
    @ColumnInfo(name = "value") var value: Float = 0.0f
    @ColumnInfo(name = "date") var date: Date = Date()
    @ColumnInfo(name = "sensorId") var sensorId : Int = 0
    @ColumnInfo(name = "server_sensor_id") var serverSensorId: Int = 0
}


@Dao
interface SensorDao {
    @Query("SELECT * FROM $SENSOR_TABLE_NAME")
    fun getAllAsLiveData(): LiveData<List<SensorEntity>>

    @Query("SELECT * FROM $SENSOR_TABLE_NAME WHERE uuid = :uuid")
    fun getSensorById(uuid: Int): Flowable<SensorEntity>

    @Insert
    fun insertAll(vararg objects: SensorEntity)

    @Delete
    fun delete(objectEntity: SensorEntity)

    @Query("DELETE FROM $SENSOR_TABLE_NAME")
    fun deleteAll()
}

@Dao
interface ValuesDao {

    @Query("SELECT * FROM $VALUES_TABLE_NAME")
    fun getAllValues(): Flowable<List<ValuesEntity>>

    @Query("SELECT * FROM $VALUES_TABLE_NAME WHERE sensorId = :sensorId")
    fun getValuesBySensorId(sensorId: Int): LiveData<List<ValuesEntity>>

    @Insert
    fun insertAll(vararg objects: ValuesEntity)

    @Delete
    fun delete(objectEntity: ValuesEntity)

}

@Database(entities = arrayOf(SensorEntity::class, ValuesEntity::class), version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun sensorDao(): SensorDao
    abstract fun valuesDao(): ValuesDao
    companion object {
        private var instance: AppDatabase? = null
        fun getInstance(ctx: Context): AppDatabase? {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(ctx, AppDatabase::class.java, "test-db").build()
                }
                return instance
            }
        }
    }
}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}

fun Context.getDb(): AppDatabase {
    return AppDatabase.getInstance(applicationContext)!!
}