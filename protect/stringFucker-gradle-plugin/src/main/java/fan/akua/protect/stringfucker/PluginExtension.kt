package fan.akua.protect.stringfucker

abstract class PluginExtension {
    var enable: Boolean = true

    var debug: Boolean = true

    var deleteIgnoreAnnotation: Boolean = true

    var implementation: IStringFucker? = null

    var mode: FuckMode = FuckMode.Java

    var allowPackages : Array<String> = emptyArray()
}