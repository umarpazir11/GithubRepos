package com.search.repos.ui.data


import com.google.gson.annotations.SerializedName

data class Repos(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("total_count")
    val totalCount: Int
)