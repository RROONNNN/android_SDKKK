package dev.pulse.sample

import android.app.Application
import dev.pulse.core.Pulse
import dev.pulse.core.PulseConfig
import dev.pulse.core.PulseTimberTree
import dev.pulse.model.PulseLevel
import timber.log.Timber

class SampleApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Pulse.init(
            this,
            PulseConfig.Builder()
                .environment(BuildConfig.PULSE_ENV)
                .minLevel(if (BuildConfig.DEBUG) PulseLevel.VERBOSE else PulseLevel.INFO)
                .build()
        )
        Timber.plant(PulseTimberTree())

        Pulse.log("app_started", attributes = mapOf("api" to Environment.API_BASE_URL))
    }
}