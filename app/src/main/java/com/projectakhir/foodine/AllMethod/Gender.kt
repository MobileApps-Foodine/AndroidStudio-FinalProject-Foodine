package com.projectakhir.foodine.AllMethod

enum class Gender() {
    male{
        override fun getImageDefault(): Int {
            return maleImage
        }
    },
    female{
        override fun getImageDefault(): Int {
            return femaleImage
        }
    };

    abstract fun getImageDefault() : Int
    override fun toString(): String {
        return super.toString().lowercase()
    }
}