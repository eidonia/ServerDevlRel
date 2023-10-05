package model

data class Conversation(
    val _id: String?,
    val title: String,
    val createdBy: String,
    val users: List<String>,
    val icon: String?
)
