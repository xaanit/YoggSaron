package me.xaanit.yogg.listeners

import discord4j.core.`object`.entity.TextChannel
import discord4j.core.`object`.entity.channel
import discord4j.core.`object`.entity.content
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.event.domain.message.guild
import discord4j.core.event.domain.message.member
import me.xaanit.yogg.commands.Command
import me.xaanit.yogg.commands.Eval
import me.xaanit.yogg.data.database.Postgres

class CommandHandler(val db: Postgres) : Listener<MessageCreateEvent> {
    val commands = {
        val map = mutableMapOf<String, Command>()
        listOf(Eval())
                .forEach {
                    val cmd = it
                    cmd.aliases.forEach { alias ->
                        map += alias.toLowerCase() to cmd
                    }
                    map += cmd.name.toLowerCase() to cmd
                }
        map.toMap()
    }()

    override suspend fun on(event: MessageCreateEvent) {
        val guild = event.guild()
        val author = event.member() ?: return
        val message = event.message
        val channel = event.message.channel() as? TextChannel ?: return
        val content = message.content() ?: return
        if(!content.startsWith("!")) return
        val blacklisted = db.blacklisted
        if (blacklisted.any { it.id == author.id.asLong() }) return
        val admins = db.admins
        val args = content.split("\\s+".toRegex())
        val command = commands[args[0].substring(1).toLowerCase()] ?: return
        if (command.admin && admins.map { it.id }.contains(author.id.asLong())) {
            command.execute(author, channel, guild, message, db)
        } else {
            command.execute(author, channel, guild, message, db)
        }
    }
}