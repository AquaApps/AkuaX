package fan.akua.protect.byteflow.core

import com.android.build.api.instrumentation.InstrumentationParameters
import fan.akua.protect.byteflow.PluginExtension
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input

abstract class TransformParams : InstrumentationParameters {

    @get:Input
    abstract val debug: Property<Boolean>

    @get:Input
    abstract val deleteIgnoreAnnotation: Property<Boolean>

    @get:Input
    abstract val allowPackages: Property<Array<String>>
}

fun TransformParams.toParams(extension: PluginExtension) {
    this.debug.set(extension.debug)
    this.deleteIgnoreAnnotation.set(extension.deleteIgnoreAnnotation)
    this.allowPackages.set(extension.allowPackages)
}