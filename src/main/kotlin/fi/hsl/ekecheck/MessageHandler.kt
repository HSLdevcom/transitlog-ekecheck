package fi.hsl.ekecheck

import fi.hsl.common.pulsar.IMessageHandler
import fi.hsl.common.pulsar.PulsarApplicationContext
import fi.hsl.common.transitdata.TransitdataProperties
import fi.hsl.common.transitdata.TransitdataSchema
import fi.hsl.common.transitdata.proto.Eke
import mu.KotlinLogging
import org.apache.pulsar.client.api.Message


class MessageHandler(val context: PulsarApplicationContext) : IMessageHandler {

    private val log = KotlinLogging.logger {}
    override fun handleMessage(received: Message<Any>) {
        try {
            if (TransitdataSchema.hasProtobufSchema(received, TransitdataProperties.ProtobufSchema.Eke)) {
                val ekeSummary = Eke.EkeSummary.parseFrom(received.data)
                DataHolder.putEkeSummary(ekeSummary)
            }
        }
        catch(e : Exception){
            log.error("Exception while handling message", e)
        }
    }
}