package dev.pulse.core

import android.content.Context
import android.util.Log
import dev.pulse.core.internal.PulseEngine
import dev.pulse.model.PulseEvent
import dev.pulse.model.PulseLevel


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
                Log.i(TAG, "Pulse SDK ${PulseVersion.NAME} initialized (build type: ${PulseVersion.BUILD_TYPE}).")
            }
        }
    }
    @JvmStatic
    @JvmOverloads
    public fun log(
        name: String,
        level: PulseLevel = PulseLevel.INFO,
        attributes: Map<String, String> = emptyMap(),
    ) {
        val current = engine ?: run {
            Log.d(TAG, "engine null")
            return
        }
        current.submit(PulseEvent(name, level, attributes, System.currentTimeMillis()))
    }

    @JvmStatic
    public fun activeSinkIds(): List<String> = engine?.sinkIds.orEmpty()

    private const val TAG = "Pulse"
}