package me.xaanit.yogg.listeners

import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.MessageChannel
import discord4j.core.event.domain.message.MessageCreateEvent
import kotlinx.coroutines.reactive.awaitSingle
import me.xaanit.yogg.HeroPictures
import me.xaanit.yogg.LevenshteinMetric
import me.xaanit.yogg.RarityGems
import me.xaanit.yogg.data.Card
import java.awt.Color
import java.time.Instant

class CardListener(val cards: List<Card>) : Listener<MessageCreateEvent> {
    val replace = "[!'-.:± ]".toRegex()
    val regex = Regex(pattern = "\\[([^]]+)]")
    override suspend fun on(event: MessageCreateEvent) {
        val member = event.member.orElse(null) ?: return
        if (member.id.asLong() != 233611560545812480L) return
        val content = event.message.content.orElse(null) ?: return
        val find = regex.find(content) ?: return
        val card = find.groups[1]?.value ?: return
        val exact = findCard(card, 0)
        if (!exact.isEmpty()) {
            event.message.channel.awaitSingle().send(exact[0])
        } else {
        }
    }


    fun findCard(name: String, threshold: Int): List<Card> =
            cards.filter {
                LevenshteinMetric.distance(
                        name.toLowerCase().replace(replace, ""),
                        it.name.toLowerCase().replace(replace, "")
                ) <= threshold
            }

    suspend fun MessageChannel.send(card: Card): Message = createEmbed {
        it.setAuthor(card.cardClass.toLowerCase().capitalize(), null, HeroPictures.getUrl(card.cardClass.toUpperCase()))
        it.setTitle(card.name)
        it.setImage(card.url)
        it.setFooter("Card art by ${card.artist}", RarityGems.url(card.rarity.toUpperCase()))
        it.setDescription("*${card.flavor.replace("[b]", "**")}*")
        it.setColor(Color(0x249999))
        it.setTimestamp(Instant.now())
    }.awaitSingle()


    //suspend fun MessageChannel.list(cards: List<Card>, amount: Int): Message =

}