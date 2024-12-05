package fan.akua.protect.byteflow

abstract class PluginExtension {
    var enable: Boolean = true

    var debug: Boolean = true

    var deleteIgnoreAnnotation: Boolean = true

    var allowPackages : Array<String> = emptyArray()
}