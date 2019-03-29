package me.xaanit.yogg

import discord4j.core.DiscordClient
import discord4j.core.event.domain.Event
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactive.awaitLast
import kotlinx.coroutines.reactor.mono
import me.xaanit.yogg.listeners.Listener

suspend inline fun <reified T : Event> DiscordClient.register(listener: Listener<T>) {
    coroutineScope {
        eventDispatcher.on(T::class.java)
                .flatMap { mono { listener.on(it) } }.awaitLast()
    }
}
