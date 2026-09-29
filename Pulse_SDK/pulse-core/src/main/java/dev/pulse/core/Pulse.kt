package dev.pulse.core

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import dev.pulse.core.internal.PulseEngine

public object Pulse {
@Volatile
private var engine: PulseEngine? = null
    @JvmStatic
    public val version: String
        get() = PulseVersion.NAME

    @JvmStatic
    public val isInitialized: Boolean
        get() = engine != null

    @JvmStatic
    @JvmOverloads
    public fun init(context: Context, config: PulseConfig = PulseConfig.Builder().build()) {
        if (engine != null) {
            Log.w(TAG, "Pulse.init() called more than once — ignored.")
            return
        }
        synchronized(this) {
            if (engine == null) {
                engine = PulseEngine.create(
                    appContext = context.applicationContext,
                    config = config,
                    sdkLabel = "${PulseVersion.NAME} (${PulseVersion.BUILD_TYPE})",
                )
            }
        }
    }

}