package com.medhdj.gow.features.fizzbuzz

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.medhdj.gow.databinding.FizzbuzzResultItemBinding

class FizzBuzzResultAdapter :
    PagingDataAdapter<String, FizzBuzzResultAdapter.ItemViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder.create(parent)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val word = getItem(position)
        word?.let {
            holder.bind(it)
        }
    }

    // View Holders
    class ItemViewHolder(private val fizzbuzzResultItemBinding: FizzbuzzResultItemBinding) :
        RecyclerView.ViewHolder(fizzbuzzResultItemBinding.root) {
        fun bind(word: String) = with(fizzbuzzResultItemBinding) {
            root.text = word
        }

        companion object {
            fun create(parent: ViewGroup): ItemViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val fizzbuzzResultItemBinding =
                    FizzbuzzResultItemBinding.inflate(inflater, parent, false)
                return ItemViewHolder(fizzbuzzResultItemBinding)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(
                oldItem: String, newItem: String
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: String, newItem: String
            ): Boolean = oldItem == newItem
        }
    }
}
