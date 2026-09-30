package dev.pulse.model

public class PulseEvent (
    public val name: String,
    public val level: PulseLevel,
    public val attributes: Map<String, String>,
    public val timestampMillis: Long,
){
    public fun withAttributes(extra: Map<String, String>): PulseEvent =
        PulseEvent(name, level, attributes + extra, timestampMillis)

    public fun toLogLine(): String = buildString {
        append('[').append(level.name).append("] ").append(name)
        if (attributes.isNotEmpty()) {
            attributes.entries.joinTo(this, prefix = " {", postfix = "}") { "${it.key}=${it.value}" }
        }
    }
    override fun toString(): String = "PulseEvent(${toLogLine()} @$timestampMillis)"

}