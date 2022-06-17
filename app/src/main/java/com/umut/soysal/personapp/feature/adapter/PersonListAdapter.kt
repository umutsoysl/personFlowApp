package com.umut.soysal.personapp.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.umut.soysal.personapp.data.model.Person
import com.umut.soysal.personapp.databinding.LayoutItemProgressBinding
import com.umut.soysal.personapp.databinding.ListPersonItemViewBinding
import javax.inject.Inject


class PersonListAdapter @Inject constructor() : ListAdapter<Person, RecyclerView.ViewHolder>(DIFF_UTIL) {

    lateinit var onLoadData: (Boolean) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ITEM -> ContentViewHolder(ListPersonItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            LOADING -> LoadingViewHolder(LayoutItemProgressBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw IllegalArgumentException("Unknown view type")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM -> (holder as? ContentViewHolder)?.bind(getItem(position) as Person)
            LOADING -> (holder as? LoadingViewHolder)?.bind(getItem(position) as Person)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return  if(position == itemCount-1) LOADING else ITEM
    }

    inner class ContentViewHolder(val binding: ListPersonItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Person) {
            onLoadData.invoke(false)
            binding.model = item
            binding.idText.text ="(${item.id})"
            binding.executePendingBindings()
        }
    }

    inner class LoadingViewHolder(val binding: LayoutItemProgressBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Person) {
            onLoadData.invoke(true)
            binding.executePendingBindings()
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Person>() {
            override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean = oldItem == newItem
        }
        const val ITEM = 0
        const val LOADING = 1
    }
}