package fan.akua.protect.stringfucker.core.asm

import fan.akua.protect.stringfucker.BlockList
import fan.akua.protect.stringfucker.FuckMode
import fan.akua.protect.stringfucker.StringFuckerWrapper
import fan.akua.protect.stringfucker.utils.TextUtil
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes

object ClassVisitorFactory {
    fun wrapper(
        debug: Boolean,
        processClassName: String,
        fuckerClassName: String,
        impl: Any,
        mode: FuckMode,
        nextClassVisitor: ClassVisitor,
        allowPackages: List<String>,
    ): ClassVisitor {
        if (BlockList.inBlockList(processClassName) or
            !isInAllowPackages(allowPackages, processClassName)
        ) {
            if (debug)
                println("StringFucker ignore: $processClassName")
            return createEmpty(nextClassVisitor)
        }
        if (debug)
            println("StringFucker process: $processClassName")
        return StringFuckerClassVisitor(
            impl = StringFuckerWrapper(impl),
            mode = mode,
            fuckerClass = fuckerClassName,
            nextCV = nextClassVisitor
        )
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