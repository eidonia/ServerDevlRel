import controller.ConversationController
import controller.MessageController
import controller.UserController
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object Application: KoinComponent {
    val userController: UserController by inject()
    val messageController: MessageController by inject()
    val conversationController: ConversationController by inject()
}