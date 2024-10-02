package fan.akua.protect.stringfucker.core.asm

import fan.akua.protect.stringfucker.BlockList
import fan.akua.protect.stringfucker.StorageMode
import fan.akua.protect.stringfucker.StringFuckerWrapper
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes

object ClassVisitorFactory {
    fun wrapper(
        processClassName: String,
        fuckerClassName: String,
        implClass: String,
        mode: StorageMode,
        nextClassVisitor: ClassVisitor
    ): ClassVisitor {
        if (BlockList.inBlockList(processClassName)) {
            println("StringFucker ignore: $processClassName")
            return createEmpty(nextClassVisitor)
        }
        println("StringFucker process: $processClassName")
        return StringFuckerClassVisitor(
            impl = StringFuckerWrapper(implClass),
            mode = mode,
            fuckerClass = fuckerClassName,
            nextCV = nextClassVisitor
        )
    }

    private fun createEmpty(cv: ClassVisitor): ClassVisitor {
        return object : ClassVisitor(Opcodes.ASM9, cv) {}
    }
}