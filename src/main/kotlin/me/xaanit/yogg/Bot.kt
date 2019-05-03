package me.xaanit.yogg

import com.google.gson.Gson
import discord4j.core.DiscordClientBuilder
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import me.xaanit.yogg.beans.Card
import me.xaanit.yogg.data.database.Postgres
import me.xaanit.yogg.listeners.CardListener
import me.xaanit.yogg.listeners.CommandHandler
import me.xaanit.yogg.listeners.OnlineListener
import org.reflections.Reflections
import java.io.File


object Bot {



    @JvmStatic
    fun main(args: Array<String>) {
        Reflections()
        val db = Postgres()
        db.createDatabases()

        val gson = Gson()
        val http = HttpClient(OkHttp) {

            engine { config { followRedirects(true) } }
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
        }
        val cards = runBlocking {
            val str = http.get<String>("https://api.hearthstonejson.com/v1/latest/enUS/cards.collectible.json", {})
            gson.fromJson(str.replace("’", "'").replace("ñ", "n"), Array<Card>::class.java).toList()
        }


        val cfg = File("./config.json")
        if (!cfg.exists()) {
            val created = cfg.createNewFile()
            println("Trying to create file.")
            val json = gson.toJson(Config(token = ""))
            if (!created) {
                println("Couldn't create file. Please create and populate with $json")
            } else {
                cfg.writeText(text = json)
                println("File created. Please fill it.")
            }
            return
        }
        val config = gson.fromJson(cfg.readText(), Config::class.java)
        val client = DiscordClientBuilder(config.token).build()
        val listeners = listOf(CardListener(cards), CommandHandler(db), OnlineListener())
        client.login().subscribe()
        runBlocking {
            listeners.map { async { client.register(it) } }.forEach { it.await() }
        }
    }
}