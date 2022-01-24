package br.com.projeto.conversordemoedas.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.projeto.conversordemoedas.core.exceptions.RemoteException
import br.com.projeto.conversordemoedas.data.model.CoinContent
import br.com.projeto.conversordemoedas.domain.GetCoinContentUseCase
import br.com.projeto.conversordemoedas.domain.SaveExchangeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(
    private val saveExchangeUseCase: SaveExchangeUseCase,
    private val getCoinContentUseCase: GetCoinContentUseCase )
    : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun getExchangeValue(coins: String) {
        viewModelScope.launch {
            getCoinContentUseCase(coins)
                .flowOn(Dispatchers.Main)
                .onStart {
                    _state.value = State.loading
                }
                .catch {

                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.Success(it)
                }
        }
    }

    fun saveExchange(value: CoinContent) {
        viewModelScope.launch {
            saveExchangeUseCase(value)
                .flowOn(Dispatchers.Main)
                .onStart {
                    _state.value = State.loading
                }
                .catch {

                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.Saved
                }
        }
    }

    sealed class State {
        object loading: State()
        object Saved: State()

        data class Success(val value: CoinContent): State()
        data class Error(val error: Throwable): State()
    }
}