package com.enfotrix.cricscore.models



data class UserModel @JvmOverloads constructor(
    var id: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var phoneNumber: String = "",
    var password: String = "",
    var coach: Boolean = false,
    var player : Boolean = false,

)
