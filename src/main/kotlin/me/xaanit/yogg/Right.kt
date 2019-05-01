package me.xaanit.yogg

class Right(val throwable: Throwable) : Either {
    override fun toString(): String = throwable.message ?: "NO MESSAGE FOUND."
}