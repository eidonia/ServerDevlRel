package model

data class Conversation(
    val id: String?,
    val title: String,
    val createdBy: String,
    val users: List<String>,
    val icon: String?
)
