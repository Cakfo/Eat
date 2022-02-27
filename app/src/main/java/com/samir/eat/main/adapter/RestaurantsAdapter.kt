package com.samir.eat.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.samir.eat.R
import com.samir.eat.databinding.RestaurantListItemBinding
import com.samir.eat.model.CommonRestaurantProperties

class RestaurantsAdapter(
    private val restaurants: ArrayList<CommonRestaurantProperties>
) :
    RecyclerView.Adapter<RestaurantsAdapter.RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding =
            RestaurantListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(restaurants[position])
    }

    override fun getItemCount() = restaurants.size

    inner class RestaurantViewHolder(private val binding: RestaurantListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listItem: CommonRestaurantProperties) {
            Glide.with(binding.root.context)
                .load(listItem.attributes?.imageUrl)
                .error(R.drawable.drawable_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.imageBackground)
            binding.run {
                textName.text = listItem.attributes?.name
                textPrice.text = "$".repeat(listItem.attributes?.priceLevel ?: 0)
                textCuisine.text = listItem.attributes?.cuisine
                textAddress.text = listItem.attributes?.address
            }
        }
    }
}