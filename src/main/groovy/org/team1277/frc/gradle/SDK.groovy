package org.team1277.frc.gradle

import org.gradle.api.GradleException

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 * The FRC Sun Spot SDK
 */
class SDK
{
	final Path root

	public SDK(Path root)
	{
		this.root = root
	}

	public Path file(String path)
	{
		return root.resolve(path)
	}

	public Path getAntFile()
	{
		return file('build.xml')
	}

	public Path getLibDir()
	{
		return file("lib")
	}

	public List<String> getLibraryNames()
	{
		return ["networktables-crio", "squawk", "wpilibj"]
	}

	public List<Path> getLibraries()
	{
		return libraryNames.collect { libDir.resolve("${it}.jar") }
	}

	public Path getJavadoc()
	{
		return file('doc/javadoc')
	}

	public Path getPropertiesFile()
	{
		return Paths.get(System.getProperty('user.home'), '.sunspotfrc.properties')
	}

	public static SDK detect()
	{
		Path root = Paths.get(System.getProperty("user.home"), "sunspotfrcsdk")
		if(Files.isDirectory(root))
		{
			return new SDK(root)
		}
		else
		{
			throw new GradleException("SDK not found at $root")
		}
	}
}
