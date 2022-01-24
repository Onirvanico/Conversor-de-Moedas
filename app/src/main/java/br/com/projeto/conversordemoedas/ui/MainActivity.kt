package br.com.projeto.conversordemoedas.ui

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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

        setSupportActionBar(binding.toolbarMainActivity)
        configObserver()

        val adapter = createAdapterDropDown()
        setAdaperDropDown(adapter)

        configConverterButton()

        binding.saveExchangeButton.setOnClickListener {
            val value = viewModel.state.value
            (value as? MainViewModel.State.Success)?.let {
                viewModel.saveExchange(it.value)
            }
        }

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

                    val coinType = CoinType.getByName(binding.convertToLayoutOption.text)

                    binding.textResult.text = result.formatCurrency(coinType.locale)
                }
                is MainViewModel.State.Saved -> {
                    loadingDialog.dismiss()
                    createDialog { setMessage("Êxito ao salvar o valor") }.show()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(br.com.projeto.conversordemoedas.R.menu.historic_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == br.com.projeto.conversordemoedas.R.id.historic_option_menu)
            startActivity(Intent(this, HistoricActivity::class.java))

        return super.onOptionsItemSelected(item)
    }
}