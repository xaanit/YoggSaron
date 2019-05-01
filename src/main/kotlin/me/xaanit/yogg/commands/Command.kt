package me.xaanit.yogg.commands

import discord4j.core.`object`.entity.Guild
import discord4j.core.`object`.entity.Member
import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.TextChannel
import me.xaanit.yogg.data.database.Postgres

abstract class Command(
        val name: String,
        val aliases: Array<String> = arrayOf(),
        val description: String = "",
        val admin: Boolean = false
) {
    abstract suspend fun execute(member: Member, channel: TextChannel, guild: Guild, message: Message, db: Postgres)
}