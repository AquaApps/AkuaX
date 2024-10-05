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
        val processClassName = classContext.currentClassData.className
        val fuckerClassName = fuckerClassName.get().replace('.', '/')
        val debug = debug.get()
        val deleteIgnoreAnnotation = deleteIgnoreAnnotation.get()
        val impl = implClass.get()
        val mode = mode.get()
        val allowPackages = allowPackages.get().toList()

        if (BlockList.inBlockList(processClassName) or
            !isInAllowPackages(allowPackages, processClassName)
        ) {
            if (debug)
                println("StringFucker ignore: $processClassName")
            return createEmpty(nextClassVisitor)
        }
        if (debug)
            println("StringFucker process: $processClassName")
        val modeWriter = when (mode) {
            FuckMode.Java -> JavaModeModeWriter(fuckerClassName)
            FuckMode.Native -> NativeModeModeWriter(fuckerClassName)
            else -> throw GradleException("Unknown Mode(${mode}).")
        }
        return FuckClassVisitor(
            nextClassVisitor,
            VisitorParams(deleteIgnoreAnnotation, modeWriter, impl as IStringFucker)
        )
    }

    override fun isInstrumentable(classData: ClassData): Boolean {
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

    private fun createEmpty(cv: ClassVisitor): ClassVisitor {
        return object : ClassVisitor(Opcodes.ASM9, cv) {}
    }
}