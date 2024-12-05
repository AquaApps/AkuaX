package fan.akua.protect.byteflow.core

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes

abstract class ByteFlowTransform : AsmClassVisitorFactory<TransformParams>, Opcodes {

    override fun createClassVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor,
    ): ClassVisitor = with(parameters.get()) {

        return object : ClassVisitor(Opcodes.ASM9, nextClassVisitor) {}
    }

    override fun isInstrumentable(classData: ClassData): Boolean {
        return true
    }
}