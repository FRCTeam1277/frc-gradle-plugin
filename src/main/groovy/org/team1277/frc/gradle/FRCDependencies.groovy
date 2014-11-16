package org.team1277.frc.gradle

import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.FlatDirectoryArtifactRepository

/**
 * Manages FRC SDK dependencies
 */
class FRCDependencies
{
	private final SDK sdk

	public FRCDependencies(SDK sdk)
	{
		this.sdk = sdk
	}

	public void addRepository(RepositoryHandler repositoryHandler)
	{
		repositoryHandler.flatDir({ FlatDirectoryArtifactRepository repo ->
			repo.name = "FRC SDK"
			repo.dirs(sdk.libDir.toFile())
		})
	}

	public void addDependencies(Configuration configuration, DependencyHandler dependencyHandler)
	{
		sdk.libraryNames.each {
			configuration.dependencies.add(dependencyHandler.create(":$it:"))
		}
	}
}
