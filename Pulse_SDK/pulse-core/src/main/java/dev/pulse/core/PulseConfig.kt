package dev.pulse.core

import dev.pulse.model.PulseLevel

public class PulseConfig private constructor(
    public val minLevel: PulseLevel,
    public val environment: String,
){
    public class Builder {
        private var minLevel: PulseLevel = PulseLevel.DEBUG
        private var environment: String = "production"

        public fun minLevel(level: PulseLevel): Builder = apply { minLevel = level }

        public fun environment(value: String): Builder = apply { environment = value }

        public fun build(): PulseConfig = PulseConfig(minLevel, environment)
    }
}
