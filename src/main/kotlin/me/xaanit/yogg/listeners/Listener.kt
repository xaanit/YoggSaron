package me.xaanit.yogg.listeners

import discord4j.core.event.domain.Event

interface Listener<T : Event> {
    suspend fun on(event: T)
}