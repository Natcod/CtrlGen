package com.example.ctrlgen.model

import com.google.gson.*
import java.lang.reflect.Type

data class SensorData(
    val oilLevel: List<Float>,
    val fuelLevel: List<Float>,
    val temperature: List<Float>,
    val current: Float,
    val voltage: Float,
    val pressure: Float,
    val powerRating: Float,
    val isPlaceholder: Boolean = false // New flag to indicate placeholder data
)

class SensorDataDeserializer : JsonDeserializer<SensorData> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): SensorData {
        val jsonObject = json.asJsonObject

        val oilLevel = parseListOrSingle(jsonObject["oilLevel"])
        val fuelLevel = parseListOrSingle(jsonObject["fuelLevel"])
        val temperature = parseListOrSingle(jsonObject["temperature"])

        val current = jsonObject.get("current").asFloat
        val voltage = jsonObject.get("voltage").asFloat
        val pressure = jsonObject.get("pressure").asFloat
        val powerRating = jsonObject.get("powerRating").asFloat

        return SensorData(
            oilLevel = oilLevel,
            fuelLevel = fuelLevel,
            temperature = temperature,
            current = current,
            voltage = voltage,
            pressure = pressure,
            powerRating = powerRating,
            isPlaceholder = false
        )
    }

    private fun parseListOrSingle(jsonElement: JsonElement): List<Float> {
        return when {
            jsonElement.isJsonArray -> {
                jsonElement.asJsonArray.map { it.asFloat }
            }
            jsonElement.isJsonPrimitive -> {
                listOf(jsonElement.asFloat)
            }
            else -> emptyList()
        }
    }
}

