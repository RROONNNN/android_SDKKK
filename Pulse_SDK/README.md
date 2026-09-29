pulse-sdk/
├── settings.gradle.kts
├── build.gradle.kts
├── gradle.properties
├── gradle/
│   ├── libs.versions.toml
│   └── wrapper/gradle-wrapper.properties      (+ gradle-wrapper.jar)
├── gradlew, gradlew.bat
├── pulse-model/                                ← JVM library → JAR
│   ├── build.gradle.kts
│   └── src/
│       ├── main/kotlin/dev/pulse/model/
│       │   ├── PulseLevel.kt
│       │   ├── PulseEvent.kt
│       │   └── PulseSink.kt
│       └── test/kotlin/dev/pulse/model/
│           └── PulseEventTest.kt
├── pulse-core/                                 ← Android library → AAR
│   ├── build.gradle.kts
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── res/values/strings.xml
│       │   └── kotlin/dev/pulse/core/
│       │       ├── Pulse.kt
│       │       ├── PulseConfig.kt
│       │       ├── PulseTimberTree.kt
│       │       └── internal/
│       │           ├── PulseEngine.kt
│       │           ├── SinkDiscovery.kt
│       │           └── TimberSupport.kt
│       ├── debug/kotlin/dev/pulse/core/internal/BuildTypeSinks.kt
│       └── release/kotlin/dev/pulse/core/internal/BuildTypeSinks.kt
├── pulse-sink-logcat/                          ← Android library → AAR
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── kotlin/dev/pulse/sink/logcat/LogcatSink.kt
│       └── resources/META-INF/services/dev.pulse.model.PulseSink
└── sample/                                     ← App → APK
├── build.gradle.kts
└── src/
├── main/
│   ├── AndroidManifest.xml
│   ├── kotlin/dev/pulse/sample/
│   │   ├── SampleApp.kt
│   │   └── MainActivity.kt
│   └── java/dev/pulse/sample/JavaCaller.java
├── staging/kotlin/dev/pulse/sample/Environment.kt
└── production/kotlin/dev/pulse/sample/Environment.kt