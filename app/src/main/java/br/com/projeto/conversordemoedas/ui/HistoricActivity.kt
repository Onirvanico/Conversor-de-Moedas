package br.com.projeto.conversordemoedas.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewStub
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.projeto.conversordemoedas.R
import br.com.projeto.conversordemoedas.createDialog
import br.com.projeto.conversordemoedas.createProgressDialog
import br.com.projeto.conversordemoedas.data.model.CoinContent
import br.com.projeto.conversordemoedas.databinding.ActivityHistoricBinding
import br.com.projeto.conversordemoedas.presentation.HistoricViewModel
import br.com.projeto.conversordemoedas.ui.adapters.HistoricAdapter
import com.androidplot.xy.SimpleXYSeries
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoricActivity : AppCompatActivity() {
    private val loadingDialog by lazy {createProgressDialog()}
    private val viewModel by viewModel<HistoricViewModel>()
    private val binding by lazy { ActivityHistoricBinding.inflate(layoutInflater) }
    private val adapter by lazy { HistoricAdapter()}
    lateinit var stub: ViewStub
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.historicToolbar)

        stub = binding.root.findViewById(br.com.projeto.conversordemoedas.R.id.stub_no_list)

        lifecycle.addObserver(viewModel)

        configToolbarNavigation()


        bindObserve()
    }

    private fun configToolbarNavigation() {
        binding.historicToolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun configAdapter(list: List<CoinContent>) {

        adapter.submitList(list)
        binding.historicRecyclerview.adapter = adapter
        binding.historicRecyclerview.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        )
        binding.historicRecyclerview.setHasFixedSize(true)
    }

    private fun bindObserve() {
        viewModel.state.observe(this) {
            retrieveList(it)
        }
    }

    private fun retrieveList(it: HistoricViewModel.State?) {
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
                if (it.values.size == 0) {
                    showEmptyList()
                } else {
                    configAdapter(it.values)
                }
            }
        }
    }

    private fun showEmptyList() {
        stub.visibility = View.VISIBLE
        binding.historicRecyclerview.visibility = View.GONE
    }
}