package com.enfotrix.cricscore.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.enfotrix.cricscore.dao.CricDao
import com.enfotrix.cricscore.models.UserModel

@Database(
    entities = [UserModel::class], version = 1
)
abstract class CricDB : RoomDatabase() {

    abstract fun CricDao(): CricDao

    companion object {
        @Volatile
        private var instance: CricDB? = null

        fun getInstance(context: Context): CricDB {
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    CricDB::class.java,
                    "cric_db"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                instance = newInstance
                newInstance
            }
        }
    }
}
