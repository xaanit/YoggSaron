package me.xaanit.yogg.data

import me.xaanit.yogg.beans.DBCard
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet

class CardMapper : RowMapper<DBCard> {
    override fun map(rs: ResultSet, ctx: StatementContext): DBCard =
            DBCard(
                    creator = rs.getLong("creator"),
                    downvotes = rs.getInt("downvotes"),
                    upvotes = rs.getInt("upvotes"),
                    durability = rs.getInt("durability"),
                    rarity = rs.getString("rarity"),
                    armour = rs.getInt("armour"),
                    attack = rs.getInt("attack"),
                    classes = rs.getString("classes").split(";"),
                    cost = rs.getInt("cost"),
                    flavour = rs.getString("flavour"),
                    health = rs.getInt("health"),
                    id = rs.getLong("id"),
                    name = rs.getString("name"),
                    overload = rs.getInt("overload"),
                    race = rs.getString("race"),
                    text = rs.getString("text"),
                    type = rs.getString("type")
            )
}