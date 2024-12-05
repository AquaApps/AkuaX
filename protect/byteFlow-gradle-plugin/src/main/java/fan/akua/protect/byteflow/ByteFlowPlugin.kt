package fan.akua.protect.byteflow

import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.Variant
import com.android.build.gradle.BaseExtension
import fan.akua.protect.byteflow.core.ByteFlowTransform
import fan.akua.protect.byteflow.core.toParams
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

class ByteFlowPlugin : Plugin<Project> {
    companion object {
        private const val PLUGIN_NAME = "byteFlow"
        private const val BANNER = """
======================================================================================
 ███████████              █████             ███████████ ████                          
░░███░░░░░███            ░░███             ░░███░░░░░░█░░███                          
 ░███    ░███ █████ ████ ███████    ██████  ░███   █ ░  ░███   ██████  █████ ███ █████
 ░██████████ ░░███ ░███ ░░░███░    ███░░███ ░███████    ░███  ███░░███░░███ ░███░░███ 
 ░███░░░░░███ ░███ ░███   ░███    ░███████  ░███░░░█    ░███ ░███ ░███ ░███ ░███ ░███ 
 ░███    ░███ ░███ ░███   ░███ ███░███░░░   ░███  ░     ░███ ░███ ░███ ░░███████████  
 ███████████  ░░███████   ░░█████ ░░██████  █████       █████░░██████   ░░████░████   
░░░░░░░░░░░    ░░░░░███    ░░░░░   ░░░░░░  ░░░░░       ░░░░░  ░░░░░░     ░░░░ ░░░░    
               ███ ░███                                                               
              ░░██████                                                                
               ░░░░░░                                                                 
======================================================================================
        """
        private const val EXCEPTION =
            "ByteFlow plugin must be used with android app, library or feature plugin. If you want to use it with Java Project, try ByteFlow-forJava (Planned)."
    }


    override fun apply(project: Project) {
        println(BANNER)
        project.extensions.create(PLUGIN_NAME, PluginExtension::class.java)
        project.extensions.findByType(BaseExtension::class.java) ?: throw GradleException(EXCEPTION)
        val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)

        androidComponents.onVariants { variant: Variant ->
            val pluginExtension = project.extensions.getByType(PluginExtension::class.java)
            println(
                """
            =========================================================
            project = ${project.name}
            variant = ${variant.name}
            
            PluginExtension Configuration:
            enable = ${pluginExtension.enable}
            debug = ${pluginExtension.debug}
            deleteIgnoreAnnotation = ${pluginExtension.deleteIgnoreAnnotation}
            allowPackages ="${pluginExtension.allowPackages.joinToString(", ")}"
            =========================================================
        """.trimIndent()
            )
            if (!pluginExtension.enable) return@onVariants

            variant.instrumentation.run {
                setAsmFramesComputationMode(FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS)
                transformClassesWith(
                    ByteFlowTransform::class.java,
                    InstrumentationScope.PROJECT
                ) { param -> param.toParams(pluginExtension) }
            }

        }
    }
}