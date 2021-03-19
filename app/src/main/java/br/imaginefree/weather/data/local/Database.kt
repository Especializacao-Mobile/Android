package br.imaginefree.weather.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.imaginefree.weather.data.model.Weather

@Database(entities = [Weather::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) { synchronized(AppDatabase::class) {
                INSTANCE = Room.databaseBuilder( context.applicationContext, AppDatabase::class.java, "myDB"
                ).build() }
            }
            return INSTANCE
        }
    } }
