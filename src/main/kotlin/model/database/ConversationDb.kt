package model.database

import model.User

data class ConversationDb(
    val _id: String?,
    val title: String,
    val createdBy: String,
    val users: List<String>,
    val icon: String?
)