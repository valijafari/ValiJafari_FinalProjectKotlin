package com.example.valijafari_finalproject

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Search : Serializable {
    @SerializedName("Title")
    @Expose
    var title: String? = null

    @SerializedName("Year")
    @Expose
    var year: String? = null

    @SerializedName("imdbID")
    @Expose
    var imdbID: String? = null

    @SerializedName("Type")
    @Expose
    var type: String? = null

    @SerializedName("Poster")
    @Expose
    var poster: String? = null
}