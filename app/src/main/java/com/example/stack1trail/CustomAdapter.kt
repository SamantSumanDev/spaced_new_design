package com.example.stack1trail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView


class CustomAdapter(private val itemList: List<Item>, private val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<CustomAdapter.ItemViewHolder>() {

    private var selectedItemPosition = RecyclerView.NO_POSITION

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: AppCompatTextView = itemView.findViewById(R.id.textView)

        init {
            itemView.setOnClickListener {
                val clickedItem = itemList[adapterPosition]
                onItemClick.invoke(clickedItem.text)
                selectedItemPosition = adapterPosition
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_depart_time, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.textView.text = currentItem.text

        // Set the background based on the selection state
        if (position == selectedItemPosition) {
            holder.itemView.setBackgroundResource(R.drawable.selected_item_bg)
        } else {
            holder.itemView.setBackgroundResource(R.drawable.depart_time_bg)
        }
    }

    override fun getItemCount() = itemList.size

    data class Item(val text: String)
}
