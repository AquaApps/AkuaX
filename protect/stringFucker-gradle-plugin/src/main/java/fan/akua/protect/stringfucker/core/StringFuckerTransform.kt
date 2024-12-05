package fan.akua.protect.stringfucker.core

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import fan.akua.protect.stringfucker.BlockList
import fan.akua.protect.stringfucker.FuckMode
import fan.akua.protect.stringfucker.IStringFucker

import fan.akua.protect.stringfucker.core.asm.FuckClassVisitor
import fan.akua.protect.stringfucker.core.asm.VisitorParams
import fan.akua.protect.stringfucker.core.mode.JavaModeModeWriter
import fan.akua.protect.stringfucker.core.mode.NativeModeModeWriter
import fan.akua.protect.stringfucker.utils.TextUtil
import org.gradle.api.GradleException
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes

abstract class StringFuckerTransform : AsmClassVisitorFactory<TransformParams> {

    override fun createClassVisitor(
        classContext: ClassContext,
        nextClassVisitor: ClassVisitor,
    ): ClassVisitor = with(parameters.get()) {
        val fuckerClassName = fuckerClassName.get().replace('.', '/')
        val deleteIgnoreAnnotation = deleteIgnoreAnnotation.get()
        val impl = implClass.get()
        val modeWriter = when (val mode = mode.get()) {
            FuckMode.Java -> JavaModeModeWriter(fuckerClassName)
            FuckMode.Native -> NativeModeModeWriter(fuckerClassName)
            else -> throw GradleException("Unknown Mode(${mode}).")
        }
        return FuckClassVisitor(
            nextClassVisitor,
            VisitorParams(deleteIgnoreAnnotation, modeWriter, impl as IStringFucker)
        )
    }

    override fun isInstrumentable(classData: ClassData): Boolean = with(parameters.get()) {
        if (BlockList.inBlockList(classData.className) or
            !isInAllowPackages(allowPackages.get().toList(), classData.className)
        ) {
            if (debug.get())
                println("StringFucker ignore: $classData.className")
            return false
        }
        return true
    }

    private fun isInAllowPackages(fogPackages: List<String>, className: String): Boolean {
        if (TextUtil.isEmpty(className)) {
            return false
        }
        if (fogPackages.isEmpty()) {
            return true
        }
        return fogPackages.parallelStream().filter { pack ->
            className.replace('/', '.').startsWith("$pack.")
        }.findAny().isPresent
    }
}