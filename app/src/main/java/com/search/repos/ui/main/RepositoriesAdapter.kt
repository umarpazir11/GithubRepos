package com.search.repos.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.search.repos.databinding.ListItemRepositoriesBinding
import com.search.repos.ui.data.Item


/**
 * Adapter for the [RecyclerView] in [RepositoriesFragment].
 *
 * Easily convert-able to PagedListAdapter
 */
class RepositoriesAdapter : ListAdapter<Item, RepositoriesAdapter.ViewHolder>(DiffCallback()) {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repoItem = getItem(position)
        holder.apply {
            bind(createOnClickListener(repoItem!!), repoItem)
            itemView.tag = repoItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = ListItemRepositoriesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ViewHolder(itemView)

    }

    private fun createOnClickListener(item: Item): View.OnClickListener {
        return View.OnClickListener {
            //TODO("We can call repositories details fragment here")
        }
    }

    class ViewHolder(
        private val binding: ListItemRepositoriesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Item) {
            binding.apply {
                clickListener = listener
                itemRepo = item
                executePendingBindings()
            }
        }
    }

}

private class DiffCallback : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}