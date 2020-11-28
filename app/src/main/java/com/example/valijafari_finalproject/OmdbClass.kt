package com.example.valijafari_finalproject

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class OmdbClass : Serializable {
    @SerializedName("Search")
    @Expose
    var search: List<Search>? = null

    @SerializedName("totalResults")
    @Expose
    var totalResults: String? = null

    @SerializedName("Response")
    @Expose
    var response: String? = null
}