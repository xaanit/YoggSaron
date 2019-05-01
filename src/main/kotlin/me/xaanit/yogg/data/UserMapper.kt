package me.xaanit.yogg.data

import me.xaanit.yogg.beans.User
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet

class UserMapper : RowMapper<User> {
    override fun map(rs: ResultSet, ctx: StatementContext): User =
            User(rs.getLong("id"), rs.getBoolean("admin"), rs.getBoolean("blacklisted"))
}