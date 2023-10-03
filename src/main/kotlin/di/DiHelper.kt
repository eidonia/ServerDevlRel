package di

import org.koin.core.context.startKoin

object DiHelper {

    fun initKoin() =
        startKoin {
            modules(
                appModule
            )
        }
}