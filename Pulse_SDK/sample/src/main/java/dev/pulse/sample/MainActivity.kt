package dev.pulse.sample

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import dev.pulse.core.Pulse
import dev.pulse.model.PulseLevel
import timber.log.Timber

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val info = TextView(this).apply {
            textSize = 16f
            text = buildString {
                appendLine("Pulse SDK ${Pulse.version}")
                appendLine("Variant: ${BuildConfig.FLAVOR} / ${BuildConfig.BUILD_TYPE}")
                appendLine("Application ID: ${BuildConfig.APPLICATION_ID}")
                appendLine("Environment: ${Environment.LABEL} (${Environment.API_BASE_URL})")
                appendLine("Active sinks: ${Pulse.activeSinkIds()}")
                appendLine()
                append("Mở Logcat, lọc tag: Pulse | PulseEvent")
            }
        }

        val kotlinButton = button("Pulse.log(\"button_clicked\")") {
            Pulse.log("button_clicked", PulseLevel.INFO, mapOf("screen" to "main"))
        }
        val badNameButton = button("Pulse.log(\"Bad Event Name\")") {
            Pulse.log("Bad Event Name")       // ở debug, EventNameLinterSink sẽ cảnh báo
        }
        val timberButton = button("Timber.w(...)") {
            Timber.w("Hello from Timber")
        }
        val javaButton = button("Gọi SDK từ Java") {
            JavaCaller.logFromJava()
        }
        val getListSinksButton = button("Lấy danh sách sink đang hoạt động") {
            val activeSinks = Pulse.activeSinkIds()
            Log.i("Pulse", "Active sinks: $activeSinks")
        }

        setContentView(
            LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                fitsSystemWindows = true
                setPadding(48, 48, 48, 48)
                listOf(info, kotlinButton, badNameButton, timberButton, javaButton,getListSinksButton).forEach(::addView)
            },
        )
    }

    private fun button(label: String, onClick: () -> Unit) = Button(this).apply {
        text = label
        isAllCaps = false
        setOnClickListener { onClick() }
    }
}