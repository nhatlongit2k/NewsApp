package com.example.newsapp.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class News(
    @SerializedName("status")
    @Expose
    var status: String,

    @SerializedName("totalResults")
    @Expose
    var totalResults: Int,

    @SerializedName("articles")
    @Expose
    var articles: ArrayList<Article>
) {

}