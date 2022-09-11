package com.example.yana.myshop.model

data class Admins(

    var userName: Long? = null,
    var phone: Long? = null,
    var password: Long? = null,

    ) {
    constructor() : this(null, null, null)
}

