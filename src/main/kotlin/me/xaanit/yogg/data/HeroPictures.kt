package me.xaanit.yogg.data

enum class HeroPictures(val url: String) {
    WARRIOR("https://gamepedia.cursecdn.com/wowpedia/thumb/6/62/ClassIcon_warrior.png/39px-ClassIcon_warrior.png?version=507bceffe18514920575325b70013ab3"),
    WARLOCK("https://gamepedia.cursecdn.com/wowpedia/thumb/7/77/ClassIcon_warlock.png/39px-ClassIcon_warlock.png?version=1e026d22af3b5f7abf3bcb4c6ae7076c"),
    DRUID("https://gamepedia.cursecdn.com/wowpedia/thumb/6/67/ClassIcon_druid.png/39px-ClassIcon_druid.png?version=d7d8a6d42081f0767a599ca3415cd2ac"),
    SHAMAN("https://gamepedia.cursecdn.com/wowpedia/thumb/0/00/ClassIcon_shaman.png/39px-ClassIcon_shaman.png?version=f6137cb27f4a816300565e93894ad075"),
    PALADIN("https://gamepedia.cursecdn.com/wowpedia/thumb/c/c5/ClassIcon_paladin.png/39px-ClassIcon_paladin.png?version=3503e01de40564f5b6c12925ff212c27"),
    PRIEST("https://gamepedia.cursecdn.com/wowpedia/thumb/3/37/ClassIcon_priest.png/39px-ClassIcon_priest.png?version=689a8e213594ab76a9a547c5ca8d8e28"),
    ROGUE("https://gamepedia.cursecdn.com/wowpedia/thumb/2/20/ClassIcon_rogue.png/39px-ClassIcon_rogue.png?version=ee3e3e40ce4292d542f459e34770b826"),
    MAGE("https://gamepedia.cursecdn.com/wowpedia/thumb/0/02/ClassIcon_mage.png/39px-ClassIcon_mage.png?version=7b14de74abd2f13836a3127a3044d06d"),
    HUNTER("https://gamepedia.cursecdn.com/wowpedia/thumb/a/a6/ClassIcon_hunter.png/39px-ClassIcon_hunter.png?version=7b5d5ea2e4fd0ed99361efffcf9a4170"),
    UNKNOWN("https://images-ext-1.discordapp.net/external/fIZ_vRbLesiC6M_Dn7ciU7ouBo_kp9qckBwVe7h9oE0/http/casaveneziausa.com/wp-content/uploads/parser/hearthstone-logo-1.png");

    companion object {
        fun getUrl(clazz: String): String =
                values().find { it.name == clazz }?.url ?: UNKNOWN.url
    }
}