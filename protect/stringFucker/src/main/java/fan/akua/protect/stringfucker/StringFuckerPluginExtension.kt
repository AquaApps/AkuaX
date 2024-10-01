package fan.akua.protect.stringfucker

abstract class StringFuckerPluginExtension {
    /**
     * Enable or disable the StringFucker plugin. Default is enabled.
     */
    var enable: Boolean = true
    /**
     * The algorithm implementation for String encryption and decryption.
     * It is required.
     */
    var implementation: String? = null
}