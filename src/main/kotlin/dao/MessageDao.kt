package dao

import Database
import Server
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import com.mongodb.kotlin.client.coroutine.FindFlow
import com.mongodb.kotlin.client.coroutine.MongoCollection
import kotlinx.coroutines.flow.first
import model.Message
import model.database.MessageDb

class MessageDao(
    db: Database,
    private val conversationDao: ConversationDao
) {

    private val collection: MongoCollection<MessageDb> = db.database.getCollection<MessageDb>("Message")

    fun findByConversationId(conversationId: String): FindFlow<MessageDb> =
        collection.find(Filters.eq(Message::conversationId.name, conversationId)).sort(Sorts.descending("${Message::arrivalDate}"))

    fun findAllMessage() =
        collection.find()

    suspend fun send(message: MessageDb) {
        collection.insertOne(message)
        val listUser = conversationDao.findById(message.conversationId).first().users.toMutableList()
        Server.broadcastMessage(message.content, message.senderUserId, listUser)
    }


}