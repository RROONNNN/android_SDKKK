package dev.pulse.core.internal

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import dev.pulse.core.PulseConfig
import dev.pulse.model.PulseEvent
import dev.pulse.model.PulseSink
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import timber.log.Timber

internal class PulseEngine private constructor(
    private val config: PulseConfig,
    private val sinks: List<PulseSink>
) {

private val scope = CoroutineScope(
    SupervisorJob() + Dispatchers.IO + CoroutineExceptionHandler { _, e ->
        Log.w(TAG, "Coroutine exception in PulseEngine: ${e.message}", e)
    }
)

    private val queue = Channel<PulseEvent>(capacity = 256, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val sinkIds: List<String> = sinks.map { it.id }

    init {
        scope.launch {
            for (event in queue) dispatch(event)
        }
    }
    fun submit(event: PulseEvent) {
        if (event.level.priority < config.minLevel.priority) return
        queue.trySend(event)
    }

    private fun dispatch(event: PulseEvent) {
        val enriched =  event.withAttributes(mapOf("env" to config.environment))
        for (sink in sinks) {
            try {
                sink.write(enriched)
            }
            catch (e: Exception) {
                Timber.tag(TAG).w(e, "Sink '${sink.id}' failed")
            }
        }
    }
companion object {
    private const val TAG = "Pulse"

    fun create(appContext: Context, config: PulseConfig, sdkLabel: String): PulseEngine{
        val sinks = SinkDiscovery.discover() +  BuildTypeSinks.create()
        Log.i(
            TAG,
            "Pulse $sdkLabel host=${appContext.packageName} " +
                    "sinks=${sinks.map { it.id }} timberOnClasspath=${TimberSupport.isAvailable}",
        )
        return PulseEngine(config, sinks)
    }
}

}