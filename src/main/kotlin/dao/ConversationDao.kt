package dao

import Database
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.FindFlow
import com.mongodb.kotlin.client.coroutine.MongoCollection
import model.Conversation


class ConversationDao(db: Database) {

    private val collection: MongoCollection<Conversation> = db.database.getCollection("Conversation")

    fun findById(id: String): FindFlow<Conversation> =
        collection.find(Filters.eq(Conversation::_id.name, id))

    fun findAllConversation() =
        collection.find()

    suspend fun create(conv: Conversation) =
        collection.insertOne(conv)
}