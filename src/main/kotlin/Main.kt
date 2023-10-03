import di.DiHelper
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*

fun main() {
    DiHelper.initKoin()

    val app = Javalin.create().apply {
        exception(Exception::class.java) { e, _ -> e.printStackTrace() }
        error(404) { ctx -> ctx.json("Not found") }
    }.start()

    app.routes {

        get("/") { ctx ->
            ctx.result("hello World")
        }

        path("/conversation") {
            //get all conversations
            get(Application.conversationController::getAllConversations.call())

            //create conf
            post(Application.conversationController::createConversation.call())

            //get one conversation with id
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

            //get one user with id
            path("/{userId}") {
                get(Application.userController::getUserById.call())
            }
        }
    }
}