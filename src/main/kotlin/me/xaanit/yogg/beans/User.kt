package me.xaanit.yogg.beans

data class User(
        val id: Long,
        val admin: Boolean,
        val blacklisted: Boolean
)