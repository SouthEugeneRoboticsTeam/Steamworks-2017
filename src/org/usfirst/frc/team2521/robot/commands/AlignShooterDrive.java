package org.usfirst.frc.team2521.robot.commands;

import org.usfirst.frc.team2521.robot.Robot;

public class AlignShooterDrive extends DriveToUltra {
	// How far from the right side of the boiler the robot's center should be
	private static final double X_SETPOINT = 21;

	private static final double ROBOT_HALF_WIDTH = 15.75;

	// How far the side ultrasonic is from front of the robot
	private static final double SIDE_ULTRA_OFFSET = 19.5;

	private static final double ROBOT_LENGTH = 26;

	public AlignShooterDrive() {
		super(0, true);
	}
	
	@Override
	protected void initialize() {
	super.setpoint = Robot.sensors.getSideLidarInches() + X_SETPOINT * Math.sqrt(2) + ROBOT_HALF_WIDTH
		- SIDE_ULTRA_OFFSET - 0.5 * ROBOT_LENGTH;
	}

}
