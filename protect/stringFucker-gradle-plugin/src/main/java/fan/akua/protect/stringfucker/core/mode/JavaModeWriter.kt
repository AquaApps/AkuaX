package fan.akua.protect.stringfucker.core.mode

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type

class JavaModeWriter (fuckerClassName: String?) : IWriter(fuckerClassName) {
    override fun write(key: ByteArray, value: ByteArray, mv: MethodVisitor): String {
        pushArray(mv, value)
        pushArray(mv, key)
        super.writeClass(mv, "([B[B)Ljava/lang/String;")
        return value.contentToString()
    }
    private fun pushArray(mv: MethodVisitor, buffer: ByteArray) {
        pushNumber(mv, buffer.size)
        mv.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_BYTE)
        mv.visitInsn(Opcodes.DUP)
        for (i in buffer.indices) {
            pushNumber(mv, i)
            pushNumber(mv, buffer[i].toInt())
            mv.visitInsn(Type.BYTE_TYPE.getOpcode(Opcodes.IASTORE))
            if (i < buffer.size - 1) mv.visitInsn(Opcodes.DUP)
        }
    }

    private fun pushNumber(mv: MethodVisitor, value: Int) {
        if (value >= -1 && value <= 5) {
            mv.visitInsn(Opcodes.ICONST_0 + value)
        } else if (value >= Byte.MIN_VALUE && value <= Byte.MAX_VALUE) {
            mv.visitIntInsn(Opcodes.BIPUSH, value)
        } else if (value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
            mv.visitIntInsn(Opcodes.SIPUSH, value)
        } else {
            mv.visitLdcInsn(value)
        }
    }
}