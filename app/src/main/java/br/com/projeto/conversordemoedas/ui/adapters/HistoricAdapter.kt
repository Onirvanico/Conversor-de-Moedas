package br.com.projeto.conversordemoedas.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.projeto.conversordemoedas.core.extensions.formatCurrency
import br.com.projeto.conversordemoedas.data.CoinType
import br.com.projeto.conversordemoedas.data.model.CoinContent
import br.com.projeto.conversordemoedas.databinding.ExchangeHistoricItemBinding

class HistoricAdapter() :
    ListAdapter<CoinContent, HistoricAdapter.HistoricViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ExchangeHistoricItemBinding.inflate(inflater, parent, false)

        return HistoricViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoricViewHolder, position: Int) {
        holder.bindViews(getItem(position))
    }


    class HistoricViewHolder(
        private val binding: ExchangeHistoricItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindViews(content: CoinContent) {
            val coin = CoinType.getByName(content.codein)
            Log.i("COIN", content.name)
            binding.exchangeResearchItem.text = content.name
            binding.exchangeValueItem.text = content.bid.formatCurrency(coin.locale)
        }
    }



}

class DiffCallback : DiffUtil.ItemCallback<CoinContent>() {
    override fun areItemsTheSame(oldItem: CoinContent, newItem: CoinContent) = oldItem == newItem
    override fun areContentsTheSame(oldItem: CoinContent, newItem: CoinContent) = oldItem.id == newItem.id

}