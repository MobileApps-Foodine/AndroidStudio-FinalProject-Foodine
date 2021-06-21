package com.projectakhir.foodine.AllMethod

import com.projectakhir.foodine.R

enum class Gender() {
    male{
        override fun getImageDefault(): Int {
            return R.drawable.goals_male
        }
    },
    female{
        override fun getImageDefault(): Int {
            return R.drawable.goals_female
        }
    };

    abstract fun getImageDefault() : Int
    override fun toString(): String {
        return super.toString().lowercase()
    }
}