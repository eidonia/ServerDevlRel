package controller

import dao.MessageDao
import io.javalin.http.Handler
import kotlinx.coroutines.runBlocking
import model.Message

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
                messageDao.send(message)
            }
        }
}