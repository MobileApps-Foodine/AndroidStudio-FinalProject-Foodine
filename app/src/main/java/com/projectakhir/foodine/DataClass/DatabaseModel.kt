package com.projectakhir.foodine.DataClass

import com.projectakhir.foodine.AllMethod.toEmpty

data class DatabaseModel(
    var id : Int?,
    var remember : Boolean?,
    var mainUser : MainUsers?
){
    constructor(mainUser: MainUsers?) :
            this( toEmpty, toEmpty, mainUser)
}
