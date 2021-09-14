package com.taufik.adeptforms.data.model.users

data class Users(
    val id: String,
    val imageUrl: String,
    val fullName: String,
    val companyName: String,
    val jobPosition: String,
    val email: String,
    val password: String
)
