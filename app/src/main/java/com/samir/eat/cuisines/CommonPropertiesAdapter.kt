package com.samir.eat.cuisines

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.samir.eat.R
import com.samir.eat.databinding.CommonListItemBinding
import com.samir.eat.model.CommonRestaurantProperties
import com.samir.eat.util.gone
import com.samir.eat.util.visible

class CommonPropertiesAdapter : RecyclerView.Adapter<CommonPropertiesAdapter.CuisineViewHolder>() {

    private val cuisines = arrayListOf<CommonRestaurantProperties>()
    var selectedItem: CommonRestaurantProperties? = null

    private var previousItem = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuisineViewHolder {
        val binding =
            CommonListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CuisineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CuisineViewHolder, position: Int) {
        holder.bind(cuisines[position])
    }

    override fun getItemCount() = cuisines.size

    fun updateData(data: ArrayList<CommonRestaurantProperties>) {
        val positionStart = if (cuisines.isEmpty()) 0 else cuisines.size - 1
        cuisines.addAll(data)
        notifyItemRangeInserted(positionStart, data.size)
    }

    inner class CuisineViewHolder(
        private val binding: CommonListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listItem: CommonRestaurantProperties) {
            val context = binding.root.context
            binding.textCuisine.text = listItem.attributes?.name
            if (listItem.selected) {
                binding.run {
                    textCuisine.setTextColor(ContextCompat.getColor(context, R.color.green))
                    imageSelected.visible()
                }

            } else {
                binding.run {
                    textCuisine.setTextColor(ContextCompat.getColor(context, R.color.dark_gray))
                    imageSelected.gone()
                }
            }
            binding.root.setOnClickListener {
                handleItemState(listItem)
            }
        }

        private fun handleItemState(listItem: CommonRestaurantProperties) {
            if (adapterPosition == previousItem) {
                if (listItem.selected) {
                    listItem.selected = false
                    selectedItem = null
                } else {
                    listItem.selected = true
                    selectedItem = listItem
                }
            } else {
                listItem.selected = true
                cuisines[previousItem].selected = false
                selectedItem = listItem
            }
            notifyItemChanged(adapterPosition)
            notifyItemChanged(previousItem)
            previousItem = adapterPosition
        }
    }
}