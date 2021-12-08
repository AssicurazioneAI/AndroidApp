package com.mmk.data.source.remote.car

import com.mmk.data.mapper.DomainMapper
import com.mmk.domain.model.CarDamage
import com.squareup.moshi.Json

data class CarDamageResponse(
    @Json(name = "responseCode")
    val responseCode: Int,
    @Json(name = "errorMessage")
    val errorMessage: String = "",
    @Json(name = "data")
    val data: Data? = null
) : DomainMapper<CarDamage> {
    companion object {
        //This Empty is created for testing purposes
        val EMPTY = CarDamageResponse(
            200, data = Data(
                predictionClasses = listOf(2, 0),
                bboxList = listOf(
                    listOf(148f, 98.9633f, 288.3227f, 160.8174f),
                    listOf(85.8808f, 118.9633f, 145.3227f, 184.8174f),
                    )

            )
        )
    }


    override fun mapToDomainModel(): CarDamage {
        val width = data?.imageWidth ?: 0
        val height = data?.imageHeight ?: 0
        val damagedPartList = mutableListOf<CarDamage.DamagedPart>()
        data?.predictionClasses?.forEachIndexed { index, it ->
            val severity = when (it) {
                0 -> CarDamage.DamagedPart.SEVERITY_LIGHT
                1 -> CarDamage.DamagedPart.SEVERITY_MEDIUM
                else -> CarDamage.DamagedPart.SEVERITY_HARD
            }
            val score = data.score.getOrNull(index)
            val coordinates = data.bboxList.getOrNull(index) ?: emptyList()
            val damagedPart =
                CarDamage.DamagedPart(coordinates = coordinates, severity = severity, score = score)

            damagedPartList.add(damagedPart)
        }

        return CarDamage(width = width, height = height, damagedPartList = damagedPartList)
    }

    class Data(
        @Json(name = "bbox") val bboxList: List<List<Float>> = listOf(),
        @Json(name = "imageHeight") val imageHeight: Int = 0,
        @Json(name = "imageWidth") val imageWidth: Int = 0,
        @Json(name = "score") val score: List<Float> = listOf(),
        @Json(name = "pred_classes") val predictionClasses: List<Int> = listOf(),
    )
}