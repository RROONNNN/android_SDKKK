package dev.pulse.model

import org.junit.Assert.assertEquals
import org.junit.Test

class PulseEventTest {
    @Test
    fun toLogLine_withoutAttributes() {
        val event = PulseEvent("app_started", PulseLevel.INFO, emptyMap(), 0L)
        assertEquals("[INFO] app_started", event.toLogLine())
    }
    @Test
    fun toLogLine_withAttributes() {
        val event = PulseEvent("click", PulseLevel.DEBUG, mapOf("screen" to "main"), 0L)
            .withAttributes(mapOf("env" to "qa"))
        assertEquals("[DEBUG] click {screen=main, env=qa}", event.toLogLine())
    }
    @Test
    fun fromPriority_mapsAndroidLogPriorities() {
        assertEquals(PulseLevel.VERBOSE, PulseLevel.fromPriority(2))
        assertEquals(PulseLevel.WARN, PulseLevel.fromPriority(5))
        assertEquals(PulseLevel.ERROR, PulseLevel.fromPriority(7))
        assertEquals(PulseLevel.VERBOSE, PulseLevel.fromPriority(0))
    }
}