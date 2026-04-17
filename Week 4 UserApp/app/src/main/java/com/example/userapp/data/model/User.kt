package com.example.userapp.data.model

/*
    Bu dosya bizim kullanıcı nesnemiz.

    API’den gelen her kullanıcıda şu bilgiler olacak:

    id
    name
    username
    email
    phone
    website
 */


data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String
)