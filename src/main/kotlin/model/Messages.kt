package model

data class Messages(
    val messageList: List<Message>,
    val page: Int,
    val pageSize: Int
)