package org.team1277.frc.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

/**
 * FRC emulator setup.
 */
class DownloadEmulator extends DefaultTask
{
	static final String EMULATOR_LOCATION = 'https://dl.dropboxusercontent.com/u/54680365/frcEmulator/frcEmulator.jar'

	DownloadEmulator()
	{
		onlyIf { !getEmulatorFile().exists() }
	}

	@OutputFile
	public File getEmulatorFile()
	{
		return new File(project.gradle.gradleUserHomeDir, 'caches/frc/frcEmulator.jar')
	}

	@TaskAction
	public void download()
	{
		emulatorFile.parentFile.mkdirs()

		emulatorFile.withOutputStream { fileOut ->
			def out = new BufferedOutputStream(fileOut)

			new URL(EMULATOR_LOCATION).withInputStream { ins ->
				out << ins
			}
		}
	}
}
