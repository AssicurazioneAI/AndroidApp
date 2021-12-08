package com.mmk.domain.model

import java.io.Serializable


data class CarDamage(
    val width: Int = 0,
    val height: Int = 0,
    val damagedPartList: List<DamagedPart> = listOf()
) : Serializable {

    class DamagedPart(
        val coordinates: List<Float> = listOf(),
        val severity: String = "",
        val score: Float? = null
    ) : Serializable{

        companion object {
            const val SEVERITY_LIGHT="Light"
            const val SEVERITY_MEDIUM="Medium"
            const val SEVERITY_HARD="Hard"
        }

    }


}