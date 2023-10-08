package dao

import Database
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.FindFlow
import com.mongodb.kotlin.client.coroutine.MongoCollection
import model.User

class UserDao(db: Database) {

    private val collection: MongoCollection<User> = db.database.getCollection("User")

    fun findById(id: String): FindFlow<User> =
        collection.find(Filters.eq(User::_id.name, id))

    fun findAllUser() =
        collection.find()

    suspend fun create(user: User) =
        collection.insertOne(user)
}