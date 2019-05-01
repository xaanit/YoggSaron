package me.xaanit.yogg.commands

import discord4j.core.`object`.entity.*
import me.xaanit.yogg.data.database.Postgres
import javax.script.ScriptEngineManager
import javax.script.SimpleBindings

class Eval : Command(name = "eval", admin = true) {
    override suspend fun execute(member: Member, channel: TextChannel, guild: Guild, message: Message, db: Postgres) {
        println("Got here")
        val engine = ScriptEngineManager().getEngineByName("kotlin")
        println("NULL: ${engine == null}")
        val res = engine.eval(message.content()!!.substring("!eval ".length), SimpleBindings(hashMapOf<String, Any>(
                "member" to member,
                "channel" to channel,
                "guild" to guild,
                "message" to message,
                "db" to db
        )))
        channel.newMessage(res.toString())
    }
}