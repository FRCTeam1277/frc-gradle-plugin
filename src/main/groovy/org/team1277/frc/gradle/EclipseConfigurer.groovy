package org.team1277.frc.gradle

import org.gradle.plugins.ide.eclipse.model.*
import org.gradle.plugins.ide.eclipse.model.internal.FileReferenceFactory

/**
 * Sets up Eclipse to build the FRC project
 */
class EclipseConfigurer
{
	private SDK sdk

	public EclipseConfigurer(SDK sdk)
	{
		this.sdk = sdk
	}

	public void configure(EclipseModel eclipseModel)
	{
		configureClasspath(eclipseModel.classpath)
	}

	private void configureClasspath(EclipseClasspath classpath)
	{
		classpath.file.whenMerged { cp ->
			FileReferenceFactory refFactory = new FileReferenceFactory()

			FileReference javadoc = refFactory.fromFile(sdk.file('doc/javadoc').toFile())
			FileReference wpiSource = refFactory.fromFile(sdk.file('lib/wpilibj.src.jar').toFile())
			FileReference networktablesJavadoc = refFactory.fromFile(sdk.file('desktop-lib/networktables-desktop-javadoc.jar').toFile())

			cp.entries.each { ClasspathEntry entry ->
				if(entry.kind == 'lib')
				{
					assert entry instanceof Library
					String name = entry.moduleVersion.name
					if(name == 'wpilibj' || name == 'squawk')
					{
						entry.javadocPath = javadoc
						// Gradle assumes it's a jar, not a directory
						entry.entryAttributes['javadoc_location'] = javadoc.file.toURI() as String

						entry.sourcePath = wpiSource
					}

					if(name.contains('networktables'))
					{
						entry.javadocPath = networktablesJavadoc
					}
				}
			}
		}
	}
}
