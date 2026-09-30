package dev.pulse.sink.logcat

import android.util.Log
import dev.pulse.model.PulseEvent
import dev.pulse.model.PulseSink

    public class LogcatSink : PulseSink {
        override val id: String = "logcat"

        override fun write(event: PulseEvent) {
            Log.println(event.level.priority, "PulseEvent", event.toLogLine())
        }
    }
