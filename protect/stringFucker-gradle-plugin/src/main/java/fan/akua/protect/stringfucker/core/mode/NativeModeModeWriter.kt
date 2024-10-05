package fan.akua.protect.stringfucker.core.mode

import org.objectweb.asm.MethodVisitor

class NativeModeModeWriter(fuckerClassName: String?) : IModeWriter(fuckerClassName) {
    override fun modifyStr(key: ByteArray, value: ByteArray, mv: MethodVisitor) {
        TODO("Native not impl yet.")
    }
}