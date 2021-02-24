package fi.hsl.ekecheck

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import org.apache.pulsar.shade.com.google.gson.JsonArray
import org.apache.pulsar.shade.com.google.gson.JsonElement
import org.apache.pulsar.shade.com.google.gson.JsonObject
import java.io.File
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

object WebsiteController {


    fun Application.registerCustomerRoutes() {
        routing{
            customerRouting()
            static("/"){
                resources("static")
            }
        }
    }

    fun Route.customerRouting(){
        route("/api/train/{trainNumber}"){
            get{
                val jsonArray = JsonArray()
                val trainNumber = call.parameters["trainNumber"]?.toInt()
                if(trainNumber != null){
                    val trainData = DataHolder.getTrainData(trainNumber)
                    if(trainData != null && trainData.isNotEmpty()) {
                        trainData.values.forEach {
                            val jsonObject = JsonObject()
                            jsonObject.addProperty("trainNumber",it.trainNumber)
                            jsonObject.addProperty("ekeDate", LocalDate.from(Instant.ofEpochMilli(it.ekeDate)).toString())
                            jsonObject.addProperty("topicPart",it.topicPart)
                            jsonArray.add(jsonObject)
                        }
                    }
                }
                call.respondText(jsonArray.toString(), ContentType.parse("application/json" ))
            }
        }
    }
}