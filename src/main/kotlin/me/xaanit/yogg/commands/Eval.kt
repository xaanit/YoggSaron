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
        val engine = ScriptEngineManager().getEngineByName("kotlin")
        val start = System.currentTimeMillis()
        val res = Either.from {
            engine.evalKotlin(message.content()!!.substring("!eval ".length), SimpleBindings(hashMapOf<String, Any>(
                    "member" to member,
                    "channel" to channel,
                    "guild" to guild,
                    "message" to message,
                    "db" to db
            )))
        }
        val result = res.toString().max(size = 2040)

        channel.newEmbed {
            it.setColor(when (res) {
                is Right -> Color(0xdd021b)
                else -> Color(0x249999)
            })
            it.setAuthor("Eval", null, HeroPictures.getUrl(""))
            it.setFooter("${System.currentTimeMillis() - start}ms", null)
            it.setTimestamp(Instant.now())
            if (result.matches("(?:([^:/?#]+):)?(?:\\//([^/?#]*))?([^?#]*\\.(?:jpg|gif|png))(?:\\?([^#]*))?(?:#(.*))?".toRegex())) it.setImage(result) else it.setDescription("""
               ```
               $result
               ```
           """.trimIndent())
        }
    }

    fun ScriptEngine.evalKotlin(originalScript: String, bindings: Bindings): Any? {
        val injection =
                bindings.keys.joinToString("\n") { key -> "val $key = bindings[\"$key\"] as ${key::class.java.name}" }
        val lastImportIndex = originalScript.lastIndexOf("import ")
        val endOfImports = originalScript.substring(lastImportIndex).indexOf("\n") + lastImportIndex
        val imports = originalScript.substring(0, endOfImports)
        val script = originalScript.substring(endOfImports)
        val evalScript = "$imports\n$injection\n$script"
        println(evalScript)
        return eval(evalScript, bindings)
    }


    private fun String.max(size: Int): String = substring(0, Math.min(length, size))
}

