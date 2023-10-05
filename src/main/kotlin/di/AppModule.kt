package di

import Application
import Database
import Server
import controller.ConversationController
import controller.MessageController
import controller.UserController
import dao.ConversationDao
import dao.MessageDao
import dao.UserDao
import mapper.MapperMessage
import org.koin.dsl.module

val appModule = module {

    single { Database() }
    single { Application }

    single { ConversationDao(get()) }
    single { UserDao(get()) }
    single { MessageDao(get(), get()) }

    factory { UserController(get()) }
    factory { ConversationController(get(), get(), get()) }
    factory { MessageController(get()) }
    factory { MapperMessage() }
}