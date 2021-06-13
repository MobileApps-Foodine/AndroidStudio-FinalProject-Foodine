package com.projectakhir.foodine.DataClass

import com.google.gson.annotations.SerializedName
import org.w3c.dom.Text
import java.util.*

class UserCondition (
    @SerializedName("weight") val userWeight: String?,
    @SerializedName("height") val userHeight: String?,
    @SerializedName("user_id") val userId: Int?
    )