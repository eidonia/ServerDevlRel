package dao

import Database
import model.User
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineFindPublisher

class UserDao(db: Database) {

    private val collection: CoroutineCollection<User> = db.database.getCollection("User")

    suspend fun findById(_id: String): User? =
        collection.findOneById(_id)

    fun findAllUser(): CoroutineFindPublisher<User> =
        collection.find()

    suspend fun create(user: User) {
        collection.save(user)
    }
}