package br.com.projeto.conversordemoedas.ui

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import br.com.projeto.conversordemoedas.core.extensions.formatCurrency
import br.com.projeto.conversordemoedas.core.extensions.hideSoftKeyboard
import br.com.projeto.conversordemoedas.core.extensions.text
import br.com.projeto.conversordemoedas.createDialog
import br.com.projeto.conversordemoedas.createProgressDialog
import br.com.projeto.conversordemoedas.data.CoinType
import br.com.projeto.conversordemoedas.databinding.ActivityMainBinding
import br.com.projeto.conversordemoedas.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val loadingDialog by lazy { createProgressDialog() }
    val viewModel by viewModel<MainViewModel>()
    val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        configObserver()

        val adapter = createAdapterDropDown()
        setAdaperDropDown(adapter)

        configConverterButton()

    }

    private fun configConverterButton() {
        binding.converterButton.setOnClickListener {
            it.hideSoftKeyboard()
            viewModel.getExchangeValue(
                "${binding.convertFromLayoutOption.text}-${binding.convertToLayoutOption.text}"
            )
        }
    }

    private fun configObserver() {
        viewModel.state.observe(this) {
            when (it) {
                MainViewModel.State.loading -> loadingDialog.show()
                is MainViewModel.State.Error -> {
                    loadingDialog.dismiss()
                    createDialog {
                        setMessage(it.error.message)
                    }.show()
                }
                is MainViewModel.State.Success -> {
                    loadingDialog.dismiss()
                    val result = it.value.bid *  binding.textLayoutInputValue.text.toDouble()

                    val coinType = CoinType.values().find {
                        it.name == binding.convertToLayoutOption.text
                    } ?: CoinType.BRL

                    binding.textResult.text = result.formatCurrency(coinType.locale)
                }
            }
        }
    }

    private fun setAdaperDropDown(adapter: ArrayAdapter<CoinType>) {
        binding.convertFromOption.setAdapter(adapter)
        binding.convertToOption.setAdapter(adapter)
        binding.convertFromOption.setText(CoinType.BRL.name, false)
        binding.convertToOption.setText(CoinType.USD.name, false)
    }

    private fun createAdapterDropDown(): ArrayAdapter<CoinType> {
        val adapter = ArrayAdapter(
            baseContext,
            R.layout.simple_list_item_1,
            CoinType.values()
        )
        return adapter
    }
}