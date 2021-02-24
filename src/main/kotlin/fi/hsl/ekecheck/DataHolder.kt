package fi.hsl.ekecheck

import fi.hsl.common.transitdata.proto.Eke

object DataHolder {
    val dataMap = mutableMapOf<Int, MutableMap<String, Eke.EkeSummary>>()

    fun getTrainData(trainNumber : Int) : MutableMap<String, Eke.EkeSummary>?{
        return dataMap[trainNumber]
    }

    fun putEkeSummary(ekeSummary : Eke.EkeSummary){
        if(dataMap[ekeSummary.trainNumber] == null){
            dataMap[ekeSummary.trainNumber] = mutableMapOf()
        }
        dataMap[ekeSummary.trainNumber]!![ekeSummary.topicPart] = ekeSummary
    }
}