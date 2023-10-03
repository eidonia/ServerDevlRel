package mapper

import model.Message
import model.Messages

class MapperMessage {

    fun toMessages(listMessage: List<Message>, page: Int = 0, pageSize: Int = listMessage.size): Messages {
        val startRange = page*pageSize
        val maxRange = if ((startRange+pageSize) - 1 < listMessage.size) {
            (startRange+pageSize) - 1
        } else {
            listMessage.lastIndex
        }
        val slicedList = listMessage.slice(IntRange(startRange, maxRange))
        return Messages(
            messageList = slicedList,
            page = page,
            pageSize = pageSize
        )
    }

}