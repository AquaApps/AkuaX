package fan.akua.protect.stringfucker.core.asm

import fan.akua.protect.stringfucker.BlockList
import fan.akua.protect.stringfucker.StorageMode
import fan.akua.protect.stringfucker.StringFuckerWrapper
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes

object ClassVisitorFactory {
    fun wrapper(
        debug: Boolean,
        processClassName: String,
        fuckerClassName: String,
        impl: Any,
        mode: StorageMode,
        nextClassVisitor: ClassVisitor,
    ): ClassVisitor {
        if (BlockList.inBlockList(processClassName)) {
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

    private fun createEmpty(cv: ClassVisitor): ClassVisitor {
        return object : ClassVisitor(Opcodes.ASM9, cv) {}
    }
}