package me.xaanit.yogg.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
class Card(
        val artist: String = "DEFAULT",
        val cardClass: String = "DEFAULT",
        val collectible: Boolean = false,
        val cost: Int = -1,
        val dbfId: Long = -1,
        val flavor: String = "DEFAULT",
        val id: String = "DEFAULT",
        val name: String = "DEFAULT",
        //  val playRequirements: String = "NEW",
        val rarity: String = "DEFAULT",
        val set: String = "DEFAULT",
        val text: String = "DEFAULT",
        val type: String = "DEFAULT",
        val mechanics: Array<String> = arrayOf("NONE"),
        val attack: Int = -1,
        val health: Int = -1,
        val referencedTags: Array<String> = arrayOf("NONE"),
        val race: String = "DEFAULT",
        val elite: Boolean = false,
        val targetingArrowText: String = "DEFAULT",
        val durability: Int = -1,
        val overload: Int = -1,
        val spellDamage: Int = -1,
        val armor: Int = -1,
        val faction: String = "DEFAULT",
        val howToEarn: String = "DEFAULT",
        val howToEarnGolden: String = "DEFAULT",
        val collectionText: String = "DEFAULT",
        val classes: Array<String> = arrayOf("NONE"),
        val multiClassGroup: String = "DEFAULT",
        val entourage: Array<String> = arrayOf("NONE"),
        val hideStats: Boolean = false,
        val questReward: String = "DEFAULT"
) {
    val url
        get() = "https://art.hearthstonejson.com/v1/render/latest/enUS/256x/$id.png"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Card) return false

        if (artist != other.artist) return false
        if (cardClass != other.cardClass) return false
        if (collectible != other.collectible) return false
        if (cost != other.cost) return false
        if (dbfId != other.dbfId) return false
        if (flavor != other.flavor) return false
        if (id != other.id) return false
        if (name != other.name) return false
        if (rarity != other.rarity) return false
        if (set != other.set) return false
        if (text != other.text) return false
        if (type != other.type) return false
        if (!mechanics.contentEquals(other.mechanics)) return false
        if (attack != other.attack) return false
        if (health != other.health) return false
        if (!referencedTags.contentEquals(other.referencedTags)) return false
        if (race != other.race) return false
        if (elite != other.elite) return false
        if (targetingArrowText != other.targetingArrowText) return false
        if (durability != other.durability) return false
        if (overload != other.overload) return false
        if (spellDamage != other.spellDamage) return false
        if (armor != other.armor) return false
        if (faction != other.faction) return false
        if (howToEarn != other.howToEarn) return false
        if (howToEarnGolden != other.howToEarnGolden) return false
        if (collectionText != other.collectionText) return false
        if (!classes.contentEquals(other.classes)) return false
        if (multiClassGroup != other.multiClassGroup) return false
        if (!entourage.contentEquals(other.entourage)) return false
        if (hideStats != other.hideStats) return false
        if (questReward != other.questReward) return false
        if (url != other.url) return false

        return true
    }

    override fun hashCode(): Int {
        var result = artist.hashCode()
        result = 31 * result + cardClass.hashCode()
        result = 31 * result + collectible.hashCode()
        result = 31 * result + cost
        result = 31 * result + dbfId.hashCode()
        result = 31 * result + flavor.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + rarity.hashCode()
        result = 31 * result + set.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + mechanics.contentHashCode()
        result = 31 * result + attack
        result = 31 * result + health
        result = 31 * result + referencedTags.contentHashCode()
        result = 31 * result + race.hashCode()
        result = 31 * result + elite.hashCode()
        result = 31 * result + targetingArrowText.hashCode()
        result = 31 * result + durability
        result = 31 * result + overload
        result = 31 * result + spellDamage
        result = 31 * result + armor
        result = 31 * result + faction.hashCode()
        result = 31 * result + howToEarn.hashCode()
        result = 31 * result + howToEarnGolden.hashCode()
        result = 31 * result + collectionText.hashCode()
        result = 31 * result + classes.contentHashCode()
        result = 31 * result + multiClassGroup.hashCode()
        result = 31 * result + entourage.contentHashCode()
        result = 31 * result + hideStats.hashCode()
        result = 31 * result + questReward.hashCode()
        result = 31 * result + url.hashCode()
        return result
    }

    override fun toString(): String {
        return "Card(artist='$artist',\n cardClass='$cardClass',\n collectible=$collectible,\n cost=$cost,\n dbfId=$dbfId,\n flavor='$flavor',\n id='$id',\n name='$name',\n rarity='$rarity',\n set='$set',\n text='$text',\n type='$type',\n mechanics=${Arrays.toString(mechanics)},\n attack=$attack,\n health=$health,\n referencedTags=${Arrays.toString(referencedTags)},\n race='$race',\n elite=$elite,\n targetingArrowText='$targetingArrowText',\n durability=$durability,\n overload=$overload,\n spellDamage=$spellDamage,\n armor=$armor,\n faction='$faction',\n howToEarn='$howToEarn',\n howToEarnGolden='$howToEarnGolden',\n collectionText='$collectionText',\n classes=${Arrays.toString(classes)},\n multiClassGroup='$multiClassGroup',\n entourage=${Arrays.toString(entourage)},\n hideStats=$hideStats,\n questReward='$questReward',\n url='${url}')"
    }
}