package br.com.projeto.conversordemoedas.presentation.di

import br.com.projeto.conversordemoedas.presentation.HistoricViewModel
import br.com.projeto.conversordemoedas.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModules {

    fun load() {
        loadKoinModules(viewModelModules())
    }

    private fun viewModelModules(): Module {
        return module {
            viewModel { MainViewModel(get(), get()) }
            viewModel { HistoricViewModel(get()) }
        }
    }
}