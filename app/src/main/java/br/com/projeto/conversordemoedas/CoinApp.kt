package br.com.projeto.conversordemoedas

import android.app.Application
import android.app.Presentation
import br.com.projeto.conversordemoedas.data.dependency_injection.DataModules
import br.com.projeto.conversordemoedas.domain.di.DomainModules
import br.com.projeto.conversordemoedas.presentation.di.PresentationModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


class CoinApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CoinApp)
        }

        DataModules.load()
        DomainModules.load()
        PresentationModules.load()
    }
}