package controller

import dao.MessageDao
import io.javalin.http.Handler
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import model.Message
import model.database.MessageDb

class MessageController(private val messageDao: MessageDao) {

    fun getAllMessages(): Handler =
        Handler { ctx ->
            runBlocking {
                val messages = messageDao.findAllMessage()
                ctx.json(messages.toList())
            }
        }

    fun sendMessage(): Handler =
        Handler { ctx ->
            runBlocking {
                val message = ctx.bodyAsClass(Message::class.java)
                messageDao.send(
                    MessageDb(
                        _id = message._id,
                        content = message.content,
                        senderUserId = message.senderUserId,
                        conversationId = message.conversationId,
                        arrivalDate = message.arrivalDate
                    )
                )

            }
        }
}