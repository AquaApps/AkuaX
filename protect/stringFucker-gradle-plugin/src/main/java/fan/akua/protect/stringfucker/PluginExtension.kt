package fan.akua.protect.stringfucker

abstract class PluginExtension {
    /**
     * Enable or disable the StringFucker plugin. Default is enabled.
     */
    var enable: Boolean = true

    /**
     * Enable or disable the StringFucker Log.
     */
    var debug: Boolean = true

    /**
     * The algorithm implementation for String encryption and decryption.
     * It is required.
     */
    var implementation: IStringFucker? = null

    /**
     * How the store string, default is in java.
     */
    var mode: FuckMode = FuckMode.Java

    /**
     * The java packages will be applied. Default is effect on all packages.
     */
    var allowPackages : Array<String> = emptyArray()
}