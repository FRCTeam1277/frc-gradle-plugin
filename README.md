## frc-gradle-plugin

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
}
```


