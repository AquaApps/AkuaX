package fan.akua.protect.stringfucker.core

import com.android.build.api.instrumentation.InstrumentationParameters
import fan.akua.protect.stringfucker.FuckMode
import fan.akua.protect.stringfucker.PluginExtension
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input

abstract class TransformParams : InstrumentationParameters {

    @get:Input
    abstract val fuckerClassName: Property<String>

    @get:Input
    abstract val debug: Property<Boolean>

    @get:Input
    abstract val mode: Property<FuckMode>

    @get:Input
    abstract val implClass: Property<Any>

    @get:Input
    abstract val allowPackages: Property<Array<String>>
}

fun TransformParams.toParams(extension: PluginExtension) {
    this.fuckerClassName.set("${CodeGenerateTask.PLUGIN_PACKAGE_NAME}.${CodeGenerateTask.PLUGIN_CLASS_NAME}")
    this.mode.set(extension.mode)
    this.debug.set(extension.debug)
    this.implClass.set(extension.implementation)
    this.allowPackages.set(extension.allowPackages)
}