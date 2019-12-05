package com.example.rssfeeder.data

data class Account (
    var uid: String = "",
    var email: String = "",
    var password: String = "",
    var blogList: List<String> = emptyList()
)