package me.xaanit.yogg.listeners

import discord4j.core.`object`.presence.Activity
import discord4j.core.`object`.presence.Presence
import discord4j.core.changePresence
import discord4j.core.event.domain.guild.GuildCreateEvent
import discord4j.core.event.domain.lifecycle.ReadyEvent
import kotlinx.coroutines.reactive.openSubscription

class OnlineListener : Listener<ReadyEvent> {
    override suspend fun on(event: ReadyEvent) {
        val done = event.client.eventDispatcher.on(GuildCreateEvent::class.java)
                .take(event.guilds.size.toLong())
                .next()
                .openSubscription()
        done.poll()
        event.client.changePresence(Presence.doNotDisturb(Activity.listening("for commands")))

    }
}