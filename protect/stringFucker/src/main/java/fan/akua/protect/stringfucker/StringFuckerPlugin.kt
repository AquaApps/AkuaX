package fan.akua.protect.stringfucker

import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.Variant
import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import fan.akua.protect.stringfucker.core.CodeGenerateTask
import fan.akua.protect.stringfucker.core.StringFuckerTransform
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

class StringFuckerPlugin : Plugin<Project> {
    companion object {
        private const val PLUGIN_NAME = "stringFucker"
        private const val BANNER = """
============================================================
/ ___|| |_ _ __(_)_ __   __ _|  ___|   _  ___| | _____ _ __ 
\___ \| __| '__| | '_ \ / _` | |_ | | | |/ __| |/ / _ \ '__|
 ___) | |_| |  | | | | | (_| |  _|| |_| | (__|   <  __/ |
|____/ \__|_|  |_|_| |_|\__, |_|   \__,_|\___|_|\_\___|_|
============================================================
        """
    }

    private fun injectTask(
        extension: BaseExtension,
        action: (com.android.build.gradle.api.BaseVariant) -> Unit
    ) {
        when (extension) {
            is AppExtension -> extension.applicationVariants.all(action)
            is LibraryExtension -> {
                extension.libraryVariants.all(action)
            }

            else -> throw GradleException(
                "StringFucker plugin must be used with android app,library or feature plugin"
            )
        }
    }

    override fun apply(project: Project) {
        println(BANNER)
        project.extensions.create(PLUGIN_NAME, PluginExtension::class.java)
        val agpExtension = project.extensions.findByType(BaseExtension::class.java)
            ?: throw GradleException("$PLUGIN_NAME plugin must be used with AGP")
        val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)

        androidComponents.onVariants { variant: Variant ->
            val stringFucker = project.extensions.getByType(PluginExtension::class.java)
            if (!stringFucker.enable) return@onVariants
            if (stringFucker.implementation.isNullOrEmpty())
                throw IllegalArgumentException("Missing stringFucker implementation config")
            variant.instrumentation.run {
                transformClassesWith(
                    StringFuckerTransform::class.java,
                    InstrumentationScope.PROJECT
                ) { param ->
                    param.fuckerClassName.set("${CodeGenerateTask.PLUGIN_PACKAGE_NAME}.${CodeGenerateTask.PLUGIN_CLASS_NAME}")
                    param.implClass.set(stringFucker.implementation)
                    param.mode.set(stringFucker.mode)
                }
                setAsmFramesComputationMode(FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS)
            }
            injectTask(agpExtension) { baseVariant ->
                val processTaskName =
                    "processStringFucker-${baseVariant.name.replaceFirstChar { it.uppercase() }}"
                if (project.getTasksByName(processTaskName, true).isNotEmpty())
                    return@injectTask
                val tmpFile = File(
                    project.buildFile,
                    "generated${File.separatorChar}source${File.separatorChar}stringFucker${File.separatorChar}${baseVariant.name.replaceFirstChar { it.uppercase() }}"
                )
                val provider =
                    project.tasks.register(processTaskName, CodeGenerateTask::class.java) { task ->
                        task.tmpDir.set(tmpFile)
                        task.implementation.set(stringFucker.implementation)
                        task.mode.set(stringFucker.mode)
                    }
                baseVariant.registerJavaGeneratingTask(provider, tmpFile)
            }
        }

//        project.afterEvaluate {
//            val task = project.tasks.getByName("mergeDexRelease")
//            task.doLast {
//                println("$PLUGIN_NAME ---> mergeDexRelease")
//            }
//            task.inputs.files.forEach { element ->
//                println("$PLUGIN_NAME ---> inputs $element")
//            }
//            task.outputs.files.forEach { element ->
//
//            }
//        }
    }
}