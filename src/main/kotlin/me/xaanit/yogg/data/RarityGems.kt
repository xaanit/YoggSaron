package me.xaanit.yogg.data

enum class RarityGems {
    COMMON,
    RARE,
    EPIC,
    LEGENDARY;

    companion object {
        fun url(rarirty: String): String {
            val gem = values().find { it.name == rarirty } ?: COMMON
            return "http://www.hearthcards.net/images/gem/gem_${gem.name.toLowerCase()}.png"
        }
    }
}