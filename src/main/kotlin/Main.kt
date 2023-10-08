import Server.app
import Server.broadcastMessage
import Server.userMap
import di.DiHelper
import io.javalin.apibuilder.ApiBuilder.*
import java.time.Duration

fun main() {
    DiHelper.initKoin()

    app.routes {
        get("/") { ctx ->
            ctx.result("hello World")
        }

        path("/conversation") {
            //get all conversations
            get(Application.conversationController::getAllConversations.call())

            //create conf
            post(Application.conversationController::createConversation.call())

            //get one conversation with _id
            path("/{conversationId}") {
                get(Application.conversationController::getConversationById.call())

                path("?page={page}&page_size={pageSize}") {
                    get(Application.conversationController::getConversationById.call())
                }
            }
        }

        path("/message") {

            //get all messages
            get(Application.messageController::getAllMessages.call())
            //send message
            post(Application.messageController::sendMessage.call())
        }

        path("/user") {
            //get all users
            get(Application.userController::getAllUsers.call())

            //create user
            post(Application.userController::createUser.call())

            //get one user with _id
            path("/{userId}") {
                get(Application.userController::getUserById.call())
            }
        }
    }

    app.ws("/chat/{userId}") { ws ->
        ws.onConnect { ctx ->
            ctx.session.idleTimeout = Duration.ofHours(2)
            val userId = ctx.pathParam("userId")
            userMap[ctx] = userId
        }
        ws.onClose {ctx ->
            userMap.remove(ctx)
        }
        ws.onMessage {
            broadcastMessage(
                senderId = "C2B3A4D5-E1F0-8A9B-4E8D-2B1A3C4D5E6F",
                userIds = listOf("C2B3A4D5-E1F0-8A9B-4E8D-2B1A3C4D5E6F", "D4E9A2C5-1A7B-4F9C-A8F2-ACE71D9FAC19"))
        }
    }
}