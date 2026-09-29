package dev.pulse.core.internal

import android.util.Log
import dev.pulse.model.PulseEvent
import dev.pulse.model.PulseSink

internal object BuildTypeSinks {
    fun create(): List<PulseSink> = listOf(EventNameLinterSink())
}

/** Cảnh báo khi tên event không theo snake_case (chỉ ở debug). */
private class EventNameLinterSink : PulseSink {
    override val id: String = "debug-name-linter"

    override fun write(event: PulseEvent) {
        if (event.attributes["source"] == "timber") return   // message của Timber là câu văn tự do
        if (!SNAKE_CASE.matches(event.name)) {
            Log.w("Pulse", "Event name '${event.name}' should be snake_case (debug-only warning)")
        }
    }

    private companion object {
        val SNAKE_CASE = Regex("^[a-z][a-z0-9_]*$")
    }
}