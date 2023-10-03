package model

data class User(
    var id: String?,
    var username: String,
    var personalInformationsId: PersonalInformations?,
    var role: String
)