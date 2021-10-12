package fi.hsl.ekecheck

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import org.json.JSONArray
import org.json.JSONObject
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
            get {
                val jsonArray = JSONArray()
                val unitNumber = call.parameters["unitNumber"]
                if (unitNumber != null) {
                    val trainData = DataHolder.getTrainData(unitNumber)
                    if (trainData != null && trainData.isNotEmpty()) {
                        trainData.values.forEach {
                            val jsonObject = JSONObject()
                            jsonObject.put("unitNumber", it.unitNumber)
                            jsonObject.put("ekeDate", Instant.ofEpochMilli(it.timestamp).toString())
                            jsonObject.put("topicPart", it.topic)

                            jsonArray.put(jsonObject)
                        }
                    }
                }
                call.respondText(jsonArray.toString(4), ContentType.parse("application/json" ))
            }
        }
    }
}