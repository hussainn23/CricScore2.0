package com.enfotrix.cricscore.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.enfotrix.cricscore.models.UserModel

@Dao
interface CricDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserModel)

    @Query("SELECT * FROM users")
    fun getUser(id : String) : LiveData<UserModel>

    @Delete
    fun deleteUser(user: UserModel)



}