package dev.pulse.core

import timber.log.Timber

public class PulseTimberTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val attributes = buildMap {
            put("source", "timber")
            if (tag != null) put("tag", tag)
            if (t != null) put("error", t.javaClass.simpleName)
        }

    }
}