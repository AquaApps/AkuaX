package fan.akua.protect.stringfucker

import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.gradle.BaseExtension
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

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

    override fun apply(project: Project) {
        println(BANNER)
        project.extensions.create(PLUGIN_NAME, StringFuckerPluginExtension::class.java)
        val agpExtension = project.extensions.findByType(BaseExtension::class.java)
            ?: throw GradleException("$PLUGIN_NAME plugin must be used with AGP")
        val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)

        project.afterEvaluate {
            val task = project.tasks.getByName("mergeDexRelease")
            task.doLast {
                println("$PLUGIN_NAME ---> mergeDexRelease")
            }
            task.inputs.files.forEach { element ->
                println("$PLUGIN_NAME ---> inputs $element")
            }
            task.outputs.files.forEach { element->

            }
        }
    }
}