package com.alexina.libsqlwrapper


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexina.libsqlwrapper.databinding.RvItemBinding
import com.alexina.libsqlwrapper.entities.Bill


class AdapterBills(private val itemListener: ItemClickListener? = null) :
    ListAdapter<Bill, AdapterBills.ItemViewHolder>(DiffCallback()) {

    private val TAG = this.javaClass.simpleName

    inner class ItemViewHolder(private val binding: RvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

//        val context: Context = binding.root.context

        fun bind(item: Bill) = with(itemView) {
            binding.tvTitle.text = adapterPosition.toString()
            binding.tvSubtitle.text = item.toString()

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterBills.ItemViewHolder {
        val binding = RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterBills.ItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCurrentListChanged(
        previousList: MutableList<Bill>,
        currentList: MutableList<Bill>
    ) {
        super.onCurrentListChanged(previousList, currentList)
        notifyItemRangeChanged(0, currentList.size)
        itemListener?.onListChanged(currentList)
    }

    interface ItemClickListener {
        fun onItemClick(item: Bill)
        fun onListChanged(currentList: MutableList<Bill>)
    }

    class DiffCallback : DiffUtil.ItemCallback<Bill>() {

        override fun areItemsTheSame(oldItem: Bill, newItem: Bill): Boolean {
            return oldItem.billId == newItem.billId
        }

        override fun areContentsTheSame(oldItem: Bill, newItem: Bill): Boolean {
            return oldItem == newItem
        }
    }

}