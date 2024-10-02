package fan.akua.protect.stringfucker.core.mode

import org.objectweb.asm.MethodVisitor

class NativeModeWriter(fuckerClassName: String?) : IWriter(fuckerClassName) {
    override fun write(key: ByteArray, value: ByteArray, mv: MethodVisitor): String {
        TODO()
    }
}