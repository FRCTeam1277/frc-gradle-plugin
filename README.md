## DEPRECATED

The 2015 FRC suite includes a [Java
plugin](http://wpilib.screenstepslive.com/s/4485/m/13809/l/145002-installing-eclipse-c-java).
This should be used instead, since it supports the new tools and build system.

## gradle-frc-plugin

[![Download](https://api.bintray.com/packages/ben-navetta/gradle-plugins/gradle-frc-plugin/images/download.svg)](https://bintray.com/ben-navetta/gradle-plugins/gradle-frc-plugin/_latestVersion)

Uses Gradle to support building FRC projects, mainly from Eclipse since the FRC
SDK won't have Eclipse support until January 2015.

### Usage

You must have the FRC SDK (`~/sunspotfrcsdk`) installed via Netbeans. See
[WPI's documentation](http://wpilib.screenstepslive.com/s/3120/m/7885/l/79405-installing-the-java-development-tools)
for help. This plugin will configure your `~/.sunspotfrc.properties` file
appropriately, set Java compatibility versions, and import the FRC build tasks. It will also add dependencies on
the FRC libraries and set up the `build` task to depend on the FRC jar task.
If the Eclipse plugin is applied, this will also set up source and Javadoc
locations.

```groovy
plugins {
	id "eclipse"
	id "org.team1277.frc" version "1.0.1"
}

frc {
    teamNumber = 1277

    robotClass = 'org.team1277.Robot'
}
```

Or to use with Gradle versions before 2.1:

```groovy

buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath 'org.team1277.frc.gradle:gradle-frc-plugin:1.0.1'
    }
}

apply plugin: 'org.team1277.frc'

```

If `frc.configureSdkProperties` is set to false, the `~/.sunspotfrc.properties`
file will not be configured. This means that the SDK must have been configured
through Netbeans instead. The `frc` extension also provides a read-only
`remoteAddress` property with the robot IP address (i.e. `10.12.77.2`).

The detected [SDK](src/main/groovy/org/team1277/frc/gradle/SDK.groovy) is
exposed under `frc.sdk`.

### Emulator

This plugin has support for the [FRC Robot Emulator](https://github.com/itsZN/FRC-Robot-Emulator) developed by a team
member. The `downloadEmulator` task will download the latest released emulator version and the emulator cam be run via
the `emulator` task. To enable debugging, pass `--debug-jvm` on the Gradle command line. Note that the `frc.robotClass`
property must be set.

## TODO

* Create a separate sample project that people can clone and rename. It should
  also have instructions for importing to Eclipse.
* Maybe add networking support. Check if it's Windows or OS X and run the
  appropriate commands to set up robot and normal networking. It would also be
  nice to have a mapping of usernames to numbers for the final IP address
  component so there automatically aren't address conflicts. There should also
  be a way of forcing the driver station address and making sure that reserved
  addresses (robot, driver station) aren't used.
