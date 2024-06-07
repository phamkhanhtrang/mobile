package com.example.infor.data

data class UseData (
    var userId: String?="",
    var name: String?="",
    var number: String?="",
    var note: String?="",
    var imageUrl: String?="",
){
    fun toMap()= mapOf(
        "userID" to userId,
        "name" to name,
        "number" to number,
        "note" to note,
        "imageUrl" to imageUrl
    )
}

data class ChatData(
    val chatID: String?="",
    val user1: ChatUser = ChatUser(),
    val user2: ChatUser=ChatUser(),

    )
data class ChatUser(
    val userId: String?="",
    val name: String?="",
    val imageUrl: String?="",
    val number: String?="",
)
data class Message(
    var sendBy:String?="",
    val message: String?="",
    val timestamp: String?=""
)
data class PostData(
    val postID: String?="",
    val user: ChatUser = ChatUser(),
    val content: String?="",
    val imageUrl: List<String>? = null
)
data class Comment(
    val username : String?="",
    val imageUrl: String?="",
    val comment: String?="",
    val timestamp: String?=""
)
data class StoryData(val user: ChatUser = ChatUser(),
    val imageUrl: String?= "",
    val timestamp: Long?=null
)