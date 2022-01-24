package br.com.projeto.conversordemoedas.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration

import br.com.projeto.conversordemoedas.createDialog
import br.com.projeto.conversordemoedas.createProgressDialog
import br.com.projeto.conversordemoedas.data.model.CoinContent
import br.com.projeto.conversordemoedas.databinding.ActivityHistoricBinding
import br.com.projeto.conversordemoedas.presentation.HistoricViewModel
import br.com.projeto.conversordemoedas.ui.adapters.HistoricAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoricActivity : AppCompatActivity() {
    private val loadingDialog by lazy {createProgressDialog()}
    private val viewModel by viewModel<HistoricViewModel>()
    private val binding by lazy { ActivityHistoricBinding.inflate(layoutInflater) }
    private val adapter by lazy { HistoricAdapter()}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.historicToolbar)

        lifecycle.addObserver(viewModel)

        configAdapter()

        bindObserve()
    }

    private fun configAdapter() {
        val list = listOf(
            CoinContent(1, "code", "codein", "name", 50.0, "timestamp"),
            CoinContent(1, "code", "codein", "name", 50.0, "timestamp"),
            CoinContent(1, "code", "codein", "name", 50.0, "timestamp")
        )
        adapter.submitList(list)
        binding.historicRecyclerview.adapter = adapter
        binding.historicRecyclerview.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        )
        binding.historicRecyclerview.setHasFixedSize(true)
    }

    private fun bindObserve() {
        viewModel.state.observe(this) {
            when (it) {
                is HistoricViewModel.State.loading -> loadingDialog.show()
                is HistoricViewModel.State.Error -> {
                    loadingDialog.dismiss()
                    createDialog {
                        setMessage(it.error.message)
                    }
                }
                is HistoricViewModel.State.Success -> {
                    loadingDialog.dismiss()
                    Snackbar.make(binding.root, it.values.toString(), 6000).show()
                    binding.historicText.text = it.values.toString()
                  //  adapter.submitList(it.values)
                    configAdapter()
                }
            }
        }
    }
}