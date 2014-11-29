package org.team1277.frc.gradle

/**
 * Configure the FRC build
 */
class FRCExtension
{
	public FRCExtension(SDK sdk)
	{
		this.sdk = sdk
	}

	/**
	 * Whether or not to configure the SDK properties file
	 * ({@code ~/.sunspotfrc.properties}). This file must contain
	 * the remote address and SDK location for the FRC build system
	 * to work.
	 */
	boolean configureSdkProperties = true

	/**
	 * The FRC SDK
	 */
	SDK sdk

	/**
	 * The team number (i.e. 1277)
	 */
	int teamNumber

	/**
	 * The main robot class (i.e. {@code org.team1277.Robot})
	 */
	String robotClass

	/**
	 * The remote address of the robot (i.e. 10.12.77.2)
	 * @return
	 */
	public String getRemoteAddress()
	{
		String teamID = String.format("%04d", getTeamNumber())
		String firstPart = teamID.substring(0, 2)
		String secondPart = teamID.substring(2)
		return String.format("10.%s.%s.2", firstPart, secondPart)
	}
}
