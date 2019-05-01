package me.xaanit.yogg.listeners

import discord4j.core.`object`.entity.*
import discord4j.core.`object`.reaction.ReactionEmoji
import discord4j.core.`object`.reaction.unicode
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.event.domain.message.ReactionAddEvent
import discord4j.core.event.domain.message.member
import discord4j.core.event.domain.message.message
import discord4j.core.event.on
import discord4j.core.spec.EmbedCreateSpec
import me.xaanit.yogg.beans.Card
import me.xaanit.yogg.data.HeroPictures
import me.xaanit.yogg.data.LevenshteinMetric
import me.xaanit.yogg.data.RarityGems
import java.awt.Color
import java.time.Instant


class CardListener(val cards: List<Card>) : Listener<MessageCreateEvent> {
    val replace = "[!'-.:± ]".toRegex()
    val regex = Regex(pattern = "\\[([^]]+)]")
    override suspend fun on(event: MessageCreateEvent) {
        val member = event.member() ?: return
        if (member.id.asLong() != 233611560545812480L) return
        val content = event.message.content() ?: return
        val find = regex.find(content) ?: return
        val card = find.groups[1]?.value ?: return
        event.message.channel().list(findCard(card, 0), 5, member)
                ?: event.message.channel().list(findCard(card, 3), 5, member)
                ?: event.message.channel().list(findCard(card, 5), 5, member)
                ?: event.message.channel().list(findCard(card, 7), 5, member)

    }


    fun findCard(name: String, threshold: Int): List<Card> =
            cards.filter {
                LevenshteinMetric.distance(
                        name.toLowerCase().replace(replace, ""),
                        it.name.toLowerCase().replace(replace, "")
                ) <= threshold
            }

    suspend fun MessageChannel.send(card: Card, old: Message? = null): Message =
            old?.update {
                it.setEmbed {
                    it.setAuthor(card.cardClass.toLowerCase().capitalize(), null, HeroPictures.getUrl(card.cardClass.toUpperCase()))
                    it.setTitle(card.name)
                    it.setImage(card.url)
                    it.setFooter("Card art by ${card.artist}", RarityGems.url(card.rarity.toUpperCase()))
                    it.setDescription("*${card.flavor.replace("[b]", "**")}*")
                    it.setColor(Color(0x249999))
                    it.setTimestamp(Instant.now())
                }
            } ?: newEmbed {
                it.setAuthor(card.cardClass.toLowerCase().capitalize(), null, HeroPictures.getUrl(card.cardClass.toUpperCase()))
                it.setTitle(card.name)
                it.setImage(card.url)
                it.setFooter("Card art by ${card.artist}", RarityGems.url(card.rarity.toUpperCase()))
                it.setDescription("*${card.flavor.replace("[b]", "**")}*")
                it.setColor(Color(0x249999))
                it.setTimestamp(Instant.now())
            }


    suspend fun MessageChannel.list(cards: List<Card>, amount: Int, user: Member, page: Int = 0, old: Message? = null): Message? {
        if (cards.isEmpty()) return null
        if (cards.size == 1) return send(cards[0])
        val description = cards.subList(amount * page, Math.min(cards.size, amount + amount * page)).mapIndexed { index, element ->
            "${index + 1}: [${element.name}](${element.url})"
        }.joinToString(separator = "\n")
        val spec: (EmbedCreateSpec) -> Unit = {
            it.setAuthor(
                    "Card search [Page ${page + 1}/${Math.ceil((cards.size.toDouble() / amount)).toInt()}]",
                    null,
                    HeroPictures.UNKNOWN.url
            )
            it.setFooter("React to see card.", null)
            it.setDescription(description)
            it.setColor(Color(0x249999))
            it.setTimestamp(Instant.now())
        }
        val message = old?.update {
            it.setEmbed(spec)
        } ?: newEmbed(spec)

        message.awaitAddReaction(ReactionEmoji.unicode("\u2B05")) // left
        for (i in 1..(Math.min(Math.min(9, amount), cards.size - (page * amount))))
            message.awaitAddReaction(ReactionEmoji.unicode("$i\u20E3")) // 1-9
        if (amount == 10)
            message.awaitAddReaction(ReactionEmoji.unicode("\uD83D\uDD1F")) //10
        message.awaitAddReaction(ReactionEmoji.unicode("\u27A1")) // right arrow
        message.awaitAddReaction(ReactionEmoji.unicode("\u274C")) // x
        val channel = message.client.eventDispatcher.on(ReactionAddEvent::class)
        for (event in channel) {
            if (event.messageId == message.id) {
                message.awaitRemoveReaction(event.emoji, event.userId)
                if (event.userId == user.id) {
                    when (event.emoji.unicode()!!.raw) {
                        "\u274c" -> message.awaitDelete() // X
                        "\u27A1" -> { // RIGHT
                            if (cards.size - (page * amount) > amount) {
                                message.awaitRemoveAllReactions()
                                list(cards, amount, user, page + 1, message)
                            }
                        }
                        "\u2B05" -> { // LEFT
                            if (page > 0) {
                                message.awaitRemoveAllReactions()
                                list(cards, amount, user, page - 1, message)
                            }
                        }
                        else -> {
                            event.message().awaitRemoveAllReactions()
                            send(cards[(event.emoji.unicode()!!.raw.toCharArray()[0].toInt() - 49) + (page * amount)], message)
                        }
                    }
                }
            }
        }
        return message
    }

}