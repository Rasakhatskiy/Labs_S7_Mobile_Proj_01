package com.example.s07_mobile_proj_1.db;

class Brand(
    var id: Int = 0, var name: String, var place: Int, var value: Int, var diff: Int
) {
    override fun toString(): String {
        return "$id - $name - $place - $value - $diff"
    }
}