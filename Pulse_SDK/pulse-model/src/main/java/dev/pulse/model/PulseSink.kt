package dev.pulse.model

public interface PulseSink {
    public val id: String

    public fun write(event: PulseEvent)
}