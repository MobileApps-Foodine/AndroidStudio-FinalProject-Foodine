package com.projectakhir.foodine.DataClass

import com.google.gson.annotations.SerializedName
import org.w3c.dom.Text
import java.util.*

class UserConditions (
    @SerializedName("weight") var userWeight: Float?,
    @SerializedName("height") var userHeight: Float?,
    @SerializedName("user_id") var userId: Int?
    )