package dao

import Database
import model.Conversation
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineFindPublisher


class ConversationDao(db: Database) {

    private val collection: CoroutineCollection<Conversation> = db.database.getCollection("Conversation")

    suspend fun findById(_id: String): Conversation? =
        collection.findOneById(_id)

    fun findAllConversation(): CoroutineFindPublisher<Conversation> =
        collection.find()

    suspend fun create(conv: Conversation) {
        collection.save(conv)
    }
}