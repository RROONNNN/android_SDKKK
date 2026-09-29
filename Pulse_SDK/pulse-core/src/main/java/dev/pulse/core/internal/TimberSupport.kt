package dev.pulse.core.internal

internal object TimberSupport {
    val isAvailable: Boolean by lazy {
        try {
            Class.forName("timber.log.Timber")
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }
}