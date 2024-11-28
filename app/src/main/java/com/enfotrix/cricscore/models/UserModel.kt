package com.enfotrix.cricscore.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class UserModel @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var phoneNumber: String = "",
    var password: String = "",
    var userType : String = ""
)
