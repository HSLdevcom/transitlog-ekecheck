package fi.hsl.ekecheck

import fi.hsl.common.transitdata.proto.Eke

object DataHolder {
    val dataMap = mutableMapOf<String, MutableMap<String, EkeSummaryDTO>>()

    fun getTrainData(unitNumber : String) : MutableMap<String, EkeSummaryDTO>?{
        return dataMap[unitNumber]
    }

    fun putEkeSummary(ekeSummary : EkeSummaryDTO){
        if(dataMap[ekeSummary.unitNumber] == null){
            dataMap[ekeSummary.unitNumber] = mutableMapOf()
        }
        dataMap[ekeSummary.unitNumber]!![ekeSummary.topic] = ekeSummary
    }
}