package com.enfotrix.cricscore.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.enfotrix.cricscore.data.Repo
import com.enfotrix.cricscore.db.CricDB
import com.enfotrix.cricscore.models.UserModel
import kotlinx.coroutines.launch

class CricViewModel(application: Application) : AndroidViewModel(application)  {
    val dao = CricDB.getInstance(application).CricDao()
    val repo = Repo(dao)

    private val _users = MutableLiveData<List<UserModel>>()
    val users: LiveData<List<UserModel>> get() = _users
    private val _registrationResult = MutableLiveData<String>()
    val registrationResult: LiveData<String> = _registrationResult

    fun fetchAllUsers() {
        repo.getAllUsers { users ->
            _users.postValue(users)
        }
    }

    fun registerUser(user: UserModel) {
        repo.registerUser(user) { message ->
            _registrationResult.postValue(message)
        }
    }

    fun insertUser(user: UserModel) {
        viewModelScope.launch {
            repo.insertUser(user)
        }
    }

}