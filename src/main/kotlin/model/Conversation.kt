package model

data class Conversation(
    val _id: String?,
    val title: String,
    val createdBy: String,
    val users: List<User>,
    val icon: String?
)
