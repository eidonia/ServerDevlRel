package controller

import dao.UserDao
import io.javalin.http.Handler
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import model.User

class UserController(private val userDao: UserDao) {

    fun getAllUsers(): Handler =
        Handler { ctx ->
            runBlocking {
                val user = userDao.findAllUser()
                ctx.json(user.toList())
            }

        }

    fun getUserById(): Handler =
        Handler { ctx ->
            runBlocking {
                val user = userDao.findById(ctx.pathParam("userId"))

                user?.let {
                    ctx.json(it)
                } ?: ctx.status(404)
            }
        }

    fun createUser(): Handler =
        Handler { ctx ->
            runBlocking {
                val user = ctx.bodyAsClass(User::class.java)
                userDao.create(user)
            }
        }
}