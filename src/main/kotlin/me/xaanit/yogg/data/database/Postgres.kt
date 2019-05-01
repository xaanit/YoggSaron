package me.xaanit.yogg.data.database

import me.xaanit.yogg.beans.DBCard
import me.xaanit.yogg.beans.User
import me.xaanit.yogg.data.CardMapper
import me.xaanit.yogg.data.UserMapper
import org.jdbi.v3.core.HandleCallback
import org.jdbi.v3.core.Jdbi

class Postgres {

    private val jdbi: Jdbi = Jdbi.create("jdbc:postgresql://localhost:5432/postgres?user=jacob")

    fun <T> execute(callback: HandleCallback<T, Exception>): T = jdbi.withHandle(callback)

    fun createDatabases() {
        execute(HandleCallback {
            it.execute("CREATE TABLE IF NOT EXISTS users(id BIGINT, admin BOOLEAN, blacklisted BOOLEAN)")
            it.execute("""CREATE TABLE IF NOT EXISTS cards(
                |id SERIAL NOT NULL PRIMARY KEY,
                |creator BIGINT NOT NULL,
                |name TEXT NOT NULL,
                |text TEXT NOT NULL,
                |flavour TEXT NOT NULL DEFAULT '',
                |attack INT NOT NULL DEFAULT -1,
                |cost INT NOT NULL,
                |health INT NOT NULL DEFAULT -1,
                |rarity TEXT NOT NULL DEFAULT 'COMMON',
                |type TEXT NOT NULL,
                |race TEXT NOT NULL DEFAULT '',
                |overload INT NOT NULL DEFAULT 0,
                |upvotes INT NOT NULL DEFAULT 0,
                |downvotes INT NOT NULL DEFAULT 0,
                |classes TEXT NOT NULL DEFAULT 'NEUTRAL'
                |)
                |""".trimMargin())
        })
    }

    val cards: List<DBCard>
        get() = execute(HandleCallback {
            it.createQuery("SELECT * FROM cards")
                    .map(CardMapper())
                    .list()
        })

    fun getCardById(id: Int = 0): DBCard? = execute(HandleCallback {
        it.createQuery("SELECT * FROM cards WHERE id = ?")
                .bind(0, id)
                .map(CardMapper())
                .firstOrNull()
    })

    fun getUserCards(id: Long): List<DBCard> = execute(HandleCallback {
        it.createQuery("SELECT * FROM cards WHERE creator = ?")
                .bind(0, id)
                .map(CardMapper())
                .list()
    })

    fun getCards(filter: (DBCard) -> Boolean): List<DBCard> = cards.filter(filter)

    val users: List<User>
        get() = execute(HandleCallback {
            it.createQuery("SELECT * FROM users")
                    .map(UserMapper())
                    .list()
        })

    val admins: List<User>
        get() = execute(HandleCallback {
            it.createQuery("SELECT * FROM users WHERE admin = true")
                    .map(UserMapper())
                    .list()
        })

    val blacklisted: List<User>
        get() = execute(HandleCallback {
            it.createQuery("SELECT * FROM users WHERE blacklisted = true")
                    .map(UserMapper())
                    .list()
                    .filter { !it.admin }
        })

}