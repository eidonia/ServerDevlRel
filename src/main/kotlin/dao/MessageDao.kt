package dao

import Database
import com.mongodb.client.result.InsertOneResult
import model.Message
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineFindPublisher
import org.litote.kmongo.eq

class MessageDao(db: Database) {

    private val collection: CoroutineCollection<Message> = db.database.getCollection("Message")

    suspend fun findById(_id: String): Message? =
        collection.findOneById(_id)

    fun findByConversationId(conversationId: String): CoroutineFindPublisher<Message> =
        collection.find(Message::conversationId eq conversationId).descendingSort(Message::arrivalDate)

    fun findAllMessage(): CoroutineFindPublisher<Message> =
        collection.find()

    suspend fun send(message: Message): InsertOneResult =
        collection.insertOne(message)

}