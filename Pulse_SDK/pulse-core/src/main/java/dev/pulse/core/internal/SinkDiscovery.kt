package dev.pulse.core.internal

import android.util.Log
import dev.pulse.model.PulseSink
import java.util.ServiceLoader

internal object SinkDiscovery {
    fun discover(): List<PulseSink> = try {
        ServiceLoader.load(PulseSink::class.java, PulseSink::class.java.classLoader).toList()
    }
    catch (e: Error) {
        Log.e("Pulse", "Failed to discover sinks: ${e.message}", e)
        emptyList()
    }
    }