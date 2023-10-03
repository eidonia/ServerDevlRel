package model

data class Message(
    val id: String?,
    val content: String,
    val senderUserId: String,
    val conversationId: String,
    val arrivalDate: String
)