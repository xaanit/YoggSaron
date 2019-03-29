package me.xaanit.yogg

import discord4j.core.DiscordClient
import discord4j.core.event.domain.Event
import kotlinx.coroutines.reactive.openSubscription
import me.xaanit.yogg.listeners.Listener

suspend inline fun <reified T : Event> DiscordClient.register(listener: Listener<T>) {
    val channel = eventDispatcher.on(T::class.java).openSubscription()
    while (!channel.isClosedForReceive) {
        listener.on(channel.receive())
    }
}
