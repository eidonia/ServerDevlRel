package dao

import Database
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.FindFlow
import com.mongodb.kotlin.client.coroutine.MongoCollection
import model.Conversation
import model.database.ConversationDb


class ConversationDao(db: Database) {

    private val collection: MongoCollection<ConversationDb> = db.database.getCollection("Conversation")

    fun findById(id: String): FindFlow<ConversationDb> =
        collection.find(Filters.eq(ConversationDb::_id.name, id))

    fun findAllConversation() =
        collection.find()

    suspend fun create(conv: ConversationDb) =
        collection.insertOne(conv)
}