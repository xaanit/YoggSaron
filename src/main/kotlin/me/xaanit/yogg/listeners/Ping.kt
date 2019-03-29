package me.xaanit.yogg.listeners

import discord4j.core.event.domain.message.MessageCreateEvent
import kotlinx.coroutines.reactive.awaitSingle

class Ping : Listener<MessageCreateEvent> {
    override suspend fun on(event: MessageCreateEvent) {
        val message = event.message
        val content = message.content.orElse("")
        val member = message.authorAsMember.awaitSingle()
        if (content != "!ping" || member.id.asLong() != 233611560545812480L) return

        val msg = message.channel.awaitSingle().createMessage("Pong!").awaitSingle()
        msg.edit { it.setContent("Pong! (${msg.timestamp.toEpochMilli() - message.timestamp.toEpochMilli()}ms)") }.awaitSingle()
    }
}