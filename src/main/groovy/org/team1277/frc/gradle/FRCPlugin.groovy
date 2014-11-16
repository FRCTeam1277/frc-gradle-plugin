package org.team1277.frc.gradle;

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.SourceSet
import org.gradle.plugins.ide.eclipse.EclipsePlugin
import org.gradle.plugins.ide.eclipse.model.EclipseModel

class FRCPlugin implements Plugin<Project>
{
	public static final String FRC_GROUP = "FRC"
	public static final String CONFIGURE_SDK_PROPERTIES_TASK_NAME = "configureSdkProperties"

	private SDK sdk
	private FRCExtension extension

	void apply(Project project)
	{
		project.plugins.apply('java')
		project.sourceCompatibility = 1.3
		project.targetCompatibility = 1.2

		sdk = SDK.detect()
		extension = project.extensions.create("frc", FRCExtension, sdk)

		importBuild(project)
		if(extension.configureSdkProperties)
		{
			createConfigureSdkPropertiesTask(project)
		}

		FRCDependencies deps = new FRCDependencies(sdk)
		deps.addRepository(project.repositories)
		deps.addDependencies(project.configurations.getByName(JavaPlugin.COMPILE_CONFIGURATION_NAME), project.dependencies)

		project.plugins.withId('eclipse') {
			new EclipseConfigurer(sdk).configure(project.extensions.getByType(EclipseModel))
		}

		//TODO: disable default java tasks?
	}

	private void importBuild(Project project)
	{
		// TODO: automatically update MANIFEST.MF with project info

		SourceSet main = project.sourceSets[SourceSet.MAIN_SOURCE_SET_NAME]

		project.ant.property(file: sdk.propertiesFile.toAbsolutePath().toFile())
		project.ant.properties['project.dir'] = project.projectDir
		project.ant.properties['src.dir'] = main.java.srcDirs.iterator().next()
		project.ant.properties['resources.dir'] = main.resources.srcDirs.iterator().next()

		project.ant.properties['sunspot.home'] = sdk.root.toAbsolutePath().toString()
		project.ant.importBuild(sdk.antFile.toFile()) {
			return 'frc-' + it
		}

		project.tasks.all { Task task ->
			if(task.name.startsWith('frc-'))
			{
				task.group = 'FRC Build'
			}
		}

		project.tasks.getByName(JavaBasePlugin.BUILD_TASK_NAME).dependsOn 'frc-jar-app'
	}

	private void createConfigureSdkPropertiesTask(Project project)
	{
		ConfigureSDKProperties task = project.tasks.create(CONFIGURE_SDK_PROPERTIES_TASK_NAME, ConfigureSDKProperties)
		task.group = FRC_GROUP
		task.description = 'Configure the FRC SDK properties'

		task.conventionMapping.map('remoteAddress', {
			extension.remoteAddress
		})
		task.conventionMapping.map('sdk', {
			extension.sdk
		})
	}
}

