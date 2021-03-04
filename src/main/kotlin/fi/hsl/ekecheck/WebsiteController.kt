package fi.hsl.ekecheck

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import org.apache.pulsar.shade.com.google.gson.JsonArray
import org.apache.pulsar.shade.com.google.gson.JsonObject
import java.io.File
import java.time.Instant

object WebsiteController {


    fun Application.registerCustomerRoutes() {
        routing{
            customerRouting()
            static("/") {
                defaultResource("index.html")
            }
            static("js"){
                resources("js")
            }
        }
    }

    fun Route.customerRouting(){
        route("/api/train/{unitNumber}"){
            get{
                val jsonArray = JsonArray()
                val unitNumber = call.parameters["unitNumber"]
                if(unitNumber != null){
                    val trainData = DataHolder.getTrainData(unitNumber)
                    if(trainData != null && trainData.isNotEmpty()) {
                        trainData.values.forEach {
                            val jsonObject = JsonObject()
                            jsonObject.addProperty("unitNumber",it.unitNumber)
                            jsonObject.addProperty("ekeDate", Instant.ofEpochMilli(it.timestamp).toString())
                            jsonObject.addProperty("topicPart",it.topic)
                            jsonArray.add(jsonObject)
                        }
                    }
                }
                call.respondText(jsonArray.toString(), ContentType.parse("application/json" ))
            }
        }
    }
}