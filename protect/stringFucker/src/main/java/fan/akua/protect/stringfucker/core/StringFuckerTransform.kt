package fan.akua.protect.stringfucker.core

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import fan.akua.protect.stringfucker.core.asm.ClassVisitorFactory
import org.objectweb.asm.ClassVisitor

abstract class StringFuckerTransform : AsmClassVisitorFactory<TransformParams> {

    override fun createClassVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor
    ) = with(parameters.get()) {
        val processClassName = classContext.currentClassData.className
        val fuckerClassName = fuckerClassName.get()
        val implClass = implClass.get()
        val mode = mode.get()
        ClassVisitorFactory.wrapper(
            processClassName = processClassName,
            fuckerClassName = fuckerClassName,
            implClass = implClass,
            mode = mode,
            nextClassVisitor = nextClassVisitor
        )
    }


    override fun isInstrumentable(classData: ClassData): Boolean {
        return true
    }

}