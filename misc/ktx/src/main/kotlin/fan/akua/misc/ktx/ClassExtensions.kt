@file:JvmName("ClassUtils")

package fan.akua.misc.ktx

/**
 * Get the tag (simple name) of class.
 */
inline val Any.TAG: String
    /**
     * @return The simple name of class
     */
    get() = this.javaClass.simpleName