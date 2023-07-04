package com.example.arcrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.arcrecyclerview.databinding.ItemCardBinding

class DataAdapter(val onItemClick: (View) -> Unit) : RecyclerView.Adapter<DataViewHolder>() {

    private val itemList = listOf(
        R.drawable.item1, R.drawable.item2, R.drawable.item3, R.drawable.item4, R.drawable.item5,
        R.drawable.item6, R.drawable.item7, R.drawable.item8, R.drawable.item9, R.drawable.item10,
    )


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(itemList[position], onItemClick)
    }

}

class DataViewHolder(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Int, onItemClick: (View) -> Unit) {
        binding.image.setImageResource(item)
        binding.root.setOnClickListener {
            onItemClick(it)
        }
    }
}