package com.example.s07_mobile_proj_1.db;

class User(
    var id: Int = 0, var name: String, var password: String
) {
    override fun toString(): String {
        return "$id - $name - $password"
    }
}