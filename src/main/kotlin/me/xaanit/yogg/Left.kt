package me.xaanit.yogg

class Left< T>(val value: T) : Either {
    override fun toString(): String = value.toString()
}