import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

class Database {
    private val client = KMongo.createClient().coroutine
    val database = client.getDatabase("DevRelIo")
}