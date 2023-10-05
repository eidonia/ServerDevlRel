package model

data class User(
    var _id: String?,
    var username: String,
    var personalInformationsId: PersonalInformations?,
    var role: String
)