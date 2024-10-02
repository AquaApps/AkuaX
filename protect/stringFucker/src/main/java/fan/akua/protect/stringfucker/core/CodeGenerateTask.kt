package fan.akua.protect.stringfucker.core

import fan.akua.protect.stringfucker.StorageMode
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.PrintWriter
import javax.inject.Inject

@CacheableTask
abstract class CodeGenerateTask @Inject constructor() : DefaultTask() {
    companion object {
        const val PLUGIN_CLASS_NAME = "StringFucker"
        const val PLUGIN_PACKAGE_NAME = "fan.akua.protect"
    }

    @get:Input
    abstract val tmpDir: Property<File>

    @get:Input
    abstract val implementation: Property<String>

    @get:Input
    abstract val mode: Property<StorageMode>

    @TaskAction
    fun injectSource() {
        val path = tmpDir.get().absolutePath +
                PLUGIN_PACKAGE_NAME.replace('.', File.separatorChar) +
                File.separatorChar +
                PLUGIN_CLASS_NAME +
                ".java"
        File(path).run {
            if (!parentFile.exists() && !parentFile.mkdirs())
                throw IOException("Can not mkdirs the dir: $parentFile")
            PrintWriter(FileWriter(this)).use { writer ->
                writer.print(generate())
            }
        }
    }


    private fun generate(): String {
        val modeImport =
            if (mode.get() == StorageMode.Native) "import $PLUGIN_PACKAGE_NAME.Native;" else ""
        val impl = implementation.get()
        val lastIndexOfDot: Int = impl.lastIndexOf(".")
        val implClassName = if (lastIndexOfDot == -1) impl else impl.substring(
            impl.lastIndexOf(".") + 1
        )

        return """
        package $PLUGIN_PACKAGE_NAME;
        
        import ${implementation.get()};
        
        $modeImport
        
        public final class $PLUGIN_CLASS_NAME {
            private static final $implClassName IMPL = new $implClassName();
            
            public static String decrypt(byte[] value, byte[] key) {
                return IMPL.decrypt(value, key);
            }
        }
    """.trimIndent()
    }
}