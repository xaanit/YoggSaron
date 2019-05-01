package me.xaanit.yogg.commands

import discord4j.core.`object`.entity.*
import me.xaanit.yogg.Either
import me.xaanit.yogg.Right
import me.xaanit.yogg.data.HeroPictures
import me.xaanit.yogg.data.database.Postgres
import java.awt.Color
import java.time.Instant
import javax.script.Bindings
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.SimpleBindings

class Eval : Command(name = "eval", admin = true) {
    override suspend fun execute(member: Member, channel: TextChannel, guild: Guild, message: Message, db: Postgres) {
        val engine = ScriptEngineManager().getEngineByName("kts")

        val res = Either.from {
            engine.evalKotlin(message.content()!!.substring("!eval ".length), SimpleBindings(hashMapOf<String, Any>(
                    "member" to member,
                    "channel" to channel,
                    "guild" to guild,
                    "message" to message,
                    "db" to db
            )))
        }
        channel.newEmbed {
            it.setColor(when (res) {
                is Right -> Color(0xdd021b)
                else -> Color(0x249999)
            })
            it.setAuthor("Eval", null, HeroPictures.getUrl(""))
            it.setTimestamp(Instant.now())
            it.setDescription(
                    res.toString().max(size = 2048)
            )
        }
    }

    fun ScriptEngine.evalKotlin(script: String, bindings: Bindings): Any? {
        val injection =
                bindings.entries.joinToString("\n") { (key, value) ->
                    "val $key" +
                            ": ${value::class.java.simpleName} = bindings[\"$key\"] as ${value::class.java.name}"
                }
        println("INJECTION: $injection")
        return eval("$injection\n$script", bindings)
    }

    private fun String.max(size: Int): String = substring(0, Math.min(length, size))
}

