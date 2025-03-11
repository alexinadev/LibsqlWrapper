package com.alexina.libsqlwrapper


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexina.libsqlwrapper.databinding.RvItemBinding
import com.alexina.libsqlwrapper.entities.Partner


class AdapterPartners(private val itemListener: ItemClickListener? = null) :
    ListAdapter<Partner, AdapterPartners.ItemViewHolder>(DiffCallback()) {

    private val TAG = this.javaClass.simpleName

    inner class ItemViewHolder(private val binding: RvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

//        val context: Context = binding.root.context

        fun bind(item: Partner) = with(itemView) {
            binding.tvTitle.text = adapterPosition.toString()
            binding.tvSubtitle.text = item.toString()

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterPartners.ItemViewHolder {
        val binding = RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterPartners.ItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCurrentListChanged(
        previousList: MutableList<Partner>,
        currentList: MutableList<Partner>
    ) {
        super.onCurrentListChanged(previousList, currentList)
        notifyItemRangeChanged(0, currentList.size)
        itemListener?.onListChanged(currentList)
    }

    interface ItemClickListener {
        fun onItemClick(item: Partner)
        fun onListChanged(currentList: MutableList<Partner>)
    }

    class DiffCallback : DiffUtil.ItemCallback<Partner>() {

        override fun areItemsTheSame(oldItem: Partner, newItem: Partner): Boolean {
            return oldItem.partnerId == newItem.partnerId
        }

        override fun areContentsTheSame(oldItem: Partner, newItem: Partner): Boolean {
            return oldItem == newItem
        }
    }

}