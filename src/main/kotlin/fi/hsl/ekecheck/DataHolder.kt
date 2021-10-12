package fi.hsl.ekecheck

object DataHolder {
    private val dataMap = mutableMapOf<String, MutableMap<String, EkeSummaryDTO>>()

    fun getTrainData(unitNumber: String): Map<String, EkeSummaryDTO>? {
        return dataMap[unitNumber]?.toMap()
    }

    fun putEkeSummary(ekeSummary: EkeSummaryDTO) {
        dataMap.computeIfAbsent(ekeSummary.unitNumber) { mutableMapOf() }[ekeSummary.topic] = ekeSummary
    }
}