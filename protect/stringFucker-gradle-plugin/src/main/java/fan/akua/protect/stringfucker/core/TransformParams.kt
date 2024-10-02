package fan.akua.protect.stringfucker.core

import com.android.build.api.instrumentation.InstrumentationParameters
import fan.akua.protect.stringfucker.StorageMode
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

abstract class TransformParams : InstrumentationParameters {

    @get:Input
    abstract val fuckerClassName: Property<String>

    @get:Input
    abstract val debug: Property<Boolean>

    @get:Input
    abstract val mode: Property<StorageMode>

    @get:Input
    abstract val implClass: Property<Any>
}