package controller

import dao.ConversationDao
import dao.MessageDao
import io.javalin.http.Handler
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import mapper.MapperMessage
import model.Conversation
import model.ConversationInfo

class ConversationController(
    private val conversationDao: ConversationDao,
    private val messageDao: MessageDao,
    private val messageMapper: MapperMessage
) {

    fun getAllConversations(): Handler =
        Handler { ctx ->
            runBlocking {
                val conversation = conversationDao.findAllConversation()
                ctx.json(conversation.toList())

            }
        }

    fun createConversation(): Handler =
        Handler { ctx ->
            runBlocking {
                val conversation = ctx.bodyAsClass(Conversation::class.java)
                conversationDao.create(conversation)
            }
        }

    fun getConversationById(): Handler =
        Handler { ctx ->
            runBlocking {
                val conv = conversationDao.findById(ctx.pathParam("conversationId"))
                val messages = messageDao.findByConversationId(ctx.pathParam("conversationId"))
                val page = ctx.queryParam("page")?.toInt() ?: 0
                val pageSize = ctx.queryParam("page_size")?.toInt() ?: messages.toList().size
                println(page)
                println(pageSize)
                ctx.json(
                    ConversationInfo(
                        conversation = conv.first(),
                        messages = messageMapper.toMessages(messages.toList(), page, pageSize),
                    )
                )
            }
        }
}