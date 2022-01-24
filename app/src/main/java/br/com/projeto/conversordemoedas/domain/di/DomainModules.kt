package br.com.projeto.conversordemoedas.domain.di

import br.com.projeto.conversordemoedas.domain.GetCoinContentUseCase
import br.com.projeto.conversordemoedas.domain.ListExchangeUseCase
import br.com.projeto.conversordemoedas.domain.SaveExchangeUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModules {

    fun load() {
        loadKoinModules(useCaseModules())
    }

    private fun useCaseModules(): Module {
        return module {
            factory { GetCoinContentUseCase(get()) }
            factory { ListExchangeUseCase(get()) }
            factory { SaveExchangeUseCase(get()) }
        }
    }
}