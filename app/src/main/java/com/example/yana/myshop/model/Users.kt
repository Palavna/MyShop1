package com.example.yana.myshop.model

data class Users(

    var userName: String? = null,
    var phone: String? = null,
    var password: String? = null,

    ) {
    constructor() : this(null, null, null)
}

