package fan.akua.protect.stringfucker.core.asm

import fan.akua.protect.stringfucker.IStringFucker
import fan.akua.protect.stringfucker.StorageMode
import fan.akua.protect.stringfucker.keygen.IKeyGen
import fan.akua.protect.stringfucker.keygen.RandomKeyGen
import fan.akua.protect.stringfucker.core.mode.IWriter
import fan.akua.protect.stringfucker.core.mode.JavaModeWriter
import org.gradle.api.GradleException
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class StringFuckerClassVisitor(
    val impl: IStringFucker,
    val mode: StorageMode,
    var fuckerClass: String,
    val nextCV: ClassVisitor?,
) :
    ClassVisitor(Opcodes.ASM9, nextCV) {
    companion object {
        const val STRING_DESC = "Ljava/lang/String;"
        private const val IGNORE_ANNOTATION: String =
            "Lfan/akua/protect/stringfucker/annotation/IgnoreStringFucker;"
        lateinit var mKeyGen: IKeyGen
        lateinit var mWriter: IWriter

        fun encryptAndWrite(value: String, impl: IStringFucker, mv: MethodVisitor) {
            val key: ByteArray = mKeyGen.generate(value)
            val encryptValue: ByteArray = impl.encrypt(value, key)
            val result: String = mWriter.write(key, encryptValue, mv)

            println("StringFucker: map $value -> $result")
        }

    }

    init {
        // todo: support more keygen
        fuckerClass = fuckerClass.replace('.', '/')
        mKeyGen = RandomKeyGen()
        when (mode) {
            StorageMode.Native -> {
                // todo: NativeModeWriter
                throw GradleException("StringFucker: The Native mode has not been implemented yet.")
            }

            StorageMode.Java -> {
                mWriter = JavaModeWriter(fuckerClass)
            }
        }
    }

    private var mClassName: String? = null
    private var mIgnoreClass: Boolean = false
    private var isCInitExists = false
    private val mStaticFinalFields = ArrayList<StringField>()
    private val mStaticFields = ArrayList<StringField>()
    private val mFinalFields = ArrayList<StringField>()
    private val mFields = ArrayList<StringField>()

    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?,
    ) {
        mClassName = name
        super.visit(version, access, name, signature, superName, interfaces)
    }

    override fun visitAnnotation(descriptor: String, visible: Boolean): AnnotationVisitor {
        mIgnoreClass = IGNORE_ANNOTATION == descriptor
        return super.visitAnnotation(descriptor, visible)
    }

    override fun visitField(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        value: Any?,
    ): FieldVisitor {
        var backValue = value
        if (STRING_DESC == descriptor && name != null && !mIgnoreClass) {
            // static final, in this condition, the value is null or not null.
            if ((access and Opcodes.ACC_STATIC) != 0 && (access and Opcodes.ACC_FINAL) != 0) {
                mStaticFinalFields.add(StringField(name, value as String?))
                backValue = null
            }

            // static, in this condition, the value is null.
            if ((access and Opcodes.ACC_STATIC) != 0 && (access and Opcodes.ACC_FINAL) == 0) {
                mStaticFields.add(StringField(name, value as String?))
                backValue = null
            }

            // final, in this condition, the value is null or not null.
            if ((access and Opcodes.ACC_STATIC) == 0 && (access and Opcodes.ACC_FINAL) != 0) {
                mFinalFields.add(StringField(name, value as String?))
                backValue = null
            }

            // normal, in this condition, the value is null.
            if ((access and Opcodes.ACC_STATIC) == 0 && (access and Opcodes.ACC_FINAL) == 0) {
                mFields.add(StringField(name, value as String?))
                backValue = null
            }
        }
        return super.visitField(access, name, descriptor, signature, backValue)
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?,
    ): MethodVisitor {
        val mv = super.visitMethod(access, name, descriptor, signature, exceptions)
        if (mv == null || mIgnoreClass) return mv
        return when (name) {
            "<clinit>" -> {
                isCInitExists = true
                CInitMethodVisitor(mv, impl).bindFields(mStaticFinalFields, mStaticFields)
                    .bindClassName(mClassName)
            }

            "<init>" -> InitMethodVisitor(mv, impl)
            else -> DefaultMethodVisitor(mv, impl).bindFields(mStaticFinalFields, mFinalFields)
                .bindClassName(mClassName)
        }
    }

    override fun visitEnd() {
        if (!mIgnoreClass && !isCInitExists && mStaticFinalFields.isNotEmpty()) {
            val mv = super.visitMethod(Opcodes.ACC_STATIC, "<clinit>", "()V", null, null)
            mv.visitCode()
            // Here init static final fields.
            mStaticFinalFields.parallelStream().filter { field ->
                impl.canFuck(field.value)
            }.forEach { field ->
                encryptAndWrite(field.value, impl, mv)
                mv.visitFieldInsn(
                    Opcodes.PUTSTATIC,
                    mClassName,
                    field.name,
                    STRING_DESC
                )
            }

            mv.visitInsn(Opcodes.RETURN)
            mv.visitMaxs(1, 0)
            mv.visitEnd()
        }
        super.visitEnd()
    }

}