package dev.pulse.sample;

import java.util.Collections;

import dev.pulse.core.Pulse;
import dev.pulse.model.PulseLevel;

final class JavaCaller {
private JavaCaller() {

}
static void logFromJava(){
    Pulse.log("java_event");
    Pulse.log("java_event_with_level", PulseLevel.WARN);
    Pulse.log("java_event_full", PulseLevel.INFO,
            Collections.singletonMap("sdk_version", Pulse.getVersion()));
}
}
