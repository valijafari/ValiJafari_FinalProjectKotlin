package com.example.valijafari_finalproject

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Rating : Serializable {
    @SerializedName("Source")
    @Expose
    var source: String? = null

    @SerializedName("Value")
    @Expose
    var value: String? = null
}