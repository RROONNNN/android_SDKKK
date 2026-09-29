package dev.pulse.model

public enum class PulseLevel(public val priority: Int) {
    VERBOSE(2),
    DEBUG(3),
    INFO(4),
    WARN(5),
    ERROR(6);

    public companion object {
        public fun fromPriority(priority: Int) : PulseLevel =entries.lastOrNull { it.priority <= priority } ?: VERBOSE
    }
}