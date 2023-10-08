package controller

import dao.ConversationDao
import dao.MessageDao
import dao.UserDao
import io.javalin.http.Handler
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import mapper.MapperMessage
import model.Conversation
import model.ConversationInfo
import model.Message
import model.User
import model.database.ConversationDb

class ConversationController(
    private val conversationDao: ConversationDao,
    private val messageDao: MessageDao,
    private val userDao: UserDao,
    private val messageMapper: MapperMessage
) {

    fun getAllConversations(): Handler =
        Handler { ctx ->
            runBlocking {
                val conversation = conversationDao.findAllConversation()
                val listConv = mutableListOf<Conversation>().also { conv ->
                    conversation.toList().forEach {
                        conv.add(
                            Conversation(
                                _id = it._id,
                                title = it.title,
                                createdBy = it.createdBy,
                                users = mutableListOf<User>().also { users ->
                                    it.users.forEach { userId ->
                                        users.add(userDao.findById(userId).first())
                                    }
                                },
                                icon = null
                            )
                        )
                    }
                }
                ctx.json(listConv)

            }
        }

    fun createConversation(): Handler =
        Handler { ctx ->
            runBlocking {
                val conversation = ctx.bodyAsClass(Conversation::class.java)
                conversationDao.create(
                    ConversationDb(
                        _id = conversation._id,
                        title = conversation.title,
                        createdBy = conversation.createdBy,
                        users = mutableListOf<String>().also { mutable ->
                            conversation.users.forEach {
                                mutable.add(it._id)
                            }
                        }.toList(),
                        icon = conversation.icon
                    )
                )
            }
        }

    fun getConversationById(): Handler =
        Handler { ctx ->
            runBlocking {
                val conv = conversationDao.findById(ctx.pathParam("conversationId")).first()
                val messages = messageDao.findByConversationId(ctx.pathParam("conversationId"))
                val page = ctx.queryParam("page")?.toInt() ?: 0
                val pageSize = ctx.queryParam("page_size")?.toInt() ?: messages.toList().size
                println(page)
                println(pageSize)
                ctx.json(
                    ConversationInfo(
                        conversation = Conversation(
                            _id = conv._id,
                            title = conv.title,
                            createdBy = conv.createdBy,
                            users = mutableListOf<User>().also { mutable ->
                                conv.users.forEach {
                                    mutable.add(userDao.findById(it).first())
                                }
                            }.toList(),
                            icon = conv.icon
                        ),
                        messages = messageMapper.toMessages(
                            mutableListOf<Message>().also { mutable ->
                                messages.toList().forEach {
                                    mutable.add(
                                        Message(
                                            _id = it._id,
                                            senderName = userDao.findById(it.senderUserId).first().username,
                                            content = it.content,
                                            senderUserId = it.senderUserId,
                                            conversationId = it.conversationId,
                                            arrivalDate = it.arrivalDate
                                        )
                                    )
                                }
                            }.toList(),
                            page,
                            pageSize
                        ),
                    )
                )
            }
        }
}