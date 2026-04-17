package com.example.userapp.data.repository

import com.example.userapp.data.model.User
import com.example.userapp.data.remote.RetrofitInstance
import kotlinx.coroutines.delay

class UserRepository {
    suspend fun getUsers(): List<User> {

        return RetrofitInstance.api.getUsers()
    }
}