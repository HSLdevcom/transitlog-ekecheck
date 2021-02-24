package fi.hsl.ekecheck

import fi.hsl.common.pulsar.IMessageHandler
import fi.hsl.common.pulsar.PulsarApplicationContext
import fi.hsl.common.transitdata.TransitdataProperties
import fi.hsl.common.transitdata.TransitdataSchema
import fi.hsl.common.transitdata.proto.Eke
import mu.KotlinLogging
import org.apache.pulsar.client.api.Consumer
import org.apache.pulsar.client.api.Message
import org.apache.pulsar.client.api.MessageId


class MessageHandler(val context: PulsarApplicationContext) : IMessageHandler {

    private val consumer: Consumer<ByteArray> = context.consumer!!

    private val log = KotlinLogging.logger {}
    override fun handleMessage(received: Message<Any>) {
        try {
            if (TransitdataSchema.hasProtobufSchema(received, TransitdataProperties.ProtobufSchema.EkeSummary)) {
                val ekeSummary = Eke.EkeSummary.parseFrom(received.data)
                DataHolder.putEkeSummary(ekeSummary)
            }
            ack(received.messageId)
        }
        catch(e : Exception){
            log.error("Exception while handling message", e)
        }
    }

    private fun ack(received: MessageId) {
        consumer.acknowledgeAsync(received)
            .exceptionally { throwable ->
                log.error("Failed to ack Pulsar message", throwable)
                null
            }
            .thenRun {}
    }
}