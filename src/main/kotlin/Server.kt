import io.javalin.Javalin
import io.javalin.websocket.WsContext
import java.util.concurrent.ConcurrentHashMap

object Server {

    val userMap: ConcurrentHashMap<WsContext, String> = ConcurrentHashMap()

    val app: Javalin = Javalin.create().apply {
        exception(Exception::class.java) { e, _ -> e.printStackTrace() }
        error(404) { ctx -> ctx.json("Not found") }
    }.start()

    fun broadcastMessage(content: String = "", senderId: String, userIds: List<String>) {
        userMap.keys.filter { ws ->
            userIds
                .filter { it != senderId }
                .contains(userMap[ws])
        }.forEach { session ->
            session.send(if (content != "") {
                "Nouveau message reçu"
            } else {
                "Plop"
            })
        }
    }
}