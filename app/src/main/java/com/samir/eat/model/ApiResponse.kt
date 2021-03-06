package com.samir.eat.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

abstract class BaseResponse {
    val meta = Meta()
    val links = Links()
}

@Parcelize
data class RestaurantResponse(
    @SerializedName("data")
    val restaurants: ArrayList<CommonRestaurantProperties>
) : BaseResponse(), Parcelable

data class CuisinesResponse(
    @SerializedName("data")
    val cuisines: ArrayList<CommonRestaurantProperties>
) : BaseResponse()

data class NeighborhoodsResponse(
    @SerializedName("data")
    val neighborhoods: ArrayList<CommonRestaurantProperties>
) : BaseResponse()

data class RegionsResponse(
    @SerializedName("data")
    val regions: ArrayList<CommonRestaurantProperties>
) : BaseResponse()

@Parcelize
data class CommonRestaurantProperties(
    val id: String? = null,
    val type: PropertyType? = null,
    val attributes: Attributes? = null,
    @Transient
    var selected: Boolean = false
) : Parcelable

@Parcelize
data class Attributes(
    val name: String,
    @SerializedName("country_code")
    val countryCode: String? = null,
    val phone: String? = null,
    @SerializedName("image_url")
    val imageUrl: String? = null,
    @SerializedName("price_level")
    val priceLevel: PriceLevel? = null,
    @SerializedName("menu_url")
    val menuUrl: String? = null,
    val difficult: Boolean? = null,
    val cuisine: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    @SerializedName("address_line_1")
    val address: String? = null,
    @SerializedName("ratings_average")
    val ratingsAverage: Double? = null,
    @SerializedName("ratings_count")
    val ratingsCount: Int? = null
) : Parcelable

data class Meta(
    val limit: Int? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_count")
    val totalCount: Int? = null,
    @SerializedName("current_page")
    val currentPage: Int? = null
)

data class Links(
    val first: String? = null,
    val next: String? = null,
    val prev: String? = null,
    val last: String? = null
)

enum class PropertyType(val value: String) {
    @SerializedName("restaurant")
    RESTAURANT("restaurant"),
    @SerializedName("cuisine")
    CUISINE("cuisine"),
    @SerializedName("region")
    REGION("region"),
}

enum class PriceLevel(val value: Int) {
    @SerializedName("1")
    PRICE_LOW(1),
    @SerializedName("2")
    PRICE_MEDIUM(2),
    @SerializedName("3")
    PRICE_HIGH(3),
    @SerializedName("0")
    PRICE_ALL(0)
}
