package dev.pulse.core.internal

import android.util.Log
import dev.pulse.model.PulseSink
import timber.log.Timber
import java.util.ServiceLoader

internal object SinkDiscovery {
    fun discover(): List<PulseSink> = try {
        ServiceLoader.load(PulseSink::class.java, PulseSink::class.java.classLoader).toList()
    }
    catch (e: Exception) {
        Timber.tag("Pulse").w(e, "Failed to load PulseSink implementations")
        emptyList()
    }
    }