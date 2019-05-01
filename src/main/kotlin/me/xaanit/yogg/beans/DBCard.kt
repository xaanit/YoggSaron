package me.xaanit.yogg.beans

data class DBCard(
        val creator: Long,
        val id: Long,
        val name: String,
        val text: String,
        val flavour: String,
        val attack: Int,
        val cost: Int,
        val health: Int,
        val rarity: String,
        val type: String,
        val armour: Int,
        val durability: Int,
        val race: String,
        val overload: Int,
        val classes: List<String>,
        val upvotes: Int,
        val downvotes: Int
)