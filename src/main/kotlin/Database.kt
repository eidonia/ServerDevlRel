
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.kotlin.client.coroutine.MongoClient

class Database {
    private val client = MongoClient.create(
        MongoClientSettings.builder()
            .applyConnectionString(ConnectionString("mongodb://mongoservice:27017"))//mongodb://localhost:27017
            .build()
    )

    val database = client.getDatabase("DevRelIo")
}