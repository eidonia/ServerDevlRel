package model.database

data class MessageDb(
    val _id: String,
    val content: String,
    val senderUserId: String,
    val conversationId: String,
    val arrivalDate: String
)